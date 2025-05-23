package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mer.utils.enums.MessageSeverityEnum;
import co.com.claro.mgl.dtos.FiltroConsultaCompaniasDto;
import co.com.claro.mgl.dtos.ResponseValidarDireccionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosCallesFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtArchivosVtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCompaniaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCuentaMatrizMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionDetalleMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtEstadoxFlujoMglFacade;
import co.com.claro.mgl.facade.cm.CmtEstadoxFlujoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtHhppVtMglFacade;
import co.com.claro.mgl.facade.cm.CmtHorarioRestriccionMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOrdenTrabajoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificioMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificiosVtFacade;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoOtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtVisitaTecnicaMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtArchivosVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHhppVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.mbeans.cm.TecnologiaHabilitadaVtBean.HhppVtCasasDto;
import co.com.claro.mgl.mbeans.cm.ot.OtMglBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.services.comun.Utilidades;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.primefaces.model.file.UploadedFile;

import static co.com.claro.mer.utils.costants.VisitaTecnicaConstants.*;


/**
 *
 * @author alejandro.martine.ext@claro.com.co
 *
 */
@Log4j2
@ManagedBean(name = "visitaTecnicaBean")
@SessionScoped
public class VisitaTecnicaBean implements Serializable {

    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    @EJB
    private CmtVisitaTecnicaMglFacadeLocal vtMglFacadeLocal;
    @EJB
    private ParametrosCallesFacadeLocal parametrosCallesFacadeLocal;
    @EJB
    private CmtHorarioRestriccionMglFacadeLocal horarioRestriccionMglFacadeLocal;
    @EJB
    private CmtCompaniaMglFacadeLocal companiaFacadeLocal;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cuentaMatrizFacadeLocal;
    @EJB
    private CmtTipoOtMglFacadeLocal cmtTipoOtMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private CmtSubEdificioMglFacadeLocal cmtSubEdificioMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private CmtVisitaTecnicaMglFacadeLocal visitaTecnicaMglFacadeLocal;
    @EJB
    private CmtArchivosVtMglFacadeLocal cmtArchivosVtMglFacadeLocal;
    @EJB
    private CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMglFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    @EJB
    private CmtOrdenTrabajoMglFacadeLocal ordenTrabajoMglFacadeLocal;


    private static final String PAHT = "/view/MGL/CM/tabsVt/";
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private String selectedTab = "CUENTA_MATRIZ";
    private CmtVisitaTecnicaMgl vt;
    private CmtVisitaTecnicaMgl vtAco;
    private CmtVisitaTecnicaMgl vtCostos;
    private CmtOrdenTrabajoMgl ot;
    private CmtSubEdificiosVt subEdificiosVt;
    private CmtBasicaMgl segmento;
    private CmtBasicaMgl actividad;
    private CmtBasicaMgl tipoVisitaTecnica;
    private CmtBasicaMgl estratoVt;
    private CmtHhppVtMgl tecnologiaHabilitada;
    private String opcionNivel5;
    private List<CmtBasicaMgl> unidadSubEdificioList;
    private List<CmtBasicaMgl> alimentacionElectricaList;
    private List<CmtBasicaMgl> canalizacionInternaList;
    private List<CmtBasicaMgl> manejoAscensoresList;
    private List<CmtBasicaMgl> segmentoList;
    private List<CmtBasicaMgl> tipoActividadList;
    private List<CmtBasicaMgl> tipoConfDistribucionList;
    private List<CmtBasicaMgl> tipoDistribucionList;
    private List<CmtBasicaMgl> tipoPuntoInicialAcometidaList;
    private List<CmtBasicaMgl> tipoVtList;
    private List<CmtBasicaMgl> estratoList;
    private List<CmtSubEdificiosVt> subEdificiosVtList;
    private List<CmtHhppVtMgl> listHhppBySubEdificio;
    private List<CmtCompaniaMgl> companiaAdminList;
    private List<CmtBasicaMgl> tipoEdificioList;
    private String formTabSeleccionado = "";
    private boolean internet = false;
    private boolean telefonia = false;
    private boolean television = false;
    private boolean apartamento = false;
    private boolean casa = false;
    private boolean oficina = false;
    private boolean local = false;
    private boolean nuevaAgenda = false;
    private OtMglBean otMglBean;
    private DrDireccion drDireccion;
    private ValidadorDireccionBean validadorDireccionBean;
    private String direccion;
    private boolean direccionValidada = false;
    private ResponseValidarDireccionDto responseValidarDireccionDto;
    private List<String> barrioslist;
    private String selectedBarrio;
    private String serverHost;
    private boolean hayErroresAcometida = true;
    private boolean hayErroresVt = true;
    private boolean hayErroresAut = true;
    private boolean hayErrorSubEdi = true;
    private boolean hayErrorHhpp = true;
    private boolean hayErrorItem = true;
    private boolean deshabilitaFormVT = false;
    private CmtSubEdificiosVt subEdiModDir;
    private List<ParametrosCalles> dirNivel5List;
    private String usuarioVT = null;
    private int perfilVt = 0;
    private String cedulaUsuarioVT = null;
    private List<CmtHorarioRestriccionMgl> horarioList;
    private BigDecimal subTipoTrabajoSelected;
    private List<CmtBasicaMgl> listTablaBasicaTecnologias;
    private List<CmtTipoOtMgl> listTipoTrabajoOt;
    private BigDecimal tipoTrabajoOtSelected;
    private String intPisoTorreSelect;
    private SecurityLogin securityLogin;
    private CmtBasicaMgl tecnologia;
    private CmtBasicaMgl tipoTrabajo;
    private CmtBasicaMgl tipoEdificio;
    private CmtTipoOtMgl subTipoTrabajo;
    private List<CmtBasicaMgl> listTipoOrdenTrabajo;
    private List<CmtTipoOtMgl> subTipoOrdenTrabajo;
    private BigDecimal periodoRecuperacion;
    private String administrador;
    private String directorIngenieroObra;
    private CmtHhppVtMgl hhppSubedificio;
    private HhppVtCasasDto hhppVtCasas;
    private List<HhppVtCasasDto> listhhppVtCasas;
    private UsuariosServicesDTO usuarioLogin;
    private TecnologiaHabilitadaVtBean tecnologiaHabilitadaVtBean;
    private SubEdificiosVtBean subEdificiosVtBean;
    private BigDecimal idCentroPoblado;
    private boolean clickValidar = false;
    private boolean otroBarrio = false;
    private boolean codVTOtro = false;
    private UploadedFile uploadedFile;
    private List<CmtArchivosVtMgl> lstArchivosVtMgls;
    private boolean activacionUCM;
    private boolean validarFlujoOt = false ;

    public VisitaTecnicaBean() {
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
            cedulaUsuarioVT = securityLogin.getIdUser();    
        } catch (IOException e) {
            String msn2 = "Error al cargar Bean de Visita Tecnica:....";
            FacesUtil.mostrarMensajeError(msn2 + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al VisitaTecnicaBean. " + e.getMessage(), e, LOGGER);
        }
    }

    public void initializeBean()  {
        hayErroresAcometida = true;
        hayErroresVt = true;
        hayErroresAut = true;
        hayErrorSubEdi = true;
               
    }

