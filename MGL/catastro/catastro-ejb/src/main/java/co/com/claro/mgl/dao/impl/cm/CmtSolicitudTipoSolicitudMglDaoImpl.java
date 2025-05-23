/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudTipoSolicitudMgl;
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
public class CmtSolicitudTipoSolicitudMglDaoImpl extends GenericDaoImpl <CmtSolicitudTipoSolicitudMgl>{
    
    private static final Logger LOGGER = LogManager.getLogger(CmtSolicitudTipoSolicitudMglDaoImpl.class);
    
    public List<CmtSolicitudTipoSolicitudMgl> findAll() throws ApplicationException {

        try {
            List<CmtSolicitudTipoSolicitudMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtSolicitudTipoSolicitudMgl.findAll");
            query.setParameter("estado", 1);
            resultList = (List<CmtSolicitudTipoSolicitudMgl>) query.getResultList();
            return resultList;

        } catch (Exception e) {   
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }
    
    
     public CmtSolicitudTipoSolicitudMgl findByIdSolicitud(BigDecimal idSolicitud) throws ApplicationException {

        try { 
            Query query = entityManager.createNamedQuery("CmtSolicitudTipoSolicitudMgl.findByIdSolicitud");
            query.setParameter("estado", 1);
            query.setParameter("idSolicitud", idSolicitud);
            CmtSolicitudTipoSolicitudMgl result = (CmtSolicitudTipoSolicitudMgl) query.getSingleResult();
            return result;

        } catch (Exception e) {   
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }
    
}
