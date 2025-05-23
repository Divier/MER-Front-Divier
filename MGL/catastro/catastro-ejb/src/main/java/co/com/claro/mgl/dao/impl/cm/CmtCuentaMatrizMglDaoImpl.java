package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dao.impl.HhppMglDaoImpl;
import co.com.claro.mgl.dtos.CuentaMatrizCompComercialDto;
import co.com.claro.mgl.dtos.FiltroConsultaCuentaMatriz;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtCuentaMatrizMglDaoImpl extends GenericDaoImpl<CmtCuentaMatrizMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtCuentaMatrizMglDaoImpl.class);
    private static final String MSG_SIN_RESULTADOS = "No se encontraron resultados para la consulta";

    public List<CmtCuentaMatrizMgl> findAll() throws ApplicationException {
        List<CmtCuentaMatrizMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtCuentaMatrizMgl.findAll");
        resultList = (List<CmtCuentaMatrizMgl>) query.getResultList();
        return resultList;
    }
       public CmtCuentaMatrizMgl findCMById(BigDecimal cmId) throws ApplicationException {
        CmtCuentaMatrizMgl cm;
        Query query = entityManager.createNamedQuery("CmtCuentaMatrizMgl.findCMById");
          query.setParameter("cuentaMatrizId", cmId);
        cm = (CmtCuentaMatrizMgl) query.getSingleResult();
        return cm;
    }

    public List<CmtCuentaMatrizMgl> findLastTenRecords() throws ApplicationException {
        List<CmtCuentaMatrizMgl> resultList;
        Query query = entityManager
                .createQuery("SELECT c FROM CmtCuentaMatrizMgl c WHERE c.estadoRegistro=1 ORDER BY c.fechaCreacion DESC")
                .setMaxResults(10);
        resultList = (List<CmtCuentaMatrizMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtCuentaMatrizMgl> findCmList(Map<String, Object> params) {
        String consulta = "select c.* "
                + "from cmt_cuenta_matriz c "
                + "inner join direccion d  on c.DIRECCION = d.DIR_ID "
                + "inner join ubicacion u  on d.UBI_ID  = u.UBI_ID "
                + "inner join geografico_politico gp  on u.GPO_ID = gp.GPO_ID ";

        if (params != null && !params.isEmpty()) {

            if (params.containsKey("barrio")) {
                consulta = consulta.concat("inner join geografico g on u.geo_id = g.geo_id ");
            }
            consulta = consulta.concat(" where 1=1 ");
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String string = entry.getKey();
                Object object = entry.getValue();
                if (string.equals("numeroCuenta")) {
                    consulta = consulta.concat(" AND (c.NUMEROCUENTA = ").concat((String) object.toString()).concat(") ");
                }
                if (string.equals("nombreCuenta")) {
                    consulta = consulta.concat(" AND (c.NOMBRECUENTA = '").concat((String) object.toString()).concat("') ");
                }
                if (string.equals("ot")) {
                    consulta = consulta.concat(" AND (c.OT = ").concat((String) object.toString()).concat(") ");
                }
                if (string.equals("departamento") && !params.containsKey("ciudad")) {
                    consulta = consulta.concat(" AND (gp.GEO_GPO_ID = ").concat((String) object.toString()).concat(") ");
                }
                if (string.equals("ciudad")) {
                    consulta = consulta.concat(" AND (gp.GPO_ID = ").concat((String) object.toString()).concat(") ");
                }

                if (string.equals("barrio")) {
                    consulta = consulta.concat(" AND (g.GEO_NOMBRE like '").concat((String) object.toString()).concat("%') ");
                }

                if (string.equals("direccion")) {

                    consulta = consulta.concat(" AND (d.DIR_formato_igac like '").concat((String) object.toString()).concat("%') ");
                }
            }
        }
        consulta = consulta.concat(" ORDER BY c.numeroCuenta ");
        Query c = entityManager.createNativeQuery(consulta, CmtCuentaMatrizMgl.class);
        List<CmtCuentaMatrizMgl> result = c.getResultList();
        return result;
    }

    /**
     * Realizes a database query with params to obtain a list of CmtCuentaMatriz
     * entities
     *
     * @param params Params to use within query
     * @param maxResults
     * @param firstResult
     * @return A list of search result to CmtCuentaMatriz entity
     * @throws ApplicationException
     */
    public FiltroConsultaCuentaMatriz findCuentasMatricesSearch(HashMap<String, Object> params, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        FiltroConsultaCuentaMatriz filtroConsultaCuentaMatriz = new FiltroConsultaCuentaMatriz();
        if (contar) {
            filtroConsultaCuentaMatriz.setNumRegistros(findCuentasMatricesSearchContar(params));
        } else {
            filtroConsultaCuentaMatriz.setListaCm(findCuentasMatricesSearchDatos(params, firstResult, maxResults));
        }
        return filtroConsultaCuentaMatriz;
    }

    public List<CmtCuentaMatrizMgl> findCuentasMatricesSearchDatos(HashMap<String, Object> params, int firstResult, int maxResults) throws ApplicationException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT DISTINCT c FROM CmtCuentaMatrizMgl c ");

            generarSelectConsultaCM(sql, params, false);
            Query q = entityManager.createQuery(sql.toString());
            if (!params.isEmpty()) {
                agregarParametros(q, params, false);
            }

            q.setFirstResult(firstResult);
            q.setMaxResults(maxResults);
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return q.getResultList();
        } catch (NoResultException nre) {
            LOGGER.error(MSG_SIN_RESULTADOS, nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "los datos para la Cuenta Matriz. Intente más tarde.");
        }
    }

    public long findCuentasMatricesSearchContar(HashMap<String, Object> params) throws ApplicationException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT COUNT(DISTINCT c) FROM CmtCuentaMatrizMgl c ");

            generarSelectConsultaCM(sql, params, true);

            Query q = entityManager.createQuery(sql.toString());
            if (!params.isEmpty()) {
                agregarParametros(q, params, true);
            }

            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return (Long) q.getSingleResult();
        } catch (NoResultException nre) {
            LOGGER.error(MSG_SIN_RESULTADOS, nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (Exception e) {
             String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "los datos para la Cuenta Matriz. Intente más tarde.");
        }
    }

    /**
     * M&eacute;todo para generar el script de b&uacute;squeda en la consulta de
     * cuentas matrices.
     *
     * @param sql {@link StringBuilder} objeto para generar el String de la
     * consulta.
     * @param params Mapa {@link HashMap}&lt;{@link String}, {@link Object}>
     * par&aacute;metros para realizar la consulta.
     * @param esConteo Atributo para validar si es consulta de registros o
     * conteo.
     */
    private void generarSelectConsultaCM(StringBuilder sql, HashMap<String, Object> params, boolean esConteo) {

        BigDecimal ciudad = (BigDecimal) params.get("centroPoblado");

        //numero de cuenta RR 
        if (params.get("numeroCuenta") != null && !params.get("numeroCuenta").toString().isEmpty()) {
            sql.append("  WHERE  c.numeroCuenta = :numeroCuenta AND c.estadoRegistro=1 ");
        } else {
            //numero de cuenta RR y cp
            if (params.get("numeroCuentaMGL") != null && !params.get("numeroCuentaMGL").toString().isEmpty()) {
                sql.append("  WHERE c.cuentaMatrizId = :numeroCuentaMGL AND c.estadoRegistro=1 ");
            } else {
                if (params.get("idOt") != null && !params.get("idOt").toString().isEmpty()) {
                    sql.append("  JOIN c.ordenesTrabajoList o ");
                    sql.append(" WHERE (:idOt IS NULL OR o.idOt = :idOt) and c.estadoRegistro=1 ");
                } else {
                    //suscriptor
                    if (params.get("cuentaSucriptor") != null && !params.get("cuentaSucriptor").toString().isEmpty()) {
                        sql.append("  JOIN c.listCmtSubEdificioMgl s ");
                        sql.append("  JOIN s.listHhpp h ");
                        sql.append("  WHERE (:cuentaSucriptor IS NULL OR UPPER(h.suscriptor) LIKE :cuentaSucriptor) ");
                    } else {
                        //nombre de la CM
                        if (params.get("nombreCuenta") != null && !params.get("nombreCuenta").toString().isEmpty()) {
                            sql.append(" WHERE c.centroPoblado.gpoId = :centroPoblado AND c.estadoRegistro=1 ");
                            sql.append(" AND (:nombreCuenta IS NULL OR UPPER(c.nombreCuenta) LIKE :nombreCuenta) ");
                        } else {
                            if (ciudad != null && params.get("telefono") != null && !params.get("telefono").toString().isEmpty()) {
                                sql.append(" JOIN c.listCmtSubEdificioMgl s ");
                                sql.append("  WHERE c.centroPoblado.gpoId = :centroPoblado AND c.estadoRegistro=1 ");
                                sql.append(" AND  (:telefono IS NULL OR UPPER(s.telefonoPorteria) LIKE :telefono) ");
                            } else {
                                //administracion o administrador 
                                if (ciudad != null && (params.get("administrador") != null && !params.get("administrador").toString().isEmpty())) {
                                    sql.append("  JOIN c.listCmtSubEdificioMgl s ");
                                    sql.append("  JOIN s.companiaAdministracionObj co ");
                                    sql.append("  WHERE c.centroPoblado.gpoId = :centroPoblado AND c.estadoRegistro=1 ");
                                    sql.append(" AND (:administrador IS NULL OR UPPER(s.administrador) LIKE :administrador) ");
                                } else {
                                    //administracion o administrador 
                                    if (ciudad != null && (params.get("administracion") != null && !params.get("administracion").toString().isEmpty())) {
                                        sql.append("  JOIN c.listCmtSubEdificioMgl s ");
                                        sql.append("  JOIN s.companiaAdministracionObj co ");
                                        sql.append("  WHERE c.centroPoblado.gpoId = :centroPoblado AND c.estadoRegistro=1 ");
                                        sql.append(" AND (:administracion IS NULL OR UPPER(co.nombreContacto) LIKE :administracion) ");
                                    } else {
                                        //centro poblado
                                        if (ciudad != null) {
                                            sql.append(" WHERE c.centroPoblado.gpoId = :centroPoblado AND c.estadoRegistro=1 ");
                                        }
                                    }
                                }
                            }

                        }

                    }
                }
            }
        }

    }

    /**
     * M&eacute;todo para adicionar los par&aacute;metros de b&uacute;squeda en
     * la consulta de cuentas matrices.
     *
     * @param q {@link Query} objeto de jpa para realizar la consulta.
     * @param params Mapa {@link HashMap}&lt;{@link String}, {@link Object}>
     * par&aacute;metros para realizar la consulta.
     * @param esConteo Atributo para validar si es consulta de registros o
     * conteo.
     */
    private void agregarParametros(Query q, HashMap<String, Object> params, boolean esConteo) {
        //FIXME Adjust the WHERE sentence settings with the HashMap values
        
        //espinosadiea: modificacion 09 de junio 2018 agregar campo numero de cuenta de mgl 
        if (params.get("numeroCuentaMGL") != null && !params.get("numeroCuentaMGL").toString().isEmpty()) {
            q.setParameter("numeroCuentaMGL",
                    (null == params.get("numeroCuentaMGL")
                    || params.get("numeroCuentaMGL").toString().isEmpty()) ? null
                    : new BigDecimal(params.get("numeroCuentaMGL").toString()));
            return;
        }        
        
        if (params.get("numeroCuenta") != null && !params.get("numeroCuenta").toString().isEmpty()) {
            q.setParameter("numeroCuenta",
                    (null == params.get("numeroCuenta")
                    || params.get("numeroCuenta").toString().isEmpty()) ? null
                    : new BigDecimal(params.get("numeroCuenta").toString()));
            return;
        }    

        if (params.get("nombreCuenta") != null && !params.get("nombreCuenta").toString().isEmpty()) {
            q.setParameter("nombreCuenta",
                    (null == params.get("nombreCuenta")
                    || params.get("nombreCuenta").toString().isEmpty()) ? null
                    : "%" + params.get("nombreCuenta").toString().toUpperCase() + "%");
        }
        
        if (params.get("centroPoblado") != null && !params.get("centroPoblado").toString().isEmpty()) {
            q.setParameter("centroPoblado",
                    (null == params.get("centroPoblado")
                    || params.get("centroPoblado").toString().isEmpty()) ? null
                    : new BigDecimal(params.get("centroPoblado").toString()));
        }    
    

        if (params.get("idOt") != null && !params.get("idOt").toString().isEmpty()) {
            q.setParameter("idOt", (null == params.get("idOt")
                    || params.get("idOt").toString().isEmpty()) ? null
                    : new BigDecimal(params.get("idOt").toString()));
        }
        if (params.get("administrador") != null && !params.get("administrador").toString().isEmpty()) {
            q.setParameter("administrador",
                    (null == params.get("administrador")
                    || params.get("administrador").toString().isEmpty()) ? null
                    : "%" + params.get("administrador").toString().toUpperCase() + "%");
        }
        
        if (params.get("administracion") != null && !params.get("administracion").toString().isEmpty()) {
            q.setParameter("administracion",
                    (null == params.get("administracion")
                    || params.get("administracion").toString().isEmpty()) ? null
                    : "%" + params.get("administracion").toString().toUpperCase() + "%");
        }

            if (params.get("numeroCuenta") != null && !params.get("numeroCuenta").toString().isEmpty()) {
                q.setParameter("numeroCuenta",
                        (null == params.get("numeroCuenta")
                        || params.get("numeroCuenta").toString().isEmpty()) ? null
                        : new BigDecimal(params.get("numeroCuenta").toString()));
            } else {

                //espinosadiea: modificacion 09 de junio 2018 agregar campo numero de cuenta de mgl 
                if (params.get("numeroCuentaMGL") != null && !params.get("numeroCuentaMGL").toString().isEmpty()) {
                    q.setParameter("numeroCuentaMGL",
                            (null == params.get("numeroCuentaMGL")
                            || params.get("numeroCuentaMGL").toString().isEmpty()) ? null
                            : new BigDecimal(params.get("numeroCuentaMGL").toString()));
                } else {
                    if (params.get("idOt") != null && !params.get("idOt").toString().isEmpty()) {
                        q.setParameter("idOt", (null == params.get("idOt")
                                || params.get("idOt").toString().isEmpty()) ? null
                                : new BigDecimal(params.get("idOt").toString()));
                    } else {
                        
                        if (params.get("centroPoblado") != null && !params.get("centroPoblado").toString().isEmpty()) {
                            q.setParameter("centroPoblado",
                                    (null == params.get("centroPoblado")
                                    || params.get("centroPoblado").toString().isEmpty()) ? null
                                    : new BigDecimal(params.get("centroPoblado").toString()));
                        }

                        if (params.get("nombreCuenta") != null && !params.get("nombreCuenta").toString().isEmpty()) {
                            q.setParameter("nombreCuenta",
                                    (null == params.get("nombreCuenta")
                                    || params.get("nombreCuenta").toString().isEmpty()) ? null
                                    : "%" + params.get("nombreCuenta").toString().toUpperCase() + "%");
                        } else {

                            if (params.get("administrador") != null && !params.get("administrador").toString().isEmpty()) {
                                q.setParameter("administrador",
                                        (null == params.get("administrador")
                                        || params.get("administrador").toString().isEmpty()) ? null
                                        : "%" + params.get("administrador").toString().toUpperCase() + "%");
                            } else {

                                if (params.get("administracion") != null && !params.get("administracion").toString().isEmpty()) {
                                    q.setParameter("administracion",
                                            (null == params.get("administracion")
                                            || params.get("administracion").toString().isEmpty()) ? null
                                            : "%" + params.get("administracion").toString().toUpperCase() + "%");
                                } else {
                                    if (params.get("telefono") != null && !params.get("telefono").toString().isEmpty()) {
                                        q.setParameter("telefono",
                                                (null == params.get("telefono")
                                                || params.get("telefono").toString().isEmpty()) ? null
                                                : "%" + params.get("telefono").toString().toUpperCase() + "%");
                                    } else {
                                        if (params.get("cuentaSucriptor") != null && !params.get("cuentaSucriptor").toString().isEmpty()) {
                                            q.setParameter("cuentaSucriptor",
                                                    (null == params.get("cuentaSucriptor")
                                                    || params.get("cuentaSucriptor").toString().isEmpty()) ? null
                                                    : params.get("cuentaSucriptor").toString().toUpperCase());
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        
    }

    public List<String> getTecnologiasInst(CmtCuentaMatrizMgl cuentaMatrizObj, boolean traerCables) {
        List<CmtSubEdificioMgl> resultList;
        List<String> resultListTecnologias = new ArrayList<>();
        String sqlQuery = "SELECT  c FROM CmtSubEdificioMgl c join c.listTecnologiasSub t WHERE c.cuentaMatrizObj= :cuentaMatrizObj";
        Query query = entityManager.createQuery(sqlQuery);
        query.setParameter("cuentaMatrizObj", cuentaMatrizObj);
        query.setMaxResults(10);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");

        resultList = (List<CmtSubEdificioMgl>) query.getResultList();
        for (CmtSubEdificioMgl cmtSubEdificioMgl : resultList) {
            if (cmtSubEdificioMgl.getListTecnologiasSub() == null) {
                continue;
            }
            if (traerCables) {
                for (CmtTecnologiaSubMgl cmtTecnologiaSubMgl : cmtSubEdificioMgl.getListTecnologiasSub()) {
                    if (cmtTecnologiaSubMgl.getBasicaIdEstadosTec() != null
                            && cmtTecnologiaSubMgl.getBasicaIdEstadosTec().getIdentificadorInternoApp() != null
                            && cmtTecnologiaSubMgl.getBasicaIdEstadosTec().getIdentificadorInternoApp()
                                    .compareTo(Constant.BASICA_TIPO_TEC_CABLE) == 0) {
                        resultListTecnologias.add(cmtTecnologiaSubMgl.getBasicaIdTecnologias().getCodigoBasica());
                    }
                }
            } else {
                for (CmtTecnologiaSubMgl cmtTecnologiaSubMgl : cmtSubEdificioMgl.getListTecnologiasSub()) {
                    if (cmtTecnologiaSubMgl.getBasicaIdEstadosTec() != null
                            && cmtTecnologiaSubMgl.getBasicaIdEstadosTec().getIdentificadorInternoApp() != null
                            && cmtTecnologiaSubMgl.getBasicaIdEstadosTec().getIdentificadorInternoApp()
                                    .compareTo(Constant.BASICA_TIPO_TEC_CABLE) != 0) {
                        resultListTecnologias.add(cmtTecnologiaSubMgl.getBasicaIdTecnologias().getCodigoBasica());
                    }
                }
            }

        }
        HashSet<String> hashSet = new HashSet<>(resultListTecnologias);
        resultListTecnologias.clear();
        resultListTecnologias.addAll(hashSet);
        return resultListTecnologias;
    }

    public List<String> getTecnologiasInstSub(CmtSubEdificioMgl subEdificioId) {
        List<CmtSubEdificioMgl> resultList;
        List<String> resultListTecnologias = new ArrayList<>();
        String sqlQuery = "SELECT  c FROM CmtSubEdificioMgl c join c.listTecnologiasSub t WHERE c.subEdificioId= :subEdificioId";
        Query query = entityManager.createQuery(sqlQuery);
        query.setParameter("subEdificioId", subEdificioId);
        query.setMaxResults(10);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");

        resultList = (List<CmtSubEdificioMgl>) query.getResultList();
        for (CmtSubEdificioMgl cmtSubEdificioMgl : resultList) {
            for (CmtTecnologiaSubMgl cmtTecnologiaSubMgl : cmtSubEdificioMgl.getListTecnologiasSub()) {
                if (cmtTecnologiaSubMgl.getBasicaIdEstadosTec() != null
                        && cmtTecnologiaSubMgl.getBasicaIdEstadosTec().getIdentificadorInternoApp()
                        .compareTo(Constant.BASICA_TIPO_TEC_CABLE) == 0) {
                    resultListTecnologias.add(cmtTecnologiaSubMgl.getBasicaIdTecnologias().getNombreBasica());
                }
            }
        }
        HashSet<String> hashSet = new HashSet<>(resultListTecnologias);
        resultListTecnologias.clear();
        resultListTecnologias.addAll(hashSet);
        return resultListTecnologias;
    }
    
  public List<CmtCuentaMatrizMgl> findByNumeroCM(BigDecimal numeroCuenta) throws ApplicationException {
        
            List<CmtCuentaMatrizMgl> cmtCuentaMatrizMgl;
            Query query = entityManager.createNamedQuery("CmtCuentaMatrizMgl.findByNumeroCuentaMatriz");
            query.setParameter("numeroCuenta", numeroCuenta);
            cmtCuentaMatrizMgl =  query.getResultList();
            return cmtCuentaMatrizMgl;
       
    }
  
    public CmtCuentaMatrizMgl findByIdCM(BigDecimal cmtCuentaMatrizMglId) throws ApplicationException {
        CmtCuentaMatrizMgl cmtCuentaMatrizMgl;
        try {
            Query query = entityManager.createNamedQuery("CmtCuentaMatrizMgl.findByIdCM");
            query.setParameter("cuentaMatrizId", cmtCuentaMatrizMglId);
            cmtCuentaMatrizMgl = (CmtCuentaMatrizMgl) query.getSingleResult();
            return cmtCuentaMatrizMgl;
        } catch (NoResultException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }
    
      /**
     * Consulta el maximo de la secuencia por cuenta matriz
     * Autor: victor bocanegra
     * @param cm 
     * @return el maximo de la secuencia
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int selectMaximoSecXCm(CmtCuentaMatrizMgl cm)
            throws ApplicationException {

        try {
            Query query = entityManager.createNamedQuery("CmtCuentaMatrizMgl.findByMaximoSecCm");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (cm != null) {
                query.setParameter("cuentaMatrizId", cm.getCuentaMatrizId());
            }  
            return query.getSingleResult() == null ? 0
                    : ((Integer) query.getSingleResult());
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

   
       public   List<CuentaMatrizCompComercialDto> getCuentasMatricesCompComercial(BigDecimal tecnologia, BigDecimal estrato,
             String nodo,Date fechaInicio,Date fechaFinal, int inicio, int fin, String usuario) throws ApplicationException {
        try {
              List<CuentaMatrizCompComercialDto> listaCuentaMatrizCompComercialDto = new ArrayList<CuentaMatrizCompComercialDto>();
              CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
              CmtTecnologiaSubMglDaoImpl cmtTecnologiaSubMglDaoImpl = new CmtTecnologiaSubMglDaoImpl();
              CuentaMatrizCompComercialDto cuentaMatrizCompComercialDto;
              CmtCuentaMatrizMglDaoImpl cmtCuentMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
              CmtTipoOtMglDaoImpl daoTipoOt = new CmtTipoOtMglDaoImpl();
              HhppMglDaoImpl hhppMglDaoImpl = new HhppMglDaoImpl();
              List<CmtSubEdificioMgl> listaCMCComercial;
              CmtTecnologiaSubMgl cmtTecnologiaSubMglGeneral = null;
              HhppMgl hhpp = null;
              BigDecimal meta = null;
              List<HhppMgl> listaHhppMgl;
              int totalHHpp = 0;
              int totalHhppActivos = 0;
              int hhppSalaventaCampamento = 0;
              StringBuilder sql = new StringBuilder();
              boolean control = true;
              List<BigDecimal> listaCm = new ArrayList<>();
 
              sql.append("SELECT DISTINCT (t) from CmtSubEdificioMgl t "
                    + "inner join CmtCuentaMatrizMgl c "
                    + "inner join CmtTecnologiaSubMgl s "
                    + "inner join HhppMgl h "
                    + "inner join NodoMgl n "
                    + "inner join CmtDireccionMgl cm "
                    + "inner join DireccionMgl mg "
                    + "on c.cuentaMatrizId = t.cuentaMatrizObj.cuentaMatrizId "
                    + "and t.subEdificioId = s.subedificioId.subEdificioId "
                    + "and h.nodId.nodId = n.nodId "
                    + "and s.tecnoSubedificioId = h.cmtTecnologiaSubId.tecnoSubedificioId "
                    + "and cm.cuentaMatrizObj.cuentaMatrizId = c.cuentaMatrizId "
                    + "and cm.direccionObj.dirId = mg.dirId "
                    + "and mg.tipoDirObj.tdiId = 2 "
                    + "and cm.estadoRegistro = 1 "
                    + "and c.estadoRegistro = 1 "
                    + "and s.basicaIdEstadosTec.identificadorInternoApp  = '@CABLE' ");
              
              
             SimpleDateFormat diaInicial = new SimpleDateFormat("yyyy-MM-dd");
            String diaIni = diaInicial.format(fechaInicio);
            SimpleDateFormat diaFin = new SimpleDateFormat("yyyy-MM-dd");
            String diaFinal = diaFin.format(fechaFinal);
            if (diaIni != null && diaFinal != null) {
                if (fechaInicio.before(fechaFinal)) {
//                    sql.append("AND func('trunc', o.fechaCreacion) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    sql.append(" AND s.fechaEdicion BETWEEN :fechaInicioOt and  :fechaFinOt");
                } else {
                    sql.append(" AND  func('trunc', s.fechaEdicion) = :fechaInicioOt");
                }
            }

            if ((tecnologia != null)) {

                sql.append(" and  s.basicaIdTecnologias.basicaId = :tecnologia ");
            }
            if (estrato != null) {
                sql.append(" and (mg.dirEstrato  = :estrato) ");
            }

            if (nodo != null && !nodo.equals("")) {
                sql.append(" AND (n.nodCodigo = :nodo) ");
            }

            Query q = entityManager.createQuery(sql.toString());

            
            int index = 1;
            if (fechaInicio != null && fechaFinal != null) {
                q.setParameter("fechaInicioOt", fechaInicio);
            }
            if (control) {
                if (fechaFinal != null) {
                    q.setParameter("fechaFinOt", fechaFinal);
                }
            }

            if (tecnologia != null) {
                q.setParameter("tecnologia", tecnologia);
            }

            if (estrato != null) {
                q.setParameter("estrato", estrato);
            }

            if (nodo != null && !nodo.equals("")) {
                q.setParameter("nodo", nodo);
            }
            q.setFirstResult(inicio);
            q.setMaxResults(fin);
            listaCMCComercial = q.getResultList();
            getEntityManager().clear();
            
            for (CmtSubEdificioMgl cmtSubEdificioMgl : listaCMCComercial) {
                // cuenta matriz
                CmtCuentaMatrizMgl cmtCuentaMatriz = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj();
                if (!listaCm.contains(cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCuentaMatrizId())) {
                    CuentaMatrizCompComercialDto compComercialDto = cargarReporte(cmtSubEdificioMgl);
                    listaCm.add(cmtCuentaMatriz.getCuentaMatrizId());
                    listaCuentaMatrizCompComercialDto.add(compComercialDto);

                } else {
                    if (!listaCm.contains(cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCuentaId())) {
                        CuentaMatrizCompComercialDto compComercialDto = cargarReporte(cmtSubEdificioMgl);
                        listaCuentaMatrizCompComercialDto.add(compComercialDto);
                    }
                }

            }
              return listaCuentaMatrizCompComercialDto;
          } catch (NoResultException nre) {
              LOGGER.error(MSG_SIN_RESULTADOS, nre);
              throw new ApplicationException("No se encontraron resultados para "
                      + "la consulta.");
          } 

            }

       
       
      public CuentaMatrizCompComercialDto cargarReporte(CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException{
                CmtTecnologiaSubMgl cmtTecnologiaSubMglGeneral = null;
                HhppMgl hhpp = null;
                BigDecimal meta = null;
                List<HhppMgl> listaHhppMgl;
                int totalHHpp = 0;
                int totalHhppActivos = 0;
                int hhppSalaventaCampamento = 0;
                CuentaMatrizCompComercialDto cuentaMatrizCompComercialDto = null;
                CmtCuentaMatrizMgl cmtCuentaMatriz = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj();
                cuentaMatrizCompComercialDto = new CuentaMatrizCompComercialDto();
                cuentaMatrizCompComercialDto.setIdCuentaMatriz(cmtCuentaMatriz.getCuentaId());
                // edificio general
                CmtSubEdificioMgl subEdificiogGeneral = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getSubEdificioGeneral();
                // tecnologia subedificio
                for (CmtTecnologiaSubMgl tec : subEdificiogGeneral.getListTecnologiasSub()) {
                    cmtTecnologiaSubMglGeneral = tec;
                }
                // hhpp
                for (HhppMgl hhppMgl : subEdificiogGeneral.getListHhpp()) {
                    hhpp = hhppMgl;
                }
                cuentaMatrizCompComercialDto.setDireccion(subEdificiogGeneral != null
                        ? (subEdificiogGeneral.getCmtCuentaMatrizMglObj() != null
                        ? (subEdificiogGeneral.getCmtCuentaMatrizMglObj().getSubEdificioGeneral() != null
                        ? (subEdificiogGeneral.getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getDireccion() != null
                        ? subEdificiogGeneral.getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getDireccion() : "") : "") : "") : "");
                cuentaMatrizCompComercialDto.setNombreCuenta(cmtCuentaMatriz.getNombreCuenta());
                cuentaMatrizCompComercialDto.setNumeroCuenta(cmtCuentaMatriz.getNumeroCuenta().toString());
                cuentaMatrizCompComercialDto.setNumeroTorres(String.valueOf(subEdificiogGeneral != null
                        ? (subEdificiogGeneral.getCmtCuentaMatrizMglObj() != null
                        ? (subEdificiogGeneral.getCmtCuentaMatrizMglObj().getListCmtSubEdificioMglActivos() != null
                        ? subEdificiogGeneral.getCmtCuentaMatrizMglObj().getListCmtSubEdificioMglActivos().size() : 0) : 0) : 0));
                cuentaMatrizCompComercialDto.setTecnologia(cmtTecnologiaSubMglGeneral.getBasicaIdTecnologias() != null ? cmtTecnologiaSubMglGeneral.getBasicaIdTecnologias().getNombreBasica() : "");
                cuentaMatrizCompComercialDto.setNodo(cmtTecnologiaSubMglGeneral.getNodoId() != null ? cmtTecnologiaSubMglGeneral.getNodoId().getNodCodigo() : "");
                cuentaMatrizCompComercialDto.setCentroPoblado(cmtCuentaMatriz.getCentroPoblado().getGpoNombre());
                cuentaMatrizCompComercialDto.setDepartamento(cmtCuentaMatriz.getDepartamento().getGpoNombre());
                cuentaMatrizCompComercialDto.setComunidadArea(cmtCuentaMatriz.getComunidad());
                cuentaMatrizCompComercialDto.setDivision(cmtCuentaMatriz.getDivision());
                cuentaMatrizCompComercialDto.setZona(cmtCuentaMatriz.getDepartamento().getGpoNombre());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                cuentaMatrizCompComercialDto.setFechaCreacion(cmtTecnologiaSubMglGeneral.getFechaEdicion() != null ? dateFormat.format(cmtTecnologiaSubMglGeneral.getFechaEdicion()) : "");

                // calcular meta 
                        BigDecimal metaSum = new BigDecimal(0);
                  if (subEdificiogGeneral.getListTecnologiasSub() != null) {
                      for (CmtTecnologiaSubMgl tec : subEdificiogGeneral.getListTecnologiasSub()) {
                          if (tec.getBasicaIdEstadosTec().getIdentificadorInternoApp().equals(Constant.BASICA_TIPO_TEC_CABLE)) {
                              metaSum = metaSum.add(tec.getMeta());
                          }
                      }
                  }
                
                if (metaSum != null) {
                    BigDecimal total = new BigDecimal(subEdificiogGeneral.getCmtCuentaMatrizMglObj().getListCmtSubEdificioMgl().size());
                    meta = metaSum.divide(total,2, RoundingMode.HALF_UP);
                }
                // hhpp totales 
                if (subEdificiogGeneral != null) {
                if(subEdificiogGeneral.getCmtCuentaMatrizMglObj()!= null && !subEdificiogGeneral.getCmtCuentaMatrizMglObj().getListCmtSubEdificioMglActivos().isEmpty()){
                    for(CmtSubEdificioMgl cmtSub :subEdificiogGeneral.getCmtCuentaMatrizMglObj().getListCmtSubEdificioMglActivos()){

                        totalHHpp =+ cmtSub.getListHhpp().size();
                    }
                 }
                }
                   // hhpp totales acrivos
                 if (subEdificiogGeneral != null) {
                    if (subEdificiogGeneral.getEstadoSubEdificioObj() != null && subEdificiogGeneral.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null
                            && subEdificiogGeneral.getEstadoSubEdificioObj().getIdentificadorInternoApp()
                                    .compareTo(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO) == 0) {
                        listaHhppMgl =  subEdificiogGeneral.getListHhpp();
                        hhppSalaventaCampamento = listaHhppMgl.size();
                        totalHHpp = totalHHpp - hhppSalaventaCampamento;
                    } 
                }
                 
                if (subEdificiogGeneral != null) {
                    if (subEdificiogGeneral.getCmtCuentaMatrizMglObj() != null && !subEdificiogGeneral.getCmtCuentaMatrizMglObj().getListCmtSubEdificioMglActivos().isEmpty()) {
                        for (CmtSubEdificioMgl cmtSubEdif: subEdificiogGeneral.getCmtCuentaMatrizMglObj().getListCmtSubEdificioMglActivos()) {
                            for (HhppMgl hhppMgl : cmtSubEdif.getListHhpp()) {
                                if (hhppMgl.getEhhId().getEhhID().equals("CS")) {
                                    totalHhppActivos++;
                                }
                            }
                     
                        }
                    }
                }
               
                // Meta
                BigDecimal activos;
                BigDecimal totales;
                int compromiso = 0;
                BigDecimal compromisoComercial = null;
                BigDecimal porcCumplido = null;
                if (totalHhppActivos != 0 && totalHHpp != 0) {
                    activos = new BigDecimal(totalHhppActivos * 100);
                    totales = new BigDecimal(totalHHpp);
                    porcCumplido = activos.divide(totales, 2, RoundingMode.HALF_UP);

                } else {
                    porcCumplido = new BigDecimal(BigInteger.ZERO);
                }
                if (meta != null) {
                    compromisoComercial = meta.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    // si el % del compromiso comercial es igual al compromiso el reporte mostrara "En cumplimient"o
                    if ((porcCumplido.compareTo(compromisoComercial) < 0)) {
                        compromiso = 0;
                    } else {
                        compromiso = 1;
                    }
                }
                String cumplimiento = "En cumplimiento";
                String sinCumplir = "Sin Cumplir";
                cuentaMatrizCompComercialDto.setCompromisoComercial(compromisoComercial != null ? compromisoComercial : new BigDecimal(BigInteger.ZERO));
                cuentaMatrizCompComercialDto.setPorcCumplido(porcCumplido != null ? porcCumplido : new BigDecimal(BigInteger.ZERO));
                cuentaMatrizCompComercialDto.setEstrato(hhpp != null && hhpp.getDireccionObj()
                        != null && hhpp.getDireccionObj().getDirEstrato() != null ? hhpp.getDireccionObj().getDirEstrato().toString().equals("-1") ? "NG" : hhpp.getDireccionObj().getDirEstrato().toString() : "");
                cuentaMatrizCompComercialDto.setEstadoCumplimiento(cuentaMatrizCompComercialDto.getPorcCumplido() != null && cuentaMatrizCompComercialDto.getCompromisoComercial() != null ? compromiso == 1 ? cumplimiento : sinCumplir : "");
                return cuentaMatrizCompComercialDto;
    }
      
      
    public Integer getCuentasMatricesCompComercialCount(BigDecimal tecnologia, BigDecimal estrato,
            String nodo, Date fechaInicio, Date fechaFinal, int inicio, int fin, String usuario) throws ApplicationException {
    try {
            
              List<Object[]> listaCMCComercial;
              StringBuilder sql = new StringBuilder();
              boolean control = true;
 
             sql.append("SELECT DISTINCT (t) from CmtSubEdificioMgl t "
                    + "inner join CmtCuentaMatrizMgl c "
                    + "inner join CmtTecnologiaSubMgl s "
                    + "inner join HhppMgl h "
                    + "inner join NodoMgl n "
                    + "inner join CmtDireccionMgl cm "
                    + "inner join DireccionMgl mg "
                    + "on c.cuentaMatrizId = t.cuentaMatrizObj.cuentaMatrizId "
                    + "and t.subEdificioId = s.subedificioId.subEdificioId "
                    + "and h.nodId.nodId = n.nodId "
                    + "and s.tecnoSubedificioId = h.cmtTecnologiaSubId.tecnoSubedificioId "
                    + "and cm.cuentaMatrizObj.cuentaMatrizId = c.cuentaMatrizId "
                    + "and cm.direccionObj.dirId = mg.dirId "
                    + "and mg.tipoDirObj.tdiId = 2 "
                    + "and cm.estadoRegistro = 1 "
                    + "and c.estadoRegistro = 1 "
                    + "and s.basicaIdEstadosTec.identificadorInternoApp  = '@CABLE' ");
              
              
             SimpleDateFormat diaInicial = new SimpleDateFormat("yyyy-MM-dd");
            String diaIni = diaInicial.format(fechaInicio);
            SimpleDateFormat diaFin = new SimpleDateFormat("yyyy-MM-dd");
            String diaFinal = diaFin.format(fechaFinal);
            if (diaIni != null && diaFinal != null) {
                if (fechaInicio.before(fechaFinal)) {
                    sql.append(" AND s.fechaEdicion BETWEEN :fechaInicioOt and  :fechaFinOt");
                } else {
                    sql.append(" AND  func('trunc', s.fechaEdicion) = :fechaInicioOt");
                }
            }

            if ((tecnologia != null)) {

                sql.append(" and  s.basicaIdTecnologias.basicaId = :tecnologia ");
            }
            if (estrato != null) {
                sql.append(" and (mg.dirEstrato  = :estrato) ");
            }

            if (nodo != null && !nodo.equals("")) {
                sql.append(" AND (n.nodCodigo = :nodo) ");
            }

            Query q = entityManager.createQuery(sql.toString());

            
            int index = 1;
            if (fechaInicio != null && fechaFinal != null) {
                q.setParameter("fechaInicioOt", fechaInicio);
            }
            if (control) {
                if (fechaFinal != null) {
                    q.setParameter("fechaFinOt", fechaFinal);
                }
            }

            if (tecnologia != null) {
                q.setParameter("tecnologia", tecnologia);
            }

            if (estrato != null) {
                q.setParameter("estrato", estrato);
            }

            if (nodo != null && !nodo.equals("")) {
                q.setParameter("nodo", nodo);
            }
            q.setFirstResult(inicio);
            q.setMaxResults(fin);
            listaCMCComercial = q.getResultList();
            getEntityManager().clear();
            Integer resultCount = 0;
            if (listaCMCComercial != null && !listaCMCComercial.isEmpty()) {
                resultCount = (Integer) listaCMCComercial.size();
            }
            return resultCount;
            } catch (NoResultException nre) {
            LOGGER.error(MSG_SIN_RESULTADOS, nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        }
    }
   /**
     * Consulta cuenta matrices 
     * segun los filtros de busqueda para exportar a excel
     * Autor: victor bocanegra
     * @param params filtros de busqueda
     * @return List<CmtCuentaMatrizMgl>
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */               
    public List<CmtCuentaMatrizMgl> findCuentasMatricesSearchDatosExp(HashMap<String, Object> params) throws ApplicationException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT DISTINCT c FROM CmtCuentaMatrizMgl c ");

            generarSelectConsultaCM(sql, params, false);
            Query q = entityManager.createQuery(sql.toString());
            if (!params.isEmpty()) {
                agregarParametros(q, params, false);
            }

            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return q.getResultList();
        } catch (NoResultException nre) {
            LOGGER.error(MSG_SIN_RESULTADOS, nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (Exception e) {
             String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "los datos para la Cuenta Matriz. Intente más tarde.");
        }
    }
    
    
    /**
     * Consulta cuenta matrices 
     * por direccion tabulada
     * Autor: Juan David Hernandez
     * @param idDireccion
     * @return List<CmtCuentaMatrizMgl>
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */               
    public List<CmtCuentaMatrizMgl> findCuentasMatricesByIdDireccion(BigDecimal idDireccion)
            throws ApplicationException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT DISTINCT c FROM CmtDireccionMgl cm, CmtCuentaMatrizMgl c ");
            sql.append(" WHERE cm.direccionObj.dirId =:idDireccion AND ");
            sql.append(" cm.cuentaMatrizObj.cuentaMatrizId = c.cuentaMatrizId AND ");         
            sql.append(" c.estadoRegistro =:estado AND ");
            sql.append(" cm.estadoRegistro =:estado ");
         
            Query q = entityManager.createQuery(sql.toString());
            q.setParameter("estado", 1);
            q.setParameter("idDireccion", idDireccion);

            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return q.getResultList();
        } catch (NoResultException nre) {
            LOGGER.error(MSG_SIN_RESULTADOS, nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (Exception e) {
             String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "los datos para la Cuenta Matriz. Intente más tarde.");
        }
    }
    /**
     * Consulta todas las ccmm que empiezen por el nombre
     * Autor: victor bocanegra
     *
     * @param name nombre de ccmm a buscar
     * @param centroPoblado de las cuentas matrices a buscar
     * @return List<CmtCuentaMatrizMgl>
     * @throws ApplicationException
     */
     public List<CmtCuentaMatrizMgl> findCcmmByNameAndCentroPoblado(String name, 
             GeograficoPoliticoMgl centroPoblado ) throws ApplicationException {
        List<CmtCuentaMatrizMgl> resultList;
        Query query = entityManager
                .createQuery("SELECT c FROM CmtCuentaMatrizMgl c  "
                        + "WHERE c.estadoRegistro=1  AND "
                        + "c.nombreCuenta LIKE :nombreCuenta AND "
                        + "c.centroPoblado = :centroPoblado "
                        + "ORDER BY c.nombreCuenta DESC");
        query.setParameter("nombreCuenta", "%"+name+"%");
        query.setParameter("centroPoblado", centroPoblado);
        resultList = (List<CmtCuentaMatrizMgl>) query.getResultList();
        return resultList;
    }
 
    /**
     * Consulta cuenta matrices agrupadoras por centro poblado Autor: Victor
     * Manuel Bocanegra
     *
     * @param centro
     * @param basicaAgrupadora
     * @return List<CmtCuentaMatrizMgl>
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<CmtCuentaMatrizMgl> findCuentasMatricesAgrupadorasByCentro(BigDecimal centro,
            CmtBasicaMgl basicaAgrupadora)
            throws ApplicationException {

        List<CmtCuentaMatrizMgl> agrupadoras = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT DISTINCT c FROM CmtSubEdificioMgl s, CmtCuentaMatrizMgl c ");
            sql.append("WHERE c.centroPoblado.gpoId =:gpoId AND ");
            sql.append(" c.cuentaMatrizId = s.cuentaMatrizObj.cuentaMatrizId AND ");
            sql.append(" s.tipoEdificioObj =:tipoEdificioObj AND ");
            sql.append(" c.estadoRegistro =:estado AND ");
            sql.append(" s.estadoRegistro =:estado ");

            Query q = entityManager.createQuery(sql.toString());
            q.setParameter("estado", 1);
            q.setParameter("tipoEdificioObj", basicaAgrupadora);
            q.setParameter("gpoId", centro);

            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            agrupadoras = q.getResultList();

        } catch (NoResultException nre) {
            LOGGER.error(MSG_SIN_RESULTADOS, nre);
            agrupadoras = new ArrayList<>();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "los datos para la Cuenta Matriz. Intente más tarde.");
        }
        return agrupadoras;
    }
}
