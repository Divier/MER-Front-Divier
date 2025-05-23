/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.CMatricesAs400Service.war.restful;

import co.com.claro.app.dtos.AppCancelarAgendaOtRequest;
import co.com.claro.app.dtos.AppConsultarAgendasOtRequest;
import co.com.claro.app.dtos.AppConsultarCapacityOtRequest;
import co.com.claro.app.dtos.AppConsultarFactibilidadTrasladoRequest;
import co.com.claro.app.dtos.AppCrearAgendaOtRequest;
import co.com.claro.app.dtos.AppCrearOtRequest;
import co.com.claro.app.dtos.AppModificarOtRequest;
import co.com.claro.app.dtos.AppResponseCrearOtDto;
import co.com.claro.app.dtos.AppResponseUpdateOtDto;
import co.com.claro.app.dtos.AppResponsesAgendaDto;
import co.com.claro.app.dtos.AppResponsesGetCapacityDto;
import co.com.claro.app.dtos.AppReagendarOtRequest;
import co.com.claro.ejb.mgl.ws.client.updateCaseStatus.EricssonFault;
import co.com.claro.ejb.mgl.ws.client.updateCaseStatus.UpdateCaseStatusResponseTYPE;
import co.com.claro.ejb.mgl.ws.client.updateCaseStatus.UpdateCaseStatusType;
import co.com.claro.mgl.businessmanager.cm.CmtNotificarCambioTcrmMglManager;
import co.com.claro.mgl.dtos.CmtAvisoProgramadoDto;
import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import co.com.claro.mgl.dtos.CmtEstadoHhppDto;
import co.com.claro.mgl.dtos.CmtMarcasHhppDto;
import co.com.claro.mgl.dtos.CmtNotasHhppDto;
import co.com.claro.mgl.dtos.CmtSolicitudDto;
import co.com.claro.mgl.dtos.NodoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.error.ApplicationExceptionCM;
import co.com.claro.mgl.facade.DireccionesValidacionFacadeLocal;
import co.com.claro.mgl.facade.DrDireccionFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.OtCMHhppMglFacadeLocal;
import co.com.claro.mgl.facade.ResulModFacDirMglFacadeLocal;
import co.com.claro.mgl.facade.SolicitudFacadeLocal;
import co.com.claro.mgl.facade.cm.*;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.cm.CmtAvisosProgramadosMgl;
import co.com.claro.mgl.rest.dtos.*;
import co.com.claro.mgl.ws.cm.response.ResponseCreaSolicitudCambioEstrato;
import co.com.telmex.catastro.data.EstadoSolicitud;
import com.sun.jersey.api.client.UniformInterfaceException;
import java.io.IOException;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 *
 * @author rodriguezluim
 */
@Path("address")
@Stateless
public class CmtLocationsRest {

    @EJB
    private SolicitudFacadeLocal solicitudFacadeLocal;
    @EJB
    private HhppMglFacadeLocal hhppMglFacadeLocal;
    @EJB
    private CmtComponenteDireccionesMglFacadeLocal cmtComponenteDireccionesMglFacadeLocal;
    @EJB
    private ICtmNodoMglFacadeLocal ctmNodoMgl;
    @EJB
    private CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMgl;
    @EJB
    private DireccionesValidacionFacadeLocal direccionesValidacionFacadeLocal;
    @EJB
    private DrDireccionFacadeLocal drDireccionFacadeLocal;
    @EJB
    private CmtAvisoProgramadoMglFacadeLocal cmtAvisoProgramadoMglFacadeLocal;
    @EJB
    private CmtNotificarCambioTcrmMglManager cmtNotificarCambioTcrmMglManager;
    @EJB
    private ResulModFacDirMglFacadeLocal resulModFacDirMglFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private OtCMHhppMglFacadeLocal otCMHhppMglFacadeLocal;
    @EJB
    private UsuariosFacadeLocal usuarioFacadeLocal;

