/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.dtos.CmtFiltroPrefichasDto;
import co.com.claro.mgl.dtos.CreacionCcmmDto;
import co.com.claro.mgl.dtos.FichasGeoDrDireccionDto;
import co.com.claro.mgl.dtos.ObservacionesFichasDTO;
import co.com.claro.mgl.dtos.ResponseValidarDireccionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.error.ApplicationExceptionCM;
import co.com.claro.mgl.facade.CmtNotasHhppDetalleVtMglFacadeLocal;
import co.com.claro.mgl.facade.CmtNotasHhppVtMglFacadeLocal;
import co.com.claro.mgl.facade.DireccionMglFacadeLocal;
import co.com.claro.mgl.facade.DireccionRRFacadeLocal;
import co.com.claro.mgl.facade.DrDireccionFacadeLocal;
import co.com.claro.mgl.facade.ExportFichasMglFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.MarcasHhppMglFacadeLocal;
import co.com.claro.mgl.facade.MarcasMglFacadeLocal;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.NotasAdicionalesMglFacadeLocal;
import co.com.claro.mgl.facade.PreFichaMglFacadeLocal;
import co.com.claro.mgl.facade.SolicitudFacadeLocal;
import co.com.claro.mgl.facade.SubDireccionMglFacadeLocal;
import co.com.claro.mgl.facade.TipoHhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBlackListMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtComunidadRrFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCuentaMatrizMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionDetalleMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtNotasMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificioMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtValidadorDireccionesFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.CmtNotasHhppDetalleVtMgl;
import co.com.claro.mgl.jpa.entities.CmtNotasHhppVtMgl;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.ExportFichasMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.NotasAdicionalesMgl;
import co.com.claro.mgl.jpa.entities.OpcionIdNombre;
import co.com.claro.mgl.jpa.entities.PreFichaGeorreferenciaMgl;
import co.com.claro.mgl.jpa.entities.PreFichaHHPPDetalleMgl;
import co.com.claro.mgl.jpa.entities.PreFichaMgl;
import co.com.claro.mgl.jpa.entities.PreFichaTxtMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.TipoHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBlackListMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.cm.ValidadorDireccionBean;
import co.com.claro.mgl.mbeans.util.AuxExportFichas;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.DataArchivoExportFichas;
import co.com.claro.mgl.mbeans.util.ExportArchivosFichas;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.mbeans.util.VerificadorExpresiones;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.services.comun.Constantes;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.ResponseMessage;
import co.com.telmex.catastro.utilws.SecurityLogin;
import com.google.gson.Gson;
import com.jlcg.db.exept.ExceptionDB;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author User
 */
@ManagedBean(name = "generarFichaBean")
@ViewScoped
public class FichaGenerarBean {

    private static final Logger LOGGER = LogManager.getLogger(FichaGenerarBean.class);
    private static final String NO_GEORREFERENCIADO = "NG";
    private HtmlDataTable dataTable = new HtmlDataTable();
    private BigDecimal idPrefichaSelected;
    private List<PreFichaMgl> fichaToGenerarList = new ArrayList<>();
    private List<PreFichaMgl> preFichaFiltradaList = new ArrayList();
    private List<PreFichaXlsMgl> edificiosVtXls;
    private List<PreFichaXlsMgl> edificiosVtXlsNoProcesados;
    private List<PreFichaXlsMgl> casasRedExternaXls;
    private List<PreFichaXlsMgl> casasRedExternaXlsNoProcesados;
    private List<PreFichaXlsMgl> conjCasasVtXls;
    private List<PreFichaXlsMgl> conjCasasVtXlsNoProcesados;
    private List<PreFichaXlsMgl> hhppSNXls;
    private List<PreFichaXlsMgl> IngresoNuevoNXls;
    private List<PreFichaXlsMgl> fichaXlsMglList;
    private List<PreFichaXlsMgl> fichaXlsMglListNoProcesados;
    private List<PreFichaXlsMgl> fichaProcessXlsList;
    private List<PreFichaHHPPDetalleMgl> hhppDetalleList;
    private PreFichaMgl prefichaCrear;
    private String isInfoProcesada = String.valueOf(false);
    private String isInfoForCreate = String.valueOf(false);
    private String labelHeaderTableLists = "Información de Georreferenciación";
    private String usuarioVT = null;
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    private String isInfocreateInRR = String.valueOf(false);
    private CmtBasicaMgl estadoHhppExiste = new CmtBasicaMgl();
    private String valorInicial = null;
    private String blackListCM1;
    private String blackListCM2;
    private String blackListHhpp1;
    private String blackListHhpp2;
    private List<MarcasMgl> listMarcas;
    private List<CmtBasicaMgl> listMarcasCM;
    private String nota;
    private int perfilVT = 0;
    private ArrayList<String> listaObservaciones;
    private Map<String, String> mapObservaciones = new HashMap<>();
    private PreFichaMgl prefichaSeleccionada;
    private boolean creoCCMMHPP;
    private List<PreFichaXlsMgl> edificios = new ArrayList<>();
    private List<PreFichaXlsMgl> casas = new ArrayList<>();
    private List<PreFichaXlsMgl> conjuntoCasas = new ArrayList<>();
    private List<ObservacionesFichasDTO> observacionesFichas = new ArrayList<>();
    private List<String> listadoObservaciones = new ArrayList<>();
    private String calle;
    private String placa;
    private List<CmtBasicaMgl> listadoEstratos;
    private String cambioEstratoMasivo;
    private CmtFiltroPrefichasDto filtrosPrefichaEdificios;
    private CmtFiltroPrefichasDto filtrosPrefichaCasas;
    private CmtFiltroPrefichasDto filtrosPrefichaConjuntoCasas;
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private int actual;
    private Date fechaInicial;
    private Date fechaFinal;
    private boolean seleccionarTodo = false;
    private boolean todasLasPreFichasMarcadas = false;
    private boolean hayFichasSeleccionadas = false;
    private boolean eliminarTodoSelected = false;
    private boolean registrosFiltrados;
    private ValidadorDireccionBean validadorDireccionBean;
    private DrDireccion drDireccion;
    private String direccionFicha;
    private ResponseValidarDireccionDto responseValidarDireccionDto;
    private String direccion;
    private List<String> barrioslist;
    private PreFichaXlsMgl prefichaSelected;

    /**
     * El flag filtro aplicado se desactiva si se cambia la fecha inicial o la
     * fecha final El flag filtro aplicado se activa al dar realizar
     * correctamente el filtrado de la lista por el rango de fechas incluidad
     * las fecha nullas si el filtro ha sido aplicado se pueden habilitar los
     * botones de navegacion filtro aplicado se diferencia de registros
     * filtrados en que incluye el rango con fechas nulas
     */
    private boolean filtroAplicado = true;
    private boolean rangoFechasValido = true;
    
    private List<TipoHhppMgl> listaTipoHhpp;
    private ArrayList<PreFichaTxtMgl> edificiosTxt;
    private ArrayList<PreFichaTxtMgl> casasTxt;
    private ArrayList<PreFichaTxtMgl> conjuntoCasasTxt;
    private boolean botonError = true;
    private boolean dirOk = false;
    private boolean botonOk = false;
    private int contNoGEo;

    @EJB
    private PreFichaMglFacadeLocal preFichaMglFacadeLocal;
    @EJB
    private AddressEJBRemote addressEJBRemote;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cmtCuentaMatrizMglFacadeLocal;
    @EJB
    private MarcasMglFacadeLocal marcasMglFacadeLocal;
    @EJB
    private CmtValidadorDireccionesFacadeLocal cmtValidadorDireccionesFacadeLocal;
    @EJB
    private DireccionRRFacadeLocal direccionRRFacadeLocal;
    @EJB
    private SolicitudFacadeLocal solicitudFacadeLocal;
    @EJB
    private CmtSubEdificioMglFacadeLocal cmtSubEdificioMglFacadeLocal;
    @EJB
    private CmtComunidadRrFacadeLocal cmtComunidadRrFacadeLocal;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private CmtTipoBasicaMglFacadeLocal tipoBasicaFacadeLocal;
    @EJB
    private HhppMglFacadeLocal hhppMglFacadeLocal;
    @EJB
    private MarcasHhppMglFacadeLocal marcasHhppMglFacadeLocal;
    @EJB
    private CmtNotasMglFacadeLocal cmtNotasMglFacadeLocal;
    @EJB
    private CmtBlackListMglFacadeLocal cmtBlackListMglFacadeLocal;
    @EJB
    private NotasAdicionalesMglFacadeLocal notasAdicionalesMglFacadeLocal;
    @EJB
    private CmtNotasHhppVtMglFacadeLocal cmtNotasHhppVtMglFacadeLocal;
    @EJB
    private CmtNotasHhppDetalleVtMglFacadeLocal cmtNotasHhppDetalleVtMglFacadeLocal;
    @EJB
    private CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMglFacadeLocal;
    @EJB
    private SubDireccionMglFacadeLocal subDireccionMglFacadeLocal;
    @EJB
    private DireccionMglFacadeLocal direccionMglFacadeLocal;
    @EJB
    private CmtDireccionMglFacadeLocal cmtDireccionMglFacadeLocal;
    @EJB
    private ExportFichasMglFacadeLocal exportFichasMglFacadeLocal;
    @EJB
    private TipoHhppMglFacadeLocal tipoHhppMglFacadeLocal;
    @EJB
    private DrDireccionFacadeLocal drDireccionFacadeLocal;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cuentaMatrizMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;

    private void crearMultiplacaCCMM(PreFichaXlsMgl p, CmtCuentaMatrizMgl cuenta) {
        try {
            String placas[] = p.getPlaca().split("/");
            String primerFracmento = p.getPlaca().substring(0, p.getPlaca().indexOf("-") + 1);
            List<DrDireccion> listaDrDireccions = new ArrayList<>();
            ValidadorDireccionBean validadorDireccionBean1 = (ValidadorDireccionBean) JSFUtil.getSessionBean(ValidadorDireccionBean.class);
            CmtSubEdificioMgl selectedCmtSubEdificioMgl = cuenta.getSubEdificioGeneral();
            for (int i = 1; i < placas.length; i++) {
                placas[i] = primerFracmento + placas[i];
                try {
                    DrDireccion drDireccionTemp = new DrDireccion();
                    usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
                    validadorDireccionBean1.setCiudadDelBean(cuenta.getMunicipio());
                    validadorDireccionBean1.setIdCentroPoblado(cuenta.getCentroPoblado().getGpoId());
                    validadorDireccionBean1.setUsuarioLogin(usuarioLogin);
                    validadorDireccionBean1.setTecnologia(null);
                    String selectedBarrio = "";
                    if (selectedBarrio == null || !selectedBarrio.isEmpty()) {
                        if (cuenta.getCuentaMatrizId() != null && cuenta.getDireccionPrincipal() != null
                                && cuenta.getDireccionPrincipal().getBarrio() != null
                                && !cuenta.getDireccionPrincipal().getBarrio().isEmpty()) {
                            selectedBarrio = cuenta.getDireccionPrincipal().getBarrio();
                        }
                    }
                    validadorDireccionBean1.validarDireccion(drDireccionTemp, p.getViaPrincipal() + " " + placas[i],
                            cuenta.getCentroPoblado(), selectedBarrio, this, FichaGenerarBean.class,
                            Constant.TIPO_VALIDACION_DIR_CM, Constant.DIFERENTE_MODIFICACION_CM);
                    listaDrDireccions.add(drDireccionTemp);

                    String direccion1 = drDireccionTemp.getDireccionHHPP();
                    CmtDireccionMgl cmtdireccionMglTemp = drDireccionTemp.convertToCmtDireccionMgl();
                    cmtdireccionMglTemp.setComentario("Creada desde Fichas Dirs con multiplaca");
                    cmtdireccionMglTemp.setCuentaMatrizObj(cuenta);
                    cmtdireccionMglTemp.setTdiId(1);
                    cmtdireccionMglTemp.setDireccionId(cuenta.getDireccionPrincipal().getDireccionId());
                    cmtDireccionMglFacadeLocal.actualizar(selectedCmtSubEdificioMgl, cmtdireccionMglTemp, drDireccionTemp, cuenta.getDireccionPrincipal().getBarrio(),
                            validadorDireccionBean1.getResponseValidarDireccionDto(), direccion1, usuarioVT, false, usuarioVT, perfilVT, false);

                } catch (ApplicationException | ApplicationExceptionCM ex) {
                    p.setObservacionFicha(p.getObservacionFicha() + " - " + ex.getMessage());
                }
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Error al momento de procesar la ficha general | metodo : crearMultiplacaCCMM : ".concat(ex.getMessage()), ex);
            p.setObservacionFicha(p.getObservacionFicha() + " - " + ex.getMessage());
        }

    }

    public static enum CLASIFICACION_NAME {

        EDIFICIOSVT, CASASREDEXTERNA, CONJUNTOCASASVT, HHPPSN, INGRESONUEVO
    }

    public static enum CLASIFICACION_COMPLEMENTO_IT_BM {

        VD, SECTOR, M, C, MZ, CS, VIA, VI, SMZ,
        AG,
        BARRIO,
        CIUDADELA,
        CONJUNTO_RESIDENCIAL,
        MULTIFAMILIAR,
        UR,
        ETAPA,
        SUPERMANZANA,
        MANZANA,
        LOTE,
        AGRUPACION,
        UNIDAD_RESIDENCIAL,
        URBANIZACION,
        COLEGIO,
        VEREDA,
        ESCUELA,
        BATALLON,
        CALLEJON,
        FINCA,
        HACIENDA,
        PARCELA,
        KM
    }

    public static enum NIVELES_DIRECCION_BARRIO_MANZANA {
        AGRUPACION("AGRUPACION"), AG("AGRUPACION"),
        BARRIO("BARRIO"), BR("BARRIO"),
        CASA("CASA"), CS("CASA"), C("CASA"),
        CIUDADELA("CIUDADELA"), CD("CIUDADELA"),
        CONJUNTO_RESIDENCIAL("CONJUNTO RESIDENCIAL"), CO("CONJUNTO RESIDENCIAL"),
        ETAPA("ETAPA"), ET("ETAPA"),
        LOTE("LOTE"), LT("LOTE"),
        MANZANA("MANZANA"), MZ("MANZANA"),
        MULTIFAMILIAR("MULTIFAMILIAR"),
        SECTOR("SECTOR"), SC("SECTOR"), ST("SECTOR"),
        SUPERMANZANA("SUPERMANZANA"), SM("SUPERMANZANA"), SMZ("SUPERMANZANA"),
        UNIDAD_RESIDENCIAL("UNIDAD RESIDENCIAL"), UL("UNIDAD RESIDENCIAL"),
        URBANIZACION("URBANIZACION"), UR("URBANIZACION");

        private final String valor;

        NIVELES_DIRECCION_BARRIO_MANZANA(String valor) {
            this.valor = valor;
        }

        public String getValue() {
            return valor;
        }
    }

    public static enum NIVELES_DIRECCION_INTRADUCIBLE {
        BATALLON("BATALLON"),
        CALLEJON("CALLEJON"),
        COLEGIO("COLEGIO"),
        CONTADOR("CONTADOR"),
        ESCUELA("ESCUELA"),
        FINCA("FINCA"), FI("FINCA"),
        HACIENDA("HACIENDA"),
        KM("KM"),
        PARCELA("PARCELA"),
        SECTOR("SECTOR"), SC("SECTOR"), ST("SECTOR"),
        VEREDA("VEREDA"), VD("VEREDA"),
        VIA("VIA"), VI("VIA");

        private final String valor;

        NIVELES_DIRECCION_INTRADUCIBLE(String valor) {
            this.valor = valor;
        }

        public String getValue() {
            return valor;
        }
    }

    public static enum DIST_TYPE {

        LC, AP, IN
    }
    private SecurityLogin securityLogin;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private HttpServletRequest httpRequest = FacesUtil.getServletRequest();
    private HttpSession httpSession = httpRequest.getSession();

    public FichaGenerarBean() {
        fichaXlsMglListNoProcesados = new ArrayList<>();
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();
            validadorDireccionBean = JSFUtil.getSessionBean(ValidadorDireccionBean.class);

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en FichaGenerarBean. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en FichaGenerarBean. ", e, LOGGER);
        }
    }

    @PostConstruct
    public void loadList() {
        try {
            //Entra al paginado
            listInfoByPage(1);
            estadoHhppExiste = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(co.com.claro.mgl.utils.Constant.BASICA_EST_HHPP_TIPO_POTENCIAL);
            listMarcas = marcasMglFacadeLocal.findAll();
            cargarBlacklist();
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
            listadoEstratos = new ArrayList<>();
            CmtTipoBasicaMgl tipoBasicaEstrato = new CmtTipoBasicaMgl();
            tipoBasicaEstrato.setTipoBasicaId(new BigDecimal(55));
            listadoEstratos = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaEstrato);
            creoCCMMHPP = false;
        } catch (ApplicationException e) {
            LOGGER.error("Error en loadList. " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en loadList. " + e.getMessage(), e);
        }
    }

