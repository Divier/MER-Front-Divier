/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.CmtReporteEstadoActualOtDirHhppDto;
import co.com.claro.mgl.dtos.ReporteOtDIRDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.TipoOtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOrdenTrabajoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoOtMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDetalleFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author cardenaslb
 */
@ManagedBean(name = "reporteEstadoActualOtDirBean")
@SessionScoped
public class ReporteEstadoActualOtDirBean {
     
    private String message;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private boolean cargar;
    private Date fechaInicio;
    private Date fechaFinal;
    private SecurityLogin securityLogin;
    private int perfilVT = 0;
    private String usuarioVT = null;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(ReporteEstadoActualOtDirBean.class);
    @EJB
    private CmtTipoBasicaMglFacadeLocal tipoBasicaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosFacade;
    @EJB
    private  CmtTipoOtMglFacadeLocal cmtTipoOtMglFacadeLocal;
    @EJB
    private CmtOrdenTrabajoMglFacadeLocal ordenTrabajoMglFacadeLocal;
    @EJB
    private TipoOtHhppMglFacadeLocal tipoOtHhppMglFacadeLocal;
    private List<ReporteOtDIRDto> listReporteOtDIRDto;
    private BigDecimal estadoSelected;
    private int filasPag = ConstantsCM.PAGINACION_OCHO_FILAS;
    private boolean habilitaObj = false;
    private int actual;
    private String numPagina;
    private boolean panelPintarPaginado = false;
    private boolean pintarPaginado = true;
    private boolean panelExportar = false;
    private boolean cantRegistros = false;
    private boolean mensajeExcel = false;
    private boolean mensajeCvsTxt = false;
    private boolean btnExcel = false;
    private boolean btnCvsTxt = false;
    private boolean btnVolver = false;
    private boolean btnReporte = true;
    private boolean panelFormulario = true;
    private List<Integer> paginaList;
    private String pageActual;
    private int page = 0;
    private int numRegAProcesar;
    private int numRegProcesados;
    private int numRegTotal;
    private static int NUM_REGISTROS_PAGINA = 50;
    private static String[] NOM_COLUMNAS = {"Numero OT MGL",
        "Tipo Ot MER", "Sub tipo OT MER", "Sub tipo de OFSC",
        "Estado Interno MER",  "Fecha creacion OT MER","Tecnologia MER",
        "Codigo CCMM/Direccion MER", "Usuario Creacion OT MER", "Usuario Edicion OT MER",
        "OT hija ONYX", "Departamento",
        "Orden RR","Sub tipo orden OFSC","Fecha agenda OFSC","Usuario Creacion Agenda","Usuario Edicion Agenda","Time slot OFSC",
        "Estado agenda OFSC","Fecha Inicio agenda OFSC","Fecha Fin agenda OFSC","Id aliado OFSC","Nombre aliado OFSC",
        "Id tecnico de aliado OFSC","Nombre Tecnico aliado OFSC","Ultima Agenda de multiagenda","Observaciones Tecnico OFSC",
        "Complejidad ONYX","NIT cliente ONYX ", "Nombre cliente ONYX", 
        "Nombre OT hija ONYX", "Numero OT hija Dir","Fecha creacion OT hija ONYX",
        "Direccion ONYX", "Descripcion ONYX", "Segmento ONYX","Tipo servicio ONYX",
        "Servicios ONYX","Recurrente mensual ONYX","Codigo servicio ONYX","Vendedor ONYX",
        "Telefono vendedor ONYX","Estado OT hija ONYX",
        "Estado OT padre ONYX","Fecha compromiso OT padre ONYX",
        "OT Padre Resolucion 1 ONYX","OT Padre Resolucion 2 ONYX","OT Padre Resolucion 3 ONYX","OT Padre Resolucion 4 ONYX",
        "OT Hija Resolucion 1 ONYX","OT Hija Resolucion 2 ONYX","OT Hija Resolucion 3 ONYX","OT Hija Resolucion 4 ONYX",
        "Numero OT padre ONYX", "Fecha Creacion OT padre ONYX ",
        "Contacto Tecnico OT padre ONYX","Telefono tecnico OT padre ONYX","Appt number OFSC"};
    
    private FacesContext facesContextTh = FacesContext.getCurrentInstance();
    private HttpSession sessionTh = (HttpSession) facesContextTh.getExternalContext().getSession(true);
    private boolean finalizado = false;
    private boolean iniciado = false;
    private HashMap<String, Object> params;
    private List<CmtBasicaMgl> listTablaBasicaTecnologias;
    private BigDecimal tecnologia;
    private String nodo;
    private BigDecimal estrato;
    private List<CmtBasicaMgl> listaTablaBasicaEstratos;
    private Integer refresh;
    private static int REFRESH_MAX = 100000;
    private static int REFRESH_MIN = 10000;
    private final Locale LOCALE = new Locale("es", "CO");
    private boolean finish;
    private boolean progress;
    private int minutos;
    private String xlsMaxReg;
    private String usuarioProceso;
    private String csvMaxReg;
    private String txtMaxReg;
    private List<CmtBasicaMgl> tipoGeneralOrdenTrabajo;
     private List<CmtBasicaMgl> listBasicaSubtipoOrdenOFSC;
    private CmtOrdenTrabajoMgl ordenTrabajo;
    private List<CmtTipoOtMgl> subTipoOrdenTrabajo;
    private List<BigDecimal> subTipoOrdenOfscSelected;
    private BigDecimal estadoOtId;
    private List<CmtDetalleFlujoMgl> detalleFlujoEstActual;
    private List<CmtBasicaMgl> listTablaBasicaEstadoInternoOt;
    private BigDecimal subTipoTrabaoListSelect;
    private List<BigDecimal> estadoInternolist;
    private List<BigDecimal> tipoOrden;
    private List<BigDecimal> subtipoOrden;
    private Integer otIni;
    private Integer otFin;
    private Date fechaInicioOt = null;
    private Date fechaFinOt = null;
    private Date fechaInicioOtOnyx = null;
    private Date fechaFinOtOnyx = null;
    private Date fechaInicioAgendOFSC = null;
    private Date fechaFinAgendOFSC = null;
    private Date fechaInicioAsigTecnico = null;
    private Date fechaFinAsigTecnico = null;
    private Date fechaInicioCierreAgenda = null;
    private Date fechaFinCierreAgenda = null;
    private Date fechaInicioCancAgenda = null;
    private Date fechaFinCancAgenda = null;
    private Date fechaInicioReagenda = null;
    private Date fechaFinReagenda = null;
    private Date fechaInicioSuspension = null;
    private Date fechaFinSuspension = null;
    private String filtroFechas;
    private boolean panelCreacionOt;
    private boolean panelcreacionONYX;
    private boolean panelAgendamientoOFSC;
    private boolean panelAsignaciontecnicoOFSC;
    private boolean panelCierreagendaOFSC;
    private boolean panelCancelacionOFSC;
    private boolean panelReagendamientoOFSC;
    private boolean panelSuspensionOFSC;
    private List<TipoOtHhppMgl> tipoOtHhppList;
    private List<CmtBasicaMgl> estadoGeneralList;
    private List<BigDecimal> tipoOrdenOfscSelected;
    private String nombreUsuarioArea ;
    private String nombreUser ;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    private String areaResponsable;
     private boolean btnLimpiar = false;
  
    public ReporteEstadoActualOtDirBean() {
        try {
            securityLogin = new SecurityLogin(FacesContext.getCurrentInstance());
             if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();


        } catch (IOException e) {
           FacesUtil.mostrarMensajeError("Error al ReporteEstadoActualOtCMBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ReporteEstadoActualOtCMBean. " + e.getMessage(), e, LOGGER);
        }

    }

