/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.businessmanager.address.NodoMglManager;
import co.com.claro.mgl.dtos.CmtFiltroConsultaNodosDto;
import co.com.claro.mgl.dtos.NodoEstadoDTO;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.rest.dtos.CmtBasicaMglDto;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.CmtCoverEnum;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ConstantFiltroConsultaNodosDto;
import co.com.claro.mgl.utils.EntityManagerUtils;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author Admin
 */
public class NodoMglDaoImpl extends GenericDaoImpl<NodoMgl> {

    public static final Logger LOGGER = LogManager.getLogger(NodoMglDaoImpl.class);

    public static final String CONSULTA_STADO_NODO_SP = Constant.MGL_DATABASE_SCHEMA+".CONSULTAS_FILTROS_EC_PKG.CONSULTA_ESTADO_NODO_SP";
    
    private static final String QUERY_GET_NODES_BASE_QUERY = " SELECT n FROM NodoMgl n WHERE n.estadoRegistro = 1 ";
    private static final String LIKE_CLAUSE = " AND UPPER(%s) LIKE :%s ";
    private static final String EQUALS_CLAUSE = " AND %s = :%s ";

    public List<NodoMgl> findAll() throws ApplicationException {
        try {
            Query query = entityManager.createNamedQuery("NodoMgl.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            return (List<NodoMgl>) query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<BigDecimal> findCodCityByDivArea(BigDecimal divId, BigDecimal areId) throws ApplicationException {
        try {
            String queryStr = "SELECT DISTINCT(n.gpoId) FROM NodoMgl n "
                    + " WHERE n.divId = :divId AND n.estadoRegistro = 1";
            if (areId != null && areId.compareTo(BigDecimal.ZERO) != 0) {
                queryStr += "and n.areId = :areId";
            }

            Query query = entityManager.createQuery(queryStr);

            query.setParameter("divId", divId);
            if (areId != null && areId.compareTo(BigDecimal.ZERO) != 0) {
                query.setParameter("areId", areId);
            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            return (List<BigDecimal>) query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<NodoMgl> findNodosByComDivArea(List<BigDecimal> comunidad, BigDecimal divisional, BigDecimal area, List<String> tipos) throws ApplicationException {
        List<NodoMgl> resulList;
        String queryStr = "SELECT n FROM NodoMgl n "
                + " WHERE n.gpoId IN :gpoId "
                + " AND n.divId = :divId "
                + " AND n.nodTipo in :nodTipo "
                + " AND n.estadoRegistro = 1";
        if (area != null && area.compareTo(BigDecimal.ZERO) != 0) {
            queryStr += "and n.areId = :areId";
        }
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("gpoId", comunidad);
        query.setParameter("divId", divisional);
        query.setParameter("nodTipo", tipos);
        if (area != null && area.compareTo(BigDecimal.ZERO) != 0) {
            query.setParameter("areId", area);
        }
        resulList = (List<NodoMgl>) query.getResultList();
        return resulList;
    }

    public List<NodoMgl> findNodosByCitytipos(List<GeograficoPoliticoMgl> cities, BigDecimal divisional, BigDecimal area) throws ApplicationException {
        List<NodoMgl> resulList = new ArrayList<>();
        String queryStr = "SELECT n FROM NodoMgl n "
                + " WHERE n.gpoId = :gpoId "
                + " AND n.divId = :divId "
                + " AND n.nodTipo in :nodTipo "
                + " AND n.estadoRegistro = 1";
        if (area != null && area.compareTo(BigDecimal.ZERO) != 0) {
            queryStr += "and n.areId = :areId";
        }

        for (GeograficoPoliticoMgl gp : cities) {
            Query query = entityManager.createQuery(queryStr);
            query.setParameter("gpoId", gp.getGpoId());
            query.setParameter("divId", divisional);
            query.setParameter("nodTipo", getTiposCity(gp));
            if (area != null && area.compareTo(BigDecimal.ZERO) != 0) {
                query.setParameter("areId", area);
            }
            List<NodoMgl> resul = (List<NodoMgl>) query.getResultList();
            if (resul != null && !resul.isEmpty()) {
                resulList.addAll(resul);
            }
        }
        return resulList;
    }

    public List<NodoMgl> findNodosByCity(BigDecimal gpoId) throws ApplicationException {
        List<NodoMgl> resulList;
        List<BigDecimal> resultadoyCiudad;

        Query queryCiudad = entityManager.createQuery("SELECT g.gpoId FROM GeograficoPoliticoMgl g WHERE g.geoGpoId = :gpoId");
        queryCiudad.setParameter("gpoId", gpoId);
        resultadoyCiudad = (List<BigDecimal>) queryCiudad.getResultList();

        Query query = entityManager.createQuery("SELECT n FROM NodoMgl n "
                + "WHERE n.gpoId in :listaCiudades AND n.estadoRegistro = 1");
        query.setParameter("listaCiudades", resultadoyCiudad);
        resulList = (List<NodoMgl>) query.getResultList();
        return resulList;
    }

    private List<String> getTiposCity(GeograficoPoliticoMgl city) {
        List<String> tipoNodoList = new ArrayList<>();
        if (!city.isSelectedUNI() && !city.isSelectedBI() && !city.isSelectedDTH()) {
            tipoNodoList.add("UNI");
            tipoNodoList.add("BI");
            tipoNodoList.add("DTH");
        } else {
            if (city.isSelectedUNI()) {
                tipoNodoList.add("UNI");
            }
            if (city.isSelectedBI()) {
                tipoNodoList.add("BI");
            }
            if (city.isSelectedDTH()) {
                tipoNodoList.add("DTH");
            }
        }
        return tipoNodoList;
    }

    public NodoMgl findByName(String name) {
        List<NodoMgl> result;
        String queryStr = "SELECT n FROM NodoMgl n "
                + " WHERE n.nodNombre = :nodNombre "
                + " AND n.estadoRegistro = 1";

        Query query = entityManager.createQuery(queryStr);
        query.setParameter("nodNombre", name.toUpperCase());
        result = (List<NodoMgl>) query.getResultList();
        if (result == null || result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    public List<NodoMgl> findByLikeCodigo(String codigo) throws ApplicationException {
        List<NodoMgl> resultList;
        Query query = entityManager.createQuery("SELECT n FROM NodoMgl n WHERE UPPER(n.nodCodigo) = :nodCodigo  ORDER BY n.nodCodigo ASC  ");
        query.setParameter("nodCodigo", codigo.toUpperCase());
        resultList = (List<NodoMgl>) query.getResultList();
        return resultList;
    }

    public NodoMgl findByCodigo(String codigo) throws ApplicationException {
        try {
            List<NodoMgl> result;
            Query query = entityManager.createQuery("SELECT n FROM NodoMgl n "
                    + " WHERE UPPER(n.nodCodigo) = :nodCodigo "
                    + " AND n.estadoRegistro = 1");
            query.setParameter("nodCodigo", codigo.toUpperCase());
            result = (List<NodoMgl>) query.getResultList();
            if (result == null || result.isEmpty()) {
                return null;
            }
            return result.get(0);
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }

    }

    public List<NodoMgl> getListNodoByCodigo(String codigo) throws ApplicationException {
        try {
            List<NodoMgl> result;
            Query query = entityManager.createQuery("SELECT n FROM NodoMgl n "
                    + " WHERE UPPER(n.nodCodigo) = :nodCodigo "
                    + " AND n.estadoRegistro = 1");
            query.setParameter("nodCodigo", codigo.toUpperCase());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = (List<NodoMgl>) query.getResultList();
            if (result == null || result.isEmpty()) {
                result = null;
            }
            return result;
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public NodoMgl findByCodigoAndCity(String codigo, BigDecimal gpoId) throws ApplicationException {
        try {
            NodoMgl result;
            Query query = entityManager.createQuery("SELECT n FROM NodoMgl n "
                    + " WHERE UPPER(n.nodCodigo) = :nodCodigo "
                    + " AND n.gpoId = :gpoId "
                    + " AND n.estadoRegistro = 1 ");
            query.setParameter("nodCodigo", codigo.toUpperCase());
            query.setParameter("gpoId", gpoId);
            result = (NodoMgl) query.getSingleResult();
            return result;
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }

    }

    public List<NodoMgl> findNodosByCitytipos(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * * valbuenayf Metodo para buscar el nodo por el codigo del nodo, id de la
     * ciudad y el id de la tecnologia
     *
     * @param codigo
     * @param idCentroPoblado
     * @param idTecnologia
     * @return
     * @throws ApplicationException
     */
    public NodoMgl findByCodigoNodo(String codigo, BigDecimal idCentroPoblado, BigDecimal idTecnologia) throws ApplicationException {
        NodoMgl nodo;
        try {
            Query query = entityManager.createNamedQuery("NodoMgl.findByCodNodIdCentroPobladoIdTec");
            query.setParameter("codigo", codigo.toUpperCase().trim());
            query.setParameter("idCentroPoblado", idCentroPoblado);
            query.setParameter("idTecnologia", idTecnologia);

            List<NodoMgl> resultList = query.getResultList();

            if (resultList != null && !resultList.isEmpty()) {
                nodo = resultList.get(0);
            } else {
                nodo = null;
            }

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
        return nodo;
    }

    /**
     * *Victor Bocanegra Metodo para buscar los nodos por los filtros de la
     * tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @return List<NodoMgl>
     * @throws ApplicationException
     */
    public List<NodoMgl> findByFiltro(int paginaSelected,
            int maxResults, CmtFiltroConsultaNodosDto consulta) throws ApplicationException {
        List<NodoMgl> resultList;
        
        StringBuilder queryTipo = new StringBuilder(QUERY_GET_NODES_BASE_QUERY);
        Map<String, Object> parameters = new HashMap<>();

        addCondition(queryTipo, parameters, consulta.getGeograficoPolitico(), ConstantFiltroConsultaNodosDto.FIELD_GPO_ID, ConstantFiltroConsultaNodosDto.PARAM_GPO_ID, EQUALS_CLAUSE);
        addLikeCondition(queryTipo, parameters, consulta.getCodigoNodo(), ConstantFiltroConsultaNodosDto.FIELD_NOD_CODIGO, ConstantFiltroConsultaNodosDto.PARAM_NOD_CODIGO);
        addLikeCondition(queryTipo, parameters, consulta.getNombreNodo(), ConstantFiltroConsultaNodosDto.FIELD_NOD_NOMBRE, ConstantFiltroConsultaNodosDto.PARAM_NOD_NOMBRE);
        addLikeCondition(queryTipo, parameters, consulta.getFechaActivacion(), ConstantFiltroConsultaNodosDto.FIELD_NOD_FECHA_ACTIVACION, ConstantFiltroConsultaNodosDto.PARAM_NOD_FECHA_ACTIVACION);
        addCondition(queryTipo, parameters, consulta.getUnidadGestion(), ConstantFiltroConsultaNodosDto.FIELD_UGE_ID, ConstantFiltroConsultaNodosDto.PARAM_UGE_ID, EQUALS_CLAUSE);
        addCondition(queryTipo, parameters, consulta.getZonaIdNodo(), ConstantFiltroConsultaNodosDto.FIELD_ZON_ID, ConstantFiltroConsultaNodosDto.PARAM_ZON_ID, EQUALS_CLAUSE);
        addLikeCondition(queryTipo, parameters, consulta.getDistritoNodo(), ConstantFiltroConsultaNodosDto.FIELD_DIS_ID, ConstantFiltroConsultaNodosDto.PARAM_DIS_ID);
        addCondition(queryTipo, parameters, consulta.getDivicionNodo(), ConstantFiltroConsultaNodosDto.FIELD_DIV_ID, ConstantFiltroConsultaNodosDto.PARAM_DIV_ID, EQUALS_CLAUSE);
        addCondition(queryTipo, parameters, consulta.getAreaNodo(), ConstantFiltroConsultaNodosDto.FIELD_ARE_ID, ConstantFiltroConsultaNodosDto.PARAM_ARE_ID, EQUALS_CLAUSE);
        addLikeCondition(queryTipo, parameters, consulta.getHeadendNodo(), ConstantFiltroConsultaNodosDto.FIELD_NOD_HEADEND, ConstantFiltroConsultaNodosDto.PARAM_NOD_HEADEND);
        addLikeCondition(queryTipo, parameters, consulta.getCampoAdi1Nodo(), ConstantFiltroConsultaNodosDto.FIELD_NOD_CAMPO_ADI1, ConstantFiltroConsultaNodosDto.PARAM_NOD_CAMPO_ADI1);
        addLikeCondition(queryTipo, parameters, consulta.getCampoAdi2Nodo(), ConstantFiltroConsultaNodosDto.FIELD_NOD_CAMPO_ADI2, ConstantFiltroConsultaNodosDto.PARAM_NOD_CAMPO_ADI2);
        addLikeCondition(queryTipo, parameters, consulta.getCampoAdi3Nodo(), ConstantFiltroConsultaNodosDto.FIELD_NOD_CAMPO_ADI3, ConstantFiltroConsultaNodosDto.PARAM_NOD_CAMPO_ADI3);
        addLikeCondition(queryTipo, parameters, consulta.getCampoAdi4Nodo(), ConstantFiltroConsultaNodosDto.FIELD_NOD_CAMPO_ADI4, ConstantFiltroConsultaNodosDto.PARAM_NOD_CAMPO_ADI4);
        addLikeCondition(queryTipo, parameters, consulta.getCampoAdi5Nodo(), ConstantFiltroConsultaNodosDto.FIELD_NOD_CAMPO_ADI5, ConstantFiltroConsultaNodosDto.PARAM_NOD_CAMPO_ADI5);
        addLikeCondition(queryTipo, parameters, consulta.getTipoNodo(), ConstantFiltroConsultaNodosDto.FIELD_NOD_TIPO, ConstantFiltroConsultaNodosDto.PARAM_NOD_TIPO);
        addLikeCondition(queryTipo, parameters, consulta.getComIdNodo(), ConstantFiltroConsultaNodosDto.FIELD_COM_ID, ConstantFiltroConsultaNodosDto.PARAM_COM_ID);
        addLikeCondition(queryTipo, parameters, consulta.getOpera(), ConstantFiltroConsultaNodosDto.FIELD_OPERA, ConstantFiltroConsultaNodosDto.PARAM_OPERA);
        addLikeCondition(queryTipo, parameters, consulta.getOlt(), ConstantFiltroConsultaNodosDto.FIELD_OLT, ConstantFiltroConsultaNodosDto.PARAM_OLT);
        addLikeCondition(queryTipo, parameters, consulta.getOltNodo(), ConstantFiltroConsultaNodosDto.FIELD_OLT_NODO, ConstantFiltroConsultaNodosDto.PARAM_OLT_NODO);
        addLikeCondition(queryTipo, parameters, consulta.getOt(), ConstantFiltroConsultaNodosDto.FIELD_OT, ConstantFiltroConsultaNodosDto.PARAM_OT);


        queryTipo.append("ORDER BY n.nodCodigo ASC ");

        Query query = entityManager.createQuery(queryTipo.toString());
        
        // Asignar parametros dinamicamente
        parameters.forEach(query::setParameter);
    
        query.setFirstResult(paginaSelected);
        query.setMaxResults(maxResults);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<NodoMgl>) query.getResultList();

        return resultList;
    }
    
     private void addLikeCondition(StringBuilder query, Map<String, Object> parameters, String value, String column, String paramName) {
        if (value != null && !value.isEmpty()) {
            query.append(String.format(LIKE_CLAUSE, column, paramName));
            parameters.put(paramName, "%" + value.toUpperCase() + "%");
        }
    }

    private void addCondition(StringBuilder query, Map<String, Object> parameters, Object value, String column, String paramName, String clause) {
        if (value != null) {
            query.append(String.format(clause, column, paramName));
            parameters.put(paramName, value);
        }
    }

    /**
     * *Victor Bocanegra Metodo para contar los nodos por los filtros de la
     * tabla
     *
     * @param consulta
     * @return Long
     * @throws ApplicationException
     */
    public Long countByNodFiltro(CmtFiltroConsultaNodosDto consulta)
            throws ApplicationException {

        Long resultCount;
        String queryTipo;
        queryTipo = "SELECT COUNT(1) FROM NodoMgl n  WHERE 1=1  ";

        if (consulta.getGeograficoPolitico() != null) {
            queryTipo += "AND  n.gpoId =  :gpoId ";
        }
        if (consulta.getCodigoNodo() != null && !consulta.getCodigoNodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodCodigo) LIKE :nodCodigo ";
        }
        if (consulta.getNombreNodo() != null && !consulta.getNombreNodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodNombre) LIKE :nodNombre ";
        }
        if (consulta.getFechaActivacion() != null && !consulta.getFechaActivacion().isEmpty()) {
            queryTipo += "AND UPPER(n.nodFechaActivacion) LIKE :nodFechaActivacion ";
        }
        if (consulta.getUnidadGestion() != null) {
            queryTipo += "AND n.ugeId = :ugeId ";
        }
        if (consulta.getZonaIdNodo() != null) {
            queryTipo += "AND n.zonId = :zonId ";
        }
        if (consulta.getDistritoNodo() != null && !consulta.getDistritoNodo().isEmpty()) {
            queryTipo += "AND UPPER(n.disId) LIKE :disId ";
        }
        if (consulta.getDivicionNodo() != null) {
            queryTipo += "AND n.divId = :divId ";
        }
        if (consulta.getAreaNodo() != null) {
            queryTipo += "AND n.areId = :areId ";
        }
        if (consulta.getHeadendNodo() != null && !consulta.getHeadendNodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodHeadEnd) LIKE :nodHeadEnd ";
        }
        if (consulta.getCampoAdi1Nodo() != null && !consulta.getCampoAdi1Nodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodCampoAdicional1) LIKE :nodCampoAdicional1 ";
        }
        if (consulta.getCampoAdi2Nodo() != null && !consulta.getCampoAdi2Nodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodCampoAdicional2) LIKE :nodCampoAdicional2 ";
        }
        if (consulta.getCampoAdi3Nodo() != null && !consulta.getCampoAdi3Nodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodCampoAdicional3) LIKE :nodCampoAdicional3 ";
        }
        if (consulta.getCampoAdi4Nodo() != null && !consulta.getCampoAdi4Nodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodCampoAdicional4) LIKE :nodCampoAdicional4 ";
        }
        if (consulta.getCampoAdi5Nodo() != null && !consulta.getCampoAdi5Nodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodCampoAdicional5) LIKE :nodCampoAdicional5 ";
        }
        if (consulta.getTipoNodo() != null && !consulta.getTipoNodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodTipo.nombreBasica) LIKE :tipoNodo ";
        }

        if (consulta.getComIdNodo() != null && !consulta.getComIdNodo().isEmpty()) {
            queryTipo += "AND UPPER(n.comId.codigoRr) LIKE :codigoRr ";
        }

        queryTipo += "AND n.estadoRegistro = 1 ";

        Query query = entityManager.createQuery(queryTipo);

        if (consulta.getGeograficoPolitico() != null) {
            query.setParameter("gpoId", consulta.getGeograficoPolitico());
        }
        if (consulta.getCodigoNodo() != null && !consulta.getCodigoNodo().isEmpty()) {
            query.setParameter("nodCodigo", "%" + consulta.getCodigoNodo().toUpperCase() + "%");
        }
        if (consulta.getNombreNodo() != null && !consulta.getNombreNodo().isEmpty()) {
            query.setParameter("nodNombre", "%" + consulta.getNombreNodo().toUpperCase() + "%");
        }
        if (consulta.getFechaActivacion() != null && !consulta.getFechaActivacion().isEmpty()) {
            query.setParameter("nodFechaActivacion", "%" + consulta.getFechaActivacion().toUpperCase() + "%");
        }
        if (consulta.getUnidadGestion() != null) {
            query.setParameter("ugeId", consulta.getUnidadGestion());
        }
        if (consulta.getZonaIdNodo() != null) {
            query.setParameter("zonId", consulta.getZonaIdNodo());
        }
        if (consulta.getDistritoNodo() != null && !consulta.getDistritoNodo().isEmpty()) {
            query.setParameter("disId", "%" + consulta.getDistritoNodo().toUpperCase() + "%");
        }
        if (consulta.getDivicionNodo() != null) {
            query.setParameter("divId", consulta.getDivicionNodo());
        }
        if (consulta.getAreaNodo() != null) {
            query.setParameter("areId", consulta.getAreaNodo());
        }
        if (consulta.getHeadendNodo() != null && !consulta.getHeadendNodo().isEmpty()) {
            query.setParameter("nodHeadEnd", "%" + consulta.getHeadendNodo().toUpperCase() + "%");
        }
        if (consulta.getCampoAdi1Nodo() != null && !consulta.getCampoAdi1Nodo().isEmpty()) {
            query.setParameter("nodCampoAdicional1", "%" + consulta.getCampoAdi1Nodo().toUpperCase() + "%");
        }
        if (consulta.getCampoAdi2Nodo() != null && !consulta.getCampoAdi2Nodo().isEmpty()) {
            query.setParameter("nodCampoAdicional2", "%" + consulta.getCampoAdi2Nodo().toUpperCase() + "%");
        }
        if (consulta.getCampoAdi3Nodo() != null && !consulta.getCampoAdi3Nodo().isEmpty()) {
            query.setParameter("nodCampoAdicional3", "%" + consulta.getCampoAdi3Nodo().toUpperCase() + "%");
        }
        if (consulta.getCampoAdi4Nodo() != null && !consulta.getCampoAdi4Nodo().isEmpty()) {
            query.setParameter("nodCampoAdicional4", "%" + consulta.getCampoAdi4Nodo().toUpperCase() + "%");
        }
        if (consulta.getCampoAdi5Nodo() != null && !consulta.getCampoAdi5Nodo().isEmpty()) {
            query.setParameter("nodCampoAdicional5", "%" + consulta.getCampoAdi5Nodo().toUpperCase() + "%");
        }

        if (consulta.getTipoNodo() != null && !consulta.getTipoNodo().isEmpty()) {
            query.setParameter("tipoNodo", "%" + consulta.getTipoNodo().toUpperCase() + "%");
        }

        if (consulta.getComIdNodo() != null && !consulta.getComIdNodo().isEmpty()) {
            query.setParameter("codigoRr", "%" + consulta.getComIdNodo().toUpperCase() + "%");
        }

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultCount = (Long) query.getSingleResult();
        return resultCount;
    }

    /**
     * Bucar nodos por tipo de tecnologia y ciudad Busca los nodos que tengan el
     * gpoId->ciudad y nodTipo->TipoTecnologia-> Basica
     *
     * @author becerraarmr
     * @param firstResult
     *
     * @param gpoId id que representa la ciudad
     * @param maxResults
     * @param nodTipo representa el tipo de tecnologia
     *
     * @return el listado de nodos->NodoMgl encontrados.
     *
     * @throws co.com.claro.mgl.error.ApplicationException si ocurre un error en
     * la busqueda de la información.
     */
    public List<NodoMgl> findNodosCentroPobladoAndTipoTecnologia(int firstResult,
            int maxResults, BigDecimal gpoId, CmtBasicaMgl nodTipo) throws ApplicationException {
        try {
            if (gpoId == null || nodTipo == null) {
                return null;
            }
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();
            List<NodoMgl> resulList;
            String sql = "SELECT n FROM NodoMgl n "
                    + " WHERE n.gpoId = :gpoId AND n.nodTipo=:nodTipo "
                    + " AND n.estadoRegistro = 1 "
                    + " ORDER BY n.nodCodigo ASC";
            Query query = entityManager.createQuery(sql);
            query.setParameter("gpoId", gpoId);
            query.setParameter("nodTipo", nodTipo);
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
            resulList = (List<NodoMgl>) query.getResultList();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * busca de nodos por tipo de tecnologia y ciudad Busca los nodos que tengan
     * el gpoId->ciudad y nodTipo->TipoTecnologia-> Basica
     *
     * @author Juan David Hernandez
     * @param firstResult
     *
     * @param gpoId id que representa la ciudad
     * @param maxResults
     * @param nodTipo representa el tipo de tecnologia
     *
     * @return cantidad de nodos
     *
     * @throws co.com.claro.mgl.error.ApplicationException si ocurre un error en
     * la busqueda de la información.
     */
    public List<NodoMgl> findNodosCiudadAndTipoTecnologia(int firstResult,
            int maxResults, BigDecimal gpoId, CmtBasicaMgl nodTipo)
            throws ApplicationException {
        try {
            if (gpoId == null || nodTipo == null) {
                return null;
            }
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();
            List<NodoMgl> resulList;
            String sql = "SELECT n FROM NodoMgl n "
                    + " WHERE n.gpoId IN (SELECT g.gpoId FROM GeograficoPoliticoMgl g "
                    + " WHERE g.geoGpoId=:gpoId) "
                    + " AND n.nodTipo=:nodTipo "
                    + " AND n.estadoRegistro = 1"
                    + " ORDER BY n.nodCodigo ASC";
            Query query = entityManager.createQuery(sql);
            query.setParameter("gpoId", gpoId);//gpoID es de ciudad
            query.setParameter("nodTipo", nodTipo);
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
            resulList = (List<NodoMgl>) query.getResultList();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Cantidad de nodos por tipo de tecnologia y centro poblado Busca los nodos
     * que tengan el gpoId->centro poblado y nodTipo->TipoTecnologia-> Basica
     *
     * @author Juan David Hernandez
     *
     * @param gpoId id que representa el centro poblado
     * @param nodTipo representa el tipo de tecnologia
     *
     * @return cantidad de nodos
     *
     * @throws co.com.claro.mgl.error.ApplicationException si ocurre un error en
     * la busqueda de la información.
     */
    public int countNodosCentroPobladoAndTipoTecnologia(BigDecimal gpoId,
            CmtBasicaMgl nodTipo) throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();
            int resulList;
            String sql = "SELECT count(1) FROM NodoMgl n "
                    + " WHERE n.gpoId = :gpoId AND n.nodTipo=:nodTipo "
                    + " AND n.estadoRegistro = 1"
                    + " ORDER BY n.nodCodigo ASC";
            Query query = entityManager.createQuery(sql);
            query.setParameter("gpoId", gpoId);
            query.setParameter("nodTipo", nodTipo);
            resulList = query.getSingleResult() == null ? 0
                    : ((Long) query.getSingleResult()).intValue();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Cantidad de nodos por tipo de tecnologia y ciudad Busca los nodos que
     * tengan el gpoId->ciudad y nodTipo->TipoTecnologia-> Basica
     *
     * @author Juan David Hernandez
     *
     * @param gpoId id que representa la ciudad
     * @param nodTipo representa el tipo de tecnologia
     *
     * @return cantidad de nodos
     *
     * @throws co.com.claro.mgl.error.ApplicationException si ocurre un error en
     * la busqueda de la información.
     */
    public int countNodosCiudadAndTipoTecnologia(BigDecimal gpoId,
            CmtBasicaMgl nodTipo) throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();
            int resulList;
            GeograficoPoliticoMgl n;
            String sql = "SELECT Count(1) FROM NodoMgl n "
                    + " WHERE n.gpoId IN (SELECT g.gpoId FROM GeograficoPoliticoMgl g "
                    + " WHERE g.geoGpoId=:gpoId) "
                    + " AND n.nodTipo=:nodTipo "
                    + " AND n.estadoRegistro = 1"
                    + " ORDER BY n.nodCodigo ASC";

            Query query = entityManager.createQuery(sql);
            query.setParameter("gpoId", gpoId);//gpoID es de ciudad
            query.setParameter("nodTipo", nodTipo);

            resulList = query.getSingleResult() == null ? 0
                    : ((Long) query.getSingleResult()).intValue();

            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Bucar nodos por tipo de tecnologia y ciudad Busca los nodos que tengan el
     * gpoId->ciudad y nodTipo->TipoTecnologia-> Basica
     *
     * @author becerraarmr
     *
     * @param gpoId id que representa la ciudad
     * @param nodTipo representa el tipo de tecnologia
     *
     * @return el listado de nodos->NodoMgl encontrados.
     *
     * @throws co.com.claro.mgl.error.ApplicationException si ocurre un error en
     * la busqueda de la información.
     */
    public List<NodoMgl> findNodosCentroPobladoAndTipoTecnologia(BigDecimal gpoId,
            CmtBasicaMgl nodTipo) throws ApplicationException {
        try {
            if (gpoId == null || nodTipo == null) {
                return null;
            }
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();
            List<NodoMgl> resulList;
            String sql = "SELECT n FROM NodoMgl n "
                    + " WHERE n.gpoId = :gpoId AND n.nodTipo=:nodTipo "
                    + " AND n.estadoRegistro = 1"
                    + " ORDER BY n.nodCodigo ASC";
            Query query = entityManager.createQuery(sql);
            query.setParameter("gpoId", gpoId);
            query.setParameter("nodTipo", nodTipo);
            resulList = (List<NodoMgl>) query.getResultList();
            entityManager.getTransaction().commit();
            return resulList;
        } catch (Exception e) {
           String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<NodoMgl> findNodosCiudadAndTipoTecnologia(BigDecimal gpoId,
            CmtBasicaMgl nodTipo) throws ApplicationException {
        try {
            if (gpoId == null || nodTipo == null) {
                return null;
            }
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();
            List<NodoMgl> resulList;
            String sql = "SELECT n FROM NodoMgl n "
                    + " WHERE n.gpoId IN (SELECT g.gpoId FROM GeograficoPoliticoMgl g "
                    + " WHERE g.geoGpoId=:gpoId) "
                    + " AND n.nodTipo=:nodTipo "
                    + " AND n.estadoRegistro = 1"
                    + " ORDER BY n.nodCodigo ASC";
            Query query = entityManager.createQuery(sql);
            query.setParameter("gpoId", gpoId);//gpoID es de ciudad
            query.setParameter("nodTipo", nodTipo);
            resulList = (List<NodoMgl>) query.getResultList();
            entityManager.getTransaction().commit();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Buscar nodos Busca los nodos según los parámetros tecnologia y centro
     * poblado.
     *
     * @author becerraarmr
     * @param nodTipo tipo de tecnologia
     * @param gpoId centro poblado
     * @return Lista con los nodos encontrados-
     * @throws ApplicationException si hay algún error en la busqueda
     */
    public List<NodoMgl> findNodos(CmtBasicaMgl nodTipo,
            BigDecimal gpoId) throws ApplicationException {
        List<NodoMgl> lista = new ArrayList<>();
        if (nodTipo == null || gpoId == null) {
            return lista;
        }
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();

            String name = "NodoMgl.findAllByNodTipoByGpoId";
            Query queryNodo = entityManager.createNamedQuery(name);
            queryNodo.setParameter("nodTipo", nodTipo);
            queryNodo.setParameter("gpoId", gpoId);
            lista = queryNodo.getResultList();
            entityManager.getTransaction().commit();
            return lista;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Buscar nodos Busca los nodos que correspondan a la busqueda
     *
     * @author becerraarmr
     * @param nodTipo básica del tipo técnologia
     * @param gpoId id del centro poblado
     * @param codNodo código del nodo
     * @return Listado encontrado
     * @throws ApplicationException error si hay
     */
    public List<NodoMgl> findNodos(CmtBasicaMgl nodTipo,
            BigDecimal gpoId, String codNodo) throws ApplicationException {
        List<NodoMgl> lista = new ArrayList<>();
        if (nodTipo == null || gpoId == null) {
            return lista;
        }
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();
            String name = "NodoMgl.findAllByNodTipoByGpoIdByNodCodigo";
            Query queryNodo = entityManager.createNamedQuery(name);
            queryNodo.setParameter("nodTipo", nodTipo);
            queryNodo.setParameter("gpoId", gpoId);
            queryNodo.setParameter("nodCodigo", codNodo);
            lista = queryNodo.getResultList();
            entityManager.getTransaction().commit();
            return lista;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * *Victor Bocanegra busqueda del nodo por codigo y comunidaddRR.
     *
     * @param codigo
     * @param cmtComunidadRr
     * @return NodoMgl
     * @throws ApplicationException
     */
    public NodoMgl findByCodigoAndComunidadRR(String codigo, CmtComunidadRr cmtComunidadRr)
            throws ApplicationException {
        try {
            Query query = entityManager.createNamedQuery("NodoMgl.findByCodigoAndComunidadRR");
            query.setParameter("codigo", codigo);
            query.setParameter("comId", cmtComunidadRr);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            return (NodoMgl) query.getSingleResult();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public NodoMgl findByNodoId(BigDecimal id)
            throws ApplicationException {
        try {
            Query query = entityManager.createNamedQuery("NodoMgl.findByCodNodId");
            query.setParameter("id", id);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            return (NodoMgl) query.getSingleResult();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * *Jenny Rodriguez metodo para buscar la ubicacion geografica del nodo.
     *
     * @param codigo codigo del nodo
     * @return NodoMglDto
     * @throws ApplicationException
     */
    public GeograficoPoliticoMgl findPoliticalGeographyNodoByCodNodo(final String codigo) throws ApplicationException {
        try {
            Query query = entityManager.createQuery("SELECT g FROM NodoMgl n, "
                    + "GeograficoPoliticoMgl g "
                    + " WHERE n.gpoId = g.gpoId AND UPPER(n.nodCodigo) = :nodCodigo "
                    + " AND n.estadoRegistro = 1");
            query.setParameter("nodCodigo", codigo.toUpperCase());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            GeograficoPoliticoMgl geograficoPoliticoMgl = (GeograficoPoliticoMgl) query.getSingleResult();
            return geograficoPoliticoMgl;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * *Jenny Rodriguez metodo para buscar la tabla basica apartir del codigo
     * del nodo
     *
     * @param codigo codigo del nodo
     * @param idBasica identificacion de la tabla basica
     * @return NodoMglDto
     * @throws ApplicationException
     */
    public CmtBasicaMglDto findBasicasNodoByCodNodo(final String codigo, String idBasica) throws ApplicationException {
        try {
            CmtBasicaMglDto basica = new CmtBasicaMglDto();
            Query query = entityManager.createQuery("SELECT b FROM NodoMgl n, "
                    + "CmtBasicaMgl b WHERE n." + idBasica + " = b.basicaId "
                    + " AND UPPER(n.nodCodigo) = :nodCodigo "
                    + " AND n.estadoRegistro = 1");

            query.setParameter("nodCodigo", codigo.toUpperCase());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            List<CmtBasicaMgl> resultList = query.getResultList();
            if (!resultList.isEmpty()) {
                CmtBasicaMgl basicaMgl = resultList.get(0);
                basica.setCodigoTipo(basicaMgl.getTipoBasicaObj().getCodigoTipo());
                basica.setNombreTipo(basicaMgl.getTipoBasicaObj().getNombreTipo());
                basica.setCodigoBasica(basicaMgl.getCodigoBasica());
                basica.setNombreBasica(basicaMgl.getNombreBasica());
                basica.setAbreviatura(basicaMgl.getAbreviatura());
            }

            return basica;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * *Victor Bocanegra Metodo para buscar los nodos por los filtros
     *
     * @param consulta
     * @return List<NodoMgl>
     * @throws ApplicationException
     */
    public List<NodoMgl> findByFiltroExportar(CmtFiltroConsultaNodosDto consulta)
            throws ApplicationException {

        List<NodoMgl> resultList;
        String queryTipo = "SELECT n FROM NodoMgl n  WHERE  1=1  ";

        if (consulta.getGeograficoPolitico() != null) {
            queryTipo += "AND  n.gpoId =  :gpoId ";
        }
        if (consulta.getCodigoNodo() != null && !consulta.getCodigoNodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodCodigo) LIKE :nodCodigo ";
        }
        if (consulta.getNombreNodo() != null && !consulta.getNombreNodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodNombre) LIKE :nodNombre ";
        }
        if (consulta.getFechaActivacion() != null && !consulta.getFechaActivacion().isEmpty()) {
            queryTipo += "AND UPPER(n.nodFechaActivacion) LIKE :nodFechaActivacion ";
        }
        if (consulta.getUnidadGestion() != null) {
            queryTipo += "AND n.ugeId = :ugeId ";
        }
        if (consulta.getZonaIdNodo() != null) {
            queryTipo += "AND n.zonId = :zonId ";
        }
        if (consulta.getDistritoNodo() != null && !consulta.getDistritoNodo().isEmpty()) {
            queryTipo += "AND UPPER(n.disId) LIKE :disId ";
        }
        if (consulta.getDivicionNodo() != null) {
            queryTipo += "AND n.divId = :divId ";
        }
        if (consulta.getAreaNodo() != null) {
            queryTipo += "AND n.areId = :areId ";
        }
        if (consulta.getHeadendNodo() != null && !consulta.getHeadendNodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodHeadEnd) LIKE :nodHeadEnd ";
        }
        if (consulta.getCampoAdi1Nodo() != null && !consulta.getCampoAdi1Nodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodCampoAdicional1) LIKE :nodCampoAdicional1 ";
        }
        if (consulta.getCampoAdi2Nodo() != null && !consulta.getCampoAdi2Nodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodCampoAdicional2) LIKE :nodCampoAdicional2 ";
        }
        if (consulta.getCampoAdi3Nodo() != null && !consulta.getCampoAdi3Nodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodCampoAdicional3) LIKE :nodCampoAdicional3 ";
        }
        if (consulta.getCampoAdi4Nodo() != null && !consulta.getCampoAdi4Nodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodCampoAdicional4) LIKE :nodCampoAdicional4 ";
        }
        if (consulta.getCampoAdi5Nodo() != null && !consulta.getCampoAdi5Nodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodCampoAdicional5) LIKE :nodCampoAdicional5 ";
        }
        if (consulta.getTipoNodo() != null && !consulta.getTipoNodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodTipo.nombreBasica) LIKE :tipoNodo ";
        }

        if (consulta.getComIdNodo() != null && !consulta.getComIdNodo().isEmpty()) {
            queryTipo += "AND UPPER(n.comId.codigoRr) LIKE :codigoRr ";
        }

        queryTipo += "AND n.estadoRegistro = 1 ";

        Query query = entityManager.createQuery(queryTipo);

        if (consulta.getGeograficoPolitico() != null) {
            query.setParameter("gpoId", consulta.getGeograficoPolitico());
        }
        if (consulta.getCodigoNodo() != null && !consulta.getCodigoNodo().isEmpty()) {
            query.setParameter("nodCodigo", "%" + consulta.getCodigoNodo().toUpperCase() + "%");
        }
        if (consulta.getNombreNodo() != null && !consulta.getNombreNodo().isEmpty()) {
            query.setParameter("nodNombre", "%" + consulta.getNombreNodo().toUpperCase() + "%");
        }
        if (consulta.getFechaActivacion() != null && !consulta.getFechaActivacion().isEmpty()) {
            query.setParameter("nodFechaActivacion", "%" + consulta.getFechaActivacion().toUpperCase() + "%");
        }
        if (consulta.getUnidadGestion() != null) {
            query.setParameter("ugeId", consulta.getUnidadGestion());
        }
        if (consulta.getZonaIdNodo() != null) {
            query.setParameter("zonId", consulta.getZonaIdNodo());
        }
        if (consulta.getDistritoNodo() != null && !consulta.getDistritoNodo().isEmpty()) {
            query.setParameter("disId", "%" + consulta.getDistritoNodo().toUpperCase() + "%");
        }
        if (consulta.getDivicionNodo() != null) {
            query.setParameter("divId", consulta.getDivicionNodo());
        }
        if (consulta.getAreaNodo() != null) {
            query.setParameter("areId", consulta.getAreaNodo());
        }
        if (consulta.getHeadendNodo() != null && !consulta.getHeadendNodo().isEmpty()) {
            query.setParameter("nodHeadEnd", "%" + consulta.getHeadendNodo().toUpperCase() + "%");
        }
        if (consulta.getCampoAdi1Nodo() != null && !consulta.getCampoAdi1Nodo().isEmpty()) {
            query.setParameter("nodCampoAdicional1", "%" + consulta.getCampoAdi1Nodo().toUpperCase() + "%");
        }
        if (consulta.getCampoAdi2Nodo() != null && !consulta.getCampoAdi2Nodo().isEmpty()) {
            query.setParameter("nodCampoAdicional2", "%" + consulta.getCampoAdi2Nodo().toUpperCase() + "%");
        }
        if (consulta.getCampoAdi3Nodo() != null && !consulta.getCampoAdi3Nodo().isEmpty()) {
            query.setParameter("nodCampoAdicional3", "%" + consulta.getCampoAdi3Nodo().toUpperCase() + "%");
        }
        if (consulta.getCampoAdi4Nodo() != null && !consulta.getCampoAdi4Nodo().isEmpty()) {
            query.setParameter("nodCampoAdicional4", "%" + consulta.getCampoAdi4Nodo().toUpperCase() + "%");
        }
        if (consulta.getCampoAdi5Nodo() != null && !consulta.getCampoAdi5Nodo().isEmpty()) {
            query.setParameter("nodCampoAdicional5", "%" + consulta.getCampoAdi5Nodo().toUpperCase() + "%");
        }

        if (consulta.getTipoNodo() != null && !consulta.getTipoNodo().isEmpty()) {
            query.setParameter("tipoNodo", "%" + consulta.getTipoNodo().toUpperCase() + "%");
        }

        if (consulta.getComIdNodo() != null && !consulta.getComIdNodo().isEmpty()) {
            query.setParameter("codigoRr", "%" + consulta.getComIdNodo().toUpperCase() + "%");
        }

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<NodoMgl>) query.getResultList();

        return resultList;
    }

    /**
     * Victor Bocanegra Metodo para consultar un nodo por el geo
     *
     * @param ot
     * @return NodoMgl
     * @throws ApplicationException
     */
    public NodoMgl consultaGeo(CmtOrdenTrabajoMgl ot) throws ApplicationException {

        DireccionMgl direccionMgl = null;
        NodoMgl nodoMglCober = null;
        NodoMglManager nodoMglManager = new NodoMglManager();
        String barrio;
        try {
            
            if (ot != null && ot.getVisitaTecnicaMglList() != null
                    && !ot.getVisitaTecnicaMglList().isEmpty()) {
                for (CmtVisitaTecnicaMgl cvtm : ot.getVisitaTecnicaMglList()) {
                    if (cvtm != null && cvtm.getListCmtSubEdificiosVt() != null
                            && !cvtm.getListCmtSubEdificiosVt().isEmpty()) {
                        for (CmtSubEdificiosVt csev : cvtm.getListCmtSubEdificiosVt()) {
                            if (csev != null && csev.getNodo() != null
                                    && !csev.getNodo().getNodNombre().isEmpty()) {
                                return csev.getNodo();
                            }
                        }

                    }
                }
            }

            
            
            if (ot != null && ot.getCmObj() != null && ot.getCmObj().getDireccionPrincipal() != null) {
                direccionMgl = ot.getCmObj().getDireccionPrincipal().getDireccionObj();
            }

            if (direccionMgl != null) {
                AddressRequest addressRequest = new AddressRequest();
                AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();

                if (direccionMgl.getUbicacion().getGpoIdObj().getGpoMultiorigen().equalsIgnoreCase("1")) {
                     barrio = direccionMgl.getDireccionDetallada().getBarrio();
                }else{
                     barrio = "-";
                }
                addressRequest.setState(ot.getCmObj().getDepartamento().getGpoNombre());
                addressRequest.setCity(ot.getCmObj().getMunicipio().getGpoNombre());
                addressRequest.setCodDaneVt(ot.getCmObj().getCentroPoblado().getGeoCodigoDane());
                addressRequest.setLevel(Constant.WS_ADDRESS_CONSULTA_COMPLETA);
                addressRequest.setAddress(direccionMgl.getDirFormatoIgac());
                addressRequest.setNeighborhood(barrio);

                AddressService addressService = addressEJBRemote.queryAddress(addressRequest);
                if (addressService == null || addressService.getIdaddress() == null) {
                    throw new ApplicationException("No se puede actualizar la cobertura, Fallo en Sitidata");
                } else {
                    if (ot.getBasicaIdTecnologia().getCodigoBasica().
                            equalsIgnoreCase(CmtCoverEnum.NODO_UNO.getCodigo())) {
                        nodoMglCober = nodoMglManager.findByCodigo(addressService.getNodoUno());
                    } else if (ot.getBasicaIdTecnologia().getCodigoBasica().
                            equalsIgnoreCase(CmtCoverEnum.NODO_DOS.getCodigo())) {
                        nodoMglCober = nodoMglManager.findByCodigo(addressService.getNodoDos());
                    } else if (ot.getBasicaIdTecnologia().getCodigoBasica().
                            equalsIgnoreCase(CmtCoverEnum.NODO_TRES.getCodigo())) {
                        nodoMglCober = nodoMglManager.findByCodigo(addressService.getNodoTres());
                    } else if (ot.getBasicaIdTecnologia().getCodigoBasica().
                            equalsIgnoreCase(CmtCoverEnum.NODO_DTH.getCodigo())) {
                        nodoMglCober = nodoMglManager.findByCodigo(addressService.getNodoDth());
                    } else if (ot.getBasicaIdTecnologia().getCodigoBasica().
                            equalsIgnoreCase(CmtCoverEnum.NODO_MOVIL.getCodigo())) {
                        nodoMglCober = nodoMglManager.findByCodigo(addressService.getNodoMovil());
                    } else if (ot.getBasicaIdTecnologia().getCodigoBasica().
                            equalsIgnoreCase(CmtCoverEnum.NODO_FTTH.getCodigo())) {
                        nodoMglCober = nodoMglManager.findByCodigo(addressService.getNodoFtth());
                    } else if (ot.getBasicaIdTecnologia().getCodigoBasica().
                            equalsIgnoreCase(CmtCoverEnum.NODO_WIFI.getCodigo())) {
                        nodoMglCober = nodoMglManager.findByCodigo(addressService.getNodoWifi());
                    } else if (ot.getBasicaIdTecnologia().getCodigoBasica().
                            equalsIgnoreCase(CmtCoverEnum.NODO_FOU.getCodigo())) {
                        nodoMglCober = nodoMglManager.findByCodigo(addressService.getNodoZonaUnifilar());
                    } else if (ot.getBasicaIdTecnologia().getCodigoBasica().
                            equalsIgnoreCase(CmtCoverEnum.NODO_RFO.getCodigo())) {
                        if (addressService.getNodoTres() != null
                                && !addressService.getNodoTres().isEmpty()) {
                            String subParteCodigo = addressService.getNodoTres().substring(1, addressService.getNodoTres().length());
                            String codigoFinal = "R" + subParteCodigo;
                            nodoMglCober = nodoMglManager.findByCodigo(codigoFinal);
                        } else if (addressService.getNodoZonaUnifilar() != null
                                && !addressService.getNodoZonaUnifilar().isEmpty()) {
                            String subParteCodigo = addressService.getNodoZonaUnifilar().
                                    substring(1, addressService.getNodoZonaUnifilar().length());
                            String codigoFinal = "R" + subParteCodigo;
                            nodoMglCober = nodoMglManager.findByCodigo(codigoFinal);
                        }
                    }
                }
            }

        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + ex.getMessage();
            LOGGER.error(msg);
        }

        return nodoMglCober;
    }

    private AddressEJBRemote lookupaddressEJBBean() throws ApplicationException {
        try {
            Context c = new InitialContext();
            return (AddressEJBRemote) c.lookup("addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote");
        } catch (NamingException ne) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + ne.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, ne);
        }catch (Exception ne) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + ne.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, ne);
        }
    }

    /**
     * Victor Bocanegra Metodo para consultar lista de codigos de nodo por
     * centro poblado
     *
     * @param centroPoblado
     * @return List<String>
     * @throws ApplicationException
     */
    public List<String> findCodigoNodoByCentroP(BigDecimal centroPoblado)
            throws ApplicationException {

        List<String> resultList;

        Query query = entityManager.createQuery("SELECT n.nodCodigo FROM NodoMgl n  "
                + "WHERE n.gpoId = :gpoId  AND n.estadoRegistro = 1  ORDER BY  n.nodCodigo ASC");

        if (centroPoblado != null) {
            query.setParameter("gpoId", centroPoblado);
        }

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<String>) query.getResultList();

        return resultList;
    }
    
    
    
    public List<NodoMgl> findNodosCiudadAndTecnoAndEstado(BigDecimal gpoId,
            CmtBasicaMgl nodTipo, CmtBasicaMgl estado) throws ApplicationException {
        try {
            if (gpoId == null || nodTipo == null || estado == null) {
                return null;
            }
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();
            List<NodoMgl> resulList;
            
            String sql = "SELECT n FROM NodoMgl n "
                    + " WHERE n.gpoId IN (SELECT g.gpoId FROM GeograficoPoliticoMgl g "
                    + " WHERE g.geoGpoId=:gpoId) "
                    + " AND n.nodTipo=:nodTipo "
                    + " AND n.estadoRegistro = 1 "
                    + " ORDER BY n.nodCodigo ASC";
            Query query = entityManager.createQuery(sql);
            query.setParameter("gpoId", gpoId);//gpoID es de ciudad
            query.setParameter("nodTipo", nodTipo);
            resulList = (List<NodoMgl>) query.getResultList();
            entityManager.getTransaction().commit();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    @SuppressWarnings("unchecked")
    /**
     * Busca un nodo NFI por comunidad
     *
     * @author Victor Bocanegra
     * @param comunidad del nodo
     * @param codigoNFI
     * @return nodo NFI encontrado.
     * @throws ApplicationException se hay error en la solicitud
     */
    public NodoMgl findNodoNFIByComunidad(CmtComunidadRr comunidadRr, String codigoNFI) {

        NodoMgl nodoNfi;

        try {

            List<NodoMgl> resultList;
            Query query = entityManager.createQuery("SELECT n FROM NodoMgl n  "
                    + " WHERE UPPER(n.nodCodigo) like :nodCodigo  "
                    + " AND n.comId = :comId "
                    + " AND n.estadoRegistro = 1");

            if (codigoNFI != null && !codigoNFI.isEmpty()) {
                query.setParameter("nodCodigo", "%" + codigoNFI.toUpperCase() + "%");
            }
            if (comunidadRr != null) {
                query.setParameter("comId", comunidadRr);
            }
            resultList = (List<NodoMgl>) query.getResultList();

            if (resultList != null && !resultList.isEmpty()) {
                nodoNfi = resultList.get(0);
            } else {
                nodoNfi = null;
            }

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
        return nodoNfi;
    }
    
    
    /**
     * cardenaslb Metodo para consultar lista de codigos de nodo por
     * centro poblado
     *
     * @param centroPoblado
     * @return List<String>
     * @throws ApplicationException
     */
    public List<NodoMgl> findCodigosByCentroPoblado(BigDecimal centroPoblado)
            throws ApplicationException {

        List<NodoMgl> resultList;

        Query query = entityManager.createQuery("SELECT n FROM NodoMgl n  "
                + "WHERE n.gpoId = :gpoId  AND n.estadoRegistro = 1  ORDER BY  n.nodCodigo ASC");

        if (centroPoblado != null) {
            query.setParameter("gpoId", centroPoblado);
        }

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<NodoMgl>) query.getResultList();

        return resultList;
    }
    
        /**
     * Juan David Hernandez Método para consultar solo 5 nodos del centro poblado
     *
     * @param centroPoblado
     * @return List<NodoMgl>
     * @throws ApplicationException
     */
    public List<NodoMgl> find5NodosByCentroPobladoList(BigDecimal centroPoblado)
            throws ApplicationException {

        List<NodoMgl> resultList;

        Query query = entityManager.createQuery("SELECT n FROM NodoMgl n  "
                + " WHERE n.gpoId = :gpoId  "
                + " AND n.estadoRegistro = 1 "
                + " AND n.divId is not null "
                + " ORDER BY n.nodId ASC ");

        query.setParameter("gpoId", centroPoblado);
        query.setFirstResult(1);
        query.setMaxResults(5);

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<NodoMgl>) query.getResultList();

        return resultList;
    }
  
    /**
     * *Bocanegra Vm Metodo para buscar el nodo por el codigo del nodo, codigo
     * dane de la ciudad y el id de la tecnologia
     *
     * @param codigo
     * @param codigodane
     * @param idTecnologia
     * @return
     * @throws ApplicationException
     */
    public NodoMgl findByCodigoNodoDaneAndTec(String codigo, String codigodane,
            BigDecimal idTecnologia) throws ApplicationException {
        try {
            NodoMgl result;
            Query query = entityManager.createQuery("SELECT n FROM NodoMgl n "
                    + " INNER JOIN GeograficoPoliticoMgl geo  ON  n.gpoId = geo.gpoId "
                    + " WHERE UPPER(n.nodCodigo) = :nodCodigo "
                    + " AND geo.geoCodigoDane = :geoCodigoDane "
                    + " AND n.nodTipo.basicaId = :idTecnologia ");

            query.setParameter("nodCodigo", codigo.toUpperCase());
            query.setParameter("geoCodigoDane", codigodane);
            query.setParameter("idTecnologia", idTecnologia);
            result = (NodoMgl) query.getSingleResult();
            return result;
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Dayver De la hoz
     * Se consulta la cantidad de registros que va a tener el exporte
     * @param consulta
     * @return
     * @throws ApplicationException
     */
    public Integer countByFiltro(CmtFiltroConsultaNodosDto consulta) throws ApplicationException {

        String queryTipo = "SELECT count(n) FROM NodoMgl n WHERE  1 = 1 ";

        if (consulta.getGeograficoPolitico() != null) {
            queryTipo += "AND  n.gpoId =  :gpoId ";
        }
        if (consulta.getCodigoNodo() != null && !consulta.getCodigoNodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodCodigo) LIKE :nodCodigo ";
        }
        if (consulta.getNombreNodo() != null && !consulta.getNombreNodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodNombre) LIKE :nodNombre ";
        }
        if (consulta.getFechaActivacion() != null && !consulta.getFechaActivacion().isEmpty()) {
            queryTipo += "AND UPPER(n.nodFechaActivacion) LIKE :nodFechaActivacion ";
        }
        if (consulta.getUnidadGestion() != null) {
            queryTipo += "AND n.ugeId = :ugeId ";
        }
        if (consulta.getZonaIdNodo() != null) {
            queryTipo += "AND n.zonId = :zonId ";
        }
        if (consulta.getDistritoNodo() != null && !consulta.getDistritoNodo().isEmpty()) {
            queryTipo += "AND UPPER(n.disId) LIKE :disId ";
        }
        if (consulta.getDivicionNodo() != null) {
            queryTipo += "AND n.divId = :divId ";
        }
        if (consulta.getAreaNodo() != null) {
            queryTipo += "AND n.areId = :areId ";
        }
        if (consulta.getHeadendNodo() != null && !consulta.getHeadendNodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodHeadEnd) LIKE :nodHeadEnd ";
        }
        if (consulta.getCampoAdi1Nodo() != null && !consulta.getCampoAdi1Nodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodCampoAdicional1) LIKE :nodCampoAdicional1 ";
        }
        if (consulta.getCampoAdi2Nodo() != null && !consulta.getCampoAdi2Nodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodCampoAdicional2) LIKE :nodCampoAdicional2 ";
        }
        if (consulta.getCampoAdi3Nodo() != null && !consulta.getCampoAdi3Nodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodCampoAdicional3) LIKE :nodCampoAdicional3 ";
        }
        if (consulta.getCampoAdi4Nodo() != null && !consulta.getCampoAdi4Nodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodCampoAdicional4) LIKE :nodCampoAdicional4 ";
        }
        if (consulta.getCampoAdi5Nodo() != null && !consulta.getCampoAdi5Nodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodCampoAdicional5) LIKE :nodCampoAdicional5 ";
        }
        if (consulta.getTipoNodo() != null && !consulta.getTipoNodo().isEmpty()) {
            queryTipo += "AND UPPER(n.nodTipo.nombreBasica) LIKE :tipoNodo ";
        }

