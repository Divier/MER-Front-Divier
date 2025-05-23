/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.otHhpp;

import co.com.claro.atencionInmediata.agenda.request.RequestAbrirIframe;
import co.com.claro.cmas400.ejb.respons.ResponseDataOtEdificios;
import co.com.claro.cmas400.ejb.respons.ResponseOtEdificiosList;
import co.com.claro.mer.exception.ExceptionServBean;
import co.com.claro.mer.utils.DateToolUtils;
import co.com.claro.mer.utils.enums.MessageSeverityEnum;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.client.https.ConsumoGenerico;
import co.com.claro.mgl.dtos.CapacidadAgendaDto;
import co.com.claro.mgl.dtos.CmtOnyxResponseDto;
import co.com.claro.mgl.dtos.DisponibilidadTecnicoDto;
import co.com.claro.mgl.dtos.FechaFranjaDto;
import co.com.claro.mgl.dtos.RestriccionAgendaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.AgendamientoHhppMglFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.OnyxOtCmDirlFacadeLocal;
import co.com.claro.mgl.facade.OtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.OtHhppTecnologiaMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtArchivosVtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCuentaMatrizMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtExtendidaTecnologiaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtExtendidaTipoTrabajoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtHorarioRestriccionMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOrdenTrabajoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccion;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.OnyxOtCmDir;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.OtHhppTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.RequestAgendaInmediataMgl;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.UbicacionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtArchivosVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTipoTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
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
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Orlando Velasquez
 */
@ViewScoped
@ManagedBean(name = "agendamientoOtHhppBean")
public class AgendamientoOtHhppBean {

    private static final String AGENDAMIENTO_OT_HHPP = "Agendamiento OT HHPP";
    private static final String CAPACITY_ERROR = "Ocurrió un error al consultar el capacity: ";
    private SecurityLogin securityLogin;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response
            = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    @Getter
    @Setter
    private String usuarioVT = null;
    @Getter
    @Setter
    private int perfilVt = 0;
    private static final Logger LOGGER = LogManager.getLogger(AgendamientoOtHhppBean.class);
    @Getter
    @Setter
    private String selectedTab = "AGENDAMIENTO";
    @Getter
    @Setter
    private List<MglAgendaDireccion> agendaList;
    @Getter
    @Setter
    private List<MglAgendaDireccion> agendasTotal;
    @Getter
    @Setter
    private MglAgendaDireccion agenda = new MglAgendaDireccion();
    @Getter
    @Setter
    private OtHhppMgl otHhppSeleccionado;
    @Getter
    @Setter
    private List<CmtBasicaMgl> prerequisitosVt = null;
    @Getter
    @Setter
    private String[] idBasicaPrerequisito;
    @Getter
    @Setter
    private List<MglAgendaDireccion> updateAgendaMgl = new ArrayList<>();
    private MglAgendaDireccion reAgendaMgl = new MglAgendaDireccion();
    private UsuariosServicesDTO usuarioSesion = new UsuariosServicesDTO();
    @Getter
    @Setter
    private List<CapacidadAgendaDto> capacidad = new ArrayList<>();
    @Getter
    @Setter
    private List<String> nombresArchivos;
    private int actual;
    @Getter
    @Setter
    private String numPagina = "1";
    @Getter
    @Setter
    private List<Integer> paginaList;
    @Getter
    @Setter
    private boolean reagendar = false;
    @Getter
    @Setter
    private boolean crearAgenda = false;
    @Getter
    @Setter
    private boolean updateAgenda = false;
    @Getter
    @Setter
    private boolean cancelar = false;
    @Getter
    @Setter
    private boolean consulta = true;
    @Getter
    @Setter
    private boolean reasignar = false;
    private String pageActual;
    @Getter
    @Setter
    private List<RestriccionAgendaDto> restricciones;
    @Getter
    @Setter
    private String estiloObligatorio = "<font color='red'>*</font>";
    @Getter
    @Setter
    private String razonReagendamiento;
    @Getter
    @Setter
    private String comentariosOrden;
    @Getter
    @Setter
    private MglAgendaDireccion actualizaAgenda = new MglAgendaDireccion();
    @Getter
    @Setter
    private List<MglAgendaDireccion> cancelaAgendaMgl = new ArrayList<>();
    @Getter
    @Setter
    private String razonCancela;
    private static final String OPCION_CREAR = "CREAR";
    private static final String OPCION_EDITAR_INI = "EDITAR_INICIADA";
    private static final String OPCION_EDITAR = "EDITAR";
    private static final String OPCION_REAGENDAR = "REAGENDAR";
    private static final String FORMULARIO = "AGENDAMIENTOSOT";
    private static final String OPCION_CREAR_ANTICIPADO = "CREAR_ANTICIPADO";
    private String mensajesValidacion;
    private String mensajesValidacionFinal;
    @Getter
    @Setter
    private boolean regresarVista;
    private HhppMglManager hhppMglManager = new HhppMglManager();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private boolean activacionUCM;
    private TipoOtHhppMgl tipoOtHhppMgl;
    private String codCuentaPar;
    @Getter
    @Setter
    private boolean tecnicoAnticipado;
    @Getter
    @Setter
    private boolean agendaInmediata;
    private NodoMgl nodoMgl;
    @Getter
    @Setter
    private List<Items> tecnicosAnticipados;
    @Getter
    @Setter
    private String razonReasigna;
    @Getter
    @Setter
    private List<MglAgendaDireccion> reasignaAgendaMgl = new ArrayList<>();
    @Getter
    @Setter
    private MglAgendaDireccion reasignaAgenda = new MglAgendaDireccion();
    @Getter
    @Setter
    private List<CapacidadAgendaDto> capacidadAgendaDtos;
    @Getter
    @Setter
    private String tecnicoSelected;
    @Getter
    @Setter
    private boolean verPanelTecnicos;
    @Getter
    @Setter
    private boolean verCapacity = true;
    @Getter
    @Setter
    private String fechaSelected;
    @Getter
    @Setter
    private List<String> fechasAgendas;
    @Getter
    @Setter
    private List<Items> tecnicosDisponibles;
    @Getter
    @Setter
    private String franjaSelected;
    @Getter
    @Setter
    private List<String> lstFranjas;
    @Getter
    @Setter
    private String horaInicio;
    @Getter
    @Setter
    private int parWorktimeNum;
    private final String EXPRESION_HORAS="^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
    private Items itemSelected;
    private MglAgendaDireccion agendaIgualFechaHora;
    @Getter
    @Setter
    private boolean reagendarTecnicoAnt;
    @Getter
    @Setter
    private String horaSelected;
    @Getter
    @Setter
    private List<String> lstHoras;
    @Getter
    @Setter
    private String minSelected;
    @Getter
    @Setter
    private List<String> lstMin;
    @Getter
    @Setter
    private MglScheduleComponetDto mglScheduleComponetDto;
    private MglScheduleComponetBusiness mglScheduleComponetBusin
            = new MglScheduleComponetBusiness();
    @Getter
    @Setter
    private String urlArchivoSoporte;
    @Getter
    @Setter
    private boolean conveniencia;
    @Getter
    private String convenienciaCheck;
    @Getter
    @Setter
    private boolean abrirPanelIframe;
    @Getter
    @Setter
    private String urlIframe;
    @Getter
    @Setter
    private boolean agendaInmediataPreIframe;
    private boolean actualmente = false;

    /**
     * Número máximo de caracteres permitido para la
     * construcción del número de OT de RR.
     */
    private final int MAX_CARACTERES_NUMERO_OT_RR = 5;
    /**
     * Número máximo de caracteres permitido para la
     * construcción del estado de la OT.
     */
    private final int MAX_CARACTERES_ESTADO_OT = 6;
    @Getter
    @Setter
    private boolean isRequest;
    private String json;
    private String[] partesMensaje;
    private static final String TABAGENDAMIENTOEDITOROTHHPP = "TABAGENDAMIENTOEDITOROTHHPP";
    

    @EJB
    private CmtOpcionesRolMglFacadeLocal opcionesFacade;
    @EJB
    private AgendamientoHhppMglFacadeLocal agendamientoHhppFacade;
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal tipoBasicaMglFacadeLocal;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private CmtHorarioRestriccionMglFacadeLocal restriccionMglFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    @EJB
    private CmtArchivosVtMglFacadeLocal cmtArchivosVtMglFacadeLocal;
    @EJB
    private CmtOrdenTrabajoMglFacadeLocal ordenTrabajoMglFacadeLocal;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cuentaMatrizMglFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private OtHhppTecnologiaMglFacadeLocal otHhppTecnologiaMglFacadeLocal;
    @EJB
    private  CmtExtendidaTipoTrabajoMglFacadeLocal  extendidaTipoTrabajoMglFacadeLocal;
    @EJB
    private OtHhppMglFacadeLocal otHhppMglFacadeLocal;
    @EJB
    private OnyxOtCmDirlFacadeLocal onyxOtCmDirlFacadeLocal;
    @EJB
    private CmtExtendidaTecnologiaMglFacadeLocal extendidaTecnologiaMglFacadeLocal;
    @Inject
    private ExceptionServBean exceptionServBean;

