/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RrNodosDth;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Juan David Hernandez
 */
public class RrNodosDthDaoImpl extends GenericDaoImpl<RrNodosDth> {
    
    private static final Logger LOGGER = LogManager.getLogger(RrNodosDthDaoImpl.class);

    public List<RrNodosDth> findNodosDthByCodCiudad(String codCiudad) throws ApplicationException {
        try {
            List<RrNodosDth> result;
            Query query = entityManager.createQuery("SELECT rn FROM RrNodosDth rn where "
                    + " rn.codCiudad = :codCiudad AND rn.estado =:estado ");
            
            query.setParameter("codCiudad", codCiudad);
            query.setParameter("estado", "A");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = (List<RrNodosDth>) query.getResultList();
            return result;
            
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }

    }

    
}
