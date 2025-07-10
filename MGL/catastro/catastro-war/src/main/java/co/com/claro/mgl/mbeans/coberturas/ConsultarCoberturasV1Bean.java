/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.coberturas;

import co.com.claro.app.dtos.ColumnDto;
import co.com.claro.mer.auth.micrositio.facade.LoginMicroSitioFacadeLocal;
import co.com.claro.mer.exception.ExceptionServBean;
import co.com.claro.mer.integration.ws.usaggeocoverage.UsagGeoCoverageRestClientPool;
import co.com.claro.mer.integration.ws.usaggecoverage.dto.CoordsOrder;
import co.com.claro.mer.integration.ws.usaggecoverage.dto.UsagGeoCoverageRestRequest;
import co.com.claro.mer.integration.ws.usaggecoverage.dto.UsagGeoCoverageRestResponse;
import co.com.claro.mer.parametro.facade.ParametroFacadeLocal;
import co.com.claro.mer.utils.SesionUtil;
import co.com.claro.mgl.businessmanager.address.DrDireccionManager;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.NodoMglManager;
import co.com.claro.mgl.businessmanager.address.VisorFactibilidadManager;
import co.com.claro.mgl.client.https.ConsumoGenerico;
import co.com.claro.mgl.constantes.cobertura.Constants;
import co.com.claro.mgl.dtos.*;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.*;
import co.com.claro.mgl.facade.cm.*;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.*;
import co.com.claro.mgl.mbeans.cm.ConsultaCuentasMatricesBean;
import co.com.claro.mgl.mbeans.cm.CuentaMatrizBean;
import co.com.claro.mgl.mbeans.cm.EncabezadoSolicitudModificacionCMBean;
import co.com.claro.mgl.mbeans.direcciones.DetalleHhppBean;
import co.com.claro.mgl.mbeans.direcciones.HhppDetalleSessionBean;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.mbeans.util.PrimeFacesUtil;
import co.com.claro.mgl.rest.dtos.*;
import co.com.claro.mgl.util.cm.ValidatePenetrationCuentaMatriz;
import co.com.claro.mgl.utils.*;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseConstruccionDireccion;
import co.com.claro.ofscCapacity.activityBookingOptions.GetActivityBookingResponses;
import co.com.claro.mer.integration.nodedistance.CalculateNodeDistanceRestClientPool;
import co.com.claro.mer.integration.nodedistance.dto.CalculateNodeDistanceRestRequest;
import co.com.claro.mer.integration.nodedistance.dto.CalculateNodeDistanceRestResponse;
import co.com.claro.mer.integration.nodedistance.exception.CalculateNodeDistanceRestClientException;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.ResponseMessage;
import co.com.telmex.catastro.utilws.SecurityLogin;
import com.amx.schema.fulfilloper.exp.customerorder.v1.CustomerOrderRequest;
import com.amx.schema.fulfilloper.exp.customerorder.v1.CustomerOrderResponse;
import com.amx.schema.fulfilloper.exp.customerorder.v1.RowSet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jlcg.db.exept.ExceptionDB;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.map.*;
import org.richfaces.component.util.Strings;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.annotation.XmlTransient;
import java.io.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Managed Bean utilizado para realizar <b>Consultas de Coberturas</b>
 * por distintos criterios.
 *
 * @author Victor Bocanegra (<i>bocanegravm</i>).
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
@ManagedBean(name = "consultarCoberturasV1Bean")
@ViewScoped
public class ConsultarCoberturasV1Bean implements Serializable, Observer {

    private static final Logger LOGGER = LogManager.getLogger(ConsultarCoberturasV1Bean.class);
    private static final String ESCALAMIENTOS_FACTIVILIDAD = "ESCALAMIENTOSFACTIVILIDAD";
    private static final String VISOR_DE_FACTIBILIDAD = "Visor de Factibilidad";

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

    private String espacio = "&nbsp;";
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
    private String msnError;
    //////
    private String longitud;
    private String latitud;
    private List<GeograficoPoliticoMgl> departamentoList;
    private List<GeograficoPoliticoMgl> ciudadesList;

    private final RequestConstruccionDireccion request
            = new RequestConstruccionDireccion();
    private List<OpcionIdNombre> ckList = new ArrayList<>();
    private List<OpcionIdNombre> bmList = new ArrayList<>();
    private List<OpcionIdNombre> itList = new ArrayList<>();
    private List<OpcionIdNombre> aptoList = new ArrayList<>();
    private List<OpcionIdNombre> ckLetras = new ArrayList<>();
    private List<OpcionIdNombre> ckBis = new ArrayList<>();
    private List<OpcionIdNombre> cardinalesList = new ArrayList<>();
    private List<OpcionIdNombre> cruceslist = new ArrayList<>();
    private List<OpcionIdNombre> complementoList = new ArrayList<>();
    private DrDireccion drDireccion = new DrDireccion();
    private ResponseConstruccionDireccion responseConstruirDireccion
            = new ResponseConstruccionDireccion();
    private GeograficoPoliticoMgl centroPobladoSelected;
    private GeograficoPoliticoMgl ciudadSelected;
    private GeograficoPoliticoMgl dptoSelected;
    private String regresarMenu = "<- Regresar Menú";
    private String pageActual;
    private String pageActualCob;
    private String numPagina = "1";
    private String numPaginaCob = "1";
    private List<Integer> paginaList;
    private int actual;
    private List<Integer> paginaListCob;
    private int actualCob;
    private int filasPag = Constants.PAGINACION_DIEZ_FILAS;
    private int filasPag5 = Constants.PAGINACION_CINCO_FILAS;
    private List<CmtDireccionDetalladaMglDto> direccionDetalladaList;
    private List<CmtDireccionDetalladaMglDto> direccionDetalladaListAux;
    /**
     * Lista resultante de filtrar las direcciones desde el componente.
     */
    private List<CmtDireccionDetalladaMglDto> filteredDireccionDetalladaList;
    private CmtSuggestedNeighborhoodsDto neighborhoodsDto;
    private List<CmtAddressSuggestedDto> barrioSugeridoList;
    private boolean select;
    private boolean mostrarMapa = false;
    private String center;
    /**
     * Direcci&oacute;n que fue seleccionada desde la tabla.
     */
    private CmtDireccionDetalladaMglDto direccionDetalladaMglDtoSel;
    private List<CmtCoverDto> listCover;

    private List<DetalleFactibilidadMgl> detalleFactibilidadMgls;
    private List<DetalleFactibilidadMgl> detalleFactibilidadMglsAux;

    private boolean showPanelCoordenadas = false;
    private boolean showPanelIngresarUbicacion = false;
    private boolean showPanelIngresarGPS = false;

    /**
     * Flag asociado a la funcionalidad seleccionada a trav&eacute;s del
     * men&uacute; del aplicativo.
     */
    private int opcionSeleccionada;
    private DetalleFactibilidadMgl detalleFactibilidadMglSelected;
    private Map<String, NodoMgl> mapNodos;
    /**
     * Flag que determina si se encuentra habilitada la selecci&oacute;n de
     * puntos en el Mapa.
     */
    private boolean pointSelectEnabled = false;
    private List<ResponseConstruccionDireccion> lstResponseConstruirDireccion = new ArrayList<>();
    private HashMap<ResponseConstruccionDireccion, BigDecimal> paramsConsulta = new HashMap<>();
    private List<CmtRequestHhppByCoordinatesDto> requestCoordenadas = new ArrayList<>();
    private CmtRequestHhppByCoordinatesDto coordenadaAgregar;
    private String expRegCoorLis;

    private boolean showCKPanel;
    private boolean showBMPanel;
    private boolean showITPanel;
    private boolean showFooter;
    private boolean showPanelBusquedaDireccion = false;
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
    private boolean verListadoDirecciones;
    private boolean mostrarBotonAtras;
    private String rutaServlet;
    private ConfigurationAddressComponent configurationAddressComponent;
    private List<HhppMgl> hhppMglsCov;
    private BigDecimal idFactibilidad;
    private String nameEdificio;
    
    private List<String> nameCentroPobladoList;
    private List<GeograficoPoliticoMgl> listCentroPoblados;
    private String nombreCentroPoblado;
    private boolean habilitaDpto = true;
    private boolean habilitaCity = true;
    private BigDecimal idCiudad;
    private BigDecimal idDpto;
    private List<CmtCuentaMatrizMgl> listCcmm;
    private CmtCuentaMatrizMgl ccmmSeleccionada;
    private String fechaDia;
    private String fechaCreacionFactibilidad;
    private String horaCreacionFactibilidad;
    private boolean dirIsCcmm;
    private boolean dirIsHhpp;
    private boolean dirIsUnidades;
    private List<CmtSubEdificioMgl> edificiosCcmmList;
    private List<HhppMgl> hhppCcmmList;
    private List<CmtDireccionDetalladaMgl> uniExistentesList;
    private CmtSubEdificioMgl cmtSubEdificioMglSel;
    private SecurityLogin securityLogin;
    //OPCION INGRESAR DIRECCION
    private final String INGRESARDIRECCION = "INGRESARDIRECCION";
    private final String AGREGARDIRFACT = "AGREGARDIRFACT";
    private final String PROCESARDIRFACT = "PROCESARDIRFACT";
    private final String CONSTRUIRDIRFACT = "CONSTRUIRDIRFACT";
    private final String FACTIBILIZARDIR = "FACTIBILIZARDIR";
    private final String LINKIDUNIDEXIST = "LINKIDUNIDEXIST";
    private final String EVENTTABLEDIR = "EVENTTABLEDIR";

    //OPCION INGRESAR PUNTO DE UBICACION
    private final String INGRESARPUNTOUBI = "INGRESARPUNTOUBI";
    private final String BUSCARPTOUBICACION = "BUSCARPTOUBICACION";
    private final String FACTPTOUBICACION = "FACTPTOUBICACION";
    //OPCION INGRESAR GPS
    private final String CONSULTAGPS = "CONSULTAGPS";
    private final String BUSCARGPS = "BUSCARGPS";
    private final String FACTIBILIZARGPS = "FACTIBILIZARGPS";
    //OPCION INGRESAR COORDENADAS
    private final String INGRESARCOORD = "INGRESARCOORD";
    private final String AGREGARDIRCOORD = "AGREGARDIRCOORD";
    private final String PROCESARDIRCOORD = "PROCESARDIRCOORD";
    private final String CONSTRUIRDIRCOORD = "CONSTRUIRDIRCOORD";
    private final String FACTIBILIZARDIRCOORD = "FACTIBILIZARDIRCOORD";
    private final String LINKIDUNIDCUENTACOORD = "LINKIDUNIDCUENTACOORD";
    private final String LINKIDUNIDDIRCOORD = "LINKIDUNIDDIRCOORD";
    private final String EVENTTABLEDIRCOORD = "EVENTTABLEDIRCOORD";
    private final String LINKTECPOLIGONO = "LINKTECPOLIGONO";
    private final String MAPAGOOGLE = "MAPAGOOGLE";
    private final String VALIDARCOBERTURA = "VALIDARCOBERTURA";
    private final String CREARSOLIVALCOBERTURA = "CREARSOLIVALCOBERTURA";
    private String mapaGoogle;
    private String colorTecno;
    private List<DetalleSlaEjecucionMgl> detalleSlaEjecucion;
    private boolean controlCcmmDetalle;
    private boolean controlHhppDetalle;
    private List<CmtBasicaMgl> listaTecnlogias;
    private boolean mostrarValidacionCobertura = false;
    private BigDecimal tecnologia;
    private String tipoSolicitud;
    private String tipoServicio;
    private String tipoValidacion;
    private String bw;
    private String tipoSitio;
    private boolean panelDetalleCobertura = false;
    private String correoUsuario;
    private String correoUsuarioCopia;
    private List<CmtBasicaMgl> listBasicaDivisional;
    private BigDecimal divisional;
    private boolean mostrarMapaCober = false;
    private int perfil = 0;
    private boolean crearCcmmMenuItem = false;
    private boolean modCcmmMenuItem = false;
    private boolean crearModHhppMenuItem = false;
    private boolean crearVtMenuItem = false;
    private boolean crearHhppMenuItem = false;
    private boolean validarCoberturaMenuItem = false;
    private boolean historicoMenuItem = false;
    private boolean mostrarHistorico = false;
    private List<FactibilidadMgl> listaFactibilidades;
    private List<CmtDireccionDetalladaMglDto> listaHistorico;
    private CmtDireccionDetalladaMglDto direccionHistoricoSelected;
    private List<CmtDireccionDetalladaMglDto> filteredDireccionHistDetalladaList;
    private boolean mostrarDetalleHistorico = false;
    private List<DetalleFactibilidadMgl> listaDetalleHistorico;
    private CmtDireccionDetalladaMglDto direccionHistoricoDetalleSelected;
    private List<String> listaDesplegableFechaIdFact;
    private GeograficoPoliticoMgl centroPobladoSolSelected;
    private GeograficoPoliticoMgl ciudadSolSelected;
    private GeograficoPoliticoMgl dptoSolSelected;
    private String telefono;
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    private String filtroIdFactFecha;
    private Map<String, BigDecimal> listaFechaIdFact = null;
    public List<SelectItem> itemsFiltros;
    private UploadedFile upFile;
    private boolean enproceso;
    private String nombre;
    private String usuarioProceso;
    private double cantidadRegistros;
    private Integer totalRegistros;
    private boolean finish;
    private String csvMaxReg;
    private Integer refresh;
    private boolean mensajeError;
    private static final Integer REFRESH_INI = 100000;
    private static final Integer REFRESH_FINAL = 10000;
    private static final String ARCHIVO_EN_PROCESO = "Se esta procesando un archivo";
    private static final String ARCHIVO_NULL = "Por favor seleccionar un archivo para cargar...";
    private static final String ARCHIVO_INVALIDO = "No es valido el tipo de archivo ";
    private List<String> lineasArchivoRetorno;
    private final Locale LOCALE = new Locale("es", "CO");
    private List<CmtDireccionDetalladaMglDto> listResultadoMasivo;
    private static final String[] NOM_COLUMNAS = {"FACTIBILIDAD_ID",
            "UBICACION_INGRESADA", "UBICACION_GENERADA", "DEPARTAMENTO", "CIUDAD", "CENTRO_POBLADO",
            "FECHA_CONSULTA", "USUARIO", "LATITUD", "LONGITUD", "ID_RR_CCMM", "ID_MER_CCMM", "NOMBRE_CCMM",
            "NOMBRE_SUBEDIFICIO", "CUENTA_CIENTE", "TIPO_DIRECCION", "TECNOLOGIA", "NODO MER", "ESTADO_NODO", "ESTADO_TECNOLOGIA",
            "FACTIBILIDAD", "ARRENDATARIO", "TIEMPO_INSTALACION", "NODO_APROXIMADO", "DISTANCIA", "NODO_BACKUP", "DIRECCION_ID", "NODO SITIDATA", "ESTRATO"};
    private static final String[] NOM_COLUMNAS_PLANTILLA = {"codigoDane", "indicador", "ubicacion",
            "idTipoDireccion", "Barrio", "CpTipoNivel1", "CpTipoNivel5",
            "CpValorNivel1", "CpValorNivel5", "MzTipoNivel1", "MzTipoNivel2", "MzTipoNivel3",
            "MzTipoNivel4", "MzTipoNivel5", "MzTipoNivel6", "MzValorNivel1", "MzValorNivel2",
            "MzValorNivel3", "MzValorNivel4", "MzValorNivel5", "MzValorNivel6", "ItTipoPlaca",
            "ItValorPlaca"};
    private static final String[] NOM_COLUMNAS_CODIGO_DANE = {"DEPARTAMENTO", "CIUDAD", "CENTRO_POBLADO",
            "CODIGO_DANE"};
    private static final String[] NOM_COLUMNAS_LOG_FACTIBILIDADES = {"idFactibilidad", "fechaFactibilidad", "fechaVencimientoFactibilidad",
            "tecnologia", "SDS", "cobertura", "claseFactibilidad", "arrendatario", "tiempoInstalaciónUM", "estadoTecnología"};

    private String cantidadregistrosParam;
    private CmtDireccionDetalladaMglDto direccionDetalladaMglDtoSelUnidad;
    private BigDecimal idSubEdificioMglSel;
    private List<DetalleFactibilidadLogDto> detalleFactibilidadLogDto;
    private boolean verListadoLogDirecciones;
    private String observacionesSolicitudValCobertura;
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private final HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private List<String> cuadranteMgls;
    private String cuadranteArrendatario;
    private LatLng latLng;
    private List<String> tecnologiasPenetracion;//Brief 98062
    private static final String FACTIBILIDAD_NEGATIVA = "NEGATIVA";
    private boolean corporativo = false;
    private boolean residencial = false;
    private CustomerOrderResponse responseCustomerOrdersTipoSolicitud;
    private CustomerOrderResponse responseCustomerOrdersTipoServicio;
    private CustomerOrderResponse responseCustomerOrdersTipoValidacion;
    private CustomerOrderResponse responseCustomerOrdersbw;
    private CustomerOrderResponse responseCustomerOrdersTipoSitio;
    private List<ColumnDto> tipoSolicitudList;
    private List<ColumnDto> tipoServicioList;
    private List<ColumnDto> tipoValidacionList;
    private List<ColumnDto> bwList;
    private List<TipoHhppMgl>  tipoSitioList;
    private String tipoSolicitudSelected;//Brief 655423
    private String tipoServicioSelected;//Brief 655423
    private boolean corporativoPrincipal = false;//Brief 655423
    private DetalleHhppBean detalleHhppBean;
    private Map<String,Object> paramsNodeDist;
    /**
     * Título principal en la cabecera del popup.
     */
    @Getter
    private String tituloPopupWarning;
    /**
     * Título descriptivo del mensaje asignado al momento de mostrar el popup.
     */
    @Getter
    private String tituloMsgDetallePopupWarning;
    /**
     * Indica si el tipo de popup a mostrar es de tipo warning.
     */
    @Getter
    private boolean isWarningMessage = false;

    /* --------------- Inyección de dependencias -----------------------*/
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoFacadeLocal;

    @EJB
    private CmtComponenteDireccionesMglFacadeLocal cmtComponenteDireccionesMglFacadeLocal;

    @EJB
    private DireccionesValidacionFacadeLocal direccionesValidacionFacadeLocal;

    @EJB
    private DrDireccionFacadeLocal drDireccionFacadeLocal;

    @EJB
    private CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMglFacadeLocal;

    @EJB
    private NodoPoligonoFacadeLocal nodoPoligonoFacadeLocal;

    @EJB
    private CmtDireccionMglFacadeLocal cmtDireccionMglFacadeLocal;

    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;

    @EJB
    private HhppMglFacadeLocal hhppMglFacadeLocal;

    @EJB
    private FactibilidadMglFacadeLocal factibilidadMglFacadeLocal;

    @EJB
    private DetalleFactibilidadMglFacadeLocal detalleFactibilidadMglFacadeLocal;

    @EJB
    private CmtCuentaMatrizMglFacadeLocal cuentaMatrizMglFacadeLocal;

    @EJB
    private CmtSubEdificioMglFacadeLocal cmtSubEdificioMglFacadeLocal;
    //////
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;

    @EJB
    private ArrendatarioMglFacadeLocal arrendatarioMglFacadeLocal;

    @EJB
    private SlaEjecucionMglFacadeLocal slaEjecucionMglFacadeLocal;

    @EJB
    private DetalleSlaEjeMglFacadeLocal detalleSlaEjeMglFacadeLocal;

    @EJB
    private CmtTecnologiaSubMglFacadeLocal tecnologiaSubMglFacadeLocal;

    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;

    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;

    @EJB
    private SolicitudFacadeLocal solicitudFacadeLocal;

    @EJB
    private CmtTiempoSolicitudMglFacadeLocal cmtTiempoSolicitudMglFacadeLocal;

    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;

    @EJB
    private OtHhppMglFacadeLocal otHhppMglFacadeLocal;

    @EJB
    private DireccionRRFacadeLocal direccionRRFacadeLocal;
    @EJB
    private ParametroFacadeLocal parametroFacadeLocal;
    @EJB
    private TipoHhppMglFacadeLocal tipoHhppMglFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    @EJB
    private LoginMicroSitioFacadeLocal loginMicroSitioFacadeLocal;
    @Inject
    private ExceptionServBean exceptionServBean;
    
    /**
     * Instrucciones que se ejecutan al momento posterior de construir el
     * objeto.
     */
    @PostConstruct
    private void init() {
        assignDefaultDetailsToPopup();

        try {
            securityLogin = new SecurityLogin(facesContext);
            Map<String, String> params = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap();
            String seguridad = params.get("security");
            String consulta = params.get("consulta");
            customerOrder();
            if (seguridad != null && !seguridad.isEmpty()) {
                if (seguridad != null && !seguridad.isEmpty()) {
                    seguridad = seguridad.replace(" ", "+");
                    consulta = consulta.replace(" ", "+");
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
                String usuario = paramQueryString[3];
                String perfilVt = paramQueryString[4];
                String nombreUsuario = paramQueryString[5];
                String correoUsuario = paramQueryString[6];
                String idUsuario = paramQueryString[7];

                session.setAttribute("token", token);
                session.setAttribute("consulta", consulta + token);
                
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

                session.setAttribute("cookieXml", seguridad);
                this.refresh = REFRESH_INI;
                this.enproceso = MasivoControlFactibilidadDtoMgl.isIsProcessing();

                if (!MasivoControlFactibilidadDtoMgl.isIsProcessing()) {

                    if (MasivoControlFactibilidadDtoMgl.getEndProcessDate() != null) {
                    } else {
                        try {
                            MasivoControlFactibilidadDtoMgl.cleanBeforeStart();
                        } catch (ApplicationException ex) {
                            LOGGER.error("Se genero error en init de ConsultarCoberturasV1Bean class ...", ex);
                        }
                    }
                }
                msnError = "";
                showPanelBusquedaDireccion = false;
                showPanelIngresarUbicacion = false;
                showPanelIngresarGPS = false;
                showPanelCoordenadas = false;
                mostrarMapa = false;
                this.coordinatesEnabled = true;
                actual = 1;
                actualCob = 1;
                this.opcionSeleccionada = 0;

                this.mapNodos = new HashMap<>();
                idFactibilidad = null;
                nameEdificio = "";
                colorTecno = "yellow";

                perfil = Integer.parseInt(perfilVt);
                boolean flagMicro = Optional.ofNullable((Boolean) session.getAttribute("flagMicro")).orElse(false);

                if (flagMicro) {
                    usuarioLogin = SesionUtil.getUserDataMicroSitio();
                } else {
                    usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioSesion);
                }

                Date dateHoy = new Date();
                DateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
                String str3 = fecha.format(dateHoy);
                String[] parts = str3.split("-");
                String mes = retornaMes(parts[1]);
                fechaDia = parts[0] + "-" + mes + "-" + parts[2];
                historicoMenuItem = true;

                correoUsuario = usuarioLogin.getEmail();
                try {
                    obtenerNombresCentroPobladosList();
                } catch (ApplicationException ex) {
                    error("Ocurrio un error consultado los departamentos: " + ex.getMessage());
                }
                showCK();
                if (validarRutaMapaGoogle()) {
                    try {
                        mapaGoogle = consultarParametro(Constants.RUTA_MAPA_GOOGLE);
                    } catch (ApplicationException ex) {
                        LOGGER.error("Se genero error en init de ConsultarCoberturasV1Bean class ...", ex);
                    }
                }

                if (flagMicro) {
                    CompletableFuture.runAsync(this::registrarLogin);
                }

            } else {
                this.refresh = REFRESH_INI;
                this.enproceso = MasivoControlFactibilidadDtoMgl.isIsProcessing();

                if (!MasivoControlFactibilidadDtoMgl.isIsProcessing()) {

                    if (MasivoControlFactibilidadDtoMgl.getEndProcessDate() != null) {
                    } else {
                        try {
                            MasivoControlFactibilidadDtoMgl.cleanBeforeStart();
                        } catch (ApplicationException ex) {
                            LOGGER.error("Se genero error en init de ConsultarCoberturasV1Bean class ...", ex);
                        }
                    }
                }
                msnError = "";
                showPanelBusquedaDireccion = false;
                showPanelIngresarUbicacion = false;
                showPanelIngresarGPS = false;
                showPanelCoordenadas = false;
                mostrarMapa = false;
                this.coordinatesEnabled = true;
                actual = 1;
                actualCob = 1;
                this.opcionSeleccionada = 0;

                this.mapNodos = new HashMap<>();
                idFactibilidad = null;
                nameEdificio = "";
                colorTecno = "yellow";
                try {
                    securityLogin = new SecurityLogin(facesContext);
                    usuarioSesion = securityLogin.getLoginUser();
                    perfil = securityLogin.getPerfilUsuario();
                    usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioSesion);
                    Date dateHoy = new Date();
                    DateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
                    String str3 = fecha.format(dateHoy);
                    String[] parts = str3.split("-");
                    String mes = retornaMes(parts[1]);
                    fechaDia = parts[0] + "-" + mes + "-" + parts[2];
                    historicoMenuItem = true;
                    correoUsuario = usuarioLogin.getEmail();
                } catch (IOException | ApplicationException e) {
                    LOGGER.error("Se produjo un error al momento de consultar la información de la sesión: " + e.getMessage(), e);
                }

                try {
                    obtenerNombresCentroPobladosList();
                } catch (ApplicationException ex) {
                    error("Ocurrio un error consultado los departamentos: " + ex.getMessage());
                }
                showCK();

                if (validarRutaMapaGoogle()) {
                    try {
                        mapaGoogle = consultarParametro(Constants.RUTA_MAPA_GOOGLE);
                    } catch (ApplicationException ex) {
                        LOGGER.error("Se genero error en init de ConsultarCoberturasV1Bean class ...", ex);
                    }
                }
            }
        } catch (IOException | ApplicationException e) {
            String msgError = "Se produjo un error al momento de consultar la información de inicio en consultarCoberturasV1BEAN: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, VISOR_DE_FACTIBILIDAD);
        }
    }

    /**
     * Constructor.
     */
    public ConsultarCoberturasV1Bean() {
        try {
            HttpServletResponse response = (HttpServletResponse) facesContext
                    .getExternalContext().getResponse();

            // Verificar el estado de la sesion.
            securityLogin = new SecurityLogin(facesContext);

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError(this.getClass().getName() + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError(this.getClass().getName() + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Registra el login del usuario en el histórico de sesiones de micrositio.
     * @author Gildardo Mora
     */
    private void registrarLogin() {
        try {
            loginMicroSitioFacadeLocal.registerUserLogin(usuarioLogin.getUsuario(), usuarioLogin.getCodPerfil());
        } catch (ApplicationException e) {
            String msgError = "Error al registrar el loginde micrositio en el histórico de sesiones.";
            LOGGER.error(msgError, e);
        }
    }

    /**
     * Muestra el Panel de Consulta por <b>Direcci&oacute;n</b>.
     *
     * @throws ApplicationException
     */
    public void verPanelDireccion() throws ApplicationException {

        // Marcar la opcion seleccionada:
        this.opcionSeleccionada = FUNCIONALIDAD_INGRESAR_DIRECCION;

        // Deshabilitar el evento Point Select:
        this.pointSelectEnabled = false;

        showPanelBusquedaDireccion = true;
        showPanelIngresarUbicacion = false;
        showPanelIngresarGPS = false;
        showPanelCoordenadas = false;
        mostrarBotonAtras = false;
        verListadoDirecciones = false;
        verListadoLogDirecciones = false;
        departamentoList = new ArrayList<>();
        ciudadesList = new ArrayList<>();
        centroPobladoSelected = null;
        nombreCentroPoblado = "";
        mostrarMapa = false;
        habilitaDpto = true;
        habilitaCity = true;
        idFactibilidad = null;
        nameEdificio = "";
        limpiarPanelDir();
        limpiarBusqueda();
        dirIsCcmm = false;
        dirIsHhpp = false;
        mostrarValidacionCobertura = false;
        panelDetalleCobertura = false;
        mostrarMapaCober = false;
        mostrarHistorico = false;
        mostrarDetalleHistorico = false;

        String tipoRed = "";

        try {
            tipoRed = consultarParametro(Constants.TIPO_RED_CONSULTA);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("No se ha configurado el parametro para consultar los componentes de dirección, "
                    + "por favor establezca el registro 'TIPO_RED_CONSULTA'.", e);
            throw e;
        }

        // Obtener la informacion de direcciones:
        CmtConfiguracionDto request1 = new CmtConfiguracionDto();
        request1.setTipoRed(tipoRed);

        configurationAddressComponent
                = cmtComponenteDireccionesMglFacadeLocal.getConfiguracionComponente();

        CmtConfigurationAddressComponentDto addressComponent;

        addressComponent
                = parserConfigurationAddressComponentToCmtConfigurationAddressComponentDto(configurationAddressComponent);

        complementoList = new ArrayList<>();
        aptoList = new ArrayList<>();

        // Convertir objeto a JSON:
        JSONObject jsonObj = this.objectToJSONObject(addressComponent);

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

    }

    /**
     * <b>Mensaje de Informaci&oacute;n</b>.
     * <p>
     * metodo que muestra el mensaje de informacion, que llega como parametro,
     * en la vista
     *
     * @param mensaje
     * @author Victor Bocanegra
     */
    public void info(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", mensaje));
    }

    /**
     * <b>Mensaje de Advertencia</b>.
     * <p>
     * M&eacute;todo que muestra el mensaje de advertencia, que llega como
     * par&aacute;metro, en la vista.
     *
     * @param mensaje Mensaje a mostrar en pantalla.
     */
    private void warn(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, ""));
    }

    /**
     * <b>Mensaje de Error</b>.
     * <p>
     * M&eacute;todo que muestra el mensaje de error, que llega como
     * par&aacute;metro, en la vista.
     *
     * @param mensaje Mensaje a mostrar en pantalla.
     */
    private void error(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, ""));
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
    }

    /**
     * Funci&oacute;n que limpiar los valores de la pantalla
     */
    public void limpiarBusqueda() throws ApplicationException {

        // Reiniciar todos los campos diligenciados en el formulario:
        PrimeFaces.current().resetInputs("formPer:contenidoBusqueda");

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
        idCiudad = null;
        idDpto = null;
        nombreCentroPoblado = "";
        division = "";
        comunidad = "";
        tipoDireccion = "CK";
        complemento = "";
        apartamento = "";
        direccionDetalladaList = new ArrayList<>();
        filteredDireccionDetalladaList = null;
        this.detalleFactibilidadMglSelected = null;
        showFooter = false;
        showPanelBusquedaDireccion = true;
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
        showCK();
        lstResponseConstruirDireccion = new ArrayList<>();
        paramsConsulta = new HashMap<>();
        habilitaCity = true;
        habilitaDpto = true;
        MasivoControlFactibilidadDtoMgl.setListArchivoWithErrors(null);
        lineasArchivoRetorno = null;
        nameEdificio = "";
        ciudadSelected = null;
        centroPobladoSelected = null;
    }

    public void limpiarBusquedaDir() {

        // Reiniciar todos los campos diligenciados en el formulario:
        PrimeFaces.current().resetInputs("formPer:contenidoBusqueda");

        direccionCk = "";
        nivelValorIt = "";
        nivelValorBm = "";
        tipoNivelBm = "";
        tipoNivelIt = "";
        tipoNivelApartamento = "";
        tipoNivelComplemento = "";
        direccionLabel = "";
        division = "";
        comunidad = "";
        tipoDireccion = "CK";
        complemento = "";
        apartamento = "";
        direccionDetalladaList = new ArrayList<>();
        filteredDireccionDetalladaList = null;
        this.detalleFactibilidadMglSelected = null;
        showFooter = false;
        showPanelBusquedaDireccion = true;
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
        idFactibilidad = null;
        nameEdificio = "";
        showCK();
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
        idCiudad = null;
        idDpto = null;
        nombreCentroPoblado = "";
        division = "";
        comunidad = "";
        tipoDireccion = "CK";
        complemento = "";
        apartamento = "";
        direccionDetalladaList = new ArrayList<>();
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
        detalleFactibilidadMgls = new ArrayList<>();
        this.detalleFactibilidadMglSelected = null;
        this.coordinatesEnabled = true;
        showCK();

    }

    /**
     * Función que realiza la busqueda de direcciones que coincidan con los
     * criterios de busqueda ingresados en pantalla de busqueda de direccion.
     *
     * @author Victor Bocanegra
     */
    public void buscarDireccion() {
        assignDefaultDetailsToPopup();

        try {

            if (paramsConsulta != null && !paramsConsulta.isEmpty()) {
                showPanelBusquedaDireccion = false;
                verListadoDirecciones = true;

                mostrarMapa = false;
                this.latitud = "";
                this.longitud = "";
                this.center = "";
                this.direccionDetalladaMglDtoSel = null;
                this.detalleFactibilidadMglSelected = null;

                if (!notGeoReferenciado) {
                    if (drDireccion == null) {
                        msnError = "Es necesario construir una dirección con el panel de dirección tabulada.";
                        alerts();
                        PrimeFaces.current().executeScript("PF('dlg2').show();");
                        return;
                    }
                    responseConstruirDireccion.setDrDireccion(drDireccion);
                }

                direccionDetalladaList = direccionesDetalladasConsulta();
                if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {
                    direccionDetalladaListAux = direccionDetalladaList;
                    direccionDetalladaMglDtoSel = direccionDetalladaList.get(0);
                    PrimeFaces current = PrimeFaces.current();
                    PrimeFacesUtil.update("@widgetVar(tablaDireccion)");
                    current.executeScript("PF('tablaDireccion').clearFilters();");

                    PrimeFacesUtil.scrollTo("panelDir");
                }

            } else {

                msnError = "Adicione al menos una dirección para la busqueda"
                        + " por favor. ";
                alerts();

            }

        } catch (ApplicationException e) {
            String msgError = "Error al momento de realizar la búsqueda de la dirección: ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), VISOR_DE_FACTIBILIDAD);
        } catch (Exception e) {
            String msgError = "Ocurrió un error al momento de realizar la búsqueda de la dirección: ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), VISOR_DE_FACTIBILIDAD);
        }
    }

    /**
     * Función que se utiliza para validar el barrio sugerido seleccionado
     *
     * @author Victor Bocanegra
     */
    public void validarBarrioSugeridoSeleccionado() {
        assignDefaultDetailsToPopup();

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
                    responseConstruirDireccion.getDrDireccion().setBarrio(barrioSugeridoStr);
                    String dirStr = responseConstruirDireccion.getDireccionStr();
                    //JDHT georeferenciacion de direcciones CK y BM para actualizar coberturas
                    try {
                        georeferenciarDireccionActualizarCoberturas(responseConstruirDireccion, centroPobladoSelected, responseConstruirDireccion.getDrDireccion());
                    } catch (Exception e) {
                        LOGGER.error("Error georeferenciando direccion "
                                + "para actualizar coberturas: " + e.getMessage() + "");
                    }
                    responseConstruirDireccion.setDireccionStr(responseConstruirDireccion.getDireccionStr() + " " + barrioSugeridoStr);
                    responseConstruirDireccion.setNombreDpto(dptoSelected.getGpoNombre());
                    responseConstruirDireccion.setNombreCity(ciudadSelected.getGpoNombre());
                    responseConstruirDireccion.setNombreCentro(centroPobladoSelected.getGpoNombre());

                    if (!lstResponseConstruirDireccion.isEmpty()) {
                        int control = 0;
                        for (ResponseConstruccionDireccion response : lstResponseConstruirDireccion) {
                            if ((response.getDireccionStr().equalsIgnoreCase(responseConstruirDireccion.getDireccionStr()))
                                    && (response.getNombreCentro().equalsIgnoreCase(responseConstruirDireccion.getNombreCentro()))
                                    && (response.getNombreCity().
                                    equalsIgnoreCase(responseConstruirDireccion.getNombreCity()))) {
                                control++;
                            }
                        }
                        if (control > 0) {
                            msnError = "Ya existe en lista una dirección igual a la que intenta agregar.";
                            alerts();
                            barrioSugeridoStr = "";
                            responseConstruirDireccion.setDireccionStr(dirStr);
                        } else {
                            lstResponseConstruirDireccion.add(responseConstruirDireccion);
                            paramsConsulta.put(responseConstruirDireccion, centroPobladoSelected.getGpoId());
                            limpiarBusquedaDir();
                        }
                    } else {
                        lstResponseConstruirDireccion.add(responseConstruirDireccion);
                        paramsConsulta.put(responseConstruirDireccion, centroPobladoSelected.getGpoId());
                        limpiarBusquedaDir();
                    }
                }
            }
        } catch (Exception e) {
            String msnError = "Error al validar barrio sugerido seleccionado: ";
            LOGGER.error(msnError, e);
            exceptionServBean.notifyError(e, msnError + e.getMessage(), VISOR_DE_FACTIBILIDAD);
        }
    }

    /**
     * Función que agrega el barrio sugerido ingresado manualmente
     *
     * @author Victor Bocanegra
     */
    public void agregarBarrioSugerido() {
        assignDefaultDetailsToPopup();

        try {
            if (barrioSugeridoStr == null || barrioSugeridoStr.isEmpty() || barrioSugeridoStr.equals("")) {
                msnError = "Ingrese el nombre de un barrio sugerido por "
                        + "favor.";
                alerts();
            } else {
                barrioSugeridoStr = barrioSugeridoStr.toUpperCase();
                responseConstruirDireccion.getDrDireccion().setBarrio(barrioSugeridoStr.toUpperCase());

                //JDHT georeferenciacion de direcciones CK y BM para actualizar coberturas
                try {
                    georeferenciarDireccionActualizarCoberturas(responseConstruirDireccion, centroPobladoSelected, responseConstruirDireccion.getDrDireccion());
                } catch (Exception e) {
                    LOGGER.error("Error georeferenciando direccion "
                            + " para actualizar coberturas: " + e.getMessage() + "");
                }

                String dirStr = responseConstruirDireccion.getDireccionStr();
                responseConstruirDireccion.setDireccionStr(responseConstruirDireccion.getDireccionStr() + " " + barrioSugeridoStr.toUpperCase());
                responseConstruirDireccion.setNombreDpto(dptoSelected.getGpoNombre());
                responseConstruirDireccion.setNombreCity(ciudadSelected.getGpoNombre());
                responseConstruirDireccion.setNombreCentro(centroPobladoSelected.getGpoNombre());

                if (!lstResponseConstruirDireccion.isEmpty()) {
                    int control = 0;
                    for (ResponseConstruccionDireccion response : lstResponseConstruirDireccion) {
                        if ((response.getDireccionStr().equalsIgnoreCase(responseConstruirDireccion.getDireccionStr()))
                                && (response.getNombreCentro().equalsIgnoreCase(responseConstruirDireccion.getNombreCentro()))
                                && (response.getNombreCity().
                                equalsIgnoreCase(responseConstruirDireccion.getNombreCity()))) {
                            control++;
                        }
                    }
                    if (control > 0) {
                        msnError = "Ya existe en lista una dirección igual a la que intenta agregar.";
                        alerts();
                        barrioSugeridoStr = "";
                        responseConstruirDireccion.setDireccionStr(dirStr);

                    } else {
                        lstResponseConstruirDireccion.add(responseConstruirDireccion);
                        paramsConsulta.put(responseConstruirDireccion, centroPobladoSelected.getGpoId());
                        limpiarBusquedaDir();
                    }
                } else {
                    lstResponseConstruirDireccion.add(responseConstruirDireccion);
                    paramsConsulta.put(responseConstruirDireccion, centroPobladoSelected.getGpoId());
                    limpiarBusquedaDir();
                }

            }
        } catch (Exception e) {
            String msnError = "Error al agregar barrio sugerido.";
            LOGGER.error(msnError, e);
            exceptionServBean.notifyError(e, msnError + e.getMessage(), VISOR_DE_FACTIBILIDAD);
        }
    }

    /**
     * Función para validar datos nulos al momento de construir.una dirección
     * tipo Calle-Carrera.
     *
     * @return
     * @author Victor Bocanegra return false si algun dato se encuentra vacio.
     */
    public boolean validarDatosDireccionCk() {
        assignDefaultDetailsToPopup();

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
                if (nombreCentroPoblado == null) {
                    msnError = "Seleccione la CENTRO POBLADO por favor.";
                    alerts();
                    return false;
                } else {
                    if (idCiudad == null) {
                        msnError = "Seleccione la CIUDAD por favor.";
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
     * @return
     * @author Victor Bocanegra return boolean false si algun dato se encuentra
     * vacio
     */
    public boolean validarDatosDireccionBm() {
        assignDefaultDetailsToPopup();

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
                if (ciudadSelected == null) {
                    msnError = "Seleccione la CIUDAD por favor.";
                    alerts();
                    return false;
                } else {
                    if (centroPobladoSelected == null) {
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
     * @return
     * @author Victor Bocanegra return false si algun dato se encuentra vacio
     */
    public boolean validarDatosDireccionIt() {
        assignDefaultDetailsToPopup();

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
                if (ciudadSelected == null) {
                    msnError = "Seleccione la CIUDAD por favor.";
                    alerts();
                    return false;
                } else {
                    if (centroPobladoSelected == null) {
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
     * @param cadena
     * @return
     * @author Victor Bocanegra} return true si el dato es úumerico
     */
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

    /**
     * Función utilizada para construir dirección de tipo calle-carrera
     *
     * @author Victor Bocanegra
     */
    public void construirDireccionCk() throws Exception {
        assignDefaultDetailsToPopup();

        try {
            if (validarDatosDireccionCk()) {

                //DATOS PARA LIMPIAR BARRIO DE MULTIORIGEN
                showBarrio = false;
                barrioSugerido = "";
                barrioSugeridoList = new ArrayList<>();
                barrioSugeridoStr = "";
                barrioSugeridoField = false;

                request.setDireccionStr(direccionCk);
                request.setComunidad(centroPobladoSelected.getGeoCodigoDane());
                drDireccion.setIdTipoDireccion(tipoDireccion);
                drDireccion.setDirPrincAlt("P");
                request.setDrDireccion(drDireccion);
                request.setTipoAdicion("N");
                request.setTipoNivel("N");

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
                    responseConstruirDireccion.setNombreDpto(dptoSelected != null ?
                            dptoSelected.getGpoNombre() : null);
                    responseConstruirDireccion.setNombreCity(ciudadSelected != null ?
                            ciudadSelected.getGpoNombre() : null);
                    responseConstruirDireccion.setNombreCentro(centroPobladoSelected != null ?
                            centroPobladoSelected.getGpoNombre() : null);
                } else {
                    //Dirección que no pudo ser traducida
                    if (responseConstruirDireccion.getDireccionStr() == null
                            || responseConstruirDireccion.getDireccionStr()
                            .isEmpty() && responseConstruirDireccion
                            .getResponseMesaje().getTipoRespuesta()
                            .equalsIgnoreCase("E")) {

                        direccionCk = "";
                        direccionConstruida = false;
                        notGeoReferenciado = false;
                        //Se limpian datos de la dirección tabulada para crearla
                        drDireccion = new DrDireccion();
                        drDireccion.setIdTipoDireccion("CK");
                        createDireccion = "";
                        msnError = "La dirección calle-carrera"
                                + " no pudo ser traducida."
                                + responseConstruirDireccion.getResponseMesaje()
                                .getMensaje();
                        PrimeFaces.current().executeScript("PF('dlg2').show();");
                        alerts();
                    }
                }
            }
        } catch (ApplicationException e) {
            direccionConstruida = false;
            notGeoReferenciado = false;
            String msgError = "Error al construir la dirección calle - carrera ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), VISOR_DE_FACTIBILIDAD);
        }
    }

    /**
     * Función utilizada para construir dirección de tipo Barrio-Manzana
     *
     * @author Victor Bocanegra
     */
    public void construirDireccionBm() {
        assignDefaultDetailsToPopup();

        try {
            if (validarDatosDireccionBm()) {
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelBm);
                request.setValorNivel(nivelValorBm.trim());
                request.setTipoAdicion("P");

                /* Retorna la dirección barrio-manzana traducida. */
                responseConstruirDireccion = drDireccionFacadeLocal.construirDireccionSolicitud(request);
                responseConstruirDireccion.setNombreDpto(dptoSelected.getGpoNombre());
                responseConstruirDireccion.setNombreCity(ciudadSelected.getGpoNombre());
                responseConstruirDireccion.setNombreCentro(centroPobladoSelected.getGpoNombre());

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
            String msgError = "Error al construir la dirección tipo barrio - manzana: ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), VISOR_DE_FACTIBILIDAD);
        }
    }

    /**
     * Función utilizada para construir dirección de tipo Intraducible
     *
     * @author Victor Bocanegra
     */
    public void construirDireccionIt() {
        assignDefaultDetailsToPopup();

        try {
            if (validarDatosDireccionIt()) {
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelIt);
                request.setValorNivel(nivelValorIt.trim());
                request.setTipoAdicion("P");

                // Retorna la dirección intraducible traducida.
                responseConstruirDireccion = drDireccionFacadeLocal.construirDireccionSolicitud(request);
                responseConstruirDireccion.setNombreDpto(dptoSelected.getGpoNombre());
                responseConstruirDireccion.setNombreCity(ciudadSelected.getGpoNombre());
                responseConstruirDireccion.setNombreCentro(centroPobladoSelected.getGpoNombre());

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
            String msgError = "Error al construir la dirección tipo intraducible: ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), VISOR_DE_FACTIBILIDAD);
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
                        direccionLabel = responseConstruirDireccion.getDireccionStr();
                        complemento = "";
                        tipoNivelComplemento = "";
                    }
                }
            }
        } catch (ApplicationException e) {
            String msgError = "Error en construir Dirección Complemento: ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), VISOR_DE_FACTIBILIDAD);
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
                    }
                }
            }

        } catch (ApplicationException e) {
            String msgError = "Error al construir Dirección Apartamento: ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), VISOR_DE_FACTIBILIDAD);
        }
    }

    /**
     * Función para validar datos nulos al momento de construir una dirección
     * con apartamento.
     *
     * @return
     * @author Victor Bocanegra return false si algun dato se encuentra vacio
     */
    public boolean validarDatosDireccionApartamento() {
        assignDefaultDetailsToPopup();

        if (tipoDireccion == null || tipoDireccion.isEmpty()) {
            msnError = "Seleccione el TIPO DE DIRECCIÓN que desea"
                    + " ingresar por favor.";
            alerts();
            return false;
        } else {
            if (ciudadSelected == null) {
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
     * @return
     * @author Victor Bocanegra return false si algun dato se encuentra vacio
     */
    public boolean validarDatosDireccionComplemento() {
        assignDefaultDetailsToPopup();

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
                if (ciudadSelected == null) {
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
     * Funci&oacute;n que obtiene el valor completo de el <b>CENTRO POBLADO</b>
     * seleccionado por el usuario en pantalla.
     *
     * @throws ApplicationException
     */
    public void obtenerCentroPobladoSeleccionado() throws ApplicationException {
        assignDefaultDetailsToPopup();

        if (nombreCentroPoblado != null && !nombreCentroPoblado.isEmpty()) {
            showBarrio = false;
            barrioSugerido = "";
            barrioSugeridoList = new ArrayList<>();
            barrioSugeridoStr = "";
            barrioSugeridoField = false;

            //Consulta el centro poblado
            listCentroPoblados
                    = geograficoPoliticoFacadeLocal.findCentroPobladosByNombre(nombreCentroPoblado);

            if (listCentroPoblados != null && !listCentroPoblados.isEmpty()
                    && listCentroPoblados.size() > 1) {
                ciudadesList = new ArrayList<>();
                ciudadSelected = null;
                dptoSelected = null;

                //Varios Centro poblados con el mismo nombre busco las ciudades por ese nombre
                for (GeograficoPoliticoMgl centros : listCentroPoblados) {
                    GeograficoPoliticoMgl ciudadCon = geograficoPoliticoFacadeLocal.findById(centros.getGeoGpoId());
                    if (ciudadCon != null && ciudadCon.getGpoId() != null) {
                        ciudadesList.add(ciudadCon);
                    }
                }
                habilitaCity = false;

            } else if ((listCentroPoblados != null && !listCentroPoblados.isEmpty()
                    && listCentroPoblados.size() == 1)) {
                ciudadesList = new ArrayList<>();
                habilitaCity = true;
                habilitaDpto = true;
                //Hay un solo centro poblado con ese nombre
                centroPobladoSelected = listCentroPoblados.get(0);
                //Busco la ciudad de ese centro
                ciudadSelected = geograficoPoliticoFacadeLocal.findById(centroPobladoSelected.getGeoGpoId());
                if (ciudadSelected != null && ciudadSelected.getGpoId() != null) {
                    ciudadesList.add(ciudadSelected);
                    idCiudad = ciudadSelected.getGpoId();
                    //Busco el departamento de la  ciudad 
                    dptoSelected = geograficoPoliticoFacadeLocal.findById(ciudadSelected.getGeoGpoId());
                    if (dptoSelected != null && dptoSelected.getGpoId() != null) {
                        departamentoList.add(dptoSelected);
                        idDpto = dptoSelected.getGpoId();
                    }
                }

                if (centroPobladoSelected != null && centroPobladoSelected.getGpoNombre() != null
                        && !centroPobladoSelected.getGpoNombre().isEmpty()
                        && (centroPobladoSelected.getGpoNombre().equalsIgnoreCase("PALMIRA")
                        || centroPobladoSelected.getGpoNombre().equalsIgnoreCase("CANDELARIA"))) {

                    msnError = "Para esta Ciudad, por favor ingrese la "
                            + "dirección por el panel de DIRECCIÓN TABULADA";
                    alerts();
                    notGeoReferenciado = false;
                    PrimeFaces.current().executeScript("PF('dlg2').show();");
                } else {
                    notGeoReferenciado = true;
                }
            }
        } else {
            showBarrio = false;
            barrioSugerido = "";
            barrioSugeridoList = new ArrayList<>();
            barrioSugeridoStr = "";
            barrioSugeridoField = false;
            ciudadesList = new ArrayList<>();
            ciudadSelected = null;
            dptoSelected = null;
            centroPobladoSelected = null;
            idCiudad = null;
            idDpto = null;
            direccionLabel = "";
        }
    }

    /**
     * Obtiene el <b>DEPARTAMENTO</b> por ciudad de la base de datos.
     *
     * @throws ApplicationException
     */
    public void obtenerDepartamento() throws ApplicationException {

        if (ciudadesList != null && !ciudadesList.isEmpty() && idCiudad != null) {

            ciudadesList.stream().filter((ciudad) -> (ciudad.getGpoId().compareTo(idCiudad) == 0)).forEachOrdered((ciudad) -> {
                ciudadSelected = ciudad;
            });
            //Busco el departamento de la  ciudad 
            dptoSelected = geograficoPoliticoFacadeLocal.findById(ciudadSelected.getGeoGpoId());
            if (dptoSelected != null && dptoSelected.getGpoId() != null) {
                departamentoList.add(dptoSelected);
                idDpto = dptoSelected.getGpoId();
            }
            if (listCentroPoblados != null && !listCentroPoblados.isEmpty()) {
                listCentroPoblados.stream().filter((centros) -> (centros.getGeoGpoId().compareTo(ciudadSelected.getGpoId()) == 0)).forEachOrdered((centros) -> {
                    centroPobladoSelected = centros;
                });
            }
        }

    }

    /**
     * Obtiene el listado del nombre de los <b>CENTRO POBLADOS</b> de la base de
     * datos.
     *
     * @throws ApplicationException
     */
    public void obtenerNombresCentroPobladosList() throws ApplicationException {

        nameCentroPobladoList = geograficoPoliticoFacadeLocal.findNamesCentroPoblado();
    }

    /**
     * Funci&oacute;n utilizada para validar si la excepci&oacute;n arrogada por
     * el servicio de validaci&oacute;n es por ser una ciudad multi-origen.
     *
     * @param multiOrigen
     * @author Victor Bocanegra
     */
    public void validarBarrioCiudadMultiOrigen(String multiOrigen) {
        assignDefaultDetailsToPopup();

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

                List<String> barriosMer = cmtDireccionDetalleMglFacadeLocal.
                        findBarriosDireccionTabuladaMer(centroPobladoSelected.getGpoId(), request.getDrDireccion());


                /*Valida que se haya obtenido un listado de barrios sugeridos
                 para desplegarlos en pantalla. */
                if (barrioSugeridoList != null && !barrioSugeridoList.isEmpty()) {
                    msnError = "La ciudad es MultiOrigen, es necesario"
                            + " seleccionar un barrio sugerido y buscar "
                            + "nuevamente";
                    alerts();

                    List<String> barriosSug = new ArrayList<>();

                    for (CmtAddressSuggestedDto cmtAddressSuggestedDto : barrioSugeridoList) {
                        barriosSug.add(cmtAddressSuggestedDto.getNeighborhood());
                    }

                    if (barriosMer != null) {
                        barriosMer
                                = Stream.concat(barriosMer.stream(), barriosSug.stream())
                                .distinct()
                                .collect(Collectors.toList());

                        barrioSugeridoList = new ArrayList<>();
                        for (String barrio : barriosMer) {
                            if (barrio != null && !barrio.isEmpty()) {
                                CmtAddressSuggestedDto barrioMer = new CmtAddressSuggestedDto();
                                barrioMer.setAddress(request.getDireccionStr());
                                barrioMer.setNeighborhood(barrio.trim());
                                barrioSugeridoList.add(barrioMer);
                            }
                        }
                    }

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
                    barrioSugeridoList = new ArrayList<>();
                    CmtAddressSuggestedDto otro = new CmtAddressSuggestedDto();
                    otro.setAddress("Otro");
                    otro.setNeighborhood("Otro");
                    barrioSugeridoList.add(otro);
                    showBarrio = true;
                }
            }
        } catch (Exception e) {
            msnError = "Error al validar barrio de la dirección: ";
            exceptionServBean.notifyError(e, msnError + e.getMessage(), VISOR_DE_FACTIBILIDAD);
        }
    }

    /**
     * Valida estructura de una direccion.
     *
     * @param direccion        objeto con la informacion de la direccion a validar.
     * @param tipoValidacionCM
     * @return true si la direccion contiene lo minimo de la validacion
     * direccion.
     * @author Vitor Bocanegra
     */
    public boolean validarEstructuraDireccion(DrDireccion direccion, String tipoValidacionCM) throws Exception {
        assignDefaultDetailsToPopup();

        try {
            if (direccion == null) {
                throw new Exception("No es posible realizar la validación por"
                        + " datos incompletos en la construcción de la dirección. ");
            }

            if (direccion.getIdTipoDireccion() == null) {
                throw new Exception("No es posible realizar la validación por falta de datos,"
                        + " no se puede identificar si la dirección es CK, BM o IT ");
            }

            /*Valida por tipo de dirección que contenga el minino de elementros 
             ingresados a la construcción de la dirección */
            if (direccion.getIdTipoDireccion() != null && !direccion.getIdTipoDireccion().isEmpty()
                    && direccion.getIdTipoDireccion().equalsIgnoreCase("CK")) {

                if (direccion.getTipoViaPrincipal() == null
                        || direccion.getTipoViaPrincipal().trim().isEmpty()) {
                    msnError = "Es necesario ingresar el TIPO VIA PRINCIPAL";
                    alerts();
                    throw new Exception("Es necesario ingresar el "
                            + "TIPO VIA PRINCIPAL. ");
                }
                if (direccion.getNumViaPrincipal() == null
                        || direccion.getNumViaPrincipal().trim().isEmpty()) {
                    msnError = "Es necesario ingresar la NUM VIA PRINCIPAL.";
                    alerts();
                    throw new Exception("Es necesario ingresar la "
                            + "NUM VIA PRINCIPAL. ");
                }
                if (direccion.getNumViaGeneradora() == null
                        || direccion.getNumViaGeneradora().trim().isEmpty()) {
                    msnError = "Es necesario ingresar la NUM VIA GENERADORA (Núm)";
                    alerts();
                    throw new Exception("Es necesario ingresar la "
                            + "NUM VIA GENERADORA (Núm)");
                }
                if (direccion.getPlacaDireccion() == null
                        || direccion.getPlacaDireccion().trim().isEmpty()) {
                    msnError = "Es necesario ingresar el N°PLACA";
                    alerts();
                    throw new Exception("Es necesario ingresar el N° PLACA");
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
     * @return
     * @throws Exception
     * @author Victor Bocanegra
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
            String msgError = "Error en pageFirst, redireccionando a primera pagina: " + ex.getMessage();
            exceptionServBean.notifyError(ex, msgError, VISOR_DE_FACTIBILIDAD);
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
            String msgError = "Error en pagePrevious, direccionando a la página anterior: " + ex.getMessage();
            exceptionServBean.notifyError(ex, msgError, VISOR_DE_FACTIBILIDAD);
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
            String msgError = "Error en irPagina: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, VISOR_DE_FACTIBILIDAD);
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
            String msgError ="Error en pageNext, direccionando a la siguiente página: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, VISOR_DE_FACTIBILIDAD);
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
            String msgError ="Error en pageLast, direccionando a la última página: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, VISOR_DE_FACTIBILIDAD);
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     *
     * @return
     * @author Victor Bocanegra
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
            error("Error en getTotalPaginas, redireccionand página: " + e.getMessage() + "");
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario en
     * los resultados.
     *
     * @return
     * @author Victor Bocanegra
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
     * @return
     * @throws Exception
     * @author Victor Bocanegra
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
            if (detalleFactibilidadMglsAux != null && !detalleFactibilidadMglsAux.isEmpty()) {

                int maxResult;
                if ((firstResult + filasPag5) > detalleFactibilidadMglsAux.size()) {
                    maxResult = detalleFactibilidadMglsAux.size();
                } else {
                    maxResult = (firstResult + filasPag5);
                }

                detalleFactibilidadMgls = new ArrayList<>();
                for (int k = firstResult; k < maxResult; k++) {
                    detalleFactibilidadMgls.add(detalleFactibilidadMglsAux.get(k));
                }
            }

        } catch (Exception e) {
            String msgError = "Error en listInfoByPageCob: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, VISOR_DE_FACTIBILIDAD);
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
            error("Error en pageFirstCob, redireccionando a primera pagina: " + ex.getMessage() + "");
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
            error("Error en pagePreviousCob, direccionando a la página anterior: " + ex.getMessage() + "");
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
            error("Error en irPaginaCob: " + e.getMessage() + "");
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
            error("Error en pageNextCob, direccionando a la siguiente página: " + e.getMessage() + "");
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
            error("Error en pageLastCob, direccionando a la última página: " + e.getMessage() + " ");
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     *
     * @return
     * @author Victor Bocanegra
     */
    public int getTotalPaginasCob() {
        try {
            int totalPaginas = 0;

            if (detalleFactibilidadMglsAux != null && !detalleFactibilidadMglsAux.isEmpty()) {
                int pageSol = detalleFactibilidadMglsAux.size();

                totalPaginas = ((pageSol % filasPag5 != 0)
                        ? (pageSol / filasPag5) + 1 : pageSol / filasPag);

            }
            return totalPaginas;
        } catch (Exception e) {
            error("Error en getTotalPaginasCob, redireccionand página: " + e.getMessage() + "");
        }
        return 1;
    }

    /**
     * Función que da a conocer la página actual en la que se encuentra el
     * usuario en los resultados.
     *
     * @return
     * @author Victor Bocanegra
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
                    ? !drDireccion.getNumViaPrincipal().isEmpty() ? drDireccion.getNumViaPrincipal() + " " : "" : "";
            createDireccion += drDireccion.getLtViaPrincipal() != null
                    ? !drDireccion.getLtViaPrincipal().isEmpty() ? drDireccion.getLtViaPrincipal() + " " : "" : "";
            createDireccion += drDireccion.getNlPostViaP() != null
                    ? !drDireccion.getNlPostViaP().isEmpty() ? drDireccion.getNlPostViaP() + " " : "" : "";
            createDireccion += drDireccion.getBisViaPrincipal() != null
                    ? !drDireccion.getBisViaPrincipal().isEmpty() ? " - " + drDireccion.getBisViaPrincipal() + " " : "" : "";
            createDireccion += drDireccion.getCuadViaPrincipal() != null
                    ? !drDireccion.getCuadViaPrincipal().isEmpty() ? drDireccion.getCuadViaPrincipal() + " " : "" : "";
            createDireccion += drDireccion.getTipoViaGeneradora() != null
                    ? !drDireccion.getTipoViaGeneradora().isEmpty() ? drDireccion.getTipoViaGeneradora() + " " : "" : "";
            createDireccion += drDireccion.getNumViaGeneradora() != null
                    ? !drDireccion.getNumViaGeneradora().isEmpty() ? " # " + drDireccion.getNumViaGeneradora() + " " : "" : "";
            createDireccion += drDireccion.getLtViaGeneradora() != null
                    ? !drDireccion.getLtViaGeneradora().isEmpty() ? drDireccion.getLtViaGeneradora() + " " : "" : "";
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
                    ? !drDireccion.getCuadViaGeneradora().isEmpty() ? drDireccion.getCuadViaGeneradora() + " " : "" : "";
            createDireccion += drDireccion.getPlacaDireccion() != null
                    ? !drDireccion.getPlacaDireccion().isEmpty() ? Integer.parseInt(drDireccion.getPlacaDireccion()) + " " : "" : "";

            if (drDireccion.getPlacaDireccion() != null && !drDireccion.getPlacaDireccion().isEmpty()) {
                int placaSinCeros = Integer.parseInt(drDireccion.getPlacaDireccion());
                drDireccion.setPlacaDireccion(String.valueOf(placaSinCeros));
            }
            direccionConstruida = true;
            direccionLabel = createDireccion;

        } catch (NumberFormatException e) {
            String msgError ="Error en buildDireccion: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, VISOR_DE_FACTIBILIDAD);
        }
    }

    /**
     * M&eacute;todo para consultar las direcciones.
     *
     * @return Listado de CmtDireccionDetalladaMglDto.
     * @throws ApplicationException
     */
    private List<CmtDireccionDetalladaMglDto> direccionesDetalladasConsulta()
            throws ApplicationException, Exception {

        CmtDireccionDetalladaMglDto direccionDetalladaMglDtosPar;
        List<CmtDireccionDetalladaMglDto> direccionDetalladaMglDtos = new ArrayList<>();

        // Realiza la busqueda normal de una direccion completa, construida correctamente
        List<CmtDireccionDetalladaMgl> direccionDetalladaPar;

        for (Map.Entry<ResponseConstruccionDireccion, BigDecimal> n : paramsConsulta.entrySet()) {

            if (n.getKey().getDetalladaMgl() != null) {
                direccionDetalladaPar = new ArrayList<>();
                direccionDetalladaPar.add(n.getKey().getDetalladaMgl());
            } else {
                direccionDetalladaPar = cmtDireccionDetalleMglFacadeLocal
                        .buscarDireccionTabuladaUnica(n.getValue(), n.getKey().getDrDireccion());
            }

            if (direccionDetalladaPar != null && !direccionDetalladaPar.isEmpty()) {
                DireccionMgl dir = direccionDetalladaPar.get(0).getDireccion();
                // Convertir el listado de entidades en listado de DTOs para la visualizacion:
                direccionDetalladaMglDtosPar
                        = this.convertirDireccionDetalladaMglADto(direccionDetalladaPar);

                if (dir != null) {
                    direccionDetalladaMglDtosPar.setDireccionMgl(dir);
                    SubDireccionMgl subDir = direccionDetalladaPar.get(0).getSubDireccion();
                    direccionDetalladaMglDtosPar.setSubDireccionMgl(subDir);
                    UbicacionMgl ubicacionMgl = dir.getUbicacion() != null
                            ? dir.getUbicacion() : null;
                    GeograficoPoliticoMgl centro;
                    if (ubicacionMgl != null) {
                        direccionDetalladaMglDtosPar.setLatitudDir(ubicacionMgl.getUbiLatitud());
                        direccionDetalladaMglDtosPar.setLongitudDir(ubicacionMgl.getUbiLongitud());
                        centro = ubicacionMgl.getGpoIdObj();
                        if (centro != null) {
                            direccionDetalladaMglDtosPar.setCentroPoblado(centro.getGpoNombre());
                            GeograficoPoliticoMgl ciudad = geograficoPoliticoFacadeLocal.findById(centro.getGeoGpoId());
                            if (ciudad != null) {
                                direccionDetalladaMglDtosPar.setCiudad(ciudad.getGpoNombre());
                                GeograficoPoliticoMgl dpto = geograficoPoliticoFacadeLocal.findById(ciudad.getGeoGpoId());
                                if (dpto != null) {
                                    direccionDetalladaMglDtosPar.setDepartamento(dpto.getGpoNombre());
                                }
                            }

                        }
                    }
                    direccionDetalladaMglDtosPar.setConfiabilidad(dir.getDirConfiabilidad() != null ? dir.getDirConfiabilidad() : BigDecimal.ONE);
                    //Busqueda cmt Direccion
                    CmtDireccionMgl direccionCm = cmtDireccionMglFacadeLocal.
                            findCmtIdDireccion(dir.getDirId());
                    if (direccionCm != null && direccionCm.getCuentaMatrizObj() != null) {
                        CmtCuentaMatrizMgl cuenta = direccionCm.getCuentaMatrizObj();
                        direccionDetalladaMglDtosPar.setTipoDireccion("CCMM");
                        direccionDetalladaMglDtosPar.setCuentaMatrizMgl(cuenta);
                    } else {
                        //JDHT  
                        /*este ajuste se realiza debido a que no siempre el mismo id_direccion de un hhpp esta asociado direccion_id de una CCMM
                        de la tabla cmt_direccion por tal motivo se utiliza la relación que tienen de la columna sub_edificio_id los hhpp */

                        //DE MOMENTO ES UNIDAD PERO SI ENCUENTRA CCMM SOBREESCRIBE EL VALOR Y QUERDA EN CCMM
                        direccionDetalladaMglDtosPar.setTipoDireccion("UNIDAD");

                        //Obtenemos los Hhpp si es Subdireccion  
                        if (direccionDetalladaMglDtosPar.getSubDireccionMgl() != null
                                && direccionDetalladaMglDtosPar.getSubDireccionMgl().getSdiId() != null) {

                            List<HhppMgl> hhhpSubDirList = hhppMglFacadeLocal
                                    .findHhppSubDireccion(direccionDetalladaMglDtosPar.getSubDireccionMgl().getSdiId());

                            if (hhhpSubDirList != null && !hhhpSubDirList.isEmpty()) {
                                if (hhhpSubDirList.get(0).getEstadoRegistro() == 1) {
                                    //Obtener numero de cuenta matriz si tiene asociada
                                    if (hhhpSubDirList.get(0).getHhppSubEdificioObj() != null && hhhpSubDirList.get(0).getHhppSubEdificioObj()
                                            .getCmtCuentaMatrizMglObj() != null) {
                                        //Asignamos la cuenta matriz obtenida del hhpp con subdireccion                                  
                                        direccionDetalladaMglDtosPar.setTipoDireccion("CCMM");
                                        direccionDetalladaMglDtosPar.setCuentaMatrizMgl(hhhpSubDirList.get(0).getHhppSubEdificioObj().getCmtCuentaMatrizMglObj());
                                    }
                                }
                            }
                        } else {
                            //Obtenemos los Hhpp si es Direccion principal    
                            if (direccionDetalladaMglDtosPar.getDireccionMgl() != null
                                    && direccionDetalladaMglDtosPar.getDireccionMgl().getDirId() != null) {

                                List<HhppMgl> hhhpDirList
                                        = hhppMglFacadeLocal
                                        .findHhppDireccion(direccionDetalladaMglDtosPar.getDireccionMgl().getDirId());

                                if (hhhpDirList != null && !hhhpDirList.isEmpty()) {
                                    if (hhhpDirList.get(0).getEstadoRegistro() == 1) {
                                        //Obtener numero de cuenta matriz si tiene asociada
                                        if (hhhpDirList.get(0).getHhppSubEdificioObj() != null && hhhpDirList.get(0).getHhppSubEdificioObj()
                                                .getCmtCuentaMatrizMglObj() != null) {
                                            //Asignamos la cuenta matriz obtenida del hhpp con subdireccion                                  
                                            direccionDetalladaMglDtosPar.setTipoDireccion("CCMM");
                                            direccionDetalladaMglDtosPar.setCuentaMatrizMgl(hhhpDirList.get(0).getHhppSubEdificioObj().getCmtCuentaMatrizMglObj());

                                        }
                                    }
                                }
                            }
                        }

                    }
                }

                //Busco si la direccion tiene una factibilidad  vigente List
                List<FactibilidadMgl> factibilidadVig
                        = factibilidadMglFacadeLocal.findFactibilidadVigByDetallada(direccionDetalladaMglDtosPar.getDireccionDetalladaId(), new Date());

                if (factibilidadVig != null && !factibilidadVig.isEmpty()) {
                    FactibilidadMgl factibilidadMgl = factibilidadVig.get(0);
                    direccionDetalladaMglDtosPar.setFactibilidadId(factibilidadMgl.getFactibilidadId());
                }
                //Fin Busqueda si la direccion tiene una factibilidad  vigente

                if (direccionDetalladaMglDtosPar.getLatitudDir() != null
                        && direccionDetalladaMglDtosPar.getLongitudDir() != null) {
                    direccionDetalladaMglDtos.add(direccionDetalladaMglDtosPar);
                }
            } else {

                //Creamos la direccion
                GeograficoPoliticoMgl geograficoPolitico = geograficoPoliticoFacadeLocal.findById(n.getValue());
                if (geograficoPolitico != null) {
                    direccionDetalladaPar = crearDireccionConsultada(n.getKey().getDrDireccion(), geograficoPolitico);
                }
                if (direccionDetalladaPar != null && !direccionDetalladaPar.isEmpty()) {
                    DireccionMgl dir = direccionDetalladaPar.get(0).getDireccion();
                    // Convertir el listado de entidades en listado de DTOs para la visualizacion:
                    direccionDetalladaMglDtosPar
                            = this.convertirDireccionDetalladaMglADto(direccionDetalladaPar);
                    if (dir != null) {
                        direccionDetalladaMglDtosPar.setDireccionMgl(dir);
                        SubDireccionMgl subDir = direccionDetalladaPar.get(0).getSubDireccion();
                        direccionDetalladaMglDtosPar.setSubDireccionMgl(subDir);
                        UbicacionMgl ubicacionMgl = dir.getUbicacion() != null
                                ? dir.getUbicacion() : null;
                        GeograficoPoliticoMgl centro;
                        if (ubicacionMgl != null) {
                            direccionDetalladaMglDtosPar.setLatitudDir(ubicacionMgl.getUbiLatitud());
                            direccionDetalladaMglDtosPar.setLongitudDir(ubicacionMgl.getUbiLongitud());
                            centro = ubicacionMgl.getGpoIdObj();
                            if (centro != null) {
                                direccionDetalladaMglDtosPar.setCentroPoblado(centro.getGpoNombre());
                                GeograficoPoliticoMgl ciudad = geograficoPoliticoFacadeLocal.findById(centro.getGeoGpoId());
                                if (ciudad != null) {
                                    direccionDetalladaMglDtosPar.setCiudad(ciudad.getGpoNombre());
                                    GeograficoPoliticoMgl dpto = geograficoPoliticoFacadeLocal.findById(ciudad.getGeoGpoId());
                                    if (dpto != null) {
                                        direccionDetalladaMglDtosPar.setDepartamento(dpto.getGpoNombre());
                                    }
                                }

                            }
                        }
                        direccionDetalladaMglDtosPar.setConfiabilidad(dir.getDirConfiabilidad() != null ? dir.getDirConfiabilidad() : BigDecimal.ONE);
                        //Busqueda cmt Direccion
                        //siempre es UNIDAD porque se acaba de crear
                        direccionDetalladaMglDtosPar.setTipoDireccion("UNIDAD");

                    }
                    //Busqueda cmt Direccion

                    //Busco si la direccion tiene una factibilidad  vigente List
                    List<FactibilidadMgl> factibilidadVig
                            = factibilidadMglFacadeLocal.findFactibilidadVigByDetallada(direccionDetalladaMglDtosPar.getDireccionDetalladaId(), new Date());

                    if (factibilidadVig != null && !factibilidadVig.isEmpty()) {
                        FactibilidadMgl factibilidadMgl = factibilidadVig.get(0);
                        direccionDetalladaMglDtosPar.setFactibilidadId(factibilidadMgl.getFactibilidadId());
                    }
                    //Fin Busqueda si la direccion tiene una factibilidad  vigente

                    if (direccionDetalladaMglDtosPar.getLatitudDir() != null
                            && direccionDetalladaMglDtosPar.getLongitudDir() != null) {
                        direccionDetalladaMglDtos.add(direccionDetalladaMglDtosPar);
                    }
                }
            }
        }

        return direccionDetalladaMglDtos;
    }

    /**
     * Realiza la conversi&oacute;n de listado de Entidad a listado de DTO.
     *
     * @param listaCmtDireccionDetalladaMgl Listado de entidad
     * CmtDireccionDetalladaMgl.
     * @return Listado de CmtDireccionDetalladaMglDto.
     * @throws ApplicationException
     */
    private CmtDireccionDetalladaMglDto convertirDireccionDetalladaMglADto(List<CmtDireccionDetalladaMgl> listaCmtDireccionDetalladaMgl)
            throws ApplicationException {
        CmtDireccionDetalladaMglDto result = null;

        if (listaCmtDireccionDetalladaMgl != null && !listaCmtDireccionDetalladaMgl.isEmpty()) {
            result = new CmtDireccionDetalladaMglDto();
            for (CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl : listaCmtDireccionDetalladaMgl) {
                CmtDireccionDetalladaMglDto cmtDireccionDetalladaMglDto = cmtDireccionDetalladaMgl.convertirADto();
                if (cmtDireccionDetalladaMglDto != null) {
                    result = cmtDireccionDetalladaMglDto;
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
     *                                     Detallada.
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
     * Victor Bocanegra Metodo para setear DrDireccion a CmtDireccionTabuladaDto
     *
     * @param addressGeneralDtos
     * @return
     */
    public List<CmtDireccionDetalladaMglDto> returnCmtDireccionDetalladaMglDto(List<CmtAddressGeneralDto> addressGeneralDtos) {

        List<CmtDireccionDetalladaMglDto> direccionDetalladaMglDtosLst = new ArrayList<>();

        if (addressGeneralDtos != null && !addressGeneralDtos.isEmpty()) {
            for (CmtAddressGeneralDto addressGeneralDto : addressGeneralDtos) {

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
                direccionDetalladaMglDtosLst.add(direccionDetalladaMglDto);
            }

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
        this.detalleFactibilidadMgls = new ArrayList<>();
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
        this.detalleFactibilidadMgls = new ArrayList<>();
        this.detalleFactibilidadMglSelected = null;
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
     * Realiza la consulta de una Direcci&oacute;n.
     *
     * @param direccionDetalladaMglDto
     * @throws MalformedURLException
     * @throws Exception
     */
    public CmtDireccionDetalladaMglDto consultarDireccionLongitudLatitud(CmtDireccionDetalladaMglDto direccionDetalladaMglDto)
            throws MalformedURLException, Exception {
        assignDefaultDetailsToPopup();
        // Reiniciar las coberturas:
        this.listCover = new ArrayList<>();
        this.detalleFactibilidadMgls = new ArrayList<>();

        if (direccionDetalladaMglDto != null && direccionDetalladaMglDto.getDireccionDetalladaId() != null) {
            direccionDetalladaMglDtoSel = direccionDetalladaMglDto;

            CmtDireccionDetalladaRequestDto request1 = new CmtDireccionDetalladaRequestDto();
            request1.setIdDireccion(direccionDetalladaMglDto.getDireccionDetalladaId());
            request1.setSegmento(Constants.SEGMENTO_CONSULTA);
            request1.setProyecto(Constants.PROYECTO_CONSULTA);

            CmtAddressResponseDto response = cmtDireccionDetalleMglFacadeLocal.consultarDireccion(request1);

            // Convertir objeto a JSON:
            JSONObject jsonObj = this.objectToJSONObject(response);

            if (jsonObj.length() > 0) {
                String typeMen = jsonObj.getString("messageType");
                String mensaje = jsonObj.getString("message");

                if (typeMen.equalsIgnoreCase("I")) {
                    JSONArray listCob = null;
                    JSONObject addresses = jsonObj.getJSONObject("addresses");
                    if (addresses != null) {
                        try {
                            JSONObject city = addresses.getJSONObject("city");
                            if (city != null) {
                                String nameCentro = city.getString("name");
                                direccionDetalladaMglDto.setCentroPoblado(nameCentro);
                                JSONObject ciudad = city.getJSONObject("uperGeographycLevel");
                                if (ciudad != null) {
                                    String nameCiudad = ciudad.getString("name");
                                    direccionDetalladaMglDto.setCiudad(nameCiudad);
                                    JSONObject dpto = ciudad.getJSONObject("uperGeographycLevel");
                                    if (dpto != null) {
                                        String namepto = dpto.getString("name");
                                        direccionDetalladaMglDto.setDepartamento(namepto);
                                    }
                                }

                            }
                        } catch (JSONException e) {
                            LOGGER.warn(e.getMessage());
                        }
                        try {
                            latitud = addresses.getString("latitudeCoordinate");
                            direccionDetalladaMglDto.setLatitudDir(latitud);
                        } catch (JSONException e) {
                            LOGGER.warn(e.getMessage());
                            latitud = null;
                        }

                        try {
                            longitud = addresses.getString("lengthCoordinate");
                            direccionDetalladaMglDto.setLongitudDir(longitud);
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

                        hhppMglsCov = new ArrayList<>();

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
                        direccionDetalladaMglDto.setListCover(listCover);
                    }
                    //Busco hhpp de la direccion
                    DireccionMgl direccionMgl = new DireccionMgl();
                    direccionMgl.setDirId(direccionDetalladaMglDto.getDireccionId());
                    SubDireccionMgl subDireccionMgl = new SubDireccionMgl();
                    if (direccionDetalladaMglDto.getSubdireccionId() != null) {
                        subDireccionMgl.setSdiId(direccionDetalladaMglDto.getSubdireccionId());
                    } else {
                        subDireccionMgl = null;
                    }

                    List<HhppMgl> listadoTecHab
                            = hhppMglFacadeLocal.findByDirAndSubDir(direccionMgl, subDireccionMgl);

                    if (listadoTecHab != null && !listadoTecHab.isEmpty()) {
                        for (HhppMgl hhppMgl : listadoTecHab) {
                            if (!hhppMglsCov.isEmpty()) {
                                for (HhppMgl hhppMglCov : hhppMglsCov) {
                                    if (hhppMglCov.getHhpId().compareTo(hhppMglCov.getHhpId()) == 0) {
                                        LOGGER.info("Hhpp iguales no lo agrego a la lista");
                                    } else {
                                        CmtCoverDto coverDto = new CmtCoverDto();
                                        coverDto.setHhppExistente(true);
                                        coverDto.setIsCobertura(false);
                                        NodoMgl nodo = hhppMgl.getNodId();
                                        CmtBasicaMgl tecnologia = nodo != null ? nodo.getNodTipo() : null;
                                        CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;

                                        coverDto.setTechnology(tecnologia != null ? tecnologia.getCodigoBasica() : "NA");
                                        coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                                        coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                                        coverDto.setHhppMgl(hhppMgl);
                                        listCover.add(coverDto);
                                    }
                                }
                            } else {
                                CmtCoverDto coverDto = new CmtCoverDto();
                                coverDto.setHhppExistente(true);
                                coverDto.setIsCobertura(false);
                                NodoMgl nodo = hhppMgl.getNodId();
                                CmtBasicaMgl tecnologia = nodo != null ? nodo.getNodTipo() : null;
                                CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;

                                coverDto.setTechnology(tecnologia != null ? tecnologia.getCodigoBasica() : "NA");
                                coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                                coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                                coverDto.setHhppMgl(hhppMgl);
                                listCover.add(coverDto);

                            }
                        }
                        direccionDetalladaMglDto.setListCover(listCover);
                        direccionDetalladaMglDto.setLstHhppMgl(listadoTecHab);

                        //Fin Busqueda hhpp de la direccion
                    }
                    if (direccionMgl.getDirId() != null) {
                        CmtDireccionMgl direccionCm = cmtDireccionMglFacadeLocal.
                                findCmtIdDireccion(direccionMgl.getDirId());
                        if (direccionCm != null && direccionCm.getCuentaMatrizObj() != null) {
                            CmtCuentaMatrizMgl cuenta = direccionCm.getCuentaMatrizObj();
                            direccionDetalladaMglDto.setTipoDireccion("CCMM");
                            direccionDetalladaMglDto.setCuentaMatrizMgl(cuenta);
                        } else {
                            direccionDetalladaMglDto.setTipoDireccion("UNIDAD");
                        }
                    }
                    //Busco si la direccion tiene una factibilidad  vigente
                    List<FactibilidadMgl> factibilidadVig
                            = factibilidadMglFacadeLocal.findFactibilidadVigByDetallada(direccionDetalladaMglDto.getDireccionDetalladaId(), new Date());

                    if (factibilidadVig != null && !factibilidadVig.isEmpty()) {
                        FactibilidadMgl factibilidadMgl = factibilidadVig.get(0);
                        direccionDetalladaMglDto.setFactibilidadId(factibilidadMgl.getFactibilidadId());
                    }
                    //Fin Busqueda si la direccion tiene una factibilidad  vigente
                } else {
                    msnError = mensaje;
                    alerts();
                }
            } else {
                msnError = "No hay informacion de la direccion";
                alerts();
            }
        }
        return direccionDetalladaMglDto;
    }

    public CmtDireccionDetalladaMglDto consultarCoberturas(CmtDireccionDetalladaMglDto direccionDetalladaMglDto) throws ApplicationException {

        List<CmtCoverDto> coberturas = null;
        listCover = new ArrayList<>();

        if (direccionDetalladaMglDto.getDireccionMgl() != null) {
            /* Brief 98062 */
            DrDireccion direccionForNodoSiti = new DrDireccion();
            direccionForNodoSiti = drDireccion;
            cmtDireccionDetalleMglFacadeLocal.setDireccionConsultaNodoSitiData(direccionForNodoSiti);
            /* Cierre Brief 98062 */
            //Consulta las coberturas para la dirección

            coberturas = cmtDireccionDetalleMglFacadeLocal.
                    coberturasDireccion(direccionDetalladaMglDto.getDireccionMgl(),
                            direccionDetalladaMglDto.getSubDireccionMgl());
        }

        if (coberturas != null && !coberturas.isEmpty()) {

            for (CmtCoverDto coverDto : coberturas) {

                if (coverDto.getNode() != null && !coverDto.getNode().isEmpty()) {
                    // Realiza la busqueda del Nodo por Codigo.
                    NodoMgl nodoMgl = mapNodos.get(coverDto.getNode());
                    if (nodoMgl == null) {
                        nodoMgl = nodoMglFacadeLocal.findByCodigo(coverDto.getNode());
                        if (nodoMgl != null) {
                            mapNodos.put(coverDto.getNode(), nodoMgl);
                        }
                    }
                }
                listCover.add(coverDto);
            }

            direccionDetalladaMglDto.setListCover(listCover);
        }

        if (direccionDetalladaMglDto.getTipoDireccion().equalsIgnoreCase("UNIDAD")) {
            //Busco hhpp de la direccion
            DireccionMgl direccionMgl = new DireccionMgl();
            direccionMgl.setDirId(direccionDetalladaMglDto.getDireccionId());
            SubDireccionMgl subDireccionMgl = new SubDireccionMgl();
            if (direccionDetalladaMglDto.getSubdireccionId() != null) {
                subDireccionMgl.setSdiId(direccionDetalladaMglDto.getSubdireccionId());
            } else {
                subDireccionMgl = null;
            }
            List<HhppMgl> listadoTecHab
                    = hhppMglFacadeLocal.findByDirAndSubDir(direccionMgl, subDireccionMgl);

            if (listadoTecHab != null && !listadoTecHab.isEmpty()) {

                CmtTipoBasicaMgl cmtTipoBasicaTec = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_TECNOLOGIA);

                CmtTipoBasicaExtMgl tipoBasicaExtMglVC = null;

                if (cmtTipoBasicaTec != null
                        && !cmtTipoBasicaTec.getListCmtTipoBasicaExtMgl().isEmpty()) {
                    for (CmtTipoBasicaExtMgl cmtTipoBasicaExtMgl
                            : cmtTipoBasicaTec.getListCmtTipoBasicaExtMgl()) {
                        if (cmtTipoBasicaExtMgl.getCampoEntidadAs400().equalsIgnoreCase(Constant.TECNOLOGIA_MANEJA_NODOS)) {
                            tipoBasicaExtMglVC = cmtTipoBasicaExtMgl;
                        }
                    }

                }

                for (HhppMgl hhppMgl : listadoTecHab) {
                    if (listCover != null && !listCover.isEmpty()) {
                        int control = 0;
                        for (CmtCoverDto coverDtoCon : new ArrayList<>(listCover)) {

                            NodoMgl nodo = hhppMgl.getNodId();
                            CmtBasicaMgl tecnologia2 = nodo != null ? nodo.getNodTipo() : null;
                            CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;
                            String nodoSitiAux; // Brief 98062

                            if (tecnologia2 != null) {
                                if ((coverDtoCon.getTechnology().equalsIgnoreCase(tecnologia2.getCodigoBasica()))) {

                                    LOGGER.info("tecnologias iguales  agrego a la lista el HHPP");
                                    nodoSitiAux = coverDtoCon.getNodoSitiData();//Retiene la información del nodo de SITIDATA
                                    listCover.remove(coverDtoCon);
                                    CmtCoverDto coverDto = new CmtCoverDto();
                                    coverDto.setHhppExistente(true);
                                    coverDto.setTechnology(tecnologia2.getCodigoBasica());
                                    coverDto.setColorTecno(retornaColorTecno(coverDto.getTechnology()));
                                    coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                                    coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                                    coverDto.setHhppMgl(hhppMgl);
                                    coverDto.setValidaCobertura(true);
                                    coverDto.setNodoSitiData(nodoSitiAux);//asigna el nodo retenido de SITIDATA en la cobertura
                                    listCover.add(coverDto);
                                    control++;
                                }

                            }

                        }
                        if (control == 0) {
                            NodoMgl nodo = hhppMgl.getNodId();
                            CmtBasicaMgl tecnologia2 = nodo != null ? nodo.getNodTipo() : null;
                            CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;
                            if (tecnologia2 != null) {
                                if (tecnologiaValidaCobertura(tecnologia2, tipoBasicaExtMglVC)) {
                                    CmtCoverDto coverDto = new CmtCoverDto();

                                    coverDto.setHhppExistente(true);
                                    coverDto.setTechnology(tecnologia2.getCodigoBasica());
                                    coverDto.setColorTecno(retornaColorTecno(coverDto.getTechnology()));
                                    coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                                    coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                                    coverDto.setHhppMgl(hhppMgl);
                                    coverDto.setValidaCobertura(true);
                                    listCover.add(coverDto);
                                }
                            }
                        }

                    } else {
                        NodoMgl nodo = hhppMgl.getNodId();
                        CmtBasicaMgl tecnologia2 = nodo != null ? nodo.getNodTipo() : null;
                        CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;

                        if (tecnologiaValidaCobertura(tecnologia2, tipoBasicaExtMglVC)) {

                            CmtCoverDto coverDto = new CmtCoverDto();
                            coverDto.setHhppExistente(true);
                            coverDto.setTechnology(tecnologia2 != null ? tecnologia2.getCodigoBasica() : "NA");
                            coverDto.setColorTecno(retornaColorTecno(coverDto.getTechnology()));
                            coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                            coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                            coverDto.setHhppMgl(hhppMgl);
                            coverDto.setValidaCobertura(true);
                            listCover.add(coverDto);
                        }
                    }
                }
                direccionDetalladaMglDto.setListCover(listCover);
                direccionDetalladaMglDto.setLstHhppMgl(listadoTecHab);
                //Fin Busqueda hhpp de la direccion
            }
        } else if (direccionDetalladaMglDto.getTipoDireccion().equalsIgnoreCase("CCMM")) {
            //Busco tecnologias sub de la ccmm  
            if (direccionDetalladaMglDto.getCuentaMatrizMgl() != null) {
                List<CmtTecnologiaSubMgl> tecnologiasCcmm = tecnologiaSubMglFacadeLocal.
                        findTecnoSubBySubEdi(direccionDetalladaMglDto.getCuentaMatrizMgl().getSubEdificioGeneral());

                if (tecnologiasCcmm != null && !tecnologiasCcmm.isEmpty()) {

                    CmtTipoBasicaMgl cmtTipoBasicaTec = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                            Constant.TIPO_BASICA_TECNOLOGIA);
                    CmtTipoBasicaExtMgl tipoBasicaExtMglVC = null;

                    if (cmtTipoBasicaTec != null
                            && !cmtTipoBasicaTec.getListCmtTipoBasicaExtMgl().isEmpty()) {
                        for (CmtTipoBasicaExtMgl cmtTipoBasicaExtMgl
                                : cmtTipoBasicaTec.getListCmtTipoBasicaExtMgl()) {
                            if (cmtTipoBasicaExtMgl.getCampoEntidadAs400().equalsIgnoreCase(Constant.TECNOLOGIA_MANEJA_NODOS)) {
                                tipoBasicaExtMglVC = cmtTipoBasicaExtMgl;
                            }
                        }

                    }

                    for (CmtTecnologiaSubMgl tecno : tecnologiasCcmm) {
                        if (listCover != null && !listCover.isEmpty()) {
                            int control = 0;
                            for (CmtCoverDto coverDtoCon : new ArrayList<>(listCover)) {
                                String nodoSitiAux; //brief 98062
                                if (tecno != null) {
                                    if ((coverDtoCon.getTechnology().equalsIgnoreCase(tecno.getBasicaIdTecnologias().getCodigoBasica()))) {

                                        LOGGER.info("tecnologias iguales  agrego a la lista tecnoSub");
                                        nodoSitiAux = coverDtoCon.getNodoSitiData();// Brief 98062
                                        listCover.remove(coverDtoCon);
                                        CmtCoverDto coverDto = new CmtCoverDto();
                                        NodoMgl nodo = tecno.getNodoId();
                                        CmtBasicaMgl tec = nodo != null ? nodo.getNodTipo() : null;
                                        CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;

                                        coverDto.setTechnology(tec != null ? tec.getCodigoBasica() : "NA");
                                        coverDto.setColorTecno(retornaColorTecno(coverDto.getTechnology()));
                                        coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                                        coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                                        coverDto.setTecnologiaSubMgl(tecno);
                                        coverDto.setValidaCobertura(true);
                                        coverDto.setNodoSitiData(nodoSitiAux);//Brief 98062
                                        listCover.add(coverDto);
                                        control++;
                                    }
                                }
                            }
                            if (control == 0) {
                                if (tecno != null) {
                                    if (tecnologiaValidaCobertura(tecno.getBasicaIdTecnologias(), tipoBasicaExtMglVC)) {

                                        CmtCoverDto coverDto = new CmtCoverDto();
                                        NodoMgl nodo = tecno.getNodoId();
                                        CmtBasicaMgl tec = nodo != null ? nodo.getNodTipo() : null;
                                        CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;

                                        coverDto.setTechnology(tec != null ? tec.getCodigoBasica() : "NA");
                                        coverDto.setColorTecno(retornaColorTecno(coverDto.getTechnology()));
                                        coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                                        coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                                        coverDto.setTecnologiaSubMgl(tecno);
                                        coverDto.setValidaCobertura(true);
                                        listCover.add(coverDto);
                                    }
                                }
                            }
                        } else { //cuando no hay coberturas
                            NodoMgl nodo = tecno != null ? tecno.getNodoId() : null;
                            CmtBasicaMgl tec = nodo != null ? nodo.getNodTipo() : null;
                            CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;

                            if (tecnologiaValidaCobertura(tec, tipoBasicaExtMglVC)) {

                                CmtCoverDto coverDto = new CmtCoverDto();
                                coverDto.setTechnology(tec != null ? tec.getCodigoBasica() : "NA");
                                coverDto.setColorTecno(retornaColorTecno(coverDto.getTechnology()));
                                coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                                coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                                coverDto.setTecnologiaSubMgl(tecno);
                                coverDto.setValidaCobertura(true);
                                listCover.add(coverDto);

                            }
                        }
                    }
                    direccionDetalladaMglDto.setListCover(listCover);
                    direccionDetalladaMglDto.setLstTecnologiaSubMgls(tecnologiasCcmm);
                    //Fin Busqueda hhpp de la direccion
                }
            }
        }

        //Busco si la direccion tiene una factibilidad  vigente
        List<FactibilidadMgl> factibilidadVig
                = factibilidadMglFacadeLocal.findFactibilidadVigByDetallada(direccionDetalladaMglDto.getDireccionDetalladaId(), new Date());

        if (factibilidadVig != null && !factibilidadVig.isEmpty()) {
            FactibilidadMgl factibilidadMgl = factibilidadVig.get(0);
            direccionDetalladaMglDto.setFactibilidadId(factibilidadMgl.getFactibilidadId());
        }
        //Fin Busqueda si la direccion tiene una factibilidad  vigente

        return direccionDetalladaMglDto;

    }

    /**
     * Realiza la b&uacute;squeda del Mapa a trav&eacute;s de las Coordenadas.
     *
     * @throws MalformedURLException
     * @throws Exception
     */
    public void mostrarMapaByCoordenadasDirSelected(CmtDireccionDetalladaMglDto direccionDetalladaMglDto)
            throws MalformedURLException, Exception {
        assignDefaultDetailsToPopup();
        model = new DefaultMapModel();
        this.detalleFactibilidadMglSelected = null;
        detalleFactibilidadMgls = new ArrayList<>();
        CmtDireccionDetalladaMglDto direccionDetalladaMglDtoClone = direccionDetalladaMglDto;

        //valida si la dirección tiene información de latitud y longitud
        if (direccionDetalladaMglDto.getLatitudDir() != null
                && direccionDetalladaMglDto.getLongitudDir() != null) {

            try {
                verListadoDirecciones = false;
                verListadoLogDirecciones = false;
                showPanelCoordenadas = false;
                showPanelIngresarGPS = false;
                mostrarBotonAtras = true;

                String latitudMod = direccionDetalladaMglDto.getLatitudDir().replace(",", ".");
                String longitudMod = direccionDetalladaMglDto.getLongitudDir().replace(",", ".");
                center = "" + latitudMod + ", " + longitudMod + "";

                latLng = new LatLng(new Double(latitudMod), new Double(longitudMod));
                model.addOverlay(new Marker(latLng, direccionDetalladaMglDto.getDireccionTexto()));

                //Consulto las coberturas
                direccionDetalladaMglDto = consultarCoberturas(direccionDetalladaMglDto);
                listCover = direccionDetalladaMglDto.getListCover();

                //Guardo la factibilidad si no hay una vigente
                if (direccionDetalladaMglDto.getFactibilidadId() == null) {
                    FactibilidadMgl factibilidadMgl = new FactibilidadMgl();
                    factibilidadMgl.setUsuario(usuarioSesion);
                    factibilidadMgl.setDireccionDetalladaId(direccionDetalladaMglDto.getDireccionDetalladaId());
                    CmtDireccionDetalladaMgl detalladaMgl = cmtDireccionDetalleMglFacadeLocal.findById(direccionDetalladaMglDto.getDireccionDetalladaId());
                    GeograficoPoliticoMgl centroDir = null;
                    if (detalladaMgl != null) {
                        UbicacionMgl ubicacionMgl = detalladaMgl.getDireccion().getUbicacion() != null
                                ? detalladaMgl.getDireccion().getUbicacion() : null;
                        if (ubicacionMgl != null) {
                            centroDir = ubicacionMgl.getGpoIdObj();
                        }
                    }

                    //Obtiene el número de dias de vigencia de la factibilidad
                    String diasVen = this.consultarParametro(Constants.DIAS_VENCIMIENTO_FACTIBILIDAD);
                    //Se valida que se haya obtenido el número de dias para vencimiento (vigencia de factibilidad)
                    if (diasVen != null && !diasVen.isEmpty()) {
                        int diasForVencer = Integer.parseInt(diasVen);
                        factibilidadMgl.setFechaCreacion(new Date());
                        //Sumo los dias de vencimiento a la fecha de creacion
                        Date fechaVence = fechaVencimiento(new Date(), diasForVencer);
                        factibilidadMgl.setFechaVencimiento(fechaVence);
                        factibilidadMgl = factibilidadMglFacadeLocal.create(factibilidadMgl);
                        //valida que se haya creado un Id de factibilidad (ejecución automática de secuencia)
                        if (factibilidadMgl.getFactibilidadId() != null) {
                            //Creo el detalle de la factibilidad
                            if (listCover != null && !listCover.isEmpty()) {
                                tecnologiasPenetracion = new ArrayList<>();//Brief 98062
                                //Se asigna los detalles de factibilidad para cada cobertura
                                for (CmtCoverDto coverDto : listCover) {
                                    DetalleFactibilidadMgl detalleFactibilidadMgl = new DetalleFactibilidadMgl();
                                    detalleFactibilidadMgl.setCodigoNodo(coverDto.getNode());
                                    detalleFactibilidadMgl.setEstadoNodo(coverDto.getState());
                                    detalleFactibilidadMgl.setNombreTecnologia(coverDto.getTechnology());
                                    detalleFactibilidadMgl.setColorTecno(coverDto.getColorTecno());
                                    detalleFactibilidadMgl.setNodoSitiData(coverDto.getNodoSitiData());

                                    //Busco el nodo
                                    CmtBasicaMgl tecnologia2;
                                    NodoMgl nodo;
                                    CmtTipoBasicaMgl cmtTipoBasicaTec = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                                            Constant.TIPO_BASICA_TECNOLOGIA);
                                    //Valida si se obtiene información del nodo de la cobertura
                                    if (coverDto.getNode() != null) {
                                        //consulta el nodo en MER
                                        nodo = nodoMglFacadeLocal.findByCodigo(coverDto.getNode());
                                        //Cuando si se obtiene nodo en MER
                                        if (nodo != null) {
                                            //Cuando el nodo no tiene información de factibilidad
                                            if (nodo.getFactibilidad() == null) {
                                                /* Brief 98062 2022/02/21*/
                                                detalleFactibilidadMgl.setClaseFactibilidad(obtenerClaseFactibilidad(coverDto));

                                                //Cuando el nodo tiene información de factibilidad y está Positiva
                                            } else if (nodo.getFactibilidad().equalsIgnoreCase("P")) {
                                                detalleFactibilidadMgl.setClaseFactibilidad(obtenerClaseFactibilidad(coverDto));

                                                //Cuando el nodo tiene información de factibilidad y está Negativa
                                            } else if (nodo.getFactibilidad().equalsIgnoreCase("N")) {
                                                /* Brief 98062 */
                                                detalleFactibilidadMgl.setClaseFactibilidad(obtenerClaseFactibilidad(coverDto));

                                                //Cuando no es cobertura y el nodo no tiene factibilidad
                                            } else if (!coverDto.isIsCobertura() && nodo.getFactibilidad() == null) {
                                                detalleFactibilidadMgl.setClaseFactibilidad(obtenerClaseFactibilidad(coverDto));
                                            }
                                            detalleFactibilidadMgl.setSds(nodo.getLimites() != null ? nodo.getLimites() : "");
                                            detalleFactibilidadMgl.setProyecto(nodo.getProyecto() != null ? nodo.getProyecto() : "");
                                            tecnologia2 = nodo.getNodTipo();

                                        } else {// Cuando no se obtuvo información de Nodo en MER
                                            detalleFactibilidadMgl.setClaseFactibilidad(obtenerClaseFactibilidad(coverDto));
                                            tecnologia2 = cmtBasicaMglFacadeLocal.findByBasicaCode(coverDto.getTechnology(),
                                                    cmtTipoBasicaTec.getTipoBasicaId());
                                        }
                                    } else {//cuando no tiene nodo en la cobertura
                                        detalleFactibilidadMgl.setClaseFactibilidad(obtenerClaseFactibilidad(coverDto));

                                        tecnologia2 = cmtBasicaMglFacadeLocal.findByBasicaCode(coverDto.getTechnology(),
                                                cmtTipoBasicaTec.getTipoBasicaId());
                                    }

                                    CmtBasicaMgl estadoTecno = null;

                                    if (coverDto.getHhppMgl() != null) {
                                        HhppMgl hhppMgl = coverDto.getHhppMgl();
                                        EstadoHhppMgl estadoHhpp = hhppMgl.getEhhId();
                                        detalleFactibilidadMgl.setEstadoTecnologia(estadoHhpp != null ? estadoHhpp.getEhhNombre() : "NA");

                                    } else if (coverDto.getTecnologiaSubMgl() != null) {
                                        estadoTecno = coverDto.getTecnologiaSubMgl().getBasicaIdEstadosTec();
                                        detalleFactibilidadMgl.setEstadoTecnologia(estadoTecno != null ? estadoTecno.getNombreBasica() : "NA");
                                    }

                                    //Detalle SLA de ejecucion
                                    if (direccionDetalladaMglDtoClone.getTipoDireccion().equalsIgnoreCase("CCMM")
                                            && tecnologia2 != null && tecnologia2.getBasicaId() != null) {
                                        //consulto sla de ejecucion
                                        SlaEjecucionMgl slaEjecucionMgl = slaEjecucionMglFacadeLocal.findByTecnoAndEjecucion(tecnologia2, "CCMM");
                                        if (slaEjecucionMgl != null && estadoTecno != null) {
                                            DetalleSlaEjecucionMgl detalleSlaEjecucionMgls = detalleSlaEjeMglFacadeLocal.
                                                    findBySlaEjecucionAndEstCcmmAndTipoOt(slaEjecucionMgl, estadoTecno, null);
                                            if (detalleSlaEjecucionMgls != null
                                                    && detalleSlaEjecucionMgls.getDetSlaEjecucionId() != null) {

                                                List<DetalleSlaEjecucionMgl> resulList = detalleSlaEjeMglFacadeLocal.
                                                        findDetalleSlaEjecucionMaySecProcesoList(detalleSlaEjecucionMgls, 0);

                                                int totalSla = 0;
                                                String detalleSla = "";
                                                if (resulList != null && !resulList.isEmpty()) {
                                                    for (DetalleSlaEjecucionMgl detalleSlaEjecucionMgl1 : resulList) {
                                                        totalSla = totalSla + detalleSlaEjecucionMgl1.getSla();
                                                        if(coverDto.getTechnology().equalsIgnoreCase(Constant.TEC_PARAM_ULTIMA_MILLA_FTTH))
                                                            totalSla = instalacionUltimaMilla(totalSla, coverDto.getNodoSitiData());
                                                        detalleSla += detalleSlaEjecucionMgl1.getDetSlaEjecucionId().toString() + ",";
                                                    }
                                                    detalleFactibilidadMgl.setDetallesSlas(detalleSla);
                                                }
                                                detalleFactibilidadMgl.setTiempoUltimaLilla(totalSla);
                                                detalleFactibilidadMgl.setSlaEjecucionMglObj(slaEjecucionMgl);
                                            }
                                            detalleFactibilidadMgl.setSlaEjecucionMglObj(slaEjecucionMgl);
                                        }
                                    } else if (direccionDetalladaMglDtoClone.getTipoDireccion().equalsIgnoreCase("UNIDAD")
                                            && tecnologia2 != null && tecnologia2.getBasicaId() != null) {
                                        //consulto sla de ejecucion
                                        SlaEjecucionMgl slaEjecucionMgl = slaEjecucionMglFacadeLocal.findByTecnoAndEjecucion(tecnologia2, "UNIDAD");
                                        List<OtHhppMgl> ordenDir = otHhppMglFacadeLocal.findOtHhppByDireccionAndTecnologias(direccionDetalladaMglDtoClone.getDireccionMgl(),
                                                direccionDetalladaMglDtoClone.getSubDireccionMgl(), tecnologia2);

                                        if (slaEjecucionMgl != null) {

                                            if (ordenDir != null && !ordenDir.isEmpty()) {
                                                OtHhppMgl ot = ordenDir.get(0);
                                                DetalleSlaEjecucionMgl detalleSlaEjecucionMgls = detalleSlaEjeMglFacadeLocal.
                                                        findBySlaEjecucionAndEstCcmmAndTipoOt(slaEjecucionMgl, null, ot.getTipoOtHhppId());

                                                if (detalleSlaEjecucionMgls != null
                                                        && detalleSlaEjecucionMgls.getDetSlaEjecucionId() != null) {

                                                    CmtBasicaMgl estadoAbierto
                                                            = cmtBasicaMglFacadeLocal.
                                                            findByCodigoInternoApp(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO_COMPLETO);
                                                    CmtBasicaMgl estadoCerrado
                                                            = cmtBasicaMglFacadeLocal.
                                                            findByCodigoInternoApp(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO_COMPLETO);

                                                    int control;
                                                    if (ot.getEstadoGeneral().getBasicaId().compareTo(estadoAbierto.getBasicaId()) == 0) {
                                                        control = 1;
                                                    } else if (ot.getEstadoGeneral().getBasicaId().compareTo(estadoCerrado.getBasicaId()) == 0) {
                                                        control = 2;
                                                    } else {
                                                        control = 3;
                                                    }
                                                    List<DetalleSlaEjecucionMgl> resulList = detalleSlaEjeMglFacadeLocal.
                                                            findDetalleSlaEjecucionMaySecProcesoList(detalleSlaEjecucionMgls, control);

                                                    int totalSla = 0;
                                                    String detalleSla = "";
                                                    if (resulList != null && !resulList.isEmpty()) {
                                                        for (DetalleSlaEjecucionMgl detalleSlaEjecucionMgl1 : resulList) {
                                                            totalSla = totalSla + detalleSlaEjecucionMgl1.getSla();
                                                            if(coverDto.getTechnology().equalsIgnoreCase(Constant.TEC_PARAM_ULTIMA_MILLA_FTTH))
                                                                totalSla = instalacionUltimaMilla(totalSla, coverDto.getNodoSitiData());
                                                            detalleSla += detalleSlaEjecucionMgl1.getDetSlaEjecucionId().toString() + ",";
                                                        }
                                                        detalleFactibilidadMgl.setDetallesSlas(detalleSla);
                                                    }
                                                    detalleFactibilidadMgl.setTiempoUltimaLilla(totalSla);
                                                }
                                            } else {
                                                //No hay orden de trabajo
                                                int totalSla = 0;
                                                String detalleSla = "";
                                                List<DetalleSlaEjecucionMgl> lstDetalleSlaEjecucionMgls = slaEjecucionMgl.getLstDetalleSlaEjecucionMgls();
                                                for (DetalleSlaEjecucionMgl detalleSlaEjecucionMgl : lstDetalleSlaEjecucionMgls) {
                                                    if (detalleSlaEjecucionMgl.getEstadoRegistro() == 1) {
                                                        totalSla = totalSla + detalleSlaEjecucionMgl.getSla();
                                                        if(coverDto.getTechnology().equalsIgnoreCase(Constant.TEC_PARAM_ULTIMA_MILLA_FTTH))
                                                            totalSla = instalacionUltimaMilla(totalSla, coverDto.getNodoSitiData());
                                                        detalleSla += detalleSlaEjecucionMgl.getDetSlaEjecucionId().toString() + ",";
                                                    }
                                                }
                                                detalleFactibilidadMgl.setDetallesSlas(detalleSla);
                                                detalleFactibilidadMgl.setTiempoUltimaLilla(totalSla);
                                            }

                                            detalleFactibilidadMgl.setSlaEjecucionMglObj(slaEjecucionMgl);
                                        }
                                    }

                                    detalleFactibilidadMgl.setFactibilidadMglObj(factibilidadMgl);
                                    if (coverDto.isHhppExistente()) {
                                        detalleFactibilidadMgl.setEsHhpp("1");
                                    } else {
                                        detalleFactibilidadMgl.setEsHhpp("0");
                                    }
                                    /* Brief 98062*/
                                    //validar penetración de CCMM para ajustarla a dato de NODO MER

                                    if (direccionDetalladaMglDtoClone.getTipoDireccion()
                                            .equalsIgnoreCase("CCMM")
                                            && direccionDetalladaMglDtoClone.getCuentaMatrizMgl() != null) {

                                        ccmmSeleccionada = direccionDetalladaMglDtoClone.getCuentaMatrizMgl();
                                        validarPenetracionDetalleFactibilidad(this.ccmmSeleccionada, detalleFactibilidadMgl);

                                    }
                                    /*Cierre Brief 98062*/
                                    
                                    if(detalleFactibilidadMgl.getNombreTecnologia().equalsIgnoreCase(Constant.TEC_PARAM_ULTIMA_MILLA_FTTH)){
                                        detalleFactibilidadMgl.setTiempoUltimaLilla(
                                            instalacionUltimaMilla(
                                                detalleFactibilidadMgl.getTiempoUltimaLilla(), 
                                                detalleFactibilidadMgl.getNodoSitiData()
                                            )
                                        );
                                    }
                                    
                                    detalleFactibilidadMgl.setTipoServicio(tipoServicioSelected);
                                    detalleFactibilidadMgl.setTipoSolicitud(tipoSolicitudSelected);
                                    
                                    /* Crea el detalle de la factibilidad en BD */
                                    detalleFactibilidadMgl = detalleFactibilidadMglFacadeLocal.create(detalleFactibilidadMgl);
                                    if (detalleFactibilidadMgl.getFactibilidadDetalleId() != null) {
                                        LOGGER.info("Detalle de la factibilidad creado satisfactoriamente.");
                                        detalleFactibilidadMgls.add(detalleFactibilidadMgl);
                                    } else {
                                        LOGGER.info("Ocurrio un error al crear el detalle de la factibilidad.");
                                    }
                                }

                            }
                            direccionDetalladaMglDtoClone.setFactibilidadId(factibilidadMgl.getFactibilidadId());
                            if (direccionDetalladaMglDtoSelUnidad == null) {
                                direccionDetalladaList.remove(direccionDetalladaMglDto);
                                direccionDetalladaList.add(direccionDetalladaMglDtoClone);
                            }
                            //Cuando la dirección es una CCMM
                            if (direccionDetalladaMglDtoClone.getTipoDireccion().equalsIgnoreCase("CCMM")) {
                                if (direccionDetalladaMglDtoClone.getCuentaMatrizMgl() != null) {
                                    ccmmSeleccionada = direccionDetalladaMglDtoClone.getCuentaMatrizMgl();
                                }
                                dirIsCcmm = true;
                                dirIsHhpp = false;
                                dirIsUnidades = false;
                            } else if (direccionDetalladaMglDtoClone.getTipoDireccion().equalsIgnoreCase("UNIDAD")) {
                                if (direccionDetalladaMglDtoSelUnidad != null) {
                                    dirIsHhpp = false;
                                    dirIsCcmm = false;
                                    dirIsUnidades = true;
                                } else {
                                    dirIsHhpp = true;
                                    dirIsCcmm = false;
                                    dirIsUnidades = false;
                                }
                            }
                            detalleFactibilidadMglsAux = detalleFactibilidadMgls;
                            mostrarMapa = true;

                            if (mostrarMapa) {
                                // Hacer Scroll en el panel de Longitud:
                                PrimeFacesUtil.scrollTo("contenidoMapa");
                            }
                        } else {
                            msnError = "Ocurrio un error creando la factibilidad";
                            alerts();
                        }
                    } else {
                        msnError = "No esta configurado el parametros de los dias de vencimiento de una factibilidad";
                        alerts();
                    }

                    //Guardo la factibilidads si no hay una vigente 
                } else {

                    String tipoDir = null;
                    if (direccionDetalladaMglDto.getTipoDireccion().equalsIgnoreCase("CCMM")) {
                        if (direccionDetalladaMglDto.getCuentaMatrizMgl() != null) {
                            ccmmSeleccionada = direccionDetalladaMglDto.getCuentaMatrizMgl();
                        }
                        dirIsCcmm = true;
                        dirIsHhpp = false;
                        dirIsUnidades = false;
                        tipoDir = "CCMM";
                    } else if (direccionDetalladaMglDto.getTipoDireccion().equalsIgnoreCase("UNIDAD")) {
                        if (direccionDetalladaMglDtoSelUnidad != null) {
                            dirIsHhpp = false;
                            dirIsCcmm = false;
                            dirIsUnidades = true;
                            tipoDir = "UNIDAD";
                        } else {
                            dirIsHhpp = true;
                            dirIsCcmm = false;
                            dirIsUnidades = false;
                            tipoDir = "UNIDAD";
                        }
                    }
                    //Existe la factibilidad la muestro
                    detalleFactibilidadMgls = detalleFactibilidadMglFacadeLocal.
                            findListDetalleFactibilidad(direccionDetalladaMglDto.getFactibilidadId(), tipoDir);
                    detalleFactibilidadMglsAux = detalleFactibilidadMgls;
                    mostrarMapa = true;

                    if (mostrarMapa) {
                        // Hacer Scroll en el panel de Longitud:
                        PrimeFacesUtil.scrollTo("contenidoMapa");
                    }
                }

                if (detalleFactibilidadMglsAux != null && !detalleFactibilidadMglsAux.isEmpty()) {                    
                    String[] lstTecnosCalculate = lstTecnosCalculate();
                    paramsNodeDist = getParamsCalculateNodeDistance();
                    for (DetalleFactibilidadMgl detalleSlaEjecucionMgl : detalleFactibilidadMglsAux) {
                        if(detalleSlaEjecucionMgl.getNombreTecnologia().equalsIgnoreCase(Constant.TEC_PARAM_ULTIMA_MILLA_FTTH)){
                            detalleSlaEjecucionMgl.setTiempoUltimaLilla(
                                instalacionUltimaMilla(
                                    detalleSlaEjecucionMgl.getTiempoUltimaLilla(), 
                                    detalleSlaEjecucionMgl.getNodoSitiData()
                                )
                            );
                        }
                        if(detalleSlaEjecucionMgl.getClaseFactibilidad().equalsIgnoreCase(Constant.FAC_NEGATIVA)){
                            for (String nodeSitidata : lstTecnosCalculate) {
                                if(nodeSitidata.equalsIgnoreCase(detalleSlaEjecucionMgl.getNombreTecnologia())){
                                    detalleSlaEjecucionMgl.setCalculateNodeDistanceWs(
                                        paramsNodeDist != null && !paramsNodeDist.isEmpty());
                                }
                            }
                        }
                    }
                    
                    String tecnologiasNegativas = generateMsgValidacionFactibilidad(detalleFactibilidadMglsAux);

                    if (StringUtils.isNotBlank(tecnologiasNegativas)) {
                        tituloPopupWarning  = "¡Validación de Factibilidad!";
                        isWarningMessage = true;
                        tituloMsgDetallePopupWarning = "No se encontró una factibilidad positiva para esta dirección.";
                        msnError = "Puede realizar un escalamiento de factibilidad para validar la cobertura " + tecnologiasNegativas;
                        //mostrar popup con detalles de alerta de validación de factibilidad.
                        alerts();
                    }
                }
            } catch (NumberFormatException ex) {
                msnError = "Formato invalido de dias a vencer: No se pudo crear la factibilidad";
                alerts();
            }
        } else {
            msnError = "La direccion no posee informacion de  Latitud y Longitud";
            alerts();
        }
    }

    /**
     * Genera el mensaje complementario para completar la información del popup
     * que indica la acción de realizar escalamiento de tecnología
     *
     * @param detalleFactibilidadMglsAux {@link List<DetalleFactibilidadMgl>}
     * @return {@link String}
     * @author Gildardo Mora
     */
    private String generateMsgValidacionFactibilidad(List<DetalleFactibilidadMgl> detalleFactibilidadMglsAux) {
        final String FOG = "FOG";
        final String FOU = "FOU";
        Map<String, String> tecnologiasMap = new HashMap<>();
        tecnologiasMap.put(FOG, "GPON");
        tecnologiasMap.put(FOU, "UNIFILAR");
        AtomicReference<String> msgResult = new AtomicReference<>("");

        detalleFactibilidadMglsAux.stream()
                .filter(factibilidad -> factibilidad.getClaseFactibilidad().equalsIgnoreCase(FACTIBILIDAD_NEGATIVA))
                .filter(factibilidad -> factibilidad.getNombreTecnologia().equalsIgnoreCase(FOG)
                        || factibilidad.getNombreTecnologia().equalsIgnoreCase(FOU))
                .forEach(factibilidad -> {
                    if (StringUtils.isBlank(msgResult.get())) {
                        msgResult.set(tecnologiasMap.get(factibilidad.getNombreTecnologia().toUpperCase()));
                    } else {
                        msgResult.set(msgResult.get() + " o " + tecnologiasMap.get(factibilidad.getNombreTecnologia().toUpperCase()));
                    }
                });

        return msgResult.get();
    }

    /**
     * Determina la clase de factibilidad (Negativa / Positiva) con base a la respuesta de SITIDATA
     *
     * @param coberturaProcesada Cobertura a la que se le está agregando detalles de factibilidad.
     * @return POSITIVA / NEGATIVA
     * @author Gildardo Mora
     */
    private String obtenerClaseFactibilidad(CmtCoverDto coberturaProcesada) {
        /*Nota: Para el caso de factibilidad masiva se realizó un método homonimo
         * en co.com.claro.mgl.businessmanager.address.MglRunableMasivoFactiblidadManager */

        final String FACTIBILIDAD_POSITIVA = "POSITIVA";

        /* Para el caso de la tecnología RFO se valida la factibilidad frente
         *  al nodo almacenado en MER y no a la respuesta de SITIDATA */
        if (coberturaProcesada.getTechnology().equals(CmtCoverEnum.NODO_RFO.getCodigo())) {

            if (StringUtils.isBlank(coberturaProcesada.getNode())) {
                return FACTIBILIDAD_NEGATIVA;
            }

            return FACTIBILIDAD_POSITIVA;
        }

        /* Para los casos de tecnología FOG y FOU
        se tiene en cuenta la letra con que inicia el código del nodo, ya que esta
        determina si está habilitado o no
        */
        if (StringUtils.isBlank(coberturaProcesada.getNodoSitiData())
                || (coberturaProcesada.getTechnology().equals(CmtCoverEnum.NODO_TRES.getCodigo())
                && coberturaProcesada.getNodoSitiData().startsWith("N"))
                || (coberturaProcesada.getTechnology().equals(CmtCoverEnum.NODO_FOU.getCodigo())
                && coberturaProcesada.getNodoSitiData().startsWith("S"))) {

            return FACTIBILIDAD_NEGATIVA;
        }

        return FACTIBILIDAD_POSITIVA;
    }

    /**
     * Se encarga de validar la penetración de tecnologías de la CCMM
     * y reasigna la información de nodo MER en el detalle de factibilidad
     *
     * @param ccmmSeleccionada {@link CmtCuentaMatrizMgl}
     * @throws ApplicationException Excepción personalizada de la aplicación
     */
    private void validarPenetracionDetalleFactibilidad(CmtCuentaMatrizMgl ccmmSeleccionada,
                                                       DetalleFactibilidadMgl detalleFactibilidadMgl) throws ApplicationException {

        ValidatePenetrationCuentaMatriz validatePenetrationCuentaMatriz = new ValidatePenetrationCuentaMatriz();
        if (tecnologiasPenetracion == null || tecnologiasPenetracion.isEmpty()) {
            tecnologiasPenetracion = validatePenetrationCuentaMatriz.findTecnologiasPenetracionCuentaMatriz(ccmmSeleccionada);
        }

        if (detalleFactibilidadMgl.getCodigoNodo() != null) {
            /* Comprueba si la lista de penetración de tecnologías de la CCMM
             * contiene la tecnología presente en el detalle de factibilidad,
             * si no existe, borra el código del nodo MER
             * */
            if (!tecnologiasPenetracion.contains(detalleFactibilidadMgl.getNombreTecnologia())) {
                detalleFactibilidadMgl.setCodigoNodo("");//Deja el nodo vacio para que no sea mostrado en el front
            }
        }

    }

    /**
     * Construye un DTO de Direcci&oacute;n Detallada, a partir del JSON Object
     * <i>splitAddres</i>, obtenido del response.
     *
     * @param splitAddress Objeto JSON que contiene la informaci&oacute;n de la
     *                     Direcci&oacute;n Detallada.
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
     * @param coverJsonObj   Objeto JSON que contiene la informaci&oacute;n de la
     *                       Cobertura.
     * @param idDireccion    Identificador de la Direcci&oacute;n del registro de
     *                       Direcci&oacute;n Detallada.
     * @param idSubDireccion Identificador de la SubDirecci&oacute;n del
     *                       registro de Direcci&oacute;n Detallada.
     * @return Data Transfer Object de Cobertura.
     */
    private CmtCoverDto getCoverDTO(JSONObject coverJsonObj, BigDecimal idDireccion, BigDecimal idSubDireccion)
            throws ApplicationException {
        CmtCoverDto coverDto = null;

        if (coverJsonObj != null) {
            coverDto = new CmtCoverDto();
            coverDto.setTechnology(coverJsonObj.getString("technology"));
            coverDto.setNode(coverJsonObj.getString("node"));
            coverDto.setIsCobertura(true);
            if (coverJsonObj.has("state")) {
                if (coverJsonObj.getString("state").equalsIgnoreCase("A")) {
                    coverDto.setState("ACTIVO");
                } else if (coverJsonObj.getString("state").equalsIgnoreCase("I")) {
                    coverDto.setState("INACTIVO");
                } else {
                    coverDto.setState(null);
                }
            } else {
                coverDto.setState(null);
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
                            = hhppMglFacadeLocal.findHhppByNodoDireccionAndSubDireccion(
                            nodoMgl.getNodId(),
                            idDireccion,
                            idSubDireccion);

                    if (listadoTecHab != null && !listadoTecHab.isEmpty()) {
                        coverDto.setHhppExistente(true);
                        hhppMglsCov.add(listadoTecHab.get(0));
                        coverDto.setHhppMgl(listadoTecHab.get(0));
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
    public void ingresarCoordenadas() throws ApplicationException {

        // Marcar la opcion seleccionada:
        this.opcionSeleccionada = FUNCIONALIDAD_INGRESAR_COORDENADAS;

        // Habilitar el evento Point Select:
        this.pointSelectEnabled = true;

        listCover = new ArrayList<>();
        detalleFactibilidadMgls = new ArrayList<>();
        showPanelBusquedaDireccion = false;
        showPanelIngresarUbicacion = false;
        showPanelIngresarGPS = false;
        showPanelCoordenadas = true;
        mostrarBotonAtras = false;
        verListadoDirecciones = false;
        verListadoLogDirecciones = false;
        departamentoList = new ArrayList<>();
        ciudadesList = new ArrayList<>();
        centroPobladoSelected = null;
        nombreCentroPoblado = "";
        requestCoordenadas = new ArrayList<>();
        mostrarMapa = false;
        habilitaDpto = true;
        habilitaCity = true;
        idFactibilidad = null;
        nameEdificio = "";
        expRegCoorLis = this.consultarParametro(Constants.EXP_REG_VAL_COOR);
        dirIsCcmm = false;
        dirIsHhpp = false;
        mostrarValidacionCobertura = false;
        panelDetalleCobertura = false;
        limpiarPanelDir();
        mostrarHistorico = false;
        mostrarDetalleHistorico = false;
        mostrarMapaCober = false;
    }

    public void cerrarMapa() {
        showPanelBusquedaDireccion = false;
        showPanelIngresarUbicacion = false;
        showPanelIngresarGPS = false;
        showPanelCoordenadas = false;
        verListadoDirecciones = false;
        verListadoLogDirecciones = false;
        mostrarMapa = false;
        mostrarMapaCober = false;
        mostrarValidacionCobertura = false;
        mostrarHistorico = false;
        mostrarDetalleHistorico = false;
        limpiarPanelDir();
    }

    /**
     * Muestra el Panel de Consulta por <b>Punto de Ubicaci&oacute;n</b>.
     */
    public void verMapaIngresarPunto() {

        // Marcar la opcion seleccionada:
        this.opcionSeleccionada = FUNCIONALIDAD_PUNTO_UBICACION;

        // Activar el evento Point Select:
        this.pointSelectEnabled = true;

        showPanelBusquedaDireccion = false;
        showPanelIngresarUbicacion = true;
        showPanelCoordenadas = false;
        showPanelIngresarGPS = false;
        mostrarBotonAtras = false;
        verListadoDirecciones = false;
        verListadoLogDirecciones = false;
        centroPobladoSelected = null;
        nombreCentroPoblado = "";
        departamentoList = new ArrayList<>();
        ciudadesList = new ArrayList<>();
        mostrarMapa = false;
        listCover = new ArrayList<>();
        detalleFactibilidadMgls = new ArrayList<>();
        habilitaDpto = true;
        habilitaCity = true;
        idFactibilidad = null;
        nameEdificio = "";
        dirIsCcmm = false;
        dirIsHhpp = false;
        limpiarPanelDir();

        // Reiniciar el modelo del mapa.
        this.model = new DefaultMapModel();

        updateCenterIngresarPunto();
        mostrarValidacionCobertura = false;
        panelDetalleCobertura = false;
        mostrarHistorico = false;
        mostrarDetalleHistorico = false;
        mostrarMapaCober = false;

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
        // Marcar la opcion seleccionada:
        this.opcionSeleccionada = FUNCIONALIDAD_GPS;

        // Deshabilitar el evento Point Select:
        this.pointSelectEnabled = false;

        limpiarPanelDir();

        longitud = "";
        latitud = "";
        showPanelBusquedaDireccion = false;
        showPanelIngresarUbicacion = false;
        showPanelCoordenadas = false;
        showPanelIngresarGPS = true;
        this.coordinatesEnabled = false;
        mostrarBotonAtras = false;
        verListadoDirecciones = false;
        verListadoLogDirecciones = false;
        centroPobladoSelected = null;
        nombreCentroPoblado = "";
        departamentoList = new ArrayList<>();
        ciudadesList = new ArrayList<>();
        listCover = new ArrayList<>();
        detalleFactibilidadMgls = new ArrayList<>();
        mostrarMapa = false;
        habilitaDpto = true;
        habilitaCity = true;
        idFactibilidad = null;
        nameEdificio = "";
        dirIsCcmm = false;
        dirIsHhpp = false;
        mostrarValidacionCobertura = false;
        panelDetalleCobertura = false;
        PrimeFacesUtil.execute("findMe();");
        mostrarHistorico = false;
        mostrarDetalleHistorico = false;
        mostrarMapaCober = false;
    }

    /**
     * Realiza la b&uacute;squeda de Direcciones cercanas, referente a las
     * Coordenadas espec&iacute;ficadas, de acuerdo a una distancia de
     * desviaci&oacute;n.
     */
    public void consultarDireccionesCercanasByCoordenadas() throws Exception {
        assignDefaultDetailsToPopup();

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

            String desviacion = this.consultarParametro(Constants.DESVIACION_EN_METROS);

            if (desviacion != null && !desviacion.isEmpty()) {
                if (isNumeric(desviacion)) {
                    int desvMts = Integer.parseInt(desviacion);
                    if (validaLatitud && validaLongitud) {

                        if (direccionDetalladaMglDtoSel == null || opcionSeleccionada == 2) {
                            if (nombreCentroPoblado != null
                                    && !nombreCentroPoblado.isEmpty()) {

                                String latitudMod = latitud.replace(",", ".");
                                String longitudMod = longitud.replace(",", ".");

                                center = "" + latitudMod + ", " + longitudMod + "";

                                CmtRequestHhppByCoordinatesDto request1 = new CmtRequestHhppByCoordinatesDto();
                                request1.setUnitsNumber(MAX_UNITS_NUMBER);
                                request1.setLatitude(latitudMod);
                                request1.setLongitude(longitudMod);
                                // Metros de desviacion.
                                request1.setDeviationMtr(desvMts);
                                request1.setCiudad(centroPobladoSelected.getGeoCodigoDane());

                                try {
                                    List<CmtDireccionDetalladaMgl> listaDireccionDetallada
                                            = cmtDireccionDetalleMglFacadeLocal.findDireccionDetalladaByCoordenadas(request1);

                                    if (listaDireccionDetallada != null && !listaDireccionDetallada.isEmpty()) {
                                        if (OBTENER_INFORMACION_ADICIONAL_HHPP) {
                                            // Obtener la informacion adicional asociada a cada direccion:
                                            this.obtenerParametrosHhpp(listaDireccionDetallada);
                                        }
                                        List<CmtDireccionDetalladaMglDto> dirIguales = new ArrayList<>();
                                        List<CmtDireccionDetalladaMglDto> dirDistintas = new ArrayList<>();

                                        for (CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl : listaDireccionDetallada) {
                                            DireccionMgl dir = cmtDireccionDetalladaMgl.getDireccion();
                                            SubDireccionMgl subDir = cmtDireccionDetalladaMgl.getSubDireccion();
                                            // Construir un DTO a partir de la entidad, y adicionarlo a la lista:
                                            CmtDireccionDetalladaMglDto cmtDireccionDetalladaMglDto
                                                    = cmtDireccionDetalladaMgl.convertirADto();

                                            if (dir != null) {
                                                cmtDireccionDetalladaMglDto.setDireccionMgl(dir);
                                                cmtDireccionDetalladaMglDto.setSubDireccionMgl(subDir);
                                                UbicacionMgl ubicacionMgl = dir.getUbicacion() != null
                                                        ? dir.getUbicacion() : null;
                                                GeograficoPoliticoMgl centro;
                                                if (ubicacionMgl != null) {
                                                    cmtDireccionDetalladaMglDto.setLatitudDir(ubicacionMgl.getUbiLatitud());
                                                    cmtDireccionDetalladaMglDto.setLongitudDir(ubicacionMgl.getUbiLongitud());
                                                    centro = ubicacionMgl.getGpoIdObj();
                                                    if (centro != null) {
                                                        cmtDireccionDetalladaMglDto.setCentroPoblado(centro.getGpoNombre());
                                                        GeograficoPoliticoMgl ciudad = geograficoPoliticoFacadeLocal.findById(centro.getGeoGpoId());
                                                        if (ciudad != null) {
                                                            cmtDireccionDetalladaMglDto.setCiudad(ciudad.getGpoNombre());
                                                            GeograficoPoliticoMgl dpto = geograficoPoliticoFacadeLocal.findById(ciudad.getGeoGpoId());
                                                            if (dpto != null) {
                                                                cmtDireccionDetalladaMglDto.setDepartamento(dpto.getGpoNombre());
                                                            }
                                                        }

                                                    }
                                                }
                                                cmtDireccionDetalladaMglDto.setConfiabilidad(dir.getDirConfiabilidad() != null ? dir.getDirConfiabilidad() : BigDecimal.ONE);
                                                //Busqueda cmt Direccion
                                                CmtDireccionMgl direccionCm = cmtDireccionMglFacadeLocal.
                                                        findCmtIdDireccion(dir.getDirId());
                                                if (direccionCm != null && direccionCm.getCuentaMatrizObj() != null) {
                                                    CmtCuentaMatrizMgl cuenta = direccionCm.getCuentaMatrizObj();
                                                    cmtDireccionDetalladaMglDto.setTipoDireccion("CCMM");
                                                    cmtDireccionDetalladaMglDto.setCuentaMatrizMgl(cuenta);
                                                } else {
                                                    cmtDireccionDetalladaMglDto.setTipoDireccion("UNIDAD");
                                                }
                                            }
                                            //Busqueda cmt Direccion

                                            //Busco si la direccion tiene una factibilidad  vigente List
                                            List<FactibilidadMgl> factibilidadVig
                                                    = factibilidadMglFacadeLocal.findFactibilidadVigByDetallada(cmtDireccionDetalladaMglDto.getDireccionDetalladaId(), new Date());

                                            if (factibilidadVig != null && !factibilidadVig.isEmpty()) {
                                                FactibilidadMgl factibilidadMgl = factibilidadVig.get(0);
                                                cmtDireccionDetalladaMglDto.setFactibilidadId(factibilidadMgl.getFactibilidadId());
                                            }
                                            //Fin Busqueda si la direccion tiene una factibilidad  vigente

                                            if (cmtDireccionDetalladaMglDto.getLatitudDir() != null
                                                    && cmtDireccionDetalladaMglDto.getLongitudDir() != null) {

                                                String latitudMod1 = cmtDireccionDetalladaMglDto.getLatitudDir().replace(",", ".");
                                                String longitudMod1 = cmtDireccionDetalladaMglDto.getLongitudDir().replace(",", ".");
                                                if ((latitudMod1.trim().equalsIgnoreCase(request1.getLatitude().trim()))
                                                        && (longitudMod1.trim().equalsIgnoreCase(request1.getLongitude().trim()))) {
                                                    dirIguales.add(cmtDireccionDetalladaMglDto);
                                                } else {
                                                    dirDistintas.add(cmtDireccionDetalladaMglDto);
                                                }
                                            }

                                        }
                                        if (!dirIguales.isEmpty()) {
                                            direccionDetalladaList.addAll(dirIguales);
                                        } else {
                                            direccionDetalladaList.addAll(dirDistintas);
                                        }
                                        if (opcionSeleccionada != 3) {
                                            verListadoDirecciones = true;
                                            showPanelIngresarUbicacion = false;
                                            mostrarBotonAtras = true;
                                        } else {
                                            longitud = request1.getLongitude();
                                            latitud = request1.getLatitude();
                                        }
                                    } else {
                                        msnError = "No se encontraron resultados en la búsqueda.";
                                        alerts();
                                    }
                                } catch (ApplicationException e) {
                                    // Imprimir en pantalla el mensaje de validacion de la consulta:
                                    String mensaje = e.getMessage();
                                    info(mensaje);
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
                } else {
                    msnError = "El valor parametrizado para la desviación en metros no es numerico";
                    alerts();
                }

            } else {
                msnError = "No existe una configuracion para el parametro: " + Constants.DESVIACION_EN_METROS + "";
                alerts();
            }

        } catch (ApplicationException e) {
            String msgError = "Error al consultar las direcciones cercanas por coordenadas." + e.getMessage();
            exceptionServBean.notifyError(e, msgError, VISOR_DE_FACTIBILIDAD);
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
     * Realiza la consulta del valor de un parámetro, a través de
     * su acrónimo.
     *
     * @param acronimo Acrónimo del Parámetro a buscar.
     * @return Valor del Parámetro.
     * @throws ApplicationException Excepción personalidad de la applicación.
     */
    private String consultarParametro(String acronimo) throws ApplicationException {
        try {
            return parametroFacadeLocal.findParameterValueByAcronymInCache(acronimo);
        } catch (NullPointerException e) {
            throw new ApplicationException("No se encuentra configurado el parámetro '" + acronimo + "'.");
        }
    }

    /**
     * Actualiza el centro, basado en el par&aacute;metro <i>centro</i>, enviado
     * desde JavaScript.
     */
    public void updateCenterIngresarPunto() {
        if (Strings.isEmpty(latitud) || Strings.isEmpty(longitud)) {
            // Establecer un centro por defecto.
            center = "4.650997360848955, -74.10712368786335";
        } else {
            center = latitud + ", " + longitud;
        }

        this.coordinatesEnabled = false;

        // Actualizar el panel del Mapa:
        PrimeFacesUtil.update("@widgetVar(panelMapaIngresarUbicacion)");
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
     * Evento llamado al momento de seleccionar un subedificio del listado.
     */
    public void onSelectSubedificio() throws ApplicationException {

        if (idSubEdificioMglSel != null) {

            if (idSubEdificioMglSel.compareTo(ccmmSeleccionada.getSubEdificioGeneral().getSubEdificioId()) == 0) {
                hhppCcmmList = hhppMglFacadeLocal.obtenerHhppCMUnicaDirAndSub(ccmmSeleccionada);
            } else {
                hhppCcmmList = hhppMglFacadeLocal.findHhppBySubEdificioIdUnicaDirAndSub(idSubEdificioMglSel);
            }

            uniExistentesList = new ArrayList<>();

            if (hhppCcmmList != null && !hhppCcmmList.isEmpty()) {
                for (HhppMgl hhpp : hhppCcmmList) {
                    CmtDireccionDetalladaMgl detallada = cmtDireccionDetalleMglFacadeLocal.findByHhPP(hhpp);
                    if (detallada != null) {
                        detallada.setHhppMgl(hhpp);
                        uniExistentesList.add(detallada);
                    }
                }
            }
        }

    }

    /**
     * Evento llamado al momento de seleccionar una direccion del listado.
     */
    public void onSelectCcMMAndHHpp(SelectEvent<CmtDireccionDetalladaMglDto> event) throws ApplicationException, CloneNotSupportedException {

        if (direccionDetalladaMglDtoSel != null && direccionDetalladaMglDtoSel.getTipoDireccion() != null) {
            if (direccionDetalladaMglDtoSel.getTipoDireccion().equalsIgnoreCase("CCMM")
                    && direccionDetalladaMglDtoSel.getCuentaMatrizMgl() != null) {
                ccmmSeleccionada = direccionDetalladaMglDtoSel.getCuentaMatrizMgl();
                if (ccmmSeleccionada != null) {
                    edificiosCcmmList = cmtSubEdificioMglFacadeLocal.findSubEdificioByCuentaMatriz(ccmmSeleccionada);
                    hhppCcmmList = hhppMglFacadeLocal.obtenerHhppCMUnicaDirAndSub(ccmmSeleccionada);

                    uniExistentesList = new ArrayList<>();
                    //recorrido para tomar el regional del hhpp
                    if (hhppCcmmList != null && !hhppCcmmList.isEmpty()) {
                        HhppMglManager hhppMglManager = new HhppMglManager();
                        for (HhppMgl hhpp : hhppCcmmList) {
                            CmtDireccionDetalladaMgl detallada = cmtDireccionDetalleMglFacadeLocal.findByHhPP(hhpp);
                            if (detallada != null) {
                                detallada.setHhppMgl(hhpp);
                                List<HhppMgl> hhhpSubDirList = hhppMglManager
                                        .findHhppSubDireccion(detallada.getSubDireccion().getSdiId());
                                if (hhhpSubDirList != null && !hhhpSubDirList.isEmpty()) {
                                    if (hhhpSubDirList.get(0).getEstadoRegistro() == 1) {
                                        detallada.setHhppExistente(true);
                                    }
                                }
                                uniExistentesList.add(detallada);
                            }
                        }
                        uniExistentesList = agregarNodoCertificado(uniExistentesList);
                    }
                    //JDHT obtener divisional del nodo del centro poblado de la CCMM
                    if (ccmmSeleccionada.getCentroPoblado() != null) {
                        NodoMglManager nodoManager = new NodoMglManager();

                        List<NodoMgl> nodoMglCentroPobladoList
                                = nodoManager.find5NodosByCentroPobladoList(ccmmSeleccionada.getCentroPoblado().getGpoId());
                        if (nodoMglCentroPobladoList != null && !nodoMglCentroPobladoList.isEmpty()) {
                            if (nodoMglCentroPobladoList.get(0).getDisId() != null) {
                                direccionDetalladaMglDtoSel.setDivisionalNodo(nodoMglCentroPobladoList.get(0).getDivId().getNombreBasica());
                            } else {
                                direccionDetalladaMglDtoSel.setDivisionalNodo("Divisional No Asociado");
                            }
                        }
                    }

                }
                dirIsCcmm = true;
                dirIsHhpp = false;
                dirIsUnidades = false;
            } else if (direccionDetalladaMglDtoSel.getTipoDireccion().equalsIgnoreCase("UNIDAD")) {
                BigDecimal detalladaMglId = direccionDetalladaMglDtoSel.getDireccionDetalladaId();
                CmtDireccionDetalladaMgl detalladaMgl = cmtDireccionDetalleMglFacadeLocal.findById(detalladaMglId);
                if (detalladaMgl != null) {

                    //JDHT obtener divisional del nodo del centro poblado de la unidad
                    if (detalladaMgl.getDireccion().getUbicacion() != null
                            && detalladaMgl.getDireccion().getUbicacion().getGpoIdObj() != null) {
                        NodoMglManager nodoManager = new NodoMglManager();

                        List<NodoMgl> nodoMglCentroPobladoList
                                = nodoManager.find5NodosByCentroPobladoList(detalladaMgl.getDireccion().getUbicacion().getGpoIdObj().getGpoId());
                        if (nodoMglCentroPobladoList != null && !nodoMglCentroPobladoList.isEmpty()) {
                            if (nodoMglCentroPobladoList.get(0).getDisId() != null) {
                                direccionDetalladaMglDtoSel.setDivisionalNodo(nodoMglCentroPobladoList.get(0).getDivId().getNombreBasica());
                            } else {
                                direccionDetalladaMglDtoSel.setDivisionalNodo("Divisional No Asociado");
                            }
                        }
                    }

                    if (detalladaMgl.getDireccion() != null && detalladaMgl.getSubDireccion() == null) {

                        // Es una unidad se consulta Hhpps
                        List<CmtDireccionDetalladaMgl> lstDetalladaMgls = new ArrayList<>();
                        lstDetalladaMgls.add(detalladaMgl);
                        hhppCcmmList = hhppMglFacadeLocal.obtenerHhppByDireccionDetallaList(lstDetalladaMgls);

                        //Es direccion principal se consulta ls unidades existentes
                        uniExistentesList = cmtDireccionDetalleMglFacadeLocal.
                                findDireccionDetallaByDirId(detalladaMgl.getDireccion().getDirId(),
                                        detalladaMgl.getDireccionDetalladaId());

                        if (uniExistentesList != null && !uniExistentesList.isEmpty()) {
                            uniExistentesList = agregarNodoCertificado(uniExistentesList);
                            for (CmtDireccionDetalladaMgl detalle : new ArrayList<>(uniExistentesList)) {

                                //Busco si la direccion tiene una factibilidad  vigente List
                                List<FactibilidadMgl> factibilidadVig
                                        = factibilidadMglFacadeLocal.findFactibilidadVigByDetallada(detalle.getDireccionDetalladaId(), new Date());

                                if (factibilidadVig != null && !factibilidadVig.isEmpty()) {
                                    FactibilidadMgl factibilidadMgl = factibilidadVig.get(0);
                                    detalle.setIdFactibilidad(factibilidadMgl.getFactibilidadId());
                                }
                            }
                        }

                        dirIsCcmm = false;
                        dirIsHhpp = false;
                        dirIsUnidades = true;
                    } else if (detalladaMgl.getDireccion() != null && detalladaMgl.getSubDireccion() != null) {
                        // Es una unidad se consulta Hhpps
                        List<CmtDireccionDetalladaMgl> lstDetalladaMgls = new ArrayList<>();
                        lstDetalladaMgls.add(detalladaMgl);
                        hhppCcmmList = hhppMglFacadeLocal.obtenerHhppByDireccionDetallaList(lstDetalladaMgls);

                        uniExistentesList = cmtDireccionDetalleMglFacadeLocal.
                                findDireccionDetallaByDirIdSdirId(detalladaMgl.getDireccion().getDirId(),
                                        detalladaMgl.getSubDireccion().getSdiId());
                        uniExistentesList = agregarNodoCertificado(uniExistentesList);
                        dirIsCcmm = false;
                        dirIsHhpp = true;
                        dirIsUnidades = false;
                    }
                }
            } else {
                dirIsCcmm = false;
                dirIsHhpp = false;
                dirIsUnidades = false;
            }
        } else {
            dirIsCcmm = false;
            dirIsHhpp = false;
            dirIsUnidades = false;
        }
    }

    /**
     * Evento llamado al momento de seleccionar una Factibilidad del listado.
     */
    public void onSelectDetalleFactibilidad() {
        assignDefaultDetailsToPopup();

        try {
            if (this.detalleFactibilidadMglSelected != null) {

                if (detalleFactibilidadMglSelected.isIsChecked()) {
                    model.getPolygons().remove(detalleFactibilidadMglSelected.getPoligono());
                    detalleFactibilidadMglSelected.setIsChecked(false);
                } else {
                    String codigoNodo = this.detalleFactibilidadMglSelected.getNodoSitiData();
                    if (detalleFactibilidadMglSelected.getClaseFactibilidad().equals(Constants.FACTIBILIDAD_POSITIVA)) {
                        if (!Strings.isEmpty(codigoNodo)) {
                            // Realizar la busqueda del Nodo a partir de su codigo:
                            NodoMgl nodoMgl = mapNodos.get(codigoNodo);
                            if (nodoMgl != null) {
                                mapNodos.put(codigoNodo, nodoMgl);
                            }
                            // Realizar la consulta del Poligono a partir del Servicio GEO_COVERAGE:

                            String[] lisTechInvalid = this.consultarParametro(Constants.GEO_COVERAGE_TEC_INVALIDAS).split(";");
                            boolean flagTechInvalid = false;
                            for (String tech : lisTechInvalid) {
                                if (tech.equals(detalleFactibilidadMglSelected.getNombreTecnologia())) {
                                    flagTechInvalid = true;
                                }
                            }

                            if (flagTechInvalid) {
                                msnError = "No cuenta con poligonos asignados en la technologia  '"
                                        + this.detalleFactibilidadMglSelected.getNombreTecnologia() + "'.";
                                alerts();
                            } else {
                                UsagGeoCoverageRestResponse responseCall
                                        = callRsResUsagGeoCoverage(centroPobladoSelected.getGeoCodigoDane(), detalleFactibilidadMglSelected.getNodoSitiData(), detalleFactibilidadMglSelected.getNombreTecnologia(), "01");
                                if (responseCall != null && responseCall.getCode().equals(Constant.RES_USAG_GEO_COVERAGE_EXITOSO)) {
                                    ElementNSImpl list = (ElementNSImpl) responseCall.getCoords().getCoordinates();
                                    List<CoordsOrder> listXY = new ArrayList<>();
                                    NodeList nodeList = list.getChildNodes();
                                    for (int i = 0; i < nodeList.getLength(); i++) {
                                        Node x1 = nodeList.item(i);
                                        Node y1 = nodeList.item(i + 1);
                                        CoordsOrder coordsOrder = new CoordsOrder();
                                        coordsOrder.setX((String) x1.getFirstChild().getNodeValue());
                                        coordsOrder.setY((String) y1.getFirstChild().getNodeValue());
                                        listXY.add(coordsOrder);
                                        i++;
                                    }

                                    if (!listXY.isEmpty()) {
                                        // Construir el Poligono:
                                        Polygon polygon = new Polygon();
                                        polygon.setStrokeColor(detalleFactibilidadMglSelected.getColorTecno());
                                        polygon.setFillColor(detalleFactibilidadMglSelected.getColorTecno());
                                        polygon.setStrokeOpacity(0.7);
                                        polygon.setFillOpacity(0.7);

                                        // Mapear los nodos en el poligono:
                                        for (CoordsOrder nodoPoligono : listXY) {
                                            if (nodoPoligono.getX() != null && nodoPoligono.getY() != null) {
                                                double longitudNodo = Double.parseDouble(nodoPoligono.getX());
                                                double latitudNodo = Double.parseDouble(nodoPoligono.getY());

                                                latLng = new LatLng(latitudNodo, longitudNodo);
                                                polygon.getPaths().add(latLng);
                                            }
                                        }

                                        model.addOverlay(polygon);
                                        detalleFactibilidadMglSelected.setIsChecked(true);
                                        detalleFactibilidadMglSelected.setPoligono(polygon);

                                    } else {
                                        msnError = "No se encontró información del Polígono para la tecnología '"
                                                + this.detalleFactibilidadMglSelected.getNombreTecnologia() + "'.";
                                        alerts();
                                    }
                                } else {
                                    msnError = "No se encontró información del Polígono para la tecnología '"
                                            + this.detalleFactibilidadMglSelected.getNombreTecnologia() + "'.";
                                    alerts();
                                }
                            }
                        } else {
                            msnError = "No se encontró la información del nodo para la tecnología '"
                                    + this.detalleFactibilidadMglSelected.getNombreTecnologia() + "'.";
                            alerts();
                        }
                    } else {
                        msnError = "No se tiene una factibilidad POSITIVA para la tecnologia '"
                                + this.detalleFactibilidadMglSelected.getNombreTecnologia() + "'.";
                        alerts();
                    }
                }
            } else {
                msnError = "Debe seleccionar una tecnología para consultar su información.";
                alerts();
            }
        } catch (ApplicationException e) {
            String msg = "Ha ocurrido un error al momento de consultar la Cobertura: " + e.getMessage();
            exceptionServBean.notifyError(e, msg, VISOR_DE_FACTIBILIDAD);
        }
    }

    /**
     * Evento llamado al momento de seleccionar una Ccmm del listado.
     */
    public void onSelectCcmm() throws ApplicationException {
        assignDefaultDetailsToPopup();

        if (this.ccmmSeleccionada != null) {
            if (opcionSeleccionada == 1) {
                responseConstruirDireccion = new ResponseConstruccionDireccion();
                responseConstruirDireccion.setNombreDpto(ccmmSeleccionada.getDepartamento().getGpoNombre());
                responseConstruirDireccion.setNombreCity(ccmmSeleccionada.getMunicipio().getGpoNombre());
                responseConstruirDireccion.setNombreCentro(ccmmSeleccionada.getCentroPoblado().getGpoNombre());

                if (ccmmSeleccionada.getDireccionPrincipal() != null
                        && ccmmSeleccionada.getDireccionPrincipal().getDireccionObj() != null) {
                    DireccionMgl direccion = ccmmSeleccionada.getDireccionPrincipal().getDireccionObj();
                    CmtDireccionDetalladaMgl detalladaMgl = direccion.getDireccionDetallada() != null
                            ? direccion.getDireccionDetallada() : null;

                    responseConstruirDireccion.setDetalladaMgl(detalladaMgl);
                    responseConstruirDireccion.setDireccionStr(direccion.getDirNostandar());

                }
                int control = 0;
                for (ResponseConstruccionDireccion response : lstResponseConstruirDireccion) {
                    if ((response.getDireccionStr().equalsIgnoreCase(responseConstruirDireccion.getDireccionStr()))
                            && (response.getNombreCentro().equalsIgnoreCase(responseConstruirDireccion.getNombreCentro()))
                            && (response.getNombreCity().
                            equalsIgnoreCase(responseConstruirDireccion.getNombreCity()))) {
                        control++;
                    }
                }

                if (control > 0) {
                    msnError = "Ya existe en lista una dirección igual a la que intenta agregar.";
                    alerts();
                } else {
                    lstResponseConstruirDireccion.add(responseConstruirDireccion);
                    paramsConsulta.put(responseConstruirDireccion, ccmmSeleccionada.getCentroPoblado().getGpoId());
                    PrimeFaces.current().executeScript("PF('dlg3').hide();");
                }
            } else {

                coordenadaAgregar = new CmtRequestHhppByCoordinatesDto();
                coordenadaAgregar.setNombreDpto(ccmmSeleccionada.getDepartamento().getGpoNombre());
                coordenadaAgregar.setNombreCiudad(ccmmSeleccionada.getMunicipio().getGpoNombre());
                coordenadaAgregar.setNombreCentro(ccmmSeleccionada.getCentroPoblado().getGpoNombre());
                coordenadaAgregar.setCiudad(ccmmSeleccionada.getCentroPoblado().getGeoCodigoDane());

                String desviacion = this.consultarParametro(Constants.DESVIACION_EN_METROS);

                if (desviacion != null && !desviacion.isEmpty()) {
                    if (isNumeric(desviacion)) {
                        int desvMts = Integer.parseInt(desviacion);

                        if (ccmmSeleccionada.getDireccionPrincipal() != null
                                && ccmmSeleccionada.getDireccionPrincipal().getDireccionObj() != null) {
                            DireccionMgl direccion = ccmmSeleccionada.getDireccionPrincipal().getDireccionObj();

                            if (direccion.getUbicacion() != null) {

                                String latitudMod = direccion.getUbicacion().getUbiLatitud().replace(",", ".");
                                String longitudMod = direccion.getUbicacion().getUbiLongitud().replace(",", ".");
                                if (!latitudMod.equalsIgnoreCase("0") && !longitudMod.equalsIgnoreCase("0")) {
                                    coordenadaAgregar.setLongitude(longitudMod);
                                    coordenadaAgregar.setLatitude(latitudMod);

                                    CmtDireccionDetalladaMgl detalladaMgl = direccion.getDireccionDetallada() != null
                                            ? direccion.getDireccionDetallada() : null;

                                    coordenadaAgregar.setDetalladaMgl(detalladaMgl);

                                    coordenadaAgregar.setUnitsNumber(MAX_UNITS_NUMBER);
                                    coordenadaAgregar.setDeviationMtr(desvMts);

                                    if (!requestCoordenadas.isEmpty()) {
                                        int control = 0;
                                        for (CmtRequestHhppByCoordinatesDto coordenadas : new ArrayList<>(requestCoordenadas)) {
                                            if ((coordenadas.getLongitude().equalsIgnoreCase(coordenadaAgregar.getLongitude()))
                                                    && (coordenadas.getLatitude().equalsIgnoreCase(coordenadaAgregar.getLatitude()))) {
                                                control++;
                                            }
                                        }
                                        if (control > 0) {
                                            msnError = "Ya existe en lista unas coordenadas igual a la que intenta agregar.";
                                            alerts();
                                        } else {
                                            requestCoordenadas.add(coordenadaAgregar);
                                        }
                                    } else {
                                        requestCoordenadas.add(coordenadaAgregar);
                                    }
                                } else {
                                    msnError = "La dirección no posee información de Latitud y Longitud";
                                    alerts();
                                }

                            } else {
                                msnError = "La dirección no posee información de Ubicación";
                                alerts();
                            }

                        } else {
                            msnError = "La Cuenta matriz no posee una direccion válida";
                            alerts();

                        }

                    } else {
                        msnError = "El valor parametrizado para la desviacion en metros no es numerico";
                        alerts();
                    }
                } else {
                    msnError = "No existe una configuración para el parametro: " + Constants.DESVIACION_EN_METROS + "";
                    alerts();
                }
            }

        } else {
            msnError = "Debe seleccionar una cuenta matriz para agregarla a la busqueda.";
            alerts();

        }

    }

    public void volverPanelDireccion() {

        switch (opcionSeleccionada) {
            case 2:
                showPanelIngresarUbicacion = true;
                verListadoDirecciones = false;
                verListadoLogDirecciones = false;
                break;
            case 4:
                showPanelCoordenadas = true;
                verListadoDirecciones = false;
                verListadoLogDirecciones = false;
                latitud = "";
                longitud = "";
                break;
            default:
                showPanelBusquedaDireccion = true;
                verListadoDirecciones = false;
                verListadoLogDirecciones = false;
                responseConstruirDireccion = new ResponseConstruccionDireccion();
                habilitaCity = true;
                break;
        }
        PrimeFaces current = PrimeFaces.current();
        PrimeFacesUtil.update("@widgetVar(tablaDireccion)");
        current.executeScript("PF('tablaDireccion').clearFilters();");

        dirIsCcmm = false;
        dirIsHhpp = false;
        dirIsUnidades = false;
        validarCoberturaMenuItem = false;

    }

    public void volverPanelListDireccion() {

        if (opcionSeleccionada == 3) {
            mostrarMapa = false;
            showPanelIngresarGPS = true;
            if (listCover != null && !listCover.isEmpty()) {
                listCover.forEach((cover) -> {
                    cover.setIsChecked(false);
                });
            }
        } else {
            verListadoDirecciones = true;
            verListadoLogDirecciones = false;
            mostrarMapa = false;
            mostrarBotonAtras = false;
            if (listCover != null && !listCover.isEmpty()) {
                listCover.forEach((cover) -> {
                    cover.setIsChecked(false);
                });
            }
        }
        dirIsCcmm = false;
        dirIsHhpp = false;
        dirIsUnidades = false;
        detalleSlaEjecucion = null;

    }

    public void volverPanelIngresarUbicacion() {

        showPanelIngresarUbicacion = true;

    }

    public void adiccionarDireccion() throws ApplicationException {
        assignDefaultDetailsToPopup();

        if (idFactibilidad != null) {
            //consulto por el id de la factiilidad que ingresan
            FactibilidadMgl factibilidadMgl = factibilidadMglFacadeLocal.
                    findFactibilidadById(idFactibilidad);
            if (factibilidadMgl != null) {
                if (factibilidadMgl.getFechaVencimiento().equals(new Date())) {
                    msnError = "El id No: " + factibilidadMgl.getFactibilidadId() + " de  "
                            + "factibilidad ingresada se encuentra vigente hasta el dia de hoy";
                    alertProcesoExistoso();
                } else if (factibilidadMgl.getFechaVencimiento().after(new Date())) {
                    msnError = "El id No: " + factibilidadMgl.getFactibilidadId() + " de  "
                            + "factibilidad ingresada se encuentra vigente";
                    alertProcesoExistoso();
                } else if (factibilidadMgl.getFechaVencimiento().before(new Date())) {
                    msnError = "El id No: " + factibilidadMgl.getFactibilidadId() + " de  "
                            + "factibilidad ingresada se encuentra vencido";
                    alertProcesoExistoso();
                }
                cargarDireccionByFactibilidad(factibilidadMgl);
            } else {
                msnError = "No existe información de la factibilidad ingresada";
                alerts();
            }
        } else if (nameEdificio != null && !nameEdificio
                .isEmpty()) {
            if (nombreCentroPoblado != null) {
                if (idCiudad != null) {
                    //consulto por el nombre del subEdicicio que ingresan y centro poblado
                    listCcmm = cuentaMatrizMglFacadeLocal.findCcmmByNameAndCentroPoblado(nameEdificio.trim(), centroPobladoSelected);
                    if (listCcmm != null && !listCcmm.isEmpty() && listCcmm.size() > 1) {
                        PrimeFaces.current().executeScript("PF('dlg3').show();");
                    } else if (listCcmm != null && !listCcmm.isEmpty() && listCcmm.size() == 1) {
                        ccmmSeleccionada = listCcmm.get(0);
                        onSelectCcmm();
                    } else {
                        msnError = "No existe información del Edificio";
                        alerts();
                    }
                } else {
                    msnError = "Debe seleccionar una CIUDAD para la búsqueda por nombre de CCMM";
                    alerts();
                }
            } else {
                msnError = "Debe seleccionar un CENTRO POBLADO para la búsqueda por nombre de CCMM";
                alerts();
            }
        } else {
            if (!lstResponseConstruirDireccion.isEmpty()) {

                if (responseConstruirDireccion == null || responseConstruirDireccion.getDireccionStr() == null
                        || responseConstruirDireccion.getDireccionStr().isEmpty()) {
                    msnError = "Debe construir una dirección para ser agregada por favor.";
                    alerts();
                    return;
                }

                int control = 0;
                for (ResponseConstruccionDireccion response : lstResponseConstruirDireccion) {
                    if (response.getDireccionStr().equalsIgnoreCase(responseConstruirDireccion.getDireccionStr())) {
                        control++;
                    }
                }
                if (control > 0) {

                    msnError = "Ya existe en lista una dirección igual a la que intenta agregar.";
                    alerts();
                } else {
                    showBarrio = false;
                    if (centroPobladoSelected.getGpoMultiorigen() != null
                            && centroPobladoSelected.getGpoMultiorigen().equalsIgnoreCase("1")
                            && !showBarrio && drDireccion != null
                            && drDireccion.getIdTipoDireccion() != null
                            && !drDireccion.getIdTipoDireccion().isEmpty()
                            && drDireccion.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                        showBarrio = true;
                        validarBarrioCiudadMultiOrigen(centroPobladoSelected.getGpoMultiorigen());

                    } else {
                        responseConstruirDireccion.setNombreDpto(dptoSelected.getGpoNombre());
                        responseConstruirDireccion.setNombreCity(ciudadSelected.getGpoNombre());
                        responseConstruirDireccion.setNombreCentro(centroPobladoSelected.getGpoNombre());
                        lstResponseConstruirDireccion.add(responseConstruirDireccion);
                        paramsConsulta.put(responseConstruirDireccion, centroPobladoSelected.getGpoId());
                        limpiarBusquedaDir();
                    }
                }

            } else {
                showBarrio = false;
                if (centroPobladoSelected != null && centroPobladoSelected.getGpoMultiorigen() != null
                        && centroPobladoSelected.getGpoMultiorigen().equalsIgnoreCase("1")
                        && !showBarrio && drDireccion != null
                        && drDireccion.getIdTipoDireccion() != null
                        && !drDireccion.getIdTipoDireccion().isEmpty()
                        && drDireccion.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                    showBarrio = true;
                    validarBarrioCiudadMultiOrigen(centroPobladoSelected.getGpoMultiorigen());

                } else {
                    if (centroPobladoSelected != null && responseConstruirDireccion.getDireccionStr() != null) {
                        responseConstruirDireccion.setNombreDpto(dptoSelected != null ?
                                dptoSelected.getGpoNombre() : null);
                        responseConstruirDireccion.setNombreCity(ciudadSelected != null ?
                                ciudadSelected.getGpoNombre() : null);
                        responseConstruirDireccion.setNombreCentro(centroPobladoSelected != null ?
                                centroPobladoSelected.getGpoNombre() : null);
                        lstResponseConstruirDireccion.add(responseConstruirDireccion);
                        paramsConsulta.put(responseConstruirDireccion, centroPobladoSelected.getGpoId());
                        limpiarBusquedaDir();
                    } else {
                        msnError = "Debe ingresar un criterio de búsqueda válido";
                        alerts();
                    }

                }
            }
        }
        //JDHT georeferenciacion de direcciones CK y BM para actualizar coberturas
        try {
            georeferenciarDireccionActualizarCoberturas(responseConstruirDireccion, centroPobladoSelected, drDireccion);
        } catch (Exception e) {
            LOGGER.error("Error georeferenciando direccion para "
                    + "actualizar coberturas: " + e.getMessage() + "");
        }
    }

    public void adiccionarCoordenada() throws ApplicationException {
        assignDefaultDetailsToPopup();

        if (idFactibilidad != null) {
            //consulto por el id de la factiilidad que ingresan
            FactibilidadMgl factibilidadMgl = factibilidadMglFacadeLocal.
                    findFactibilidadById(idFactibilidad);
            if (factibilidadMgl != null) {
                if (factibilidadMgl.getFechaVencimiento().equals(new Date())) {
                    msnError = "El id No: " + factibilidadMgl.getFactibilidadId() + " de  "
                            + "factibilidad ingresada se encuentra vigente hasta el dia de hoy";
                    alertProcesoExistoso();
                } else if (factibilidadMgl.getFechaVencimiento().after(new Date())) {
                    msnError = "El id No: " + factibilidadMgl.getFactibilidadId() + " de  "
                            + "factibilidad ingresada se encuentra vigente";
                    alertProcesoExistoso();
                } else if (factibilidadMgl.getFechaVencimiento().before(new Date())) {
                    msnError = "El id No: " + factibilidadMgl.getFactibilidadId() + " de  "
                            + "factibilidad ingresada se encuentra vencido";
                    alertProcesoExistoso();
                }
                cargarDireccionByFactibilidadCoordenadas(factibilidadMgl);
            } else {
                msnError = "No existe información de la factibilidad";
                alerts();
            }
        } else if (nameEdificio != null && !nameEdificio
                .isEmpty()) {
            if (nombreCentroPoblado != null) {
                if (idCiudad != null) {
                    //consulto por el nombre del subEdicicio que ingresan y centro poblado
                    listCcmm = cuentaMatrizMglFacadeLocal.findCcmmByNameAndCentroPoblado(nameEdificio.trim(), centroPobladoSelected);
                    if (listCcmm != null && !listCcmm.isEmpty() && listCcmm.size() > 1) {
                        PrimeFaces.current().executeScript("PF('dlg3').show();");
                    } else if (listCcmm != null && !listCcmm.isEmpty() && listCcmm.size() == 1) {
                        ccmmSeleccionada = listCcmm.get(0);
                        onSelectCcmm();
                    } else {
                        msnError = "No existe información del Edificio";
                        alerts();
                    }
                } else {
                    msnError = "Debe seleccionar una CIUDAD para la búsqueda por nombre de CCMM";
                    alerts();
                }
            } else {
                msnError = "Debe seleccionar un CENTRO POBLADO para la búsqueda por nombre de CCMM";
                alerts();
            }
        } else {
            Pattern pat = Pattern.compile(expRegCoorLis);

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

            String desviacion = this.consultarParametro(Constants.DESVIACION_EN_METROS);

            if (desviacion != null && !desviacion.isEmpty()) {
                if (isNumeric(desviacion)) {
                    int desvMts = Integer.parseInt(desviacion);
                    if (nombreCentroPoblado != null) {
                        if (idCiudad != null) {

                            if (validaLatitud && validaLongitud) {
                                coordenadaAgregar = new CmtRequestHhppByCoordinatesDto();
                                coordenadaAgregar.setUnitsNumber(MAX_UNITS_NUMBER);
                                coordenadaAgregar.setDeviationMtr(desvMts);
                                coordenadaAgregar.setCiudad(centroPobladoSelected.getGeoCodigoDane());
                                String latitudMod = latitud.replace(",", ".");
                                String longitudMod = longitud.replace(",", ".");
                                coordenadaAgregar.setLongitude(longitudMod);
                                coordenadaAgregar.setLatitude(latitudMod);
                                coordenadaAgregar.setNombreCentro(centroPobladoSelected.getGpoNombre());
                                coordenadaAgregar.setNombreCiudad(ciudadSelected.getGpoNombre());
                                coordenadaAgregar.setNombreDpto(dptoSelected.getGpoNombre());

                                if (!requestCoordenadas.isEmpty()) {
                                    int control = 0;
                                    for (CmtRequestHhppByCoordinatesDto coordenadas : new ArrayList<>(requestCoordenadas)) {
                                        if ((coordenadas.getLongitude().equalsIgnoreCase(coordenadaAgregar.getLongitude()))
                                                && (coordenadas.getLatitude().equalsIgnoreCase(coordenadaAgregar.getLatitude()))) {
                                            control++;
                                        }
                                    }
                                    if (control > 0) {
                                        msnError = "Ya existe en lista unas coordenadas igual a la que intenta agregar.";
                                        alerts();
                                    } else {
                                        requestCoordenadas.add(coordenadaAgregar);
                                        longitud = "";
                                        latitud = "";
                                    }
                                } else {
                                    requestCoordenadas.add(coordenadaAgregar);
                                    longitud = "";
                                    latitud = "";
                                }

                            } else if (!validaLatitud && !validaLongitud) {
                                msnError = "Formato inválido de Latitud y Longitud: por favor ingrese valores como: "
                                        + "Longitud= -4,5678996 : Latitud=6,75445748";
                                alerts();
                            } else if (!validaLatitud) {
                                msnError = "Formato inválido de Latitud: por favor ingrese valores como: "
                                        + "Latitud= -4,5678996 : 6,75445748";
                                alerts();
                            } else if (!validaLongitud) {
                                msnError = "Formato inválido de Longitud: por favor ingrese valores como: "
                                        + "Longitud= -4,5678996 : 6,75445748";
                                alerts();
                            }

                        } else {
                            msnError = "Debe seleccionar un CIUDAD para la búsqueda";
                            alerts();
                        }

                    } else {
                        msnError = "Debe seleccionar un CENTRO POBLADO para la búsqueda";
                        alerts();
                    }

                } else {
                    msnError = "El valor parametrizado para la desviacion en metros no es numerico";
                    alerts();
                }
            } else {
                msnError = "No existe una configuracion para el parametro: " + Constants.DESVIACION_EN_METROS + "";
                alerts();
            }

        }
    }

    public void quitarDireccionList(ResponseConstruccionDireccion responseConstruccionDireccion) {

        lstResponseConstruirDireccion.remove(responseConstruccionDireccion);
        paramsConsulta.remove(responseConstruccionDireccion);
    }

    public void quitarCoordenadaList(CmtRequestHhppByCoordinatesDto requestHhppByCoordinatesDto) {

        requestCoordenadas.remove(requestHhppByCoordinatesDto);

    }

    public CmtDireccionDetalladaMgl returnDirDetallada(List<CmtDireccionDetalladaMgl> direccionesDetalladas, DrDireccion drDireccion) {
        return null;
    }

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
     * Función que realiza la creacion de una direccion en las tablas direccion,
     * subdireccion, direcciondetallada
     *
     * @param centroPobladoSeleccionado
     * @throws ApplicationException
     * @author Victor Bocanegra
     */
    public List<CmtDireccionDetalladaMgl> crearDireccionConsultada(DrDireccion direccionConstruida,
                                                                   GeograficoPoliticoMgl centroPobladoSeleccionado) throws ApplicationException {
        assignDefaultDetailsToPopup();

        try {
            List<CmtDireccionDetalladaMgl> resultado = null;
            DrDireccionManager drDireccionManager = new DrDireccionManager();
            String address = drDireccionManager.getDireccion(direccionConstruida);
            String barrioStr = "";
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setCodDaneVt(centroPobladoSeleccionado.getGeoCodigoDane());
            addressRequest.setAddress(address);
            addressRequest.setCity(centroPobladoSeleccionado.getGeoCodigoDane());
            addressRequest.setState("");

            if (direccionConstruida.getIdTipoDireccion() != null
                    && direccionConstruida.getIdTipoDireccion().equalsIgnoreCase("CK")
                    && centroPobladoSeleccionado.getGpoMultiorigen() != null
                    && centroPobladoSeleccionado.getGpoMultiorigen().equalsIgnoreCase("1")
                    && direccionConstruida.getBarrio() != null) {
                addressRequest.setNeighborhood(direccionConstruida.getBarrio());
            }
            addressRequest.setTipoDireccion("CK");

            //JDHT Se pone en true para indicar que es una direccion creada desde factibilidad
            direccionConstruida.setDireccionFactibilidad(true);
            AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
            ResponseMessage responseMessageCreateDir
                    = addressEJBRemote.createAddress(addressRequest,
                    usuarioSesion, "MGL", "", direccionConstruida);

            if (responseMessageCreateDir != null
                    && responseMessageCreateDir.getMessageType().equalsIgnoreCase(ResponseMessage.TYPE_ERROR)
                    && responseMessageCreateDir.isDireccionAntiguaFactibilidad()) {

                msnError = address + " " + responseMessageCreateDir.getMessageText();
                alerts();
                return null;
            }

            if (responseMessageCreateDir != null && responseMessageCreateDir.getIdaddress() != null
                    && !responseMessageCreateDir.getIdaddress().trim()
                    .isEmpty()) {
                if (responseMessageCreateDir.getNuevaDireccionDetallada() != null
                        && responseMessageCreateDir.getNuevaDireccionDetallada().getDireccionDetalladaId() != null) {
                    resultado = new ArrayList<>();
                    resultado.add(responseMessageCreateDir.getNuevaDireccionDetallada());
                }

            }
            return resultado;

        } catch (ApplicationException | ExceptionDB ex) {
            LOGGER.error("Error en crearDireccionConsultada " + ex.getMessage(), ex);
            throw new ApplicationException("Error en crearDireccionConsultada " + ex.getMessage(), ex);
        }
    }

    public void consultarDireccionesCercanasByListCoordenadas() throws Exception {
        assignDefaultDetailsToPopup();

        if (requestCoordenadas != null && !requestCoordenadas.isEmpty()) {
            direccionDetalladaList = new ArrayList<>();
            for (CmtRequestHhppByCoordinatesDto coordinatesDto : requestCoordenadas) {
                List<CmtDireccionDetalladaMgl> listaDireccionDetallada;
                if (coordinatesDto.getDetalladaMgl() != null) {
                    listaDireccionDetallada = new ArrayList<>();
                    listaDireccionDetallada.add(coordinatesDto.getDetalladaMgl());
                    if (OBTENER_INFORMACION_ADICIONAL_HHPP) {
                        // Obtener la informacion adicional asociada a cada direccion:
                        this.obtenerParametrosHhpp(listaDireccionDetallada);
                    }

                    for (CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl : listaDireccionDetallada) {
                        DireccionMgl dir = cmtDireccionDetalladaMgl.getDireccion();
                        // Construir un DTO a partir de la entidad, y adicionarlo a la lista:
                        CmtDireccionDetalladaMglDto cmtDireccionDetalladaMglDto
                                = cmtDireccionDetalladaMgl.convertirADto();

                        if (dir != null) {
                            cmtDireccionDetalladaMglDto.setDireccionMgl(dir);
                            SubDireccionMgl subDir = cmtDireccionDetalladaMgl.getSubDireccion();
                            cmtDireccionDetalladaMglDto.setSubDireccionMgl(subDir);
                            UbicacionMgl ubicacionMgl = dir.getUbicacion() != null
                                    ? cmtDireccionDetalladaMgl.getDireccion().getUbicacion() : null;
                            GeograficoPoliticoMgl centro;
                            if (ubicacionMgl != null) {
                                cmtDireccionDetalladaMglDto.setLatitudDir(ubicacionMgl.getUbiLatitud());
                                cmtDireccionDetalladaMglDto.setLongitudDir(ubicacionMgl.getUbiLongitud());
                                centro = ubicacionMgl.getGpoIdObj();
                                if (centro != null) {
                                    cmtDireccionDetalladaMglDto.setCentroPoblado(centro.getGpoNombre());
                                    GeograficoPoliticoMgl ciudad = geograficoPoliticoFacadeLocal.findById(centro.getGeoGpoId());
                                    if (ciudad != null) {
                                        cmtDireccionDetalladaMglDto.setCiudad(ciudad.getGpoNombre());
                                        GeograficoPoliticoMgl dpto = geograficoPoliticoFacadeLocal.findById(ciudad.getGeoGpoId());
                                        if (dpto != null) {
                                            cmtDireccionDetalladaMglDto.setDepartamento(dpto.getGpoNombre());
                                        }
                                    }

                                }
                            }
                            cmtDireccionDetalladaMglDto.setConfiabilidad(dir.getDirConfiabilidad() != null ? dir.getDirConfiabilidad() : BigDecimal.ONE);
                            //Busqueda cmt Direccion
                            CmtDireccionMgl direccionCm = cmtDireccionMglFacadeLocal.
                                    findCmtIdDireccion(dir.getDirId());
                            if (direccionCm != null && direccionCm.getCuentaMatrizObj() != null) {
                                CmtCuentaMatrizMgl cuenta = direccionCm.getCuentaMatrizObj();
                                cmtDireccionDetalladaMglDto.setTipoDireccion("CCMM");
                                cmtDireccionDetalladaMglDto.setCuentaMatrizMgl(cuenta);
                            } else {
                                cmtDireccionDetalladaMglDto.setTipoDireccion("UNIDAD");
                            }
                        }
                        //Busqueda cmt Direccion

                        //Busco si la direccion tiene una factibilidad  vigente List
                        List<FactibilidadMgl> factibilidadVig
                                = factibilidadMglFacadeLocal.findFactibilidadVigByDetallada(cmtDireccionDetalladaMglDto.getDireccionDetalladaId(), new Date());

                        if (factibilidadVig != null && !factibilidadVig.isEmpty()) {
                            FactibilidadMgl factibilidadMgl = factibilidadVig.get(0);
                            cmtDireccionDetalladaMglDto.setFactibilidadId(factibilidadMgl.getFactibilidadId());
                        }
                        //Fin Busqueda si la direccion tiene una factibilidad  vigente

                        if (cmtDireccionDetalladaMglDto.getLongitudDir() != null
                                && cmtDireccionDetalladaMglDto.getLatitudDir() != null) {
                            direccionDetalladaList.add(cmtDireccionDetalladaMglDto);
                        }
                    }
                } else {
                    listaDireccionDetallada
                            = cmtDireccionDetalleMglFacadeLocal.findDireccionDetalladaByCoordenadas(coordinatesDto);

                    if (listaDireccionDetallada != null && !listaDireccionDetallada.isEmpty()) {
                        if (OBTENER_INFORMACION_ADICIONAL_HHPP) {
                            // Obtener la informacion adicional asociada a cada direccion:
                            this.obtenerParametrosHhpp(listaDireccionDetallada);
                        }
                        List<CmtDireccionDetalladaMglDto> dirIguales = new ArrayList<>();
                        List<CmtDireccionDetalladaMglDto> dirDistintas = new ArrayList<>();

                        for (CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl : listaDireccionDetallada) {
                            DireccionMgl dir = cmtDireccionDetalladaMgl.getDireccion();
                            // Construir un DTO a partir de la entidad, y adicionarlo a la lista:
                            CmtDireccionDetalladaMglDto cmtDireccionDetalladaMglDto
                                    = cmtDireccionDetalladaMgl.convertirADto();

                            if (dir != null) {
                                cmtDireccionDetalladaMglDto.setDireccionMgl(dir);
                                SubDireccionMgl subDir = cmtDireccionDetalladaMgl.getSubDireccion();
                                cmtDireccionDetalladaMglDto.setSubDireccionMgl(subDir);
                                UbicacionMgl ubicacionMgl = dir.getUbicacion() != null
                                        ? cmtDireccionDetalladaMgl.getDireccion().getUbicacion() : null;
                                GeograficoPoliticoMgl centro;
                                if (ubicacionMgl != null) {
                                    cmtDireccionDetalladaMglDto.setLatitudDir(ubicacionMgl.getUbiLatitud());
                                    cmtDireccionDetalladaMglDto.setLongitudDir(ubicacionMgl.getUbiLongitud());
                                    centro = ubicacionMgl.getGpoIdObj();
                                    if (centro != null) {
                                        cmtDireccionDetalladaMglDto.setCentroPoblado(centro.getGpoNombre());
                                        GeograficoPoliticoMgl ciudad = geograficoPoliticoFacadeLocal.findById(centro.getGeoGpoId());
                                        if (ciudad != null) {
                                            cmtDireccionDetalladaMglDto.setCiudad(ciudad.getGpoNombre());
                                            GeograficoPoliticoMgl dpto = geograficoPoliticoFacadeLocal.findById(ciudad.getGeoGpoId());
                                            if (dpto != null) {
                                                cmtDireccionDetalladaMglDto.setDepartamento(dpto.getGpoNombre());
                                            }
                                        }
                                    }
                                }
                                cmtDireccionDetalladaMglDto.setConfiabilidad(dir.getDirConfiabilidad() != null ? dir.getDirConfiabilidad() : BigDecimal.ONE);
                                //Busqueda cmt Direccion
                                CmtDireccionMgl direccionCm = cmtDireccionMglFacadeLocal.
                                        findCmtIdDireccion(dir.getDirId());
                                if (direccionCm != null && direccionCm.getCuentaMatrizObj() != null) {
                                    CmtCuentaMatrizMgl cuenta = direccionCm.getCuentaMatrizObj();
                                    cmtDireccionDetalladaMglDto.setTipoDireccion("CCMM");
                                    cmtDireccionDetalladaMglDto.setCuentaMatrizMgl(cuenta);
                                } else {
                                    cmtDireccionDetalladaMglDto.setTipoDireccion("UNIDAD");
                                }
                            }
                            //Busqueda cmt Direccion

                            //Busco si la direccion tiene una factibilidad  vigente List
                            List<FactibilidadMgl> factibilidadVig
                                    = factibilidadMglFacadeLocal.findFactibilidadVigByDetallada(cmtDireccionDetalladaMglDto.getDireccionDetalladaId(), new Date());

                            if (factibilidadVig != null && !factibilidadVig.isEmpty()) {
                                FactibilidadMgl factibilidadMgl = factibilidadVig.get(0);
                                cmtDireccionDetalladaMglDto.setFactibilidadId(factibilidadMgl.getFactibilidadId());
                            }
                            //Fin Busqueda si la direccion tiene una factibilidad  vigente

                            if (cmtDireccionDetalladaMglDto.getLongitudDir() != null
                                    && cmtDireccionDetalladaMglDto.getLatitudDir() != null) {

                                String latitudMod1 = cmtDireccionDetalladaMglDto.getLatitudDir().replace(",", ".");
                                String longitudMod1 = cmtDireccionDetalladaMglDto.getLongitudDir().replace(",", ".");
                                if ((latitudMod1.trim().equalsIgnoreCase(coordinatesDto.getLatitude().trim()))
                                        && (longitudMod1.equalsIgnoreCase(coordinatesDto.getLongitude()))) {
                                    dirIguales.add(cmtDireccionDetalladaMglDto);
                                } else {
                                    dirDistintas.add(cmtDireccionDetalladaMglDto);
                                }
                            }

                        }

                        if (!dirIguales.isEmpty()) {
                            direccionDetalladaList.addAll(dirIguales);
                        } else {
                            direccionDetalladaList.addAll(dirDistintas);
                        }
                    }
                }
            }
            if (direccionDetalladaList == null || direccionDetalladaList.isEmpty()) {
                msnError = "No se encontraron resultados en la búsqueda.";
                alerts();
            } else {
                verListadoDirecciones = true;
                mostrarBotonAtras = true;
                showPanelCoordenadas = false;
            }
        } else {
            msnError = "Adiccione al menos una coordenada para la busqueda"
                    + " por favor. ";
            alerts();
        }
    }

    public void agregarDireccion() throws Exception {

        try {
            if (validarEstructuraDireccion(drDireccion, tipoDireccion)) {
                responseConstruirDireccion.setDireccionStr(direccionLabel);
                responseConstruirDireccion.setDrDireccion(drDireccion);
                responseConstruirDireccion.setNombreDpto(dptoSelected.getGpoNombre());
                responseConstruirDireccion.setNombreCity(ciudadSelected.getGpoNombre());
                responseConstruirDireccion.setNombreCentro(centroPobladoSelected.getGpoNombre());
                PrimeFaces.current().executeScript("PF('dlg2').hide();");
            }

        } catch (Exception e) {
            notGeoReferenciado = false;
            PrimeFaces.current().executeScript("PF('dlg2').show();");
            String msnError = e.getMessage();
        }

    }

    public CmtConfigurationAddressComponentDto
    parserConfigurationAddressComponentToCmtConfigurationAddressComponentDto(ConfigurationAddressComponent configurationAddressComponent) {

        CmtConfigurationAddressComponentDto cmtConfigurationAddressComponentDto
                = new CmtConfigurationAddressComponentDto();
        cmtConfigurationAddressComponentDto.setAptoValues(configurationAddressComponent.getAptoValues());
        cmtConfigurationAddressComponentDto.setBmValues(configurationAddressComponent.getBmValues());
        cmtConfigurationAddressComponentDto.setCkValues(configurationAddressComponent.getCkValues());
        cmtConfigurationAddressComponentDto.setComplementoValues(configurationAddressComponent.getComplementoValues());
        cmtConfigurationAddressComponentDto.setItValues(configurationAddressComponent.getItValues());
        cmtConfigurationAddressComponentDto.setMessage(espacio);
        cmtConfigurationAddressComponentDto.setMessageType(espacio);
        cmtConfigurationAddressComponentDto.setTiposDireccion(configurationAddressComponent.getTiposDireccion());
        return cmtConfigurationAddressComponentDto;
    }

    public void factibilizarDireccion(SelectEvent<CmtDireccionDetalladaMglDto> event)
            throws MalformedURLException, Exception {
        assignDefaultDetailsToPopup();
        renderMenuOpciones();
        if (this.direccionDetalladaMglDtoSel != null) {
            this.mostrarMapaByCoordenadasDirSelected(this.direccionDetalladaMglDtoSel);

            //JDHT obtener divisional del nodo del centro poblado de la CCMM
            if (direccionDetalladaMglDtoSel.getDireccionMgl() != null
                    && direccionDetalladaMglDtoSel.getDireccionMgl().getUbicacion() != null
                    && direccionDetalladaMglDtoSel.getDireccionMgl().getUbicacion().getGpoIdObj() != null) {
                NodoMglManager nodoManager = new NodoMglManager();

                List<NodoMgl> nodoMglCentroPobladoList
                        = nodoManager.find5NodosByCentroPobladoList(direccionDetalladaMglDtoSel.getDireccionMgl().getUbicacion().getGpoIdObj().getGpoId());
                if (nodoMglCentroPobladoList != null && !nodoMglCentroPobladoList.isEmpty()) {
                    if (nodoMglCentroPobladoList.get(0).getDisId() != null) {
                        direccionDetalladaMglDtoSel.setDivisionalNodo(nodoMglCentroPobladoList.get(0).getDivId().getNombreBasica());
                    } else {
                        direccionDetalladaMglDtoSel.setDivisionalNodo("Divisional No Asociado");
                    }
                }
                if (direccionDetalladaMglDtoSel != null && direccionDetalladaMglDtoSel.getDireccionDetalladaId() != null) {
                    //Consulto las factibilidades vigentes y no vigentes de esa direccion  
                    List<FactibilidadMgl> factibilidadMgls
                            = factibilidadMglFacadeLocal.findFactibilidadByDirDetallada(direccionDetalladaMglDtoSel.getDireccionDetalladaId());
                    if (factibilidadMgls != null && !factibilidadMgls.isEmpty()) {
                        detalleFactibilidadLogDto = new ArrayList<>();

                        for (FactibilidadMgl factibilidadMgl : factibilidadMgls) {
                            if (factibilidadMgl.getFactibilidadId().intValueExact() == direccionDetalladaMglDtoSel.getFactibilidadId().intValueExact()) {
                                Date dateFactibilidad = factibilidadMgl.getFechaCreacion();
                                DateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
                                DateFormat hora = new SimpleDateFormat("hh:mm aa");
                                String str1 = fecha.format(dateFactibilidad);
                                String[] parts = str1.split("-");
                                String mes = retornaMes(parts[1]);
                                fechaCreacionFactibilidad = parts[0] + "-" + mes + "-" + parts[2];
                                horaCreacionFactibilidad = hora.format(dateFactibilidad);
                            }
                        }

                        if (session.getAttribute("consulta") != null) {
                            VisorFactibilidadManager visorFactibilidadManager = new VisorFactibilidadManager();
                            VisorFactibilidad visorFactibilidad = new VisorFactibilidad();
                            visorFactibilidad.setVisorFactibilidadId(BigDecimal.valueOf(direccionDetalladaMglDtoSel.getFactibilidadId().longValue()));
                            visorFactibilidad.setCodigo(String.valueOf(session.getAttribute("consulta")));
                            visorFactibilidad.setFechaCreacion(new Date());
                            visorFactibilidadManager.update(visorFactibilidad);
                        }
                    }
                }
            }
        } else {
            msnError = "No ha seleccionado una direccion para calcular su factibilidad.";
            alerts();
        }
    }

    public Date fechaVencimiento(Date fecha, int diasAsumar) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, diasAsumar);
        return calendar.getTime();
    }

    public String returnDirCcmm(CmtCuentaMatrizMgl ccmm)
            throws ApplicationException {

        String direccion = "N.N";
        if (ccmm != null && ccmm.getDireccionPrincipal()
                != null && ccmm.getDireccionPrincipal().getDireccionObj() != null) {
            direccion = ccmm.getDireccionPrincipal().getDireccionObj().getDirNostandar();
        }
        return direccion;
    }

    public void cargarDireccionByFactibilidad(FactibilidadMgl factibilidadMgl) throws ApplicationException {
        assignDefaultDetailsToPopup();
        responseConstruirDireccion = new ResponseConstruccionDireccion();
        CmtDireccionDetalladaMgl detalladaMgl = cmtDireccionDetalleMglFacadeLocal.findById(factibilidadMgl.getDireccionDetalladaId());

        if (detalladaMgl != null && detalladaMgl.getDireccion() != null) {
            DireccionMgl direccionMgl = detalladaMgl.getDireccion();
            UbicacionMgl ubicacionMgl = detalladaMgl.getDireccion().getUbicacion() != null
                    ? detalladaMgl.getDireccion().getUbicacion() : null;
            GeograficoPoliticoMgl centro;
            if (ubicacionMgl != null) {
                centro = ubicacionMgl.getGpoIdObj();
                if (centro != null) {
                    responseConstruirDireccion.setNombreCentro(centro.getGpoNombre());
                    GeograficoPoliticoMgl ciudad = geograficoPoliticoFacadeLocal.findById(centro.getGeoGpoId());
                    if (ciudad != null) {
                        responseConstruirDireccion.setNombreCity(ciudad.getGpoNombre());
                        GeograficoPoliticoMgl dpto = geograficoPoliticoFacadeLocal.findById(ciudad.getGeoGpoId());
                        if (dpto != null) {
                            responseConstruirDireccion.setNombreDpto(dpto.getGpoNombre());
                        }
                    }
                    responseConstruirDireccion.setDetalladaMgl(detalladaMgl);
                    responseConstruirDireccion.setDireccionStr(direccionMgl.getDirNostandar());

                    int control = 0;
                    for (ResponseConstruccionDireccion response : lstResponseConstruirDireccion) {
                        if ((response.getDireccionStr().equalsIgnoreCase(responseConstruirDireccion.getDireccionStr()))
                                && (response.getNombreCentro().equalsIgnoreCase(responseConstruirDireccion.getNombreCentro()))
                                && (response.getNombreCity().
                                equalsIgnoreCase(responseConstruirDireccion.getNombreCity()))) {
                            control++;
                        }
                    }

                    if (control > 0) {
                        msnError = "Ya existe en lista una dirección igual a la que intenta agregar.";
                        alerts();
                    } else {
                        lstResponseConstruirDireccion.add(responseConstruirDireccion);
                        paramsConsulta.put(responseConstruirDireccion, centro.getGpoId());
                        limpiarBusquedaDir();
                    }
                }
            }
        }

    }

    public void cargarDireccionByFactibilidadCoordenadas(FactibilidadMgl factibilidadMgl) throws ApplicationException {
        assignDefaultDetailsToPopup();
        CmtDireccionDetalladaMgl detalladaMgl = cmtDireccionDetalleMglFacadeLocal.findById(factibilidadMgl.getDireccionDetalladaId());

        String desviacion = this.consultarParametro(Constants.DESVIACION_EN_METROS);

        if (desviacion != null && !desviacion.isEmpty()) {
            if (isNumeric(desviacion)) {
                int desvMts = Integer.parseInt(desviacion);

                if (detalladaMgl != null && detalladaMgl.getDireccion() != null) {
                    UbicacionMgl ubicacionMgl = detalladaMgl.getDireccion().getUbicacion() != null
                            ? detalladaMgl.getDireccion().getUbicacion() : null;

                    if (ubicacionMgl != null) {
                        String latitudMod = ubicacionMgl.getUbiLatitud().replace(",", ".");
                        String longitudMod = ubicacionMgl.getUbiLongitud().replace(",", ".");
                        if (!latitudMod.equalsIgnoreCase("0") && !longitudMod.equalsIgnoreCase("0")) {
                            coordenadaAgregar = new CmtRequestHhppByCoordinatesDto();
                            coordenadaAgregar.setLongitude(longitudMod);
                            coordenadaAgregar.setLatitude(latitudMod);

                            if (ubicacionMgl.getGpoIdObj() != null) {
                                coordenadaAgregar.setNombreCentro(ubicacionMgl.getGpoIdObj().getGpoNombre());
                                coordenadaAgregar.setCiudad(ubicacionMgl.getGpoIdObj().getGeoCodigoDane());
                                GeograficoPoliticoMgl ciudad = geograficoPoliticoFacadeLocal.findById(ubicacionMgl.getGpoIdObj().getGeoGpoId());
                                if (ciudad != null) {
                                    coordenadaAgregar.setNombreCiudad(ciudad.getGpoNombre());
                                    GeograficoPoliticoMgl dpto = geograficoPoliticoFacadeLocal.findById(ciudad.getGeoGpoId());
                                    if (dpto != null) {
                                        coordenadaAgregar.setNombreDpto(dpto.getGpoNombre());
                                    }

                                }
                                coordenadaAgregar.setUnitsNumber(MAX_UNITS_NUMBER);
                                coordenadaAgregar.setDeviationMtr(desvMts);
                                coordenadaAgregar.setDetalladaMgl(detalladaMgl);

                                if (!requestCoordenadas.isEmpty()) {
                                    int control = 0;
                                    for (CmtRequestHhppByCoordinatesDto coordenadas : new ArrayList<>(requestCoordenadas)) {
                                        if ((coordenadas.getLongitude().equalsIgnoreCase(coordenadaAgregar.getLongitude()))
                                                && (coordenadas.getLatitude().equalsIgnoreCase(coordenadaAgregar.getLatitude()))) {
                                            control++;
                                        }
                                    }
                                    if (control > 0) {
                                        msnError = "Ya existe en lista una dirección igual a la que intenta agregar.";
                                        alerts();
                                    } else {
                                        requestCoordenadas.add(coordenadaAgregar);
                                    }
                                } else {
                                    requestCoordenadas.add(coordenadaAgregar);
                                }
                            }
                        } else {
                            msnError = "La direccion no posee informacion de  Latitud y Longitud";
                            alerts();
                        }
                    }
                }

            } else {
                msnError = "El valor parametrizado para la desviacion en metros no es numerico";
                alerts();
            }
        } else {
            msnError = "No existe una configuracion para el parametro: " + Constants.DESVIACION_EN_METROS + "";
            alerts();
        }
    }

    private String retornaMes(String numMes) {

        String returMes;

        int numberMes = Integer.parseInt(numMes);

        switch (numberMes) {
            case 1:
                returMes = "Enero";
                break;
            case 2:
                returMes = "Febrero";
                break;
            case 3:
                returMes = "Marzo";
                break;
            case 4:
                returMes = "Abril";
                break;
            case 5:
                returMes = "Mayo";
                break;
            case 6:
                returMes = "Junio";
                break;
            case 7:
                returMes = "Julio";
                break;
            case 8:
                returMes = "Agosto";
                break;
            case 9:
                returMes = "Septiembre";
                break;
            case 10:
                returMes = "Octubre";
                break;
            case 11:
                returMes = "Noviembre";
                break;
            case 12:
                returMes = "Diciembre";
                break;
            default:
                returMes = "Invalid month";
                break;
        }
        return returMes;
    }

    /**
     * Función utilizada para hacer el rediccionamiento al detalle de la cuenta
     * matriz
     *
     * @throws
     * @author Victor Bocanegra
     */
    public void irCuentaMatrizDetalle() {
        assignDefaultDetailsToPopup();

        try {

            if (ccmmSeleccionada != null && ccmmSeleccionada.getEstadoRegistro() == 1) {

                CuentaMatrizBean cuentaMatrizBean = (CuentaMatrizBean) JSFUtil.getBean("cuentaMatrizBean");
                cuentaMatrizBean.setCuentaMatriz(this.ccmmSeleccionada);
                ConsultaCuentasMatricesBean consultaCuentasMatricesBean = (ConsultaCuentasMatricesBean) JSFUtil.getSessionBean(ConsultaCuentasMatricesBean.class);
                consultaCuentasMatricesBean.goCuentaMatrizEstadosSol(cuentaMatrizBean);

            } else {
                msnError = "La cuenta Matriz se encuentra en un estado no válido para su redirecionamiento";
                alerts();

            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irCuentaMatrizDetalle: " + e.getMessage() + " ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para hacer el rediccionamiento al detalle del Hhpp
     *
     * @throws
     * @author Victor Bocanegra
     */
    public void irCuentaHhppDetalle(HhppMgl hhppSlected) {
        assignDefaultDetailsToPopup();

        try {
            HhppDetalleSessionBean hhppDetalleSessionBean
                    = (HhppDetalleSessionBean) JSFUtil.getBean("hhppDetalleSessionBean");

            if (hhppSlected != null) {
                // Instacia Bean de Session para obtener el hhpp seleccionado 
                CmtDireccionDetalladaMgl detalladaMgl = cmtDireccionDetalleMglFacadeLocal.findByHhPP(hhppSlected);
                hhppDetalleSessionBean.setHhppSeleccionado(hhppSlected);
                hhppDetalleSessionBean.setDireccionDetalladaSeleccionada(detalladaMgl);
                FacesUtil.navegarAPagina("/view/MGL/VT/moduloHhpp/detalleHhpp.jsf");
            } else {
                msnError = "No hay un hhpp asociado a la unidad existente";
                alerts();
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irCuentaHhppDetalle: " + e.getMessage() + " ", e, LOGGER);
        }

    }

    /**
     * Función utilizada para hacer el rediccionamiento al detalle del Hhpp desde factibilizar
     * @param hhppSlected 
     * @throws
     * @author ramirezcamp
     */
    public void irCuentaHhppDetalleFact(HhppMgl hhppSlected) {
        assignDefaultDetailsToPopup();

        try {
            HhppDetalleSessionBean hhppDetalleSessionBean
                    = (HhppDetalleSessionBean) JSFUtil.getBean("hhppDetalleSessionBean");

            if (hhppSlected != null) {
                hhppSlected.setDetalleHhppFact(true);
                CmtDireccionDetalladaMgl detalladaMgl = cmtDireccionDetalleMglFacadeLocal.findByHhPP(hhppSlected);
                hhppDetalleSessionBean.setHhppSeleccionado(hhppSlected);
                hhppDetalleSessionBean.setDireccionDetalladaSeleccionada(detalladaMgl);
                
                detalleHhppBean
                    = (DetalleHhppBean) JSFUtil.getBean("detalleHhppBean");
                detalleHhppBean.cargarHhppSeleccionada();
                
                PrimeFaces current = PrimeFaces.current();
                PrimeFacesUtil.update("@widgetVar(dlgDetalleHhppFact)");
                current.executeScript("PF('dlgDetalleHhppFact').show();");
            } else {
                msnError = "No hay un hhpp asociado a la unidad existente";
                alerts();
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irCuentaHhppDetalle: " + e.getMessage() + " ", e, LOGGER);
        }

    }

    /**
     * Función utilizada para hacer el rediccionamiento al detalle del Hhpp
     *
     * @throws
     * @author Victor Bocanegra
     */
    public void irCuentaHhppDetalleByDir(CmtDireccionDetalladaMglDto direccion) {

        try {
            HhppDetalleSessionBean hhppDetalleSessionBean
                    = (HhppDetalleSessionBean) JSFUtil.getBean("hhppDetalleSessionBean");

            if (direccion != null) {
                // Instacia Bean de Session para obtener el hhpp seleccionado 
                CmtDireccionDetalladaMgl detalladaMgl = cmtDireccionDetalleMglFacadeLocal.findById(direccion.getDireccionDetalladaId());
                if (detalladaMgl != null) {
                    SubDireccionMgl subDireccionMgl = null;
                    if (detalladaMgl.getSubDireccion() != null) {
                        subDireccionMgl = detalladaMgl.getSubDireccion();
                    }

                    List<HhppMgl> hhppSlected = hhppMglFacadeLocal.findByDirAndSubDir(detalladaMgl.getDireccion(), subDireccionMgl);
                    if (hhppSlected != null && !hhppSlected.isEmpty()) {
                        hhppDetalleSessionBean.setHhppSeleccionado(hhppSlected.get(0));
                    }
                }
                hhppDetalleSessionBean.setDireccionDetalladaSeleccionada(detalladaMgl);
                FacesUtil.navegarAPagina("/view/MGL/VT/moduloHhpp/detalleHhpp.jsf");
            } else {
                error("No hay un hhpp asociado a la unidad existente");
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irCuentaHhppDetalleByDir: " + e.getMessage() + " ", e, LOGGER);
        }

    }

    /**
     * Función utilizada para hacer el rediccionamiento al horario de la cm
     *
     * @author cardenaslb
     */
    public void irCuentaMatrizHorario() {
        assignDefaultDetailsToPopup();

        try {

            if (ccmmSeleccionada != null && ccmmSeleccionada.getEstadoRegistro() == 1) {

                CuentaMatrizBean cuentaMatrizBean = (CuentaMatrizBean) JSFUtil.getBean("cuentaMatrizBean");
                cuentaMatrizBean.setCuentaMatriz(this.ccmmSeleccionada);
                ConsultaCuentasMatricesBean consultaCuentasMatricesBean = (ConsultaCuentasMatricesBean) JSFUtil.getSessionBean(ConsultaCuentasMatricesBean.class);
                consultaCuentasMatricesBean.goCuentaHorarioCcmm(cuentaMatrizBean);

            } else {
                msnError = "La cuenta Matriz se encuentra en un estado no válido para su redirecionamiento";
                alerts();
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irCuentaMatrizHorario: " + e.getMessage() + " ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para hacer el rediccionamiento a la solicitud de
     * creacion de cm
     *
     * @author cardenaslb
     */
    public void irSolicitudCuentaMatriz() {
        try {

            EncabezadoSolicitudModificacionCMBean encabezadoSolicitudModCMBean = JSFUtil.getSessionBean(EncabezadoSolicitudModificacionCMBean.class);
            CuentaMatrizBean cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            cuentaMatrizBean.setCuentaMatriz(ccmmSeleccionada);
            String pagina = encabezadoSolicitudModCMBean.ingresarSolicitudCrearCm();
            if (direccionDetalladaMglDtoSel != null) {
                session.setAttribute("direccionDeta", direccionDetalladaMglDtoSel);
            }
            FacesUtil.navegarAPagina(pagina + ".jsf");

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irSolicitudCuentaMatriz: " + e.getMessage() + " ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irSolicitudCuentaMatriz: " + e.getMessage() + " ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para hacer el rediccionamiento a la solicitud de
     * modificacion de la cm
     *
     * @author cardenaslb
     */
    public void irSolicitudModCuentaMatriz() {
        assignDefaultDetailsToPopup();

        try {

            if (ccmmSeleccionada != null && ccmmSeleccionada.getEstadoRegistro() == 1) {
                EncabezadoSolicitudModificacionCMBean encabezadoSolicitudModCMBean = JSFUtil.getSessionBean(EncabezadoSolicitudModificacionCMBean.class);
                CuentaMatrizBean cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
                cuentaMatrizBean.setCuentaMatriz(ccmmSeleccionada);
                String pagina = encabezadoSolicitudModCMBean.ingresarSolicitudModificacionCm();
                FacesUtil.navegarAPagina(pagina + ".jsf");

            } else {
                msnError = "La cuenta Matriz se encuentra en un estado no válido para su redirecionamiento";
                alerts();

            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irSolicitudModCuentaMatriz: " + e.getMessage() + " ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para hacer el rediccionamiento a la solicitud de
     * creacion y modificacion de la cm
     *
     * @author cardenaslb
     */
    public void irSolCreacionModHhppCuentaMatriz() {
        assignDefaultDetailsToPopup();

        try {

            if (ccmmSeleccionada != null && ccmmSeleccionada.getEstadoRegistro() == 1) {
                EncabezadoSolicitudModificacionCMBean encabezadoSolicitudModCMBean;
                encabezadoSolicitudModCMBean = JSFUtil.getSessionBean(EncabezadoSolicitudModificacionCMBean.class);
                CuentaMatrizBean cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
                cuentaMatrizBean.setCuentaMatriz(ccmmSeleccionada);
                String pagina = encabezadoSolicitudModCMBean.ingresarSolicitudHhpp();
                FacesUtil.navegarAPagina(pagina + ".jsf");

            } else {
                msnError = "La cuenta Matriz se encuentra en un estado no válido para su redirecionamiento";
                alerts();
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irSolCreacionModHhppCuentaMatriz: " + e.getMessage() + " ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irSolCreacionModHhppCuentaMatriz: " + e.getMessage() + " ", e, LOGGER);
        }

    }

    /**
     * Función utilizada para hacer el rediccionamiento a la solicitud de
     * creacion de Vt en cm
     *
     * @author cardenaslb
     */
    public void irSolModVtCuentaMatriz() {
        assignDefaultDetailsToPopup();

        try {

            if (ccmmSeleccionada != null && ccmmSeleccionada.getEstadoRegistro() == 1) {

                EncabezadoSolicitudModificacionCMBean encabezadoSolicitudModCMBean;
                encabezadoSolicitudModCMBean = JSFUtil.getSessionBean(EncabezadoSolicitudModificacionCMBean.class);
                encabezadoSolicitudModCMBean.setModoGestion(false);
                CuentaMatrizBean cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
                cuentaMatrizBean.setCuentaMatriz(ccmmSeleccionada);
                String pagina = encabezadoSolicitudModCMBean.ingresarSolicitudVt();
                if (pagina != null && !pagina.isEmpty()) {
                    FacesUtil.navegarAPagina(pagina + ".jsf");
                } else {
                    msnError = "Estado de la CM NO Valido para la creacion de la Solicitud";
                    alerts();
                }
            } else {
                msnError = "Ud debe factibilizar una direccion de CCMM";
                alerts();

            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irSolModVtCuentaMatriz: " + e.getMessage() + "", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irSolModVtCuentaMatriz: " + e.getMessage() + " ", e, LOGGER);
        }

    }
    /**
     * Función utilizada para hacer el rediccionamiento a la solicitud de
     * creacion de hhpp
     *
     * @author cardenaslb
     */
    public void irSolCreacionHHPP() {
        assignDefaultDetailsToPopup();

           try {
            if (direccionDetalladaMglDtoSel != null
                    && direccionDetalladaMglDtoSel.getTipoDireccion().equalsIgnoreCase("UNIDAD")) {
                session.setAttribute("direccionDeta", direccionDetalladaMglDtoSel);
                FacesUtil.navegarAPagina("/view/MGL/VT/solicitudes/DTH/crearSolicitudHhppDth.jsf");
            } else if (direccionDetalladaMglDtoSel != null
                    && direccionDetalladaMglDtoSel.getTipoDireccion().equalsIgnoreCase("CCMM")) {
                msnError = "La direccion es tipo CCMM y no se puede"
                        + " redirigir al modulo de creacion solicitud HHPP";
                alerts();
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irSolCreacionHHPP: " + e.getMessage() + "", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irSolCreacionHHPP: " + e.getMessage() + "", e, LOGGER);
        }

    }

    /**
     * Función utilizada para hacer el rediccionamiento a la solicitud de
     * validacion de cobertura
     *
     * @author cardenaslb
     */
    public void irSolValidarCobertura() {
        assignDefaultDetailsToPopup();

        try {
            if (direccionDetalladaMglDtoSel != null) {
                BigDecimal detalladaMglId = direccionDetalladaMglDtoSel.getDireccionDetalladaId();
                CmtDireccionDetalladaMgl detalladaMgl = cmtDireccionDetalleMglFacadeLocal.findById(detalladaMglId);
                UbicacionMgl ubicacionMgl = detalladaMgl.getDireccion().getUbicacion() != null
                        ? detalladaMgl.getDireccion().getUbicacion() : null;
                centroPobladoSolSelected = ubicacionMgl != null ? ubicacionMgl.getGpoIdObj() : null;
                if (centroPobladoSolSelected != null) {
                    ciudadSolSelected = geograficoPoliticoFacadeLocal.findById(centroPobladoSolSelected.getGeoGpoId());
                    if (ciudadSolSelected != null) {
                        dptoSolSelected = geograficoPoliticoFacadeLocal.findById(ciudadSolSelected.getGeoGpoId());
                    }
                }
                listaTecnlogias = cmtBasicaMglFacadeLocal.findTecnolosgias();
                CmtTipoBasicaMgl tipoBasicaDivicional;
                tipoBasicaDivicional = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_DIVICIONAL_COMERCIAL_TELMEX);
                listBasicaDivisional = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaDivicional);
                mostrarValidacionCobertura = true;
                panelDetalleCobertura = true;
                showPanelBusquedaDireccion = false;
                showPanelIngresarUbicacion = false;
                showPanelIngresarGPS = false;
                showPanelCoordenadas = false;
                verListadoDirecciones = false;
                verListadoLogDirecciones = false;
                dirIsCcmm = false;
                mostrarMapa = false;
                mostrarMapaCober = true;
                mostrarHistorico = false;
                if (usuarioLogin != null && usuarioLogin.getEmail() != null
                        && !usuarioLogin.getEmail().isEmpty()) {
                    correoUsuario = usuarioLogin.getEmail();
                }
                correoUsuarioCopia = null;
                telefono = null;
                divisional = null;
                tecnologia = null;
                observacionesSolicitudValCobertura = "";

            } else {
                msnError = "Ud debe factibilizar una direccion antes de realizar una Solicitud de Cobertura";
                alerts();

            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irSolValidarCobertura: " + e.getMessage() + "", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irSolValidarCobertura: " + e.getMessage() + "", e, LOGGER);
        }

    }

    /**
     * Función utilizada creacion de solicitud de validacion de cobertura
     *
     * @author cardenaslb
     */
    public void creaSolicitudValCobertura() {
        assignDefaultDetailsToPopup();

        try {
            Solicitud solicitud = new Solicitud();
            if (direccionDetalladaMglDtoSel != null && direccionDetalladaMglDtoSel.getTipoDireccion() != null) {
                BigDecimal detalladaMglId = direccionDetalladaMglDtoSel.getDireccionDetalladaId();
                CmtDireccionDetalladaMgl detalladaMgl = cmtDireccionDetalleMglFacadeLocal.findById(detalladaMglId);
                if (!validarSolicitudes()) {
                    msnError = "Ud ya superó el número de solicitudes mensuales";
                    alerts();
                    return;
                }
                if (usuarioLogin == null) {
                    msnError = "El usuario no se encuentra logueado!";
                    alerts();
                    return;

                }
                if (residencial && tecnologia == null) {
                    msnError = "La tecnología es un campo Obligatorio";
                    alerts();
                    return;

                }
                List<Solicitud> solicitudEnCursoList = solicitudEnCurso(detalladaMgl,
                        centroPobladoSolSelected.getGpoId(), tecnologia,
                        Constant.TIPO_SOLICITUD_VALIDACION_COBERTURA);
                if (residencial && solicitudEnCursoList != null && !solicitudEnCursoList.isEmpty()) {
                    msnError = "Ya existe una solicitud en curso que se encuentra PENDIENTE.";
                    alerts();
                    return;
                } else {
                    if (corporativo && divisional == null) {
                        msnError = "La divisional es un campo Obligatorio";
                        alerts();
                        return;

                    }
		     if (corporativo && tecnologia == null) {
                        msnError = "La tecnologia es un campo Obligatorio";
                        alerts();
                        return;

                    }
                    if (corporativo && tipoSolicitud == null) {
                        msnError = "La Tipo de Solicitud es un campo Obligatorio";
                        alerts();
                        return;

                    }
                    if (corporativo && tipoServicio == null) {
                        msnError = "La Tipo de Servicio es un campo Obligatorio";
                        alerts();
                        return;

                    }
                    if (corporativo && tipoValidacion == null) {
                        msnError = "La Tipo de Validacion es un campo Obligatorio";
                        alerts();
                        return;

                    }
                    if (corporativo && bw == null) {
                        msnError = "La bw es un campo Obligatorio";
                        alerts();
                        return;

                    }
                    if (corporativo && tipoSitio == null) {
                        msnError = "La tipo de Sitio es un campo Obligatorio";
                        alerts();
                        return;

                    }
                    if (telefono == null || telefono.isEmpty()) {
                        msnError = "El teléfono es un campo Obligatorio";
                        alerts();
                        return;

                    }

                    if (correoUsuarioCopia != null
                            && !correoUsuarioCopia.trim().isEmpty()) {
                        if (correoUsuarioCopia.length() > 100) {
                            msnError = "El campo correo copia no debe exceder de 100 caracteres";
                            alerts();
                            return;
                        }
                    }
                    if (correoUsuarioCopia != null
                            && !correoUsuarioCopia.trim().isEmpty()) {
                        if (!validarCorreo(correoUsuarioCopia)) {
                            msnError = "El campo correo copia no tiene el formato requerido";
                            alerts();
                            return;
                        } else {
                            if (correoUsuarioCopia != null && !correoUsuarioCopia.isEmpty()) {
                                if (!validarDominioCorreos(correoUsuarioCopia)) {
                                    msnError = "El campo correo copia debe ser un correo con dominio telmexla.com, telmex.com.co, telmex.com, comcel.com.co o claro.com.co";
                                    alerts();
                                    return;
                                }
                            }
                        }
                    }

                    solicitud.setCentroPobladoId(centroPobladoSolSelected.getGpoId());
                    solicitud.setUsuario(usuarioLogin.getUsuario());
                    solicitud.setCorreo(correoUsuario);
                    solicitud.setCorreoCopia(correoUsuarioCopia);
                    solicitud.setIdFactibilidad(direccionDetalladaMglDtoSel.getFactibilidadId());
                    solicitud.setIdBasicaDivi(cmtBasicaMglFacadeLocal.findById(divisional));
                    solicitud.setCoordX(direccionDetalladaMglDtoSel.getLatitudDir());
                    solicitud.setCoordY(direccionDetalladaMglDtoSel.getLongitudDir());
                    solicitud.setDireccionDetallada(detalladaMgl);
                    solicitud.setDireccion(detalladaMgl.getDireccionTexto());
                    solicitud.setContacto(usuarioLogin.getNombre());
                    solicitud.setSolicitante(usuarioLogin.getNombre());
                    solicitud.setTelContacto(telefono);
                    if (residencial) {
                        solicitud.setTecnologiaId(cmtBasicaMglFacadeLocal.findById(tecnologia));
                    }
                    if (corporativo) {
                        solicitud.setTipoDeSolicitud(buscarTipoSolicitud());
                        solicitud.setTecnologiaId(cmtBasicaMglFacadeLocal.findById(tecnologia));
                        solicitud.setTipoDeServicio(tipoServicio);
                        solicitud.setTipoDeValidacion(tipoValidacion);
                        solicitud.setBw(bw);
                        solicitud.setTipoDeSitio(tipoHhppMglFacadeLocal.findTipoHhppMglById(tipoSitio).getThhValor());
                    }
                    CmtBasicaMgl basicaMglEstado;
                    basicaMglEstado = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE);
                    solicitud.setEstado(basicaMglEstado.getNombreBasica());
                    solicitud.setCambioDir(Constant.TIPO_SOLICITUD_VALIDACION_COBERTURA);
                    solicitud.setTipoSol("VALIDACION DE COBERTURA");
                    solicitud.setFechaIngreso(new Date());
                    solicitud.setCuentaMatriz("0");
                    solicitud.setDisponibilidadGestion("0");
                    solicitud.setEstadoRegistro(1);
                    solicitud.setTipo("VALIDACION_DE_CORBERTURA");
                    solicitud.setMotivo(observacionesSolicitudValCobertura);
                    if (direccionDetalladaMglDtoSel != null
                            && direccionDetalladaMglDtoSel.getCuentaMatrizMgl() != null) {
                        solicitud.setCuentaMatriz(direccionDetalladaMglDtoSel.getCuentaMatrizMgl().getNombreCuenta().trim());
                    }

                    solicitud = solicitudFacadeLocal.create(solicitud);
                    if (solicitud != null) {
                        guardarTiempoSolicitud(solicitud);
                        msnError = "La solicitud  VALIDACIÓN DE COBERTURA con número "
                                + solicitud.getIdSolicitud() + "' fue creada con exito.";
                        alertProcesoExistoso();

                    }
                    enviarCorreoSolicitud(solicitud);
                }
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al intentar crear la solicitud de validación de cobertura " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al intentar crear la solicitud de validación de cobertura " + e.getMessage(), e, LOGGER);
        }
    }

    public List<Solicitud> solicitudEnCurso(CmtDireccionDetalladaMgl dirDet, BigDecimal centroPoblado,
                                            BigDecimal tecnologia, String tipoSol) {

        try {
            return solicitudFacadeLocal.solictudesHhppEnCurso(dirDet.getDireccionDetalladaId(), centroPoblado, tecnologia, tipoSol);
        } catch (ApplicationException ex) {
            LOGGER.error("Se genero error en solicitudEnCurso de ConsultarCoberturasV1Bean class ...", ex);
        }
        return null;
    }

    public void guardarTiempoSolicitud(Solicitud solicitud) {

        try {
            CmtTiempoSolicitudMgl cmtTiempoSolicitudMgl = new CmtTiempoSolicitudMgl();
            cmtTiempoSolicitudMgl.setArchivoSoporte("NA");
            cmtTiempoSolicitudMgl.setEstadoRegistro(1);
            cmtTiempoSolicitudMgl.setFechaCreacion(new Date());
            cmtTiempoSolicitudMgl.setPerfilCreacion(perfil);
            cmtTiempoSolicitudMgl.setTiempoEspera("00:00:00");
            cmtTiempoSolicitudMgl.setTiempoGestion("00:00:00");
            cmtTiempoSolicitudMgl.setTiempoSolicitud("00:00:00");
            cmtTiempoSolicitudMgl.setTiempoTotal("00:00:00");
            cmtTiempoSolicitudMgl.setUsuarioCreacion(usuarioSesion);
            cmtTiempoSolicitudMgl.setSolicitudObj(solicitud);
            cmtTiempoSolicitudMglFacadeLocal.setUser(usuarioSesion, perfil);
            cmtTiempoSolicitudMglFacadeLocal.create(cmtTiempoSolicitudMgl);
        } catch (ApplicationException ex) {
            LOGGER.error("Se genero error en solicitudEnCurso de ConsultarCoberturasV1Bean class ...", ex);
        }

    }

    private boolean validarCorreo(String correo) {
        boolean respuesta = false;
        try {
            String mail = "([\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z])?";
            Pattern pattern = Pattern.compile(mail);
            Matcher matcher = pattern.matcher(correo);
            respuesta = matcher.matches();
        } catch (Exception e) {
            LOGGER.error("Error en validarCorreo de InfoCreaCMBean: " + e);
        }
        return respuesta;
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

    public void volverPanelValidacionCobertura() {

        switch (opcionSeleccionada) {
            case 2:
                showPanelIngresarUbicacion = true;
                verListadoDirecciones = false;
                verListadoLogDirecciones = false;
                mostrarValidacionCobertura = false;
                mostrarMapaCober = false;
                break;
            case 4:
                showPanelCoordenadas = true;
                verListadoDirecciones = false;
                verListadoLogDirecciones = false;
                mostrarValidacionCobertura = false;
                mostrarMapaCober = false;
                latitud = "";
                longitud = "";
                break;
            default:
                showPanelBusquedaDireccion = true;
                verListadoDirecciones = false;
                verListadoLogDirecciones = false;
                mostrarMapaCober = false;
                panelDetalleCobertura = false;
                mostrarValidacionCobertura = false;
                mostrarHistorico = false;
                break;
        }
        dirIsCcmm = false;
        dirIsHhpp = false;
        dirIsUnidades = false;
        validarCoberturaMenuItem = false;
        direccionLabel = "";
        barrioSugeridoStr = "";

    }

    //OPCION INGRESAR DIRECCION
    public boolean validarIngresarDireccion() {
        return validarEdicion(INGRESARDIRECCION);
    }

    public boolean validarAgregarDirFact() {
        return validarEdicion(AGREGARDIRFACT);
    }

    public boolean validarProcesarDirFact() {
        return validarEdicion(PROCESARDIRFACT);
    }

    public boolean validarConstDirFact() {
        return validarEdicion(CONSTRUIRDIRFACT);
    }

    public boolean validarLinkIdUnidExistentes() {
        return validarEdicion(LINKIDUNIDEXIST);
    }

    public boolean validarEventTableDir() {
        return validarEdicion(EVENTTABLEDIR);
    }

    public boolean validarFactibilizarDirFact() {
        return validarEdicion(FACTIBILIZARDIR);
    }

    //OPCION INGRESAR PUNTO DE UBICACION
    public boolean validarIngresarPuntoUbicacion() {
        return validarEdicion(INGRESARPUNTOUBI);
    }

    public boolean validarBuscarPtoUbicacion() {
        return validarEdicion(BUSCARPTOUBICACION);
    }

    public boolean validarFactPtoUbicacion() {
        return validarEdicion(FACTPTOUBICACION);
    }

    //OPCION INGRESAR GPS
    public boolean validarConsultaGps() {
        return validarEdicion(CONSULTAGPS);
    }

    public boolean validarGps() {
        return validarEdicion(CONSULTAGPS);
    }

    public boolean validarBuscarGps() {
        return validarEdicion(BUSCARGPS);
    }

    public boolean validarFactibilizarGps() {
        return validarEdicion(FACTIBILIZARGPS);
    }

    //OPCION INGRESAR COORDENADAS
    public boolean validarIngresarCoord() {
        return validarEdicion(INGRESARCOORD);
    }

    public boolean validarBuscarDirCoord() {
        return validarEdicion(PROCESARDIRCOORD);
    }

    public boolean validarAgregarDirCoord() {
        return validarEdicion(AGREGARDIRCOORD);
    }

    public boolean validarConstDirCoord() {
        return validarEdicion(CONSTRUIRDIRCOORD);
    }

    public boolean validarFactDirCoord() {
        return validarEdicion(FACTIBILIZARDIRCOORD);
    }

    public boolean validarLinkCuentaCoord() {
        return validarEdicion(LINKIDUNIDCUENTACOORD);
    }

    public boolean validarLinkDirCoord() {
        return validarEdicion(LINKIDUNIDDIRCOORD);
    }

    public boolean validarEventTableUnidDirCoord() {
        return validarEdicion(EVENTTABLEDIRCOORD);
    }

    public boolean validarLinkTecPoligono() {
        return validarEdicion(LINKTECPOLIGONO);
    }

    public boolean validarRutaMapaGoogle() {
        return validarEdicion(MAPAGOOGLE);
    }

    public boolean validarMenuCobertura() {
        return validarEdicion(VALIDARCOBERTURA);
    }

    public boolean validarCrearSolicitud() {
        return validarEdicion(CREARSOLIVALCOBERTURA);
    }

    private boolean validarEdicion(String formulario) {
        try {
            return ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /**
     * Metodo para validar si el usuario tiene permisos de creacion
     * @return  {@code boolean} true si tiene permisos de creacion, false en caso contrario
     * @author Manuel Hernandez
     */
    public boolean isRolCrear(){
        try {
            return ValidacionUtil.validarVisualizacion(ESCALAMIENTOS_FACTIVILIDAD, ValidacionUtil.OPC_CREACION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar el rol de creación. ");
        }
        return false;
    }

    public void irDetalleSlaEjecucion(DetalleFactibilidadMgl detalleFactibilidadMgl) throws ApplicationException {
        assignDefaultDetailsToPopup();

        if (detalleFactibilidadMgl != null && detalleFactibilidadMgl.getSlaEjecucionMglObj() != null) {

            if (detalleFactibilidadMgl.getDetallesSlas() != null
                    && !detalleFactibilidadMgl.getDetallesSlas().isEmpty()) {
                List<BigDecimal> ids = null;
                if (detalleFactibilidadMgl.getDetallesSlas().contains(",")) {
                    String[] idDet = detalleFactibilidadMgl.getDetallesSlas().split(",");
                    if (idDet != null && idDet.length > 0) {
                        ids = new ArrayList<>();
                        for (String s : idDet) {
                            ids.add(new BigDecimal(s));
                        }
                    }
                }
                if (ids != null && !ids.isEmpty()) {
                    detalleSlaEjecucion = detalleSlaEjeMglFacadeLocal.
                            findDetalleSlaEjecucionByIds(ids);
                }
            } else {
                msnError = "No hay detalles de  sla de ejecucion para la tecnologia: "
                        + detalleFactibilidadMgl.getNombreTecnologia() + "";
                alerts();
                detalleSlaEjecucion = null;
            }

            if (detalleFactibilidadMgl.getSlaEjecucionMglObj().getTipoEjecucion().equalsIgnoreCase("CCMM")) {
                controlCcmmDetalle = true;
                controlHhppDetalle = false;
            } else if (detalleFactibilidadMgl.getSlaEjecucionMglObj().getTipoEjecucion().equalsIgnoreCase("UNIDAD")) {
                controlCcmmDetalle = false;
                controlHhppDetalle = true;
            }

        } else {
            if (detalleFactibilidadMgl != null) {
                msnError = "No hay informacion de un sla de ejecucion para la tecnologia: " + detalleFactibilidadMgl.getNombreTecnologia() + "";
                alerts();
                detalleSlaEjecucion = null;
            } else {
                msnError = "No hay informacion de un sla de ejecucion para la tecnologia";
                alerts();
                detalleSlaEjecucion = null;
            }

        }

    }

    private void renderMenuOpciones() {
        if (direccionDetalladaMglDtoSel != null && direccionDetalladaMglDtoSel.getTipoDireccion() != null) {
            if (direccionDetalladaMglDtoSel.getTipoDireccion().equalsIgnoreCase("UNIDAD")) {
                crearCcmmMenuItem = true;
                crearHhppMenuItem = true;
                validarCoberturaMenuItem = true;
                historicoMenuItem = true;
            } else {
                crearCcmmMenuItem = false;
                modCcmmMenuItem = true;
                crearModHhppMenuItem = true;
                crearVtMenuItem = true;
                validarCoberturaMenuItem = true;
                historicoMenuItem = true;
            }
        }
    }

    public void irHistoricoFactibilidad() {

        try {
            obtenerListaHistorico();
            mostrarValidacionCobertura = false;
            mostrarMapa = false;
            mostrarMapaCober = false;
            panelDetalleCobertura = false;
            mostrarHistorico = true;
            mostrarDetalleHistorico = true;
            listaDetalleHistorico = null;
            showPanelBusquedaDireccion = false;
            showPanelIngresarUbicacion = false;
            showPanelIngresarGPS = false;
            showPanelCoordenadas = false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ConsultarCoberturaV1Bean:"
                    + "irHistoricoFactibilidad:" + e.getMessage() + " ", e, LOGGER);
        }

    }

    public void obtenerListaHistorico() {

        try {
            CmtDireccionDetalladaMglDto direccionDetalladaMglDtosPar;
            listaHistorico = new ArrayList<>();
            listaFactibilidades = factibilidadMglFacadeLocal.findFactibilidadByUsuario(usuarioSesion);
            List<CmtDireccionDetalladaMgl> direccionDetallada = null;
            for (FactibilidadMgl fact : listaFactibilidades) {
                CmtDireccionDetalladaMgl det = cmtDireccionDetalleMglFacadeLocal.findById(fact.getDireccionDetalladaId());
                if (det != null) {
                    direccionDetallada = new ArrayList<>();
                    direccionDetallada.add(det);
                }
                if (direccionDetallada != null && !direccionDetallada.isEmpty()) {
                    DireccionMgl dir = direccionDetallada.get(0).getDireccion();
                    // Convertir el listado de entidades en listado de DTOs para la visualizacion:
                    direccionDetalladaMglDtosPar
                            = this.convertirDireccionDetalladaMglADto(direccionDetallada);
                    if (dir != null) {
                        direccionDetalladaMglDtosPar.setDireccionMgl(dir);
                        SubDireccionMgl subDir = direccionDetallada.get(0).getSubDireccion();
                        direccionDetalladaMglDtosPar.setSubDireccionMgl(subDir);
                        UbicacionMgl ubicacionMgl = dir.getUbicacion() != null
                                ? dir.getUbicacion() : null;
                        GeograficoPoliticoMgl centro;
                        if (ubicacionMgl != null) {
                            direccionDetalladaMglDtosPar.setLatitudDir(ubicacionMgl.getUbiLatitud());
                            direccionDetalladaMglDtosPar.setLongitudDir(ubicacionMgl.getUbiLongitud());
                            centro = ubicacionMgl.getGpoIdObj();
                            if (centro != null) {
                                direccionDetalladaMglDtosPar.setCentroPoblado(centro.getGpoNombre());
                                GeograficoPoliticoMgl ciudad = geograficoPoliticoFacadeLocal.findById(centro.getGeoGpoId());
                                if (ciudad != null) {
                                    direccionDetalladaMglDtosPar.setCiudad(ciudad.getGpoNombre());
                                    GeograficoPoliticoMgl dpto = geograficoPoliticoFacadeLocal.findById(ciudad.getGeoGpoId());
                                    if (dpto != null) {
                                        direccionDetalladaMglDtosPar.setDepartamento(dpto.getGpoNombre());
                                    }
                                }

                            }
                        }
                        direccionDetalladaMglDtosPar.setConfiabilidad(dir.getDirConfiabilidad() != null ? dir.getDirConfiabilidad() : BigDecimal.ONE);
                        //Busqueda cmt Direccion
                        CmtDireccionMgl direccionCm = cmtDireccionMglFacadeLocal.
                                findCmtIdDireccion(dir.getDirId());
                        if (direccionCm != null && direccionCm.getCuentaMatrizObj() != null) {
                            CmtCuentaMatrizMgl cuenta = direccionCm.getCuentaMatrizObj();
                            direccionDetalladaMglDtosPar.setTipoDireccion("CCMM");
                            direccionDetalladaMglDtosPar.setCuentaMatrizMgl(cuenta);
                        } else {
                            direccionDetalladaMglDtosPar.setTipoDireccion("UNIDAD");
                        }
                    }
                    //Busqueda cmt Direccion

                    //Busco si la direccion tiene una factibilidad  vigente List
                    List<FactibilidadMgl> factibilidadVig
                            = factibilidadMglFacadeLocal.findFactibilidadVigByDetallada(direccionDetalladaMglDtosPar.getDireccionDetalladaId(), new Date());

                    if (factibilidadVig != null && !factibilidadVig.isEmpty()) {
                        FactibilidadMgl factibilidadMgl = factibilidadVig.get(0);
                        direccionDetalladaMglDtosPar.setFactibilidadId(factibilidadMgl.getFactibilidadId());
                    }
                    //Fin Busqueda si la direccion tiene una factibilidad  vigente

                    if (direccionDetalladaMglDtosPar.getLatitudDir() != null
                            && direccionDetalladaMglDtosPar.getLongitudDir() != null) {
                        listaHistorico.add(direccionDetalladaMglDtosPar);
                    }
                }
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irCuentaMatrizDetalle. ", e, LOGGER);
        }

    }

    /**
     * Evento llamado al momento de seleccionar una direccion del listado.
     *
     * @param event
     * @throws ApplicationException
     */
    public void onSelectHistorico(SelectEvent<CmtDireccionDetalladaMglDto> event) throws ApplicationException {

        if (direccionHistoricoSelected != null && direccionHistoricoSelected.getTipoDireccion() != null) {
            listaDetalleHistorico = new ArrayList<>();
            mostrarHistorico = true;
            mostrarDetalleHistorico = true;
            cargarListaFiltro(direccionHistoricoSelected.getDireccionDetalladaId());

        }

    }

    public boolean validarSolicitudes() {
        boolean permitirSolicitudes = false;
        try {

            int numeroSolUsuario = 0;
            String cantidadSolPermitidas;
            numeroSolUsuario = solicitudFacadeLocal.solictudesByUsuario(usuarioSesion);
            cantidadSolPermitidas = consultarParametro(Constants.VALIDACION_COBERTURA_MENSUAL);
            if (numeroSolUsuario < (Integer.parseInt(cantidadSolPermitidas))) {
                permitirSolicitudes = true;
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irCuentaMatrizDetalle. ", e, LOGGER);
        }
        return permitirSolicitudes;

    }

    /**
     * Evento llamado al momento de seleccionar una direccion del listado.
     *
     * @param dirDet
     * @throws ApplicationException
     */
    public void cargarListaFiltro(BigDecimal dirDet) throws ApplicationException {
        itemsFiltros = new ArrayList<>();
        listaDesplegableFechaIdFact = new ArrayList<>();
        List<FactibilidadMgl> listaHistoricoByUsuario = null;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        listaFechaIdFact = new LinkedHashMap<>();
        listaHistoricoByUsuario = factibilidadMglFacadeLocal.findFactibilidadByDirDetallada(dirDet);
        for (FactibilidadMgl fact : listaHistoricoByUsuario) {
            listaDesplegableFechaIdFact.add(fact.getFactibilidadId() + "-" + formateador.format(fact.getFechaCreacion()));
        }

    }

    /**
     * Evento llamado al momento de seleccionar una direccion del listado.
     *
     * @throws ApplicationException
     */
    public void filtroDetalleHistorico() throws ApplicationException {

        if (filtroIdFactFecha != null && !filtroIdFactFecha.isEmpty()) {
            if (!filtroIdFactFecha.isEmpty()) {
                String splitted[] = filtroIdFactFecha.split("-", 2);
                String id = splitted[0];
                String fecha = splitted[1];

                listaDetalleHistorico = detalleFactibilidadMglFacadeLocal.findListByIdFactFecha(new BigDecimal(id), fecha, usuarioSesion);
            }
            mostrarDetalleHistorico = true;
        }
    }

    public void enviarCorreoSolicitud(Solicitud sol) {
        try {
            String direccion;
            if (direccionDetalladaMglDtoSel != null && direccionDetalladaMglDtoSel.getSubDireccionMgl() != null) {
                direccion = direccionDetalladaMglDtoSel.getSubDireccionMgl().getSdiFormatoIgac();
            } else {
                direccion = direccionDetalladaMglDtoSel.getDireccionMgl().getDirFormatoIgac();
            }
            MailSender.send(sol.getCorreo() + "," + sol.getCorreoCopia(),
                    "", "", "Nueva solicitud de validación de cobertura : " + sol.getIdSolicitud(),
                    solicitudFacadeLocal.mensajeCorreoSolicitud(sol, "Validacion de Cobertura: " + sol.getIdSolicitud(),
                            dptoSolSelected.getGpoNombre(), ciudadSolSelected.getGpoNombre(),
                            centroPobladoSolSelected.getGpoNombre(), direccion));
        } catch (ApplicationException ex) {
            LOGGER.error("Se genero error en enviarCorreoSolicitud de ConsultarCoberturasV1Bean class ...", ex);
        }

    }

    public void limpiarCampos() {
        correoUsuarioCopia = "";
        this.divisional = null;
        this.tecnologia = null;
        this.tipoSolicitud = null;
        this.tipoServicio = null;
        this.tipoValidacion = null;
        this.bw = null;
        this.tipoSitio = null;
        telefono = "";
        observacionesSolicitudValCobertura = "";
        corporativo = false;
        residencial = false;
    }

    public void limpiarCoordenadas() {
        idFactibilidad = null;
        nameEdificio = "";
        idDpto = null;
        idCiudad = null;
        nombreCentroPoblado = "";
        longitud = "";
        latitud = "";
        listCcmm = new ArrayList();
        ccmmSeleccionada = new CmtCuentaMatrizMgl();
        centroPobladoSelected = new GeograficoPoliticoMgl();
        responseConstruirDireccion = new ResponseConstruccionDireccion();
        lstResponseConstruirDireccion = new ArrayList();
        coordenadaAgregar = new CmtRequestHhppByCoordinatesDto();
        requestCoordenadas = new ArrayList();
        habilitaCity = true;
    }

    /**
     * Metodo para cargar el archivo de las factibilidades
     */
    public void cargarArchivo() {
        assignDefaultDetailsToPopup();

        try {
            this.refresh = REFRESH_FINAL;
            lineasArchivoRetorno = null;

            if (MasivoControlFactibilidadDtoMgl.isIsProcessing()) {
                String msn = ARCHIVO_EN_PROCESO;
                error(msn);
                return;
            } else {
                MasivoControlFactibilidadDtoMgl.cleanBeforeStart();
            }
            this.mensajeError = false;
            if (upFile.getFileName() == null) {
                msnError = ARCHIVO_NULL;
                alerts();
                this.nombre = null;
                return;
            } else {
                this.nombre = upFile.getFileName();
            }
            String ext = upFile.getFileName().substring(upFile.getFileName().length() - 4, upFile.getFileName().length());
            if (!ext.toUpperCase().equals(".CSV")) {
                String msn = ARCHIVO_INVALIDO;
                error(msn);
                this.nombre = null;
                return;
            }

            LineNumberReader reader = new LineNumberReader(new InputStreamReader(this.upFile.getInputStream(), "ISO-8859-1"));
            Long cantReg = reader.lines().count();
            int reg = cantReg.intValue() - 1;

            cantidadregistrosParam = this.consultarParametro(Constants.CANTIDAD_REGISTROS_MASIVO_FACTIBILIDAD);
            if (cantidadregistrosParam != null && !cantidadregistrosParam.isEmpty()) {
                if (isNumeric(cantidadregistrosParam)) {
                    int cantidadReg = Integer.parseInt(cantidadregistrosParam);
                    if (reg > cantidadReg) {
                        error("El numero de registros excede la cantidad permitida que es: " + cantidadReg + " ");
                        return;
                    }
                } else {
                    error("El valor parametrizado para la cantidad de registros no es numerico");
                    return;
                }
            } else {
                error("No esta parametrizado un valor para la cantidad de registros a procesar");
                return;
            }

            factibilidadMglFacadeLocal.calcularFactibilidadMasiva(this, this.upFile, this.usuarioSesion, this.nombre);
            this.enproceso = MasivoControlFactibilidadDtoMgl.isIsProcessing();
            this.finish = MasivoControlFactibilidadDtoMgl.isIsFinished();
            this.enproceso = true;

        } catch (ApplicationException | IOException | NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en cargarArchivo  de ConsultarCoberturasV1Bean :  class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * bocanegravm metodo para generar el reporte final
     */
    public void generarReporteFinal() {

        try {
            if (listResultadoMasivo != null && !listResultadoMasivo.isEmpty()) {

                final StringBuffer sb = new StringBuffer();
                SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss", LOCALE);

                int numeroRegistros = listResultadoMasivo.size();
                byte[] csvData;
                if (numeroRegistros > 0) {

                    for (int j = 0; j < NOM_COLUMNAS.length; j++) {
                        sb.append(NOM_COLUMNAS[j]);
                        if (j < NOM_COLUMNAS.length) {
                            sb.append(";");
                        }
                    }
                    sb.append("\n");
                    for (CmtDireccionDetalladaMglDto detalladaMglDto : listResultadoMasivo) {

                        if (detalladaMglDto.getLstDetalleFactibilidadMgls() != null
                                && !detalladaMglDto.getLstDetalleFactibilidadMgls().isEmpty()) {

                            for (DetalleFactibilidadMgl detalleFactibilidadMgl : detalladaMglDto.getLstDetalleFactibilidadMgls()) {

                                String idFactibilidadRes = detalladaMglDto.getFactibilidadId() == null ? " " : detalladaMglDto.getFactibilidadId().toString();
                                sb.append(idFactibilidadRes);
                                sb.append(";");
                                formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss", LOCALE);
                                String ubicacionIng = detalladaMglDto.getUbicacionIngresada() == null ? " " : detalladaMglDto.getUbicacionIngresada();
                                sb.append(ubicacionIng);
                                sb.append(";");
                                String ubicacionGen = "";
                                if (detalladaMglDto.getDireccionMgl() != null) {
                                    ubicacionGen = detalladaMglDto.getDireccionMgl().getDirNostandar();
                                }
                                sb.append(ubicacionGen);
                                sb.append(";");

                                String departamento = detalladaMglDto.getDepartamento() == null ? " " : detalladaMglDto.getDepartamento();
                                sb.append(departamento);
                                sb.append(";");
                                String ciudad = detalladaMglDto.getCiudad() == null ? " " : detalladaMglDto.getCiudad();
                                sb.append(ciudad);
                                sb.append(";");
                                String centro = detalladaMglDto.getCentroPoblado() == null ? " " : detalladaMglDto.getCentroPoblado();
                                sb.append(centro);
                                sb.append(";");
                                String todayStr = formato.format(new Date());
                                sb.append(todayStr);
                                sb.append(";");
                                sb.append(usuarioLogin.getUsuario());
                                sb.append(";");

                                String latitudRes = detalladaMglDto.getLatitudDir() == null ? " " : detalladaMglDto.getLatitudDir();
                                latitudRes = latitudRes.replace(".", ",");
                                sb.append(latitudRes);
                                sb.append(";");
                                String longitudRes = detalladaMglDto.getLongitudDir() == null ? " " : detalladaMglDto.getLongitudDir();
                                longitudRes = longitudRes.replace(".", ",");
                                sb.append(longitudRes);
                                sb.append(";");

                                String idCcmmRr = "";
                                String idCcmmMgl = "";
                                String nameCCmm = "";
                                String nameSub = "";

                                if (detalladaMglDto.getCuentaMatrizMgl() != null) {
                                    CmtCuentaMatrizMgl cm = detalladaMglDto.getCuentaMatrizMgl();
                                    CmtSubEdificioMgl sub = null;
                                    if (cm != null && cm.getSubEdificioGeneral() != null) {
                                        sub = cm.getSubEdificioGeneral();
                                    }
                                    idCcmmRr = cm.getNumeroCuenta() == null ? " " : cm.getNumeroCuenta().toString();
                                    idCcmmMgl = cm.getCuentaMatrizId() == null ? " " : cm.getCuentaMatrizId().toString();
                                    nameCCmm = cm.getNombreCuenta() == null ? " " : cm.getNombreCuenta();
                                    if (sub != null) {
                                        nameSub = sub.getNombreSubedificio() == null ? " " : sub.getNombreSubedificio();
                                    }
                                }
                                sb.append(idCcmmRr);
                                sb.append(";");
                                sb.append(idCcmmMgl);
                                sb.append(";");
                                sb.append(nameCCmm);
                                sb.append(";");
                                sb.append(nameSub);
                                sb.append(";");

                                String cuentaCliente = "";
                                if (detalladaMglDto.getHhppMgl() != null
                                        && detalladaMglDto.getHhppMgl().getSuscriptor() != null) {
                                    cuentaCliente = detalladaMglDto.getHhppMgl().getSuscriptor();
                                }
                                sb.append(cuentaCliente);
                                sb.append(";");

                                String tipoDir = detalladaMglDto.getTipoDireccion() == null ? " " : detalladaMglDto.getTipoDireccion();
                                sb.append(tipoDir);
                                sb.append(";");

                                String tecnologiaRes = detalleFactibilidadMgl.getNombreTecnologia() == null ? " " : detalleFactibilidadMgl.getNombreTecnologia();
                                sb.append(tecnologiaRes);
                                sb.append(";");

                                String nodo = detalleFactibilidadMgl.getCodigoNodo() == null ? " " : detalleFactibilidadMgl.getCodigoNodo();
                                sb.append(nodo);
                                sb.append(";");

                                String estadoNodo = detalleFactibilidadMgl.getEstadoNodo() == null ? " " : detalleFactibilidadMgl.getEstadoNodo();
                                sb.append(estadoNodo);
                                sb.append(";");

                                String estTecnologia = detalleFactibilidadMgl.getEstadoTecnologia() == null ? " " : detalleFactibilidadMgl.getEstadoTecnologia();
                                sb.append(estTecnologia);
                                sb.append(";");

                                String factibilidad = detalleFactibilidadMgl.getClaseFactibilidad() == null ? " " : detalleFactibilidadMgl.getClaseFactibilidad();
                                sb.append(factibilidad);
                                sb.append(";");

                                String arrendatario = detalleFactibilidadMgl.getNombreArrendatario() == null ? " " : detalleFactibilidadMgl.getNombreArrendatario();
                                sb.append(arrendatario);
                                sb.append(";");

                                String tiempo = detalleFactibilidadMgl.getTiempoUltimaLilla() == 0 ? "" : String.valueOf(detalleFactibilidadMgl.getTiempoUltimaLilla());
                                sb.append(tiempo);
                                sb.append(";");

                                String nodoAproximado = detalleFactibilidadMgl.getNodoMglAproximado() == null ? "" : detalleFactibilidadMgl.getNodoMglAproximado().getNodCodigo();
                                sb.append(nodoAproximado);
                                sb.append(";");

                                String distancia = detalleFactibilidadMgl.getDistanciaNodoApro() == 0 ? "" : String.valueOf(detalleFactibilidadMgl.getDistanciaNodoApro() + "Mts");
                                sb.append(distancia);
                                sb.append(";");

                                String nodoBackup = detalleFactibilidadMgl.getNodoMglBackup() == null ? "" : detalleFactibilidadMgl.getNodoMglBackup().getNodCodigo();
                                sb.append(nodoBackup);
                                sb.append(";");

                                sb.append(String.valueOf(detalladaMglDto.getDireccionDetalladaId()));
                                sb.append(";");

                                /* Brief 98062 */
                                String sitidata = detalleFactibilidadMgl.getNodoSitiData() == null ? " " : detalleFactibilidadMgl.getNodoSitiData();
                                    sb.append(sitidata);
                                sb.append(";");
                                /* Cierre Brief 98062*/

                                /* Brief 117487  */
                                String estrato = "";
                                int datoEstrato = Integer.parseInt(String.valueOf(detalladaMglDto.getDireccionMgl().getDirNivelSocioecono()));
                                if (detalladaMglDto.getDireccionMgl().getDirNivelSocioecono() == null) {
                                    estrato = "NG";
                                } else {
                                    if (datoEstrato <= 6 && datoEstrato >= 0) {
                                        estrato = detalladaMglDto.getDireccionMgl().getDirNivelSocioecono() == null ? "NG" : String.valueOf(detalladaMglDto.getDireccionMgl().getDirNivelSocioecono());
                                    } else {
                                        estrato = "NG";
                                    }

                                }

                                sb.append(estrato);
                                sb.append(";");
                                /* Cierre Brief 117487 */

                                sb.append("\n");
                            }
                        } else {
                            //No hay coberturas
                            String idFactibilidadRes = detalladaMglDto.getFactibilidadId() == null ? " " : detalladaMglDto.getFactibilidadId().toString();
                            sb.append(idFactibilidadRes);
                            sb.append(";");
                            formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss", LOCALE);
                            String ubicacionIng = detalladaMglDto.getUbicacionIngresada() == null ? " " : detalladaMglDto.getUbicacionIngresada();
                            sb.append(ubicacionIng);
                            sb.append(";");
                            String ubicacionGen = "";
                            if (detalladaMglDto.getDireccionMgl() != null) {
                                ubicacionGen = detalladaMglDto.getDireccionMgl().getDirNostandar();
                            }
                            sb.append(ubicacionGen);
                            sb.append(";");

                            String departamento = detalladaMglDto.getDepartamento() == null ? " " : detalladaMglDto.getDepartamento();
                            sb.append(departamento);
                            sb.append(";");
                            String ciudad = detalladaMglDto.getCiudad() == null ? " " : detalladaMglDto.getCiudad();
                            sb.append(ciudad);
                            sb.append(";");
                            String centro = detalladaMglDto.getCentroPoblado() == null ? " " : detalladaMglDto.getCentroPoblado();
                            sb.append(centro);
                            sb.append(";");
                            String todayStr = formato.format(new Date());
                            sb.append(todayStr);
                            sb.append(";");
                            sb.append(usuarioLogin.getUsuario());
                            sb.append(";");

                            String latitudRes = detalladaMglDto.getLatitudDir() == null ? " " : detalladaMglDto.getLatitudDir();
                            latitudRes = latitudRes.replace(".", ",");
                            sb.append(latitudRes);
                            sb.append(";");
                            String longitudRes = detalladaMglDto.getLongitudDir() == null ? " " : detalladaMglDto.getLongitudDir();
                            longitudRes = longitudRes.replace(".", ",");
                            sb.append(longitudRes);
                            sb.append(";");

                            String idCcmmRr = "";
                            String idCcmmMgl = "";
                            String nameCCmm = "";
                            String nameSub = "";

                            if (detalladaMglDto.getCuentaMatrizMgl() != null) {
                                CmtCuentaMatrizMgl cm = detalladaMglDto.getCuentaMatrizMgl();
                                CmtSubEdificioMgl sub = null;
                                if (cm != null && cm.getSubEdificioGeneral() != null) {
                                    sub = cm.getSubEdificioGeneral();
                                }
                                idCcmmRr = cm.getNumeroCuenta() == null ? " " : cm.getNumeroCuenta().toString();
                                idCcmmMgl = cm.getCuentaMatrizId() == null ? " " : cm.getCuentaMatrizId().toString();
                                nameCCmm = cm.getNombreCuenta() == null ? " " : cm.getNombreCuenta();
                                if (sub != null) {
                                    nameSub = sub.getNombreSubedificio() == null ? " " : sub.getNombreSubedificio();
                                }
                            }
                            sb.append(idCcmmRr);
                            sb.append(";");
                            sb.append(idCcmmMgl);
                            sb.append(";");
                            sb.append(nameCCmm);
                            sb.append(";");
                            sb.append(nameSub);
                            sb.append(";");

                            String cuentaCliente = "";
                            if (detalladaMglDto.getHhppMgl() != null
                                    && detalladaMglDto.getHhppMgl().getSuscriptor() != null) {
                                cuentaCliente = detalladaMglDto.getHhppMgl().getSuscriptor();
                            }
                            sb.append(cuentaCliente);
                            sb.append(";");

                            String tipoDir = detalladaMglDto.getTipoDireccion() == null ? " " : detalladaMglDto.getTipoDireccion();
                            sb.append(tipoDir);
                            sb.append(";");

                            String tecnologiaRes = "";
                            sb.append(tecnologiaRes);
                            sb.append(";");

                            String nodo = "";
                            sb.append(nodo);
                            sb.append(";");

                            String estadoNodo = "";
                            sb.append(estadoNodo);
                            sb.append(";");

                            String estTecnologia = "";
                            sb.append(estTecnologia);
                            sb.append(";");

                            String factibilidad = "NEGATIVA";
                            sb.append(factibilidad);
                            sb.append(";");

                            String arrendatario = "";
                            sb.append(arrendatario);
                            sb.append(";");

                            String tiempo = "";
                            sb.append(tiempo);
                            sb.append(";");

                            String nodoAproximado = "";
                            sb.append(nodoAproximado);
                            sb.append(";");

                            String distancia = "";
                            sb.append(distancia);
                            sb.append(";");

                            String nodoBackup = "";
                            sb.append(nodoBackup);
                            sb.append(";");

                            sb.append(String.valueOf(detalladaMglDto.getDireccionDetalladaId()));
                            sb.append(";");

                            /* Brief 98062 */
                            String sitidata = "";
                            sb.append(sitidata);
                            sb.append(";");
                            /* Cierre Brief 98062*/

                            /* Brief 117487  */
                            String estrato = detalladaMglDto.getDireccionMgl().getDirNivelSocioecono() == null ? "NG" : String.valueOf(detalladaMglDto.getDireccionMgl().getDirNivelSocioecono());
                            sb.append(estrato);
                            sb.append(";");
                            /* Cierre Brief 117487 */

                            sb.append("\n");

                        }

                    }
                }
                csvData = sb.toString().getBytes();
                String todayStr = formato.format(new Date());
                String fileName = "Resultado_Masivo_Factibilidad" + "_" + todayStr + "." + "csv";
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
                response1.setHeader("Content-disposition", "attached; filename=\""
                        + fileName + "\"");
                response1.setContentType("application/octet-stream;charset=UTF-8;");
                response1.setCharacterEncoding("UTF-8");
                response1.getOutputStream().write(csvData);
                response1.getOutputStream().flush();
                response1.getOutputStream().close();
                fc.responseComplete();
            } else {
                error("No se encontraron registros para el reporte");
            }

        } catch (IOException e) {
            String mensaje = "Ocurrió un error en la creación del archivo excel csv";
            FacesUtil.mostrarMensajeError(mensaje + e.getMessage(), e, LOGGER);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConsultarCoberturasV1Bean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    public String downloadFileWithErrors() {
        try {
            final StringBuffer sb = new StringBuffer();
            SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss", LOCALE);

            if (lineasArchivoRetorno != null && !lineasArchivoRetorno.isEmpty()) {
                byte[] csvData;
                for (String registro : lineasArchivoRetorno) {
                    sb.append(registro);
                    sb.append("\n");
                }

                csvData = sb.toString().getBytes();
                String todayStr = formato.format(new Date());
                String fileName = "Reporte_Error_Masivo_Factibilidad" + "_" + todayStr + "." + "csv";
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
                response1.setHeader("Content-disposition", "attached; filename=\""
                        + fileName + "\"");
                response1.setContentType("application/octet-stream;charset=UTF-8;");
                response1.setCharacterEncoding("UTF-8");
                response1.getOutputStream().write(csvData);
                response1.getOutputStream().flush();
                response1.getOutputStream().close();
                fc.responseComplete();
                enproceso = false;
                setFinish(false);
                MasivoControlFactibilidadDtoMgl.setNombreArchivo("");
                lineasArchivoRetorno = null;
                MasivoControlFactibilidadDtoMgl.setListArchivoWithErrors(null);
                this.refresh = REFRESH_FINAL;
            } else {
                error("No se encontraron registros para el reporte");
            }

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en ConsultarCoberturasV1Bean:downloadFileWithErrors. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ConsultarCoberturasV1Bean:downloadFileWithErrors " + e.getMessage(), e, LOGGER);
        }
        return "case1";
    }

    public void volverList() {
        try {
            enproceso = false;
            setFinish(false);
            MasivoControlFactibilidadDtoMgl.setIsProcessing(false);
            MasivoControlFactibilidadDtoMgl.cleanBeforeStart();
            this.refresh = REFRESH_FINAL;
            MasivoControlFactibilidadDtoMgl.setListArchivoWithErrors(null);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en ConsultarCoberturasV1Bean:volverList(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void factibilizarUnidad(CmtDireccionDetalladaMgl detalladaMgl) throws Exception {
        assignDefaultDetailsToPopup();
        // Construir un DTO a partir de la entidad, y adicionarlo a la lista:
        CmtDireccionDetalladaMglDto cmtDireccionDetalladaMglDto
                = detalladaMgl.convertirADto();

        DireccionMgl dir = detalladaMgl.getDireccion();

        if (dir != null) {
            cmtDireccionDetalladaMglDto.setDireccionMgl(dir);
            SubDireccionMgl subDir = detalladaMgl.getSubDireccion();
            cmtDireccionDetalladaMglDto.setSubDireccionMgl(subDir);
            UbicacionMgl ubicacionMgl = dir.getUbicacion() != null
                    ? dir.getUbicacion() : null;
            GeograficoPoliticoMgl centro;
            if (ubicacionMgl != null) {
                cmtDireccionDetalladaMglDto.setLatitudDir(ubicacionMgl.getUbiLatitud());
                cmtDireccionDetalladaMglDto.setLongitudDir(ubicacionMgl.getUbiLongitud());
                centro = ubicacionMgl.getGpoIdObj();
                if (centro != null) {
                    cmtDireccionDetalladaMglDto.setCentroPoblado(centro.getGpoNombre());
                    GeograficoPoliticoMgl ciudad = geograficoPoliticoFacadeLocal.findById(centro.getGeoGpoId());
                    if (ciudad != null) {
                        cmtDireccionDetalladaMglDto.setCiudad(ciudad.getGpoNombre());
                        GeograficoPoliticoMgl dpto = geograficoPoliticoFacadeLocal.findById(ciudad.getGeoGpoId());
                        if (dpto != null) {
                            cmtDireccionDetalladaMglDto.setDepartamento(dpto.getGpoNombre());
                        }
                    }

                }
            }
            cmtDireccionDetalladaMglDto.setConfiabilidad(dir.getDirConfiabilidad() != null ? dir.getDirConfiabilidad() : BigDecimal.ONE);
            //Busqueda cmt Direccion
            CmtDireccionMgl direccionCm = cmtDireccionMglFacadeLocal.
                    findCmtIdDireccion(dir.getDirId());
            if (direccionCm != null && direccionCm.getCuentaMatrizObj() != null) {
                CmtCuentaMatrizMgl cuenta = direccionCm.getCuentaMatrizObj();
                cmtDireccionDetalladaMglDto.setTipoDireccion("CCMM");
                cmtDireccionDetalladaMglDto.setCuentaMatrizMgl(cuenta);
            } else {
                cmtDireccionDetalladaMglDto.setTipoDireccion("UNIDAD");
            }
        }
        //Busqueda cmt Direccion

        if (cmtDireccionDetalladaMglDto != null) {
            direccionDetalladaMglDtoSelUnidad = cmtDireccionDetalladaMglDto;
            // Es una unidad se consulta Hhpps
            List<CmtDireccionDetalladaMgl> lstDetalladaMgls = new ArrayList<>();
            lstDetalladaMgls.add(detalladaMgl);
            hhppCcmmList = hhppMglFacadeLocal.obtenerHhppByDireccionDetallaList(lstDetalladaMgls);

            //recorrido para tomar el regional del hhpp
            if (hhppCcmmList != null && !hhppCcmmList.isEmpty()) {
                if (hhppCcmmList.get(0).getNodId() != null
                        && hhppCcmmList.get(0).getNodId().getDivId() != null
                        && hhppCcmmList.get(0).getNodId().getDivId().getNombreBasica() != null) {
                    direccionDetalladaMglDtoSelUnidad.setDivisionalNodo(hhppCcmmList.get(0).getNodId().getDivId().getNombreBasica());
                }
            }
            this.mostrarMapaByCoordenadasDirSelected(direccionDetalladaMglDtoSelUnidad);
        } else {
            msnError = "No ha seleccionado una direccion para calcular su factibilidad.";
            alerts();
        }

    }

    public String retornaColorTecno(String codigoBasica) {

        String color = "";

        if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_UNO.getCodigo())) {
            color = "orange";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_DOS.getCodigo())) {
            color = "blue";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_TRES.getCodigo())) {
            color = "red";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_DTH.getCodigo())) {
            color = "green";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_MOVIL.getCodigo())) {
            color = "yellow";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_FTTH.getCodigo())) {
            color = "brown";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_FTTX.getCodigo())){
            color = "brown";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_WIFI.getCodigo())) {
            color = "pink";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_FOU.getCodigo())) {
            color = "violet";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_ZONA_GPON_DISENIADO.getCodigo())) {
            color = "turquoise";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_ZONA_MICRO_ONDAS.getCodigo())) {
            color = "fuchsia";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_ZONA_COBERTURA_CAVS.getCodigo())) {
            color = "gray";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_ZONA_COBERTURA_ULTIMA_MILLA.getCodigo())) {
            color = "black";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_ZONA_CURRIER.getCodigo())) {
            color = "purple";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_ZONA_3G.getCodigo())) {
            color = "yellowgreen";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_ZONA_4G.getCodigo())) {
            color = "salmon";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_ZONA_5G.getCodigo())) {
            color = "indigo";
        }

        return color;
    }

    public void generarPlantilla() {

        try {

            final StringBuffer sb = new StringBuffer();
            byte[] csvData;
            SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss", LOCALE);

            for (int j = 0; j < NOM_COLUMNAS_PLANTILLA.length; j++) {
                sb.append(NOM_COLUMNAS_PLANTILLA[j]);
                if (j < NOM_COLUMNAS_PLANTILLA.length) {
                    sb.append(";");
                }
            }
            sb.append("\n");

            csvData = sb.toString().getBytes();
            String todayStr = formato.format(new Date());
            String fileName = "Plantilla_Masivo_Factibilidad" + "_" + todayStr + "." + "csv";
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
            response1.setHeader("Content-disposition", "attached; filename=\""
                    + fileName + "\"");
            response1.setContentType("application/octet-stream;charset=UTF-8;");
            response1.setCharacterEncoding("UTF-8");
            response1.getOutputStream().write(csvData);
            response1.getOutputStream().flush();
            response1.getOutputStream().close();
            fc.responseComplete();

        } catch (IOException e) {
            String mensaje = "Ocurrió un error en la creación del archivo excel csv";
            FacesUtil.mostrarMensajeError(mensaje + e.getMessage(), e, LOGGER);
        }

    }

    /**
     * exporta la lista de centros poblados Autor: Juan David Hernandez
     *
     * @return
     */
    public String exportExcel() throws ApplicationException, IOException {
        try {
            List<CmtGeograficoPoliticoDto> centroPoblados = geograficoPoliticoFacadeLocal.findAllCentroPoblados();

            if (centroPoblados != null && !centroPoblados.isEmpty()) {

                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("codigosDane");
                String[] objArr = {"DEPARTAMENTO", "CIUDAD", "CENTRO_POBLADO", "CODIGO_DANE"};
                int rownum = 0;
                int cellnum = 0;

                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse responseNod = (HttpServletResponse) fc.getExternalContext().getResponse();
                responseNod.reset();
                responseNod.setContentType("application/vnd.ms-excel;charset=UTF-8;");

                SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");

                responseNod.setHeader("Content-Disposition", "attachment; filename=\""
                        + "Plantilla_Codigos_Dane" + formato.format(new Date())
                        + ".xlsx\"");
                OutputStream output = responseNod.getOutputStream();

                for (CmtGeograficoPoliticoDto geograficoPoliticoDto : centroPoblados) {

                    Row row = sheet.createRow(rownum);
                    if (rownum == 0) {
                        for (String c : objArr) {
                            Cell cell = row.createCell(cellnum++);
                            cell.setCellValue(c);
                        }
                        rownum++;
                    }
                    cellnum = 0;
                    row = sheet.createRow(rownum);

                    String departamento = geograficoPoliticoDto.getNombreDepartamento();
                    Cell cell = row.createCell(cellnum++);
                    cell.setCellValue(departamento != null ? departamento : "");

                    String city = geograficoPoliticoDto.getNombreCiudad();
                    Cell cel2 = row.createCell(cellnum++);
                    cel2.setCellValue(city != null ? city : "");

                    String center1 = geograficoPoliticoDto.getNombreCentro();
                    Cell cel3 = row.createCell(cellnum++);
                    cel3.setCellValue(center1 != null ? center1 : "");

                    String codigoDane = geograficoPoliticoDto.getCodigoDane();
                    Cell cel4 = row.createCell(cellnum++);
                    cel4.setCellValue(codigoDane != null ? codigoDane : "");

                    rownum++;

                }
                workbook.write(output);
                output.flush();
                output.close();
                fc.responseComplete();
            }

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError(ConsultarCoberturasV1Bean.class.getName() + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al expor ot gestion hhpp. " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void descargarCodigosDane() {

        try {

            List<CmtGeograficoPoliticoDto> centroPoblados = geograficoPoliticoFacadeLocal.findAllCentroPoblados();

            if (centroPoblados != null && !centroPoblados.isEmpty()) {

                final StringBuffer sb = new StringBuffer();
                SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss", LOCALE);

                int numeroRegistros = centroPoblados.size();
                byte[] csvData;
                if (numeroRegistros > 0) {

                    for (int j = 0; j < NOM_COLUMNAS_CODIGO_DANE.length; j++) {
                        sb.append(NOM_COLUMNAS_CODIGO_DANE[j]);
                        if (j < NOM_COLUMNAS_CODIGO_DANE.length) {
                            sb.append(";");
                        }
                    }
                    sb.append("\n");
                    for (CmtGeograficoPoliticoDto geograficoPoliticoDto : centroPoblados) {

                        String departamento = geograficoPoliticoDto.getNombreDepartamento();
                        sb.append(departamento);
                        sb.append(";");
                        String city = geograficoPoliticoDto.getNombreCiudad();
                        sb.append(city);
                        sb.append(";");
                        String center1 = geograficoPoliticoDto.getNombreCentro();
                        sb.append(center1);
                        sb.append(";");
                        //Se añade comilla para que no le quite los 0 de la izquierda
                        String dane = "'" + geograficoPoliticoDto.getCodigoDane();
                        sb.append(dane);
                        sb.append(";");

                        sb.append("\n");

                    }
                }
                csvData = sb.toString().getBytes();
                String todayStr = formato.format(new Date());
                String fileName = "Plantilla_Codigos_Dane" + "_" + todayStr + "." + "csv";
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
                response1.setHeader("Content-disposition", "attached; filename=\""
                        + fileName + "\"");
                response1.setContentType("application/octet-stream;charset=UTF-8;");
                response1.setCharacterEncoding("UTF-8");
                response1.getOutputStream().write(csvData);
                response1.getOutputStream().flush();
                response1.getOutputStream().close();
                fc.responseComplete();
            } else {
                error("No se encontraron registros para el reporte");
            }

        } catch (IOException e) {
            String mensaje = "Ocurrió un error en la creación del archivo excel csv";
            FacesUtil.mostrarMensajeError(mensaje + e.getMessage(), e, LOGGER);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConsultarCoberturasV1Bean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    public void verLogDireccion(CmtDireccionDetalladaMglDto direccionDetalladaMglDto) throws ApplicationException {
        assignDefaultDetailsToPopup();

        if (direccionDetalladaMglDto != null && direccionDetalladaMglDto.getDireccionDetalladaId() != null) {
            //Consulto las factibilidades vigentes y no vigentes de esa direccion  
            List<FactibilidadMgl> factibilidadMgls
                    = factibilidadMglFacadeLocal.findFactibilidadByDirDetallada(direccionDetalladaMglDto.getDireccionDetalladaId());
            if (factibilidadMgls != null && !factibilidadMgls.isEmpty()) {
                detalleFactibilidadLogDto = new ArrayList<>();

                for (FactibilidadMgl factibilidadMgl : factibilidadMgls) {
                    //consulto detalle de factibilidad
                    List<DetalleFactibilidadMgl> detalleFactibilidadMglsLog
                            = detalleFactibilidadMglFacadeLocal.findListDetalleFactibilidad(factibilidadMgl.getFactibilidadId());
                    if (detalleFactibilidadMglsLog != null && !detalleFactibilidadMglsLog.isEmpty()) {
                        for (DetalleFactibilidadMgl detalleFactibilidadMgl : detalleFactibilidadMglsLog) {
                            DetalleFactibilidadLogDto detalleFactibilidadLogDto1 = new DetalleFactibilidadLogDto();
                            detalleFactibilidadLogDto1.setIdFactibilidad(factibilidadMgl.getFactibilidadId());
                            detalleFactibilidadLogDto1.setFechaFactibilidad(factibilidadMgl.getFechaCreacion());
                            detalleFactibilidadLogDto1.setFechaVencimientoFactibilidad(factibilidadMgl.getFechaVencimiento());

                            detalleFactibilidadLogDto1.setTecnologia(detalleFactibilidadMgl.getNombreTecnologia());
                            detalleFactibilidadLogDto1.setSDS(detalleFactibilidadMgl.getSds());
                            detalleFactibilidadLogDto1.setCobertura(detalleFactibilidadMgl.getCodigoNodo());
                            detalleFactibilidadLogDto1.setClaseFactibilidad(detalleFactibilidadMgl.getClaseFactibilidad());
                            detalleFactibilidadLogDto1.setArrendatario(detalleFactibilidadMgl.getNombreArrendatario());
                            detalleFactibilidadLogDto1.setTiempoInstalaciónUM(detalleFactibilidadMgl.getTiempoUltimaLilla());
                            detalleFactibilidadLogDto1.setEstadoTecnología(detalleFactibilidadMgl.getEstadoTecnologia());
                            detalleFactibilidadLogDto.add(detalleFactibilidadLogDto1);
                        }

                    } else {
                        DetalleFactibilidadLogDto detalleFactibilidadLogDto1 = new DetalleFactibilidadLogDto();
                        detalleFactibilidadLogDto1.setIdFactibilidad(factibilidadMgl.getFactibilidadId());
                        detalleFactibilidadLogDto1.setFechaFactibilidad(factibilidadMgl.getFechaCreacion());
                        detalleFactibilidadLogDto1.setFechaVencimientoFactibilidad(factibilidadMgl.getFechaVencimiento());
                        detalleFactibilidadLogDto.add(detalleFactibilidadLogDto1);
                    }

                }
                verListadoDirecciones = false;
                verListadoLogDirecciones = true;
            } else {
                msnError = "No existe historico de factibilidades para esta direccion";
                alerts();

            }

        }

    }

    public void exportarLog() {

        try {
            if (detalleFactibilidadLogDto != null && !detalleFactibilidadLogDto.isEmpty()) {

                final StringBuffer sb = new StringBuffer();
                SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss", LOCALE);

                int numeroRegistros = detalleFactibilidadLogDto.size();
                byte[] csvData;
                if (numeroRegistros > 0) {

                    for (int j = 0; j < NOM_COLUMNAS_LOG_FACTIBILIDADES.length; j++) {
                        sb.append(NOM_COLUMNAS_LOG_FACTIBILIDADES[j]);
                        if (j < NOM_COLUMNAS_LOG_FACTIBILIDADES.length) {
                            sb.append(";");
                        }
                    }
                    sb.append("\n");
                    for (DetalleFactibilidadLogDto detalleFactibilidadLogDtoExp : detalleFactibilidadLogDto) {

                        String idFactibilidadExp = detalleFactibilidadLogDtoExp.getIdFactibilidad().toString();
                        sb.append(idFactibilidadExp);
                        sb.append(";");

                        String fechaFactibilidad = formato.format(detalleFactibilidadLogDtoExp.getFechaFactibilidad());
                        sb.append(fechaFactibilidad);
                        sb.append(";");

                        String fechaVenceFactibilidad = formato.format(detalleFactibilidadLogDtoExp.getFechaVencimientoFactibilidad());
                        sb.append(fechaVenceFactibilidad);
                        sb.append(";");

                        String tecnologiaExp = detalleFactibilidadLogDtoExp.getTecnologia() != null ? detalleFactibilidadLogDtoExp.getTecnologia() : "";
                        sb.append(tecnologiaExp);
                        sb.append(";");

                        String sdsExp = detalleFactibilidadLogDtoExp.getSDS() != null ? detalleFactibilidadLogDtoExp.getSDS() : "";
                        sb.append(sdsExp);
                        sb.append(";");

                        String coberturaExp = detalleFactibilidadLogDtoExp.getCobertura() != null ? detalleFactibilidadLogDtoExp.getCobertura() : "";
                        sb.append(coberturaExp);
                        sb.append(";");

                        String factibilidadExp = detalleFactibilidadLogDtoExp.getClaseFactibilidad() != null ? detalleFactibilidadLogDtoExp.getClaseFactibilidad() : "";
                        sb.append(factibilidadExp);
                        sb.append(";");

                        String arrendaExp = detalleFactibilidadLogDtoExp.getArrendatario() != null ? detalleFactibilidadLogDtoExp.getArrendatario() : "";
                        sb.append(arrendaExp);
                        sb.append(";");

                        int tiempoUMExp = 0;
                        if (detalleFactibilidadLogDtoExp.getTiempoInstalaciónUM() > 0) {
                            tiempoUMExp = detalleFactibilidadLogDtoExp.getTiempoInstalaciónUM();
                        }
                        sb.append(tiempoUMExp);
                        sb.append(";");
                        String estTecExp = detalleFactibilidadLogDtoExp.getEstadoTecnología() != null ? detalleFactibilidadLogDtoExp.getEstadoTecnología() : "";
                        sb.append(estTecExp);
                        sb.append(";");

                        sb.append("\n");

                    }
                }
                csvData = sb.toString().getBytes();
                String todayStr = formato.format(new Date());
                String fileName = "Archivo_Log_Direccion" + "_" + todayStr + "." + "csv";
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
                response1.setHeader("Content-disposition", "attached; filename=\""
                        + fileName + "\"");
                response1.setContentType("application/octet-stream;charset=UTF-8;");
                response1.setCharacterEncoding("UTF-8");
                response1.getOutputStream().write(csvData);
                response1.getOutputStream().flush();
                response1.getOutputStream().close();
                fc.responseComplete();
            } else {
                error("No se encontraron registros para el reporte");
            }

        } catch (IOException e) {
            String mensaje = "Ocurrió un error en la creación del archivo excel csv";
            FacesUtil.mostrarMensajeError(mensaje + e.getMessage(), e, LOGGER);
        }

    }

    /**
     * Evento llamado al momento de consultar un nodo aproximado.
     */
    public void consultarNodoAproximado(DetalleFactibilidadMgl detalleFac) throws ApplicationException, Exception {
        assignDefaultDetailsToPopup();

        if (direccionDetalladaMglDtoSel != null
                && direccionDetalladaMglDtoSel.getLongitudDir() != null
                && direccionDetalladaMglDtoSel.getLatitudDir() != null) {

            if (direccionDetalladaMglDtoSel.getDireccionMgl() != null
                    && direccionDetalladaMglDtoSel.getDireccionMgl().getUbicacion() != null) {

                String latitudMod = direccionDetalladaMglDtoSel.getLatitudDir().replace(",", ".");
                String longitudMod = direccionDetalladaMglDtoSel.getLongitudDir().replace(",", ".");

                //Capturo el centro poblado
                GeograficoPoliticoMgl centro = direccionDetalladaMglDtoSel.getDireccionMgl().
                        getUbicacion().getGpoIdObj();
                CmtTipoBasicaMgl tipoBasicaTecnologia;
                tipoBasicaTecnologia = cmtTipoBasicaMglFacadeLocal.
                        findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
                
                /* Brief 310284 */
                if (detalleFac.isCalculateNodeDistanceWs()) {
                    String nombreTecnologia = detalleFac.getNombreTecnologia();
                    if(detalleFac.getNombreTecnologia().equalsIgnoreCase(Constant.TEC_BI_CALCULATE_NODE)){
                        nombreTecnologia = Constant.TEC_HFC_CALCULATE_NODE;
                    }
                    CalculateNodeDistanceRestResponse calculateNodeDistance
                            = callCalculateNodeDistance(longitudMod,latitudMod,
                                    nombreTecnologia,centro.getGeoCodigoDane());
                    if (calculateNodeDistance != null){
                        if(calculateNodeDistance.getCode().equalsIgnoreCase(Constant.RESP_CODE_NODE_APROX_OK)){
                            detalleFac.setNodoMglAproximado(
                                    calculateNodeDistance.getCursor().getRow()[0].getCodeNode());
                            int nodeDistanceWs = (int) Double.parseDouble(
                                    calculateNodeDistance.getCursor().getRow()[0].getDistance());
                            detalleFac.setDistanciaNodoApro(nodeDistanceWs);
                            detalleFac.setCalculateNodeDistanceWs(false);
                        } else if(
                                (calculateNodeDistance.getCode().equalsIgnoreCase(Constant.RESP_CODE_NODE_APROX_NO_DATA)) ||
                                (calculateNodeDistance.getCode().equalsIgnoreCase(Constant.RESP_CODE_NODE_APROX_ERR5))
                                ){
                            detalleFac.setNodoMglAproximado(Constant.INFO_NODE_APROX_NO_DATA);
                            detalleFac.setCalculateNodeDistanceWs(false);
                        } else if(
                                (calculateNodeDistance.getCode().equalsIgnoreCase(Constant.RESP_CODE_NODE_APROX_ERR1)) ||
                                (calculateNodeDistance.getCode().equalsIgnoreCase(Constant.RESP_CODE_NODE_APROX_ERR2)) ||
                                (calculateNodeDistance.getCode().equalsIgnoreCase(Constant.RESP_CODE_NODE_APROX_ERR3)) ||
                                (calculateNodeDistance.getCode().equalsIgnoreCase(Constant.RESP_CODE_NODE_APROX_ERR4))
                                ){
                            detalleFac.setNodoMglAproximado(Constant.INFO_NODE_APROX_GEN_ERROR);
                            detalleFac.setCalculateNodeDistanceWs(false);
                        } else {
                            LOGGER.error("Se genero error WS CalculateNodeDistance ConsultarCoberturasV1Bean class ... " +
                                "Response[ Code: " + calculateNodeDistance.getCode() + 
                                ", Message: " + calculateNodeDistance.getMessage() + "]");
                        }
                    }
                } else {
                    //Capturo la tecnologia
                    CmtBasicaMgl tecno = cmtBasicaMglFacadeLocal.findByBasicaCode(detalleFac.getNombreTecnologia(),
                            tipoBasicaTecnologia.getTipoBasicaId());

                    String desviacion = this.consultarParametro(Constants.DESVIACION_EN_METROS_NODO_APROXIMADO);

                    if (desviacion != null && !desviacion.isEmpty()) {
                        if (isNumeric(desviacion)) {
                            int desvMts = Integer.parseInt(desviacion);

                            NodoPoligono nodoPoligono = nodoPoligonoFacadeLocal.findNodosByCoordenadasDirTecnoAndCentro(latitudMod, longitudMod, tecno, centro, desvMts, null);

                            if (nodoPoligono != null) {
                                detalleFac.setDistanciaNodoApro(nodoPoligono.getDistanciaMts());
                                detalleFac.setNodoMglAproximado(nodoPoligono.getNodoVertice());
                                detalleFactibilidadMglFacadeLocal.update(detalleFac);
                                listInfoByPageCob(1);
                            } else {
                                msnError = "No se encontro nodo cercano para la tecnologia seleccionada";
                                alerts();
                            }
                        } else {
                            msnError = "El valor parametrizado para la desviacion en metros no es numerico";
                            alerts();
                        }
                    } else {
                        msnError = "No existe una configuracion para el parametro: " + Constants.DESVIACION_EN_METROS_NODO_APROXIMADO + "";
                        alerts();
                    }
                }
            }
        } else {
            warn("La direccion seleccionada no presenta coordenadas");
        }

    }

    /**
     * Evento llamado al momento de consultar un nodo backup.
     */
    public void consultarNodoBackup(DetalleFactibilidadMgl detalleFac) throws ApplicationException, Exception {
        assignDefaultDetailsToPopup();

        if (direccionDetalladaMglDtoSel != null
                && direccionDetalladaMglDtoSel.getLongitudDir() != null
                && direccionDetalladaMglDtoSel.getLatitudDir() != null) {

            if (direccionDetalladaMglDtoSel.getDireccionMgl() != null
                    && direccionDetalladaMglDtoSel.getDireccionMgl().getUbicacion() != null) {

                String latitudMod = direccionDetalladaMglDtoSel.getLatitudDir().replace(",", ".");
                String longitudMod = direccionDetalladaMglDtoSel.getLongitudDir().replace(",", ".");

                //Capturo el centro poblado
                GeograficoPoliticoMgl centro = direccionDetalladaMglDtoSel.getDireccionMgl().
                        getUbicacion().getGpoIdObj();
                CmtTipoBasicaMgl tipoBasicaTecnologia;
                tipoBasicaTecnologia = cmtTipoBasicaMglFacadeLocal.
                        findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);

                //Capturo la tecnologia
                CmtBasicaMgl tecno = cmtBasicaMglFacadeLocal.findByBasicaCode(detalleFac.getNombreTecnologia(),
                        tipoBasicaTecnologia.getTipoBasicaId());

                String desviacion = this.consultarParametro(Constants.DESVIACION_EN_METROS_NODO_APROXIMADO);

                if (desviacion != null && !desviacion.isEmpty()) {
                    if (isNumeric(desviacion)) {
                        int desvMts = Integer.parseInt(desviacion);
                        NodoMgl nodoMglRef = null;
                        if (detalleFac.getCodigoNodo() != null
                                && !detalleFac.getCodigoNodo().isEmpty()) {
                            nodoMglRef = nodoMglFacadeLocal.findByCodigo(detalleFac.getCodigoNodo());
                        }
                        NodoPoligono nodoPoligono = nodoPoligonoFacadeLocal.
                                findNodosByCoordenadasDirTecnoAndCentro(latitudMod, longitudMod, tecno, centro, desvMts, nodoMglRef);

                        if (nodoPoligono != null) {
                            detalleFac.setNodoMglBackup(nodoPoligono.getNodoVertice());
                            detalleFactibilidadMglFacadeLocal.update(detalleFac);
                            listInfoByPageCob(1);
                        } else {
                            msnError = "No se encontro nodo cercano para la tecnologia seleccionada";
                            alerts();
                        }
                    } else {
                        msnError = "El valor parametrizado para la desviacion en metros no es numerico";
                        alerts();
                    }
                } else {
                    msnError = "No existe una configuracion para el parametro: " + Constants.DESVIACION_EN_METROS_NODO_APROXIMADO + "";
                    alerts();
                }
            }
        } else {
            warn("La direccion seleccionada no presenta coordenadas");
        }

    }

    public void consultarCuadrante(DetalleFactibilidadMgl detalleFac)
            throws ApplicationException, Exception {
        assignDefaultDetailsToPopup();
        //Consulto cuadrante del nodo en OFSC
        String cuadrante = null;
        StringBuilder nombreArrendatarios = new StringBuilder("");
        NodoMgl nodo;

        if (detalleFac != null && detalleFac.getCodigoNodo() != null) {
            nodo = nodoMglFacadeLocal.findByCodigo(detalleFac.getCodigoNodo());

            if (nodo != null) {
                GetActivityBookingResponses responses = arrendatarioMglFacadeLocal.consultarCuadrante(nodo);
                if (responses != null) {
                    cuadrante = responses.getWorkZone();
                }
                //Buscamos los arrendatarios que tiene ese cuadrante
                if (cuadrante != null && !cuadrante.isEmpty()) {
                    final String SEPARADOR = ",";
                    GeograficoPoliticoMgl centro = geograficoPoliticoFacadeLocal.findById(nodo.getGpoId());
                    if (centro != null && centro.getGpoId() != null) {
                        List<ArrendatarioMgl> cuadranteMglsCons = arrendatarioMglFacadeLocal.
                                findArrendatariosByCentro(centro);

                        if (cuadranteMglsCons != null && !cuadranteMglsCons.isEmpty()) {

                            for (ArrendatarioMgl arrendatarioCuadranteMgl : cuadranteMglsCons) {
                                if (arrendatarioCuadranteMgl.getCuadrante().trim().equalsIgnoreCase(cuadrante)
                                        && arrendatarioCuadranteMgl.getEstadoRegistro() == 1) {
                                    nombreArrendatarios.append(arrendatarioCuadranteMgl.getNombreArrendatario());
                                    nombreArrendatarios.append(SEPARADOR);
                                }
                            }
                            if (!nombreArrendatarios.toString().isEmpty()) {
                                detalleFac.setNombreArrendatario(nombreArrendatarios.toString());
                                detalleFactibilidadMglFacadeLocal.update(detalleFac);
                            } else {
                                msnError = "No existen arrendatarios creados para el cuadrante: " + cuadrante + " del Nodo";
                                alerts();
                            }
                        } else {
                            msnError = "No hay creados arrendatarios por cuadrantes para el centro poblado";
                            alerts();
                        }
                    } else {
                        msnError = "No existe centro poblado con el id= " + nodo.getGpoId() + " ";
                        alerts();
                    }
                } else {
                    msnError = "No existen acuadrante en OFSC para el nodo: " + nodo.getNodCodigo() + "";
                    alerts();
                }
            }
        } else {
            msnError = "No hay un nodo para consultar el cuadrante en OFSC";
            alerts();
        }
    }

    /**
     * Asigna los valores por defecto para los títulos e imagen asignada al popup de errores y validaciones.
     *
     * @author Gildardo Mora
     */
    private void assignDefaultDetailsToPopup() {
        tituloPopupWarning = "!Error de Validación!";
        tituloMsgDetallePopupWarning = "Se ha generado un error de validación";
        isWarningMessage = false;
    }

    public List<String> returnList(String nombreArrendatarios) {

        cuadranteMgls = new ArrayList<>();
        if (nombreArrendatarios != null && nombreArrendatarios.contains(",")) {
            String[] parts = nombreArrendatarios.split(",");
            if (parts != null && parts.length > 0) {
                for (String parte : parts) {
                    cuadranteMgls.add(parte);
                }
            }
        }

        return cuadranteMgls;
    }

    /**
     * Volver al panel de historico
     *
     * @throws ApplicationException
     */
    public void volverHistorico() throws ApplicationException {
        mostrarDetalleHistorico = false;

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

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
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

    public BigDecimal getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(BigDecimal idCiudad) {
        this.idCiudad = idCiudad;
    }

    public BigDecimal getIdDpto() {
        return idDpto;
    }

    public void setIdDpto(BigDecimal idDpto) {
        this.idDpto = idDpto;
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

    public String getMsnError() {
        return msnError;
    }

    public void setMsnError(String msnError) {
        this.msnError = msnError;
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

    public List<GeograficoPoliticoMgl> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<GeograficoPoliticoMgl> departamentoList) {
        this.departamentoList = departamentoList;
    }

    public List<GeograficoPoliticoMgl> getCiudadesList() {
        return ciudadesList;
    }

    public void setCiudadesList(List<GeograficoPoliticoMgl> ciudadesList) {
        this.ciudadesList = ciudadesList;
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

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
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

    public List<DetalleFactibilidadMgl> getDetalleFactibilidadMgls() {
        return detalleFactibilidadMgls;
    }

    public void setDetalleFactibilidadMgls(List<DetalleFactibilidadMgl> detalleFactibilidadMgls) {
        this.detalleFactibilidadMgls = detalleFactibilidadMgls;
    }

    public boolean isShowPanelCoordenadas() {
        return showPanelCoordenadas;
    }

    public void setShowPanelCoordenadas(boolean showPanelCoordenadas) {
        this.showPanelCoordenadas = showPanelCoordenadas;
    }

    public DetalleFactibilidadMgl getDetalleFactibilidadMglSelected() {
        return detalleFactibilidadMglSelected;
    }

    public void setDetalleFactibilidadMglSelected(DetalleFactibilidadMgl detalleFactibilidadMglSelected) {
        this.detalleFactibilidadMglSelected = detalleFactibilidadMglSelected;
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

    public boolean isVerListadoDirecciones() {
        return verListadoDirecciones;
    }

    public void setVerListadoDirecciones(boolean verListadoDirecciones) {
        this.verListadoDirecciones = verListadoDirecciones;
    }

    public List<ResponseConstruccionDireccion> getLstResponseConstruirDireccion() {
        return lstResponseConstruirDireccion;
    }

    public void setLstResponseConstruirDireccion(List<ResponseConstruccionDireccion> lstResponseConstruirDireccion) {
        this.lstResponseConstruirDireccion = lstResponseConstruirDireccion;
    }

    public boolean isMostrarBotonAtras() {
        return mostrarBotonAtras;
    }

    public void setMostrarBotonAtras(boolean mostrarBotonAtras) {
        this.mostrarBotonAtras = mostrarBotonAtras;
    }

    public boolean isShowPanelIngresarUbicacion() {
        return showPanelIngresarUbicacion;
    }

    public void setShowPanelIngresarUbicacion(boolean showPanelIngresarUbicacion) {
        this.showPanelIngresarUbicacion = showPanelIngresarUbicacion;
    }

    public boolean isShowPanelIngresarGPS() {
        return showPanelIngresarGPS;
    }

    public void setShowPanelIngresarGPS(boolean showPanelIngresarGPS) {
        this.showPanelIngresarGPS = showPanelIngresarGPS;
    }

    public List<CmtRequestHhppByCoordinatesDto> getRequestCoordenadas() {
        return requestCoordenadas;
    }

    public void setRequestCoordenadas(List<CmtRequestHhppByCoordinatesDto> requestCoordenadas) {
        this.requestCoordenadas = requestCoordenadas;
    }

    public String getRutaServlet() {
        return rutaServlet;
    }

    public void setRutaServlet(String rutaServlet) {
        this.rutaServlet = rutaServlet;
    }

    public BigDecimal getIdFactibilidad() {
        return idFactibilidad;
    }

    public void setIdFactibilidad(BigDecimal idFactibilidad) {
        this.idFactibilidad = idFactibilidad;
    }

    public String getNameEdificio() {
        return nameEdificio;
    }

    public void setNameEdificio(String nameEdificio) {
        this.nameEdificio = nameEdificio;
    }

    public List<String> getNameCentroPobladoList() {
        return nameCentroPobladoList;
    }

    public void setNameCentroPobladoList(List<String> nameCentroPobladoList) {
        this.nameCentroPobladoList = nameCentroPobladoList;
    }

    public String getNombreCentroPoblado() {
        return nombreCentroPoblado;
    }

    public void setNombreCentroPoblado(String nombreCentroPoblado) {
        this.nombreCentroPoblado = nombreCentroPoblado;
    }

    public boolean isHabilitaDpto() {
        return habilitaDpto;
    }

    public void setHabilitaDpto(boolean habilitaDpto) {
        this.habilitaDpto = habilitaDpto;
    }

    public boolean isHabilitaCity() {
        return habilitaCity;
    }

    public void setHabilitaCity(boolean habilitaCity) {
        this.habilitaCity = habilitaCity;
    }

    public List<CmtCuentaMatrizMgl> getListCcmm() {
        return listCcmm;
    }

    public void setListCcmm(List<CmtCuentaMatrizMgl> listCcmm) {
        this.listCcmm = listCcmm;
    }

    public CmtCuentaMatrizMgl getCcmmSeleccionada() {
        return ccmmSeleccionada;
    }

    public void setCcmmSeleccionada(CmtCuentaMatrizMgl ccmmSeleccionada) {
        this.ccmmSeleccionada = ccmmSeleccionada;
    }

    public String getFechaDia() {
        return fechaDia;
    }

    public void setFechaDia(String fechaDia) {
        this.fechaDia = fechaDia;
    }

    public String getFechaCreacionFactibilidad() {
        return fechaCreacionFactibilidad;
    }

    public void setFechaCreacionFactibilidad(String fechaCreacionFactibilidad) {
        this.fechaCreacionFactibilidad = fechaCreacionFactibilidad;
    }

    public String getHoraCreacionFactibilidad() {
        return horaCreacionFactibilidad;
    }

    public void setHoraCreacionFactibilidad(String horaCreacionFactibilidad) {
        this.horaCreacionFactibilidad = horaCreacionFactibilidad;
    }

    public String getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(String usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public boolean isDirIsCcmm() {
        return dirIsCcmm;
    }

    public void setDirIsCcmm(boolean dirIsCcmm) {
        this.dirIsCcmm = dirIsCcmm;
    }

    public boolean isDirIsHhpp() {
        return dirIsHhpp;
    }

    public void setDirIsHhpp(boolean dirIsHhpp) {
        this.dirIsHhpp = dirIsHhpp;
    }

    public List<CmtSubEdificioMgl> getEdificiosCcmmList() {
        return edificiosCcmmList;
    }

    public void setEdificiosCcmmList(List<CmtSubEdificioMgl> edificiosCcmmList) {
        this.edificiosCcmmList = edificiosCcmmList;
    }

    public List<HhppMgl> getHhppCcmmList() {
        return hhppCcmmList;
    }

    public void setHhppCcmmList(List<HhppMgl> hhppCcmmList) {
        this.hhppCcmmList = hhppCcmmList;
    }

    public CmtSubEdificioMgl getCmtSubEdificioMglSel() {
        return cmtSubEdificioMglSel;
    }

    public void setCmtSubEdificioMglSel(CmtSubEdificioMgl cmtSubEdificioMglSel) {
        this.cmtSubEdificioMglSel = cmtSubEdificioMglSel;
    }

    public String getMapaGoogle() {
        return mapaGoogle;
    }

    public void setMapaGoogle(String mapaGoogle) {
        this.mapaGoogle = mapaGoogle;
    }

    public List<DetalleSlaEjecucionMgl> getDetalleSlaEjecucion() {
        return detalleSlaEjecucion;
    }

    public void setDetalleSlaEjecucion(List<DetalleSlaEjecucionMgl> detalleSlaEjecucion) {
        this.detalleSlaEjecucion = detalleSlaEjecucion;
    }

    public boolean isControlCcmmDetalle() {
        return controlCcmmDetalle;
    }

    public void setControlCcmmDetalle(boolean controlCcmmDetalle) {
        this.controlCcmmDetalle = controlCcmmDetalle;
    }

    public boolean isControlHhppDetalle() {
        return controlHhppDetalle;
    }

    public void setControlHhppDetalle(boolean controlHhppDetalle) {
        this.controlHhppDetalle = controlHhppDetalle;
    }

    public String getColorTecno() {
        return colorTecno;
    }

    public void setColorTecno(String colorTecno) {
        this.colorTecno = colorTecno;
    }

    public boolean isMostrarValidacionCobertura() {
        return mostrarValidacionCobertura;
    }

    public void setMostrarValidacionCobertura(boolean mostrarValidacionCobertura) {
        this.mostrarValidacionCobertura = mostrarValidacionCobertura;
    }

    public boolean isPanelDetalleCobertura() {
        return panelDetalleCobertura;
    }

    public void setPanelDetalleCobertura(boolean panelDetalleCobertura) {
        this.panelDetalleCobertura = panelDetalleCobertura;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getCorreoUsuarioCopia() {
        return correoUsuarioCopia;
    }

    public void setCorreoUsuarioCopia(String correoUsuarioCopia) {
        this.correoUsuarioCopia = correoUsuarioCopia;
    }

    public List<CmtBasicaMgl> getListBasicaDivisional() {
        return listBasicaDivisional;
    }

    public void setListBasicaDivisional(List<CmtBasicaMgl> listBasicaDivisional) {
        this.listBasicaDivisional = listBasicaDivisional;
    }

    public BigDecimal getDivisional() {
        return divisional;
    }

    public void setDivisional(BigDecimal divisional) {
        this.divisional = divisional;
    }

    public boolean isMostrarMapaCober() {
        return mostrarMapaCober;
    }

    public void setMostrarMapaCober(boolean mostrarMapaCober) {
        this.mostrarMapaCober = mostrarMapaCober;
    }

    public boolean isCrearCcmmMenuItem() {
        return crearCcmmMenuItem;
    }

    public void setCrearCcmmMenuItem(boolean crearCcmmMenuItem) {
        this.crearCcmmMenuItem = crearCcmmMenuItem;
    }

    public boolean isModCcmmMenuItem() {
        return modCcmmMenuItem;
    }

    public void setModCcmmMenuItem(boolean modCcmmMenuItem) {
        this.modCcmmMenuItem = modCcmmMenuItem;
    }

    public boolean isCrearModHhppMenuItem() {
        return crearModHhppMenuItem;
    }

    public void setCrearModHhppMenuItem(boolean crearModHhppMenuItem) {
        this.crearModHhppMenuItem = crearModHhppMenuItem;
    }

    public boolean isCrearVtMenuItem() {
        return crearVtMenuItem;
    }

    public void setCrearVtMenuItem(boolean crearVtMenuItem) {
        this.crearVtMenuItem = crearVtMenuItem;
    }

    public boolean isCrearHhppMenuItem() {
        return crearHhppMenuItem;
    }

    public void setCrearHhppMenuItem(boolean crearHhppMenuItem) {
        this.crearHhppMenuItem = crearHhppMenuItem;
    }

    public boolean isValidarCoberturaMenuItem() {
        return validarCoberturaMenuItem;
    }

    public void setValidarCoberturaMenuItem(boolean validarCoberturaMenuItem) {
        this.validarCoberturaMenuItem = validarCoberturaMenuItem;
    }

    public boolean isHistoricoMenuItem() {
        return historicoMenuItem;
    }

    public void setHistoricoMenuItem(boolean historicoMenuItem) {
        this.historicoMenuItem = historicoMenuItem;
    }

    public List<CmtBasicaMgl> getListaTecnlogias() {
        return listaTecnlogias;
    }

    public void setListaTecnlogias(List<CmtBasicaMgl> listaTecnlogias) {
        this.listaTecnlogias = listaTecnlogias;
    }

    public boolean isMostrarHistorico() {
        return mostrarHistorico;
    }

    public void setMostrarHistorico(boolean mostrarHistorico) {
        this.mostrarHistorico = mostrarHistorico;
    }

    public List<FactibilidadMgl> getListaFactibilidades() {
        return listaFactibilidades;
    }

    public void setListaFactibilidades(List<FactibilidadMgl> listaFactibilidades) {
        this.listaFactibilidades = listaFactibilidades;
    }

    public List<CmtDireccionDetalladaMglDto> getListaHistorico() {
        return listaHistorico;
    }

    public void setListaHistorico(List<CmtDireccionDetalladaMglDto> listaHistorico) {
        this.listaHistorico = listaHistorico;
    }

    public CmtDireccionDetalladaMglDto getDireccionHistoricoSelected() {
        return direccionHistoricoSelected;
    }

    public void setDireccionHistoricoSelected(CmtDireccionDetalladaMglDto direccionHistoricoSelected) {
        this.direccionHistoricoSelected = direccionHistoricoSelected;
    }

    public List<CmtDireccionDetalladaMglDto> getFilteredDireccionHistDetalladaList() {
        return filteredDireccionHistDetalladaList;
    }

    public void setFilteredDireccionHistDetalladaList(List<CmtDireccionDetalladaMglDto> filteredDireccionHistDetalladaList) {
        this.filteredDireccionHistDetalladaList = filteredDireccionHistDetalladaList;
    }

    public boolean isMostrarDetalleHistorico() {
        return mostrarDetalleHistorico;
    }

    public void setMostrarDetalleHistorico(boolean mostrarDetalleHistorico) {
        this.mostrarDetalleHistorico = mostrarDetalleHistorico;
    }

    public List<DetalleFactibilidadMgl> getListaDetalleHistorico() {
        return listaDetalleHistorico;
    }

    public void setListaDetalleHistorico(List<DetalleFactibilidadMgl> listaDetalleHistorico) {
        this.listaDetalleHistorico = listaDetalleHistorico;
    }

    public CmtDireccionDetalladaMglDto getDireccionHistoricoDetalleSelected() {
        return direccionHistoricoDetalleSelected;
    }

    public void setDireccionHistoricoDetalleSelected(CmtDireccionDetalladaMglDto direccionHistoricoDetalleSelected) {
        this.direccionHistoricoDetalleSelected = direccionHistoricoDetalleSelected;
    }

    public List<String> getListaDesplegableFechaIdFact() {
        return listaDesplegableFechaIdFact;
    }

    public void setListaDesplegableFechaIdFact(List<String> listaDesplegableFechaIdFact) {
        this.listaDesplegableFechaIdFact = listaDesplegableFechaIdFact;
    }

    public GeograficoPoliticoMgl getCentroPobladoSolSelected() {
        return centroPobladoSolSelected;
    }

    public void setCentroPobladoSolSelected(GeograficoPoliticoMgl centroPobladoSolSelected) {
        this.centroPobladoSolSelected = centroPobladoSolSelected;
    }

    public GeograficoPoliticoMgl getCiudadSolSelected() {
        return ciudadSolSelected;
    }

    public void setCiudadSolSelected(GeograficoPoliticoMgl ciudadSolSelected) {
        this.ciudadSolSelected = ciudadSolSelected;
    }

    public GeograficoPoliticoMgl getDptoSolSelected() {
        return dptoSolSelected;
    }

    public void setDptoSolSelected(GeograficoPoliticoMgl dptoSolSelected) {
        this.dptoSolSelected = dptoSolSelected;
    }

    public BigDecimal getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(BigDecimal tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFiltroIdFactFecha() {
        return filtroIdFactFecha;
    }

    public void setFiltroIdFactFecha(String filtroIdFactFecha) {
        this.filtroIdFactFecha = filtroIdFactFecha;
    }

    public Map<String, BigDecimal> getListaFechaIdFact() {
        return listaFechaIdFact;
    }

    public void setListaFechaIdFact(Map<String, BigDecimal> listaFechaIdFact) {
        this.listaFechaIdFact = listaFechaIdFact;
    }

    public UploadedFile getUpFile() {
        return upFile;
    }

    public void setUpFile(UploadedFile upFile) {
        this.upFile = upFile;
    }

    public boolean isEnproceso() {
        return enproceso = MasivoControlFactibilidadDtoMgl.isIsProcessing();
    }

    public void setEnproceso(boolean enproceso) {
        this.enproceso = enproceso;
    }

    public String getNombre() {
        this.nombre = MasivoControlFactibilidadDtoMgl.getNombreArchivo();
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuarioProceso() {
        this.usuarioProceso = MasivoControlFactibilidadDtoMgl.getUserRunningProcess();
        return usuarioProceso;
    }

    public void setUsuarioProceso(String usuarioProceso) {
        this.usuarioProceso = usuarioProceso;
    }

    public double getCantidadRegistros() {
        this.cantidadRegistros = MasivoControlFactibilidadDtoMgl.getNumeroRegistrosProcesados();
        return cantidadRegistros;
    }

    public void setCantidadRegistros(double cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    public Integer getTotalRegistros() {
        this.totalRegistros = MasivoControlFactibilidadDtoMgl.getNumeroRegistrosAProcesar();
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public boolean isFinish() {
        this.finish = MasivoControlFactibilidadDtoMgl.isIsFinished();
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

    public Integer getRefresh() {
        return refresh;
    }

    public void setRefresh(Integer refresh) {
        this.refresh = refresh;
    }

    public List<String> getLineasArchivoRetorno() {
        this.lineasArchivoRetorno = MasivoControlFactibilidadDtoMgl.getListArchivoWithErrors();
        return lineasArchivoRetorno;
    }

    public void setLineasArchivoRetorno(List<String> lineasArchivoRetorno) {
        this.lineasArchivoRetorno = lineasArchivoRetorno;
    }

    public List<CmtDireccionDetalladaMglDto> getListResultadoMasivo() {
        return listResultadoMasivo = MasivoControlFactibilidadDtoMgl.getListDireccionesDetalladas();
    }

    public void setListResultadoMasivo(List<CmtDireccionDetalladaMglDto> listResultadoMasivo) {
        this.listResultadoMasivo = listResultadoMasivo;
    }

    public List<CmtDireccionDetalladaMgl> getUniExistentesList() {
        return uniExistentesList;
    }

    public void setUniExistentesList(List<CmtDireccionDetalladaMgl> uniExistentesList) {
        this.uniExistentesList = uniExistentesList;
    }

    public boolean isDirIsUnidades() {
        return dirIsUnidades;
    }

    public void setDirIsUnidades(boolean dirIsUnidades) {
        this.dirIsUnidades = dirIsUnidades;
    }

    public CmtDireccionDetalladaMglDto getDireccionDetalladaMglDtoSelUnidad() {
        return direccionDetalladaMglDtoSelUnidad;
    }

    public void setDireccionDetalladaMglDtoSelUnidad(CmtDireccionDetalladaMglDto direccionDetalladaMglDtoSelUnidad) {
        this.direccionDetalladaMglDtoSelUnidad = direccionDetalladaMglDtoSelUnidad;
    }

    public BigDecimal getIdSubEdificioMglSel() {
        return idSubEdificioMglSel;
    }

    public void setIdSubEdificioMglSel(BigDecimal idSubEdificioMglSel) {
        this.idSubEdificioMglSel = idSubEdificioMglSel;
    }

    public List<DetalleFactibilidadLogDto> getDetalleFactibilidadLogDto() {
        return detalleFactibilidadLogDto;
    }

    public void setDetalleFactibilidadLogDto(List<DetalleFactibilidadLogDto> detalleFactibilidadLogDto) {
        this.detalleFactibilidadLogDto = detalleFactibilidadLogDto;
    }

    public boolean isVerListadoLogDirecciones() {
        return verListadoLogDirecciones;
    }

    public void setVerListadoLogDirecciones(boolean verListadoLogDirecciones) {
        this.verListadoLogDirecciones = verListadoLogDirecciones;
    }
    public boolean isCorporativo() {
        return corporativo;
    }

    public void setCorporativo(boolean corporativo) {
        this.corporativo = corporativo;
    }

    public boolean isResidencial() {
        return residencial;
    }

    public void setResidencial(boolean residencial) {
        this.residencial = residencial;
    }

    public CustomerOrderResponse getResponseCustomerOrdersTipoSolicitud() {
        return responseCustomerOrdersTipoSolicitud;
    }

    public void setResponseCustomerOrdersTipoSolicitud(CustomerOrderResponse responseCustomerOrdersTipoSolicitud) {
        this.responseCustomerOrdersTipoSolicitud = responseCustomerOrdersTipoSolicitud;
    }

    public CustomerOrderResponse getResponseCustomerOrdersTipoServicio() {
        return responseCustomerOrdersTipoServicio;
    }

    public void setResponseCustomerOrdersTipoServicio(CustomerOrderResponse responseCustomerOrdersTipoServicio) {
        this.responseCustomerOrdersTipoServicio = responseCustomerOrdersTipoServicio;
    }

    public CustomerOrderResponse getResponseCustomerOrdersTipoValidacion() {
        return responseCustomerOrdersTipoValidacion;
    }

    public void setResponseCustomerOrdersTipoValidacion(CustomerOrderResponse responseCustomerOrdersTipoValidacion) {
        this.responseCustomerOrdersTipoValidacion = responseCustomerOrdersTipoValidacion;
    }

    public CustomerOrderResponse getResponseCustomerOrdersbw() {
        return responseCustomerOrdersbw;
    }

    public void setResponseCustomerOrdersbw(CustomerOrderResponse responseCustomerOrdersbw) {
        this.responseCustomerOrdersbw = responseCustomerOrdersbw;
    }

    public CustomerOrderResponse getResponseCustomerOrdersTipoSitio() {
        return responseCustomerOrdersTipoSitio;
    }

    public void setResponseCustomerOrdersTipoSitio(CustomerOrderResponse responseCustomerOrdersTipoSitio) {
        this.responseCustomerOrdersTipoSitio = responseCustomerOrdersTipoSitio;
    }

    public List<ColumnDto> getTipoSolicitudList() {
        return tipoSolicitudList;
    }

    public void setTipoSolicitudList(List<ColumnDto> tipoSolicitudList) {
        this.tipoSolicitudList = tipoSolicitudList;
    }

    public List<ColumnDto> getTipoServicioList() {
        return tipoServicioList;
    }

    public void setTipoServicioList(List<ColumnDto> tipoServicioList) {
        this.tipoServicioList = tipoServicioList;
    }

    public List<ColumnDto> getTipoValidacionList() {
        return tipoValidacionList;
    }

    public void setTipoValidacionList(List<ColumnDto> tipoValidacionList) {
        this.tipoValidacionList = tipoValidacionList;
    }

    public List<ColumnDto> getBwList() {
        return bwList;
    }

    public void setBwList(List<ColumnDto> bwList) {
        this.bwList = bwList;
    }

    public List<TipoHhppMgl> getTipoSitioList() {
        return tipoSitioList;
    }

    public void setTipoSitioList(List<TipoHhppMgl> tipoSitioList) {
        this.tipoSitioList = tipoSitioList;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getTipoValidacion() {
        return tipoValidacion;
    }

    public void setTipoValidacion(String tipoValidacion) {
        this.tipoValidacion = tipoValidacion;
    }

    public String getBw() {
        return bw;
    }

    public void setBw(String bw) {
        this.bw = bw;
    }

    public String getTipoSitio() {
        return tipoSitio;
    }

    public void setTipoSitio(String tipoSitio) {
        this.tipoSitio = tipoSitio;
    }

    public DetalleHhppBean getDetalleHhppBean() {
        return detalleHhppBean;
    }

    public void setDetalleHhppBean(DetalleHhppBean detalleHhppBean) {
        this.detalleHhppBean = detalleHhppBean;
    }
    

    @Override
    public void update(Observable obs, Object o) {
        if (o instanceof MasivoFactibilidadDto) {//retorna por cada registro procesado de la lista
            MasivoFactibilidadDto mmDto = (MasivoFactibilidadDto) o;
        } else if (o instanceof List) {//retorna cuando el proceso termino
        }
    }

    public void georeferenciarDireccionActualizarCoberturas(ResponseConstruccionDireccion responseConstruccionDireccion,
                                                            GeograficoPoliticoMgl centroPobladoSelected, DrDireccion drDireccionTmp) {
        try {
            if (responseConstruccionDireccion != null && responseConstruccionDireccion.getDireccionStr() != null
                    && !responseConstruccionDireccion.getDireccionStr().isEmpty()
                    && centroPobladoSelected != null && centroPobladoSelected.getGeoCodigoDane() != null
                    && drDireccionTmp != null && drDireccionTmp.getIdTipoDireccion() != null &&
                    !drDireccionTmp.getIdTipoDireccion().isEmpty()
                    && !drDireccionTmp.getIdTipoDireccion().equalsIgnoreCase("IT")) {
                /*Consultar el WebService parera obtener los datos de la direccion
                 * ingresada */
                AddressRequest requestSrv = new AddressRequest();

                requestSrv.setCodDaneVt(centroPobladoSelected.getGeoCodigoDane());
                //Asignar la ciudad
                requestSrv.setCity(centroPobladoSelected.getGpoNombre());
                //Asignar el departamento
                requestSrv.setState("");
                //Asignar la direccion a consultar
                requestSrv.setAddress(responseConstruccionDireccion.getDireccionStr());

                if (drDireccionTmp.getIdTipoDireccion().equalsIgnoreCase("CK")
                        && centroPobladoSelected.getGpoMultiorigen().equalsIgnoreCase("1")
                        && drDireccionTmp.getBarrio() != null && !drDireccionTmp.getBarrio().isEmpty()) {
                    //Asignar el barrio
                    requestSrv.setNeighborhood(drDireccionTmp.getBarrio());
                    requestSrv.setTipoDireccion("CK");
                }
                requestSrv.setLevel(co.com.telmex.catastro.services.util.Constant.TIPO_CONSULTA_COMPLETA);

                AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
                addressEJBRemote.queryAddressEnrich(requestSrv);
            }

        } catch (ApplicationException e) {
            LOGGER.error("NO fue posible georeferenciar correctamente la dirección", e);
        }
    }

    public void activarCorporativo() {
        residencial = false;
        divisional = null;
        tecnologia = null;
        tipoSolicitud = null;        
        tipoServicio = null;        
        tipoValidacion = null;        
        bw = null;        
        tipoSitio = null;
    }
    
    public void activarResidencial() {
        corporativo = false;
        divisional = null;
        tecnologia = null;
        tipoSolicitud = null;        
        tipoServicio = null;        
        tipoValidacion = null;        
        bw = null;        
        tipoSitio = null;
    }

    //Metodo para mostrar alertas, errores, y advertencias en la vista
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

    public boolean tecnologiaValidaCobertura(CmtBasicaMgl tecnologia,
                                             CmtTipoBasicaExtMgl tipoBasicaExtMglVC) {

        boolean validaCob = false;

        if (tecnologia != null) {

            List<CmtBasicaExtMgl> extenList = tecnologia.getListCmtBasicaExtMgl();
            if (extenList != null && !extenList.isEmpty() && tipoBasicaExtMglVC != null) {
                for (CmtBasicaExtMgl basicaExtMgl : extenList) {

                    if (Objects.equals(basicaExtMgl.getIdTipoBasicaExt().
                            getIdTipoBasicaExt(), tipoBasicaExtMglVC.getIdTipoBasicaExt())
                            && basicaExtMgl.getIdBasicaObj().getBasicaId().compareTo(tecnologia.getBasicaId()) == 0
                            && basicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")
                            && basicaExtMgl.getEstadoRegistro() == 1) {
                        validaCob = true;
                    }
                }

            }
        }

        return validaCob;
    }

    public String devuelveDir(BigDecimal idDetallada)
            throws ApplicationException {

        if (idDetallada != null) {
            CmtDireccionDetalladaMgl direccionDetalladaMgl = cmtDireccionDetalleMglFacadeLocal.
                    buscarDireccionIdDireccion(idDetallada);
            drDireccion = cmtDireccionDetalleMglFacadeLocal.
                    parseCmtDireccionDetalladaMglToDrDireccion(direccionDetalladaMgl);

            if (direccionDetalladaMgl.getDireccion().getUbicacion() != null && direccionDetalladaMgl.getDireccion().getUbicacion().getGpoIdObj() != null
                    && direccionDetalladaMgl.getDireccion().getUbicacion().getGpoIdObj().getGpoMultiorigen().equalsIgnoreCase("1")
                    && direccionDetalladaMgl.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                String direccion = direccionRRFacadeLocal.getDireccion(drDireccion);
                String barrio = drDireccion.getBarrio() != null ? drDireccion.getBarrio().toUpperCase() : "";

                return direccion + " " + barrio;
            } else {
                return direccionRRFacadeLocal.getDireccion(drDireccion);
            }


        } else {
            return "";
        }

    }

    public String getObservacionesSolicitudValCobertura() {
        return observacionesSolicitudValCobertura;
    }

    public void setObservacionesSolicitudValCobertura(String observacionesSolicitudValCobertura) {
        this.observacionesSolicitudValCobertura = observacionesSolicitudValCobertura;
    }

    public List<String> getCuadranteMgls() {
        return cuadranteMgls;
    }

    public void setCuadranteMgls(List<String> cuadranteMgls) {
        this.cuadranteMgls = cuadranteMgls;
    }

    public String getCuadranteArrendatario() {
        return cuadranteArrendatario;
    }

    public void setCuadranteArrendatario(String cuadranteArrendatario) {
        this.cuadranteArrendatario = cuadranteArrendatario;
    }

    @XmlTransient
    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    /*metodo que valida estrato si se cumple una condicion la muestra ya que
    lo solicitado es un campo String anivel de base de dato el campo es Bigdecimal
    para no modificar codigo afectar otros modulos se aplica esta validacion anivel
     de vista
      */
    public String valEstrato() {
        String dirEstrato = "";

        try {

            if (direccionDetalladaMglDtoSel.getDireccionMgl().getDirNivelSocioecono() != null) {
                int valEstrato = Integer.parseInt(String.valueOf(direccionDetalladaMglDtoSel.getDireccionMgl().getDirNivelSocioecono()));

                if (valEstrato <= 6 && valEstrato >= 0) {

                    dirEstrato = direccionDetalladaMglDtoSel.getDireccionMgl().getDirNivelSocioecono() == null ? "NG" : String.valueOf(direccionDetalladaMglDtoSel.getDireccionMgl().getDirNivelSocioecono());

                } else {
                    dirEstrato = "NG";
                }
            } else {
                dirEstrato = "NG";
            }
            return dirEstrato;
        } catch (Exception e) {
            return e.getMessage().toString();

        }

    }

    private void customerOrder() {
        try {
            String url = parametrosMglFacadeLocal.findByAcronimoName(Constant.PROPERTY_URL_WS_RS_CUSORDE_FEASIB_PROFILING).getParValor();
            URL url1 = new URL(url);
            ConsumoGenerico.conectionWsdlTest(url1, Constant.PROPERTY_URL_WS_RS_CUSORDE_FEASIB_PROFILING);
            CustomerOrderRequest requestCustomerOrder = new CustomerOrderRequest();
            
            tipoSolicitud = null;
            tipoServicio = null;
            tipoValidacion = null;
            bw = null;
            tipoSitio = null;
            tipoSolicitudList = new ArrayList<>();
            tipoServicioList = new ArrayList<>();
            tipoValidacionList = new ArrayList<>();
            bwList = new ArrayList<>();
            tipoSitioList = new ArrayList<>();
            requestCustomerOrder.setIdOption(1);
            requestCustomerOrder.setCodDepend("0");
            requestCustomerOrder.setRelationParameter("0");
            responseCustomerOrdersTipoSolicitud = detalleFactibilidadMglFacadeLocal.listCustomerOrder(url, requestCustomerOrder);
            for (RowSet.Row row : responseCustomerOrdersTipoSolicitud.getCursor().getRow()) {
                ColumnDto column = new ColumnDto();
                column.setKey(row.getColumn().get(0).getValue());
                column.setValue(row.getColumn().get(1).getValue());
                tipoSolicitudList.add(column);
            }
            requestCustomerOrder.setIdOption(2);
            requestCustomerOrder.setCodDepend("0");
            requestCustomerOrder.setRelationParameter("0");
            responseCustomerOrdersTipoServicio =  detalleFactibilidadMglFacadeLocal.listCustomerOrder(url, requestCustomerOrder);
            for (RowSet.Row row : responseCustomerOrdersTipoServicio.getCursor().getRow()) {
                ColumnDto column = new ColumnDto();
                column.setKey(row.getColumn().get(0).getValue());
                column.setValue(row.getColumn().get(1).getValue());
                tipoServicioList.add(column);
            }
            requestCustomerOrder.setIdOption(4);
            requestCustomerOrder.setCodDepend("0");
            requestCustomerOrder.setRelationParameter("0");
            responseCustomerOrdersbw =  detalleFactibilidadMglFacadeLocal.listCustomerOrder(url, requestCustomerOrder);
            for (RowSet.Row row : responseCustomerOrdersbw.getCursor().getRow()) {
                ColumnDto column = new ColumnDto();
                column.setKey(row.getColumn().get(0).getValue());
                column.setValue(row.getColumn().get(1).getValue());
                bwList.add(column);
            }
            tipoSitioList = tipoHhppMglFacadeLocal.findAll();
            
        } catch (ApplicationException | IOException ex) {
            LOGGER.error("NO fue posible consultar servicio customerOrder correctamente ", ex);
        }
    }
    
    public void customerOrderTipoValidacion() throws ApplicationException{
        assignDefaultDetailsToPopup();
        try {
            String url = parametrosMglFacadeLocal.findByAcronimoName(Constant.PROPERTY_URL_WS_RS_CUSORDE_FEASIB_PROFILING).getParValor();
            URL url1 = new URL(url);
            ConsumoGenerico.conectionWsdlTest(url1, Constant.PROPERTY_URL_WS_RS_CUSORDE_FEASIB_PROFILING);
            CustomerOrderRequest requestCustomerOrder = new CustomerOrderRequest();
            tipoValidacionList = new ArrayList<>();
            requestCustomerOrder.setIdOption(3);
            requestCustomerOrder.setCodDepend("1");
            requestCustomerOrder.setRelationParameter(tipoSolicitud);
            responseCustomerOrdersTipoValidacion =  detalleFactibilidadMglFacadeLocal.listCustomerOrder(url, requestCustomerOrder);
            for (RowSet.Row row : responseCustomerOrdersTipoValidacion.getCursor().getRow()) {
                ColumnDto column = new ColumnDto();
                column.setKey(row.getColumn().get(0).getValue());
                column.setValue(row.getColumn().get(1).getValue());
                tipoValidacionList.add(column);
            }
            
        } catch (IOException ex) {
            LOGGER.error("NO fue posible consultar servicio customerOrderTipoValidacion correctamente ", ex);
        }
    }

    private String buscarTipoSolicitud() {
            for (RowSet.Row row : responseCustomerOrdersTipoSolicitud.getCursor().getRow()) {
                if (tipoSolicitud.equals(row.getColumn().get(0).getValue())) {
                    return row.getColumn().get(1).getValue();
                }             
            }
        return null;
    }
    
    public String getTipoSolicitudSelected() {
        return tipoSolicitudSelected;
    }

    public void setTipoSolicitudSelected(String tipoSolicitudSelected) {
        this.tipoSolicitudSelected = tipoSolicitudSelected;
    }

    public String getTipoServicioSelected() {
        return tipoServicioSelected;
    }

    public void setTipoServicioSelected(String tipoServicioSelected) {
        this.tipoServicioSelected = tipoServicioSelected;
    }

    public boolean isCorporativoPrincipal() {
        return corporativoPrincipal;
    }

    public void setCorporativoPrincipal(boolean corporativoPrincipal) {
        this.corporativoPrincipal = corporativoPrincipal;
    }
    
    /**
     * Funcion utilizada para encontrar nodos certificado
     * @param uniExistentesList
     * @return
     * @throws ApplicationException 
     * @author camiloram
     */
    private List<CmtDireccionDetalladaMgl> agregarNodoCertificado(List<CmtDireccionDetalladaMgl> uniExistentesList) throws ApplicationException {
        List<CmtDireccionDetalladaMgl> direccionDetalladaListNodos = new ArrayList<>();
        List<HhppMgl> hhppList = new ArrayList<>();
        HhppMglManager hhppMglManager = new HhppMglManager();
        for (CmtDireccionDetalladaMgl direccion : uniExistentesList) {
            if (direccion.isHhppExistente()) {
                if (direccion.getSubDireccion() != null && direccion.getSubDireccion().getSdiId() != null) {
                    hhppList = hhppMglManager
                            .findHhppSubDireccion(direccion.getSubDireccion().getSdiId());
                } else {
                    hhppList = hhppMglManager
                            .findHhppDireccion(direccion.getDireccion().getDirId());
                }
                for (HhppMgl hhppMgl : hhppList) {
                    String nodo = (nodoMglFacadeLocal.findById(hhppMgl.getNodId().getNodId())).getEstado().getNombreBasica();
                    if (nodo.equals(Constant.NODO_CERTIFICADO)) {
                        direccion.setIsNodoCertificado(true);
                        break;
                    } else {
                        direccion.setIsNodoCertificado(false);
                    }
                }
            }
            direccionDetalladaListNodos.add(direccion);
        }
        
        return direccionDetalladaListNodos;
    }

    /**
     * Calculo de instalacionUltimaMilla para FTTH segun Brief 601238
     * @param totalSla
     * @return 
     * @author ramirezcamp
     */
    private int instalacionUltimaMilla(int totalSla, String sitiData){
        try {
            String nodeSitidataLastMileFtth = this.consultarParametro(Constants.NODO_SITIDATA_ULTIMA_MILLA_FTTH);
            String timeLastMileFtth = this.consultarParametro(Constants.TIEMPO_ULTIMA_MILLA_FTTH);
            
            if(this.consultarParametro(Constants.INSTALACION_ULTIMA_MILLA_FTTH).equalsIgnoreCase("1")){
                String[] lstNodeSitidata = nodeSitidataLastMileFtth.split("\\|");
                if (lstNodeSitidata != null && lstNodeSitidata.length > 0) {
                    for (String nodeSitidata : lstNodeSitidata) {
                        if(nodeSitidata.equalsIgnoreCase(sitiData))
                            return Integer.parseInt(timeLastMileFtth);
                    }
                }
            }
            return totalSla;
        } catch (ApplicationException ex) {
            java.util.logging.Logger.getLogger(ConsultarCoberturasV1Bean.class.getName()).log(Level.SEVERE, null, ex);
            return totalSla;
        }
    }

    /**
     * Tecnologias permitidas para la consulta de distancia nodo aproximado
     * parametrizable (Constants.TECS_CALCULATE_NODE_DISTANCE)
     * default Constant.TEC_FTTH_CALCULATE_NODE, Constant.TEC_HFC_CALCULATE_NODE
     * Brief 310284
     * 
     * @return 
     * @author ramirezcamp
     */
    private String[] lstTecnosCalculate() {
        try {
            return this.consultarParametro(Constants.TECS_CALCULATE_NODE_DISTANCE).split("\\|");
        } catch (ApplicationException ex) {
            java.util.logging.Logger.getLogger(ConsultarCoberturasV1Bean.class.getName()).log(Level.SEVERE, null, ex);
            return new String[]{Constant.TEC_FTTH_CALCULATE_NODE, Constant.TEC_HFC_CALCULATE_NODE};
        }
    }

    /**
     * Metodo para realizar consumo de operacion CalculateNodeDistance
     * @author ramirezcamp
     * 
     * @param latitudMod
     * @param longitudMod
     * @param nombreTecnologia
     * @param geoCodigoDane
     * @throws ApplicationException 
     */
    private CalculateNodeDistanceRestResponse callCalculateNodeDistance(
            String longitudMod, String latitudMod, String nombreTecnologia, String geoCodigoDane)
            throws ApplicationException, CalculateNodeDistanceRestClientException {
        
        try {
            Map<String,Object> header = new HashMap<>();
            String endPoint = (String) paramsNodeDist.getOrDefault(Constants.NODE_DISTANCE_ENDPOINT, "");
            header.put(Constants.HEADER_NODE_DISTANCE_USER, 
                    paramsNodeDist.getOrDefault(Constants.HEADER_NODE_DISTANCE_USER, ""));
            header.put(Constants.HEADER_NODE_DISTANCE_SYSTEM, 
                    paramsNodeDist.getOrDefault(Constants.HEADER_NODE_DISTANCE_SYSTEM, ""));
            header.put(Constants.HEADER_NODE_DISTANCE_IDAPPLICATION, 
                    paramsNodeDist.getOrDefault(Constants.HEADER_NODE_DISTANCE_IDAPPLICATION, ""));
            header.put(Constants.HEADER_NODE_DISTANCE_PASSWORD, 
                    paramsNodeDist.getOrDefault(Constants.HEADER_NODE_DISTANCE_PASSWORD, ""));
            
            CalculateNodeDistanceRestRequest nodeDistRestRequest
                    = new CalculateNodeDistanceRestRequest(longitudMod, latitudMod, nombreTecnologia, geoCodigoDane);
            CalculateNodeDistanceRestClientPool iCalculateNodeClientPool = 
                    CalculateNodeDistanceRestClientPool.getInstance((String) paramsNodeDist.getOrDefault(Constants.NODE_DISTANCE_ENDPOINT, ""));
            
            String headerStr = header.keySet().stream().map(key -> key + "=" + header.get(key)).collect(Collectors.joining(", ", "{", "}"));
            
            LOGGER.info("Send object [" + endPoint + "] Header: [" + headerStr + "] Request: " + new ObjectMapper().writeValueAsString(nodeDistRestRequest));
            CalculateNodeDistanceRestResponse response = iCalculateNodeClientPool.calculateNodeDistance(
                    nodeDistRestRequest, header);
            LOGGER.info("Response: " + new ObjectMapper().writeValueAsString(response));
            
            return response;
        } catch (Exception ex) {
            LOGGER.error("Error en callCalculateNodeDistance", ex);
        }
        return null;
    }
     /**
      * Metodo para obtener los parametros requeridos en el consumo de la 
      * operacion CalculateNodeDistance
      * 
      * @author ramirezcamp
      * @return Map<String, Object>
      */
    private Map<String, Object> getParamsCalculateNodeDistance() {
        try {
            Map<String,Object> paramNodeDist = new HashMap<>();
            paramNodeDist.put(Constants.NODE_DISTANCE_ENDPOINT, 
                    consultarParametro(Constants.PROP_URL_WS_NODE_DISTANCE));
            paramNodeDist.put(Constants.HEADER_NODE_DISTANCE_USER, 
                    consultarParametro(Constants.PROP_HEADER_WS_NODE_DISTANCE_USER));
            paramNodeDist.put(Constants.HEADER_NODE_DISTANCE_SYSTEM, 
                    consultarParametro(Constants.PROP_HEADER_WS_NODE_DISTANCE_SYSTEM));
            paramNodeDist.put(Constants.HEADER_NODE_DISTANCE_IDAPPLICATION, 
                    consultarParametro(Constants.PROP_HEADER_WS_NODE_DISTANCE_ID_APPLICATION));
            paramNodeDist.put(Constants.HEADER_NODE_DISTANCE_PASSWORD, 
                    consultarParametro(Constants.PROP_HEADER_WS_NODE_DISTANCE_PASSWORD));
            return paramNodeDist;
        } catch (Exception ex) {
            LOGGER.info("Se genero error en la obtencion de parametros WS CalculateNodeDistance ConsultarCoberturasV1Bean class ...");
            return null;
        }
    }
    
        /**
     * Metodo para realizar consumo de operacion UsagGeoCoverage
     * @author 45111073
     * 
     * @throws ApplicationException 
     */
    private UsagGeoCoverageRestResponse callRsResUsagGeoCoverage(
            String daneCode, String nodeCode, String technologyType, String owner)
            throws ApplicationException {
            UsagGeoCoverageRestResponse responseUsagGeoCoverage;
        try {
            Map<String,Object> header = new HashMap<>();
            String endPoint = (String) this.consultarParametro(Constants.URL_RS_RES_USAG_GEO_COVERAGE);
            header.put(Constants.HEADER_NODE_DISTANCE_USER, 
                    this.consultarParametro(Constants.GEO_COVERAGE_OWNER_USER));
            header.put(Constants.HEADER_NODE_DISTANCE_SYSTEM, 
                    this.consultarParametro(Constants.GEO_COVERAGE_OWNER_SYSTEM));
            header.put(Constants.HEADER_NODE_DISTANCE_IDAPPLICATION, 
                    this.consultarParametro(Constants.GEO_COVERAGE_OWNER_IP_APPLICATION));
            header.put(Constants.HEADER_NODE_DISTANCE_PASSWORD, 
                    this.consultarParametro(Constants.GEO_COVERAGE_OWNER_PASSWORD));
            
            UsagGeoCoverageRestRequest usagGeoCoverageRestRequest 
                    = new UsagGeoCoverageRestRequest(daneCode, nodeCode, technologyType, owner);
            
            UsagGeoCoverageRestClientPool usagGeoCoverageRestClientPool =
                    UsagGeoCoverageRestClientPool.getInstance(endPoint);
            
            
            String headerStr = header.keySet().stream().map(key -> key + "=" + header.get(key)).collect(Collectors.joining(", ", "{", "}"));
            
            LOGGER.info("Send object [" + endPoint + "] Header: [" + headerStr + "] Request: " + new ObjectMapper().writeValueAsString(usagGeoCoverageRestRequest));
            responseUsagGeoCoverage = usagGeoCoverageRestClientPool.coordsByNode(usagGeoCoverageRestRequest, header);
            LOGGER.info("Response: " + new ObjectMapper().writeValueAsString(responseUsagGeoCoverage));
            
            return responseUsagGeoCoverage;
        } catch (Exception ex) {
            LOGGER.error("Error en callCalculateNodeDistance", ex);
        }
        return null;
    }
}
