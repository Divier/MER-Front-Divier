package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Marcas;
import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.SolicitudNegocio;
import co.com.telmex.catastro.data.TipoHhpp;
import co.com.telmex.catastro.data.TipoHhppConexion;
import co.com.telmex.catastro.data.TipoHhppRed;
import co.com.telmex.catastro.services.solicitud.SolicitudNegocioEJBRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase SolicitudNegocioDelegate
 *
 * @author Nataly Orozco Torres
 * @version	1.0
 */
public class SolicitudNegocioDelegate {

    private static final Logger LOGGER = LogManager.getLogger(SolicitudNegocioDelegate.class);

    private static String SOLICITUDNEGOCIOEJB = "solicitudNegocioEJB#co.com.telmex.catastro.services.solicitud.SolicitudNegocioEJBRemote";

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     * @throws javax.naming.NamingException
     */
    public static SolicitudNegocioEJBRemote getSolicitudNEjb() throws ApplicationException, NamingException {
        InitialContext ctx = new InitialContext();
        SolicitudNegocioEJBRemote obj = (SolicitudNegocioEJBRemote) ctx.lookup(SOLICITUDNEGOCIOEJB);
        return obj;
    }

    /**
     *
     * @param nombreFuncionalidad
     * @param solicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static BigDecimal insertSolicitudNegocio(String nombreFuncionalidad, SolicitudNegocio solicitud) throws ApplicationException {
        BigDecimal idSon = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                idSon = obj.insertSolicitudNegocio(nombreFuncionalidad, solicitud);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al agregar la solicitud de negocio. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al agregar la solicitud de negocio. EX000 " + ex.getMessage(), ex);
        }
        return idSon;
    }

    /**
     *
     * @param estado
     * @param gpo
     * @param tipoSolicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<SolicitudNegocio> querySolicitudesPorGestionar(String estado, GeograficoPolitico gpo, String tipoSolicitud) throws ApplicationException {
        List<SolicitudNegocio> solicitudes = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                solicitudes = obj.querySolicitudesPorGestionar(estado, gpo, tipoSolicitud);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar las solicitudes por gestionar. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar las solicitudes por gestionar. EX000 " + ex.getMessage(), ex);
        }
        return solicitudes;
    }

    /**
     *
     * @param tipoSolicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<SolicitudNegocio> querySolicitudesPorProcesar(String tipoSolicitud) throws ApplicationException {
        List<SolicitudNegocio> solicitudes = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                solicitudes = obj.querySolicitudesPorProcesar(tipoSolicitud);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar las solicitudes por gestionar. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar las solicitudes por gestionar. EX000 " + ex.getMessage(), ex);
        }
        return solicitudes;
    }

    /**
     *
     * @param nombreArchivo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<SolicitudNegocio> querySolicitudesParaRR(String nombreArchivo) throws ApplicationException {
        List<SolicitudNegocio> solicitudes = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                solicitudes = obj.querySolicitudesParaRR(nombreArchivo);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar las solicitudes para RR. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar las solicitudes para RR. EX000 " + ex.getMessage(), ex);
        }
        return solicitudes;
    }

    /**
     *
     * @param nombreFuncionalidad
     * @param user
     * @param solicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static boolean updateSolicitudNegocio(String nombreFuncionalidad, String user, SolicitudNegocio solicitud) throws ApplicationException {
        boolean result = false;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                result = obj.updateSolicitudNegocio(nombreFuncionalidad, user, solicitud);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al actualizar la solicitud de negocio. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al actualizar la solicitud de negocio. EX000 " + ex.getMessage(), ex);
        }
        return result;
    }

    /**
     *
     * @param solicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static boolean cancelSolicitudNegocio(SolicitudNegocio solicitud) throws ApplicationException {
        boolean result = false;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                result = obj.cancelSolicitudNegocio(solicitud);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al cancelar la solicitud de negocio. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al cancelar la solicitud de negocio. EX000 " + ex.getMessage(), ex);
        }
        return result;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<Multivalor> loadTiposSolicitud() throws ApplicationException {
        List<Multivalor> tiposSolicitud = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                tiposSolicitud = obj.loadTiposSolicitud();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al cargar los tipos de solicitud. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al cargar los tipos de solicitud. EX000 " + ex.getMessage(), ex);
        }
        return tiposSolicitud;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<Multivalor> loadTiposSolicitudModificacion() throws ApplicationException {
        List<Multivalor> tiposSolicitud = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                tiposSolicitud = obj.loadTiposSolicitudModificacion();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al cargar los tipos de modificación de solicitud. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al cargar los tipos de modificación de solicitud. EX000 " + ex.getMessage(), ex);
        }
        return tiposSolicitud;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<Multivalor> loadTiposRespuesta() throws ApplicationException {
        List<Multivalor> tiposRespuesta = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                tiposRespuesta = obj.loadTiposRespuesta();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al cargar los tipos de respuesta. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al cargar los tipos de respuesta. EX000 " + ex.getMessage(), ex);
        }
        return tiposRespuesta;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<Multivalor> loadTiposRespuestaModificacion() throws ApplicationException {
        List<Multivalor> tiposRespuesta = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                tiposRespuesta = obj.loadTiposRespuestaModificacion();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al cargar los tipo s de modificacion de respuesta. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al cargar los tipo s de modificacion de respuesta. EX000 " + ex.getMessage(), ex);
        }
        return tiposRespuesta;
    }

    /**
     *
     * @param idGrupoMultivalor
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<Multivalor> loadMultivalores(String idGrupoMultivalor) throws ApplicationException {
        List<Multivalor> multivalores = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                multivalores = obj.loadMultivalores(idGrupoMultivalor);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al cargar multivalores. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al cargar multivalores. EX000 " + ex.getMessage(), ex);
        }
        return multivalores;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<GeograficoPolitico> queryPaises() throws ApplicationException {
        List<GeograficoPolitico> regionales = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                regionales = obj.queryPaises();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar paises. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar paises. EX000 " + ex.getMessage(), ex);
        }
        return regionales;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<Multivalor> queryEstrato() throws ApplicationException {
        List<Multivalor> estrato = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                estrato = obj.queryEstrato();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar estrato EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar estrato EX000 " + ex.getMessage(), ex);
        }
        return estrato;
    }

    /**
     *
     * @param idPais
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<GeograficoPolitico> queryRegionales(BigDecimal idPais) throws ApplicationException {
        List<GeograficoPolitico> regionales = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                regionales = obj.queryRegionales(idPais);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar regionales. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar regionales. EX000 " + ex.getMessage(), ex);
        }
        return regionales;
    }

    /**
     *
     * @param idRegional
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<GeograficoPolitico> queryCiudades(BigDecimal idRegional) throws ApplicationException {
        List<GeograficoPolitico> ciudades = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                ciudades = obj.queryCiudades(idRegional);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar ciudades. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar ciudades. EX000 " + ex.getMessage(), ex);
        }
        return ciudades;
    }

    /**
     *
     * @param neighborhoodName
     * @param gpo_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static Geografico queryNeighborhood(String neighborhoodName, BigDecimal gpo_id) throws ApplicationException {
        Geografico neighborhood = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                neighborhood = obj.queryNeighborhood(neighborhoodName, gpo_id);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar neighborhood. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar neighborhood. EX000 " + ex.getMessage(), ex);
        }
        return neighborhood;
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
            nodo = SolicitudRedDelegate.queryNodo(codigoNodo, gpo_id);
        } catch (ApplicationException ex) {
            LOGGER.error("Error al consultar nodo. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar nodo. EX000 " + ex.getMessage(), ex);
        }
        return nodo;
    }

    /**
     *
     * @param gpo_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static Nodo queryNodoNFI(BigDecimal gpo_id) throws ApplicationException {
        Nodo nodo = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                nodo = obj.queryNodoNFI(gpo_id);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar nodo FI. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar nodo FI. EX000 " + ex.getMessage(), ex);
        }
        return nodo;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<Multivalor> queryCalles() throws ApplicationException {
        List<Multivalor> calles = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                calles = obj.queryCalles();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar calles. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar calles. EX000 " + ex.getMessage(), ex);
        }
        return calles;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<Multivalor> queryCardinales() throws ApplicationException {
        List<Multivalor> cardinales = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                cardinales = obj.queryCardinales();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar cardinales. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar cardinales. EX000 " + ex.getMessage(), ex);
        }
        return cardinales;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<Multivalor> queryLetras() throws ApplicationException {
        List<Multivalor> letras = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                letras = obj.queryLetras();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error consultar letras. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error consultar letras. EX000 " + ex.getMessage(), ex);
        }
        return letras;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<Multivalor> queryLetrasyNumeros() throws ApplicationException {
        List<Multivalor> letras = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                letras = obj.queryLetrasyNumeros();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar letras números. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar letras números. EX000 " + ex.getMessage(), ex);
        }
        return letras;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<Multivalor> queryPrefijos() throws ApplicationException {
        List<Multivalor> prefijos = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                prefijos = obj.queryPrefijos();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar prefijos. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar prefijos. EX000 " + ex.getMessage(), ex);
        }
        return prefijos;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<TipoHhpp> queryTiposHhpp() throws ApplicationException {
        List<TipoHhpp> tunidades = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                tunidades = obj.queryTipoHhpp();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar tipos HHPP. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar tipos HHPP. EX000 " + ex.getMessage(), ex);
        }
        return tunidades;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<TipoHhppConexion> queryTipoConexion() throws ApplicationException {
        List<TipoHhppConexion> tconexiones = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                tconexiones = obj.queryTipoConexion();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar tipo conexión. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar tipo conexión. EX000 " + ex.getMessage(), ex);
        }
        return tconexiones;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<TipoHhppRed> queryTipoRed() throws ApplicationException {
        List<TipoHhppRed> tred = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                tred = obj.queryTipoRed();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar tipo de red. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar tipo de red. EX000 " + ex.getMessage(), ex);
        }
        return tred;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<Marcas> queryBlackList() throws ApplicationException {
        List<Marcas> tblackl = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                tblackl = obj.queryBlackList();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar lista negra. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar lista negra. EX000 " + ex.getMessage(), ex);
        }
        return tblackl;
    }

    /**
     *
     * @param idSolicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static SolicitudNegocio querySolicitudNegocio(BigDecimal idSolicitud) throws ApplicationException {
        SolicitudNegocio solicitud = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                solicitud = obj.querySolicitudNegocio(idSolicitud);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar solicitud de negocio. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar solicitud de negocio. EX000 " + ex.getMessage(), ex);
        }
        return solicitud;

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
    public static String procesarSolicitudesNegocio(List<SolicitudNegocio> solicitudes, String nombreFuncionalidad, String nombreArchivo, String user) throws ApplicationException {
        String message = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                message = obj.procesarSolicitudesNegocio(solicitudes, nombreFuncionalidad, nombreArchivo, user);
            }
        } catch (NamingException ex) {
            message = ex.getMessage();
            LOGGER.error("Error al procesar solicitudes de negocio. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            message = ex.getMessage();
            LOGGER.error("Error al procesar solicitudes de negocio. EX000 " + ex.getMessage(), ex);
        }
        return message;

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
    public static String procesarSolicitudesModificacion(List<SolicitudNegocio> solicitudes, String nombreFuncionalidad, String nombreArchivo, String user) throws ApplicationException {
        String message = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                message = obj.procesarSolicitudesModificacion(solicitudes, nombreFuncionalidad, nombreArchivo, user);
            }
        } catch (NamingException ex) {
            message = ex.getMessage();
            LOGGER.error("Error al procesar solicitudes de modificación. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            message = ex.getMessage();
            LOGGER.error("Error al procesar solicitudes de modificación. EX000 " + ex.getMessage(), ex);
        }
        return message;
    }

    /**
     *
     * @param CodDane
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static String ConsultaTipoDireccionByCodigoDane(String CodDane) throws ApplicationException {
        String message = null;
        try {
            SolicitudNegocioEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                message = obj.ConsultaTipoDireccionByCodigoDane(CodDane);
            }
        } catch (ApplicationException | NamingException ex) {
            message = ex.getMessage();
            LOGGER.error("Error al consultar tipo dirección por código dane. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            message = ex.getMessage();
            LOGGER.error("Error al consultar tipo dirección por código dane. EX000 " + ex.getMessage(), ex);
        }
        return message;
    }
}
