package co.com.claro.mgl.dao.impl.ptlus;

import co.com.claro.mgl.dao.impl.MglGestionGenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ptlus.UsuariosPortal;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;


/**
 * Data Access Object para el manejo de Usuarios del Portal.
 * Tabla: <i>GESTIONNEW</i>.<i><b>PTLUS_USUARIOS_PORTAL</b></i>.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
public class UsuariosPortalDaoImpl extends MglGestionGenericDaoImpl<UsuariosPortal> {
    
    private static final Logger LOGGER = LogManager.getLogger(UsuariosPortalDaoImpl.class);
    
    
    /**
     * Constructor.
     */
    public UsuariosPortalDaoImpl() {
        super();
    }
    
    
    /**
     * Realiza la b&uacute;squeda de todos los usuarios del portal.
     * 
     * @return Lista de <i>UsuariosPortal</i>.
     * @throws ApplicationException 
     */
    public List<UsuariosPortal> findAll() throws ApplicationException {
        List<UsuariosPortal> resultList;
        TypedQuery<UsuariosPortal> query = entityManager.createNamedQuery("UsuariosPortal.findAll",
                UsuariosPortal.class);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = query.getResultList();
        return resultList;
    }
    
    
    /**
     * Realiza la b&uacute;squeda de un Usuario Portal por el identificador.
     *
     * @param idPortal Identificador Portal.
     * @return Instancia de <i>UsuariosPortal</i>.
     * @throws ApplicationException
     */
    public UsuariosPortal findUsuarioPortalById(BigDecimal idPortal) throws ApplicationException {
        UsuariosPortal result;
        try {
            TypedQuery<UsuariosPortal> query = entityManager.createQuery("SELECT u FROM UsuariosPortal u WHERE u.idPortal = :idPortal ",
                    UsuariosPortal.class);
            query.setParameter("idPortal", idPortal);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = query.getSingleResult();

        } catch (NoResultException nre) {          
            result = null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al buscar usuario: " + e.getMessage(), e);
        }

        return result;
    }
    
    
    /**
     * Realiza la b&uacute;squeda de un Usuario Portal por la c&eacute;dula del
     * usuario.
     *
     * @param cedula C&eacute;dula del Usuario.
     * @return Instancia de <i>UsuariosPortal</i>.
     * @throws ApplicationException
     */
    public UsuariosPortal findUsuarioPortalByCedula(String cedula) throws ApplicationException {
        UsuariosPortal resultUsuario;
        try {
            TypedQuery<UsuariosPortal> query = entityManager.createNamedQuery("UsuariosPortal.findUsuarioPortalByCedula",
                    UsuariosPortal.class);
            query.setParameter("cedula", cedula);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultUsuario = query.getSingleResult();
        } catch (NoResultException ne) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + ne.getMessage();
            LOGGER.error(msg);
            // retorna informacion del usuario en blanco, ya que no hubo resultado.
            resultUsuario = null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al buscar usuario: " + e.getMessage(), e);
        }

        return resultUsuario;
    }
    
    
    /**
     * Realiza la b&uacute;squeda de un Usuario Portal por el nombre de usuario.
     *
     * @param usuario Nombre de Usuario.
     * @return Instancia de <i>UsuariosPortal</i>.
     * @throws ApplicationException
     */
    public UsuariosPortal findUsuarioPortalByUsuario(String usuario) throws ApplicationException {
        UsuariosPortal resultUsuario;
        try {
            TypedQuery<UsuariosPortal> query = entityManager.createNamedQuery("UsuariosPortal.findUsuarioPortalByUsuario",
                    UsuariosPortal.class);
            query.setParameter("usuario", usuario);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultUsuario = query.getSingleResult();
        } catch (NoResultException ne) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + ne.getMessage();
            LOGGER.error(msg);
            // retorna informacion del usuario en blanco, ya que no hubo resultado.
            resultUsuario = null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al buscar usuario: " + e.getMessage(), e);
        }

        return resultUsuario;
    }
    
}
