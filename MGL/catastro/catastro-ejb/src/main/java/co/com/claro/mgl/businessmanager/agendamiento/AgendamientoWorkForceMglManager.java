package co.com.claro.mgl.businessmanager.agendamiento;

import co.claro.wcc.client.gestor.ClientGestorDocumental;
import co.claro.wcc.schema.schemadocument.DocumentType;
import co.claro.wcc.schema.schemadocument.FieldType;
import co.claro.wcc.schema.schemadocument.FileType;
import co.claro.wcc.schema.schemaoperations.ActionStatusEnumType;
import co.claro.wcc.schema.schemaoperations.FileRequestType;
import co.claro.wcc.schema.schemaoperations.RequestType;
import co.claro.wcc.schema.schemaoperations.ResponseType;
import co.claro.wcc.services.search.ordenesservicio.SearchOrdenesServicioFault;
import co.claro.wcc.services.upload.ordenesservicio.UploadOrdenesServicioFault;
import co.com.claro.app.dtos.AppCrearAgendaOtPropertyRequest;
import co.com.claro.atencionInmediata.agenda.request.Data;
import co.com.claro.atencionInmediata.agenda.request.RequestAgendaInmediata;
import co.com.claro.atencionInmediata.agenda.request.ResponseAgendaInmediata;
import co.com.claro.mer.dtos.sp.cursors.CtmGestionSegCMDto;
import co.com.claro.mgl.businessmanager.address.AgendamientoHhppMglManager;
import co.com.claro.mgl.businessmanager.address.CmtNotasOtHhppMglManager;
import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.OtHhppMglManager;
import co.com.claro.mgl.businessmanager.address.OtHhppTecnologiaMglManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.businessmanager.address.RequestAgendaInmediataMglManager;
import co.com.claro.mgl.businessmanager.address.RequestResponsesAgeMglManager;
import co.com.claro.mgl.businessmanager.address.SolicitudManager;
import co.com.claro.mgl.businessmanager.address.TipoOtHhppMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtAgendamientoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtAliadosMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtArchivosVtMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaExtMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtBlackListMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDetalleFlujoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtEstadoIntxExtMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtEstadoxFlujoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtExtendidaTecnologiaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtExtendidaTipoTrabajoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtHorarioRestriccionMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtNotaOtMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtOrdenTrabajoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtOrdenTrabajoRrMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtProyectosMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtSubEdificioMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTecnologiaSubMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTipoBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtVisitaTecnicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CtmGestionSegCMManager;
import co.com.claro.mgl.businessmanager.cm.HomologacionRazonesMglManager;
import co.com.claro.mgl.businessmanager.cm.OnyxOtCmDirManager;
import co.com.claro.mgl.client.https.ConstansClient;
import co.com.claro.mgl.client.https.ConsumoGenerico;
import co.com.claro.mgl.dtos.CapacidadAgendaDto;
import co.com.claro.mgl.dtos.CmtFiltroProyectosDto;
import co.com.claro.mgl.dtos.CmtOnyxResponseDto;
import co.com.claro.mgl.dtos.CmtTecSiteSapRespDto;
import co.com.claro.mgl.dtos.CruReadCtechDto;
import co.com.claro.mgl.dtos.FiltroBusquedaDirecccionDto;
import co.com.claro.mgl.ejb.wsclient.rest.cm.EnumeratorServiceName;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientAgendamiento;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtNotasOtHhppMgl;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HomologacionRazonesMgl;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccion;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.OnyxOtCmDir;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.OtHhppTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.RequestAgendaInmediataMgl;
import co.com.claro.mgl.jpa.entities.RequestResponsesAgeMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendamientoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtAliados;
import co.com.claro.mgl.jpa.entities.cm.CmtArchivosVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBlackListMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDetalleFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoIntxExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTipoTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotaOtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoRrMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
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
import co.com.claro.mgw.agenda.enrutar.AsignarRecursoRequest;
import co.com.claro.mgw.agenda.hardclose.OrderCompleteRequest;
import co.com.claro.mgw.agenda.iniciar_visita.IniciarVisitaRequest;
import co.com.claro.mgw.agenda.nodone.UnrealizedActivityRequest;
import co.com.claro.mgw.agenda.reagendar.TypeAppointmentElement;
import co.com.claro.mgw.agenda.reagendar.TypeCommandElement;
import co.com.claro.mgw.agenda.reagendar.TypeCommandsArray;
import co.com.claro.mgw.agenda.reagendar.TypeDataElement;
import co.com.claro.mgw.agenda.reagendar.TypePropertiesArray;
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
import co.com.claro.mgw.agenda.suspender_visita.SuspenderVisitaRequest;
import co.com.claro.mgw.agenda.util.GetResourcesResponses;
import co.com.claro.mgw.agenda.util.GetTechnologiesResponse;
import co.com.claro.mgw.agenda.util.ServicesAgendamientosResponse;
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
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.telmex.catastro.services.comun.Constantes;
import com.amx.service.automaticclosingonyx.v1.FaultMessage;
import com.amx.service.exp.operation.updateclosingtask.v1.ClientOnix;
import com.amx.service.exp.operation.updateclosingtask.v1.UpdateClosingTaskRequest;
import com.amx.service.exp.operation.updateclosingtask.v1.UpdateClosingTaskResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.util.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Clase para consumir los servicios de agendamiento en <b>WorkForce</b>
 *
 * @author wgavidia
 * @version 21/11/2017
 */
public class AgendamientoWorkForceMglManager {

    private static final Logger LOGGER = LogManager.getLogger(AgendamientoWorkForceMglManager.class);
    private static final String NOTA_ARMADA_ONIX = "Nota armada Onix: ";

    private final ParametrosMglManager parametrosMglManager;
    private final CmtProyectosMglManager cmtProyectosMglManager;
    private final RestClientAgendamiento clientAgendamiento;
    private final String BASE_URI;
    private static final String ERROR_CAPACIDAD = "Error consultando la capacidad";
    private static final String ERROR_AGENDA = "Error realizando el agendamiento por causa: %s";
    private static final String ERROR_REAGENDA = "Error realizando el Reagendamiento por causa: %s";
    private static final String ERROR_ACTUALIZA_AGENDA = "Error Actualizando la agenda: %s";
    private static final String ERROR_CANCELAR = "No se pudo cancelar el agendamiento: %s";
    private final String BASE_URI_RES_ETA;
    private String userPwdAutorization;
    private final TipoOtHhppMglManager tipoOtHhppMglManager = new TipoOtHhppMglManager();
    private final CmtDireccionDetalleMglManager direccionDetalleManager = new CmtDireccionDetalleMglManager();
    private final AgendamientoHhppMglManager agendamientoHhppManager = new AgendamientoHhppMglManager();
    private final OtHhppTecnologiaMglManager otHhppTecnologiaMglManager = new OtHhppTecnologiaMglManager();
    private final CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
    private final CmtOrdenTrabajoRrMglManager ordenTrabajoRrMglManager = new CmtOrdenTrabajoRrMglManager();//FCP
    private final CmtOrdenTrabajoMglManager ordenTrabajoMglManager = new CmtOrdenTrabajoMglManager();//FCP
    private final CmtEstadoxFlujoMglManager estadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
    private final CmtEstadoIntxExtMglManager estadoIntxExtMglManager = new CmtEstadoIntxExtMglManager();
    private final CmtExtendidaTipoTrabajoMglManager extendidaTipoTrabajoMglManager = new CmtExtendidaTipoTrabajoMglManager();
    private final RequestResponsesAgeMglManager requestResponsesAgeMglManager;
    private final CmtTecnologiaSubMglManager tecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
    private final CtmGestionSegCMManager gestionSegCmManager = new CtmGestionSegCMManager();
    private String letraOrigen = "";
    private CmtExtendidaTecnologiaMgl mglExtendidaTecnologia = new CmtExtendidaTecnologiaMgl();
    private final CmtExtendidaTecnologiaMglManager extendidaTecnologiaMglManager = new CmtExtendidaTecnologiaMglManager();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private CmtCuentaMatrizMgl cm = new CmtCuentaMatrizMgl();
    private final CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
    private final String cadenaBlacklist1 = "\u003cblackList\u003e";
    private final String cadenaBlacklist2 = "\u003c/blackList\u003e";
    private final String cadenaEstXTecno1 = "\u003cesttecnologiaMA\u003e";
    private final String cadenaEstXTecno2 = "\u003c/esttecnologiaMA\u003e";
    private final String cadenaHorAtencion1 = "\u003cHorariosAtencion\u003e";
    private final String cadenaHorAtencion2 = "\u003c/HorariosAtencion\u003e";
    private String[] partesMensaje;
    private CmtTecSiteSapRespDto infoCuentaMatrizUbiDto;
    private CruReadCtechDto listInfoCtech;
    private String sitio = "";
    private String ubicaTecnica = "";
    private String idSitio = "";
    private String codDane = "";

    /**
     * Constructor de la clase
     *
     * @throws ApplicationException Excepci&oacute;n lanzada al crear la
     * instancia del cliente ws.
     */
    public AgendamientoWorkForceMglManager() throws ApplicationException {
        parametrosMglManager = new ParametrosMglManager();
        cmtProyectosMglManager = new CmtProyectosMglManager();
        requestResponsesAgeMglManager = new RequestResponsesAgeMglManager();
        BASE_URI = parametrosMglManager.findByAcronimo(
                co.com.telmex.catastro.services.util.Constant.BASE_URI_REST_AGENDA)
                .iterator().next().getParValor();
        clientAgendamiento = new RestClientAgendamiento(BASE_URI);

        BASE_URI_RES_ETA = parametrosMglManager.findByAcronimo(
                co.com.telmex.catastro.services.util.Constant.BASE_URI_REST_GET_ETA_ACTUAL)
                .iterator().next().getParValor();
    }

