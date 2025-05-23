package co.com.claro.mgl.mbeans.cm;


import co.com.claro.atencionInmediata.agenda.request.RequestAbrirIframe;
import co.com.claro.cmas400.ejb.respons.ResponseDataOtEdificios;
import co.com.claro.cmas400.ejb.respons.ResponseOtEdificiosList;
import co.com.claro.mer.utils.DateToolUtils;
import co.com.claro.mgl.client.https.ConsumoGenerico;
import co.com.claro.mgl.dtos.CapacidadAgendaDto;
import co.com.claro.mgl.dtos.CmtOnyxResponseDto;
import co.com.claro.mgl.dtos.DisponibilidadTecnicoDto;
import co.com.claro.mgl.dtos.FechaFranjaDto;
import co.com.claro.mgl.dtos.RestriccionAgendaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.OnyxOtCmDirlFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtAgendamientoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtArchivosVtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtEstadoxFlujoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtExtendidaTecnologiaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtExtendidaTipoTrabajoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtHorarioRestriccionMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOrdenTrabajoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTecnologiaSubMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.OnyxOtCmDir;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.RequestAgendaInmediataMgl;
import co.com.claro.mgl.jpa.entities.UbicacionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendamientoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtArchivosVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTipoTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.cm.ot.OtMglBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.CmtUtilidadesAgenda;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.claro.mgl.war.components.bussiness.MglScheduleComponetBusiness;
import co.com.claro.mgl.war.components.dtos.schedule.MglCapacityElement;
import co.com.claro.mgl.war.components.dtos.schedule.MglCapacityRestrictionElement;
import co.com.claro.mgl.war.components.dtos.schedule.MglScheduleComponetDto;
import co.com.claro.mgl.ws.utils.JSONUtils;
import co.com.claro.ofscCore.findMatchingResources.ApiFindTecnicosResponse;
import co.com.claro.ofscCore.findMatchingResources.Items;
import co.com.claro.ofscCore.findMatchingResources.Resource;
import co.com.telmex.catastro.util.FacesUtil;
import static co.com.telmex.catastro.util.FacesUtil.getShortRequestContextPath;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *
 * @author wgavidia
 */
@ViewScoped
@ManagedBean(name = "cmtAgendamientoMglBean")
public class CmtAgendamientoMglBean implements Serializable {
   
    
    private static final Logger LOGGER = LogManager.getLogger(CmtAgendamientoMglBean.class);
    private static final String FORMULARIO = "AGENDAMIENTOSOT";
    private static final String OPCION_REAGENDAR = "REAGENDAR";
    private static final String OPCION_EDITAR = "EDITAR";
    private static final String OPCION_CREAR = "CREAR";
    private static final String OPCION_EDITAR_INI = "EDITAR_INICIADA";
    private static final long serialVersionUID = 1L;
    private static final String OPCION_CREAR_ANTICIPADO = "CREAR_ANTICIPADO";   
    /**
     * N&uacute;mero m&aacute;ximo de caracteres permitido para la
     * construcci&oacute;n del n&uacute;mero de OT de RR.
     */
    private final int MAX_CARACTERES_NUMERO_OT_RR = 5;
    /**
     * N&uacute;mero m&aacute;ximo de caracteres permitido para la
     * construcci&oacute;n del estado de la OT.
     */
    private final int MAX_CARACTERES_ESTADO_OT = 6;
    
    private List<CmtAgendamientoMgl> agendas;
    private List<CmtAgendamientoMgl> agendasTotal;
    private CmtOrdenTrabajoMgl ot;
    private String usuarioVt;
    private Integer perfilVt;
    private List<CapacidadAgendaDto> capacidad = new ArrayList<>();
    private boolean visible;
    private List<RestriccionAgendaDto> restricciones;
    private OtMglBean otMglBean;
    private UsuariosServicesDTO usuarioSesion = new UsuariosServicesDTO();
    private CmtAgendamientoMgl agenda = new CmtAgendamientoMgl();
    private CmtAgendamientoMgl actualizaAgenda = new CmtAgendamientoMgl();
    private CmtAgendamientoMgl reasignaAgenda = new CmtAgendamientoMgl();
    private CmtAgendamientoMgl reAgendaMgl = new CmtAgendamientoMgl();
    private List<CmtAgendamientoMgl> cancelaAgendaMgl = new ArrayList<>();
    private List<CmtAgendamientoMgl> updateAgendaMgl = new ArrayList<>();
    private List<CmtBasicaMgl> prerequisitosVt = null;
    private boolean reagendar = false;
    private boolean crearAgenda = false;
    private boolean updateAgenda = false;
    private boolean cancelar = false;
    private boolean reasignar = false;
    private boolean consulta = true;
    private String razonReagendamiento;
    private String razonCancela;
    private String razonReasigna;
    private String comentariosOrden;
    private SecurityLogin securityLogin;
    private String mensajesValidacion;
    private List<String> nombresArchivos;
    private String[] idBasicaPrerequisito;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private boolean showFooterTable;
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private int actual;
    private MglScheduleComponetDto mglScheduleComponetDto;
    MglScheduleComponetBusiness mglScheduleComponetBusin =
            new MglScheduleComponetBusiness();
    private String mensajesValidacionFinal;
    private final FacesContext facesContext = FacesContext.getCurrentInstance();
    private final HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private String urlArchivoSoporte;
    private boolean activacionUCM;
    private boolean tecnicoAnticipado;
    private boolean agendaInmediata;
    private NodoMgl nodoMgl;
    private boolean verPanelTecnicos;
    private List<CapacidadAgendaDto> capacidadAgendaDtos;
    private List<Items> tecnicosAnticipados;
    private String tecnicoSelected;
    private boolean isRequest;
    private String json;
    private String[] partesMensaje;
    private boolean verCapacity = true;
    private List<Items> tecnicosAnticipadosReasignar;
    private List<String> fechasAgendas;
    private String fechaSelected;
    private List<String> lstFranjas;
    private String franjaSelected;
    private String horaInicio;
    private List<Items> tecnicosDisponibles;
    private int parWorktimeNum;
    private Items itemSelected;
    private final String EXPRESION_HORAS="^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
    private CmtAgendamientoMgl agendaIgualFechaHora;
    private boolean reagendarTecnicoAnt;
    private String horaSelected;
    private List<String> lstHoras;
    private String minSelected;
    private List<String> lstMin;
    private boolean conveniencia;
    private String convenienciaCheck;
    private boolean abrirPanelIframe;
    private boolean agendaInmediataPreIframe;
    private String urlIframe;

    
    @EJB
    private CmtAgendamientoMglFacadeLocal agendamientoFacade;
    @EJB
    private UsuarioServicesFacadeLocal usuarioServicesFacade;
    @EJB
    private CmtOpcionesRolMglFacadeLocal opcionesFacade;
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal tipoBasicaMglFacadeLocal;
    @EJB
    private CmtHorarioRestriccionMglFacadeLocal restriccionMglFacadeLocal;
    @EJB
    private CmtArchivosVtMglFacadeLocal cmtArchivosVtMglFacadeLocal;
    @EJB
    private  CmtEstadoxFlujoMglFacadeLocal cmtEstadoxFlujoMglFacadeLocal;
    @EJB
    private CmtOrdenTrabajoMglFacadeLocal ordenTrabajoMglFacadeLocal;
    @EJB
    private CmtEstadoxFlujoMglFacadeLocal estadoxFlujoMglFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    @EJB
    private CmtTecnologiaSubMglFacadeLocal tecnologiaSubMglFacadeLocal;
    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;
    @EJB
    private CmtExtendidaTipoTrabajoMglFacadeLocal extendidaTipoTrabajoMglFacadeLocal;
    @EJB
    private OnyxOtCmDirlFacadeLocal onyxOtCmDirlFacadeLocal;
    @EJB
    private CmtExtendidaTecnologiaMglFacadeLocal extendidaTecnologiaMglFacadeLocal;
   


    
    public CmtAgendamientoMglBean() {
    }

    @PostConstruct
    public void init()  {
        try {
            
            securityLogin = new SecurityLogin(facesContext);
            usuarioVt = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();
            this.otMglBean = JSFUtil.getSessionBean(OtMglBean.class);
            ot = otMglBean.getOrdenTrabajo();
            agendamientoFacade.setUser(usuarioVt, perfilVt);
            listInfoByPage(1);
            if (session != null && session.getAttribute("activaUCM") != null) {
                activacionUCM = (boolean) session.getAttribute("activaUCM");
                session.removeAttribute("activaUCM");
            }
            if (activacionUCM) {
                mostrarDocumentosAdjuntos(agendas);
                listInfoByPage(1);
            }
            usuarioSesion = usuarioServicesFacade.consultaInfoUserPorUsuario(usuarioVt);  
            String irCapacity = (String) session.getAttribute("capacity");
            session.removeAttribute("capacity");
            if (irCapacity != null) {
                consultarAgenda(null, false);
            }
            if (ot != null && ot.getIdOt() != null) {
                tecnicoAnticipado = validarTecnicoAnticipado(ot);
                agendaInmediata = validarAgendaInmediata(ot);
            }
            conveniencia = false;
        } catch (ApplicationException | IOException ex) {
            FacesUtil.mostrarMensajeError("Se generea error en CmtAgendamientoMglBean class ..." + ex.getMessage(), ex, LOGGER);
       } 
    }

