package co.com.telmex.catastro.services.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.services.util.ConsultaMasivaTable;
import java.util.List;
import javax.ejb.Remote;

/**
 * Interfase CambiarEstadoDirPendientesEJBRemote
 *
 * @author 	Deiver Rovira.
 * @version	1.0
 */
@Remote
public interface CambiarEstadoDirPendientesEJBRemote {

    /**
     * 
     * @param direccion
     * @param codCiudad
     * @param maxCantRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<ConsultaMasivaTable> queryConsultaEspecificaFiltroEmpiezaPor(String direccion, String codCiudad, String maxCantRegistros) throws ApplicationException;

    /**
     * 
     * @param idDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String queryAddressOnRepoById(String idDireccion) throws ApplicationException;

    /**
     * 
     * @param igac
     * @param idCiudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Direccion> queryAddressOnRepoByIgac(String igac, String idCiudad) throws ApplicationException;

    /**
     * 
     * @param acronimo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public int queryCantMaxRegistrosFromParametros(String acronimo) throws ApplicationException;

    /**
     * 
     * @param direccion
     * @param user
     * @param nombreFuncionalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean updateDirRevisarByIdDir(Direccion direccion, String user, String nombreFuncionalidad) throws ApplicationException;

    public List<String> queryVerificarExistenciaPorLista(List<String> idsDirecciones)throws ApplicationException;
}
