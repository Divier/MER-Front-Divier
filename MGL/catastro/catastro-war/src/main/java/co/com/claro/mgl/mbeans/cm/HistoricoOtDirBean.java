package co.com.claro.mgl.mbeans.cm;


import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mgl.dtos.CmtReporteHistoricoOtDIRDto;
import co.com.claro.mgl.dtos.EstadosOtCmDirDto;
import co.com.claro.mgl.dtos.ReporteHistoricoOtDIRDto;
import co.com.claro.mgl.dtos.SegmentoDto;
import co.com.claro.mgl.dtos.TipoOtCmDirDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.OnyxOtCmDirlFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.RrRegionalesFacadeLocal;
import co.com.claro.mgl.facade.TipoOtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtComunidadRrFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtEstadoIntxExtMglFacaceLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOrdenTrabajoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtRegionalMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoOtMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtDetalleFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoIntxExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author cardenaslb
 */
@ManagedBean(name = "historicoOtDirBean")
@SessionScoped
@Log4j2
public class HistoricoOtDirBean {
   
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
    @Inject
    private BlockReportServBean blockReportServBean;
    private List<ReporteHistoricoOtDIRDto> listReporteHistoricoOtDIRDto;
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
    private static final String[] NOM_COLUMNAS = {
        "Numero Ot","Tipo OT MER", "SubTipo OT MER", "Est. Interno OT MER",  "Est.Interno OT MER[Audit]","Fecha creacion OT MER",
        "Fecha creacion OT MER [Audit]","Fecha Modificacion OT","Estado de Ot","Estado de Ot[Audit]","Segmento OT MER", "Tecnologia OT MGL", "Codigo CCMM", "Direccion OT MER", 
        "Codigo CCMM RR", "Nombre CCMM MER", "Usuario CREACION OT MER","Usuario EDICION OT MER","Usuario EDICION OT MER[Audit]",
        "OT hija ONYX","Departamento", "Complejidad ONYX","Complejidad ONYX[Audit]","NIT cliente ONYX ","NIT cliente ONYX[Audit]", 
        "Nombre cliente ONYX", "Nombre cliente ONYX[Audit]", "Nombre OT hija ONYX","Nombre OT hija ONYX[Audit]", "Numero OT hija ONYX",
        "Numero OT hija ONYX[Audit]","Fecha creacion OT hija ONYX","Fecha creacion OT hija ONYX[Audit]","Numero OT padre ONYX", 
        "Numero OT padre ONYX[Audit]","Fecha Creacion OT padre ONYX ", "Fecha Creacion OT padre ONYX[Audit] ","Contacto Tecnico OT padre ONYX",
        "Contacto Tecnico OT padre ONYX[Audit]","Telefono tecnico OT padre ONYX", "Telefono tecnico OT padre ONYX[Audit]","Descripcion ONYX",
        "Descripcion ONYX[Audit]","Segmento ONYX","Segmento ONYX[Audit]","Tipo Solucion ONYX","Tipo Solucion ONYX[Audit]","Servicios ONYX",
        "Servicios ONYX[Audit]","Recurrente mensual ONYX","Recurrente Mensual[Audit]","Codigo servicio ONYX","Codigo servicio ONYX[Audit]",
        "Vendedor ONYX","Vendedor ONYX[Audit]","Telefono Vendedor ONYX","Telefono Vendedor ONYX[Audit]","Estado OT hija ONYX","Estado OT hija ONYX[Audit]",
        "Estado OT padre ONYX","Estado OT padre ONYX[Audit]","Fecha compromiso OT padre ONYX","Fecha compromiso OT padre ONYX[Audit]","OT Hija Resolucion 1 ONYX",
        "OT Hija Resolucion 1 ONYX[Audit]","OT Hija Resolucion 2 ONYX","OT Hija Resolucion 2 ONYX[Audit]","OT Hija Resolucion 3 ONYX",
        "OT Hija Resolucion 3 ONYX[Audit]","OT Hija Resolucion 4 ONYX","OT Hija Resolucion 4 ONYX[Audit]","OT Padre Resolucion 1 ONYX",
        "OT Padre Resolucion 1 ONYX[Audit]","OT Padre Resolucion 2 ONYX","OT Padre Resolucion 2 ONYX[Audit]","OT Padre Resolucion 3 ONYX",
        "OT Padre Resolucion 3 ONYX[Audit]","OT Padre Resolucion 4 ONYX","OT Padre Resolucion 4 ONYX[Audit]","Appt number OFSC",
        "Orden RR","Sub tipo orden OFSC","Sub tipo orden OFSC[Audit]","Fecha agenda MER","Usuario Creacion Agenda","Usuario Edicion Agenda",
        "Usuario Edicion Agenda[Audit]","Time slot OFSC","Estado agenda OFSC","Estado Agenda OFSC[Audit]","Fecha Inicia agenda MER","Fecha Inicia Agend MER[Audit]",
        "Fecha Fin agenda MER","Fecha Fin Agenda MER[Audit]","Id aliado OFSC","Nombre aliado OFSC","Id tecnico de aliado OFSC","Id tecnico de aliado OFSC[Audit]",
        "Nombre Tecnico aliado OFSC","Nombre Tecnico aliado OFSC[Audit]","Multiagenda","Observaciones Tecnico OFSC","Regional","Ciudad","Codigo Proyecto",
        "Direccion Facturacion ","Direccion Facturacion[Audit]","Ciudad Facturacion ","Ciudad Facturacion[Audit]","Indicador de cumplimiento de fecha de compromiso ",
        "Nombre persona contacto que confirmo","Email persona contacto que confirmo","Número telefonico persona que confirmo",
        "Persona que Atiende","Telefono persona atiende","Tiempo de programacion","Tiempo de atencion compromiso","Tiempo de ejecucion del trabajo",
        "Resultado de la orden de trabajo","Antiguedad de Ot Onyx","Cant Reagendas","Conveniencia","Motivos de Reagendas","Aliado Implementacion"};
    
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
    private List<TipoOtCmDirDto> listTipoOtMgl;
//    private List<CmtTipoOtMgl> listTipoOtMgl;
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
    private boolean panelcreacionONYXHija;
    private boolean panelAgendamientoOFSC;
    private boolean panelAsignaciontecnicoOFSC;
    private boolean panelCierreagendaOFSC;
    private boolean panelCancelacionOFSC;
    private boolean panelReagendamientoOFSC;
    private boolean panelSuspensionOFSC;
    private boolean panelcreacionONYX;
    private List<TipoOtHhppMgl> tipoOtHhppList;
    private List<BigDecimal> subtipoOrden;
    private List<CmtBasicaMgl> estadoGeneralList;
    private List<BigDecimal>tipoOrdenOfscSelected;
    private String nombreUsuarioArea ;
    private String nombreUser ;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    private String areaResponsable;
    private boolean btnLimpiar = false;
    private List<String> listCodRegionales;
    private List<String> listCodCiudades;
    private String codProyecto;
    private String nitCliente;
    private String numOtOnyxPadre;
    private String numeroOtOnyxHija;
    private String nombreCliente;
    private String listaResolucionSelected;
    private List<String> listNombresSegmentos;
    private List<CmtBasicaMgl> listaEstadosExternos;
    private List<BigDecimal> listEstadosOtCmDirSelected;
    private List<EstadosOtCmDirDto> listEstadosOtCmDir;
    private String tipoSolucionSelected;
    private List<SegmentoDto> listaSegmentos;
    private List<BigDecimal> listSegmentoSelected;
    @EJB
    private OnyxOtCmDirlFacadeLocal onyxOtCmDirlFacadeLocal;
    @EJB
    private CmtComunidadRrFacadeLocal cmtComunidadRrFacadeLocal;
    @EJB
    private RrRegionalesFacadeLocal rrRegionalesFacadeLocal;
    private List<CmtRegionalRr> regionales;
    private List<BigDecimal> listRegionalSelected;
    private List<BigDecimal> listRrCiudadesSelected;
    private List<CmtComunidadRr> rrCiudades;
    private String valor;
    private String filtroVarios;
    private List<BigDecimal> subTipoOrden;
    private Date fechaCreacionIniOnyx;
    private Date fechaCreacionFinOnyx;
    private Date fechaInicioOtHijaOnyx;
    private Date fechaFinOtHijaOnyx;
    private List<BigDecimal> listEstadosSelected;
    private List<CmtBasicaMgl> listaEstadosOt;
    private int cantMaxRegExcel;
    private int cantMaxRegCvs;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
     @EJB
    private CmtRegionalMglFacadeLocal cmtRegionalMglFacadeLocal;
    @EJB
    private CmtEstadoIntxExtMglFacaceLocal cmtEstadoIntxExtMglFacaceLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;

    private List<CmtBasicaMgl> listaBasicaDir;
    private List<CmtBasicaMgl> listaBasicaCm;
    private List<CmtRegionalRr> listacmtRegionalMgl;
    private List<CmtComunidadRr> listacmtComunidadRr;
    private List<CmtEstadoIntxExtMgl> listaEstadosIntExt;
    private int cantRegConsulta;
    private int numRegistrosResultado;
    private int resultadosEncontrados;
    private List<CmtBasicaMgl> listBasicaSubtipoOrdenDirOFSC;
    private List<CmtBasicaMgl> listBasicaFinalSubtipoOrdenOFSC;
    
    //Opciones agregadas para Rol
    private final String BTREPORTEHITRAC = "BTREPORTEHITRAC";
    
    public HistoricoOtDirBean() {
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
           
            btnVolver = true;
            // Sub Tipo de Trabajo
            cargarListaTiposOrdenes();
           
             // tipo de trabajo
             cargarListaSubTiposOrdenes();
           
            // tipo de ordenes OFSC
            cargarListaSubTiposOrdenesOFSC();
            
             //Consulta los estados internos
            cargarListaEstadosOtCmDir();
            
            //Consulta los estados externos
            cargarListaEstados();
            
            // lista de basicas estado direcciones
            cargarListaBasicaDir();
            
            // lista basica estados de ccmm
            cargarListaBasicaCm();
            
            // lista de regionales
            cargarListacmtRegionalMgl();
            
            // lista de comunidades
            cargarListacmtComunidadRr();
            
            // lista estados internos externos
            cargarListaEstadosIntExt();

            //Lista de Segemntos 
            listaSegmentos = onyxOtCmDirlFacadeLocal.findAllSegmento();
            regionales = rrRegionalesFacadeLocal.findRegionales();
            if (listRegionalSelected != null && !listRegionalSelected.isEmpty()) {
                if (listRegionalSelected.size() == 1) {
                    String selected = listRegionalSelected.get(0).toString();
                    BigDecimal idReg = new BigDecimal(selected);
                    rrCiudades = cmtComunidadRrFacadeLocal.findByIdRegional(idReg);
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Ud selecciono mas de una Regional, no puede filtrar por Ciudad", ""));
                }
            }
            // cant de registros configurados en bd
            cantMaxRegExcel = Integer.parseInt(parametrosMglFacadeLocal.findParametroMgl(Constant.REPORTE_EXCEL_HIST_REG_MAX).getParValor());
            cantMaxRegCvs = Integer.parseInt(parametrosMglFacadeLocal.findParametroMgl(Constant.REPORTE_CVS_TXT_HIST_REG_MAX).getParValor());
            cantRegConsulta = Integer.parseInt(parametrosMglFacadeLocal.findParametroMgl(Constant.REPORTE_HIST_REG_MAX_CONSULTA).getParValor());
            limpiarValores();
            filtroFechas = null;
            panelCreacionOt = true;
        } catch (ApplicationException e) {
          FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:fillSqlList(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:fillSqlList() " + e.getMessage(), e, LOGGER);
        }

    }

    public void regionalCodeChanged(ValueChangeEvent e) {
        boolean nofiltrar;
        nofiltrar = cargarCiudades((List) e.getNewValue());

    }

    public boolean cargarCiudades(List codRegional) {
        boolean filtrar = false;
        try {
          List<CmtComunidadRr> listaCom = null;
          rrCiudades = new ArrayList<>();
            if (!codRegional.isEmpty()) {
                rrCiudades = cmtComunidadRrFacadeLocal.findByListRegional(codRegional);
                return filtrar = true;
            }
        } catch (ApplicationException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return filtrar;
    }

    public void getReporte() {
        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock("Reporte Historico Estado Actual CCMM Y Direcciones")) return;
        } catch (ApplicationException e) {
            LOGGER.error(e.getMessage(), e);
        }

        btnExcel = false;
            btnCvsTxt = false;
            buscar();
            obtenerReporte();
        
    }
    
    
   public void obtenerReporte() {
        try {
            
            if (this.fechaInicioOt != null && this.fechaFinOt == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Ingrese el número de OT final a filtrar", ""));
                return;
            }

            if (fechaFinOt != null && fechaInicioOt == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Ingrese el número de OT inicial a filtrar", ""));
                return;
            }
            if (filtroFechas == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Filtro Fecha es obligatorio.", ""));
                return ;
            }
            if(valor.isEmpty() && filtroVarios!=null){
               FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Introduzca un valor para el filtro", ""));
                return ;
            }
            if(!valor.isEmpty() && filtroVarios==null){
               FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Seleccione un filtro varios", ""));
                return ;
            }
            if (validarFechaCreacionOt() ) {
                processar();
            } else if (validarFechaInicioOtOnyxHija()) {
               processar();
            }else if (validarFechaCreacionInicioOtOnyx()) {
               processar();
            }else if (validarFechaInicioAgendOFSC()) {
               processar();
            } else if (validarFechaFinAsigTecnico()) {
                processar();
            } else if (validarFechaInicioCierreAgenda()) {
                processar();
            } else if (validarFechaInicioCancAgenda()) {
                processar();
            } else if (validarFechaInicioReagenda()) {
                processar();
            } else if (validarFechaInicioSuspension() ) {
                processar();
            }else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Introduzca un rango de fechas", ""));
                
            } 
           
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:obtenerReporte(). " + e.getMessage(), e, LOGGER);
        }
   }
    
    public void processar()  {
        try {
            btnVolver = true;
            panelFormulario = true;
            btnReporte = true;
            listReporteHistoricoOtDIRDto = new ArrayList<ReporteHistoricoOtDIRDto>();
            numRegistrosResultado = ordenTrabajoMglFacadeLocal.findReporteHistoricoOtDIRTotal(params, usuarioVT);
            if (numRegistrosResultado > 0) {
                if (numRegistrosResultado < cantMaxRegCvs) {
                    btnCvsTxt = true;
                     if(numRegistrosResultado > cantMaxRegExcel){
                          btnExcel = false;
                          cantRegistros = true;
                     }else{
                          btnExcel = true;
                          cantRegistros = false;
                     }
                    setBtnLimpiar(true);
                    xlsMaxReg = this.resultadosEncontrados > cantMaxRegExcel ? Constant.EXPORT_XLS_MAX : "";
                    csvMaxReg = this.resultadosEncontrados > cantMaxRegCvs ? Constant.EXPORT_CSV_MAX : "";
                } else {
                    panelPintarPaginado = false;
                    setProgress(false);
                    cantRegistros = true;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "La consulta obtuvo mas de " +cantMaxRegCvs+  " registros para un Archivo .CVS. Realice una nueva consulta ", ""));
                }

            } else {
                panelPintarPaginado = false;
                setProgress(false);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "No se encontraron resultados para la consulta.", ""));
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:obtenerReporte(). " + e.getMessage(), e, LOGGER);
        }
        

    }

       public void limpiarFechas() {

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
        fechaInicioOtHijaOnyx = null;
        fechaFinOtHijaOnyx = null;
        
    }

    /**
     * bocanegravm metodo para resetear los parametros de busqueda
     */
    public void limpiarValores() {
        numRegAProcesar = -1;
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
        tipoOrdenOfscSelected = null;
        numRegProcesados = 0;
        filtroFechas = null;
        nombreUsuarioArea = "";
        tipoSolucionSelected = null;
        listEstadosOtCmDirSelected = null;
        valor = null;
        listRegionalSelected = null;
        listRrCiudadesSelected = null;
        listSegmentoSelected = null;
        filtroVarios = null;
        subTipoOrden = null;
        tipoOrden = null;
        listEstadosSelected = null;
        fechaInicioOtHijaOnyx = null;
        fechaFinOtHijaOnyx = null;
        fechaCreacionIniOnyx = null;
        fechaCreacionFinOnyx = null;
        cantRegistros = false;
        numRegistrosResultado = 0;
    }


 

    public void volverList() {
            limpiarValores();
            setProgress(false);
            setFinish(false);
            panelPintarPaginado = false;

    }

