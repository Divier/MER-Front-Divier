
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RrCiudades;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Parzifal de León
 */
public class RrCiudadesDaoImpl extends GenericDaoImpl<RrCiudades> {
    
    private static final Logger LOGGER = LogManager.getLogger(RrCiudadesDaoImpl.class);

    public List<RrCiudades> findByCodregional(String codregional) {
        List<RrCiudades> resultList = null;
        String query = "select * from rr_ciudades where codregional = ? and estado = ? order by nombre";
        Query q = entityManager.createNativeQuery(query, RrCiudades.class);
        q.setParameter(1, codregional);
        q.setParameter(2, "A");
		q.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<RrCiudades>) q.getResultList();
        return resultList;
    }

    @Override
    public RrCiudades find(String id) throws ApplicationException {
        try {
            return this.entityManager.find(entityClass, id);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
    public RrCiudades findNombreCiudadByCodigo(String codigo) throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("RrCiudades.findNombreCiudadByCodigo");
            query.setParameter("codigo", codigo);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            RrCiudades result = (RrCiudades) query.getSingleResult();
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
