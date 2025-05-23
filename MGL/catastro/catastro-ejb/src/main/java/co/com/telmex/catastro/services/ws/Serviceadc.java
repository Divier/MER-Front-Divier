package co.com.telmex.catastro.services.ws;

import co.com.claro.mgl.businessmanager.address.DireccionesValidacionManager;
import co.com.claro.mgl.businessmanager.address.DrDireccionManager;
import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.ParametrosCallesManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.businessmanager.address.SolicitudManager;
import co.com.claro.mgl.businessmanager.cm.CmConvergenciaManager;
import co.com.claro.mgl.businessmanager.cm.CmtComponenteDireccionesMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTipoValidacionMglManager;
import co.com.claro.mgl.dtos.CmtBasicResponse;
import co.com.claro.mgl.dtos.CmtHhppMglDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ConfigurationAddressComponent;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.mgl.ws.cm.request.RequestCreaSolicitud;
import co.com.claro.mgl.ws.cm.request.RequestCreaSolicitudCambioEstrato;
import co.com.claro.mgl.ws.cm.request.RequestValidacionViabilidad;
import co.com.claro.mgl.ws.cm.request.RequestViabilidadTecnicaVenta;
import co.com.claro.mgl.ws.cm.response.ResponseConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseCreaSolicitud;
import co.com.claro.mgl.ws.cm.response.ResponseCreaSolicitudCambioEstrato;
import co.com.claro.mgl.ws.cm.response.ResponseGeograficoPolitico;
import co.com.claro.mgl.ws.cm.response.ResponseMesaje;
import co.com.claro.mgl.ws.cm.response.ResponseValidacionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseValidacionViabilidad;
import co.com.claro.mgl.ws.cm.response.ResponseViabilidadTecnicaVenta;
import co.com.claro.visitasTecnicas.business.ConsultaEspecificaManager;
import co.com.claro.visitasTecnicas.business.HHPPManager;
import co.com.claro.visitasTecnicas.business.NegocioParamMultivalor;
import co.com.claro.visitasTecnicas.business.NodoManager;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.claro.visitasTecnicas.entities.ParamMultivalor;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.AddressSuggested;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.data.EstadoSolicitud;
import co.com.telmex.catastro.data.Hhpp;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.SubDireccion;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.services.ws.initialize.Initialized;
import co.com.telmex.catastro.utilws.ResponseMessage;
import com.jlcg.db.exept.ExceptionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * WebService Serviceadc
 *
 * @author Jose Luis Caicedo G.
 * @version	1.0 - Modificado:Direcciones fase I Carlos Villamil 2012-12-11
 */
@Stateless
@WebService(serviceName = "DataAddress")
public class Serviceadc {

    private static String ADDRESSEJB = "addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote";
    private static String COMMON_TRUE = "TRUE";
    private static String COMMON_FALSE = "FALSE";
    private static final Logger LOGGER = LogManager.getLogger(Serviceadc.class);

    public Serviceadc() {
        Initialized.getInstance();
    }

    /**
     *
     * @param level
     * @param address
     * @param state
     * @param city
     * @param neighborhood
     * @return
     */
    @WebMethod
    @WebResult(name = "addressService")
    public AddressService queryStandarAddress(@WebParam(name = "level") String level,
            @WebParam(name = "address") String address,
            @WebParam(name = "state") String state,
            @WebParam(name = "city") String city,
            @WebParam(name = "neighborhood") String neighborhood) {
        AddressService addressService = new AddressService();
        if (address == null || city == null || state == null || level == null) {
            addressService.setRecommendations("Faltan datos se requiere nivel de detalle, dirección, ciudad y departamento");
        } else if ("".equals(address) || "".equals(city) || "".equals(state) || "".equals(level)) {
            addressService.setRecommendations("Faltan datos se requiere nivel de detalle, dirección, ciudad y departamento");
        } else {
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setAddress(address);
            addressRequest.setCity(city);
            addressRequest.setState(state);
            addressRequest.setLevel(level);
            if (neighborhood != null) {
                addressRequest.setNeighborhood(neighborhood);
            }
            try {
                addressService = callServiceAddress(addressRequest);
                if (addressService != null && addressService.getCoddanemcpio() != null
                        && !addressService.getCoddanemcpio().isEmpty()) {
                    if (ValidarMultiorigen(addressService.getCoddanemcpio().trim())) {
                        if (neighborhood == null || neighborhood.isEmpty() || "".equals(neighborhood)) {
                            addressService = new AddressService();
                            addressService.setRecommendations("Faltan datos el campo Barrio es obligatorio para ciudad Multiorigen.");
                        }
                    }
                }
            } catch (ApplicationException | NamingException ex) {
                LOGGER.error("Error al momento de consultar la dirección estándar. EX000: " + ex.getMessage(), ex);
                addressService.setRecommendations("ERROR: " + ex.getMessage());
            }
        }
        return addressService;
    }

