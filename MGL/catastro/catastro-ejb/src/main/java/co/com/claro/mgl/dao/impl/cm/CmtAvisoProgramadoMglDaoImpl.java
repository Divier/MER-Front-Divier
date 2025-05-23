/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtAvisosProgramadosMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author rodriguezluim
 */
public class CmtAvisoProgramadoMglDaoImpl extends GenericDaoImpl<CmtAvisosProgramadosMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtAvisoProgramadoMglDaoImpl.class);
    
     public List<CmtAvisosProgramadosMgl> findByHhpp(BigDecimal hhppId) throws ApplicationException {
        try {
            List<CmtAvisosProgramadosMgl> resultList;
            Query query = entityManager.createQuery("SELECT ap FROM CmtAvisosProgramadosMgl ap WHERE ap.hhppId = :hhppId");
            query.setParameter("hhppId", hhppId);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<CmtAvisosProgramadosMgl>) query.getResultList();
            return resultList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }        
    }

    public List<CmtAvisosProgramadosMgl> findByTecnologiaCcmm(BigDecimal cuentaMatrizId) throws ApplicationException {
        try {
            List<CmtAvisosProgramadosMgl> resultList;
            Query query = entityManager.createQuery("SELECT ap FROM CmtAvisosProgramadosMgl ap WHERE ap.tecnologiaSubedificioId = :tecnologiaSubedificioId");
            query.setParameter("tecnologiaSubedificioId", cuentaMatrizId);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<CmtAvisosProgramadosMgl>) query.getResultList();
            return resultList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    public List<CmtAvisosProgramadosMgl> findBySolicitud(BigDecimal solicitudId) throws ApplicationException {
        try {
            List<CmtAvisosProgramadosMgl> resultList;
            Query query = entityManager.createQuery("SELECT ap FROM CmtAvisosProgramadosMgl ap WHERE ap.solicitudId = :solicitudId");
            query.setParameter("solicitudId", solicitudId);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<CmtAvisosProgramadosMgl>) query.getResultList();
            return resultList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
    
    /**
     * valbuenayf metodo para buscar la orden de trabajo de un HHPP 
     * @param otHhppId
     * @return
     * @throws ApplicationException 
     */
    public CmtAvisosProgramadosMgl findByordenTrabajoHhpp(BigDecimal otHhppId) throws ApplicationException {
        try {
            CmtAvisosProgramadosMgl result;
            Query query = entityManager.createQuery("SELECT ap FROM CmtAvisosProgramadosMgl ap WHERE ap.otHhppId = :otHhppId");
            query.setParameter("otHhppId", otHhppId);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            result = (CmtAvisosProgramadosMgl) query.getSingleResult();
            return result;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
    
}
