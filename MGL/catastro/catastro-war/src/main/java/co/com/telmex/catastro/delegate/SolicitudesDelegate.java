package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.SolicitudNegocio;
import co.com.telmex.catastro.data.SolicitudRed;
import co.com.telmex.catastro.services.solicitud.SolicitudesEstadoEJBRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase SolicitudesDelegate
 *
 * @author Deiver Rovira
 * @version	1.0
 */
public class SolicitudesDelegate {

    private static final Logger LOGGER = LogManager.getLogger(SolicitudesDelegate.class);

    private static String SOLICITUDESTADOPEJB = "solicitudesEstadoEJB#co.com.telmex.catastro.services.solicitud.SolicitudesEstadoEJBRemote";

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     * @throws javax.naming.NamingException
     */
    public static SolicitudesEstadoEJBRemote getSolEstadoEjb() throws ApplicationException, NamingException {
        InitialContext ctx = new InitialContext();
        SolicitudesEstadoEJBRemote obj = (SolicitudesEstadoEJBRemote) ctx.lookup(SOLICITUDESTADOPEJB);
        return obj;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<SolicitudNegocio> querySolicitudesNegocio() throws ApplicationException {
        List<SolicitudNegocio> list = null;
        try {
            SolicitudesEstadoEJBRemote obj = getSolEstadoEjb();
            if (obj != null) {
                list = obj.querySolicitudesNegocio();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar solicitudes de negocio. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar solicitudes de negocio. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<SolicitudRed> querySolicitudesRed() throws ApplicationException {
        List<SolicitudRed> list = null;
        try {
            SolicitudesEstadoEJBRemote obj = getSolEstadoEjb();
            if (obj != null) {
                list = obj.querySolicitudesRed();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar solicitudes de red. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar solicitudes de red. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @param id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static SolicitudNegocio querySolNegocioById(String id) throws ApplicationException {
        SolicitudNegocio solNeg = null;
        try {
            SolicitudesEstadoEJBRemote obj = getSolEstadoEjb();
            if (obj != null) {
                solNeg = obj.querySolNegocioById(id);
            }
        } catch (ApplicationException | NamingException ex) {
            LOGGER.error("Error al consultar solicitud de negocio por id. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar solicitud de negocio por id. EX000 " + ex.getMessage(), ex);
        }
        return solNeg;
    }

    /**
     *
     * @param id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static SolicitudRed querySolRedById(String id) throws ApplicationException {
        SolicitudRed solRed = null;
        try {
            SolicitudesEstadoEJBRemote obj = getSolEstadoEjb();
            if (obj != null) {
                solRed = obj.querySolRedById(id);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar solicitud de red por id. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar solicitud de red por id. EX000 " + ex.getMessage(), ex);
        }
        return solRed;
    }

    /**
     *
     * @param estadoFinal
     * @param user
     * @param solicitudNegocio
     * @param idSolNeg
     * @param nombreFuncionalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static String updateStateSolNegocioById(String estadoFinal, String user, SolicitudNegocio solicitudNegocio, String idSolNeg, String nombreFuncionalidad) throws ApplicationException {
        String mensaje = "";
        try {
            SolicitudesEstadoEJBRemote obj = getSolEstadoEjb();
            if (obj != null) {
                mensaje = obj.updateStateSolNegocioById(estadoFinal, user, solicitudNegocio, idSolNeg, nombreFuncionalidad);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al actualizar el estado de la solicitud de negocio. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al actualizar el estado de la solicitud de negocio. EX000 " + ex.getMessage(), ex);
        }
        return mensaje;
    }

    /**
     *
     * @param estadoFinal
     * @param user
     * @param solRed
     * @param idSolRed
     * @param nombreFuncionalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static String updateStateSolRedById(String estadoFinal, String user, SolicitudRed solRed, String idSolRed, String nombreFuncionalidad) throws ApplicationException {
        String mensaje = "";
        try {
            SolicitudesEstadoEJBRemote obj = getSolEstadoEjb();
            if (obj != null) {
                mensaje = obj.updateStateSolRedById(estadoFinal, user, solRed, idSolRed, nombreFuncionalidad);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al actualizar el estado de la solicitud de red. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al actualizar el estado de la solicitud de red. EX000 " + ex.getMessage(), ex);
        }
        return mensaje;
    }

    /**
     *
     * @param fileName
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static SolicitudNegocio querySolNegocioByFileName(String fileName) throws ApplicationException {
        SolicitudNegocio solNeg = null;
        try {
            SolicitudesEstadoEJBRemote obj = getSolEstadoEjb();
            if (obj != null) {
                solNeg = obj.querySolNegocioByFileName(fileName);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar la solicitud de negocio por nombre de archivo. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar la solicitud de negocio por nombre de archivo. EX000 " + ex.getMessage(), ex);
        }
        return solNeg;
    }

    /**
     *
     * @param fileName
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<SolicitudNegocio> querySolNegociosByFileName(String fileName) throws ApplicationException {
        List<SolicitudNegocio> solNeg = null;
        try {
            SolicitudesEstadoEJBRemote obj = getSolEstadoEjb();
            if (obj != null) {
                solNeg = obj.querySolNegociosByFileName(fileName);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar la solicitud de negocio por nombre de archivo. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar la solicitud de negocio por nombre de archivo. EX000 " + ex.getMessage(), ex);
        }
        return solNeg;
    }

    /**
     *
     * @param fileName
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<SolicitudRed> querySolRedesByFileName(String fileName) throws ApplicationException {
        List<SolicitudRed> solRed = null;
        try {
            SolicitudesEstadoEJBRemote obj = getSolEstadoEjb();
            if (obj != null) {
                solRed = obj.querySolRedesByFileName(fileName);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar la solicitud de redes por nombre de archivo. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar la solicitud de redes por nombre de archivo. EX000 " + ex.getMessage(), ex);
        }
        return solRed;
    }
}