    /**
     * valbuenayf 
     * Metodo para consultar la direccion por texto o tabulada
     * @param cmtDireccionDetalladaRequestDto
     * @return
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("consultaDireccion")
    public CmtAddressResponseDto ConsultaDireccion(CmtDireccionDetalladaRequestDto cmtDireccionDetalladaRequestDto) {
        return cmtDireccionDetalleMgl.consultarDireccion(cmtDireccionDetalladaRequestDto);
    }
    
    /**
     * valbuenayf Metodo para consultar la direccion por texto o tabulada
     *
     * @param cmtDireccionRequestDto
     * @return
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("consultaDireccionGeneral")
    public CmtAddressGeneralResponseDto ConsultaDireccionGeneral(CmtDireccionRequestDto cmtDireccionRequestDto) {
        return cmtDireccionDetalleMgl.ConsultaDireccionGeneral(cmtDireccionRequestDto);
    }
    
   /**
     * Obtener el estado de una solicitud por medio id de solicitud. Servicio
     * rest que permite buscar por medio del id solicitud
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param cmtSolicitudDtoRequest objeto de tipo solicitud solo requiere
     * setear el idSolicitud para su consulta
     * @return Retorna el estado de la solicitudo consultada
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("buscarSolicitudPorIdSolicitud")
    public EstadoSolicitud obtenerSolicitudBySolId(CmtSolicitudDto cmtSolicitudDtoRequest) {
        
        if (cmtSolicitudDtoRequest.getIdSolicitudRequest() != null) {
        } else {
            EstadoSolicitud estado = new EstadoSolicitud();
            estado.setMessageType("ERROR");
            estado.setMessage("Debe ingresa el id de la solicitud que desea consultar");
        }

        if (cmtSolicitudDtoRequest.getTipoSolicitud() != null && !cmtSolicitudDtoRequest.getTipoSolicitud().isEmpty()) {
            if (!cmtSolicitudDtoRequest.getTipoSolicitud().equalsIgnoreCase("CCMM") && !cmtSolicitudDtoRequest.getTipoSolicitud().equalsIgnoreCase("HHPP")) {
                EstadoSolicitud estado = new EstadoSolicitud();
                estado.setMessageType("ERROR");
                estado.setMessage("Debe ingresa el campo tipoSolicitud correcto: HHPP ó CCMM");
                return estado;
            }

        } else {
            EstadoSolicitud estado = new EstadoSolicitud();
            estado.setMessageType("ERROR");
            estado.setMessage("Debe ingresa el campo tipoSolicitud que desea consultar: HHPP ó CCMM");
            return estado;
        }

        Solicitud solicitud = new Solicitud();
        solicitud.setIdSolicitud(cmtSolicitudDtoRequest.getIdSolicitudRequest());
        solicitud.setTipoSol(cmtSolicitudDtoRequest.getTipoSolicitud());
        return solicitudFacadeLocal.findBySolicitud(solicitud);
    }

    /**
     * Obtener el estado de una solicitud por medio id de TCRM. Servicio rest
     * que permite buscar por medio del id de TCRM
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param cmtSolicitudDtoRequest objeto de tipo solicitud solo requiere
     * setear el idSolicitud para su consulta
     * @return Retorna el estado de la solicitudo consultada
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("buscarSolicitudPorIdCasoTcrm")
    public EstadoSolicitud obtenerSolicitudByTcrmId(CmtSolicitudDto cmtSolicitudDtoRequest) {
        Solicitud solicitud = new Solicitud();
        solicitud.setIdCasoTcrm(cmtSolicitudDtoRequest.getIdTcrmRequest());
        return solicitudFacadeLocal.findByIdCasoTcrm(solicitud);
    }

    /**
     * Crear un aviso programado para un HHPP.Este metodo permite crear un
 aviso para cuando un HHPP cambie de estado a disponible genere un aviso a
 TCRM
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param avisoRequest
     * @return Una respuesta basica para evidenciar si pudo o no realizar la
     * tarea
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("crearAvisoProgramadoHhpp")
    public CmtDefaultBasicResponse crearAvisoProgramadoHhpp(CmtAvisoProgramadoDto avisoRequest) {
                
        if(avisoRequest != null){
            CmtAvisosProgramadosMgl capm = new CmtAvisosProgramadosMgl();
            capm.setCasoTcrmId(avisoRequest.getIdCasoTcrm());
            capm.setHhppId(avisoRequest.getIdHhpp());
            capm.setTecnologiaSubedificioId(avisoRequest.getIdTecnologiaSubEdificio());
            capm.setTecnologia(avisoRequest.getTecnologia());
            CmtDefaultBasicResponse cmtDefaultBasicResponse = cmtAvisoProgramadoMglFacadeLocal.crearAvisoProgramadoHhpp(capm);
            return cmtDefaultBasicResponse;
        }else{
            CmtDefaultBasicResponse cmtDefaultBasicResponse = new CmtAddressResponseDto();
            cmtDefaultBasicResponse.setMessageType("E");
            cmtDefaultBasicResponse.setMessage("Los datos obligatorios para la creacion del aviso programado no estan completos.");
            return cmtDefaultBasicResponse;
        }
    }
	
    /**
     * valbuenayf Metodo para consultar la direccion por texto o tabulada
     *
     * @param nodo codigo del nodo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationExceptionCM
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("getNodeDataByCod")
    public NodoMglDto getNodeDataByCod(NodoDto nodo) throws ApplicationExceptionCM {
        return ctmNodoMgl.getNodeDataByCod(nodo.getCodigo());
    }

    /**
     * Cambiar el estado de un HHPP.Permite realizar el cambio de estado de
 hhpp teniendo en cuenta reglas para los cambios de estado
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param cmtEstadoHhppDtoRequest
     * @return Retorna el objeto estado hhpp que quedo asignado al hhpp
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("cambiarEstadoHhpp")
    public CmtDefaultBasicResponse cambioEstadoHhpp(CmtEstadoHhppDto cmtEstadoHhppDtoRequest) {
        HhppMgl hhppRequest = new HhppMgl();
        hhppRequest.setHhpId(cmtEstadoHhppDtoRequest.getIdHhppRequest());
        return hhppMglFacadeLocal.cambioEstadoHhpp(hhppRequest, cmtEstadoHhppDtoRequest);
    }

    /**
     * Agregar marcas a un HHPP existente.Permite agregar uno o varias marcas a
 un hhpp existente en la plataforma
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param cmtMarcasHhppDtoRequest 
     * @return Una respuesta basica para evidenciar si pudo o no realizar la
     * tarea
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("agregarMarcasHhpp")
    public CmtDefaultBasicResponse agregarMarcasHhpp(CmtMarcasHhppDto cmtMarcasHhppDtoRequest) {
        
        if (cmtMarcasHhppDtoRequest != null) {
            if(cmtMarcasHhppDtoRequest.getIdHhppRequest() != null 
                    && !cmtMarcasHhppDtoRequest.getIdHhppRequest().equals(BigDecimal.ZERO)){

                if (cmtMarcasHhppDtoRequest.getUser() != null && !cmtMarcasHhppDtoRequest.getUser().isEmpty()) {
                    HhppMgl hhppRequest = new HhppMgl();
                    hhppRequest.setHhpId(cmtMarcasHhppDtoRequest.getIdHhppRequest());
                    return hhppMglFacadeLocal.agregarMarcasHhpp(hhppRequest, cmtMarcasHhppDtoRequest.getListaMarcasMgls(), cmtMarcasHhppDtoRequest.getUser());
                } else {
                    CmtDefaultBasicResponse response = new CmtDefaultBasicResponse();
                    response.setMessageType("E");
                    response.setMessage("Debe ingresar un usuario (User) para consumir el servicio");
                    return response;
                }
               
            } else {
                CmtDefaultBasicResponse response = new CmtDefaultBasicResponse();
                response.setMessageType("E");
                response.setMessage("Debe ingresar el idHhppRequest al cual desea agregarle las marcas");
                return response;
            }
            
        } else {
            CmtDefaultBasicResponse response = new CmtDefaultBasicResponse();
            response.setMessageType("E");
            response.setMessage("Debe construir una peticion para consumir el servicio.");
            return response;
        }

    }
    
    /**
     * eliminar marcas a un HHPP existente.Permite eliminar uno o varias marcas a
 un hhpp existente en la plataforma
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param cmtMarcasHhppDtoRequest 
     * @return Una respuesta basica para evidenciar si pudo o no realizar la
     * tarea
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("eliminarMarcasHhpp")
    public CmtDefaultBasicResponse eliminarMarcasHhpp(CmtMarcasHhppDto cmtMarcasHhppDtoRequest) {
        
        if (cmtMarcasHhppDtoRequest != null) {
            if (cmtMarcasHhppDtoRequest.getIdHhppRequest() != null
                    && !cmtMarcasHhppDtoRequest.getIdHhppRequest().equals(BigDecimal.ZERO)) {

                if (cmtMarcasHhppDtoRequest.getUser() != null && !cmtMarcasHhppDtoRequest.getUser().isEmpty()) {
                    HhppMgl hhppRequest = new HhppMgl();
                    hhppRequest.setHhpId(cmtMarcasHhppDtoRequest.getIdHhppRequest());
                    return hhppMglFacadeLocal.eliminarMarcasHhpp(hhppRequest, cmtMarcasHhppDtoRequest.getListaMarcasMgls());
                } else {
                    CmtDefaultBasicResponse response = new CmtDefaultBasicResponse();
                    response.setMessageType("E");
                    response.setMessage("Debe ingresar un usuario (User) para consumir el servicio");
                    return response;
                }

            } else {
                CmtDefaultBasicResponse response = new CmtDefaultBasicResponse();
                response.setMessageType("E");
                response.setMessage("Debe construir una petición para consumir el servicio");
                return response;
            }
        } else {
            CmtDefaultBasicResponse response = new CmtDefaultBasicResponse();
            response.setMessageType("E");
            response.setMessage("Debe ingresar el idHhppRequest al cual desea eliminar las marcas");
            return response;
        }

       
    }

    /**
     * Agregar notas a un HHPP existente.Permite agregar uno o varias notas a
 un hhpp existente en la plataforma
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param cmtNotasHhppDtoRequest 
     * @return Una respuesta basica para evidenciar si pudo o no realizar la
     * tarea
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("agregarNotasHhpp")
    public CmtDefaultBasicResponse agregarNotasHhpp(CmtNotasHhppDto cmtNotasHhppDtoRequest) {
        HhppMgl hhppRequest = new HhppMgl();
        hhppRequest.setHhpId(cmtNotasHhppDtoRequest.getIdHhppRequest());
        return hhppMglFacadeLocal.agregarNotasHhpp(hhppRequest, cmtNotasHhppDtoRequest.getListaNotasHhpp());
    }
    /**
     * Obtine todos los niveles para una direccion.Permite obtener todos los
 niveles necesarios para los diferentes niveles de una direccion segun el
 tipo de red
     *
     * @param cmtConfiguracionDto
     * @author Victor Bocanegra
     * @return Configuracion de los niveles de la direccion segun el tipo de red
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("putObtenerConfiguracionComponenteDireccion")
    public CmtConfigurationAddressComponentDto getObtenerConfiguracionComponenteDireccion(
           CmtConfiguracionDto cmtConfiguracionDto) throws  ApplicationException {
        return cmtComponenteDireccionesMglFacadeLocal.getConfiguracionComponente(cmtConfiguracionDto);
    }

    /**
     * Construye la direccion para una Solicitud. Permite crear una dirección
     * detallada, campo a campo para luego ser usada en una solicitud.
     *
     * @param cmtRequestConstruccionDireccionDto request con la información necesaria
     *                                           para la creación de la solicitud
     * @return respuesta con el proceso de creación de la solicitud
     * @author Victor Bocanegra
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("putConstruirDireccionHhpp")
    public CmtResponseConstruccionDireccionDto putConstruirDireccionHhpp(
            CmtRequestConstruccionDireccionDto cmtRequestConstruccionDireccionDto) throws ApplicationExceptionCM {
        return drDireccionFacadeLocal.construirDireccionSolicitudHhpp(cmtRequestConstruccionDireccionDto);
    }

    /**
     * Obtiene la lista de barrios Autor: Victor Bocanegra
     *
     * @param cmtRequestConstruccionDireccionDto
     * @return List<CmtAddressSuggestedDto>
     * @throws ApplicationExceptionCM
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("putObtenerBarrioListHhpp")
    public CmtSuggestedNeighborhoodsDto putObtenerBarrioListHhpp(
            CmtRequestConstruccionDireccionDto cmtRequestConstruccionDireccionDto) throws ApplicationExceptionCM {
        return direccionesValidacionFacadeLocal.obtenerBarrioSugeridoHhpp(cmtRequestConstruccionDireccionDto);
    }
    /**
     * Valida una direccion.Realiza las operaciones de validacion en el
 repositorio para verificar existencia.Incluye la validacion de direccion
 nueva y antigua.
     *
     * @author Victor Bocanegra
     * @param cmtRequestConstruccionDireccionDto
     * @return CmtCityEntityDto resultado de la validacion direccion.
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("putValidarDireccionHhpp")
    public CmtCityEntityDto putValidarDireccionHhpp(
            CmtRequestConstruccionDireccionDto cmtRequestConstruccionDireccionDto) {
        return direccionesValidacionFacadeLocal.validaDireccionHhpp(cmtRequestConstruccionDireccionDto);
    }
    /**
     * Crea Solicitud. Permite crear una solicitud sobre el repositorio para su
     * posterior gestion
     *
     * @author Victor Bocanegra
     * @param cmtCrearSolicitudInspiraDto con la que se debe crear la solicitud
     * @return CmtResponseCreaSolicitudDto respuesta con el proceso de creacion
     * de la solicitud
     *
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("putCrearSolicitudInspira")
    public CmtResponseCreaSolicitudDto putCrearSolicitudInspira(
            CmtCrearSolicitudInspiraDto cmtCrearSolicitudInspiraDto) {
        return solicitudFacadeLocal.crearGestionarSolicitudinspira(cmtCrearSolicitudInspiraDto);
    }
    
    /**
     * valbuenayf metodo para crear una solicitud de cambio de estrato TRCM
     *
     * @param request
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("crearSolitudCambioEstratoTcrm")
    public ResponseCreaSolicitudCambioEstrato crearSolitudCambioEstratoTcrm(
            RequestSolicitudCambioEstratoDto request) throws ApplicationException {
        return solicitudFacadeLocal.crearSolitudCambioEstratoTcrm(request);
    }
  
    /**
     * valbuenayf metodo para consimir el servicio de TRCM cambio actualizacion
     * de estrato TODO este servicio es TEMPORAL para las pruebas de integracion
     *
     * @param request
     * @return
     * @throws ApplicationExceptionCM
     * @throws EricssonFault
     */
    @Deprecated
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("gestionarSolicitudCambioEstratoTcrmIntegracion")
    public UpdateCaseStatusResponseTYPE gestionarSolicitudCambioEstratoTcrmIntegracion(
            UpdateCaseStatusType request) throws ApplicationExceptionCM, EricssonFault {
        return cmtNotificarCambioTcrmMglManager.enviarNotificacionCambioTcrm(request);
    }
    
    
    
