package co.com.telmex.catastro.services.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.visitasTecnicas.business.NodoRR;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.services.util.ConsultaMasivaTable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;

/**
 * Interfase ConsultaEspecificaEJBRemote
 *
 * @author 	Deiver Rovira.
 * @version	1.0
 */
@Remote
public interface ConsultaEspecificaEJBRemote {

    /**
     * 
     * @param nodo
     * @param ctaMatriz
     * @param tipoRed
     * @param estrato
     * @param nivelSocio
     * @param barrio
     * @param direccion
     * @param codCiudad
     * @param maxCantRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<ConsultaMasivaTable> queryConsultaEspecificaFiltroEmpiezaPor(String nodo, String ctaMatriz, String tipoRed, String estrato, String nivelSocio, String barrio, String direccion, String codCiudad, String maxCantRegistros) throws ApplicationException;

    /**
     * 
     * @param nodo
     * @param ctaMatriz
     * @param tipoRed
     * @param estrato
     * @param nivelSocio
     * @param barrio
     * @param direccion
     * @param codCiudad
     * @param maxCantRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<ConsultaMasivaTable> queryConsultaEspecificaFiltroTerminaCon(String nodo, String ctaMatriz, String tipoRed, String estrato, String nivelSocio, String barrio, String direccion, String codCiudad, String maxCantRegistros) throws ApplicationException;

    /**
     * 
     * @param nodo
     * @param ctaMatriz
     * @param tipoRed
     * @param estrato
     * @param nivelSocio
     * @param barrio
     * @param direccion
     * @param codCiudad
     * @param maxCantRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<ConsultaMasivaTable> queryConsultaEspecificaFiltroNoContiene(String nodo, String ctaMatriz, String tipoRed, String estrato, String nivelSocio, String barrio, String direccion, String codCiudad, String maxCantRegistros) throws ApplicationException;

    /**
     * 
     * @param nodo
     * @param ctaMatriz
     * @param tipoRed
     * @param estrato
     * @param nivelSocio
     * @param barrio
     * @param direccion
     * @param codCiudad
     * @param maxCantRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<ConsultaMasivaTable> queryConsultaEspecificaFiltroEsIgualA(String nodo, String ctaMatriz, String tipoRed, String estrato, String nivelSocio, String barrio, String direccion, String codCiudad, String maxCantRegistros) throws ApplicationException;

    /**
     * 
     * @param nodo
     * @param ctaMatriz
     * @param tipoRed
     * @param estrato
     * @param nivelSocio
     * @param barrio
     * @param direccion
     * @param codCiudad
     * @param maxCantRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<ConsultaMasivaTable> queryConsultaEspecificaFiltroComodin(String nodo, String ctaMatriz, String tipoRed, String estrato, String nivelSocio, String barrio, String direccion, String codCiudad, String maxCantRegistros) throws ApplicationException;

    /**
     * 
     * @param idDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Direccion queryAddressOnRepoById(String idDireccion) throws ApplicationException;
    
    public Direccion queryAddressOnRepoBySubDir(String idDireccion) throws ApplicationException;

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
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Nodo> queryNodos() throws ApplicationException;

    /**
     * 
     * @param acronimo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public int queryCantMaxRegistrosFromParametros(String acronimo) throws ApplicationException;
    
    public ArrayList<NodoRR> queryGetNodoRRNfiByCodCiudad(String codCiudad) throws ApplicationException;
}
