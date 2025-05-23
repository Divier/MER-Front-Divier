/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.agendamiento;

import co.com.claro.app.dtos.AppCrearAgendaOtPropertyRequest;
import co.com.claro.mgl.businessmanager.address.AgendamientoHhppMglManager;
import co.com.claro.mgl.businessmanager.address.CmtNotasOtHhppMglManager;
import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.OtHhppMglManager;
import co.com.claro.mgl.businessmanager.address.OtHhppTecnologiaMglManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.businessmanager.address.RequestResponsesAgeMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtAliadosMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaExtMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtExtendidaTecnologiaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtExtendidaTipoTrabajoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTipoBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.OnyxOtCmDirManager;
import co.com.claro.mgl.client.https.ConstansClient;
import co.com.claro.mgl.client.https.ConsumoGenerico;
import co.com.claro.mgl.dtos.CapacidadAgendaDto;
import co.com.claro.mgl.dtos.CmtOnyxResponseDto;
import co.com.claro.mgl.ejb.wsclient.rest.cm.EnumeratorServiceName;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientAgendamiento;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtNotasOtHhppMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccion;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.OnyxOtCmDir;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.OtHhppTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.RequestResponsesAgeMgl;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendamientoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtAliados;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTipoTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.util.cm.CmtAgendamientoMglConstantes;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.claro.mgl.ws.utils.JSONUtils;
import co.com.claro.mgw.agenda.actualizar.MgwTypeActualizarRequestElement;
import co.com.claro.mgw.agenda.actualizar.TypeAppointmentElementUp;
import co.com.claro.mgw.agenda.actualizar.TypeCommandElementUp;
import co.com.claro.mgw.agenda.actualizar.TypeCommandsArrayUp;
import co.com.claro.mgw.agenda.actualizar.TypeDataElementUp;
import co.com.claro.mgw.agenda.actualizar.TypePropertiesArrayUp;
import co.com.claro.mgw.agenda.actualizar.TypePropertyElementUp;
import co.com.claro.mgw.agenda.agendar.crear.ResourcePreferences;
import co.com.claro.mgw.agenda.agendar.crear.TypeAppointmentElementCr;
import co.com.claro.mgw.agenda.agendar.crear.TypeCommandElementCr;
import co.com.claro.mgw.agenda.agendar.crear.TypeCommandsArrayCr;
import co.com.claro.mgw.agenda.agendar.crear.TypeDataElementCr;
import co.com.claro.mgw.agenda.agendar.crear.TypeItemElementCr;
import co.com.claro.mgw.agenda.agendar.crear.TypePropertiesArrayCr;
import co.com.claro.mgw.agenda.agendar.crear.TypePropertyElementCr;
import co.com.claro.mgw.agenda.capacidad.ActivityFieldElement;
import co.com.claro.mgw.agenda.capacidad.CapacityElement;
import co.com.claro.mgw.agenda.reagendar.TypeCommandElement;
import co.com.claro.mgw.agenda.reagendar.TypePropertyElement;
import co.com.claro.mgw.agenda.shareddata.InfoOrdenAct;
import co.com.claro.mgw.agenda.shareddata.MgwHeadElement;
import co.com.claro.mgw.agenda.shareddata.MgwTypeActualizarResponseElement;
import co.com.claro.mgw.agenda.shareddata.MgwTypeAgendarRequestElement;
import co.com.claro.mgw.agenda.shareddata.MgwTypeAgendarResponseElement;
import co.com.claro.mgw.agenda.shareddata.MgwTypeCancelRequestElement;
import co.com.claro.mgw.agenda.shareddata.MgwTypeCancelResponseElement;
import co.com.claro.mgw.agenda.shareddata.MgwTypeCapacityRequestElement;
import co.com.claro.mgw.agenda.shareddata.MgwTypeCapacityResponseElement;
import co.com.claro.mgw.agenda.shareddata.MgwTypeReAgendarRequestElement;
import co.com.claro.mgw.agenda.shareddata.MgwTypeReAgendarResponseElement;
import co.com.claro.mgw.agenda.util.GetResourcesResponses;
import co.com.claro.notification.putmessage.Attach;
import co.com.claro.notification.putmessage.HeaderRequest;
import co.com.claro.notification.putmessage.Message;
import co.com.claro.notification.putmessage.MessageBox;
import co.com.claro.notification.putmessage.MessageBox1;
import co.com.claro.notification.putmessage.RequestNotificationMail;
import co.com.claro.notification.putmessage.ResponseNotificationMail;
import co.com.claro.ofscCore.findMatchingResources.Activity;
import co.com.claro.ofscCore.findMatchingResources.ApiFindTecnicosRequest;
import co.com.claro.ofscCore.findMatchingResources.ApiFindTecnicosResponse;
import co.com.claro.ofscCore.findMatchingResources.Criteria;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Orlando Velasquez
 */
public class AgendamientoHhppWorkForceMglManager {
    private static final Logger LOGGER = LogManager.getLogger(AgendamientoHhppWorkForceMglManager.class);

    private final AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
    private final CmtDireccionDetalleMglManager direccionDetalleManager
            = new CmtDireccionDetalleMglManager();
    private String letraOrigen = "";
    private final ParametrosMglManager parametrosMglManager;
    private final RequestResponsesAgeMglManager requestResponsesAgeMglManager;
    private final RestClientAgendamiento clientAgendamiento;
    private String BASE_URI_RES_ETA;
    private String userPwdAutorization;
    private final String ERROR_CAPACIDAD = "Error consultando la capacidad";
    private final String ERROR_REAGENDA = "";
    private final String ERROR_ACTUALIZA_AGENDA = "Error Actualizando la agenda";
    private final String ERROR_CANCELAR = "No se pudo cancelar el agendamiento";
    private final String BASE_URI;
    private final String ERROR_AGENDA = "Error realizando el agendamiento por causa: %s";
    private final CmtExtendidaTipoTrabajoMglManager extendidaTipoTrabajoMglManager = new CmtExtendidaTipoTrabajoMglManager();
    private final CmtExtendidaTecnologiaMglManager extendidaTecnologiaMglManager
            = new CmtExtendidaTecnologiaMglManager();
    private final OtHhppTecnologiaMglManager otHhppTecnologiaMglManager = new OtHhppTecnologiaMglManager();
    private final CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public AgendamientoHhppWorkForceMglManager() throws ApplicationException {

        parametrosMglManager = new ParametrosMglManager();
        requestResponsesAgeMglManager = new RequestResponsesAgeMglManager();
        BASE_URI = parametrosMglManager.findByAcronimo(
                co.com.telmex.catastro.services.util.Constant.BASE_URI_REST_AGENDA)
                .iterator().next().getParValor();
          clientAgendamiento = new RestClientAgendamiento(BASE_URI);

    }

    /**
     * Metodo para construir el Appt_number para enviar a WorkForce en el caso
     * de los Hhpp debe empezar con 2 para identificar que es una OT de Hhpp
     *
     * @param orden orden de trabajo de un Hhpp
     * @return boolean disponibilidad de las tecnologias
     */
    public String armarNumeroOtOfscHhpp(OtHhppMgl orden) {

        String numeroArmado = "";
        String secuencia = "";
        String numOrden = "";
        int maximo = 0;
        long tamanio;
        TipoOtHhppMgl tipoOtHhppMgl = orden.getTipoOtHhppId();
        String subtipoOfsc = null;
        if(tipoOtHhppMgl != null && tipoOtHhppMgl.getSubTipoOrdenOFSC() != null){
           subtipoOfsc = tipoOtHhppMgl.getSubTipoOrdenOFSC().getCodigoBasica();
        }
        
        try {

            tamanio = manager.cantidadAgendasPorOtHhpp(orden, subtipoOfsc);

            if (tamanio == 0) {
                maximo++;
            } else {
                maximo = manager.selectMaximoSecXOt(orden.getOtHhppId());
                maximo++;
            }

            if (maximo > 0) {
                String numFi = String.valueOf(maximo);
                if (numFi.length() == 2) {
                    secuencia = "0" + numFi;
                } else {
                    secuencia = "00" + numFi;
                }

                String numOt = orden.getOtHhppId().toString();
                switch (numOt.length()) {
                    case 12:
                        numOrden = numOt;
                        break;
                    case 11:
                        numOrden = "2" + numOt;
                        break;
                    case 10:
                        numOrden = "20" + numOt;
                        break;
                    case 9:
                        numOrden = "200" + numOt;
                        break;
                    case 8:
                        numOrden = "2000" + numOt;
                        break;
                    case 7:
                        numOrden = "20000" + numOt;
                        break;
                    case 6:
                        numOrden = "200000" + numOt;
                        break;
                    case 5:
                        numOrden = "2000000" + numOt;
                        break;
                    case 4:
                        numOrden = "20000000" + numOt;
                        break;
                    case 3:
                        numOrden = "200000000" + numOt;
                        break;
                    case 2:
                        numOrden = "2000000000" + numOt;
                        break;
                    case 1:
                        numOrden = "20000000000" + numOt;
                        break;
                    default:
                        break;
                }
            }

            numeroArmado = numOrden + secuencia;

        } catch (ApplicationException e) {
            LOGGER.error("Error en armarNumeroOtOfSchHhpp. " +e.getMessage(), e);  
        }

        return numeroArmado;

    }

    /**
     * Metodo para consultar la disponibilidad en el agendamiento
     *
     * @param otDireccion {@link OtHhppMgl} ot para la cual se busca la
     * capacidad
     * @param usuario {@link UsuariosServicesDTO} usuario de sesion
     * @param numeroOtOfsc {@link String} numero de la ot en OFSC
     * @param nodoMgl {@link NodoMgl} nodo para la peticion
     * @return {@link List}&lt{@link CapacidadAgendaDto}> Lista con la capacidad
     * existente
     * @throws ApplicationException Excepci&oacute;n lanzada al realizar el
     * consumo del ws
     */
    public List<CapacidadAgendaDto> getCapacidadOtDireccion(
            OtHhppMgl otDireccion, UsuariosServicesDTO usuario,
            String numeroOtOfsc, NodoMgl nodoMgl) throws ApplicationException {
        try {

            String numeroarmado;
            String nombreRed = "";
            if (numeroOtOfsc == null || numeroOtOfsc.isEmpty()) {
                numeroarmado = armarNumeroOtOfscHhpp(otDireccion);
            } else {
                numeroarmado = numeroOtOfsc;
            }

            String subTipoWork;
            int diasAmostrar;

            CmtExtendidaTecnologiaMgl mglExtendidaTecnologia = null;

            
            diasAmostrar = otDireccion.getTipoOtHhppId().getDiasAMostrarAgenda();

            MgwTypeCapacityRequestElement request = new MgwTypeCapacityRequestElement();

            request.setAppt_number(numeroarmado);
            
            if (diasAmostrar < 7) {
                diasAmostrar = 7;
            }

            request.setDate(sumarDiasAFecha(new Date(), diasAmostrar));
            request.setAddress_id(null);

            if (usuario.getCedula() != null) {
                request.setDocument_id(Integer.parseInt(usuario.getCedula()));
            }

            String location = "";
            String tipoWork = otDireccion.getTipoOtHhppId().getTipoTrabajoOFSC() != null ? otDireccion.getTipoOtHhppId().getTipoTrabajoOFSC().getAbreviatura().trim()  : "";
            subTipoWork = otDireccion.getTipoOtHhppId().getSubTipoOrdenOFSC() != null ? otDireccion.getTipoOtHhppId().getSubTipoOrdenOFSC().getAbreviatura().trim() : "";
            String codNodo = "NA";
            String comNodo = "";
            CmtComunidadRr comunidadRr = null;

            
            if (nodoMgl != null) {
                mglExtendidaTecnologia = extendidaTecnologiaMglManager.
                        findBytipoTecnologiaObj(nodoMgl.getNodTipo());
                    codNodo = nodoMgl.getNodCodigo();
                    comNodo = nodoMgl.getComId() != null ? nodoMgl.getComId().getCodigoRr(): "";
                    comunidadRr= nodoMgl.getComId();
            }
             
            if (mglExtendidaTecnologia != null && mglExtendidaTecnologia.getIdExtTec() != null) {
                nombreRed = mglExtendidaTecnologia.getNomTecnologiaOFSC().getAbreviatura();
            }

                       
            /*obtenemos la extendida de la cual se extra el location de la combinacion de 
            comunidad vs tipoOt */
             List<CmtExtendidaTipoTrabajoMgl> extendidaTipoTrabajoMgl = null;
             CmtExtendidaTipoTrabajoMgl extendida = null;
            if (otDireccion != null && otDireccion.getTipoOtHhppId() != null
                    && otDireccion.getTipoOtHhppId().getTipoTrabajoOFSC() != null && comunidadRr != null) {                
                extendidaTipoTrabajoMgl = extendidaTipoTrabajoMglManager.
                        findBytipoTrabajoObjAndCom(comunidadRr, otDireccion.getTipoOtHhppId().getTipoTrabajoOFSC());
            }
            
            //obtenemos el location
            List<ParametrosMgl> parametrosFlagList;
            parametrosFlagList = parametrosMglManager.findByAcronimo(Constant.AGENDAMIENTO_OFSC_ONOFF);                
            ParametrosMgl parametrosFlag = parametrosFlagList.get(0);
              if (extendidaTipoTrabajoMgl != null && !extendidaTipoTrabajoMgl.isEmpty()
                  && !parametrosFlag.getParValor().equals(Constant.AGENDAMIENTO_OFSC_VALOR)) {
                extendida =   extendidaTipoTrabajoMgl.get(0);
                location = extendida.getLocationCodigo();
                request.setLocation(Arrays.asList(location.trim()));
            }
           /** JDHT**/


            letraOrigen = parametrosMglManager.findByAcronimo(
                    Constant.LETRA_ORIGEN_WFM_MGL)
                    .iterator().next().getParValor();

            ActivityFieldElement tipoTrabajo = new ActivityFieldElement("worktype_label", tipoWork);
            ActivityFieldElement subTipoTrabajo = new ActivityFieldElement("XA_WorkOrderSubtype", subTipoWork);
            ActivityFieldElement red = new ActivityFieldElement("XA_Red", nombreRed); //"Bidireccional"
            ActivityFieldElement ciudad = new ActivityFieldElement("XA_Idcity", comNodo);//"BOG"
            ActivityFieldElement nodo = new ActivityFieldElement("Node", codNodo);//"2B5012"

            request.setActivityField(Arrays.asList(tipoTrabajo, subTipoTrabajo, red, ciudad, nodo));

            InfoOrdenAct origen = new InfoOrdenAct("origen", letraOrigen);//"W"
            InfoOrdenAct tipoTrabajoInf = new InfoOrdenAct("tipoTrabajo", tipoWork);
            InfoOrdenAct subTipoTrabajoInf = new InfoOrdenAct("subtipotrabajo", subTipoWork);

            request.setInfoOrderAct(
                    Arrays.asList(origen, tipoTrabajoInf, subTipoTrabajoInf));
            
            ParametrosMgl saveRequestResponses = parametrosMglManager.
                    findByAcronimoName(Constant.ACTIVAR_SAVE_REQUEST_RESPONSES);
            String save = "";
            if (saveRequestResponses != null) {
                save = saveRequestResponses.getParValor();
            }
            if(save.equalsIgnoreCase("1")){
                //esta activo el flag para almacenar las peticiones
                RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                String json;
                json = JSONUtils.objetoToJson(request);
                requestResponsesAgeMgl.setTipoPeticion("Request");
                requestResponsesAgeMgl.setTipoOperacion("Capacity");
                requestResponsesAgeMgl.setContenidoPeticion(json);
                requestResponsesAgeMgl.setNumeroOrden(numeroarmado);
                requestResponsesAgeMgl.setFechaCreacion(new Date());
                requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                if(requestResponsesAgeMgl.getRequestRespId()!= null){
                    LOGGER.info("registro almacenado satisfatoriamente");
                }
                
            }
            
            MgwTypeCapacityResponseElement response = clientAgendamiento.callWebService(
                    EnumeratorServiceName.AGENDAMIENTO_CAPACIDAD,
                    MgwTypeCapacityResponseElement.class, request);
            
           
            if (response.getReport() != null
                    && response.getReport().getMessage() != null
                    && Constant.ERROR.equalsIgnoreCase(response.getReport().getMessage().getResult())) {
                String msg = response.getReport().getMessage().getResult()
                        + ": " + response.getReport().getMessage().getDescription();
                LOGGER.info(msg);              
                String json;
                json = JSONUtils.objetoToJson(request);
                
                if (save.equalsIgnoreCase("1")) {
                    //esta activo el flag para almacenar las peticiones
                    RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                    requestResponsesAgeMgl.setTipoPeticion("Responses");
                    requestResponsesAgeMgl.setTipoOperacion("Capacity");
                    String err;
                    err = JSONUtils.objetoToJson(response);
                    requestResponsesAgeMgl.setContenidoPeticion(err);
                    requestResponsesAgeMgl.setNumeroOrden(numeroarmado);
                    requestResponsesAgeMgl.setFechaCreacion(new Date());
                    requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                    if (requestResponsesAgeMgl.getRequestRespId() != null) {
                        LOGGER.info("registro almacenado satisfatoriamente");
                    }

                }
                throw new ApplicationException(msg + "$" + json);
            }
            
             if(save.equalsIgnoreCase("1")){
                //esta activo el flag para almacenar las peticiones
                RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                String json;
                json = JSONUtils.objetoToJson(response);
                requestResponsesAgeMgl.setTipoPeticion("Response");
                requestResponsesAgeMgl.setTipoOperacion("Capacity");
                requestResponsesAgeMgl.setContenidoPeticion(json);
                requestResponsesAgeMgl.setNumeroOrden(numeroarmado);
                requestResponsesAgeMgl.setFechaCreacion(new Date()); 
                requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                if(requestResponsesAgeMgl.getRequestRespId()!= null){
                    LOGGER.info("registro almacenado satisfatoriamente");
                }
                
            }
           
            List<CapacidadAgendaDto> agenda = new ArrayList<>();
            for (CapacityElement capacity : response.getCapacity()) {
                if (capacity.getQuota() == 0l || "".equals(capacity.getWork_skill())) {
                    continue;
                }

                Date fechaMasUno;

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(capacity.getDate().toGregorianCalendar().getTime());
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                fechaMasUno = calendar.getTime();

                agenda.add(new CapacidadAgendaDto(
                        fechaMasUno,
                        capacity.getTime_slot(),
                        capacity.getWork_skill(),
                        (new Long(capacity.getAvailable())).intValue(),
                        (new Long(capacity.getQuota())).intValue(),
                        capacity.getBucket(),
                        capacity.getRestriction()));
            }
           // Collections.sort(agenda);
            return agenda;
        } catch (ApplicationException e) {
            LOGGER.error(ERROR_CAPACIDAD, e);
            throw new ApplicationException(e.getMessage(), e);
        } catch (UniformInterfaceException | IOException e) {
            LOGGER.error(ERROR_CAPACIDAD, e);
            throw new ApplicationException(ERROR_CAPACIDAD);
        } catch (ClientHandlerException e) {
            LOGGER.error(ERROR_CAPACIDAD, e);
            throw new ApplicationException("No fue posible conectar con servicio de WorkForce de agendamiento");
        }
        
    }

    public List<String> sumarDiasAFecha(Date fecha, int dias) {

        List<String> fechas = new ArrayList<>();
        String fechaCon;
        Date fechaSel;

        if (dias == 0) {
            fechaCon = ValidacionUtil.dateFormatyyyyMMdd(fecha);
            fechas.add(fechaCon);

        } else {
            fechaCon = ValidacionUtil.dateFormatyyyyMMdd(fecha);
            fechas.add(fechaCon);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);
            int diaSuma = 1;

            for (int i = 0; i < dias; i++) {

                calendar.add(Calendar.DAY_OF_YEAR, diaSuma);
                fechaSel = calendar.getTime();
                fechaCon = ValidacionUtil.dateFormatyyyyMMdd(fechaSel);
                fechas.add(fechaCon);
            }
        }

