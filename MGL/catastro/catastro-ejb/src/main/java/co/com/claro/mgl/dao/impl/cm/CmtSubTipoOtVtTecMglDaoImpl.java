/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaSubTipoOtTecDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubtipoOrdenVtTecMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
/**
 *
 * @author cardenaslb
 */
public class CmtSubTipoOtVtTecMglDaoImpl extends GenericDaoImpl<CmtSubtipoOrdenVtTecMgl>{
      private static final Logger LOGGER = LogManager.getLogger(CmtSubTipoOtVtTecMglDaoImpl.class);
      
      
    public FiltroConsultaSubTipoOtTecDto findSubtipoOtTec(HashMap<String, Object> params, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        FiltroConsultaSubTipoOtTecDto filtroConsultaEstadosxFlujoDto = new FiltroConsultaSubTipoOtTecDto();
        if (contar) {
            filtroConsultaEstadosxFlujoDto.setNumRegistros(findSubtipoOtTecContar(params));
        } else {
            filtroConsultaEstadosxFlujoDto.setListaCmtSubtipoOrdenVtTecMgl(findSubtipoOtTecSearchData(params, firstResult, maxResults));
        }
        return filtroConsultaEstadosxFlujoDto;
    }
    
    
      public long findSubtipoOtTecContar(HashMap<String, Object> params) throws ApplicationException {
        try {
             Long resultCount = 0L;
             
            String queryTipo = "SELECT COUNT(1) FROM CmtSubtipoOrdenVtTecMgl n WHERE n.estadoRegistro = 1  ";
 
            if (params.get("tipoFlujoOtObj") != null && !params.get("tipoFlujoOtObj").toString().trim().isEmpty()) {
                queryTipo += "AND  UPPER (n.tipoFlujoOtObj.descTipoOt) like :tipoFlujo ";
            }
            if (params.get("basicaTecnologia") != null && !params.get("basicaTecnologia").toString().trim().isEmpty()) {
                queryTipo += "AND  UPPER (n.basicaTecnologia.nombreBasica) like :tecnologia ";
            }

            Query query = entityManager.createQuery(queryTipo);

            if (params.get("tipoFlujoOtObj") != null && !params.get("tipoFlujoOtObj").toString().trim().isEmpty()) {
                query.setParameter("tipoFlujo", "%" + params.get("tipoFlujoOtObj").toString().trim().toUpperCase() + "%");
            }
            if (params.get("basicaTecnologia") != null && !params.get("basicaTecnologia").toString().trim().isEmpty()) {
                query.setParameter("basicaTecnologia", "%" + params.get("basicaTecnologia").toString().trim().toUpperCase() + "%");
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
      
    public List<CmtSubtipoOrdenVtTecMgl> findSubtipoOtTecSearchData(HashMap<String, Object> params, int firstResult, int maxResults) throws ApplicationException {
        try {

            String queryTipo = "SELECT n FROM CmtSubtipoOrdenVtTecMgl n WHERE n.estadoRegistro = 1  ";

            if (params.get("tipoFlujoOtObj") != null && !params.get("tipoFlujoOtObj").toString().trim().isEmpty()) {
                queryTipo += "AND  UPPER (n.tipoFlujoOtObj.descTipoOt) like :tipoFlujo ";
            }
            if (params.get("basicaTecnologia") != null && !params.get("basicaTecnologia").toString().trim().isEmpty()) {
                queryTipo += "AND  UPPER (n.basicaTecnologia.nombreBasica) like :tecnologia ";
            }

            Query q = entityManager.createQuery(queryTipo);

            if (params.get("tipoFlujoOtObj") != null && !params.get("tipoFlujoOtObj").toString().trim().isEmpty()) {
                q.setParameter("tipoFlujo", "%" + params.get("tipoFlujoOtObj").toString().trim().toUpperCase() + "%");
            }
            if (params.get("basicaTecnologia") != null && !params.get("basicaTecnologia").toString().trim().isEmpty()) {
                q.setParameter("basicaTecnologia", "%" + params.get("basicaTecnologia").toString().trim().toUpperCase() + "%");
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
    
        public CmtSubtipoOrdenVtTecMgl findById(BigDecimal id) throws ApplicationException {

        CmtSubtipoOrdenVtTecMgl result = null;
        try {
            Query query = entityManager.createNamedQuery("CmtSubtipoOrdenVtTecMgl.findId");
            query.setParameter("estadoxFlujoId", id);
            
            result = (CmtSubtipoOrdenVtTecMgl) query.getSingleResult();
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
        
    public CmtSubtipoOrdenVtTecMgl findSubTipoxTecnologia(CmtBasicaMgl basicaTecnologia) throws ApplicationException {

        CmtSubtipoOrdenVtTecMgl result = null;
        try {
            String queryTipo = "SELECT n FROM CmtSubtipoOrdenVtTecMgl n WHERE n.basicaTecnologia = :basicaTecnologia and  n.estadoRegistro = 1  ";
            Query q = entityManager.createQuery(queryTipo);
            q.setParameter("basicaTecnologia", basicaTecnologia);
           
            result = (CmtSubtipoOrdenVtTecMgl) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            LOGGER.error("Se encontro mas de un estado final: ", ex);
            throw new ApplicationException("Se encontro mas de un estado incial, es necesario Configurarlo para continuar. :", ex);
        } catch (NoResultException nre) {
            LOGGER.error("No se encontro un estado final: ", nre);
            throw new ApplicationException("No se encontro un estado incial: es necesario Configurarlo para continuar.", nre);
        } catch (Exception e) {
            throw new ApplicationException("Error al momento de consultar el estado por flujo. EX000: " + e.getMessage(), e);
        }
        return result;
    }
    
    
    public List<CmtSubtipoOrdenVtTecMgl> findSubtipoOtxTecno(CmtBasicaMgl basicaTecnologia) throws ApplicationException {
        try {

            String queryTipo = "SELECT n FROM CmtSubtipoOrdenVtTecMgl n WHERE n.basicaTecnologia = :basicaTecnologia and  n.estadoRegistro = 1  ";
            Query q = entityManager.createQuery(queryTipo);
            q.setParameter("basicaTecnologia", basicaTecnologia);

            return (List<CmtSubtipoOrdenVtTecMgl>) q.getResultList();
        } catch (NonUniqueResultException ex) {
            LOGGER.error("Se encontro mas de un estado final: ", ex);
            throw new ApplicationException("Se encontro mas de un estado incial :", ex);
        } catch (NoResultException nre) {
            LOGGER.error("No se encontro un estado final: ", nre);
            throw new ApplicationException("No se encontro un estado incial:", nre);
        } catch (Exception e) {
            throw new ApplicationException("Error al momento de consultar el estado por flujo. EX000: " + e.getMessage(), e);
        }
    }

    public List<CmtSubtipoOrdenVtTecMgl> findConfSubtipoOtxTecno(CmtBasicaMgl basicaTecnologia, CmtTipoOtMgl cmtTipoOtMgl) throws ApplicationException {
        try {

            String queryTipo = "SELECT n FROM CmtSubtipoOrdenVtTecMgl n WHERE n.basicaTecnologia.basicaId = :basicaTecnologia "
                    + "and  n.tipoFlujoOtObj.idTipoOt = :cmtTipoOtMgl and  n.estadoRegistro = 1  ";
            Query q = entityManager.createQuery(queryTipo);
            q.setParameter("basicaTecnologia", basicaTecnologia.getBasicaId());
            q.setParameter("cmtTipoOtMgl", cmtTipoOtMgl.getIdTipoOt());

            return (List<CmtSubtipoOrdenVtTecMgl>) q.getResultList();
        } catch (NonUniqueResultException ex) {
            LOGGER.error("Se encontro mas de un estado final: ", ex);
            throw new ApplicationException("Se encontro mas de un estado incial :", ex);
        } catch (NoResultException nre) {
            LOGGER.error("No se encontro un estado final: ", nre);
            throw new ApplicationException("No se encontro un estado incial:", nre);
        } catch (Exception e) {
            throw new ApplicationException("Error al momento de consultar el estado por flujo. EX000: " + e.getMessage(), e);
        }
    }
}
