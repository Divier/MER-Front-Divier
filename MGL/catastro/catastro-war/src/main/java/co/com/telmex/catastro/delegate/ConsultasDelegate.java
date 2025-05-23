package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Comunidad;
import co.com.telmex.catastro.data.SolicitudConsulta;
import co.com.telmex.catastro.data.TipoHhppConexion;
import co.com.telmex.catastro.data.TipoHhppRed;
import co.com.telmex.catastro.services.solicitud.ConsultaSolicitudEJBRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase ConsultasDelegate
 *
 * @author Ana María Malambo.
 * @version	1.0
 */
public class ConsultasDelegate {

    private static final Logger LOGGER = LogManager.getLogger(ConsultasDelegate.class);

    private static String CONSULTAEJB = "consultaSolicitudEJB#co.com.telmex.catastro.services.solicitud.ConsultaSolicitudEJBRemote";

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     * @throws javax.naming.NamingException
     */
    public static ConsultaSolicitudEJBRemote getConsultaSolicitudEjb() throws ApplicationException, NamingException {
        InitialContext ctx = new InitialContext();
        ConsultaSolicitudEJBRemote obj = (ConsultaSolicitudEJBRemote) ctx.lookup(CONSULTAEJB);
        return obj;
    }

    /**
     *
     * @param direccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<SolicitudConsulta> queryCuentaMatriz(String direccion) throws ApplicationException {
        List<SolicitudConsulta> list = null;
        try {
            ConsultaSolicitudEJBRemote obj = getConsultaSolicitudEjb();
            if (obj != null) {
                list = obj.queryCuentaMatriz(direccion);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar la cuenta matriz por dirección. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar la cuenta matriz por dirección. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @param socio
     * @param direccion
     * @param user
     * @param funcionalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<SolicitudConsulta> modificarEst(String socio, String direccion, String user, String funcionalidad) throws ApplicationException {
        List<SolicitudConsulta> list = null;
        try {
            ConsultaSolicitudEJBRemote obj = getConsultaSolicitudEjb();
            if (obj != null) {
                list = obj.modificarEst(socio, direccion, user, funcionalidad);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al modificar el estrato. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al modificar el estrato. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     * Metodo para consultar TipoHhppConexion autor: Nataly
     *
     * @param idTipoHhppConexion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static TipoHhppConexion queryTipoHhppConexion(BigDecimal idTipoHhppConexion) throws ApplicationException {
        TipoHhppConexion thc = null;
        try {
            ConsultaSolicitudEJBRemote obj = getConsultaSolicitudEjb();
            if (obj != null) {
                thc = obj.queryTipoHhppConexion(idTipoHhppConexion);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar el tipo de conexión HHPP . EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar el tipo de conexión HHPP . EX000 " + ex.getMessage(), ex);
        }
        return thc;
    }

    /**
     * Metodo para consultar TipoHhppRed autor: Nataly
     *
     * @param idTipoHhppRed
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static TipoHhppRed queryTipoHhppRed(BigDecimal idTipoHhppRed) throws ApplicationException {
        TipoHhppRed thr = null;
        try {
            ConsultaSolicitudEJBRemote obj = getConsultaSolicitudEjb();
            if (obj != null) {
                thr = obj.queryTipoHhppRed(idTipoHhppRed);
            }
        } catch (ApplicationException | NamingException ex) {
            LOGGER.error("Error al consultar el tipo de red HHPP. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar el tipo de red HHPP. EX000 " + ex.getMessage(), ex);
        }
        return thr;
    }

    /**
     * Buscar un Tipo de Red segun el código
     *
     * @param codigo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static TipoHhppRed queryTipoHhppRedByCodigo(String codigo) throws ApplicationException {
        TipoHhppRed thr = null;
        try {
            ConsultaSolicitudEJBRemote obj = getConsultaSolicitudEjb();
            if (obj != null) {
                thr = obj.queryTipoHhppRedByCodigo(codigo);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar el tipo de red HHPP por codigo. EX000 " + ex.getMessage(), ex);
        } catch (ApplicationException ex) {
            LOGGER.error("Error al consultar el tipo de red HHPP por codigo. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar el tipo de red HHPP por codigo. EX000 " + ex.getMessage(), ex);
        }
        return thr;
    }

    /**
     * consultar uan comunidad
     *
     * @param idComunidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static Comunidad queryComunidad(String idComunidad) throws ApplicationException {
        Comunidad comunidad = null;
        try {
            ConsultaSolicitudEJBRemote obj = getConsultaSolicitudEjb();
            if (obj != null) {
                comunidad = obj.queryComunidad(idComunidad);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar comunidad. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar comunidad. EX000 " + ex.getMessage(), ex);
        }
        return comunidad;
    }
}
