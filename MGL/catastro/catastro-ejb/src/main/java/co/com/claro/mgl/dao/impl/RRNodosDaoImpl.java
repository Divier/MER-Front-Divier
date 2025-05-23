/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RRNodos;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class RRNodosDaoImpl extends GenericDaoImpl<RRNodos> {
    
    private static final Logger LOGGER = LogManager.getLogger(RRNodosDaoImpl.class);

    public RRNodos findAll() throws ApplicationException {
        try {
            RRNodos result;
            Query query = entityManager.createNamedQuery("RRNodos.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = (RRNodos) query.getSingleResult();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    public List<RRNodos> findNodesByCodCiudad(String codCiudad) throws ApplicationException {
        try {
            List<RRNodos> result;
            Query query = entityManager.createQuery("SELECT rn FROM RRNodos rn where "
                    + " rn.codCiudad = :codCiudad  ");
            query.setParameter("codCiudad", codCiudad);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = (List<RRNodos>) (RRNodos) query.getResultList();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }

    }

    public RRNodos findNodeNFIByCommunity(String codComunidad) throws ApplicationException {
        try {
            RRNodos result;
            Query query = entityManager.createQuery("SELECT rn FROM RRNodos rn where "
                    + " rn.codCiudad = :codCiudad  AND rn.codEQ = :codEQ  ");
            query.setParameter("codCiudad", codComunidad);
            query.setParameter("codEQ", Constant.NODO_NFI_COD_EQ);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = (RRNodos) query.getSingleResult();
            return result;
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (NonUniqueResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Se encontro mas de un nodo NFI para la comunidad "+codComunidad);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }

    }
}
