package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.CargueGeografico;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.TipoHhpp;
import co.com.telmex.catastro.data.TipoHhppConexion;
import co.com.telmex.catastro.data.TipoHhppRed;
import co.com.telmex.catastro.data.TipoMarcas;
import co.com.telmex.catastro.services.util.CargueGeograficoEJBRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase CargueGeograficoDelegate
 *
 * @author Deiver Rovira.
 * @version	1.0
 */
public class CargueGeograficoDelegate {

    private static final Logger LOGGER = LogManager.getLogger(CargueGeograficoDelegate.class);

    private static String CARGUEGEOGRAFICOEJB = "cargueGeograficoEJB#co.com.telmex.catastro.services.util.CargueGeograficoEJBRemote";

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     * @throws javax.naming.NamingException
     */
    public static CargueGeograficoEJBRemote getSolicitudNEjb() throws ApplicationException, NamingException {
        InitialContext ctx = new InitialContext();
        CargueGeograficoEJBRemote obj = (CargueGeograficoEJBRemote) ctx.lookup(CARGUEGEOGRAFICOEJB);
        return obj;
    }

    /**
     *
     * @param solicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static BigDecimal insertSolicitudNegocio(CargueGeografico solicitud) throws ApplicationException {
        BigDecimal idSon = null;
        try {
            CargueGeograficoEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                idSon = obj.insertSolicitudNegocio(solicitud);
            }
        } catch (ApplicationException| NamingException ex) {
            LOGGER.error("Error al agregar la solicitud de negocio. EX000 " + ex.getMessage(), ex);
        }
        return idSon;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<Multivalor> loadTiposSolicitud() throws ApplicationException {
        List<Multivalor> tiposSolicitud = null;
        try {
            CargueGeograficoEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                tiposSolicitud = obj.loadTiposSolicitud();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al cargar los tipos de solicitud. EX000 " + ex.getMessage(), ex);
        }
        return tiposSolicitud;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<GeograficoPolitico> queryPaises() throws ApplicationException {
        List<GeograficoPolitico> regionales = null;
        try {
            CargueGeograficoEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                regionales = obj.queryPaises();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar paises. EX000 " + ex.getMessage(), ex);
        }
        return regionales;
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
            CargueGeograficoEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                regionales = obj.queryRegionales(idPais);
            }
        } catch (NamingException ex) {
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
            CargueGeograficoEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                ciudades = obj.queryCiudades(idRegional);
            }
        } catch (NamingException ex) {
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
            CargueGeograficoEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                neighborhood = obj.queryNeighborhood(neighborhoodName, gpo_id);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar neigborhood. EX000 " + ex.getMessage(), ex);
        }
        return neighborhood;
    }

    /**
     *
     * @param nombreNodo
     * @param gpo_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static Nodo queryNodo(String nombreNodo, BigDecimal gpo_id) throws ApplicationException {
        Nodo nodo = null;
        try {
            nodo = SolicitudRedDelegate.queryNodo(nombreNodo, gpo_id);
        } catch (ApplicationException ex) {
            LOGGER.error("Error al consultar nodo_. EX000 " + ex.getMessage(), ex);
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
            CargueGeograficoEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                calles = obj.queryCalles();
            }
        } catch (NamingException ex) {
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
            CargueGeograficoEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                cardinales = obj.queryCardinales();
            }
        } catch (NamingException ex) {
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
            CargueGeograficoEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                letras = obj.queryLetras();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar letras. EX000 " + ex.getMessage(), ex);
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
            CargueGeograficoEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                prefijos = obj.queryPrefijos();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error eal consulta prefijos. EX000 " + ex.getMessage(), ex);
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
            CargueGeograficoEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                tunidades = obj.queryTipoHhpp();
            }
        } catch (NamingException ex) {
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
            CargueGeograficoEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                tconexiones = obj.queryTipoConexion();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar tipos de conexción. EX000 " + ex.getMessage(), ex);
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
            CargueGeograficoEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                tred = obj.queryTipoRed();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar tipo de red. EX000 " + ex.getMessage(), ex);
        }
        return tred;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<TipoMarcas> queryBlackList() throws ApplicationException {
        List<TipoMarcas> tblackl = null;
        try {
            CargueGeograficoEJBRemote obj = getSolicitudNEjb();
            if (obj != null) {
                tblackl = obj.queryBlackList();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar la lista negra. EX000 " + ex.getMessage(), ex);
        }
        return tblackl;
    }
}