    /* -------------------------------------------------- */
    public AgendamientoOtHhppBean() {

        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();

            if (usuarioVT == null) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en AgendamientoOtHhppBean. Validando autenticacion: "+e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en AgendamientoOtHhppBean. Validando autenticacion: "+e.getMessage(), e, LOGGER);
        }

    }

    @PostConstruct
    public void init() {

        try {
            regresarVista = false;
            cargarOtHhppSeleccionada();
            listInfoByPage(1);
            usuarioSesion = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
            agendamientoHhppFacade.setUser(usuarioVT, perfilVt);
            if (session != null && session.getAttribute("activaUCM") != null) {
                activacionUCM = (boolean) session.getAttribute("activaUCM");
                session.removeAttribute("activaUCM");
            }
            tecnicoAnticipado = validarTecnicoAnticipado(otHhppSeleccionado);
            agendaInmediata = validarAgendaInmediata(otHhppSeleccionado);
            if (activacionUCM && agendaList != null && !agendaList.isEmpty()) {
                mostrarDocumentosAdjuntos(agendaList);
                listInfoByPage(1);
            }
     
        } catch (ApplicationException e) {
            LOGGER.error("Error al realizar cargue inicial de la pantalla"
                    + " ", e);
            String msnError = "Error al hacer cargue inicial de la página. ";
            exceptionServBean.notifyError(e, msnError + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        } catch (Exception e) {
            LOGGER.error("Error al realizar cargue inicial de la pantalla"
                    + " ", e);
            String msnError = "Error al hacer cargue inicial de la página: ";
            exceptionServBean.notifyError(e, msnError + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        }

    }

    /**
     * Método para validar si el usuario tiene permisos para crear Agenda Tradicional
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolCrearAgendaTradicional() {
        return isRol("CREARAGENDATRAD");
    }

    /**
     * Método para validar si el usuario tiene permisos para crear Agenda Anticipada
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolCrearAgendaAnticipado() {
        return isRol("CREARAGENDAANT");
    }

    /**
     * Método para validar si el usuario tiene permisos para crear Agenda inmediata
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolCrearAgendaInmediata() {
        return isRol("CREARAGENDAINMED");
    }

    /**
     * Método para validar si el usuario tiene permisos para reagendar
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolReagendar() {
        return isRol(OPCION_REAGENDAR);
    }

    /**
     * Método para validar si el usuario tiene permisos para actualizar agenda
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolActualizarAgenda() {
        return isRol("ACTUALIZARAGENDA");
    }

    /**
     * Método para validar si el usuario tiene permisos para cancelar Agenda
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolCancelarAgenda() {
        return isRol("CANCELARAGENDA");
    }

    /**
     * Método para validar si el usuario tiene permisos para reasignar agenda
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolReasignarAgenda() {
        return isRol("REASIGNARAGENDA");
    }

    /**
     * Método para validar si el usuario tiene permisos para la opción de agendamiento indicada.
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    private boolean isRol(String nombreOpcion) {
        try {
            return ValidacionUtil.validarVisualizacion(TABAGENDAMIENTOEDITOROTHHPP,
                    nombreOpcion, opcionesFacade, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al verificar si tiene rol de permisos de creación de notas. ", e);
        }

        return false;
    }

    public void cancelarAgenda(MglAgendaDireccion agenda) {
        if (!isRolCancelarAgenda()) {
            FacesUtil.mostrarMensajeWarn("No tiene permisos para cancelar la agenda.");
            return;
        }

        if (agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                .equalsIgnoreCase(Constant.ESTADO_AGENDA_CANCELADA)) {
            mensajesValidacion = "La agenda ya se encuentra cancelada.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, mensajesValidacion);
            return;

        } else if (agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                .equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA)) {
            mensajesValidacion = "La agenda se encuentra cerrada y no se puede cancelar.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, mensajesValidacion);
            return;

        } else if (agenda.getFechaInivioVt() != null) {
            mensajesValidacion = "La agenda ya inicio visita y no se puede cancelar.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, mensajesValidacion);
            return;
        } else {
            //Valida que la agenda seleccionada tenga una fecha inferior a la fecha actual 
            if (validaFechaAgendamiento(agenda)) {
                mensajesValidacion = "No se puede cancelar una agenda con fecha menor a la del dia de hoy.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        mensajesValidacion, null));
                return;
            }else if (actualmente){            
                //Una vez confirmada que la agenda es de hoy hace la validación para mostrar el mensaje popup
                int tiempoAgendaMinutos = tipoOtHhppMgl.getTiempoAgendaOfsc();
                boolean tiempoAntes;
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
                    //Validación de los 30 minutos antes
                    if (tiempoAgendaMinutos > 0) {
                        LocalDateTime fechaActual = LocalDateTime.now(ZoneId.of("America/Bogota"));
                        int horaInicioFranja = Integer.parseInt(timeSlot[0]);
                        if ((fechaActual.getHour() + 1) == horaInicioFranja) {
                            //Obtiene la fecha de capacidad de agenda transformada al tipo objeto requerido.
                            LocalDateTime fechayHoraAgendaHab = DateToolUtils.convertDateToLocalDateTime(agenda.getFechaAgenda());
                            LocalTime horaFranja = LocalTime.of(horaInicioFranja, 0, 0);
                            //Se obtiene la fecha de agenda, modificando su hora y minutos
                            LocalDateTime fechayHoraAgendaMod = fechayHoraAgendaHab.with(horaFranja);
                            //Se resta a la hora de la agenda habilitada, los minutos definidos en el parámetro.
                            fechayHoraAgendaMod = fechayHoraAgendaMod.minusMinutes(tiempoAgendaMinutos);
                            //se valida si la fecha y hora actual es mayor a la fecha de agenda modificada
                            // si se cumple la condición anterior, asigna valor true, indicando que bloquea esos cupos.
                            tiempoAntes = fechaActual.isAfter(fechayHoraAgendaMod);
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
        }

        cancelar = true;
        crearAgenda = false;
        reagendar = false;
        reagendarTecnicoAnt = false;
        consulta = false;
        updateAgenda = false;
        razonCancela = "";
        razonReasigna = "";
        comentariosOrden = "";
        this.cancelaAgendaMgl = new ArrayList<>();
        this.cancelaAgendaMgl.add(agenda);

    }

    public void updateAgenda(MglAgendaDireccion agenda) {
        if (!isRolActualizarAgenda()) {
            FacesUtil.mostrarMensajeWarn("No tiene permisos para actualizar la agenda.");
            return;
        }

        try {
            if (agenda != null) {
                if (agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                        .equalsIgnoreCase(Constant.ESTADO_AGENDA_CANCELADA)) {
                    mensajesValidacion = "La agenda se encuentra cancelada y no se puede editar.";
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, mensajesValidacion);
                    return;

                } else if (agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                        .equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA)) {
                    mensajesValidacion = "La agenda se encuentra cerrada y no se puede editar.";
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, mensajesValidacion);
                    return;
                } else if (agenda.getFechaInivioVt() != null) {
                    if (!validarAccionAgendamiento(OPCION_EDITAR_INI)) {
                        mensajesValidacion = "La agenda ya inicio visita y no se puede editar, "
                                + "solo el rol especial la puede editar.";
                        FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, mensajesValidacion);
                        return;
                    }
                }

                updateAgenda = true;
                cancelar = false;
                crearAgenda = false;
                reagendar = false;
                reagendarTecnicoAnt = false;
                consulta = false;
                reasignar = false;
                razonCancela = "";
                razonReasigna = "";
                comentariosOrden = "";
                this.updateAgendaMgl = new ArrayList<>();
                this.updateAgendaMgl.add(agenda);
                actualizaAgenda = agenda;
                CmtTipoBasicaMgl tipoPrerequisitoVt = tipoBasicaMglFacadeLocal.
                        findByCodigoInternoApp(Constant.TIPO_BASICA_PREREQUISITOS_AGENDA);

                prerequisitosVt = basicaMglFacadeLocal.findByTipoBasica(tipoPrerequisitoVt);
                idBasicaPrerequisito = agenda.getPrerequisitosVT() != null ? agenda.getPrerequisitosVT().split("\\|") : new String[]{};
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Ocurrió un error actualizando el agendamiento", "La agenda es nula."));
            }

        } catch (ApplicationException e) {
            String msgError = "Error al actualizar agenda ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        } catch (Exception e) {
            String msgError = "Ocurrió un error al actualizar agenda ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        }
    }

    public String cancelarAgendaEnOFSC(MglAgendaDireccion agenda) {
        try {
            
            if (razonCancela == null || razonCancela.isEmpty()) {
                mensajesValidacion = "La razon es obligatoria para cancelar.";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, mensajesValidacion);
                return null;
            }
            if (comentariosOrden == null || comentariosOrden.isEmpty()) {
                mensajesValidacion = "El comentario es obligatorio para cancelar.";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, mensajesValidacion);
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
            
            agendamientoHhppFacade.cancelar(agenda, razonCancela,
                    comentariosOrden, usuarioVT, perfilVt);
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
            exceptionServBean.notifyError(e, "Ocurrió un error cancelando el agendamiento: " + msg, AGENDAMIENTO_OT_HHPP);
            String msg1 = "Se produjo un error al momento de ejecutar el método ' "
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + msg;
            LOGGER.error(msg1);
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

            exceptionServBean.notifyError(e, "Ocurrió un error cancelando el agendamiento: " + msg, AGENDAMIENTO_OT_HHPP);
            String msg1 = "Se produjo un error al momento de ejecutar el método ' "
                    + "" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + msg;
            LOGGER.error(msg1);
        }
        return "";
    }

    public void updateAgendaMgl(MglAgendaDireccion agenda) {

        try {
            isRequest = false;

            if (validarAccionAgendamiento(OPCION_EDITAR)) {

                agenda.setPersonaRecVt(actualizaAgenda.getPersonaRecVt());
                agenda.setDocPerRecVt(actualizaAgenda.getDocPerRecVt());
                agenda.setEmailPerRecVT(actualizaAgenda.getEmailPerRecVT() != null ? actualizaAgenda.getEmailPerRecVT() : "");

                if (agenda.getPersonaRecVt() == null || agenda.getPersonaRecVt().equalsIgnoreCase("") || agenda.getPersonaRecVt().isEmpty()) {
                    mensajesValidacion = "El nombre de la persona que recibe la visita es obligatorio.";
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, mensajesValidacion);
                    return;
                }
                if (agenda.getTelPerRecVt() == null) {
                    mensajesValidacion = "El telefono de la persona que recibe la visita es obligatorio.";
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, mensajesValidacion);
                    return;
                }
                if (agenda.getEmailPerRecVT() == null || agenda.getEmailPerRecVT().equalsIgnoreCase("") || agenda.getEmailPerRecVT().isEmpty()) {
                    mensajesValidacion = "El correo de la persona que recibe la visita es obligatorio.";
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, mensajesValidacion);
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
                    agenda.setPrerequisitosVT(prerequisitos);

                    //Actualizo la orden para que tome el nuevo contacto
                    otHhppSeleccionado = otHhppMglFacadeLocal.update(otHhppSeleccionado);

                    agenda = agendamientoHhppFacade.updateAgendasForContacto(agenda, usuarioVT, perfilVt);

                    String msn = "Se ha modificado la agenda:  " + agenda.getOfpsId() + "  "
                            + "  para la ot: " + agenda.getOfpsOtId() + " ";
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.INFO, msn);
                    listInfoByPage(1);
                    regresar();
                } else {
                    String prerequisitos = regresaCadenaTipoPrerequisito(idBasicaPrerequisito);
                    agenda.setPrerequisitosVT(prerequisitos);

                    //Actualizo la orden para que tome el nuevo contacto
                    otHhppSeleccionado = otHhppMglFacadeLocal.update(otHhppSeleccionado);

                    //Consultamos de nuevo la orden para que tome las ultimas actualizaciones
                    OtHhppMgl ot = otHhppMglFacadeLocal.findOtByIdOt(otHhppSeleccionado.getOtHhppId());
                    BigDecimal numOtHija = null;
                    if (ot.getOtHhppId() != null && ot.getOnyxOtHija() != null) {
                        numOtHija = ot.getOnyxOtHija();
                    }

                    CmtOnyxResponseDto cmtOnyxResponseDto = null;
                    if (numOtHija != null) {
                        //Validamos disponibilidad del servicio
                        ParametrosMgl wsdlOtHija
                                = parametrosMglFacadeLocal.findParametroMgl(Constant.WSDL_OT_HIJA_ONIX);

                        if (wsdlOtHija == null) {
                            throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WSDL_OT_HIJA_ONIX);
                        }

                        URL urlCon = new URL(wsdlOtHija.getParValor());

                        ConsumoGenerico.conectionWsdlTest(urlCon, Constant.WSDL_OT_HIJA_ONIX);
                        //Fin Validacion disponibilidad del servicio
                        
                        cmtOnyxResponseDto = ordenTrabajoMglFacadeLocal.consultarOTHijaOnyx(numOtHija.toString());

                    }

                    if (cmtOnyxResponseDto != null) {
                        agenda.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                    }

                    agenda = agendamientoHhppFacade.update(agenda, usuarioVT, perfilVt);

                    String msn = "Se ha modificado la agenda:  " + agenda.getOfpsId() + "  "
                            + "  para la ot: " + agenda.getOfpsOtId() + " ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    msn, ""));
                    listInfoByPage(1);
                    regresar();
                }
            } else {
                mensajesValidacion = "No tiene el rol especifico para esta accion.";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, mensajesValidacion);
            }

        } catch (ApplicationException | IOException e) {
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

            exceptionServBean.notifyError(e, "Error en updateAgendaMgl, al modificar la agenda: " + msg, AGENDAMIENTO_OT_HHPP);
        }
    }

    public void crearAgenda() {
        if (!isRolCrearAgendaTradicional()) {
            FacesUtil.mostrarMensajeWarn("No tiene permisos para crear Agendas. ");
            return;
        }

        try {
            if (agendaList != null) {
                conveniencia = false;
                int agendasActivas = agendamientoHhppFacade.
                        buscarAgendasActivas(otHhppSeleccionado);

                if (agendasActivas == 0) {
                    if (!validarExistenciaAgendaCerrada()) {
                        CmtTipoBasicaMgl tipoPrerequisitoVt = tipoBasicaMglFacadeLocal.
                                findByCodigoInternoApp(Constant.TIPO_BASICA_PREREQUISITOS_AGENDA);
                        prerequisitosVt = basicaMglFacadeLocal.findByTipoBasica(tipoPrerequisitoVt);
                        idBasicaPrerequisito = null;
                        MglAgendaDireccion agendaNew = new MglAgendaDireccion();

                        if (consultarCapacidadWorkForce(agendaNew)) {
                            crearAgenda = true;
                            reagendar = false;
                            cancelar = false;
                            reasignar = false;
                            consulta = false;
                            updateAgenda = false;
                            tecnicoAnticipado = validarTecnicoAnticipado(otHhppSeleccionado);
                            agendaInmediata = validarAgendaInmediata(otHhppSeleccionado);
                            verCapacity=true;
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        "No se puede generar una nueva agenda debido"
                                        + " a que existe una cerrada exitosamente", ""));
                    }
                } else {
                    if (tipoOtHhppMgl.getIsMultiagenda().equalsIgnoreCase("S")) {
                        if (!validarExistenciaAgendaCerrada()) {
                            CmtTipoBasicaMgl tipoPrerequisitoVt = tipoBasicaMglFacadeLocal.
                                    findByCodigoInternoApp(Constant.TIPO_BASICA_PREREQUISITOS_AGENDA);
                            prerequisitosVt = basicaMglFacadeLocal.findByTipoBasica(tipoPrerequisitoVt);
                            idBasicaPrerequisito = null;
                            MglAgendaDireccion agendaNew = new MglAgendaDireccion();

                            if (consultarCapacidadWorkForce(agendaNew)) {
                                crearAgenda = true;
                                reagendar = false;
                                cancelar = false;
                                reasignar = false;
                                consulta = false;
                                updateAgenda = false;
                                tecnicoAnticipado = validarTecnicoAnticipado(otHhppSeleccionado);
                                agendaInmediata = validarAgendaInmediata(otHhppSeleccionado);
                                verCapacity=true;
                            }
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            "No se puede generar una nueva agenda debido"
                                            + " a que existe una cerrada exitosamente", ""));
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "No es posible crear una nueva agenda "
                                        + "debido a que la OT no es multiagenda", ""));
                    }

                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de crear la agenda. EX000: " + e.getMessage(), e);
            exceptionServBean.notifyError(e, "Ocurrió un error consultando la capacidad " + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        } catch (Exception e) {
            LOGGER.error("Error al momento de crear la agenda. EX000: " + e.getMessage(), e);
            exceptionServBean.notifyError(e, "Ocurrió un error consultando la capacidad: " + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        }

    }

    public boolean validarExistenciaAgendaCerrada() throws ApplicationException {
        List<MglAgendaDireccion> agendasAsociadasAlaOt
                = agendamientoHhppFacade.buscarAgendasPorOt(otHhppSeleccionado);

        if (tipoOtHhppMgl.getIsMultiagenda().equalsIgnoreCase("S")) {
            for (MglAgendaDireccion agendaLis : agendasAsociadasAlaOt) {
                if (agendaLis.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                        .equals(Constant.ESTADO_AGENDA_CERRADA)
                        && agendaLis.getUltimaAgenda().equalsIgnoreCase("Y")) {
                    return true;
                }
            }
        } else {
            for (MglAgendaDireccion agendaLis : agendasAsociadasAlaOt) {
                if (agendaLis.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                        .equals(Constant.ESTADO_AGENDA_CERRADA)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void reAgendar(MglAgendaDireccion agenda) throws ApplicationException {
        if (!isRolReagendar()) {
            FacesUtil.mostrarMensajeWarn("No tienes permisos para reagendar");
            return;
        }

        conveniencia = false;
        try {
            if (agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                    .equalsIgnoreCase(Constant.ESTADO_AGENDA_CANCELADA)) {
                mensajesValidacion = "La agenda se encuentra cancelada y no se puede reagendar.";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, mensajesValidacion);
                return;

            } else if (agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                    .equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA)) {
                mensajesValidacion = "La agenda se encuentra cerrada y no se puede reagendar.";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, mensajesValidacion);
                return;
            } else if (agenda.getFechaInivioVt() != null) {
                mensajesValidacion = "La agenda ya inicio visita y no se puede reagendar.";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, mensajesValidacion);
                return;
            } else if (validaFechaAgendamiento(agenda)) {
                    mensajesValidacion = "No es posible reagendar. Fecha de Agenda inferior a la Fecha Actual.";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, mensajesValidacion);
                    return;
                }

            tecnicoAnticipado = validarTecnicoAnticipado(otHhppSeleccionado);

            if (agenda.getTecnicoAnticipado().equalsIgnoreCase("Y")) {
                reAgendaMgl = agendamientoHhppFacade.buscarAgendaPorIdOtWorkforce(agenda.getOfpsId());
                reAgendaMgl.setTimeSlotAntes(agenda.getTimeSlot());
                reagendarTecnicoAnt = true;
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
                lstMin = new ArrayList<>();
                lstHoras = new ArrayList<>();
            } else if (consultarCapacidadWorkForce(agenda)) {
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
                reAgendaMgl = agendamientoHhppFacade.buscarAgendaPorIdOtWorkforce(agenda.getOfpsId());
                reAgendaMgl.setTimeSlotAntes(agenda.getTimeSlot());
            }
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error en reAgendar: " + e.getMessage(), AGENDAMIENTO_OT_HHPP);
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
        agenda = new MglAgendaDireccion();
        reasignar = false;
        verPanelTecnicos=false;
        reAgendaMgl = new MglAgendaDireccion();
    }

    public void consultarDocumentos(MglAgendaDireccion agenda) {
        try {

            nombresArchivos = agendamientoHhppFacade.
                    consultarDocumentos(agenda, usuarioVT, perfilVt);
        } catch (ApplicationException ex) {
            LOGGER.error("Error consultado los documentos adjuntos", ex);
            exceptionServBean.notifyError(ex, "Ocurrió un error al consultar los documentos adjuntos ", AGENDAMIENTO_OT_HHPP);
        } catch (Exception ex) {
            LOGGER.error("Error consultado los documentos adjuntos", ex);
            exceptionServBean.notifyError(ex, "Ocurrió un error al consultar los documentos adjuntos: ", AGENDAMIENTO_OT_HHPP);
        }

    }

    public void mostrarDocumentosAdjuntos(List<MglAgendaDireccion> agendas) {

        for (MglAgendaDireccion agendaOT : agendas) {

            if (agendaOT.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                    .equalsIgnoreCase(Constant.ESTADO_AGENDA_NODONE)
                    || agendaOT.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                            .equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA)) {

                if (agendaOT.getDocAdjuntos() == null) {
                    consultarDocumentos(agendaOT);
                }

            }
        }
    }

    public List<CmtArchivosVtMgl> consultaArchivosAge(MglAgendaDireccion agenda) {

        List<CmtArchivosVtMgl> archivosAge = new ArrayList<>();
        try {
            archivosAge = cmtArchivosVtMglFacadeLocal.findByIdOtHhppAndAge(agenda);
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Ocurrió un error al consultar archivo de la agenda " + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error al consultar archivo de la agenda: " + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        }
        return archivosAge;
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
            exceptionServBean.notifyError(e, "Error en validarAccionAgendamiento. " + Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + " ", AGENDAMIENTO_OT_HHPP);
            return false;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error en validarAccionAgendamiento: " + Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + " ", AGENDAMIENTO_OT_HHPP);
            return false;
        }
    }

    /**
     * Valida si no existe restriccion de horario para agendar la OT en el caso
     * de que el Hhpp se encuentre dentro de una CM
     *
     * @param capacidad capacidad obtenida de WorkForce para validar
     * restricciones
     * @author Orlando Velasquez
     * @throws ApplicationException Excepcion lanzada en la consulta
     */
    private boolean validarRestriccionesCcmm(CapacidadAgendaDto capacidad) {
        try {

            if (capacidad.getTimeSlot() != null && 
                    capacidad.getTimeSlot().equalsIgnoreCase(Constant.DURANTE_EL_DIA)) {
                if (otHhppSeleccionado != null) {
                    HhppMgl hhpp = hhppMglManager.findHhppByDirAndSubDir(otHhppSeleccionado);
                    if (hhpp != null) {
                        if (hhpp.getHhppSubEdificioObj() != null) {
                            CmtSubEdificioMgl subEdificio = hhpp.getHhppSubEdificioObj();
                            CmtCuentaMatrizMgl cuentaMatriz = subEdificio.getCuentaMatrizObj();
                            if (cuentaMatriz != null) {
                                List<CmtHorarioRestriccionMgl> restriccionMgls
                                        = restriccionMglFacadeLocal.findByCuentaMatrizId(cuentaMatriz.getCuentaMatrizId());

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
                        }
                    }
                }
                return true;
            } else if (capacidad.getTimeSlot() != null) {
                if (otHhppSeleccionado != null) {
                    HhppMgl hhpp = hhppMglManager.findHhppByDirAndSubDir(otHhppSeleccionado);
                    if (hhpp != null) {
                        if (hhpp.getHhppSubEdificioObj() != null) {
                            CmtSubEdificioMgl subEdificio = hhpp.getHhppSubEdificioObj();
                            CmtCuentaMatrizMgl cuentaMatriz = subEdificio.getCuentaMatrizObj();
                            if (cuentaMatriz != null) {
                                List<CmtHorarioRestriccionMgl> restriccionMgls
                                        = restriccionMglFacadeLocal.findByCuentaMatrizId(cuentaMatriz.getCuentaMatrizId());

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
                        }
                    }
                }
                return true;
            } else if (capacidad.getHoraInicio() != null) {
                if (otHhppSeleccionado != null) {
                    HhppMgl hhpp = hhppMglManager.findHhppByDirAndSubDir(otHhppSeleccionado);
                    if (hhpp != null) {
                        if (hhpp.getHhppSubEdificioObj() != null) {
                            CmtSubEdificioMgl subEdificio = hhpp.getHhppSubEdificioObj();
                            CmtCuentaMatrizMgl cuentaMatriz = subEdificio.getCuentaMatrizObj();
                            if (cuentaMatriz != null) {
                                List<CmtHorarioRestriccionMgl> restriccionMgls
                                        = restriccionMglFacadeLocal.findByCuentaMatrizId(cuentaMatriz.getCuentaMatrizId());

                                GregorianCalendar cal = new GregorianCalendar();
                                cal.setTime(capacidad.getDate());
                                int dia = cal.get(Calendar.DAY_OF_WEEK);
                                String diaFecha = devuelveDia(dia);

                                String[] partsHorIniFi = capacidad.getHoraInicio().split(":");
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
                        }
                    }
                }
                return true;
            }
        } catch (ApplicationException | NumberFormatException e) {
            exceptionServBean.notifyError(e, "Error en validarRestriccionesCcmm. " + Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + " ", AGENDAMIENTO_OT_HHPP);
            return false;
        }
         return false;
    }

    public String devuelveDia(int dia) {

        String diaSemana = "";
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

        return diaSemana;
    }

    public List<String> diasComparar(String diaIni, String diaFin) {

        List<String> result = new ArrayList<>();

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
        return result;
    }

    private String listInfoByPage(int page) {
        try {
            actual = page;
            getPageActual();
            String subTipoOfsc = null;
            if (tipoOtHhppMgl != null && tipoOtHhppMgl.getSubTipoOrdenOFSC() != null) {
                subTipoOfsc = tipoOtHhppMgl.getSubTipoOrdenOFSC().getCodigoBasica();
            }
            agendaList = agendamientoHhppFacade.buscarAgendaPorOt
        (page, ConstantsCM.PAGINACION_DIEZ_FILAS, otHhppSeleccionado, subTipoOfsc);
            agendasTotal = agendamientoHhppFacade.buscarAgendaPorOt
        (0, 0, otHhppSeleccionado, subTipoOfsc);
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error en listInfoByPage " + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error en listInfoByPage: " + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        }
        return null;
    }

    public void pageFirst() {
        listInfoByPage(1);
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
            exceptionServBean.notifyError(ex, "Error al momento de navegar a la página anterior. EX000: " + ex.getMessage(), AGENDAMIENTO_OT_HHPP);
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
        } catch (NumberFormatException ex) {
            exceptionServBean.notifyError(ex, "Error al momento de ir a página. EX000: " + ex.getMessage(), AGENDAMIENTO_OT_HHPP);
        } catch (Exception ex) {
            exceptionServBean.notifyError(ex, "Error al momento de ir a la página. EX000: " + ex.getMessage(), AGENDAMIENTO_OT_HHPP);
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
        } catch (NumberFormatException ex) {
            exceptionServBean.notifyError(ex, "Error en pageNext " + ex.getMessage(), AGENDAMIENTO_OT_HHPP);
        } catch (Exception ex) {
            exceptionServBean.notifyError(ex, "Error en pageNext: " + ex.getMessage(), AGENDAMIENTO_OT_HHPP);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            exceptionServBean.notifyError(ex, "Error al navegar a la última página. EX000: " + ex.getMessage(), AGENDAMIENTO_OT_HHPP);
        }
    }

    public int getTotalPaginas() {
        try {
            String subTipoOfsc = null;
            if (tipoOtHhppMgl != null && tipoOtHhppMgl.getSubTipoOrdenOFSC() != null) {
                subTipoOfsc = tipoOtHhppMgl.getSubTipoOrdenOFSC().getCodigoBasica();
            }
            int pageSol = agendamientoHhppFacade.cantidadAgendasPorOtHhpp(otHhppSeleccionado, subTipoOfsc);
            return (pageSol % ConstantsCM.PAGINACION_DIEZ_FILAS != 0)
                    ? (pageSol / ConstantsCM.PAGINACION_DIEZ_FILAS) + 1
                    : pageSol / ConstantsCM.PAGINACION_DIEZ_FILAS;
        } catch (ApplicationException ex) {
            exceptionServBean.notifyError(ex, "Error en getTotalPaginas " + ex.getMessage(), AGENDAMIENTO_OT_HHPP);
        } catch (Exception ex) {
            exceptionServBean.notifyError(ex, "Error en getTotalPaginas: " + ex.getMessage(), AGENDAMIENTO_OT_HHPP);
        }
        return 1;
    }

    public String getPageActual() {
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
        return pageActual;
    }

    /**
     * Carga la orden de trabajo seleccionada
     *
     * @author Orlando Velasquez
     * @throws ApplicationException Excepción lanzada en la consulta
     */
    public void cargarOtHhppSeleccionada() throws ApplicationException {
        try {
            OtHhppMglSessionBean otHhppMglSessionBean
                    = JSFUtil.getSessionBean(OtHhppMglSessionBean.class);
            otHhppSeleccionado = otHhppMglSessionBean.getOtHhppMglSeleccionado();
            tipoOtHhppMgl = otHhppSeleccionado.getTipoOtHhppId();
        } catch (RuntimeException e) {
            exceptionServBean.notifyError(e, "Error en cargarOtHhppSeleccionada, al intentar cargar la ot seleccionada: "
                    + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarOtHhppSeleccionada, al intentar cargar la ot seleccionada:"+e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que permite navegar entre Tabs en la pantalla
     *
     * @author Juan David Hernandez
     * @param sSeleccionado
     */
    public void cambiarTab(String sSeleccionado) {
        try {
            ConstantsCM.TABS_HHPP Seleccionado
                    = ConstantsCM.TABS_HHPP.valueOf(sSeleccionado);
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            switch (Seleccionado) {
                case GENERAL:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "editarOtHhpp.jsf");
                    break;
                case AGENDAMIENTO:

                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "agendamientoOtHhpp.jsf");
                    selectedTab = "AGENDAMIENTO";
                    activacionUCM = activaDesactivaUCM();
                    session.setAttribute("activaUCM", activacionUCM);
                    break;
                case NOTAS:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "notasOtHhpp.jsf");
                    selectedTab = "NOTAS";
                    break;
                case ONYX:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "otOnixHhpp.jsf");
                    selectedTab = "ONYX";
                    break;
                case HISTORICO:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "historicoAgendasOtHhpp.jsf");
                    selectedTab = "HISTORICO";
                    activacionUCM = activaDesactivaUCM();
                    session.setAttribute("activaUCM", activacionUCM);
                    break;
                case BITACORA:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "bitacoraOtHhpp.jsf");
                    selectedTab = "BITACORA";
                    break;
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al realizar el cambio de Tab", e);
            String msnError = "Error al realizar el cambio de Tab ";
            exceptionServBean.notifyError(e, msnError + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        } catch (Exception e) {
            LOGGER.error("Error al realizar el cambio de Tab", e);
            String msnError = "Error al realizar el cambio de Tab";
            exceptionServBean.notifyError(e, msnError + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        }
    }

    /**
     * Metodo para validar la disponibilidad de las tecnologias seleccionadas en
     * WorkForce
     *
     * @param agenda
     * @throws co.com.claro.mgl.error.ApplicationException
     * @Author Orlando Velasquez
     * @return boolean disponibilidad de las tecnologias
     */
    public boolean consultarCapacidadWorkForce(MglAgendaDireccion agenda) throws ApplicationException {
        boolean esViable;
        try {
            isRequest = false;
            esViable = agendamientoHhppFacade.validarTecnologiasWorkForce(otHhppSeleccionado);
            if (esViable) {
                //Validamos disponibilidad del servicio
                ParametrosMgl wadlAgenda
                        = parametrosMglFacadeLocal.findParametroMgl(Constant.WADL_SERVICES_AGENDAMIENTOS);

                if (wadlAgenda == null) {
                    throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WADL_SERVICES_AGENDAMIENTOS);
                }

                URL url = new URL(wadlAgenda.getParValor());

                ConsumoGenerico.conectionWsdlTest(url, Constant.WADL_SERVICES_AGENDAMIENTOS);
                //Fin Validacion disponibilidad del servicio

                this.capacidad = agendamientoHhppFacade.getCapacidadOtDireccion(
                        otHhppSeleccionado, usuarioSesion, agenda.getOfpsOtId(), nodoMgl);

                int diasAmostrar = tipoOtHhppMgl.getDiasAMostrarAgenda();

                if (diasAmostrar < 7) {
                    diasAmostrar = 7;
                }
                
                if (capacidad.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "No hay capacidad disponible para el tipo de  "
                                    + "trabajo:  " + tipoOtHhppMgl.getTipoTrabajoOFSC().getNombreBasica()
                                    + "  y el subTipo: " + tipoOtHhppMgl.
                                            getSubTipoOrdenOFSC().getNombreBasica() + "", null));

                    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form33646");
                    return false;
                }

                List<MglCapacityElement> capacityElements = retornaCapacityComponente(capacidad,agenda);

                try {
                    mglScheduleComponetDto = mglScheduleComponetBusin.getScheduleView(
                            capacityElements, diasAmostrar, new Date());
                } catch (ApplicationException ex) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Ocurrio un error al momento de mostrar la capacidad: " + ex.getMessage() + "", ""));
                } catch (Exception ex) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Ocurrio un error al momento de mostrar la capacidad: " + ex.getMessage() + "", ""));
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Tecnologia Seleccionada no disponible "
                                + "para agendamiento en WorkForce", ""));
            }

            return esViable;
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

            exceptionServBean.notifyError(e, "Error en consultarCapacidadWorkForce, al momento de mostrar la capacidad " + msg, AGENDAMIENTO_OT_HHPP);
            return false;
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

            exceptionServBean.notifyError(e, "Error en consultarCapacidadWorkForce, al momento de mostrar la capacidad: " + msg, AGENDAMIENTO_OT_HHPP);
            return false;
        }
    }

    public boolean activaDesactivaUCM() {

        String msn;
        try {
            ParametrosMgl parametrosMgl = parametrosMglFacadeLocal.findByAcronimoName(Constant.ACTIVACION_ENVIO_UCM);
            String valor;
            if (parametrosMgl != null) {
                valor = parametrosMgl.getParValor();
                if (!valor.equalsIgnoreCase("1") && !valor.equalsIgnoreCase("0")) {
                    msn = "El valor configurado para el parametro:  "
                            + "" + Constant.ACTIVACION_ENVIO_UCM + " debe ser '1' o '0'  "
                            + "actualmente se encuentra el valor: " + valor + "";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    activacionUCM = false;
                } else if (valor.equalsIgnoreCase("1")) {
                    activacionUCM = true;
                } else {
                    activacionUCM = false;
                }

            }
        } catch (ApplicationException ex) {
            exceptionServBean.notifyError(ex, "Error al consultar el parametro: " + Constant.ACTIVACION_ENVIO_UCM + " " + ex.getMessage(), AGENDAMIENTO_OT_HHPP);
            activacionUCM = false;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al consultar el parámetro: " + Constant.ACTIVACION_ENVIO_UCM + " " + e.getMessage(), AGENDAMIENTO_OT_HHPP);
            activacionUCM = false;
        }
        return activacionUCM;
    }

    public List<MglCapacityElement> retornaCapacityComponente(List<CapacidadAgendaDto> capacityConsulta,
            MglAgendaDireccion agendaCon) throws ApplicationException {

        List<MglCapacityElement> capacityElementList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        MglAgendaDireccion ageAnterior;
        MglAgendaDireccion agePosterior;
        int secCon = 0;

        if (agendaCon.getAgendaId() != null) {

            try {
                //Busco agenda anterior y posterior
                ageAnterior = agendamientoHhppFacade.buscarAgendaAnteriorPosteriorHhpp(agendaCon, secCon);

                secCon = 1;

                agePosterior = agendamientoHhppFacade.buscarAgendaAnteriorPosteriorHhpp(agendaCon, secCon);

                //Reagendar
                String fechaReagenda = format.format(agendaCon.getFechaAgenda());
                Date fechaRea = format.parse(fechaReagenda);

                for (CapacidadAgendaDto capacidadAgendaDto : capacityConsulta) {

                    MglCapacityElement capacityElement = new MglCapacityElement();

                    int agendaBaseCon = 0;
                    Date fechaCap = null;

                    if (!agendasTotal.isEmpty()) {
                        for (MglAgendaDireccion agendaDireccion : agendasTotal) {

                            String fechaCapacity = format.format(capacidadAgendaDto.getDate());
                            String fechaAgenda = format.format(agendaDireccion.getFechaAgenda());

                            Date fechaAge;

                            try {
                                fechaCap = format.parse(fechaCapacity);
                                fechaAge = format.parse(fechaAgenda);

                                if (fechaCap.before(fechaAge)) {
                                    LOGGER.info("La Fecha del capacity  es menor a la del agendamiento ");
                                } else if (fechaAge.before(fechaCap)) {
                                    LOGGER.info("La Fecha del capacity es Mayor a la del agendamiento ");
                                } else {
                                    if (capacidadAgendaDto.getTimeSlot().equalsIgnoreCase(agendaDireccion.getTimeSlot())) {

                                        if (!agendaDireccion.getBasicaIdEstadoAgenda().
                                                getIdentificadorInternoApp().
                                                equalsIgnoreCase(Constant.ESTADO_AGENDA_CANCELADA) &&
                                                !agendaDireccion.getBasicaIdEstadoAgenda().
                                                getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA) 
                                                && !agendaDireccion.getBasicaIdEstadoAgenda().
                                                getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTADO_AGENDA_NODONE) ) {
                                            agendaBaseCon = 1;
                                            agendaDireccion.setEstaEnCapacity(true);
                                        } else {
                                            LOGGER.info("Agenda esta en estado cancelada");
                                        }
                                    } else {
                                        LOGGER.info("Igual fecha distinta franja");
                                    }
                                }
                            } catch (ParseException ex) {
                                exceptionServBean.notifyError(ex, CAPACITY_ERROR + ex.getMessage(), AGENDAMIENTO_OT_HHPP);
                                LOGGER.error(CAPACITY_ERROR + ex.getMessage());
                            } catch (Exception ex) {
                                exceptionServBean.notifyError(ex, "Ocurrió un error retornando capacidad en CmtAgendamientoMglBean: retornaCapacityComponente()"
                                        + ex.getMessage(), AGENDAMIENTO_OT_HHPP);
                                LOGGER.error("Ocurrió un error retornando capacidad en  agendamientoOtHhppBean: retornaCapacityComponente() " + ex.getMessage());
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
                                capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
                            } else if (agePosterior != null && ageAnterior == null) {

                                if (fechaPos != null) {
                                    if (fechaPos.after(fechaRea) && timSlotPos == 0) {
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
                                    } else {
                                        capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                    }
                                }

                            } else if (ageAnterior != null && agePosterior == null) {
                                if (fechaAnt != null) {
                                    if (fechaAnt.before(fechaRea) || fechaCapa.after(fechaRea)) {
                                        if (fechaCapa.after(fechaRea)) {
                                            capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
                                        } else {
                                            if (timSlotAnt > 0) {
                                                capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                            } else {
                                                capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
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
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
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
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
                                    } else if (fechaCap.after(fechaPos)) {
                                        capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                    } else {
                                        if (timSlotCap < timSlotPos) {
                                            capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
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
                                            capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);

                                        } else if (fechaCap != null && fechaCap.after(fechaPos)) {
                                            capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                        } else {
                                            if (timSlotCap < timSlotPos) {
                                                capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
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
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
                                    } else if (fechaCap.after(fechaRea)) {
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
                                    } else {
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
                                    }
                                } else {
                                    if (timSlotCap < timSlotAnt) {
                                        capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                    } else {
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
                                    }
                                }

                            } else if (fechaPos != null && fechaAnt == null) {

                                if (fechaCap != null && fechaCap.before(fechaPos)) {
                                    capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
                                } else if (fechaCap != null && fechaCap.after(fechaPos)) {
                                    capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                } else {
                                    if (timSlotCap < timSlotPos) {
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
                                    } else {
                                        capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                    }
                                }
                            } else {
                                capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
                            }
                        }
                    }
                    capacityElementList.add(capacityElement);
                }
            } catch (ParseException ex) {
                exceptionServBean.notifyError(ex, CAPACITY_ERROR + ex.getMessage(), AGENDAMIENTO_OT_HHPP);
            } catch (ApplicationException | NumberFormatException ex) {
                exceptionServBean.notifyError(ex, "Ocurrió un error retornando capacidad en agendamientoOtHhppBean: retornaCapacityComponente() " + ex.getMessage(), AGENDAMIENTO_OT_HHPP);
            }
        } else {
            //Nueva agenda  
            MglAgendaDireccion ultimaAgenda;
            String subTipoWorkFoce = otHhppSeleccionado.getTipoOtHhppId().getSubTipoOrdenOFSC().getCodigoBasica();

            ultimaAgenda = agendamientoHhppFacade.buscarUltimaAgendaHhpp(otHhppSeleccionado, estadosAgendadas(), subTipoWorkFoce);
            try {
                for (CapacidadAgendaDto capacidadAgendaDto : capacityConsulta) {

                    MglCapacityElement capacityElement = new MglCapacityElement();

                    int control = 0;
                    String fechaCapacity = format.format(capacidadAgendaDto.getDate());
                    Date fechaCap = format.parse(fechaCapacity);

                    if (!agendasTotal.isEmpty()) {
                        for (MglAgendaDireccion agendamientoMgl : agendasTotal) {

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
                                    capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
                                } else {
                                    capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                }
                            } else {
                                capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
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
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
                                    } else {
                                        capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                    }
                                } else {
                                    int timeSlotUltima = Integer.parseInt(ultimaAgenda.getTimeSlot().substring(0, 2));
                                    Date ultimaAge = format.parse(fechaUltimaAgenda);
                                    if (fechaCap.before(ultimaAge)) {
                                        capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                    } else if (fechaCap.after(ultimaAge)) {
                                        capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
                                    } else {
                                        if (timSlotCap < timeSlotUltima) {
                                            capacityElement = retornaCapacityBloqueado(capacityElement, capacidadAgendaDto);
                                        } else {
                                            capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
                                        }
                                    }
                                }
                            } else {
                                capacityElement = retornaCapacityLibre(capacityElement, capacidadAgendaDto);
                            }
                        }
                    }
                    capacityElementList.add(capacityElement);
                }
            } catch (ParseException e) {
                exceptionServBean.notifyError(e, "Ocurrió un error en Agendamiento class: " + e.getMessage(), AGENDAMIENTO_OT_HHPP);
            } catch (NumberFormatException e) {
                exceptionServBean.notifyError(e, "Ocurrió un error retornando capacidad en CmtAgendamientoMglBean: retornaCapacityComponente()  " + e.getMessage(), AGENDAMIENTO_OT_HHPP);
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

       List<MglAgendaDireccion> agendarOTSubtipo = null;
       
        try {
          mensajesValidacionFinal = "";
          mensajesValidacion = "";
          isRequest = false;
          
          //Consultamos de nuevo la orden para que tome las ultimas actualizaciones
            OtHhppMgl ot = otHhppMglFacadeLocal.findOtByIdOt(otHhppSeleccionado.getOtHhppId());
            BigDecimal numOtHija = null;
            if (ot.getOtHhppId() != null && ot.getOnyxOtHija() != null) {
                numOtHija = ot.getOnyxOtHija();
            }
          
            if (!validarAccionAgendamiento(OPCION_CREAR)) {
                mensajesValidacion = "No tiene el rol especifico para esta accion.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajesValidacion, null));
                return null;
            }

            if (otHhppSeleccionado.getNombreContacto() == null || otHhppSeleccionado.getNombreContacto().isEmpty() || otHhppSeleccionado.getNombreContacto().isEmpty()) {
                mensajesValidacion = "El nombre de la persona que recibe la visita es obligatorio.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajesValidacion, null));
                return null;
            }
            if (otHhppSeleccionado.getTelefonoContacto() == null || otHhppSeleccionado.getTelefonoContacto().isEmpty() || otHhppSeleccionado.getTelefonoContacto().isEmpty()) {
                mensajesValidacion = "El telefono de la persona que recibe la visita es obligatorio.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajesValidacion, null));
                return null;
            }
            if (otHhppSeleccionado.getCorreoContacto() == null || otHhppSeleccionado.getCorreoContacto().isEmpty() || otHhppSeleccionado.getCorreoContacto().isEmpty()) {
                mensajesValidacion = "El correo de la persona que recibe la visita es obligatorio.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajesValidacion, null));
                return null;
            }
            
            CmtOnyxResponseDto cmtOnyxResponseDto = null;
            if (numOtHija != null) {
                //Validamos disponibilidad del servicio
                ParametrosMgl wsdlOtHija
                        = parametrosMglFacadeLocal.findParametroMgl(Constant.WSDL_OT_HIJA_ONIX);

                if (wsdlOtHija == null) {
                    throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WSDL_OT_HIJA_ONIX);
                }

                URL urlCon = new URL(wsdlOtHija.getParValor());

                ConsumoGenerico.conectionWsdlTest(urlCon, Constant.WSDL_OT_HIJA_ONIX);
                //Fin Validacion disponibilidad del servicio

                cmtOnyxResponseDto = ordenTrabajoMglFacadeLocal.consultarOTHijaOnyx(numOtHija.toString());

            } else {
                List<OnyxOtCmDir> listaOnyx = onyxOtCmDirlFacadeLocal.findOnyxOtHhppById(otHhppSeleccionado.getOtHhppId());
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
 
                    cmtOnyxResponseDto = ordenTrabajoMglFacadeLocal.consultarOTHijaOnyx(objOnyx.getOnyx_Ot_Hija_Dir());
                    otHhppSeleccionado.setOnyxOtHija(new BigDecimal(objOnyx.getOnyx_Ot_Hija_Dir()));
                    otHhppSeleccionado.setComplejidadServicio(objOnyx.getComplejidadServicio());
                } else {
                    if (otHhppSeleccionado.getTipoOtHhppId() != null
                            && otHhppSeleccionado.getTipoOtHhppId().getRequiereOnyx()
                            != null && otHhppSeleccionado.getTipoOtHhppId().
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
            }else{
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
                    }else if (!validoFechaHoraAgendas(agendaList)) {
                        mensajesValidacion = "Existe ya una agenda para el dia: "+fechaSelected+" y  "
                                + "la misma hora : "+agendaIgualFechaHora.getHoraInicio()+"";
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


            if (capacidadAgendaDtos.size() > 0) {

                if (capacidadAgendaDtos.size() > 1
                        && tipoOtHhppMgl.getIsMultiagenda().equalsIgnoreCase("N")) {
                    mensajesValidacion = "Debe selecionar solo una celda para agendar.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacion, null));
                    return null;
                } else {

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
                    capacidadAgendaDtos.stream().filter((capacidadAgendaDto) -> (capacidadAgendaDto.getTimeSlot() != null 
                            && capacidadAgendaDto.getTimeSlot().equalsIgnoreCase(Constant.DURANTE_EL_DIA))).forEachOrdered((capacidadAgendaDto) -> {
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
                        
                        if (tipoOtHhppMgl.getIsMultiagenda().equalsIgnoreCase("N")) {
                            //Es unica Agenda
                            CapacidadAgendaDto capacidadAgendaDto = capacidadAgendaDtos.get(0);
                            agenda.setOrdenTrabajo(otHhppSeleccionado);
                            agenda.setPersonaRecVt(otHhppSeleccionado.getNombreContacto());
                            agenda.setTelPerRecVt(otHhppSeleccionado.getTelefonoContacto());
                            agenda.setEmailPerRecVT(otHhppSeleccionado.getCorreoContacto());
                            agenda.setIdentificacionTecnico(capacidadAgendaDto.getResourceId());
                            agenda.setNombreTecnico(capacidadAgendaDto.getNombreTecnico());
                            agenda.setIdentificacionAliado(capacidadAgendaDto.getAliadoId());
                            agenda.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                            agenda.setNumeroOrdenInmediata(capacidadAgendaDto.getNumeroOrdenInmediata());
                            
                            if (nodoMgl != null) {
                                agenda.setNodoMgl(nodoMgl);
                            }

                            String prerequisitos = regresaCadenaTipoPrerequisito(idBasicaPrerequisito);
                            agenda.setPrerequisitosVT(prerequisitos);

                            String numeroOtRr = null;
                            codCuentaPar = otHhppSeleccionado.getDireccionId().getDirId().toString();

                            agenda = agendamientoHhppFacade.agendar(capacidadAgendaDto, agenda, numeroOtRr, null, tipoAgendamiento);
                            // conveniencia
                            convenienciaCheck = conveniencia ? "Y" : "N";
                            agenda.setConveniencia(convenienciaCheck);
                            agenda.setCantAgendas("0");
                            agendamientoHhppFacade.create(agenda);
                            
                            if (cmtOnyxResponseDto != null) {
                                agenda.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                                try {
                                    agendamientoHhppFacade.cargarInformacionForEnvioNotificacion(agenda, 1);
                                } catch (ApplicationException ex) {
                                     String msn = "Ocurrio un error al momento de "
                                            + "enviar notificacion de agenda: " + ex.getMessage() + "";
                                    exceptionServBean.notifyError(ex, msn, AGENDAMIENTO_OT_HHPP);
                                }
                            }

                            if (agenda.getIdentificacionTecnico() != null
                                    && !agenda.getIdentificacionTecnico().isEmpty()) {

                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String fechAge = df.format(agenda.getFechaAgenda());

                                String msn = "Se ha creado la agenda:  " + agenda.getOfpsOtId() + "  "
                                        + "  para la ot: " + agenda.getOrdenTrabajo().getOtHhppId() + " "
                                        + " con el tecnico:" + agenda.getNombreTecnico()
                                        + " [" + agenda.getIdentificacionTecnico() + "] "
                                        + "para el dia: " + fechAge + " en la hora: " + agenda.getHoraInicio() + " ";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                msn, ""));
                            } else {
                                String msn = "Se ha creado la agenda:  " + agenda.getOfpsOtId() + "  "
                                        + "  para la ot: " + agenda.getOrdenTrabajo().getOtHhppId() + " ";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                msn, ""));
                            }

                            listInfoByPage(1);
                            regresar();
                        } else {
                            //Es multi agenda
                            String mensajeFinal = "";
                            String mensajeFinalErrorCap = "";
                            int i = 0;
                            int j = 0;

                            String prerequisitos = regresaCadenaTipoPrerequisito(idBasicaPrerequisito);

                            String numeroOTRr = null;
                            ParametrosMgl parametroHabilitarOtInRr = parametrosMglFacadeLocal.findParametroMgl(Constant.HABILITAR_RR);

                            if (parametroHabilitarOtInRr == null) {
                                throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.HABILITAR_RR);
                            }

                            CmtCuentaMatrizMgl cuentaAgrupadora = null;
                            ////Consulta de cuenta matriz agrupadora  si es multiagenda

                            String subTipo = tipoOtHhppMgl.getSubTipoOrdenOFSC().getCodigoBasica();
                            List<MglAgendaDireccion> agendasCcmm = agendamientoHhppFacade.
                                    buscarAgendasByOtAndSubtipopOfsc(otHhppSeleccionado, subTipo);
                            if ((agendasCcmm == null || agendasCcmm.isEmpty())) {
                                LOGGER.info("Se consulta Ccmm agrupadora ,"
                                        + " es la primera agenda del estado actual de la OT en MGL ");

                                if (otHhppSeleccionado.getDireccionId() != null
                                        && otHhppSeleccionado.getDireccionId().getUbicacion() != null) {
                                    GeograficoPoliticoMgl geograficoPoliticoMgl
                                            = otHhppSeleccionado.getDireccionId().getUbicacion().getGpoIdObj();

                                    if (geograficoPoliticoMgl != null) {

                                        CmtBasicaMgl basicaMgl = basicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_TIPO_EDIFICIO_AGRUPADOR_DIRECCIONES_BARRIO);

                                        if (basicaMgl != null) {

                                            List<CmtCuentaMatrizMgl> cuentasAgrupadora
                                                    = cuentaMatrizMglFacadeLocal.findCuentasMatricesAgrupadorasByCentro(geograficoPoliticoMgl.getGpoId(), basicaMgl);

                                            if (cuentasAgrupadora != null && cuentasAgrupadora.size() == 1) {
                                                //Hay una sola CM agrupadora
                                                cuentaAgrupadora = cuentasAgrupadora.get(0);
                                                codCuentaPar = cuentaAgrupadora.getNumeroCuenta().toString();

                                            } else if (cuentasAgrupadora != null && cuentasAgrupadora.size() > 1) {
                                                //Hay Varias CM agrupadora
                                                for (CmtCuentaMatrizMgl cuentaMatrizMgl : cuentasAgrupadora) {
                                                    String msn = "Existe la cuenta matriz:  " + cuentaMatrizMgl.getCuentaMatrizId() + "  "
                                                            + "  creada como agrupadora para la ciudad:  "
                                                            + "" + cuentaMatrizMgl.getMunicipio().getGpoNombre() + ". ";
                                                    mensajeFinal += msn;
                                                }
                                                throw new ApplicationException(mensajeFinal);
                                            } else {
                                                GeograficoPoliticoMgl ciudad = geograficoPoliticoMglFacadeLocal.
                                                        findById(geograficoPoliticoMgl.getGeoGpoId());

                                                throw new ApplicationException("No hay creada una "
                                                        + "cuenta matriz de tipo: AGRUPADOR_DIRECCIONES_BARRIO "
                                                        + " para la ciudad: " + ciudad.getGpoNombre() + " ");
                                            }
                                        } else {
                                            throw new ApplicationException("No hay configurado "
                                                    + "un tipo edificio:AGRUPADOR_DIRECCIONES_BARRIO en las tablas basicas ");
                                        }

                                    }

                                }
                                //////Consulta de cuenta matriz agrupadora    

                            } else {
                                //Ya existen agendas tomo la ccmm asociada
                                MglAgendaDireccion agendaDir = agendasCcmm.get(0);
                                cuentaAgrupadora = agendaDir.getCuentaMatrizMgl();
                                if (cuentaAgrupadora != null) {
                                    codCuentaPar = cuentaAgrupadora.getNumeroCuenta().toString();
                                }

                            }


                            if (parametroHabilitarOtInRr.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                                if (tipoOtHhppMgl != null) {
                                    if (tipoOtHhppMgl.getSubTipoOrdenOFSC() != null
                                            && tipoOtHhppMgl.getTipoTrabajoRR() != null
                                            && tipoOtHhppMgl.getEstadoOtRRInicial() != null
                                            && tipoOtHhppMgl.getEstadoOtRRFinal() != null) {
                                        String subTipoWorForce = tipoOtHhppMgl.getSubTipoOrdenOFSC().getCodigoBasica();

                                        agendarOTSubtipo = agendamientoHhppFacade.buscarAgendasByOtAndSubtipopOfsc(otHhppSeleccionado, subTipoWorForce);
                                        if ((agendarOTSubtipo == null || agendarOTSubtipo.isEmpty())) {
                                            LOGGER.info("Se creara OT en RR , es la primera agenda del estado actual de la OT en MGL ");
                                            numeroOTRr = ordenTrabajoMglFacadeLocal.crearOtRRporAgendamientoHhpp
                                            (codCuentaPar, tipoOtHhppMgl, usuarioSesion.getNombre(),otHhppSeleccionado);
                                        }
                                    } else {
                                        LOGGER.info("El estado actual del tipo de OT no tiene los campos de creacion de OT RR configurados , se enviara id de OT MGL");
                                        String mensajeCamposRRestado = "Recuerde que para que se cree OT en RR y continuar con el agendamiento debe diligenciar los campos , Tipo OT RR , Estado Inicial y Estado Cierre en el estado x flujo actual de OT MGL. ";
                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeCamposRRestado, null));
                                        return null;
                                    }

                                } else {
                                    LOGGER.info("El estado actual de la OT no permite agendamiento: el tipo de Ot es null");
                                }
                                //--
                            } else {
                                LOGGER.info("El parámetro " + Constant.ACTIVAR_CREACION_OT_RR + " no se encuentra habilitado. No se crea orden en RR.");
                            }

                            if (agendarOTSubtipo != null && !agendarOTSubtipo.isEmpty()) {
                                numeroOTRr = agendarOTSubtipo.get(0).getIdOtenrr() != null ? agendarOTSubtipo.get(0).getIdOtenrr() : otHhppSeleccionado.getOtHhppId().toString();
                            }

                            String numeroOTRrOFSC;

                            if (numeroOTRr == null) {
                                // Si no fue generada la OT en Rr           
                                numeroOTRrOFSC = generarNumeroOtRr(codCuentaPar, numeroOTRr, otHhppSeleccionado, false);
                            } else {
                                numeroOTRrOFSC = generarNumeroOtRr(codCuentaPar, numeroOTRr, otHhppSeleccionado, true);
                            }

                            for (CapacidadAgendaDto capacidadAgendaDto : capacidadAgendaDtos) {

                                agenda = new MglAgendaDireccion();
                                agenda.setOrdenTrabajo(otHhppSeleccionado);
                                agenda.setPersonaRecVt(otHhppSeleccionado.getNombreContacto());
                                agenda.setTelPerRecVt(otHhppSeleccionado.getTelefonoContacto());
                                agenda.setEmailPerRecVT(otHhppSeleccionado.getCorreoContacto());
                                agenda.setIdentificacionTecnico(capacidadAgendaDto.getResourceId());
                                agenda.setNombreTecnico(capacidadAgendaDto.getNombreTecnico());
                                agenda.setIdentificacionAliado(capacidadAgendaDto.getAliadoId());
                                agenda.setPrerequisitosVT(prerequisitos);
                                agenda.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                                agenda.setNumeroOrdenInmediata(capacidadAgendaDto.getNumeroOrdenInmediata());

                                if (nodoMgl != null) {
                                    agenda.setNodoMgl(nodoMgl);
                                }

                                if (i == 0) {
                                        
                                    agenda = agendamientoHhppFacade.agendar(capacidadAgendaDto, agenda, numeroOTRrOFSC, cuentaAgrupadora, tipoAgendamiento);
                                    agenda.setIdOtenrr(numeroOTRr);
                                    agendamientoHhppFacade.setUser(usuarioVT, perfilVt);
                                    // conveniencia
                                    convenienciaCheck = conveniencia ? "Y" : "N";
                                    agenda.setConveniencia(convenienciaCheck);
                                    agenda.setCantAgendas("0");
                                    agendamientoHhppFacade.create(agenda);
                                    if (cmtOnyxResponseDto != null) {
                                        agenda.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                                        try {
                                            agendamientoHhppFacade.cargarInformacionForEnvioNotificacion(agenda, 1);
                                        } catch (ApplicationException ex) {
                                            String msn = "Ocurrio un error al momento de "
                                                    + "enviar notificacion de agenda: " + ex.getMessage() + "";
                                            exceptionServBean.notifyError(ex, msn, AGENDAMIENTO_OT_HHPP);
                                        }
                                    }
                                    if (agenda.getTecnicoAnticipado() != null && 
                                            agenda.getTecnicoAnticipado().equalsIgnoreCase("Y")) {

                                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                        String fechAge = df.format(agenda.getFechaAgenda());

                                        String msn = "Se ha creado la agenda:  " + agenda.getOfpsOtId() + "  "
                                                + "  para la ot: " + agenda.getOrdenTrabajo().getOtHhppId() + " "
                                                + " con el tecnico:" + agenda.getNombreTecnico()
                                                + " [" + agenda.getIdentificacionTecnico() + "] "
                                                + "para el dia: " + fechAge + " en la hora: " + agenda.getHoraInicio() + " ";

                                        mensajeFinal += msn;
                                    } else {
                                        String msn = "Se ha creado la agenda:  " + agenda.getOfpsOtId() + "  "
                                                + "  para la ot: " + agenda.getOrdenTrabajo().getOtHhppId() + " ";
                                        mensajeFinal += msn;
                                    }
                                } else {
                                    List<CapacidadAgendaDto> resultado = getCapacidadMulti();
                                    if (resultado.size() > 0) {
                                        
                                        agenda = agendamientoHhppFacade.agendar(capacidadAgendaDto, agenda, numeroOTRrOFSC, cuentaAgrupadora, tipoAgendamiento);
                                        agenda.setIdOtenrr(numeroOTRr);
                                        agendamientoHhppFacade.setUser(usuarioVT, perfilVt);
                                        // conveniencia
                                        convenienciaCheck = conveniencia ? "Y" : "N";
                                        agenda.setCantAgendas("0");
                                        agenda.setConveniencia(convenienciaCheck);
                                        agendamientoHhppFacade.create(agenda);
                                        if (cmtOnyxResponseDto != null) {
                                            agenda.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                                            try {
                                                agendamientoHhppFacade.cargarInformacionForEnvioNotificacion(agenda, 1);
                                            } catch (ApplicationException ex) {
                                                String msn = "Ocurrio un error al momento de "
                                                        + "enviar notificacion de agenda: " + ex.getMessage() + "";
                                                exceptionServBean.notifyError(ex, msn, AGENDAMIENTO_OT_HHPP);
                                            }
                                        }
                                         if (agenda.getTecnicoAnticipado() != null && 
                                            agenda.getTecnicoAnticipado().equalsIgnoreCase("Y")) {

                                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                            String fechAge = df.format(agenda.getFechaAgenda());

                                            String msn = "Se ha creado la agenda:  " + agenda.getOfpsOtId() + "  "
                                                    + "  para la ot: " + agenda.getOrdenTrabajo().getOtHhppId() + " "
                                                    + " con el tecnico:" + agenda.getNombreTecnico()
                                                    + " [" + agenda.getIdentificacionTecnico() + "] "
                                                    + "para el dia: " + fechAge + " en la hora: " + agenda.getHoraInicio() + " ";
                                            mensajeFinal += msn;
                                        } else {
                                            String msn = "Se ha creado la agenda:  " + agenda.getOfpsOtId() + "  "
                                                    + "  para la ot: " + agenda.getOrdenTrabajo().getOtHhppId() + " ";
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
                        //Actualizo la orden para que tome el numero de ot hija
                        otHhppSeleccionado = otHhppMglFacadeLocal.update(otHhppSeleccionado);
                    }
                }
            } else {
                mensajesValidacion = "Debe selecionar al menos una celda para agendar.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajesValidacion, null));
                return null;
            }

        } catch (ApplicationException ex) {
            String msg;
            if (ex.getMessage().contains("$")) {
                partesMensaje = ex.getMessage().split("\\$");
                msg = partesMensaje[0];
            } else {
                msg = ex.getMessage();
            }

            if (partesMensaje != null && partesMensaje[1] != null && !partesMensaje[1].isEmpty()) {
                isRequest = true;
                json = partesMensaje[1];
                session.setAttribute("json", json);
            }

            exceptionServBean.notifyError(ex, "Ocurrió un error al realizar el agendamiento: " + msg, AGENDAMIENTO_OT_HHPP);
            if ((agendarOTSubtipo == null || agendarOTSubtipo.isEmpty())) {
                rollbackOrdenInRr(agenda);
            }
        } catch (IOException|ParseException ex) {
            String msg;
            if (ex.getMessage().contains("$")) {
                partesMensaje = ex.getMessage().split("\\$");
                msg = partesMensaje[0];
            } else {
                msg = ex.getMessage();
            }

            if (partesMensaje != null && partesMensaje[1] != null && !partesMensaje[1].isEmpty()) {
                isRequest = true;
                json = partesMensaje[1];
                session.setAttribute("json", json);
            }

            exceptionServBean.notifyError(ex, "Ocurrió un error al realizar el agendamiento: " + msg, AGENDAMIENTO_OT_HHPP);
            if ((agendarOTSubtipo == null || agendarOTSubtipo.isEmpty())) {
                rollbackOrdenInRr(agenda);
            }
        }

        return "";
    }

    public String reagendarOrden() {

        try {
            isRequest = false;

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
            if (razonReagendamiento == null || comentariosOrden.isEmpty()) {
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

            if (reAgendaMgl.getHoraInicio() != null && reAgendaMgl.getTecnicoAnticipado() != null &&
                    reAgendaMgl.getTecnicoAnticipado().equalsIgnoreCase("Y")) {
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
                    } else if (!validoFechaHoraAgendas(agendaList)) {
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
                            int cantAgend = agendamientoHhppFacade.getCountAgendamiento(reAgendaMgl);
                            reAgendaMgl.setCantAgendas(String.valueOf(cantAgend));
                            
                             if (reAgendaMgl.getMotivosReagenda() != null
                                    && !reAgendaMgl.getMotivosReagenda().isEmpty()) {
                                reAgendaMgl.setMotivosReagenda(reAgendaMgl.getMotivosReagenda() + razonReagendamiento + ",");
                            } else {
                                reAgendaMgl.setMotivosReagenda(razonReagendamiento + ",");
                            }
                             
                            agendamientoHhppFacade.reagendar(capacidadAgendaDto1, reAgendaMgl,
                                    razonReagendamiento, comentariosOrden,
                                    usuarioVT, perfilVt);
                            reAgendaMgl.setComentarioReasigna("Modificacion del tecnico al reagendar");
                            agendamientoHhppFacade.update(reAgendaMgl, usuarioVT, perfilVt);
                            String msn = "Se ha Reagendado la agenda:  " + reAgendaMgl.getOfpsOtId() + "  "
                                    + "  para la ot: " + reAgendaMgl.getOrdenTrabajo().getOtHhppId() + " ";
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

                if (!capacidadAgendaDtos.isEmpty()) {
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
                            int cantAgend = agendamientoHhppFacade.getCountAgendamiento(reAgendaMgl);
                            reAgendaMgl.setCantAgendas(String.valueOf(cantAgend));
                            
                             if (reAgendaMgl.getMotivosReagenda() != null
                                    && !reAgendaMgl.getMotivosReagenda().isEmpty()) {
                                reAgendaMgl.setMotivosReagenda(reAgendaMgl.getMotivosReagenda() + razonReagendamiento + ",");
                            } else {
                                reAgendaMgl.setMotivosReagenda(razonReagendamiento + ",");
                            }
                             
                            agendamientoHhppFacade.reagendar(capacidadAgendaDto, reAgendaMgl,
                                    razonReagendamiento, comentariosOrden,
                                    usuarioVT, perfilVt);                            
                  
                            
                            if (reAgendaMgl.getTecnicoAnticipado() != null && reAgendaMgl.getTecnicoAnticipado().equalsIgnoreCase("Y")) {

                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String fechAge = df.format(reAgendaMgl.getFechaAgenda());
                                String msn = "Se ha reagendado la agenda:  " + reAgendaMgl.getOfpsOtId() + "  "
                                        + "  para la ot: " + reAgendaMgl.getOrdenTrabajo().getOtHhppId() + " "
                                        + " con el técnico:" + reAgendaMgl.getNombreTecnico()
                                        + " [" + reAgendaMgl.getIdentificacionTecnico() + "] "
                                        + "para el dia: " + fechAge + " en la hora: " + reAgendaMgl.getHoraInicio() + " ";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                msn, ""));
                            } else {
                                String msn = "Se ha Reagendado la agenda:  " + reAgendaMgl.getOfpsOtId() + "  "
                                        + "  para la ot: " + reAgendaMgl.getOrdenTrabajo().getOtHhppId() + " ";
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
        } catch (ApplicationException | ParseException| IOException e) {
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

            exceptionServBean.notifyError(e, "Error en reagendarOrden: " + msg + "", AGENDAMIENTO_OT_HHPP);
        }

        return "";
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

    /**
     * Genera un N&uacute;mero de OT de RR, de acuerdo a si fue creada o no una
     * OT en RR.
     *
     * @param codCuentaGenerico
     * @param numeroOtRr N&uacute;mero de la OT en RR.
     * @param otHhppMgl
     * @param habilitaRr Flag que determina si se encuentra RR habilitado.
     * @return N&uacute;mero de la OT en RR.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public String generarNumeroOtRr(String codCuentaGenerico, String numeroOtRr, OtHhppMgl otHhppMgl,
            boolean habilitaRr)
            throws ApplicationException {

        String numeroGenerado = "";

        try {
            if (codCuentaGenerico != null) {
                if (habilitaRr) {
                    //Si se generó una Ot en RR:
                    if (numeroOtRr != null) {
                        // Número de cuenta matriz de RR>+<Número orden de RR a 5,  justificada a la derecha y relleno con ceros.
                        numeroGenerado = codCuentaGenerico.trim() + StringUtils.leftPad(numeroOtRr, MAX_CARACTERES_NUMERO_OT_RR, "0");
                    }

                } else {
                    //Si no se generó una Ot en RR:
                    if (otHhppMgl.getOtHhppId() != null) {
                        // Número de Orden de direccion>+< la Orden de trabajo a 6 dígitos  justificada a la derecha y relleno con ceros.
                        numeroGenerado = otHhppMgl.getOtHhppId().toString().trim() + StringUtils.leftPad(otHhppMgl.getOtHhppId().toString().trim(), MAX_CARACTERES_ESTADO_OT, "0");
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

    public List<CapacidadAgendaDto> getCapacidadMulti() {

        capacidadAgendaDtos = new ArrayList<>();
        try {
            capacidadAgendaDtos = agendamientoHhppFacade.getCapacidadOtDireccion(
                    otHhppSeleccionado, usuarioSesion, null, nodoMgl);

        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en getCapacidadMulti " + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en getCapacidadMulti: " + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        }
        return capacidadAgendaDtos;
    }

    public void rollbackOrdenInRr(MglAgendaDireccion agendaDireccion) {

        boolean respuestaElimina;

        try {
            if (agendaDireccion.getIdOtenrr() != null && !agendaDireccion.getIdOtenrr().isEmpty()) {
                //Consultamos la ot en rr si existe
                ResponseOtEdificiosList responseOtEdificiosList = ordenTrabajoMglFacadeLocal.
                        ordenTrabajoEdificioQuery(codCuentaPar);

                if (responseOtEdificiosList != null
                        && responseOtEdificiosList.getListOtEdificios() != null
                        && !responseOtEdificiosList.getListOtEdificios().isEmpty()) {

                    for (ResponseDataOtEdificios responseDataOtEdificios
                            : responseOtEdificiosList.getListOtEdificios()) {

                        if (responseDataOtEdificios.getNumeroTrabajo().
                                equalsIgnoreCase(agendaDireccion.getIdOtenrr())) {
                            //Eliminamos la ot en rr si existe
                            respuestaElimina = ordenTrabajoMglFacadeLocal.
                                    ordenTrabajoEdificioDeleteHhpp(codCuentaPar, agendaDireccion.getIdOtenrr(), usuarioVT);

                            if (respuestaElimina) {
                                LOGGER.info("Orden No: " + agendaDireccion.getIdOtenrr() + " borrada por rollback en RR");
                            }
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Ocurrió un error al realizar el agendamiento: rollbackOrdenInRr " + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error al realizar el agendamiento: rollbackOrdenInRr  " + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        }

    }

    public MglCapacityElement retornaCapacityLibre(MglCapacityElement capacityElement, CapacidadAgendaDto capacidadAgendaDto) {

        capacityElement.setUsed(false);
        capacityElement.setDate(capacidadAgendaDto.getDate());
        capacityElement.setTimeSlot(capacidadAgendaDto.getTimeSlot());

        if ((getValidateMinutesSlot(capacidadAgendaDto))) {
                capacityElement.setQuota(0);
                capacityElement.setAvailable(0);
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
            exceptionServBean.notifyError(ex, "Ocurrió un error al consultar los estados de agenda " + ex.getMessage(), AGENDAMIENTO_OT_HHPP);
        } catch (Exception ex) {
            exceptionServBean.notifyError(ex, "Ocurrió un error al consultar los estados de agenda: " + ex.getMessage(), AGENDAMIENTO_OT_HHPP);
        }

        return idsEstados;
    }
    
    private boolean getValidateMinutesSlot(CapacidadAgendaDto capacidadAgendaDto) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String diaActual = format.format(new Date());
        String diaCapacidad = format.format(capacidadAgendaDto.getDate());
        boolean bandInhabilitar = false;

        if (!diaCapacidad.isEmpty() && diaActual.equals(diaCapacidad)) {
            int tiempoMinutos = tipoOtHhppMgl.getTiempoAgendaOfsc();

            if (capacidadAgendaDto.getTimeSlot().matches("[0-9]?[0-9]-[0-9]?[0-9]")) {
                String[] timeSlot = capacidadAgendaDto.getTimeSlot().split("-");

                Calendar horaActual = Calendar.getInstance();

                if (Integer.parseInt(timeSlot[0]) <= horaActual.get(Calendar.HOUR_OF_DAY)
                        && horaActual.get(Calendar.HOUR_OF_DAY) <= Integer.parseInt(timeSlot[1])) {
                    return true;
                }

                if (tiempoMinutos > 0) {
                    LocalDateTime fechaActual = LocalDateTime.now(ZoneId.of("America/Bogota"));
                    int horaInicioFranja = Integer.parseInt(timeSlot[0]);

                    if ((fechaActual.getHour() + 1) == horaInicioFranja) {
                        //Obtiene la fecha de capacidad de agenda transformada al tipo objeto requerido.
                        LocalDateTime fechayHoraAgendaHab = DateToolUtils.convertDateToLocalDateTime(capacidadAgendaDto.getDate());
                        LocalTime horaFranja = LocalTime.of(horaInicioFranja, 0, 0);
                        //Se obtiene la fecha de agenda, modificando su hora y minutos
                        LocalDateTime fechayHoraAgendaMod = fechayHoraAgendaHab.with(horaFranja);
                        //Se resta a la hora de la agenda habilitada, los minutos definidos en el parámetro.
                        fechayHoraAgendaMod = fechayHoraAgendaMod.minusMinutes(tiempoMinutos);
                        //se valida si la fecha y hora actual es mayor a la fecha de agenda modificada
                        // si se cumple la condición anterior, asigna valor true, indicando que bloquea esos cupos.
                        bandInhabilitar = fechaActual.isAfter(fechayHoraAgendaMod);
                    }
                }
            }
        }
        return bandInhabilitar;
    }
    
    
    private boolean validarTecnicoAnticipado(OtHhppMgl otDireccion) throws ApplicationException {

        boolean tecnicoAnticipadoRes = false;

        try {

            List<OtHhppTecnologiaMgl> otHhppTecnologiaMglList
                    = otHhppTecnologiaMglFacadeLocal.findOtHhppTecnologiaByOtHhppId(otDireccion.getOtHhppId());

            //JDHT
            OtHhppTecnologiaMgl tecnologiaPrioridadLocation = null;

            if (otHhppTecnologiaMglList != null && !otHhppTecnologiaMglList.isEmpty()) {

                for (OtHhppTecnologiaMgl otHhppTecnologiaMgl : otHhppTecnologiaMglList) {

                    if (otHhppTecnologiaMgl.getTecnoglogiaBasicaId() != null) {
                        //prioridades de tecnologias para extraer location
                        //BI, UNI, DTH, FTTH, GPON, FOU, RFO
                        //BI
                        if (otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                && !otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                && otHhppTecnologiaMgl.getTecnoglogiaBasicaId()
                                        .getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_BID)) {
                            tecnologiaPrioridadLocation = otHhppTecnologiaMgl;
                            break;
                        }

                        //UNI
                        if (otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                && !otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                && otHhppTecnologiaMgl.getTecnoglogiaBasicaId()
                                        .getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_UNI)) {
                            tecnologiaPrioridadLocation = otHhppTecnologiaMgl;
                            break;
                        }

                        //DTH
                        if (otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                && !otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                && otHhppTecnologiaMgl.getTecnoglogiaBasicaId()
                                        .getIdentificadorInternoApp().equalsIgnoreCase(Constant.DTH)) {
                            tecnologiaPrioridadLocation = otHhppTecnologiaMgl;
                            break;
                        }

                        //FTTH
                        if (otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                && !otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                && otHhppTecnologiaMgl.getTecnoglogiaBasicaId()
                                        .getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_FTTTH)) {
                            tecnologiaPrioridadLocation = otHhppTecnologiaMgl;
                            break;
                        }

                        //GPON
                        if (otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                && !otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                && otHhppTecnologiaMgl.getTecnoglogiaBasicaId()
                                        .getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_OP_GPON)) {
                            tecnologiaPrioridadLocation = otHhppTecnologiaMgl;
                            break;
                        }

                        //FOU
                        if (otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                && !otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                && otHhppTecnologiaMgl.getTecnoglogiaBasicaId()
                                        .getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_OP_UNI)) {
                            tecnologiaPrioridadLocation = otHhppTecnologiaMgl;
                            break;
                        }

                        //RFO
                        if (otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                && !otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                && otHhppTecnologiaMgl.getTecnoglogiaBasicaId()
                                        .getIdentificadorInternoApp().equalsIgnoreCase(Constant.RED_FO)) {
                            tecnologiaPrioridadLocation = otHhppTecnologiaMgl;
                            break;
                        }
                    }
                }
            }

            if (tecnologiaPrioridadLocation != null && tecnologiaPrioridadLocation.getNodo() != null) {
                nodoMgl = tecnologiaPrioridadLocation.getNodo();
            }

            CmtComunidadRr comunidadRr = null;

            if (nodoMgl != null) {
                comunidadRr = nodoMgl.getComId();
            } else {
                throw new ApplicationException("No existe nodo de cobertura para la tecnologia de la Ot");
            }

            //Consulta de la extendida tipo de trabajo
            List<CmtExtendidaTipoTrabajoMgl> extendidaTipoTrabajoMgl = extendidaTipoTrabajoMglFacadeLocal.
                    findBytipoTrabajoObjAndCom(comunidadRr, otDireccion.getTipoOtHhppId().getTipoTrabajoOFSC());

            if (extendidaTipoTrabajoMgl != null && !extendidaTipoTrabajoMgl.isEmpty()) {
                CmtExtendidaTipoTrabajoMgl extendida = extendidaTipoTrabajoMgl.get(0);
                String tecnicoAnt = extendida.getTecnicoAnticipado();

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
    
    private boolean validarAgendaInmediata(OtHhppMgl otDireccion) throws ApplicationException {

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
                    findBytipoTrabajoObjAndCom(comunidadRr, otDireccion.getTipoOtHhppId().getTipoTrabajoOFSC());

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
    
    public void reasignarAgenda(MglAgendaDireccion agenda) {
        if (!isRolReasignarAgenda()) {
            FacesUtil.mostrarMensajeWarn("No tiene permisos para reasignar la agenda");
            return;
        }

        try {
            Date fechaHoy = new Date();
            horaSelected = "";
            minSelected = "";
            lstMin = new ArrayList<>();
            lstHoras = new ArrayList<>();
            if (agenda != null) {
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
                } else if (agenda.getFechaInivioVt() != null) {
                    if (!validarAccionAgendamiento(OPCION_EDITAR_INI)) {
                        mensajesValidacion = "La agenda ya inicio visita y no se puede editar, "
                                + "solo el rol especial la puede editar.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        mensajesValidacion, null));
                        return;
                    }
                }else {
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

                ApiFindTecnicosResponse responseTec = agendamientoHhppFacade.
                        consultarTecnicosDisponibles(otHhppSeleccionado, agenda.getNodoMgl(), fechas);
                
                if (responseTec != null && responseTec.getDetail() != null
                        && responseTec.getStatus() != null) {

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    responseTec.getDetail(), null));

                } else if (responseTec != null && responseTec.getItems() != null
                        && !responseTec.getItems().isEmpty()) {
                   
                    tecnicosDisponibles = new ArrayList<>();
                    tecnicosAnticipados = responseTec.getItems();
                    retornaTecnicosDisponibles();
                    reasignar = true;
                    updateAgenda = false;
                    cancelar = false;
                    crearAgenda = false;
                    reagendar = false;
                    consulta = false;
                    razonCancela = "";
                    razonReasigna = "";
                    comentariosOrden = "";
                    this.reasignaAgendaMgl = new ArrayList<>();
                    this.reasignaAgendaMgl.add(agenda);
                    reasignaAgenda = agenda;
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "El servicio de tecnicos anticipados no retorna informacion "
                                    + "de tecnicos disponibles", null));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Ocurrió un error realizando la reasignacion de la agenda:", "La agenda es nula."));
            }

        } catch (ApplicationException | IOException e) {
            exceptionServBean.notifyError(e, "Error en reasignarAgenda: " + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        }

    }
    
    public void abrirPanelTecnicos() throws ApplicationException {

        try {

            if (!validarAccionAgendamiento(OPCION_CREAR_ANTICIPADO)) {
                mensajesValidacion = "No tiene el rol especifico para esta accion.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajesValidacion, null));
                return;
            }
            if (!validarExistenciaAgendaCerrada()) {
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

                int diasAmostrar = tipoOtHhppMgl.getDiasAMostrarAgenda();

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
                idBasicaPrerequisito = null;

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
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "No se puede generar una nueva agenda debido"
                                + " a que existe una cerrada exitosamente", ""));
            }
        } catch (ApplicationException ex) {
            exceptionServBean.notifyError(ex, "Error en abrirPanelTecnicos: " + ex.getMessage(), AGENDAMIENTO_OT_HHPP);
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
                horaInicio="";
                horaSelected = "";
                lstHoras = null;
                minSelected = "";
                lstMin = null;
                
                ApiFindTecnicosResponse responseTec = agendamientoHhppFacade.
                        consultarTecnicosDisponibles(otHhppSeleccionado, nodoMgl, fechas);
                
                if (responseTec != null && responseTec.getDetail() != null
                        && responseTec.getStatus() != null) {

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    responseTec.getDetail(), null));

                } else if (responseTec != null && responseTec.getItems() != null
                        && !responseTec.getItems().isEmpty()) {
                    tecnicosDisponibles = new ArrayList<>();
                    if (reAgendaMgl != null && reAgendaMgl.getAgendaId() != null) {
                        List<Items> lstItems = new ArrayList<>();
                        for (Items items : responseTec.getItems()) {
                            if (items.getResource().getResourceId().
                                    equalsIgnoreCase(reAgendaMgl.getIdentificacionTecnico())) {
                                lstItems.add(items);
                                tecnicoSelected = reAgendaMgl.getIdentificacionTecnico();
                            } else {
                                lstItems.add(items);
                            }
                        }
                        tecnicosAnticipados = lstItems;
                        retornaTecnicosDisponibles();
                        listarFranjas();
                    } else {
                        tecnicosAnticipados = responseTec.getItems();
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
            exceptionServBean.notifyError(e, "Se genera error consultando los tecnicos en AgendamientoOtHhppBean: consultarTecnicos()"
                    + e.getMessage(), AGENDAMIENTO_OT_HHPP);
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
        agenda = new MglAgendaDireccion();
        verPanelTecnicos = false;
        reasignar = false;
        reAgendaMgl = new MglAgendaDireccion();
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
                            
                            reasignaAgenda.setComentarioReasigna(razonReasigna);
                            reasignaAgenda.setIdentificacionTecnico(tecnicoSelected);
                            String idAliadoFin = null;
                            if (tecnicosAnticipados != null && !tecnicosAnticipados.isEmpty()) {
                                for (Items item : tecnicosAnticipados) {
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
                            agendamientoHhppFacade.update(reasignaAgenda, usuarioVT, perfilVt);
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String fechAge = df.format(reasignaAgenda.getFechaAgenda());
                            String msn = "Se ha modificado la agenda:  " + reasignaAgenda.getOfpsOtId() + "  "
                                    + "  para la ot: " + reasignaAgenda.getOrdenTrabajo().getOtHhppId()
                                    + " con el tecnico: " + reasignaAgenda.getNombreTecnico()
                                    + "[" + reasignaAgenda.getIdentificacionTecnico() + "] "
                                    + "para el dia: " + fechAge + " en la hora:  " + reasignaAgenda.getHoraInicio()+ "";

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
            }

        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Ocurrió un error al realizar la reasignación de la agenda: " + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error al realizar la reasignación de la agenda: en AgendamientoOtHhppBean: updateAgendaForTecnicoMgl()" + e.getMessage(), AGENDAMIENTO_OT_HHPP);
        }
        return "";
    }
    
    public void validacionDisponibilidad(DisponibilidadTecnicoDto disponibilidadTecnicoDto,
            int parWorktimeNum, Items item) {

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
            if (franjasDis != null && !franjasDis.isEmpty()) {
                item.setFranjasDisponibles(franjasDis);
                tecnicosDisponibles.add(item);
            }
        }
    }

     public boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error en isNumeric | AgendamientoOtHhppBean : ".concat(e.getMessage()), e);
            return false;
        }
    }
     
    public boolean isNumericLong(String cadena) {
        try {
            Long.parseLong(cadena);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error en isNumericLong | AgendamientoOtHhppBean : ".concat(e.getMessage()), e);
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
    
     public boolean validoFechaHoraAgendas(List<MglAgendaDireccion> agendaList) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        if (agendaList != null && !agendaList.isEmpty()) {
            for (MglAgendaDireccion agendaCom : agendaList) {
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
        long cc;
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
        horaSelected = "";
        minSelected = "";
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
            String tamanio = String.valueOf(ini);
            if (tamanio.length() == 1) {
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
    
     public void abrirIframe() throws ApplicationException {

           if (!validarExistenciaAgendaCerrada()) {
               
               if (otHhppSeleccionado.getNombreContacto() != null && !otHhppSeleccionado.getNombreContacto().isEmpty()
                       && otHhppSeleccionado.getTelefonoContacto()!= null && !otHhppSeleccionado.getTelefonoContacto().isEmpty()
                       && otHhppSeleccionado.getCorreoContacto()!= null && !otHhppSeleccionado.getCorreoContacto().isEmpty()) {

                   OtHhppMgl otHhppActual = otHhppMglFacadeLocal.findOtByIdOt(otHhppSeleccionado.getOtHhppId());
                   otHhppActual.setNombreContacto(otHhppSeleccionado.getNombreContacto());
                   otHhppActual.setTelefonoContacto(otHhppSeleccionado.getTelefonoContacto());
                   otHhppActual.setCorreoContacto(otHhppSeleccionado.getCorreoContacto());
                   //actualiza los datos de la persona que recibe la visita
                   otHhppMglFacadeLocal.update(otHhppActual);

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

        String appNumber = agendamientoHhppFacade.armarNumeroOtOfscHhpp(otHhppSeleccionado);
        String appNumberEncriptado = CmtUtilidadesAgenda.EncriptarAES256(appNumber);

        GeograficoPoliticoMgl geograficoPoliticoMgl
                = otHhppSeleccionado.getDireccionId().getUbicacion().getGpoIdObj();
        request.setCiudad(geograficoPoliticoMgl.getGpoNombre());

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
            
            DireccionMgl direccion = otHhppSeleccionado.getDireccionId();
            if (direccion != null && direccion.getUbicacion() != null) {
                UbicacionMgl ubicacionMgl = direccion.getUbicacion();
                String latitudMod = ubicacionMgl.getUbiLatitud().replace(",", ".");
                String longitudMod = ubicacionMgl.getUbiLongitud().replace(",", ".");
                double lat = Double.parseDouble(latitudMod);
                double lon = Double.parseDouble(longitudMod);
                request.setLatitude(lat);
                request.setLongitude(lon);
            }

            request.setNode(nodoMgl.getNodCodigo());

            String tipoWork = otHhppSeleccionado.getTipoOtHhppId().getTipoTrabajoOFSC() != null ? otHhppSeleccionado.getTipoOtHhppId().getTipoTrabajoOFSC().getAbreviatura().trim() : "";
            request.setActivityType(tipoWork);

            String subTipoWork = otHhppSeleccionado.getTipoOtHhppId().getSubTipoOrdenOFSC() != null ? otHhppSeleccionado.getTipoOtHhppId().getSubTipoOrdenOFSC().getAbreviatura().trim() : "";
            request.setXA_WorkOrderSubtype(subTipoWork);

            CmtExtendidaTecnologiaMgl mglExtendidaTecnologia
                    = extendidaTecnologiaMglFacadeLocal.findBytipoTecnologiaObj(nodoMgl.getNodTipo());

            if (mglExtendidaTecnologia != null
                    && mglExtendidaTecnologia.getIdExtTec() != null
                    && mglExtendidaTecnologia.getNomTecnologiaOFSC() != null) {
                String nombreRed = mglExtendidaTecnologia.getNomTecnologiaOFSC().getAbreviatura();
                request.setXA_Red(nombreRed);
            }

            ParametrosMgl canalAgendaInmediata = parametrosMglFacadeLocal.findParametroMgl(Constant.CANAL_AGENDA_INMEDIATA);
            ParametrosMgl tiempoDuracion = parametrosMglFacadeLocal.findParametroMgl(Constant.TIME_OUT_AGENDA_INMEDIATA_IFRAME); 
            request.setCanal(canalAgendaInmediata.getParValor());
            request.setDuration(tiempoDuracion.getParValor());

            String js = JSONUtils.objetoToJson(request);

            String jsEncriptado = CmtUtilidadesAgenda.EncriptarAES256(js);

            urlIframe = urlAtencionInm.getParValor() + "apptNumber=" + appNumberEncriptado + "&request=" + jsEncriptado + "";

        } catch (ApplicationException ex) {
            String msg = ex.getMessage();
            exceptionServBean.notifyError(ex, msg, AGENDAMIENTO_OT_HHPP);
            abrirPanelIframe = false;
            consulta = true;
            listInfoByPage(1);
        }
        
         } else {
             FacesContext.getCurrentInstance().addMessage(null,
                     new FacesMessage(FacesMessage.SEVERITY_WARN,
                             "No se puede generar una nueva agenda debido"
                             + " a que existe una cerrada exitosamente", ""));
         }

    }

    public void cerrarIframe() {
        abrirPanelIframe = false;
        consulta = true;
        agendaInmediataPreIframe = false;
        listInfoByPage(1);
    }

    public void cerrarAgendaInmediata() {
        abrirPanelIframe = false;
        consulta = true;
        agendaInmediataPreIframe = false;
        listInfoByPage(1);
    }
     
       
    public void crearAgendaPendiente(RequestAgendaInmediataMgl requestAgendaInmediataMgl) 
            throws ApplicationException {

        if (validarExistenciaAgendaCerrada()) {
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
    
    public void abrirAgendaInmediata() throws ApplicationException{
        if (otHhppSeleccionado != null) {
            if (otHhppSeleccionado.getOnyxOtHija() != null) {
                abrirPanelIframe = false;
                agendaInmediataPreIframe = true;
                consulta = false;

            } else {
                List<OnyxOtCmDir> listaOnyx = onyxOtCmDirlFacadeLocal.findOnyxOtHhppById(otHhppSeleccionado.getOtHhppId());
                if (listaOnyx != null && !listaOnyx.isEmpty()) {
                    abrirPanelIframe = false;
                    agendaInmediataPreIframe = true;
                    consulta = false;
                } else {
                    if (otHhppSeleccionado.getTipoOtHhppId() != null
                            && otHhppSeleccionado.getTipoOtHhppId().getRequiereOnyx()
                            != null && otHhppSeleccionado.getTipoOtHhppId().
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
    
    public boolean validaFechaAgendamiento(MglAgendaDireccion agenda){
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
                }else if (fechaDate2.before(fechaDate1)) {
                    LOGGER.info("La Fecha de la agenda es Mayor a la de hoy ");
                } else {
                    LOGGER.info("Las Fechas Son iguales ");
                    actualmente = true;
                }                
            } catch (ParseException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
           return respuesta;
       }

}
