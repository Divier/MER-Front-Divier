/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.ejb.mgl.address.dto.TecMarcasTecnologiaHabAudDto;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.dtos.CmtFiltroCoberturasDto;
import co.com.claro.mgl.dtos.EstadoTecnologiaHhppDto;
import co.com.claro.mgl.dtos.FiltroMarcasMglDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.CmtNotasHhppDetalleVtMglFacadeLocal;
import co.com.claro.mgl.facade.CmtNotasHhppVtMglFacadeLocal;
import co.com.claro.mgl.facade.DireccionMglFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.HhppAuditoriaMglFacadeLocal;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.MarcasHhppMglFacadeLocal;
import co.com.claro.mgl.facade.MarcasMglFacadeLocal;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.OtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.OtHhppTecnologiaMglFacadeLocal;
import co.com.claro.mgl.facade.SolicitudFacadeLocal;
import co.com.claro.mgl.facade.SubDireccionMglFacadeLocal;
import co.com.claro.mgl.facade.TecArcCamEstratoFacadeLocal;
import co.com.claro.mgl.facade.TecMarcasTecnologiaHabAudFacadeLocal;
import co.com.claro.mgl.facade.TipoHhppMglFacadeLocal;
import co.com.claro.mgl.facade.TipoOtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.UbicacionMglFacadeLocal;
import co.com.claro.mgl.facade.cm.*;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.CmtNotasHhppDetalleVtMgl;
import co.com.claro.mgl.jpa.entities.CmtNotasHhppVtMgl;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasHhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.OtHhppTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.TecArchivosCambioEstrato;
import co.com.claro.mgl.jpa.entities.TecMarcasTecnologiaHabAud;
import co.com.claro.mgl.jpa.entities.TipoHhppMgl;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCoberturaEntregaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.cm.ConsultaCuentasMatricesBean;
import co.com.claro.mgl.mbeans.cm.CuentaMatrizBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.mbeans.vt.estadoSolicitudHhppInspira.EstadoSolicitudHhppBean;
import co.com.claro.mgl.mbeans.vt.otHhpp.OtHhppMglSessionBean;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.claro.visitasTecnicas.business.ConsultaEspecificaManager;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Juan David Hernandez
 */
@ViewScoped
@ManagedBean(name = "detalleHhppBean")
public class DetalleHhppBean implements Serializable {

    private SecurityLogin securityLogin;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private static final Logger LOGGER = LogManager.getLogger(DetalleHhppBean.class);
    private List<CmtNotasHhppVtMgl> notasHhppList;
    private List<CmtBasicaMgl> tecnologiaBasicaList;
    private List<CmtNotasHhppDetalleVtMgl> comentarioList;
    private List<CmtBasicaMgl> tipoNotaList;
    private List<MarcasHhppMgl> etiquetasHhppList;
    private List<DireccionMgl> direccionAlternaList;
    private List<AuditoriaDto> listAuditoria;
    private List<AuditoriaDto> listAuditoriaDireccion;
    private List<HhppAuditoriaMgl> historicoClientesList;
    private List<EstadoTecnologiaHhppDto> estadoTecnologiaHhppDtoList;
    private List<TecArchivosCambioEstrato> tecArchivosCambioEstratos;
    private List<HhppMgl> hhppTecnologiaList;
    private List<HhppMgl> hhppList;
    private List<CmtTecnologiaSubMgl> tecnologiaSubMglList;
    private List<CmtCoberturaEntregaMgl> coberturaEntregaList;
    private ArrayList<CmtHorarioRestriccionMgl> horarioRestriccion;
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private CmtDireccionDetalladaMgl direccionDetallada;
    private CmtCuentaMatrizMgl cuentaMatrizHhpp;
    private CmtBasicaMgl tipoNotaSelected = new CmtBasicaMgl();
    private CmtNotasHhppVtMgl cmtNotasHhppVtMgl = new CmtNotasHhppVtMgl();
    private GeograficoPoliticoMgl ciudadGpo;
    private GeograficoPoliticoMgl departamentoGpo;
    private GeograficoPoliticoMgl centroPobladoGpo;
    private HhppMgl hhppSeleccionado;
    private AddressService addressService;
    private String usuarioVT = null;
    private int perfilVt = 0;
    private String currier;
    private String codigoPostal;
    private String direccionAntiguaHhpp;
    private String direccionHhpp;
    private String descripcionPredio;
    private String pageActualHistorico;
    private String numPaginaHistorico = "1";
    private String inicioPagina = "<< - Inicio";
    private String anteriorPagina = "< - Anterior";
    private String finPagina = "Fin - >>";
    private String siguientePagina = "Siguiente - >";
    private String nivelSocioEcoDireccion;
    private List<Integer> paginaHistoricoList;
    private List<OtHhppMgl> otHhppList;
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private int actual;
    private int actualCover;
    private int actualHistorico;
    private int filasPag = ConstantsCM.PAGINACION_DIEZ_FILAS;
    private int filasPagEtiquetas = ConstantsCM.PAGINACION_CINCO_FILAS;
    private String espacio = "&nbsp;";
    private String notaComentarioStr;
    private String selectedTab = "GENERAL";
    private String direccionAlterna;
    private String etiquetasHhpp;
    private String localidadHhpp;
    private String estrato;
    private String telefonosCuentaMatriz;
    private boolean cuentaMatrizActiva;
    private boolean showEtiquetas;
    private boolean horarioCargado;
    private boolean generalTab;
    private boolean ordenesTab;
    private boolean notasTab;
    private boolean infoTecnicaTab;
    private boolean horarioTab;
    private boolean historicoClientesTab;
    private boolean bitacoraTab;
    private boolean bitacoraDireccionTab;
    private boolean curriesTab;
    private boolean marcasTab;
    private boolean hhppExistente;
    private CmtFiltroCoberturasDto cmtFiltroCoberturasDto;
    private List<CmtCoberturaEntregaMgl> cmtCoberturaEntregaMglList;
    private HashMap<String, Object> paramsCover;
    private HashMap<String, Object> paramsEtiquetas;
    private boolean habilitaObj = false;
    private boolean pintarPaginadoCover;
    private String pageActualCover;
    private List<Integer> paginaListCover;
    private String numPaginaCover;
    private String tipoInvCovertura = "";
    private String tipoInvOperador = "";
    private List<TipoHhppMgl> listaTipoHhpp;
    private String tipoHHppSelected;
    private List<Solicitud> ordenesSolicitudList;
    private List<Integer> paginaListSolicitudes;
    private int actualSolicitudes;
    private String pageActualSolicitudes;
    private String numPaginaSolicitudes = "1";
    private Solicitud filtrosSolicitudes;
    private List<CmtBasicaMgl> tiposSolitudesList;
    private List<CmtBasicaMgl> tiposTecnologiaList;
    private OtHhppMgl filtroOrdenes;
    private List<TipoOtHhppMgl> tipoOtList;
    private boolean activarTodasMarcasHhpp;
    private String activarTodasMarcasHhppString;
    private List<MarcasHhppMgl> listaMarcasHomePass;
    private List<MarcasHhppMgl> listaMarcasHomePassAux;
    private List<TecMarcasTecnologiaHabAudDto> listaMarcasHomePassLog;
    private MarcasHhppMgl marcaHomePassSeleccionado;
    private String crudMarcasHhppActivo = "C";//C:consultar; A:adicionar; M:modificar; L:logAuditoria
    private List<MarcasMgl> listaMarcas;
    private BigDecimal marcaSeleccionada;
    private BigDecimal tecnologiaHhppSeleccionada;//id del home pass seleccionado
    private BigDecimal estadoRegistroMarca = new BigDecimal(BigInteger.ONE);
    private String observacionesMarcas;
    private HtmlDataTable tablaMarcaHhpp;
    private List<BigDecimal> idHhppList = new ArrayList();
    //atributos de filtro
    private String codigoMarcaFiltro;
    private FiltroMarcasMglDto filtroMarcasMglDto;
    private String nombreEtiquetaFiltro;
    private String tecnologiaFiltro;
    private String estadoFiltro;
    private String auditoriaFiltro;
    private String pageActualEtiquetas;
    private int actualEtiquetas;
    private String numPaginaEtiquetas = "1";
    private List<Integer> paginaListEtiquetas;

    @Getter
    private static final String TAB_GENERAL_HHPP ="TABGENERALHHPP";
    @Getter
    private static final String TAB_NOTAS_HHPP ="TABNOTASHHPP";
    @Getter
    private static final String TAB_ETIQUETAS_HHPP ="TABETIQUETASHHPP";
    @Getter
    private static final String TAB_HORARIO_HHPP = "TABHORARIOHHPP";

    @EJB
    private CmtTecnologiaSubedificioMglFacadeLocal cmtTecnologiaSubMglFacadeLocal;
    @EJB
    private TecArcCamEstratoFacadeLocal tecArcCamEstratoFacadeLocal;
    @EJB
    private CmtHorarioRestriccionMglFacadeLocal horarioRestriccionFacadeLocal;
    @EJB
    private CmtNotasHhppDetalleVtMglFacadeLocal notasHhppDetalleVtMglFacadeLocal;
    @EJB
    private CmtNotasHhppVtMglFacadeLocal notasHhppVtMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private HhppMglFacadeLocal hhppMglFacadeLocal;
    @EJB
    private HhppAuditoriaMglFacadeLocal hhppAuditoriaMglFacadeLocal;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cmtCuentaMatrizMglFacadeLocal;
    @EJB
    private MarcasHhppMglFacadeLocal marcasHhppMglFacadeLocal;
    @EJB
    private UbicacionMglFacadeLocal ubicacionMglFacadeLocal;
    @EJB
    private DireccionMglFacadeLocal direccionMglFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private OtHhppMglFacadeLocal otHhppMglFacadeLocal;
    @EJB
    private CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMglFacadeLocal;
    @EJB
    private OtHhppTecnologiaMglFacadeLocal otHhppTecnologiaMglFacadeLocal;
    @EJB
    private CmtSubEdificioMglFacadeLocal cmtSubEdificioMglFacadeLocal;
    @EJB
    private CmtDireccionMglFacadeLocal cmtDireccionMglFacadeLocal;
    @EJB
    private CmtValidadorDireccionesFacadeLocal cmtValidadorDireccionesFacadeLocal;
    @EJB
    private SubDireccionMglFacadeLocal subDireccionMglFacadeLocal;
    @EJB
    private CmtCoberturaEntregaMglFacadeLocal cmtCoberturaEntregaMglFacadeLocal;
    @EJB
    private TipoHhppMglFacadeLocal tipoHhppMglFacadeLocal;
    @EJB
    private SolicitudFacadeLocal solicitudFacadeLocal;
    @EJB
    private TipoOtHhppMglFacadeLocal tipoOtHhppMglFacadeLocal;
    @EJB
    private MarcasMglFacadeLocal marcasMglFacade;
    @EJB
    private TecMarcasTecnologiaHabAudFacadeLocal tmthaFacade;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cuentaMatrizFacadeLocal;
    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;