        return fechas;

    }

    /**
     * Metodo para realizar el agendamiento de una orden de trabajo
     *
     * @param capacidad {@link CapacidadAgendaDto} registro seleccionado en
     * capacidad
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que se va almacenar en
     * OFSC la agenda
     * @param numeroOTRR
     * @param cuentaAgrupadora
     * @param tecnicoAnticipado  si el agendamiento es de tecnico anticipado.
     * @return CmtAgendamientoMgl agenda para almacenar en MGL
     * @throws ApplicationException Excepcion lanzada al agendar
     */
    public MglAgendaDireccion agendar(CapacidadAgendaDto capacidad,
            MglAgendaDireccion agendaMgl, String numeroOTRR,
            CmtCuentaMatrizMgl cuentaMatrizAgrupadora,
            boolean tecnicoAnticipado) throws ApplicationException {

        String jsonRes = null;
        
        try {

            String direccionHhpp = null;
            String coorX;
            String coorY;
            String subTipoWork;
            CmtExtendidaTecnologiaMgl mglExtendidaTecnologia = null;
            String nombreRed = "";
            CmtBasicaMgl unidadGesBasica;
            String undGestion = "NA";
            CmtBasicaMgl areaBasica;
            String area = "NA";
            CmtBasicaMgl zonaBasica;
            String zona = "NA";
            CmtBasicaMgl distritoBasica;
            String distrito = "NA";
            List<CmtNotasOtHhppMgl> notasOt;
            
            String numeroArmadoCmOFSC;
            BigDecimal numeroCuentaMatrizMER = null;
            if (agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getIsMultiagenda().equalsIgnoreCase("S")) {
               numeroCuentaMatrizMER = cuentaMatrizAgrupadora.getCuentaMatrizId();
               numeroArmadoCmOFSC = armarNumeroCmOfsc(new BigDecimal(cuentaMatrizAgrupadora.getNumeroCuenta().toString()));
            } else {
                numeroArmadoCmOFSC = armarNumeroCmOfsc(agendaMgl.getOrdenTrabajo().getDireccionId().getDirId());

            }
            
            List<TypePropertyElementCr> lstPropElements = new ArrayList<>();

            MgwTypeAgendarRequestElement request = new MgwTypeAgendarRequestElement();
            String formattedDate = sdf.format(new Date());
            MgwHeadElement head = new MgwHeadElement(formattedDate,
                    agendaMgl.getOrdenTrabajo().getOtHhppId().toString());
            request.setHead(head);
            TypeDataElementCr data = new TypeDataElementCr();
            TypeAppointmentElementCr appoiment = new TypeAppointmentElementCr();

            List<CmtDireccionDetalladaMgl> lsDirDetallada = new ArrayList<>();
            if (agendaMgl.getOrdenTrabajo().getDireccionId().getDirId() != null && agendaMgl.getOrdenTrabajo().getSubDireccionId() != null) {
                BigDecimal dirId = agendaMgl.getOrdenTrabajo().getDireccionId().getDirId();
                BigDecimal subDirId = agendaMgl.getOrdenTrabajo().getSubDireccionId().getSdiId();
                lsDirDetallada = direccionDetalleManager.findDireccionDetallaByDirIdSdirId(dirId, subDirId);
            }
            
            if (agendaMgl.getCmtOnyxResponseDto() != null) {
                direccionHhpp = agendaMgl.getOrdenTrabajo().getDireccionId().getDirFormatoIgac();
            } else {
                if (lsDirDetallada != null && !lsDirDetallada.isEmpty()) {
                    direccionHhpp = lsDirDetallada.get(0).getDireccionTexto();
                }
            }
            
            coorX = agendaMgl.getOrdenTrabajo().getDireccionId().getUbicacion().getUbiLongitud();
            coorY = agendaMgl.getOrdenTrabajo().getDireccionId().getUbicacion().getUbiLatitud();

            if (coorX.equals("0") && coorY.equals("0")){
                coorX = null;
                coorY = null;
            }
            if (coorX != null) {
                coorX = coorX.replace(",", ".");
                appoiment.setZip("001");
                    appoiment.setAddress(direccionHhpp);
            }
            if (coorY != null) {
                coorY = coorY.replace(",", ".");
                appoiment.setZip("001");
                appoiment.setAddress(direccionHhpp);
            }
           
            appoiment.setCoordx(coorX);
            appoiment.setCoordy(coorY);

            String numeroArmadoOtOFSC;
            if (agendaMgl.getNumeroOrdenInmediata() != null
                    && !agendaMgl.getNumeroOrdenInmediata().isEmpty()) {
                numeroArmadoOtOFSC = agendaMgl.getNumeroOrdenInmediata();
            } else {
                numeroArmadoOtOFSC = armarNumeroOtOfscHhpp(agendaMgl.getOrdenTrabajo());
            }
            
            numeroArmadoCmOFSC = armarNumeroCmOfsc(new BigDecimal(numeroArmadoCmOFSC));
            
            appoiment.setAppt_number(numeroArmadoOtOFSC);
            
            if(capacidad.getTimeSlot() != null){
             appoiment.setTime_slot(capacidad.getTimeSlot());   
            }

            appoiment.setCustomer_number(numeroArmadoCmOFSC);

            String tipoWork = agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getTipoTrabajoOFSC().getAbreviatura();

            appoiment.setWorktype_label(tipoWork);
            
            GeograficoPoliticoMgl departamento = null;
            GeograficoPoliticoMgl centro;
           
            String codigoDanDepto = "";
            String codigoDanCiudad = "";
            String codigoDanCentro = "";
            
            String codigoDanCiudadAux;
            String codigoDanCentroAux;
            
            
            if(agendaMgl.getOrdenTrabajo().getDireccionId().getUbicacion().getGpoIdObj() != null){
                centro = agendaMgl.getOrdenTrabajo().getDireccionId().getUbicacion().getGpoIdObj();
                codigoDanCentroAux=centro.getGeoCodigoDane();
                if (codigoDanCentroAux != null && !codigoDanCentroAux.isEmpty()) {
                    if (codigoDanCentroAux.length() >= 8) {
                        codigoDanCentro = codigoDanCentroAux.substring(5, 8);
                    } else {
                        throw new ApplicationException("El código DANE del Centro Poblado '" + centro.getGpoNombre() + "' no está correctamente configurado (" + codigoDanCentroAux + ").");
                    }
                } else {
                    throw new ApplicationException("No existe un código DANE para el Centro Poblado '" + centro.getGpoNombre() + "'.");
                }

                GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                GeograficoPoliticoMgl ciudad =    geograficoPoliticoManager.findById(agendaMgl.getOrdenTrabajo().getDireccionId().getUbicacion().getGpoIdObj().getGeoGpoId());
                if (ciudad != null) {
                    codigoDanCiudadAux = ciudad.getGeoCodigoDane();
                    if (codigoDanCiudadAux != null && !codigoDanCiudadAux.equalsIgnoreCase("")) {
                        if (codigoDanCiudadAux.length() >= 5) {
                            codigoDanCiudad = codigoDanCiudadAux.substring(2, 5);
                        } else {
                            throw new ApplicationException("El código DANE de la Ciudad '" + ciudad.getGpoNombre() + "' no está correctamente configurado (" + codigoDanCiudadAux + ").");
                        }
                    } else {
                        throw new ApplicationException("No existe un código DANE para la Ciudad '" + ciudad.getGpoNombre() + "'.");
                    }

                   departamento = geograficoPoliticoManager.findById(ciudad.getGeoGpoId());
                   
                   if(departamento != null){
                     codigoDanDepto = departamento.getGeoCodigoDane();   
                   }
                }
                
            }
            appoiment.setState(departamento != null ? 
                    departamento.getGpoNombre() : "" );

            TypeCommandElementCr command = new TypeCommandElementCr();    
            command.setDate(ValidacionUtil.dateFormatyyyyMMdd(capacidad.getDate()));
            command.setFallback_external_id("0");
            command.setType("0");
            String location = "";
            

            CmtComunidadRr comunidadRr = null;
            NodoMgl nodoMgl;
            String codNodo = "NA";
            CmtRegionalRr regionalRr;
            String comNodo = "";
            String regNodo = "";
            
            
            if (agendaMgl.getNodoMgl() != null) {
                mglExtendidaTecnologia = extendidaTecnologiaMglManager.
                        findBytipoTecnologiaObj(agendaMgl.getNodoMgl().getNodTipo());
                nodoMgl = agendaMgl.getNodoMgl();
                codNodo = nodoMgl.getNodCodigo();
                comunidadRr = nodoMgl.getComId();
                comNodo = comunidadRr != null ? comunidadRr.getCodigoRr() : "";
                regionalRr = comunidadRr != null ? comunidadRr.getRegionalRr() : null;
                regNodo = regionalRr != null ? regionalRr.getCodigoRr() : "";

                if (nodoMgl.getUgeId() != null) {
                    unidadGesBasica = basicaMglManager.findById(nodoMgl.getUgeId());
                    if (unidadGesBasica != null) {
                        undGestion = unidadGesBasica.getCodigoBasica();
                    }
                }
                if (nodoMgl.getAreId() != null) {
                    areaBasica = basicaMglManager.findById(nodoMgl.getAreId());
                    if (areaBasica != null) {
                        area = areaBasica.getCodigoBasica();
                    }
                }
                if (nodoMgl.getZonId() != null) {
                    zonaBasica = basicaMglManager.findById(nodoMgl.getZonId());
                    if (zonaBasica != null) {
                        zona = zonaBasica.getCodigoBasica();
                    }
                }
                if (nodoMgl.getDisId() != null) {
                    distritoBasica = basicaMglManager.findById(nodoMgl.getDisId());
                    if (distritoBasica != null) {
                        distrito = distritoBasica.getCodigoBasica();
                    }
                }
            } else {
                throw new ApplicationException("No existe nodo de cobertura para la tecnologia de la Ot");
            }


            appoiment.setCity(comNodo);

            if (mglExtendidaTecnologia != null && mglExtendidaTecnologia.getIdExtTec() != null) {
                nombreRed = mglExtendidaTecnologia.getNomTecnologiaOFSC().getAbreviatura();
            }

            /*obtenemos la extendida de la cual se extra el location de la combinacion de 
            comunidad vs tipoOt */
            if (agendaMgl.getCmtOnyxResponseDto() != null) {
                List<CmtExtendidaTipoTrabajoMgl> extendidaTipoTrabajoMgl = null;
                CmtExtendidaTipoTrabajoMgl extendida = null;
                if (agendaMgl.getOrdenTrabajo() != null && agendaMgl.getOrdenTrabajo().getTipoOtHhppId() != null
                        && agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getTipoTrabajoOFSC() != null && comunidadRr != null) {
                    extendidaTipoTrabajoMgl = extendidaTipoTrabajoMglManager.
                            findBytipoTrabajoObjAndCom(comunidadRr, agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getTipoTrabajoOFSC());
                }
                //obtenemos el location
                List<ParametrosMgl> parametrosFlagList;
                parametrosFlagList = parametrosMglManager.findByAcronimo(Constant.AGENDAMIENTO_OFSC_ONOFF);                
                ParametrosMgl parametrosFlag = parametrosFlagList.get(0);
                if(parametrosFlag.getParValor().equals(Constant.AGENDAMIENTO_OFSC_VALOR) && capacidad.getBucket() != null && !capacidad.getBucket().isEmpty()) {
                    location = capacidad.getBucket(); 
                }else {                    
                    if (extendidaTipoTrabajoMgl != null && !extendidaTipoTrabajoMgl.isEmpty()) {
                        extendida = extendidaTipoTrabajoMgl.get(0);
                        location = extendida.getLocationCodigo().trim();
                    }
                }
            } else {
                ParametrosMgl paramFlag = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.AGENDAMIENTO_OFSC_ONOFF);
                if(!paramFlag.getParValor().equalsIgnoreCase(co.com.claro.mgl.utils.Constant.AGENDAMIENTO_OFSC_VALOR)) {
                    List<CmtExtendidaTipoTrabajoMgl> extendidaTipoTrabajoMgl = null;
                    CmtExtendidaTipoTrabajoMgl extendida = null;
                    if (agendaMgl.getOrdenTrabajo() != null && agendaMgl.getOrdenTrabajo().getTipoOtHhppId() != null
                            && agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getTipoTrabajoOFSC() != null && comunidadRr != null) {
                        extendidaTipoTrabajoMgl = extendidaTipoTrabajoMglManager.
                                findBytipoTrabajoObjAndCom(comunidadRr, agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getTipoTrabajoOFSC());
                    }
                    if (extendidaTipoTrabajoMgl != null && !extendidaTipoTrabajoMgl.isEmpty()) {
                        extendida = extendidaTipoTrabajoMgl.get(0);
                        location = extendida.getLocationCodigo().trim();
                    }
                } else {
                    location = capacidad.getBucket();
                }
            }

            subTipoWork = agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getSubTipoOrdenOFSC().getCodigoBasica();
            
            String esTecnicoAnticipado = "N";
            String esAgendaInmediata = "N";
             
            if (tecnicoAnticipado && agendaMgl.getIdentificacionTecnico() != null) {
                command.setExternal_id(location);
                ResourcePreferences resourcePreferences = new ResourcePreferences();
                TypeItemElementCr typeItemElementCr = new TypeItemElementCr();
                typeItemElementCr.setResourceId(agendaMgl.getIdentificacionTecnico());
                typeItemElementCr.setPreferenceType("required");
                List<TypeItemElementCr> lst = new ArrayList<>();
                lst.add(typeItemElementCr);
                resourcePreferences.setItems(lst);
                appoiment.setResourcePreferences(resourcePreferences);
                agendaMgl.setFechaAsigTecnico(new Date());
                esTecnicoAnticipado = "Y";
                //Verificamos si viene aliado
                if (agendaMgl.getIdentificacionAliado() != null
                        && !agendaMgl.getIdentificacionAliado().isEmpty()) {
                    CmtAliados aliados;
                    CmtAliadosMglManager aliadosMglManager = new CmtAliadosMglManager();
                    aliados = aliadosMglManager.findByIdAliado(new BigDecimal(agendaMgl.getIdentificacionAliado()));
                    if (aliados != null && aliados.getIdAliado() != null) {
                        agendaMgl.setIdentificacionAliado(aliados.getIdAliado().toString());
                        agendaMgl.setNombreAliado(aliados.getNombre());
                    } else {
                        LOGGER.error("NO HAY INFORMACION DEL ALIADO EN BD.");
                    }
                }
            }else if(agendaMgl.getNumeroOrdenInmediata() != null
                    && agendaMgl.getIdentificacionTecnico() != null) {
                command.setExternal_id(location);
                ResourcePreferences resourcePreferences = new ResourcePreferences();
                TypeItemElementCr typeItemElementCr = new TypeItemElementCr();
                typeItemElementCr.setResourceId(agendaMgl.getIdentificacionTecnico());
                typeItemElementCr.setPreferenceType("required");
                List<TypeItemElementCr> lst = new ArrayList<>();
                lst.add(typeItemElementCr);
                resourcePreferences.setItems(lst);
                appoiment.setResourcePreferences(resourcePreferences);
                agendaMgl.setFechaAsigTecnico(new Date());
                esAgendaInmediata = "Y";

                GetResourcesResponses responsesResources;
                
                BASE_URI_RES_ETA = parametrosMglManager.findByAcronimo(
                co.com.telmex.catastro.services.util.Constant.BASE_URI_REST_GET_ETA_ACTUAL)
                .iterator().next().getParValor();
                
                URL url = new URL(BASE_URI_RES_ETA
                        + CmtAgendamientoMglConstantes.RESOURCES
                        + agendaMgl.getIdentificacionTecnico());

                userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                        .iterator().next().getParValor();
                String comAutorization = "Basic" + " " + userPwdAutorization;

                ConsumoGenerico consumoGenerico = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url,
                        comAutorization, null, null, null);

                responsesResources = consumoGenerico.consumirServicioResources();
                if (responsesResources.getResourceId() == null
                        || responsesResources.getResourceId().isEmpty()) {
                    LOGGER.error("No se encontro informacion del tecnico");
                    throw new ApplicationException("No se encontro informacion del tecnico en OFSC");
                } else {
                    agendaMgl.setNombreTecnico(responsesResources.getName());
                    if (responsesResources.getXR_IdAliado() == null
                            || responsesResources.getXR_IdAliado().isEmpty()) {
                        LOGGER.error("LA RESPUESTA NO TIENE INFORMACION DEL ALIADO");
                    } else {
                        String idAliadoFin = "";
                        if (responsesResources.getXR_IdAliado().contains("-")) {
                            String parts[] = responsesResources.getXR_IdAliado().split("-");
                            if (parts != null && parts.length > 0) {
                                idAliadoFin = parts[0];
                            }
                        } else {
                            idAliadoFin = responsesResources.getXR_IdAliado();
                        }
                        CmtAliados aliados;
                        CmtAliadosMglManager aliadosMglManager = new CmtAliadosMglManager();
                        aliados = aliadosMglManager.findByIdAliado(new BigDecimal(idAliadoFin));
                        if (aliados != null && aliados.getIdAliado() != null) {
                            agendaMgl.setIdentificacionAliado(aliados.getIdAliado().toString());
                            agendaMgl.setNombreAliado(aliados.getNombre());
                        } else {
                            LOGGER.error("NO HAY INFORMACION DEL ALIADO EN BD.");
                        }

                    }
                }
            } else if (tecnicoAnticipado && agendaMgl.getIdentificacionTecnico() == null) {
                command.setExternal_id(location); //"DCE021"     
            } else if (!tecnicoAnticipado) {
                //CONSULTO SI HAY AGENDAS PARA ESTA OT CON TECNICO
                List<BigDecimal> idsEstForUpdate = estadosAgendasForClose();

                List<MglAgendaDireccion> agendas = manager.agendasWithTecnicoHhpp(agendaMgl.getOrdenTrabajo(),
                        idsEstForUpdate, subTipoWork);

                if (agendas != null && !agendas.isEmpty()) {
                    MglAgendaDireccion agendaWithTecnico = agendas.get(0);
                    command.setExternal_id(agendaWithTecnico.getIdentificacionTecnico());
                    agendaMgl.setIdentificacionTecnico(agendaWithTecnico.getIdentificacionTecnico());
                    agendaMgl.setNombreTecnico(agendaWithTecnico.getNombreTecnico());
                    agendaMgl.setIdentificacionAliado(agendaWithTecnico.getIdentificacionAliado());
                    agendaMgl.setNombreAliado(agendaWithTecnico.getNombreAliado());
                    agendaMgl.setFechaAsigTecnico(new Date());
                } else {
                    command.setExternal_id(location); //"DCE021"     
                }

            }

            command.setAppointment(appoiment);
            TypeCommandsArrayCr commands = new TypeCommandsArrayCr();
            commands.add(command);
            data.setCommands(commands);
            request.setData(data);

            String obs = "NOTAS DE OT HHPP:";
            CmtNotasOtHhppMglManager notasManager = new CmtNotasOtHhppMglManager();
            notasOt = notasManager.findNotasByOtHhppId(agendaMgl.getOrdenTrabajo().getOtHhppId());
          
            if (notasOt != null) {
                for (CmtNotasOtHhppMgl notas : notasOt) {
                    obs = obs + " " + notas.getNota();
                }
            }

            List<OtHhppTecnologiaMgl> otHhppTecnologiaMglList
                    = otHhppTecnologiaMglManager.findOtHhppTecnologiaByOtHhppId(agendaMgl.getOrdenTrabajo().getOtHhppId());
           
            //Maneja Tecnologias
            if (agendaMgl.getOrdenTrabajo().getTipoOtHhppId().
                    getManejaTecnologias().compareTo(BigDecimal.ONE) == 0 &&
                    otHhppTecnologiaMglList != null) {
                //Agregar Tecnologias con los nodos
                for (OtHhppTecnologiaMgl tecnologia : otHhppTecnologiaMglList) {

                    if (tecnologia.getTecnoglogiaBasicaId().getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_UNI)) {
                        TypePropertyElementCr tecUni = new TypePropertyElementCr("XA_NODO_HFCUNI", tecnologia.getNodo().getNodCodigo());
                        lstPropElements.add(tecUni);
                    }

                    if (tecnologia.getTecnoglogiaBasicaId().getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_BID)) {
                        TypePropertyElementCr tecBidi = new TypePropertyElementCr("XA_NODO_HFCBI", tecnologia.getNodo().getNodCodigo());
                        lstPropElements.add(tecBidi);
                    }

                    if (tecnologia.getTecnoglogiaBasicaId().getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_OP_GPON)) {
                        TypePropertyElementCr tecFog = new TypePropertyElementCr("XA_NODO_FOG", tecnologia.getNodo().getNodCodigo());
                        lstPropElements.add(tecFog);
                    }

                    if (tecnologia.getTecnoglogiaBasicaId().getIdentificadorInternoApp().equalsIgnoreCase(Constant.DTH)) {
                        TypePropertyElementCr tecDth = new TypePropertyElementCr("XA_NODO_DTH", tecnologia.getNodo().getNodCodigo());
                        lstPropElements.add(tecDth);
                    }

                    if (tecnologia.getTecnoglogiaBasicaId().getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_FTTTH)) {
                        TypePropertyElementCr tecFtth = new TypePropertyElementCr("XA_NODO_FTTH", tecnologia.getNodo().getNodCodigo());
                        lstPropElements.add(tecFtth);
                    }

                    if (tecnologia.getTecnoglogiaBasicaId().getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_OP_UNI)) {
                        TypePropertyElementCr tecFou = new TypePropertyElementCr("XA_NODO_FOU", tecnologia.getNodo().getNodCodigo());
                        lstPropElements.add(tecFou);
                    }

                    if (tecnologia.getTecnoglogiaBasicaId().getIdentificadorInternoApp().equalsIgnoreCase(Constant.BTS_MOVIL)) {
                        TypePropertyElementCr tecBts = new TypePropertyElementCr("XA_NODO_BTS", tecnologia.getNodo().getNodCodigo());
                        lstPropElements.add(tecBts);
                    }

                }
            }
            if (nodoMgl.getNodId() != null) {
                TypePropertyElementCr tecnologiaNew = new TypePropertyElementCr("XA_CM_Tecnologia", 
                        nodoMgl.getNodTipo().getNombreBasica());
                lstPropElements.add(tecnologiaNew);
            }

            String slaWindow;
            if (capacidad.getHoraInicio() != null) {
                TypePropertyElementCr horaIni = new TypePropertyElementCr("deliveryWindowStart", capacidad.getHoraInicio());
                lstPropElements.add(horaIni);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                slaWindow = df.format(capacidad.getDate()) + " " + capacidad.getHoraInicio();
                TypePropertyElementCr slaWindowStart = new TypePropertyElementCr(
                        "slaWindowStart", slaWindow);
                lstPropElements.add(slaWindowStart);
            }

            if (appoiment.getCoordx() == null || appoiment.getCoordx() == null) {
                TypePropertyElementCr xa_MerAdress = new TypePropertyElementCr("XA_MerAddress", direccionHhpp);
                lstPropElements.add(xa_MerAdress);
            }

            TypePropertyElementCr tecnologia = new TypePropertyElementCr("XA_Red", nombreRed);
            lstPropElements.add(tecnologia);

            TypePropertyElementCr daneDep = new TypePropertyElementCr("XA_Departamento", codigoDanDepto);
            lstPropElements.add(daneDep);
            
            TypePropertyElementCr daneCiu = new TypePropertyElementCr("XA_Municipio", codigoDanCiudad);
            lstPropElements.add(daneCiu);
            
            TypePropertyElementCr daneCen = new TypePropertyElementCr("XA_CenPoblado", codigoDanCentro);
            lstPropElements.add(daneCen);

            TypePropertyElementCr notCm = new TypePropertyElementCr("activity_notes", obs);
            lstPropElements.add(notCm);

            TypePropertyElementCr city = new TypePropertyElementCr("XA_Idcity", comNodo);
            lstPropElements.add(city);

            String fecCreaOt = ValidacionUtil.dateFormatyyyyMMdd(agendaMgl.getOrdenTrabajo().getFechaCreacion());
            TypePropertyElementCr fechaCreaOt = new TypePropertyElementCr("XA_FechaCreacion", fecCreaOt);
            lstPropElements.add(fechaCreaOt);

            TypePropertyElementCr tipOrdSup = new TypePropertyElementCr("XA_TipoOrdenSupervision", "OS");
            lstPropElements.add(tipOrdSup);

            TypePropertyElementCr subTipoTra = new TypePropertyElementCr("XA_WorkOrderSubtype", subTipoWork);
            lstPropElements.add(subTipoTra);

            TypePropertyElementCr bucket = new TypePropertyElementCr("XA_Bucket", location);
            lstPropElements.add(bucket);

            TypePropertyElementCr dirActual = new TypePropertyElementCr("XA_DireccionActual", direccionHhpp);
            lstPropElements.add(dirActual);
            
            if (!codNodo.equalsIgnoreCase("NA")) {
                TypePropertyElementCr nodo = new TypePropertyElementCr("Node", codNodo);
                lstPropElements.add(nodo);
            }
           
            String SLA = String.valueOf(agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getAns());
            String avisoSla = String.valueOf(agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getAnsAviso());
            String avisoSlaFi = avisoSla + " " + "Horas";

            TypePropertyElementCr slaSus = new TypePropertyElementCr("XA_SLASuscriptor", SLA);
            lstPropElements.add(slaSus);

            TypePropertyElementCr estSLA = new TypePropertyElementCr("XA_EstadoSLA", agendaMgl.getOrdenTrabajo().getAns());
            lstPropElements.add(estSLA);

            TypePropertyElementCr indEstSLA = new TypePropertyElementCr("XA_IndicadorEstadoSLA", "E");
            lstPropElements.add(indEstSLA);

            TypePropertyElementCr slaCum = new TypePropertyElementCr("XA_SLACumplimiento", avisoSlaFi);
            lstPropElements.add(slaCum);

            //SOLICITADOS POR EL CLIENTE
            String usuarioCreaOt = agendaMgl.getOrdenTrabajo().getUsuarioCreacion();
            TypePropertyElementCr usuCreOt = new TypePropertyElementCr("XA_UsuarioCrea", usuarioCreaOt);//XA_UsuarioCrea
            lstPropElements.add(usuCreOt);

            TypePropertyElementCr usuCreOtnew = new TypePropertyElementCr("XA_UsuarioCrea1", usuarioCreaOt);
            lstPropElements.add(usuCreOtnew);

            TypePropertyElementCr dirPrincipal = new TypePropertyElementCr("XA_DireccionEdificio", direccionHhpp);
            lstPropElements.add(dirPrincipal);

            String tipoOt = agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getNombreTipoOt();
            TypePropertyElementCr claseOt = new TypePropertyElementCr("XA_ClaseOrden", tipoOt);
            lstPropElements.add(claseOt);
            
            String comAscensores = "NA";

            if (!comAscensores.equalsIgnoreCase("NA")) {
                TypePropertyElementCr comAscen = new TypePropertyElementCr("XA_CompaniaAsensores", comAscensores);
                lstPropElements.add(comAscen);
            }
            
            String comAdministracion = "NA";
           
            if (!comAdministracion.equalsIgnoreCase("NA")) {
                TypePropertyElementCr comAdm = new TypePropertyElementCr("XA_CompaniaAdministracion", comAdministracion);
                lstPropElements.add(comAdm);
            }
            
            TypePropertyElementCr regional = new TypePropertyElementCr("XA_Regional", regNodo);
            lstPropElements.add(regional);
            
                  
            if (esAgendaInmediata.equalsIgnoreCase("Y")) {
                TypePropertyElementCr esAgendaInmediataMer = new TypePropertyElementCr("MER_AGE_INMEDIATA", "1");
                lstPropElements.add(esAgendaInmediataMer);                
            }

            String barrio = null;
            if (lsDirDetallada != null && !lsDirDetallada.isEmpty()) {
                barrio = lsDirDetallada.get(0).getBarrio();
            }

            TypePropertyElementCr barrioCm;

            if (barrio != null) {
                barrioCm = new TypePropertyElementCr("XA_Barrio", barrio);
                lstPropElements.add(barrioCm);
            } 

            //pendiente blacklist
            TypePropertyElementCr indRein = new TypePropertyElementCr("XA_IndicadorReincidencias", "N");
            lstPropElements.add(indRein);

            if (!distrito.equalsIgnoreCase("NA")) {
                TypePropertyElementCr distritoSave = new TypePropertyElementCr("XA_Distrito", distrito);
                lstPropElements.add(distritoSave);
            }
            
            if (!undGestion.equalsIgnoreCase("NA")) {
                TypePropertyElementCr undGesSave = new TypePropertyElementCr("XA_UnidadGestion", undGestion);
                lstPropElements.add(undGesSave);
            }
            
            if (!area.equalsIgnoreCase("NA")) {
                TypePropertyElementCr areaSave = new TypePropertyElementCr("XA_Area", area);
                lstPropElements.add(areaSave);
            }
           
            if (!zona.equalsIgnoreCase("NA")) {
                TypePropertyElementCr zonaSave = new TypePropertyElementCr("XA_Zona", zona);
                lstPropElements.add(zonaSave);
            }
           
            String nombreCon = agendaMgl.getPersonaRecVt();
            String tel2Con = agendaMgl.getTelPerRecVt();

            TypePropertyElementCr contacto = new TypePropertyElementCr("XA_Contacto", nombreCon);
            lstPropElements.add(contacto);

            TypePropertyElementCr telUnoCon = new TypePropertyElementCr("XA_Telefonouno", tel2Con);
            lstPropElements.add(telUnoCon);

            TypePropertyElementCr telDosCon = new TypePropertyElementCr("XA_Telefonodos_Contacto", tel2Con);
            lstPropElements.add(telDosCon);

            TypePropertyElementCr numMarker = new TypePropertyElementCr("XA_NumeroMarker", "No Hay");
            lstPropElements.add(numMarker);

            TypePropertyElementCr marker = new TypePropertyElementCr("XA_Marker", "1");
            lstPropElements.add(marker);

            TypePropertyElementCr confMail = new TypePropertyElementCr("XA_Confirmation_Mail", "0");
            lstPropElements.add(confMail);
            
            if (agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getIsMultiagenda().equalsIgnoreCase("S")) {
                TypePropertyElementCr otmasterApptNumber = new TypePropertyElementCr("masterApptNumber", numeroOTRR);
                lstPropElements.add(otmasterApptNumber);
                
                String tipoOrdenMGW = parametrosMglManager.findByAcronimo(
                        Constant.LETRA_TIPO_ORDEN_MGW_HHPP_MULTI)
                        .iterator().next().getParValor();
                TypePropertyElementCr tipOrdenMGW = new TypePropertyElementCr("XA_TipoOrdenMGW", tipoOrdenMGW);
                lstPropElements.add(tipOrdenMGW);
            }else{
                String tipoOrdenMGW = parametrosMglManager.findByAcronimo(
                        Constant.LETRA_TIPO_ORDEN_MGW_HHPP)
                        .iterator().next().getParValor();
                TypePropertyElementCr tipOrdenMGW = new TypePropertyElementCr("XA_TipoOrdenMGW", tipoOrdenMGW);
                lstPropElements.add(tipOrdenMGW);
            }
            
            if (numeroCuentaMatrizMER != null) {
                TypePropertyElementCr numeroCuentaMatrizMer = new TypePropertyElementCr("XA_CuentaMatrizMER", numeroCuentaMatrizMER.toString());
                lstPropElements.add(numeroCuentaMatrizMer);
            }
            if(agendaMgl.getIdOtenrr() != null){
                TypePropertyElementCr idOtRR = new TypePropertyElementCr("XA_IdOtRRMER", agendaMgl.getIdOtenrr());
                lstPropElements.add(idOtRR);
            }else if(numeroOTRR != null){
                TypePropertyElementCr idOtRR = new TypePropertyElementCr("XA_IdOtRRMER", numeroOTRR.substring(6));
                lstPropElements.add(idOtRR);
            }
            
            TypePropertyElementCr nomCorresPon = new TypePropertyElementCr("XA_NombreCorrespondencia", comAdministracion);
            lstPropElements.add(nomCorresPon);

            String origenOrden = parametrosMglManager.findByAcronimo(
                    Constant.PROPIEDAD_ORIGEN_ORDEN)
                    .iterator().next().getParValor();
            TypePropertyElementCr orgOrden = new TypePropertyElementCr("XA_OrigenOrden", origenOrden);
            lstPropElements.add(orgOrden);

            TypePropertyElementCr valMatSoftclose = new TypePropertyElementCr("XA_ValidacionMateriales_SoftClose", "0");
            lstPropElements.add(valMatSoftclose);

            TypePropertyElementCr valActPel = new TypePropertyElementCr("XA_ValidacionActivPeligrosa_SoftClose", "0");
            lstPropElements.add(valActPel);

            TypePropertyElementCr valFirSoftclose = new TypePropertyElementCr("XA_ValidacionFirma_SoftClose", "0");
            lstPropElements.add(valFirSoftclose);

            TypePropertyElementCr telefonoDos = new TypePropertyElementCr("XA_Telefonodos", tel2Con);
            lstPropElements.add(telefonoDos);

            TypePropertyElementCr numReincOt = new TypePropertyElementCr("XA_NumeroReincidenciasOT", "1");
            lstPropElements.add(numReincOt);

            TypePropertyElementCr numCancelaciones = new TypePropertyElementCr("XA_NumeroCancelaciones", "0");
            lstPropElements.add(numCancelaciones);

            TypePropertyElementCr numReincServ = new TypePropertyElementCr("XA_NumeroReincidenciasServicios", "0");
            lstPropElements.add(numReincServ);

            TypePropertyElementCr numReincCal = new TypePropertyElementCr("XA_NumeroReincidenciasCalidad", "0");
            lstPropElements.add(numReincCal);

            TypePropertyElementCr codigoHash = new TypePropertyElementCr("XA_CodigoHash", "ARTOC_1");
            lstPropElements.add(codigoHash);
            
            if (agendaMgl.getOrdenTrabajo().getSegmento() != null
                    && agendaMgl.getOrdenTrabajo().getSegmento().getNombreBasica() != null
                    && !agendaMgl.getOrdenTrabajo().getSegmento().getNombreBasica().isEmpty()) {
                TypePropertyElementCr tipoCliente = new TypePropertyElementCr("XA_TipoCliente", agendaMgl.getOrdenTrabajo().getSegmento().getNombreBasica());
                lstPropElements.add(tipoCliente);
            } else {
                TypePropertyElementCr tipoCliente = new TypePropertyElementCr("XA_TipoCliente", "0");
                lstPropElements.add(tipoCliente);
            }
            
            if (agendaMgl.getOrdenTrabajo().getFechaCreacionOt() != null) {
                TypePropertyElementCr fechaCreacionOt = new TypePropertyElementCr("MER_CM_FechaSOT", agendaMgl.getOrdenTrabajo().getFechaCreacionOt().toString());
                lstPropElements.add(fechaCreacionOt);
            }           
            
            String estrato = String.valueOf(agendaMgl.getOrdenTrabajo().getDireccionId().getDirEstrato());

            TypePropertyElementCr estratoCm = new TypePropertyElementCr("XA_Estrato", estrato);
            lstPropElements.add(estratoCm);

            TypePropertyElementCr candelario = new TypePropertyElementCr("XA_Calendario", "TC");
            lstPropElements.add(candelario);

            TypePropertyElementCr rentaCliente = new TypePropertyElementCr("XA_RentaCliente", "0");
            lstPropElements.add(rentaCliente);

            TypePropertyElementCr saldo = new TypePropertyElementCr("XA_Saldo", "0");
            lstPropElements.add(saldo);

            TypePropertyElementCr ultPago = new TypePropertyElementCr("XA_UltimoPago", "0");
            lstPropElements.add(ultPago);

            TypePropertyElementCr servOt = new TypePropertyElementCr("XA_ServiciosOT", "1MT");
            lstPropElements.add(servOt);

            TypePropertyElementCr servAfec = new TypePropertyElementCr("XA_Servicios_Afectados", "TV");
            lstPropElements.add(servAfec);

            TypePropertyElementCr hogActivos = new TypePropertyElementCr("XA_HogaresActivos", "0");
            lstPropElements.add(hogActivos);

            TypePropertyElementCr espComercial = new TypePropertyElementCr("XA_EspecialistaComercial", "CRM User");
            lstPropElements.add(espComercial);

            TypePropertyElementCr codEspComercial = new TypePropertyElementCr("XA_CodigoEspecialistaComercial", "007");
            lstPropElements.add(codEspComercial);

            if (agendaMgl.getCmtOnyxResponseDto() != null) {

                TypePropertyElementCr prioridad = new TypePropertyElementCr("XA_Prioridad", "2");
                lstPropElements.add(prioridad);
                 
                if (agendaMgl.getCmtOnyxResponseDto().getNIT_Cliente() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getNIT_Cliente().isEmpty()) {
                    TypePropertyElementCr nitClienteOnix = new TypePropertyElementCr("XA_CM_NitCliente",
                            agendaMgl.getCmtOnyxResponseDto().getNIT_Cliente());
                    lstPropElements.add(nitClienteOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getNombre() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getNombre().isEmpty()) {
                    TypePropertyElementCr nombreClienteOnix = new TypePropertyElementCr("XA_CM_Nombre",
                            agendaMgl.getCmtOnyxResponseDto().getNombre());
                    lstPropElements.add(nombreClienteOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getNombre_OT_Hija() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getNombre_OT_Hija().isEmpty()) {
                    TypePropertyElementCr nombreOtHijaOnix = new TypePropertyElementCr("XA_CM_NombreOTHija",
                            agendaMgl.getCmtOnyxResponseDto().getNombre_OT_Hija());
                    lstPropElements.add(nombreOtHijaOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getOTP() > 0) {
                    TypePropertyElementCr numeroOtPadreOnix = new TypePropertyElementCr("XA_CM_OTP",
                            String.valueOf(agendaMgl.getCmtOnyxResponseDto().getOTP()));
                    lstPropElements.add(numeroOtPadreOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getOTH() > 0) {
                    TypePropertyElementCr numeroOtHijaOnix = new TypePropertyElementCr("XA_CM_OTH",
                            String.valueOf(agendaMgl.getCmtOnyxResponseDto().getOTH()));
                    lstPropElements.add(numeroOtHijaOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getFechaCreacionOTH() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getFechaCreacionOTH().isEmpty()) {
                    TypePropertyElementCr fecCreaOtHijaOnix = new TypePropertyElementCr("XA_CM_FecCreacionHj",
                            agendaMgl.getCmtOnyxResponseDto().getFechaCreacionOTH());
                    lstPropElements.add(fecCreaOtHijaOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getFechaCreacionOTP() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getFechaCreacionOTP().isEmpty()) {
                    TypePropertyElementCr fecCreaOtPadreOnix = new TypePropertyElementCr("XA_CM_FecCreacionPd",
                            agendaMgl.getCmtOnyxResponseDto().getFechaCreacionOTP());
                    lstPropElements.add(fecCreaOtPadreOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getContactoTecnicoOTP() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getContactoTecnicoOTP().isEmpty()) {
                    TypePropertyElementCr conTecOtPadreOnix = new TypePropertyElementCr("XA_CM_Contactos",
                            agendaMgl.getCmtOnyxResponseDto().getContactoTecnicoOTP());
                    lstPropElements.add(conTecOtPadreOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getTelefonoContacto() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getTelefonoContacto().isEmpty()) {
                    TypePropertyElementCr telContOtPadreOnix = new TypePropertyElementCr("XA_CM_Telefono",
                            agendaMgl.getCmtOnyxResponseDto().getTelefonoContacto());
                    lstPropElements.add(telContOtPadreOnix);
                }
                //Pendiente descripcion por parte de OFSC      
                if (agendaMgl.getCmtOnyxResponseDto().getDireccion() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getDireccion().isEmpty()) {
                    TypePropertyElementCr direccionOnix = new TypePropertyElementCr("XA_DirAlterna",
                            agendaMgl.getCmtOnyxResponseDto().getDireccion());
                    lstPropElements.add(direccionOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getSegmento() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getSegmento().isEmpty()) {
                    TypePropertyElementCr segmentoOnix = new TypePropertyElementCr("XA_CM_Segmento",
                            agendaMgl.getCmtOnyxResponseDto().getSegmento());
                    lstPropElements.add(segmentoOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getTipoServicio() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getTipoServicio().isEmpty()) {
                    TypePropertyElementCr tipoServicioOnix = new TypePropertyElementCr("XA_CM_TipoServicio",
                            agendaMgl.getCmtOnyxResponseDto().getTipoServicio());
                    lstPropElements.add(tipoServicioOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getServicios() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getServicios().isEmpty()) {
                    TypePropertyElementCr serviciosOnix = new TypePropertyElementCr("XA_CM_Servicios",
                            agendaMgl.getCmtOnyxResponseDto().getServicios());
                    lstPropElements.add(serviciosOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getRecurrenteMensual() != null) {
                    TypePropertyElementCr recurrenteMenOnix = new TypePropertyElementCr("XA_RentaCliente",
                            agendaMgl.getCmtOnyxResponseDto().getRecurrenteMensual().toString());
                    lstPropElements.add(recurrenteMenOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getCodigoServicio() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getCodigoServicio().isEmpty()) {
                    TypePropertyElementCr codServicioOnix = new TypePropertyElementCr("XA_CM_CodServEnl",
                            agendaMgl.getCmtOnyxResponseDto().getCodigoServicio());
                    lstPropElements.add(codServicioOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getVendedor() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getVendedor().isEmpty()) {
                    TypePropertyElementCr vendedorOnix = new TypePropertyElementCr("XA_CM_Vendedor",
                            agendaMgl.getCmtOnyxResponseDto().getVendedor());
                    lstPropElements.add(vendedorOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getTelefono() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getTelefono().isEmpty()) {
                    TypePropertyElementCr telefonoOnix = new TypePropertyElementCr("XA_CM_Tel",
                            agendaMgl.getCmtOnyxResponseDto().getTelefono());
                    lstPropElements.add(telefonoOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getNotasOTH() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getNotasOTH().isEmpty()) {
                    TypePropertyElementCr notasOtHijaOnix = new TypePropertyElementCr("XA_CM_NotasOTH",
                            agendaMgl.getCmtOnyxResponseDto().getNotasOTH());
                    lstPropElements.add(notasOtHijaOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getEstadoOTH() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getEstadoOTH().isEmpty()) {
                    TypePropertyElementCr estOtHijaOnix = new TypePropertyElementCr("XA_CM_EstOsOth",
                            agendaMgl.getCmtOnyxResponseDto().getEstadoOTH());
                    lstPropElements.add(estOtHijaOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getEstadoOTP() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getEstadoOTP().isEmpty()) {
                    TypePropertyElementCr estOtPadreOnix = new TypePropertyElementCr("XA_CM_EstOpOtp",
                            agendaMgl.getCmtOnyxResponseDto().getEstadoOTP());
                    lstPropElements.add(estOtPadreOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getFechaCompromisoOTP() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getFechaCompromisoOTP().isEmpty()) {
                    TypePropertyElementCr fecComproOtPadreOnix = new TypePropertyElementCr("XA_CM_FechaCom",
                            agendaMgl.getCmtOnyxResponseDto().getFechaCompromisoOTP());
                    lstPropElements.add(fecComproOtPadreOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getCodResolucion1OTH() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getCodResolucion1OTH().isEmpty()) {
                    TypePropertyElementCr codResoOtHija1Onix = new TypePropertyElementCr("XA_CM_CodRes1",
                            agendaMgl.getCmtOnyxResponseDto().getCodResolucion1OTH());
                    lstPropElements.add(codResoOtHija1Onix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getCodResolucion2OTH() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getCodResolucion2OTH().isEmpty()) {
                    TypePropertyElementCr codResoOtHija2Onix = new TypePropertyElementCr("XA_CM_CodRes2",
                            agendaMgl.getCmtOnyxResponseDto().getCodResolucion2OTH());
                    lstPropElements.add(codResoOtHija2Onix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getCodResolucion3OTH() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getCodResolucion3OTH().isEmpty()) {
                    TypePropertyElementCr codResoOtHija3Onix = new TypePropertyElementCr("XA_CM_CodRes3",
                            agendaMgl.getCmtOnyxResponseDto().getCodResolucion3OTH());
                    lstPropElements.add(codResoOtHija3Onix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getCodResolucion4OTH() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getCodResolucion4OTH().isEmpty()) {
                    TypePropertyElementCr codResoOtHija4Onix = new TypePropertyElementCr("XA_CM_CodRes4",
                            agendaMgl.getCmtOnyxResponseDto().getCodResolucion4OTH());
                    lstPropElements.add(codResoOtHija4Onix);
                }
                
                 if (agendaMgl.getOrdenTrabajo().getComplejidadServicio() != null
                        && !agendaMgl.getOrdenTrabajo().getComplejidadServicio().isEmpty()) {
                    TypePropertyElementCr complejidadServ= new TypePropertyElementCr("XA_CM_Complejidad",
                            agendaMgl.getOrdenTrabajo().getComplejidadServicio());
                    lstPropElements.add(complejidadServ);
                }
                //Pendiente codigo resolucion ot padre por parte de OFSC
            } else {
                 
                TypePropertyElementCr prioridad = new TypePropertyElementCr("XA_Prioridad", "1");
                lstPropElements.add(prioridad);
                
                String etqManNoOtPadre = parametrosMglManager.findByAcronimo(
                        Constant.MAN_NO_OT_PADRE)
                        .iterator().next().getParValor();
                
                String etqManNoOtHija = parametrosMglManager.findByAcronimo(
                        Constant.MAN_NO_OT_HIJA)
                        .iterator().next().getParValor();
                
                if(capacidad.getProperty() != null) {
                    for(AppCrearAgendaOtPropertyRequest property : capacidad.getProperty()) {
                        if(property.getLabel().equals(etqManNoOtPadre)) {
                            TypePropertyElementCr manOTPadre = new TypePropertyElementCr(etqManNoOtPadre, property.getValue());
                            lstPropElements.add(manOTPadre);
                        }
                        if(property.getLabel().equals(etqManNoOtHija)) {
                            TypePropertyElementCr manOTHija = new TypePropertyElementCr(etqManNoOtHija, property.getValue());
                            lstPropElements.add(manOTHija);
                        }
                    }
                }
            }

            TypePropertiesArrayCr propertiesArrayCr = new TypePropertiesArrayCr(lstPropElements);

            appoiment.setProperties(propertiesArrayCr);
            
            ParametrosMgl saveRequestResponses = parametrosMglManager.
                    findByAcronimoName(Constant.ACTIVAR_SAVE_REQUEST_RESPONSES);
            String save = "";
            if (saveRequestResponses != null) {
                save = saveRequestResponses.getParValor();
            }
            if(save.equalsIgnoreCase("1")){
                //esta activo el flag para almacenar las peticiones
                RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                String json;
                json = JSONUtils.objetoToJson(request);
                requestResponsesAgeMgl.setTipoPeticion("Request");
                requestResponsesAgeMgl.setTipoOperacion("Agendar");
                requestResponsesAgeMgl.setContenidoPeticion(json);
                requestResponsesAgeMgl.setNumeroOrden(numeroArmadoOtOFSC);
                requestResponsesAgeMgl.setFechaCreacion(new Date()); 
                requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                if(requestResponsesAgeMgl.getRequestRespId()!= null){
                    LOGGER.info("registro almacenado satisfatoriamente");
                }
                
            }
            
            MgwTypeAgendarResponseElement response = clientAgendamiento.callWebService(
                    EnumeratorServiceName.AGENDAMIENTO_AGENDAR,
                    MgwTypeAgendarResponseElement.class, request);
            
             jsonRes = JSONUtils.objetoToJson(response);

            if ((response.getReport() == null || (response.getReport().getMessage() != null
                    && "".equals(response.getReport().getMessage().getDescription())))
                    && (response.getData().getCommands().getCommand().get(0).getAppointment().getReport() != null
                    && !Constant.ERROR.equals(response.getData().getCommands().getCommand()
                            .get(0).getAppointment().getReport().getMessage().getResult()))) {
                
                 if (save.equalsIgnoreCase("1")) {
                    //esta activo el flag para almacenar las peticiones
                    RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                    String json;
                    json = JSONUtils.objetoToJson(response);
                    requestResponsesAgeMgl.setTipoPeticion("Response");
                    requestResponsesAgeMgl.setTipoOperacion("Agendar");
                    requestResponsesAgeMgl.setContenidoPeticion(json);
                    requestResponsesAgeMgl.setNumeroOrden(numeroArmadoOtOFSC);
                    requestResponsesAgeMgl.setFechaCreacion(new Date()); 
                    requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                    if (requestResponsesAgeMgl.getRequestRespId() != null) {
                        LOGGER.info("registro almacenado satisfatoriamente");
                    }

                }


                LOGGER.info(String.format("Se ha creado la agenda para la ot %s", numeroArmadoOtOFSC));

                CmtBasicaMgl estadoAgBasicaMgl = basicaMglManager.
                        findByCodigoInternoApp(Constant.ESTADO_AGENDA_AGENDADA);

                String consecutivo = numeroArmadoOtOFSC.substring(numeroArmadoOtOFSC.length() - 3, numeroArmadoOtOFSC.length());
                int consecutivoMultiAge = Integer.parseInt(consecutivo);
                      
                agendaMgl =  new MglAgendaDireccion(capacidad.getDate(),
                        response.getData().getCommands().getCommand().get(0).getType(),
                        response.getData().getCommands().getCommand().get(0).getExternal_id(),
                        response.getData().getCommands().getCommand().get(0).getAppointment().getAid(),
                        response.getData().getCommands().getCommand().get(0).getAppointment().getReport().getMessage().getResult(),
                        response.getData().getCommands().getCommand().get(0).getAppointment().getReport().getMessage().getType(),
                        agendaMgl.getOrdenTrabajo(), numeroArmadoOtOFSC,
                        null, estadoAgBasicaMgl, consecutivoMultiAge, capacidad.getTimeSlot(), agendaMgl.getPersonaRecVt(),
                        agendaMgl.getTelPerRecVt(), agendaMgl.getPrerequisitosVT(),
                        agendaMgl.getEmailPerRecVT(),"N", subTipoWork, agendaMgl.getOrdenTrabajo().getEstadoInternoInicial(),agendaMgl.getIdentificacionTecnico(), 
                        agendaMgl.getNombreTecnico(),agendaMgl.getIdentificacionAliado(),
                        agendaMgl.getNombreAliado(),agendaMgl.getFechaAsigTecnico(),
                        response, agendaMgl.getNodoMgl(), esTecnicoAnticipado, 
                        cuentaMatrizAgrupadora, capacidad.getHoraInicio(),esAgendaInmediata);
                
                return agendaMgl;
            }
            String error = response.getReport() != null
                    ? response.getReport().getMessage().getDescription()
                    : response.getData().getCommands().getCommand().get(0).getAppointment().getReport().getMessage().getDescription();
            String json;
            json = JSONUtils.objetoToJson(request);
             if(save.equalsIgnoreCase("1")){
                //esta activo el flag para almacenar las peticiones
                RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                requestResponsesAgeMgl.setTipoPeticion("Responses");
                requestResponsesAgeMgl.setTipoOperacion("Agendar");
                String err;
                err = JSONUtils.objetoToJson(response);
                requestResponsesAgeMgl.setContenidoPeticion(err);
                requestResponsesAgeMgl.setNumeroOrden(numeroArmadoOtOFSC);
                requestResponsesAgeMgl.setFechaCreacion(new Date()); 
                requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                if(requestResponsesAgeMgl.getRequestRespId()!= null){
                    LOGGER.info("registro almacenado satisfatoriamente");
                }
                
            }
            throw new ApplicationException(error + "$" + json);

        } catch (ApplicationException | UniformInterfaceException | IOException | NumberFormatException
                | NullPointerException | EJBException  e) {
            String msg = String.format(ERROR_AGENDA, e.getMessage());
            LOGGER.error(msg, e);
            if (jsonRes != null && !jsonRes.isEmpty()) {
                if (jsonRes.equalsIgnoreCase("{}")) {
                    msg = "La respuesta del servicio viene vacio: " + msg;
                }
            }
            throw new ApplicationException(msg, e);
        }
    }

    public String armarNumeroCmOfsc(BigDecimal idCcmm) {

        String numeroArmado = "";
        String numCm = idCcmm.toString();

        switch (numCm.length()) {
            case 15:
                numeroArmado = numCm;
                break;
            case 14:
                numeroArmado = "0" + numCm;
                break;
            case 13:
                numeroArmado = "00" + numCm;
                break;
            case 12:
                numeroArmado = "000" + numCm;
                break;
            case 11:
                numeroArmado = "0000" + numCm;
                break;
            case 10:
                numeroArmado = "00000" + numCm;
                break;
            case 9:
                numeroArmado = "000000" + numCm;
                break;
            case 8:
                numeroArmado = "0000000" + numCm;
                break;
            case 7:
                numeroArmado = "00000000" + numCm;
                break;
            case 6:
                numeroArmado = "000000000" + numCm;
                break;
            case 5:
                numeroArmado = "0000000000" + numCm;
                break;
            case 4:
                numeroArmado = "00000000000" + numCm;
                break;
            case 3:
                numeroArmado = "000000000000" + numCm;
                break;
            case 2:
                numeroArmado = "0000000000000" + numCm;
                break;
            case 1:
                numeroArmado = "00000000000000" + numCm;
                break;
            default:
                break;
        }

        return numeroArmado;

    }

    /**
     * Metodo para validar la disponibilidad de las tecnologias seleccionadas en
     * WorkForce
     *
     * @param otDireccion orden de trabajo para Hhpp seleccionada
     * @Author Orlando Velasquez
     * @throws co.com.claro.mgl.error.ApplicationException
     * @return boolean disponibilidad de las tecnologias
     */
    public boolean validarTecnologiasWorkForce(OtHhppMgl otDireccion) throws ApplicationException {
        CmtExtendidaTecnologiaMgl mglExtendidaTecnologia;

        List<OtHhppTecnologiaMgl> otHhppTecnologiaMglList
                = otHhppTecnologiaMglManager.findOtHhppTecnologiaByOtHhppId(otDireccion.getOtHhppId());

        if (otHhppTecnologiaMglList != null) {
            if (!otHhppTecnologiaMglList.isEmpty()) {
                for (OtHhppTecnologiaMgl tecnologia : otHhppTecnologiaMglList) {
                    mglExtendidaTecnologia = extendidaTecnologiaMglManager.findBytipoTecnologiaObj(
                            tecnologia.getTecnoglogiaBasicaId());
                    if (mglExtendidaTecnologia.getNomTecnologiaOFSC() == null) {
                        return false;
                    }
                }
            }
        }
        return true;

    }

    /**
     * Metodo para realizar el reagendamiento en una orden de trabajo Autor:
     * Victor Bocanegra
     *
     * @param capacidad nuevo capacidad de agendamiento
     * @param agenda agenda la cual se va a modificar
     * @param razonReagenda razon del reagendamiento
     * @param comentarios comentarios adicionales.
     * @param usuario usuario de sesion que reagenda
     * @param perfil perfil del usuario
     * @throws ApplicationException Excepcion lanzada al reagendar
     */
    public void reagendar(CapacidadAgendaDto capacidad,
            MglAgendaDireccion agenda,
            String razonReagenda, String comentarios, String usuario, int perfil)
            throws ApplicationException {
        try {
            String reagenda = "REAGENDA";
            List<CmtExtendidaTipoTrabajoMgl> extendidaTipoTrabajoMgl = null;
            CmtExtendidaTipoTrabajoMgl extendida = null;
            MgwTypeReAgendarRequestElement request = new MgwTypeReAgendarRequestElement();
            List<co.com.claro.mgw.agenda.reagendar.TypePropertyElement> lstPropElements
                    = new ArrayList<>();

            String formattedDate = sdf.format(new Date());
            MgwHeadElement head = new MgwHeadElement(formattedDate,
                    agenda.getOrdenTrabajo().getOtHhppId().toString());

            request.setHead(head);

            co.com.claro.mgw.agenda.reagendar.TypeDataElement data
                    = new co.com.claro.mgw.agenda.reagendar.TypeDataElement();

            co.com.claro.mgw.agenda.reagendar.TypeAppointmentElement appoiment
                    = new co.com.claro.mgw.agenda.reagendar.TypeAppointmentElement();
            TypeCommandElement command = new TypeCommandElement();

            command.setDate(ValidacionUtil.dateFormatyyyyMMdd(capacidad.getDate()));
            command.setType("0");
            
            String location = "";

            CmtComunidadRr comunidadRr = null;
            NodoMgl nodoMgl;

            if (agenda.getNodoMgl() != null) {
                nodoMgl = agenda.getNodoMgl();
                comunidadRr = nodoMgl.getComId();
            } else {
                throw new ApplicationException("No existe nodo de cobertura para la tecnologia de la Ot");
            }

            /*obtenemos la extendida de la cual se extra el location de la combinacion de 
            comunidad vs tipoOt */
            
            if (agenda.getOrdenTrabajo() != null && agenda.getOrdenTrabajo().getTipoOtHhppId() != null
                    && agenda.getOrdenTrabajo().getTipoOtHhppId().getTipoTrabajoOFSC() != null) {                
                extendidaTipoTrabajoMgl = extendidaTipoTrabajoMglManager.
                        findBytipoTrabajoObjAndCom(comunidadRr, agenda.getOrdenTrabajo().getTipoOtHhppId().getTipoTrabajoOFSC());
            }
            
            //obtenemos el location
            List<ParametrosMgl> parametrosFlagList;
            parametrosFlagList = parametrosMglManager.findByAcronimo(Constant.AGENDAMIENTO_OFSC_ONOFF);                
            ParametrosMgl parametrosFlag = parametrosFlagList.get(0);
            if(parametrosFlag.getParValor().equals(Constant.AGENDAMIENTO_OFSC_VALOR) && capacidad.getBucket() != null && !capacidad.getBucket().isEmpty()) {
                location = capacidad.getBucket(); 
            }else {                    
                if (extendidaTipoTrabajoMgl != null && !extendidaTipoTrabajoMgl.isEmpty()) {
                    extendida = extendidaTipoTrabajoMgl.get(0);
                    location = extendida.getLocationCodigo().trim();
                }
            } 
           /** JDHT**/  

            command.setExternal_id(location);

            String numeroArmadoCmOFSC;

            if (agenda.getOrdenTrabajo().getTipoOtHhppId().getIsMultiagenda().equalsIgnoreCase("S") 
                    && agenda.getCuentaMatrizMgl() != null) {         
                numeroArmadoCmOFSC = armarNumeroCmOfsc(new BigDecimal(agenda.getCuentaMatrizMgl().getNumeroCuenta().toString()));

            } else {
                numeroArmadoCmOFSC = armarNumeroCmOfsc(agenda.getOrdenTrabajo().getDireccionId().getDirId());

            }
            
            appoiment.setAppt_number(agenda.getOfpsOtId());
            appoiment.setCustomer_number(numeroArmadoCmOFSC);
            appoiment.setAction_if_completed("Si");

            String tipoWork = agenda.getOrdenTrabajo().getTipoOtHhppId().getTipoTrabajoOFSC().getAbreviatura();
            appoiment.setWorktype_label(tipoWork);

            String slaWindow;
            if (agenda.getTecnicoAnticipado().equalsIgnoreCase("Y") && capacidad.getHoraInicio() != null) {
                TypePropertyElement horaIni = new TypePropertyElement("deliveryWindowStart", capacidad.getHoraInicio());
                agenda.setIdentificacionTecnico(capacidad.getResourceId());
                agenda.setNombreTecnico(capacidad.getNombreTecnico());
                agenda.setIdentificacionAliado(capacidad.getAliadoId());
                lstPropElements.add(horaIni);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                slaWindow = df.format(capacidad.getDate()) + " " + capacidad.getHoraInicio();
                TypePropertyElement slaWindowStart = new TypePropertyElement(
                "slaWindowStart", slaWindow);
                lstPropElements.add(slaWindowStart);
            } else {
                appoiment.setTime_slot(capacidad.getTimeSlot());
            }

            command.setAppointment(appoiment);

            co.com.claro.mgw.agenda.reagendar.TypeCommandsArray commands
                    = new co.com.claro.mgw.agenda.reagendar.TypeCommandsArray();
            commands.add(command);
            data.setCommands(commands);
            request.setData(data);

            co.com.claro.mgw.agenda.reagendar.TypePropertyElement razonRea
                    = new co.com.claro.mgw.agenda.reagendar.TypePropertyElement("XA_RazonDeReagendacion", razonReagenda);
            lstPropElements.add(razonRea);

            co.com.claro.mgw.agenda.reagendar.TypePropertyElement comentariosOt
                    = new co.com.claro.mgw.agenda.reagendar.TypePropertyElement("order_comments", comentarios);
            lstPropElements.add(comentariosOt);
            
            co.com.claro.mgw.agenda.reagendar.TypePropertyElement reagendaEstado 
                    = new co.com.claro.mgw.agenda.reagendar.TypePropertyElement("XA_EstadoMER_Reag", reagenda);
            lstPropElements.add(reagendaEstado);

            co.com.claro.mgw.agenda.reagendar.TypePropertiesArray propertiesArray
                    = new co.com.claro.mgw.agenda.reagendar.TypePropertiesArray(lstPropElements);
            appoiment.setProperties(propertiesArray);
            
            ParametrosMgl saveRequestResponses = parametrosMglManager.
                    findByAcronimoName(Constant.ACTIVAR_SAVE_REQUEST_RESPONSES);
            String save = "";
            if (saveRequestResponses != null) {
                save = saveRequestResponses.getParValor();
            }
            if(save.equalsIgnoreCase("1")){
                //esta activo el flag para almacenar las peticiones
                RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                String json;
                json = JSONUtils.objetoToJson(request);
                requestResponsesAgeMgl.setTipoPeticion("Request");
                requestResponsesAgeMgl.setTipoOperacion("Reagendar");
                requestResponsesAgeMgl.setContenidoPeticion(json);
                requestResponsesAgeMgl.setNumeroOrden(agenda.getOfpsOtId());
                requestResponsesAgeMgl.setFechaCreacion(new Date()); 
                requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                if(requestResponsesAgeMgl.getRequestRespId()!= null){
                    LOGGER.info("registro almacenado satisfatoriamente");
                }
                
            }
            
            MgwTypeReAgendarResponseElement response = clientAgendamiento.callWebService(
                    EnumeratorServiceName.AGENDAMIENTO_REAGENDAR,
                    MgwTypeReAgendarResponseElement.class, request);

            if ((response.getReport() == null || (response.getReport().getMessage() != null
                    && "".equals(response.getReport().getMessage().getDescription())))
                    && (response.getData().getCommands().getCommand().get(0).getAppointment().getReport() != null
                    && !Constant.ERROR.equals(response.getData().getCommands().getCommand()
                            .get(0).getAppointment().getReport().getMessage().get(0).getResult()))) {

                LOGGER.info(String.format("Se ha reagendado la agenda para la ot %s", agenda.getOfpsOtId()));
                
                if (save.equalsIgnoreCase("1")) {
                    //esta activo el flag para almacenar las peticiones
                    RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                    String json;
                    json = JSONUtils.objetoToJson(response);
                    requestResponsesAgeMgl.setTipoPeticion("Response");
                    requestResponsesAgeMgl.setTipoOperacion("Reagendar");
                    requestResponsesAgeMgl.setContenidoPeticion(json);
                    requestResponsesAgeMgl.setNumeroOrden(agenda.getOfpsOtId());
                    requestResponsesAgeMgl.setFechaCreacion(new Date()); 
                    requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                    if (requestResponsesAgeMgl.getRequestRespId() != null) {
                        LOGGER.info("registro almacenado satisfatoriamente");
                    }

                }

                CmtBasicaMgl estadoAgBasicaMgl = basicaMglManager.
                        findByCodigoInternoApp(Constant.ESTADO_AGENDA_REAGENDADA);

                agenda.setBasicaIdEstadoAgenda(estadoAgBasicaMgl);
                agenda.setTimeSlot(capacidad.getTimeSlot());
                agenda.setFechaReagenda(capacidad.getDate());
                agenda.setRazonReagenda(razonReagenda);

                AgendamientoHhppMglManager agendamientoMglManager = new AgendamientoHhppMglManager();

                agendamientoMglManager.update(agenda, usuario, perfil);
                CmtOnyxResponseDto onyxResponseDto = returnInfoOnixHhpp(agenda);
                agenda.setCmtOnyxResponseDto(onyxResponseDto);
                agenda.setHoraInicio(capacidad.getHoraInicio());
                try {
                    cargarInformacionForEnvioNotificacion(agenda, 2);
                } catch (ApplicationException ex) {
                    String msn = "Ocurrio un error al momento de "
                            + "enviar notificacion de reagendamiento: " + ex.getMessage() + "";
                    LOGGER.info(msn);
                }
            } else {
                String error = response.getReport() != null
                        ? response.getReport().getMessage().getDescription()
                        : response.getData().getCommands().getCommand().get(0).
                                getAppointment().getReport().getMessage().get(0).getDescription();
                String json;
                json = JSONUtils.objetoToJson(request);
                if (save.equalsIgnoreCase("1")) {
                    //esta activo el flag para almacenar las peticiones
                    RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                    requestResponsesAgeMgl.setTipoPeticion("Responses");
                    requestResponsesAgeMgl.setTipoOperacion("Reagendar");
                    String err;
                    err = JSONUtils.objetoToJson(response);
                    requestResponsesAgeMgl.setContenidoPeticion(err);
                    requestResponsesAgeMgl.setNumeroOrden(agenda.getOfpsOtId());
                    requestResponsesAgeMgl.setFechaCreacion(new Date()); 
                    requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                    if (requestResponsesAgeMgl.getRequestRespId() != null) {
                        LOGGER.info("registro almacenado satisfatoriamente");
                    }

                }
                throw new ApplicationException(error + "$" + json);
            }

        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            LOGGER.error(ERROR_REAGENDA, ex);
            throw new ApplicationException(ex.getMessage());
        }
    }

    /**
     * Metodo para realizar la actualizacion de una agenda en OFSC y MGL Autor:
     * Victor Bocanegra
     *
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que se va actualizar
     * en OFSC y MGL
     * @param usuario que ejecuta la actualizacion
     * @param perfil del usuario
     * @return CmtAgendamientoMgl agenda actualizada
     * @throws ApplicationException Excepcion lanzada al actulizar
     */
    public MglAgendaDireccion updateAgendaMgl(
            MglAgendaDireccion agendaMgl, String usuario, int perfil)
            throws ApplicationException {
        try {

            String subTipoWork;
            List<TypePropertyElementUp> lstPropElements = new ArrayList<>();

            CmtExtendidaTecnologiaMgl mglExtendidaTecnologia = null;
            String nombreRed = "";
            CmtBasicaMgl unidadGesBasica;
            String undGestion = "NA";
            CmtBasicaMgl areaBasica;
            String area = "NA";
            CmtBasicaMgl zonaBasica;
            String zona = "NA";
            CmtBasicaMgl distritoBasica;
            String distrito = "NA";
            List<CmtNotasOtHhppMgl> notasOt;
            OtHhppMglManager otHhppMglManager = new OtHhppMglManager();

            MgwTypeActualizarRequestElement request = new MgwTypeActualizarRequestElement();
            String formattedDate = sdf.format(new Date());
            MgwHeadElement head = new MgwHeadElement(formattedDate,
                    agendaMgl.getOrdenTrabajo().getOtHhppId().toString());
            request.setHead(head);
            TypeDataElementUp data = new TypeDataElementUp();
            TypeAppointmentElementUp appoiment = new TypeAppointmentElementUp();
            OtHhppMgl ot = otHhppMglManager.findById(agendaMgl.getOrdenTrabajo());
            
            String numeroArmadoCmOFSC;          
            if (agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getIsMultiagenda().equalsIgnoreCase("S")
                    && agendaMgl.getCuentaMatrizMgl() != null) {            
                numeroArmadoCmOFSC = armarNumeroCmOfsc(new BigDecimal(agendaMgl.getCuentaMatrizMgl().getNumeroCuenta().toString()));
            } else {
                numeroArmadoCmOFSC = armarNumeroCmOfsc(agendaMgl.getOrdenTrabajo().getDireccionId().getDirId());
            }

            appoiment.setAppt_number(agendaMgl.getOfpsOtId());
            appoiment.setCustomer_number(numeroArmadoCmOFSC);
            appoiment.setTime_slot(agendaMgl.getTimeSlot());

            String tipoWork = agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getTipoTrabajoOFSC().getAbreviatura();
            appoiment.setWorktype_label(tipoWork);

            TypeCommandElementUp command = new TypeCommandElementUp();
            command.setAppointment(appoiment);
            command.setDate(ValidacionUtil.dateFormatyyyyMMdd(agendaMgl.getFechaAgenda()));

            String location = "";
            
            GeograficoPoliticoMgl departamento;
            GeograficoPoliticoMgl centro;
           
            String codigoDanDepto = "";
            String codigoDanCiudad = "";
            String codigoDanCentro = "";
            
            String codigoDanCiudadAux;
            String codigoDanCentroAux;
            
            
            if(agendaMgl.getOrdenTrabajo().getDireccionId().getUbicacion().getGpoIdObj() != null){
                centro = agendaMgl.getOrdenTrabajo().getDireccionId().getUbicacion().getGpoIdObj();
                codigoDanCentroAux=centro.getGeoCodigoDane();
                if (codigoDanCentroAux != null && !codigoDanCentroAux.isEmpty()) {
                    if (codigoDanCentroAux.length() >= 8) {
                        codigoDanCentro = codigoDanCentroAux.substring(5, 8);
                    } else {
                        throw new ApplicationException("El código DANE del Centro Poblado '" + centro.getGpoNombre() + "' no está correctamente configurado (" + codigoDanCentroAux + ").");
                    }
                } else {
                    throw new ApplicationException("No existe un código DANE para el Centro Poblado '" + centro.getGpoNombre() + "'.");
                }

                GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                GeograficoPoliticoMgl ciudad =    geograficoPoliticoManager.findById(agendaMgl.getOrdenTrabajo().getDireccionId().getUbicacion().getGpoIdObj().getGeoGpoId());
                if (ciudad != null) {
                    codigoDanCiudadAux = ciudad.getGeoCodigoDane();
                    if (codigoDanCiudadAux != null && !codigoDanCiudadAux.equalsIgnoreCase("")) {
                        if (codigoDanCiudadAux.length() >= 5) {
                            codigoDanCiudad = codigoDanCiudadAux.substring(2, 5);
                        } else {
                            throw new ApplicationException("El código DANE de la Ciudad '" + ciudad.getGpoNombre() + "' no está correctamente configurado (" + codigoDanCiudadAux + ").");
                        }
                    } else {
                        throw new ApplicationException("No existe un código DANE para la Ciudad '" + ciudad.getGpoNombre() + "'.");
                    }

                   departamento = geograficoPoliticoManager.findById(ciudad.getGeoGpoId());
                   
                   if(departamento != null){
                     codigoDanDepto = departamento.getGeoCodigoDane();   
                   }
                }
                
            }
            
            CmtComunidadRr comunidadRr = null;
            NodoMgl nodoMgl;
            String codNodo = "NA";
            CmtRegionalRr regionalRr;
            String regNodo = "";

           if (agendaMgl.getNodoMgl() != null) {
                mglExtendidaTecnologia = extendidaTecnologiaMglManager.
                        findBytipoTecnologiaObj(agendaMgl.getNodoMgl().getNodTipo());
                nodoMgl = agendaMgl.getNodoMgl();
                codNodo = nodoMgl.getNodCodigo();
                comunidadRr = nodoMgl.getComId();
                regionalRr = comunidadRr != null ? comunidadRr.getRegionalRr() : null;
                regNodo = regionalRr != null ? regionalRr.getCodigoRr() : "";

                if (nodoMgl.getUgeId() != null) {
                    unidadGesBasica = basicaMglManager.findById(nodoMgl.getUgeId());
                    if (unidadGesBasica != null) {
                        undGestion = unidadGesBasica.getCodigoBasica();
                    }
                }
                if (nodoMgl.getAreId() != null) {
                    areaBasica = basicaMglManager.findById(nodoMgl.getAreId());
                    if (areaBasica != null) {
                        area = areaBasica.getCodigoBasica();
                    }
                }
                if (nodoMgl.getZonId() != null) {
                    zonaBasica = basicaMglManager.findById(nodoMgl.getZonId());
                    if (zonaBasica != null) {
                        zona = zonaBasica.getCodigoBasica();
                    }
                }
                if (nodoMgl.getDisId() != null) {
                    distritoBasica = basicaMglManager.findById(nodoMgl.getDisId());
                    if (distritoBasica != null) {
                        distrito = distritoBasica.getCodigoBasica();
                    }
                }
            } else {
                throw new ApplicationException("No existe nodo de cobertura para la tecnologia de la Ot");
            }

            if (mglExtendidaTecnologia != null && mglExtendidaTecnologia.getIdExtTec() != null) {
                nombreRed = mglExtendidaTecnologia.getNomTecnologiaOFSC().getAbreviatura();
            }


            /*obtenemos la extendida de la cual se extra el location de la combinacion de 
            comunidad vs tipoOt */
            List<CmtExtendidaTipoTrabajoMgl> extendidaTipoTrabajoMgl = null;
            CmtExtendidaTipoTrabajoMgl extendida = null;
            if (agendaMgl.getOrdenTrabajo() != null && agendaMgl.getOrdenTrabajo().getTipoOtHhppId() != null
                    && agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getTipoTrabajoOFSC() != null) {
                extendidaTipoTrabajoMgl = extendidaTipoTrabajoMglManager.
                        findBytipoTrabajoObjAndCom(comunidadRr, agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getTipoTrabajoOFSC());
            }
            
            //obtenemos el location
              if (extendidaTipoTrabajoMgl != null && !extendidaTipoTrabajoMgl.isEmpty()) {
                extendida =   extendidaTipoTrabajoMgl.get(0);
                location = extendida.getLocationCodigo().trim();
            }
           /** JDHT**/  

            if (agendaMgl.getComentarioReasigna() != null
                    && !agendaMgl.getComentarioReasigna().isEmpty()) {
        
                command.setExternal_id(location);
                ResourcePreferences resourcePreferences = new ResourcePreferences();
                TypeItemElementCr typeItemElementCr = new TypeItemElementCr();
                typeItemElementCr.setResourceId(agendaMgl.getIdentificacionTecnico());
                typeItemElementCr.setPreferenceType("required");
                List<TypeItemElementCr> lst = new ArrayList<>();
                lst.add(typeItemElementCr);
                resourcePreferences.setItems(lst);
                appoiment.setResourcePreferences(resourcePreferences);
                agendaMgl.setFechaAsigTecnico(new Date());
                
                //Verificamos si viene aliado
                if (agendaMgl.getIdentificacionAliado() != null
                        && !agendaMgl.getIdentificacionAliado().isEmpty()) {
                    CmtAliados aliados;
                    CmtAliadosMglManager aliadosMglManager = new CmtAliadosMglManager();
                    aliados = aliadosMglManager.findByIdAliado(new BigDecimal(agendaMgl.getIdentificacionAliado()));
                    if (aliados != null && aliados.getIdAliado() != null) {
                        agendaMgl.setIdentificacionAliado(aliados.getIdAliado().toString());
                        agendaMgl.setNombreAliado(aliados.getNombre());
                    } else {
                        LOGGER.error("NO HAY INFORMACION DEL ALIADO EN BD.");
                    }
                }
            } else {
                command.setExternal_id(location); //"DCE021"    
            }

            TypeCommandsArrayUp commands = new TypeCommandsArrayUp();
            commands.add(command);
            data.setCommands(commands);
            request.setData(data);

            String obs = "NOTAS DE OT HHPP:";
            CmtNotasOtHhppMglManager notasManager = new CmtNotasOtHhppMglManager();
            notasOt = notasManager.findNotasByOtHhppId(agendaMgl.getOrdenTrabajo().getOtHhppId());

            if (notasOt != null) {
                for (CmtNotasOtHhppMgl notas : notasOt) {
                    obs = obs + " " + notas.getNota();
                }
            }

            if (nodoMgl.getNodId() != null) {
                TypePropertyElementUp tecnologiaNew = new TypePropertyElementUp("XA_CM_Tecnologia", 
                        nodoMgl.getNodTipo().getNombreBasica());
                lstPropElements.add(tecnologiaNew);
            }
                   
            TypePropertyElementUp notCm = new TypePropertyElementUp("activity_notes", obs);
            lstPropElements.add(notCm);
            
            TypePropertyElementUp tecnologia = new TypePropertyElementUp("XA_Red", nombreRed);
            lstPropElements.add(tecnologia);
            
            TypePropertyElementUp daneDep = new TypePropertyElementUp("XA_Departamento", codigoDanDepto);
            lstPropElements.add(daneDep);
            
            TypePropertyElementUp daneCiu = new TypePropertyElementUp("XA_Municipio", codigoDanCiudad);
            lstPropElements.add(daneCiu);
            
            TypePropertyElementUp daneCen = new TypePropertyElementUp("XA_CenPoblado", codigoDanCentro);
            lstPropElements.add(daneCen);

            String fecCreaOt = ValidacionUtil.dateFormatyyyyMMdd(agendaMgl.getOrdenTrabajo().getFechaCreacion());
            TypePropertyElementUp fechaCreaOt = new TypePropertyElementUp("XA_FechaCreacion", fecCreaOt);
            lstPropElements.add(fechaCreaOt);

            TypePropertyElementUp tipOrdSup = new TypePropertyElementUp("XA_TipoOrdenSupervision", "OS");
            lstPropElements.add(tipOrdSup);

            subTipoWork = agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getSubTipoOrdenOFSC().getAbreviatura();

            TypePropertyElementUp subTipoTra = new TypePropertyElementUp("XA_WorkOrderSubtype", subTipoWork);
            lstPropElements.add(subTipoTra);

            TypePropertyElementUp bucket = new TypePropertyElementUp("XA_Bucket", location);
            lstPropElements.add(bucket);

            String direccionHhpp = agendaMgl.getOrdenTrabajo().getDireccionId().getDirFormatoIgac();
            TypePropertyElementUp dirActual = new TypePropertyElementUp("XA_DireccionActual", direccionHhpp);
            lstPropElements.add(dirActual);

            String SLA = String.valueOf(agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getAns());
            String avisoSla = String.valueOf(agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getAnsAviso());
            String avisoSlaFi = avisoSla + " " + "Horas";

            TypePropertyElementUp slaSus = new TypePropertyElementUp("XA_SLASuscriptor", SLA);
            lstPropElements.add(slaSus);

            TypePropertyElementUp estSLA = new TypePropertyElementUp("XA_EstadoSLA", agendaMgl.getOrdenTrabajo().getAns());
            lstPropElements.add(estSLA);

            TypePropertyElementUp indEstSLA = new TypePropertyElementUp("XA_IndicadorEstadoSLA", "E");
            lstPropElements.add(indEstSLA);

            TypePropertyElementUp slaCum = new TypePropertyElementUp("XA_SLACumplimiento", avisoSlaFi);
            lstPropElements.add(slaCum);

            //SOLICITADOS POR EL CLIENTE
            String usuarioCreaOt = agendaMgl.getOrdenTrabajo().getUsuarioCreacion();
            TypePropertyElementUp usuCreOt = new TypePropertyElementUp("XA_UsuarioCrea", usuarioCreaOt);//XA_UsuarioCrea
            lstPropElements.add(usuCreOt);

            TypePropertyElementUp usuCreOtnew = new TypePropertyElementUp("XA_UsuarioCrea1", usuarioCreaOt);
            lstPropElements.add(usuCreOtnew);

            String tipoOt = agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getNombreTipoOt();
            TypePropertyElementUp claseOt = new TypePropertyElementUp("XA_ClaseOrden", tipoOt);
            lstPropElements.add(claseOt);

            String comAscensores = "NA";

            
            if (!comAscensores.equalsIgnoreCase("NA")) {
                TypePropertyElementUp comAscen = new TypePropertyElementUp("XA_CompaniaAsensores", comAscensores);
                lstPropElements.add(comAscen);
            }
                       
            if (!codNodo.equalsIgnoreCase("NA")) {
                TypePropertyElementUp nodo = new TypePropertyElementUp("Node", codNodo);
                lstPropElements.add(nodo);
            }
            
            TypePropertyElementUp regional = new TypePropertyElementUp("XA_Regional", regNodo);
            lstPropElements.add(regional);
            
            List<CmtDireccionDetalladaMgl> direccionDetallada;
            BigDecimal direccion = agendaMgl.getOrdenTrabajo().getDireccionId().getDirId();
            BigDecimal subDireccion = null;
            if (agendaMgl.getOrdenTrabajo().getSubDireccionId() != null) {
                subDireccion = agendaMgl.getOrdenTrabajo().getSubDireccionId().getSdiId();
            }
            direccionDetallada = direccionDetalleManager.findDireccionDetallaByDirIdSdirId(direccion, subDireccion);

            String barrio = null;
            if (direccionDetallada != null && !direccionDetallada.isEmpty()) {
                barrio = direccionDetallada.get(0).getBarrio();
            }

            TypePropertyElementUp barrioCm;
            if (barrio != null) {
                barrioCm = new TypePropertyElementUp("XA_Barrio", barrio);
                lstPropElements.add(barrioCm);
            } 
            
            //pendiente blacklist
            TypePropertyElementUp indRein = new TypePropertyElementUp("XA_IndicadorReincidencias", "N");
            lstPropElements.add(indRein);

            if (!distrito.equalsIgnoreCase("NA")) {
                TypePropertyElementUp distritoSave = new TypePropertyElementUp("XA_Distrito", distrito);
                lstPropElements.add(distritoSave);
            }
            
            if (!undGestion.equalsIgnoreCase("NA")) {
                TypePropertyElementUp undGes = new TypePropertyElementUp("XA_UnidadGestion", undGestion);
                lstPropElements.add(undGes);
            }
                   
            if (!area.equalsIgnoreCase("NA")) {
                TypePropertyElementUp areaSave = new TypePropertyElementUp("XA_Area", area);
                lstPropElements.add(areaSave);
            }
            
            if (!zona.equalsIgnoreCase("NA")) {
                TypePropertyElementUp zonaSave = new TypePropertyElementUp("XA_Zona", zona);
                lstPropElements.add(zonaSave);
            }
            
            String nombreCon = ot != null ? ot.getNombreContacto() : "NA";
            String tel2Con = ot != null ? ot.getTelefonoContacto() : "NA";

            TypePropertyElementUp contacto = new TypePropertyElementUp("XA_Contacto", nombreCon);
            lstPropElements.add(contacto);

            TypePropertyElementUp telUnoCon = new TypePropertyElementUp("XA_Telefonouno", tel2Con);
            lstPropElements.add(telUnoCon);

            TypePropertyElementUp telDosCon = new TypePropertyElementUp("XA_Telefonodos_Contacto", tel2Con);
            lstPropElements.add(telDosCon);

            String origenOrden = parametrosMglManager.findByAcronimo(
                    Constant.PROPIEDAD_ORIGEN_ORDEN)
                    .iterator().next().getParValor();
            TypePropertyElementUp orgOrden = new TypePropertyElementUp("XA_OrigenOrden", origenOrden);
            lstPropElements.add(orgOrden);

            TypePropertyElementUp telefonoDos = new TypePropertyElementUp("XA_Telefonodos", tel2Con);
            lstPropElements.add(telefonoDos);

            String estrato = String.valueOf(agendaMgl.getOrdenTrabajo().getDireccionId().getDirEstrato());

            TypePropertyElementUp estratoCm = new TypePropertyElementUp("XA_Estrato", estrato);
            lstPropElements.add(estratoCm);   
            
             if (agendaMgl.getCmtOnyxResponseDto() != null) {

                if (agendaMgl.getCmtOnyxResponseDto().getNIT_Cliente() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getNIT_Cliente().isEmpty()) {
                    TypePropertyElementUp nitClienteOnix = new TypePropertyElementUp("XA_CM_NitCliente",
                            agendaMgl.getCmtOnyxResponseDto().getNIT_Cliente());
                    lstPropElements.add(nitClienteOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getNombre() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getNombre().isEmpty()) {
                    TypePropertyElementUp nombreClienteOnix = new TypePropertyElementUp("XA_CM_Nombre",
                            agendaMgl.getCmtOnyxResponseDto().getNombre());
                    lstPropElements.add(nombreClienteOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getNombre_OT_Hija() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getNombre_OT_Hija().isEmpty()) {
                    TypePropertyElementUp nombreOtHijaOnix = new TypePropertyElementUp("XA_CM_NombreOTHija",
                            agendaMgl.getCmtOnyxResponseDto().getNombre_OT_Hija());
                    lstPropElements.add(nombreOtHijaOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getOTP() > 0) {
                    TypePropertyElementUp numeroOtPadreOnix = new TypePropertyElementUp("XA_CM_OTP",
                            String.valueOf(agendaMgl.getCmtOnyxResponseDto().getOTP()));
                    lstPropElements.add(numeroOtPadreOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getOTH() > 0) {
                    TypePropertyElementUp numeroOtHijaOnix = new TypePropertyElementUp("XA_CM_OTH",
                            String.valueOf(agendaMgl.getCmtOnyxResponseDto().getOTH()));
                    lstPropElements.add(numeroOtHijaOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getFechaCreacionOTH() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getFechaCreacionOTH().isEmpty()) {
                    TypePropertyElementUp fecCreaOtHijaOnix = new TypePropertyElementUp("XA_CM_FecCreacionHj",
                            agendaMgl.getCmtOnyxResponseDto().getFechaCreacionOTH());
                    lstPropElements.add(fecCreaOtHijaOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getFechaCreacionOTP() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getFechaCreacionOTP().isEmpty()) {
                    TypePropertyElementUp fecCreaOtPadreOnix = new TypePropertyElementUp("XA_CM_FecCreacionPd",
                            agendaMgl.getCmtOnyxResponseDto().getFechaCreacionOTP());
                    lstPropElements.add(fecCreaOtPadreOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getContactoTecnicoOTP() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getContactoTecnicoOTP().isEmpty()) {
                    TypePropertyElementUp conTecOtPadreOnix = new TypePropertyElementUp("XA_CM_Contactos",
                            agendaMgl.getCmtOnyxResponseDto().getContactoTecnicoOTP());
                    lstPropElements.add(conTecOtPadreOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getTelefonoContacto() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getTelefonoContacto().isEmpty()) {
                    TypePropertyElementUp telContOtPadreOnix = new TypePropertyElementUp("XA_CM_Telefono",
                            agendaMgl.getCmtOnyxResponseDto().getTelefonoContacto());
                    lstPropElements.add(telContOtPadreOnix);
                }
                //Pendiente descripcion por parte de OFSC      
                if (agendaMgl.getCmtOnyxResponseDto().getDireccion() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getDireccion().isEmpty()) {
                    TypePropertyElementUp direccionOnix = new TypePropertyElementUp("XA_DirAlterna",
                            agendaMgl.getCmtOnyxResponseDto().getDireccion());
                    lstPropElements.add(direccionOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getSegmento() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getSegmento().isEmpty()) {
                    TypePropertyElementUp segmentoOnix = new TypePropertyElementUp("XA_CM_Segmento",
                            agendaMgl.getCmtOnyxResponseDto().getSegmento());
                    lstPropElements.add(segmentoOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getTipoServicio() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getTipoServicio().isEmpty()) {
                    TypePropertyElementUp tipoServicioOnix = new TypePropertyElementUp("XA_CM_TipoServicio",
                            agendaMgl.getCmtOnyxResponseDto().getTipoServicio());
                    lstPropElements.add(tipoServicioOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getServicios() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getServicios().isEmpty()) {
                    TypePropertyElementUp serviciosOnix = new TypePropertyElementUp("XA_CM_Servicios",
                            agendaMgl.getCmtOnyxResponseDto().getServicios());
                    lstPropElements.add(serviciosOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getRecurrenteMensual() != null) {
                    TypePropertyElementUp recurrenteMenOnix = new TypePropertyElementUp("XA_RentaCliente",
                            agendaMgl.getCmtOnyxResponseDto().getRecurrenteMensual().toString());
                    lstPropElements.add(recurrenteMenOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getCodigoServicio() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getCodigoServicio().isEmpty()) {
                    TypePropertyElementUp codServicioOnix = new TypePropertyElementUp("XA_CM_CodServEnl",
                            agendaMgl.getCmtOnyxResponseDto().getCodigoServicio());
                    lstPropElements.add(codServicioOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getVendedor() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getVendedor().isEmpty()) {
                    TypePropertyElementUp vendedorOnix = new TypePropertyElementUp("XA_CM_Vendedor",
                            agendaMgl.getCmtOnyxResponseDto().getVendedor());
                    lstPropElements.add(vendedorOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getTelefono() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getTelefono().isEmpty()) {
                    TypePropertyElementUp telefonoOnix = new TypePropertyElementUp("XA_CM_Tel",
                            agendaMgl.getCmtOnyxResponseDto().getTelefono());
                    lstPropElements.add(telefonoOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getNotasOTH() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getNotasOTH().isEmpty()) {
                    TypePropertyElementUp notasOtHijaOnix = new TypePropertyElementUp("XA_CM_NotasOTH",
                            agendaMgl.getCmtOnyxResponseDto().getNotasOTH());
                    lstPropElements.add(notasOtHijaOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getEstadoOTH() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getEstadoOTH().isEmpty()) {
                    TypePropertyElementUp estOtHijaOnix = new TypePropertyElementUp("XA_CM_EstOsOth",
                            agendaMgl.getCmtOnyxResponseDto().getEstadoOTH());
                    lstPropElements.add(estOtHijaOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getEstadoOTP() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getEstadoOTP().isEmpty()) {
                    TypePropertyElementUp estOtPadreOnix = new TypePropertyElementUp("XA_CM_EstOpOtp",
                            agendaMgl.getCmtOnyxResponseDto().getEstadoOTP());
                    lstPropElements.add(estOtPadreOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getFechaCompromisoOTP() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getFechaCompromisoOTP().isEmpty()) {
                    TypePropertyElementUp fecComproOtPadreOnix = new TypePropertyElementUp("XA_CM_FechaCom",
                            agendaMgl.getCmtOnyxResponseDto().getFechaCompromisoOTP());
                    lstPropElements.add(fecComproOtPadreOnix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getCodResolucion1OTH() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getCodResolucion1OTH().isEmpty()) {
                    TypePropertyElementUp codResoOtHija1Onix = new TypePropertyElementUp("XA_CM_CodRes1",
                            agendaMgl.getCmtOnyxResponseDto().getCodResolucion1OTH());
                    lstPropElements.add(codResoOtHija1Onix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getCodResolucion2OTH() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getCodResolucion2OTH().isEmpty()) {
                    TypePropertyElementUp codResoOtHija2Onix = new TypePropertyElementUp("XA_CM_CodRes2",
                            agendaMgl.getCmtOnyxResponseDto().getCodResolucion2OTH());
                    lstPropElements.add(codResoOtHija2Onix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getCodResolucion3OTH() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getCodResolucion3OTH().isEmpty()) {
                    TypePropertyElementUp codResoOtHija3Onix = new TypePropertyElementUp("XA_CM_CodRes3",
                            agendaMgl.getCmtOnyxResponseDto().getCodResolucion3OTH());
                    lstPropElements.add(codResoOtHija3Onix);
                }
                if (agendaMgl.getCmtOnyxResponseDto().getCodResolucion4OTH() != null
                        && !agendaMgl.getCmtOnyxResponseDto().getCodResolucion4OTH().isEmpty()) {
                    TypePropertyElementUp codResoOtHija4Onix = new TypePropertyElementUp("XA_CM_CodRes4",
                            agendaMgl.getCmtOnyxResponseDto().getCodResolucion4OTH());
                    lstPropElements.add(codResoOtHija4Onix);
                }
                
                 if (agendaMgl.getOrdenTrabajo().getComplejidadServicio() != null
                        && !agendaMgl.getOrdenTrabajo().getComplejidadServicio().isEmpty()) {
                    TypePropertyElementUp complejidadServ= new TypePropertyElementUp("XA_CM_Complejidad",
                            agendaMgl.getOrdenTrabajo().getComplejidadServicio());
                    lstPropElements.add(complejidadServ);
                }              
                 
                
                //Pendiente coido resolicion ot padre por parte de OFSC      

            }
             
             

            TypePropertiesArrayUp propertiesArrayUp = new TypePropertiesArrayUp(lstPropElements);
            appoiment.setProperties(propertiesArrayUp);
            
             ParametrosMgl saveRequestResponses = parametrosMglManager.
                    findByAcronimoName(Constant.ACTIVAR_SAVE_REQUEST_RESPONSES);
            String save = "";
            if (saveRequestResponses != null) {
                save = saveRequestResponses.getParValor();
            }
            if(save.equalsIgnoreCase("1")){
                //esta activo el flag para almacenar las peticiones
                RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                String json;
                json = JSONUtils.objetoToJson(request);
                requestResponsesAgeMgl.setTipoPeticion("Request");
                requestResponsesAgeMgl.setTipoOperacion("Actualizar");
                requestResponsesAgeMgl.setContenidoPeticion(json);
                requestResponsesAgeMgl.setNumeroOrden(agendaMgl.getOfpsOtId());
                requestResponsesAgeMgl.setFechaCreacion(new Date()); 
                requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                if(requestResponsesAgeMgl.getRequestRespId()!= null){
                    LOGGER.info("registro almacenado satisfatoriamente");
                }
                
            }

            MgwTypeActualizarResponseElement response = clientAgendamiento.callWebService(
                    EnumeratorServiceName.AGENDAMIENTO_ACTUALIZAR,
                    MgwTypeActualizarResponseElement.class, request);

            if ((response.getReport() == null || (response.getReport().getMessage() != null
                    && "".equals(response.getReport().getMessage().getDescription())))
                    && (response.getData().getCommands().getCommand().get(0).getAppointment().getReport() != null
                    && !Constant.ERROR.equals(response.getData().getCommands().getCommand()
                            .get(0).getAppointment().getReport().getMessage().getResult()))) {

                LOGGER.info(String.format("Se ha modificado la agenda numero: %s", agendaMgl.getOfpsOtId()));
               
                if (save.equalsIgnoreCase("1")) {
                    //esta activo el flag para almacenar las peticiones
                    RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                    String json;
                    json = JSONUtils.objetoToJson(response);
                    requestResponsesAgeMgl.setTipoPeticion("Response");
                    requestResponsesAgeMgl.setTipoOperacion("Actualizar");
                    requestResponsesAgeMgl.setContenidoPeticion(json);
                    requestResponsesAgeMgl.setNumeroOrden(agendaMgl.getOfpsOtId());
                    requestResponsesAgeMgl.setFechaCreacion(new Date()); 
                    requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                    if (requestResponsesAgeMgl.getRequestRespId() != null) {
                        LOGGER.info("registro almacenado satisfatoriamente");
                    }

                }

                return manager.update(agendaMgl, usuario, perfil);

            }
            String error = response.getReport() != null
                    ? response.getReport().getMessage().getDescription()
                    : response.getData().getCommands().getCommand().get(0).getAppointment().getReport().getMessage().getDescription();
            String json;
            json = JSONUtils.objetoToJson(request);
            
            if (save.equalsIgnoreCase("1")) {
                //esta activo el flag para almacenar las peticiones
                RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                requestResponsesAgeMgl.setTipoPeticion("Responses");
                requestResponsesAgeMgl.setTipoOperacion("Actualizar");
                String err;
                err = JSONUtils.objetoToJson(response);
                requestResponsesAgeMgl.setContenidoPeticion(err);
                requestResponsesAgeMgl.setNumeroOrden(agendaMgl.getOfpsOtId());
                requestResponsesAgeMgl.setFechaCreacion(new Date()); 
                requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                if (requestResponsesAgeMgl.getRequestRespId() != null) {
                    LOGGER.info("registro almacenado satisfatoriamente");
                }

            }
            throw new ApplicationException(error + "$" + json);

        } catch (ApplicationException e) {
            LOGGER.error(ERROR_ACTUALIZA_AGENDA, e);
            throw new ApplicationException(e.getMessage(), e);

        } catch (UniformInterfaceException | IOException ex) {
            LOGGER.error(ERROR_ACTUALIZA_AGENDA, ex);
            throw new ApplicationException(String.format(ERROR_ACTUALIZA_AGENDA, ex.getMessage()));
        }
    }

    /**
     * Metodo para cancelar el agendamiento de una orden de trabajo. Autor:
     * Victor Bocanegra
     *
     * @param agenda {@link CmtAgendamientoMgl} Agenda de la orden de trabajo a
     * cancelar
     * @param razonCancela razon por la cual cancelan agendamiento
     * @param comentarios comentarios adicionales.
     * @param usuario usuario de sesion que cancela
     * @param perfil perfil del usuario
     * @throws ApplicationException Excepcion lanzada al cancelar
     */
    public void cancelar(MglAgendaDireccion agenda,
            String razonCancela, String comentarios,
            String usuario, int perfil) throws ApplicationException {
        try {
            MgwTypeCancelRequestElement request = new MgwTypeCancelRequestElement();
            List<co.com.claro.mgw.agenda.cancelar.TypePropertyElement> lstPropElements
                    = new ArrayList<>();

            request.setHead(new MgwHeadElement(
                    ValidacionUtil.dateFormatyyyyMMdd(new Date()),
                    agenda.getOrdenTrabajo().getOtHhppId().toString()));

            co.com.claro.mgw.agenda.cancelar.TypeAppointmentElement appoiment = new co.com.claro.mgw.agenda.cancelar.TypeAppointmentElement();

            appoiment.setAppt_number(agenda.getOfpsOtId());
            appoiment.setTime_slot(agenda.getTimeSlot());
            appoiment.setDate(ValidacionUtil.dateFormatyyyyMMdd(agenda.getFechaAgenda()));

            String location = "";

            CmtComunidadRr comunidadRr = null;
            NodoMgl nodoMgl;

            if (agenda.getNodoMgl() != null) {
                nodoMgl = agenda.getNodoMgl();
                comunidadRr = nodoMgl.getComId();
            } else {
                throw new ApplicationException("No existe nodo de cobertura para la tecnologia de la Ot");
            }

            /*obtenemos la extendida de la cual se extra el location de la combinacion de 
            comunidad vs tipoOt */
             List<CmtExtendidaTipoTrabajoMgl> extendidaTipoTrabajoMgl = null;
             CmtExtendidaTipoTrabajoMgl extendida = null;
            if (agenda.getOrdenTrabajo() != null && agenda.getOrdenTrabajo().getTipoOtHhppId() != null
                    && agenda.getOrdenTrabajo().getTipoOtHhppId().getTipoTrabajoOFSC() != null) {                
                extendidaTipoTrabajoMgl = extendidaTipoTrabajoMglManager.
                        findBytipoTrabajoObjAndCom(comunidadRr, agenda.getOrdenTrabajo().getTipoOtHhppId().getTipoTrabajoOFSC());
            }
            
            //obtenemos el location
            List<ParametrosMgl> parametrosFlagList;
            parametrosFlagList = parametrosMglManager.findByAcronimo(Constant.AGENDAMIENTO_OFSC_ONOFF);                
            ParametrosMgl parametrosFlag = parametrosFlagList.get(0);
            if (extendidaTipoTrabajoMgl != null && !extendidaTipoTrabajoMgl.isEmpty()
                && !parametrosFlag.getParValor().equals(Constant.AGENDAMIENTO_OFSC_VALOR)){ 
                extendida =   extendidaTipoTrabajoMgl.get(0);
                location = extendida.getLocationCodigo().trim();
                appoiment.setExternal_id(location);
            }
           /** JDHT**/  
           

            String tipoWork = agenda.getOrdenTrabajo().getTipoOtHhppId().getTipoTrabajoOFSC().getAbreviatura();
            appoiment.setWorktype_label(tipoWork);

            co.com.claro.mgw.agenda.cancelar.TypeCommandsArray commands
                    = new co.com.claro.mgw.agenda.cancelar.TypeCommandsArray();

            commands.add(new co.com.claro.mgw.agenda.cancelar.TypeCommandElement(appoiment));

            request.setData(new co.com.claro.mgw.agenda.cancelar.TypeDataElement(commands));

            co.com.claro.mgw.agenda.cancelar.TypePropertyElement razonRea
                    = new co.com.claro.mgw.agenda.cancelar.TypePropertyElement("XA_RazonDeCancelacion", razonCancela);
            lstPropElements.add(razonRea);

            co.com.claro.mgw.agenda.cancelar.TypePropertyElement comentariosOt = new co.com.claro.mgw.agenda.cancelar.TypePropertyElement("order_comments", comentarios);
            lstPropElements.add(comentariosOt);

            co.com.claro.mgw.agenda.cancelar.TypePropertiesArray propertiesArray = new co.com.claro.mgw.agenda.cancelar.TypePropertiesArray(lstPropElements);

            appoiment.setProperties(propertiesArray);
            
               ParametrosMgl saveRequestResponses = parametrosMglManager.
                    findByAcronimoName(Constant.ACTIVAR_SAVE_REQUEST_RESPONSES);
            String save = "";
            if (saveRequestResponses != null) {
                save = saveRequestResponses.getParValor();
            }
            if(save.equalsIgnoreCase("1")){
                //esta activo el flag para almacenar las peticiones
                RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                String json;
                json = JSONUtils.objetoToJson(request);
                requestResponsesAgeMgl.setTipoPeticion("Request");
                requestResponsesAgeMgl.setTipoOperacion("Cancelar");
                requestResponsesAgeMgl.setContenidoPeticion(json);
                requestResponsesAgeMgl.setNumeroOrden(agenda.getOfpsOtId());
                requestResponsesAgeMgl.setFechaCreacion(new Date()); 
                requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                if(requestResponsesAgeMgl.getRequestRespId()!= null){
                    LOGGER.info("registro almacenado satisfatoriamente");
                }
                
            }
            
            MgwTypeCancelResponseElement response = clientAgendamiento.callWebService(
                    EnumeratorServiceName.AGENDAMIENTO_CANCELAR,
                    MgwTypeCancelResponseElement.class, request);

            if ((response.getReport() == null || (response.getReport().getMessage() != null
                    && "".equals(response.getReport().getMessage().getDescription())))
                    && (response.getData().getCommands().getCommand().get(0).getAppointment().getReport() != null
                    && !Constant.ERROR.equals(response.getData().getCommands().getCommand()
                            .get(0).getAppointment().getReport().getMessage().get(0).getResult()))) {
                
                if (save.equalsIgnoreCase("1")) {
                    //esta activo el flag para almacenar las peticiones
                    RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                    String json;
                    json = JSONUtils.objetoToJson(response);
                    requestResponsesAgeMgl.setTipoPeticion("Response");
                    requestResponsesAgeMgl.setTipoOperacion("Cancelar");
                    requestResponsesAgeMgl.setContenidoPeticion(json);
                    requestResponsesAgeMgl.setNumeroOrden(agenda.getOfpsOtId());
                    requestResponsesAgeMgl.setFechaCreacion(new Date()); 
                    requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                    if (requestResponsesAgeMgl.getRequestRespId() != null) {
                        LOGGER.info("registro almacenado satisfatoriamente");
                    }

                }

                LOGGER.info(String.format("Se ha cancelado la agenda para la ot %s", agenda.getOfpsOtId()));

                CmtBasicaMgl estadoAgBasicaMgl = basicaMglManager.
                        findByCodigoInternoApp(Constant.ESTADO_AGENDA_CANCELADA);

                agenda.setBasicaIdEstadoAgenda(estadoAgBasicaMgl);
                agenda.setRazonCancela(razonCancela);
             
                manager.update(agenda, usuario, perfil);
                CmtOnyxResponseDto onyxResponseDto = returnInfoOnixHhpp(agenda);
                agenda.setCmtOnyxResponseDto(onyxResponseDto);
                agenda.setComentarioCancela(comentarios);
                try {
                    cargarInformacionForEnvioNotificacion(agenda, 3);
                } catch (ApplicationException ex) {
                    String msn = "Ocurrio un error al momento de "
                            + "enviar notificacion de cancelamiento: " + ex.getMessage() + "";
                    LOGGER.info(msn);
                }
            } else {
                String error = response.getReport() != null
                        ? response.getReport().getMessage().getDescription()
                        : response.getData().getCommands().getCommand().get(0).
                                getAppointment().getReport().getMessage().get(0).getDescription();
                String json;
                json = JSONUtils.objetoToJson(request);

                if (save.equalsIgnoreCase("1")) {
                    //esta activo el flag para almacenar las peticiones
                    RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                    requestResponsesAgeMgl.setTipoPeticion("Responses");
                    requestResponsesAgeMgl.setTipoOperacion("Cancelar");
                    String err;
                    err = JSONUtils.objetoToJson(response);
                    requestResponsesAgeMgl.setContenidoPeticion(err);
                    requestResponsesAgeMgl.setNumeroOrden(agenda.getOfpsOtId());
                    requestResponsesAgeMgl.setFechaCreacion(new Date()); 
                    requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                    if (requestResponsesAgeMgl.getRequestRespId() != null) {
                        LOGGER.info("registro almacenado satisfatoriamente");
                    }

                }
                throw new ApplicationException(error + "$" + json);
            }
        } catch (ApplicationException e) {
            LOGGER.error(ERROR_CANCELAR, e);
            throw new ApplicationException(e.getMessage(), e);

        } catch (UniformInterfaceException | IOException  ex) {
            LOGGER.error(ERROR_CANCELAR, ex);
            throw new ApplicationException(ex.getMessage());
        }
    }

    /**
     * metodo para consultar los documentos adjuntos de una actividad Autor:
     * victor bocanegra
     *
     * @param agenda
     * @param usuario
     * @param perfil
     * @return List<String>
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<String> consultarDocumentos(MglAgendaDireccion agenda, String usuario, int perfil)
            throws ApplicationException {
        
        AgendamientoWorkForceMglManager agendamiento = new AgendamientoWorkForceMglManager();
        return agendamiento.consultarDocumentosHhpp(agenda, usuario, perfil);
        
    }

	public List<BigDecimal> estadosAgendasForClose() {

        List<BigDecimal> idsEstados = new ArrayList<>();

        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        try {
            CmtBasicaMgl agendada = cmtBasicaMglManager.findByCodigoInternoApp(Constant.ESTADO_AGENDA_AGENDADA);
            CmtBasicaMgl reagendada = cmtBasicaMglManager.findByCodigoInternoApp(Constant.ESTADO_AGENDA_REAGENDADA);

            if (agendada != null) {
                idsEstados.add(agendada.getBasicaId());
            }
            if (reagendada != null) {
                idsEstados.add(reagendada.getBasicaId());
            }

        } catch (ApplicationException e) {
            LOGGER.error("Error en estadosAgendaForClose. " + e.getMessage(), e);
        }

        return idsEstados;
    }
    
         
    /**
     * Metodo para consultar tecnicos disponibles para el dia Autor: Victor
     * Bocanegra
     *
     * @param ot orden de trabajo
     * @param nodo para la consulta
     * @param fechas para la consulta
     * @return ApiFindTecnicosResponse
     * @throws ApplicationException Excepcion
     */
    public ApiFindTecnicosResponse consultarTecnicosDisponibles(OtHhppMgl ot, NodoMgl nodo,
            List<String> fechas)
            throws ApplicationException, IOException {

        ApiFindTecnicosRequest request = new ApiFindTecnicosRequest();
        ApiFindTecnicosResponse response;
        CmtExtendidaTecnologiaMgl mglExtendidaTecnologia;
        List<CmtExtendidaTipoTrabajoMgl> extendidaTipoTrabajoMgl = null;
        CmtExtendidaTipoTrabajoMgl extendida = null;
        String subTipoWork;
        String nombreRed = "";
        CmtComunidadRr comunidadRr;


        request.setDate(fechas.get(0));
        List<String> campos = new ArrayList<>();
        campos.add("resourceId");
        campos.add("resourceType");
        campos.add("name");
        campos.add("XR_IdAliado");
        
        Criteria criteria = new Criteria();
        
        String parWorktime = parametrosMglManager.findByAcronimo(
                co.com.telmex.catastro.services.util.Constant.PAR_WORKTIME)
                .iterator().next().getParValor();
        
        String parWorkzone = parametrosMglManager.findByAcronimo(
                co.com.telmex.catastro.services.util.Constant.PAR_WORKZONE)
                .iterator().next().getParValor();
        
        String parWorkSkill = parametrosMglManager.findByAcronimo(
                co.com.telmex.catastro.services.util.Constant.PAR_WORKSKILL)
                .iterator().next().getParValor();
        
        String parResourcePre = parametrosMglManager.findByAcronimo(
                co.com.telmex.catastro.services.util.Constant.PAR_RESOURCE_PREFERENCE)
                .iterator().next().getParValor();
        
        int parWorktimeNum=0;
        int parWorkzoneNum=0;
        int parWorkSkillNum=0;
        int parResourcePreNum=0;
        
        if (parWorktime != null && !parWorktime.isEmpty()) {
            if (isNumeric(parWorktime)) {
                parWorktimeNum = Integer.parseInt(parWorktime);
                if (parWorkzone != null && !parWorkzone.isEmpty()) {
                    if (isNumeric(parWorkzone)) {
                         parWorkzoneNum = Integer.parseInt(parWorkzone);
                        if (parWorkSkill != null && !parWorkSkill.isEmpty()) {
                            if (isNumeric(parWorkSkill)) {
                                parWorkSkillNum = Integer.parseInt(parWorkSkill);
                                if (parResourcePre != null && !parResourcePre.isEmpty()) {
                                    if (isNumeric(parResourcePre)) {
                                        parResourcePreNum = Integer.parseInt(parResourcePre);
                                        LOGGER.info("Parametros configurados correctamente");
                                    }
                                } else {
                                    throw new ApplicationException("No esta configurado el parametro: "
                                            + "" + co.com.telmex.catastro.services.util.Constant.PAR_RESOURCE_PREFERENCE + 
                                            " para la consulta de los tecnicos");                                    
                                }
                            } else {
                                throw new ApplicationException("El valor: " + parWorkSkill + " no es numerico");                                
                            }
                        } else {
                            throw new ApplicationException("No esta configurado el parametro: "
                                    + "" + co.com.telmex.catastro.services.util.Constant.PAR_WORKSKILL + 
                                    " para la consulta de los tecnicos");                            
                        }
                    } else {
                        throw new ApplicationException("El valor: " + parWorkzone + " no es numerico");                        
                    }
                } else {
                    throw new ApplicationException("No esta configurado el parametro: "
                            + "" + co.com.telmex.catastro.services.util.Constant.PAR_WORKZONE + 
                            " para la consulta de los tecnicos");                    
                }
                
            } else {
                throw new ApplicationException("El valor: " + parWorktime + " no es numerico");                
            }
        } else {
            throw new ApplicationException("No esta configurado el parametro: "
                    + "" + co.com.telmex.catastro.services.util.Constant.PAR_WORKTIME + 
                    " para la consulta de los tecnicos");            
        }
        
        criteria.setWorkTime(parWorktimeNum);
        criteria.setWorkZone(parWorkzoneNum);
        criteria.setWorkSkill(parWorkSkillNum);
        criteria.setResourcePreference(parResourcePreNum);
        
        request.setCriteria(criteria);

        request.setFields(campos);
        
        request.setSchedulesToReturn(fechas);
        
        List<String> schedulesFields = new ArrayList<>();
        schedulesFields.add("freeTimeWindows");
        
        request.setSchedulesFields(schedulesFields);

        Activity activity = new Activity();
        activity.setTimeZone("America/Bogota");

        String tipoWork = ot.getTipoOtHhppId().getTipoTrabajoOFSC() != null
                ? ot.getTipoOtHhppId().getTipoTrabajoOFSC().getAbreviatura().trim() : "";

        subTipoWork = ot.getTipoOtHhppId().getSubTipoOrdenOFSC() != null
                ? ot.getTipoOtHhppId().getSubTipoOrdenOFSC().getAbreviatura().trim() : "";

        activity.setActivityType(tipoWork);

        activity.setXA_WorkOrderSubtype(subTipoWork);
        activity.setNode(nodo.getNodCodigo());//"2D1022"

        mglExtendidaTecnologia = extendidaTecnologiaMglManager.findBytipoTecnologiaObj(nodo.getNodTipo());

        if (mglExtendidaTecnologia != null
                && mglExtendidaTecnologia.getIdExtTec() != null) {
            if (mglExtendidaTecnologia.getNomTecnologiaOFSC() != null) {
                nombreRed = mglExtendidaTecnologia.getNomTecnologiaOFSC().getAbreviatura();
            }
        }

        comunidadRr = nodo.getComId() != null ? nodo.getComId() : null;

        String location = "";
        if (comunidadRr != null) {
            extendidaTipoTrabajoMgl = extendidaTipoTrabajoMglManager.
                    findBytipoTrabajoObjAndCom(comunidadRr, ot.getTipoOtHhppId().getTipoTrabajoOFSC());
        }

        if (extendidaTipoTrabajoMgl != null && !extendidaTipoTrabajoMgl.isEmpty()) {
            extendida = extendidaTipoTrabajoMgl.get(0);
            location = extendida.getLocationCodigo().trim();
        }

        activity.setResourceId(location);
        activity.setXA_Red(nombreRed);

        request.setActivity(activity);

        String rutaServicio = parametrosMglManager.findByAcronimo(
                co.com.telmex.catastro.services.util.Constant.BASE_URI_TECNICOS_ANTICIPADOS)
                .iterator().next().getParValor();

        URL url = new URL(rutaServicio);

        String userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                .iterator().next().getParValor();
        String comAutorization = "Basic" + " " + userPwdAutorization;

        ConsumoGenerico consumoGenerico = new ConsumoGenerico("POST", url,
                comAutorization, null, null, null);

        String json = JSONUtils.objetoToJson(request);

        ParametrosMgl saveRequestResponses = parametrosMglManager.
                findByAcronimoName(Constant.ACTIVAR_SAVE_REQUEST_RESPONSES);
        String save = "";
        if (saveRequestResponses != null) {
            save = saveRequestResponses.getParValor();
        }
        if (save.equalsIgnoreCase("1")) {
            //esta activo el flag para almacenar las peticiones
            RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
            requestResponsesAgeMgl.setTipoPeticion("Request");
            requestResponsesAgeMgl.setTipoOperacion("Tecnico");
            requestResponsesAgeMgl.setNumeroOrden(ot.getOtHhppId().toString());
            requestResponsesAgeMgl.setContenidoPeticion(json);
            requestResponsesAgeMgl.setFechaCreacion(new Date());
            requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
            if (requestResponsesAgeMgl.getRequestRespId() != null) {
                LOGGER.info("registro almacenado satisfatoriamente");
            }

        }

        response = consumoGenerico.consultarTecnicos(json, ApiFindTecnicosResponse.class);

        if (save.equalsIgnoreCase("1")) {
            //esta activo el flag para almacenar las peticiones
            RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
            String jsonR;
            jsonR = JSONUtils.objetoToJson(response);
            requestResponsesAgeMgl.setTipoPeticion("Response");
            requestResponsesAgeMgl.setTipoOperacion("Tecnico");
            requestResponsesAgeMgl.setNumeroOrden(ot.getOtHhppId().toString());
            requestResponsesAgeMgl.setContenidoPeticion(jsonR);
            requestResponsesAgeMgl.setFechaCreacion(new Date());
            requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
            if (requestResponsesAgeMgl.getRequestRespId() != null) {
                LOGGER.info("registro almacenado satisfatoriamente");
            }

        }
        
        return response;
    }
    
      public boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            LOGGER.error("Error en isNumeric. " + nfe.getMessage(), nfe);
            return false;
        } catch (Exception nfe) {
            LOGGER.error("Error en isNumeric. " + nfe.getMessage(), nfe);
            return false;
        }
    }
    
    public void cargarInformacionForEnvioNotificacion(MglAgendaDireccion agendaDir, int tipoOperacion)
            throws ApplicationException{

        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        CmtBasicaMglManager basicaMglManager1 = new CmtBasicaMglManager();
        CmtBasicaMgl tipoTrabajo = agendaDir.getOrdenTrabajo().getTipoOtHhppId().getTipoTrabajoOFSC();
        List<CmtTipoBasicaExtMgl> lstCmtTipoBasicaExtMgl = new ArrayList<>();

        CmtTipoBasicaMgl cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                co.com.claro.mgl.utils.Constant.TIPO_BASICA_TIPO_GENERAL_OT);

        CmtTipoBasicaMgl cmtTipoBasicaMglSubTipo = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                co.com.claro.mgl.utils.Constant.TIPO_BASICA_SUB_TIPO_TRABAJO_DIRECCIONES);
        
        CmtBasicaMgl subtipoTrabajo = null;
        
        try{

        List<CmtBasicaMgl> subTipos = basicaMglManager1.findByTipoBasica(cmtTipoBasicaMglSubTipo);

        if (subTipos != null && !subTipos.isEmpty()) {
            for (CmtBasicaMgl basicaMgl : subTipos) {
                if (basicaMgl.getCodigoBasica().equalsIgnoreCase(agendaDir.getSubTipoWorkFoce())) {
                    subtipoTrabajo = basicaMgl;
                }
            }
        }

        if (!cmtTipoBasicaMgl.getListCmtTipoBasicaExtMgl().isEmpty()) {

            for (CmtTipoBasicaExtMgl cmtTipoBasicaExtMgl : cmtTipoBasicaMgl.getListCmtTipoBasicaExtMgl()) {

                switch (tipoOperacion) {
                    case 1:
                        //tipo operacion agendamiento
                        //Determino el tipo de agendamiento
                        if ((agendaDir.getTecnicoAnticipado() != null
                                && agendaDir.getTecnicoAnticipado().equalsIgnoreCase("Y"))||
                                (agendaDir.getAgendaInmediata() != null
                                && agendaDir.getAgendaInmediata().equalsIgnoreCase("Y"))) { //falta implementar agenda inmediata
                            if (cmtTipoBasicaExtMgl.getCampoEntidadAs400().
                                    equalsIgnoreCase("Claro_Cita_Agendada_InmediadaAnticipada")) {
                                lstCmtTipoBasicaExtMgl.add(cmtTipoBasicaExtMgl);
                            }
                        } else {
                            if (cmtTipoBasicaExtMgl.getCampoEntidadAs400().
                                    equalsIgnoreCase("Claro_Cita_Agendada_Construccion")
                                    || cmtTipoBasicaExtMgl.getCampoEntidadAs400().
                                            equalsIgnoreCase("Claro_Cita_Agendada_Tradicional")) {
                                lstCmtTipoBasicaExtMgl.add(cmtTipoBasicaExtMgl);
                            }
                        }
                        break;

                    case 2:
                        //tipo operacion Reagendamiento
                        if (cmtTipoBasicaExtMgl.getCampoEntidadAs400().
                                equalsIgnoreCase("Claro_Cita_Cambio_de_Fecha_Construccion")
                                || cmtTipoBasicaExtMgl.getCampoEntidadAs400().
                                        equalsIgnoreCase("Claro_Cita_Cambio_de_Fecha_Cliente")) {
                            lstCmtTipoBasicaExtMgl.add(cmtTipoBasicaExtMgl);
                        }
                        break;

                    case 3:
                        //tipo operacion cancela agendamiento
                        if (cmtTipoBasicaExtMgl.getCampoEntidadAs400().
                                equalsIgnoreCase("Claro_Cancelacion_Cita_Construccion")
                                || cmtTipoBasicaExtMgl.getCampoEntidadAs400().
                                        equalsIgnoreCase("Claro_Cancelacion_Cita_Cliente")) {
                            lstCmtTipoBasicaExtMgl.add(cmtTipoBasicaExtMgl);
                        }
                        break;

                    case 4:
                        //tipo operacion completado agendamiento
                        if (cmtTipoBasicaExtMgl.getCampoEntidadAs400().
                                equalsIgnoreCase("Claro_Cita_Completada_Construccion")
                                || cmtTipoBasicaExtMgl.getCampoEntidadAs400().
                                        equalsIgnoreCase("Claro_Cita_Completada_Cliente")) {
                            lstCmtTipoBasicaExtMgl.add(cmtTipoBasicaExtMgl);
                        }
                        break;
                    default:
                        break;
                }

            }
        }
        CmtBasicaExtMglManager cmtBasicaExtMglManager = new CmtBasicaExtMglManager();
        //Consulta la basica extendida 
        List<CmtBasicaExtMgl> plantillasTipoTrabajo = cmtBasicaExtMglManager.
                findByTipoBasicaExtByBasica(lstCmtTipoBasicaExtMgl, tipoTrabajo);
        CmtTipoBasicaExtMgl tipoPlantilla = null;
        if (plantillasTipoTrabajo != null && !plantillasTipoTrabajo.isEmpty()) {
            for (CmtBasicaExtMgl cmtBasicaExtMgl : plantillasTipoTrabajo) {
                if (cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {
                    tipoPlantilla = cmtBasicaExtMgl.getIdTipoBasicaExt();
                }
            }
        }
        if (tipoPlantilla != null) {

            switch (tipoOperacion) {
                case 1:
                    //tipo operacion agendamiento
                    if (tipoPlantilla.getCampoEntidadAs400().
                            equalsIgnoreCase("Claro_Cita_Agendada_Construccion")) {

                        Map<String, String> variablesMap = new HashMap<>();
                        String[] variables = co.com.telmex.catastro.services.util.Constant.VARIABLES_AGENDA_TRADICIONAL_CONSTRUCCION.split(",");

                        if (variables != null && variables.length > 0) {
                            variablesMap.put(variables[0], "NA");
                            variablesMap.put(variables[1], agendaDir.getOrdenTrabajo().getDireccionId().getDirFormatoIgac() != null ? 
                                    agendaDir.getOrdenTrabajo().getDireccionId().getDirFormatoIgac() : "NA");
                            variablesMap.put(variables[2], agendaDir.getOrdenTrabajo().
                                    getDireccionId().getUbicacion().getGpoIdObj().getNombreCiudad() != null ? 
                                    agendaDir.getOrdenTrabajo().
                                    getDireccionId().getUbicacion().getGpoIdObj().getNombreCiudad() : "NA");
                            variablesMap.put(variables[3], "NA");
                            variablesMap.put(variables[4], tipoTrabajo.getNombreBasica() != null ? 
                                    tipoTrabajo.getNombreBasica() : "NA");
                            variablesMap.put(variables[5], subtipoTrabajo!= null ? 
                                    subtipoTrabajo.getDescripcion() : "NA");
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String fechAge = df.format(agendaDir.getFechaAgenda());
                            variablesMap.put(variables[6], fechAge != null ? fechAge : "NA");
                            variablesMap.put(variables[7], agendaDir.getTimeSlot() != null ? 
                                    agendaDir.getTimeSlot() : "NA");
                            variablesMap.put(variables[8], agendaDir.getPersonaRecVt() != null &&
                                    !agendaDir.getPersonaRecVt().isEmpty()? 
                                    agendaDir.getPersonaRecVt() : "NA");
                            variablesMap.put(variables[9], agendaDir.getTelPerRecVt() != null &&
                                    !agendaDir.getTelPerRecVt().isEmpty() ? 
                                    agendaDir.getTelPerRecVt() : "NA");
                        }
                        String asunto = "Agendamiento No: " + agendaDir.getOfpsOtId() + "";
                        ResponseNotificationMail response
                                = enviarNotificationForAgendamiento(variablesMap, asunto, agendaDir,
                                        tipoPlantilla.getCampoEntidadAs400());

                        if (response != null && response.getHeaderResponse() != null) {
                            LOGGER.info("isValid: " + response.isIsValid() + "  message: " + response.getMessage() + "");
                        } else if (response != null
                                && response.getCategoryCode() != null && !response.getCategoryCode().isEmpty()) {
                            LOGGER.error("Ocurrio un error enviando el correo: "
                                    + "" + response.getCategoryDescription() + "");
                        }

                    } else if (tipoPlantilla.getCampoEntidadAs400().
                            equalsIgnoreCase("Claro_Cita_Agendada_Tradicional")) {

                        Map<String, String> variablesMap = new HashMap<>();
                        CmtOnyxResponseDto cmtOnyxResponseDto = agendaDir.getCmtOnyxResponseDto();
                            String[] variables = co.com.telmex.catastro.services.util.Constant.VARIABLES_AGENDA_TRADICIONAL.split(",");
                            if (variables != null && variables.length > 0) {
                                variablesMap.put(variables[0], cmtOnyxResponseDto != null && 
                                        !cmtOnyxResponseDto.getNombre().isEmpty() ? 
                                        cmtOnyxResponseDto.getNombre() : "NA");
                                variablesMap.put(variables[1], cmtOnyxResponseDto != null && 
                                        cmtOnyxResponseDto.getNIT_Cliente() != null && 
                                        !cmtOnyxResponseDto.getNIT_Cliente().isEmpty() ? 
                                        cmtOnyxResponseDto.getNIT_Cliente() : "NA");
                                variablesMap.put(variables[2], cmtOnyxResponseDto != null && 
                                        String.valueOf(cmtOnyxResponseDto.getOTH()) != null ? 
                                         String.valueOf(cmtOnyxResponseDto.getOTH()) : "NA");
                                variablesMap.put(variables[3], cmtOnyxResponseDto != null && 
                                        cmtOnyxResponseDto.getDireccion() != null && 
                                        !cmtOnyxResponseDto.getDireccion().isEmpty() ? 
                                        cmtOnyxResponseDto.getDireccion() : "NA");
                                variablesMap.put(variables[4], tipoTrabajo.getNombreBasica() != null ? 
                                        tipoTrabajo.getNombreBasica() : "NA");
                                variablesMap.put(variables[5], subtipoTrabajo != null ? 
                                        subtipoTrabajo.getDescripcion() : "NA");
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String fechAge = df.format(agendaDir.getFechaAgenda());
                                variablesMap.put(variables[6], fechAge != null ? fechAge : "NA");
                                variablesMap.put(variables[7], agendaDir.getTimeSlot() != null ? 
                                        agendaDir.getTimeSlot() : "NA");
                                variablesMap.put(variables[8], agendaDir.getPersonaRecVt() != null &&
                                    !agendaDir.getPersonaRecVt().isEmpty()? 
                                    agendaDir.getPersonaRecVt() : "NA");
                                variablesMap.put(variables[9], agendaDir.getTelPerRecVt() != null &&
                                    !agendaDir.getTelPerRecVt().isEmpty() ? 
                                    agendaDir.getTelPerRecVt() : "NA");
                            }

                        String asunto = "Agendamiento No: " + agendaDir.getOfpsOtId() + "";
                        ResponseNotificationMail response
                                = enviarNotificationForAgendamiento(variablesMap, asunto, 
                                        agendaDir,tipoPlantilla.getCampoEntidadAs400());

                        if (response != null && response.getHeaderResponse() != null) {
                            LOGGER.info("isValid: " + response.isIsValid() + "  message: " + response.getMessage() + "");
                        } else if (response != null
                                && response.getCategoryCode() != null && !response.getCategoryCode().isEmpty()) {
                            LOGGER.error("Ocurrio un error enviando el correo: "
                                    + "" + response.getCategoryDescription() + "");
                        }
                    } else if (tipoPlantilla.getCampoEntidadAs400().
                            equalsIgnoreCase("Claro_Cita_Agendada_InmediadaAnticipada")) {
                        Map<String, String> variablesMap = new HashMap<>();
                        CmtOnyxResponseDto cmtOnyxResponseDto = agendaDir.getCmtOnyxResponseDto();
                            String[] variables = co.com.telmex.catastro.services.util.Constant.VARIABLES_AGENDA_ANTICIPADA_INMEDIATA.split(",");
                            if (variables != null && variables.length > 0) {
                                variablesMap.put(variables[0], cmtOnyxResponseDto != null && 
                                        cmtOnyxResponseDto.getNombre() != null && 
                                        !cmtOnyxResponseDto.getNombre().isEmpty() ? 
                                        cmtOnyxResponseDto.getNombre() : "NA");
                                variablesMap.put(variables[1], cmtOnyxResponseDto != null 
                                        && cmtOnyxResponseDto.getNIT_Cliente() != null && 
                                        !cmtOnyxResponseDto.getNIT_Cliente().isEmpty() ? 
                                        cmtOnyxResponseDto.getNIT_Cliente() : "NA");
                                variablesMap.put(variables[2], cmtOnyxResponseDto != null && 
                                        String.valueOf(cmtOnyxResponseDto.getOTH()) != null ? 
                                        String.valueOf(cmtOnyxResponseDto.getOTH()) : "NA");
                                variablesMap.put(variables[3], cmtOnyxResponseDto != null && 
                                        cmtOnyxResponseDto.getDireccion() != null && 
                                        !cmtOnyxResponseDto.getDireccion().isEmpty()? 
                                        cmtOnyxResponseDto.getDireccion() : "NA");
                                variablesMap.put(variables[4], tipoTrabajo.getNombreBasica() != null ? 
                                        tipoTrabajo.getNombreBasica() : "NA");
                                variablesMap.put(variables[5], subtipoTrabajo != null ? 
                                         subtipoTrabajo.getDescripcion() : "NA");
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String fechAge = df.format(agendaDir.getFechaAgenda());
                                variablesMap.put(variables[6], fechAge != null ? fechAge : "NA");
                                variablesMap.put(variables[7], agendaDir.getTimeSlot() != null ? 
                                        agendaDir.getTimeSlot() : "NA");
                                variablesMap.put(variables[8], agendaDir.getPersonaRecVt() != null &&
                                    !agendaDir.getPersonaRecVt().isEmpty()? 
                                    agendaDir.getPersonaRecVt() : "NA");
                                variablesMap.put(variables[9], agendaDir.getTelPerRecVt() != null &&
                                    !agendaDir.getTelPerRecVt().isEmpty() ? 
                                    agendaDir.getTelPerRecVt() : "NA");
                                variablesMap.put(variables[10], agendaDir.getNombreTecnico() != null 
                                        && !agendaDir.getNombreTecnico().isEmpty() ? 
                                        agendaDir.getNombreTecnico() : "NA");
                                variablesMap.put(variables[11], agendaDir.getIdentificacionTecnico() != null 
                                        && !agendaDir.getIdentificacionTecnico().isEmpty() ? 
                                        agendaDir.getIdentificacionTecnico() : "NA");
                                variablesMap.put(variables[12], agendaDir.getNombreAliado() != null 
                                        && !agendaDir.getNombreAliado().isEmpty() ? 
                                        agendaDir.getNombreAliado()  : "NA");
                            }
                            
                        String asunto = "Agendamiento Anticipada/Inmediata No: " + agendaDir.getOfpsOtId() + "";
                        ResponseNotificationMail response
                                = enviarNotificationForAgendamiento(variablesMap, asunto, 
                                        agendaDir, tipoPlantilla.getCampoEntidadAs400() );

                        if (response != null && response.getHeaderResponse() != null) {
                            LOGGER.info("isValid: " + response.isIsValid() + "  message: " + response.getMessage() + "");
                        } else if (response != null
                                && response.getCategoryCode() != null && !response.getCategoryCode().isEmpty()) {
                            LOGGER.error("Ocurrio un error enviando el correo: "
                                    + "" + response.getCategoryDescription() + "");
                        }
                    }
                    break;
                case 2:
                    //tipo operacion Reagendamiento
                    if (tipoPlantilla.getCampoEntidadAs400().
                            equalsIgnoreCase("Claro_Cita_Cambio_de_Fecha_Cliente")) {

                        Map<String, String> variablesMap = new HashMap<>();
                        CmtOnyxResponseDto cmtOnyxResponseDto = agendaDir.getCmtOnyxResponseDto();
                            String[] variables = co.com.telmex.catastro.services.util.Constant.VARIABLES_REAGENDA.split(",");
                            if (variables != null && variables.length > 0) {
                                variablesMap.put(variables[0], cmtOnyxResponseDto != null && 
                                        cmtOnyxResponseDto.getNombre() != null && 
                                        !cmtOnyxResponseDto.getNombre().isEmpty() ? 
                                        cmtOnyxResponseDto.getNombre() : "NA");
                                variablesMap.put(variables[1], cmtOnyxResponseDto != null && 
                                        cmtOnyxResponseDto.getNIT_Cliente() != null &&
                                        !cmtOnyxResponseDto.getNIT_Cliente().isEmpty() ? 
                                        cmtOnyxResponseDto.getNIT_Cliente() : "NA");
                                variablesMap.put(variables[2], cmtOnyxResponseDto != null && 
                                        String.valueOf(cmtOnyxResponseDto.getOTH()) != null ? 
                                        String.valueOf(cmtOnyxResponseDto.getOTH()) : "NA");
                                variablesMap.put(variables[3], cmtOnyxResponseDto != null &&
                                        cmtOnyxResponseDto.getDireccion() != null && 
                                        !cmtOnyxResponseDto.getDireccion().isEmpty()? 
                                        cmtOnyxResponseDto.getDireccion() : "NA");
                                variablesMap.put(variables[4], tipoTrabajo.getNombreBasica() != null ? 
                                        tipoTrabajo.getNombreBasica() : "NA");
                                variablesMap.put(variables[5], subtipoTrabajo != null ? 
                                        subtipoTrabajo.getDescripcion() : "NA");
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String fechAge = df.format(agendaDir.getFechaAgenda());
                                variablesMap.put(variables[6], fechAge != null ? fechAge : "NA");
                                variablesMap.put(variables[7], agendaDir.getTimeSlotAntes() != null ? 
                                        agendaDir.getTimeSlotAntes() : "NA");
                                String fechReage = df.format(agendaDir.getFechaReagenda());
                                variablesMap.put(variables[8], fechReage != null ? fechReage : "NA");
                                variablesMap.put(variables[9], agendaDir.getTimeSlot() != null ? 
                                        agendaDir.getTimeSlot() : "NA");
                                variablesMap.put(variables[10], agendaDir.getPersonaRecVt() != null &&
                                    !agendaDir.getPersonaRecVt().isEmpty()? 
                                    agendaDir.getPersonaRecVt() : "NA");
                                variablesMap.put(variables[11], agendaDir.getTelPerRecVt() != null &&
                                    !agendaDir.getTelPerRecVt().isEmpty() ? 
                                    agendaDir.getTelPerRecVt() : "NA");
                            }

                        String asunto = "Reagendamiento de agenda No: " + agendaDir.getOfpsOtId() + "";
                        ResponseNotificationMail response
                                = enviarNotificationForAgendamiento(variablesMap, asunto, 
                                        agendaDir,tipoPlantilla.getCampoEntidadAs400());

                        if (response != null && response.getHeaderResponse() != null) {
                            LOGGER.info("isValid: " + response.isIsValid() + "  message: " + response.getMessage() + "");
                        } else if (response != null
                                && response.getCategoryCode() != null && !response.getCategoryCode().isEmpty()) {
                            LOGGER.error("Ocurrio un error enviando el correo: "
                                    + "" + response.getCategoryDescription() + "");
                        }
                    } else if (tipoPlantilla.getCampoEntidadAs400().
                            equalsIgnoreCase("Claro_Cita_Cambio_de_Fecha_Construccion")) {
                        Map<String, String> variablesMap = new HashMap<>();
                        String[] variables = co.com.telmex.catastro.services.util.Constant.VARIABLES_REAGENDA_CONSTRUCCION.split(",");
                        if (variables != null && variables.length > 0) {
                            variablesMap.put(variables[0], "NA");
                            variablesMap.put(variables[1], agendaDir.getOrdenTrabajo().getDireccionId().getDirFormatoIgac() != null ? 
                                    agendaDir.getOrdenTrabajo().getDireccionId().getDirFormatoIgac() : "NA");
                            variablesMap.put(variables[2], agendaDir.getOrdenTrabajo().
                                    getDireccionId().getUbicacion().getGpoIdObj().getNombreCiudad() != null ? 
                                    agendaDir.getOrdenTrabajo().
                                    getDireccionId().getUbicacion().getGpoIdObj().getNombreCiudad() : "NA");
                            variablesMap.put(variables[3], "NA");
                            variablesMap.put(variables[4], tipoTrabajo.getNombreBasica() != null ? 
                                    tipoTrabajo.getNombreBasica() : "NA");
                            variablesMap.put(variables[5], subtipoTrabajo != null ? 
                                    subtipoTrabajo.getDescripcion() : "NA");
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String fechAge = df.format(agendaDir.getFechaAgenda());
                            variablesMap.put(variables[6], fechAge != null ? fechAge : "NA");
                            variablesMap.put(variables[7], agendaDir.getTimeSlotAntes() != null ? 
                                    agendaDir.getTimeSlotAntes() : "NA");
                            String fechReage = df.format(agendaDir.getFechaReagenda());
                            variablesMap.put(variables[8], fechReage != null ? fechReage : "NA");
                            variablesMap.put(variables[9], agendaDir.getTimeSlot() != null ? 
                                    agendaDir.getTimeSlot() : "NA");
                            variablesMap.put(variables[10], agendaDir.getPersonaRecVt() != null &&
                                    !agendaDir.getPersonaRecVt().isEmpty()? 
                                    agendaDir.getPersonaRecVt() : "NA");
                            variablesMap.put(variables[11], agendaDir.getTelPerRecVt() != null &&
                                    !agendaDir.getTelPerRecVt().isEmpty() ? 
                                    agendaDir.getTelPerRecVt() : "NA");
                        }
                        String asunto = "Reagendamiento de agenda No: " + agendaDir.getOfpsOtId() + "";
                        ResponseNotificationMail response
                                = enviarNotificationForAgendamiento(variablesMap, asunto, 
                                        agendaDir,tipoPlantilla.getCampoEntidadAs400());

                        if (response != null && response.getHeaderResponse() != null) {
                            LOGGER.info("isValid: " + response.isIsValid() + "  message: " + response.getMessage() + "");
                        } else if (response != null
                                && response.getCategoryCode() != null && !response.getCategoryCode().isEmpty()) {
                            LOGGER.error("Ocurrio un error enviando el correo: "
                                    + "" + response.getCategoryDescription() + "");
                        }

                    }
                    break;

                case 3:
                    //tipo operacion cancela agendamiento
                    if (tipoPlantilla.getCampoEntidadAs400().
                            equalsIgnoreCase("Claro_Cancelacion_Cita_Cliente")) {

                        Map<String, String> variablesMap = new HashMap<>();
                        CmtOnyxResponseDto cmtOnyxResponseDto = agendaDir.getCmtOnyxResponseDto();
                            String[] variables = co.com.telmex.catastro.services.util.Constant.VARIABLES_CANCELADA.split(",");
                            String[] variablesVis = co.com.telmex.catastro.services.util.Constant.VARIABLES_VISIBLE_TABLAS.split(",");
                            if (variables != null && variables.length > 0) {
                                variablesMap.put(variables[0], cmtOnyxResponseDto != null && 
                                        cmtOnyxResponseDto.getNombre() != null && 
                                        !cmtOnyxResponseDto.getNombre().isEmpty()? 
                                        cmtOnyxResponseDto.getNombre() : "NA");
                                variablesMap.put(variables[1], cmtOnyxResponseDto != null &&
                                        cmtOnyxResponseDto.getNIT_Cliente() != null &&
                                        !cmtOnyxResponseDto.getNIT_Cliente().isEmpty() ? 
                                        cmtOnyxResponseDto.getNIT_Cliente() : "NA");
                                variablesMap.put(variables[2], cmtOnyxResponseDto != null && 
                                        String.valueOf(cmtOnyxResponseDto.getOTH()) != null ? 
                                        String.valueOf(cmtOnyxResponseDto.getOTH()) : "NA");
                                variablesMap.put(variables[3], cmtOnyxResponseDto != null && 
                                        cmtOnyxResponseDto.getDireccion() != null && 
                                        !cmtOnyxResponseDto.getDireccion().isEmpty() ? 
                                        cmtOnyxResponseDto.getDireccion() : "NA");
                                variablesMap.put(variables[4], tipoTrabajo.getNombreBasica() != null ? 
                                        tipoTrabajo.getNombreBasica() : "NA");
                                variablesMap.put(variables[5], subtipoTrabajo != null ? 
                                        subtipoTrabajo.getDescripcion() : "NA");
                                variablesMap.put(variables[6], agendaDir.getComentarioCancela() != null ? 
                                        agendaDir.getComentarioCancela() : "NA");
                                //Consulto las agendas de la orden
                                List<MglAgendaDireccion> agendas = manager.
                                        buscarAgendasByOtAndSubtipopOfsc(agendaDir.getOrdenTrabajo(),
                                                agendaDir.getSubTipoWorkFoce());

                                if (agendas != null && !agendas.isEmpty()) {
                                    int index = 6 + 1;
                                    int visible =0;
                                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                    for (MglAgendaDireccion agendaMgl : agendas) {
                                        
                                        String fechAge = df.format(agendaDir.getFechaAgenda());
                                        variablesMap.put(variables[index], fechAge);
                                        index++;
                                        variablesMap.put(variables[index], agendaMgl.getTimeSlot());
                                        index++;
                                        variablesMap.put(variables[index], agendaMgl.getBasicaIdEstadoAgenda().getNombreBasica());
                                        index++;
                                        variablesMap.put(variablesVis[visible], "contents");
                                        visible++;
                                    }
                                    
                                    while (visible < variablesVis.length) {
                                        variablesMap.put(variablesVis[visible], "none");
                                        visible++;
                                    }
                                }
                            }

                        String asunto = "Cancelamiento de agenda No: " + agendaDir.getOfpsOtId() + "";
                        ResponseNotificationMail response
                                = enviarNotificationForAgendamiento(variablesMap, asunto,
                                        agendaDir, tipoPlantilla.getCampoEntidadAs400());

                        if (response != null && response.getHeaderResponse() != null) {
                            LOGGER.info("isValid: " + response.isIsValid() + "  message: " + response.getMessage() + "");
                        } else if (response != null
                                && response.getCategoryCode() != null && !response.getCategoryCode().isEmpty()) {
                            LOGGER.error("Ocurrio un error enviando el correo: "
                                    + "" + response.getCategoryDescription() + "");
                        }
                    } else if (tipoPlantilla.getCampoEntidadAs400().
                            equalsIgnoreCase("Claro_Cancelacion_Cita_Construccion")) {
                        Map<String, String> variablesMap = new HashMap<>();
                        String[] variables = co.com.telmex.catastro.services.util.Constant.VARIABLES_CANCELADA_CONSTRUCCION.split(",");
                        String[] variablesVis = co.com.telmex.catastro.services.util.Constant.VARIABLES_VISIBLE_TABLAS.split(",");
                        if (variables != null && variables.length > 0) {
                            variablesMap.put(variables[0], "NA");
                            variablesMap.put(variables[1], agendaDir.getOrdenTrabajo().getDireccionId().getDirFormatoIgac() != null ? 
                                    agendaDir.getOrdenTrabajo().getDireccionId().getDirFormatoIgac() : "NA");
                            variablesMap.put(variables[2], agendaDir.getOrdenTrabajo().
                                    getDireccionId().getUbicacion().getGpoIdObj().getNombreCiudad() != null ? 
                                    agendaDir.getOrdenTrabajo().
                                    getDireccionId().getUbicacion().getGpoIdObj().getNombreCiudad() : "NA");
                            variablesMap.put(variables[3], "NA");
                            variablesMap.put(variables[4], tipoTrabajo.getNombreBasica() != null ? 
                                    tipoTrabajo.getNombreBasica() : "NA");
                            variablesMap.put(variables[5], subtipoTrabajo != null ? 
                                    subtipoTrabajo.getDescripcion() : "NA");
                            variablesMap.put(variables[6], agendaDir.getComentarioCancela() != null ? 
                                    agendaDir.getComentarioCancela() : "NA");

                            //Consulto las agendas de la orden
                            List<MglAgendaDireccion> agendas = manager.
                                    buscarAgendasByOtAndSubtipopOfsc(agendaDir.getOrdenTrabajo(),
                                            agendaDir.getSubTipoWorkFoce());

                            if (agendas != null && !agendas.isEmpty()) {
                                int index = 6 + 1;
                                int visible = 0;
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                for (MglAgendaDireccion agendaMgl : agendas) {
                                    String fechAge = df.format(agendaDir.getFechaAgenda());
                                    variablesMap.put(variables[index], fechAge);
                                    index++;
                                    variablesMap.put(variables[index], agendaMgl.getTimeSlot());
                                    index++;
                                    variablesMap.put(variables[index], agendaMgl.getBasicaIdEstadoAgenda().getNombreBasica());
                                    index++;
                                    variablesMap.put(variablesVis[visible], "contents");
                                    visible++;
                                    
                                }
                                while (visible < variablesVis.length) {
                                    variablesMap.put(variablesVis[visible], "none");
                                    visible++;
                                }
                            }

                        }
                        String asunto = "Cancelamiento de agenda No: " + agendaDir.getOfpsOtId() + "";
                        ResponseNotificationMail response
                                = enviarNotificationForAgendamiento(variablesMap, asunto, 
                                        agendaDir, tipoPlantilla.getCampoEntidadAs400());

                        if (response != null && response.getHeaderResponse() != null) {
                            LOGGER.info("isValid: " + response.isIsValid() + "  message: " + response.getMessage() + "");
                        } else if (response != null
                                && response.getCategoryCode() != null && !response.getCategoryCode().isEmpty()) {
                            LOGGER.error("Ocurrio un error enviando el correo: "
                                    + "" + response.getCategoryDescription() + "");
                        }

                    }
                    break;

                case 4:
                    //tipo operacion completado agendamiento
                    if (tipoPlantilla.getCampoEntidadAs400().
                            equalsIgnoreCase("Claro_Cita_Completada_Cliente")) {

                        Map<String, String> variablesMap = new HashMap<>();
                        CmtOnyxResponseDto cmtOnyxResponseDto = agendaDir.getCmtOnyxResponseDto();
                            String[] variables = co.com.telmex.catastro.services.util.Constant.VARIABLES_COMPLETADA.split(",");
                            if (variables != null && variables.length > 0) {
                                variablesMap.put(variables[0], cmtOnyxResponseDto != null && 
                                        cmtOnyxResponseDto.getNombre() != null && 
                                        !cmtOnyxResponseDto.getNombre().isEmpty()? 
                                        cmtOnyxResponseDto.getNombre() : "NA");
                                variablesMap.put(variables[1], cmtOnyxResponseDto != null &&
                                        cmtOnyxResponseDto.getNIT_Cliente() != null && 
                                        !cmtOnyxResponseDto.getNIT_Cliente().isEmpty()? 
                                        cmtOnyxResponseDto.getNIT_Cliente() : "NA");
                                variablesMap.put(variables[2], cmtOnyxResponseDto != null && 
                                        String.valueOf(cmtOnyxResponseDto.getOTH()) != null ? 
                                        String.valueOf(cmtOnyxResponseDto.getOTH()) : "NA");
                                variablesMap.put(variables[3], cmtOnyxResponseDto != null && 
                                        cmtOnyxResponseDto.getDireccion() != null && 
                                        !cmtOnyxResponseDto.getDireccion().isEmpty()? 
                                        cmtOnyxResponseDto.getDireccion() : "NA");
                                variablesMap.put(variables[4], tipoTrabajo.getNombreBasica() != null ? 
                                        tipoTrabajo.getNombreBasica() : "NA");
                                variablesMap.put(variables[5], subtipoTrabajo != null ? 
                                        subtipoTrabajo.getDescripcion() : "NA");
                                variablesMap.put(variables[6], agendaDir.getPersonaRecVt() != null &&
                                    !agendaDir.getPersonaRecVt().isEmpty()? 
                                    agendaDir.getPersonaRecVt() : "NA");
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String fechFin = null;
                                if (agendaDir.getFechaFinVt() != null) {
                                    fechFin = df.format(agendaDir.getFechaFinVt());
                                }
                                variablesMap.put(variables[7], fechFin != null ? fechFin : "NA");
                                DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
                                if (agendaDir.getFechaInivioVt() != null) {
                                    variablesMap.put(variables[8], hourFormat.format(agendaDir.getFechaInivioVt()));
                                } else {
                                    variablesMap.put(variables[8], "NA");
                                }
                                if (agendaDir.getFechaFinVt() != null) {
                                    variablesMap.put(variables[9], hourFormat.format(agendaDir.getFechaFinVt()));
                                } else {
                                    variablesMap.put(variables[9], "NA");
                                }
                                variablesMap.put(variables[10], agendaDir.getIdentificacionTecnico() != null
                                        && !agendaDir.getIdentificacionTecnico().isEmpty()
                                        ? agendaDir.getIdentificacionTecnico() : "NA");
                                variablesMap.put(variables[11], agendaDir.getNombreTecnico() != null &&
                                        !agendaDir.getNombreTecnico().isEmpty() ? 
                                        agendaDir.getNombreTecnico() : "NA");
                                variablesMap.put(variables[12], agendaDir.getNombreAliado() != null &&
                                        !agendaDir.getNombreAliado().isEmpty() ? 
                                        agendaDir.getNombreAliado() : "NA");
                                variablesMap.put(variables[13], agendaDir.getObservacionesTecnico() != null &&
                                        !agendaDir.getObservacionesTecnico().isEmpty()? 
                                        agendaDir.getObservacionesTecnico() : "NA");
                                agendaDir = consultarFirma(agendaDir);
                                variablesMap.put(variables[14], agendaDir.getNameArchivo() != null ? 
                                        agendaDir.getNameArchivo() : "NA");
                            }

                        String asunto = "Completado de agenda No: " + agendaDir.getOfpsOtId() + "";
                        ResponseNotificationMail response
                                = enviarNotificationForAgendamiento(variablesMap, asunto,
                                        agendaDir, tipoPlantilla.getCampoEntidadAs400());

                        if (response != null && response.getHeaderResponse() != null) {
                            LOGGER.info("isValid: " + response.isIsValid() + "  message: " + response.getMessage() + "");
                        } else if (response != null
                                && response.getCategoryCode() != null && !response.getCategoryCode().isEmpty()) {
                            LOGGER.error("Ocurrio un error enviando el correo: "
                                    + "" + response.getCategoryDescription() + "");
                        }
                    } else if (tipoPlantilla.getCampoEntidadAs400().
                            equalsIgnoreCase("Claro_Cita_Completada_Construccion")) {
                        Map<String, String> variablesMap = new HashMap<>();
                        String[] variables = co.com.telmex.catastro.services.util.Constant.VARIABLES_COMPLETADA_CONSTRUCCION.split(",");
                        if (variables != null && variables.length > 0) {
                            variablesMap.put(variables[0], "NA");
                            variablesMap.put(variables[1], agendaDir.getOrdenTrabajo().getDireccionId().getDirFormatoIgac() != null ? 
                                    agendaDir.getOrdenTrabajo().getDireccionId().getDirFormatoIgac() : "NA");
                            variablesMap.put(variables[2], agendaDir.getOrdenTrabajo().
                                    getDireccionId().getUbicacion().getGpoIdObj().getNombreCiudad() != null ? 
                                    agendaDir.getOrdenTrabajo().
                                    getDireccionId().getUbicacion().getGpoIdObj().getNombreCiudad() : "NA");
                            variablesMap.put(variables[3], "NA");
                            variablesMap.put(variables[4], tipoTrabajo.getNombreBasica() != null ? 
                                    tipoTrabajo.getNombreBasica() : "NA");
                            variablesMap.put(variables[5], subtipoTrabajo != null ? 
                           subtipoTrabajo.getDescripcion() : "NA");
                            variablesMap.put(variables[6], agendaDir.getPersonaRecVt() != null &&
                                    !agendaDir.getPersonaRecVt().isEmpty()? 
                                    agendaDir.getPersonaRecVt() : "NA");
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String fechFin = null;
                            if (agendaDir.getFechaFinVt() != null) {
                                fechFin = df.format(agendaDir.getFechaFinVt());
                            }
                            variablesMap.put(variables[7], fechFin != null ? fechFin : "NA");
                            DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
                            if (agendaDir.getFechaInivioVt() != null) {
                                variablesMap.put(variables[8], hourFormat.format(agendaDir.getFechaInivioVt()));
                            } else {
                                variablesMap.put(variables[8], "NA");
                            }
                            if (agendaDir.getFechaFinVt() != null) {
                                variablesMap.put(variables[9], hourFormat.format(agendaDir.getFechaFinVt()));
                            } else {
                                variablesMap.put(variables[9], "NA");
                            }
                            variablesMap.put(variables[10], agendaDir.getIdentificacionTecnico() != null
                                    && !agendaDir.getIdentificacionTecnico().isEmpty()
                                    ? agendaDir.getIdentificacionTecnico() : "NA");
                            variablesMap.put(variables[11], agendaDir.getNombreTecnico() != null
                                    &&       !agendaDir.getNombreTecnico().isEmpty() ? 
                                    agendaDir.getNombreTecnico() : "NA");
                            variablesMap.put(variables[12], agendaDir.getNombreAliado() != null &&
                                    !agendaDir.getNombreAliado().isEmpty()? 
                                    agendaDir.getNombreAliado() : "AN");
                            variablesMap.put(variables[13], agendaDir.getObservacionesTecnico() != null &&
                                    !agendaDir.getObservacionesTecnico().isEmpty()? 
                                    agendaDir.getObservacionesTecnico() : "NA");
                            agendaDir = consultarFirma(agendaDir);
                            variablesMap.put(variables[14], agendaDir.getNameArchivo() != null ? 
                                    agendaDir.getNameArchivo() : "NA");
                        }
                        String asunto = "Completado de agenda  No: " + agendaDir.getOfpsOtId() + "";
                        ResponseNotificationMail response
                                = enviarNotificationForAgendamiento(variablesMap, asunto, 
                                        agendaDir, tipoPlantilla.getCampoEntidadAs400());

                            if (response != null && response.getHeaderResponse() != null) {
                                LOGGER.info("isValid: " + response.isIsValid() + "  message: " + response.getMessage() + "");
                            } else if (response != null
                                    && response.getCategoryCode() != null && !response.getCategoryCode().isEmpty()) {
                                LOGGER.error("Ocurrio un error enviando el correo: "
                                        + "" + response.getCategoryDescription() + "");
                            }

                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (ApplicationException ex) {
            String msg = String.format(ERROR_AGENDA, ex.getMessage());
            LOGGER.error(msg, ex);
            throw new ApplicationException(msg, ex);
        }
    }

    /**
     * Metodo para enviar un correo Autor: Victor Bocanegra
     *
     * @param variables de las plantillas
     * @param subject asunto del correo
     * @param agenda 
     * @param nombrePlantilla nombre de la plantilla que se utilizara para enviar el correo
     * @return ResponseNotificationMail
     * @throws ApplicationException Excepcion
     */
    public ResponseNotificationMail enviarNotificationForAgendamiento(Map<String, String> variables,
            String subject, MglAgendaDireccion agenda,  String nombrePlantilla)
            throws ApplicationException{

        RequestNotificationMail request = new RequestNotificationMail();
        ResponseNotificationMail response = null;

        try{
            
        HeaderRequest headerRequest = new HeaderRequest();
        GregorianCalendar c = new GregorianCalendar();
        Date fecha = new Date();
        c.setTime(fecha);

        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

        headerRequest.setRequestDate(date2.toString());
        headerRequest.setIpApplication("MER");

        Message message = new Message();

        message.setPushType("SINGLE");
        message.setTypeCostumer("9F1AA44D-B90F-E811-80ED-FA163E10DFBE");
        List<String> customerId = new ArrayList<>();
        customerId.add("9F1AA44D-B90F-E811-80ED-FA163E10DFBE"); //Identificacion de la persona a quien envian?
        message.setCustomerId(customerId);

        MessageBox1 messageBox1 = new MessageBox1();

        messageBox1.setMessageChannel("SMTP");

        List<MessageBox> lstMessageBox = new ArrayList<>();
        MessageBox messageBox = new MessageBox();
        messageBox.setCustomerBox(agenda.getEmailPerRecVT());

        lstMessageBox.add(messageBox);
        messageBox1.setMessageBox(lstMessageBox);

        List<MessageBox1> lstMessageBox1 = new ArrayList<>();
        lstMessageBox1.add(messageBox1);

        message.setMessageBox(lstMessageBox1);
     
        String perfil = parametrosMglManager.findByAcronimo(
                co.com.telmex.catastro.services.util.Constant.PERFIL_ENVIO_CORREOS)
                .iterator().next().getParValor();
        
        if (perfil == null) {
            throw new ApplicationException("No se encuentra configurado el parámetro " +
                    co.com.telmex.catastro.services.util.Constant.PERFIL_ENVIO_CORREOS);
        }

        List<String> profileId = new ArrayList<>();
        profileId.add(perfil);

        message.setProfileId(profileId);

        List<String> communicationType = new ArrayList<>();
        communicationType.add("NOTIFICACION_AGENDA");

        message.setCommunicationType(communicationType);
        message.setCommunicationOrigin("FS_BSCS");
        message.setDeliveryReceipts("NO");
        message.setContentType("CONTENTID");
        message.setMessageContent(nombrePlantilla);

        if (agenda.getNameArchivo() != null) {
            List<Attach> lstAttach = new ArrayList<>();
            Attach attach = new Attach();
            attach.setName(agenda.getNameArchivo());
            attach.setType(agenda.getTypeArchivo());
            attach.setContent(agenda.getContentArchivo());
            attach.setEncode("BASE64");
            lstAttach.add(attach);
            message.setAttach(lstAttach);
        }
        
        message.setVariableMap(variables);
        message.setSubject(subject);

        String mesage = JSONUtils.objetoToJson(message);

        request.setHeaderRequest(headerRequest);

            request.setMessage(mesage);

            String rutaServicio = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_ENVIO_CORREOS)
                    .iterator().next().getParValor();

            if (rutaServicio == null) {
                throw new ApplicationException("No se encuentra configurado el parámetro "
                        + co.com.telmex.catastro.services.util.Constant.BASE_URI_ENVIO_CORREOS);
            }

            URL url = new URL(rutaServicio);

            ConsumoGenerico consumoGenerico = new ConsumoGenerico("PUT", url,
                    null, null, null, null);

            String json = JSONUtils.objetoToJson(request);
            
            int timeOut = 10;
            String timeOutServices = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.TIME_OUT_CONSUMO_SERVICES)
                    .iterator().next().getParValor();

            if (timeOutServices != null && !timeOutServices.isEmpty()) {
                timeOut = Integer.parseInt(timeOutServices);
            }
            

            response = consumoGenerico.enviarCorreos(timeOut, json, ResponseNotificationMail.class);

        } catch (ApplicationException | IOException | DatatypeConfigurationException ex) {
            String msg = String.format(ERROR_AGENDA, ex.getMessage());
            LOGGER.error(msg, ex);
            throw new ApplicationException(ex.getMessage());
        }
        return response;
    }

    public CmtOnyxResponseDto returnInfoOnixHhpp(MglAgendaDireccion agendaMgl) throws ApplicationException {

        CmtOnyxResponseDto onyxResponseDto = new CmtOnyxResponseDto();
        onyxResponseDto.setNIT_Cliente("");
        onyxResponseDto.setOTH(0);
        onyxResponseDto.setNombre("");
        onyxResponseDto.setDireccion("");

        OnyxOtCmDirManager onyxOtCmDirManager = new OnyxOtCmDirManager();
        if (agendaMgl.getOrdenTrabajo().getOtHhppId() != null
                && agendaMgl.getOrdenTrabajo().getOnyxOtHija() != null) {

            List<OnyxOtCmDir> listaOnyx = onyxOtCmDirManager.findOnyxByOtHhppAndOtHija
                        (agendaMgl.getOrdenTrabajo().getOtHhppId(),
                                agendaMgl.getOrdenTrabajo().getOnyxOtHija().toString());
            if (listaOnyx != null && !listaOnyx.isEmpty()) {

                OnyxOtCmDir objOnyx = listaOnyx.get(0);
                onyxResponseDto.setNIT_Cliente(objOnyx.getNit_Cliente_Onyx());
                onyxResponseDto.setOTH(Integer.parseInt(objOnyx.getOnyx_Ot_Hija_Dir()));
                onyxResponseDto.setNombre(objOnyx.getNombre_Cliente_Onyx());
                onyxResponseDto.setDireccion(objOnyx.getDireccion_Onyx());
            }
        }
        return onyxResponseDto;
    }
    
    /**
     * metodo para consultar la firma del usuario que atendio la agenda
     * Autor:victor bocanegra
     *
     * @param agenda
     * @return List<String>
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public MglAgendaDireccion consultarFirma(MglAgendaDireccion agenda)
            throws ApplicationException {
        
       String urlArchivo = null;

        try {

            String userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                    .iterator().next().getParValor();
            String comAutorization = "Basic" + " " + userPwdAutorization;
            
            String BASE_URI_RES_ETA = parametrosMglManager.findByAcronimo(
                co.com.telmex.catastro.services.util.Constant.BASE_URI_REST_GET_ETA_ACTUAL)
                .iterator().next().getParValor();

            URL url2 = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.ACTIVITIES
                    + agenda.getOfpsId() + "/" + "csign");

            ConsumoGenerico consumoGenerico1 = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url2,
                    comAutorization, null, null, null);

            //Consumo para bajar de eta
            JSONObject jsonObj = consumoGenerico1.consumirServicioHttpFirma();

            if (jsonObj != null) {
                
                String nameArchivo = jsonObj.getString(ConstansClient.ATR_NAME);

                JSONArray array = jsonObj.getJSONArray(ConstansClient.ATR_LINKS);

                String canonical;

                for (int i = 0; i < array.length(); ++i) {
                    JSONObject rec = array.getJSONObject(i);
                    canonical = rec.getString("rel");
                    if (canonical.equalsIgnoreCase("canonical")) {
                        urlArchivo = rec.getString("href");
                    }
                }

                InputStream in = consumoGenerico1.downloadForWorkforce(urlArchivo);
                LOGGER.error("reading from resource and writing to file...");

                agenda.setNameArchivo(nameArchivo);
                agenda.setTypeArchivo("FILE");

                byte[] bytes = IOUtils.toByteArray(in);
                in.read(bytes, 0, bytes.length);
                String imageStr = Arrays.toString(Base64.encodeBase64(bytes));
                in.close();
                agenda.setContentArchivo(imageStr);
            }

        } catch (ApplicationException | IOException
                | IllegalArgumentException | JSONException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, ex);
        }
        return agenda;
    }
    
    /**
     * Metodo para realizar la actualizacion de una agenda en OFSC por notas  
     * Autor: Victor Bocanegra
     *
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que se va actualizar
     * en OFSC y MGL
     * @param usuario que ejecuta la actualizacion
     * @param perfil del usuario
     * @return CmtAgendamientoMgl agenda actualizada
     * @throws ApplicationException Excepcion lanzada al actulizar
     */
    public MglAgendaDireccion updateAgendasForNotas(
            MglAgendaDireccion agendaMgl, String usuario, int perfil)
            throws ApplicationException {
        try {

            List<TypePropertyElementUp> lstPropElements = new ArrayList<>();
            MgwTypeActualizarRequestElement request = new MgwTypeActualizarRequestElement();
            String formattedDate = sdf.format(new Date());
            List<CmtNotasOtHhppMgl> notasOt;
            MgwHeadElement head = new MgwHeadElement(formattedDate,
                    agendaMgl.getOrdenTrabajo().getOtHhppId().toString());
            request.setHead(head);
            TypeDataElementUp data = new TypeDataElementUp();
            TypeAppointmentElementUp appoiment = new TypeAppointmentElementUp();
            
            String numeroArmadoCmOFSC;          
            if (agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getIsMultiagenda().equalsIgnoreCase("S")
                    && agendaMgl.getCuentaMatrizMgl() != null) {            
                numeroArmadoCmOFSC = armarNumeroCmOfsc(new BigDecimal(agendaMgl.getCuentaMatrizMgl().getNumeroCuenta().toString()));
            } else {
                numeroArmadoCmOFSC = armarNumeroCmOfsc(agendaMgl.getOrdenTrabajo().getDireccionId().getDirId());
            }
       
            appoiment.setAppt_number(agendaMgl.getOfpsOtId());
            appoiment.setCustomer_number(numeroArmadoCmOFSC);

            TypeCommandElementUp command = new TypeCommandElementUp();

            command.setAppointment(appoiment);
            command.setDate(ValidacionUtil.dateFormatyyyyMMdd(agendaMgl.getFechaAgenda()));

            TypeCommandsArrayUp commands = new TypeCommandsArrayUp();
            commands.add(command);
            data.setCommands(commands);
            request.setData(data);

            String actTec = "NOTAS ACTUALIZACION:";
            CmtNotasOtHhppMglManager notasManager = new CmtNotasOtHhppMglManager();
            notasOt = notasManager.findNotasByOtHhppId(agendaMgl.getOrdenTrabajo().getOtHhppId());

            String obs = "";

            if (notasOt != null) {
                for (CmtNotasOtHhppMgl notas : notasOt) {
                    obs = obs + " "+"TIPO NOTA:" +notas.getTipoNotaObj().getNombreBasica()+" " 
                            +"Nota: (" + notas.getNota()+")";
                }
            }
            TypePropertyElementUp notCm = new TypePropertyElementUp("activity_notes", actTec + "|" + obs);
            lstPropElements.add(notCm);

            String origenOrden = parametrosMglManager.findByAcronimo(
                    Constant.PROPIEDAD_ORIGEN_ORDEN)
                    .iterator().next().getParValor();

            TypePropertyElementUp orgOrden = new TypePropertyElementUp("XA_OrigenOrden", origenOrden);
            lstPropElements.add(orgOrden);

            TypePropertiesArrayUp propertiesArrayUp = new TypePropertiesArrayUp(lstPropElements);
            appoiment.setProperties(propertiesArrayUp);

            MgwTypeActualizarResponseElement response = clientAgendamiento.callWebService(
                    EnumeratorServiceName.AGENDAMIENTO_ACTUALIZAR,
                    MgwTypeActualizarResponseElement.class, request);

            if ((response.getReport() == null || (response.getReport().getMessage() != null
                    && "".equals(response.getReport().getMessage().getDescription())))
                    && (response.getData().getCommands().getCommand().get(0).getAppointment().getReport() != null
                    && !Constant.ERROR.equals(response.getData().getCommands().getCommand()
                            .get(0).getAppointment().getReport().getMessage().getResult()))) {

                LOGGER.info(String.format("Se ha modificado la agenda  numero: %s", agendaMgl.getOfpsOtId() + "  por actualizacion de notas"));
                return agendaMgl;
            }
            String error = response.getReport() != null
                    ? response.getReport().getMessage().getDescription()
                    : response.getData().getCommands().getCommand().get(0).getAppointment().getReport().getMessage().getDescription();
            throw new ApplicationException(error);

        } catch (ApplicationException e) {
            LOGGER.error(ERROR_ACTUALIZA_AGENDA, e);
            throw new ApplicationException(e.getMessage(), e);

        } catch (UniformInterfaceException | IOException ex) {
            LOGGER.error(ERROR_ACTUALIZA_AGENDA, ex);
            throw new ApplicationException(ex.getMessage());
        }
    }
    
      /**
     * Metodo para realizar la actualizacion de una agenda en OFSC por contacto  
     * Autor: Victor Bocanegra
     *
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que se va actualizar
     * en OFSC y MGL
     * @param usuario que ejecuta la actualizacion
     * @param perfil del usuario
     * @return CmtAgendamientoMgl agenda actualizada
     * @throws ApplicationException Excepcion lanzada al actulizar
     */
    public MglAgendaDireccion updateAgendasForContacto(
            MglAgendaDireccion agendaMgl, String usuario, int perfil)
            throws ApplicationException {
        try {

            List<TypePropertyElementUp> lstPropElements = new ArrayList<>();
            MgwTypeActualizarRequestElement request = new MgwTypeActualizarRequestElement();
            String formattedDate = sdf.format(new Date());
            List<CmtNotasOtHhppMgl> notasOt;
             MgwHeadElement head = new MgwHeadElement(formattedDate,
                    agendaMgl.getOrdenTrabajo().getOtHhppId().toString());
            request.setHead(head);
            TypeDataElementUp data = new TypeDataElementUp();
            TypeAppointmentElementUp appoiment = new TypeAppointmentElementUp();
            OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
            OtHhppMgl ot = otHhppMglManager.findById(agendaMgl.getOrdenTrabajo());
            
            String numeroArmadoCmOFSC;          
            if (agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getIsMultiagenda().equalsIgnoreCase("S")
                    && agendaMgl.getCuentaMatrizMgl() != null) {            
                numeroArmadoCmOFSC = armarNumeroCmOfsc(new BigDecimal(agendaMgl.getCuentaMatrizMgl().getNumeroCuenta().toString()));
            } else {
                numeroArmadoCmOFSC = armarNumeroCmOfsc(agendaMgl.getOrdenTrabajo().getDireccionId().getDirId());
            }
       
            appoiment.setAppt_number(agendaMgl.getOfpsOtId());
            appoiment.setCustomer_number(numeroArmadoCmOFSC);

            TypeCommandElementUp command = new TypeCommandElementUp();

            command.setAppointment(appoiment);
            command.setDate(ValidacionUtil.dateFormatyyyyMMdd(agendaMgl.getFechaAgenda()));

            TypeCommandsArrayUp commands = new TypeCommandsArrayUp();
            commands.add(command);
            data.setCommands(commands);
            request.setData(data);

            String actTec = "NOTAS ACTUALIZACION CONTACTO:";
            CmtNotasOtHhppMglManager notasManager = new CmtNotasOtHhppMglManager();
            notasOt = notasManager.findNotasByOtHhppId(agendaMgl.getOrdenTrabajo().getOtHhppId());
            String obs = "";

            if (notasOt != null) {
                for (CmtNotasOtHhppMgl notas : notasOt) {
                    obs = obs + " "+"TIPO NOTA:" +notas.getTipoNotaObj().getNombreBasica()+" " 
                            +"Nota: (" + notas.getNota()+")";
                }
            }
            TypePropertyElementUp notCm = new TypePropertyElementUp("activity_notes", actTec + "|" + obs);
            lstPropElements.add(notCm);
            
            String nombreCon = ot != null ? ot.getNombreContacto() : "NA";
            String tel1Con = ot != null ? ot.getTelefonoContacto() : "NA";
            
            TypePropertyElementUp contacto = new TypePropertyElementUp("XA_Contacto", nombreCon);
            lstPropElements.add(contacto);

            TypePropertyElementUp telUnoCon = new TypePropertyElementUp("XA_Telefonouno", tel1Con);
            lstPropElements.add(telUnoCon);

            String origenOrden = parametrosMglManager.findByAcronimo(
                    Constant.PROPIEDAD_ORIGEN_ORDEN)
                    .iterator().next().getParValor();

            TypePropertyElementUp orgOrden = new TypePropertyElementUp("XA_OrigenOrden", origenOrden);
            lstPropElements.add(orgOrden);

            TypePropertiesArrayUp propertiesArrayUp = new TypePropertiesArrayUp(lstPropElements);
            appoiment.setProperties(propertiesArrayUp);

            MgwTypeActualizarResponseElement response = clientAgendamiento.callWebService(
                    EnumeratorServiceName.AGENDAMIENTO_ACTUALIZAR,
                    MgwTypeActualizarResponseElement.class, request);

            if ((response.getReport() == null || (response.getReport().getMessage() != null
                    && "".equals(response.getReport().getMessage().getDescription())))
                    && (response.getData().getCommands().getCommand().get(0).getAppointment().getReport() != null
                    && !Constant.ERROR.equals(response.getData().getCommands().getCommand()
                            .get(0).getAppointment().getReport().getMessage().getResult()))) {

               LOGGER.info(String.format("Se ha modificado la agenda  numero: %s", agendaMgl.getOfpsOtId() + "  por actualizacion de contacto"));
               return manager.update(agendaMgl, usuario, perfil);
            }
            String error = response.getReport() != null
                    ? response.getReport().getMessage().getDescription()
                    : response.getData().getCommands().getCommand().get(0).getAppointment().getReport().getMessage().getDescription();
            throw new ApplicationException(error);

        } catch (ApplicationException e) {
            LOGGER.error(ERROR_ACTUALIZA_AGENDA, e);
            throw new ApplicationException(e.getMessage(), e);

        } catch (UniformInterfaceException | IOException ex) {
            LOGGER.error(ERROR_ACTUALIZA_AGENDA, ex);
            throw new ApplicationException(ex.getMessage());
        }
    }
}
