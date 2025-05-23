package co.com.telmex.catastro.services.solicitud;

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
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 * Interfase SolicitudNegocioEJBRemote
 *
 * @author 	Nataly Orozco Torres
 * @version	1.0
 */
@Remote
public interface SolicitudNegocioEJBRemote {

    /**
     * 
     * @param nombreFuncionalidad
     * @param solicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal insertSolicitudNegocio(String nombreFuncionalidad, SolicitudNegocio solicitud) throws ApplicationException;

    /**
     * 
     * @param gpo_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Nodo queryNodoNFI(BigDecimal gpo_id) throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Multivalor> loadTiposSolicitud() throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Multivalor> loadTiposSolicitudModificacion() throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Multivalor> loadTiposRespuesta() throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Multivalor> loadTiposRespuestaModificacion() throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<GeograficoPolitico> queryPaises() throws ApplicationException;

    /**
     * 
     * @param idPais
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<GeograficoPolitico> queryRegionales(BigDecimal idPais) throws ApplicationException;

    /**
     * 
     * @param idRegional
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<GeograficoPolitico> queryCiudades(BigDecimal idRegional) throws ApplicationException;

    /**
     * 
     * @param neighborhoodName
     * @param gpo_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Geografico queryNeighborhood(String neighborhoodName, BigDecimal gpo_id) throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Multivalor> queryCalles() throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Multivalor> queryCardinales() throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Multivalor> queryLetras() throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Multivalor> queryLetrasyNumeros() throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Multivalor> queryPrefijos() throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<TipoHhpp> queryTipoHhpp() throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<TipoHhppConexion> queryTipoConexion() throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<TipoHhppRed> queryTipoRed() throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Multivalor> queryEstrato() throws ApplicationException;
       
    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Marcas> queryBlackList() throws ApplicationException;

    /*UPDATE*/
    /**
     * 
     * @param idSolicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public SolicitudNegocio querySolicitudNegocio(BigDecimal idSolicitud) throws ApplicationException;

    /**
     * 
     * @param nombreFuncionalidad
     * @param user
     * @param solicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean updateSolicitudNegocio(String nombreFuncionalidad, String user, SolicitudNegocio solicitud) throws ApplicationException;

    /**
     * 
     * @param solicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean cancelSolicitudNegocio(SolicitudNegocio solicitud) throws ApplicationException;

    /**
     * 
     * @param estado
     * @param gpo
     * @param tipoSolicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<SolicitudNegocio> querySolicitudesPorGestionar(String estado, GeograficoPolitico gpo, String tipoSolicitud) throws ApplicationException;

    /**
     * 
     * @param idGrupoMultivalor
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Multivalor> loadMultivalores(String idGrupoMultivalor) throws ApplicationException;

    /**
     * 
     * @param tipoSolicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<SolicitudNegocio> querySolicitudesPorProcesar(String tipoSolicitud) throws ApplicationException;

    /**
     * 
     * @param nombreArchivo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<SolicitudNegocio> querySolicitudesParaRR(String nombreArchivo) throws ApplicationException;

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
    public String procesarSolicitudesNegocio(List<SolicitudNegocio> solicitudes, String nombreFuncionalidad, String nombreArchivo, String user) throws ApplicationException;

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
    public String procesarSolicitudesModificacion(List<SolicitudNegocio> solicitudes, String nombreFuncionalidad, String nombreArchivo, String user) throws ApplicationException;

    /**
     * 
     * @param CodDane
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws java.lang.Exception
     */
    public java.lang.String ConsultaTipoDireccionByCodigoDane(java.lang.String CodDane) throws ApplicationException; //Camilo Gaviria - 2013/01/14
}