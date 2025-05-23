package co.com.claro.mgl.mbeans.vt.solicitudes;

import co.com.claro.cmas400.ejb.request.RequestDataValidaRazonesCreaHhppVt;
import co.com.claro.cmas400.ejb.respons.ResponseDataValidaRazonesCreaHhppVt;
import co.com.claro.mer.dtos.response.service.UsuarioPortalResponseDto;
import co.com.claro.mer.exception.ExceptionServBean;
import co.com.claro.mer.parametro.facade.ParametroFacadeLocal;
import co.com.claro.mer.utils.FileToolUtils;
import co.com.claro.mer.utils.StringToolUtils;
import co.com.claro.mer.utils.constants.ConstantsDirecciones;
import co.com.claro.mer.utils.enums.MessageSeverityEnum;
import co.com.claro.mer.utils.constants.ConstantsSolicitudHhpp;
import co.com.claro.mer.utils.enums.TiposSolicitudesHhppEnum;
import co.com.claro.mgl.businessmanager.address.SolicitudManager;
import co.com.claro.mgl.dtos.FiltroConjsultaBasicasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.*;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtComponenteDireccionesMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionDetalleMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtExtendidaTecnologiaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtNotasSolicitudDetalleVtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtNotasSolicitudVtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSolicitudTipoSolicitudMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTiempoSolicitudMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoSolicitudMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudDetalleVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTiempoSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.claro.mgl.mbeans.cm.CuentaMatrizBean;
import co.com.claro.mgl.mbeans.direcciones.HhppDetalleSessionBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;

import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.mgl.ws.cm.request.RequestCreaSolicitud;
import co.com.claro.mgl.ws.cm.request.RequestCreaSolicitudEscalamientoHHPP;
import co.com.claro.mgl.ws.cm.request.RequestCreaSolicitudTrasladoHhppBloqueado;
import co.com.claro.mgl.ws.cm.response.ResponseConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseCreaSolicitud;
import co.com.claro.mgl.ws.cm.response.ResponseMesaje;
import co.com.claro.mgl.ws.cm.response.ResponseValidacionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseValidacionViabilidad;
import co.com.claro.mgl.ws.cm.response.ResponseValidacionViabilidadMensaje;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.telmex.catastro.data.AddressSuggested;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import co.com.claro.ejb.mgl.address.dto.UnidadesConflictoDto;
import co.com.claro.ejb.mgl.address.dto.ValidacionParametrizadaDto;
import co.com.claro.mgl.dtos.CmtDireccionDetalladaMglDto;
import co.com.claro.mgl.facade.cm.CmtArchivosVtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCuentaMatrizMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificioMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtArchivosVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.mbeans.cm.ConsultaCuentasMatricesBean;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ValidacionUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.file.UploadedFile;

import static co.com.claro.mer.utils.constants.ConstantsSolicitudHhpp.DIRECCION_DETA;
import static co.com.claro.mer.utils.constants.ConstantsSolicitudHhpp.SOLICITUDES_HHPP;
import static co.com.claro.mer.utils.enums.TiposSolicitudesHhppEnum.*;
import static co.com.telmex.catastro.utilws.ResponseMessage.MESSAGE_ERROR_BARRIO_OBLIGATORIO;


/**
 * MAnagedBean que sirve para la atención de la vista para
 * la creación de una solicitud
 *
 * @author Juan David Hernandez
 */
@Log4j2
@ViewScoped
@ManagedBean(name = "crearSolicitudDthBean")
public final class CrearSolicitudDthBean implements Serializable {

    private static final String SELECCIONE_EL_CENTRO_POBLADO_POR_FAVOR = "Seleccione el CENTRO POBLADO por favor.";
    /**
     * Etiqueta HTML de apertura para colocar las letras en itálica ó cursiva.
     */
    private static final String HTML_ITALIC_OPEN = " <i>";
    /**
     * Etiqueta HTML de cierre para colocar las letras en itálica ó cursiva.
     */
    private static final String HTML_ITALIC_CLOSE = "</i>";
    /**
     * Etiqueta HTML de cierre para colocar las letras en negrita.
     */
    private static final String HTML_BOLD_CLOSE = "</b>";
    private Date tiempoFinalSolicitud;
    private Date dateInicioCreacion;
    private String usuarioVT = null;
    private String cedulaUsuarioVT = null;
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    private Solicitud solicitud = new Solicitud();
    private Solicitud solicitudCreated = new Solicitud();
    private CmtBasicaMgl tipoNotaSelected = new CmtBasicaMgl();
    private CmtBasicaMgl tipoTecnologiaBasica = new CmtBasicaMgl();
    private CmtNotasSolicitudVtMgl cmtNotasSolicitudVtMgl = new CmtNotasSolicitudVtMgl();
    @Getter
    @Setter
    private CmtTiempoSolicitudMgl cmtTiempoSolicitudDth = new CmtTiempoSolicitudMgl();
    private CmtTiempoSolicitudMgl cmtTiempoSolicitudMglToCreate = new CmtTiempoSolicitudMgl();
    private CmtTipoSolicitudMgl cmtTipoSolicitudMgl = new CmtTipoSolicitudMgl();
    private GeograficoPoliticoMgl centroPobladoSeleccionado;
    private ResponseConstruccionDireccion responseConstruirApartamentoDireccion = new ResponseConstruccionDireccion();
    private RequestConstruccionDireccion requestNuevoApartamento = new RequestConstruccionDireccion();
    private DrDireccion drDireccion = new DrDireccion();
    private DrDireccion drDireccionNuevoApartamento = new DrDireccion();
    private DrDireccion drDireccionAmbiguaCopia;
    private CmtCuentaMatrizMgl cuentaMatrizOtraDireccion;
    private HhppMgl hhppSelected;
    private int perfilVt = 0;
    private String departamento;
    private BigDecimal idSolicitudDth;
    private BigDecimal idCentroPoblado;
    private BigDecimal ciudad;
    private String nuevoEstratoHhpp;
    private String direccionAmbiguaAntigua;
    private String direccionAmbiguaNueva;
    private String mensajeMatrizViabilidad;
    private String inicioPagina = "<< - Inicio";
    private String anteriorPagina = "< - Anterior";
    private String finPagina = "Fin - >>";
    private String siguientePagina = "Siguiente - >";
    private String notaComentarioStr;
    private String tipoTecnologia;
    private String tipoDireccion;
    private String rrRegional;
    private String rrCiudad;
    private String dirEstado;
    private String direccionCk;
    private String nivelValorBm;
    private String nivelValorIt;
    private String tipoNivelBm;
    private String tipoNivelIt;
    private String tipoNivelApartamento;
    private String tipoNivelComplemento;
    private String tipoNivelNuevoApartamento;
    private String valorNivelNuevoApartamento;
    private String apartamento;
    private String nuevoApartamento;
    private String nuevoApartamentoRr;
    private String complemento;
    private String direccionLabel;
    private String barrioSugerido;
    private String barrioSugeridoStr;
    private String barrio;
    private String tmpInicio;
    private String deltaTiempo;
    private String tmpFin;
    private String timeSol;
    private String tipoAccionSolicitud;
    private String valorNivel6;
    private String valorNivel5;
    private String espacio = "&nbsp;";
    private String pageActual;
    private String numPagina = "1";
    private String direccionRespuestaGeo;
    private String complementoCreate;
    private List<Integer> paginaList;
    private int actual;
    private boolean direccionesCambioEstrato = false;
    private boolean solicitudCambioEstrato = false;
    private boolean hhppExiste;
    private boolean soporteRequerido;
    private boolean ambiguaAntigua;
    private boolean ambiguaNueva;
    private boolean direccionAmbiguaPanel;
    private boolean cambioDireccionPanel;
    private boolean enableApto;
    private boolean deshabilitarCreacion;
    private boolean apartamentoIngresado;
    private boolean enableTabs;
    private boolean showSolicitud;
    private boolean showTrack;
    private boolean showNotas;
    private boolean showCKPanel;
    private boolean showBMPanel;
    private boolean showITPanel;
    private boolean barrioSugeridoPanel;
    private boolean barrioSugeridoField;
    private boolean crearSolicitudButton;
    private boolean deshabilitarValidar;
    private boolean direccionConstruida;
    private boolean unidadesPredio;
    private boolean habilitarViabilidad;
    private boolean cuentaMatrizActiva;
    private boolean bloquearCambioAmbigua;
    private boolean mallaNuevaAmbigua;
    @Getter
    @Setter
    private boolean mostrarCampoTipoDocumento;
    private String pageActualHhpp;
    private String numPaginaHhpp = "1";
    private List<Integer> paginaListHhpp;
    private int actualHhpp;
    private int filasPagHhpp = ConstantsCM.PAGINACION_CINCO_FILAS;
    private List<HhppMgl> hhppList;
    private List<HhppMgl> hhppAuxList;
    private List<OpcionIdNombre> listNivel5;
    private List<OpcionIdNombre> listNivel6;
    private List<CmtNotasSolicitudVtMgl> notasSolicitudList;
    private List<CmtBasicaMgl> tipoNotaList;
    private List<RrRegionales> regionalList;
    private List<GeograficoPoliticoMgl> ciudadesList;
    private List<ParametrosCalles> areaCanalList;
    private List<CmtBasicaMgl> tipoTecnologiaBasicaList;
    @Getter
    @Setter
    private List<CmtBasicaMgl> tipoAccionSolicitudBasicaList;
    private List<ValidacionParametrizadaTipoValidacionMgl> validacionParametrizadaList;
    private GeograficoPoliticoMgl ciudadGpo;
    private GeograficoPoliticoMgl departamentoGpo;
    private ConfigurationAddressComponent configurationAddressComponent;
    private List<OpcionIdNombre> ckList = new ArrayList<>();
    private List<OpcionIdNombre> bmList = new ArrayList<>();
    private List<OpcionIdNombre> itList = new ArrayList<>();
    @Getter
    @Setter
    private List<OpcionIdNombre> aptoList = new ArrayList<>();
    private List<SelectItem> documentoList = new ArrayList<>();
    private List<OpcionIdNombre> complementoList = new ArrayList<>();
    private List<AddressSuggested> barrioSugeridoList;
    private List<UnidadStructPcml> unidadList;
    private List<UnidadStructPcml> unidadAuxiliarList;
    private List<GeograficoPoliticoMgl> centroPobladoList;
    private List<GeograficoPoliticoMgl> departamentoList;
    private List<CmtBasicaMgl> estratoHhppList;
    @Getter
    @Setter
    private String selectedTab = "SOLICITUD";
    private String regresarMenu = "<- Regresar Menú";
    private int filasPag = ConstantsCM.PAGINACION_CINCO_FILAS;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletRequest requestHttp = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    private RequestConstruccionDireccion request = new RequestConstruccionDireccion();
    private ResponseValidacionDireccion responseValidarDireccion = new ResponseValidacionDireccion();
    private ResponseConstruccionDireccion responseConstruirDireccion = new ResponseConstruccionDireccion();
    private RequestCreaSolicitud crearSolicitudRequest = new RequestCreaSolicitud();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private static final String MSG_HHPP_EN_ANTIGUA_NUEVA = "Existen HHPP en conflicto, ya "
            + "que existen en MGL tanto en la direccion Antigua como en la"
            + " direccion NUEVA";
    @Getter
    @Setter
    private UploadedFile uploadedFile;

    //modificaciones para link's de archivos cambio de estrato
    private String estiloObligatorio = "<font color='red'>*</font>";
    private String tipoContexto;
    private List<String> linksDocumentos = new ArrayList<>();
    private String url;
    @Getter
    @Setter
    private boolean goGestion = false;
    private SecurityLogin securityLogin;
    /**
     * Indica si la dirección de la solicitud es georeferenciada.
     */
    @Getter
    @Setter
    private boolean notGeoReferenciado = true;
    @Getter
    @Setter
    private String createDireccion;
    @Getter
    @Setter
    private String tipoViviendaSeleccionada;
    @Getter
    @Setter
    private List<TipoHhppMgl> listaTipoHhpp;
    @Getter
    @Setter
    private List<File> lstFilesCambiosEstrato = new ArrayList<>();
    @Getter
    @Setter
    private List<File> lstFilesEscalamientoHHPP = new ArrayList<>();
    private File fileDelete = null;
    @Getter
    @Setter
    private List<TecArchivosCambioEstrato> tecArchivosCambioEstratos;
    @Getter
    @Setter
    private List<TecArchivosEscalamientosHHPP> tecArchivosEscalamientosHHPP;
    private GeograficoPoliticoMgl centroPobladoDireccion;
    private GeograficoPoliticoMgl ciudadDireccion;
    private GeograficoPoliticoMgl departamentoDireccion;
    private boolean habilitarRR;
    private CmtDireccionDetalladaMglDto direccionDetalladaMgl;
    @Getter
    @Setter
    private UploadedFile fileCambioEstrato;
    @Getter
    @Setter
    private UploadedFile fileEscalamientoHHPP;
    //Opciones agregadas para Rol
    private final String BTCRESOLVALDIR = "BTCRESOLVALDIR";
    @Getter
    @Setter
    private boolean showAdjuntoEscalamiento;
    @Getter
    @Setter
    private boolean showContacto = true;
    @Getter
    @Setter
    private String ccmm;
    @Getter
    @Setter
    private String solicitudOT;
    @Getter
    @Setter
    private String tipoGestion;
    @Getter
    @Setter
    private String tipoHHPP;
    private static final String CREACION = "CREACION";
    @Setter
    @Getter
    private List<CmtBasicaMgl> tablasBasicasMglList;
    @Getter
    private Date fechaFin;
    private boolean hayComplmento;
    private Date startCreteRequest;
    private boolean bmExitoso;
    private boolean itExitoso;
    /* Brief 57762 Creación de HHPP virtual - traslado HHPP Bloqueado */
    @Getter
    @Setter
    private String numCuentaClienteTraslado;
    private HhppMgl hhppReal;
    /**
     * Retiene de forma temporal el tipo de apartamento para poder
     * verificar combinaciones de tipos de apartamento.
     */
    private String tipoApartTmp = "";
    /* ----------------------------------------------------------------*/
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMgl;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cuentaMatrizFacadeLocal;
    @EJB
    private ValidacionParametrizadaTipoValidacionMglFacadeLocal validacionParametrizadaMglFacadeLocal;
    @EJB
    private CmtNotasSolicitudDetalleVtMglFacadeLocal notasSolicitudDetalleVtMglFacadeLocal;
    @EJB
    private CmtNotasSolicitudVtMglFacadeLocal notasSolicitudVtMglFacadeLocal;
    @EJB
    private CmtComponenteDireccionesMglFacadeLocal componenteDireccionesMglFacade;
    @EJB
    private ParametrosCallesFacadeLocal parametrosCallesFacade;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTiempoSolicitudMglFacadeLocal cmtTiempoSolicitudMglFacadeLocal;
    @EJB
    private CmtTipoSolicitudMglFacadeLocal cmtTipoSolicitudMglFacadeLocal;
    @EJB
    private CmtSolicitudTipoSolicitudMglFacadeLocal cmtSolicitudTipoSolicitudMglFacadeLocal;
    @EJB
    private DireccionRRFacadeLocal direccionRRFacadeLocal;
    @EJB
    private DireccionesValidacionFacadeLocal direccionesValidacionFacadeLocal;
    @EJB
    private SolicitudFacadeLocal solicitudFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private HhppMglFacadeLocal hhppMglFacadeLocal;
    @EJB
    private CmtExtendidaTecnologiaMglFacadeLocal cmtExtendidaTecnologiaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMglFacadeLocal;
    @EJB
    private SubDireccionMglFacadeLocal subDireccionMglFacadeLocal;
    @EJB
    private TecArcCamEstratoFacadeLocal tecArcCamEstratoFacadeLocal;
    @EJB
    private ITecArcEscalaHHPPFacadeLocal tecArcEscalaHHPPFacadeLocal;
    @EJB
    private TipoHhppMglFacadeLocal tipoHhppMglFacadeLocal;
    @EJB
    private CmtArchivosVtMglFacadeLocal archivosVtMglFacadeLocal;
    @EJB
    private CmtSubEdificioMglFacadeLocal cmtSubEdificioMglFacadeLocal;
    @EJB
    private ParametroFacadeLocal parametroFacadeLocal;
    @EJB
    private DetalleFactibilidadMglFacadeLocal detalleFactibilidadMglFacadeLocal;
    @EJB
    private CmtDireccionDetalleMglFacadeLocal direccionDetalleMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacade;
    @EJB
    private HhppVirtualMglFacadeLocal hhppVirtualMglFacadeLocal;
    @Inject
    private ExceptionServBean exceptionServBean;

    /* ---------------------------------------------------------------- */

    public CrearSolicitudDthBean() throws ApplicationException, IOException {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                response.sendRedirect(SecurityLogin.SERVER_PARA_AUTENTICACION);
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();
            cedulaUsuarioVT = securityLogin.getIdUser();

            if (usuarioVT == null) {
                response.sendRedirect(SecurityLogin.SERVER_PARA_AUTENTICACION);
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se genera error Validando Autenticacion" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error Validando Autenticacion" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Verificar el rol del usuario. Verifica si el usuario tiene el pasado por
     * parámetro.
     *
     * @param rol Rol a verificar.
     * @return True cuando el usuario tiene el rol del parámetro de lo contrario
     * false.
     * @author Johnnatan Ortiz.
     */
    private boolean verificaRol(String rol) {
        try {
            return securityLogin.usuarioTieneRoll(rol);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al Validar el Rol" + e.getMessage(), e, LOGGER);
            return false;
        }
    }

    /**
     * Verificar el rol del usuario. Verifica si el usuario tiene el pasado por
     * parámetro.
     *
     * @return True cuando el usuario tiene el rol del parámetro de lo contrario
     * false.
     * @author Juan David Hernandez.
     */
    private boolean validarRolGestion(Solicitud solicitudCreated) {
        try {
            if (solicitudCreated != null && solicitudCreated.getIdSolicitud() != null) {
                cmtTipoSolicitudMgl = obtenerTipoSolicitud(tipoAccionSolicitudBasicaList, solicitudCreated.getCambioDir());
                return securityLogin.usuarioTieneRoll(cmtTipoSolicitudMgl.getGestionRol());
            } else {
                return false;
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al Validar el Rol" + e.getMessage(), e, LOGGER);
            return false;
        }
    }

    /**
     * Función que obtiene el tipo de solicitud apartir del cambio dir de la solicitud
     * consultando su correpondiente basica y haciendo el cruce con la tabla de tipo de solicitud
     *
     * @param tipoAccionSolicitudBasicaIdList {@link List<CmtBasicaMgl>}
     * @param cambioDir {@link String}
     * @return {@link CmtTipoSolicitudMgl}
     * @author Juan David Hernandez
     */
    public CmtTipoSolicitudMgl obtenerTipoSolicitud(List<CmtBasicaMgl> tipoAccionSolicitudBasicaIdList,
                                                    String cambioDir) {
        try {
            CmtBasicaMgl tipoSolicitudSeleccionadaBasicaId = new CmtBasicaMgl();
            if (tipoAccionSolicitudBasicaIdList != null && !tipoAccionSolicitudBasicaIdList.isEmpty()) {
                for (CmtBasicaMgl cmtBasicaMgl : tipoAccionSolicitudBasicaIdList) {
                    if (cambioDir.equalsIgnoreCase(cmtBasicaMgl.getCodigoBasica())) {
                        tipoSolicitudSeleccionadaBasicaId = cmtBasicaMgl.clone();
                        break;
                    }
                }
            }

            if (tipoSolicitudSeleccionadaBasicaId != null
                    && tipoSolicitudSeleccionadaBasicaId.getBasicaId() != null) {
                cmtTipoSolicitudMgl = cmtTipoSolicitudMglFacadeLocal
                        .findTipoSolicitudByTipoSolicitudBasicaId(tipoSolicitudSeleccionadaBasicaId);
                return cmtTipoSolicitudMgl;
            } else {
                return null;
            }
        } catch (ApplicationException | CloneNotSupportedException e) {
            String msgError = "Error al obtener tipo de solicitud";
            exceptionServBean.notifyError(e, msgError, SOLICITUDES_HHPP);
            return null;
        }
    }

    @PostConstruct
    public void init() {
        try {
            habilitarRR = Optional.ofNullable(parametroFacadeLocal.findParameterValueByAcronymInCache(Constant.HABILITAR_RR))
                    .map("1"::equalsIgnoreCase)
                    .orElse(false);
            cargarListasDesplegables();
            establecerVisibilidadElementos();
            inicializarElementos();
            validarDireccionDeFactibilidad();
            generarListaTipoDocumento();
        } catch (ApplicationException | IOException e) {
            String msgError = "Error al realizar cargue de configuración de listados";
            exceptionServBean.notifyError(e, msgError, SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que se encarga de cargar las listas desplegables de la vista
     *
     * @throws ApplicationException Excepción de aplicación
     * @throws IOException      Excepción de entrada y salida
     */
    private void cargarListasDesplegables() throws ApplicationException, IOException {
        cargarListaCrearSolicitudHomePassed();
        cargarListaTipoTecnologia();
        cargarListadoAreaCanal();
        cargarListaTipoNotas();
        cargarListaDepartamento();
    }

    /**
     * Establece la visibilidad de los elementos de la vista
     */
    private void establecerVisibilidadElementos() {
        hideTipoDireccion();
        showCKPanel = true;
        enableTabs = false;
        apartamentoIngresado = false;
        notGeoReferenciado = true;
    }

    /**
     * Inicializa los elementos de la vista
     * @throws ApplicationException Excepción en caso de error
     */
    private void inicializarElementos() throws ApplicationException {
        usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
        cmtTiempoSolicitudMglFacadeLocal.setUser(usuarioVT, perfilVt);
        cmtSolicitudTipoSolicitudMglFacadeLocal.setUser(usuarioVT, perfilVt);
        createDireccion = "";
        iniciarTiempoCreaSolicitud();
        asignarUsuarioActivo();
        showSolicitud();
        consultarTipoGestion();
        lstFilesCambiosEstrato = new ArrayList<>();
        lstFilesEscalamientoHHPP = new ArrayList<>();
        listaTipoHhpp = tipoHhppMglFacadeLocal.findAll();
        startCreteRequest = new Date();
    }

    /**
     * Verifica la dirección detallada procesada en factibilidad
     * @throws ApplicationException Excepción en caso de error
     */
    private void validarDireccionDeFactibilidad() throws ApplicationException {
        //Valido direccion capturada en el modulo de  factibilidades
        if (session.getAttribute(DIRECCION_DETA) == null) {
            LOGGER.debug("No se encontro direccion de factibilidad en sesion, no se requiere validación");
            return;
        }

        direccionDetalladaMgl = (CmtDireccionDetalladaMglDto) session.getAttribute(DIRECCION_DETA);
        DireccionMgl direccion = direccionDetalladaMgl.getDireccionMgl();
        SubDireccionMgl subDireccion = direccionDetalladaMgl.getSubDireccionMgl();
        UbicacionMgl ubicacionMgl = direccion.getUbicacion();
        idCentroPoblado = ubicacionMgl.getGpoIdObj().getGpoId();

        if (ubicacionMgl.getGpoIdObj().getGeoGpoId() != null) {
            GeograficoPoliticoMgl city = geograficoPoliticoMglFacadeLocal.findById(ubicacionMgl.getGpoIdObj().getGeoGpoId());
            ciudad = city.getGpoId();

            if (city.getGeoGpoId() != null) {
                GeograficoPoliticoMgl dep = geograficoPoliticoMglFacadeLocal.findById(city.getGeoGpoId());
                departamento = dep.getGpoId().toString();
                obtenerCiudadesList();
                obtenerCentroPobladoList();
            }
        }

        List<DetalleFactibilidadMgl> detalleFactibilidadMgls = null;

        if (direccionDetalladaMgl.getFactibilidadId() != null) {
            detalleFactibilidadMgls = detalleFactibilidadMglFacadeLocal.findListDetalleFactibilidad(direccionDetalladaMgl.getFactibilidadId());
        }

        String tecno;

        if (CollectionUtils.isNotEmpty(detalleFactibilidadMgls)) {
            tecno = detalleFactibilidadMgls.stream()
                    .filter(detalle -> "POSITIVA".equalsIgnoreCase(detalle.getClaseFactibilidad()))
                    .map(DetalleFactibilidadMgl::getNombreTecnologia)
                    .findFirst()
                    .orElse(null);

            if (StringUtils.isNotBlank(tecno)) {
                //Consulto Tecnologia
                CmtTipoBasicaMgl tipoBasicaTipoTecnologia = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
                CmtBasicaMgl tecnology = cmtBasicaMglFacadeLocal.findByBasicaCode(tecno, tipoBasicaTipoTecnologia.getTipoBasicaId());
                tipoTecnologia = tecnology.getBasicaId().toString();
                validarTecnologiaSeleccionada(tipoTecnologia);
            }
        }

        CmtDireccionDetalladaMgl dirDeta = direccionDetalleMglFacadeLocal.findById(direccionDetalladaMgl.getDireccionDetalladaId());
        drDireccion = direccionDetalleMglFacadeLocal.parseCmtDireccionDetalladaMglToDrDireccion(dirDeta);

        if (ubicacionMgl.getGpoIdObj() != null && ubicacionMgl.getGpoIdObj().getGpoMultiorigen().equalsIgnoreCase("1")
                && dirDeta.getIdTipoDireccion().equalsIgnoreCase("CK") && drDireccion.getBarrio() != null) {
            barrioSugeridoStr = drDireccion.getBarrio();
            request.setBarrio(drDireccion.getBarrio());
        }

        direccionCk = Optional.ofNullable(subDireccion)
                .map(SubDireccionMgl::getSdiId).isPresent() ? Optional.of(subDireccion)
                .map(SubDireccionMgl::getSdiFormatoIgac).orElse("") : direccion.getDirNostandar();
        request.setDireccionStr(direccionCk);
        Optional.ofNullable(ubicacionMgl.getGpoIdObj())
                .filter(gpoIdObj -> "1".equalsIgnoreCase(gpoIdObj.getGpoMultiorigen()))
                .filter(gpoIdObj -> "CK".equalsIgnoreCase(dirDeta.getIdTipoDireccion()))
                .filter(gpoIdObj -> drDireccion.getBarrio() != null)
                .ifPresent(gpoIdObj ->
                        request.setDireccionStr(direccionCk + " " + barrioSugeridoStr));

        request.setComunidad(ubicacionMgl.getGpoIdObj().getGeoCodigoDane());
        drDireccion.setIdTipoDireccion(dirDeta.getIdTipoDireccion());
        tipoDireccion = dirDeta.getIdTipoDireccion();
        request.setDrDireccion(drDireccion);
        request.setIdUsuario(usuarioLogin.getCedula());
        responseConstruirDireccion.setDrDireccion(drDireccion);
        centroPobladoSeleccionado = ubicacionMgl.getGpoIdObj();
        direccionLabel = direccionRRFacadeLocal.getDireccion(drDireccion);
        tipoAccionSolicitud = "0";
        puedeMostrarCampoTipoDocumento();
        direccionConstruida = true;
        apartamentoIngresado = true;
        session.removeAttribute(DIRECCION_DETA);
    }

    /**
     * genera la lista de tipos de documento habilitada
     */
    private void generarListaTipoDocumento() {
        documentoList = new ArrayList<>();
        documentoList.add(new SelectItem("Cedula", "Cedula"));
        documentoList.add(new SelectItem("Cedula Exranjeria", "Cedula Exranjeria"));
        documentoList.add(new SelectItem("NIT", "NIT"));
        documentoList.add(new SelectItem("Pasaporte", "Pasaporte"));
    }

    /**
     * Consulta y carga la lista de tipos de solicitud habilitados para
     * la creación de solicitudes de Home Passed
     *
     * @author Juan David Hernandez
     */
    private void cargarListaCrearSolicitudHomePassed() {
        try {
            List<CmtTipoSolicitudMgl> tipoSolicitudByRolList = solicitudFacadeLocal.consultarTiposSolicitudesCreacion(facesContext, Constant.TIPO_APLICACION_SOLICITUD_HHPP);
            CmtTipoBasicaMgl tipoBasica = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_ACCION_SOLICITUD);
            tipoAccionSolicitudBasicaList = cmtBasicaMglFacadeLocal.findByCodigoBasicaList(tipoSolicitudByRolList, tipoBasica);
            boolean isActiveHhppTrasladoHhppBloqueado = hhppVirtualMglFacadeLocal.isActiveHhppTrasladoHhppBloqueado();
            tipoAccionSolicitudBasicaList.removeIf(tipoAccion -> tipoAccion.getCodigoBasica().equals(Constant.VALIDACION_COBERTURA_12)
                    || !isActiveHhppTrasladoHhppBloqueado && tipoAccion.getNombreBasica().equalsIgnoreCase(ConstantsSolicitudHhpp.NOMBRE_TIPO_TRASLADO_HHPP_BLOQUEADO));
        } catch (Exception e) {
            String msgError = "Error al obtener el tipo de acción de la solicitud: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que obtiene el valor completo de el centro poblado seleccionado
     * por el usuario en pantalla de creación de solicitud.
     *
     * @author Juan David Hernandez
     */
    public void obtenerCentroPobladoSeleccionado() {
        try {
            if (idCentroPoblado != null) {
                centroPobladoSeleccionado = geograficoPoliticoMglFacadeLocal.findById(idCentroPoblado);
            }
        } catch (Exception e) {
            String msgError = "Error al consultar el centro poblado seleccionado ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Obtiene el listado de ciudades de la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void obtenerCiudadesList() {
        try {
            if (StringUtils.isNotBlank(departamento)) {
                //Obtenemos el listado de ciudades para el filtro de la pantalla
                ciudadesList = geograficoPoliticoMglFacadeLocal.findCiudades(new BigDecimal(departamento));
            }
            centroPobladoList = new ArrayList<>();

        } catch (Exception e) {
            String msgError = "Error al obtener listado de ciudades ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Metodo utilizado para realizar la busqueda de parametros en la base de
     * datos
     *
     * @param type llave de busqueda a realizar
     * @return {@link List<ParametrosCalles>}
     */
    public List<ParametrosCalles> obtenerListadoTipoSolicitudList(String type) {
        List<ParametrosCalles> tList = new ArrayList<>();
        try {
            tList = parametrosCallesFacade.findByTipo(type);
        } catch (Exception e) {
            String msgError = "Error al realizar consulta para obtener listado de tipo de solicitudes ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
        return tList;
    }

    /**
     * Obtiene el listado de centro poblado por ciudad de la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void obtenerCentroPobladoList() {
        try {
            if (ciudad == null) {
                centroPobladoList = new ArrayList<>();
                departamentoGpo = null;
                ciudadGpo = null;
                return;
            }

            centroPobladoList = geograficoPoliticoMglFacadeLocal.findCentroPoblado(ciudad);
            ciudadGpo = geograficoPoliticoMglFacadeLocal.findById(ciudad);
            //obtiene el departamento geograficoPolitico completo
            if (ciudadGpo != null) {
                departamentoGpo = geograficoPoliticoMglFacadeLocal.findById(ciudadGpo.getGeoGpoId());
            }

        } catch (ApplicationException e) {
            String msgError = "Error al obtener listado de centro poblado: " ;
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            String msgError = "Ocurrió un error al obtener listado de centro poblado ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Obtiene el listado de departamentos de la base de datos.
     *
     * @author Juan David Hernandez
     */
    private void cargarListaDepartamento() {
        try {
            departamentoList = geograficoPoliticoMglFacadeLocal.findDptos();
        } catch (ApplicationException e) {
            String msgError = "Error al obtener listado de departamentos ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            String msgError = "Ocurrió un error al obtener listado de departamentos ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para construir dirección de tipo calle-carrera
     *
     * @param ambigua boolean que indica si la dirección es ambigua
     * @author Juan David Hernandez
     */
    public void construirDireccionCk(boolean ambigua) {
        try {
            boolean datosValidos = validarDatosDireccionTipoSolicitud() && validarDatosDireccionCk();

            if (!datosValidos) {
                LOGGER.debug("Datos de dirección no validos");
                return;
            }

            prepararRequestDireccionCk();
            //Consume servicio que retorna la dirección calle-carrera traducida.
            responseConstruirDireccion = direccionRRFacadeLocal.construirDireccionSolicitud(request);
            procesarRespuestaDireccionCk(ambigua);
        } catch (Exception e) {
            String msgError = "No fue posible realizar la construcción de la dirección Calle-Carrera ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Prepara los datos del request para la construcción de la dirección calle-carrera
     */
    private void prepararRequestDireccionCk() {
        request.setDireccionStr(direccionCk);
        request.setComunidad(centroPobladoSeleccionado.getGeoCodigoDane());
        drDireccion.setIdTipoDireccion(tipoDireccion);
        request.setDrDireccion(drDireccion);
        request.setTipoAdicion("N");
        request.setTipoNivel("N");
        request.setIdUsuario(usuarioLogin.getCedula());

        if (responseConstruirDireccion != null && responseConstruirDireccion.getDrDireccion() != null) {
            request.setDrDireccion(responseConstruirDireccion.getDrDireccion());
        }
    }

    /**
     * Procesa la respuesta de la construcción de la dirección calle-carrera
     * @param ambigua Indica si la dirección es ambigua
     * @throws ApplicationException Excepción en caso de error
     */
    private void procesarRespuestaDireccionCk(boolean ambigua) throws ApplicationException {
        //direccion respuesta del geo sin alteraciones como AVENIDAS
        if (StringUtils.isNotEmpty(responseConstruirDireccion.getDireccionRespuestaGeo())) {
            direccionRespuestaGeo = responseConstruirDireccion.getDireccionRespuestaGeo();
        }

        if (StringUtils.isEmpty(responseConstruirDireccion.getDireccionStr())
                || !responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equalsIgnoreCase("I")) {
            manejarErrorDireccionCk();
            return;
        }

        if (!ambigua && !direccionAmbiguaPanel && tipoAccionSolicitud.equalsIgnoreCase(Constant.RR_DIR_CREA_HHPP_0)) {
            validarDireccionAmbiguaCk();
        }

        actualizarEstadoDireccionCk();
    }

    /**
     * valida la dirección ambigua de calle-carrera
     * @throws ApplicationException Excepción en caso de error
     */
    private void validarDireccionAmbiguaCk() throws ApplicationException {
        List<String> ambiguaList = direccionesValidacionFacadeLocal.validarDireccionAmbigua(request.getComunidad(),
                request.getBarrio(), responseConstruirDireccion.getDrDireccion());

        if (CollectionUtils.isNotEmpty(ambiguaList) && !ambiguaList.get(0).equals("0")) {
            if (ambiguaList.get(0).equals("1")) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "La dirección ingresada es AMBIGUA." +
                        "Por favor seleccione en que Malla Vial se encuentra, " +
                        "NUEVA o ANTIGUA para continuar con la solicitud. ");
                ambiguaAntigua = false;
                ambiguaNueva = false;
                direccionAmbiguaPanel = true;
                direccionLabel = responseConstruirDireccion.getDireccionStr();
            }
            return;
        }

        direccionAmbiguaPanel = false;
        bloquearCambioAmbigua = false;
    }

    /**
     * Actualiza el estado de la dirección de tipo calle-carrera
     */
    private void actualizarEstadoDireccionCk() {
        //se toma el estado que devuelve la construcción de la dirección.
        dirEstado = responseConstruirDireccion.getDrDireccion().getDirEstado();
        /*se toma la dirección en String que devuelve la construcción
         * de la dirección ya estandarizada*/
        direccionLabel = responseConstruirDireccion.getDireccionStr();
        direccionConstruida = true;
        barrioSugeridoField = false;
        barrioSugeridoStr = "";
        notGeoReferenciado = true;
        apartamentoIngresado = false;
        request.setDrDireccion(responseConstruirDireccion.getDrDireccion());
    }

    /**
     * Maneja el error de la dirección de tipo calle-carrera
     */
    private void manejarErrorDireccionCk() {
        if (StringUtils.isEmpty(responseConstruirDireccion.getDireccionStr())
                && responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equalsIgnoreCase("E")) {
            direccionLabel = "";
            tipoApartTmp = "";
            barrioSugeridoField = false;
            crearSolicitudButton = false;
            notGeoReferenciado = false;
            barrioSugeridoStr = "";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "La dirección calle-carrera no pudo ser traducida."
                    + responseConstruirDireccion.getResponseMesaje().getMensaje());
        }
    }

    /**
     * Función utilizada para construir dirección de tipo Barrio-Manzana
     *
     * @author Juan David Hernandez
     */
    public void construirDireccionBm() {
        try {
            if (validarDatosDireccionTipoSolicitud() && validarDatosDireccionBm()) {
                request.setComunidad(centroPobladoSeleccionado.getGeoCodigoDane());
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelBm);
                request.setValorNivel(nivelValorBm.trim());
                request.setTipoAdicion("P");
                request.setIdUsuario(usuarioLogin.getCedula());

                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion
                            .getDrDireccion());
                }
                /* Consume servicio que retorna la dirección barrio-manzana 
                 traducida. */
                responseConstruirDireccion = direccionRRFacadeLocal
                        .construirDireccionSolicitud(request);

                // Dirección no construida correctamente
                if (responseConstruirDireccion == null
                        || responseConstruirDireccion.getResponseMesaje()
                        .getTipoRespuesta().equals("E")) {
                    crearSolicitudButton = false;
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                            Optional.ofNullable(responseConstruirDireccion)
                                    .map(ResponseConstruccionDireccion::getResponseMesaje)
                                    .map(ResponseMesaje::getMensaje)
                                    .orElse("No se construyo la dirección BM, continue .."));
                } else {
                    //Dirección construida correctamente
                    if (responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("I")) {
                        dirEstado = responseConstruirDireccion.getDrDireccion().getDirEstado();
                        request.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                        direccionLabel = responseConstruirDireccion.getDireccionStr();
                        direccionConstruida = true;
                        apartamentoIngresado = false;
                        tipoNivelBm = "";
                        nivelValorBm = "";
                    }
                }
            }
            bmExitoso = true;
        } catch (Exception e) {
            String msgError = "Error al construir dirección Barrio-Manzana ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para construir dirección de tipo Intraducible
     *
     * @author Juan David Hernandez
     */
    public void construirDireccionIt() {
        try {
            if (validarDatosDireccionTipoSolicitud() && validarDatosDireccionIt()) {
                request.setComunidad(centroPobladoSeleccionado.getGeoCodigoDane());
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelIt);
                request.setValorNivel(nivelValorIt.trim());
                request.setTipoAdicion("P");
                request.setIdUsuario(usuarioLogin.getCedula());

                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion
                            .getDrDireccion());
                }
                // Consume servicio que retorna la dirección intraducible traducida.
                responseConstruirDireccion = direccionRRFacadeLocal
                        .construirDireccionSolicitud(request);
                //Dirección no construida correctamente
                if (responseConstruirDireccion == null
                        || responseConstruirDireccion.getResponseMesaje()
                        .getTipoRespuesta().equals("E")) {
                    crearSolicitudButton = false;
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                            Optional.ofNullable(responseConstruirDireccion)
                                    .map(ResponseConstruccionDireccion::getResponseMesaje)
                                    .map(ResponseMesaje::getMensaje)
                                    .orElse("No se construyo la dirección IT, continue .."));
                } else {
                    //Dirección construida correctamente
                    if (responseConstruirDireccion.getResponseMesaje()
                            .getTipoRespuesta().equals("I")) {
                        request.setDrDireccion(responseConstruirDireccion
                                .getDrDireccion());
                        dirEstado = responseConstruirDireccion.getDrDireccion()
                                .getDirEstado();
                        direccionLabel = responseConstruirDireccion
                                .getDireccionStr();
                        direccionConstruida = true;
                        apartamentoIngresado = false;
                        tipoNivelIt = "";
                        nivelValorIt = "";
                    }
                }
            }
            itExitoso = true;
        }  catch (Exception e) {
            String msgError = "Error al construir dirección Intraducible ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para construir dirección con complemento
     *
     * @author Juan David Hernandez
     */
    public void construirDireccionComplemento() {
        try {
            if (validarDatosDireccionComplemento()) {
                request.setComunidad(centroPobladoSeleccionado.getGeoCodigoDane());
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(ESCALAMIENTO_HHPP.getCodigoBasica().equalsIgnoreCase(tipoAccionSolicitud) ? "C" : tipoNivelComplemento);
                request.setValorNivel(complemento);
                complementoCreate = complemento;
                request.setTipoAdicion("C");
                request.setIdUsuario(usuarioLogin.getCedula());

                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                }
                // Consume servicio que retorna la dirección con complemento.
                responseConstruirDireccion = direccionRRFacadeLocal.
                        construirDireccionSolicitud(request);
                //Complemento no agregado a la dirección correctamente
                if (responseConstruirDireccion == null
                        || responseConstruirDireccion.getResponseMesaje()
                        .getTipoRespuesta().equals("E")) {
                    crearSolicitudButton = false;
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                            Optional.ofNullable(responseConstruirDireccion)
                                    .map(ResponseConstruccionDireccion::getResponseMesaje)
                                    .map(ResponseMesaje::getMensaje)
                                    .orElse("No se construyo la dirección con complemento, continue .."));
                } else {
                    //Complemento agregado a la dirección correctamente
                    if (responseConstruirDireccion.getResponseMesaje()
                            .getTipoRespuesta().equals("I")) {
                        dirEstado = responseConstruirDireccion.getDrDireccion()
                                .getDirEstado();
                        direccionLabel = responseConstruirDireccion
                                .getDireccionStr();
                        // complemento = "";
                        // tipoNivelComplemento = "";
                    }
                }
            }
            hayComplmento = true;
        } catch (Exception e) {
            String msgError = "Error al construir dirección Complemento ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para construir dirección con apartamento
     *
     * @author Juan David Hernandez
     */
    public void construirDireccionApartamento() {
        try {
            if (StringUtils.isBlank(tipoNivelApartamento)) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "Debe seleccionar el TIPO DE APARTAMENTO por favor ..");
                return;
            }

            if (validarDatosDireccionApartamento()
                    && validarDatosDireccionApartamentoParaTecnologiaSeleccionada(responseConstruirDireccion.getDrDireccion(),
                    tipoNivelApartamento, apartamento)) {
                request.setComunidad(centroPobladoSeleccionado.getGeoCodigoDane());
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelApartamento);
                request.setValorNivel(apartamento);
                request.setTipoAdicion("A");
                request.setIdUsuario(usuarioLogin.getCedula());

                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                }
                // Consume servicio que retorna la dirección con apartamento.
                responseConstruirDireccion = direccionRRFacadeLocal.construirDireccionSolicitud(request);

                // Apartamento no agregado a la dirección correctamente
                if (responseConstruirDireccion == null
                        || responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("E")) {
                    crearSolicitudButton = false;
                    apartamentoIngresado = false;
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                            Optional.ofNullable(responseConstruirDireccion)
                                    .map(ResponseConstruccionDireccion::getResponseMesaje)
                                    .map(ResponseMesaje::getMensaje)
                                    .orElse("No se construyo la dirección con apartamento, continue .."));
                    return;
                }

                // Apartamento agregado a la dirección correctamente
                if (responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("I")) {
                    dirEstado = responseConstruirDireccion.getDrDireccion().getDirEstado();
                    direccionLabel = responseConstruirDireccion.getDireccionStr();
                    //apartamento = "";
                    //tipoNivelApartamento = "";
                    apartamentoIngresado = true;
                }
            }
        } catch (Exception e) {
            String msgError = "Error al construir dirección apartamento ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para construir dirección con apartamento
     *
     * @author Juan David Hernandez
     */
    public void construirDireccionNuevoApartamento() {
        try {
            if (validarDatosDireccionNuevoApartamento() && validarDatosDireccionApartamentoParaTecnologiaSeleccionada(
                    responseConstruirApartamentoDireccion.getDrDireccion(), tipoNivelNuevoApartamento, nuevoApartamento)) {

                requestNuevoApartamento.setComunidad(centroPobladoSeleccionado.getGeoCodigoDane());
                drDireccionNuevoApartamento.setIdTipoDireccion(tipoDireccion);
                requestNuevoApartamento.setDrDireccion(drDireccionNuevoApartamento);
                requestNuevoApartamento.setTipoNivel(tipoNivelNuevoApartamento);
                requestNuevoApartamento.setValorNivel(nuevoApartamento);
                requestNuevoApartamento.setTipoAdicion("A");
                request.setIdUsuario(usuarioLogin.getCedula());

                Optional<DrDireccion> dirResponse = Optional.ofNullable(responseConstruirApartamentoDireccion)
                        .map(ResponseConstruccionDireccion::getDrDireccion);
                dirResponse.ifPresent(direccion -> requestNuevoApartamento.setDrDireccion(direccion));
                // Consume servicio que retorna la dirección con apartamento.
                responseConstruirApartamentoDireccion = direccionRRFacadeLocal.construirDireccionSolicitud(requestNuevoApartamento);

                // Apartamento no agregado a la dirección correctamente
                if (responseConstruirApartamentoDireccion == null
                        || responseConstruirApartamentoDireccion.getResponseMesaje().getTipoRespuesta().equals("E")) {

                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                            Optional.ofNullable(responseConstruirApartamentoDireccion)
                                    .map(ResponseConstruccionDireccion::getResponseMesaje)
                                    .map(ResponseMesaje::getMensaje)
                                    .orElse("No se construyo la dirección nuevo apartamento, continue .."));
                    return;
                }

                // Apartamento agregado a la dirección correctamente
                if (responseConstruirApartamentoDireccion.getResponseMesaje().getTipoRespuesta().equals("I")) {
                    DetalleDireccionEntity direccion = new DetalleDireccionEntity();
                    direccion.setCptiponivel5(responseConstruirApartamentoDireccion.getDrDireccion().getCpTipoNivel5());
                    direccion.setCpvalornivel5(responseConstruirApartamentoDireccion.getDrDireccion().getCpValorNivel5());
                    direccion.setCptiponivel6(responseConstruirApartamentoDireccion.getDrDireccion().getCpTipoNivel6());
                    direccion.setCpvalornivel6(responseConstruirApartamentoDireccion.getDrDireccion().getCpValorNivel6());
                    DireccionRRManager direccionRRManager = new DireccionRRManager(false);
                    nuevoApartamentoRr = direccionRRManager.generarNumAptoBMRR(direccion);
                    if (StringUtils.isNotBlank(nuevoApartamentoRr) && nuevoApartamentoRr.contains("OTR")) {
                        nuevoApartamentoRr = nuevoApartamentoRr.substring(3);
                    }
                    nuevoApartamento = "";
                    tipoNivelNuevoApartamento = "";
                }
            }
        } catch (Exception e) {
            String msgError = "Error al construir dirección del nuevo apartamento ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para limpiar campos de tecnologia al seleccionar el tipo de acccion.
     *
     * @throws ApplicationException Excepción en caso de error al realizar la operación.
     * @author Juan David Hernandez
     */
    public void resetTecnologia() throws ApplicationException {
        try {
            if (StringUtils.isNotBlank(tipoAccionSolicitud)) {
                TiposSolicitudesHhppEnum tiposSolicitudesHhppEnum = TiposSolicitudesHhppEnum.fromCodigoBasica(tipoAccionSolicitud);

                switch (tiposSolicitudesHhppEnum) {
                    case CAMBIO_ESTRATO:
                        cargarListadosConfiguracionGenericos();
                        obtenerEstratoList();
                        tipoContexto = "RR";
                        soporteRequerido = false;
                        showAdjuntoEscalamiento = false;
                        showContacto = true;
                        break;
                    case ESCALAMIENTO_HHPP:
                        showAdjuntoEscalamiento = true;
                        showContacto = false;
                        break;
                    case DEFAULT:
                    default:
                        tipoTecnologiaBasica = null;
                        tipoTecnologia = "";
                        showAdjuntoEscalamiento = false;
                        showContacto = true;
                        break;
                }
            } else {
                tipoTecnologiaBasica = null;
                tipoTecnologia = "";
                showAdjuntoEscalamiento = false;
                showContacto = true;
            }

            puedeMostrarCampoTipoDocumento();
        } catch (Exception e) {
            String msgError = "Error al realizar el cambio de tipo de solicitud";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para limpiar campos al crear un nuevo apartamento.
     *
     * @author Juan David Hernandez
     */
    public void limpiarNuevoApartamento() {
        responseConstruirApartamentoDireccion = new ResponseConstruccionDireccion();
        drDireccionNuevoApartamento = new DrDireccion();
        requestNuevoApartamento = new RequestConstruccionDireccion();
        nuevoApartamentoRr = "";
        nuevoApartamento = "";
        tipoNivelNuevoApartamento = "";
    }

    public boolean validarCiudadSeleccionadaNoGeoreferenciable(GeograficoPoliticoMgl ciudadGpo) {
        try {
            if (ciudadGpo != null && ciudadGpo.getGpoNombre() != null && !ciudadGpo.getGpoNombre().isEmpty()
                    && (ciudadGpo.getGpoNombre().equalsIgnoreCase("PALMIRA")
                    || ciudadGpo.getGpoNombre().equalsIgnoreCase("CANDELARIA"))) {

                String msnError = "Para esta Ciudad, por favor ingrese la "
                        + "dirección por el panel de DIRECCIÓN TABULADA";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, msnError, ""));
                notGeoReferenciado = false;
                return false;
            } else {
                notGeoReferenciado = true;
                return true;
            }

        } catch (Exception e) {
            String msgError = "Error al validar Ciudad Seleccionada No Georeferenciable. ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Función para validar datos nulos al momento de construir.una dirección
     * tipo Calle-Carrera.
     *
     * @return {@code boolean} Retorna {@code false} si alguno de los campos no tiene datos.
     * @author Juan David Hernandez
     */
    public boolean validarDatosDireccionCk() {
        try {
            if (isInvalidTipoDireccion()) return false;

            if (StringUtils.isBlank(direccionCk)) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "Ingrese la dirección por favor.");
                return false;
            }

            if (isInvalidCiudad()) return false;

            if (idCentroPoblado == null || idCentroPoblado.equals(BigDecimal.ZERO)) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                        SELECCIONE_EL_CENTRO_POBLADO_POR_FAVOR);
                return false;
            }

            return true;
        } catch (Exception e) {
            String msgError = "Error al validar los datos de la dirección calle-carrera";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Verifica si el campo de "ciudad" no es válido.
     *
     * @return {@code boolean}
     * @author Gildardo Mora
     */
    private boolean isInvalidCiudad() {
        if (ciudad == null || ciudad.equals(BigDecimal.ZERO)) {
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "Seleccione la CIUDAD por favor.");
            return true;
        }

        return false;
    }

    /**
     * Verifica si el campo "tipoDireccion" no es válido.
     *
     * @return {@code boolean}
     * @author Gildardo Mora
     */
    private boolean isInvalidTipoDireccion() {
        if (StringUtils.isBlank(tipoDireccion)) {
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                    "Seleccione el TIPO DE DIRECCIÓN que desea ingresar por favor.");
            return true;
        }
        
        return false;
    }

    /**
     * Función para validar datos nulos al momento de construir.una dirección
     * tipo Calle-Carrera.
     *
     * @return {@code boolean}
     * @author Juan David Hernandez
     * vacio.
     */
    public boolean validarDatosDireccionTipoSolicitud() {
        try {
            if (isInvalidTipoSolicitud()) return false;

            if (!tipoAccionSolicitud.equalsIgnoreCase(Constant.RR_DIR_CAMBIO_ESTRATO_2)
                    && (tipoTecnologiaBasica == null || StringUtils.isBlank(tipoTecnologiaBasica.getAbreviatura()))) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "Seleccione un TIPO de TECNOLOGÍA por favor. ");
                return false;
            }

            return true;
        } catch (Exception e) {
            String msgError = "Error al validar los datos de la dirección calle-carrera ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Función para validar datos nulos al momento de construir.una dirección
     * tipo Barrio-Manzana.
     *
     * @return {@code boolean}
     * @author Juan David Hernandez
     * encuentra vacio
     */
    public boolean validarDatosDireccionBm() {
        try {
            if (isInvalidTipoDireccion()) return false;

            if (isInvalidTipoNivel(tipoNivelBm)) return false;

            if (isInvalidCiudad()) return false;

            if (StringUtils.isBlank(nivelValorBm)) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                        "Ingrese un valor en el campo de nivel barrio-manzana por favor.");
                return false;
            }
            return true;
        } catch (Exception e) {
            String msgError = "Error al validar los datos de la dirección barrio-manzana ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Verifica si el tipoNivel no es válido.
     *
     * @param tipoNivel {@link String}
     * @return {@code boolean}
     * @author Gildardo Mora
     */
    private boolean isInvalidTipoNivel(String tipoNivel) {
        if (StringUtils.isBlank(tipoNivel)) {
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                    "Seleccione el TIPO DE NIVEL que desea agregar por favor.");
            return true;
        }
        return false;
    }

    /**
     * Función para validar datos nulos al momento de construir.una dirección
     * tipo Intraducible.
     *
     * @return {@code boolean}
     * @author Juan David Hernandez
     */
    public boolean validarDatosDireccionIt() {
        try {
            if (isInvalidTipoDireccion()) return false;

            if (isInvalidTipoNivel(tipoNivelIt)) return false;

            if (isInvalidCiudad()) return false;

            if (StringUtils.isBlank(nivelValorIt)) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                        "Ingrese un valor en el campo de nivel intraducible por favor");
                return false;
            }

            if (tipoNivelIt.equalsIgnoreCase("CONTADOR") && nivelValorIt.length() > 7) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                        "El valor para Contador no puede exceder los 6 caracteres");
                return false;
            }

            if (tipoNivelIt.equalsIgnoreCase("CONTADOR") && !StringToolUtils.containsOnlyNumbers(nivelValorIt)) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "El valor para Contador debe ser numérico.");
                return false;
            }
            return true;
        } catch (Exception e) {
            String msgError = "Error al validar los datos de la dirección intraducible" ;
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Función que se utiliza para validar el barrio sugerido seleccionado
     *
     * @author Juan David Hernandez
     */
    public void validarBarrioSugeridoSeleccionado() {
        try {
            /*Si selecciona otro renderiza campo de texto en la pantalla para
             * ingresar manualmente el barrio*/
            if (Objects.nonNull(barrioSugerido) && barrioSugerido.equalsIgnoreCase("Otro")) {
                barrioSugeridoField = true;
                barrioSugeridoStr = "";
                request.setBarrio(null);
            } else {
                /*Si selecciona un barrio del listado, este se asigna de inmediato
                 * a la dirección*/
                if (StringUtils.isNotBlank(barrioSugerido)
                        && !barrioSugerido.equalsIgnoreCase("Otro")) {
                    barrioSugeridoStr = barrioSugerido;
                    barrioSugeridoField = false;
                    request.setBarrio(barrioSugerido);
                }
            }
        } catch (Exception e) {
            String msgError = "Error al validar barrio sugerido seleccionado ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que agrega el barrio sugerido ingresado manualmente
     *
     * @author Juan David Hernandez
     */
    public void agregarBarrioSugerido() {
        try {
            if (StringUtils.isBlank(barrioSugeridoStr)) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "Ingrese el nombre de un barrio sugerido por favor.");
            } else {
                barrioSugeridoStr = barrioSugeridoStr.toUpperCase();
                request.setBarrio(barrioSugeridoStr);
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.INFO, "Barrio sugerido agregado exitosamente.");
            }
        } catch (Exception e) {
            String msgError = "Error al agregar barrio sugerido";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función para validar datos nulos al momento de construir una dirección
     * con apartamento.
     *
     * @return return false si algun dato se encuentra vacio
     * @author Juan David Hernandez
     */
    public boolean validarDatosDireccionApartamento() {
        try {
            if (StringUtils.isBlank(tipoAccionSolicitud)) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "Seleccione el TIPO DE SOLICITUD por favor.");
                return false;
            }

            if (isInvalidTipoDireccion()) return false;

            if (isInvalidCiudad()) return false;

            if (tipoAccionSolicitud.equalsIgnoreCase(Constant.RR_DIR_CAMBIO_ESTRATO_2)) {
                tipoApartTmp = tipoNivelApartamento;
                return true;
            }

            if (StringUtils.isBlank(direccionLabel)) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                        "Debe agregar primero la dirección antes de agregar el apartamento");
                return false;
            }

            if (StringUtils.isBlank(tipoNivelApartamento)) {
                String msnError = "Seleccione el TIPO DE NIVEL de"
                        + " apartamento que desea agregar por favor.";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msnError);
                return false;
            }

            if (tipoNivelApartamento.equalsIgnoreCase("CASA")
                    || tipoNivelApartamento.equalsIgnoreCase("ADMINISTRACION")
                    || tipoNivelApartamento.equalsIgnoreCase("FUENTE")
                    || tipoNivelApartamento.equalsIgnoreCase("RECEPTOR")) {
                tipoApartTmp = tipoNivelApartamento;
                return true;
            }

            if (StringUtils.isBlank(apartamento)) {
                String msnError = "Ingrese un valor en el campo apartamento por favor.";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msnError);
                return false;
            }

            if (!validarCombinacionesApartamento()) return false;
            tipoApartTmp = tipoNivelApartamento;
            return true;
        } catch (Exception e) {
            String msgError = "Error al validar datos de dirección apartamento";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Realiza la validación sobre la combinación de apartamento en la dirección
     *
     * @return {@code boolean}
     * @author Gildardo Mora
     */
    private boolean validarCombinacionesApartamento() {
        if (StringUtils.isBlank(tipoTecnologia)) {
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "Seleccione el TIPO DE TECNOLOGÍA por favor.");
            return false;
        }

        if (direccionLabel.contains(tipoNivelApartamento)) {
            String msg = "La dirección ya tiene agregado el complemento " + tipoNivelApartamento;
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msg);
            return false;
        }

        if (combinacionApartNoPermitida("PISO + APTO", "APARTAMENTO")
                || combinacionApartNoPermitida("PISO + INTERIOR", "INTERIOR")
                || combinacionApartNoPermitida("PISO + LOCAL", "LOCAL")) {
            return false;
        }

        //valores que contienen (piso), (piso +), y excepción de (casa + piso)
        if (tipoTecnologiaBasica.getIdentificadorInternoApp() != null
                && (tipoTecnologiaBasica.getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_BID)
                || tipoTecnologiaBasica.getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_FTTTH)
                || tipoTecnologiaBasica.getIdentificadorInternoApp().equalsIgnoreCase(Constant.INTERNET_SOCIAL_MINTIC))
                && tipoNivelApartamento.contains("PI") && !tipoNivelApartamento.contains("CASA")
                && !(apartamento.equalsIgnoreCase("1")
                || apartamento.equalsIgnoreCase("2")
                || apartamento.equalsIgnoreCase("3"))) {

            String msnError = "El valor permitido para PISO solo puede ser 1, 2 o 3";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msnError);
            return false;
        }

        return true;
    }

    /**
     * Verifica si permite la combinación de nivel apartamento
     *
     * @param apartamentoInicial {@link String}
     * @param apartamentoCombinado {@link String}
     * @return {@code boolean} Retorna true si no es permitida la combinación
     * @author Gildardo Mora
     */
    private boolean combinacionApartNoPermitida(String apartamentoInicial, String apartamentoCombinado) {
        if (StringUtils.isNotBlank(tipoApartTmp) && tipoApartTmp.equalsIgnoreCase(apartamentoInicial)
                && !tipoNivelApartamento.equalsIgnoreCase(apartamentoCombinado)) {
            String msg = "El tipo de apartamento en la dirección construida <BR> es " + tipoApartTmp
                    + ". No debe agregar " + tipoNivelApartamento
                    + "<BR> por favor seleccione " + apartamentoCombinado;
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msg);
            return true;
        }

        return false;
    }

    /**
     * Función para validar la construcción de una dirección para la tecnología seleccionada.
     *
     * @param drDireccion          {@link DrDireccion}
     * @param tipoNivelApartamento {@link String}
     * @param apartamento          {@link String}
     * @return Retorna true, si cumple con las validaciones estipuladas para crear la dirección.
     * @author Juan David Hernandez
     */
    public boolean validarDatosDireccionApartamentoParaTecnologiaSeleccionada(DrDireccion drDireccion,
            String tipoNivelApartamento, String apartamento) {
        try {
            //Si es solicitud de cambio de estrato no realizar validación
            if (tipoAccionSolicitud.equalsIgnoreCase(CAMBIO_ESTRATO.getCodigoBasica())) return true;

            if (!notGeoReferenciado) return true;

            if(tipoAccionSolicitud.equalsIgnoreCase(CREACION_HOME_PASSED.getCodigoBasica())){
                Map<String, String> datosValidarApartamento = new HashMap<>();
                datosValidarApartamento.put(ConstantsDirecciones.TIPO_NIVEL_APARTAMENTO, tipoNivelApartamento);
                datosValidarApartamento.put(ConstantsDirecciones.APARTAMENTO, apartamento);
                List<String> codigosTecnologiasPorValidar = cmtBasicaMglFacadeLocal.findCodigosTecnologiasPorValidarEnSolicitud();
                String codigoBasica = tipoTecnologiaBasica.getCodigoBasica();
                datosValidarApartamento.put(ConstantsSolicitudHhpp.CODIGO_BASICA, codigoBasica);

                return direccionesValidacionFacadeLocal.validarConstruccionApartamentoPorTecnologias(
                        drDireccion, codigosTecnologiasPorValidar, datosValidarApartamento);
            }

            return direccionesValidacionFacadeLocal.validarConstruccionApartamentoBiDireccional(
                    drDireccion, tipoTecnologiaBasica.getIdentificadorInternoApp(),
                            tipoNivelApartamento, apartamento);

        } catch (Exception e) {
            String msgError = "Validación de apartamento para tecnología " + tipoTecnologiaBasica.getNombreBasica();
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Función para validar datos nulos al momento de construir una dirección
     * con nuevo apartamento.
     *
     * @return {@code boolean}
     * @author Juan David Hernandez
     * vacio
     */
    public boolean validarDatosDireccionNuevoApartamento() {
        try {
            if (isInvalidTipoDireccion()) return false;

            if (isInvalidCiudad()) return false;

            if (StringUtils.isBlank(tipoNivelNuevoApartamento)) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                        "Seleccione el TIPO DE NIVEL del nuevo apartamento que desea agregar por favor.");
                return false;
            }

            if (tipoNivelNuevoApartamento.equalsIgnoreCase("CASA")
                    || tipoNivelNuevoApartamento.equalsIgnoreCase("ADMINISTRACION")
                    || tipoNivelNuevoApartamento.equalsIgnoreCase("FUENTE")
                    || tipoNivelNuevoApartamento.equalsIgnoreCase("RECEPTOR")) {
                return true;
            }

            if (StringUtils.isBlank(nuevoApartamento)) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "Ingrese un valor en el campo apartamento por favor.");
                return false;
            }

            if (tipoNivelNuevoApartamento.contains("PI") && !tipoNivelNuevoApartamento.contains("CASA")
                    && !(nuevoApartamento.equalsIgnoreCase("1")
                    || nuevoApartamento.equalsIgnoreCase("2")
                    || nuevoApartamento.equalsIgnoreCase("3"))
                    && tipoTecnologiaBasica.getIdentificadorInternoApp() != null
                    && tipoTecnologiaBasica.getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_BID)) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "El valor permitido  para PISO solo puede ser 1, 2 o 3");
                return false;
            }

            return true;
        } catch (Exception e) {
            String msgError = "Error al validar datos de direccion apartamento";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Función que redirecciona a pantalla de gestión de solicitud.
     *
     * @author Juan David Hernandez
     */
    public void goGestionarSolicitudDth() {
        try {
            //Instacia Bean para poder acceder al metodo de redireccionamiento
            GestionSolicitudDthBean gestionSolicitudDthBean
                    = JSFUtil.getSessionBean(GestionSolicitudDthBean.class);

            Objects.requireNonNull(gestionSolicitudDthBean,
                    "El objeto recuperado de la solicitud es nulo");

            //enviamos al método la solicitud recien creada
            gestionSolicitudDthBean.goGestionarSolicitudDesdeCrearSolicitud(solicitudCreated);
        } catch (Exception e) {
            String msgError = "Error al gestionar solicitud ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que renderiza panel de cambio de direccion según la acción
     *
     * @author Juan David Hernandez
     */
    public void validarCambioAccion() {
        try {
            if (tipoAccionSolicitud.equals(Constant.RR_DIR_CAMB_HHPP_1)) {
                cambioDireccionPanel = true;
            } else {
                cambioDireccionPanel = false;
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al validar cambio de acción" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función para validar datos nulos al momento de construir una dirección
     * con complemento.
     *
     * @return {@code boolean}
     * @author Juan David Hernandez
     */
    public boolean validarDatosDireccionComplemento() {
        try {
            if (isInvalidTipoDireccion()) return false;

            if (!tipoAccionSolicitud.equalsIgnoreCase(Constant.RR_DIR_ESCALAMIENTO_HHPP)) {
                if (StringUtils.isBlank(tipoNivelComplemento)) {
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                            "Seleccione TIPO DE NIVEL del complemento por favor.");
                    return false;
                }

                if (isInvalidCiudad()) return false;

                if (StringUtils.isBlank(complemento)) {
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                            "Ingrese un valor en el campo del complemento por favor.");
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            String msgError = "Error al validar datos de dirección complemento";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Función utilizada para validar la dirección contra el Georreferenciador
     *
     * @author Juan David Hernandez
     */
    public void validarDireccion() throws ApplicationException {
        try {
            if (isInvalidTipoSolicitud()) return;

            //valida la direccion para una dirección diferente a la solicitud de cambio de estrato

            //validación de tipo y número de documento del cliente
            if (tipoAccionSolicitud.equals(Constant.RR_DIR_CAMB_HHPP_1) ||
                    tipoAccionSolicitud.equals(Constant.RR_DIR_CAMBIO_ESTRATO_2) ||
                    tipoAccionSolicitud.equals(Constant.RR_DIR_CREA_HHPP_0)) {

                if (solicitud.getTipoDoc() == null || solicitud.getTipoDoc().equals("")) {
                    String msn = "Seleccione un tipo de documento por favor.";
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
                    return;
                }else if(solicitud.getNumDocCliente() == null || solicitud.getNumDocCliente().equals("")){
                    String msn = "Ingrese un número de documento por favor.";
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
                    return;
                }
            }

            if(tipoAccionSolicitud.equals(Constant.TRASLADO_HHPP_BLOQUEADO_5)){
                validarDireccionTrasladoHhppBloqueado();
                return;
            }

            if (!tipoAccionSolicitud.equalsIgnoreCase(Constant.RR_DIR_CAMBIO_ESTRATO_2)) {
                // si la solicitud es de creacion hhpp o cambio de direccion
                if (validarCrearSolicitud()
                        && validarEstructuraDireccion(responseConstruirDireccion.getDrDireccion())) {
                    /*Consume servicio que valida la correcta construcción 
                         de la dirección. */
                    CityEntity cityEntity;
                    try {
                        request.getDrDireccion().setIdSolicitud(new BigDecimal(tipoAccionSolicitud));
                        cityEntity = direccionesValidacionFacadeLocal
                                .validaDireccion(request, ambiguaNueva);

                        responseValidarDireccion = new ResponseValidacionDireccion();
                        ResponseMesaje responseMesaje = new ResponseMesaje();
                        responseMesaje.setMensaje("Proceso Exitoso");
                        responseMesaje.setTipoRespuesta("I");

                        if (request.getDrDireccion().getBarrio() == null || request.getDrDireccion().getBarrio().isEmpty()) {
                            request.getDrDireccion().setBarrio(cityEntity.getBarrio());
                        }
                        //valida que la direccion no este asociada a una Cuenta Matriz
                        if (!validarSolicitudesActivasCuentaMatriz()) {
                            return;
                        }

                        responseValidarDireccion.setResponseMesaje(responseMesaje);
                        request.getDrDireccion().convertToDetalleDireccionEntity().
                                setMultiOrigen(cityEntity.getActualDetalleDireccionEntity().getMultiOrigen());
                        responseValidarDireccion.setDrDireccion(request.getDrDireccion());
                        responseValidarDireccion.setCityEntity(cityEntity);

                        /*La dirección validada y construida despliega los mensajes
                         * de respuesta del sitidata.*/
                        boolean resultMensajesValidacion = mensajesValidacionesDetalleDireccion(cityEntity);

                        /*Validacion de Existencia de la Unidad para las
                             acciones de modificacion de la Unidad */
                        if (tipoAccionSolicitud.equalsIgnoreCase(Constant.RR_DIR_CREA_HHPP_0)) {

                            //Primero se busca inhabilitado para saber si se debe cambiar de tipo de solicitud
                            if (validaExistenciaUnidad(cityEntity, Constant.RR_DIR_REAC_HHPP_3)) {
                                //cambia el tipo de solicitud de creacion a reactivacion
                                tipoAccionSolicitud = Constant.RR_DIR_REAC_HHPP_3;

                                crearSolicitudButton = false;
                                deshabilitarValidar = false;
                                habilitarViabilidad = false;
                                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.INFO,
                                        "El hhpp se encuentra inhabilitado por tal motivo la solicitud "
                                                + "cambia de CREACIÓN a REACTIVACIÓN "
                                                + "y es necesario validar nuevamente la dirección.");
                                return;
                            }

                            //validar si el hhpp de la tecnologia pedida no existe para poderlo crear
                            if (validaExistenciaUnidad(cityEntity, tipoAccionSolicitud)) {
                                crearSolicitudButton = false;
                                deshabilitarValidar = false;
                                habilitarViabilidad = false;
                                hhppExiste = true;

                                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR,
                                        "La unidad ya existe con la tecnología seleccionada. No es posible" +
                                                " crear la solicitud de creación de Hhpp. ");
                                return;
                            }

                        } else if (tipoAccionSolicitud.equalsIgnoreCase(
                                Constant.RR_DIR_CAMB_HHPP_1)) {

                            //SI .C no se valida la existencia ya que es un cambio de antigua por nueva y no debe existir el hhpp
                            if (cityEntity.getExisteRr() != null && !cityEntity.getExisteRr().isEmpty()
                                    && cityEntity.getExisteRr().contains(".(C)") && cityEntity.getHhppMglCambioANueva() != null) {
                                solicitud.setHhppMgl(cityEntity.getHhppMglCambioANueva());
                            } else {
                                if (!validaExistenciaUnidad(cityEntity, tipoAccionSolicitud)) {

                                    /*.C indica que el validar la direccion es nueva y existe en MER 
                                     como antigua y se debe hacer un cambio de direccion*/
                                    crearSolicitudButton = false;
                                    deshabilitarValidar = false;
                                    habilitarViabilidad = false;
                                    hhppExiste = false;
                                    enableApto = false;
                                    cambioDireccionPanel = false;

                                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR,
                                            "La unidad No existe. No es posible crear la solicitud debido a que debe "
                                                    + "existir para hacer el cambio de dirección.");
                                    return;
                                }
                            }
                        }
                        // Validación de estado de hhpp para reactivación del hhpp
                        if (tipoAccionSolicitud.equalsIgnoreCase(
                                Constant.RR_DIR_REAC_HHPP_3)) {
                            try {
                                if (!validaExistenciaUnidad(cityEntity, tipoAccionSolicitud)) {
                                    String msnError;

                                    //validacion para saber si el mensaje que se muestra es de hhpp activo.
                                    if (validaExistenciaUnidad(cityEntity, Constant.RR_DIR_CREA_HHPP_0)) {
                                        msnError = "El HHPP se encuentra en estado Activo. No es posible realizar una reactivación";
                                    } else {
                                        //mensaje de hhpp que no existe en reactivación.
                                        msnError = "El hhpp ingresado no existe en la tecnologia para ser reactivado";
                                    }

                                    crearSolicitudButton = false;
                                    deshabilitarValidar = false;
                                    habilitarViabilidad = false;

                                    FacesContext.getCurrentInstance()
                                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                    msnError, ""));
                                    return;
                                }
                            } catch (ApplicationException e) {
                                crearSolicitudButton = false;
                                deshabilitarValidar = false;
                                habilitarViabilidad = false;
                                resultMensajesValidacion = false;
                                FacesUtil.mostrarMensajeError("Error en validaExistenciaUnidad" + e.getMessage(), e, LOGGER);
                            } catch (Exception e) {
                                crearSolicitudButton = false;
                                deshabilitarValidar = false;
                                habilitarViabilidad = false;
                                resultMensajesValidacion = false;
                                FacesUtil.mostrarMensajeError("Error en validaExistenciaUnidad" + e.getMessage(), e, LOGGER);
                            }
                        }

                        /* La dirección validada y construida pasa por la
                            matriz de viabilidad */
                        if (tipoAccionSolicitud.equalsIgnoreCase(Constant.RR_DIR_CREA_HHPP_0)) {
                            validarMatrizViabilidadInspira(resultMensajesValidacion);
                        }

                        //Se obtienen las unidades asociadas al predio.              
                        if (tipoAccionSolicitud.equals(Constant.RR_DIR_CREA_HHPP_0)) {
                            obtenerUnidadesAsociadasPredio(responseValidarDireccion.getCityEntity(),
                                    idCentroPoblado);

                            boolean validarUnidadesPreviasCasa = false;
                            if (unidadAuxiliarList != null && !unidadAuxiliarList.isEmpty()) {
                                for (UnidadStructPcml unidadStructPcml : unidadAuxiliarList) {
                                    if (unidadStructPcml.getHhppMgl() != null && unidadStructPcml.getHhppMgl().getHhpApart().equalsIgnoreCase("CASA")) {
                                        validarUnidadesPreviasCasa = true;
                                        break;
                                    }
                                }
                            }

                            //SI YA EXISTE UNA CASA EN LAS UNIDADES PREVIAS NO VALIDA LA CREACION DE CASA Y LO DEJA CREAR.
                            if (!validarUnidadesPreviasCasa) {

                                if (responseConstruirDireccion.getDrDireccion() != null
                                        && responseConstruirDireccion.getDrDireccion().getCpTipoNivel5() != null
                                        && !responseConstruirDireccion.getDrDireccion().getCpTipoNivel5().isEmpty()
                                        && responseConstruirDireccion.getDrDireccion().getCpTipoNivel5().equalsIgnoreCase("CASA")
                                        && (responseConstruirDireccion.getDrDireccion().getCpValorNivel5() == null
                                        || responseConstruirDireccion.getDrDireccion().getCpValorNivel5().equals(""))
                                        && unidadAuxiliarList != null && !unidadAuxiliarList.isEmpty()) {
                                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR,
                                            "No es posible continuar con la solicitud para CASA debido a que ya " +
                                                    "existen unidades anteriores en la misma dirección.");
                                    crearSolicitudButton = false;
                                    deshabilitarValidar = false;
                                    habilitarViabilidad = false;
                                    enableApto = false;
                                    return;
                                }
                            }

                        }
                        if (tipoAccionSolicitud.equals(Constant.RR_DIR_CAMB_HHPP_1)) {
                            unidadesPredio = false;
                        }
                        validarTipoSolicitudAccion(cityEntity);
                        cargarCambioDireccion(cityEntity);

                        if (resultMensajesValidacion) {
                            crearSolicitudButton = true;
                            deshabilitarValidar = false;
                            habilitarViabilidad = false;
                            hhppExiste = false;
                            centroPobladoDireccion = geograficoPoliticoMglFacadeLocal.findById(idCentroPoblado);
                            ciudadDireccion = geograficoPoliticoMglFacadeLocal.findById(ciudad);
                            departamentoDireccion = geograficoPoliticoMglFacadeLocal.findById(ciudadDireccion.getGeoGpoId());
                            //Si valida si la direccion existe en el repositorio
                            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.INFO,
                                    "Dirección construida y validada correctamente. ");
                        } else {
                            crearSolicitudButton = false;
                            deshabilitarValidar = false;
                            habilitarViabilidad = false;
                        }
                    } catch (ApplicationException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                        responseValidarDireccion = new ResponseValidacionDireccion();
                        ResponseMesaje responseMesaje = new ResponseMesaje();
                        responseMesaje.setMensaje("Ocurrio Un Error Validando"
                                + " la Dirección " + e.getMessage());
                        responseMesaje.setTipoRespuesta("E");
                        responseValidarDireccion.setResponseMesaje(responseMesaje);
                        responseValidarDireccion.setDrDireccion(request.getDrDireccion());
                        //Si el error no es de multiorigen lo muestre
                        if (!e.getMessage().contains("{multiorigen=1}")) {
                            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, e.getMessage());
                        }
                    }
                    //Si el error no es de multiorigen lo muestre

                    /*Se valida si la respuesta de la excepción es por ciudad
                     * multi-origen*/
                    if (responseValidarDireccion != null
                            && responseValidarDireccion.getResponseMesaje().
                            getTipoRespuesta().equalsIgnoreCase("E")) {
                        validarBarrioCiudadMultiOrigen();
                    }
                }

            } else {
                //Validacion(Busca) para creacion de solicitud cambio de estrato
                if (validarBuscarDireccionCambioEstrato()) {
                    soporteRequerido = false;

                    //si la direccion tiene Avenida y se tiene la direccion del geo sin alteraciones
                    if (responseConstruirDireccion != null
                            && responseConstruirDireccion.getDrDireccion() != null
                            && direccionRespuestaGeo != null
                            && !direccionRespuestaGeo.isEmpty()) {
                        responseConstruirDireccion.getDrDireccion().setDireccionRespuestaGeo(direccionRespuestaGeo);
                    }

                    hhppList = cmtDireccionDetalleMglFacadeLocal
                            .findHhppByDireccion(responseConstruirDireccion.getDrDireccion(),
                                    idCentroPoblado, true, 0, filasPagHhpp, false);

                    hhppList = filtrarListadoMismoHhppDiferenteTech(hhppList);
                    hhppAuxList = copiarHhppList(hhppList);

                    if (hhppList != null && !hhppList.isEmpty()
                            && hhppAuxList != null && !hhppAuxList.isEmpty()) {
                        listInfoByPageHhpp(1);
                        FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.INFO,
                                "Dirección validada correctamente, puede continuar con el proceso de cambio de estrato");
                        crearSolicitudButton = true;
                        deshabilitarValidar = false;
                        direccionesCambioEstrato = true;

                    } else {
                        //Si no se encontraron Hhpp de manera tabulada, realiza busqueda por texto.
                        if (responseConstruirDireccion != null && responseConstruirDireccion.getDireccionRespuestaGeo() != null
                                && !responseConstruirDireccion.getDireccionRespuestaGeo().isEmpty()
                                && responseConstruirDireccion.getDrDireccion() != null) {

                            //busqueda por texto.
                            List<CmtDireccionDetalladaMgl> direccionDetalladaList
                                    = cmtDireccionDetalleMglFacadeLocal.busquedaDireccionTextoRespuestaGeo(responseConstruirDireccion.getDireccionRespuestaGeo(),
                                    responseConstruirDireccion.getDrDireccion(), idCentroPoblado);

                            //si se encontraon direcciones detalladas por texto, se buscan hhpp de esas direcciones
                            if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {
                                hhppList = hhppMglFacadeLocal.obtenerHhppByDireccionDetallaList(direccionDetalladaList);
                            }

                            hhppAuxList = copiarHhppList(hhppList);
                        }
                        if (hhppList != null && !hhppList.isEmpty()
                                && hhppAuxList != null && !hhppAuxList.isEmpty()) {
                            listInfoByPageHhpp(1);
                            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.INFO,
                                    "Dirección validada correctamente, puede continuar con el proceso de cambio de estrato");
                            crearSolicitudButton = true;
                            deshabilitarValidar = false;
                            direccionesCambioEstrato = true;

                        } else {

                            direccionesCambioEstrato = false;
                            crearSolicitudButton = false;
                            deshabilitarValidar = false;
                            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                                    "No se encuentra ningun HHPP asociado a la dirección ingresada para realizar " +
                                            "la solicitud de cambio de estrato.");
                        }
                    }
                }
            }

        } catch (ApplicationException e) {
            String msgError = "Error al validar dirección ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            String msgError = "Ocurrió un error al validar dirección ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Realiza la validación de los datos de dirección
     *
     * @author Gildardo Mora
     */
    private void validarDireccionTrasladoHhppBloqueado() {

        if (Objects.isNull(request)) {
            String msg = "El request de construcción de dirección es nulo, intente de nuevamente mas tarde ...";
            LOGGER.warn(msg);
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, msg);
            return;
        }

        if (!validarCrearSolicitud() || !validarEstructuraDireccion(responseConstruirDireccion.getDrDireccion())) {
            return;
        }

         /* Consume servicio que válida la correcta construcción de la dirección. */
        try {
            CityEntity cityEntity;
            request.getDrDireccion().setIdSolicitud(new BigDecimal(tipoAccionSolicitud));
            cityEntity = direccionesValidacionFacadeLocal.validaDireccion(request, ambiguaNueva);
            responseValidarDireccion = new ResponseValidacionDireccion();
            ResponseMesaje responseMesaje = new ResponseMesaje();
            responseMesaje.setMensaje("Proceso Exitoso");
            responseMesaje.setTipoRespuesta("I");
            String barrioDireccion = Optional.of(request).map(RequestConstruccionDireccion::getDrDireccion).map(DrDireccion::getBarrio).orElse("");

            if (StringUtils.isBlank(barrioDireccion)) {
                request.getDrDireccion().setBarrio(cityEntity.getBarrio());
            }

            //valida que la direccion no este asociada a una Cuenta Matriz
            if (!validarSolicitudesActivasCuentaMatriz()) {
                return;
            }

            responseValidarDireccion.setResponseMesaje(responseMesaje);
            request.getDrDireccion().convertToDetalleDireccionEntity().setMultiOrigen(cityEntity.getActualDetalleDireccionEntity().getMultiOrigen());
            responseValidarDireccion.setDrDireccion(request.getDrDireccion());
            responseValidarDireccion.setCityEntity(cityEntity);
            /* La dirección validada y construida despliega los mensajes de respuesta del SITIDATA.*/
            boolean resultMensajesValidacion = mensajesValidacionesDetalleDireccion(cityEntity);

            //TODO: verificar que no exista el HHPP inhabilitado

            if (!validarSolicitudTrasladoHhppBloqueado(cityEntity)) {
                //si no cumple todas las validaciones, se interrumpe y muestra el msg con los detalles
                return;
            }

            validarTipoSolicitudAccion(cityEntity);
            cargarCambioDireccion(cityEntity);

            if (!resultMensajesValidacion) {
                crearSolicitudButton = false;
                deshabilitarValidar = false;
                habilitarViabilidad = false;
                return;
            }

            crearSolicitudButton = true;
            deshabilitarValidar = false;
            habilitarViabilidad = false;
            hhppExiste = false;
            centroPobladoDireccion = geograficoPoliticoMglFacadeLocal.findById(idCentroPoblado);
            ciudadDireccion = geograficoPoliticoMglFacadeLocal.findById(ciudad);
            departamentoDireccion = geograficoPoliticoMglFacadeLocal.findById(ciudadDireccion.getGeoGpoId());
            String msg = "Dirección construida y validada correctamente. ";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.INFO, msg);

        } catch (Exception e) {
            assignResponseMessageValidarDireccion(e);
            responseValidarDireccion.setDrDireccion(request.getDrDireccion());
            //Si el error no es de multiorigen lo muestre
            if (!e.getMessage().contains("{multiorigen=1}")) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, e.getMessage());
            }

            exceptionServBean.notifyError(e, "Error al validar dirección" + e.getMessage(), SOLICITUDES_HHPP);

        } finally {
            //Si el error no es de multiorigen lo muestre
            /*Se valida si la respuesta de la excepción es por ciudad multi-origen*/
            if (Objects.nonNull(responseValidarDireccion) && responseValidarDireccion.getResponseMesaje().getTipoRespuesta().equalsIgnoreCase("E")) {
                validarBarrioCiudadMultiOrigen();
            }
        }
    }

    /**
     * Asigna el mensaje de error en la respuesta
     *
     * @param e {@link Exception}
     * @author Gildardo Mora
     */
    private void assignResponseMessageValidarDireccion(Exception e) {
        responseValidarDireccion = new ResponseValidacionDireccion();
        ResponseMesaje responseMesaje = new ResponseMesaje();
        responseMesaje.setMensaje("Ocurrio Un Error Validando la Dirección " + e.getMessage());
        responseMesaje.setTipoRespuesta("E");
        responseValidarDireccion.setResponseMesaje(responseMesaje);
    }

    public List<HhppMgl> filtrarListadoMismoHhppDiferenteTech(List<HhppMgl> hhppList) {
        try {

            List<HhppMgl> hhppFilterList = new ArrayList();

            if (hhppList != null && !hhppList.isEmpty()) {
                // copia de los hhpps encontradas para realizar comparacion 
                List<HhppMgl> hhppListTmp = copiarHhppList(hhppList);

                //recorrido del listado original con todos los hhpp
                for (HhppMgl hhppMgl : hhppList) {

                    //si la lista que se va a retornar se encuentra vacia es el primer elemento y se agrega directamente.
                    if (hhppFilterList.isEmpty()) {
                        hhppFilterList.add(hhppMgl);
                    } else {
                        //si la lista ya contiene un hhpp previo se recorre para no agregarlo nuevamente
                        if (!hhppFilterList.isEmpty()) {

                            boolean hhppExistente = false;

                            for (HhppMgl hhppFilterMgl : hhppFilterList) {

                                //Como subdireccionId puede ser null pero es necesario compararlo se utiliza variables para la comparacion
                                BigDecimal idSubDireccion;
                                BigDecimal idSubDireccionFilter;

                                if (hhppMgl.getSubDireccionObj() != null) {
                                    idSubDireccion = hhppMgl.getSubDireccionObj().getSdiId();
                                } else {
                                    idSubDireccion = BigDecimal.ONE;
                                }

                                if (hhppFilterMgl.getSubDireccionObj() != null) {
                                    idSubDireccionFilter = hhppFilterMgl.getSubDireccionObj().getSdiId();
                                } else {
                                    idSubDireccionFilter = BigDecimal.ONE;
                                }

                                //condición de comparación para saber si el hhpp ya se encuentra en la lista
                                if (hhppMgl.getDirId().equals(hhppFilterMgl.getDirId()) && idSubDireccion.equals(idSubDireccionFilter)) {
                                    hhppExistente = true;
                                    break;
                                }
                            }

                            if (!hhppExistente) {
                                hhppFilterList.add(hhppMgl);
                            }
                        }
                    }
                }
            }
            return hhppFilterList;


        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al filtrar los hhpp con diferentes tecnologías" + e.getMessage(),
                    SOLICITUDES_HHPP);
            return null;
        }
    }

    /**
     * Función utilizada para copiar el listado de hhpp
     * a un listado auxiliar que permita realizar la paginacion en pantalla
     *
     * @return {@code boolean}
     * @author Juan David Hernandez
     */
    public boolean validarNuevoEstratoSeleccionado() {
        try {
            if (StringUtils.isBlank(nuevoEstratoHhpp)) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                        "Debe seleccionar el nuevo estrato que desea cambiarle a la dirección");
                return false;
            }

            if (hhppSelected.getSubDireccionObj() != null
                    && hhppSelected.getSubDireccionObj().getSdiEstrato() != null) {
                if (new BigDecimal(nuevoEstratoHhpp).equals(hhppSelected.getSubDireccionObj().getSdiEstrato())) {
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                            "El estrato nuevo seleccionado no debe ser igual al estrato actual de la dirección.");
                    return false;
                }
            } else {
                if (hhppSelected.getDireccionObj() != null
                        && hhppSelected.getDireccionObj().getDirEstrato() != null
                        && new BigDecimal(nuevoEstratoHhpp).equals(hhppSelected.getDireccionObj().getDirEstrato())) {
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                            "El estrato nuevo seleccionado no debe ser igual al estrato actual de la dirección.");
                    return false;
                }
            }

            if (hhppSelected.getDireccionObj() != null && hhppSelected.getSubDireccionObj() == null) {
                List<SubDireccionMgl> subDireccionList = subDireccionMglFacadeLocal
                        .findByIdDireccionMgl(hhppSelected.getDireccionObj().getDirId());

                if (CollectionUtils.isNotEmpty(subDireccionList)) {
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                            "Es una dirección que cuenta con subdirecciones,"
                            + " es necesario que le realice la solicitud de "
                            + "cambio a una de sus subdirecciones");
                    return false;
                }
            }

            return true;

        } catch (ApplicationException e) {
            String msgError = "Error en validar Nuevo Estrato Seleccionado ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        } catch (Exception e) {
            String msgError = "Ocurrió un error al validar Nuevo Estrato Seleccionado ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Función utilizada para copiar el listado de hhpp
     * a un listado auxiliar que permita realizar la paginacion en pantalla
     *
     * @param hhppList {@link List<HhppMgl>}
     * @return {@link List<HhppMgl>}
     * @author Juan David Hernandez
     */
    public List<HhppMgl> copiarHhppList(List<HhppMgl> hhppList) {
        try {
            List<HhppMgl> hhppMglAuxList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(hhppList)) {
                hhppMglAuxList.addAll(hhppList);
            }
            return hhppMglAuxList;
        } catch (Exception e) {
            String msgError = "Error en copiar HhppList ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
            return Collections.emptyList();
        }
    }

    /**
     * Función utilizada para realizar una copia de un objeto DrDireccion
     *
     * @param drDireccionOriginal
     * @author Juan David Hernandez
     */
    public void realizarCopiaDrDireccion(DrDireccion drDireccionOriginal) {
        try {
            drDireccionAmbiguaCopia = new DrDireccion();
            drDireccionAmbiguaCopia.setCpTipoNivel1(drDireccionOriginal.getCpTipoNivel1());
            drDireccionAmbiguaCopia.setCpTipoNivel2(drDireccionOriginal.getCpTipoNivel2());
            drDireccionAmbiguaCopia.setCpTipoNivel3(drDireccionOriginal.getCpTipoNivel3());
            drDireccionAmbiguaCopia.setCpTipoNivel4(drDireccionOriginal.getCpTipoNivel4());
            drDireccionAmbiguaCopia.setCpTipoNivel5(drDireccionOriginal.getCpTipoNivel5());
            drDireccionAmbiguaCopia.setCpTipoNivel6(drDireccionOriginal.getCpTipoNivel6());

            drDireccionAmbiguaCopia.setCpValorNivel1(drDireccionOriginal.getCpValorNivel1());
            drDireccionAmbiguaCopia.setCpValorNivel2(drDireccionOriginal.getCpValorNivel2());
            drDireccionAmbiguaCopia.setCpValorNivel3(drDireccionOriginal.getCpValorNivel3());
            drDireccionAmbiguaCopia.setCpValorNivel4(drDireccionOriginal.getCpValorNivel4());
            drDireccionAmbiguaCopia.setCpValorNivel5(drDireccionOriginal.getCpValorNivel5());
            drDireccionAmbiguaCopia.setCpValorNivel6(drDireccionOriginal.getCpValorNivel6());
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al realizar copia de DrDirección" + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para validar si la excepción arrogada por el servicio
     * de validación es por ser una ciudad multi-origen.
     *
     * @author Juan David Hernandez
     */
    public void validarBarrioCiudadMultiOrigen() {
        try {
            /*Valida si la dirección construida es una dirección 
             Multi-Origen para hacer cargue de listado de barrios sugeridos. */
            if (responseValidarDireccion.getResponseMesaje().
                    getMensaje().contains("{multiorigen=1}")
                    && request.getBarrio() == null) {


                /*Consume servicio que permite obtener un listado de barrios
                 * sugeridos para el usuario. */
                ResponseConstruccionDireccion responseObtenerBarrio;
                try {
                    List<AddressSuggested> barrioList
                            = direccionesValidacionFacadeLocal
                            .obtenerBarrioSugerido(request);

                    responseObtenerBarrio = new ResponseConstruccionDireccion();
                    ResponseMesaje responseMesaje = new ResponseMesaje();
                    responseMesaje.setMensaje("Proceso Exitoso");
                    responseMesaje.setTipoRespuesta("I");
                    responseObtenerBarrio.setResponseMesaje(responseMesaje);
                    responseObtenerBarrio.setDrDireccion(request.getDrDireccion());

                    String dirStr = direccionRRFacadeLocal
                            .getDireccion(request.getDrDireccion());

                    responseObtenerBarrio.setDireccionStr(dirStr);
                    responseObtenerBarrio.setBarrioList(barrioList);
                } catch (Exception e) {
                    responseObtenerBarrio = new ResponseConstruccionDireccion();
                    ResponseMesaje responseMesaje = new ResponseMesaje();
                    responseMesaje.setMensaje("Ocurrio Un Error Obteniendo "
                            + "los barrios "
                            + e.getMessage());
                    responseMesaje.setTipoRespuesta("E");
                    responseObtenerBarrio.setResponseMesaje(responseMesaje);
                    responseObtenerBarrio.setDrDireccion(request.getDrDireccion());
                    exceptionServBean.notifyError(e, "Ocurrió un error obteniendo los barrios: " + e.getMessage(), SOLICITUDES_HHPP);
                }

                /*Valida que se haya obtenido un listado de barrios sugeridos
                 para desplegarlos en pantalla. */
                if (responseObtenerBarrio.getBarrioList() != null) {
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                            "La ciudad es MultiOrigen, es necesario seleccionar un barrio sugerido y validar nuevamente");

                    //Se agrega el valor 'Otro' al listado de barrios sugeridos
                    barrioSugeridoList = new ArrayList<>();
                    barrioSugeridoList = responseObtenerBarrio.getBarrioList();
                    AddressSuggested otro = new AddressSuggested();
                    otro.setAddress("Otro");
                    otro.setNeighborhood("Otro");
                    barrioSugeridoList.add(otro);
                    barrioSugeridoPanel = true;
                } else {
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                            "La ciudad es MultiOrigen y no fue posibleobtener barrios sugeridos. Seleccione el valor"
                                    + " 'Otro' e ingreselo manualmente por favor");
                    //Se agrega el valor 'Otro' al listado de barrios sugeridos
                    barrioSugeridoList = new ArrayList<>();
                    AddressSuggested otro = new AddressSuggested();
                    otro.setAddress("Otro");
                    otro.setNeighborhood("Otro");
                    barrioSugeridoList.add(otro);
                    barrioSugeridoPanel = true;
                }
            }
        } catch (Exception e) {
            String msgError = "Error al validar barrio de la dirección ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para cargar la direccion cuando se ha seleccionado un
     * cambio de direccion como tipo de accion.
     *
     * @param cityEntity {@link CityEntity}
     * @author Juan David Hernandez
     */
    public void cargarCambioDireccion(CityEntity cityEntity) {
        try {
            if (cityEntity != null) {
                if (tipoAccionSolicitud.equals(Constant.RR_DIR_CAMB_HHPP_1)
                        && cityEntity.getExisteRr().contains("(C)")) {
                    if (cityEntity.getDireccionRREntityAntigua() != null) {
                        solicitud.setStreetName(cityEntity.getDireccionRREntityAntigua()
                                .getCalle().toUpperCase());
                        solicitud.setHouseNumber(cityEntity.getDireccionRREntityAntigua()
                                .getNumeroUnidad());
                        solicitud.setAparmentNumber(cityEntity.getDireccionRREntityAntigua()
                                .getNumeroApartamento());
                    }
                    return;
                }

                if (tipoAccionSolicitud.equals(Constant.RR_DIR_CAMB_HHPP_1)
                        && cityEntity.getDireccionRREntityNueva() != null) {
                    solicitud.setStreetName(cityEntity.getDireccionRREntityNueva()
                            .getCalle().toUpperCase());
                    solicitud.setHouseNumber(cityEntity.getDireccionRREntityNueva()
                            .getNumeroUnidad());
                    solicitud.setAparmentNumber(cityEntity.getDireccionRREntityNueva()
                            .getNumeroApartamento());
                }
            }
        } catch (Exception e) {
            String msgError = "Error al cargar dirección antigua ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada desplegar los mensajes en pantalla correspondientes a
     * las respuestas obtenidas por el web service que valida la dirección.
     *
     * @param cityEntity
     * @return
     * @author Juan David Hernandez return false cuando se trata de una opción
     * en la que no puede continuar con la solicitud
     */
    public boolean mensajesValidacionesDetalleDireccion(CityEntity cityEntity) {
        try {
            boolean result = true;
            if (cityEntity != null) {
                //Si el tipo de accion no es una reactivación.  
                if (!tipoAccionSolicitud.equalsIgnoreCase(Constant.RR_DIR_REAC_HHPP_3) && !tipoAccionSolicitud.equalsIgnoreCase(
                        Constant.RR_DIR_CAMB_HHPP_1)) {
                    /* Si la dirección ya existe en RR como Antigua y se esta
                     * ingresando una dirección nueva */
                    if (cityEntity.getExisteRr().contains("(C)")) {

                        FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, cityEntity.getExisteRr());
                        tipoAccionSolicitud = Constant.RR_DIR_CAMB_HHPP_1;

                    } else {

                        if (cityEntity.getExisteRr() != null
                                && !cityEntity.getExisteRr().isEmpty()
                                && cityEntity.getExisteRr().contains("(N)")) {

                            result = false;
                            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, cityEntity.getExisteRr());

                        } else {
                            //Si la direccion tiene una direccion antigua pero no un hhpp
                            if (cityEntity.getExisteRr() != null
                                    && !cityEntity.getExisteRr().isEmpty()) {
                                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, cityEntity.getExisteRr());
                            }
                        }
                    }
                } else {
                    if (tipoAccionSolicitud.equalsIgnoreCase(
                            Constant.RR_DIR_CAMB_HHPP_1)) {
                        if (cityEntity.getExisteRr().contains("(C)")) {
                            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, cityEntity.getExisteRr());

                        }
                    }
                }


                DrDireccion dirValidar = new DrDireccion();
                if (cityEntity.getDetalleDireccionEntity() != null) {
                    String direccionTemporalAntigua = direccionLabel;
                    dirValidar.obtenerFromDetalleDireccionEntity(cityEntity.getDetalleDireccionEntity());
                    //obtiene la dirección nueva actual         
                } else {
                    if (cityEntity.getActualDetalleDireccionEntity() != null) {
                        dirValidar.obtenerFromDetalleDireccionEntity(cityEntity.getActualDetalleDireccionEntity());
                    }
                }

                /* se reemplaza la dirección ingresada por la traducida 
                 en los labels en pantalla. */
                direccionLabel = direccionRRFacadeLocal.getDireccion(dirValidar);
                direccionCk = direccionRRFacadeLocal.getDireccion(dirValidar);
            }
            return result;
        } catch (Exception e) {
            String msgError = "Error al obtener los mensajes de validación de la dirección ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Valida si Existe la unidad.Función utilizada para validar si la unidad
     * existe
     *
     * @param cityEntity objeto en el que viene cargada la direccion que se
     *                   desea validar su existencia
     * @param tipoAccion tipo de accion de la solicitud para validar. 3.
     *                   reactivación.
     * @return verdarero si la unidad Existe en Repositorio
     * @throws ApplicationException Excepción en caso de error.
     * @author Juan David Hernandez
     */
    public boolean validaExistenciaUnidad(CityEntity cityEntity,
                                          String tipoAccion) throws ApplicationException {
        try {
            HhppMgl hhppMgl;
            if (responseConstruirDireccion.getDireccionRespuestaGeo() != null
                    && !responseConstruirDireccion.getDireccionRespuestaGeo().isEmpty()
                    && cityEntity != null) {
                CityEntity cityEntityTmp = cityEntity.clone();
                cityEntityTmp.setDireccionRespuestaGeo(responseConstruirDireccion.getDireccionRespuestaGeo());
                hhppMgl = hhppMglFacadeLocal.validaExistenciaHhpp(
                        cityEntityTmp, centroPobladoSeleccionado.getGpoId(),
                        tipoAccion, tipoTecnologiaBasica.getBasicaId());
            } else {
                hhppMgl = hhppMglFacadeLocal.validaExistenciaHhpp(
                        cityEntity, centroPobladoSeleccionado.getGpoId(),
                        tipoAccion, tipoTecnologiaBasica.getBasicaId());
            }

            solicitud.setHhppMgl(hhppMgl);
            //si la unidad existe
            return hhppMgl != null && hhppMgl.getHhpId() != null;
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al validar existencia de unidad " + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        } catch (CloneNotSupportedException e) {
            exceptionServBean.notifyError(e, "Error en valida Existencia unidad: " + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Función utilizada para obtener las unidades asociadas que tiene un predio
     * de la dirección nueva y antigua.
     *
     * @param cityEntity
     * @param centroPobladoId
     * @author Juan David Hernandez
     */
    public void obtenerUnidadesAsociadasPredio(CityEntity cityEntity, BigDecimal centroPobladoId) {
        try {
            ArrayList<UnidadStructPcml> unidadesVecinasConflictosAntiguas = new ArrayList();
            ArrayList<UnidadStructPcml> unidadesVecinasConflictosNuevas = new ArrayList();
            unidadList = new ArrayList<>();
            unidadAuxiliarList = new ArrayList<>();

            if (cityEntity != null && cityEntity.getDireccionAntiguaGeo() != null
                    || cityEntity.getDireccionNuevaGeo() != null) {

                DrDireccion direccionAntiguaGeoTmp = null;
                DrDireccion direccionNuevaGeoTmp = null;

                if (cityEntity != null && cityEntity.getDireccionAntiguaGeo() != null) {
                    direccionAntiguaGeoTmp = cityEntity.getDireccionAntiguaGeo().clone();
                    asignarDireccionAntigua(cityEntity);
                }
                if (cityEntity != null && cityEntity.getDireccionNuevaGeo() != null) {
                    direccionNuevaGeoTmp = cityEntity.getDireccionNuevaGeo().clone();
                }

                if (cityEntity != null && cityEntity.getDireccionRespuestaGeo() != null
                        && !cityEntity.getDireccionRespuestaGeo().isEmpty() && direccionNuevaGeoTmp != null) {
                    direccionNuevaGeoTmp.setDireccionRespuestaGeo(cityEntity.getDireccionRespuestaGeo());
                }

                //Cargue de unidades asociadas al predio desde MGL
                // Si tiene dirección nueva se buscan unidades con la direccion nueva.
                if (direccionNuevaGeoTmp != null) {
                    direccionNuevaGeoTmp.setCpTipoNivel5(null);
                    direccionNuevaGeoTmp.setCpTipoNivel6(null);
                    direccionNuevaGeoTmp.setCpValorNivel5(null);
                    direccionNuevaGeoTmp.setCpValorNivel6(null);

                    if (direccionRespuestaGeo != null && direccionRespuestaGeo.isEmpty()) {
                        direccionNuevaGeoTmp.setDireccionRespuestaGeo(direccionRespuestaGeo);
                    }

                    //Obtenemos los hhpp asociados a la direccion y subdirección.
                    List<HhppMgl> hhppList = cmtDireccionDetalleMglFacadeLocal
                            .findHhppByDireccion(direccionNuevaGeoTmp, centroPobladoId, true,
                                    0, 0, false);

                    //Los hhpp encontrados los pasamos a la estructura de unidades.
                    if (hhppList != null && !hhppList.isEmpty()) {

                        int idIncremental = 0;
                        for (HhppMgl hhppMgl : hhppList) {
                            UnidadStructPcml unidad = new UnidadStructPcml();
                            idIncremental++;
                            unidad.setIdUnidad(new BigDecimal(idIncremental));
                            unidad.setCalleUnidad(hhppMgl.getHhpCalle() != null
                                    ? hhppMgl.getHhpCalle() : "");
                            unidad.setCasaUnidad(hhppMgl.getHhpPlaca() != null
                                    ? hhppMgl.getHhpPlaca() : "");
                            unidad.setAptoUnidad(hhppMgl.getHhpApart() != null
                                    ? hhppMgl.getHhpApart() : "");
                            unidad.setEstadUnidadad(hhppMgl.getEhhId() != null ? hhppMgl.getEhhId().getEhhNombre() : "");
                            //Conversion estrato 
                            if (hhppMgl.getSubDireccionObj() != null) {
                                if (hhppMgl.getSubDireccionObj().getSdiEstrato() != null
                                        && hhppMgl.getSubDireccionObj().getSdiEstrato().equals(new BigDecimal(-1))) {
                                    unidad.setEstratoUnidad("NG");
                                } else {
                                    if (hhppMgl.getSubDireccionObj().getSdiEstrato() != null
                                            && hhppMgl.getSubDireccionObj().getSdiEstrato().equals(new BigDecimal(0))) {
                                        unidad.setEstratoUnidad("NR");
                                    } else {
                                        if (hhppMgl.getSubDireccionObj().getSdiEstrato() != null) {
                                            unidad.setEstratoUnidad(hhppMgl.getSubDireccionObj().getSdiEstrato().toString());
                                        }
                                    }
                                }
                            } else {
                                if (hhppMgl.getDireccionObj().getDirEstrato() != null) {
                                    if (hhppMgl.getDireccionObj().getDirEstrato() != null
                                            && hhppMgl.getDireccionObj().getDirEstrato().equals(new BigDecimal(-1))) {
                                        unidad.setEstratoUnidad("NG");
                                    } else {
                                        if (hhppMgl.getDireccionObj().getDirEstrato() != null
                                                && hhppMgl.getDireccionObj().getDirEstrato().equals(new BigDecimal(0))) {
                                            unidad.setEstratoUnidad("NR");
                                        } else {
                                            unidad.setEstratoUnidad(hhppMgl.getDireccionObj().getDirEstrato().toString());
                                        }
                                    }
                                }
                            }

                            unidad.setNodUnidad(hhppMgl.getNodId().getNodCodigo() != null
                                    ? hhppMgl.getNodId().getNodCodigo() : "");
                            unidad.setTecnologiaHabilitadaId(hhppMgl.getHhpId());
                            unidad.setTecnologiaId(hhppMgl.getNodId().getNodTipo().getBasicaId());
                            unidad.setNombreTecnologia(hhppMgl.getNodId().getNodTipo().getNombreBasica());
                            unidad.setConflictApto("0");
                            //hhpp completo
                            unidad.setHhppMgl(hhppMgl);

                            if (unidadList != null && !unidadList.isEmpty()) {
                                List<UnidadStructPcml> copiaUnidadList = copiaUnidadesValidacionHhppMismaDir(unidadList);
                                boolean hhppRepetida = false;
                                for (UnidadStructPcml unidadValidada : copiaUnidadList) {
                                    /*Si el hhpp tiene direccion y subdireccion valida con 
                                     los que se han agregado y que tambien tienen direccion y subdireccion*/
                                    if (hhppMgl.getDireccionObj() != null
                                            && hhppMgl.getDireccionObj().getDirId() != null
                                            && hhppMgl.getSubDireccionObj() != null
                                            && hhppMgl.getSubDireccionObj().getSdiId() != null
                                            && unidadValidada.getHhppMgl() != null
                                            && unidadValidada.getHhppMgl().getDireccionObj() != null
                                            && unidadValidada.getHhppMgl().getDireccionObj().getDirId() != null
                                            && unidadValidada.getHhppMgl().getSubDireccionObj() != null
                                            && unidadValidada.getHhppMgl().getSubDireccionObj().getSdiId() != null) {

                                        if (hhppMgl.getDireccionObj().getDirId().equals(unidadValidada.getHhppMgl().getDireccionObj().getDirId())
                                                && hhppMgl.getSubDireccionObj().getSdiId()
                                                .equals(unidadValidada.getHhppMgl().getSubDireccionObj().getSdiId())) {
                                            hhppRepetida = true;
                                        }
                                    } else {
                                        if (hhppMgl.getDireccionObj() != null
                                                && hhppMgl.getDireccionObj().getDirId() != null
                                                && hhppMgl.getSubDireccionObj() == null
                                                && unidadValidada.getHhppMgl() != null
                                                && unidadValidada.getHhppMgl().getDireccionObj() != null
                                                && unidadValidada.getHhppMgl().getDireccionObj().getDirId() != null
                                                && unidadValidada.getHhppMgl().getSubDireccionObj() == null) {

                                            if (hhppMgl.getDireccionObj().getDirId().equals(unidadValidada.getHhppMgl().getDireccionObj().getDirId())) {
                                                hhppRepetida = true;
                                            }
                                        }
                                    }
                                }
                                if (!hhppRepetida) {
                                    unidadList.add(unidad);
                                    unidadAuxiliarList.add(unidad);
                                    //se crea listado para ser comparado en la validación de unidades con conflicto
                                    unidadesVecinasConflictosNuevas.add(unidad);
                                }

                            } else {
                                //primera vez cuando no hay ningun registro en la lista
                                unidadList.add(unidad);
                                unidadAuxiliarList.add(unidad);
                                //se crea listado para ser comparado en la validación de unidades con conflicto
                                unidadesVecinasConflictosNuevas.add(unidad);
                            }
                        }
                    }
                }

                /*Se valida si la dirección georeferenciada cuenta
                 * con dirección antigua y la georeferenciamos para
                 * obtener el drDireccion de la dirección antigua*/
                if (direccionAntiguaGeoTmp != null) {
                    direccionAntiguaGeoTmp.setCpTipoNivel5(null);
                    direccionAntiguaGeoTmp.setCpTipoNivel6(null);
                    direccionAntiguaGeoTmp.setCpValorNivel5(null);
                    direccionAntiguaGeoTmp.setCpValorNivel6(null);

                    //Obtenemos los hhpp asociados a la dirección antigua
                    List<HhppMgl> hhppDirAntiguaList = cmtDireccionDetalleMglFacadeLocal
                            .findHhppByDireccion(direccionAntiguaGeoTmp, centroPobladoId, true,
                                    0, 0, false);
                    //Los hhpp encontrados los pasamos a la estructura de unidades.
                    if (hhppDirAntiguaList != null && !hhppDirAntiguaList.isEmpty()) {
                        /*asignamos el listado de unidades antiguas para cambio de 
                         dirección a la nueva en la solicitud.*/
                        responseValidarDireccion.getCityEntity()
                                .setUnidadesAsociadasPredioAntiguasList(hhppDirAntiguaList);

                        int idIncremental = 100;
                        for (HhppMgl hhppMgl : hhppDirAntiguaList) {
                            UnidadStructPcml unidad = new UnidadStructPcml();
                            idIncremental++;
                            unidad.setIdUnidad(new BigDecimal(idIncremental));
                            unidad.setCalleUnidad(hhppMgl.getHhpCalle() != null
                                    ? hhppMgl.getHhpCalle() : "");
                            unidad.setCasaUnidad(hhppMgl.getHhpPlaca() != null
                                    ? hhppMgl.getHhpPlaca() : "");
                            unidad.setAptoUnidad(hhppMgl.getHhpApart() != null
                                    ? hhppMgl.getHhpApart() : "");
                            unidad.setEstadUnidadad(hhppMgl.getEhhId() != null
                                    ? hhppMgl.getEhhId().getEhhNombre() : "");
                            //Conversion estrato
                            if (hhppMgl.getSubDireccionObj() != null) {
                                if (hhppMgl.getSubDireccionObj().getSdiEstrato() != null
                                        && hhppMgl.getSubDireccionObj().getSdiEstrato().equals(new BigDecimal(-1))) {
                                    unidad.setEstratoUnidad("NG");
                                } else {
                                    if (hhppMgl.getSubDireccionObj().getSdiEstrato() != null
                                            && hhppMgl.getSubDireccionObj().getSdiEstrato().equals(new BigDecimal(0))) {
                                        unidad.setEstratoUnidad("NR");
                                    } else {
                                        if (hhppMgl.getSubDireccionObj().getSdiEstrato() != null) {
                                            unidad.setEstratoUnidad(hhppMgl.getSubDireccionObj().getSdiEstrato().toString());
                                        }
                                    }
                                }
                            } else {
                                if (hhppMgl.getDireccionObj().getDirEstrato() != null) {
                                    if (hhppMgl.getDireccionObj().getDirEstrato().equals(new BigDecimal(-1))) {
                                        unidad.setEstratoUnidad("NG");
                                    } else {
                                        if (hhppMgl.getDireccionObj().getDirEstrato().equals(new BigDecimal(0))) {
                                            unidad.setEstratoUnidad("NR");
                                        } else {
                                            unidad.setEstratoUnidad(hhppMgl.getDireccionObj().getDirEstrato().toString());
                                        }
                                    }
                                }
                            }
                            unidad.setNodUnidad(hhppMgl.getNodId().getNodCodigo() != null
                                    ? hhppMgl.getNodId().getNodCodigo() : "");
                            unidad.setTecnologiaHabilitadaId(hhppMgl.getHhpId());
                            unidad.setTecnologiaId(hhppMgl.getNodId().getNodTipo().getBasicaId());
                            unidad.setNombreTecnologia(hhppMgl.getNodId().getNodTipo().getNombreBasica());
                            unidad.setConflictApto("0");
                            //hhpp completo
                            unidad.setHhppMgl(hhppMgl);

                            if (unidadList != null && !unidadList.isEmpty()) {
                                List<UnidadStructPcml> copiaUnidadList = copiaUnidadesValidacionHhppMismaDir(unidadList);
                                boolean hhppRepetida = false;
                                for (UnidadStructPcml unidadValidada : copiaUnidadList) {
                                    /*Si el hhpp tiene direccion y subdireccion valida con 
                                     los que se han agregado y que tambien tienen direccion y subdireccion*/
                                    if (hhppMgl.getDireccionObj() != null
                                            && hhppMgl.getDireccionObj().getDirId() != null
                                            && hhppMgl.getSubDireccionObj() != null
                                            && hhppMgl.getSubDireccionObj().getSdiId() != null
                                            && unidadValidada.getHhppMgl() != null
                                            && unidadValidada.getHhppMgl().getDireccionObj() != null
                                            && unidadValidada.getHhppMgl().getDireccionObj().getDirId() != null
                                            && unidadValidada.getHhppMgl().getSubDireccionObj() != null
                                            && unidadValidada.getHhppMgl().getSubDireccionObj().getSdiId() != null) {

                                        if (hhppMgl.getDireccionObj().getDirId().equals(unidadValidada.getHhppMgl().getDireccionObj().getDirId())
                                                && hhppMgl.getSubDireccionObj().getSdiId()
                                                .equals(unidadValidada.getHhppMgl().getSubDireccionObj().getSdiId())) {
                                            hhppRepetida = true;
                                        }
                                    } else {
                                        if (hhppMgl.getDireccionObj() != null
                                                && hhppMgl.getDireccionObj().getDirId() != null
                                                && hhppMgl.getSubDireccionObj() == null
                                                && unidadValidada.getHhppMgl() != null
                                                && unidadValidada.getHhppMgl().getDireccionObj() != null
                                                && unidadValidada.getHhppMgl().getDireccionObj().getDirId() != null
                                                && unidadValidada.getHhppMgl().getSubDireccionObj() == null) {

                                            if (hhppMgl.getDireccionObj().getDirId().equals(unidadValidada.getHhppMgl().getDireccionObj().getDirId())) {
                                                hhppRepetida = true;
                                            }
                                        }
                                    }
                                }

                                if (!hhppRepetida) {
                                    unidadList.add(unidad);
                                    unidadAuxiliarList.add(unidad);
                                    //se crea listado para ser comparado en la validación de unidades con conflicto
                                    unidadesVecinasConflictosAntiguas.add(unidad);
                                }

                            } else {
                                //primera vez cuando no hay ningun registro en la lista  
                                unidadList.add(unidad);
                                unidadAuxiliarList.add(unidad);
                                //se crea listado para ser comparado en la validación de unidades con conflicto
                                unidadesVecinasConflictosAntiguas.add(unidad);
                            }
                        }
                    }
                }
            } else {

                if (cityEntity != null && cityEntity.getDireccionPrincipal() != null
                        && cityEntity.getDireccionAntiguaGeo() == null
                        || cityEntity.getDireccionNuevaGeo() == null) {
                    DrDireccion direccionPrincipalTmp = cityEntity.getDireccionPrincipal().clone();
                    direccionPrincipalTmp.setCpTipoNivel5(null);
                    direccionPrincipalTmp.setCpTipoNivel6(null);
                    direccionPrincipalTmp.setCpValorNivel5(null);
                    direccionPrincipalTmp.setCpValorNivel6(null);

                    //Obtenemos los hhpp asociados a la dirección BM o IT
                    List<HhppMgl> hhppBMITList = cmtDireccionDetalleMglFacadeLocal
                            .findHhppByDireccion(direccionPrincipalTmp, centroPobladoId, true,
                                    0, 0, false);

                    //Los hhpp encontrados los pasamos a la estructura de unidades.
                    if (hhppBMITList != null && !hhppBMITList.isEmpty()) {
                        int idIncremental = 200;
                        for (HhppMgl hhppMgl : hhppBMITList) {
                            UnidadStructPcml unidad = new UnidadStructPcml();
                            idIncremental++;
                            unidad.setIdUnidad(new BigDecimal(idIncremental));
                            unidad.setCalleUnidad(hhppMgl.getHhpCalle() != null
                                    ? hhppMgl.getHhpCalle() : "");
                            unidad.setCasaUnidad(hhppMgl.getHhpPlaca() != null
                                    ? hhppMgl.getHhpPlaca() : "");
                            unidad.setAptoUnidad(hhppMgl.getHhpApart() != null
                                    ? hhppMgl.getHhpApart() : "");
                            unidad.setEstadUnidadad(hhppMgl.getEhhId()
                                    != null ? hhppMgl.getEhhId().getEhhNombre() : "");
                            //Conversion estrato
                            //conversion de estrato
                            if (hhppMgl.getSubDireccionObj() != null) {
                                if (hhppMgl.getSubDireccionObj().getSdiEstrato().equals(new BigDecimal(-1))) {
                                    unidad.setEstratoUnidad("NG");
                                } else {
                                    if (hhppMgl.getSubDireccionObj().getSdiEstrato().equals(new BigDecimal(0))) {
                                        unidad.setEstratoUnidad("NR");
                                    } else {
                                        unidad.setEstratoUnidad(hhppMgl.getSubDireccionObj().getSdiEstrato().toString());
                                    }
                                }
                            } else {
                                if (hhppMgl.getDireccionObj().getDirEstrato() != null) {
                                    if (hhppMgl.getDireccionObj().getDirEstrato().equals(new BigDecimal(-1))) {
                                        unidad.setEstratoUnidad("NG");
                                    } else {
                                        if (hhppMgl.getDireccionObj().getDirEstrato().equals(new BigDecimal(0))) {
                                            unidad.setEstratoUnidad("NR");
                                        } else {
                                            unidad.setEstratoUnidad(hhppMgl.getDireccionObj().getDirEstrato().toString());
                                        }
                                    }
                                }
                            }
                            unidad.setNodUnidad(hhppMgl.getNodId().getNodCodigo() != null
                                    ? hhppMgl.getNodId().getNodCodigo() : "");
                            unidad.setTecnologiaHabilitadaId(hhppMgl.getHhpId());
                            unidad.setTecnologiaId(hhppMgl.getNodId().getNodTipo().getBasicaId());
                            unidad.setNombreTecnologia(hhppMgl.getNodId().getNodTipo().getNombreBasica());
                            unidad.setConflictApto("0");
                            //hhpp completo
                            unidad.setHhppMgl(hhppMgl);

                            if (unidadList != null && !unidadList.isEmpty()) {
                                List<UnidadStructPcml> copiaUnidadList = copiaUnidadesValidacionHhppMismaDir(unidadList);
                                boolean hhppRepetida = false;
                                for (UnidadStructPcml unidadValidada : copiaUnidadList) {
                                    /*Si el hhpp tiene direccion y subdireccion valida con 
                                     los que se han agregado y que tambien tienen direccion y subdireccion*/
                                    if (hhppMgl.getDireccionObj() != null
                                            && hhppMgl.getDireccionObj().getDirId() != null
                                            && hhppMgl.getSubDireccionObj() != null
                                            && hhppMgl.getSubDireccionObj().getSdiId() != null
                                            && unidadValidada.getHhppMgl() != null
                                            && unidadValidada.getHhppMgl().getDireccionObj() != null
                                            && unidadValidada.getHhppMgl().getDireccionObj().getDirId() != null
                                            && unidadValidada.getHhppMgl().getSubDireccionObj() != null
                                            && unidadValidada.getHhppMgl().getSubDireccionObj().getSdiId() != null) {

                                        if (hhppMgl.getDireccionObj().getDirId().equals(unidadValidada.getHhppMgl().getDireccionObj().getDirId())
                                                && hhppMgl.getSubDireccionObj().getSdiId()
                                                .equals(unidadValidada.getHhppMgl().getSubDireccionObj().getSdiId())) {
                                            hhppRepetida = true;
                                        }
                                    } else {
                                        if (hhppMgl.getDireccionObj() != null
                                                && hhppMgl.getDireccionObj().getDirId() != null
                                                && hhppMgl.getSubDireccionObj() == null
                                                && unidadValidada.getHhppMgl() != null
                                                && unidadValidada.getHhppMgl().getDireccionObj() != null
                                                && unidadValidada.getHhppMgl().getDireccionObj().getDirId() != null
                                                && unidadValidada.getHhppMgl().getSubDireccionObj() == null) {

                                            if (hhppMgl.getDireccionObj().getDirId().equals(unidadValidada.getHhppMgl().getDireccionObj().getDirId())) {
                                                hhppRepetida = true;
                                            }
                                        }
                                    }
                                }
                                if (!hhppRepetida) {
                                    unidadList.add(unidad);
                                    unidadAuxiliarList.add(unidad);
                                }
                            } else {
                                //primera vez cuando no hay ningun registro en la lista
                                unidadList.add(unidad);
                                unidadAuxiliarList.add(unidad);
                            }
                        }
                    }
                }
            }

            //se asignan las unidades antiguas para validacion de conflictos
            if (unidadesVecinasConflictosAntiguas != null && !unidadesVecinasConflictosAntiguas.isEmpty()) {
                responseValidarDireccion.getCityEntity()
                        .setUnidadStructPcmlAntiguasList(unidadesVecinasConflictosAntiguas);
            }
            //se asignan las unidades nuevas para validacion de conflictos
            if (unidadesVecinasConflictosNuevas != null && !unidadesVecinasConflictosNuevas.isEmpty()) {
                responseValidarDireccion.getCityEntity()
                        .setUnidadStructPcmlNuevasList(unidadesVecinasConflictosNuevas);
            }

            if (unidadList != null && !unidadList.isEmpty()) {
                unidadesPredio = true;

                listInfoByPage(1);
                String msn = "El predio cuenta con unidades asociadas.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, msn,
                                ""));
            }

            /* Listados de opciones para cambio de apartamento cuando un predio 
             tiene unidades asociadas */
            ConfigurationAddressComponent configurationComponentListadoUnidades
                    = componenteDireccionesMglFacade
                    .getConfiguracionComponente(tipoTecnologiaBasica.getIdentificadorInternoApp());

            listNivel5 = configurationComponentListadoUnidades.getAptoValues()
                    .getTiposApto();
            listNivel6 = componenteDireccionesMglFacade.traerListasPorTipo(Constant.TIPO_APTO_COMP);

        } catch (ApplicationException | CloneNotSupportedException e) {
            exceptionServBean.notifyError(e, "Error al obtener las unidades asociadas al predio " + e.getMessage(),
                    SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para realizar una copia de los hhpp asociados a la
     * direccion encontrados y poder validar si ya se encuentran en el listado
     * con diferente tecnologia para no mostrarlos en pantalla
     *
     * @param unidadesHhppList listado de hhpp
     * @return
     * @author Juan David Hernandez
     */
    public List<UnidadStructPcml> copiaUnidadesValidacionHhppMismaDir(List<UnidadStructPcml> unidadesHhppList) {
        try {
            List<UnidadStructPcml> copiaUnidadesHhpp = new ArrayList<UnidadStructPcml>();

            if (unidadesHhppList != null && !unidadesHhppList.isEmpty()) {
                for (UnidadStructPcml unidadStructPcml : unidadesHhppList) {
                    UnidadStructPcml unidadCopia = unidadStructPcml.clone();
                    copiaUnidadesHhpp.add(unidadCopia);
                }
            }
            return copiaUnidadesHhpp;

        } catch (CloneNotSupportedException e) {
            exceptionServBean.notifyError(e, "Error copia Unidades Validacion Hhpp Misma Dirección" + e.getMessage(),
                    SOLICITUDES_HHPP);
            return null;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error copia Unidades Validacion Hhpp Misma Dirección" + e.getMessage(),
                    SOLICITUDES_HHPP);
            return null;
        }
    }

    /**
     * Función utilizada para asigar en el campo de OFICINA de la solicitud la
     * dirección antigua del hhpp si llega a tener.Este es separado por &; para
     * posteriormente ser dividido y obtener la dirección.
     *
     * @param cityEntity
     * @author Juan David Hernandez
     */
    public void asignarDireccionAntigua(CityEntity cityEntity) {
        try {
            /*Se construye la dirección antigua para guardarla
             en el Campo OFICINA de la solicitud */
            if (cityEntity.getDireccionRREntityAntigua() != null) {
                String direccionAntigua = cityEntity.getDireccionRREntityAntigua()
                        .getCalle() + "&"
                        + cityEntity.getDireccionRREntityAntigua()
                        .getNumeroUnidad() + "&"
                        + cityEntity.getDireccionRREntityAntigua()
                        .getNumeroApartamento();

                solicitud.setDireccionAntiguaIgac(direccionAntigua);
            }
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error en asignar dirección antigua " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para validar si existen valores repetidos en el listado
     * de unidades debido a que no se debe estar ingresando una unidad ya
     * existente
     *
     * @return
     * @author Juan David Hernandez
     */
    public boolean validarUnidadesRepetidas() {
        try {
            boolean result = true;
            if (unidadesPredio) {
                copiarInformacionUnidades();
                if (unidadAuxiliarList != null && !unidadAuxiliarList.isEmpty()) {
                    List<UnidadStructPcml> unidadesPredioList = new ArrayList<>();

                    //Se realiza copia con la que se realizara la comparación.
                    for (UnidadStructPcml unidadAux : unidadAuxiliarList) {
                        UnidadStructPcml unidadStructPcml = new UnidadStructPcml();
                        unidadStructPcml.setIdUnidad(unidadAux.getIdUnidad());
                        unidadStructPcml.setAptoUnidad(unidadAux.getAptoUnidad());
                        unidadStructPcml.setTipoNivel5(unidadAux.getTipoNivel5()
                                != null ? unidadAux.getTipoNivel5() : "");
                        unidadStructPcml.setValorNivel5(unidadAux.getValorNivel5()
                                != null ? unidadAux.getValorNivel5() : "");
                        unidadStructPcml.setTipoNivel6(unidadAux.getTipoNivel6()
                                != null ? unidadAux.getTipoNivel6() : "");
                        unidadStructPcml.setValorNivel6(unidadAux.getValorNivel6()
                                != null ? unidadAux.getValorNivel6() : "");
                        unidadesPredioList.add(unidadStructPcml);
                    }
                    //Una vez con la copia realizada, se procede a comparar los listados
                    if (!unidadesPredioList.isEmpty()) {
                        for (UnidadStructPcml unidadStructPcml
                                : unidadesPredioList) {
                            for (UnidadStructPcml unidad : unidadAuxiliarList) {
                                if (unidad.getTipoNivel5() != null
                                        && !unidad.getTipoNivel5()
                                        .trim().isEmpty()) {
                                    DetalleDireccionEntity direccion
                                            = new DetalleDireccionEntity();
                                    direccion.setCptiponivel5(unidad.getTipoNivel5());
                                    if (unidad.getValorNivel5() != null
                                            && !unidad.getValorNivel5().trim()
                                            .isEmpty()) {
                                        direccion.setCpvalornivel5(unidad.getValorNivel5().trim());
                                    }
                                    if (unidad.getTipoNivel5().contains("+")) {
                                        if (unidad.getTipoNivel6() != null
                                                && !unidad.getTipoNivel6()
                                                .trim().isEmpty()
                                                && unidad.getValorNivel6()
                                                != null
                                                && !unidad.getValorNivel6()
                                                .trim().isEmpty()) {
                                            direccion.setCptiponivel6(unidad.getTipoNivel6());
                                            direccion.setCpvalornivel6(unidad.getValorNivel6()
                                                    .trim());
                                        }
                                    }
                                    String newApto
                                            = direccionRRFacadeLocal
                                            .generarNumAptoRR(direccion);
                                    if (unidadStructPcml.getAptoUnidad()
                                            .equalsIgnoreCase(newApto)) {
                                        String msn = "Existen unidades"
                                                + " asociadas al "
                                                + "predio repetidas."
                                                + " Verifique por favor.";
                                        FacesContext.getCurrentInstance()
                                                .addMessage(null,
                                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                                msn, ""));
                                        return false;
                                    }
                                }

                                //Validacion unidades repetidas entre si en cambios
                                //valor valor Vs valor valor
                                if (unidad.getTipoNivel5() != null
                                        && !unidad.getTipoNivel5()
                                        .trim().isEmpty() && unidadStructPcml
                                        .getTipoNivel5() != null
                                        && !unidadStructPcml.getTipoNivel5()
                                        .trim().isEmpty()
                                        && unidad.getValorNivel5() != null
                                        && !unidad.getValorNivel5().trim()
                                        .isEmpty() && unidadStructPcml
                                        .getValorNivel5() != null
                                        && !unidadStructPcml.getValorNivel5().trim()
                                        .isEmpty()
                                        && unidad.getTipoNivel6() != null
                                        && !unidad.getTipoNivel6()
                                        .trim().isEmpty() && unidadStructPcml
                                        .getTipoNivel6() != null
                                        && !unidadStructPcml.getTipoNivel6()
                                        .trim().isEmpty()
                                        && unidad.getValorNivel6() != null
                                        && !unidad.getValorNivel6().trim()
                                        .isEmpty()
                                        && unidadStructPcml.getValorNivel6() != null
                                        && !unidadStructPcml.getValorNivel6().trim()
                                        .isEmpty()
                                        && unidad.getTipoNivel5()
                                        .equals(unidadStructPcml
                                                .getTipoNivel5())
                                        && unidad.getValorNivel5()
                                        .equals(unidadStructPcml.getValorNivel5())
                                        && unidad.getTipoNivel6()
                                        .equals(unidadStructPcml
                                                .getTipoNivel6())
                                        && unidad.getValorNivel6()
                                        .equals(unidadStructPcml.getValorNivel6())
                                        && !unidad.getIdUnidad()
                                        .equals(unidadStructPcml.getIdUnidad())) {
                                    String msn = "Existen unidades"
                                            + " asociadas al "
                                            + "predio repetidas en los cambios."
                                            + " Verifique por favor.";
                                    FacesContext.getCurrentInstance()
                                            .addMessage(null,
                                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                            msn, ""));
                                    return false;
                                }
                                // valor null vs null null a excepción de casa, admin etc.
                                if (unidad.getTipoNivel5() != null
                                        && !unidad.getTipoNivel5()
                                        .trim().isEmpty() && unidadStructPcml
                                        .getTipoNivel5() != null
                                        && !unidadStructPcml.getTipoNivel5()
                                        .trim().isEmpty()
                                        && (unidad.getValorNivel5() == null
                                        || unidad.getValorNivel5().trim()
                                        .isEmpty()) && (unidadStructPcml
                                        .getValorNivel5() == null
                                        || unidadStructPcml.getValorNivel5().trim()
                                        .isEmpty())
                                        && (unidad.getTipoNivel6() == null
                                        || unidad.getTipoNivel6()
                                        .trim().isEmpty()) && (unidadStructPcml
                                        .getTipoNivel6() == null
                                        || unidadStructPcml.getTipoNivel6()
                                        .trim().isEmpty())
                                        && (unidad.getValorNivel6() == null
                                        || unidad.getValorNivel6().trim()
                                        .isEmpty())
                                        && (unidadStructPcml.getValorNivel6() == null
                                        || unidadStructPcml.getValorNivel6().trim()
                                        .isEmpty())
                                        && !unidad.getIdUnidad()
                                        .equals(unidadStructPcml.getIdUnidad())
                                        && ((unidad.getTipoNivel5().equalsIgnoreCase("CASA")
                                        && unidadStructPcml.getTipoNivel5().equalsIgnoreCase("CASA"))
                                        || (unidad.getTipoNivel5().equalsIgnoreCase("ADMINISTRACION")
                                        && unidadStructPcml.getTipoNivel5().equalsIgnoreCase("ADMINISTRACION"))
                                        || (unidad.getTipoNivel5().equalsIgnoreCase("FUENTE")
                                        && unidadStructPcml.getTipoNivel5().equalsIgnoreCase("FUENTE")
                                        || (unidad.getTipoNivel5().equalsIgnoreCase("RECEPTOR")
                                        && unidadStructPcml.getTipoNivel5().equalsIgnoreCase("RECEPTOR"))))) {

                                    String msn = "Existen unidades"
                                            + " asociadas al "
                                            + "predio repetidas en los cambios."
                                            + " Verifique por favor.";
                                    FacesContext.getCurrentInstance()
                                            .addMessage(null,
                                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                            msn, ""));
                                    return false;
                                }
                                // valor valor vs null null
                                if (unidad.getTipoNivel5() != null
                                        && !unidad.getTipoNivel5()
                                        .trim().isEmpty() && unidadStructPcml
                                        .getTipoNivel5() != null
                                        && !unidadStructPcml.getTipoNivel5()
                                        .trim().isEmpty()
                                        && (unidad.getValorNivel5() != null
                                        && !unidad.getValorNivel5().trim()
                                        .isEmpty()) && (unidadStructPcml
                                        .getValorNivel5() != null
                                        && !unidadStructPcml.getValorNivel5().trim()
                                        .isEmpty())
                                        && (unidad.getTipoNivel6() == null
                                        || unidad.getTipoNivel6()
                                        .trim().isEmpty()) && (unidadStructPcml
                                        .getTipoNivel6() == null
                                        || unidadStructPcml.getTipoNivel6()
                                        .trim().isEmpty())
                                        && (unidad.getValorNivel6() == null
                                        || unidad.getValorNivel6().trim()
                                        .isEmpty())
                                        && (unidadStructPcml.getValorNivel6() == null
                                        || unidadStructPcml.getValorNivel6().trim()
                                        .isEmpty())
                                        && unidad.getTipoNivel5()
                                        .equals(unidadStructPcml.getTipoNivel5())
                                        && unidad.getValorNivel5()
                                        .equals(unidadStructPcml.getValorNivel5())
                                        && !unidad.getIdUnidad()
                                        .equals(unidadStructPcml.getIdUnidad())) {

                                    String msn = "Existen unidades"
                                            + " asociadas al "
                                            + "predio repetidas en los cambios en el primer nivel."
                                            + " Verifique por favor.";
                                    FacesContext.getCurrentInstance()
                                            .addMessage(null,
                                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                            msn, ""));
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            return result;
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error en validar unidades repetidas " + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al validar las unidades repetidas " + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Función utilizada resolver los conflictos que presentan unidades cuando
     * se encuentran en RR tanto en dirección ANTIGUA como en NUEVA.
     *
     * @param citiEntity
     * @return
     * @author Juan David Hernandez return Listado de unidades con conflictos
     * que no se pudieron resolver
     */
    public UnidadesConflictoDto resolverConflictosUnidades(CityEntity citiEntity) {
        UnidadesConflictoDto unidadesConflictoDto = new UnidadesConflictoDto();
        try {
            List<UnidadStructPcml> unidadesConflictosSinResolver = new ArrayList();
            if (citiEntity != null) {
                if (citiEntity.getUnidadStructPcmlAntiguasList() != null
                        && citiEntity.getUnidadStructPcmlNuevasList() != null) {

                    unidadesConflictoDto = direccionesValidacionFacadeLocal
                            .resolverConflictosUnidades(citiEntity);
                }
            }
            return unidadesConflictoDto;

        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al resolver conflicto de unidades " + e.getMessage(), SOLICITUDES_HHPP);
            return null;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al resolver conflictos de unidades: " + e.getMessage(), SOLICITUDES_HHPP);
            return null;
        }
    }

    /**
     * Función utilizada para validar la correcta construicción de la dirección
     * cuando es tipo Barrio Manzana.
     *
     * @param direccion drDireccion con la información de la dirección construida
     * @return
     * @author Juan David Hernandez
     * <p>
     * return true si la dirección cumple con la estructura minima de
     * construcción de una dirección
     */
    public boolean validarEstructuraDireccion(DrDireccion direccion) {
        try {
            return direccionesValidacionFacadeLocal.validarEstructuraDireccion(direccion, Constant.TIPO_VALIDACION_DIR_HHPP);
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error validando construcción de la dirección " + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al validar estructura de la dirección " + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Función utilizada para validar que no existan datos nulos al momento de
     * crear una solicitud Dth.
     *
     * @author Juan David Hernandez return boolean
     * @return Retorna true si las validaciones de datos de los campos se cumplen
     * @author Juan David Hernandez return boolean
     */
    public boolean validarCrearSolicitud() {
        try {
            if (Objects.isNull(solicitud)) {
                String msn = "No hay datos de solicitud en curso, intente nuevamente por favor...";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
                return false;
            }

            if (StringUtils.isBlank(Optional.ofNullable(tipoTecnologiaBasica).map(CmtBasicaMgl::getAbreviatura).orElse(null))) {
                String msn = "Seleccione TIPO DE TECNOLOGÍA por favor.";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
                return false;
            }

            if (!validarDatosDireccion()) {
                //Si no cumple con los datos de la dirección muestra el mensaje de error de validación previamente asignado.
                return false;
            }

            if (isInvalidTipoSolicitud()) return false;

            if (!validarDatosSolicitud()) {
                //Si no cumple con los datos complementarios de la solicitud muestra el mensaje de error de validación previamente asignado.
                return false;
            }

            if (tipoAccionSolicitud.equalsIgnoreCase(Constant.RR_DIR_CREA_HHPP_0)) {
                if (StringUtils.isBlank(tipoViviendaSeleccionada)) {
                    String msn = "Seleccione un tipo de vivienda por favor.";
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
                    return false;
                }

                solicitud.setTipoTecHabilitadaId(tipoViviendaSeleccionada);
            }

            if (tipoAccionSolicitud.equals(Constant.RR_DIR_ESCALAMIENTO_HHPP)) {
                if (StringUtils.isBlank(tipoHHPP)) {
                    String msn = "Debe ingresar Tipo por favor.";
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
                    return false;
                }
                if (StringUtils.isBlank(tipoGestion)) {
                    String msn = "Debe ingresar Tipo Gestión por favor.";
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al validar campos de dirección para la creación de la solicitud "
                    + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Verifica si se cumple con los datos requeridos sobre la dirección.
     *
     * @return {@code boolean} Retorna true si cumple las validaciones de la dirección.
     * @author Gildardo Mora
     */
    private boolean validarDatosDireccion() {
        if (ciudad == null || ciudad.equals(BigDecimal.ZERO)) {
            String msn = "Seleccione una CIUDAD por favor.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return false;
        }

        if (StringUtils.isBlank(tipoDireccion)) {
            String msn = "Seleccione TIPO DE DIRECCIÓN por favor.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return false;
        }

        if (idCentroPoblado == null || idCentroPoblado.compareTo(BigDecimal.ZERO) == 0) {
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, SELECCIONE_EL_CENTRO_POBLADO_POR_FAVOR);
            return false;
        }

        if (tipoDireccion.equalsIgnoreCase("CK") && StringUtils.isBlank(direccionCk)) {
            String msn = "Ingrese una dirección por favor.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return false;
        }

        if (request.getDrDireccion() == null) {
            String msn = "Debe construir una dirección para poder validarla.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return false;
        }

        if (barrioSugeridoField && StringUtils.isBlank(barrioSugeridoStr)) {
            String msn = "Debe ingresar un barrio al haber seleccionado la opción 'OTRO'";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return false;
        }

        if (!direccionConstruida) {
            String msn = "Debe construir una dirección correcta para poder validarla.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return false;
        }

        if (!apartamentoIngresado) {
            String msnError = "Debe ingresar nivel apartamento a la construcción de la dirección.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msnError);
            return false;
        }

        return true;
    }

    /**
     * Verifica que los datos complementarios de la solicitud cumplan los requisitos.
     *
     * @return {@code boolean} Retorna true si cumple todas las validaciones asociadas a los datos complementarios de la solicitud.
     * @author Gildardo Mora
     */
    private boolean validarDatosSolicitud() {
        /*Brief 57662*/
        if (tipoAccionSolicitud.equals(Constant.TRASLADO_HHPP_BLOQUEADO_5)
                && (StringUtils.isBlank(numCuentaClienteTraslado) || numCuentaClienteTraslado.equals("0")
                || !StringToolUtils.containsOnlyNumbers(numCuentaClienteTraslado))) {

            String msg = "Debe ingresar un número de cuenta cliente valido";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msg);
            return false;
        }
        /*Cierre Brief  57762 */

        if (StringUtils.isBlank(Optional.ofNullable(solicitud).map(Solicitud::getContacto).orElse(null))) {
            String msn = "Debe ingresar el contacto por favor.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return false;
        }

        if (StringUtils.isBlank(Optional.ofNullable(solicitud).map(Solicitud::getTelContacto).orElse(null))) {
            String msn = "Debe ingresar el teléfono del contacto por favor.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return false;
        }

        if (StringUtils.isBlank(Optional.ofNullable(solicitud).map(Solicitud::getTipoSol).orElse(null))) {
            String msn = "Debe ingresar el área por favor. ";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return false;
        }

        if (StringUtils.isBlank(Optional.ofNullable(solicitud).map(Solicitud::getTelSolicitante).orElse(null))) {
            String msn = "Debe ingresar el teléfono del solicitante por favor.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return false;
        }

        String motivoSol = Optional.ofNullable(solicitud).map(Solicitud::getMotivo).orElse(null);
        if (StringUtils.isBlank(motivoSol)) {
            String msn = "Debe ingresar observaciones por favor.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return false;
        }

        if (motivoSol.length() > 2000) {
            String msn = "Las observaciones no pueden ser mayores a 2000 caracteres.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return false;
        }

        return true;
    }

    /**
     * Función utilizada para validar que se haya seleccionado una direccion
     *
     * @param hhppMglList {@link List<HhppMgl>}
     * @author Juan David Hernandez
     * return boolean
     */
    public boolean validarSeleccionDireccion(List<HhppMgl> hhppMglList) {
        try {
            if (hhppMglList == null || hhppMglList.isEmpty()) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                        "No se encuentra ninguna dirección seleccionada"
                                + " para realizar la solicitud");
                return false;
            }

            int cont = 0;
            for (HhppMgl hhpp : hhppMglList) {
                if (hhpp.isSelected()) {
                    cont++;
                    hhppSelected = hhpp;
                }
            }

            if (cont == 1) {
                return true;
            }

            hhppSelected = null;
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,
                    "Debe seleccionar una única dirección con"
                            + " la que desea realizar la solicitud");
            return false;
        } catch (Exception e) {
            String msgError = "Ocurrió un error intentando validar la selección de la dirección debido a: ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Función utilizada para validar que no existan datos nulos al momento de
     * crear una solicitud Dth.
     *
     * @return {@code boolean}
     * @author Juan David Hernandez
     */
    public boolean validarBuscarDireccionCambioEstrato() {
        try {
            if (tipoTecnologiaBasica == null
                    || StringUtils.isBlank(tipoTecnologiaBasica.getAbreviatura())) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "Seleccione TIPO DE TECNOLOGÍA por favor.");
                return false;
            }

            if (isInvalidCiudad()) return false;

            if (isInvalidTipoDireccion()) return false;

            if (idCentroPoblado == null || idCentroPoblado.compareTo(BigDecimal.ZERO) == 0) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, SELECCIONE_EL_CENTRO_POBLADO_POR_FAVOR);
                return false;
            }

            if (tipoDireccion.equalsIgnoreCase("CK") && StringUtils.isBlank(direccionCk)) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,"Ingrese una dirección por favor.");
                return false;
            }

            if (request.getDrDireccion() == null) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,"Debe construir una dirección para poder validarla.");
                return false;
            }

            if (!direccionConstruida) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,"Debe construir una dirección correcta para poder validarla.");
                return false;
            }

            if (isInvalidTipoSolicitud()) return false;

            if (solicitud == null || StringUtils.isBlank(solicitud.getContacto())) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,"Debe ingresar el contacto por favor.");
                return false;
            }

            if (StringUtils.isBlank(solicitud.getTelContacto())) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,"Debe ingresar el telefono del contacto por favor.");
                return false;
            }

            if (StringUtils.isBlank(solicitud.getTipoSol())) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,"Debe ingresar el área por favor.");
                return false;
            }

            if (StringUtils.isBlank(solicitud.getTelSolicitante())) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,"Debe ingresar el teléfono del solicitante por favor." );
                return false;
            }

            if (StringUtils.isBlank(solicitud.getMotivo())) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,"Debe ingresar observaciones por favor.");
                return false;
            }

            return true;
        } catch (Exception e) {
            String msgError = "Error al validar campos al buscar dirección para la creación de la solicitud : ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Verifica si el tipoAccionSolicitud no es válido.
     *
     * @return {@code boolean}
     * @author Gildardo Mora
     */
    private boolean isInvalidTipoSolicitud() {
        if (StringUtils.isBlank(tipoAccionSolicitud)) {
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,"Seleccione TIPO DE SOLICITUD por favor.");
            return true;
        }
        return false;
    }

    /**
     * Función utilizada para validar los datos de contacto del fomulario de
     * crear una solicitud.
     *
     * @return
     * @author Juan David Hernandez return boolean
     */
    public boolean validarDatosContacto() {
        try {
            if (solicitud == null
                    || solicitud.getContacto() == null
                    || solicitud.getContacto().isEmpty()) {
                String msn = "Debe ingresar el"
                        + " contacto por favor.";
                FacesContext.getCurrentInstance()
                        .addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msn, ""));
                return false;
            } else {
                if (solicitud.getTelContacto() == null
                        || solicitud.getTelContacto().isEmpty()) {
                    String msn = "Debe ingresar el telefono del contacto"
                            + " por favor.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msn, ""));
                    return false;
                } else {
                    if (solicitud.getTipoSol() == null
                            || solicitud.getTipoSol().isEmpty()) {
                        String msn = "Debe ingresar el área por favor.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msn, ""));
                        return false;
                    } else {
                        if (solicitud.getTelSolicitante() == null
                                || solicitud.getTelSolicitante().isEmpty()) {
                            String msn = "Debe ingresar el teléfono del "
                                    + "solicitante por favor.";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msn, ""));
                            return false;
                        } else {
                            if (solicitud.getMotivo() == null
                                    || solicitud.getMotivo().isEmpty()) {
                                String msn = "Debe ingresar observaciones "
                                        + "por favor.";
                                FacesContext.getCurrentInstance()
                                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al validar datos de contacto " + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Función utilizada para validar la dirección por la matriz de viabilidad
     * previamente configurada
     *
     * @param resultMensajesValidacion false si solo se quiere pasar la
     *                                 solicitud por la matriz pero él resultado de la validación no se tendra
     *                                 en cuenta debido a que previamente ya se identifico que no es posible
     *                                 continuar con la solicitud
     * @author Juan David Hernandez
     */
    public void validarMatrizViabilidad(boolean resultMensajesValidacion) {
        try {
            SolicitudManager manager = new SolicitudManager();
            /*Se envia dirección a la matriz de viabilidad para obtener si 
             existe viabilidad*/
            List<ResponseValidacionViabilidad> responseValidacionViabilidadList
                    = solicitudFacadeLocal.validarMatrizViabilidad(
                    responseValidarDireccion,
                    direccionLabel);

            if (responseValidacionViabilidadList != null
                    && !responseValidacionViabilidadList.isEmpty()) {
                for (ResponseValidacionViabilidad responseValidacionViabilidad
                        : responseValidacionViabilidadList) {

                    /*Si la respuesta es false se hace recorrido de la
                     * lista de mensajes para ser desplegados todos por proyecto */
                    if (!responseValidacionViabilidad.isResultadoValidacion()) {
                        crearSolicitudButton = false;
                        habilitarViabilidad = false;
                        deshabilitarValidar = false;

                        String msn = "Proyecto: "
                                + responseValidacionViabilidad
                                .getNombreProyecto() == null
                                ? "Sin Proyecto Configurado"
                                : responseValidacionViabilidad.getNombreProyecto();
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msn, ""));

                        if (responseValidacionViabilidad.getMensajes() != null
                                && !responseValidacionViabilidad
                                .getMensajes().isEmpty()) {
                            for (ResponseValidacionViabilidadMensaje mensaje
                                    : responseValidacionViabilidad
                                    .getMensajes()) {

                                String tabla = mensaje.getTabla();
                                String viabilidad = mensaje.getViabilidad();

                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                tabla + ": " + mensaje.getDescripcion()
                                                        + ", Viabilidad: " + viabilidad + " ",
                                                ""));
                            }
                            String mensaje = "Solicitud rechazada,"
                                    + " No hay viabilidad. ";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, ""));
                        } else {
                            /*Si el listado de mensajes de la matriz llega vació se
                             * despliega el mensaje global de la validación de
                             * la matriz de viablidad. */
                            String mensajeValidacion
                                    = responseValidacionViabilidad.getMensajeRespuesta();
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            mensajeValidacion, ""));
                        }
                    } else {
                        String msn = "Proyecto: "
                                + responseValidacionViabilidad.getNombreProyecto() == null
                                ? "Sin Proyecto Configurado"
                                : responseValidacionViabilidad.getNombreProyecto();
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));

                        /* Si la lista de mensajes se encuentra vacia se despliega
                         * el mensaje global de la validación de la matriz
                         * de viabilidad*/
                        if (responseValidacionViabilidad.getMensajes() != null
                                && !responseValidacionViabilidad.getMensajes()
                                .isEmpty()) {
                            for (ResponseValidacionViabilidadMensaje mensaje
                                    : responseValidacionViabilidad.getMensajes()) {

                                String tabla = mensaje.getTabla();
                                String viabilidad = mensaje.getViabilidad();

                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                tabla + ": " + mensaje.getDescripcion()
                                                        + ", Viabilidad: " + viabilidad + " ", ""));
                            }
                        } else {
                            String mensajeValidacion
                                    = responseValidacionViabilidad.getMensajeRespuesta();
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            mensajeValidacion, ""));
                        }
                        //Se obtienen las unidades asociadas al predio.
                        obtenerUnidadesAsociadasPredio(responseValidarDireccion.getCityEntity(),
                                idCentroPoblado);

                        /*Si en los mensajes de validación retorna false no permite
                         continuar con la creación de la solicitud */
                        if (resultMensajesValidacion) {
                            crearSolicitudButton = true;
                            deshabilitarValidar = false;
                            habilitarViabilidad = false;
                        } else {
                            crearSolicitudButton = false;
                            deshabilitarValidar = false;
                            habilitarViabilidad = false;
                        }
                        String msj = "Dirección construida y validada "
                                + "correctamente. ";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO, msj,
                                        ""));

                        String mensajeValidacion = "Existe viabilidad. ";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                        mensajeValidacion, ""));
                    }
                }
            } else {
                String msn = "Ocurrio un error y no fue posible realizar la "
                        + "validación en la matriz de viabilidad. ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            }
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al validar matriz de viabilidad " + e.getMessage(), SOLICITUDES_HHPP);
            crearSolicitudButton = false;
            habilitarViabilidad = false;
            deshabilitarValidar = false;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al validar matriz de viabilidad " + e.getMessage(), SOLICITUDES_HHPP);
            crearSolicitudButton = false;
            habilitarViabilidad = false;
            deshabilitarValidar = false;
        }
    }

    /**
     * Función utilizada para validar la dirección por la matriz de viabilidad
     * previamente configurada
     *
     * @param resultMensajesValidacion false si solo se quiere pasar la
     *                                 solicitud por la matriz pero él resultado de la validación no se tendra
     *                                 en cuenta debido a que previamente ya se identifico que no es posible
     *                                 continuar con la solicitud
     * @throws java.lang.NoSuchMethodException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.InstantiationException
     * @throws java.lang.ClassNotFoundException
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws java.lang.reflect.InvocationTargetException
     * @author Juan David Hernandez
     */
    public void validarMatrizViabilidadInspira(boolean resultMensajesValidacion)
            throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, ApplicationException, ClassNotFoundException, InstantiationException {
        try {
            SolicitudManager manager = new SolicitudManager();
            /*Se envia dirección a la matriz de viabilidad para obtener si 
             existe viabilidad*/
            CmtBasicaMgl basicaValidacionParametrizada = cmtBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.BASICA_TIPO_VALIDACION_PARAMETRIZADA_CREAR_SOLICITUD);

            if (basicaValidacionParametrizada != null) {
                validacionParametrizadaList = validacionParametrizadaMglFacadeLocal
                        .findValidacionParametrizadaByTipo(basicaValidacionParametrizada.getBasicaId());

                if (validacionParametrizadaList != null) {

                    for (ValidacionParametrizadaTipoValidacionMgl val : validacionParametrizadaList) {
                        if (val.getValidacionActiva().equals(BigDecimal.ONE)) {
                            String className = "co.com.claro.mgl.businessmanager.address.";
                            Class<?> dogClass = Class.forName(className + val.getValidacionParametrizadaId().getNombreClase()); // convert string classname to class
                            Object dog = dogClass.newInstance(); // invoke empty constructor

                            String methodName = val.getValidacionParametrizadaId().getNombreMetodo();

                            Method setNameMethod = dog.getClass().getMethod(methodName, ValidacionParametrizadaDto.class);

                            ValidacionParametrizadaDto validacionActual = new ValidacionParametrizadaDto();
                            validacionActual.setDrDireccion(responseValidarDireccion.getDrDireccion());
                            validacionActual.setCentroPoblado(centroPobladoSeleccionado);
                            validacionActual.setDepartamento(departamentoGpo);
                            validacionActual.setTecnologiaBasicaId(tipoTecnologiaBasica);

                            ValidacionParametrizadaDto resultadoValidacion = (ValidacionParametrizadaDto) setNameMethod.invoke(dog, validacionActual);

                            if (!resultadoValidacion.isResultadoValidacion()) {
                                String msn = resultadoValidacion.getMensajeValidacion();
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));

                            }
                        }
                    }
                }
            }

        } catch (ApplicationException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            String msgError = "No fue posible realizar la validación de viabilidad de la solicitud"
                    + " pero puede continuar con la solicitud ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            String msgError = "No fue posible realizar la validación de viabilidad de la solicitud"
                    + " pero puede continuar con la solicitud. ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que valida cuando el soporte es requerido que este sea
     * obligatorio de cargar
     *
     * @return
     * @author Juan David Hernandez
     */
    public boolean validarSoporteRequerido() {
        //si el soporte es requerido para todas las solicitudes menos para reactivacion
        if (soporteRequerido && tipoAccionSolicitud != null
                && !tipoAccionSolicitud.isEmpty()
                && !tipoAccionSolicitud.equalsIgnoreCase(Constant.RR_DIR_REAC_HHPP_3)) {
            if (uploadedFile != null && uploadedFile.getFileName() != null) {
            } else {
                String msn = "Es necesario cargar un archivo de soporte por favor.  ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;
            }
        }
        return true;
    }

    /**
     * Función que valida cuando el soporte es requerido que este sea
     * obligatorio de cargar
     *
     * @return
     * @author Juan David Hernandez
     */
    public boolean validarSoporteRequeridoCambioEstrato() throws ApplicationException {
        //si el soporte es requerido para todas las solicitudes menos para reactivacion
        if (soporteRequeridoTecnologia() && tipoAccionSolicitud != null
                && !tipoAccionSolicitud.isEmpty()
                && !tipoAccionSolicitud.equalsIgnoreCase(Constant.RR_DIR_REAC_HHPP_3)) {
            if (lstFilesCambiosEstrato != null && !lstFilesCambiosEstrato.isEmpty()) {
                return true;
            } else {
                String msn = "Es necesario cargar un archivo de soporte para el cambio de estrato por favor. ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;
            }
        }
        return true;
    }

    /**
     * Función utilizada para validar la estructura de las unidades asociadas al
     * predio al momento de hacer cambios sobre las mismas.
     *
     * @param unidadesAuxList listado de unidades a validar
     * @return
     * @author Juan David Hernandez return true si ninguna unidad presenta
     * inconsistencias.
     */
    public boolean validarEstructuraUnidades(List<UnidadStructPcml> unidadesAuxList) {
        try {
            if (unidadesPredio) {
                if (unidadesAuxList != null && !unidadesAuxList.isEmpty()) {
                    int pi1 = 0;
                    int pi2 = 0;
                    int pi3 = 0;
                    for (UnidadStructPcml unidad : unidadesAuxList) {
                        if (responseConstruirDireccion.getDrDireccion()
                                .getCpTipoNivel5().equalsIgnoreCase("CASA")
                                && (responseConstruirDireccion.getDrDireccion().getCpValorNivel5() == null
                                || responseConstruirDireccion.getDrDireccion().getCpValorNivel5().isEmpty())) {
                            if (unidad.getAptoUnidad().contains("PI")) {
                                String msn = "No es posible agregar el valor "
                                        + "CASA como Apto debido a que estructura"
                                        + " es de solo PISOS. Es necesario volver "
                                        + "a construir la dirección. ";
                                FacesContext.getCurrentInstance()
                                        .addMessage(null,
                                                new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                                return false;
                            }
                        }

                    /* obtenemos el número de veces que se encuentra una unidad 
                     con PISO en el listado */
                        if ((unidad.getAptoUnidad().contains("-") || unidad.getAptoUnidad().contains("PISO"))
                                && !(unidad.getAptoUnidad().contains("CASA"))) {
                            String unidadAptopi = unidad.getAptoUnidad().split("-")[0];

                            if (unidadAptopi.equalsIgnoreCase("PI1") || unidadAptopi.equalsIgnoreCase("PISO1")) {
                                pi1++;
                            } else {
                                if (unidadAptopi.equalsIgnoreCase("PI2") || unidadAptopi.equalsIgnoreCase("PISO2")) {
                                    pi2++;
                                } else {
                                    if (unidadAptopi.equalsIgnoreCase("PI3") || unidadAptopi.equalsIgnoreCase("PISO3")) {
                                        pi3++;
                                    }
                                }
                            }
                        }
                    }

                    if (tipoAccionSolicitud.equals(Constant.RR_DIR_CREA_HHPP_0)) {
                        //validación de número de unidades por piso
                        if ((responseValidarDireccion.getDrDireccion()
                                .getCpTipoNivel5().contains("PI") && !responseValidarDireccion.getDrDireccion()
                                .getCpTipoNivel5().contains("CASA"))
                                && responseValidarDireccion.getDrDireccion()
                                .getCpValorNivel5() != null
                                && !responseValidarDireccion.getDrDireccion()
                                .getCpValorNivel5().isEmpty()) {

                            int pi = Integer.parseInt(responseValidarDireccion.getDrDireccion()
                                    .getCpValorNivel5());
                            if (pi == 1 && pi1 >= 3) {
                                String msn = "No es posible crear la "
                                        + "solicitud con PISO 1 debido a que"
                                        + " ya se encuentran 3 unidades en"
                                        + " el PISO 1.";
                                FacesContext.getCurrentInstance()
                                        .addMessage(null,
                                                new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                                return false;
                            } else {
                                if (pi == 2 && pi2 >= 3) {
                                    String msn = "No es posible crear la "
                                            + "solicitud con PISO 2 debido a que"
                                            + "ya se encuentran 3 unidades en"
                                            + " el PISO 2.";
                                    FacesContext.getCurrentInstance()
                                            .addMessage(null,
                                                    new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                                } else {
                                    if (pi == 3 && pi3 >= 3) {
                                        String msn = "No es posible crear la "
                                                + "solicitud con PISO 3 debido a que"
                                                + "ya se encuentran 3 unidades en"
                                                + " el PISO 3.";
                                        FacesContext.getCurrentInstance()
                                                .addMessage(null,
                                                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return true;

        } catch (NumberFormatException e) {
            String msgError = "Error al validar estructura de unidades asociadas al predio ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        } catch (Exception e) {
            String msgError = "Error al validar estructura de unidades asociadas al predio. ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Función utilizada para crear una solicitud en la base datos.
     *
     * @author Juan David Hernandez
     */
    public void crearSolicitud() {
        try {
            if (StringUtils.isBlank(tipoAccionSolicitud)) {
                String msn = "Seleccione Tipo Solicitud por favor.";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
                return;
            }

            /* Realiza el proceso de creación y autogestión de la solicitud de Traslado HHPP Bloqueado (creación HHPP Virtual) */
            if (tipoAccionSolicitud.equalsIgnoreCase(TRASLADO_HHPP_BLOQUEADO.getCodigoBasica())) {
                crearSolicitudTrasladoHhppBloqueado();
                return;
            }

            if (tipoAccionSolicitud.equalsIgnoreCase(ESCALAMIENTO_HHPP.getCodigoBasica())) {
                crearSolicitudEscalamientoHHPP();
                return;
            }

            if (tipoAccionSolicitud.equalsIgnoreCase(CAMBIO_ESTRATO.getCodigoBasica())) {
                crearSolicitudCambioEstrato();
                return;
            }

            crearSolicitudHhpp();

        } catch (ApplicationException | IOException e) {
            exceptionServBean.notifyError(e, "Error al crear la solicitud " + e.getMessage(), SOLICITUDES_HHPP);
            if (fileDelete != null) {
                FileToolUtils.deleteFile(fileDelete);
            }
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error al crear la solicitud " + e.getMessage(), SOLICITUDES_HHPP);
            if (fileDelete != null) {
                FileToolUtils.deleteFile(fileDelete);
            }
        }
    }

    /**
     * Permite crear una solicitud sobre HHPP
     * @throws ApplicationException si ocurre un error al crear la solicitud
     * @throws IOException Si ocurre un error de entrada/salida
     */
    private void crearSolicitudHhpp() throws ApplicationException, IOException {
        if (isInvalidSolicitud()) {
            LOGGER.debug("No se pudo crear la solicitud debido a que no se cumplen las condiciones necesarias");
            return;
        }

        if (request == null) {
            String msn = "La dirección no se encuentra construida "
                    + "correctamente. Intente de nuevo por favor.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, msn);
            return;
        }

        //Construcción de request de solicitud
        crearSolicitudRequest = crearSolicitudRequest();
        /*Si la dirección presenta conflictos, estos se resolveran
         automaticamente los que sean posibles*/
        resolverConflictosDireccion();

        //se establece la direccion con AVENIDA que es respuesta del GEO sin alteraciones para enviarla a la gestion
        if (StringUtils.isNotBlank(direccionRespuestaGeo)
                && crearSolicitudRequest != null && crearSolicitudRequest.getDrDireccion() != null) {
            crearSolicitudRequest.getDrDireccion().setDireccionRespuestaGeo(direccionRespuestaGeo);
        }

        /*Consume servicio que hace inserción de la solicitud Dth
         * en la base de datos */
        ResponseCreaSolicitud responseCrearSolicitud
                = solicitudFacadeLocal.crearSolicitudDth(crearSolicitudRequest,
                tipoTecnologiaBasica.getAbreviatura(),
                unidadAuxiliarList, timeSol, idCentroPoblado,
                usuarioVT, perfilVt, tipoAccionSolicitud,
                solicitud, habilitarRR, true, centroPobladoDireccion,
                ciudadDireccion, departamentoDireccion, false);

        if (!responseCrearSolicitud.getTipoRespuesta().equalsIgnoreCase("I")) {
            if (responseCrearSolicitud.getTipoRespuesta().equalsIgnoreCase("E")) {
                String msn = "Ocurrió un error intentando crear la solicitud " + responseCrearSolicitud.getMensaje();
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, msn);
            }

            LOGGER.debug("No se pudo crear la solicitud: {}", Optional.of(responseCrearSolicitud)
                    .map(ResponseCreaSolicitud::getMensaje).orElse(""));
            return;
        }

        solicitudCreated.setIdSolicitud(new BigDecimal(responseCrearSolicitud.getIdSolicitud()));
        solicitudCreated = solicitudFacadeLocal.findById(solicitudCreated.getIdSolicitud());
        cmtTiempoSolicitudDth.setTiempoSolicitud(timeSol);
        validarTecnologiaSeleccionada(tipoTecnologia);
        tiempoFinalSolicitud = new Date();
        crearSolicitudButton = true;
        deshabilitarCreacion = true;
        enableTabs = true;
        cargarArchivo();
        /* Si se da este tipo de respuesta en los mensajes de
         * validación al momento de validar la dirección se cambia
         * el mensaje debido a que no se creará una nueva solicitud
         * sino que se realizará un cambio de dirección.*/
        String msn = Optional.ofNullable(responseValidarDireccion)
                .map(ResponseValidacionDireccion::getCityEntity)
                .map(CityEntity::getExisteRr)
                .filter(existeRr -> existeRr.contains("(C)"))
                .map(existeRr -> "Hhpp actualizado a la dirección: " + HTML_ITALIC_OPEN
                        + direccionLabel + HTML_ITALIC_CLOSE + " correctamente. Proceso exitoso")
                .orElse("Solicitud <b>" + responseCrearSolicitud.getIdSolicitud()
                        + HTML_BOLD_CLOSE + " para la dirección " + HTML_ITALIC_OPEN
                        + direccionLabel + HTML_ITALIC_CLOSE + " creada correctamente. Proceso exitoso.");
        FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.INFO, msn);
    }

    /**
     * Resuelve resolver conflictos de dirección
     */
    private void resolverConflictosDireccion() {
        if (responseValidarDireccion.getCityEntity().isExisteHhppAntiguoNuevo()) {
            UnidadesConflictoDto unidadesConflictoDto = resolverConflictosUnidades(responseValidarDireccion.getCityEntity());

            if (Objects.nonNull(unidadesConflictoDto) && CollectionUtils.isNotEmpty(unidadesConflictoDto.getUnidadesConflicto())) {
                /*Se agregan las unidades en conflicto como si fuesen unidades con cambios de apto */
                unidadAuxiliarList.addAll(unidadesConflictoDto.getUnidadesConflicto());
                /*si existen HHPP tanto en la direccion antigua como en la nueva se deja la anotacion en la nota (Conflicto) */
                solicitud.setMotivo(solicitud.getMotivo() + " " + MSG_HHPP_EN_ANTIGUA_NUEVA);
            } else {
                /*Si no existen unidades con conflictos y fueron resueltos todos, se cambia el valor de la variable */
                crearSolicitudRequest.getCityEntity().setExisteHhppAntiguoNuevo(false);
            }

            if (Objects.nonNull(unidadesConflictoDto) && CollectionUtils.isNotEmpty(unidadesConflictoDto.getUnidadesConflictoResuelto())
                    && crearSolicitudRequest.getCityEntity() != null
                    && crearSolicitudRequest.getCityEntity().getUnidadesAsociadasPredioAntiguasList() != null) {

                crearSolicitudRequest.getCityEntity().getUnidadesAsociadasPredioAntiguasList().stream()
                        .filter(hhpp -> unidadesConflictoDto.getUnidadesConflictoResuelto().stream()
                                .anyMatch(unidadConflictoResuelto -> unidadConflictoResuelto.getHhppMgl().getHhpId().equals(hhpp.getHhpId()))
                        ).forEach(hhpp -> hhpp.setEstadoRegistro(0));
            }
        }
    }

    /**
     * Crea el request para crear una solicitud sobre HHPP
     * @return {@link RequestCreaSolicitud}
     */
    private RequestCreaSolicitud crearSolicitudRequest() {
        RequestCreaSolicitud requestCreaSolicitud = new RequestCreaSolicitud();
        requestCreaSolicitud.setIdUsuario(this.request.getIdUsuario());
        requestCreaSolicitud.setObservaciones(solicitud.getMotivo().toUpperCase().replace(";", " "));
        requestCreaSolicitud.setDrDireccion(this.request.getDrDireccion());
        requestCreaSolicitud.setComunidad(responseValidarDireccion.getCityEntity().getCodCity());
        requestCreaSolicitud.setContacto(solicitud.getContacto().toUpperCase());
        requestCreaSolicitud.setTelefonoContacto(solicitud.getTelContacto());
        requestCreaSolicitud.setCanalVentas(solicitud.getTipoSol());
        requestCreaSolicitud.setCityEntity(responseValidarDireccion.getCityEntity());
        requestCreaSolicitud.getCityEntity().setMessage(solicitud.getTipoTecHabilitadaId());
        requestCreaSolicitud.setTipoDocumento(solicitud.getTipoDoc());
        requestCreaSolicitud.setNumDocCli(solicitud.getNumDocCliente());
        return requestCreaSolicitud;
    }

    /**
     * Verifica si los datos son validos para la solicitud
     * @return Retorna true si los datos son invalidos, de lo contrario false
     */
    private boolean isInvalidSolicitud() {
        return !validarNuevoApartamento() || validarUnidadesPredio()
                || !validarInformacionUnidadesPredios()
                || !validarUnidadesRepetidas()
                || !validarEstructuraUnidades(unidadAuxiliarList)
                || !validarSoporteRequerido();
    }

    /**
     * Crea la solicitud de cambio de estrato
     * @throws Exception si ocurre un error al crear la solicitud
     */
    private void crearSolicitudCambioEstrato() throws Exception {
        //Creacion de solicitud de cambio de estrato
        if (request == null || !validarSeleccionDireccion(hhppList)
                || !validarNuevoEstratoSeleccionado() || !validarSoporteRequeridoCambioEstrato()) {
            LOGGER.debug("No se pudo crear la solicitud de cambio de estrato debido a que no se cumplen las condiciones necesarias");
            return;
        }

        //Construcción de request de solicitud
        StringBuilder mensajeBienFinal = new StringBuilder();
        StringBuilder mensajeErrorFinal = new StringBuilder();
        crearSolicitudRequest = new RequestCreaSolicitud();
        crearSolicitudRequest.setIdUsuario(request.getIdUsuario());
        crearSolicitudRequest.setObservaciones(solicitud.getMotivo().toUpperCase());
        crearSolicitudRequest.setDrDireccion(request.getDrDireccion());
        crearSolicitudRequest.setComunidad(centroPobladoSeleccionado.getGeoCodigoDane());
        crearSolicitudRequest.setContacto(solicitud.getContacto().toUpperCase());
        crearSolicitudRequest.setTelefonoContacto(solicitud.getTelContacto());
        crearSolicitudRequest.setCanalVentas(solicitud.getTipoSol());
        CityEntity cityEntity = new CityEntity();
        crearSolicitudRequest.setCityEntity(cityEntity);
        crearSolicitudRequest.getCityEntity().setMessage(solicitud.getTipoTecHabilitadaId());
        /*Consume servicio que hace inserción de la solicitud Dth
         * en la base de datos */
        crearSolicitudRequest.setTipoDocumento(solicitud.getTipoDoc());
        crearSolicitudRequest.setNumDocCli(solicitud.getNumDocCliente());
        ResponseCreaSolicitud responseCrearSolicitud =
                solicitudFacadeLocal.crearSolicitudCambioEstratoDir(crearSolicitudRequest,
                        timeSol, idCentroPoblado, usuarioVT, perfilVt, hhppSelected,
                        tipoAccionSolicitud, hhppSelected.getEstratoActual(),
                        nuevoEstratoHhpp);

        if (!responseCrearSolicitud.getTipoRespuesta().equalsIgnoreCase("I")) {
            if (responseCrearSolicitud.getTipoRespuesta().equalsIgnoreCase("E")) {
                String msn = "Ocurrio un error intentando crear la solicitud "
                        + responseCrearSolicitud.getMensaje();
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, msn);
            }
            return;
        }

        solicitudCreated.setIdSolicitud(new BigDecimal(responseCrearSolicitud.getIdSolicitud()));
        solicitudCreated = solicitudFacadeLocal.findById(solicitudCreated.getIdSolicitud());
        cmtTiempoSolicitudDth.setTiempoSolicitud(timeSol);
        tiempoFinalSolicitud = new Date();
        crearSolicitudButton = true;
        deshabilitarCreacion = true;
        enableTabs = true;
        /* Si se da este tipo de respuesta en los mensajes de
         * validación al momento de validar la dirección se cambia
         * el mensaje debido a que no se creará una nueva solicitud
         * sino que se realizará un cambio de dirección.*/
        String msn = "Solicitud de cambio de estrato <b>"
                + responseCrearSolicitud.getIdSolicitud() +
                HTML_BOLD_CLOSE + " para la dirección" + HTML_ITALIC_OPEN +
                hhppSelected.getDireccionDetalladaHhpp()
                + HTML_ITALIC_CLOSE + " creada correctamente. Proceso exitoso";
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        msn, ""));

        //Guardo los documentos de cambio de estrato
        if (CollectionUtils.isEmpty(lstFilesCambiosEstrato)) {
            LOGGER.debug("No se encontraron archivos de cambio de estrato para subir");
            return;
        }

        for (File archivo : lstFilesCambiosEstrato) {
            fileDelete = archivo;
            String nombreArchivo;
            String[] partsUrls = archivo.getName().split("-");
            nombreArchivo = partsUrls[0];
            String extension = FilenameUtils.getExtension(archivo.getName());
            String nombreArchivoFinal = solicitudCreated.getIdSolicitud().toString() + "_" + nombreArchivo + "." + extension;
            String responseArc = solicitudFacadeLocal.uploadArchivoCambioEstrato(archivo, nombreArchivoFinal);

            if (StringUtils.isBlank(responseArc)) {
                String msg = "Ocurrio un error al subir el archivo: " + nombreArchivoFinal + "\n";
                mensajeErrorFinal.append(msg);
            } else {
                TecArchivosCambioEstrato tecArchivosCambioEstrato = new TecArchivosCambioEstrato();
                tecArchivosCambioEstrato.setUrlArchivoSoporte(responseArc);
                tecArchivosCambioEstrato.setSolicitudObj(solicitudCreated);
                tecArchivosCambioEstrato.setNombreArchivo(nombreArchivoFinal);
                tecArchivosCambioEstrato = tecArcCamEstratoFacadeLocal.crear(tecArchivosCambioEstrato, usuarioVT, perfilVt);

                if (tecArchivosCambioEstrato != null) {
                    LOGGER.info("Archivo guardado en el repositorio");
                    String msg = "Archivo: " + nombreArchivoFinal + " subido satisfactoriamente \n";
                    mensajeBienFinal.append(msg);
                }

            }
            FileToolUtils.deleteFile(archivo);
        }

        if (!mensajeBienFinal.toString().isEmpty()) {
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.INFO, mensajeBienFinal.toString());
        }

        if (!mensajeErrorFinal.toString().isEmpty()) {
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, mensajeErrorFinal.toString());
        }

        tecArchivosCambioEstratos = tecArcCamEstratoFacadeLocal.findUrlsByIdSolicitud(solicitudCreated);
    }

    /**
     * Realiza el proceso de creación de la solicitud de traslado de HHPP bloqueado.
     *
     * @author Gildardo Mora
     */
    private void crearSolicitudTrasladoHhppBloqueado() {

        try {
            if (Objects.isNull(request)) {
                String msn = "La dirección no se encuentra construida correctamente. Intente de nuevo por favor.";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, msn);
            }

            RequestCreaSolicitudTrasladoHhppBloqueado requestTrasladoHhppBloqueado = assignRequestTrasladoHhppBloqueado();
            /*Si la dirección presenta conflictos, estos se resolveran automaticamente los que sean posibles*/
            resolverConflictosDireccionTraslado(requestTrasladoHhppBloqueado);

            //se establece la direccion con AVENIDA que es respuesta del GEO sin alteraciones para enviarla a la gestion
            if (StringUtils.isNotBlank(direccionRespuestaGeo) && requestTrasladoHhppBloqueado.getDrDireccion() != null) {
                requestTrasladoHhppBloqueado.getDrDireccion().setDireccionRespuestaGeo(direccionRespuestaGeo);
            }

            /* Realiza el proceso de creación de la solicitud*/
            Optional<ResponseCreaSolicitud> responseCrearSolicitud = solicitudFacadeLocal.crearSolicitudTrasladoHhppBloqueado(requestTrasladoHhppBloqueado);

            if (!responseCrearSolicitud.isPresent() || !responseCrearSolicitud.get().getTipoRespuesta().equalsIgnoreCase("I")) {
                if (responseCrearSolicitud.isPresent() && responseCrearSolicitud.get().getTipoRespuesta().equalsIgnoreCase("E")) {
                    String msn = "Ocurrió un error intentando crear la solicitud " + responseCrearSolicitud.get().getMensaje();
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, msn);
                }
                return;
            }

            solicitudCreated.setIdSolicitud(new BigDecimal(responseCrearSolicitud.get().getIdSolicitud()));
            solicitudCreated = solicitudFacadeLocal.findById(solicitudCreated.getIdSolicitud());
            cmtTiempoSolicitudDth.setTiempoSolicitud(timeSol);
            validarTecnologiaSeleccionada(tipoTecnologia);
            tiempoFinalSolicitud = new Date();
            crearSolicitudButton = true;
            deshabilitarCreacion = true;
            enableTabs = true;

            /* Si se da este tipo de respuesta en los mensajes de validación al momento de validar la dirección se cambia
             * el mensaje debido a que no se creará una nueva solicitud si no que se realizará un cambio de dirección.*/
            if (responseValidarDireccion.getCityEntity().getExisteRr() != null
                    && responseValidarDireccion.getCityEntity().getExisteRr().contains("(C)")) {
                String msn = "Hhpp actualizado a la dirección: " + HTML_ITALIC_OPEN + direccionLabel + HTML_ITALIC_CLOSE + " correctamente. Proceso exitoso";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.INFO, msn);
                return;
            }

            String idSolicitud = responseCrearSolicitud.get().getIdSolicitud();
            String msn = "Solicitud <b>" + idSolicitud + HTML_BOLD_CLOSE + " para la dirección" + HTML_ITALIC_OPEN
                    + direccionLabel + "V" + HTML_ITALIC_CLOSE + " creada correctamente. Proceso exitoso";

            UsuarioPortalResponseDto usuario = new UsuarioPortalResponseDto();
            usuario.setIdPerfil(String.valueOf(perfilVt));
            usuario.setUsuario(usuarioVT);
            solicitud.setIdSolicitud(new BigDecimal(idSolicitud));
            solicitud.setHhppReal(hhppReal);
            solicitudCreated.setHhppReal(hhppReal);
            /* Gestiona la solicitud de traslado HHPP Bloqueado*/
            Map<String, Object> solicitudTrasladoHhppBloqueadoProcesada = solicitudFacadeLocal
                    .gestionarSolicitudTrasladoHhppBloqueado(solicitud, solicitudCreated, requestTrasladoHhppBloqueado);

            if (!((boolean) solicitudTrasladoHhppBloqueadoProcesada.getOrDefault(ConstantsSolicitudHhpp.RESULTADO_GESTION, false))) {
                msn += "<br><br> Solicitud de traslado HHPP bloqueado No: " + idSolicitud + " No se pudo procesar correctamente.";
                msn += StringUtils.SPACE + solicitudTrasladoHhppBloqueadoProcesada.getOrDefault(ConstantsSolicitudHhpp.MSG_RESULTADO, "");
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
                return;
            }

            msn += "<br><br> Solicitud de traslado HHPP bloqueado No: " + idSolicitud + " Procesada y finalizada con éxito.";
            msn += "<br> " + solicitudTrasladoHhppBloqueadoProcesada.getOrDefault(ConstantsSolicitudHhpp.MSG_RESULTADO, "");
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.INFO, msn);

        } catch (ApplicationException ae) {
            exceptionServBean.notifyError(ae, "Error al crear la solicitud de traslado HHPP Bloqueado" + ae.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al crear la solicitud de traslado HHPP Bloqueado. " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Resuelve conflictos de la dirección
     *
     * @param requestTrasladoHhppBloqueado {@link RequestCreaSolicitudTrasladoHhppBloqueado}
     * @author Gildardo Mora
     */
    private void resolverConflictosDireccionTraslado(RequestCreaSolicitudTrasladoHhppBloqueado requestTrasladoHhppBloqueado) {
        if (!responseValidarDireccion.getCityEntity().isExisteHhppAntiguoNuevo()) {
            return;
        }

        UnidadesConflictoDto unidadesConflictoDto = resolverConflictosUnidades(responseValidarDireccion.getCityEntity());
        List<UnidadStructPcml> unidadesConflicto = Optional.ofNullable(unidadesConflictoDto)
                .map(UnidadesConflictoDto::getUnidadesConflicto).orElse(null);

        if (Objects.isNull(unidadesConflicto) || unidadesConflicto.isEmpty()) {
            /*Si no existen unidades con conflictos y fueron resueltos todos, se cambia el valor de la variable */
            requestTrasladoHhppBloqueado.getCityEntity().setExisteHhppAntiguoNuevo(false);
        } else {
            /*Se agregan las unidades en conflicto como si fuesen unidades con cambios de apto */
            unidadAuxiliarList.addAll(unidadesConflicto);
            /*si existen HHPP tanto en la direccion antigua como en la nueva se deja la anotacion en la nota (Conflicto) */
            solicitud.setMotivo(solicitud.getMotivo() + " " + MSG_HHPP_EN_ANTIGUA_NUEVA);
        }

        List<UnidadStructPcml> unidadesConflictoResuelto = Optional.ofNullable(unidadesConflictoDto)
                .map(UnidadesConflictoDto::getUnidadesConflictoResuelto).orElse(null);

        if (CollectionUtils.isNotEmpty(unidadesConflictoResuelto) && Optional.of(requestTrasladoHhppBloqueado)
                .map(RequestCreaSolicitud::getCityEntity).map(CityEntity::getUnidadesAsociadasPredioAntiguasList).isPresent()) {

            for (UnidadStructPcml unidadConflictoResuelto : unidadesConflictoResuelto) {
                for (HhppMgl hhpp : requestTrasladoHhppBloqueado.getCityEntity().getUnidadesAsociadasPredioAntiguasList()) {
                    if (unidadConflictoResuelto.getHhppMgl().getHhpId().equals(hhpp.getHhpId())) {
                        hhpp.setEstadoRegistro(0);
                    }
                }
            }
        }
    }

    /**
     * Asigna los datos requeridos sobre el request general para el proceso de traslado HHPP Bloqueado
     *
     * @return {@link RequestCreaSolicitudTrasladoHhppBloqueado}
     * @author Gildardo Mora
     */
    private RequestCreaSolicitudTrasladoHhppBloqueado assignRequestTrasladoHhppBloqueado() {
        RequestCreaSolicitudTrasladoHhppBloqueado requestTrasladoHhppBloqueado = new RequestCreaSolicitudTrasladoHhppBloqueado();
        requestTrasladoHhppBloqueado.setIdUsuario(request.getIdUsuario());
        requestTrasladoHhppBloqueado.setObservaciones(solicitud.getMotivo().toUpperCase().replace(";", " "));
        requestTrasladoHhppBloqueado.setDrDireccion(request.getDrDireccion());
        requestTrasladoHhppBloqueado.setComunidad(responseValidarDireccion.getCityEntity().getCodCity());
        requestTrasladoHhppBloqueado.setContacto(solicitud.getContacto().toUpperCase());
        requestTrasladoHhppBloqueado.setTelefonoContacto(solicitud.getTelContacto());
        requestTrasladoHhppBloqueado.setCanalVentas(solicitud.getTipoSol());
        requestTrasladoHhppBloqueado.setCityEntity(responseValidarDireccion.getCityEntity());
        requestTrasladoHhppBloqueado.getCityEntity().setMessage(solicitud.getTipoTecHabilitadaId());
        requestTrasladoHhppBloqueado.setTipoDocumento(solicitud.getTipoDoc());
        requestTrasladoHhppBloqueado.setNumDocCli(solicitud.getNumDocCliente());
        //TODO: validar asignación
        requestTrasladoHhppBloqueado.setNumeroCuentaClienteTrasladar(numCuentaClienteTraslado);
        requestTrasladoHhppBloqueado.setHhppReal(hhppReal);
        solicitud.setNumeroCuentaClienteTrasladar(numCuentaClienteTraslado);

        /* TODO: validar que parámetros son necesario*/
        requestTrasladoHhppBloqueado.setTipoTecnologia(tipoTecnologiaBasica.getAbreviatura());
        requestTrasladoHhppBloqueado.setUnidadesList(unidadAuxiliarList);
        requestTrasladoHhppBloqueado.setTiempoDuracionSolicitud(timeSol);
        requestTrasladoHhppBloqueado.setIdCentroPoblado(idCentroPoblado);
        requestTrasladoHhppBloqueado.setUsuarioVt(usuarioVT);
        requestTrasladoHhppBloqueado.setPerfilVt(perfilVt);
        requestTrasladoHhppBloqueado.setTipoAccionSolicitud(tipoAccionSolicitud);
        requestTrasladoHhppBloqueado.setDireccionActual(solicitud);
        requestTrasladoHhppBloqueado.setHabilitarRrMer(habilitarRR);
        requestTrasladoHhppBloqueado.setCreacionDesdeWebService(false);
        requestTrasladoHhppBloqueado.setSolicitudDesdeMER(true);
        requestTrasladoHhppBloqueado.setCentroPobladoDireccion(centroPobladoDireccion);
        requestTrasladoHhppBloqueado.setCiudadDireccion(ciudadDireccion);
        requestTrasladoHhppBloqueado.setDepartamentoDireccion(departamentoDireccion);
        return requestTrasladoHhppBloqueado;
    }

    /**
     * Carga Archivo de soporte. Permite cargar el archivo de soporte de la
     * solicitud
     *
     * @throws IOException
     * @author Johnnatan Ortiz
     */
    public void cargarArchivo() throws IOException {

        String usuario = usuarioVT;
        String solicitudStr = "SOL-HHPP" + solicitudCreated.getIdSolicitud()
                .toString().trim();
        Date fecha = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String fechaN = format.format(fecha);

        if (uploadedFile != null && uploadedFile.getFileName() != null) {
            String fileName = fechaN;
            fileName += FilenameUtils.getName(uploadedFile.getFileName());
            File file = new File(System.getProperty("user.dir"));
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            File archive = File.createTempFile(fileName + "-", "." + extension, file);
            FileOutputStream output = new FileOutputStream(archive);
            output.write(uploadedFile.getContent());
            output.close();
            try {
                String responseL
                        = solicitudFacadeLocal.uploadArchivoCambioEstrato(archive, fileName);

                if (responseL == null
                        || responseL.isEmpty()) {

                    String msg = "Error al subir el archivo";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                } else {
                    String msg = "Archivo cargado correctamente";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    msg, ""));
                    CmtTiempoSolicitudMgl tiempoSol
                            = solicitudCreated.getTiempoSolicitudMgl();
                    tiempoSol.setArchivoSoporte(solicitudStr + "_" + fileName);
                    cmtTiempoSolicitudMglFacadeLocal.update(tiempoSol);

                    CmtArchivosVtMgl cmtArchivosVtMgl = new CmtArchivosVtMgl();
                    cmtArchivosVtMgl.setSolicitudHhpp(solicitudCreated);
                    cmtArchivosVtMgl.setRutaArchivo(responseL);
                    cmtArchivosVtMgl.setNombreArchivo(solicitudStr + "_" + fileName);
                    cmtArchivosVtMgl = archivosVtMglFacadeLocal.create(cmtArchivosVtMgl, usuario, perfilVt);
                    if (cmtArchivosVtMgl.getIdArchivosVt() != null) {
                        LOGGER.info("Archivo almacenado en el repositorio");
                    } else {
                        LOGGER.info("Ocurrio un error al momento de guardar el archivo en el repositorio");
                    }
                }
                FileToolUtils.deleteFile(archive);
            } catch (ApplicationException e) {
                if (archive != null) {
                    try {
                        Files.deleteIfExists(archive.toPath());
                    } catch (IOException ex) {
                        LOGGER.error(ex.getMessage());
                    }
                }

                exceptionServBean.notifyError(e, "Error al realizar el cargue de archivo en la solicitud " + e.getMessage(), SOLICITUDES_HHPP);
            } catch (Exception e) {
                if (archive != null) {
                    try {
                        Files.deleteIfExists(archive.toPath());
                    } catch (IOException ex) {
                        LOGGER.error(ex.getMessage());
                    }
                }
                exceptionServBean.notifyError(e, "Ocurrió un error al cargar el archivo en la solicitud " + e.getMessage(), SOLICITUDES_HHPP);
            }
        }
    }

    /**
     * Función utilizada validar si la dirección ingresada ya se encuentra
     * asociada a una cuenta matriz.Se realiza la validación suprimiendo los
     * complementos del a dirección. Se realiza una copia y se le quita el
     * complemento a la dirección para esta ser buscada hasta la placa de la
     * dirección.
     *
     * @return
     * @author Juan David Hernandez return true si se encuentra una cuenta
     * matriz
     */
    public boolean validarSolicitudesActivasCuentaMatriz() {
        try {
            if (tipoAccionSolicitud.equalsIgnoreCase(CREACION_HOME_PASSED.getCodigoBasica())) {
                if (request.getDrDireccion() != null) {
                    DrDireccion drDireccionSinComplemento = request.getDrDireccion().clone();
                    drDireccionSinComplemento.setCpTipoNivel1(null);
                    drDireccionSinComplemento.setCpValorNivel1(null);
                    drDireccionSinComplemento.setCpTipoNivel2(null);
                    drDireccionSinComplemento.setCpValorNivel2(null);
                    drDireccionSinComplemento.setCpTipoNivel3(null);
                    drDireccionSinComplemento.setCpValorNivel3(null);
                    drDireccionSinComplemento.setCpTipoNivel4(null);
                    drDireccionSinComplemento.setCpValorNivel4(null);
                    drDireccionSinComplemento.setCpTipoNivel5(null);
                    drDireccionSinComplemento.setCpValorNivel5(null);
                    drDireccionSinComplemento.setCpTipoNivel6(null);
                    drDireccionSinComplemento.setCpValorNivel6(null);

                    List<CmtCuentaMatrizMgl> listaCmtCuentaMatrizMgl = cuentaMatrizFacadeLocal
                            .findCuentasMatricesByDrDireccion(centroPobladoSeleccionado, drDireccionSinComplemento);

                    if (listaCmtCuentaMatrizMgl != null && !listaCmtCuentaMatrizMgl.isEmpty()
                            && (!tipoAccionSolicitud.equals(CAMBIO_ESTRATO.getCodigoBasica()))) {

                        String msn = "La dirección se encuentra asociada a una "
                                + "cuenta matriz. "
                                + HTML_ITALIC_OPEN + "Número de cuenta" + HTML_ITALIC_CLOSE + ": " + listaCmtCuentaMatrizMgl.get(0)
                                .getNumeroCuenta()
                                + " No es posible realizar la solicitud. ";
                        FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
                        cuentaMatrizOtraDireccion = listaCmtCuentaMatrizMgl.get(0);
                        cuentaMatrizActiva = true;
                        crearSolicitudButton = false;
                        deshabilitarValidar = false;
                        habilitarViabilidad = false;
                        return false;

                    } else {
                        cuentaMatrizActiva = false;
                    }

                } else {
                    String msn = "Es necesario construir una dirección "
                            + "correctamente para realizar la validación sobre "
                            + " cuenta matrices. ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, msn,
                                    ""));
                    return false;
                }
            }
            return true;
        } catch (ApplicationException | CloneNotSupportedException e) {
            String msgError = "Ocurrió un error realizando la validación de la dirección en una cuenta matriz ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Función utilizada realizar el cambio de dirección cuando se presenta una
     * ambigua, segun la dirección seleccionada se cambia y se construye de
     * nuevo.
     *
     * @author Juan David Hernandez return true si se encuentra una cuenta
     * matriz
     */
    public void validarCambioDireccionAmbigua() {
        try {
            bloquearCambioAmbigua = true;
            /*Como la dirección ambigua se encuentra en la Malla
             * Antigua se envia FALSE*/
            construirDireccionCk(false);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error haciendo el cambio de dirección ambigua " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para validar y asignar los valores del cambio de dir.
     *
     * @return
     * @author Juan David Hernandez return true si el nuevo apartamento no
     * presenta inconvenientes.
     */
    public boolean validarNuevoApartamento() {
        try {
            if (tipoAccionSolicitud.equals(Constant.RR_DIR_CAMB_HHPP_1)
                    && !responseValidarDireccion.getCityEntity().getExisteRr()
                    .contains("(C)")) {

                if (nuevoApartamentoRr == null || nuevoApartamentoRr.isEmpty()
                        || requestNuevoApartamento == null) {
                    String msn = "Si realiza un cambio de dirección es"
                            + " necesario ingresar el nuevo apartamento ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, msn,
                                    ""));
                    return false;
                }

                //valida que se haya ingresado correctamente el apto
                if (requestNuevoApartamento.getDrDireccion()
                        .getCpTipoNivel5()
                        .equalsIgnoreCase(Constant.OPT_PISO_INTERIOR)
                        || requestNuevoApartamento.getDrDireccion()
                        .getCpTipoNivel5()
                        .equalsIgnoreCase(Constant.OPT_PISO_LOCAL)
                        || requestNuevoApartamento.getDrDireccion()
                        .getCpTipoNivel5()
                        .equalsIgnoreCase(Constant.OPT_PISO_APTO)
                        || requestNuevoApartamento.getDrDireccion()
                        .getCpTipoNivel5()
                        .equalsIgnoreCase(Constant.OPT_CASA_PISO)) {
                    if (requestNuevoApartamento.getDrDireccion()
                            .getCpValorNivel5() == null
                            || requestNuevoApartamento.getDrDireccion()
                            .getCpValorNivel5().isEmpty()
                            || requestNuevoApartamento.getDrDireccion()
                            .getCpValorNivel6() == null
                            || requestNuevoApartamento.getDrDireccion()
                            .getCpValorNivel6().isEmpty()) {
                        String msn = "Debe ingresar los 2 valores del "
                                + "nivel del nuevo apartamento por favor.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msn, ""));
                        return false;
                    }
                }
                
               
                /*valida que el nuevo apartamento no se encuentre repetido 
                 en unidades asociadas al predio*/
                if (unidadAuxiliarList != null && !unidadAuxiliarList.isEmpty()
                        && unidadList != null && !unidadList.isEmpty()) {
                    copiarInformacionUnidades();
                    for (UnidadStructPcml u : unidadAuxiliarList) {
                        if (u.getAptoUnidad().equalsIgnoreCase(nuevoApartamentoRr)) {

                            String msn = "El apartamento nuevo no puede ser "
                                    + " igual al de unidades asociadas en el "
                                    + " predio. ";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msn, ""));
                            return false;
                        }
                    }
                }

                /*Asignamos el nuevo apartamento al request con el que se va
                 * a crear la solicitud */
                request.getDrDireccion()
                        .setCpTipoNivel5(requestNuevoApartamento
                                .getDrDireccion().getCpTipoNivel5() != null ? requestNuevoApartamento
                                .getDrDireccion().getCpTipoNivel5() : null);
                request.getDrDireccion()
                        .setCpValorNivel5(requestNuevoApartamento
                                .getDrDireccion().getCpValorNivel5() != null ? requestNuevoApartamento
                                .getDrDireccion().getCpValorNivel5() : null);
                request.getDrDireccion()
                        .setCpTipoNivel6(requestNuevoApartamento
                                .getDrDireccion().getCpTipoNivel6() != null ? requestNuevoApartamento
                                .getDrDireccion().getCpTipoNivel6() : null);
                request.getDrDireccion()
                        .setCpValorNivel6(requestNuevoApartamento
                                .getDrDireccion().getCpValorNivel6() != null ? requestNuevoApartamento
                                .getDrDireccion().getCpValorNivel6() : null);
            }
            return true;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error realizando la validación del nuevo apartamento "
                    + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Función utilizada para habilitar panel de cambio de dirección.
     *
     * @param cityEntity
     * @author Juan David Hernandez
     */
    public void validarTipoSolicitudAccion(CityEntity cityEntity) {
        if (tipoAccionSolicitud.equals(Constant.RR_DIR_CAMB_HHPP_1)
                && !cityEntity.getExisteRr().contains("(C)")) {
            cambioDireccionPanel = true;
            enableApto = true;
        } else {
            cambioDireccionPanel = false;
            enableApto = false;
        }
    }

    /**
     * Función utilizada para hacer el rediccionamiento a la gestion de la
     * solicitud
     *
     * @author Juan David Hernandez}
     */
    public void irCuentaCMdir() {
        try {
            //JDHT
            if (cuentaMatrizOtraDireccion != null) {

                CuentaMatrizBean cuentaMatrizBean = (CuentaMatrizBean) JSFUtil.getBean("cuentaMatrizBean");
                ConsultaCuentasMatricesBean consultaCuentasMatricesBean = (ConsultaCuentasMatricesBean) JSFUtil.getBean("consultaCuentasMatricesBean");
                cuentaMatrizBean.setCuentaMatriz(cuentaMatrizOtraDireccion);
                consultaCuentasMatricesBean.obtenerTipoTecnologiaList();
                consultaCuentasMatricesBean.tecnologiasCoberturaCuentaMatriz();

                consultaCuentasMatricesBean.setTecnologiasCables(cuentaMatrizFacadeLocal.getTecnologiasInst(cuentaMatrizOtraDireccion, true));
                consultaCuentasMatricesBean.setTecnologiasVarias(cuentaMatrizFacadeLocal.getTecnologiasInst(cuentaMatrizOtraDireccion, false));
                cuentaMatrizOtraDireccion.setCoberturasList(consultaCuentasMatricesBean.getTecnologiasList());
                List<CmtSubEdificioMgl> subEdificiosMgl = cmtSubEdificioMglFacadeLocal.findSubEdificioByCuentaMatriz(cuentaMatrizOtraDireccion);
                if (CollectionUtils.isNotEmpty(subEdificiosMgl)) {
                    CmtCuentaMatrizMgl cmtCuentaMatrizMgl = cuentaMatrizOtraDireccion;
                    cmtCuentaMatrizMgl.setListCmtSubEdificioMgl(subEdificiosMgl);
                    cuentaMatrizBean.setCuentaMatriz(cmtCuentaMatrizMgl);
                } else {
                    CmtCuentaMatrizMgl cmtCuentaMatrizMgl = cuentaMatrizOtraDireccion;
                    cuentaMatrizBean.setCuentaMatriz(cmtCuentaMatrizMgl);
                }

                FacesUtil.navegarAPagina("/view/MGL/CM/tabs/hhpp.jsf");
            } else {
                String msn = "Ocurrio un error cargando la cuenta matriz que se desea redireccionar. Intente de nuevo por favor. ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msn, ""));
            }

        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error en irCuentaCMdir " + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error en irCuentaCMdir  " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para hacer el rediccionamiento al modelo de hhpp
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez}
     */
    public void irModeloHhpp() throws ApplicationException {
        try {
            if (solicitud != null && solicitud.getHhppMgl() != null) {
                // Instacia Bean de Session para obtener el hhpp seleccionado
                HhppDetalleSessionBean hhppDetalleSessionBean
                        = (HhppDetalleSessionBean) JSFUtil.getBean("hhppDetalleSessionBean");
                BigDecimal subDirId = null;
                if (solicitud.getHhppMgl().getSubDireccionObj() != null) {
                    subDirId = solicitud.getHhppMgl().getSubDireccionObj().getSdiId();
                }

                List<CmtDireccionDetalladaMgl> direccionDetalladaList = cmtDireccionDetalleMglFacadeLocal
                        .findDireccionDetallaByDirIdSdirId
                                (solicitud.getHhppMgl().getDireccionObj().getDirId(), subDirId);

                if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {
                    hhppDetalleSessionBean.setHhppSeleccionado(solicitud.getHhppMgl());
                    hhppDetalleSessionBean.setDireccionDetalladaSeleccionada(direccionDetalladaList.get(0));
                    FacesUtil.navegarAPagina("/view/MGL/VT/moduloHhpp/detalleHhpp.jsf");
                } else {
                    String msn = "No fue posible hallar la dirección "
                            + "detallada del Hhpp para hacer la redirección al modelo de hhpp";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn,
                                    ""));
                }
            }
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error en irModeloHhpp " + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error en irModeloHhpp : " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que realizar validación de los datos ingresados al cambio de
     * apartamento en las unidades asociadas al predio
     *
     * @return
     * @author Juan David Hernandez return false si ninguna unidad presenta
     * inconsistencias.
     */
    public boolean validarUnidadesPredio() {
        boolean errorEnUnidades = false;
        if (unidadesPredio) {
            if (!Objects.isNull(unidadAuxiliarList) && !unidadAuxiliarList.isEmpty()
                    && !Objects.isNull(unidadList) && !unidadList.isEmpty()) {
                copiarInformacionUnidades();

                for (UnidadStructPcml u : unidadAuxiliarList) {
                    u.setErrorValidator("");
                    //Validaciones para el nivel 5
                    if (!Objects.isNull(u.getTipoNivel5()) && !Objects.isNull(u.getValorNivel5())) {
                        if (!u.getTipoNivel5().trim().equalsIgnoreCase("CASA")
                                && !u.getTipoNivel5().trim().equalsIgnoreCase("FUENTE")
                                && !u.getTipoNivel5().trim().equalsIgnoreCase("RECEPTOR")
                                && !u.getTipoNivel5().trim().equalsIgnoreCase("ADMINISTRACION")) {
                            if (Objects.isNull(u.getValorNivel5()) || u.getValorNivel5().trim().isEmpty()) {
                                u.setErrorValidator("El valor para el campo "
                                        + u.getTipoNivel5() + " es obligatorio");
                                String msn = "Existen valores no permitidos para"
                                        + " las unidades en "
                                        + "el Predio ";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                msn, ""));
                                return true;
                            }
                        }

                        //nueva validacion Inspira
                        /**
                         * ******** INICIO VALIDACIONES TECNOLOGIA BI **********
                         */
                        if (Constant.HFC_BID.equalsIgnoreCase(tipoTecnologiaBasica.getIdentificadorInternoApp())
                                && !Objects.isNull(u.getTipoNivel5())
                                && !Objects.isNull(u.getValorNivel5())) {

                            if ("CASA".equalsIgnoreCase(u.getTipoNivel5().trim())
                                    && !u.getValorNivel5().isEmpty()) {
                                u.setErrorValidator("El valor CASA para tecnología"
                                        + " BI no puede llevar complemento");
                                String msn = "Existen valores no permitidos para"
                                        + " las unidades en "
                                        + "el Predio ";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                msn, ""));
                                return true;
                            }

                            if ("APARTAMENTO".equalsIgnoreCase(u.getTipoNivel5().trim())) {
                                u.setErrorValidator("No es posible agregar este tipo de"
                                        + " nivel para tecnología BI. Seleccione PISO + APARTAMENTO.");
                                String msn = "Existen valores no permitidos para"
                                        + " las unidades en "
                                        + "el Predio ";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                msn, ""));
                                return true;
                            } else if (u.getTipoNivel5().trim().equalsIgnoreCase("INTERIOR")) {
                                u.setErrorValidator("No es posible agregar este"
                                        + " tipo de nivel para tecnología BI. Seleccione"
                                        + " PISO + INTERIOR.");
                                String msn = "Existen valores no permitidos para"
                                        + " las unidades en el Predio ";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                msn, ""));
                                return true;

                            } else if (u.getTipoNivel5().trim().equalsIgnoreCase("LOCAL")) {
                                u.setErrorValidator("No es posible agregar "
                                        + "este tipo de nivel para tecnología BI."
                                        + " Seleccione PISO + LOCAL.");
                                String msn = "Existen valores no permitidos para"
                                        + " las unidades en el Predio ";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                msn, ""));
                                return true;
                            }
                        }
                        /**
                         * ******** FIN VALIDACIONES TECNOLOGIA BI **********
                         */

                        if (u.getTipoNivel5().contains("+")) {
                            if (u.getTipoNivel6() == null
                                    || u.getTipoNivel6().trim().isEmpty()) {
                                u.setErrorValidator("Debe seleccionar valor en la "
                                        + "segunda lista ");
                                String msn = "Existen valores no permitidos"
                                        + " para las unidades en "
                                        + "el Predio ";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                msn, ""));
                                return true;
                            }

                        } else if (u.getTipoNivel6() != null
                                && !u.getTipoNivel6().trim().isEmpty()) {
                            u.setErrorValidator("No Debe seleccionar valor en la "
                                    + "segunda lista ");
                            String msn = "Existen valores no permitidos "
                                    + "para las unidades en "
                                    + "el Predio ";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msn, ""));
                            return true;
                        }
                        if (u.getTipoNivel5().contains("PI") && !u.getTipoNivel5().contains("CASA")) {
                            if (!u.getValorNivel5().equalsIgnoreCase("1")
                                    && !u.getValorNivel5().equalsIgnoreCase("2")
                                    && !u.getValorNivel5().equalsIgnoreCase("3")
                                    && tipoTecnologiaBasica.getIdentificadorInternoApp() != null
                                    && tipoTecnologiaBasica.getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_BID)) {
                                u.setErrorValidator("El valor permitido en "
                                        + "PISO es 1, 2, o 3. ");
                                String msn = "Existen valores no permitidos para"
                                        + " las unidades en "
                                        + "el Predio ";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                msn, ""));
                                return true;
                            }
                        }
                    }
                    if (u.getTipoNivel5() != null && u.getTipoNivel5().contains("PI") && !u.getTipoNivel5().contains("CASA")) {
                        if (!"1".equalsIgnoreCase(u.getValorNivel5())
                                && !"2".equalsIgnoreCase(u.getValorNivel5())
                                && !"3".equalsIgnoreCase(u.getValorNivel5())
                                && tipoTecnologiaBasica.getIdentificadorInternoApp() != null
                                && Constant.HFC_BID.equalsIgnoreCase(tipoTecnologiaBasica.getIdentificadorInternoApp())) {
                            u.setErrorValidator("El valor permitido en "
                                    + "PISO es 1, 2, o 3. ");
                            String msn = "Existen valores no permitidos para"
                                    + " las unidades en "
                                    + "el Predio ";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msn, ""));
                            return true;
                        }
                    }
                    //Validaciones para el campo del nivel 5
                    if (u.getValorNivel5() != null && !u.getValorNivel5().trim().isEmpty()) {
                        if (u.getTipoNivel5() == null
                                || u.getTipoNivel5().trim().isEmpty()) {
                            u.setErrorValidator("Seleccione valor para la "
                                    + "primera lista");
                            String msn = "Existen valores no permitidos"
                                    + " para las unidades en "
                                    + "el Predio ";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msn, ""));
                            return true;
                        }
                    }
                    //Validaciones para el campo del nivel 5
                    if (u.getValorNivel6() != null && !u.getValorNivel6().trim().isEmpty()) {
                        if (u.getTipoNivel6() == null || u.getTipoNivel6().trim().isEmpty()) {
                            u.setErrorValidator("Seleccione valor para la "
                                    + "segunda lista");
                            String msn = "Existen valores no permitidos "
                                    + "para las unidades en "
                                    + "el Predio ";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msn, ""));
                            return true;
                        }
                    }
                }
            }
        }
        return errorEnUnidades;
    }

    /**
     * Función que realizar validación de los datos ingresados al cambio de
     * apartamento en las unidades asociadas al predio NO sea igual al de la
     * solicitud que se esta creando.
     *
     * @return
     * @author Juan David Hernandez return true si ninguna unidad presenta
     * inconsistencias
     */
    public boolean validarInformacionUnidadesPredios() {
        try {
            if (unidadesPredio) {
                if (unidadAuxiliarList != null && !unidadAuxiliarList.isEmpty()
                        && unidadList != null && !unidadList.isEmpty()) {
                    copiarInformacionUnidades();
                    for (UnidadStructPcml u : unidadAuxiliarList) {
                        //Validación con hhpp que se esta creando.
                        if (u.getTipoNivel5() != null
                                && responseConstruirDireccion
                                .getDrDireccion().getCpTipoNivel5() != null
                                && u.getValorNivel5() != null
                                && responseConstruirDireccion
                                .getDrDireccion().getCpValorNivel5() != null
                                && (u.getTipoNivel5()
                                .equals(responseConstruirDireccion
                                        .getDrDireccion().getCpTipoNivel5()))
                                && u.getValorNivel5().trim()
                                .equals(responseConstruirDireccion
                                        .getDrDireccion().getCpValorNivel5())
                                && (u.getTipoNivel6() == null || u.getTipoNivel6().isEmpty())
                                && (responseConstruirDireccion
                                .getDrDireccion().getCpTipoNivel6() == null
                                || responseConstruirDireccion
                                .getDrDireccion().getCpTipoNivel6().isEmpty())) {
                            u.setErrorValidator("El HHPP solicitado a crear no"
                                    + " puede ser igual al HHPP solicitado en"
                                    + " unidades del predio.");
                            String msn = "Existen valores no permitidos"
                                    + " para las unidades en el Predio ";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msn, ""));
                            return false;
                        } else {
                            if (u.getAptoUnidad().equalsIgnoreCase("CASA")
                                    && responseConstruirDireccion
                                    .getDrDireccion().getCpTipoNivel5().contains("PI")
                                    && (u.getTipoNivel5() == null || u.getTipoNivel5().isEmpty())) {

                                u.setErrorValidator("Es necesario que realice cambio de apto "
                                        + "a la unidad que tiene 'CASA' para continuar "
                                        + "con la solicitud de creación de Piso.");
                                String msn = "Existen valores no permitidos"
                                        + " para las unidades en el Predio ";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                msn, ""));
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error en validar información unidades predios " + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Verifica si fue seleccionada una tecnología para la solicitud.
     * @author Gildardo Mora
     */
    public void validarTecnologiaSeleccionada() {
        if (StringUtils.isBlank(tipoTecnologia)) {
            LOGGER.warn("No hay tecnología seleccionada");
            return;
        }

        validarTecnologiaSeleccionada(tipoTecnologia);
    }

    /**
     * Función utilizada para obtener la configuración de listados según
     * tecnología seleccionada datos.
     * @param tipoTecnologia {@link String} Tecnología seleccionada
     * @author Juan David Hernandez
     */
    private void validarTecnologiaSeleccionada(String tipoTecnologia) {
        try {
            Objects.requireNonNull(tipoTecnologia, "No hay tecnología seleccionada para validar");
            tipoTecnologiaBasica = obtenerValorTecnologiaBasica(tipoTecnologia);
            if (tipoTecnologiaBasica != null && tipoTecnologiaBasica.getBasicaId() != null) {
                cargarListadosConfiguracion();
                obtenerConfiguracionTipoTecnologia(tipoTecnologiaBasica.getBasicaId());
            }

        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al validar la tecnología seleccionada " + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al validar la tecnología seleccionada: " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para obtener el valor del objeto tipo basica de
     * tecnologia.
     *
     * @param tecnologiaSeleccionada
     * @return
     * @author Juan David Hernandez
     */
    public CmtBasicaMgl obtenerValorTecnologiaBasica(String tecnologiaSeleccionada) {
        try {
            if (StringUtils.isBlank(tecnologiaSeleccionada)) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "No se seleccionó una tecnología");
                return null;
            }

            return cmtBasicaMglFacadeLocal.findById(new BigDecimal(tecnologiaSeleccionada));

        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al obtener el valor de la tecnología " + e.getMessage(), SOLICITUDES_HHPP);
            return null;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al obtener el valor de la tecnología: " + e.getMessage(), SOLICITUDES_HHPP);
            return null;
        }
    }

    /**
     * Función utilizada para obtener el listado de ciudades desde la base
     * datos.
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    public void obtenerListaCiudades() throws ApplicationException {
        try {
            if (rrRegional != null && !rrRegional.equals("")
                    && !rrRegional.isEmpty()) {
                //Obtiene listado de ciudad por regional
                reiniciarDireccion();
            } else {
                ciudadesList = null;
                centroPobladoList = null;
                reiniciarDireccion();
            }
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al realizar consulta para obtener listado de ciudades " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para la configuracion de la tecnologia seleccionada.
     *
     * @param tipoTecnologiaId
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    public void obtenerConfiguracionTipoTecnologia(BigDecimal tipoTecnologiaId)
            throws ApplicationException {
        try {

            CmtExtendidaTecnologiaMgl extendidaTecnologia;
            CmtBasicaMgl tecnologiaBasic = new CmtBasicaMgl();
            tecnologiaBasic.setBasicaId(tipoTecnologiaId);
            extendidaTecnologia = cmtExtendidaTecnologiaMglFacadeLocal
                    .findBytipoTecnologiaObj(tecnologiaBasic);

            if (extendidaTecnologia != null
                    && extendidaTecnologia.getReqSoporte() == 1) {
                soporteRequerido = true;
                /*Si el canal es PYMES no se debe pedir soporte sin importa si la
                tecnologia lo exije*/
                if (solicitud.getTipoSol() != null && !solicitud.getTipoSol().isEmpty()
                        && (solicitud.getTipoSol().equalsIgnoreCase("PYMES")
                        || solicitud.getTipoSol().equalsIgnoreCase("TRASLADOS")
                        || solicitud.getTipoSol().equalsIgnoreCase("CORTESIA")
                        || solicitud.getTipoSol().equalsIgnoreCase("FUENTE DE PODER"))) {
                    soporteRequerido = false;
                }
            } else {
                soporteRequerido = false;
            }

        } catch (ApplicationException e) {
            String msgError = "Error al realizar consulta para obtener la configuración de la tecnología seleccionada ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            String msgError = "Error al realizar consulta para obtener la configuración de la tecnología seleccionada. ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para la configuracion de la tecnologia seleccionada.
     *
     * @author Juan David Hernandez
     */
    public void validarSoporteRequeridoArea() {
        try {

            if (solicitud != null && solicitud.getTipoSol() != null
                    && !solicitud.getTipoSol().isEmpty()
                    && (solicitud.getTipoSol().equalsIgnoreCase("PYMES")
                    || solicitud.getTipoSol().equalsIgnoreCase("TRASLADOS")
                    || solicitud.getTipoSol().equalsIgnoreCase("CORTESIA")
                    || solicitud.getTipoSol().equalsIgnoreCase("FUENTE DE PODER"))) {
                soporteRequerido = false;
            }

        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al validar soporte requerido por area " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para validar la ciudad seleccionada y limpiar los
     * paneles de dirección en la pantalla.
     *
     * @author Juan David Hernandez
     */
    public void validarCiudadSeleccionada() {
        try {
            if (rrCiudad == null || rrCiudad.isEmpty()) {
                hideTipoDireccion();
            } else {
                //Obtiene centro Poblado
                obtenerGeograficoPoliticoList();
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al validar ciudad seleccionada" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al validar ciudad seleccionada" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función utilizada para obtener del georeferenciador el departamento,
     * ciudad y el listado de centro poblado.
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    public void obtenerGeograficoPoliticoList() throws ApplicationException {
        try {
            GeograficoPoliticoMgl geograficoPoliticoMgl
                    = geograficoPoliticoMglFacadeLocal
                    .findCityByComundidad(rrCiudad);
            departamentoGpo = (geograficoPoliticoMglFacadeLocal
                    .findById(geograficoPoliticoMgl.getGeoGpoId()));
            ciudadGpo = geograficoPoliticoMgl;
            centroPobladoList = geograficoPoliticoMglFacadeLocal
                    .findCentroPoblado(ciudadGpo.getGpoId());
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al validar ciudad seleccionada " + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al validar ciudad seleccionada. " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para obtiener valores de tipo de acción de solicitud
     * básica.
     *
     * @author Juan David Hernandez
     */
    public void obtenerTipoAccionSolicitudList() {
        try {
            CmtTipoBasicaMgl tipoBasicaTipoAccion;
            tipoBasicaTipoAccion = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_ACCION_SOLICITUD);
            tipoAccionSolicitudBasicaList
                    = cmtBasicaMglFacadeLocal
                    .findByTipoBasica(tipoBasicaTipoAccion);

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al obtener el tipo de acción de la solicitud" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener el tipo de acción de la solicitud" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función utilizada para obtener el listado de tipos de solicitudes desde
     * la base de datos
     *
     * @author Juan David Hernandez
     */
    private void cargarListadoAreaCanal() {
        try {
            areaCanalList = parametrosCallesFacade.findByTipo("TIPO_SOLICITUD");
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al realizar consulta para obtener listado de tipo de solicitudes " + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al realziar consulta para obtener listado de tipo de solicitudes. " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Funcion utilizada para obtener los listados utilizados en la pantalla
     * desde la base de datos
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    public void cargarListadosConfiguracion() throws ApplicationException {
        try {
            //Obtiene lista de listas de los componentes de la dirección.          
            configurationAddressComponent
                    = componenteDireccionesMglFacade
                    .getConfiguracionComponente(tipoTecnologiaBasica.getAbreviatura(),
                            tipoTecnologiaBasica.getBasicaId());

            ckList = componenteDireccionesMglFacade
                    .obtenerCalleCarreraList(configurationAddressComponent);
            bmList = componenteDireccionesMglFacade
                    .obtenerBarrioManzanaList(configurationAddressComponent);
            itList = componenteDireccionesMglFacade
                    .obtenerIntraducibleList(configurationAddressComponent);
            aptoList = componenteDireccionesMglFacade
                    .obtenerApartamentoList(configurationAddressComponent);
            
            OpcionIdNombre elemento = null;
            for (OpcionIdNombre opcion : aptoList) {
                if (opcion.getDescripcion().equals("CAJERO")) {
                    elemento = opcion;
                }
            }
            aptoList.remove(elemento);     
            
            complementoList = componenteDireccionesMglFacade
                    .obtenerComplementoList(configurationAddressComponent);
            configurationAddressComponent.getAptoValues();

        } catch (ApplicationException e) {
            String msgError = "Error al realizar consultas para obtener configuración de listados";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            String msgError = "Error al realizar consultas para obtener configuración de listados. ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Funcion utilizada para obtener los listados utilizados en la pantalla
     * desde la base de datos sin tener en cuenta la tecnologia
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    public void cargarListadosConfiguracionGenericos() throws ApplicationException {
        try {
            //Obtiene lista de listas de los componentes de la dirección.          
            configurationAddressComponent
                    = componenteDireccionesMglFacade
                    .getConfiguracionComponente();

            ckList = componenteDireccionesMglFacade
                    .obtenerCalleCarreraList(configurationAddressComponent);
            bmList = componenteDireccionesMglFacade
                    .obtenerBarrioManzanaList(configurationAddressComponent);
            itList = componenteDireccionesMglFacade
                    .obtenerIntraducibleList(configurationAddressComponent);
            aptoList = componenteDireccionesMglFacade
                    .obtenerApartamentoList(configurationAddressComponent);

            complementoList = componenteDireccionesMglFacade
                    .obtenerComplementoList(configurationAddressComponent);
            configurationAddressComponent.getAptoValues();

        } catch (ApplicationException e) {
            String msgError = "Error al realizar consultas para obtener configuración generica de listados ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            String msgError = "Error al realizar consultas para obtener configuración generica de listados. ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que extrae los valores tipo dirección y los agrupa en un solo
     * listado.
     *
     * @author Juan David Hernandez
     */
    public void validarTipoDireccion() {
        try {
            if (tipoDireccion.equals("CK")) {
                limpiarCamposTipoDireccion();
                showCK();
            } else if (tipoDireccion.equals("BM")) {
                limpiarCamposTipoDireccion();
                showBM();
            } else if (tipoDireccion.equals("IT")) {
                limpiarCamposTipoDireccion();
                showIT();
            } else if (tipoDireccion.equals("")) {
                limpiarCamposTipoDireccion();
            }
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al validar el tipo de dirección seleccionado " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que limpiar los valores de la pantalla
     *
     * @author Juan David Hernandez
     */
    public void limpiarFormulario() {
        request = new RequestConstruccionDireccion();
        responseValidarDireccion = new ResponseValidacionDireccion();
        responseConstruirDireccion = new ResponseConstruccionDireccion();
        solicitud = new Solicitud();
        tipoTecnologiaBasica = new CmtBasicaMgl();
        tipoTecnologia = "";
        tipoAccionSolicitud = "";
        asignarUsuarioActivo();
        regionalList = new ArrayList<>();
        ciudadesList = new ArrayList<>();
        unidadList = new ArrayList<>();
        centroPobladoList = new ArrayList<>();
        drDireccion = new DrDireccion();
        rrRegional = "";
        rrCiudad = "";
        tipoDireccion = "";
        dirEstado = "";
        direccionCk = "";
        nivelValorBm = "";
        nivelValorIt = "";
        tipoNivelBm = "";
        tipoNivelIt = "";
        tipoNivelApartamento = "";
        tipoNivelComplemento = "";
        apartamento = "";
        complemento = "";
        direccionLabel = "";
        barrioSugeridoStr = "";
        barrioSugerido = "";
        enableApto = false;
        direccionesCambioEstrato = false;
        cambioDireccionPanel = false;
        barrioSugeridoPanel = false;
        barrioSugeridoField = false;
        crearSolicitudButton = false;
        direccionConstruida = false;
        deshabilitarCreacion = false;
        deshabilitarValidar = false;
        habilitarViabilidad = false;
        cuentaMatrizActiva = false;
        hhppExiste = false;
        enableTabs = true;
        unidadesPredio = false;
        apartamentoIngresado = false;
        direccionAmbiguaPanel = false;
        ambiguaAntigua = false;
        ambiguaNueva = false;
        direccionAmbiguaAntigua = "";
        direccionAmbiguaNueva = "";
        idCentroPoblado = BigDecimal.ZERO;
        bloquearCambioAmbigua = false;
        departamento = "";
        ciudad = BigDecimal.ZERO;
        createDireccion = "";
        notGeoReferenciado = true;
        uploadedFile = null;
        hhppList = new ArrayList<>();
        tipoViviendaSeleccionada = "";
        direccionRespuestaGeo = "";
        hideTipoDireccion();
        showCK();
        limpiarNuevoApartamento();
        lstFilesCambiosEstrato = new ArrayList<>();
        lstFilesEscalamientoHHPP = new ArrayList<>();
        showAdjuntoEscalamiento = false;
        tipoHHPP = "";
        ccmm = "";
        tipoGestion = "";
        solicitudOT = "";
        showContacto = true;
        numCuentaClienteTraslado = "";
        tipoApartTmp = "";
    }

    public void iniciarNuevaSolicitud() {
        iniciarTiempoCreaSolicitud();
        solicitudCreated = new Solicitud();
        cmtTiempoSolicitudDth = new CmtTiempoSolicitudMgl();
        cmtTiempoSolicitudMglToCreate = new CmtTiempoSolicitudMgl();
        enableTabs = false;
        goGestion = false;
        createDireccion = "";
        notGeoReferenciado = true;
        showContacto = true;
        hayComplmento = false;
        limpiarFormulario();
    }

    public void limpiarDireccion() {
        hayComplmento = false;
        request = new RequestConstruccionDireccion();
        responseValidarDireccion = new ResponseValidacionDireccion();
        responseConstruirDireccion = new ResponseConstruccionDireccion();
        asignarUsuarioActivo();
        reiniciarDireccion();
        tipoTecnologiaBasica = new CmtBasicaMgl();
        tipoTecnologia = "";
        drDireccion = new DrDireccion();
        tipoDireccion = "";
        direccionCk = "";
        nivelValorBm = "";
        nivelValorIt = "";
        tipoNivelBm = "";
        tipoNivelIt = "";
        tipoNivelApartamento = "";
        tipoNivelComplemento = "";
        apartamento = "";
        complemento = "";
        direccionLabel = "";
        barrioSugeridoStr = "";
        enableApto = false;
        direccionesCambioEstrato = false;
        cambioDireccionPanel = false;
        barrioSugeridoPanel = false;
        barrioSugeridoField = false;
        crearSolicitudButton = false;
        direccionConstruida = false;
        deshabilitarCreacion = false;
        deshabilitarValidar = false;
        habilitarViabilidad = false;
        cuentaMatrizActiva = false;
        hhppExiste = false;
        enableTabs = false;
        unidadesPredio = false;
        apartamentoIngresado = false;
        barrioSugerido = "";
        direccionAmbiguaPanel = false;
        ambiguaAntigua = false;
        ambiguaNueva = false;
        direccionAmbiguaAntigua = "";
        direccionAmbiguaNueva = "";
        bloquearCambioAmbigua = false;
        departamento = "";
        ciudad = BigDecimal.ZERO;
        createDireccion = "";
        notGeoReferenciado = true;
        uploadedFile = null;
        tipoViviendaSeleccionada = "";
        direccionRespuestaGeo = "";
        hhppList = new ArrayList<>();
        hideTipoDireccion();
        limpiarNuevoApartamento();
        showCK();
        lstFilesCambiosEstrato = new ArrayList<>();
        tipoApartTmp = "";
    }

    /**
     * Función que limpiar los valores de la pantalla de tipo dirección
     *
     * @author Juan David Hernandez
     */
    public void limpiarCamposTipoDireccion() {
        drDireccion = new DrDireccion();
        responseConstruirDireccion.setDrDireccion(null);
        responseConstruirDireccion.setDireccionRespuestaGeo(null);
        direccionCk = "";
        nivelValorIt = "";
        nivelValorBm = "";
        tipoNivelBm = "";
        tipoNivelIt = "";
        tipoNivelApartamento = "";
        tipoNivelComplemento = "";
        direccionLabel = "";
        apartamento = "";
        complemento = "";
        unidadesPredio = false;
        tipoApartTmp = "";
    }

    /**
     * Función utilizada para reiniciar la dirección al realizar un cambio de
     * ciudad.
     *
     * @author Juan David Hernandez
     */
    public void reiniciarDireccion() {
        request = new RequestConstruccionDireccion();
        responseValidarDireccion = new ResponseValidacionDireccion();
        responseConstruirDireccion = new ResponseConstruccionDireccion();
        tipoAccionSolicitud = "";
        drDireccion = new DrDireccion();
        tipoDireccion = "";
        direccionCk = "";
        nivelValorBm = "";
        nivelValorIt = "";
        tipoNivelBm = "";
        tipoNivelIt = "";
        tipoNivelApartamento = "";
        tipoNivelComplemento = "";
        apartamento = "";
        complemento = "";
        direccionLabel = "";
        barrioSugeridoStr = "";
        barrioSugeridoPanel = false;
        barrioSugeridoField = false;
        crearSolicitudButton = false;
        direccionConstruida = false;
        deshabilitarCreacion = false;
        deshabilitarValidar = false;
        habilitarViabilidad = false;
        enableTabs = false;
        unidadesPredio = false;
        apartamentoIngresado = false;
        barrioSugerido = "";
        rrCiudad = "";
        rrRegional = "";
        idCentroPoblado = null;
        centroPobladoList = null;
        createDireccion = "";
        notGeoReferenciado = true;
        direccionRespuestaGeo = "";
        hideTipoDireccion();
        limpiarNuevoApartamento();
        showCK();
        tipoApartTmp = "";
    }

    /**
     * Función que guardar una nota asociada a la solicitud
     *
     * @author Juan David Hernandez
     */
    public void guardarNota() {
        try {
            if (validarCamposNota()) {
                cmtNotasSolicitudVtMgl.setSolicitud(solicitudCreated);
                cmtNotasSolicitudVtMgl.setTipoNotaObj(tipoNotaSelected);
                notasSolicitudVtMglFacadeLocal.setUser(usuarioVT, perfilVt);
                cmtNotasSolicitudVtMgl
                        = notasSolicitudVtMglFacadeLocal
                        .crearNotSol(cmtNotasSolicitudVtMgl);
                if (cmtNotasSolicitudVtMgl != null
                        && cmtNotasSolicitudVtMgl.getNotasId() != null) {
                    findNotasBySolicitud();
                    tipoNotaSelected = new CmtBasicaMgl();
                    cmtNotasSolicitudVtMgl = new CmtNotasSolicitudVtMgl();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Nota agregada correctamente.", ""));
                }
                cambiarTab("NOTAS");
            }
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al guardar nota " + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error al guardar Nota" + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Funcion que se encarga de carga la lista de estrato posibles para el
     * cambio de estrato
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    public void obtenerEstratoList() throws ApplicationException {
        try {
            CmtTipoBasicaMgl cmtTipoBasica = cmtTipoBasicaMgl.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_ESTRATO_HHPP);
            estratoHhppList = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasica);
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al obtener el listado de estratos para HHPP " + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al obtener la lista de estratos para HHPP " + e.getMessage(), SOLICITUDES_HHPP);
        }

    }

    /**
     * Función que valida los datos necesarios para guardar una nota esten
     * ingresados
     *
     * @return {@code boolean}
     * @author Juan David Hernandez
     */
    public boolean validarCamposNota() {
        try {
            Objects.requireNonNull(cmtNotasSolicitudVtMgl, "El objeto de cmtNotasSolicitudVtMgl es nulo");

            if (StringUtils.isBlank(cmtNotasSolicitudVtMgl.getDescripcion())) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,"Ingrese una descripción a la nota por favor.");
                return false;
            }

            if (tipoNotaSelected.getBasicaId() == null) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,"Ingrese un tipo de nota por favor.");
                return false;
            }

            if (StringUtils.isBlank(cmtNotasSolicitudVtMgl.getNota())) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN,"Ingrese la nota que desea guardar por favor.");
                return false;
            }

            return true;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error validando campos al agregar nota " + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Función que guardar una comentario a una nota asociada a la solicitud
     *
     * @author Juan David Hernandez
     */
    public void guardarComentarioNota() {
        try {
            CmtNotasSolicitudDetalleVtMgl notaComentarioMgl = new CmtNotasSolicitudDetalleVtMgl();
            notaComentarioMgl.setNota(notaComentarioStr);
            notaComentarioMgl.setNotaSolicitud(cmtNotasSolicitudVtMgl);
            notasSolicitudDetalleVtMglFacadeLocal.setUser(usuarioVT, perfilVt);
            notaComentarioMgl
                    = notasSolicitudDetalleVtMglFacadeLocal.create(notaComentarioMgl);
            if (notaComentarioMgl.getNotasDetalleId() != null) {
                notaComentarioStr = "";
                tipoNotaSelected = new CmtBasicaMgl();
                cmtNotasSolicitudVtMgl = new CmtNotasSolicitudVtMgl();
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.INFO, "Comentario añadido correctamente.");
            }
            cambiarTab("NOTAS");
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al guardar comentario en la nota seleccionada " + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al guardar comentario en la nota seleccionada: " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función utilizada para mostrar el panel que permite agregar un comentario
     *
     * @param nota
     * @author Juan David Hernandez
     */
    public void mostarComentario(CmtNotasSolicitudVtMgl nota) {
        cmtNotasSolicitudVtMgl = nota;
        tipoNotaSelected = nota.getTipoNotaObj();
        cambiarTab("NOTAS");
    }

    public void limpiarCamposNota() {
        cmtNotasSolicitudVtMgl = new CmtNotasSolicitudVtMgl();
        tipoNotaSelected = new CmtBasicaMgl();
    }

    /**
     * Función que inicializa el cronómetro del tiempo que se toma el usuario en
     * crear una solicitud.
     *
     * @author Juan David Hernandez
     */
    public void iniciarTiempoCreaSolicitud() {
        FacesContext facesContextL = FacesContext.getCurrentInstance();
        HttpSession sessionL = (HttpSession) facesContextL.getExternalContext().getSession(false);

        tmpInicio = null;
        deltaTiempo = null;
        tmpFin = null;
        timeSol = null;
        dateInicioCreacion = new Date();
        sessionL.setAttribute("tiempoInicio", null);
        tmpInicio = (String) sessionL.getAttribute("tiempoInicio");

        if (tmpInicio == null) {
            long milli = (new Date()).getTime();
            if (sessionL.getAttribute("deltaTiempo") != null) {
                long delta = ((Long) sessionL.getAttribute("deltaTiempo")).longValue();
                milli = milli - (delta);
            }
            tmpInicio = milli + "";
            sessionL.setAttribute("tiempoInicio", tmpInicio);
        }
    }

    /**
     * Función que asigna el tiempo de espera que tuvo la solicitud la pantalla
     * de listado de solicitudes. desde el momento en el que se creo la
     * solicitud hasta cuando ingreso a la pantalla de gestionar la solicitud
     *
     * @author Juan David Hernandez
     */
    public void asignarTiempoEsperaGestion() {
        try {
            cmtTiempoSolicitudMglToCreate.setTiempoEspera(getTiempo(solicitudCreated.getFechaIngreso(), new Date()));
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al asignar el tiempo de espera de la solicitud" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que se utiliza para realizar el cronómetro en pantalla
     *
     * @param dateInicio
     * @param dateFin
     * @return result
     * @author Juan David Hernandez
     */
    public String getTiempo(Date dateInicio, Date dateFin) {
        String result = "00:00:00";
        if (dateInicio != null) {
            Date fechaG = new Date();
            if (dateFin != null) {
                fechaG = dateFin;
            }
            long diffDate = fechaG.getTime() - dateInicio.getTime();
            //Diferencia de las Fechas en Segundos
            long diffSeconds = Math.abs(diffDate / 1000);
            //Diferencia de las Fechas en Minutos
            long diffMinutes = Math.abs(diffDate / (60 * 1000));
            long seconds = diffSeconds % 60;
            long minutes = diffMinutes % 60;
            long hours = Math.abs(diffDate / (60 * 60 * 1000));

            String hoursStr = (hours < 10 ? "0" + String.valueOf(hours)
                    : String.valueOf(hours));
            String minutesStr = (minutes < 10 ? "0" + String.valueOf(minutes)
                    : String.valueOf(minutes));
            String secondsStr = (seconds < 10 ? "0" + String.valueOf(seconds)
                    : String.valueOf(seconds));

            result = hoursStr + ":" + minutesStr + ":" + secondsStr;
        }
        return result;
    }

    /**
     * Función que asigna el usuario activo para los datos de auditoria. de la
     * base de datos.
     *
     * @author Juan David Hernandez
     */
    public void asignarUsuarioActivo() {
        try {
            if (usuarioLogin != null) {
                solicitud.setSolicitante(usuarioLogin.getNombre() != null
                        ? usuarioLogin.getNombre() : "Usuario Invalido");
                solicitud.setCorreo(usuarioLogin.getEmail() != null
                        ? usuarioLogin.getEmail() : "Usuario Invalido");
            }
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al intentar asignar el usuario activo " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que redirecciona al menú principal de la aplicación.
     *
     * @throws java.io.IOException
     * @author Juan David Hernandez
     */
    public void redirecToMenu() throws IOException {
        try {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .redirect("/Visitas_Tecnicas/faces/Gestion.jsp");
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se genera error al itentar regresar al menú principal" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al itentar regresar al menú principal" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que permite navegar entre Tabs en la pantalla de gestión de la
     * solicitud
     *
     * @param sSeleccionado
     * @author Juan David Hernandez
     */
    public void cambiarTab(String sSeleccionado) {
        try {
            ConstantsCM.TABS_MOD_CM Seleccionado
                    = ConstantsCM.TABS_MOD_CM.valueOf(sSeleccionado);
            switch (Seleccionado) {
                case SOLICITUD:
                    selectedTab = "SOLICITUD";
                    showSolicitud();
                    break;
                case TRACK:
                    selectedTab = "TRACK";
                    findTrackBySolicitud();
                    showTrack();
                    break;
                case NOTAS:
                    selectedTab = "NOTAS";
                    findNotasBySolicitud();
                    showNotas();
                    break;
            }
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al realizar el cambio de Tab " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que obtiene los tiempos de la traza de las solicitud seleccionada
     *
     * @author Juan David Hernandez
     */
    public void findTrackBySolicitud() {
        try {
            /*asigna tiempo transcurrido en la creación de la solicitud
             * (cronómetro en pantalla) */
            cmtTiempoSolicitudMglToCreate.setTiempoSolicitud(timeSol);
            /*asigna el tiempo que ha transcurrido desde el momento que se creo
             la solicitud y el instante actual */
            cmtTiempoSolicitudMglToCreate.setTiempoEspera(getTiempo(solicitudCreated.getFechaIngreso(), new Date()));
            /*asigna el tiempo total entre la creación de la solicitud y 
             el momento en el que se realiza la gestión */
            cmtTiempoSolicitudMglToCreate.setTiempoTotal(getTiempo(solicitudCreated.getFechaIngreso(), new Date()));
            //obtiene el ANS de la solitidu por tipo de solicitud
            cmtTipoSolicitudMgl = cmtTipoSolicitudMglFacadeLocal
                    .findTipoSolicitudByAbreviatura(solicitudCreated.getTipo());

        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al obtener tiempos de la solicitud " + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al obtener tiempos de la solicitud: " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que obtiene las notas de la solicitud creada.
     *
     * @author Juan David Hernandez
     */
    public void findNotasBySolicitud() {
        try {
            notasSolicitudList = notasSolicitudVtMglFacadeLocal
                    .findNotasBySolicitudId(solicitudCreated.getIdSolicitud());
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al obtener las notas de la solicitud " + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al obtener las notas de la solicitud: " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que obtiene tipo de notas
     *
     * @author Juan David Hernandez
     */
    private void cargarListaTipoNotas() {
        try {
            CmtTipoBasicaMgl tipoBasicaNotaOt = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_DE_NOTAS);
            tipoNotaList = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaNotaOt);
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al obtener tipos de notas " + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al obtener tipos de notas: " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que obtiene tipo de tecnología
     *
     * @author Juan David Hernandez
     */
    private void cargarListaTipoTecnologia() {
        try {
            CmtTipoBasicaMgl tipoBasicaTipoTecnologia = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
            tipoTecnologiaBasicaList = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoTecnologia);
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al obtener el tipo de tecnología " + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al obtener el tipo de tecnología: " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que obtiene valor del tipo de tecnologia
     *
     * @author Juan David Hernandez
     */
    public void obtenerValorTipoTecnologia() {
        try {
            tipoTecnologiaBasica
                    = cmtBasicaMglFacadeLocal.findValorTipoSolicitud(solicitudCreated.getTipo());
            //Asignamos el valor de la abreviatura a la variable que se maneja en pantalla
            tipoTecnologia = tipoTecnologiaBasica.getBasicaId().toString();

        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al obtener el valor del tipo de tecnología " + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al obtener el valor del tipo de tecnología: " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que realiza paginación de la tabla. de las unidades asociadas al
     * predio
     *
     * @param page;
     * @author Juan David Hernandez
     */
    public void listInfoByPage(int page) {
        try {
            actual = page;
            getPageActual();
            int firstResult = 0;
            if (page > 1) {
                firstResult = (filasPag * (page - 1));
            }

            //Obtenemos el rango de la paginación
            int maxResult;
            if ((firstResult + filasPag) > unidadAuxiliarList.size()) {
                maxResult = unidadAuxiliarList.size();
            } else {
                maxResult = (firstResult + filasPag);
            }
            copiarInformacionUnidades();
            /*Obtenemos los objetos que se encuentran en el rango de la paginación
             y los guardarmos en la lista que se va a desplegar en pantalla*/
            unidadList = new ArrayList<>();
            for (int i = firstResult; i < maxResult; i++) {
                unidadList.add(unidadAuxiliarList.get(i));
            }
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error en la lista de paginación " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que carga la primera página de resultados de las unidades
     * asociadas al predio
     *
     * @author Juan David Hernandez
     */
    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a la primera página " + e.getMessage(), SOLICITUDES_HHPP);
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
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a la pagina anterior " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que permite ir directamente a la página seleccionada de
     * resultados de las unidades asociadas al predio
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
        } catch (NumberFormatException e) {
            exceptionServBean.notifyError(e, "Error direccionando a página " + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a página: " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que carga la siguiente página de resultados. de las unidades
     * asociadas al predio
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
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a la siguiente página " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que carga la última página de resultados de las unidades
     * asociadas al predio}
     *
     * @author Juan David Hernandez
     */
    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a la última página " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados de las
     * unidades asociadas al predio
     *
     * @return
     * @author Juan David Hernandez
     */
    public int getTotalPaginas() {
        try {
            //obtener la lista original para conocer su tamaño total                 
            int pageSol = unidadAuxiliarList.size();
            return (int) ((pageSol % filasPag != 0)
                    ? (pageSol / filasPag) + 1 : pageSol / filasPag);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a la primera página " + e.getMessage(), SOLICITUDES_HHPP);
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario en
     * los resultados.
     *
     * @return
     * @author Juan David Hernandez
     */
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
     * Comparación de modificaciones realizadas a la lista por pantalla.
     *
     * @author Juan David Hernandez
     */
    public void copiarInformacionUnidades() {
        try {
            if (unidadAuxiliarList != null && !unidadAuxiliarList.isEmpty()
                    && unidadList != null && !unidadList.isEmpty()) {

                for (UnidadStructPcml unidadAux : unidadAuxiliarList) {

                    for (UnidadStructPcml unidad : unidadList) {

                        if (unidadAux.getIdUnidad().equals(unidad.getIdUnidad())) {

                            if (unidad.getTipoNivel5() != null
                                    && !unidad.getTipoNivel5().isEmpty()) {
                                String tipoNivel5 = unidad.getTipoNivel5();
                                unidadAux.setTipoNivel5(tipoNivel5);
                            }

                            if (unidad.getTipoNivel6() != null
                                    && !unidad.getTipoNivel6().isEmpty()) {
                                String tipoNivel6 = unidad.getTipoNivel6();
                                unidadAux.setTipoNivel6(tipoNivel6);
                            }

                            if (unidad.getValorNivel5() != null
                                    && !unidad.getValorNivel5().isEmpty()) {
                                String unidadValorNivel5 = unidad.getValorNivel5();
                                unidadAux.setValorNivel5(unidadValorNivel5);
                            }

                            if (unidad.getValorNivel6() != null
                                    && !unidad.getValorNivel6().isEmpty()) {
                                String unidadValorNivel6 = unidad.getValorNivel6();
                                unidadAux.setValorNivel6(unidadValorNivel6);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error realizando copia de unidades asociadas al predio " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que obtiene los tiempos de la traza de las solicitud
     * selesccionada
     *
     * @author Juan David Hernandez
     */
    public void showSolicitud() {
        showSolicitud = true;
        showTrack = false;
        showNotas = false;
    }

    /**
     * Función que renderiza el Tab de Track de la solicitud
     *
     * @author Juan David Hernandez
     */
    public void showTrack() {
        showSolicitud = false;
        showTrack = true;
        showNotas = false;
    }

    /**
     * Función que renderiza el Tab de Notas de la solicitud
     *
     * @author Juan David Hernandez
     */
    public void showNotas() {
        showSolicitud = false;
        showTrack = false;
        showNotas = true;
    }

    /**
     * Función que renderiza paneles de tipo dirección en la pantalla.
     *
     * @author Juan David Hernandez
     */
    private void hideTipoDireccion() {
        showBMPanel = false;
        showITPanel = false;
        showCKPanel = false;
    }

    /**
     * Función que renderiza panel de tipo dirección calle-carrera en la
     * pantalla
     *
     * @author Juan David Hernandez
     */
    private void showCK() {
        showCKPanel = true;
        showBMPanel = false;
        showITPanel = false;
    }

    /**
     * Función que renderiza panel de tipo dirección barrio-manzana en la
     * pantalla
     *
     * @author Juan David Hernandez
     */
    private void showBM() {
        showBMPanel = true;
        showITPanel = false;
        showCKPanel = false;
    }

    /**
     * Función que renderiza panel de tipo dirección intraducible en la pantalla
     *
     * @author Juan David Hernandez
     */
    private void showIT() {
        showITPanel = true;
        showBMPanel = false;
        showCKPanel = false;
    }

    public void buildDireccion() {
        if (drDireccion == null) {
            createDireccion = "";
            return;
        }
        createDireccion = "";
        createDireccion += drDireccion.getTipoViaPrincipal() != null
                ? !drDireccion.getTipoViaPrincipal().isEmpty() ? drDireccion.getTipoViaPrincipal() + " " : "" : "";
        createDireccion += drDireccion.getNumViaPrincipal() != null
                ? !drDireccion.getNumViaPrincipal().isEmpty() ? " " + drDireccion.getNumViaPrincipal() + " " : "" : "";
        createDireccion += drDireccion.getLtViaPrincipal() != null
                ? !drDireccion.getLtViaPrincipal().isEmpty() ? " " + drDireccion.getLtViaPrincipal() + " " : "" : "";
        createDireccion += drDireccion.getNlPostViaP() != null
                ? !drDireccion.getNlPostViaP().isEmpty() ? " " + drDireccion.getNlPostViaP() + " " : "" : "";
        createDireccion += drDireccion.getBisViaPrincipal() != null
                ? !drDireccion.getBisViaPrincipal().isEmpty() ? " - " + drDireccion.getBisViaPrincipal() + " " : "" : "";
        createDireccion += drDireccion.getCuadViaPrincipal() != null
                ? !drDireccion.getCuadViaPrincipal().isEmpty() ? " " + drDireccion.getCuadViaPrincipal() + " " : "" : "";
        createDireccion += drDireccion.getTipoViaGeneradora() != null
                ? !drDireccion.getTipoViaGeneradora().isEmpty() ? " " + drDireccion.getTipoViaGeneradora() + " " : "" : "";
        createDireccion += drDireccion.getNumViaGeneradora() != null
                ? !drDireccion.getNumViaGeneradora().isEmpty() ? " # " + drDireccion.getNumViaGeneradora() + " " : "" : "";
        createDireccion += drDireccion.getLtViaGeneradora() != null
                ? !drDireccion.getLtViaGeneradora().isEmpty() ? " " + drDireccion.getLtViaGeneradora() + " " : "" : "";
        createDireccion += drDireccion.getNlPostViaG() != null
                ? !drDireccion.getNlPostViaG().isEmpty() ? " - " + drDireccion.getNlPostViaG() + " " : "" : "";
        createDireccion += drDireccion.getLetra3G() != null
                ? !drDireccion.getLetra3G().isEmpty() ? " - " + drDireccion.getLetra3G() + " " : "" : "";

        if (drDireccion.getBisViaGeneradora() != null && !drDireccion.getBisViaGeneradora().isEmpty()
                && drDireccion.getBisViaGeneradora().equalsIgnoreCase("BIS")) {
            createDireccion += drDireccion.getBisViaGeneradora() + " ";
        } else {
            createDireccion += drDireccion.getBisViaGeneradora() != null
                    ? !drDireccion.getBisViaGeneradora().isEmpty() ? " Bis " + drDireccion.getBisViaGeneradora() + " " : "" : "";
        }

        createDireccion += drDireccion.getCuadViaGeneradora() != null
                ? !drDireccion.getCuadViaGeneradora().isEmpty() ? " " + drDireccion.getCuadViaGeneradora() + " " : "" : "";
        createDireccion += drDireccion.getPlacaDireccion() != null
                ? !drDireccion.getPlacaDireccion().isEmpty() ? " " + Integer.parseInt(drDireccion.getPlacaDireccion()) + " " : "" : "";

        if (drDireccion.getPlacaDireccion() != null && !drDireccion.getPlacaDireccion().isEmpty()) {
            int placaSinCeros = Integer.parseInt(drDireccion.getPlacaDireccion());
            drDireccion.setPlacaDireccion(String.valueOf(placaSinCeros));
        }

        direccionConstruida = true;
        direccionLabel = createDireccion;
    }

    public List<ParametrosCalles> optionBisDir(String tipo) throws ApplicationException {
        List<ParametrosCalles> result = new ArrayList<>();
        ParametrosCalles pc = new ParametrosCalles();
        pc.setIdParametro("BIS");
        pc.setDescripcion("BIS");
        pc.setIdTipo("BIS");
        result.add(pc);
        result.addAll(parametrosCallesFacade.findByTipo(tipo));
        return result;
    }

    /**
     * Función que elimina un link de referencia hacia archivos respectivos de
     * la solicitud
     *
     * @param field
     * @author Jonathan Peña
     */
    public void eliminarLink(final String field) {
        linksDocumentos.remove(field);
    }

    /**
     * Función que agrega un link de referencia hacia archivos respectivos de la
     * solicitud
     *
     * @author Jonathan Peña
     */
    public void agregarLink() {
        if (!(url.trim().isEmpty())) {
            linksDocumentos.add(url);
            url = "";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Campo Link No Puede Estar Vacio", ""));
        }
    }

    /**
     * Función que realiza paginación de la tabla.de las unidades asociadas al
     * predio
     *
     * @param pageHhpp
     * @author Juan David Hernandez
     */
    public void listInfoByPageHhpp(int pageHhpp) {
        try {
            actualHhpp = pageHhpp;
            getPageActualHhpp();

            int firstResultHhpp = 0;
            if (pageHhpp > 1) {
                firstResultHhpp = (filasPagHhpp * (pageHhpp - 1));
            }

            //Obtenemos el rango de la paginación
            int maxResult;
            if ((firstResultHhpp + filasPagHhpp) > hhppAuxList.size()) {
                maxResult = hhppAuxList.size();
            } else {
                maxResult = (firstResultHhpp + filasPagHhpp);
            }
            
            /*Obtenemos los objetos que se encuentran en el rango de la paginación
             y los guardarmos en la lista que se va a desplegar en pantalla*/
            hhppList = new ArrayList<>();
            for (int i = firstResultHhpp; i < maxResult; i++) {

                //Conversion de estrato
                if (hhppAuxList.get(i).getSubDireccionObj() != null
                        && hhppAuxList.get(i).getSubDireccionObj().getSdiEstrato() != null) {

                    if (hhppAuxList.get(i).getSubDireccionObj().getSdiEstrato().equals(new BigDecimal(-1))) {
                        hhppAuxList.get(i).setEstratoActual("NG");
                    } else {
                        if (hhppAuxList.get(i).getSubDireccionObj().getSdiEstrato().equals(new BigDecimal(0))) {
                            hhppAuxList.get(i).setEstratoActual("NR");
                        } else {
                            hhppAuxList.get(i).setEstratoActual(hhppAuxList.get(i)
                                    .getSubDireccionObj().getSdiEstrato().toString());
                        }
                    }

                } else {
                    if (hhppAuxList.get(i).getDireccionObj() != null
                            && hhppAuxList.get(i).getDireccionObj().getDirEstrato() != null) {
                        if (hhppAuxList.get(i).getDireccionObj().getDirEstrato().equals(new BigDecimal(-1))) {
                            hhppAuxList.get(i).setEstratoActual("NG");
                        } else {
                            if (hhppAuxList.get(i).getDireccionObj().getDirEstrato().equals(new BigDecimal(0))) {
                                hhppAuxList.get(i).setEstratoActual("NR");
                            } else {
                                hhppAuxList.get(i).setEstratoActual(hhppAuxList.get(i)
                                        .getDireccionObj().getDirEstrato().toString());
                            }
                        }
                    }
                }
                //SAe agrega el objeto a la lista que se va a mostrar en pantalla
                hhppList.add(hhppAuxList.get(i));
            }

        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error en la lista de paginación de direcciones " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que carga la primera página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageFirstHhpp() {
        try {
            listInfoByPageHhpp(1);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a la primera página " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     *
     * @author Juan David Hernandez
     */
    public void pagePreviousHhpp() {
        try {
            int totalPaginasHhpp = getTotalPaginasHhpp();
            int nuevaPageActualHhpp = actualHhpp - 1;
            if (nuevaPageActualHhpp > totalPaginasHhpp) {
                nuevaPageActualHhpp = totalPaginasHhpp;
            }
            if (nuevaPageActualHhpp <= 0) {
                nuevaPageActualHhpp = 1;
            }
            listInfoByPageHhpp(nuevaPageActualHhpp);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a la página anterior " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que cpermite ir directamente a la página seleccionada
     * de resultados.
     *
     * @author Juan David Hernandez
     */
    public void irPaginaHhpp() {
        try {
            int totalPaginasHhpp = getTotalPaginasHhpp();
            int nuevaPageActualHhpp = Integer.parseInt(numPaginaHhpp);
            if (nuevaPageActualHhpp > totalPaginasHhpp) {
                nuevaPageActualHhpp = totalPaginasHhpp;
            }
            listInfoByPageHhpp(nuevaPageActualHhpp);
        } catch (NumberFormatException e) {
            exceptionServBean.notifyError(e, "Error direccionando a página " + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a página: " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     *
     * @author Juan David Hernandez
     */
    public void pageNextHhpp() {
        try {
            int totalPaginasHhpp = getTotalPaginasHhpp();
            int nuevaPageActualHhpp = actualHhpp + 1;
            if (nuevaPageActualHhpp > totalPaginasHhpp) {
                nuevaPageActualHhpp = totalPaginasHhpp;
            }
            listInfoByPageHhpp(nuevaPageActualHhpp);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a la siguiente página " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que carga la última página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageLastHhppHhpp() {
        try {
            int totalPaginasHhpp = getTotalPaginasHhpp();
            listInfoByPageHhpp(totalPaginasHhpp);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a la última página " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     *
     * @return
     * @author Juan David Hernandez
     */
    public int getTotalPaginasHhpp() {
        try {
            int totalPaginasHhpp;
            int pageSolHhpp = hhppAuxList.size();
            totalPaginasHhpp = (int) ((pageSolHhpp % filasPagHhpp != 0) ?
                    (pageSolHhpp / filasPagHhpp) : (pageSolHhpp / filasPagHhpp) + 1);
            return totalPaginasHhpp;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a la primera página " + e.getMessage(), SOLICITUDES_HHPP);
        }
        return 1;
    }

    /**
     * Función que permite conocer la página actual en la que se encuentra el usuario
     * en los resultados.
     *
     * @return
     * @author Juan David Hernandez
     */
    public String getPageActualHhpp() {
        paginaListHhpp = new ArrayList<>();
        int totalPaginasHhpp = getTotalPaginasHhpp();
        for (int i = 1; i <= totalPaginasHhpp; i++) {
            paginaListHhpp.add(i);
        }
        pageActualHhpp = String.valueOf(actualHhpp) + " de "
                + String.valueOf(totalPaginasHhpp);

        if (numPaginaHhpp == null) {
            numPaginaHhpp = "1";
        }
        numPaginaHhpp = String.valueOf(actualHhpp);

        return pageActualHhpp;
    }


    public String guardarArchivo() throws IOException {

        Date fecha = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String fechaN = format.format(fecha);


        if (lstFilesCambiosEstrato.size() > 3) {
            String msg = "Solo se pueden subir un maximo de 4 archivos";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
        } else {
            if (fileCambioEstrato != null && fileCambioEstrato.getFileName() != null) {

                String fileName = fechaN;
                fileName += FilenameUtils.getName(fileCambioEstrato.getFileName());
                File file = new File(System.getProperty("user.dir"));
                String extension = FilenameUtils.getExtension(fileCambioEstrato.getFileName());
                if (extension == null || extension.equals("")) {
                    String msg = "El archivo que ud esta cargando no extension, solamente se pueden cargar archivos con extension";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                    return "";
                }
                File archive = File.createTempFile(fileName + "-", "." + extension, file);
                FileOutputStream output = new FileOutputStream(archive);
                output.write(fileCambioEstrato.getContent());
                output.close();
                lstFilesCambiosEstrato.add(archive);
            } else {
                String msg = "Debe seleccionar un archivo para guardar";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msg, ""));
            }
        }
        return "";
    }

    /**
     * Método creado para guardar los archivos para el Escalamiento HHPP
     *
     * @throws IOException excepción de entrada y salida
     * @author José René Miranda de la O  - ALGARTECH
     */
    public void guardarArchivoEscalamientoHHPP() throws IOException {
        Date fecha = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String fechaN = format.format(fecha);

        if (lstFilesEscalamientoHHPP.size() >= 10) {
            String msg = "Solo se pueden subir un máximo de 10 archivos";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, msg);
            return;
        }

        if (fileEscalamientoHHPP == null || StringUtils.isEmpty(fileEscalamientoHHPP.getFileName())) {
            String msg = "Debe seleccionar un archivo para guardar";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, msg);
            return;
        }

        long kb = fileEscalamientoHHPP.getSize() / 1024;
        if (kb > 512) {
            String msg = "El archivo que ud esta cargando, es mayor al permitido, debe ser menor o igual a 512 kb";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, msg);
            fileEscalamientoHHPP = null;
            return;
        }

        String fileName = fechaN;
        fileName += FilenameUtils.getName(fileEscalamientoHHPP.getFileName());
        File file = new File(System.getProperty("user.dir"));
        String extension = FilenameUtils.getExtension(fileEscalamientoHHPP.getFileName());
        if (StringUtils.isBlank(extension)) {
            String msg = "El archivo que ud esta cargando no cuenta con extensión, solamente se pueden cargar archivos con extensión";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, msg);
            return;
        }

        File archive = File.createTempFile(fileName + "-", "." + extension, file);

        try (FileOutputStream output = new FileOutputStream(archive);){
            output.write(fileEscalamientoHHPP.getContent());
        } catch (IOException e) {
            LOGGER.error(e);
        }

        lstFilesEscalamientoHHPP.add(archive);
    }

    public String eliminarArchivo(File archivo) {
        if (lstFilesCambiosEstrato.remove(archivo)) {
            FileToolUtils.deleteFile(archivo);
            String msg = "Archivo borrado satisfatoriamente";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.INFO, msg);
        }

        return "";
    }

    /**
     * Método creado para eliminar un archivo de Escalamiento HHPP
     *
     * @param archivo es el archivo que se va a eliminar
     * @return mensaje de borrado satisfactorio
     */
    public String eliminarArchivoEscalamientoHHPP(File archivo) {
        if (lstFilesEscalamientoHHPP.remove(archivo)) {
            FileToolUtils.deleteFile(archivo);
            String msg = "Archivo borrado satisfatoriamente";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.INFO, msg);
        }

        return "";
    }

    public String nombreArchivo(File archive) {

        String name = archive.getName();
        String[] partsUrls = name.split("-");
        name = partsUrls[0];

        return name;
    }

    /**
     * Función utilizada validar si la tecnologia requiere soporte.
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Victor Bocanegra
     */
    public boolean soporteRequeridoTecnologia()
            throws ApplicationException {

        CmtBasicaMgl tipoTecnoBasica = obtenerValorTecnologiaBasica(tipoTecnologia);

        CmtExtendidaTecnologiaMgl extendidaTecnologia = cmtExtendidaTecnologiaMglFacadeLocal
                .findBytipoTecnologiaObj(tipoTecnoBasica);

        if (solicitud.getTipoSol() != null && !solicitud.getTipoSol().isEmpty()
                && (solicitud.getTipoSol().equalsIgnoreCase("PYMES")
                || solicitud.getTipoSol().equalsIgnoreCase("TRASLADOS")
                || solicitud.getTipoSol().equalsIgnoreCase("CORTESIA")
                || solicitud.getTipoSol().equalsIgnoreCase("FUENTE DE PODER"))) {
            return false;
        }

        return extendidaTecnologia != null
                && extendidaTecnologia.getReqSoporte() == 1;

    }

    // Validar Opciones por Rol    
    public boolean validarOpcionValidarDir() {
        return validarEdicionRol(BTCRESOLVALDIR);
    }

    //funcion General
    private boolean validarEdicionRol(String formulario) {
        try {
            return ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al subtipoOtVtTecBean. " + e.getMessage(), SOLICITUDES_HHPP);
        }
        return false;
    }

    /**
     * Crea la solicitud de escalamiento HHPP
     */
    private void crearSolicitudEscalamientoHHPP() {
        if (!validarCrearSolicitudEscalamientos()) {
            LOGGER.debug("Error al validar campos para la creación de la solicitud");
            return;
        }

        ResponseValidacionDireccion responseValidacionDireccion = validarDireccionEscalamiento();
        boolean isValid = Optional.ofNullable(responseValidacionDireccion)
                .map(ResponseValidacionDireccion::getResponseMesaje)
                .map(ResponseMesaje::getTipoRespuesta)
                .filter(tipoRespuesta -> StringUtils.isNotBlank(tipoRespuesta)
                        && tipoRespuesta.equalsIgnoreCase("I"))
                .isPresent();

        if (!isValid) {
            String msg = Optional.ofNullable(responseValidacionDireccion)
                    .map(ResponseValidacionDireccion::getResponseMesaje)
                    .map(ResponseMesaje::getMensaje)
                    .orElse("No se pudo validar la dirección para la creación de la solicitud, intente nuevamente.");
            
            if (!msg.contains("{multiorigen=1}")) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, msg);
            }

            LOGGER.warn("Error al validar dirección para la creación de la solicitud {}", msg);
            return;
        }

        final RequestCreaSolicitudEscalamientoHHPP requestEscalamiento = new RequestCreaSolicitudEscalamientoHHPP();
        requestEscalamiento.setTipoSolicitud(tipoAccionSolicitud);
        requestEscalamiento.setDepartamento(departamento);
        requestEscalamiento.setCiudad(ciudad);
        requestEscalamiento.setCentroPoblado(idCentroPoblado);
        requestEscalamiento.setArea(solicitud.getTipoSol());
        requestEscalamiento.setTipoTecnologia(tipoTecnologia);
        requestEscalamiento.setTipoDireccion(tipoDireccion);
        if (StringUtils.isNotBlank(direccionCk)) {
            requestEscalamiento.setDireccion(notGeoReferenciado ? direccionCk : direccionLabel);
        } else {
            requestEscalamiento.setDireccion(direccionLabel);
        }
        requestEscalamiento.setComplemento(complementoCreate);
        requestEscalamiento.setApartamento(apartamento);
        asignarTiempoSolicitudEscalamiento();
        requestEscalamiento.setFechaInicioSolicitud(timeSol);
        requestEscalamiento.setCcmm(ccmm);
        requestEscalamiento.setSolicitudOT(solicitudOT);
        requestEscalamiento.setObservaciones(solicitud.getMotivo());
        requestEscalamiento.setSolicitante(solicitud.getSolicitante());
        requestEscalamiento.setTelefono(solicitud.getTelSolicitante());
        requestEscalamiento.setCorreo(solicitud.getCorreo());
        requestEscalamiento.setAdjuntos(lstFilesEscalamientoHHPP);
        requestEscalamiento.setIdUsuario(request.getIdUsuario());
        requestEscalamiento.setUsuarioVT(usuarioVT);
        requestEscalamiento.setPerfillVT(perfilVt);
        requestEscalamiento.setTipoGestion(tipoGestion);
        requestEscalamiento.setTipoHHPP(tipoHHPP);
        requestEscalamiento.setDrDireccion(request.getDrDireccion());
        requestEscalamiento.setCmtBasicaMgl(tipoTecnologiaBasica);

        try {
            requestEscalamiento.setTipoSolicitudStr(obtenerTipoSolicitudStr(tipoAccionSolicitud));
            ResponseCreaSolicitud responseCrearSolicitud
                    = solicitudFacadeLocal.crearSolicitudEscalamientosHHPP(requestEscalamiento);
            
            if (StringUtils.isNotBlank(responseCrearSolicitud.getTipoRespuesta())
                    && responseCrearSolicitud.getMensaje().contains(MESSAGE_ERROR_BARRIO_OBLIGATORIO)) {
                barrioSugeridoPanel = true;
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, responseCrearSolicitud.getMensaje());
                return;
            }

            if (StringUtils.isBlank(responseCrearSolicitud.getTipoRespuesta()) ||
                    !responseCrearSolicitud.getTipoRespuesta().equalsIgnoreCase("I")) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, responseCrearSolicitud.getMensaje());
                return;
            }

            solicitudCreated.setIdSolicitud(new BigDecimal(responseCrearSolicitud.getIdSolicitud()));
            procesarCargueArchivosEscalamiento(responseCrearSolicitud);
            crearSolicitudButton = true;
            deshabilitarCreacion = true;
            fechaFin = new Date();
            tiempoFinalSolicitud = new Date();
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.INFO, responseCrearSolicitud.getMensaje());
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al crearSolicitudEscalamientoHHPP. " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    private void asignarTiempoSolicitudEscalamiento() {
        if (StringUtils.isBlank(timeSol) || timeSol.equals("00:00:00")) {
            final Date now = new Date();
            final long l = now.getTime() - startCreteRequest.getTime();
            final long day = l / (24 * 60 * 60 * 1000);
            final long hour = (l / (60 * 60 * 1000) - day * 24);
            final long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            final long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            timeSol = hour + ":" + min + ":" + s;
        }
    }

    private void procesarCargueArchivosEscalamiento(ResponseCreaSolicitud responseCrearSolicitud) {
        if (CollectionUtils.isEmpty(lstFilesEscalamientoHHPP)) {
            LOGGER.debug("No hay archivos para cargar en la solicitud de escalamiento HHPP");
            return;
        }

        try {
            cargarArchivosSolicitudEscalamiento(responseCrearSolicitud);
            tecArchivosEscalamientosHHPP = tecArcEscalaHHPPFacadeLocal.
                    findUrlsByIdSolicitudAndOrigenAndObservacion(solicitudCreated, CREACION, null);
        } catch (Exception e) {
            crearSolicitudButton = true;
            deshabilitarCreacion = true;
            fechaFin = new Date();
            tiempoFinalSolicitud = new Date();
            exceptionServBean.notifyError(e, "Error al cargar archivos adjuntos de la solicitud de escalamiento HHPP. "
                    + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    private ResponseValidacionDireccion validarDireccionEscalamiento() {
          /*Consume servicio que valida la correcta construcción de la dirección. */
        CityEntity cityEntity;
        try {
            request.getDrDireccion().setIdSolicitud(new BigDecimal(tipoAccionSolicitud));
            cityEntity = direccionesValidacionFacadeLocal.validaDireccion(request, ambiguaNueva);
            responseValidarDireccion = new ResponseValidacionDireccion();
            ResponseMesaje responseMesaje = new ResponseMesaje();
            responseMesaje.setMensaje("Proceso Exitoso");
            responseMesaje.setTipoRespuesta("I");

            if (StringUtils.isBlank(request.getDrDireccion().getBarrio())) {
                request.getDrDireccion().setBarrio(cityEntity.getBarrio());
            }

            responseValidarDireccion.setResponseMesaje(responseMesaje);
            request.getDrDireccion().convertToDetalleDireccionEntity().
                    setMultiOrigen(cityEntity.getActualDetalleDireccionEntity().getMultiOrigen());
            responseValidarDireccion.setDrDireccion(request.getDrDireccion());
            responseValidarDireccion.setCityEntity(cityEntity);
            return responseValidarDireccion;
        } catch (Exception e) {
            String msg = Optional.of(e)
                    .map(Throwable::getMessage)
                    .orElse("Error al validar la dirección de escalamiento HHPP");
            responseValidarDireccion = new ResponseValidacionDireccion();
            ResponseMesaje responseMesaje = new ResponseMesaje();
            responseMesaje.setMensaje("Ocurrio Un Error Validando la Dirección " + msg);
            responseMesaje.setTipoRespuesta("E");
            responseValidarDireccion.setResponseMesaje(responseMesaje);
            responseValidarDireccion.setDrDireccion(request.getDrDireccion());

            if (!msg.contains("{multiorigen=1}")) {
                responseMesaje = new ResponseMesaje();
                responseMesaje.setMensaje(msg);
                responseMesaje.setTipoRespuesta("E");
                return responseValidarDireccion;
            }

            validarBarrioCiudadMultiOrigen();
            return responseValidarDireccion;
        }
    }

    /**
     * Método que permite cargar los archivos de la solicitud de escalamiento HHPP
     * @param responseCrearSolicitud Respuesta de la creación de la solicitud
     * @throws Exception Error al cargar los archivos
     */
    private void cargarArchivosSolicitudEscalamiento(ResponseCreaSolicitud responseCrearSolicitud) throws Exception {
        try {
            for (File archivo : lstFilesEscalamientoHHPP) {
                fileDelete = archivo;
                String nombreArchivoFinal = obtenerNombreArchivoFinalEscalamiento(responseCrearSolicitud, archivo);
                String responseArc = solicitudFacadeLocal.uploadArchivoEscalamientoHHPP(archivo, nombreArchivoFinal);

                if (StringUtils.isBlank(responseArc)) {
                    String msg = "Ocurrio un error al subir el archivo: " + nombreArchivoFinal + "\n";
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, msg);
                } else {
                    TecArchivosEscalamientosHHPP archivoEscalamiento = new TecArchivosEscalamientosHHPP();
                    archivoEscalamiento.setUrlArchivoSoporte(responseArc);
                    archivoEscalamiento.setSolicitudObj(solicitudCreated);
                    archivoEscalamiento.setNombreArchivo(nombreArchivoFinal);
                    archivoEscalamiento.setOrigen(CREACION);
                    tecArcEscalaHHPPFacadeLocal.crear(archivoEscalamiento, usuarioVT, perfilVt);
                }
                FileToolUtils.deleteFile(archivo);
            }

        } catch (ConnectException ce) {
            String msg = "Error de conexión con el servicio de cargue de archivos\n"
                    + " en la solicitud de escalamiento HHPP";
            LOGGER.error(msg, ce);
            throw new ApplicationException(msg);
        } catch (Exception e) {
            String msg = "Ocurrio un error al cargar archivo adjunto\n"
                    + " en la solicitud de escalamiento HHPP";
            LOGGER.error(msg, e);
            throw new ApplicationException(msg);
        }
    }

    /**
     * Método que permite obtener el nombre del archivo final de la solicitud de escalamiento
     * @param responseCrearSolicitud Respuesta de la creación de la solicitud
     * @param archivo Archivo a subir
     * @return Nombre del archivo final
     */
    private String obtenerNombreArchivoFinalEscalamiento(ResponseCreaSolicitud responseCrearSolicitud, File archivo) {
        String[] partsUrls = archivo.getName().split("-");
        String nombreArchivo = partsUrls[0];
        String extension = FilenameUtils.getExtension(archivo.getName());
        String nombreArchivoFinal = null;
        if (nombreArchivo.contains(extension)) {
            nombreArchivoFinal = responseCrearSolicitud.getIdSolicitud() + "_" + nombreArchivo;
        } else {
            nombreArchivoFinal = responseCrearSolicitud.getIdSolicitud() + "_" + nombreArchivo + "." + extension;
        }
        return nombreArchivoFinal;
    }

    private boolean validarCrearSolicitudEscalamientos() {
        try {
            if (isInvalidCamposObligatoriosEscalamiento()) return false;

            boolean validacion;
            switch (tipoDireccion) {
                case "CK" :
                    validacion = validarDireccionCk(direccionCk);
                    break;
                case "BM" :
                    validacion = validarDireccionBm();
                    break;
                case "IT":
                  validacion = validarDireccionIt();
                  break;
                default:
                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "Seleccione un Tipo Dirección válido por favor.");
                    validacion = false;
            }

            if (!validacion) return false;

            if (StringUtils.isBlank(tipoGestion)) {
                String msn = "Seleccione Tipo Gestión por favor.";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
                return false;
            }
            if (StringUtils.isBlank(solicitud.getTelSolicitante())) {
                String msn = "Ingrese Teléfono por favor.";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
                return false;
            }
            if (StringUtils.isBlank(solicitud.getMotivo())) {
                String msn = "Ingrese Observaciones  por favor.";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
                return false;
            }
            return true;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al validar campos al validar dirección para la creación de la solcitud "
                    + e.getMessage(), SOLICITUDES_HHPP);
            return false;
        }
    }

    /**
     * Método que permite validar el campo de la dirección CK
     * @param direccionCk Dirección CK
     * @return Retorna true si la dirección CK está diligenciada
     */
    private boolean validarDireccionCk(String direccionCk) {
        if (StringUtils.isBlank(direccionCk)) {
            String msn = "Ingrese Dirección por favor.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return false;
        }

        return true;
    }

    /**
     * Método que permite validar los campos de la dirección BM
     * @return Retorna true si los campos de la dirección BM están diligenciados
     */
    private boolean validarDireccionBm() {
        if (!bmExitoso) {
            if (StringUtils.isBlank(tipoNivelBm)) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "Seleccione Dirección por favor.");
                return false;
            }

            if (StringUtils.isBlank(nivelValorBm)) {
                String dato = bmList.stream()
                        .filter(bm -> bm.getIdParametro().equals(tipoNivelBm))
                        .map(OpcionIdNombre::getDescripcion)
                        .findFirst()
                        .orElse("");
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "Ingrese descripción de " + dato + " por favor.");
                return false;
            }
        }

        return true;
    }

    /**
     * Método que permite validar los campos de la dirección IT
     * @return Retorna true si los campos de la dirección IT están diligenciados
     */
    private boolean validarDireccionIt() {
        if (!itExitoso) {
            if (StringUtils.isBlank(tipoNivelIt)) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "Seleccione Dirección por favor.");
                return false;
            }
            if (StringUtils.isBlank(nivelValorIt)) {
                String dato = itList.stream()
                        .filter(it -> it.getIdParametro().equals(tipoNivelIt))
                        .map(OpcionIdNombre::getDescripcion)
                        .findFirst()
                        .orElse("");
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "Ingrese descripción de " + dato + " por favor. ");
                return false;
            }
        }
        return true;
    }

    /**
     * Método que permite validar los campos obligatorios para el escalamiento
     * @return Retorna true si los campos obligatorios no están diligenciados
     */
    private boolean isInvalidCamposObligatoriosEscalamiento() {
        if (StringUtils.isBlank(departamento)) {
            String msn = "Seleccione Departamento por favor.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return true;
        }
        if (ciudad == null || BigDecimal.ZERO.equals(ciudad)) {
            String msn = "Seleccione Ciudad por favor.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return true;
        }
        if (idCentroPoblado == null || BigDecimal.ZERO.equals(idCentroPoblado)) {
            String msn = "Seleccione Centro Poblado por favor.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return true;
        }
        if (StringUtils.isBlank(tipoTecnologia)) {
            String msn = "Seleccione Tipo Tecnología por favor.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return true;
        }
        if (StringUtils.isBlank(solicitud.getTipoSol())) {
            String msn = "Seleccione Área por favor.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return true;
        }

        if (StringUtils.isBlank(tipoHHPP)) {
            String msn = "Seleccione Tipo por favor.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return true;
        }
        if (StringUtils.isBlank(tipoDireccion)) {
            String msn = "Seleccione Tipo Dirección por favor.";
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
            return true;
        }
        return false;
    }

    public void consultarTipoGestion() {
        try {
            final FiltroConjsultaBasicasDto filtroConjsultaBasicasDto = new FiltroConjsultaBasicasDto();
            filtroConjsultaBasicasDto.setIdTipoBasica(new BigDecimal("141236"));
            tablasBasicasMglList = cmtBasicaMglFacade.findByFiltro(filtroConjsultaBasicasDto);
        } catch (ApplicationException e) {
            String msn = "Se presenta error al filtrar los registros: " + e.getMessage();
            exceptionServBean.notifyError(e, msn + e.getMessage(), SOLICITUDES_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al consultar el tipo de gestión " + e.getMessage(), SOLICITUDES_HHPP);
        }
    }

    public String obtenerTipoSolicitudStr(final String tipoAccionSolicitud) throws ApplicationException {
        String tipoSol = null;
        try {
            if (tipoAccionSolicitud != null && tipoAccionSolicitudBasicaList != null) {
                for (CmtBasicaMgl tipoAccion : tipoAccionSolicitudBasicaList) {
                    if (tipoAccionSolicitud.equalsIgnoreCase(tipoAccion.getCodigoBasica())) {
                        tipoSol = tipoAccion.getNombreBasica();
                        break;
                    }
                }
            }
        } catch (RuntimeException e) {
            LOGGER.error("Error en obtenerTipoSolicitud, estableciendo el tipo de solicitud " + e.getMessage());
            throw new ApplicationException("Error en obtenerTipoSolicitud, estableciendo el tipo de solicitud: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en obtenerTipoSolicitud, estableciendo el tipo de solicitud " + e.getMessage());
            throw new ApplicationException("Error en obtenerTipoSolicitud, estableciendo el tipo de solicitud: " + e.getMessage(), e);
        }
        return tipoSol;
    }

        /* Brief 57762 */

    /**
     * Determina si se cumplen las condiciones para crear una solicitud de traslado de HHPP Bloqueado
     *
     * @return Retorna true, cuando se cumplen todas las validaciones para Traslado de HHPP
     * @throws ApplicationException Excepción de la App
     */
    public boolean validarSolicitudTrasladoHhppBloqueado(CityEntity cityEntity) throws ApplicationException {
        String msgValidationHhpp;

        try {
            Map<String, Object> existeDirHhppMap = existeDireccionHhppEnMer(responseConstruirDireccion, cityEntity);
            boolean direccionExisteEnMer = (boolean) existeDirHhppMap.getOrDefault(ConstantsSolicitudHhpp.EXISTE_HHPP, false);
            HhppMgl hhppTraslado = (HhppMgl) existeDirHhppMap.getOrDefault(ConstantsSolicitudHhpp.HHPP_TRASLADO, null);
            hhppReal = hhppTraslado;

            if (!direccionExisteEnMer) {
                msgValidationHhpp = "El HHPP no existe, debe solicitar la creación de HHPP";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msgValidationHhpp);
                return false;
            }
            //Asigna datos de la petición al servicio
            Optional<RequestDataValidaRazonesCreaHhppVt> requestValidateReasonsRs = hhppVirtualMglFacadeLocal
                    .generateRequestForServiceValidate(hhppTraslado, numCuentaClienteTraslado);

            if (!requestValidateReasonsRs.isPresent()){
                msgValidationHhpp = "No se pudo realizar la petición al servicio, intente mas tarde.";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msgValidationHhpp);
                return false;
            }

            //Ejecuta y obtiene respuesta del servicio
            Optional<ResponseDataValidaRazonesCreaHhppVt> responseValidateReasonsRs = hhppVirtualMglFacadeLocal
                    .callServiceValidateMoveReasonsResource(requestValidateReasonsRs.get());

            if (!responseValidateReasonsRs.isPresent()) {
                msgValidationHhpp = "No hubo respuesta del servicio validateMoveReasonsResource, intente mas tarde.";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, msgValidationHhpp);
                return false;
            }

            //realiza las validaciones para el traslado de HHPP
            Map<String, Object> validateResult = hhppVirtualMglFacadeLocal
                    .validateTrasladoHhppBloqueado(responseValidateReasonsRs.get(), numCuentaClienteTraslado);

            if (!(boolean) validateResult.getOrDefault("esValido", false)) {
                msgValidationHhpp = String.valueOf(validateResult.get("msg"));
                MessageSeverityEnum severityType = (MessageSeverityEnum) validateResult.getOrDefault("tipoMsg", MessageSeverityEnum.INFO);
                FacesUtil.mostrarMensajePopUp(severityType, msgValidationHhpp);
                return false;
            }

            return true;

        } catch (Exception e) {
            String msgError = "Excp al validar solicitud de traslado: " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e.getMessage());
            throw new ApplicationException(e);
        }
    }

    /**
     * Verifica si existe la dirección del HHPP en MER
     *
     * @param responseConstruirDireccion {@link ResponseConstruccionDireccion}
     * @param cityEntity {@link CityEntity}
     * @return {Map<String,Object>} existeHHppMap
     * @throws ApplicationException Excepción de la App
     * @author Gildardo Mora
     */
    private Map<String, Object> existeDireccionHhppEnMer(ResponseConstruccionDireccion responseConstruirDireccion, CityEntity cityEntity)
            throws ApplicationException {

        Map<String, Object> existeHHppMap = new HashMap<>();

        try {
            Objects.requireNonNull(cityEntity, "No se obtuvo información de cityEntity");
            boolean unidadExiste = validaExistenciaUnidad(cityEntity, Constant.TRASLADO_HHPP_BLOQUEADO_5);
            if (unidadExiste && solicitud.getHhppMgl() != null) {
                existeHHppMap.put(ConstantsSolicitudHhpp.EXISTE_HHPP, true);
                existeHHppMap.put(ConstantsSolicitudHhpp.HHPP_TRASLADO, solicitud.getHhppMgl());
                return existeHHppMap;
            }

            if (Objects.isNull(responseConstruirDireccion) || Objects.isNull(responseConstruirDireccion.getDrDireccion())) {
                String msgError = "No se obtuvo información de responseConstruirDireccion";
                LOGGER.error(msgError);
              throw new ApplicationException(msgError);
            }

            List<HhppMgl> hhppMglList = cmtDireccionDetalleMglFacadeLocal.
                    findHhppByDireccion(responseConstruirDireccion.getDrDireccion(), idCentroPoblado,
                            true, 0, filasPagHhpp, false);

            if (hhppMglList.isEmpty()) {
                List<CmtDireccionDetalladaMgl> direccionDetalladaList = new ArrayList<>();
                String dirRespuestaGeo = Optional.of(responseConstruirDireccion).map(ResponseConstruccionDireccion::getDireccionRespuestaGeo).orElse(null);

                if (StringUtils.isNotBlank(dirRespuestaGeo)) {
                    //realiza búsqueda por texto.
                    direccionDetalladaList = cmtDireccionDetalleMglFacadeLocal
                            .busquedaDireccionTextoRespuestaGeo(dirRespuestaGeo, responseConstruirDireccion.getDrDireccion(), idCentroPoblado);
                }

                //si se encontraron direcciones detalladas por texto, se buscan hhpp de esas direcciones
                if (CollectionUtils.isNotEmpty(direccionDetalladaList)) {
                    hhppMglList = hhppMglFacadeLocal.obtenerHhppByDireccionDetallaList(direccionDetalladaList);
                }
            }

            HhppMgl hhppTraslado = new HhppMgl();

            if (CollectionUtils.isNotEmpty(hhppMglList)) {
                hhppTraslado = hhppMglList.stream()
                        .filter(hhpp -> (
                                        hhpp.getDireccionDetalladaHhpp() + StringUtils.SPACE
                                                + hhpp.getBarrioHhpp()).equals(responseValidarDireccion.getCityEntity().getDireccion())
                                        || hhpp.getHhpComunidad().equals(centroPobladoSeleccionado.getGpoCodigo())
                                        || (hhpp.getDireccionDetalladaHhpp().equals(responseValidarDireccion.getCityEntity().getDireccion())
                                        && hhpp.getBarrioHhpp().equals(responseValidarDireccion.getCityEntity().getBarrio())
                                        && !hhpp.getHhpEstadoUnit().equalsIgnoreCase("DE")
                                )
                        )
                        .findFirst().orElse(null);
            }

            if (Objects.nonNull(Optional.ofNullable(hhppTraslado).map(HhppMgl::getHhpDivision).orElse(null))) {
                existeHHppMap.put(ConstantsSolicitudHhpp.EXISTE_HHPP, true);
                existeHHppMap.put(ConstantsSolicitudHhpp.HHPP_TRASLADO, hhppTraslado);
                return existeHHppMap;
            }

            existeHHppMap.put(ConstantsSolicitudHhpp.EXISTE_HHPP, false);
            return existeHHppMap;

        } catch (Exception e) {
            LOGGER.error(e);
            throw new ApplicationException(e);
        }
    }

        /* Cierre Brief 57762 */


    /* ------------------- Getters and Setters  ------------------- */

    /**
     * get field
     *
     * @return tiempoFinalSolicitud
     */
    public Date getTiempoFinalSolicitud() {
        return this.tiempoFinalSolicitud;
    }

    /**
     * set field
     *
     * @param tiempoFinalSolicitud
     */
    public void setTiempoFinalSolicitud(Date tiempoFinalSolicitud) {
        this.tiempoFinalSolicitud = tiempoFinalSolicitud;
    }

    /**
     * get field
     *
     * @return dateInicioCreacion
     */
    public Date getDateInicioCreacion() {
        return this.dateInicioCreacion;
    }

    /**
     * set field
     *
     * @param dateInicioCreacion
     */
    public void setDateInicioCreacion(Date dateInicioCreacion) {
        this.dateInicioCreacion = dateInicioCreacion;
    }

    /**
     * get field
     *
     * @return usuarioVT
     */
    public String getUsuarioVT() {
        return this.usuarioVT;
    }

    /**
     * set field
     *
     * @param usuarioVT
     */
    public void setUsuarioVT(String usuarioVT) {
        this.usuarioVT = usuarioVT;
    }

    /**
     * get field
     *
     * @return cedulaUsuarioVT
     */
    public String getCedulaUsuarioVT() {
        return this.cedulaUsuarioVT;
    }

    /**
     * set field
     *
     * @param cedulaUsuarioVT
     */
    public void setCedulaUsuarioVT(String cedulaUsuarioVT) {
        this.cedulaUsuarioVT = cedulaUsuarioVT;
    }

    /**
     * get field
     *
     * @return usuarioLogin
     */
    public UsuariosServicesDTO getUsuarioLogin() {
        return this.usuarioLogin;
    }

    /**
     * set field
     *
     * @param usuarioLogin
     */
    public void setUsuarioLogin(UsuariosServicesDTO usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    /**
     * get field
     *
     * @return solicitud
     */
    public Solicitud getSolicitud() {
        return this.solicitud;
    }

    /**
     * set field
     *
     * @param solicitud
     */
    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    /**
     * get field
     *
     * @return solicitudCreated
     */
    public Solicitud getSolicitudCreated() {
        return this.solicitudCreated;
    }

    /**
     * set field
     *
     * @param solicitudCreated
     */
    public void setSolicitudCreated(Solicitud solicitudCreated) {
        this.solicitudCreated = solicitudCreated;
    }

    /**
     * get field
     *
     * @return tipoNotaSelected
     */
    public CmtBasicaMgl getTipoNotaSelected() {
        return this.tipoNotaSelected;
    }

    /**
     * set field
     *
     * @param tipoNotaSelected
     */
    public void setTipoNotaSelected(CmtBasicaMgl tipoNotaSelected) {
        this.tipoNotaSelected = tipoNotaSelected;
    }

    /**
     * get field
     *
     * @return tipoTecnologiaBasica
     */
    public CmtBasicaMgl getTipoTecnologiaBasica() {
        return this.tipoTecnologiaBasica;
    }

    /**
     * set field
     *
     * @param tipoTecnologiaBasica
     */
    public void setTipoTecnologiaBasica(CmtBasicaMgl tipoTecnologiaBasica) {
        this.tipoTecnologiaBasica = tipoTecnologiaBasica;
    }

    /**
     * get field
     *
     * @return cmtNotasSolicitudVtMgl
     */
    public CmtNotasSolicitudVtMgl getCmtNotasSolicitudVtMgl() {
        return this.cmtNotasSolicitudVtMgl;
    }

    /**
     * set field
     *
     * @param cmtNotasSolicitudVtMgl
     */
    public void setCmtNotasSolicitudVtMgl(CmtNotasSolicitudVtMgl cmtNotasSolicitudVtMgl) {
        this.cmtNotasSolicitudVtMgl = cmtNotasSolicitudVtMgl;
    }

    /**
     * get field
     *
     * @return cmtTiempoSolicitudMglToCreate
     */
    public CmtTiempoSolicitudMgl getCmtTiempoSolicitudMglToCreate() {
        return this.cmtTiempoSolicitudMglToCreate;
    }

    /**
     * set field
     *
     * @param cmtTiempoSolicitudMglToCreate
     */
    public void setCmtTiempoSolicitudMglToCreate(CmtTiempoSolicitudMgl cmtTiempoSolicitudMglToCreate) {
        this.cmtTiempoSolicitudMglToCreate = cmtTiempoSolicitudMglToCreate;
    }

    /**
     * get field
     *
     * @return cmtTipoSolicitudMgl
     */
    public CmtTipoSolicitudMgl getCmtTipoSolicitudMgl() {
        return this.cmtTipoSolicitudMgl;
    }

    /**
     * set field
     *
     * @param cmtTipoSolicitudMgl
     */
    public void setCmtTipoSolicitudMgl(CmtTipoSolicitudMgl cmtTipoSolicitudMgl) {
        this.cmtTipoSolicitudMgl = cmtTipoSolicitudMgl;
    }

    /**
     * get field
     *
     * @return centroPobladoSeleccionado
     */
    public GeograficoPoliticoMgl getCentroPobladoSeleccionado() {
        return this.centroPobladoSeleccionado;
    }

    /**
     * set field
     *
     * @param centroPobladoSeleccionado
     */
    public void setCentroPobladoSeleccionado(GeograficoPoliticoMgl centroPobladoSeleccionado) {
        this.centroPobladoSeleccionado = centroPobladoSeleccionado;
    }

    /**
     * get field
     *
     * @return responseConstruirApartamentoDireccion
     */
    public ResponseConstruccionDireccion getResponseConstruirApartamentoDireccion() {
        return this.responseConstruirApartamentoDireccion;
    }

    /**
     * set field
     *
     * @param responseConstruirApartamentoDireccion
     */
    public void setResponseConstruirApartamentoDireccion(ResponseConstruccionDireccion responseConstruirApartamentoDireccion) {
        this.responseConstruirApartamentoDireccion = responseConstruirApartamentoDireccion;
    }

    /**
     * get field
     *
     * @return requestNuevoApartamento
     */
    public RequestConstruccionDireccion getRequestNuevoApartamento() {
        return this.requestNuevoApartamento;
    }

    /**
     * set field
     *
     * @param requestNuevoApartamento
     */
    public void setRequestNuevoApartamento(RequestConstruccionDireccion requestNuevoApartamento) {
        this.requestNuevoApartamento = requestNuevoApartamento;
    }

    /**
     * get field
     *
     * @return drDireccion
     */
    public DrDireccion getDrDireccion() {
        return this.drDireccion;
    }

    /**
     * set field
     *
     * @param drDireccion
     */
    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    /**
     * get field
     *
     * @return drDireccionNuevoApartamento
     */
    public DrDireccion getDrDireccionNuevoApartamento() {
        return this.drDireccionNuevoApartamento;
    }

    /**
     * set field
     *
     * @param drDireccionNuevoApartamento
     */
    public void setDrDireccionNuevoApartamento(DrDireccion drDireccionNuevoApartamento) {
        this.drDireccionNuevoApartamento = drDireccionNuevoApartamento;
    }

    /**
     * get field
     *
     * @return DrDireccionAmbiguaCopia
     */
    public DrDireccion getDrDireccionAmbiguaCopia() {
        return this.drDireccionAmbiguaCopia;
    }

    /**
     * set field
     *
     * @param DrDireccionAmbiguaCopia
     */
    public void setDrDireccionAmbiguaCopia(DrDireccion DrDireccionAmbiguaCopia) {
        this.drDireccionAmbiguaCopia = DrDireccionAmbiguaCopia;
    }

    /**
     * get field
     *
     * @return cuentaMatrizOtraDireccion
     */
    public CmtCuentaMatrizMgl getCuentaMatrizOtraDireccion() {
        return this.cuentaMatrizOtraDireccion;
    }

    /**
     * set field
     *
     * @param cuentaMatrizOtraDireccion
     */
    public void setCuentaMatrizOtraDireccion(CmtCuentaMatrizMgl cuentaMatrizOtraDireccion) {
        this.cuentaMatrizOtraDireccion = cuentaMatrizOtraDireccion;
    }

    /**
     * get field
     *
     * @return hhppSelected
     */
    public HhppMgl getHhppSelected() {
        return this.hhppSelected;
    }

    /**
     * set field
     *
     * @param hhppSelected
     */
    public void setHhppSelected(HhppMgl hhppSelected) {
        this.hhppSelected = hhppSelected;
    }

    /**
     * get field
     *
     * @return perfilVt
     */
    public int getPerfilVt() {
        return this.perfilVt;
    }

    /**
     * set field
     *
     * @param perfilVt
     */
    public void setPerfilVt(int perfilVt) {
        this.perfilVt = perfilVt;
    }

    /**
     * get field
     *
     * @return departamento
     */
    public String getDepartamento() {
        return this.departamento;
    }

    /**
     * set field
     *
     * @param departamento
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * get field
     *
     * @return idSolicitudDth
     */
    public BigDecimal getIdSolicitudDth() {
        return this.idSolicitudDth;
    }

    /**
     * set field
     *
     * @param idSolicitudDth
     */
    public void setIdSolicitudDth(BigDecimal idSolicitudDth) {
        this.idSolicitudDth = idSolicitudDth;
    }

    /**
     * get field
     *
     * @return idCentroPoblado
     */
    public BigDecimal getIdCentroPoblado() {
        return this.idCentroPoblado;
    }

    /**
     * set field
     *
     * @param idCentroPoblado
     */
    public void setIdCentroPoblado(BigDecimal idCentroPoblado) {
        this.idCentroPoblado = idCentroPoblado;
    }

    /**
     * get field
     *
     * @return ciudad
     */
    public BigDecimal getCiudad() {
        return this.ciudad;
    }

    /**
     * set field
     *
     * @param ciudad
     */
    public void setCiudad(BigDecimal ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * get field
     *
     * @return nuevoEstratoHhpp
     */
    public String getNuevoEstratoHhpp() {
        return this.nuevoEstratoHhpp;
    }

    /**
     * set field
     *
     * @param nuevoEstratoHhpp
     */
    public void setNuevoEstratoHhpp(String nuevoEstratoHhpp) {
        this.nuevoEstratoHhpp = nuevoEstratoHhpp;
    }

    /**
     * get field
     *
     * @return direccionAmbiguaAntigua
     */
    public String getDireccionAmbiguaAntigua() {
        return this.direccionAmbiguaAntigua;
    }

    /**
     * set field
     *
     * @param direccionAmbiguaAntigua
     */
    public void setDireccionAmbiguaAntigua(String direccionAmbiguaAntigua) {
        this.direccionAmbiguaAntigua = direccionAmbiguaAntigua;
    }

    /**
     * get field
     *
     * @return direccionAmbiguaNueva
     */
    public String getDireccionAmbiguaNueva() {
        return this.direccionAmbiguaNueva;
    }

    /**
     * set field
     *
     * @param direccionAmbiguaNueva
     */
    public void setDireccionAmbiguaNueva(String direccionAmbiguaNueva) {
        this.direccionAmbiguaNueva = direccionAmbiguaNueva;
    }

    /**
     * get field
     *
     * @return mensajeMatrizViabilidad
     */
    public String getMensajeMatrizViabilidad() {
        return this.mensajeMatrizViabilidad;
    }

    /**
     * set field
     *
     * @param mensajeMatrizViabilidad
     */
    public void setMensajeMatrizViabilidad(String mensajeMatrizViabilidad) {
        this.mensajeMatrizViabilidad = mensajeMatrizViabilidad;
    }

    /**
     * get field
     *
     * @return inicioPagina
     */
    public String getInicioPagina() {
        return this.inicioPagina;
    }

    /**
     * set field
     *
     * @param inicioPagina
     */
    public void setInicioPagina(String inicioPagina) {
        this.inicioPagina = inicioPagina;
    }

    /**
     * get field
     *
     * @return anteriorPagina
     */
    public String getAnteriorPagina() {
        return this.anteriorPagina;
    }

    /**
     * set field
     *
     * @param anteriorPagina
     */
    public void setAnteriorPagina(String anteriorPagina) {
        this.anteriorPagina = anteriorPagina;
    }

    /**
     * get field
     *
     * @return finPagina
     */
    public String getFinPagina() {
        return this.finPagina;
    }

    /**
     * set field
     *
     * @param finPagina
     */
    public void setFinPagina(String finPagina) {
        this.finPagina = finPagina;
    }

    /**
     * get field
     *
     * @return siguientePagina
     */
    public String getSiguientePagina() {
        return this.siguientePagina;
    }

    /**
     * set field
     *
     * @param siguientePagina
     */
    public void setSiguientePagina(String siguientePagina) {
        this.siguientePagina = siguientePagina;
    }

    /**
     * get field
     *
     * @return notaComentarioStr
     */
    public String getNotaComentarioStr() {
        return this.notaComentarioStr;
    }

    /**
     * set field
     *
     * @param notaComentarioStr
     */
    public void setNotaComentarioStr(String notaComentarioStr) {
        this.notaComentarioStr = notaComentarioStr;
    }

    /**
     * get field
     *
     * @return tipoTecnologia
     */
    public String getTipoTecnologia() {
        return this.tipoTecnologia;
    }

    /**
     * set field
     *
     * @param tipoTecnologia
     */
    public void setTipoTecnologia(String tipoTecnologia) {
        this.tipoTecnologia = tipoTecnologia;
    }

    /**
     * get field
     *
     * @return tipoDireccion
     */
    public String getTipoDireccion() {
        return this.tipoDireccion;
    }

    /**
     * set field
     *
     * @param tipoDireccion
     */
    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    /**
     * get field
     *
     * @return rrRegional
     */
    public String getRrRegional() {
        return this.rrRegional;
    }

    /**
     * set field
     *
     * @param rrRegional
     */
    public void setRrRegional(String rrRegional) {
        this.rrRegional = rrRegional;
    }

    /**
     * get field
     *
     * @return rrCiudad
     */
    public String getRrCiudad() {
        return this.rrCiudad;
    }

    /**
     * set field
     *
     * @param rrCiudad
     */
    public void setRrCiudad(String rrCiudad) {
        this.rrCiudad = rrCiudad;
    }

    /**
     * get field
     *
     * @return dirEstado
     */
    public String getDirEstado() {
        return this.dirEstado;
    }

    /**
     * set field
     *
     * @param dirEstado
     */
    public void setDirEstado(String dirEstado) {
        this.dirEstado = dirEstado;
    }

    /**
     * get field
     *
     * @return direccionCk
     */
    public String getDireccionCk() {
        return this.direccionCk;
    }

    /**
     * set field
     *
     * @param direccionCk
     */
    public void setDireccionCk(String direccionCk) {
        this.direccionCk = direccionCk;
    }

    /**
     * get field
     *
     * @return nivelValorBm
     */
    public String getNivelValorBm() {
        return this.nivelValorBm;
    }

    /**
     * set field
     *
     * @param nivelValorBm
     */
    public void setNivelValorBm(String nivelValorBm) {
        this.nivelValorBm = nivelValorBm;
    }

    /**
     * get field
     *
     * @return nivelValorIt
     */
    public String getNivelValorIt() {
        return this.nivelValorIt;
    }

    /**
     * set field
     *
     * @param nivelValorIt
     */
    public void setNivelValorIt(String nivelValorIt) {
        this.nivelValorIt = nivelValorIt;
    }

    /**
     * get field
     *
     * @return tipoNivelBm
     */
    public String getTipoNivelBm() {
        return this.tipoNivelBm;
    }

    /**
     * set field
     *
     * @param tipoNivelBm
     */
    public void setTipoNivelBm(String tipoNivelBm) {
        this.tipoNivelBm = tipoNivelBm;
    }

    /**
     * get field
     *
     * @return tipoNivelIt
     */
    public String getTipoNivelIt() {
        return this.tipoNivelIt;
    }

    /**
     * set field
     *
     * @param tipoNivelIt
     */
    public void setTipoNivelIt(String tipoNivelIt) {
        this.tipoNivelIt = tipoNivelIt;
    }

    /**
     * get field
     *
     * @return tipoNivelApartamento
     */
    public String getTipoNivelApartamento() {
        return this.tipoNivelApartamento;
    }

    /**
     * set field
     *
     * @param tipoNivelApartamento
     */
    public void setTipoNivelApartamento(String tipoNivelApartamento) {
        this.tipoNivelApartamento = tipoNivelApartamento;
    }

    /**
     * get field
     *
     * @return tipoNivelComplemento
     */
    public String getTipoNivelComplemento() {
        return this.tipoNivelComplemento;
    }

    /**
     * set field
     *
     * @param tipoNivelComplemento
     */
    public void setTipoNivelComplemento(String tipoNivelComplemento) {
        this.tipoNivelComplemento = tipoNivelComplemento;
    }

    /**
     * get field
     *
     * @return tipoNivelNuevoApartamento
     */
    public String getTipoNivelNuevoApartamento() {
        return this.tipoNivelNuevoApartamento;
    }

    /**
     * set field
     *
     * @param tipoNivelNuevoApartamento
     */
    public void setTipoNivelNuevoApartamento(String tipoNivelNuevoApartamento) {
        this.tipoNivelNuevoApartamento = tipoNivelNuevoApartamento;
    }

    /**
     * get field
     *
     * @return valorNivelNuevoApartamento
     */
    public String getValorNivelNuevoApartamento() {
        return this.valorNivelNuevoApartamento;
    }

    /**
     * set field
     *
     * @param valorNivelNuevoApartamento
     */
    public void setValorNivelNuevoApartamento(String valorNivelNuevoApartamento) {
        this.valorNivelNuevoApartamento = valorNivelNuevoApartamento;
    }

    /**
     * get field
     *
     * @return apartamento
     */
    public String getApartamento() {
        return this.apartamento;
    }

    /**
     * set field
     *
     * @param apartamento
     */
    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    /**
     * get field
     *
     * @return nuevoApartamento
     */
    public String getNuevoApartamento() {
        return this.nuevoApartamento;
    }

    /**
     * set field
     *
     * @param nuevoApartamento
     */
    public void setNuevoApartamento(String nuevoApartamento) {
        this.nuevoApartamento = nuevoApartamento;
    }

    /**
     * get field
     *
     * @return nuevoApartamentoRr
     */
    public String getNuevoApartamentoRr() {
        return this.nuevoApartamentoRr;
    }

    /**
     * set field
     *
     * @param nuevoApartamentoRr
     */
    public void setNuevoApartamentoRr(String nuevoApartamentoRr) {
        this.nuevoApartamentoRr = nuevoApartamentoRr;
    }

    /**
     * get field
     *
     * @return complemento
     */
    public String getComplemento() {
        return this.complemento;
    }

    /**
     * set field
     *
     * @param complemento
     */
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    /**
     * get field
     *
     * @return direccionLabel
     */
    public String getDireccionLabel() {
        return this.direccionLabel;
    }

    /**
     * set field
     *
     * @param direccionLabel
     */
    public void setDireccionLabel(String direccionLabel) {
        this.direccionLabel = direccionLabel;
    }

    /**
     * get field
     *
     * @return barrioSugerido
     */
    public String getBarrioSugerido() {
        return this.barrioSugerido;
    }

    /**
     * set field
     *
     * @param barrioSugerido
     */
    public void setBarrioSugerido(String barrioSugerido) {
        this.barrioSugerido = barrioSugerido;
    }

    /**
     * get field
     *
     * @return barrioSugeridoStr
     */
    public String getBarrioSugeridoStr() {
        return this.barrioSugeridoStr;
    }

    /**
     * set field
     *
     * @param barrioSugeridoStr
     */
    public void setBarrioSugeridoStr(String barrioSugeridoStr) {
        this.barrioSugeridoStr = barrioSugeridoStr;
    }

    /**
     * get field
     *
     * @return barrio
     */
    public String getBarrio() {
        return this.barrio;
    }

    /**
     * set field
     *
     * @param barrio
     */
    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    /**
     * get field
     *
     * @return tmpInicio
     */
    public String getTmpInicio() {
        return this.tmpInicio;
    }

    /**
     * set field
     *
     * @param tmpInicio
     */
    public void setTmpInicio(String tmpInicio) {
        this.tmpInicio = tmpInicio;
    }

    /**
     * get field
     *
     * @return deltaTiempo
     */
    public String getDeltaTiempo() {
        return this.deltaTiempo;
    }

    /**
     * set field
     *
     * @param deltaTiempo
     */
    public void setDeltaTiempo(String deltaTiempo) {
        this.deltaTiempo = deltaTiempo;
    }

    /**
     * get field
     *
     * @return tmpFin
     */
    public String getTmpFin() {
        return this.tmpFin;
    }

    /**
     * set field
     *
     * @param tmpFin
     */
    public void setTmpFin(String tmpFin) {
        this.tmpFin = tmpFin;
    }

    /**
     * get field
     *
     * @return timeSol
     */
    public String getTimeSol() {
        return this.timeSol;
    }

    /**
     * set field
     *
     * @param timeSol
     */
    public void setTimeSol(String timeSol) {
        this.timeSol = timeSol;
    }

    /**
     * get field
     *
     * @return tipoAccionSolicitud
     */
    public String getTipoAccionSolicitud() {
        return this.tipoAccionSolicitud;
    }

    /**
     * set field
     *
     * @param tipoAccionSolicitud
     */
    public void setTipoAccionSolicitud(String tipoAccionSolicitud) {
        this.tipoAccionSolicitud = tipoAccionSolicitud;
    }

    /**
     * get field
     *
     * @return valorNivel6
     */
    public String getValorNivel6() {
        return this.valorNivel6;
    }

    /**
     * set field
     *
     * @param valorNivel6
     */
    public void setValorNivel6(String valorNivel6) {
        this.valorNivel6 = valorNivel6;
    }

    /**
     * get field
     *
     * @return valorNivel5
     */
    public String getValorNivel5() {
        return this.valorNivel5;
    }

    /**
     * set field
     *
     * @param valorNivel5
     */
    public void setValorNivel5(String valorNivel5) {
        this.valorNivel5 = valorNivel5;
    }

    /**
     * get field
     *
     * @return espacio
     */
    public String getEspacio() {
        return this.espacio;
    }

    /**
     * set field
     *
     * @param espacio
     */
    public void setEspacio(String espacio) {
        this.espacio = espacio;
    }

    /**
     * set field
     *
     * @param pageActual
     */
    public void setPageActual(String pageActual) {
        this.pageActual = pageActual;
    }

    /**
     * get field
     *
     * @return numPagina
     */
    public String getNumPagina() {
        return this.numPagina;
    }

    /**
     * set field
     *
     * @param numPagina
     */
    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    /**
     * get field
     *
     * @return direccionRespuestaGeo
     */
    public String getDireccionRespuestaGeo() {
        return this.direccionRespuestaGeo;
    }

    /**
     * set field
     *
     * @param direccionRespuestaGeo
     */
    public void setDireccionRespuestaGeo(String direccionRespuestaGeo) {
        this.direccionRespuestaGeo = direccionRespuestaGeo;
    }

    /**
     * get field
     *
     * @return paginaList
     */
    public List<Integer> getPaginaList() {
        return this.paginaList;
    }

    /**
     * set field
     *
     * @param paginaList
     */
    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    /**
     * get field
     *
     * @return actual
     */
    public int getActual() {
        return this.actual;
    }

    /**
     * set field
     *
     * @param actual
     */
    public void setActual(int actual) {
        this.actual = actual;
    }

    /**
     * get field
     *
     * @return direccionesCambioEstrato
     */
    public boolean isDireccionesCambioEstrato() {
        return this.direccionesCambioEstrato;
    }

    /**
     * set field
     *
     * @param direccionesCambioEstrato
     */
    public void setDireccionesCambioEstrato(boolean direccionesCambioEstrato) {
        this.direccionesCambioEstrato = direccionesCambioEstrato;
    }

    /**
     * get field
     *
     * @return solicitudCambioEstrato
     */
    public boolean isSolicitudCambioEstrato() {
        return this.solicitudCambioEstrato;
    }

    /**
     * set field
     *
     * @param solicitudCambioEstrato
     */
    public void setSolicitudCambioEstrato(boolean solicitudCambioEstrato) {
        this.solicitudCambioEstrato = solicitudCambioEstrato;
    }

    /**
     * get field
     *
     * @return hhppExiste
     */
    public boolean isHhppExiste() {
        return this.hhppExiste;
    }

    /**
     * set field
     *
     * @param hhppExiste
     */
    public void setHhppExiste(boolean hhppExiste) {
        this.hhppExiste = hhppExiste;
    }

    /**
     * get field
     *
     * @return soporteRequerido
     */
    public boolean isSoporteRequerido() {
        return this.soporteRequerido;
    }

    /**
     * set field
     *
     * @param soporteRequerido
     */
    public void setSoporteRequerido(boolean soporteRequerido) {
        this.soporteRequerido = soporteRequerido;
    }

    /**
     * get field
     *
     * @return ambiguaAntigua
     */
    public boolean isAmbiguaAntigua() {
        return this.ambiguaAntigua;
    }

    /**
     * set field
     *
     * @param ambiguaAntigua
     */
    public void setAmbiguaAntigua(boolean ambiguaAntigua) {
        this.ambiguaAntigua = ambiguaAntigua;
    }

    /**
     * get field
     *
     * @return ambiguaNueva
     */
    public boolean isAmbiguaNueva() {
        return this.ambiguaNueva;
    }

    /**
     * set field
     *
     * @param ambiguaNueva
     */
    public void setAmbiguaNueva(boolean ambiguaNueva) {
        this.ambiguaNueva = ambiguaNueva;
    }

    /**
     * get field
     *
     * @return direccionAmbiguaPanel
     */
    public boolean isDireccionAmbiguaPanel() {
        return this.direccionAmbiguaPanel;
    }

    /**
     * set field
     *
     * @param direccionAmbiguaPanel
     */
    public void setDireccionAmbiguaPanel(boolean direccionAmbiguaPanel) {
        this.direccionAmbiguaPanel = direccionAmbiguaPanel;
    }

    /**
     * get field
     *
     * @return cambioDireccionPanel
     */
    public boolean isCambioDireccionPanel() {
        return this.cambioDireccionPanel;
    }

    /**
     * set field
     *
     * @param cambioDireccionPanel
     */
    public void setCambioDireccionPanel(boolean cambioDireccionPanel) {
        this.cambioDireccionPanel = cambioDireccionPanel;
    }

    /**
     * get field
     *
     * @return enableApto
     */
    public boolean isEnableApto() {
        return this.enableApto;
    }

    /**
     * set field
     *
     * @param enableApto
     */
    public void setEnableApto(boolean enableApto) {
        this.enableApto = enableApto;
    }

    /**
     * get field
     *
     * @return deshabilitarCreacion
     */
    public boolean isDeshabilitarCreacion() {
        return this.deshabilitarCreacion;
    }

    /**
     * set field
     *
     * @param deshabilitarCreacion
     */
    public void setDeshabilitarCreacion(boolean deshabilitarCreacion) {
        this.deshabilitarCreacion = deshabilitarCreacion;
    }

    /**
     * get field
     *
     * @return enableTabs
     */
    public boolean isEnableTabs() {
        return this.enableTabs;
    }

    /**
     * set field
     *
     * @param enableTabs
     */
    public void setEnableTabs(boolean enableTabs) {
        this.enableTabs = enableTabs;
    }

    /**
     * get field
     *
     * @return showSolicitud
     */
    public boolean isShowSolicitud() {
        return this.showSolicitud;
    }

    /**
     * set field
     *
     * @param showSolicitud
     */
    public void setShowSolicitud(boolean showSolicitud) {
        this.showSolicitud = showSolicitud;
    }

    /**
     * get field
     *
     * @return showTrack
     */
    public boolean isShowTrack() {
        return this.showTrack;
    }

    /**
     * set field
     *
     * @param showTrack
     */
    public void setShowTrack(boolean showTrack) {
        this.showTrack = showTrack;
    }

    /**
     * get field
     *
     * @return showNotas
     */
    public boolean isShowNotas() {
        return this.showNotas;
    }

    /**
     * set field
     *
     * @param showNotas
     */
    public void setShowNotas(boolean showNotas) {
        this.showNotas = showNotas;
    }

    /**
     * get field
     *
     * @return showCKPanel
     */
    public boolean isShowCKPanel() {
        return this.showCKPanel;
    }

    /**
     * set field
     *
     * @param showCKPanel
     */
    public void setShowCKPanel(boolean showCKPanel) {
        this.showCKPanel = showCKPanel;
    }

    /**
     * get field
     *
     * @return showBMPanel
     */
    public boolean isShowBMPanel() {
        return this.showBMPanel;
    }

    /**
     * set field
     *
     * @param showBMPanel
     */
    public void setShowBMPanel(boolean showBMPanel) {
        this.showBMPanel = showBMPanel;
    }

    /**
     * get field
     *
     * @return showITPanel
     */
    public boolean isShowITPanel() {
        return this.showITPanel;
    }

    /**
     * set field
     *
     * @param showITPanel
     */
    public void setShowITPanel(boolean showITPanel) {
        this.showITPanel = showITPanel;
    }

    /**
     * get field
     *
     * @return barrioSugeridoPanel
     */
    public boolean isBarrioSugeridoPanel() {
        return this.barrioSugeridoPanel;
    }

    /**
     * set field
     *
     * @param barrioSugeridoPanel
     */
    public void setBarrioSugeridoPanel(boolean barrioSugeridoPanel) {
        this.barrioSugeridoPanel = barrioSugeridoPanel;
    }

    /**
     * get field
     *
     * @return barrioSugeridoField
     */
    public boolean isBarrioSugeridoField() {
        return this.barrioSugeridoField;
    }

    /**
     * set field
     *
     * @param barrioSugeridoField
     */
    public void setBarrioSugeridoField(boolean barrioSugeridoField) {
        this.barrioSugeridoField = barrioSugeridoField;
    }

    /**
     * get field
     *
     * @return crearSolicitudButton
     */
    public boolean isCrearSolicitudButton() {
        return this.crearSolicitudButton;
    }

    /**
     * set field
     *
     * @param crearSolicitudButton
     */
    public void setCrearSolicitudButton(boolean crearSolicitudButton) {
        this.crearSolicitudButton = crearSolicitudButton;
    }

    /**
     * get field
     *
     * @return deshabilitarValidar
     */
    public boolean isDeshabilitarValidar() {
        return this.deshabilitarValidar;
    }

    /**
     * set field
     *
     * @param deshabilitarValidar
     */
    public void setDeshabilitarValidar(boolean deshabilitarValidar) {
        this.deshabilitarValidar = deshabilitarValidar;
    }

    /**
     * get field
     *
     * @return direccionConstruida
     */
    public boolean isDireccionConstruida() {
        return this.direccionConstruida;
    }

    /**
     * set field
     *
     * @param direccionConstruida
     */
    public void setDireccionConstruida(boolean direccionConstruida) {
        this.direccionConstruida = direccionConstruida;
    }

    /**
     * get field
     *
     * @return unidadesPredio
     */
    public boolean isUnidadesPredio() {
        return this.unidadesPredio;
    }

    /**
     * set field
     *
     * @param unidadesPredio
     */
    public void setUnidadesPredio(boolean unidadesPredio) {
        this.unidadesPredio = unidadesPredio;
    }

    /**
     * get field
     *
     * @return habilitarViabilidad
     */
    public boolean isHabilitarViabilidad() {
        return this.habilitarViabilidad;
    }

    /**
     * set field
     *
     * @param habilitarViabilidad
     */
    public void setHabilitarViabilidad(boolean habilitarViabilidad) {
        this.habilitarViabilidad = habilitarViabilidad;
    }

    /**
     * get field
     *
     * @return cuentaMatrizActiva
     */
    public boolean isCuentaMatrizActiva() {
        return this.cuentaMatrizActiva;
    }

    /**
     * set field
     *
     * @param cuentaMatrizActiva
     */
    public void setCuentaMatrizActiva(boolean cuentaMatrizActiva) {
        this.cuentaMatrizActiva = cuentaMatrizActiva;
    }

    /**
     * get field
     *
     * @return bloquearCambioAmbigua
     */
    public boolean isBloquearCambioAmbigua() {
        return this.bloquearCambioAmbigua;
    }

    /**
     * set field
     *
     * @param bloquearCambioAmbigua
     */
    public void setBloquearCambioAmbigua(boolean bloquearCambioAmbigua) {
        this.bloquearCambioAmbigua = bloquearCambioAmbigua;
    }

    /**
     * get field
     *
     * @return mallaNuevaAmbigua
     */
    public boolean isMallaNuevaAmbigua() {
        return this.mallaNuevaAmbigua;
    }

    /**
     * set field
     *
     * @param mallaNuevaAmbigua
     */
    public void setMallaNuevaAmbigua(boolean mallaNuevaAmbigua) {
        this.mallaNuevaAmbigua = mallaNuevaAmbigua;
    }

    /**
     * set field
     *
     * @param pageActualHhpp
     */
    public void setPageActualHhpp(String pageActualHhpp) {
        this.pageActualHhpp = pageActualHhpp;
    }

    /**
     * get field
     *
     * @return numPaginaHhpp
     */
    public String getNumPaginaHhpp() {
        return this.numPaginaHhpp;
    }

    /**
     * set field
     *
     * @param numPaginaHhpp
     */
    public void setNumPaginaHhpp(String numPaginaHhpp) {
        this.numPaginaHhpp = numPaginaHhpp;
    }

    /**
     * get field
     *
     * @return paginaListHhpp
     */
    public List<Integer> getPaginaListHhpp() {
        return this.paginaListHhpp;
    }

    /**
     * set field
     *
     * @param paginaListHhpp
     */
    public void setPaginaListHhpp(List<Integer> paginaListHhpp) {
        this.paginaListHhpp = paginaListHhpp;
    }

    /**
     * get field
     *
     * @return actualHhpp
     */
    public int getActualHhpp() {
        return this.actualHhpp;
    }

    /**
     * set field
     *
     * @param actualHhpp
     */
    public void setActualHhpp(int actualHhpp) {
        this.actualHhpp = actualHhpp;
    }

    /**
     * get field
     *
     * @return filasPagHhpp
     */
    public int getFilasPagHhpp() {
        return this.filasPagHhpp;
    }

    /**
     * set field
     *
     * @param filasPagHhpp
     */
    public void setFilasPagHhpp(int filasPagHhpp) {
        this.filasPagHhpp = filasPagHhpp;
    }

    /**
     * get field
     *
     * @return hhppList
     */
    public List<HhppMgl> getHhppList() {
        return this.hhppList;
    }

    /**
     * set field
     *
     * @param hhppList
     */
    public void setHhppList(List<HhppMgl> hhppList) {
        this.hhppList = hhppList;
    }

    /**
     * get field
     *
     * @return hhppAuxList
     */
    public List<HhppMgl> getHhppAuxList() {
        return this.hhppAuxList;
    }

    /**
     * set field
     *
     * @param hhppAuxList
     */
    public void setHhppAuxList(List<HhppMgl> hhppAuxList) {
        this.hhppAuxList = hhppAuxList;
    }

    /**
     * get field
     *
     * @return listNivel5
     */
    public List<OpcionIdNombre> getListNivel5() {
        return this.listNivel5;
    }

    /**
     * set field
     *
     * @param listNivel5
     */
    public void setListNivel5(List<OpcionIdNombre> listNivel5) {
        this.listNivel5 = listNivel5;
    }

    /**
     * get field
     *
     * @return listNivel6
     */
    public List<OpcionIdNombre> getListNivel6() {
        return this.listNivel6;
    }

    /**
     * set field
     *
     * @param listNivel6
     */
    public void setListNivel6(List<OpcionIdNombre> listNivel6) {
        this.listNivel6 = listNivel6;
    }

    /**
     * get field
     *
     * @return notasSolicitudList
     */
    public List<CmtNotasSolicitudVtMgl> getNotasSolicitudList() {
        return this.notasSolicitudList;
    }

    /**
     * set field
     *
     * @param notasSolicitudList
     */
    public void setNotasSolicitudList(List<CmtNotasSolicitudVtMgl> notasSolicitudList) {
        this.notasSolicitudList = notasSolicitudList;
    }

    /**
     * get field
     *
     * @return tipoNotaList
     */
    public List<CmtBasicaMgl> getTipoNotaList() {
        return this.tipoNotaList;
    }

    /**
     * set field
     *
     * @param tipoNotaList
     */
    public void setTipoNotaList(List<CmtBasicaMgl> tipoNotaList) {
        this.tipoNotaList = tipoNotaList;
    }

    /**
     * get field
     *
     * @return regionalList
     */
    public List<RrRegionales> getRegionalList() {
        return this.regionalList;
    }

    /**
     * set field
     *
     * @param regionalList
     */
    public void setRegionalList(List<RrRegionales> regionalList) {
        this.regionalList = regionalList;
    }

    /**
     * get field
     *
     * @return ciudadesList
     */
    public List<GeograficoPoliticoMgl> getCiudadesList() {
        return this.ciudadesList;
    }

    /**
     * set field
     *
     * @param ciudadesList
     */
    public void setCiudadesList(List<GeograficoPoliticoMgl> ciudadesList) {
        this.ciudadesList = ciudadesList;
    }

    /**
     * get field
     *
     * @return areaCanalList
     */
    public List<ParametrosCalles> getAreaCanalList() {
        return this.areaCanalList;
    }

    /**
     * set field
     *
     * @param areaCanalList
     */
    public void setAreaCanalList(List<ParametrosCalles> areaCanalList) {
        this.areaCanalList = areaCanalList;
    }

    /**
     * get field
     *
     * @return tipoTecnologiaBasicaList
     */
    public List<CmtBasicaMgl> getTipoTecnologiaBasicaList() {
        return this.tipoTecnologiaBasicaList;
    }

    /**
     * set field
     *
     * @param tipoTecnologiaBasicaList
     */
    public void setTipoTecnologiaBasicaList(List<CmtBasicaMgl> tipoTecnologiaBasicaList) {
        this.tipoTecnologiaBasicaList = tipoTecnologiaBasicaList;
    }

    /**
     * get field
     *
     * @return validacionParametrizadaList
     */
    public List<ValidacionParametrizadaTipoValidacionMgl> getValidacionParametrizadaList() {
        return this.validacionParametrizadaList;
    }

    /**
     * set field
     *
     * @param validacionParametrizadaList
     */
    public void setValidacionParametrizadaList(List<ValidacionParametrizadaTipoValidacionMgl> validacionParametrizadaList) {
        this.validacionParametrizadaList = validacionParametrizadaList;
    }

    /**
     * get field
     *
     * @return ciudadGpo
     */
    public GeograficoPoliticoMgl getCiudadGpo() {
        return this.ciudadGpo;
    }

    /**
     * set field
     *
     * @param ciudadGpo
     */
    public void setCiudadGpo(GeograficoPoliticoMgl ciudadGpo) {
        this.ciudadGpo = ciudadGpo;
    }

    /**
     * get field
     *
     * @return departamentoGpo
     */
    public GeograficoPoliticoMgl getDepartamentoGpo() {
        return this.departamentoGpo;
    }

    /**
     * set field
     *
     * @param departamentoGpo
     */
    public void setDepartamentoGpo(GeograficoPoliticoMgl departamentoGpo) {
        this.departamentoGpo = departamentoGpo;
    }

    /**
     * get field
     *
     * @return configurationAddressComponent
     */
    public ConfigurationAddressComponent getConfigurationAddressComponent() {
        return this.configurationAddressComponent;
    }

    /**
     * set field
     *
     * @param configurationAddressComponent
     */
    public void setConfigurationAddressComponent(ConfigurationAddressComponent configurationAddressComponent) {
        this.configurationAddressComponent = configurationAddressComponent;
    }

    /**
     * get field
     *
     * @return ckList
     */
    public List<OpcionIdNombre> getCkList() {
        return this.ckList;
    }

    /**
     * set field
     *
     * @param ckList
     */
    public void setCkList(List<OpcionIdNombre> ckList) {
        this.ckList = ckList;
    }

    /**
     * get field
     *
     * @return bmList
     */
    public List<OpcionIdNombre> getBmList() {
        return this.bmList;
    }

    /**
     * set field
     *
     * @param bmList
     */
    public void setBmList(List<OpcionIdNombre> bmList) {
        this.bmList = bmList;
    }

    /**
     * get field
     *
     * @return itList
     */
    public List<OpcionIdNombre> getItList() {
        return this.itList;
    }

    /**
     * set field
     *
     * @param itList
     */
    public void setItList(List<OpcionIdNombre> itList) {
        this.itList = itList;
    }

    public List<SelectItem> getDocumentoList() {
        return documentoList;
    }

    public void setDocumentoList(List<SelectItem> documentoList) {
        this.documentoList = documentoList;
    }

    public List<OpcionIdNombre> getComplementoList() {
        return this.complementoList;
    }

    /**
     * set field
     *
     * @param complementoList
     */
    public void setComplementoList(List<OpcionIdNombre> complementoList) {
        this.complementoList = complementoList;
    }

    /**
     * get field
     *
     * @return barrioSugeridoList
     */
    public List<AddressSuggested> getBarrioSugeridoList() {
        return this.barrioSugeridoList;
    }

    /**
     * set field
     *
     * @param barrioSugeridoList
     */
    public void setBarrioSugeridoList(List<AddressSuggested> barrioSugeridoList) {
        this.barrioSugeridoList = barrioSugeridoList;
    }

    /**
     * get field
     *
     * @return unidadList
     */
    public List<UnidadStructPcml> getUnidadList() {
        return this.unidadList;
    }

    /**
     * set field
     *
     * @param unidadList
     */
    public void setUnidadList(List<UnidadStructPcml> unidadList) {
        this.unidadList = unidadList;
    }

    /**
     * get field
     *
     * @return unidadAuxiliarList
     */
    public List<UnidadStructPcml> getUnidadAuxiliarList() {
        return this.unidadAuxiliarList;
    }

    /**
     * set field
     *
     * @param unidadAuxiliarList
     */
    public void setUnidadAuxiliarList(List<UnidadStructPcml> unidadAuxiliarList) {
        this.unidadAuxiliarList = unidadAuxiliarList;
    }

    /**
     * get field
     *
     * @return centroPobladoList
     */
    public List<GeograficoPoliticoMgl> getCentroPobladoList() {
        return this.centroPobladoList;
    }

    /**
     * set field
     *
     * @param centroPobladoList
     */
    public void setCentroPobladoList(List<GeograficoPoliticoMgl> centroPobladoList) {
        this.centroPobladoList = centroPobladoList;
    }

    /**
     * get field
     *
     * @return departamentoList
     */
    public List<GeograficoPoliticoMgl> getDepartamentoList() {
        return this.departamentoList;
    }

    /**
     * set field
     *
     * @param departamentoList
     */
    public void setDepartamentoList(List<GeograficoPoliticoMgl> departamentoList) {
        this.departamentoList = departamentoList;
    }

    /**
     * get field
     *
     * @return estratoHhppList
     */
    public List<CmtBasicaMgl> getEstratoHhppList() {
        return this.estratoHhppList;
    }

    /**
     * set field
     *
     * @param estratoHhppList
     */
    public void setEstratoHhppList(List<CmtBasicaMgl> estratoHhppList) {
        this.estratoHhppList = estratoHhppList;
    }

    /**
     * get field
     *
     * @return regresarMenu
     */
    public String getRegresarMenu() {
        return this.regresarMenu;
    }

    /**
     * set field
     *
     * @param regresarMenu
     */
    public void setRegresarMenu(String regresarMenu) {
        this.regresarMenu = regresarMenu;
    }

    /**
     * get field
     *
     * @return requestHttp
     */
    public HttpServletRequest getRequestHttp() {
        return this.requestHttp;
    }

    /**
     * set field
     *
     * @param requestHttp
     */
    public void setRequestHttp(HttpServletRequest requestHttp) {
        this.requestHttp = requestHttp;
    }

    /**
     * get field
     *
     * @return responseValidarDireccion
     */
    public ResponseValidacionDireccion getResponseValidarDireccion() {
        return this.responseValidarDireccion;
    }

    /**
     * set field
     *
     * @param responseValidarDireccion
     */
    public void setResponseValidarDireccion(ResponseValidacionDireccion responseValidarDireccion) {
        this.responseValidarDireccion = responseValidarDireccion;
    }

    /**
     * get field
     *
     * @return responseConstruirDireccion
     */
    public ResponseConstruccionDireccion getResponseConstruirDireccion() {
        return this.responseConstruirDireccion;
    }

    /**
     * set field
     *
     * @param responseConstruirDireccion
     */
    public void setResponseConstruirDireccion(ResponseConstruccionDireccion responseConstruirDireccion) {
        this.responseConstruirDireccion = responseConstruirDireccion;
    }

    /**
     * get field
     *
     * @return crearSolicitudRequest
     */
    public RequestCreaSolicitud getCrearSolicitudRequest() {
        return this.crearSolicitudRequest;
    }

    /**
     * set field
     *
     * @param crearSolicitudRequest
     */
    public void setCrearSolicitudRequest(RequestCreaSolicitud crearSolicitudRequest) {
        this.crearSolicitudRequest = crearSolicitudRequest;
    }

    /**
     * get field
     *
     * @return response
     */
    public HttpServletResponse getResponse() {
        return this.response;
    }

    /**
     * set field
     *
     * @param response
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * get field
     *
     * @return session
     */
    public HttpSession getSession() {
        return this.session;
    }

    /**
     * set field
     *
     * @param session
     */
    public void setSession(HttpSession session) {
        this.session = session;
    }

    /**
     * get field //modificaciones para link's de archivos cambio de estrato
     *
     * @return estiloObligatorio //modificaciones para link's de archivos cambio de estrato
     */
    public String getEstiloObligatorio() {
        return this.estiloObligatorio;
    }

    /**
     * set field //modificaciones para link's de archivos cambio de estrato
     *
     * @param estiloObligatorio //modificaciones para link's de archivos cambio de estrato
     */
    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    /**
     * get field
     *
     * @return tipoContexto
     */
    public String getTipoContexto() {
        return this.tipoContexto;
    }

    /**
     * set field
     *
     * @param tipoContexto
     */
    public void setTipoContexto(String tipoContexto) {
        this.tipoContexto = tipoContexto;
    }

    /**
     * get field
     *
     * @return linksDocumentos
     */
    public List<String> getLinksDocumentos() {
        return this.linksDocumentos;
    }

    /**
     * set field
     *
     * @param linksDocumentos
     */
    public void setLinksDocumentos(List<String> linksDocumentos) {
        this.linksDocumentos = linksDocumentos;
    }

    /**
     * get field
     *
     * @return securityLogin
     */
    public SecurityLogin getSecurityLogin() {
        return this.securityLogin;
    }

    /**
     * set field
     *
     * @param securityLogin
     */
    public void setSecurityLogin(SecurityLogin securityLogin) {
        this.securityLogin = securityLogin;
    }

    /**
     * get field
     *
     * @return fileDelete
     */
    public File getFileDelete() {
        return this.fileDelete;
    }

    /**
     * set field
     *
     * @param fileDelete
     */
    public void setFileDelete(File fileDelete) {
        this.fileDelete = fileDelete;
    }

    /**
     * get field
     *
     * @return centroPobladoDireccion
     */
    public GeograficoPoliticoMgl getCentroPobladoDireccion() {
        return this.centroPobladoDireccion;
    }

    /**
     * set field
     *
     * @param centroPobladoDireccion
     */
    public void setCentroPobladoDireccion(GeograficoPoliticoMgl centroPobladoDireccion) {
        this.centroPobladoDireccion = centroPobladoDireccion;
    }

    /**
     * get field
     *
     * @return ciudadDireccion
     */
    public GeograficoPoliticoMgl getCiudadDireccion() {
        return this.ciudadDireccion;
    }

    /**
     * set field
     *
     * @param ciudadDireccion
     */
    public void setCiudadDireccion(GeograficoPoliticoMgl ciudadDireccion) {
        this.ciudadDireccion = ciudadDireccion;
    }

    /**
     * get field
     *
     * @return departamentoDireccion
     */
    public GeograficoPoliticoMgl getDepartamentoDireccion() {
        return this.departamentoDireccion;
    }

    /**
     * set field
     *
     * @param departamentoDireccion
     */
    public void setDepartamentoDireccion(GeograficoPoliticoMgl departamentoDireccion) {
        this.departamentoDireccion = departamentoDireccion;
    }

    /**
     * get field
     *
     * @return habilitarRR
     */
    public boolean isHabilitarRR() {
        return this.habilitarRR;
    }

    /**
     * set field
     *
     * @param habilitarRR
     */
    public void setHabilitarRR(boolean habilitarRR) {
        this.habilitarRR = habilitarRR;
    }

    /**
     * get field
     *
     * @return direccionDetalladaMgl
     */
    public CmtDireccionDetalladaMglDto getDireccionDetalladaMgl() {
        return this.direccionDetalladaMgl;
    }

    /**
     * set field
     *
     * @param direccionDetalladaMgl
     */
    public void setDireccionDetalladaMgl(CmtDireccionDetalladaMglDto direccionDetalladaMgl) {
        this.direccionDetalladaMgl = direccionDetalladaMgl;
    }

    /**
     * get field
     *
     * @return hayComplmento
     */
    public boolean isHayComplmento() {
        return this.hayComplmento;
    }

    /**
     * set field
     *
     * @param hayComplmento
     */
    public void setHayComplmento(boolean hayComplmento) {
        this.hayComplmento = hayComplmento;
    }

    /**
     * get field
     *
     * @return complementoCreate
     */
    public String getComplementoCreate() {
        return this.complementoCreate;
    }

    /**
     * set field
     *
     * @param complementoCreate
     */
    public void setComplementoCreate(String complementoCreate) {
        this.complementoCreate = complementoCreate;
    }

    /**
     * get field
     *
     * @return startCreteRequest
     */
    public Date getStartCreteRequest() {
        return this.startCreteRequest;
    }

    /**
     * set field
     *
     * @param startCreteRequest
     */
    public void setStartCreteRequest(Date startCreteRequest) {
        this.startCreteRequest = startCreteRequest;
    }

    public void puedeMostrarCampoTipoDocumento() {
        List<TiposSolicitudesHhppEnum> solicitudesValidas = Arrays.asList(CAMBIO_DIRECCION,
                CAMBIO_ESTRATO, CREACION_HOME_PASSED
        );

        mostrarCampoTipoDocumento = StringUtils.isNotBlank(tipoAccionSolicitud) &&
                solicitudesValidas.stream().anyMatch(sol -> sol.getCodigoBasica().equals(tipoAccionSolicitud));

    }

    /**
     * get field
     *
     * @return bmExitoso
     */
    public boolean isBmExitoso() {
        return this.bmExitoso;
    }

    /**
     * set field
     *
     * @param bmExitoso
     */
    public void setBmExitoso(boolean bmExitoso) {
        this.bmExitoso = bmExitoso;
    }

    /**
     * get field
     *
     * @return itExitoso
     */
    public boolean isItExitoso() {
        return this.itExitoso;
    }

    /**
     * set field
     *
     * @param itExitoso
     */
    public void setItExitoso(boolean itExitoso) {
        this.itExitoso = itExitoso;
    }
}
