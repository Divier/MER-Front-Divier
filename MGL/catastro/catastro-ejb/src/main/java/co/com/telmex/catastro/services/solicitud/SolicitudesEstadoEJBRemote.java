package co.com.telmex.catastro.services.solicitud;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.SolicitudNegocio;
import co.com.telmex.catastro.data.SolicitudRed;
import java.util.List;
import javax.ejb.Remote;
import javax.faces.model.SelectItem;

/**
 * Interfase SolicitudesEstadoEJBRemote
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
@Remote
public interface SolicitudesEstadoEJBRemote {

    //Metodos a consumir
    /**
     * 
     * @param estadoFinal
     * @param user
     * @param solNeg
     * @param idSolNeg
     * @param nombreFuncionalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String updateStateSolNegocioById(String estadoFinal, String user, SolicitudNegocio solNeg, String idSolNeg, String nombreFuncionalidad) throws ApplicationException;

    /**
     * 
     * @param estadoFinal
     * @param user
     * @param solRed
     * @param idSolRed
     * @param nombreFuncionalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String updateStateSolRedById(String estadoFinal, String user, SolicitudRed solRed, String idSolRed, String nombreFuncionalidad) throws ApplicationException;

    /**
     * 
     * @param idEstadoInicial
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<SelectItem> queryEstadosFinales(String idEstadoInicial) throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<SolicitudNegocio> querySolicitudesNegocio() throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<SolicitudRed> querySolicitudesRed() throws ApplicationException;

    /**
     * 
     * @param id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public SolicitudNegocio querySolNegocioById(String id) throws ApplicationException;

    /**
     * 
     * @param fileName
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public SolicitudNegocio querySolNegocioByFileName(String fileName) throws ApplicationException;

    /**
     * 
     * @param fileName
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<SolicitudNegocio> querySolNegociosByFileName(String fileName) throws ApplicationException;

    /**
     * 
     * @param fileName
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<SolicitudRed> querySolRedesByFileName(String fileName) throws ApplicationException;

    /**
     * 
     * @param id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public SolicitudRed querySolRedById(String id) throws ApplicationException;
}
