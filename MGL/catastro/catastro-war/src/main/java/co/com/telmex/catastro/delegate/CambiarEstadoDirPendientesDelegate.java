package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.services.direcciones.CambiarEstadoDirPendientesEJBRemote;
import co.com.telmex.catastro.services.util.ConsultaMasivaTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase CambiarEstadoDirPendientesDelegate
 *
 * @author Deiver Rovira.
 * @version	1.0
 */
public class CambiarEstadoDirPendientesDelegate {

    private static final Logger LOGGER = LogManager.getLogger(CambiarEstadoDirPendientesDelegate.class);

    private static String CAMBIARESTADODIRPDTESEJB = "cambiarEstadoDirPendientesEJB#co.com.telmex.catastro.services.direcciones.CambiarEstadoDirPendientesEJBRemote";

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static CambiarEstadoDirPendientesEJBRemote getCambiarEstadoDirPendientesEjb() throws ApplicationException {
        try {
            InitialContext ctx = new InitialContext();
            CambiarEstadoDirPendientesEJBRemote obj = (CambiarEstadoDirPendientesEJBRemote) ctx.lookup(CAMBIARESTADODIRPDTESEJB);
            return obj;
        } catch (NamingException ex) {
            String msg = "Error al momento de cambiar el estado de la dirección. EX000: ";
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de cambiar el estado de la dirección. EX000: " + ex.getMessage(), ex);
        }catch (Exception ex) {
            String msg = "Error al momento de cambiar el estado de la dirección. EX000: ";
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de cambiar el estado de la dirección. EX000: " + ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param igac
     * @param codCiudad
     * @param cantMaxRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    //Cambio para adicion de columnas en validacion de direcciones
    public static List<ConsultaMasivaTable> queryConsultaEspecificaFiltroEmpiezaPor(String igac, String codCiudad, String cantMaxRegistros) throws ApplicationException {
        List<ConsultaMasivaTable> list = null;
        CambiarEstadoDirPendientesEJBRemote obj = getCambiarEstadoDirPendientesEjb();
        if (obj != null) {
            list = obj.queryConsultaEspecificaFiltroEmpiezaPor(igac, codCiudad, cantMaxRegistros);
        }
        return list;
    }

    /**
     *
     * @param direccion
     * @param user
     * @param nombreFuncionalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static boolean updateDirRevisarByIdDir(Direccion direccion, String user, String nombreFuncionalidad) throws ApplicationException {
        boolean hayError = false;
        CambiarEstadoDirPendientesEJBRemote obj = getCambiarEstadoDirPendientesEjb();
        if (obj != null) {
            hayError = obj.updateDirRevisarByIdDir(direccion, user, nombreFuncionalidad);
        }
        return hayError;
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
        CambiarEstadoDirPendientesEJBRemote obj = getCambiarEstadoDirPendientesEjb();
        if (obj != null) {
            direcciones = obj.queryAddressOnRepoByIgac(igac, idCiudad);
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
        CambiarEstadoDirPendientesEJBRemote obj = getCambiarEstadoDirPendientesEjb();
        if (obj != null) {
            nivelConfiabilidad = obj.queryCantMaxRegistrosFromParametros(acronimo);
        }
        return nivelConfiabilidad;
    }

    /**
     *
     * @param idsDirecciones
     * @param acronimo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public static List<String> queryVerificarExistenciaPorLista(List<String> idsDirecciones) throws ApplicationException {
        List<String> lista = null;
        CambiarEstadoDirPendientesEJBRemote obj = getCambiarEstadoDirPendientesEjb();
        if (obj != null) {
            lista = obj.queryVerificarExistenciaPorLista(idsDirecciones);
        }
        return lista;
    }
}
