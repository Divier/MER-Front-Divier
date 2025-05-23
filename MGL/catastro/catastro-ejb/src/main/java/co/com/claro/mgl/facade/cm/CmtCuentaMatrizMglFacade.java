package co.com.claro.mgl.facade.cm;

import co.claro.wcc.services.search.searchcuentasmatrices.SearchCuentasMatricesFault;
import co.claro.wcc.services.upload.uploadcuentasmatrices.UploadCuentasMatricesFault;
import co.com.claro.mgl.businessmanager.cm.CmtCuentaMatrizMglManager;
import co.com.claro.mgl.dtos.CreacionCcmmDto;
import co.com.claro.mgl.dtos.CuentaMatrizCompComercialDto;
import co.com.claro.mgl.dtos.FiltroConsultaCuentaMatriz;
import co.com.claro.mgl.error.ApplicationException;
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
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.file.UploadedFile;


/**
 *
 * @author Admin
 */
@Stateless
public class CmtCuentaMatrizMglFacade implements CmtCuentaMatrizMglFacadeLocal {

    private static final Logger LOGGER = LogManager.getLogger(CmtCuentaMatrizMglFacade.class);
    private String user = "";
    private int perfil = 0;

    /**
     * {@inheritDoc }
     *
     * @param user {@link String} usaurio de sesi&oacute;n
     * @param perfil {@link Integer} Identificador del perfil de usuario
     */
    @Override
    public void setUser(String user, Integer perfil) {
        this.user = user;
        this.perfil = perfil;
    }

    @Override
    public List<CmtCuentaMatrizMgl> findAll() throws ApplicationException {
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.findAll();
    }

    @Override
    public List<CmtCuentaMatrizMgl> findLastTenRecords() throws ApplicationException {
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.findLastTenRecords();
    }

    @Override
    public CmtCuentaMatrizMgl create(CmtCuentaMatrizMgl t) throws ApplicationException {
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.create(t);
    }

    @Override
    public CmtCuentaMatrizMgl update(CmtCuentaMatrizMgl t) throws ApplicationException {
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.update(t);
    }

    @Override
    public boolean delete(CmtCuentaMatrizMgl t) throws ApplicationException {
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.delete(t);
    }

    @Override
    public CmtCuentaMatrizMgl findById(CmtCuentaMatrizMgl sqlData) throws ApplicationException {
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.findById(sqlData);
    }

    @Override
    public List<CmtCuentaMatrizMgl> findCmList(Map<String, Object> params) {
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.getCmList(params);
    }

    /**
     * Get a list of cuentas matrices with a set of params given from principal
     * form
     *
     * @param cuentaMatriz Object with cuentaMatriz params
     * @param direccion Object with direccion params
     * @param firstResult
     * @param maxResults
     * @return A list of search result to CmtCuentaMatriz entity
     * @throws ApplicationException
     */
    @Override
    public FiltroConsultaCuentaMatriz findSolicitudesCuentaMatriz(HashMap<String, Object> params, boolean contar, int firstResult, int maxResults)
            throws ApplicationException {
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.getCuentasMatricesSearch(params, contar, firstResult, maxResults);
    }

    /**
     * Get a list de coberturas de la cuenta matriz
     *
     * @param direccion
     * @param departameto
     * @param ciudad
     * @param barrio
     * @return
     */
    @Override
    public List<String> findCoberturasCuentaMatriz(String direccion, String departameto, String ciudad, String barrio) {
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.getCoberturas(direccion, departameto, ciudad, barrio);
    }

    /**
     * Get a list de tecnologias instaladas de la cuenta matriz en estado ok o
     * cable
     *
     * @param cuentaMatriz
     * @param traerCables
     *
     * @return
     */
    @Override
    public List<String> getTecnologiasInst(CmtCuentaMatrizMgl cuentaMatriz, boolean traerCables) {
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.getTecnologiasInstaladas(cuentaMatriz, traerCables);
    }

    /**
     * {@inheritDoc }
     *
     * @param cuentaMatriz {@link CmtCuentaMatrizMgl} cuenta matriz a eliminar
     * @return boolean Resultado de la transacci&oacute;n
     * @throws ApplicationException Excepci&oacute;n lanzada por proceso
     */
    @Override
    public boolean deleteCm(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.deleteCm(cuentaMatriz, user, perfil);
    }

