package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.DetalleSolicitud;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.SolicitudRed;
import co.com.telmex.catastro.services.solicitud.SolicitudRedEJBRemote;
import com.jlcg.db.exept.ExceptionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase SolicitudRedDelegate
 *
 * @author Ana María Malambo
 * @version	1.0
 */
public class SolicitudRedDelegate {

    private static String SOLICITUDREDEJB = "solicitudRedEJB#co.com.telmex.catastro.services.solicitud.SolicitudRedEJBRemote";
    private static final Logger LOGGER = LogManager.getLogger(SolicitudRedDelegate.class);

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     * @throws javax.naming.NamingException
     */
    public static SolicitudRedEJBRemote getSolicitudRedEjb() throws ApplicationException, NamingException {
        InitialContext ctx = new InitialContext();
        SolicitudRedEJBRemote obj = (SolicitudRedEJBRemote) ctx.lookup(SOLICITUDREDEJB);
        return obj;
    }

    /**
     *
     * @param detalles
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static BigDecimal createSolicitudPlaneacionRedMasivo(List<DetalleSolicitud> detalles) throws ApplicationException {
        BigDecimal idSre = BigDecimal.ZERO;
        try {
            SolicitudRedEJBRemote obj = getSolicitudRedEjb();
            if (obj != null) {
                idSre = obj.createSolicitudPlaneacionRedMasivo(detalles);
            }
        } catch (NamingException e) {
            LOGGER.error("Error al crear solicitud de planeación de red. EX000 " + e.getMessage(), e);
        }catch (Exception e) {
            LOGGER.error("Error al crear solicitud de planeación de red. EX000 " + e.getMessage(), e);
        }
        return idSre;
    }

    /**
     *
     * @param detalles
     * @param funcionalidad
     * @param user
     * @return
     * @throws Exception
     */

