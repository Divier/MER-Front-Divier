package co.com.telmex.catastro.services.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.SolicitudConsulta;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 * Interfase ValidacionDirEJBRemote
 *
 * @author 	Deiver Rovira.
 * @version	1.0
 */
@Remote
public interface ValidacionDirEJBRemote {

    /**
     * 
     * @param direccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<SolicitudConsulta> queryCuentaMatriz(String direccion) throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal[] queryRolesAdministradores() throws ApplicationException;

    /**
     * 
     * @param idCiudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String queryTipoCiudadByID(String idCiudad) throws ApplicationException;

    /**
     * 
     * @param idCiudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean queryCiudadMultiorigen(String idCiudad) throws ApplicationException;

    /**
     * 
     * @param acronimo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public int queryNivelConfiabilidadFromParametros(String acronimo) throws ApplicationException;
}
