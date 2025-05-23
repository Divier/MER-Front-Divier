package co.com.telmex.catastro.services.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.services.comun.Constantes;
import co.com.telmex.catastro.services.comun.Utilidades;
import co.com.telmex.catastro.services.seguridad.AuditoriaEJB;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.ConsultaMasivaTable;
import co.com.telmex.catastro.services.util.DireccionUtil;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataList;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Clase CambiarEstadoDirPendientesEJB implementa
 * CambiarEstadoDirPendientesEJBRemote
 *
 * @author Deiver Rovira.
 * @version	1.0
 */
@Stateless(name = "cambiarEstadoDirPendientesEJB", mappedName = "cambiarEstadoDirPendientesEJB", description = "cambiarEstadoDirPendientes")
@Remote({CambiarEstadoDirPendientesEJBRemote.class})
public class CambiarEstadoDirPendientesEJB implements CambiarEstadoDirPendientesEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(CambiarEstadoDirPendientesEJB.class);
    private static String MESSAGE_OK = "La operación fue correcta.";
    private static String MESSAGE_ERROR = "Fallo en la operación";

    /**
     *
     * @param dirIgac
     * @param codCiudad
     * @param maxCantRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    //Se realiza cambio para adicion de columnas Ivan Turriago
    
    @Override
    public List<ConsultaMasivaTable> queryConsultaEspecificaFiltroEmpiezaPor(String dirIgac, String codCiudad, String maxCantRegistros) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("dir15", dirIgac+"%", codCiudad, maxCantRegistros);
            DireccionUtil.closeConnection(adb);

            //Se realiza cambio para reciclaje de codigo y 
            //adicion de columnas Ivan Turriago
            return Utilidades.fillTableOrigen(list);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar filtros. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza el flag de revisar en campo a false
     *
     * @param direccion
     * @param user
     * @param nombreFuncionalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public boolean updateDirRevisarByIdDir(Direccion direccion, String user, String nombreFuncionalidad) throws ApplicationException {
        boolean hayError = false;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            boolean ok = adb.in("dir16", user, direccion.getDirId().toString());

            if (ok) {
                AuditoriaEJB audi = new AuditoriaEJB();
                audi.auditar(nombreFuncionalidad, Constant.DIRECCION, user, Constant.UPDATE, direccion.auditoria(), adb);
                hayError = false;
            } else {
                hayError = true;
            }
            DireccionUtil.closeConnection(adb);
            return hayError;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al actualizar el flag de revisar en campo a false. EX000 " + e.getMessage(), e);
        }

    }

    private void queryBarrioAndLocalidadName(String idGeoGeo, ConsultaMasivaTable consulta) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("geo8", idGeoGeo);
            String nombreLocalidad = "";
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                BigDecimal id_localidad = obj.getBigDecimal("IDLOCALIDAD");
                //Se asigna el barrio
                consulta.setCm_barrio(obj.getString("NOMBRE_GEO"));
                if (id_localidad != null) {
                    nombreLocalidad = queryGeograficoNameById(id_localidad.toString());
                    consulta.setCm_localidad(nombreLocalidad);
                }
            }
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar el barrio y la localidad. EX000 " + e.getMessage(), e);
        }
    }

    private String queryGeograficoNameById(String idGeoLocalidad) throws ApplicationException {
        String geoName = "";
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("geo9", idGeoLocalidad);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                geoName = obj.getString("NOMBRE_GEO");
            }
            return geoName;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al conusltar geografico por id. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param acronimo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public int queryCantMaxRegistrosFromParametros(String acronimo) throws ApplicationException {
        int nivelConfiabilidad = 95;
        String nivelConf = "";
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("par1", acronimo);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                nivelConf = obj.getString("PAR_VALOR");
                nivelConfiabilidad = Integer.valueOf(nivelConf);
            }
            return nivelConfiabilidad;
        } catch (ExceptionDB | NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar la cantidad maxima de registros segun parametros. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param idDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    //Se implementa para verificar la si un ID de direccion
    //realmente existe en la tabla de direccion
    @Override
    public String queryAddressOnRepoById(String idDireccion) throws ApplicationException {
        String direccionId = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("dir8", idDireccion);
            if (obj != null) {
                direccionId = String.valueOf(obj.getBigDecimal(Constantes.DIR_ID));
            }
            DireccionUtil.closeConnection(adb);
            return direccionId;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar si un ID de direccion realmente existe en la tabla de direccion . EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param igac
     * @param idCiudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<Direccion> queryAddressOnRepoByIgac(String igac, String idCiudad) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // Se adiciona para cambiar la verificacion de una direccion
    // masivamente.
    /**
     *
     * @param idsDirecciones
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<String> queryVerificarExistenciaPorLista(List<String> idsDirecciones) throws ApplicationException {
        List<String> datosInvalidos = new ArrayList<String>();
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            StringBuffer direccionesValidas = new StringBuffer();

            for (Iterator<String> it = idsDirecciones.iterator(); it.hasNext();) {
                String id = it.next();
                try {
                    String direccionId = queryAddressOnRepoById(id);
                    if (direccionId == null) {
                        datosInvalidos.add(id);
                    }
                } catch (Exception ex) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                    LOGGER.error(msg);
                    datosInvalidos.add(id);
                }
                direccionesValidas.append(id);
                if (it.hasNext()) {
                    direccionesValidas.append(",");
                }

            }
            if (!direccionesValidas.toString().isEmpty()) {
                boolean obj = adb.in("masivo1", direccionesValidas.toString());
            }

            DireccionUtil.closeConnection(adb);

            return datosInvalidos;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al verificar la existencia de una direccion por lista. EX000 " + e.getMessage(), e);
        }
    }
}
