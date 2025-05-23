package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Restriccion;
import co.com.telmex.catastro.services.comun.RestrictionEJBRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase RestrictionDelegate
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class RestrictionDelegate {

    private static final Logger LOGGER = LogManager.getLogger(RestrictionDelegate.class);
    private static String RESTRICTION_EJB = "restrictionEJB#co.com.telmex.catastro.services.comun.RestrictionEJBRemote";

    /**
     * 
     * @return
     * @throws javax.naming.NamingException
     */
    public static RestrictionEJBRemote getMenuEjb() throws NamingException {
        InitialContext ctx = new InitialContext();
        RestrictionEJBRemote obj = (RestrictionEJBRemote) ctx.lookup(RESTRICTION_EJB);
        return obj;
    }

    /**
     * 
     * @param rolId
     * @param nameTable
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<Restriccion> queryRestriction(BigDecimal rolId, String nameTable) throws ApplicationException {
        List<Restriccion> list = null;
        try {
            RestrictionEJBRemote obj = getMenuEjb();
            if (obj != null) {
                list = obj.queryRestriction(rolId, nameTable);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al momento de consultar las restricciones. EX000: " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al momento de consultar las restricciones. EX000: " + ex.getMessage(), ex);
        }
        return list;
    }
}