    /**
     * Función que carga la lsita de basica apra la blacklist
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void cargarBlacklist() {
        try {
            CmtTipoBasicaMgl basicaBlacklist = tipoBasicaFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_BLACK_LIST_CM);
            listMarcasCM = cmtBasicaMglFacadeLocal.findByTipoBasica(basicaBlacklist);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarBlacklist. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarBlacklist. ", e, LOGGER);
        }

    }

    public boolean validaPlacaCCMM(PreFichaXlsMgl ficha) {
        return ficha.getPlaca().contains("/");
    }

    public void cargarDetalleFicha(PreFichaMgl prefichaSelect) {
        try {
            prefichaSeleccionada = new PreFichaMgl();
            this.prefichaSeleccionada = prefichaSelect;
            isInfoProcesada = String.valueOf(false);
            prefichaCrear = prefichaSelect;
            BigDecimal idGeoPol = prefichaCrear.getNodoMgl().getGpoId();
            GeograficoPoliticoMgl centroPoblado = geograficoPoliticoMglFacadeLocal.findById(idGeoPol);
            fichaXlsMglList = new ArrayList<>();
            edificiosVtXls = new ArrayList<>();
            casasRedExternaXls = new ArrayList<>();
            conjCasasVtXls = new ArrayList<>();
            hhppSNXls = new ArrayList<>();
            IngresoNuevoNXls = new ArrayList<>();
            listadoObservaciones = new ArrayList<>();
            mapObservaciones = new HashMap<>();
            blackListCM2 = "";
            blackListCM1 = "";
            blackListHhpp1 = "";
            blackListHhpp2 = "";
            hhppDetalleList = new ArrayList<>();
            filtrosPrefichaEdificios = new CmtFiltroPrefichasDto();
            filtrosPrefichaCasas = new CmtFiltroPrefichasDto();
            filtrosPrefichaConjuntoCasas = new CmtFiltroPrefichasDto();
            cargarListadosPrefichas();
            cargarListadosPrefichasNoProcesados();
            creoCCMMHPP = false;
            String msn = "Total registros: " + (fichaXlsMglList.size() - hhppSNXls.size()) + "\n"
                    + "Procesados: " + (edificiosVtXls.size() + casasRedExternaXls.size() + conjCasasVtXls.size()) + "\n"
                    + "No procesados: " + (edificiosVtXlsNoProcesados.size()
                    + casasRedExternaXlsNoProcesados.size()
                    + conjCasasVtXlsNoProcesados.size());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarDetalleFicha. Al cargar la informacion detalle de la preficha seleccionada ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarDetalleFicha. Al cargar la informacion detalle de la preficha seleccionada ", e, LOGGER);
        } finally {
            System.gc();
        }
    }

    public void cargarListadosPrefichas() {
        try {
            fichaXlsMglList = preFichaMglFacadeLocal.getListXLSByPrefichaFase(prefichaCrear.getPrfId(), prefichaCrear.getFase());
            edificiosVtXls = new ArrayList<>();
            casasRedExternaXls = new ArrayList<>();
            conjCasasVtXls = new ArrayList<>();

            edificiosTxt = new ArrayList<>();
            casasTxt = new ArrayList<>();
            conjuntoCasasTxt = new ArrayList<>();

            List<PreFichaTxtMgl> lis = preFichaMglFacadeLocal.getListadoPrefichasTxtPorTab(prefichaCrear.getPrfId());
            for (PreFichaTxtMgl li : lis) {
                if (li.getPlacaUnida() != null
                        && !li.getPlacaUnida().trim().isEmpty()
                        && (li.getPlacaUnida().contains("SN")
                        || li.getPlacaUnida().contains("S-N")
                        || li.getPlacaUnida().contains("CD")
                        || li.getPlacaUnida().contains("S/N")
                        || li.getPlacaUnida().contains("NN"))) {
                    continue;
                } else if (li.getBlockName() != null
                        && li.getBlockName().toUpperCase().equalsIgnoreCase("N_Edificio".toUpperCase())) {
                    edificiosTxt.add(li);
                } else if (li.getBlockName().toUpperCase().equals("N_Casa".toUpperCase())) {
                    if (li.getPisos() != null
                            && li.getPisos().intValue() > 3) {
                        edificiosTxt.add(li);
                    } else if ((li.getNombreConj() != null && !li.getNombreConj().trim().isEmpty()
                            && !li.getNombreConj().trim().equalsIgnoreCase("<>")
                            && isNameConjunto(li.getNombreConj().trim()))
                            || (li.getNumCasas() != null && li.getNumCasas().intValue() > 1)) {
                        conjuntoCasasTxt.add(li);
                    } else {
                        casasTxt.add(li);
                    }
                }

            }

            edificiosVtXls = preFichaMglFacadeLocal.getListadoPrefichasPorTab(prefichaCrear.getPrfId(), prefichaCrear.getFase(), Constant.EDIFICIOSVT, filtrosPrefichaEdificios);
            casasRedExternaXls = preFichaMglFacadeLocal.getListadoPrefichasPorTab(prefichaCrear.getPrfId(), prefichaCrear.getFase(), Constant.CASAS_RED_EXTERNA, filtrosPrefichaCasas);
            conjCasasVtXls = preFichaMglFacadeLocal.getListadoPrefichasPorTab(prefichaCrear.getPrfId(), prefichaCrear.getFase(), Constant.CONJUNTOCASASVT, filtrosPrefichaConjuntoCasas);
            hhppSNXls = preFichaMglFacadeLocal.getListadoByFaseAndIdPfAndPestana(prefichaCrear.getPrfId(), prefichaCrear.getFase(), Constant.HHPPSN);

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarListadosPrefichas. Al cargar listados de prefichas ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarListadosPrefichas. Al cargar listados de prefichas ", e, LOGGER);
        }

    }

    private boolean isNameConjunto(String nameToValidate) {

        String regExpression = "^(CO\\s)([^\\s])+";
        Pattern patter;
        patter = Pattern.compile(regExpression);
        return patter.matcher(nameToValidate.trim()).matches();
    }

    public void cargarListadosPrefichasNoProcesados() {
        try {
            edificiosVtXlsNoProcesados = new ArrayList<>();
            casasRedExternaXlsNoProcesados = new ArrayList<>();
            conjCasasVtXlsNoProcesados = new ArrayList<>();
            fichaXlsMglListNoProcesados = new ArrayList<>();

            CmtFiltroPrefichasDto filtros = new CmtFiltroPrefichasDto();
            edificiosVtXlsNoProcesados = preFichaMglFacadeLocal.getListadoPrefichasPorTab(prefichaCrear.getPrfId(), prefichaCrear.getFase(), Constant.EDIFICIOSVT_NO_PROCESADOS, filtros);
            casasRedExternaXlsNoProcesados = preFichaMglFacadeLocal.getListadoPrefichasPorTab(prefichaCrear.getPrfId(), prefichaCrear.getFase(), Constant.CASAS_RED_EXTERNA_NO_PROCESADOS, filtros);
            conjCasasVtXlsNoProcesados = preFichaMglFacadeLocal.getListadoPrefichasPorTab(prefichaCrear.getPrfId(), prefichaCrear.getFase(), Constant.CONJUNTOCASASVT_NO_PROCESADOS, filtros);

            fichaXlsMglListNoProcesados.addAll(edificiosVtXlsNoProcesados);
            fichaXlsMglListNoProcesados.addAll(casasRedExternaXlsNoProcesados);
            fichaXlsMglListNoProcesados.addAll(conjCasasVtXlsNoProcesados);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarListadosPrefichasNoProcesados. Al cargar listados de prefichas no procesadas ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarListadosPrefichasNoProcesados. Al cargar listados de prefichas no procesadas ", e, LOGGER);
        }
    }

    public boolean validarBlackList() {
        if (!blackListCM1.equals("0") && !blackListCM2.equals("0") && blackListCM1.equals(blackListCM2)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La marca 1 y marca 2 para CM son iguales , seleccione marcas diferentes", ""));
            return false;
        }
        if (!blackListHhpp1.equals("0") && !blackListHhpp2.equals("0") && blackListHhpp1.equals(blackListHhpp2)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La marca 1 y marca 2 para HHPP son iguales , seleccione marcas diferentes", ""));
            return false;
        }
        return true;
    }

    public void procesarPreficha() {
        dirOk = false;
        contNoGEo = 0;
        edificios = new ArrayList();
        casas = new ArrayList();             
        conjuntoCasas = new ArrayList();

        try {

            if (validarBlackList()) {
                isInfoProcesada = String.valueOf(false);
                fichaProcessXlsList = new ArrayList<>();

                //Se obtienen los datos del Nodo para poder georeferenciar la direccion
                BigDecimal idGeoPol = prefichaCrear.getNodoMgl().getGpoId();
                GeograficoPoliticoMgl centro = geograficoPoliticoMglFacadeLocal.findById(idGeoPol);
                GeograficoPoliticoMgl city = geograficoPoliticoMglFacadeLocal.findById(centro.getGeoGpoId());
                GeograficoPoliticoMgl state = geograficoPoliticoMglFacadeLocal.findById(city.getGeoGpoId());

                for (PreFichaXlsMgl p : fichaXlsMglList) {
                    if (p.getPlaca().contains("SECTOR")) {
                        String s = p.getPlaca().substring(0, p.getPlaca().indexOf("-"));
                        p.setPlaca(p.getPlaca().replace(s, "0"));
                        p.setViaPrincipal(p.getViaPrincipal() + " " + s);
                    }

                    if (p.getClasificacion().equalsIgnoreCase("HHPPSN")) {
                        continue;
                    }
                    calle = "";
                    placa = "";
                    //Se obtiene calle y placa de acuerdo a la geoferenciacion de la preficha
                    obtenerCallePlacaPreficha(p, centro, state, city);
                    // Se calcula o se aplica la distribucion cargada en la preficha
                    calcularDistribucion(p);
                }
                hhppDetalleList = new ArrayList<>();
                //despues de descomponer cada registro en los respectivos HHPP procedemos a georreferenciarlos
                for (PreFichaXlsMgl fp : fichaProcessXlsList) {
                    if (fp.getClasificacion().equalsIgnoreCase("HHPPSN")) {
                        continue;
                    }
                    String viaPrincipal = fp.getViaPrincipal();

                    if (fp.getFichaGeorreferenciaMgl().getSource() != null && fp.getFichaGeorreferenciaMgl().getSource().equals("ANTIGUA")
                            && fp.getFichaGeorreferenciaMgl().getAlternateAddress() != null
                            && !fp.getFichaGeorreferenciaMgl().getAlternateAddress().isEmpty()
                            && fp.getIdTipoDireccion().equals(Constant.ACRONIMO_CK)) {
                        viaPrincipal = fp.getFichaGeorreferenciaMgl().getAlternateAddress();

                    }
                    PreFichaGeorreferenciaMgl fichaGeorreferenciaMgl;
                    fichaGeorreferenciaMgl = obtenerDireccionTabuladaPreficha(fp, viaPrincipal, fp.getPlaca(), city, centro, true);
                    if (fichaGeorreferenciaMgl != null) {
                        if (fichaGeorreferenciaMgl.getDrDireccionPreficha() == null) {
                            contNoGEo++;
                        } else {
                            List<PreFichaGeorreferenciaMgl> georreferenciaList = new ArrayList<>();
                            List<String> dirRRList = new ArrayList<>();
                            List<PreFichaHHPPDetalleMgl> hhppDetalleListPreficha = new ArrayList<>();

                            fichaGeorreferenciaMgl.setPreFichaXlsMgl(fp);
                            fichaGeorreferenciaMgl.setCambioEstrato(fp.getFichaGeorreferenciaMgl().getCambioEstrato());
                            georreferenciaList.add(fichaGeorreferenciaMgl);
                            fichaGeorreferenciaMgl.setChangeNumber("1");
                            fichaGeorreferenciaMgl.setFechaCreacion(fp.getFechaCreacion());
                            fichaGeorreferenciaMgl.setFechaEdicion(fp.getFechaEdicion());
                            fichaGeorreferenciaMgl.setUsuarioCreacion(httpSession.getAttribute("loginUserSecurity").toString());
                            fichaGeorreferenciaMgl.setUsuarioEdicion(httpSession.getAttribute("loginUserSecurity").toString());
                            for (String direccionHHPP : fp.getDireccionStrList()) {
                                DireccionRREntity direccionRR = agregarComplementosHHPPFichas(fp.getDrDireccionList().get(0), direccionHHPP, fp);
                                String direccionFormatoRR = "[" + direccionRR.getCalle() + "]" + "[" + direccionRR.getNumeroUnidad() + "]" + "[" + direccionRR.getNumeroApartamento() + "]";
                                PreFichaHHPPDetalleMgl detalleHHPPMgl = getHHPPDetalle(fichaGeorreferenciaMgl, fp, direccionFormatoRR, prefichaCrear);
                                detalleHHPPMgl.setDireccionMGL(direccionHHPP);
                                detalleHHPPMgl.setStreetName(direccionRR.getCalle());
                                detalleHHPPMgl.setHouseNumber(direccionRR.getNumeroUnidad());
                                detalleHHPPMgl.setApartmentNumber(direccionRR.getNumeroApartamento());
                                detalleHHPPMgl.setDireccionRR(direccionFormatoRR);
                                dirRRList.add(direccionFormatoRR);
                                hhppDetalleList.add(detalleHHPPMgl);
                                hhppDetalleListPreficha.add(detalleHHPPMgl);
                            }

                            fp.setGeorreferenciaList(georreferenciaList);
                            fp.setHhppList(dirRRList);
                            fp.setHhppDetalleList(hhppDetalleListPreficha);
                        }
                    }

                    if (Constant.EDIFICIOSVT.equals(fp.getPestana())) {
                        edificios.add(fp);
                    }
                    if (Constant.CASAS_RED_EXTERNA.equals(fp.getPestana())) {
                        casas.add(fp);
                    }
                    if (Constant.CONJUNTOCASASVT.equals(fp.getPestana())) {
                        conjuntoCasas.add(fp);
                    }

                    listaTipoHhpp = tipoHhppMglFacadeLocal.findAll();

                }
                if (contNoGEo == 0) {
                    isInfoProcesada = String.valueOf(true);
                }else{
                     String msn = "Por favor revisar la columna 'ERROR ESTADARIZACIÓN' de cada una de las pestañas "
                             + "y las direcciones que se encuentran en ROJO necesitan ser estandarizadas "
                             + "por medio del panel de Dirección Tabulada";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));

                }
                dirOk = true;
            }
        } catch (ApplicationException | NullPointerException | CloneNotSupportedException e) {
            FacesUtil.mostrarMensajeError("Error en procesarPreficha. Al momento de crear la ficha " + (e instanceof ApplicationException ? e.getMessage() : ""), e, LOGGER);
        }
    }

    public void obtenerCallePlacaPreficha(PreFichaXlsMgl p, GeograficoPoliticoMgl centro, GeograficoPoliticoMgl state, GeograficoPoliticoMgl city) throws ApplicationException {
        String placaAValidar = p.getPlaca();
        try {
            PreFichaGeorreferenciaMgl fichaGeorreferenciaHPHPMgl;

            if ((p.getPisos().intValue() > 3 && p.getClasificacion().equals(Constant.CASAS_RED_EXTERNA)) || p.getClasificacion().equals(Constant.EDIFICIOSVT)) {
                if (placaAValidar.contains("/")) {
                    placaAValidar = placaAValidar.substring(0, placaAValidar.indexOf("/"));
                    List<String> listaDirCCMM = new ArrayList<>();
                    String placasCCMM[] = p.getPlaca().split("/");
                    for (int i = 1; i < placasCCMM.length; i++) {
                        listaDirCCMM.add(p.getViaPrincipal() + " " + (p.getPlaca().contains("-") ? p.getPlaca().substring(0, p.getPlaca().indexOf("-")) : p.getPlaca().indexOf(" ") != -1 ? p.getPlaca().substring(0, p.getPlaca().indexOf(" ")) : "") + "-" + placasCCMM[i]);
                    }
                    p.setDireccionCCMMStrList(listaDirCCMM);
                }
            }
            if (p.getFichaGeorreferenciaMgl() != null && p.getFichaGeorreferenciaMgl().getAddressCode() != null && !p.getFichaGeorreferenciaMgl().getAddressCode().isEmpty() && Constant.ACRONIMO_CK.equals(p.getIdTipoDireccion().trim())) {
                String viaG;
                if (p.getFichaGeorreferenciaMgl().getSource() != null && Constant.DIRECCIONES_ORIGEN_ANTIGUA.equals(p.getFichaGeorreferenciaMgl().getSource())
                        && p.getFichaGeorreferenciaMgl().getAlternateAddress() != null && !p.getFichaGeorreferenciaMgl().getAlternateAddress().isEmpty()
                        && p.getIdTipoDireccion().equals(Constant.ACRONIMO_CK)) {

                    viaG = p.getFichaGeorreferenciaMgl().getAlternateAddress();

                    fichaGeorreferenciaHPHPMgl = obtenerDireccionTabuladaPreficha(p, viaG, placaAValidar, city, centro, true);
                    String direccionGeo = fichaGeorreferenciaHPHPMgl.getAddress();
                    DrDireccion direccionTabulada = p.getDrDireccionList().get(0);
                    String construccionViaGeneradora = direccionTabulada.getNumViaGeneradora() + " " + direccionTabulada.getLtViaGeneradora() + " " + direccionTabulada.getNlPostViaG() + " "
                            + direccionTabulada.getBisViaGeneradora() + " " + direccionTabulada.getCuadViaGeneradora() + " " + direccionTabulada.getPlacaDireccion();
                    String viaGeneradora = construccionViaGeneradora.replaceAll("\\s{2,}", " ");
                    String direccionSeparada[] = null;
                    if (direccionGeo != null && !direccionGeo.isEmpty()) {
                        direccionSeparada = direccionGeo.split(viaGeneradora);
                    }
                    if (direccionSeparada != null && direccionSeparada.length > 1) {
                        calle = direccionSeparada[0];
                        placa = viaGeneradora;
                    } else {
                        calle = p.getViaPrincipal();
                        placa = placaAValidar;
                    }
                } else {
                    calle = p.getViaPrincipal();
                    placa = placaAValidar;
                }

                LOGGER.info("Se obtuvo Calle y placa de manera correcta");
            } else {
                calle = p.getViaPrincipal();
                if (isMultiplePlaca(placa)) {
                    placa = placaAValidar.substring(0, placaAValidar.indexOf("-") + 1) + placaAValidar.substring(placaAValidar.indexOf("-") + 1).trim().split("/")[0];
                } else {
                    placa = placaAValidar;
                }
            }
        } catch (Exception e) {
            String msg = "No se pudo obtener calle y placa de la preficha con direccion " + p.getViaPrincipal() + " " + placaAValidar;
            LOGGER.error(msg);
            throw new ApplicationException(msg);
        }
    }

    public void calcularDistribucion(PreFichaXlsMgl p) throws ApplicationException {
        try {
            if (!p.getClasificacion().equalsIgnoreCase(Constant.HHPPSN)) {
                List<String> direccionList = new ArrayList<>();
                List<String> listClasificacion = new ArrayList<>();
                listClasificacion.add(p.getClasificacion());

                if (p.getDistribucion() != null && !p.getDistribucion().trim().isEmpty()
                        && Constant.CASAS_RED_EXTERNA.equals(p.getClasificacion())) {

                    if (p.getTotalHHPP().intValue() == 1 && p.getAptos().intValue() == 1 && !p.getDistribucion().contains("IN")) {
                        direccionList.add(calle + " # " + placa + "| CASA");
                    } else {
                        List<String> gruposStr = getGruposFromDist(p.getDistribucion());
                        if (gruposStr != null && !gruposStr.isEmpty()) {
                            for (String str : gruposStr) {
                                String tipo = str.split("\\s")[0];
                                String[] valores = str.split("\\s")[1].split("-");
                                DIST_TYPE type = DIST_TYPE.valueOf(tipo);
                                switch (type) {
                                    case LC:
                                    for (String valore : valores) {
                                        direccionList.add(calle + " # " + placa + "| PISO-" + valore.substring(0, valore.length() - 2) + " LOCAL-" + valore);
                                    }
                                        break;

                                    case AP:
                                    for (String valore : valores) {
                                        direccionList.add(calle + " # " + placa + "| PISO-" + valore.substring(0, valore.length() - 2) + " APARTAMENTO-" + valore);
                                    }
                                        break;

                                    case IN:
                                    for (String valore : valores) {
                                        direccionList.add(calle + " # " + placa + "| PISO-1 INTERIOR-" + valore);
                                    }
                                        break;

                                }
                            }
                        }
                    }
                } else {//si el registro no posee distribucion
                    VerificadorExpresiones verificarDireccion = (VerificadorExpresiones) JSFUtil.getBean("verificadorExpresiones");
                    List<String> distribucionHHPP = new ArrayList<>();
                    //Calculo de Distribucion para Casas de Red externa con 3 o menos pisos
                    if (p.getClasificacion().equals(Constant.CASAS_RED_EXTERNA) && p.getPisos().intValue() <= 3) {
                        distribucionHHPP = verificarDireccion.calcularDistribucionCasasRedExterna(p.getTotalHHPP().intValue(), p.getAptos().intValue(), p.getLocales().intValue(), p.getPisos().intValue());

                    } else //Calculo de Distribucion para Casas de Red externa que se convierten a Edificio VT  con 4 pisos
                    if (p.getClasificacion().equals(Constant.CASAS_RED_EXTERNA) && p.getPisos().intValue() == 4) {
                        distribucionHHPP = verificarDireccion.calcularDistribucionEdificiosVt(p.getAptos().intValue(), p.getLocales().intValue());
                    }
                    for (String hhpp : distribucionHHPP) {
                        StringBuilder hhppDistribucion = new StringBuilder();
                        hhppDistribucion.append(calle).append(" # ").append(placa).append("| ").append(hhpp);
                        direccionList.add(hhppDistribucion.toString());
                    }
                }

                if (!direccionList.isEmpty()) {
                    p.setDireccionStrList(direccionList);

                } else {
                    p.setDireccionStrList(new ArrayList<>());
                }
                p.setListClasificacion(listClasificacion);
                fichaProcessXlsList.add(p);
            }
        } catch (Exception e) {
            String msg = "No se pudo calcular o aplicar la distribucion a la preficha de direccion " + p.getViaPrincipal() + " " + p.getPlaca();
            LOGGER.error(msg);
            throw new ApplicationException(msg);
        }

    }

    public String convertirDireccionaMGL(String dir) {
        String direccion1 = dir;
        //Siglas calle-carrera
        direccion1 = direccion1.replace("CR ", "KR ");
        direccion1 = direccion1.replace("TR ", "TV ");
        //SIglas barrio-Manzana
        direccion1 = direccion1.replace("AG ", "AGRUPACION ");
        return direccion1;
    }

    private PreFichaHHPPDetalleMgl getHHPPDetalle(PreFichaGeorreferenciaMgl fichaGeorreferenciaMgl,
            PreFichaXlsMgl preFichaXlsMgl,
            String direccionRR,
            PreFichaMgl fichaMgl) throws ApplicationException {

        PreFichaHHPPDetalleMgl detalleHHPPMgl = new PreFichaHHPPDetalleMgl();
        NodoMgl nodo = nodoMglFacadeLocal.findByCodigoNodo(fichaMgl.getNodoMgl().getNodCodigo(), fichaMgl.getNodoMgl().getGpoId(), fichaMgl.getNodoMgl().getNodTipo().getBasicaId());
        GeograficoPoliticoMgl geoPo = geograficoPoliticoMglFacadeLocal.findById(nodo.getGpoId());
        String comunidad = geoPo.getGpoId().toString();
        String division = geoPo.getRegionalTecnicaObj().getCodigoBasica();
        DireccionRRManager direccionRRManager = new DireccionRRManager(true);
        String headEnd = "9999";
        String postalCode = "000";

        detalleHHPPMgl.setTipoDireccion(preFichaXlsMgl.getIdTipoDireccion());
        detalleHHPPMgl.setCummunity(comunidad);
        detalleHHPPMgl.setDivision(division);
        detalleHHPPMgl.setBuildingName(fichaGeorreferenciaMgl.getNeighborhood());
        detalleHHPPMgl.setDealer("9999");
        detalleHHPPMgl.setDropType("A");
        detalleHHPPMgl.setDropTypeCable("11");
        detalleHHPPMgl.setFichaGeorreferenciaMgl(fichaGeorreferenciaMgl);
        detalleHHPPMgl.setGridPosition(comunidad);
        detalleHHPPMgl.setHeadEnd(headEnd);
        detalleHHPPMgl.setPlantLocType("ND");
        detalleHHPPMgl.setPlantLocation(prefichaCrear.getNodoMgl().getNodCodigo());
        detalleHHPPMgl.setPostalCode(postalCode);
        detalleHHPPMgl.setPreFichaXlsMgl(preFichaXlsMgl);
        detalleHHPPMgl.setFechaCreacion(new Date());
        detalleHHPPMgl.setFechaEdicion(new Date());
        detalleHHPPMgl.setUsuarioCreacion(httpSession.getAttribute("loginUserSecurity").toString());
        detalleHHPPMgl.setUsuarioEdicion(httpSession.getAttribute("loginUserSecurity").toString());
        detalleHHPPMgl.setPerfilCreacion(1);
        detalleHHPPMgl.setPerfilEdicion(1);
        detalleHHPPMgl.setStratus(fichaGeorreferenciaMgl.getLevelEconomic());
        String estUnit = cmtBasicaMglFacadeLocal.findValorExtendidoEstHhpp(estadoHhppExiste);
        detalleHHPPMgl.setUnitStatus(estUnit);//"E"
        String unitType = direccionRRManager.obtenerTipoEdificio(direccionRR, "VERIFICACION_CASAS", "FICHAS");
        detalleHHPPMgl.setUnitType(unitType);
        detalleHHPPMgl.setTipoVivienda(unitType);

        return detalleHHPPMgl;
    }

    public PreFichaGeorreferenciaMgl obtenerDireccionTabuladaPreficha(PreFichaXlsMgl preficha, String viaPrincipal, String placa, GeograficoPoliticoMgl ciudad, GeograficoPoliticoMgl centroPoblado, boolean isProcesar) {
        PreFichaGeorreferenciaMgl preFichaGeorreferenciaMgl = new PreFichaGeorreferenciaMgl();
        try {

            FichasGeoDrDireccionDto fichasGeoDrDireccionDto = new FichasGeoDrDireccionDto();
            DrDireccion drDireccion1 = new DrDireccion();
            String direccionPreficha;

            if ((!viaPrincipal.contains("|") && preficha.getIdTipoDireccion() != null && preficha.getIdTipoDireccion().equals("CK"))) {
                direccionPreficha = (viaPrincipal + " # " + placa);
            } else {
                direccionPreficha = viaPrincipal;
            }
            String barrio = preficha.getBarrio() == null ? (preficha.getFichaGeorreferenciaMgl().getNeighborhood() != null && !preficha.getFichaGeorreferenciaMgl().getNeighborhood().isEmpty() ? preficha.getFichaGeorreferenciaMgl().getNeighborhood() : "") : preficha.getBarrio();

            //Si la direccion es multiplaca se obtiene la placa sin / para poder pasar al geo en caso de ser Calle-Carrera
            if ((preficha.getPisos().intValue() > 3 && preficha.getClasificacion().equals(Constant.CASAS_RED_EXTERNA)) || preficha.getClasificacion().equals(Constant.EDIFICIOSVT)) {
                if (placa.contains("/")) {
                    placa = placa.substring(0, placa.indexOf("/"));
                }
            }

            if (preficha.getIdTipoDireccion() != null && !preficha.getIdTipoDireccion().isEmpty()) {

                if (preficha.getIdTipoDireccion().equals(Constant.ACRONIMO_IT)) {
                    barrio = "";
                }

                switch (preficha.getIdTipoDireccion()) {
                    case Constant.ACRONIMO_IT: {
                        //Se construye DrDireccion para direccion Intraducible a partir de un String
                        drDireccion1 = construirDireccionIT(direccionPreficha, centroPoblado, drDireccion1, placa);
                        drDireccion1.setBarrio(barrio);
                        AddressService responseGeo = new AddressService();
                        responseGeo.setRespuestaGeo(AddressGeodata.instanciaVacia());
                        preficha.setResponseGeo(responseGeo);
                        preficha.setGeoDrDireccionNull(true);
                        preficha.setGeoDrDireccionNotNull(false);
                        break;
                    }
                    case Constant.ACRONIMO_BM: {
                        //Se construye DrDireccion para direccion Barrio-Manzana a partir de un String
                        if (preficha.getBarrio() != null) {
                            drDireccion1.setBarrio(preficha.getBarrio());
                        } else {
                            drDireccion1.setBarrio(obtenerBarrioDireccionBM((direccionPreficha.split("#")[0]).trim()));
                        }
                        preficha.setGeoDrDireccionNull(true);
                        preficha.setGeoDrDireccionNotNull(false);
                        drDireccion1 = construirDireccionBM(direccionPreficha, centroPoblado, drDireccion1, placa);
                        AddressService responseGeo = new AddressService();
                        responseGeo.setRespuestaGeo(AddressGeodata.instanciaVacia());
                        preficha.setResponseGeo(responseGeo);
                        break;
                    }
                    case Constant.ACRONIMO_CK:

                        //cuando ya se le construyó una dirección tabulada
                        if (preficha.getDireccionTabulada() != null) {
                            drDireccion1 = preficha.getDireccionTabulada();
                            AddressService responseGeo = new AddressService();
                            responseGeo.setRespuestaGeo(AddressGeodata.instanciaVacia());
                            preficha.setResponseGeo(responseGeo);
                            
                            preficha.setGeoDrDireccionNull(true);
                            preficha.setGeoDrDireccionNotNull(false);

                            if (edificiosVtXls != null && !edificiosVtXls.isEmpty()) {
                                for (PreFichaXlsMgl pr : edificiosVtXls) {
                                    if (pr.getId().compareTo(preficha.getId()) == 0) {
                                        pr.setGeoDrDireccionNull(true);
                                        break;
                                    }
                                }
                            }

                            if (casasRedExternaXls != null && !casasRedExternaXls.isEmpty()) {
                                for (PreFichaXlsMgl pr : casasRedExternaXls) {
                                    if (pr.getId().compareTo(preficha.getId()) == 0) {
                                        pr.setGeoDrDireccionNull(true);
                                        break;
                                    }
                                }
                            }

                            if (conjCasasVtXls != null && !conjCasasVtXls.isEmpty()) {
                                for (PreFichaXlsMgl pr : conjCasasVtXls) {
                                    if (pr.getId().compareTo(preficha.getId()) == 0) {
                                        pr.setGeoDrDireccionNull(true);
                                        break;
                                    }
                                }
                            }

                        } else {

                            //Se construye DrDireccion para direccion Calle-Carrera a partir del codigo que devuelve el Geo
                            fichasGeoDrDireccionDto = cmtValidadorDireccionesFacadeLocal.convertirDireccionStringADrDireccionFichas(direccionPreficha, centroPoblado.getGpoId());

                            //Si el GEO SI pudo traducir la dirección
                            if (fichasGeoDrDireccionDto != null) {
                                drDireccion1 = fichasGeoDrDireccionDto.getDrDireccion();
                                drDireccion1.setIdDirCatastro(fichasGeoDrDireccionDto.getResponseGeo().getIdaddress());
                                preficha.setResponseGeo(fichasGeoDrDireccionDto.getResponseGeo());
                                preficha.setGeoDrDireccionNull(true);
                                preficha.setGeoDrDireccionNotNull(false);

                                //Cuando si la traduce
                                for (PreFichaXlsMgl pr : edificiosVtXls) {
                                    if (pr.getId().compareTo(preficha.getId()) == 0) {
                                        pr.setGeoDrDireccionNull(true);
                                        pr.setGeoDrDireccionNotNull(false);
                                        //edificiosVtXls.remove(pr);
                                        //edificiosVtXls.add(pr);
                                        //dirOk = true;
                                    }
                                }

                                for (PreFichaXlsMgl pr : casasRedExternaXls) {
                                    if (pr.getId().compareTo(preficha.getId()) == 0) {
                                        pr.setGeoDrDireccionNull(true);
                                        pr.setGeoDrDireccionNotNull(false);
                                        //casasRedExternaXls.remove(pr);
                                        //casasRedExternaXls.add(pr);
                                        //dirOk = true;
                                    }
                                }

                                for (PreFichaXlsMgl pr : conjCasasVtXls) {
                                    if (pr.getId().compareTo(preficha.getId()) == 0) {
                                        pr.setGeoDrDireccionNull(true);
                                        pr.setGeoDrDireccionNotNull(false);
                                        // conjCasasVtXls.remove(pr);
                                        //conjCasasVtXls.add(pr);
                                        //dirOk = true;
                                    }
                                }

                            } else {

                                //Primer recorrido y no la puede traducir el GEO
                                for (PreFichaXlsMgl pr : edificiosVtXls) {
                                    if (pr.getId().compareTo(preficha.getId()) == 0) {
                                        pr.setGeoDrDireccionNotNull(true);
                                        pr.setGeoDrDireccionNull(false);
                                        // edificiosVtXls.remove(pr);
                                        // edificiosVtXls.add(pr);
//                                            dirOk = true;
                                    }
                                }

                                for (PreFichaXlsMgl pr : casasRedExternaXls) {
                                    if (pr.getId().compareTo(preficha.getId()) == 0) {
                                        pr.setGeoDrDireccionNotNull(true);
                                        pr.setGeoDrDireccionNull(false);
                                        // casasRedExternaXls.remove(pr);
                                        // casasRedExternaXls.add(pr);                                            
//                                            dirOk = true;
                                    }
                                }

                                for (PreFichaXlsMgl pr : conjCasasVtXls) {
                                    if (pr.getId().compareTo(preficha.getId()) == 0) {
                                        pr.setGeoDrDireccionNotNull(true);
                                        pr.setGeoDrDireccionNull(false);
                                        // conjCasasVtXls.remove(pr);
                                        // conjCasasVtXls.add(pr);                                            
//                                            dirOk = true;
                                    }
                                }
                            }
                        }
                }
            }
            drDireccion1.setEstrato(preficha.getFichaGeorreferenciaMgl().getCambioEstrato());
            drDireccion1.setDireccionHHPP(direccionPreficha);

            if (ciudad.getGpoMultiorigen().equals("1") && preficha.getIdTipoDireccion().equals(Constant.ACRONIMO_CK)) {
                String bRef = null;
                if (fichasGeoDrDireccionDto != null && fichasGeoDrDireccionDto.getResponseGeo().getAddressSuggested() != null && !fichasGeoDrDireccionDto.getResponseGeo().getAddressSuggested().isEmpty()) {
                    bRef = fichasGeoDrDireccionDto.getResponseGeo().getAddressSuggested().get(0).getNeighborhood();
                } else if (fichasGeoDrDireccionDto != null && fichasGeoDrDireccionDto.getResponseGeo() != null) {
                    bRef = fichasGeoDrDireccionDto.getResponseGeo().getNeighborhood();
                }
                if (bRef != null) {
                    if (preficha.getBarrio() != null && preficha.getBarrio().equals(bRef) || bRef.isEmpty()) {
                        drDireccion1.setBarrio(preficha.getBarrio());
                        if (fichasGeoDrDireccionDto != null && fichasGeoDrDireccionDto.getResponseGeo() != null) {
                            preFichaGeorreferenciaMgl = copyResposeGeoToPreFicha(fichasGeoDrDireccionDto.getResponseGeo());
                        }
                    } else if (preficha.getBarrio() != null && !preficha.getBarrio().equals(bRef)) {
                        drDireccion1.setBarrio(preficha.getBarrio());
                        preFichaGeorreferenciaMgl.setNeighborhood(preficha.getBarrio());
                    }
                }
            } else if (preficha.getIdTipoDireccion().equals(Constant.ACRONIMO_CK)) {
                if (preficha.getFichaGeorreferenciaMgl().getNeighborhood() != null && !preficha.getFichaGeorreferenciaMgl().getNeighborhood().isEmpty()) {
                    drDireccion1.setBarrio(preficha.getFichaGeorreferenciaMgl().getNeighborhood());
                    preFichaGeorreferenciaMgl.setNeighborhood(preficha.getFichaGeorreferenciaMgl().getNeighborhood());
                } else {
                    drDireccion1.setBarrio(preficha.getBarrio() != null ? preficha.getBarrio() : "");
                    preFichaGeorreferenciaMgl.setNeighborhood(preficha.getBarrio() != null ? preficha.getBarrio() : "");
                }
                if (fichasGeoDrDireccionDto != null && fichasGeoDrDireccionDto.getResponseGeo() != null) {
                    preFichaGeorreferenciaMgl = copyResposeGeoToPreFicha(fichasGeoDrDireccionDto.getResponseGeo());
                }

            }

            preFichaGeorreferenciaMgl.setAddressCode(preficha.getFichaGeorreferenciaMgl().getAddressCode() != null ? preficha.getFichaGeorreferenciaMgl().getAddressCode() : "");

            if (preficha.isGeoDrDireccionNull()) {
                preFichaGeorreferenciaMgl.setDrDireccionPreficha(drDireccion1);

                if (preficha.getDrDireccionList() == null) {
                    preficha.setDrDireccionList(new ArrayList<>());
                }
                preficha.getDrDireccionList().add(drDireccion1);

            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerDireccionTabuladaPreficha. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerDireccionTabuladaPreficha. ", e, LOGGER);
        } finally {
            preFichaGeorreferenciaMgl.setEstadoRegistro(1);
            return preFichaGeorreferenciaMgl;
        }
    }

    public DireccionRREntity agregarComplementosHHPPFichas(DrDireccion drDireccionPreficha, String direccionHHPP, PreFichaXlsMgl preficha) throws CloneNotSupportedException {
        DrDireccion drDireccionHHPP = drDireccionPreficha.clone();
        DireccionRREntity direccionRR = null;
        try {
            if (!direccionHHPP.isEmpty() && direccionHHPP.contains("|")) {
                String complementosHHPP = direccionHHPP.substring(direccionHHPP.indexOf("|") + 1, direccionHHPP.length());
                List <OpcionIdNombre> complementos = getDataComplementosDistribuciones(complementosHHPP);
                for (OpcionIdNombre entrada : complementos) {                    
                
                    drDireccionHHPP = drDireccionFacadeLocal.agregaNivelCompAptoDireccion(drDireccionHHPP, "A", entrada.getIdParametro(), entrada.getDescripcion());
                }
                direccionRR = cmtValidadorDireccionesFacadeLocal.convertirDrDireccionARR(drDireccionHHPP, "0");
                preficha.getDrDireccionList().add(drDireccionHHPP);
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en agregarComplementosHHPPFichas: " + e.getMessage() + "", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en agregarComplementosHHPPFichas. " + e.getMessage() + " ", e, LOGGER);
        } finally {
            return direccionRR;
        }
    }

    private PreFichaGeorreferenciaMgl georreferenciarPrefichaElement(PreFichaXlsMgl p, String viaGene, String placa, String state, String city) throws ApplicationException {
        PreFichaGeorreferenciaMgl preFichaGeorreferenciaMgl = new PreFichaGeorreferenciaMgl();
        FichasGeoDrDireccionDto fichasGeoDrDireccionDto = new FichasGeoDrDireccionDto();
        DrDireccion drDireccion = new DrDireccion();
        String direccionHHPP;

        if ((p.getPisos().intValue() > 3 && p.getClasificacion().equals(Constant.CASAS_RED_EXTERNA)) || p.getClasificacion().equals(Constant.EDIFICIOSVT)) {
            if (placa.contains("/")) {
                placa = placa.substring(0, placa.indexOf("/"));
            }
        }

        if (p.getIdTipoDireccion() != null) {

            if ((!viaGene.contains("|") && p.getIdTipoDireccion() != null && p.getIdTipoDireccion().equals("CK"))) {
                direccionHHPP = (viaGene + " # " + placa);
            } else {
                direccionHHPP = viaGene;
            }

            String complemento = direccionHHPP;
            BigDecimal idGeoPol = prefichaCrear.getNodoMgl().getGpoId();
            GeograficoPoliticoMgl centroPoblado = geograficoPoliticoMglFacadeLocal.findById(idGeoPol);
            GeograficoPoliticoMgl ciudad = geograficoPoliticoMglFacadeLocal.findById(centroPoblado.getGeoGpoId());

            String barrio = p.getBarrio() == null ? (p.getFichaGeorreferenciaMgl().getNeighborhood() != null && !p.getFichaGeorreferenciaMgl().getNeighborhood().isEmpty() ? p.getFichaGeorreferenciaMgl().getNeighborhood() : "") : p.getBarrio();
            if (p.getIdTipoDireccion().equals(Constant.ACRONIMO_IT)) {
                barrio = "";
            }

            if (p.getIdTipoDireccion() != null && p.getIdTipoDireccion().equals(Constant.ACRONIMO_IT)) {
                //Se construye la direccion tabulada para direcciones tipo intraducible
                drDireccion.setBarrio(barrio);
                drDireccion = construirDireccionIT(viaGene, centroPoblado, drDireccion, placa);
                AddressService responseGeo = new AddressService();
                responseGeo.setRespuestaGeo(AddressGeodata.instanciaVacia());
                p.setResponseGeo(responseGeo);
            } else if (p.getIdTipoDireccion() != null && p.getIdTipoDireccion().equals(Constant.ACRONIMO_BM)) {
                //Se construye la direccion tabulada para direcciones tipo barrio-manzana
                if (p.getBarrio() != null) {
                    drDireccion.setBarrio(p.getBarrio());
                } else {
                    drDireccion.setBarrio(obtenerBarrioDireccionBM((direccionHHPP.split("#")[0]).trim()));
                }
                drDireccion = construirDireccionBM(viaGene.contains("#") ? viaGene : viaGene + " # " + placa, centroPoblado, drDireccion, placa);
                AddressService responseGeo = new AddressService();
                responseGeo.setRespuestaGeo(AddressGeodata.instanciaVacia());
                p.setResponseGeo(responseGeo);
            } else {
                //Se construye la direccion tabulada para direcciones tipo calle-carrera a partir del codigo del retornado por el geo
                fichasGeoDrDireccionDto = cmtValidadorDireccionesFacadeLocal.convertirDireccionStringADrDireccionFichas(direccionHHPP, centroPoblado.getGpoId());
                if (fichasGeoDrDireccionDto != null) {
                    drDireccion = fichasGeoDrDireccionDto.getDrDireccion();
                    drDireccion.setIdDirCatastro(fichasGeoDrDireccionDto.getResponseGeo().getIdaddress());
                    p.setResponseGeo(fichasGeoDrDireccionDto.getResponseGeo());
                }
            }

            if (drDireccion != null) {
                if (complemento != null && !complemento.isEmpty() && complemento.contains("|") && complemento.matches(".*([A-Za-z\\s]{4,}).*")) {
                    //Agrega complementos si la direccion tiene | y ademas es de tipo Barrio-Manzana o Intraducicle
                    agregarComplementos(drDireccion, complemento, centroPoblado.getGeoCodigoDane(), p.getFichaGeorreferenciaMgl().getAddressCode(), ciudad.getGpoId());
                }

                if (direccionHHPP.contains("|")) {
                    String complementos[] = direccionHHPP.split("\\|");
                    if (complementos.length != 0 && complementos[1].equals(" CASA")) {
                        drDireccion.setCpTipoNivel5("CASA");
                        drDireccion.setCpValorNivel5(null);
                    }
                }
                if (p.getDrDireccionList() == null) {
                    p.setDrDireccionList(new ArrayList<>());
                }
                drDireccion.setEstrato(p.getFichaGeorreferenciaMgl().getCambioEstrato());

                if (ciudad.getGpoMultiorigen().equals("1") && p.getIdTipoDireccion().equals(Constant.ACRONIMO_CK)) {
                    String bRef;
                    if (fichasGeoDrDireccionDto != null && fichasGeoDrDireccionDto.getResponseGeo().getAddressSuggested() != null && !fichasGeoDrDireccionDto.getResponseGeo().getAddressSuggested().isEmpty()) {
                        bRef = fichasGeoDrDireccionDto.getResponseGeo().getAddressSuggested().get(0).getNeighborhood();
                    } else {
                        bRef = fichasGeoDrDireccionDto != null ? fichasGeoDrDireccionDto.getResponseGeo().getNeighborhood() : null;
                    }
                    if (bRef != null) {
                        if (p.getBarrio() != null && p.getBarrio().equals(bRef) || bRef.isEmpty()) {
                            drDireccion.setBarrio(p.getBarrio());
                            preFichaGeorreferenciaMgl = copyResposeGeoToPreFicha(fichasGeoDrDireccionDto != null ? fichasGeoDrDireccionDto.getResponseGeo() : null);
                        } else if (p.getBarrio() != null && !p.getBarrio().equals(bRef)) {
                            drDireccion.setBarrio(p.getBarrio());
                            preFichaGeorreferenciaMgl.setNeighborhood(p.getBarrio());
                        }
                    }
                } else if (p.getIdTipoDireccion().equals(Constant.ACRONIMO_CK)) {
                    if (p.getFichaGeorreferenciaMgl().getNeighborhood() != null && !p.getFichaGeorreferenciaMgl().getNeighborhood().isEmpty()) {
                        drDireccion.setBarrio(p.getFichaGeorreferenciaMgl().getNeighborhood());
                        preFichaGeorreferenciaMgl.setNeighborhood(p.getFichaGeorreferenciaMgl().getNeighborhood());
                    } else {
                        drDireccion.setBarrio(p.getBarrio() != null ? p.getBarrio() : "");
                        preFichaGeorreferenciaMgl.setNeighborhood(p.getBarrio() != null ? p.getBarrio() : "");
                    }
                    preFichaGeorreferenciaMgl = copyResposeGeoToPreFicha(fichasGeoDrDireccionDto != null ? fichasGeoDrDireccionDto.getResponseGeo() : null);
                }

                drDireccion.setDireccionHHPP(direccionHHPP);
                p.getDrDireccionList().add(drDireccion);
                preFichaGeorreferenciaMgl.setAddressCode(p.getFichaGeorreferenciaMgl().getAddressCode() != null ? p.getFichaGeorreferenciaMgl().getAddressCode() : "");

                if (viaGene.contains("|")) {
                    DireccionRREntity direccionRR = cmtValidadorDireccionesFacadeLocal.convertirDrDireccionARR(drDireccion, "0");
                    preFichaGeorreferenciaMgl.setDireccionFormatoRR("[" + direccionRR.getCalle() + "]" + "[" + direccionRR.getNumeroUnidad() + "]" + "[" + direccionRR.getNumeroApartamento() + "]");

                }
                LOGGER.error("responseGeo: " + p.getFichaGeorreferenciaMgl().getAddress() != null ? p.getFichaGeorreferenciaMgl().getAddress() : "");
            } else {
                String msg = "No fue construído el DrDireccion para la dirección " + direccionHHPP + " con Tipo Dirección " + p.getIdTipoDireccion();
                LOGGER.error(msg);
                throw new ApplicationException(msg);
            }
        } else {
            String msg = "La dirección " + viaGene + " " + placa + " no contiene tipo de dirección.";
            LOGGER.error(msg);
            throw new ApplicationException(msg);
        }

        return preFichaGeorreferenciaMgl;

    }

    public CmtDireccionDetalladaMgl crearDireccionMGL(String direccionHHPP, DrDireccion drDireccion, String city, String state, PreFichaXlsMgl p, 
            boolean isCCMM, String codigoDane) throws ApplicationException {

        CmtDireccionDetalladaMgl direccionDetallada = new CmtDireccionDetalladaMgl();
        try {
            AddressRequest addressRequest = new AddressRequest();
            if (p.getIdTipoDireccion().equals("BM")) {
                String direccionBarrioManzana;
                if (direccionHHPP.contains(NIVELES_DIRECCION_BARRIO_MANZANA.BARRIO.toString() + " ")) {
                    direccionBarrioManzana = direccionHHPP.replace(NIVELES_DIRECCION_BARRIO_MANZANA.BARRIO.toString() + " ", "");
                    addressRequest.setAddress(direccionBarrioManzana);
                }
                if (direccionHHPP.contains(NIVELES_DIRECCION_BARRIO_MANZANA.BR.toString() + " ")) {
                    direccionBarrioManzana = direccionHHPP.replace(NIVELES_DIRECCION_BARRIO_MANZANA.BR.toString() + " ", "");
                    addressRequest.setAddress(direccionBarrioManzana);
                }
            } else {
                addressRequest.setAddress(direccionHHPP);
            }
            city = city.replace(",", "");

            addressRequest.setCity(city);
            addressRequest.setState(state);
            addressRequest.setCodDaneVt(codigoDane);
            if (p.getIdTipoDireccion().equals("CK")) {
                if (p.getFichaGeorreferenciaMgl().getNeighborhood() != null && !p.getFichaGeorreferenciaMgl().getNeighborhood().isEmpty()) {
                    addressRequest.setNeighborhood(p.getFichaGeorreferenciaMgl().getNeighborhood());
                } else {
                    addressRequest.setNeighborhood(p.getBarrio() != null ? p.getBarrio() : "");
                }
            } else {
                addressRequest.setNeighborhood(drDireccion.getBarrio());
            }
            //Revisar Creacion Direccion
            ResponseMessage responseCreate;
            if (isCCMM) {
                responseCreate = addressEJBRemote.createAddressFichas(p.getResponseGeo().getRespuestaGeo(), new AddressGeodata(), addressRequest,  p.getUsuarioCreacion(), "MGL-VT", "", drDireccion);
            } else {
                responseCreate = addressEJBRemote.createAddress(addressRequest, p.getUsuarioCreacion(), "MGL-VT", "", drDireccion);
            }

            if (responseCreate != null && responseCreate.getIdaddress() != null && !responseCreate.getIdaddress().isEmpty()) {
                drDireccion.setIdDirCatastro(responseCreate.getIdaddress());
                if (p.getIdTipoDireccion().equals("IT")) {
                    drDireccion.setBarrio("NG");
                }
                direccionDetallada = responseCreate.getNuevaDireccionDetallada();
            } else {
                throw new Exception(responseCreate != null ? responseCreate.getMessageText() : "No se pudo crear la direccion causa:Null en la respuesta");
            }

        } catch (ExceptionDB e) {
            FacesUtil.mostrarMensajeError("Error en crearDireccionMGL: " + e.getMessage() + " ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en crearDireccionMGL: " + e.getMessage() + " ", e, LOGGER);
        } finally {
            return direccionDetallada;
        }
    }

    public String obtenerBarrioDireccionBM(String direccion) {
        String barrio = "NG";
        String valoresDireccion[] = direccion.split(" ");
        if (valoresDireccion.length > 1 && (valoresDireccion[0].contains(NIVELES_DIRECCION_BARRIO_MANZANA.BARRIO.toString()) || valoresDireccion[0].contains(NIVELES_DIRECCION_BARRIO_MANZANA.BR.toString()))) {
            barrio = "";
            for (int i = 1; i < valoresDireccion.length; i++) {
                String tipoNivel = nivelDireccionBarrioManzana(valoresDireccion[i]);
                if (tipoNivel == null) {
                    if (i == 1) {
                        barrio = valoresDireccion[i];
                    } else {
                        barrio = barrio + " " + valoresDireccion[i];
                    }

                } else {
                    break;
                }
            }
        } else {
            barrio = "";
            for (int i = 0; i < valoresDireccion.length; i++) {
                if (!valoresDireccion[i].equals(NIVELES_DIRECCION_BARRIO_MANZANA.ETAPA.toString()) && !valoresDireccion[i].equals(NIVELES_DIRECCION_BARRIO_MANZANA.ET.toString())
                        && !valoresDireccion[i].equals(NIVELES_DIRECCION_BARRIO_MANZANA.SECTOR.toString()) && !valoresDireccion[i].equals(NIVELES_DIRECCION_BARRIO_MANZANA.SC.toString())
                        && !valoresDireccion[i].equals(NIVELES_DIRECCION_BARRIO_MANZANA.SMZ.toString()) && !valoresDireccion[i].equals(NIVELES_DIRECCION_BARRIO_MANZANA.SM.toString())) {
                    if (i == 0) {
                        barrio = valoresDireccion[i];
                    } else {
                        barrio = barrio + " " + valoresDireccion[i];
                    }
                } else {
                    break;
                }
            }
        }
        if (barrio.contains("AG ")) {
            barrio = barrio.replace("AG ", "AGRUPACION ");
        } else if (barrio.contains("CD ")) {
            barrio = barrio.replace("CD ", "CIUDADELA ");
        } else if (barrio.contains("CO ")) {
            barrio = barrio.replace("CO ", "CONJUNTO RESIDENCIAL ");
        } else if (barrio.contains("UL ")) {
            barrio = barrio.replace("UL ", "UNIDAD RESIDENCIAL ");
        } else if (barrio.contains("UR ")) {
            barrio = barrio.replace("UR ", "URBANIZACION ");
        }
        return barrio.trim();
    }

    public void agregarComplementos(DrDireccion drDireccion, String complemento, String codigoDane, String codGeo, BigDecimal city) {
        complemento = complemento.substring(complemento.indexOf("|") + 1, complemento.length());
        try {
              List <OpcionIdNombre> complements = getDataComplementosDistribuciones(complemento);
              
             HashMap<String, String> complementos = new HashMap<>();
            for (OpcionIdNombre comple : complements) {
                complementos.put(comple.getIdParametro(), comple.getDescripcion());
            }                
            //si existe el caso de tener piso+ apto o alguna otra combinacion posble
            //se procesara primero
            String tipodireccion = drDireccion.getIdTipoDireccion() == null
                    || drDireccion.getIdTipoDireccion().trim().isEmpty()
                    ? "CK" : drDireccion.getIdTipoDireccion();
            if (valorInicial != null) {

                String tipoNivelComplemento = valorInicial;
                String valorNivel = complementos.get(valorInicial);

                RequestConstruccionDireccion request = new RequestConstruccionDireccion();
                request.setComunidad(codigoDane);
                drDireccion.setIdTipoDireccion(tipodireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelComplemento);
                request.setValorNivel(valorNivel);
                String tipoAdicion = (isComplemento("")) ? "C" : "A";
                request.setTipoAdicion(tipoAdicion);
                request.setIdUsuario("USUARIO");
                request.setIsFichas(true);
                complementos.remove(valorInicial);
                valorInicial = null;

            }
            //SE PROCESA EL RESTO DE ELEMENTOS
            for (Map.Entry<String, String> entry : complementos.entrySet()) {

                String tipoNivelComplemento = entry.getKey();
                String valorNivel = entry.getValue();

                RequestConstruccionDireccion request = new RequestConstruccionDireccion();
                request.setComunidad(codigoDane);
                drDireccion.setIdTipoDireccion(tipodireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelComplemento);
                request.setValorNivel(valorNivel);
                String tipoAdicion = (isComplemento("")) ? "C" : "A";
                request.setTipoAdicion(tipoAdicion);
                request.setIdUsuario("USUARIO");
                valorInicial = null;
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en agregarComplementos: " + e.getMessage() + " ", e, LOGGER);
        }

    }

    /**
     * Función que se encarga de Construir la direccion IT a partir de la placa
     * y la viageneradora
     *
     * @author Jonathan Peña
     * @return String
     */
    private DrDireccion construirDireccionIT(String direccion,
            GeograficoPoliticoMgl centroPoblado, DrDireccion drDireccion, String placa) throws ApplicationException {

        drDireccion.setIdTipoDireccion("IT");
        String[] valores = direccion.split("#");
        direccion = valores[0];
        valores = direccion.split(" ");
        String nivel;
        for (int i = 0; i < valores.length; i++) {
            nivel = valores[i];
            RequestConstruccionDireccion request = new RequestConstruccionDireccion();
            request.setComunidad(centroPoblado.getGeoCodigoDane());
            request.setDrDireccion(drDireccion);
            if (nivelDireccionIntraducible(nivel) != null) {
                request.setTipoNivel(nivelDireccionIntraducible(nivel));
                String valor = "";
                if ((i + 1) < valores.length) {
                    while (nivelDireccionIntraducible(valores[i + 1]) == null) {
                        valor = valor + " " + valores[i + 1];
                        i++;
                        if (i == valores.length - 1) {
                            break;
                        }
                    }
                }
                request.setValorNivel(valor.trim());
                request.setTipoAdicion("P");
                request.setIdUsuario(usuarioLogin.getCedula().toString());
                drDireccion = direccionRRFacadeLocal.construirDireccionSolicitud(request).getDrDireccion();
            }
        }
        if ((placa.contains("SC") | placa.contains("SECTOR") | placa.contains("ST"))) {
            RequestConstruccionDireccion request = new RequestConstruccionDireccion();
            request.setComunidad(centroPoblado.getGeoCodigoDane());
            request.setDrDireccion(drDireccion);
            if (nivelDireccionBarrioManzana(placa.substring(0, placa.indexOf(" "))) != null) {
                request.setTipoNivel(nivelDireccionBarrioManzana(placa.substring(0, placa.indexOf(" "))));
                String valor = placa.contains("SC") ? placa.replace("SC ", "")
                        : placa.contains("SECTOR") ? placa.replace("SECTOR ", "")
                        : placa.contains("ST") ? placa.replace("ST ", "") : "";
                request.setValorNivel(valor.trim());
                request.setTipoAdicion("P");
                request.setIdUsuario(usuarioLogin.getCedula().toString());
                drDireccion = direccionRRFacadeLocal.construirDireccionSolicitud(request).getDrDireccion();
            }
        }
        if (placa.contains("-CS") || placa.contains("-C")) {
            drDireccion.setItTipoPlaca("MANZANA-CASA");
            placa = placa.replace("-CS", "-C");
        } else {
            drDireccion.setItTipoPlaca("PLACA");
        }
        drDireccion.setItValorPlaca(placa.replace(" ", ""));
        return drDireccion;
    }