    /**
     * M&eacute;todo para consultar la disponibilidad en el agendamiento
     *
     * @param ot {@link CmtOrdenTrabajoMgl} ot para la cual se busca la
     * capacidad
     * @param usuario {@link Usuarios} usuario de sesion
     * @param numeroOtOfsc {@link String} numero de la ot en OFSC
     * @param nodo {@link String} nodo para el agendamiento
     * @return {@link List}&lt{@link CapacidadAgendaDto}> Lista con la capacidad
     * existente
     * @throws ApplicationException Excepci&oacute;n lanzada al realizar el
     * consumo del ws
     */
    public List<CapacidadAgendaDto> getCapacidad(CmtOrdenTrabajoMgl ot,
            String numeroOtOfsc, NodoMgl nodo) throws ApplicationException {
        try {
            CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl;
            List<CmtExtendidaTipoTrabajoMgl> extendidaTipoTrabajoMgl = null;
            String subTipoWork = "";
            int diasAmostrar = 0;
            String nombreTecnologiaOFSC = "";
            String nombreRed = "";

            cmtEstadoxFlujoMgl = estadoxFlujoMglManager.
                    findByTipoFlujoAndEstadoInt(ot.getTipoOtObj().getTipoFlujoOt(), ot.getEstadoInternoObj(), ot.getBasicaIdTecnologia());

            if (cmtEstadoxFlujoMgl.getEstadoxFlujoId() != null) {
                if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                    subTipoWork = cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC().getNombreBasica();
                    diasAmostrar = cmtEstadoxFlujoMgl.getDiasAMostrarAgenda();
                }

            }

            String numeroarmado;
            if (numeroOtOfsc == null || numeroOtOfsc.isEmpty()) {
                numeroarmado = armarNumeroOtOfsc(ot);
            } else {
                numeroarmado = numeroOtOfsc;
            }

            mglExtendidaTecnologia = extendidaTecnologiaMglManager.findBytipoTecnologiaObj(ot.getBasicaIdTecnologia());

            if (mglExtendidaTecnologia != null
                    && mglExtendidaTecnologia.getIdExtTec() != null) {
                if (mglExtendidaTecnologia.getNomTecnologiaOFSC() != null) {
                    nombreTecnologiaOFSC = mglExtendidaTecnologia.getNomTecnologiaOFSC().getNombreBasica();
                    nombreRed = mglExtendidaTecnologia.getNomTecnologiaOFSC().getAbreviatura();
                }
            }

            MgwTypeCapacityRequestElement request = new MgwTypeCapacityRequestElement();

            request.setAppt_number(numeroarmado);

            if (diasAmostrar < 7) {
                diasAmostrar = 7;
            }

            request.setDate(sumarDiasAFecha(new Date(), diasAmostrar));

            CmtSubEdificioMgl subEdificioMgl;
            subEdificioMgl = ot.getCmObj().getSubEdificioGeneral();
            CmtCuentaMatrizMgl ccmm  = ot.getCmObj();


            String codNodo = "";
            String comNodo = "";
            CmtComunidadRr comunidadRr = null;

            if (nodo != null) {
                codNodo = nodo.getNodCodigo();
                comNodo = nodo.getComId() != null ? nodo.getComId().getCodigoRr() : "";
                comunidadRr = nodo.getComId() != null ? nodo.getComId() : null;
            }

            String location = "";
            if (comunidadRr != null) {
                extendidaTipoTrabajoMgl = extendidaTipoTrabajoMglManager.
                        findBytipoTrabajoObjAndCom(comunidadRr, ot.getBasicaIdTipoTrabajo());
            }

            List<ParametrosMgl> parametrosFlagList;
            parametrosFlagList = parametrosMglManager.findByAcronimo(Constant.AGENDAMIENTO_OFSC_ONOFF);                
            ParametrosMgl parametrosFlag = parametrosFlagList.get(0);
              if (extendidaTipoTrabajoMgl != null && !extendidaTipoTrabajoMgl.isEmpty()
                  && !parametrosFlag.getParValor().equals(Constant.AGENDAMIENTO_OFSC_VALOR)) {
                CmtExtendidaTipoTrabajoMgl extendida =   extendidaTipoTrabajoMgl.get(0);
                location = extendida.getLocationCodigo().trim();
                request.setLocation(Arrays.asList(location));
            }


            request.setDocument_id(ccmm.getCuentaMatrizId().intValue());
            

            request.setAddress_id(subEdificioMgl.getSubEdificioId().intValue());

            letraOrigen = parametrosMglManager.findByAcronimo(
                    Constant.LETRA_ORIGEN_WFM_MGL)
                    .iterator().next().getParValor();

            String tipoWork = ot.getBasicaIdTipoTrabajo().getAbreviatura();

            ActivityFieldElement tipoTrabajo = new ActivityFieldElement("worktype_label", tipoWork);//"AR"
            ActivityFieldElement subTipoTrabajo = new ActivityFieldElement("XA_WorkOrderSubtype", subTipoWork);//"HFCAR"
            ActivityFieldElement red = new ActivityFieldElement("XA_Red", nombreRed); //"Bidireccional"
            ActivityFieldElement ciudad = new ActivityFieldElement("XA_Idcity", comNodo);//"BOG"
            ActivityFieldElement nodoCap = new ActivityFieldElement("Node", codNodo);//"2B5012"

            request.setActivityField(Arrays.asList(tipoTrabajo, subTipoTrabajo, red, ciudad, nodoCap));

            InfoOrdenAct origen = new InfoOrdenAct("origen", letraOrigen);//"W"
            InfoOrdenAct servAfected = new InfoOrdenAct("servAfectado", "Internet");
            InfoOrdenAct tecnologia = new InfoOrdenAct("tecnologia", nombreTecnologiaOFSC);
            InfoOrdenAct cantServicios = new InfoOrdenAct("cantServicios", "2");
            InfoOrdenAct tipoTrabajoInf = new InfoOrdenAct("tipoTrabajo", tipoWork);
            InfoOrdenAct subTipoTrabajoInf = new InfoOrdenAct("subtipotrabajo", subTipoWork);

            request.setInfoOrderAct(
                    Arrays.asList(origen, servAfected, tecnologia,
                            cantServicios, tipoTrabajoInf, subTipoTrabajoInf));
            
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
                    String error;
                    error = JSONUtils.objetoToJson(response);
                    requestResponsesAgeMgl.setContenidoPeticion(error);
                    requestResponsesAgeMgl.setNumeroOrden(numeroarmado);
                    requestResponsesAgeMgl.setFechaCreacion(new Date());
                    requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                    if (requestResponsesAgeMgl.getRequestRespId() != null) {
                        LOGGER.info("registro almacenado satisfatoriamente");
                    }

                }
                throw new ApplicationException(msg + "$" + json);
            }
            
            if (save.equalsIgnoreCase("1")) {
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
                if (requestResponsesAgeMgl.getRequestRespId() != null) {
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

           //Collections.sort(agenda);
            return agenda;
        } catch (ApplicationException | UniformInterfaceException | IOException e) {
            LOGGER.error(ERROR_CAPACIDAD, e);
            throw new ApplicationException(ERROR_CAPACIDAD + " : " + e.getMessage() + "");
        }
    }

    /**
     * Metodo para realizar el agendamiento de una orden de trabajo
     *
     * @param capacidad {@link CapacidadAgendaDto} registro seleccionado en
     * capacidad
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que se va almacenar en
     * OFSC la agenda
     * @param numeroOTRR N&uacute;mero de la OT de RR.
     * @param requestContextPath Ruta del actual contexto Faces.
     * @param tecnicoAnticipado  si el agendamiento es de tecnico anticipado.
     * @return CmtAgendamientoMgl agenda para almacenar en MGL
     * @throws ApplicationException Excepcion lanzada al agendar
     */
    public CmtAgendamientoMgl agendar(CapacidadAgendaDto capacidad,
            CmtAgendamientoMgl agendaMgl, String numeroOTRR,
            String requestContextPath, boolean tecnicoAnticipado) throws ApplicationException {
        
        String jsonRes = null;
        try {
            
            String direccionCm = null;
            String coorX = null;
            String coorY = null;
            String subTipoWork = "";
            String codNodo = "";
            String nomNodo = "";
            NodoMgl nodoMgl;
            String cerraduraElect;
            BigDecimal cuentaMatrizId;
            CtmGestionSegCMDto gestionSeg;
            CmtBasicaMgl unidadGesBasica;
            String undGestion = "NA";
            CmtBasicaMgl areaBasica;
            String area = "NA";
            CmtBasicaMgl zonaBasica;
            String zona = "NA";
            CmtBasicaMgl distritoBasica;
            String distrito = "NA";
            CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = null;
            List<CmtExtendidaTipoTrabajoMgl> extendidaTipoTrabajoMgl;
            CmtExtendidaTipoTrabajoMgl extendida;
            List<CmtNotaOtMgl> notasOt;
            List<TypePropertyElementCr> lstPropElements = new ArrayList<>();
            CmtArchivosVtMglManager archivosVtMglManager = new CmtArchivosVtMglManager();
            CmtVisitaTecnicaMglManager visitaTecnicaMglManager = new CmtVisitaTecnicaMglManager();

            int cantidadHhppCon;
            HhppMglManager hhppMglManager = new HhppMglManager();

            MgwTypeAgendarRequestElement request = new MgwTypeAgendarRequestElement();
            String formattedDate = sdf.format(new Date());
            MgwHeadElement head = new MgwHeadElement(formattedDate,
                    agendaMgl.getOrdenTrabajo().getIdOt().toString());
            request.setHead(head);
            TypeDataElementCr data = new TypeDataElementCr();
            TypeAppointmentElementCr appoiment = new TypeAppointmentElementCr();

            cm = agendaMgl.getOrdenTrabajo().getCmObj();
            if (cm != null && cm.getDireccionPrincipal() != null
                    && cm.getDireccionPrincipal().getDireccionObj() != null) {
                direccionCm = cm.getDireccionPrincipal().getDireccionObj().getDirFormatoIgac();
                if (cm.getDireccionPrincipal().getDireccionObj().getUbicacion() != null) {
                    coorX = cm.getDireccionPrincipal().getDireccionObj().getUbicacion().getUbiLongitud();
                    coorY = cm.getDireccionPrincipal().getDireccionObj().getUbicacion().getUbiLatitud();
                }
            }

            if (coorX.equals("0") && coorY.equals("0")){
                coorX = null;
                coorY = null;
            }
            if (coorX != null) {
                coorX = coorX.replace(",", ".");
                appoiment.setZip("001");
                appoiment.setAddress(direccionCm);//"Calle: CL 3 Placa: 5-31 Apto: CASA Com: BOG Div: TVC"
            }
            if (coorY != null) {
                coorY = coorY.replace(",", ".");
                appoiment.setZip("001");
                appoiment.setAddress(direccionCm);//"Calle: CL 3 Placa: 5-31 Apto: CASA Com: BOG Div: TVC"
            }

            appoiment.setCoordx(coorX);//"-74.07778841"
            appoiment.setCoordy(coorY); //"4.58979588"

            if (agendaMgl.getOrdenTrabajo() != null
                    && agendaMgl.getOrdenTrabajo().getTipoOtObj() != null) {
                cmtEstadoxFlujoMgl = estadoxFlujoMglManager.
                        findByTipoFlujoAndEstadoInt(agendaMgl.getOrdenTrabajo().getTipoOtObj().getTipoFlujoOt(),
                                agendaMgl.getOrdenTrabajo().getEstadoInternoObj(),
                                agendaMgl.getOrdenTrabajo().getBasicaIdTecnologia());
            }

            if (cmtEstadoxFlujoMgl != null && cmtEstadoxFlujoMgl.getEstadoxFlujoId() != null) {
                if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                    subTipoWork = cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC().getNombreBasica();
                }

            }

            String numeroArmadoOtOFSC;
            if (agendaMgl.getNumeroOrdenInmediata() != null
                    && !agendaMgl.getNumeroOrdenInmediata().isEmpty()) {
                numeroArmadoOtOFSC = agendaMgl.getNumeroOrdenInmediata();
            } else {
                numeroArmadoOtOFSC = armarNumeroOtOfsc(agendaMgl.getOrdenTrabajo());
            }
            
            String numeroArmadoCmOFSC = armarNumeroCmOfsc(cm.getNumeroCuenta());

            appoiment.setAppt_number(numeroArmadoOtOFSC);
      
            if(capacidad.getTimeSlot() != null){
             appoiment.setTime_slot(capacidad.getTimeSlot());   
            }
            appoiment.setCell(cm.getSubEdificioGeneral().getTelefonoPorteria2());

            appoiment.setCustomer_number(numeroArmadoCmOFSC);

            String tipoWork = agendaMgl.getOrdenTrabajo().getBasicaIdTipoTrabajo().getAbreviatura();
            appoiment.setWorktype_label(tipoWork);

            String phone = "NA";
            if (cm.getSubEdificioGeneral().getCompaniaAdministracionObj() != null) {
                if (cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getTelefonos() != null) {
                    phone = cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getTelefonos();
                }
            }
            if (!phone.trim().equalsIgnoreCase("NA")) {
                appoiment.setPhone(phone);
            }
           
            appoiment.setName(cm.getNombreCuenta());//"MODESTA PERNETT"
            appoiment.setState(cm.getDepartamento().getGpoNombre() != null ? cm.getDepartamento().getGpoNombre() : ""); //"BOGOTA D C"

            String email = "NA";
            if (cm.getSubEdificioGeneral().getCompaniaAdministracionObj() != null) {
                if (cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getEmail() != null) {
                    email = cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getEmail();
                }
            }
            if (!email.trim().equalsIgnoreCase("NA")) {
                appoiment.setEmail(email);
            }
            

            TypeCommandElementCr command = new TypeCommandElementCr();
            
            command.setDate(ValidacionUtil.dateFormatyyyyMMdd(capacidad.getDate()));
            command.setFallback_external_id("0");
            command.setType("0");
            String location = "";

            CmtSubEdificioMgl subEdificioMgl;
            subEdificioMgl = cm.getSubEdificioGeneral();
            CmtComunidadRr comunidadRr = null;
            CmtRegionalRr regionalRr;
            String comNodo = "";          
            String regNodo = "";
            
            if (agendaMgl.getNodoMgl() != null) {
                nodoMgl = agendaMgl.getNodoMgl();
                if (nodoMgl != null) {
                    codNodo = nodoMgl.getNodCodigo();
                    nomNodo = nodoMgl.getNodNombre();
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
                }
            } else {
                throw new ApplicationException("No existe nodo de cobertura para la tecnologia de la Ot");
            }
            
            appoiment.setCity(comNodo); //"BOGOTA"     
             
            extendidaTipoTrabajoMgl = extendidaTipoTrabajoMglManager.
                    findBytipoTrabajoObjAndCom(comunidadRr, agendaMgl.getOrdenTrabajo().getBasicaIdTipoTrabajo());

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
                List<CmtAgendamientoMgl> agendas = manager.agendasWithTecnico(agendaMgl.getOrdenTrabajo(),
                        idsEstForUpdate, subTipoWork);

                if (agendas != null && !agendas.isEmpty()) {
                    CmtAgendamientoMgl agendaWithTecnico = agendas.get(0);
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

            notasOt = agendaMgl.getOrdenTrabajo().getNotasList();
            String obs = "";

            if (notasOt != null) {
                for (CmtNotaOtMgl notas : notasOt) {
                    obs = obs + " "+"TIPO NOTA:" +notas.getTipoNotaObj().getNombreBasica()+" " 
                            +"Nota: (" + notas.getNota()+")";
                }
            }
            
            
            String slaWindow;
            if (capacidad.getHoraInicio() != null) {
                TypePropertyElementCr horaIni = new TypePropertyElementCr(
                        "deliveryWindowStart", capacidad.getHoraInicio());
                lstPropElements.add(horaIni);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                slaWindow =  df.format(capacidad.getDate())+" "+capacidad.getHoraInicio();
                TypePropertyElementCr slaWindowStart = new TypePropertyElementCr(
                        "slaWindowStart", slaWindow);
                lstPropElements.add(slaWindowStart);
            }

            TypePropertyElementCr notCm = new TypePropertyElementCr("activity_notes", obs);
            lstPropElements.add(notCm);

            TypePropertyElementCr city = new TypePropertyElementCr("XA_Idcity", comNodo);
            lstPropElements.add(city);

            
            if (appoiment.getCoordx() == null || appoiment.getCoordy() == null) {
                TypePropertyElementCr xa_MerAdress = new TypePropertyElementCr("XA_MerAddress", direccionCm);
                lstPropElements.add(xa_MerAdress);
            }

            
            if (agendaMgl.getOrdenTrabajo() != null) {
                String fecCreaOt = ValidacionUtil.dateFormatyyyyMMdd(agendaMgl.getOrdenTrabajo().getFechaCreacion());
                TypePropertyElementCr fechaCreaOt = new TypePropertyElementCr("XA_FechaCreacion", fecCreaOt);
                lstPropElements.add(fechaCreaOt);
            }
            
            if (esAgendaInmediata.equalsIgnoreCase("Y")) {
                TypePropertyElementCr esAgendaInmediataMER = new TypePropertyElementCr("MER_AGE_INMEDIATA", "1");
                lstPropElements.add(esAgendaInmediataMER);
            }

            TypePropertyElementCr regional = new TypePropertyElementCr("XA_Regional", regNodo);
            lstPropElements.add(regional);

            TypePropertyElementCr tipOrdSup = new TypePropertyElementCr("XA_TipoOrdenSupervision", "OS");
            lstPropElements.add(tipOrdSup);

            TypePropertyElementCr nodo = new TypePropertyElementCr("Node", codNodo);
            lstPropElements.add(nodo);

            TypePropertyElementCr subTipoTra = new TypePropertyElementCr("XA_WorkOrderSubtype", subTipoWork);
            lstPropElements.add(subTipoTra);

            TypePropertyElementCr bucket = new TypePropertyElementCr("XA_Bucket", location);
            lstPropElements.add(bucket);

            String nombreRed = "";

            mglExtendidaTecnologia = extendidaTecnologiaMglManager.
                    findBytipoTecnologiaObj(agendaMgl.getOrdenTrabajo()
                            .getBasicaIdTecnologia());

            if (mglExtendidaTecnologia.getIdExtTec() != null) {
                nombreRed = mglExtendidaTecnologia.getNomTecnologiaOFSC().getAbreviatura();
            }
            TypePropertyElementCr tecnologia = new TypePropertyElementCr("XA_Red", nombreRed);
            lstPropElements.add(tecnologia);

            TypePropertyElementCr nombreNodo = new TypePropertyElementCr("XA_NombreNodo", nomNodo);
            lstPropElements.add(nombreNodo);

            TypePropertyElementCr nombreComCm = new TypePropertyElementCr("XA_NombreCompleto", cm.getNombreCuenta());
            lstPropElements.add(nombreComCm);

            TypePropertyElementCr dirActual = new TypePropertyElementCr("XA_DireccionActual", direccionCm);
            lstPropElements.add(dirActual);

            String avisoSlaFi = null;
            if (agendaMgl.getOrdenTrabajo() != null && agendaMgl.getOrdenTrabajo().getTipoOtObj() != null) {
                String SLA = String.valueOf(agendaMgl.getOrdenTrabajo().getTipoOtObj().getAns());
                String avisoSla = String.valueOf(agendaMgl.getOrdenTrabajo().getTipoOtObj().getAnsAviso());
                avisoSlaFi = avisoSla + " " + "Horas";

                TypePropertyElementCr slaSus = new TypePropertyElementCr("XA_SLASuscriptor", SLA);
                lstPropElements.add(slaSus);
            }

            if (agendaMgl.getOrdenTrabajo() != null) {
                TypePropertyElementCr estSLA = new TypePropertyElementCr("XA_EstadoSLA", agendaMgl.getOrdenTrabajo().getAns());
                lstPropElements.add(estSLA);
            }

            TypePropertyElementCr indEstSLA = new TypePropertyElementCr("XA_IndicadorEstadoSLA", "E");
            lstPropElements.add(indEstSLA);

            if (avisoSlaFi != null) {
                TypePropertyElementCr slaCum = new TypePropertyElementCr("XA_SLACumplimiento", avisoSlaFi);
                lstPropElements.add(slaCum);
            }

            //SOLICITADOS POR EL CLIENTE
            String usuarioCreaOt = agendaMgl.getOrdenTrabajo().getUsuarioCreacion();
            TypePropertyElementCr usuCreOt = new TypePropertyElementCr("XA_UsuarioCrea", usuarioCreaOt);//XA_UsuarioCrea
            lstPropElements.add(usuCreOt);

            TypePropertyElementCr usuCreOtnew = new TypePropertyElementCr("XA_UsuarioCrea1", usuarioCreaOt);
            lstPropElements.add(usuCreOtnew);

            TypePropertyElementCr dirPrincipal = new TypePropertyElementCr("XA_DireccionEdificio", direccionCm);
            lstPropElements.add(dirPrincipal);

            List<CmtDireccionMgl> listDireccionesAlternas;
            CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
            listDireccionesAlternas = cmtDireccionMglManager.findBySubEdificio(subEdificioMgl);

            if (listDireccionesAlternas != null && !listDireccionesAlternas.isEmpty()) {
                int i = 1;
                for (CmtDireccionMgl direccionMgl : listDireccionesAlternas) {

                    if (direccionMgl != null && direccionMgl.getDireccionObj() != null) {
                        if (i == 1) {
                            TypePropertyElementCr dirAlterna = new TypePropertyElementCr("XA_DireccionEdificio2", direccionMgl.getDireccionObj().getDirFormatoIgac());
                            lstPropElements.add(dirAlterna);

                        } else if (i == 2) {
                            TypePropertyElementCr dirOtras = new TypePropertyElementCr("XA_OtrasDirecciones", direccionMgl.getDireccionObj().getDirFormatoIgac());
                            lstPropElements.add(dirOtras);
                        }
                    }

                    i++;
                }
            }
            //Tabla Blacklist
            CmtBlackListMglManager blackListMglManager = new CmtBlackListMglManager();
            List<CmtBlackListMgl> lsBlackListMgls = blackListMglManager.findBySubEdificio(subEdificioMgl);
            String cadenaFinalBlacklist;
            if (lsBlackListMgls != null && !lsBlackListMgls.isEmpty()) {
                String black;
                String blackVarios = "";
                for (CmtBlackListMgl blackListMgl : lsBlackListMgls) {
                    black = "\u003cblack\u003e\u003ccodigo\u003e" + blackListMgl.getBlackListObj().getCodigoBasica() + "\u003c/codigo\u003e\u003cdescripcion\u003e" + blackListMgl.getBlackListObj().getNombreBasica() + "\u003c/descripcion\u003e\u003c/black\u003e";
                    blackVarios = blackVarios + black;
                }
                cadenaFinalBlacklist = cadenaBlacklist1 + blackVarios + cadenaBlacklist2;
                TypePropertyElementCr blacklist = new TypePropertyElementCr("XA_BlackListEdificio", cadenaFinalBlacklist);
                lstPropElements.add(blacklist);
            }
            //Tabla Blacklist
            //Tabla EstadosForTecno
            CmtSubEdificioMglManager subEdificioMglManager = new CmtSubEdificioMglManager();
            List<CmtSubEdificioMgl> subEdificioMgls = subEdificioMglManager.findSubEdificioByCuentaMatriz(cm);
            List<CmtTecnologiaSubMgl> tecnologiasFinales = new ArrayList<>();

            if (subEdificioMgls != null && !subEdificioMgls.isEmpty()) {
                for (CmtSubEdificioMgl subEdificioMgl1 : subEdificioMgls) {
                    List<CmtTecnologiaSubMgl> tecnologias = tecnologiaSubMglManager.
                            findTecnoSubBySubEdi(subEdificioMgl1);
                    if (tecnologias != null && !tecnologias.isEmpty()) {
                        for (CmtTecnologiaSubMgl tecno : tecnologias) {
                            tecnologiasFinales.add(tecno);
                        }
                    }
                }
                String cadenaFinalEstxTecno;

                if (!tecnologiasFinales.isEmpty()) {
                    String estTecno;
                    String estTecnoVarios = "";
                    String tecnNoEdi;
                    String tecnNoEdiFin = "";

                    CmtTipoBasicaMglManager tipoBasicaMglManager = new CmtTipoBasicaMglManager();
                    CmtTipoBasicaMgl tipoBasicaTecno = tipoBasicaMglManager.findByCodigoInternoApp(
                            co.com.claro.mgl.utils.Constant.TIPO_BASICA_TECNOLOGIA);
                    List<CmtBasicaMgl> listTablaBasicaTecnologias = basicaMglManager.
                            findByTipoBasica(tipoBasicaTecno);

                    for (CmtTecnologiaSubMgl tenSubMgl : tecnologiasFinales) {

                        if (listTablaBasicaTecnologias != null) {
                            for (CmtBasicaMgl basiTecBasicaMgl : listTablaBasicaTecnologias) {
                                if (basiTecBasicaMgl.getBasicaId().compareTo(tenSubMgl.getBasicaIdTecnologias().getBasicaId()) == 0) {
                                    if (tenSubMgl.getBasicaIdEstadosTec() != null) {
                                        tecnNoEdi = "\u003c" + basiTecBasicaMgl.getCodigoBasica() + "\u003e" + tenSubMgl.getBasicaIdEstadosTec().getNombreBasica() + "\u003c/" + basiTecBasicaMgl.getCodigoBasica() + "\u003e";
                                    } else {
                                        tecnNoEdi = "\u003c" + basiTecBasicaMgl.getCodigoBasica() + "\u003eNA\u003c/" + basiTecBasicaMgl.getCodigoBasica() + "\u003e";
                                    }
                                } else {
                                    tecnNoEdi = "\u003c" + basiTecBasicaMgl.getCodigoBasica() + "\u003e\u003c/" + basiTecBasicaMgl.getCodigoBasica() + "\u003e";
                                }
                                tecnNoEdiFin = tecnNoEdiFin + tecnNoEdi;
                            }
                        }
                        FiltroBusquedaDirecccionDto filtro = new FiltroBusquedaDirecccionDto();
                        int cantidadHhppSub = hhppMglManager.countListHhppSubEdif(filtro, tenSubMgl.getSubedificioId());
                        String nameSub = "";
                        if(tenSubMgl.getSubedificioId() != null && tenSubMgl.getSubedificioId().getNombreSubedificio() != null){
                         nameSub = tenSubMgl.getSubedificioId().getNombreSubedificio().replace("-", " ");
                        }
                        String estrato = tenSubMgl.getSubedificioId().getEstrato() != null ? tenSubMgl.getSubedificioId().getEstrato().getCodigoBasica():"NG";
                        estTecno = "\u003cesttecnologia\u003e\u003ctorrebloque\u003e" + nameSub + "\u003c/torrebloque\u003e" + tecnNoEdiFin + "\u003cestrato\u003e" + estrato + "\u003c/estrato\u003e\u003ccantidaddehpps\u003e" + cantidadHhppSub + "\u003c/cantidaddehpps\u003e\u003c/esttecnologia\u003e";
                        estTecnoVarios = estTecnoVarios + estTecno;
                    }
                    cadenaFinalEstxTecno = cadenaEstXTecno1 + estTecnoVarios + cadenaEstXTecno2;
                    TypePropertyElementCr estadosxTecnologia = new TypePropertyElementCr("XA_CM_EstadosPorTecnologia", cadenaFinalEstxTecno);
                    lstPropElements.add(estadosxTecnologia);
                }
            }
            //Tabla EstadosForTecno
            //Tabla Horarios de atencion
            CmtHorarioRestriccionMglManager horarioRestriccionMglManager = new CmtHorarioRestriccionMglManager();
            List<CmtHorarioRestriccionMgl> horarioRestriccionMgls = horarioRestriccionMglManager.findByCuentaMatrizId(cm.getCuentaMatrizId());

            String cadenaFinalHorarios;

            if (horarioRestriccionMgls != null && !horarioRestriccionMgls.isEmpty()) {
                String horarios;
                String horariosVarios = "";

                for (CmtHorarioRestriccionMgl horarioRestriccionMgl : horarioRestriccionMgls) {
                    horarios = "\u003cHorario\u003e\u003cdiaInicio\u003e" + horarioRestriccionMgl.getDiaInicio().getDia() + "\u003c/diaInicio\u003e\u003cdiaFin\u003e" + horarioRestriccionMgl.getDiaFin().getDia() + "\u003c/diaFin\u003e\u003choraInicio\u003e" + horarioRestriccionMgl.getHoraInicio() + "\u003c/horaInicio\u003e\u003choraFin\u003e" + horarioRestriccionMgl.getHoraFin() + "\u003c/horaFin\u003e\u003c/Horario\u003e";
                    horariosVarios = horariosVarios + horarios;
                }
                cadenaFinalHorarios = cadenaHorAtencion1 + horariosVarios + cadenaHorAtencion2;
                TypePropertyElementCr horariosAtencion = new TypePropertyElementCr("XA_HorariosAtencion", cadenaFinalHorarios);
                lstPropElements.add(horariosAtencion);
            }
            //Tabla Horarios de atencion

            CmtEstadoIntxExtMgl estadoInternoExterno = null;

            if (agendaMgl.getOrdenTrabajo() != null) {
                estadoInternoExterno = estadoIntxExtMglManager.
                        findByEstadoInterno(agendaMgl.getOrdenTrabajo().getEstadoInternoObj());
            }

            String estadoOt = estadoInternoExterno != null && estadoInternoExterno.getIdEstadoExt() != null ? estadoInternoExterno.getIdEstadoExt().getNombreBasica() : "NA";
            TypePropertyElementCr estOT = new TypePropertyElementCr("XA_EstadoOrdenCRM", estadoOt);
            lstPropElements.add(estOT);

            if (agendaMgl.getOrdenTrabajo() != null && agendaMgl.getOrdenTrabajo().getTipoOtObj() != null) {
                String tipoOt = agendaMgl.getOrdenTrabajo().getTipoOtObj().getDescTipoOt();
                TypePropertyElementCr claseOt = new TypePropertyElementCr("XA_ClaseOrden", tipoOt);
                lstPropElements.add(claseOt);
            }

            if (agendaMgl.getOrdenTrabajo() != null && agendaMgl.getOrdenTrabajo().getEstadoInternoObj() != null) {
                String estInternoOt = agendaMgl.getOrdenTrabajo().getEstadoInternoObj().getNombreBasica();
                TypePropertyElementCr estIntOt = new TypePropertyElementCr("XA_EstadoInternoOT", estInternoOt);
                lstPropElements.add(estIntOt);
            }

            String comAscensores = "NA";

            if (cm.getSubEdificioGeneral().getCompaniaAscensorObj() != null) {
                comAscensores = cm.getSubEdificioGeneral().getCompaniaAscensorObj().getNombreCompania();
            }
            
            if (!comAscensores.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementCr comAscen = new TypePropertyElementCr("XA_CompaniaAsensores", comAscensores);
                lstPropElements.add(comAscen);
            }
           

            String comAdministracion = "NA";
            String comentarioCompaniaAdm = "NA";
            String dirCompaniaAdm = "NA";
            String email2CompaniaAdm = "NA";
            String emailContCompaniaAdm = "NA";
            String tel2CompaniaAdm = "NA";
            String tel3CompaniaAdm = "NA";

            if (cm.getSubEdificioGeneral().getCompaniaAdministracionObj() != null) {
                comAdministracion = cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getNombreCompania() != null ? cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getNombreCompania() : "NA";
                comentarioCompaniaAdm = cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getJustificacion() != null ? cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getJustificacion() : "NA";
                dirCompaniaAdm = cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getDireccion() != null ? cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getDireccion() : "NA";
                email2CompaniaAdm = cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getEmail() != null ? cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getEmail() : "NA";
                emailContCompaniaAdm = cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getEmail() != null ? cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getEmail(): "NA";
                tel2CompaniaAdm = cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getTelefono2() != null ? cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getTelefono2() : "NA";
                tel3CompaniaAdm = cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getTelefono3() != null ? cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getTelefono3() : "NA";
            }

            if (!comAdministracion.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementCr comAdm = new TypePropertyElementCr("XA_CompaniaAdministracion", comAdministracion);
                lstPropElements.add(comAdm);
            }
           
            if (!dirCompaniaAdm.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementCr dirCorresAdm = new TypePropertyElementCr("XA_DireccionCorrespondencia", dirCompaniaAdm);
                lstPropElements.add(dirCorresAdm);
            }
           
            if (!comentarioCompaniaAdm.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementCr comCorresAdm = new TypePropertyElementCr("XA_ComentarioCorrespondencia", comentarioCompaniaAdm);
                lstPropElements.add(comCorresAdm);
            }

            if (!email2CompaniaAdm.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementCr email2CorresAdm = new TypePropertyElementCr("XA_Email2", email2CompaniaAdm);
                lstPropElements.add(email2CorresAdm);
            }
            
            if (!emailContCompaniaAdm.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementCr emailContCorresAdm = new TypePropertyElementCr("XA_Email", emailContCompaniaAdm);
                lstPropElements.add(emailContCorresAdm);
            }
            
      
            if (!tel2CompaniaAdm.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementCr tel2CorresAdm = new TypePropertyElementCr("XA_Telefonodos", tel2CompaniaAdm);
                lstPropElements.add(tel2CorresAdm);
            }
             
            if (!tel3CompaniaAdm.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementCr tel3CorresAdm = new TypePropertyElementCr("XA_Telefonotres", tel3CompaniaAdm);
                lstPropElements.add(tel3CorresAdm);
            }
           

            if (cm.getDireccionPrincipal() != null && cm.getDireccionPrincipal().getBarrio() != null
                    && !cm.getDireccionPrincipal().getBarrio().isEmpty()) {
                String barrio = cm.getDireccionPrincipal().getBarrio();
                TypePropertyElementCr barrioCm;
                if (barrio != null) {
                    barrioCm = new TypePropertyElementCr("XA_Barrio", barrio);
                    lstPropElements.add(barrioCm);
                }
            }
            
            //pendiente blacklist
            TypePropertyElementCr indRein = new TypePropertyElementCr("XA_IndicadorReincidencias", "N");
            lstPropElements.add(indRein);

            cuentaMatrizId = cm.getCuentaMatrizId();
            gestionSeg = gestionSegCmManager.findAllManagementAccount(cuentaMatrizId);
            cerraduraElect = gestionSeg.getCerraduraElect();
            
            String tipoOrdenMGW = parametrosMglManager.findByAcronimo(
                    Constant.LETRA_TIPO_ORDEN_MGW_CM)
                    .iterator().next().getParValor();
            
            String tipoOrdenMGWSEG = parametrosMglManager.findByAcronimo(
                    Constant.LETRA_TIPO_ORDEN_MGW_SEG)
                    .iterator().next().getParValor();
            
            if(cerraduraElect != null && !cerraduraElect.isEmpty() 
               && cerraduraElect.equals(Constant.CERRADURA_ELECT_S)){
               TypePropertyElementCr tipOrdenMGW = new TypePropertyElementCr(Constant.XA_TIPO_ORDEN_MGW, tipoOrdenMGWSEG);
               lstPropElements.add(tipOrdenMGW);  
            }else{
               TypePropertyElementCr tipOrdenMGW = new TypePropertyElementCr(Constant.XA_TIPO_ORDEN_MGW, tipoOrdenMGW);
               lstPropElements.add(tipOrdenMGW);   
            }

            if (!distrito.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementCr distritoSave = new TypePropertyElementCr("XA_Distrito", distrito);
                lstPropElements.add(distritoSave);
            }
            
            if (!undGestion.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementCr undGesSave = new TypePropertyElementCr("XA_UnidadGestion", undGestion);
                lstPropElements.add(undGesSave);
            }

            if (!area.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementCr areaSave = new TypePropertyElementCr("XA_Area", area);
                lstPropElements.add(areaSave);
            }
            
            if (!zona.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementCr zonaSave = new TypePropertyElementCr("XA_Zona", zona);
                lstPropElements.add(zonaSave);
            }
            
            String nombreCon = agendaMgl.getOrdenTrabajo().getPersonaRecVt();
            String tel1Con = agendaMgl.getOrdenTrabajo().getTelPerRecVt();
            String tel2Con = "NA";
            
            if (agendaMgl.getOrdenTrabajo().getSegmento() != null
                    && agendaMgl.getOrdenTrabajo().getSegmento().getNombreBasica() != null
                    && !agendaMgl.getOrdenTrabajo().getSegmento().getNombreBasica().isEmpty()) {
                TypePropertyElementCr tipoCliente = new TypePropertyElementCr("XA_TipoCliente", agendaMgl.getOrdenTrabajo().getSegmento().getNombreBasica());
                lstPropElements.add(tipoCliente);
            }else{
                TypePropertyElementCr tipoCliente = new TypePropertyElementCr("XA_TipoCliente", "0");
                lstPropElements.add(tipoCliente);
            }

            if (agendaMgl.getOrdenTrabajo().getFechaProgramacion() != null) {
                TypePropertyElementCr fechaSugerida = new TypePropertyElementCr("MER_CM_FechaSOT", agendaMgl.getOrdenTrabajo().getFechaProgramacion().toString());
                lstPropElements.add(fechaSugerida);
            }


            TypePropertyElementCr contacto = new TypePropertyElementCr("XA_Contacto", nombreCon);
            lstPropElements.add(contacto);

            TypePropertyElementCr telUnoCon = new TypePropertyElementCr("XA_Telefonouno", tel1Con);
            lstPropElements.add(telUnoCon);

            if (!tel2Con.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementCr telDosCon = new TypePropertyElementCr("XA_Telefonodos_Contacto", tel2Con);
                lstPropElements.add(telDosCon);
            }
            
            TypePropertyElementCr numMarker = new TypePropertyElementCr("XA_NumeroMarker", "No Hay");
            lstPropElements.add(numMarker);

            TypePropertyElementCr marker = new TypePropertyElementCr("XA_Marker", "1");
            lstPropElements.add(marker);

            TypePropertyElementCr confMail = new TypePropertyElementCr("XA_Confirmation_Mail", "0");
            lstPropElements.add(confMail);

            TypePropertyElementCr otmasterApptNumber = new TypePropertyElementCr("masterApptNumber", numeroOTRR);
            lstPropElements.add(otmasterApptNumber);
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

            if (cm.getDireccionPrincipal() != null) {
                String estrato = String.valueOf(cm.getDireccionPrincipal().getEstrato());
                TypePropertyElementCr estratoCm = new TypePropertyElementCr("XA_Estrato", estrato);
                lstPropElements.add(estratoCm);
            }

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

            //Nuevos
            TypePropertyElementCr nombreEdificioGral = new TypePropertyElementCr("XA_NombreEdificio", subEdificioMgl.getNombreSubedificio());
            lstPropElements.add(nombreEdificioGral);

            String contactoEdi = subEdificioMgl.getAdministrador() != null ? subEdificioMgl.getAdministrador() : "NA";

            TypePropertyElementCr contactoEdiCm = new TypePropertyElementCr("XA_ContactoEdificio", contactoEdi);
            lstPropElements.add(contactoEdiCm);

            String telUnoPor = subEdificioMgl.getTelefonoPorteria() != null ? subEdificioMgl.getTelefonoPorteria() : "NA";

            if (!telUnoPor.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementCr telefonoUno = new TypePropertyElementCr("XA_TelefonoUnoEdificio", telUnoPor);
                lstPropElements.add(telefonoUno);
            }
            
            String telDosPor = subEdificioMgl.getTelefonoPorteria2() != null ? subEdificioMgl.getTelefonoPorteria2() : "NA";

            if (!telDosPor.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementCr telefonoDospor = new TypePropertyElementCr("XA_TelefonoDosEdificio", telDosPor);
                lstPropElements.add(telefonoDospor);
            }
            
            TypePropertyElementCr cuentaNo = new TypePropertyElementCr("XA_CuentaMatriz", cm.getCuentaMatrizId().toString());
            lstPropElements.add(cuentaNo);
            
            if(agendaMgl.getIdOtenrr() != null){
                TypePropertyElementCr idOtRR = new TypePropertyElementCr("XA_IdOtRRMER", agendaMgl.getIdOtenrr());
                lstPropElements.add(idOtRR);
            }else if(numeroOTRR != null){
                TypePropertyElementCr idOtRR = new TypePropertyElementCr("XA_IdOtRRMER", numeroOTRR.substring(6));
                lstPropElements.add(idOtRR);
            }
            
            String codigoDanDep = cm.getDepartamento().getGeoCodigoDane() != null ? cm.getDepartamento().getGeoCodigoDane() : "";

            TypePropertyElementCr daneDep = new TypePropertyElementCr("XA_Departamento", codigoDanDep);
            lstPropElements.add(daneDep);

            String codigoDanCiu = cm.getMunicipio().getGeoCodigoDane() != null ? cm.getMunicipio().getGeoCodigoDane() : "";

            if (codigoDanCiu != null && !codigoDanCiu.isEmpty()) {
                if (codigoDanCiu.length() >= 5) {
                    codigoDanCiu = codigoDanCiu.substring(2, 5);
                } else {
                    throw new ApplicationException("El código DANE de la Ciudad '" + cm.getMunicipio().getGpoNombre() + "' no está correctamente configurado (" + codigoDanCiu + ").");
                }
            } else {
                throw new ApplicationException("No existe un código DANE para la Ciudad '" + cm.getMunicipio().getGpoNombre() + "'.");
            }

            TypePropertyElementCr daneCiu = new TypePropertyElementCr("XA_Municipio", codigoDanCiu);
            lstPropElements.add(daneCiu);

            String codigoDanCen = cm.getCentroPoblado().getGeoCodigoDane() != null ? cm.getCentroPoblado().getGeoCodigoDane() : "";

            if (codigoDanCen != null && !codigoDanCen.isEmpty()) {
                if (codigoDanCen.length() >= 8) {
                    codigoDanCen = codigoDanCen.substring(5, 8);
                } else {
                    throw new ApplicationException("El código DANE del Centro Poblado '" + cm.getCentroPoblado().getGpoNombre() + "' no está correctamente configurado (" + codigoDanCen + ").");
                }
            } else {
                throw new ApplicationException("No existe un código DANE para el Centro Poblado '" + cm.getCentroPoblado().getGpoNombre() + "'.");
            }

            TypePropertyElementCr daneCen = new TypePropertyElementCr("XA_CenPoblado", codigoDanCen);
            lstPropElements.add(daneCen);
            
            
            
            

            cantidadHhppCon = hhppMglManager.cantidadHhppCuentaMatriz(cm);

            TypePropertyElementCr cantidadHhpp = new TypePropertyElementCr("XA_NoHHPPS", String.valueOf(cantidadHhppCon));
            lstPropElements.add(cantidadHhpp);

            TypePropertyElementCr tecnologiaNew = new TypePropertyElementCr("XA_CM_Tecnologia", agendaMgl.getOrdenTrabajo().getBasicaIdTecnologia().getNombreBasica());
            lstPropElements.add(tecnologiaNew);

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
                    TypePropertyElementCr complejidadServ = new TypePropertyElementCr("XA_CM_Complejidad",
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

            //Consulta de archivos de costos de vt
            CmtVisitaTecnicaMgl visitaTecnicaAct = visitaTecnicaMglManager.
                    findVTActiveByIdOt(agendaMgl.getOrdenTrabajo());
            if (visitaTecnicaAct != null) {
                List<CmtArchivosVtMgl> lstArchivosVtMgls
                        = archivosVtMglManager.findAllByVt(visitaTecnicaAct);
                if (lstArchivosVtMgls != null && !lstArchivosVtMgls.isEmpty()) {
                    int i = 1;
                    String urlForOfsc;
                    for (CmtArchivosVtMgl cmtArchivosVtMgl : lstArchivosVtMgls) {
                        // URL correspondiente al Servlet que descarga documentos de VT desde UCM.
                        urlForOfsc = requestContextPath + "/view/MGL/document/download/" + cmtArchivosVtMgl.getIdArchivosVt();
                        switch (i) {
                            case 1:
                                TypePropertyElementCr anexoDoc1 = new TypePropertyElementCr("XA_Link_Anexo_Docto1", urlForOfsc);
                                lstPropElements.add(anexoDoc1);
                                break;
                            case 2:
                                TypePropertyElementCr anexoDoc2 = new TypePropertyElementCr("XA_Link_Anexo_Docto2", urlForOfsc);
                                lstPropElements.add(anexoDoc2);
                                break;
                            case 3:
                                TypePropertyElementCr anexoDoc3 = new TypePropertyElementCr("XA_Link_Anexo_Docto3", urlForOfsc);
                                lstPropElements.add(anexoDoc3);
                                break;
                            case 4:
                                TypePropertyElementCr anexoDoc4 = new TypePropertyElementCr("XA_Link_Anexo_Docto4", urlForOfsc);
                                lstPropElements.add(anexoDoc4);
                                break;
                            case 5:
                                TypePropertyElementCr anexoDoc5 = new TypePropertyElementCr("XA_Link_Anexo_Docto5", urlForOfsc);
                                lstPropElements.add(anexoDoc5);
                                break;
                            default:
                                break;
                        }
                        i++;
                    }
                }
            }
            if (cm.getCuentaMatrizId() != null) {
                readInfoCtech(cm.getCuentaMatrizId());
            }
            LOGGER.info("Cuenta Matriz agenda en ofsc: " + cm.getCuentaMatrizId());
            
            if (sitio != null && !sitio.isEmpty()) {
                TypePropertyElementCr xaSitio = new TypePropertyElementCr("XA_Sitio", sitio);
                lstPropElements.add(xaSitio);
            }
            if (idSitio != null && !idSitio.isEmpty()) {
                TypePropertyElementCr xaIdSitio = new TypePropertyElementCr("XA_IdSitio", idSitio);
                lstPropElements.add(xaIdSitio);
            }
            if (ubicaTecnica != null && !ubicaTecnica.isEmpty()) {
                TypePropertyElementCr xaUbicaTecnica = new TypePropertyElementCr("XA_UbicacionTecnica", ubicaTecnica);
                lstPropElements.add(xaUbicaTecnica);
            }
            if (codDane != null && !codDane.isEmpty()) {
                TypePropertyElementCr xaCodDane = new TypePropertyElementCr("XA_CodDane", codDane);
                lstPropElements.add(xaCodDane);
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

                agendaMgl = new  CmtAgendamientoMgl(capacidad.getDate(),
                        response.getData().getCommands().getCommand().get(0).getType(),
                        response.getData().getCommands().getCommand().get(0).getExternal_id(),
                        response.getData().getCommands().getCommand().get(0).getAppointment().getAid(),
                        response.getData().getCommands().getCommand().get(0).getAppointment().getReport().getMessage().getResult(),
                        response.getData().getCommands().getCommand().get(0).getAppointment().getReport().getMessage().getType(),
                        agendaMgl.getOrdenTrabajo(), numeroArmadoOtOFSC,
                        null, estadoAgBasicaMgl, consecutivoMultiAge, capacidad.getTimeSlot(), "N", subTipoWork,
                        agendaMgl.getOrdenTrabajo().getEstadoInternoObj(),agendaMgl.getIdentificacionTecnico(), 
                        agendaMgl.getNombreTecnico(),agendaMgl.getIdentificacionAliado(),
                        agendaMgl.getNombreAliado(),agendaMgl.getFechaAsigTecnico(), 
                        agendaMgl.getNodoMgl(), esTecnicoAnticipado, capacidad.getHoraInicio(), esAgendaInmediata);
                
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

        } catch (ApplicationException | UniformInterfaceException | IOException | NumberFormatException e) {
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

    /**
     * Metodo para realizar la actualizacion de una agenda en OFSC y MGL Autor:
     * Victor Bocanegra
     *
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que se va actualizar
     * en OFSC y MGL
     * @param usuario que ejecuta la actualizacion
     * @param perfil del usuario
     * @param requestContextPath Ruta del actual contexto Faces.
     * @return CmtAgendamientoMgl agenda actualizada
     * @throws ApplicationException Excepcion lanzada al actulizar
     */
    public CmtAgendamientoMgl updateAgendaMgl(
            CmtAgendamientoMgl agendaMgl, String usuario, int perfil,
            String requestContextPath)
            throws ApplicationException {
        try {

            String subTipoWork = "";
            CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl;
            List<CmtExtendidaTipoTrabajoMgl> extendidaTipoTrabajoMgl;
            CmtExtendidaTipoTrabajoMgl extendida;
            List<CmtNotaOtMgl> notasOt;
            List<TypePropertyElementUp> lstPropElements = new ArrayList<>();
            String direccionCm;
            String codNodo = "";
            String nomNodo = "";           
            NodoMgl nodoMgl;
            CmtBasicaMgl unidadGesBasica;
            String undGestion = "NA";
            CmtBasicaMgl areaBasica;
            String area = "NA";
            CmtBasicaMgl zonaBasica;
            String zona = "NA";
            CmtBasicaMgl distritoBasica;
            String distrito = "NA";
            int cantidadHhppCon;
            HhppMglManager hhppMglManager = new HhppMglManager();
            CmtArchivosVtMglManager archivosVtMglManager = new CmtArchivosVtMglManager();
            CmtVisitaTecnicaMglManager visitaTecnicaMglManager = new CmtVisitaTecnicaMglManager();
            CmtOrdenTrabajoMgl ot= null;
            
            if(agendaMgl.getOrdenTrabajo() != null){
              ot = ordenTrabajoMglManager.findOtById(agendaMgl.getOrdenTrabajo().getIdOt());    
            }

            MgwTypeActualizarRequestElement request = new MgwTypeActualizarRequestElement();
            String formattedDate = sdf.format(new Date());
            MgwHeadElement head = new MgwHeadElement(formattedDate,
                    agendaMgl.getOrdenTrabajo().getIdOt().toString());
            request.setHead(head);
            TypeDataElementUp data = new TypeDataElementUp();
            TypeAppointmentElementUp appoiment = new TypeAppointmentElementUp();
            

            cm = agendaMgl.getOrdenTrabajo().getCmObj();
            direccionCm = cm.getDireccionPrincipal().getDireccionObj().getDirFormatoIgac();
            String numeroArmadoCmOFSC = armarNumeroCmOfsc(cm.getNumeroCuenta());

            appoiment.setAppt_number(agendaMgl.getOfpsOtId());
            appoiment.setCustomer_number(numeroArmadoCmOFSC);
            appoiment.setTime_slot(agendaMgl.getTimeSlot());
            String tipoWork = agendaMgl.getOrdenTrabajo().getBasicaIdTipoTrabajo().getAbreviatura();
            appoiment.setWorktype_label(tipoWork);
            appoiment.setName(cm.getNombreCuenta());

            TypeCommandElementUp command = new TypeCommandElementUp();
            command.setAppointment(appoiment);
            command.setDate(ValidacionUtil.dateFormatyyyyMMdd(agendaMgl.getFechaAgenda()));

            CmtSubEdificioMgl subEdificioMgl;
            subEdificioMgl = cm.getSubEdificioGeneral();
            CmtComunidadRr comunidadRr = null;
            CmtRegionalRr regionalRr;
            String comNodo="";         
            String regNodo = "";            
            
            if (agendaMgl.getNodoMgl() != null) {
                nodoMgl = agendaMgl.getNodoMgl();
                if (nodoMgl != null) {
                    codNodo = nodoMgl.getNodCodigo();
                    nomNodo = nodoMgl.getNodNombre();
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
                }
            } else {
                throw new ApplicationException("No existe nodo de cobertura para la tecnologia de la Ot");
            }

            String location = "";
            extendidaTipoTrabajoMgl = extendidaTipoTrabajoMglManager.
                    findBytipoTrabajoObjAndCom(comunidadRr, agendaMgl.getOrdenTrabajo().getBasicaIdTipoTrabajo());

            if (extendidaTipoTrabajoMgl != null && !extendidaTipoTrabajoMgl.isEmpty()) {
                extendida = extendidaTipoTrabajoMgl.get(0);
                location = extendida.getLocationCodigo().trim();
            }
            
            if (agendaMgl.getComentarioReasigna() != null && 
                    !agendaMgl.getComentarioReasigna().isEmpty()) {

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

            notasOt = agendaMgl.getOrdenTrabajo().getNotasList();
            String obs = "";

            if (notasOt != null) {
                for (CmtNotaOtMgl notas : notasOt) {
                    obs = obs + " "+"TIPO NOTA:" +notas.getTipoNotaObj().getNombreBasica()+" " 
                            +"Nota: (" + notas.getNota()+")";
                }
            }
            
           
            TypePropertyElementUp notCm = new TypePropertyElementUp("activity_notes", obs);
            lstPropElements.add(notCm);

            TypePropertyElementUp city = new TypePropertyElementUp("XA_Idcity", comNodo);
            lstPropElements.add(city);

            String fecCreaOt = ValidacionUtil.dateFormatyyyyMMdd(agendaMgl.getOrdenTrabajo().getFechaCreacion());
            TypePropertyElementUp fechaCreaOt = new TypePropertyElementUp("XA_FechaCreacion", fecCreaOt);
            lstPropElements.add(fechaCreaOt);

            TypePropertyElementUp regional = new TypePropertyElementUp("XA_Regional", regNodo);
            lstPropElements.add(regional);

            TypePropertyElementUp tipOrdSup = new TypePropertyElementUp("XA_TipoOrdenSupervision", "OS");
            lstPropElements.add(tipOrdSup);

            TypePropertyElementUp nodo = new TypePropertyElementUp("Node", codNodo);
            lstPropElements.add(nodo);

            cmtEstadoxFlujoMgl = estadoxFlujoMglManager.
                    findByTipoFlujoAndEstadoInt(agendaMgl.getOrdenTrabajo().getTipoOtObj().getTipoFlujoOt(),
                            agendaMgl.getOrdenTrabajo().getEstadoInternoObj(),
                            agendaMgl.getOrdenTrabajo().getBasicaIdTecnologia());

            if (cmtEstadoxFlujoMgl.getEstadoxFlujoId() != null) {
                if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                    subTipoWork = cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC().getNombreBasica();
                }

            }
            TypePropertyElementUp subTipoTra = new TypePropertyElementUp("XA_WorkOrderSubtype", subTipoWork);
            lstPropElements.add(subTipoTra);

            TypePropertyElementUp bucket = new TypePropertyElementUp("XA_Bucket", location);
            lstPropElements.add(bucket);

            String nombreRed = "";

            mglExtendidaTecnologia = extendidaTecnologiaMglManager.
                    findBytipoTecnologiaObj(agendaMgl.getOrdenTrabajo()
                            .getBasicaIdTecnologia());

            if (mglExtendidaTecnologia.getIdExtTec() != null) {
                nombreRed = mglExtendidaTecnologia.getNomTecnologiaOFSC().getAbreviatura();
            }
            TypePropertyElementUp tecnologia = new TypePropertyElementUp("XA_Red", nombreRed);
            lstPropElements.add(tecnologia);

            TypePropertyElementUp nombreNodo = new TypePropertyElementUp("XA_NombreNodo", nomNodo);
            lstPropElements.add(nombreNodo);

            TypePropertyElementUp nombreComCm = new TypePropertyElementUp("XA_NombreCompleto", cm.getNombreCuenta());
            lstPropElements.add(nombreComCm);

            TypePropertyElementUp dirActual = new TypePropertyElementUp("XA_DireccionActual", direccionCm);
            lstPropElements.add(dirActual);

            String SLA = String.valueOf(agendaMgl.getOrdenTrabajo().getTipoOtObj().getAns());
            String avisoSla = String.valueOf(agendaMgl.getOrdenTrabajo().getTipoOtObj().getAnsAviso());
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

            TypePropertyElementUp dirPrincipal = new TypePropertyElementUp("XA_DireccionEdificio", direccionCm);
            lstPropElements.add(dirPrincipal);

            List<CmtDireccionMgl> listDireccionesAlternas;
            CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
            listDireccionesAlternas = cmtDireccionMglManager.findBySubEdificio(subEdificioMgl);

            if (!listDireccionesAlternas.isEmpty()) {
                int i = 1;
                for (CmtDireccionMgl direccionMgl : listDireccionesAlternas) {

                    if (i == 1) {
                        TypePropertyElementUp dirAlterna = new TypePropertyElementUp("XA_DireccionEdificio2", direccionMgl.getDireccionObj().getDirFormatoIgac());
                        lstPropElements.add(dirAlterna);

                    } else if (i == 2) {
                        TypePropertyElementUp dirOtras = new TypePropertyElementUp("XA_OtrasDirecciones", direccionMgl.getDireccionObj().getDirFormatoIgac());
                        lstPropElements.add(dirOtras);
                    }

                    i++;
                }
            }
            //Tabla Blacklist
            CmtBlackListMglManager blackListMglManager = new CmtBlackListMglManager();
            List<CmtBlackListMgl> lsBlackListMgls = blackListMglManager.findBySubEdificio(subEdificioMgl);
            String cadenaFinalBlacklist;
            if (!lsBlackListMgls.isEmpty()) {
                String black;
                String blackVarios = "";
                for (CmtBlackListMgl blackListMgl : lsBlackListMgls) {
                    black = "\u003cblack\u003e\u003ccodigo\u003e" + blackListMgl.getBlackListObj().getCodigoBasica() + "\u003c/codigo\u003e\u003cdescripcion\u003e" + blackListMgl.getBlackListObj().getNombreBasica() + "\u003c/descripcion\u003e\u003c/black\u003e";
                    blackVarios = blackVarios + black;
                }
                cadenaFinalBlacklist = cadenaBlacklist1 + blackVarios + cadenaBlacklist2;
                TypePropertyElementUp blacklist = new TypePropertyElementUp("XA_BlackListEdificio", cadenaFinalBlacklist);
                lstPropElements.add(blacklist);
            }
            //Tabla Blacklist

            //Tabla EstadosForTecno
            CmtSubEdificioMglManager subEdificioMglManager = new CmtSubEdificioMglManager();
            List<CmtSubEdificioMgl> subEdificioMgls = subEdificioMglManager.findSubEdificioByCuentaMatriz(cm);
            List<CmtTecnologiaSubMgl> tecnologiasFinales = new ArrayList<>();

            if (!subEdificioMgls.isEmpty()) {
                for (CmtSubEdificioMgl subEdificioMgl1 : subEdificioMgls) {
                    List<CmtTecnologiaSubMgl> tecnologias = tecnologiaSubMglManager.
                            findTecnoSubBySubEdi(subEdificioMgl1);
                    if (!tecnologias.isEmpty()) {
                        for (CmtTecnologiaSubMgl tecno : tecnologias) {
                            tecnologiasFinales.add(tecno);
                        }
                    }
                }
                String cadenaFinalEstxTecno;

                if (!tecnologiasFinales.isEmpty()) {
                    String estTecno;
                    String estTecnoVarios = "";
                    String tecnNoEdi;
                    String tecnNoEdiFin = "";

                    CmtTipoBasicaMglManager tipoBasicaMglManager = new CmtTipoBasicaMglManager();
                    CmtTipoBasicaMgl tipoBasicaTecno = tipoBasicaMglManager.findByCodigoInternoApp(
                            co.com.claro.mgl.utils.Constant.TIPO_BASICA_TECNOLOGIA);
                    List<CmtBasicaMgl> listTablaBasicaTecnologias = basicaMglManager.
                            findByTipoBasica(tipoBasicaTecno);

                    for (CmtTecnologiaSubMgl tenSubMgl : tecnologiasFinales) {

                        for (CmtBasicaMgl basiTecBasicaMgl : listTablaBasicaTecnologias) {
                            if (basiTecBasicaMgl.getBasicaId().compareTo(tenSubMgl.getBasicaIdTecnologias().getBasicaId()) == 0) {
                                tecnNoEdi = "\u003c" + basiTecBasicaMgl.getCodigoBasica() + "\u003e" + tenSubMgl.getBasicaIdEstadosTec().getNombreBasica() + "\u003c/" + basiTecBasicaMgl.getCodigoBasica() + "\u003e";
                            } else {
                                tecnNoEdi = "\u003c" + basiTecBasicaMgl.getCodigoBasica() + "\u003e\u003c/" + basiTecBasicaMgl.getCodigoBasica() + "\u003e";
                            }
                            tecnNoEdiFin = tecnNoEdiFin + tecnNoEdi;
                        }
                        FiltroBusquedaDirecccionDto filtro = new FiltroBusquedaDirecccionDto();
                        int cantidadHhppSub = hhppMglManager.countListHhppSubEdif(filtro, tenSubMgl.getSubedificioId());
                        String nameSub = tenSubMgl.getSubedificioId().getNombreSubedificio().replace("-", " ");
                        String estrato = tenSubMgl.getSubedificioId().getEstrato() != null ? tenSubMgl.getSubedificioId().getEstrato().getCodigoBasica():"NG";
                        estTecno = "\u003cesttecnologia\u003e\u003ctorrebloque\u003e" + nameSub + "\u003c/torrebloque\u003e" + tecnNoEdiFin + "\u003cestrato\u003e" + estrato + "\u003c/estrato\u003e\u003ccantidaddehpps\u003e" + cantidadHhppSub + "\u003c/cantidaddehpps\u003e\u003c/esttecnologia\u003e";
                        estTecnoVarios = estTecnoVarios + estTecno;
                    }
                    cadenaFinalEstxTecno = cadenaEstXTecno1 + estTecnoVarios + cadenaEstXTecno2;
                    TypePropertyElementUp estadosxTecnologia = new TypePropertyElementUp("XA_CM_EstadosPorTecnologia", cadenaFinalEstxTecno);
                    lstPropElements.add(estadosxTecnologia);
                }
            }
            //Tabla EstadosForTecno

            //Tabla Horarios de atencion
            CmtHorarioRestriccionMglManager horarioRestriccionMglManager = new CmtHorarioRestriccionMglManager();
            List<CmtHorarioRestriccionMgl> horarioRestriccionMgls = horarioRestriccionMglManager.findByCuentaMatrizId(cm.getCuentaMatrizId());

            String cadenaFinalHorarios;

            if (!horarioRestriccionMgls.isEmpty()) {
                String horarios;
                String horariosVarios = "";

                for (CmtHorarioRestriccionMgl horarioRestriccionMgl : horarioRestriccionMgls) {
                    horarios = "\u003cHorario\u003e\u003cdiaInicio\u003e" + horarioRestriccionMgl.getDiaInicio().getDia() + "\u003c/diaInicio\u003e\u003cdiaFin\u003e" + horarioRestriccionMgl.getDiaFin().getDia() + "\u003c/diaFin\u003e\u003choraInicio\u003e" + horarioRestriccionMgl.getHoraInicio() + "\u003c/horaInicio\u003e\u003choraFin\u003e" + horarioRestriccionMgl.getHoraFin() + "\u003c/horaFin\u003e\u003c/Horario\u003e";
                    horariosVarios = horariosVarios + horarios;
                }
                cadenaFinalHorarios = cadenaHorAtencion1 + horariosVarios + cadenaHorAtencion2;
                TypePropertyElementUp horariosAtencion = new TypePropertyElementUp("XA_HorariosAtencion", cadenaFinalHorarios);
                lstPropElements.add(horariosAtencion);
            }
            //Tabla Horarios de atencion

            CmtEstadoIntxExtMgl estadoInternoExterno;

            estadoInternoExterno = estadoIntxExtMglManager.
                    findByEstadoInterno(agendaMgl.getOrdenTrabajo().getEstadoInternoObj());

            String estadoOt = estadoInternoExterno.getIdEstadoExt().getNombreBasica();
            TypePropertyElementUp estOT = new TypePropertyElementUp("XA_EstadoOrdenCRM", estadoOt);
            lstPropElements.add(estOT);

            String tipoOt = agendaMgl.getOrdenTrabajo().getTipoOtObj().getDescTipoOt();
            TypePropertyElementUp claseOt = new TypePropertyElementUp("XA_ClaseOrden", tipoOt);
            lstPropElements.add(claseOt);

            String estInternoOt = agendaMgl.getOrdenTrabajo().getEstadoInternoObj().getNombreBasica();
            TypePropertyElementUp estIntOt = new TypePropertyElementUp("XA_EstadoInternoOT", estInternoOt);
            lstPropElements.add(estIntOt);

            String comAscensores = "NA";

            if (cm.getSubEdificioGeneral().getCompaniaAscensorObj() != null) {
                comAscensores = cm.getSubEdificioGeneral().getCompaniaAscensorObj().getNombreCompania();
            }
            
            if (!comAscensores.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementUp comAscen = new TypePropertyElementUp("XA_CompaniaAsensores", comAscensores);
                lstPropElements.add(comAscen);
            }
            
            String comAdministracion = "NA";
            String comentarioCompaniaAdm = "NA";
            String dirCompaniaAdm = "NA";
            String email2CompaniaAdm = "NA";
            String emailContCompaniaAdm = "NA";
            String tel2CompaniaAdm = "NA";
            String tel3CompaniaAdm = "NA";

            if (cm.getSubEdificioGeneral().getCompaniaAdministracionObj() != null) {
                comAdministracion = cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getNombreCompania() != null ? cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getNombreCompania() : "NA";
                comentarioCompaniaAdm = cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getJustificacion() != null ? cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getJustificacion() : "NA";
                dirCompaniaAdm = cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getDireccion() != null ? cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getDireccion() : "NA";
                email2CompaniaAdm = cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getEmail() != null ? cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getEmail() : "NA";
                emailContCompaniaAdm = cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getEmail() != null ? cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getEmail() : "NA";
                tel2CompaniaAdm = cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getTelefono2() != null ? cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getTelefono2() : "NA";
                tel3CompaniaAdm = cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getTelefono3() != null ? cm.getSubEdificioGeneral().getCompaniaAdministracionObj().getTelefono3() : "NA";
            }

            if (!comAdministracion.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementUp comAdm = new TypePropertyElementUp("XA_CompaniaAdministracion", comAdministracion);
                lstPropElements.add(comAdm);
            }
            
            if (!dirCompaniaAdm.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementUp dirCorresAdm = new TypePropertyElementUp("XA_DireccionCorrespondencia", dirCompaniaAdm);
                lstPropElements.add(dirCorresAdm);
            }
            
            if (!comentarioCompaniaAdm.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementUp comCorresAdm = new TypePropertyElementUp("XA_ComentarioCorrespondencia", comentarioCompaniaAdm);
                lstPropElements.add(comCorresAdm);
            }
            
            if (!email2CompaniaAdm.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementUp email2CorresAdm = new TypePropertyElementUp("XA_Email2", email2CompaniaAdm);
                lstPropElements.add(email2CorresAdm);
            }
           
            if (!emailContCompaniaAdm.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementUp emailContCorresAdm = new TypePropertyElementUp("XA_Email", emailContCompaniaAdm);
                lstPropElements.add(emailContCorresAdm);
            }
                     
            if (!tel2CompaniaAdm.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementUp tel2CorresAdm = new TypePropertyElementUp("XA_Telefonodos", tel2CompaniaAdm);
                lstPropElements.add(tel2CorresAdm);
            }
            
            if (!tel3CompaniaAdm.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementUp tel3CorresAdm = new TypePropertyElementUp("XA_Telefonotres", tel3CompaniaAdm);
                lstPropElements.add(tel3CorresAdm);
            }

            String barrio = cm.getDireccionPrincipal().getBarrio();
            TypePropertyElementUp barrioCm;
            if (barrio != null) {
                barrioCm = new TypePropertyElementUp("XA_Barrio", barrio);
                lstPropElements.add(barrioCm);
            } 
           
            //pendiente blacklist
            TypePropertyElementUp indRein = new TypePropertyElementUp("XA_IndicadorReincidencias", "N");
            lstPropElements.add(indRein);

            String tipoOrdenMGW = parametrosMglManager.findByAcronimo(
                    Constant.LETRA_TIPO_ORDEN_MGW_CM)
                    .iterator().next().getParValor();
            TypePropertyElementUp tipOrdenMGW = new TypePropertyElementUp(Constant.XA_TIPO_ORDEN_MGW, tipoOrdenMGW);
            lstPropElements.add(tipOrdenMGW);

            if (!distrito.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementUp distritoSave = new TypePropertyElementUp("XA_Distrito", distrito);
                lstPropElements.add(distritoSave);
            }
           
            if (!undGestion.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementUp undGes = new TypePropertyElementUp("XA_UnidadGestion", undGestion);
                lstPropElements.add(undGes);
            }
           
            if (!area.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementUp areaSave = new TypePropertyElementUp("XA_Area", area);
                lstPropElements.add(areaSave);
            }
            
            if (!zona.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementUp zonaSave = new TypePropertyElementUp("XA_Zona", zona);
                lstPropElements.add(zonaSave);
            }
            
            String nombreCon = ot != null ? ot.getPersonaRecVt(): "NA";
            String tel1Con =  ot != null ? ot.getTelPerRecVt(): "NA";
            String tel2Con = "NA";

            TypePropertyElementUp contacto = new TypePropertyElementUp("XA_Contacto", nombreCon);
            lstPropElements.add(contacto);

            TypePropertyElementUp telUnoCon = new TypePropertyElementUp("XA_Telefonouno", tel1Con);
            lstPropElements.add(telUnoCon);
            
            if (!tel2Con.trim().equalsIgnoreCase("NA")) {
                TypePropertyElementUp telDosCon = new TypePropertyElementUp("XA_Telefonodos_Contacto", tel2Con);
                lstPropElements.add(telDosCon);
            }
            
            TypePropertyElementUp nomCorresPon = new TypePropertyElementUp("XA_NombreCorrespondencia", comAdministracion);
            lstPropElements.add(nomCorresPon);

            String origenOrden = parametrosMglManager.findByAcronimo(
                    Constant.PROPIEDAD_ORIGEN_ORDEN)
                    .iterator().next().getParValor();
            TypePropertyElementUp orgOrden = new TypePropertyElementUp("XA_OrigenOrden", origenOrden);
            lstPropElements.add(orgOrden);
            String estrato = String.valueOf(cm.getDireccionPrincipal().getEstrato());
            TypePropertyElementUp estratoCm = new TypePropertyElementUp("XA_Estrato", estrato);
            lstPropElements.add(estratoCm);

            //Nuevos
            TypePropertyElementUp nombreEdificioGral = new TypePropertyElementUp("XA_NombreEdificio", subEdificioMgl.getNombreSubedificio());
            lstPropElements.add(nombreEdificioGral);

            String contactoEdi = subEdificioMgl.getAdministrador() != null ? subEdificioMgl.getAdministrador() : "NA";

            TypePropertyElementUp contactoEdiCm = new TypePropertyElementUp("XA_ContactoEdificio", contactoEdi);
            lstPropElements.add(contactoEdiCm);

            String telUnoPor = subEdificioMgl.getTelefonoPorteria() != null ? subEdificioMgl.getTelefonoPorteria() : "NA";

            TypePropertyElementUp telefonoUno = new TypePropertyElementUp("XA_TelefonoUnoEdificio", telUnoPor);
            lstPropElements.add(telefonoUno);

            String telDosPor = subEdificioMgl.getTelefonoPorteria2() != null ? subEdificioMgl.getTelefonoPorteria2() : "NA";

            TypePropertyElementUp telefonoDospor = new TypePropertyElementUp("XA_TelefonoDosEdificio", telDosPor);
            lstPropElements.add(telefonoDospor);

            TypePropertyElementUp cuentaNo = new TypePropertyElementUp("XA_CuentaMatriz", cm.getCuentaMatrizId().toString());
            lstPropElements.add(cuentaNo);
            
            String codigoDanDep = cm.getDepartamento().getGeoCodigoDane() != null ? cm.getDepartamento().getGeoCodigoDane() : "";

            TypePropertyElementUp daneDep = new TypePropertyElementUp("XA_Departamento", codigoDanDep);
            lstPropElements.add(daneDep);

            String codigoDanCiu = cm.getMunicipio().getGeoCodigoDane() != null ? cm.getMunicipio().getGeoCodigoDane() : "";

            if (codigoDanCiu != null && !codigoDanCiu.equalsIgnoreCase("")) {
                if (codigoDanCiu.length() >= 5) {
                    codigoDanCiu = codigoDanCiu.substring(2, 5);
                } else {
                    throw new ApplicationException("El código DANE de la Ciudad '" + cm.getMunicipio().getGpoNombre() + "' no está correctamente configurado (" + codigoDanCiu + ").");
                }
            } else {
                throw new ApplicationException("No existe un código DANE para la Ciudad '" + cm.getMunicipio().getGpoNombre() + "'.");
            }

            TypePropertyElementUp daneCiu = new TypePropertyElementUp("XA_Municipio", codigoDanCiu);
            lstPropElements.add(daneCiu);

            String codigoDanCen = cm.getCentroPoblado().getGeoCodigoDane() != null ? cm.getCentroPoblado().getGeoCodigoDane() : "";

            if (codigoDanCen != null && !codigoDanCen.equalsIgnoreCase("")) {
                if (codigoDanCen.length() >= 8) {
                    codigoDanCen = codigoDanCen.substring(5, 8);
                } else {
                    throw new ApplicationException("El código DANE del Centro Poblado '" + cm.getCentroPoblado().getGpoNombre() + "' no está correctamente configurado (" + codigoDanCen + ").");
                }
            } else {
                throw new ApplicationException("No existe un código DANE para el Centro Poblado '" + cm.getCentroPoblado().getGpoNombre() + "'.");
            }

            TypePropertyElementUp daneCen = new TypePropertyElementUp("XA_CenPoblado", codigoDanCen);
            lstPropElements.add(daneCen);

            cantidadHhppCon = hhppMglManager.cantidadHhppCuentaMatriz(cm);

            TypePropertyElementUp cantidadHhpp = new TypePropertyElementUp("XA_NoHHPPS", String.valueOf(cantidadHhppCon));
            lstPropElements.add(cantidadHhpp);

            TypePropertyElementUp tecnologiaNew = new TypePropertyElementUp("XA_CM_Tecnologia", agendaMgl.getOrdenTrabajo().getBasicaIdTecnologia().getNombreBasica());
            lstPropElements.add(tecnologiaNew);

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
                    TypePropertyElementUp complejidadServ = new TypePropertyElementUp("XA_CM_Complejidad",
                            agendaMgl.getOrdenTrabajo().getComplejidadServicio());
                    lstPropElements.add(complejidadServ);
                }
                //Pendiente coido resolicion ot padre por parte de OFSC      

            }
            //Consulta de archivos de costos de vt

            CmtVisitaTecnicaMgl visitaTecnicaAct = visitaTecnicaMglManager.
                    findVTActiveByIdOt(agendaMgl.getOrdenTrabajo());
            if (visitaTecnicaAct != null) {
                List<CmtArchivosVtMgl> lstArchivosVtMgls = archivosVtMglManager.findAllByVtxOrigen(visitaTecnicaAct, Constant.ORIGEN_CARGA_ARCHIVO_COSTOS);
                if (lstArchivosVtMgls != null && !lstArchivosVtMgls.isEmpty()) {
                    int i = 1;
                    String urlForOfsc;
                    for (CmtArchivosVtMgl cmtArchivosVtMgl : lstArchivosVtMgls) {
                        // URL correspondiente al Servlet que descarga documentos de VT desde UCM.
                        urlForOfsc = requestContextPath + "/view/MGL/document/download/" + cmtArchivosVtMgl.getIdArchivosVt();
                        switch (i) {
                            case 1:
                                TypePropertyElementUp anexoDoc1 = new TypePropertyElementUp("XA_Link_Anexo_Docto1", urlForOfsc);
                                lstPropElements.add(anexoDoc1);
                                break;
                            case 2:
                                TypePropertyElementUp anexoDoc2 = new TypePropertyElementUp("XA_Link_Anexo_Docto2", urlForOfsc);
                                lstPropElements.add(anexoDoc2);
                                break;
                            case 3:
                                TypePropertyElementUp anexoDoc3 = new TypePropertyElementUp("XA_Link_Anexo_Docto3", urlForOfsc);
                                lstPropElements.add(anexoDoc3);
                                break;
                            case 4:
                                TypePropertyElementUp anexoDoc4 = new TypePropertyElementUp("XA_Link_Anexo_Docto4", urlForOfsc);
                                lstPropElements.add(anexoDoc4);
                                break;
                            case 5:
                                TypePropertyElementUp anexoDoc5 = new TypePropertyElementUp("XA_Link_Anexo_Docto5", urlForOfsc);
                                lstPropElements.add(anexoDoc5);
                                break;
                            default:
                                break;
                        }
                        i++;
                    }
                }
            }
            
            if (agendaMgl.getComentarioReasigna() != null && !agendaMgl.getComentarioReasigna().isEmpty()) {
                TypePropertyElementUp comentariosOt = new TypePropertyElementUp("order_comments", agendaMgl.getComentarioReasigna());
                lstPropElements.add(comentariosOt);
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
            
             if(save.equalsIgnoreCase("1")){
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
                if(requestResponsesAgeMgl.getRequestRespId()!= null){
                    LOGGER.info("registro almacenado satisfatoriamente");
                }
                
            }
            throw new ApplicationException(error+"$"+json);

        } catch (ApplicationException | UniformInterfaceException | IOException e) {
            String msg = String.format(ERROR_ACTUALIZA_AGENDA, e.getMessage());
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);

        }
    }

    /**
     * Metodo para realizar el reagendamiento en una orden de trabajo Autor:
     * Victor Bocanegra
     *
     * @param capacidad nuevo capacidad de agendamiento
     * @param agendaMgl agenda la cual se va a modificar
     * @param razonReagenda razon del reagendamiento
     * @param comentarios comentarios adicionales.
     * @param usuario usuario de sesion que reagenda
     * @param perfil perfil del usuario
     * @throws ApplicationException Excepcion lanzada al reagendar
     */
    public void reagendar(CapacidadAgendaDto capacidad,
            CmtAgendamientoMgl agendaMgl,
            String razonReagenda, String comentarios, String usuario, int perfil)
            throws ApplicationException {
        try {
            String reagenda = "REAGENDA";
            List<CmtExtendidaTipoTrabajoMgl> extendidaTipoTrabajoMgl;
            CmtExtendidaTipoTrabajoMgl extendida;
            MgwTypeReAgendarRequestElement request = new MgwTypeReAgendarRequestElement();
            List<TypePropertyElement> lstPropElements = new ArrayList<>();

            String formattedDate = sdf.format(new Date());
            MgwHeadElement head = new MgwHeadElement(formattedDate,
                    agendaMgl.getOrdenTrabajo().getIdOt().toString());

            request.setHead(head);

            TypeDataElement data = new TypeDataElement();

            TypeAppointmentElement appoiment = new TypeAppointmentElement();
            TypeCommandElement command = new TypeCommandElement();

            command.setDate(ValidacionUtil.dateFormatyyyyMMdd(capacidad.getDate()));
            command.setType("0");

            CmtComunidadRr comunidadRr = null;
            NodoMgl nodoMgl;

            if (agendaMgl.getNodoMgl() != null) {
                nodoMgl = agendaMgl.getNodoMgl();
                if (nodoMgl != null) {
                    comunidadRr = nodoMgl.getComId();
                }
            } else {
                throw new ApplicationException("No existe nodo de cobertura para la tecnologia de la Ot");
            }

            String location = "";
            extendidaTipoTrabajoMgl = extendidaTipoTrabajoMglManager.
                    findBytipoTrabajoObjAndCom(comunidadRr, agendaMgl.getOrdenTrabajo().getBasicaIdTipoTrabajo());

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
          
            command.setExternal_id(location);
            //obtiene la CM de la agenda
            cm = agendaMgl.getOrdenTrabajo().getCmObj();

            String numeroArmadoCmOFSC = armarNumeroCmOfsc(cm.getNumeroCuenta());
            
            appoiment.setAppt_number(agendaMgl.getOfpsOtId());
            appoiment.setCustomer_number(numeroArmadoCmOFSC);
            appoiment.setAction_if_completed("Si");

            String tipoWork = agendaMgl.getOrdenTrabajo().getBasicaIdTipoTrabajo().getAbreviatura();
            appoiment.setWorktype_label(tipoWork);
            
            String slaWindow;
            if (agendaMgl.getTecnicoAnticipado().equalsIgnoreCase("Y") && capacidad.getHoraInicio() != null) {
                TypePropertyElement horaIni = new TypePropertyElement("deliveryWindowStart", capacidad.getHoraInicio());
                agendaMgl.setIdentificacionTecnico(capacidad.getResourceId());
                agendaMgl.setNombreTecnico(capacidad.getNombreTecnico());
                agendaMgl.setIdentificacionAliado(capacidad.getAliadoId());
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

            TypeCommandsArray commands = new TypeCommandsArray();
            commands.add(command);
            data.setCommands(commands);
            request.setData(data);

            TypePropertyElement razonRea = new TypePropertyElement("XA_RazonDeReagendacion", razonReagenda);
            lstPropElements.add(razonRea);

            TypePropertyElement comentariosOt = new TypePropertyElement("order_comments", comentarios);
            lstPropElements.add(comentariosOt);
            
            TypePropertyElement reagendaEstado = new TypePropertyElement("XA_EstadoMER_Reag", reagenda);
            lstPropElements.add(reagendaEstado);
			
            if (cm.getCuentaMatrizId() != null) {
                readInfoCtech(cm.getCuentaMatrizId());
            }
            LOGGER.info("Cuenta Matriz reagenda en ofsc: " + cm.getCuentaMatrizId());

            if (sitio != null && !sitio.isEmpty()) {
                TypePropertyElement xaSitio = new TypePropertyElement("XA_Sitio", sitio);
                lstPropElements.add(xaSitio);
            }
            if (idSitio != null && !idSitio.isEmpty()) {
                TypePropertyElement xaIdSitio = new TypePropertyElement("XA_IdSitio", idSitio);
                lstPropElements.add(xaIdSitio);
            }
            if (ubicaTecnica != null && !ubicaTecnica.isEmpty()) {
                TypePropertyElement xaUbicaTecnica = new TypePropertyElement("XA_UbicacionTecnica", ubicaTecnica);
                lstPropElements.add(xaUbicaTecnica);
            }
            if (codDane != null && !codDane.isEmpty()) {
                TypePropertyElement xaCodDane = new TypePropertyElement("XA_CodDane", codDane);
                lstPropElements.add(xaCodDane);
            }
            TypePropertiesArray propertiesArray = new TypePropertiesArray(lstPropElements);
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
                requestResponsesAgeMgl.setNumeroOrden(agendaMgl.getOfpsOtId());
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

                LOGGER.info(String.format("Se ha reagendado la agenda para la ot %s", agendaMgl.getOfpsOtId()));
                
                if (save.equalsIgnoreCase("1")) {
                    //esta activo el flag para almacenar las peticiones
                    RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                    String json;
                    json = JSONUtils.objetoToJson(response);
                    requestResponsesAgeMgl.setTipoPeticion("Response");
                    requestResponsesAgeMgl.setTipoOperacion("Reagendar");
                    requestResponsesAgeMgl.setContenidoPeticion(json);
                    requestResponsesAgeMgl.setNumeroOrden(agendaMgl.getOfpsOtId());
                    requestResponsesAgeMgl.setFechaCreacion(new Date());
                    requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                    if (requestResponsesAgeMgl.getRequestRespId() != null) {
                        LOGGER.info("registro almacenado satisfatoriamente");
                    }

                }

                CmtBasicaMgl estadoAgBasicaMgl = basicaMglManager.
                        findByCodigoInternoApp(Constant.ESTADO_AGENDA_REAGENDADA);

                agendaMgl.setBasicaIdEstadoAgenda(estadoAgBasicaMgl);
                agendaMgl.setTimeSlot(capacidad.getTimeSlot());
                agendaMgl.setHoraInicio(capacidad.getHoraInicio());
                agendaMgl.setFechaReagenda(capacidad.getDate());
                agendaMgl.setRazonReagenda(razonReagenda);
                
                CmtAgendamientoMglManager agendamientoMglManager = new CmtAgendamientoMglManager();
                
                //actualizamos el tecnico en OFSC
                if (agendaMgl.getIdentificacionTecnico() != null
                        && !agendaMgl.getIdentificacionTecnico().equalsIgnoreCase("")) {

                    updateAgendasForTecnicoMgl(agendaMgl, agendaMgl.getIdentificacionTecnico(),
                            usuario, perfil);
                }else{
                    agendamientoMglManager.update(agendaMgl, usuario, perfil);
                }
                CmtOnyxResponseDto onyxResponseDto = returnInfoOnix(agendaMgl);
                agendaMgl.setCmtOnyxResponseDto(onyxResponseDto);
               
                try {
                    cargarInformacionForEnvioNotificacion(agendaMgl, 2);
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
                    requestResponsesAgeMgl.setNumeroOrden(agendaMgl.getOfpsOtId());
                    requestResponsesAgeMgl.setFechaCreacion(new Date());
                    requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                    if (requestResponsesAgeMgl.getRequestRespId() != null) {
                        LOGGER.info("registro almacenado satisfatoriamente");
                    }

                }
                throw new ApplicationException(error + "$" + json);
            }

        } catch (ApplicationException | UniformInterfaceException | IOException  e) {
            LOGGER.error(ERROR_REAGENDA, e);
            String msg = String.format(ERROR_REAGENDA, e.getMessage());
            throw new ApplicationException(msg, e);

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
    public void cancelar(CmtAgendamientoMgl agenda,
            String razonCancela, String comentarios,
            String usuario, int perfil) throws ApplicationException {

        try {
            MgwTypeCancelRequestElement request = new MgwTypeCancelRequestElement();
            List<CmtExtendidaTipoTrabajoMgl> extendidaTipoTrabajoMgl;
            CmtExtendidaTipoTrabajoMgl extendida;
            List<co.com.claro.mgw.agenda.cancelar.TypePropertyElement> lstPropElements
                    = new ArrayList<>();

            cm = agenda.getOrdenTrabajo().getCmObj();

            request.setHead(new MgwHeadElement(
                    ValidacionUtil.dateFormatyyyyMMdd(new Date()),
                    agenda.getOrdenTrabajo().getIdOt().toString()));

            co.com.claro.mgw.agenda.cancelar.TypeAppointmentElement appoiment = new co.com.claro.mgw.agenda.cancelar.TypeAppointmentElement();

            appoiment.setAppt_number(agenda.getOfpsOtId());
            appoiment.setTime_slot(agenda.getTimeSlot());
            appoiment.setDate(ValidacionUtil.dateFormatyyyyMMdd(agenda.getFechaAgenda()));

            CmtComunidadRr comunidadRr = null;
            NodoMgl nodoMgl;

            if (agenda.getNodoMgl() != null) {
                nodoMgl = agenda.getNodoMgl();
                if (nodoMgl != null) {
                    comunidadRr = nodoMgl.getComId();
                }
            } else {
                throw new ApplicationException("No existe nodo de cobertura para la tecnologia de la Ot");
            }

            String location = "";
            extendidaTipoTrabajoMgl = extendidaTipoTrabajoMglManager.
                    findBytipoTrabajoObjAndCom(comunidadRr, agenda.getOrdenTrabajo().getBasicaIdTipoTrabajo());

            List<ParametrosMgl> parametrosFlagList;
            parametrosFlagList = parametrosMglManager.findByAcronimo(Constant.AGENDAMIENTO_OFSC_ONOFF);                
            ParametrosMgl parametrosFlag = parametrosFlagList.get(0);
            if (extendidaTipoTrabajoMgl  != null && !extendidaTipoTrabajoMgl.isEmpty() 
                && !parametrosFlag.getParValor().equals(Constant.AGENDAMIENTO_OFSC_VALOR)){                
                extendida = extendidaTipoTrabajoMgl.get(0);
                location = extendida.getLocationCodigo().trim();
                appoiment.setExternal_id(location);                
            }

            

            String tipoWork = agenda.getOrdenTrabajo().getBasicaIdTipoTrabajo().getAbreviatura();
            appoiment.setWorktype_label(tipoWork);

            appoiment.setName(cm.getNombreCuenta());

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

                LOGGER.info(String.format("Se ha cancelado la agenda para la ot %s", agenda.getOfpsOtId()));
                
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

                CmtBasicaMgl estadoAgBasicaMgl = basicaMglManager.
                        findByCodigoInternoApp(Constant.ESTADO_AGENDA_CANCELADA);

                agenda.setBasicaIdEstadoAgenda(estadoAgBasicaMgl);
                agenda.setRazonCancela(razonCancela);
                
                CmtAgendamientoMglManager agendamientoMglManager = new CmtAgendamientoMglManager();
                agendamientoMglManager.update(agenda, usuario, perfil);
                CmtOnyxResponseDto onyxResponseDto = returnInfoOnix(agenda);
                agenda.setComentarioCancela(comentarios);
                agenda.setCmtOnyxResponseDto(onyxResponseDto);
                try {
                    cargarInformacionForEnvioNotificacion(agenda, 3);
                } catch (ApplicationException ex) {
                    String msn = "Ocurrio un error al momento de "
                            + "enviar notificacion de cancelamiento de agenda: " + ex.getMessage() + "";
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
        } catch (ApplicationException | UniformInterfaceException | IOException  e) {
            LOGGER.error(ERROR_CANCELAR, e);
            String msg = String.format(ERROR_CANCELAR, e.getMessage());
            throw new ApplicationException(msg, e);

        }
    }

    /**
     * metodo para asignar un tecnico a la agenda de OT Autor: victor bocanegra
     *
     * @param asignarRecursoRequest
     * @return ServicesAgendamientosResponse
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public ServicesAgendamientosResponse asignarRecursoAgendaMgl(AsignarRecursoRequest asignarRecursoRequest) throws ApplicationException {

        ServicesAgendamientosResponse response = new ServicesAgendamientosResponse();
        GetResourcesResponses responsesResources;
        CmtAliadosMglManager aliadosMglManager = new CmtAliadosMglManager();
        CmtAliados aliados = null;

        if (asignarRecursoRequest.getIdTecnico() == null
                || asignarRecursoRequest.getIdTecnico().isEmpty()) {
            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALTA);
            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALTA);
        } else if (asignarRecursoRequest.getIdOt() == null
                || asignarRecursoRequest.getIdOt().isEmpty()) {
            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALTA);
            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALTA);
        } else {

            String controlCcmmDir = asignarRecursoRequest.getIdOt().substring(0, 1);

            if (controlCcmmDir.equalsIgnoreCase(ConstansClient.CONTROL_NUM_CCMM)) {
                //ES UN NUMERO CREADO DESDE CCMM
                try {

                    CmtAgendamientoMgl cmtAgendamientoMgl;

                    cmtAgendamientoMgl = manager.buscarAgendaPorOtIdMgl(asignarRecursoRequest.getIdOt());
                    if (cmtAgendamientoMgl.getId() != null
                            && cmtAgendamientoMgl.getTecnicoAnticipado() != null
                            && cmtAgendamientoMgl.getTecnicoAnticipado()
                                    .equalsIgnoreCase("Y")||cmtAgendamientoMgl.getId() != null
                            && cmtAgendamientoMgl.getAgendaInmediata() != null
                            && cmtAgendamientoMgl.getAgendaInmediata()
                                    .equalsIgnoreCase("Y")) {

                        response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_OK);
                        response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_OK);
                        response.setStatus(CmtAgendamientoMglConstantes.STATUS_OK);
                        response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_OK);

                    } else if (cmtAgendamientoMgl.getId() != null) {

                        URL url = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.RESOURCES + asignarRecursoRequest.getIdTecnico());

                        userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                                .iterator().next().getParValor();
                        String comAutorization = "Basic" + " " + userPwdAutorization;

                        ConsumoGenerico consumoGenerico = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url,
                                comAutorization, null, null, null);

                        responsesResources = consumoGenerico.consumirServicioResources();
                        if (responsesResources.getResourceId() == null
                                || responsesResources.getResourceId().isEmpty()) {

                            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);

                        } else {

                            cmtAgendamientoMgl.setIdentificacionTecnico(responsesResources.getResourceId());
                            cmtAgendamientoMgl.setNombreTecnico(responsesResources.getName());
                            if (responsesResources.getXR_IdAliado() == null
                                    || responsesResources.getXR_IdAliado().isEmpty()) {
                                LOGGER.error("LA RESPUESTA NO TIENE INFORMACION DEL ALIADO");
                            } else {
                                String idAliadoFin="";
                                if (responsesResources.getXR_IdAliado().contains("-")) {
                                    String parts[] = responsesResources.getXR_IdAliado().split("-");
                                    if (parts != null && parts.length > 0) {
                                        idAliadoFin = parts[0];
                                    }
                                } else {
                                    idAliadoFin = responsesResources.getXR_IdAliado();
                                }                  
                                aliados = aliadosMglManager.findByIdAliado(new BigDecimal(idAliadoFin));
                                if (aliados != null && aliados.getIdAliado() != null) {
                                    cmtAgendamientoMgl.setIdentificacionAliado(aliados.getIdAliado().toString());
                                    cmtAgendamientoMgl.setNombreAliado(aliados.getNombre());
                                } else {
                                    LOGGER.error("NO HAY INFORMACION DEL ALIADO EN BD.");
                                }

                            }
                            if (asignarRecursoRequest.getIdMensaje() > 0) {
                                cmtAgendamientoMgl.setIdMensajeAsignarRecurso(asignarRecursoRequest.getIdMensaje());
                            }
                            cmtAgendamientoMgl.setFechaAsigTecnico(new Date());
                            cmtAgendamientoMgl = manager.update(cmtAgendamientoMgl,
                                    asignarRecursoRequest.getUser().getLogin(), 1);

                            if (cmtAgendamientoMgl.getIdentificacionTecnico() != null) {
                                response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_OK);
                                response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_OK);
                                response.setStatus(CmtAgendamientoMglConstantes.STATUS_OK);
                                response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_OK);
                                //ASIGNO TECNICO A LAS OTRAS AGENDAS DEL MISMO TIPO
                                List<BigDecimal> idsEstForUpdate = estadosAgendasForClose();
                                //Consulto agendas a actualizar    
                                List<CmtAgendamientoMgl> agendas = manager.agendasForUpdateTecnico
                                           (cmtAgendamientoMgl.getOrdenTrabajo(), idsEstForUpdate,
                                        cmtAgendamientoMgl.getId(), cmtAgendamientoMgl.getFechaAgenda());
                                if (!agendas.isEmpty()) {
                                    for (CmtAgendamientoMgl agendaMgl : agendas) {
                                        agendaMgl.setIdentificacionTecnico(responsesResources.getResourceId());
                                        agendaMgl.setNombreTecnico(responsesResources.getName());
                                        if (aliados != null) {
                                            agendaMgl.setIdentificacionAliado(aliados.getIdAliado().toString());
                                            agendaMgl.setNombreAliado(aliados.getNombre());
                                        } else {
                                            LOGGER.error("NO HAY INFORMACION DEL ALIADO EN BD.");
                                        }
                                        if (asignarRecursoRequest.getIdMensaje() > 0) {
                                            agendaMgl.setIdMensajeAsignarRecurso(asignarRecursoRequest.getIdMensaje());
                                        }
                                        updateAgendasForTecnicoMgl(agendaMgl, responsesResources.getResourceId(),
                                                asignarRecursoRequest.getUser().getLogin(), 1);
                                    }
                                }

                            } else {
                                response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                                response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                                response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                                response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);
                            }
                        }
                    } else {
                        response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                        response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                        response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                        response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ENCUENTRA);
                    }
                } catch (ApplicationException | MalformedURLException e) {
                    LOGGER.error(ERROR_ACTUALIZA_AGENDA, e);
                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                    response.setDescription(e.getMessage());
                }
            } else if (controlCcmmDir.equalsIgnoreCase(ConstansClient.CONTROL_NUM_DIR)) {
                try {

                    MglAgendaDireccion agendaDirecciones;

                    agendaDirecciones = agendamientoHhppManager.buscarAgendaPorOtIdMgl(asignarRecursoRequest.getIdOt());
                    if (agendaDirecciones.getAgendaId() != null
                            && agendaDirecciones.getTecnicoAnticipado() != null
                            && agendaDirecciones.getTecnicoAnticipado()
                                    .equalsIgnoreCase("Y")||agendaDirecciones.getAgendaId() != null
                            && agendaDirecciones.getAgendaInmediata() != null
                            && agendaDirecciones.getAgendaInmediata()
                                    .equalsIgnoreCase("Y")) {

                        response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_OK);
                        response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_OK);
                        response.setStatus(CmtAgendamientoMglConstantes.STATUS_OK);
                        response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_OK);

                    } else if (agendaDirecciones.getAgendaId() != null) {

                        URL url = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.RESOURCES + asignarRecursoRequest.getIdTecnico());

                        userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                                .iterator().next().getParValor();
                        String comAutorization = "Basic" + " " + userPwdAutorization;

                        ConsumoGenerico consumoGenerico = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url,
                                comAutorization, null, null, null);

                        responsesResources = consumoGenerico.consumirServicioResources();
                        if (responsesResources.getResourceId() == null
                                || responsesResources.getResourceId().isEmpty()) {

                            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);

                        } else {

                            agendaDirecciones.setIdentificacionTecnico(responsesResources.getResourceId());
                            agendaDirecciones.setNombreTecnico(responsesResources.getName());
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
                                aliados = aliadosMglManager.findByIdAliado(new BigDecimal(idAliadoFin));
                                if (aliados != null && aliados.getIdAliado() != null) {
                                    agendaDirecciones.setIdentificacionAliado(aliados.getIdAliado().toString());
                                    agendaDirecciones.setNombreAliado(aliados.getNombre());
                                } else {
                                    LOGGER.error("NO HAY INFORMACION DEL ALIADO EN BD.");
                                }

                            }
                            if (asignarRecursoRequest.getIdMensaje() > 0) {
                                agendaDirecciones.setIdMensajeAsignarRecurso(asignarRecursoRequest.getIdMensaje());
                            }
                            agendaDirecciones.setFechaAsigTecnico(new Date());

                            agendaDirecciones = agendamientoHhppManager.update(agendaDirecciones,
                                    asignarRecursoRequest.getUser().getLogin(), 1);

                            if (agendaDirecciones.getIdentificacionTecnico() != null) {
                                response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_OK);
                                response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_OK);
                                response.setStatus(CmtAgendamientoMglConstantes.STATUS_OK);
                                response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_OK);

                                //ASIGNO TECNICO A LAS OTRAS AGENDAS DEL MISMO TIPO
                                List<BigDecimal> idsEstForUpdate = estadosAgendasForClose();
                                //Consulto agendas a actualizar  
                                String subtipoWorfoce = agendaDirecciones.getOrdenTrabajo().getTipoOtHhppId().getSubTipoOrdenOFSC().getCodigoBasica();

                                List<MglAgendaDireccion> agendas = agendamientoHhppManager.agendasForUpdateTecnico(agendaDirecciones.getOrdenTrabajo(), idsEstForUpdate, new BigDecimal(agendaDirecciones.getAgendaId()), subtipoWorfoce, agendaDirecciones.getFechaAgenda());
                                if (!agendas.isEmpty()) {
                                    for (MglAgendaDireccion agendaMgl : agendas) {
                                        agendaMgl.setIdentificacionTecnico(responsesResources.getResourceId());
                                        agendaMgl.setNombreTecnico(responsesResources.getName());
                                        if (aliados != null) {
                                            agendaMgl.setIdentificacionAliado(aliados.getIdAliado().toString());
                                            agendaMgl.setNombreAliado(aliados.getNombre());
                                        } else {
                                            LOGGER.error("NO HAY INFORMACION DEL ALIADO EN BD.");
                                        }
                                        if (asignarRecursoRequest.getIdMensaje() > 0) {
                                            agendaMgl.setIdMensajeAsignarRecurso(asignarRecursoRequest.getIdMensaje());
                                        }
                                        updateAgendasForTecnicoMglHhpp(agendaMgl, responsesResources.getResourceId(),
                                                asignarRecursoRequest.getUser().getLogin(), 1);
                                    }
                                }

                            } else {
                                response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                                response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                                response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                                response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);
                            }
                        }
                    } else {
                        response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                        response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                        response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                        response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ENCUENTRA);
                    }

                } catch (ApplicationException | MalformedURLException e) {
                    LOGGER.error(ERROR_ACTUALIZA_AGENDA, e);
                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                    response.setDescription(e.getMessage());
                }
            } else {
                response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                response.setDescription("No es una orden de MGL");
            }
        }
        return response;
    }

    /**
     * metodo para informar del inicio de una visita a MGL Autor: victor
     * bocanegra
     *
     * @param iniciarVisitaRequest
     * @return ServicesAgendamientosResponse
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public ServicesAgendamientosResponse iniciarVisitaAgendaMgl(IniciarVisitaRequest iniciarVisitaRequest)
            throws ApplicationException {

        ServicesAgendamientosResponse response = new ServicesAgendamientosResponse();
        GetResourcesResponses datosTecnico;
        CmtAliadosMglManager aliadosMglManager = new CmtAliadosMglManager();

        if (iniciarVisitaRequest.getIdTecnico() == null
                || iniciarVisitaRequest.getIdTecnico().isEmpty()) {
            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALTA);
            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALTA);
        } else if (iniciarVisitaRequest.getFechaInicio() == null
                || iniciarVisitaRequest.getFechaInicio().isEmpty()) {
            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALTA);
            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALTA);
        } else if (iniciarVisitaRequest.getIdOt() == null
                || iniciarVisitaRequest.getIdOt().isEmpty()) {
            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALTA);
            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALTA);
        } else {

            String controlCcmmDir = iniciarVisitaRequest.getIdOt().substring(0, 1);

            if (controlCcmmDir.equalsIgnoreCase(ConstansClient.CONTROL_NUM_CCMM)) {
                //ES UN NUMERO CREADO DESDE CCMM   
                try {

                    URL url = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.RESOURCES + iniciarVisitaRequest.getIdTecnico());

                    userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                            .iterator().next().getParValor();
                    String comAutorization = "Basic" + " " + userPwdAutorization;

                    ConsumoGenerico consumoGenerico = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url,
                            comAutorization, null, null, null);

                    datosTecnico = consumoGenerico.consumirServicioResources();
                    if (datosTecnico.getResourceId() == null
                            || datosTecnico.getResourceId().isEmpty()) {

                        response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                        response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                        response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                        response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);

                    } else {
                        CmtAgendamientoMgl cmtAgendamientoMgl;
                        cmtAgendamientoMgl = manager.buscarAgendaPorOtIdMgl(iniciarVisitaRequest.getIdOt());

                        if (cmtAgendamientoMgl.getId() != null) {
                            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date fechaIniV = formatoDelTexto.parse(iniciarVisitaRequest.getFechaInicio());
                            if (iniciarVisitaRequest.getIdMensaje() > 0) {
                                cmtAgendamientoMgl.setIdMensajeIniciarVt(iniciarVisitaRequest.getIdMensaje());
                            }
                            cmtAgendamientoMgl.setFechaInivioVt(fechaIniV);
                            cmtAgendamientoMgl.setIdentificacionTecnico(datosTecnico.getResourceId());
                            cmtAgendamientoMgl.setNombreTecnico(datosTecnico.getName());

                            if (datosTecnico.getXR_IdAliado() == null
                                    || datosTecnico.getXR_IdAliado().isEmpty()) {
                                LOGGER.error("LA RESPUESTA NO TIENE INFORMACION DEL ALIADO");
                            } else {
                                String idAliadoFin = "";
                                if (datosTecnico.getXR_IdAliado().contains("-")) {
                                    String parts[] = datosTecnico.getXR_IdAliado().split("-");
                                    if (parts != null && parts.length > 0) {
                                        idAliadoFin = parts[0];
                                    }
                                } else {
                                    idAliadoFin = datosTecnico.getXR_IdAliado();
                                }
                                CmtAliados aliados = aliadosMglManager.findByIdAliado(new BigDecimal(idAliadoFin));
                                if (aliados != null && aliados.getIdAliado() != null) {
                                    cmtAgendamientoMgl.setIdentificacionAliado(aliados.getIdAliado().toString());
                                    cmtAgendamientoMgl.setNombreAliado(aliados.getNombre());
                                } else {
                                    LOGGER.error("NO HAY INFORMACION DEL ALIADO EN BD.");
                                }
                            }
                            CmtBasicaMglManager basicaMglManager1 = new CmtBasicaMglManager();
                            CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
                            CmtTipoBasicaMgl cmtTipoBasicaMgl
                                    = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                                            Constant.TIPO_BASICA_ESTADOS_AGENDA);
                            cmtAgendamientoMgl.setBasicaIdEstadoAgenda(
                                    basicaMglManager1.findByBasicaCode(
                                            "EAG000", cmtTipoBasicaMgl.getTipoBasicaId()));
                            cmtAgendamientoMgl = manager.update(cmtAgendamientoMgl,
                                    iniciarVisitaRequest.getUser().getLogin(), 1);

                            if (cmtAgendamientoMgl.getFechaInivioVt() != null) {
                                response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_OK);
                                response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_OK);
                                response.setStatus(CmtAgendamientoMglConstantes.STATUS_OK);
                                response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_OK_IV);
                            } else {
                                response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                                response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                                response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                                response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);
                            }

                        } else {
                            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ENCUENTRA);
                        }
                    }
                } catch (ApplicationException | MalformedURLException | ParseException e) {
                    LOGGER.error(ERROR_ACTUALIZA_AGENDA, e);
                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                    response.setDescription(e.getMessage());
                }
            } else if (controlCcmmDir.equalsIgnoreCase(ConstansClient.CONTROL_NUM_DIR)) {
                try {

                    URL url = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.RESOURCES + iniciarVisitaRequest.getIdTecnico());

                    userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                            .iterator().next().getParValor();
                    String comAutorization = "Basic" + " " + userPwdAutorization;

                    ConsumoGenerico consumoGenerico = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url,
                            comAutorization, null, null, null);

                    datosTecnico = consumoGenerico.consumirServicioResources();
                    if (datosTecnico.getResourceId() == null
                            || datosTecnico.getResourceId().isEmpty()) {

                        response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                        response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                        response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                        response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);

                    } else {
                        MglAgendaDireccion agendaDirecciones;
                        agendaDirecciones = agendamientoHhppManager.buscarAgendaPorOtIdMgl(iniciarVisitaRequest.getIdOt());

                        if (agendaDirecciones.getAgendaId() != null) {
                            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date fechaIniV = formatoDelTexto.parse(iniciarVisitaRequest.getFechaInicio());
                            if (iniciarVisitaRequest.getIdMensaje() > 0) {
                                agendaDirecciones.setIdMensajeIniciarVt(iniciarVisitaRequest.getIdMensaje());
                            }
                            agendaDirecciones.setFechaInivioVt(fechaIniV);
                            agendaDirecciones.setIdentificacionTecnico(datosTecnico.getResourceId());
                            agendaDirecciones.setNombreTecnico(datosTecnico.getName());

                            if (datosTecnico.getXR_IdAliado() == null
                                    || datosTecnico.getXR_IdAliado().isEmpty()) {
                                LOGGER.error("LA RESPUESTA NO TIENE INFORMACION DEL ALIADO");
                            } else {
                                String idAliadoFin = "";
                                if (datosTecnico.getXR_IdAliado().contains("-")) {
                                    String parts[] = datosTecnico.getXR_IdAliado().split("-");
                                    if (parts != null && parts.length > 0) {
                                        idAliadoFin = parts[0];
                                    }
                                } else {
                                    idAliadoFin = datosTecnico.getXR_IdAliado();
                                }
                                CmtAliados aliados = aliadosMglManager.findByIdAliado(new BigDecimal(idAliadoFin));
                                if (aliados != null && aliados.getIdAliado() != null) {
                                    agendaDirecciones.setIdentificacionAliado(aliados.getIdAliado().toString());
                                    agendaDirecciones.setNombreAliado(aliados.getNombre());
                                } else {
                                    LOGGER.error("NO HAY INFORMACION DEL ALIADO EN BD.");
                                }
                            }
                            CmtBasicaMgl estadoAgBasicaMgl = basicaMglManager.
                                    findByCodigoInternoApp(Constant.ESTADO_AGENDA_AGENDADA);

                            agendaDirecciones.setBasicaIdEstadoAgenda(estadoAgBasicaMgl);
                            agendaDirecciones = agendamientoHhppManager.update(agendaDirecciones,
                                    iniciarVisitaRequest.getUser().getLogin(), 1);

                            if (agendaDirecciones.getFechaInivioVt() != null) {
                                response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_OK);
                                response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_OK);
                                response.setStatus(CmtAgendamientoMglConstantes.STATUS_OK);
                                response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_OK_IV);
                            } else {
                                response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                                response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                                response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                                response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);
                            }

                        } else {
                            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ENCUENTRA);
                        }
                    }
                } catch (ApplicationException | MalformedURLException | ParseException e) {
                    LOGGER.error(ERROR_ACTUALIZA_AGENDA, e);
                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                    response.setDescription(e.getMessage());
                }
            } else {
                response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                response.setDescription("No es una orden de MGL");
            }
        }

        return response;
    }

    /**
     * metodo no_Done para informar de la no realizacion de la actividad Autor:
     * victor bocanegra
     *
     * @param unrealizedActivityRequest
     * @return ServicesAgendamientosResponse
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public ServicesAgendamientosResponse actividadNoRealizadaAgendaMgl(UnrealizedActivityRequest unrealizedActivityRequest)
            throws ApplicationException {

        ServicesAgendamientosResponse response = new ServicesAgendamientosResponse();
        GetResourcesResponses datosTecnico;
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        CmtAliadosMglManager aliadosMglManager = new CmtAliadosMglManager();
        CmtDetalleFlujoMglManager cmtDetalleFlujoMglManager = new CmtDetalleFlujoMglManager();
        List<CmtDetalleFlujoMgl> detalleFlujoEstActual;
        
        

        if (unrealizedActivityRequest.getIdTecnico() == null
                || unrealizedActivityRequest.getIdTecnico().isEmpty()) {
            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALTA);
            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALTA);
        } else if (unrealizedActivityRequest.getOrden() == null
                || unrealizedActivityRequest.getOrden().isEmpty()) {
            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALTA);
            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALTA);
        } else if (unrealizedActivityRequest.getRazon() == null
                || unrealizedActivityRequest.getRazon().isEmpty()) {
            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALTA);
            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALTA);
        } else if (unrealizedActivityRequest.getObservaciones() == null
                || unrealizedActivityRequest.getObservaciones().isEmpty()) {
            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALTA);
            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALTA);
        } else {

            String controlCcmmDir = unrealizedActivityRequest.getOrden().substring(0, 1);

            if (controlCcmmDir.equalsIgnoreCase(ConstansClient.CONTROL_NUM_CCMM)) {
                //ES UN NUMERO CREADO DESDE CCMM 
                try {
                    URL url = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.RESOURCES + unrealizedActivityRequest.getIdTecnico());

                    userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                            .iterator().next().getParValor();
                    String comAutorization = "Basic" + " " + userPwdAutorization;

                    ConsumoGenerico consumoGenerico = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url,
                            comAutorization, null, null, null);

                    datosTecnico = consumoGenerico.consumirServicioResources();
                    if (datosTecnico.getResourceId() == null
                            || datosTecnico.getResourceId().isEmpty()) {

                        response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                        response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                        response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                        response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);

                    } else {
                        CmtAgendamientoMgl cmtAgendamientoMgl;
                        cmtAgendamientoMgl = manager.buscarAgendaPorOtIdMgl(unrealizedActivityRequest.getOrden());

                        if (cmtAgendamientoMgl.getId() != null) {
                            CmtBasicaMgl basicaMgl= null;
                            
                            CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
                            CmtTipoBasicaMgl cmtTipoBasicaMgl
                                    = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                                            Constant.TIPO_BASICA_RAZONES_NODONE);
                            if (cmtTipoBasicaMgl != null) {
                                basicaMgl = cmtBasicaMglManager.findByTipoBasicaAndCodigo(cmtTipoBasicaMgl.getTipoBasicaId(), unrealizedActivityRequest.getRazon());
                            }
                           
                            if (basicaMgl != null) {
                                cmtAgendamientoMgl.setBasicaIdrazones(basicaMgl);
                                if (unrealizedActivityRequest.getIdMensaje() > 0) {
                                    cmtAgendamientoMgl.setIdMensajeNodone(unrealizedActivityRequest.getIdMensaje());
                                }
                                cmtAgendamientoMgl.setIdentificacionTecnico(datosTecnico.getResourceId());
                                cmtAgendamientoMgl.setNombreTecnico(datosTecnico.getName());
                                cmtAgendamientoMgl.setIdentificacionAliado(datosTecnico.getXR_IdAliado());

                                if (datosTecnico.getXR_IdAliado() == null
                                        || datosTecnico.getXR_IdAliado().isEmpty()) {
                                    LOGGER.error("LA RESPUESTA NO TIENE INFORMACION DEL ALIADO");
                                } else {
                                    String idAliadoFin = "";
                                    if (datosTecnico.getXR_IdAliado().contains("-")) {
                                        String parts[] = datosTecnico.getXR_IdAliado().split("-");
                                        if (parts != null && parts.length > 0) {
                                            idAliadoFin = parts[0];
                                        }
                                    } else {
                                        idAliadoFin = datosTecnico.getXR_IdAliado();
                                    }
                                    CmtAliados aliados = aliadosMglManager.findByIdAliado(new BigDecimal(idAliadoFin));
                                    if (aliados != null && aliados.getIdAliado() != null) {
                                        cmtAgendamientoMgl.setIdentificacionAliado(aliados.getIdAliado().toString());
                                        cmtAgendamientoMgl.setNombreAliado(aliados.getNombre());
                                    } else {
                                        LOGGER.error("NO HAY INFORMACION DEL ALIADO EN BD.");
                                    }

                                }
                                CmtBasicaMgl estadoAgBasicaMgl = basicaMglManager.
                                        findByCodigoInternoApp(Constant.ESTADO_AGENDA_NODONE);

                                cmtAgendamientoMgl.setBasicaIdEstadoAgenda(estadoAgBasicaMgl);
                                cmtAgendamientoMgl.setObservacionesTecnico(unrealizedActivityRequest.getObservaciones());
                                
                                // Actualiza orden estados razonados
                                CmtOrdenTrabajoMgl otMgl = cmtAgendamientoMgl.getOrdenTrabajo();
                                otMgl = ordenTrabajoMglManager.findOtById(otMgl.getIdOt());
                                if (otMgl != null && otMgl.getTipoOtObj() != null) {
                                    detalleFlujoEstActual = cmtDetalleFlujoMglManager.
                                            findByTipoFlujoAndEstadoIniAndTecRazon(otMgl.getTipoOtObj().getTipoFlujoOt(),
                                                    otMgl.getEstadoInternoObj(), otMgl.getBasicaIdTecnologia());

                                    CmtDetalleFlujoMgl detalleFlujoMglOtRazonado = null;
                                    if (detalleFlujoEstActual != null && !detalleFlujoEstActual.isEmpty()) {
                                        for (CmtDetalleFlujoMgl detalleFlujoMgl : detalleFlujoEstActual) {
                                            if (detalleFlujoMgl.getBasicaRazonNodone().
                                                    getBasicaId().compareTo(basicaMgl.getBasicaId()) == 0) {
                                                detalleFlujoMglOtRazonado = detalleFlujoMgl;
                                            }
                                        }

                                    }
                                    if (detalleFlujoMglOtRazonado != null) {
                                        //Actualizo la orden con el estado final del detalle flujo
                                        otMgl.setEstadoInternoObj(detalleFlujoMglOtRazonado.getProximoEstado());
                                        ordenTrabajoMglManager.actualizarOtCcmm
                                         (otMgl,  unrealizedActivityRequest.getUser().getLogin(), 1);
                                    }
                                }
                                // Fin actualiza orden estados razonados

                                cmtAgendamientoMgl = manager.update(cmtAgendamientoMgl,
                                        unrealizedActivityRequest.getUser().getLogin(), 1);

                                if (cmtAgendamientoMgl.getBasicaIdrazones() != null) {
                                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_OK);
                                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_OK);
                                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_OK);
                                    response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_OK_NODONE);
                                } else {
                                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                                    response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);
                                }
                                if (cmtAgendamientoMgl.getOrdenTrabajo().getTipoOtObj() != null
                                        && cmtAgendamientoMgl.getOrdenTrabajo().getTipoOtObj().getRequiereOnyx()
                                        != null && cmtAgendamientoMgl.getOrdenTrabajo().getTipoOtObj().
                                                getRequiereOnyx().equalsIgnoreCase("Y")) {
                                    updateCloseOrdenOnix(cmtAgendamientoMgl, null,
                                            2, unrealizedActivityRequest.getRazon(),
                                            unrealizedActivityRequest.getUser().getLogin(),
                                            unrealizedActivityRequest.getObservaciones());
                                }
                            } else {
                                response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                                response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                                response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                                response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_NO_HAY_RAZON);
                            }
                        } else {
                            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ENCUENTRA);
                        }
                    }
                } catch (ApplicationException | MalformedURLException e) {
                    LOGGER.error(ERROR_ACTUALIZA_AGENDA, e);
                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                    response.setDescription(e.getMessage());
                }
            } else if (controlCcmmDir.equalsIgnoreCase(ConstansClient.CONTROL_NUM_DIR)) {
                try {
                    URL url = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.RESOURCES + unrealizedActivityRequest.getIdTecnico());

                    userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                            .iterator().next().getParValor();
                    String comAutorization = "Basic" + " " + userPwdAutorization;

                    ConsumoGenerico consumoGenerico = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url,
                            comAutorization, null, null, null);

                    datosTecnico = consumoGenerico.consumirServicioResources();
                    if (datosTecnico.getResourceId() == null
                            || datosTecnico.getResourceId().isEmpty()) {

                        response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                        response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                        response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                        response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);

                    } else {
                        MglAgendaDireccion agendaDirecciones;
                        agendaDirecciones = agendamientoHhppManager.buscarAgendaPorOtIdMgl(unrealizedActivityRequest.getOrden());

                        if (agendaDirecciones.getAgendaId() != null) {
                            CmtBasicaMgl basicaMgl = null;
                            
                            CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
                            CmtTipoBasicaMgl cmtTipoBasicaMgl
                                    = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                                            Constant.TIPO_BASICA_RAZONES_NODONE);
                            if (cmtTipoBasicaMgl != null) {
                                basicaMgl = cmtBasicaMglManager.findByTipoBasicaAndCodigo(cmtTipoBasicaMgl.getTipoBasicaId(), unrealizedActivityRequest.getRazon());
                            }

                            if (basicaMgl != null) {
                                agendaDirecciones.setBasicaIdrazones(basicaMgl);
                                if (unrealizedActivityRequest.getIdMensaje() > 0) {
                                    agendaDirecciones.setIdMensajeNodone(unrealizedActivityRequest.getIdMensaje());
                                }
                                agendaDirecciones.setIdentificacionTecnico(datosTecnico.getResourceId());
                                agendaDirecciones.setNombreTecnico(datosTecnico.getName());
                                agendaDirecciones.setIdentificacionAliado(datosTecnico.getXR_IdAliado());

                                if (datosTecnico.getXR_IdAliado() == null
                                        || datosTecnico.getXR_IdAliado().isEmpty()) {
                                    LOGGER.error("LA RESPUESTA NO TIENE INFORMACION DEL ALIADO");
                                } else {
                                    String idAliadoFin = "";
                                    if (datosTecnico.getXR_IdAliado().contains("-")) {
                                        String parts[] = datosTecnico.getXR_IdAliado().split("-");
                                        if (parts != null && parts.length > 0) {
                                            idAliadoFin = parts[0];
                                        }
                                    } else {
                                        idAliadoFin = datosTecnico.getXR_IdAliado();
                                    }
                                    CmtAliados aliados = aliadosMglManager.findByIdAliado(new BigDecimal(idAliadoFin));
                                    if (aliados != null && aliados.getIdAliado() != null) {
                                        agendaDirecciones.setIdentificacionAliado(aliados.getIdAliado().toString());
                                        agendaDirecciones.setNombreAliado(aliados.getNombre());
                                    } else {
                                        LOGGER.error("NO HAY INFORMACION DEL ALIADO EN BD.");
                                    }

                                }
                                CmtBasicaMgl estadoAgBasicaMgl = basicaMglManager.
                                        findByCodigoInternoApp(Constant.ESTADO_AGENDA_NODONE);

                                agendaDirecciones.setBasicaIdEstadoAgenda(estadoAgBasicaMgl);
                                agendaDirecciones.setObservacionesTecnico(unrealizedActivityRequest.getObservaciones());

                                agendaDirecciones = agendamientoHhppManager.update(agendaDirecciones,
                                        unrealizedActivityRequest.getUser().getLogin(), 1);

                                if (agendaDirecciones.getBasicaIdrazones() != null) {
                                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_OK);
                                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_OK);
                                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_OK);
                                    response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_OK_NODONE);
                                } else {
                                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                                    response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);
                                }
                                if (agendaDirecciones.getOrdenTrabajo().getTipoOtHhppId() != null
                                        && agendaDirecciones.getOrdenTrabajo().getTipoOtHhppId().getRequiereOnyx()
                                        != null && agendaDirecciones.getOrdenTrabajo().getTipoOtHhppId().
                                                getRequiereOnyx().equalsIgnoreCase("Y")) {
                                    updateCloseOrdenOnix(null, agendaDirecciones,
                                            2, unrealizedActivityRequest.getRazon(),
                                            unrealizedActivityRequest.getUser().getLogin(),
                                            unrealizedActivityRequest.getObservaciones());
                                }
                            } else {
                                response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                                response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                                response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                                response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_NO_HAY_RAZON);
                            }
                        } else {
                            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ENCUENTRA);
                        }
                    }
                } catch (ApplicationException | MalformedURLException e) {
                    LOGGER.error(ERROR_ACTUALIZA_AGENDA, e);
                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                    response.setDescription(e.getMessage());
                }
            } else {
                response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                response.setDescription("No es una orden de MGL");
            }
        }
        return response;
    }

    /**
     * metodo para informar a MGL que una OT termino Autor: victor bocanegra
     *
     * @param orderCompleteRequest
     * @return ServicesAgendamientosResponse
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public ServicesAgendamientosResponse ordenTerminadaAgendaMgl(OrderCompleteRequest orderCompleteRequest)
            throws ApplicationException {

        ServicesAgendamientosResponse response = new ServicesAgendamientosResponse();
        GetResourcesResponses datosTecnico;
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        CmtAliadosMglManager aliadosMglManager = new CmtAliadosMglManager();
        int controlCierre = 0;

        if (orderCompleteRequest.getIdTecnico() == null
                || orderCompleteRequest.getIdTecnico().isEmpty()) {
            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALTA);
            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALTA);
        } else if (orderCompleteRequest.getFechaSalidaTecnico() == null
                || orderCompleteRequest.getFechaSalidaTecnico().isEmpty()) {
            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALTA);
            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALTA);
        } else if (orderCompleteRequest.getIdOT() == null
                || orderCompleteRequest.getIdOT().isEmpty()) {
            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALTA);
            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALTA);
        } else {

            String controlCcmmDir = orderCompleteRequest.getIdOT().substring(0, 1);

            if (controlCcmmDir.equalsIgnoreCase(ConstansClient.CONTROL_NUM_CCMM)) {
                //ES UN NUMERO CREADO DESDE CCMM
                try {

                    URL url = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.RESOURCES + orderCompleteRequest.getIdTecnico());

                    userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                            .iterator().next().getParValor();
                    String comAutorization = "Basic" + " " + userPwdAutorization;

                    ConsumoGenerico consumoGenerico = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url,
                            comAutorization, null, null, null);

                    datosTecnico = consumoGenerico.consumirServicioResources();
                    if (datosTecnico.getResourceId() == null
                            || datosTecnico.getResourceId().isEmpty()) {

                        response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                        response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                        response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                        response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);

                    } else {

                        CmtAgendamientoMgl cmtAgendamientoMgl;
                        cmtAgendamientoMgl = manager.buscarAgendaPorOtIdMgl(orderCompleteRequest.getIdOT());

                        if (cmtAgendamientoMgl.getId() != null) {
                            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date fechaFinV = formatoDelTexto.parse(orderCompleteRequest.getFechaSalidaTecnico());
                            if (orderCompleteRequest.getIdMensaje() > 0) {
                                cmtAgendamientoMgl.setIdMensajeHardclose(orderCompleteRequest.getIdMensaje());
                            }

                            if (orderCompleteRequest.getRazon() != null && !orderCompleteRequest.getRazon().trim().isEmpty()) {
                                //Consulto la basica de cerrar agenda
                                CmtBasicaMgl cierraAgeBasicaMgl = cmtBasicaMglManager.findByCodigoInternoApp(Constant.BASICA_RAZON_CERRAR);

                                cmtAgendamientoMgl.setFechaFinVt(fechaFinV);
                                CmtBasicaMgl basicaMgl= null;
                               
                                CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
                                CmtTipoBasicaMgl cmtTipoBasicaMgl
                                        = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                                                Constant.TIPO_BASICA_RAZONES_NODONE);
                                if (cmtTipoBasicaMgl != null) {
                                    basicaMgl = cmtBasicaMglManager.findByTipoBasicaAndCodigo(cmtTipoBasicaMgl.getTipoBasicaId(), orderCompleteRequest.getRazon());
                                }
                                
                                if (basicaMgl != null) {

                                    cmtAgendamientoMgl.setBasicaIdrazones(basicaMgl);

                                    cmtAgendamientoMgl.setIdentificacionTecnico(datosTecnico.getResourceId());
                                    cmtAgendamientoMgl.setNombreTecnico(datosTecnico.getName());
                                    cmtAgendamientoMgl.setIdentificacionAliado(datosTecnico.getXR_IdAliado());

                                    CmtBasicaMgl estadoAgBasicaMgl = basicaMglManager.
                                            findByCodigoInternoApp(Constant.ESTADO_AGENDA_CERRADA);

                                    cmtAgendamientoMgl.setBasicaIdEstadoAgenda(estadoAgBasicaMgl);

                                    if (datosTecnico.getXR_IdAliado() == null
                                            || datosTecnico.getXR_IdAliado().isEmpty()) {
                                        LOGGER.error("LA RESPUESTA NO TIENE INFORMACION DEL ALIADO");
                                    } else {
                                        String idAliadoFin = "";
                                        if (datosTecnico.getXR_IdAliado().contains("-")) {
                                            String parts[] = datosTecnico.getXR_IdAliado().split("-");
                                            if (parts != null && parts.length > 0) {
                                                idAliadoFin = parts[0];
                                            }
                                        } else {
                                            idAliadoFin = datosTecnico.getXR_IdAliado();
                                        }
                                        CmtAliados aliados = aliadosMglManager.findByIdAliado(new BigDecimal(idAliadoFin));
                                        if (aliados != null && aliados.getIdAliado() != null) {
                                            cmtAgendamientoMgl.setIdentificacionAliado(aliados.getIdAliado().toString());
                                            cmtAgendamientoMgl.setNombreAliado(aliados.getNombre());
                                        } else {
                                            LOGGER.error("NO HAY INFORMACION DEL ALIADO EN BD.");
                                        }

                                    }

                                    //Validacion si el codigo de cierre es el OK manual
                                    if (basicaMgl.getBasicaId().compareTo(cierraAgeBasicaMgl.getBasicaId()) == 0) {

                                        //Cancelo Agendas pendientes por ser cierre OK manual
                                        List<BigDecimal> idsEstForClose = estadosAgendasForClose();

                                        List<CmtAgendamientoMgl> agendas = manager.agendasForCancelar(cmtAgendamientoMgl.getOrdenTrabajo(), idsEstForClose,
                                                cmtAgendamientoMgl.getId(), cmtAgendamientoMgl.getFechaAgenda());

                                        if (!agendas.isEmpty()) {
                                            for (CmtAgendamientoMgl agenda : agendas) {
                                                //cancelo agenda
                                                cancelar(agenda, Constant.RAZON_CANCELAR, Constant.COMENTARIO_CANCELAR,
                                                        orderCompleteRequest.getUser().getLogin(), 1);
                                                LOGGER.error("AGENDA  " + agenda.getOfpsOtId() + " CANCELADA");
                                            }
                                        } else {
                                            LOGGER.error("NO HAY AGENDAS PARA CANCELAR");
                                        }
                                        //Fin cancelo Agendas pendientes por ser cierre OK manual

                                        //Actualizo la orden en RR por se cierre OK manual
                                        CmtOrdenTrabajoMgl otMgl = cmtAgendamientoMgl.getOrdenTrabajo();
                                        otMgl = ordenTrabajoMglManager.findOtById(otMgl.getIdOt());
                                        CmtOrdenTrabajoRrMgl ordenRR = new CmtOrdenTrabajoRrMgl();
                                        ordenRR.setOtObj(otMgl);
                                        CmtEstadoxFlujoMgl estadoXFlujo;
                                        ParametrosMgl parametroHabilitarOtInRr = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);

                                        if (parametroHabilitarOtInRr != null && parametroHabilitarOtInRr.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {

                                            estadoXFlujo = estadoxFlujoMglManager.findByTipoFlujoAndEstadoInt(otMgl.getTipoOtObj().getTipoFlujoOt(), otMgl.getEstadoInternoObj(), otMgl.getBasicaIdTecnologia());
                                            if (estadoXFlujo != null
                                                    && estadoXFlujo.getEstadoOtRRInicial() != null
                                                    && estadoXFlujo.getEstadoOtRRFinal() != null) {
                                                String notaCierre="Cierre Ultima Agenda MER-"+cmtAgendamientoMgl.getOfpsOtId()+""
                                                        + "-"+orderCompleteRequest.getUser().getLogin()+""
                                                        + "-"+cmtAgendamientoMgl.getNodoMgl().getNodId()+"";
                                                ordenTrabajoRrMglManager.actualizarEstadoResultadoOTRR(ordenRR, cmtAgendamientoMgl.getIdOtenrr(),
                                                        orderCompleteRequest.getUser().getLogin(), estadoXFlujo, false, notaCierre);
                                            }

                                        }
                                        //Fin actualizo la orden en RR por se cierre OK manual
                                        cmtAgendamientoMgl.setUltimaAgenda("Y");
                                        controlCierre = 1;
                                    } else {
                                        controlCierre = 2;
                                    }
                                    
                                    cmtAgendamientoMgl.setObservacionesTecnico(orderCompleteRequest.getNotasFirma());

                                    cmtAgendamientoMgl = manager.update(cmtAgendamientoMgl,
                                            orderCompleteRequest.getUser().getLogin(), 1);
                                    
                                    CmtOnyxResponseDto onyxResponseDto = returnInfoOnix(cmtAgendamientoMgl);
                                    cmtAgendamientoMgl.setCmtOnyxResponseDto(onyxResponseDto);

                                    try {
                                        cargarInformacionForEnvioNotificacion(cmtAgendamientoMgl, 4);
                                    } catch (ApplicationException ex) {
                                        String msn = "Ocurrio un error al momento de "
                                                + "enviar notificacion de completado de agenda: " + ex.getMessage() + "";
                                        LOGGER.info(msn);
                                    }

                                    if (cmtAgendamientoMgl.getFechaFinVt() != null) {
                                        response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_OK);
                                        response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_OK);
                                        response.setStatus(CmtAgendamientoMglConstantes.STATUS_OK);
                                        response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_OK_HARDCLOSE);
                                    } else {
                                        response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                                        response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                                        response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                                        response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);
                                    }
                                    if (cmtAgendamientoMgl.getOrdenTrabajo().getTipoOtObj() != null
                                            && cmtAgendamientoMgl.getOrdenTrabajo().getTipoOtObj().getRequiereOnyx()
                                            != null && cmtAgendamientoMgl.getOrdenTrabajo().getTipoOtObj().
                                                    getRequiereOnyx().equalsIgnoreCase("Y")) {
                                        updateCloseOrdenOnix(cmtAgendamientoMgl, null, controlCierre,
                                                orderCompleteRequest.getRazon(), orderCompleteRequest.getUser().getLogin(),
                                                null);
                                    }
                                  
                                } else {
                                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                                    response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_NO_HAY_RAZON);
                                }
                            } else {
                                //Actualizo sin codigo razon
                                controlCierre = 3;
                                cmtAgendamientoMgl.setFechaFinVt(fechaFinV);
                                cmtAgendamientoMgl.setIdentificacionTecnico(datosTecnico.getResourceId());
                                cmtAgendamientoMgl.setNombreTecnico(datosTecnico.getName());
                                cmtAgendamientoMgl.setIdentificacionAliado(datosTecnico.getXR_IdAliado());

                                CmtBasicaMgl estadoAgBasicaMgl = basicaMglManager.
                                        findByCodigoInternoApp(Constant.ESTADO_AGENDA_CERRADA);

                                cmtAgendamientoMgl.setBasicaIdEstadoAgenda(estadoAgBasicaMgl);

                                if (datosTecnico.getXR_IdAliado() == null
                                        || datosTecnico.getXR_IdAliado().isEmpty()) {
                                    LOGGER.error("LA RESPUESTA NO TIENE INFORMACION DEL ALIADO");
                                } else {
                                    String idAliadoFin = "";
                                    if (datosTecnico.getXR_IdAliado().contains("-")) {
                                        String parts[] = datosTecnico.getXR_IdAliado().split("-");
                                        if (parts != null && parts.length > 0) {
                                            idAliadoFin = parts[0];
                                        }
                                    } else {
                                        idAliadoFin = datosTecnico.getXR_IdAliado();
                                    }
                                    CmtAliados aliados = aliadosMglManager.findByIdAliado(new BigDecimal(idAliadoFin));
                                    if (aliados != null && aliados.getIdAliado() != null) {
                                        cmtAgendamientoMgl.setIdentificacionAliado(aliados.getIdAliado().toString());
                                        cmtAgendamientoMgl.setNombreAliado(aliados.getNombre());
                                    } else {
                                        LOGGER.error("NO HAY INFORMACION DEL ALIADO EN BD.");
                                    }

                                }

                                cmtAgendamientoMgl.setObservacionesTecnico(orderCompleteRequest.getNotasFirma());

                                cmtAgendamientoMgl = manager.update(cmtAgendamientoMgl,
                                        orderCompleteRequest.getUser().getLogin(), 1);
                                
                                CmtOnyxResponseDto onyxResponseDto = returnInfoOnix(cmtAgendamientoMgl);
                                cmtAgendamientoMgl.setCmtOnyxResponseDto(onyxResponseDto);
                                try {
                                    cargarInformacionForEnvioNotificacion(cmtAgendamientoMgl, 4);
                                } catch (ApplicationException ex) {
                                    String msn = "Ocurrio un error al momento de "
                                            + "enviar notificacion de completado de agenda: " + ex.getMessage() + "";
                                    LOGGER.info(msn);
                                }

                                if (cmtAgendamientoMgl.getFechaFinVt() != null) {
                                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_OK);
                                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_OK);
                                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_OK);
                                    response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_OK_HARDCLOSE);
                                } else {
                                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                                    response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);
                                }
                                if (cmtAgendamientoMgl.getOrdenTrabajo().getTipoOtObj() != null
                                        && cmtAgendamientoMgl.getOrdenTrabajo().getTipoOtObj().getRequiereOnyx()
                                        != null && cmtAgendamientoMgl.getOrdenTrabajo().getTipoOtObj().
                                                getRequiereOnyx().equalsIgnoreCase("Y")) {
                                    updateCloseOrdenOnix(cmtAgendamientoMgl, null, controlCierre,
                                            orderCompleteRequest.getRazon(), orderCompleteRequest.getUser().getLogin(),
                                            null);
                                }
                            }
                        } else {
                            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ENCUENTRA);
                        }

                    }
                } catch (ApplicationException | ParseException
                        |/*DatatypeConfigurationException |*/IOException e) {
                    
                    LOGGER.error(ERROR_ACTUALIZA_AGENDA, e);
                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                    response.setDescription(e.getMessage());
                }
            } else if (controlCcmmDir.equalsIgnoreCase(ConstansClient.CONTROL_NUM_DIR)) {

                try {
                    URL url = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.RESOURCES + orderCompleteRequest.getIdTecnico());

                    userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                            .iterator().next().getParValor();
                    String comAutorization = "Basic" + " " + userPwdAutorization;

                    ConsumoGenerico consumoGenerico = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url,
                            comAutorization, null, null, null);

                    datosTecnico = consumoGenerico.consumirServicioResources();
                    if (datosTecnico.getResourceId() == null
                            || datosTecnico.getResourceId().isEmpty()) {

                        response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                        response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                        response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                        response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);

                    } else {

                        MglAgendaDireccion agendaDirecciones;
                        agendaDirecciones = agendamientoHhppManager.buscarAgendaPorOtIdMgl(orderCompleteRequest.getIdOT());

                        if (agendaDirecciones.getAgendaId() != null) {
                            if (agendaDirecciones.getOrdenTrabajo().getTipoOtHhppId().getIsMultiagenda().equalsIgnoreCase("N")) {
                                SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date fechaFinV = formatoDelTexto.parse(orderCompleteRequest.getFechaSalidaTecnico());
                                if (orderCompleteRequest.getIdMensaje() > 0) {
                                    agendaDirecciones.setIdMensajeHardclose(orderCompleteRequest.getIdMensaje());
                                }

                                agendaDirecciones.setFechaFinVt(fechaFinV);
                                CmtBasicaMgl basicaMgl = null;

                                CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
                                CmtTipoBasicaMgl cmtTipoBasicaMgl
                                        = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                                                Constant.TIPO_BASICA_RAZONES_NODONE);
                                if (cmtTipoBasicaMgl != null) {
                                    basicaMgl = cmtBasicaMglManager.findByTipoBasicaAndCodigo(cmtTipoBasicaMgl.getTipoBasicaId(), orderCompleteRequest.getRazon());
                                }

                                agendaDirecciones.setEstadoVisita(null);
                                agendaDirecciones.setUltimaAgenda("Y");
                                controlCierre = 1;
                                
                                if (basicaMgl != null) {
                                    agendaDirecciones.setBasicaIdrazones(basicaMgl);
                                }
                                agendaDirecciones.setIdentificacionTecnico(datosTecnico.getResourceId());
                                agendaDirecciones.setNombreTecnico(datosTecnico.getName());
                                agendaDirecciones.setIdentificacionAliado(datosTecnico.getXR_IdAliado());

                                CmtBasicaMgl estadoAgBasicaMgl = basicaMglManager.
                                        findByCodigoInternoApp(Constant.ESTADO_AGENDA_CERRADA);

                                if (agendaDirecciones.getBasicaIdEstadoAgenda().getBasicaId()
                                        .compareTo(estadoAgBasicaMgl.getBasicaId()) == 0) {
                                } else {
                                    agendaDirecciones.setBasicaIdEstadoAgenda(estadoAgBasicaMgl);

                                    if (datosTecnico.getXR_IdAliado() == null
                                            || datosTecnico.getXR_IdAliado().isEmpty()) {
                                        LOGGER.error("LA RESPUESTA NO TIENE INFORMACION DEL ALIADO");
                                    } else {
                                        String idAliadoFin = "";
                                        if (datosTecnico.getXR_IdAliado().contains("-")) {
                                            String parts[] = datosTecnico.getXR_IdAliado().split("-");
                                            if (parts != null && parts.length > 0) {
                                                idAliadoFin = parts[0];
                                            }
                                        } else {
                                            idAliadoFin = datosTecnico.getXR_IdAliado();
                                        }
                                        CmtAliados aliados = aliadosMglManager.findByIdAliado(new BigDecimal(idAliadoFin));
                                        if (aliados != null && aliados.getIdAliado() != null) {
                                            agendaDirecciones.setIdentificacionAliado(aliados.getIdAliado().toString());
                                            agendaDirecciones.setNombreAliado(aliados.getNombre());
                                        } else {
                                            LOGGER.error("NO HAY INFORMACION DEL ALIADO EN BD.");
                                        }
                                    }

                                    OtHhppMgl ordenDeTrabajoDeLaAgenda = agendaDirecciones.getOrdenTrabajo();

                                    //Valida si el tipo de Ot Permite tecnologias
                                    if (ordenDeTrabajoDeLaAgenda.getTipoOtHhppId().
                                            getManejaTecnologias().compareTo(BigDecimal.ONE) == 0) {

                                        //consulta la Ot en OSFC para saber el estado de las tecnologias
                                        GetTechnologiesResponse estadoTecnologiasOfsc
                                                = consultarTecnologiasOtOfsc(agendaDirecciones);

                                        //guarda en Mgl el estado de las tecnologias
                                        guardarEstadoTecnologiasOsfcEnMgl(orderCompleteRequest,
                                                agendaDirecciones, estadoTecnologiasOfsc);

                                        //Valida si el tipo de Ot permite ampliacion de Tab
                                        if (ordenDeTrabajoDeLaAgenda.getTipoOtHhppId().
                                                getAmpliacionTab().compareTo(BigDecimal.ONE) == 0) {

                                            //Valida si alguna tecnologia requiere OT de ampliacion de tab
                                            //Y de ser necesario la crea en Mgl
                                            validarYCrearOtDeAmpliacionDeTab(agendaDirecciones,
                                                    orderCompleteRequest.getUser().getLogin(), 1);
                                        }

                                        //Consulta las tecnologias viables para creacion de Hhpp
                                        List<OtHhppTecnologiaMgl> tecnologiasViablesOt
                                                = otHhppTecnologiaMglManager.findTecnologiasViables(
                                                        agendaDirecciones.getOrdenTrabajo().getOtHhppId());

                                        //Valida si existen tecnologias viables para creacion de Hhpp
                                        if (!tecnologiasViablesOt.isEmpty()) {
                                            //Creacion de Hhpp
                                            crearHhppConTecnologiasViables(tecnologiasViablesOt, orderCompleteRequest);
                                        }
                                    }

                                }
                                CmtBasicaMgl basicaVisitaExitosa
                                        = cmtBasicaMglManager.findByCodigoInternoApp(
                                                Constant.VISITA_DOMICILIARIA_EXITOSA);

                                agendaDirecciones.setEstadoVisita(basicaVisitaExitosa);
                                agendaDirecciones.setObservacionesTecnico(orderCompleteRequest.getNotasFirma());

                                agendaDirecciones = agendamientoHhppManager.update(agendaDirecciones,
                                        orderCompleteRequest.getUser().getLogin(), 1);
                                
                                AgendamientoHhppWorkForceMglManager agendamientoHhppWorkForceMglManager = new AgendamientoHhppWorkForceMglManager();
                                CmtOnyxResponseDto onyxResponseDto = returnInfoOnixHhpp(agendaDirecciones);
                                agendaDirecciones.setCmtOnyxResponseDto(onyxResponseDto);
                                try {
                                   agendamientoHhppWorkForceMglManager.cargarInformacionForEnvioNotificacion(agendaDirecciones, 4);
                                } catch (ApplicationException ex) {
                                    String msn = "Ocurrio un error al momento de "
                                            + "enviar notificacion de completado de agenda: " + ex.getMessage() + "";
                                    LOGGER.info(msn);
                                }
                                
                                if (agendaDirecciones.getFechaFinVt() != null) {
                                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_OK);
                                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_OK);
                                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_OK);
                                    response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_OK_HARDCLOSE);
                                } else {
                                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                                    response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);
                                }
                                if (agendaDirecciones.getOrdenTrabajo().getTipoOtHhppId() != null
                                        && agendaDirecciones.getOrdenTrabajo().getTipoOtHhppId().getRequiereOnyx()
                                        != null && agendaDirecciones.getOrdenTrabajo().getTipoOtHhppId().
                                                getRequiereOnyx().equalsIgnoreCase("Y")) {
                                    updateCloseOrdenOnix(null, agendaDirecciones,
                                            controlCierre, orderCompleteRequest.getRazon(),
                                            orderCompleteRequest.getUser().getLogin(), null);
                                }
                            } else {
                                //Es multiagenda
                                SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date fechaFinV = formatoDelTexto.parse(orderCompleteRequest.getFechaSalidaTecnico());
                                if (orderCompleteRequest.getIdMensaje() > 0) {
                                    agendaDirecciones.setIdMensajeHardclose(orderCompleteRequest.getIdMensaje());
                                }

                                agendaDirecciones.setFechaFinVt(fechaFinV);
                                CmtBasicaMgl basicaMgl = null;

                                CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
                                CmtTipoBasicaMgl cmtTipoBasicaMgl
                                        = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                                                Constant.TIPO_BASICA_RAZONES_NODONE);
                                if (cmtTipoBasicaMgl != null) {
                                    basicaMgl = cmtBasicaMglManager.findByTipoBasicaAndCodigo(cmtTipoBasicaMgl.getTipoBasicaId(), orderCompleteRequest.getRazon());
                                }

                                agendaDirecciones.setEstadoVisita(null);

                                if (basicaMgl != null) {
                                    agendaDirecciones.setBasicaIdrazones(basicaMgl);
                                }
                                agendaDirecciones.setIdentificacionTecnico(datosTecnico.getResourceId());
                                agendaDirecciones.setNombreTecnico(datosTecnico.getName());
                                agendaDirecciones.setIdentificacionAliado(datosTecnico.getXR_IdAliado());

                                CmtBasicaMgl estadoAgBasicaMgl = basicaMglManager.
                                        findByCodigoInternoApp(Constant.ESTADO_AGENDA_CERRADA);

                                if (agendaDirecciones.getBasicaIdEstadoAgenda().getBasicaId()
                                        .compareTo(estadoAgBasicaMgl.getBasicaId()) == 0) {
                                } else {
                                    agendaDirecciones.setBasicaIdEstadoAgenda(estadoAgBasicaMgl);

                                    if (datosTecnico.getXR_IdAliado() == null
                                            || datosTecnico.getXR_IdAliado().isEmpty()) {
                                        LOGGER.error("LA RESPUESTA NO TIENE INFORMACION DEL ALIADO");
                                    } else {
                                        String idAliadoFin = "";
                                        if (datosTecnico.getXR_IdAliado().contains("-")) {
                                            String parts[] = datosTecnico.getXR_IdAliado().split("-");
                                            if (parts != null && parts.length > 0) {
                                                idAliadoFin = parts[0];
                                            }
                                        } else {
                                            idAliadoFin = datosTecnico.getXR_IdAliado();
                                        }
                                        CmtAliados aliados = aliadosMglManager.findByIdAliado(new BigDecimal(idAliadoFin));
                                        if (aliados != null && aliados.getIdAliado() != null) {
                                            agendaDirecciones.setIdentificacionAliado(aliados.getIdAliado().toString());
                                            agendaDirecciones.setNombreAliado(aliados.getNombre());
                                        } else {
                                            LOGGER.error("NO HAY INFORMACION DEL ALIADO EN BD.");
                                        }
                                    }

                                }
                                CmtBasicaMgl basicaVisitaExitosa
                                        = cmtBasicaMglManager.findByCodigoInternoApp(
                                                Constant.VISITA_DOMICILIARIA_EXITOSA);

                                agendaDirecciones.setEstadoVisita(basicaVisitaExitosa);
                                agendaDirecciones.setObservacionesTecnico(orderCompleteRequest.getNotasFirma());

                                //Verificacion si es cierre ok manual = 1
                                if (orderCompleteRequest.getRazon() != null
                                        && !orderCompleteRequest.getRazon().trim().isEmpty()) {
                                    //Consulto la basica de cerrar agenda
                                    CmtBasicaMgl cierraAgeBasicaMgl = cmtBasicaMglManager.
                                            findByCodigoInternoApp(Constant.BASICA_RAZON_CERRAR);

                                    
                                    CmtTipoBasicaMgl cmtTipoBasicaMgl1
                                            = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                                                    Constant.TIPO_BASICA_RAZONES_NODONE);
                                    if (cmtTipoBasicaMgl1 != null) {
                                        basicaMgl = cmtBasicaMglManager.findByTipoBasicaAndCodigo(cmtTipoBasicaMgl1.getTipoBasicaId(), orderCompleteRequest.getRazon());
                                    }

                                    if (basicaMgl != null) {

                                        if (basicaMgl.getBasicaId().compareTo(cierraAgeBasicaMgl.getBasicaId()) == 0) {
                                            controlCierre = 1;
                                            OtHhppMgl ordenDeTrabajoDeLaAgenda = agendaDirecciones.getOrdenTrabajo();

                                            //Valida si el tipo de Ot Permite tecnologias/ solo generamos tecnologias en ultima agenda
                                            if (ordenDeTrabajoDeLaAgenda.getTipoOtHhppId().
                                                    getManejaTecnologias().compareTo(BigDecimal.ONE) == 0) {

                                                //consulta la Ot en OSFC para saber el estado de las tecnologias
                                                GetTechnologiesResponse estadoTecnologiasOfsc
                                                        = consultarTecnologiasOtOfsc(agendaDirecciones);

                                                //guarda en Mgl el estado de las tecnologias
                                                guardarEstadoTecnologiasOsfcEnMgl(orderCompleteRequest,
                                                        agendaDirecciones, estadoTecnologiasOfsc);

                                                //Valida si el tipo de Ot permite ampliacion de Tab
                                                if (ordenDeTrabajoDeLaAgenda.getTipoOtHhppId().
                                                        getAmpliacionTab().compareTo(BigDecimal.ONE) == 0) {

                                                    //Valida si alguna tecnologia requiere OT de ampliacion de tab
                                                    //Y de ser necesario la crea en Mgl
                                                    validarYCrearOtDeAmpliacionDeTab(agendaDirecciones,
                                                            orderCompleteRequest.getUser().getLogin(), 1);
                                                }

                                                //Consulta las tecnologias viables para creacion de Hhpp
                                                List<OtHhppTecnologiaMgl> tecnologiasViablesOt
                                                        = otHhppTecnologiaMglManager.findTecnologiasViables(
                                                                agendaDirecciones.getOrdenTrabajo().getOtHhppId());

                                                //Valida si existen tecnologias viables para creacion de Hhpp
                                                if (!tecnologiasViablesOt.isEmpty()) {
                                                    //Creacion de Hhpp
                                                    crearHhppConTecnologiasViables(tecnologiasViablesOt, orderCompleteRequest);
                                                }
                                            }

                                            //Cancelacion de las agendas que tenga pendiente por ser cierre OK manual
                                            List<BigDecimal> idsEstForClose = estadosAgendasForClose();
                                            TipoOtHhppMgl tipoOtHhppMgl = agendaDirecciones.getOrdenTrabajo().getTipoOtHhppId();
                                            String subTipoWorkfoce = tipoOtHhppMgl.getSubTipoOrdenOFSC().getCodigoBasica();

                                            List<MglAgendaDireccion> agendas = agendamientoHhppManager.agendasForCancelarHhpp(agendaDirecciones.getOrdenTrabajo(), idsEstForClose,
                                                    new BigDecimal(agendaDirecciones.getAgendaId()), subTipoWorkfoce, agendaDirecciones.getFechaAgenda());

                                            if (!agendas.isEmpty()) {
                                                for (MglAgendaDireccion agenda : agendas) {
                                                    //cancelo agenda
                                                    agendamientoHhppManager.cancelar(agenda, Constant.RAZON_CANCELAR, Constant.COMENTARIO_CANCELAR,
                                                            orderCompleteRequest.getUser().getLogin(), 1);
                                                    LOGGER.error("AGENDA  " + agenda.getOfpsOtId() + " CANCELADA");
                                                }
                                            } else {
                                                LOGGER.error("NO HAY AGENDAS PARA CANCELAR");
                                            }
                                            //Fin cancelacion de las agendas que tenga pendiente por ser cierre OK manual

                                            //Actualizacion de la orden en RR
                                            String numeroOtRr = agendaDirecciones.getIdOtenrr();
                                            BigDecimal codCuentaPar;

                                            if (agendaDirecciones.getCuentaMatrizMgl() != null) {
                                                //Hay una sola CM agrupadora
                                                codCuentaPar = agendaDirecciones.getCuentaMatrizMgl().getNumeroCuenta();

                                                ParametrosMgl parametroHabilitarOtInRr
                                                        = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
                                                if (parametroHabilitarOtInRr != null
                                                        && parametroHabilitarOtInRr.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)
                                                        && tipoOtHhppMgl.getEstadoOtRRInicial() != null
                                                        && tipoOtHhppMgl.getEstadoOtRRFinal() != null) {
                                                     String notaCierre="Cierre Ultima Agenda MER-"+agendaDirecciones.getOfpsOtId()+""
                                                        + "-"+orderCompleteRequest.getUser().getLogin()+""
                                                        + "-"+agendaDirecciones.getNodoMgl().getNodId()+"";
                                                    ordenTrabajoRrMglManager.actualizarEstadoResultadoOTRRHhpp
                                                 (codCuentaPar.toString(), numeroOtRr, orderCompleteRequest.getUser().getLogin(),
                                                         tipoOtHhppMgl, false, agendaDirecciones.getOrdenTrabajo(),notaCierre);
                                                }
                                            }
                                            //Fin actualizacion de la orden en RR
                                            agendaDirecciones.setUltimaAgenda("Y");
                                        } else {
                                            controlCierre = 2;
                                        }
                                    }
                                } else {
                                    controlCierre = 3;
                                }
                                
                                agendaDirecciones = agendamientoHhppManager.update(agendaDirecciones,
                                        orderCompleteRequest.getUser().getLogin(), 1);

                                AgendamientoHhppWorkForceMglManager agendamientoHhppWorkForceMglManager = new AgendamientoHhppWorkForceMglManager();
                                CmtOnyxResponseDto onyxResponseDto = returnInfoOnixHhpp(agendaDirecciones);
                                agendaDirecciones.setCmtOnyxResponseDto(onyxResponseDto);
                                try {
                                    agendamientoHhppWorkForceMglManager.cargarInformacionForEnvioNotificacion(agendaDirecciones, 4);
                                } catch (ApplicationException ex) {
                                    String msn = "Ocurrio un error al momento de "
                                            + "enviar notificacion de completado de agenda: " + ex.getMessage() + "";
                                    LOGGER.info(msn);
                                }

                                if (agendaDirecciones.getFechaFinVt() != null) {
                                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_OK);
                                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_OK);
                                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_OK);
                                    response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_OK_HARDCLOSE);
                                } else {
                                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                                    response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);
                                }
                                if (agendaDirecciones.getOrdenTrabajo().getTipoOtHhppId() != null
                                        && agendaDirecciones.getOrdenTrabajo().getTipoOtHhppId().getRequiereOnyx()
                                        != null && agendaDirecciones.getOrdenTrabajo().getTipoOtHhppId().
                                                getRequiereOnyx().equalsIgnoreCase("Y")) {
                                    updateCloseOrdenOnix(null, agendaDirecciones,
                                            controlCierre, orderCompleteRequest.getRazon(),
                                            orderCompleteRequest.getUser().getLogin(), null);
                                }
                            }
                        } else {
                            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ENCUENTRA);
                        }
                    }
                } catch (ApplicationException  | ParseException |/*DatatypeConfigurationException|*/ IOException e) {
                    LOGGER.error(ERROR_ACTUALIZA_AGENDA, e);
                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                    response.setDescription(e.getMessage());
                }

            } else {
                response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                response.setDescription("No es una orden de MGL");
            }
        }
        return response;
    }

    /**
     * Metodo para guardar en la base de datos Mgl si las tecnologias guardadas
     * en Osfc requieren ampliacion de tab o requieren ser creadas como Hhpp
     *
     * @Author Orlando Velasquez
     * @param orderCompleteRequest
     * @param agendaDirecciones agendamiento de direcciones
     * @param tecnologiasOsfc tecnologias obtenidas desde OSFC
     * @throws ApplicationException Excepcion lanzada al agendar
     */
    public void guardarEstadoTecnologiasOsfcEnMgl(
            OrderCompleteRequest orderCompleteRequest,
            MglAgendaDireccion agendaDirecciones,
            GetTechnologiesResponse tecnologiasOsfc) throws ApplicationException {

        List<OtHhppTecnologiaMgl> tecnologiasExistentesOt
                = otHhppTecnologiaMglManager.findOtHhppTecnologiaByOtHhppId(
                        agendaDirecciones.getOrdenTrabajo().getOtHhppId());

        for (OtHhppTecnologiaMgl tecnologiaExistenteOt : tecnologiasExistentesOt) {
            if (tecnologiaExistenteOt.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null) {

                if ((tecnologiasOsfc.getXA_NodoHfcUniSelect() != null
                        || tecnologiasOsfc.getXA_NodoHfcUniAmplia() != null)
                        && tecnologiaExistenteOt.getTecnoglogiaBasicaId().
                                getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_UNI)) {

                    if (tecnologiasOsfc.getXA_NodoHfcUniSelect() != null) {
                        guardarTecnologiaViable(tecnologiaExistenteOt, orderCompleteRequest);
                    }

                    if (tecnologiasOsfc.getXA_NodoHfcUniAmplia() != null
                            && tecnologiasOsfc.getXA_NodoHfcUniSelect() != null) {
                        guardarAmpliacionDeTab(tecnologiaExistenteOt, orderCompleteRequest);
                    }
                }

                if ((tecnologiasOsfc.getXA_NodoHfcBiSelect() != null
                        || tecnologiasOsfc.getXA_NodoHfcBiAmplia() != null)
                        && tecnologiaExistenteOt.getTecnoglogiaBasicaId().
                                getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_BID)) {

                    if (tecnologiasOsfc.getXA_NodoHfcBiSelect() != null) {
                        guardarTecnologiaViable(tecnologiaExistenteOt, orderCompleteRequest);
                    }

                    if (tecnologiasOsfc.getXA_NodoHfcBiAmplia() != null
                            && tecnologiasOsfc.getXA_NodoHfcBiSelect() != null) {
                        guardarAmpliacionDeTab(tecnologiaExistenteOt, orderCompleteRequest);
                    }

                }

                if ((tecnologiasOsfc.getXA_NodoDthSelect() != null
                        || tecnologiasOsfc.getXA_NodoDthAmplia() != null)
                        && tecnologiaExistenteOt.getTecnoglogiaBasicaId().
                                getIdentificadorInternoApp().equalsIgnoreCase(Constant.DTH)) {

                    if (tecnologiasOsfc.getXA_NodoDthSelect() != null) {
                        guardarTecnologiaViable(tecnologiaExistenteOt, orderCompleteRequest);
                    }

                    if (tecnologiasOsfc.getXA_NodoDthAmplia() != null
                            && tecnologiasOsfc.getXA_NodoDthSelect() != null) {
                        guardarAmpliacionDeTab(tecnologiaExistenteOt, orderCompleteRequest);
                    }

                }

                if ((tecnologiasOsfc.getXA_NodoFtthSelect() != null
                        || tecnologiasOsfc.getXA_NodoFtthAmplia() != null)
                        && tecnologiaExistenteOt.getTecnoglogiaBasicaId().
                                getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_FTTTH)) {

                    if (tecnologiasOsfc.getXA_NodoFtthSelect() != null) {
                        guardarTecnologiaViable(tecnologiaExistenteOt, orderCompleteRequest);
                    }

                    if (tecnologiasOsfc.getXA_NodoFtthAmplia() != null
                            && tecnologiasOsfc.getXA_NodoFtthSelect() != null) {
                        guardarAmpliacionDeTab(tecnologiaExistenteOt, orderCompleteRequest);
                    }

                }

                if ((tecnologiasOsfc.getXA_NodoFogSelect() != null
                        || tecnologiasOsfc.getXA_NodoFogAmplia() != null)
                        && tecnologiaExistenteOt.getTecnoglogiaBasicaId().
                                getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_OP_GPON)) {

                    if (tecnologiasOsfc.getXA_NodoFogSelect() != null) {
                        guardarTecnologiaViable(tecnologiaExistenteOt, orderCompleteRequest);
                    }

                    if (tecnologiasOsfc.getXA_NodoFogAmplia() != null
                            && tecnologiasOsfc.getXA_NodoFogSelect() != null) {
                        guardarAmpliacionDeTab(tecnologiaExistenteOt, orderCompleteRequest);
                    }

                }

                if ((tecnologiasOsfc.getXA_NodoBtsSelect() != null
                        || tecnologiasOsfc.getXA_NodoBtsAmplia() != null)
                        && tecnologiaExistenteOt.getTecnoglogiaBasicaId().
                                getIdentificadorInternoApp().equalsIgnoreCase(Constant.BTS_MOVIL)) {

                    if (tecnologiasOsfc.getXA_NodoBtsSelect() != null) {
                        guardarTecnologiaViable(tecnologiaExistenteOt, orderCompleteRequest);
                    }

                    if (tecnologiasOsfc.getXA_NodoBtsAmplia() != null
                            && tecnologiasOsfc.getXA_NodoBtsSelect() != null) {
                        guardarAmpliacionDeTab(tecnologiaExistenteOt, orderCompleteRequest);
                    }

                }

                if ((tecnologiasOsfc.getXA_NodoFouSelect() != null
                        || tecnologiasOsfc.getXA_NodoFouAmplia() != null)
                        && tecnologiaExistenteOt.getTecnoglogiaBasicaId().
                                getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_OP_UNI)) {

                    if (tecnologiasOsfc.getXA_NodoFouSelect() != null) {
                        guardarTecnologiaViable(tecnologiaExistenteOt, orderCompleteRequest);
                    }

                    if (tecnologiasOsfc.getXA_NodoFouAmplia() != null
                            && tecnologiasOsfc.getXA_NodoFouSelect() != null) {
                        guardarAmpliacionDeTab(tecnologiaExistenteOt, orderCompleteRequest);
                    }
                }
                
                
            }
        }
    }

    /**
     * Metodo para persistir la tecnologia viable enviada por el tecnico
     *
     * @Author Orlando Velasquez
     * @param tecnologiaExistenteOt tecnologia asociada a la OT
     * @param orderCompleteRequest
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void guardarTecnologiaViable(
            OtHhppTecnologiaMgl tecnologiaExistenteOt,
            OrderCompleteRequest orderCompleteRequest
    ) throws ApplicationException {
        tecnologiaExistenteOt.setTecnologiaViable("Y");
        otHhppTecnologiaMglManager.updateOtHhppTecnologiaMgl(
                tecnologiaExistenteOt, orderCompleteRequest.getUser().getLogin(), 1);
    }

    /**
     * Metodo para persistir si la agenda requiere ampliacion de tab
     *
     * @Author Orlando Velasquez
     * @param tecnologiaExistenteOt tecnologia asociada a la OT
     * @param orderCompleteRequest
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void guardarAmpliacionDeTab(
            OtHhppTecnologiaMgl tecnologiaExistenteOt,
            OrderCompleteRequest orderCompleteRequest
    ) throws ApplicationException {
        tecnologiaExistenteOt.setAmpliacionTab("Y");
        otHhppTecnologiaMglManager.updateOtHhppTecnologiaMgl(
                tecnologiaExistenteOt, orderCompleteRequest.getUser().getLogin(), 1);

    }

    /**
     * Metodo para persistir si la agenda requiere ampliacion de tab
     *
     * @Author Orlando Velasquez
     * @param agendaDirecciones agenda a la cual pertenecen las tecnologias
     * @param usuario
     * @param perfil
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void validarYCrearOtDeAmpliacionDeTab(
            MglAgendaDireccion agendaDirecciones,
            String usuario, int perfil
    ) throws ApplicationException {

        //consulta las tecnologias existentes para esta Ot en Mgl
        List<OtHhppTecnologiaMgl> tecnologiasExistentesDeLaOtEnMgl
                = otHhppTecnologiaMglManager.findOtHhppTecnologiaByOtHhppId(
                        agendaDirecciones.getOrdenTrabajo().getOtHhppId());

        for (OtHhppTecnologiaMgl tecnologiaAmpliacionTab : tecnologiasExistentesDeLaOtEnMgl) {
            //Valida si la tecnologia requiere ampliacion Ot de Tab
            if (tecnologiaAmpliacionTab.getAmpliacionTab().equalsIgnoreCase("Y")) {
                OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
                OtHhppMgl otHhppMgl = new OtHhppMgl();
                OtHhppMgl otHhppMglCreada;

                //Buscar Tipo Ot de ampliacion de tab dependiendo la seleccion almacenada 
                //en la base de datos
                BigDecimal tipoOtAmpliacionTab = agendaDirecciones.getOrdenTrabajo().
                        getTipoOtHhppId().getTipoOtAmpliacionTab();
                TipoOtHhppMgl tipoAmpliacionTab = tipoOtHhppMglManager.findTipoOtHhppById(tipoOtAmpliacionTab);

                otHhppMgl.setTipoOtHhppId(tipoAmpliacionTab);
                otHhppMgl.setEstadoGeneral(agendaDirecciones.getOrdenTrabajo().getEstadoGeneral());
                otHhppMgl.setNombreContacto(agendaDirecciones.getOrdenTrabajo().getNombreContacto());
                otHhppMgl.setTelefonoContacto(agendaDirecciones.getOrdenTrabajo().getTelefonoContacto());
                otHhppMgl.setCorreoContacto(agendaDirecciones.getOrdenTrabajo().getCorreoContacto());
                otHhppMgl.setDireccionId(agendaDirecciones.getOrdenTrabajo().getDireccionId());
                otHhppMgl.setSubDireccionId(agendaDirecciones.getOrdenTrabajo().getSubDireccionId());
                otHhppMgl.setSolicitudId(agendaDirecciones.getOrdenTrabajo().getSolicitudId());
                otHhppMgl.setBasicaIdTipoTrabajo(agendaDirecciones.getOrdenTrabajo().getBasicaIdTipoTrabajo());
                otHhppMgl.setFechaCreacionOt(new Date());

                otHhppMglCreada = otHhppMglManager.createOtDirecciones(
                        otHhppMgl, usuario, perfil);

                OtHhppTecnologiaMgl nuevaTecnologia = new OtHhppTecnologiaMgl();
                if (otHhppMglCreada.getOtHhppId() != null) {
                    nuevaTecnologia.setOtHhppId(otHhppMglCreada);
                    nuevaTecnologia.setTecnoglogiaBasicaId(tecnologiaAmpliacionTab.getTecnoglogiaBasicaId());
                    nuevaTecnologia.setNodo(tecnologiaAmpliacionTab.getNodo());
                    nuevaTecnologia.setSincronizaRr(tecnologiaAmpliacionTab.getSincronizaRr());
                    nuevaTecnologia.setAmpliacionTab("N");
                    otHhppTecnologiaMglManager.createOtHhppTecnologiaMgl(
                            nuevaTecnologia, usuario, perfil);
                }
            }
        }
    }

    /**
     * Metodo para persistir los nuevos Hhpp de cada tecnologia en RR y MGL
     *
     * @Author Orlando Velasquez
     * @param tecnologiasViables lista de tecnologias viables
     * @param orderCompleteRequestAddresses
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void crearHhppConTecnologiasViables(List<OtHhppTecnologiaMgl> tecnologiasViables,
            OrderCompleteRequest orderCompleteRequestAddresses
    ) throws ApplicationException, ApplicationException {

        SolicitudManager solicitudManager = new SolicitudManager();
        DireccionRRManager direccionRRManager;
        ParametrosMglManager parametrosMglManager1 = new ParametrosMglManager();
        boolean habilitarRR = false;
        ParametrosMgl parametroHabilitarRR = parametrosMglManager1.findParametroMgl(Constant.HABILITAR_RR);
        if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
            habilitarRR = true;
        }

        for (OtHhppTecnologiaMgl tecnologiaViable : tecnologiasViables) {

            //Solicitud a procesar
            Solicitud solicitudDth = tecnologiaViable.getOtHhppId().getSolicitudId();
            boolean sincronizarConRr = sincronizarConRr(tecnologiaViable.getSincronizaRr());

            if (solicitudDth == null) {

                //Nodo de la tecnologia
                String nodo = tecnologiaViable.getNodo().getNodCodigo();

                //Obtiene el tipo de Solicitud (carpeta RR)
                String tipoSolicitud = solicitudManager.obtenerCarpetaTipoDireccion(tecnologiaViable.getTecnoglogiaBasicaId());
                DireccionMgl direccion = tecnologiaViable.getOtHhppId().getDireccionId();
                BigDecimal subDireccion = null;

                if (tecnologiaViable.getOtHhppId().getSubDireccionId() != null) {
                    subDireccion = tecnologiaViable.getOtHhppId().getSubDireccionId().getSdiId();
                }
                List<CmtDireccionDetalladaMgl> direccionDetalladas
                        = direccionDetalleManager.findDireccionDetallaByDirIdSdirId(direccion.getDirId(), subDireccion);
                DrDireccion drDireccion
                        = direccionDetalleManager.parseCmtDireccionDetalladaMglToDrDireccion(direccionDetalladas.get(0));

                drDireccion.setEstrato(tecnologiaViable.getOtHhppId().getDireccionId().getDirEstrato().toString());
                //obtiene el estrato de la direccion
                String estrato = solicitudManager.validarEstrato(drDireccion);

                GeograficoPoliticoMgl centroPoblado = direccion.getUbicacion().getGpoIdObj();

                //obtener instancia inyectandole parametros en el constructor
                direccionRRManager = obtenerInstanciaDireccionRRManager(centroPoblado.getGeoGpoId(), drDireccion);

                String camposVivienda = direccionDetalladas.get(0).getCpTipoNivel1() != null ? direccionDetalladas.get(0).getCpTipoNivel1() : "CASA";
                camposVivienda = camposVivienda.concat(direccionDetalladas.get(0).getCpTipoNivel2() != null ? direccionDetalladas.get(0).getCpTipoNivel2() : " ");
                String tipoSolOt = "OTRO";
                if (tecnologiaViable.getTecnoglogiaBasicaId().getIdentificadorInternoApp().
                        equals(Constant.RED_FO)
                        || tecnologiaViable.getTecnoglogiaBasicaId().getIdentificadorInternoApp().
                                equals(Constant.FIBRA_OP_GPON)
                        || tecnologiaViable.getTecnoglogiaBasicaId().getIdentificadorInternoApp().
                                equals(Constant.FIBRA_OP_UNI)) {
                    tipoSolOt = Constantes.TIPOSOL_PYMES;
                }

                String tipoUnidad = direccionRRManager.obtenerTipoEdificio(camposVivienda, "OTRA", tipoSolOt);
                direccionRRManager.registrarHHPP_Inspira_Independiente(
                        nodo, nodo,
                        orderCompleteRequestAddresses.getUser().getLogin(), tipoSolicitud,
                        Constant.FUNCIONALIDAD_DIRECCIONES,
                        estrato, false,
                        null,
                        Constant.RESULTADO_HHPP_CREADO,
                        orderCompleteRequestAddresses.getUser().getLogin(),
                        centroPoblado.getGeoGpoId(),
                        sincronizarConRr,
                        tipoUnidad, null);
            } else {

                //Nodo de la tecnologia
                String nodo = tecnologiaViable.getNodo().getNodCodigo();

                //Obtiene el tipo de Solicitud (carpeta RR)
                String tipoSolicitud = solicitudManager.obtenerCarpetaTipoDireccion(solicitudDth.getTecnologiaId());

                CmtDireccionDetalladaMgl direccionDetallada
                        = direccionDetalleManager
                                .buscarDireccionIdDireccion(solicitudDth.getDireccionDetallada()
                                        .getDireccionDetalladaId());

                DrDireccion drDireccion
                        = direccionDetalleManager.parseCmtDireccionDetalladaMglToDrDireccion(direccionDetallada);

                drDireccion.setEstrato(solicitudDth.getEstratoAntiguo());
                //obtiene el estrato de la direccion
                String estrato = solicitudManager.validarEstrato(drDireccion);

                String dirAntiguaFormatoIgac = obtenerDireccionAntiguaFormatoIgac(solicitudDth);

                //obtener instancia inyectandole parametros en el constructor
                direccionRRManager = obtenerInstanciaDireccionRRManager(solicitudDth.getCentroPobladoId(), drDireccion);

                direccionRRManager.registrarHHPP_Inspira(
                        tecnologiaViable.getNodo(),
                        orderCompleteRequestAddresses.getUser().getLogin(), tipoSolicitud,
                        Constant.FUNCIONALIDAD_DIRECCIONES,
                        estrato, false,
                        solicitudDth.getIdSolicitud().toString(),
                        solicitudDth.getTipoSol(),
                        solicitudDth.getCiudad(),
                        solicitudDth.getRespuesta(),
                        solicitudDth.getSolicitante(),
                        solicitudDth.getRptGestion(),
                        orderCompleteRequestAddresses.getUser().getLogin(),
                        solicitudDth.getContacto(),
                        solicitudDth.getTelContacto(),
                        dirAntiguaFormatoIgac, solicitudDth.getCentroPobladoId(),
                        sincronizarConRr, "", null, habilitarRR,null,false);
            }
        }

    }

    /**
     * Función usada para obtener el valor si debe o no sincronizar en Rr
     *
     * @author Orlando Velasquez Diaz
     * @param sincronizar
     *
     * @return
     */
    public boolean sincronizarConRr(String sincronizar) {
        return sincronizar.equalsIgnoreCase("1");
    }

    /**
     * Función que obtiene la instancia de DireccionRRManager
     *
     * @author Orlando Velasquez Diaz
     * @param centroPobladoId
     * @param drDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public DireccionRRManager obtenerInstanciaDireccionRRManager(BigDecimal centroPobladoId, DrDireccion drDireccion)
            throws ApplicationException, ApplicationException {

        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        DetalleDireccionEntity detalleDireccionEntity;
        detalleDireccionEntity = drDireccion.convertToDetalleDireccionEntity();
        //obtenemos el centro poblado de la solicitud para conocer si la ciudad es multiorigen
        GeograficoPoliticoMgl centroPobladoSolicitud;
        centroPobladoSolicitud = geograficoPoliticoManager.findById(centroPobladoId);

        //Conocer si es multi-origen         
        detalleDireccionEntity.setMultiOrigen(centroPobladoSolicitud.getGpoMultiorigen());
        //obtener la direccion en formato RR

        DireccionRRManager direccionRRManager
                = new DireccionRRManager(detalleDireccionEntity);

        return direccionRRManager;
    }

    /**
     * Función que valida si la solicitud tiene una dirección antigua para
     * obtener las unidades de la antigua dirección
     *
     * @author Juan David Hernandez
     * @param solicitud
     * @return
     */
    public String obtenerDireccionAntiguaFormatoIgac(Solicitud solicitud) {
        String dirAntiguaFormatoIgac = "";
        if (solicitud.getDireccionAntiguaIgac() != null
                && !solicitud.getDireccionAntiguaIgac().isEmpty()) {

            String[] direccionAntigua = solicitud
                    .getDireccionAntiguaIgac().trim().split("&");
            String calleAntigua = direccionAntigua[0];
            String casaAntigua = direccionAntigua[1];

            dirAntiguaFormatoIgac = calleAntigua
                    + " " + casaAntigua;
        }

        return dirAntiguaFormatoIgac;
    }

    /**
     * Metodo para construir el Appt_number para enviar a WorkForce en el caso
     * de CM debe empezar con 1 para identificar que es una OT de CM
     *
     * @param orden orden de trabajo de un Hhpp
     * @return boolean disponibilidad de las tecnologias
     */
    public String armarNumeroOtOfsc(CmtOrdenTrabajoMgl orden) {

        String numeroArmado = "";
        String secuencia = "";
        String numOrden = "";
        int maximo = 0;
        List<CmtAgendamientoMgl> agendas;
        int tamanio;
        try {

            agendas = manager.agendasPorOT(orden);
            tamanio = agendas.size();

            if (tamanio == 0) {
                maximo++;
            } else {
                maximo = manager.selectMaximoSecXOt(orden.getIdOt());
                maximo++;
            }

            if (maximo > 0) {
                String numFi = String.valueOf(maximo);
                if (numFi.length() == 2) {
                    secuencia = "0" + numFi;
                } else {
                    secuencia = "00" + numFi;
                }

            String numOt = orden.getIdOt().toString();
                switch (numOt.length()) {
                    case 12:
                        numOrden = numOt;
                        break;
                    case 11:
                        numOrden = "1" + numOt;
                        break;
                    case 10:
                        numOrden = "10" + numOt;
                        break;
                    case 9:
                        numOrden = "100" + numOt;
                        break;
                    case 8:
                        numOrden = "1000" + numOt;
                        break;
                    case 7:
                        numOrden = "10000" + numOt;
                        break;
                    case 6:
                        numOrden = "100000" + numOt;
                        break;
                    case 5:
                        numOrden = "1000000" + numOt;
                        break;
                    case 4:
                        numOrden = "10000000" + numOt;
                        break;
                    case 3:
                        numOrden = "100000000" + numOt;
                        break;
                    case 2:
                        numOrden = "1000000000" + numOt;
                        break;
                    case 1:
                        numOrden = "10000000000" + numOt;
                        break;
                    default:
                        break;
                }
            }

            numeroArmado = numOrden + secuencia;

        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
        }

        return numeroArmado;

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
     * metodo para informar de la suspencion de una visita a MGL Autor: victor
     * bocanegra
     *
     * @param suspenderVisitaRequest
     * @return ServicesAgendamientosResponse
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public ServicesAgendamientosResponse suspenderVisitaMgl(SuspenderVisitaRequest suspenderVisitaRequest)
            throws ApplicationException {

        ServicesAgendamientosResponse response = new ServicesAgendamientosResponse();
        GetResourcesResponses datosTecnico;
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();

        if (suspenderVisitaRequest.getIdTecnico() == null
                || suspenderVisitaRequest.getIdTecnico().isEmpty()) {
            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALTA);
            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALTA);
        } else if (suspenderVisitaRequest.getFechaSuspende() == null
                || suspenderVisitaRequest.getFechaSuspende().isEmpty()) {
            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALTA);
            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALTA);
        } else if (suspenderVisitaRequest.getIdOt() == null
                || suspenderVisitaRequest.getIdOt().isEmpty()) {
            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALTA);
            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALTA);
        } else if (suspenderVisitaRequest.getRazon() == null
                || suspenderVisitaRequest.getRazon().isEmpty()) {
            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALTA);
            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALTA);
        } else {

            String controlCcmmDir = suspenderVisitaRequest.getIdOt().substring(0, 1);

            if (controlCcmmDir.equalsIgnoreCase(ConstansClient.CONTROL_NUM_CCMM)) {
                //ES UN NUMERO CREADO DESDE CCMM
                try {

                    URL url = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.RESOURCES + suspenderVisitaRequest.getIdTecnico());

                    userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                            .iterator().next().getParValor();
                    String comAutorization = "Basic" + " " + userPwdAutorization;

                    ConsumoGenerico consumoGenerico = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url,
                            comAutorization, null, null, null);

                    datosTecnico = consumoGenerico.consumirServicioResources();
                    if (datosTecnico.getResourceId() == null
                            || datosTecnico.getResourceId().isEmpty()) {

                        response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                        response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                        response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                        response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);

                    } else {
                        CmtAgendamientoMgl cmtAgendamientoMgl;
                        cmtAgendamientoMgl = manager.buscarAgendaPorOtIdMgl(suspenderVisitaRequest.getIdOt());

                        if (cmtAgendamientoMgl.getId() != null) {

                            CmtBasicaMgl basicaMgl= null;

                            CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
                            CmtTipoBasicaMgl cmtTipoBasicaMgl
                                    = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                                            Constant.TIPO_BASICA_RAZONES_NODONE);
                            if (cmtTipoBasicaMgl != null) {
                                basicaMgl = cmtBasicaMglManager.findByTipoBasicaAndCodigo(cmtTipoBasicaMgl.getTipoBasicaId(), suspenderVisitaRequest.getRazon());
                            }

                            if (basicaMgl != null) {
                                cmtAgendamientoMgl.setBasicaIdrazones(basicaMgl);
                                SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date fechaSusV = formatoDelTexto.parse(suspenderVisitaRequest.getFechaSuspende());
                                if (suspenderVisitaRequest.getIdMensaje() > 0) {
                                    cmtAgendamientoMgl.setIdMensajeSuspende(suspenderVisitaRequest.getIdMensaje());
                                }
                                cmtAgendamientoMgl.setFechaSuspendeVt(fechaSusV);
                                cmtAgendamientoMgl.setIdentificacionTecnico(datosTecnico.getResourceId());
                                cmtAgendamientoMgl.setNombreTecnico(datosTecnico.getName());
                                cmtAgendamientoMgl.setIdentificacionAliado(datosTecnico.getXR_IdAliado());

                                CmtBasicaMgl estadoAgBasicaMgl = basicaMglManager.
                                        findByCodigoInternoApp(Constant.ESTADO_AGENDA_SUSPENDIDA);

                                cmtAgendamientoMgl.setBasicaIdEstadoAgenda(estadoAgBasicaMgl);

                                cmtAgendamientoMgl = manager.update(cmtAgendamientoMgl,
                                        suspenderVisitaRequest.getUser().getLogin(), 1);

                                if (cmtAgendamientoMgl.getFechaSuspendeVt() != null) {
                                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_OK);
                                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_OK);
                                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_OK);
                                    response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_OK_SV);
                                } else {
                                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                                    response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);
                                }
                                if (cmtAgendamientoMgl.getOrdenTrabajo().getTipoOtObj() != null
                                        && cmtAgendamientoMgl.getOrdenTrabajo().getTipoOtObj().getRequiereOnyx()
                                        != null && cmtAgendamientoMgl.getOrdenTrabajo().getTipoOtObj().
                                                getRequiereOnyx().equalsIgnoreCase("Y")) {

                                    updateCloseOrdenOnix(cmtAgendamientoMgl, null,
                                            2, suspenderVisitaRequest.getRazon(),
                                            suspenderVisitaRequest.getUser().getLogin(),
                                            null);
                                }
                            } else {
                                response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                                response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                                response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                                response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_NO_HAY_RAZON);
                            }
                        } else {
                            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ENCUENTRA);
                        }
                    }
                } catch (ApplicationException | MalformedURLException | ParseException e) {
                    LOGGER.error(ERROR_ACTUALIZA_AGENDA, e);
                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                    response.setDescription(e.getMessage());
                }
            } else if (controlCcmmDir.equalsIgnoreCase(ConstansClient.CONTROL_NUM_DIR)) {
                try {

                    URL url = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.RESOURCES + suspenderVisitaRequest.getIdTecnico());

                    userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                            .iterator().next().getParValor();
                    String comAutorization = "Basic" + " " + userPwdAutorization;

                    ConsumoGenerico consumoGenerico = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url,
                            comAutorization, null, null, null);

                    datosTecnico = consumoGenerico.consumirServicioResources();
                    if (datosTecnico.getResourceId() == null
                            || datosTecnico.getResourceId().isEmpty()) {

                        response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                        response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                        response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                        response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);

                    } else {
                        MglAgendaDireccion agendaDirecciones;
                        agendaDirecciones = agendamientoHhppManager.buscarAgendaPorOtIdMgl(suspenderVisitaRequest.getIdOt());

                        if (agendaDirecciones.getAgendaId() != null) {

                            CmtBasicaMgl basicaMgl = null;
                            
                            CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
                            CmtTipoBasicaMgl cmtTipoBasicaMgl
                                    = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                                            Constant.TIPO_BASICA_RAZONES_NODONE);
                            if (cmtTipoBasicaMgl != null) {
                                basicaMgl = cmtBasicaMglManager.findByTipoBasicaAndCodigo(cmtTipoBasicaMgl.getTipoBasicaId(), suspenderVisitaRequest.getRazon());
                            }

                            if (basicaMgl != null) {
                                agendaDirecciones.setBasicaIdrazones(basicaMgl);
                                SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date fechaSusV = formatoDelTexto.parse(suspenderVisitaRequest.getFechaSuspende());
                                if (suspenderVisitaRequest.getIdMensaje() > 0) {
                                    agendaDirecciones.setIdMensajeSuspende(suspenderVisitaRequest.getIdMensaje());
                                }
                                agendaDirecciones.setFechaSuspendeVt(fechaSusV);
                                agendaDirecciones.setIdentificacionTecnico(datosTecnico.getResourceId());
                                agendaDirecciones.setNombreTecnico(datosTecnico.getName());
                                agendaDirecciones.setIdentificacionAliado(datosTecnico.getXR_IdAliado());

                                CmtBasicaMgl estadoAgBasicaMgl = basicaMglManager.
                                        findByCodigoInternoApp(Constant.ESTADO_AGENDA_SUSPENDIDA);

                                agendaDirecciones.setBasicaIdEstadoAgenda(estadoAgBasicaMgl);

                                agendaDirecciones = agendamientoHhppManager.update(agendaDirecciones,
                                        suspenderVisitaRequest.getUser().getLogin(), 1);

                                if (agendaDirecciones.getFechaSuspendeVt() != null) {
                                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_OK);
                                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_OK);
                                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_OK);
                                    response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_OK_SV);
                                } else {
                                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                                    response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA);
                                }
                                if (agendaDirecciones.getOrdenTrabajo().getTipoOtHhppId() != null
                                        && agendaDirecciones.getOrdenTrabajo().getTipoOtHhppId().getRequiereOnyx()
                                        != null && agendaDirecciones.getOrdenTrabajo().getTipoOtHhppId().
                                                getRequiereOnyx().equalsIgnoreCase("Y")) {
                                    updateCloseOrdenOnix(null, agendaDirecciones,
                                            2, suspenderVisitaRequest.getRazon(),
                                            suspenderVisitaRequest.getUser().getLogin(),
                                            null);
                                }
                            } else {
                                response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                                response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                                response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                                response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_NO_HAY_RAZON);
                            }
                        } else {
                            response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                            response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                            response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                            response.setDescription(CmtAgendamientoMglConstantes.DESCRIPCION_MENSAJE_FALLA_NO_ENCUENTRA);
                        }
                    }
                } catch (ApplicationException | MalformedURLException | ParseException e) {
                    LOGGER.error(ERROR_ACTUALIZA_AGENDA, e);
                    response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                    response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                    response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                    response.setDescription(e.getMessage());
                }
            } else {
                response.setMessageId(CmtAgendamientoMglConstantes.ID_MENSAJE_FALLA);
                response.setCodigo(CmtAgendamientoMglConstantes.CODIGO_FALLA);
                response.setStatus(CmtAgendamientoMglConstantes.STATUS_FALTA);
                response.setDescription("No es una orden de MGL");
            }
        }

        return response;
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
    public List<String> consultarDocumentos(CmtAgendamientoMgl agenda, String usuario, int perfil)
            throws ApplicationException {

        List<String> links;
        List<String> archivos = new ArrayList<>();
        String documentosGuardar = "";
        CmtArchivosVtMglManager archivosVtMglManager = new CmtArchivosVtMglManager();
        try {

            URL url = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.ACTIVITIES + agenda.getWorkForceId());

            userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                    .iterator().next().getParValor();
            String comAutorization = "Basic" + " " + userPwdAutorization;

            ConsumoGenerico consumoGenerico = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url,
                    comAutorization, null, null, null);

            links = consumoGenerico.consumirServicioActivities();

            if (!links.isEmpty()) {
                for (String propiedad : links) {

                    URL url2 = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.ACTIVITIES
                            + agenda.getWorkForceId() + "/" + propiedad);

                    ConsumoGenerico consumoGenerico1 = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url2,
                            comAutorization, null, null, null);

                    //Consumo para bajar de eta
                    JSONObject jsonObj = consumoGenerico1.consumirServicioHttp();

                    String nameArchivo = jsonObj.getString(ConstansClient.ATR_NAME);

                    JSONArray array = jsonObj.getJSONArray(ConstansClient.ATR_LINKS);

                    String urlArchivo = null;
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

                    byte[] bytes = IOUtils.toByteArray(in);

                    boolean response = cargarArchivoAgendaOtxUCM(agenda, bytes, usuario, perfil, nameArchivo);

                    if (response) {
                        LOGGER.info("Archivo cargado en el repositorio");
                    } else {
                        LOGGER.error("Ocurrio un error al momento de guardar el archivo");
                    }
                    in.close();
                }

                List<CmtArchivosVtMgl> archivosOtAgenda = archivosVtMglManager.findByIdOtAndAge(agenda);
                if (!archivosOtAgenda.isEmpty()) {
                    for (CmtArchivosVtMgl archivosVtMgl : archivosOtAgenda) {
                        documentosGuardar = documentosGuardar + archivosVtMgl.getRutaArchivo() + "|";
                        archivos.add(archivosVtMgl.getRutaArchivo());
                    }

                }
                if (!documentosGuardar.isEmpty()) {
                    agenda.setDocAdjuntos(documentosGuardar);
                    manager.update(agenda, usuario, perfil);
                }
            }

        } catch (SearchOrdenesServicioFault | UploadOrdenesServicioFault | 
                ApplicationException | IOException | 
                IllegalArgumentException | JSONException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, ex);
        }
        return archivos;
    }

    /**
     * Metodo para consultar en Osfc el estado de las tecnologias
     *
     * @Author Orlando Velasquez
     * @param agenda agenda a la cual pertenecen las tecnologias
     * @return
     *
     */
    public GetTechnologiesResponse consultarTecnologiasOtOfsc(MglAgendaDireccion agenda) {
        GetTechnologiesResponse tecnologias = new GetTechnologiesResponse();
        try {

            userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                    .iterator().next().getParValor();
            String comAutorization = "Basic" + " " + userPwdAutorization;

            URL url = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.ACTIVITIES + agenda.getOfpsId());
            ConsumoGenerico consumoGenerico = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url,
                    comAutorization, null, null, null);

            tecnologias = consumoGenerico.consumirServicioTechnologies();

            return tecnologias;
        } catch (ApplicationException | IOException ex) {
            LOGGER.error("Error al momento de consultar la tecnología. EX000: " + ex.getMessage(), ex);
        }
        return tecnologias;
    }

    /**
     * Metodo para cargar un documento de VT al UCM Autor: Victor Bocanegra
     *
     * @param agenda
     * @param bytes
     * @param usuario que realiza la operacion
     * @param nombreArchivo
     * @param perfil del usuario que realiza la operacion
     * @return boolean
     * @throws java.net.MalformedURLException
     * @throws ApplicationException Excepcion lanzada por la carga del archivo
     * @throws java.io.FileNotFoundException
     */
    public boolean cargarArchivoAgendaOtxUCM(CmtAgendamientoMgl agenda,
            byte[] bytes, String usuario, int perfil, String nombreArchivo)
            throws MalformedURLException, FileNotFoundException, ApplicationException,
            UploadOrdenesServicioFault, SearchOrdenesServicioFault {

        boolean respuesta = false;
        Integer maximo;
        CmtArchivosVtMglManager archivosVtMglManager = new CmtArchivosVtMglManager();
        CmtOrdenTrabajoMgl ot;

        if (agenda != null) {
            ot = agenda.getOrdenTrabajo();
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
          
            ParametrosMgl param = parametros.findByAcronimoName(Constant.PROPERTY_URL_WS_UCM_UPLOAD_ORDENES);
            String ruta = "";
            if (param != null) {
                ruta = param.getParValor();
            }
            
            String tipoDocumental = "";
            CmtBasicaMgl basicaTipoDoc = basicaMglManager.
                    findByCodigoInternoApp(Constant.TIPO_DOCUMENTAL_VISITA_TECNICA);
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
            field3.setName("xdOrdenServicio");
            field3.setValue(ot.getIdOt().toString());
            documentType.getField().add(field3);
            documentTypeBuscar.getField().add(field3);
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            FieldType field4 = new FieldType();
            field4.setName("xdFechaDocumento");
            field4.setValue(dateFormat.format(date));
            documentType.getField().add(field4);
            documentTypeBuscar.getField().add(field4);
            
            maximo = archivosVtMglManager.selectMaximoSecXOt(agenda.getOrdenTrabajo(),
                    Constant.ORIGEN_CARGA_ARCHIVO_DOCUMENTOS_AGENDAMIENTOS);
            
            if (maximo == 0) {
                maximo++;
            } else {
                maximo++;
            }
            
            String numUnico = agenda.getOrdenTrabajo().getIdOt().toString() + String.valueOf(maximo) + Constant.ORIGEN_CARGA_ARCHIVO_DOCUMENTOS_AGENDAMIENTOS;
           
            FieldType field5 = new FieldType();
            field5.setName("xdIdProceso");
            field5.setValue(numUnico);
            documentType.getField().add(field5);
            documentTypeBuscar.getField().add(field5);
            
            FieldType field6 = new FieldType();
            field6.setName("xdNumeroCtaMatriz");
            field6.setValue(ot.getCmObj().getCuentaMatrizId().toString());
            documentType.getField().add(field6);
            documentTypeBuscar.getField().add(field6);
            
            fielFileType.setName(nombreArchivo);
            fielFileType.setContent(bytes);
            
            request.setDocument(documentType);
            request.setFile(fielFileType);
            ResponseType response = cliente.insertOrdenes(request, url);
            ActionStatusEnumType status = response.getActionStatus();
            String urlArchivo = null;
            if (status.value().equalsIgnoreCase("success")) {
                
                //Consultamos el archivo subido al gestor
                ParametrosMgl paramUrl = parametros.findByAcronimoName(Constant.PROPERTY_URL_WS_UCM_SEARCH_ORDENES);
                String rutaBuscar = "";
                if (paramUrl != null) {
                    rutaBuscar = paramUrl.getParValor();
                }
                
                URL urlBuscar = new URL(null, rutaBuscar, new sun.net.www.protocol.http.Handler());
                RequestType requestBuscar = new RequestType();
                requestBuscar.setDocument(documentTypeBuscar);
                
                ResponseType responseBuscar = cliente.findOrdenes(requestBuscar, urlBuscar);
                ActionStatusEnumType statusBuscar = responseBuscar.getActionStatus();
                
                if (statusBuscar.value().equalsIgnoreCase("success")) {
                    
                    for (DocumentType documentType1 : responseBuscar.getDocument()) {
                        if (!documentType1.getField().isEmpty()) {
                            
                            for (FieldType atributos : documentType1.getField()) {
                                if (atributos.getName().equalsIgnoreCase("xdURLDocumento")) {
                                    urlArchivo = atributos.getValue();
                                }
                            }
                            
                        }
                        
                    }
                }
                
                if (urlArchivo != null && !urlArchivo.isEmpty()) {
                    CmtArchivosVtMgl cmtArchivosVtMgl;
                    cmtArchivosVtMgl = archivosVtMglManager.findByNombreArchivoAndOrigenAndOt(nombreArchivo, Constant.ORIGEN_CARGA_ARCHIVO_DOCUMENTOS_AGENDAMIENTOS, agenda.getOrdenTrabajo());
                    
                    if (cmtArchivosVtMgl != null) {
                        LOGGER.info("No se guarda el archivo ya existe uno con esas caracteristicas.");
                    } else {
                        cmtArchivosVtMgl = new CmtArchivosVtMgl();
                        cmtArchivosVtMgl.setOrdenTrabajoMglobj(agenda.getOrdenTrabajo());
                        cmtArchivosVtMgl.setRutaArchivo(urlArchivo);
                        cmtArchivosVtMgl.setNombreArchivo(nombreArchivo);
                        cmtArchivosVtMgl.setSecArchivo(maximo);
                        cmtArchivosVtMgl.setArchivoAge(agenda.getWorkForceId());
                        cmtArchivosVtMgl.setOrigenArchivos(Constant.ORIGEN_CARGA_ARCHIVO_DOCUMENTOS_AGENDAMIENTOS);
                        cmtArchivosVtMgl = archivosVtMglManager.create(cmtArchivosVtMgl, usuario, perfil);
                        
                        respuesta = cmtArchivosVtMgl.getIdArchivosVt() != null;
                    }
                } else {
                    LOGGER.info("Empiezan los 10 segundos");
                    esperar(10);
                    
                    responseBuscar = cliente.findOrdenes(requestBuscar, urlBuscar);
                    ActionStatusEnumType statusBuscar2 = responseBuscar.getActionStatus();
                    
                    if (statusBuscar2.value().equalsIgnoreCase("success")) {
                        
                        for (DocumentType documentType1 : responseBuscar.getDocument()) {
                            if (!documentType1.getField().isEmpty()) {

                                for (FieldType atributos : documentType1.getField()) {
                                    if (atributos.getName().equalsIgnoreCase("xdURLDocumento")) {
                                        urlArchivo = atributos.getValue();
                                    }
                                }

                            }

                        }
                    }
                    if (urlArchivo != null && !urlArchivo.isEmpty()) {
                        CmtArchivosVtMgl cmtArchivosVtMgl;
                        cmtArchivosVtMgl = archivosVtMglManager.
                                findByNombreArchivoAndOrigenAndOt(nombreArchivo, Constant.ORIGEN_CARGA_ARCHIVO_DOCUMENTOS_AGENDAMIENTOS, agenda.getOrdenTrabajo());
                        if (cmtArchivosVtMgl != null) {
                            LOGGER.info("No se guarda el archivo ya existe uno con esas caracteristicas.");
                        } else {
                            cmtArchivosVtMgl = new CmtArchivosVtMgl();
                            cmtArchivosVtMgl.setOrdenTrabajoMglobj(agenda.getOrdenTrabajo());
                            cmtArchivosVtMgl.setRutaArchivo(urlArchivo);
                            cmtArchivosVtMgl.setNombreArchivo(nombreArchivo);
                            cmtArchivosVtMgl.setSecArchivo(maximo);
                            cmtArchivosVtMgl.setArchivoAge(agenda.getWorkForceId());
                            cmtArchivosVtMgl.setOrigenArchivos(Constant.ORIGEN_CARGA_ARCHIVO_DOCUMENTOS_AGENDAMIENTOS);
                            cmtArchivosVtMgl = archivosVtMglManager.create(cmtArchivosVtMgl, usuario, perfil);

                            respuesta = cmtArchivosVtMgl.getIdArchivosVt() != null;
                        }
                        
                    } else {
                        respuesta = false;
                    }
                }
            } else {
                respuesta = false;
            }
           
        }
         return respuesta;
    }
        
    public void esperar(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {
            LOGGER.error("Ocurrio un error en el timeout");
        }
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
     * Metodo para realizar la actualizacion de una agenda en OFSC y MGL Autor:
     * Victor Bocanegra
     *
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que se va actualizar
     * en OFSC y MGL
     * @param idTecnico
     * @param usuario que ejecuta la actualizacion
     * @param perfil del usuario
     * @return CmtAgendamientoMgl agenda actualizada
     * @throws ApplicationException Excepcion lanzada al actulizar
     */
    public CmtAgendamientoMgl updateAgendasForTecnicoMgl(
            CmtAgendamientoMgl agendaMgl, String idTecnico, String usuario, int perfil)
            throws ApplicationException {
        try {

            List<TypePropertyElementUp> lstPropElements = new ArrayList<>();
            MgwTypeActualizarRequestElement request = new MgwTypeActualizarRequestElement();
            String formattedDate = sdf.format(new Date());
            List<CmtNotaOtMgl> notasOt;
            MgwHeadElement head = new MgwHeadElement(formattedDate,
                    agendaMgl.getOrdenTrabajo().getIdOt().toString());
            request.setHead(head);
            TypeDataElementUp data = new TypeDataElementUp();
            TypeAppointmentElementUp appoiment = new TypeAppointmentElementUp();

            appoiment.setAppt_number(agendaMgl.getOfpsOtId());
			cm = agendaMgl.getOrdenTrabajo().getCmObj();
            String numeroArmadoCmOFSC = armarNumeroCmOfsc(cm.getCuentaMatrizId());
            appoiment.setCustomer_number(agendaMgl.getOrdenTrabajo().getCmObj().getNumeroCuenta().toString());
            
			TypeCommandElementUp command = new TypeCommandElementUp();
            command.setAppointment(appoiment);
            command.setDate(ValidacionUtil.dateFormatyyyyMMdd(agendaMgl.getFechaAgenda()));
            command.setExternal_id(idTecnico);

            TypeCommandsArrayUp commands = new TypeCommandsArrayUp();
            commands.add(command);
            data.setCommands(commands);
            request.setData(data);

            String actTec = "NOTAS ACTUALIZACION: ASIGNACION DE TECNICO";
            notasOt = agendaMgl.getOrdenTrabajo().getNotasList();

            String obs = "";

            if (notasOt != null) {
                for (CmtNotaOtMgl notas : notasOt) {
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
            
            if (numeroArmadoCmOFSC != null && !numeroArmadoCmOFSC.isEmpty()) {
                TypePropertyElementUp numeroCuentaMatrizMER = new TypePropertyElementUp("XA_CuentaMatrizMER", numeroArmadoCmOFSC);
                lstPropElements.add(numeroCuentaMatrizMER);
            }
            
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

                LOGGER.info(String.format("Se ha modificado la agenda  numero: %s", agendaMgl.getOfpsOtId() + "  por asignacion de tecnico"));

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
 
    /**
     * metodo para consultar los documentos adjuntos en el modulo de hhpp de una
     * actividad Autor: victor bocanegra
     *
     * @param agenda
     * @param usuario
     * @param perfil
     * @return List<String>
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<String> consultarDocumentosHhpp(MglAgendaDireccion agenda, String usuario, int perfil)
            throws ApplicationException {

        List<String> links;
        List<String> archivos = new ArrayList<>();
        String documentosGuardar = "";
        CmtArchivosVtMglManager archivosVtMglManager = new CmtArchivosVtMglManager();
        try {

            URL url = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.ACTIVITIES + agenda.getOfpsId());

            userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                    .iterator().next().getParValor();
            String comAutorization = "Basic" + " " + userPwdAutorization;

            ConsumoGenerico consumoGenerico = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url,
                    comAutorization, null, null, null);

            links = consumoGenerico.consumirServicioActivities();

            if (!links.isEmpty()) {
                for (String propiedad : links) {

                    URL url2 = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.ACTIVITIES
                            + agenda.getOfpsId() + "/" + propiedad);

                    ConsumoGenerico consumoGenerico1 = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url2,
                            comAutorization, null, null, null);

                    //Consumo para bajar de eta
                    JSONObject jsonObj = consumoGenerico1.consumirServicioHttp();

                    String nameArchivo = jsonObj.getString(ConstansClient.ATR_NAME);

                    JSONArray array = jsonObj.getJSONArray(ConstansClient.ATR_LINKS);

                    String urlArchivo = null;
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

                    byte[] bytes = IOUtils.toByteArray(in);

                    boolean response = cargarArchivoAgendaOtHhppxUCM(agenda, bytes, usuario, perfil, nameArchivo);

                    if (response) {
                        LOGGER.info("Archivo cargado en el repositorio");
                    } else {
                        LOGGER.error("Ocurrio un error al momento de guardar el archivo");
                    }
                    in.close();
                }

                List<CmtArchivosVtMgl> archivosOtAgenda = archivosVtMglManager.findByIdOtHhppAndAge(agenda);
                if (!archivosOtAgenda.isEmpty()) {
                    for (CmtArchivosVtMgl archivosVtMgl : archivosOtAgenda) {
                        documentosGuardar = documentosGuardar + archivosVtMgl.getRutaArchivo() + "|";
                        archivos.add(archivosVtMgl.getRutaArchivo());
                    }

                }
                if (!documentosGuardar.isEmpty()) {
                    agenda.setDocAdjuntos(documentosGuardar);
                    agendamientoHhppManager.update(agenda, usuario, perfil);
                }
            }

        } catch (SearchOrdenesServicioFault | UploadOrdenesServicioFault | 
                ApplicationException | IOException | IllegalArgumentException | 
                DatatypeConfigurationException | JSONException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, ex);
        }
        return archivos;
    }

    /**
     * Metodo para cargar un documento de agenda del modulo de hhpp al UCM
     * Autor: Victor Bocanegra
     *
     * @param agenda
     * @param bytes
     * @param usuario que realiza la operacion
     * @param nombreArchivo
     * @param perfil del usuario que realiza la operacion
     * @return boolean
     * @throws java.net.MalformedURLException
     * @throws ApplicationException Excepcion lanzada por la carga del archivo
     * @throws java.io.FileNotFoundException
     */
    public boolean cargarArchivoAgendaOtHhppxUCM(MglAgendaDireccion agenda,
            byte[] bytes, String usuario, int perfil, String nombreArchivo)
            throws MalformedURLException, FileNotFoundException, 
            ApplicationException, DatatypeConfigurationException, SearchOrdenesServicioFault, UploadOrdenesServicioFault{

        boolean respuesta = false;
        Integer maximo;
        CmtArchivosVtMglManager archivosVtMglManager = new CmtArchivosVtMglManager();
        

        if (agenda != null) {

            URL url;
            OtHhppMgl ot = agenda.getOrdenTrabajo();
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
            
            ParametrosMgl param = parametros.findByAcronimoName(Constant.PROPERTY_URL_WS_UCM_UPLOAD_ORDENES);
            String ruta = "";
            if (param != null) {
                ruta = param.getParValor();
            }
            
            String tipoDocumental = "";
            CmtBasicaMgl basicaTipoDoc = basicaMglManager.
                    findByCodigoInternoApp(Constant.TIPO_DOCUMENTAL_VISITA_TECNICA);
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
            field3.setName("xdOrdenServicio");
            field3.setValue(ot.getOtHhppId().toString());
            documentType.getField().add(field3);
            documentTypeBuscar.getField().add(field3);
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            FieldType field4 = new FieldType();
            field4.setName("xdFechaDocumento");
            field4.setValue(dateFormat.format(date));
            documentType.getField().add(field4);
            documentTypeBuscar.getField().add(field4);
            
            maximo = archivosVtMglManager.selectMaximoSecXOtHhpp(ot,
                    Constant.ORIGEN_CARGA_ARCHIVO_DOCUMENTOS_AGENDAMIENTOS);
           
            if (maximo == 0) {
                maximo++;
            } else {
                maximo++;
            }
            
            String numUnico = ot.getOtHhppId().toString() + String.valueOf(maximo) + Constant.ORIGEN_CARGA_ARCHIVO_DOCUMENTOS_AGENDAMIENTOS;
            FieldType field5 = new FieldType();
            field5.setName("xdIdProceso");
            field5.setValue(numUnico);
            documentType.getField().add(field5);
            documentTypeBuscar.getField().add(field5);
            
            
            FieldType field6 = new FieldType();
            field6.setName("xdNumeroCtaMatriz");
            field6.setValue(ot.getDireccionId().getDirId().toString());
            documentType.getField().add(field6);
            documentTypeBuscar.getField().add(field6);
            
            fielFileType.setName(nombreArchivo);
            fielFileType.setContent(bytes);
            
            request.setDocument(documentType);
            request.setFile(fielFileType);
            
            ResponseType response = cliente.insertOrdenes(request, url);
            ActionStatusEnumType status = response.getActionStatus();
            String urlArchivo = null;
            if (status.value().equalsIgnoreCase("success")) {
                
                //Consultamos el archivo subido al gestor
                ParametrosMgl paramUrl = parametros.findByAcronimoName(Constant.PROPERTY_URL_WS_UCM_SEARCH_ORDENES);
                String rutaBuscar = "";
                if (paramUrl != null) {
                    rutaBuscar = paramUrl.getParValor();
                }
                
                URL urlBuscar = new URL(null, rutaBuscar, new sun.net.www.protocol.http.Handler());
                RequestType requestBuscar = new RequestType();
                requestBuscar.setDocument(documentTypeBuscar);
                
                ResponseType responseBuscar = cliente.findOrdenes(requestBuscar, urlBuscar);
                ActionStatusEnumType statusBuscar = responseBuscar.getActionStatus();
                
                if (statusBuscar.value().equalsIgnoreCase("success")) {
                    
                    for (DocumentType documentType1 : responseBuscar.getDocument()) {
                        if (!documentType1.getField().isEmpty()) {
                            
                            for (FieldType atributos : documentType1.getField()) {
                                if (atributos.getName().equalsIgnoreCase("xdURLDocumento")) {
                                    urlArchivo = atributos.getValue();
                                }
                            }
                            
                        }
                        
                    }
                }
                
                if (urlArchivo != null && !urlArchivo.isEmpty()) {
                    CmtArchivosVtMgl cmtArchivosVtMgl;
                    cmtArchivosVtMgl = archivosVtMglManager.findByNombreArchivoAndOrigenAndOtHhpp(nombreArchivo, Constant.ORIGEN_CARGA_ARCHIVO_DOCUMENTOS_AGENDAMIENTOS, agenda.getOrdenTrabajo());
                    
                    if (cmtArchivosVtMgl != null) {
                        LOGGER.info("No se guarda el archivo ya existe uno con esas caracteristicas.");
                    } else {
                        cmtArchivosVtMgl = new CmtArchivosVtMgl();
                        cmtArchivosVtMgl.setOrdenTrabajoHhppMglobj(agenda.getOrdenTrabajo());
                        cmtArchivosVtMgl.setRutaArchivo(urlArchivo);
                        cmtArchivosVtMgl.setNombreArchivo(nombreArchivo);
                        cmtArchivosVtMgl.setSecArchivo(maximo);
                        cmtArchivosVtMgl.setArchivoAge(new BigDecimal(agenda.getOfpsId()));
                        cmtArchivosVtMgl.setOrigenArchivos(Constant.ORIGEN_CARGA_ARCHIVO_DOCUMENTOS_AGENDAMIENTOS);
                        cmtArchivosVtMgl = archivosVtMglManager.create(cmtArchivosVtMgl, usuario, perfil);
                        
                        respuesta = cmtArchivosVtMgl.getIdArchivosVt() != null;
                    }
                } else {
                    LOGGER.info("Empiezan los 10 segundos");
                    esperar(10);
                    
                    responseBuscar = cliente.findOrdenes(requestBuscar, urlBuscar);
                    ActionStatusEnumType statusBuscar2 = responseBuscar.getActionStatus();
                    
                    if (statusBuscar2.value().equalsIgnoreCase("success")) {
                        
                        for (DocumentType documentType1 : responseBuscar.getDocument()) {
                            if (!documentType1.getField().isEmpty()) {

                                for (FieldType atributos : documentType1.getField()) {
                                    if (atributos.getName().equalsIgnoreCase("xdURLDocumento")) {
                                        urlArchivo = atributos.getValue();
                                    }
                                }

                            }

                        }
                    }
                    if (urlArchivo != null && !urlArchivo.isEmpty()) {
                        CmtArchivosVtMgl cmtArchivosVtMgl;
                        cmtArchivosVtMgl = archivosVtMglManager.
                                findByNombreArchivoAndOrigenAndOtHhpp(nombreArchivo, Constant.ORIGEN_CARGA_ARCHIVO_DOCUMENTOS_AGENDAMIENTOS, agenda.getOrdenTrabajo());
                        if (cmtArchivosVtMgl != null) {
                            LOGGER.info("No se guarda el archivo ya existe uno con esas caracteristicas.");
                        } else {
                            cmtArchivosVtMgl = new CmtArchivosVtMgl();
                            cmtArchivosVtMgl.setOrdenTrabajoHhppMglobj(agenda.getOrdenTrabajo());
                            cmtArchivosVtMgl.setRutaArchivo(urlArchivo);
                            cmtArchivosVtMgl.setNombreArchivo(nombreArchivo);
                            cmtArchivosVtMgl.setSecArchivo(maximo);
                            cmtArchivosVtMgl.setArchivoAge(new BigDecimal(agenda.getOfpsId()));
                            cmtArchivosVtMgl.setOrigenArchivos(Constant.ORIGEN_CARGA_ARCHIVO_DOCUMENTOS_AGENDAMIENTOS);
                            cmtArchivosVtMgl = archivosVtMglManager.create(cmtArchivosVtMgl, usuario, perfil);

                            respuesta = cmtArchivosVtMgl.getIdArchivosVt() != null;
                        }
                        
                    } else {
                        respuesta = false;
                    }
                }
            } else {
                respuesta = false;
            }
           
         }
        return respuesta;
    }

    /**
     * Metodo para realizar la actualizacion de una agenda en OFSC y MGL Autor:
     * Victor Bocanegra
     *
     * @param agendaMgl {@link MglAgendaDireccion} agenda que se va actualizar
     * en OFSC y MGL
     * @param idTecnico
     * @param usuario que ejecuta la actualizacion
     * @param perfil del usuario
     * @return CmtAgendamientoMgl agenda actualizada
     * @throws ApplicationException Excepcion lanzada al actulizar
     */
    public MglAgendaDireccion updateAgendasForTecnicoMglHhpp(
            MglAgendaDireccion agendaMgl, String idTecnico, String usuario, int perfil)
            throws ApplicationException {
        try {

            List<TypePropertyElementUp> lstPropElements = new ArrayList<>();
            MgwTypeActualizarRequestElement request = new MgwTypeActualizarRequestElement();
            String formattedDate = sdf.format(new Date());
            List<CmtNotasOtHhppMgl> notasOthhpp;
            CmtNotasOtHhppMglManager notasOtHhppMglManager = new CmtNotasOtHhppMglManager();
            MgwHeadElement head = new MgwHeadElement(formattedDate,
                    agendaMgl.getOrdenTrabajo().getOtHhppId().toString());
            request.setHead(head);
            TypeDataElementUp data = new TypeDataElementUp();
            TypeAppointmentElementUp appoiment = new TypeAppointmentElementUp();

            appoiment.setAppt_number(agendaMgl.getOfpsOtId());

	   String numeroArmadoCmOFSC = null;

            if (agendaMgl.getOrdenTrabajo().getTipoOtHhppId().getIsMultiagenda().equalsIgnoreCase("S")) {

                BigDecimal codCuentaPar = null;

                if (agendaMgl.getCuentaMatrizMgl() != null) {
                    codCuentaPar = agendaMgl.getCuentaMatrizMgl().getNumeroCuenta();
                }
                if (codCuentaPar != null) {
                    numeroArmadoCmOFSC = armarNumeroCmOfsc(new BigDecimal(codCuentaPar.toString()));
                }
            } else {
                numeroArmadoCmOFSC = armarNumeroCmOfsc(agendaMgl.getOrdenTrabajo().getDireccionId().getDirId());

            }
            
            appoiment.setCustomer_number(numeroArmadoCmOFSC);

            TypeCommandElementUp command = new TypeCommandElementUp();
            command.setAppointment(appoiment);
            command.setDate(ValidacionUtil.dateFormatyyyyMMdd(agendaMgl.getFechaAgenda()));
            command.setExternal_id(idTecnico);

            TypeCommandsArrayUp commands = new TypeCommandsArrayUp();
            commands.add(command);
            data.setCommands(commands);
            request.setData(data);

            String actTec = "NOTAS ACTUALIZACION: ASIGNACION DE TECNICO";

            notasOthhpp = notasOtHhppMglManager.findNotasByOtHhppId(agendaMgl.getOrdenTrabajo().getOtHhppId());

            String obs = "";

            if (notasOthhpp != null) {
                for (CmtNotasOtHhppMgl notas : notasOthhpp) {
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

                LOGGER.info(String.format("Se ha modificado la agenda  numero: %s", agendaMgl.getOfpsOtId() + "  por asignacion de tecnico"));

                return agendamientoHhppManager.update(agendaMgl, usuario, perfil);

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
            throw new ApplicationException(String.format(ERROR_ACTUALIZA_AGENDA, ex.getMessage()));
        }
    }
    
    /**
     * Metodo para consultar tecnicos disponibles para el dia
     * Autor: Victor Bocanegra
     *
     * @param ot orden de trabajo
     * @param nodo para la consulta
     * @param fechas para la consulta
     * @return ApiFindTecnicosResponse 
     * @throws ApplicationException Excepcion 
     */
    public ApiFindTecnicosResponse consultarTecnicosDisponibles(CmtOrdenTrabajoMgl ot, 
            NodoMgl nodo, List<String> fechas)
            throws ApplicationException, MalformedURLException, IOException {

        ApiFindTecnicosRequest request = new ApiFindTecnicosRequest();
        ApiFindTecnicosResponse response;
        CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl;
        List<CmtExtendidaTipoTrabajoMgl> extendidaTipoTrabajoMgl = null;
        CmtExtendidaTipoTrabajoMgl extendida;
        String subTipoWork = "";
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

        String tipoWork = ot.getBasicaIdTipoTrabajo().getAbreviatura();
        activity.setActivityType(tipoWork);

        cmtEstadoxFlujoMgl = estadoxFlujoMglManager.
                findByTipoFlujoAndEstadoInt(ot.getTipoOtObj().getTipoFlujoOt(),
                        ot.getEstadoInternoObj(), ot.getBasicaIdTecnologia());

        if (cmtEstadoxFlujoMgl.getEstadoxFlujoId() != null) {
            if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                subTipoWork = cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC().getNombreBasica();
            }

        }
        activity.setXA_WorkOrderSubtype(subTipoWork);
        activity.setNode(nodo.getNodCodigo());//"2D1022"

        mglExtendidaTecnologia = extendidaTecnologiaMglManager.findBytipoTecnologiaObj(ot.getBasicaIdTecnologia());

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
                    findBytipoTrabajoObjAndCom(comunidadRr, ot.getBasicaIdTipoTrabajo());
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

        userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
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
            requestResponsesAgeMgl.setNumeroOrden(ot.getIdOt().toString());
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
            requestResponsesAgeMgl.setNumeroOrden(ot.getIdOt().toString());
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
    
    public void cargarInformacionForEnvioNotificacion(CmtAgendamientoMgl agenda, int tipoOperacion) throws ApplicationException{

        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        CmtBasicaMglManager basicaMglManager1 = new CmtBasicaMglManager();
        
        CmtBasicaMgl tipoTrabajo = agenda.getOrdenTrabajo().getBasicaIdTipoTrabajo();
        List<CmtTipoBasicaExtMgl> lstCmtTipoBasicaExtMgl = new ArrayList<>();
        
        CmtBasicaMgl subtipoTrabajo = null;
        
        try{
             
        CmtTipoBasicaMgl cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                co.com.claro.mgl.utils.Constant.TIPO_BASICA_TIPO_GENERAL_OT);
        
        CmtTipoBasicaMgl cmtTipoBasicaMglSubTipo = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                co.com.claro.mgl.utils.Constant.TIPO_BASICA_SUB_TIPO_ORDEN_OFSC);
        
        List<CmtBasicaMgl> subTipos = basicaMglManager1.findByTipoBasica(cmtTipoBasicaMglSubTipo);
             
        if (subTipos != null && !subTipos.isEmpty()) {
            for (CmtBasicaMgl basicaMgl : subTipos) {
                if (basicaMgl.getCodigoBasica().equalsIgnoreCase(agenda.getSubTipoWorkFoce())) {
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
                        if ((agenda.getTecnicoAnticipado() != null
                                && agenda.getTecnicoAnticipado().equalsIgnoreCase("Y"))||
                                (agenda.getAgendaInmediata() != null
                                && agenda.getAgendaInmediata().equalsIgnoreCase("Y"))) { 
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
                        CmtCuentaMatrizMgl edificio = agenda.getOrdenTrabajo().getCmObj();
                        if (edificio != null && variables != null && variables.length > 0) {
                            variablesMap.put(variables[0], edificio.getNombreCuenta() != null ? edificio.getNombreCuenta() : "NA");
                            variablesMap.put(variables[1], edificio.getDireccionPrincipal().
                                    getDireccionObj().getDirFormatoIgac() != null ? edificio.getDireccionPrincipal().
                                    getDireccionObj().getDirFormatoIgac() : "NA");
                            variablesMap.put(variables[2], edificio.getMunicipio().getGpoNombre()  != null ? 
                                    edificio.getMunicipio().getGpoNombre() : "NA");
                            variablesMap.put(variables[3], edificio.getCuentaMatrizId().toString() != null ? 
                                    edificio.getCuentaMatrizId().toString() : "NA");
                            variablesMap.put(variables[4], tipoTrabajo.getNombreBasica() != null ? 
                                    tipoTrabajo.getNombreBasica() : "NA");
                            variablesMap.put(variables[5], subtipoTrabajo != null ? 
                                    subtipoTrabajo.getDescripcion(): "NA" );
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String fechAge = df.format(agenda.getFechaAgenda());
                            variablesMap.put(variables[6], fechAge != null ? fechAge : "NA");
                            variablesMap.put(variables[7], agenda.getTimeSlot() != null ? agenda.getTimeSlot() : "NA");
                            variablesMap.put(variables[8], agenda.getOrdenTrabajo().getPersonaRecVt() != null && 
                                    !agenda.getOrdenTrabajo().getPersonaRecVt().isEmpty()? 
                                    agenda.getOrdenTrabajo().getPersonaRecVt() : "NA");
                            variablesMap.put(variables[9], agenda.getOrdenTrabajo().getTelPerRecVt() != null &&
                                    !agenda.getOrdenTrabajo().getTelPerRecVt().isEmpty() ? 
                                    agenda.getOrdenTrabajo().getTelPerRecVt() : "NA");
                        }
                        String asunto = "Agendamiento No: " + agenda.getOfpsOtId() + "";
                        ResponseNotificationMail response
                                = enviarNotificationForAgendamiento(variablesMap, asunto, 
                                        agenda, tipoPlantilla.getCampoEntidadAs400());

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
                        CmtOnyxResponseDto cmtOnyxResponseDto = agenda.getCmtOnyxResponseDto();
                        String[] variables = co.com.telmex.catastro.services.util.Constant.VARIABLES_AGENDA_TRADICIONAL.split(",");
                            if (variables != null && variables.length > 0) {
                                variablesMap.put(variables[0], cmtOnyxResponseDto != null && 
                                        cmtOnyxResponseDto.getNombre() != null &&
                                        !cmtOnyxResponseDto.getNombre().isEmpty()? 
                                        cmtOnyxResponseDto.getNombre() : "NA");
                                variablesMap.put(variables[1], cmtOnyxResponseDto != null && 
                                        cmtOnyxResponseDto.getNIT_Cliente()  != null && 
                                        !cmtOnyxResponseDto.getNIT_Cliente().isEmpty()? 
                                        cmtOnyxResponseDto.getNIT_Cliente() : "NA");
                                variablesMap.put(variables[2], cmtOnyxResponseDto != null && 
                                        String.valueOf(cmtOnyxResponseDto.getOTH())  != null ? 
                                        String.valueOf(cmtOnyxResponseDto.getOTH()) :"NA");
                                variablesMap.put(variables[3], cmtOnyxResponseDto != null &&
                                        cmtOnyxResponseDto.getDireccion()  != null &&
                                        !cmtOnyxResponseDto.getDireccion().isEmpty()? 
                                        cmtOnyxResponseDto.getDireccion() : "NA");
                                variablesMap.put(variables[4], tipoTrabajo.getNombreBasica()  != null ? 
                                        tipoTrabajo.getNombreBasica() : "NA");
                                variablesMap.put(variables[5], subtipoTrabajo  != null ? 
                                        subtipoTrabajo.getDescripcion() : "NA");
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String fechAge = df.format(agenda.getFechaAgenda());
                                variablesMap.put(variables[6], fechAge  != null ? fechAge : "NA");
                                variablesMap.put(variables[7], agenda.getTimeSlot()  != null ? 
                                        agenda.getTimeSlot() : "NA");
                                variablesMap.put(variables[8], agenda.getOrdenTrabajo().getPersonaRecVt()  != null &&
                                        !agenda.getOrdenTrabajo().getPersonaRecVt().isEmpty() ? 
                                        agenda.getOrdenTrabajo().getPersonaRecVt() : "NA");
                                variablesMap.put(variables[9], agenda.getOrdenTrabajo().getTelPerRecVt()  != null && 
                                        !agenda.getOrdenTrabajo().getTelPerRecVt().isEmpty()? 
                                        agenda.getOrdenTrabajo().getTelPerRecVt() : "NA");
                            }

                        String asunto = "Agendamiento No: " + agenda.getOfpsOtId() + "";
                        ResponseNotificationMail response
                                = enviarNotificationForAgendamiento(variablesMap, asunto,
                                        agenda, tipoPlantilla.getCampoEntidadAs400());

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
                        CmtOnyxResponseDto cmtOnyxResponseDto = agenda.getCmtOnyxResponseDto();
                        String[] variables = co.com.telmex.catastro.services.util.Constant.VARIABLES_AGENDA_ANTICIPADA_INMEDIATA.split(",");
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
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String fechAge = df.format(agenda.getFechaAgenda());
                                variablesMap.put(variables[6], fechAge != null ? fechAge : "NA");
                                variablesMap.put(variables[7], agenda.getTimeSlot() != null ? 
                                        agenda.getTimeSlot() : "NA");
                                variablesMap.put(variables[8], agenda.getOrdenTrabajo().getPersonaRecVt() != null &&
                                        !agenda.getOrdenTrabajo().getPersonaRecVt().isEmpty() ? 
                                        agenda.getOrdenTrabajo().getPersonaRecVt() : "NA");
                                variablesMap.put(variables[9], agenda.getOrdenTrabajo().getTelPerRecVt() != null &&
                                        !agenda.getOrdenTrabajo().getTelPerRecVt().isEmpty() ? 
                                        agenda.getOrdenTrabajo().getTelPerRecVt() : "NA");
                                variablesMap.put(variables[10], agenda.getNombreTecnico() != null &&
                                        !agenda.getNombreTecnico().isEmpty()? 
                                        agenda.getNombreTecnico() : "NA");
                                variablesMap.put(variables[11], agenda.getIdentificacionTecnico() != null &&
                                        !agenda.getIdentificacionTecnico().isEmpty()? 
                                        agenda.getIdentificacionTecnico() : "NA");
                                variablesMap.put(variables[12], agenda.getNombreAliado() != null && 
                                        !agenda.getNombreAliado().isEmpty() ? 
                                        agenda.getNombreAliado() : "NA");
                            }
                        String asunto = "Agendamiento Anticipada/Inmediata No: " + agenda.getOfpsOtId() + "";
                        ResponseNotificationMail response
                                = enviarNotificationForAgendamiento(variablesMap, asunto, 
                                        agenda, tipoPlantilla.getCampoEntidadAs400());

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
                        CmtOnyxResponseDto cmtOnyxResponseDto = agenda.getCmtOnyxResponseDto();
                        String[] variables = co.com.telmex.catastro.services.util.Constant.VARIABLES_REAGENDA.split(",");
                            if (variables != null && variables.length > 0) {
                                variablesMap.put(variables[0],cmtOnyxResponseDto != null && 
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
                                String fechAge = df.format(agenda.getFechaAgenda());
                                variablesMap.put(variables[6], fechAge != null ? fechAge : "NA");
                                variablesMap.put(variables[7], agenda.getTimeSlotAntes() != null ? 
                                        agenda.getTimeSlotAntes() : "NA");
                                String fechReAge = df.format(agenda.getFechaReagenda());
                                variablesMap.put(variables[8], fechReAge != null ? fechReAge : "NA");
                                variablesMap.put(variables[9], agenda.getTimeSlot() != null ? 
                                        agenda.getTimeSlot() : "NA");
                                variablesMap.put(variables[10], agenda.getOrdenTrabajo().getPersonaRecVt() != null && 
                                        !agenda.getOrdenTrabajo().getPersonaRecVt().isEmpty() ? 
                                        agenda.getOrdenTrabajo().getPersonaRecVt() : "NA");
                                variablesMap.put(variables[11], agenda.getOrdenTrabajo().getTelPerRecVt() != null &&
                                        !agenda.getOrdenTrabajo().getTelPerRecVt().isEmpty()? 
                                        agenda.getOrdenTrabajo().getTelPerRecVt() : "NA");
                            }
                            
                        String asunto = "Reagendamiento de agenda No: " + agenda.getOfpsOtId() + "";
                        ResponseNotificationMail response
                                = enviarNotificationForAgendamiento(variablesMap, asunto,
                                        agenda, tipoPlantilla.getCampoEntidadAs400());

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
                        CmtCuentaMatrizMgl edificio = agenda.getOrdenTrabajo().getCmObj();
                        if (edificio != null && variables != null && variables.length > 0) {
                            variablesMap.put(variables[0], edificio.getNombreCuenta() != null ? 
                                    edificio.getNombreCuenta() : "NA");
                            variablesMap.put(variables[1], edificio.getDireccionPrincipal().
                                    getDireccionObj().getDirFormatoIgac() != null ? 
                                    edificio.getDireccionPrincipal().
                                    getDireccionObj().getDirFormatoIgac() : "NA");
                            variablesMap.put(variables[2], edificio.getMunicipio().getGpoNombre() != null ? 
                                    edificio.getMunicipio().getGpoNombre() : "NA");
                            variablesMap.put(variables[3], edificio.getCuentaMatrizId().toString() != null ? 
                                    edificio.getCuentaMatrizId().toString() : "NA");
                            variablesMap.put(variables[4], tipoTrabajo.getNombreBasica() != null ? 
                                    tipoTrabajo.getNombreBasica() : "NA");
                            variablesMap.put(variables[5], subtipoTrabajo != null ? 
                                    subtipoTrabajo.getDescripcion() : "NA");
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String fechAge = df.format(agenda.getFechaAgenda());
                            variablesMap.put(variables[6], fechAge != null ? fechAge : "NA");
                            variablesMap.put(variables[7], agenda.getTimeSlotAntes() != null ? 
                                    agenda.getTimeSlotAntes() : "NA");
                            String fechReAge = df.format(agenda.getFechaReagenda());
                            variablesMap.put(variables[8], fechReAge != null ? fechReAge : "NA");
                            variablesMap.put(variables[9], agenda.getTimeSlot() != null ? 
                                    agenda.getTimeSlot() : "NA");
                            variablesMap.put(variables[10], agenda.getOrdenTrabajo().getPersonaRecVt() != null &&
                                    !agenda.getOrdenTrabajo().getPersonaRecVt().isEmpty()? 
                                    agenda.getOrdenTrabajo().getPersonaRecVt() : "NA");
                            variablesMap.put(variables[11], agenda.getOrdenTrabajo().getTelPerRecVt() != null && 
                                    !agenda.getOrdenTrabajo().getTelPerRecVt().isEmpty()? 
                                    agenda.getOrdenTrabajo().getTelPerRecVt() : "NA");
                        }
                        String asunto = "Reagendamiento de agenda No: " + agenda.getOfpsOtId() + "";
                        ResponseNotificationMail response
                                = enviarNotificationForAgendamiento(variablesMap, asunto, 
                                        agenda, tipoPlantilla.getCampoEntidadAs400());

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
                        CmtOnyxResponseDto cmtOnyxResponseDto = agenda.getCmtOnyxResponseDto();
                            String[] variables = co.com.telmex.catastro.services.util.Constant.VARIABLES_CANCELADA.split(",");
                            String[] variablesVis = co.com.telmex.catastro.services.util.Constant.VARIABLES_VISIBLE_TABLAS.split(",");
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
                                variablesMap.put(variables[6], agenda.getComentarioCancela() != null ? 
                                        agenda.getComentarioCancela() : "NA");
                                //Consulto las agendas de la orden
                                List<CmtAgendamientoMgl> agendas = manager.
                                        agendasPorOrdenAndSubTipoWorkfoce(agenda.getOrdenTrabajo(),
                                                agenda.getSubTipoWorkFoce());

                                if (agendas != null && !agendas.isEmpty()) {
                                    int index = 6 + 1;
                                    int visible = 0;
                                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                    for (CmtAgendamientoMgl agendaMgl : agendas) {
                                       
                                        String fechAge = df.format(agenda.getFechaAgenda());
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

                        String asunto = "Cancelamiento de agenda No: " + agenda.getOfpsOtId() + "";
                        ResponseNotificationMail response
                                = enviarNotificationForAgendamiento(variablesMap, asunto, 
                                        agenda, tipoPlantilla.getCampoEntidadAs400());

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
                        CmtCuentaMatrizMgl edificio = agenda.getOrdenTrabajo().getCmObj();
                        if (edificio != null && variables != null && variables.length > 0) {
                            variablesMap.put(variables[0], edificio.getNombreCuenta() != null ? 
                                    edificio.getNombreCuenta() : "NA");
                            variablesMap.put(variables[1], edificio.getDireccionPrincipal().
                                    getDireccionObj().getDirFormatoIgac() != null ? 
                                    edificio.getDireccionPrincipal().
                                    getDireccionObj().getDirFormatoIgac() : "NA");
                            variablesMap.put(variables[2], edificio.getMunicipio().getGpoNombre() != null ? 
                                    edificio.getMunicipio().getGpoNombre() : "NA");
                            variablesMap.put(variables[3], edificio.getCuentaMatrizId().toString() != null ? 
                                    edificio.getCuentaMatrizId().toString() : "NA");
                            variablesMap.put(variables[4], tipoTrabajo.getNombreBasica() != null ? 
                                    tipoTrabajo.getNombreBasica() : "NA");
                            variablesMap.put(variables[5], subtipoTrabajo != null ? 
                                    subtipoTrabajo.getDescripcion() : "NA");
                            variablesMap.put(variables[6], agenda.getComentarioCancela() != null ? 
                                    agenda.getComentarioCancela() : "NA");

                            //Consulto las agendas de la orden
                            List<CmtAgendamientoMgl> agendas = manager.
                                    agendasPorOrdenAndSubTipoWorkfoce(agenda.getOrdenTrabajo(),
                                            agenda.getSubTipoWorkFoce());

                            if (agendas != null && !agendas.isEmpty()) {
                                int index = 6 + 1;
                                int visible = 0;
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                for (CmtAgendamientoMgl agendaMgl : agendas) {
                                    
                                    String fechAge = df.format(agenda.getFechaAgenda());
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
                        String asunto = "Cancelamiento de agenda No: " + agenda.getOfpsOtId() + "";
                        ResponseNotificationMail response
                                = enviarNotificationForAgendamiento(variablesMap, asunto, 
                                        agenda, tipoPlantilla.getCampoEntidadAs400());

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
                        CmtOnyxResponseDto cmtOnyxResponseDto = agenda.getCmtOnyxResponseDto();
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
                                variablesMap.put(variables[6], agenda.getOrdenTrabajo().getPersonaRecVt() != null && 
                                        !agenda.getOrdenTrabajo().getPersonaRecVt().isEmpty()? 
                                        agenda.getOrdenTrabajo().getPersonaRecVt() : "NA");
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String fechFin = null;
                                if (agenda.getFechaFinVt() != null) {
                                    fechFin = df.format(agenda.getFechaFinVt());
                                }
                                variablesMap.put(variables[7], fechFin != null ? fechFin : "NA");
                                DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
                                if (agenda.getFechaInivioVt() != null) {
                                    variablesMap.put(variables[8], hourFormat.format(agenda.getFechaInivioVt()));
                                } else {
                                    variablesMap.put(variables[8], "NA");
                                }
                                if (agenda.getFechaFinVt() != null) {
                                    variablesMap.put(variables[9], hourFormat.format(agenda.getFechaFinVt()));
                                } else {
                                    variablesMap.put(variables[9], "NA");
                                }
                                variablesMap.put(variables[10], agenda.getIdentificacionTecnico() != null && 
                                        !agenda.getIdentificacionTecnico().isEmpty()? 
                                        agenda.getIdentificacionTecnico() : "NA");
                                variablesMap.put(variables[11], agenda.getNombreTecnico() != null &&
                                        !agenda.getNombreTecnico().isEmpty()? 
                                        agenda.getNombreTecnico() : "NA");
                                variablesMap.put(variables[12], agenda.getNombreAliado() != null &&
                                        !agenda.getNombreAliado().isEmpty() ? 
                                        agenda.getNombreAliado() : "NA");
                                variablesMap.put(variables[13], agenda.getObservacionesTecnico() != null &&
                                        !agenda.getObservacionesTecnico().isEmpty() ? 
                                        agenda.getObservacionesTecnico() : "NA");
                                agenda = consultarFirma(agenda);
                                variablesMap.put(variables[14], agenda.getNameArchivo() != null ? 
                                        agenda.getNameArchivo() : "NA");
                            }

                        String asunto = "Completado de agenda No: " + agenda.getOfpsOtId() + "";
                        ResponseNotificationMail response
                                = enviarNotificationForAgendamiento(variablesMap, asunto, 
                                        agenda,tipoPlantilla.getCampoEntidadAs400());

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
                        CmtCuentaMatrizMgl edificio = agenda.getOrdenTrabajo().getCmObj();
                        if (edificio != null && variables != null && variables.length > 0) {
                            variablesMap.put(variables[0], edificio.getNombreCuenta() != null ? 
                                    edificio.getNombreCuenta() : "NA");
                            variablesMap.put(variables[1], edificio.getDireccionPrincipal().
                                    getDireccionObj().getDirFormatoIgac() != null ? edificio.getDireccionPrincipal().
                                    getDireccionObj().getDirFormatoIgac() : "NA");
                            variablesMap.put(variables[2], edificio.getMunicipio().getGpoNombre() != null ? 
                                    edificio.getMunicipio().getGpoNombre() : "NA");
                            variablesMap.put(variables[3], edificio.getCuentaMatrizId().toString() != null ? 
                                    edificio.getCuentaMatrizId().toString() : "NA");
                            variablesMap.put(variables[4], tipoTrabajo.getNombreBasica() != null ? 
                                    tipoTrabajo.getNombreBasica() : "NA");
                            variablesMap.put(variables[5], subtipoTrabajo != null ? 
                                    subtipoTrabajo.getDescripcion() : "NA");
                            variablesMap.put(variables[6], agenda.getOrdenTrabajo().getPersonaRecVt() != null
                                    && !agenda.getOrdenTrabajo().getPersonaRecVt().isEmpty()
                                    ? agenda.getOrdenTrabajo().getPersonaRecVt() : "NA");
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String fechFin = null;
                            if(agenda.getFechaFinVt() != null){
                              fechFin = df.format(agenda.getFechaFinVt());  
                            }
                            variablesMap.put(variables[7], fechFin != null ? fechFin : "NA");
                            DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
                            if (agenda.getFechaInivioVt() != null) {
                                variablesMap.put(variables[8], hourFormat.format(agenda.getFechaInivioVt()));
                            } else {
                                variablesMap.put(variables[8], "NA");
                            }
                            if (agenda.getFechaFinVt() != null) {
                                variablesMap.put(variables[9], hourFormat.format(agenda.getFechaFinVt()));
                            } else {
                                variablesMap.put(variables[9], "NA");
                            }
                            variablesMap.put(variables[10], agenda.getIdentificacionTecnico() != null
                                    && !agenda.getIdentificacionTecnico().isEmpty()
                                    ? agenda.getIdentificacionTecnico() : "NA");
                            variablesMap.put(variables[11], agenda.getNombreTecnico() != null
                                    && !agenda.getNombreTecnico().isEmpty()
                                    ? agenda.getNombreTecnico() : "NA");
                            variablesMap.put(variables[12], agenda.getNombreAliado() != null
                                    && !agenda.getNombreAliado().isEmpty()
                                    ? agenda.getNombreAliado() : "NA");
                            variablesMap.put(variables[13], agenda.getObservacionesTecnico() != null
                                    && !agenda.getObservacionesTecnico().isEmpty()
                                    ? agenda.getObservacionesTecnico() : "NA");
                            agenda = consultarFirma(agenda);
                            variablesMap.put(variables[14], agenda.getNameArchivo() != null
                                    ? agenda.getNameArchivo() : "NA");
                        }
                        String asunto = "Completado de agenda  No: " + agenda.getOfpsOtId() + "";
                        ResponseNotificationMail response
                                = enviarNotificationForAgendamiento(variablesMap, asunto,
                                        agenda, tipoPlantilla.getCampoEntidadAs400());

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
        } catch (ApplicationException  ex) {
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
            String subject, CmtAgendamientoMgl agenda, String nombrePlantilla)
            throws ApplicationException {

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
        messageBox.setCustomerBox(agenda.getOrdenTrabajo().getEmailPerRecVT());

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
            attach.setEncode("BASE64");
            attach.setContent(agenda.getContentArchivo());
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
            throw new ApplicationException("No se encuentra configurado el parámetro " +
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_ENVIO_CORREOS);
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
    
    public CmtOnyxResponseDto returnInfoOnix(CmtAgendamientoMgl agendaMgl) throws ApplicationException {

        CmtOnyxResponseDto onyxResponseDto = new CmtOnyxResponseDto();
        onyxResponseDto.setNIT_Cliente("");
        onyxResponseDto.setOTH(0);
        onyxResponseDto.setNombre("");
        onyxResponseDto.setDireccion("");
        
        OnyxOtCmDirManager onyxOtCmDirManager = new OnyxOtCmDirManager();
        if (agendaMgl.getOrdenTrabajo().getIdOt() != null
                && agendaMgl.getOrdenTrabajo().getOnyxOtHija() != null) {
            List<OnyxOtCmDir> listaOnyx = onyxOtCmDirManager.findOnyxByOtCmAndOtHija(agendaMgl.getOrdenTrabajo().getIdOt(), agendaMgl.getOrdenTrabajo().getOnyxOtHija().toString());
            if (listaOnyx != null && !listaOnyx.isEmpty()) {

                OnyxOtCmDir objOnyx = listaOnyx.get(0);
                onyxResponseDto.setNIT_Cliente(objOnyx.getNit_Cliente_Onyx());
                onyxResponseDto.setOTH(Integer.parseInt(objOnyx.getOnyx_Ot_Hija_Cm()));
                onyxResponseDto.setNombre(objOnyx.getNombre_Cliente_Onyx());
                onyxResponseDto.setDireccion(objOnyx.getDireccion_Onyx());
            }
        }

        return onyxResponseDto;
    }
    
    public CmtOnyxResponseDto returnInfoOnixHhpp(MglAgendaDireccion agendaMgl) throws ApplicationException {

        CmtOnyxResponseDto onyxResponseDto = new CmtOnyxResponseDto();

        OnyxOtCmDirManager onyxOtCmDirManager = new OnyxOtCmDirManager();
        List<OnyxOtCmDir> listaOnyx = onyxOtCmDirManager.findOnyxOtHHppById(agendaMgl.getOrdenTrabajo().getOtHhppId());
        if (listaOnyx != null && !listaOnyx.isEmpty()) {

            OnyxOtCmDir objOnyx = listaOnyx.get(0);
            onyxResponseDto.setNIT_Cliente(objOnyx.getNit_Cliente_Onyx());
            onyxResponseDto.setOTH(Integer.parseInt(objOnyx.getOnyx_Ot_Hija_Dir()));
            onyxResponseDto.setNombre(objOnyx.getNombre_Cliente_Onyx());
            onyxResponseDto.setDireccion(objOnyx.getDireccion_Onyx());
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
    public CmtAgendamientoMgl consultarFirma(CmtAgendamientoMgl agenda)
            throws ApplicationException {

        String urlArchivo = null;

        try {

            userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                    .iterator().next().getParValor();
            String comAutorization = "Basic" + " " + userPwdAutorization;

            URL url2 = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.ACTIVITIES
                    + agenda.getWorkForceId() + "/" + "csign");

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
                in.close();
                String imageStr = Base64.encodeBase64String(bytes);

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
     * Metodo para actualizar/cerrar una orden de trabajo en ONIX
     *
     * @author Victor Bocanegra
     * @param agendaCcmm agenda CCMM orden actualizar/cerrar
     * @param agendaHhpp agenda HHPP orden actualizar/cerrar
     * @param controlCierre tipo de cierre
     * @param codigoRazon codigo razon OFSC
     * @param user usuario que ejecuta la operacion
     * @return UpdateClosingTaskResponse
     * @throws ApplicationException
     */
    public void updateCloseOrdenOnix(CmtAgendamientoMgl agendaCcmm,
            MglAgendaDireccion agendaHhpp, int controlCierre,
            String codigoRazon, String user, String notasNodone)
            throws ApplicationException {

        UpdateClosingTaskResponse responseType;
        co.com.claro.visitastecnicas.ws.proxy.HeaderRequest headerRequest = new co.com.claro.visitastecnicas.ws.proxy.HeaderRequest();
        UpdateClosingTaskRequest requestUp = new UpdateClosingTaskRequest();
        HomologacionRazonesMglManager homologacionRazonesMglManager = new HomologacionRazonesMglManager();
        HomologacionRazonesMgl homologacionRazonesMgl;
        URL url = null;
        String wsURL1;

        GregorianCalendar c = new GregorianCalendar();
        Date fecha = new Date();
        c.setTime(fecha);
        
        try {

            ClientOnix clientOnix = new ClientOnix();
            XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            headerRequest.setRequestDate(date2);
            headerRequest.setSystem("MER");
            
            ParametrosMgl param = parametrosMglManager.
                    findByAcronimoName(Constant.PROPERTY_URL_WS_NOTIFICA_UPDATE_CLOSING_ONIX);
            if (param != null) {
                wsURL1 = param.getParValor();
                url = new URL(wsURL1);
            }
            
            //Validamos disponibilidad del servicio
            ConsumoGenerico.conectionWsdlTest(url, Constant.PROPERTY_URL_WS_NOTIFICA_UPDATE_CLOSING_ONIX);
            //Fin Validacion disponibilidad del servicio

            if (agendaCcmm != null) {
                //Es orden de CCMM
                switch (controlCierre) {
                    case 1:
                        //Ultima agenda
                        if (agendaCcmm.getOrdenTrabajo() != null
                                && agendaCcmm.getOrdenTrabajo().getOnyxOtHija() != null) {
                            requestUp.setIIncidentIdOTH(Integer.parseInt(agendaCcmm.getOrdenTrabajo().
                                    getOnyxOtHija().toString()));
                        } else {
                            requestUp.setIIncidentIdOTH(0);
                        }
                        //Homologacion codigos
                        homologacionRazonesMgl
                                = homologacionRazonesMglManager.findHomologacionByCodigoOFSC(codigoRazon);
                        if (homologacionRazonesMgl != null
                                && homologacionRazonesMgl.getHomologacionRazonesId() != null) {
                            requestUp.setICode1(Integer.parseInt(homologacionRazonesMgl.getCodResOnix()));
                            String nota = armarNota(agendaCcmm, null);
                            String msgNota = "Nota Onix: " + nota;
                            LOGGER.info(msgNota);
                            requestUp.setVchAnotaciones(nota);
                            requestUp.setIEventoId(1);
                            requestUp.setChuserid(user);
                            
                            //Guardar información del Request en BD
                            ParametrosMgl saveRequestResponses = parametrosMglManager.
                                    findByAcronimoName(Constant.ACTIVAR_SAVE_REQUEST_RESPONSES);
                            String save = "";
                            if (saveRequestResponses != null) {
                                save = saveRequestResponses.getParValor();
                            }
                            if (save.equalsIgnoreCase("1")) {
                                //esta activo el flag para almacenar las peticiones
                                RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                                String json;
                                json = JSONUtils.objetoToJson(requestUp);
                                requestResponsesAgeMgl.setTipoPeticion("Request");
                                requestResponsesAgeMgl.setTipoOperacion("updateClosingTask 1");
                                requestResponsesAgeMgl.setContenidoPeticion(json);
                                requestResponsesAgeMgl.setNumeroOrden(agendaCcmm.getOfpsOtId());
                                requestResponsesAgeMgl.setFechaCreacion(new Date());
                                requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                                if (requestResponsesAgeMgl.getRequestRespId() != null) {
                                    LOGGER.info("registro almacenado satisfatoriamente");
                                }
                            }
                            //Fin Guardar Request
                            
                            responseType = clientOnix.updateClosingTask(headerRequest,requestUp, url);

                            if (responseType != null) {
                                if (responseType.getIcodOperacion().equalsIgnoreCase("1")) {
                                    String msnError = "Operación Exitosa: " + responseType.getVchDescOperacion() + "";
                                    LOGGER.info(msnError);
                                }
                            
                                //Guardar información del Response en BD
                                if (save.equalsIgnoreCase("1")) {
                                    //esta activo el flag para almacenar las peticiones
                                    RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                                    String json;
                                    json = JSONUtils.objetoToJson(responseType);
                                    requestResponsesAgeMgl.setTipoPeticion("Response");
                                    requestResponsesAgeMgl.setTipoOperacion("updateClosingTask 1");
                                    requestResponsesAgeMgl.setContenidoPeticion(json);
                                    requestResponsesAgeMgl.setNumeroOrden(agendaCcmm.getOfpsOtId());
                                    requestResponsesAgeMgl.setFechaCreacion(new Date());
                                    requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                                    if (requestResponsesAgeMgl.getRequestRespId() != null) {
                                        LOGGER.info("registro almacenado satisfatoriamente");
                                    }
                                }
                                //Fin Guardar Response
                            }
                        } else {
                            LOGGER.error("No se encontró homologación configurada para código razón OFSC: " + codigoRazon + "");
                        }
                        break;
                    case 2:
                        //No es cierre OK
                        if (agendaCcmm.getOrdenTrabajo() != null
                                && agendaCcmm.getOrdenTrabajo().getOnyxOtHija() != null) {
                            requestUp.setIIncidentIdOTH(Integer.parseInt(agendaCcmm.getOrdenTrabajo().
                                    getOnyxOtHija().toString()));
                        } else {
                            requestUp.setIIncidentIdOTH(0);
                        }
                        //Homologacion codigos
                        homologacionRazonesMgl
                                = homologacionRazonesMglManager.findHomologacionByCodigoOFSC(codigoRazon);
                        if (homologacionRazonesMgl != null
                                && homologacionRazonesMgl.getHomologacionRazonesId() != null) {
                            requestUp.setICode1(Integer.parseInt(homologacionRazonesMgl.getCodResOnix()));
                            String nota;
                            if (notasNodone != null) {
                                nota = notasNodone;
                            } else {
                                nota = armarNota(agendaCcmm, null);
                            }
                            String msgNota = NOTA_ARMADA_ONIX + nota;
                            LOGGER.info(msgNota);
                            requestUp.setVchAnotaciones(nota);
                            requestUp.setIEventoId(2);
                            requestUp.setChuserid(user);
                            
                            //Guardar información del Request en BD
                            ParametrosMgl saveRequestResponses = parametrosMglManager.
                                    findByAcronimoName(Constant.ACTIVAR_SAVE_REQUEST_RESPONSES);
                            String save = "";
                            if (saveRequestResponses != null) {
                                save = saveRequestResponses.getParValor();
                            }
                            if (save.equalsIgnoreCase("1")) {
                                //esta activo el flag para almacenar las peticiones
                                RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                                String json;
                                json = JSONUtils.objetoToJson(requestUp);
                                requestResponsesAgeMgl.setTipoPeticion("Request");
                                requestResponsesAgeMgl.setTipoOperacion("updateClosingTask 2");
                                requestResponsesAgeMgl.setContenidoPeticion(json);
                                requestResponsesAgeMgl.setNumeroOrden(agendaCcmm.getOfpsOtId());
                                requestResponsesAgeMgl.setFechaCreacion(new Date());
                                requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                                if (requestResponsesAgeMgl.getRequestRespId() != null) {
                                    LOGGER.info("registro almacenado satisfatoriamente");
                                }
                            }
                            //Fin Guardar Request
                            
                            responseType = clientOnix.updateClosingTask(headerRequest,requestUp, url);

                            if (responseType != null) {
                                if (responseType.getIcodOperacion().equalsIgnoreCase("1")) {
                                    String msnError = "Operación Exitosa: " + responseType.getVchDescOperacion() + "";
                                    LOGGER.info(msnError);
                                }
                                
                                //Guardar información del Response en BD
                                if (save.equalsIgnoreCase("1")) {
                                    //esta activo el flag para almacenar las peticiones
                                    RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                                    String json;
                                    json = JSONUtils.objetoToJson(responseType);
                                    requestResponsesAgeMgl.setTipoPeticion("Response");
                                    requestResponsesAgeMgl.setTipoOperacion("updateClosingTask 2");
                                    requestResponsesAgeMgl.setContenidoPeticion(json);
                                    requestResponsesAgeMgl.setNumeroOrden(agendaCcmm.getOfpsOtId());
                                    requestResponsesAgeMgl.setFechaCreacion(new Date());
                                    requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                                    if (requestResponsesAgeMgl.getRequestRespId() != null) {
                                        LOGGER.info("registro almacenado satisfatoriamente");
                                    }
                                }
                                //Fin Guardar Response
                            }
                        } else {
                            LOGGER.error("No se encontró homologación configurada para código razon OFSC: " + codigoRazon + "");
                        }
                        break;
                    default:
                        //Cierre sin codigo razon de OFSC
                        break;
                }
            } else if (agendaHhpp != null) {
                //Es orden de HHPP
                switch (controlCierre) {
                    case 1:
                        //Ultima agenda
                        if (agendaHhpp.getOrdenTrabajo() != null
                                && agendaHhpp.getOrdenTrabajo().getOnyxOtHija() != null) {
                            requestUp.setIIncidentIdOTH(Integer.parseInt(agendaHhpp.getOrdenTrabajo().
                                    getOnyxOtHija().toString()));
                        } else {
                            requestUp.setIIncidentIdOTH(0);
                        }
                        //Homologacion codigos
                        homologacionRazonesMgl
                                = homologacionRazonesMglManager.findHomologacionByCodigoOFSC(codigoRazon);
                        if (homologacionRazonesMgl != null
                                && homologacionRazonesMgl.getHomologacionRazonesId() != null) {
                            requestUp.setICode1(Integer.parseInt(homologacionRazonesMgl.getCodResOnix()));
                            String nota = armarNota(null, agendaHhpp);
                            String msgNota = NOTA_ARMADA_ONIX + nota;
                            LOGGER.info(msgNota);
                            requestUp.setVchAnotaciones(nota);
                            requestUp.setIEventoId(1);
                            requestUp.setChuserid(user);
                            
                            //Guardar información del Request en BD
                            ParametrosMgl saveRequestResponses = parametrosMglManager.
                                    findByAcronimoName(Constant.ACTIVAR_SAVE_REQUEST_RESPONSES);
                            String save = "";
                            if (saveRequestResponses != null) {
                                save = saveRequestResponses.getParValor();
                            }
                            if (save.equalsIgnoreCase("1")) {
                                //esta activo el flag para almacenar las peticiones
                                RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                                String json;
                                json = JSONUtils.objetoToJson(requestUp);
                                requestResponsesAgeMgl.setTipoPeticion("Request");
                                requestResponsesAgeMgl.setTipoOperacion("updateClosingTask 1");
                                requestResponsesAgeMgl.setContenidoPeticion(json);
                                requestResponsesAgeMgl.setNumeroOrden(agendaHhpp.getOfpsOtId());
                                requestResponsesAgeMgl.setFechaCreacion(new Date());
                                requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                                if (requestResponsesAgeMgl.getRequestRespId() != null) {
                                    LOGGER.info("registro almacenado satisfatoriamente");
                                }
                            }
                            //Fin Guardar Request
                            
                            responseType = clientOnix.updateClosingTask(headerRequest,requestUp, url);

                            if (responseType != null) {
                                if (responseType.getIcodOperacion().equalsIgnoreCase("1")) {
                                    String msnError = "Operación Exitosa: " + responseType.getVchDescOperacion() + "";
                                    LOGGER.info(msnError);
                                }
                                
                                //Guardar información del Response en BD
                                if (save.equalsIgnoreCase("1")) {
                                    //esta activo el flag para almacenar las peticiones
                                    RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                                    String json;
                                    json = JSONUtils.objetoToJson(responseType);
                                    requestResponsesAgeMgl.setTipoPeticion("Response");
                                    requestResponsesAgeMgl.setTipoOperacion("updateClosingTask 1");
                                    requestResponsesAgeMgl.setContenidoPeticion(json);
                                    requestResponsesAgeMgl.setNumeroOrden(agendaHhpp.getOfpsOtId());
                                    requestResponsesAgeMgl.setFechaCreacion(new Date());
                                    requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                                    if (requestResponsesAgeMgl.getRequestRespId() != null) {
                                        LOGGER.info("registro almacenado satisfatoriamente");
                                    }
                                }
                                //Fin Guardar Response
                            }
                        } else {
                            LOGGER.error("No se encontró homologación configurada para código razón OFSC: " + codigoRazon + "");
                        }
                        break;
                    case 2:
                        //No es cierre OK
                        if (agendaHhpp.getOrdenTrabajo() != null
                                && agendaHhpp.getOrdenTrabajo().getOnyxOtHija() != null) {
                            requestUp.setIIncidentIdOTH(Integer.parseInt(agendaHhpp.getOrdenTrabajo().
                                    getOnyxOtHija().toString()));
                        } else {
                            requestUp.setIIncidentIdOTH(0);
                        }
                        //Homologacion codigos
                        homologacionRazonesMgl
                                = homologacionRazonesMglManager.findHomologacionByCodigoOFSC(codigoRazon);
                        if (homologacionRazonesMgl != null
                                && homologacionRazonesMgl.getHomologacionRazonesId() != null) {
                            requestUp.setICode1(Integer.parseInt(homologacionRazonesMgl.getCodResOnix()));
                             String nota;
                            if (notasNodone != null) {
                                nota = notasNodone;
                            } else {
                                nota = armarNota(null, agendaHhpp);
                            }
                            String infoNota = NOTA_ARMADA_ONIX + nota;
                            LOGGER.info(infoNota);
                            requestUp.setVchAnotaciones(nota);
                            requestUp.setIEventoId(2);
                            requestUp.setChuserid(user);
                            
                            //Guardar información del Request en BD
                            ParametrosMgl saveRequestResponses = parametrosMglManager.
                                    findByAcronimoName(Constant.ACTIVAR_SAVE_REQUEST_RESPONSES);
                            String save = "";
                            if (saveRequestResponses != null) {
                                save = saveRequestResponses.getParValor();
                            }
                            if (save.equalsIgnoreCase("1")) {
                                //esta activo el flag para almacenar las peticiones
                                RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                                String json;
                                json = JSONUtils.objetoToJson(requestUp);
                                requestResponsesAgeMgl.setTipoPeticion("Request");
                                requestResponsesAgeMgl.setTipoOperacion("updateClosingTask 2");
                                requestResponsesAgeMgl.setContenidoPeticion(json);
                                requestResponsesAgeMgl.setNumeroOrden(agendaHhpp.getOfpsOtId());
                                requestResponsesAgeMgl.setFechaCreacion(new Date());
                                requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                                if (requestResponsesAgeMgl.getRequestRespId() != null) {
                                    LOGGER.info("registro almacenado satisfatoriamente");
                                }
                            }
                            //Fin Guardar Request
                            
                            responseType = clientOnix.updateClosingTask(headerRequest,requestUp, url);

                            if (responseType != null) {
                                if (responseType.getIcodOperacion().equalsIgnoreCase("1")) {
                                    String msnError = "Operacion Exitosa: " + responseType.getVchDescOperacion() + "";
                                    LOGGER.info(msnError);
                                }
                                
                                //Guardar información del Response en BD
                                if (save.equalsIgnoreCase("1")) {
                                    //esta activo el flag para almacenar las peticiones
                                    RequestResponsesAgeMgl requestResponsesAgeMgl = new RequestResponsesAgeMgl();
                                    String json;
                                    json = JSONUtils.objetoToJson(responseType);
                                    requestResponsesAgeMgl.setTipoPeticion("Response");
                                    requestResponsesAgeMgl.setTipoOperacion("updateClosingTask 2");
                                    requestResponsesAgeMgl.setContenidoPeticion(json);
                                    requestResponsesAgeMgl.setNumeroOrden(agendaHhpp.getOfpsOtId());
                                    requestResponsesAgeMgl.setFechaCreacion(new Date());
                                    requestResponsesAgeMgl = requestResponsesAgeMglManager.create(requestResponsesAgeMgl);
                                    if (requestResponsesAgeMgl.getRequestRespId() != null) {
                                        LOGGER.info("registro almacenado satisfatoriamente");
                                    }
                                }
                                //Fin Guardar Response
                            }
                        } else {
                            LOGGER.error("No se encontró homologación configurada para código razon OFSC: " + codigoRazon + "");
                        }
                        break;
                    default:
                        //Cierre sin codigo razon de OFSC
                        break;
                }
            }
        } catch (ApplicationException
                 |DatatypeConfigurationException | FaultMessage |IOException ex ) {
            throw new ApplicationException(ex.getMessage());
        }

    }

    public String armarNota(CmtAgendamientoMgl agendaCcmm,
            MglAgendaDireccion agendaHhpp) throws ApplicationException {

        String nota = "";
        List<String> notaFormularios;

        if (agendaCcmm != null) {
            CmtOrdenTrabajoMgl ordenCCMM = agendaCcmm.getOrdenTrabajo();
            CmtBasicaMgl tipoTrabajo = ordenCCMM.getBasicaIdTipoTrabajo();
            nota = "Notas Orden CCMM: \n";

            //Consulto Formularios
            notaFormularios = consultarFormulariosOfsc(null, agendaCcmm);
            if (notaFormularios != null && !notaFormularios.isEmpty()) {
                //Verifico si viene nota del tecnico
                String notaTecnico;
                String notaForm;
                CmtBasicaMgl notaAgenda = basicaMglManager.findByCodigoInternoApp(Constant.BASICA_TIPO_NOTA_AGENDA);

                if (notaFormularios.get(0) != null && !notaFormularios.get(0).isEmpty()) {
                    notaTecnico = notaFormularios.get(0);
                    //Persisto la nota en MER
                    String descripcion = "Nota Tecnico";
                    CmtNotaOtMgl notaOtMgl = new CmtNotaOtMgl();
                    notaOtMgl.setDescripcion(descripcion);
                    notaOtMgl.setTipoNotaObj(notaAgenda);
                    notaOtMgl.setNota(notaTecnico);
                    notaOtMgl.setOrdenTrabajoObj(ordenCCMM);
                    CmtNotaOtMglManager notaOtMglManager = new CmtNotaOtMglManager();
                    notaOtMgl = notaOtMglManager.crear(notaOtMgl, "OFSC", 1);
                    if (notaOtMgl != null && notaOtMgl.getNotaOtId() != null) {
                        LOGGER.info("Nota creada satisfatoriamente");
                    }
                }
                if (notaFormularios.get(1) != null && !notaFormularios.get(1).isEmpty()) {
                    notaForm = notaFormularios.get(1);
                    //Persisto la nota en MER
                    String descripcion2 = tipoTrabajo.getNombreBasica() + "-"
                            + ordenCCMM.getOnyxOtHija() + "-" + ordenCCMM.getIdOt() + " \n";
                    CmtNotaOtMgl notaOtMgl2 = new CmtNotaOtMgl();
                    notaOtMgl2.setDescripcion(descripcion2);
                    notaOtMgl2.setTipoNotaObj(notaAgenda);
                    notaOtMgl2.setNota(notaForm);
                    notaOtMgl2.setOrdenTrabajoObj(ordenCCMM);
                    CmtNotaOtMglManager notaOtMglManager1 = new CmtNotaOtMglManager();
                    notaOtMgl2 = notaOtMglManager1.crear(notaOtMgl2, "OFSC", 1);
                    if (notaOtMgl2 != null && notaOtMgl2.getNotaOtId() != null) {
                        LOGGER.info("Nota creada satisfatoriamente");
                    }
                }
            }
            CmtNotaOtMglManager notasMglManager = new CmtNotaOtMglManager();
            List<CmtNotaOtMgl> lstCmtNotaOtMgls = notasMglManager.findByOt(ordenCCMM);

            if (lstCmtNotaOtMgls != null
                    && !lstCmtNotaOtMgls.isEmpty()) {
                String notas = "";
                for (CmtNotaOtMgl notaOtMgl : lstCmtNotaOtMgls) {

                    notas = notas + "Descripcion: " + notaOtMgl.getDescripcion() + " \n"
                            + "Tipo de nota: " + notaOtMgl.getTipoNotaObj().getNombreBasica() + " \n"
                            + "Nota: " + notaOtMgl.getNota() + " \n";
                }
                nota = nota + notas;
            }
        } else if (agendaHhpp != null) {
            OtHhppMgl ordenHhpp = agendaHhpp.getOrdenTrabajo();
            nota = "Notas Orden HHPP: \n";
            CmtBasicaMgl tipoTrabajo = ordenHhpp.getTipoOtHhppId().getTipoTrabajoOFSC();
            //Consulto Formularios
            notaFormularios = consultarFormulariosOfsc(agendaHhpp, null);
            if (notaFormularios != null && !notaFormularios.isEmpty()) {
                //Verifico si viene nota del tecnico
                String notaTecnico;
                String notaForm;
                CmtBasicaMgl notaAgenda = basicaMglManager.findByCodigoInternoApp(Constant.BASICA_TIPO_NOTA_AGENDA);

                if (notaFormularios.get(0) != null && !notaFormularios.get(0).isEmpty()) {
                    notaTecnico = notaFormularios.get(0);
                    //Persisto la nota en MER
                    String descripcion = "Nota Tecnico";
                    CmtNotasOtHhppMgl notaOtHhppMgl = new CmtNotasOtHhppMgl();
                    notaOtHhppMgl.setDescripcion(descripcion);
                    notaOtHhppMgl.setTipoNotaObj(notaAgenda);
                    notaOtHhppMgl.setNota(notaTecnico);
                    notaOtHhppMgl.setOtHhppId(ordenHhpp);
                    CmtNotasOtHhppMglManager notaOtHhppMglManager = new CmtNotasOtHhppMglManager();
                    notaOtHhppMgl = notaOtHhppMglManager.create(notaOtHhppMgl, "OFSC", 1);
                    if (notaOtHhppMgl != null && notaOtHhppMgl.getNotasId() != null) {
                        LOGGER.info("Nota creada satisfatoriamente");
                    }
                }
                if (notaFormularios.get(1) != null && !notaFormularios.get(1).isEmpty()) {
                    notaForm = notaFormularios.get(1);
                    //Persisto la nota en MER
                    String descripcion2;
                    String numOtHija = ordenHhpp.getOnyxOtHija() != null 
                                ? ordenHhpp.getOnyxOtHija().toString():"NA";
                    
                    if (tipoTrabajo != null) {
                        descripcion2 = tipoTrabajo.getNombreBasica()+ 
                                "-" +numOtHija + "-" + ordenHhpp.getOtHhppId() + " \n";
                    } else {
                        descripcion2 = "Sin Tipo Trabajo" + 
                                "-" +numOtHija + "-" + ordenHhpp.getOtHhppId() + " \n";
                    }
                    CmtNotasOtHhppMgl notaOtHhppMgl2 = new CmtNotasOtHhppMgl();
                    notaOtHhppMgl2.setDescripcion(descripcion2);
                    notaOtHhppMgl2.setTipoNotaObj(notaAgenda);
                    notaOtHhppMgl2.setNota(notaForm);
                    notaOtHhppMgl2.setOtHhppId(ordenHhpp);
                    CmtNotasOtHhppMglManager notaOtHhppMglManager1 = new CmtNotasOtHhppMglManager();
                    notaOtHhppMgl2 = notaOtHhppMglManager1.create(notaOtHhppMgl2, "OFSC", 1);
                    if (notaOtHhppMgl2 != null && notaOtHhppMgl2.getNotasId() != null) {
                        LOGGER.info("Nota creada satisfatoriamente");
                    }
                }
            }
            CmtNotasOtHhppMglManager notasOtHhppMglManager = new CmtNotasOtHhppMglManager();
            List<CmtNotasOtHhppMgl> lstNotasOtHhppMgls
                    = notasOtHhppMglManager.findNotasByOtHhppId(ordenHhpp.getOtHhppId());
            if (lstNotasOtHhppMgls != null
                    && !lstNotasOtHhppMgls.isEmpty()) {
                String notas = "";
                for (CmtNotasOtHhppMgl notaOtHhppMgl : lstNotasOtHhppMgls) {

                    notas = notas + "Descripcion : " + notaOtHhppMgl.getDescripcion() + " \n"
                            + "Tipo de nota:" + notaOtHhppMgl.getTipoNotaObj().getNombreBasica() + " \n"
                            + "Nota: " + notaOtHhppMgl.getNota() + " \n";
                }
                nota = nota + notas;
            }
        }
        return nota;
    }
    
    /**
     * Metodo para consultar en Osfc la informacion de los formularios
     * utilizados durante el agendamiento.
     *
     * @Author Victor Bocanegra
     * @param agendaHhpp
     * @param agendaCcmm
     * @return List<String>
     *
     */
    public List<String> consultarFormulariosOfsc(MglAgendaDireccion agendaHhpp,
            CmtAgendamientoMgl agendaCcmm) {

        List<String> notas = null;

        try {

            userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                    .iterator().next().getParValor();
            String comAutorization = "Basic" + " " + userPwdAutorization;

            URL url = null;

            if (agendaCcmm != null) {
                url = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.ACTIVITIES + agendaCcmm.getWorkForceId());
            } else if (agendaHhpp != null) {
                url = new URL(BASE_URI_RES_ETA + CmtAgendamientoMglConstantes.ACTIVITIES + agendaHhpp.getOfpsId());
            }

            ConsumoGenerico consumoGenerico = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url,
                    comAutorization, null, null, null);

            notas = consumoGenerico.consultaFormularios();

        } catch (ApplicationException | IOException ex) {
            LOGGER.error("Error al momento de consultar la tecnología. EX000: " + ex.getMessage(), ex);
        }
        return notas;
    }
    
        /**
     * metodo para realizar un agendamiento inmediato con la informacion del
     * Iframe Autor: victor bocanegra
     *
     * @param requestAgendaInmediata
     * @return ResponseAgendaInmediata
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public ResponseAgendaInmediata WSManageSchedulesAppointmentsMgl(RequestAgendaInmediata requestAgendaInmediata) throws ApplicationException {

        ResponseAgendaInmediata response = new ResponseAgendaInmediata();

        try {

            if (requestAgendaInmediata.getWorkOrder() == null
                    || requestAgendaInmediata.getWorkOrder() == 0) {
                Data data = new Data();
                data.setMessage("Bad Request(Numero de orden es obligatorio)");
                response.setData(data);
                response.setCode("400");
            } else if (requestAgendaInmediata.getType() == null
                    || requestAgendaInmediata.getType().isEmpty()) {
                Data data = new Data();
                data.setMessage("Bad Request(Tipo es obligatorio)");
                response.setData(data);
                response.setCode("400");
            } else if (requestAgendaInmediata.getTechncianId() == null
                    || requestAgendaInmediata.getTechncianId().isEmpty()) {
                Data data = new Data();
                data.setMessage("Bad Request(identificacion del tecnico es obligatorio)");
                response.setData(data);
                response.setCode("400");
            } else if (requestAgendaInmediata.getStart() == null
                    || requestAgendaInmediata.getStart().isEmpty()) {
                Data data = new Data();
                data.setMessage("Bad Request(Hora inicio es obligatorio)");
                response.setData(data);
                response.setCode("400");
            } else if (requestAgendaInmediata.getEnd() == null
                    || requestAgendaInmediata.getEnd().isEmpty()) {
                Data data = new Data();
                data.setMessage("Bad Request(Hora Fin es obligatorio)");
                response.setData(data);
                response.setCode("400");
            } else if (requestAgendaInmediata.getWorkOrder().toString().length()
                    != 15) {
                Data data = new Data();
                data.setMessage("Bad Request(El numero de orden ingresado "
                        + "no tiene la longitud establecida de 15 caracteres)");
                response.setData(data);
                response.setCode("400");
            } else if (!requestAgendaInmediata.getStart().
                    matches("(?:[01]\\d|2[0123]):(?:[012345]\\d):(?:[012345]\\d)")) {
                Data data = new Data();
                data.setMessage("Bad Request"
                        + "(Formato de hora "+requestAgendaInmediata.getStart()+" "
                                + "invalido requerido: 'HH:MM:SS')");
                response.setData(data);
                response.setCode("400");
            }else if (!requestAgendaInmediata.getEnd()
                    .matches("(?:[01]\\d|2[0123]):(?:[012345]\\d):(?:[012345]\\d)")) {
                Data data = new Data();
                data.setMessage("Bad Request"
                        + "(Formato de hora "+requestAgendaInmediata.getEnd()+" "
                                + "invalido requerido: 'HH:MM:SS')");
                response.setData(data);
                response.setCode("400");
            }else {
                //Determino si el numero orden es CCMM o Direcciones
                String controlCcmmDir = requestAgendaInmediata.getWorkOrder().toString().substring(0, 1);

                if (controlCcmmDir.equalsIgnoreCase(ConstansClient.CONTROL_NUM_CCMM)) {//Es orden de CCMM
                    //Busco que no haya una agenda con el numero ingresado
                    CmtAgendamientoMglManager agendamientoMglManager = new CmtAgendamientoMglManager();
                    OnyxOtCmDirManager onyxOtCmDirManager = new OnyxOtCmDirManager();
                    CmtAgendamientoMgl agendaCon = agendamientoMglManager.buscarAgendaPorOtIdMgl(requestAgendaInmediata.getWorkOrder().toString());

                    if (agendaCon != null && agendaCon.getId() != null) {
                        Data data = new Data();
                        data.setMessage("Internal Server Error(Ya existe una agenda con el numero : " + requestAgendaInmediata.getWorkOrder() + "");
                        response.setData(data);
                        response.setCode("500");
                    } else {
                        //Guardo el registro
                        RequestAgendaInmediataMgl requestAgendaInmediataMgl = new RequestAgendaInmediataMgl();

                        String numeroSinsecuencia = requestAgendaInmediata.getWorkOrder().toString().
                                substring(0, requestAgendaInmediata.getWorkOrder().toString().length() - 3);

                        String numeroSinElPrimero = numeroSinsecuencia.substring(1, numeroSinsecuencia.length());
                        String numeroOt = eliminaCaracterIzqDer(numeroSinElPrimero, '0');

                        //Busco el numero de la ot
                        CmtOrdenTrabajoMgl ordenTrabajoMgl = ordenTrabajoMglManager.findOtById(new BigDecimal(numeroOt));                                       

                        if (ordenTrabajoMgl != null && ordenTrabajoMgl.getIdOt() != null) {
                            
                            List<OnyxOtCmDir> listaOnyx = onyxOtCmDirManager.findOnyxOtCmById(ordenTrabajoMgl.getIdOt());
                            if (listaOnyx != null && !listaOnyx.isEmpty()) {
                                ordenTrabajoMgl.setOnyxOtHija(new BigDecimal(listaOnyx.get(0).getOnyx_Ot_Hija_Cm()));
                            }
                            requestAgendaInmediataMgl.setNumeroOrden(requestAgendaInmediata.getWorkOrder().toString());
                            requestAgendaInmediataMgl.setTypeOrden(requestAgendaInmediata.getType());
                            requestAgendaInmediataMgl.setIdTecnico(requestAgendaInmediata.getTechncianId());
                            requestAgendaInmediataMgl.setHoraInicio(requestAgendaInmediata.getStart());
                            requestAgendaInmediataMgl.setHoraFin(requestAgendaInmediata.getEnd());
                            requestAgendaInmediataMgl.setUserId("AGEINMEDIATA");
                            requestAgendaInmediataMgl.setOrdenTrabajoMgl(ordenTrabajoMgl);

                            requestAgendaInmediataMgl.setEstado("0");
                            requestAgendaInmediataMgl.setFechaCreacion(new Date());

                            RequestAgendaInmediataMglManager managerReq = new RequestAgendaInmediataMglManager();
                            requestAgendaInmediataMgl = managerReq.create(requestAgendaInmediataMgl);
                            if (requestAgendaInmediataMgl.getRequestAgeInmId() != null) {

                                AgendamientoRunAble agendamientoRunAble
                                        = new AgendamientoRunAble(requestAgendaInmediataMgl, 1);

                                agendamientoRunAble.crearAgenda();

                                Data data = new Data();
                                data.setWorkOrder(requestAgendaInmediataMgl.getNumeroOrden());
                                data.setType(requestAgendaInmediataMgl.getTypeOrden());
                                data.setStart(requestAgendaInmediataMgl.getHoraInicio());
                                data.setEnd(requestAgendaInmediataMgl.getHoraFin());
                                data.setMessage("Orden Agendada Correctamente");
                                response.setData(data);
                                response.setCode("200");
                            } else {
                                Data data = new Data();
                                data.setMessage("Internal Server Error(Ocurrio un error creando el registro)");
                                response.setData(data);
                                response.setCode("500");
                            }
                        } else {
                            Data data = new Data();
                            data.setMessage("Internal Server Error(No existe orden de trabajo en MER con el numero: " + numeroOt + "");
                            response.setData(data);
                            response.setCode("500");
                        }
                    }
                    //Fin  Busqueda  agenda con el numero ingresado
                } else if (controlCcmmDir.equalsIgnoreCase(ConstansClient.CONTROL_NUM_DIR)) {
                    //Es orden de DIRECCIONES 

                    //Busco que no haya una agenda con el numero ingresado
                    AgendamientoHhppMglManager agendamientoMglManager = new AgendamientoHhppMglManager();
                    OnyxOtCmDirManager onyxOtCmDirManager = new OnyxOtCmDirManager();
                    MglAgendaDireccion agendaCon = agendamientoMglManager.buscarAgendaPorOtIdMgl(requestAgendaInmediata.getWorkOrder().toString());

                    if (agendaCon != null && agendaCon.getAgendaId() != null) {
                        Data data = new Data();
                        data.setMessage("Internal Server Error(Ya existe una agenda con el numero : " + requestAgendaInmediata.getWorkOrder() + "");
                        response.setData(data);
                        response.setCode("500");
                    } else {
                        //Guardo el registro
                        RequestAgendaInmediataMgl requestAgendaInmediataMgl = new RequestAgendaInmediataMgl();

                        String numeroSinsecuencia = requestAgendaInmediata.getWorkOrder().toString().
                                substring(0, requestAgendaInmediata.getWorkOrder().toString().length() - 3);

                        String numeroSinElPrimero = numeroSinsecuencia.substring(1, numeroSinsecuencia.length());
                        String numeroOt = eliminaCaracterIzqDer(numeroSinElPrimero, '0');

                        //Busco el numero de la ot
                        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
                        OtHhppMgl ordenDirecciones = otHhppMglManager.findOtByIdOt(new BigDecimal(numeroOt));
                          

                        if (ordenDirecciones != null && ordenDirecciones.getOtHhppId() != null) {                  
                            
                            List<OnyxOtCmDir> listaOnyx = onyxOtCmDirManager.findOnyxOtHHppById(ordenDirecciones.getOtHhppId());
                             if (listaOnyx != null && !listaOnyx.isEmpty()) {
                                ordenDirecciones.setOnyxOtHija(new BigDecimal(listaOnyx.get(0).getOnyx_Ot_Hija_Dir()));
                            }
                            
                            requestAgendaInmediataMgl.setNumeroOrden(requestAgendaInmediata.getWorkOrder().toString());
                            requestAgendaInmediataMgl.setTypeOrden(requestAgendaInmediata.getType());
                            requestAgendaInmediataMgl.setIdTecnico(requestAgendaInmediata.getTechncianId());
                            requestAgendaInmediataMgl.setHoraInicio(requestAgendaInmediata.getStart());
                            requestAgendaInmediataMgl.setHoraFin(requestAgendaInmediata.getEnd());
                            requestAgendaInmediataMgl.setUserId("AGEINMEDIATA");
                            requestAgendaInmediataMgl.setOrdenDirHhppMgl(ordenDirecciones);

                            requestAgendaInmediataMgl.setEstado("0");
                            requestAgendaInmediataMgl.setFechaCreacion(new Date());

                            RequestAgendaInmediataMglManager managerReq = new RequestAgendaInmediataMglManager();
                            requestAgendaInmediataMgl = managerReq.create(requestAgendaInmediataMgl);
                            if (requestAgendaInmediataMgl.getRequestAgeInmId() != null) {

                                AgendamientoRunAble agendamientoRunAble
                                        = new AgendamientoRunAble(requestAgendaInmediataMgl, 2);

                                agendamientoRunAble.crearAgenda();

                                Data data = new Data();
                                data.setWorkOrder(requestAgendaInmediataMgl.getNumeroOrden());
                                data.setType(requestAgendaInmediataMgl.getTypeOrden());
                                data.setStart(requestAgendaInmediataMgl.getHoraInicio());
                                data.setEnd(requestAgendaInmediataMgl.getHoraFin());
                                data.setMessage("Orden Agendada Correctamente");
                                response.setData(data);
                                response.setCode("200");
                            } else {
                                Data data = new Data();
                                data.setMessage("Internal Server Error(Ocurrio un error creando el registro)");
                                response.setData(data);
                                response.setCode("500");
                            }
                        } else {
                            Data data = new Data();
                            data.setMessage("Internal Server Error(No existe orden de trabajo en MER con el numero: " + numeroOt + "");
                            response.setData(data);
                            response.setCode("500");
                        }
                    }

                } else {
                    Data data = new Data();
                    data.setMessage("Not Found(Numero: " + requestAgendaInmediata.getWorkOrder() + ""
                            + " No es una orden de MGL)");
                    response.setData(data);
                    response.setCode("404");
                }

            }

        } catch (ApplicationException ex) {
            Data data = new Data();
            String msg;
            if (ex.getMessage().contains("$")) {
                partesMensaje = ex.getMessage().split("\\$");
                msg = partesMensaje[0];
            } else {
                msg = ex.getMessage();
            }
            data.setMessage("Internal Server Error(" + msg + ")");
            response.setData(data);
            response.setCode("500");
        }
        return response;
    }

    public String eliminaCaracterIzqDer(String cad, char cadEliminar) {
        String[] acad = cad.split("");
        int posL = 0, posR = acad.length;
        for (int i = 0; i < acad.length; i++) {
            if (!acad[i].equals(Character.toString(cadEliminar))) {
                posL = i;
                break;
            }
        }

        return cad.substring(posL, posR);
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
    public CmtAgendamientoMgl updateAgendasForNotas(
            CmtAgendamientoMgl agendaMgl, String usuario, int perfil)
            throws ApplicationException {
        try {

            List<TypePropertyElementUp> lstPropElements = new ArrayList<>();
            MgwTypeActualizarRequestElement request = new MgwTypeActualizarRequestElement();
            String formattedDate = sdf.format(new Date());
            List<CmtNotaOtMgl> notasOt;
            MgwHeadElement head = new MgwHeadElement(formattedDate,
                    agendaMgl.getOrdenTrabajo().getIdOt().toString());
            request.setHead(head);
            TypeDataElementUp data = new TypeDataElementUp();
            TypeAppointmentElementUp appoiment = new TypeAppointmentElementUp();

            appoiment.setAppt_number(agendaMgl.getOfpsOtId());
            cm = agendaMgl.getOrdenTrabajo().getCmObj();
            String numeroArmadoCmOFSC = armarNumeroCmOfsc(cm.getCuentaMatrizId());
            appoiment.setCustomer_number(agendaMgl.getOrdenTrabajo().getCmObj().getNumeroCuenta().toString());

            TypeCommandElementUp command = new TypeCommandElementUp();

            command.setAppointment(appoiment);
            command.setDate(ValidacionUtil.dateFormatyyyyMMdd(agendaMgl.getFechaAgenda()));

            TypeCommandsArrayUp commands = new TypeCommandsArrayUp();
            commands.add(command);
            data.setCommands(commands);
            request.setData(data);

            String actTec = "NOTAS ACTUALIZACION:";
            notasOt = agendaMgl.getOrdenTrabajo().getNotasList();
            String obs = "";

            if (notasOt != null) {
                for (CmtNotaOtMgl notas : notasOt) {
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

            if (numeroArmadoCmOFSC != null && !numeroArmadoCmOFSC.isEmpty()) {
                TypePropertyElementUp numeroCuentaMatrizMER = new TypePropertyElementUp("XA_CuentaMatrizMER", numeroArmadoCmOFSC);
                lstPropElements.add(numeroCuentaMatrizMER);
            }

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
    public CmtAgendamientoMgl updateAgendasForContacto(
            CmtAgendamientoMgl agendaMgl, String usuario, int perfil)
            throws ApplicationException {
        try {

            List<TypePropertyElementUp> lstPropElements = new ArrayList<>();
            MgwTypeActualizarRequestElement request = new MgwTypeActualizarRequestElement();
            String formattedDate = sdf.format(new Date());
            List<CmtNotaOtMgl> notasOt;
            MgwHeadElement head = new MgwHeadElement(formattedDate,
                    agendaMgl.getOrdenTrabajo().getIdOt().toString());
            request.setHead(head);
            TypeDataElementUp data = new TypeDataElementUp();
            TypeAppointmentElementUp appoiment = new TypeAppointmentElementUp();
            CmtOrdenTrabajoMgl ot = null;
            
            if(agendaMgl.getOrdenTrabajo() != null){
              ot = ordenTrabajoMglManager.findOtById(agendaMgl.getOrdenTrabajo().getIdOt());    
            }

            appoiment.setAppt_number(agendaMgl.getOfpsOtId());
            cm = agendaMgl.getOrdenTrabajo().getCmObj();
            String numeroArmadoCmOFSC = armarNumeroCmOfsc(cm.getCuentaMatrizId());
            appoiment.setCustomer_number(agendaMgl.getOrdenTrabajo().getCmObj().getNumeroCuenta().toString());

            TypeCommandElementUp command = new TypeCommandElementUp();

            command.setAppointment(appoiment);
            command.setDate(ValidacionUtil.dateFormatyyyyMMdd(agendaMgl.getFechaAgenda()));

            TypeCommandsArrayUp commands = new TypeCommandsArrayUp();
            commands.add(command);
            data.setCommands(commands);
            request.setData(data);

            String actTec = "NOTAS ACTUALIZACION:";
            notasOt = agendaMgl.getOrdenTrabajo().getNotasList();
            String obs = "";

            if (notasOt != null) {
                for (CmtNotaOtMgl notas : notasOt) {
                    obs = obs + " "+"TIPO NOTA:" +notas.getTipoNotaObj().getNombreBasica()+" " 
                            +"Nota: (" + notas.getNota()+")";
                }
            }
            TypePropertyElementUp notCm = new TypePropertyElementUp("activity_notes", actTec + "|" + obs);
            lstPropElements.add(notCm);
            
            String nombreCon = ot != null ? ot.getPersonaRecVt() : "NA";
            String tel1Con = ot != null ? ot.getTelPerRecVt() : "NA";
            
            TypePropertyElementUp contacto = new TypePropertyElementUp("XA_Contacto", nombreCon);
            lstPropElements.add(contacto);

            TypePropertyElementUp telUnoCon = new TypePropertyElementUp("XA_Telefonouno", tel1Con);
            lstPropElements.add(telUnoCon);

            String origenOrden = parametrosMglManager.findByAcronimo(
                    Constant.PROPIEDAD_ORIGEN_ORDEN)
                    .iterator().next().getParValor();

            TypePropertyElementUp orgOrden = new TypePropertyElementUp("XA_OrigenOrden", origenOrden);
            lstPropElements.add(orgOrden);

            if (numeroArmadoCmOFSC != null && !numeroArmadoCmOFSC.isEmpty()) {
                TypePropertyElementUp numeroCuentaMatrizMER = new TypePropertyElementUp("XA_CuentaMatrizMER", numeroArmadoCmOFSC);
                lstPropElements.add(numeroCuentaMatrizMER);
            }

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
    
    public void readInfoCtech(BigDecimal cuentaMatriz) {
        try {

            CmtFiltroProyectosDto filtro = new CmtFiltroProyectosDto();
            filtro.setOpcion("C");
            filtro.setCcmm(cuentaMatriz.intValue());
            filtro.setCentroPoblado(null);
            filtro.setDaneCp(null);
            filtro.setDaneMunicipio(null);

            filtro.setIdSitio(null);
            filtro.setSitio(null);
            filtro.setTipoSitio(null);
            filtro.setUbicacionTecnica(null);
            filtro.setUsuarioEdicion(null);

            filtro.setDisponibilidad(null);
            filtro.setUsuarioCreacion(null);

            filtro.setEstadoRegistro(1);

            infoCuentaMatrizUbiDto = new CmtTecSiteSapRespDto();
            infoCuentaMatrizUbiDto = cmtProyectosMglManager.crudProyectosCm(filtro);
            if (infoCuentaMatrizUbiDto != null
                    && infoCuentaMatrizUbiDto.getListaReadCtechDto() != null
                    && infoCuentaMatrizUbiDto.getListaReadCtechDto().get(0).getSitesapId() != null) {
                listInfoCtech = infoCuentaMatrizUbiDto.getListaReadCtechDto().get(0);
                sitio = listInfoCtech.getSitio();
                idSitio = listInfoCtech.getIdSitio();
                codDane = listInfoCtech.getDaneCp();
                ubicaTecnica = listInfoCtech.getUbicacionTecnica();
                
            } else {
                sitio = "";
                ubicaTecnica = "";
                idSitio = "";
                codDane = "";
            }

        } catch (ApplicationException e) {
            LOGGER.error("No se encontraron resultados para la consulta:.." , e);
        } catch (Exception e) {
            LOGGER.error("Se genera error en InfoTecnicaBean class ..." , e);
        }

    }

    public CmtTecSiteSapRespDto getInfoCuentaMatrizUbiDto() {
        return infoCuentaMatrizUbiDto;
    }

    public void setInfoCuentaMatrizUbiDto(CmtTecSiteSapRespDto infoCuentaMatrizUbiDto) {
        this.infoCuentaMatrizUbiDto = infoCuentaMatrizUbiDto;
    }

    public CruReadCtechDto getListInfoCtech() {
        return listInfoCtech;
    }

    public void setListInfoCtech(CruReadCtechDto listInfoCtech) {
        this.listInfoCtech = listInfoCtech;
    }

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    public String getUbicaTecnica() {
        return ubicaTecnica;
    }

    public void setUbicaTecnica(String ubicaTecnica) {
        this.ubicaTecnica = ubicaTecnica;
    }

    public String getIdSitio() {
        return idSitio;
    }

    public void setIdSitio(String idSitio) {
        this.idSitio = idSitio;
    }

    public String getCodDane() {
        return codDane;
    }

    public void setCodDane(String codDane) {
        this.codDane = codDane;
    }  
    
}