    @PostConstruct
    public void fillSqlList() {

        try {
            this.minutos = tiempoReporte(Constant.TIEMPO_REPORTE);
            btnVolver = true;
            // Sub Tipo de Trabajo
             CmtTipoBasicaMgl tipoGeneralOrdenId;
            tipoGeneralOrdenId = tipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_GENERAL_OT);
            tipoGeneralOrdenTrabajo = basicaMglFacadeLocal.findByTipoBasica(tipoGeneralOrdenId);
            // tipo de ordenes OFSC
             CmtTipoBasicaMgl tipoBasicaSubTipoOrdenOFSC;
            tipoBasicaSubTipoOrdenOFSC = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_SUB_TIPO_TRABAJO_DIRECCIONES);
            listBasicaSubtipoOrdenOFSC = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaSubTipoOrdenOFSC);
             //Consulta los estados internos
                         CmtTipoBasicaMgl tipoBasica;
            tipoBasica = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.ESTADO_GENERAL_OT_HHPP);
            estadoGeneralList
                    = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasica);
             
            
            CmtTipoBasicaMgl tipoBasicaTipoEstadoInternoOt;
            tipoBasicaTipoEstadoInternoOt = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_INTERNO_OT);
            listTablaBasicaEstadoInternoOt = cmtBasicaMglFacadeLocal.
                    findByTipoBasica(tipoBasicaTipoEstadoInternoOt);
            //tipo de trabajo
             tipoOtHhppList = tipoOtHhppMglFacadeLocal.findAll();
         
            setProgress(false);
            setFinish(false);
            panelCreacionOt = true;
            limpiarValores();
        } catch (ApplicationException e) {
          FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:fillSqlList(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:fillSqlList() " + e.getMessage(), e, LOGGER);
        }

    }

    public void getReporte() {
        
            btnExcel = false;
            btnCvsTxt = false;
            buscar();
            obtenerReporte();
        
    }
    
    
    public void obtenerReporte() {
        try {
            nombreUsuarioArea = getNombreUsuario() + ", AREA: "+ getAreaResponsable();
            if (validarFechaCreacionOt() && filtroFechas != null) {
                processar();
            } else if (validarFechaInicioOtOnyx() && filtroFechas != null) {
                processar();
            } else if (validarFechaInicioAgendOFSC() && filtroFechas != null) {
                processar();
            } else if (validarFechaFinAsigTecnico() && filtroFechas != null) {
                processar();
            } else if (validarFechaInicioCierreAgenda() && filtroFechas != null) {
                processar();
            } else if (validarFechaInicioCancAgenda() && filtroFechas != null) {
                processar();
            } else if (validarFechaInicioReagenda() && filtroFechas != null) {
                processar();
            } else if (validarFechaInicioSuspension() && filtroFechas != null) {
                processar();
            } else if (!tipoOrden.isEmpty() && tipoOrden != null) {
                processar();
            } else if (!subtipoOrden.isEmpty() && subtipoOrden != null) {
                processar();
            } else if (!estadoInternolist.isEmpty() && estadoInternolist != null) {
                processar();
            } else if (!subTipoOrdenOfscSelected.isEmpty() && subTipoOrdenOfscSelected != null) {
                processar();
            } else if (!tipoOrdenOfscSelected.isEmpty() && tipoOrdenOfscSelected != null) {
                processar();
            } else if (otFin != null && otIni != null) {
                processar();
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Ingrese el tipo de fecha a filtrar", ""));
            }
           
            
            
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:obtenerReporte(). " + e.getMessage(), e, LOGGER);
        }


    }
    
    public void processar() throws ApplicationException {
            btnVolver = true;
            panelFormulario = true;
            btnReporte = true;
            listReporteOtDIRDto = new ArrayList<>();
            numRegAProcesar = 0;
            numRegAProcesar = ordenTrabajoMglFacadeLocal.findReporteOtDIRTotal(params, usuarioVT);
            if (numRegAProcesar > 0) {  
                btnReporte = false;  
            } else {
                btnReporte = true;
                panelPintarPaginado = false;
                setProgress(false);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "No se encontraron resultados para la consulta.", ""));
            }
           
    }

    public boolean IsStarted() {
        if (CmtReporteEstadoActualOtDirHhppDto.getNumeroRegistrosAProcesar() == 0) {
            iniciado = true;
        } else {
            iniciado = false;
        }
        return iniciado;
    }

    public boolean IsFinished() {
        if (CmtReporteEstadoActualOtDirHhppDto.isIsFinished()) {
            finalizado = true;
        }
        return finalizado;
    }

    /**
     * bocanegravm metodo para verificar si ya termino el proceso del reporte
     *
     * @return
     */
    public boolean getReady() {
        if (CmtReporteEstadoActualOtDirHhppDto.getEndProcessDate() != null
                && !new Date().before(sumarTiempoFecha(CmtReporteEstadoActualOtDirHhppDto.getEndProcessDate(), this.minutos))) {
            limpiarValores();
            setProgress(false);
            setFinish(false);
            try {
                CmtReporteEstadoActualOtDirHhppDto.cleanBeforeStart();
            } catch (ApplicationException ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                LOGGER.error(msg);
            }
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("formPrincipal");
            FacesContext.getCurrentInstance().getPartialViewContext().setRenderAll(true);
        }

        if (this.progress) {
            this.finish = CmtReporteEstadoActualOtDirHhppDto.isIsFinished();
            if (this.finish) {
                this.refresh = REFRESH_MIN;
            }
        } else {
            this.finish = false;
        }
        return this.finish;
    }

    /**
     * metodo para sumar minutos a la fecha de terminacion del reporte
     *
     * @param fecha
     * @param minutos
     * @return
     */
    private Date sumarTiempoFecha(Date fecha, int minutos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MINUTE, minutos);
        return calendar.getTime();
    }

    /**
     * bocanegravm metodo para resetear los parametros de busqueda
     */
    public void limpiarValores() {
         numRegAProcesar = 0;
        btnCvsTxt = false;
        btnExcel = false;
        fechaInicioOt = null;
        fechaFinOt = null;
        fechaInicioOtOnyx = null;
        fechaFinOtOnyx = null;
        fechaInicioAgendOFSC = null;
        fechaFinAgendOFSC = null;
        fechaInicioAsigTecnico = null;
        fechaFinAsigTecnico = null;
        fechaInicioCierreAgenda = null;
        fechaFinCierreAgenda = null;
        fechaInicioCancAgenda = null;
        fechaFinCancAgenda = null;
        fechaInicioReagenda = null;
        fechaFinReagenda = null;
        fechaInicioSuspension = null;
        fechaFinSuspension = null;
        tipoOrden = null;
        subTipoOrdenOfscSelected = null;
        estadoInternolist = null;
        otIni = null;
        otFin = null;
        filtroFechas = "";
        tipoOrdenOfscSelected = null;
        numRegProcesados = 0;
        nombreUsuarioArea = "";    
    }


 

    public void volverList() {       
            btnReporte = true;
            limpiarValores();
            setProgress(false);
            setFinish(false);          
            panelPintarPaginado = false;      
    }




    public String downloadCvsTxtGen(List<ReporteOtDIRDto> listaCM,String ext) {
           try {
               final StringBuffer sb = new StringBuffer();
            SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");
               if (!listaCM.isEmpty()) {
                   int numeroRegistros = listaCM.size();
                   int numPaginas = numeroRegistros / NUM_REGISTROS_PAGINA;
                   byte[] csvData = null;
                   if (numeroRegistros > 0) {
                           for (int j = 0; j < NOM_COLUMNAS.length; j++) {
                               sb.append(NOM_COLUMNAS[j]);
                               if (j < NOM_COLUMNAS.length) {
                                   sb.append(";");
                               }
                           }
                           
                           sb.append("\n");

                            for (ReporteOtDIRDto report : listReporteOtDIRDto) {

                                String otId = report.getOt_Direccion_Id() == null ? " " : report.getOt_Direccion_Id();
                                sb.append(otId);
                                sb.append(";");
                                String tipoOt = report.getSub_tipo_OT_MER() == null ? " " :  StringUtils.stripAccents(report.getSub_tipo_OT_MER());
                                sb.append(tipoOt);
                                sb.append(";");
                                String subTipoOt = report.getTipo_OT_MER()== null ? " " :  StringUtils.stripAccents(report.getTipo_OT_MER());
                                sb.append(subTipoOt);
                                sb.append(";");
                                String subTipoOtOfsc = report.getSubTipo_Orden_OFSC() == null ? " " : report.getSubTipo_Orden_OFSC();
                                sb.append(subTipoOtOfsc);
                                sb.append(";");
                                String estadoOt = report.getEstado_interno_OT_MER() == null ? " " : report.getEstado_interno_OT_MER();
                                sb.append(estadoOt);
                                sb.append(";");
                                String fechaCreacionOt = report.getFecha_creacion_OT_MER() == null ? " " : report.getFecha_creacion_OT_MER();
                                sb.append(fechaCreacionOt);
                                sb.append(";");
                                String tecnologia = report.getTecnologia_OT_MGL() != null ? report.getTecnologia_OT_MGL() : "";
                                sb.append(tecnologia);
                                sb.append(";");
                                String direccionOt = report.getDireccionMer() != null ? report.getDireccionMer() : "";
                                sb.append(direccionOt);
                                sb.append(";");
                                String usuarioCreacionOt = report.getUsuario_Creacion_OT_MER() == null ? " " : report.getUsuario_Creacion_OT_MER();
                                sb.append(usuarioCreacionOt);
                                sb.append(";");
                                String usuarioEdicionOt = report.getUsuarioModOt() == null ? " " : report.getUsuarioModOt();
                                sb.append(usuarioEdicionOt);
                                sb.append(";");
                                String onyxOtCm = report.getOnyx_Ot_Hija_Dir() != null ? StringUtils.stripAccents(report.getOnyx_Ot_Hija_Dir()) : "";
                                sb.append(onyxOtCm);
                                sb.append(";");
                                String departamento = report.getDepartamento() != null ? report.getDepartamento() : "";
                                sb.append(departamento);
                                sb.append(";");
                                String orden_RR = report.getOrden_RR() != null ? report.getOrden_RR() : "";
                                sb.append(orden_RR);
                                sb.append(";");
                                String subTipo_Orden_OFSC = report.getSubTipo_Orden_OFSC() != null ? report.getSubTipo_Orden_OFSC() : "";
                                sb.append(subTipo_Orden_OFSC);
                                sb.append(";");
                                String fecha_agenda_OFSC = report.getFecha_agenda_OFSC() != null ? report.getFecha_agenda_OFSC() : "";
                                sb.append(fecha_agenda_OFSC);
                                sb.append(";");
                                String usuario_creacion_agenda_OFSC = report.getUsuario_creacion_agenda_OFSC() != null ? report.getUsuario_creacion_agenda_OFSC() : "";
                                sb.append(usuario_creacion_agenda_OFSC);
                                sb.append(";");
                                String usuarioEdicionAgenda = report.getUsuarioModAgenda() == null ? " " : report.getUsuarioModAgenda();
                                sb.append(usuarioEdicionAgenda);
                                sb.append(";");
                                String time_slot_OFSC = report.getTime_slot_OFSC() != null ? report.getTime_slot_OFSC() : "";
                                sb.append(time_slot_OFSC);
                                sb.append(";");
                                String estado_agenda_OFSC = report.getEstado_agenda_OFSC() != null ? report.getEstado_agenda_OFSC() : "";
                                sb.append(estado_agenda_OFSC);
                                sb.append(";");
                                String fecha_inicia_agenda_OFSC = report.getFecha_inicia_agenda_OFSC() != null ? report.getFecha_inicia_agenda_OFSC() : "";
                                sb.append(fecha_inicia_agenda_OFSC);
                                sb.append(";");
                                String fecha_fin_agenda_OFSC = report.getFecha_fin_agenda_OFSC() != null ? report.getFecha_fin_agenda_OFSC() : "";
                                sb.append(fecha_fin_agenda_OFSC);
                                sb.append(";");

                                String id_aliado_OFSC = report.getId_aliado_OFSC() != null ? report.getId_aliado_OFSC() : "";
                                sb.append(id_aliado_OFSC);
                                sb.append(";");
                                String nombre_aliado_OFSC = report.getNombre_aliado_OFSC() != null ? report.getNombre_aliado_OFSC() : "";
                                sb.append(nombre_aliado_OFSC);
                                sb.append(";");
                                String id_tecnico_aliado_OFSC = report.getId_tecnico_aliado_OFSC() != null ? report.getId_tecnico_aliado_OFSC() : "";
                                sb.append(id_tecnico_aliado_OFSC);
                                sb.append(";");
                                String nombre_tecnico_aliado_OFSC = report.getNombre_tecnico_aliado_OFSC() != null ? report.getNombre_tecnico_aliado_OFSC() : "";
                                sb.append(nombre_tecnico_aliado_OFSC);
                                sb.append(";");
                                String multiAgenda = report.getUltima_agenda_multiagenda() != null ? report.getUltima_agenda_multiagenda() : "";
                                sb.append(multiAgenda);
                                sb.append(";");
                                String observaciones_tecnico_OFSC = report.getObservaciones_tecnico_OFSC() != null ? StringUtils.stripAccents(report.getObservaciones_tecnico_OFSC())  : "";
                                sb.append(observaciones_tecnico_OFSC);
                                sb.append(";");
                                String complejidadServicio = report.getComplejidadServicio() != null ? report.getComplejidadServicio() : "";
                                sb.append(complejidadServicio);
                                sb.append(";");
                                String nitOnyx = report.getNit_Cliente_Onyx() != null ? report.getNit_Cliente_Onyx() : "";
                                sb.append(nitOnyx);
                                sb.append(";");
                                String nombreOnyx = report.getNombre_Cliente_Onyx() != null ? StringUtils.stripAccents(report.getNombre_Cliente_Onyx()) : "";
                                sb.append(nombreOnyx);
                                sb.append(";");
                                String tec = report.getNombre_Ot_Hija_Onyx() != null ? StringUtils.stripAccents(report.getNombre_Ot_Hija_Onyx()) : "";
                                sb.append(tec);
                                sb.append(";");
                                String numeroOtOnyx = report.getOnyx_Ot_Hija_Dir() != null ? StringUtils.stripAccents(report.getOnyx_Ot_Hija_Dir()) : "";
                                sb.append(numeroOtOnyx);
                                sb.append(";");
                                String fechaCreacionHija = report.getFecha_Creacion_Ot_Hija_Onyx() != null ? report.getFecha_Creacion_Ot_Hija_Onyx() : "";
                                sb.append(fechaCreacionHija);
                                sb.append(";");
                                String direccionOnyx = report.getDireccion_Onyx() != null ? StringUtils.stripAccents(report.getDireccion_Onyx()) : "";
                                sb.append(direccionOnyx);
                                sb.append(";");
                                String descripcionOnyx = report.getDescripcion_Onyx() != null ? report.getDescripcion_Onyx() : "";
                                sb.append(descripcionOnyx);
                                sb.append(";");
                                String segmento_Onyx = report.getSegmento_Onyx() != null ? StringUtils.stripAccents(report.getSegmento_Onyx())  : "";
                                sb.append(segmento_Onyx);
                                sb.append(";");
                                String tipo_Servicio_Onyx = report.getTipo_Servicio_Onyx() != null ? report.getTipo_Servicio_Onyx() : "";
                                sb.append(tipo_Servicio_Onyx);
                                sb.append(";");
                                String servicios_Onyx = report.getServicios_Onyx() != null ? report.getServicios_Onyx() : "";
                                sb.append(servicios_Onyx);
                                sb.append(";");
                                String recurrente_Mensual_Onyx = report.getRecurrente_Mensual_Onyx() != null ? report.getRecurrente_Mensual_Onyx() : "";
                                sb.append(recurrente_Mensual_Onyx);
                                sb.append(";");
                                String codigo_Servicio_Onyx = report.getCodigo_Servicio_Onyx() != null ? report.getCodigo_Servicio_Onyx() : "";
                                sb.append(codigo_Servicio_Onyx);
                                sb.append(";");
                                String vendedor_Onyx = report.getVendedor_Onyx() != null ? StringUtils.stripAccents(report.getVendedor_Onyx()) : "";
                                sb.append(vendedor_Onyx);
                                sb.append(";");
                                String telefono_Vendedor_Onyx = report.getTelefono_Vendedor_Onyx() != null ? report.getTelefono_Vendedor_Onyx() : "";
                                sb.append(telefono_Vendedor_Onyx);
                                sb.append(";");
                                String estado_Ot_Hija_Onyx_Cm = report.getEstado_Ot_Hija_Onyx_Dir()!= null ? StringUtils.stripAccents(report.getEstado_Ot_Hija_Onyx_Dir())  : "";
                                sb.append(estado_Ot_Hija_Onyx_Cm);
                                sb.append(";");
                                String estado_Ot_Padre_Onyx_Cm = report.getEstado_Ot_Padre_Onyx_Dir()!= null ? StringUtils.stripAccents(report.getEstado_Ot_Padre_Onyx_Dir()) : "";
                                sb.append(estado_Ot_Padre_Onyx_Cm);
                                sb.append(";");
                                String fecha_Compromiso_Ot_Padre_Onyx = report.getFecha_Compromiso_Ot_Padre_Onyx() != null ? report.getFecha_Compromiso_Ot_Padre_Onyx() : "";
                                sb.append(fecha_Compromiso_Ot_Padre_Onyx);
                                sb.append(";");
                                String ot_Hija_Resolucion_1_Onyx = report.getOt_Hija_Resolucion_1_Onyx() != null ? StringUtils.stripAccents(report.getOt_Hija_Resolucion_1_Onyx()) : "";
                                sb.append(ot_Hija_Resolucion_1_Onyx);
                                sb.append(";");
                                String ot_Hija_Resolucion_2_Onyx = report.getOt_Hija_Resolucion_2_Onyx() != null ? StringUtils.stripAccents(report.getOt_Hija_Resolucion_2_Onyx()) : "";
                                sb.append(ot_Hija_Resolucion_2_Onyx);
                                sb.append(";");
                                String ot_Hija_Resolucion_3_Onyx = report.getOt_Hija_Resolucion_3_Onyx() != null ? StringUtils.stripAccents(report.getOt_Hija_Resolucion_3_Onyx()) : "";
                                sb.append(ot_Hija_Resolucion_3_Onyx);
                                sb.append(";");
                                String ot_Hija_Resolucion_4_Onyx = report.getOt_Hija_Resolucion_4_Onyx() != null ? StringUtils.stripAccents(report.getOt_Hija_Resolucion_4_Onyx()) : "";
                                sb.append(ot_Hija_Resolucion_4_Onyx);
                                sb.append(";");
                                String ot_Padre_Resolucion_1_Onyx = report.getOt_Padre_Resolucion_1_Onyx() != null ? StringUtils.stripAccents(report.getOt_Padre_Resolucion_1_Onyx()) : "";
                                sb.append(ot_Padre_Resolucion_1_Onyx);
                                sb.append(";");
                                String ot_Padre_Resolucion_2_Onyx = report.getOt_Padre_Resolucion_2_Onyx() != null ? StringUtils.stripAccents(report.getOt_Padre_Resolucion_2_Onyx()) : "";
                                sb.append(ot_Padre_Resolucion_2_Onyx);
                                sb.append(";");
                                String ot_Padre_Resolucion_3_Onyx = report.getOt_Padre_Resolucion_3_Onyx() != null ? StringUtils.stripAccents(report.getOt_Padre_Resolucion_3_Onyx()) : "";
                                sb.append(ot_Padre_Resolucion_3_Onyx);
                                sb.append(";");
                                String ot_Padre_Resolucion_4_Onyx = report.getOt_Padre_Resolucion_4_Onyx() != null ? StringUtils.stripAccents(report.getOt_Padre_Resolucion_4_Onyx()) : "";
                                sb.append(ot_Padre_Resolucion_4_Onyx);
                                sb.append(";");
                                String numeroOtPadre = report.getOnyx_Ot_Padre_Dir() != null ? report.getOnyx_Ot_Padre_Dir() : "";
                                sb.append(numeroOtPadre);
                                sb.append(";");
                                String fechaCreacionOtPadre = report.getFecha_Creacion_Ot_Padre_Onyx() != null ? report.getFecha_Creacion_Ot_Padre_Onyx() : "";
                                sb.append(fechaCreacionOtPadre);
                                sb.append(";");
                                String contactoTecnico = report.getContacto_Tecnico_Ot_Padre_Onyx() != null ? StringUtils.stripAccents(report.getContacto_Tecnico_Ot_Padre_Onyx()) : "";
                                sb.append(contactoTecnico);
                                sb.append(";");
                                String telefonoTecnico = report.getTelefono_Tecnico_Ot_Padre_Onyx() != null ? report.getTelefono_Tecnico_Ot_Padre_Onyx() : "";
                                sb.append(telefonoTecnico);
                                sb.append(";");
                                String apptnumberOFSC = report.getAppt_number_OFSC() != null ? report.getAppt_number_OFSC() : "";
                                sb.append(apptnumberOFSC);
                                sb.append(";");
                                sb.append("\n");
                           }

                   }
                   csvData = sb.toString().getBytes();
                   String todayStr = formato.format(new Date());
                   String fileName = "Reporte_actual_Ordenes_Direcciones" + "_" + todayStr + "." + ext;
                   FacesContext fc = FacesContext.getCurrentInstance();
                   HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
                   response.setHeader("Content-disposition", "attached; filename=\""
                           + fileName + "\"");
                   response.setContentType("application/force.download");
                   response.getOutputStream().write(csvData);
                   response.setCharacterEncoding("UTF-8");
                   response.getOutputStream().flush();
                   response.getOutputStream().close();
                   fc.responseComplete();
               } else {
                   FacesContext.getCurrentInstance().addMessage(null,
                           new FacesMessage(FacesMessage.SEVERITY_ERROR,
                           "No se encontraron registros para el reporte", ""));
               }

          } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:downloadCvsTxtGen. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:downloadCvsTxtGen. " + e.getMessage(), e, LOGGER);
        }
        return "case1";
    }

    public void downloadCvsTxt(String ext) {
        try {
            //exportCsv(ext);
            limpiarValores(); 
        } catch (Exception e) {
            String msn = "Se presenta error al filtrar los registros: " + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn, e);
        }
    }

    public boolean validarFechaCreacionOt() {
           boolean respuesta = false;
        try {
                if (validarFecha(fechaInicioOt) && validarFecha(fechaFinOt)) {
                    if (fechaInicioOt.before(fechaFinOt)) {
                        System.err.println("Fecha Inicio  es menor ");
                        respuesta = true;
                    } else {
                        if (fechaFinOt.before(fechaInicioOt)) {
                            System.err.println("La Fecha Inicio es Mayor ");
                            respuesta = false;
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "La fecha Inicial es Mayor a la Final.", ""));
                        } else {
                            respuesta = true;
                        }
                    }
                }else{
                    respuesta = false;
                }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:validarFechas(). " + e.getMessage(), e, LOGGER);
        }

        return respuesta;
    }

    public boolean validarFechaInicioOtOnyx() {
        boolean respuesta = false;
        try {
            if (validarFecha(fechaInicioOtOnyx) && validarFecha(fechaFinOtOnyx)) {
                if (fechaInicioOtOnyx.before(fechaFinOtOnyx)) {
                    System.err.println("Fecha Inicio  es menor ");
                    respuesta = true;
                } else {
                    if (fechaFinOtOnyx.before(fechaInicioOtOnyx)) {
                        System.err.println("La Fecha Inicio es Mayor ");
                        respuesta = false;
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "La fecha Inicial es Mayor a la Final.", ""));
                    } else {
                        respuesta = true;
                    }
                }
            }else{
                    respuesta = false;
                }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:validarFechas(). " + e.getMessage(), e, LOGGER);
        }

        return respuesta;
    }
    public boolean validarFechaInicioAgendOFSC() {
        boolean respuesta = false;
        try {
            if (validarFecha(fechaInicioAgendOFSC) && validarFecha(fechaFinAgendOFSC)) {
                if (fechaInicioAgendOFSC.before(fechaFinAgendOFSC)) {
                    System.err.println("Fecha Inicio  es menor ");
                    respuesta = true;
                } else {
                    if (fechaFinAgendOFSC.before(fechaInicioAgendOFSC)) {
                        System.err.println("La Fecha Inicio es Mayor ");
                        respuesta = false;
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "La fecha Inicial es Mayor a la Final.", ""));
                    } else {
                        respuesta = true;
                    }
                }
            }else{
                    respuesta = false;
                }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:validarFechas(). " + e.getMessage(), e, LOGGER);
        }

        return respuesta;
    }
    
    
    public boolean validarFechaFinAsigTecnico() {
        boolean respuesta = false;
        try {
            if (validarFecha(fechaInicioAsigTecnico) && validarFecha(fechaFinAsigTecnico)) {
                if (fechaInicioAsigTecnico.before(fechaFinAsigTecnico)) {
                    System.err.println("Fecha Inicio  es menor ");
                    respuesta = true;
                } else {
                    if (fechaFinAsigTecnico.before(fechaInicioAsigTecnico)) {
                        System.err.println("La Fecha Inicio es Mayor ");
                        respuesta = false;
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "La fecha Inicial es Mayor a la Final.", ""));
                    } else {
                        respuesta = true;
                    }
                }
            } else {
                respuesta = false;
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:validarFechas(). " + e.getMessage(), e, LOGGER);
        }

        return respuesta;
    }
    
    public boolean validarFechaInicioCierreAgenda() {
        boolean respuesta = false;
        try {
            if (validarFecha(fechaInicioCierreAgenda) && validarFecha(fechaFinCierreAgenda)) {
                if (fechaInicioCierreAgenda.before(fechaFinCierreAgenda)) {
                    System.err.println("Fecha Inicio  es menor ");
                    respuesta = true;
                } else {
                    if (fechaFinCierreAgenda.before(fechaInicioCierreAgenda)) {
                        System.err.println("La Fecha Inicio es Mayor ");
                        respuesta = false;
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "La fecha Inicial es Mayor a la Final.", ""));
                    } else {
                        respuesta = true;
                    }
                }
            }else{
                    respuesta = false;
                }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:validarFechas(). " + e.getMessage(), e, LOGGER);
        }

        return respuesta;
    }
    public boolean validarFechaInicioCancAgenda() {
        boolean respuesta = false;
        try {
            if (validarFecha(fechaInicioCancAgenda) && validarFecha(fechaFinCancAgenda)) {
                if (fechaInicioCancAgenda.before(fechaFinCancAgenda)) {
                    System.err.println("Fecha Inicio  es menor ");
                    respuesta = true;
                } else {
                    if (fechaFinCancAgenda.before(fechaInicioCancAgenda)) {
                        System.err.println("La Fecha Inicio es Mayor ");
                        respuesta = false;
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "La fecha Inicial es Mayor a la Final.", ""));
                    } else {
                        respuesta = true;
                    }
                }
            }else{
                    respuesta = false;
                }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:validarFechas(). " + e.getMessage(), e, LOGGER);
        }

        return respuesta;
    }
    
       public boolean validarFechaInicioReagenda() {
        boolean respuesta = false;
        try {
            if (validarFecha(fechaInicioReagenda) && validarFecha(fechaFinReagenda)) {
                if (fechaInicioReagenda.before(fechaFinReagenda)) {
                    System.err.println("Fecha Inicio  es menor ");
                    respuesta = true;
                } else {
                    if (fechaFinReagenda.before(fechaInicioReagenda)) {
                        System.err.println("La Fecha Inicio es Mayor ");
                        respuesta = false;
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "La fecha Inicial es Mayor a la Final.", ""));
                    } else {
                        respuesta = true;
                    }
                }
            }else{
                    respuesta = false;
                }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:validarFechas(). " + e.getMessage(), e, LOGGER);
        }

        return respuesta;
    }
      
      
          
    public boolean validarFechaInicioSuspension() {
        boolean respuesta = false;
        try {
            if (validarFecha(fechaInicioSuspension) && validarFecha(fechaFinSuspension)) {
                if (fechaInicioSuspension.before(fechaFinSuspension)) {
                    System.err.println("Fecha Inicio  es menor ");
                    respuesta = true;
                } else {
                    if (fechaFinSuspension.before(fechaInicioSuspension)) {
                        System.err.println("La Fecha Inicio es Mayor ");
                        respuesta = false;
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "La fecha Inicial es Mayor a la Final.", ""));
                    } else {
                        respuesta = true;
                    }
                }
            }else{
                    respuesta = false;
                }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:validarFechas(). " + e.getMessage(), e, LOGGER);
        }

        return respuesta;
    }
        
         public static boolean validarFecha(Date fecha) {

        try {
            if (fecha != null) {
                String fechaInicial = null;
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                fechaInicial = format.format(fecha);
                format.setLenient(false);
                format.parse(fechaInicial);
            } else {
               return false ;
            }


        } catch (ParseException e) {
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:validarFormatoFecha. " + e.getMessage(), e, LOGGER);
        }
        return true;
    }
       /**
     * valbuenayf Metodo para buscar el numero tiempo de espera para borrar el
     * reporte
     *
     * @param nombreParametro
     * @return
     */
    private Integer tiempoReporte(String nombreParametro) {
        Integer numero = null;
        Integer DEFAULT = 5;
        try {
            ParametrosMgl parametrosMgl = parametrosFacade.findByAcronimoName(nombreParametro);
            if (parametrosMgl != null) {
                numero = Integer.parseInt(parametrosMgl.getParValor());
            } else {
                numero = DEFAULT;
            }
        } catch (ApplicationException | NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error  en tiempoMaximo de ReporteEstadoActualOtCMBean:tiempoReporte "  + e.getMessage(), e, LOGGER);
            return DEFAULT;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:tiempoReporte " + e.getMessage(), e, LOGGER);
        }
        return numero;
    }

    

    /**
     *
     * Execute the search of CuentaMatriz list with params
     *
     */
    public void buscar() {
        
        params = new HashMap<String, Object>();
        params.put("fechaInicioOt", this.fechaInicioOt);
        params.put("fechaFinOt", this.fechaFinOt);
        params.put("fechaInicioOtOnyx", this.fechaInicioOtOnyx);
        params.put("fechaFinOtOnyx", this.fechaFinOtOnyx);
        params.put("fechaInicioAgendOFSC", this.fechaInicioAgendOFSC);
        params.put("fechaFinAgendOFSC", this.fechaFinAgendOFSC);
        params.put("fechaInicioAsigTecnico", this.fechaInicioAsigTecnico);
        params.put("fechaFinAsigTecnico", this.fechaFinAsigTecnico);
        params.put("fechaInicioCierreAgenda", this.fechaInicioCierreAgenda);
        params.put("fechaFinCierreAgenda", this.fechaFinCierreAgenda);
        params.put("fechaInicioCancAgenda", this.fechaInicioCancAgenda);
        params.put("fechaFinCancAgenda", this.fechaFinCancAgenda);
        params.put("fechaInicioReagenda", this.fechaInicioReagenda);
        params.put("fechaFinReagenda", this.fechaFinReagenda);
        params.put("fechaInicioSuspension", this.fechaInicioSuspension);
        params.put("fechaFinSuspension", this.fechaFinSuspension);
        params.put("tipoOrden", this.tipoOrden);
        params.put("subtipoOrden", this.subtipoOrden);
        params.put("subTipoOrdenOfscSelected", this.subTipoOrdenOfscSelected);
        params.put("estadoInternolist", this.estadoInternolist);
        params.put("otIni", this.otIni);
        params.put("otFin", this.otFin);
         params.put("tipoOrdenOfscSelected", this.tipoOrdenOfscSelected);

   
    }
    public void mostralPanel() {
        if (filtroFechas.equals(Constant.FECHA_CREACION_OT)) {
            panelCreacionOt = true;
            panelcreacionONYX = false;
            panelAgendamientoOFSC = false;
            panelAsignaciontecnicoOFSC = false;
            panelCierreagendaOFSC = false;
            panelCancelacionOFSC = false;
            panelReagendamientoOFSC = false;
            panelSuspensionOFSC = false;
        } else if (filtroFechas.equals(Constant.FECHA_CREACION_OT)) {
            panelCreacionOt = false;
            panelcreacionONYX = true;
            panelAgendamientoOFSC = false;
            panelAsignaciontecnicoOFSC = false;
            panelCierreagendaOFSC = false;
            panelCancelacionOFSC = false;
            panelReagendamientoOFSC = false;
            panelSuspensionOFSC = false;
        } else if (filtroFechas.equals(Constant.FECHA_AGENDACMIENTO_OFSC)) {
            panelCreacionOt = false;
            panelcreacionONYX = false;
            panelAgendamientoOFSC = true;
            panelAsignaciontecnicoOFSC = false;
            panelCierreagendaOFSC = false;
            panelCancelacionOFSC = false;
            panelReagendamientoOFSC = false;
            panelSuspensionOFSC = false;
        } else if (filtroFechas.equals(Constant.FECHA_ASIGNACION_TECNICO_OFSC)) {
            panelCreacionOt = false;
            panelcreacionONYX = false;
            panelAgendamientoOFSC = false;
            panelAsignaciontecnicoOFSC = true;
            panelCierreagendaOFSC = false;
            panelCancelacionOFSC = false;
            panelReagendamientoOFSC = false;
            panelSuspensionOFSC = false;
        } else if (filtroFechas.equals(Constant.FECHA_CIERRE_AGENDA_OFSC)) {
            panelCreacionOt = false;
            panelcreacionONYX = false;
            panelAgendamientoOFSC = false;
            panelAsignaciontecnicoOFSC = false;
            panelCierreagendaOFSC = true;
            panelCancelacionOFSC = false;
            panelReagendamientoOFSC = false;
            panelSuspensionOFSC = false;
        } else if (filtroFechas.equals(Constant.FECHA_CANCELACION_AGENDA_OFSC)) {
            panelCreacionOt = false;
            panelcreacionONYX = false;
            panelAgendamientoOFSC = false;
            panelAsignaciontecnicoOFSC = false;
            panelCierreagendaOFSC = false;
            panelCancelacionOFSC = true;
            panelReagendamientoOFSC = false;
            panelSuspensionOFSC = false;
        } else if (filtroFechas.equals(Constant.FECHA_REAGENDAMIENTO_OFSC)) {
            panelCreacionOt = false;
            panelcreacionONYX = false;
            panelAgendamientoOFSC = false;
            panelAsignaciontecnicoOFSC = false;
            panelCierreagendaOFSC = false;
            panelCancelacionOFSC = false;
            panelReagendamientoOFSC = true;
            panelSuspensionOFSC = false;
        } else {
            panelCreacionOt = false;
            panelcreacionONYX = false;
            panelAgendamientoOFSC = false;
            panelAsignaciontecnicoOFSC = false;
            panelCierreagendaOFSC = false;
            panelCancelacionOFSC = false;
            panelReagendamientoOFSC = false;
            panelSuspensionOFSC = true;
        }
        
    }
       
        public String getAreaResponsable() {
        
            try {
                usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
                if (usuarioLogin != null && usuarioLogin.getDescripcionArea() != null) {
                    areaResponsable = usuarioLogin.getDescripcionArea();
                }
            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError("Error al obtener el Área Responsable del solicitante. ", e, LOGGER);
            }
        
        return areaResponsable;
    }
        
    public String getNombreUsuario() {
       
            try {
                usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
                if (usuarioLogin != null && usuarioLogin.getNombre()!= null) {
                    nombreUser = usuarioLogin.getNombre();
                }
            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError("Error al obtener el Área Responsable del solicitante. ", e, LOGGER);
            }
        
        return nombreUser;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public boolean isCargar() {
        return cargar;
    }

    public void setCargar(boolean cargar) {
        this.cargar = cargar;
    }

    public Date getFechaInicio() {
       return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public BigDecimal getEstadoSelected() {
        return estadoSelected;
    }

    public void setEstadoSelected(BigDecimal estadoSelected) {
        this.estadoSelected = estadoSelected;
    }

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    public boolean isHabilitaObj() {
        return habilitaObj;
    }

    public void setHabilitaObj(boolean habilitaObj) {
        this.habilitaObj = habilitaObj;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public boolean isPintarPaginado() {
        return pintarPaginado;
    }

    public void setPintarPaginado(boolean pintarPaginado) {
        this.pintarPaginado = pintarPaginado;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNumRegAProcesar() {       
        return numRegAProcesar;
    }

    public void setNumRegAProcesar(int numRegAProcesar) {
        this.numRegAProcesar = numRegAProcesar;
    }

  
    
    public int getNumRegProcesados() {
          numRegProcesados = CmtReporteEstadoActualOtDirHhppDto.getNumeroRegistrosProcesados();
        return numRegProcesados;
    }

    public void setNumRegProcesados(int numRegProcesados) {
        this.numRegProcesados = numRegProcesados;
    }

    public boolean isPanelExportar() {
        return panelExportar;
    }

    public void setPanelExportar(boolean panelExportar) {
        this.panelExportar = panelExportar;
    }

    public boolean isCantRegistros() {
        return cantRegistros;
    }

    public void setCantRegistros(boolean cantRegistros) {
        this.cantRegistros = cantRegistros;
    }

    public boolean isPanelPintarPaginado() {
        return panelPintarPaginado;
    }

    public void setPanelPintarPaginado(boolean panelPintarPaginado) {
        this.panelPintarPaginado = panelPintarPaginado;
    }

    public int getNumRegTotal() {
        return numRegTotal;
    }

    public void setNumRegTotal(int numRegTotal) {
        this.numRegTotal = numRegTotal;
    }

    public boolean isMensajeExcel() {
        return mensajeExcel;
    }

    public void setMensajeExcel(boolean mensajeExcel) {
        this.mensajeExcel = mensajeExcel;
    }

    public boolean isMensajeCvsTxt() {
        return mensajeCvsTxt;
    }

    public void setMensajeCvsTxt(boolean mensajeCvsTxt) {
        this.mensajeCvsTxt = mensajeCvsTxt;
    }

    public boolean isBtnExcel() {
        return btnExcel;
    }

    public void setBtnExcel(boolean btnExcel) {
        this.btnExcel = btnExcel;
    }

    public boolean isBtnCvsTxt() {
        return btnCvsTxt;
    }

    public void setBtnCvsTxt(boolean btnCvsTxt) {
        this.btnCvsTxt = btnCvsTxt;
    }

    public boolean isPanelFormulario() {
        return panelFormulario;
    }

    public void setPanelFormulario(boolean panelFormulario) {
        this.panelFormulario = panelFormulario;
    }

    public boolean isBtnVolver() {
        return btnVolver;
    }

    public void setBtnVolver(boolean btnVolver) {
        this.btnVolver = btnVolver;
    }

    public boolean isBtnReporte() {
        return btnReporte;
    }

    public void setBtnReporte(boolean btnReporte) {
        this.btnReporte = btnReporte;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public boolean isIniciado() {
        return iniciado;
    }

    public void setIniciado(boolean iniciado) {
        this.iniciado = iniciado;
    }



    public List<CmtBasicaMgl> getListTablaBasicaTecnologias() {
        return listTablaBasicaTecnologias;
    }

    public void setListTablaBasicaTecnologias(List<CmtBasicaMgl> listTablaBasicaTecnologias) {
        this.listTablaBasicaTecnologias = listTablaBasicaTecnologias;
    }

    public BigDecimal getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(BigDecimal tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public List<CmtBasicaMgl> getListaTablaBasicaEstratos() {
        return listaTablaBasicaEstratos;
    }

    public void setListaTablaBasicaEstratos(List<CmtBasicaMgl> listaTablaBasicaEstratos) {
        this.listaTablaBasicaEstratos = listaTablaBasicaEstratos;
    }

    public BigDecimal getEstrato() {
        return estrato;
    }

    public void setEstrato(BigDecimal estrato) {
        this.estrato = estrato;
    }

    public Integer getRefresh() {
        return refresh;
    }

    public void setRefresh(Integer refresh) {
        this.refresh = refresh;
    }

    public boolean isProgress() {
        return progress;
    }

    public void setProgress(boolean progress) {
        this.progress = progress;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public String getXlsMaxReg() {
        return xlsMaxReg;
    }

    public void setXlsMaxReg(String xlsMaxReg) {
        this.xlsMaxReg = xlsMaxReg;
    }

    public String getUsuarioProceso() {
        return usuarioProceso;
    }

    public void setUsuarioProceso(String usuarioProceso) {
        this.usuarioProceso = usuarioProceso;
    }

    public String getTxtMaxReg() {
        return txtMaxReg;
    }

    public void setTxtMaxReg(String txtMaxReg) {
        this.txtMaxReg = txtMaxReg;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public String getCsvMaxReg() {
        return csvMaxReg;
    }

    public void setCsvMaxReg(String csvMaxReg) {
        this.csvMaxReg = csvMaxReg;
    }

    public List<CmtBasicaMgl> getTipoGeneralOrdenTrabajo() {
        return tipoGeneralOrdenTrabajo;
    }

    public void setTipoGeneralOrdenTrabajo(List<CmtBasicaMgl> tipoGeneralOrdenTrabajo) {
        this.tipoGeneralOrdenTrabajo = tipoGeneralOrdenTrabajo;
    }

    public List<CmtBasicaMgl> getListBasicaSubtipoOrdenOFSC() {
        return listBasicaSubtipoOrdenOFSC;
    }

    public void setListBasicaSubtipoOrdenOFSC(List<CmtBasicaMgl> listBasicaSubtipoOrdenOFSC) {
        this.listBasicaSubtipoOrdenOFSC = listBasicaSubtipoOrdenOFSC;
    }

    public CmtOrdenTrabajoMgl getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(CmtOrdenTrabajoMgl ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public List<CmtTipoOtMgl> getSubTipoOrdenTrabajo() {
        return subTipoOrdenTrabajo;
    }

    public void setSubTipoOrdenTrabajo(List<CmtTipoOtMgl> subTipoOrdenTrabajo) {
        this.subTipoOrdenTrabajo = subTipoOrdenTrabajo;
    }

  
    public BigDecimal getEstadoOtId() {
        return estadoOtId;
    }

    public void setEstadoOtId(BigDecimal estadoOtId) {
        this.estadoOtId = estadoOtId;
    }

    public List<CmtDetalleFlujoMgl> getDetalleFlujoEstActual() {
        return detalleFlujoEstActual;
    }

    public void setDetalleFlujoEstActual(List<CmtDetalleFlujoMgl> detalleFlujoEstActual) {
        this.detalleFlujoEstActual = detalleFlujoEstActual;
    }

    public List<CmtBasicaMgl> getListTablaBasicaEstadoInternoOt() {
        return listTablaBasicaEstadoInternoOt;
    }

    public void setListTablaBasicaEstadoInternoOt(List<CmtBasicaMgl> listTablaBasicaEstadoInternoOt) {
        this.listTablaBasicaEstadoInternoOt = listTablaBasicaEstadoInternoOt;
    }

    public BigDecimal getSubTipoTrabaoListSelect() {
        return subTipoTrabaoListSelect;
    }

    public void setSubTipoTrabaoListSelect(BigDecimal subTipoTrabaoListSelect) {
        this.subTipoTrabaoListSelect = subTipoTrabaoListSelect;
    }


 
    public Date getFechaInicioOt() {
        return fechaInicioOt;
    }

    public void setFechaInicioOt(Date fechaInicioOt) {
        this.fechaInicioOt = fechaInicioOt;
    }

    public Date getFechaFinOt() {
        return fechaFinOt;
    }

    public void setFechaFinOt(Date fechaFinOt) {
        this.fechaFinOt = fechaFinOt;
    }

    public Date getFechaInicioOtOnyx() {
        return fechaInicioOtOnyx;
    }

    public void setFechaInicioOtOnyx(Date fechaInicioOtOnyx) {
        this.fechaInicioOtOnyx = fechaInicioOtOnyx;
    }

    public Date getFechaFinOtOnyx() {
        return fechaFinOtOnyx;
    }

    public void setFechaFinOtOnyx(Date fechaFinOtOnyx) {
        this.fechaFinOtOnyx = fechaFinOtOnyx;
    }

    public Date getFechaInicioAgendOFSC() {
        return fechaInicioAgendOFSC;
    }

    public void setFechaInicioAgendOFSC(Date fechaInicioAgendOFSC) {
        this.fechaInicioAgendOFSC = fechaInicioAgendOFSC;
    }

    public Date getFechaFinAgendOFSC() {
        return fechaFinAgendOFSC;
    }

    public void setFechaFinAgendOFSC(Date fechaFinAgendOFSC) {
        this.fechaFinAgendOFSC = fechaFinAgendOFSC;
    }

    public Date getFechaInicioAsigTecnico() {
        return fechaInicioAsigTecnico;
    }

    public void setFechaInicioAsigTecnico(Date fechaInicioAsigTecnico) {
        this.fechaInicioAsigTecnico = fechaInicioAsigTecnico;
    }

    public Date getFechaFinAsigTecnico() {
        return fechaFinAsigTecnico;
    }

    public void setFechaFinAsigTecnico(Date fechaFinAsigTecnico) {
        this.fechaFinAsigTecnico = fechaFinAsigTecnico;
    }

    public Date getFechaInicioCierreAgenda() {
        return fechaInicioCierreAgenda;
    }

    public void setFechaInicioCierreAgenda(Date fechaInicioCierreAgenda) {
        this.fechaInicioCierreAgenda = fechaInicioCierreAgenda;
    }

    public Date getFechaFinCierreAgenda() {
        return fechaFinCierreAgenda;
    }

    public void setFechaFinCierreAgenda(Date fechaFinCierreAgenda) {
        this.fechaFinCierreAgenda = fechaFinCierreAgenda;
    }

    public Date getFechaInicioCancAgenda() {
        return fechaInicioCancAgenda;
    }

    public void setFechaInicioCancAgenda(Date fechaInicioCancAgenda) {
        this.fechaInicioCancAgenda = fechaInicioCancAgenda;
    }

    public Date getFechaFinCancAgenda() {
        return fechaFinCancAgenda;
    }

    public void setFechaFinCancAgenda(Date fechaFinCancAgenda) {
        this.fechaFinCancAgenda = fechaFinCancAgenda;
    }

    public Date getFechaInicioReagenda() {
        return fechaInicioReagenda;
    }

    public void setFechaInicioReagenda(Date fechaInicioReagenda) {
        this.fechaInicioReagenda = fechaInicioReagenda;
    }

    public Date getFechaFinReagenda() {
        return fechaFinReagenda;
    }

    public void setFechaFinReagenda(Date fechaFinReagenda) {
        this.fechaFinReagenda = fechaFinReagenda;
    }

    public Date getFechaInicioSuspension() {
        return fechaInicioSuspension;
    }

    public void setFechaInicioSuspension(Date fechaInicioSuspension) {
        this.fechaInicioSuspension = fechaInicioSuspension;
    }

    public Date getFechaFinSuspension() {
        return fechaFinSuspension;
    }

    public void setFechaFinSuspension(Date fechaFinSuspension) {
        this.fechaFinSuspension = fechaFinSuspension;
    }

    public Integer getOtIni() {
        return otIni;
    }

    public void setOtIni(Integer otIni) {
        this.otIni = otIni;
    }

    public Integer getOtFin() {
        return otFin;
    }

    public void setOtFin(Integer otFin) {
        this.otFin = otFin;
    }



    public List<BigDecimal> getSubTipoOrdenOfscSelected() {
        return subTipoOrdenOfscSelected;
    }

    public void setSubTipoOrdenOfscSelected(List<BigDecimal> subTipoOrdenOfscSelected) {
        this.subTipoOrdenOfscSelected = subTipoOrdenOfscSelected;
    }

    public List<BigDecimal> getEstadoInternolist() {
        return estadoInternolist;
    }

    public void setEstadoInternolist(List<BigDecimal> estadoInternolist) {
        this.estadoInternolist = estadoInternolist;
    }

    public List<BigDecimal> getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(List<BigDecimal> tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public String getFiltroFechas() {
        return filtroFechas;
    }

    public boolean isPanelCreacionOt() {
        return panelCreacionOt;
    }

    public void setPanelCreacionOt(boolean panelCreacionOt) {
        this.panelCreacionOt = panelCreacionOt;
    }

    public void setFiltroFechas(String filtroFechas) {
        this.filtroFechas = filtroFechas;
    }

    public boolean isPanelcreacionONYX() {
        return panelcreacionONYX;
    }

    public void setPanelcreacionONYX(boolean panelcreacionONYX) {
        this.panelcreacionONYX = panelcreacionONYX;
    }

    public boolean isPanelAgendamientoOFSC() {
        return panelAgendamientoOFSC;
    }

    public void setPanelAgendamientoOFSC(boolean panelAgendamientoOFSC) {
        this.panelAgendamientoOFSC = panelAgendamientoOFSC;
    }

    public boolean isPanelAsignaciontecnicoOFSC() {
        return panelAsignaciontecnicoOFSC;
    }

    public void setPanelAsignaciontecnicoOFSC(boolean panelAsignaciontecnicoOFSC) {
        this.panelAsignaciontecnicoOFSC = panelAsignaciontecnicoOFSC;
    }

    public boolean isPanelCierreagendaOFSC() {
        return panelCierreagendaOFSC;
    }

    public void setPanelCierreagendaOFSC(boolean panelCierreagendaOFSC) {
        this.panelCierreagendaOFSC = panelCierreagendaOFSC;
    }

    public boolean isPanelCancelacionOFSC() {
        return panelCancelacionOFSC;
    }

    public void setPanelCancelacionOFSC(boolean panelCancelacionOFSC) {
        this.panelCancelacionOFSC = panelCancelacionOFSC;
    }

    public boolean isPanelReagendamientoOFSC() {
        return panelReagendamientoOFSC;
    }

    public void setPanelReagendamientoOFSC(boolean panelReagendamientoOFSC) {
        this.panelReagendamientoOFSC = panelReagendamientoOFSC;
    }

    public boolean isPanelSuspensionOFSC() {
        return panelSuspensionOFSC;
    }

    public void setPanelSuspensionOFSC(boolean panelSuspensionOFSC) {
        this.panelSuspensionOFSC = panelSuspensionOFSC;
    }

    public List<TipoOtHhppMgl> getTipoOtHhppList() {
        return tipoOtHhppList;
    }

    public void setTipoOtHhppList(List<TipoOtHhppMgl> tipoOtHhppList) {
        this.tipoOtHhppList = tipoOtHhppList;
    }

    public List<BigDecimal> getSubtipoOrden() {
        return subtipoOrden;
    }

    public void setSubtipoOrden(List<BigDecimal> subtipoOrden) {
        this.subtipoOrden = subtipoOrden;
    }

    public List<CmtBasicaMgl> getEstadoGeneralList() {
        return estadoGeneralList;
    }

    public void setEstadoGeneralList(List<CmtBasicaMgl> estadoGeneralList) {
        this.estadoGeneralList = estadoGeneralList;
    }

    public List<BigDecimal> getTipoOrdenOfscSelected() {
        return tipoOrdenOfscSelected;
    }

    public void setTipoOrdenOfscSelected(List<BigDecimal> tipoOrdenOfscSelected) {
        this.tipoOrdenOfscSelected = tipoOrdenOfscSelected;
    }

    public String getNombreUsuarioArea() {
        return nombreUsuarioArea;
    }

    public void setNombreUsuarioArea(String nombreUsuarioArea) {
        this.nombreUsuarioArea = nombreUsuarioArea;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public UsuarioServicesFacadeLocal getUsuariosFacade() {
        return usuariosFacade;
    }

    public void setUsuariosFacade(UsuarioServicesFacadeLocal usuariosFacade) {
        this.usuariosFacade = usuariosFacade;
    }

    public UsuariosServicesDTO getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(UsuariosServicesDTO usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public boolean isBtnLimpiar() {
        return btnLimpiar;
    }

    public void setBtnLimpiar(boolean btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }


}
