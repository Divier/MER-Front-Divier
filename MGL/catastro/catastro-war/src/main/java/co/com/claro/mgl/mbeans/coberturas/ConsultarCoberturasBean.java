package co.com.claro.mgl.mbeans.coberturas;

import co.com.claro.mer.auth.micrositio.facade.LoginMicroSitioFacadeLocal;
import co.com.claro.mer.utils.FileToolUtils;
import co.com.claro.mer.utils.SesionUtil;
import co.com.claro.mgl.businessmanager.address.DrDireccionManager;
import co.com.claro.mgl.businessmanager.address.SolicitudManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.constantes.cobertura.Constants;
import co.com.claro.mgl.dtos.CmtDireccionDetalladaMglDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.DireccionesValidacionFacadeLocal;
import co.com.claro.mgl.facade.DrDireccionFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.NodoPoligonoFacadeLocal;
import co.com.claro.mgl.facade.ParametrosCallesFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.ResultModFacDirMglFacadeLocal;
import co.com.claro.mgl.facade.SolicitudFacadeLocal;
import co.com.claro.mgl.facade.TipoHhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtArchivosVtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtComponenteDireccionesMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionDetalleMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtExtendidaTecnologiaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtNotasSolicitudMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSolicitudCmMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSolicitudHhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSolicitudTipoSolicitudMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificioMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTiempoSolicitudMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoSolicitudMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.NodoPoligono;
import co.com.claro.mgl.jpa.entities.OpcionIdNombre;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.ResultModFacDirMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.TipoHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtArchivosVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTiempoSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.PrimeFacesUtil;
import co.com.claro.mgl.rest.dtos.CmtAddressGeneralDto;
import co.com.claro.mgl.rest.dtos.CmtAddressGeneralResponseDto;
import co.com.claro.mgl.rest.dtos.CmtAddressResponseDto;
import co.com.claro.mgl.rest.dtos.CmtAddressSuggestedDto;
import co.com.claro.mgl.rest.dtos.CmtConfiguracionDto;
import co.com.claro.mgl.rest.dtos.CmtConfigurationAddressComponentDto;
import co.com.claro.mgl.rest.dtos.CmtCoverDto;
import co.com.claro.mgl.rest.dtos.CmtDireccionDetalladaRequestDto;
import co.com.claro.mgl.rest.dtos.CmtDireccionRequestDto;
import co.com.claro.mgl.rest.dtos.CmtDireccionTabuladaDto;
import co.com.claro.mgl.rest.dtos.CmtRequestConstruccionDireccionDto;
import co.com.claro.mgl.rest.dtos.CmtRequestHhppByCoordinatesDto;
import co.com.claro.mgl.rest.dtos.CmtSuggestedNeighborhoodsDto;
import co.com.claro.mgl.utils.CmtUtilidadesAgenda;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.HhppUtils;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.mgl.ws.cm.request.RequestCreaSolicitud;
import co.com.claro.mgl.ws.cm.response.ResponseConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseCreaSolicitud;
import co.com.claro.mgl.ws.cm.response.ResponseGeograficoPolitico;
import co.com.claro.visitasTecnicas.business.NegocioParamMultivalor;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.ParamMultivalor;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.ResponseMessage;
import co.com.telmex.catastro.utilws.SecurityLogin;
import com.google.gson.Gson;
import com.jlcg.db.exept.ExceptionDB;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polygon;
import org.richfaces.component.util.Strings;
import javax.xml.bind.annotation.XmlTransient;

import co.com.claro.mer.utils.enums.DelimitadorEnum;
import co.com.claro.mer.utils.StringToolUtils;
import co.com.claro.mer.utils.constants.ConstantsDirecciones;
import co.com.claro.mer.utils.constants.ConstantsSolicitudHhpp;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.facade.ModificacionDirFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.ConfigurationAddressComponent;
import co.com.claro.mgl.jpa.entities.ModificacionDir;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;

import static co.com.claro.mer.utils.constants.MicrositioConstants.*;
import static co.com.claro.mgl.mbeans.vt.solicitudes.GestionSolicitudBean.DEFAULT_TIME;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.telmex.catastro.data.AddressService;

import org.apache.commons.lang.StringUtils;

