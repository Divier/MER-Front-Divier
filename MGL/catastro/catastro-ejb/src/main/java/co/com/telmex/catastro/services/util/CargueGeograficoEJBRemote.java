package co.com.telmex.catastro.services.util;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.CargueGeografico;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.data.TipoHhpp;
import co.com.telmex.catastro.data.TipoHhppConexion;
import co.com.telmex.catastro.data.TipoHhppRed;
import co.com.telmex.catastro.data.TipoMarcas;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 * Interfase CargueGeograficoEJBRemote
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
@Remote
public interface CargueGeograficoEJBRemote {

    /**
     * 
     * @param solicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal insertSolicitudNegocio(CargueGeografico solicitud) throws ApplicationException;

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
    public List<TipoMarcas> queryBlackList() throws ApplicationException;
}