    /**
     *
     * @param Codigo
     * @return
     * @throws NamingException
     * @throws Exception
     */
    private boolean ValidarMultiorigen(String Codigo) throws NamingException, ApplicationException {
        boolean Resultado = false;
        AddressEJBRemote obj = getAddressEjb();
        if (obj != null) {
            if ("1".equals(obj.queryMultiorigenByDivipola(Codigo))) {
                Resultado = true;
            }
        }
        return Resultado;
    }

    /**
     *
     * @param addressesRequest
     * @return
     */
    @WebMethod
    @WebResult(name = "addressesService")
    public List<AddressService> queryAddressBatch(@WebParam(name = "addressesRequest") List<AddressRequest> addressesRequest) {
        List<AddressService> addressesService = new ArrayList<>();

        AddressService addressService = new AddressService();
        for (int i = 0; i < addressesRequest.size(); i++) {

            if ("".equals(addressesRequest.get(i).getCity())) {
                addressService.setRecommendations("Faltan datos se requiere nivel de detalle, dirección, ciudad y departamento");
            }
        }
        if (addressesRequest != null && !addressesRequest.isEmpty()) {
            try {
                addressesService = callServiceAddressBatch(addressesRequest);
            } catch (ApplicationException | NamingException ex) {
                LOGGER.error("Error al momento de consultar la dirección estándar. EX000: " + ex.getMessage(), ex);
                addressService.setRecommendations("ERROR: " + ex.getMessage());
                addressesService.add(addressService);
            }
        }

        return addressesService;
    }

    /**
     *
     * @param address
     * @param state
     * @param city
     * @param neighborhood
     * @param user
     * @param applicationName
     * @param validate
     * @param codDaneVt
     * @return
     */
    @WebMethod(operationName = "createAddress")
    @WebResult(name = "createAddress")
    public ResponseMessage createAddress(
            @WebParam(name = "address") String address,
            @WebParam(name = "state") String state,
            @WebParam(name = "city") String city,
            @WebParam(name = "neighborhood") String neighborhood,
            @WebParam(name = "user") String user,
            @WebParam(name = "applicationName") String applicationName,
            @WebParam(name = "validate") String validate,
            @WebParam(name = "codDaneVt") String codDaneVt) {
        ResponseMessage responseMessage = new ResponseMessage();
        AddressService addressService = new AddressService();
        //validacion de usuario y aplicacion
        if (user == null || user.isEmpty()) {
            responseMessage.setMessageType(ResponseMessage.TYPE_ERROR);
            responseMessage.setMessageText(ResponseMessage.MESSAGE_ERROR_USUARIO_OBLIGATORIO);
        } else if (applicationName == null || applicationName.isEmpty()) {
            responseMessage.setMessageType(ResponseMessage.TYPE_ERROR);
            responseMessage.setMessageText(ResponseMessage.MESSAGE_ERROR_APPLICATION_NAME_OBLIGATORIO);
        } //Validacion de campos obligatorios
        else if (address == null || city == null || state == null) {
            responseMessage.setMessageType(ResponseMessage.TYPE_ERROR);
            responseMessage.setMessageText(ResponseMessage.MESSAGE_CAMPOS_OBLIGATORIOS);
        } else if ("".equals(address) || "".equals(city) || "".equals(state)) {
            responseMessage.setMessageType(ResponseMessage.TYPE_ERROR);
            responseMessage.setMessageText(ResponseMessage.MESSAGE_CAMPOS_OBLIGATORIOS);
        } else {
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setCodDaneVt(codDaneVt);
            addressRequest.setAddress(address);
            addressRequest.setCity(city);
            addressRequest.setState(state);
            if (neighborhood != null && !neighborhood.isEmpty()) {
                addressRequest.setNeighborhood(neighborhood);
            }
            try {
                responseMessage = createServiceAddress(addressRequest, user, applicationName, validate);
            } catch (ApplicationException | NamingException ex) {
                LOGGER.error("Error al momento de crear la dirección. EX000: " + ex.getMessage(), ex);
                addressService.setRecommendations("ERROR: " + ex.getMessage());
            }
        }
        return responseMessage;
    }

