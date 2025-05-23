package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJBRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase ResourceDelegate
 *
 * @author Deiver Rovira
 * @version	1.0
 */
public class ResourceDelegate {

    private static final Logger LOGGER = LogManager.getLogger(ResourceDelegate.class);

    private static String RESOURCEEJB = "resourceEJB#co.com.telmex.catastro.services.util.ResourceEJBRemote";

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     * @throws javax.naming.NamingException
     */
    public static ResourceEJBRemote getResourceEjb() throws ApplicationException, NamingException {
        InitialContext ctx = new InitialContext();
        ResourceEJBRemote obj = (ResourceEJBRemote) ctx.lookup(RESOURCEEJB);
        return obj;
    }

    /**
     *
     * @param acronimo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static Parametros queryParametros(String acronimo) throws ApplicationException {
        Parametros param = null;
        try {
            ResourceEJBRemote obj = getResourceEjb();
            if (obj != null) {
                param = obj.queryParametros(acronimo);
            }
        } catch (ApplicationException | NamingException ex) {
            LOGGER.error("Error al momento de consultar los parámetros. EX000: " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al momento de consultar los parámetros. EX000: " + ex.getMessage(), ex);
        }
        return param;
    }
}
