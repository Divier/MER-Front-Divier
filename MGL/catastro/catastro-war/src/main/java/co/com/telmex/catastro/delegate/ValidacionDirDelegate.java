package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.services.direcciones.ValidacionDirEJBRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase MarcasDelegate
 *
 * @author 	Deiver Rovira
 * @version	1.0
 */
public class ValidacionDirDelegate {
    
    private static final Logger LOGGER = LogManager.getLogger(ValidacionDirDelegate.class);

    private static String VALIDACIONEJB = "validacionDirEJB#co.com.telmex.catastro.services.direcciones.ValidacionDirEJBRemote";

    /**
     * 
     * @return
     * @throws javax.naming.NamingException
     */
    public static ValidacionDirEJBRemote getValidacionDirEjb() throws NamingException {
        InitialContext ctx = new InitialContext();
        ValidacionDirEJBRemote obj = (ValidacionDirEJBRemote) ctx.lookup(VALIDACIONEJB);
        return obj;
    }

    /**
     * 
     * @param idCiudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static String queryTipoCiudadByID(String idCiudad) throws ApplicationException {
        String tipoCiudad = null;
        try {
            ValidacionDirEJBRemote obj = getValidacionDirEjb();
            if (obj != null) {
                tipoCiudad = obj.queryTipoCiudadByID(idCiudad);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al momento de consultar el tipo ciudad. EX000: " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al momento de consultar el tipo ciudad. EX000: " + ex.getMessage(), ex);
        }
        return tipoCiudad;
    }

    /**
     * 
     * @param idCiudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static boolean queryCiudadMultiorigen(String idCiudad) throws ApplicationException {
        boolean multiorigen = false;
        try {
            ValidacionDirEJBRemote obj = getValidacionDirEjb();
            if (obj != null) {
                multiorigen = obj.queryCiudadMultiorigen(idCiudad);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al momento de consultar la ciudad multiorigen. EX000: " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al momento de consultar la ciudad multiorigen. EX000: " + ex.getMessage(), ex);
        }
        return multiorigen;
    }

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static BigDecimal[] queryRolesAdministradores() throws ApplicationException {
        BigDecimal[] roles = null;
        try {
            ValidacionDirEJBRemote obj = getValidacionDirEjb();
            if (obj != null) {
                roles = obj.queryRolesAdministradores();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al momento de consultar los roles administradores. EX000: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al momento de consultar los roles administradores. EX000: " + ex.getMessage(), ex);
        }
        return roles;
    }

    /**
     * 
     * @param acronimo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static int queryNivelConfiabilidadFromParametros(String acronimo) throws ApplicationException {
        int nivelConfiabilidad = 95;
        try {
            ValidacionDirEJBRemote obj = getValidacionDirEjb();
            if (obj != null) {
                nivelConfiabilidad = obj.queryNivelConfiabilidadFromParametros(acronimo);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al momento de consultar el nivel de confiabilidad. EX000: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al momento de consultar el nivel de confiabilidad. EX000: " + ex.getMessage(), ex);
        }
        return nivelConfiabilidad;
    }
}