    /**
     *
     * @param idaddress
     * @param user
     * @param applicationName
     * @param updateToNewAddress
     * @return
     */
    @WebMethod
    @WebResult(name = "updateAddress")
    public ResponseMessage updateAddress(@WebParam(name = "idaddress") String idaddress, @WebParam(name = "user") String user, @WebParam(name = "applicationName") String applicationName, @WebParam(name = "updateToNewAddress") String updateToNewAddress) {
        ResponseMessage responseMessage = new ResponseMessage();
        //validacion de campos obligatorios
        if (user == null || user.isEmpty()) {
            responseMessage.setMessageType(ResponseMessage.TYPE_ERROR);
            responseMessage.setMessageText(ResponseMessage.MESSAGE_ERROR_USUARIO_OBLIGATORIO);
        } else if (applicationName == null || applicationName.isEmpty()) {
            responseMessage.setMessageType(ResponseMessage.TYPE_ERROR);
            responseMessage.setMessageText(ResponseMessage.MESSAGE_ERROR_USUARIO_OBLIGATORIO);
        } //Validacion de campos obligatorios
        else if (idaddress == null || "".equals(idaddress)) {
            responseMessage.setMessageType(ResponseMessage.TYPE_ERROR);
            responseMessage.setMessageText(ResponseMessage.MESSAGE_ADDRESS_OBLIGATORIOS);
        } else if (!updateToNewAddress.isEmpty()
                && (!updateToNewAddress.equalsIgnoreCase(COMMON_TRUE) && !updateToNewAddress.equalsIgnoreCase(COMMON_FALSE))) {
            responseMessage.setMessageType(ResponseMessage.TYPE_ERROR);
            responseMessage.setMessageText(ResponseMessage.MESSAGE_ERROR_FLAG_UPDATE_TO_NEW_INVALID);
        } else {
            try {
                responseMessage = updateServiceAddress(idaddress, user, applicationName, updateToNewAddress);
            } catch (ApplicationException | NamingException e) {
               LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
                responseMessage.setMessageType(ResponseMessage.TYPE_ERROR);
                responseMessage.setMessageText(e.getMessage());
            }
        }
        return responseMessage;
    }

    /**
     *
     * @param addressRequest
     * @return
     * @throws NamingException
     * @throws Exception
     */
    private AddressService callServiceAddress(AddressRequest addressRequest) throws NamingException, ApplicationException {
        AddressService addressService = null;
        AddressEJBRemote obj = getAddressEjb();
        if (obj != null) {   //Se modifica para requerimiento de direcciones sugeridas. Camilo Gaviria 2012-12-26
            addressService = obj.queryAddress(addressRequest);
        }
        return addressService;
    }

    /**
     *
     * @return @throws Exception
     */
    private static AddressEJBRemote getAddressEjb() throws ApplicationException, NamingException {
        InitialContext ctx = new InitialContext();
        AddressEJBRemote obj = (AddressEJBRemote) ctx.lookup(ADDRESSEJB);
        return obj;
    }

    /**
     *
     * @param addressRequest
     * @param user
     * @param nombreFuncionalidad
     * @param validate
     * @return
     * @throws NamingException
     * @throws Exception
     */
    private ResponseMessage createServiceAddress(AddressRequest addressRequest, String user, String nombreFuncionalidad, String validate) throws NamingException, ApplicationException {
        ResponseMessage response = null;
        AddressEJBRemote obj = getAddressEjb();
        if (obj != null) {
            response = obj.createAddress(addressRequest, user, nombreFuncionalidad, validate);
        }
        return response;
    }

    /**
     *
     * @param idaddress
     * @param user
     * @param applicationName
     * @param updateToNewAddress
     * @return
     * @throws NamingException
     * @throws Exception
     */
    private ResponseMessage updateServiceAddress(String idaddress, String user, String applicationName, String updateToNewAddress) throws NamingException, ApplicationException {
        ResponseMessage response = null;
        AddressEJBRemote obj = getAddressEjb();
        if (obj != null) {
            response = obj.updateAddress(idaddress, user, applicationName, updateToNewAddress);
        }
        return response;
    }

    /**
     *
     * @param token
     * @return
     */
    private boolean validateToken(String token) {
        //TODO; Algoritmo para validar token        
        return true;
    }