        if (consulta.getComIdNodo() != null && !consulta.getComIdNodo().isEmpty()) {
            queryTipo += "AND UPPER(n.comId.codigoRr) LIKE :codigoRr ";
        }

        queryTipo += "AND n.estadoRegistro = 1  ORDER BY n.nodCodigo ASC ";

        Query query = entityManager.createQuery(queryTipo);

        if (consulta.getGeograficoPolitico() != null) {
            query.setParameter("gpoId", consulta.getGeograficoPolitico());
        }
        if (consulta.getCodigoNodo() != null && !consulta.getCodigoNodo().isEmpty()) {
            query.setParameter("nodCodigo", "%" + consulta.getCodigoNodo().toUpperCase() + "%");
        }
        if (consulta.getNombreNodo() != null && !consulta.getNombreNodo().isEmpty()) {
            query.setParameter("nodNombre", "%" + consulta.getNombreNodo().toUpperCase() + "%");
        }
        if (consulta.getFechaActivacion() != null && !consulta.getFechaActivacion().isEmpty()) {
            query.setParameter("nodFechaActivacion", "%" + consulta.getFechaActivacion().toUpperCase() + "%");
        }
        if (consulta.getUnidadGestion() != null) {
            query.setParameter("ugeId", consulta.getUnidadGestion());
        }
        if (consulta.getZonaIdNodo() != null) {
            query.setParameter("zonId", consulta.getZonaIdNodo());
        }
        if (consulta.getDistritoNodo() != null && !consulta.getDistritoNodo().isEmpty()) {
            query.setParameter("disId", "%" + consulta.getDistritoNodo().toUpperCase() + "%");
        }
        if (consulta.getDivicionNodo() != null) {
            query.setParameter("divId", consulta.getDivicionNodo());
        }
        if (consulta.getAreaNodo() != null) {
            query.setParameter("areId", consulta.getAreaNodo());
        }
        if (consulta.getHeadendNodo() != null && !consulta.getHeadendNodo().isEmpty()) {
            query.setParameter("nodHeadEnd", "%" + consulta.getHeadendNodo().toUpperCase() + "%");
        }
        if (consulta.getCampoAdi1Nodo() != null && !consulta.getCampoAdi1Nodo().isEmpty()) {
            query.setParameter("nodCampoAdicional1", "%" + consulta.getCampoAdi1Nodo().toUpperCase() + "%");
        }
        if (consulta.getCampoAdi2Nodo() != null && !consulta.getCampoAdi2Nodo().isEmpty()) {
            query.setParameter("nodCampoAdicional2", "%" + consulta.getCampoAdi2Nodo().toUpperCase() + "%");
        }
        if (consulta.getCampoAdi3Nodo() != null && !consulta.getCampoAdi3Nodo().isEmpty()) {
            query.setParameter("nodCampoAdicional3", "%" + consulta.getCampoAdi3Nodo().toUpperCase() + "%");
        }
        if (consulta.getCampoAdi4Nodo() != null && !consulta.getCampoAdi4Nodo().isEmpty()) {
            query.setParameter("nodCampoAdicional4", "%" + consulta.getCampoAdi4Nodo().toUpperCase() + "%");
        }
        if (consulta.getCampoAdi5Nodo() != null && !consulta.getCampoAdi5Nodo().isEmpty()) {
            query.setParameter("nodCampoAdicional5", "%" + consulta.getCampoAdi5Nodo().toUpperCase() + "%");
        }

