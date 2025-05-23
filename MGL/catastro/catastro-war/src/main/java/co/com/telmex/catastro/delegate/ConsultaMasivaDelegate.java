package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.services.direcciones.ConsultaMasivaEJBRemote;
import co.com.telmex.catastro.services.util.ConsultaMasivaTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase ConsultaMasivaDelegate
 *
 * @author Deiver Rovira.
 * @version	1.0
 */
public class ConsultaMasivaDelegate {

    private static final Logger LOGGER = LogManager.getLogger(ConsultaMasivaDelegate.class);

    private static String CONSULTAMASIVAEJB = "consultaMasivaEJB#co.com.telmex.catastro.services.direcciones.ConsultaMasivaEJBRemote";

    /**
     *
     * @return
     * @throws javax.naming.NamingException @throws Exception
     */
    public static ConsultaMasivaEJBRemote getConsultaMasivaEjb() throws NamingException {
        InitialContext ctx = new InitialContext();
        ConsultaMasivaEJBRemote obj = (ConsultaMasivaEJBRemote) ctx.lookup(CONSULTAMASIVAEJB);
        return obj;
    }

    /**
     *
     * @param calle
     * @param cod_ciudad
     * @param cantMaxregistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<ConsultaMasivaTable> queryConsultaMasivaPorCruce(String calle, String cod_ciudad, String cantMaxregistros) throws ApplicationException {
        List<ConsultaMasivaTable> list = null;
        try {
            ConsultaMasivaEJBRemote obj = getConsultaMasivaEjb();
            if (obj != null) {
                list = obj.queryConsultaMasivaPorCruce(calle, cod_ciudad, cantMaxregistros);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar por cruce. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar por cruce. EX000 " + ex.getMessage(), ex);
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
     * @param cod_ciudad
     * @param cantMaxregistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<ConsultaMasivaTable> queryConsultaMasivaPorPropiedades(String nodo, String ctaMatriz, String tipoRed, String estrato,
            String nivelSocio, String barrio, String cod_ciudad, String cantMaxregistros) throws ApplicationException {
        List<ConsultaMasivaTable> list = null;
        try {
            ConsultaMasivaEJBRemote obj = getConsultaMasivaEjb();
            if (obj != null) {
                list = obj.queryConsultaMasivaPorPropiedades(nodo, ctaMatriz, tipoRed, estrato, nivelSocio, barrio, cod_ciudad, cantMaxregistros);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar por propiedades. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar por propiedades. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<Nodo> queryNodos() throws ApplicationException {
        List<Nodo> list = null;
        try {
            ConsultaMasivaEJBRemote obj = getConsultaMasivaEjb();
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
     * @param acronimo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static int queryCantMaxRegistrosFromParametros(String acronimo) throws ApplicationException {
        int nivelConfiabilidad = 95;
        try {
            ConsultaMasivaEJBRemote obj = getConsultaMasivaEjb();
            if (obj != null) {
                nivelConfiabilidad = obj.queryCantMaxRegistrosFromParametros(acronimo);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar la cantidad máxima según los parámetros. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar la cantidad máxima según los parámetros. EX000 " + ex.getMessage(), ex);
        }
        return nivelConfiabilidad;
    }
}
