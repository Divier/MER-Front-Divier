package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Rol;
import co.com.telmex.catastro.data.Usuario;
import co.com.telmex.catastro.services.seguridad.AuthenticEJBRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase SeguridadDelegate
 *
 * @author Jose Luis Caicedo G.
 * @version	1.0
 */
public class SeguridadDelegate {

    private static final Logger LOGGER = LogManager.getLogger(SeguridadDelegate.class);

    private static String SECURITYEJB = "authenticEJB#co.com.telmex.catastro.services.seguridad.AuthenticEJBRemote";

    /**
     *
     * @return
     * @throws javax.naming.NamingException @throws Exception
     */
    public static AuthenticEJBRemote getSecuritygEjb() throws NamingException {
        InitialContext ctx = new InitialContext();
        AuthenticEJBRemote obj = (AuthenticEJBRemote) ctx.lookup(SECURITYEJB);
        return obj;
    }

    /**
     *
     * @param login
     * @param pwd
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static Usuario processAuthentic(String login, String pwd) throws ApplicationException {
        Usuario user = null;
        try {
            AuthenticEJBRemote obj = getSecuritygEjb();
            if (obj != null) {
                user = obj.processAuthentic(login, pwd);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al momento de procesar la autenticación. EX000: " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al momento de procesar la autenticación. EX000: " + ex.getMessage(), ex);
        }
        return user;
    }

    /**
     *
     * @param usuId
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<Rol> queryListRol(BigDecimal usuId) throws ApplicationException {
        List<Rol> list = null;
        try {
            AuthenticEJBRemote obj = getSecuritygEjb();
            if (obj != null) {
                list = obj.queryListRol(usuId);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al momento de consultar el listado de roles. EX000: " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al momento de consultar el listado de roles. EX000: " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @param idRol
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static boolean validateRolMenu(String idRol) throws ApplicationException {
        boolean ok = false;
        try {
            AuthenticEJBRemote obj = getSecuritygEjb();
            if (obj != null) {
                ok = obj.validateRolMenu(idRol);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al momento de validar los roles del menú. EX000: " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al momento de validar los roles del menú. EX000: " + ex.getMessage(), ex);
        }
        return ok;
    }
}