    /**
     * Función que se encarga de Construir la direccion BM a partir de la placa
     * y la viageneradora
     *
     * @author Jonathan Peña
     * @return String
     */
    private DrDireccion construirDireccionBM(String direccion, GeograficoPoliticoMgl centroPoblado, DrDireccion drDireccion, String placaDireccion) throws ApplicationException {
        drDireccion.setIdTipoDireccion("BM");
        direccion = direccion.replace("|", "");
        String[] valores = direccion.split("#");
        direccion = valores[0];
        direccion = direccion.replace("-", " ");
        valores = direccion.split(" ");
        String nivel;
        for (int i = 0; i < valores.length; i++) {
            nivel = valores[i];
            RequestConstruccionDireccion request = new RequestConstruccionDireccion();
            request.setComunidad(centroPoblado.getGeoCodigoDane());
            request.setDrDireccion(drDireccion);
            if (i == 0 && valores.length > 1) {
                if (valores[0].equals("CONJUNTO") && valores[1].equals("RESIDENCIAL")) {
                    nivel = "CONJUNTO_RESIDENCIAL";
                    i++;
                }
                if (valores[0].equals("UNIDAD") && valores[1].equals("RESIDENCIAL")) {
                    nivel = "UNIDAD_RESIDENCIAL";
                    i++;
                }
            }
            if (nivelDireccionBarrioManzana(nivel) != null) {
                request.setTipoNivel(nivelDireccionBarrioManzana(nivel));
                String valor = "";
                while (nivelDireccionBarrioManzana(valores[i + 1]) == null) {
                    valor = valor + " " + valores[i + 1];
                    i++;
                    if (i == valores.length - 1) {
                        break;
                    }
                }
                request.setValorNivel(valor.trim());
                request.setTipoAdicion("P");
                request.setIdUsuario(usuarioLogin.getCedula().toString());
                drDireccion = direccionRRFacadeLocal.construirDireccionSolicitud(request).getDrDireccion();
            }
        }

        String valoresPlaca[] = placaDireccion.split("-");
        if (valoresPlaca.length == 2 && (valoresPlaca[0].contains("MZ "))) {
            String valorNivel4 = valoresPlaca[0].replace("MZ ", "");
            drDireccion.setMzTipoNivel4("MANZANA");
            drDireccion.setMzValorNivel4(valorNivel4.trim());
        }
        if (valoresPlaca.length == 2 && valoresPlaca[1].contains("C ")) {
            String valorNivel5 = valoresPlaca[1].replace("C ", "");
            drDireccion.setMzTipoNivel5("CASA");
            drDireccion.setMzValorNivel5(valorNivel5.trim());
        }
        if (valoresPlaca.length == 2 && valoresPlaca[1].contains("CS ")) {
            String valorNivel5 = valoresPlaca[1].replace("CS ", "");
            drDireccion.setMzTipoNivel5("CASA");
            drDireccion.setMzValorNivel5(valorNivel5.trim());
        }
        if (valoresPlaca.length == 2 && valoresPlaca[1].contains("LT ")) {
            String valorNivel5 = valoresPlaca[1].replace("LT ", "");
            drDireccion.setMzTipoNivel5("LOTE");
            drDireccion.setMzValorNivel5(valorNivel5.trim());
        }
        if (valoresPlaca.length == 2 && valoresPlaca[1].contains("L ")) {
            String valorNivel5 = valoresPlaca[1].replace("L ", "");
            drDireccion.setMzTipoNivel5("LOTE");
            drDireccion.setMzValorNivel5(valorNivel5.trim());
        }
        if (valoresPlaca.length == 2 && (valoresPlaca[0].contains("SC") | valoresPlaca[0].contains("SECTOR") | valoresPlaca[0].contains("ST"))) {
            RequestConstruccionDireccion request = new RequestConstruccionDireccion();
            request.setComunidad(centroPoblado.getGeoCodigoDane());
            request.setDrDireccion(drDireccion);
            if (nivelDireccionBarrioManzana(valoresPlaca[0].substring(0, valoresPlaca[0].indexOf(" "))) != null) {
                request.setTipoNivel(nivelDireccionBarrioManzana(valoresPlaca[0].substring(0, valoresPlaca[0].indexOf(" "))));
                String valor = valoresPlaca[0].contains("SC") ? valoresPlaca[0].replace("SC ", "")
                        : valoresPlaca[0].contains("SECTOR") ? valoresPlaca[0].replace("SECTOR ", "")
                        : valoresPlaca[0].contains("ST") ? valoresPlaca[0].replace("ST ", "") : "";
                request.setValorNivel(valor.trim());
                request.setTipoAdicion("P");
                request.setIdUsuario(usuarioLogin.getCedula().toString());
                drDireccion = direccionRRFacadeLocal.construirDireccionSolicitud(request).getDrDireccion();
            }
        }
        return drDireccion;
    }

