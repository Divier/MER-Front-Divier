package co.com.claro.mgl.businessmanager.cm;

import co.claro.wcc.client.gestor.ClientGestorDocumental;
import co.claro.wcc.schema.schemadocument.DocumentType;
import co.claro.wcc.schema.schemadocument.FieldType;
import co.claro.wcc.schema.schemadocument.FileType;
import co.claro.wcc.schema.schemaoperations.ActionStatusEnumType;
import co.claro.wcc.schema.schemaoperations.FileRequestType;
import co.claro.wcc.schema.schemaoperations.RequestType;
import co.claro.wcc.schema.schemaoperations.ResponseType;
import co.claro.wcc.services.search.searchcuentasmatrices.SearchCuentasMatricesFault;
import co.claro.wcc.services.upload.uploadcuentasmatrices.UploadCuentasMatricesFault;
import co.com.claro.mgl.businessmanager.address.DrDireccionManager;
import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.dao.impl.cm.*;
import co.com.claro.mgl.dtos.CmtParamentrosComplejosDto;
import co.com.claro.mgl.dtos.CreacionCcmmDto;
import co.com.claro.mgl.dtos.CuentaMatrizCompComercialDto;
import co.com.claro.mgl.dtos.FiltroConsultaCuentaMatriz;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.*;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJB;
import co.com.telmex.catastro.services.ws.Serviceadc;
import co.com.telmex.catastro.utilws.ResponseMessage;
import com.amx.co.schema.claroheaders.v1.HeaderRequest;
import com.amx.schema.operation.getmassivefailurerequest.v1.ClientMaximo;
import com.amx.schema.operation.getmassivefailurerequest.v1.GetMassiveFailureRequest;
import com.amx.schema.operation.getmassivefailurerequest.v1.GetMassiveFailureResponse;
import com.amx.schema.operation.getmassivefailurerequest.v1.MassiveFailureRequest;
import com.jlcg.db.exept.ExceptionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.file.UploadedFile;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author Admin
 */
public class CmtCuentaMatrizMglManager {

    private static final Logger LOGGER = LogManager.getLogger(CmtCuentaMatrizMglManager.class);
    private final static String BLACK_LIST_POR_ORIGEN = "BLACK_LIST_POR_ORIGEN";
    private final static String BLACK_LIST_DFAULT = "BLACK_LIST_DFAULT";
    private final static String PERMITE_CREA_NFI = "PERMITE_CREA_NFI";
    private final static String PERMITE_CREA_NOD_OFF = "PERMITE_CREA_NOD_OFF";

    
    public List<CmtCuentaMatrizMgl> findAll() throws ApplicationException {
        List<CmtCuentaMatrizMgl> resulList;
        CmtCuentaMatrizMglDaoImpl cmtCuentMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
        resulList = cmtCuentMatrizMglDaoImpl.findAll();
        return resulList;
    }

    public List<CmtCuentaMatrizMgl> findLastTenRecords() throws ApplicationException {
        List<CmtCuentaMatrizMgl> resulList;
        CmtCuentaMatrizMglDaoImpl cmtCuentMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
        resulList = cmtCuentMatrizMglDaoImpl.findLastTenRecords();
        return resulList;
    }

    public CmtCuentaMatrizMgl create(CmtCuentaMatrizMgl cmtCuentMatrizMgl) throws ApplicationException {
        CmtCuentaMatrizMglDaoImpl cmtCuentMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
        return cmtCuentMatrizMglDaoImpl.create(cmtCuentMatrizMgl);
    }

    public CmtCuentaMatrizMgl createCm(CmtCuentaMatrizMgl cmtCuentMatrizMgl, String user, int perfil) throws ApplicationException {
        CmtCuentaMatrizMglDaoImpl cmtCuentMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
        return cmtCuentMatrizMglDaoImpl.createCm(cmtCuentMatrizMgl, user, perfil);
    }

    public CmtCuentaMatrizMgl update(CmtCuentaMatrizMgl cmtCuentMatrizMgl) throws ApplicationException {
        CmtCuentaMatrizMglDaoImpl cmtCuentMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
        return cmtCuentMatrizMglDaoImpl.update(cmtCuentMatrizMgl);
    }

    public CmtCuentaMatrizMgl updateSinRr(CmtCuentaMatrizMgl cmtCuentMatrizMgl, String user, int perfil) throws ApplicationException {
        CmtCuentaMatrizMglDaoImpl cmtCuentMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
        return cmtCuentMatrizMglDaoImpl.updateCm(cmtCuentMatrizMgl, user, perfil);
    }

    public boolean deleteSinRr(CmtCuentaMatrizMgl cmtCuentMatrizMgl, String user, int perfil) throws ApplicationException {
        CmtCuentaMatrizMglDaoImpl cmtCuentMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
        return cmtCuentMatrizMglDaoImpl.deleteCm(cmtCuentMatrizMgl, user, perfil);
    }

    public boolean delete(CmtCuentaMatrizMgl cmtCuentMatrizMgl) throws ApplicationException {
        CmtCuentaMatrizMglDaoImpl cmtCuentMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
        return cmtCuentMatrizMglDaoImpl.delete(cmtCuentMatrizMgl);
    }

    public CmtCuentaMatrizMgl findById(CmtCuentaMatrizMgl cmtCuentMatrizMgl) throws ApplicationException {
        CmtCuentaMatrizMglDaoImpl cmtCuentMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
        return cmtCuentMatrizMglDaoImpl.find(cmtCuentMatrizMgl.getCuentaMatrizId());
    }
    
    public List<CmtCuentaMatrizMgl> getCmList(Map<String, Object> params) {
        CmtCuentaMatrizMglDaoImpl cmtCuentMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
        return cmtCuentMatrizMglDaoImpl.findCmList(params);
    }


    /**
     * Get a cuentaMatriz list with a set of params given from facade
     *
     * @param cuentaMatriz Object cuentaMatriz with params
     * @param maxResults
     * @param firstResult
     * @return A list of search result to CmtCuentaMatriz entity
     * @throws ApplicationException
     */
    public FiltroConsultaCuentaMatriz getCuentasMatricesSearch(HashMap<String, Object> params, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        CmtCuentaMatrizMglDaoImpl cmtCuentMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
        String direccion = (String) params.get("direccionBusqueda");
        BigDecimal ciudad = (BigDecimal) params.get("centroPoblado");
        if (direccion != null && !direccion.isEmpty()) {
            CmtValidadorDireccionesManager cmtValidadorDireccionesManager = new CmtValidadorDireccionesManager();
            try {
                DrDireccion drDireccion = cmtValidadorDireccionesManager.convertirDireccionStringADrDireccion(direccion, ciudad);
                if (drDireccion != null) {
                    params.put("idTipoDireccion", drDireccion.getIdTipoDireccion());
                    params.put("tipoViaPrincipal", drDireccion.getTipoViaPrincipal());
                    params.put("numViaPrincipal", drDireccion.getNumViaPrincipal());
                    params.put("ltViaPrincipal", drDireccion.getLtViaPrincipal());
                    params.put("nlPostViaP", drDireccion.getNlPostViaP());
                    params.put("bisViaPrincipal", drDireccion.getBisViaPrincipal());
                    params.put("cuadViaPrincipal", drDireccion.getCuadViaPrincipal());
                    params.put("tipoViaGeneradora", drDireccion.getTipoViaGeneradora());
                    params.put("numViaGeneradora", drDireccion.getNumViaGeneradora());
                    params.put("ltViaGeneradora", drDireccion.getLtViaGeneradora());
                    params.put("nlPostViaG", drDireccion.getNlPostViaG());
                    params.put("letra3G", drDireccion.getLetra3G());
                    params.put("bisViaGeneradora", drDireccion.getBisViaGeneradora());
                    params.put("cuadViaGeneradora", drDireccion.getCuadViaGeneradora());
                    params.put("placaDireccion", drDireccion.getPlacaDireccion());
                    params.remove("direccionBusqueda");
                }
            } catch (ApplicationException ex) {
				String msg = "Se produjo un error al momento de ejecutar el método '"+
				ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
				LOGGER.error(msg);
                throw new ApplicationException(ex);
            }
        }
        return cmtCuentMatrizMglDaoImpl.findCuentasMatricesSearch(params, contar, firstResult, maxResults);
    }

    /**
     * Realiza el cambio del estado de una CM.Permite realizar el cambio de
 esta a una cuenta matriz.
     *
     * @author Johnnatan Ortiz
     * @param cuentaMatrizMgl CM a la cual se quiere cambiar el estado
     * @param nuevoEstadoCm estado nuevo de la CM
     * @return 
     * @throws ApplicationException
     */
    public boolean cambiarEstadoCm(CmtCuentaMatrizMgl cuentaMatrizMgl,
            CmtBasicaMgl nuevoEstadoCm) throws ApplicationException {

        boolean result = false;
        //TODO: validar si se puede hacer el cambio al nuevo estado, 
        //no se ha definido las reglas entre estados para la CM

        //TODO: Obtener sub edificios sobre los cuales se hara el cambio del estado
        //Verificamos la existencia de subEdificios en al CM
        if (cuentaMatrizMgl.getListCmtSubEdificioMglActivos() != null
                && !cuentaMatrizMgl.getListCmtSubEdificioMglActivos().isEmpty()) {
            if (cuentaMatrizMgl.getListCmtSubEdificioMglActivos().size() == 1) {
                CmtSubEdificioMgl subEdificioMgl = cuentaMatrizMgl.getListCmtSubEdificioMglActivos().get(0);
                CmtBasicaMgl estadoSubEdificio = subEdificioMgl.getEstadoSubEdificioObj();
                /*
                 * validamos si el edificio encontrado se trata del edificio 
                 * general de una CM Multiedificio.
                 */
                if (estadoSubEdificio.getIdentificadorInternoApp()
                        .equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                    result = true;
                } else {
                    subEdificioMgl.setEstadoSubEdificioObj(nuevoEstadoCm);
                    CmtSubEdificioMglManager edificioMglManager = new CmtSubEdificioMglManager();
                    edificioMglManager.update(subEdificioMgl);
                    result = true;
                }
            } else {
                /*
                 * cambiamos el estado a todos los subedificios exceptuando al general
                 */
                CmtSubEdificioMglManager edificioMglManager = new CmtSubEdificioMglManager();
                for (CmtSubEdificioMgl se : cuentaMatrizMgl.getListCmtSubEdificioMglActivos()) {
                    if (se.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                            && !se.getEstadoSubEdificioObj().getIdentificadorInternoApp()
                            .equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                        edificioMglManager.update(se);
                    }
                }
                result = true;
            }
        }
        return result;
    }

