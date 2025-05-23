package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.MglGestionGenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.UsRolPerfil;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;


/**
 * DAO para el manejo de Roles y Perfiles <i>US_ROL_PERFIL</i>.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
public class UsRolPerfilDaoImpl extends MglGestionGenericDaoImpl<UsRolPerfil> {
    
    private static final Logger LOGGER = LogManager.getLogger(UsRolPerfilDaoImpl.class);
    
    /**
     * Busca todo el listado de ROL_PERFIL.
     * @return Lista de UsRolPerfil.
     * @throws ApplicationException 
     */
    public List<UsRolPerfil> findAll() throws ApplicationException {
        List<UsRolPerfil> resultList;
        Query query = entityManager.createNamedQuery("UsRolPerfil.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<UsRolPerfil>) query.getResultList();
        return resultList;
    }
    
    
    /**
     * Busca todos los roles asociados a un perfil.
     * 
     * @param idPerfil
     * @return Lista de UsRolPerfil.
     * @throws ApplicationException 
     */
    public List<UsRolPerfil> findByPerfil (BigDecimal idPerfil) throws ApplicationException {
        List<UsRolPerfil> resultList = null;
        try {
            Query query = entityManager.createNamedQuery("UsRolPerfil.findByPerfil");
            query.setParameter("idPerfil", idPerfil);
            query.setParameter("estadoRol", "A");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<UsRolPerfil>) query.getResultList();

        } catch (NoResultException nre) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + nre.getMessage();
            LOGGER.error(msg);
            resultList = null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
        return resultList;
    }
    
}