        if (consulta.getTipoNodo() != null && !consulta.getTipoNodo().isEmpty()) {
            query.setParameter("tipoNodo", "%" + consulta.getTipoNodo().toUpperCase() + "%");
        }

        if (consulta.getComIdNodo() != null && !consulta.getComIdNodo().isEmpty()) {
            query.setParameter("codigoRr", "%" + consulta.getComIdNodo().toUpperCase() + "%");
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return ((Long) query.getSingleResult()).intValue();
    }

    /**
     * Dayver De la hoz
     * Metodo para buscar los nodos con todas las columnas requeridas por el exporte
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @return List<Object[]> Lista de objetos se parsea a lista de NodoMerDto
     * @throws ApplicationException
     */
    public List<Object[]> findByFiltroMun(int paginaSelected,
                                            int maxResults, CmtFiltroConsultaNodosDto consulta) throws ApplicationException {

        try {
            String queryTipo = "SELECT * FROM \n" +
                    "(SELECT a.*, ROWNUM rnum  FROM \n" +
                    "(SELECT ND.NODO_ID \"0\", ND.COSTO_RED \"1\", ND.ESTADO_REGISTRO \"2\", ND.FACTIBILIDAD \"3\", ND.GEOGRAFICO_POLITICO_ID \"4\", ND.LIMITES \"5\", ND.CAMPO_ADICIONAL1 \"6\", ND.CAMPO_ADICIONAL2 \"7\", \n" +
                    "ND.CAMPO_ADICIONAL3 \"8\", ND.CAMPO_ADICIONAL4 \"9\", ND.CAMPO_ADICIONAL5 \"10\", ND.CODIGO \"11\", ND.FECHA_ACTIVACION \"12\", ND.FECHA_CREACION \"13\", ND.FECHA_MODIFICACION \"14\", ND.HEAD_END \"15\", ND.NOMBRE \"16\", \n" +
                    "ND.USUARIO_CREACION \"17\", ND.USUARIO_MODIFICACION \"18\", ND.PERFIL_CREACION \"19\", ND.PERFIL_EDICION \"20\", ND.PROYECTO \"21\", ND.ALIADO_ID \"22\", MB.NOMBRE_BASICA \"23\", ND.COM_ID \"24\", MB2.NOMBRE_BASICA \"25\", \n" +
                    "ND.DIVISIONAL_BASICA_ID \"26\", MB5.NOMBRE_BASICA \"27\", MB6.NOMBRE_BASICA \"28\", MB4.NOMBRE_BASICA \"29\", MB3.NOMBRE_BASICA \"30\", CP.NOMBRE as \"31\", MU.NOMBRE as \"32\", DP.NOMBRE as \"33\",\n" +
                    "cm.nombre_comunidad \"34\", cr.nombre_regional \"35\" \n" +
                    "FROM " + Constant.MGL_DATABASE_SCHEMA + "." + "TEC_NODO ND, " + Constant.MGL_DATABASE_SCHEMA + "." + "MGL_GEOGRAFICO_POLITICO CP, \n" +
                    Constant.MGL_DATABASE_SCHEMA + "." + "MGL_GEOGRAFICO_POLITICO MU, " + Constant.MGL_DATABASE_SCHEMA + "." + "MGL_GEOGRAFICO_POLITICO DP, \n" +
                    Constant.MGL_DATABASE_SCHEMA + "." + "CMT_COMUNIDAD_RR CM, " + Constant.MGL_DATABASE_SCHEMA + "." + "CMT_REGIONAL_RR CR, \n" +
                    Constant.MGL_DATABASE_SCHEMA + "." + "MGL_BASICA MB, " + Constant.MGL_DATABASE_SCHEMA + "." + "MGL_BASICA MB2, " + Constant.MGL_DATABASE_SCHEMA + "." + "MGL_BASICA MB3, \n" +
                    Constant.MGL_DATABASE_SCHEMA + "." + "MGL_BASICA MB4, " + Constant.MGL_DATABASE_SCHEMA + "." + "MGL_BASICA MB5, " + Constant.MGL_DATABASE_SCHEMA + "." + "MGL_BASICA MB6 \n" +
                    "WHERE 1 = 1 AND " +
                    " (ND.GEOGRAFICO_POLITICO_ID = CP.GEOGRAFICO_POLITICO_ID) AND (CP.GEOGRAFICO_GEO_POLITICO_ID = MU.GEOGRAFICO_POLITICO_ID) AND (MU.GEOGRAFICO_GEO_POLITICO_ID = DP.GEOGRAFICO_POLITICO_ID) " +
                    " AND (ND.COM_ID = CM.COMUNIDAD_RR_ID) AND (CM.regional_rr_id = cr.regional_rr_id) AND (ND.AREA_BASICA_ID = MB.basica_id) AND (ND.DISTRITO_BASICA_ID = MB2.basica_id)\n" +
                    " AND (ND.ZONA_BASICA_ID = MB3.basica_id) AND (ND.UNIDAD_GESTION_BASICA_ID = MB4.basica_id) AND (ND.ESTADO = MB5.basica_id) AND (ND.TIPO = MB6.basica_id) ";

            if (consulta.getGeograficoPolitico() != null) {
                queryTipo += "AND ND.GEOGRAFICO_POLITICO_ID = ? ";
            }
            if (consulta.getCodigoNodo() != null && !consulta.getCodigoNodo().isEmpty()) {
                queryTipo += "AND UPPER(ND.CODIGO) LIKE ? ";
            }
            if (consulta.getNombreNodo() != null && !consulta.getNombreNodo().isEmpty()) {
                queryTipo += "AND UPPER(ND.NOMBRE) LIKE ? ";
            }
            if (consulta.getFechaActivacion() != null && !consulta.getFechaActivacion().isEmpty()) {
                queryTipo += "AND UPPER(ND.FECHA_ACTIVACION) LIKE ? ";
            }
            if (consulta.getUnidadGestion() != null) {
                queryTipo += "AND ND.UNIDAD_GESTION_BASICA_ID = ? ";
            }
            if (consulta.getZonaIdNodo() != null) {
                queryTipo += "AND ND.DISTRITO_BASICA_ID = ? ";
            }
            if (consulta.getDistritoNodo() != null && !consulta.getDistritoNodo().isEmpty()) {
                queryTipo += "AND UPPER(ND.DISTRITO_BASICA_ID) LIKE ? ";
            }
            if (consulta.getDivicionNodo() != null) {
                queryTipo += "AND ND.DIVISIONAL_BASICA_ID = ? ";
            }
            if (consulta.getAreaNodo() != null) {
                queryTipo += "AND ND.AREA_BASICA_ID = ? ";
            }
            if (consulta.getHeadendNodo() != null && !consulta.getHeadendNodo().isEmpty()) {
                queryTipo += "AND UPPER(ND.HEAD_END) LIKE ? ";
            }
            if (consulta.getCampoAdi1Nodo() != null && !consulta.getCampoAdi1Nodo().isEmpty()) {
                queryTipo += "AND UPPER(ND.CAMPO_ADICIONAL1) LIKE ? ";
            }
            if (consulta.getCampoAdi2Nodo() != null && !consulta.getCampoAdi2Nodo().isEmpty()) {
                queryTipo += "AND UPPER(ND.CAMPO_ADICIONAL2) LIKE ? ";
            }
            if (consulta.getCampoAdi3Nodo() != null && !consulta.getCampoAdi3Nodo().isEmpty()) {
                queryTipo += "AND UPPER(ND.CAMPO_ADICIONAL3) LIKE ? ";
            }
            if (consulta.getCampoAdi4Nodo() != null && !consulta.getCampoAdi4Nodo().isEmpty()) {
                queryTipo += "AND UPPER(ND.CAMPO_ADICIONAL4) LIKE ? ";
            }
            if (consulta.getCampoAdi5Nodo() != null && !consulta.getCampoAdi5Nodo().isEmpty()) {
                queryTipo += "AND UPPER(ND.CAMPO_ADICIONAL5) LIKE ? ";
            }
            if (consulta.getTipoNodo() != null && !consulta.getTipoNodo().isEmpty()) {
                queryTipo += "AND UPPER(n.nodTipo.nombreBasica) LIKE ? ";
            }

            if (consulta.getComIdNodo() != null && !consulta.getComIdNodo().isEmpty()) {
                queryTipo += "AND UPPER(n.comId.codigoRr) LIKE ? ";
            }

            queryTipo += " AND ND.estado_Registro = 1 ORDER BY ND.CODIGO ASC) a " +
                    " WHERE ROWNUM <= ?) WHERE rnum > ? ";

            Query query = entityManager.createNativeQuery(queryTipo);

            int parameter = 1;
            if (consulta.getGeograficoPolitico() != null) {
                query.setParameter(parameter++, consulta.getGeograficoPolitico());
            }
            if (consulta.getCodigoNodo() != null && !consulta.getCodigoNodo().isEmpty()) {
                query.setParameter(parameter++, "%" + consulta.getCodigoNodo().toUpperCase() + "%");
            }
            if (consulta.getNombreNodo() != null && !consulta.getNombreNodo().isEmpty()) {
                query.setParameter(parameter++, "%" + consulta.getNombreNodo().toUpperCase() + "%");
            }
            if (consulta.getFechaActivacion() != null && !consulta.getFechaActivacion().isEmpty()) {
                query.setParameter(parameter++, "%" + consulta.getFechaActivacion().toUpperCase() + "%");
            }
            if (consulta.getUnidadGestion() != null) {
                query.setParameter(parameter++, consulta.getUnidadGestion());
            }
            if (consulta.getZonaIdNodo() != null) {
                query.setParameter(parameter++, consulta.getZonaIdNodo());
            }
            if (consulta.getDistritoNodo() != null && !consulta.getDistritoNodo().isEmpty()) {
                query.setParameter(parameter++, "%" + consulta.getDistritoNodo().toUpperCase() + "%");
            }
            if (consulta.getDivicionNodo() != null) {
                query.setParameter(parameter++, consulta.getDivicionNodo());
            }
            if (consulta.getAreaNodo() != null) {
                query.setParameter(parameter++, consulta.getAreaNodo());
            }
            if (consulta.getHeadendNodo() != null && !consulta.getHeadendNodo().isEmpty()) {
                query.setParameter(parameter++, "%" + consulta.getHeadendNodo().toUpperCase() + "%");
            }
            if (consulta.getCampoAdi1Nodo() != null && !consulta.getCampoAdi1Nodo().isEmpty()) {
                query.setParameter(parameter++, "%" + consulta.getCampoAdi1Nodo().toUpperCase() + "%");
            }
            if (consulta.getCampoAdi2Nodo() != null && !consulta.getCampoAdi2Nodo().isEmpty()) {
                query.setParameter(parameter++, "%" + consulta.getCampoAdi2Nodo().toUpperCase() + "%");
            }
            if (consulta.getCampoAdi3Nodo() != null && !consulta.getCampoAdi3Nodo().isEmpty()) {
                query.setParameter(parameter++, "%" + consulta.getCampoAdi3Nodo().toUpperCase() + "%");
            }
            if (consulta.getCampoAdi4Nodo() != null && !consulta.getCampoAdi4Nodo().isEmpty()) {
                query.setParameter(parameter++, "%" + consulta.getCampoAdi4Nodo().toUpperCase() + "%");
            }
            if (consulta.getCampoAdi5Nodo() != null && !consulta.getCampoAdi5Nodo().isEmpty()) {
                query.setParameter(parameter++, "%" + consulta.getCampoAdi5Nodo().toUpperCase() + "%");
            }

            if (consulta.getTipoNodo() != null && !consulta.getTipoNodo().isEmpty()) {
                query.setParameter(parameter++, "%" + consulta.getTipoNodo().toUpperCase() + "%");
            }

            if (consulta.getComIdNodo() != null && !consulta.getComIdNodo().isEmpty()) {
                query.setParameter(parameter++, "%" + consulta.getComIdNodo().toUpperCase() + "%");
            }

            query.setParameter(parameter++, maxResults);
            query.setParameter(parameter++, paginaSelected);

            List<Object[]> listaR = query.getResultList();
            return listaR;
        }catch (Exception e){
            throw new ApplicationException(e);
        }
    }

    public List<NodoEstadoDTO> consultarEstadoNodos() {
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtils.getEntityManager(this.getClass().getName());
            StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery(CONSULTA_STADO_NODO_SP);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CURSOR", void.class, ParameterMode.REF_CURSOR);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CODIGO", Integer.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_RESULTADO", String.class, ParameterMode.OUT);

            storedProcedureQuery.execute();
            List<Object[]> listaEstadoNodoResult = storedProcedureQuery.getResultList();
            List<NodoEstadoDTO> estadoNodos = new ArrayList<>();

            if (!listaEstadoNodoResult.isEmpty()) {
                listaEstadoNodoResult.forEach(r -> estadoNodos.add(NodoEstadoDTO.builder().nombre((String) r[0]).build()));
            }

            return estadoNodos;

        } catch (NoResultException ex) {
            LOGGER.error("Excepcion en el Procedimiento almacenado "+ CONSULTA_STADO_NODO_SP + ex);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en el Procedimiento almacenado " + CONSULTA_STADO_NODO_SP + e);
            return null;
        } finally {
            if (Objects.nonNull(entityManager) && entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }
    }

}