public String exportCsv() throws ApplicationException {
        try {
            //Cantidad de registros por pagina a consultar de la DB            
            int expLonPag = 100;
            numRegistrosResultado = 0;
            boolean contar = true;
            List<ReporteHistoricoOtDIRDto> reporteOtResultList = new ArrayList();
            
            //numero total de registros del reporte
             numRegistrosResultado = ordenTrabajoMglFacadeLocal.findReporteHistoricoOtDIRTotal(params, usuarioVT);

            long totalPag = numRegistrosResultado;
            StringBuilder sb = new StringBuilder();
            byte[] csvData;
            SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");

            for (int j = 0; j < NOM_COLUMNAS.length; j++) {
                sb.append(NOM_COLUMNAS[j]);
                if (j < NOM_COLUMNAS.length) {
                    sb.append(";");
                }
            }
            sb.append("\n");
            
            String todayStr = formato.format(new Date());
            String fileName = "Historico_actual_Ordenes_CCMM_Agendas" + "_" + todayStr + "." + "csv";
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
            response1.setHeader("Content-disposition", "attached; filename=\""
                    + fileName + "\"");
            response1.setContentType("application/octet-stream;charset=UTF-8;");           
            response1.setCharacterEncoding("UTF-8");
            
            csvData = sb.toString().getBytes();
            response1.getOutputStream().write(csvData);
            

        for (int exPagina = 1; exPagina <= ((totalPag/expLonPag)+ (totalPag%expLonPag != 0 ? 1: 0)); exPagina++) {    
            
                progress = true;
         
            int inicioRegistros = 0;
            if (exPagina > 1) {
                inicioRegistros = (expLonPag * (exPagina - 1));
            }
    
                //consulta paginada de los resultados que se van a imprimir en el reporte
                    params.put("inicioRegistros", inicioRegistros);
                    params.put("expLonPag", expLonPag);
                    reporteOtResultList = ordenTrabajoMglFacadeLocal.findReporteHistoricoOtDIR(params, usuarioVT,
                            listaBasicaDir,
                            listaBasicaCm,
                            listacmtRegionalMgl,
                            listacmtComunidadRr,
                            listaEstadosIntExt);
               
                //listado de nodos cargados      
                if (reporteOtResultList != null && !reporteOtResultList.isEmpty()) {
                    
                    for (ReporteHistoricoOtDIRDto report : reporteOtResultList) {
                        
                        if (sb.toString().length() > 1) {
                            sb.delete(0, sb.toString().length());
                        }

                            //"Numero Ot","Tipo OT MER", "SubTipo OT MER", "Est. Interno OT MER", "Fecha creacion OT MER","Estado de Ot",
                            String otId = report.getOt_Id_Cm() == null ? " " : report.getOt_Id_Cm();
                            sb.append(otId);
                            sb.append(";");
                            String tipoOt = report.getTipo_OT_MER() == null ? " " : StringUtils.stripAccents(report.getTipo_OT_MER());
                            sb.append(tipoOt);
                            sb.append(";");
                            String subTIpo = report.getSub_tipo_OT_MER() == null ? " " : StringUtils.stripAccents(report.getSub_tipo_OT_MER());
                            sb.append(subTIpo);
                            sb.append(";");
                            String estadoInternoOt = report.getEstado_interno_OT_MER() == null ? " " : report.getEstado_interno_OT_MER();
                            sb.append(estadoInternoOt);
                            sb.append(";");
                            String estadoInternoOtAud = report.getEstado_interno_OT_MER_Aud() == null ? " " : report.getEstado_interno_OT_MER_Aud();
                            sb.append(estadoInternoOtAud);
                            sb.append(";");
                            String fechaCreacionOt = report.getFecha_creacion_OT_MER() == null ? " " : report.getFecha_creacion_OT_MER();
                            sb.append(fechaCreacionOt);
                            sb.append(";");
                            String fechaCreacionOtAud = report.getFecha_Creacion_OT_MER_Aud() == null ? " " : report.getFecha_Creacion_OT_MER_Aud();
                            sb.append(fechaCreacionOtAud);
                            sb.append(";");
                            
                            String fechaModificacionOt= report.getFecha_Modificacion_Ot() == null ? " " : report.getFecha_Modificacion_Ot();
                            sb.append(fechaModificacionOt);
                            sb.append(";");
                            
                            String estadoOt = report.getEstadoOt() == null ? " " : report.getEstadoOt();
                            sb.append(estadoOt);
                            sb.append(";");
                            String estadoOtAud = report.getEstadoOt_Aud()== null ? " " : report.getEstadoOt_Aud();
                            sb.append(estadoOtAud);
                            sb.append(";");
                            //"Segmento OT MER", "Tecnologia OT MGL", "Codigo CCMM", "Direccion OT MER", "Codigo CCMM RR", "Nombre CCMM MER", "Usuario CREACION OT MER","Usuario EDICION OT MER",
                            String segmentoOt = report.getSegmento_OT_MER() == null ? " " : report.getSegmento_OT_MER();
                            sb.append(segmentoOt);
                            sb.append(";");
                            String tecnologia1 = report.getTecnologia_OT_MGL() == null ? " " : report.getTecnologia_OT_MGL();
                            sb.append(tecnologia1);
                            sb.append(";");
                            String codigoCM = report.getCmObj() == null ? " " : report.getCmObj();
                            sb.append(codigoCM);
                            sb.append(";");
                            String direccionMer = report.getDireccionMer() == null ? " " : report.getDireccionMer();
                            sb.append(direccionMer);
                            sb.append(";");
                            String codigoCMRR = report.getCodigoCMR() == null ? " " : report.getCodigoCMR();
                            sb.append(codigoCMRR);
                            sb.append(";");
                            String nombreCM = report.getNombreCM() == null ? " " : report.getNombreCM();
                            sb.append(nombreCM);
                            sb.append(";");
                            String usuarioCreacionCM = report.getUsuario_Creacion_OT_MER() == null ? " " : report.getUsuario_Creacion_OT_MER();
                            sb.append(usuarioCreacionCM);
                            sb.append(";");
                            String usuarioEdicionCM = report.getUsuarioModOt() == null ? " " : report.getUsuarioModOt();
                            sb.append(usuarioEdicionCM);
                            sb.append(";");
                            String usuarioEdicionCMAud = report.getUsuarioModOtAud() == null ? " " : report.getUsuarioModOtAud();
                            sb.append(usuarioEdicionCMAud);
                            sb.append(";");

                            //"OT hija ONYX","Departamento", "Complejidad ONYX","Complejidad ONYX[Audit]","NIT cliente ONYX ","NIT cliente ONYX[Audit]", 
                            String onyxOtCm = report.getOnyx_Ot_Hija_Cm() != null ? report.getOnyx_Ot_Hija_Cm() : "";
                            sb.append(onyxOtCm);
                            sb.append(";");
                            String departamento = report.getDepartamento() != null ? StringUtils.stripAccents(report.getDepartamento()) : "";
                            sb.append(departamento);
                            sb.append(";");
                            String complejidadServicio = report.getComplejidadServicio() != null ? report.getComplejidadServicio() : "";
                            sb.append(complejidadServicio);
                            sb.append(";");
                            String complejidadServicioAud = report.getComplejidadServicioAud() != null ? report.getComplejidadServicioAud() : "";
                            sb.append(complejidadServicioAud);
                            sb.append(";");
                            String nitOnyx = report.getNit_Cliente_Onyx() != null ? report.getNit_Cliente_Onyx() : "";
                            sb.append(nitOnyx);
                            sb.append(";");
                            String nitOnyxAud = report.getNit_Cliente_Onyx_Aud() != null ? report.getNit_Cliente_Onyx_Aud() : "";
                            sb.append(nitOnyxAud);
                            sb.append(";");

                            //"Nombre cliente ONYX", "Nombre cliente ONYX[Audit]", "Nombre OT hija ONYX","Nombre OT hija ONYX[Audit]", "Numero OT hija ONYX",
                            String nombreOnyx = report.getNombre_Cliente_Onyx() != null ? StringUtils.stripAccents(report.getNombre_Cliente_Onyx()) : "";
                            sb.append(nombreOnyx);
                            sb.append(";");
                            String nombreOnyxAud = report.getNombre_Cliente_Onyx_Aud() != null ? StringUtils.stripAccents(report.getNombre_Cliente_Onyx_Aud()) : "";
                            sb.append(nombreOnyxAud);
                            sb.append(";");
                            String oth = report.getNombre_Ot_Hija_Onyx() != null ? StringUtils.stripAccents(report.getNombre_Ot_Hija_Onyx()) : "";
                            sb.append(oth);
                            sb.append(";");
                            String othAud = report.getNombre_OT_Hija_Onyx_Aud() != null ? StringUtils.stripAccents(report.getNombre_OT_Hija_Onyx_Aud()) : "";
                            sb.append(othAud);
                            sb.append(";");
                            String numeroOtOnyx = report.getOnyx_Ot_Hija_Cm() != null ? report.getOnyx_Ot_Hija_Cm() : "";
                            sb.append(numeroOtOnyx);
                            sb.append(";");

                            //"Numero OT hija ONYX[Audit]","Fecha creación OT hija ONYX","Fecha creacion OT hija ONYX[Audit]","Numero OT padre ONYX", 
                            String numeroOtOnyxAud = report.getOnyx_Ot_Hija_Cm_Aud() != null ? StringUtils.stripAccents(report.getOnyx_Ot_Hija_Cm_Aud()) : "";
                            sb.append(numeroOtOnyxAud);
                            sb.append(";");
                            String fechaCreacionHija = report.getFecha_Creacion_Ot_Hija_Onyx() != null ? report.getFecha_Creacion_Ot_Hija_Onyx() : "";
                            sb.append(fechaCreacionHija);
                            sb.append(";");
                            String fechaCreacionHijaAud = report.getFecha_Creación_OT_Hija_Onyx_Aud() != null ? report.getFecha_Creación_OT_Hija_Onyx_Aud() : "";
                            sb.append(fechaCreacionHijaAud);
                            sb.append(";");
                            String numeroOtPadre = report.getOnyx_Ot_Padre_Cm() != null ? StringUtils.stripAccents(report.getOnyx_Ot_Padre_Cm()) : "";
                            sb.append(numeroOtPadre);
                            sb.append(";");

                            //"Numero OT padre ONYX", "Fecha Creacion OT padre ONYX ", "Contacto Tecnico OT padre ONYX", "Telefono tecnico OT padre ONYX", "Descripcion ONYX",
                            String numeroOtPadreAud = report.getOnyx_Ot_Padre_Cm_Aud() != null ? StringUtils.stripAccents(report.getOnyx_Ot_Padre_Cm_Aud()) : "";
                            sb.append(numeroOtPadreAud);
                            sb.append(";");
                            String fechaCreacionOtPadre = report.getFecha_Creacion_Ot_Padre_Onyx() != null ? report.getFecha_Creacion_Ot_Padre_Onyx() : "";
                            sb.append(fechaCreacionOtPadre);
                            sb.append(";");
                            String fechaCreacionOtPadreAud = report.getFecha_Creacion_OT_Padre_Onyx_Aud() != null ? report.getFecha_Creacion_OT_Padre_Onyx_Aud() : "";
                            sb.append(fechaCreacionOtPadreAud);
                            sb.append(";");
                            String contactoTecnico = report.getContacto_Tecnico_Ot_Padre_Onyx() != null ? StringUtils.stripAccents(report.getContacto_Tecnico_Ot_Padre_Onyx()) : "";
                            sb.append(contactoTecnico);
                            sb.append(";");
                            String contactoTecnicoAud = report.getContacto_Tecnico_Ot_Padre_Onyx_Aud() != null ? StringUtils.stripAccents(report.getContacto_Tecnico_Ot_Padre_Onyx_Aud()) : "";
                            sb.append(contactoTecnicoAud);
                            sb.append(";");
                            String telefonoTecnico = report.getTelefono_Tecnico_Ot_Padre_Onyx() != null ? report.getTelefono_Tecnico_Ot_Padre_Onyx() : "";
                            sb.append(telefonoTecnico);
                            sb.append(";");
                            String telefonoTecnicoAud = report.getTelefono_Tecnico_Ot_Padre_Onyx_Aud() != null ? report.getTelefono_Tecnico_Ot_Padre_Onyx_Aud() : "";
                            sb.append(telefonoTecnicoAud);
                            sb.append(";");
                            String descripcionOnyx = report.getDescripcion_Onyx() != null ? StringUtils.stripAccents(report.getDescripcion_Onyx()) : "";
                            sb.append(descripcionOnyx);
                            sb.append(";");
                            String descripcionOnyxAud = report.getDescripcion_Onyx_Aud() != null ? StringUtils.stripAccents(report.getDescripcion_Onyx_Aud()) : "";
                            sb.append(descripcionOnyxAud);
                            sb.append(";");

                            //"Segmento ONYX","Tipo Solucion ONYX","Servicios ONYX","Recurrente mensual ONYX","Codigo servicio ONYX",
                            String segmento_Onyx = report.getSegmento_Onyx() != null ? StringUtils.stripAccents(report.getSegmento_Onyx()) : "";
                            sb.append(segmento_Onyx);
                            sb.append(";");
                            String segmento_OnyxAud = report.getSegmento_Onyx_Aud() != null ? StringUtils.stripAccents(report.getSegmento_Onyx_Aud()) : "";
                            sb.append(segmento_OnyxAud);
                            sb.append(";");
                            String tipo_Servicio_Onyx = report.getTipo_Servicio_Onyx() != null ? report.getTipo_Servicio_Onyx() : "";
                            sb.append(tipo_Servicio_Onyx);
                            sb.append(";");
                            String tipo_Servicio_OnyxAud = report.getTipo_Servicio_Onyx_Aud() != null ? report.getTipo_Servicio_Onyx_Aud() : "";
                            sb.append(tipo_Servicio_OnyxAud);
                            sb.append(";");
                            String servicios_Onyx = report.getServicios_Onyx() != null ? report.getServicios_Onyx() : "";
                            sb.append(servicios_Onyx);
                            sb.append(";");
                            String servicios_OnyxAud = report.getServicios_Onyx_Aud() != null ? report.getServicios_Onyx_Aud() : "";
                            sb.append(servicios_OnyxAud);
                            sb.append(";");
                            String recurrente_Mensual_Onyx = report.getRecurrente_Mensual_Onyx() != null ? report.getRecurrente_Mensual_Onyx() : "";
                            sb.append(recurrente_Mensual_Onyx);
                            sb.append(";");
                            String recurrente_Mensual_OnyxAud = report.getRecurrente_Mensual_Onyx_Aud() != null ? report.getRecurrente_Mensual_Onyx_Aud() : "";
                            sb.append(recurrente_Mensual_OnyxAud);
                            sb.append(";");
                            String codigo_Servicio_Onyx = report.getCodigo_Servicio_Onyx() != null ? report.getCodigo_Servicio_Onyx() : "";
                            sb.append(codigo_Servicio_Onyx);
                            sb.append(";");
                            String codigo_Servicio_OnyxAud = report.getCodigo_Servicio_Onyx_Aud() != null ? report.getCodigo_Servicio_Onyx_Aud() : "";
                            sb.append(codigo_Servicio_OnyxAud);
                            sb.append(";");

                            //"Vendedor ONYX","Telefono Vendedor ONYX","Estado OT hija ONYX","Estado OT padre ONYX","Fecha compromiso OT padre ONYX",
                            String vendedor_Onyx = report.getVendedor_Onyx() != null ? report.getVendedor_Onyx() : "";
                            sb.append(vendedor_Onyx);
                            sb.append(";");
                            String vendedor_OnyxAud = report.getVendedor_Onyx_Aud() != null ? report.getVendedor_Onyx_Aud() : "";
                            sb.append(vendedor_OnyxAud);
                            sb.append(";");
                            String telefono_Vendedor_Onyx = report.getTelefono_Vendedor_Onyx() != null ? report.getTelefono_Vendedor_Onyx() : "";
                            sb.append(telefono_Vendedor_Onyx);
                            sb.append(";");
                            String telefono_Vendedor_OnyxAud = report.getTelefono_Vendedor_Onyx_Aud() != null ? report.getTelefono_Vendedor_Onyx_Aud() : "";
                            sb.append(telefono_Vendedor_OnyxAud);
                            sb.append(";");
                            String estado_Ot_Hija_Onyx_Cm = report.getEstado_Ot_Hija_Onyx_Cm() != null ? StringUtils.stripAccents(report.getEstado_Ot_Hija_Onyx_Cm()) : "";
                            sb.append(estado_Ot_Hija_Onyx_Cm);
                            sb.append(";");
                            String estado_Ot_Hija_Onyx_CmAud = report.getEstado_Ot_Hija_Onyx_Cm_Aud() != null ? StringUtils.stripAccents(report.getEstado_Ot_Hija_Onyx_Cm_Aud()) : "";
                            sb.append(estado_Ot_Hija_Onyx_CmAud);
                            sb.append(";");
                            String estado_Ot_Padre_Onyx_Cm = report.getEstado_Ot_Padre_Onyx_Cm() != null ? StringUtils.stripAccents(report.getEstado_Ot_Padre_Onyx_Cm()) : "";
                            sb.append(estado_Ot_Padre_Onyx_Cm);
                            sb.append(";");
                            String estado_Ot_Padre_Onyx_CmAud = report.getEstado_Ot_Padre_Onyx_Cm_Aud() != null ? StringUtils.stripAccents(report.getEstado_Ot_Padre_Onyx_Cm_Aud()) : "";
                            sb.append(estado_Ot_Padre_Onyx_CmAud);
                            sb.append(";");
                            String fecha_Compromiso_Ot_Padre_Onyx = report.getFecha_Compromiso_Ot_Padre_Onyx() != null ? report.getFecha_Compromiso_Ot_Padre_Onyx() : "";
                            sb.append(fecha_Compromiso_Ot_Padre_Onyx);
                            sb.append(";");
                            String fecha_Compromiso_Ot_Padre_OnyxAud = report.getFecha_Compromiso_Ot_Padre_Onyx_Aud() != null ? report.getFecha_Compromiso_Ot_Padre_Onyx_Aud() : "";
                            sb.append(fecha_Compromiso_Ot_Padre_OnyxAud);
                            sb.append(";");

                            // "OT Hija Resolucion 1 ONYX","OT Hija Resolucion 2 ONYX","OT Hija Resolucion 3 ONYX","OT Hija Resolucion 4 ONYX",
                            String ot_Hija_Resolucion_1_Onyx = report.getOt_Hija_Resolucion_1_Onyx() != null ? StringUtils.stripAccents(report.getOt_Hija_Resolucion_1_Onyx()) : "";
                            sb.append(ot_Hija_Resolucion_1_Onyx);
                            sb.append(";");
                            String ot_Hija_Resolucion_1_OnyxAud = report.getOt_Hija_Resolucion_1_Onyx_Aud() != null ? StringUtils.stripAccents(report.getOt_Hija_Resolucion_1_Onyx_Aud()) : "";
                            sb.append(ot_Hija_Resolucion_1_OnyxAud);
                            sb.append(";");
                            String ot_Hija_Resolucion_2_Onyx = report.getOt_Hija_Resolucion_2_Onyx() != null ? StringUtils.stripAccents(report.getOt_Hija_Resolucion_2_Onyx()) : "";
                            sb.append(ot_Hija_Resolucion_2_Onyx);
                            sb.append(";");
                            String ot_Hija_Resolucion_2_OnyxAud = report.getOt_Hija_Resolucion_2_Onyx_Aud() != null ? StringUtils.stripAccents(report.getOt_Hija_Resolucion_2_Onyx_Aud()) : "";
                            sb.append(ot_Hija_Resolucion_2_OnyxAud);
                            sb.append(";");
                            String ot_Hija_Resolucion_3_Onyx = report.getOt_Hija_Resolucion_3_Onyx() != null ? StringUtils.stripAccents(report.getOt_Hija_Resolucion_3_Onyx()) : "";
                            sb.append(ot_Hija_Resolucion_3_Onyx);
                            sb.append(";");
                            String ot_Hija_Resolucion_3_OnyxAud = report.getOt_Hija_Resolucion_3_Onyx_Aud() != null ? StringUtils.stripAccents(report.getOt_Hija_Resolucion_3_Onyx_Aud()) : "";
                            sb.append(ot_Hija_Resolucion_3_OnyxAud);
                            sb.append(";");
                            String ot_Hija_Resolucion_4_Onyx = report.getOt_Hija_Resolucion_4_Onyx() != null ? StringUtils.stripAccents(report.getOt_Hija_Resolucion_4_Onyx()) : "";
                            sb.append(ot_Hija_Resolucion_4_Onyx);
                            sb.append(";");
                            String ot_Hija_Resolucion_4_OnyxAud = report.getOt_Hija_Resolucion_4_Onyx_Aud() != null ? StringUtils.stripAccents(report.getOt_Hija_Resolucion_4_Onyx_Aud()) : "";
                            sb.append(ot_Hija_Resolucion_4_OnyxAud);
                            sb.append(";");
                            //"OT Padre Resolucion 1 ONYX","OT Padre Resolucion 2 ONYX","OT Padre Resolucion 3 ONYX","OT Padre Resolucion 4 ONYX","Fecha Creacion Ot Onyx",
                            String ot_Padre_Resolucion_1_Onyx = report.getOt_Padre_Resolucion_1_Onyx() != null ? StringUtils.stripAccents(report.getOt_Padre_Resolucion_1_Onyx()) : "";
                            sb.append(ot_Padre_Resolucion_1_Onyx);
                            sb.append(";");
                            String ot_Padre_Resolucion_1_OnyxAud = report.getOt_Padre_Resolucion_1_Onyx_Aud() != null ? StringUtils.stripAccents(report.getOt_Padre_Resolucion_1_Onyx_Aud()) : "";
                            sb.append(ot_Padre_Resolucion_1_OnyxAud);
                            sb.append(";");
                            String ot_Padre_Resolucion_2_Onyx = report.getOt_Padre_Resolucion_2_Onyx() != null ? StringUtils.stripAccents(report.getOt_Padre_Resolucion_2_Onyx()) : "";
                            sb.append(ot_Padre_Resolucion_2_Onyx);
                            sb.append(";");
                            String ot_Padre_Resolucion_2_OnyxAud = report.getOt_Padre_Resolucion_2_Onyx_Aud() != null ? StringUtils.stripAccents(report.getOt_Padre_Resolucion_2_Onyx_Aud()) : "";
                            sb.append(ot_Padre_Resolucion_2_OnyxAud);
                            sb.append(";");
                            String ot_Padre_Resolucion_3_Onyx = report.getOt_Padre_Resolucion_3_Onyx() != null ? StringUtils.stripAccents(report.getOt_Padre_Resolucion_3_Onyx()) : "";
                            sb.append(ot_Padre_Resolucion_3_Onyx);
                            sb.append(";");
                            String ot_Padre_Resolucion_3_OnyxAud = report.getOt_Padre_Resolucion_3_Onyx_Aud() != null ? StringUtils.stripAccents(report.getOt_Padre_Resolucion_3_Onyx_Aud()) : "";
                            sb.append(ot_Padre_Resolucion_3_OnyxAud);
                            sb.append(";");
                            String ot_Padre_Resolucion_4_Onyx = report.getOt_Padre_Resolucion_4_Onyx() != null ? StringUtils.stripAccents(report.getOt_Padre_Resolucion_4_Onyx()) : "";
                            sb.append(ot_Padre_Resolucion_4_Onyx);
                            sb.append(";");
                            String ot_Padre_Resolucion_4_OnyxAud = report.getOt_Padre_Resolucion_4_Onyx_Aud() != null ? StringUtils.stripAccents(report.getOt_Padre_Resolucion_4_Onyx_Aud()) : "";
                            sb.append(ot_Padre_Resolucion_4_OnyxAud);
                            sb.append(";");

                            // "Appt number OFSC","Orden RR","Sub tipo orden OFSC","Fecha agenda MER","Usuario Creacion Agenda","Usuario Edicion Agenda",
                            String appt_number_OFSC = report.getAppt_number_OFSC() != null ? report.getAppt_number_OFSC() : "";
                            sb.append(appt_number_OFSC);
                            sb.append(";");
                            String orden_RR = report.getOrden_RR() != null ? report.getOrden_RR() : "";
                            sb.append(orden_RR);
                            sb.append(";");
                            String subTipo_Orden_OFSC = report.getSubTipo_Orden_OFSC() != null ? report.getSubTipo_Orden_OFSC() : "";
                            sb.append(subTipo_Orden_OFSC);
                            sb.append(";");
                            String subTipo_Orden_OFSCAud = report.getSubTipo_Orden_OFSC_Aud() != null ? report.getSubTipo_Orden_OFSC_Aud() : "";
                            sb.append(subTipo_Orden_OFSCAud);
                            sb.append(";");
                            String fecha_agenda_OFSC = report.getFecha_agenda_OFSC() != null ? report.getFecha_agenda_OFSC() : "";
                            sb.append(fecha_agenda_OFSC);
                            sb.append(";");
                            String usuario_creacion_agenda_OFSC = report.getUsuario_creacion_agenda_OFSC() != null ? report.getUsuario_creacion_agenda_OFSC() : "";
                            sb.append(usuario_creacion_agenda_OFSC);
                            sb.append(";");
                            String usuario_edicion_agenda_OFSC = report.getUsuarioModAgenda() != null ? report.getUsuarioModAgenda() : "";
                            sb.append(usuario_edicion_agenda_OFSC);
                            sb.append(";");
                            String usuario_edicion_agenda_OFSCAud = report.getUsuarioModAgendaAud() != null ? report.getUsuarioModAgendaAud() : "";
                            sb.append(usuario_edicion_agenda_OFSCAud);
                            sb.append(";");
                            //"Time slot OFSC","Estado agenda OFSC","Fecha de inicia agenda MER","Fecha fin agenda MER","Id aliado OFSC","Nombre aliado OFSC",
                            String time_slot_OFSC = report.getTime_slot_OFSC() != null ? report.getTime_slot_OFSC() : "";
                            sb.append(time_slot_OFSC);
                            sb.append(";");
                            String estado_agenda_OFSC = report.getEstado_agenda_OFSC() != null ? report.getEstado_agenda_OFSC() : "";
                            sb.append(estado_agenda_OFSC);
                            sb.append(";");
                            String estado_agenda_OFSCAud = report.getEstado_agenda_OFSC_Aud() != null ? report.getEstado_agenda_OFSC_Aud() : "";
                            sb.append(estado_agenda_OFSCAud);
                            sb.append(";");
                            String fecha_inicia_agenda_OFSC = report.getFecha_inicia_agenda_OFSC() != null ? report.getFecha_inicia_agenda_OFSC() : "";
                            sb.append(fecha_inicia_agenda_OFSC);
                            sb.append(";");
                            String fecha_inicia_agenda_OFSCAud = report.getFecha_inicia_agenda_OFSC_Aud() != null ? report.getFecha_inicia_agenda_OFSC_Aud() : "";
                            sb.append(fecha_inicia_agenda_OFSCAud);
                            sb.append(";");
                            String fecha_fin_agenda_OFSC = report.getFecha_fin_agenda_OFSC() != null ? report.getFecha_fin_agenda_OFSC() : "";
                            sb.append(fecha_fin_agenda_OFSC);
                            sb.append(";");
                            String fecha_fin_agenda_OFSCAud = report.getFecha_fin_agenda_OFSC_Aud() != null ? report.getFecha_fin_agenda_OFSC_Aud() : "";
                            sb.append(fecha_fin_agenda_OFSCAud);
                            sb.append(";");
                            String id_aliado_OFSC = report.getId_aliado_OFSC() != null ? report.getId_aliado_OFSC() : "";
                            sb.append(id_aliado_OFSC);
                            sb.append(";");
                            String id_aliado_OFSCAud = report.getId_aliado_OFSC_Aud() != null ? report.getId_aliado_OFSC_Aud() : "";
                            sb.append(id_aliado_OFSCAud);
                            sb.append(";");
                            String nombre_aliado_OFSC = report.getNombre_aliado_OFSC() != null ? StringUtils.stripAccents(report.getNombre_aliado_OFSC()) : "";
                            sb.append(nombre_aliado_OFSC);
                            sb.append(";");
                            //"Id tecnico de aliado OFSC","Nombre Tecnico aliado OFSC","Multiagenda","Observaciones Tecnico OFSC",
                            String id_tecnico_aliado_OFSC = report.getId_tecnico_aliado_OFSC() != null ? report.getId_tecnico_aliado_OFSC() : "";
                            sb.append(id_tecnico_aliado_OFSC);
                            sb.append(";");
                            String nombre_tecnico_aliado_OFSC = report.getNombre_tecnico_aliado_OFSC() != null ? StringUtils.stripAccents(report.getNombre_tecnico_aliado_OFSC()) : "";
                            sb.append(nombre_tecnico_aliado_OFSC);
                            sb.append(";");
                            String nombre_tecnico_aliado_OFSCAud = report.getNombre_tecnico_aliado_OFSC_Aud() != null ? StringUtils.stripAccents(report.getNombre_tecnico_aliado_OFSC_Aud()) : "";
                            sb.append(nombre_tecnico_aliado_OFSCAud);
                            sb.append(";");
                            String multiAgenda = report.getUltima_agenda_multiagenda() != null ? report.getUltima_agenda_multiagenda() : "";
                            sb.append(multiAgenda);
                            sb.append(";");
                            String observaciones_tecnico_OFSC = report.getObservaciones_tecnico_OFSC() != null ? StringUtils.stripAccents(report.getObservaciones_tecnico_OFSC()) : "";
                            sb.append(observaciones_tecnico_OFSC);
                            sb.append(";");
                            //campos nuevos
                            //"Regional","Ciudad","Codigo Proyecto","Direccion Facturacion","Indicador de cumplimiento de fecha de compromiso ","Nombre persona contacto que confirmó","Email persona contacto que confirmo",
                            String regional = report.getRegional() != null ? StringUtils.stripAccents(report.getRegional()) : "";
                            sb.append(regional);
                            sb.append(";");
                            String ciudad = report.getCiudad() != null ? StringUtils.stripAccents(report.getCiudad()) : "";
                            sb.append(ciudad);
                            sb.append(";");
                            String codigoProyecto = report.getCodigoProyecto() != null ? report.getCodigoProyecto() : "";
                            sb.append(codigoProyecto);
                            sb.append(";");
                            String direccionFact = report.getDireccion_Onyx() != null ? StringUtils.stripAccents(report.getDireccion_Onyx()) : "";
                            sb.append(direccionFact);
                            sb.append(";");
                            String direccionFactAud = report.getDireccion_Onyx_Aud() != null ? StringUtils.stripAccents(report.getDireccion_Onyx_Aud()) : "";
                            sb.append(direccionFactAud);
                            sb.append(";");
                            String ciudadFact = report.getCiudadFact() != null ? StringUtils.stripAccents(report.getCiudadFact()) : "";
                            sb.append(ciudadFact);
                            sb.append(";");
                            String ciudadFactAud = report.getCiudadFactAud() != null ? StringUtils.stripAccents(report.getCiudadFactAud()) : "";
                            sb.append(ciudadFactAud);
                            sb.append(";");
                            String indicadorCumplimiento = report.getIndicadorCumplimiento() != null ? StringUtils.stripAccents(report.getIndicadorCumplimiento()) : "";
                            sb.append(indicadorCumplimiento);
                            sb.append(";");
                            //"Nombre persona contacto que confirmó","Email persona contacto que confirmo","Número telefónico persona que confirmo",
                            String nombreTec = report.getContacto_Tecnico_Ot_Padre_Onyx() != null ? report.getContacto_Tecnico_Ot_Padre_Onyx() : "";
                            sb.append(nombreTec);
                            sb.append(";");
                            String emailTec = report.getEmailCTec() != null ? report.getEmailCTec() : "";
                            sb.append(emailTec);
                            sb.append(";");
                            String telTec = report.getTelefono_Tecnico_Ot_Padre_Onyx() != null ? report.getTelefono_Tecnico_Ot_Padre_Onyx() : "";
                            sb.append(telTec);
                            sb.append(";");
                            //"Persona que Atiende","Telefono persona atiende","Tiempo de programación","Tiempo de atención compromiso","Tiempo de ejecución del trabajo",
                            String personaAtiende = report.getPersonaAtiendeSitio() != null ? StringUtils.stripAccents(report.getPersonaAtiendeSitio()) : "";
                            sb.append(personaAtiende);
                            sb.append(";");
                            String telfPersonaAtiende = report.getTelefonoAtiendeSitio() != null ? report.getTelefonoAtiendeSitio() : "";
                            sb.append(telfPersonaAtiende);
                            sb.append(";");
                            String tiempoProgramacion = report.getTiempoProgramacion() != null ? report.getTiempoProgramacion() : "";
                            sb.append(tiempoProgramacion);
                            sb.append(";");
                            String tiempoAtencion = report.getTiempoAtencion() != null ? report.getTiempoAtencion() : "";
                            sb.append(tiempoAtencion);
                            sb.append(";");
                            String tiempoEjecucion = report.getTiempoEjecucion() != null ? report.getTiempoEjecucion() : "";
                            sb.append(tiempoEjecucion);
                            sb.append(";");
                            //"Resultado de la orden de trabajo","Antiguedad de Ot Onyx","Cant Reagendas","Motivos de Reagendas","Aliado Implementacion"};
                            String resultadoOrden = report.getResultadoOrden() != null ? report.getResultadoOrden() : "";
                            sb.append(resultadoOrden);
                            sb.append(";");
                            String antiguedad = report.getAntiguedadOrden() != null ? report.getAntiguedadOrden() : "";
                            sb.append(antiguedad);
                            sb.append(";");
                            String cantReagendas = report.getCantReagenda() != null ? report.getCantReagenda() : "";
                            sb.append(cantReagendas);
                            sb.append(";");
                            String conv = report.getConveniencia() != null ? report.getConveniencia() : "";
                            sb.append(conv);
                            sb.append(";");
                            String motivoReagenda = report.getMotivosReagenda() != null ? report.getMotivosReagenda() : "";
                            sb.append(motivoReagenda);
                            sb.append(";");
                            String aliadoImplementacion = report.getaImplement() != null ? report.getaImplement() : "";
                            sb.append(aliadoImplementacion);
                            sb.append(";");

                                sb.append("\n");
                        csvData = sb.toString().getBytes();
                        response1.getOutputStream().write(csvData);
                        response1.getOutputStream().flush();
                        response1.flushBuffer();

                    }
                    System.gc();                    
                }
            }     
            response1.getOutputStream().close();
            fc.responseComplete();
            progress = false;
            reporteOtResultList = null;
          
        } catch (IOException e) {
              progress = false;
            FacesUtil.mostrarMensajeError("Error en exportExcel. ", e, LOGGER);
        } catch (ApplicationException e) {
              progress = false;
            FacesUtil.mostrarMensajeError("Error en exportExcel. ", e, LOGGER);
        }
        return null;
    }

   
        
         public String exportExcelDet() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Reporte_actual_Ordenes_Ccmm_Direcciones");
            String[] objArr = {//orden
                "Numero Ot", "Tipo OT MER", "SubTipo OT MER", "Est. Interno OT MER", "Est.Interno OT MER[Audit]", "Fecha creacion OT MER",
                "Fecha creacion OT MER [Audit]", "Estado de Ot", "Estado de Ot[Audit]", "Fecha Modifiación Ot" ,"Segmento OT MER", "Tecnologia OT MGL", "Codigo CCMM", "Direccion OT MER",
                "Codigo CCMM RR", "Nombre CCMM MER", "Usuario CREACION OT MER", "Usuario EDICION OT MER", "Usuario EDICION OT MER[Audit]",
                "OT hija ONYX", "Departamento", "Complejidad ONYX", "Complejidad ONYX[Audit]", "NIT cliente ONYX ", "NIT cliente ONYX[Audit]",
                "Nombre cliente ONYX", "Nombre cliente ONYX[Audit]", "Nombre OT hija ONYX", "Nombre OT hija ONYX[Audit]", "Numero OT hija ONYX",
                "Numero OT hija ONYX[Audit]", "Fecha creacion OT hija ONYX", "Fecha creacion OT hija ONYX[Audit]", "Numero OT padre ONYX",
                "Numero OT padre ONYX[Audit]", "Fecha Creacion OT padre ONYX ", "Fecha Creacion OT padre ONYX[Audit] ", "Contacto Tecnico OT padre ONYX",
                "Contacto Tecnico OT padre ONYX[Audit]", "Telefono tecnico OT padre ONYX", "Telefono tecnico OT padre ONYX[Audit]", "Descripcion ONYX",
                "Descripcion ONYX[Audit]", "Segmento ONYX", "Segmento ONYX[Audit]", "Tipo Solucion ONYX", "Tipo Solucion ONYX[Audit]", "Servicios ONYX",
                "Servicios ONYX[Audit]", "Recurrente mensual ONYX", "Recurrente Mensual[Audit]", "Codigo servicio ONYX", "Codigo servicio ONYX[Audit]",
                "Vendedor ONYX", "Vendedor ONYX[Audit]", "Telefono Vendedor ONYX", "Telefono Vendedor ONYX[Audit]", "Estado OT hija ONYX", "Estado OT hija ONYX[Audit]",
                "Estado OT padre ONYX", "Estado OT padre ONYX[Audit]", "Fecha compromiso OT padre ONYX", "Fecha compromiso OT padre ONYX[Audit]", "OT Hija Resolucion 1 ONYX",
                "OT Hija Resolucion 1 ONYX[Audit]", "OT Hija Resolucion 2 ONYX", "OT Hija Resolucion 2 ONYX[Audit]", "OT Hija Resolucion 3 ONYX",
                "OT Hija Resolucion 3 ONYX[Audit]", "OT Hija Resolucion 4 ONYX", "OT Hija Resolucion 4 ONYX[Audit]", "OT Padre Resolucion 1 ONYX",
                "OT Padre Resolucion 1 ONYX[Audit]", "OT Padre Resolucion 2 ONYX", "OT Padre Resolucion 2 ONYX[Audit]", "OT Padre Resolucion 3 ONYX",
                "OT Padre Resolucion 3 ONYX[Audit]", "OT Padre Resolucion 4 ONYX", "OT Padre Resolucion 4 ONYX[Audit]", "Appt number OFSC",
                "Orden RR", "Sub tipo orden OFSC", "Sub tipo orden OFSC[Audit]", "Fecha agenda MER", "Usuario Creacion Agenda", "Usuario Edicion Agenda",
                "Usuario Edicion Agenda[Audit]", "Time slot OFSC", "Estado agenda OFSC", "Estado Agenda OFSC[Audit]", "Fecha Inicia agenda MER", "Fecha Inicia Agend MER[Audit]",
                "Fecha Fin agenda MER", "Fecha Fin Agenda MER[Audit]", "Id aliado OFSC", "Nombre aliado OFSC", "Id tecnico de aliado OFSC", "Id tecnico de aliado OFSC[Audit]",
                "Nombre Tecnico aliado OFSC", "Nombre Tecnico aliado OFSC[Audit]", "Multiagenda", "Observaciones Tecnico OFSC", "Regional", "Ciudad", "Codigo Proyecto",
                "Direccion Facturacion ", "Direccion Facturacion[Audit]", "Ciudad Facturacion ", "Ciudad Facturacion[Audit]", "Indicador de cumplimiento de fecha de compromiso ",
                "Nombre persona contacto que confirmo", "Email persona contacto que confirmo", "Número telefonico persona que confirmo",
                "Persona que Atiende", "Telefono persona atiende", "Tiempo de programacion", "Tiempo de atencion compromiso", "Tiempo de ejecucion del trabajo",
                "Resultado de la orden de trabajo", "Antiguedad de Ot Onyx", "Cant Reagendas", "Conveniencia", "Motivos de Reagendas", "Aliado Implementacion"};

            List<ReporteHistoricoOtDIRDto> reporteOtResultList = new ArrayList();

            int rownum = 0;

            int expLonPag = cantRegConsulta;
           

            for (int exPagina = 1; exPagina <= ((numRegistrosResultado / expLonPag) + (numRegistrosResultado % expLonPag != 0 ? 1 : 0));
                    exPagina++) {

                int inicioRegistros = 0;
                if (exPagina > 0) {
                    inicioRegistros = (expLonPag * (exPagina - 1));
                }
                params.put("inicioRegistros", inicioRegistros);
                params.put("expLonPag", expLonPag);

                reporteOtResultList = ordenTrabajoMglFacadeLocal.findReporteHistoricoOtDIR(params, usuarioVT,
                        listaBasicaDir,
                        listaBasicaCm,
                        listacmtRegionalMgl,
                        listacmtComunidadRr,
                        listaEstadosIntExt);

                if (!reporteOtResultList.isEmpty()) {
                    for (ReporteHistoricoOtDIRDto a : reporteOtResultList) {
                        Row row = sheet.createRow(rownum);
                        int cellnum = 0;
                        if (rownum == 0) {
                            for (String c : objArr) {
                                Cell cell = row.createCell(cellnum++);
                                cell.setCellValue(c);
                            }
                            cellnum = 0;
                            rownum += 1;
                            row = sheet.createRow(rownum);
                            //  "Numero Ot","Tipo OT MER", "SubTipo OT MER", "Est. Interno OT MER",  "Est.Interno OT MER[Audit]","Fecha creacion OT MER",
                            Cell cell1 = row.createCell(cellnum++);
                            cell1.setCellValue(a.getOt_Id_Cm());
                            Cell cell2 = row.createCell(cellnum++);
                            cell2.setCellValue(a.getTipo_OT_MER());
                            Cell cell3 = row.createCell(cellnum++);
                            cell3.setCellValue(a.getSub_tipo_OT_MER());
                            Cell cell18 = row.createCell(cellnum++);
                            cell18.setCellValue(a.getEstado_interno_OT_MER());
                            Cell cell18Aud = row.createCell(cellnum++);
                            cell18Aud.setCellValue(a.getEstado_interno_OT_MER_Aud());
                            Cell cell19 = row.createCell(cellnum++);
                            cell19.setCellValue(a.getFecha_creacion_OT_MER());
                            Cell cell19Aud = row.createCell(cellnum++);

                            //"Fecha creacion OT MER [Audit]","Estado de Ot","Segmento OT MER", "Tecnologia OT MGL", "Codigo CCMM", "Direccion OT MER", 
                            cell19Aud.setCellValue(a.getUsuario_Creacion_OT_MER() != null ? a.getUsuario_Creacion_OT_MER() : "");
                            Cell cell1999 = row.createCell(cellnum++);
                            cell1999.setCellValue(a.getFecha_Modificacion_Ot() != null ? a.getFecha_Modificacion_Ot() : "");
                            
                            Cell cell199 = row.createCell(cellnum++);
                            cell199.setCellValue(a.getEstadoOt());
                            Cell cell1991 = row.createCell(cellnum++);
                            cell1991.setCellValue(a.getEstadoOt_Aud());
                            Cell cell4 = row.createCell(cellnum++);
                            cell4.setCellValue(a.getSegmento_OT_MER());
                            Cell cell5 = row.createCell(cellnum++);
                            cell5.setCellValue(a.getTecnologia_OT_MGL());
                            Cell cell6 = row.createCell(cellnum++);
                            cell6.setCellValue(a.getCmObj());
                            Cell cell7 = row.createCell(cellnum++);
                            cell7.setCellValue(a.getDireccionMer());

                            // "Codigo CCMM RR", "Nombre CCMM MER", "Usuario CREACION OT MER","Usuario EDICION OT MER","Usuario EDICION OT MER[Audit]",
                            Cell cell8 = row.createCell(cellnum++);
                            cell8.setCellValue(a.getCodigoCMR());
                            Cell cell9 = row.createCell(cellnum++);
                            cell9.setCellValue(a.getNombreCM());
                            Cell cell12 = row.createCell(cellnum++);
                            cell12.setCellValue(a.getUsuario_Creacion_OT_MER() != null ? a.getUsuario_Creacion_OT_MER() : "");
                            Cell cell13 = row.createCell(cellnum++);
                            cell13.setCellValue(a.getUsuarioModOt() != null ? a.getUsuarioModOt() : "");
                            Cell cell13Aud = row.createCell(cellnum++);
                            cell13Aud.setCellValue(a.getUsuarioModOtAud() != null ? a.getUsuarioModOtAud() : "");

                            // "OT hija ONYX","Departamento", "Complejidad ONYX","Complejidad ONYX[Audit]","NIT cliente ONYX ","NIT cliente ONYX[Audit]", 
                            Cell cell14 = row.createCell(cellnum++);
                            cell14.setCellValue(a.getOnyx_Ot_Hija_Cm() != null ? a.getOnyx_Ot_Hija_Cm() : "");
                            Cell cell15 = row.createCell(cellnum++);
                            cell15.setCellValue(a.getDepartamento() != null ? a.getDepartamento() : "");
                            Cell cell16 = row.createCell(cellnum++);
                            cell16.setCellValue(a.getComplejidadServicio() != null ? a.getComplejidadServicio() : "");
                            Cell cell16Aud = row.createCell(cellnum++);
                            cell16Aud.setCellValue(a.getComplejidadServicioAud() != null ? a.getComplejidadServicioAud() : "");
                            Cell cell17 = row.createCell(cellnum++);
                            cell17.setCellValue(a.getNit_Cliente_Onyx() != null ? a.getNit_Cliente_Onyx() : "");
                            Cell cell17Aud = row.createCell(cellnum++);
                            cell17Aud.setCellValue(a.getNit_Cliente_Onyx_Aud() != null ? a.getNit_Cliente_Onyx_Aud() : "");

                            //  "Nombre cliente ONYX", "Nombre cliente ONYX[Audit]", "Nombre OT hija ONYX","Nombre OT hija ONYX[Audit]", "Numero OT hija ONYX",
                            Cell cell20 = row.createCell(cellnum++);
                            cell20.setCellValue(a.getNombre_Cliente_Onyx() != null ? a.getNombre_Cliente_Onyx() : "");
                            Cell cell20Aud = row.createCell(cellnum++);
                            cell20Aud.setCellValue(a.getNombre_Cliente_Onyx_Aud() != null ? a.getNombre_Cliente_Onyx_Aud() : "");
                            Cell cell21 = row.createCell(cellnum++);
                            cell21.setCellValue(a.getNombre_Ot_Hija_Onyx() != null ? a.getNombre_Ot_Hija_Onyx() : "");
                            Cell cell21Aud = row.createCell(cellnum++);
                            cell21Aud.setCellValue(a.getNombre_OT_Hija_Onyx_Aud() != null ? a.getNombre_OT_Hija_Onyx_Aud() : "");
                            Cell cell22 = row.createCell(cellnum++);
                            cell22.setCellValue(a.getOnyx_Ot_Hija_Cm() != null ? a.getOnyx_Ot_Hija_Cm() : "");

                            //"Numero OT hija ONYX[Audit]","Fecha creación OT hija ONYX","Fecha creacion OT hija ONYX[Audit]","Numero OT padre ONYX", 
                            Cell cell22Aud = row.createCell(cellnum++);
                            cell22Aud.setCellValue(a.getOnyx_Ot_Hija_Cm_Aud() != null ? a.getOnyx_Ot_Hija_Cm_Aud() : "");
                            Cell cell23 = row.createCell(cellnum++);
                            cell23.setCellValue(a.getFecha_Creacion_Ot_Hija_Onyx() != null ? a.getFecha_Creacion_Ot_Hija_Onyx() : "");
                            Cell cell23Aud = row.createCell(cellnum++);
                            cell23Aud.setCellValue(a.getFecha_Creación_OT_Hija_Onyx_Aud() != null ? a.getFecha_Creación_OT_Hija_Onyx_Aud() : "");
                            Cell celda14 = row.createCell(cellnum++);
                            celda14.setCellValue(a.getOnyx_Ot_Padre_Cm() != null ? a.getOnyx_Ot_Padre_Cm() : "");

                            //  "Numero OT padre ONYX[Audit]","Fecha Creacion OT padre ONYX ", "Fecha Creacion OT padre ONYX[Audit] ","Contacto Tecnico OT padre ONYX",
                            Cell celda14Aud = row.createCell(cellnum++);
                            celda14Aud.setCellValue(a.getOnyx_Ot_Padre_Cm_Aud() != null ? a.getOnyx_Ot_Padre_Cm_Aud() : "");
                            Cell celda15 = row.createCell(cellnum++);
                            celda15.setCellValue(a.getFecha_Creacion_Ot_Padre_Onyx() != null ? a.getFecha_Creacion_Ot_Padre_Onyx() : "");
                            Cell celda15Aud = row.createCell(cellnum++);
                            celda15Aud.setCellValue(a.getFecha_Creacion_OT_Padre_Onyx_Aud() != null ? a.getFecha_Creacion_OT_Padre_Onyx_Aud() : "");
                            Cell celda16 = row.createCell(cellnum++);
                            celda16.setCellValue(a.getContacto_Tecnico_Ot_Padre_Onyx() != null ? a.getContacto_Tecnico_Ot_Padre_Onyx() : "");

                            // "Contacto Tecnico OT padre ONYX[Audit]","Telefono tecnico OT padre ONYX", "Telefono tecnico OT padre ONYX[Audit]","Descripcion ONYX",
                            Cell celda16Aud = row.createCell(cellnum++);
                            celda16Aud.setCellValue(a.getContacto_Tecnico_Ot_Padre_Onyx_Aud() != null ? a.getContacto_Tecnico_Ot_Padre_Onyx_Aud() : "");
                            Cell celda17 = row.createCell(cellnum++);
                            celda17.setCellValue(a.getTelefono_Tecnico_Ot_Padre_Onyx() != null ? a.getTelefono_Tecnico_Ot_Padre_Onyx() : "");
                            Cell celda17Aud = row.createCell(cellnum++);
                            celda17Aud.setCellValue(a.getTelefono_Tecnico_Ot_Padre_Onyx_Aud() != null ? a.getTelefono_Tecnico_Ot_Padre_Onyx_Aud() : "");
                            Cell celda18 = row.createCell(cellnum++);
                            celda18.setCellValue(a.getDescripcion_Onyx() != null ? a.getDescripcion_Onyx() : "");

                            // "Descripcion ONYX[Audit]","Segmento ONYX","Segmento ONYX[Audit]","Tipo Solucion ONYX","Tipo Solucion ONYX[Audit]","Servicios ONYX",
                            Cell celda18Aud = row.createCell(cellnum++);
                            celda18Aud.setCellValue(a.getDescripcion_Onyx_Aud() != null ? a.getDescripcion_Onyx_Aud() : "");
                            Cell cell25 = row.createCell(cellnum++);
                            cell25.setCellValue(a.getSegmento_Onyx() != null ? a.getSegmento_Onyx() : "");
                            Cell cell25Aud = row.createCell(cellnum++);
                            cell25Aud.setCellValue(a.getSegmento_Onyx_Aud() != null ? a.getSegmento_Onyx_Aud() : "");
                            Cell cell26 = row.createCell(cellnum++);
                            cell26.setCellValue(a.getTipo_Servicio_Onyx() != null ? a.getTipo_Servicio_Onyx() : "");
                            Cell cell26Aud = row.createCell(cellnum++);
                            cell26Aud.setCellValue(a.getTipo_Servicio_Onyx_Aud() != null ? a.getTipo_Servicio_Onyx_Aud() : "");
                            Cell cell27 = row.createCell(cellnum++);
                            cell27.setCellValue(a.getServicios_Onyx() != null ? a.getServicios_Onyx() : "");

                            //"Servicios ONYX[Audit]","Recurrente mensual ONYX","Recurrente Mensual[Audit]","Codigo servicio ONYX","Codigo servicio ONYX[Audit]",
                            Cell cell27Aud = row.createCell(cellnum++);
                            cell27Aud.setCellValue(a.getServicios_Onyx_Aud() != null ? a.getServicios_Onyx_Aud() : "");
                            Cell cell28 = row.createCell(cellnum++);
                            cell28.setCellValue(a.getRecurrente_Mensual_Onyx() != null ? a.getRecurrente_Mensual_Onyx() : "");
                            Cell cell28Aud = row.createCell(cellnum++);
                            cell28Aud.setCellValue(a.getRecurrente_Mensual_Onyx_Aud() != null ? a.getRecurrente_Mensual_Onyx_Aud() : "");
                            Cell cell29 = row.createCell(cellnum++);
                            cell29.setCellValue(a.getCodigo_Servicio_Onyx() != null ? a.getCodigo_Servicio_Onyx() : "");
                            Cell cell29Aud = row.createCell(cellnum++);
                            cell29Aud.setCellValue(a.getCodigo_Servicio_Onyx_Aud() != null ? a.getCodigo_Servicio_Onyx_Aud() : "");

                            //  "Vendedor ONYX","Vendedor ONYX[Audit]","Telefono Vendedor ONYX","Telefono Vendedor ONYX[Audit]","Estado OT hija ONYX","Estado OT hija ONYX[Audit]",
                            Cell cell30 = row.createCell(cellnum++);
                            cell30.setCellValue(a.getVendedor_Onyx() != null ? a.getVendedor_Onyx() : "");
                            Cell cell30Aud = row.createCell(cellnum++);
                            cell30Aud.setCellValue(a.getVendedor_Onyx_Aud() != null ? a.getVendedor_Onyx_Aud() : "");
                            Cell cell31 = row.createCell(cellnum++);
                            cell31.setCellValue(a.getTelefono_Vendedor_Onyx() != null ? a.getTelefono_Vendedor_Onyx() : "");
                            Cell cell31Aud = row.createCell(cellnum++);
                            cell31Aud.setCellValue(a.getTelefono_Vendedor_Onyx_Aud() != null ? a.getTelefono_Vendedor_Onyx_Aud() : "");
                            Cell cell32 = row.createCell(cellnum++);
                            cell32.setCellValue(a.getEstado_Ot_Hija_Onyx_Cm() != null ? a.getEstado_Ot_Hija_Onyx_Cm() : "");
                            Cell cell32Aud = row.createCell(cellnum++);
                            cell32Aud.setCellValue(a.getEstado_Ot_Hija_Onyx_Cm_Aud() != null ? a.getEstado_Ot_Hija_Onyx_Cm_Aud() : "");

                            //"Estado OT padre ONYX","Estado OT padre ONYX[Audit]","Fecha compromiso OT padre ONYX","Fecha compromiso OT padre ONYX[Audit]","OT Hija Resolucion 1 ONYX",
                            Cell cell33 = row.createCell(cellnum++);
                            cell33.setCellValue(a.getEstado_Ot_Padre_Onyx_Cm() != null ? a.getEstado_Ot_Padre_Onyx_Cm() : "");
                            Cell cell33Aud = row.createCell(cellnum++);
                            cell33Aud.setCellValue(a.getEstado_Ot_Padre_Onyx_Cm_Aud() != null ? a.getEstado_Ot_Padre_Onyx_Cm_Aud() : "");
                            Cell cell34 = row.createCell(cellnum++);
                            cell34.setCellValue(a.getFecha_Compromiso_Ot_Padre_Onyx() != null ? a.getFecha_Compromiso_Ot_Padre_Onyx() : "");
                            Cell cell34Aud = row.createCell(cellnum++);
                            cell34Aud.setCellValue(a.getFecha_Compromiso_Ot_Padre_Onyx_Aud() != null ? a.getFecha_Compromiso_Ot_Padre_Onyx_Aud() : "");

                            // "OT Hija Resolucion 1 ONYX","OT Hija Resolucion 1 ONYX[Audit]","OT Hija Resolucion 2 ONYX","OT Hija Resolucion 2 ONYX[Audit]",
                            Cell cell35 = row.createCell(cellnum++);
                            cell35.setCellValue(a.getOt_Hija_Resolucion_1_Onyx() != null ? a.getOt_Hija_Resolucion_1_Onyx() : "");
                            Cell cell35Aud = row.createCell(cellnum++);
                            cell35Aud.setCellValue(a.getOt_Hija_Resolucion_1_Onyx_Aud() != null ? a.getOt_Hija_Resolucion_1_Onyx_Aud() : "");
                            Cell cell36 = row.createCell(cellnum++);
                            cell36.setCellValue(a.getOt_Hija_Resolucion_2_Onyx() != null ? a.getOt_Hija_Resolucion_2_Onyx() : "");
                            Cell cell36Aud = row.createCell(cellnum++);
                            cell36Aud.setCellValue(a.getOt_Hija_Resolucion_2_Onyx_Aud() != null ? a.getOt_Hija_Resolucion_2_Onyx_Aud() : "");

                            //"OT Hija Resolucion 3 ONYX", "Resolucion 3 ONYX[Audit]","OT Hija Resolucion 4 ONYX","OT Hija Resolucion 4 ONYX[Audit]"
                            Cell cell37 = row.createCell(cellnum++);
                            cell37.setCellValue(a.getOt_Hija_Resolucion_3_Onyx() != null ? a.getOt_Hija_Resolucion_4_Onyx() : "");
                            Cell cell37Aud = row.createCell(cellnum++);
                            cell37Aud.setCellValue(a.getOt_Hija_Resolucion_3_Onyx_Aud() != null ? a.getOt_Hija_Resolucion_3_Onyx_Aud() : "");
                            Cell cell38 = row.createCell(cellnum++);
                            cell38.setCellValue(a.getOt_Hija_Resolucion_4_Onyx() != null ? a.getOt_Hija_Resolucion_4_Onyx() : "");
                            Cell cell38Aud = row.createCell(cellnum++);
                            cell38Aud.setCellValue(a.getOt_Hija_Resolucion_4_Onyx_Aud() != null ? a.getOt_Hija_Resolucion_4_Onyx_Aud() : "");

                            // "OT Padre Resolucion 1 ONYX","OT Padre Resolucion 1 ONYX[Audit]","OT Padre Resolucion 2 ONYX","OT Padre Resolucion 2 ONYX[Audit]",
                            Cell cell39 = row.createCell(cellnum++);
                            cell39.setCellValue(a.getOt_Padre_Resolucion_1_Onyx() != null ? a.getOt_Padre_Resolucion_1_Onyx() : "");
                            Cell cell39Aud = row.createCell(cellnum++);
                            cell39Aud.setCellValue(a.getOt_Padre_Resolucion_1_Onyx_Aud() != null ? a.getOt_Padre_Resolucion_1_Onyx_Aud() : "");
                            Cell cell40 = row.createCell(cellnum++);
                            cell40.setCellValue(a.getOt_Padre_Resolucion_2_Onyx() != null ? a.getOt_Padre_Resolucion_2_Onyx() : "");
                            Cell cell40Aud = row.createCell(cellnum++);
                            cell40Aud.setCellValue(a.getOt_Padre_Resolucion_2_Onyx_Aud() != null ? a.getOt_Padre_Resolucion_2_Onyx_Aud() : "");

                            //"OT Padre Resolucion 3 ONYX","OT Padre Resolucion 3 ONYX[Audit]","OT Padre Resolucion 4 ONYX","OT Padre Resolucion 4 ONYX[Audit]",
                            Cell cell41 = row.createCell(cellnum++);
                            cell41.setCellValue(a.getOt_Padre_Resolucion_3_Onyx() != null ? a.getOt_Padre_Resolucion_3_Onyx() : "");
                            Cell cell41Aud = row.createCell(cellnum++);
                            cell41Aud.setCellValue(a.getOt_Padre_Resolucion_3_Onyx_Aud() != null ? a.getOt_Padre_Resolucion_3_Onyx_Aud() : "");
                            Cell cell42 = row.createCell(cellnum++);
                            cell42.setCellValue(a.getOt_Padre_Resolucion_4_Onyx() != null ? a.getOt_Padre_Resolucion_4_Onyx() : "");
                            Cell cell42Aud = row.createCell(cellnum++);
                            cell42Aud.setCellValue(a.getOt_Padre_Resolucion_4_Onyx_Aud() != null ? a.getOt_Padre_Resolucion_4_Onyx_Aud() : "");

                            // "Appt number OFSC","Orden RR","Sub tipo orden OFSC","Fecha agenda MER","Usuario Creacion Agenda","Usuario Edicion Agenda",
                            Cell cell43 = row.createCell(cellnum++);
                            cell43.setCellValue(a.getAppt_number_OFSC() != null ? a.getAppt_number_OFSC() : "");
                            Cell cell44 = row.createCell(cellnum++);
                            cell44.setCellValue(a.getOrden_RR() != null ? a.getOrden_RR() : "");
                            Cell cell45 = row.createCell(cellnum++);
                            cell45.setCellValue(a.getSubTipo_Orden_OFSC() != null ? a.getSubTipo_Orden_OFSC() : "");
                            Cell cell45Aud = row.createCell(cellnum++);
                            cell45Aud.setCellValue(a.getSubTipo_Orden_OFSC_Aud() != null ? a.getSubTipo_Orden_OFSC_Aud() : "");
                            Cell cell46 = row.createCell(cellnum++);
                            cell46.setCellValue(a.getFecha_agenda_OFSC() != null ? a.getFecha_agenda_OFSC() : "");
                            Cell cell47 = row.createCell(cellnum++);
                            cell47.setCellValue(a.getUsuario_creacion_agenda_OFSC() != null ? a.getUsuario_creacion_agenda_OFSC() : "");
                            Cell cell48 = row.createCell(cellnum++);
                            cell48.setCellValue(a.getUsuarioModAgenda() != null ? a.getUsuarioModAgenda() : "");
                            Cell cell48Aud = row.createCell(cellnum++);
                            cell48Aud.setCellValue(a.getUsuarioModAgendaAud() != null ? a.getUsuarioModAgendaAud() : "");

                            //"Time slot OFSC","Estado agenda OFSC","Fecha de inicia agenda MER","Fecha fin agenda MER","Id aliado OFSC","Nombre aliado OFSC",
                            Cell cell49 = row.createCell(cellnum++);
                            cell49.setCellValue(a.getTime_slot_OFSC() != null ? a.getTime_slot_OFSC() : "");
                            Cell cell50 = row.createCell(cellnum++);
                            cell50.setCellValue(a.getEstado_agenda_OFSC() != null ? a.getEstado_agenda_OFSC() : "");
                            Cell cell50Aud = row.createCell(cellnum++);
                            cell50Aud.setCellValue(a.getEstado_agenda_OFSC_Aud() != null ? a.getEstado_agenda_OFSC_Aud() : "");
                            Cell cell51 = row.createCell(cellnum++);
                            cell51.setCellValue(a.getFecha_inicia_agenda_OFSC() != null ? a.getFecha_inicia_agenda_OFSC() : "");
                            Cell cell51Aud = row.createCell(cellnum++);
                            cell51Aud.setCellValue(a.getFecha_inicia_agenda_OFSC_Aud() != null ? a.getFecha_inicia_agenda_OFSC_Aud() : "");
                            Cell cell52 = row.createCell(cellnum++);
                            cell52.setCellValue(a.getFecha_fin_agenda_OFSC() != null ? a.getFecha_fin_agenda_OFSC() : "");
                            Cell cell52Aud = row.createCell(cellnum++);
                            cell52Aud.setCellValue(a.getFecha_fin_agenda_OFSC_Aud() != null ? a.getFecha_fin_agenda_OFSC_Aud() : "");
                            Cell cell53 = row.createCell(cellnum++);
                            cell53.setCellValue(a.getId_aliado_OFSC() != null ? a.getId_aliado_OFSC() : "");
                            Cell cell54 = row.createCell(cellnum++);
                            cell54.setCellValue(a.getNombre_aliado_OFSC() != null ? a.getNombre_aliado_OFSC() : "");

                            // "Id tecnico de aliado OFSC","Nombre Tecnico aliado OFSC","Multiagenda","Observaciones Tecnico OFSC",
                            Cell cell55 = row.createCell(cellnum++);
                            cell55.setCellValue(a.getId_tecnico_aliado_OFSC() != null ? a.getId_tecnico_aliado_OFSC() : "");
                            Cell cell55Aud = row.createCell(cellnum++);
                            cell55Aud.setCellValue(a.getId_tecnico_aliado_OFSC_Aud() != null ? a.getId_tecnico_aliado_OFSC_Aud() : "");
                            Cell cell56 = row.createCell(cellnum++);
                            cell56.setCellValue(a.getNombre_tecnico_aliado_OFSC() != null ? a.getNombre_tecnico_aliado_OFSC() : "");
                            Cell cell56Aud = row.createCell(cellnum++);
                            cell56Aud.setCellValue(a.getNombre_tecnico_aliado_OFSC_Aud() != null ? a.getNombre_tecnico_aliado_OFSC_Aud() : "");
                            Cell cell57 = row.createCell(cellnum++);
                            cell57.setCellValue(a.getUltima_agenda_multiagenda() != null ? a.getUltima_agenda_multiagenda() : "");
                            Cell cell58 = row.createCell(cellnum++);
                            cell58.setCellValue(a.getObservaciones_tecnico_OFSC() != null ? a.getObservaciones_tecnico_OFSC() : "");
                            Cell cell59 = row.createCell(cellnum++);

                            //"Regional","Ciudad","Codigo Proyecto","Direccion Facturacion","Indicador de cumplimiento de fecha de compromiso ",
                            cell59.setCellValue(a.getRegional() != null ? a.getRegional() : "");
                            Cell cell60 = row.createCell(cellnum++);
                            cell60.setCellValue(a.getCiudad() != null ? a.getCiudad() : "");
                            Cell cell61 = row.createCell(cellnum++);
                            cell61.setCellValue(a.getCodigoProyecto() != null ? a.getCodigoProyecto() : "");
                            Cell cell62 = row.createCell(cellnum++);
                            cell62.setCellValue(a.getDireccion_Onyx() != null ? a.getDireccion_Onyx() : "");
                            Cell cell62Aud = row.createCell(cellnum++);
                            cell62Aud.setCellValue(a.getDireccion_Onyx_Aud() != null ? a.getDireccion_Onyx_Aud() : "");
                            Cell cell622 = row.createCell(cellnum++);
                            cell622.setCellValue(a.getCiudadFact() != null ? a.getCiudadFact() : "");
                            Cell cell622Aud = row.createCell(cellnum++);
                            cell622Aud.setCellValue(a.getCiudadFactAud() != null ? a.getCiudadFactAud() : "");
                            Cell cell63 = row.createCell(cellnum++);
                            cell63.setCellValue(a.getIndicadorCumplimiento() != null ? a.getIndicadorCumplimiento() : "");
                            Cell cell644 = row.createCell(cellnum++);

                            //"Nombre persona contacto que confirmó","Email persona contacto que confirmo","Número telefónico persona que confirmo",
                            cell644.setCellValue(a.getContacto_Tecnico_Ot_Padre_Onyx() != null ? a.getContacto_Tecnico_Ot_Padre_Onyx() : "");
                            Cell cell64 = row.createCell(cellnum++);
                            cell64.setCellValue(a.getEmailCTec() != null ? a.getEmailCTec() : "");
                            Cell cell645 = row.createCell(cellnum++);
                            cell645.setCellValue(a.getTelefono_Tecnico_Ot_Padre_Onyx() != null ? a.getTelefono_Tecnico_Ot_Padre_Onyx() : "");
                            Cell cell65 = row.createCell(cellnum++);

                            // "Persona que Atiende","Telefono persona atiende","Tiempo de programación","Tiempo de atención compromiso","Tiempo de ejecución del trabajo",
                            cell65.setCellValue(a.getPersonaAtiendeSitio() != null ? a.getPersonaAtiendeSitio() : "");
                            Cell cell66 = row.createCell(cellnum++);
                            cell66.setCellValue(a.getTelefonoAtiendeSitio() != null ? a.getTelefonoAtiendeSitio() : "");
                            Cell cell67 = row.createCell(cellnum++);
                            cell67.setCellValue(a.getTiempoProgramacion() != null ? a.getTiempoProgramacion() : "");
                            Cell cell68 = row.createCell(cellnum++);
                            cell68.setCellValue(a.getTiempoAtencion() != null ? a.getTiempoAtencion() : "");
                            Cell cell69 = row.createCell(cellnum++);
                            cell69.setCellValue(a.getTiempoEjecucion() != null ? a.getTiempoEjecucion() : "");

                            // "Resultado de la orden de trabajo","Antiguedad de Ot Onyx","Cant Reagendas","Motivos de Reagendas","Aliado Implementacion"};
                            Cell cell70 = row.createCell(cellnum++);
                            cell70.setCellValue(a.getResultadoOrden() != null ? a.getResultadoOrden() : "");
                            Cell cell71 = row.createCell(cellnum++);
                            cell71.setCellValue(a.getAntiguedadOrden() != null ? a.getAntiguedadOrden() : "");
                            Cell cell72 = row.createCell(cellnum++);
                            cell72.setCellValue(a.getCantReagenda() != null ? a.getCantReagenda() : "");
                            Cell cell722 = row.createCell(cellnum++);
                            cell722.setCellValue(a.getConveniencia() != null ? a.getConveniencia() : "");
                            Cell cell73 = row.createCell(cellnum++);
                            cell73.setCellValue(a.getMotivosReagenda() != null ? a.getMotivosReagenda() : "");
                            Cell cell74 = row.createCell(cellnum++);
                            cell74.setCellValue(a.getaImplement() != null ? a.getaImplement() : "");

                        } else {

                            Cell cell1 = row.createCell(cellnum++);
                            if (a.getOt_Id_Cm() != null) {
                                cell1.setCellValue(a.getOt_Id_Cm());
                            }

                            //  "Numero Ot","Tipo OT MER", "SubTipo OT MER", "Est. Interno OT MER",  "Est.Interno OT MER[Audit]","Fecha creacion OT MER",
                            Cell cell2 = row.createCell(cellnum++);
                            cell2.setCellValue(a.getTipo_OT_MER());
                            Cell cell3 = row.createCell(cellnum++);
                            cell3.setCellValue(a.getSub_tipo_OT_MER());
                            Cell cell18 = row.createCell(cellnum++);
                            cell18.setCellValue(a.getEstado_interno_OT_MER());
                            Cell cell18Aud = row.createCell(cellnum++);
                            cell18Aud.setCellValue(a.getEstado_interno_OT_MER_Aud());
                            Cell cell19 = row.createCell(cellnum++);
                            cell19.setCellValue(a.getFecha_creacion_OT_MER());
                            Cell cell19Aud = row.createCell(cellnum++);

                            //"Fecha creacion OT MER [Audit]","Estado de Ot","Segmento OT MER", "Tecnologia OT MGL", "Codigo CCMM", "Direccion OT MER", 
                            cell19Aud.setCellValue(a.getFecha_Creacion_OT_MER_Aud());
                            Cell cell199 = row.createCell(cellnum++);
                            cell199.setCellValue(a.getEstadoOt());
                            Cell cell1991 = row.createCell(cellnum++);
                            cell1991.setCellValue(a.getEstadoOt_Aud());
                            Cell cell4 = row.createCell(cellnum++);
                            cell4.setCellValue(a.getSegmento_OT_MER());
                            Cell cell5 = row.createCell(cellnum++);
                            cell5.setCellValue(a.getTecnologia_OT_MGL());
                            Cell cell6 = row.createCell(cellnum++);
                            cell6.setCellValue(a.getCmObj());
                            Cell cell7 = row.createCell(cellnum++);
                            cell7.setCellValue(a.getDireccionMer());

                            // "Codigo CCMM RR", "Nombre CCMM MER", "Usuario CREACION OT MER","Usuario EDICION OT MER","Usuario EDICION OT MER[Audit]",
                            Cell cell8 = row.createCell(cellnum++);
                            cell8.setCellValue(a.getCodigoCMR());
                            Cell cell9 = row.createCell(cellnum++);
                            cell9.setCellValue(a.getNombreCM());
                            Cell cell12 = row.createCell(cellnum++);
                            cell12.setCellValue(a.getUsuario_Creacion_OT_MER() != null ? a.getUsuario_Creacion_OT_MER() : "");
                            Cell cell13 = row.createCell(cellnum++);
                            cell13.setCellValue(a.getUsuarioModOt() != null ? a.getUsuarioModOt() : "");
                            Cell cell13Aud = row.createCell(cellnum++);
                            cell13Aud.setCellValue(a.getUsuarioModOtAud() != null ? a.getUsuarioModOtAud() : "");

                            // "OT hija ONYX","Departamento", "Complejidad ONYX","Complejidad ONYX[Audit]","NIT cliente ONYX ","NIT cliente ONYX[Audit]", 
                            Cell cell14 = row.createCell(cellnum++);
                            cell14.setCellValue(a.getOnyx_Ot_Hija_Cm() != null ? a.getOnyx_Ot_Hija_Cm() : "");
                            Cell cell15 = row.createCell(cellnum++);
                            cell15.setCellValue(a.getDepartamento() != null ? a.getDepartamento() : "");
                            Cell cell16 = row.createCell(cellnum++);
                            cell16.setCellValue(a.getComplejidadServicio() != null ? a.getComplejidadServicio() : "");
                            Cell cell16Aud = row.createCell(cellnum++);
                            cell16Aud.setCellValue(a.getComplejidadServicioAud() != null ? a.getComplejidadServicioAud() : "");
                            Cell cell17 = row.createCell(cellnum++);
                            cell17.setCellValue(a.getNit_Cliente_Onyx() != null ? a.getNit_Cliente_Onyx() : "");
                            Cell cell17Aud = row.createCell(cellnum++);
                            cell17Aud.setCellValue(a.getNit_Cliente_Onyx_Aud() != null ? a.getNit_Cliente_Onyx_Aud() : "");

                            //  "Nombre cliente ONYX", "Nombre cliente ONYX[Audit]", "Nombre OT hija ONYX","Nombre OT hija ONYX[Audit]", "Numero OT hija ONYX",
                            Cell cell20 = row.createCell(cellnum++);
                            cell20.setCellValue(a.getNombre_Cliente_Onyx() != null ? a.getNombre_Cliente_Onyx() : "");
                            Cell cell20Aud = row.createCell(cellnum++);
                            cell20Aud.setCellValue(a.getNombre_Cliente_Onyx_Aud() != null ? a.getNombre_Cliente_Onyx_Aud() : "");
                            Cell cell21 = row.createCell(cellnum++);
                            cell21.setCellValue(a.getNombre_Ot_Hija_Onyx() != null ? a.getNombre_Ot_Hija_Onyx() : "");
                            Cell cell21Aud = row.createCell(cellnum++);
                            cell21Aud.setCellValue(a.getNombre_OT_Hija_Onyx_Aud() != null ? a.getNombre_OT_Hija_Onyx_Aud() : "");
                            Cell cell22 = row.createCell(cellnum++);
                            cell22.setCellValue(a.getOnyx_Ot_Hija_Cm() != null ? a.getOnyx_Ot_Hija_Cm() : "");

                            //"Numero OT hija ONYX[Audit]","Fecha creación OT hija ONYX","Fecha creacion OT hija ONYX[Audit]","Numero OT padre ONYX", 
                            Cell cell22Aud = row.createCell(cellnum++);
                            cell22Aud.setCellValue(a.getOnyx_Ot_Hija_Cm_Aud() != null ? a.getOnyx_Ot_Hija_Cm_Aud() : "");
                            Cell cell23 = row.createCell(cellnum++);
                            cell23.setCellValue(a.getFecha_Creacion_Ot_Hija_Onyx() != null ? a.getFecha_Creacion_Ot_Hija_Onyx() : "");
                            Cell cell23Aud = row.createCell(cellnum++);
                            cell23Aud.setCellValue(a.getFecha_Creación_OT_Hija_Onyx_Aud() != null ? a.getFecha_Creación_OT_Hija_Onyx_Aud() : "");
                            Cell celda14 = row.createCell(cellnum++);
                            celda14.setCellValue(a.getOnyx_Ot_Padre_Cm() != null ? a.getOnyx_Ot_Padre_Cm() : "");

                            //  "Numero OT padre ONYX[Audit]","Fecha Creacion OT padre ONYX ", "Fecha Creacion OT padre ONYX[Audit] ","Contacto Tecnico OT padre ONYX",
                            Cell celda14Aud = row.createCell(cellnum++);
                            celda14Aud.setCellValue(a.getOnyx_Ot_Padre_Cm_Aud() != null ? a.getOnyx_Ot_Padre_Cm_Aud() : "");
                            Cell celda15 = row.createCell(cellnum++);
                            celda15.setCellValue(a.getFecha_Creacion_Ot_Padre_Onyx() != null ? a.getFecha_Creacion_Ot_Padre_Onyx() : "");
                            Cell celda15Aud = row.createCell(cellnum++);
                            celda15Aud.setCellValue(a.getFecha_Creacion_OT_Padre_Onyx_Aud() != null ? a.getFecha_Creacion_OT_Padre_Onyx_Aud() : "");
                            Cell celda16 = row.createCell(cellnum++);
                            celda16.setCellValue(a.getContacto_Tecnico_Ot_Padre_Onyx() != null ? a.getContacto_Tecnico_Ot_Padre_Onyx() : "");

                            // "Contacto Tecnico OT padre ONYX[Audit]","Telefono tecnico OT padre ONYX", "Telefono tecnico OT padre ONYX[Audit]","Descripcion ONYX",
                            Cell celda16Aud = row.createCell(cellnum++);
                            celda16Aud.setCellValue(a.getContacto_Tecnico_Ot_Padre_Onyx_Aud() != null ? a.getContacto_Tecnico_Ot_Padre_Onyx_Aud() : "");
                            Cell celda17 = row.createCell(cellnum++);
                            celda17.setCellValue(a.getTelefono_Tecnico_Ot_Padre_Onyx() != null ? a.getTelefono_Tecnico_Ot_Padre_Onyx() : "");
                            Cell celda17Aud = row.createCell(cellnum++);
                            celda17Aud.setCellValue(a.getTelefono_Tecnico_Ot_Padre_Onyx_Aud() != null ? a.getTelefono_Tecnico_Ot_Padre_Onyx_Aud() : "");
                            Cell celda18 = row.createCell(cellnum++);
                            celda18.setCellValue(a.getDescripcion_Onyx() != null ? a.getDescripcion_Onyx() : "");

                            // "Descripcion ONYX[Audit]","Segmento ONYX","Segmento ONYX[Audit]","Tipo Solucion ONYX","Tipo Solucion ONYX[Audit]","Servicios ONYX",
                            Cell celda18Aud = row.createCell(cellnum++);
                            celda18Aud.setCellValue(a.getDescripcion_Onyx_Aud() != null ? a.getDescripcion_Onyx_Aud() : "");
                            Cell cell25 = row.createCell(cellnum++);
                            cell25.setCellValue(a.getSegmento_Onyx() != null ? a.getSegmento_Onyx() : "");
                            Cell cell25Aud = row.createCell(cellnum++);
                            cell25Aud.setCellValue(a.getSegmento_Onyx_Aud() != null ? a.getSegmento_Onyx_Aud() : "");
                            Cell cell26 = row.createCell(cellnum++);
                            cell26.setCellValue(a.getTipo_Servicio_Onyx() != null ? a.getTipo_Servicio_Onyx() : "");
                            Cell cell26Aud = row.createCell(cellnum++);
                            cell26Aud.setCellValue(a.getTipo_Servicio_Onyx_Aud() != null ? a.getTipo_Servicio_Onyx_Aud() : "");
                            Cell cell27 = row.createCell(cellnum++);
                            cell27.setCellValue(a.getServicios_Onyx() != null ? a.getServicios_Onyx() : "");

                            //"Servicios ONYX[Audit]","Recurrente mensual ONYX","Recurrente Mensual[Audit]","Codigo servicio ONYX","Codigo servicio ONYX[Audit]",
                            Cell cell27Aud = row.createCell(cellnum++);
                            cell27Aud.setCellValue(a.getServicios_Onyx_Aud() != null ? a.getServicios_Onyx_Aud() : "");
                            Cell cell28 = row.createCell(cellnum++);
                            cell28.setCellValue(a.getRecurrente_Mensual_Onyx() != null ? a.getRecurrente_Mensual_Onyx() : "");
                            Cell cell28Aud = row.createCell(cellnum++);
                            cell28Aud.setCellValue(a.getRecurrente_Mensual_Onyx_Aud() != null ? a.getRecurrente_Mensual_Onyx_Aud() : "");
                            Cell cell29 = row.createCell(cellnum++);
                            cell29.setCellValue(a.getCodigo_Servicio_Onyx() != null ? a.getCodigo_Servicio_Onyx() : "");
                            Cell cell29Aud = row.createCell(cellnum++);
                            cell29Aud.setCellValue(a.getCodigo_Servicio_Onyx_Aud() != null ? a.getCodigo_Servicio_Onyx_Aud() : "");

                            //  "Vendedor ONYX","Vendedor ONYX[Audit]","Telefono Vendedor ONYX","Telefono Vendedor ONYX[Audit]","Estado OT hija ONYX","Estado OT hija ONYX[Audit]",
                            Cell cell30 = row.createCell(cellnum++);
                            cell30.setCellValue(a.getVendedor_Onyx() != null ? a.getVendedor_Onyx() : "");
                            Cell cell30Aud = row.createCell(cellnum++);
                            cell30Aud.setCellValue(a.getVendedor_Onyx_Aud() != null ? a.getVendedor_Onyx_Aud() : "");
                            Cell cell31 = row.createCell(cellnum++);
                            cell31.setCellValue(a.getTelefono_Vendedor_Onyx() != null ? a.getTelefono_Vendedor_Onyx() : "");
                            Cell cell31Aud = row.createCell(cellnum++);
                            cell31Aud.setCellValue(a.getTelefono_Vendedor_Onyx_Aud() != null ? a.getTelefono_Vendedor_Onyx_Aud() : "");
                            Cell cell32 = row.createCell(cellnum++);
                            cell32.setCellValue(a.getEstado_Ot_Hija_Onyx_Cm() != null ? a.getEstado_Ot_Hija_Onyx_Cm() : "");
                            Cell cell32Aud = row.createCell(cellnum++);
                            cell32Aud.setCellValue(a.getEstado_Ot_Hija_Onyx_Cm_Aud() != null ? a.getEstado_Ot_Hija_Onyx_Cm_Aud() : "");

                            //"Estado OT padre ONYX","Estado OT padre ONYX[Audit]","Fecha compromiso OT padre ONYX","Fecha compromiso OT padre ONYX[Audit]","OT Hija Resolucion 1 ONYX",
                            Cell cell33 = row.createCell(cellnum++);
                            cell33.setCellValue(a.getEstado_Ot_Padre_Onyx_Cm() != null ? a.getEstado_Ot_Padre_Onyx_Cm() : "");
                            Cell cell33Aud = row.createCell(cellnum++);
                            cell33Aud.setCellValue(a.getEstado_Ot_Padre_Onyx_Cm_Aud() != null ? a.getEstado_Ot_Padre_Onyx_Cm_Aud() : "");
                            Cell cell34 = row.createCell(cellnum++);
                            cell34.setCellValue(a.getFecha_Compromiso_Ot_Padre_Onyx() != null ? a.getFecha_Compromiso_Ot_Padre_Onyx() : "");
                            Cell cell34Aud = row.createCell(cellnum++);
                            cell34Aud.setCellValue(a.getFecha_Compromiso_Ot_Padre_Onyx_Aud() != null ? a.getFecha_Compromiso_Ot_Padre_Onyx_Aud() : "");

                            // "OT Hija Resolucion 1 ONYX","OT Hija Resolucion 1 ONYX[Audit]","OT Hija Resolucion 2 ONYX","OT Hija Resolucion 2 ONYX[Audit]",
                            Cell cell35 = row.createCell(cellnum++);
                            cell35.setCellValue(a.getOt_Hija_Resolucion_1_Onyx() != null ? a.getOt_Hija_Resolucion_1_Onyx() : "");
                            Cell cell35Aud = row.createCell(cellnum++);
                            cell35Aud.setCellValue(a.getOt_Hija_Resolucion_1_Onyx_Aud() != null ? a.getOt_Hija_Resolucion_1_Onyx_Aud() : "");
                            Cell cell36 = row.createCell(cellnum++);
                            cell36.setCellValue(a.getOt_Hija_Resolucion_2_Onyx() != null ? a.getOt_Hija_Resolucion_2_Onyx() : "");
                            Cell cell36Aud = row.createCell(cellnum++);
                            cell36Aud.setCellValue(a.getOt_Hija_Resolucion_2_Onyx_Aud() != null ? a.getOt_Hija_Resolucion_2_Onyx_Aud() : "");

                            //"OT Hija Resolucion 3 ONYX", "Resolucion 3 ONYX[Audit]","OT Hija Resolucion 4 ONYX","OT Hija Resolucion 4 ONYX[Audit]"
                            Cell cell37 = row.createCell(cellnum++);
                            cell37.setCellValue(a.getOt_Hija_Resolucion_3_Onyx() != null ? a.getOt_Hija_Resolucion_4_Onyx() : "");
                            Cell cell37Aud = row.createCell(cellnum++);
                            cell37Aud.setCellValue(a.getOt_Hija_Resolucion_3_Onyx_Aud() != null ? a.getOt_Hija_Resolucion_3_Onyx_Aud() : "");
                            Cell cell38 = row.createCell(cellnum++);
                            cell38.setCellValue(a.getOt_Hija_Resolucion_4_Onyx() != null ? a.getOt_Hija_Resolucion_4_Onyx() : "");
                            Cell cell38Aud = row.createCell(cellnum++);
                            cell38Aud.setCellValue(a.getOt_Hija_Resolucion_4_Onyx_Aud() != null ? a.getOt_Hija_Resolucion_4_Onyx_Aud() : "");

                            // "OT Padre Resolucion 1 ONYX","OT Padre Resolucion 1 ONYX[Audit]","OT Padre Resolucion 2 ONYX","OT Padre Resolucion 2 ONYX[Audit]",
                            Cell cell39 = row.createCell(cellnum++);
                            cell39.setCellValue(a.getOt_Padre_Resolucion_1_Onyx() != null ? a.getOt_Padre_Resolucion_1_Onyx() : "");
                            Cell cell39Aud = row.createCell(cellnum++);
                            cell39Aud.setCellValue(a.getOt_Padre_Resolucion_1_Onyx_Aud() != null ? a.getOt_Padre_Resolucion_1_Onyx_Aud() : "");
                            Cell cell40 = row.createCell(cellnum++);
                            cell40.setCellValue(a.getOt_Padre_Resolucion_2_Onyx() != null ? a.getOt_Padre_Resolucion_2_Onyx() : "");
                            Cell cell40Aud = row.createCell(cellnum++);
                            cell40Aud.setCellValue(a.getOt_Padre_Resolucion_2_Onyx_Aud() != null ? a.getOt_Padre_Resolucion_2_Onyx_Aud() : "");

                            //"OT Padre Resolucion 3 ONYX","OT Padre Resolucion 3 ONYX[Audit]","OT Padre Resolucion 4 ONYX","OT Padre Resolucion 4 ONYX[Audit]",
                            Cell cell41 = row.createCell(cellnum++);
                            cell41.setCellValue(a.getOt_Padre_Resolucion_3_Onyx() != null ? a.getOt_Padre_Resolucion_3_Onyx() : "");
                            Cell cell41Aud = row.createCell(cellnum++);
                            cell41Aud.setCellValue(a.getOt_Padre_Resolucion_3_Onyx_Aud() != null ? a.getOt_Padre_Resolucion_3_Onyx_Aud() : "");
                            Cell cell42 = row.createCell(cellnum++);
                            cell42.setCellValue(a.getOt_Padre_Resolucion_4_Onyx() != null ? a.getOt_Padre_Resolucion_4_Onyx() : "");
                            Cell cell42Aud = row.createCell(cellnum++);
                            cell42Aud.setCellValue(a.getOt_Padre_Resolucion_4_Onyx_Aud() != null ? a.getOt_Padre_Resolucion_4_Onyx_Aud() : "");

                            // "Appt number OFSC","Orden RR","Sub tipo orden OFSC","Fecha agenda MER","Usuario Creacion Agenda","Usuario Edicion Agenda",
                            Cell cell43 = row.createCell(cellnum++);
                            cell43.setCellValue(a.getAppt_number_OFSC() != null ? a.getAppt_number_OFSC() : "");
                            Cell cell44 = row.createCell(cellnum++);
                            cell44.setCellValue(a.getOrden_RR() != null ? a.getOrden_RR() : "");
                            Cell cell45 = row.createCell(cellnum++);
                            cell45.setCellValue(a.getSubTipo_Orden_OFSC() != null ? a.getSubTipo_Orden_OFSC() : "");
                            Cell cell45Aud = row.createCell(cellnum++);
                            cell45Aud.setCellValue(a.getSubTipo_Orden_OFSC_Aud() != null ? a.getSubTipo_Orden_OFSC_Aud() : "");
                            Cell cell46 = row.createCell(cellnum++);
                            cell46.setCellValue(a.getFecha_agenda_OFSC() != null ? a.getFecha_agenda_OFSC() : "");
                            Cell cell47 = row.createCell(cellnum++);
                            cell47.setCellValue(a.getUsuario_creacion_agenda_OFSC() != null ? a.getUsuario_creacion_agenda_OFSC() : "");
                            Cell cell48 = row.createCell(cellnum++);
                            cell48.setCellValue(a.getUsuarioModAgenda() != null ? a.getUsuarioModAgenda() : "");
                            Cell cell48Aud = row.createCell(cellnum++);
                            cell48Aud.setCellValue(a.getUsuarioModAgendaAud() != null ? a.getUsuarioModAgendaAud() : "");

                            //"Time slot OFSC","Estado agenda OFSC","Fecha de inicia agenda MER","Fecha fin agenda MER","Id aliado OFSC","Nombre aliado OFSC",
                            Cell cell49 = row.createCell(cellnum++);
                            cell49.setCellValue(a.getTime_slot_OFSC() != null ? a.getTime_slot_OFSC() : "");
                            Cell cell50 = row.createCell(cellnum++);
                            cell50.setCellValue(a.getEstado_agenda_OFSC() != null ? a.getEstado_agenda_OFSC() : "");
                            Cell cell50Aud = row.createCell(cellnum++);
                            cell50Aud.setCellValue(a.getEstado_agenda_OFSC_Aud() != null ? a.getEstado_agenda_OFSC_Aud() : "");
                            Cell cell51 = row.createCell(cellnum++);
                            cell51.setCellValue(a.getFecha_inicia_agenda_OFSC() != null ? a.getFecha_inicia_agenda_OFSC() : "");
                            Cell cell51Aud = row.createCell(cellnum++);
                            cell51Aud.setCellValue(a.getFecha_inicia_agenda_OFSC_Aud() != null ? a.getFecha_inicia_agenda_OFSC_Aud() : "");
                            Cell cell52 = row.createCell(cellnum++);
                            cell52.setCellValue(a.getFecha_fin_agenda_OFSC() != null ? a.getFecha_fin_agenda_OFSC() : "");
                            Cell cell52Aud = row.createCell(cellnum++);
                            cell52Aud.setCellValue(a.getFecha_fin_agenda_OFSC_Aud() != null ? a.getFecha_fin_agenda_OFSC_Aud() : "");
                            Cell cell53 = row.createCell(cellnum++);
                            cell53.setCellValue(a.getId_aliado_OFSC() != null ? a.getId_aliado_OFSC() : "");
                            Cell cell54 = row.createCell(cellnum++);
                            cell54.setCellValue(a.getNombre_aliado_OFSC() != null ? a.getNombre_aliado_OFSC() : "");

                            // "Id tecnico de aliado OFSC","Nombre Tecnico aliado OFSC","Multiagenda","Observaciones Tecnico OFSC",
                            Cell cell55 = row.createCell(cellnum++);
                            cell55.setCellValue(a.getId_tecnico_aliado_OFSC() != null ? a.getId_tecnico_aliado_OFSC() : "");
                            Cell cell55Aud = row.createCell(cellnum++);
                            cell55Aud.setCellValue(a.getId_tecnico_aliado_OFSC_Aud() != null ? a.getId_tecnico_aliado_OFSC_Aud() : "");
                            Cell cell56 = row.createCell(cellnum++);
                            cell56.setCellValue(a.getNombre_tecnico_aliado_OFSC() != null ? a.getNombre_tecnico_aliado_OFSC() : "");
                            Cell cell56Aud = row.createCell(cellnum++);
                            cell56Aud.setCellValue(a.getNombre_tecnico_aliado_OFSC_Aud() != null ? a.getNombre_tecnico_aliado_OFSC_Aud() : "");
                            Cell cell57 = row.createCell(cellnum++);
                            cell57.setCellValue(a.getUltima_agenda_multiagenda() != null ? a.getUltima_agenda_multiagenda() : "");
                            Cell cell58 = row.createCell(cellnum++);
                            cell58.setCellValue(a.getObservaciones_tecnico_OFSC() != null ? a.getObservaciones_tecnico_OFSC() : "");
                            Cell cell59 = row.createCell(cellnum++);

                            //"Regional","Ciudad","Codigo Proyecto","Direccion Facturacion","Indicador de cumplimiento de fecha de compromiso ",
                            cell59.setCellValue(a.getRegional() != null ? a.getRegional() : "");
                            Cell cell60 = row.createCell(cellnum++);
                            cell60.setCellValue(a.getCiudad() != null ? a.getCiudad() : "");
                            Cell cell61 = row.createCell(cellnum++);
                            cell61.setCellValue(a.getCodigoProyecto() != null ? a.getCodigoProyecto() : "");
                            Cell cell62 = row.createCell(cellnum++);
                            cell62.setCellValue(a.getDireccion_Onyx() != null ? a.getDireccion_Onyx() : "");
                            Cell cell62Aud = row.createCell(cellnum++);
                            cell62Aud.setCellValue(a.getDireccion_Onyx_Aud() != null ? a.getDireccion_Onyx_Aud() : "");
                            Cell cell622 = row.createCell(cellnum++);
                            cell622.setCellValue(a.getCiudadFact() != null ? a.getCiudadFact() : "");
                            Cell cell622Aud = row.createCell(cellnum++);
                            cell622Aud.setCellValue(a.getCiudadFactAud() != null ? a.getCiudadFactAud() : "");
                            Cell cell63 = row.createCell(cellnum++);
                            cell63.setCellValue(a.getIndicadorCumplimiento() != null ? a.getIndicadorCumplimiento() : "");
                            Cell cell644 = row.createCell(cellnum++);

                            //"Nombre persona contacto que confirmó","Email persona contacto que confirmo","Número telefónico persona que confirmo",
                            cell644.setCellValue(a.getContacto_Tecnico_Ot_Padre_Onyx() != null ? a.getContacto_Tecnico_Ot_Padre_Onyx() : "");
                            Cell cell64 = row.createCell(cellnum++);
                            cell64.setCellValue(a.getEmailCTec() != null ? a.getEmailCTec() : "");
                            Cell cell645 = row.createCell(cellnum++);
                            cell645.setCellValue(a.getTelefono_Tecnico_Ot_Padre_Onyx() != null ? a.getTelefono_Tecnico_Ot_Padre_Onyx() : "");
                            Cell cell65 = row.createCell(cellnum++);

                            // "Persona que Atiende","Telefono persona atiende","Tiempo de programación","Tiempo de atención compromiso","Tiempo de ejecución del trabajo",
                            cell65.setCellValue(a.getPersonaAtiendeSitio() != null ? a.getPersonaAtiendeSitio() : "");
                            Cell cell66 = row.createCell(cellnum++);
                            cell66.setCellValue(a.getTelefonoAtiendeSitio() != null ? a.getTelefonoAtiendeSitio() : "");
                            Cell cell67 = row.createCell(cellnum++);
                            cell67.setCellValue(a.getTiempoProgramacion() != null ? a.getTiempoProgramacion() : "");
                            Cell cell68 = row.createCell(cellnum++);
                            cell68.setCellValue(a.getTiempoAtencion() != null ? a.getTiempoAtencion() : "");
                            Cell cell69 = row.createCell(cellnum++);
                            cell69.setCellValue(a.getTiempoEjecucion() != null ? a.getTiempoEjecucion() : "");

                            // "Resultado de la orden de trabajo","Antiguedad de Ot Onyx","Cant Reagendas","Motivos de Reagendas","Aliado Implementacion"};
                            Cell cell70 = row.createCell(cellnum++);
                            cell70.setCellValue(a.getResultadoOrden() != null ? a.getResultadoOrden() : "");
                            Cell cell71 = row.createCell(cellnum++);
                            cell71.setCellValue(a.getAntiguedadOrden() != null ? a.getAntiguedadOrden() : "");
                            Cell cell72 = row.createCell(cellnum++);
                            cell72.setCellValue(a.getCantReagenda() != null ? a.getCantReagenda() : "");
                            Cell cell722 = row.createCell(cellnum++);
                            cell722.setCellValue(a.getConveniencia() != null ? a.getConveniencia() : "");
                            Cell cell73 = row.createCell(cellnum++);
                            cell73.setCellValue(a.getMotivosReagenda() != null ? a.getMotivosReagenda() : "");
                            Cell cell74 = row.createCell(cellnum++);
                            cell74.setCellValue(a.getaImplement() != null ? a.getaImplement() : "");
                        }

                        rownum++;
                    }
                    System.gc();
                }
            }
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/vnd.ms-excel");

            SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");

            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + "Historico Ordenes Ccmm - Direcciones" + "_" + formato.format(new Date())
                    + ".xlsx\"");
            OutputStream output = response.getOutputStream();

            workbook.write(output);
            output.flush();
            output.close();
            fc.responseComplete();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en ReporteCompComercial:exportExcelDet() " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteCompComercial:exportExcelDet() " + e.getMessage(), e, LOGGER);
        }
        return "OK";
    }

    public void downloadCvsTxt() {
        try {
            exportCsv();
        } catch (Exception e) {
            String msn = "Se presenta error al filtrar los registros: " + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn, e);
        }
    }

    public boolean validarFechaCreacionOt() {
        boolean respuesta = false;
        try {
            if (filtroFechas == null) {
                  FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "Filtro Fecha es obligatorio.", ""));
                return respuesta = false;
            }
            if (fechaInicioOt != null && fechaFinOt != null) {
                if (validarFecha(fechaInicioOt) && validarFecha(fechaFinOt)) {
                    if (fechaInicioOt.before(fechaFinOt)) {
                        System.err.println("Fecha Inicio  es menor ");
                        return respuesta = true;
                    } else {
                        if (fechaFinOt.before(fechaInicioOt)) {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "La fecha Inicial es Mayor a la Final.", ""));
                            return respuesta = false;
                        } else {
                            return respuesta = true;
                        }
                    }
                } else {
                    return respuesta = false;
                }
            } 

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:validarFechas(). " + e.getMessage(), e, LOGGER);
        }

        return respuesta;
    }

    public boolean validarFechaInicioOtOnyxHija() {
        boolean respuesta = false;
        try {
            if (filtroFechas == null) {
               
                return respuesta = false;

            }
            if (fechaInicioOtHijaOnyx != null && fechaFinOtHijaOnyx != null) {
                if (validarFecha(fechaInicioOtHijaOnyx) && validarFecha(fechaFinOtHijaOnyx)) {
                    if (fechaInicioOtHijaOnyx.before(fechaFinOtHijaOnyx)) {
                        System.err.println("Fecha Inicio  es menor ");
                        return respuesta = true;
                    } else {
                        if (fechaFinOtHijaOnyx.before(fechaInicioOtHijaOnyx)) {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "La fecha Inicial es Mayor a la Final.", ""));
                            return respuesta = false;
                        } else {
                            return respuesta = true;
                        }
                    }
                } else {
                    return respuesta = false;
                }
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:validarFechas(). " + e.getMessage(), e, LOGGER);
        }

        return respuesta;
    }
    public boolean validarFechaCreacionInicioOtOnyx() {
        boolean respuesta = false;
        try {
            if (filtroFechas == null) {
              
                return respuesta = false;

            }
            if (fechaCreacionIniOnyx != null && fechaCreacionFinOnyx != null) {
                if (validarFecha(fechaCreacionIniOnyx) && validarFecha(fechaCreacionFinOnyx)) {
                    if (fechaCreacionIniOnyx.before(fechaCreacionFinOnyx)) {
                        System.err.println("Fecha Inicio  es menor ");
                        return respuesta = true;
                    } else {
                        if (fechaCreacionFinOnyx.before(fechaCreacionIniOnyx)) {

                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "La fecha Inicial es Mayor a la Final.", ""));
                            return respuesta = false;
                        } else {
                            return respuesta = true;
                        }
                    }
                } else {
                    return respuesta = false;
                }
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:validarFechas(). " + e.getMessage(), e, LOGGER);
        }

        return respuesta;
    }
    public boolean validarFechaInicioAgendOFSC() {
        boolean respuesta = false;
        try {
            if (filtroFechas == null) {
               
                return respuesta = false;

            }
            if (fechaInicioAgendOFSC != null && fechaFinAgendOFSC != null) {
                if (validarFecha(fechaInicioAgendOFSC) && validarFecha(fechaFinAgendOFSC)) {
                    if (fechaInicioAgendOFSC.before(fechaFinAgendOFSC)) {
                        return respuesta = true;
                    } else {
                        if (fechaFinAgendOFSC.before(fechaInicioAgendOFSC)) {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "La fecha Inicial es Mayor a la Final.", ""));
                            return respuesta = false;

                        } else {
                            return respuesta = true;
                        }
                    }
                } else {
                    return respuesta = false;
                }
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:validarFechas(). " + e.getMessage(), e, LOGGER);
        }

        return respuesta;
    }
    
    
    public boolean validarFechaFinAsigTecnico() {
        boolean respuesta = false;
        try {
            if (filtroFechas == null) {
               
                return respuesta = false;

            }
            if (fechaInicioAsigTecnico != null && fechaFinAsigTecnico != null) {
                if (validarFecha(fechaInicioAsigTecnico) && validarFecha(fechaFinAsigTecnico)) {
                    if (fechaInicioAsigTecnico.before(fechaFinAsigTecnico)) {
                        System.err.println("Fecha Inicio  es menor ");
                        return respuesta = true;
                    } else {
                        if (fechaFinAsigTecnico.before(fechaInicioAsigTecnico)) {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "La fecha Inicial es Mayor a la Final.", ""));
                            return respuesta = false;

                        } else {
                            return respuesta = true;
                        }
                    }
                } else {
                    return respuesta = false;
                }
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:validarFechas(). " + e.getMessage(), e, LOGGER);
        }

        return respuesta;
    }
    
    public boolean validarFechaInicioCierreAgenda() {
        boolean respuesta = false;
        try {
             if (filtroFechas == null) {
               
                return respuesta = false;

            }
                if (fechaInicioCierreAgenda != null && fechaFinCierreAgenda != null) {
                    if (validarFecha(fechaInicioCierreAgenda) && validarFecha(fechaFinCierreAgenda)) {
                        if (fechaInicioCierreAgenda.before(fechaFinCierreAgenda)) {
                            System.err.println("Fecha Inicio  es menor ");
                            return respuesta = true;
                        } else {
                            if (fechaFinCierreAgenda.before(fechaInicioCierreAgenda)) {
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                "La fecha Inicial es Mayor a la Final.", ""));
                                return respuesta = false;
                               
                            } else {
                                return respuesta = true;
                            }
                        }
                    } else {
                        return respuesta = false;
                    }
                }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:validarFechas(). " + e.getMessage(), e, LOGGER);
        }

        return respuesta;
    }
    public boolean validarFechaInicioCancAgenda() {
        boolean respuesta = false;
        try {
            if (filtroFechas == null) {
               
                return respuesta = false;

            }
            if (fechaInicioCancAgenda != null && fechaFinCancAgenda != null) {
                if (validarFecha(fechaInicioCancAgenda) && validarFecha(fechaFinCancAgenda)) {
                    if (fechaInicioCancAgenda.before(fechaFinCancAgenda)) {
                        System.err.println("Fecha Inicio  es menor ");
                        return respuesta = true;
                    } else {
                        if (fechaFinCancAgenda.before(fechaInicioCancAgenda)) {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "La fecha Inicial es Mayor a la Final.", ""));
                            return respuesta = false;

                        } else {
                            return respuesta = true;
                        }
                    }
                } else {
                    return respuesta = false;
                }
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:validarFechas(). " + e.getMessage(), e, LOGGER);
        }

        return respuesta;
    }
    
       public boolean validarFechaInicioReagenda() {
        boolean respuesta = false;
        try {
             if (filtroFechas == null) {
                
                return respuesta = false;

            }
                if (fechaInicioReagenda != null && fechaFinReagenda != null) {
                    if (validarFecha(fechaInicioReagenda) && validarFecha(fechaFinReagenda)) {
                        if (fechaInicioReagenda.before(fechaFinReagenda)) {
                           
                            return respuesta = true;
                        } else {
                            if (fechaFinReagenda.before(fechaInicioReagenda)) {
                                 FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                "La fecha Inicial es Mayor a la Final.", ""));
                                return respuesta = false;
                              
                            } else {
                                return respuesta = true;
                            }
                        }
                    } else {
                        return respuesta = false;
                    }
                }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteEstadoActualOtCMBean:validarFechas(). " + e.getMessage(), e, LOGGER);
        }

        return respuesta;
    }
      
      
          
    public boolean validarFechaInicioSuspension() {
        boolean respuesta = false;
        try {
            if (filtroFechas == null) {
               
                return respuesta = false;

            }
                if (fechaInicioSuspension != null && fechaFinSuspension != null) {
                    if (validarFecha(fechaInicioSuspension) && validarFecha(fechaFinSuspension)) {
                        if (fechaInicioSuspension.before(fechaFinSuspension)) {
                            System.err.println("Fecha Inicio  es menor ");
                            return respuesta = true;
                        } else {
                            if (fechaFinSuspension.before(fechaInicioSuspension)) {
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                "La fecha Inicial es Mayor a la Final.", ""));
                                return respuesta = false;
                               
                            } else {
                                return respuesta = true;
                            }
                        }
                    } else {
                        return respuesta = false;
                    }
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
        //ordenes
        params.put("subTipoOrden", this.subTipoOrden);
        params.put("tipoOrden", this.tipoOrden);
        params.put("tipoOrdenOfscSelected", this.tipoOrdenOfscSelected);
        params.put("subTipoOrdenOfscSelected", this.subTipoOrdenOfscSelected);
        //estados
        params.put("listEstadosOtCmDirSelected", this.estadoInternolist);
        params.put("listEstadosSelected", this.listEstadosSelected);
        //ot
        params.put("otIni", this.otIni);
        params.put("otFin", this.otFin);
        params.put("tipoSolucionSelected", this.tipoSolucionSelected);
        params.put("filtroFechas", this.filtroFechas);
        //segmento
        params.put("listSegmentoSelected", this.listSegmentoSelected);
        listNombresSegmentos = new ArrayList<>();
        if (((List<BigDecimal>) params.get("listSegmentoSelected")) != null && 
                !((List<BigDecimal>) params.get("listSegmentoSelected")).isEmpty() && !listaSegmentos.isEmpty()) {
            List<String> listaid = ((List<String>) params.get("listSegmentoSelected"));
            for (SegmentoDto segmento : listaSegmentos) {
                String idSegmento = segmento.getIdSegmento().toString();
                for (String idSeg : listaid) {
                    if (idSeg.equals(idSegmento)) {
                        listNombresSegmentos.add(segmento.getNombreSegmento());

                    }
                }
            }
        }
        params.put("listSegmentoSelected", this.listNombresSegmentos);
        
        //regional
        params.put("listRegionalSelected", this.listRegionalSelected);
        try {
            List<CmtRegionalRr> listaRegionales ;
            listCodRegionales = new ArrayList<>();
            if (!((List<BigDecimal>) params.get("listRegionalSelected")).isEmpty()) {
                listaRegionales = rrRegionalesFacadeLocal.findByCodigo((List<BigDecimal>) params.get("listRegionalSelected"));
                for (CmtRegionalRr regional : listaRegionales) {
                    listCodRegionales.add(regional.getCodigoRr());
                }
            }

        } catch (ApplicationException ex) {
            String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + " " + ex.getMessage();
            LOGGER.error(msgError, ex);
        }
        params.put("listRegionalSelected", this.listCodRegionales);
        
        //ciudades
        params.put("listRrCiudadesSelected", this.listRrCiudadesSelected);
       
        try {
            List<CmtComunidadRr> listCiudades ;
            listCodCiudades = new ArrayList<>();
            if (!((List<BigDecimal>) params.get("listRrCiudadesSelected")).isEmpty()) {
                listCiudades = cmtComunidadRrFacadeLocal.findByListComunidad((List<BigDecimal>) params.get("listRrCiudadesSelected"));
                for (CmtComunidadRr regional : listCiudades) {
                    listCodCiudades.add(regional.getCodigoRr());
                }
            }
 
        } catch (ApplicationException ex) {
            String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + " " + ex.getMessage();
            LOGGER.error(msgError, ex);
        }
        params.put("listRrCiudadesSelected", this.listCodCiudades);
       //filtros varios
        if (this.filtroVarios != null && this.valor != null) {
            params.put("valor", this.valor);
            if (this.filtroVarios.equals("Codigo de proyecto")) {
                params.put("codProyecto", this.filtroVarios);
            }
            if (this.filtroVarios.equals("Nit del cliente")) {
                params.put("nitCliente", this.filtroVarios);
            }
            if (this.filtroVarios.equals("Numero de Ot Padre")) {
                params.put("numOtOnyxPadre", this.filtroVarios);
            }
            if (this.filtroVarios.equals("Numero de Ot Hija")) {
                params.put("numeroOtOnyxHija", this.filtroVarios);
            }
            if (this.filtroVarios.equals("Nombre del cliente")) {
                params.put("nombreCliente", this.filtroVarios);
            }
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
    
     
    public void cargarListaTiposOrdenes() throws ApplicationException {
        CmtTipoBasicaMgl tipoGeneralOrdenId;
        tipoGeneralOrdenId = tipoBasicaMglFacadeLocal.
                findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_GENERAL_OT);
        tipoGeneralOrdenTrabajo = basicaMglFacadeLocal.findByTipoBasica(tipoGeneralOrdenId);
    }
    
    public void cargarListaSubTiposOrdenes() throws ApplicationException {
        // tipo de trabajo ccmm y hhpp
        listTipoOtMgl = cmtTipoOtMglFacadeLocal.findAllSubTipoOtCmHhpp();

    }
    
    public void cargarListaCodigosTiposOrdenesOFSC() throws ApplicationException {
        // tipo de ordenes OFSC
       CmtTipoBasicaMgl tipoGeneralOrdenId;
        tipoGeneralOrdenId = tipoBasicaMglFacadeLocal.
                findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_GENERAL_OT);
        tipoGeneralOrdenTrabajo = basicaMglFacadeLocal.findByTipoBasica(tipoGeneralOrdenId);
    }
    
    public void cargarListaSubTiposOrdenesOFSC() throws ApplicationException {
         listBasicaFinalSubtipoOrdenOFSC = new ArrayList<>();
        // tipo de ordenes OFSC cm
        CmtTipoBasicaMgl tipoBasicaCmSubTipoOrdenOFSC;
        tipoBasicaCmSubTipoOrdenOFSC = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                Constant.TIPO_BASICA_SUB_TIPO_ORDEN_OFSC);
        // tipo de ordenes OFSC dir
        CmtTipoBasicaMgl tipoBasicaDirSubTipoOrdenOFSC;
        tipoBasicaDirSubTipoOrdenOFSC = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                Constant.TIPO_BASICA_SUB_TIPO_TRABAJO_DIRECCIONES);

        listBasicaSubtipoOrdenOFSC = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaCmSubTipoOrdenOFSC);
        listBasicaSubtipoOrdenDirOFSC = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaDirSubTipoOrdenOFSC);
        listBasicaFinalSubtipoOrdenOFSC.addAll(listBasicaSubtipoOrdenOFSC);
        listBasicaFinalSubtipoOrdenOFSC.addAll(listBasicaSubtipoOrdenDirOFSC);
        
    }

    public void cargarListaEstadosOtCmDir() throws ApplicationException {
            // estados internos 
        List<CmtBasicaMgl> listaEstadosIntCM = null;
        List<CmtBasicaMgl> listaEstadosIntAbiertos = null;
        List<CmtBasicaMgl> listaEstadosIntCerrado = null;
        List<CmtBasicaMgl> listaEstadosIntAnulado = null;
        listTablaBasicaEstadoInternoOt = new ArrayList<>();
        
        // Lista estados Internos de cuentas matrices     
        CmtTipoBasicaMgl tipoBasicaTipoEstadoInternoOt;
        tipoBasicaTipoEstadoInternoOt = cmtTipoBasicaMglFacadeLocal.
                findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_INTERNO_OT);
          listaEstadosIntCM = cmtBasicaMglFacadeLocal.
                findByTipoBasica(tipoBasicaTipoEstadoInternoOt);
        
         //ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO
        CmtTipoBasicaMgl tipoBasicaEstadoExternoAbierto;
        tipoBasicaEstadoExternoAbierto = tipoBasicaMglFacadeLocal.
                findByCodigoInternoApp(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO);
        listaEstadosIntAbiertos = cmtBasicaMglFacadeLocal.
                findByTipoBasica(tipoBasicaEstadoExternoAbierto);
        
          //ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO
        CmtTipoBasicaMgl tipoBasicaEstadoExternoCerrado;
        tipoBasicaEstadoExternoCerrado = tipoBasicaMglFacadeLocal.
                findByCodigoInternoApp(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO);
        listaEstadosIntCerrado = cmtBasicaMglFacadeLocal.
                findByTipoBasica(tipoBasicaEstadoExternoCerrado);
        
        
         //ESTADO_GENERAL_OT_HHPP_RAZON_ANULADO
        CmtTipoBasicaMgl tipoBasicaEstadoExternoAnulado;
        tipoBasicaEstadoExternoAnulado = tipoBasicaMglFacadeLocal.
                findByCodigoInternoApp(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ANULADO);
        listaEstadosIntAnulado = cmtBasicaMglFacadeLocal.
                findByTipoBasica(tipoBasicaEstadoExternoAnulado);
        
        
        listTablaBasicaEstadoInternoOt.addAll(listaEstadosIntCM);
        listTablaBasicaEstadoInternoOt.addAll(listaEstadosIntAbiertos);
        listTablaBasicaEstadoInternoOt.addAll(listaEstadosIntCerrado);
        listTablaBasicaEstadoInternoOt.addAll(listaEstadosIntAnulado);
    }

    public void cargarListaEstados() throws ApplicationException {
               
          
           List<CmtBasicaMgl> listaEstadosDirecciones = null;
        List<CmtBasicaMgl> listaEstadosCuentasM = null;
        listaEstadosOt = new ArrayList<>();
         // estados hhpp 
        CmtTipoBasicaMgl tipoBasicaEstadoDir;
        tipoBasicaEstadoDir = tipoBasicaMglFacadeLocal.
                findByCodigoInternoApp(Constant.ESTADO_GENERAL_OT_HHPP);
        if (tipoBasicaEstadoDir != null) {
            listaEstadosDirecciones = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaEstadoDir);
        }

        if (listaEstadosDirecciones != null) {
            for (CmtBasicaMgl estadoDir : listaEstadosDirecciones) {
                estadoDir.setNombreBasica(estadoDir.getNombreBasica() + "(DIR)");
            }
        }

        // estados ccmm listaEstadosOt
        CmtTipoBasicaMgl tipoBasicaEstadoCm;
        tipoBasicaEstadoCm = tipoBasicaMglFacadeLocal.
                findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_EXTERNO_OT);

        if (tipoBasicaEstadoCm != null) {
            listaEstadosCuentasM = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaEstadoCm);
        }
        if (listaEstadosCuentasM != null) {
            for (CmtBasicaMgl estadoCm : listaEstadosCuentasM) {
                estadoCm.setNombreBasica(estadoCm.getNombreBasica() + "(CM)");
            }
        }
       
        listaEstadosOt.addAll(listaEstadosDirecciones);
        listaEstadosOt.addAll(listaEstadosCuentasM);
         

    }

    public void cargarListaBasicaDir() throws ApplicationException {

        // direcciones 
        CmtTipoBasicaMgl subtipoOdenDir = null;
        subtipoOdenDir = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                Constant.TIPO_BASICA_SUB_TIPO_TRABAJO_DIRECCIONES);
        listaBasicaDir = cmtBasicaMglFacadeLocal.findByTipoBasica(subtipoOdenDir);
    }

    public void cargarListaBasicaCm() throws ApplicationException {

        // cuentas matrices
        CmtTipoBasicaMgl subtipoOdenCm = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                Constant.TIPO_BASICA_SUB_TIPO_ORDEN_OFSC);
        listaBasicaCm = cmtBasicaMglFacadeLocal.findByTipoBasica(subtipoOdenCm);

    }
    
    // Validar Opciones por Rol    
    public boolean validarOpcionReporte() {
        return validarEdicionRol(BTREPORTEHITRAC);
    }    
    //funcion General
      private boolean validarEdicionRol(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
            return tieneRolPermitido;
        }catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al SubtipoOtVtTecBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public void cargarListacmtRegionalMgl() throws ApplicationException {
        //regionales
        listacmtRegionalMgl = cmtRegionalMglFacadeLocal.findAll();
    }

    public void cargarListacmtComunidadRr() throws ApplicationException {
        //comunidades
        listacmtComunidadRr = cmtComunidadRrFacadeLocal.findAll();
    }

    public void cargarListaEstadosIntExt() throws ApplicationException {

        // estados internos externos  
        listaEstadosIntExt = cmtEstadoIntxExtMglFacaceLocal.findAll();
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
          numRegProcesados = CmtReporteHistoricoOtDIRDto.getNumeroRegistrosProcesados();
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

    public List<ReporteHistoricoOtDIRDto> getListReporteHistoricoOtDIRDto() {
        return listReporteHistoricoOtDIRDto;
    }

    public void setListReporteHistoricoOtDIRDto(List<ReporteHistoricoOtDIRDto> listReporteHistoricoOtDIRDto) {
        this.listReporteHistoricoOtDIRDto = listReporteHistoricoOtDIRDto;
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

    public List<TipoOtCmDirDto> getListTipoOtMgl() {
        return listTipoOtMgl;
    }

    public void setListTipoOtMgl(List<TipoOtCmDirDto> listTipoOtMgl) {
        this.listTipoOtMgl = listTipoOtMgl;
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

    public List<String> getListCodRegionales() {
        return listCodRegionales;
    }

    public void setListCodRegionales(List<String> listCodRegionales) {
        this.listCodRegionales = listCodRegionales;
    }

    public List<String> getListCodCiudades() {
        return listCodCiudades;
    }

    public void setListCodCiudades(List<String> listCodCiudades) {
        this.listCodCiudades = listCodCiudades;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getNumOtOnyxPadre() {
        return numOtOnyxPadre;
    }

    public void setNumOtOnyxPadre(String numOtOnyxPadre) {
        this.numOtOnyxPadre = numOtOnyxPadre;
    }

    public String getNumeroOtOnyxHija() {
        return numeroOtOnyxHija;
    }

    public void setNumeroOtOnyxHija(String numeroOtOnyxHija) {
        this.numeroOtOnyxHija = numeroOtOnyxHija;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getListaResolucionSelected() {
        return listaResolucionSelected;
    }

    public void setListaResolucionSelected(String listaResolucionSelected) {
        this.listaResolucionSelected = listaResolucionSelected;
    }

    public List<String> getListNombresSegmentos() {
        return listNombresSegmentos;
    }

    public void setListNombresSegmentos(List<String> listNombresSegmentos) {
        this.listNombresSegmentos = listNombresSegmentos;
    }

    public List<CmtBasicaMgl> getListaEstadosExternos() {
        return listaEstadosExternos;
    }

    public void setListaEstadosExternos(List<CmtBasicaMgl> listaEstadosExternos) {
        this.listaEstadosExternos = listaEstadosExternos;
    }

    public List<BigDecimal> getListEstadosOtCmDirSelected() {
        return listEstadosOtCmDirSelected;
    }

    public void setListEstadosOtCmDirSelected(List<BigDecimal> listEstadosOtCmDirSelected) {
        this.listEstadosOtCmDirSelected = listEstadosOtCmDirSelected;
    }

    public List<EstadosOtCmDirDto> getListEstadosOtCmDir() {
        return listEstadosOtCmDir;
    }

    public void setListEstadosOtCmDir(List<EstadosOtCmDirDto> listEstadosOtCmDir) {
        this.listEstadosOtCmDir = listEstadosOtCmDir;
    }

    public String getCodProyecto() {
        return codProyecto;
    }

    public void setCodProyecto(String codProyecto) {
        this.codProyecto = codProyecto;
    }

    public String getTipoSolucionSelected() {
        return tipoSolucionSelected;
    }

    public void setTipoSolucionSelected(String tipoSolucionSelected) {
        this.tipoSolucionSelected = tipoSolucionSelected;
    }

    public List<SegmentoDto> getListaSegmentos() {
        return listaSegmentos;
    }

    public void setListaSegmentos(List<SegmentoDto> listaSegmentos) {
        this.listaSegmentos = listaSegmentos;
    }

    public List<BigDecimal> getListSegmentoSelected() {
        return listSegmentoSelected;
    }

    public void setListSegmentoSelected(List<BigDecimal> listSegmentoSelected) {
        this.listSegmentoSelected = listSegmentoSelected;
    }

    public List<CmtRegionalRr> getRegionales() {
        return regionales;
    }

    public void setRegionales(List<CmtRegionalRr> regionales) {
        this.regionales = regionales;
    }

    public List<BigDecimal> getListRegionalSelected() {
        return listRegionalSelected;
    }

    public void setListRegionalSelected(List<BigDecimal> listRegionalSelected) {
        this.listRegionalSelected = listRegionalSelected;
    }

    public List<BigDecimal> getListRrCiudadesSelected() {
        return listRrCiudadesSelected;
    }

    public void setListRrCiudadesSelected(List<BigDecimal> listRrCiudadesSelected) {
        this.listRrCiudadesSelected = listRrCiudadesSelected;
    }

    public List<CmtComunidadRr> getRrCiudades() {
        return rrCiudades;
    }

    public void setRrCiudades(List<CmtComunidadRr> rrCiudades) {
        this.rrCiudades = rrCiudades;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getFiltroVarios() {
        return filtroVarios;
    }

    public void setFiltroVarios(String filtroVarios) {
        this.filtroVarios = filtroVarios;
    }

    public List<BigDecimal> getSubTipoOrden() {
        return subTipoOrden;
    }

    public void setSubTipoOrden(List<BigDecimal> subTipoOrden) {
        this.subTipoOrden = subTipoOrden;
    }

    public boolean isPanelcreacionONYXHija() {
        return panelcreacionONYXHija;
    }

    public void setPanelcreacionONYXHija(boolean panelcreacionONYXHija) {
        this.panelcreacionONYXHija = panelcreacionONYXHija;
    }

    public Date getFechaCreacionFinOnyx() {
        return fechaCreacionFinOnyx;
    }

    public void setFechaCreacionFinOnyx(Date fechaCreacionFinOnyx) {
        this.fechaCreacionFinOnyx = fechaCreacionFinOnyx;
    }

    public Date getFechaInicioOtHijaOnyx() {
        return fechaInicioOtHijaOnyx;
    }

    public void setFechaInicioOtHijaOnyx(Date fechaInicioOtHijaOnyx) {
        this.fechaInicioOtHijaOnyx = fechaInicioOtHijaOnyx;
    }

    public Date getFechaFinOtHijaOnyx() {
        return fechaFinOtHijaOnyx;
    }

    public void setFechaFinOtHijaOnyx(Date fechaFinOtHijaOnyx) {
        this.fechaFinOtHijaOnyx = fechaFinOtHijaOnyx;
    }

    public Date getFechaCreacionIniOnyx() {
        return fechaCreacionIniOnyx;
    }

    public void setFechaCreacionIniOnyx(Date fechaCreacionIniOnyx) {
        this.fechaCreacionIniOnyx = fechaCreacionIniOnyx;
    }

    public List<BigDecimal> getListEstadosSelected() {
        return listEstadosSelected;
    }

    public void setListEstadosSelected(List<BigDecimal> listEstadosSelected) {
        this.listEstadosSelected = listEstadosSelected;
    }

    public List<CmtBasicaMgl> getListaEstadosOt() {
        return listaEstadosOt;
    }

    public void setListaEstadosOt(List<CmtBasicaMgl> listaEstadosOt) {
        this.listaEstadosOt = listaEstadosOt;
    }

    public int getCantMaxRegExcel() {
        return cantMaxRegExcel;
    }

    public void setCantMaxRegExcel(int cantMaxRegExcel) {
        this.cantMaxRegExcel = cantMaxRegExcel;
    }

    public int getCantMaxRegCvs() {
        return cantMaxRegCvs;
    }

    public void setCantMaxRegCvs(int cantMaxRegCvs) {
        this.cantMaxRegCvs = cantMaxRegCvs;
    }

    public List<CmtBasicaMgl> getListaBasicaDir() {
        return listaBasicaDir;
    }

    public void setListaBasicaDir(List<CmtBasicaMgl> listaBasicaDir) {
        this.listaBasicaDir = listaBasicaDir;
    }

    public List<CmtBasicaMgl> getListaBasicaCm() {
        return listaBasicaCm;
    }

    public void setListaBasicaCm(List<CmtBasicaMgl> listaBasicaCm) {
        this.listaBasicaCm = listaBasicaCm;
    }

    public List<CmtRegionalRr> getListacmtRegionalMgl() {
        return listacmtRegionalMgl;
    }

    public void setListacmtRegionalMgl(List<CmtRegionalRr> listacmtRegionalMgl) {
        this.listacmtRegionalMgl = listacmtRegionalMgl;
    }

    public List<CmtComunidadRr> getListacmtComunidadRr() {
        return listacmtComunidadRr;
    }

    public void setListacmtComunidadRr(List<CmtComunidadRr> listacmtComunidadRr) {
        this.listacmtComunidadRr = listacmtComunidadRr;
    }

    public List<CmtEstadoIntxExtMgl> getListaEstadosIntExt() {
        return listaEstadosIntExt;
    }

    public void setListaEstadosIntExt(List<CmtEstadoIntxExtMgl> listaEstadosIntExt) {
        this.listaEstadosIntExt = listaEstadosIntExt;
    }

    public int getCantRegConsulta() {
        return cantRegConsulta;
    }

    public void setCantRegConsulta(int cantRegConsulta) {
        this.cantRegConsulta = cantRegConsulta;
    }

    public int getNumRegistrosResultado() {
        return numRegistrosResultado;
    }

    public void setNumRegistrosResultado(int numRegistrosResultado) {
        this.numRegistrosResultado = numRegistrosResultado;
    }

    public int getResultadosEncontrados() {
        return resultadosEncontrados;
    }

    public void setResultadosEncontrados(int resultadosEncontrados) {
        this.resultadosEncontrados = resultadosEncontrados;
    }

    public List<CmtBasicaMgl> getListBasicaSubtipoOrdenDirOFSC() {
        return listBasicaSubtipoOrdenDirOFSC;
    }

    public void setListBasicaSubtipoOrdenDirOFSC(List<CmtBasicaMgl> listBasicaSubtipoOrdenDirOFSC) {
        this.listBasicaSubtipoOrdenDirOFSC = listBasicaSubtipoOrdenDirOFSC;
    }

    public List<CmtBasicaMgl> getListBasicaFinalSubtipoOrdenOFSC() {
        return listBasicaFinalSubtipoOrdenOFSC;
    }

    public void setListBasicaFinalSubtipoOrdenOFSC(List<CmtBasicaMgl> listBasicaFinalSubtipoOrdenOFSC) {
        this.listBasicaFinalSubtipoOrdenOFSC = listBasicaFinalSubtipoOrdenOFSC;
    }


    
}
