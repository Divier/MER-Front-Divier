package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.DataShowSolicitudRed;
import co.com.telmex.catastro.data.DetalleSolicitud;
import co.com.telmex.catastro.services.solicitud.SolicitudHHPPDisenioRedEJBRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase ProcesarSolHHPPDisenoRedDelegate
 *
 * @author Ana María Malambo
 * @version	1.0
 */
public class ProcesarSolHHPPDisenoRedDelegate {

    private static final Logger LOGGER = LogManager.getLogger(ProcesarSolHHPPDisenoRedDelegate.class);

    private static String PROCESARMODIFICACION = "solicitudHHPPDisenioRedEJB#co.com.telmex.catastro.services.solicitud.SolicitudHHPPDisenioRedEJBRemote";

    /**
     *
     * @return
     * @throws javax.naming.NamingException @throws Exception
     */
    public static SolicitudHHPPDisenioRedEJBRemote getProcesarSolicitudModHHPPSolRedEjb() throws NamingException {
        InitialContext ctx = new InitialContext();
        SolicitudHHPPDisenioRedEJBRemote obj = (SolicitudHHPPDisenioRedEJBRemote) ctx.lookup(PROCESARMODIFICACION);
        return obj;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<DataShowSolicitudRed> querySolicitudesProcesar() throws ApplicationException {
        List<DataShowSolicitudRed> list = null;
        try {
            SolicitudHHPPDisenioRedEJBRemote obj = getProcesarSolicitudModHHPPSolRedEjb();
            if (obj != null) {
                list = obj.querySolicitudesProcesar();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar las solicitudes. EX000: " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar las solicitudes. EX000: " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @param idSolRed
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<DetalleSolicitud> queryDetalleSolicitudCreacionBySolRedId(BigDecimal idSolRed) throws ApplicationException {
        List<DetalleSolicitud> list = null;
        try {
            SolicitudHHPPDisenioRedEJBRemote obj = getProcesarSolicitudModHHPPSolRedEjb();
            if (obj != null) {
                list = obj.queryDetallesSolicitudBySolRedId(idSolRed);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al momento de consultar el detalle de la solicitud de creación. EX000: " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al momento de consultar el detalle de la solicitud de creación. EX000: " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @param idSolRed
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<DetalleSolicitud> queryDetalleSolicitudCreacionBySolRedIdValidos(String idSolRed) throws ApplicationException {
        List<DetalleSolicitud> list = null;
        try {
            SolicitudHHPPDisenioRedEJBRemote obj = getProcesarSolicitudModHHPPSolRedEjb();
            if (obj != null) {
                list = obj.queryDetalleSolicitudCreacionBySolRedIdValidos(idSolRed);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al momento de consultar el detalle de la solicitud de creación. EX000: " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al momento de consultar el detalle de la solicitud de creación. EX000: " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @param solicitudes
     * @param nombreFuncionalidad
     * @param nombreArchivo
     * @param user
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static String procesarSolicitudesCreacionRed(DetalleSolicitud solicitudes, String nombreFuncionalidad, String nombreArchivo, String user) throws ApplicationException {
        String message = null;
        try {
            SolicitudHHPPDisenioRedEJBRemote obj = getProcesarSolicitudModHHPPSolRedEjb();
            if (obj != null) {
                message = obj.procesarSolicitudesCreacionRed(solicitudes, nombreFuncionalidad, nombreArchivo, user);
            }
        } catch (NamingException ex) {
            message = ex.getMessage();
            LOGGER.error("Error al momento de procesar las solicitudes de creación. EX000: " + ex.getMessage(), ex);
        }catch (Exception ex) {
            message = ex.getMessage();
            LOGGER.error("Error al momento de procesar las solicitudes de creación. EX000: " + ex.getMessage(), ex);
        }
        return message;

    }
}
