package co.com.telmex.catastro.services.solicitud;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Comunidad;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.SolicitudConsulta;
import co.com.telmex.catastro.data.TipoGeografico;
import co.com.telmex.catastro.data.TipoHhpp;
import co.com.telmex.catastro.data.TipoHhppConexion;
import co.com.telmex.catastro.data.TipoHhppRed;
import co.com.telmex.catastro.data.TipoMarcas;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 * Interfase ConsultaSolicitudEJBRemote
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
@Remote
public interface ConsultaSolicitudEJBRemote {

    /**
     * 
     * @param dir
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<SolicitudConsulta> queryCuentaMatriz(String dir) throws ApplicationException;

    /**
     * 
     * @param nivsocio
     * @param dir
     * @param user
     * @param funcionalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<SolicitudConsulta> modificarEst(String nivsocio, String dir, String user, String funcionalidad) throws ApplicationException;

    /**
     * 
     * @param dirFormatoIGAC
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean validateExistAddressIntoRepo(String dirFormatoIGAC) throws ApplicationException;

    /**
     * 
     * @param subdirFormatoIGAC
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean validateExistSubAddressIntoRepo(String subdirFormatoIGAC) throws ApplicationException;

    /**
     * 
     * @param idTipoHhpp
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public TipoHhpp queryTipoHhpp(String idTipoHhpp) throws ApplicationException;

    /**
     * 
     * @param idTipoHhppRed
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public TipoHhppRed queryTipoHhppRed(BigDecimal idTipoHhppRed) throws ApplicationException;

    /**
     * 
     * @param codigo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public TipoHhppRed queryTipoHhppRedByCodigo(String codigo) throws ApplicationException;

    /**
     * 
     * @param idTipoHhppConexion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public TipoHhppConexion queryTipoHhppConexion(BigDecimal idTipoHhppConexion) throws ApplicationException;

    /**
     * 
     * @param tge_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public TipoGeografico queryTipoGeografico(BigDecimal tge_id) throws ApplicationException;

    /**
     * 
     * @param idTipoMarcas
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public TipoMarcas queryTipoMarcas(BigDecimal idTipoMarcas) throws ApplicationException;

    /**
     * 
     * @param gpo_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public GeograficoPolitico queryGeograficoPolitico(BigDecimal gpo_id) throws ApplicationException;

    /**
     * 
     * @param idComunidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Comunidad queryComunidad(String idComunidad) throws ApplicationException;
}
