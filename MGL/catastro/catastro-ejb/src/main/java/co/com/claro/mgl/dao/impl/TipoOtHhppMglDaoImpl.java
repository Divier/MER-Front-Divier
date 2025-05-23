/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;


/**
 *
 * @author Juan David Hernandez
 */
public class TipoOtHhppMglDaoImpl extends GenericDaoImpl<TipoOtHhppMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(TipoOtHhppMglDaoImpl.class);


    public List<TipoOtHhppMgl> findAll() throws ApplicationException {
        List<TipoOtHhppMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "TipoOtHhppMgl c WHERE c.estadoRegistro = :estado ORDER BY c.fechaCreacion DESC");
        query.setParameter("estado", 1);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<TipoOtHhppMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    public TipoOtHhppMgl findTipoOtHhppById(BigDecimal tipoOtId)
            throws ApplicationException {
        try {
            TipoOtHhppMgl result;
            Query query = entityManager.createQuery("SELECT c FROM "
                    + "TipoOtHhppMgl c WHERE c.tipoOtId = :tipoOtId ");
            query.setParameter("tipoOtId", tipoOtId);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = (TipoOtHhppMgl) query.getSingleResult();
            getEntityManager().clear();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            return null;
        }        
    }
    
    
    public int countAllTipoOtHhpp() throws ApplicationException {
        Query query = entityManager.createQuery("SELECT count(1) FROM "
                + "TipoOtHhppMgl c WHERE c.estadoRegistro = :estado ");
        query.setParameter("estado", 1);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        int result = query.getSingleResult() == null ? 0
                : ((Long) query.getSingleResult()).intValue();
        getEntityManager().clear();
        return result;
    }

    public List<TipoOtHhppMgl> findAllTipoOtHhppPaginada(int firstResult,
            int maxResults) throws ApplicationException {
        List<TipoOtHhppMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "TipoOtHhppMgl c WHERE c.estadoRegistro = :estado "
                + " ORDER BY c.tipoOtId DESC");
        query.setParameter("estado", 1);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<TipoOtHhppMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }
    
    
    public TipoOtHhppMgl findTipoOtHhppByNombreTipoOt(String nombreTipoOt)
            throws ApplicationException {
        try {
            TipoOtHhppMgl result;
            Query query = entityManager.createQuery("SELECT c FROM "
                    + "TipoOtHhppMgl c WHERE c.nombreTipoOt = :nombreTipoOt "
                    + "AND c.estadoRegistro = 1");
            query.setParameter("nombreTipoOt", nombreTipoOt);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = (TipoOtHhppMgl) query.getSingleResult();
            getEntityManager().clear();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            return null;
        }     
    }
    /**
     * Busca Tipo de Ot por Abreviatura.Permite buscar un tipo de OT en la base de
 datos por la Abreviatura
     *
     * @author ortizjaf
     * @param identificadorInterno
     * @return tipo de ot encontrada por la abreviatura
     * @throws ApplicationException
     */
    public TipoOtHhppMgl findTipoOtByIdentificadorInterno(String identificadorInterno)
            throws ApplicationException {
        try {
            TipoOtHhppMgl result;
            Query query = entityManager.createNamedQuery("TipoOtHhppMgl.findByIdentificadorInterno");
            query.setParameter("identificadorInterno", identificadorInterno);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = (TipoOtHhppMgl) query.getSingleResult();
            getEntityManager().clear();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException (msg,e);
        }     
    }
    
}