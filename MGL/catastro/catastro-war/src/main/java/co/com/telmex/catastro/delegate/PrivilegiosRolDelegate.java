package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Privilegios;
import co.com.telmex.catastro.services.seguridad.PrivilegiosRolEJBRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase PrivilegiosRolDelegate
 *
 * @author Ana María Malambo
 * @version	1.0
 */
public class PrivilegiosRolDelegate {

    private static final Logger LOGGER = LogManager.getLogger(PrivilegiosRolDelegate.class);

    private static String PRIVILEGIOEJB = "privilegiosRolEJB#co.com.telmex.catastro.services.seguridad.PrivilegiosRolEJBRemote";

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     * @throws javax.naming.NamingException
     */
    public static PrivilegiosRolEJBRemote getPrivilegiosRolEjb() throws ApplicationException, NamingException {
        InitialContext ctx = new InitialContext();
        PrivilegiosRolEJBRemote obj = (PrivilegiosRolEJBRemote) ctx.lookup(PRIVILEGIOEJB);
        return obj;
    }

    /**
     *
     * @param rol
     * @param funcionalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<Privilegios> queryPrivilegiosRol(BigDecimal rol, String funcionalidad) throws ApplicationException {
        List<Privilegios> list = null;
        try {
            PrivilegiosRolEJBRemote obj = getPrivilegiosRolEjb();
            if (obj != null) {
                list = obj.queryPrivilegiosRol(rol, funcionalidad);
            }
        } catch (ApplicationException | NamingException ex) {
            LOGGER.error("Error al momento de consultar los provilegios de rol. EX000: " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al momento de consultar los provilegios de rol. EX000: " + ex.getMessage(), ex);
        }
        return list;
    }
}
