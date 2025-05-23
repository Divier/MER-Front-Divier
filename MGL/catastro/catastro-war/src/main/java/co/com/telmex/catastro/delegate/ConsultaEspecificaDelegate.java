package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.services.direcciones.ConsultaEspecificaEJBRemote;
import co.com.telmex.catastro.services.util.ConsultaMasivaTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase ConsultaEspecificaDelegate
 *
 * @author 	Deiver Rovira.
 * @version	1.0
 */
public class ConsultaEspecificaDelegate {
    
    private static final Logger LOGGER = LogManager.getLogger(ConsultaEspecificaDelegate.class);

    private static String CONSULTAESPECIFICAEJB = "consultaEspecificaEJB#co.com.telmex.catastro.services.direcciones.ConsultaEspecificaEJBRemote";

    /**
     * 
     * @return
     * @throws javax.naming.NamingException
     */
    public static ConsultaEspecificaEJBRemote getConsultaEspecificaEjb() throws NamingException {
        InitialContext ctx = new InitialContext();
        ConsultaEspecificaEJBRemote obj = (ConsultaEspecificaEJBRemote) ctx.lookup(CONSULTAESPECIFICAEJB);
        return obj;
    }

    /**
     * 
     * @param nodo
     * @param ctaMatriz
     * @param tipoRed
     * @param estrato
     * @param nivelSocio
     * @param barrio
     * @param igac
     * @param codCiudad
     * @param cantMaxRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<ConsultaMasivaTable> queryConsultaEspecificaFiltroEmpiezaPor(String nodo, String ctaMatriz, String tipoRed, String estrato,
            String nivelSocio, String barrio, String igac, String codCiudad, String cantMaxRegistros) throws ApplicationException {
        List<ConsultaMasivaTable> list = null;
        try {
            ConsultaEspecificaEJBRemote obj = getConsultaEspecificaEjb();
            if (obj != null) {
                list = obj.queryConsultaEspecificaFiltroEmpiezaPor(nodo, ctaMatriz, tipoRed, estrato, nivelSocio, barrio, igac, codCiudad, cantMaxRegistros);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar por filtro especifico. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar por filtro especifico. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     * 
     * @param nodo
     * @param ctaMatriz
     * @param tipoRed
     * @param estrato
     * @param nivelSocio
     * @param barrio
     * @param tipoFiltro
     * @param codCiudad
     * @param cantMaxRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<ConsultaMasivaTable> queryConsultaEspecificaFiltroTerminaCon(String nodo, String ctaMatriz, String tipoRed, String estrato,
            String nivelSocio, String barrio, String tipoFiltro, String codCiudad, String cantMaxRegistros) throws ApplicationException {
        List<ConsultaMasivaTable> list = null;
        try {
            ConsultaEspecificaEJBRemote obj = getConsultaEspecificaEjb();
            if (obj != null) {
                list = obj.queryConsultaEspecificaFiltroTerminaCon(nodo, ctaMatriz, tipoRed, estrato, nivelSocio, barrio, tipoFiltro, codCiudad, cantMaxRegistros);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar por filtro termina con. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar por filtro termina con. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     * 
     * @param nodo
     * @param ctaMatriz
     * @param tipoRed
     * @param estrato
     * @param nivelSocio
     * @param barrio
     * @param tipoFiltro
     * @param codCiudad
     * @param cantMaxRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<ConsultaMasivaTable> queryConsultaEspecificaFiltroNoContiene(String nodo, String ctaMatriz, String tipoRed, String estrato,
            String nivelSocio, String barrio, String tipoFiltro, String codCiudad, String cantMaxRegistros) throws ApplicationException {
        List<ConsultaMasivaTable> list = null;
        try {
            ConsultaEspecificaEJBRemote obj = getConsultaEspecificaEjb();
            if (obj != null) {
                list = obj.queryConsultaEspecificaFiltroNoContiene(nodo, ctaMatriz, tipoRed, estrato, nivelSocio, barrio, tipoFiltro, codCiudad, cantMaxRegistros);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar con filtro no contiene. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar con filtro no contiene. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     * 
     * @param nodo
     * @param ctaMatriz
     * @param tipoRed
     * @param estrato
     * @param nivelSocio
     * @param barrio
     * @param tipoFiltro
     * @param codCiudad
     * @param cantMaxRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<ConsultaMasivaTable> queryConsultaEspecificaFiltroEsIgualA(String nodo, String ctaMatriz, String tipoRed, String estrato,
            String nivelSocio, String barrio, String tipoFiltro, String codCiudad, String cantMaxRegistros) throws ApplicationException {
        List<ConsultaMasivaTable> list = null;
        try {
            ConsultaEspecificaEJBRemote obj = getConsultaEspecificaEjb();
            if (obj != null) {
                list = obj.queryConsultaEspecificaFiltroEsIgualA(nodo, ctaMatriz, tipoRed, estrato, nivelSocio, barrio, tipoFiltro, codCiudad, cantMaxRegistros);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar por filtro es igual a. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar por filtro es igual a. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     * 
     * @param nodo
     * @param ctaMatriz
     * @param tipoRed
     * @param estrato
     * @param nivelSocio
     * @param barrio
     * @param tipoFiltro
     * @param codCiudad
     * @param cantMaxRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<ConsultaMasivaTable> queryConsultaEspecificaFiltroComodin(String nodo, String ctaMatriz, String tipoRed, String estrato,
            String nivelSocio, String barrio, String tipoFiltro, String codCiudad, String cantMaxRegistros) throws ApplicationException {
        List<ConsultaMasivaTable> list = null;
        try {
            ConsultaEspecificaEJBRemote obj = getConsultaEspecificaEjb();
            if (obj != null) {
                list = obj.queryConsultaEspecificaFiltroComodin(nodo, ctaMatriz, tipoRed, estrato, nivelSocio, barrio, tipoFiltro, codCiudad, cantMaxRegistros);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar por filtro comodin. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar por filtro comodin. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<Nodo> queryNodos() throws ApplicationException {
        List<Nodo> list = null;
        try {
            ConsultaEspecificaEJBRemote obj = getConsultaEspecificaEjb();
            if (obj != null) {
                list = obj.queryNodos();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar nodos. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar nodos. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     * 
     * @param idDir
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static Direccion queryAddressOnRepoById(String idDir) throws ApplicationException {
        Direccion direccion = null;
        try {
            ConsultaEspecificaEJBRemote obj = getConsultaEspecificaEjb();
            if (obj != null) {
                direccion = obj.queryAddressOnRepoById(idDir);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar direcciones en el reporte por id. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar direcciones en el reporte por id. EX000 " + ex.getMessage(), ex);
        }
        return direccion;
    }

    /**
     * 
     * @param igac
     * @param idCiudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<Direccion> queryAddressOnRepoByIgac(String igac, String idCiudad) throws ApplicationException {
        List<Direccion> direcciones = null;
        try {
            ConsultaEspecificaEJBRemote obj = getConsultaEspecificaEjb();
            if (obj != null) {
                direcciones = obj.queryAddressOnRepoByIgac(igac, idCiudad);
            }
        } catch (NamingException ex) {
            LOGGER.error("Erroral consultar dirección en el reoprte por IGAC. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Erroral consultar dirección en el reoprte por IGAC. EX000 " + ex.getMessage(), ex);
        }
        return direcciones;
    }

    /**
     * 
     * @param acronimo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static int queryCantMaxRegistrosFromParametros(String acronimo) throws ApplicationException {
        int nivelConfiabilidad = 95;
        try {
            ConsultaEspecificaEJBRemote obj = getConsultaEspecificaEjb();
            if (obj != null) {
                nivelConfiabilidad = obj.queryCantMaxRegistrosFromParametros(acronimo);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar cantidad maxima de registros según parametros. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar cantidad maxima de registros según parametros. EX000 " + ex.getMessage(), ex);
        }
        return nivelConfiabilidad;
    }
}