    public String cambiarTab(String sSeleccionado)  {

        ConstantsCM.TABS_VT Seleccionado = ConstantsCM.TABS_VT.valueOf(sSeleccionado);
        try {
            if (otMglBean != null && otMglBean.getOrdenTrabajo() != null) {
                deshabilitaFormVT = ordenTrabajoMglFacadeLocal.
                        validaProcesoOt(otMglBean.getOrdenTrabajo(),
                                Constant.PARAMETRO_VALIDACION_OT_HABILITA_FORMULARIO_VT);
                if (!deshabilitaFormVT) {
                    deshabilitaFormVT = true;
                } else {
                    deshabilitaFormVT = false;
                }

            }
            switch (Seleccionado) {          
                case CUENTA_MATRIZ:          
                    formTabSeleccionado = "cuenta_matriz";
                    selectedTab = "CUENTA_MATRIZ";
                    break;
                case CONSTRUCTORA:
                    formTabSeleccionado = "constructora";
                    selectedTab = "CONSTRUCTORA";
                    break;
                case ACOMETIDA:
                    formTabSeleccionado = "acometida";
                    selectedTab = "ACOMETIDA";
                    break;
                case AUTORIZACION:
                    formTabSeleccionado = "modeloFinanciero";
                    selectedTab = "AUTORIZACION";
                    cargarListas();
                    activacionUCM = activaDesactivaUCM();
                    session.setAttribute("activaUCM", activacionUCM);
                    break;
                case MULTIEDIFICIO:
                    formTabSeleccionado = "multiedificio";
                    selectedTab = "MULTIEDIFICIO";
                    break;
                case HHPP:
                    formTabSeleccionado = "hhpp";
                    selectedTab = "HHPP";
                    break;
                case MANO_OBRA:
                    formTabSeleccionado = "item";
                    selectedTab = "MANO_OBRA";
                    break;
                case MATERIALES:
                    formTabSeleccionado = "item";
                    selectedTab = "MATERIALES";
                    break;
                case COSTOS:
                    formTabSeleccionado = "costos";
                    selectedTab = "COSTOS";
                    cargarListas();
                    activacionUCM = activaDesactivaUCM();
                    session.setAttribute("activaUCM", activacionUCM);
                    break;
                case PLANO:
                    formTabSeleccionado = "plano";
                    selectedTab = "PLANO";
                    break;
                case HORARIO:
                    formTabSeleccionado = "horario";
                    selectedTab = "HORARIO";
                    break;
                case MTDISENO:
                    formTabSeleccionado = "item";
                    selectedTab = "MTDISENO";
                    break;
                case MODISENO:
                    formTabSeleccionado = "item";
                    selectedTab = "MODISENO";
                    break;
                default:
                    formTabSeleccionado = "cuenta_matriz";
                    selectedTab = "CUENTA_MATRIZ";
                    break;

            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: cambiarTab(). " + e.getMessage(), e, LOGGER);
        }
        return PAHT + formTabSeleccionado;
    }

    @PostConstruct
    public void init() {
        try {
            cmtSubEdificioMglFacadeLocal.setUser(usuarioVT, perfilVt);

            serverHost = Utilidades.queryParametrosConfig(co.com.telmex.catastro.services.util.Constant.HOST_REDIR_VT);
            vtMglFacadeLocal.setUser(usuarioVT, perfilVt);
            ot = new CmtOrdenTrabajoMgl();
            segmento = new CmtBasicaMgl();
            actividad = basicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_TIPO_ACTIVIDAD_CONTRUCCION);
            tipoVisitaTecnica = new CmtBasicaMgl();
            tecnologia = new CmtBasicaMgl();
            tipoTrabajo = new CmtBasicaMgl();
            subTipoTrabajo = null;
            tipoEdificio = new CmtBasicaMgl();
            administrador = "";
            directorIngenieroObra = "";

            dirNivel5List = parametrosCallesFacadeLocal.findByTipo("DIR_SUB_EDIFICIO");

            CmtTipoBasicaMgl tipoAlimentacionElectrica;
            tipoAlimentacionElectrica = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_DE_ALIMENTACION_ELECTRICA);
            alimentacionElectricaList = basicaMglFacadeLocal.findByTipoBasica(
                    tipoAlimentacionElectrica);

            CmtTipoBasicaMgl tipoCanalizacionInterna;
            tipoCanalizacionInterna = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_DE_CANALIZACION_INTERNA);
            canalizacionInternaList = basicaMglFacadeLocal.findByTipoBasica(
                    tipoCanalizacionInterna);