    /**
     * Método para validar si el usuario tiene rol para crear Agenda Tradicional
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolCrearAgendaTradicional() {
        return isRol("CREARAGENDATRAD");
    }

    /**
     * Método para validar si el usuario tiene rol para crear Agenda Anticipada
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolCrearAgendaAnticipado() {
        return isRol("CREARAGENDAANT");
    }

    /**
     * Método para validar si el usuario tiene rol para crear Agenda inmediata
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolCrearAgendaInmediata() {
        return isRol("CREARAGENDAINMED");
    }

    /**
     * Método para validar si el usuario tiene rol para reagendar
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolReagendar() {
        return isRol(OPCION_REAGENDAR);
    }

    /**
     * Método para validar si el usuario tiene rol para actualizar agenda
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolActualizarAgenda() {
        return isRol("ACTUALIZARAGENDA");
    }

    /**
     * Método para validar si el usuario tiene rol para cancelar Agenda
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolCancelarAgenda() {
        return isRol("CANCELARAGENDA");
    }

    /**
     * Método para validar si el usuario tiene rol para reasignar agenda
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolReasignarAgenda() {
        return isRol("REASIGNARAGENDA");
    }

    /**
     * Método para validar si el usuario tiene rol para la opción de agendar visita indicada.
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @param nombreOpcion Nombre de la opción a validar
     * @author Gildardo Mora
     */
    private boolean isRol(String nombreOpcion) {
        try {
            return ValidacionUtil.validarVisualizacion("TABAGENDARVISITAOTCCMM",
                    nombreOpcion, opcionesFacade, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al verificar si tiene rol de permisos para la opción indicada. ", e);
        }

        return false;
    }

    public void cancelarAgenda(CmtAgendamientoMgl agenda) throws ApplicationException {
        if (!isRolCancelarAgenda()) {
            FacesUtil.mostrarMensajeWarn("No tiene permiso para cancelar la agenda..");
            return;
        }

        Date fechaHoy = new Date();

        //Obtiene el TiempoAgendaOfsc para implementar restricción
        CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = cmtEstadoxFlujoMglFacadeLocal.
        findByTipoFlujoAndEstadoInt(ot.getTipoOtObj().getTipoFlujoOt(), ot.getEstadoInternoObj(), ot.getBasicaIdTecnologia());
        int tiempoAgendaMin = cmtEstadoxFlujoMgl.getTiempoAgendaOfsc();
        boolean tiempoAntes;
        
        if (agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                .equalsIgnoreCase(Constant.ESTADO_AGENDA_CANCELADA)) {
            mensajesValidacion = "La agenda ya se encuentra cancelada.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    mensajesValidacion, null));
            return;

        } else if (agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                .equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA)) {
            mensajesValidacion = "La agenda se encuentra cerrada y no se puede cancelar.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    mensajesValidacion, null));
            return;

        } else if (agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                .equalsIgnoreCase(Constant.ESTADO_AGENDA_NODONE)) {
            mensajesValidacion = "La agenda se encuentra nodone(cerrada) y no se puede cancelar.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    mensajesValidacion, null));
            return;

        } else if (agenda.getFechaInivioVt() != null) {
            mensajesValidacion = "La agenda ya inicio visita y no se puede cancelar.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    mensajesValidacion, null));
            return;
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");            
            String fechaAgenda = format.format(agenda.getFechaAgenda());
            
            //Si la agenda tiene una fecha de reagenda toma esa fecha para hacer validacion de cancelar
            if(agenda.getFechaReagenda() != null){
               fechaAgenda = format.format(agenda.getFechaReagenda());
            }           
            
            String fechaActual = format.format(fechaHoy);
            Date fechaDate1;
            Date fechaDate2;
            boolean valida;
            boolean momentoActual = false;
            try {
                fechaDate1 = format.parse(fechaAgenda);
                fechaDate2 = format.parse(fechaActual);
                if (fechaDate1.before(fechaDate2)) {
                    LOGGER.info("La Fecha de la agenda  es menor a la de hoy ");
                    valida=false;
                } else if (fechaDate2.before(fechaDate1)) {
                    LOGGER.info("La Fecha de la agenda es Mayor a la de hoy ");
                    valida=true;
                } else {
                    LOGGER.info("Las Fechas Son iguales ");
                    valida=true;
                    momentoActual=true;
                }
                if (valida) {
                    //Valido agendas posteriores
                   if(validarAgendasPosteriores(agenda)){
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            mensajesValidacion, null));
                    return; 
                   }else if (momentoActual){
                       //Validación para implementar restriccion 
                        if (agenda.getTimeSlot().matches("[0-9]?[0-9]-[0-9]?[0-9]")) {
            
                        String[] timeSlot = agenda.getTimeSlot().split("-");
                        Calendar horaActual = Calendar.getInstance();
                        //Validación cuando la agenda este en curso
                        if (Integer.parseInt(timeSlot[0]) <= horaActual.get(Calendar.HOUR_OF_DAY) && 
                                horaActual.get(Calendar.HOUR_OF_DAY) <= Integer.parseInt(timeSlot[1])){
                            mensajesValidacion = "No se cancela debido a que la franja ya inicio.";
                            FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        mensajesValidacion, null));
                            cancelar = false;
                        return;
                        }
                        //Validación de los 30 minutos antes o lo parametrizado
                        if (tiempoAgendaMin > 0) {
                            LocalDateTime fechaActualD = LocalDateTime.now(ZoneId.of("America/Bogota"));
                            int horaInicioFranja = Integer.parseInt(timeSlot[0]);
                            if ((fechaActualD.getHour() + 1) == horaInicioFranja) {
                                //Obtiene la fecha de capacidad de agenda transformada al tipo objeto requerido.
                                LocalDateTime fechayHoraAgendaHab = DateToolUtils.convertDateToLocalDateTime(agenda.getFechaAgenda());
                                LocalTime horaFranja = LocalTime.of(horaInicioFranja, 0, 0);
                                //Se obtiene la fecha de agenda, modificando su hora y minutos
                                LocalDateTime fechayHoraAgendaMod = fechayHoraAgendaHab.with(horaFranja);
                                //Se resta a la hora de la agenda habilitada, los minutos definidos en el parámetro.
                                fechayHoraAgendaMod = fechayHoraAgendaMod.minusMinutes(tiempoAgendaMin);
                                //se valida si la fecha y hora actual es mayor a la fecha de agenda modificada
                                // si se cumple la condición anterior, asigna valor true, indicando que bloquea esos cupos.
                                tiempoAntes = fechaActualD.isAfter(fechayHoraAgendaMod);
                                if(tiempoAntes){
                                    mensajesValidacion = "No se cancela debido a que la franja esta previa a iniciar.";
                                    FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                mensajesValidacion, null));
                                    cancelar = false;
                                return;
                                }
                            }
                        }                
                        }
                    }
                } else {
                    mensajesValidacion = "No se puede cancelar una agenda con fecha menor a la  del dia de hoy.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            mensajesValidacion, null));
                    return;
                }
            } catch (ParseException e) {
                 FacesUtil.mostrarMensajeError("Se genera error  cancelando la agenda CmtAgendamientoMglBean: cancelarAgenda() ..." + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Se genera error  cancelando la agenda CmtAgendamientoMglBean: cancelarAgenda() ..."  + e.getMessage(), e, LOGGER);
            } 
        }
        cancelar = true;
        reasignar =false;
        crearAgenda = false;
        reagendar = false;
        reagendarTecnicoAnt = false;
        consulta = false;
        updateAgenda = false;
        razonCancela = "";
        razonReasigna ="";
        comentariosOrden = "";
        this.cancelaAgendaMgl = new ArrayList<>();
        this.cancelaAgendaMgl.add(agenda);


    }

    public void updateAgendamiento(CmtAgendamientoMgl agenda) {
        if (!isRolActualizarAgenda()) {
            FacesUtil.mostrarMensajeWarn("No tiene permiso para actualizar la agenda..");
            return;
        }

        try {
            
            Date fechaHoy = new Date();
            
            if (agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                    .equalsIgnoreCase(Constant.ESTADO_AGENDA_CANCELADA)) {
                mensajesValidacion = "La agenda se encuentra cancelada y no se puede editar.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        mensajesValidacion, null));
                return;

            } else if (agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                    .equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA)) {
                mensajesValidacion = "La agenda se encuentra cerrada y no se puede editar.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        mensajesValidacion, null));
                return;
            } else if (agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                    .equalsIgnoreCase(Constant.ESTADO_AGENDA_NODONE)) {
                mensajesValidacion = "La agenda se encuentra nodone(cerrada) y no se puede editar.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        mensajesValidacion, null));
                return;
            } else if (agenda.getFechaInivioVt() != null) {
                if (!validarAccionAgendamiento(OPCION_EDITAR_INI)) {
                    mensajesValidacion = "La agenda ya inicio visita y no se puede editar, "
                            + "solo el rol especial la puede editar.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            mensajesValidacion, null));
                    return;
                }
            } else {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String fechaAgenda = format.format(agenda.getFechaAgenda());
                String fechaActual = format.format(fechaHoy);
                Date fechaDate1;
                Date fechaDate2;
                boolean valida;
                try {
                    fechaDate1 = format.parse(fechaAgenda);
                    fechaDate2 = format.parse(fechaActual);
                    if (fechaDate1.before(fechaDate2)) {
                        LOGGER.info("La Fecha de la agenda  es menor a la de hoy ");
                        valida = false;
                    } else if (fechaDate2.before(fechaDate1)) {
                        LOGGER.info("La Fecha de la agenda es Mayor a la de hoy ");
                        valida = true;
                    } else {
                        LOGGER.info("Las Fechas Son iguales ");
                        valida = true;
                    }
                    if (valida) {
                        LOGGER.info("Se puede actualizar la agenda.");
                    } else {
                        mensajesValidacion = "No se puede actualizar una agenda con fecha de agendamiento  "
                                + "menor al dia de hoy.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajesValidacion, null));
                        return;
                    }
                } catch (ParseException ex) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                    LOGGER.error(msg);
                }
            }
            updateAgenda = true;
            cancelar = false;
            reasignar = false;
            crearAgenda = false;
            reagendar = false;
            reagendarTecnicoAnt = false;
            consulta = false;
            razonCancela = "";
            razonReasigna = "";
            comentariosOrden = "";
            this.updateAgendaMgl = new ArrayList<>();
            this.updateAgendaMgl.add(agenda);
            actualizaAgenda = agenda;
            CmtTipoBasicaMgl tipoPrerequisitoVt = tipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_PREREQUISITOS_AGENDA);

            prerequisitosVt = basicaMglFacadeLocal.findByTipoBasica(tipoPrerequisitoVt);
            if (ot.getPrerequisitosVT() != null) {
                idBasicaPrerequisito = ot.getPrerequisitosVT().split("\\|");
            }
           
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error actualizando la Agenda  en CmtAgendamientoMglBean: updateAgenda()" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error actualizando la Agenda  en CmtAgendamientoMglBean: updateAgenda()" + e.getMessage(), e, LOGGER);
        }

    }
    
    public void reasignarAgenda(CmtAgendamientoMgl agenda) {
        if (!isRolReasignarAgenda()) {
            FacesUtil.mostrarMensajeWarn("No tiene permiso para reasignar la agenda..");
            return;
        }

        try {

            Date fechaHoy = new Date();
            horaSelected = "";
            minSelected = "";
            lstMin = new ArrayList<>();
            lstHoras = new ArrayList<>();
            if (agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                    .equalsIgnoreCase(Constant.ESTADO_AGENDA_CANCELADA)) {
                mensajesValidacion = "La agenda se encuentra cancelada y no se puede editar.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajesValidacion, null));
                return;

            } else if (agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                    .equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA)) {
                mensajesValidacion = "La agenda se encuentra cerrada y no se puede editar.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajesValidacion, null));
                return;
            } else if (agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                    .equalsIgnoreCase(Constant.ESTADO_AGENDA_NODONE)) {
                mensajesValidacion = "La agenda se encuentra nodone(cerrada) y no se puede editar.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajesValidacion, null));
                return;
            } else if (agenda.getFechaInivioVt() != null) {
                if (!validarAccionAgendamiento(OPCION_EDITAR_INI)) {
                    mensajesValidacion = "La agenda ya inicio visita y no se puede editar, "
                            + "solo el rol especial la puede editar.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return;
                }
            } else {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String fechaAgenda = format.format(agenda.getFechaAgenda());
                String fechaActual = format.format(fechaHoy);
                Date fechaDate1;
                Date fechaDate2;
                boolean valida;
                try {
                    fechaDate1 = format.parse(fechaAgenda);
                    fechaDate2 = format.parse(fechaActual);
                    if (fechaDate1.before(fechaDate2)) {
                        LOGGER.info("La Fecha de la agenda  es menor a la de hoy ");
                        valida = false;
                    } else if (fechaDate2.before(fechaDate1)) {
                        LOGGER.info("La Fecha de la agenda es Mayor a la de hoy ");
                        valida = true;
                    } else {
                        LOGGER.info("Las Fechas Son iguales ");
                        valida = true;
                    }
                    if (valida) {
                        LOGGER.info("Se puede actualizar la agenda.");
                    } else {
                        mensajesValidacion = "No se puede actualizar una agenda con fecha de agendamiento  "
                                + "menor al dia de hoy.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        mensajesValidacion, null));
                        return;
                    }
                } catch (ParseException ex) {
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                    LOGGER.error(msg);
                }
            }

            //Consulto el parametro WORKTIME
            String parWorktime = parametrosMglFacadeLocal.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.PAR_WORKTIME)
                    .iterator().next().getParValor();

            if (parWorktime != null && !parWorktime.isEmpty()) {
                if (isNumeric(parWorktime)) {
                    parWorktimeNum = Integer.parseInt(parWorktime);
                } else {
                    mensajesValidacion = "El valor: " + parWorktime + " no es numerico";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return;
                }
            } else {
                mensajesValidacion = "No esta configurado el parametro: "
                        + "" + co.com.telmex.catastro.services.util.Constant.PAR_WORKTIME
                        + " para la consulta de los tecnicos";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajesValidacion, null));
                return;
            }

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            List<String> fechas = new ArrayList<>();
            String fechAge = df.format(agenda.getFechaAgenda());
            fechas.add(fechAge);

            itemSelected = null;
            tecnicosDisponibles = null;
            tecnicoSelected = null;
            franjaSelected = null;
            lstFranjas = null;
            horaInicio = "";
            horaSelected = "";
            lstHoras = null;
            minSelected = "";
            lstMin = null;

            ApiFindTecnicosResponse response = agendamientoFacade.consultarTecnicosDisponibles(ot, agenda.getNodoMgl(), fechas);
            if (response != null && response.getDetail() != null
                    && response.getStatus() != null) {

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                response.getDetail(), null));

            } else if (response != null && response.getItems() != null
                    && !response.getItems().isEmpty()) {
                tecnicosDisponibles = new ArrayList<>();
                tecnicosAnticipados = response.getItems();
                retornaTecnicosDisponibles();
                reasignar = true;
                updateAgenda = false;
                cancelar = false;
                crearAgenda = false;
                reagendar = false;
                reagendarTecnicoAnt = false;
                consulta = false;
                razonCancela = "";
                razonReasigna = "";
                comentariosOrden = "";
                reasignaAgenda = agenda;
            } else {
                tecnicosDisponibles = null;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "El servicio de tecnicos anticipados no retorna informacion "
                                + "de tecnicos disponibles", null));
            }
        } catch (ApplicationException | IOException e) {
            FacesUtil.mostrarMensajeError("Se genera error reasignando la Agenda  en CmtAgendamientoMglBean: reasignarAgenda()" + e.getMessage(), e, LOGGER);
        }

    }

    public String cancelarAgendaEnOFSC(CmtAgendamientoMgl agenda) {

        try {
        
            if (razonCancela == null || razonCancela.isEmpty()) {
                mensajesValidacion = "La razon es obligatoria para cancelar.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajesValidacion, null));
                return null;
            }
            if (comentariosOrden == null || comentariosOrden.isEmpty()) {
                mensajesValidacion = "El comentario es obligatorio para cancelar.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajesValidacion, null));
                return null;
            }
            
            //Validamos disponibilidad del servicio
            ParametrosMgl wadlAgenda
                    = parametrosMglFacadeLocal.findParametroMgl(Constant.WADL_SERVICES_AGENDAMIENTOS);

            if (wadlAgenda == null) {
                throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WADL_SERVICES_AGENDAMIENTOS);
            }

            URL url = new URL(wadlAgenda.getParValor());

            ConsumoGenerico.conectionWsdlTest(url, Constant.WADL_SERVICES_AGENDAMIENTOS);
            //Fin Validacion disponibilidad del servicio

            isRequest = false;
            
            agendamientoFacade.cancelar(agenda, razonCancela,
                    comentariosOrden, usuarioVt, perfilVt);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha solicitado la cancelación  "
                    + "de la orden  " + agenda.getOfpsOtId() + " ", null));
            listInfoByPage(1);
            regresar();

        } catch (ApplicationException e) {
            String msg;
            if (e.getMessage().contains("$")) {
                partesMensaje = e.getMessage().split("\\$");
                msg = partesMensaje[0];
            } else {
                msg = e.getMessage();
            }

            if (partesMensaje != null && partesMensaje[1] != null && !partesMensaje[1].isEmpty()) {
                isRequest = true;
                json = partesMensaje[1];
                session.setAttribute("json", json);
            }
            FacesUtil.mostrarMensajeError("Ocurrió un error cancelando el agendamiento: " + msg, e, LOGGER);
        } catch (Exception e) {
            String msg;
            if (e.getMessage().contains("$")) {
                partesMensaje = e.getMessage().split("\\$");
                msg = partesMensaje[0];
            } else {
                msg = e.getMessage();
            }

            if (partesMensaje != null && partesMensaje[1] != null && !partesMensaje[1].isEmpty()) {
                isRequest = true;
                json = partesMensaje[1];
                session.setAttribute("json", json);
            }
            FacesUtil.mostrarMensajeError("Se generea error cancelando la Agenda  en CmtAgendamientoMglBean: cancelarAgendaEnOFSC()" + msg, e, LOGGER);
        }
       return "";
    }

    public void updateAgendamientoMgl(CmtAgendamientoMgl agenda) {

        try {
            isRequest= false;
            
            if (validarAccionAgendamiento(OPCION_EDITAR)) {
                
                if (ot.getPersonaRecVt().isEmpty() && ot.getTelPerRecVt() == null) {
                    mensajesValidacion = "El nombre y el telefono  de la persona que recibe la visita es obligatorio.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            mensajesValidacion, null));
                    return;
                }

                if (ot.getPersonaRecVt() == null  || ot.getPersonaRecVt().isEmpty()) {
                    mensajesValidacion = "El nombre de la persona que recibe la visita es obligatorio.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            mensajesValidacion, null));
                    return;
                }
                if (ot.getTelPerRecVt() == null) {
                    mensajesValidacion = "El telefono de la persona que recibe la visita es obligatorio.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            mensajesValidacion, null));
                    return;
                }
                if (ot.getEmailPerRecVT()== null  || ot.getEmailPerRecVT().isEmpty()) {
                    mensajesValidacion = "El Correo de la persona que recibe la visita es obligatorio.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            mensajesValidacion, null));
                    return;
                }
                
                //Validamos disponibilidad del servicio
                ParametrosMgl wadlAgenda
                        = parametrosMglFacadeLocal.findParametroMgl(Constant.WADL_SERVICES_AGENDAMIENTOS);

                if (wadlAgenda == null) {
                    throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WADL_SERVICES_AGENDAMIENTOS);
                }

                URL url = new URL(wadlAgenda.getParValor());

                ConsumoGenerico.conectionWsdlTest(url, Constant.WADL_SERVICES_AGENDAMIENTOS);
                //Fin Validacion disponibilidad del servicio
                
                if (agenda.getFechaInivioVt() != null) {
                    String prerequisitos = regresaCadenaTipoPrerequisito(idBasicaPrerequisito);

                    ot.setPrerequisitosVT(prerequisitos);

                    ordenTrabajoMglFacadeLocal.actualizarOtCcmm(ot, usuarioVt, perfilVt);

                    agendamientoFacade.updateAgendasForContacto(agenda, usuarioVt, perfilVt);

                    String msn = "Se ha modificado la agenda:  " + agenda.getOfpsOtId() + "  "
                            + "  para la ot: " + agenda.getOrdenTrabajo().getIdOt() + " ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    msn, ""));
                    listInfoByPage(1);
                    regresar();
                } else {

                    String prerequisitos = regresaCadenaTipoPrerequisito(idBasicaPrerequisito);

                    ot.setPrerequisitosVT(prerequisitos);

                    ordenTrabajoMglFacadeLocal.actualizarOtCcmm(ot, usuarioVt, perfilVt);

                    CmtOnyxResponseDto cmtOnyxResponseDto = null;
                    if (ot.getOnyxOtHija() != null) {
                        //Validamos disponibilidad del servicio
                        ParametrosMgl wsdlOtHija
                                = parametrosMglFacadeLocal.findParametroMgl(Constant.WSDL_OT_HIJA_ONIX);

                        if (wsdlOtHija == null) {
                            throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WSDL_OT_HIJA_ONIX);
                        }

                        URL urlCon = new URL(wsdlOtHija.getParValor());

                        ConsumoGenerico.conectionWsdlTest(urlCon, Constant.WSDL_OT_HIJA_ONIX);
                        //Fin Validacion disponibilidad del servicio
                        cmtOnyxResponseDto = ordenTrabajoMglFacadeLocal.consultarOTHijaOnyx(ot.getOnyxOtHija().toString());

                    }
                    agenda.setCmtOnyxResponseDto(cmtOnyxResponseDto);

                    String requestURL = FacesUtil.getFullRequestContextPath();

                    agendamientoFacade.update(agenda, usuarioVt, perfilVt, requestURL);

                    String msn = "Se ha modificado la agenda:  " + agenda.getOfpsOtId() + "  "
                            + "  para la ot: " + agenda.getOrdenTrabajo().getIdOt() + " ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    msn, ""));
                    listInfoByPage(1);
                    regresar();
                }
            } else {
                mensajesValidacion = "No tiene el rol especifico para esta accion.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajesValidacion, null));
            }
        } catch (ApplicationException e) {
            String msg;
            if (e.getMessage().contains("$")) {
                partesMensaje = e.getMessage().split("\\$");
                msg = partesMensaje[0];
            } else {
                msg = e.getMessage();
            }

            if (partesMensaje != null && partesMensaje[1] != null && !partesMensaje[1].isEmpty()) {
                isRequest = true;
                json = partesMensaje[1];
                session.setAttribute("json", json);
            }
            FacesUtil.mostrarMensajeError("Ocurrió un error al realizar la modificacion de la agenda: " + msg, e, LOGGER);
        } catch (IOException e) {
            String msg;
            if (e.getMessage().contains("$")) {
                partesMensaje = e.getMessage().split("\\$");
                msg = partesMensaje[0];
            } else {
                msg = e.getMessage();
            }

            if (partesMensaje != null && partesMensaje[1] != null && !partesMensaje[1].isEmpty()) {
                isRequest = true;
                json = partesMensaje[1];
                session.setAttribute("json", json);
            }
            FacesUtil.mostrarMensajeError("Se generea error actualizando la Agenda  en CmtAgendamientoMglBean: updateAgendaMgl()" + msg, e, LOGGER);
        }
    }
    
    public String updateAgendaForTecnicoMgl() {

        try {
            if (validarAccionAgendamiento(OPCION_EDITAR)) {
                if (razonReasigna == null || razonReasigna.isEmpty()) {
                    mensajesValidacion = "La razon es obligatoria para reasignar.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                } 
                if (tecnicoSelected == null || tecnicoSelected.isEmpty()) {
                    mensajesValidacion = "Tecnico Disponible es obligatorio.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                }
                if (franjaSelected == null || franjaSelected.isEmpty()) {
                    mensajesValidacion = "La Franja Disponible es obligatorio.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                }
                if (horaInicio == null || horaInicio.isEmpty()) {
                    mensajesValidacion = "La Hora Inicio es obligatorio.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                }
                if (!validarCedulaTecnico(tecnicoSelected)) {
                    mensajesValidacion = "La cedula del tecnico debe ser un valor diferente  a 0.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                }
                if (reasignaAgenda.getFechaInivioVt() != null) {
                    mensajesValidacion = "La agenda ya inicio visita y no se puede actualizar.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                }
                if (!horaInicio.matches(EXPRESION_HORAS)) {
                    mensajesValidacion = "La Hora Inicio no tiene el formato adecuado Ejp:(00:00).";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                } else {
                    if (!validaHoraInicio()) {
                        mensajesValidacion = "La hora seleccionada no corresponde con el horario disponible del técnico";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        mensajesValidacion, null));
                        return null;
                    } else {
                        if (tecnicoSelected.equalsIgnoreCase(reasignaAgenda.getIdentificacionTecnico())
                                && horaInicio.equalsIgnoreCase(reasignaAgenda.getHoraInicio())) {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "El tecnico no fue asociado a la agenda: tecnico igual y hora igual al de la agenda", null));
                            return null;
                        } else {
                            //Validamos disponibilidad del servicio
                            ParametrosMgl wadlAgenda
                                    = parametrosMglFacadeLocal.findParametroMgl(Constant.WADL_SERVICES_AGENDAMIENTOS);

                            if (wadlAgenda == null) {
                                throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WADL_SERVICES_AGENDAMIENTOS);
                            }

                            URL url = new URL(wadlAgenda.getParValor());

                            ConsumoGenerico.conectionWsdlTest(url, Constant.WADL_SERVICES_AGENDAMIENTOS);
                            //Fin Validacion disponibilidad del servicio

                            String requestURL = FacesUtil.getFullRequestContextPath();
                            reasignaAgenda.setComentarioReasigna(razonReasigna);
                            reasignaAgenda.setIdentificacionTecnico(tecnicoSelected);

                            String idAliadoFin = null;
                            if (tecnicosDisponibles != null && !tecnicosDisponibles.isEmpty()) {
                                for (Items item : tecnicosDisponibles) {
                                    if (item.getResource() != null
                                            && item.getResource().getResourceId().equalsIgnoreCase(tecnicoSelected)) {
                                        reasignaAgenda.setNombreTecnico(item.getResource().getName());

                                        if (item.getResource().getXR_IdAliado() != null
                                                && !item.getResource().getXR_IdAliado().isEmpty()
                                                && item.getResource().getXR_IdAliado().contains("-")) {
                                            String parts[] = item.getResource().getXR_IdAliado().split("-");
                                            if (parts != null && parts.length > 0) {
                                                idAliadoFin = parts[0];
                                            }
                                        } else if (item.getResource().getXR_IdAliado() != null && !item.getResource().getXR_IdAliado().isEmpty()) {
                                            idAliadoFin = item.getResource().getXR_IdAliado();
                                        }
                                        reasignaAgenda.setIdentificacionAliado(idAliadoFin);
                                    }
                                }
                            }
                            reasignaAgenda.setHoraInicio(horaInicio+":00");
                            agendamientoFacade.update(reasignaAgenda, usuarioVt, perfilVt, requestURL);
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String fechAge = df.format(reasignaAgenda.getFechaAgenda());

                            String msn = "Se ha modificado la agenda:  " + reasignaAgenda.getOfpsOtId() + "  "
                                    + "  para la ot: " + reasignaAgenda.getOrdenTrabajo().getIdOt()
                                    + " con el tecnico: " + reasignaAgenda.getNombreTecnico()
                                    + "[" + reasignaAgenda.getIdentificacionTecnico() + "]  "
                                    + "para el dia: " + fechAge + " en la hora:  " + reasignaAgenda.getHoraInicio() + "";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            msn, ""));
                            listInfoByPage(1);
                            regresar();

                        }
                    }
                }
            } else {
                mensajesValidacion = "No tiene el rol especifico para esta accion.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajesValidacion, null));
                return null;
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error al realizar la reasignacion de la agenda: " + e.getMessage(), e, LOGGER);
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error al realizar la reasignacion de la agenda: en CmtAgendamientoMglBean: updateAgendaForTecnicoMgl()" + e.getMessage(), e, LOGGER);
        }
        return "";
    }

    public void consultarAgenda(CmtAgendamientoMgl agenda, boolean desdeReagendar) throws ApplicationException {
        if (!isRolReagendar()) {
            FacesUtil.mostrarMensajeWarn("No tiene permiso para reagendar la agenda");
            return;
        }

        try {
            conveniencia = false;
            isRequest= false;
            String agendaTecnicoAnticipado= "";  
            if (agenda != null) {
                if (agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                        .equalsIgnoreCase(Constant.ESTADO_AGENDA_CANCELADA)) {
                    mensajesValidacion = "La agenda se encuentra cancelada y no se puede reagendar.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            mensajesValidacion, null));
                    return;

                } else if (agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                        .equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA)) {
                    mensajesValidacion = "La agenda se encuentra cerrada y no se puede reagendar.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            mensajesValidacion, null));
                    return;
                } else if (agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                        .equalsIgnoreCase(Constant.ESTADO_AGENDA_NODONE)) {
                    mensajesValidacion = "La agenda se encuentra en nodone(cerrada) y no se puede reagendar.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            mensajesValidacion, null));
                    return;
                } else if (agenda.getFechaInivioVt() != null) {
                    mensajesValidacion = "La agenda ya inicio visita y no se puede reagendar.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            mensajesValidacion, null));
                    return;
                } else if (validaFechaAgendamiento(agenda)) {
                    mensajesValidacion = "No es posible reagendar. Fecha de Agenda inferior a la Fecha Actual.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            mensajesValidacion, null));
                    return;
                }
                
                tecnicoAnticipado = validarTecnicoAnticipado(ot);
                if (agenda.getTecnicoAnticipado().equalsIgnoreCase("Y")) {
                    reAgendaMgl = agendamientoFacade.
                            buscarAgendaPorIdOtWorkforce(agenda.getWorkForceId());
                    reAgendaMgl.setTimeSlotAntes(agenda.getTimeSlot());
                    reagendarTecnicoAnt=true;
                    reagendar = false;
                    abrirPanelTecnicos();
                    consultarTecnicos();
                    crearAgenda = false;
                    cancelar = false;
                    reasignar = false;
                    consulta = false;
                    updateAgenda = false;
                    razonReagendamiento = "";
                    comentariosOrden = "";
                    horaSelected = "";
                    minSelected = "";
                    agendaTecnicoAnticipado = agenda.getTecnicoAnticipado();
                    lstMin = new ArrayList<>();
                    lstHoras = new ArrayList<>();
                } else {
                    reagendar = true;
                    reagendarTecnicoAnt = false;
                    verCapacity = true;
                    crearAgenda = false;
                    cancelar = false;
                    reasignar = false;
                    consulta = false;
                    updateAgenda = false;
                    razonReagendamiento = "";
                    comentariosOrden = "";
                    horaSelected = "";
                    minSelected = "";
                    lstMin = new ArrayList<>();
                    lstHoras = new ArrayList<>();
                    reAgendaMgl = agendamientoFacade.
                            buscarAgendaPorIdOtWorkforce(agenda.getWorkForceId());
                }
            } else {
                crearAgenda = true;
                tecnicoAnticipado = validarTecnicoAnticipado(ot);
                agendaInmediata = validarAgendaInmediata(ot);
                reagendar = false;
                reagendarTecnicoAnt = false;
                cancelar = false;
                consulta = false;
                updateAgenda = false;
                verCapacity = true;
                horaSelected = "";
                minSelected = "";
                lstMin = new ArrayList<>();
                lstHoras = new ArrayList<>();
                CmtTipoBasicaMgl tipoPrerequisitoVt = tipoBasicaMglFacadeLocal.
                        findByCodigoInternoApp(Constant.TIPO_BASICA_PREREQUISITOS_AGENDA);

                prerequisitosVt = basicaMglFacadeLocal.findByTipoBasica(tipoPrerequisitoVt);
                if (ot.getPrerequisitosVT() != null) {
                    idBasicaPrerequisito = ot.getPrerequisitosVT().split("\\|");
                } else {
                    idBasicaPrerequisito = null;
                }
                agenda = new CmtAgendamientoMgl();
            }
            if (validarCreacion()) {                
                if (agenda.getHoraInicio() == null || desdeReagendar) {
                    //Validamos disponibilidad del servicio
                    ParametrosMgl wadlAgenda
                            = parametrosMglFacadeLocal.findParametroMgl(Constant.WADL_SERVICES_AGENDAMIENTOS);

                    if (wadlAgenda == null) {
                        throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WADL_SERVICES_AGENDAMIENTOS);
                    }

                    URL url = new URL(wadlAgenda.getParValor());

                    ConsumoGenerico.conectionWsdlTest(url, Constant.WADL_SERVICES_AGENDAMIENTOS);
                    //Fin Validacion disponibilidad del servicio
                    
                    this.capacidad = agendamientoFacade.getCapacidad(ot, agenda.getOfpsOtId(), nodoMgl);

                    CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = cmtEstadoxFlujoMglFacadeLocal.
                            findByTipoFlujoAndEstadoInt(ot.getTipoOtObj().getTipoFlujoOt(), ot.getEstadoInternoObj(), ot.getBasicaIdTecnologia());
                    int diasAmostrar = 0;
                    if (cmtEstadoxFlujoMgl.getEstadoxFlujoId() != null) {
                        if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                            diasAmostrar = cmtEstadoxFlujoMgl.getDiasAMostrarAgenda();
                        }

                    }
                    
                    if (!agendaTecnicoAnticipado.equalsIgnoreCase("Y")) {
                        if (capacidad.isEmpty()) {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "No hay capacidad disponible para el tipo de  "
                                            + "trabajo:  " + ot.getTipoOtObj().getBasicaIdTipoOt().
                                                    getNombreBasica() + "  y el subTipo: " + cmtEstadoxFlujoMgl.
                                                    getSubTipoOrdenOFSC().getNombreBasica() + "", null));

                            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form33646");

                            return;
                        }
                   }

                    List<MglCapacityElement> capacityElements = retornaCapacityComponente(capacidad, agenda, cmtEstadoxFlujoMgl);

                    if (diasAmostrar < 7) {
                        diasAmostrar = 7;
                    }
                    mglScheduleComponetDto = mglScheduleComponetBusin.getScheduleView(
                            capacityElements, diasAmostrar, new Date());
                   
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Ya se encuentra una última agenda cerrada no se pueden crear más agendas ", null));
                listInfoByPage(1);
                regresar();
            }
        } catch (ApplicationException | IOException e) {
            String msg;
            if (e.getMessage() != null && e.getMessage().contains("$")) {
                partesMensaje = e.getMessage().split("\\$");
                msg = partesMensaje[0];
            } else {
                msg = e.getMessage();
            }

            if (partesMensaje != null && partesMensaje[1] != null && !partesMensaje[1].isEmpty()) {
                isRequest = true;
                json = partesMensaje[1];
                session.setAttribute("json", json);
            }
            consulta = true;
            FacesUtil.mostrarMensajeError("Ocurrió un error consultando la capacidad: " + msg, e, LOGGER);
        }
    }

    public String verRestricciones(CapacidadAgendaDto capacidad) {
        if (capacidad != null) {
            this.restricciones = capacidad.getRestricciones();
        }
        return "$('#dialogAgenda').dialog();";
    }

    public void regresar() {
        consulta = true;
        reagendar = false;
        reagendarTecnicoAnt = false;
        cancelar = false;
        crearAgenda = false;
        updateAgenda = false;
        this.capacidad = null;
        agenda = new CmtAgendamientoMgl();
        verPanelTecnicos=false;
        reasignar = false;
        reAgendaMgl = new CmtAgendamientoMgl();
    }

    public void consultarDocumentos(CmtAgendamientoMgl agenda) {
        try {

            nombresArchivos = agendamientoFacade.
                    consultarDocumentos(agenda, usuarioVt, perfilVt);
        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Ocurrió un error al consultar los documentos adjuntos: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error consultando documentos en CmtAgendamientoMglBean: consultarDocumentos()" + e.getMessage(), e, LOGGER);
        }


    }

    public void mostrarDocumentosAdjuntos(List<CmtAgendamientoMgl> agendas) {
        try {

            if (agendas != null && !agendas.isEmpty()) {
                for (CmtAgendamientoMgl agendaOT : agendas) {

                    if (agendaOT.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                            .equalsIgnoreCase(Constant.ESTADO_AGENDA_NODONE)
                            || agendaOT.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                                    .equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA)) {

                        if (agendaOT.getDocAdjuntos() == null) {
                            consultarDocumentos(agendaOT);
                        }

                    }
                }
            } else {
                LOGGER.info("No existen agendas, por lo cual no se encontraron documentos adjuntos.");
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error al mostrar archivos adjuntos en CmtAgendamientoMglBean: mostrarDocumentosAdjuntos()" + e.getMessage(), e, LOGGER);
        }
    }

    public List<String> consultaArchivos(String adjuntos) {

        List<String> docAdjuntos = new ArrayList<>();
        String[] parts = adjuntos.split("\\|");
        docAdjuntos.addAll(Arrays.asList(parts));
        return docAdjuntos;
    }
    
      public List<CmtArchivosVtMgl> consultaArchivosAge(CmtAgendamientoMgl agenda) {

        List<CmtArchivosVtMgl> archivosAge = new ArrayList<>();
          try {
              archivosAge = cmtArchivosVtMglFacadeLocal.findByIdOtAndAge(agenda);
          } catch (ApplicationException e) {
              FacesUtil.mostrarMensajeError("Ocurrió un error al consultar archivo de la agenda: " + e.getMessage(), e, LOGGER);
          } catch (Exception e) {
              FacesUtil.mostrarMensajeError("Se genera error consultando archivos en CmtAgendamientoMglBean: consultaArchivosAge()"  + e.getMessage(), e, LOGGER);
          }
        return archivosAge;
    }

    public String traerNombre(String url) {

        String nombre;
        nombre = url.substring(url.lastIndexOf('/') + 1);
        LOGGER.info("Path: " + url + " -- File: " + nombre);
        return nombre;

    }
   
    public String regresaCadenaTipoPrerequisito(String[] prerequisitos) {
        
        String requisitos = "";
        int i = 0;
        for (String s : prerequisitos) {
            if (i == 0) {
                requisitos = s;
            } else {
                requisitos = requisitos + "|" + s;
            }
            i++;
        }
        return requisitos;
    }

    /**
     * Metodo para validar las acciones a realizar en el formulario de
     * agendamiento Autor: Victor Bocanegra
     *
     * @param opcion String nombre de la opcion que realizara el componente
     * @return boolean indicador para verificar si se visualizan o no los
     * componentes
     */
    private boolean validarAccionAgendamiento(String opcion) {
        try {
            return ValidacionUtil.validarVisualizacion(FORMULARIO, opcion, opcionesFacade, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
         } catch (Exception e) {
              FacesUtil.mostrarMensajeError("Se generea error validando accion de Agendamiento en CmtAgendamientoMglBean: validarAccionAgendamiento()" + e.getMessage(), e, LOGGER);
          }
        return false;
    }
    
    private boolean validarRestriccionesCcmm(CapacidadAgendaDto capacidad) {
        try {

            if (capacidad.getTimeSlot() != null && capacidad.getTimeSlot().equalsIgnoreCase(Constant.DURANTE_EL_DIA)) {
                if (ot.getCmObj() != null) {
                    List<CmtHorarioRestriccionMgl> restriccionMgls
                            = restriccionMglFacadeLocal.findByCuentaMatrizId(ot.getCmObj().getCuentaMatrizId());

                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(capacidad.getDate());
                    int dia = cal.get(Calendar.DAY_OF_WEEK);
                    String diaFecha = devuelveDia(dia);

                    List<String> diasCompara;

                    if (restriccionMgls.size() > 0) {
                        for (CmtHorarioRestriccionMgl horarioRestriccionMgl : restriccionMgls) {

                            if (horarioRestriccionMgl.getTipoRestriccion().toString().
                                    equalsIgnoreCase(Constant.TIPO_RES_RESTRICCION)
                                    || horarioRestriccionMgl.getTipoRestriccion().toString().
                                            equalsIgnoreCase(Constant.TIPO_RES_NO_DISPONIBLE)) {

                                diasCompara = diasComparar(horarioRestriccionMgl.getDiaInicio().getDia(),
                                        horarioRestriccionMgl.getDiaFin().getDia());

                                for (String diaComparar : diasCompara) {

                                    if (diaComparar.equalsIgnoreCase(diaFecha)) {
                                        return false;
                                    }
                                }

                            }
                        }
                    }
                }
                return true;
            } else if (capacidad.getTimeSlot() != null) {
                if (ot.getCmObj() != null) {
                    List<CmtHorarioRestriccionMgl> restriccionMgls
                            = restriccionMglFacadeLocal.findByCuentaMatrizId(ot.getCmObj().getCuentaMatrizId());

                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(capacidad.getDate());
                    int dia = cal.get(Calendar.DAY_OF_WEEK);
                    String diaFecha = devuelveDia(dia);

                    String[] partsHorIniFi = capacidad.getTimeSlot().split("-");
                    int parteHorIni = Integer.parseInt(partsHorIniFi[0]);

                    List<String> diasCompara;

                    if (restriccionMgls.size() > 0) {
                        for (CmtHorarioRestriccionMgl horarioRestriccionMgl : restriccionMgls) {

                            if (horarioRestriccionMgl.getTipoRestriccion().toString().
                                    equalsIgnoreCase(Constant.TIPO_RES_RESTRICCION)
                                    || horarioRestriccionMgl.getTipoRestriccion().toString().
                                            equalsIgnoreCase(Constant.TIPO_RES_NO_DISPONIBLE)) {

                                diasCompara = diasComparar(horarioRestriccionMgl.getDiaInicio().getDia(),
                                        horarioRestriccionMgl.getDiaFin().getDia());

                                for (String diaComparar : diasCompara) {

                                    if (diaComparar.equalsIgnoreCase(diaFecha)) {
                                        String hIni = horarioRestriccionMgl.getHoraInicio().substring(0, 2);
                                        String hFin = horarioRestriccionMgl.getHoraFin().substring(0, 2);
                                        int resHorIni = Integer.parseInt(hIni);
                                        int resHorFin = Integer.parseInt(hFin);
                                        for (int i = resHorIni; i < resHorFin; i++) {
                                            if (i == parteHorIni) {
                                                return false;
                                            }
                                        }

                                    }

                                }

                            }
                        }
                    }
                }
                return true;
            } else if (capacidad.getHoraInicio() != null) {
                if (ot.getCmObj() != null) {
                    List<CmtHorarioRestriccionMgl> restriccionMgls
                            = restriccionMglFacadeLocal.findByCuentaMatrizId(ot.getCmObj().getCuentaMatrizId());

                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(capacidad.getDate());
                    int dia = cal.get(Calendar.DAY_OF_WEEK);
                    String diaFecha = devuelveDia(dia);

                    String[] partsHorIniFi = null;
                    if (capacidad.getHoraInicio().contains(":")) {
                        partsHorIniFi = capacidad.getHoraInicio().split(":");
                    }

                    int parteHorIni = Integer.parseInt(partsHorIniFi[0]);

                    List<String> diasCompara;

                    if (restriccionMgls.size() > 0) {
                        for (CmtHorarioRestriccionMgl horarioRestriccionMgl : restriccionMgls) {

                            if (horarioRestriccionMgl.getTipoRestriccion().toString().
                                    equalsIgnoreCase(Constant.TIPO_RES_RESTRICCION)
                                    || horarioRestriccionMgl.getTipoRestriccion().toString().
                                            equalsIgnoreCase(Constant.TIPO_RES_NO_DISPONIBLE)) {

                                diasCompara = diasComparar(horarioRestriccionMgl.getDiaInicio().getDia(),
                                        horarioRestriccionMgl.getDiaFin().getDia());

                                for (String diaComparar : diasCompara) {

                                    if (diaComparar.equalsIgnoreCase(diaFecha)) {
                                        String hIni = horarioRestriccionMgl.getHoraInicio().substring(0, 2);
                                        String hFin = horarioRestriccionMgl.getHoraFin().substring(0, 2);
                                        int resHorIni = Integer.parseInt(hIni);
                                        int resHorFin = Integer.parseInt(hFin);
                                        for (int i = resHorIni; i < resHorFin; i++) {
                                            if (i == parteHorIni) {
                                                return false;
                                            }
                                        }

                                    }

                                }

                            }
                        }
                    }
                }
                return true;
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Se generea error validando restricciones en CmtAgendamientoMglBean: validarRestriccionesCcmm()" + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
    public String devuelveDia(int dia){
         String diaSemana="";
        try {

             switch (dia) {
                 case 1:
                     diaSemana = "DOMINGO";
                     break;
                 case 2:
                     diaSemana = "LUNES";
                     break;
                 case 3:
                     diaSemana = "MARTES";
                     break;
                 case 4:
                     diaSemana = "MIERCOLES";
                     break;
                 case 5:
                     diaSemana = "JUEVES";
                     break;
                 case 6:
                     diaSemana = "VIERNES";
                     break;
                 case 7:
                     diaSemana = "SABADO";
                     break;
                 default:
                     break;
             }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error validando fechas en CmtAgendamientoMglBean: devuelveDia() " + e.getMessage(), e, LOGGER);
        }
        return diaSemana;
    }
  
    public List<String> diasComparar(String diaIni, String diaFin) {

        List<String> result = new ArrayList<>();

        try {
            if (diaIni.equalsIgnoreCase(diaFin)) {
                result.add(diaIni);
            } else if (diaIni.equalsIgnoreCase("LUNES")) {
                if (diaFin.equalsIgnoreCase("MARTES")) {
                    result.add("LUNES");
                    result.add("MARTES");
                } else if (diaFin.equalsIgnoreCase("MIERCOLES")) {
                    result.add("LUNES");
                    result.add("MARTES");
                    result.add("MIERCOLES");
                } else if (diaFin.equalsIgnoreCase("JUEVES")) {
                    result.add("LUNES");
                    result.add("MARTES");
                    result.add("MIERCOLES");
                    result.add("JUEVES");
                } else if (diaFin.equalsIgnoreCase("VIERNES")) {
                    result.add("LUNES");
                    result.add("MARTES");
                    result.add("MIERCOLES");
                    result.add("JUEVES");
                    result.add("VIERNES");
                } else if (diaFin.equalsIgnoreCase("SABADO")) {
                    result.add("LUNES");
                    result.add("MARTES");
                    result.add("MIERCOLES");
                    result.add("JUEVES");
                    result.add("VIERNES");
                    result.add("SABADO");
                } else if (diaFin.equalsIgnoreCase("DOMINGO")) {
                    result.add("LUNES");
                    result.add("MARTES");
                    result.add("MIERCOLES");
                    result.add("JUEVES");
                    result.add("VIERNES");
                    result.add("SABADO");
                    result.add("DOMINGO");
                }
            } else if (diaIni.equalsIgnoreCase("MARTES")) {
                if (diaFin.equalsIgnoreCase("MIERCOLES")) {
                    result.add("MARTES");
                    result.add("MIERCOLES");
                } else if (diaFin.equalsIgnoreCase("JUEVES")) {
                    result.add("MARTES");
                    result.add("MIERCOLES");
                    result.add("JUEVES");
                } else if (diaFin.equalsIgnoreCase("VIERNES")) {
                    result.add("MARTES");
                    result.add("MIERCOLES");
                    result.add("JUEVES");
                    result.add("VIERNES");
                } else if (diaFin.equalsIgnoreCase("SABADO")) {
                    result.add("MARTES");
                    result.add("MIERCOLES");
                    result.add("JUEVES");
                    result.add("VIERNES");
                    result.add("SABADO");
                } else if (diaFin.equalsIgnoreCase("DOMINGO")) {
                    result.add("MARTES");
                    result.add("MIERCOLES");
                    result.add("JUEVES");
                    result.add("VIERNES");
                    result.add("SABADO");
                    result.add("DOMINGO");
                }
            } else if (diaIni.equalsIgnoreCase("MIERCOLES")) {
                if (diaFin.equalsIgnoreCase("JUEVES")) {
                    result.add("MIERCOLES");
                    result.add("JUEVES");
                } else if (diaFin.equalsIgnoreCase("VIERNES")) {
                    result.add("MIERCOLES");
                    result.add("JUEVES");
                    result.add("VIERNES");
                } else if (diaFin.equalsIgnoreCase("SABADO")) {
                    result.add("MIERCOLES");
                    result.add("JUEVES");
                    result.add("VIERNES");
                    result.add("SABADO");
                } else if (diaFin.equalsIgnoreCase("DOMINGO")) {
                    result.add("MIERCOLES");
                    result.add("JUEVES");
                    result.add("VIERNES");
                    result.add("SABADO");
                    result.add("DOMINGO");
                }
            } else if (diaIni.equalsIgnoreCase("JUEVES")) {
                if (diaFin.equalsIgnoreCase("VIERNES")) {
                    result.add("JUEVES");
                    result.add("VIERNES");
                } else if (diaFin.equalsIgnoreCase("SABADO")) {
                    result.add("JUEVES");
                    result.add("VIERNES");
                    result.add("SABADO");
                } else if (diaFin.equalsIgnoreCase("DOMINGO")) {
                    result.add("JUEVES");
                    result.add("VIERNES");
                    result.add("SABADO");
                    result.add("DOMINGO");
                }
            } else if (diaIni.equalsIgnoreCase("VIERNES")) {
                if (diaFin.equalsIgnoreCase("SABADO")) {
                    result.add("VIERNES");
                    result.add("SABADO");
                } else if (diaFin.equalsIgnoreCase("DOMINGO")) {
                    result.add("VIERNES");
                    result.add("SABADO");
                    result.add("DOMINGO");
                }
            } else if (diaIni.equalsIgnoreCase("SABADO")) {
                if (diaFin.equalsIgnoreCase("DOMINGO")) {
                    result.add("SABADO");
                    result.add("DOMINGO");
                }
            } else {
                result.add("DOMINGO");
            }
        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en Agendamiento al comparar días: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error comparando fechas en CmtAgendamientoMglBean: diasComparar() "+ e.getMessage(), e, LOGGER);
        }
        return result;
    }
    
    
    private String listInfoByPage(int page) {
        try {
            actual = page;
            getPageActual();
            String subTipoWorForce = retornaSubtipoWorfoce(ot);
            agendas = agendamientoFacade.buscarAgendaPorOtAndSubTipoWorkfoce
                    (page,ConstantsCM.PAGINACION_DIEZ_FILAS,ot,subTipoWorForce);
            agendasTotal = agendamientoFacade.buscarAgendaPorOtAndSubTipoWorkfoce
                    (0, 0, ot,subTipoWorForce);

        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error cargando lista en CmtAgendamientoMglBean: listInfoByPage() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }
    
    public boolean validarCreacion() {

        boolean respuesta = true;
        try {
            String subTipoWorForce = retornaSubtipoWorfoce(ot);
            //Consultamos si la orden tiene agendas pendientes
            List<CmtAgendamientoMgl> agendasCons = agendamientoFacade.
                    agendasPorOrdenAndSubTipoWorkfoce(ot, subTipoWorForce);
            if (agendasCons != null && agendasCons.size() > 0) {
                for (CmtAgendamientoMgl agendasFor : agendasCons) {
                    if (agendasFor.getUltimaAgenda().equalsIgnoreCase("Y")) {
                        respuesta = false;
                    }
                }
            }
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error validando crenacion en  CmtAgendamientoMglBean: validarCreacion() "  + e.getMessage(), e, LOGGER);
        }
        return respuesta;
    }

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Ocurrió un error en la paginacion en CmtAgendamientoMglBean: pageFirst() " + ex.getMessage(), ex);
        }
    }

    public void pagePrevious() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception ex) {
              FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en CmtAgendamientoMglBean: pagePrevious() " + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Ocurrió un error en la paginacion en CmtAgendamientoMglBean: pagePrevious() " + ex.getMessage(), ex);
        }
    }

    public void irPagina() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = Integer.parseInt(numPagina);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en Agendamiento class: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en CmtAgendamientoMglBean: irPagina() " + e.getMessage(), e, LOGGER);
        }
    }

    public void pageNext() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en CmtAgendamientoMglBean: pageNext() " + ex.getMessage() + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Ocurrió un error en la paginacion en CmtAgendamientoMglBean: pageNext() " + ex.getMessage(), ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en CmtAgendamientoMglBean: pageLast() " + ex.getMessage() + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Ocurrió un error en la paginacion en CmtAgendamientoMglBean: pageLast() "+ ex.getMessage(), ex);
        }
    }

    public int getTotalPaginas() {
        try {
            String subTipoWorForce = retornaSubtipoWorfoce(ot);
            int pageSol = agendamientoFacade.getCountAgendasByOtAndSubTipoWorkfoce(ot,subTipoWorForce);
            int totalPaginas = (int) (
                    (pageSol % ConstantsCM.PAGINACION_DIEZ_FILAS != 0)
                        ? (pageSol / ConstantsCM.PAGINACION_DIEZ_FILAS) + 1
                        : pageSol / ConstantsCM.PAGINACION_DIEZ_FILAS);
            return totalPaginas;
        } catch (ApplicationException e) {
              FacesUtil.mostrarMensajeError("Ocurrió un error en Agendamiento class: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en CmtAgendamientoMglBean: getTotalPaginas() " + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    public String getPageActual() {
        try {
            paginaList = new ArrayList<>();
            int totalPaginas = getTotalPaginas();
            for (int i = 1; i <= totalPaginas; i++) {
                paginaList.add(i);
            }
            pageActual = String.valueOf(actual) + " de "
                    + String.valueOf(totalPaginas);

            if (numPagina == null) {
                numPagina = "1";
            }
            numPagina = String.valueOf(actual);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en CmtAgendamientoMglBean: getPageActual() " + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }
    
    public List<MglCapacityElement> retornaCapacityComponente(List<CapacidadAgendaDto> capacityConsulta,
            CmtAgendamientoMgl agendaCon, CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl) throws ApplicationException {

        List<MglCapacityElement> capacityElementList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        CmtAgendamientoMgl ageAnterior;
        CmtAgendamientoMgl agePosterior;
        int secCon = 0;
        if (agendaCon.getId() != null) {

            try {
                //Busco agenda anterior y posterior
                ageAnterior = agendamientoFacade.buscarAgendaAnteriorPosterior(agendaCon, secCon);

                secCon = 1;

                agePosterior = agendamientoFacade.buscarAgendaAnteriorPosterior(agendaCon, secCon);

                //Reagendar
                String fechaReagenda = format.format(agendaCon.getFechaAgenda());
                Date fechaRea = format.parse(fechaReagenda);

                for (CapacidadAgendaDto capacidadAgendaDto : capacityConsulta) {

                    MglCapacityElement capacityElement = new MglCapacityElement();

                    int agendaBaseCon = 0;
                    Date fechaCap = null;

                    if (agendasTotal.size() > 0) {
                        for (CmtAgendamientoMgl agendamientoMgl : agendasTotal) {

                            String fechaCapacity = format.format(capacidadAgendaDto.getDate());
                            String fechaAgenda = format.format(agendamientoMgl.getFechaAgenda());

                            Date fechaAge;

                            try {
                                fechaCap = format.parse(fechaCapacity);
                                fechaAge = format.parse(fechaAgenda);

                                if (fechaCap.before(fechaAge)) {
                                    LOGGER.info("La Fecha del capacity  es menor a la del agendamiento ");
                                } else if (fechaAge.before(fechaCap)) {
                                    LOGGER.info("La Fecha del capacity es Mayor a la del agendamiento ");
                                } else {
                                    if (capacidadAgendaDto.getTimeSlot().equalsIgnoreCase(agendamientoMgl.getTimeSlot())) {

                                        if (!agendamientoMgl.getBasicaIdEstadoAgenda().
                                                getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTADO_AGENDA_CANCELADA)
                                                && !agendamientoMgl.getBasicaIdEstadoAgenda().
                                                getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA) 
                                                && !agendamientoMgl.getBasicaIdEstadoAgenda().
                                                getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTADO_AGENDA_NODONE) ){
                                            agendaBaseCon = 1;
                                            agendamientoMgl.setEstaEnCapacity(true);
                                        } else {
                                            LOGGER.info("Agenda esta en estado cancelada");
                                        }
                                    } else {
                                        LOGGER.info("Igual fecha distinta franja");
                                    }
                                }
                            } catch (ParseException ex) {
                                LOGGER.error("Ocurrió un error al consultar el capacity: " + ex.getMessage());
                            } catch (Exception ex) {
                                FacesUtil.mostrarMensajeError("Ocurrió un error retornando capacidad en  CmtAgendamientoMglBean: retornaCapacityComponente() " + ex.getMessage() + ex.getMessage(), ex, LOGGER);
                                LOGGER.error("Ocurrió un error retornando capacidad en  CmtAgendamientoMglBean: retornaCapacityComponente() " + ex.getMessage());
                            }
                        }
                    }

                    if (agendaBaseCon == 1) {

                        capacityElement = retornaCapacityUsed(capacityElement, capacidadAgendaDto);

                    } else {

                        String fechaAnterior;
                        Date fechaAnt = null;
                        String fechaPosterior;
                        Date fechaPos = null;
                        String timeSlotAnt;
                        String timeSlotPos;
                        int timSlotAnt = 0;
                        int timSlotPos = 0;
                        int timSlotCap;

                        if (ageAnterior != null && !ageAnterior.getTimeSlot().equalsIgnoreCase(Constant.DURANTE_EL_DIA)) {
                            fechaAnterior = format.format(ageAnterior.getFechaAgenda());
                            fechaAnt = format.parse(fechaAnterior);
                            timeSlotAnt = ageAnterior.getTimeSlot();
                            timSlotAnt = Integer.parseInt(timeSlotAnt.substring(0, 2));
                        } else if (ageAnterior != null) {
                            fechaAnterior = format.format(ageAnterior.getFechaAgenda());
                            fechaAnt = format.parse(fechaAnterior);
                        }

                        if (agePosterior != null && !agePosterior.getTimeSlot().equalsIgnoreCase(Constant.DURANTE_EL_DIA)) {
                            fechaPosterior = format.format(agePosterior.getFechaAgenda());
                            fechaPos = format.parse(fechaPosterior);
                            timeSlotPos = agePosterior.getTimeSlot();
                            timSlotPos = Integer.parseInt(timeSlotPos.substring(0, 2));
                        } else if (agePosterior != null) {
                            fechaPosterior = format.format(agePosterior.getFechaAgenda());
                            fechaPos = format.parse(fechaPosterior);
                        }

                        if (capacidadAgendaDto.getTimeSlot().equalsIgnoreCase(Constant.DURANTE_EL_DIA)) {
                            LOGGER.info("Time slot en letras reagendar");

                            String fechaCapacity = format.format(capacidadAgendaDto.getDate());
                            Date fechaCapa = format.parse(fechaCapacity);

                            if (ageAnterior == null && agePosterior == null) {
                                capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                            } else if (agePosterior != null && ageAnterior == null) {

                                if (fechaPos != null) {
                                    if (fechaPos.after(fechaRea) && timSlotPos == 0) {
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                                    } else {
                                        capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                    }
                                }

                            } else if (ageAnterior != null && agePosterior == null) {
                                if (fechaAnt != null) {
                                    if (fechaAnt.before(fechaRea) || fechaCapa.after(fechaRea)) {
                                        if (fechaCapa.after(fechaRea)) {
                                            capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                                        } else {
                                            if (timSlotAnt > 0) {
                                                capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                            } else {
                                                capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                                            }
                                        }
                                    } else {
                                        capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                    }
                                }
                            } else {
                                //Hay agenda anterior y Posterior
                                if (fechaPos != null && fechaAnt != null) {
                                    if (fechaPos.after(fechaRea) && fechaAnt.before(fechaRea) && fechaCapa.before(fechaPos)) {
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                                    } else {
                                        capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                    }
                                }
                            }
                        } else {
                            timSlotCap = Integer.parseInt(capacidadAgendaDto.getTimeSlot().substring(0, 2));

                            if (fechaAnt != null && fechaPos != null) {
                                if (fechaCap != null && fechaCap.before(fechaAnt)) {
                                    capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                } else if (fechaCap != null && fechaCap.after(fechaAnt)) {
                                    if (fechaCap.before(fechaPos)) {
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                                    } else if (fechaCap.after(fechaPos)) {
                                        capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                    } else {
                                        if (timSlotCap < timSlotPos) {
                                            capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                                        } else {
                                            capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                        }
                                    }
                                } else {
                                    if (ageAnterior != null && ageAnterior.getTimeSlot().equalsIgnoreCase(Constant.DURANTE_EL_DIA)) {
                                        capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                    } else {
                                        if (timSlotCap < timSlotAnt) {
                                            capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                        } else if (fechaCap != null && fechaCap.before(fechaPos)) {
                                            capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);

                                        } else if (fechaCap != null && fechaCap.after(fechaPos)) {
                                            capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                        } else {
                                            if (timSlotCap < timSlotPos) {
                                                capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                                            } else {
                                                capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                            }
                                        }
                                    }
                                }
                            } else if (fechaAnt != null && fechaPos == null) {
                                if (fechaCap != null && fechaCap.before(fechaAnt)) {
                                    capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                } else if (fechaCap != null && fechaCap.after(fechaAnt)) {
                                    if (fechaCap.before(fechaRea)) {
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                                    } else if (fechaCap.after(fechaRea)) {
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                                    } else {
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                                    }
                                } else {
                                    if (timSlotCap < timSlotAnt) {
                                        capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                    } else {
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                                    }
                                }

                            } else if (fechaPos != null && fechaAnt == null) {

                                if (fechaCap != null && fechaCap.before(fechaPos)) {
                                    capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                                } else if (fechaCap != null && fechaCap.after(fechaPos)) {
                                    capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                } else {
                                    if (timSlotCap < timSlotPos) {
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                                    } else {
                                        capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                    }
                                }
                            } else {
                                capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                            }
                        }
                    }
                    capacityElementList.add(capacityElement);
                }
            } catch (ParseException ex) {
                FacesUtil.mostrarMensajeError("Ocurrió un error al consultar el capacity: " + ex.getMessage(), ex, LOGGER);
            } catch (ApplicationException | NumberFormatException ex) {
                FacesUtil.mostrarMensajeError("Ocurrió un error retornando capacidad en  CmtAgendamientoMglBean: retornaCapacityComponente() " + ex.getMessage(), ex, LOGGER);
            }
        } else {
            //Nueva agenda  
            CmtAgendamientoMgl ultimaAgenda;
            ultimaAgenda = agendamientoFacade.buscarUltimaAgenda(ot, estadosAgendadas(), retornaSubtipoWorfoce(ot));
            try {
                for (CapacidadAgendaDto capacidadAgendaDto : capacityConsulta) {

                    MglCapacityElement capacityElement = new MglCapacityElement();

                    int control = 0;
                    String fechaCapacity = format.format(capacidadAgendaDto.getDate());
                    Date fechaCap = format.parse(fechaCapacity);

                    if (agendasTotal.size() > 0) {
                        for (CmtAgendamientoMgl agendamientoMgl : agendasTotal) {

                            String fechaParaAgendar = format.format(capacidadAgendaDto.getDate());
                            String fechaValidar = format.format(agendamientoMgl.getFechaAgenda());
                            Date fechaDate1;
                            Date fechaDate2;

                            try {
                                fechaDate1 = format.parse(fechaParaAgendar);
                                fechaDate2 = format.parse(fechaValidar);

                                if (fechaDate1.before(fechaDate2)) {
                                    LOGGER.info("La Fecha del capacity  es menor a la del agendamiento ");
                                } else if (fechaDate2.before(fechaDate1)) {
                                    LOGGER.info("La Fecha del capacity es Mayor a la del agendamiento ");
                                } else {
                                    if (capacidadAgendaDto.getTimeSlot().equalsIgnoreCase(agendamientoMgl.getTimeSlot())) {

                                        if (!agendamientoMgl.getBasicaIdEstadoAgenda().
                                                getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTADO_AGENDA_CANCELADA)) {
                                            control = 1;
                                            agendamientoMgl.setEstaEnCapacity(true);
                                        } else {
                                            LOGGER.info("Agenda esta en estado cancelada");
                                        }
                                    } else {
                                        LOGGER.info("Igual fecha distinta franja");
                                    }
                                }
                            } catch (ParseException ex) {
                                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                                LOGGER.error(msg, ex);
                            }
                        }
                    }

                    if (control == 1) {
                        capacityElement = retornaCapacityUsed(capacityElement, capacidadAgendaDto);
                    } else {
                        if (capacidadAgendaDto.getTimeSlot().equalsIgnoreCase(Constant.DURANTE_EL_DIA)) {
                            if (ultimaAgenda != null) {
                                if (fechaCap.before(ultimaAgenda.getFechaAgenda())) {
                                    capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                } else if (fechaCap.after(ultimaAgenda.getFechaAgenda())) {
                                    capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                                } else {
                                    capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                }
                            } else {
                                capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                            }
                        } else {
                            int timSlotCap = Integer.parseInt(capacidadAgendaDto.getTimeSlot().substring(0, 2));

                            if (ultimaAgenda != null) {
                                String fechaUltimaAgenda = format.format(ultimaAgenda.getFechaAgenda());
                                if (ultimaAgenda.getTimeSlot().equalsIgnoreCase(Constant.DURANTE_EL_DIA)) {
                                    LOGGER.info("Time slot en letras ultima agenda ");
                                    Date ultimaAge = format.parse(fechaUltimaAgenda);
                                    if (fechaCap.before(ultimaAge)) {
                                        capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                    } else if (fechaCap.after(ultimaAge)) {
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                                    } else {
                                        capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                    }
                                } else {
                                    int timeSlotUltima = Integer.parseInt(ultimaAgenda.getTimeSlot().substring(0, 2));
                                    Date ultimaAge = format.parse(fechaUltimaAgenda);
                                    if (fechaCap.before(ultimaAge)) {
                                        capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                    } else if (fechaCap.after(ultimaAge)) {
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                                    } else {
                                        if (timSlotCap < timeSlotUltima) {
                                            capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                        } else {
                                            capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                                        }
                                    }
                                }
                            } else {
                                capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto, cmtEstadoxFlujoMgl);
                            }
                        }
                    }
                    capacityElementList.add(capacityElement);
                }
            } catch (ParseException e) {
                FacesUtil.mostrarMensajeError("Ocurrió un error en Agendamiento class: " + e.getMessage(), e, LOGGER);
            } catch (NumberFormatException e) {
                FacesUtil.mostrarMensajeError("Ocurrió un error retornando capacidad en  CmtAgendamientoMglBean: retornaCapacityComponente() " + e.getMessage(), e, LOGGER);
            }
        }

        return capacityElementList;
    }

    private XMLGregorianCalendar generateDate(int year, int mount, int day) throws DatatypeConfigurationException {
        GregorianCalendar dates = new GregorianCalendar();
        dates.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
        dates.set(year, mount, day);
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(dates);
        date2.setMonth(mount);
        return date2;
    }
    
    public String crearAgendas(boolean tipoAgendamiento) {

         List<CmtAgendamientoMgl> agendarOTSubtipo = null;
         capacidadAgendaDtos = new ArrayList();
         
        try {
            isRequest= false;
            if (!validarAccionAgendamiento(OPCION_CREAR)) {
                mensajesValidacion = "No tiene el rol especifico para esta accion.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        mensajesValidacion, null));
                return null;
            }

            if (ot.getPersonaRecVt() == null || ot.getPersonaRecVt().isEmpty()) {
                mensajesValidacion = "El nombre de la persona que recibe la visita es obligatorio.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        mensajesValidacion, null));
                return null;
            }
            if (ot.getTelPerRecVt() == null) {
                mensajesValidacion = "El telefono de la persona que recibe la visita es obligatorio.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        mensajesValidacion, null));
                return null;
            }

            if (ot.getEmailPerRecVT() == null || ot.getEmailPerRecVT().isEmpty()) {
                mensajesValidacion = "El Correo de la persona que recibe la visita es obligatorio.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajesValidacion, null));
                return null;
            }

            CmtOnyxResponseDto cmtOnyxResponseDto = null;
            if (ot.getOnyxOtHija() != null) {
                //Validamos disponibilidad del servicio
                ParametrosMgl wsdlOtHija
                        = parametrosMglFacadeLocal.findParametroMgl(Constant.WSDL_OT_HIJA_ONIX);

                if (wsdlOtHija == null) {
                    throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WSDL_OT_HIJA_ONIX);
                }

                URL urlCon = new URL(wsdlOtHija.getParValor());

                ConsumoGenerico.conectionWsdlTest(urlCon, Constant.WSDL_OT_HIJA_ONIX);
                //Fin Validacion disponibilidad del servicio
                
                cmtOnyxResponseDto = ordenTrabajoMglFacadeLocal.consultarOTHijaOnyx(ot.getOnyxOtHija().toString());
                
            } else {
                List<OnyxOtCmDir> listaOnyx = onyxOtCmDirlFacadeLocal.findOnyxOtCmDirById(ot.getIdOt());
                if (listaOnyx != null && !listaOnyx.isEmpty()) {
                    OnyxOtCmDir objOnyx = listaOnyx.get(0);
                    //Validamos disponibilidad del servicio
                    ParametrosMgl wsdlOtHija
                            = parametrosMglFacadeLocal.findParametroMgl(Constant.WSDL_OT_HIJA_ONIX);

                    if (wsdlOtHija == null) {
                        throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WSDL_OT_HIJA_ONIX);
                    }

                    URL urlCon = new URL(wsdlOtHija.getParValor());

                    ConsumoGenerico.conectionWsdlTest(urlCon, Constant.WSDL_OT_HIJA_ONIX);
                    //Fin Validacion disponibilidad del servicio

                    cmtOnyxResponseDto = ordenTrabajoMglFacadeLocal.consultarOTHijaOnyx(objOnyx.getOnyx_Ot_Hija_Cm());
                    
                    ot.setOnyxOtHija(new BigDecimal(objOnyx.getOnyx_Ot_Hija_Cm()));
                    ot.setComplejidadServicio(objOnyx.getComplejidadServicio());
                } else {
                    if (ot.getTipoOtObj() != null
                            && ot.getTipoOtObj().getRequiereOnyx()
                            != null && ot.getTipoOtObj().
                                    getRequiereOnyx().equalsIgnoreCase("Y")) {
                        mensajesValidacion = "Debe ir a la pestaña de Onix y diligenciar la OT hija para agendar.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        mensajesValidacion, null));
                        return null;
                    }
                }
            }

            int errores = 0;

            if (!tipoAgendamiento) {
                capacidadAgendaDtos = mglScheduleComponetDto.getCapacidadAgendaDtos();
            } else {
                //Es tecnico anticipado valido los valores obligatorios
                if (fechaSelected == null || fechaSelected.isEmpty()) {
                    mensajesValidacion = "La Fecha Agenda es obligatorio.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                }
                if (tecnicoSelected == null || tecnicoSelected.isEmpty()) {
                    mensajesValidacion = "Tecnico Disponible es obligatorio.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                }
                if (franjaSelected == null || franjaSelected.isEmpty()) {
                    mensajesValidacion = "La Franja Disponible es obligatorio.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                }
                if (horaInicio == null || horaInicio.isEmpty()) {
                    mensajesValidacion = "La Hora Inicio es obligatorio.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                }
                if (!validarCedulaTecnico(tecnicoSelected)) {
                     mensajesValidacion = "La cedula del tecnico debe ser un valor diferente  a 0.";
                     FacesContext.getCurrentInstance().addMessage(null,
                             new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     mensajesValidacion, null));
                     return null;
                 }
                if (!horaInicio.matches(EXPRESION_HORAS)) {
                    mensajesValidacion = "La Hora Inicio no tiene el formato adecuado Ejp:(00:00).";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                } else {
                    if (!validaHoraInicio()) {
                        mensajesValidacion = "La hora seleccionada no corresponde con el horario disponible del técnico";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        mensajesValidacion, null));
                        return null;
                    } else if (!validoFechaHoraAgendas(agendas)) {
                        mensajesValidacion = "Existe ya una agenda para el dia: "+fechaSelected+" y  "
                                + "la misma hora : "+agendaIgualFechaHora.getHoraInicio()+"";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        mensajesValidacion, null));
                        return null;
                    }else{
                   
                        SimpleDateFormat formatterFecha_Selected = new SimpleDateFormat("yyyy-MM-dd");
                        Date feccha_select_date = formatterFecha_Selected.parse(fechaSelected);
                        DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                        Date fechaAgenda = formato.parse(formato.format(feccha_select_date));
                        CapacidadAgendaDto capacidadAgendaDto = new CapacidadAgendaDto();
                        capacidadAgendaDto.setDate(fechaAgenda);
                        capacidadAgendaDto.setHoraInicio(horaInicio+":00");
                       
                         if (itemSelected != null) {
                            Resource resource = itemSelected.getResource();
                            String idAliadoFin="";
                            if (resource.getXR_IdAliado() != null && !resource.getXR_IdAliado().isEmpty()
                                    && resource.getXR_IdAliado().contains("-")) {
                                String parts[] = resource.getXR_IdAliado().split("-");
                                if (parts != null && parts.length > 0) {
                                    idAliadoFin = parts[0];
                                }
                            } else if (resource.getXR_IdAliado() != null && !resource.getXR_IdAliado().isEmpty()) {
                                idAliadoFin = resource.getXR_IdAliado();
                            }
                            capacidadAgendaDto.setAliadoId(idAliadoFin);
                            capacidadAgendaDto.setNombreTecnico(resource.getName());
                            capacidadAgendaDto.setResourceId(resource.getResourceId());
                        }
                        capacidadAgendaDtos.add(capacidadAgendaDto);
                    }
                }
            }
            
            //Validamos disponibilidad del servicio
            ParametrosMgl wadlAgenda
                    = parametrosMglFacadeLocal.findParametroMgl(Constant.WADL_SERVICES_AGENDAMIENTOS);

            if (wadlAgenda == null) {
                throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WADL_SERVICES_AGENDAMIENTOS);
            }
            
            URL url = new URL(wadlAgenda.getParValor());
            
            ConsumoGenerico.conectionWsdlTest(url, Constant.WADL_SERVICES_AGENDAMIENTOS);
            //Fin Validacion disponibilidad del servicio

            if (capacidadAgendaDtos != null && capacidadAgendaDtos.size() > 0) {
                mensajesValidacion = "";
                mensajesValidacionFinal="";
                
                
                for (CapacidadAgendaDto capacidadAgendaDto : capacidadAgendaDtos) {
                    //Valida restricciones de la ccmm
                    if (!validarRestriccionesCcmm(capacidadAgendaDto)) {
                        mensajesValidacion = "La cuenta matriz tiene horarios de restriccion o no "
                                + "disponibilidad para la hora: " + capacidadAgendaDto.getHoraInicio() + ".";
                        errores++;
                    }
                    mensajesValidacionFinal += mensajesValidacion;
                }
                
                
                List<CapacidadAgendaDto> capacityAllDay = new ArrayList<>();
                capacidadAgendaDtos.stream().filter((capacidadAgendaDto) ->
                        (capacidadAgendaDto.getTimeSlot() != null && capacidadAgendaDto.getTimeSlot().equalsIgnoreCase(Constant.DURANTE_EL_DIA))).forEachOrdered((capacidadAgendaDto) -> {
                    capacityAllDay.add(capacidadAgendaDto);
                });

                if (!capacityAllDay.isEmpty()) {
                    SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                    for (CapacidadAgendaDto capacidadAllday : capacityAllDay) {
                        for (CapacidadAgendaDto capacidadSeleccionada : capacidadAgendaDtos) {
                            if ((capacidadAllday.getDate().equals(capacidadSeleccionada.getDate()))
                                    && !capacidadSeleccionada.getTimeSlot().
                                            equalsIgnoreCase(Constant.DURANTE_EL_DIA)) {
                                mensajesValidacion = "No puede agendar una franja y Durante el Dia para "
                                        + "el dia: " + formateador.format(capacidadAllday.getDate()) + " .";
                                errores++;

                                mensajesValidacionFinal += mensajesValidacion;

                            }
                        }
                    }

                }
                //Valido si no hay errores
                if (errores > 0) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            mensajesValidacionFinal, null));
                    return null;

                } else {
                    //Creo las agendas no hay errores
                    String mensajeFinal = "";
                    String mensajeFinalErrorCap = "";
                       int i =0;
                       int j =0;
                    if (idBasicaPrerequisito != null) {
                        String prerequisitos = regresaCadenaTipoPrerequisito(idBasicaPrerequisito);
                        ot.setPrerequisitosVT(prerequisitos);
                    }

                       //CREAR OT EN RR                           
                    CmtEstadoxFlujoMgl estadoFlujoOrden;
                    String numeroOTRr = null;
                    
                    ParametrosMgl parametroHabilitarRR = parametrosMglFacadeLocal.findParametroMgl(Constant.HABILITAR_RR);
                    
                    if (parametroHabilitarRR == null) {
                        throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.HABILITAR_RR);
                    }

                  if (parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                        estadoFlujoOrden = estadoxFlujoMglFacadeLocal.findByTipoFlujoAndEstadoInt(ot.getTipoOtObj().getTipoFlujoOt(), ot.getEstadoInternoObj(), ot.getBasicaIdTecnologia());
                        if (estadoFlujoOrden != null) {
                            if (estadoFlujoOrden.getSubTipoOrdenOFSC() != null && estadoFlujoOrden.getTipoTrabajoRR() != null && estadoFlujoOrden.getEstadoOtRRInicial() != null && estadoFlujoOrden.getEstadoOtRRFinal() != null) {
                                String subTipoWorForce = estadoFlujoOrden.getSubTipoOrdenOFSC().getNombreBasica();
                                agendarOTSubtipo = agendamientoFacade.agendasPorOrdenAndSubTipoWorkfoce(ot, subTipoWorForce);
                                if ((agendarOTSubtipo == null || agendarOTSubtipo.isEmpty())) {
                                    LOGGER.info("Se creara OT en RR , es la primera agenda del estado actual de la OT en MGL ");
                                    numeroOTRr = ordenTrabajoMglFacadeLocal.crearOtRRporAgendamiento(ot, estadoFlujoOrden, usuarioSesion.getNombre(), perfilVt);
                                }
                            } else {
                                LOGGER.info("El estado actual de la OT no tiene los campos de creacion de OT RR configurados , se enviara id de OT MGL");
                                String mensajeCamposRRestado = "Recuerde que para que se cree OT en RR y continuar con el agendamiento debe diligenciar los campos , Tipo OT RR , Estado Inicial y Estado Cierre en el estado x flujo actual de OT MGL. ";
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeCamposRRestado, null));
                                return null;
                            }

                        } else {
                            LOGGER.info("El estado actual de la OT no permite agendamiento");
                        }
                        //--
                    } else {
                        LOGGER.info("El parámetro " + Constant.ACTIVAR_CREACION_OT_RR + " no se encuentra habilitado. No se crea orden en RR.");  
                    }
    
                    if (agendarOTSubtipo != null && !agendarOTSubtipo.isEmpty()) {
                        numeroOTRr = agendarOTSubtipo.get(0).getIdOtenrr() != null ? agendarOTSubtipo.get(0).getIdOtenrr() : ot.getIdOt().toString();
                    }
                    
                    String numeroOTRrOFSC;
                    if (numeroOTRr == null) {
                        // Si no fue generada la OT en Rr           
                        numeroOTRrOFSC = generarNumeroOtRr(ot, numeroOTRr, false);

                    } else {
                        numeroOTRrOFSC = generarNumeroOtRr(ot, numeroOTRr, true);
                    }

                    String requestURL = FacesUtil.getFullRequestContextPath();
 
                    
                    for (CapacidadAgendaDto capacidadAgendaDto : capacidadAgendaDtos) {
                        agenda = new CmtAgendamientoMgl();
                        agenda.setOrdenTrabajo(ot);
                        agenda.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                        agenda.setIdentificacionTecnico(capacidadAgendaDto.getResourceId());
                        agenda.setNombreTecnico(capacidadAgendaDto.getNombreTecnico());
                        agenda.setIdentificacionAliado(capacidadAgendaDto.getAliadoId());
                        agenda.setNumeroOrdenInmediata(capacidadAgendaDto.getNumeroOrdenInmediata());
                                      
                        if (numeroOTRr != null && !numeroOTRr.isEmpty()) {
                            agenda.setIdOtenrr(numeroOTRr);
                        }
                        
                        if (nodoMgl != null) {
                            agenda.setNodoMgl(nodoMgl);
                        }
                        
                        if (i == 0) {

                            agenda = agendamientoFacade.agendar(capacidadAgendaDto, agenda, numeroOTRrOFSC, requestURL, tipoAgendamiento);
                            agenda.setIdOtenrr(numeroOTRr);
                            agendamientoFacade.setUser(usuarioVt, perfilVt);
                            // conveniencia
                            convenienciaCheck = conveniencia ? "Y" : "N";
                            agenda.setConveniencia(convenienciaCheck);
                            agenda.setCantAgendas("0");
                            agendamientoFacade.create(agenda);

                            if (cmtOnyxResponseDto != null) {
                                agenda.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                                try {
                                    agendamientoFacade.cargarInformacionForEnvioNotificacion(agenda, 1);
                                } catch (ApplicationException ex) {
                                    String msn = "Ocurrio un error al momento de "
                                            + "enviar notificacion de agenda: " + ex.getMessage() + "";
                                    FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                    msn, ""));

                                }
                            }

                            if (agenda.getTecnicoAnticipado() != null
                                    && agenda.getTecnicoAnticipado().equalsIgnoreCase("Y")) {
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String fechAge = df.format(agenda.getFechaAgenda());
                                String msn = "Se ha creado la agenda:  " + agenda.getOfpsOtId() + "  "
                                        + "  para la ot: " + agenda.getOrdenTrabajo().getIdOt() + " "
                                        + " con el tecnico:" + agenda.getNombreTecnico()
                                        + " [" + agenda.getIdentificacionTecnico() + "] "
                                        + "para el dia: "+fechAge+" en la hora: "+agenda.getHoraInicio()+" ";
                                mensajeFinal += msn;
                            } else {
                                String msn = "Se ha creado la agenda:  " + agenda.getOfpsOtId() + "  "
                                        + "  para la ot: " + agenda.getOrdenTrabajo().getIdOt() + " ";
                                mensajeFinal += msn;
                            }
                        } else {
                            List<CapacidadAgendaDto> resultado = getCapacidadMulti();
                            if (resultado.size() > 0) {

                                agenda = agendamientoFacade.agendar(capacidadAgendaDto, agenda, numeroOTRrOFSC, requestURL, tipoAgendamiento);
                                agenda.setIdOtenrr(numeroOTRr);
                                agendamientoFacade.setUser(usuarioVt, perfilVt);
                                // conveniencia
                                convenienciaCheck = conveniencia ? "Y" : "N";
                                agenda.setConveniencia(convenienciaCheck);
                                agenda.setCantAgendas("0");
                                agendamientoFacade.create(agenda);
                                if (cmtOnyxResponseDto != null) {
                                    agenda.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                                    try {
                                        agendamientoFacade.cargarInformacionForEnvioNotificacion(agenda, 1);
                                    } catch (ApplicationException ex) {
                                        String msn = "Ocurrio un error al momento de "
                                                + "enviar notificacion de agenda: " + ex.getMessage() + "";
                                        FacesContext.getCurrentInstance().addMessage(null,
                                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                        msn, ""));
                                    }
                                }

                                if (agenda.getTecnicoAnticipado() != null
                                    && agenda.getTecnicoAnticipado().equalsIgnoreCase("Y")) {
                                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                    String fechAge = df.format(agenda.getFechaAgenda());
                                    String msn = "Se ha creado la agenda:  " + agenda.getOfpsOtId() + "  "
                                            + "  para la ot: " + agenda.getOrdenTrabajo().getIdOt() + " "
                                            + " con el tecnico:" + agenda.getNombreTecnico()
                                            + " [" + agenda.getIdentificacionTecnico() + "] "
                                            + "para el dia: " + fechAge + " en la hora: " + agenda.getHoraInicio() + " ";
                                    mensajeFinal += msn;
                                } else {
                                    String msn = "Se ha creado la agenda:  " + agenda.getOfpsOtId() + "  "
                                            + "  para la ot: " + agenda.getOrdenTrabajo().getIdOt() + " ";
                                    mensajeFinal += msn;
                                }
                            } else {
                                String msnCap = "Ocurrio un error agendando para la "
                                        + " fecha: " + capacidadAgendaDto.getDate() + " ";

                                mensajeFinalErrorCap += msnCap;
                                j++;
                            }
                        }
                        i++;
                    }
                    ordenTrabajoMglFacadeLocal.actualizarOtCcmm(ot, usuarioVt, perfilVt);
                    
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                            mensajeFinal, ""));

                    if (j > 0) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajeFinalErrorCap, ""));
                    }
                    listInfoByPage(1);
                    regresar();
                }
                
            } else {
                mensajesValidacion = "Debe selecionar al menos una celda para agendar.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        mensajesValidacion, null));
                return null;
            }

        } catch (ApplicationException e) {
            String msg;
            if (e.getMessage().contains("$")) {
                partesMensaje = e.getMessage().split("\\$");
                msg = partesMensaje[0];
            } else {
                msg = e.getMessage();
            }

            if (partesMensaje != null && partesMensaje[1] != null && !partesMensaje[1].isEmpty()) {
                isRequest = true;
                json = partesMensaje[1];
                session.setAttribute("json", json);
            }
            FacesUtil.mostrarMensajeError("Ocurrió un error al realizar el agendamiento: " + msg, e, LOGGER);
            if ((agendarOTSubtipo == null || agendarOTSubtipo.isEmpty())) {
                rollbackOrdenInRr(agenda);
            }
        } catch (ParseException  | IOException e) {
            String msg;
            if (e.getMessage().contains("$")) {
                partesMensaje = e.getMessage().split("\\$");
                msg = partesMensaje[0];
            } else {
                msg = e.getMessage();
            }

            if (partesMensaje != null && partesMensaje[1] != null && !partesMensaje[1].isEmpty()) {
                isRequest = true;
                json = partesMensaje[1];
                session.setAttribute("json", json);
            }
            FacesUtil.mostrarMensajeError("Ocurrió un error creando la agenda CmtAgendamientoMglBean: crearAgendas() " + msg, e, LOGGER);
            if ((agendarOTSubtipo == null || agendarOTSubtipo.isEmpty())) {
                rollbackOrdenInRr(agenda);
            }
        }

        return "";
    }
    
    public String reagendarOrden() {

        try {
            isRequest= false;
            
            if (!validarAccionAgendamiento(OPCION_REAGENDAR)) {

                mensajesValidacion = "No tiene el rol especifico para esta accion.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        mensajesValidacion, null));
                return null;
            }

            if (razonReagendamiento == null || razonReagendamiento.isEmpty()) {
                mensajesValidacion = "La razon es obligatoria para reagendar.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        mensajesValidacion, null));
                return null;
            }
            if (comentariosOrden == null || comentariosOrden.isEmpty()) {
                mensajesValidacion = "El comentario es obligatorio para reagendar.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajesValidacion, null));
                return null;
            }
            
            //Validamos disponibilidad del servicio
            ParametrosMgl wadlAgenda
                    = parametrosMglFacadeLocal.findParametroMgl(Constant.WADL_SERVICES_AGENDAMIENTOS);

            if (wadlAgenda == null) {
                throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WADL_SERVICES_AGENDAMIENTOS);
            }

            URL url = new URL(wadlAgenda.getParValor());

            ConsumoGenerico.conectionWsdlTest(url, Constant.WADL_SERVICES_AGENDAMIENTOS);
            //Fin Validacion disponibilidad del servicio
            
            if (reAgendaMgl.getHoraInicio() != null && reAgendaMgl.getTecnicoAnticipado() != null 
                    && reAgendaMgl.getTecnicoAnticipado().equalsIgnoreCase("Y")) {
                //Es tecnico anticipado valido los valores obligatorios
                if (fechaSelected == null || fechaSelected.isEmpty()) {
                    mensajesValidacion = "La Fecha Agenda es obligatorio.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                }
                if (tecnicoSelected == null || tecnicoSelected.isEmpty()) {
                    mensajesValidacion = "Tecnico Disponible es obligatorio.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                }
                if (franjaSelected == null || franjaSelected.isEmpty()) {
                    mensajesValidacion = "La Franja Disponible es obligatorio.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                }
                if (horaInicio == null || horaInicio.isEmpty()) {
                    mensajesValidacion = "La Hora Inicio es obligatorio.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                }

                if (!horaInicio.matches(EXPRESION_HORAS)) {
                    mensajesValidacion = "La Hora Inicio no tiene el formato adecuado Ejp:(00:00).";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                } else {
                    if (!validaHoraInicio()) {
                        mensajesValidacion = "La hora seleccionada no corresponde con el horario disponible del técnico";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        mensajesValidacion, null));
                        return null;
                    } else if (!validoFechaHoraAgendas(agendas)) {
                        mensajesValidacion = "Existe ya una agenda para el dia: " + fechaSelected + " y  "
                                + "la misma hora : " + agendaIgualFechaHora.getHoraInicio() + "";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        mensajesValidacion, null));
                        return null;
                    } else {
                        
                        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                        capacidadAgendaDtos = new ArrayList<>();
                        Date fechaAgenda;
                        fechaAgenda = formato.parse(fechaSelected);
                        CapacidadAgendaDto capacidadAgendaDto = new CapacidadAgendaDto();
                        capacidadAgendaDto.setDate(fechaAgenda);
                        capacidadAgendaDto.setHoraInicio(horaInicio+":00");
                        
                        if (itemSelected != null) {
                            Resource resource = itemSelected.getResource();
                            String idAliadoFin = "";
                            if (resource.getXR_IdAliado() != null && !resource.getXR_IdAliado().isEmpty()
                                    && resource.getXR_IdAliado().contains("-")) {
                                String parts[] = resource.getXR_IdAliado().split("-");
                                if (parts != null && parts.length > 0) {
                                    idAliadoFin = parts[0];
                                }
                            } else if (resource.getXR_IdAliado() != null && !resource.getXR_IdAliado().isEmpty()) {
                                idAliadoFin = resource.getXR_IdAliado();
                            }
                            capacidadAgendaDto.setAliadoId(idAliadoFin);
                            capacidadAgendaDto.setNombreTecnico(resource.getName());
                            capacidadAgendaDto.setResourceId(resource.getResourceId());
                        }
                        capacidadAgendaDtos.add(capacidadAgendaDto);
                        mensajesValidacion = "";
                        CapacidadAgendaDto capacidadAgendaDto1 = capacidadAgendaDtos.get(0);

                        if (!validarRestriccionesCcmm(capacidadAgendaDto1)) {
                            mensajesValidacion = "La cuenta matriz tiene horarios de restriccion o no "
                                    + "disponibilidad en la franja: " + capacidadAgendaDto1.getTimeSlot() + ".";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            mensajesValidacion, null));
                            return null;
                        } else {
                            // conveniencia
                            convenienciaCheck = conveniencia ? "Y" : "N";
                            reAgendaMgl.setConveniencia(convenienciaCheck);
                            //conteo de agendas
                            int cantAgend = agendamientoFacade.getCountAgendamiento(ot,reAgendaMgl);
                            reAgendaMgl.setCantAgendas(String.valueOf(cantAgend));
                            
                            if (reAgendaMgl.getMotivosReagenda() != null
                                    && !reAgendaMgl.getMotivosReagenda().isEmpty()) {
                                reAgendaMgl.setMotivosReagenda(reAgendaMgl.getMotivosReagenda() + razonReagendamiento + ",");
                            } else {
                                reAgendaMgl.setMotivosReagenda(razonReagendamiento + ",");
                            }

                            agendamientoFacade.reagendar(capacidadAgendaDto1, reAgendaMgl,
                                    razonReagendamiento, comentariosOrden,
                                    usuarioVt, perfilVt);
                            
                            String requestURL = FacesUtil.getFullRequestContextPath();
                            reAgendaMgl.setComentarioReasigna("Modificacion del tecnico al reagendar");
                            agendamientoFacade.update(reAgendaMgl, usuarioVt, perfilVt, requestURL);
                            String msn = "Se ha Reagendado la agenda:  " + reAgendaMgl.getOfpsOtId() + "  "
                                    + "  para la ot: " + reAgendaMgl.getOrdenTrabajo().getIdOt() + " ";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            msn, ""));

                            listInfoByPage(1);
                            regresar();
                        }

                    }
                }

            } else {
                capacidadAgendaDtos = mglScheduleComponetDto.getCapacidadAgendaDtos();

                if (capacidadAgendaDtos.size() > 0) {
                    if (capacidadAgendaDtos.size() > 1) {
                        mensajesValidacion = "Debe selecionar solo una celda para reagendar la agenda.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        mensajesValidacion, null));
                        return null;
                    } else {
                        mensajesValidacion = "";
                        CapacidadAgendaDto capacidadAgendaDto = capacidadAgendaDtos.get(0);

                        if (!validarRestriccionesCcmm(capacidadAgendaDto)) {
                            mensajesValidacion = "La cuenta matriz tiene horarios de restriccion o no "
                                    + "disponibilidad en la franja: " + capacidadAgendaDto.getTimeSlot() + ".";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            mensajesValidacion, null));
                            return null;
                        } else {
                            // conveniencia
                            convenienciaCheck = conveniencia ? "Y" : "N";
                            reAgendaMgl.setConveniencia(convenienciaCheck);
                             //conteo de agendas
                            int cantAgend = agendamientoFacade.getCountAgendamiento(ot,reAgendaMgl);
                            reAgendaMgl.setCantAgendas(String.valueOf(cantAgend));
                            
                             if (reAgendaMgl.getMotivosReagenda() != null
                                    && !reAgendaMgl.getMotivosReagenda().isEmpty()) {
                                reAgendaMgl.setMotivosReagenda(reAgendaMgl.getMotivosReagenda() + razonReagendamiento + ",");
                            } else {
                                reAgendaMgl.setMotivosReagenda(razonReagendamiento + ",");
                            }
                            agendamientoFacade.reagendar(capacidadAgendaDto, reAgendaMgl,
                                    razonReagendamiento, comentariosOrden,
                                    usuarioVt, perfilVt);
                           
                            
                            if (reAgendaMgl.getTecnicoAnticipado() != null && reAgendaMgl.getTecnicoAnticipado().equalsIgnoreCase("Y")) {

                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String fechAge = df.format(reAgendaMgl.getFechaAgenda());
                                String msn = "Se ha reagendado la agenda:  " + reAgendaMgl.getOfpsOtId()+ "  "
                                        + "  para la ot: " + reAgendaMgl.getOrdenTrabajo().getIdOt()+ " "
                                        + " con el técnico:" + reAgendaMgl.getNombreTecnico()
                                        + " [" + reAgendaMgl.getIdentificacionTecnico() + "] "
                                        + "para el dia: " + fechAge + " en la hora: " + reAgendaMgl.getHoraInicio() + " ";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                msn, ""));
                            } else {
                                String msn = "Se ha Reagendado la agenda:  " + reAgendaMgl.getOfpsOtId() + "  "
                                        + "  para la ot: " + reAgendaMgl.getOrdenTrabajo().getIdOt() + " ";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                msn, ""));
                            }

                            listInfoByPage(1);
                            regresar();
                        }
                    }
                } else {
                    mensajesValidacion = "Debe selecionar al menos una celda para reagendar.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                }
            }
        } catch (ApplicationException e) {
            String msg;
            if (e.getMessage().contains("$")) {
                partesMensaje = e.getMessage().split("\\$");
                msg = partesMensaje[0];
            } else {
                msg = e.getMessage();
            }

            if (partesMensaje != null && partesMensaje[1] != null && !partesMensaje[1].isEmpty()) {
                isRequest = true;
                json = partesMensaje[1];
                session.setAttribute("json", json);
            }
            FacesUtil.mostrarMensajeError("Ocurrió un error al realizar el reagendamiento: " + msg, e, LOGGER);
        } catch (ParseException|IOException e) {
            String msg;
            if (e.getMessage().contains("$")) {
                partesMensaje = e.getMessage().split("\\$");
                msg = partesMensaje[0];
            } else {
                msg = e.getMessage();
            }

            if (partesMensaje != null && partesMensaje[1] != null && !partesMensaje[1].isEmpty()) {
                isRequest = true;
                json = partesMensaje[1];
                session.setAttribute("json", json);
            }
            FacesUtil.mostrarMensajeError("Ocurrió un error reagendando en CmtAgendamientoMglBean: reagendarOrden() " + msg, e, LOGGER);
        }
        return "";
    }
    
     public List<CapacidadAgendaDto> getCapacidadMulti() {

        capacidadAgendaDtos = new ArrayList<>();
         try {
             capacidadAgendaDtos
                     = agendamientoFacade.getCapacidad(ot, null, nodoMgl);

         } catch (ApplicationException e) {
              FacesUtil.mostrarMensajeError("Ocurrió un error en Agendamiento class: " + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
             FacesUtil.mostrarMensajeError("Ocurrió un error obteniendo capacidad CmtAgendamientoMglBean: getCapacidadMulti() " + e.getMessage(), e, LOGGER);
         }
        return capacidadAgendaDtos;
    }
    
     
     public String retornaSubtipoWorfoce(CmtOrdenTrabajoMgl ot) throws ApplicationException{
         
        String subtipoWorfoce = "";
         try {
             if (ot != null) {
                 CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = cmtEstadoxFlujoMglFacadeLocal.
                         findByTipoFlujoAndEstadoInt(ot.getTipoOtObj().getTipoFlujoOt(),
                                 ot.getEstadoInternoObj(),
                                 ot.getBasicaIdTecnologia());

                 if (cmtEstadoxFlujoMgl != null) {
                     if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                         subtipoWorfoce = cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC().getNombreBasica();
                     }
                 }

             }
         } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Ocurrió un error retornando subtipoWorforce en CmtAgendamientoMglBean: retornaSubtipoWorfoce() "+ e.getMessage(), e, LOGGER);
         }
        return subtipoWorfoce;
    }
     
    ////<editor-fold defaultstate="collapsed" desc="GETTERS & SETTERS">
    public List<CmtAgendamientoMgl> getAgendas() {
        return this.agendas;
    }

    public List<CapacidadAgendaDto> getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(List<CapacidadAgendaDto> capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<RestriccionAgendaDto> getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(List<RestriccionAgendaDto> restricciones) {
        this.restricciones = restricciones;
    }
    ////</editor-fold>

    public CmtOrdenTrabajoMgl getOt() {
        return ot;
    }

    public void setOt(CmtOrdenTrabajoMgl ot) {
        this.ot = ot;
    }

    public CmtAgendamientoMgl getAgenda() {
        return agenda;
    }

    public void setAgenda(CmtAgendamientoMgl agenda) {
        this.agenda = agenda;
    }

    public boolean isReagendar() {
        return reagendar;
    }

    public void setReagendar(boolean reagendar) {
        this.reagendar = reagendar;
    }

    public String getRazonReagendamiento() {
        return razonReagendamiento;
    }

    public void setRazonReagendamiento(String razonReagendamiento) {
        this.razonReagendamiento = razonReagendamiento;
    }

    public String getComentariosOrden() {
        return comentariosOrden;
    }

    public void setComentariosOrden(String comentariosOrden) {
        this.comentariosOrden = comentariosOrden;
    }

    public boolean isCrearAgenda() {
        return crearAgenda;
    }

    public void setCrearAgenda(boolean crearAgenda) {
        this.crearAgenda = crearAgenda;
    }

    public boolean isCancelar() {
        return cancelar;
    }

    public void setCancelar(boolean cancelar) {
        this.cancelar = cancelar;
    }

    public String getRazonCancela() {
        return razonCancela;
    }

    public void setRazonCancela(String razonCancela) {
        this.razonCancela = razonCancela;
    }

    public List<CmtAgendamientoMgl> getCancelaAgendaMgl() {
        return cancelaAgendaMgl;
    }

    public void setCancelaAgendaMgl(List<CmtAgendamientoMgl> cancelaAgendaMgl) {
        this.cancelaAgendaMgl = cancelaAgendaMgl;
    }

    public List<CmtAgendamientoMgl> getUpdateAgendaMgl() {
        return updateAgendaMgl;
    }

    public void setUpdateAgendaMgl(List<CmtAgendamientoMgl> updateAgendaMgl) {
        this.updateAgendaMgl = updateAgendaMgl;
    }

    public boolean isConsulta() {
        return consulta;
    }

    public void setConsulta(boolean consulta) {
        this.consulta = consulta;
    }

    public List<String> getNombresArchivos() {
        return nombresArchivos;
    }

    public void setNombresArchivos(List<String> nombresArchivos) {
        this.nombresArchivos = nombresArchivos;
    }

    public boolean isUpdateAgenda() {
        return updateAgenda;
    }

    public void setUpdateAgenda(boolean updateAgenda) {
        this.updateAgenda = updateAgenda;
    }

    public CmtAgendamientoMgl getActualizaAgenda() {
        return actualizaAgenda;
    }

    public void setActualizaAgenda(CmtAgendamientoMgl actualizaAgenda) {
        this.actualizaAgenda = actualizaAgenda;
    }

    public List<CmtBasicaMgl> getPrerequisitosVt() {
        return prerequisitosVt;
    }

    public void setPrerequisitosVt(List<CmtBasicaMgl> prerequisitosVt) {
        this.prerequisitosVt = prerequisitosVt;
    }

    public String[] getIdBasicaPrerequisito() {
        return idBasicaPrerequisito;
    }

    public void setIdBasicaPrerequisito(String[] idBasicaPrerequisito) {
        this.idBasicaPrerequisito = idBasicaPrerequisito;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    public boolean isShowFooterTable() {
        return showFooterTable;
    }

    public void setShowFooterTable(boolean showFooterTable) {
        this.showFooterTable = showFooterTable;
    }

    public MglScheduleComponetDto getMglScheduleComponetDto() {
        return mglScheduleComponetDto;
    }

    public void setMglScheduleComponetDto(MglScheduleComponetDto mglScheduleComponetDto) {
        this.mglScheduleComponetDto = mglScheduleComponetDto;
    }

    public List<CmtAgendamientoMgl> getAgendasTotal() {
        return agendasTotal;
    }

    public void setAgendasTotal(List<CmtAgendamientoMgl> agendasTotal) {
        this.agendasTotal = agendasTotal;
    }
    
    public void entrarPorOt() throws ApplicationException {

        
    }

    public String getUrlArchivoSoporte() {
        return urlArchivoSoporte;
    }

    public void setUrlArchivoSoporte(String urlArchivoSoporte) {
        this.urlArchivoSoporte = urlArchivoSoporte;
    }

    public boolean isTecnicoAnticipado() {
        return tecnicoAnticipado;
    }

    public void setTecnicoAnticipado(boolean tecnicoAnticipado) {
        this.tecnicoAnticipado = tecnicoAnticipado;
    }

    public boolean isVerPanelTecnicos() {
        return verPanelTecnicos;
    }

    public void setVerPanelTecnicos(boolean verPanelTecnicos) {
        this.verPanelTecnicos = verPanelTecnicos;
    }

    public String armarUrl(CmtArchivosVtMgl archivosVtMgl) {

        // Documento original de UCM
        String urlOriginal = archivosVtMgl.getRutaArchivo();

        String requestContextPath = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestContextPath();

        // URL correspondiente al Servlet que descarga imagenes de CCMM desde UCM.
        urlOriginal = requestContextPath + "/view/MGL/document/download/" + urlOriginal;

        return urlArchivoSoporte = " <a href=\"" + urlOriginal
                + "\"  target=\"blank\">" + archivosVtMgl.getNombreArchivo() + "</a>";

    }
    
    public MglCapacityElement retornaCapacityLibre(MglCapacityElement capacityElement,
            CapacidadAgendaDto capacidadAgendaDto, CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl) throws ParseException {

        capacityElement.setUsed(false);
        capacityElement.setDate(capacidadAgendaDto.getDate());
        capacityElement.setTimeSlot(capacidadAgendaDto.getTimeSlot());
        
        if ((getValidateMinutesSlot(capacidadAgendaDto, cmtEstadoxFlujoMgl))) {
                capacityElement.setQuota(new Long("0"));
                capacityElement.setAvailable(new Long("0"));
        } else {
                capacityElement.setQuota(capacidadAgendaDto.getQuota());
                capacityElement.setAvailable(capacidadAgendaDto.getAvailable());
        }
        capacityElement.setBucket(capacidadAgendaDto.getBucket());
        capacityElement.setWorkSkill(capacidadAgendaDto.getWorkSkill());
        if (capacidadAgendaDto.getRestricciones().size() > 0) {
            List<MglCapacityRestrictionElement> capacityRestrictionElements = new ArrayList<>();
            for (RestriccionAgendaDto restriccionAgendaDto
                    : capacidadAgendaDto.getRestricciones()) {
                MglCapacityRestrictionElement mglCapacityRestrictionElement = new MglCapacityRestrictionElement();
                mglCapacityRestrictionElement.setCode(restriccionAgendaDto.getCode());
                mglCapacityRestrictionElement.setDescription(restriccionAgendaDto.getDescription());
                mglCapacityRestrictionElement.setType(restriccionAgendaDto.getType());
                capacityRestrictionElements.add(mglCapacityRestrictionElement);
            }
            capacityElement.setRestriction(capacityRestrictionElements);
        }
        return capacityElement;
    }
    
    public MglCapacityElement retornaCapacityUsed(MglCapacityElement capacityElement,
            CapacidadAgendaDto capacidadAgendaDto) {

        capacityElement.setUsed(true);
        capacityElement.setDate(capacidadAgendaDto.getDate());
        capacityElement.setTimeSlot(capacidadAgendaDto.getTimeSlot());
        capacityElement.setQuota(capacidadAgendaDto.getQuota());
        capacityElement.setAvailable(capacidadAgendaDto.getAvailable());
        capacityElement.setBucket(capacidadAgendaDto.getBucket());
        capacityElement.setWorkSkill(capacidadAgendaDto.getWorkSkill());
        if (capacidadAgendaDto.getRestricciones().size() > 0) {
            List<MglCapacityRestrictionElement> capacityRestrictionElements = new ArrayList<>();
            for (RestriccionAgendaDto restriccionAgendaDto
                    : capacidadAgendaDto.getRestricciones()) {
                MglCapacityRestrictionElement mglCapacityRestrictionElement = new MglCapacityRestrictionElement();
                mglCapacityRestrictionElement.setCode(restriccionAgendaDto.getCode());
                mglCapacityRestrictionElement.setDescription(restriccionAgendaDto.getDescription());
                mglCapacityRestrictionElement.setType(restriccionAgendaDto.getType());
                capacityRestrictionElements.add(mglCapacityRestrictionElement);
            }
            capacityElement.setRestriction(capacityRestrictionElements);
        }
        return capacityElement;
    }
    
    public MglCapacityElement retornaCapacityBloqueado(MglCapacityElement capacityElement,
            CapacidadAgendaDto capacidadAgendaDto) {

        capacityElement.setDate(capacidadAgendaDto.getDate());
        capacityElement.setTimeSlot(capacidadAgendaDto.getTimeSlot());
        capacityElement.setQuota(new Long("0"));
        capacityElement.setAvailable(new Long("0"));
        capacityElement.setBucket(capacidadAgendaDto.getBucket());
        capacityElement.setWorkSkill(capacidadAgendaDto.getWorkSkill());
        
        return capacityElement;
    }
    
    public List<BigDecimal> estadosAgendadas() {

        List<BigDecimal> idsEstados = new ArrayList<>();

        try {
            CmtBasicaMgl agendada = basicaMglFacadeLocal.findByCodigoInternoApp(Constant.ESTADO_AGENDA_AGENDADA);
            CmtBasicaMgl reagendada = basicaMglFacadeLocal.findByCodigoInternoApp(Constant.ESTADO_AGENDA_REAGENDADA);

            if (agendada != null) {
                idsEstados.add(agendada.getBasicaId());
            }
            if (reagendada != null) {
                idsEstados.add(reagendada.getBasicaId());
            }

        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Ocurrió un error al consultar los estados de agenda: " + ex.getMessage(), ex, LOGGER);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Ocurrió un error al consultar los estados de agenda: " + ex.getMessage(), ex, LOGGER);
        }

        return idsEstados;
    }
    
   private boolean getValidateMinutesSlot(CapacidadAgendaDto capacidadAgendaDto,
            CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String diaActual = format.format(new Date());
        String diaCapacidad = format.format(capacidadAgendaDto.getDate());
        boolean band_inhabilitar = false;
        
        if (diaCapacidad != null && !diaCapacidad.isEmpty()) {
            if (diaActual.equals(diaCapacidad)) {
                int minutes = cmtEstadoxFlujoMgl.getTiempoAgendaOfsc();
                
                if (capacidadAgendaDto.getTimeSlot().matches("[0-9]?[0-9]-[0-9]?[0-9]")) {
                    String[] timeSlot = capacidadAgendaDto.getTimeSlot().split("-");

                    Calendar horaActual = Calendar.getInstance();

                    if (Integer.parseInt(timeSlot[0]) <= horaActual.get(Calendar.HOUR_OF_DAY)
                            && horaActual.get(Calendar.HOUR_OF_DAY) <= Integer.parseInt(timeSlot[1])) {
                        return true;
                    }

                    if (minutes > 0) {

                         LocalDateTime fechaActual = LocalDateTime.now(ZoneId.of("America/Bogota"));
                        int horaInicioFranja = Integer.parseInt(timeSlot[0]);

                        if ((fechaActual.getHour() + 1) == horaInicioFranja) {
                            //Obtiene la fecha de capacidad de agenda transformada al tipo objeto requerido.
                            LocalDateTime fechayHoraAgendaHab = DateToolUtils.convertDateToLocalDateTime(capacidadAgendaDto.getDate());
                            LocalTime horaFranja = LocalTime.of(horaInicioFranja, 0, 0);
                            //Se obtiene la fecha de agenda, modificando su hora y minutos
                            LocalDateTime fechayHoraAgendaMod = fechayHoraAgendaHab.with(horaFranja);
                            //Se resta a la hora de la agenda habilitada, los minutos definidos en el parámetro.
                            fechayHoraAgendaMod = fechayHoraAgendaMod.minusMinutes(minutes);
                            //se valida si la fecha y hora actual es mayor a la fecha de agenda modificada
                            // si se cumple la condición anterior, asigna valor true, indicando que bloquea esos cupos.
                            band_inhabilitar = fechaActual.isAfter(fechayHoraAgendaMod);
                        }
                    }
                }
            }
        }
        return band_inhabilitar;
    }
   
    /**
     * Metodo para validar si hay agendas posteriores a la que se va a cancelar 
     * Autor: Victor Bocanegra
     *
     * @param agendaCancela agenda que se va a cancelar
     * @return boolean
     */
    public boolean validarAgendasPosteriores(CmtAgendamientoMgl agendaCancela) {

        boolean hayAgendasPosteriores = false;
        List<BigDecimal> idsEstAgendadas = estadosAgendadas();
        List<CmtAgendamientoMgl> agendasPos;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {

            agendasPos = agendamientoFacade.buscarAgendasrPosteriores(agendaCancela, idsEstAgendadas);
            if (agendasPos != null && agendasPos.size() > 0) {
                hayAgendasPosteriores = true;
                String msn = "No es posible cancelar la agenda seleccionada antes debe cancelar "
                        + "las agendas de fecha: ";

                String mensajeFinal="";
                String complemento;
                String fechaAgenda;

                for (CmtAgendamientoMgl agendamientoMgl : agendasPos) {
                    fechaAgenda = format.format(agendamientoMgl.getFechaAgenda());
                    complemento = fechaAgenda + " y franja: " + agendamientoMgl.getTimeSlot() + " |";
                    mensajeFinal += complemento;
                }
                mensajesValidacion = msn + mensajeFinal;

            } else {
                //No hay agendas posteriores
                hayAgendasPosteriores = false;

            }

        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Ocurrió un error al consultar las agendas posteriores: " + ex.getMessage(), ex, LOGGER);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Ocurrió un error al consultar las agendas posteriores: " + ex.getMessage(), ex, LOGGER);
        }
        return hayAgendasPosteriores;
    }
    
    
    /**
     * Genera un N&uacute;mero de OT de RR, de acuerdo a si fue creada o no una OT en RR.
     * @param orden Orden de Trabajo.
     * @param numeroOtRr N&uacute;mero de la OT en RR.
     * @param habilitaRr Flag que determina si se encuentra RR habilitado.
     * @return N&uacute;mero de la OT en RR.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public String generarNumeroOtRr(CmtOrdenTrabajoMgl orden, String numeroOtRr, boolean habilitaRr) 
            throws ApplicationException {
        
        String numeroGenerado = "";
        
        try {
            if (orden != null) {
                if (habilitaRr) {
                    //Si se generó una Ot en RR:
                    if (numeroOtRr != null && orden.getCmObj() != null && orden.getCmObj().getNumeroCuenta() != null) {
                        // Número de cuenta matriz de RR>+<Número orden de RR a 5,  justificada a la derecha y relleno con ceros.
                        numeroGenerado = orden.getCmObj().getNumeroCuenta().toString().trim() + StringUtils.leftPad(numeroOtRr, MAX_CARACTERES_NUMERO_OT_RR, "0");
                    }

                } else {
                    //Si no se generó una Ot en RR:
                    if (orden.getEstadoInternoObj() != null && orden.getEstadoInternoObj().getBasicaId() != null) {
                        String estadoOt = orden.getEstadoInternoObj().getBasicaId().toString();

                        if (orden.getIdOt() != null) {
                            // Número de Orden de MGL>+<PK del Estado en que este la Orden de trabajo a 6 dígitos  justificada a la derecha y relleno con ceros.
                            numeroGenerado = orden.getIdOt().toString().trim() + StringUtils.leftPad(estadoOt, MAX_CARACTERES_ESTADO_OT, "0");
                        }
                    }
                }
            }
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de obtener el Número OT de RR: " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
        
        return numeroGenerado;
    }
    
    public void rollbackOrdenInRr(CmtAgendamientoMgl agendamientoMgl) {

        boolean respuestaElimina;

        try {
            if (agendamientoMgl.getIdOtenrr() != null && !agendamientoMgl.getIdOtenrr().isEmpty()) {
                //Consultamos la ot en rr si existe
                ResponseOtEdificiosList responseOtEdificiosList = ordenTrabajoMglFacadeLocal.
                        ordenTrabajoEdificioQuery(agendamientoMgl.getOrdenTrabajo().getCmObj().getNumeroCuenta().toString());

                if (responseOtEdificiosList != null
                        && responseOtEdificiosList.getListOtEdificios() != null
                        && !responseOtEdificiosList.getListOtEdificios().isEmpty()) {

                    for (ResponseDataOtEdificios responseDataOtEdificios
                            : responseOtEdificiosList.getListOtEdificios()) {

                        if (responseDataOtEdificios.getNumeroTrabajo().
                                equalsIgnoreCase(agendamientoMgl.getIdOtenrr())) {
                            //Eliminamos la ot en rr si existe
                            respuestaElimina = ordenTrabajoMglFacadeLocal.
                                    eliminarOtRRporAgendamiento(agendamientoMgl, usuarioVt);

                            if (respuestaElimina) {
                                LOGGER.info("Orden No: " + agendamientoMgl.getIdOtenrr() + " borrada por rollback en RR");
                            }
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error al realizar el agendamiento: rollbackOrdenInRr " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error creando la agenda CmtAgendamientoMglBean: rollbackOrdenInRr() " + e.getMessage(), e, LOGGER);
        }

    }
   
    /**
     * Realiza una redirecci&oacute;n a la URL especificada.
     *
     * @param url URL a la que se desea dirigir.
     * @throws ApplicationException
     */
    public static void navegarAPagina(String url) throws ApplicationException {
        if (url != null && !url.isEmpty()) {
            try {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.
                        redirect(getShortRequestContextPath() + url.replace(getShortRequestContextPath(), ""));
            } catch (IOException e) {
                throw new ApplicationException("Error al navegar a la página: " + e.getMessage(), e);
            }
        }
    }

    
    private boolean validarTecnicoAnticipado(CmtOrdenTrabajoMgl ot) throws ApplicationException {

        boolean tecnicoAnticipadoRes = false;

        try {

            CmtCuentaMatrizMgl cm = ot.getCmObj();
            CmtSubEdificioMgl subEdificioMgl;
            subEdificioMgl = cm.getSubEdificioGeneral();
            CmtComunidadRr comunidadRr = null;

            CmtTecnologiaSubMgl tecnologiaSubMgl = tecnologiaSubMglFacadeLocal.
                    findBySubEdificioTecnologia(subEdificioMgl,
                            ot.getBasicaIdTecnologia());

            if (tecnologiaSubMgl != null && tecnologiaSubMgl.getTecnoSubedificioId() != null && tecnologiaSubMgl.getNodoId()!= null) {
               
                    nodoMgl = nodoMglFacadeLocal.findById(tecnologiaSubMgl.getNodoId().getNodId());
                    if (nodoMgl != null) {
                        comunidadRr = nodoMgl.getComId();
                    }
            } else {
                //Consulto el geo
                nodoMgl = nodoMglFacadeLocal.consultaGeo(ot);
                if (nodoMgl == null) {
                    throw new ApplicationException("No existe nodo de cobertura para la tecnologia de la Ot");
                } else {
                    comunidadRr = nodoMgl.getComId();
                }
            }
            //Consulta de la extendida tipo de trabajo
            List<CmtExtendidaTipoTrabajoMgl> extendidaTipoTrabajoMgl = extendidaTipoTrabajoMglFacadeLocal.
                    findBytipoTrabajoObjAndCom(comunidadRr, ot.getBasicaIdTipoTrabajo());

            if (extendidaTipoTrabajoMgl != null && !extendidaTipoTrabajoMgl.isEmpty()) {
                CmtExtendidaTipoTrabajoMgl extendidaTipoTrabajoMgl1 = extendidaTipoTrabajoMgl.get(0);
                String tecnicoAnt = extendidaTipoTrabajoMgl1.getTecnicoAnticipado();

                if (tecnicoAnt != null && tecnicoAnt.equalsIgnoreCase("1")) {
                    tecnicoAnticipadoRes = true;
                } else if (tecnicoAnt != null && tecnicoAnt.equalsIgnoreCase("0")) {
                    tecnicoAnticipadoRes = false;
                }
            }

        } catch (ApplicationException ex) {
           throw new ApplicationException(ex.getMessage());
        }
        return tecnicoAnticipadoRes;
    }
    
    private boolean validarAgendaInmediata(CmtOrdenTrabajoMgl ot) throws ApplicationException {

        boolean agendaInmediataRes = false;

        try {
            CmtComunidadRr comunidadRr = null;

            if (nodoMgl == null) {
                throw new ApplicationException("No existe nodo de cobertura para la tecnologia de la Ot");
            } else {
                comunidadRr = nodoMgl.getComId();
            }

            //Consulta de la extendida tipo de trabajo
            List<CmtExtendidaTipoTrabajoMgl> extendidaTipoTrabajoMgl = extendidaTipoTrabajoMglFacadeLocal.
                    findBytipoTrabajoObjAndCom(comunidadRr, ot.getBasicaIdTipoTrabajo());

            if (extendidaTipoTrabajoMgl != null && !extendidaTipoTrabajoMgl.isEmpty()) {
                CmtExtendidaTipoTrabajoMgl extendida = extendidaTipoTrabajoMgl.get(0);
                String agendaInm = extendida.getAgendaInmediata();

                if (agendaInm != null && agendaInm.equalsIgnoreCase("1")) {
                    agendaInmediataRes = true;
                } else if (agendaInm != null && agendaInm.equalsIgnoreCase("0")) {
                    agendaInmediataRes = false;
                }
            }
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return agendaInmediataRes;
    }
    
    public void abrirPanelTecnicos() throws ApplicationException, IOException {

        try {

            if (!validarAccionAgendamiento(OPCION_CREAR_ANTICIPADO)) {
                mensajesValidacion = "No tiene el rol especifico para esta accion.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajesValidacion, null));
                return;
            }
            if (validarCreacion()) {
                //Consulto el parametro WORKTIME
                String parWorktime = parametrosMglFacadeLocal.findByAcronimo(
                        co.com.telmex.catastro.services.util.Constant.PAR_WORKTIME)
                        .iterator().next().getParValor();

                if (parWorktime != null && !parWorktime.isEmpty()) {
                    if (isNumeric(parWorktime)) {
                        parWorktimeNum = Integer.parseInt(parWorktime);
                    } else {
                        mensajesValidacion = "El valor: " + parWorktime + " no es numerico";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        mensajesValidacion, null));
                        return;
                    }
                } else {
                    mensajesValidacion = "No esta configurado el parametro: "
                            + "" + co.com.telmex.catastro.services.util.Constant.PAR_WORKTIME
                            + " para la consulta de los tecnicos";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return;
                }

                CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = cmtEstadoxFlujoMglFacadeLocal.
                        findByTipoFlujoAndEstadoInt(ot.getTipoOtObj().getTipoFlujoOt(),
                                ot.getEstadoInternoObj(), ot.getBasicaIdTecnologia());
                int diasAmostrar = 0;
                if (cmtEstadoxFlujoMgl.getEstadoxFlujoId() != null) {
                    if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                        diasAmostrar = cmtEstadoxFlujoMgl.getDiasAMostrarAgenda();
                    }

                }
                if (diasAmostrar < 7) {
                    diasAmostrar = 7;
                }

                fechasAgendas = sumarDiasAFecha(new Date(), diasAmostrar);
                fechaSelected = null;
                itemSelected = null;
                tecnicosDisponibles = null;
                tecnicoSelected = null;
                franjaSelected = null;
                lstFranjas = null;
                horaInicio = "";
                horaSelected = "";
                lstHoras = null;
                minSelected = "";
                lstMin = null;

                reagendar = false;
                cancelar = false;
                consulta = false;
                updateAgenda = false;

                CmtTipoBasicaMgl tipoPrerequisitoVt = tipoBasicaMglFacadeLocal.
                        findByCodigoInternoApp(Constant.TIPO_BASICA_PREREQUISITOS_AGENDA);

                prerequisitosVt = basicaMglFacadeLocal.findByTipoBasica(tipoPrerequisitoVt);
                if (ot.getPrerequisitosVT() != null) {
                    idBasicaPrerequisito = ot.getPrerequisitosVT().split("\\|");
                } else {
                    idBasicaPrerequisito = null;
                }
                if (reagendarTecnicoAnt) {
                    verPanelTecnicos = false;
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String fechAge;
                    if (reAgendaMgl.getFechaReagenda() != null) {
                        fechAge = df.format(reAgendaMgl.getFechaReagenda());
                        fechaSelected = fechAge;
                    } else {
                        fechAge = df.format(reAgendaMgl.getFechaAgenda());
                        fechaSelected = fechAge;
                    }
                } else {
                    verPanelTecnicos = true;
                }
                verCapacity = false;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Ya se encuentra una ultima agenda cerrada no se pueden crear más agendas ", null));
                listInfoByPage(1);
                regresar();
            }
        } catch (ApplicationException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            ex.getMessage(), null));
        }
    }

    public void consultarTecnicos() {

        try {

            if (fechaSelected != null && !fechaSelected.isEmpty()) {

                List<String> fechas = new ArrayList<>();
                fechas.add(fechaSelected);
                itemSelected= null;
                tecnicosDisponibles = null;
                tecnicoSelected=null;
                franjaSelected=null;
                lstFranjas = null;
                horaSelected = "";
                lstHoras = null;
                minSelected = "";
                lstMin = null;
                horaInicio="";
                ApiFindTecnicosResponse response = agendamientoFacade.consultarTecnicosDisponibles(ot, nodoMgl, fechas);
                if (response != null && response.getDetail() != null
                        && response.getStatus() != null) {

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    response.getDetail(), null));

                } else if (response != null && response.getItems() != null
                        && !response.getItems().isEmpty()) {
                    tecnicosDisponibles = new ArrayList<>();
                    if (reAgendaMgl != null && reAgendaMgl.getId() != null) {
                        List<Items> lstItems = new ArrayList<>();
                        for (Items items : response.getItems()) {
                            if (items.getResource().getResourceId().
                                    equalsIgnoreCase(reAgendaMgl.getIdentificacionTecnico())) {
                                lstItems.add(items);
                                tecnicoSelected = reAgendaMgl.getIdentificacionTecnico();
                            }else{
                                lstItems.add(items);
                            }
                        }
                        tecnicosAnticipados = lstItems;
                        retornaTecnicosDisponibles();
                        listarFranjas();          
                    } else {
                        tecnicosAnticipados = response.getItems();
                        retornaTecnicosDisponibles();
                    }
                } else {
                    tecnicosDisponibles=null;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "El servicio de tecnicos anticipados no retorna informacion "
                                    + "de tecnicos disponibles", null));
                }
            } else {
                tecnicosDisponibles=null;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Debe seleccionar una fecha para la consulta", null));
            }
        } catch (ApplicationException | IOException e) {
            FacesUtil.mostrarMensajeError("Se genera error consultando los tecnicos"
                    + "  en CmtAgendamientoMglBean: consultarTecnicos()" + e.getMessage(), e, LOGGER);
        }

    }
    
    public void cerrarPanelTecnicos() {

        consulta = true;
        reagendar = false;
        reagendarTecnicoAnt = false;
        cancelar = false;
        crearAgenda = false;
        updateAgenda = false;
        this.capacidad = null;
        agenda = new CmtAgendamientoMgl();
        verPanelTecnicos=false;
        reasignar = false;
        reAgendaMgl = new CmtAgendamientoMgl();

    }
    
    
    public void validacionDisponibilidad(
            DisponibilidadTecnicoDto disponibilidadTecnicoDto, int parWorktimeNum, Items item) {

        int tothFin = 0;
        int tothIni = 0;
        List<String> franjasDis = null;

        //validamos fechas
        if (disponibilidadTecnicoDto.getFechaFranjaDtos() != null
                && !disponibilidadTecnicoDto.getFechaFranjaDtos().isEmpty()) {

            for (FechaFranjaDto fechaFranjaDto : disponibilidadTecnicoDto.getFechaFranjaDtos()) {
                if (fechaFranjaDto.getFranjasDisponiblesBloques() != null
                        && !fechaFranjaDto.getFranjasDisponiblesBloques().isEmpty()) {
                    
                    franjasDis = new ArrayList<>();
                    
                    for (String bloque : fechaFranjaDto.getFranjasDisponiblesBloques()) {
                        bloque = bloque.replace(" ", "");
                        if (bloque.contains(",")) {
                           
                            String[] part = bloque.split(",");
                            String horIni = part[0].replace("[", "");
                            String[] partHorIni = horIni.split(":");
                            if (isNumeric(partHorIni[0])) {
                                int hIni = Integer.parseInt(partHorIni[0]);
                                int hIniBy60 = hIni * 60;
                                if (isNumeric(partHorIni[1])) {
                                    int hIniMin = Integer.parseInt(partHorIni[1]);
                                    tothIni = hIniBy60 + hIniMin;
                                }

                                String horFin = part[1].replace("]", "");
                                String[] partHorFin = horFin.split(":");
                                if (isNumeric(partHorFin[0])) {
                                    int hFin = Integer.parseInt(partHorFin[0]);

                                    int hFinBy60 = hFin * 60;
                                    if (isNumeric(partHorFin[1])) {
                                        int hFinMin = Integer.parseInt(partHorFin[1]);
                                        tothFin = hFinBy60 + hFinMin;
                                    }
                                }
                                if (tothFin != 0 && tothIni != 0) {
                                    int res = tothFin - tothIni;
                                    if (res > parWorktimeNum) {
                                        franjasDis.add(bloque);
                                    }

                                }
                            }

                        }
                    }
                }
            }
           if(franjasDis != null && !franjasDis.isEmpty()){
               item.setFranjasDisponibles(franjasDis);
               tecnicosDisponibles.add(item);
           } 
        }

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
     
     public boolean isNumericLong(String cadena) {
        try {
            Long.parseLong(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            LOGGER.error("Error en isNumeric. " + nfe.getMessage(), nfe);
            return false;
        } catch (Exception nfe) {
            LOGGER.error("Error en isNumeric. " + nfe.getMessage(), nfe);
            return false;
        }
    } 
     
    public void retornaTecnicosDisponibles() {

        List<String> franjasBloques;
        DisponibilidadTecnicoDto disponibilidadTecnicoDto;
        List<FechaFranjaDto> listFechFranjas;
        FechaFranjaDto fechaFranjaDto;

        if (tecnicosAnticipados != null && !tecnicosAnticipados.isEmpty()) {

            tecnicosDisponibles = new ArrayList<>();

            for (Items item : tecnicosAnticipados) {
                if (item.getSchedules() != null && !item.getSchedules().isEmpty()) {
                    Iterator<Entry<String, Object>> iterator
                            = item.getSchedules().entrySet().iterator();
                    disponibilidadTecnicoDto = new DisponibilidadTecnicoDto();
                    listFechFranjas = new ArrayList<>();
                    disponibilidadTecnicoDto.setTecnico(item.getResource());

                    while (iterator.hasNext()) {

                        franjasBloques = new ArrayList<>();
                        fechaFranjaDto = new FechaFranjaDto();
                        Entry<String, Object> n = iterator.next();
                        fechaFranjaDto.setFechaDisponible(n.getKey());
                        LinkedHashMap< String, ArrayList> map
                                = (LinkedHashMap<String, ArrayList>) n.getValue();

                        for (ArrayList value : map.values()) {
                            if (value != null && !value.isEmpty()) {
                                ArrayList fr = value;
                                if (!fr.isEmpty()) {
                                    for (int i = 0; i < fr.size(); i++) {
                                        ArrayList fs = (ArrayList) fr.get(i);
                                        if (!fs.isEmpty()) {
                                            franjasBloques.add(fs.toString());
                                        }
                                    }

                                }

                            }
                            fechaFranjaDto.setFranjasDisponiblesBloques(franjasBloques);
                            listFechFranjas.add(fechaFranjaDto);
                        }

                    }
                    disponibilidadTecnicoDto.setFechaFranjaDtos(listFechFranjas);
                    validacionDisponibilidad(disponibilidadTecnicoDto, parWorktimeNum, item);

                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "El servicio no devolvio tecnicos disponibles", null));
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
    
    public void listarFranjas() {

        if (tecnicoSelected != null && !tecnicoSelected.isEmpty()) {
            for (Items item : tecnicosDisponibles) {
                Resource resource = item.getResource();
                if (tecnicoSelected.equalsIgnoreCase(resource.getResourceId())) {
                    lstFranjas = item.getFranjasDisponibles();
                    itemSelected = item;
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Debe selecionar un tecnico", null));
        }
    }
    
    public boolean validaHoraInicio() {

        boolean respuesta = false;

        //Busco la referencia de la hora inicial
        franjaSelected = franjaSelected.replace(" ", "");
        int tothIniFranja = 0;
        int tothFinFranja = 0;

        if (franjaSelected.contains(",")) {

            String[] part = franjaSelected.split(",");
            String horIni = part[0].replace("[", "");
            String[] partHorIni = horIni.split(":");

            if (isNumeric(partHorIni[0])) {
                int hIni = Integer.parseInt(partHorIni[0]);
                int hIniBy60 = hIni * 60;
                if (isNumeric(partHorIni[1])) {
                    int hIniMin = Integer.parseInt(partHorIni[1]);
                    tothIniFranja = hIniBy60 + hIniMin;
                }

                String horFin = part[1].replace("]", "");
                String[] partHorFin = horFin.split(":");
                if (isNumeric(partHorFin[0])) {
                    int hFin = Integer.parseInt(partHorFin[0]);

                    int hFinBy60 = hFin * 60;
                    if (isNumeric(partHorFin[1])) {
                        int hFinMin = Integer.parseInt(partHorFin[1]);
                        tothFinFranja = hFinBy60 + hFinMin;
                    }
                }
            }

        }

        if (horaInicio.contains(":")) {

            String[] partHorIni = horaInicio.split(":");
            int tothIniDig = 0;
            if (isNumeric(partHorIni[0])) {
                int hIni = Integer.parseInt(partHorIni[0]);
                int hIniBy60 = hIni * 60;
                if (isNumeric(partHorIni[1])) {
                    int hIniMin = Integer.parseInt(partHorIni[1]);
                    tothIniDig = hIniBy60 + hIniMin;
                }

                if (tothIniDig >= tothIniFranja) {
                    if (tothIniDig <= tothFinFranja) {
                        //Sumamos el timeWork a la hora inicio digitada
                        int sumaHorIniTimeW = tothIniDig + parWorktimeNum;
                        if (sumaHorIniTimeW <= tothFinFranja) {
                            respuesta = true;
                        } else {
                            respuesta = false;
                        }
                    } else {
                        respuesta = false;
                    }
                } else {
                    respuesta = false;
                }

            }

        }
        return respuesta;
    }
    
    public boolean validoFechaHoraAgendas(List<CmtAgendamientoMgl> agendaList) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        if (agendaList != null && !agendaList.isEmpty()) {
            for (CmtAgendamientoMgl agendaCom : agendaList) {
                if (agendaCom.getHoraInicio() != null) {
                    if (agendaCom.getFechaReagenda() != null) {
                        String fechAge = df.format(agendaCom.getFechaReagenda());
                        if (fechAge.equalsIgnoreCase(fechaSelected)
                                && agendaCom.getHoraInicio().equalsIgnoreCase(horaInicio)) {
                            agendaIgualFechaHora = agendaCom;
                            return false;
                        }
                    } else {
                        String fechAge = df.format(agendaCom.getFechaAgenda());
                        if (fechAge.equalsIgnoreCase(fechaSelected)
                                && agendaCom.getHoraInicio().equalsIgnoreCase(horaInicio)) {
                            agendaIgualFechaHora = agendaCom;
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    
    public boolean validarCedulaTecnico(String cedula) {

        boolean cedulaValida;
        long  cc;
        if (isNumericLong(cedula)) {
            cc = Long.parseLong(cedula);
            if (cc != 0) {
                cedulaValida = true;
            } else {
                cedulaValida = false;
            }
        } else {
            cedulaValida = false;
        }
        return cedulaValida;
    }
    
    public void seleccionarHoras() {

        //Busco la referencia de la hora inicial
        franjaSelected = franjaSelected.replace(" ", "");
        horaSelected ="";
        minSelected="";
        lstMin = new ArrayList<>();
        
        if (franjaSelected.contains(",")) {

            String[] part = franjaSelected.split(",");
            String horIni = part[0].replace("[", "");
            String[] partHorIni = horIni.split(":");

            if (isNumeric(partHorIni[0])) {
                int hIni = Integer.parseInt(partHorIni[0]);

                String horFin = part[1].replace("]", "");
                String[] partHorFin = horFin.split(":");
                if (isNumeric(partHorFin[0])) {
                    int hFin = Integer.parseInt(partHorFin[0]);
                    lstHoras = new ArrayList<>();
                    while (hIni <= hFin) {
                        if (String.valueOf(hIni).length() == 1) {
                            lstHoras.add("0" + String.valueOf(hIni));
                        } else {
                            lstHoras.add(String.valueOf(hIni));
                        }
                        hIni++;
                    }

                }
            }

        }
    }
    
    public void seleccionarMin() {
        lstMin = new ArrayList<>();
        int ini = 0;
        while (ini < 60) {
            String tamaño = String.valueOf(ini);
            if (tamaño.length() == 1) {
                lstMin.add("0" + String.valueOf(ini));
            } else {
                lstMin.add(String.valueOf(ini));
            }
            ini++;
        }
        horaInicio = horaSelected + ":" + minSelected;
    }
    
    public void organizarHorasMin() {
        horaInicio = horaSelected + ":" + minSelected;
    }
    
    public void abrirAgendaInmediaraPreIframe() throws ApplicationException {
        if (ot != null) {
            if (ot.getOnyxOtHija() != null) {
                abrirPanelIframe = false;
                agendaInmediataPreIframe = true;
                consulta = false;

            } else {
                List<OnyxOtCmDir> listaOnyx = onyxOtCmDirlFacadeLocal.findOnyxOtCmDirById(ot.getIdOt());
                if (listaOnyx != null && !listaOnyx.isEmpty()) {
                    abrirPanelIframe = false;
                    agendaInmediataPreIframe = true;
                    consulta = false;
                } else {
                    if (ot.getTipoOtObj() != null
                            && ot.getTipoOtObj().getRequiereOnyx()
                            != null && ot.getTipoOtObj().
                                    getRequiereOnyx().equalsIgnoreCase("Y")) {
                        mensajesValidacion = "Debe ir a la pestaña de Onix y diligenciar la OT hija para agendar.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        mensajesValidacion, null));
                    } else {
                        abrirPanelIframe = false;
                        agendaInmediataPreIframe = true;
                        consulta = false;
                    }
                }
            }
        }

        
    }
    
    public void abrirIframe() throws ApplicationException {
        
        //Validación de Ultima Agenda Cerrada
        if (validarCreacion()) {
        
            if (ot.getPersonaRecVt() != null && !ot.getPersonaRecVt().isEmpty()
                    && ot.getTelPerRecVt() != null && !ot.getTelPerRecVt().isEmpty()
                    && ot.getEmailPerRecVT() != null && !ot.getEmailPerRecVT().isEmpty()) {

                CmtOrdenTrabajoMgl otActual = ordenTrabajoMglFacadeLocal.findOtById(ot.getIdOt());
                otActual.setPersonaRecVt(ot.getPersonaRecVt());
                otActual.setTelPerRecVt(ot.getTelPerRecVt());
                otActual.setEmailPerRecVT(ot.getEmailPerRecVT());
                //actualiza los datos de la persona que recibe la visita
                ordenTrabajoMglFacadeLocal.actualizarOtCcmm(otActual, usuarioVt, perfilVt);

            } else {
                String msg = "Todos los datos de la persona que recibe la visita son obligatorios.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msg, null));
                return;
            } 
            
         

        abrirPanelIframe = true;
        consulta = false;
        RequestAbrirIframe request = new RequestAbrirIframe();

        String appNumber = agendamientoFacade.armarNumeroOtOfsc(ot);
        String appNumberEncriptado = CmtUtilidadesAgenda.EncriptarAES256(appNumber);

        CmtCuentaMatrizMgl cuenta = ot.getCmObj();
        request.setCiudad(cuenta.getCentroPoblado().getGpoNombre());

        ParametrosMgl urlAtencionInm = parametrosMglFacadeLocal.findParametroMgl(Constant.BASE_URI_ATENCION_INMEDIATA);

        if (urlAtencionInm == null) {
            String msg = "No se encuentra configurado el parámetro " + Constant.BASE_URI_ATENCION_INMEDIATA;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, null));
            abrirPanelIframe = false;
            consulta = true;
            listInfoByPage(1);
            return;
        }

        try {
            //Validamos disponibilidad del servicio
            URL urlCon = new URL(urlAtencionInm.getParValor());

            //Fin Validacion disponibilidad del servicio

            CmtDireccionMgl direccionCmt = cuenta.getDireccionPrincipal();

            if (direccionCmt != null) {
                DireccionMgl direccion = direccionCmt.getDireccionObj();
                if (direccion != null && direccion.getUbicacion() != null) {
                    UbicacionMgl ubicacionMgl = direccion.getUbicacion();
                    double lat = Double.parseDouble(ubicacionMgl.getUbiLatitud());
                    double lon = Double.parseDouble(ubicacionMgl.getUbiLongitud());
                    request.setLatitude(lat);
                    request.setLongitude(lon);
                }
            }
            request.setNode(nodoMgl.getNodCodigo());

            CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = cmtEstadoxFlujoMglFacadeLocal.
                    findByTipoFlujoAndEstadoInt(ot.getTipoOtObj().getTipoFlujoOt(),
                            ot.getEstadoInternoObj(), ot.getBasicaIdTecnologia());
            if (cmtEstadoxFlujoMgl.getEstadoxFlujoId() != null) {
                if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                    String subTipoWork = cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC().getNombreBasica();
                    request.setXA_WorkOrderSubtype(subTipoWork);
                }

            }

            CmtExtendidaTecnologiaMgl mglExtendidaTecnologia
                    = extendidaTecnologiaMglFacadeLocal.findBytipoTecnologiaObj(ot.getBasicaIdTecnologia());

            if (mglExtendidaTecnologia != null
                    && mglExtendidaTecnologia.getIdExtTec() != null) {
                if (mglExtendidaTecnologia.getNomTecnologiaOFSC() != null) {
                    String nombreRed = mglExtendidaTecnologia.getNomTecnologiaOFSC().getAbreviatura();
                    request.setXA_Red(nombreRed);
                }
            }
            String tipoWork = ot.getBasicaIdTipoTrabajo().getAbreviatura();
            request.setActivityType(tipoWork);
            
            ParametrosMgl canalAgendaInmediata = parametrosMglFacadeLocal.findParametroMgl(Constant.CANAL_AGENDA_INMEDIATA);   
            ParametrosMgl tiempoDuracion = parametrosMglFacadeLocal.findParametroMgl(Constant.TIME_OUT_AGENDA_INMEDIATA_IFRAME);  
            request.setCanal(canalAgendaInmediata.getParValor());
            request.setDuration(tiempoDuracion.getParValor());

            String js = JSONUtils.objetoToJson(request);

            String jsEncriptado = CmtUtilidadesAgenda.EncriptarAES256(js);

            urlIframe = urlAtencionInm.getParValor() + "apptNumber=" + appNumberEncriptado + "&request=" + jsEncriptado + "";
        } catch (ApplicationException | IOException ex) {
            String msg = ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, null));
            abrirPanelIframe = false;
            consulta = true;
            listInfoByPage(1);
        }

        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Ya se encuentra una última agenda cerrada no se pueden crear más agendas ", null));
            listInfoByPage(1);
        }

    }
    
    public void cerrarIframe(){
        abrirPanelIframe=false;
        consulta = true;
        agendaInmediataPreIframe = false;
        listInfoByPage(1);
    }
    
    public void crearAgendaPendiente(RequestAgendaInmediataMgl requestAgendaInmediataMgl) {

        if (validarCreacion()) {
            CapacidadAgendaDto capacidadAgendaDto = new CapacidadAgendaDto();
            capacidadAgendaDto.setDate(requestAgendaInmediataMgl.getFechaCreacion());

            String franjaIni = requestAgendaInmediataMgl.getHoraInicio().substring(0, 2);
            String franjaFin = requestAgendaInmediataMgl.getHoraFin().substring(0, 2);
            String timeSlot = franjaIni + "-" + franjaFin;
            capacidadAgendaDto.setTimeSlot(timeSlot);
            capacidadAgendaDto.setNumeroOrdenInmediata(requestAgendaInmediataMgl.getNumeroOrden());
            capacidadAgendaDtos = new ArrayList<>();
            capacidadAgendaDtos.add(capacidadAgendaDto);
            crearAgendas(true);
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Ya se encuentra una ultima agenda cerrada no se pueden crear más agendas ", null));
            listInfoByPage(1);
            regresar();
        }

    }

    public void abrirAgendaInmediata() {
        abrirPanelIframe = false;
        agendaInmediataPreIframe = true;
        consulta = false;

    }
    
       public void cerrarAgendaInmediata() {
        abrirPanelIframe = false;
        consulta = true;
        agendaInmediataPreIframe = false;
        listInfoByPage(1);
    }
       
       public boolean validaFechaAgendamiento(CmtAgendamientoMgl agenda){
           boolean respuesta = false;
           Date fechaHoy = new Date();
           SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
           String fechaAgenda = format.format(agenda.getFechaAgenda());
            
            //Si la agenda tiene una fecha de reagenda toma esa fecha para hacer validacion
            if(agenda.getFechaReagenda() != null){
               fechaAgenda = format.format(agenda.getFechaReagenda());
            }
            
            String fechaActual = format.format(fechaHoy);             
            try {
                Date fechaDate1  = format.parse(fechaAgenda);
                Date fechaDate2 = format.parse(fechaActual);
                
                if (fechaDate1.before(fechaDate2)) {
                    respuesta = true;
                }                
            } catch (ParseException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
           return respuesta;
       }

    public List<CapacidadAgendaDto> getCapacidadAgendaDtos() {
        return capacidadAgendaDtos;
    }

    public void setCapacidadAgendaDtos(List<CapacidadAgendaDto> capacidadAgendaDtos) {
        this.capacidadAgendaDtos = capacidadAgendaDtos;
    }

    public List<Items> getTecnicosAnticipados() {
        return tecnicosAnticipados;
    }

    public void setTecnicosAnticipados(List<Items> tecnicosAnticipados) {
        this.tecnicosAnticipados = tecnicosAnticipados;
    }

    public boolean isReasignar() {
        return reasignar;
    }

    public void setReasignar(boolean reasignar) {
        this.reasignar = reasignar;
    }

    public CmtAgendamientoMgl getReasignaAgenda() {
        return reasignaAgenda;
    }

    public void setReasignaAgenda(CmtAgendamientoMgl reasignaAgenda) {
        this.reasignaAgenda = reasignaAgenda;
    }

    public String getRazonReasigna() {
        return razonReasigna;
    }

    public void setRazonReasigna(String razonReasigna) {
        this.razonReasigna = razonReasigna;
    }

    public String getTecnicoSelected() {
        return tecnicoSelected;
    }

    public void setTecnicoSelected(String tecnicoSelected) {
        this.tecnicoSelected = tecnicoSelected;
    }
   
    public boolean isIsRequest() {
        return isRequest;
    }

    public void setIsRequest(boolean isRequest) {
        this.isRequest = isRequest;
    }

    public boolean isVerCapacity() {
        return verCapacity;
    }

    public void setVerCapacity(boolean verCapacity) {
        this.verCapacity = verCapacity;
    }
    
    public List<Items> getTecnicosAnticipadosReasignar() {
        return tecnicosAnticipadosReasignar;
    }

    public void setTecnicosAnticipadosReasignar(List<Items> tecnicosAnticipadosReasignar) {
        this.tecnicosAnticipadosReasignar = tecnicosAnticipadosReasignar;
    }

    public List<String> getFechasAgendas() {
        return fechasAgendas;
    }

    public void setFechasAgendas(List<String> fechasAgendas) {
        this.fechasAgendas = fechasAgendas;
    }

    public String getFechaSelected() {
        return fechaSelected;
    }

    public void setFechaSelected(String fechaSelected) {
        this.fechaSelected = fechaSelected;
    }

    public List<String> getLstFranjas() {
        return lstFranjas;
    }

    public void setLstFranjas(List<String> lstFranjas) {
        this.lstFranjas = lstFranjas;
    }

    public String getFranjaSelected() {
        return franjaSelected;
    }

    public void setFranjaSelected(String franjaSelected) {
        this.franjaSelected = franjaSelected;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public List<Items> getTecnicosDisponibles() {
        return tecnicosDisponibles;
    }

    public void setTecnicosDisponibles(List<Items> tecnicosDisponibles) {
        this.tecnicosDisponibles = tecnicosDisponibles;
    }

    public int getParWorktimeNum() {
        return parWorktimeNum;
    }

    public void setParWorktimeNum(int parWorktimeNum) {
        this.parWorktimeNum = parWorktimeNum;
    }

    public boolean isReagendarTecnicoAnt() {
        return reagendarTecnicoAnt;
    }

    public void setReagendarTecnicoAnt(boolean reagendarTecnicoAnt) {
        this.reagendarTecnicoAnt = reagendarTecnicoAnt;
    }

    public String getHoraSelected() {
        return horaSelected;
    }

    public void setHoraSelected(String horaSelected) {
        this.horaSelected = horaSelected;
    }

    public List<String> getLstHoras() {
        return lstHoras;
    }

    public void setLstHoras(List<String> lstHoras) {
        this.lstHoras = lstHoras;
    }

    public String getMinSelected() {
        return minSelected;
    }

    public void setMinSelected(String minSelected) {
        this.minSelected = minSelected;
    }

    public List<String> getLstMin() {
        return lstMin;
    }

    public void setLstMin(List<String> lstMin) {
        this.lstMin = lstMin;
    }
    
    public boolean isConveniencia() {
        return conveniencia;
    }

    public void setConveniencia(boolean conveniencia) {
        this.conveniencia = conveniencia;
    }

    public String getConvenienciaCheck() {
        return convenienciaCheck;
    }

    public void setConvenienciaCheck(String convenienciaCheck) {
        this.convenienciaCheck = convenienciaCheck;
    }

    public boolean isAgendaInmediata() {
        return agendaInmediata;
    }

    public void setAgendaInmediata(boolean agendaInmediata) {
        this.agendaInmediata = agendaInmediata;
    }

    public boolean isAbrirPanelIframe() {
        return abrirPanelIframe;
    }

    public void setAbrirPanelIframe(boolean abrirPanelIframe) {
        this.abrirPanelIframe = abrirPanelIframe;
    }

    public String getUrlIframe() {
        return urlIframe;
    }

    public void setUrlIframe(String urlIframe) {
        this.urlIframe = urlIframe;
    }

    public boolean isAgendaInmediataPreIframe() {
        return agendaInmediataPreIframe;
    }

    public void setAgendaInmediataPreIframe(boolean agendaInmediataPreIframe) {
        this.agendaInmediataPreIframe = agendaInmediataPreIframe;
    }
    
    
}