    /**
     * Firma: villamilc metodo para consultar una direcci&oacute;n por coordenadas.Lógica: Juan David Hernandez
     * @param request
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("getDireccionByCoordenadas")
    public CmtAddressGeneralResponseDto getDireccionByCoordenadas(
            CmtRequestHhppByCoordinatesDto request) throws ApplicationException {
        
        CmtAddressGeneralResponseDto cmtAddressGeneralResponseDto = 
                new CmtAddressGeneralResponseDto();
        cmtAddressGeneralResponseDto.setMessage("NotImplementedException");
        cmtAddressGeneralResponseDto.setMessageType("E");
        
        return cmtDireccionDetalleMgl.findDireccionByCoordenadas(request);
    }
    
        /**
     * Crea Solicitud.Permite crear una solicitud sobre el repositorio para su
 posterior gestion
     *
     * @author cardenaslb
     * @param cmtRequestConsByTokenDto
     * @return CmtResponseCreaSolicitudDto respuesta con el proceso de creacion
     * de la solicitud
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("findDirBytoken")
    public CmtResponseDirByToken findDirBytoken(CmtRequestConsByTokenDto cmtRequestConsByTokenDto) 
            throws ApplicationException {
        return resulModFacDirMglFacadeLocal.findDirByToken(cmtRequestConsByTokenDto);
    }
    
    
    /**
     * Metodo para la consulta por id de un geografico politico o un grupo de
     * geografico politicos
     *
     * @author Victor Bocanegra
     * @param mglRequestGeoPoliticoDto
     * @return CmtResponseCreaSolicitudDto respuesta con el proceso de creacion
     * de la solicitud
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("getListGeoPolByGroupId")
    public MglResponseGeoPoliticoDto getListGeoPolByGroupId(MglRequestGeoPoliticoDto mglRequestGeoPoliticoDto)
            throws ApplicationException {
        return geograficoPoliticoMglFacadeLocal.getListGeoPolByGroupId(mglRequestGeoPoliticoDto);
    }
    
    /**
     * Metodo para consultar la indicacion de una direccion
     *
     * @author bocanegravm
     * @param indicacionesRequestDto
     * @return CmtIndicacionesResponseDto respuesta con el proceso de consulta
     * de la solicitud
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("consultarIndicaciones")
    public CmtIndicacionesResponseDto consultarIndicaciones(CmtIndicacionesRequestDto indicacionesRequestDto)
            throws ApplicationException {
        return cmtDireccionDetalleMgl.consultarIndicaciones(indicacionesRequestDto);
    }

    /**
     * Metodo de creacion de una OT de CCMM/HHPP desde el sistema APP
     *
     * @author Divier Casas
     * @version 1.0 revision
     * @param appCrearOtRequest
     * @return AppDefaultResponseOtDto
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("createOrderSupport")
    public AppResponseCrearOtDto createOrderSupport(AppCrearOtRequest appCrearOtRequest)
            throws ApplicationException {
        return otCMHhppMglFacadeLocal.createOrderSupport(appCrearOtRequest);
    }
    
    /**
     * Creacion de una OT de CCMM/HHPP desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param appCrearOtRequest
     * @return AppDefaultResponseOtDto
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("crearOtCcmmHhppApp")
    public AppResponseCrearOtDto crearOtCcmmHhppApp(AppCrearOtRequest appCrearOtRequest)
            throws ApplicationException {
        return otCMHhppMglFacadeLocal.crearOtCcmmHhppApp(appCrearOtRequest);
    }

    /**
     * Modificacion de una OT de CCMM/HHPP desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param appModificarOtRequest
     * @return AppResponseUpdateOtDto
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("modificarOTCcmmHhppApp")
    public AppResponseUpdateOtDto modificarOTDireccionApp(AppModificarOtRequest appModificarOtRequest)
            throws ApplicationException {
        return otCMHhppMglFacadeLocal.modificarOTCcmmHhppApp(appModificarOtRequest);
    }

    /**
     * Consulta del capacity en OFSC de una OT desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param consultarCapacityOtDirRequest
     * @return AppResponsesGetCapacityDto
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("getCapacidadOtCcmmHhppApp")
    public AppResponsesGetCapacityDto getCapacidadOtCcmmHhppApp(AppConsultarCapacityOtRequest consultarCapacityOtRequest) throws IOException {
        return otCMHhppMglFacadeLocal.getCapacidadOtCcmmHhppApp(consultarCapacityOtRequest);
    }

    /**
     * Metodo para crear una agenda de Orden desde el sistema APP
     *
     * @author Divier Casas
     * @version 1.0 revision .
     * @param crearAgendaOtRequest
     * @return AppResponsesAgendaDto
     * @throws java.io.IOException
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("agendarOtCcmmHhppTMApp")
    public AppResponsesAgendaDto agendarOtCcmmHhppTMApp(AppCrearAgendaOtRequest crearAgendaOtRequest)
            throws UniformInterfaceException, IOException, ApplicationException {
        return otCMHhppMglFacadeLocal.agendarOtCcmmHhppTMApp(crearAgendaOtRequest);
    }
    
    /**
     * Metodo para crear una agenda de Orden desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param crearAgendaOtDirRequest
     * @return AppResponsesAgendaDto
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("agendarOtCcmmHhppApp")
    public AppResponsesAgendaDto agendarOtCcmmHhppApp(AppCrearAgendaOtRequest crearAgendaOtRequest)
            throws UniformInterfaceException, IOException, ApplicationException {
        return otCMHhppMglFacadeLocal.agendarOtCcmmHhppApp(crearAgendaOtRequest);
    }

    /**
     * Metodo para reagendar una agenda de Orden desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param reagendarOtRequest
     * @return AppResponsesAgendaDto
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("reagendarOtCcmmHhppApp")
    public AppResponsesAgendaDto reagendarOtCcmmHhppApp(AppReagendarOtRequest reagendarOtRequest)
            throws ApplicationException {
        return otCMHhppMglFacadeLocal.reagendarOtCcmmHhppApp(reagendarOtRequest);
    }

    /**
     * Metodo para cancelar una agenda de Orden desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param cancelarAgeRequest
     * @return AppResponsesAgendaDto
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("cancelarAgendaOtCcmmHhppApp")
    public AppResponsesAgendaDto cancelarAgendaOtCcmmHhppApp(AppCancelarAgendaOtRequest cancelarAgeRequest)
            throws ApplicationException {
        return otCMHhppMglFacadeLocal.cancelarAgendaOtCcmmHhppApp(cancelarAgeRequest);
    }

    /**
     * Metodo para consultar las agendas by id Enlace desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param consultarAgendasOtRequest
     * @return AppResponsesAgendaDto
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("consultarAgendasOtCcmmHhppApp")
    public AppResponsesAgendaDto consultarAgendasOtCcmmHhppApp(AppConsultarAgendasOtRequest consultarAgendasOtRequest)
            throws ApplicationException {
        return otCMHhppMglFacadeLocal.consultarAgendasOtCcmmHhppApp(consultarAgendasOtRequest);
    }

    /**
     * Metodo para consultar la factibilidad de un traslado de direccion desde
     * el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param trasladoRequest
     * @return CmtDefaultBasicResponse
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("consultarFactibilidadTrasladoApp")
    public CmtDefaultBasicResponse consultarFactibilidadTrasladoApp(AppConsultarFactibilidadTrasladoRequest trasladoRequest)
            throws ApplicationException {
        return otCMHhppMglFacadeLocal.consultarFactibilidadTrasladoApp(trasladoRequest);
    }
    
        /**
     * Juan David Hernandez Metodo para consultar la direccion tabulada exacta sobre MER
     *
     * @param cmtDireccionRequestDto
     * @return
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("consultaDireccionExactaTabulada")
    public CmtAddressGeneralResponseDto consultaDireccionExactaTabulada(CmtDireccionRequestDto cmtDireccionRequestDto) {
        return cmtDireccionDetalleMgl.consultaDireccionExactaTabulada(cmtDireccionRequestDto);
    }

    /**
     * Metodo para consultar el id de MER a partir del id de RR
     *
     * @author Johan Forero
     * @version 1.0 revision .
     * @param ConsultaIdMerRequestDto
     * @return ConsultaIdMerResponseDto
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("consultaIdMER")
    public ConsultaIdMerResponseDto consultaIdDireccionMER(ConsultaIdMerRequestDto consultaIdMerRequestDto) {
        return cmtDireccionDetalleMgl.consultaIdDireccionMER(consultaIdMerRequestDto);
    }

    /**
    * Manuel Hernández Rivas
    * Metodo para consultar la direccion por texto o tabulada
    * @param accountRequestDto
    * @return CmtAddressResponseDto*/
   @PUT
   @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("getNodeIDsByAccount")
   public CmtAddressResponseDto getNodeIDsByAccount(getNodeIDsByAccountRequestDto accountRequestDto) {
       return cmtDireccionDetalleMgl.getNodeIDsByAccount(accountRequestDto);
   }

