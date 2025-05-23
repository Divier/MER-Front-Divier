/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.SolicitudCEMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
public class SolicitudCEMglDaoImpl extends GenericDaoImpl<SolicitudCEMgl>  {
    
    private static final Logger LOGGER = LogManager.getLogger(SolicitudCEMglDaoImpl.class);

    public List<SolicitudCEMgl> findAll() throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("SolicitudCEMgl.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            return (List<SolicitudCEMgl>) query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
}
