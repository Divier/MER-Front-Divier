package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.data.EstadoHhpp;
import co.com.telmex.catastro.data.Hhpp;
import co.com.telmex.catastro.data.SolicitudRed;
import co.com.telmex.catastro.services.hhpp.HhppEJBRemote;
import co.com.telmex.catastro.services.solicitud.SolicitudHHPPDisenioRedEJBRemote;
import co.com.telmex.catastro.services.util.ConsultaSolRedModificacion;
import com.jlcg.db.exept.ExceptionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase ProcesarSolModHHPPSolRedDelegate
 *
 * @author Deiver Rovira
 * @version	1.0
 */
public class ProcesarSolModHHPPSolRedDelegate {

    private static final Logger LOGGER = LogManager.getLogger(ProcesarSolModHHPPSolRedDelegate.class);

    private static String PROCESARMODIFICACION = "solicitudHHPPDisenioRedEJB#co.com.telmex.catastro.services.solicitud.SolicitudHHPPDisenioRedEJBRemote";

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     * @throws javax.naming.NamingException
     */
    public static SolicitudHHPPDisenioRedEJBRemote getProcesarSolicitudModHHPPSolRedEjb() throws ApplicationException, NamingException {
        InitialContext ctx = new InitialContext();
        SolicitudHHPPDisenioRedEJBRemote obj = (SolicitudHHPPDisenioRedEJBRemote) ctx.lookup(PROCESARMODIFICACION);
        return obj;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<ConsultaSolRedModificacion> querySolicitudesRedCreadas() throws ApplicationException {
        List<ConsultaSolRedModificacion> list = null;
        try {
            SolicitudHHPPDisenioRedEJBRemote obj = getProcesarSolicitudModHHPPSolRedEjb();
            if (obj != null) {
                list = obj.querySolicitudesRedCreadas();
            }
        } catch (ApplicationException | NamingException ex) {
            LOGGER.error("Error al consultar solicitudes de red creadas. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar solicitudes de red creadas. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<ConsultaSolRedModificacion> querySolicitudesRedAProcesar() throws ApplicationException {
        List<ConsultaSolRedModificacion> list = null;
        try {
            SolicitudHHPPDisenioRedEJBRemote obj = getProcesarSolicitudModHHPPSolRedEjb();
            if (obj != null) {
                list = obj.querySolicitudesRedAProcesar();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar solicitudes de red a procesar. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar solicitudes de red a procesar. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @param idSolRed
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<ConsultaSolRedModificacion> queryDetalleSolicitudBySolRedId(String idSolRed) throws ApplicationException {
        List<ConsultaSolRedModificacion> list = null;
        try {
            SolicitudHHPPDisenioRedEJBRemote obj = getProcesarSolicitudModHHPPSolRedEjb();
            if (obj != null) {
                list = obj.queryDetalleSolicitudBySolRedId(idSolRed);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar detalle de solicitud por solRedId. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar detalle de solicitud por solRedId. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @param idSolRed
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<ConsultaSolRedModificacion> queryDetalleSolicitudBySolRedIdValidos(String idSolRed) throws ApplicationException {
        List<ConsultaSolRedModificacion> list = null;
        try {
            SolicitudHHPPDisenioRedEJBRemote obj = getProcesarSolicitudModHHPPSolRedEjb();
            if (obj != null) {
                list = obj.queryDetalleSolicitudBySolRedIdValidos(idSolRed);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar detalle solicitud por solRedId validos. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar detalle solicitud por solRedId validos. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @param idHhpp
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static Hhpp queryHhppById(String idHhpp) throws ApplicationException {
        Hhpp hhpp = null;
        try {
            HhppEJBRemote obj = HhppDelegate.getHhppEjb();
            if (obj != null) {
                hhpp = obj.queryHhppById(idHhpp);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar HHPP por id. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar HHPP por id. EX000 " + ex.getMessage(), ex);
        }
        return hhpp;
    }

    /**
     *
     * @param nuevoHhpp
     * @param nombreFuncionalidad
     * @param user
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static String updateHhpp(Hhpp nuevoHhpp, String nombreFuncionalidad, String user) throws ApplicationException {
        String ok = "";
        try {
            HhppEJBRemote obj = HhppDelegate.getHhppEjb();
            if (obj != null) {
                ok = obj.updateHhpp(nuevoHhpp, nombreFuncionalidad, user);
            }
        } catch (ApplicationException | ExceptionDB | NamingException ex) {
            LOGGER.error("Error al actualizar HHPP. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al actualizar HHPP. EX000 " + ex.getMessage(), ex);
        }
        return ok;
    }

    /**
     *
     * @param nombreEstado
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static EstadoHhpp queryEstadoHhpp(String nombreEstado) throws ApplicationException {
        EstadoHhpp estadoHhpp = null;
        try {
            HhppEJBRemote obj = HhppDelegate.getHhppEjb();
            if (obj != null) {
                estadoHhpp = obj.queryEstadoHhpp(nombreEstado);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar estado HHPP. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar estado HHPP. EX000 " + ex.getMessage(), ex);
        }
        return estadoHhpp;
    }

    /**
     *
     * @param nuevaDireccion
     * @param nombreFuncionalidad
     * @param user
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static String updateDireccion(Direccion nuevaDireccion, String nombreFuncionalidad, String user) throws ApplicationException {
        String ok = "";
        try {
            SolicitudHHPPDisenioRedEJBRemote obj = getProcesarSolicitudModHHPPSolRedEjb();
            if (obj != null) {
                ok = obj.updateDireccion(nuevaDireccion, nombreFuncionalidad, user);
            }
        } catch (ApplicationException | ExceptionDB | NamingException ex) {
            LOGGER.error("Error al actualizar la dirección EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al actualizar la dirección EX000 " + ex.getMessage(), ex);
        }
        return ok;
    }

    /**
     *
     * @param estadoFinal
     * @param fileName
     * @param user
     * @param nombreFuncionalidad
     * @param solRed
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static String updateSolicitudRedById(String estadoFinal, String fileName, String user, String nombreFuncionalidad, SolicitudRed solRed) throws ApplicationException {
        String mensaje = "";
        try {
            SolicitudHHPPDisenioRedEJBRemote obj = getProcesarSolicitudModHHPPSolRedEjb();
            if (obj != null) {
                mensaje = obj.updateSolicitudRedById(estadoFinal, fileName, user, nombreFuncionalidad, solRed);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al actualizar la solicitud de red por Id. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al actualizar la solicitud de red por Id. EX000 " + ex.getMessage(), ex);
        }
        return mensaje;
    }

    /**
     *
     * @param detalleAConsultar
     * @param subdirIdDir
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static ConsultaSolRedModificacion complementarSalidaModHHPP(ConsultaSolRedModificacion detalleAConsultar, BigDecimal subdirIdDir) throws ApplicationException {
        try {
            SolicitudHHPPDisenioRedEJBRemote obj = getProcesarSolicitudModHHPPSolRedEjb();
            if (obj != null) {
                detalleAConsultar = obj.complementarSalidaModHHPP(detalleAConsultar, subdirIdDir);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al complementar la modificacion EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al complementar la modificacion EX000 " + ex.getMessage(), ex);
        }

        return detalleAConsultar;
    }
}
