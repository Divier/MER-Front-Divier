package co.com.claro.visitasTecnicas.business;

import co.com.claro.mgl.businessmanager.address.SolicitudCEMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.SolicitudCEMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.FileServer;
import co.com.claro.visitasTecnicas.entities.SolicitudCambioEstratoEntity;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.DireccionUtil;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJB;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataList;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author user
 */
public class SolicitudCambioEstratoBusiness {

    private static final Logger LOGGER = LogManager.getLogger(SolicitudCambioEstratoBusiness.class);
    private static final String CONS_SOLICITUD_CAMBIOE_BY_IDSOL = "ConsSolCambieByIdSol";
    private static final String DEL_SOLICITUD_CAMBIOE_BY_IDSOL = "DelSolCambieByIdSol";

    public List<SolicitudCambioEstratoEntity> getSolicitudesCambioEByIdSol(String idSolicitud) throws ApplicationException {
        AccessData adb = null;
        List<SolicitudCambioEstratoEntity> result = null;
        try {
            result = new ArrayList<SolicitudCambioEstratoEntity>();
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList(CONS_SOLICITUD_CAMBIOE_BY_IDSOL, idSolicitud);
            if (list != null) {
                for (DataObject obj : list.getList()) {
                    SolicitudCambioEstratoEntity cambioEstratoEntity = new SolicitudCambioEstratoEntity();
                    cambioEstratoEntity.setId(obj.getBigDecimal("ID"));
                    cambioEstratoEntity.setUsuarioCreacion(obj.getString("USUARIO"));
                    cambioEstratoEntity.setFechaIngresoStr(obj.getString("FECHA"));
                    cambioEstratoEntity.setIdSolicitud(obj.getBigDecimal("ID_SOLICITUD"));
                    cambioEstratoEntity.setNombreDocumento(obj.getString("PATH"));
                    cambioEstratoEntity.setEstado(obj.getString("ESTADO"));

                    result.add(cambioEstratoEntity);
                }
            }

        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de obtener las solicitudes de cambio estrato. EX000: " + e.getMessage(), e);
        } finally {
            DireccionUtil.closeConnection(adb);
        }

        return result;
    }

    public boolean deleteSolicitudesCambioEByIdSol(String idSolicitud) throws ApplicationException {
        AccessData adb = null;
        boolean result = false;
        try {
            adb = ManageAccess.createAccessData();
            result = adb.in(DEL_SOLICITUD_CAMBIOE_BY_IDSOL, idSolicitud);

        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de eliminar la solicitud de cambio de estrato. EX000: " + e.getMessage(), e);
        } finally {
            DireccionUtil.closeConnection(adb);
        }

        return result;
    }

    public Boolean uploadFile(File uploadedFile, String usuario, String solicitud, String fileName, Date fecha) throws ApplicationException, Exception {
        try {
            ResourceEJB resourceEJB = new ResourceEJB();
            Parametros param;
            String ruta = "";

            // Ruta donde se almacenan los archivos de Cambio de Estrato.
            param = resourceEJB.queryParametros(Constant.URL_STORE_FILE_CAMBIO_ESTRATO);
            if (param != null && param.getValor() != null) {
                ruta = param.getValor();
            }

            boolean archivoCargado = FileServer.uploadFileMultiServer(uploadedFile, fileName);

            if (archivoCargado) {
                // Successfully Uploaded
                SolicitudCEMglManager solicitudCEMglManager = new SolicitudCEMglManager();
                SolicitudCEMgl solicitudCE = new SolicitudCEMgl();
                solicitudCE.setUsuarioCreador(usuario);
                solicitudCE.setFechaIngreso(fecha);
                solicitudCE.setPath(fileName);
                solicitudCE.setIdSolicitud(solicitud);
                solicitudCEMglManager.create(solicitudCE);

                LOGGER.error("correcto al subir el archivo");
                return true;
            } else {
                LOGGER.error("error al subir el archivo");
                return false;
                // Did not upload. Add your logic here. Maybe you want to retry.
            }
        } catch (IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de actualizar el archivo. EX000: " + ex.getMessage(), ex);
        }

    }

    public Boolean uploadFileCM(File uploadedFile, String usuario, String solicitud, String fileName, Date fecha) throws ApplicationException, Exception {
        try {
            return (FileServer.uploadFileMultiServer(uploadedFile, fileName));
        } catch (IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de cargar el archivo de la CCMM. EX000: " + ex.getMessage(), ex);
        }
    }

    public Boolean uploadFileVt(File uploadedFile, String fileName)
            throws ApplicationException, Exception {
        try {
            return (FileServer.uploadFileMultiServer(uploadedFile, fileName));
        } catch (IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de cargar el archivo de la VT. EX000: " + ex.getMessage(), ex);
        }
    }

}
