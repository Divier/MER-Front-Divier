/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.rr;


import co.com.claro.mgl.jpa.entities.rr.HhppRR;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * Representar las acciones realizadas a la AS400
 * <p>
 * Para realizar consultas a la As400
 *
 * @author becerraarmr
 *
 * @version 2017 revisión 1.0
 * @see GeGenericDaoRRImpl
 */
public class As400Dao {

  private static final Logger LOGGER = LogManager.getLogger(As400Dao.class);
  /**
   * Para la conexión con el datasource AS400PU
   */
  private final EntityManager entityManager;

  /**
   * Crear la instancia
   * <p>
   * Crea una instancia
   * 
   * @author becerraarmr
   */
  public As400Dao() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("AS400PU");
    entityManager = emf.createEntityManager();
  }

  /**
   * Buscar los datos que corresponden a la cuenta matriz
   * <p>
   * Se busca los datos correspondientes a la cuenta matriz
   *
   * @author becerraarmr
   * @version 2010 revisión 1.0
   *
   * @param cuentaMatriz valor de la cuenta a matriz a consultar
   *
   * @return Map con la data de la cuenta matriz consultada.
   */
  public Map<String, String> findDataCuentaMatriz(String cuentaMatriz) {
    String sql = "SELECT STSTNM as CALLE, EDHOME AS CASA, "
            + "EDTEL1 AS TELEFONO, EDNOCO AS CONTACTO, "
            + "EDNODO AS NODO, EDTUNI AS UNIDADES, "
            + "TPNOMB AS PRODUCTO, COUNT(SENOMB) AS TORRE, "
            + "EDCODG AS CUENTAMATRIZ "
            + "FROM TVCABLEDTA.TCEDFF00 JOIN CABLEDTA.STRTMSTR "
            + "ON STSTRÑ=EDCALL LEFT JOIN TVCABLEDTA.TCSEDF00 "
            + "ON EDCODG = SECODG JOIN TVCABLEDTA.TCTPDL00 "
            + "ON EDCOF6=TPCODG WHERE  EDCODG ="+cuentaMatriz+" "
            + "GROUP BY STSTNM, EDHOME, EDTEL1, EDNOCO, "
            + "EDNODO, EDTUNI,TPNOMB, EDCODG";
    try {
      if (!entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().begin();
      }
      entityManager.flush();
      Query query=entityManager.createNativeQuery(sql);
      List<Object[]> list=query.getResultList();
      entityManager.getTransaction().commit();
      if(!list.isEmpty()){
         Object[] object=list.get(0);
         Map<String, String> data = new HashMap<>();
          final String UNIDADES = "UNIDADES";
          final String NODO = "NODO";
          final String TORRE = "TORRE";
          final String CONTACTO = "CONTACTO";
          final String DIRECCION = "DIRECCION";
          final String PRODUCTO = "PRODUCTO";
          final String TELEFONO = "TELEFONO";
          data.put(DIRECCION, object[0]+" "+object[1]);//calle y casa
          data.put(TELEFONO, object[2]+"");//teléfono
          data.put(CONTACTO, object[3]+"");//contacto
          data.put(NODO, object[4]+"");//Nodo
          data.put(UNIDADES, object[5]+"");//Unidades
          data.put(PRODUCTO, object[6]+"");//Producto
          data.put(TORRE, object[7]+"");//Torre
          return data;
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      throw new Error(e.getMessage());
    }
    return null;
  }
  
  
    /**
     * Buscar los datos en la tabla de log de hhpp en RR
     * @author Victor Bocanegra
     * @param paginaSelected
     * @param maxResults
     * @param fechaInicioUlt
     * @param fechaInicio
     * @param horaInicio
     * @return List<HhppRR> con la data de los hhpp encontrados.
     */
    public List<HhppRR> findHhppLogAuditoriaRr(int paginaSelected,
            int maxResults, Date fechaInicioUlt, String fechaInicio, String horaInicio) {

        List<HhppRR> resultList = null;
        try {

            Date fechaHoy = new Date();
            int diasDiferencia=(int) ((fechaHoy.getTime()-fechaInicioUlt.getTime())/86400000);
            List<Date> fechasDiferencia;
            BigDecimal sigloIni = new BigDecimal(fechaInicio.substring(0,2));
            BigDecimal añoIni = new BigDecimal(fechaInicio.substring(2,4));
            BigDecimal mesIni = new BigDecimal(fechaInicio.substring(4,6));
            BigDecimal diaIni = new BigDecimal(fechaInicio.substring(6,8));
            BigDecimal horaIni = new BigDecimal(horaInicio);
            BigDecimal horaFin = horaIni.subtract(BigDecimal.ONE);
            
            SimpleDateFormat formatoFechaFt = new SimpleDateFormat("yyyyMMdd");
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT DISTINCT h FROM HhppRR h "
                    + " INNER JOIN LogHhppRr l "
                    + " ON  h.idUnitMstr = l.idUnitLog "
                    + " WHERE (l.fechaLogSiglo  = :sigloInicio AND l.fechaLogAño = :añoInicio "
                    + " AND l.fechaLogMes = :mesInicio AND l.fechaLogDia = :diaInicio  "
                    + " AND (l.hora BETWEEN :horaInicio AND 235959)) ");

            
            if (diasDiferencia > 0) {
                
                int diasDiferenciasTotal = diasDiferencia -1;
                
                fechasDiferencia = new ArrayList<>();
                Date fechaTosumar = fechaInicioUlt;
                for (int i = 0; i < diasDiferenciasTotal; i++) {
                    fechaTosumar = sumarDiaToFecha(fechaTosumar);
                    fechasDiferencia.add(fechaTosumar);
                }
                if (!fechasDiferencia.isEmpty()) {
                    for (Date fechaMas : fechasDiferencia) {
                        String fechaMasFor = formatoFechaFt.format(fechaMas);
                        BigDecimal sigloMas = new BigDecimal(fechaMasFor.substring(0, 2));
                        BigDecimal añoMas = new BigDecimal(fechaMasFor.substring(2, 4));
                        BigDecimal mesMas = new BigDecimal(fechaMasFor.substring(4, 6));
                        BigDecimal diaMas = new BigDecimal(fechaMasFor.substring(6, 8));

                        sql.append(" OR  (l.fechaLogSiglo = ").append(sigloMas).
                                append(" AND l.fechaLogAño = ").append(añoMas).
                                append("  AND l.fechaLogMes = ").append(mesMas).
                                append(" AND l.fechaLogDia = ").append(diaMas).append(" ) ");
                    }
                }
            }
            
            String fechaHoyFor = formatoFechaFt.format(fechaHoy);
            BigDecimal sigloHoy = new BigDecimal(fechaHoyFor.substring(0, 2));
            BigDecimal añoHoy = new BigDecimal(fechaHoyFor.substring(2, 4));
            BigDecimal mesHoy = new BigDecimal(fechaHoyFor.substring(4, 6));
            BigDecimal diaHoy = new BigDecimal(fechaHoyFor.substring(6, 8));
            
            sql.append(" OR  (l.fechaLogSiglo = ").append(sigloHoy).
                    append(" AND l.fechaLogAño= ").append(añoHoy).
                    append("  AND l.fechaLogMes = ").append(mesHoy).
                    append(" AND l.fechaLogDia = ").append(diaHoy).
                    append("   AND (l.hora BETWEEN 0 AND :horaFin))");

               
            Query query = entityManager.createQuery(sql.toString());
            query.setFirstResult(paginaSelected);
            query.setMaxResults(maxResults);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            
            query.setParameter("sigloInicio", sigloIni);
            query.setParameter("añoInicio", añoIni);
            query.setParameter("mesInicio", mesIni);
            query.setParameter("diaInicio", diaIni);
            query.setParameter("horaInicio", horaIni);
            query.setParameter("horaFin", horaFin);
           
            
            resultList = (List<HhppRR>) query.getResultList();

        } catch (PersistenceException e) {
            LOGGER.error("Error consultando auditoria de Hhpp en RR. ", e);
        }
        return resultList;
    }
    
    
     /**
     * Cuenta los datos en la tabla de log de hhpp en RR
     * @author Victor Bocanegra
     * @param fechaInicioUlt
     * @param fechaInicio
     * @param horaInicio
     * @return List<HhppRR> con la data de los hhpp encontrados.
     */
    public Long countHhppLogAuditoriaRr(Date fechaInicioUlt, String fechaInicio, String horaInicio) {

          Long resultCount = 0L;
        try {

            Date fechaHoy = new Date();
            int diasDiferencia=(int) ((fechaHoy.getTime()-fechaInicioUlt.getTime())/86400000);
            List<Date> fechasDiferencia;
            BigDecimal sigloIni = new BigDecimal(fechaInicio.substring(0,2));
            BigDecimal añoIni = new BigDecimal(fechaInicio.substring(2,4));
            BigDecimal mesIni = new BigDecimal(fechaInicio.substring(4,6));
            BigDecimal diaIni = new BigDecimal(fechaInicio.substring(6,8));
            BigDecimal horaIni = new BigDecimal(horaInicio);
            BigDecimal horaFin = horaIni.subtract(BigDecimal.ONE);
            SimpleDateFormat formatoFechaFt = new SimpleDateFormat("yyyyMMdd");
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT DISTINCT h FROM HhppRR h "
                    + " INNER JOIN LogHhppRr l "
                    + " ON  h.idUnitMstr = l.idUnitLog "
                    + " WHERE (l.fechaLogSiglo  = :sigloInicio AND l.fechaLogAño = :añoInicio "
                    + " AND l.fechaLogMes = :mesInicio AND l.fechaLogDia = :diaInicio  "
                    + " AND (l.hora BETWEEN :horaInicio AND 235959)) ");

            
            if (diasDiferencia > 0) {
                
                int diasDiferenciasTotal = diasDiferencia -1;
                
                fechasDiferencia = new ArrayList<>();
                Date fechaTosumar = fechaInicioUlt;
                for (int i = 0; i < diasDiferenciasTotal; i++) {
                    fechaTosumar = sumarDiaToFecha(fechaTosumar);
                    fechasDiferencia.add(fechaTosumar);
                }
                if (!fechasDiferencia.isEmpty()) {
                    for (Date fechaMas : fechasDiferencia) {
                        String fechaMasFor = formatoFechaFt.format(fechaMas);
                        BigDecimal sigloMas = new BigDecimal(fechaMasFor.substring(0, 2));
                        BigDecimal añoMas = new BigDecimal(fechaMasFor.substring(2, 4));
                        BigDecimal mesMas = new BigDecimal(fechaMasFor.substring(4, 6));
                        BigDecimal diaMas = new BigDecimal(fechaMasFor.substring(6, 8));

                        sql.append(" OR  (l.fechaLogSiglo = ").append(sigloMas).
                                append(" AND l.fechaLogAño = ").append(añoMas).
                                append("  AND l.fechaLogMes = ").append(mesMas).
                                append(" AND l.fechaLogDia = ").append(diaMas).append(" ) ");
                    }
                }
            }
            
            String fechaHoyFor = formatoFechaFt.format(fechaHoy);
            BigDecimal sigloHoy = new BigDecimal(fechaHoyFor.substring(0, 2));
            BigDecimal añoHoy = new BigDecimal(fechaHoyFor.substring(2, 4));
            BigDecimal mesHoy = new BigDecimal(fechaHoyFor.substring(4, 6));
            BigDecimal diaHoy = new BigDecimal(fechaHoyFor.substring(6, 8));
            
            sql.append(" OR  (l.fechaLogSiglo = ").append(sigloHoy).
                    append(" AND l.fechaLogAño= ").append(añoHoy).
                    append("  AND l.fechaLogMes = ").append(mesHoy).
                    append(" AND l.fechaLogDia = ").append(diaHoy).
                    append("   AND (l.hora BETWEEN 0 AND  :horaFin))");

               
            Query query = entityManager.createQuery(sql.toString());
            
            query.setParameter("sigloInicio", sigloIni);
            query.setParameter("añoInicio", añoIni);
            query.setParameter("mesInicio", mesIni);
            query.setParameter("diaInicio", diaIni);
            query.setParameter("horaInicio", horaIni);
            query.setParameter("horaFin", horaFin);

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<HhppRR> lst = (List<HhppRR>) query.getResultList();
            if(lst != null && !lst.isEmpty()){
                int registros = lst.size();
                resultCount =  new Long(registros);
            }
        } catch (PersistenceException e) {
            LOGGER.error("Error consultando auditoria de Hhpp en RR. ", e);
        }

        return resultCount;
    }
    
        
     public Date sumarDiaToFecha(Date fechaToSumar) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaToSumar);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }
}
