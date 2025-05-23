package co.com.telmex.catastro.services.solicitud;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.DetalleSolicitud;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.SolicitudRed;
import com.jlcg.db.exept.ExceptionDB;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 * Interfase SolicitudRedEJBRemote
 *
 * @author 	Nataly Orozco Torres
 * @version	1.0
 */
@Remote
public interface SolicitudRedEJBRemote {

    /**
     * 
     * @param detalles
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal createSolicitudPlaneacionRedMasivo(List<DetalleSolicitud> detalles) throws ApplicationException;

    /**
     * 
     * @param codigoNodo
     * @param gpo_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Nodo queryNodo(String codigoNodo, BigDecimal gpo_id) throws ApplicationException;

    /**
     * 
     * @param idNodo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Nodo queryNodoById(String idNodo) throws ApplicationException;

    /**
     * 
     * @param gpo_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public GeograficoPolitico queryGeograficoPoliticoId(BigDecimal gpo_id) throws ApplicationException;

    /**
     * 
     * @param gpo_name
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public GeograficoPolitico queryGeograficoPolitico(String gpo_name) throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<SolicitudRed> querySolicitudesRedPendientes() throws ApplicationException;

    /**
     * 
     * @param idSre
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public int queryQDetalleSolicitud(BigDecimal idSre) throws ApplicationException;



    /**
     * 
     * @param CodDir
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal consultaIdHHP(String CodDir) throws ApplicationException;

    /**
     * 
     * @param CodDir
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal consultaIdHHPSubDir(String CodDir) throws ApplicationException;
    
    /**
     * 
     * @param idSolicitud
     * @return
     * @throws com.jlcg.db.exept.ExceptionDB
     * @throws ApplicationException,ExceptionDB
     */
    public List<DetalleSolicitud> ConsultarSolicitudDeRed(BigDecimal idSolicitud) throws ExceptionDB, ApplicationException;

    public List<DetalleSolicitud> procesarSolicitudDeRed(List<DetalleSolicitud> listaDetalleSolicitud) throws ExceptionDB, ApplicationException;

}