    /**
     * Metodo para consultar el detalle de la direccion tecnica, tecnologias habilitadas
     *
     * @author Yasser Leon
     * @version 1.0 revision .
     * @param consultaFactibilidadRequestDto
     * @return ConsultaFactibilidadResponseDto
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("consultaFactibilidad")
    public ConsultaFactibilidadResponseDto consultaFactibilidad(ConsultaFactibilidadRequestDto consultaFactibilidadRequestDto) {
        return cmtDireccionDetalleMgl.consultaFactibilidad(consultaFactibilidadRequestDto);
    }

    /**
     * Metodo para consultar el token de seguridad a partir del usuario
     *
     * @author Yasser Leon
     * @version 1.0 revision .
     * @param tokenUsuarioRequestDto
     * @return TokenUsuarioDto
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("consultarTokenUsuario")
    public TokenUsuarioResponseDto consultarTokenUsuario(TokenUsuarioRequestDto tokenUsuarioRequestDto) {
        return usuarioFacadeLocal.consultarTokenUsuario(tokenUsuarioRequestDto);
    }
	
	/**
     * Consume el procedimiento prc_consulta_tecnodo
     *
     * @author Leidy Montero
      * @param consultarNodosPorRecursoRequest
     * @return CmtAddressResponseDto detalle de la direccion
     *
     */
	 
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("consultarNodosPorRecurso")
    public CmtAddressResponseDto consultarNodosPorRecurso(ConsultarNodosPorRecursoRequest consultarNodosPorRecursoRequest) {
        return cmtDireccionDetalleMgl.consultarNodosPorRecurso(consultarNodosPorRecursoRequest);
    }
}
