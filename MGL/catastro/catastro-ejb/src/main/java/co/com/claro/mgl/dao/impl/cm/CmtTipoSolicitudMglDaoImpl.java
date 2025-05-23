package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaSlaSolicitudDto;
import co.com.claro.mgl.dtos.FiltroConsultaSolicitudDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtTipoSolicitudMglDaoImpl extends GenericDaoImpl<CmtTipoSolicitudMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtTipoSolicitudMglDaoImpl.class);

    public List<CmtTipoSolicitudMgl> findAll() throws ApplicationException {
        List<CmtTipoSolicitudMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtTipoSolicitudMgl.findAll");
        resultList = (List<CmtTipoSolicitudMgl>) query.getResultList();
        return resultList;
    }

    public CmtTipoSolicitudMgl findTipoSolicitudBySolicitud(BigDecimal idSolicitud) throws ApplicationException {
        try {

            Query query = entityManager.createQuery("SELECT s FROM CmtTipoSolicitudMgl s, "
                    + " CmtSolicitudTipoSolicitudMgl st "
                    + " WHERE s.tipoSolicitudId = st.tipoSolicitudObj.tipoSolicitudId AND "
                    + " st.solicitudObj.idSolicitud =:idSolicitud AND "
                    + " s.estadoRegistro =:estado ");

            query.setParameter("idSolicitud", idSolicitud);
            query.setParameter("estado", 1);

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0           
            return (CmtTipoSolicitudMgl) query.getSingleResult();

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Obtiene el tipo de solicitud por Abreviatura
     *
     * @author Juan David Hernandez
     * @param abreviatura
     * @return CmtTipoSolicitudMgl
     * @throws ApplicationException
     */
    public CmtTipoSolicitudMgl findTipoSolicitudByAbreviatura(String abreviatura)
            throws ApplicationException {
        try {

            Query query = entityManager.createQuery("SELECT s FROM "
                    + "CmtTipoSolicitudMgl s "
                    + " WHERE s.abreviatura =:abreviatura AND "
                    + " s.estadoRegistro =:estado ");

            query.setParameter("abreviatura", abreviatura);
            query.setParameter("estado", 1);

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return (CmtTipoSolicitudMgl) query.getSingleResult();

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Obtiene listado de roles de gestion
     *
     * @author Juan David Hernandez
     * @param tipoAplicacion
     * @return Listado de roles de gestion
     * @throws ApplicationException
     */
    public List<CmtTipoSolicitudMgl> obtenerTipoSolicitudHhppList(String tipoAplicacion)
            throws ApplicationException {
        try {

            Query query = entityManager.createQuery("SELECT s FROM"
                    + " CmtTipoSolicitudMgl s "
                    + " WHERE s.aplicacion =:aplicacion  AND "
                    + " s.estadoRegistro =:estado ");

            query.setParameter("aplicacion", tipoAplicacion);           
            query.setParameter("estado", 1);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<CmtTipoSolicitudMgl> result = query.getResultList();
            return result;

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
     
     
  
  /**
   * Buscar reporte detallado para visitas técnicas
   * <p>
   * Buscar en la base de datos el listado de datos que corresponden según los
   * parámetros de entrada.
   *
   * @author becerraarmr
   * @param fechaInicial fecha de inicio
   * @param fechaFinalizacion fecha de finalizacion
   * @param tipoSolicitud tipo de solicitud
   * @param estado estado de la solicitud
   * @param finRegistros fin de registros a buscar
   * @param inicioRegistros inicio de registros a buscar
   *
   * @return Listado con la solicitud pedida
   */
  public List<Object[]> buscarReporteDetalle(String fechaInicial,
          String fechaFinalizacion, String tipoSolicitud, String estado,
          int finRegistros, int inicioRegistros) throws ApplicationException {

    List<Object[]> list = new ArrayList<Object[]>();

    String sql = "SELECT SQ.USLOGUEO,SQ.IDSOLICITUD,SQ.CUENTAMATRIZ,"
            + " SQ.CUENTASUSCRIPTOR, "
            + " SQ.CALLE, SQ.PLACA,SQ.TIPOVIVIENDA,SQ.COMPLEMENTO, "
            + " SQ.SOLICITANTE,SQ.CONTACTO,SQ.TELCONTACTO,SQ.REGIONAL, "
            + " SQ.CIUDAD,SQ.NODO,SQ.DESCRIPCION,SQ.FECHAINGRESO, "
            + " SQ.FECHA_MODIFICACION,SQ.FECHA_FINALIZACION,SQ.TIEMPO,"
            + " SQ.MOTIVO, "
            + " SQ.ESTADO,SQ.TIPOSOL,SQ.USUARIO,SQ.USUARIO_VERIFICADOR, "
            + " SQ.RGESTION,SQ.RESPUESTA,SQ.CORREGIR_HHPPP,SQ.CAMBIO_NODO, "
            + " SQ.NOMBRE_NUEVOEDIFICIO,SQ.NUEVO_PRODUCTO,"
            + " SQ.ESTRATO_ANTIGUO,SQ.ESTRATO_NUEVO, "
            + " SQ.BARRIO,SQ.HHP_ID,SQ.DIRECCION_NO_ESTANDARIZADA,"
            + " SQ.NOD_NOMBRE, "
            + " SQ.TIPO_UNIDAD,SQ.SDI_FORMATO_IGAC,SQ.TIPO_CONEXION,"
            + " SQ.TIPO_RED, "
            + " SQ.HHP_FECHA_CREACION,SQ.HHP_USUARIO_CREACION,"
            + " SQ.HHP_FECHA_MODIFICACION,SQ.HHP_USUARIO_MODIFICACION, "
            + " SQ.ESTADO_HHPP,SQ.HHP_ID_RR,SQ.HHP_CALLE,SQ.HHP_PLACA, "
            + " SQ.HHP_APART,SQ.HHP_COMUNIDAD,SQ.HHP_DIVISION,"
            + " SQ.HHP_ESTADO_UNIT, "
            + " SQ.HHP_VENDEDOR,SQ.HHP_CODIGO_POSTAL,SQ.HHP_EDIFICIO,"
            + " SQ.HHP_TIPO_ACOMET, "
            + " SQ.HHP_ULT_UBICACION,SQ.HHP_HEAD_END,SQ.HHP_TIPO,"
            + " SQ.HHP_TIPO_UNIDAD, "
            + " SQ.HHP_TIPO_CBL_ACOMETIDA,SQ.HHP_FECHA_AUDIT,SQ.NOTAADD "
            + " FROM ( "
            + "  SELECT ROWNUM ROW_NUM, "
            + "  SUBQ.USLOGUEO,SUBQ.IDSOLICITUD,SUBQ.CUENTAMATRIZ,"
            + " SUBQ.CUENTASUSCRIPTOR, "
            + " SUBQ.CALLE, SUBQ.PLACA,SUBQ.TIPOVIVIENDA,SUBQ.COMPLEMENTO, "
            + " SUBQ.SOLICITANTE,SUBQ.CONTACTO,SUBQ.TELCONTACTO,"
            + " SUBQ.REGIONAL, "
            + " SUBQ.CIUDAD,SUBQ.NODO,SUBQ.DESCRIPCION,SUBQ.FECHAINGRESO, "
            + " SUBQ.FECHA_MODIFICACION,SUBQ.FECHA_FINALIZACION,"
            + " SUBQ.TIEMPO,SUBQ.MOTIVO, "
            + " SUBQ.ESTADO,SUBQ.TIPOSOL,SUBQ.USUARIO,"
            + " SUBQ.USUARIO_VERIFICADOR, "
            + " SUBQ.RGESTION,SUBQ.RESPUESTA,SUBQ.CORREGIR_HHPPP,"
            + " SUBQ.CAMBIO_NODO, "
            + " SUBQ.NOMBRE_NUEVOEDIFICIO,SUBQ.NUEVO_PRODUCTO,"
            + " SUBQ.ESTRATO_ANTIGUO,SUBQ.ESTRATO_NUEVO, "
            + " SUBQ.BARRIO,SUBQ.HHP_ID,SUBQ.DIRECCION_NO_ESTANDARIZADA,"
            + " SUBQ.NOD_NOMBRE, "
            + " SUBQ.TIPO_UNIDAD,SUBQ.SDI_FORMATO_IGAC,SUBQ.TIPO_CONEXION,"
            + " SUBQ.TIPO_RED, "
            + " SUBQ.HHP_FECHA_CREACION,SUBQ.HHP_USUARIO_CREACION,"
            + " SUBQ.HHP_FECHA_MODIFICACION,SUBQ.HHP_USUARIO_MODIFICACION, "
            + " SUBQ.ESTADO_HHPP,SUBQ.HHP_ID_RR,SUBQ.HHP_CALLE,SUBQ.HHP_PLACA, "
            + " SUBQ.HHP_APART,SUBQ.HHP_COMUNIDAD,SUBQ.HHP_DIVISION,"
            + " SUBQ.HHP_ESTADO_UNIT, "
            + " SUBQ.HHP_VENDEDOR,SUBQ.HHP_CODIGO_POSTAL,SUBQ.HHP_EDIFICIO,"
            + " SUBQ.HHP_TIPO_ACOMET, "
            + " SUBQ.HHP_ULT_UBICACION,SUBQ.HHP_HEAD_END,SUBQ.HHP_TIPO,"
            + " SUBQ.HHP_TIPO_UNIDAD, "
            + " SUBQ.HHP_TIPO_CBL_ACOMETIDA,SUBQ.HHP_FECHA_AUDIT,SUBQ.NOTAADD "
            + "  FROM  "
            + "    (SELECT US.USUARIO USLOGUEO,  V.IDSOLICITUD IDSOLICITUD,  "
            + "    V.CUENTAMATRIZ CUENTAMATRIZ,V.CUENTASUSCRIPTOR CUENTASUSCRIPTOR,"
            + "    V.DIRECCION CALLE, V.NUMPUERTA PLACA,  "
            + "    V.TIPOVIVIENDA TIPOVIVIENDA, V.COMPLEMENTO COMPLEMENTO, "
            + "    V.SOLICITANTE SOLICITANTE, V.CONTACTO CONTACTO, "
            + "    V.TELCONTACTO TELCONTACTO, RR.NOMBRE REGIONAL, "
            + "    RC.NOMBRE CIUDAD, V.NODO NODO, "
            + "    VT.DESCRIPCION DESCRIPCION, "
            + "    TO_CHAR(V.FECHAINGRESO, 'DD-MM-YYYY HH:MI AM')FECHAINGRESO, "
            + "    TO_CHAR(V.FECHAMODIFICACION,'DD-MM-YYYY HH:MI AM') FECHA_MODIFICACION,   "
            + "    TO_CHAR(V.FECHACANCELACION, 'DD-MM-YYYY HH:MI AM') FECHA_FINALIZACION, "
            + "    ROUND((TO_CHAR((V.FECHACANCELACION) - V.FECHAINGRESO ))*1440) TIEMPO, "
            + "    V.RESPUESTA RESPUESTA, V.MOTIVO MOTIVO, V.ESTADO ESTADO,"
            + "    V.TIPOSOL TIPOSOL, "
            + "    V.USUARIO USUARIO, V.USUARIO_VERIFICADOR USUARIO_VERIFICADOR, "
            + "    V.RGESTION RGESTION, "
            + "    V.CORREGIR_HHPPP CORREGIR_HHPPP, V.CAMBIO_NODO CAMBIO_NODO, "
            + "    V.NOMBRE_NUEVOEDIFICIO NOMBRE_NUEVOEDIFICIO, "
            + "    V.NUEVO_PRODUCTO NUEVO_PRODUCTO, "
            + "    V.ESTRATO_ANTIGUO ESTRATO_ANTIGUO, "
            + "    V.ESTRATO_NUEVO ESTRATO_NUEVO, V.BARRIO BARRIO, "
            + "    H.HHP_ID HHP_ID, "
            + "    D.DIR_NOSTANDAR DIRECCION_NO_ESTANDARIZADA, "
            + "    ND.NOD_NOMBRE NOD_NOMBRE, TH.THH_VALOR TIPO_UNIDAD, "
            + "    SD.SDI_FORMATO_IGAC SDI_FORMATO_IGAC, "
            + "    TC.THC_NOMBRE TIPO_CONEXION, TR.THR_NOMBRE TIPO_RED,"
            + "    H.HHP_FECHA_CREACION HHP_FECHA_CREACION, "
            + "    H.HHP_USUARIO_CREACION HHP_USUARIO_CREACION, "
            + "    H.HHP_FECHA_MODIFICACION HHP_FECHA_MODIFICACION, "
            + "    H.HHP_USUARIO_MODIFICACION HHP_USUARIO_MODIFICACION, "
            + "    EH.EHH_NOMBRE ESTADO_HHPP, H.HHP_ID_RR HHP_ID_RR,  "
            + "    H.HHP_CALLE HHP_CALLE, H.HHP_PLACA HHP_PLACA, "
            + "    H.HHP_APART HHP_APART, "
            + "    H.HHP_COMUNIDAD HHP_COMUNIDAD, H.HHP_DIVISION HHP_DIVISION,"
            + "    H.HHP_ESTADO_UNIT HHP_ESTADO_UNIT, H.HHP_VENDEDOR HHP_VENDEDOR, "
            + "    H.HHP_CODIGO_POSTAL HHP_CODIGO_POSTAL,  H.HHP_EDIFICIO HHP_EDIFICIO, "
            + "    H.HHP_TIPO_ACOMET HHP_TIPO_ACOMET,  H.HHP_ULT_UBICACION HHP_ULT_UBICACION, "
            + "    H.HHP_HEAD_END HHP_HEAD_END, H.HHP_TIPO HHP_TIPO,  "
            + "    H.HHP_TIPO_UNIDAD HHP_TIPO_UNIDAD, H.HHP_TIPO_CBL_ACOMETIDA HHP_TIPO_CBL_ACOMETIDA, "
            + "    H.HHP_FECHA_AUDIT HHP_FECHA_AUDIT,   "
            + "    ( CASE WHEN V.HHP_ID IS NOT NULL THEN "
            + "        (SELECT listagg (Z.NHH_NOTA, ' | ') "
            + "      WITHIN GROUP (ORDER BY Z.HHP_ID,Z.IDSOLICITUD DESC)  "
            + "            FROM NOTAS_ADICIONALES_HHPP Z  "
            + "            WHERE (Z.HHP_ID =V.HHP_ID OR Z.IDSOLICITUD=V.IDSOLICITUD) AND ROWNUM < 5 )   "
            + "      ELSE (SELECT Z.NHH_NOTA  "
            + "            FROM NOTAS_ADICIONALES_HHPP Z  "
            + "            WHERE Z.IDSOLICITUD=V.IDSOLICITUD AND ROWNUM = 1 )  END ) AS NOTAADD  "
            + "    FROM TM_MAS_VT_SOLICITUDES_VT V   "
            + "    INNER JOIN USUARIOS US ON US.ID_USUARIO=V.ID_SOLICITANTE  "
            + "    INNER JOIN RR_REGIONALES RR ON RR.CODIGO = V.REGIONAL   "
            + "    INNER JOIN TM_MAS_VT_TIPOSOLICITUDES VT ON VT.ID_SOLICITUD_VT = V.TIPO   "
            + "    INNER JOIN RR_CIUDADES RC ON RC.CODIGO = V.CIUDAD   "
            + "    lEFT JOIN HHPP H ON H.HHP_ID=V.HHP_ID   "
            + "    lEFT JOIN DIRECCION D ON H.DIR_ID=D.DIR_ID   "
            + "    lEFT JOIN NODO ND ON H.NOD_ID=ND.NOD_ID   "
            + "    lEFT JOIN TIPO_HHPP TH ON H.THH_ID=TH.THH_ID   "
            + "    LEFT JOIN SUB_DIRECCION SD ON H.SDI_ID=SD.DIR_ID   "
            + "    lEFT JOIN TIPO_HHPP_CONEXION TC ON H.THC_ID=TC.THC_ID   "
            + "    lEFT JOIN TIPO_HHPP_RED TR ON H.THR_ID=TR.THR_ID   "
            + "    lEFT JOIN ESTADO_HHPP EH ON H.EHH_ID=EH.EHH_ID   "
            + "    WHERE V.FECHAINGRESO BETWEEN TO_DATE( ? , 'DD-MM-YYYY') "
            + "    AND TO_DATE( ? , 'DD-MM-YYYY')  "
            + "    AND V.TIPO =  ?   ";

    if (estado != null
            && !estado.trim().isEmpty()) {
      sql += " AND V.ESTADO =  ? ";
    }
    sql += " ORDER BY V.FECHAINGRESO  "
            + " ) SUBQ"
            + "     WHERE ROWNUM <=?) SQ "
            + " WHERE ROW_NUM > ?";
    try {

      if (!entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().begin();
      }
      entityManager.flush();
      Query query = entityManager.createNativeQuery(sql);
      int item = 1;
      query.setParameter(item++, fechaInicial);
      query.setParameter(item++, fechaFinalizacion);
      query.setParameter(item++, tipoSolicitud);
      if (estado != null && !estado.trim().isEmpty()) {
        query.setParameter(item++, estado);
      }
      query.setParameter(item++, finRegistros);
      query.setParameter(item++, inicioRegistros);

      list = query.getResultList();
      entityManager.getTransaction().commit();

    } catch (Exception e) {
        String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
        LOGGER.error(msg);
        throw new ApplicationException(msg, e);
    }
    return list;

  }
  
  /**
   * Contar número de registros.
   * 
   * Cuenta el número de registros para realizar el reporte detallado.
   * @author becerraarmr
   * @param fechaInicial fecha de inicial
   * @param fechaFinalizacion fecha de finalización
   * @param tipoSolicitud tipo de solicitud realizada
   * @param estado estado de la solitudi
   * @return un entero con la cantidad encontrada.
   */
  public int numRegistroReporteDetalle(String fechaInicial,
          String fechaFinalizacion, String tipoSolicitud, String estado) throws ApplicationException {
    int i = 0;//inicia con número encontrados vacios
    try {
      String sql = "SELECT COUNT(1) NUMREGISTROS "
              + " FROM TM_MAS_VT_SOLICITUDES_VT V "
              + " INNER JOIN RR_REGIONALES RR ON RR.CODIGO = V.REGIONAL "
              + " INNER JOIN TM_MAS_VT_TIPOSOLICITUDES VT ON VT.ID_SOLICITUD_VT = V.TIPO "
              + " INNER JOIN RR_CIUDADES RC ON RC.CODIGO = V.CIUDAD "
              + " lEFT JOIN HHPP H ON H.HHP_ID=V.HHP_ID "
              + " lEFT JOIN DIRECCION D ON H.DIR_ID=D.DIR_ID "
              + " lEFT JOIN NODO ND ON H.NOD_ID=ND.NOD_ID "
              + " lEFT JOIN TIPO_HHPP TH ON H.THH_ID=TH.THH_ID "
              + " LEFT JOIN SUB_DIRECCION SD ON H.SDI_ID=SD.DIR_ID "
              + " lEFT JOIN TIPO_HHPP_CONEXION TC ON H.THC_ID=TC.THC_ID "
              + " lEFT JOIN TIPO_HHPP_RED TR ON H.THR_ID=TR.THR_ID "
              + " lEFT JOIN ESTADO_HHPP EH ON H.EHH_ID=EH.EHH_ID "
              + " WHERE FECHAINGRESO BETWEEN TO_DATE( ? , 'DD-MM-YYYY') "
              + " AND TO_DATE( ? , 'DD-MM-YYYY') "
              + " AND TIPO =  ?  ";

      if (estado != null && !estado.trim().isEmpty()) {
        sql += " AND V.ESTADO = ? ";
      }
      sql += " ORDER BY FECHAINGRESO ";
      if (!entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().begin();
      }
      entityManager.flush();
      Query query = entityManager.createNativeQuery(sql);
      query.setParameter(1, fechaInicial);
      query.setParameter(2, fechaFinalizacion);
      query.setParameter(3, tipoSolicitud);
      if (estado != null && !estado.trim().isEmpty()) {
        query.setParameter(4, estado);
      }
      BigDecimal cant = (BigDecimal) query.getSingleResult();
      if (cant != null) {
        i = cant.intValue();
      }
      entityManager.getTransaction().commit();

    } catch (Exception e) {
      String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
        LOGGER.error(msg);
        throw new ApplicationException(msg, e);
    }
    return i;
  }
  
  /**
   * Buscar el tipo de solicitudes.
   * @author becerraarmr
   * @return el listado de arreglos con la data de solicitudes.
   */
  public List<Object[]> findTipoSolicitudes() throws ApplicationException {
    List<Object[]> list = new ArrayList<Object[]>();
    try {

      String sql = "SELECT c.abreviatura,c.nombreTipo "
              + " FROM CmtTipoSolicitudMgl c where c.aplicacion=:aplicacion "
              + " ORDER bY c.nombreTipo ASC";
      if (!entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().begin();
      }
      entityManager.flush();
      Query query = entityManager.createQuery(sql);
      query.setParameter("aplicacion", "VT");
      list = query.getResultList();
      entityManager.getTransaction().commit();

    } catch (Exception e) {
        String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
        LOGGER.error(msg);
        throw new ApplicationException(msg, e);
    }
    return list;
  }

    public FiltroConsultaSlaSolicitudDto findTablasSlaTipoSolicitud(FiltroConsultaSolicitudDto filtro, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        FiltroConsultaSlaSolicitudDto filtroConsultaSlaSolicitudDto = new FiltroConsultaSlaSolicitudDto();
        if (contar) {
            filtroConsultaSlaSolicitudDto.setNumRegistros(findListCountTipoSolicitudData(filtro));
        } else {
            filtroConsultaSlaSolicitudDto.setListaCmtTipoSolicitud(findListTipoSolicitudOtData(filtro, firstResult, maxResults));
        }
        return filtroConsultaSlaSolicitudDto;
    }

    public long findListCountTipoSolicitudData(FiltroConsultaSolicitudDto filtro) throws ApplicationException {
        try {

            String queryStr = "SELECT COUNT(DISTINCT c) FROM CmtTipoSolicitudMgl c WHERE 1=1";

            if (filtro.getCodigoSeleccionado() != null
                    && !filtro.getCodigoSeleccionado().equals(BigDecimal.ZERO)) {
                queryStr += " AND c.tipoSolicitudId = :tipoSolicitudId ";
            }

            if (filtro.getNombreSeleccionado() != null && !filtro.getNombreSeleccionado().isEmpty()) {
                queryStr += " AND UPPER(c.nombreTipo) LIKE :nombreTipo ";
            }

            if (filtro.getEstadoSeleccionado() != null && !filtro.getEstadoSeleccionado().isEmpty()) {
                queryStr += " AND c.estadoRegistro = :estadoRegistro  ";
            }

            if (filtro.getCreaRolSeleccionado() != null && !filtro.getCreaRolSeleccionado().isEmpty()) {
                queryStr += " AND UPPER(c.creacionRol) LIKE :creacionRol ";
            }

            if (filtro.getGestionRolSeleccionado() != null && !filtro.getGestionRolSeleccionado().isEmpty()) {
                queryStr += " AND UPPER(c.gestionRol) LIKE :gestionRol ";
            }

            if (filtro.getSlaSeleccionado() > 0) {
                queryStr += " AND c.ans = :ans ";
            }

            if (filtro.getSlaAvisoSeleccionado() > 0) {
                queryStr += " AND c.ansAviso = :ansAviso ";
            }

            Query query = entityManager.createQuery(queryStr);

            if (filtro.getCodigoSeleccionado() != null
                    && !filtro.getCodigoSeleccionado().equals(BigDecimal.ZERO)) {
                query.setParameter("tipoSolicitudId", filtro.getCodigoSeleccionado());
            }

            if (filtro.getNombreSeleccionado() != null && !filtro.getNombreSeleccionado().isEmpty()) {
                query.setParameter("nombreTipo", "%" + filtro.getNombreSeleccionado().trim().toUpperCase() + "%");
            }

            if (filtro.getEstadoSeleccionado() != null && !filtro.getEstadoSeleccionado().isEmpty()) {
                String est = filtro.getEstadoSeleccionado().trim().toUpperCase();
                int e;
                if (est.equalsIgnoreCase("ACTIVADO")) {
                    e = 1;
                } else if (est.equalsIgnoreCase("DESACTIVADO")) {
                    e = 0;
                } else {
                    e = 3;
                }
                query.setParameter("estadoRegistro", e);
            }

            if (filtro.getCreaRolSeleccionado() != null && !filtro.getCreaRolSeleccionado().isEmpty()) {
                query.setParameter("creacionRol", "%" + filtro.getCreaRolSeleccionado().trim().toUpperCase() + "%");
            }

            if (filtro.getGestionRolSeleccionado() != null && !filtro.getGestionRolSeleccionado().isEmpty()) {
                query.setParameter("gestionRol", "%" + filtro.getGestionRolSeleccionado().trim().toUpperCase() + "%");
            }

            if (filtro.getSlaSeleccionado() > 0) {
                query.setParameter("ans", filtro.getSlaSeleccionado());
            }

            if (filtro.getSlaAvisoSeleccionado() > 0) {
                query.setParameter("ansAviso", filtro.getSlaAvisoSeleccionado());
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return (Long) query.getSingleResult();
        } catch (NoResultException nre) {
            LOGGER.error("No se encontraron resultados para la consulta: ", nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "los datos para la Cuenta Matriz. Intente más tarde.");
        }
    }

    public List<CmtTipoSolicitudMgl> findListTipoSolicitudOtData(FiltroConsultaSolicitudDto filtro, int firstResult, int maxResults) throws ApplicationException {
        try {

            String queryStr = "SELECT DISTINCT c FROM CmtTipoSolicitudMgl c WHERE 1=1";

            if (filtro.getCodigoSeleccionado() != null
                    && !filtro.getCodigoSeleccionado().equals(BigDecimal.ZERO)) {
                queryStr += " AND c.tipoSolicitudId = :tipoSolicitudId ";
            }

            if (filtro.getNombreSeleccionado() != null && !filtro.getNombreSeleccionado().isEmpty()) {
                queryStr += " AND UPPER(c.nombreTipo) LIKE :nombreTipo ";
            }

            if (filtro.getEstadoSeleccionado() != null && !filtro.getEstadoSeleccionado().isEmpty()) {
                queryStr += " AND c.estadoRegistro = :estadoRegistro  ";
            }

            if (filtro.getCreaRolSeleccionado() != null && !filtro.getCreaRolSeleccionado().isEmpty()) {
                queryStr += " AND UPPER(c.creacionRol) LIKE :creacionRol ";
            }

            if (filtro.getGestionRolSeleccionado() != null && !filtro.getGestionRolSeleccionado().isEmpty()) {
                queryStr += " AND UPPER(c.gestionRol) LIKE :gestionRol ";
            }

            if (filtro.getSlaSeleccionado() > 0) {
                queryStr += " AND c.ans = :ans ";
            }

            if (filtro.getSlaAvisoSeleccionado() > 0) {
                queryStr += " AND c.ansAviso = :ansAviso ";
            }

            Query query = entityManager.createQuery(queryStr);
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);

            if (filtro.getCodigoSeleccionado() != null
                    && !filtro.getCodigoSeleccionado().equals(BigDecimal.ZERO)) {
                query.setParameter("tipoSolicitudId", filtro.getCodigoSeleccionado());
            }

            if (filtro.getNombreSeleccionado() != null && !filtro.getNombreSeleccionado().isEmpty()) {
                query.setParameter("nombreTipo", "%" + filtro.getNombreSeleccionado().trim().toUpperCase() + "%");
            }

            if (filtro.getEstadoSeleccionado() != null && !filtro.getEstadoSeleccionado().isEmpty()) {
                String est = filtro.getEstadoSeleccionado().trim().toUpperCase();
                int e;
                if (est.equalsIgnoreCase("ACTIVADO")) {
                    e = 1;
                } else if (est.equalsIgnoreCase("DESACTIVADO")) {
                    e = 0;
                } else {
                    e = 3;
                }
                query.setParameter("estadoRegistro", e);
            }

            if (filtro.getCreaRolSeleccionado() != null && !filtro.getCreaRolSeleccionado().isEmpty()) {
                query.setParameter("creacionRol", "%" + filtro.getCreaRolSeleccionado().trim().toUpperCase() + "%");
            }

            if (filtro.getGestionRolSeleccionado() != null && !filtro.getGestionRolSeleccionado().isEmpty()) {
                query.setParameter("gestionRol", "%" + filtro.getGestionRolSeleccionado().trim().toUpperCase() + "%");
            }

            if (filtro.getSlaSeleccionado() > 0) {
                query.setParameter("ans", filtro.getSlaSeleccionado());
            }

            if (filtro.getSlaAvisoSeleccionado() > 0) {
                query.setParameter("ansAviso", filtro.getSlaAvisoSeleccionado());
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return query.getResultList();
        } catch (NoResultException nre) {
            LOGGER.error("No se encontraron resultados para la consulta: ", nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "los datos para la Cuenta Matriz. Intente más tarde.");
        }

    }

    /**
     * Consulta de tipo de solicitud de eliminaci&oacute;n
     * 
     * @return {@link CmtTipoSolicitudMgl} Tipo de solicitud de eliminaci&oacute;n
     * @throws ApplicationException Excepci&oacute;n lanzada por la consulta
     */
    public CmtTipoSolicitudMgl obtenerSolicitudEliminacion() throws ApplicationException {
        return obtenerTipoSolicitud("ELIMINACION_CM");
    }

    /**
     * Consulta de tipo de solicitud de escalamiento de acometidas
     *
     * @return {@link CmtTipoSolicitudMgl} Tipo de solicitud de escalamiento
     * @throws ApplicationException Excepci&oacute;n lanzada por la consulta
     */
    public CmtTipoSolicitudMgl obtenerSolicitudEscalamiento() throws ApplicationException {
        return obtenerTipoSolicitud("ESCALADO ACOMETIDAS");
    }

    /**
     * M&eacute;todo privado para consultar un tipo de solicitud por nombre
     * 
     * @param TIPO {@link String} nombre del tipo de la solicitud
     * @return {@link CmtTipoSolicitudMgl} Tipo de solicitud encontrada
     * @throws ApplicationException Excepcion lanzada en la consulta
     */
    private CmtTipoSolicitudMgl obtenerTipoSolicitud(String TIPO) throws ApplicationException {
        try {
            String consulta = "SELECT t FROM CmtTipoSolicitudMgl t WHERE t.nombreTipo = :nombre";
            return (CmtTipoSolicitudMgl) entityManager.createQuery(consulta)
                    .setParameter("nombre", TIPO)
                    .getSingleResult();
        } catch (PersistenceException e) {
            LOGGER.error("Error consultando el tipo ELIMINACION CM", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
     /**
     * Metodo privado para consultar las tipo de solicitud de CM
     * Autor:Victor Manuel Bocanegra
     * @return List<CmtTipoSolicitudMgl>
     * @throws ApplicationException Excepcion lanzada en la consulta
     */
     public List<CmtTipoSolicitudMgl> findByTipoApplication() throws ApplicationException {
        
        List<CmtTipoSolicitudMgl> resultList; 
        Query query = entityManager.createNamedQuery("CmtTipoSolicitudMgl.findByTipoApplication");
        query.setParameter("aplicacion", Constant.TIPO_VALIDACION_DIR_CM);      
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtTipoSolicitudMgl>) query.getResultList();
        return resultList;
    }
     
      /**
     * Obtiene el tipo de solicitud por basica id tipo solicitud
     *
     * @author Juan David Hernandez
     * @param basicaIdTipoSolicitud
     * @return CmtTipoSolicitudMgl
     * @throws ApplicationException
     */
    public CmtTipoSolicitudMgl findTipoSolicitudByBasicaIdTipoSolicitud(BigDecimal basicaIdTipoSolicitud)
            throws ApplicationException {
        try {

            Query query = entityManager.createQuery("SELECT s FROM "
                    + "CmtTipoSolicitudMgl s "
                    + " WHERE s.tipoSolicitudBasicaId.basicaId =:basicaIdTipoSolicitud AND "
                    + " s.estadoRegistro =:estado ");

            query.setParameter("basicaIdTipoSolicitud", basicaIdTipoSolicitud);
            query.setParameter("estado", 1);

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return (CmtTipoSolicitudMgl) query.getSingleResult();

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    
    /**
     * Obtiene el tipo de solicitud por Abreviatura
     *
     * @author Juan David Hernandez
     * @param tipoSolicitudBasicaId
     * @return CmtTipoSolicitudMgl
     * @throws ApplicationException
     */
    public CmtTipoSolicitudMgl findTipoSolicitudByTipoSolicitudBasicaId(CmtBasicaMgl tipoSolicitudBasicaId)
            throws ApplicationException {
        try {

            Query query = entityManager.createQuery("SELECT s FROM "
                    + "CmtTipoSolicitudMgl s "
                    + " WHERE s.tipoSolicitudBasicaId.basicaId =:basicaId AND "
                    + " s.estadoRegistro =:estado ");

            query.setParameter("basicaId", tipoSolicitudBasicaId.getBasicaId());
            query.setParameter("estado", 1);

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return (CmtTipoSolicitudMgl) query.getSingleResult();

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}