            CmtTipoBasicaMgl tipoManejoAscensores;
            tipoManejoAscensores = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_DE_MANEJO_ASCENSORES);
            manejoAscensoresList = basicaMglFacadeLocal.findByTipoBasica(tipoManejoAscensores);

            CmtTipoBasicaMgl tipoSegmento;
            tipoSegmento = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_DE_SEGMENTO);
            segmentoList = basicaMglFacadeLocal.findByTipoBasica(tipoSegmento);

            CmtTipoBasicaMgl tipoActividad;
            tipoActividad = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_ACTIVIDAD);
            tipoActividadList = basicaMglFacadeLocal.findByTipoBasica(tipoActividad);

            CmtTipoBasicaMgl tipoConfDistribucion;
            tipoConfDistribucion = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_CONFIGURACION_DISTRIBUCION);
            tipoConfDistribucionList = basicaMglFacadeLocal.findByTipoBasica(
                    tipoConfDistribucion);

            CmtTipoBasicaMgl tipoDistribucion;
            tipoDistribucion = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_DISTRIBUCION);
            tipoDistribucionList = basicaMglFacadeLocal.findByTipoBasica(tipoDistribucion);

            CmtTipoBasicaMgl tipoPuntoInicialAcometida;
            tipoPuntoInicialAcometida = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_PUNTO_INICIAL_ACOMETIDA);
            tipoPuntoInicialAcometidaList = basicaMglFacadeLocal.findByTipoBasica(
                    tipoPuntoInicialAcometida);

            CmtTipoBasicaMgl tipoVt;
            tipoVt = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_VISITA_TECNICA);
            tipoVtList = basicaMglFacadeLocal.findByTipoBasica(tipoVt);

            CmtTipoBasicaMgl tipoGeneralOrdenId;
            tipoGeneralOrdenId = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_GENERAL_OT);
            listTipoOrdenTrabajo = basicaMglFacadeLocal.findByTipoBasica(tipoGeneralOrdenId);


            listTipoOrdenTrabajo = basicaMglFacadeLocal.findByTipoBasica(tipoGeneralOrdenId);

            subTipoOrdenTrabajo = new ArrayList<>();

            CmtTipoBasicaMgl tipoBasicaTipoProyecto;
            tipoBasicaTipoProyecto = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
            listTablaBasicaTecnologias = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoProyecto);
            vt = vtMglFacadeLocal.findById(vt);
            selectedTab = null;
            validarEstadoFlujoOT();
        } catch (ApplicationException e) {
            LOGGER.error(e.getMessage());
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: init(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Método para validar si el usuario tiene permisos para
     * crear Costos en visita técnica OT de CCMM
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolCrear() {
        try {
            return ValidacionUtil.validarVisualizacion("TABCOSTOSOTVTCCMM",
                    ValidacionUtil.OPC_CREACION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al verificar si tiene rol de permisos de creación de costos en OT CCMM" , e);
        }

        return false;
    }

    public boolean campoConstructora() {
        boolean activar = false;
        String nomBasica = "";
        try {
            if (vt != null && vt.getOtObj() != null && vt.getOtObj().getCmObj() != null
                    && vt.getOtObj().getCmObj().getSubEdificioGeneral() != null
                    && vt.getOtObj().getCmObj().getSubEdificioGeneral().getTipoProyectoObj() != null) {
                nomBasica = vt.getOtObj().getCmObj().getSubEdificioGeneral().getTipoProyectoObj().getNombreBasica();
            }
            List<CmtBasicaMgl> nombresBasica;
            nombresBasica = cmtBasicaMglFacadeLocal.findByNombreBasica(Constant.NOMBRE_BASICA_CONSTRUCTORA);
            for (CmtBasicaMgl nombre : nombresBasica) {
                if (nombre != null && nombre.getNombreBasica() != null
                        && nombre.getNombreBasica().equals(nomBasica)) {
                    activar = true;
                }
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: campoConstructora(). " + e.getMessage(), e, LOGGER);
        }
        return activar;
    }
            
    
    public String obtenerDirAlterna() {
        try {
            String dirAltStr = "";
            if (vt != null && vt.getOtObj() != null && vt.getOtObj().getCmObj() != null
                    && vt.getOtObj().getCmObj().getDireccionAlternaList() != null
                    && !vt.getOtObj().getCmObj().getDireccionAlternaList().isEmpty()) {
                dirAltStr = vt.getOtObj().getCmObj().getDireccionAlternaList().get(0).getDireccionObj().getDirFormatoIgac();
            }
            return dirAltStr;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: obtenerDirAlterna(). " + e.getMessage(), e, LOGGER);
            return null;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: obtenerDirAlterna(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String ingresar() {
        try {
            selectedTab = "CUENTA_MATRIZ";
            this.otMglBean = (OtMglBean) JSFUtil.getSessionBean(OtMglBean.class);
            if (otMglBean.getOrdenTrabajo() != null) {
                ot = otMglBean.getOrdenTrabajo();

                if (otMglBean.tieneAgendasPendientes(ot)) {
                    this.vt = new CmtVisitaTecnicaMgl();
                    vt.setOtObj(otMglBean.getOrdenTrabajo());

                    //cargamos el tipo de actividad -Construccion por default
                    actividad = new CmtBasicaMgl();
                    actividad.setBasicaId(basicaMglFacadeLocal.findByCodigoInternoApp(
                            Constant.BASICA_TIPO_ACTIVIDAD_CONTRUCCION).getBasicaId());
                    //asignamos el tipo de segmento de la OT
                    segmento = otMglBean.getOrdenTrabajo().getSegmento();

                    //Autor: Victor Bocanegra
                    tecnologia = otMglBean.getOrdenTrabajo().getBasicaIdTecnologia();
                    tipoTrabajo = otMglBean.getOrdenTrabajo().getBasicaIdTipoTrabajo();
                    subTipoTrabajo = otMglBean.getOrdenTrabajo().getTipoOtObj();
                    tipoEdificio = vt.getOtObj().getCmObj().getSubEdificioGeneral().getTipoEdificioObj();
                    administrador = vt.getOtObj().getCmObj().getSubEdificioGeneral().getAdministrador();
                    directorIngenieroObra = vt.getOtObj().getCmObj().getSubEdificioGeneral()
                            .getDirectorIngenieroObra();
                    consultarSubTipoTrabajo();
                    //Inicializamos el cod de Vt
                    tipoVisitaTecnica = new CmtBasicaMgl();
                    tipoVisitaTecnica.setBasicaId(BigDecimal.ZERO);
                    //Cargamos el estrato por defecto
                    estratoVt = new CmtBasicaMgl();
                    estratoVt.setBasicaId(BigDecimal.ZERO);
                    //Inicializamos la compania de administracion
                    CmtCuentaMatrizMgl cuentaMatriz = cuentaMatrizFacadeLocal.findById(vt.getOtObj().getCmObj());
                    if (cuentaMatriz != null
                            && cuentaMatriz.getSubEdificioGeneral() != null) {
                        if (vt.getAdministracionObj() == null
                                && cuentaMatriz.getSubEdificioGeneral().
                                getCompaniaAdministracionObj() != null) {
                            vt.setAdministracionObj(cuentaMatriz.
                                    getSubEdificioGeneral().getCompaniaAdministracionObj());
                        } else {
                            CmtCompaniaMgl companiaMglAdmin = new CmtCompaniaMgl();
                            companiaMglAdmin.setCompaniaId(BigDecimal.ZERO);
                            vt.setAdministracionObj(companiaMglAdmin);
                        }

                        if (cuentaMatriz.getSubEdificioGeneral().getEstrato() != null) {
                            estratoVt = cuentaMatriz.getSubEdificioGeneral().getEstrato();
                        }

                    }
                    cargarListas();                  
              
                    deshabilitaFormVT = ordenTrabajoMglFacadeLocal.
                            validaProcesoOt(otMglBean.getOrdenTrabajo(),
                                    Constant.PARAMETRO_VALIDACION_OT_HABILITA_FORMULARIO_VT);
                    
                    if (!deshabilitaFormVT) {
                        deshabilitaFormVT = true;
                    } else {
                        deshabilitaFormVT = false;
                    }
                
                    return "/view/MGL/CM/tabsVt/cuenta_matriz";
                } else {
                    String msg = "La orden de trabajo No: "+ot.getIdOt()+" tiene agendas pendientes por cerrar";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                }

            }
            
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de ingresar. EX000: " + e.getMessage(), e);
            String msn = "Error Ingrensado a la creacion del Formulario VT. ";
            FacesContext.getCurrentInstance().
                    addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, e.getMessage()));
       } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: ingresar(). " + e.getMessage(), e, LOGGER);
       }
        return null;
    }
    public String ingresarAcometidaNueva() {

        activacionUCM = activaDesactivaUCM();
        session.setAttribute("activaUCM", activacionUCM);
        cargarDatos();
        return "/view/MGL/CM/tabsAcometida/costosAcometida";

    }

    public String ingresarGestionVtAco(CmtVisitaTecnicaMgl visitaTecnicaMgl)  {

        try {
            session.setAttribute("acometidaSelected", visitaTecnicaMgl);
            activacionUCM = activaDesactivaUCM();
            session.setAttribute("activaUCM", activacionUCM);
            cargarDatos();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: ingresarGestionVtAco(). " + e.getMessage(), e, LOGGER);
        }
      
        return "/view/MGL/CM/tabsAcometida/costosAcometida";

    }
      
    public void cargarDatos() {

        try {
            this.otMglBean = JSFUtil.getSessionBean(OtMglBean.class);
            if (otMglBean.getOrdenTrabajo() != null) {
                ot = otMglBean.getOrdenTrabajo();
                this.vt = new CmtVisitaTecnicaMgl();
                vt.setOtObj(otMglBean.getOrdenTrabajo());
                actividad = new CmtBasicaMgl();
                actividad.setBasicaId(basicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.BASICA_TIPO_ACTIVIDAD_CONTRUCCION).getBasicaId());
                segmento = otMglBean.getOrdenTrabajo().getSegmento();
                tecnologia = otMglBean.getOrdenTrabajo().getBasicaIdTecnologia();
                tipoTrabajo = otMglBean.getOrdenTrabajo().getBasicaIdTipoTrabajo();
                subTipoTrabajo = otMglBean.getOrdenTrabajo().getTipoOtObj();
                tipoEdificio = vt.getOtObj().getCmObj().getSubEdificioGeneral().getTipoEdificioObj();
                administrador = vt.getOtObj().getCmObj().getSubEdificioGeneral().getAdministrador();
                directorIngenieroObra = vt.getOtObj().getCmObj().getSubEdificioGeneral()
                        .getDirectorIngenieroObra();
                consultarSubTipoTrabajo();
                tipoVisitaTecnica = new CmtBasicaMgl();
                tipoVisitaTecnica.setBasicaId(BigDecimal.ZERO);
                estratoVt = new CmtBasicaMgl();
                estratoVt.setBasicaId(BigDecimal.ZERO);
                CmtCuentaMatrizMgl cuentaMatriz = cuentaMatrizFacadeLocal.findById(vt.getOtObj().getCmObj());
                if (cuentaMatriz != null
                        && cuentaMatriz.getSubEdificioGeneral() != null) {
                    if (vt.getAdministracionObj() == null
                            && cuentaMatriz.getSubEdificioGeneral().
                            getCompaniaAdministracionObj() != null) {
                        vt.setAdministracionObj(cuentaMatriz.
                                getSubEdificioGeneral().getCompaniaAdministracionObj());
                    } else {
                        CmtCompaniaMgl companiaMglAdmin = new CmtCompaniaMgl();
                        companiaMglAdmin.setCompaniaId(BigDecimal.ZERO);
                        vt.setAdministracionObj(companiaMglAdmin);
                    }

                    if (cuentaMatriz.getSubEdificioGeneral().getEstrato() != null) {
                        estratoVt = cuentaMatriz.getSubEdificioGeneral().getEstrato();
                    }

                }
                cargarListas();
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al  momento de cargar los datos. EX000: " + e.getMessage(), e);
            String msn = "Error Ingrensado a la Gestion de la Acometida. ";
            FacesContext.getCurrentInstance().
                    addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, e.getMessage()));
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: cargarDatos(). " + e.getMessage(), e, LOGGER);
        }
    }

    public String ingresarGestion(CmtVisitaTecnicaMgl visitaTecnicaMgl)  {
        try {
            selectedTab = "CUENTA_MATRIZ";
            if (visitaTecnicaMgl != null && visitaTecnicaMgl.getIdVt() != null) {
                OtMglBean OtSessionBean
                        = JSFUtil.getSessionBean(OtMglBean.class);
                OtSessionBean.getEstadoOtId();
                if (OtSessionBean != null && OtSessionBean.getOrdenTrabajo() != null
                        && OtSessionBean.getOrdenTrabajo().getIdOt() != null) {

                    deshabilitaFormVT = ordenTrabajoMglFacadeLocal.
                            validaProcesoOt(OtSessionBean.getOrdenTrabajo(),
                                    Constant.PARAMETRO_VALIDACION_OT_HABILITA_FORMULARIO_VT);
                    
                    if (!deshabilitaFormVT) {
                        deshabilitaFormVT = true;
                    } else {
                        deshabilitaFormVT = false;
                    }
                }

                vt = vtMglFacadeLocal.findById(visitaTecnicaMgl);
                cargarListas();
                horarioList = horarioRestriccionMglFacadeLocal.findByVt(vt);
                //cargamos el tipo de actividad -Construccion por default
                actividad = vt.getTipoActividadObj();
                //asignamos el tipo de segmento de la OT
                segmento = vt.getSegmentoObj();

                tecnologia = vt.getOtObj().getBasicaIdTecnologia();
                tipoTrabajo = vt.getOtObj().getBasicaIdTipoTrabajo();
                subTipoTrabajo = vt.getOtObj().getTipoOtObj();
                tipoEdificio = vt.getOtObj().getCmObj().getSubEdificioGeneral().getTipoEdificioObj();
                administrador = vt.getOtObj().getCmObj().getSubEdificioGeneral().getAdministrador();
                directorIngenieroObra  = vt.getOtObj().getCmObj().getSubEdificioGeneral()
                        .getDirectorIngenieroObra();

                consultarSubTipoTrabajo();

                //Inicializamos el cod de Vt
                if (vt.getTipoVtObj() != null
                        && vt.getTipoVtObj().getBasicaId().compareTo(new BigDecimal("-1")) != 0) {
                    tipoVisitaTecnica = vt.getTipoVtObj();
                    vt.setOtros("");
                } else {
                    tipoVisitaTecnica = new CmtBasicaMgl();
                    tipoVisitaTecnica.setBasicaId(new BigDecimal("-1"));
                }

                if (vt.getEstratoObj() != null) {
                    estratoVt = vt.getEstratoObj();
                } else {
                    estratoVt = new CmtBasicaMgl();
                    estratoVt.setBasicaId(BigDecimal.ZERO);
                }

            }
            return "/view/MGL/CM/tabsVt/cuenta_matriz";
        } catch (ApplicationException e) {
            String msn = "Error Ingrensado a la Gestion del Formulario VT. ";
            FacesUtil.mostrarMensajeError(msn+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: ingresarGestion(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String CodVtListChange() {
        if (tipoVisitaTecnica != null) {
            if (tipoVisitaTecnica.getBasicaId().compareTo(new BigDecimal("-1")) != 0) {
                vt.setOtros("");
                codVTOtro = true;
            } else {

                codVTOtro = false;
            }
        }
        return null;
    }

    public void cargarListas()  {
        try {
            //cargamos los tipos de actividad
            CmtTipoBasicaMgl tipoActividad;
            tipoActividad = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_ACTIVIDAD);
            tipoActividadList = basicaMglFacadeLocal.findByTipoBasica(tipoActividad);
            //cargamos el tipo de segmento
            CmtTipoBasicaMgl tipoSegmento;
            tipoSegmento = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_DE_SEGMENTO);
            segmentoList = basicaMglFacadeLocal.findByTipoBasica(tipoSegmento);
            //Cargamos los Codigos de la visita Tecnica
            CmtTipoBasicaMgl tipoVt;
            tipoVt = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_VISITA_TECNICA);
            tipoVtList = basicaMglFacadeLocal.findByTipoBasica(tipoVt);

            //cargamos las companias de administracion
            //Inicializamos la compania de administracion
            CmtCuentaMatrizMgl cuentaMatriz = cuentaMatrizFacadeLocal.findById(vt.getOtObj().getCmObj());
            FiltroConsultaCompaniasDto filtroConsulta = new FiltroConsultaCompaniasDto();
            filtroConsulta.setTipoCompania(Constant.TIPO_COMPANIA_ID_ADMINISTRACION);
            filtroConsulta.setEstado("Y");
            filtroConsulta.setMunicipio(cuentaMatriz.getMunicipio().getGpoId());

            companiaAdminList = companiaFacadeLocal.findByfiltro(filtroConsulta,false);

            //Cargamos la Lista de estratos
            CmtTipoBasicaMgl tipoBasicaEstrato;
            tipoBasicaEstrato = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_ESTRATO);
            estratoList = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaEstrato);

            //Cargamos la Lista de tipo de edificios
            CmtTipoBasicaMgl tEdificio;
            tEdificio = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_CUENTA_MATRIZ);
            tipoEdificioList = basicaMglFacadeLocal.findByTipoBasica(tEdificio);

            apartamento = vt.getUnidadApartamento() != null
                    && vt.getUnidadApartamento().equalsIgnoreCase("Y");
            casa = vt.getUnidadCasa() != null
                    && vt.getUnidadCasa().equalsIgnoreCase("Y");
            local = vt.getUnidadLocal() != null
                    && vt.getUnidadLocal().equalsIgnoreCase("Y");
            oficina = vt.getUnidadOficina() != null
                    && vt.getUnidadOficina().equalsIgnoreCase("Y");

            internet = vt.getCompromisoInternet() != null
                    && vt.getCompromisoInternet().equalsIgnoreCase("Y");
            telefonia = vt.getCompromisoTelefonia() != null
                    && vt.getCompromisoTelefonia().equalsIgnoreCase("Y");
            television = vt.getCompromisoTelevision() != null
                    && vt.getCompromisoTelevision().equalsIgnoreCase("Y");

            //pestania acometida
            if (vt.getTipoPtoIniAcometObj() == null) {
                CmtBasicaMgl tipo = new CmtBasicaMgl();
                tipo.setBasicaId(BigDecimal.ZERO);
                vt.setTipoPtoIniAcometObj(tipo);
            }

            if (vt.getCanalizacionIntObj() == null) {
                CmtBasicaMgl tipo = new CmtBasicaMgl();
                tipo.setBasicaId(BigDecimal.ZERO);
                vt.setCanalizacionIntObj(tipo);
            }

            if (vt.getTipoConfDistribObj() == null) {
                CmtBasicaMgl tipo = new CmtBasicaMgl();
                tipo.setBasicaId(BigDecimal.ZERO);
                vt.setTipoConfDistribObj(tipo);
            }

            if (vt.getAlimentElectObj() == null) {
                CmtBasicaMgl tipo = new CmtBasicaMgl();
                tipo.setBasicaId(BigDecimal.ZERO);
                vt.setAlimentElectObj(tipo);
            }

            if (vt.getTipoDistribucionObj() == null) {
                CmtBasicaMgl tipo = new CmtBasicaMgl();
                tipo.setBasicaId(BigDecimal.ZERO);
                vt.setTipoDistribucionObj(tipo);
            }
            if (vt.getManejoAscensoresObj() == null) {
                CmtBasicaMgl tipo = new CmtBasicaMgl();
                tipo.setBasicaId(BigDecimal.ZERO);
                vt.setManejoAscensoresObj(tipo);
            }
            if (vt.getAdministracionObj() == null) {
                CmtCompaniaMgl companiaAdminMgl = new CmtCompaniaMgl();
                companiaAdminMgl.setCompaniaId(BigDecimal.ZERO);
                vt.setAdministracionObj(companiaAdminMgl);
            }
            if (selectedTab != null) {
                if (selectedTab.equalsIgnoreCase("COSTOS")) {
                    lstArchivosVtMgls = cmtArchivosVtMglFacadeLocal.findAllByVtxOrigen(vt, Constant.ORIGEN_CARGA_ARCHIVO_COSTOS);
                } else {
                    lstArchivosVtMgls = cmtArchivosVtMglFacadeLocal.findAllByVtxOrigen(vt, Constant.ORIGEN_CARGA_ARCHIVO_FINANCIERA);
                }
            }
        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean:  cargarListas(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean:  cargarListas(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void crearVt() {
        try {
            if (selectedTab.equals("CUENTA_MATRIZ")) {


            }
            if (estratoVt != null
                    && estratoVt.getBasicaId().compareTo(BigDecimal.ZERO) != 0) {
                vt.setEstratoObj(estratoVt);
            } else {
                vt.setEstratoObj(null);
            }
            vt.setSegmentoObj(segmento);
            vt.setTipoActividadObj(actividad);
            if (tipoVisitaTecnica != null) {
                if (tipoVisitaTecnica != null) {
                     if (tipoVisitaTecnica.getBasicaId().compareTo(new BigDecimal("-1")) == 0 && !vt.getOtros().isEmpty() ) {
                        String msn2 = "Ud ya selecciono  Cod. VT";
                        FacesContext.getCurrentInstance()
                                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                        return;
                    } else {
                        vt.setTipoVtObj(tipoVisitaTecnica);
                    }

                } else {
                    if (selectedTab.equals("CUENTA_MATRIZ") && tipoVisitaTecnica == null) {
                        String msn2 = "EL campo Cod. Vt es necesario";
                        FacesContext.getCurrentInstance()
                                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                        return;
                    }
                }
            }
            vt.setEdificioManzana("E");
            vt.setMultiEdificio("U");
            vt.setVersionVt(1);
            vt.setEstadoRegistro(1);

            if (selectedTab.equals("CUENTA_MATRIZ") && !casa && !apartamento && !oficina && !local) {
                String msn2 = "Se debe seleccionar al menos un tipo de unidad.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            } else {
                if (apartamento) {
                    vt.setUnidadApartamento("Y");
                } else {
                    vt.setUnidadApartamento("N");
                }
                if (casa) {
                    vt.setUnidadCasa("Y");
                } else {
                    vt.setUnidadCasa("N");
                }
                if (local) {
                    vt.setUnidadLocal("Y");
                } else {
                    vt.setUnidadLocal("N");
                }
                if (oficina) {
                    vt.setUnidadOficina("Y");
                } else {
                    vt.setUnidadOficina("N");
                }

                //Autor: Victor Bocanegra
                vt.setTipoPtoIniAcometObj(new CmtBasicaMgl());
                vt.setEstadoVisitaTecnica(BigDecimal.ONE);
                vtMglFacadeLocal.setUser(usuarioVT, perfilVt);
                vt = vtMglFacadeLocal.createCm(vt);
                cargarListas();
                hayErroresVt = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, ConstantsCM.MSN_PROCESO_EXITOSO, ""));

                if (vt != null && vt.getIdVt() != BigDecimal.ZERO) {
                    vtMglFacadeLocal.cambiarEstadoVisitasTecnicasAnteriores(vt);
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: crearVt(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: crearVt(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void editarVt() {
        try {

            this.otMglBean = JSFUtil.getSessionBean(OtMglBean.class);
           
            if(this.otMglBean.getOrdenTrabajo() != null){
                ot=this.otMglBean.getOrdenTrabajo();
            }
            if (otMglBean.tieneAgendasPendientes(ot)) {
                /* ajuste para mantener valores de la pestania costos */
                vtCostos = vtMglFacadeLocal.findById(vt);
                vt.setSegmentoObj(segmento);
                vt.setTipoActividadObj(actividad);
                if (tipoVisitaTecnica != null) {

                    if (tipoVisitaTecnica.getBasicaId().compareTo(new BigDecimal("-1")) != 0 && vt.getOtros() != null && !vt.getOtros().isEmpty()) {
                        String msn2 = "EL Otro Cod. VT es necesario";
                        FacesContext.getCurrentInstance()
                                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                        return;
                    } else {
                        vt.setTipoVtObj(tipoVisitaTecnica);
                    }

                } else {
                    if (selectedTab.equals("CUENTA_MATRIZ") && tipoVisitaTecnica == null) {
                        String msn2 = "EL campo Cod. Vt es necesario";
                        FacesContext.getCurrentInstance()
                                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                        return;
                    }
                }

                if (selectedTab.equals("CUENTA_MATRIZ") && !casa && !apartamento && !oficina && !local) {
                    String msn2 = "Se debe seleccionar al menos un tipo de unidad.";
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                    return;
                }
                this.vt = getVt();

                if (selectedTab.equals("CUENTA_MATRIZ")) {
                    vt.setEstratoObj(estratoVt);
                    vt.setUnidadApartamento(apartamento ? "Y" : "N");
                    vt.setUnidadCasa(casa ? "Y" : "N");
                    vt.setUnidadLocal(local ? "Y" : "N");
                    vt.setUnidadOficina(oficina ? "Y" : "N");

                }

                if (selectedTab.equals("AUTORIZACION")) {
                    vt.setCompromisoInternet(internet ? "Y" : "N");
                    vt.setCompromisoTelefonia(telefonia ? "Y" : "N");
                    vt.setCompromisoTelevision(television ? "Y" : "N");
                    cargarArchivoVtAUCM(Constant.ORIGEN_CARGA_ARCHIVO_FINANCIERA);
                }

                vt.setCtoManoObra(vtCostos.getCtoManoObra());
                vt.setCtoMaterialesRed(vtCostos.getCtoMaterialesRed());
                vt.setCostoMaterialesDiseno(vtCostos.getCostoMaterialesDiseno());
                vt.setCostoManoObraDiseno(vtCostos.getCostoManoObraDiseno());
                vt = vtMglFacadeLocal.update(vt);
                try {
                    CmtSubEdificioMgl subTemp = cmtSubEdificioMglFacadeLocal.findById(vt.getOtObj().getCmObj().getSubEdificioGeneral().getSubEdificioId());
                    tipoEdificio = basicaMglFacadeLocal.findById(tipoEdificio.getBasicaId());
                    subTemp.setTipoEdificioObj(tipoEdificio);
                    subTemp.setAdministrador(administrador);
                    subTemp.setDirectorIngenieroObra(directorIngenieroObra);
                    // Modificacion 29/98/2017 solicitado por Usuario PISO_TORE pasa de 'DRSE_UNI' a null en tabla
                    cmtSubEdificioMglFacadeLocal.update(subTemp);
                } catch (ApplicationException e) {
                    LOGGER.error(e.getMessage());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            ConstantsCM.MSN_ERROR_PROCESO + e.getMessage(), e.getMessage()));
                }

                cargarListas();
                if (selectedTab.equals("ACOMETIDA")) {
                    hayErroresAcometida = false;
                }
                if (selectedTab.equals("AUTORIZACION")) {
                    hayErroresAut = false;
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        ConstantsCM.MSN_PROCESO_EXITOSO, ""));

            } else {
                String msg = "La orden de trabajo No: "+ot.getIdOt()+" tiene agendas pendientes por cerrar";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        msg, ""));
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: editarVt(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: editarVt() " + e.getMessage(), e, LOGGER);
        }
    }
    
    public String irPopUpDireccion(CmtSubEdificiosVt subEdi) {
        // multi
        try{
        subEdiModDir = subEdi;
        subEdiModDir.setClickValidarDir(true);
        direccion = subEdi.getDireccionSubEdificio();
        drDireccion = new DrDireccion();
        idCentroPoblado = vt.getOtObj().getCmObj().getCentroPoblado().getGpoId();
        try {
           usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        validadorDireccionBean = (ValidadorDireccionBean) JSFUtil.getSessionBean(ValidadorDireccionBean.class);
        validadorDireccionBean.setUsuarioLogin(usuarioLogin);
        validadorDireccionBean.setDireccionLabel("");
        validadorDireccionBean.setIdCentroPoblado(idCentroPoblado);
        validadorDireccionBean.setTecnologia(vt.getOtObj().getBasicaIdTecnologia());
        //JDHT
        String barrioCm = "";
        if(vt.getOtObj() != null && vt.getOtObj().getCmObj() != null 
                && vt.getOtObj().getCmObj().getDireccionPrincipal() != null 
                && vt.getOtObj().getCmObj().getDireccionPrincipal().getBarrio() != null
                && !vt.getOtObj().getCmObj().getDireccionPrincipal().getBarrio().isEmpty()){
            barrioCm = vt.getOtObj().getCmObj().getDireccionPrincipal().getBarrio();
        }
        
        validadorDireccionBean.validarDireccion(drDireccion, direccion, vt.getOtObj().getCmObj().getCentroPoblado(), barrioCm, this, VisitaTecnicaBean.class, Constant.TIPO_VALIDACION_DIR_CM_HHPP,null);
        
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: irPopUpDireccion(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String irPopUpDireccionHHpp(CmtHhppVtMgl cmtHhppVtMgl)  {
        try {
            validadorDireccionBean = JSFUtil.getSessionBean(ValidadorDireccionBean.class);
            CmtSubEdificiosVt subEdi = new CmtSubEdificiosVt();
            subEdi.setDireccionSubEdificio(cmtHhppVtMgl.getDireccionValidada());
            cmtHhppVtMgl.setDireccionValidada("");
            validadorDireccionBean.setDireccionLabel("");
            irPopUpDireccion(subEdi);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: irPopUpDireccionHHpp(). " + e.getMessage(), e, LOGGER);
        }
  
        return null;
    }
    




    public void recargar(ResponseValidarDireccionDto direccionDto) {
        try {
            //JDHT
            if(direccionDto != null && direccionDto.isValidacionExitosa()){
                 direccionValidada = true;
            }else{
                direccionValidada = false;
            }
           
            responseValidarDireccionDto = direccionDto;
            drDireccion = responseValidarDireccionDto.getDrDireccion().clone();
            subEdiModDir.setDireccionSubEdificio(responseValidarDireccionDto.getDireccion());
            subEdiModDir.setCalleRr(responseValidarDireccionDto.getDireccionRREntity().getCalle());
            subEdiModDir.setUnidadRr(responseValidarDireccionDto.getDireccionRREntity().getNumeroUnidad());
            subEdiModDir.setCodigoRr(responseValidarDireccionDto.getDireccionRREntity().getId());
            barrioslist = responseValidarDireccionDto.getBarrios();
            selectedBarrio = responseValidarDireccionDto.getDrDireccion().getBarrio();
            subEdiModDir.mapearCamposDrDireccion(drDireccion);
            //JDHT
            //si la direccion es validada correcctamente que busque HHPP
            if (direccionValidada) {
                cargarHHppVt(subEdiModDir);
            }
            setResponseValidarDireccionDto(null);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: recargar(). " + e.getMessage(), e, LOGGER);
        }
    }
    
    public void cargarHHppVt(CmtSubEdificiosVt cmtSubEdificiosVt) {
        try {
            tecnologiaHabilitadaVtBean = (TecnologiaHabilitadaVtBean) JSFUtil.getSessionBean(TecnologiaHabilitadaVtBean.class);
            subEdificiosVtBean = (SubEdificiosVtBean) JSFUtil.getSessionBean(SubEdificiosVtBean.class);
            if (selectedTab.toString().contains("MULTIEDIFICIO")) {
                List<CmtDireccionDetalladaMgl> cmtDireccionDetalladaMgl = cmtDireccionDetalleMglFacadeLocal.findDireccionDetalladaByDrDireccion(drDireccion, cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getCentroPoblado().getGpoId());
                if (cmtDireccionDetalladaMgl.isEmpty()) {
                    List<CmtSubEdificiosVt> subEdifLista;
                    subEdifLista = subEdificiosVtBean.getSubEdificioVtList();
                    for (CmtSubEdificiosVt subEdificioVt : subEdifLista) {
                        if (subEdificioVt.getListTecnologiaHabilitada().isEmpty() && subEdiModDir.getIdEdificioVt() == subEdificioVt.getIdEdificioVt()) {
                            subEdificioVt.setDireccionSubEdificio(subEdiModDir.getDireccionSubEdificio());
                        }
                    }
                    subEdificiosVtBean.setSubEdificioVtList(subEdifLista);
                } else {
                    CmtDireccionDetalladaMgl dir = null;
                    for (CmtDireccionDetalladaMgl dirExistente : cmtDireccionDetalladaMgl) {
                        if (dirExistente.getSubDireccion() == null) {
                            dir = dirExistente;
                        }
                    }
                    String msn2 = "La direccion ya existe en el Repositorio : " + dir.getDireccionTexto();
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn2, null));
                    return;
                }

            } else {
                /* Lista para cargar el form de cargue de hhpp para casas con su propia direccion */
                List<CmtHhppVtMgl> listcmtHhppVtCargue;
                listcmtHhppVtCargue = tecnologiaHabilitadaVtBean.getListHhppCargue();
                for (CmtHhppVtMgl cmtHhppVtMgl : listcmtHhppVtCargue) {
                    if (cmtHhppVtMgl.getDireccionValidada() != null || !cmtHhppVtMgl.getDireccionValidada().contains("")) {
                        cmtHhppVtMgl.setDireccionValidada(subEdiModDir.getDireccionSubEdificio());
                    } else {
                        cmtHhppVtMgl.setDireccionValidada("");
                    }

                }

                if (tecnologiaHabilitadaVtBean.getSelectedSubEdificioVt().getTipoNivel1()
                        .getIdentificadorInternoApp().equals(Constant.BASICA_CAMPUS_BATALLON)) {
                    tecnologiaHabilitadaVtBean.setHabilitarColums(true);
                    tecnologiaHabilitadaVtBean.setQuitarComplemeto(false);
                } else {
                    tecnologiaHabilitadaVtBean.setHabilitarColums(true);
                    tecnologiaHabilitadaVtBean.setQuitarComplemeto(true);
                }
                tecnologiaHabilitadaVtBean.setListHhppCargue(listcmtHhppVtCargue);

            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: cargarHHppVt(). " + e.getMessage(), e, LOGGER);
        }
    }

    public boolean validarVT() {
        if (vt.getObservacionesAdmon().isEmpty() || vt.getObservacionesAdmon() == null) {
            hayErroresVt = true;
        } else if (vt.getOrigenVt().isEmpty() || vt.getOrigenVt() == null) {
            hayErroresVt = true;
        } else {
            hayErroresVt = false;
        }
        return hayErroresVt;
    }

    public String ingresarVt(String idVt) {
        try {
            vt = vtMglFacadeLocal.findById(vt);
            if (vt == null) {
                String msn = "No fue posible encontrar la VT";
                LOGGER.error(msn);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
            return cambiarTab("CUENTA_MATRIZ");
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de ingresar VT. EX000: " + e.getMessage(), e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error al momento de ingresar VT. EX000: " + e.getMessage(), e);
            return null;
        }
    }

    public String ingresarGestionOt() {
        OtMglBean otMglBeanInstance = JSFUtil.getSessionBean(OtMglBean.class);

        if (Objects.isNull(otMglBeanInstance)) {
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, "Error al recuperar la información de la OT");
            return StringUtils.EMPTY;
        }

        return otMglBeanInstance.ingresarGestion(vt.getOtObj());
    }

    public String ingresarGestionCm() {
        try {
            CuentaMatrizBean cuentaMatrizBean = (CuentaMatrizBean) JSFUtil.getSessionBean(CuentaMatrizBean.class);
            cuentaMatrizBean.setCuentaMatriz(vt.getOtObj().getCmObj());
            ConsultaCuentasMatricesBean consultaCuentasMatricesBean = (ConsultaCuentasMatricesBean) JSFUtil.getSessionBean(ConsultaCuentasMatricesBean.class);
            ot = ordenTrabajoMglFacadeLocal.bloquearDesbloquearOrden(vt.getOtObj(), false);
            consultaCuentasMatricesBean.goCuentaMatriz();
            
        } catch (ApplicationException e) {
            String msg = "Se present\u00f3 un error al procesar"
                    + " la petici\u00f3sn. Por favor intente m\u00e1s tarde";
            
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    e.getCause() != null ? e.getCause().getMessage() : 
                    e.getMessage() != null ? e.getMessage() : "", 
                    msg));
            LOGGER.error(msg, e);
        }
        return null;
    }

    public String guardarHorarioRestriccion()  {
        try {
            horarioRestriccionMglFacadeLocal.setUser(usuarioVT, perfilVt);
            if (session.getAttribute("ComponenteHorario") != null) {
                horarioList = (List<CmtHorarioRestriccionMgl>) session.getAttribute("ComponenteHorario");
                session.removeAttribute("ComponenteHorario");
            }
            if (vt != null && vt.getIdVt() != null) {
                if (horarioList != null && !horarioList.isEmpty()) {
                    if (!horarioRestriccionMglFacadeLocal.deleteByVt(vt)) {
                        String msn = "Ocurrio un error al eliminar los registros de horario ";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        return null;
                    }
                    try {
                        for (CmtHorarioRestriccionMgl hr : horarioList) {
                            hr.setVt(vt);
                            hr.setHorRestriccionId(null);
                            hr.setEstadoRegistro(ConstantsCM.REGISTRO_ENUSO_ID);
                            horarioRestriccionMglFacadeLocal.create(hr);
                        }
                        horarioList = horarioRestriccionMglFacadeLocal.findByVt(vt);
                        session.removeAttribute("ComponenteHorario");
                        String msn = "Horario creados con \u00e9xito";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                    } catch (ApplicationException e) {
                        String msn = "Error insertando horario";
                        FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
                    } catch (Exception e) {
                        FacesUtil.mostrarMensajeError("Error al VisitaTecnicaBean. " + e.getMessage(), e, LOGGER);
                    }
                } else {
                    String msn = "No puede agregar horario vac\u00edo.";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                }
            } else {
                String msn = "No puede agregar horario si la solicitud no ha sido creada.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }

            return cambiarTab("HORARIO");
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: guardarHorarioRestriccion(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void consultarSubTipoTrabajo() {
        try {
            listTipoTrabajoOt = cmtTipoOtMglFacadeLocal.findByBasicaId(tipoTrabajo);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: consultarSubTipoTrabajo(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: consultarSubTipoTrabajo(). " + e.getMessage(), e, LOGGER);
        }
    }
      public String mostrarOtrobarrio() {
        otroBarrio = !otroBarrio;
        return null;
    }
      
        public void cargarArchivoVtAUCM(String origen) {

        String usuario = usuarioVT;
            try {
                if (uploadedFile != null && uploadedFile.getFileName() != null) {
                    try {
                        boolean responseVt = visitaTecnicaMglFacadeLocal.
                                cargarArchivoVTxUCM(vt, uploadedFile, usuario, perfilVt, origen);

                        if (responseVt) {
                            String msg = "Archivo cargado correctamente";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            msg, ""));
                        } else {
                            String msg = "Ocurrio un error al guardar el archivo";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            msg, ""));

                        }
                    } catch (ApplicationException | FileNotFoundException | MalformedURLException e) {
                        String msg = "Error al crear la solictud" + e.getMessage();
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        msg, ""));
                        LOGGER.error(msg);
                    }

                }
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: cargarArchivoVtAUCM. " + e.getMessage(), e, LOGGER);
            }


    }
      public void eliminarArchivo(CmtArchivosVtMgl archivosVtMgl) {
        try {
            boolean elimina = cmtArchivosVtMglFacadeLocal.delete(archivosVtMgl, usuarioVT, perfilVt);
            if (elimina) {
                String msg = "Se elimino el registro " + archivosVtMgl.getIdArchivosVt() + " de la base de datos";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        msg, ""));
                LOGGER.error(msg);
                cargarListas();
            } else {
                String msg = "Ocurrio un problema al tratar de eliminar el registro ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        msg, ""));
                LOGGER.error(msg);
            }
          } catch (ApplicationException e) {
               FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: eliminarArchivo(). " + e.getMessage(), e, LOGGER);
          } catch (Exception e) {
              FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: eliminarArchivo(). " + e.getMessage(), e, LOGGER);
          }

    }
    

    public boolean validarTabCM() {
        return validarPermisos(TAB_CM, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarTabConstructora() {
        return validarPermisos(TAB_CONSTRUCTORA, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarTabAcometida() {
        return validarPermisos(TAB_ACOMETIDA, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarTabMultiedificio() {
        return validarPermisos(TAB_MULTIEDIFICIO, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarTabHHPP() {
        return validarPermisos(TAB_HHPP, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarTabMOInfraestructura() {
        return validarPermisos(TAB_MO_INFRA, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarTabMTInfraestructura() {
        return validarPermisos(TAB_MT_INFRA, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarTabMODiseno() {
        return validarPermisos(TAB_MO_DISENO, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarTabMTDiseno() {
        return validarPermisos(TAB_MT_DISENO, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarTabCostos() {
        return validarPermisos(TAB_COSTOS, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarTabHorario() {
        return validarPermisos(TAB_HORARIO, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarTabViabilidad() {
        return validarPermisos(TAB_VIABILIDAD, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarCreacionCM() {
        return validarPermisos(FORMULARIO_CM, ValidacionUtil.OPC_CREACION);
    }

    public boolean validarEdicionCM() {
        return validarPermisos(FORMULARIO_CM, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarELiminarMultiedificio(){
        return  validarPermisos(TAB_MULTIEDIFICI0_HOT_CCMM, ValidacionUtil.OPC_BORRADO);
    }

    public boolean validarCrearMultiedificio(){
        return  validarPermisos(TAB_MULTIEDIFICI0_HOT_CCMM, ValidacionUtil.OPC_CREACION);
    }

    public boolean validarEdicionConstructora() {
        return validarPermisos(FORMULARIO_CONSTRUCTORA, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarEdicionAcometida() {
        return validarPermisos(FORMULARIO_ACOMETIDA, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarCreacionMultiedificio() {
        return validarPermisos(FORMULARIO_MULTIEDIFICIO, ValidacionUtil.OPC_CREACION);
    }

    public boolean validarEdicionMultiedificio() {
        return validarPermisos(FORMULARIO_MULTIEDIFICIO, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarBorradoMultiedificio() {
        return validarPermisos(FORMULARIO_MULTIEDIFICIO, ValidacionUtil.OPC_BORRADO);
    }

    public boolean validarEdicionMOInfraestructura() {
        return validarPermisos(FORMULARIO_MO_INFRA, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarEdicionMTInfraestructura() {
        return validarPermisos(FORMULARIO_MT_INFRA, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarEdicionMODiseno() {
        return validarPermisos(FORMULARIO_MO_DISENO, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarEdicionMTDiseno() {
        return validarPermisos(FORMULARIO_MT_DISENO, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarEdicionCostosVisitasTecnicas() {
        return validarPermisos(FORMULARIO_COSTOS_VT, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarEdicionModeloFinanciero() {
        return validarPermisos(FORMULARIO_MODELO_FINANCIERO, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarBtnActCm() {
        return validarPermisos(BTN_ACT_CCMM, ValidacionUtil.OPC_EDICION);
    }

    /**
     * Verifica si tiene permiso para eliminar subEdificio
     * @return {@code boolean} true si tiene permiso, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean validarEliminarSubEdificioVt() {
        return validarPermisos(TAB_MULTIEDIFICI0_HOT_CCMM, "ELIMINARSUBEDIFICIO");
    }

    private boolean validarPermisos(final String formulario, final String accion) {
        try {
            return ValidacionUtil.validarVisualizacion(formulario, accion, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de validar los permisos. EX000: " + e.getMessage(), e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION, ""));
            return false;
        }
    }

    public String getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(String selectedTab) {
        this.selectedTab = selectedTab;
    }

    public CmtVisitaTecnicaMgl getVt() {
        return vt;
    }

    public void setVt(CmtVisitaTecnicaMgl vt) {
        this.vt = vt;
    }

    public CmtOrdenTrabajoMgl getOt() {
        return ot;
    }

    public void setOt(CmtOrdenTrabajoMgl ot) {
        this.ot = ot;
    }

    public CmtBasicaMgl getSegmento() {
        return segmento;
    }

    public void setSegmento(CmtBasicaMgl segmento) {
        this.segmento = segmento;
    }

    public CmtBasicaMgl getActividad() {
        return actividad;
    }

    public void setActividad(CmtBasicaMgl actividad) {
        this.actividad = actividad;
    }

    public CmtBasicaMgl getTipoVisitaTecnica() {
        return tipoVisitaTecnica;
    }

    public void setTipoVisitaTecnica(CmtBasicaMgl tipoVisitaTecnica) {
        this.tipoVisitaTecnica = tipoVisitaTecnica;
    }

    public List<CmtBasicaMgl> getUnidadSubEdificioList() {
        return unidadSubEdificioList;
    }

    public void setUnidadSubEdificioList(List<CmtBasicaMgl> unidadSubEdificioList) {
        this.unidadSubEdificioList = unidadSubEdificioList;
    }

    public List<CmtBasicaMgl> getAlimentacionElectricaList() {
        return alimentacionElectricaList;
    }

    public void setAlimentacionElectricaList(List<CmtBasicaMgl> alimentacionElectricaList) {
        this.alimentacionElectricaList = alimentacionElectricaList;
    }

    public List<CmtBasicaMgl> getCanalizacionInternaList() {
        return canalizacionInternaList;
    }

    public void setCanalizacionInternaList(List<CmtBasicaMgl> canalizacionInternaList) {
        this.canalizacionInternaList = canalizacionInternaList;
    }

    public List<CmtBasicaMgl> getManejoAscensoresList() {
        return manejoAscensoresList;
    }

    public void setManejoAscensoresList(List<CmtBasicaMgl> manejoAscensoresList) {
        this.manejoAscensoresList = manejoAscensoresList;
    }

    public List<CmtBasicaMgl> getSegmentoList() {
        return segmentoList;
    }

    public void setSegmentoList(List<CmtBasicaMgl> segmentoList) {
        this.segmentoList = segmentoList;
    }

    public List<CmtBasicaMgl> getTipoActividadList() {
        return tipoActividadList;
    }

    public void setTipoActividadList(List<CmtBasicaMgl> tipoActividadList) {
        this.tipoActividadList = tipoActividadList;
    }

    public List<CmtBasicaMgl> getTipoConfDistribucionList() {
        return tipoConfDistribucionList;
    }

    public void setTipoConfDistribucionList(List<CmtBasicaMgl> tipoConfDistribucionList) {
        this.tipoConfDistribucionList = tipoConfDistribucionList;
    }

    public List<CmtBasicaMgl> getTipoDistribucionList() {
        return tipoDistribucionList;
    }

    public void setTipoDistribucionList(List<CmtBasicaMgl> tipoDistribucionList) {
        this.tipoDistribucionList = tipoDistribucionList;
    }

    public List<CmtBasicaMgl> getTipoPuntoInicialAcometidaList() {
        return tipoPuntoInicialAcometidaList;
    }

    public void setTipoPuntoInicialAcometidaList(List<CmtBasicaMgl> tipoPuntoInicialAcometidaList) {
        this.tipoPuntoInicialAcometidaList = tipoPuntoInicialAcometidaList;
    }

    public List<CmtBasicaMgl> getTipoVtList() {
        return tipoVtList;
    }

    public void setTipoVtList(List<CmtBasicaMgl> tipoVtList) {
        this.tipoVtList = tipoVtList;
    }

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public boolean isInternet() {
        return internet;
    }

    public void setInternet(boolean internet) {
        this.internet = internet;
    }

    public boolean isTelefonia() {
        return telefonia;
    }

    public void setTelefonia(boolean telefonia) {
        this.telefonia = telefonia;
    }

    public boolean isTelevision() {
        return television;
    }

    public void setTelevision(boolean television) {
        this.television = television;
    }

    public boolean isApartamento() {
        return apartamento;
    }

    public void setApartamento(boolean apartamento) {
        this.apartamento = apartamento;
    }

    public boolean isCasa() {
        return casa;
    }

    public void setCasa(boolean casa) {
        this.casa = casa;
    }

    public boolean isOficina() {
        return oficina;
    }

    public void setOficina(boolean oficina) {
        this.oficina = oficina;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public String getFormTabSeleccionado() {
        return formTabSeleccionado;
    }

    public void setFormTabSeleccionado(String formTabSeleccionado) {
        this.formTabSeleccionado = formTabSeleccionado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isDireccionValidada() {
        return direccionValidada;
    }

    public void setDireccionValidada(boolean direccionValidada) {
        this.direccionValidada = direccionValidada;
    }

    public List<String> getBarrioslist() {
        return barrioslist;
    }

    public void setBarrioslist(List<String> barrioslist) {
        this.barrioslist = barrioslist;
    }

    public String getSelectedBarrio() {
        return selectedBarrio;
    }

    public void setSelectedBarrio(String selectedBarrio) {
        this.selectedBarrio = selectedBarrio;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public boolean isHayErroresAcometida() {
        return hayErroresAcometida;
    }

    public void setHayErroresAcometida(boolean hayErroresAcometida) {
        this.hayErroresAcometida = hayErroresAcometida;
    }

    public boolean isHayErroresVt() {
        return hayErroresVt;
    }

    public void setHayErroresVt(boolean hayErroresVt) {
        this.hayErroresVt = hayErroresVt;
    }

    public boolean isHayErroresAut() {
        return hayErroresAut;
    }

    public void setHayErroresAut(boolean hayErroresAut) {
        this.hayErroresAut = hayErroresAut;
    }

    public boolean isHayErrorSubEdi() {
        return hayErrorSubEdi;
    }

    public void setHayErrorSubEdi(boolean hayErrorSubEdi) {
        this.hayErrorSubEdi = hayErrorSubEdi;
    }

    public boolean isHayErrorHhpp() {
        return hayErrorHhpp;
    }

    public void setHayErrorHhpp(boolean hayErrorHhpp) {
        this.hayErrorHhpp = hayErrorHhpp;
    }

    public boolean isHayErrorItem() {
        return hayErrorItem;
    }

    public void setHayErrorItem(boolean hayErrorItem) {
        this.hayErrorItem = hayErrorItem;
    }

    public List<ParametrosCalles> getDirNivel5List() {
        return dirNivel5List;
    }

    public void setDirNivel5List(List<ParametrosCalles> dirNivel5List) {
        this.dirNivel5List = dirNivel5List;
    }

    public String getUsuarioVT() {
        return usuarioVT;
    }

    public void setUsuarioVT(String usuarioVT) {
        this.usuarioVT = usuarioVT;
    }

    public int getPerfilVt() {
        return perfilVt;
    }

    public void setPerfilVt(int perfilVt) {
        this.perfilVt = perfilVt;
    }

    public CmtSubEdificiosVt getSubEdificiosVt() {
        return subEdificiosVt;
    }

    public void setSubEdificiosVt(CmtSubEdificiosVt subEdificiosVt) {
        this.subEdificiosVt = subEdificiosVt;
    }

    public CmtHhppVtMgl getTecnologiaHabilitada() {
        return tecnologiaHabilitada;
    }

    public void setTecnologiaHabilitada(CmtHhppVtMgl tecnologiaHabilitada) {
        this.tecnologiaHabilitada = tecnologiaHabilitada;
    }

    public List<CmtSubEdificiosVt> getSubEdificiosVtList() {
        return subEdificiosVtList;
    }

    public void setSubEdificiosVtList(List<CmtSubEdificiosVt> subEdificiosVtList) {
        this.subEdificiosVtList = subEdificiosVtList;
    }

    public List<CmtHhppVtMgl> getListHhppBySubEdificio() {
        return listHhppBySubEdificio;
    }

    public void setListHhppBySubEdificio(List<CmtHhppVtMgl> listHhppBySubEdificio) {
        this.listHhppBySubEdificio = listHhppBySubEdificio;
    }

    public String getOpcionNivel5() {
        return opcionNivel5;
    }

    public void setOpcionNivel5(String opcionNivel5) {
        this.opcionNivel5 = opcionNivel5;
    }

    public List<CmtHorarioRestriccionMgl> getHorarioList() {
        return horarioList;
    }

    public void setHorarioList(List<CmtHorarioRestriccionMgl> horarioList) {
        this.horarioList = horarioList;
    }

    public List<CmtCompaniaMgl> getCompaniaAdminList() {
        return companiaAdminList;
    }

    public void setCompaniaAdminList(List<CmtCompaniaMgl> companiaAdminList) {
        this.companiaAdminList = companiaAdminList;
    }

    public List<CmtBasicaMgl> getEstratoList() {
        return estratoList;
    }

    public void setEstratoList(List<CmtBasicaMgl> estratoList) {
        this.estratoList = estratoList;
    }

    public CmtBasicaMgl getEstratoVt() {
        return estratoVt;
    }

    public void setEstratoVt(CmtBasicaMgl estratoVt) {
        this.estratoVt = estratoVt;
    }

    public List<CmtBasicaMgl> getListTablaBasicaTecnologias() {
        return listTablaBasicaTecnologias;
    }

    public void setListTablaBasicaTecnologias(List<CmtBasicaMgl> listTablaBasicaTecnologias) {
        this.listTablaBasicaTecnologias = listTablaBasicaTecnologias;
    }

    public BigDecimal getSubTipoTrabajoSelected() {
        return subTipoTrabajoSelected;
    }

    public void setSubTipoTrabajoSelected(BigDecimal subTipoTrabajoSelected) {
        this.subTipoTrabajoSelected = subTipoTrabajoSelected;
    }

    public List<CmtTipoOtMgl> getListTipoTrabajoOt() {
        return listTipoTrabajoOt;
    }

    public void setListTipoTrabajoOt(List<CmtTipoOtMgl> listTipoTrabajoOt) {
        this.listTipoTrabajoOt = listTipoTrabajoOt;
    }

    public BigDecimal getTipoTrabajoOtSelected() {
        return tipoTrabajoOtSelected;
    }

    public void setTipoTrabajoOtSelected(BigDecimal tipoTrabajoOtSelected) {
        this.tipoTrabajoOtSelected = tipoTrabajoOtSelected;
    }

    public String getIntPisoTorreSelect() {
        return intPisoTorreSelect;
    }

    public void setIntPisoTorreSelect(String intPisoTorreSelect) {
        this.intPisoTorreSelect = intPisoTorreSelect;
    }

    public CmtBasicaMgl getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(CmtBasicaMgl tecnologia) {
        this.tecnologia = tecnologia;
    }

    public CmtBasicaMgl getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(CmtBasicaMgl tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public CmtTipoOtMgl getSubTipoTrabajo() {
        return subTipoTrabajo;
    }

    public void setSubTipoTrabajo(CmtTipoOtMgl subTipoTrabajo) {
        this.subTipoTrabajo = subTipoTrabajo;
    }

    public List<CmtBasicaMgl> getListTipoOrdenTrabajo() {
        return listTipoOrdenTrabajo;
    }

    public void setListTipoOrdenTrabajo(List<CmtBasicaMgl> listTipoOrdenTrabajo) {
        this.listTipoOrdenTrabajo = listTipoOrdenTrabajo;
    }

    public List<CmtTipoOtMgl> getSubTipoOrdenTrabajo() {
        return subTipoOrdenTrabajo;
    }

    public void setSubTipoOrdenTrabajo(List<CmtTipoOtMgl> subTipoOrdenTrabajo) {
        this.subTipoOrdenTrabajo = subTipoOrdenTrabajo;
    }

    public Integer contarSubEdificiosVt() {
        Integer count = null;
        try {
            CmtSubEdificiosVtFacade cmtSubEdificiosVtFacade = new CmtSubEdificiosVtFacade();
            count = cmtSubEdificiosVtFacade.getCountByVt(vt);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al VisitaTecnicaBean. " + e.getMessage(), e, LOGGER);
        }
        return count;
    }

    public Integer contarHhppSubEdificioVt()  {
         Integer count = null;
        try {
            CmtHhppVtMglFacade tecnologiaHabilitadaMglFacade = new CmtHhppVtMglFacade();
            count = tecnologiaHabilitadaMglFacade.findByVt(vt).size();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: contarHhppSubEdificioVt(). " + e.getMessage(), e, LOGGER);
        }
        return count;
    }

    public BigDecimal getPeriodoRecuperacion() {
        return periodoRecuperacion;
    }

    public void setPeriodoRecuperacion(BigDecimal periodoRecuperacion) {
        this.periodoRecuperacion = periodoRecuperacion;
    }

    public boolean validarEstadoFlujoOT() {
        try {
            CmtEstadoxFlujoMglFacadeLocal cmtEstadoxFlujoMglFacadeLocal = new CmtEstadoxFlujoMglFacade();
            CmtBasicaMgl tipoFlujo = null;
            CmtBasicaMgl estadoInterno = null;
            CmtBasicaMgl tec = null;
            if (vt != null && vt.getOtObj() != null) {
                estadoInterno = vt.getOtObj().getEstadoInternoObj();
                tec = vt.getOtObj().getBasicaIdTecnologia();
                if (vt.getOtObj().getTipoOtObj() != null) {
                    tipoFlujo = vt.getOtObj().getTipoOtObj().getTipoFlujoOt();
                }
            }
            validarFlujoOt = cmtEstadoxFlujoMglFacadeLocal.finalizoEstadosxFlujoDto(tipoFlujo, estadoInterno, tec);
            return validarFlujoOt;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: validarEstadoFlujoOT() " + e.getMessage(), e, LOGGER);
            return true;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en VisitaTecnicaBean: validarEstadoFlujoOT() " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public List<CmtBasicaMgl> getTipoEdificioList() {
        return tipoEdificioList;
    }

    public void setTipoEdificioList(List<CmtBasicaMgl> tipoEdificioList) {
        this.tipoEdificioList = tipoEdificioList;
    }

    public CmtBasicaMgl getTipoEdificio() {
        return tipoEdificio;
    }

    public void setTipoEdificio(CmtBasicaMgl tipoEdificio) {
        this.tipoEdificio = tipoEdificio;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public void verAgenda() {
        this.nuevaAgenda = false;
    }

    public void setNuevaAgenda(boolean nuevaAgenda) {
        this.nuevaAgenda = nuevaAgenda;
    }

    public Boolean getNuevaAgenda() {
        return this.nuevaAgenda;
    }

    public CmtHhppVtMgl getHhppSubedificio() {
        return hhppSubedificio;
    }

    public void setHhppSubedificio(CmtHhppVtMgl hhppSubedificio) {
        this.hhppSubedificio = hhppSubedificio;
    }

    public HhppVtCasasDto getHhppVtCasas() {
        return hhppVtCasas;
    }

    public void setHhppVtCasas(HhppVtCasasDto hhppVtCasas) {
        this.hhppVtCasas = hhppVtCasas;
    }

    public List<HhppVtCasasDto> getListhhppVtCasas() {
        return listhhppVtCasas;
    }

    public void setListhhppVtCasas(List<HhppVtCasasDto> listhhppVtCasas) {
        this.listhhppVtCasas = listhhppVtCasas;
    }


    public UsuariosServicesDTO getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(UsuariosServicesDTO usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }
    
    public BigDecimal getIdCentroPoblado() {
        return idCentroPoblado;
    }

    public void setIdCentroPoblado(BigDecimal idCentroPoblado) {
        this.idCentroPoblado = idCentroPoblado;
    }

    public CmtVisitaTecnicaMgl getVtAco() {
        return vtAco;
    }

    public void setVtAco(CmtVisitaTecnicaMgl vtAco) {
        this.vtAco = vtAco;
    }

    public ResponseValidarDireccionDto getResponseValidarDireccionDto() {
        return responseValidarDireccionDto;
    }

    public void setResponseValidarDireccionDto(ResponseValidarDireccionDto responseValidarDireccionDto) {
        this.responseValidarDireccionDto = responseValidarDireccionDto;
    }

    public boolean isClickValidar() {
        return clickValidar;
    }

    public void setClickValidar(boolean clickValidar) {
        this.clickValidar = clickValidar;
    }

    public boolean isOtroBarrio() {
        return otroBarrio;
    }

    public void setOtroBarrio(boolean otroBarrio) {
        this.otroBarrio = otroBarrio;
    }
    
    public String getDirectorIngenieroObra() {
        return directorIngenieroObra;
    }

    public void setDirectorIngenieroObra(String directorIngenieroObra) {
        this.directorIngenieroObra = directorIngenieroObra;
    }

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    public CmtSubEdificiosVt getSubEdiModDir() {
        return subEdiModDir;
    }

    public void setSubEdiModDir(CmtSubEdificiosVt subEdiModDir) {
        this.subEdiModDir = subEdiModDir;
    }

    public boolean isCodVTOtro() {
        return codVTOtro;
    }

    public void setCodVTOtro(boolean codVTOtro) {
        this.codVTOtro = codVTOtro;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<CmtArchivosVtMgl> getLstArchivosVtMgls() {
        return lstArchivosVtMgls;
    }

    public void setLstArchivosVtMgls(List<CmtArchivosVtMgl> lstArchivosVtMgls) {
        this.lstArchivosVtMgls = lstArchivosVtMgls;
    }

    public boolean isActivacionUCM() {
        return activacionUCM;
    }

    public void setActivacionUCM(boolean activacionUCM) {
        this.activacionUCM = activacionUCM;
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
            FacesUtil.mostrarMensajeError("Error al consultar el parametro: " + Constant.ACTIVACION_ENVIO_UCM + "" + ex.getMessage(), ex, LOGGER);
            activacionUCM = false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al consultar el parametro: " + Constant.ACTIVACION_ENVIO_UCM + "" + e.getMessage(), e, LOGGER);
            activacionUCM = false;
        }
        return activacionUCM;
    }

    public boolean isValidarFlujoOt() {
        return validarFlujoOt;
    }

    public void setValidarFlujoOt(boolean validarFlujoOt) {
        this.validarFlujoOt = validarFlujoOt;
    }

    public boolean isDeshabilitaFormVT() {
        return deshabilitaFormVT;
    }

    public void setDeshabilitaFormVT(boolean deshabilitaFormVT) {
        this.deshabilitaFormVT = deshabilitaFormVT;
    }
    
    
    
    
}