    /**
     *
     * @param addressesRequest
     * @return
     * @throws NamingException
     * @throws Exception
     */
    private List<AddressService> callServiceAddressBatch(List<AddressRequest> addressesRequest) throws NamingException, ApplicationException {
        List<AddressService> addressesService = null;
        AddressEJBRemote obj = getAddressEjb();
        if (obj != null) {
            addressesService = obj.queryAddressBatch(addressesRequest);
        }
        return addressesService;
    }

    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "getListGeoPolByGroupId")
    public List<ParamMultivalor> getListGeoPolByGroupId(@WebParam(name = "groupId") String groupId) {
        try {
            NegocioParamMultivalor result = new NegocioParamMultivalor();
            return result.getListElementByGroupId(groupId);
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de obtener la lista geográfica por grupo. EX000: " + e.getMessage(), e);
            return null;
        }
    }

    @WebMethod(operationName = "getNodeFromRepository")
    public Nodo getNodeFromRepository(@WebParam(name = "codNode") String codNode) throws ApplicationException {
        try {
            NodoManager nodoManager = new NodoManager();
            return nodoManager.queryNodo(codNode);
        } catch (Exception e) {
            LOGGER.error("Error al momento de obtener el nodo. EX000: " , e);
            throw new ApplicationException("Error al momento de obtener el nodo. EX000: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "createNodeInRepository")
    public boolean createNodeInRepository(@WebParam(name = "node") Nodo node) throws ApplicationException {
        try {
            NodoManager nodoManager = new NodoManager();
            return nodoManager.createNodo(node);
        } catch (Exception e) {
           LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            throw new ApplicationException("Error al momento de crear el nodo. EX000: " + e.getMessage(), e);
        }
    }


    @WebMethod(operationName = "createHHPPInRepository")
    public BigDecimal createHHPPInRepository(@WebParam(name = "hhpp") Hhpp hhpp,
            @WebParam(name = "nombreFuncionalidad") String nombreFuncionalidad) throws ApplicationException {
        try {
            HHPPManager manager = new HHPPManager();
            return manager.persistHhpp(hhpp, nombreFuncionalidad);
        } catch (ApplicationException e) {
           LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            throw new ApplicationException("Error al momento de crear el HHPP. EX000: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "getCityByComunity")
    public CityEntity getCityByComunity(@WebParam(name = "comunity") String comunity) throws ApplicationException {
        try {
            NegocioParamMultivalor manager = new NegocioParamMultivalor();
            return manager.consultaDptoCiudadVisor(comunity);
        } catch (ApplicationException e) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            throw new ApplicationException("Error al momento de obtener la ciudad por comunidad. EX000: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "getDireccionById")
    public Direccion getDireccionById(@WebParam(name = "idDirCatastro") String idDirCatastro) throws ApplicationException {
        try {
            ConsultaEspecificaManager manager = new ConsultaEspecificaManager();
            Direccion detalleDireccion = new Direccion();
            String indicador = idDirCatastro.substring(0, 1);
            if (indicador.equalsIgnoreCase("d")) {
                detalleDireccion = manager.queryAddressOnRepoById(idDirCatastro.substring(1));
            } else if (indicador.equalsIgnoreCase("s")) {
                SubDireccion detalleSubDireccion =
                        manager.querySubAddressOnRepositoryById(new BigDecimal(idDirCatastro.substring(1)));
                detalleDireccion =
                        manager.queryAddressOnRepoById(detalleSubDireccion.getDireccion().getDirId().toString());
            }
            return detalleDireccion;
        } catch (ApplicationException e) {
              LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            throw new ApplicationException("Error al momento de obtener la dirección. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "getEstadoBySolId")
    public EstadoSolicitud getEstadoBySolId(@WebParam(name = "solId") String solId) throws ApplicationException {
        try {
            SolicitudManager solicitudManager = new SolicitudManager();
            Solicitud sol = solicitudManager.findById(new BigDecimal(solId));
            EstadoSolicitud estado = new EstadoSolicitud();
            estado.setEstado(sol.getEstado());
            if (estado.getEstado().equals("FINALIZADO") || estado.getEstado().equals("FINALIZADA")) {
                estado.setResultado(sol.getRptGestion());
            } else {
                estado.setResultado("");
            }
            return estado;
        } catch (ApplicationException e) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            throw new ApplicationException("Error al momento de obtener el estado de la solicitud. EX000: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "getViabilidadHhppVenta")
    public ResponseViabilidadTecnicaVenta getViabilidadHhppVenta(@WebParam(name = "request") RequestViabilidadTecnicaVenta request) {
        CmConvergenciaManager cmConvergenciaManager = new CmConvergenciaManager();
        try {
            return cmConvergenciaManager.viabilidadTecnicaVenta(request);
        } catch (ApplicationException ex) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ ex.getMessage());
            ResponseViabilidadTecnicaVenta rvtv = new ResponseViabilidadTecnicaVenta();
            rvtv.setTipoRespuesta("E");
            rvtv.setViable("No");
            rvtv.setMensaje(ex.getMessage());
            return rvtv;
        }
    }

    @WebMethod(operationName = "getDireccionCalleCarreraRr")
    public DireccionRREntity getDireccionCalleCarreraRr(
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "comunidad") String comunidad,
            @WebParam(name = "barrio") String barrio) {
        CmConvergenciaManager cmConvergenciaManager = new CmConvergenciaManager();
        try {
            DireccionRREntity rvtv =
                    cmConvergenciaManager.convertirDireccionStringADrDireccion(direccion, comunidad, barrio);
            rvtv.setTipoMensaje("I");
            rvtv.setMensaje("Correcto");
            return rvtv;
        } catch (ApplicationException ex) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ ex.getMessage());
            DireccionRREntity rvtv = new DireccionRREntity();
            rvtv.setTipoMensaje("E");
            rvtv.setMensaje("Error al trasnformar dirreccion: " + ex.getMessage());
            return rvtv;
        } catch (Exception ex) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ ex.getMessage());
            DireccionRREntity rvtv = new DireccionRREntity();
            rvtv.setTipoMensaje("E");
            rvtv.setMensaje("Error al trasnformar dirreccion: " + ex.getMessage());
            return rvtv;
        }
    }

    /**
     * Crea Solicitud de cambio de estrato. Permite crear una solicitud de
     * cambio de estrato cuenta suscriptor sobre el repositorio para su
     * posterior gestion
     *
     * @author Johnnatan Ortiz
     * @param request request con la informacion necesaria para la creacion de
     * la solicitud
     * @return respuesta con el proceso de creacion de la solicitud
     *
     */
    @WebMethod(operationName = "crearSolicitudCambioEstrato")
    public ResponseCreaSolicitudCambioEstrato crearSolicitudCambioEstrato(
            @WebParam(name = "request") RequestCreaSolicitudCambioEstrato request) {
        try {
            SolicitudManager solicitudManager = new SolicitudManager();
            return solicitudManager.crearSolitudCambioEstrato(request);
        } catch (ApplicationException e) {
            ResponseCreaSolicitudCambioEstrato response =
                    new ResponseCreaSolicitudCambioEstrato();
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            response.setTipoRespuesta("E");
            response.setMensaje(e.getMessage());
            response.setIdSolicitud("0");
            return response;
        }
    }

    /**
     * Construye la direccion para una Solicitud. Permite crear una direccion
     * detallada campo a campo para luego se usada en una solicitud.
     *
     * @author Johnnatan Ortiz
     * @param request request con la informacion necesaria para la construccion
     * de la direccion
     * @return respuesta con direccion detallada
     *
     */
    @WebMethod(operationName = "construirDireccionSolicitud")
    public ResponseConstruccionDireccion construirDireccionSolicitud(
            @WebParam(name = "request") RequestConstruccionDireccion request) {
        try {
            DrDireccionManager manager = new DrDireccionManager();
            return manager.construirDireccionSolicitud(request);
        } catch (ApplicationException e) {
             LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            ResponseConstruccionDireccion response =
                    new ResponseConstruccionDireccion();
            ResponseMesaje responseMesaje = new ResponseMesaje();
            responseMesaje.setTipoRespuesta("E");
            responseMesaje.setMensaje(e.getMessage());
            response.setResponseMesaje(responseMesaje);
            response.setDrDireccion(request.getDrDireccion());
            return response;
        }
    }

    /**
     * Obtiene el listado de barrios de una direccion. Permite obtener el
     * listado de barrios de una direccion multiorigen.
     *
     * @author Johnnatan Ortiz
     * @param request request con la informacion de la direccion multiorigen
     * para obtener los barrios
     * @return respuesta con el proceso de creacion de la solicitud
     *
     */
    @WebMethod(operationName = "obtenerBarrioList")
    public ResponseConstruccionDireccion obtenerBarrioList(
            @WebParam(name = "request") RequestConstruccionDireccion request) {
        try {
            DireccionesValidacionManager manager = new DireccionesValidacionManager();
            DrDireccionManager managerDrDireccion = new DrDireccionManager();
            List<AddressSuggested> barrioList =
                    manager.obtenerBarrioSugerido(
                    request, false);
            ResponseConstruccionDireccion response = new ResponseConstruccionDireccion();
            ResponseMesaje responseMesaje = new ResponseMesaje();
            responseMesaje.setMensaje("Proceso Exitoso");
            responseMesaje.setTipoRespuesta("I");
            response.setResponseMesaje(responseMesaje);
            response.setDrDireccion(request.getDrDireccion());
            String dirStr = managerDrDireccion.getDireccion(request.getDrDireccion());
            response.setDireccionStr(dirStr);
            response.setBarrioList(barrioList);
            return response;
        } catch (Exception e) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            ResponseConstruccionDireccion response = new ResponseConstruccionDireccion();
            ResponseMesaje responseMesaje = new ResponseMesaje();
            responseMesaje.setMensaje("Ocurrio Un Error Obteniendo los barrios "
                    + e.getMessage());
            responseMesaje.setTipoRespuesta("E");
            response.setResponseMesaje(responseMesaje);
            response.setDrDireccion(request.getDrDireccion());
            return response;
        }
    }

    /**
     * Obtiene el listado de barrios de una direccion. Permite obtener el
     * listado de barrios de una direccion multiorigen.
     *
     * @author Johnnatan Ortiz
     * @param request request con la informacion de la direccion multiorigen
     * para obtener los barrios
     * @return respuesta con el proceso de creacion de la solicitud
     *
     */
    @WebMethod(operationName = "validarDireccion")
    public ResponseValidacionDireccion validarDireccion(
            @WebParam(name = "request") RequestConstruccionDireccion request) {
        try {
            DireccionesValidacionManager manager = new DireccionesValidacionManager();
            CityEntity cityEntity =
                    manager.validaDireccion(request, false);
            ResponseValidacionDireccion response = new ResponseValidacionDireccion();
            ResponseMesaje responseMesaje = new ResponseMesaje();
            responseMesaje.setMensaje("Proceso Exitoso");
            responseMesaje.setTipoRespuesta("I");
            response.setResponseMesaje(responseMesaje);
            response.setDrDireccion(request.getDrDireccion());
            response.setCityEntity(cityEntity);
            return response;
        } catch (ApplicationException e) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            ResponseValidacionDireccion response = new ResponseValidacionDireccion();
            ResponseMesaje responseMesaje = new ResponseMesaje();
            responseMesaje.setMensaje("Ocurrio Un Error Validadoando la Dirección "
                    + e.getMessage());
            responseMesaje.setTipoRespuesta("E");
            response.setResponseMesaje(responseMesaje);
            response.setDrDireccion(request.getDrDireccion());
            return response;
        }
    }

    /**
     * Obtine todos los niveles para una direccion. Permite obtener todos los
     * niveles necesarios para los diferentes niveles de una direccion
     *
     * @author Johnnatan Ortiz la solicitud
     * @return Configuracion de los niveles de la direccion
     *
     */
    @WebMethod(operationName = "obtenerConfiguracionComponenteDireccion")
    public ConfigurationAddressComponent obtenerConfiguracionComponenteDireccion() {
        try {
            CmtComponenteDireccionesMglManager cmtComponenteDireccionesMglManager =
                    new CmtComponenteDireccionesMglManager();
            return cmtComponenteDireccionesMglManager.getConfiguracionComponente();
        } catch (ApplicationException e) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            return null;
        }
    }

    /**
     * Crea Solicitud DTH.Permite crear una solicitud DTH sobre el repositorio
 para su posterior gestion
     *
     * @author Juan David Hernandez
     * @param request request con la informacion necesaria para la creacion de
     * la solicitud DTH
     * @param solicitudDireccionActual
     * @return respuesta con el proceso de creacion de la solicitud DTH
     *
     */
    @WebMethod(operationName = "crearSolicitudDth")
    public ResponseCreaSolicitud crearSolicitudDth(
            @WebParam(name = "request") RequestCreaSolicitud request,
            @WebParam(name = "tipoTecnologia") String tipoTecnologia,
            @WebParam(name = "unidadesList") List<UnidadStructPcml> unidadesList,
            @WebParam(name = "tiempoDuracionSolicitud") String tiempoDuracionSolicitud,
            @WebParam(name = "idCentroPoblado") BigDecimal idCentroPoblado,
            @WebParam(name = "usuarioVt") String usuarioVt,
            @WebParam(name = "perfilVt") int perfilVt,
            @WebParam(name = "tipoAccionSolicitud") String tipoAccionSolicitud,
            @WebParam(name = "solicitudDireccionActual") Solicitud solicitudDireccionActual) throws ExceptionDB {
        try {
            boolean habilitarRR = false;
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            //Valida si RR se encuentra encendido o apagado para realizar las operaciones en RR                 
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }
            SolicitudManager solicitudManager = new SolicitudManager();
            return solicitudManager.crearSolicitudDth(request, tipoTecnologia,
                    unidadesList, tiempoDuracionSolicitud, idCentroPoblado,
                    usuarioVt, perfilVt, tipoAccionSolicitud,
                    solicitudDireccionActual, habilitarRR, false, null, null, null, false);
        } catch (ApplicationException e) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            ResponseCreaSolicitud response = new ResponseCreaSolicitud();
            response.setTipoRespuesta("E");
            response.setMensaje(e.getMessage());
            response.setIdSolicitud("0");
            return response;
        }
    }

    /**
     * Crea Solicitud DTH.Permite crear una solicitud DTH sobre el repositorio
 para su posterior gestion
     *
     * @author Juan David Hernandez
     * @param request request con la informacion necesaria para la creacion de
     * la solicitud DTH
     * @param respuesta
     * @return respuesta con el proceso de creacion de la solicitud DTH
     *
     */
    @WebMethod(operationName = "crearSolicitudVisor")
    public ResponseCreaSolicitud crearSolicitudVisor(
            @WebParam(name = "request") RequestCreaSolicitud request,
            @WebParam(name = "tipoTecnologia") String tipoTecnologia,
            @WebParam(name = "tiempoDuracionSolicitud") String tiempoDuracionSolicitud,
            @WebParam(name = "idCentroPoblado") BigDecimal idCentroPoblado,
            @WebParam(name = "usuarioVt") String usuarioVt,
            @WebParam(name = "perfilVt") int perfilVt,
            @WebParam(name = "nodoGestion") String nodoGestion,
            @WebParam(name = "nodoCercano") String nodoCercano,
            @WebParam(name = "respuestaGestion") String respuestaGestion,
            @WebParam(name = "respuesta") String respuesta) throws ApplicationException, ExceptionDB {
        try {
            SolicitudManager solicitudManager = new SolicitudManager();
            return solicitudManager.crearGestionarSolicitudVisor(request, tipoTecnologia,
                    tiempoDuracionSolicitud, idCentroPoblado, usuarioVt, perfilVt,
                    nodoGestion, nodoCercano, respuestaGestion, respuesta);

        } catch (ApplicationException e) {
           LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            ResponseCreaSolicitud response = new ResponseCreaSolicitud();
            response.setTipoRespuesta("E");
            response.setMensaje(e.getMessage());
            response.setIdSolicitud("0");
            return response;
        }
    }

    /**
     * obtenerCentroPoblado. Permite obtener el listado de centro poblado
     * apartir de una ciudad.
     *
     * @author Juan David Hernandez
     * @param ciudadRr ciudad de la cuan se desea obtener el listado de centro
     * poblado
     * @return listrado de centro poblado de la ciudad
     *
     */
    @WebMethod(operationName = "obtenerCentroPoblado")
    public List<ResponseGeograficoPolitico> obtenerCentroPoblado(
            @WebParam(name = "ciudadRr") String ciudadRr) {
        try {
            GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
            return geograficoPoliticoManager.obtenerCentroPobladoList(ciudadRr);
        } catch (ApplicationException e) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            return null;
        }
    }

    @WebMethod(operationName = "getHhppRepositorioByIdRr")
    @WebResult(name = "idHhppResult")
    public CmtBasicResponse getHhppRepositorioByIdRr(@WebParam(name = "idHhppRr") String idHhppRr) {
        HHPPManager manager = new HHPPManager();
        CmtBasicResponse basicResponse = new CmtBasicResponse();
        try {
            basicResponse.setMessage("Operacion Exitosa");
            basicResponse.setMessageType("I");
            basicResponse.setValue(manager.findByIdRr(idHhppRr));
            return basicResponse;
        } catch (Exception ex) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ ex.getMessage());
            basicResponse.setMessage("Error:".concat(ex.getMessage()));
            basicResponse.setMessageType("E");
            basicResponse.setValue("0");
            return basicResponse;
        }
    }

    @WebMethod(operationName = "createOrUpdateHhppOnRepository")
    @WebResult(name = "CreateOrUpdatehhppResult")
    public CmtBasicResponse createOrUpdateHhppOnRepository(@WebParam(name = "hhppRequest") CmtHhppMglDto hhpp,
            @WebParam(name = "requestAplication") String aplicacion) {
        HHPPManager manager = new HHPPManager();
        CmtBasicResponse basicResponse = new CmtBasicResponse();
        try {
            basicResponse.setMessage("Operacion Exitosa");
            basicResponse.setMessageType("I");
            basicResponse.setValue(manager.updateCreateHhppDb(hhpp, aplicacion).toString().trim());
            return basicResponse;
        } catch (Exception ex) {
           LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ ex.getMessage());
            basicResponse.setMessage("Error:".concat(ex.getMessage()));
            basicResponse.setMessageType("E");
            basicResponse.setValue("0");
            return basicResponse;
        }
    }

    @WebMethod(operationName = "wifiDthViability")
    @WebResult(name = "viabiltyResponse")
    public CmtBasicResponse wifiDthViability(@WebParam(name = "idHhppRr") String idHhppRr) {
        HHPPManager manager = new HHPPManager();
        CmtBasicResponse basicResponse = new CmtBasicResponse();
        try {
            basicResponse.setMessage("Operacion Exitosa");
            basicResponse.setMessageType("I");
            basicResponse.setValue(manager.viavilidadWifiDth(idHhppRr));
            return basicResponse;
        } catch (Exception ex) {
             LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ ex.getMessage());
            basicResponse.setMessage("Error:".concat(ex.getMessage()));
            basicResponse.setMessageType("E");
            basicResponse.setValue("0");
            return basicResponse;
        }
    }

    @WebMethod(operationName = "coverageRepositoryUpdate")
    @WebResult(name = "coverageUpdateResponse")
    public CmtBasicResponse coverageRepositoryUpdate(@WebParam(name = "idHhppRr") String idHhppRr) {
        HHPPManager manager = new HHPPManager();
        CmtBasicResponse basicResponse = new CmtBasicResponse();
        try {
            basicResponse.setMessage("Operacion Exitosa");
            basicResponse.setMessageType("I");
            basicResponse.setValue(manager.coverageRepositoryUpdate(idHhppRr));
            return basicResponse;
        } catch (Exception ex) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ ex.getMessage());
            basicResponse.setMessage("Error:".concat(ex.getMessage()));
            basicResponse.setMessageType("E");
            basicResponse.setValue("0");
            return basicResponse;
        }
    }

    /**
     * Invocar el procedimiento de vialibilidad.Invoca el procedimiento para
 validar la viabilidad de una peticion segun los parametros que se
 encuentran en la variable request.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param request valor con la información a procesar.
     * @return una instancia <code>ResponseValidacionViabilidad</code> con el
     * resultado de la validación.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @WebMethod(operationName = "getViabilidad")
    public ResponseValidacionViabilidad getViabilidad(@WebParam(
            name = "RequestValidacionViabilidad", header = true) RequestValidacionViabilidad request) throws ApplicationException {
        CmtTipoValidacionMglManager manager = new CmtTipoValidacionMglManager();
        return manager.getViabilidad(request);
    }

    /**
     * Obtener listado de parametros dth. Permite obtener el listado por tipo de
     * parametro necesario en la solicitud dth
     *
     * @author Juan David Hernandez
     * @param tipoParametro el tipo de parametro por el cual se va a obtener el
     * listado.
     * @return el listado correspondiente al parametro solicitado.
     *
     */
    @WebMethod(operationName = "obtenerParametrosGestionList")
    public List<ParametrosCalles> obtenerParametrosGestionList(
            @WebParam(name = "tipoParametro") String tipoParametro) {
        try {
            ParametrosCallesManager parametrosCallesManager = new ParametrosCallesManager();
            return parametrosCallesManager.findByTipo(tipoParametro);
        } catch (Exception e) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            return null;
        }
    }

    /**
     * Validacion Matriz Viabilidad.Permite conocer si una solicitud cumple los
 criterios de venta para su creacion.
     *
     * @author Juan David Hernandez
     * @param request request con la informacion necesaria para la validación de
     * la solicitud Dth
     * @param direccion con la que se debe crear la solicitud
     *
     * @return respuesta con el proceso de creacion de la solicitud Dth
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    @WebMethod(operationName = "validarMatrizViabilidadPorProyecto")
    public List<ResponseValidacionViabilidad> validarMatrizViabilidadPorProyecto(
            @WebParam(name = "responseValidacionDireccion") ResponseValidacionDireccion responseValidacionDireccion,
            @WebParam(name = "direccionStr") String direccionStr)
            throws ApplicationException {
        SolicitudManager solicitudMgr = new SolicitudManager();
        return solicitudMgr.validarMatrizViabilidad(
                responseValidacionDireccion,
                direccionStr);
    }
    
        
    @WebMethod(operationName = "thirdGViability")
    @WebResult(name = "thirdGResponse")
    public CmtBasicResponse thirdGViability(
            @WebParam(name = "comunityStr") String comunityStr) {
        HHPPManager manager = new HHPPManager();
        CmtBasicResponse basicResponse=new CmtBasicResponse();
        try {
            basicResponse.setMessage("Operacion Exitosa");
            basicResponse.setMessageType("I");
            basicResponse.setValue(manager.thirdGViability(comunityStr));
            return basicResponse;
                    
        } catch (Exception ex) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ ex.getMessage());
            basicResponse.setMessage("Error:".concat(ex.getMessage()));
            basicResponse.setMessageType("E");
            basicResponse.setValue("0");
            return basicResponse;
        }
    }
    
    
        /**
     * Crea Solicitud Bidireccional. Permite crear una solicitud Bidireccional
     * sobre el repositorio para su posterior gestion
     *
     * @author Johnnatan Ortiz
     * @param request request con la informacion necesaria para la creacion de
     * la solicitud
     * @return respuesta con el proceso de creacion de la solicitud
     *
     */
    @WebMethod(operationName = "crearSolicitud")
    public ResponseCreaSolicitud crearSolicitud(
            @WebParam(name = "request") RequestCreaSolicitud request) {
        try {
            SolicitudManager solicitudManager = new SolicitudManager();
            return solicitudManager.crearSolicitud(request);
        } catch (ApplicationException e) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            ResponseCreaSolicitud response = new ResponseCreaSolicitud();
            response.setTipoRespuesta("E");
            response.setMensaje(e.getMessage());
            response.setIdSolicitud("0");
            return response;
        }
    }
    
     /**
     * obtenerCentroPoblado. Permite obtener un centro poblado
     * apartir del id del mismo.
     *
     * @author Victor Bocanegra
     * @param idCentro id del centro poblado a consultar
     * poblado
     * @return centro poblado de la ciudad
     *
     */
    @WebMethod(operationName = "obtenerCentroPobladoById")
    public ResponseGeograficoPolitico obtenerCentroPobladoByIdCentro(
            @WebParam(name = "idCentro") String idCentro) {
        try {
            GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
            GeograficoPoliticoMgl geograficoPoliticoMgl = 
                    geograficoPoliticoManager.findById(new BigDecimal(idCentro));       
            return geograficoPoliticoManager.
                    parseGeograficoPoliticoMglToResponseGeograficoPolitico(geograficoPoliticoMgl);
        } catch (ApplicationException e) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            return null;
        }
    }
    

}