    public String nivelDireccionBarrioManzana(String nivel) {
        try {
            NIVELES_DIRECCION_BARRIO_MANZANA nivelesBarrioManzana[] = NIVELES_DIRECCION_BARRIO_MANZANA.values();
            for (int i = 0; i < nivelesBarrioManzana.length; i++) {
                if (nivel.equals(nivelesBarrioManzana[i].toString())) {
                    return nivelesBarrioManzana[i].getValue();
                }
            }
            return null;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en nivelDireccionBarrioManzana: " + e.getMessage() + "", e, LOGGER);
            return null;
        }
    }

    public String nivelDireccionIntraducible(String nivel) {
        try {
            NIVELES_DIRECCION_INTRADUCIBLE nivelesIntraducible[] = NIVELES_DIRECCION_INTRADUCIBLE.values();
            for (int i = 0; i < nivelesIntraducible.length; i++) {
                if (nivel.equals(nivelesIntraducible[i].toString())) {
                    return nivelesIntraducible[i].getValue();
                }
            }
            return null;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en nivelDireccionIntraducible: " + e.getMessage() + " ", e, LOGGER);
            return null;
        }
    }

    /**
     * Función que el String a eviar para el tipo de complemento o apartamento
     * para el tipo de direcciones BM o IT
     *
     * @author Jonathan Peña
     * @param complemento
     * @return String
     */
   public List <OpcionIdNombre> getDataComplementosDistribuciones(String complementoGlobal) {
        String complemento = complementoGlobal.trim();
        String[] direccion1 = complemento.trim().split(" ");
        ArrayList<String> ls = new ArrayList<>();

        //Pasamos los complementos a una lista  
        for (String d : direccion1) {
            ls.add(d);
        }
        
        List <OpcionIdNombre> com = new ArrayList();
        //Recorremos la lista que tenemos para llenar el hasmap con el tipo de
        //complemento o de apartamento
        for (String elemento : ls) {
            if (!elemento.equalsIgnoreCase("") || !elemento.isEmpty()) {
                String[] valores = elemento.split("-");
                if (valores[0].equals("PISO") || valores[0].equals("CASA")) {
                    valores[0] = tipoApto(complemento, valores[0]);
                }

                if (valores.length > 1) {
                    OpcionIdNombre complementoDir = new OpcionIdNombre();
                    complementoDir.setIdParametro(valores[0]);
                    complementoDir.setDescripcion(valores[1]);
                    com.add(complementoDir);                    
                } else {
                    if (valores[0].contains("PISO")) {
                        if (complemento.split(" ").length == 3) {
                            OpcionIdNombre complementoDir = new OpcionIdNombre();
                            complementoDir.setIdParametro(complemento.split(" ")[1]);
                            complementoDir.setDescripcion(complemento.split(" ")[2]);
                            com.add(complementoDir);
                        } else {
                            OpcionIdNombre complementoDir = new OpcionIdNombre();
                            complementoDir.setIdParametro(complemento.split(" ")[0]);
                            complementoDir.setDescripcion(complemento.split(" ")[1]);
                            com.add(complementoDir);
                        }
                    }
                }
            }
        }

        String complementos[] = complemento.split("\\|");
        if (complementos.length != 0 && complementos[0].equals(" CASA")) {            
            OpcionIdNombre complementoDir = new OpcionIdNombre();
            complementoDir.setIdParametro("CASA");
            complementoDir.setDescripcion("");
            com.add(complementoDir);           
        }

        return com;
    }

    /**
     * se evalua si el tipo de apartamento con el fin de reajustarel mismo
     *
     * @author Felipe Garcia
     * @param complemento
     * @param valorEntrada
     * @return String
     */
    public String tipoApto(String complemento, String valorEntrada) {
        String valor = valorEntrada;

        if (complemento.contains("CASA") && complemento.contains("PISO") && !"PISO".equals(valorEntrada)) {
            valor = "CASA + PISO";
            valorInicial = valor;
        } else if (complemento.contains("APARTAMENTO") && complemento.contains("PISO")) {
            valor = "PISO + APTO";
            valorInicial = valor;
        } else if (complemento.contains("INTERIOR") && complemento.contains("PISO")) {
            valor = "PISO + INTERIOR";
            valorInicial = valor;
        } else if (complemento.contains("LOCAL") && complemento.contains("PISO")) {
            valor = "PISO + LOCAL";
            valorInicial = valor;
        }

        return valor;
    }

    public boolean isComplemento(String dir) {

        String[] complementos = {"BLOQUE", "AGRUPACION", "ENTRADA", "ETAPA", "INTERIOR,MANZANA", "NIVEL", "SECTOR", "SUBETAPA", "SUPERMANZANA"};

        for (String c : complementos) {
            if (c.equalsIgnoreCase(dir)) {
                return true;
            }
        }
        return false;
    }

    private PreFichaGeorreferenciaMgl copyResposeGeoToPreFicha(AddressService responseGeo) {
        PreFichaGeorreferenciaMgl result = new PreFichaGeorreferenciaMgl();

        result.setActivityEconomic(responseGeo.getActivityeconomic());
        result.setAddress(responseGeo.getAddress());
        result.setAddressCode(responseGeo.getAddressCode());
        result.setAddressCodeFound(responseGeo.getAddressCodeFound());
        result.setAlternateAddress(responseGeo.getAlternateaddress());
        result.setAppletStandar(responseGeo.getAppletstandar());
        result.setCategory(responseGeo.getCategory());
        result.setChangeNumber(responseGeo.getChagenumber());
        result.setCodDaneMcpio(responseGeo.getCoddanemcpio());

        result.setDaneCity(responseGeo.getDaneCity());
        result.setDanePopArea(responseGeo.getDanePopulatedArea());
        result.setExist(responseGeo.getExist());
        result.setIdAddress(responseGeo.getIdaddress());

        result.setLevelLive(responseGeo.getLevellive());
        result.setLocality(responseGeo.getLocality());
        result.setNeighborhood(responseGeo.getNeighborhood());
        result.setNodoUno(responseGeo.getNodoUno());
        result.setNodoDos(responseGeo.getNodoDos());
        result.setNodoTres(responseGeo.getNodoTres());
        result.setQualifiers(responseGeo.getQualifiers());
        result.setSource(responseGeo.getSource());
        result.setState(responseGeo.getState());
        result.setTranslate(responseGeo.getTraslate());
        result.setZipCode(responseGeo.getZipCode());
        result.setZipCodeDistrict(responseGeo.getZipCodeDistrict());
        result.setZipCodeState(responseGeo.getZipCodeState());

        result.setLevelEconomic(responseGeo.getLeveleconomic());
        result.setCx(responseGeo.getCx());
        result.setCy(responseGeo.getCy());
        //se valida si el geo entrego informacion de estrato y coordenadas 
        //si no se marca el resultado como NO GEORREFERENCIADO
        if (responseGeo.getLeveleconomic() == null || responseGeo.getLeveleconomic().trim().isEmpty()) {
            result.setLevelEconomic(NO_GEORREFERENCIADO);
        }

        return result;

    }

    private boolean isMultiplePlaca(String placa) {
        String regExpression = "\\d{1,3}[a-zA-Z0-9]{0,2}\\s*-\\s*\\d{1,3}(/\\d{1,3})+";
        Pattern patter;
        patter = Pattern.compile(regExpression);
        return patter.matcher(placa).matches();
    }

    private List<String> getGruposFromDist(String distribucion) {
        List<String> gruposStr = null;

        String regExpGrupoApLcOf = "((AP|LC|IN)\\s((?!\\-)(\\-?\\w+)+)( ?))";
        Pattern patter = Pattern.compile(regExpGrupoApLcOf);

        if (patter.matcher(distribucion).find()) {
            gruposStr = new ArrayList<>();
            Matcher matcher = patter.matcher(distribucion);
            while (matcher.find()) {
                gruposStr.add(matcher.group());
            }
        }
        return gruposStr;
    }

    public void crearHHPPRR() {
        try {
            for (PreFichaXlsMgl p : fichaProcessXlsList) {

                if (p != null && p.getGeorreferenciaList() != null
                        && !p.getGeorreferenciaList().isEmpty()) {
                    if (p.getGeorreferenciaList().get(0).getId() == null) {
                        for (int i = 0; i < p.getGeorreferenciaList().size(); i++) {

                            if (p.getGeorreferenciaList().get(i).getFechaCreacion() == null || p.getGeorreferenciaList().get(i).getFechaEdicion() == null) {
                                LOGGER.error(p.getGeorreferenciaList().get(i).getId() + "||" + p.getGeorreferenciaList().get(i).getAddress());
                            }

                        }
                        preFichaMglFacadeLocal.savePreFichaGeoList(p.getGeorreferenciaList());
                    }
                }
            }

            preFichaMglFacadeLocal.savePreFichaDetalleHHPPList(hhppDetalleList);
            prefichaCrear.setMarcas(consolidarMarcasPreficha());
            prefichaCrear.setNota(nota);
            preFichaMglFacadeLocal.updatePreFicha(prefichaCrear);
            String msn = "La información ha sido cargada en la Base de Datos";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));

