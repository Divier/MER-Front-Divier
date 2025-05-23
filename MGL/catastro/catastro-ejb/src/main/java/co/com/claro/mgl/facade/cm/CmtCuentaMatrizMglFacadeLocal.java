package co.com.claro.mgl.facade.cm;

import co.claro.wcc.services.search.searchcuentasmatrices.SearchCuentasMatricesFault;
import co.claro.wcc.services.upload.uploadcuentasmatrices.UploadCuentasMatricesFault;
import co.com.claro.mgl.dtos.CreacionCcmmDto;
import co.com.claro.mgl.dtos.CuentaMatrizCompComercialDto;
import co.com.claro.mgl.dtos.FiltroConsultaCuentaMatriz;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.telmex.catastro.data.AddressService;
import com.amx.schema.operation.getmassivefailurerequest.v1.GetMassiveFailureResponse;
import com.jlcg.db.exept.ExceptionDB;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.model.file.UploadedFile;

/**
 * Se modifica el metodo para incluir los cambios pendientes
 *
 * @author Antonio gil
 * @version 1.00.0001
 */
public interface CmtCuentaMatrizMglFacadeLocal extends BaseFacadeLocal<CmtCuentaMatrizMgl> {

    /**
     * M&eacute;todo para configurar usuario y perfil de transacciones
     *
     * @param user {@link String} usaurio de sesi&oacute;n
     * @param perfil {@link Integer} Identificador del perfil de usuario
     */
    void setUser(String user, Integer perfil);

    List<CmtCuentaMatrizMgl> findCmList(Map<String, Object> params);

    @Override
    CmtCuentaMatrizMgl create(CmtCuentaMatrizMgl t)
            throws ApplicationException;
     @Override
    CmtCuentaMatrizMgl update(CmtCuentaMatrizMgl t)
            throws ApplicationException;
    @Override
    boolean delete(CmtCuentaMatrizMgl t) throws ApplicationException;

    @Override
    CmtCuentaMatrizMgl findById(CmtCuentaMatrizMgl sqlData)
            throws ApplicationException;
    @Override
    List<CmtCuentaMatrizMgl> findAll() throws ApplicationException;

    
    List<CmtCuentaMatrizMgl> findLastTenRecords() throws ApplicationException;

    FiltroConsultaCuentaMatriz findSolicitudesCuentaMatriz(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults)
            throws ApplicationException;

    List<String> findCoberturasCuentaMatriz(String direccion, String departameto, String ciudad, String barrio) throws ApplicationException;

    List<String> getTecnologiasInst(CmtCuentaMatrizMgl cuentaMatriz, boolean traerCables) throws ApplicationException;

