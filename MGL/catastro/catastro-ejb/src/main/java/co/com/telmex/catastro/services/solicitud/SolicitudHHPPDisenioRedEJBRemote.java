package co.com.telmex.catastro.services.solicitud;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.DataShowSolicitudRed;
import co.com.telmex.catastro.data.DetalleSolicitud;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.data.SolicitudRed;
import co.com.telmex.catastro.services.util.ConsultaSolRedModificacion;
import com.jlcg.db.exept.ExceptionDB;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 * Interfase SolicitudHHPPDisenioRedEJBRemote
 *
 * @author 	Nataly Orozco Torres
 * @version	1.0
 */
@Remote
public interface SolicitudHHPPDisenioRedEJBRemote {

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<ConsultaSolRedModificacion> querySolicitudesRedCreadas() throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<ConsultaSolRedModificacion> querySolicitudesRedAProcesar() throws ApplicationException;

    /**
     * 
     * @param idSolRed
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<ConsultaSolRedModificacion> queryDetalleSolicitudBySolRedId(String idSolRed) throws ApplicationException;

    /**
     * 
     * @param idSolRed
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<ConsultaSolRedModificacion> queryDetalleSolicitudBySolRedIdValidos(String idSolRed) throws ApplicationException;

    /**
     * 
     * @param idSolRed
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<DetalleSolicitud> queryDetalleSolicitudCreacionBySolRedIdValidos(String idSolRed) throws ApplicationException;

    /**
     * 
     * @param idsolRed
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<DetalleSolicitud> queryDetallesSolicitudBySolRedId(BigDecimal idsolRed) throws ApplicationException;

    /**
     * 
     * @param nuevaDireccion
     * @param nombreFuncionalidad
     * @param user
     * @return
     * @throws ExceptionDB
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String updateDireccion(Direccion nuevaDireccion, String nombreFuncionalidad, String user) throws ExceptionDB, ApplicationException;

    /**
     * 
     * @param estadoFinal
     * @param fileName
     * @param user
     * @param nombreFuncionalidad
     * @param detalleSolicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String updateDetalleSolicitudRed(String estadoFinal, String fileName, String user, String nombreFuncionalidad, DetalleSolicitud detalleSolicitud) throws ApplicationException;

    /**
     * 
     * @param estadoFinal
     * @param fileName
     * @param user
     * @param nombreFuncionalidad
     * @param solRed
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String updateSolicitudRedById(String estadoFinal, String fileName, String user, String nombreFuncionalidad, SolicitudRed solRed) throws ApplicationException;

    /**
     * 
     * @param solicitudes
     * @param nombreFuncionalidad
     * @param nombreArchivo
     * @param user
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String procesarSolicitudesCreacionRed(DetalleSolicitud solicitudes, String nombreFuncionalidad, String nombreArchivo, String user) throws ApplicationException;

    /**
     * 
     * @param detalleAConsultar
     * @param subdirIdDir
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public ConsultaSolRedModificacion complementarSalidaModHHPP(ConsultaSolRedModificacion detalleAConsultar, BigDecimal subdirIdDir)throws ApplicationException;

    /**
     * 
     * @param 
     * @param 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */    
    public List<DataShowSolicitudRed> querySolicitudesProcesar() throws ApplicationException;
}