/**
 * Managed Bean utilizado para realizar <b>Consultas de Coberturas</b>
 * por distintos criterios.
 *
 * @author Victor Bocanegra (<i>bocanegravm</i>).
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
@ManagedBean(name = "consultarCoberturasBean")
@ViewScoped
public class ConsultarCoberturasBean implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(ConsultarCoberturasBean.class);

    /**
     * Flag que determina si se realizar&aacute; <b>Responsive</b> a los
     * DataTable.
     */
    private final boolean DATATABLE_RESPONSIVE_ENABLED = false;
    /**
     * Flag que determina si es requerido obtener informaci&oacute;n adicional
     * del HHPP.
     */
    private final boolean OBTENER_INFORMACION_ADICIONAL_HHPP = false;

    /**
     * Cantidad m&aacute;xima de decimales, usado en Coordenadas.
     */
    private final int CANTIDAD_DECIMALES_COORDENADA = 9;
    /**
     * Cantidad m&aacute;xima de unidades que ser&aacute;n consultadas en la
     * b&uacute;squeda de aproximaci&oacute;n por Coordenadas.
     */
    private final int MAX_UNITS_NUMBER = 999999;

    /**
     * Constante asociada a la funcionalidad <b>Ingresar Direcci&oacute;n</b>.
     */
    private final int FUNCIONALIDAD_INGRESAR_DIRECCION = 1;
    /**
     * Constante asociada a la funcionalidad <b>Punto Ubicaci&oacute;n</b>.
     */
    private final int FUNCIONALIDAD_PUNTO_UBICACION = 2;
    /**
     * Constante asociada a la funcionalidad <b>GPS</b>.
     */
    private final int FUNCIONALIDAD_GPS = 3;
    /**
     * Constante asociada a la funcionalidad <b>Ingresar Coordenadas</b>.
     */
    private final int FUNCIONALIDAD_INGRESAR_COORDENADAS = 4;
	

    private boolean viabilidadGestionAutomatica = false;
    private String espacio = "&nbsp;";
    private String departamento;
    private String ciudad;
    private String comunidad;
    private String division;
    private String tipoDireccion;
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
    private String complemento;
    private String direccionLabel;
    private String createDireccion;
    private String barrioSugerido;
    private String barrioSugeridoStr;

    // SOLICITUD HHPP
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    private boolean habilitarRR;
    private UploadedFile upFile;
    private Solicitud solicitud = new Solicitud();
    private String usuario;
    private String perfilVt;
    private boolean soporteRequerido = false;
    private boolean mostrarSolicitudHhpp = false;
    private boolean mostrarSolicitudHhppCcmm = false;
    private List<ParametrosCalles> areaCanalList;
    private String canalHhpp;
    private String tipoViviendaSeleccionada;
    private List<TipoHhppMgl> tipoViviendaList;
    private String nombreContacto;
    private String telefonoContacto;
    private String telefonoSolicitante;
    private String nombreSolicitante;
    private String correoSolicitante;
    private String tipoVivienda;
    private String areaCanal;
    private String observacionesSolicitud;
    private String archivoSoporte;
    private String tecnologiaSeleccionada;
    private String nombreUsuario;
    private String correoUsuario;
    private String idUsuario;
    private BigDecimal tecnologiaIdSeleccionada;
    Solicitud solicitudCreated = new Solicitud();
    GeograficoPoliticoMgl ciudadCompleto;
    GeograficoPoliticoMgl centroPobladoCompleto;
    GeograficoPoliticoMgl departamentoCompleto;
    private String direccionSeleccionada;
    private CmtBasicaMgl tecnologiaSeleccionadaBasica;
    private boolean solicitudCreada = false;
    private CmtTipoBasicaMgl tipoBasicaTipoTecnologia;
    private CmtCuentaMatrizMgl cuentaMatrizSeleccionada;
    private List<CmtSubEdificioMgl> subEdificioList;
    private List<CmtBasicaMgl> origenSolicitudList;
    private BigDecimal subEdificioSeleccionado;
    private String asesorCcmm;
    private String correoAsesor;
    private String correoCopiaA;
    private BigDecimal origenSolicitud;
    CmtSolicitudCmMgl solicitudModCM = new CmtSolicitudCmMgl();
    SolicitudManager solicitudManager = new SolicitudManager();

	
    //////
    private String longitud;
    private String latitud;
    private List<ParamMultivalor> departamentoList;
    private List<ParamMultivalor> ciudadesList;
    private List<ParamMultivalor> centroPobladoList;
    private final RequestConstruccionDireccion request
            = new RequestConstruccionDireccion();
    private List<OpcionIdNombre> ckList = new ArrayList<>();
    private List<OpcionIdNombre> bmList = new ArrayList<>();
    private List<OpcionIdNombre> itList = new ArrayList<>();
    private List<OpcionIdNombre> aptoList = new ArrayList<>();
    private List<OpcionIdNombre> aptoListVisor = new ArrayList<>();
    private List<OpcionIdNombre> ckLetras = new ArrayList<>();
    private List<OpcionIdNombre> ckBis = new ArrayList<>();
    private List<OpcionIdNombre> cardinalesList = new ArrayList<>();
    private List<OpcionIdNombre> cruceslist = new ArrayList<>();
    private List<OpcionIdNombre> complementoList = new ArrayList<>();
    private DrDireccion drDireccion = new DrDireccion();
    private ResponseConstruccionDireccion responseConstruirDireccion
            = new ResponseConstruccionDireccion();
    private ResponseGeograficoPolitico responsesCentroPoblado;
    private String regresarMenu = "<- Regresar Menú";
    private String pageActual;
    private String pageActualCob;
    private String numPagina = "1";
    private String numPaginaCob = "1";
    private List<Integer> paginaList;
    private int actual;
    private List<Integer> paginaListCob;
    private int actualCob;
    private final int filasPag = Constants.PAGINACION_DIEZ_FILAS;
    private int filasPag5 = Constants.PAGINACION_CINCO_FILAS;
    private List<CmtDireccionDetalladaMglDto> direccionDetalladaList;
    private List<CmtDireccionDetalladaMglDto> direccionDetalladaListAux;
    /**
     * Lista resultante de filtrar las direcciones desde el componente.
     */
    private List<CmtDireccionDetalladaMglDto> filteredDireccionDetalladaList;
    private CmtAddressGeneralResponseDto responsesDirecciones;
    private CmtDireccionRequestDto requestDirecciones;
    private String idCentroPoblado;
    private CmtSuggestedNeighborhoodsDto neighborhoodsDto;
    private List<CmtAddressSuggestedDto> barrioSugeridoList;
    private final CmtRequestConstruccionDireccionDto requestBarrios
            = new CmtRequestConstruccionDireccionDto();
    private boolean select;
    private boolean mostrarMapa = false;
    private String center;
    /**
     * Direcci&oacute;n que fue seleccionada desde la tabla.
     */
    private CmtDireccionDetalladaMglDto direccionDetalladaMglDtoSel;
    private List<CmtCoverDto> listCover;
    private List<CmtCoverDto> listCoverAux;
    private boolean showPanelBusquedaCentro = false;
    private boolean showPanelCoordenadas = false;
    private String volverPanelInicial = "";

    /**
     * Flag asociado a la funcionalidad seleccionada a trav&eacute;s del
     * men&uacute; del aplicativo.
     */
    private int opcionSeleccionada;
    /**
     * Registro de Cobertura seleccionado.
     */
    private CmtCoverDto coberturaSeleccionada;
    private Map<String, NodoMgl> mapNodos;
    /**
     * Flag que determina si se encuentra habilitada la selecci&oacute;n de
     * puntos en el Mapa.
     */
    private boolean pointSelectEnabled = false;
    private boolean showCKPanel;
    private boolean showBMPanel;
    private boolean showITPanel;
    private boolean showFooter;
    private boolean showPanelBusquedaDireccion = false;
    private boolean showPanelDirecciones = false;
    private boolean notGeoReferenciado = true;
    private boolean showBarrio;
    private boolean direccionConstruida;
    private boolean barrioSugeridoField;
    /**
     * Flag que determina si se habilitar&aacute;n los input de Coordenadas.
     */
    private boolean coordinatesEnabled;
    private MapModel model = new DefaultMapModel();

    /**
     * Contexto Faces.
     */
    private final FacesContext facesContext = FacesContext.getCurrentInstance();
    /**
     * Usuario de la Sesi&oacute;n.
     */
    private String usuarioSesion;
    private String rutaServlet;
    private String tokenFactibilidades;
    private boolean verMapa;
    private final HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private final HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String msnError;
    private LatLng latLng;
    
    private DireccionRREntity direccionRREntity;
    private DetalleDireccionEntity detalleDireccionEntity;
    
    private List<UnidadStructPcml> unidadModificadaList = new ArrayList<>();
    private List<UnidadStructPcml> unidadModificadaTemporalList = new ArrayList<>();
    private List<UnidadStructPcml> unidadConflictoList = new ArrayList<>();
    private List<UnidadStructPcml> unidadConflictoTmpList = new ArrayList<>();
    private CmtTiempoSolicitudMgl cmtTiempoSolicitudMglToCreate = new CmtTiempoSolicitudMgl();
    
    private String tmpInicio;
    private String deltaTiempo;
    private String tmpFin;
    private String timeSol;
    private Date dateInicioCreacion;
    
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;    
    @EJB
    private ModificacionDirFacadeLocal modificacionDirFacadeLocal;   
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoFacadeLocal;
    @EJB
    private CmtComponenteDireccionesMglFacadeLocal cmtComponenteDireccionesMglFacadeLocal;
    @EJB
    private DireccionesValidacionFacadeLocal direccionesValidacionFacadeLocal;

    @EJB
    private DrDireccionFacadeLocal drDireccionFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    @EJB
    private CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMglFacadeLocal;
    @EJB
    private NodoPoligonoFacadeLocal nodoPoligonoFacadeLocal;
    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;
    @EJB
    private HhppMglFacadeLocal hhppMglFacadeLocal;
    @EJB
    private ResultModFacDirMglFacadeLocal resultModFacDirMglFacadeLocal;
    //Solicitud HHPP
    @EJB
    private CmtExtendidaTecnologiaMglFacadeLocal cmtExtendidaTecnologiaMglFacadeLocal;
    @EJB
    private SolicitudFacadeLocal solicitudFacadeLocal;
    @EJB
    private CmtTiempoSolicitudMglFacadeLocal cmtTiempoSolicitudMglFacadeLocal;
    @EJB
    private CmtSolicitudTipoSolicitudMglFacadeLocal cmtSolicitudTipoSolicitudMglFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal paramentrosMglFacadeLocal;
    @EJB
    private ParametrosCallesFacadeLocal parametrosCallesFacade;
    @EJB
    private TipoHhppMglFacadeLocal tipoHhppMglFacadeLocal;
    @EJB
    private CmtArchivosVtMglFacadeLocal archivosVtMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtDireccionMglFacadeLocal cmtDireccionMglFacadeLocal;
    @EJB
    private CmtSolicitudCmMglFacadeLocal solicitudCcmmFacadeLocal;
    @EJB
    private CmtSolicitudHhppMglFacadeLocal cmtSolicitudHhppMglFacadeLocal;
    @EJB
    private CmtNotasSolicitudMglFacadeLocal notasSolicitudMglFacadeLocal;
    @EJB
    private CmtTipoSolicitudMglFacadeLocal tipoSolicitudFacadeLocal;
    @EJB
    private CmtSubEdificioMglFacadeLocal cmtSubEdificioMglFacadeLocal;
    @EJB
    private LoginMicroSitioFacadeLocal loginMicroSitioFacadeLocal;
	
    /**
     * Instrucciones que se ejecutan al momento posterior de construir el
     * objeto.
     */
    @PostConstruct
    private void init() {

	   
        showPanelBusquedaDireccion = false;
        showPanelDirecciones = false;
        showPanelBusquedaCentro = false;
        showPanelCoordenadas = false;
        volverPanelInicial = "";
        mostrarMapa = false;
        this.coordinatesEnabled = true;
        mostrarSolicitudHhpp = false;
        mostrarSolicitudHhppCcmm = false;
        actual = 1;
        actualCob = 1;
        this.opcionSeleccionada = 0;

        this.mapNodos = new HashMap<>();

        try {
            SecurityLogin securityLogin = new SecurityLogin(facesContext);
            //JDHT ajuste realizado por cvillamil para iframe
            Map<String, String> params = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap();
            String seguridad = params.get("security");
            if (seguridad != null && !seguridad.isEmpty()) {
                seguridad = seguridad.replace(" ", "+");
            }

            String security = CmtUtilidadesAgenda.Desencriptar(seguridad);

            security = security.replace("|", ":");
            String paramQueryString[] = security.split(":");

            if (paramQueryString == null) {
                response.sendRedirect(securityLogin.redireccionarLogin());
                return;
            }

            if (paramQueryString.length != 8) {
                response.sendRedirect(securityLogin.redireccionarLogin());
                return;
            }

            String token = paramQueryString[0];
            String vermapa = paramQueryString[2];
            usuario = paramQueryString[3];
            perfilVt = paramQueryString[4];
            nombreUsuario = paramQueryString[5];
            correoUsuario = paramQueryString[6];
            idUsuario = paramQueryString[7];

            ResultModFacDirMgl resultModFacDirMgl = resultModFacDirMglFacadeLocal.findBytoken(token);
            if (resultModFacDirMgl != null) {
                String msgError = "El token de micrositio ya fue utilizado";
                LOGGER.warn(msgError);
                session.setAttribute(ERROR_ATTRIBUTE, 5);
                session.setAttribute(MENSAJE_EXEPCION, msgError);
                String urlContext = Optional.ofNullable(facesContext)
                        .map(FacesContext::getExternalContext)
                        .map(ExternalContext::getRequest)
                        .map(e -> (HttpServletRequest) e)
                        .map(HttpServletRequest::getContextPath)
                        .orElse("");

                if (StringUtils.isNotBlank(urlContext)) {
                    response.sendRedirect(urlContext + URL_DE_RESPUESTA_ERROR);
                    return;
                }

                response.sendRedirect(securityLogin.redireccionarLogin());
                return;
            }

            boolean verMapa = false;
            if (vermapa != null && vermapa.equalsIgnoreCase("true")) {
                verMapa = true;
            }

            usuarioSesion = null;
            session.setAttribute("verMapa", verMapa);
            session.setAttribute("token", token);

            if (token == null || token.isEmpty()) {
                if (usuarioSesion == null) {
                    response.sendRedirect(SecurityLogin.SERVER_PARA_AUTENTICACION);
                }
            }

            if (usuarioSesion == null) {
                usuarioSesion = usuario;
                if (usuarioSesion == null || usuarioSesion.isEmpty()) {
                    response.sendRedirect(SecurityLogin.SERVER_PARA_AUTENTICACION);
                }
            }

			
			
            if (session.getAttribute("verMapa") != null) {
                verMapa = (boolean) session.getAttribute("verMapa");
                session.removeAttribute("verMapa");
            }
            if (session.getAttribute("token") != null) {
                tokenFactibilidades = (String) session.getAttribute("token");
                session.removeAttribute("token");
            } else {
                response.sendRedirect(securityLogin.redireccionarLogin());
                return;
            }
            session.setAttribute("cookieXml", seguridad);
            obtenerDepartamentoList();
            if (verMapa) {
                rutaServlet = this.consultarParametro(Constant.RUTA_MAPA_GOOGLE);
            }
					 
								   

            habilitarRR = false;

            //Valida si RR se encuentra encendido o apagado para realizar las operaciones en RR                 
            ParametrosMgl parametroHabilitarRR = paramentrosMglFacadeLocal.findParametroMgl(Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }
            cmtTiempoSolicitudMglFacadeLocal.setUser(usuario, Integer.parseInt(perfilVt));
            cmtSolicitudTipoSolicitudMglFacadeLocal.setUser(usuario, Integer.parseInt(perfilVt));
            tipoViviendaList = tipoHhppMglFacadeLocal.findAll();
            obtenerListadoAreaCanalList();
            tipoBasicaTipoTecnologia = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TECNOLOGIA);

            CmtTipoBasicaMgl tipoBasicaOrigenSolicitud;
            tipoBasicaOrigenSolicitud = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_ORIGEN_SOLICITUD);
            origenSolicitudList = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaOrigenSolicitud);
            CompletableFuture.runAsync(this::registerLogin);

        } catch (IOException | ApplicationException e) {
            LOGGER.error("Se produjo un error al momento de consultar la información de inicio: " + e.getMessage(), e);

        }
        showCK();

    }

	
    /**
     * Constructor.
     */
    public ConsultarCoberturasBean() {

    }

    /**
     * Registra el login del usuario en el histórico de sesiones de micrositio.
     * @author Gildardo Mora
     */
    private void registerLogin() {
        try {
            loginMicroSitioFacadeLocal.registerUserLogin(usuarioSesion, perfilVt);
        } catch (ApplicationException e) {
            String msgError = "Error al registrar el loginde micrositio en el histórico de sesiones.";
            LOGGER.error(msgError, e);
        }
    }

    /**
     * Funcion utilizada para obtener los listados utilizados en la pantalla
     * desde la base de datos para creacion hhpp de barrio abierto desde visor
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Johan Gómez
     */
    public void cargarListadosComplVisor() throws ApplicationException {
        try {
            //Obtiene lista de listas de los componentes de la dirección.          
            
            ConfigurationAddressComponent addressComponent
                = cmtComponenteDireccionesMglFacadeLocal.getConfiguracionComponente(tecnologiaSeleccionadaBasica.getAbreviatura(),
                            tecnologiaSeleccionadaBasica.getBasicaId());
            
            aptoListVisor = cmtComponenteDireccionesMglFacadeLocal
                    .obtenerApartamentoList(addressComponent);

            addressComponent.getAptoValues();

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al realizar consultas para obtener configuración "
                    + "de listados" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al realizar consultas para obtener configuración "
                    + "de listados" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Muestra el Panel de Consulta por <b>Direcci&oacute;n</b>.
     *
     * @throws ApplicationException
     */
    public void verPanelDireccion() throws ApplicationException {

        volverPanelInicial = "verPanelDireccion";
        // Marcar la opcion seleccionada:
        this.opcionSeleccionada = FUNCIONALIDAD_INGRESAR_DIRECCION;

        // Deshabilitar el evento Point Select:
        this.pointSelectEnabled = false;

        showPanelBusquedaDireccion = true;
        showPanelBusquedaCentro = false;
        showPanelCoordenadas = true;
        mostrarSolicitudHhpp = false;
        mostrarSolicitudHhppCcmm = false;
        ciudadesList = new ArrayList<>();
        centroPobladoList = new ArrayList<>();
        responsesCentroPoblado = null;
        mostrarMapa = false;
        limpiarPanelDir();
        limpiarBusqueda();

		
        String tipoRed = "";

        try {
            tipoRed = consultarParametro(Constants.TIPO_RED_CONSULTA);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("No se ha configurado el parametro para consultar los componentes de dirección, "
                    + "por favor establezca el registro 'TIPO_RED_CONSULTA'.", e);
            throw e;
        }

		
        // Obtener la informacion de direcciones:
        CmtConfiguracionDto request = new CmtConfiguracionDto();
        request.setTipoRed(tipoRed);

        CmtConfigurationAddressComponentDto addressComponent
                = cmtComponenteDireccionesMglFacadeLocal.getConfiguracionComponente(request);
        complementoList = new ArrayList();
        aptoList = new ArrayList();
        aptoListVisor = new ArrayList();
        bmList = new ArrayList();
        itList = new ArrayList();
        ckList = new ArrayList();
        cruceslist = new ArrayList();
        cardinalesList = new ArrayList();
        ckBis = new ArrayList();
        ckLetras = new ArrayList();

        // Convertir objeto a JSON:
        JSONObject jsonObj = this.objectToJSONObject(addressComponent);

        String typeMen = jsonObj.getString("messageType");
        String mensaje = jsonObj.getString("message");

        if (typeMen.equalsIgnoreCase("I")) {
            JSONObject complementosP = jsonObj.getJSONObject("complementoValues");

            //Complementos de direccion
            JSONArray jArray = complementosP.getJSONArray("complementoDir");

            if (jArray != null && jArray.length() > 0) {

                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject json_obj = jArray.getJSONObject(i);
                    OpcionIdNombre compo = new OpcionIdNombre();
                    compo.setIdParametro(json_obj.getString("idParametro"));
                    compo.setDescripcion(json_obj.getString("descripcion"));
                    compo.setIdTipo(json_obj.getString("idTipo"));
                    complementoList.add(compo);
                }

            }
            // Ordenar el listado de complementos:
            Collections.sort(complementoList);
            //Complementos de direccion

            //Valores apartamentos
            JSONObject aptoVal = jsonObj.getJSONObject("aptoValues");

            JSONArray tiposApto = aptoVal.getJSONArray("tiposApto");
            if (tiposApto != null && tiposApto.length() > 0) {

                for (int i = 0; i < tiposApto.length(); i++) {

                    JSONObject json_obj = tiposApto.getJSONObject(i);
                    OpcionIdNombre tipA = new OpcionIdNombre();
                    tipA.setIdParametro(json_obj.getString("idParametro"));
                    tipA.setDescripcion(json_obj.getString("descripcion"));
                    tipA.setIdTipo(json_obj.getString("idTipo"));
                    aptoList.add(tipA);
                }
            }

            JSONArray tiposAptoComp = aptoVal.getJSONArray("tiposAptoComplemento");
            if (tiposAptoComp != null && tiposAptoComp.length() > 0) {
                for (int i = 0; i < tiposAptoComp.length(); i++) {

                    JSONObject json_obj = tiposAptoComp.getJSONObject(i);
                    OpcionIdNombre tipAComp = new OpcionIdNombre();
                    tipAComp.setIdParametro(json_obj.getString("idParametro"));
                    tipAComp.setDescripcion(json_obj.getString("descripcion"));
                    tipAComp.setIdTipo(json_obj.getString("idTipo"));
                    aptoList.add(tipAComp);
                }
            }
            // Ordenar el listado de apartamentos:
            Collections.sort(aptoList);
            //Valores apartamentos

            //Valores barrios manzana
            JSONObject bmVal = jsonObj.getJSONObject("bmValues");

            JSONArray tipoConjuntoBm = bmVal.getJSONArray("tipoConjuntoBm");
            if (tipoConjuntoBm != null && tipoConjuntoBm.length() > 0) {
                for (int i = 0; i < tipoConjuntoBm.length(); i++) {

                    JSONObject json_obj = tipoConjuntoBm.getJSONObject(i);
                    OpcionIdNombre tipCbm = new OpcionIdNombre();
                    tipCbm.setIdParametro(json_obj.getString("idParametro"));
                    tipCbm.setDescripcion(json_obj.getString("descripcion"));
                    tipCbm.setIdTipo(json_obj.getString("idTipo"));
                    bmList.add(tipCbm);
                }

            }
            JSONArray subdivision1Bm = bmVal.getJSONArray("subdivision1Bm");
            if (subdivision1Bm != null && subdivision1Bm.length() > 0) {
                for (int i = 0; i < subdivision1Bm.length(); i++) {

                    JSONObject json_obj = subdivision1Bm.getJSONObject(i);
                    OpcionIdNombre sub1Bm = new OpcionIdNombre();
                    sub1Bm.setIdParametro(json_obj.getString("idParametro"));
                    sub1Bm.setDescripcion(json_obj.getString("descripcion"));
                    sub1Bm.setIdTipo(json_obj.getString("idTipo"));
                    bmList.add(sub1Bm);
                }

            }
            JSONArray subdivision2Bm = bmVal.getJSONArray("subdivision2Bm");
            if (subdivision2Bm != null && subdivision2Bm.length() > 0) {
                for (int i = 0; i < subdivision2Bm.length(); i++) {

                    JSONObject json_obj = subdivision2Bm.getJSONObject(i);
                    OpcionIdNombre sub2Bm = new OpcionIdNombre();
                    sub2Bm.setIdParametro(json_obj.getString("idParametro"));
                    sub2Bm.setDescripcion(json_obj.getString("descripcion"));
                    sub2Bm.setIdTipo(json_obj.getString("idTipo"));
                    bmList.add(sub2Bm);
                }

            }
            JSONArray subdivision3Bm = bmVal.getJSONArray("subdivision3Bm");
            if (subdivision3Bm != null && subdivision3Bm.length() > 0) {
                for (int i = 0; i < subdivision3Bm.length(); i++) {

                    JSONObject json_obj = subdivision3Bm.getJSONObject(i);
                    OpcionIdNombre sub3Bm = new OpcionIdNombre();
                    sub3Bm.setIdParametro(json_obj.getString("idParametro"));
                    sub3Bm.setDescripcion(json_obj.getString("descripcion"));
                    sub3Bm.setIdTipo(json_obj.getString("idTipo"));
                    bmList.add(sub3Bm);
                }

            }
            JSONArray subdivision4Bm = bmVal.getJSONArray("subdivision4Bm");
            if (subdivision4Bm != null && subdivision4Bm.length() > 0) {
                for (int i = 0; i < subdivision4Bm.length(); i++) {

                    JSONObject json_obj = subdivision4Bm.getJSONObject(i);
                    OpcionIdNombre sub4Bm = new OpcionIdNombre();
                    sub4Bm.setIdParametro(json_obj.getString("idParametro"));
                    sub4Bm.setDescripcion(json_obj.getString("descripcion"));
                    sub4Bm.setIdTipo(json_obj.getString("idTipo"));
                    bmList.add(sub4Bm);
                }

            }
            //Valores barrios manzana

            //Valores intraducibles
            JSONObject itValues = jsonObj.getJSONObject("itValues");

            JSONArray intr1It = itValues.getJSONArray("intr1It");
            if (intr1It != null && intr1It.length() > 0) {
                for (int i = 0; i < intr1It.length(); i++) {

                    JSONObject json_obj = intr1It.getJSONObject(i);
                    OpcionIdNombre intr1It1 = new OpcionIdNombre();
                    intr1It1.setIdParametro(json_obj.getString("idParametro"));
                    intr1It1.setDescripcion(json_obj.getString("descripcion"));
                    intr1It1.setIdTipo(json_obj.getString("idTipo"));
                    itList.add(intr1It1);
                }

            }
            JSONArray intr2It = itValues.getJSONArray("intr2It");
            if (intr2It != null && intr2It.length() > 0) {
                for (int i = 0; i < intr2It.length(); i++) {

                    JSONObject json_obj = intr2It.getJSONObject(i);
                    OpcionIdNombre intr1It2 = new OpcionIdNombre();
                    intr1It2.setIdParametro(json_obj.getString("idParametro"));
                    intr1It2.setDescripcion(json_obj.getString("descripcion"));
                    intr1It2.setIdTipo(json_obj.getString("idTipo"));
                    itList.add(intr1It2);
                }

            }

            JSONArray intr3It = itValues.getJSONArray("intr3It");
            if (intr3It != null && intr3It.length() > 0) {
                for (int i = 0; i < intr3It.length(); i++) {

                    JSONObject json_obj = intr3It.getJSONObject(i);
                    OpcionIdNombre intr1It3 = new OpcionIdNombre();
                    intr1It3.setIdParametro(json_obj.getString("idParametro"));
                    intr1It3.setDescripcion(json_obj.getString("descripcion"));
                    intr1It3.setIdTipo(json_obj.getString("idTipo"));
                    itList.add(intr1It3);
                }

            }

            JSONArray intr4It = itValues.getJSONArray("intr4It");
            if (intr4It != null && intr4It.length() > 0) {
                for (int i = 0; i < intr4It.length(); i++) {

                    JSONObject json_obj = intr4It.getJSONObject(i);
                    OpcionIdNombre intr1It4 = new OpcionIdNombre();
                    intr1It4.setIdParametro(json_obj.getString("idParametro"));
                    intr1It4.setDescripcion(json_obj.getString("descripcion"));
                    intr1It4.setIdTipo(json_obj.getString("idTipo"));
                    itList.add(intr1It4);
                }

            }

            JSONArray intr5It = itValues.getJSONArray("intr5It");
            if (intr5It != null && intr5It.length() > 0) {
                for (int i = 0; i < intr5It.length(); i++) {

                    JSONObject json_obj = intr5It.getJSONObject(i);
                    OpcionIdNombre intr1It5 = new OpcionIdNombre();
                    intr1It5.setIdParametro(json_obj.getString("idParametro"));
                    intr1It5.setDescripcion(json_obj.getString("descripcion"));
                    intr1It5.setIdTipo(json_obj.getString("idTipo"));
                    itList.add(intr1It5);
                }
            }

            JSONArray intr6It = itValues.getJSONArray("intr6It");
            if (intr6It != null && intr6It.length() > 0) {
                for (int i = 0; i < intr6It.length(); i++) {

                    JSONObject json_obj = intr6It.getJSONObject(i);
                    OpcionIdNombre intr1It6 = new OpcionIdNombre();
                    intr1It6.setIdParametro(json_obj.getString("idParametro"));
                    intr1It6.setDescripcion(json_obj.getString("descripcion"));
                    intr1It6.setIdTipo(json_obj.getString("idTipo"));
                    itList.add(intr1It6);
                }

            }

            JSONArray placaIt = itValues.getJSONArray("placaIt");
            if (placaIt != null && placaIt.length() > 0) {
                for (int i = 0; i < placaIt.length(); i++) {

                    JSONObject json_obj = placaIt.getJSONObject(i);
                    OpcionIdNombre placaIt1 = new OpcionIdNombre();
                    placaIt1.setIdParametro(json_obj.getString("idParametro"));
                    placaIt1.setDescripcion(json_obj.getString("descripcion"));
                    placaIt1.setIdTipo(json_obj.getString("idTipo"));
                    itList.add(placaIt1);
                }

            }
            //Valores intraducibles

            //Valores tipo via principal
            JSONObject ckValues = jsonObj.getJSONObject("ckValues");

            JSONArray tiposDeViaPrinCK = ckValues.getJSONArray("tiposDeViaPrinCK");

            if (tiposDeViaPrinCK != null && tiposDeViaPrinCK.length() > 0) {
                for (int i = 0; i < tiposDeViaPrinCK.length(); i++) {

                    JSONObject json_obj = tiposDeViaPrinCK.getJSONObject(i);
                    OpcionIdNombre tiposDeViaPrinCK1 = new OpcionIdNombre();
                    tiposDeViaPrinCK1.setIdParametro(json_obj.getString("idParametro"));
                    tiposDeViaPrinCK1.setDescripcion(json_obj.getString("descripcion"));
                    tiposDeViaPrinCK1.setIdTipo(json_obj.getString("idTipo"));
                    ckList.add(tiposDeViaPrinCK1);
                }

            }
            //Valores tipo via principal

            //Valores letras via principal
            JSONArray letrasCK = ckValues.getJSONArray("letrasCK");

            if (letrasCK != null && letrasCK.length() > 0) {
                for (int i = 0; i < letrasCK.length(); i++) {

                    JSONObject json_obj = letrasCK.getJSONObject(i);
                    OpcionIdNombre letrasCK1 = new OpcionIdNombre();
                    letrasCK1.setIdParametro(json_obj.getString("idParametro"));
                    letrasCK1.setDescripcion(json_obj.getString("descripcion"));
                    letrasCK1.setIdTipo(json_obj.getString("idTipo"));
                    ckLetras.add(letrasCK1);
                }

            }

            JSONArray letrasBis = ckValues.getJSONArray("letrasCK");

            if (letrasBis != null && letrasBis.length() > 0) {

                OpcionIdNombre Bis1 = new OpcionIdNombre();
                Bis1.setIdParametro("BIS");
                Bis1.setDescripcion("BIS");
                Bis1.setIdTipo("BIS");
                ckBis.add(Bis1);

                for (int i = 0; i < letrasBis.length(); i++) {

                    JSONObject json_obj = letrasBis.getJSONObject(i);
                    OpcionIdNombre letrasBis1 = new OpcionIdNombre();
                    letrasBis1.setIdParametro(json_obj.getString("idParametro"));
                    letrasBis1.setDescripcion(json_obj.getString("descripcion"));
                    letrasBis1.setIdTipo(json_obj.getString("idTipo"));
                    ckBis.add(letrasBis1);
                }

            }
            //Valores letras via principal

            //Valores cardinales
            JSONArray cardinalesCK = ckValues.getJSONArray("cardinalesCK");

            if (cardinalesCK != null && cardinalesCK.length() > 0) {
                for (int i = 0; i < cardinalesCK.length(); i++) {

                    JSONObject json_obj = cardinalesCK.getJSONObject(i);
                    OpcionIdNombre cardinalesCK1 = new OpcionIdNombre();
                    cardinalesCK1.setIdParametro(json_obj.getString("idParametro"));
                    cardinalesCK1.setDescripcion(json_obj.getString("descripcion"));
                    cardinalesCK1.setIdTipo(json_obj.getString("idTipo"));
                    cardinalesList.add(cardinalesCK1);
                }

            }
            //Valores cardinales

            //Valores cruces
            JSONArray cruceCK = ckValues.getJSONArray("cruceCK");

            if (cruceCK != null && cruceCK.length() > 0) {
                for (int i = 0; i < cruceCK.length(); i++) {

                    JSONObject json_obj = cruceCK.getJSONObject(i);
                    OpcionIdNombre cruceCK1 = new OpcionIdNombre();
                    cruceCK1.setIdParametro(json_obj.getString("idParametro"));
                    cruceCK1.setDescripcion(json_obj.getString("descripcion"));
                    cruceCK1.setIdTipo(json_obj.getString("idTipo"));
                    cruceslist.add(cruceCK1);
                }

            }
            //Valores cruces   
        } else {
            msnError = mensaje;
            alertProcesoExistoso();

        }

    }
	

    private void alerts() {
        PrimeFaces current = PrimeFaces.current();
        PrimeFacesUtil.update("@widgetVar(dlg1)");
        current.executeScript("PF('dlg1').show();");

    }

    public void alertProcesoExistoso() {
        PrimeFaces current = PrimeFaces.current();
        PrimeFacesUtil.update("@widgetVar(dlg1Exitoso)");
        current.executeScript("PF('dlg1Exitoso').show();");
    }
	
   
	

    /**
     * Función que extrae los valores tipo dirección y los agrupa en un solo
     * listado.
     *
     * @author Victor Bocanegra
     */
    public void validarTipoDireccion() {

        switch (tipoDireccion) {
            case "CK":
                limpiarCamposTipoDireccion();
                showCK();
                break;
            case "BM":
                limpiarCamposTipoDireccion();
                showBM();
                break;
            case "IT":
                limpiarCamposTipoDireccion();
                showIT();
                break;
            case "":
                limpiarCamposTipoDireccion();
                break;
            default:
                break;
        }

    }

    /**
     * Función que limpiar los valores de la pantalla de tipo dirección
     *
     * @author Victor Bocanegra
     */
    public void limpiarCamposTipoDireccion() {
        //drDireccion = new DrDireccion();
        //responseConstruirDireccion.setDrDireccion(null);
        direccionCk = "";
        nivelValorIt = "";
        nivelValorBm = "";
        tipoNivelBm = "";
        tipoNivelIt = "";
        tipoNivelApartamento = "";
        tipoNivelComplemento = "";
        direccionLabel = "";
        notGeoReferenciado = true;
        direccionConstruida = false;
        createDireccion = "";
        showBarrio = false;
        barrioSugerido = "";
        barrioSugeridoField = false;
        barrioSugeridoStr = "";
        mostrarSolicitudHhpp = false;
        mostrarSolicitudHhppCcmm = false;
    }

    /**
     * Funci&oacute;n que limpiar los valores de la pantalla
     */
    public void limpiarBusqueda() {

        // Reiniciar todos los campos diligenciados en el formulario:
        PrimeFaces.current().resetInputs("formPer:centroBusqueda");
        PrimeFaces.current().resetInputs("formPer:contenidoBusqueda");
        PrimeFaces.current().resetInputs("formPer:panelCoordenadas");

        drDireccion = new DrDireccion();
        responseConstruirDireccion.setDrDireccion(null);
        direccionCk = "";
        nivelValorIt = "";
        nivelValorBm = "";
        tipoNivelBm = "";
        tipoNivelIt = "";
        tipoNivelApartamento = "";
        tipoNivelComplemento = "";
        direccionLabel = "";
        ciudad = null;
        departamento = "";
        idCentroPoblado = null;
        division = "";
        comunidad = "";
        tipoDireccion = "CK";
        complemento = "";
        apartamento = "";
        direccionDetalladaList = new ArrayList();
        filteredDireccionDetalladaList = null;
        this.coberturaSeleccionada = null;
        showFooter = false;
        showPanelBusquedaDireccion = true;
        showPanelDirecciones = false;
        notGeoReferenciado = true;
        direccionConstruida = false;
        createDireccion = "";
        showBarrio = false;
        barrioSugerido = "";
        barrioSugeridoField = false;
        barrioSugeridoStr = "";
        longitud = "";
        latitud = "";
        mostrarMapa = false;
        direccionDetalladaMglDtoSel = null;
        showCK();
        mostrarSolicitudHhpp = false;
        mostrarSolicitudHhppCcmm = false;

    }

    /**
     * Función que limpiar los valores de la pantalla
     *
     * @author Victor Bocanegra
     */
    public void limpiarPanelDir() {
        drDireccion = new DrDireccion();
        responseConstruirDireccion.setDrDireccion(null);
        direccionCk = "";
        nivelValorIt = "";
        nivelValorBm = "";
        tipoNivelBm = "";
        tipoNivelIt = "";
        tipoNivelApartamento = "";
        tipoNivelComplemento = "";
        direccionLabel = "";
        ciudad = null;
        departamento = "";
        idCentroPoblado = null;
        division = "";
        comunidad = "";
        tipoDireccion = "CK";
        complemento = "";
        apartamento = "";
        direccionDetalladaList = new ArrayList();
        filteredDireccionDetalladaList = null;
        showFooter = false;
        notGeoReferenciado = true;
        direccionConstruida = false;
        createDireccion = "";
        showBarrio = false;
        barrioSugerido = "";
        barrioSugeridoField = false;
        barrioSugeridoStr = "";
        longitud = "";
        latitud = "";
        center = "";
        direccionDetalladaMglDtoSel = null;
        listCover = new ArrayList<>();
        this.coberturaSeleccionada = null;
        this.coordinatesEnabled = true;
        showCK();
        mostrarSolicitudHhpp = false;
        mostrarSolicitudHhppCcmm = false;

    }

    /**
     * Función que realiza la busqueda de direcciones que coincidan con los
     * criterios de busqueda ingresados en pantalla de busqueda de direccion.
     *
     * @author Victor Bocanegra
     */
    public void buscarDireccion() {

        try {
            showPanelBusquedaDireccion = false;
            showPanelDirecciones = true;
            mostrarMapa = false;
            this.latitud = "";
            this.longitud = "";
            this.center = "";
            this.direccionDetalladaMglDtoSel = null;
            this.coberturaSeleccionada = null;

            if (!notGeoReferenciado) {
                if (drDireccion == null) {
                    showPanelBusquedaDireccion = true;
                    showPanelDirecciones = false;
                    mostrarMapa = false;
                    mostrarSolicitudHhpp = false;
                    mostrarSolicitudHhppCcmm = false;
                    msnError = "Es necesario construir una dirección con el panel de dirección tabulada.";
                    alerts();
                    return;
                }
                responseConstruirDireccion.setDrDireccion(drDireccion);
            }

            //Si la busqueda se realiza por dirección construida
            if (responseConstruirDireccion.getDrDireccion() != null
                    && validarEstructuraDireccion(responseConstruirDireccion.getDrDireccion(),
                            Constants.TIPO_VALIDACION_DIR_HHPP)) {
                direccionDetalladaList = direccionesDetalladasConsulta();
                if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {
                    direccionDetalladaListAux = direccionDetalladaList;
                    PrimeFacesUtil.scrollTo("panelDir");
                }
            } else {
                msnError = "Ingrese un criterio de búsqueda"
                        + " por favor. ";
                showPanelBusquedaDireccion = true;
                showPanelDirecciones = false;
                mostrarMapa = false;
                mostrarSolicitudHhpp = false;
                mostrarSolicitudHhppCcmm = false;
                alerts();
            }

        } catch (ApplicationException e) {
            showPanelBusquedaDireccion = true;
            showPanelDirecciones = false;
            mostrarMapa = false;
            mostrarSolicitudHhpp = false;
            mostrarSolicitudHhppCcmm = false;
            msnError = "Error al momento de realizar la búsqueda de la dirección: " + e.getMessage();
            alerts();
        } catch (Exception e) {
            showPanelBusquedaDireccion = true;
            showPanelDirecciones = false;
            mostrarMapa = false;
            msnError = "Error al momento de realizar la búsqueda de la dirección: " + e.getMessage();
            alerts();
        }
    }

    /**
     * Función que se utiliza para validar el barrio sugerido seleccionado
     *
     * @author Victor Bocanegra
     */
    public void validarBarrioSugeridoSeleccionado() {
        try {
            /*Si selecciona otro renderiza campo de texto en la pantalla para 
             * ingresar manualmente el barrio*/
            if (barrioSugerido.equalsIgnoreCase("Otro")) {
                barrioSugeridoField = true;
                barrioSugeridoStr = "";
                request.setBarrio(null);
            } else {
                /*Si selecciona un barrio del listado, este se asigna de inmediato
                 * a la dirección*/
                if (!barrioSugerido.equalsIgnoreCase("Otro")
                        && !barrioSugerido.isEmpty()
                        && !barrioSugerido.equals("")) {
                    barrioSugeridoStr = barrioSugerido.toUpperCase();
                    barrioSugeridoField = false;
                    request.setBarrio(barrioSugerido.toUpperCase());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al validar barrio sugerido seleccionado ", e);
            msnError = "Error al validar barrio sugerido seleccionado: " + e.getMessage();
            alerts();
        }
    }

    /**
     * Función que agrega el barrio sugerido ingresado manualmente
     *
     * @author Victor Bocanegra
     */
    public void agregarBarrioSugerido() {
        try {
            if (barrioSugeridoStr == null || barrioSugeridoStr.isEmpty() || barrioSugeridoStr.equals("")) {
                msnError = "Ingrese el nombre de un barrio sugerido por "
                        + "favor.";
                alerts();
            } else {
                request.setBarrio(barrioSugeridoStr.toUpperCase());
                barrioSugeridoStr = barrioSugeridoStr.toUpperCase();
                msnError = "Barrio sugerido agregado exitosamente.";
                alertProcesoExistoso();
            }
        } catch (Exception e) {
            LOGGER.error("Error al agregar barrio sugerido ", e);
            msnError = "Error al agregar barrio sugerido." + e.getMessage();
            alerts();
        }
    }

    /**
     * Función para validar datos nulos al momento de construir.una dirección
     * tipo Calle-Carrera.
     *
     * @author Victor Bocanegra return false si algun dato se encuentra vacio.
     * @return
     */
    public boolean validarDatosDireccionCk() {

        if (tipoDireccion == null || tipoDireccion.isEmpty()) {
            msnError = "Seleccione el TIPO DE DIRECCIÓN que "
                    + "desea ingresar por favor.";
            alerts();
            return false;
        } else {
            if (direccionCk == null || direccionCk.isEmpty()) {
                msnError = "Ingrese la dirección por favor.";
                alerts();
                return false;
            } else {
                if (ciudad == null) {
                    msnError = "Seleccione la CIUDAD por favor.";
                    alerts();
                    return false;
                } else {
                    if (idCentroPoblado == null) {
                        msnError = "Seleccione el CENTRO POBLADO por favor.";
                        alerts();
                        return false;
                    }
                }
            }
        }
        return true;

    }

    /**
     * Función para validar datos nulos al momento de construir.una dirección
     * tipo Barrio-Manzana.
     *
     * @author Victor Bocanegra return boolean false si algun dato se encuentra
     * vacio
     * @return
     */
    public boolean validarDatosDireccionBm() {

        if (tipoDireccion == null || tipoDireccion.isEmpty()) {
            msnError = "Seleccione el TIPO DE DIRECCIÓN que desea"
                    + " ingresar por favor.";
            alerts();
            return false;
        } else {
            if (tipoNivelBm == null || tipoNivelBm.isEmpty()) {
                msnError = "Seleccione el TIPO DE NIVEL que desea"
                        + " agregar por favor.";
                alerts();
                return false;
            } else {
                if (ciudad == null) {
                    msnError = "Seleccione la CIUDAD por favor.";
                    alerts();
                    return false;
                } else {
                    if (idCentroPoblado == null) {
                        msnError = "Seleccione el CENTRO POBLADO por favor.";
                        alerts();
                        return false;
                    } else {
                        if (nivelValorBm == null || nivelValorBm.isEmpty()) {
                            msnError = "Ingrese un valor en el campo de "
                                    + "nivel barrio-manzana por favor.";
                            alerts();
                            return false;
                        }
                    }
                }
            }
        }
        return true;

    }

    /**
     * Función para validar datos nulos al momento de construir.una dirección
     * tipo Intraducible.
     *
     * @author Victor Bocanegra return false si algun dato se encuentra vacio
     * @return
     */
    public boolean validarDatosDireccionIt() {

        if (tipoDireccion == null || tipoDireccion.isEmpty()) {
            msnError = "Seleccione el TIPO DE DIRECCIÓN "
                    + "que desea ingresar por favor.";
            alerts();
            return false;
        } else {
            if (tipoNivelIt == null || tipoNivelIt.isEmpty()) {
                msnError = "Seleccione el TIPO DE NIVEL que "
                        + "desea agregar por favor.";
                alerts();
                return false;
            } else {
                if (ciudad == null) {
                    msnError = "Seleccione la CIUDAD por favor.";
                    alerts();
                    return false;
                } else {
                    if (idCentroPoblado == null) {
                        msnError = "Seleccione el CENTRO POBLADO por favor.";
                        alerts();
                        return false;
                    } else {
                        if (nivelValorIt == null || nivelValorIt.isEmpty()) {
                            msnError = "Ingrese un valor en el campo "
                                    + "de nivel intraducible por favor";
                            alerts();
                            return false;
                        } else {
                            if (tipoNivelIt.equalsIgnoreCase("CONTADOR")
                                    && nivelValorIt.length() > 7) {
                                msnError = "El valor para Contador no "
                                        + "puede exceder los 6 caracteres";
                                alerts();
                                return false;
                            } else {
                                if (tipoNivelIt.equalsIgnoreCase("CONTADOR")
                                        && !isNumeric(nivelValorIt)) {
                                    msnError = "El valor para Contador "
                                            + "debe ser númerico.";
                                    alerts();
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;

    }

    /**
     * Función para validar si el dato recibido es númerico
     *
     * @author Victor Bocanegra} return true si el dato es númerico
     * @param cadena
     * @return
     */
    public boolean isNumeric(String cadena) {
        try {
            new BigDecimal(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            LOGGER.error("Error en isNumeric. " + nfe.getMessage(), nfe);
            return false;
        } catch (Exception nfe) {
            LOGGER.error("Error en isNumeric. " + nfe.getMessage(), nfe);
            return false;
        }
    }

    /**
     * Función utilizada para construir dirección de tipo calle-carrera
     *
     * @author Victor Bocanegra
     */
    public void construirDireccionCk() throws Exception {

        try {
            if (validarDatosDireccionCk()) {

                request.setDireccionStr(direccionCk);
                request.setComunidad(responsesCentroPoblado.getGeoCodigoDane());
                drDireccion.setIdTipoDireccion(tipoDireccion);
                drDireccion.setDirPrincAlt("P");
                request.setDrDireccion(drDireccion);
                request.setTipoAdicion("N");
                request.setTipoNivel("N");

                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion
                            .getDrDireccion());
                }
                // Retorna la dirección calle-carrera traducida.
                responseConstruirDireccion = drDireccionFacadeLocal.construirDireccionSolicitud(request);

                //Direccion traducida correctamente
                if (responseConstruirDireccion.getDireccionStr() != null
                        && !responseConstruirDireccion.getDireccionStr().isEmpty()
                        && responseConstruirDireccion.getResponseMesaje().
                                getTipoRespuesta().equalsIgnoreCase("I")) {

                    direccionLabel
                            = responseConstruirDireccion.getDireccionStr();
                    direccionConstruida = true;
                    notGeoReferenciado = true;
                    request.setDrDireccion(responseConstruirDireccion
                            .getDrDireccion());
                } else {
                    //Dirección que no pudo ser traducida
                    if (responseConstruirDireccion.getDireccionStr() == null
                            || responseConstruirDireccion.getDireccionStr()
                                    .isEmpty() && responseConstruirDireccion
                                    .getResponseMesaje().getTipoRespuesta()
                                    .equalsIgnoreCase("E")) {

                        direccionLabel = direccionCk;
                        direccionConstruida = false;
                        notGeoReferenciado = false;
                        msnError = "La dirección calle-carrera"
                                + " no pudo ser traducida."
                                + responseConstruirDireccion.getResponseMesaje()
                                        .getMensaje();
                        alerts();
                    }
                }
                
                //Si la consulta viene de visor Movilidad 
                    if (tokenFactibilidades.substring(0, 14).equalsIgnoreCase(Constant.VISOR_MOVILIDAD)) {
                        msnError = "La direccion: "+
                                responseConstruirDireccion.getDireccionStr()+
                                " Fue consultada desde VISORMOVILIDAD con token: "+
                                tokenFactibilidades;
                        alertProcesoExistoso();
                        
                    }
            }
        } catch (ApplicationException e) {
            direccionConstruida = false;
            notGeoReferenciado = false;
            msnError = "Error en construirDireccionCk: " + e.getMessage();
            alerts();
        }
    }

    /**
     * Función utilizada para construir dirección de tipo Barrio-Manzana
     *
     * @author Victor Bocanegra
     */
    public void construirDireccionBm() {
        try {
            if (validarDatosDireccionBm()) {
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelBm);
                request.setValorNivel(nivelValorBm.trim());
                request.setTipoAdicion("P");

                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion
                            .getDrDireccion());
                }
                /* Retorna la dirección barrio-manzana traducida. */
                responseConstruirDireccion = drDireccionFacadeLocal.construirDireccionSolicitud(request);

                // Dirección no construida correctamente
                if (responseConstruirDireccion == null
                        || responseConstruirDireccion.getResponseMesaje()
                                .getTipoRespuesta().equals("E")) {
                    msnError = responseConstruirDireccion.getResponseMesaje()
                            .getMensaje();
                    alerts();
                } else {
                    //Dirección construida correctamente
                    if (responseConstruirDireccion != null
                            && responseConstruirDireccion.getResponseMesaje()
                                    .getTipoRespuesta().equals("I")) {
                        request.setDrDireccion(responseConstruirDireccion
                                .getDrDireccion());
                        String barrio = responseConstruirDireccion.getDrDireccion()
                                .getBarrio() != null ? responseConstruirDireccion
                                                .getDrDireccion().getBarrio().toUpperCase() : "";
                        direccionLabel = responseConstruirDireccion.getDireccionStr();
                        tipoNivelBm = "";
                        nivelValorBm = "";
                    }
                }
            }
        } catch (ApplicationException e) {
            msnError = "Error en construirDireccionBm: " + e.getMessage();
            alerts();
        }
    }

    /**
     * Función utilizada para construir dirección de tipo Intraducible
     *
     * @author Victor Bocanegra
     */
    public void construirDireccionIt() {
        try {
            if (validarDatosDireccionIt()) {
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelIt);
                request.setValorNivel(nivelValorIt.trim());
                request.setTipoAdicion("P");

                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion
                            .getDrDireccion());
                }
                // Retorna la dirección intraducible traducida.
                responseConstruirDireccion = drDireccionFacadeLocal.construirDireccionSolicitud(request);

                //Dirección no construida correctamente
                if (responseConstruirDireccion == null
                        || responseConstruirDireccion.getResponseMesaje()
                                .getTipoRespuesta().equals("E")) {
                    msnError = responseConstruirDireccion
                            .getResponseMesaje().getMensaje();
                    alerts();
                } else {
                    //Dirección construida correctamente
                    if (responseConstruirDireccion != null
                            && responseConstruirDireccion.getResponseMesaje()
                                    .getTipoRespuesta().equals("I")) {
                        request.setDrDireccion(responseConstruirDireccion
                                .getDrDireccion());

                        String barrioDireccion = responseConstruirDireccion
                                .getDrDireccion().getBarrio() != null
                                        ? responseConstruirDireccion.getDrDireccion()
                                                .getBarrio().toUpperCase() : "";
                        direccionLabel = responseConstruirDireccion
                                .getDireccionStr();
                        tipoNivelIt = "";
                        nivelValorIt = "";
                    }
                }
            }
        } catch (ApplicationException e) {
            msnError = "Error en construirDireccionIt: " + e.getMessage();
            alerts();
        }
    }
    
    /**
     * Función utilizada para construir dirección con complemento desde Visor para creacion automatica de Hhpp
     *
     * @author Johan Gómez
     */
    public void construirDirComplVisor() {
        try {
            if (validarDatosDireccionComplemento()) {
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelComplemento);
                request.setValorNivel(complemento);
                request.setTipoAdicion("C");

                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                }
                // Retorna la dirección con complemento.
                responseConstruirDireccion = drDireccionFacadeLocal.construirDireccionSolicitud(request);

                //Complemento no agregado a la dirección correctamente
                if (responseConstruirDireccion.equals(null) 
                        || responseConstruirDireccion.getResponseMesaje()
                                .getTipoRespuesta().equals("E")) {
                    msnError
                            = responseConstruirDireccion.getResponseMesaje()
                                    .getMensaje();
                    alerts();

                } else {
                    //Complemento agregado a la dirección correctamente
                    if (responseConstruirDireccion != null
                            && responseConstruirDireccion.getResponseMesaje()
                                    .getTipoRespuesta().equals("I")) {
                        
                        direccionLabel = responseConstruirDireccion
                                .getDireccionStr();
                        complemento = "";
                        tipoNivelComplemento = "";
                        
                        //Se actualiza direccion en la vista del formulario
                        direccionSeleccionada = responseConstruirDireccion.getDireccionStr();
                        //consulto lista de direcciones con el complemento de la direccion agregado y actualizo direcciondetalladaseleccionada
                        List<CmtDireccionDetalladaMglDto> listDirTemp = direccionesDetalladasConsulta();
                        if(listDirTemp.size() > 0){
                            direccionDetalladaMglDtoSel = listDirTemp.get(0);
                        }
                        
                        
                        //se actualiza lista de coberturas para la direccion con complemento agregado y se modifica el registro con el id de la nueva direccion
                        this.consultarDireccionSeleccionada(direccionDetalladaMglDtoSel);
                    }
                }
            }
        } catch (Exception e ) {
            msnError = "Error en construirDireccionComplemento: " + e.getMessage();
            alerts();
        } 
    }

    /**
     * Función para validar la construcción de una dirección para la tecnología seleccionada para creacion de hhpp desde visor.
     *
     * @param drDireccion          {@link DrDireccion}
     * @param tipoNivelApartamento {@link String}
     * @param apartamento          {@link String}
     * @return Retorna true, si cumple con las validaciones estipuladas para crear la dirección.
     * @author Johan J. Gómez. V.
     */
    public boolean validarDatosDirAptoParaTecnologiaSeleccionadaVisor(DrDireccion drDireccion,
            String tipoNivelApartamento, String apartamento) {
        try {
                Map<String, String> datosValidarApartamento = new HashMap<>();
                datosValidarApartamento.put(ConstantsDirecciones.TIPO_NIVEL_APARTAMENTO, tipoNivelApartamento);
                datosValidarApartamento.put(ConstantsDirecciones.APARTAMENTO, apartamento);
                List<String> codigosTecnologiasPorValidar = cmtBasicaMglFacadeLocal.findCodigosTecnologiasPorValidarEnSolicitud();
                String codigoBasica = tecnologiaSeleccionadaBasica.getCodigoBasica();
                datosValidarApartamento.put(ConstantsSolicitudHhpp.CODIGO_BASICA, codigoBasica);

                return direccionesValidacionFacadeLocal.validarConstruccionApartamentoPorTecnologias(
                        drDireccion, codigosTecnologiasPorValidar, datosValidarApartamento);
            

        } catch (Exception e) {
            String msgError = "Validación de apartamento para tecnología " + tecnologiaSeleccionadaBasica.getNombreBasica()
                    + e.getMessage();
            FacesUtil.mostrarMensajeError(msgError, e, LOGGER);
            return false;
        }
    }
    
    /**
     * Función utilizada para construir dirección con apartamento desde Visor para creación autómatica de Hhpp
     *
     * @author Johan Gómez
     */
    public void construirDirAptoVisor() 
            throws Exception {
        try {
            if (validarDatosDirAptoVisor() 
                    && validarDatosDirAptoParaTecnologiaSeleccionadaVisor(responseConstruirDireccion.getDrDireccion(),
                    tipoNivelApartamento, apartamento)) {
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelApartamento);
                request.setValorNivel(apartamento);
                request.setTipoAdicion("A");

                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                }
                // Retorna la dirección con apartamento.
                responseConstruirDireccion = drDireccionFacadeLocal.construirDireccionSolicitud(request);
                
                // Apartamento no agregado a la dirección correctamente
                if (responseConstruirDireccion.equals(null) 
                        || responseConstruirDireccion.getResponseMesaje()
                                .getTipoRespuesta().equals("E")) {
                    msnError
                            = responseConstruirDireccion.getResponseMesaje()
                                    .getMensaje();
                    alerts();

                } else {
                    // Apartamento agregado a la dirección correctamente
                    if (responseConstruirDireccion != null
                            && responseConstruirDireccion.getResponseMesaje()
                                    .getTipoRespuesta().equals("I")) {
                        
                        direccionLabel = responseConstruirDireccion
                                .getDireccionStr();
                        apartamento = "";
                        tipoNivelApartamento = "";
                        
                        //Se actualiza direccion en la vista del formulario
                        direccionSeleccionada = responseConstruirDireccion.getDireccionStr();
                        //consulto lista de direcciones con el complemento de la direccion agregado y actualizo direcciondetalladaseleccionada
                        List<CmtDireccionDetalladaMglDto> listDirTemp = direccionesDetalladasConsulta();
                        if(listDirTemp.size() > 0){
                            direccionDetalladaMglDtoSel = listDirTemp.get(0);
                        }
                        
                        //se actualiza lista de coberturas para la direccion con complemento agregado y se modifica el registro con el id de la nueva direccion
                        this.consultarDireccionSeleccionada(direccionDetalladaMglDtoSel);
                    }
                }
            }

        } catch (ApplicationException e) {
            msnError = "Error en construirDireccionApartamento: " + e.getMessage();
            alerts();
        }
    }
    /**
     * Función utilizada para construir dirección con complemento
     *
     * @author Victor Bocanegra
     */
    public void construirDireccionComplemento() {
        try {
            if (validarDatosDireccionComplemento()) {
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelComplemento);
                request.setValorNivel(complemento);
                request.setTipoAdicion("C");

                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                }
                // Retorna la dirección con complemento.
                responseConstruirDireccion = drDireccionFacadeLocal.construirDireccionSolicitud(request);

                //Complemento no agregado a la dirección correctamente
                if (responseConstruirDireccion == null
                        || responseConstruirDireccion.getResponseMesaje()
                                .getTipoRespuesta().equals("E")) {
                    msnError
                            = responseConstruirDireccion.getResponseMesaje()
                                    .getMensaje();
                    alerts();

                } else {
                    //Complemento agregado a la dirección correctamente
                    if (responseConstruirDireccion != null
                            && responseConstruirDireccion.getResponseMesaje()
                                    .getTipoRespuesta().equals("I")) {
                        String barrio = responseConstruirDireccion
                                .getDrDireccion().getBarrio() != null
                                        ? responseConstruirDireccion.getDrDireccion()
                                                .getBarrio().toUpperCase() : "";
                        direccionLabel = responseConstruirDireccion
                                .getDireccionStr();
                        complemento = "";
                        tipoNivelComplemento = "";
                    }
                }
            }
        } catch (Exception e) {
            msnError = "Error en construirDireccionComplemento: " + e.getMessage();
            alerts();
        }
    }

    /**
     * Función utilizada para construir dirección con apartamento
     *
     * @author Victor Bocanegra
     */
    public void construirDireccionApartamento() {
        try {
            if (validarDatosDireccionApartamento()) {
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelApartamento);
                request.setValorNivel(apartamento);
                request.setTipoAdicion("A");

                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                }
                // Retorna la dirección con apartamento.
                responseConstruirDireccion = drDireccionFacadeLocal.construirDireccionSolicitud(request);
                
                // Apartamento no agregado a la dirección correctamente
                if (responseConstruirDireccion == null
                        || responseConstruirDireccion.getResponseMesaje()
                                .getTipoRespuesta().equals("E")) {
                    msnError
                            = responseConstruirDireccion.getResponseMesaje()
                                    .getMensaje();
                    alerts();

                } else {
                    // Apartamento agregado a la dirección correctamente
                    if (responseConstruirDireccion != null
                            && responseConstruirDireccion.getResponseMesaje()
                                    .getTipoRespuesta().equals("I")) {
                        String barrio
                                = responseConstruirDireccion.getDrDireccion()
                                        .getBarrio() != null ? responseConstruirDireccion
                                                .getDrDireccion().getBarrio().toUpperCase() : "";
                        direccionLabel = responseConstruirDireccion
                                .getDireccionStr();
                        apartamento = "";
                        tipoNivelApartamento = "";
                        
                        direccionSeleccionada = responseConstruirDireccion.getDireccionStr();
                    }
                }
            }

        } catch (ApplicationException e) {
            msnError = "Error en construirDireccionApartamento: " + e.getMessage();
            alerts();
        }
    }

    /**
     * Función para validar datos nulos al momento de construir una dirección
     * con apartamento.
     *
     * @author Victor Bocanegra return false si algun dato se encuentra vacio
     * @return
     */
    public boolean validarDatosDirAptoVisor() {

        if (tipoDireccion == null || tipoDireccion.isEmpty()) {
            msnError = "Seleccione el TIPO DE DIRECCIÓN que desea"
                    + " ingresar por favor.";
            alerts();
            return false;
        } else {
            if (ciudad == null || ciudad.equals("0")) {
                msnError = "Seleccione la CIUDAD por favor.";
                alerts();
                return false;
            } else {

                if (tipoNivelApartamento == null
                        || tipoNivelApartamento.isEmpty()) {
                    msnError = "Seleccione el TIPO DE NIVEL de"
                            + " apartamento que desea agregar por favor.";
                    alerts();
                    return false;
                }
                else{
                    if (tipoNivelApartamento.equalsIgnoreCase("CASA")
                            || tipoNivelApartamento
                                    .equalsIgnoreCase("ADMINISTRACION")
                            || tipoNivelApartamento
                                    .equalsIgnoreCase("FUENTE")
                            || tipoNivelApartamento
                                    .equalsIgnoreCase("RECEPTOR")) {
                    } else {
                        if (Objects.isNull(apartamento) || apartamento.isEmpty()) {
                            msnError = "Ingrese un valor en el campo"
                                    + " apartamento por favor.";
                        }
                        //valores que contienen (piso), (piso +), y excepción de (casa + piso)
                        if (tipoNivelApartamento.contains("PI") && !tipoNivelApartamento.contains("CASA")
                                && !"1".equalsIgnoreCase(apartamento) 
                                && !"2".equalsIgnoreCase(apartamento)
                                && !"3".equalsIgnoreCase(apartamento)
                                && tecnologiaSeleccionadaBasica.getIdentificadorInternoApp() != null
                                && tecnologiaSeleccionadaBasica.getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_BID)) {

                            msnError = "El valor permitido"
                                    + " para PISO solo puede "
                                    + "ser 1, 2 o 3";
                            alerts();
                            return false;
                        } else {
                            //valores que contienen (piso), (piso +), y excepción de (casa + piso)
                            if (tipoNivelApartamento.contains("PI") && !tipoNivelApartamento.contains("CASA")
                                    && !"1".equalsIgnoreCase(apartamento) 
                                    && !"2".equalsIgnoreCase(apartamento)
                                    && !"3".equalsIgnoreCase(apartamento)
                                    && tecnologiaSeleccionadaBasica.getIdentificadorInternoApp() != null
                                    && tecnologiaSeleccionadaBasica.getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_BID)) {

                                msnError = "El valor permitido"
                                        + " para PISO solo puede "
                                        + "ser 1, 2 o 3";
                                alerts();
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;

    }
    
    /**
     * Función para validar datos nulos al momento de construir una dirección
     * con apartamento.
     *
     * @author Victor Bocanegra return false si algun dato se encuentra vacio
     * @return
     */
    public boolean validarDatosDireccionApartamento() {

        if (tipoDireccion == null || tipoDireccion.isEmpty()) {
            msnError = "Seleccione el TIPO DE DIRECCIÓN que desea"
                    + " ingresar por favor.";
            alerts();
            return false;
        } else {
            if (ciudad == null) {
                msnError = "Seleccione la CIUDAD por favor.";
                alerts();
                return false;
            } else {

                if (tipoNivelApartamento == null
                        || tipoNivelApartamento.isEmpty()) {
                    msnError = "Seleccione el TIPO DE NIVEL de"
                            + " apartamento que desea agregar por favor.";
                    alerts();
                    return false;
                }
            }
        }
        return true;

    }

    /**
     * Función para validar datos nulos al momento de construir una dirección
     * con complemento.
     *
     * @author Victor Bocanegra return false si algun dato se encuentra vacio
     * @return
     */
    public boolean validarDatosDireccionComplemento() {

        if (tipoDireccion == null || tipoDireccion.isEmpty()) {
            msnError = "Seleccione el TIPO DE DIRECCIÓN que desea"
                    + " ingresar por favor.";
            alerts();
            return false;
        } else {
            if (tipoNivelComplemento == null
                    || tipoNivelComplemento.isEmpty()) {
                msnError = "Seleccione TIPO DE NIVEL del complemento"
                        + " por favor.";
                alerts();
                return false;
            } else {
                if (ciudad == null) {
                    msnError = "Seleccione la CIUDAD por favor.";
                    alerts();
                    return false;
                } else {
                    if (complemento == null || complemento.isEmpty()) {
                        msnError = "Ingrese un valor en el campo "
                                + "del complemento por favor.";
                        alerts();
                        return false;
                    }
                }
            }
        }
        return true;

    }

    /**
     * Obtiene el listado de <b>CIUDADES</b> de la base de datos.
     *
     * @throws ApplicationException
     */
    public void obtenerCiudadesList() throws ApplicationException {
        if (departamento != null
                && !departamento.isEmpty()) {

            //Obtenemos el listado de ciudades para el filtro de la pantalla
            NegocioParamMultivalor result = new NegocioParamMultivalor();
            ciudadesList = result.getListElementByGroupId(departamento);
            Collections.sort(ciudadesList);

            centroPobladoList = new ArrayList();
        }
    }
	

    /**
     * Funci&oacute;n que obtiene el valor completo de el <b>CENTRO POBLADO</b>
     * seleccionado por el usuario en pantalla.
     *
     * @throws ApplicationException
     */
    public void obtenerCentroPobladoSeleccionado() throws ApplicationException {
        if (idCentroPoblado != null && !idCentroPoblado.isEmpty()) {

            //Consulta el centro poblado
            GeograficoPoliticoMgl geograficoPoliticoMgl
                    = geograficoPoliticoFacadeLocal.findById(new BigDecimal(idCentroPoblado));
            responsesCentroPoblado = geograficoPoliticoFacadeLocal.
                    parseGeograficoPoliticoMglToResponseGeograficoPolitico(geograficoPoliticoMgl);

            if (centroPobladoList != null && !centroPobladoList.isEmpty()) {

                ParamMultivalor centroSeleccionado = null;
                for (ParamMultivalor ret : centroPobladoList) {
                    if (idCentroPoblado.equalsIgnoreCase(ret.getIdParametro())) {
                        centroSeleccionado = ret;
                    }
                }
                if (centroSeleccionado != null && centroSeleccionado.getDescripcion() != null
                        && !centroSeleccionado.getDescripcion().isEmpty()
                        && (centroSeleccionado.getDescripcion().equalsIgnoreCase("PALMIRA")
                        || centroSeleccionado.getDescripcion().equalsIgnoreCase("CANDELARIA"))) {

                    msnError = "Para esta Ciudad, por favor ingrese la "
                            + "dirección por el panel de DIRECCIÓN TABULADA";
                    alerts();
                    notGeoReferenciado = false;
                } else {
                    notGeoReferenciado = true;

                }
            }
        }
    }

    /**
     * Obtiene el listado de <b>CENTRO POBLADO</b> por ciudad de la base de
     * datos.
     *
     * @throws ApplicationException
     */
    public void obtenerCentroPobladoList() throws ApplicationException {
        //Obtenemos el listado de centros poblados para el filtro de la pantalla  
        if (ciudad != null) {
            NegocioParamMultivalor result = new NegocioParamMultivalor();
            centroPobladoList = result.getListElementByGroupId(ciudad);
            Collections.sort(centroPobladoList);
        } else {
            centroPobladoList = new ArrayList();
        }
    }

	
    /**
     * Obtiene el listado de <b>DEPARTAMENTOS</b> de la base de datos.
     *
     * @throws ApplicationException
     */
    public void obtenerDepartamentoList() throws ApplicationException {
        NegocioParamMultivalor result = new NegocioParamMultivalor();
        departamentoList = result.getListElementByGroupId(Constants.PAIS);
        Collections.sort(departamentoList);
    }
	

    /**
     * Funci&oacute;n utilizada para validar si la excepci&oacute;n arrogada por
     * el servicio de validaci&oacute;n es por ser una ciudad multi-origen.
     *
     * @author Victor Bocanegra
     * @param multiOrigen
     */
    public void validarBarrioCiudadMultiOrigen(String multiOrigen) {
        try {
            /*Valida si la dirección construida es una dirección 
             Multi-Origen para hacer cargue de listado de barrios sugeridos. */
            if (multiOrigen.equalsIgnoreCase("1")) {
                /*Consume servicio que permite obtener un listado de barrios 
                 * sugeridos para el usuario. */

                CmtRequestConstruccionDireccionDto requestBarrios1 = parserRequest(request);
                neighborhoodsDto
                        = direccionesValidacionFacadeLocal
                                .obtenerBarrioSugeridoHhpp(requestBarrios1);

				
                if (neighborhoodsDto != null) {
                    if (neighborhoodsDto.getNeighborhoodsList() != null 
                            && !neighborhoodsDto.getNeighborhoodsList().isEmpty() 
                            && neighborhoodsDto.getNeighborhoodsList().get(0).getMessageType() != null
                            && neighborhoodsDto.getNeighborhoodsList().get(0).getMessageType().equalsIgnoreCase("E")) {
                        msnError = neighborhoodsDto.getNeighborhoodsList().get(0).getMessage();
                        alerts();
                        return;
                    } else if (neighborhoodsDto.getNeighborhoodsList() != null
                            && !neighborhoodsDto.getNeighborhoodsList().isEmpty()) {
                        barrioSugeridoList = neighborhoodsDto.getNeighborhoodsList();
                    }
                }

                /*Valida que se haya obtenido un listado de barrios sugeridos
                 para desplegarlos en pantalla. */
                if (barrioSugeridoList != null && !barrioSugeridoList.isEmpty()) {
                    msnError = "La ciudad es MultiOrigen, es necesario"
                            + " seleccionar un barrio sugerido y buscar "
                            + "nuevamente";
                    alerts();

                    //Se agrega el valor 'Otro' al listado de barrios sugeridos    
                    CmtAddressSuggestedDto otro = new CmtAddressSuggestedDto();
                    otro.setAddress("Otro");
                    otro.setNeighborhood("Otro");
                    barrioSugeridoList.add(otro);
                    showBarrio = true;
                } else {
                    msnError = "La ciudad es MultiOrigen y no fue posible"
                            + " obtener barrios sugeridos. Seleccione el valor"
                            + " 'Otro' e ingreselo manualmente por favor";
                    alerts();
                    //Se agrega el valor 'Otro' al listado de barrios sugeridos
                    barrioSugeridoList = new ArrayList();
                    CmtAddressSuggestedDto otro = new CmtAddressSuggestedDto();
                    otro.setAddress("Otro");
                    otro.setNeighborhood("Otro");
                    barrioSugeridoList.add(otro);
                    showBarrio = true;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al validar barrio de la dirección ", e);
            msnError = "Error al validar barrio de la dirección: " + e.getMessage();
            alerts();
        }
    }
	

    /**
     * Valida estructura de una direccion.
     *
     * @author Vitor Bocanegra
     * @param direccion objeto con la informacion de la direccion a validar.
     * @param tipoValidacionCM
     *
     * @return true si la direccion contiene lo minimo de la validacion
     * direccion.
     */
    public boolean validarEstructuraDireccion(DrDireccion direccion, String tipoValidacionCM) throws Exception {
        try {
            if (direccion == null) {
                throw new Exception("No es posible realizar la validación por"
                        + " datos incompletos en la construcción de la dirección. ");
            }

            /*Valida por tipo de dirección que contenga el minino de elementros 
             ingresados a la construcción de la dirección */
            if (direccion.getIdTipoDireccion() != null && !direccion.getIdTipoDireccion().isEmpty()
                    && direccion.getIdTipoDireccion().equalsIgnoreCase("CK")) {

                if (direccion.getTipoViaPrincipal() == null
                        || direccion.getTipoViaPrincipal().trim().isEmpty()) {
                    throw new Exception("Es necesario ingresar el "
                            + "TIPO VIA PRINCIPAL. ");
                }
                if (direccion.getNumViaPrincipal() == null
                        || direccion.getNumViaPrincipal().trim().isEmpty()) {
                    throw new Exception("Es necesario ingresar la "
                            + "NUM VIA PRINCIPAL. ");
                }

            } else if (direccion.getIdTipoDireccion() != null && !direccion.getIdTipoDireccion().isEmpty()
                    && direccion.getIdTipoDireccion().equalsIgnoreCase("BM")) {
                if ((direccion.getMzValorNivel1() == null
                        || direccion.getMzValorNivel1().trim().isEmpty()) && (direccion.getMzValorNivel2() == null
                        || direccion.getMzValorNivel2().trim().isEmpty()) && (direccion.getMzValorNivel3() == null
                        || direccion.getMzValorNivel3().trim().isEmpty()) && (direccion.getMzValorNivel4() == null
                        || direccion.getMzValorNivel4().trim().isEmpty()) && (direccion.getMzValorNivel5() == null
                        || direccion.getMzValorNivel5().trim().isEmpty()) && (direccion.getMzValorNivel6() == null
                        || direccion.getMzValorNivel6().trim().isEmpty())) {
                    throw new Exception("Es necesario ingresar un nivel  "
                            + "  para la dirección barrio-manzana. ");
                }
            } else if (direccion.getIdTipoDireccion() != null && !direccion.getIdTipoDireccion().isEmpty()
                    && direccion.getIdTipoDireccion().equalsIgnoreCase("IT")) {

                if ((direccion.getMzValorNivel1() == null
                        || direccion.getMzValorNivel1().trim().isEmpty()) && (direccion.getMzValorNivel2() == null
                        || direccion.getMzValorNivel2().trim().isEmpty()) && (direccion.getMzValorNivel3() == null
                        || direccion.getMzValorNivel3().trim().isEmpty()) && (direccion.getMzValorNivel4() == null
                        || direccion.getMzValorNivel4().trim().isEmpty()) && (direccion.getMzValorNivel5() == null
                        || direccion.getMzValorNivel5().trim().isEmpty()) && (direccion.getMzValorNivel6() == null
                        || direccion.getMzValorNivel6().trim().isEmpty())) {
                    throw new Exception("Es necesario ingresar un nivel  "
                            + "  para la dirección intraducible. ");
                }
                if (direccion.getItTipoPlaca() == null
                        || direccion.getItTipoPlaca().isEmpty()
                        || direccion.getItValorPlaca() == null
                        || direccion.getItValorPlaca().isEmpty()) {
                    throw new Exception("Es necesario ingresar el valor"
                            + " de la Placa para la dirección intraducible. ");
                }
            }

            if (!Constants.TIPO_VALIDACION_DIR_CM.equalsIgnoreCase(tipoValidacionCM)) {
                //todo:
                /*Valida que el complemento Apartamento si tiene "+" contenga los 
                 2 valores */
                if (direccion.getCpTipoNivel5() != null && direccion.getCpTipoNivel5()
                        .equalsIgnoreCase(Constants.OPT_PISO_INTERIOR)) {

                    if (direccion.getCpValorNivel5() == null
                            || direccion.getCpValorNivel5().isEmpty()
                            || direccion.getCpValorNivel6() == null
                            || direccion.getCpValorNivel6().isEmpty()) {
                        throw new Exception("Debe ingresar los 2 valores del nivel "
                                + "apartamento: PISO + INTERIOR");
                    }
                } else {
                    if (direccion.getCpTipoNivel5() != null && direccion.getCpTipoNivel5()
                            .equalsIgnoreCase(Constants.OPT_PISO_LOCAL)) {
                        if (direccion.getCpValorNivel5() == null
                                || direccion.getCpValorNivel5().isEmpty()
                                || direccion.getCpValorNivel6() == null
                                || direccion.getCpValorNivel6().isEmpty()) {
                            throw new Exception("Debe ingresar los 2 valores del nivel "
                                    + "apartamento: PISO + LOCAL");
                        }
                    } else {
                        if (direccion.getCpTipoNivel5() != null && direccion.getCpTipoNivel5()
                                .equalsIgnoreCase(Constants.OPT_PISO_APTO)) {
                            if (direccion.getCpValorNivel5() == null
                                    || direccion.getCpValorNivel5().isEmpty()
                                    || direccion.getCpValorNivel6() == null
                                    || direccion.getCpValorNivel6().isEmpty()) {
                                throw new Exception("Debe ingresar los 2 valores del nivel "
                                        + "apartamento: PISO + APARTAMENTO");
                            }
                        } else {
                            if (direccion.getCpTipoNivel5() != null && direccion.getCpTipoNivel5()
                                    .equalsIgnoreCase(Constants.OPT_CASA_PISO)) {
                                if (direccion.getCpValorNivel5() == null
                                        || direccion.getCpValorNivel5().isEmpty()
                                        || direccion.getCpValorNivel6() == null
                                        || direccion.getCpValorNivel6().isEmpty()) {
                                    throw new Exception("Debe ingresar los 2 valores del nivel "
                                            + "apartamento: CASA + PISO");
                                }
                            }
                        }
                    }
                }
            }
            return true;

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método: validarEstructuraDireccion " + e.getMessage();
            LOGGER.error(msg);
            throw new Exception("Error al validar la direccion. EX000: " + e.getMessage(), e);
        }
    }

	
    /**
     * Funci&oacute;n que realiza paginaci&oacute;n de la tabla.
     *
     * @param page;
     * @author Victor Bocanegra
     * @return
     * @throws java.lang.Exception
     */
    public String listInfoByPage(int page) throws Exception {
        try {
            actual = page;
            getPageActual();
            showFooter = direccionDetalladaList != null && !direccionDetalladaList.isEmpty();

            int firstResult = 0;
            if (page > 1) {
                firstResult = (filasPag5 * (page - 1));
            }
            //Obtenemos el rango de la paginación
            if (direccionDetalladaListAux != null && !direccionDetalladaListAux.isEmpty()) {

                int maxResult;
                if ((firstResult + filasPag5) > direccionDetalladaListAux.size()) {
                    maxResult = direccionDetalladaListAux.size();
                } else {
                    maxResult = (firstResult + filasPag5);
                }

                direccionDetalladaList = new ArrayList<>();
                for (int k = firstResult; k < maxResult; k++) {
                    direccionDetalladaList.add(direccionDetalladaListAux.get(k));
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error en listInfoByPage. ", e);
            throw new Exception("Error en listInfoByPage: " + e.getMessage() + "");
        }
        return null;
    }
	

    /**
     * Función que carga la primera página de resultados
     *
     * @author Victor Bocanegra
     */
    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            msnError = "Error en pageFirst, redireccionando a primera pagina: " + ex.getMessage();
            alerts();
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     *
     * @author Victor Bocanegra
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
        } catch (Exception ex) {
            msnError = "Error en pagePrevious, direccionando a la página anterior: " + ex.getMessage();
            alerts();
        }
    }

    /**
     * Función que permite ir directamente a la página seleccionada de
     * resultados.
     *
     * @author Victor Bocanegra
     */
    public void irPagina() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = Integer.parseInt(numPagina);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception e) {
            msnError = "Error en irPagina: " + e.getMessage();
            alerts();
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     *
     * @author Victor Bocanegra
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
            msnError = "Error en pageNext, direccionando a la siguiente página: " + e.getMessage();
            alerts();
        }
    }

    /**
     * Función que carga la última página de resultados
     *
     * @author Victor Bocanegra
     */
    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception e) {
            msnError = "Error en pageLast, direccionando a la última página: " + e.getMessage();
            alerts();
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     *
     * @author Victor Bocanegra
     * @return
     */
    public int getTotalPaginas() {
        try {
            int totalPaginas = 0;

            if (direccionDetalladaListAux != null && !direccionDetalladaListAux.isEmpty()) {
                int pageSol = direccionDetalladaListAux.size();

                totalPaginas = ((pageSol % filasPag5 != 0)
                        ? (pageSol / filasPag5) + 1 : pageSol / filasPag);

            }
            return totalPaginas;
        } catch (Exception e) {
            msnError = "Error en getTotalPaginas, redireccionand página: " + e.getMessage();
            alerts();
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario en
     * los resultados.
     *
     * @author Victor Bocanegra
     * @return
     */
    public String getPageActual() {
        paginaList = new ArrayList<>();
        int totalPaginas = getTotalPaginas();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaList.add(i);
        }
        pageActual = String.valueOf(actual);
        numPagina = String.valueOf(actual);

        return pageActual;
    }

    //////////////////Paginado coberturas//////////////////////
    /**
     * Función que realiza paginación de la tabla.
     *
     * @param page;
     * @author Victor Bocanegra
     * @return
     * @throws java.lang.Exception
     */
    public String listInfoByPageCob(int page) throws Exception {
        try {
            actualCob = page;
            getPageActualCob();

            int firstResult = 0;
            if (page > 1) {
                firstResult = (filasPag5 * (page - 1));
            }
            //Obtenemos el rango de la paginación
            if (listCoverAux != null && !listCoverAux.isEmpty()) {

                int maxResult;
                if ((firstResult + filasPag5) > listCoverAux.size()) {
                    maxResult = listCoverAux.size();
                } else {
                    maxResult = (firstResult + filasPag5);
                }

                listCover = new ArrayList<>();
                for (int k = firstResult; k < maxResult; k++) {
                    listCover.add(listCoverAux.get(k));
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error en listInfoByPageCob. ", e);
            throw new Exception("Error en listInfoByPageCob: " + e.getMessage() + "");
        }
        return null;
    }

    /**
     * Función que carga la primera página de resultados
     *
     * @author Victor Bocanegra
     */
    public void pageFirstCob() {
        try {
            listInfoByPageCob(1);
        } catch (Exception ex) {
            msnError = "Error en pageFirstCob, redireccionando a primera pagina: " + ex.getMessage();
            alerts();
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     *
     * @author Victor Bocanegra
     */
    public void pagePreviousCob() {
        try {
            int totalPaginas = getTotalPaginasCob();
            int nuevaPageActual = actualCob - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPageCob(nuevaPageActual);
        } catch (Exception ex) {
            msnError = "Error en pagePreviousCob, direccionando a la página anterior: " + ex.getMessage();
            alerts();
        }
    }

    /**
     * Función que cpermite ir directamente a la página seleccionada de
     * resultados.
     *
     * @author Victor Bocanegra
     */
    public void irPaginaCob() {
        try {
            int totalPaginas = getTotalPaginasCob();
            int nuevaPageActual = Integer.parseInt(numPaginaCob);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageCob(nuevaPageActual);
        } catch (Exception e) {
            msnError = "Error en irPaginaCob: " + e.getMessage();
            alerts();
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     *
     * @author Victor Bocanegra
     */
    public void pageNextCob() {
        try {
            int totalPaginas = getTotalPaginasCob();
            int nuevaPageActual = actualCob + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageCob(nuevaPageActual);
        } catch (Exception e) {
            msnError = "Error en pageNextCob, direccionando a la siguiente página: " + e.getMessage();
            alerts();
        }
    }

    /**
     * Función que carga la última página de resultados
     *
     * @author Victor Bocanegra
     */
    public void pageLastCob() {
        try {
            int totalPaginas = getTotalPaginasCob();
            listInfoByPageCob(totalPaginas);
        } catch (Exception e) {
            msnError = "Error en pageLastCob, direccionando a la última página: " + e.getMessage();
            alerts();
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     *
     * @author Victor Bocanegra
     * @return
     */
    public int getTotalPaginasCob() {
        try {
            int totalPaginas = 0;

            if (listCoverAux != null && !listCoverAux.isEmpty()) {
                int pageSol = listCoverAux.size();

                totalPaginas = ((pageSol % filasPag5 != 0)
                        ? (pageSol / filasPag5) + 1 : pageSol / filasPag);

            }
            return totalPaginas;
        } catch (Exception e) {
            msnError = "Error en getTotalPaginasCob, redireccionand página: " + e.getMessage();
            alerts();
        }
        return 1;
    }

    /**
     * Función que da a conocer la página actual en la que se encuentra el
     * usuario en los resultados.
     *
     * @author Victor Bocanegra
     * @return
     */
    public String getPageActualCob() {
        paginaListCob = new ArrayList<>();
        int totalPaginas = getTotalPaginasCob();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListCob.add(i);
        }
        pageActualCob = String.valueOf(actualCob);
        numPaginaCob = String.valueOf(actualCob);

        return pageActualCob;
    }
/////////////////////Fin paginado Coberturas//////////////////////////////

    /**
     * Función que renderiza paneles de tipo dirección en la pantalla.
     *
     * @author Victor Bocanegra
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
     * @author Victor Bocanegra
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
     * @author Victor Bocanegra
     */
    private void showBM() {
        showBMPanel = true;
        showITPanel = false;
        showCKPanel = false;
    }

    /**
     * Función que renderiza panel de tipo dirección intraducible en la pantalla
     *
     * @author Victor Bocanegra
     */
    private void showIT() {
        showITPanel = true;
        showBMPanel = false;
        showCKPanel = false;
    }

    public void showHideDireccion() {
        showPanelBusquedaDireccion = !showPanelBusquedaDireccion;
    }

    public void buildDireccion() {
        try {
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

        } catch (Exception e) {
            msnError = "Error en buildDireccion: " + e.getMessage();
            alerts();
        }
    }

	
    /**
     * M&eacute;todo para consultar las direcciones.
     *
     * @return Listado de CmtDireccionDetalladaMglDto.
     * @throws ApplicationException
     */
    private List<CmtDireccionDetalladaMglDto> direccionesDetalladasConsulta()
            throws ApplicationException {

        List<CmtDireccionDetalladaMglDto> direccionDetalladaMglDtos = null;

        // Realiza la busqueda normal de una direccion completa, construida correctamente
        List<CmtDireccionDetalladaMgl> direccionDetalladaBusquedaOriginalList
                = cmtDireccionDetalleMglFacadeLocal
                        .findDireccionByDireccionDetallada(responseConstruirDireccion.getDrDireccion(),
                                new BigDecimal(idCentroPoblado), false, 1, filasPag5, false);

        List<CmtDireccionDetalladaMgl> direccionDetalladaTextoList = new ArrayList();
        ///Busqueda por texto para direcciones con nombres en la via principal      
        if (responseConstruirDireccion != null
                && responseConstruirDireccion.getDireccionRespuestaGeo() != null
                && !responseConstruirDireccion.getDireccionRespuestaGeo().isEmpty()
                && responseConstruirDireccion != null && responseConstruirDireccion.getDrDireccion() != null) {

            direccionDetalladaTextoList = cmtDireccionDetalleMglFacadeLocal.busquedaDireccionTextoRespuestaGeo(responseConstruirDireccion.getDireccionRespuestaGeo(),
                    responseConstruirDireccion.getDrDireccion(), new BigDecimal(idCentroPoblado));
        }

        if ((direccionDetalladaBusquedaOriginalList != null && !direccionDetalladaBusquedaOriginalList.isEmpty())
                || (direccionDetalladaTextoList != null && !direccionDetalladaTextoList.isEmpty())) {
            direccionDetalladaBusquedaOriginalList
                    = cmtDireccionDetalleMglFacadeLocal.combinarDireccionDetalladaList(direccionDetalladaBusquedaOriginalList, direccionDetalladaTextoList);
        }

        if (direccionDetalladaBusquedaOriginalList != null && !direccionDetalladaBusquedaOriginalList.isEmpty()) {

            if (OBTENER_INFORMACION_ADICIONAL_HHPP) {
                // Obtener la informacion adicional asociada a cada direccion:
                this.obtenerParametrosHhpp(direccionDetalladaBusquedaOriginalList);
            }

            // Convertir el listado de entidades en listado de DTOs para la visualizacion:
            direccionDetalladaMglDtos
                    = this.convertirDireccionDetalladaMglADto(direccionDetalladaBusquedaOriginalList);

        } else {
            //si tiene lo minimo de una dirección se envia a creacion
            if (direccionesValidacionFacadeLocal.validarEstructuraDireccion(responseConstruirDireccion.getDrDireccion(),
                    Constant.TIPO_VALIDACION_DIR_HHPP)) {

                if (responsesCentroPoblado != null && responsesCentroPoblado.getGeoCodigoDane() != null) {
                    //crear la direccion en MGL si no se encontró
                    crearDireccionConsultada(responseConstruirDireccion.getDrDireccion(), responsesCentroPoblado.getGeoCodigoDane());
                    //busca nuevamente la direccion para mostrarla en pantalla
                    direccionDetalladaBusquedaOriginalList
                            = busquedaDireccionDetalladaTabuladaTexto();

                    if (direccionDetalladaBusquedaOriginalList == null || direccionDetalladaBusquedaOriginalList.isEmpty()) {
                        msnError = "No se obtuvieron resultados con la dirección"
                                + " construida.";
                        alerts();
                        showPanelBusquedaDireccion = true;
                        return null;
                    }
                }
            }

			
            if (direccionDetalladaBusquedaOriginalList == null || direccionDetalladaBusquedaOriginalList.isEmpty()) {
                // Realizar la busqueda a traves de la Consulta por Direccion General.
                requestDirecciones = new CmtDireccionRequestDto();
                requestDirecciones.setUser(this.usuarioSesion);
                requestDirecciones.setCodigoDane(request.getComunidad());
                requestDirecciones.setDireccion(responseConstruirDireccion.getDireccionStr());

                CmtDireccionTabuladaDto direccionTabuladaDto
                        = parseDrDireccionToCmtDireccionTabuladaDto(responseConstruirDireccion.getDrDireccion());

                requestDirecciones.setDireccionTabulada(direccionTabuladaDto);
                responsesDirecciones
                        = cmtDireccionDetalleMglFacadeLocal.ConsultaDireccionGeneral(requestDirecciones);

                if (responsesDirecciones != null
                        && responsesDirecciones.getListAddresses() != null
                        && !responsesDirecciones.getListAddresses().isEmpty()) {
                    direccionDetalladaMglDtos = 
                            returnCmtDireccionDetalladaMglDto(responsesDirecciones.getListAddresses());

                    if (direccionDetalladaMglDtos != null && !direccionDetalladaMglDtos.isEmpty()) {
                        if (OBTENER_INFORMACION_ADICIONAL_HHPP) {
                            // Obtener la informacion adicional, relacionada con el HHPP.
                            this.obtenerParametrosHhppDto(direccionDetalladaMglDtos);
                        }
                    }
                }
            } else {
                direccionDetalladaMglDtos
                        = this.convertirDireccionDetalladaMglADto(direccionDetalladaBusquedaOriginalList);
            }
        }

		
        // Si no fue encontrada de ninguna de las dos maneras:
        if (direccionDetalladaMglDtos == null) {
            //Si el centro poblado es multiorigen se debe pedir el barrio para buscar y crear la dirección.
            if (responsesCentroPoblado.getGpoMultiorigen() != null
                    && responsesCentroPoblado.getGpoMultiorigen().equalsIgnoreCase("1")
                    && !showBarrio && drDireccion != null
                    && drDireccion.getIdTipoDireccion() != null
                    && !drDireccion.getIdTipoDireccion().isEmpty()
                    && drDireccion.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                showBarrio = true;
                validarBarrioCiudadMultiOrigen(responsesCentroPoblado.getGpoMultiorigen());
                return null;
            }

            //Si el centro poblado es multiorigen se debe pedir el barrio para buscar y crear la dirección.
            if (responsesCentroPoblado.getGpoMultiorigen() != null
                    && responsesCentroPoblado.getGpoMultiorigen().equalsIgnoreCase("1")) {
                if (barrioSugeridoStr == null || barrioSugeridoStr.isEmpty()) {
                    {
                        if (drDireccion.getIdTipoDireccion() != null
                                && !drDireccion.getIdTipoDireccion().isEmpty()
                                && drDireccion.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                            msnError = "La ciudad es multiorigen y es necesario ingresar un barrio.";
                            alerts();
                            return null;
                        }
                    }
                }
            }

            //se asigna el barrio al objeto DrDireccion que va a crear la dirección.
            if (barrioSugeridoStr != null && !barrioSugeridoStr.isEmpty()) {
                drDireccion.setBarrio(barrioSugeridoStr.toUpperCase());
            }

            if (responsesDirecciones.getMessageType() != null
                    && responsesDirecciones.getMessageType().equalsIgnoreCase("E")) {
                msnError = responsesDirecciones.getMessage();
                alerts();
                return null;
            }

        }

        return direccionDetalladaMglDtos;
    }

	
	
    /**
     * Realiza la b&uacute;squeda de la Direcci&oacute;n Detallada de forma
     * Tabulada.
     *
     * @return
     * @throws ApplicationException
     */
    public List<CmtDireccionDetalladaMgl> busquedaDireccionDetalladaTabuladaTexto() throws ApplicationException {
        List<CmtDireccionDetalladaMgl> direccionDetalladaBusquedaOriginalList = null;

        try {

            //busqueda normal de una dirección completa construida correctamente
            direccionDetalladaBusquedaOriginalList = cmtDireccionDetalleMglFacadeLocal
                    .findDireccionByDireccionDetallada(responseConstruirDireccion.getDrDireccion(),
                            new BigDecimal(idCentroPoblado), false, 1, filasPag5, false);

            List<CmtDireccionDetalladaMgl> direccionDetalladaTextoList = new ArrayList();
            ///Busqueda por texto para direcciones con nombres en la via principal      
            if (responseConstruirDireccion != null
                    && responseConstruirDireccion.getDireccionRespuestaGeo() != null
                    && !responseConstruirDireccion.getDireccionRespuestaGeo().isEmpty()
                    && responseConstruirDireccion != null && responseConstruirDireccion.getDrDireccion() != null) {

                if (responsesCentroPoblado.getGeoCodigoDane() != null) {
                    direccionDetalladaTextoList
                            = cmtDireccionDetalleMglFacadeLocal.busquedaDireccionTextoRespuestaGeo(responseConstruirDireccion.getDireccionRespuestaGeo(),
                                    responseConstruirDireccion.getDrDireccion(),
                                    new BigDecimal(responsesCentroPoblado.getGeoCodigoDane()));
                }
            }

            if ((direccionDetalladaBusquedaOriginalList != null && !direccionDetalladaBusquedaOriginalList.isEmpty())
                    || (direccionDetalladaTextoList != null && !direccionDetalladaTextoList.isEmpty())) {
                direccionDetalladaBusquedaOriginalList
                        = cmtDireccionDetalleMglFacadeLocal.combinarDireccionDetalladaList(direccionDetalladaBusquedaOriginalList,
																							   
                                direccionDetalladaTextoList);
            }

            if (direccionDetalladaBusquedaOriginalList != null && !direccionDetalladaBusquedaOriginalList.isEmpty()) {

                if (OBTENER_INFORMACION_ADICIONAL_HHPP) {
                    // Obtener la informacion adicional asociada a cada direccion:
                    this.obtenerParametrosHhpp(direccionDetalladaBusquedaOriginalList);
                }

                if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {
                    showFooter = true;
                }
            } else {
                showFooter = false;
            }

        } catch (ApplicationException e) {
            LOGGER.error("Error en listInfoByPage. ", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en listInfoByPage. ", e);
            throw new ApplicationException("Error en listInfoByPage. ", e);
        }
        return (direccionDetalladaBusquedaOriginalList);
    }

	
	
    /**
     * Funci&oacute;n que realiza la creaci&oacute;n de una direcci&oacute;n en
     * las tablas direcci&oacute;n, subdirecci&oacute;n,
     * direcci&oacute;ndetallada.
     *
     * @author Juan David Hernandez
     * @param codigoDaneCentroPoblado C&oacute;digo DANE del Centro Poblado.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    private void crearDireccionConsultada(DrDireccion direccionConstruida,
            String codigoDaneCentroPoblado) throws ApplicationException {
        try {

            NegocioParamMultivalor param = new NegocioParamMultivalor();
            CityEntity cityEntityCreaDir = param.consultaDptoCiudadGeo(codigoDaneCentroPoblado);

            if (cityEntityCreaDir == null
                    || cityEntityCreaDir.getCityName() == null
                    || cityEntityCreaDir.getDpto() == null
                    || cityEntityCreaDir.getCityName().isEmpty()
                    || cityEntityCreaDir.getDpto().isEmpty()) {
                throw new ApplicationException("La Ciudad no esta"
                        + " configurada en Direcciones");
            }

            DrDireccionManager drDireccionManager = new DrDireccionManager();
            String address = drDireccionManager.getDireccion(direccionConstruida);
            String barrioStr = drDireccionManager.obtenerBarrio(direccionConstruida);
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setCodDaneVt(cityEntityCreaDir.getCodDane());
            addressRequest.setAddress(address);
            addressRequest.setCity(cityEntityCreaDir.getCityName());
            addressRequest.setState(cityEntityCreaDir.getDpto());
            if (barrioStr != null && !barrioStr.isEmpty()) {
                addressRequest.setNeighborhood(barrioStr.toUpperCase());
            }

            AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
            ResponseMessage responseMessageCreateDir
                    = addressEJBRemote.createAddress(addressRequest,
                            this.usuarioSesion, "MGL", "", direccionConstruida);
            if (responseMessageCreateDir.getIdaddress() != null
                    && !responseMessageCreateDir.getIdaddress().trim()
                            .isEmpty()) {
            }

        } catch (ApplicationException | ExceptionDB ex) {
            String msgError = "Error al momento de Crear la Dirección consultada: " + ex.getMessage();
            LOGGER.error(msgError, ex);
            throw new ApplicationException(msgError, ex);
        }
    }

	
	
    /**
     * Realiza Lookup al EJB de Address.
     *
     * @return Instancia de AddressEJBRemote.
     * @throws ApplicationException
     */
    private AddressEJBRemote lookupaddressEJBBean() throws ApplicationException {
        try {
            Context c = new InitialContext();
            return (AddressEJBRemote) c.lookup("addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote");
        } catch (NamingException ne) {
            LOGGER.error("Error en lookupaddressEJBBean. " + ne.getMessage(), ne);
            throw new ApplicationException(ne);
        }
    }

	
    /**
     * Realiza la conversi&oacute;n de listado de Entidad a listado de DTO.
     *
     * @param listaCmtDireccionDetalladaMgl Listado de entidad
     * CmtDireccionDetalladaMgl.
     * @return Listado de CmtDireccionDetalladaMglDto.
     * @throws ApplicationException
     */
    private List<CmtDireccionDetalladaMglDto> convertirDireccionDetalladaMglADto(List<CmtDireccionDetalladaMgl> listaCmtDireccionDetalladaMgl)
            throws ApplicationException {
        List<CmtDireccionDetalladaMglDto> result = null;

        if (listaCmtDireccionDetalladaMgl != null && !listaCmtDireccionDetalladaMgl.isEmpty()) {
            result = new ArrayList<>();
            for (CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl : listaCmtDireccionDetalladaMgl) {
                CmtDireccionDetalladaMglDto cmtDireccionDetalladaMglDto = cmtDireccionDetalladaMgl.convertirADto();
                if (cmtDireccionDetalladaMglDto != null) {
                    result.add(cmtDireccionDetalladaMglDto);
                }
            }
        }

        return (result);
    }

	
    /**
     * Funci&oacute;n que obtiene par&aacute;metros espec&iacute;ficos que se
     * muestran en en listado de <i>HHPP</i> encontrado en pantalla.
     *
     * @param cmtDireccionDetalladaMglList Listado de Entidad Direcci&oacute;n
     * Detallada.
     */
    private void obtenerParametrosHhpp(List<CmtDireccionDetalladaMgl> cmtDireccionDetalladaMglList)
            throws ApplicationException {
        try {
            if (cmtDireccionDetalladaMglList != null && !cmtDireccionDetalladaMglList.isEmpty()) {
                for (CmtDireccionDetalladaMgl dirDetallada : cmtDireccionDetalladaMglList) {
                    // Obtener la informacion relevante para el HHPP.
                    HhppUtils.obtenerParametrosHhpp(dirDetallada);
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al obtenerParametrosHhppListado. ", e);
            throw (e);
        } catch (Exception e) {
            LOGGER.error("Error al obtenerParametrosHhppListado. ", e);
            throw new ApplicationException("Error al obtenerParametrosHhppListado. ", e);
        }
    }

	
    /**
     * Funci&oacute;n que obtiene par&aacute;metros espec&iacute;ficos que se
     * muestran en en listado de <i>HHPP</i> encontrado en pantalla.
     *
     * @param cmtDireccionDetalladaMglList Listado de DTO Direcci&oacute;n
     * Detallada.
     */
    private void obtenerParametrosHhppDto(List<CmtDireccionDetalladaMglDto> cmtDireccionDetalladaMglDtoList)
            throws ApplicationException {
        try {
            if (cmtDireccionDetalladaMglDtoList != null && !cmtDireccionDetalladaMglDtoList.isEmpty()) {
                for (CmtDireccionDetalladaMglDto dirDetallada : cmtDireccionDetalladaMglDtoList) {
                    // Obtener la informacion relevante para el HHPP.
                    HhppUtils.obtenerParametrosHhpp(dirDetallada);
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al obtenerParametrosHhppDto: " + e.getMessage(), e);
            throw (e);
        } catch (Exception e) {
            LOGGER.error("Error al obtenerParametrosHhppListado. ", e);
            throw new ApplicationException("Error al obtenerParametrosHhppDto: " + e.getMessage(), e);
        }
    }
	
	

    /**
     * Victor Bocanegra Metodo para setear DrDireccion a CmtDireccionTabuladaDto
     *
     * @param addressGeneralDtos
     * @return
     */
    public List<CmtDireccionDetalladaMglDto> returnCmtDireccionDetalladaMglDto(List<CmtAddressGeneralDto> addressGeneralDtos) {

        List<CmtDireccionDetalladaMglDto> direccionDetalladaMglDtosLst = new ArrayList<>();

        if (addressGeneralDtos != null && !addressGeneralDtos.isEmpty()) {
            addressGeneralDtos.stream().map((addressGeneralDto) -> {
                CmtDireccionDetalladaMglDto direccionDetalladaMglDto = new CmtDireccionDetalladaMglDto();
                if (addressGeneralDto.getSplitAddres().getIdDireccionDetallada() != null && !addressGeneralDto.getSplitAddres().getIdDireccionDetallada().trim().isEmpty()) {
                    direccionDetalladaMglDto.setDireccionDetalladaId(new BigDecimal(addressGeneralDto.getSplitAddres().getIdDireccionDetallada().toUpperCase()));
                }
                if (addressGeneralDto.getSplitAddres().getBarrio() != null && !addressGeneralDto.getSplitAddres().getBarrio().trim().isEmpty()) {
                    direccionDetalladaMglDto.setBarrio(addressGeneralDto.getSplitAddres().getBarrio().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getTipoViaPrincipal() != null && !addressGeneralDto.getSplitAddres().getTipoViaPrincipal().trim().isEmpty()) {
                    direccionDetalladaMglDto.setTipoViaPrincipal(addressGeneralDto.getSplitAddres().getTipoViaPrincipal().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getNumViaPrincipal() != null && !addressGeneralDto.getSplitAddres().getNumViaPrincipal().trim().isEmpty()) {
                    direccionDetalladaMglDto.setNumViaPrincipal(addressGeneralDto.getSplitAddres().getNumViaPrincipal().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getLtViaPrincipal() != null && !addressGeneralDto.getSplitAddres().getLtViaPrincipal().trim().isEmpty()) {
                    direccionDetalladaMglDto.setLtViaPrincipal(addressGeneralDto.getSplitAddres().getLtViaPrincipal().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getNlPostViaP() != null && !addressGeneralDto.getSplitAddres().getNlPostViaP().trim().isEmpty()) {
                    direccionDetalladaMglDto.setNlPostViaP(addressGeneralDto.getSplitAddres().getNlPostViaP().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getBisViaPrincipal() != null && !addressGeneralDto.getSplitAddres().getBisViaPrincipal().trim().isEmpty()) {
                    direccionDetalladaMglDto.setBisViaPrincipal(addressGeneralDto.getSplitAddres().getBisViaPrincipal().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getCuadViaPrincipal() != null && !addressGeneralDto.getSplitAddres().getCuadViaPrincipal().trim().isEmpty()) {
                    direccionDetalladaMglDto.setCuadViaPrincipal(addressGeneralDto.getSplitAddres().getCuadViaPrincipal().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getTipoViaGeneradora() != null && !addressGeneralDto.getSplitAddres().getTipoViaGeneradora().trim().isEmpty()) {
                    direccionDetalladaMglDto.setTipoViaGeneradora(addressGeneralDto.getSplitAddres().getTipoViaGeneradora().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getNumViaGeneradora() != null && !addressGeneralDto.getSplitAddres().getNumViaGeneradora().trim().isEmpty()) {
                    direccionDetalladaMglDto.setNumViaGeneradora(addressGeneralDto.getSplitAddres().getNumViaGeneradora().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getLtViaGeneradora() != null && !addressGeneralDto.getSplitAddres().getLtViaGeneradora().trim().isEmpty()) {
                    direccionDetalladaMglDto.setLtViaGeneradora(addressGeneralDto.getSplitAddres().getLtViaGeneradora().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getNlPostViaG() != null && !addressGeneralDto.getSplitAddres().getNlPostViaG().trim().isEmpty()) {
                    direccionDetalladaMglDto.setNlPostViaG(addressGeneralDto.getSplitAddres().getNlPostViaG().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getBisViaGeneradora() != null && !addressGeneralDto.getSplitAddres().getBisViaGeneradora().trim().isEmpty()) {
                    direccionDetalladaMglDto.setBisViaGeneradora(addressGeneralDto.getSplitAddres().getBisViaGeneradora().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getCuadViaGeneradora() != null && !addressGeneralDto.getSplitAddres().getCuadViaGeneradora().trim().isEmpty()) {
                    direccionDetalladaMglDto.setCuadViaGeneradora(addressGeneralDto.getSplitAddres().getCuadViaGeneradora().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getPlacaDireccion() != null && !addressGeneralDto.getSplitAddres().getPlacaDireccion().trim().isEmpty()) {
                    direccionDetalladaMglDto.setPlacaDireccion(addressGeneralDto.getSplitAddres().getPlacaDireccion().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getMzTipoNivel1() != null && !addressGeneralDto.getSplitAddres().getMzTipoNivel1().trim().isEmpty()) {
                    direccionDetalladaMglDto.setMzTipoNivel1(addressGeneralDto.getSplitAddres().getMzTipoNivel1().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getMzTipoNivel2() != null && !addressGeneralDto.getSplitAddres().getMzTipoNivel2().trim().isEmpty()) {
                    direccionDetalladaMglDto.setMzTipoNivel2(addressGeneralDto.getSplitAddres().getMzTipoNivel2().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getMzTipoNivel3() != null && !addressGeneralDto.getSplitAddres().getMzTipoNivel3().trim().isEmpty()) {
                    direccionDetalladaMglDto.setMzTipoNivel3(addressGeneralDto.getSplitAddres().getMzTipoNivel3().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getMzTipoNivel4() != null && !addressGeneralDto.getSplitAddres().getMzTipoNivel4().trim().isEmpty()) {
                    direccionDetalladaMglDto.setMzTipoNivel4(addressGeneralDto.getSplitAddres().getMzTipoNivel4().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getMzTipoNivel5() != null && !addressGeneralDto.getSplitAddres().getMzTipoNivel5().trim().isEmpty()) {
                    direccionDetalladaMglDto.setMzTipoNivel5(addressGeneralDto.getSplitAddres().getMzTipoNivel5().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getMzValorNivel1() != null && !addressGeneralDto.getSplitAddres().getMzValorNivel1().trim().isEmpty()) {
                    direccionDetalladaMglDto.setMzValorNivel1(addressGeneralDto.getSplitAddres().getMzValorNivel1().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getMzTipoNivel6() != null && !addressGeneralDto.getSplitAddres().getMzTipoNivel6().trim().isEmpty()) {
                    direccionDetalladaMglDto.setMzTipoNivel6(addressGeneralDto.getSplitAddres().getMzTipoNivel6().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getMzValorNivel6() != null && !addressGeneralDto.getSplitAddres().getMzValorNivel6().trim().isEmpty()) {
                    direccionDetalladaMglDto.setMzValorNivel6(addressGeneralDto.getSplitAddres().getMzValorNivel6().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getMzValorNivel2() != null && !addressGeneralDto.getSplitAddres().getMzValorNivel2().trim().isEmpty()) {
                    direccionDetalladaMglDto.setMzValorNivel2(addressGeneralDto.getSplitAddres().getMzValorNivel2().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getMzValorNivel3() != null && !addressGeneralDto.getSplitAddres().getMzValorNivel3().trim().isEmpty()) {
                    direccionDetalladaMglDto.setMzValorNivel3(addressGeneralDto.getSplitAddres().getMzValorNivel3().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getMzValorNivel4() != null && !addressGeneralDto.getSplitAddres().getMzValorNivel4().trim().isEmpty()) {
                    direccionDetalladaMglDto.setMzValorNivel4(addressGeneralDto.getSplitAddres().getMzValorNivel4().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getMzValorNivel5() != null && !addressGeneralDto.getSplitAddres().getMzValorNivel5().trim().isEmpty()) {
                    direccionDetalladaMglDto.setMzValorNivel5(addressGeneralDto.getSplitAddres().getMzValorNivel5().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getCpTipoNivel1() != null && !addressGeneralDto.getSplitAddres().getCpTipoNivel1().trim().isEmpty()) {
                    direccionDetalladaMglDto.setCpTipoNivel1(addressGeneralDto.getSplitAddres().getCpTipoNivel1().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getCpTipoNivel2() != null && !addressGeneralDto.getSplitAddres().getCpTipoNivel2().trim().isEmpty()) {
                    direccionDetalladaMglDto.setCpTipoNivel2(addressGeneralDto.getSplitAddres().getCpTipoNivel2().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getCpTipoNivel3() != null && !addressGeneralDto.getSplitAddres().getCpTipoNivel3().trim().isEmpty()) {
                    direccionDetalladaMglDto.setCpTipoNivel3(addressGeneralDto.getSplitAddres().getCpTipoNivel3().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getCpTipoNivel4() != null && !addressGeneralDto.getSplitAddres().getCpTipoNivel4().trim().isEmpty()) {
                    direccionDetalladaMglDto.setCpTipoNivel4(addressGeneralDto.getSplitAddres().getCpTipoNivel4().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getCpTipoNivel5() != null && !addressGeneralDto.getSplitAddres().getCpTipoNivel5().trim().isEmpty()) {
                    direccionDetalladaMglDto.setCpTipoNivel5(addressGeneralDto.getSplitAddres().getCpTipoNivel5().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getCpTipoNivel6() != null && !addressGeneralDto.getSplitAddres().getCpTipoNivel6().trim().isEmpty()) {
                    direccionDetalladaMglDto.setCpTipoNivel6(addressGeneralDto.getSplitAddres().getCpTipoNivel6().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getCpValorNivel1() != null && !addressGeneralDto.getSplitAddres().getCpValorNivel1().trim().isEmpty()) {
                    direccionDetalladaMglDto.setCpValorNivel1(addressGeneralDto.getSplitAddres().getCpValorNivel1().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getCpValorNivel2() != null && !addressGeneralDto.getSplitAddres().getCpValorNivel2().trim().isEmpty()) {
                    direccionDetalladaMglDto.setCpValorNivel2(addressGeneralDto.getSplitAddres().getCpValorNivel2().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getCpValorNivel3() != null && !addressGeneralDto.getSplitAddres().getCpValorNivel3().trim().isEmpty()) {
                    direccionDetalladaMglDto.setCpValorNivel3(addressGeneralDto.getSplitAddres().getCpValorNivel3().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getCpValorNivel4() != null && !addressGeneralDto.getSplitAddres().getCpValorNivel4().trim().isEmpty()) {
                    direccionDetalladaMglDto.setCpValorNivel4(addressGeneralDto.getSplitAddres().getCpValorNivel4().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getCpValorNivel5() != null && !addressGeneralDto.getSplitAddres().getCpValorNivel5().trim().isEmpty()) {
                    direccionDetalladaMglDto.setCpValorNivel5(addressGeneralDto.getSplitAddres().getCpValorNivel5().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getCpValorNivel6() != null && !addressGeneralDto.getSplitAddres().getCpValorNivel6().trim().isEmpty()) {
                    direccionDetalladaMglDto.setCpValorNivel6(addressGeneralDto.getSplitAddres().getCpValorNivel6().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getItTipoPlaca() != null && !addressGeneralDto.getSplitAddres().getItTipoPlaca().trim().isEmpty()) {
                    direccionDetalladaMglDto.setItTipoPlaca(addressGeneralDto.getSplitAddres().getItTipoPlaca().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getItValorPlaca() != null && !addressGeneralDto.getSplitAddres().getItValorPlaca().trim().isEmpty()) {
                    direccionDetalladaMglDto.setItValorPlaca(addressGeneralDto.getSplitAddres().getItValorPlaca().toUpperCase());
                }
                if (addressGeneralDto.getSplitAddres().getDireccionTexto() != null && !addressGeneralDto.getSplitAddres().getDireccionTexto().trim().isEmpty()) {
                    direccionDetalladaMglDto.setDireccionTexto(addressGeneralDto.getSplitAddres().getDireccionTexto().toUpperCase());
                }
                return direccionDetalladaMglDto;
            }).forEachOrdered((direccionDetalladaMglDto) -> {
                direccionDetalladaMglDtosLst.add(direccionDetalladaMglDto);
            });

        }
        return direccionDetalladaMglDtosLst;
    }

    /**
     * Victor Bocanegra Metodo para setear DrDireccion a CmtDireccionTabuladaDto
     *
     * @param direccionTabulada
     * @return
     */
    public CmtDireccionTabuladaDto parseDrDireccionToCmtDireccionTabuladaDto(DrDireccion direccionTabulada) {
        CmtDireccionTabuladaDto direccionTabDto = new CmtDireccionTabuladaDto();
        try {

            if (direccionTabulada.getBarrio() != null && !direccionTabulada.getBarrio().trim().isEmpty()) {
                direccionTabDto.setBarrio(direccionTabulada.getBarrio().toUpperCase());
            }

            if (direccionTabulada.getIdTipoDireccion() != null && !direccionTabulada.getIdTipoDireccion().trim().isEmpty()) {
                direccionTabDto.setIdTipoDireccion(direccionTabulada.getIdTipoDireccion().toUpperCase());
            }
            if (direccionTabulada.getTipoViaPrincipal() != null && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {
                direccionTabDto.setTipoViaPrincipal(direccionTabulada.getTipoViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getNumViaPrincipal() != null && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                direccionTabDto.setNumViaPrincipal(direccionTabulada.getNumViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getLtViaPrincipal() != null && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                direccionTabDto.setLtViaPrincipal(direccionTabulada.getLtViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaP() != null && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                direccionTabDto.setNlPostViaP(direccionTabulada.getNlPostViaP().toUpperCase());
            }
            if (direccionTabulada.getBisViaPrincipal() != null && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                direccionTabDto.setBisViaPrincipal(direccionTabulada.getBisViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getCuadViaPrincipal() != null && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                direccionTabDto.setCuadViaPrincipal(direccionTabulada.getCuadViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getTipoViaGeneradora() != null && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                direccionTabDto.setTipoViaGeneradora(direccionTabulada.getTipoViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNumViaGeneradora() != null && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                direccionTabDto.setNumViaGeneradora(direccionTabulada.getNumViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getLtViaGeneradora() != null && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                direccionTabDto.setLtViaGeneradora(direccionTabulada.getLtViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaG() != null && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                direccionTabDto.setNlPostViaG(direccionTabulada.getNlPostViaG().toUpperCase());
            }
            if (direccionTabulada.getBisViaGeneradora() != null && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                direccionTabDto.setBisViaGeneradora(direccionTabulada.getBisViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getCuadViaGeneradora() != null && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                direccionTabDto.setCuadViaGeneradora(direccionTabulada.getCuadViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getPlacaDireccion() != null && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                direccionTabDto.setPlacaDireccion(direccionTabulada.getPlacaDireccion().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel1() != null && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                direccionTabDto.setMzTipoNivel1(direccionTabulada.getMzTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel2() != null && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                direccionTabDto.setMzTipoNivel2(direccionTabulada.getMzTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel3() != null && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                direccionTabDto.setMzTipoNivel3(direccionTabulada.getMzTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel4() != null && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                direccionTabDto.setMzTipoNivel4(direccionTabulada.getMzTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel5() != null && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {
                direccionTabDto.setMzTipoNivel5(direccionTabulada.getMzTipoNivel5().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel1() != null && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                direccionTabDto.setMzValorNivel1(direccionTabulada.getMzValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel6() != null && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                direccionTabDto.setMzTipoNivel6(direccionTabulada.getMzTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel6() != null && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                direccionTabDto.setMzValorNivel6(direccionTabulada.getMzValorNivel6().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel2() != null && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                direccionTabDto.setMzValorNivel2(direccionTabulada.getMzValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel3() != null && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                direccionTabDto.setMzValorNivel3(direccionTabulada.getMzValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel4() != null && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                direccionTabDto.setMzValorNivel4(direccionTabulada.getMzValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel5() != null && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                direccionTabDto.setMzValorNivel5(direccionTabulada.getMzValorNivel5().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel1() != null && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                direccionTabDto.setCpTipoNivel1(direccionTabulada.getCpTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel2() != null && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                direccionTabDto.setCpTipoNivel2(direccionTabulada.getCpTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel3() != null && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                direccionTabDto.setCpTipoNivel3(direccionTabulada.getCpTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel4() != null && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                direccionTabDto.setCpTipoNivel4(direccionTabulada.getCpTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel5() != null && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                direccionTabDto.setCpTipoNivel5(direccionTabulada.getCpTipoNivel5().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel6() != null && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                direccionTabDto.setCpTipoNivel6(direccionTabulada.getCpTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel1() != null && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                direccionTabDto.setCpValorNivel1(direccionTabulada.getCpValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel2() != null && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                direccionTabDto.setCpValorNivel2(direccionTabulada.getCpValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel3() != null && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                direccionTabDto.setCpValorNivel3(direccionTabulada.getCpValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel4() != null && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                direccionTabDto.setCpValorNivel4(direccionTabulada.getCpValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel5() != null && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                direccionTabDto.setCpValorNivel5(direccionTabulada.getCpValorNivel5().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel6() != null && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                direccionTabDto.setCpValorNivel6(direccionTabulada.getCpValorNivel6().toUpperCase());
            }

            if (direccionTabulada.getItTipoPlaca() != null && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                direccionTabDto.setItTipoPlaca(direccionTabulada.getItTipoPlaca().toUpperCase());
            }

            if (direccionTabulada.getItValorPlaca() != null && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                direccionTabDto.setItValorPlaca(direccionTabulada.getItValorPlaca().toUpperCase());
            }

            if (direccionTabulada.getDirPrincAlt() != null && !direccionTabulada.getDirPrincAlt().trim().isEmpty()) {
                direccionTabDto.setDirPrincAlt(direccionTabulada.getDirPrincAlt().toUpperCase());
            }
        } catch (Exception e) {
            LOGGER.error("Error en rl metodo: parseDrDireccionToCmtDireccionTabuladaDto: " + e.getMessage());
        }
        return direccionTabDto;

    }

    public CmtRequestConstruccionDireccionDto parserRequest(RequestConstruccionDireccion requestConstruccionDireccion) {

        CmtRequestConstruccionDireccionDto requestConstruccionDireccionDto;

        requestConstruccionDireccionDto = new CmtRequestConstruccionDireccionDto();
        requestConstruccionDireccionDto.setBarrio(requestConstruccionDireccion.getBarrio());
        requestConstruccionDireccionDto.setComunidad(requestConstruccionDireccion.getComunidad());
        requestConstruccionDireccionDto.setDireccionStr(requestConstruccionDireccion.getDireccionStr());
        requestConstruccionDireccion.getDrDireccion().setDirPrincAlt("P");
        requestConstruccionDireccionDto.setDrDireccion(requestConstruccionDireccion.getDrDireccion());
        requestConstruccionDireccionDto.setIdUsuario(requestConstruccionDireccion.getIdUsuario());
        requestConstruccionDireccionDto.setTipoAdicion(requestConstruccionDireccion.getTipoAdicion());
        requestConstruccionDireccionDto.setTipoNivel(requestConstruccionDireccion.getTipoNivel());
        requestConstruccionDireccionDto.setValorNivel(requestConstruccionDireccion.getValorNivel());
        requestConstruccionDireccionDto.setIdUsuario(this.usuarioSesion);

        return requestConstruccionDireccionDto;
    }

	
    /**
     * Reinicia los componentes de consulta.
     */
    private void reiniciarComponentesConsulta() {
        this.direccionDetalladaList = new ArrayList();
        this.filteredDireccionDetalladaList = null;
        this.listCover = new ArrayList<>();
        this.direccionDetalladaMglDtoSel = null;
    }

	
    /**
     * Reinicia los componentes asociados a la consulta de Coordenadas.
     */
    private void reiniciarComponentesCoordenadas() {
        // Restaurar los componentes de consulta:
        this.direccionDetalladaMglDtoSel = null;
        this.direccionDetalladaList = null;
        this.filteredDireccionDetalladaList = null;
        select = false;
        mostrarMapa = false;

        PrimeFaces.current().resetInputs("formPer:panelDir");

        // Reiniciar las coberturas:
        this.listCover = new ArrayList<>();
        this.coberturaSeleccionada = null;
    }

	
    /**
     * Evento llamado al momento de cambiar la Latitud.
     */
    public void onChangeLatitud() {
        // Restaurar los componentes de consulta:
        this.reiniciarComponentesCoordenadas();
    }

	
    /**
     * Evento llamado al momento de cambiar la Longitud.
     */
    public void onChangeLongitud() {
        // Restaurar los componentes de consulta:
        this.reiniciarComponentesCoordenadas();
    }

	
    /**
     * Evento llamado al momento de seleccionar una Direcci&oacute;n de la
     * tabla.
     *
     * @param event Select Event.
     * @throws MalformedURLException
     * @throws Exception
     */
    public void onSelectDireccion(SelectEvent<CmtDireccionDetalladaMglDto> event)
            throws MalformedURLException, Exception {
        if (this.direccionDetalladaMglDtoSel != null) {
            this.consultarDireccionSeleccionada(this.direccionDetalladaMglDtoSel);
        }
    }

	
    /**
     * Realiza la consulta de una Direcci&oacute;n.
     *
     * @param direccionDetalladaMglDto
     * @throws MalformedURLException
     * @throws Exception
     */
    public void consultarDireccionSeleccionada(CmtDireccionDetalladaMglDto direccionDetalladaMglDto)
            throws Exception {

        select = false;
        mostrarMapa = false;

        // Reiniciar las coberturas:
        this.listCover = new ArrayList<>();

		
        if (direccionDetalladaMglDto != null && direccionDetalladaMglDto.getDireccionDetalladaId() != null) {
            direccionDetalladaMglDtoSel = direccionDetalladaMglDto;

            CmtDireccionDetalladaRequestDto request1 = new CmtDireccionDetalladaRequestDto();
            request1.setIdDireccion(direccionDetalladaMglDto.getDireccionDetalladaId());
            request1.setSegmento(Constants.SEGMENTO_CONSULTA);
            request1.setProyecto(Constants.PROYECTO_CONSULTA);

            CmtAddressResponseDto response = cmtDireccionDetalleMglFacadeLocal.consultarDireccion(request1);

            // Convertir objeto a JSON:
            JSONObject jsonObj = this.objectToJSONObject(response);
			 

            String typeMen = jsonObj.getString("messageType");
            String mensaje = jsonObj.getString("message");

            if (typeMen.equalsIgnoreCase("I")) {
                JSONArray listCob = null;
                JSONObject addresses = jsonObj.getJSONObject("addresses");
                if (addresses != null) {
                    try {
                        latitud = addresses.getString("latitudeCoordinate");
                    } catch (JSONException e) {
                        LOGGER.warn(e.getMessage());
                        latitud = null;
                    }

                    try {
                        longitud = addresses.getString("lengthCoordinate");
                    } catch (JSONException e) {
                        LOGGER.warn(e.getMessage());
                        longitud = null;
                    }

                    try {
                        listCob = addresses.getJSONArray("listCover");
                    } catch (JSONException e) {
                        LOGGER.warn(e.getMessage());
                        listCob = null;
                    }
                }
				

                if (listCob != null && listCob.length() > 0) {

                    listCover = new ArrayList<>();

                    for (int i = 0; i < listCob.length(); i++) {
                        JSONObject coverJsonObj = listCob.getJSONObject(i);
                        CmtCoverDto cobCoverDto
                                = this.getCoverDTO(coverJsonObj,
                                        direccionDetalladaMglDto.getDireccionId(),
                                        direccionDetalladaMglDto.getSubdireccionId());
                        if (cobCoverDto != null) {
                            listCover.add(cobCoverDto);
                        }
                    }
                    listCoverAux = listCover;
                    //Busco si ya existe un token para esta consulta
                    ResultModFacDirMgl resultModFacDirMgl = resultModFacDirMglFacadeLocal.findBytoken(tokenFactibilidades);
                    if (resultModFacDirMgl != null && resultModFacDirMgl.getDireccionDetallada() != null) {
                        // Modifico el registro con el id de la nueva direccion
                        resultModFacDirMgl.setDireccionDetallada(direccionDetalladaMglDtoSel.getDireccionDetalladaId());
                        resultModFacDirMglFacadeLocal.update(resultModFacDirMgl);
                    } else {
                        // Si no existe  creo el registro con el id de la direccion  
                        ResultModFacDirMgl resultModFacDirMglNew = new ResultModFacDirMgl();
                        resultModFacDirMglNew.setTokenConsulta(tokenFactibilidades);
                        resultModFacDirMglNew.setDireccionDetallada(direccionDetalladaMglDtoSel.getDireccionDetalladaId());
                        resultModFacDirMglNew = resultModFacDirMglFacadeLocal.create(resultModFacDirMglNew);
                        if (resultModFacDirMglNew.getResultModFactDirId() != null) {
                            LOGGER.info("Registro: " + resultModFacDirMglNew.getResultModFactDirId() + " creado en repositorio ");
                        }
                    }
                } else {
                    msnError = "La dirección seleccionada no posee cobertura.";
                    alertProcesoExistoso();
                }

                select = true;

                PrimeFacesUtil.scrollTo("panelCoordenadas");

            } else {
                msnError = mensaje;
                alertProcesoExistoso();
            }
        }

    }

	
    /**
     * Realiza la b&uacute;squeda del Mapa a trav&eacute;s de las Coordenadas.
     *
     * @throws MalformedURLException
     * @throws Exception
     */
    public void mostrarMapaByCoordenadas() throws Exception {        
        showPanelDirecciones = false;
        mostrarMapa = true;
        model = new DefaultMapModel();
        this.coberturaSeleccionada = null;

        if ((latitud == null || latitud.isEmpty()) && (longitud == null || longitud.isEmpty())) {
            msnError = "No se puede mostrar cobertura para una dirección sin coordenadas.";
            alerts();
		 
        } else {
            if (direccionDetalladaMglDtoSel == null) {
                listCover = new ArrayList<>();
            }

            String expRegCoor = this.consultarParametro(Constants.EXP_REG_VAL_COOR);

            Pattern pat = Pattern.compile(expRegCoor);

            Matcher matcher = pat.matcher(longitud.trim());
            boolean validaLongitud = false;
            while (matcher.find()) {
                validaLongitud = true;
            }
            matcher = pat.matcher(latitud.trim());
            boolean validaLatitud = false;
            while (matcher.find()) {
                validaLatitud = true;
            }

            if (validaLatitud && validaLongitud) {

                if (direccionDetalladaMglDtoSel == null) {
                    if (responsesCentroPoblado != null
                            && !responsesCentroPoblado.getGeoCodigoDane().isEmpty()) {

                        String latitudMod = latitud.replace(",", ".");
                        String longitudMod = longitud.replace(",", ".");

                        center = "" + latitudMod + ", " + longitudMod + "";

                        CmtRequestHhppByCoordinatesDto request1 = new CmtRequestHhppByCoordinatesDto();
                        request1.setUnitsNumber(Constants.UNITS_NUMBER);
                        request1.setLatitude(latitudMod);
                        request1.setLongitude(longitudMod);
                        request1.setDeviationMtr(Constants.DEVIATION_MTR);
                        request1.setCiudad(responsesCentroPoblado.getGeoCodigoDane());

                        CmtAddressGeneralResponseDto response
                                = cmtDireccionDetalleMglFacadeLocal.findDireccionByCoordenadas(request1);

                        JSONObject jsonObj = this.objectToJSONObject(response);
                        String typeMen = jsonObj.getString("messageType");
                        String mensaje = jsonObj.getString("message");

                        if (typeMen.equalsIgnoreCase("I")) {
                            JSONArray listDir = jsonObj.getJSONArray("listAddresses");
                            BigDecimal idDetallada = null;
                            String dirTexto = null;
                            // ID de la Direccion asociada a la Detallada.
                            BigDecimal idDireccionDD = null;
                            // ID de la Subdireccion asociada a la Detallada.
                            BigDecimal idSubdireccionDD = null;

                            if (listDir != null && listDir.length() > 0) {

                                // Obtenemos el primer registro del listado de direcciones.
                                JSONObject splitAddressJsonObj = listDir.getJSONObject(0).getJSONObject("splitAddres");
                                CmtDireccionDetalladaMglDto cmtDireccionDetalladaMglDto
                                        = this.getDireccionDetalladaDTO(splitAddressJsonObj);
                                if (cmtDireccionDetalladaMglDto != null) {
                                    idDetallada = cmtDireccionDetalladaMglDto.getDireccionDetalladaId();
                                    dirTexto = cmtDireccionDetalladaMglDto.getDireccionTexto();
                                    idDireccionDD = cmtDireccionDetalladaMglDto.getDireccionId();
                                    idSubdireccionDD = cmtDireccionDetalladaMglDto.getSubdireccionId();
                                }

                                if (idDetallada != null) {

                                    CmtDireccionDetalladaRequestDto requestDireccion = new CmtDireccionDetalladaRequestDto();
                                    requestDireccion.setIdDireccion(idDetallada);
                                    requestDireccion.setSegmento(Constants.SEGMENTO_CONSULTA);
                                    requestDireccion.setProyecto(Constants.PROYECTO_CONSULTA);

                                    CmtAddressResponseDto responseDireccion
                                            = cmtDireccionDetalleMglFacadeLocal.consultarDireccion(requestDireccion);

                                    jsonObj = this.objectToJSONObject(responseDireccion);

                                    String typeMen1 = jsonObj.getString("messageType");
                                    String mensaje1 = jsonObj.getString("message");

                                    if (typeMen1.equalsIgnoreCase("I")) {
                                        JSONArray listCob = null;
                                        JSONObject addresses = jsonObj.getJSONObject("addresses");
                                        if (addresses != null) {
                                            try {
                                                listCob = addresses.getJSONArray("listCover");
                                            } catch (JSONException e) {
                                                LOGGER.warn(e.getMessage());
                                                listCob = null;
                                            }
                                        }

                                        if (listCob != null && listCob.length() > 0) {

                                            listCover = new ArrayList<>();

                                            for (int i = 0; i < listCob.length(); i++) {
                                                JSONObject coverJsonObj = listCob.getJSONObject(i);
                                                CmtCoverDto cobCoverDto
                                                        = this.getCoverDTO(coverJsonObj, idDireccionDD, idSubdireccionDD);
                                                if (cobCoverDto != null) {
                                                    listCover.add(cobCoverDto);
                                                }
                                            }
                                            listCoverAux = listCover;
                                            latLng = new LatLng(new Double(latitudMod), new Double(longitudMod));
                                            model.addOverlay(new Marker(latLng, dirTexto != null ? dirTexto : "M1"));
                                            mostrarMapa = true;

                                        } else {
                                            msnError = "No existen tecnologias asociadas a la dirección.";
                                            alertProcesoExistoso();
                                            latLng = new LatLng(new Double(latitudMod), new Double(longitudMod));
                                            model.addOverlay(new Marker(latLng, dirTexto != null ? dirTexto : "M1"));
                                            mostrarMapa = true;
                                        }
                                    } else {
                                        msnError = mensaje1;
                                        alertProcesoExistoso();
                                    }
                                    latLng = new LatLng(new Double(latitudMod), new Double(longitudMod));
                                    model.addOverlay(new Marker(latLng, dirTexto != null ? dirTexto : "M1"));
                                    mostrarMapa = true;
                                }
                            }
                        } else {
                            msnError = mensaje;
                            alertProcesoExistoso();
                        }
                    } else {
                        msnError = "Debe seleccionar un centro poblado para la busqueda";
                        alertProcesoExistoso();
                    }

                } else {

                    String latitudMod = latitud.replace(",", ".");
                    String longitudMod = longitud.replace(",", ".");
                    center = "" + latitudMod + ", " + longitudMod + "";

                    latLng = new LatLng(new Double(latitudMod), new Double(longitudMod));
                    model.addOverlay(new Marker(latLng, direccionDetalladaMglDtoSel != null ? direccionDetalladaMglDtoSel.getDireccionTexto() : "M1"));

                    mostrarMapa = true;
                }

                if (mostrarMapa) {
                    // Hacer Scroll en el panel de Longitud:
                    PrimeFacesUtil.scrollTo("contenidoMapa");
                }

            } else if (!validaLatitud && !validaLongitud) {
                msnError = "Formato invalido de Latitud y Longitud: por favor ingrese valores como: "
                        + "Longitud= -4,5678996 : Latitud=6,75445748";
                alerts();
            } else if (!validaLatitud) {
                msnError = "Formato invalido de Latitud: por favor ingrese valores como: "
                        + "Latitud= -4,5678996 : 6,75445748";
                alerts();
            } else if (!validaLongitud) {
                msnError = "Formato invalido de Longitud: por favor ingrese valores como: "
                        + "Longitud= -4,5678996 : 6,75445748";
                alerts();
            }
        }
    }

	
	
    /**
     * Construye un DTO de Direcci&oacute;n Detallada, a partir del JSON Object
     * <i>splitAddres</i>, obtenido del response.
     *
     * @param splitAddress Objeto JSON que contiene la informaci&oacute;n de la
     * Direcci&oacute;n Detallada.
     * @return Data Transfer Object de Direcci&oacute;n Detallada.
     */
    private CmtDireccionDetalladaMglDto getDireccionDetalladaDTO(JSONObject splitAddress) {
        CmtDireccionDetalladaMglDto cmtDireccionDetalladaMglDto = null;

        if (splitAddress != null) {
            String idDetallada = splitAddress.getString("idDireccionDetallada");
            String dirTexto = splitAddress.getString("direccionTexto");

            BigDecimal direccionId;
            try {
                direccionId = splitAddress.getBigDecimal("direccionId");
            } catch (JSONException e) {
                direccionId = null;
            }
            BigDecimal subdireccionId;
            try {
                subdireccionId = splitAddress.getBigDecimal("subdireccionId");
            } catch (JSONException e) {
                subdireccionId = null;
            }

			
            // Construir un nuevo DTO y asociarlo al listado de direcciones:
            cmtDireccionDetalladaMglDto = new CmtDireccionDetalladaMglDto();
            cmtDireccionDetalladaMglDto.setDireccionTexto(dirTexto);
            if (idDetallada != null) {
                cmtDireccionDetalladaMglDto.setDireccionDetalladaId(new BigDecimal(idDetallada));
            }
            cmtDireccionDetalladaMglDto.setDireccionId(direccionId);
            cmtDireccionDetalladaMglDto.setSubdireccionId(subdireccionId);
        }

        return (cmtDireccionDetalladaMglDto);
    }

	
    /**
     * Construye un DTO de Cobertura, a partir del JSON Object <i>listCover</i>,
     * obtenido del response, y la Direcci&oacute;n y SubDirecci&oacute;n del
     * registro de Direcci&oacute;n Detallada.
     *
     * @param coverJsonObj Objeto JSON que contiene la informaci&oacute;n de la
     * Cobertura.
     * @param idDireccion Identificador de la Direcci&oacute;n del registro de
     * Direcci&oacute;n Detallada.
     * @param idSubDireccion Identificador de la SubDirecci&oacute;n del
     * registro de Direcci&oacute;n Detallada.
     * @return Data Transfer Object de Cobertura.
     */
    private CmtCoverDto getCoverDTO(JSONObject coverJsonObj, BigDecimal idDireccion, BigDecimal idSubDireccion)
            throws ApplicationException {
        CmtCoverDto coverDto = null;

        if (coverJsonObj != null) {
            coverDto = new CmtCoverDto();
            coverDto.setTechnology(coverJsonObj.getString("technology"));
            coverDto.setNode(coverJsonObj.getString("node"));
            if (coverJsonObj.has("state")) {
                coverDto.setState(coverJsonObj.getString("state"));
            } else {
                coverDto.setState(null);
            }

            if (coverJsonObj.getString("technology").equalsIgnoreCase("ZONA_3G") || coverJsonObj.getString("technology").equalsIgnoreCase("ZONA_4G")
                    || coverJsonObj.getString("technology").equalsIgnoreCase("NODO_ZONA_5G")) {
                coverDto.setZona3G4G5G(true);
            }

				
            if (coverDto.getNode() != null && !coverDto.getNode().isEmpty()
                    && idDireccion != null && idSubDireccion != null) {
                // Realiza la busqueda del Nodo por Codigo.
                NodoMgl nodoMgl = mapNodos.get(coverDto.getNode());
                if (nodoMgl == null) {
                    nodoMgl = nodoMglFacadeLocal.findByCodigo(coverDto.getNode());
                    if (nodoMgl != null) {
                        mapNodos.put(coverDto.getNode(), nodoMgl);
                    }
                }

                if (nodoMgl != null && nodoMgl.getNodId() != null) {
                    // Realizar la consulta en Tecnologia Habilitada por ID Nodo y ID Direccion:
                    List<HhppMgl> listadoTecHab
                            = hhppMglFacadeLocal.findHhppByNodoDireccionYSubDireccion(
                                    nodoMgl.getNodId(),
                                    idDireccion,
                                    idSubDireccion);

                    if (listadoTecHab != null && !listadoTecHab.isEmpty()) {
                        coverDto.setHhppExistente(true);
                    } else {
                        coverDto.setHhppExistente(false);
                    }
                }
            }
        }

        return (coverDto);
    }

	
    /**
     * Muestra el Panel de Consulta por <b>Coordenadas</b>.
     */
    public void ingresarCoordenadas() {
        volverPanelInicial = "ingresarCoordenadas";
        // Marcar la opcion seleccionada:
        this.opcionSeleccionada = FUNCIONALIDAD_INGRESAR_COORDENADAS;

        // Habilitar el evento Point Select:
        this.pointSelectEnabled = true;

        listCover = new ArrayList<>();
        showPanelBusquedaDireccion = false;
        showPanelCoordenadas = true;
        showPanelBusquedaCentro = true;
        ciudadesList = new ArrayList<>();
        centroPobladoList = new ArrayList<>();
        responsesCentroPoblado = null;
        mostrarMapa = false;
        mostrarSolicitudHhpp = false;
        mostrarSolicitudHhppCcmm = false;

        limpiarPanelDir();
    }

    public void cerrarMapa() {
        volverPanelInicial = "cerrarMapa";
        mostrarMapa = false;
        mostrarSolicitudHhpp = false;
        mostrarSolicitudHhppCcmm = false;
    }

	
    /**
     * Muestra el Panel de Consulta por <b>Punto de Ubicaci&oacute;n</b>.
     */
    public void verMapaIngresarPunto() {
        volverPanelInicial = "verMapaIngresarPunto";
        // Marcar la opcion seleccionada:
        this.opcionSeleccionada = FUNCIONALIDAD_PUNTO_UBICACION;

        // Activar el evento Point Select:
        this.pointSelectEnabled = true;
        showPanelDirecciones = false;
        showPanelBusquedaDireccion = false;
        showPanelBusquedaCentro = true;
        showPanelCoordenadas = true;
        responsesCentroPoblado = null;
        ciudadesList = new ArrayList<>();
        centroPobladoList = new ArrayList<>();
        mostrarMapa = true;
        mostrarSolicitudHhpp = false;
        mostrarSolicitudHhppCcmm = false;
        listCover = new ArrayList<>();
        limpiarPanelDir();

        // Reiniciar el modelo del mapa.
        this.model = new DefaultMapModel();

		
        // Ejecutar JavaScript para posicionar el centro con la ubicacion actual.
        PrimeFacesUtil.execute("findMeAndUpdateCenter();");

    }

	
    /**
     * Evento llamado al momento de dar clic en un punto del Mapa.
     *
     * @param event Evento Point Select.
     */
    public void onPointSelect(PointSelectEvent event) {

        if (pointSelectEnabled) {
            // Restaurar los componentes de consulta:
            this.reiniciarComponentesConsulta();

			
            model = new DefaultMapModel();
            latLng = event.getLatLng();

            String latitudMod = String.valueOf(latLng.getLat()).replace(",", ".");
            String longitudMod = String.valueOf(latLng.getLng()).replace(",", ".");

            latLng = new LatLng(new Double(latitudMod), new Double(longitudMod));
            model.addOverlay(new Marker(latLng, direccionDetalladaMglDtoSel != null ? direccionDetalladaMglDtoSel.getDireccionTexto() : "M1"));
            latitud = latitudMod;
            longitud = longitudMod;
            center = "" + latitud + ", " + longitud + "";
        }
    }

	
    /**
     * Muestra el Panel de Consulta por <b>GPS</b>.
     */
    public void consultarUbicacion() {

        volverPanelInicial = "consultarUbicacion";
        // Marcar la opcion seleccionada:
        this.opcionSeleccionada = FUNCIONALIDAD_GPS;

        // Deshabilitar el evento Point Select:
        this.pointSelectEnabled = false;

        limpiarPanelDir();

        longitud = "";
        latitud = "";
        showPanelBusquedaCentro = true;
        showPanelCoordenadas = true;
        showPanelDirecciones = false;
        showPanelBusquedaDireccion = false;
        responsesCentroPoblado = null;
        ciudadesList = new ArrayList<>();
        centroPobladoList = new ArrayList<>();
        listCover = new ArrayList<>();
        mostrarMapa = false;
        mostrarSolicitudHhpp = false;
        mostrarSolicitudHhppCcmm = false;

        PrimeFacesUtil.execute("findMe();");
    }

	
    /**
     * Realiza la b&uacute;squeda de Direcciones cercanas, referente a las
     * Coordenadas espec&iacute;ficadas, de acuerdo a una distancia de
     * desviaci&oacute;n.
     */
    public void consultarDireccionesCercanasByCoordenadas() {

        try {
            // Inicializar el listado de direcciones resultante.           
            this.direccionDetalladaList = new ArrayList<>();

            String expRegCoor = this.consultarParametro(Constants.EXP_REG_VAL_COOR);

            Pattern pat = Pattern.compile(expRegCoor);

            Matcher matcher = pat.matcher(longitud.trim());
            boolean validaLongitud = false;
            while (matcher.find()) {
                validaLongitud = true;
            }
            matcher = pat.matcher(latitud.trim());
            boolean validaLatitud = false;
            while (matcher.find()) {
                validaLatitud = true;
            }

            if (validaLatitud && validaLongitud) {

                if (direccionDetalladaMglDtoSel == null) {
                    if (responsesCentroPoblado != null
                            && !responsesCentroPoblado.getGeoCodigoDane().isEmpty()) {

                        String latitudMod = latitud.replace(",", ".");
                        String longitudMod = longitud.replace(",", ".");

                        center = "" + latitudMod + ", " + longitudMod + "";

                        CmtRequestHhppByCoordinatesDto request1 = new CmtRequestHhppByCoordinatesDto();
                        request1.setUnitsNumber(MAX_UNITS_NUMBER);
                        request1.setLatitude(latitudMod);
                        request1.setLongitude(longitudMod);
                        // Metros de desviacion.
                        request1.setDeviationMtr(Constants.DEVIATION_MTR);
                        request1.setCiudad(responsesCentroPoblado.getGeoCodigoDane());

                        try {
                            List<CmtDireccionDetalladaMgl> listaDireccionDetallada
                                    = cmtDireccionDetalleMglFacadeLocal.findDireccionDetalladaByCoordenadas(request1);

                            if (listaDireccionDetallada != null && !listaDireccionDetallada.isEmpty()) {
                                if (OBTENER_INFORMACION_ADICIONAL_HHPP) {
                                    // Obtener la informacion adicional asociada a cada direccion:
                                    this.obtenerParametrosHhpp(listaDireccionDetallada);
                                }

                                listaDireccionDetallada.stream().map((cmtDireccionDetalladaMgl) -> cmtDireccionDetalladaMgl.convertirADto()).forEachOrdered((cmtDireccionDetalladaMglDto) -> {
                                    // Construir un DTO a partir de la entidad, y adicionarlo a la lista:
                                    direccionDetalladaList.add(cmtDireccionDetalladaMglDto);
                                });
                            } else {
                                msnError = "No se encontraron resultados en la búsqueda.";
                                alertProcesoExistoso();

                            }
                        } catch (ApplicationException e) {
                            // Imprimir en pantalla el mensaje de validacion de la consulta:
                            msnError = e.getMessage();
                            alerts();
                        }
                    } else {
                        msnError = "Debe seleccionar un centro poblado para la busqueda";
                        alerts();
                    }

                } else {

                    String latitudMod = latitud.replace(",", ".");
                    String longitudMod = longitud.replace(",", ".");
                    center = "" + latitudMod + ", " + longitudMod + "";

                    latLng = new LatLng(new Double(latitudMod), new Double(longitudMod));
                    model.addOverlay(new Marker(latLng, direccionDetalladaMglDtoSel != null ? direccionDetalladaMglDtoSel.getDireccionTexto() : "M1"));

                    mostrarMapa = true;
                }

                if (mostrarMapa) {
                    // Hacer Scroll en el panel de Longitud:
                    PrimeFacesUtil.scrollTo("contenidoMapa");
                }

            } else if (!validaLatitud && !validaLongitud) {
                msnError = "Formato invalido de Latitud y Longitud: por favor ingrese valores como: "
                        + "Longitud= -4,5678996 : Latitud=6,75445748";
                alerts();
            } else if (!validaLatitud) {
                msnError = "Formato invalido de Latitud: por favor ingrese valores como: "
                        + "Latitud= -4,5678996 : 6,75445748";
                alerts();
            } else if (!validaLongitud) {
                msnError = "Formato invalido de Longitud: por favor ingrese valores como: "
                        + "Longitud= -4,5678996 : 6,75445748";
                alerts();
            }
           
            if(direccionDetalladaList != null && !direccionDetalladaList.isEmpty() ){
                
                showPanelBusquedaDireccion = false;
                showPanelBusquedaCentro = false;
                mostrarMapa = false;
                showPanelDirecciones = true;
                showPanelCoordenadas = true;
                mostrarSolicitudHhpp = false;
                mostrarSolicitudHhppCcmm = false;

            } else {
                if (volverPanelInicial.equals("verPanelDireccion")) {
                    showPanelDirecciones = true;
                    mostrarSolicitudHhpp = false;
                    mostrarSolicitudHhppCcmm = false;
                } else if (volverPanelInicial.equals("verMapaIngresarPunto")) {
                    showPanelBusquedaCentro = true;
                    showPanelCoordenadas = true;
                    mostrarMapa = true;
                    mostrarSolicitudHhpp = false;
                    mostrarSolicitudHhppCcmm = false;
				 
                } else {
                    showPanelBusquedaCentro = true;
                    showPanelCoordenadas = true;
                    mostrarSolicitudHhpp = false;
                    mostrarSolicitudHhppCcmm = false;
                }
            }

        } catch (ApplicationException e) {
            msnError = e.getMessage();
            alerts();
        }
    }

	
    /**
     * Convierte un Objeto tradicional en Objeto JSON.
     *
     * @param o
     * @return Object to <b>JSONObject</b>.
     */
    private JSONObject objectToJSONObject(Object o) {
        String jsonString = new Gson().toJson(o);
        return (new JSONObject(jsonString));
    }

	
    /**
     * Realiza la consulta del valor de un par&aacute;metro, a trav&eacute;s de
     * su acr&oacute;nimo.
     *
     * @param acronimo Acr&oacute;nimo del Par&aacute;metro a buscar.
     * @return Valor del Par&aacute;metro.
     * @throws ApplicationException
     */
    private String consultarParametro(String acronimo) throws ApplicationException {
        String valor = null;

        try {
            valor
                    = !parametrosMglFacadeLocal.findByAcronimo(acronimo).isEmpty() ?
                            parametrosMglFacadeLocal.findByAcronimo(acronimo).iterator().next().getParValor() : null;
        } catch (NullPointerException e) {
            throw new ApplicationException("No se encuentra configurado el parámetro '" + acronimo + "'.");
        }

        return (valor);
    }

    /**
     * Retorna al panel de Busqueda
     */
    public void volverPanelBusquedaDireccion() throws ApplicationException {

        if (volverPanelInicial.equals("verPanelDireccion")) {
            verPanelDireccion();
        } else if (volverPanelInicial.equals("verMapaIngresarPunto")) {
            verMapaIngresarPunto();
        } else if (volverPanelInicial.equals("consultarUbicacion")) {
            consultarUbicacion();
        } else if (volverPanelInicial.equals("ingresarCoordenadas")) {
            ingresarCoordenadas();
        } else {
            cerrarMapa();
        }
    }

    /**
     * Retorna al panel de Direccion
     */
    public void volverPanelDireccion() {
        showPanelDirecciones = true;
        mostrarMapa = false;
        mostrarSolicitudHhpp = false;
        mostrarSolicitudHhppCcmm = false;
    }

    /**
     * Actualiza el centro, basado en el par&aacute;metro <i>centro</i>, enviado
     * desde JavaScript.
     */
    public void updateCenter() {
        if (Strings.isEmpty(latitud) || Strings.isEmpty(longitud)) {
            // Establecer un centro por defecto.
            center = "4.60365529, -74.09208697";
        } else {
            center = latitud + ", " + longitud;
        }

        this.mostrarMapa = true;
        this.coordinatesEnabled = false;

        // Actualizar el panel del Mapa:
        PrimeFacesUtil.update("@widgetVar(contenidoMapa)");
    }

	
    /**
     * Evento llamado al momento de seleccionar una Cobertura del listado.
     */
    public void onSelectCobertura() {
        try {
            if (this.coberturaSeleccionada != null) {

                if (coberturaSeleccionada.isIsChecked()) {
                    model.getPolygons().remove(coberturaSeleccionada.getPoligono());
                    coberturaSeleccionada.setIsChecked(false);
                } else {
                    String codigoNodo = this.coberturaSeleccionada.getNode();
                    if (!Strings.isEmpty(codigoNodo)) {
                        // Realizar la busqueda del Nodo a partir de su codigo:
                        NodoMgl nodoMgl = mapNodos.get(codigoNodo);
                        if (nodoMgl == null) {
                            // Realizar la busqueda en base de datos:
                            nodoMgl = nodoMglFacadeLocal.findByCodigo(codigoNodo);
                        } else {
                            mapNodos.put(codigoNodo, nodoMgl);
                        }

                        if (nodoMgl != null && nodoMgl.getNodId() != null) {
                            BigDecimal idNodoVertice = nodoMgl.getNodId();

                            // Realizar la consulta del Poligono a partir del vertice:
                            List<NodoPoligono> poligonoList
                                    = nodoPoligonoFacadeLocal.findByNodoVertice(idNodoVertice);

                            if (poligonoList != null && !poligonoList.isEmpty()) {
                                // Construir el Poligono:
                                Polygon polygon = new Polygon();
                                polygon.setStrokeColor("#FF9900");
                                polygon.setFillColor("#FF9900");
                                polygon.setStrokeOpacity(0.7);
                                polygon.setFillOpacity(0.7);

                                // Mapear los nodos en el poligono:
                                for (NodoPoligono nodoPoligono : poligonoList) {
                                    if (nodoPoligono.getX() != null && nodoPoligono.getY() != null) {
                                        double longitudNodo = nodoPoligono.getX().doubleValue();
                                        double latitudNodo = nodoPoligono.getY().doubleValue();

                                        latLng = new LatLng(latitudNodo, longitudNodo);
                                        polygon.getPaths().add(latLng);
                                    }
                                }
                                model.addOverlay(polygon);
                                coberturaSeleccionada.setIsChecked(true);
                                coberturaSeleccionada.setPoligono(polygon);
                                // Actualizar el panel del Mapa:
                                PrimeFacesUtil.update("@widgetVar(contenidoMapa)");

                            } else {
                                msnError = "No se encontró información del Polígono para la tecnología '"
                                        + this.coberturaSeleccionada.getTechnology() + "'.";
                                alertProcesoExistoso();

                            }
                        } else {
                            msnError = "No se encontró la información del nodo vértice para la tecnología '"
                                    + this.coberturaSeleccionada.getTechnology() + "'.";
                            alerts();
                            
                        }
                    }
                }

            } else {
                msnError = "Debe seleccionar una tecnología para consultar su información.";
                alerts();
            }
        } catch (ApplicationException e) {
            msnError = "Ha ocurrido un error al momento de consultar la Cobertura: "
                    + e.getMessage();
            alerts();
        }
    }

    public List<CmtSubEdificioMgl> getSelectedCmtSubEdificioMgl(CmtCuentaMatrizMgl cuentaMatrizSeleccionada) {
        try {
            List<CmtSubEdificioMgl> listCmtSubEdificioMgl = null;
            if (cuentaMatrizSeleccionada != null) {
                listCmtSubEdificioMgl = cuentaMatrizSeleccionada.getSubEdificiosValidacionesHhpp();
            }
            return listCmtSubEdificioMgl;
        } catch (Exception e) {
            return null;
        }
    }

	
    public boolean validacionTecnologiaBiTipoApto(CmtDireccionDetalladaMglDto direccionSeleccionada, CmtBasicaMgl tecnologiaSelected) throws ApplicationException {
        try {
            if (direccionSeleccionada != null && tecnologiaSelected != null) {

                if (tecnologiaSelected.getIdentificadorInternoApp() != null
                        && !tecnologiaSelected.getIdentificadorInternoApp().isEmpty()
                        && tecnologiaSelected.getIdentificadorInternoApp()
                                .equalsIgnoreCase(co.com.claro.mgl.utils.Constant.HFC_BID)) {

                    if (direccionSeleccionada.getCpTipoNivel5() != null
                            && direccionSeleccionada.getCpTipoNivel5().equalsIgnoreCase("APARTAMENTO")) {
                        msnError = "No es posible crear la solicitud en esta tecnología para este tipo de dirección de Apartamento. ";
                        alerts();
                        return false;
                    } else {
                        if (direccionSeleccionada.getCpTipoNivel5() != null
                                && direccionSeleccionada.getCpTipoNivel5().equalsIgnoreCase("INTERIOR")) {
                            msnError = "No es posible crear la solicitud en esta tecnología para este tipo de dirección de Interior. ";
                            alerts();
                            return false;
                        } else {
                            if (direccionSeleccionada.getCpTipoNivel5() != null
                                    && direccionSeleccionada.getCpTipoNivel5().equalsIgnoreCase("LOCAL")) {
                                msnError = "No es posible crear la solicitud en esta tecnología para este tipo de dirección de Local. ";
                                alerts();
                                return false;
                            } else {
                                if (direccionSeleccionada.getCpTipoNivel5() != null
                                        && direccionSeleccionada.getCpTipoNivel5().equalsIgnoreCase("CASA")
                                        && (direccionSeleccionada.getCpValorNivel5() != null
                                        && !direccionSeleccionada.getCpValorNivel5().isEmpty())) {
                                    msnError = "No es posible crear la solicitud en esta tecnología para direcciones tipo CASA con valor.  ";
                                    alerts();
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            msnError = "Ha ocurrido un error en la validación de tipo de apto para tecnología BI "
                    + e.getMessage();
            alerts();
            return false;
        }
    }

    public boolean validarDatosSolicitud() {
        try {
            //si la solicitud es sobre CCMM
            if (mostrarSolicitudHhppCcmm) {
                if (subEdificioSeleccionado != null && !subEdificioSeleccionado.equals(BigDecimal.ZERO)) {
                    if (origenSolicitud != null && !origenSolicitud.equals(BigDecimal.ZERO)) {
                        if (asesorCcmm != null && !asesorCcmm.isEmpty()) {

                            if (correoAsesor != null && !correoAsesor.isEmpty()) {

                                if (!validarCorreo(correoAsesor)) {
                                    msnError = "El campo correo asesor no tiene el formato requerido";
                                    alerts();
                                    return false;
                                }

                                if (!validarDominioCorreos(correoAsesor)) {
                                    msnError = "El campo asesor debe ser un correo con dominio telmexla.com,"
                                            + " telmex.com.co, telmex.com, comcel.com.co o claro.com.co";
                                    alerts();
                                    return false;
                                }

                                if (correoCopiaA != null && !correoCopiaA.isEmpty()) {

                                    if (!validarCorreo(correoCopiaA)) {
                                        msnError = "El campo Correo Copia A: no tiene el formato requerido";
                                        alerts();
                                        return false;
                                    }
                                    if (!validarDominioCorreos(correoAsesor)) {
                                        msnError = "El campo asesor debe ser un correo con dominio telmexla.com,"
                                                + " telmex.com.co, telmex.com, comcel.com.co o claro.com.co";
                                        alerts();
                                        return false;
                                    }
                                }

                                if (telefonoContacto != null && !telefonoContacto.isEmpty()) {
                                    if (!isNumeric(telefonoContacto)) {
                                        msnError = "El número de teléfono del contacto debe ser solo números.";
                                        alerts();
                                        return false;
                                    }

                                    if (telefonoContacto.length() == 7 || telefonoContacto.length() == 10) {
                                    } else {
                                        msnError = "El telefono de contacto debe ser de 7 ó 10 digitos por favor.";
                                        alerts();
                                        return false;
                                    }

                                    if (observacionesSolicitud != null && !observacionesSolicitud.isEmpty()) {
                                        if (observacionesSolicitud.length() > 400) {
                                            msnError = "Las observaciones de la solicitud debe tener menos de 400 caracteres.";
                                            alerts();
                                            return false;
                                        }

                                        return true;
                                    } else {
                                        msnError = "Ingrese una observación a la solicitud por favor.";
                                        alerts();
                                        return false;
                                    }

                                } else {
                                    msnError = "Ingrese un teléfono de asesor por favor.";
                                    alerts();
                                    return false;
                                }

                            } else {
                                msnError = "Ingrese un correo de asesor por favor ";
                                alerts();
                                return false;
                            }

                        } else {
                            msnError = "Ingrese un nombre de Asesor por favor ";
                            alerts();
                            return false;
                        }

                    } else {
                        msnError = "Ingrese un Tipo de Solicitud por favor. ";
                        alerts();
                        return false;
                    }
                } else {
                    msnError = "Debe seleccionar un SubEdificio de la cuenta matriz por favor. ";
                    alerts();
                    return false;
                }
            } else {

                //Si la solicitud es de HHPP barrio abierto
                if (areaCanal != null && !areaCanal.isEmpty()) {
                    if (tipoVivienda != null && !tipoVivienda.isEmpty()) {
                        if (nombreContacto != null && !nombreContacto.isEmpty()) {

                            if (telefonoContacto != null && !telefonoContacto.isEmpty()) {

                                if (!isNumeric(telefonoContacto)) {
                                    msnError = "El número de teléfono del contacto debe ser solo números.";
                                    alerts();
                                    return false;
                                }
                                if (telefonoContacto.length() == 7 || telefonoContacto.length() == 10) {

                                } else {
                                    msnError = "El telefono de contacto debe ser de 7 ó 10 digitos por favor.";
                                    alerts();
                                    return false;
                                }

                                if (telefonoSolicitante != null && !telefonoSolicitante.isEmpty()) {
                                    if (!isNumeric(telefonoSolicitante)) {
                                        msnError = "El número de teléfono del solicitante debe ser solo números.";
                                        alerts();
                                        return false;
                                    }
                                    if (telefonoSolicitante.length() == 7 || telefonoSolicitante.length() == 10) {
                                    } else {
                                        msnError = "El telefono de contacto debe ser de 7 ó 10 digitos por favor.";
                                        alerts();
                                        return false;
                                    }

                                    if (observacionesSolicitud != null && !observacionesSolicitud.isEmpty()) {
                                        if (observacionesSolicitud.length() > 400) {
                                            msnError = "Las observaciones de la solicitud debe tener menos de 400 caracteres.";
                                            alerts();
                                            return false;
                                        }
                                        return true;
                                    } else {
                                        msnError = "Ingrese una observación a la solicitud por favor. ";
                                        alerts();
                                        return false;
                                    }
                                } else {
                                    msnError = "Ingrese un teléfono del solicitante por favor. ";
                                    alerts();
                                    return false;
                                }
                            } else {
                                msnError = "Ingrese un teléfono de contacto por favor. ";
                                alerts();
                                return false;
                            }
                        } else {
                            msnError = "Ingrese un nombre de Contacto por favor. ";
                            alerts();
                            return false;
                        }
                    } else {
                        msnError = "Seleccione un Tipo Vivienda por favor. ";
                        alerts();
                        return false;
                    }
                } else {
                    msnError = "Seleccione un Área/Canal por favor. ";
                    alerts();
                    return false;
                }
            }

        } catch (Exception e) {
            msnError = "Ocurrio un error validando los datos de la solicitud ";
            alerts();
            return false;
        }
    }

    public boolean validarDominioCorreos(String correoCopia) {
        boolean resulto = false;
        if (correoCopia != null && !correoCopia.isEmpty()) {
            if (correoCopia.toLowerCase().contains("claro.com.co")
                    || correoCopia.toLowerCase().contains("telmex.com.co")
                    || correoCopia.toLowerCase().contains("comcel.com.co")
                    || correoCopia.toLowerCase().contains("telmexla.com")
                    || correoCopia.toLowerCase().contains("telmex.com")) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
						   
     * Juan David Hernandez metodo para validar correo
     *
     * @param correo
     * @return
     */
    private boolean validarCorreo(String correo) {
        boolean respuesta = false;
        try {
            String mail = "([\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z])?";
            Pattern pattern = Pattern.compile(mail);
            Matcher matcher = pattern.matcher(correo);
            respuesta = matcher.matches();
        } catch (Exception e) {
            LOGGER.error("Error en validarCorreo de consultarCoberturaBean: " + e);
        }
        return respuesta;
    }

	
    /**
     * Función que permite crear una solicitud de hhpp sobre barrio abierto o
     * cuenta matriz.
     *
     * @author Juan David Hernandez
     * @param registroSeleccionado
     * @param nodoSeleccionado
     */
    public void mostrarCrearSolicitudHhpp(CmtCoverDto registroSeleccionado) throws ApplicationException {
        try {

            limpiarSolicitudHhpp();
            direccionSeleccionada = "";
            tecnologiaSeleccionada = "";
            mostrarSolicitudHhpp = false;
            mostrarSolicitudHhppCcmm = false;

            if (registroSeleccionado != null) {

                if (registroSeleccionado.getTechnology() != null && !registroSeleccionado.getTechnology().isEmpty()) {
                    CmtBasicaMgl tecnologiaSeleccionadoByCodigo = null;
                    try {
                        tecnologiaSeleccionadoByCodigo = cmtBasicaMglFacadeLocal.findByBasicaCode(registroSeleccionado.getTechnology(), tipoBasicaTipoTecnologia.getTipoBasicaId());
                    } catch (Exception e) {
                    }

                    if (tecnologiaSeleccionadoByCodigo != null) {
                        tecnologiaSeleccionadaBasica = tecnologiaSeleccionadoByCodigo.clone();
                    } else {
                        tecnologiaSeleccionadaBasica = null;
                    }
                }

                //si no se encontró la tecnología por código la busca por nodo
                if (tecnologiaSeleccionadaBasica != null) {
                } else {
                    if (registroSeleccionado.getNode() != null && !registroSeleccionado.getNode().isEmpty()) {
                        NodoMgl nodoSeleccionadoMgl = null;
                        try {
                            nodoSeleccionadoMgl = nodoMglFacadeLocal.findByCodigo(registroSeleccionado.getNode());
                        } catch (Exception e) {
                        }

                        if (nodoSeleccionadoMgl != null && nodoSeleccionadoMgl.getNodTipo() != null) {
                            tecnologiaSeleccionadaBasica = nodoSeleccionadoMgl.getNodTipo();
                        }
                    }
                }

                if (tecnologiaSeleccionadaBasica != null) {
                    
                    //se carga listados de configuracion (complemento y apartamento) para barrio abierto                    
                    cargarListadosComplVisor();

                    if (tecnologiaSeleccionadaBasica.getIdentificadorInternoApp() != null
                            && tecnologiaSeleccionadaBasica.getIdentificadorInternoApp().equalsIgnoreCase(Constant.DTH)) {
					
					
                        if (direccionDetalladaMglDtoSel != null && direccionDetalladaMglDtoSel.getDireccionId() != null) {

                            SubDireccionMgl subDirId = new SubDireccionMgl();
                            DireccionMgl dirId = new DireccionMgl();
                            dirId.setDirId(direccionDetalladaMglDtoSel.getDireccionId());

                            if (direccionDetalladaMglDtoSel.getSubdireccionId() != null) {
                                subDirId.setSdiId(direccionDetalladaMglDtoSel.getSubdireccionId());
                            } else {
                                subDirId = null;
                            }

                            //validacion existencia hhpp
                            List<HhppMgl> hhppList = hhppMglFacadeLocal.findByDirAndSubDir(dirId, subDirId);

                            int tecnologiaExistenteCont = 0;
                            if (hhppList != null && !hhppList.isEmpty()) {
                                for (HhppMgl hhppMgl : hhppList) {
                                    if (hhppMgl.getNodId() != null && hhppMgl.getNodId().getNodTipo() != null) {
                                        if (hhppMgl.getNodId().getNodTipo().getBasicaId().equals(tecnologiaSeleccionadaBasica.getBasicaId())) {
                                            tecnologiaExistenteCont++;
                                        }
                                    }
                                }
                            }

						
                            if (tecnologiaExistenteCont != 0) {
                                msnError = "El Hhpp ya cuenta con la tecnología seleccionada, no es posible realizar una Solicitud de Hhpp para esta tecnología";
                                alerts();
                                return;
                            }

                        } else {
                            msnError = "La dirección seleccionada no cuenta con IdDirección en la base de datos";
                            alerts();
                            return;
                        }
                    }

                    //Validación para determinar si es una CCMM la dirección
                    CmtDireccionMgl direccionEnCuentaMatriz = cmtDireccionMglFacadeLocal.
                            findCmtIdDireccion(direccionDetalladaMglDtoSel.getDireccionId());
                    if (direccionEnCuentaMatriz != null && direccionEnCuentaMatriz.getCuentaMatrizObj() != null) {
                        //Validacion sobre la dirección
                        if (direccionDetalladaMglDtoSel != null) {
                            if (direccionDetalladaMglDtoSel.getCpTipoNivel5() != null
                                    && !direccionDetalladaMglDtoSel.getCpTipoNivel5().isEmpty()) {
                            } else {
                                msnError = "Para este tipo de dirección sin Apartamento y que pertenece a una Cuenta Matriz no es posible realizar una solicitud de Creación de HHPP ";
                                alerts();
                                return;
                            }
                        }

                        mostrarSolicitudHhppCcmm = true;
                        cuentaMatrizSeleccionada = direccionEnCuentaMatriz.getCuentaMatrizObj();
                        solicitudModCM = new CmtSolicitudCmMgl();
                        cargarTipoSolicitudCcmm();
                        solicitudModCM.setUnidad("1");
                        solicitudModCM.setCuentaMatrizObj(cuentaMatrizSeleccionada);
                        solicitudModCM.setComunidad(cuentaMatrizSeleccionada.getComunidad());
                        solicitudModCM.setDivision(cuentaMatrizSeleccionada.getDivision());
                        solicitudModCM.setCiudadGpo(cuentaMatrizSeleccionada.getMunicipio());
                        solicitudModCM.setDepartamentoGpo(cuentaMatrizSeleccionada.getDepartamento());
                        solicitudModCM.setCentroPobladoGpo(cuentaMatrizSeleccionada.getCentroPoblado());
                        CmtBasicaMgl estadoSolicitud = new CmtBasicaMgl();
                        estadoSolicitud.setBasicaId(cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE).getBasicaId());
                        solicitudModCM.setEstadoSolicitudObj(estadoSolicitud);
                        solicitudModCM.setModCobertura(new Short("0"));
                        solicitudModCM.setModDatosCm(new Short("0"));
                        solicitudModCM.setModDireccion(new Short("0"));
                        solicitudModCM.setModSubedificios(new Short("0"));
                        solicitudModCM.setFechaInicioCreacion(new Date());
                        subEdificioList = getSelectedCmtSubEdificioMgl(cuentaMatrizSeleccionada);

                    } else {
                        mostrarSolicitudHhppCcmm = false;
                        //validacion si la direccion no existe en CCMM y tiene complementos de HHPP no se crea solicitud
                        boolean direccionCcmm = validarDireccionComplementos(direccionDetalladaMglDtoSel);
                        if (direccionCcmm) {
                            msnError = "La dirección no existe en una cuenta matriz de MER. No es posible crear la solicitud de hhpp ";
                            alerts();
                            return;
                        }
                    }

                    if (!mostrarSolicitudHhppCcmm) {

                        //VALIDACIÓN PARA DETERMINAR SI EXISTE UNA SOLICITUD EN CURSO PARA LA MISMA TECNOLOGÍA    
                        List<Solicitud> solicitudEnCursoList = null;
                        //Validacion si existe una solicitud en curso para la unidad            
                        solicitudEnCursoList = solicitudManager.solictudesHhppEnCurso(direccionDetalladaMglDtoSel.getDireccionDetalladaId(),
                                new BigDecimal(idCentroPoblado), tecnologiaSeleccionadaBasica.getBasicaId(), "0");

                        if (solicitudEnCursoList != null && !solicitudEnCursoList.isEmpty()) {
                            msnError = "Ya existe una solicitud en curso para esta tecnología con el ID:  " + solicitudEnCursoList.get(0).getIdSolicitud();
                            alerts();
                            return;
                        }

                        //validacion de creacion de solicitud para tecnologia BI para barrio abierto
                        boolean tecnologiaBI = false;
                        boolean validacionTecnologiaBI = false;

                        if (tecnologiaSeleccionadaBasica.getIdentificadorInternoApp() != null && !tecnologiaSeleccionadaBasica.getIdentificadorInternoApp().isEmpty()
                                && tecnologiaSeleccionadaBasica.getIdentificadorInternoApp().equalsIgnoreCase(co.com.claro.mgl.utils.Constant.HFC_BID)) {
                            tecnologiaBI = true;
                            validacionTecnologiaBI = validacionTecnologiaBiTipoApto(direccionDetalladaMglDtoSel, tecnologiaSeleccionadaBasica);
                        }
                        if (tecnologiaBI) {
                            if (!validacionTecnologiaBI) {
                                return;
                            }
                        }
                        //Fin validación tecnologia BI
                    }

                    mostrarMapa = false;
                    showPanelCoordenadas = false;
                    mostrarSolicitudHhpp = true;
                    tecnologiaSeleccionada = tecnologiaSeleccionadaBasica.getNombreBasica();
                    tecnologiaIdSeleccionada = tecnologiaSeleccionadaBasica.getBasicaId();

                    nombreSolicitante = nombreUsuario;
                    correoSolicitante = correoUsuario;
                    direccionSeleccionada = direccionDetalladaMglDtoSel.getDireccionTexto();
                    //se obtiene la lectura de la parametrización para el soporte es requerido o no                            
                    obtenerConfiguracionTipoTecnologia(tecnologiaSeleccionadaBasica.getBasicaId());
                    //si la solicitud es de CCMM no se pide soporte
                    if (mostrarSolicitudHhppCcmm) {
                        soporteRequerido = false;
                    }

                    centroPobladoCompleto
                            = geograficoPoliticoFacadeLocal.findById(new BigDecimal(idCentroPoblado));
                    ciudadCompleto
                            = geograficoPoliticoFacadeLocal.findById(centroPobladoCompleto.getGeoGpoId());
                    departamentoCompleto
                            = geograficoPoliticoFacadeLocal.findById(ciudadCompleto.getGeoGpoId());

                } else {
                    msnError = "El nodo y la tecnología seleccionadas no se encuentran registradas en la base de datos. Intente con otro por favor. ";
                    alerts();
                }
            } else {
                msnError = "Ocurrio un erro seleccionado el registro, intente nuevamente por favor. ";
                alerts();
            }

        } catch (ApplicationException | CloneNotSupportedException e) {
            msnError = "Ha ocurrido un error al de cargar los datos para una nueva solicitud de hhpp "
                    + e.getMessage();
            alerts();

        }
    }

	
    public boolean validarDireccionComplementos(CmtDireccionDetalladaMglDto direccionDetalladaMglDtoSeleccionada) {
        try {
            if (direccionDetalladaMglDtoSeleccionada != null) {
                if (direccionDetalladaMglDtoSeleccionada.getCpTipoNivel1() != null
                        || direccionDetalladaMglDtoSeleccionada.getCpTipoNivel2() != null
                        || direccionDetalladaMglDtoSeleccionada.getCpTipoNivel3() != null
                        || direccionDetalladaMglDtoSeleccionada.getCpTipoNivel4() != null) {
                    return true;
                }
            }
            return false;

        } catch (Exception e) {
            return false;
        }
    }

	
    /**
     * Función utilizada para crear una solicitud de hhpp
     *
     * @author Juan David Hernandez
							  
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void crearSolicitudHhpp() {
        
        try {
            
            //Consulto token parametrizado de visor movilidad
            String tokenVisor = this.consultarParametro(Constants.TOKEN_VISOR);
            
            if (validarDatosSolicitud() && validarSoporteRequerido()) {
                String tiempoSolicitudDefault = "00:00:30";
                //creación de solicitud de HHPP sobre CCMM
                if (mostrarSolicitudHhppCcmm) {
                    
                    //Si la creacion viene de visor Movilidad, se valida si la direccion esta dentro de una cuenta matriz
                    if (tokenFactibilidades.substring(0, 14).equalsIgnoreCase(tokenVisor)) {
                        String tipoDir = esCcmm();
                        if (StringUtils.isNotEmpty(tipoDir) && tipoDir.equalsIgnoreCase("CCMM")) {
                            viabilidadGestionAutomatica = false;
                            
                            msnError = "HHPP se encuentra dentro de un CM,"
                                + " Debe solicitar la creación de HHPP por el modulo de gestión";
                            LOGGER.info(msnError);
                            throw new Exception(msnError);
                        }
                    }
                    
                    
                    CmtSubEdificioMgl subEdificio = cmtSubEdificioMglFacadeLocal.findById(subEdificioSeleccionado);
                    if (subEdificio != null) {
                    } else {
                        msnError = "El SubEdificio seleccionado ya no se encuentra en la base de datos. No es posible crear la solicitud con este Subedificio.";
                        alerts();
                        return;
                    }

                    CmtSolicitudHhppMgl cmtSolicitudHhppMgl = new CmtSolicitudHhppMgl();
                    cmtSolicitudHhppMgl.setTipoSolicitud(1);
                    cmtSolicitudHhppMgl.setCmtSubEdificioMglObj(subEdificio);
                    if (direccionDetalladaMglDtoSel != null) {

                        if (direccionDetalladaMglDtoSel.getCpTipoNivel5() != null
                                && !direccionDetalladaMglDtoSel.getCpTipoNivel5().isEmpty()) {
                            cmtSolicitudHhppMgl.setOpcionNivel5(direccionDetalladaMglDtoSel.getCpTipoNivel5());
                        }
                        if (direccionDetalladaMglDtoSel.getCpValorNivel5() != null
                                && !direccionDetalladaMglDtoSel.getCpValorNivel5().isEmpty()) {
                            cmtSolicitudHhppMgl.setValorNivel5(direccionDetalladaMglDtoSel.getCpValorNivel5());
                        }

                        if (direccionDetalladaMglDtoSel.getCpTipoNivel6() != null
                                && !direccionDetalladaMglDtoSel.getCpTipoNivel6().isEmpty()) {
                            cmtSolicitudHhppMgl.setOpcionNivel6(direccionDetalladaMglDtoSel.getCpTipoNivel6());
                        }
                        if (direccionDetalladaMglDtoSel.getCpValorNivel6() != null
                                && !direccionDetalladaMglDtoSel.getCpValorNivel6().isEmpty()) {
                            cmtSolicitudHhppMgl.setValorNivel6(direccionDetalladaMglDtoSel.getCpValorNivel6());
                        }
                    }

                    List<CmtSolicitudHhppMgl> cmtSolicitudHhppMglListToChanges = new ArrayList();
                    cmtSolicitudHhppMglListToChanges.add(cmtSolicitudHhppMgl);
                    //subedificio
                    //usuario
                    //correo usuario

                    CmtBasicaMgl origenSolicitudBasica = cmtBasicaMglFacadeLocal.findById(origenSolicitud);
                    if (origenSolicitudBasica != null) {
                        solicitudModCM.setOrigenSolicitud(origenSolicitudBasica);
                    } else {
                        msnError = "El tipo de solicitud seleccionado no se encuentra en la base de datos, seleccione otro por favor. ";
                        alerts();
                    }
                    solicitudModCM.setAsesor(asesorCcmm.toUpperCase());
                    solicitudModCM.setCorreoAsesor(correoAsesor.toUpperCase());
                    solicitudModCM.setTelefonoAsesor(telefonoContacto);
                    solicitudModCM.setBasicaIdTecnologia(tecnologiaSeleccionadaBasica);
                    if (correoCopiaA != null && !correoCopiaA.isEmpty()) {
                        solicitudModCM.setCorreoCopiaSolicitud(correoCopiaA);
                    }

					  
                    //asigno todos los datos para guardar            
					  
                    solicitudModCM.setListCmtSolicitudHhppMgl(null);
                    solicitudModCM.setTempSolicitud(tiempoSolicitudDefault);
                    solicitudModCM.setDisponibilidadGestion("1");
                    solicitudCcmmFacadeLocal.setUser(usuario, Integer.parseInt(perfilVt));
                    solicitudModCM.setUsuarioCreacion(usuario);
                    if (idUsuario != null && !idUsuario.isEmpty()) {
                        solicitudModCM.setUsuarioSolicitudId(new BigDecimal(idUsuario));
                    }
                    solicitudModCM = solicitudCcmmFacadeLocal.crearSol(solicitudModCM);

                    if (solicitudModCM.getSolicitudCmId() != null) {
                        //Creacion de las solicitudes
                        String validacionHhpp;
                        validacionHhpp = cmtSolicitudHhppMglFacadeLocal.GuardaListadoHHPP(cmtSolicitudHhppMglListToChanges, solicitudModCM, usuario, Integer.parseInt(perfilVt));
                        if (validacionHhpp == null) {
                            msnError = "Ocurrio un error al guardar los HHPP en la solicitud, por favor intente de nuevo crear la Solicitud. ";
                            alerts();
                            return;
                        }
                        //Creacion de nota..
                        solicitudModCM = solicitudCcmmFacadeLocal.findById(solicitudModCM.getSolicitudCmId());
                        CmtNotasSolicitudMgl notaSolicitudMgl = new CmtNotasSolicitudMgl();
                        BigDecimal tipoNotaId = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_NOTA_HHHPP).getBasicaId();
                        CmtBasicaMgl tipoNota = cmtBasicaMglFacadeLocal.findById(tipoNotaId);
                        notaSolicitudMgl.setTipoNotaObj(tipoNota);
                        notaSolicitudMgl.setTelefonoUsuario(telefonoContacto);
                        notaSolicitudMgl.setNota(observacionesSolicitud);
                        notaSolicitudMgl.setSolicitudCm(solicitudModCM);
                        notaSolicitudMgl.setDescripcion("Creacion Solicitud HHPP");
                        notasSolicitudMglFacadeLocal.setUser(usuario, Integer.parseInt(perfilVt));
                        notaSolicitudMgl = notasSolicitudMglFacadeLocal.crearNotSol(notaSolicitudMgl);

                        msnError = "Solicitud "
                                + solicitudModCM.getSolicitudCmId()
                                + " para la dirección: " + direccionLabel
                                + " creada correctamente sobre la CCMM. ¡Proceso exitoso!";
                        solicitudCreada = true;
                        alertProcesoExistoso();
                    }

                } else {
                    
                    
                    //Si la creacion viene de visor Movilidad realiza validaciones de la direccion
                    if (tokenFactibilidades.substring(0, 14).equalsIgnoreCase(tokenVisor)) {
                        esViableGestionAutomatica();
                        
                    }

                    //creación de solicitud de HHPP barrio abierto
                    RequestCreaSolicitud crearSolicitudRequest = new RequestCreaSolicitud();

                    //Tiempo defaul de la solicitud
                    String tipoAccionSolicitud = Constant.RR_DIR_CREA_HHPP_0;

                    crearSolicitudRequest.setIdUsuario(usuario);
                    crearSolicitudRequest.setObservaciones(observacionesSolicitud.toUpperCase().replace(";", " "));
                    CmtDireccionDetalleMglManager manager = new CmtDireccionDetalleMglManager();
                    CmtDireccionDetalladaMgl direccionDetalladaSeleccionada = manager.findById(direccionDetalladaMglDtoSel.getDireccionDetalladaId());
                    DrDireccion drDireccionSolicitud = manager.parseCmtDireccionDetalladaMglToDrDireccion(direccionDetalladaSeleccionada);

                    //si la direccion es principal y no tiene complemento, para crear el hhpp se setea una CASA
                    if (drDireccionSolicitud.getCpTipoNivel5() != null && !drDireccionSolicitud.getCpTipoNivel5().isEmpty()) {
                    } else {
                        drDireccionSolicitud.setCpTipoNivel5("CASA");
                    }
                    crearSolicitudRequest.setDrDireccion(drDireccionSolicitud);
                    crearSolicitudRequest.setComunidad(centroPobladoCompleto.getGeoCodigoDane());
                    crearSolicitudRequest.setContacto(nombreContacto);
                    crearSolicitudRequest.setTelefonoContacto(telefonoContacto);
                    crearSolicitudRequest.setCanalVentas(areaCanal);
                    CityEntity cityEntity = new CityEntity();
                    crearSolicitudRequest.setCityEntity(cityEntity);
                    crearSolicitudRequest.getCityEntity().setMessage(tipoVivienda);
                    Boolean flagMicro = Optional.ofNullable((Boolean) session.getAttribute("flagMicro")).orElse(false);
                    /*Consume servicio que hace inserción de la solicitud Dth
                            * en la base de datos */
                    ResponseCreaSolicitud responseCrearSolicitud
                            = solicitudFacadeLocal.crearSolicitudDth(crearSolicitudRequest,
                                    tecnologiaSeleccionadaBasica.getAbreviatura(),
                                    null, tiempoSolicitudDefault, centroPobladoCompleto.getGpoId(),
                                    usuario, Integer.parseInt(perfilVt), tipoAccionSolicitud,
                                    solicitud, habilitarRR, true, centroPobladoCompleto,
                                    ciudadCompleto, departamentoCompleto, flagMicro);
                    
                    if (responseCrearSolicitud.getTipoRespuesta().equalsIgnoreCase("I")) {
                        solicitudCreated = new Solicitud();
                        solicitudCreated.setIdSolicitud(new BigDecimal(responseCrearSolicitud.getIdSolicitud()));
                        solicitudCreated = solicitudFacadeLocal.findById(solicitudCreated.getIdSolicitud());
                        solicitudCreada = true;
                        cargarArchivo(solicitudCreated);

                        LOGGER.info("Solicitud "
                                + responseCrearSolicitud.getIdSolicitud()
                                + " para la dirección: " + direccionLabel
                                + " creada correctamente. ¡Proceso exitoso!");
                        
                        if (viabilidadGestionAutomatica) {                            
                            //Se setea propiedad que indica que es una solicitud creada de forma automatica
                            solicitudCreated.setCreacionAutomatica(true);                        
                            solicitudCreated.setRptGestion(consultarParametro(Constant.GESTION_RESULTADO_GESTION));                            
                            if(solicitudCreated.getRptGestion() == null){
                                solicitudCreated.setRptGestion(Constant.RZ_HHPP_CREADO);
                            }
                            //Se valida el nodo entre la lista de coberturas de la direccion y la cobertura seleccionada
                            NodoMgl nodo = null;
                            String codigoNodo = "";
                            
                            if(this.coberturaSeleccionada != null){
                                codigoNodo = this.coberturaSeleccionada.getNode();
                            }
                            else{
                                if(this.listCover != null){
                                    for(CmtCoverDto coverDto : this.listCover){
                                        if(tecnologiaSeleccionadaBasica.getCodigoBasica().equalsIgnoreCase(coverDto.getTechnology())){
                                            codigoNodo = coverDto.getNode();
                                            break;
                                        }
                                    }
                                }
                            }
                            
                            if (!Strings.isEmpty(codigoNodo)) {
                                    nodo = nodoMglFacadeLocal.findByCodigo(codigoNodo);
                                }                   
                            
                            //se obtiene el Objeto DrDireccion a partir de la direccion detallada
                            obtenerDrDireccion(direccionDetalladaSeleccionada);
                            //obtenerDireccionFormatoRr a partir de objeto de DrDireccion para gestionar solicitud
                            obtenerDireccionFormatoRr(new BigDecimal(idCentroPoblado));
                            //obtiene listado de unidades asociadas al predio que fueron modificadas en la creación de la solicitud
                            obtenerUnidadesPrediosModificadas(solicitudCreated.getIdSolicitud());
                            //Inicia tiempos para la gestión de la solicitud
                            iniciarTiempoGestion();
                            //Asigna tiempos para gestión de solicitud
                            asignarTiemposGestion();
                            //obtener el listado de unidades que presentan conflicto y eliminarlas del listado de cambios de apto
                            obtenerUnidadesConflictoList(solicitudCreated.getIdSolicitud());
                            //setear direccion detallada a la solicitud
                            solicitudCreated.setDireccionDetallada(direccionDetalladaSeleccionada);                                                                                                               
                            solicitudFacadeLocal.gestionSolicitud(solicitudCreated, drDireccion,
                                    detalleDireccionEntity, direccionRREntity, nodo, usuario, Integer.parseInt(perfilVt), idUsuario, 
                                    unidadModificadaTemporalList, cmtTiempoSolicitudMglToCreate, unidadConflictoTmpList, 
                                    true, true, 
                                    habilitarRR, centroPobladoCompleto,
                                    ciudadCompleto, departamentoCompleto, "");                            

                            msnError = "Solicitud "
                            + responseCrearSolicitud.getIdSolicitud()
                            + " para la dirección: " + direccionLabel
                            + " creada  y gestionada Automaticamente de forma correcta. ¡Proceso exitoso!";
                            alertProcesoExistoso();
                        } else {
                            msnError = "Solicitud "
                                    + responseCrearSolicitud.getIdSolicitud()
                                    + " para la dirección: " + direccionLabel
                                    + " creada correctamente. ¡Proceso exitoso!";
                            alertProcesoExistoso();
                        }

                    } else {
                        if (responseCrearSolicitud.getTipoRespuesta()
                                .equalsIgnoreCase("E")) {
                            msnError = "Ocurrió un error intentando crear la solicitud " + responseCrearSolicitud.getMensaje().toString();
                            alerts();
                        }
                    }
                }
            }
        } catch (Exception e) {
            msnError = "Ocurrió un error creando la solicitud de hhpp: " + e.getMessage();
            alerts();
        }

    }
    
    private String esCcmm() throws Exception {
        
        String tipoDir = "";
        try {
           
                //Se valida si la direccion esta dentro de una cuenta matriz
                DireccionMgl direccionMgl = new DireccionMgl();
                direccionMgl.setDirId(direccionDetalladaMglDtoSel.getDireccionId());
                
                if (direccionMgl.getDirId() != null) {
                    CmtDireccionMgl direccionCm = cmtDireccionMglFacadeLocal.findCmtIdDireccion(direccionMgl.getDirId());
                    if (direccionCm != null && direccionCm.getCuentaMatrizObj() != null) {
                        tipoDir = "CCMM";
                    } else {
                        tipoDir = "UNIDAD";
                    }
                }
           
       } catch (ApplicationException ex) {            
            LOGGER.error(ex.getMessage());            
        }
        return tipoDir;
									  
		 
    }
    public void esViableGestionAutomatica() throws Exception {
	 
											
        try {
                String tipoDir = esCcmm();
                
                if (StringUtils.isNotEmpty(tipoDir) && !tipoDir.equals("CCMM")) {                    
                    cmtDireccionDetalleMglFacadeLocal.setDireccionConsultaNodoSitiData(responseConstruirDireccion.getDrDireccion());                    
                    //Consulta el centro poblado
                    GeograficoPoliticoMgl geograficoPoliticoMgl = geograficoPoliticoFacadeLocal.findById(new BigDecimal(idCentroPoblado));
                    
                    AddressService addressGeoData = cmtDireccionDetalleMglFacadeLocal.
                            consultarGeoDataVisor(direccionDetalladaMglDtoSel.getDireccionMgl(),
                                    geograficoPoliticoMgl);

                    /*Validaciones de estado, confiabilidad y estrato para permitir creación automática de Hhpp*/
                    String estadoGeo = addressGeoData.getState();
                    String confiabilidad = addressGeoData.getReliability();
                    String confiabilidadPlaca = addressGeoData.getReliabilityPlaca();

                    if (confiabilidad.equalsIgnoreCase(confiabilidadPlaca)) {
                        confiabilidad = confiabilidadPlaca;
                    } else if (Integer.valueOf(confiabilidadPlaca) > Integer.valueOf(confiabilidad)) {
                        confiabilidad = confiabilidadPlaca;
                    }

                    String estrato = addressGeoData.getEstratoDef();

                    //Consulto estados parametrizados para aprobar creación automática de Hhhp
                    String estadosParam = this.consultarParametro(Constants.ESTADOS_GEO_VISOR);
                    List<String> estadosList = StringToolUtils.convertStringToList(estadosParam, DelimitadorEnum.PIPE, true);

                    //Consulto confiabilidad parametrizada para aprobar creación automática de Hhhp
                    String confiabilidadParam = this.consultarParametro(Constants.CONFIABILIDAD_GEO_VISOR);
                    List<String> confiabilidadList = StringToolUtils.convertStringToList(confiabilidadParam, DelimitadorEnum.PIPE, true);

                    //Consulto estratos parametrizados para aprobar creación automática de Hhhp
                    String estratosParam = this.consultarParametro(Constants.ESTRATOS_GEO_VISOR);
                    List<String> estratosList = StringToolUtils.convertStringToList(estratosParam, DelimitadorEnum.PIPE, true);
                    
                    try {
                        boolean flagMicro = Optional.ofNullable((Boolean) session.getAttribute("flagMicro")).orElse(false);

                        if (flagMicro) {
                            usuarioLogin = SesionUtil.getUserDataMicroSitio();
                        } else {
                            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioSesion.toUpperCase());
                        }

                    } catch (ApplicationException e){
                        LOGGER.error(e.getMessage());
                    }                    
                                        
                    int habilitaCreacion = Objects.isNull(usuarioLogin.getCodPerfil()) ? 
                            0 : resultModFacDirMglFacadeLocal.valParamHabCreacion(usuarioLogin.getCodPerfil());
                    
                            
                    //validacion de complemento de la direccion 
                    if( (Objects.isNull(direccionDetalladaMglDtoSel.getCpTipoNivel5()) || direccionDetalladaMglDtoSel.getCpTipoNivel5().isEmpty()) &&
                            (Objects.isNull(direccionDetalladaMglDtoSel.getCpTipoNivel6()) || direccionDetalladaMglDtoSel.getCpTipoNivel6().isEmpty()) ){
                        
                        //No cumple reglas para creacion automática de HHPP
                        msnError = "Debe ingresar nivel apartamento a la construcción de la dirección";
                        LOGGER.info(msnError);           
                        throw new Exception(msnError);
                    }
                    
                    if (estadosList.contains(estadoGeo) && confiabilidadList.contains(confiabilidad) && estratosList.contains(estrato) && habilitaCreacion != 0) {
                        drDireccion.setEstrato(estrato);
                        viabilidadGestionAutomatica = true;
                    } else {
                        msnError = "";
                        
                        if(!estadosList.contains(estadoGeo)){
                            msnError  = msnError + " Estado de la dirección: '"+estadoGeo+"' No cumple para creación autómatica de HHPP.";
                        }
                        if(!confiabilidadList.contains(confiabilidad)){
                            msnError  = msnError + " Confiabilidad de la dirección: '"+confiabilidad+"' No cumple para creación autómatica de HHPP.";
                        }
                        if(!estratosList.contains(estrato)){
                            msnError  = msnError + " Estrato de la dirección: '"+estrato+"' No cumple para creación autómatica de HHPP.";
                        }
                        if(habilitaCreacion == 0){
                            msnError  = msnError + " Perfil de usuario: '"+usuarioLogin.getCodPerfil()+"' No cumple para creación autómatica de HHPP.";
                        }
                        
                        //No cumple reglas para creacion automática de HHPP
                        LOGGER.info(msnError);           
                        throw new Exception(msnError);
                        
                    }
                } else {
                    viabilidadGestionAutomatica = false;
                    msnError = "HHPP se encuentra dentro de un CM,"
                                + " Debe solicitar la creación de HHPP por el modulo de gestión";
                    LOGGER.info(msnError);
                    throw new Exception(msnError);
                }
        } catch (ApplicationException ex) {            
            LOGGER.error(ex.getMessage());            
        }
    }
    
    /**
     * Función que el DrDireccion de una direccion apartir de la dirDetallada
     *
     * @param direccionDetallada {@link CmtDireccionDetalladaMgl}
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Juan David Hernandez
     */
    public void obtenerDrDireccion(CmtDireccionDetalladaMgl direccionDetallada)
            throws ApplicationException {
        try {
            //Se obtiene el estrato
            String estrato = drDireccion.getEstrato();
            CmtDireccionDetalleMglManager direccionDetalladaManager = new CmtDireccionDetalleMglManager();            
            drDireccion = direccionDetalladaManager.parseCmtDireccionDetalladaMglToDrDireccion(direccionDetallada);
            drDireccion.setEstrato(estrato);
            if (drDireccion.equals(null)) {
                throw new ApplicationException("No fue posible obtener la direccion apartir de la dirección detallada,"
                        + " intente crear de nuevo la solicitud ya que no es posible gestionarla ");
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error al obtener la direccion detallada de la solicitud" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener la direccion detallada de la solicitud" + e.getMessage(), e, LOGGER);
        }
    }
    
    /**
     * Función que obtiene si la dirección es multi-origen y genera la dirección
     * en formato Rr.
     *
     * @author Juan David Hernandez
     * @param centroPobladoId
     */
    public void obtenerDireccionFormatoRr(BigDecimal centroPobladoId) {
        try {            
           if(!Objects.isNull(drDireccion)){
               detalleDireccionEntity = drDireccion.convertToDetalleDireccionEntity();
               //obtenemos el centro poblado de la solicitud para conocer si la ciudad es multiorigen
               GeograficoPoliticoMgl centroPobladoSolicitud = geograficoPoliticoFacadeLocal.findById(centroPobladoId);

               if(!Objects.isNull(centroPobladoSolicitud)){
                   //Conocer si es multi-origen
                   detalleDireccionEntity.setMultiOrigen(centroPobladoSolicitud.getGpoMultiorigen());
                   DireccionRRManager direccionRRManager = new DireccionRRManager(detalleDireccionEntity);
                   //Se obtiene el detalle de la direccion en formato RR
                   direccionRREntity = direccionRRManager.getDireccion();
               }
           }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al generar dirección en formato Rr" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al generar dirección en formato Rr" + e.getMessage(), e, LOGGER);
        }
    }
    
    /**
     * Función que obtiene listado de unidades asociadas al predio que fueron
     * modificadas en la creación de la solicitud.
     *
     * @author ...
     * @param idSolicitud
     */
    public void obtenerUnidadesPrediosModificadas(BigDecimal idSolicitud) {
        try {
            DireccionRRManager direccionRRManager
                    = new DireccionRRManager(detalleDireccionEntity);
            //verificamos que existan cambios de HHPP en el Creacion
            List<ModificacionDir> modificacionList
                    = modificacionDirFacadeLocal
                            .findByIdSolicitud(idSolicitud);

            if ((unidadModificadaList == null || unidadModificadaList.isEmpty())
                    && modificacionList != null && !modificacionList.isEmpty()) {
                unidadModificadaList = new ArrayList<>();

                for (ModificacionDir m : modificacionList) {
                    HhppMgl hhppMgl = null;
                    if (m.getTecnologiaHabilitadaId() != null) {
                        hhppMgl = hhppMglFacadeLocal.findById(m.getTecnologiaHabilitadaId());
                    }
                    if (hhppMgl != null && !Objects.isNull(direccionRRManager)) {
                        UnidadStructPcml u = new UnidadStructPcml();
                        u.setCalleUnidad(!Objects.isNull(direccionRRManager.getDireccion())?direccionRRManager.getDireccion().getCalle():"");
                        u.setCasaUnidad(!Objects.isNull(direccionRRManager.getDireccion())?direccionRRManager.getDireccion().getNumeroUnidad():"");
                        u.setAptoUnidad(m.getOldApto());
                        u.setNewAparment(m.getNewApto());
                        u.setConflictApto("0");
                        u.setTecnologiaHabilitadaId(m.getTecnologiaHabilitadaId() != null
                                ? m.getTecnologiaHabilitadaId() : null);
                        u.setTipoNivel5(m.getCpTipoNivel5() != null
                                ? m.getCpTipoNivel5() : null);
                        u.setTipoNivel6(m.getCpTipoNivel6() != null
                                ? m.getCpTipoNivel6() : null);
                        u.setValorNivel5(m.getCpValorNivel5() != null
                                ? m.getCpValorNivel5() : null);
                        u.setValorNivel6(m.getCpValorNivel6() != null
                                ? m.getCpValorNivel6() : null);
                        if (hhppMgl.getEhhId() != null) {
                            u.setEstadUnidadad(hhppMgl.getEhhId() != null
                                    ? hhppMgl.getEhhId().getEhhNombre() : null);
                        }

                        if (hhppMgl.getSubDireccionObj() != null) {
                            if (hhppMgl.getSubDireccionObj().getSdiEstrato() != null
                                    && Objects.equals(hhppMgl.getSubDireccionObj().getSdiEstrato(), BigDecimal.valueOf(-1))) {
                                u.setEstratoUnidad("NG");
                            } else {
                                if (hhppMgl.getSubDireccionObj().getSdiEstrato() != null
                                        && Objects.equals(hhppMgl.getSubDireccionObj().getSdiEstrato(),BigDecimal.ZERO)) {
                                    u.setEstratoUnidad("NR");
                                } else {
                                    if (hhppMgl.getSubDireccionObj().getSdiEstrato() != null) {
                                        u.setEstratoUnidad(String.valueOf(hhppMgl.getSubDireccionObj().getSdiEstrato()));
                                    }
                                }
                            }
                        } else {
                            if (hhppMgl.getDireccionObj() != null) {
                                if (Objects.equals(hhppMgl.getDireccionObj().getDirEstrato(),BigDecimal.valueOf(-1))) {
                                    u.setEstratoUnidad("NG");
                                } else {
                                    if (Objects.equals(hhppMgl.getDireccionObj().getDirEstrato(), BigDecimal.ZERO)) {
                                        u.setEstratoUnidad("NR");
                                    } else {
                                        u.setEstratoUnidad(String.valueOf(hhppMgl.getDireccionObj().getDirEstrato()));
                                    }
                                }
                            }
                        }

                        u.setNodUnidad(hhppMgl.getNodId() != null ? hhppMgl.getNodId().getNodCodigo() : null);
                        u.setTecnologiaHabilitadaId(hhppMgl.getNodId() != null
                                ? hhppMgl.getNodId().getNodTipo().getBasicaId() : null);
                        u.setNombreTecnologia(hhppMgl.getNodId() != null
                                ? hhppMgl.getNodId().getNodTipo().getNombreBasica() : null);
                        u.setHhppMgl(hhppMgl);
                        unidadModificadaList.add(u);
                    }
                }
                copiaUnidadesModificadas();
            }
            

        } catch (ApplicationException e) {
            String msn = "Error al obtener listado de unidades modificadas "
                    + "y asociadas al predio ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al obtener listado de unidades modificadas "
                    + "y asociadas al predio ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }
    
    /**
     * Función que se utilizada para obtener el listado de unidades que
     * presentan conflicto y eliminarlas del listado de cambios de apto
     *
     * @author Juan David Hernandez
     * @param idSolicitud
     */
    public void obtenerUnidadesConflictoList(BigDecimal idSolicitud) {
        try {
            //verificamos que existan cambios de HHPP en el Creacion
            List<ModificacionDir> hhppConflictoList
                    = modificacionDirFacadeLocal
                            .findHhppConflicto(idSolicitud);

            if (hhppConflictoList != null && !hhppConflictoList.isEmpty()) {
                HhppMglManager hhppMglManager = new HhppMglManager();
                int idUnidadTmp = 0;

                for (ModificacionDir unidadConflicto : hhppConflictoList) {
                    if ((unidadConflicto.getConflictApto() != null && !unidadConflicto.getConflictApto().isEmpty())
                            && org.apache.commons.lang3.StringUtils.equalsIgnoreCase("1", unidadConflicto.getConflictApto())) {

                        if (unidadConflicto.getTecnologiaHabilitadaId() != null
                                && unidadConflicto.getTecHabilitadaIdNuevaDireccion() != null) {
                            //Nueva    
                            HhppMgl hhppConflictoNueva = hhppMglManager.findById(unidadConflicto.getTecHabilitadaIdNuevaDireccion());
                            //Antigua    
                            HhppMgl hhppConflictoAntigua = hhppMglManager.findById(unidadConflicto.getTecnologiaHabilitadaId());

                            if (hhppConflictoNueva != null && hhppConflictoAntigua != null) {

                                //Nueva
                                UnidadStructPcml unidadNuevaConflicto = new UnidadStructPcml();
                                idUnidadTmp++;
                                unidadNuevaConflicto.setIdUnidad(new BigDecimal(idUnidadTmp));
                                unidadNuevaConflicto.setCalleUnidad(hhppConflictoNueva.getHhpCalle() != null ? hhppConflictoNueva.getHhpCalle() : "");
                                unidadNuevaConflicto.setCasaUnidad(hhppConflictoNueva.getHhpPlaca() != null ? hhppConflictoNueva.getHhpPlaca() : "");
                                unidadNuevaConflicto.setAptoUnidad(hhppConflictoNueva.getHhpApart() != null ? hhppConflictoNueva.getHhpApart() : "");
                                unidadNuevaConflicto.setEstadUnidadad(hhppConflictoNueva.getEhhId() != null ? hhppConflictoNueva.getEhhId().getEhhNombre() : "");

                                //conversion de estrato
                                if (hhppConflictoNueva.getSubDireccionObj() != null
                                        && hhppConflictoNueva.getSubDireccionObj().getSdiEstrato() != null) {
                                    if (Objects.equals(hhppConflictoNueva.getSubDireccionObj().getSdiEstrato(), BigDecimal.valueOf(-1))) {
                                        unidadNuevaConflicto.setEstratoUnidad("NG");
                                    } else {
                                        if (Objects.equals(hhppConflictoNueva.getSubDireccionObj().getSdiEstrato(),BigDecimal.ZERO)) {
                                            unidadNuevaConflicto.setEstratoUnidad("NR");
                                        } else {
                                            unidadNuevaConflicto.setEstratoUnidad(String.valueOf(hhppConflictoNueva.getSubDireccionObj().getSdiEstrato()));
                                        }
                                    }
                                } else {
                                    if (hhppConflictoNueva.getDireccionObj() != null
                                            && hhppConflictoNueva.getDireccionObj().getDirEstrato() != null) {
                                        if (Objects.equals(hhppConflictoNueva.getDireccionObj().getDirEstrato(), BigDecimal.valueOf(-1))) {
                                            unidadNuevaConflicto.setEstratoUnidad("NG");
                                        } else {
                                            if (Objects.equals(hhppConflictoNueva.getDireccionObj().getDirEstrato(), BigDecimal.ZERO)) {
                                                unidadNuevaConflicto.setEstratoUnidad("NR");
                                            } else {
                                                unidadNuevaConflicto.setEstratoUnidad(hhppConflictoNueva.getDireccionObj().getDirEstrato().toString());
                                            }
                                        }
                                    }
                                }
                                unidadNuevaConflicto.setNodUnidad(hhppConflictoNueva.getNodId() != null
                                        ? hhppConflictoNueva.getNodId().getNodCodigo() : "");
                                unidadNuevaConflicto.setHhppMgl(hhppConflictoNueva);
                                unidadNuevaConflicto.setMallaDireccion("nueva");
                                unidadConflictoList.add(unidadNuevaConflicto);

                                //Antigua
                                UnidadStructPcml unidadAntiguaConflicto = new UnidadStructPcml();
                                idUnidadTmp++;
                                unidadAntiguaConflicto.setIdUnidad(new BigDecimal(idUnidadTmp));
                                unidadAntiguaConflicto.setCalleUnidad(hhppConflictoAntigua.getHhpCalle() != null ? hhppConflictoAntigua.getHhpCalle() : "");
                                unidadAntiguaConflicto.setCasaUnidad(hhppConflictoAntigua.getHhpPlaca() != null ? hhppConflictoAntigua.getHhpPlaca() : "");
                                unidadAntiguaConflicto.setAptoUnidad(hhppConflictoAntigua.getHhpApart() != null ? hhppConflictoAntigua.getHhpApart() : "");
                                unidadAntiguaConflicto.setEstadUnidadad(hhppConflictoAntigua.getEhhId() != null ? hhppConflictoAntigua.getEhhId().getEhhNombre() : "");
                                //conversion de estrato

                                if (hhppConflictoAntigua.getSubDireccionObj() != null) {
                                    if (hhppConflictoAntigua.getSubDireccionObj() != null
                                            && Objects.equals(hhppConflictoAntigua.getSubDireccionObj().getSdiEstrato(), BigDecimal.valueOf(-1))) {
                                        unidadAntiguaConflicto.setEstratoUnidad("NG");
                                    } else {
                                        if (hhppConflictoAntigua.getSubDireccionObj() != null
                                                && Objects.equals(hhppConflictoAntigua.getSubDireccionObj().getSdiEstrato(), BigDecimal.ZERO)) {
                                            unidadAntiguaConflicto.setEstratoUnidad("NR");
                                        } else {
                                            if (hhppConflictoAntigua.getSubDireccionObj() != null) {
                                                unidadAntiguaConflicto.setEstratoUnidad(hhppConflictoAntigua.getSubDireccionObj().getSdiEstrato().toString());
                                            }
                                        }
                                    }
                                } else {
                                    if (hhppConflictoAntigua.getDireccionObj() != null
                                            && hhppConflictoAntigua.getDireccionObj().getDirEstrato() != null) {
                                        if (Objects.equals(hhppConflictoAntigua.getDireccionObj().getDirEstrato(), BigDecimal.valueOf(-1))) {
                                            unidadAntiguaConflicto.setEstratoUnidad("NG");
                                        } else {
                                            if (Objects.equals(hhppConflictoAntigua.getDireccionObj().getDirEstrato(), BigDecimal.ZERO)) {
                                                unidadAntiguaConflicto.setEstratoUnidad("NR");
                                            } else {
                                                unidadAntiguaConflicto.setEstratoUnidad(hhppConflictoNueva.getDireccionObj().getDirEstrato().toString());
                                            }
                                        }
                                    }
                                }

                                unidadAntiguaConflicto.setNodUnidad(hhppConflictoAntigua.getNodId() != null
                                        ? hhppConflictoAntigua.getNodId().getNodCodigo() : "");
                                unidadAntiguaConflicto.setHhppMgl(hhppConflictoAntigua);
                                unidadAntiguaConflicto.setMallaDireccion("antigua");
                                unidadConflictoList.add(unidadAntiguaConflicto);
                            }
                        }
                    }

                }
            }

            if (unidadConflictoList != null && !unidadConflictoList.isEmpty()) {
                copiaUnidadesConflicto();
            }
        } catch (ApplicationException e) {
            String msn = "Error al obteniendo unidades con conflicto ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al obteniendo unidades con conflicto ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }
    
    public void copiaUnidadesConflicto() {
        try {
            unidadConflictoTmpList = new ArrayList();
            if (unidadConflictoList != null && !unidadConflictoList.isEmpty()) {
                for (UnidadStructPcml u : unidadConflictoList) {
                    unidadConflictoTmpList.add(u.clone());
                }
            }

        } catch (CloneNotSupportedException e) {
            String msn = "Error al realizar copia de unidades en conflicto  ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }
    
    /**
     * Función que asigna los tiempos de la traza de la solicitud en la base de
     * datos
     *
     * @author Juan David Hernandez
     */
    public void asignarTiemposGestion() {
        try {
            
            if (solicitudCreated.getTiempoSolicitudMgl() != null && solicitudCreated.getTiempoSolicitudMgl().getTiempoSolicitudId() != null) {

                cmtTiempoSolicitudMglToCreate.setTiempoSolicitudId(solicitudCreated.getTiempoSolicitudMgl().getTiempoSolicitudId());
                cmtTiempoSolicitudMglToCreate.setFechaCreacion(solicitudCreated.getTiempoSolicitudMgl().getFechaCreacion());
                cmtTiempoSolicitudMglToCreate.setUsuarioCreacion(solicitudCreated.getTiempoSolicitudMgl().getUsuarioCreacion());
                cmtTiempoSolicitudMglToCreate.setPerfilCreacion(solicitudCreated.getTiempoSolicitudMgl().getPerfilCreacion());
                cmtTiempoSolicitudMglToCreate.setTiempoSolicitud(solicitudCreated.getTiempoSolicitudMgl().getTiempoSolicitud());                        
                cmtTiempoSolicitudMglToCreate.setEstadoRegistro(1);
                solicitudCreated.setIdSolicitud(solicitudCreated.getIdSolicitud());
                cmtTiempoSolicitudMglToCreate.setTiempoEspera(DEFAULT_TIME);
                //asigna tiempo transcurrido en la gestión (cronómetro en pantalla)
                cmtTiempoSolicitudMglToCreate.setTiempoGestion(DEFAULT_TIME);
                /*asigna el tiempo total entre la creación de la solicitud y el 
             * momento en el que se realiza la gestión*/
                cmtTiempoSolicitudMglToCreate.setTiempoTotal(getTiempo(solicitudCreated.getFechaIngreso(),
                        new Date()));
                /*asigna el objeto solicitud con el cual se relacionaran los 
             tiempos guardados */
                cmtTiempoSolicitudMglToCreate.setSolicitudObj(solicitudCreated);
                /*Realiza la actualización de los tiempos de la solicitud en la base
             de datos. */

                cmtTiempoSolicitudMglToCreate.setArchivoSoporte(solicitudCreated.getTiempoSolicitudMgl()
                        .getArchivoSoporte());                            
            } else {     
               
                //asigna tiempo transcurrido en la solicitud (cronómetro en pantalla)
                cmtTiempoSolicitudMglToCreate.setTiempoSolicitud("00:02:30");
                /*asigna el objeto solicitud con el cual se relacionaran los tiempos guardados */
                cmtTiempoSolicitudMglToCreate.setSolicitudObj(solicitudCreated);
                /*Realiza la actualización de los tiempos de la solicitud en la base de datos.*/
                cmtTiempoSolicitudMglToCreate.setTiempoEspera(DEFAULT_TIME);
                cmtTiempoSolicitudMglToCreate.setTiempoGestion(timeSol);
                cmtTiempoSolicitudMglToCreate.setTiempoTotal(getTiempo(solicitudCreated.getFechaIngreso(),new Date()));
                cmtTiempoSolicitudMglToCreate.setArchivoSoporte("NA");
                cmtTiempoSolicitudMglToCreate.setFechaCreacion(solicitudCreated.getFechaIngreso());
                cmtTiempoSolicitudMglToCreate.setUsuarioCreacion(solicitudCreated.getUsuario());
                cmtTiempoSolicitudMglToCreate.setPerfilCreacion(Integer.parseInt(perfilVt));
                //guarda en la base de datos el track de tiempos.
                cmtTiempoSolicitudMglFacadeLocal.setUser(usuario, Integer.parseInt(perfilVt));
                cmtTiempoSolicitudMglToCreate = cmtTiempoSolicitudMglFacadeLocal.create(cmtTiempoSolicitudMglToCreate);
                
            }


        } catch (Exception e) {
            String msn = "Error al realizar actualización del registro de tiempos.";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }
    
    /**
     * Función que inicializa el cronómetro en la pantalla.
     *
     * @author Juan David Hernandez
     */
    public void iniciarTiempoGestion() {
        tmpInicio = null;
        deltaTiempo = null;
        tmpFin = null;
        timeSol = null;
        dateInicioCreacion = new Date();
        session.setAttribute("tiempoInicio", null);
        tmpInicio = (String) session.getAttribute("tiempoInicio");
        if (tmpInicio == null) {
            long milli = (new Date()).getTime();
            if (session.getAttribute("deltaTiempo") != null) {
                long delta = ((Long) session.getAttribute("deltaTiempo")).longValue();
                milli = milli - (delta);
            }
            tmpInicio = milli + "";
            session.setAttribute("tiempoInicio", tmpInicio);
        }
    }
    
    public void copiaUnidadesModificadas() {
        try {
            unidadModificadaTemporalList = new ArrayList();
            if (unidadModificadaList != null && !unidadModificadaList.isEmpty()) {
                for (UnidadStructPcml u : unidadModificadaList) {
                    unidadModificadaTemporalList.add(u.clone());
                }
            }

        } catch (CloneNotSupportedException e) {
            String msn = "Error al realizar copia de unidades modificadas  ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }
    
    /**
     * Función que se utiliza para realizar el cronómetro en pantalla
     *
     * @author Juan David Hernandez
     * @param dateInicio
     * @param dateFin
     * @return result
     */
    public String getTiempo(Date dateInicio, Date dateFin) {
        String result = DEFAULT_TIME;
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
    
    public void limpiarSolicitudHhpp() {
        soporteRequerido = false;
        solicitudCreated = new Solicitud();
        observacionesSolicitud = "";
        nombreContacto = "";
        telefonoContacto = "";
        telefonoSolicitante = "";
        areaCanal = "";
        tipoVivienda = "";
        upFile = null;
        solicitudCreada = false;
        //datos solicitud CCMM
        origenSolicitud = BigDecimal.ZERO;
        subEdificioSeleccionado = BigDecimal.ZERO;
        solicitudModCM = new CmtSolicitudCmMgl();
        asesorCcmm = "";
        correoAsesor = "";
        correoCopiaA = "";

    }

    public void cerrarPanelSolicitudHhpp() {
        mostrarSolicitudHhpp = false;
        mostrarSolicitudHhppCcmm = false;
        mostrarMapa = true;
        showPanelCoordenadas = true;
        direccionSeleccionada = "";
        tecnologiaSeleccionada = "";
        cuentaMatrizSeleccionada = new CmtCuentaMatrizMgl();
        limpiarSolicitudHhpp();

    }

    public void cargarTipoSolicitudCcmm() {
        try {
            String tipoSol = ConstantsCM.TIPO_SOLICITUD_CREACION_HHPP;
            CmtTipoSolicitudMgl cmtTipoSolicitudMgl = tipoSolicitudFacadeLocal.findTipoSolicitudByAbreviatura(tipoSol);
            CmtTipoSolicitudMgl tipoSolicitudMgl = tipoSolicitudFacadeLocal.find(cmtTipoSolicitudMgl.getTipoSolicitudId());

            if (tipoSolicitudMgl != null) {
                solicitudModCM.setTipoSolicitudObj(tipoSolicitudMgl);
            }
        } catch (ApplicationException e) {
            msnError = "Ocurrió un error intentando cargar el tipo de solicitud ";
            alerts();

        } catch (Exception e) {
            msnError = "Ocurrió un error intentando cargar el tipo de solicitud ";
            alerts();
        }

    }

    /**
     * Función utilizada para la configuracion de la tecnologia seleccionada.
     *
     * @author Juan David Hernandez
     * @param tipoTecnologiaId
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void obtenerConfiguracionTipoTecnologia(BigDecimal tipoTecnologiaId)
            throws ApplicationException {
        try {

            //Si la solicitud es de CCMM no pide soporte
            if (!mostrarSolicitudHhppCcmm) {

                CmtExtendidaTecnologiaMgl extendidaTecnologia;
                CmtBasicaMgl tecnologiaBasic = new CmtBasicaMgl();
                tecnologiaBasic.setBasicaId(tipoTecnologiaId);
                extendidaTecnologia = cmtExtendidaTecnologiaMglFacadeLocal.findBytipoTecnologiaObj(tecnologiaBasic);

                if (extendidaTecnologia != null && extendidaTecnologia.getReqSoporte() == 1) {
                    soporteRequerido = true;
                } else {
                    soporteRequerido = false;
                }

            } else {
                soporteRequerido = false;
            }

        } catch (ApplicationException e) {
            msnError = "Error al realizar consulta para obtener "
                    + "la configuracion de la tecnologia seleccionada" + e.getMessage();
            alerts();

        } catch (Exception e) {
            msnError = "Error al realizar consulta para obtener "
                    + "la configuracion de la tecnologia seleccionada" + e.getMessage();
            alerts();
        }
    }

	
    /**
     * Función que valida cuando el soporte es requerido que este sea
     * obligatorio de cargar
     *
     * @author Juan David Hernandez
     * @return
     *
     */
    public boolean validarSoporteRequerido() {

        if (!mostrarSolicitudHhppCcmm) {
            //si el soporte es requerido para todas las solicitudes menos para reactivacion
            if (soporteRequerido) {
                if (upFile == null && upFile.getFileName() != null) {
                    msnError = "Es necesario cargar un archivo de soporte para esta tecnología por favor.";
                    alerts();
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Función utilizada para la configuracion de la tecnologia seleccionada.
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void validarSoporteRequeridoArea() throws ApplicationException {
        try {
            if (areaCanal != null && !areaCanal.isEmpty()
                    && (areaCanal.equalsIgnoreCase("PYMES")
                    || areaCanal.equalsIgnoreCase("TRASLADOS")
                    || areaCanal.equalsIgnoreCase("CORTESIA")
                    || areaCanal.equalsIgnoreCase("FUENTE DE PODER"))) {
                soporteRequerido = false;
            } else {
                obtenerConfiguracionTipoTecnologia(tecnologiaIdSeleccionada);
            }
        } catch (Exception e) {
            msnError = "Error al realizar validacion para soporte requerido por area";
            alerts();
        }
    }


	
    /**
     * Función utilizada para obtener el listado de tipos de solicitudes desde
     * la base de datos
     *
     * @author Juan David Hernandez
     */
    public void obtenerListadoAreaCanalList() {
        try {
            areaCanalList
                    = parametrosCallesFacade.findByTipo("TIPO_SOLICITUD");
        } catch (ApplicationException e) {
            msnError = "Error al realizar consulta para obtener listado de"
                    + " tipo de solicitudes";
            alerts();
        } catch (Exception e) {
            msnError = "Error al realizar consulta para obtener listado de"
                    + " tipo de solicitudes";
            alerts();
        }
    }

    /**
     * Carga Archivo de soporte. Permite cargar el archivo de soporte de la
     * solicitud
     *
     * @throws IOException
     * @author Johnnatan Ortiz
     */
    public void cargarArchivo(Solicitud solicitudCreada) throws IOException {

        //String usuario = usuarioVT;
        String solicitudStr = "SOL-HHPP" + solicitudCreada.getIdSolicitud()
                .toString().trim();
        Date fecha = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String fechaN = format.format(fecha);

        if (upFile != null && upFile.getFileName() != null) {
            String fileName = fechaN;
            fileName += FilenameUtils.getName(upFile.getFileName());
            File file = new File(System.getProperty("user.dir"));
            String extension = FilenameUtils.getExtension(upFile.getFileName());
            File archive = File.createTempFile(fileName + "-", "." + extension, file);
            FileOutputStream output = new FileOutputStream(archive);
            output.write(upFile.getContent());
            output.close();
            try {
                String responseL
                        = solicitudFacadeLocal.uploadArchivoCambioEstrato(archive, fileName);

                if (responseL == null
                        || responseL.isEmpty()) {
			
                    msnError = "Error al subir el archivo al servidor de alojamiento de documentos.";
                    alerts();

                } else {
                    msnError = "Archivo cargado correctamente";
                    alertProcesoExistoso();

                    CmtTiempoSolicitudMgl tiempoSol = solicitudCreada.getTiempoSolicitudMgl();
                    tiempoSol.setArchivoSoporte(solicitudStr + "_" + fileName);
                    cmtTiempoSolicitudMglFacadeLocal.update(tiempoSol);

                    CmtArchivosVtMgl cmtArchivosVtMgl = new CmtArchivosVtMgl();
                    cmtArchivosVtMgl.setSolicitudHhpp(solicitudCreada);
                    cmtArchivosVtMgl.setRutaArchivo(responseL);
                    cmtArchivosVtMgl.setNombreArchivo(solicitudStr + "_" + fileName);
                    cmtArchivosVtMgl = archivosVtMglFacadeLocal.create(cmtArchivosVtMgl, usuario, Integer.parseInt(perfilVt));
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
                FacesUtil.mostrarMensajeError("Error al subir el archivo" + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                if (archive != null) {
                    try {
                        Files.deleteIfExists(archive.toPath());
                    } catch (IOException ex) {
                        LOGGER.error(ex.getMessage());
                    }
                }
                FacesUtil.mostrarMensajeError("Error al subir el archivo" + e.getMessage(), e, LOGGER);
            }
        }
    }

    /**
     * Realiza la verificaci&oacute;n si fue seleccionada la funcionalidad de
     * <b>Ingresar Direcci&oacute;n</b>.
     *
     * @return Se abri&oacute; opci&oacute;n "<i>Ingresar Direcci&oacute;n</i>"?
     */
    public boolean isOpcionDireccionSelected() {
        return (this.opcionSeleccionada == FUNCIONALIDAD_INGRESAR_DIRECCION);
    }

	
    /**
     * Realiza la verificaci&oacute;n si fue seleccionada la funcionalidad de
     * <b>Punto Ubicaci&oacute;n</b>.
     *
     * @return Se abri&oacute; opci&oacute;n "<i>Punto Ubicaci&oacute;n</i>"?
     */
    public boolean isOpcionPuntoUbicacionSelected() {
        return (this.opcionSeleccionada == FUNCIONALIDAD_PUNTO_UBICACION);
    }

	
    /**
     * Realiza la verificaci&oacute;n si fue seleccionada la funcionalidad de
     * <b>GPS</b>.
     *
     * @return Se abri&oacute; opci&oacute;n "<i>GPS</i>"?
     */
    public boolean isOpcionGPSSelected() {
        return (this.opcionSeleccionada == FUNCIONALIDAD_GPS);
    }

	
    /**
     * Realiza la verificaci&oacute;n si fue seleccionada la funcionalidad de
     * <b>Ingresar Coordenadas</b>.
     *
     * @return Se abri&oacute; opci&oacute;n "<i>Ingresar Coordenadas</i>"?
     */
    public boolean isOpcionCoordenadasSelected() {
        return (this.opcionSeleccionada == FUNCIONALIDAD_INGRESAR_COORDENADAS);
    }

	
    /**
     * Determina si se realizar&aacute; <b>Responsive</b> a los DataTable.
     *
     * @return DataTable Responsive activo?
     */
    public boolean isDatatableResponsiveEnabled() {
        return (this.DATATABLE_RESPONSIVE_ENABLED);
    }

	
    /**
     * Determina si se obtendr&aacute; informaci&oacute;n adicional de un HHPP.
     *
     * @return Ser&aacute; obtenida informaci&oacute;n adicional del HHPP?
     */
    public boolean isObtenerInformacionAdicionalHHPP() {
        return (this.OBTENER_INFORMACION_ADICIONAL_HHPP);
    }

	
    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public String getDireccionCk() {
        return direccionCk;
    }

    public void setDireccionCk(String direccionCk) {
        this.direccionCk = direccionCk;
    }

    public String getNivelValorBm() {
        return nivelValorBm;
    }

    public void setNivelValorBm(String nivelValorBm) {
        this.nivelValorBm = nivelValorBm;
    }

    public String getNivelValorIt() {
        return nivelValorIt;
    }

    public void setNivelValorIt(String nivelValorIt) {
        this.nivelValorIt = nivelValorIt;
    }

    public String getTipoNivelBm() {
        return tipoNivelBm;
    }

    public void setTipoNivelBm(String tipoNivelBm) {
        this.tipoNivelBm = tipoNivelBm;
    }

    public String getTipoNivelIt() {
        return tipoNivelIt;
    }

    public void setTipoNivelIt(String tipoNivelIt) {
        this.tipoNivelIt = tipoNivelIt;
    }

    public String getTipoNivelApartamento() {
        return tipoNivelApartamento;
    }

    public void setTipoNivelApartamento(String tipoNivelApartamento) {
        this.tipoNivelApartamento = tipoNivelApartamento;
    }

    public String getTipoNivelComplemento() {
        return tipoNivelComplemento;
    }

    public void setTipoNivelComplemento(String tipoNivelComplemento) {
        this.tipoNivelComplemento = tipoNivelComplemento;
    }

    public String getTipoNivelNuevoApartamento() {
        return tipoNivelNuevoApartamento;
    }

    public void setTipoNivelNuevoApartamento(String tipoNivelNuevoApartamento) {
        this.tipoNivelNuevoApartamento = tipoNivelNuevoApartamento;
    }

    public String getValorNivelNuevoApartamento() {
        return valorNivelNuevoApartamento;
    }

    public void setValorNivelNuevoApartamento(String valorNivelNuevoApartamento) {
        this.valorNivelNuevoApartamento = valorNivelNuevoApartamento;
    }

    public String getDireccionLabel() {
        return direccionLabel;
    }

    public void setDireccionLabel(String direccionLabel) {
        this.direccionLabel = direccionLabel;
    }

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    public boolean isShowCKPanel() {
        return showCKPanel;
    }

    public void setShowCKPanel(boolean showCKPanel) {
        this.showCKPanel = showCKPanel;
    }

    public boolean isShowBMPanel() {
        return showBMPanel;
    }

    public void setShowBMPanel(boolean showBMPanel) {
        this.showBMPanel = showBMPanel;
    }

    public boolean isShowITPanel() {
        return showITPanel;
    }

    public void setShowITPanel(boolean showITPanel) {
        this.showITPanel = showITPanel;
    }

    public String getApartamento() {
        return apartamento;
    }

    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getIdCentroPoblado() {
        return idCentroPoblado;
    }

    public void setIdCentroPoblado(String idCentroPoblado) {
        this.idCentroPoblado = idCentroPoblado;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getEspacio() {
        return espacio;
    }

    public void setEspacio(String espacio) {
        this.espacio = espacio;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public String getNumPaginaCob() {
        return numPaginaCob;
    }

    public void setNumPaginaCob(String numPaginaCob) {
        this.numPaginaCob = numPaginaCob;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public String getRegresarMenu() {
        return regresarMenu;
    }

    public void setRegresarMenu(String regresarMenu) {
        this.regresarMenu = regresarMenu;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public boolean isShowFooter() {
        return showFooter;
    }

    public void setShowFooter(boolean showFooter) {
        this.showFooter = showFooter;
    }

    public List<CmtDireccionDetalladaMglDto> getDireccionDetalladaList() {
        return direccionDetalladaList;
    }

    public void setDireccionDetalladaList(List<CmtDireccionDetalladaMglDto> direccionDetalladaList) {
        this.direccionDetalladaList = direccionDetalladaList;
    }

    public boolean isShowPanelBusquedaDireccion() {
        return showPanelBusquedaDireccion;
    }

    public void setShowPanelBusquedaDireccion(boolean showPanelBusquedaDireccion) {
        this.showPanelBusquedaDireccion = showPanelBusquedaDireccion;
    }

    public boolean isShowPanelDirecciones() {
        return showPanelDirecciones;
    }

    public void setShowPanelDirecciones(boolean showPanelDirecciones) {
        this.showPanelDirecciones = showPanelDirecciones;
    }

    public boolean isNotGeoReferenciado() {
        return notGeoReferenciado;
    }

    public void setNotGeoReferenciado(boolean notGeoReferenciado) {
        this.notGeoReferenciado = notGeoReferenciado;
    }

    public String getCreateDireccion() {
        return createDireccion;
    }

    public void setCreateDireccion(String createDireccion) {
        this.createDireccion = createDireccion;
    }

    public boolean isShowBarrio() {
        return showBarrio;
    }

    public void setShowBarrio(boolean showBarrio) {
        this.showBarrio = showBarrio;
    }

    public String getBarrioSugerido() {
        return barrioSugerido;
    }

    public void setBarrioSugerido(String barrioSugerido) {
        this.barrioSugerido = barrioSugerido;
    }

    public String getBarrioSugeridoStr() {
        return barrioSugeridoStr;
    }

    public void setBarrioSugeridoStr(String barrioSugeridoStr) {
        this.barrioSugeridoStr = barrioSugeridoStr;
    }

    public boolean isBarrioSugeridoField() {
        return barrioSugeridoField;
    }

    public void setBarrioSugeridoField(boolean barrioSugeridoField) {
        this.barrioSugeridoField = barrioSugeridoField;
    }

    public List<CmtAddressSuggestedDto> getBarrioSugeridoList() {
        return barrioSugeridoList;
    }

    public void setBarrioSugeridoList(List<CmtAddressSuggestedDto> barrioSugeridoList) {
        this.barrioSugeridoList = barrioSugeridoList;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public List<ParamMultivalor> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<ParamMultivalor> departamentoList) {
        this.departamentoList = departamentoList;
    }

    public List<ParamMultivalor> getCiudadesList() {
        return ciudadesList;
    }

    public void setCiudadesList(List<ParamMultivalor> ciudadesList) {
        this.ciudadesList = ciudadesList;
    }

    public List<ParamMultivalor> getCentroPobladoList() {
        return centroPobladoList;
    }

    public void setCentroPobladoList(List<ParamMultivalor> centroPobladoList) {
        this.centroPobladoList = centroPobladoList;
    }

    public List<OpcionIdNombre> getComplementoList() {
        return complementoList;
    }

    public void setComplementoList(List<OpcionIdNombre> complementoList) {
        this.complementoList = complementoList;
    }

    public List<OpcionIdNombre> getCkList() {
        return ckList;
    }

    public void setCkList(List<OpcionIdNombre> ckList) {
        this.ckList = ckList;
    }

    public List<OpcionIdNombre> getBmList() {
        return bmList;
    }

    public void setBmList(List<OpcionIdNombre> bmList) {
        this.bmList = bmList;
    }

    public List<OpcionIdNombre> getItList() {
        return itList;
    }

    public void setItList(List<OpcionIdNombre> itList) {
        this.itList = itList;
    }

    public List<OpcionIdNombre> getAptoList() {
        return aptoList;
    }

    public void setAptoList(List<OpcionIdNombre> aptoList) {
        this.aptoList = aptoList;
    }
    
    public List<OpcionIdNombre> getAptoListVisor() {
        return aptoListVisor;
    }

    public void setAptoListVisor(List<OpcionIdNombre> aptoListVisor) {
        this.aptoListVisor = aptoListVisor;
    }

    public List<OpcionIdNombre> getCkLetras() {
        return ckLetras;
    }

    public void setCkLetras(List<OpcionIdNombre> ckLetras) {
        this.ckLetras = ckLetras;
    }

    public List<OpcionIdNombre> getCkBis() {
        return ckBis;
    }

    public void setCkBis(List<OpcionIdNombre> ckBis) {
        this.ckBis = ckBis;
    }

    public List<OpcionIdNombre> getCardinalesList() {
        return cardinalesList;
    }

    public void setCardinalesList(List<OpcionIdNombre> cardinalesList) {
        this.cardinalesList = cardinalesList;
    }

    public List<OpcionIdNombre> getCruceslist() {
        return cruceslist;
    }

    public void setCruceslist(List<OpcionIdNombre> cruceslist) {
        this.cruceslist = cruceslist;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getFilasPag5() {
        return filasPag5;
    }

    public void setFilasPag5(int filasPag5) {
        this.filasPag5 = filasPag5;
    }

    public MapModel getModel() {
        return model;
    }

    public void setModel(MapModel model) {
        this.model = model;
    }

    public boolean isMostrarMapa() {
        return mostrarMapa;
    }

    public void setMostrarMapa(boolean mostrarMapa) {
        this.mostrarMapa = mostrarMapa;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public List<CmtCoverDto> getListCover() {
        return listCover;
    }

    public void setListCover(List<CmtCoverDto> listCover) {
        this.listCover = listCover;
    }

    public boolean isShowPanelBusquedaCentro() {
        return showPanelBusquedaCentro;
    }

    public void setShowPanelBusquedaCentro(boolean showPanelBusquedaCentro) {
        this.showPanelBusquedaCentro = showPanelBusquedaCentro;
    }

    public String isVolverPanelInicial() {
        return volverPanelInicial;
    }

    public void setVolverPanelInicial(String volverPanelInicial) {
        this.volverPanelInicial = volverPanelInicial;
    }

    public boolean isShowPanelCoordenadas() {
        return showPanelCoordenadas;
    }

    public void setShowPanelCoordenadas(boolean showPanelCoordenadas) {
        this.showPanelCoordenadas = showPanelCoordenadas;
    }

    public CmtCoverDto getCoberturaSeleccionada() {
        return coberturaSeleccionada;
    }

    public void setCoberturaSeleccionada(CmtCoverDto coberturaSeleccionada) {
        this.coberturaSeleccionada = coberturaSeleccionada;
    }

    public boolean isPointSelectEnabled() {
        return pointSelectEnabled;
    }

    public void setPointSelectEnabled(boolean pointSelectEnabled) {
        this.pointSelectEnabled = pointSelectEnabled;
    }

    public CmtDireccionDetalladaMglDto getDireccionDetalladaMglDtoSel() {
        return direccionDetalladaMglDtoSel;
    }

    public void setDireccionDetalladaMglDtoSel(CmtDireccionDetalladaMglDto direccionDetalladaMglDtoSel) {
        this.direccionDetalladaMglDtoSel = direccionDetalladaMglDtoSel;
    }

    public List<CmtDireccionDetalladaMglDto> getFilteredDireccionDetalladaList() {
        return filteredDireccionDetalladaList;
    }

    public void setFilteredDireccionDetalladaList(List<CmtDireccionDetalladaMglDto> filteredDireccionDetalladaList) {
        this.filteredDireccionDetalladaList = filteredDireccionDetalladaList;
    }

    public boolean isCoordinatesEnabled() {
        return coordinatesEnabled;
    }

    public void setCoordinatesEnabled(boolean coordinatesEnabled) {
        this.coordinatesEnabled = coordinatesEnabled;
    }

    public String getRutaServlet() {
        return rutaServlet;
    }

    public void setRutaServlet(String rutaServlet) {
        this.rutaServlet = rutaServlet;
    }

    public boolean isVerMapa() {
        return verMapa;
    }

    public void setVerMapa(boolean verMapa) {
        this.verMapa = verMapa;
    }

    public String getMsnError() {
        return msnError;
    }

    public void setMsnError(String msnError) {
        this.msnError = msnError;
    }

    public boolean isSoporteRequerido() {
        return soporteRequerido;
    }

    public void setSoporteRequerido(boolean soporteRequerido) {
        this.soporteRequerido = soporteRequerido;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public List<ParametrosCalles> getAreaCanalList() {
        return areaCanalList;
    }

    public void setAreaCanalList(List<ParametrosCalles> areaCanalList) {
        this.areaCanalList = areaCanalList;
    }

    public String getCanalHhpp() {
        return canalHhpp;
    }

    public void setCanalHhpp(String canalHhpp) {
        this.canalHhpp = canalHhpp;
    }

    public String getTipoViviendaSeleccionada() {
        return tipoViviendaSeleccionada;
    }

    public void setTipoViviendaSeleccionada(String tipoViviendaSeleccionada) {
        this.tipoViviendaSeleccionada = tipoViviendaSeleccionada;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getTelefonoSolicitante() {
        return telefonoSolicitante;
    }

    public void setTelefonoSolicitante(String telefonoSolicitante) {
        this.telefonoSolicitante = telefonoSolicitante;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public boolean isMostrarSolicitudHhpp() {
        return mostrarSolicitudHhpp;
    }

    public void setMostrarSolicitudHhpp(boolean mostrarSolicitudHhpp) {
        this.mostrarSolicitudHhpp = mostrarSolicitudHhpp;
    }

    public boolean isMostrarSolicitudHhppCcmm() {
        return mostrarSolicitudHhppCcmm;
    }

    public void setMostrarSolicitudHhppCcmm(boolean mostrarSolicitudHhppCcmm) {
        this.mostrarSolicitudHhppCcmm = mostrarSolicitudHhppCcmm;
    }

    public String getAreaCanal() {
        return areaCanal;
    }

    public void setAreaCanal(String areaCanal) {
        this.areaCanal = areaCanal;
    }

    public List<TipoHhppMgl> getTipoViviendaList() {
        return tipoViviendaList;
    }

    public void setTipoViviendaList(List<TipoHhppMgl> tipoViviendaList) {
        this.tipoViviendaList = tipoViviendaList;
    }

    public String getCorreoSolicitante() {
        return correoSolicitante;
    }

    public void setCorreoSolicitante(String correoSolicitante) {
        this.correoSolicitante = correoSolicitante;
    }

    public String getArchivoSoporte() {
        return archivoSoporte;
    }

    public void setArchivoSoporte(String archivoSoporte) {
        this.archivoSoporte = archivoSoporte;
    }

    public Solicitud getSolicitudCreated() {
        return solicitudCreated;
    }

    public void setSolicitudCreated(Solicitud solicitudCreated) {
        this.solicitudCreated = solicitudCreated;
    }

    public String getTecnologiaSeleccionada() {
        return tecnologiaSeleccionada;
    }

    public void setTecnologiaSeleccionada(String tecnologiaSeleccionada) {
        this.tecnologiaSeleccionada = tecnologiaSeleccionada;
    }

    public String getObservacionesSolicitud() {
        return observacionesSolicitud;
    }

    public void setObservacionesSolicitud(String observacionesSolicitud) {
        this.observacionesSolicitud = observacionesSolicitud;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getDireccionSeleccionada() {
        return direccionSeleccionada;
    }

    public void setDireccionSeleccionada(String direccionSeleccionada) {
        this.direccionSeleccionada = direccionSeleccionada;
    }

    public boolean isSolicitudCreada() {
        return solicitudCreada;
    }

    public void setSolicitudCreada(boolean solicitudCreada) {
        this.solicitudCreada = solicitudCreada;
    }

    public UploadedFile getUpFile() {
        return upFile;
    }

    public void setUpFile(UploadedFile upFile) {
        this.upFile = upFile;
    }

    public CmtCuentaMatrizMgl getCuentaMatrizSeleccionada() {
        return cuentaMatrizSeleccionada;
    }

    public void setCuentaMatrizSeleccionada(CmtCuentaMatrizMgl cuentaMatrizSeleccionada) {
        this.cuentaMatrizSeleccionada = cuentaMatrizSeleccionada;
    }

    public List<CmtSubEdificioMgl> getSubEdificioList() {
        return subEdificioList;
    }

    public void setSubEdificioList(List<CmtSubEdificioMgl> subEdificioList) {
        this.subEdificioList = subEdificioList;
    }

    public BigDecimal getSubEdificioSeleccionado() {
        return subEdificioSeleccionado;
    }

    public void setSubEdificioSeleccionado(BigDecimal subEdificioSeleccionado) {
        this.subEdificioSeleccionado = subEdificioSeleccionado;
    }

	

    public String getAsesorCcmm() {
        return asesorCcmm;
    }

    public void setAsesorCcmm(String asesorCcmm) {
        this.asesorCcmm = asesorCcmm;
    }

    public String getCorreoAsesor() {
        return correoAsesor;
    }

    public void setCorreoAsesor(String correoAsesor) {
        this.correoAsesor = correoAsesor;
    }

    public String getCorreoCopiaA() {
        return correoCopiaA;
    }

    public void setCorreoCopiaA(String correoCopiaA) {
        this.correoCopiaA = correoCopiaA;
    }

    public List<CmtBasicaMgl> getOrigenSolicitudList() {
        return origenSolicitudList;
    }

    public void setOrigenSolicitudList(List<CmtBasicaMgl> origenSolicitudList) {
        this.origenSolicitudList = origenSolicitudList;
    }

    public BigDecimal getOrigenSolicitud() {
        return origenSolicitud;
    }

    public void setOrigenSolicitud(BigDecimal origenSolicitud) {
        this.origenSolicitud = origenSolicitud;
    }

    @XmlTransient
    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

}