    public DetalleHhppBean() throws ApplicationException, IOException {
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
            FacesUtil.mostrarMensajeError("Error en DetalleHhppBean, al Validando Autenticacion ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en DetalleHhppBean, al Validando Autenticacion ", e, LOGGER);
        }
    }

    @PostConstruct
    private void init() {
        try {
            filtrosSolicitudes = new Solicitud();
            filtrosSolicitudes.setTecnologiaId(new CmtBasicaMgl());
            filtroOrdenes = new OtHhppMgl();
            filtroOrdenes.setTipoOtHhppId(new TipoOtHhppMgl());
            filtroOrdenes.setEstadoGeneral(new CmtBasicaMgl());
            tiposSolitudesList = new ArrayList<>();
            tiposTecnologiaList = new ArrayList<>();
            CmtTipoBasicaMgl tipoBasicaTipoAccion = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_ACCION_SOLICITUD);
            tiposSolitudesList = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoAccion);
            tiposTecnologiaList = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA));
            tipoOtList = new ArrayList<>();
            tipoOtList = tipoOtHhppMglFacadeLocal.findAll();
            cargarHhppSeleccionada();

        } catch (ApplicationException e) {
            LOGGER.error("Error en init, al realizar cargue inicial de la pantalla " + e.getMessage()
                    + " ", e);
        }
    }

    /**
     * Metodo para validar si el usuario tiene permisos de creacion
     * @return  {@code boolean} true si tiene permisos de creacion, false en caso contrario
     * @author Manuel Hernandez
     */
    public boolean isRolCrear(String formulario){
        try {
            return ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_CREACION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar el rol de creación. ");
        }
        return false;
    }

    /**
     * Metodo para validar si el usuario tiene permisos de edicion
     * @return  {@code boolean} true si tiene permisos de edicion, false en caso contrario
     * @author Manuel Hernandez
     */
    public boolean isRolEditar(String formulario){
        try {
            return ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar el rol de edición. ");
        }
        return false;
    }

    /**
     * Metodo para validar si el usuario tiene permisos de eliminar
     * @return  {@code boolean} true si tiene permisos de eliminar, false en caso contrario
     * @author Manuel Hernandez
     */
    public boolean isRolEliminar(String formulario){
        try {
            return ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_BORRADO, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar el rol de eliminación. ");
        }
        return false;
    }

    /**
     * Fu/nción que carga toda la información correspondiente a la dirección
     * seleccionada
     *
     * @author Juan David Hernandez
     */
    public void cargarHhppSeleccionada() throws ApplicationException {
        try {
            String cadenaTipoTecnologiaHHpp;

            // Instacia Bean de Session para obtener la solicitud seleccionada
            HhppDetalleSessionBean hhppDetalleSessionBean
                    = (HhppDetalleSessionBean) JSFUtil.getSessionBean(HhppDetalleSessionBean.class);

            if(hhppDetalleSessionBean.getDireccionDetalladaSeleccionada() != null){
                //Obtenemos el hhpp por Id para tener información actualizada.
                direccionDetallada = hhppDetalleSessionBean
                        .getDireccionDetalladaSeleccionada().clone();

                if (hhppDetalleSessionBean.getHhppSeleccionado() != null
                        && hhppDetalleSessionBean.getHhppSeleccionado().getHhpId() != null) {

                    //Obtenemos el hhpp por Id para tener información actualizada.
                    hhppSeleccionado = hhppMglFacadeLocal
                            .findById(hhppDetalleSessionBean.getHhppSeleccionado().getHhpId());
                    hhppExistente = true;
                    if (hhppSeleccionado == null) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "Ha ocurrido un error detallando el Hhpp,"
                                        + " intente nuevamente por favor", ""));
                        return;
                    }
                    showGeneralTab();
                    obtenerHhppAsociadosList(direccionDetallada);
                    obtenerLocalidadHhpp(direccionDetallada);
                    obtenerEtiquetasList(hhppSeleccionado, hhppTecnologiaList);
                    obtenerDireccionAlterna(hhppSeleccionado);
                    obtenerCentroPobladoCiudadDepartamentoHhpp(direccionDetallada);
                    obtenerDireccionAntiguaGeo(direccionDetallada, centroPobladoGpo, departamentoGpo);
                    obtenerEstadosTecnologias(tecnologiaSubMglList, hhppList, addressService, hhppDetalleSessionBean.getHhppSeleccionado().isDetalleHhppFact());
                    obtenerCuentaMatriz(hhppSeleccionado);
                    conversionEstrato(direccionDetallada);
                    obtenerTipoNotasList();
                    cargarArchivosCambioEstrato(direccionDetallada);
                    obtenerCurrierDireccion(direccionDetallada);
                    obtenerTipoViviendaHhpp(hhppSeleccionado);

                } else {
                    hhppExistente = false;
                    if (hhppDetalleSessionBean.getDireccionDetalladaSeleccionada() != null) {
                        //cuando se detallada una direccion que no tiene hhpp
                        showGeneralTab();
                        obtenerLocalidadHhpp(direccionDetallada);
                        obtenerCentroPobladoCiudadDepartamentoHhpp(direccionDetallada);
                        obtenerDireccionAntiguaGeo(direccionDetallada, centroPobladoGpo, departamentoGpo);
                        obtenerEstadosTecnologias(tecnologiaSubMglList, hhppList, addressService, false);
                        conversionEstrato(direccionDetallada);
                        obtenerCurrierDireccion(direccionDetallada);
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "Ha ocurrido un error detallando el registro,"
                                        + " intente nuevamente por favor", ""));
                        return;
                    }
                }
            }
        } catch (ApplicationException | CloneNotSupportedException e) {
            LOGGER.error("Error al cargar dirección seleccionada", e);
            throw new ApplicationException(e);
        } catch (Exception e) {
            LOGGER.error("Error al cargar dirección seleccionada", e);
            throw new ApplicationException(e);
        }
    }

    /**
     * Función que permite navegar entre Tabs en la pantalla
     *
     * @author Juan David Hernandez
     * @param sSeleccionado
     */
    public void cambiarTab(String sSeleccionado) throws ApplicationException {
        try {
            ConstantsCM.TABS_HHPP Seleccionado
                    = ConstantsCM.TABS_HHPP.valueOf(sSeleccionado);
            switch (Seleccionado) {
                case GENERAL:
                    selectedTab = "GENERAL";
                    showGeneralTab();
                    break;
                case ORDENES:
                    selectedTab = "ORDENES";
                    cargarOtHhppList();
                    cargarOrdenesSolicitudesList();
                    showOrdenesTab();
                    break;
                case NOTAS:
                    selectedTab = "NOTAS";
                    findNotasByHhpp(hhppList);
                    showNotasTab();
                    break;
                case INFOTECNICA:
                    selectedTab = "INFOTECNICA";
                    showInfoTecnicaTab();
                    break;
                case HORARIO:
                    selectedTab = "HORARIO";
                    consultarHorarioRestriccion();
                    showHorarioTab();
                    break;
                case HISTORICOCLIENTES:
                    selectedTab = "HISTORICOCLIENTES";
                    obtenerHistoricoClientes(hhppList);
                    showHistoricoClientesTab();
                    break;
                case BITACORA:
                    selectedTab = "BITACORA";
                    obtenerBitacoraHhpp(hhppList);
                    showBitacora();
                    break;
                case BITACORADIRECCION:
                    selectedTab = "BITACORADIRECCION";
                    obtenerBitacoraDireccion(direccionDetallada);
                    showBitacoraDireccion();
                    break;
                case CURRIER:
                    selectedTab = "CURRIER";
                    showCurriesDireccion();
                    findByFiltroCover();
                    break;
                case MARCAS:
                    //inicializa variables filtros
                    filtroMarcasMglDto = new FiltroMarcasMglDto();
                    codigoMarcaFiltro = "";
                    nombreEtiquetaFiltro = "";
                    tecnologiaFiltro = "";
                    estadoFiltro = "";

                    if (hhppSeleccionado != null) {
                        selectedTab = "MARCAS";
                        showMarcas();
                        etiquetaConsultar();
                    }
                    break;
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al realizar el cambio de Tab " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error al realizar el cambio de Tab " + e.getMessage(), e);
            throw new ApplicationException("Error al realizar el cambio de Tab ", e);
        }
    }

    /**
     * Función redirecciona el detalle de la ot
     *
     * @author Juan David Hernandez
     * @param otHhppSeleccionada
     */
    public void irDetalleOtHhpp(OtHhppMgl otHhppSeleccionada) {
        try {
            // Instacia Bean de Session para obtener la solicitud seleccionada
            OtHhppMglSessionBean otHhppMglSessionBean
                    = (OtHhppMglSessionBean) JSFUtil.getSessionBean(OtHhppMglSessionBean.class);
            otHhppMglSessionBean.setOtHhppMglSeleccionado(otHhppSeleccionada);
            otHhppMglSessionBean.setDetalleOtHhpp(true);
            FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/editarOtHhpp.jsf");
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irDetalleOtHhpp, al redireccionar al detalle de la OT. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irDetalleOtHhpp, al redireccionar al detalle de la OT. ", e, LOGGER);
        }
    }

    /**
     * Función obtiene la direccion antigua apartir de la georefenciación de la
     * direccion
     *
     * @author Juan David Hernandez
     * @param departamento
     * @param centroPoblado
     */
    public void obtenerDireccionAntiguaGeo(CmtDireccionDetalladaMgl direccionDetallada,
            GeograficoPoliticoMgl centroPoblado, GeograficoPoliticoMgl departamento) throws ApplicationException {
        try {
            if (direccionDetallada != null) {

                CmtDireccionDetalleMglManager direccionDetalladaManager = new CmtDireccionDetalleMglManager();

                DrDireccion drDireccion
                        = direccionDetalladaManager.parseCmtDireccionDetalladaMglToDrDireccion(direccionDetallada);

                addressService = cmtValidadorDireccionesFacadeLocal
                        .calcularCoberturaDrDireccion(drDireccion, centroPoblado);
                if (addressService != null && addressService.getAlternateaddress() != null
                        && !addressService.getAlternateaddress().isEmpty()) {
                    direccionAntiguaHhpp = addressService.getAlternateaddress();
                }
            }

        } catch (ApplicationException e) {
            LOGGER.error("Error redireccionar al detalle de la OT" + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error redireccionar al detalle de la OT" + e.getMessage(), e);
            throw e;

        }
    }

    /**
     * Metodo para consultar todos los links de las rutas de los archivos de
     * soportes asociados a una solicitud de cambio de estrato.
     *
     * @author Juan David Hernandez
     * @param direccionDetallada del hhpp para buscar por solicitud de cambio de
     * estrato.
     */
    public void cargarArchivosCambioEstrato(CmtDireccionDetalladaMgl direccionDetallada) throws ApplicationException {
        try {
            if (direccionDetallada != null && direccionDetallada.getDireccionDetalladaId() != null) {
                //carga la lista de links de documentos  
                tecArchivosCambioEstratos = tecArcCamEstratoFacadeLocal.
                        findUrlsByIdDireccionDetalladaSolicitud(direccionDetallada.getDireccionDetalladaId());
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al intentar obtener las url de los archivos de "
                    + "solicitud de cambio de estrato de la direccion " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error al intentar obtener las url de los archivos de "
                    + "solicitud de cambio de estrato de la direccion " + e.getMessage(), e);
            throw new ApplicationException(e);
        }
    }

    /**
     * Metodo obtener el currier de la direccion o subdireccion segun
     * corresponda
     *
     * @author Juan David Hernandez
     * @param direccionDetallada del hhpp para buscar el currier
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void obtenerCurrierDireccion(CmtDireccionDetalladaMgl direccionDetallada) throws ApplicationException {
        try {
            if (direccionDetallada != null) {
                currier = "";
                if (direccionDetallada.getSubDireccion() != null) {
                    if (direccionDetallada.getSubDireccion().getGeoZonaCurrier() != null) {
                        currier = direccionDetallada.getSubDireccion().getGeoZonaCurrier();
                        codigoPostal = direccionDetallada.getSubDireccion().getGeoZona5G();
                    }
                } else {
                    if (direccionDetallada.getDireccion() != null) {
                        if (direccionDetallada.getDireccion().getGeoZonaCurrier() != null) {
                            currier = direccionDetallada.getDireccion().getGeoZonaCurrier();
                            codigoPostal = direccionDetallada.getDireccion().getGeoZona5G();
                        }
                    }
                }
            }
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtenerCurrierDireccion " + e.getMessage(), e);
            throw new ApplicationException(e);

        } catch (Exception e) {
            LOGGER.error("Error al obtenerCurrierDireccion " + e.getMessage(), e);
            throw new ApplicationException("Error al obtenerCurrierDireccion ", e);

        }
    }

    /**
     * Metodo obtener tipo de vivienda de un hhpp con nombre y valor
     *
     * @author Juan David Hernandez
     * @param hhppMgl hhpp seleccionado
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void obtenerTipoViviendaHhpp(HhppMgl hhppMgl) throws ApplicationException {
        try {
            if (hhppMgl != null && hhppMgl.getThhId() != null
                    && !hhppMgl.getThhId().isEmpty()) {
                listaTipoHhpp = tipoHhppMglFacadeLocal.findAll();
                if (listaTipoHhpp != null && !listaTipoHhpp.isEmpty()) {
                    for (TipoHhppMgl tipoHhppMgl : listaTipoHhpp) {
                        if (tipoHhppMgl.getThhID().equalsIgnoreCase(hhppMgl.getThhId())) {
                            tipoHHppSelected = tipoHhppMgl.getThhID() + " - " + tipoHhppMgl.getThhValor();
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al obtenerTipoViviendaHhpp " + e.getMessage(), e);
            throw new ApplicationException(e);

        } catch (Exception e) {
            LOGGER.error("Error al obtenerTipoViviendaHhpp " + e.getMessage(), e);
            throw new ApplicationException("Error al obtenerTipoViviendaHhpp ", e);

        }
    }

    public String retornaNombreArchivo(String Url) {

        String nombre = "";
        nombre = Url.substring(Url.lastIndexOf('/') + 1);
        LOGGER.info("Path: " + Url + " -- File: " + nombre);
        return nombre;
    }

    /**
     * Función obtiene el listado de tecnologias y las compara con las
     * tecnologias que tengan los hhpps de la dirección actual
     *
     * @author Juan David Hernandez
     * @param addressServices
     * @param hhppAsociadosList
     */
    public void obtenerEstadosTecnologias(List<CmtTecnologiaSubMgl> tecnologiaSubList,
            List<HhppMgl> hhppAsociadosList, AddressService addressServices, boolean isDetalleHhppFact) throws ApplicationException {
        try {
            //Obtiene valores de tipo de tecnología
            CmtTipoBasicaMgl tipoBasicaTipoTecnologia;
            tipoBasicaTipoTecnologia = cmtTipoBasicaMglFacadeLocal
                    .findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
            tecnologiaBasicaList
                    = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoTecnologia);

            //Obtenemos listado de hhpp asociados a la dirección para conocer los estados   
            hhppTecnologiaList = new ArrayList();

            //listado de hhpp asociados a la misma direccion
            if (hhppAsociadosList != null && !hhppAsociadosList.isEmpty()) {
                for (HhppMgl hhppSubMgl : hhppAsociadosList) {
                    if (!hhppTecnologiaList.contains(hhppSubMgl) && hhppSubMgl.getEstadoRegistro() == 1) {
                        hhppTecnologiaList.add(hhppSubMgl);
                    }
                }
            }
            estadoTecnologiaHhppDtoList = new ArrayList();

            if (tecnologiaBasicaList != null && !tecnologiaBasicaList.isEmpty()
                    && hhppTecnologiaList != null && !hhppTecnologiaList.isEmpty()) {

                for (CmtBasicaMgl tecnologia : tecnologiaBasicaList) {

                    boolean flag = false;
                    EstadoTecnologiaHhppDto estadoTecnologiaHhppDto = new EstadoTecnologiaHhppDto();
                    estadoTecnologiaHhppDto.setIdTecnologia(tecnologia.getBasicaId());
                    estadoTecnologiaHhppDto.setNombreTecnologia(tecnologia.getNombreBasica().trim());
                    NodoMgl nodo = new NodoMgl();

                    for (HhppMgl hhppMglTecnologia : hhppTecnologiaList) {
                        if (tecnologia.getBasicaId().equals(hhppMglTecnologia
                                .getNodId().getNodTipo().getBasicaId())) {
                            flag = true;
                            estadoTecnologiaHhppDto.setEstadoTecnologia(hhppMglTecnologia.getEhhId().getEhhID());
                            estadoTecnologiaHhppDto.setNombreEstadoTecnologia(hhppMglTecnologia.getEhhId().getEhhNombre());
                            estadoTecnologiaHhppDto.setHhppMglSelected(hhppMglTecnologia);
                            estadoTecnologiaHhppDto.setEnableLink(true);
                            nodo = nodoMglFacadeLocal.findById(
                                        hhppMglTecnologia.getNodId().getNodId()
                                );
                            estadoTecnologiaHhppDto.setStateNodeTecnologCM((nodo.getEstado().getBasicaId().equals(Constant.NODO_TIPO_CERTIFICADO))?
                                nodo.getEstado().getNombreBasica():Constant.NO_CERTIFICADO);
                            estadoTecnologiaHhppDto.setEnableLinkNode(true);
                        }
                    }
                    if (!flag) {
                        String nodoCobertura = establecerNodoCoberturaSugerido(tecnologia);
                        if (nodoCobertura == null || nodoCobertura.isEmpty() || nodoCobertura.equals("")) {
                            estadoTecnologiaHhppDto.setEstadoTecnologia("No Disponible");
                            estadoTecnologiaHhppDto.setNombreEstadoTecnologia("No Disponible");
                        } else {
                            estadoTecnologiaHhppDto.setEstadoTecnologia("Cobertura: " + nodoCobertura);
                            estadoTecnologiaHhppDto.setNombreEstadoTecnologia("Cobertura: " + nodoCobertura);
                        }
                        estadoTecnologiaHhppDto.setStateNodeTecnologCM("No Disponible");
                    }

                    //validacion para agregar la tecnologia de la CM del subedificio principal
                    boolean flag2 = false;
                    for (HhppMgl hhppMglTecnologia : hhppTecnologiaList) {
                        if (hhppMglTecnologia.getCmtTecnologiaSubId() != null) {
                            if (hhppMglTecnologia.getCmtTecnologiaSubId().getBasicaIdTecnologias().getBasicaId().equals(tecnologia.getBasicaId())) {
                                flag2 = true;
                                estadoTecnologiaHhppDto.setNombreEstadoTecnologCM(hhppMglTecnologia.getCmtTecnologiaSubId().getBasicaIdEstadosTec().getNombreBasica());
                            }
                        }
                    }
                    if (!flag2) {
                        estadoTecnologiaHhppDto.setNombreEstadoTecnologCM("No Disponible");
                    }
                    if(isDetalleHhppFact){
                        if(tecnologia.getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_FTTTH))
                            estadoTecnologiaHhppDtoList.add(estadoTecnologiaHhppDto);
                    } else {
                        estadoTecnologiaHhppDtoList.add(estadoTecnologiaHhppDto);
                    }
                }
            } else {
                if (tecnologiaBasicaList != null && !tecnologiaBasicaList.isEmpty()
                        && addressServices != null) {

                    for (CmtBasicaMgl tecnologia : tecnologiaBasicaList) {
                        if (direccionDetallada.getSubDireccion() != null) {
                            String nodo = "";
                            nodo = establecerNodoCoberturaSugerido(tecnologia);
                            if (nodo != null && !nodo.isEmpty()) {
                                tecnologia.setNodoCobertura("Coberturna: " + nodo);
                            } else {
                                tecnologia.setNodoCobertura("No Disponible");
                            }
                        } else {
                            if (direccionDetallada.getDireccion() != null) {
                                String nodo = "";
                                nodo = establecerNodoCoberturaSugerido(tecnologia);
                                if (nodo != null && !nodo.isEmpty()) {
                                    tecnologia.setNodoCobertura("Coberturna: " + nodo);
                                } else {
                                    tecnologia.setNodoCobertura("No Disponible");
                                }
                            }
                        }
                    }
                }
            }

        } catch (ApplicationException e) {
            LOGGER.error("Error al obtener estado de tecnologías " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error al obtener estado de tecnologías " + e.getMessage(), e);
            throw new ApplicationException("Error al obtener estado de tecnologías ", e);
        }
    }

    public String establecerNodoCoberturaSugerido(CmtBasicaMgl tecnologia) throws ApplicationException {
        String nodoCobertura = "";
        try {
            if (tecnologia.getIdentificadorInternoApp() != null && !tecnologia.getIdentificadorInternoApp().isEmpty()) {
                if (tecnologia.getIdentificadorInternoApp().equals(Constant.DTH)) {
                    nodoCobertura = addressService.getNodoDth();

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.FIBRA_FTTTH)) {
                    nodoCobertura = addressService.getNodoFtth();

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.HFC_BID)) {
                    nodoCobertura = addressService.getNodoUno();

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.HFC_UNI)) {
                    nodoCobertura = addressService.getNodoDos();

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.FIBRA_OP_GPON)) {
                    nodoCobertura = addressService.getNodoTres();

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.LTE_INTERNET)) {
                    nodoCobertura = addressService.getNodoWifi();

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.BTS_MOVIL)) {
                    nodoCobertura = addressService.getNodoMovil();
                }
            }
        } catch (RuntimeException ex) {
            nodoCobertura = "";
            LOGGER.error("Error en establecerNodoCoberturaSugerido " + ex.getMessage(), ex);
            throw new ApplicationException("Error en establecerNodoCoberturaSugerido ", ex);
        } catch (Exception ex) {
            nodoCobertura = "";
            LOGGER.error("Error en establecerNodoCoberturaSugerido " + ex.getMessage(), ex);
            throw new ApplicationException("Error en establecerNodoCoberturaSugerido ", ex);
        }
        return nodoCobertura;

    }

    /**
     * Función que realiza la conversión del valor del estrato
     *
     * @author Juan David Hernandez
     * @param direccionDetallada
     */
    public void conversionEstrato(CmtDireccionDetalladaMgl direccionDetallada) throws ApplicationException {
        try {
            if (direccionDetallada != null && direccionDetallada.getSubDireccion() != null) {
                if (direccionDetallada.getSubDireccion().getSdiEstrato() != null) {
                    if (direccionDetallada.getSubDireccion().getSdiEstrato().equals(new BigDecimal(-1))) {
                        estrato = "NG";
                    } else {
                        if (direccionDetallada.getSubDireccion().getSdiEstrato().equals(new BigDecimal(0))) {
                            estrato = "NR";
                        } else {
                            estrato = direccionDetallada.getSubDireccion().getSdiEstrato().toString();
                        }
                    }
                }
                if (direccionDetallada.getSubDireccion().getSdiNivelSocioecono() != null) {
                    if (direccionDetallada.getSubDireccion().getSdiNivelSocioecono().equals(new BigDecimal(-1))) {
                        nivelSocioEcoDireccion = "NG";
                    } else {
                        if (direccionDetallada.getSubDireccion().getSdiNivelSocioecono().equals(new BigDecimal(0))) {
                            nivelSocioEcoDireccion = "NR";
                        } else {
                            nivelSocioEcoDireccion = direccionDetallada.getSubDireccion().getSdiNivelSocioecono().toString();
                        }
                    }
                }

            } else {
                if (direccionDetallada.getDireccion() != null) {
                    if (direccionDetallada.getDireccion().getDirEstrato() != null) {
                        if (direccionDetallada.getDireccion().getDirEstrato().equals(new BigDecimal(-1))) {
                            estrato = "NG";
                        } else {
                            if (direccionDetallada.getDireccion().getDirEstrato().equals(new BigDecimal(0))) {
                                estrato = "NR";
                            } else {
                                estrato = direccionDetallada.getDireccion().getDirEstrato().toString();
                            }
                        }
                    }
                    if (direccionDetallada.getDireccion().getDirNivelSocioecono() != null) {
                        if (direccionDetallada.getDireccion().getDirNivelSocioecono().equals(new BigDecimal(-1))) {
                            nivelSocioEcoDireccion = "NG";
                        } else {
                            if (direccionDetallada.getDireccion().getDirNivelSocioecono().equals(new BigDecimal(0))) {
                                nivelSocioEcoDireccion = "NR";
                            } else {
                                nivelSocioEcoDireccion = direccionDetallada.getDireccion().getDirNivelSocioecono().toString();
                            }
                        }
                    }
                }

            }
        } catch (RuntimeException e) {
            LOGGER.error("Error al intentar hacer la conversión de estrato "
                    + "no georeferencia a un valor" + e.getMessage(), e);
            throw new ApplicationException(e);
        } catch (Exception e) {
            LOGGER.error("Error al intentar hacer la conversión de estrato "
                    + "no georeferencia a un valor" + e.getMessage(), e);
            throw new ApplicationException(e);
        }
    }

    /**
     * Función que obtiene listado de hhpp asociados a la dirección
     *
     * @author Juan David Hernandez
     * @param direccionDetalladaMgl
     */
    public void obtenerHhppAsociadosList(CmtDireccionDetalladaMgl direccionDetalladaMgl) throws ApplicationException {
        try {
            //Obtenemos listado de hhpp asociados a la dirección  
            hhppTecnologiaList = new ArrayList();
            hhppList = new ArrayList();
            HhppMglManager hhppMglManager = new HhppMglManager();
            if (direccionDetalladaMgl != null) {
                //Obtenemos los Hhpp de la Subdireccion  
                if (direccionDetalladaMgl.getSubDireccion() != null
                        && direccionDetalladaMgl.getSubDireccion().getSdiId() != null) {

                    hhppList = hhppMglManager
                            .findHhppSubDireccion(direccionDetalladaMgl.getSubDireccion().getSdiId());

                    if (hhppList != null && !hhppList.isEmpty()) {
                        for (HhppMgl hhppSubMgl : hhppList) {
                            if (!hhppTecnologiaList.contains(hhppSubMgl) && hhppSubMgl.getEstadoRegistro() == 1) {
                                hhppTecnologiaList.add(hhppSubMgl);
                            }
                        }
                    }

                } else {
                    //Obtenemos los Hhpp de la Direccion principal    
                    if (direccionDetalladaMgl.getDireccion() != null
                            && direccionDetalladaMgl.getDireccion().getDirId() != null) {

                        hhppList
                                = hhppMglManager
                                        .findHhppDireccion(direccionDetalladaMgl.getDireccion().getDirId());

                        if (hhppList != null && !hhppList.isEmpty()) {
                            for (HhppMgl hhppMgl : hhppList) {
                                if (!hhppTecnologiaList.contains(hhppMgl) && hhppMgl.getEstadoRegistro() == 1) {
                                    hhppTecnologiaList.add(hhppMgl);
                                }
                            }
                        }
                    }
                }
            }

        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener hhpp asociados " + e.getMessage(), e);
            throw new ApplicationException("Error al obtener hhpp asociados ", e);
        } catch (Exception e) {
            LOGGER.error("Error al obtener hhpp asociados " + e.getMessage(), e);
            throw new ApplicationException("Error al obtener hhpp asociados ", e);
        }
    }

    /**
     * Función que obtiene listado de Etiquetas del Hhpp
     *
     * @author Juan David Hernandez
     * @param hhppMglSeleccionado
     */
    public void obtenerDireccionDetallada(HhppMgl hhppMglSeleccionado) {
        try {
            //Validación de valor null para la subdirección
            BigDecimal sdiId = null;

            if (hhppMglSeleccionado.getSubDireccionObj() != null
                    && hhppMglSeleccionado.getSubDireccionObj().getSdiId() != null) {
                sdiId = hhppMglSeleccionado.getSubDireccionObj().getSdiId();
            }
            List<CmtDireccionDetalladaMgl> direccionDetalladaList = cmtDireccionDetalleMglFacadeLocal
                    .findDireccionDetallaByDirIdSdirId(hhppMglSeleccionado.getDireccionObj().getDirId(), sdiId);

            if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {
                direccionDetallada = direccionDetalladaList.get(0);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerDireccionDetallada. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerDireccionDetallada. ", e, LOGGER);
        }
    }

    /**
     * Función que obtiene tipo de notas
     *
     * @author Juan David Hernandez
     */
    public void obtenerTipoNotasList() throws ApplicationException {
        try {
            CmtTipoBasicaMgl tipoBasicaNotaOt;
            tipoBasicaNotaOt = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_DE_NOTAS);
            tipoNotaList = cmtBasicaMglFacadeLocal
                    .findByTipoBasica(tipoBasicaNotaOt);
        } catch (ApplicationException e) {
            LOGGER.error("Error al obtener tipos de notas " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error al obtener tipos de notas " + e.getMessage(), e);
            throw new ApplicationException("Error al obtener tipos de notas ", e);
        }
    }

    /**
     * Función que obtiene listado de Etiquetas del Hhpp
     *
     * @author Juan David Hernandez
     * @param hhppMgl
     * @param hhppAsociadosList
     */
    public void obtenerEtiquetasList(HhppMgl hhppMgl, List<HhppMgl> hhppAsociadosList) throws ApplicationException {
        try {
            if (hhppAsociadosList != null && !hhppAsociadosList.isEmpty()) {
                List<MarcasHhppMgl> etiquetas = new ArrayList();
                for (HhppMgl hhppMgl1 : hhppAsociadosList) {
                    etiquetasHhppList = new ArrayList();
                    etiquetasHhppList = marcasHhppMglFacadeLocal
                            .findMarcasHhppMglidHhpp(hhppMgl1.getHhpId());

                    if (etiquetasHhppList != null && !etiquetasHhppList.isEmpty()) {
                        if (etiquetas != null && !etiquetas.isEmpty()) {

                            for (MarcasHhppMgl etiqueta : etiquetasHhppList) {
                                List<MarcasHhppMgl> etiquetasCopiaList = copiaMarcasList(etiquetas);

                                if (etiquetasCopiaList != null && !etiquetasCopiaList.isEmpty()) {
                                    int contEtiqueta = 0;
                                    for (MarcasHhppMgl marcasHhppMgl : etiquetasCopiaList) {
                                        if (etiqueta.getMarId().equals(marcasHhppMgl.getMarId())) {
                                            contEtiqueta++;
                                        }
                                    }

                                    if (contEtiqueta == 0) {
                                        etiquetas.add(etiqueta);
                                    }
                                }
                            }

                        } else {
                            etiquetas.addAll(etiquetasHhppList);
                        }

                    }
                }

                //recorrido que se realiza para obtener todas las marcas en un String separadas por -qñ
                if (etiquetas != null && !etiquetas.isEmpty()) {
                    int cont = 1;
                    etiquetasHhpp = "";
                    for (MarcasHhppMgl eti : etiquetas) {
                        etiquetasHhpp += eti.getMarId().getMarNombre();
                        if (cont < etiquetas.size()) {
                            etiquetasHhpp += " - ";
                        }
                        cont++;
                    }
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al obtener listado de etiquetas " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error al obtener listado de etiquetas " + e.getMessage(), e);
            throw new ApplicationException("Error al obtener listado de etiquetas. ", e);
        }
    }

    public List<MarcasHhppMgl> copiaMarcasList(List<MarcasHhppMgl> etiquetasList) throws ApplicationException {
        List<MarcasHhppMgl> copiaMarcasList = new ArrayList();
        try {
            if (etiquetasList != null && !etiquetasList.isEmpty()) {
                for (MarcasHhppMgl marcasHhppMgl : etiquetasList) {
                    copiaMarcasList.add(marcasHhppMgl);
                }
            }

        } catch (RuntimeException e) {
            LOGGER.error("Error al realizar copia del listado de etiquetas", e);
            throw new ApplicationException("Error al realizar copia del listado de etiquetas. ", e);
        } catch (Exception e) {
            LOGGER.error("Error al realizar copia del listado de etiquetas" + e.getMessage(), e);
            throw new ApplicationException("Error al realizar copia del listado de etiquetas. ", e);
        }
        return copiaMarcasList;
    }

    /**
     * Función que obtiene listado de Etiquetas del Hhpp
     *
     * @author Juan David Hernandez
     * @param direccionDetallada
     */
    public void obtenerLocalidadHhpp(CmtDireccionDetalladaMgl direccionDetallada) throws ApplicationException {
        try {
            ConsultaEspecificaManager manager
                    = new ConsultaEspecificaManager();
            if (direccionDetallada != null) {
                localidadHhpp
                        = manager.obtenerLocalidadDir(direccionDetallada.getDireccion().getDirId().toString());
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al obtener listado de etiquetas. ", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error al obtener listado de etiquetas. ", e);
            throw new ApplicationException("Error al obtener listado de etiquetas. ", e);
        }
    }

    /**
     * Función que obtiene las direcciones alternas de un Hhpp
     *
     * @author Juan David Hernandez
     * @param hhppMgl
     */
    public void obtenerDireccionAlterna(HhppMgl hhppMgl) throws ApplicationException {
        try {
            if (hhppMgl != null && hhppMgl.getHhppSubEdificioObj() != null
                    && hhppMgl.getHhppSubEdificioObj().getSubEdificioId() != null
                    && hhppMgl.getHhppSubEdificioObj().getCmtCuentaMatrizMglObj() != null) {

                if (hhppMgl.getHhppSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral() != null) {

                    cuentaMatrizActiva = true;
                    telefonosCuentaMatriz = "";
                    //se concatenan los telefeonos del edificio principal de la cuenta matriz
                    if (hhppMgl.getHhppSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getTelefonoPorteria() != null
                            && !hhppMgl.getHhppSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getTelefonoPorteria().isEmpty()) {
                        telefonosCuentaMatriz += hhppMgl.getHhppSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getTelefonoPorteria() + ", ";
                    }
                    if (hhppMgl.getHhppSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getTelefonoPorteria2() != null
                            && !hhppMgl.getHhppSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getTelefonoPorteria2().isEmpty()) {
                        telefonosCuentaMatriz += hhppMgl.getHhppSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getTelefonoPorteria2();
                    }

                    List<CmtDireccionMgl> direccionAlternaList = cmtDireccionMglFacadeLocal
                            .findDireccionAlternaByCmSub(hhppMgl.getHhppSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getCmtCuentaMatrizMglObj().getCuentaMatrizId(),
                                    hhppMgl.getHhppSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getSubEdificioId());

                    if (direccionAlternaList != null) {
                        direccionAlterna = "";
                        for (CmtDireccionMgl cmtDireccionMgl : direccionAlternaList) {
                            direccionAlterna = direccionAlterna + cmtDireccionMgl.getCalleRr() + " " + cmtDireccionMgl.getUnidadRr() + "\n";
                        }
                    }

                }

            }

        } catch (ApplicationException e) {
            LOGGER.error("Error al obtener listado de direcciones alternas. " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error al obtener listado de direcciones alternas. " + e.getMessage(), e);
            throw new ApplicationException("Error al obtener listado de direcciones alternas. ", e);
        }
    }

    /**
     * Función que obtiene una cuenta matriz por hhpp
     *
     * @author Juan David Hernandez
     * @param hhppSeleccionado
     */
    public void obtenerCuentaMatriz(HhppMgl hhppSeleccionado) throws ApplicationException {
        try {
            if (hhppSeleccionado.getHhppSubEdificioObj() != null
                    && hhppSeleccionado.getHhppSubEdificioObj()
                            .getCmtCuentaMatrizMglObj() != null) {
                //Asignamos el número de la cuenta matriz
                hhppSeleccionado.setCuenta(hhppSeleccionado.getHhppSubEdificioObj()
                        .getCmtCuentaMatrizMglObj().getCuentaMatrizId().toString());
                //Asignamos el nombre de la cuenta matriz
                hhppSeleccionado.setNombreCuenta(hhppSeleccionado.getHhppSubEdificioObj()
                        .getCmtCuentaMatrizMglObj().getNombreCuenta());
            }
        } catch (RuntimeException e) {
            LOGGER.error("Error al obtener listado de etiquetas. " + e.getMessage(), e);
            throw new ApplicationException("Error al obtener listado de etiquetas. ", e);

        } catch (Exception e) {
            LOGGER.error("Error al obtener listado de etiquetas. " + e.getMessage(), e);
            throw new ApplicationException("Error al obtener listado de etiquetas. ", e);

        }
    }

    /**
     * Función que obtiene el centropoblado, ciudad y departamento
     *
     * @author Juan David Hernandez
     * @param direccionDetallada
     */
    public void obtenerCentroPobladoCiudadDepartamentoHhpp(CmtDireccionDetalladaMgl direccionDetallada) throws ApplicationException {
        try {
            if (direccionDetallada.getDireccion().getUbicacion() != null
                    && direccionDetallada.getDireccion().getUbicacion().getGpoIdObj() != null) {

                centroPobladoGpo = geograficoPoliticoMglFacadeLocal
                        .findById(direccionDetallada.getDireccion().getUbicacion().getGpoIdObj().getGpoId());

                if (centroPobladoGpo != null) {
                    ciudadGpo = geograficoPoliticoMglFacadeLocal.findById(direccionDetallada.getDireccion()
                            .getUbicacion().getGpoIdObj().getGeoGpoId());
                }

                if (ciudadGpo != null) {
                    departamentoGpo = geograficoPoliticoMglFacadeLocal.findById(ciudadGpo.getGeoGpoId());
                }
            }

        } catch (ApplicationException e) {
            LOGGER.error("Error al obtener listado de etiquetas. " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error al obtener listado de etiquetas. " + e.getMessage(), e);
            throw new ApplicationException("Error al obtener listado de etiquetas. ", e);
        }
    }

    /**
     * Función que obtiene el horario de una cuenta matriz
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void consultarHorarioRestriccion() throws ApplicationException {
        try {
            if (hhppSeleccionado != null && hhppSeleccionado.getCuenta() != null
                    && hhppSeleccionado.getHhppSubEdificioObj() != null
                    && hhppSeleccionado.getHhppSubEdificioObj()
                            .getCuentaMatrizObj() != null) {

                List<CmtHorarioRestriccionMgl> alhorarios = horarioRestriccionFacadeLocal
                        .findBySubEdificioId(hhppSeleccionado.getHhppSubEdificioObj().getSubEdificioId());
                horarioRestriccion = new ArrayList<CmtHorarioRestriccionMgl>();
                if (!alhorarios.isEmpty() && alhorarios.size() > 0) {
                    for (CmtHorarioRestriccionMgl h : alhorarios) {
                        horarioRestriccion.add(h);
                    }
                }
                horarioCargado = true;
            } else {
                horarioCargado = false;
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al obtener horario de cuenta matriz. " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error al obtener horario de cuenta matriz. " + e.getMessage(), e);
            throw new ApplicationException(e);
        }
    }

    /**
     * Función que carga el listado de Ot creadas en la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void cargarOtHhppList() throws ApplicationException {
        try {
            listInfoByPage(1);
        } catch (ApplicationException e) {
            LOGGER.error("Error al realizar cargue del listado de Ot "
                    + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error al realizar cargue del listado de Ot "
                    + e.getMessage(), e);
            throw new ApplicationException("Error al realizar cargue del listado de Ot ", e);
        }
    }

    public void cargarOrdenesSolicitudesList() throws ApplicationException {
        try {
            listInfoByPageOrdenesSolicitud(1);
        } catch (ApplicationException e) {
            LOGGER.error("Error al realizar cargue del listado de ordenes de solicitudes "
                    + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error al realizar cargue del listado de ordenes de solicitudes "
                    + e.getMessage(), e);
            throw new ApplicationException("Error al realizar cargue del listado de ordenes de solicitudes ", e);
        }
    }

    public String listInfoByPageOrdenesSolicitud(int page) throws ApplicationException {
        try {
            if (direccionDetallada != null) {
                actualSolicitudes = page;
                getPageActualSolicitudes();
                ordenesSolicitudList = solicitudFacadeLocal.consultarOrdenesDeSolicitudHHPP(page, filasPag, direccionDetallada, filtrosSolicitudes);
                if (ordenesSolicitudList != null && !ordenesSolicitudList.isEmpty()) {
                    for (Solicitud solicitud : ordenesSolicitudList) {
                        CmtTipoBasicaMgl tipoBasicaTipoAccion = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_ACCION_SOLICITUD);
                        CmtBasicaMgl tipoSolicitud = cmtBasicaMglFacadeLocal.findByBasicaCode(solicitud.getCambioDir(), tipoBasicaTipoAccion.getTipoBasicaId());
                        if (tipoSolicitud != null) {
                            solicitud.setTipoAccionSolicitudStr(tipoSolicitud.getNombreBasica());
                        }
                    }
                }
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en listInfoByPageOrdenesSolicitud. ", e, LOGGER);
        }
        return null;
    }

    /**
     * Función que realiza paginación de la tabla.
     *
     * @param page;
     * @author Juan David Hernandez
     * @return
     */
    public String listInfoByPage(int page) throws ApplicationException {
        try {
            if (direccionDetallada != null) {
                actual = page;
                getPageActual();
                BigDecimal direccionId = null;
                BigDecimal subDireccionId = null;
                if (direccionDetallada.getSubDireccion() != null
                        && direccionDetallada.getSubDireccion().getSdiId() != null) {
                    direccionId = direccionDetallada.getDireccion().getDirId();
                    subDireccionId = direccionDetallada.getSubDireccion().getSdiId();
                } else {
                    direccionId = direccionDetallada.getDireccion().getDirId();
                }

                otHhppList = otHhppMglFacadeLocal
                        .findAllOtHhppByDireccionDetalladaId(page, filasPag,
                                direccionId, subDireccionId, filtroOrdenes);

                if (otHhppList != null && !otHhppList.isEmpty()) {
                    List<OtHhppMgl> aRemover = new ArrayList<>();
                    for (OtHhppMgl otHhpp : otHhppList) {
                        List<OtHhppTecnologiaMgl> otHhppTecnologiaList = otHhppTecnologiaMglFacadeLocal
                                .findOtHhppTecnologiaByOtHhppId(otHhpp.getOtHhppId());
                        if (otHhppTecnologiaList != null && !otHhppTecnologiaList.isEmpty()) {
                            if (filtroOrdenes != null && filtroOrdenes.getTipoTecnologiaId() != null && filtroOrdenes.getTipoTecnologiaId().intValue() != 0) {
                                if (otHhppTecnologiaList.get(0).getTecnoglogiaBasicaId().getBasicaId().intValue() == filtroOrdenes.getTipoTecnologiaId().intValue()) {
                                    otHhpp.setTecnologia(otHhppTecnologiaList.get(0).getTecnoglogiaBasicaId().getNombreBasica());
                                } else {
                                    aRemover.add(otHhpp);
                                }
                            } else {
                                otHhpp.setTecnologia(otHhppTecnologiaList.get(0)
                                        .getTecnoglogiaBasicaId().getNombreBasica());
                            }
                        }
                    }
                    if (filtroOrdenes != null && filtroOrdenes.getTipoTecnologiaId() != null) {
                        otHhppList.removeAll(aRemover);
                    }
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en lista de paginación. " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en lista de paginación. " + e.getMessage(), e);
            throw new ApplicationException("Error en lista de paginación. ", e);
        }
        return null;
    }

    /**
     * Función que carga la primera página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageFirst, direccionando a la primera página ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageFirst, direccionando a la primera página ", e, LOGGER);
        }
    }

    public void pageFirstSolicitudes() {
        try {
            listInfoByPageOrdenesSolicitud(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageFirstSolicitudes, direccionando a la primera página ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageFirstSolicitudes, direccionando a la primera página ", e, LOGGER);
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     *
     * @author Juan David Hernandez
     */
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
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pagePrevious, direccionando a la página anterior ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pagePrevious, direccionando a la página anterior ", e, LOGGER);
        }
    }

    public void pagePreviousSolicitudes() {
        try {
            int totalPaginas = getTotalPaginasSolicitudes();
            int nuevaPageActual = actualSolicitudes - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPageOrdenesSolicitud(nuevaPageActual);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pagePreviousSolicitudes, direccionando a la página anterior ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pagePreviousSolicitudes, direccionando a la página anterior ", e, LOGGER);
        }
    }

    /**
     * Función que cpermite ir directamente a la página seleccionada de
     * resultados.
     *
     * @author Juan David Hernandez
     */
    public void irPagina() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = Integer.parseInt(numPagina);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (NumberFormatException ex) {
            FacesUtil.mostrarMensajeError("Error en irPagina. ", ex, LOGGER);
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en irPagina. ", ex, LOGGER);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en irPagina. ", ex, LOGGER);
        }
    }

    public void irPaginaSolicitudes() {
        try {
            int totalPaginas = getTotalPaginasSolicitudes();
            int nuevaPageActual = Integer.parseInt(numPaginaSolicitudes);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageOrdenesSolicitud(nuevaPageActual);
        } catch (NumberFormatException ex) {
            FacesUtil.mostrarMensajeError("Error en irPaginaSolicitudes. ", ex, LOGGER);
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en irPaginaSolicitudes. ", ex, LOGGER);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en irPaginaSolicitudes. ", ex, LOGGER);
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     *
     * @author Juan David Hernandez
     */
    public void pageNext() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageNext, direccionando a la siguiente página ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageNext, direccionando a la siguiente página ", e, LOGGER);
        }
    }

    public void pageNextSolicitudes() {
        try {
            int totalPaginas = getTotalPaginasSolicitudes();
            int nuevaPageActual = actualSolicitudes + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageOrdenesSolicitud(nuevaPageActual);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageNextSolicitudes, direccionando a la siguiente página ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageNextSolicitudes, direccionando a la siguiente página ", e, LOGGER);
        }
    }

    /**
     * Función que carga la última página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageLast, direccionando a la última página ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageLast, direccionando a la última página ", e, LOGGER);
        }
    }

    public void pageLastSolicitudes() {
        try {
            int totalPaginas = getTotalPaginasSolicitudes();
            listInfoByPageOrdenesSolicitud(totalPaginas);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageLastSolicitudes, direccionando a la última página ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageLastSolicitudes, direccionando a la última página ", e, LOGGER);
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     *
     * @author Juan David Hernandez
     * @return
     */
    public int getTotalPaginas() {
        try {
            int totalPaginas = 0;
            if (direccionDetallada != null) {
                BigDecimal direccionId = null;
                BigDecimal subDireccionId = null;
                if (direccionDetallada.getSubDireccion() != null
                        && direccionDetallada.getSubDireccion().getSdiId() != null) {
                    direccionId = direccionDetallada.getDireccion().getDirId();
                    subDireccionId = direccionDetallada.getSubDireccion().getSdiId();
                } else {
                    direccionId = direccionDetallada.getDireccion().getDirId();
                }
                int pageSol = otHhppMglFacadeLocal
                        .countAllOtHhppByDireccionDetalladaId(direccionId, subDireccionId, filtroOrdenes);

                totalPaginas = (int) ((pageSol % filasPag != 0)
                        ? (pageSol / filasPag) + 1 : pageSol / filasPag);
            }
            return totalPaginas;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginas, direccionando a página.  ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginas, direccionando a página.  ", e, LOGGER);

        }
        return 1;
    }

    public int getTotalPaginasSolicitudes() throws ApplicationException {
        try {
            int totalPaginas = 0;
            int pageSol = solicitudFacadeLocal.countOrdenesDeSolicitudHHPP(direccionDetallada, filtrosSolicitudes);
            totalPaginas = (int) ((pageSol % filasPag != 0)
                    ? (pageSol / filasPag) + 1 : pageSol / filasPag);
            return totalPaginas;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginasSolicitudes, direccionando a página.  ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginasSolicitudes, direccionando a página.  ", e, LOGGER);
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario en
     * los resultados.
     *
     * @author Juan David Hernandez
     * @return
     */
    public String getPageActual() {
        paginaList = new ArrayList<Integer>();
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

    public String getPageActualSolicitudes() throws ApplicationException {
        paginaListSolicitudes = new ArrayList<Integer>();
        int totalPaginas = getTotalPaginasSolicitudes();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListSolicitudes.add(i);
        }
        pageActualSolicitudes = String.valueOf(actualSolicitudes) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaSolicitudes == null) {
            numPaginaSolicitudes = "1";
        }
        numPaginaSolicitudes = String.valueOf(actualSolicitudes);

        return pageActualSolicitudes;
    }

    /**
     * Función que guardar una nota asociada a al hhpp
     *
     * @author Juan David Hernandez
     */
    public void guardarNota() {
        try {
            if (validarCamposNota()) {
                HhppMgl hhppSelected = new HhppMgl();
                hhppSelected.setHhpId(hhppSeleccionado.getHhpId());
                cmtNotasHhppVtMgl.setHhppId(hhppSelected);
                cmtNotasHhppVtMgl.setTipoNotaObj(tipoNotaSelected);
                notasHhppVtMglFacadeLocal.setUser(usuarioVT, perfilVt);
                cmtNotasHhppVtMgl
                        = notasHhppVtMglFacadeLocal
                                .crearNotSol(cmtNotasHhppVtMgl);
                if (cmtNotasHhppVtMgl != null
                        && cmtNotasHhppVtMgl.getNotasId() != null) {
                    findNotasByHhpp(hhppList);
                    tipoNotaSelected = new CmtBasicaMgl();
                    cmtNotasHhppVtMgl = new CmtNotasHhppVtMgl();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Nota agregada correctamente.", ""));
                }
                cambiarTab("NOTAS");
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al guardarNota. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al guardarNota. ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para hacer el rediccionamiento al detalle de la cuenta
     * matriz
     *
     * @author Juan David Hernandez
     * @throws
     */
    public void irCuentaMatrizDetalle() {
        try {
             //JDHT
             if (hhppSeleccionado.getCuenta() != null
                    && hhppSeleccionado.getHhppSubEdificioObj() != null
                    && hhppSeleccionado.getHhppSubEdificioObj()
                            .getCuentaMatrizObj() != null) {

                 if (hhppSeleccionado.getHhppSubEdificioObj()
                        .getCuentaMatrizObj().getEstadoRegistro() == 1) {

                CuentaMatrizBean cuentaMatrizBean = (CuentaMatrizBean) JSFUtil.getBean("cuentaMatrizBean");
                ConsultaCuentasMatricesBean consultaCuentasMatricesBean = (ConsultaCuentasMatricesBean) JSFUtil.getBean("consultaCuentasMatricesBean");
                cuentaMatrizBean.setCuentaMatriz(hhppSeleccionado.getHhppSubEdificioObj()
                            .getCuentaMatrizObj());
                consultaCuentasMatricesBean.obtenerTipoTecnologiaList();
                consultaCuentasMatricesBean.tecnologiasCoberturaCuentaMatriz();

                consultaCuentasMatricesBean.setTecnologiasCables(cuentaMatrizFacadeLocal.getTecnologiasInst(hhppSeleccionado.getHhppSubEdificioObj()
                            .getCuentaMatrizObj(), true));
                consultaCuentasMatricesBean.setTecnologiasVarias(cuentaMatrizFacadeLocal.getTecnologiasInst(hhppSeleccionado.getHhppSubEdificioObj()
                            .getCuentaMatrizObj(), false));
                hhppSeleccionado.getHhppSubEdificioObj()
                            .getCuentaMatrizObj().setCoberturasList(consultaCuentasMatricesBean.getTecnologiasList());
                List<CmtSubEdificioMgl> subEdificiosMgl = cmtSubEdificioMglFacadeLocal.findSubEdificioByCuentaMatriz(hhppSeleccionado.getHhppSubEdificioObj()
                            .getCuentaMatrizObj());
                if (subEdificiosMgl.size() > 0) {
                    CmtCuentaMatrizMgl cmtCuentaMatrizMgl = hhppSeleccionado.getHhppSubEdificioObj()
                            .getCuentaMatrizObj();
                    cmtCuentaMatrizMgl.setListCmtSubEdificioMgl(subEdificiosMgl);
                    cuentaMatrizBean.setCuentaMatriz(cmtCuentaMatrizMgl);
                } else {
                    CmtCuentaMatrizMgl cmtCuentaMatrizMgl = hhppSeleccionado.getHhppSubEdificioObj()
                            .getCuentaMatrizObj();
                    cuentaMatrizBean.setCuentaMatriz(cmtCuentaMatrizMgl);
                    }

                    FacesUtil.navegarAPagina("/view/MGL/CM/tabs/hhpp.jsf");
                } else {
                    String msn = "Ocurrio un error cargando la cuenta matriz que se desea redireccionar. Intente de nuevo por favor. ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msn, ""));
                }
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irCuentaMatrizDetalle. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irCuentaMatrizDetalle. ", e, LOGGER);
        }
    }

    public boolean verificaEstadoCCMM() {
        if (hhppSeleccionado != null && hhppSeleccionado.getCuenta() != null
                    && hhppSeleccionado.getHhppSubEdificioObj() != null
                    && hhppSeleccionado.getHhppSubEdificioObj()
                            .getCuentaMatrizObj() != null) {

                if (hhppSeleccionado.getHhppSubEdificioObj()
                        .getCuentaMatrizObj().getEstadoRegistro() == 1) {
                    return true;
                }
        }
        return false;
    }

    /**
     * Función utilizada para hacer el rediccionamiento a la consulta de hhpp
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void irConsultaHhpp() {
        try {
            FacesUtil.navegarAPagina("/view/MGL/VT/moduloHhpp/consultaHhpp.jsf");
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irConsultaHhpp. al redireccionar a pantalla de consulta de Hhpp. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irConsultaHhpp. al redireccionar a pantalla de consulta de Hhpp. ", e, LOGGER);
        }
    }

    /**
     * Función utilizada obtener la auditoria (bitacora) de Hhpp
     *
     * @author Juan David Hernandez
     * @param hhppAsociadosList
     */
    public void obtenerBitacoraHhpp(List<HhppMgl> hhppAsociadosList) throws ApplicationException {
        try {
            if (hhppAsociadosList != null && !hhppAsociadosList.isEmpty()) {
                List<AuditoriaDto> auditoriaHhppList;
                listAuditoria = new ArrayList();
                for (HhppMgl hhppMgl : hhppAsociadosList) {
                    auditoriaHhppList = hhppMglFacadeLocal.construirAuditoria(hhppMgl);
                    listAuditoria.addAll(auditoriaHhppList);
                }
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            LOGGER.error("Error al carga información de Bitacora hhpp. ", e);
            throw new ApplicationException("Error al carga información de Bitacora hhpp. ", e);
        } catch (Exception e) {
            LOGGER.error("Error al carga información de Bitacora hhpp. ", e);
            throw new ApplicationException("Error al carga información de Bitacora hhpp. ", e);
        }
    }

    /**
     * Función utilizada redireccionar al detalle de la bitacora de la
     * tecnologia seleccionada
     *
     * @author Juan David Hernandez
     * @param estadoTecnologiaHhppDtoSelected
     */
    public void verBitacoraTecnologia(EstadoTecnologiaHhppDto estadoTecnologiaHhppDtoSelected) {
        try {
            if (estadoTecnologiaHhppDtoSelected != null
                    && estadoTecnologiaHhppDtoSelected.getHhppMglSelected() != null) {
                obtenerBitacoraDireccion(direccionDetallada);
                showBitacoraDireccion();
            } else {
                String msn = "Ocurrio un error con la tecnologia seleccionada."
                        + " Intente nuevamente por favor";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msn, ""));
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en verBitacoraTecnologia, al carga bitacora por tecnologia. ", e, LOGGER);
        }
    }

    /**
     * Función utilizada obtener la auditoria (bitacora) de Direccion
     *
     * @author Juan David Hernandez
     * @param direccionDetallada
     */
    public void obtenerBitacoraDireccion(CmtDireccionDetalladaMgl direccionDetallada) {
        try {
            if (direccionDetallada != null) {
                if (direccionDetallada.getSubDireccion() != null) {
                    List<AuditoriaDto> auditoriaSubDireccionList;
                    listAuditoriaDireccion = new ArrayList();
                    auditoriaSubDireccionList = subDireccionMglFacadeLocal
                            .construirAuditoria(direccionDetallada.getSubDireccion());
                    listAuditoriaDireccion.addAll(auditoriaSubDireccionList);
                } else {
                    if (direccionDetallada.getDireccion() != null) {
                        List<AuditoriaDto> auditoriaDireccionList;
                        listAuditoriaDireccion = new ArrayList();
                        auditoriaDireccionList = direccionMglFacadeLocal
                                .construirAuditoria(direccionDetallada.getDireccion());
                        listAuditoriaDireccion.addAll(auditoriaDireccionList);
                    }
                }

            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerBitacoraDireccion. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerBitacoraDireccion. ", e, LOGGER);
        }
    }

    /**
     * Función utilizada obtener los curries de la direccion por centro poblado
     *
     * @author Juan David Hernandez
     * @param centroPobladoGpo
     */
    public void obtenerCurriesDireccion(GeograficoPoliticoMgl centroPobladoGpo) {
        try {
            if (centroPobladoGpo != null && centroPobladoGpo.getGpoId() != null) {
                coberturaEntregaList = new ArrayList();
                coberturaEntregaList
                        = cmtCoberturaEntregaMglFacadeLocal
                                .findCoberturaEntregaListByCentroPobladoId(centroPobladoGpo.getGpoId());
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerCurriesDireccion. obtener los curries de la direccion por centro poblado. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerCurriesDireccion. obtener los curries de la direccion por centro poblado. ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para mostrar/ocultar panel de etiquetas de hhpp.
     *
     * @author Juan David Hernandez
     */
    public void mostrarOcultarEtiquetas() {
        if (showEtiquetas) {
            showEtiquetas = false;
        } else {
            showEtiquetas = true;
        }
    }

    /**
     * Función utilizada para guardar una descripción del predio en las notas.
     *
     * @author Juan David Hernandez
     */
    public void guardarDescripcionPredio() {
        try {
            if (validarDescripcionPredio()) {
                CmtNotasHhppVtMgl descripcionPredioHhpp = new CmtNotasHhppVtMgl();
                HhppMgl hhppSelected = new HhppMgl();
                CmtBasicaMgl tipoNota = new CmtBasicaMgl();
                tipoNota.setBasicaId(new BigDecimal(339));

                hhppSelected.setHhpId(hhppSeleccionado.getHhpId());
                descripcionPredioHhpp.setHhppId(hhppSelected);
                descripcionPredioHhpp.setTipoNotaObj(tipoNota);
                descripcionPredioHhpp.setNota(this.descripcionPredio);
                descripcionPredioHhpp.setDescripcion("Descripción Predio");
                notasHhppVtMglFacadeLocal.setUser(usuarioVT, perfilVt);
                descripcionPredioHhpp
                        = notasHhppVtMglFacadeLocal
                                .crearNotSol(descripcionPredioHhpp);
                if (descripcionPredioHhpp != null
                        && descripcionPredioHhpp.getNotasId() != null) {

                    this.descripcionPredio = "";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "La descripción del predio fue guardada en Notas del Hhpp correctamente.", ""));
                }
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en guardarDescripcionPredio. guardar una descripción del predio en las notas. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en guardarDescripcionPredio. guardar una descripción del predio en las notas. ", e, LOGGER);
        }
    }

    /**
     * Función que valida los datos necesarios para guardar una descrición del
     * predio
     *
     * @author Juan David Hernandez return true si no se encuentran datos vacios
     * @return
     */
    public boolean validarCamposNota() {
        try {
            if (cmtNotasHhppVtMgl == null) {
                String msn = "Ha ocurrido un error intentando guardar una nota."
                        + " Intente más tarde por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;
            } else {
                if (cmtNotasHhppVtMgl.getDescripcion() == null
                        || cmtNotasHhppVtMgl.getDescripcion().isEmpty()) {
                    String msn = "Ingrese una descripción a la nota por favor.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, msn,
                                    ""));
                    return false;
                } else {
                    if (tipoNotaSelected.getBasicaId() == null) {
                        String msn = "Ingrese un tipo de nota por favor.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN, msn,
                                        ""));
                        return false;
                    } else {
                        if (cmtNotasHhppVtMgl.getNota() == null
                                || cmtNotasHhppVtMgl.getNota().isEmpty()) {
                            String msn = "Ingrese la nota que desea guardar por "
                                    + "favor.";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msn, ""));
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            }
        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Error en validarCamposNota. ", e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validarCamposNota. ", e, LOGGER);
            return false;
        }
    }

    /**
     * Función utilizada obtener la auditoria (bitacora) de Hhpp
     *
     * @author Juan David Hernandez
     * @param hhppAsociadosList
     */
    public void obtenerHistoricoClientes(List<HhppMgl> hhppAsociadosList) {
        try {
            listInfoByPageHistorico(1, hhppAsociadosList);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerHistoricoClientes. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerHistoricoClientes. ", e, LOGGER);
        }
    }

    /**
     * Función que obtiene las notas de la ot creada.
     *
     * @author Juan David Hernandez
     * @param hhppAsociadosList
     */
    public void findNotasByHhpp(List<HhppMgl> hhppAsociadosList) {
        try {
            List<CmtNotasHhppVtMgl> notasList;
            notasHhppList = new ArrayList();
            if (hhppAsociadosList != null && !hhppAsociadosList.isEmpty()) {
                for (HhppMgl hhppMgl : hhppAsociadosList) {
                    notasList = notasHhppVtMglFacadeLocal
                            .findNotasByHhppId(hhppMgl.getHhpId());
                    if (notasList != null && !notasList.isEmpty()) {
                        notasHhppList.addAll(notasList);
                    }
                }
            }
            comentarioList = new ArrayList();
            cmtNotasHhppVtMgl = new CmtNotasHhppVtMgl();
        } catch (ApplicationException e) {
            LOGGER.error("Error al obtener las notas de la solicitud ", e);
            String msn = "Error al obtener las notas de la solicitud.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msn + e.getMessage(), ""));
        } catch (Exception e) {
            LOGGER.error("Error al obtener las notas de la solicitud ", e);
            String msn = "Error al obtener las notas de la solicitud.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msn + e.getMessage(), ""));
        }
    }

    /**
     * Función que valida los datos necesarios para guardar una nota esten
     * ingresados
     *
     * @author Juan David Hernandez return true si no se encuentran datos vacios
     * @return
     */
    public boolean validarDescripcionPredio() {
        try {
            if (descripcionPredio == null || descripcionPredio.isEmpty()) {
                String msn = "Debe ingresar una descripción del predio por favor. ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;
            } else {
                if (descripcionPredio.length() > 4000) {
                    String msn = "El texto de la descripción no debe superar los 40000 caracteres por favor.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msn, ""));
                    return false;
                }
            }
            return true;

        } catch (RuntimeException e) {
            LOGGER.error("Error validando descripcion predio", e);
            String msnError = "Error validando descripcion predio ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError
                            + e.getMessage(), ""));
            return false;
        } catch (Exception e) {
            LOGGER.error("Error validando descripcion predio", e);
            String msnError = "Error validando descripcion predio ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError
                            + e.getMessage(), ""));
            return false;
        }
    }

    /**
     * Función que guardar una comentario a una nota asociada a la ot
     *
     * @author Juan David Hernandez
     */
    public void guardarComentarioNota() {
        try {
            CmtNotasHhppDetalleVtMgl notaComentarioMgl = new CmtNotasHhppDetalleVtMgl();
            notaComentarioMgl.setNota(notaComentarioStr);
            notaComentarioMgl.setNotaHhpp(cmtNotasHhppVtMgl);
            notasHhppDetalleVtMglFacadeLocal.setUser(usuarioVT, perfilVt);
            notaComentarioMgl
                    = notasHhppDetalleVtMglFacadeLocal.create(notaComentarioMgl);
            if (notaComentarioMgl.getNotasDetalleId() != null) {
                notaComentarioStr = "";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Comentario añadido correctamente.", ""));
            }
            comentarioList.add(notaComentarioMgl);
            cmtNotasHhppVtMgl.setComentarioList(comentarioList);
            mostarComentario(cmtNotasHhppVtMgl);
        } catch (ApplicationException ex) {
            LOGGER.error("Error al guardar comentario en la nota seleccionada. ", ex);
            String msn = "Error al guardar comentario en la nota seleccionada.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msn + ex.getMessage(), ""));
        } catch (Exception ex) {
            LOGGER.error("Error al guardar comentario en la nota seleccionada. ", ex);
            String msn = "Error al guardar comentario en la nota seleccionada.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msn + ex.getMessage(), ""));
        }

    }

    /**
     * Función utilizada para mostrar el panel que permite agregar un comentario
     *
     * @author Juan David Hernandez
     * @param nota
     */
    public void mostarComentario(CmtNotasHhppVtMgl nota) {
        cmtNotasHhppVtMgl = nota;
        tipoNotaSelected = nota.getTipoNotaObj();
        comentarioList = null;
        comentarioList = cmtNotasHhppVtMgl.getComentarioList();
    }

    public void limpiarCamposNota() {
        cmtNotasHhppVtMgl = new CmtNotasHhppVtMgl();
        tipoNotaSelected = new CmtBasicaMgl();
    }

    public void limpiarDescripcionPredio() {
        descripcionPredio = "";
    }

    /**
     * Función que realiza paginación de la tabla.
     *
     * @param page;
     * @author Juan David Hernandez
     * @param hhppAsociadosList
     * @return
     */
    public String listInfoByPageHistorico(int page, List<HhppMgl> hhppAsociadosList) throws ApplicationException {
        try {
            if (hhppAsociadosList != null && !hhppAsociadosList.isEmpty()) {
                List<String> historicoList = new ArrayList();
                historicoClientesList = new ArrayList();

                for (HhppMgl hhppMgl : hhppAsociadosList) {

                    //obtiene los suscriptores de la tabla de auditoria de tecnologiaHabilitada                    
                    historicoList = hhppAuditoriaMglFacadeLocal
                            .findHistoricoSuscriptor(hhppMgl.getHhpId(),
                                    page, filasPag);

                    //agrega el suscriptor actual que esta en la tabla tecnologia habilitada
                    if (hhppMgl.getSuscriptor() != null && !hhppMgl.getSuscriptor().isEmpty()
                            && !hhppMgl.getSuscriptor().equals("NA")) {

                        HhppAuditoriaMgl hhppAuditoriaMgl = new HhppAuditoriaMgl();
                        hhppAuditoriaMgl.setSuscriptor(hhppMgl.getSuscriptor());

                        //Validacion con suscriptores del mismo hhpp pero diferente tecnologia 
                        int conta = 0;
                        if (historicoClientesList != null && !historicoClientesList.isEmpty()) {
                            for (HhppAuditoriaMgl suscriptor : historicoClientesList) {
                                if (hhppAuditoriaMgl.getSuscriptor().equalsIgnoreCase(suscriptor.getSuscriptor())) {
                                    conta++;
                                }
                            }
                        }

                        //  ----------
                        //Validacion sobre listado historico del hhpp recorrido
                        List<HhppAuditoriaMgl> copiaSuscriptorList = copiaHistoricoClientes(historicoList);
                        int conta2 = 0;
                        if (copiaSuscriptorList != null && !copiaSuscriptorList.isEmpty()
                                && historicoList != null && !historicoList.isEmpty()) {
                            for (HhppAuditoriaMgl suscriptor : copiaSuscriptorList) {
                                conta2 = 0;
                                if (hhppAuditoriaMgl.getSuscriptor().equalsIgnoreCase(suscriptor.getSuscriptor())) {
                                    conta2++;
                                }
                            }
                        }
                        if (conta == 0 && conta2 == 0) {
                            historicoClientesList.add(hhppAuditoriaMgl);
                        }
                    }

                    //---------------------
                    //Validacion sobre listado historico del hhpp recorrido
                    List<HhppAuditoriaMgl> copiaSuscriptorList = copiaHistoricoClientes(historicoList);
                    int conta = 0;
                    if (copiaSuscriptorList != null && !copiaSuscriptorList.isEmpty() && historicoList != null && !historicoList.isEmpty()
                            && historicoClientesList != null && !historicoClientesList.isEmpty()) {
                        for (HhppAuditoriaMgl suscriptor : copiaSuscriptorList) {
                            conta = 0;

                            for (HhppAuditoriaMgl hhppAuditoriaMgl : historicoClientesList) {

                                if (hhppAuditoriaMgl.getSuscriptor().equalsIgnoreCase(suscriptor.getSuscriptor())) {
                                    conta++;
                                }
                            }
                            if (conta == 0) {
                                historicoClientesList.add(suscriptor);
                            }
                        }
                    } else {
                        if (copiaSuscriptorList != null && !copiaSuscriptorList.isEmpty()
                                && historicoList != null && !historicoList.isEmpty()) {
                            historicoClientesList.addAll(copiaSuscriptorList);
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en listInfoByPageHistorico.  Al realizar paginación de la tabla. " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en listInfoByPageHistorico.  Al realizar paginación de la tabla. " + e.getMessage(), e);
            throw new ApplicationException("Error en istInfoByPageHistorico.  Al realizar paginación de la tabla. ", e);
        }
        return null;
    }

    public List<HhppAuditoriaMgl> copiaHistoricoClientes(List<String> historico) {
        try {
            List<HhppAuditoriaMgl> historicoClient = new ArrayList();
            if (historico != null && !historico.isEmpty()) {
                for (String hhppAuditoriaMgl : historico) {
                    HhppAuditoriaMgl suscriptorAuditoria = new HhppAuditoriaMgl();
                    suscriptorAuditoria.setSuscriptor(hhppAuditoriaMgl);
                    historicoClient.add(suscriptorAuditoria);
                }
            }
            return historicoClient;

        } catch (RuntimeException e) {
            LOGGER.error("Error en xxx. copiaHistoricoClientes. " + e.getMessage(), e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en xxx. copiaHistoricoClientes. " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Función que carga la primera página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageFirstHistorico() {
        try {
            listInfoByPageHistorico(1, hhppList);
        } catch (ApplicationException ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError + ex.getMessage(), ""));
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     *
     * @author Juan David Hernandez
     */
    public void pagePreviousHistorico() {
        try {
            int totalPaginas = getTotalPaginasHistorico();
            int nuevaPageActual = actualHistorico - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPageHistorico(nuevaPageActual, hhppList);
        } catch (ApplicationException ex) {
            LOGGER.error("Error direccionando a la página anterior", ex);
            String msnError = "Error direccionando a la página anterior";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError + ex.getMessage(), ""));
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la página anterior", ex);
            String msnError = "Error direccionando a la página anterior";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que cpermite ir directamente a la página seleccionada de
     * resultados.
     *
     * @author Juan David Hernandez
     */
    public void irPaginaHistorico() {
        try {
            int totalPaginas = getTotalPaginasHistorico();
            int nuevaPageActual = Integer.parseInt(numPaginaHistorico);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageHistorico(nuevaPageActual, hhppList);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en irPaginaHistorico. Al ir directamente a la página seleccionada de resultados ", e, LOGGER);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irPaginaHistorico. Al ir directamente a la página seleccionada de resultados ", e, LOGGER);
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     *
     * @author Juan David Hernandez
     */
    public void pageNextHistorico() {
        try {
            int totalPaginas = getTotalPaginasHistorico();
            int nuevaPageActual = actualHistorico + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageHistorico(nuevaPageActual, hhppList);
        } catch (ApplicationException ex) {
            LOGGER.error("Error direccionando a la siguiente página", ex);
            String msnError = "Error direccionando a la siguiente página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError + ex.getMessage(), ""));
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la siguiente página", ex);
            String msnError = "Error direccionando a la siguiente página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la última página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageLastHistorico() {
        try {
            int totalPaginas = getTotalPaginasHistorico();
            listInfoByPageHistorico(totalPaginas, hhppList);
        } catch (ApplicationException ex) {
            LOGGER.error("Error direccionando a la última página", ex);
            String msnError = "Error direccionando a la última página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError + ex.getMessage(), ""));
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la última página", ex);
            String msnError = "Error direccionando a la última página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     *
     * @author Juan David Hernandez
     * @return
     */
    public int getTotalPaginasHistorico() {
        try {
            int totalPaginas = 0;
            int pageSol = hhppAuditoriaMglFacadeLocal
                    .countHistoricoSuscriptor(hhppSeleccionado.getHhpId());

            totalPaginas = (int) ((pageSol % filasPag != 0)
                    ? (pageSol / filasPag) + 1 : pageSol / filasPag);
            return totalPaginas;
        } catch (ApplicationException ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError + ex.getMessage(), ""));
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError + ex.getMessage(), ""));
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario en
     * los resultados.
     *
     * @author Juan David Hernandez
     * @return
     */
    public String getPageActualHistorico() {
        paginaHistoricoList = new ArrayList<Integer>();
        int totalPaginas = getTotalPaginasHistorico();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaHistoricoList.add(i);
        }
        pageActualHistorico = String.valueOf(actualHistorico) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaHistorico == null) {
            numPaginaHistorico = "1";
        }
        numPaginaHistorico = String.valueOf(actualHistorico);

        return pageActualHistorico;
    }

    /**
     * Función que renderiza la pestalla de General
     *
     * @author Juan David Hernandez
     */
    public void showGeneralTab() throws ApplicationException {
        obtenerEtiquetasList(hhppSeleccionado, hhppTecnologiaList);
        generalTab = true;
        ordenesTab = false;
        notasTab = false;
        infoTecnicaTab = false;
        horarioTab = false;
        historicoClientesTab = false;
        bitacoraTab = false;
        bitacoraDireccionTab = false;
        curriesTab = false;
        marcasTab = false;
    }

    /**
     * Función que renderiza la pestalla de Ordenes
     *
     * @author Juan David Hernandez
     */
    public void showOrdenesTab() {
        generalTab = false;
        ordenesTab = true;
        notasTab = false;
        infoTecnicaTab = false;
        horarioTab = false;
        historicoClientesTab = false;
        bitacoraTab = false;
        bitacoraDireccionTab = false;
        curriesTab = false;
        marcasTab = false;
    }

    /**
     * Función que renderiza la pestaña de Notas
     *
     * @author Juan David Hernandez
     */
    public void showNotasTab() {
        generalTab = false;
        ordenesTab = false;
        notasTab = true;
        infoTecnicaTab = false;
        horarioTab = false;
        historicoClientesTab = false;
        bitacoraTab = false;
        bitacoraDireccionTab = false;
        curriesTab = false;
        marcasTab = false;
    }

    /**
     * Función que renderiza la pestaña de InfoTecnica
     *
     * @author Juan David Hernandez
     */
    public void showInfoTecnicaTab() {
        generalTab = false;
        ordenesTab = false;
        notasTab = false;
        infoTecnicaTab = true;
        horarioTab = false;
        historicoClientesTab = false;
        bitacoraTab = false;
        bitacoraDireccionTab = false;
        curriesTab = false;
        marcasTab = false;
    }

    /**
     * Función que renderiza la pestaña de InfoTecnica
     *
     * @author Juan David Hernandez
     */
    public void showHorarioTab() {
        generalTab = false;
        ordenesTab = false;
        notasTab = false;
        infoTecnicaTab = false;
        horarioTab = true;
        historicoClientesTab = false;
        bitacoraTab = false;
        bitacoraDireccionTab = false;
        curriesTab = false;
        marcasTab = false;
    }

    /**
     * Función que renderiza la pestaña de Historico Clientes
     *
     * @author Juan David Hernandez
     */
    public void showHistoricoClientesTab() {
        generalTab = false;
        ordenesTab = false;
        notasTab = false;
        infoTecnicaTab = false;
        horarioTab = false;
        historicoClientesTab = true;
        bitacoraTab = false;
        curriesTab = false;
        marcasTab = false;
    }

    /**
     * Función que renderiza la pestaña de bitacora de hhpp
     *
     * @author Juan David Hernandez
     */
    public void showBitacora() {
        generalTab = false;
        ordenesTab = false;
        notasTab = false;
        infoTecnicaTab = false;
        horarioTab = false;
        historicoClientesTab = false;
        bitacoraTab = true;
        bitacoraDireccionTab = false;
        curriesTab = false;
        marcasTab = false;
    }

    /**
     * Función que renderiza la pestaña de bitacora de direccion
     *
     * @author Juan David Hernandez
     */
    public void showBitacoraDireccion() {
        generalTab = false;
        ordenesTab = false;
        notasTab = false;
        infoTecnicaTab = false;
        horarioTab = false;
        historicoClientesTab = false;
        bitacoraTab = false;
        bitacoraDireccionTab = true;
        curriesTab = false;
        marcasTab = false;
    }

    /**
     * Función que renderiza la pestaña de curries de direccion
     *
     * @author Juan David Hernandez
     */
    public void showCurriesDireccion() {
        generalTab = false;
        ordenesTab = false;
        notasTab = false;
        infoTecnicaTab = false;
        horarioTab = false;
        historicoClientesTab = false;
        bitacoraTab = false;
        bitacoraDireccionTab = false;
        curriesTab = true;
        marcasTab = false;
    }

    /**
     * Función que renderiza la pestaña de curries de direccion
     *
     * @author Juan David Hernandez
     */
    public void showMarcas() {
        generalTab = false;
        ordenesTab = false;
        notasTab = false;
        infoTecnicaTab = false;
        horarioTab = false;
        historicoClientesTab = false;
        bitacoraTab = false;
        bitacoraDireccionTab = false;
        curriesTab = false;
        marcasTab = true;
    }

    public String getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(String selectedTab) {
        this.selectedTab = selectedTab;
    }

    public boolean isGeneralTab() {
        return generalTab;
    }

    public void setGeneralTab(boolean generalTab) {
        this.generalTab = generalTab;
    }

    public boolean isOrdenesTab() {
        return ordenesTab;
    }

    public void setOrdenesTab(boolean ordenesTab) {
        this.ordenesTab = ordenesTab;
    }

    public boolean isNotasTab() {
        return notasTab;
    }

    public void setNotasTab(boolean notasTab) {
        this.notasTab = notasTab;
    }

    public boolean isInfoTecnicaTab() {
        return infoTecnicaTab;
    }

    public void setInfoTecnicaTab(boolean infoTecnicaTab) {
        this.infoTecnicaTab = infoTecnicaTab;
    }

    public boolean isHistoricoClientesTab() {
        return historicoClientesTab;
    }

    public void setHistoricoClientesTab(boolean historicoClientesTab) {
        this.historicoClientesTab = historicoClientesTab;
    }

    public boolean isBitacoraTab() {
        return bitacoraTab;
    }

    public void setBitacoraTab(boolean bitacoraTab) {
        this.bitacoraTab = bitacoraTab;
    }

    public boolean isHorarioTab() {
        return horarioTab;
    }

    public void setHorarioTab(boolean horarioTab) {
        this.horarioTab = horarioTab;
    }

    public List<CmtNotasHhppVtMgl> getNotasHhppList() {
        return notasHhppList;
    }

    public void setNotasHhppList(List<CmtNotasHhppVtMgl> notasHhppList) {
        this.notasHhppList = notasHhppList;
    }

    public List<CmtBasicaMgl> getTipoNotaList() {
        return tipoNotaList;
    }

    public void setTipoNotaList(List<CmtBasicaMgl> tipoNotaList) {
        this.tipoNotaList = tipoNotaList;
    }

    public CmtBasicaMgl getTipoNotaSelected() {
        return tipoNotaSelected;
    }

    public void setTipoNotaSelected(CmtBasicaMgl tipoNotaSelected) {
        this.tipoNotaSelected = tipoNotaSelected;
    }

    public CmtNotasHhppVtMgl getCmtNotasHhppVtMgl() {
        return cmtNotasHhppVtMgl;
    }

    public void setCmtNotasHhppVtMgl(CmtNotasHhppVtMgl cmtNotasHhppVtMgl) {
        this.cmtNotasHhppVtMgl = cmtNotasHhppVtMgl;
    }

    public String getNotaComentarioStr() {
        return notaComentarioStr;
    }

    public void setNotaComentarioStr(String notaComentarioStr) {
        this.notaComentarioStr = notaComentarioStr;
    }

    public HhppMgl getHhppSeleccionado() {
        return hhppSeleccionado;
    }

    public void setHhppSeleccionado(HhppMgl hhppSeleccionado) {
        this.hhppSeleccionado = hhppSeleccionado;
    }

    public String getDireccionAlterna() {
        return direccionAlterna;
    }

    public void setDireccionAlterna(String direccionAlterna) {
        this.direccionAlterna = direccionAlterna;
    }

    public String getEtiquetasHhpp() {
        return etiquetasHhpp;
    }

    public void setEtiquetasHhpp(String etiquetasHhpp) {
        this.etiquetasHhpp = etiquetasHhpp;
    }

    public CmtCuentaMatrizMgl getCuentaMatrizHhpp() {
        return cuentaMatrizHhpp;
    }

    public void setCuentaMatrizHhpp(CmtCuentaMatrizMgl cuentaMatrizHhpp) {
        this.cuentaMatrizHhpp = cuentaMatrizHhpp;
    }

    public String getLocalidadHhpp() {
        return localidadHhpp;
    }

    public void setLocalidadHhpp(String localidadHhpp) {
        this.localidadHhpp = localidadHhpp;
    }

    public List<AuditoriaDto> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<AuditoriaDto> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }

    public ArrayList<CmtHorarioRestriccionMgl> getHorarioRestriccion() {
        return horarioRestriccion;
    }

    public void setHorarioRestriccion(ArrayList<CmtHorarioRestriccionMgl> horarioRestriccion) {
        this.horarioRestriccion = horarioRestriccion;
    }

    public boolean isHorarioCargado() {
        return horarioCargado;
    }

    public void setHorarioCargado(boolean horarioCargado) {
        this.horarioCargado = horarioCargado;
    }

    public List<HhppAuditoriaMgl> getHistoricoClientesList() {
        return historicoClientesList;
    }

    public void setHistoricoClientesList(List<HhppAuditoriaMgl> historicoClientesList) {
        this.historicoClientesList = historicoClientesList;
    }

    public String getInicioPagina() {
        return inicioPagina;
    }

    public void setInicioPagina(String inicioPagina) {
        this.inicioPagina = inicioPagina;
    }

    public String getAnteriorPagina() {
        return anteriorPagina;
    }

    public void setAnteriorPagina(String anteriorPagina) {
        this.anteriorPagina = anteriorPagina;
    }

    public String getFinPagina() {
        return finPagina;
    }

    public void setFinPagina(String finPagina) {
        this.finPagina = finPagina;
    }

    public List<Integer> getPaginaHistoricoList() {
        return paginaHistoricoList;
    }

    public void setPaginaHistoricoList(List<Integer> paginaHistoricoList) {
        this.paginaHistoricoList = paginaHistoricoList;
    }

    public int getActualHistorico() {
        return actualHistorico;
    }

    public void setActualHistorico(int actualHistorico) {
        this.actualHistorico = actualHistorico;
    }

    public String getNumPaginaHistorico() {
        return numPaginaHistorico;
    }

    public void setNumPaginaHistorico(String numPaginaHistorico) {
        this.numPaginaHistorico = numPaginaHistorico;
    }

    public String getSiguientePagina() {
        return siguientePagina;
    }

    public void setSiguientePagina(String siguientePagina) {
        this.siguientePagina = siguientePagina;
    }

    public String getEspacio() {
        return espacio;
    }

    public void setEspacio(String espacio) {
        this.espacio = espacio;
    }

    public boolean isShowEtiquetas() {
        return showEtiquetas;
    }

    public void setShowEtiquetas(boolean showEtiquetas) {
        this.showEtiquetas = showEtiquetas;
    }

    public String getDescripcionPredio() {
        return descripcionPredio;
    }

    public void setDescripcionPredio(String descripcionPredio) {
        this.descripcionPredio = descripcionPredio;
    }

    public List<OtHhppMgl> getOtHhppList() {
        return otHhppList;
    }

    public void setOtHhppList(List<OtHhppMgl> otHhppList) {
        this.otHhppList = otHhppList;
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

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    public CmtDireccionDetalladaMgl getDireccionDetallada() {
        return direccionDetallada;
    }

    public void setDireccionDetallada(CmtDireccionDetalladaMgl direccionDetallada) {
        this.direccionDetallada = direccionDetallada;
    }

    public List<CmtNotasHhppDetalleVtMgl> getComentarioList() {
        return comentarioList;
    }

    public void setComentarioList(List<CmtNotasHhppDetalleVtMgl> comentarioList) {
        this.comentarioList = comentarioList;
    }

    public List<CmtBasicaMgl> getTecnologiaBasicaList() {
        return tecnologiaBasicaList;
    }

    public void setTecnologiaBasicaList(List<CmtBasicaMgl> tecnologiaBasicaList) {
        this.tecnologiaBasicaList = tecnologiaBasicaList;
    }

    public List<EstadoTecnologiaHhppDto> getEstadoTecnologiaHhppDtoList() {
        return estadoTecnologiaHhppDtoList;
    }

    public void setEstadoTecnologiaHhppDtoList(List<EstadoTecnologiaHhppDto> estadoTecnologiaHhppDtoList) {
        this.estadoTecnologiaHhppDtoList = estadoTecnologiaHhppDtoList;
    }

    public List<TecArchivosCambioEstrato> getTecArchivosCambioEstratos() {
        return tecArchivosCambioEstratos;
    }

    public void setTecArchivosCambioEstratos(List<TecArchivosCambioEstrato> tecArchivosCambioEstratos) {
        this.tecArchivosCambioEstratos = tecArchivosCambioEstratos;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

    public String getDireccionHhpp() {
        return direccionHhpp;
    }

    public void setDireccionHhpp(String direccionHhpp) {
        this.direccionHhpp = direccionHhpp;
    }

    public GeograficoPoliticoMgl getDepartamentoGpo() {
        return departamentoGpo;
    }

    public void setDepartamentoGpo(GeograficoPoliticoMgl departamentoGpo) {
        this.departamentoGpo = departamentoGpo;
    }

    public List<HhppMgl> getHhppTecnologiaList() {
        return hhppTecnologiaList;
    }

    public void setHhppTecnologiaList(List<HhppMgl> hhppTecnologiaList) {
        this.hhppTecnologiaList = hhppTecnologiaList;
    }

    public String getDireccionAntiguaHhpp() {
        return direccionAntiguaHhpp;
    }

    public void setDireccionAntiguaHhpp(String direccionAntiguaHhpp) {
        this.direccionAntiguaHhpp = direccionAntiguaHhpp;
    }

    public GeograficoPoliticoMgl getCiudadGpo() {
        return ciudadGpo;
    }

    public void setCiudadGpo(GeograficoPoliticoMgl ciudadGpo) {
        this.ciudadGpo = ciudadGpo;
    }

    public GeograficoPoliticoMgl getCentroPobladoGpo() {
        return centroPobladoGpo;
    }

    public void setCentroPobladoGpo(GeograficoPoliticoMgl centroPobladoGpo) {
        this.centroPobladoGpo = centroPobladoGpo;
    }

    public boolean isHhppExistente() {
        return hhppExistente;
    }

    public void setHhppExistente(boolean hhppExistente) {
        this.hhppExistente = hhppExistente;
    }

    public String getNivelSocioEcoDireccion() {
        return nivelSocioEcoDireccion;
    }

    public void setNivelSocioEcoDireccion(String nivelSocioEcoDireccion) {
        this.nivelSocioEcoDireccion = nivelSocioEcoDireccion;
    }

    public String getTelefonosCuentaMatriz() {
        return telefonosCuentaMatriz;
    }

    public void setTelefonosCuentaMatriz(String telefonosCuentaMatriz) {
        this.telefonosCuentaMatriz = telefonosCuentaMatriz;
    }

    public boolean isBitacoraDireccionTab() {
        return bitacoraDireccionTab;
    }

    public void setBitacoraDireccionTab(boolean bitacoraDireccionTab) {
        this.bitacoraDireccionTab = bitacoraDireccionTab;
    }

    public List<AuditoriaDto> getListAuditoriaDireccion() {
        return listAuditoriaDireccion;
    }

    public void setListAuditoriaDireccion(List<AuditoriaDto> listAuditoriaDireccion) {
        this.listAuditoriaDireccion = listAuditoriaDireccion;
    }

    public boolean isCurriesTab() {
        return curriesTab;
    }

    public void setCurriesTab(boolean curriesTab) {
        this.curriesTab = curriesTab;
    }

    public boolean isMarcasTab() {
        return marcasTab;
    }

    public void setMarcasTab(boolean marcasTab) {
        this.marcasTab = marcasTab;
    }

    public List<CmtCoberturaEntregaMgl> getCoberturaEntregaList() {
        return coberturaEntregaList;
    }

    public void setCoberturaEntregaList(List<CmtCoberturaEntregaMgl> coberturaEntregaList) {
        this.coberturaEntregaList = coberturaEntregaList;
    }

    public String getCurrier() {
        return currier;
    }

    public void setCurrier(String currier) {
        this.currier = currier;
    }

    public String listInfoByPageCover(int page) {
        try {
            int firstResult = 0;
            if (page > 1) {
                firstResult = (ConstantsCM.PAGINACION_OCHO_FILAS * (page - 1));
            }
            cmtFiltroCoberturasDto = cmtCoberturaEntregaMglFacadeLocal.findCoberturaEntregaListByCentroPoblado(
                    paramsCover, ConstantsCM.PAGINACION_DATOS, firstResult, ConstantsCM.PAGINACION_OCHO_FILAS);
            cmtCoberturaEntregaMglList = cmtFiltroCoberturasDto.getListaCmtCoberturaEntregaMgl();
            habilitaObj = !cmtCoberturaEntregaMglList.isEmpty();
            if (cmtCoberturaEntregaMglList.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "No se encontraron resultados para la consulta.", ""));
                return "";
            }

            actualCover = page;
        } catch (ApplicationException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "No se encontraron resultados para la consulta:.." + ex.getMessage(), ""));
            LOGGER.error("Error en listInfoByPageCover " + ex.getMessage(), ex);
            return "";
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "No se encontraron resultados para la consulta:.." + ex.getMessage(), ""));
            LOGGER.error("Error en listInfoByPageCover " + ex.getMessage(), ex);
            return "";
        }
        return "";
    }

    public void buscar() {
        try {
            paramsCover = new HashMap<String, Object>();
            paramsCover.put("gpoId", centroPobladoGpo.getGpoId());
            paramsCover.put("operador", tipoInvOperador);
            paramsCover.put("tipoInventario", tipoInvCovertura);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al armar la consulta de CM.", ""));
            LOGGER.error("Error al armar la consulta de CM. " + e.getMessage(), e);
        }
    }

    public void findByFiltroCover() {
        pageFirstCover();
    }

    public void pageFirstCover() {
        try {
            buscar();
            listInfoByPageCover(1);
        } catch (RuntimeException ex) {
            LOGGER.error("Error en findByFiltroCover " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error en findByFiltroCover " + ex.getMessage(), ex);
        }
    }

    public void pageFirstEtiqueta() {
        try {
            buscar();
            listInfoByPageCover(1);
        } catch (RuntimeException ex) {
            LOGGER.error("Error en findByFiltroCover " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error en findByFiltroCover " + ex.getMessage(), ex);
        }
    }

    public void pagePreviousCover() {
        try {
            int totalPaginas = getTotalPaginasCover();
            int nuevaPageActual = actualCover - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPageCover(nuevaPageActual);
        } catch (RuntimeException ex) {
            LOGGER.error("Error en pagePreviousCover " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error en pagePreviousCover " + ex.getMessage(), ex);
        }
    }

    public String getPageActualCover() {
        paginaListCover = new ArrayList<Integer>();
        int totalPaginas = getTotalPaginasCover();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListCover.add(i);
        }
        pageActualCover = String.valueOf(actualCover) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaCover == null) {
            numPaginaCover = "1";
        }
        numPaginaCover = String.valueOf(actualCover);
        return pageActualCover;
    }

    public void irPaginaCover() {
        try {
            int totalPaginas = getTotalPaginasCover();
            int nuevaPageActual = Integer.parseInt(numPaginaCover);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageCover(nuevaPageActual);
        } catch (NumberFormatException ex) {
            LOGGER.error("Error en irPaginaCover " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error en irPaginaCover " + ex.getMessage(), ex);
        }
    }

    public void pageNextCover() {
        try {
            int totalPaginas = getTotalPaginasCover();
            int nuevaPageActual = actualCover + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageCover(nuevaPageActual);
        } catch (RuntimeException ex) {
            LOGGER.error("Error en pageNextCover " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error en pageNextCover " + ex.getMessage(), ex);
        }
    }

    public void pageLastCover() {
        try {
            int totalPaginas = getTotalPaginasCover();
            listInfoByPageCover(totalPaginas);
        } catch (RuntimeException ex) {
            LOGGER.error("Error en pageLastCover " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error en pageLastCover " + ex.getMessage(), ex);
        }
    }

    public int getTotalPaginasCover() {
        try {
            CmtFiltroCoberturasDto filtroCoberturasDto;
            filtroCoberturasDto = cmtCoberturaEntregaMglFacadeLocal.
                    findCoberturaEntregaListByCentroPoblado(paramsCover, ConstantsCM.PAGINACION_CONTAR, 0, 0);
            Long count = filtroCoberturasDto.getNumRegistros();
            int totalPaginas = (int) ((count % ConstantsCM.PAGINACION_OCHO_FILAS != 0)
                    ? (count / ConstantsCM.PAGINACION_OCHO_FILAS) + 1
                    : count / ConstantsCM.PAGINACION_OCHO_FILAS);
            return totalPaginas;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginasCover. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginasCover. ", e, LOGGER);
        }
        return 1;
    }

    public void detallarSolicitud(Solicitud solicitudSeleccionada) {
        EstadoSolicitudHhppBean estadoSolicitud
                = (EstadoSolicitudHhppBean) JSFUtil.getSessionBean(EstadoSolicitudHhppBean.class);
        estadoSolicitud.detallarSolicitud(solicitudSeleccionada);
    }

    public boolean isPintarPaginadoCover() {
        return pintarPaginadoCover;
    }

    public void setPintarPaginadoCover(boolean pintarPaginadoCover) {
        this.pintarPaginadoCover = pintarPaginadoCover;
    }

    public List<Integer> getPaginaListCover() {
        return paginaListCover;
    }

    public void setPaginaListCover(List<Integer> paginaListCover) {
        this.paginaListCover = paginaListCover;
    }

    public String getNumPaginaCover() {
        return numPaginaCover;
    }

    public void setNumPaginaCover(String numPaginaCover) {
        this.numPaginaCover = numPaginaCover;
    }

    public String getTipoInvCovertura() {
        return tipoInvCovertura;
    }

    public void setTipoInvCovertura(String tipoInvCovertura) {
        this.tipoInvCovertura = tipoInvCovertura;
    }

    public String getTipoInvOperador() {
        return tipoInvOperador;
    }

    public void setTipoInvOperador(String tipoInvOperador) {
        this.tipoInvOperador = tipoInvOperador;
    }

    public List<CmtCoberturaEntregaMgl> getCmtCoberturaEntregaMglList() {
        return cmtCoberturaEntregaMglList;
    }

    public void setCmtCoberturaEntregaMglList(List<CmtCoberturaEntregaMgl> cmtCoberturaEntregaMglList) {
        this.cmtCoberturaEntregaMglList = cmtCoberturaEntregaMglList;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public List<TipoHhppMgl> getListaTipoHhpp() {
        return listaTipoHhpp;
    }

    public void setListaTipoHhpp(List<TipoHhppMgl> listaTipoHhpp) {
        this.listaTipoHhpp = listaTipoHhpp;
    }

    public String getTipoHHppSelected() {
        return tipoHHppSelected;
    }

    public void setTipoHHppSelected(String tipoHHppSelected) {
        this.tipoHHppSelected = tipoHHppSelected;
    }

    public void etiquetaAdicionar() {
        crudMarcasHhppActivo = "A";
        listaMarcas = new ArrayList<MarcasMgl>();
        observacionesMarcas = null;
        marcaSeleccionada = BigDecimal.ZERO;
        tecnologiaHhppSeleccionada = BigDecimal.ZERO;
        this.setActivarTodasMarcasHhpp(false);//desactivo la marca
    }

    public void etiquetaCrear() {
        MarcasMgl asignarMarcaNueva = null;
        HhppMgl asignaTecnologiaNueva = null;
        if (marcaSeleccionada == null
                || marcaSeleccionada.equals(BigDecimal.ZERO)) {
            String msg = "Seleccione una Marca.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, msg, ""));
            return;
        } else {
            for (MarcasMgl temporal : listaMarcas) {
                if (marcaSeleccionada.compareTo(temporal.getMarId()) == 0) {
                    asignarMarcaNueva = temporal;
                }
            }
            if (asignarMarcaNueva == null) {
                String msg = "No encuentra la marca seleccionada en el listado.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, msg, ""));
                return;
            }
        }
        if (!activarTodasMarcasHhpp && (tecnologiaHhppSeleccionada == null
                || tecnologiaHhppSeleccionada.equals(BigDecimal.ZERO))) {
            String msg = "Seleccione una tecnologia.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, msg, ""));
            return;
        } else {
            if (!activarTodasMarcasHhpp) {
                for (HhppMgl temporal : hhppTecnologiaList) {
                    if (tecnologiaHhppSeleccionada.compareTo(temporal.getHhpId()) == 0) {
                        asignaTecnologiaNueva = temporal;
                    }
                }
                if (asignaTecnologiaNueva == null) {
                    //las tecnologias se seleccionan como hhpp
                    String msg = "No encuentra la tecnologia seleccionada en el listado.";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, msg, ""));
                    return;
                }
            }
        }
        if (observacionesMarcas == null || observacionesMarcas.isEmpty()) {
            String msg = "Ingresar observaciones. ";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, msg, ""));
            return;
        }
        if (!activarTodasMarcasHhpp) {
            try {
                List<MarcasHhppMgl> listaHhppMarcas = marcasHhppMglFacadeLocal.buscarXHhppMarca(
                        tecnologiaHhppSeleccionada, marcaSeleccionada);
                if (listaHhppMarcas.size() != 0) {
                    String msg = "La marca y tecnologia que intenta crear ya existe. ";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, msg, ""));
                    return;
                }
            } catch (ApplicationException ae) {
                FacesUtil.mostrarMensajeError(ae.getMessage(), ae);
                return;
            }
            MarcasHhppMgl marcaHomePassNuevo = new MarcasHhppMgl();
            marcaHomePassNuevo.setHhpp(asignaTecnologiaNueva);
            marcaHomePassNuevo.setMarId(asignarMarcaNueva);
            marcaHomePassNuevo.setObservaciones(observacionesMarcas);
            marcaHomePassNuevo.setMhhFechaCreacion(new Date());
            marcaHomePassNuevo.setMhhUsuarioCreacion(usuarioVT);
            marcaHomePassNuevo.setPerfilCreacion(perfilVt);
            marcaHomePassNuevo.setEstadoRegistro(estadoRegistroMarca.intValue());
            try {
                marcasHhppMglFacadeLocal.create(marcaHomePassNuevo);
            } catch (ApplicationException ae) {
                FacesUtil.mostrarMensajeError(ae.getMessage(), ae);
                return;
            }
        } else {
            for (HhppMgl temporal : hhppTecnologiaList) {
                List<MarcasHhppMgl> listaHhppMarcas;
                try {
                    listaHhppMarcas = marcasHhppMglFacadeLocal.buscarXHhppMarca(
                            temporal.getHhpId(), marcaSeleccionada);
                } catch (ApplicationException ae) {
                    String msg = "La tecnologia " + temporal.getNodId().getNodTipo().getNombreBasica()
                            + " error en la busqueda.";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, msg, ""));
                    continue;
                }
                if (listaHhppMarcas.isEmpty()) {
                    MarcasHhppMgl marcaHomePassNuevo = new MarcasHhppMgl();
                    marcaHomePassNuevo.setHhpp(temporal);
                    marcaHomePassNuevo.setMarId(asignarMarcaNueva);
                    marcaHomePassNuevo.setObservaciones(observacionesMarcas);
                    marcaHomePassNuevo.setMhhFechaCreacion(new Date());
                    marcaHomePassNuevo.setMhhUsuarioCreacion(usuarioVT);
                    marcaHomePassNuevo.setPerfilCreacion(perfilVt);
                    marcaHomePassNuevo.setEstadoRegistro(estadoRegistroMarca.intValue());
                    try {
                        marcasHhppMglFacadeLocal.create(marcaHomePassNuevo);
                    } catch (ApplicationException ae) {
                        String msg = "La tecnologia " + temporal.getNodId().getNodTipo().getNombreBasica()
                                + " no puede ser creada.";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_ERROR, msg, ""));
                        continue;
                    }
                } else {
                    String msg = "La tecnologia " + temporal.getNodId().getNodTipo().getNombreBasica()
                            + " ya existe con la marca seleccionada.";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, msg, ""));
                }
            }
        }
        String msg = "Etiqueta creada satisfactoriamente.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, msg, ""));
        etiquetaConsultar();
    }

    public void etiquetaConsultar() {
        try {
            crudMarcasHhppActivo = "C";
            listaMarcasHomePass = new ArrayList<MarcasHhppMgl>(
                    marcasHhppMglFacadeLocal.findMarcasHhppMglidHhppSinEstado(hhppTecnologiaList));

            //copia a listado que se despliega en pantalla           
            listaMarcasHomePassAux = copiarEtiquetasOriginalList(listaMarcasHomePass);
            listInfoByPageEtiquetas(1);

             //se extraen los ids de todos los tecnologias asociadas al hhpp para filtros
            if (hhppTecnologiaList != null && !hhppTecnologiaList.isEmpty()) {
                idHhppList = new ArrayList();
                for (HhppMgl hhppAsociados : hhppTecnologiaList) {
                    idHhppList.add(hhppAsociados.getHhpId());
                }
            }
        } catch (ApplicationException ae) {
            FacesUtil.mostrarMensajeError(ae.getMessage(), ae);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error generico en la consulta de etiquetas. ", e);
        }
    }

    public List<MarcasHhppMgl> copiarEtiquetasOriginalList(List<MarcasHhppMgl> listaMarcasHomePass){
        try {
            List<MarcasHhppMgl> marcasListAux = new ArrayList();
            //se realiza copia de una lista original a una lista auxiliar
            if(listaMarcasHomePass != null && !listaMarcasHomePass.isEmpty()){
                for (MarcasHhppMgl listaMarcasHomePas : listaMarcasHomePass) {
                    marcasListAux.add(listaMarcasHomePas.clone());
                }
            }
            return marcasListAux;
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error generico en la copia de etiquetas. ", e);
            return null;
        }
    }

    public void buscarMarcasTecnologia() {
        try {
            listaMarcas.clear();
            if (tecnologiaHhppSeleccionada == null && !activarTodasMarcasHhpp) {
                return;
            }
            List<HhppMgl> homePassSeleccionado = new ArrayList<HhppMgl>();
            ArrayList<MarcasHhppMgl> listaMarcasExcluir = new ArrayList<MarcasHhppMgl>();
            List<MarcasMgl> listaMarcasTot = new ArrayList<MarcasMgl>(marcasMglFacade.findAll());
            if (activarTodasMarcasHhpp) {
                listaMarcas = listaMarcasTot;
                tecnologiaHhppSeleccionada = null;
                return;//retorna todas las marcas solo cuando escojen todas las tecnologias
            }
            for (HhppMgl temporal : hhppTecnologiaList) {
                if (temporal.getHhpId().compareTo(tecnologiaHhppSeleccionada) == 0) {
                    homePassSeleccionado.add(temporal);
                    break;//tam pronto encuentre la tecnologia se sale del ciclo
                }
            }
            if (homePassSeleccionado != null) {//toma las marcas del home pass seleccionado
                //listado de marcas que ya tienen marca
                listaMarcasExcluir = new ArrayList<MarcasHhppMgl>(
                        marcasHhppMglFacadeLocal.findMarcasHhppMglidHhppSinEstado(homePassSeleccionado));
                for (MarcasMgl tem : listaMarcasTot) {
                    boolean insertar = true;
                    for (MarcasHhppMgl temporal : listaMarcasExcluir) {
                        if (tem.getMarId().compareTo(temporal.getMarId().getMarId()) == 0) {
                            insertar = false;
                        }
                    }
                    if (insertar) {
                        //inserta las marcas que no encuentre para la tecnologia seleccionada o las aun disponibles
                        listaMarcas.add(tem);
                    }
                }
            }
        } catch (ApplicationException ae) {
            String msg = "Busqueda de marcas para la tecnologia seleccionada presenta errores.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, msg, ""));
        }
    }

    public void etiquetaModificar() {
        if (marcaHomePassSeleccionado == null) {
            String msg = "Marca home pass seleccionada presenta errores en la carga.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, msg, ""));
            return;
        }
        crudMarcasHhppActivo = "M";
        listaMarcas = new ArrayList<MarcasMgl>();
        tecnologiaHhppSeleccionada = marcaHomePassSeleccionado.getHhpp().getHhpId();
        buscarMarcasTecnologia();
        marcaSeleccionada = marcaHomePassSeleccionado.getMarId().getMarId();
        listaMarcas.add(marcaHomePassSeleccionado.getMarId());//a las posibles marcas le añado la actual marca seleccionada
        observacionesMarcas = "";
        estadoRegistroMarca = BigDecimal.valueOf(marcaHomePassSeleccionado.getEstadoRegistro());
        this.setActivarTodasMarcasHhpp(false);//desactivo la marca
    }

     /**
     * Metodo para consulta de geografico_politico con filtro
     * @author Juan David Hernandez Torres
     */
    public void findByEtiqueta(){
        try {
           filtroMarcasMglDto = new FiltroMarcasMglDto();
           //ids cargados al entrar a la pestaña
           filtroMarcasMglDto.setHhppIdList(idHhppList);

           //se consulta el id de la marca ya que en la pantalla se filtra por código
            if(codigoMarcaFiltro != null && !codigoMarcaFiltro.isEmpty()){
                  filtroMarcasMglDto.setCodigoEtiqueta(codigoMarcaFiltro.trim().toUpperCase());
            }

            //se consulta el nombre de la etiqueta
            if(nombreEtiquetaFiltro != null && !nombreEtiquetaFiltro.isEmpty()){
                  filtroMarcasMglDto.setNombreEtiqueta(nombreEtiquetaFiltro.trim());
            }

            //se consulta la tecnología
            if(tecnologiaFiltro != null && !tecnologiaFiltro.isEmpty()){
                  filtroMarcasMglDto.setTecnologia(tecnologiaFiltro.trim());
            }

            //se consulta el estado
            if(estadoFiltro != null && !estadoFiltro.isEmpty()){
                  filtroMarcasMglDto.setEstado(estadoFiltro.trim());
            }else{
                filtroMarcasMglDto.setEstado(null);
            }
            listaMarcasHomePass = marcasHhppMglFacadeLocal.findResultadosFiltro(filtroMarcasMglDto);
            listInfoByPageEtiquetas(1);

        } catch (Exception e) {
            String msg = "Error al filtrar marcas";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, msg, ""));
        }
    }

    public void etiquetaModificarConfirmacion() {
        MarcasMgl asignarMarcaNueva = null;
        HhppMgl asignaTecnologiaNueva = null;
        if (marcaSeleccionada == null || marcaSeleccionada.equals(BigDecimal.ZERO)) {
            String msg = "Seleccione una Marca.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, msg, ""));
            return;
        } else {
            for (MarcasMgl temporal : listaMarcas) {
                if (marcaSeleccionada.compareTo(temporal.getMarId()) == 0) {
                    asignarMarcaNueva = temporal;
                }
            }
            if (asignarMarcaNueva == null) {
                String msg = "No encuentra la marca seleccionada en el listado.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, msg, ""));
                return;
            }
        }
        if (!activarTodasMarcasHhpp && (tecnologiaHhppSeleccionada == null
                || tecnologiaHhppSeleccionada.equals(BigDecimal.ZERO))) {
            String msg = "Seleccione una tecnologia.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, msg, ""));
            return;
        } else {
            if (!activarTodasMarcasHhpp) {
                for (HhppMgl temporal : hhppTecnologiaList) {
                    if (tecnologiaHhppSeleccionada.compareTo(temporal.getHhpId()) == 0) {
                        asignaTecnologiaNueva = temporal;
                    }
                }
                if (asignaTecnologiaNueva == null) {
                    String msg = "No encuentra la tecnologia seleccionada en el listado.";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, msg, ""));
                    return;
                }
            }
        }
        if (observacionesMarcas == null || observacionesMarcas.isEmpty()) {
            String msg = "Ingresar observaciones. ";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, msg, ""));
            return;
        }
        if (!activarTodasMarcasHhpp) {
            marcaHomePassSeleccionado.setHhpp(asignaTecnologiaNueva);//la tecnologia no puede cambiar
            marcaHomePassSeleccionado.setObservaciones(observacionesMarcas);
            marcaHomePassSeleccionado.setMhhFechaModificacion(new Date());
            marcaHomePassSeleccionado.setMhhUsuarioModificacion(usuarioVT);
            marcaHomePassSeleccionado.setPerfilEdicion(perfilVt);
            marcaHomePassSeleccionado.setMarId(asignarMarcaNueva);//cambia la marca se elimina RR el 
            marcaHomePassSeleccionado.setEstadoRegistro(estadoRegistroMarca.intValue());
            //si lo inactiva elimina RR si lo activa Crea RR
            try {
                marcasHhppMglFacadeLocal.update(marcaHomePassSeleccionado);
            } catch (ApplicationException ae) {
                String msg = "Error:DetalleHhppBean:etiquetaModificarConfirmacion: " + ae.getMessage();
                FacesUtil.mostrarMensajeError(msg, ae);
            }
        } else {
            for (HhppMgl temporal : hhppTecnologiaList) {
                List<MarcasHhppMgl> listaHhppMarcas;
                try {
                    listaHhppMarcas = marcasHhppMglFacadeLocal.buscarXHhppMarca(
                            temporal.getHhpId(), marcaSeleccionada);

                } catch (ApplicationException ae) {
                    String msg = "La tecnologia " + temporal.getNodId().getNodTipo().getNombreBasica()
                            + " error en la busqueda.";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, msg, ""));
                    continue;
                }

            }
        }
        String msg = "Etiqueta Actualizada Satisfactoriamente.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, msg, ""));
        etiquetaConsultar();
    }

    public void etiquetaLog() {
        try {
            crudMarcasHhppActivo = "L";
            List<TecMarcasTecnologiaHabAud> listaMarcaHhppAud = tmthaFacade.obtenerLog(marcaHomePassSeleccionado);
            listaMarcasHomePassLog = new ArrayList<TecMarcasTecnologiaHabAudDto>();
            String dataAnterior = "";
            TecMarcasTecnologiaHabAud temporal = new TecMarcasTecnologiaHabAud();
            temporal.setFechaCreacion(marcaHomePassSeleccionado.getMhhFechaCreacion());
            temporal.setUsuarioCreacion(marcaHomePassSeleccionado.getMhhUsuarioCreacion());
            temporal.setFechaEdicion(marcaHomePassSeleccionado.getMhhFechaModificacion());
            temporal.setUsuarioEdicion(marcaHomePassSeleccionado.getMhhUsuarioModificacion());
            String estadoActualMarca = marcaHomePassSeleccionado.getEstadoRegistro() == 0 ? "inactivo" : "activo";
            String dataNuevo = "Marca: " + marcaHomePassSeleccionado.getMarId().getMarNombre()
                    + "\nTecnologia: " + marcaHomePassSeleccionado.getHhpp().getNodId().getNodTipo().getNombreBasica()
                    + "\nEstado: " + estadoActualMarca;
            TecMarcasTecnologiaHabAudDto nuevo = new TecMarcasTecnologiaHabAudDto();
            nuevo.setJustificacion(marcaHomePassSeleccionado.getObservaciones());
            nuevo.setTecmarca(temporal);
            for (TecMarcasTecnologiaHabAud temporal2 : listaMarcaHhppAud) {
                String dataEstado = "";
                if (temporal2.getEstadoRegistro() != null) {
                    dataEstado = temporal2.getEstadoRegistro().compareTo(Short.valueOf("0")) == 0 ? "inactivo" : "activo";
                }
                dataAnterior = "Marca: " + temporal2.getMarcasId().getMarNombre()
                        + "\nTecnologia: " + temporal2.getTecnologiaHabilitadaId().getNodId().getNodTipo().getNombreBasica()
                        + "\nEstado: " + dataEstado;
                nuevo.setDataNueva(dataNuevo);
                nuevo.setDataAnterior(dataAnterior);
                listaMarcasHomePassLog.add(nuevo);
                dataNuevo = dataAnterior;
                nuevo = new TecMarcasTecnologiaHabAudDto();
                nuevo.setJustificacion(temporal2.getObservaciones());
                nuevo.setTecmarca(temporal2);
            }
            nuevo.setDataNueva(dataNuevo);
            nuevo.setDataAnterior("");
            listaMarcasHomePassLog.add(nuevo);//ultimo registro leido
        } catch (ApplicationException ex) {
            String msg = "Error en el Log de Marcas; no se pueden traer los datos para el Home Pass";
            FacesUtil.mostrarMensajeError(msg, ex);
        }
    }

        /**
     * Función que realiza paginación de la tabla. de las unidades asociadas al
     * predio
     *
     * @param page;
     * @author Juan David Hernandez
     */
    public void listInfoByPageEtiquetas(int page) {
        try {
            actualEtiquetas = page;
            getPageActualEtiquetas();
            int firstResult = 0;
            if (page > 1) {
                firstResult = (filasPagEtiquetas * (page - 1));
            }

            //Obtenemos el rango de la paginación
            int maxResult;
            if ((firstResult + filasPagEtiquetas) > listaMarcasHomePass.size()) {
                maxResult = listaMarcasHomePass.size();
            } else {
                maxResult = (firstResult + filasPagEtiquetas);
            }            
            /*Obtenemos los objetos que se encuentran en el rango de la paginación
             y los guardarmos en la lista que se va a desplegar en pantalla*/
            listaMarcasHomePassAux = new ArrayList<MarcasHhppMgl>();
            for (int i = firstResult; i < maxResult; i++) {
                listaMarcasHomePassAux.add(listaMarcasHomePass.get(i));
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en lista de paginación" + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que carga la primera página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageFirstEtiquetas() {
        try {
            listInfoByPageEtiquetas(1);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a la primera página" + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     *
     * @author Juan David Hernandez
     */
    public void pagePreviousEtiquetas() {
        try {
            int totalPaginas = getTotalPaginasEtiquetas();
            int nuevaPageActual = actualEtiquetas - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPageEtiquetas(nuevaPageActual);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a la página anterior" + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que permite ir directamente a la página seleccionada 
     *
     * @author Juan David Hernandez
     */
    public void irPaginaEtiquetas() {
        try {
            int totalPaginas = getTotalPaginasEtiquetas();
            int nuevaPageActual = Integer.parseInt(numPaginaEtiquetas);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageEtiquetas(nuevaPageActual);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que carga la siguiente página de resultados. 
     *
     * @author Juan David Hernandez
     */
    public void pageNextEtiquetas() {
        try {
            int totalPaginas = getTotalPaginasEtiquetas();
            int nuevaPageActual = actualEtiquetas + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageEtiquetas(nuevaPageActual);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a la siguiente página" + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que carga la última página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageLastEtiquetas() {
        try {
            int totalPaginas = getTotalPaginasEtiquetas();
            listInfoByPageEtiquetas(totalPaginas);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a la última página" + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados de las
     * unidades asociadas al predio
     *
     * @author Juan David Hernandez
     * @return
     */
    public int getTotalPaginasEtiquetas() {
        try {
            //obtener la lista original para conocer su tamaño total                 
            int pageSol = listaMarcasHomePass.size();
            return (int) ((pageSol % filasPagEtiquetas != 0)
                    ? (pageSol / filasPagEtiquetas) + 1 : pageSol / filasPagEtiquetas);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a la primera página" + e.getMessage() , e, LOGGER);
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario en
     * los resultados.
     *
     * @author Juan David Hernandez
     * @return
     */
    public String getPageActualEtiquetas() {
        paginaListEtiquetas = new ArrayList<Integer>();
        int totalPaginas = getTotalPaginasEtiquetas();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListEtiquetas.add(i);
        }
        pageActualEtiquetas = String.valueOf(actualEtiquetas) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaEtiquetas == null) {
            numPaginaEtiquetas = "1";
        }
        numPaginaEtiquetas = String.valueOf(actualEtiquetas);
        return pageActualEtiquetas;
    }

    public String getActivarTodasMarcasHhppString() {
        return activarTodasMarcasHhppString;
    }

    public void setActivarTodasMarcasHhppString(String activarTodasMarcasHhppString) {
        this.activarTodasMarcasHhppString = activarTodasMarcasHhppString;
    }

    public boolean isActivarTodasMarcasHhpp() {
        return activarTodasMarcasHhpp;
    }

    public void setActivarTodasMarcasHhpp(boolean activarTodasMarcasHhpp) {
        this.activarTodasMarcasHhpp = activarTodasMarcasHhpp;
        if (this.activarTodasMarcasHhpp) {
            activarTodasMarcasHhppString = "true";
        } else {
            activarTodasMarcasHhppString = "false";
        }
    }

    public MarcasHhppMgl getMarcaHomePassSeleccionado() {
        return marcaHomePassSeleccionado;
    }

    public void setMarcaHomePassSeleccionado(MarcasHhppMgl marcaHomePassSeleccionado) {
        this.marcaHomePassSeleccionado = marcaHomePassSeleccionado;
    }

    public List<MarcasHhppMgl> getListaMarcasHomePass() {
        return listaMarcasHomePass;
    }

    public void setListaMarcasHomePass(ArrayList<MarcasHhppMgl> listaMarcasHomePass) {
        this.listaMarcasHomePass = listaMarcasHomePass;
    }

    public String getCrudMarcasHhppActivo() {
        return crudMarcasHhppActivo;
    }

    public void setCrudMarcasHhppActivo(String crudMarcasHhppActivo) {
        this.crudMarcasHhppActivo = crudMarcasHhppActivo;
    }

    public List<MarcasMgl> getListaMarcas() {
        return listaMarcas;
    }

    public void setListaMarcas(List<MarcasMgl> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }

    public BigDecimal getMarcaSeleccionada() {
        return marcaSeleccionada;
    }

    public void setMarcaSeleccionada(BigDecimal marcaSeleccionada) {
        this.marcaSeleccionada = marcaSeleccionada;
    }

    public BigDecimal getTecnologiaHhppSeleccionada() {
        return tecnologiaHhppSeleccionada;
    }

    public void setTecnologiaHhppSeleccionada(BigDecimal tecnologiaHhppSeleccionada) {
        this.tecnologiaHhppSeleccionada = tecnologiaHhppSeleccionada;
    }

    public BigDecimal getEstadoRegistroMarca() {
        return estadoRegistroMarca;
    }

    public void setEstadoRegistroMarca(BigDecimal estadoRegistroMarca) {
        this.estadoRegistroMarca = estadoRegistroMarca;
    }

    public String getObservacionesMarcas() {
        return observacionesMarcas;
    }

    public void setObservacionesMarcas(String observacionesMarcas) {
        this.observacionesMarcas = observacionesMarcas;
    }

    public HtmlDataTable getTablaMarcaHhpp() {
        return tablaMarcaHhpp;
    }

    public void setTablaMarcaHhpp(HtmlDataTable tablaMarcaHhpp) {
        this.tablaMarcaHhpp = tablaMarcaHhpp;
    }

    public List<Solicitud> getOrdenesSolicitudList() {
        return ordenesSolicitudList;
    }

    public void setOrdenesSolicitudList(List<Solicitud> ordenesSolicitudList) {
        this.ordenesSolicitudList = ordenesSolicitudList;
    }

    public int getActualSolicitudes() {
        return actualSolicitudes;
    }

    public void setActualSolicitudes(int actualSolicitudes) {
        this.actualSolicitudes = actualSolicitudes;
    }

    public void setPageActualSolicitudes(String pageActualSolicitudes) {
        this.pageActualSolicitudes = pageActualSolicitudes;
    }

    public List<Integer> getPaginaListSolicitudes() {
        return paginaListSolicitudes;
    }

    public void setPaginaListSolicitudes(List<Integer> paginaListSolicitudes) {
        this.paginaListSolicitudes = paginaListSolicitudes;
    }

    public String getNumPaginaSolicitudes() {
        return numPaginaSolicitudes;
    }

    public void setNumPaginaSolicitudes(String numPaginaSolicitudes) {
        this.numPaginaSolicitudes = numPaginaSolicitudes;
    }

    public Solicitud getFiltrosSolicitudes() {
        return filtrosSolicitudes;
    }

    public void setFiltrosSolicitudes(Solicitud filtrosSolicitudes) {
        this.filtrosSolicitudes = filtrosSolicitudes;
    }

    public List<CmtBasicaMgl> getTiposSolitudesList() {
        return tiposSolitudesList;
    }

    public void setTiposSolitudesList(List<CmtBasicaMgl> tiposSolitudesList) {
        this.tiposSolitudesList = tiposSolitudesList;
    }

    public List<CmtBasicaMgl> getTiposTecnologiaList() {
        return tiposTecnologiaList;
    }

    public void setTiposTecnologiaList(List<CmtBasicaMgl> tiposTecnologiaList) {
        this.tiposTecnologiaList = tiposTecnologiaList;
    }

    public OtHhppMgl getFiltroOrdenes() {
        return filtroOrdenes;
    }

    public void setFiltroOrdenes(OtHhppMgl filtroOrdenes) {
        this.filtroOrdenes = filtroOrdenes;
    }

    public List<TipoOtHhppMgl> getTipoOtList() {
        return tipoOtList;
    }

    public void setTipoOtList(List<TipoOtHhppMgl> tipoOtList) {
        this.tipoOtList = tipoOtList;
    }

    public List<TecMarcasTecnologiaHabAudDto> getListaMarcasHomePassLog() {
        return listaMarcasHomePassLog;
    }

    public void setListaMarcasHomePassLog(List<TecMarcasTecnologiaHabAudDto> listaMarcasHomePassLog) {
        this.listaMarcasHomePassLog = listaMarcasHomePassLog;
    }

    public String getCodigoMarcaFiltro() {
        return codigoMarcaFiltro;
    }

    public void setCodigoMarcaFiltro(String codigoMarcaFiltro) {
        this.codigoMarcaFiltro = codigoMarcaFiltro;
    }

    public String getNombreEtiquetaFiltro() {
        return nombreEtiquetaFiltro;
    }

    public void setNombreEtiquetaFiltro(String nombreEtiquetaFiltro) {
        this.nombreEtiquetaFiltro = nombreEtiquetaFiltro;
    }

    public String getTecnologiaFiltro() {
        return tecnologiaFiltro;
    }

    public void setTecnologiaFiltro(String tecnologiaFiltro) {
        this.tecnologiaFiltro = tecnologiaFiltro;
    }

    public String getEstadoFiltro() {
        return estadoFiltro;
    }

    public void setEstadoFiltro(String estadoFiltro) {
        this.estadoFiltro = estadoFiltro;
    }

    public String getAuditoriaFiltro() {
        return auditoriaFiltro;
    }

    public void setAuditoriaFiltro(String auditoriaFiltro) {
        this.auditoriaFiltro = auditoriaFiltro;
    }

    public String getNumPaginaEtiquetas() {
        return numPaginaEtiquetas;
    }

    public void setNumPaginaEtiquetas(String numPaginaEtiquetas) {
        this.numPaginaEtiquetas = numPaginaEtiquetas;
    }

    public List<Integer> getPaginaListEtiquetas() {
        return paginaListEtiquetas;
    }

    public void setPaginaListEtiquetas(List<Integer> paginaListEtiquetas) {
        this.paginaListEtiquetas = paginaListEtiquetas;
    }

    public List<MarcasHhppMgl> getListaMarcasHomePassAux() {
        return listaMarcasHomePassAux;
    }

    public void setListaMarcasHomePassAux(List<MarcasHhppMgl> listaMarcasHomePassAux) {
        this.listaMarcasHomePassAux = listaMarcasHomePassAux;
    }

    public int getActualEtiquetas() {
        return actualEtiquetas;
    }

    public void setActualEtiquetas(int actualEtiquetas) {
        this.actualEtiquetas = actualEtiquetas;
    }


}