            isInfoForCreate = String.valueOf(true);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en crearHHPPRR al estandarizar las direcciones: " + e.getMessage() + " ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en crearHHPPRR al estandarizar las direcciones: " + e.getMessage() + " ", e, LOGGER);
        }
    }

    public String consolidarMarcasPreficha() {
        String marcas = "";
        try {
            if (!blackListCM1.equals("0")) {
                CmtBasicaMgl blackListCCMM1 = cmtBasicaMglFacadeLocal.findById(new BigDecimal(blackListCM1));
                if (blackListCCMM1 != null) {
                    marcas = marcas + blackListCCMM1.getCodigoBasica() + "-" + blackListCCMM1.getDescripcion().trim() + "|";
                } else {
                    marcas = marcas + "|";
                }
            } else {
                marcas = marcas + "|";
            }

            if (!blackListCM2.equals("0")) {
                CmtBasicaMgl blackListCCMM2 = cmtBasicaMglFacadeLocal.findById(new BigDecimal(blackListCM2));
                if (blackListCCMM2 != null) {
                    marcas = marcas + blackListCCMM2.getCodigoBasica() + "-" + blackListCCMM2.getDescripcion().trim() + "|";
                } else {
                    marcas = marcas + "|";
                }
            } else {
                marcas = marcas + "|";
            }

            if (!blackListHhpp1.equals("0")) {
                MarcasMgl marcaHHPP1 = marcasHhppMglFacadeLocal.findMarcasMglByCodigo(blackListHhpp1);
                if (marcaHHPP1 != null) {
                    marcas = marcas + marcaHHPP1.getMarCodigo() + "-" + marcaHHPP1.getMarNombre().trim() + "|";
                } else {
                    marcas = marcas + "|";
                }
            } else {
                marcas = marcas + "|";
            }

            if (!blackListHhpp2.equals("0")) {
                MarcasMgl marcaHhpp2 = marcasHhppMglFacadeLocal.findMarcasMglByCodigo(blackListHhpp2);
                if (marcaHhpp2 != null) {
                    marcas = marcas + marcaHhpp2.getMarCodigo() + "-" + marcaHhpp2.getMarNombre().trim() + "|";
                } else {
                    marcas = marcas + "|";
                }
            } else {
                marcas = marcas + "|";
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en consolidarMarcasPreficha al consolidar marcas: " + e.getMessage() + " ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en consolidarMarcasPreficha al consolidar marcas: " + e.getMessage() + " ", e, LOGGER);
        } finally {
            return marcas;
        }

    }

    /**
     * Metodo que permite modificar la informacion de las prefichas , validando
     * direccion mediante expresion regular
     *
     * @throws ApplicationException
     */
    public void modificarPrefichasNoProcesadas() {

        VerificadorExpresiones verificarDireccion = (VerificadorExpresiones) JSFUtil.getBean("verificadorExpresiones");
        String direccion = "";
        try {
            for (PreFichaXlsMgl edificioNoProcesado : edificiosVtXlsNoProcesados) {
                direccion = edificioNoProcesado.getViaPrincipal() + " " + edificioNoProcesado.getPlaca();
                PreFichaGeorreferenciaMgl preFichaGeoActual = preFichaMglFacadeLocal.findPreFichaGeorreferenciaByIdPrefichaXls(edificioNoProcesado.getId());
                PreFichaGeorreferenciaMgl preFichaGeorreferenciaMglNueva = edificioNoProcesado.getFichaGeorreferenciaMgl();
                String tipoDireccionCalle = verificarDireccion.validarDireccionNombreCalle(edificioNoProcesado.getViaPrincipal().trim());
                String placaAValidar = edificioNoProcesado.getPlaca();
                if (placaAValidar.contains("/")) {
                    placaAValidar = placaAValidar.substring(0, placaAValidar.indexOf("/"));
                }
                String tipoDireccionPlaca = verificarDireccion.validarDireccionPlaca(placaAValidar);
                edificioNoProcesado.setPestana(Constant.EDIFICIOSVT);
                if (edificioNoProcesado.getPlaca().contains("SC")) {
                    tipoDireccionPlaca = "";
                }

                boolean validarDistribucion = verificarDireccion.isValidCountDistribucion(edificioNoProcesado.getDistribucion(), edificioNoProcesado);
                boolean pisosInexistentes = true;
                if (edificioNoProcesado.getDistribucion() != null && !edificioNoProcesado.getDistribucion().isEmpty()) {
                    pisosInexistentes = VerificadorExpresiones.isDistribucionEsValida(edificioNoProcesado.getPisos().intValue(), edificioNoProcesado.getDistribucion());
                }

                boolean isCasa = edificioNoProcesado.getClasificacion().equals("CASASREDEXTERNA");

                if (!verificarDireccion.validaBarrio(edificioNoProcesado.getBarrio())) {
                    continue;
                }

                if ((isCasa && edificioNoProcesado.getTotalHHPP().intValue() > 4) || (isCasa && edificioNoProcesado.getPisos().intValue() > 4)) {
                    if (!validarDistribucion || !pisosInexistentes) {
                        continue;
                    }
                }

                if (!tipoDireccionCalle.isEmpty() && !tipoDireccionPlaca.isEmpty()
                        && (Constant.ACRONIMO_CK.equals(tipoDireccionCalle) && !Constant.ACRONIMO_BM.equals(tipoDireccionPlaca))) {

                    edificioNoProcesado.setRegistroValido(1);

                    //Empieza GeoReferenciacion
                    PreFichaMgl preFicha = preFichaMglFacadeLocal.getPreFichaById(edificioNoProcesado.getPrfId());
                    BigDecimal idGeoPol = preFicha.getNodoMgl().getGpoId();
                    GeograficoPoliticoMgl centro = geograficoPoliticoMglFacadeLocal.findById(idGeoPol);
                    GeograficoPoliticoMgl city = geograficoPoliticoMglFacadeLocal.findById(centro.getGeoGpoId());

                    String viaPrincipal = edificioNoProcesado.getViaPrincipal();
                    if (Constant.ACRONIMO_BM.equals(tipoDireccionPlaca)) {
                        edificioNoProcesado.setIdTipoDireccion(tipoDireccionPlaca);
                        viaPrincipal = edificioNoProcesado.getViaPrincipal().replace("BARRIO ", "");
                    } else {
                        edificioNoProcesado.setIdTipoDireccion(tipoDireccionCalle);
                    }
                    //Se establece tipo de direccion , se georeferencia y se guarda
                    preFichaGeorreferenciaMglNueva = obtenerDireccionTabuladaPreficha(edificioNoProcesado, viaPrincipal, edificioNoProcesado.getPlaca(), city, centro, false);
                    if (preFichaGeorreferenciaMglNueva != null && preFichaGeorreferenciaMglNueva.getLevelEconomic() != null && !preFichaGeorreferenciaMglNueva.getLevelEconomic().isEmpty() && preFichaGeorreferenciaMglNueva.getLevelEconomic().equals("NG")) {
                        preFichaGeorreferenciaMglNueva.setLevelEconomic("-1");
                    }
                    if (isMultiorigen() && edificioNoProcesado.getBarrio() != null && !edificioNoProcesado.getBarrio().isEmpty()) {
                        preFichaMglFacadeLocal.acualizarPrefichaXls(preFichaGeoActual, preFichaGeorreferenciaMglNueva, edificioNoProcesado, true);
                    } else if (!isMultiorigen()) {
                        preFichaMglFacadeLocal.acualizarPrefichaXls(preFichaGeoActual, preFichaGeorreferenciaMglNueva, edificioNoProcesado, true);
                    }

                }

                if (!tipoDireccionCalle.isEmpty() && !tipoDireccionPlaca.isEmpty() && tipoDireccionCalle.equalsIgnoreCase(tipoDireccionPlaca)) {
                    edificioNoProcesado.setRegistroValido(1);
                    edificioNoProcesado.setIdTipoDireccion(tipoDireccionCalle);
                    preFichaMglFacadeLocal.acualizarPrefichaXls(preFichaGeoActual, preFichaGeorreferenciaMglNueva, edificioNoProcesado, true);
                }

                if (!tipoDireccionCalle.isEmpty() && !tipoDireccionPlaca.isEmpty() && (Constant.ACRONIMO_IT.equals(tipoDireccionCalle) && (Constant.ACRONIMO_IT.equals(tipoDireccionPlaca) || (Constant.ACRONIMO_BM.equals(tipoDireccionPlaca) && edificioNoProcesado.getPlaca().replace(" ", "").trim().length() <= 10)))) {
                    edificioNoProcesado.setRegistroValido(1);
                    edificioNoProcesado.setIdTipoDireccion(tipoDireccionCalle);
                    preFichaMglFacadeLocal.acualizarPrefichaXls(preFichaGeoActual, preFichaGeorreferenciaMglNueva, edificioNoProcesado, true);
                }
            }

            for (PreFichaXlsMgl casasRedNoProcesado : casasRedExternaXlsNoProcesados) {
                direccion = casasRedNoProcesado.getViaPrincipal() + " " + casasRedNoProcesado.getPlaca();
                PreFichaGeorreferenciaMgl preFichaGeoActual = preFichaMglFacadeLocal.findPreFichaGeorreferenciaByIdPrefichaXls(casasRedNoProcesado.getId());
                PreFichaGeorreferenciaMgl preFichaGeorreferenciaMglNueva = casasRedNoProcesado.getFichaGeorreferenciaMgl();
                String tipoDireccionCalle = verificarDireccion.validarDireccionNombreCalle(casasRedNoProcesado.getViaPrincipal().trim());
                String tipoDireccionPlaca = verificarDireccion.validarDireccionPlaca(casasRedNoProcesado.getPlaca().trim());
                casasRedNoProcesado.setPestana(Constant.CASAS_RED_EXTERNA);
                if (casasRedNoProcesado.getPlaca().contains("SC")) {
                    if (casasRedNoProcesado.getPlaca().contains("SC") || casasRedNoProcesado.getPlaca().contains("ST") || casasRedNoProcesado.getPlaca().contains("SECTOR")) {
                        String s = casasRedNoProcesado.getPlaca().substring(0, casasRedNoProcesado.getPlaca().indexOf("-"));
                        casasRedNoProcesado.setPlaca(casasRedNoProcesado.getPlaca().replace(s, "0"));
                        casasRedNoProcesado.setViaPrincipal(casasRedNoProcesado.getViaPrincipal() + " " + s);
                    }
                    if (casasRedNoProcesado.getPlaca().contains("SC")) {
                         tipoDireccionPlaca = "";
                    }
                }
                boolean validarDistribucion = verificarDireccion.isValidCountDistribucion(casasRedNoProcesado.getDistribucion(), casasRedNoProcesado);
                boolean pisosInexistentes = true;
                if (casasRedNoProcesado.getDistribucion() != null && !casasRedNoProcesado.getDistribucion().isEmpty()) {
                    pisosInexistentes = VerificadorExpresiones.isDistribucionEsValida(casasRedNoProcesado.getPisos().intValue(), casasRedNoProcesado.getDistribucion());
                }
                boolean isCasa = casasRedNoProcesado.getClasificacion().equals("CASASREDEXTERNA");

                if (!verificarDireccion.validaBarrio(casasRedNoProcesado.getBarrio())) {
                    continue;
                }

                if ((isCasa && casasRedNoProcesado.getTotalHHPP().intValue() > 4) || (isCasa && casasRedNoProcesado.getPisos().intValue() > 4)) {
                    if (!validarDistribucion || !pisosInexistentes) {
                        casasRedNoProcesado.setObservacionFicha("Distrubucion no valida");
                        continue;
                    }
                }

                if (!tipoDireccionCalle.isEmpty() && !tipoDireccionPlaca.isEmpty()
                        && (Constant.ACRONIMO_CK.equals(tipoDireccionCalle) && !Constant.ACRONIMO_BM.equals(tipoDireccionPlaca))) {

                    casasRedNoProcesado.setRegistroValido(1);

                    //Empieza GeoReferenciacion
                    PreFichaMgl preFicha = preFichaMglFacadeLocal.getPreFichaById(casasRedNoProcesado.getPrfId());
                    BigDecimal idGeoPol = preFicha.getNodoMgl().getGpoId();
                    GeograficoPoliticoMgl centro = geograficoPoliticoMglFacadeLocal.findById(idGeoPol);
                    GeograficoPoliticoMgl city = geograficoPoliticoMglFacadeLocal.findById(centro.getGeoGpoId());
                    GeograficoPoliticoMgl state = geograficoPoliticoMglFacadeLocal.findById(city.getGeoGpoId());
                    String viaPrincipal = casasRedNoProcesado.getViaPrincipal();
                    if (Constant.ACRONIMO_BM.equals(tipoDireccionPlaca)) {
                        casasRedNoProcesado.setIdTipoDireccion(tipoDireccionPlaca);
                        viaPrincipal = casasRedNoProcesado.getViaPrincipal().replace("BARRIO ", "");
                    } else {
                        casasRedNoProcesado.setIdTipoDireccion(tipoDireccionCalle);
                    }
                    //Se establece tipo de direccion , se georeferencia y se guarda
                    preFichaGeorreferenciaMglNueva = obtenerDireccionTabuladaPreficha(casasRedNoProcesado, viaPrincipal, casasRedNoProcesado.getPlaca(), city, centro, false);
                    if (preFichaGeorreferenciaMglNueva != null && preFichaGeorreferenciaMglNueva.getLevelEconomic() != null && !preFichaGeorreferenciaMglNueva.getLevelEconomic().isEmpty() && preFichaGeorreferenciaMglNueva.getLevelEconomic().equals("NG")) {
                        preFichaGeorreferenciaMglNueva.setLevelEconomic("-1");
                    }
                    if (isMultiorigen() && casasRedNoProcesado.getBarrio() != null && !casasRedNoProcesado.getBarrio().isEmpty()) {
                        preFichaMglFacadeLocal.acualizarPrefichaXls(preFichaGeoActual, preFichaGeorreferenciaMglNueva, casasRedNoProcesado, true);
                    } else if (!isMultiorigen()) {
                        preFichaMglFacadeLocal.acualizarPrefichaXls(preFichaGeoActual, preFichaGeorreferenciaMglNueva, casasRedNoProcesado, true);
                    }
                }

                if (!tipoDireccionCalle.isEmpty() && !tipoDireccionPlaca.isEmpty() 
                        && tipoDireccionCalle.equalsIgnoreCase(tipoDireccionPlaca)) {
                    casasRedNoProcesado.setRegistroValido(1);
                    casasRedNoProcesado.setIdTipoDireccion(tipoDireccionCalle);
                    preFichaMglFacadeLocal.acualizarPrefichaXls(preFichaGeoActual, preFichaGeorreferenciaMglNueva, casasRedNoProcesado, true);
                }

                if (!tipoDireccionCalle.isEmpty() && !tipoDireccionPlaca.isEmpty() && (Constant.ACRONIMO_IT.equals(tipoDireccionCalle)
                        && (Constant.ACRONIMO_IT.equals(tipoDireccionPlaca) || (Constant.ACRONIMO_BM.equals(tipoDireccionPlaca) && casasRedNoProcesado.getPlaca().replace(" ", "").trim().length() <= 10)))) {
                    casasRedNoProcesado.setRegistroValido(1);
                    casasRedNoProcesado.setIdTipoDireccion(tipoDireccionCalle);
                    preFichaMglFacadeLocal.acualizarPrefichaXls(preFichaGeoActual, preFichaGeorreferenciaMglNueva, casasRedNoProcesado, true);
                }
            }

            for (PreFichaXlsMgl conjuntoCasaNoProcesado : conjCasasVtXlsNoProcesados) {
                direccion = conjuntoCasaNoProcesado.getViaPrincipal() + " " + conjuntoCasaNoProcesado.getPlaca();
                PreFichaGeorreferenciaMgl preFichaGeoActual = preFichaMglFacadeLocal.findPreFichaGeorreferenciaByIdPrefichaXls(conjuntoCasaNoProcesado.getId());
                PreFichaGeorreferenciaMgl preFichaGeorreferenciaMglNueva = conjuntoCasaNoProcesado.getFichaGeorreferenciaMgl();
                String tipoDireccionCalle = verificarDireccion.validarDireccionNombreCalle(conjuntoCasaNoProcesado.getViaPrincipal().trim());
                String tipoDireccionPlaca = verificarDireccion.validarDireccionPlaca(conjuntoCasaNoProcesado.getPlaca().trim());
                conjuntoCasaNoProcesado.setPestana(Constant.CONJUNTOCASASVT);
                if (conjuntoCasaNoProcesado.getPlaca().contains("SC")) {
                    tipoDireccionPlaca = "";
                }
                boolean validarDistribucion = verificarDireccion.isValidCountDistribucion(conjuntoCasaNoProcesado.getDistribucion(), conjuntoCasaNoProcesado);
                boolean isCasa = conjuntoCasaNoProcesado.getClasificacion().equals("CASASREDEXTERNA");

                if (!verificarDireccion.validaBarrio(conjuntoCasaNoProcesado.getBarrio())) {
                    continue;
                }

                if ((isCasa && conjuntoCasaNoProcesado.getTotalHHPP().intValue() > 4) || (isCasa && conjuntoCasaNoProcesado.getPisos().intValue() > 4)) {
                    if (!validarDistribucion) {
                        continue;
                    }
                }
                if (!tipoDireccionCalle.isEmpty() && !tipoDireccionPlaca.isEmpty()
                        && (Constant.ACRONIMO_CK.equals(tipoDireccionCalle) && !Constant.ACRONIMO_BM.equals(tipoDireccionPlaca))) {

                    conjuntoCasaNoProcesado.setRegistroValido(1);

                    //Empieza GeoReferenciacion
                    PreFichaMgl preFicha = preFichaMglFacadeLocal.getPreFichaById(conjuntoCasaNoProcesado.getPrfId());
                    BigDecimal idGeoPol = preFicha.getNodoMgl().getGpoId();
                    GeograficoPoliticoMgl centro = geograficoPoliticoMglFacadeLocal.findById(idGeoPol);
                    GeograficoPoliticoMgl city = geograficoPoliticoMglFacadeLocal.findById(centro.getGeoGpoId());
                    GeograficoPoliticoMgl state = geograficoPoliticoMglFacadeLocal.findById(city.getGeoGpoId());
                    String viaPrincipal = conjuntoCasaNoProcesado.getViaPrincipal();
                    if (Constant.ACRONIMO_BM.equals(tipoDireccionPlaca)) {
                        conjuntoCasaNoProcesado.setIdTipoDireccion(tipoDireccionPlaca);
                        viaPrincipal = conjuntoCasaNoProcesado.getViaPrincipal().replace("BARRIO ", "");
                    } else {
                        conjuntoCasaNoProcesado.setIdTipoDireccion(tipoDireccionCalle);
                    }
                    //Se establece tipo de direccion , se georeferencia y se guarda
                    preFichaGeorreferenciaMglNueva = obtenerDireccionTabuladaPreficha(conjuntoCasaNoProcesado, viaPrincipal, conjuntoCasaNoProcesado.getPlaca(), city, centro, false);
                    if (preFichaGeorreferenciaMglNueva != null && preFichaGeorreferenciaMglNueva.getLevelEconomic() != null && !preFichaGeorreferenciaMglNueva.getLevelEconomic().isEmpty() && preFichaGeorreferenciaMglNueva.getLevelEconomic().equals("NG")) {
                        preFichaGeorreferenciaMglNueva.setLevelEconomic("-1");
                    }
                    if (isMultiorigen() && conjuntoCasaNoProcesado.getBarrio() != null && !conjuntoCasaNoProcesado.getBarrio().isEmpty()) {
                        preFichaMglFacadeLocal.acualizarPrefichaXls(preFichaGeoActual, preFichaGeorreferenciaMglNueva, conjuntoCasaNoProcesado, true);
                    } else if (!isMultiorigen()) {
                        preFichaMglFacadeLocal.acualizarPrefichaXls(preFichaGeoActual, preFichaGeorreferenciaMglNueva, conjuntoCasaNoProcesado, true);
                    }
                }

                if (!tipoDireccionCalle.isEmpty() && !tipoDireccionPlaca.isEmpty() && tipoDireccionCalle.equalsIgnoreCase(tipoDireccionPlaca)) {
                    conjuntoCasaNoProcesado.setRegistroValido(1);
                    conjuntoCasaNoProcesado.setIdTipoDireccion(tipoDireccionCalle);
                    preFichaMglFacadeLocal.acualizarPrefichaXls(preFichaGeoActual, preFichaGeorreferenciaMglNueva, conjuntoCasaNoProcesado, true);
                }
                if (!tipoDireccionCalle.isEmpty() && !tipoDireccionPlaca.isEmpty() && (Constant.ACRONIMO_IT.equals(tipoDireccionCalle) && (Constant.ACRONIMO_IT.equals(tipoDireccionPlaca) || (Constant.ACRONIMO_BM.equals(tipoDireccionPlaca) && conjuntoCasaNoProcesado.getPlaca().trim().length() <= 10)))) {
                    conjuntoCasaNoProcesado.setRegistroValido(1);
                    conjuntoCasaNoProcesado.setIdTipoDireccion(tipoDireccionCalle);
                    preFichaMglFacadeLocal.acualizarPrefichaXls(preFichaGeoActual, preFichaGeorreferenciaMglNueva, conjuntoCasaNoProcesado, true);
                }
            }
            cargarDetalleFicha(prefichaCrear);
            String msn = "Registros no procesados actualizados de manera correcta";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en modificarPrefichasNoProcesadas. No fue posible guardar las prefichas modificadas, Verifique que las direcciones cumplan con una estructura valida , o que la direccion exista en la ciudad seleccionada: " + direccion + " ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en modificarPrefichasNoProcesadas. No fue posible guardar las prefichas modificadas, Verifique que las direcciones cumplan con una estructura valida , o que la direccion exista en la ciudad seleccionada: " + direccion + " ", e, LOGGER);
        }

    }

    /**
     * Metodo encargado de agregar la nota a el HHPP en MGL y RR
     *
     * @param hhpp Home passed al que se le agregaran las notas
     * @param notas Nota a agregar al HHPP
     * @return Observaciones del proceso
     */
    private String procesarNotas(HhppMgl hhpp, String notas) {
        String observaciones;
        try {
            if (hhpp != null && notas != null) {
                if (!notas.isEmpty()) {
                    NotasAdicionalesMgl notasMgl = new NotasAdicionalesMgl();
                    notasMgl.setHhppId(hhpp.getHhpId() + "");
                    notasMgl.setEstadoRegistro(1);
                    notasMgl.setFechaCreacion(new Date());
                    notasMgl.setNota(notas);
                    notasAdicionalesMglFacadeLocal.create(notasMgl);
                    DireccionRRManager direccionRrManager = new DireccionRRManager(true);
                    List<NotasAdicionalesMgl> nota1 = new ArrayList<>();
                    nota1.add(notasMgl);
                    direccionRrManager.agregarNotasHhppRR(hhpp, nota1);

                    //Persistir nota en MGL
                    CmtBasicaMgl tipoNota = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_NOTA_HHHPP);
                    CmtNotasHhppVtMgl cmtNotasHhppVtMgl = new CmtNotasHhppVtMgl();
                    cmtNotasHhppVtMgl.setNota(notas);
                    cmtNotasHhppVtMgl.setDescripcion(notas);
                    cmtNotasHhppVtMgl.setTipoNotaObj(tipoNota);
                    cmtNotasHhppVtMgl.setHhppId(hhpp);

                    cmtNotasHhppVtMgl = cmtNotasHhppVtMglFacadeLocal.create(cmtNotasHhppVtMgl, usuarioVT, perfilVT);

                    CmtNotasHhppDetalleVtMgl cmtNotasHhppDetalleVtMgl = new CmtNotasHhppDetalleVtMgl();
                    cmtNotasHhppDetalleVtMgl.setNotaHhpp(cmtNotasHhppVtMgl);
                    cmtNotasHhppDetalleVtMgl.setNota(notas);
                    cmtNotasHhppDetalleVtMglFacadeLocal.create(cmtNotasHhppDetalleVtMgl, usuarioVT, perfilVT);
                    observaciones = "Se creo la nota";
                } else {
                    observaciones = "No hay notas";
                }
            } else {
                observaciones = "No hay notas";
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en procesarNotas. EX. " + e.getMessage(), e);
            observaciones = e.getMessage();
        } catch (Exception e) {
            LOGGER.error("Error en procesarNotas. EX. " + e.getMessage(), e);
            observaciones = e.getMessage();
        }
        return observaciones;
    }

    private String getDirToGeorreferenciar(PreFichaXlsMgl pft) {
        String calle1 = pft.getViaPrincipal();
        String placa1;
        //si tenemos multiplaca tomamos la primera placa
        if (isMultiplePlaca(pft.getPlaca())) {
            placa1 = pft.getPlaca().substring(0, pft.getPlaca().indexOf("-") + 1) + pft.getPlaca().substring(pft.getPlaca().indexOf("-") + 1).trim().split("/")[0];
        } else {
            placa1 = pft.getPlaca();
        }
        return calle1 + " # " + placa1;
    }

    public void crearCm(PreFichaXlsMgl p) throws ApplicationException {

        BigDecimal idGeoPol = prefichaCrear.getNodoMgl().getGpoId();
        GeograficoPoliticoMgl centroPoblado = geograficoPoliticoMglFacadeLocal.findById(idGeoPol);
        GeograficoPoliticoMgl ciudad = geograficoPoliticoMglFacadeLocal.findById(centroPoblado.getGeoGpoId());
        GeograficoPoliticoMgl departamento = geograficoPoliticoMglFacadeLocal.findById(ciudad.getGeoGpoId());
        CmtBasicaMgl tecnologia = prefichaCrear.getNodoMgl().getNodTipo();
        CmtComunidadRr comunidadRr = cmtComunidadRrFacadeLocal.findByCodigoRR(centroPoblado.getGpoCodigo());

        LOGGER.error("Ciudad: " + ciudad.getGeoGpoId()
                + " Departamento: " + departamento.getGeoGpoId()
                + " Tecnologia :" + tecnologia.getBasicaId()
                + " Via principal: " + p.getViaPrincipal()
                + " Placa: " + p.getPlaca()
                + " Total HHPP: " + p.getTotalHHPP());

        CmtCuentaMatrizMgl cm = new CmtCuentaMatrizMgl();
        String nombreCuenta = "";
        if (p.getNombreConj() != null) {
            nombreCuenta = p.getNombreConj();
        } else {
            nombreCuenta = p.getViaPrincipal() + " " + p.getPlaca();
        }
        cm.setNombreCuenta(nombreCuenta);
        cm.setNumeroCuenta(new BigDecimal("0"));
        cm.setFechaCreacion(new Date());
        cm.setUsuarioCreacion(httpSession.getAttribute("loginUserSecurity").toString());
        cm.setFechaEdicion(new Date());
        cm.setUsuarioEdicion(httpSession.getAttribute("loginUserSecurity").toString());
        cm.setComunidad(centroPoblado.getGpoCodigo());
        cm.setDivision(comunidadRr.getRegionalRr().getCodigoRr());
        cm.setDepartamento(departamento);
        cm.setMunicipio(ciudad);
        cm.setCentroPoblado(centroPoblado);
        cm.setEstadoRegistro(1);
        cm.setPerfilCreacion(perfilVT);
        cm.setPerfilEdicion(perfilVT);
        cmtCuentaMatrizMglFacadeLocal.create(cm);

        CmtSubEdificioMgl se = new CmtSubEdificioMgl();
        se.setCuentaMatrizObj(cm);
        se.setNombreEntSubedificio("");
        CmtBasicaMgl estadoSubEdificio = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SIN_VISITA_TECNICA);
        se.setEstadoSubEdificioObj(estadoSubEdificio);
        se.setNodoObj(prefichaCrear.getNodoMgl());
        CmtBasicaMgl tipoEdificio = cmtBasicaMglFacadeLocal.findById(new BigDecimal("1"));
        se.setTipoEdificioObj(tipoEdificio);
        se.setDireccion(p.getViaPrincipal() + " " + p.getPlaca());
        se.setCodigoRr("0000");
        se.setVisitaTecnica("N");
        se.setCierre("N");
        se.setFechaCreacion(new Date());
        se.setUsuarioCreacion(httpSession.getAttribute("loginUserSecurity").toString());
        se.setPerfilCreacion(perfilVT);
        se.setFechaEdicion(new Date());
        se.setUsuarioEdicion(httpSession.getAttribute("loginUserSecurity").toString());
        se.setPerfilEdicion(perfilVT);
        se.setEstadoRegistro(1);
        se.setConexionCorriente("NA");
        se.setPlanos("NA");
        se.setReDiseno("NA");
        cmtSubEdificioMglFacadeLocal.create(se);

    }

    /**
     * Instancia un objeto de tipo CmtCuentaMatrizMgl con los datos de la
     * preficha
     *
     * @param p preficha que contiene la informacion para instanciar la cuenta
     * matriz
     * @param centroPoblado
     * @param ciudad
     * @param departamento
     * @param comunidadRr
     * @param tecnologia
     * @return
     */
    public CreacionCcmmDto mapearCuentaMatriz(PreFichaXlsMgl p, GeograficoPoliticoMgl centroPoblado,
            GeograficoPoliticoMgl ciudad, GeograficoPoliticoMgl departamento,
            CmtBasicaMgl tecnologia, CmtComunidadRr comunidadRr, DireccionRREntity direccionRRentity) {
        try {

            //Apartir del nodo obtenemos los datos de ubicacion
            CreacionCcmmDto cm = new CreacionCcmmDto();
            cm.setNumeroCuenta(new BigDecimal("0"));
            cm.setFechaCreacion(new Date());
            cm.setUsuarioCreacion(usuarioVT);
            cm.setFechaEdicion(new Date());
            cm.setUsuarioEdicion(usuarioVT);
            cm.setComunidad(centroPoblado.getGpoCodigo());
            cm.setDivision(comunidadRr.getRegionalRr().getCodigoRr());
            cm.setDepartamento(departamento);
            cm.setMunicipio(ciudad);
            cm.setCentroPoblado(centroPoblado);
            cm.setEstadoRegistro(1);
            cm.setPerfilCreacion(perfilVT);
            cm.setPerfilEdicion(perfilVT);
            
            List<CmtDireccionSolicitudMgl> listaSolicitudDirecciones = new ArrayList<>();
            CmtDireccionSolicitudMgl cmtDireccionSolictudMgl = new CmtDireccionSolicitudMgl();
            cmtDireccionSolictudMgl.mapearCamposDrDireccion(p.getDrDireccionList().get(0));
            cmtDireccionSolictudMgl.setFechaCreacion(new Date());
            cmtDireccionSolictudMgl.setUsuarioCreacion(usuarioVT);
            cmtDireccionSolictudMgl.setPerfilCreacion(perfilVT);
            cmtDireccionSolictudMgl.setCalleRr(direccionRRentity.getCalle().toUpperCase());
            cmtDireccionSolictudMgl.setUnidadRr(direccionRRentity.getNumeroUnidad().toUpperCase());

            listaSolicitudDirecciones.add(cmtDireccionSolictudMgl);
            cm.setListCmtDireccionesMgl(listaSolicitudDirecciones);

            
            CmtSolicitudSubEdificioMgl solicitudSubEdificioMgl = new CmtSolicitudSubEdificioMgl();
            solicitudSubEdificioMgl.setCompaniaAdministracionObj(new CmtCompaniaMgl());
            solicitudSubEdificioMgl.setCompaniaConstructoraObj(new CmtCompaniaMgl());
            solicitudSubEdificioMgl.setCompaniaAscensorObj(new CmtCompaniaMgl());
            if (solicitudSubEdificioMgl.getCompaniaAdministracionObj().getCompaniaId() == null) {
                solicitudSubEdificioMgl.setCompaniaAdministracionObj(null);
            }
            if (solicitudSubEdificioMgl.getCompaniaConstructoraObj().getCompaniaId() == null) {
                solicitudSubEdificioMgl.setCompaniaConstructoraObj(null);
            }
            if (solicitudSubEdificioMgl.getCompaniaAscensorObj().getCompaniaId() == null) {
                solicitudSubEdificioMgl.setCompaniaAscensorObj(null);
            }
            if (solicitudSubEdificioMgl.getNombreSubedificio() == null
                    || solicitudSubEdificioMgl.getNombreSubedificio().isEmpty()) {
                solicitudSubEdificioMgl.
                        setNombreSubedificio("N.N " + direccionRRentity.getCalle().toUpperCase() + " " + direccionRRentity.getNumeroUnidad().toUpperCase());
            }
            
            solicitudSubEdificioMgl.setEstadoRegistro(1);
            solicitudSubEdificioMgl.setFechaCreacion(new Date());
            solicitudSubEdificioMgl.setUsuarioCreacion(usuarioVT);
            solicitudSubEdificioMgl.setPerfilCreacion(perfilVT);
            solicitudSubEdificioMgl.setTipoEdificioObj(cmtBasicaMglFacadeLocal.findById(new BigDecimal("1")));
            if (p.getFichaGeorreferenciaMgl() != null && p.getFichaGeorreferenciaMgl().getLevelEconomic() != null && !p.getFichaGeorreferenciaMgl().getLevelEconomic().isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaEstrato = tipoBasicaFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_ESTRATO);
                solicitudSubEdificioMgl.setEstratoSubEdificioObj(cmtBasicaMglFacadeLocal.findByBasicaCode(p.getFichaGeorreferenciaMgl().getLevelEconomic(), tipoBasicaEstrato.getTipoBasicaId()));
            } else {
                CmtTipoBasicaMgl tipoBasicaEstrato = tipoBasicaFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_ESTRATO);
                solicitudSubEdificioMgl.setEstratoSubEdificioObj(cmtBasicaMglFacadeLocal.findByBasicaCode("-1", tipoBasicaEstrato.getTipoBasicaId()));
            }
            solicitudSubEdificioMgl.setNombreSubedificio(cm.getNombreCuenta());
            solicitudSubEdificioMgl.setUnidadesVt(p.getTotalHHPP().intValue());
            solicitudSubEdificioMgl.setUnidades(p.getTotalHHPP().intValue());
            cm.setListCmtSolicitudSubEdificioMgl(new ArrayList<>());
            cm.getListCmtSolicitudSubEdificioMgl().add(solicitudSubEdificioMgl); 
 
            return cm;

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en mapearCuentaMatriz: " + e.getMessage() + "", e, LOGGER);
        }
        return null;
    }

    private HashMap<PreFichaXlsMgl, CmtCuentaMatrizMgl> mapaCCMMCreadas;
    private List<AuxExportFichas> mapaHHPPCreadas;
    private List<AuxExportFichas> mapaErroresYDuplicados;

    /**
     * Metodo encargado de crear un CCMM en MGL y RR usando el metodo que
     * gestiona las solicitudes
     *
     * @param p preficha con los datos de la cuenta matriz a ser creada
     * @param ciudad
     * @param comunidadRr
     * @param departamento
     * @param tecnologia
     * @param basicaAcometidaDefault
     * @return CmtCuentaMatrizMgl
     */
    public CmtCuentaMatrizMgl generarCCMMDesdeSolicitud(PreFichaXlsMgl p, GeograficoPoliticoMgl centroPoblado, GeograficoPoliticoMgl ciudad,
            GeograficoPoliticoMgl departamento, CmtComunidadRr comunidadRr, CmtBasicaMgl tecnologia, 
            CmtBasicaMgl basicaAcometidaDefault) {
        String observaciones;
        try {

            if (p.getPlaca().contains("SC") || p.getPlaca().contains("ST") || p.getPlaca().contains("SECTOR")) {
                String s = p.getPlaca().substring(0, p.getPlaca().indexOf("-"));
                p.setPlaca(p.getPlaca().replace(s, "0"));
                p.setViaPrincipal(p.getViaPrincipal() + " " + s);
            }
            
          //Se convierte la direccion a un objeto de tipo DireccionRREtitiy
            DireccionRREntity direccionRRentity = cmtValidadorDireccionesFacadeLocal.
                    convertirDrDireccionARR(p.getDrDireccionList().get(0),
                    ciudad.getGpoMultiorigen());
            
            
            //Construimos la cuenta matriz a partir de los datos de la ficha
            CreacionCcmmDto cm = mapearCuentaMatriz(p, centroPoblado,
                    ciudad, departamento, tecnologia, comunidadRr,direccionRRentity);

            //Valida si viene nombre para la cuenta matriz si no coloca NN mas la direccion formato RR como MGL
            String nombreCuenta;
            if (p.getNombreConj() != null && !p.getNombreConj().isEmpty()) {
                nombreCuenta = p.getNombreConj();
            } else {
                nombreCuenta = "N.N " + direccionRRentity.getCalle().toUpperCase() + " " + direccionRRentity.getNumeroUnidad().toUpperCase();
            }
            cm.setOrigenSolicitud(basicaAcometidaDefault);
            cm.setNombreCuenta(nombreCuenta);
            if(direccionRRentity.getCalle()!= null && !direccionRRentity.getCalle().isEmpty() 
                    && direccionRRentity.getNumeroUnidad()!= null && !direccionRRentity.getNumeroUnidad().isEmpty()){
                String nombreEdifGen = "N.N " + direccionRRentity.getCalle().toUpperCase() + " " + direccionRRentity.getNumeroUnidad().toUpperCase();
                cm.setNombreEdificioGeneral(nombreEdifGen);
            }
            
            
            
            
            if (p.getClasificacion().equals("CASASREDEXTERNA") && p.getPisos().intValue() >= 4) {
                cm.setCasaaEdificio(true);
            }
            
            AddressService addressServiceGestion = p.getResponseGeo();
            Map<CmtBasicaMgl, NodoMgl> datosGestion = new HashMap<>();
            prefichaCrear.getNodoMgl().getNodTipo().setNodoTecnologia(true);
            datosGestion.put(prefichaCrear.getNodoMgl().getNodTipo(), prefichaCrear.getNodoMgl());
            
            CmtCuentaMatrizMgl cuenta  = cuentaMatrizMglFacadeLocal.
                    creacionCCMMOptima(cm, addressServiceGestion, datosGestion,
                            prefichaCrear.getNodoMgl(),usuarioVT, perfilVT, true);
            
            observaciones = "Se creo la cuenta matriz en RR " + cuenta.getNumeroCuenta() + "  y " + cuenta.getCuentaMatrizId() + " en MGL";
            observaciones += "| " + agregarNotas(cuenta);
            observaciones += "| " + ageregarBlackList(cuenta);

            mapaCCMMCreadas.put(p, cuenta);

            if (p.getPlaca().contains("/")) {
                crearMultiplacaCCMM(p, cuenta);
            }

            if (mapObservaciones.containsKey(p.getViaPrincipal() + " " + p.getPlaca())) {
                mapObservaciones.put(p.getViaPrincipal() + " " + p.getPlaca() + " ", observaciones);
            } else {
                mapObservaciones.put(p.getViaPrincipal() + " " + p.getPlaca(), observaciones);
            }

            p.setObservacionFicha(observaciones);

            return cuenta;

        } catch (ApplicationException | ExceptionDB e) {
            LOGGER.error("Error en generarCCMMDesdeSolicitud. " + e.getMessage(), e);
            if (mapObservaciones.containsKey(p.getViaPrincipal() + " " + p.getPlaca())) {
                mapObservaciones.put(p.getViaPrincipal() + " " + p.getPlaca() + " ", e.getMessage());
            } else {
                mapObservaciones.put(p.getViaPrincipal() + " " + p.getPlaca(), e.getMessage());
            }
            p.setObservacionFicha(e.getMessage());
            mapaErroresYDuplicados.add(new AuxExportFichas(AuxExportFichas.TIPO_CCMM, p, ""));
            return null;
        }

    }

    /**
     * Metodo encargado de agregar los blackList a la Cuenta Matriz en MGL y RR
     *
     * @param cuenta
     * @return String con las observaciones del proceso.
     */
    public String ageregarBlackList(CmtCuentaMatrizMgl cuenta) {
        String observaciones = "";
        try {
            CmtBlackListMgl cmtBlackListMgl = new CmtBlackListMgl();
            CmtBlackListMgl cmtBlackListMgl2 = new CmtBlackListMgl();
            cmtBlackListMgl.setSubEdificioObj(cuenta.getSubEdificioGeneral());
            cmtBlackListMgl.setEstadoRegistro(1);
            cmtBlackListMgl.setDetalle("PREFICHA");
            cmtBlackListMgl.setActivado("Y");
            cmtBlackListMgl.setSubEdificioObj(cuenta.getSubEdificioGeneral());

            cmtBlackListMgl2.setSubEdificioObj(cuenta.getSubEdificioGeneral());
            cmtBlackListMgl2.setEstadoRegistro(1);
            cmtBlackListMgl2.setDetalle("PREFICHA");
            cmtBlackListMgl2.setActivado("Y");
            cmtBlackListMgl2.setSubEdificioObj(cuenta.getSubEdificioGeneral());

            if (blackListCM1 != null && !blackListCM1.isEmpty() && !blackListCM1.equals("0")) {
                CmtBasicaMgl blackList1 = cmtBasicaMglFacadeLocal.findById(new BigDecimal(blackListCM1));
                cmtBlackListMgl.setBlackListObj(blackList1);
                cmtBlackListMglFacadeLocal.create(cmtBlackListMgl, usuarioVT, perfilVT);
                observaciones += "Se creo el  blacklist: " + blackList1.getCodigoBasica();

            }
            if (blackListCM2 != null && !blackListCM2.isEmpty() && !blackListCM2.equals("0")) {
                CmtBasicaMgl blackList2 = cmtBasicaMglFacadeLocal.findById(new BigDecimal(blackListCM2));
                cmtBlackListMgl2.setBlackListObj(blackList2);
                cmtBlackListMglFacadeLocal.create(cmtBlackListMgl2, usuarioVT, perfilVT);
                observaciones += "| Se creo el  blacklist: " + blackList2.getCodigoBasica();
            }

        } catch (ApplicationException e) {
            LOGGER.error("Error en ageregarBlackList. " + e.getMessage(), e);
            observaciones = e.getMessage();
        } catch (Exception e) {
            LOGGER.error("Error en ageregarBlackList. " + e.getMessage(), e);
            observaciones = e.getMessage();
        }
        return observaciones;

    }

    /**
     * Metodo encargado de agregar las notas a la Cuenta Matriz en MGL y RR
     *
     * @param solicitudCm
     * @return String con las observaciones del proceso
     */
    public String agregarNotas(CmtCuentaMatrizMgl cuenta) {
        String observaciones;
        try {
            if (nota != null && !nota.isEmpty()) {
                CmtNotasMgl notaObj = new CmtNotasMgl();
                CmtBasicaMgl tipoNota = new CmtBasicaMgl();
                tipoNota.setBasicaId(new BigDecimal(339));//OBSERVACIONES
                notaObj.setTipoNotaObj(tipoNota);
                notaObj.setNota(nota);
                notaObj.setSubEdificioObj(cuenta.getSubEdificioGeneral());
                if (notaObj.getSubEdificioObj() != null) {
                    notaObj.setDescripcion(notaObj.getSubEdificioObj().getNombreSubedificio() != null ? notaObj.getSubEdificioObj().getNombreSubedificio() : "ND");
                } else {
                    notaObj.setDescripcion("ND");
                }

                notaObj.setDetalle("CREACION DE NOTA");
                cmtNotasMglFacadeLocal.create(notaObj, httpSession.getAttribute("loginUserSecurity").toString(), perfilVT);
                observaciones = "|Se creo la nota correctamente|";
            } else {
                observaciones = "No hay notas";
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en agregarNotas." + e.getMessage(), e);
            observaciones = e.getMessage();

        } catch (Exception e) {
            LOGGER.error("Error en agregarNotas." + e.getMessage(), e);
            observaciones = e.getMessage();

        }
        return observaciones;
    }

    /**
     * Crea las cuentas matrices y los home passed segun la logica definida (Se
     * crean las CCMM de los objetos contenidos en las listas edificiosVT y
     * ConjuntoCasasVT. Si dentro de casasRedExterna hay registros con una
     * distr- bucion mayor a 4 pisos, se crean los HHPP y las CCMM)
     */
    public void crearCCMMHHPP() {
        isInfoForCreate = "false";
        mapaErroresYDuplicados = new ArrayList<>();
        mapaHHPPCreadas = new ArrayList<>();
        mapaCCMMCreadas = new HashMap<>();
        String msn;
        FacesMessage facesMessage;
        try {
            if ((edificios != null && !edificios.isEmpty())
                    || (conjuntoCasas != null && !conjuntoCasas.isEmpty())
                    || (casas != null && !casas.isEmpty())) {

                //DATOS DE UBICACIÓN
                //Apartir del nodo obtenemos los datos de ubicacion
                BigDecimal idGeoPol = prefichaCrear.getNodoMgl().getGpoId();
                //Se Obtiene el centro poblado a partir del idGeoPol del nodo
                GeograficoPoliticoMgl centroPoblado = geograficoPoliticoMglFacadeLocal.findById(idGeoPol);
                //Se obtiene la ciudad a partir del idGeoGp del centro poblado
                GeograficoPoliticoMgl ciudad = geograficoPoliticoMglFacadeLocal.findById(centroPoblado.getGeoGpoId());
                //Se obtiene el departamento a partir del idGeoGp de la ciudad
                GeograficoPoliticoMgl departamento = geograficoPoliticoMglFacadeLocal.findById(ciudad.getGeoGpoId());
                //Tecnologia seleccionada para la preficha
                CmtBasicaMgl tecnologia = prefichaCrear.getNodoMgl().getNodTipo();
                //Codigo comunidadRR
                CmtComunidadRr comunidadRr = cmtComunidadRrFacadeLocal.findByCodigoRR(centroPoblado.getGpoCodigo());

                CmtBasicaMgl basicaAcometidaDefault = cmtBasicaMglFacadeLocal.
                        findByCodigoInternoApp(Constant.BASICA_TIPO_SOLICITUD_ACOMETIDA);

                for (PreFichaXlsMgl preficha : edificios) {
                    preficha.getFichaGeorreferenciaMgl().setLevelEconomic(preficha.getFichaGeorreferenciaMgl().getCambioEstrato());
                    if (!preficha.getClasificacion().equalsIgnoreCase("CASASREDEXTERNA")) {
                        generarCCMMDesdeSolicitud(preficha, centroPoblado, ciudad, departamento, comunidadRr, tecnologia, basicaAcometidaDefault);

                    } else {
                        //Crear Cascaron cuenta matriz  
                        CmtCuentaMatrizMgl cuenta = generarCCMMDesdeSolicitud(preficha, centroPoblado, ciudad, departamento, comunidadRr, tecnologia, basicaAcometidaDefault);
                        //Se crean los HHPP
                        if (cuenta != null && cuenta.getCuentaMatrizId() != null) {
                            crearHHPP(preficha, cuenta, centroPoblado, ciudad, departamento);
                        }
                    }
                }
                //Casas RED EXTERNA
                for (PreFichaXlsMgl preficha : casas) {
                    preficha.getFichaGeorreferenciaMgl().setLevelEconomic(preficha.getFichaGeorreferenciaMgl().getCambioEstrato());
                    crearHHPP(preficha, null, centroPoblado, ciudad, departamento);
                }
                //ConjuntoCasasVT
                for (PreFichaXlsMgl preficha : conjuntoCasas) {
                    preficha.getFichaGeorreferenciaMgl().setLevelEconomic(preficha.getFichaGeorreferenciaMgl().getCambioEstrato());
                    generarCCMMDesdeSolicitud(preficha, centroPoblado, ciudad, departamento, comunidadRr, tecnologia, basicaAcometidaDefault);
                }
                msn = "Proceso Finalizado";
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, msn, "");
            } else {
                msn = "Se proceso la preficha, pero no se encontraron registros";
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, msn, "");
            }
            prefichaCrear.setFichaCreada("1");
            preFichaMglFacadeLocal.updatePreFicha(prefichaCrear);
            isInfocreateInRR = String.valueOf(true);
            creoCCMMHPP = true;
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            generarExportProcesoFichas();
            listInfoByPage(1);

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en crearCCMMHHPP. No se pudo crear la CCMM: " + e.getMessage() + " ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en crearCCMMHHPP. No se pudo crear la CCMM: " + e.getMessage() + " ", e, LOGGER);
        }

    }

    public void crearHHPP(PreFichaXlsMgl preficha, CmtCuentaMatrizMgl cuenta, GeograficoPoliticoMgl centro, GeograficoPoliticoMgl city, GeograficoPoliticoMgl state) {
        preficha.getDrDireccionList().remove(0);

        String observaciones;
        int contador = 0;
        preficha.setUsuarioCreacion(usuarioVT);
        for (DrDireccion drDireccionFor : preficha.getDrDireccionList()) {
            observaciones = "";
            try {
                List<HhppMgl> hhppList;
                HhppMgl hhppMgl;
                DrDireccion sinComplemento = drDireccionFor.clone();
                sinComplemento.setCpTipoNivel5(null);
                sinComplemento.setCpValorNivel5(null);
                sinComplemento.setCpTipoNivel6(null);
                sinComplemento.setCpValorNivel6(null);
                //Verificar Busqueda direccion , (Indices , particion)
                hhppList = cmtDireccionDetalleMglFacadeLocal.findHhppByDireccion(sinComplemento, centro.getGpoId(), true, 0, 0, false);
                //Verificar Busqueda HHPP
                hhppMgl = hhppMglFacadeLocal.validaExistenciaHhppFichas(drDireccionFor, centro.getGpoId(), "0", prefichaCrear.getNodoMgl().getNodTipo().getBasicaId());
                if (drDireccionFor.getIdTipoDireccion().equals("BM")) {
                    if (drDireccionFor.getPlacaDireccion() != null) {
                        drDireccionFor.setPlacaDireccion(drDireccionFor.getPlacaDireccion().replace(" ", ""));
                    }
                }
                boolean validacioUnidades = true;
                boolean validacioCasa = true;
                boolean validacionEstructuraPisos = true;
                if (drDireccionFor.getCpTipoNivel5() != null && !drDireccionFor.getCpTipoNivel5().isEmpty() && drDireccionFor.getIdTipoDireccion().equals("BM")) {
                    if (contador < preficha.getDireccionStrList().size()) {
                        if (drDireccionFor.getCpTipoNivel5().equalsIgnoreCase("CASA") && hhppList != null && !hhppList.isEmpty() && (drDireccionFor.getCpValorNivel5() == null
                                || drDireccionFor.getCpValorNivel5().equals(""))) {
                            observaciones += "No es posible continuar con la solicitud para CASA "
                                    + "debido a que ya existen unidades anteriores en la misma dirección|";
                            if (mapObservaciones.containsKey(preficha.getDireccionStrList().get(contador))) {

                                mapObservaciones.put(preficha.getDireccionStrList().get(contador) + " ", observaciones);
                            } else {
                                mapObservaciones.put(preficha.getDireccionStrList().get(contador), observaciones);
                            }
                            mapaErroresYDuplicados.add(new AuxExportFichas(AuxExportFichas.TIPO_HHPP, preficha, preficha.getDireccionStrList().get(contador), drDireccionFor));
                            validacioUnidades = false;
                        }
                    }
                }
                if (contador < preficha.getDireccionStrList().size()) {
                    if (hhppMgl != null && hhppMgl.getHhpId() != null) {
                        observaciones += "La unidad ya existe con la tecnología seleccionada. No es posible"
                                + " crear la solicitud de creación de Hhpp|";
                        if (mapObservaciones.containsKey(preficha.getDireccionStrList().get(contador))) {
                            mapObservaciones.put(preficha.getDireccionStrList().get(contador) + " ", observaciones);
                        } else {
                            mapObservaciones.put(preficha.getDireccionStrList().get(contador), observaciones);
                        }
                        mapaErroresYDuplicados.add(new AuxExportFichas(AuxExportFichas.TIPO_HHPP, preficha, preficha.getDireccionStrList().get(contador), drDireccionFor));
                        validacioCasa = false;
                    }
                }
                if (contador < preficha.getDireccionStrList().size()) {
                    if (hhppList != null && !hhppList.isEmpty()) {
                        for (HhppMgl hhppp : hhppList) {
                            if (hhppp.getHhpApart().contains("CASA") && drDireccionFor.getCpTipoNivel5().contains("PISO")) {
                                observaciones += "Es necesario que realice cambio de apto "
                                        + "a la unidad que tiene 'CASA' para continuar "
                                        + "con la solicitud de creación de Piso|";
                                if (mapObservaciones.containsKey(preficha.getDireccionStrList().get(contador))) {
                                    mapObservaciones.put(preficha.getDireccionStrList().get(contador) + " ", observaciones);
                                } else {
                                    mapObservaciones.put(preficha.getDireccionStrList().get(contador), observaciones);
                                }
                                mapaErroresYDuplicados.add(new AuxExportFichas(AuxExportFichas.TIPO_HHPP, preficha, preficha.getDireccionStrList().get(contador), drDireccionFor));
                                validacionEstructuraPisos = false;
                                break;
                            }
                        }
                    }
                }

                if (validacioUnidades == false || validacioCasa == false || validacionEstructuraPisos == false) {
                    if (contador < preficha.getDireccionStrList().size()) {
                        drDireccionFor.setObservacionFicha(preficha.getDireccionStrList().get(contador) + ";" + observaciones);
                    }
                    contador++;
                } else {

                    CmtDireccionDetalladaMgl direccionDetallada = crearDireccionMGL
                 (drDireccionFor.getDireccionHHPP(), drDireccionFor, city.getGpoNombre(), state.getGpoNombre(),
                 preficha, false, centro.getGeoCodigoDane());
                    //Metodo Sacar Formato RR
                    DireccionRRManager direccionRRManager = obtenerInstanciaDireccionRRManager(prefichaCrear.getNodoMgl().getGpoId(),
                            drDireccionFor);
                    String tipoSolicitud = solicitudFacadeLocal.obtenerCarpetaTipoDireccion(prefichaCrear.getNodoMgl().getNodTipo());
                    String estrato = solicitudFacadeLocal.validarEstrato(drDireccionFor) == null || solicitudFacadeLocal.validarEstrato(drDireccionFor).isEmpty()
                            ? "NG" : solicitudFacadeLocal.validarEstrato(drDireccionFor);
                    drDireccionFor.setEstrato(estrato);

                    if (drDireccionFor.getEstrato() != null && !drDireccionFor.getEstrato().isEmpty() && direccionDetallada != null) {
                        actualizarEstratoDireccion(direccionDetallada, drDireccionFor, tipoSolicitud, direccionRRManager.getDireccion());
                        LOGGER.info("Cambio de estrato realizado");
                    }

                    drDireccionFor.setTipoViviendaHHPP(preficha.getHhppDetalleList().get(contador).getTipoVivienda());
                    direccionRRManager.setPrefichaXlsMgl(preficha);
                    DireccionRREntity dir = direccionRRManager.registrarHHPP_Inspira_Independiente(
                            prefichaCrear.getNodoMgl().getNodCodigo(), prefichaCrear.getNodoMgl().getNodCodigo(),
                            usuarioVT, tipoSolicitud,
                            Constant.FUNCIONALIDAD_DIRECCIONES,
                            estrato, false,
                            null,
                            "HHPP CREADO",
                            usuarioVT,
                            prefichaCrear.getNodoMgl().getGpoId(),
                            true,
                            drDireccionFor.getTipoViviendaHHPP(),
                            prefichaCrear.getNodoMgl());

                    HhppMgl hhpp = hhppMglFacadeLocal.findById(new BigDecimal(dir.getIdHhpp()));

                    hhpp.setListMarcasHhpp(new ArrayList<>());
                    if (cuenta != null && cuenta.getCuentaMatrizId() != null) {
                        hhpp.setCuenta(cuenta.getCuentaId());
                        hhpp.setHhppSubEdificioObj(cuenta.getSubEdificioGeneral());
                        hhpp.setCmtTecnologiaSubId(cuenta.getSubEdificioGeneral().getListTecnologiasSub().get(0));
                    }
                    hhpp.setHhppOrigenFicha(Constant.PLAN_DE_EXPANSION_NACIONAL);
                    hhppMglFacadeLocal.update(hhpp);

                    observaciones = "Se creo el HHPP";
                    observaciones += "| " + crearMarcasHHPP(dir);
                    observaciones += "| " + procesarNotas(hhpp, nota);
                    
                   
                    if (contador < preficha.getDireccionStrList().size()) {
                         mapaHHPPCreadas.add(new AuxExportFichas(AuxExportFichas.TIPO_HHPP, preficha, preficha.getDireccionStrList().get(contador), hhpp));
                        if (mapObservaciones.containsKey(preficha.getDireccionStrList().get(contador))) {
                            mapObservaciones.put(preficha.getDireccionStrList().get(contador) + " ", observaciones);
                        } else {
                            mapObservaciones.put(preficha.getDireccionStrList().get(contador), observaciones);
                        }
                          drDireccionFor.setObservacionFicha(preficha.getDireccionStrList().get(contador) + ";" + observaciones);
                    }
                    contador++;
                }
            } catch (ApplicationException | CloneNotSupportedException | 
                    IndexOutOfBoundsException | NullPointerException e) {
                LOGGER.error("Error en crearHHPP " + e.getMessage(), e);
                try {
                    if (contador < preficha.getDireccionStrList().size()) {
                        if (mapObservaciones.containsKey(preficha.getDireccionStrList().get(contador))) {
                            mapObservaciones.put(preficha.getDireccionStrList().get(contador) + " ", e.getMessage());
                        } else {
                            mapObservaciones.put(preficha.getDireccionStrList().get(contador), e.getMessage());
                        }
                        drDireccionFor.setObservacionFicha(preficha.getDireccionStrList().get(contador) + ";" + e.getMessage());
                         mapaErroresYDuplicados.add(new AuxExportFichas(AuxExportFichas.TIPO_HHPP, preficha, preficha.getDireccionStrList().get(contador), drDireccionFor));
                    }
                   
                    contador++;
                } catch (IndexOutOfBoundsException ex) {
                    mapObservaciones.put(drDireccionFor.getDireccionHHPP() + " ", ex.getMessage());
                    drDireccionFor.setObservacionFicha(drDireccionFor.getDireccionHHPP() + ";" + ex.getMessage());
                    mapaErroresYDuplicados.add(new AuxExportFichas(AuxExportFichas.TIPO_HHPP, preficha, drDireccionFor.getDireccionHHPP(), drDireccionFor));
                    contador++;
                }
            }

        }

    }

    private String crearMarcasHHPP(DireccionRREntity dir) {
        String observaciones = "";
        try {
            HhppMgl hhpp = hhppMglFacadeLocal.findById(new BigDecimal(dir.getIdHhpp()));
            MarcasMgl marcaHhpp1 = marcasHhppMglFacadeLocal.findMarcasMglByCodigo(blackListHhpp1);
            MarcasMgl marcaHhpp2 = marcasHhppMglFacadeLocal.findMarcasMglByCodigo(blackListHhpp2);
            List<MarcasMgl> listaMarcasMgl = new ArrayList<>();
            //Agrega la nueva marca a la lista en la primera posicion
            if (marcaHhpp1 != null) {
                listaMarcasMgl.add(marcaHhpp1);
            }
            if (marcaHhpp2 != null) {
                listaMarcasMgl.add(marcaHhpp2);
            }
            //Traer los datos anteriores
            //Se agregan las marcas
            hhppMglFacadeLocal.agregarMarcasHhppFichasNodos(hhpp, listaMarcasMgl);

        } catch (ApplicationException e) {
            LOGGER.error("Error en crearMarcasHHPP. " + e.getMessage(), e);
            observaciones = e.getMessage();
        } catch (Exception e) {
            LOGGER.error("Error en crearMarcasHHPP. " + e.getMessage(), e);
            observaciones = e.getMessage();
        }
        return observaciones;
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
    public DireccionRRManager obtenerInstanciaDireccionRRManager(BigDecimal centroPobladoId, DrDireccion drDireccion) throws ApplicationException {

        try {
            GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
            DetalleDireccionEntity detalleDireccionEntity;
            detalleDireccionEntity = drDireccion.convertToDetalleDireccionEntity();
            //obtenemos el centro poblado de la solicitud para conocer si la ciudad es multiorigen
            GeograficoPoliticoMgl centroPobladoSolicitud;
            centroPobladoSolicitud = geograficoPoliticoManager.findById(centroPobladoId);

            //Conocer si es multi-origen
            detalleDireccionEntity.setMultiOrigen(centroPobladoSolicitud.getGpoMultiorigen());
            //obtener la direccion en formato RR
            return new DireccionRRManager(detalleDireccionEntity);
        } catch (ApplicationException e) {
            LOGGER.error("Error en obtenerInstanciaDireccionRRManager. " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en obtenerInstanciaDireccionRRManager. " + e.getMessage(), e);
            throw new ApplicationException("Error en obtenerInstanciaDireccionRRManager. " + e.getMessage(), e);
        }
    }

    public void desacargarExportProcesoFichas() {
        exportProcesoFichas(true);
    }

    public void generarExportProcesoFichas() {
        exportProcesoFichas(false);
    }

    public void exportProcesoFichas(boolean isBoton) {
        try {

            SimpleDateFormat formato = new SimpleDateFormat("yyyy_MM_dd");
            String nombreArchivo = prefichaCrear.getNodoMgl().getNodCodigo() + "_" + prefichaCrear.getNodoMgl().getNodNombre() + "_" + formato.format(new Date()) + ".xlsx";

            ExportArchivosFichas exportador = new ExportArchivosFichas(nombreArchivo, prefichaCrear, edificiosTxt, casasTxt, conjuntoCasasTxt, hhppSNXls, mapaCCMMCreadas, mapaHHPPCreadas, mapaErroresYDuplicados, geograficoPoliticoMglFacadeLocal);
            OutputStream salida;
            FacesContext fc = FacesContext.getCurrentInstance();
            if (isBoton) {
                fc = FacesContext.getCurrentInstance();
                HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
                response1.reset();
                response1.setContentType("application/vnd.ms-excel");
                response1.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo);
                salida = response1.getOutputStream();
            } else {
                salida = new ByteArrayOutputStream();
            }

            exportador.generarArchivo(salida);

            DataArchivoExportFichas datosArchivo = exportador.getDataHistorico();

            if (!isBoton) {
                BigDecimal idGeoPol = prefichaCrear.getNodoMgl().getGpoId();
                datosArchivo.setIdNodo(idGeoPol);
                GeograficoPoliticoMgl centro = geograficoPoliticoMglFacadeLocal.findById(idGeoPol);
                GeograficoPoliticoMgl city = geograficoPoliticoMglFacadeLocal.findById(centro.getGeoGpoId());
                GeograficoPoliticoMgl state = geograficoPoliticoMglFacadeLocal.findById(city.getGeoGpoId());
                List<ExportFichasMgl> historicos = exportFichasMglFacadeLocal.findHistoricoFichaPorIdPreficha(prefichaCrear.getPrfId());
                if (historicos == null || historicos.isEmpty()) {
                    ExportFichasMgl registroHistorico = new ExportFichasMgl();
                    registroHistorico.setPrefichaId(prefichaCrear);
                    registroHistorico.setNombreArchivo(nombreArchivo);
                    registroHistorico.setFechaCreacion(new Date());
                    registroHistorico.setUsuarioCrea(httpSession.getAttribute("loginUserSecurity").toString());
                    registroHistorico.setTipoTecnologiaId(prefichaCrear.getNodoMgl().getNodTipo());
                    registroHistorico.setNodo(prefichaCrear.getNodoMgl());
                    registroHistorico.setDepartamento(state);
                    registroHistorico.setCiudad(city);
                    registroHistorico.setCentroPoblado(centro);
                    registroHistorico.setTextoArchivo(new Gson().toJson(datosArchivo));
                    exportFichasMglFacadeLocal.crearRegistroExportFichas(registroHistorico);
                }
            }
            if (isBoton) {
                fc.responseComplete();
            }
        } catch (ApplicationException | IOException e) {
            LOGGER.error("Error en desacargarExportProcesoFichas. No se pudo descargar el archivo de Preficha la preficha: " + e.getMessage() + " ", e);
            FacesUtil.mostrarMensajeError("Error en desacargarExportProcesoFichas. No se pudo descargar el archivo de Preficha la preficha: " + e.getMessage() + " ", e, LOGGER);
        }
    }

    public Map<String, String> getMapObservaciones() {

        Map<String, String> map = new HashMap<>();
        listadoObservaciones = new ArrayList<>();
        observacionesFichas = new ArrayList<>();
        String key;
        String concatenado;
        for (Map.Entry<String, String> entry : mapObservaciones.entrySet()) {
            key = entry.getKey();
            key = key.replace("APARTAMENTO", "AP");
            key = key.replace("LOCAL", "LC");
            key = key.replace("CASA", "CS");
            key = key.replace("#", "");
            concatenado = key + ";" + entry.getValue();
            listadoObservaciones.add(concatenado);
            map.put(key, entry.getValue());
        }
        return map;
    }

    public void generarExcelResultadoCargue() {
        try {
            XSSFWorkbook libroResultadoFicha = new XSSFWorkbook();

            //Creacion Hojas Libro Excel
            XSSFSheet hojaEdificiosVT = libroResultadoFicha.createSheet("EDIFICIOS VT");
            XSSFSheet hojaCasasRedExterna = libroResultadoFicha.createSheet("CASAS RED EXTERNA");
            XSSFSheet hojaConjuntoCasas = libroResultadoFicha.createSheet("CONJUNTO CASAS");
            XSSFSheet hojahhppSN = libroResultadoFicha.createSheet("SN");
            CellStyle cellStyle = libroResultadoFicha.createCellStyle();

            Font font = libroResultadoFicha.createFont();
            font.setColor((short) 0);

            cellStyle.setFont(font);

            //se ingresan los datos de edificios VT 
            construirHojaEdificiosVT(hojaEdificiosVT, cellStyle);
            //se ingresan los datos de casas red externa 
            construirHojaCasasRedExterna(hojaCasasRedExterna, cellStyle);
            //se ingresan los datos de conjunto de casas VT
            construirHojaConjuntoCasas(hojaConjuntoCasas, cellStyle);
            //se ingresan los datos de los hhppp clasificados como sn
            construirHojaSN(hojahhppSN, cellStyle);

            String fileName = "ResultadoFicha-";
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
            response1.reset();
            response1.setContentType("application/vnd.ms-excel");
            SimpleDateFormat formato = new SimpleDateFormat("ddMMMyyyy_HH_mm_ss");
            response1.setHeader("Content-Disposition", "attachment; filename=" + fileName + formato.format(new Date()) + ".xlsx");
            OutputStream output = response1.getOutputStream();
            libroResultadoFicha.write(output);
            output.flush();
            output.close();
            fc.responseComplete();
        } catch (IOException | NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en generarExcelResultadoCargue. No se pudo construir y descargar informe de cargue de ficha: " + e.getMessage() + " ", e, LOGGER);
        }
    }

    public void construirHojaCasasRedExterna(XSSFSheet hojaCasasRedExterna, CellStyle cellStyle) {
        Row cabecera = hojaCasasRedExterna.createRow(0);
        Cell direccion1 = cabecera.createCell(0);
        direccion1.setCellValue("DIRECCIÓN");
        direccion1.setCellStyle(cellStyle);
        Cell observaciones = cabecera.createCell(1);
        observaciones.setCellValue("OBSERVACIONES");
        observaciones.setCellStyle(cellStyle);
        int numeroFila = 1;
        if (casas != null && !casas.isEmpty()) {
            for (PreFichaXlsMgl casa : casas) {
                if (casa.getDrDireccionList() != null && !casa.getDrDireccionList().isEmpty()) {
                    if (casa.getDrDireccionList() != null && !casa.getDrDireccionList().isEmpty()) {
                        for (DrDireccion drDireccion1 : casa.getDrDireccionList()) {
                            if (drDireccion1.getObservacionFicha() != null && !drDireccion1.getObservacionFicha().isEmpty()) {
                                Row direccionFicha1 = hojaCasasRedExterna.createRow(numeroFila++);
                                String observacionesDireccion[] = drDireccion1.getObservacionFicha().split("\\;");
                                Cell direccionProcesada = direccionFicha1.createCell(0);
                                direccionProcesada.setCellValue(observacionesDireccion[0]);
                                direccionProcesada.setCellStyle(cellStyle);
                                Cell observacionesCargue = direccionFicha1.createCell(1);
                                observacionesCargue.setCellValue(observacionesDireccion[1]);
                                observacionesCargue.setCellStyle(cellStyle);
                            }
                        }
                    }
                }
            }
        }
    }

    public void construirHojaEdificiosVT(XSSFSheet hojaEdificiosVT, CellStyle cellStyle) {
        Row cabecera = hojaEdificiosVT.createRow(0);
        Cell direccion1 = cabecera.createCell(0);
        direccion1.setCellValue("DIRECCIÓN");
        direccion1.setCellStyle(cellStyle);
        Cell observaciones = cabecera.createCell(1);
        observaciones.setCellValue("OBSERVACIONES");
        observaciones.setCellStyle(cellStyle);
        int numeroFila = 1;
        if (edificios != null && !edificios.isEmpty()) {
            for (PreFichaXlsMgl edificio : edificios) {
                Row direccionFicha1 = hojaEdificiosVT.createRow(numeroFila++);
                Cell direccionProcesada = direccionFicha1.createCell(0);
                direccionProcesada.setCellValue(edificio.getViaPrincipal() + " " + edificio.getPlaca());
                direccionProcesada.setCellStyle(cellStyle);
                Cell observacionesCargue = direccionFicha1.createCell(1);
                observacionesCargue.setCellValue(edificio.getObservacionFicha());
                observacionesCargue.setCellStyle(cellStyle);
                if (edificio.getDrDireccionList() != null && !edificio.getDrDireccionList().isEmpty()) {
                    for (DrDireccion drDireccion1 : edificio.getDrDireccionList()) {
                        if (drDireccion1.getObservacionFicha() != null && !drDireccion1.getObservacionFicha().isEmpty()) {
                            Row direccionHhhpp = hojaEdificiosVT.createRow(numeroFila++);
                            String observacionesDireccion[] = drDireccion1.getObservacionFicha().split("\\;");
                            Cell direccionProcesadaHHPP = direccionHhhpp.createCell(0);
                            direccionProcesadaHHPP.setCellValue(observacionesDireccion[0]);
                            direccionProcesadaHHPP.setCellStyle(cellStyle);
                            Cell observacionesHHPP = direccionHhhpp.createCell(1);
                            observacionesHHPP.setCellValue(observacionesDireccion[1]);
                            observacionesHHPP.setCellStyle(cellStyle);
                        }

                    }
                }
            }
        }
    }

    public void construirHojaConjuntoCasas(XSSFSheet hojaConjuntoCasas, CellStyle cellStyle) {
        Row cabecera = hojaConjuntoCasas.createRow(0);
        Cell direccion1 = cabecera.createCell(0);
        direccion1.setCellValue("DIRECCIÓN");
        direccion1.setCellStyle(cellStyle);
        Cell observaciones = cabecera.createCell(1);
        observaciones.setCellValue("OBSERVACIONES");
        observaciones.setCellStyle(cellStyle);
        int numeroFila = 1;
        if (conjuntoCasas != null && !conjuntoCasas.isEmpty()) {
            for (PreFichaXlsMgl conjunto : conjuntoCasas) {
                Row direccionFicha1 = hojaConjuntoCasas.createRow(numeroFila++);
                Cell direccionProcesada = direccionFicha1.createCell(0);
                direccionProcesada.setCellValue(conjunto.getViaPrincipal() + " " + conjunto.getPlaca());
                direccionProcesada.setCellStyle(cellStyle);
                Cell observacionesCargue = direccionFicha1.createCell(1);
                observacionesCargue.setCellValue(conjunto.getObservacionFicha());
                observacionesCargue.setCellStyle(cellStyle);
            }
        }
    }

    public void construirHojaSN(XSSFSheet hojahhppSN, CellStyle cellStyle) {
        Row cabecera = hojahhppSN.createRow(0);
        Cell direccion1 = cabecera.createCell(0);
        direccion1.setCellValue("DIRECCIÓN");
        direccion1.setCellStyle(cellStyle);
        int numeroFila = 1;
        if (hhppSNXls != null && !hhppSNXls.isEmpty()) {
            for (PreFichaXlsMgl sn : hhppSNXls) {
                Row direccionFicha1 = hojahhppSN.createRow(numeroFila++);
                Cell direccionProcesada = direccionFicha1.createCell(0);
                direccionProcesada.setCellValue(sn.getViaPrincipal() + " " + sn.getPlaca());
                direccionProcesada.setCellStyle(cellStyle);
            }
        }
    }

    public void aplicarCambioEstratoMasivo() {
        try {
            for (PreFichaXlsMgl edificio : edificiosVtXls) {
                edificio.getFichaGeorreferenciaMgl().setCambioEstrato(cambioEstratoMasivo);
                preFichaMglFacadeLocal.acualizarPrefichaXls(edificio.getFichaGeorreferenciaMgl(), edificio.getFichaGeorreferenciaMgl(), edificio, false);
            }
            for (PreFichaXlsMgl casa : casasRedExternaXls) {
                casa.getFichaGeorreferenciaMgl().setCambioEstrato(cambioEstratoMasivo);
                preFichaMglFacadeLocal.acualizarPrefichaXls(casa.getFichaGeorreferenciaMgl(), casa.getFichaGeorreferenciaMgl(), casa, false);
            }
            for (PreFichaXlsMgl conjunto : conjCasasVtXls) {
                conjunto.getFichaGeorreferenciaMgl().setCambioEstrato(cambioEstratoMasivo);
                preFichaMglFacadeLocal.acualizarPrefichaXls(conjunto.getFichaGeorreferenciaMgl(), conjunto.getFichaGeorreferenciaMgl(), conjunto, false);
            }
            fichaXlsMglList = preFichaMglFacadeLocal.getListXLSByPrefichaFase(prefichaCrear.getPrfId(), prefichaCrear.getFase());
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en aplicarCambioEstratoMasivo. No se pudo cambiar estrato de forma masiva formulario: " + e.getMessage() + " ", e, LOGGER);
        }
    }

    public void aplicarCambioEstratoIndividual(PreFichaXlsMgl preficha) {
        try {
            preFichaMglFacadeLocal.acualizarPrefichaXls(preficha.getFichaGeorreferenciaMgl(), preficha.getFichaGeorreferenciaMgl(), preficha, false);
            fichaXlsMglList = preFichaMglFacadeLocal.getListXLSByPrefichaFase(prefichaCrear.getPrfId(), prefichaCrear.getFase());
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en aplicarCambioEstratoIndividual. No se pudo cambiar estrato de forma masiva formulario: " + e.getMessage() + " ", e, LOGGER);
        }
    }

    public void actualizarEstratoDireccion(CmtDireccionDetalladaMgl direccionDetallada, DrDireccion estratoSeleccionado, String tipoSol, DireccionRREntity direccionRREntity) {
        try {
            DireccionRRManager rrManager = new DireccionRRManager(true);
            String tipoEdificio = rrManager.obtenerTipoEdificio(direccionRREntity.getNumeroApartamento(),
                    Constant.FUNCIONALIDAD_DIRECCIONES,
                    tipoSol);

            if (direccionDetallada != null) {
                //se obtiene los registros de direccion y sub direccion para actualizar el estrato de cada uno si es una subdireccion
                if (direccionDetallada.getDireccion() != null && direccionDetallada.getSubDireccion() != null) {
                    SubDireccionMgl subDireccion = subDireccionMglFacadeLocal.findById(direccionDetallada.getSubDireccion());
                    if (subDireccion != null) {
                        String estrato;
                        estrato = obtenerEstrato(estratoSeleccionado.getEstrato(),
                                tipoSol, tipoEdificio);
                         // estrato puede venir null
                        if (estrato != null) {
                            if (!estrato.equals("") && (estrato.equals("NG") || estrato.equals("NR"))) {
                                estrato = "-1";
                            }
                        }else{
                             estrato = "0";
                        }
                        subDireccion.setSdiEstrato(new BigDecimal(estrato));
                        subDireccionMglFacadeLocal.updateSubDireccionMgl(subDireccion);
                    }
                } else {
                    if (direccionDetallada.getDireccion() != null) {
                        DireccionMgl direccion1 = direccionMglFacadeLocal.findById(direccionDetallada.getDireccion());
                        if (direccion1 != null) {
                            String estrato;
                            estrato = obtenerEstrato(estratoSeleccionado.getEstrato(),
                                    tipoSol, tipoEdificio);
                            // estrato puede venir null
                            if (estrato != null) {
                                if (!estrato.equals("") && (estrato.equals("NG") || estrato.equals("NR"))) {
                                    estrato = "-1";
                                }
                            } else {
                                estrato = "0";
                            }
                            direccion1.setDirEstrato(new BigDecimal(estrato));
                            direccionMglFacadeLocal.updateDireccionMgl(direccion1);
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al intentar hacer la actualización del estrato de la dirección" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al intentar hacer la actualización del estrato de la dirección" + e.getMessage(), e, LOGGER);
        }
    }

    public String obtenerEstrato(String nvlSocioEconomico, String tipoSol, String tipoEdificio) {
        String estrato = nvlSocioEconomico;
        if (tipoSol != null && tipoSol.equalsIgnoreCase(Constantes.TIPOSOL_PYMES)) {
            estrato = "0";
        } else if (tipoEdificio.equalsIgnoreCase("K")
                || tipoEdificio.equalsIgnoreCase("O")
                || tipoEdificio.equalsIgnoreCase("L")) {
            estrato = "0";//NR
        }
        return estrato;
    }

    private String listInfoByPage(int page) {
        try {

            //se cargan las Preficha que hayan sido ya se georreferenciaron
            fichaToGenerarList = preFichaMglFacadeLocal.getListFichaToCreateByDate(page, ConstantsCM.PAGINACION_DIEZ_FILAS, true, fechaInicial, fechaFinal);
            if (todasLasPreFichasMarcadas) {
                marcarListaFichas();
            }
            actual = page;

        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error cargando lista en FichaGenerarBean: listInfoByPage() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Ocurrió un error en la paginacion en FichaGenerarBean: pageFirst() " + ex.getMessage(), ex);
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
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en FichaGenerarBean: pagePrevious() " + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Ocurrió un error en la paginacion en FichaGenerarBean: pagePrevious() " + ex.getMessage(), ex);
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
            FacesUtil.mostrarMensajeError("Ocurrió un error en FichaGenerarBean class: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en FichaGenerarBean: irPagina() " + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en FichaGenerarBean: pageNext() " + ex.getMessage() + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Ocurrió un error en la paginacion en FichaGenerarBean: pageNext() " + ex.getMessage(), ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en FichaGenerarBean: pageLast() " + ex.getMessage() + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Ocurrió un error en la paginacion en FichaGenerarBean: pageLast() " + ex.getMessage(), ex);
        }
    }

    public int getTotalPaginas() {
        try {

            List<PreFichaMgl> fichaToGenerarListCon = preFichaMglFacadeLocal.getListFichaToCreateByDate(0, 0, false, fechaInicial, fechaFinal);
            int pageSol = fichaToGenerarListCon.size();
            return (pageSol % ConstantsCM.PAGINACION_DIEZ_FILAS != 0)
                    ? (pageSol / ConstantsCM.PAGINACION_DIEZ_FILAS) + 1
                    : pageSol / ConstantsCM.PAGINACION_DIEZ_FILAS;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en FichaGenerarBean class: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en FichaGenerarBean: getTotalPaginas() " + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en FichaGenerarBean: getPageActual() " + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public void filtrarLista() {
        eliminarTodoSelected = false;
        todasLasPreFichasMarcadas = false;
        hayFichasSeleccionadas = false;
        registrosFiltrados = false;
        filtroAplicado = false;

        if (fechaInicial != null && fechaFinal != null) {

            if (fechaInicial.compareTo(fechaFinal) > 0) {
                String msn = "La fecha inicial debe ser anterior a la final.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, msn, ""));
            } else {

                pageFirst();
                this.registrosFiltrados = true;
                this.filtroAplicado = true;

                if (fichaToGenerarList == null || fichaToGenerarList.isEmpty()) {
                    String msn = "No se encontraron registros para el rango de fechas suministrado.";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO, msn, ""));
                }
            }

        } else {
            if (fechaInicial == null && fechaFinal == null) {
                pageFirst();
                this.filtroAplicado = true;
            } else {
                String msn = "Por favor, indique las fechas a filtrar con el formato dd/mm/yyyy";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, msn, ""));

            }
        }
        seleccionarTodo = false;

    }

    public String getSelectedFichasPorEliminar() {

        try {

            if (fichaToGenerarList != null && !fichaToGenerarList.isEmpty()) {
                if (todasLasPreFichasMarcadas) {
                    for (PreFichaMgl ficha : preFichaFiltradaList) {

                        ficha.setEstadoRegistro(0);
                        preFichaMglFacadeLocal.updatePreFicha(ficha);

                    }
                    seleccionarTodo = false;

                } else {
                    for (PreFichaMgl ficha : getFichaToGenerarList()) {
                        if (ficha.isSelected()) {

                            ficha.setEstadoRegistro(0);
                            preFichaMglFacadeLocal.updatePreFicha(ficha);

                        }

                    }
                }
                String msn = "La eliminacion ha sido exitosa.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, msn, ""));
                todasLasPreFichasMarcadas = false;
                hayFichasSeleccionadas = false;
                eliminarTodoSelected = false;

            } else {
                String msn = "Marque primero las prefichas a Eliminar.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, msn, ""));
            }

        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en eliminarFichas. " + e.getMessage(), e, LOGGER);
        }
        fechaInicial = null;
        fechaFinal = null;
        pageFirst();
        return null;
    }

    public String evaluarSiHayFichasMarcadas() {
        hayFichasSeleccionadas = false;
        for (PreFichaMgl ficha : fichaToGenerarList) {
            if (ficha.isSelected()) {
                hayFichasSeleccionadas = true;
                break;
            }

        }

        return null;
    }

    public String marcarTodo() {

        todasLasPreFichasMarcadas = true;

        try {

            if (fechaInicial != null && fechaFinal != null) {
                seleccionarTodo = !seleccionarTodo;

                preFichaFiltradaList = preFichaMglFacadeLocal.getListFichaToCreateByDate(0, 0, false, fechaInicial, fechaFinal);
                if (!preFichaFiltradaList.isEmpty()) {
                    hayFichasSeleccionadas = seleccionarTodo;
                }
                marcarListaFichas();
            } else {
                String msn = "Por favor, indique las fechas a filtrar";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, msn, ""));
                eliminarTodoSelected = false;
                todasLasPreFichasMarcadas = false;
                hayFichasSeleccionadas = false;
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en marcarTodo. " + e.getMessage(), e, LOGGER);
        }
        return null;

    }

    public void fechaAlterada() {
        filtroAplicado = false;
        rangoFechasValido = false;

        if (fechaInicial == null && fechaFinal == null) {
            rangoFechasValido = true;
        } else {

            if (fechaInicial != null && fechaFinal != null) {

                if (fechaInicial.compareTo(fechaFinal) <= 0) {

                    rangoFechasValido = true;
                }

            }

        }
    }

    /**
     * Función que renderiza PopUp tabla tabulación dirección Fichas (EDIFICIOS
     * VT/CASA RED EXTERNA/CONJUNTO VT),para direcciones No Georeferenciadas.
     *
     * @author YurannyCastiblanco
     * @param preficha
     * @return 
     */
    public String irPopUpDireccionFicha(PreFichaXlsMgl preficha) {

        drDireccion = new DrDireccion();
        prefichaSelected = preficha;
        direccion = preficha.getFichaGeorreferenciaMgl().getAddress();
        try {
            
            BigDecimal idGeoPol = prefichaCrear.getNodoMgl().getGpoId();
            GeograficoPoliticoMgl centroPoblado = geograficoPoliticoMglFacadeLocal.findById(idGeoPol);

            validadorDireccionBean = (ValidadorDireccionBean) JSFUtil.getSessionBean(ValidadorDireccionBean.class);
            validadorDireccionBean.validarDireccion(drDireccion, direccion, centroPoblado, preficha.getBarrio(), this, FichaGenerarBean.class, Constant.TIPO_VALIDACION_DIR_HHPP, Constant.DIFERENTE_MODIFICACION_CM);

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class irPopUpDireccionFicha1" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class irPopUpDireccionFicha" + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Método que carga los iconos (OK/Error)en las pestañas (EDIFICIOS VT/CASA
     * RED EXTERNA/CONJUNTO VT),para direcciones No Georeferenciadas(icono Eror)
     * y direcciones Georeferenciadas(Ok).
     *
     * @author YurannyCastiblanco
     */
    public void recargar(ResponseValidarDireccionDto direccionDto) {


        responseValidarDireccionDto = direccionDto;
        drDireccion = responseValidarDireccionDto.getDrDireccion();
        direccion = responseValidarDireccionDto.getDireccion();
        prefichaSelected.setDireccionTabulada(drDireccion);

        //se asigna direccion respuesta del geo sin alteraciones para busqueda por avenidas        
        if (direccionDto != null && direccionDto.getDireccionRespuestaGeo() != null
                && !direccionDto.getDireccionRespuestaGeo().isEmpty()) {
            if (drDireccion != null) {
                drDireccion.setDireccionRespuestaGeo(direccionDto.getDireccionRespuestaGeo());
            }
        }
        barrioslist = responseValidarDireccionDto.getBarrios();

        // Para la multiorigen, en caso tal que el GEO no obtenga el listado de 
        // barrios, se adiciona al listado el campo "barrioGeo" (si existe).
        if ((barrioslist == null || barrioslist.isEmpty())
                && responseValidarDireccionDto.isMultiOrigen()
                && responseValidarDireccionDto.getBarrioGeo() != null
                && !responseValidarDireccionDto.getBarrioGeo().isEmpty()) {
            barrioslist = new ArrayList<>();
            barrioslist.add(responseValidarDireccionDto.getBarrioGeo());
        }

        if (responseValidarDireccionDto.getDrDireccion() != null
                && responseValidarDireccionDto.getBarrioGeo() != null
                && !responseValidarDireccionDto.getBarrioGeo().isEmpty()) {
            drDireccion.setBarrio(responseValidarDireccionDto.getBarrioGeo().toUpperCase());
        } 

        if (responseValidarDireccionDto.isDirTabulada()) {
            
            //se realiza recorrido al listado global para setear la tabulada realizada
            if (fichaProcessXlsList != null && !fichaProcessXlsList.isEmpty()) {
                for (PreFichaXlsMgl preFichaXlsMgl : fichaProcessXlsList) {
                    if (preFichaXlsMgl.getId().equals(prefichaSelected.getId())) {
                        preFichaXlsMgl.setDireccionTabulada(prefichaSelected.getDireccionTabulada());
                    }
                }
            }            
            
            for (PreFichaXlsMgl preFichaXlsMgl : edificiosVtXls) {
                if (preFichaXlsMgl.getId().equals(prefichaSelected.getId())) {
                    //edificiosVtXls.remove(preFichaXlsMgl);
                    //edificiosVtXls.add(preFichaXlsMgl);
                    preFichaXlsMgl.setGeoDrDireccionNull(true);
                    preFichaXlsMgl.setGeoDrDireccionNotNull(false);
                    preFichaXlsMgl.getFichaGeorreferenciaMgl().setAddress(direccion);
                    if(prefichaSelected.getDireccionTabulada() != null){
                    preFichaXlsMgl.setDireccionTabulada(prefichaSelected.getDireccionTabulada());
                    }
                    break;
                }
            }

            for (PreFichaXlsMgl preFichaXlsMgl : casasRedExternaXls) {
                if (preFichaXlsMgl.getId().equals(prefichaSelected.getId())) {
                    //casasRedExternaXls.remove(preFichaXlsMgl); 
                    //casasRedExternaXls.add(preFichaXlsMgl);
                    preFichaXlsMgl.setGeoDrDireccionNull(true);
                    preFichaXlsMgl.setGeoDrDireccionNotNull(false);
                    preFichaXlsMgl.getFichaGeorreferenciaMgl().setAddress(direccion);
                    if(prefichaSelected.getDireccionTabulada() != null){
                    preFichaXlsMgl.setDireccionTabulada(prefichaSelected.getDireccionTabulada());
                    }
                    break;
                }
            }

            for (PreFichaXlsMgl preFichaXlsMgl : conjCasasVtXls) {
                if (preFichaXlsMgl.getId().equals(prefichaSelected.getId())) {
                    //conjCasasVtXls.remove(preFichaXlsMgl);
                    //conjCasasVtXls.add(preFichaXlsMgl);
                    preFichaXlsMgl.setGeoDrDireccionNull(true);
                    preFichaXlsMgl.setGeoDrDireccionNotNull(false);
                    preFichaXlsMgl.getFichaGeorreferenciaMgl().setAddress(direccion);
                    if(prefichaSelected.getDireccionTabulada() != null){
                    preFichaXlsMgl.setDireccionTabulada(prefichaSelected.getDireccionTabulada());
                    }
                    break;
                }
            }
            prefichaSelected.setButtonOk(responseValidarDireccionDto.isDirTabulada());
            responseValidarDireccionDto.setPrefichaSelected(prefichaSelected);
            prefichaSelected = new PreFichaXlsMgl();
            dirOk = true;
        }

    }

    public boolean isTodasLasPreFichasMarcadas() {
        return todasLasPreFichasMarcadas;
    }

    public void setTodasLasPreFichasMarcadas(boolean todasLasPreFichasMarcadas) {
        this.todasLasPreFichasMarcadas = todasLasPreFichasMarcadas;
    }

    private void marcarListaFichas() {
        getFichaToGenerarList().forEach((ficha) -> {
            ficha.setSelected(seleccionarTodo);
        });
    }

    public void setMapObservaciones(Map<String, String> map) {
        this.mapObservaciones = map;
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public BigDecimal getIdPrefichaSelected() {
        return idPrefichaSelected;
    }

    public void setIdPrefichaSelected(BigDecimal idPrefichaSelected) {
        this.idPrefichaSelected = idPrefichaSelected;
    }

    public List<PreFichaMgl> getFichaToGenerarList() {
        return fichaToGenerarList;
    }

    public void setFichaToGenerarList(List<PreFichaMgl> fichaToGenerarList) {
        this.fichaToGenerarList = fichaToGenerarList;
    }

    public List<PreFichaXlsMgl> getEdificiosVtXls() {
        return edificiosVtXls;
    }

    public void setEdificiosVtXls(List<PreFichaXlsMgl> edificiosVtXls) {
        this.edificiosVtXls = edificiosVtXls;
    }

    public List<PreFichaXlsMgl> getCasasRedExternaXls() {
        return casasRedExternaXls;
    }

    public void setCasasRedExternaXls(List<PreFichaXlsMgl> casasRedExternaXls) {
        this.casasRedExternaXls = casasRedExternaXls;
    }

    public List<PreFichaXlsMgl> getConjCasasVtXls() {
        return conjCasasVtXls;
    }

    public void setConjCasasVtXls(List<PreFichaXlsMgl> conjCasasVtXls) {
        this.conjCasasVtXls = conjCasasVtXls;
    }

    public List<PreFichaXlsMgl> getHhppSNXls() {
        return hhppSNXls;
    }

    public void setHhppSNXls(List<PreFichaXlsMgl> hhppSNXls) {
        this.hhppSNXls = hhppSNXls;
    }

    public List<PreFichaXlsMgl> getIngresoNuevoNXls() {
        return IngresoNuevoNXls;
    }

    public void setIngresoNuevoNXls(List<PreFichaXlsMgl> IngresoNuevoNXls) {
        this.IngresoNuevoNXls = IngresoNuevoNXls;
    }

    public List<PreFichaXlsMgl> getFichaXlsMglList() {
        return fichaXlsMglList;
    }

    public void setFichaXlsMglList(List<PreFichaXlsMgl> fichaXlsMglList) {
        this.fichaXlsMglList = fichaXlsMglList;
    }

    public String getLabelHeaderTableLists() {
        return labelHeaderTableLists;
    }

    public void setLabelHeaderTableLists(String labelHeaderTableLists) {
        this.labelHeaderTableLists = labelHeaderTableLists;
    }

    public List<PreFichaXlsMgl> getFichaProcessXlsList() {
        return fichaProcessXlsList;
    }

    public void setFichaProcessXlsList(List<PreFichaXlsMgl> fichaProcessXlsList) {
        this.fichaProcessXlsList = fichaProcessXlsList;
    }

    public String getIsInfoProcesada() {
        return isInfoProcesada;
    }

    public void setIsInfoProcesada(String isInfoProcesada) {
        this.isInfoProcesada = isInfoProcesada;
    }

    public String getIsInfoForCreate() {
        return isInfoForCreate;
    }

    public void setIsInfoForCreate(String isInfoForCreate) {
        this.isInfoForCreate = isInfoForCreate;
    }

    public String getIsInfocreateInRR() {
        return isInfocreateInRR;
    }

    public void setIsInfocreateInRR(String isInfocreateInRR) {
        this.isInfocreateInRR = isInfocreateInRR;
    }

    public List<PreFichaHHPPDetalleMgl> getHhppDetalleList() {
        return hhppDetalleList;
    }

    public void setHhppDetalleList(List<PreFichaHHPPDetalleMgl> hhppDetalleList) {
        this.hhppDetalleList = hhppDetalleList;
    }

    public String getBlackListCM1() {
        return blackListCM1;
    }

    public void setBlackListCM1(String blackListCM1) {
        this.blackListCM1 = blackListCM1;
    }

    public String getBlackListCM2() {
        return blackListCM2;
    }

    public void setBlackListCM2(String blackListCM2) {
        this.blackListCM2 = blackListCM2;
    }

    public String getBlackListHhpp1() {
        return blackListHhpp1;
    }

    public void setBlackListHhpp1(String blackListHhpp1) {
        this.blackListHhpp1 = blackListHhpp1;
    }

    public String getBlackListHhpp2() {
        return blackListHhpp2;
    }

    public void setBlackListHhpp2(String blackListHhpp2) {
        this.blackListHhpp2 = blackListHhpp2;
    }

    public List<MarcasMgl> getListMarcas() {
        return listMarcas;
    }

    public void setListMarcas(List<MarcasMgl> listMarcas) {
        this.listMarcas = listMarcas;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public List<PreFichaXlsMgl> getEdificiosVtXlsNoProcesados() {
        return edificiosVtXlsNoProcesados;
    }

    public void setEdificiosVtXlsNoProcesados(List<PreFichaXlsMgl> edificiosVtXlsNoProcesados) {
        this.edificiosVtXlsNoProcesados = edificiosVtXlsNoProcesados;
    }

    public List<PreFichaXlsMgl> getCasasRedExternaXlsNoProcesados() {
        return casasRedExternaXlsNoProcesados;
    }

    public void setCasasRedExternaXlsNoProcesados(List<PreFichaXlsMgl> casasRedExternaXlsNoProcesados) {
        this.casasRedExternaXlsNoProcesados = casasRedExternaXlsNoProcesados;
    }

    public List<PreFichaXlsMgl> getConjCasasVtXlsNoProcesados() {
        return conjCasasVtXlsNoProcesados;
    }

    public void setConjCasasVtXlsNoProcesados(List<PreFichaXlsMgl> conjCasasVtXlsNoProcesados) {
        this.conjCasasVtXlsNoProcesados = conjCasasVtXlsNoProcesados;
    }

    public List<PreFichaXlsMgl> getFichaXlsMglListNoProcesados() {
        return fichaXlsMglListNoProcesados;
    }

    public void setFichaXlsMglListNoProcesados(List<PreFichaXlsMgl> fichaXlsMglListNoProcesados) {
        this.fichaXlsMglListNoProcesados = fichaXlsMglListNoProcesados;
    }

    public List<CmtBasicaMgl> getListMarcasCM() {
        return listMarcasCM;
    }

    public void setListMarcasCM(List<CmtBasicaMgl> listMarcasCM) {
        this.listMarcasCM = listMarcasCM;
    }

    public PreFichaMgl getPrefichaSeleccionada() {
        return prefichaSeleccionada;
    }

    public void setPrefichaSeleccionada(PreFichaMgl prefichaSeleccionada) {
        this.prefichaSeleccionada = prefichaSeleccionada;
    }

    public boolean isCreoCCMMHPP() {
        return creoCCMMHPP;
    }

    public void setCreoCCMMHPP(boolean creoCCMMHPP) {
        this.creoCCMMHPP = creoCCMMHPP;
    }

    public List<PreFichaXlsMgl> getEdificios() {
        return edificios;
    }

    public void setEdificios(List<PreFichaXlsMgl> edificios) {
        this.edificios = edificios;
    }

    public List<PreFichaXlsMgl> getCasas() {
        return casas;
    }

    public void setCasas(List<PreFichaXlsMgl> casas) {
        this.casas = casas;
    }

    public List<PreFichaXlsMgl> getConjuntoCasas() {
        return conjuntoCasas;
    }

    public void setConjuntoCasas(List<PreFichaXlsMgl> conjuntoCasas) {
        this.conjuntoCasas = conjuntoCasas;
    }

    public List<ObservacionesFichasDTO> getObservacionesFichas() {
        getMapObservaciones();
        if (listadoObservaciones != null && !listadoObservaciones.isEmpty()) {
            Collections.sort(listadoObservaciones);
            for (String observacion : listadoObservaciones) {
                ObservacionesFichasDTO observaciones = new ObservacionesFichasDTO();
                observaciones.setDireccion(observacion.split("\\;")[0]);
                observaciones.setObservaciones(observacion.split("\\;")[1]);
                observacionesFichas.add(observaciones);
            }
        }
        return observacionesFichas;
    }

    public void setObservacionesFichas(List<ObservacionesFichasDTO> observacionesFichas) {
        this.observacionesFichas = observacionesFichas;
    }

    public ArrayList<String> getListaObservaciones() {
        return listaObservaciones;
    }

    public void setListaObservaciones(ArrayList<String> listaObservaciones) {
        this.listaObservaciones = listaObservaciones;
    }

    public boolean isMultiorigen() {
        BigDecimal idGeoPol = prefichaCrear.getNodoMgl().getGpoId();
        boolean respuesta = true;
        try {
            GeograficoPoliticoMgl centro = geograficoPoliticoMglFacadeLocal.findById(idGeoPol);
            respuesta = "1".equals(centro.getGpoMultiorigen());
        } catch (ApplicationException e) {
            LOGGER.error("Error en isMultiorigen. No se pudo determinar multiorigen: " + e.getMessage());
        }

        return respuesta;
    }

    public String obtenerReglaTipoVivienda(String apartamento) {
        DireccionRRManager DireccionRRManager = new DireccionRRManager(true);
        return DireccionRRManager.obtenerTipoEdificio(apartamento, usuarioVT, null);
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public CmtValidadorDireccionesFacadeLocal getCmtValidadorDireccionesFacadeLocal() {
        return cmtValidadorDireccionesFacadeLocal;
    }

    public void setCmtValidadorDireccionesFacadeLocal(CmtValidadorDireccionesFacadeLocal cmtValidadorDireccionesFacadeLocal) {
        this.cmtValidadorDireccionesFacadeLocal = cmtValidadorDireccionesFacadeLocal;
    }

    public List<CmtBasicaMgl> getListadoEstratos() {
        return listadoEstratos;
    }

    public void setListadoEstratos(List<CmtBasicaMgl> listadoEstratos) {
        this.listadoEstratos = listadoEstratos;
    }

    public String getCambioEstratoMasivo() {
        return cambioEstratoMasivo;
    }

    public void setCambioEstratoMasivo(String cambioEstratoMasivo) {
        this.cambioEstratoMasivo = cambioEstratoMasivo;
    }

    public CmtFiltroPrefichasDto getFiltrosPrefichaEdificios() {
        return filtrosPrefichaEdificios;
    }

    public void setFiltrosPrefichaEdificios(CmtFiltroPrefichasDto filtrosPrefichaEdificios) {
        this.filtrosPrefichaEdificios = filtrosPrefichaEdificios;
    }

    public CmtFiltroPrefichasDto getFiltrosPrefichaCasas() {
        return filtrosPrefichaCasas;
    }

    public void setFiltrosPrefichaCasas(CmtFiltroPrefichasDto filtrosPrefichaCasas) {
        this.filtrosPrefichaCasas = filtrosPrefichaCasas;
    }

    public CmtFiltroPrefichasDto getFiltrosPrefichaConjuntoCasas() {
        return filtrosPrefichaConjuntoCasas;
    }

    public void setFiltrosPrefichaConjuntoCasas(CmtFiltroPrefichasDto filtrosPrefichaConjuntoCasas) {
        this.filtrosPrefichaConjuntoCasas = filtrosPrefichaConjuntoCasas;
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

    public List<TipoHhppMgl> getListaTipoHhpp() {
        return listaTipoHhpp;
    }

    public void setListaTipoHhpp(List<TipoHhppMgl> listaTipoHhpp) {
        this.listaTipoHhpp = listaTipoHhpp;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public boolean isHayFichasSeleccionadas() {
        return hayFichasSeleccionadas;
    }

    public void setHayFichasSeleccionadas(boolean hayFichasSeleccionadas) {
        this.hayFichasSeleccionadas = hayFichasSeleccionadas;
    }

    public boolean isEliminarTodoSelected() {
        return eliminarTodoSelected;
    }

    public void setEliminarTodoSelected(boolean eliminarTodoSelected) {
        this.eliminarTodoSelected = eliminarTodoSelected;
    }

    public boolean isRegistrosFiltrados() {
        return registrosFiltrados;
    }

    public void setRegistrosFiltrados(boolean registrosFiltrados) {
        this.registrosFiltrados = registrosFiltrados;
    }

    public boolean isFiltroAplicado() {
        return filtroAplicado;
    }

    public void setFiltroAplicado(boolean filtroAplicado) {
        this.filtroAplicado = filtroAplicado;
    }

    public boolean isRangoFechasValido() {
        return rangoFechasValido;
    }

    public void setRangoFechasValido(boolean rangoFechasValido) {
        this.rangoFechasValido = rangoFechasValido;
    }

    public String getDireccionFicha() {
        return direccionFicha;
    }

    public void setDireccionFicha(String direccionFicha) {
        this.direccionFicha = direccionFicha;
    }

    public boolean isBotonError() {
        return botonError;
    }

    public void setBotonError(boolean botonError) {
        this.botonError = botonError;
    }

    public boolean isDirOk() {
        return dirOk;
    }

    public void setDirOk(boolean dirOk) {
        this.dirOk = dirOk;
    }

    public PreFichaXlsMgl getPrefichaSelected() {
        return prefichaSelected;
    }

    public void setPrefichaSelected(PreFichaXlsMgl prefichaSelected) {
        this.prefichaSelected = prefichaSelected;
    }

    public boolean isBotonOk() {
        return botonOk;
    }

    public void setBotonOk(boolean botonOk) {
        this.botonOk = botonOk;
    }
    
    
    
     //Opciones agregadas para Rol
    private final String CRGINFBTN = "CRGINFBTN";
    private final String ELIBTNFIC = "ELIBTNFIC";
    
    public boolean validarOpcionCargarInfo() {
        return validarEdicionRol(CRGINFBTN);
    }
    
    public boolean validarOpcionEliminar() {
        return validarEdicionRol(ELIBTNFIC);
    }
    
    //funcion General
      private boolean validarEdicionRol(String formulario) {
        try {
            return ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
        }catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

}