    /**
     *
     * @param codDir
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static BigDecimal consultaIdHHP(String codDir) throws ApplicationException {
        BigDecimal idHhp = BigDecimal.ZERO;
        try {
            SolicitudRedEJBRemote obj = getSolicitudRedEjb();
            if (obj != null) {
                idHhp = obj.consultaIdHHP(codDir);
            }
        } catch (NamingException e) {
            LOGGER.error("Error al consultar HHPP por codigo dirección. EX000 " + e.getMessage(), e);
        }catch (Exception e) {
            LOGGER.error("Error al consultar HHPP por codigo dirección. EX000 " + e.getMessage(), e);
        }
        return idHhp;
    }

    /**
     *
     * @param codDir
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static BigDecimal consultaIdHHPSubDir(String codDir) throws ApplicationException {
        BigDecimal idHhpSub = BigDecimal.ZERO;
        try {
            SolicitudRedEJBRemote obj = getSolicitudRedEjb();
            if (obj != null) {
                idHhpSub = obj.consultaIdHHPSubDir(codDir);
            }
        } catch (NamingException e) {
            LOGGER.error("Error al consultar HHPP por subdirección. EX000 " + e.getMessage(), e);
        }catch (Exception e) {
            LOGGER.error("Error al consultar HHPP por subdirección. EX000 " + e.getMessage(), e);
        }
        return idHhpSub;
    }

    /**
     *
     * @param codigoNodo
     * @param gpo_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static Nodo queryNodo(String codigoNodo, BigDecimal gpo_id) throws ApplicationException {
        Nodo nodo = null;
        try {
            SolicitudRedEJBRemote obj = getSolicitudRedEjb();
            if (obj != null) {
                nodo = obj.queryNodo(codigoNodo, gpo_id);
            }
        } catch (NamingException e) {
            LOGGER.error("Error al consultar nodo. EX000 " + e.getMessage(), e);
        }catch (Exception e) {
            LOGGER.error("Error al consultar nodo. EX000 " + e.getMessage(), e);
        }
        return nodo;
    }

    /**
     *
     * @param idNodo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static Nodo queryNodoById(String idNodo) throws ApplicationException {
        Nodo nodo = null;
        try {
            SolicitudRedEJBRemote obj = getSolicitudRedEjb();
            if (obj != null) {
                nodo = obj.queryNodoById(idNodo);
            }
        } catch (NamingException e) {
            LOGGER.error("Error al consultar nodo por id. EX000 " + e.getMessage(), e);
        }catch (Exception e) {
            LOGGER.error("Error al consultar nodo por id. EX000 " + e.getMessage(), e);
        }
        return nodo;
    }

    /**
     *
     * @param gpo_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static GeograficoPolitico queryGeograficoPoliticoId(BigDecimal gpo_id) throws ApplicationException {
        GeograficoPolitico gpo = new GeograficoPolitico();
        try {
            SolicitudRedEJBRemote obj = getSolicitudRedEjb();
            if (obj != null) {
                gpo = obj.queryGeograficoPoliticoId(gpo_id);
            }
        } catch (NamingException e) {
            LOGGER.error("Error al consultar geografico politico por id. EX000 " + e.getMessage(), e);
        }catch (Exception e) {
            LOGGER.error("Error al consultar geografico politico por id. EX000 " + e.getMessage(), e);
        }
        return gpo;
    }

    /**
     *
     * @param gpo_name
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static GeograficoPolitico queryGeograficoPolitico(String gpo_name) throws ApplicationException {
        GeograficoPolitico gpo = new GeograficoPolitico();
        try {
            SolicitudRedEJBRemote obj = getSolicitudRedEjb();
            if (obj != null) {
                gpo = obj.queryGeograficoPolitico(gpo_name);
            }
        } catch (NamingException e) {
            LOGGER.error("Error al consultar geografico politico por nombre. EX000 " + e.getMessage(), e);
        }catch (Exception e) {
            LOGGER.error("Error al consultar geografico politico por nombre. EX000 " + e.getMessage(), e);
        }
        return gpo;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<SolicitudRed> querySolicitudesRedPendientes() throws ApplicationException {
        List<SolicitudRed> solicitudes = new ArrayList<SolicitudRed>();
        try {
            SolicitudRedEJBRemote obj = getSolicitudRedEjb();
            if (obj != null) {
                solicitudes = obj.querySolicitudesRedPendientes();
            }
        } catch (NamingException e) {
            LOGGER.error("Error consultar solicitudes de red pendientes. EX000 " + e.getMessage(), e);
        }catch (Exception e) {
            LOGGER.error("Error consultar solicitudes de red pendientes. EX000 " + e.getMessage(), e);
        }
        return solicitudes;
    }

    /**
     *
     * @param idSre
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static int queryQDetalleSolicitud(BigDecimal idSre) throws ApplicationException {
        int qHhpp = 0;
        try {
            SolicitudRedEJBRemote obj = getSolicitudRedEjb();
            if (obj != null) {
                qHhpp = obj.queryQDetalleSolicitud(idSre);
            }
        } catch (NamingException e) {
            LOGGER.error("Error consultar detalle de la solicitud. EX000 " + e.getMessage(), e);
        }catch (Exception e) {
            LOGGER.error("Error consultar detalle de la solicitud. EX000 " + e.getMessage(), e);
        }
        return qHhpp;
    }

    public static List<DetalleSolicitud> ConsultarSolicitudDeRed(BigDecimal idSolicitud) throws ApplicationException {
        List<DetalleSolicitud> listaDetalleSolicitud = null;
        try {
            SolicitudRedEJBRemote obj = getSolicitudRedEjb();
            if (obj != null) {
                listaDetalleSolicitud = obj.ConsultarSolicitudDeRed(idSolicitud);
            }
        } catch (ApplicationException | ExceptionDB | NamingException e) {
            LOGGER.error("Error al consultar solicitud de red. EX000 " + e.getMessage(), e);
        }catch (Exception e) {
            LOGGER.error("Error al consultar solicitud de red. EX000 " + e.getMessage(), e);
        }
        return listaDetalleSolicitud;
    }

    public static List<DetalleSolicitud> procesarSolicitudDeRed(List<DetalleSolicitud> listaDetalleSolicitud) throws ApplicationException {
        try {
            SolicitudRedEJBRemote obj = getSolicitudRedEjb();
            if (obj != null) {
                listaDetalleSolicitud = obj.procesarSolicitudDeRed(listaDetalleSolicitud);
            }
        } catch (ApplicationException | ExceptionDB | NamingException e) {
            LOGGER.error("Error al procesar solicitud de red. EX000 " + e.getMessage(), e);
        }catch (Exception e) {
            LOGGER.error("Error al procesar solicitud de red. EX000 " + e.getMessage(), e);
        }
        return listaDetalleSolicitud;
    }

}