    /**
     * M&eacute;todo de borrado l&oacute;gico de la cuenta matriz
     *
     * @param cuentaMatriz {@link CmtCuentaMatrizMgl} cuenta matriz a eliminar
     * @return boolean Resultado de la transacci&oacute;n
     * @throws ApplicationException Excepci&oacute;n lanzada por proceso
     */
    boolean deleteCm(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException;

    /**
     * M&eacute;todo para consultar inventario por CM
     *
     * @param cuentaMatriz {@link CmtCuentaMatrizMgl} Cuenta matriz a verificar
     * @return {@link Boolean} validaci&oacute;n de inventario sobre la cuenta
     * matriz
     * @throws ApplicationException Excepci&oacute;n lanzada por la
     * petici&oacute;
     */
    Boolean existeInventarioCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException;

    
    CmtCuentaMatrizMgl findByIdCM(BigDecimal numeroCuenta)
            throws ApplicationException;

    List<CmtCuentaMatrizMgl> findByNumeroCM(BigDecimal numeroCuenta)
            throws ApplicationException;

    /**
     * Metodo para consultar el servicio de ericcson maximo para obtener los
     * casos de la ccmm
     *
     * @author Victor Bocanegra
     * @param cm cuenta matriz a consultar
     * @return GetMassiveFailureRequestResponse
     * @throws ApplicationException
     */
    public GetMassiveFailureResponse casosCcmm(CmtCuentaMatrizMgl cm)
            throws ApplicationException;

    /**
     * Metodo para cargar un documento al UCM Autor: Victor Bocanegra
     *
     * @param cuentaMatrizMgl a la cual se le van adjuntar la imagen
     * @param archivo archivo que se va a cargar al UCM
     * @param usuario que realiza la operacion
     * @param perfil del usuario que realiza la operacion
     * @return boolean
     * @throws java.net.MalformedURLException
     * @throws ApplicationException Excepcion lanzada por la carga del archivo
     * @throws java.io.FileNotFoundException
     */
    boolean cargarImagenCMxUCM(CmtCuentaMatrizMgl cuentaMatrizMgl,
            UploadedFile archivo, String usuario, int perfil)
            throws MalformedURLException, FileNotFoundException, ApplicationException, 
            UploadCuentasMatricesFault, SearchCuentasMatricesFault;

    /**
     * Permite encontrar las cuentas matrices que no cumplen con el compromiso
     * comercial
     *
     * @author cardenaslb
     * @param tecnologia
     * @param estrato
     * @param nodo
     * @param fechaInicio
     * @param usuarioVT
     * @param fechaFinal
     * @return 
     * @throws ApplicationException
     */
    public List<CuentaMatrizCompComercialDto> getCuentasMatricesCompComercial(BigDecimal tecnologia,
            BigDecimal estrato, String nodo, Date fechaInicio, Date fechaFinal, int inicio, int fin , String usuarioVT) throws ApplicationException;

     Integer getCuentasMatricesCompComercialCount(BigDecimal tecnologia, BigDecimal estrato,
            String nodo, Date fechaInicio, Date fechaFinal, int inicio, int fin, String usuario) throws ApplicationException;

    /**
     * Get a cuentaMatriz list with a set of params given from facade Autor:
     * Victor Bocanegra
     *
     * @param params
     * @return A list of search result to CmtCuentaMatriz entity
     * @throws ApplicationException
     */
     List<CmtCuentaMatrizMgl> getCuentasMatricesSearchExp(HashMap<String, Object> params)
            throws ApplicationException;
     
        /**
     * Obtener cuenta matriz por drdireccion
     * Juan David Hernandez
     *
     * @param gpoId
     * @param drDireccionBusqueda
     * @return A list of search result to CmtCuentaMatriz entity
     * @throws ApplicationException
     */
     List<CmtCuentaMatrizMgl> findCuentasMatricesByDrDireccion(GeograficoPoliticoMgl centroPoblado,
            DrDireccion drDireccionBusqueda)
            throws ApplicationException;
     
       
    /**
     * Consulta todas las ccmm que empiezen por el nombre
     * Autor: victor bocanegra
     *
     * @param name nombre de ccmm a buscar
     * @param centroPoblado de las cuentas matrices a buscar
     * @return List<CmtCuentaMatrizMgl>
     * @throws ApplicationException
     */
     List<CmtCuentaMatrizMgl> findCcmmByNameAndCentroPoblado(String name, 
             GeograficoPoliticoMgl centroPoblado ) throws ApplicationException;
     
             /**
     * Consulta cuenta matrices agrupadoras por centro poblado Autor: Victor
     * Manuel Bocanegra
     *
     * @param centro
     * @param basicaAgrupadora
     * @return List<CmtCuentaMatrizMgl>
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    List<CmtCuentaMatrizMgl> findCuentasMatricesAgrupadorasByCentro(BigDecimal centro,
            CmtBasicaMgl basicaAgrupadora)
            throws ApplicationException; 
  
    /**
     * Metodo para la creacion de cuenta matrices-edificios y hhpp en MER y RR
     * Autor: Victor Manuel Bocanegra
     *
     * @param creacionCCmm dto con toda la informacion necesaria para la
     * creacion
     * @param addressServiceGestion informacion del geo
     * @param datosGestion datos de las tecnologias a crear
     * @param nodoXdefecto nodo por defecto
     * @param usuario en sesion
     * @param perfil del usuario en sesion
     * @return CmtCuentaMatrizMgl
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    CmtCuentaMatrizMgl creacionCCMMOptima(CreacionCcmmDto creacionCCmm,
            AddressService addressServiceGestion,
            Map<CmtBasicaMgl, NodoMgl> datosGestion, NodoMgl nodoXdefecto,
            String usuario, int perfil,
            boolean isFichaNodos) throws ApplicationException,
            ApplicationException, ExceptionDB;
}