    /**
     * {@inheritDoc }
     *
     * @param cuentaMatriz {@link CmtCuentaMatrizMgl} Cuenta matriz a verificar
     * @return {@link Boolean} validaci&oacute;n de inventario sobre la cuenta
     * matriz
     * @throws ApplicationException Excepci&oacute;n lanzada por la
     * petici&oacute;
     */
    @Override
    public Boolean existeInventarioCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.existeInventarioCuentaMatriz(cuentaMatriz);
    }
    
    @Override
    public CmtCuentaMatrizMgl findByIdCM(BigDecimal numeroCuenta)
            throws ApplicationException {
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.findByIdCM(numeroCuenta);
    }

    @Override
    public List<CmtCuentaMatrizMgl> findByNumeroCM(BigDecimal numeroCuenta)
            throws ApplicationException {
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.findByNumeroCM(numeroCuenta);
    }

   /**
     * Metodo para consultar el servicio 
     * de ericcson maximo para obtener los casos de la ccmm
     *
     * @author Victor Bocanegra
     * @param cm cuenta matriz a consultar
     * @return  GetMassiveFailureRequestResponse
     * @throws ApplicationException
     */
    @Override
    public GetMassiveFailureResponse casosCcmm(CmtCuentaMatrizMgl cm) 
            throws ApplicationException {

        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.casosCcmm(cm);
    }
    
        
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
    @Override
    public boolean cargarImagenCMxUCM(CmtCuentaMatrizMgl cuentaMatrizMgl,
            UploadedFile archivo, String usuario, int perfil)
            throws MalformedURLException, FileNotFoundException, ApplicationException, 
            UploadCuentasMatricesFault, SearchCuentasMatricesFault {
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.cargarImagenCMxUCM(cuentaMatrizMgl, archivo, usuario, perfil);
        
    }
    
   
    @Override
    public List<CuentaMatrizCompComercialDto> getCuentasMatricesCompComercial(BigDecimal tecnologia, BigDecimal estrato,
            String nodo,Date fechaInicio,Date fechaFinal,int inicio , int fin, String usuario) throws ApplicationException{
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        List<CuentaMatrizCompComercialDto> lista = null;
        lista = cmtCuentMatrizMglManager.getCuentasMatricesCompComercial(tecnologia, estrato, 
                nodo, fechaInicio, fechaFinal, inicio, fin, usuario);
        return lista;
    }
    
    @Override
    public Integer  getCuentasMatricesCompComercialCount(BigDecimal tecnologia, BigDecimal estrato,String nodo,Date fechaInicio,Date fechaFinal,int inicio, int fin,String usuario) throws ApplicationException{
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        Integer total = null;
        total =  cmtCuentMatrizMglManager.getCuentasMatricesCompComercialCount(tecnologia, estrato, nodo, fechaInicio, fechaFinal,inicio,fin, usuario);
        return total;
    }
    
    /**
     * Get a cuentaMatriz list with a set of params given from facade Autor:
     * Victor Bocanegra
     *
     * @param params
     * @return A list of search result to CmtCuentaMatriz entity
     * @throws ApplicationException
     */
    @Override
    public List<CmtCuentaMatrizMgl> getCuentasMatricesSearchExp(HashMap<String, Object> params)
            throws ApplicationException {
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.getCuentasMatricesSearchExp(params);

    }
    
        /**
     * Obtener cuenta matriz por drdireccion
     * Juan David Hernandez
     *
     * @param gpoId
     * @param drDireccionBusqueda
     * @return A list of search result to CmtCuentaMatriz entity
     * @throws ApplicationException
     */
    @Override
    public List<CmtCuentaMatrizMgl> findCuentasMatricesByDrDireccion(GeograficoPoliticoMgl centroPoblado,
            DrDireccion drDireccionBusqueda)
            throws ApplicationException {
        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.findCuentasMatricesByDrDireccion(centroPoblado,drDireccionBusqueda);
    }
  
    /**
     * Consulta todas las ccmm que empiezen por el nombre
     * Autor: victor bocanegra
     *
     * @param name nombre de ccmm a buscar
     * @param centroPoblado de las cuentas matrices a buscar
     * @return List<CmtCuentaMatrizMgl>
     * @throws ApplicationException
     */
    @Override
     public List<CmtCuentaMatrizMgl> findCcmmByNameAndCentroPoblado(String name, 
             GeograficoPoliticoMgl centroPoblado ) throws ApplicationException {

        CmtCuentaMatrizMglManager manager = new CmtCuentaMatrizMglManager();
        return manager.findCcmmByNameAndCentroPoblado(name, centroPoblado);
    }
    
        /**
     * Consulta cuenta matrices agrupadoras por centro poblado Autor: Victor
     * Manuel Bocanegra
     *
     * @param centro
     * @param basicaAgrupadora
     * @return List<CmtCuentaMatrizMgl>
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public List<CmtCuentaMatrizMgl> findCuentasMatricesAgrupadorasByCentro(BigDecimal centro,
            CmtBasicaMgl basicaAgrupadora)
            throws ApplicationException {

        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.findCuentasMatricesAgrupadorasByCentro(centro, basicaAgrupadora);
    }
    
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
    @Override
    public CmtCuentaMatrizMgl creacionCCMMOptima(CreacionCcmmDto creacionCCmm,
            AddressService addressServiceGestion,
            Map<CmtBasicaMgl, NodoMgl> datosGestion, NodoMgl nodoXdefecto,
            String usuario, int perfil,
            boolean isFichaNodos) throws ApplicationException,
            ApplicationException, ExceptionDB {

        CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
        return cmtCuentMatrizMglManager.creacionCCMMOptima(creacionCCmm, addressServiceGestion,
                datosGestion, nodoXdefecto, usuario, perfil, isFichaNodos);

    }

}
