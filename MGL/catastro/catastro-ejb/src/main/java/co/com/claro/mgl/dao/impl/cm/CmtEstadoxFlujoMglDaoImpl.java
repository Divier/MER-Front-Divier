/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaEstadosxFlujoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 * Dao Estados del Flujo de un tipo de Orden de Trabajo. Contiene la logica de
 * acceso a datos a los estados del flujo de un tipo de ordenes de trabajo en el
 * repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtEstadoxFlujoMglDaoImpl extends GenericDaoImpl<CmtEstadoxFlujoMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtEstadoxFlujoMglDaoImpl.class);

    /**
     * Obtiene el estado inicial del flujo.Permite obtener el estado incial del
 flujo.
     *
     * @author Johnnatan Ortiz
     * @param tipoFlujoOt Tipo de Flujo
     * @param tegnologia
     * @return Estado Inicial del Tipo de Flujo
     * @throws ApplicationException
     */
    public CmtEstadoxFlujoMgl getEstadoInicialFlujo(CmtBasicaMgl tipoFlujoOt, CmtBasicaMgl tegnologia)
            throws ApplicationException {
        try {
            CmtEstadoxFlujoMgl result;
            Query query = entityManager.createNamedQuery("CmtEstadoxFlujoMgl.findEstadoIniTipoFlujo");
            query.setParameter("tipoFlujoOt", tipoFlujoOt);
            query.setParameter("esEstadoInicial", "Y");
            query.setParameter("tecnologia", tegnologia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            result = (CmtEstadoxFlujoMgl) query.getSingleResult();
            return result;
        } catch (NonUniqueResultException ex) {
            LOGGER.error("Se encontro mas de un estado incial para la tecnologia: ", ex);
            throw new ApplicationException("Se encontró más de un estado incial"
                    + " para la tecnología: "+tegnologia+"", ex);
        } catch (NoResultException ex) {
            String msg = "No se encontró un estado incial para la tecnología: "+tegnologia+": " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontró un estado incial para la tecnología: "+tegnologia+" :" + ex.getMessage(), ex);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de consultar el estado inicial del flujo '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Se produjo un error al momento de consultar el estado inicial del flujo: " + ex.getMessage(), ex);
        }
    }

    /**
     * Obtiene los estados de un tipo de flujo.Permite obtener los estados de
 un tipo de flujo.
     *
     * @author Johnnatan Ortiz
     * @param tipoFlujoOt Tipo de Flujo
     * @param tegnologia
     * @return Estados del Tipo de Flujo
     * @throws ApplicationException
     */
    public List<CmtEstadoxFlujoMgl> getEstadosByTipoFlujo(CmtBasicaMgl tipoFlujoOt, CmtBasicaMgl tegnologia)
            throws ApplicationException {
        try {
            List<CmtEstadoxFlujoMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtEstadoxFlujoMgl.findByTipoFlujo");
            query.setParameter("tipoFlujoOt", tipoFlujoOt);
            query.setParameter("tecnologia", tegnologia);
            resultList = (List<CmtEstadoxFlujoMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            throw new ApplicationException("Error al momento de consultar los estados por tipo flujo. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene un estado de un tipo de FLujo.Permite obtener un estados de un
 tipo de flujo.
     *
     * @author Johnnatan Ortiz
     * @param tipoFlujoOt Tipo de Flujo
     * @param estadoInterno Estado Interno
     * @param tegnologia
     * @return Estado del Tipo de Flujo
     * @throws ApplicationException
     */
    public CmtEstadoxFlujoMgl findByTipoFlujoAndEstadoInt(CmtBasicaMgl tipoFlujoOt,
            CmtBasicaMgl estadoInterno, CmtBasicaMgl tegnologia) throws ApplicationException {

        CmtEstadoxFlujoMgl result = null;
        try {
            Query query = entityManager.createNamedQuery("CmtEstadoxFlujoMgl.findByTipoFlujoAndEstadoInt");
            query.setParameter("tipoFlujoOt", tipoFlujoOt);
            query.setParameter("estadoInterno", estadoInterno);
            query.setParameter("tecnologia", tegnologia);
            result = query.getResultList().size() == 0 ? null : (CmtEstadoxFlujoMgl) query.getSingleResult();
        } catch (NonUniqueResultException ex) {
            LOGGER.error("Se encontro mas de un estado final para la tecnologia: ", ex);
            throw new ApplicationException("Se encontro mas de un estado incial para la tecnologia:", ex);
        } catch (NoResultException nre) {
            LOGGER.error("No se encontro un estado final para la tecnologia: ", nre);
            throw new ApplicationException("No se encontro un estado incial para la tecnologia:", nre);                    
        } catch (Exception e) {
            throw new ApplicationException("Error al momento de consultar los estados por tipo y estado interno de flujo. EX000: " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * Obtiene los estados X flujo por ROL. Permite obtener los estados X flujo
     * por una lista de roles.
     *
     * @author Johnnatan Ortiz
     * @param gestionRolList lista de roles
     * @return estados X flujo
     * @throws ApplicationException
     */
    public List<CmtEstadoxFlujoMgl> findByInGestionRol(ArrayList<String> gestionRolList)
            throws ApplicationException {

        List<CmtEstadoxFlujoMgl> result = null;
        try {
            Query query = entityManager.createNamedQuery("CmtEstadoxFlujoMgl.findByInGestionRol");
            query.setParameter("gestionRolList", gestionRolList);
            result = (List<CmtEstadoxFlujoMgl>) query.getResultList();
        } catch (Exception e) {
            throw new ApplicationException("Error al momento de consultar los estados por rol gestionador. EX000: " + e.getMessage(), e);
        }
        return result;
    }

    
    public FiltroConsultaEstadosxFlujoDto findTablasTipoBasicaSearch(HashMap<String, Object> params, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        FiltroConsultaEstadosxFlujoDto filtroConsultaEstadosxFlujoDto = new FiltroConsultaEstadosxFlujoDto();
        if (contar) {
            filtroConsultaEstadosxFlujoDto.setNumRegistros(findTablasEstadoxFlujoContar(params));
        } else {
            filtroConsultaEstadosxFlujoDto.setListaCmtEstadoxFlujoMgl(findTablasEstadoxFlujoSearchData(params, firstResult, maxResults));
        }
        return filtroConsultaEstadosxFlujoDto;
    }

   public long findTablasEstadoxFlujoContar(HashMap<String, Object> params) throws ApplicationException {
        try {
             Long resultCount = 0L;
             String queryTipo;

               queryTipo = "SELECT COUNT(1) FROM CmtEstadoxFlujoMgl c "
                         + "WHERE  c.estadoRegistro = 1  ";
           
            if (params.get("tipoFlujoOtObj") != null && !params.get("tipoFlujoOtObj").toString().trim().isEmpty()) {
                queryTipo += "AND UPPER (c.tipoFlujoOtObj.nombreBasica) LIKE :tipoFlujo  ";
            }
            if (params.get("tecnologia") != null && !params.get("tecnologia").toString().trim().isEmpty()) {
                queryTipo += "AND UPPER (c.basicaTecnologia.nombreBasica) LIKE :tecnologia  ";
            }
            if (params.get("subTipoOrden") != null && !params.get("subTipoOrden").toString().trim().isEmpty()) {
                queryTipo += "AND UPPER (c.subTipoOrdenOFSC.nombreBasica) LIKE :subTipoOrden  ";
            }

            Query query = entityManager.createQuery(queryTipo);

            if (params.get("tipoFlujoOtObj") != null && !params.get("tipoFlujoOtObj").toString().trim().isEmpty()) {
                query.setParameter("tipoFlujo", "%" + params.get("tipoFlujoOtObj").toString().trim().toUpperCase() + "%");
            }
            if (params.get("tecnologia") != null && !params.get("tecnologia").toString().trim().isEmpty()) {
                query.setParameter("tecnologia", "%" + params.get("tecnologia").toString().trim().toUpperCase() + "%");
            }
            if (params.get("subTipoOrden") != null && !params.get("subTipoOrden").toString().trim().isEmpty()) {
                query.setParameter("subTipoOrden", "%" + params.get("subTipoOrden").toString().trim().toUpperCase() + "%");
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultCount = (Long) query.getSingleResult();
            return resultCount;

        } catch (NoResultException nre) {
            LOGGER.error("No se encontraron resultados para la consulta.", nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.", nre);
        } catch (Exception e) {
            LOGGER.error("Error al momento de realizar la consulta findTablasEstadoxFlujoContar: " + e.getMessage(), e);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "la información: " + e.getMessage(), e);
        }
    }

    public List<CmtEstadoxFlujoMgl> findTablasEstadoxFlujoSearchData(HashMap<String, Object> params, int firstResult, int maxResults) throws ApplicationException {
        try {


            String queryTipo = "SELECT n FROM CmtEstadoxFlujoMgl n WHERE n.estadoRegistro = 1  ";

            if (params.get("tipoFlujoOtObj") != null && !params.get("tipoFlujoOtObj").toString().trim().isEmpty()) {
                queryTipo += "AND  UPPER (n.tipoFlujoOtObj.nombreBasica) like :tipoFlujo ";
            }
            if (params.get("tecnologia") != null && !params.get("tecnologia").toString().trim().isEmpty()) {
                queryTipo += "AND  UPPER (n.basicaTecnologia.nombreBasica) like :tecnologia ";
            }
            if (params.get("subTipoOrden") != null && !params.get("subTipoOrden").toString().trim().isEmpty()) {
                queryTipo += "AND  UPPER (n.subTipoOrdenOFSC.nombreBasica) like :subTipoOrden ";
            }

            Query q = entityManager.createQuery(queryTipo);

            if (params.get("tipoFlujoOtObj") != null && !params.get("tipoFlujoOtObj").toString().trim().isEmpty()) {
                q.setParameter("tipoFlujo", "%" + params.get("tipoFlujoOtObj").toString().trim().toUpperCase() + "%");
            }
            if (params.get("tecnologia") != null && !params.get("tecnologia").toString().trim().isEmpty()) {
                q.setParameter("tecnologia", "%" + params.get("tecnologia").toString().trim().toUpperCase() + "%");
            }
            if (params.get("subTipoOrden") != null && !params.get("subTipoOrden").toString().trim().isEmpty()) {
                q.setParameter("subTipoOrden", "%" + params.get("subTipoOrden").toString().trim().toUpperCase() + "%");
            }

            q.setFirstResult(firstResult);
            q.setMaxResults(maxResults);

            return q.getResultList();
        } catch (NoResultException nre) {
            LOGGER.error("No se encontraron resultados para la consulta: " + nre.getMessage());
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.", nre);
        } catch (Exception e) {
            LOGGER.error("Se presentó un error al consultar la información: " + e.getMessage(), e);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "la información: " + e.getMessage(), e);
        }

    }
    
    public List<CmtEstadoxFlujoMgl> findAll(int firstResult, int maxResults) throws ApplicationException {
        try {
            Query q = entityManager.createNamedQuery("CmtEstadoxFlujoMgl.findAll");
            q.setFirstResult(firstResult);
            q.setMaxResults(maxResults);

            return q.getResultList();
        } catch (NoResultException nre) {
            LOGGER.error("No se encontraron resultados para la consulta: ", nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.", nre);
        } catch (Exception e) {
            LOGGER.error("Se presentó un error al consultar la información: " + e.getMessage(), e);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "la información: " + e.getMessage(), e);
        }

    }
    
       /**
     * Obtiene un estado de un tipo de FLujo. Permite obtener un estados de un
     * tipo de flujo.
     *
     * @author Victor Bocanegra 
     * @param id Tipo de Flujo
     * @return Estado del Tipo de Flujo
     * @throws ApplicationException
     */
    public CmtEstadoxFlujoMgl findById(BigDecimal id) throws ApplicationException {

        CmtEstadoxFlujoMgl result = null;
        try {
            Query query = entityManager.createNamedQuery("CmtEstadoxFlujoMgl.findId");
            query.setParameter("estadoxFlujoId", id);
            
            result = (CmtEstadoxFlujoMgl) query.getSingleResult();
        } catch (NonUniqueResultException ex) {
            LOGGER.error("Se encontro mas de un estado final: ", ex);
            throw new ApplicationException("Se encontro mas de un estado incial :", ex);
        } catch (NoResultException nre) {
            LOGGER.error("No se encontro un estado final: ", nre);
            throw new ApplicationException("No se encontro un estado incial:", nre);                    
        } catch (Exception e) {
            throw new ApplicationException("Error al momento de consultar el estado por flujo. EX000: " + e.getMessage(), e);
        }
        return result;
    }
    
    
        
    
     /**
     * Valia si existen configuraciones duplicadas
     * tipo de flujo.
     *
     * @author Lenis Cardenas
     * @param params
     * @return Estado del Tipo de Flujo
     * @throws ApplicationException
     */
    
        public List<CmtEstadoxFlujoMgl> findByAllFields(HashMap<String, Object> params) throws ApplicationException {
        
        try {
            String queryTipo = "SELECT n FROM CmtEstadoxFlujoMgl n WHERE n.estadoRegistro = 1  ";

            if (params.get("estadoInternoObj") != null ) {
                queryTipo += "AND n.estadoInternoObj.basicaId = :estadoInterno ";
            }
            if (params.get("tipoFlujoOtObj") != null ) {
                queryTipo += "AND  n.tipoFlujoOtObj.basicaId = :tipoFlujoOt ";
            }
            if (params.get("cambiaCmEstadoObj") != null ) {
                queryTipo += "AND  n.cambiaCmEstadoObj.basicaId = :estadoCm ";
            }
            if (params.get("basicaTecnologia") != null ) {
                queryTipo += "AND  n.basicaTecnologia.basicaId = :tecnologia ";
            }
            if (params.get("formulario") != null ) {
                queryTipo += "AND  n.formulario.basicaId = :form ";
            }
            if (params.get("esEstadoInicial") != null ) {
                queryTipo += "AND  n.esEstadoInicial = :estadoIni ";
            }
            if (params.get("gestionRol") != null && !params.get("gestionRol").toString().isEmpty() ) {
                queryTipo += "AND  n.gestionRol = :rol ";
            }
            if (params.get("subTipoOrdenOFSC") != null ) {
                queryTipo += "AND  n.subTipoOrdenOFSC.basicaId = :ofsc ";
            }else{
                  queryTipo += "AND  n.subTipoOrdenOFSC is null ";
            }
             if (params.get("diasAMostrarAgenda") != null && !params.get("diasAMostrarAgenda").toString().trim().isEmpty()) {
                queryTipo += "AND  n.diasAMostrarAgenda = :dias ";
            }
             else{
                  queryTipo += "AND  n.diasAMostrarAgenda is null ";
            }
             
             if (params.get("tipoOTRR") != null && !params.get("tipoOTRR").toString().trim().isEmpty()){
                 queryTipo += "AND  n.tipoTrabajoRR.basicaId = :tipoTrabajoRR ";
             }else{
                  queryTipo += "AND  n.tipoTrabajoRR is null ";
             }
             
             if (params.get("estadoInicialOTRR") != null && !params.get("estadoInicialOTRR").toString().trim().isEmpty()){
                 queryTipo += "AND  n.estadoOtRRInicial.basicaId = :estadoInicialOTRR ";
             }else{
                 queryTipo += "AND  n.estadoOtRRInicial is null ";
             }
             
             if (params.get("estadoCierreOTRR") != null && !params.get("estadoCierreOTRR").toString().trim().isEmpty()){
                 queryTipo += "AND  n.estadoOtRRFinal.basicaId = :estadoCierreOTRR ";
             }else{
                  queryTipo += "AND  n.estadoOtRRFinal is null ";
             }
             
             if (params.get("tiempoAgendaOfsc") != null && !params.get("tiempoAgendaOfsc").toString().trim().isEmpty()) {
                queryTipo += "AND  n.tiempoAgendaOfsc = :tiempoAgendaOfsc ";
            }
             else{
                  queryTipo += "AND  n.tiempoAgendaOfsc is null ";
            }

            Query q = entityManager.createQuery(queryTipo);

            if (params.get("estadoInternoObj") != null && !params.get("estadoInternoObj").toString().trim().isEmpty()) {
                q.setParameter("estadoInterno", new BigDecimal(params.get("estadoInternoObj").toString()));
            }
            if (params.get("tipoFlujoOtObj") != null && !params.get("tipoFlujoOtObj").toString().trim().isEmpty()) {
                q.setParameter("tipoFlujoOt",  new BigDecimal(params.get("tipoFlujoOtObj").toString()));
            }
            if (params.get("cambiaCmEstadoObj") != null && !params.get("cambiaCmEstadoObj").toString().trim().isEmpty()) {
                q.setParameter("estadoCm",  new BigDecimal(params.get("cambiaCmEstadoObj").toString()));
            }
            if (params.get("basicaTecnologia") != null && !params.get("basicaTecnologia").toString().trim().isEmpty()) {
                q.setParameter("tecnologia",   new BigDecimal(params.get("basicaTecnologia").toString()));
            }
            if (params.get("formulario") != null && !params.get("formulario").toString().trim().isEmpty()) {
                q.setParameter("form", new BigDecimal(params.get("formulario").toString()));
            }
            if (params.get("esEstadoInicial") != null && !params.get("esEstadoInicial").toString().trim().isEmpty()) {
                q.setParameter("estadoIni",  params.get("esEstadoInicial").toString() );
            }
            if (params.get("gestionRol") != null && !params.get("gestionRol").toString().trim().isEmpty()) {
                q.setParameter("rol", (params.get("gestionRol").toString()) );
            }
            if (params.get("subTipoOrdenOFSC") != null && !params.get("subTipoOrdenOFSC").toString().trim().isEmpty()) {
                q.setParameter("ofsc",  new BigDecimal(params.get("subTipoOrdenOFSC").toString()));
            }
            if (params.get("diasAMostrarAgenda") != null && !params.get("diasAMostrarAgenda").toString().trim().isEmpty()) {
                q.setParameter("dias",  new Integer(params.get("diasAMostrarAgenda").toString()));
            }
            
            if (params.get("tipoOTRR") != null && !params.get("tipoOTRR").toString().trim().isEmpty()) {
                q.setParameter("tipoTrabajoRR",  new BigDecimal(params.get("tipoOTRR").toString()));
            }
            if (params.get("estadoInicialOTRR") != null && !params.get("estadoInicialOTRR").toString().trim().isEmpty()) {
                q.setParameter("estadoInicialOTRR",  new BigDecimal(params.get("estadoInicialOTRR").toString()));
            }
            if (params.get("estadoCierreOTRR") != null && !params.get("estadoCierreOTRR").toString().trim().isEmpty()) {
                q.setParameter("estadoCierreOTRR",  new BigDecimal(params.get("estadoCierreOTRR").toString()));
            }
            
            if (params.get("tiempoAgendaOfsc") != null && !params.get("tiempoAgendaOfsc").toString().trim().isEmpty()) {
                q.setParameter("tiempoAgendaOfsc",  new Integer(params.get("tiempoAgendaOfsc").toString()));
            }

            q.setHint("javax.persistence.cache.storeMode", "REFRESH");

            return q.getResultList();
            
        } catch (NumberFormatException e) {
            throw new ApplicationException(e.getMessage(), e);
        }
    }
        
    /**
     * Obtiene los estados del tipo de flujo ot Vt con estados internos de
     * cierre.
     *
     * @author Victor Bocanegra Ortiz
     * @param tipoFlujoOt Tipo de Flujo
     * @param tegnologia
     * @param estadosInternosCerrarOt estados de cierre comercial
     * @return List<CmtEstadoxFlujoMgl>
     * @throws ApplicationException
     */
    public List<CmtEstadoxFlujoMgl> getEstadosByTipoFlujoAndTecAndCieCom(CmtBasicaMgl tipoFlujoOt,
            CmtBasicaMgl tegnologia, List<BigDecimal> estadosInternosCerrarOt)
            throws ApplicationException {
        try {
            List<CmtEstadoxFlujoMgl> resultList;

            String queryStr = "SELECT c FROM CmtEstadoxFlujoMgl c "
                    + " WHERE c.tipoFlujoOtObj = :tipoFlujoOt  "
                    + " AND c.basicaTecnologia = :tecnologia  "
                    + " AND c.estadoInternoObj.basicaId IN :estadoInternoObj "
                    + " AND c.estadoRegistro = :estadoRegistro  ";

            Query query = entityManager.createQuery(queryStr);
            query.setParameter("estadoRegistro", 1);

            if (tipoFlujoOt != null) {
                query.setParameter("tipoFlujoOt", tipoFlujoOt);
            }
            if (tegnologia != null) {
                query.setParameter("tecnologia", tegnologia);
            }
            if (estadosInternosCerrarOt != null && !estadosInternosCerrarOt.isEmpty()) {
                query.setParameter("estadoInternoObj", estadosInternosCerrarOt);
            }

            resultList = (List<CmtEstadoxFlujoMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            throw new ApplicationException("Error al momento de consultar los estados por flujo. EX000: " + e.getMessage(), e);
        }
    }
    
      /**
     * Obtiene un estado de un tipo de FLujo con estado inicial 'C'
     *
     * @author Victor Bocanegra
     * @param tipoFlujoOt Tipo de Flujo
     * @param tegnologia tipo tecnologia
     * @return Estado del Tipo de Flujo
     * @throws ApplicationException
     */
    public CmtEstadoxFlujoMgl findByTipoFlujoAndTecnoAndCancelado(CmtBasicaMgl tipoFlujoOt,
           CmtBasicaMgl tegnologia) throws ApplicationException {

        CmtEstadoxFlujoMgl result = null;
        try {
            Query query = entityManager.createNamedQuery("CmtEstadoxFlujoMgl.findByTipoFlujoAndTecnoAndCancelado");
            query.setParameter("tipoFlujoOt", tipoFlujoOt);
            query.setParameter("tecnologia", tegnologia);
            result = (CmtEstadoxFlujoMgl) query.getSingleResult();
        } catch (NonUniqueResultException ex) {
            LOGGER.error("Se encontro mas de un estado  para la tecnologia: ", ex);
            throw new ApplicationException("Se encontro mas de un estado incial para la tecnologia:", ex);
        } catch (NoResultException nre) {
            LOGGER.error("No se encontro un estado para la tecnologia: ", nre);
            throw new ApplicationException("No se encontro un estado para la tecnologia:", nre);                    
        } catch (Exception e) {
            throw new ApplicationException("Error al momento de consultar los estados por flujo. EX000: " + e.getMessage(), e);
        }
        return result;
    }
    
    /**
     * Obtiene el estado inicial del flujo.Permite obtener el estado incial del
     * flujo.
     *
     * @author Victor Bocanegra
     * @param tipoFlujoOt Tipo de Flujo
     * @param tegnologia
     * @return Estado Inicial del Tipo de Flujo
     * @throws ApplicationException
     */
    public CmtEstadoxFlujoMgl findEstadoInicialFlujo(CmtBasicaMgl tipoFlujoOt, CmtBasicaMgl tegnologia)
            throws ApplicationException {
        try {
            CmtEstadoxFlujoMgl result;
            Query query = entityManager.createNamedQuery("CmtEstadoxFlujoMgl.findEstadoIniTipoFlujo");
            query.setParameter("tipoFlujoOt", tipoFlujoOt);
            query.setParameter("esEstadoInicial", "Y");
            query.setParameter("tecnologia", tegnologia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            result = (CmtEstadoxFlujoMgl) query.getSingleResult();
            return result;
        } catch (NonUniqueResultException ex) {
            LOGGER.error("Se encontro mas de un estado incial para la tecnologia: ", ex);
            return null;
        } catch (NoResultException ex) {
            String msg = "No se encontró un estado incial para la tecnología: " + tegnologia + ": " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de consultar el estado inicial del flujo '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

}