    public List<String> getTecnologiasInstaladas(CmtCuentaMatrizMgl cmtCuentaMatrizMgl, boolean traerCables) {
        List<String> resulList;
        CmtCuentaMatrizMglDaoImpl cmtCuentMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
        resulList = cmtCuentMatrizMglDaoImpl.getTecnologiasInst(cmtCuentaMatrizMgl, traerCables);
        return resulList;
    }

    public List<String> getCoberturas(String direccion, String departameto, String ciudad, String barrio) {
        Serviceadc service = new Serviceadc();
        AddressService address = service.queryStandarAddress("C", direccion, departameto, ciudad, barrio);
        //TODO Ajustar validación para coberturas.
        String[] nodos = {address.getNodoUno(), address.getNodoDos(), address.getNodoTres(), address.getNodoDth()};
        List<String> coberturas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (nodos[i] != null) {// 
                if (nodos[i].length() > 0) {//SI NO ESTA VACIO
                    if (i == 2 || i == 1) {
                        nodos[i] = "UNIDIRECCIONAL";
                        coberturas.add(nodos[i]);
                    }
                    if (i == 0) {
                        nodos[i] = "BIDIRECCIONAL";
                        coberturas.add(nodos[i]);
                    }
                    if (i == 3) {
                        nodos[i] = "DTH";
                        coberturas.add(nodos[i]);
                    }
                } else {//SI ESTÁ VACÍO
                    nodos[i] = null;
                }
            }
        }
        return coberturas;
    }

    /**
     * M&eacute;todo de borrado l&oacute;gico de la cuenta matriz
     *
     * @param cuentaMatriz {@link CmtCuentaMatrizMgl} cuenta matriz a eliminar
     * @param user {@link String} Usuario de sesi&oacute;n que genera la
     * transacci&oacue:n
     * @param perfil {@link Integer} Perfil del usuario que genera la
     * transacci&oacute;n
     * @return boolean Resultado de la transacci&oacute;n
     * @throws ApplicationException Excepci&oacute;n lanzada por proceso
     */
    public boolean deleteCm(CmtCuentaMatrizMgl cuentaMatriz, String user, Integer perfil) throws ApplicationException {
        CmtCuentaMatrizMglDaoImpl cmtCuentMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
        return cmtCuentMatrizMglDaoImpl.deleteCm(cuentaMatriz, user, perfil);
    }

    /**
     * M&eacute;todo para consultar inventario por CM
     *
     * @param cuentaMatriz {@link CmtCuentaMatrizMgl} Cuenta matriz a verificar
     * @return {@link Boolean} validaci&oacute;n de inventario sobre la cuenta
     * matriz
     * @throws ApplicationException Excepci&oacute;n lanzada por la
     * petici&oacute;
     */
    public Boolean existeInventarioCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        try {
            CmtCuentaMatrizRRMglManager cmtCuentaMatrizRRMglManager = new CmtCuentaMatrizRRMglManager();
            return cmtCuentaMatrizRRMglManager.existeInventarioCuentaMatriz(cuentaMatriz);
        } catch (ApplicationException e) {
            String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + ": " + e.getMessage();
            LOGGER.error(msgError, e);
            throw new ApplicationException(e);
        }
    }       
    
    public List<CmtCuentaMatrizMgl> findByNumeroCM(BigDecimal numeroCM) throws ApplicationException{
      List<CmtCuentaMatrizMgl> cmtCuentaMatrizMgl;
        CmtCuentaMatrizMglDaoImpl cmtCuentMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
        cmtCuentaMatrizMgl = cmtCuentMatrizMglDaoImpl.findByNumeroCM(numeroCM);
        return cmtCuentaMatrizMgl;
}
    
    public CmtCuentaMatrizMgl findByIdCM(BigDecimal numeroCM) throws ApplicationException{
        CmtCuentaMatrizMgl cmtCuentaMatrizMgl;
        try{
            CmtCuentaMatrizMglDaoImpl cmtCuentMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
            cmtCuentaMatrizMgl = cmtCuentMatrizMglDaoImpl.findByIdCM(numeroCM);
        }catch( ApplicationException ex ){
            LOGGER.error("Error al momento de consultar la CCMM. EX000: " + ex.getMessage(), ex);
            cmtCuentaMatrizMgl = null;
        }
        return cmtCuentaMatrizMgl;
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
    public GetMassiveFailureResponse casosCcmm(CmtCuentaMatrizMgl cm) 
            throws ApplicationException {
        
        GetMassiveFailureResponse response = null;
        MassiveFailureRequest request = null;
        ClientMaximo cliente = new ClientMaximo();
        URL url = null;
        try {
            ResourceEJB resourceEJB = new ResourceEJB();
            String wsURL = "";

            Parametros param = resourceEJB.queryParametros(Constant.PROPERTY_URL_WS_MAXIMO);
            if (param != null) {
                wsURL = param.getValor();
                url = new URL(null, wsURL, new sun.net.www.protocol.http.Handler());
            }

            GetMassiveFailureRequest getMassiveFailureRequest = new GetMassiveFailureRequest();
            HeaderRequest headerRequest = new HeaderRequest();

            GregorianCalendar c = new GregorianCalendar();
            Date fecha = new Date();
            c.setTime(fecha);

            XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            headerRequest.setRequestDate(date2);
            headerRequest.setSystem("");
            headerRequest.setTransactionId("");
            getMassiveFailureRequest.setHeaderRequest(headerRequest);

            MassiveFailureRequest massiveFailureRequest = new MassiveFailureRequest();
            massiveFailureRequest.setCcmm(cm.getCuentaMatrizId().toString());//"CCMM06"
            getMassiveFailureRequest.setMassiveFailureRequest(massiveFailureRequest);


            response = cliente.getMassiveFailureRequest(getMassiveFailureRequest, url);


            LOGGER.error(response.getHeaderResponse().getResponseDate());
            LOGGER.error(response.getMassiveFailureResponse().get(0).getFailureCause());


        } catch (MalformedURLException | DatatypeConfigurationException ex) {
            String msg = "Se produjo un error al momento de consultar los incidentes de la CCMM: '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			LOGGER.error(msg);
			throw new ApplicationException(msg,  ex);
        }
        return response;
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
    public boolean cargarImagenCMxUCM(CmtCuentaMatrizMgl cuentaMatrizMgl,
            UploadedFile archivo, String usuario, int perfil)
            throws MalformedURLException, ApplicationException, 
            UploadCuentasMatricesFault, SearchCuentasMatricesFault {

        
        boolean respuesta = false;
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        int maximo;

        try {

            URL url;
            ParametrosMglManager parametros = new ParametrosMglManager();
            ParametrosMgl paramUser = parametros.findByAcronimoName(Constant.USER_AUTENTICACION_GESTOR_DOCUMENTAL);
            String user = "";
            if (paramUser != null) {
                user = paramUser.getParValor();
            }

            ParametrosMgl paramPass = parametros.findByAcronimoName(Constant.PASS_AUTENTICACION_GESTOR_DOCUMENTAL);
            String pass = "";
            if (paramPass != null) {
                pass = paramPass.getParValor();
            }

            ParametrosMgl param = parametros.findByAcronimoName(Constant.PROPERTY_URL_WS_UCM_UPLOAD_CCMM);
            String ruta = "";
            if (param != null) {
                ruta = param.getParValor();
            }

            String tipoDocumental = "";
            CmtBasicaMgl basicaTipoDoc = basicaMglManager.
                    findByCodigoInternoApp(Constant.TIPO_DOCUMENTAL_CUENTA_MATRIZ);

            if (basicaTipoDoc != null) {
                tipoDocumental = basicaTipoDoc.getNombreBasica();
            }

            String empresa = "";
            ParametrosMgl param2 = parametros.findByAcronimoName(Constant.PROPERTY_UCM_TIPO_EMPRESA);
            if (param2 != null) {
                empresa = param2.getParValor();
            }

            url = new URL(null, ruta, new sun.net.www.protocol.http.Handler());

            ClientGestorDocumental cliente = new ClientGestorDocumental(user, pass);

            FileRequestType request = new FileRequestType();
            DocumentType documentType = new DocumentType();
            DocumentType documentTypeBuscar = new DocumentType();

            FileType fielFileType = new FileType();

            FieldType field1 = new FieldType();
            field1.setName("xdEmpresa");
            field1.setValue(empresa);
            documentType.getField().add(field1);
            documentTypeBuscar.getField().add(field1);

            FieldType field2 = new FieldType();
            field2.setName("xdTipoDocumental");
            field2.setValue(tipoDocumental);
            documentType.getField().add(field2);
            documentTypeBuscar.getField().add(field2);

            FieldType field3 = new FieldType();
            field3.setName("xdNumeroCtaMatriz");
            field3.setValue(cuentaMatrizMgl.getCuentaMatrizId().toString());
            documentType.getField().add(field3);
            documentTypeBuscar.getField().add(field3);

            
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();


            FieldType field4 = new FieldType();
            field4.setName("xdFechaDocumento");
            field4.setValue(dateFormat.format(date));
            documentType.getField().add(field4);
            documentTypeBuscar.getField().add(field4);

            maximo = selectMaximoSecXCm(cuentaMatrizMgl);

            if (maximo == 0) {
                maximo++;
            } else {
                maximo++;
            }

            String numUnico = cuentaMatrizMgl.getCuentaMatrizId().toString() + String.valueOf(maximo);

            FieldType field5 = new FieldType();
            field5.setName("xdIdProceso");
            field5.setValue(numUnico);
            documentType.getField().add(field5);
            documentTypeBuscar.getField().add(field5);
            
            FieldType field6 = new FieldType();
            field6.setName("xdDireccionPredio");
            if (cuentaMatrizMgl.getDireccionPrincipal() != null && cuentaMatrizMgl.getDireccionPrincipal().getDireccionObj() != null) {
                field6.setValue(cuentaMatrizMgl.getDireccionPrincipal().getDireccionObj().getDirFormatoIgac());
            }else{
                 field6.setValue("Sin Direccion");
            }
            
            documentType.getField().add(field6);
            documentTypeBuscar.getField().add(field6);
            
            FieldType field7 = new FieldType();
            field7.setName("xdPredio");
            field7.setValue(cuentaMatrizMgl.getNombreCuenta());
            documentType.getField().add(field7);
            documentTypeBuscar.getField().add(field7);
            
            
            FieldType field9 = new FieldType();
            field9.setName("xdNumeroIdentificacion");
            field9.setValue(cuentaMatrizMgl.getNumeroCuenta().toString());
            documentType.getField().add(field9);
            documentTypeBuscar.getField().add(field9);
            
            fielFileType.setName(archivo.getFileName());
            fielFileType.setContent(archivo.getContent());

            request.setDocument(documentType);
            request.setFile(fielFileType);

            ResponseType response = cliente.insert(request, url);
            ActionStatusEnumType status = response.getActionStatus();

            String urlArchivo = null;

            if (status.value().equalsIgnoreCase("success")) {

                //Consultamos el archivo subido al gestor
                ParametrosMgl paramUrl = parametros.findByAcronimoName(Constant.PROPERTY_URL_WS_UCM_SEARCH_CCMM);
                String rutaBuscar = "";
                if (paramUrl != null) {
                    rutaBuscar = paramUrl.getParValor();
                }

                URL urlBuscar = new URL(null, rutaBuscar, new sun.net.www.protocol.http.Handler());
                RequestType requestBuscar = new RequestType();
                requestBuscar.setDocument(documentTypeBuscar);

                ResponseType responseBuscar = cliente.find(requestBuscar, urlBuscar);
                ActionStatusEnumType statusBuscar = responseBuscar.getActionStatus();

                if (statusBuscar.value().equalsIgnoreCase("success")) {

                    for (DocumentType documentType1 : responseBuscar.getDocument()) {
                        if (documentType1.getField().size() > 0) {

                            for (FieldType atributos : documentType1.getField()) {
                                if (atributos.getName().equalsIgnoreCase("xdURLDocumento")) {
                                    urlArchivo = atributos.getValue();
                                }
                            }

                        }

                    }
                }

                if (urlArchivo != null && !urlArchivo.isEmpty()) {
                    cuentaMatrizMgl.setImgCuenta(urlArchivo);
                    cuentaMatrizMgl.setSecArchivo(maximo);
                    update(cuentaMatrizMgl);
                    respuesta = cuentaMatrizMgl.getSecArchivo() == maximo;

                } else {
                    LOGGER.info("Empiezan los 10 segundos");
                    esperar(10);

                    responseBuscar = cliente.find(requestBuscar, urlBuscar);
                    ActionStatusEnumType statusBuscar2 = responseBuscar.getActionStatus();

                    if (statusBuscar2.value().equalsIgnoreCase("success")) {

                        for (DocumentType documentType1 : responseBuscar.getDocument()) {
                            if (documentType1.getField().size() > 0) {

                                for (FieldType atributos : documentType1.getField()) {
                                    if (atributos.getName().equalsIgnoreCase("xdURLDocumento")) {
                                        urlArchivo = atributos.getValue();
                                    }
                                }

                            }

                        }
                    }
                    if (urlArchivo != null && !urlArchivo.isEmpty()) {
                      cuentaMatrizMgl.setImgCuenta(urlArchivo);
                    cuentaMatrizMgl.setSecArchivo(maximo);
                    update(cuentaMatrizMgl);
                    respuesta = cuentaMatrizMgl.getSecArchivo() == maximo;
                    } else {
                        respuesta = false;
                    }
                }
            } else {
                respuesta = false;
            }

        } catch (ApplicationException | IOException  ex) {
            String msg = "Error al momento de cargar el archivo. EX000 '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, ex);
        }
        return respuesta;
        
        
        
        
        
    }
    /**
     * Consulta el maximo de la secuencia por cuenta matriz Autor: victor
     * bocanegra
     *
     * @param cm
     * @return el maximo de la secuencia
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int selectMaximoSecXCm(CmtCuentaMatrizMgl cm)
            throws ApplicationException {

        CmtCuentaMatrizMglDaoImpl cmtCuentMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
        return cmtCuentMatrizMglDaoImpl.selectMaximoSecXCm(cm);
    }
    
    public void esperar(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException ex) {
            String msg = "Ocurrio un error en el timeout '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
		 LOGGER.error(msg);
	}
    }
                        
        public int  getHhppTotal(BigDecimal cuentaMtrizId, HashMap<String, Object> params) throws ApplicationException{
            int hHpp;
            int totalHhpp = 0;
            CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
            CmtTecnologiaSubMglDaoImpl cmtTecnologiaSubMglDaoImpl = new CmtTecnologiaSubMglDaoImpl();
            List<CmtSubEdificioMgl> cmtSubEdificioMgllist;
            // lista de subedificios por cuenta matriz
            cmtSubEdificioMgllist = cmtSubEdificioMglDaoImpl.findSubEdificioByCuentaMatrizId(cuentaMtrizId);
            for (CmtSubEdificioMgl cmtSubEdificioMgl : cmtSubEdificioMgllist) {
                hHpp = cmtTecnologiaSubMglDaoImpl.countReportHhppTecCM(cmtSubEdificioMgl.getSubEdificioId());
                totalHhpp =+ hHpp;
            }
          
        return totalHhpp;
    }
        
        
                
    public int getHhppActivosTotal(BigDecimal cuentaMtrizId, HashMap<String, Object> params) throws ApplicationException {
        int hHppActivos;
        int totalHhppActivos = 0;
        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        CmtTecnologiaSubMglDaoImpl cmtTecnologiaSubMglDaoImpl = new CmtTecnologiaSubMglDaoImpl();
        List<CmtSubEdificioMgl> cmtSubEdificioMgllist;
        // lista de subedificios por cuenta matriz
        cmtSubEdificioMgllist = cmtSubEdificioMglDaoImpl.findSubEdificioByCuentaMatrizId(cuentaMtrizId);
        for (CmtSubEdificioMgl cmtSubEdificioMgl : cmtSubEdificioMgllist) {
            hHppActivos = cmtTecnologiaSubMglDaoImpl.countReportClientesActivos(cmtSubEdificioMgl.getSubEdificioId());
            totalHhppActivos = +hHppActivos;
        }

        return totalHhppActivos;
    }
        
        
    /**
     * Obtiene reporte detallado de solicitudes
     *
     * @author Lenis Cardenas
     * @param tecnologia
     * @param estrato
     * @param nodo
     * @param fechaInicio
     * @param fechaFinal
     * @param inicio
     * @param fin
     * @param usuario
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public List<CuentaMatrizCompComercialDto> getCuentasMatricesCompComercial(BigDecimal tecnologia, BigDecimal estrato, 
            String nodo, Date fechaInicio, Date fechaFinal, int inicio , int fin ,String usuario) throws ApplicationException {
        CmtCuentaMatrizMglManager cmtCuentaMatrizMglManager = new CmtCuentaMatrizMglManager();
        List<CuentaMatrizCompComercialDto> lista;
        lista = cmtCuentaMatrizMglManager.getCuentasMatricesCompComercialTotal(tecnologia, estrato, 
                nodo, fechaInicio, fechaFinal, inicio, fin, usuario);
        return lista;
    }

    
     public List<CuentaMatrizCompComercialDto>  getCuentasMatricesCompComercialTotal(BigDecimal tecnologia, BigDecimal estrato,
             String nodo,Date fechaInicio,Date fechaFinal, int inicio, int fin, String usuario) throws ApplicationException {
                 List<CuentaMatrizCompComercialDto> listaCuentaMatrizCompComercialDto;
                 CmtCuentaMatrizMglDaoImpl cmtCuentaMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
                  
         if (estrato != null) {
            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
             CmtBasicaMgl cmtBasicaMgl;
             cmtBasicaMgl = cmtBasicaMglManager.findById(estrato);
             if (cmtBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTRATO_1)) {
                 estrato = new BigDecimal("1");
             }  else if (cmtBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTRATO_2)) {
                cmtBasicaMgl.setBasicaId(estrato);
                 estrato = new BigDecimal("2");
             } else if (cmtBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTRATO_3)) {
                  cmtBasicaMgl.setBasicaId(estrato);
                 estrato = new BigDecimal("3");
             } else if (cmtBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTRATO_4)) {
                  cmtBasicaMgl.setBasicaId(estrato);
                 estrato = new BigDecimal("4");
             } else if (cmtBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTRATO_5)) {
                  cmtBasicaMgl.setBasicaId(estrato);
                 estrato = new BigDecimal("5");
             } else if (cmtBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTRATO_6)) {
                  cmtBasicaMgl.setBasicaId(estrato);
                 estrato = new BigDecimal("6");
             } else if (cmtBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTRATO_7)) {
                  cmtBasicaMgl.setBasicaId(estrato);
                 estrato = new BigDecimal("7");
             } else if (cmtBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTRATO_NG)) {
                 estrato = new BigDecimal("-1");
             } else  {
                 estrato = new BigDecimal("0");
             } 

         }
              listaCuentaMatrizCompComercialDto = cmtCuentaMatrizMglDaoImpl.getCuentasMatricesCompComercial(tecnologia, estrato,
                 nodo, fechaInicio, fechaFinal, inicio, fin, usuario);
         return listaCuentaMatrizCompComercialDto;
         
     }
     
        public Integer  getCuentasMatricesCompComercialCount(BigDecimal tecnologia, BigDecimal estrato,
             String nodo,Date fechaInicio,Date fechaFinal,int inicio, int fin, String usuario) throws ApplicationException {
            CmtCuentaMatrizMglDaoImpl cmtCuentaMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();
           
            if (estrato != null) {
                CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
                CmtBasicaMgl cmtBasicaMgl;
                cmtBasicaMgl = cmtBasicaMglManager.findById(estrato);
                if (cmtBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTRATO_1)) {
                    estrato = new BigDecimal("1");
                } else if (cmtBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTRATO_2)) {
                    cmtBasicaMgl.setBasicaId(estrato);
                    estrato = new BigDecimal("2");
                } else if (cmtBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTRATO_3)) {
                    cmtBasicaMgl.setBasicaId(estrato);
                    estrato = new BigDecimal("3");
                } else if (cmtBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTRATO_4)) {
                    cmtBasicaMgl.setBasicaId(estrato);
                    estrato = new BigDecimal("4");
                } else if (cmtBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTRATO_5)) {
                    cmtBasicaMgl.setBasicaId(estrato);
                    estrato = new BigDecimal("5");
                } else if (cmtBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTRATO_6)) {
                    cmtBasicaMgl.setBasicaId(estrato);
                    estrato = new BigDecimal("6");
                } else if (cmtBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTRATO_7)) {
                    cmtBasicaMgl.setBasicaId(estrato);
                    estrato = new BigDecimal("7");
                } else if (cmtBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTRATO_NG)) {
                    estrato = new BigDecimal("-1");
                } else {
                    estrato = new BigDecimal("0");
                }

            }
            return cmtCuentaMatrizMglDaoImpl.getCuentasMatricesCompComercialCount(tecnologia, estrato, nodo, fechaInicio, fechaFinal, inicio, fin, usuario);
         
     }
        
        
    /**
     * Get a cuentaMatriz list with a set of params given from facade Autor:
     * Victor Bocanegra
     *
     * @param params
     * @return A list of search result to CmtCuentaMatriz entity
     * @throws ApplicationException
     */
    public List<CmtCuentaMatrizMgl> getCuentasMatricesSearchExp(HashMap<String, Object> params) throws ApplicationException {
        CmtCuentaMatrizMglDaoImpl cmtCuentMatrizMglDaoImpl = new CmtCuentaMatrizMglDaoImpl();


        if ((params.get("cuentaSucriptor") != null && !((String) params.get("cuentaSucriptor")).isEmpty())
                || (params.get("serialEquipo") != null && !((String) params.get("serialEquipo")).isEmpty())
                || (params.get("administracion") != null && !((String) params.get("administracion")).isEmpty())
                || (params.get("administrador") != null && !((String) params.get("administrador")).isEmpty())) {
        }
        String direccion = (String) params.get("direccionBusqueda");
        BigDecimal ciudad = (BigDecimal) params.get("centroPoblado");
        if (direccion != null && !direccion.isEmpty()) {
            CmtValidadorDireccionesManager cmtValidadorDireccionesManager = new CmtValidadorDireccionesManager();
            try {
                DrDireccion drDireccion = cmtValidadorDireccionesManager.convertirDireccionStringADrDireccion(direccion, ciudad);
                if (drDireccion != null) {
                    params.put("idTipoDireccion", drDireccion.getIdTipoDireccion());
                    params.put("tipoViaPrincipal", drDireccion.getTipoViaPrincipal());
                    params.put("numViaPrincipal", drDireccion.getNumViaPrincipal());
                    params.put("ltViaPrincipal", drDireccion.getLtViaPrincipal());
                    params.put("nlPostViaP", drDireccion.getNlPostViaP());
                    params.put("bisViaPrincipal", drDireccion.getBisViaPrincipal());
                    params.put("cuadViaPrincipal", drDireccion.getCuadViaPrincipal());
                    params.put("tipoViaGeneradora", drDireccion.getTipoViaGeneradora());
                    params.put("numViaGeneradora", drDireccion.getNumViaGeneradora());
                    params.put("ltViaGeneradora", drDireccion.getLtViaGeneradora());
                    params.put("nlPostViaG", drDireccion.getNlPostViaG());
                    params.put("letra3G", drDireccion.getLetra3G());
                    params.put("bisViaGeneradora", drDireccion.getBisViaGeneradora());
                    params.put("cuadViaGeneradora", drDireccion.getCuadViaGeneradora());
                    params.put("placaDireccion", drDireccion.getPlacaDireccion());
                    params.remove("direccionBusqueda");
                }
            } catch (ApplicationException ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
				 LOGGER.error(msg);
				 throw new ApplicationException(msg, ex);
            }
        }
        return cmtCuentMatrizMglDaoImpl.findCuentasMatricesSearchDatosExp(params);
    }
    
       /**
     * Get a cuentaMatriz list with a set of params given from facade Autor:
     * Victor Bocanegra
     *
     * @param centroPobladoId
     * @param gpoId
     * @param params
     * @param drDireccionBusqueda
     * @return A list of search result to CmtCuentaMatriz entity
     * @throws ApplicationException
     * @throws java.lang.CloneNotSupportedException
     */
    public List<CmtCuentaMatrizMgl> findCuentasMatricesByDrDireccion(GeograficoPoliticoMgl centroPoblado,
            DrDireccion drDireccionBusqueda) throws ApplicationException {
        CmtDireccionDetalleMglDaoImpl cmtDireccionDetalleMglDao = new CmtDireccionDetalleMglDaoImpl();   
        CmtDireccionDetalleMglManager direccionDetalladaManager = new CmtDireccionDetalleMglManager();
        List<CmtCuentaMatrizMgl> cuentaMatrizResultList = new ArrayList<>();      
        
        if (centroPoblado != null && centroPoblado.getGpoMultiorigen() != null
                && !centroPoblado.getGpoMultiorigen().isEmpty() && centroPoblado.getGpoMultiorigen().equalsIgnoreCase("1")) {
            drDireccionBusqueda.setMultiOrigen("1");
        } else {
            drDireccionBusqueda.setMultiOrigen("0");
        }
        
        //Buscamos la direccion tabulada en direccion_detallada
         List<CmtDireccionDetalladaMgl> direccionDetalladaList = cmtDireccionDetalleMglDao.buscarDireccionTabuladaMer(centroPoblado.getGpoId(),
                drDireccionBusqueda, false, 0, 0);
               
        
        List<CmtDireccionDetalladaMgl> direccionDetalladaTextoList = new ArrayList();
        ///Busqueda por texto para direcciones con nombres en la via principal      
        if (drDireccionBusqueda != null
                && drDireccionBusqueda.getDireccionRespuestaGeo() != null
                && !drDireccionBusqueda.getDireccionRespuestaGeo().isEmpty()) {
            direccionDetalladaTextoList = direccionDetalladaManager.busquedaDireccionTextoRespuestaGeo
                                   (drDireccionBusqueda.getDireccionRespuestaGeo(), drDireccionBusqueda, centroPoblado.getGpoId());
        }
        
        if ((direccionDetalladaList != null && !direccionDetalladaList.isEmpty())
                || (direccionDetalladaTextoList != null && !direccionDetalladaTextoList.isEmpty())) {
            direccionDetalladaList = direccionDetalladaManager.combinarDireccionDetalladaList
                                                 (direccionDetalladaList, direccionDetalladaTextoList);
        }


        if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {
            cuentaMatrizResultList = obtenerCuentaMatrizByDireccionDetalladaList(direccionDetalladaList);
        }
        //si el listado de cuenta matriz se encuentra vacio se envia en null
        if (cuentaMatrizResultList == null || cuentaMatrizResultList.isEmpty()) {
            return null;
        }

        return cuentaMatrizResultList;
    }
    
    
    public List<CmtCuentaMatrizMgl> obtenerCuentaMatrizByDireccionDetalladaList(List<CmtDireccionDetalladaMgl> direccionDetalladaList) {
        try {            
            CmtCuentaMatrizMglDaoImpl cuentaMatrizDaoImpl = new CmtCuentaMatrizMglDaoImpl();
            List<CmtCuentaMatrizMgl> cuentaMatrizResultList = new ArrayList();
            
            if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {
                for (CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl : direccionDetalladaList) {
                    List<CmtCuentaMatrizMgl> cmList = null;
                    if (cmtDireccionDetalladaMgl.getSubDireccion() == null) {
                        cmList = cuentaMatrizDaoImpl.findCuentasMatricesByIdDireccion
                                                    (cmtDireccionDetalladaMgl.getDireccion().getDirId());
                    }
                    if (cmList != null && !cmList.isEmpty()) {
                        for (CmtCuentaMatrizMgl cmtCuentaMatrizMgl : cmList) {
                            cuentaMatrizResultList.add(cmtCuentaMatrizMgl);
                        }
                    }
                }
            }
            return cuentaMatrizResultList;
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al obtener las cuentas "
                    + "matrices apartir de la direccion detallada: "+ex.getMessage()+"";
            LOGGER.error(msg);           
            return null;
        } catch (Exception ex) {
            String msg = "Se produjo un error al obtener las cuentas "
                    + "matrices apartir de la direccion detallada: "+ex.getMessage()+"";
            LOGGER.error(msg);
            return null;
        }
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
     public List<CmtCuentaMatrizMgl> findCcmmByNameAndCentroPoblado(String name, 
             GeograficoPoliticoMgl centroPoblado ) throws ApplicationException {

        CmtCuentaMatrizMglDaoImpl dao = new CmtCuentaMatrizMglDaoImpl();
        return dao.findCcmmByNameAndCentroPoblado(name, centroPoblado);
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
    public List<CmtCuentaMatrizMgl> findCuentasMatricesAgrupadorasByCentro(BigDecimal centro,
            CmtBasicaMgl basicaAgrupadora)
            throws ApplicationException {

        CmtCuentaMatrizMglDaoImpl cuentaMatrizDaoImpl = new CmtCuentaMatrizMglDaoImpl();
        return cuentaMatrizDaoImpl.findCuentasMatricesAgrupadorasByCentro(centro, basicaAgrupadora);
    }

    /**
     * Metodo para la creacion de 
     * cuenta matrices-edificios y hhpp en MER y RR
     * Autor: Victor Manuel Bocanegra
     *
     * @param creacionCCmm dto con toda la informacion necesaria para la creacion
     * @param addressServiceGestion informacion del geo
     * @param datosGestion datos de las tecnologias a crear
     * @param nodoXdefecto nodo por defecto
     * @param usuario en sesion
     * @param perfil del usuario en sesion
     * @return CmtCuentaMatrizMgl
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public CmtCuentaMatrizMgl creacionCCMMOptima(CreacionCcmmDto creacionCCmm,
            AddressService addressServiceGestion,
            Map<CmtBasicaMgl, NodoMgl> datosGestion, NodoMgl nodoXdefecto,
            String usuario, int perfil,
            boolean isFichaNodos) throws ApplicationException{
        
        CmtCuentaMatrizMglManager cuentaMatrizMglManager = new CmtCuentaMatrizMglManager();
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
        CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
        CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
        CmtCuentaMatrizMgl cmtCuentaMatrizMgl = new CmtCuentaMatrizMgl();
        try {
            //Valida si RR se encuentra encendido o apagado para realizar las operaciones en RR
            boolean habilitarRR = false;
            ParametrosMglManager parametrosManager = new ParametrosMglManager();
            ParametrosMgl parametroHabilitarRR = parametrosManager.findParametroMgl(Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }
            boolean hasTorres = false;
            boolean creaTecnoSub;
            boolean sincronizaRR = false;
            List<CmtParamentrosComplejosDto> complejosDtosList;
            AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
            String comunidad = "000";
            String division = "000";
            String headEnd = "00";
            NodoMgl nodoUnico = new NodoMgl();
            CmtCompaniaMglDaoImpl cmtCompaniaMglDaoImpl = new CmtCompaniaMglDaoImpl();

            for (Map.Entry<CmtBasicaMgl, NodoMgl> n : datosGestion.entrySet()) {
                validarCreacionCM(creacionCCmm.getOrigenSolicitud(), n.getValue());
                if (!n.getKey().getListCmtBasicaExtMgl().isEmpty()) {
                    for (CmtBasicaExtMgl cmtBasicaExtMgl : n.getKey().getListCmtBasicaExtMgl()) {
                        if (habilitarRR) {
                            if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                    equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                    && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y") && n.getKey().isNodoTecnologia()) {
                                CmtComunidadRr comunidadRrr = n.getValue().getComId();
                                headEnd = n.getValue().getNodHeadEnd();
                                if (comunidadRrr != null) {
                                    comunidad = comunidadRrr.getCodigoRr();
                                    division = comunidadRrr.getRegionalRr().getCodigoRr();
                                    sincronizaRR = true;
                                    nodoUnico = n.getValue();
                                }
                            }
                        }
                    }
                }
            }
            cuentaMatrizMglManager = new CmtCuentaMatrizMglManager();

            cmtCuentaMatrizMgl.setCentroPoblado(creacionCCmm.getCentroPoblado());
            cmtCuentaMatrizMgl.setComunidad(comunidad);
            cmtCuentaMatrizMgl.setDepartamento(creacionCCmm.getDepartamento());
            cmtCuentaMatrizMgl.setDivision(division);
            cmtCuentaMatrizMgl.setMunicipio(creacionCCmm.getMunicipio());
            cmtCuentaMatrizMgl.setNombreCuenta(creacionCCmm.getNombreCuenta());
            cmtCuentaMatrizMgl.setEstadoRegistro(1);
            cmtCuentaMatrizMgl.setNumeroCuenta(BigDecimal.ZERO);
            if (isFichaNodos) {
                cmtCuentaMatrizMgl.setOrigenFicha(Constant.PLAN_DE_EXPANSION_NACIONAL);
            }

            //Creacion CCMM
            cmtCuentaMatrizMgl = cuentaMatrizMglManager.createCm(cmtCuentaMatrizMgl, usuario, perfil);
            //Fin Creacion CCMM

            CmtDireccionMgl cmtDireccionMgl;
            cmtDireccionMgl = creacionCCmm.getListCmtDireccionesMgl().get(0).mapearCamposCmtDireccionMgl();
            cmtDireccionMgl.setCuentaMatrizObj(cmtCuentaMatrizMgl);
            cmtDireccionMgl.setTdiId(2);
            cmtDireccionMgl.setEstadoRegistro(1);
            DrDireccion drDireccion = creacionCCmm.getListCmtDireccionesMgl().get(0).getCamposDrDireccion();
            DetalleDireccionEntity direccionEntity = drDireccion.convertToDetalleDireccionEntity();
            direccionEntity.setMultiOrigen(creacionCCmm.getCentroPoblado().getGpoMultiorigen());
            if (isFichaNodos) {
                cmtDireccionMgl.setCalleRr(creacionCCmm.getListCmtDireccionesMgl().get(0).getCalleRr());
                cmtDireccionMgl.setUnidadRr(creacionCCmm.getListCmtDireccionesMgl().get(0).getUnidadRr());
            } else {
                DireccionRRManager drrm = new DireccionRRManager(direccionEntity, "", null);
                String calleRRCorreg;
                calleRRCorreg = drrm.getDireccion().getCalle();

                if (cmtDireccionMgl.getCuentaMatrizObj() != null) {
                    GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                    GeograficoPoliticoMgl centroPobladoCompleto;
                    centroPobladoCompleto = geograficoPoliticoManager.findGeoPoliticoById(cmtDireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoId());

                    if (centroPobladoCompleto != null) {
                        if (centroPobladoCompleto.getCorregimiento() != null
                                && !centroPobladoCompleto.getCorregimiento().isEmpty()
                                && centroPobladoCompleto.getCorregimiento().equalsIgnoreCase("Y")) {

                            calleRRCorreg += " ";
                            String[] corregimiento = centroPobladoCompleto.getGpoNombre().split(" ");
                            for (int i = 0; i < corregimiento.length; i++) {
                                calleRRCorreg += corregimiento[i];
                                //JDHT
                                cmtDireccionMgl.getCuentaMatrizObj().setCorregimientoAplicado(true);
                            }

                            if (calleRRCorreg.trim().length() > 50) {
                                throw new ApplicationException("Campo Calle ha superado el número máximo de caracteres en formato RR [" + calleRRCorreg + "] al añadir el corregimiento.");
                            }
                        }
                    }
                }

                cmtDireccionMgl.setCalleRr(calleRRCorreg);
                cmtDireccionMgl.setUnidadRr(drrm.getDireccion().getNumeroUnidad());
            }
            cmtDireccionMgl.setComentario("CREACION");

            AddressRequest addressRequest = new AddressRequest();
            DrDireccionManager direccionManager = new DrDireccionManager();
            String direccion = direccionManager.getDireccion(drDireccion);
            addressRequest.setAddress(direccion);
            addressRequest.setCity(creacionCCmm.getCentroPoblado().getGpoNombre());
            addressRequest.setCodDaneVt(creacionCCmm.getCentroPoblado().getGeoCodigoDane());
            addressRequest.setLevel("C");

            if (creacionCCmm.getCentroPoblado().getGpoMultiorigen().equalsIgnoreCase("1")
                    && cmtDireccionMgl.getCodTipoDir() != null && cmtDireccionMgl.getCodTipoDir().equalsIgnoreCase("CK")) {
                if (cmtDireccionMgl.getBarrio() != null && !cmtDireccionMgl.getBarrio().isEmpty()) {
                    addressRequest.setNeighborhood(cmtDireccionMgl.getBarrio());
                }
            }

            addressRequest.setState(creacionCCmm.getDepartamento().getGpoNombre());
            ResponseMessage responseMessage;
            if (isFichaNodos) {
                //Metodo Create Address para fichas nodos pasando los datos ya georeferenciados
                responseMessage = addressEJBRemote.createAddressFichas(addressServiceGestion.getRespuestaGeo(), new AddressGeodata(), addressRequest, usuario, "CM-MGL", "true", drDireccion);
            } else {
                responseMessage = addressEJBRemote.createAddress(addressRequest, usuario, "CM-MGL", "true", drDireccion);
                if (ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE_EN_MALLA_ANTIGUA.equalsIgnoreCase(responseMessage.getMessageText())) {
                    CmtValidadorDireccionesManager direccionesManager = new CmtValidadorDireccionesManager();
                    boolean isCalleCarrera = cmtDireccionMgl.getCodTipoDir().equalsIgnoreCase("CK");
                    direccionesManager.actualizarDireccionMayaNueva(addressRequest.getAddress(), creacionCCmm.getMunicipio(), cmtDireccionMgl.getBarrio(), isCalleCarrera);
                }
            }

            if (responseMessage.getIdaddress() != null) {
                if ("ERROR".equalsIgnoreCase(responseMessage.getMessageType())) {
                    if (!responseMessage.getIdaddress().toUpperCase().contains("D")) {
                        throw new ApplicationException(" " + responseMessage.getMessageText());
                    }
                }
                if (responseMessage.getIdaddress().toUpperCase().equalsIgnoreCase("")) {
                    throw new ApplicationException("Hubo un error al crear la direccion, Verifique con el area de HHPP");
                }
                if (responseMessage.getIdaddress().toUpperCase().contains("S")) {
                    throw new ApplicationException("La direccion se entendio como subdireccion, no es validada, Verifique con el area de HHPP");
                }
            } else {
                LOGGER.error("No fue entregado el Id Address a partir de la respuesta del servicio: " + responseMessage);
                throw new ApplicationException("No fue entregado el Id Address a partir de la respuesta del servicio de creación de dirección GEO: " + responseMessage);

            }
            DireccionMgl direccionMgl = new DireccionMgl();
            if (responseMessage.getIdaddress() != null) {
                direccionMgl.setDirId(new BigDecimal(responseMessage.getIdaddress().replace("d", "").replace("D", "")));
            }
            cmtDireccionMgl.setDireccionObj(direccionMgl);
            if (responseMessage.getNuevaDireccionDetallada() != null) {
                cmtDireccionMgl.setEstrato(responseMessage.getNuevaDireccionDetallada().getDireccion().getDirEstrato().intValue());
            } else {
                LOGGER.error("No se encontró nueva dirección detallada para la dirección: " + responseMessage.getAddress());
                throw new ApplicationException("No se encontró nueva dirección detallada para la dirección: " + responseMessage.getAddress());

            }
            //Creacion cmtDireccion
            cmtDireccionMglManager.create(cmtDireccionMgl, usuario, perfil);
            //Fin Creacion cmtDireccion

            cmtCuentaMatrizMgl.getDireccionesList().add(cmtDireccionMgl);
            cmtCuentaMatrizMgl.setDireccionesList(new ArrayList<>());
            cmtCuentaMatrizMgl.getDireccionesList().add(cmtDireccionMgl);

            CmtSubEdificioMgl cmtSubEdificioMgl = new CmtSubEdificioMgl();

            CmtSolicitudSubEdificioMgl cmtSubedificiosSolicitudMglTemp = creacionCCmm.getListCmtSolicitudSubEdificioMgl().get(0);
            cmtSubEdificioMgl.setAdministrador(cmtSubedificiosSolicitudMglTemp.getAdministrador());
            cmtSubEdificioMgl.setCmtCuentaMatrizMglObj(cmtCuentaMatrizMgl);
            if (cmtSubedificiosSolicitudMglTemp.getCompaniaConstructoraObj() == null
                    && creacionCCmm.getOrigenSolicitud().getBasicaId().compareTo(basicaMglManager.findByCodigoInternoApp(
                            Constant.BASICA_TIPO_SOLICITUD_ACOMETIDA).getBasicaId()) == 0) {
                CmtCompaniaMgl cmtCompaniaMgl = cmtCompaniaMglDaoImpl.find(new BigDecimal("1294"));
                cmtSubEdificioMgl.setCompaniaConstructoraObj(cmtCompaniaMgl);
            } else {
                cmtSubEdificioMgl.setCompaniaConstructoraObj(cmtSubedificiosSolicitudMglTemp.getCompaniaConstructoraObj());
            }
            cmtSubEdificioMgl.setCompaniaAdministracionObj(cmtSubedificiosSolicitudMglTemp.getCompaniaAdministracionObj());
            cmtSubEdificioMgl.setCompaniaAscensorObj(cmtSubedificiosSolicitudMglTemp.getCompaniaAscensorObj());
            cmtSubEdificioMgl.setCuentaMatrizObj(cmtCuentaMatrizMgl);
            cmtSubEdificioMgl.setDireccion(direccion);
            cmtSubEdificioMgl.setTelefonoPorteria(cmtSubedificiosSolicitudMglTemp.getTelefonoPorteria());
            cmtSubEdificioMgl.setTelefonoPorteria2(cmtSubedificiosSolicitudMglTemp.getTelefonoPorteria2());
            cmtSubEdificioMgl.setDireccionAntigua(addressServiceGestion != null && addressServiceGestion.getAlternateaddress() != null ? addressServiceGestion.getAlternateaddress() : "");
            cmtSubEdificioMgl.setFechaEntregaEdificio(cmtSubedificiosSolicitudMglTemp.getFechaEntregaEdificio());
            cmtSubEdificioMgl.setCambioestado("N");
            BigDecimal basicaId = nodoXdefecto.getNodTipo().getBasicaId();
            if (creacionCCmm.isCasaaEdificio()) {
                CmtBasicaMgl basicaMgl = new CmtBasicaMgl();                
                if (basicaId.toString().equals(Constant.NODO_TIPO_RED_TECNOLOGIA_FTTH)) {           
                    basicaMgl.setBasicaId(basicaMglManager.findIdBasicaCodigo(
                            Constant.CODIGO_BASICA_FTTH_RED_EXTERNA, Constant.NOMBRE_TIPO_ESTADOS_CCMM).getBasicaId());                 
                } else if(basicaId.toString().equals(Constant.NODO_TIPO_RED_TECNOLOGIA_BI)) {
                    basicaMgl.setBasicaId(basicaMglManager.findByCodigoInternoAppAndCodigo(
                            Constant.BASICA_TIPO_TEC_CABLE, Constant.CODIGO_BASICA_HFC_RED_EXTERNA).getBasicaId());
                } else {
                    basicaMgl.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                            Constant.BASICA_ESTADO_SIN_VISITA_TECNICA).getBasicaId());
                }
                basicaMgl = basicaMglManager.findById(basicaMgl);
                cmtSubEdificioMgl.setEstadoSubEdificioObj(basicaMgl);
                creaTecnoSub = true;
                cmtSubEdificioMgl.getCuentaMatrizObj().setUnicoEdif("SI");
            } else {
                if (creacionCCmm.getOrigenSolicitud() != null
                        && creacionCCmm.getOrigenSolicitud().getIdentificadorInternoApp()
                                .equals(Constant.BASICA_ORIGEN_DE_SOLICTUD_CONSTRUCTORA)) {
                    if (creacionCCmm.getCantidadTorres() == null || creacionCCmm.getCantidadTorres().compareTo(BigDecimal.ZERO) == 0) {
                        CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                        basicaMgl.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                                Constant.BASICA_ESTADO_SIN_VISITA_TECNICA).getBasicaId());
                        basicaMgl = basicaMglManager.findById(basicaMgl);
                        cmtSubEdificioMgl.setEstadoSubEdificioObj(basicaMgl);
                        creaTecnoSub = true;
                        cmtSubEdificioMgl.getCuentaMatrizObj().setUnicoEdif("SI");
                    } else {
                        CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                        basicaMgl.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                                Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId());
                        basicaMgl = basicaMglManager.findById(basicaMgl);
                        cmtSubEdificioMgl.setEstadoSubEdificioObj(basicaMgl);
                        creaTecnoSub = true;
                        cmtSubEdificioMgl.getCuentaMatrizObj().setUnicoEdif("NO");
                    }
                } else {
                    CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                    if (basicaId.toString().equals(Constant.NODO_TIPO_RED_TECNOLOGIA_FTTH)) {                   
                        basicaMgl.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                            Constant.EST_INI_FIBRA_FTTTH).getBasicaId());                    
                    } else if(basicaId.toString().equals(Constant.NODO_TIPO_RED_TECNOLOGIA_BI)) {
                        basicaMgl.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                                Constant.BASICA_ESTADO_SIN_VISITA_TECNICA).getBasicaId());
                    } else {
                        basicaMgl.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                                Constant.BASICA_ESTADO_SIN_VISITA_TECNICA).getBasicaId());
                    }
                    basicaMgl = basicaMglManager.findById(basicaMgl);
                    cmtSubEdificioMgl.setEstadoSubEdificioObj(basicaMgl);
                    cmtSubEdificioMgl.getCuentaMatrizObj().setUnicoEdif("SI");
                    creaTecnoSub = true;
                }
            }
            cmtSubEdificioMgl.setEstrato(cmtSubedificiosSolicitudMglTemp.getEstratoSubEdificioObj());
            cmtSubEdificioMgl.setHeadEnd(headEnd);
            cmtSubEdificioMgl.setNombreSubedificio(creacionCCmm.getNombreEdificioGeneral());
            cmtSubEdificioMgl.setOrigenDatosObj(null);
            cmtSubEdificioMgl.setTipoEdificioObj(cmtSubedificiosSolicitudMglTemp.getTipoEdificioObj());
            cmtSubEdificioMgl.setTipoLoc("ND");
            CmtBasicaMgl basicaMglOrigendatos = new CmtBasicaMgl();
            if (isFichaNodos) {
                basicaMglOrigendatos.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                        Constant.BASICA_ORIGEN_DATOS_NODO_NUEVO).getBasicaId());
            } else {
                basicaMglOrigendatos.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                        Constant.BASICA_ORIGEN_DATOS_BARRIDO_CALLES).getBasicaId());
            }
            basicaMglOrigendatos = basicaMglManager.findById(basicaMglOrigendatos);
            cmtSubEdificioMgl.setOrigenDatosObj(basicaMglOrigendatos);
            cmtSubEdificioMgl.setPlanos("N");
            cmtSubEdificioMgl.setReDiseno("N");
            cmtSubEdificioMgl.setVisitaTecnica("N");
            cmtSubEdificioMgl.setCierre("N");
            cmtSubEdificioMgl.setConexionCorriente("N");
            cmtSubEdificioMgl.setUnidadesEstimadas(cmtSubedificiosSolicitudMglTemp.getUnidades());
            cmtSubEdificioMgl.setCodigoRr("0000");
            if (sincronizaRR) {
                cmtSubEdificioMgl.setNodoObj(nodoUnico);
            } else {
                cmtSubEdificioMgl.setNodoObj(nodoXdefecto);
            }
            cmtSubEdificioMgl.setEstadoRegistro(1);

            CmtBasicaMgl basicaMglTipoProyecto = new CmtBasicaMgl();
            if (creacionCCmm.getOrigenSolicitud().getIdentificadorInternoApp().equals(
                    Constant.BASICA_ORIGEN_DE_SOLICTUD_CONSTRUCTORA)) {
                basicaMglTipoProyecto.setBasicaId(basicaMglManager.findByCodigoInternoApp(Constant.BASICA_TIPO_PROYECTO_CONSTRUCTORARECIDENCIAL).getBasicaId());
            } else {
                if (isFichaNodos) {
                    basicaMglTipoProyecto.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                            Constant.BASICA_TIPO_PROYECTO_NODO_NUEVO).getBasicaId());
                } else {
                    basicaMglTipoProyecto.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                            Constant.BASICA_TIPO_PROYECTO_ACOMETIDA_NORMAL).getBasicaId());
                }
            }
            basicaMglTipoProyecto = basicaMglManager.findById(basicaMglTipoProyecto);
            cmtSubEdificioMgl.setTipoProyectoObj(basicaMglTipoProyecto);

            //Creacion subedifico en MER
            cmtSubEdificioMgl = cmtSubEdificioMglManager.create(cmtSubEdificioMgl, usuario, perfil);
            //Fin Creacion subedifico en MER

            if (creaTecnoSub) {
                cmtTecnologiaSubMglManager.crearTecnSubXGestion(datosGestion, cmtSubEdificioMgl, usuario, perfil);
            }
            
            CmtBasicaMgl basicaMglProducto = new CmtBasicaMgl();
            
            if (basicaId.toString().equals(Constant.NODO_TIPO_RED_TECNOLOGIA_FTTH)) {                   
                basicaMglProducto.setBasicaId(Constant.BASICA_PRODUCTO_FTTH);
            } else if(basicaId.toString().equals(Constant.NODO_TIPO_RED_TECNOLOGIA_BI)) {
                basicaMglProducto.setBasicaId(basicaMglManager.findByCodigoInternoApp(Constant.BASICA_TIPO_PRODUCTO_MULTIPLAY).getBasicaId()); 
            }
            basicaMglProducto = basicaMglManager.findById(basicaMglProducto);
            cmtSubEdificioMgl.setProductoObj(basicaMglProducto);
            cmtCuentaMatrizMgl.setListCmtSubEdificioMgl(new ArrayList<>());
            cmtCuentaMatrizMgl.getListCmtSubEdificioMgl().add(cmtSubEdificioMgl);

            if (creacionCCmm.getCantidadTorres() != null
                    && creacionCCmm.getCantidadTorres().compareTo(BigDecimal.ZERO) > 0) {
                CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                basicaMgl.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                        Constant.BASICA_ESTADO_SIN_VISITA_TECNICA).getBasicaId());
                basicaMgl = basicaMglManager.findById(basicaMgl);
                int CantidadTorresAGenerar = creacionCCmm.getCantidadTorres().intValue();
                for (int ContadorTorres = 1; ContadorTorres <= CantidadTorresAGenerar; ContadorTorres++) {
                    cmtSubEdificioMgl = new CmtSubEdificioMgl();
                    cmtSubEdificioMgl.setAdministrador(cmtSubedificiosSolicitudMglTemp.getAdministrador());
                    cmtSubEdificioMgl.setCmtCuentaMatrizMglObj(cmtCuentaMatrizMgl);
                    cmtSubEdificioMgl.setCompaniaAdministracionObj(cmtSubedificiosSolicitudMglTemp.getCompaniaAdministracionObj());
                    cmtSubEdificioMgl.setCompaniaAscensorObj(cmtSubedificiosSolicitudMglTemp.getCompaniaAscensorObj());
                    cmtSubEdificioMgl.setCompaniaConstructoraObj(cmtSubedificiosSolicitudMglTemp.getCompaniaConstructoraObj());
                    cmtSubEdificioMgl.setTelefonoPorteria(cmtSubedificiosSolicitudMglTemp.getTelefonoPorteria());
                    cmtSubEdificioMgl.setFechaEntregaEdificio(cmtSubedificiosSolicitudMglTemp.getFechaEntregaEdificio());
                    cmtSubEdificioMgl.setCuentaMatrizObj(cmtCuentaMatrizMgl);
                    cmtSubEdificioMgl.setDireccion("");
                    cmtSubEdificioMgl.setDireccionAntigua("");
                    cmtSubEdificioMgl.setEstadoRegistro(1);
                    cmtSubEdificioMgl.setEstadoSubEdificioObj(basicaMgl);
                    cmtSubEdificioMgl.setOrigenDatosObj(basicaMglOrigendatos);
                    cmtSubEdificioMgl.setTipoProyectoObj(basicaMglTipoProyecto);
                    cmtSubEdificioMgl.setEstrato(cmtSubedificiosSolicitudMglTemp.getEstratoSubEdificioObj());
                    cmtSubEdificioMgl.setHeadEnd(headEnd);
                    cmtSubEdificioMgl.setNombreSubedificio("TORRE " + ContadorTorres);
                    cmtSubEdificioMgl.setOrigenDatosObj(null);
                    cmtSubEdificioMgl.setTipoEdificioObj(cmtSubedificiosSolicitudMglTemp.getTipoEdificioObj());
                    cmtSubEdificioMgl.setTipoLoc("ND");
                    cmtSubEdificioMgl.setPlanos("N");
                    cmtSubEdificioMgl.setReDiseno("N");
                    cmtSubEdificioMgl.setVisitaTecnica("N");
                    cmtSubEdificioMgl.setCierre("N");
                    cmtSubEdificioMgl.setConexionCorriente("N");
                    cmtSubEdificioMgl.setUnidadesEstimadas(cmtSubedificiosSolicitudMglTemp.getUnidades());
                    cmtSubEdificioMgl.setCodigoRr("0000");
                    if (sincronizaRR) {
                        cmtSubEdificioMgl.setNodoObj(nodoUnico);
                    } else {
                        cmtSubEdificioMgl.setNodoObj(nodoXdefecto);
                    }
                    cmtSubEdificioMgl.setProductoObj(basicaMglProducto);
                    cmtSubEdificioMglManager.create(cmtSubEdificioMgl, usuario, perfil);
                    cmtSubEdificioMgl.setEstadoRegistro(1);
                    cmtCuentaMatrizMgl.getListCmtSubEdificioMgl().add(cmtSubEdificioMgl);
                    cmtSubEdificioMgl.getCuentaMatrizObj().setUnicoEdif("NO");
                    cmtTecnologiaSubMglManager.crearTecnSubXGestion(datosGestion, cmtSubEdificioMgl, usuario, perfil);

                    hasTorres = true;
                }
            }

            //Proceso para creacion de edificio en rr 
            registrarInfoInRR(cmtCuentaMatrizMgl, direccionEntity, cmtDireccionMgl,
                    hasTorres, usuario, addressServiceGestion, datosGestion, perfil,
                    sincronizaRR, addressRequest, addressEJBRemote, isFichaNodos, creacionCCmm);
            
         return cmtCuentaMatrizMgl;
         
        } catch (ApplicationException | ExceptionDB ex) {
            List<CmtSubEdificioMgl> SubEEraseList = null;
            List<CmtDireccionMgl> direccionEraseList = null;
            if (cmtCuentaMatrizMgl != null) {
                SubEEraseList = cmtCuentaMatrizMgl.getListCmtSubEdificioMglActivos();
                direccionEraseList = cmtCuentaMatrizMgl.getDireccionesList();
            }

            if (SubEEraseList != null && !SubEEraseList.isEmpty()) {
                for (CmtSubEdificioMgl edificioMgl : SubEEraseList) {
                    cmtSubEdificioMglManager.deleteSinRr(edificioMgl, usuario, perfil);
                    List<CmtTecnologiaSubMgl> listaTecnoABorrar;
                    listaTecnoABorrar = cmtTecnologiaSubMglManager.findTecnoSubBySubEdi(edificioMgl);
                    for (CmtTecnologiaSubMgl lista : listaTecnoABorrar) {
                        cmtTecnologiaSubMglManager.deleteSubEdificioTecnologia(lista, usuario, perfil);
                    }
                }
            }
            if (direccionEraseList != null) {
                for (CmtDireccionMgl direccionE : direccionEraseList) {
                    cmtDireccionMglManager.deleteSinRr(direccionE, usuario, perfil);
                }
            }
            boolean deleteSinRr = false;
            if (cmtCuentaMatrizMgl != null) {
                deleteSinRr = cuentaMatrizMglManager.deleteSinRr(cmtCuentaMatrizMgl, usuario, perfil);
            }
            if (deleteSinRr) {
                LOGGER.info("MATRIZ BORRADA ROLLBACK");
            }
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg, ex);
            throw new ApplicationException("MATRIZ BORRADA ROLLBACK " + ex.getMessage(), ex);
        }
    }

    private AddressEJBRemote lookupaddressEJBBean() {
        try {
            Context c = new InitialContext();
            return (AddressEJBRemote) c.lookup("addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote");
        } catch (NamingException ne) {
            LOGGER.error(ne);
            throw new RuntimeException(ne);
        }
    }

    public void validarCreacionCM(CmtBasicaMgl origenSol, NodoMgl nodoMgl)
            throws ApplicationException {
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        List<CmtParamentrosComplejosDto> complejosDtosList;
        boolean isValido = false;
        if (nodoMgl.getNodCodigo().toUpperCase().contains("NFI")) {
            complejosDtosList = parametrosMglManager.findComplejo(PERMITE_CREA_NFI);
            if (complejosDtosList != null) {
                String valueTipoOrigen = origenSol.getAbreviatura();
                for (CmtParamentrosComplejosDto val : complejosDtosList) {
                    if (val.getValue().equalsIgnoreCase(valueTipoOrigen)) {
                        isValido = true;
                    }
                }
                if (!isValido) {
                    throw new ApplicationException("No se puede crear la cuenta "
                            + " matriz de Origen solicitud " + origenSol.getNombreBasica()
                            + " sobre un nodo NFI;");
                }
            } else {
                throw new ApplicationException("No se encontro la configuracion "
                        + "para validar creacion  sobre NFI(PERMITE_CREA_NFI);");
            }
        }
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        CmtBasicaMgl inactivo = basicaMglManager.findByCodigoInternoApp(Constant.BASICA_TIPO_ESTADO_NODO_NO_CERTIFICADO);
        if (nodoMgl.getEstado().getBasicaId() == inactivo.getBasicaId()) {
            if (!nodoMgl.getNodCodigo().toUpperCase().contains("NFI")) {
                complejosDtosList = parametrosMglManager.findComplejo(PERMITE_CREA_NOD_OFF);
                isValido = false;
                if (complejosDtosList != null) {
                    String valueTipoOrigen = origenSol.getAbreviatura();
                    for (CmtParamentrosComplejosDto val : complejosDtosList) {
                        if (val.getValue().equalsIgnoreCase(valueTipoOrigen)) {
                            isValido = true;
                        }
                    }
                    if (!isValido) {
                        throw new ApplicationException("No se puede crear la "
                                + "cuenta matriz de Origen solicitud "
                                + origenSol.getNombreBasica()
                                + " sobre un nodo no certificado;");
                    }
                } else {
                    throw new ApplicationException("No se encontro la "
                            + "configuracion para validar creacion  "
                            + "sobre nodos apagados(PERMITE_CREA_NOD_OFF);");
                }
            }
        }
    }

    public boolean registrarInfoInRR(CmtCuentaMatrizMgl cuentaMatrizMgl,
            DetalleDireccionEntity direccionEntity, CmtDireccionMgl cmtDireccionMgl,
            boolean hasTorres, String usuario, AddressService addressServiceGestion,
            Map<CmtBasicaMgl, NodoMgl> datosGestion, int perfil, boolean sincronizaRR,
            AddressRequest addressRequest, AddressEJBRemote addressEJBRemote,
            boolean isFichaNodos, CreacionCcmmDto creacionCcmmDto)
            throws ApplicationException {

    
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
        basicaMgl.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                Constant.BASICA_ESTADO_SIN_VISITA_TECNICA).getBasicaId());
        CmtTablasBasicasRRMglManager cmtTablasBasicasRRMglManager = new CmtTablasBasicasRRMglManager();
        boolean habilitarRR = false;
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
        if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
            habilitarRR = true;
        }
        try {
            for (Map.Entry<CmtBasicaMgl, NodoMgl> n : datosGestion.entrySet()) {
                sincronizaRR = n.getKey().isNodoTecnologia(); //valbuenayf  ajuste checkbox 
                if (habilitarRR) {
                    if (sincronizaRR) {
                        try {
                            boolean siCreaEdificioRr = cmtTablasBasicasRRMglManager.edificioAdd(
                                    n.getValue(),
                                    addressServiceGestion,
                                    cuentaMatrizMgl,
                                    usuario,
                                    isFichaNodos);
                            if (!siCreaEdificioRr) {
                                throw new ApplicationException("Error creando edificio en registrarInfoInRR");
                            }
                        } catch (ApplicationException ae) {
                            try {
                                if (sincronizaRR) {
                                    if (!cuentaMatrizMgl.getNumeroCuenta().equals(0)) {
                                        cmtTablasBasicasRRMglManager.edificioDelete(cuentaMatrizMgl, usuario);
                                    }
                                }
                            } catch (ApplicationException ae1) {
                                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ae1.getMessage();
                                LOGGER.error(msg, ae1);
                            }
                            CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
                            List<CmtSubEdificioMgl> SubEEraseList = cuentaMatrizMgl.getListCmtSubEdificioMglActivos();
                            List<CmtDireccionMgl> direccionEraseList = cuentaMatrizMgl.getDireccionesList();
                            if (SubEEraseList != null) {
                                CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
                                for (CmtSubEdificioMgl edificioMgl : SubEEraseList) {
                                    cmtSubEdificioMglManager.deleteSinRr(edificioMgl, usuario, perfil);
                                    CmtTecnologiaSubMgl cmtTecnologiaSubMglBorra;
                                    cmtTecnologiaSubMglBorra = cmtTecnologiaSubMglManager.findBySubEdificioTecnologia(edificioMgl, n.getKey());
                                    if (cmtTecnologiaSubMglBorra != null && cmtTecnologiaSubMglBorra.getTecnoSubedificioId() != null) { //valbuenayf  ajuste checkbox 
                                        cmtTecnologiaSubMglManager.deleteSubEdificioTecnologia(cmtTecnologiaSubMglBorra, usuario, perfil);
                                    }
                                }
                            }
                            if (direccionEraseList != null) {
                                CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
                                for (CmtDireccionMgl direccionE : direccionEraseList) {
                                    cmtDireccionMglManager.deleteSinRr(direccionE, usuario, perfil);
                                }
                            }

                            if (cuentaMatrizMgl.getCuentaMatrizId() != null) {
                                deleteSinRr(cuentaMatrizMgl, usuario, perfil);
                            }
                            throw new ApplicationException(ae);
                        }
                        updateSinRr(cuentaMatrizMgl, usuario, perfil);
                        List<CmtSubEdificioMgl> SubEUpdateList = cuentaMatrizMgl.getListCmtSubEdificioMglActivos();
                        if (SubEUpdateList != null) {
                             CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
                            for (CmtSubEdificioMgl edificioMgl : SubEUpdateList) {
                                cmtSubEdificioMglManager.updateSinRr(edificioMgl, usuario, perfil);
                            }
                        }
                    }
                }
            }
        } catch (ApplicationException ex) {
            try {
                if (sincronizaRR) {
                    if (!cuentaMatrizMgl.getNumeroCuenta().equals(0)) {
                        cmtTablasBasicasRRMglManager.edificioDelete(cuentaMatrizMgl, usuario);
                    }
                }
            } catch (ApplicationException ae1) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ae1.getMessage();
                LOGGER.error(msg, ae1);
            }
            CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
            List<CmtSubEdificioMgl> SubEEraseList = cuentaMatrizMgl.getListCmtSubEdificioMglActivos();
            List<CmtDireccionMgl> direccionEraseList = cuentaMatrizMgl.getDireccionesList();
            if (SubEEraseList != null) {
                CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
                for (Map.Entry<CmtBasicaMgl, NodoMgl> n : datosGestion.entrySet()) {
                    for (CmtSubEdificioMgl edificioMgl : SubEEraseList) {
                        cmtSubEdificioMglManager.deleteSinRr(edificioMgl, usuario, perfil);
                        CmtTecnologiaSubMgl cmtTecnologiaSubMglBorra;
                        cmtTecnologiaSubMglBorra = cmtTecnologiaSubMglManager.findBySubEdificioTecnologia(edificioMgl, n.getKey());
                        if (cmtTecnologiaSubMglBorra != null && cmtTecnologiaSubMglBorra.getTecnoSubedificioId() != null) {
                            cmtTecnologiaSubMglManager.deleteSubEdificioTecnologia(cmtTecnologiaSubMglBorra, usuario, perfil);
                        }
                    }
                }
            }
            if (direccionEraseList != null) {
                CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
                for (CmtDireccionMgl direccionE : direccionEraseList) {
                    cmtDireccionMglManager.deleteSinRr(direccionE, usuario, perfil);
                }
            }

            if (cuentaMatrizMgl.getCuentaMatrizId() != null) {
                deleteSinRr(cuentaMatrizMgl, usuario, perfil);
            }

            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg, ex);
            throw new ApplicationException(ex.getMessage(), ex);
        }
        return true;
    }
}
