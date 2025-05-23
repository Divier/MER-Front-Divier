package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mer.utils.FileToolUtils;
import co.com.claro.mer.utils.StringToolUtils;
import co.com.claro.mer.utils.enums.DelimitadorEnum;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.dtos.CmtDireccionDetalladaMglDto;
import co.com.claro.mgl.dtos.CmtNodoValidado;
import co.com.claro.mgl.dtos.FiltroConsultaCompaniasDto;
import co.com.claro.mgl.dtos.NodoDto;
import co.com.claro.mgl.dtos.ResponseValidarDireccionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.error.ApplicationExceptionCM;
import co.com.claro.mgl.facade.DrDireccionFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosCallesFacadeLocal;
import co.com.claro.mgl.facade.SolicitudFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtArchivosVtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCompaniaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCuentaMatrizMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionSolicitudMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtInfoTecnicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtNodosSolicitudMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtNotasSolicitudMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSolicitudCmMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSolicitudSubEdificioMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTecnologiaSubMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtUnidadesPreviasMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtValidadorDireccionesFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.UbicacionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtArchivosVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtInfoTecnicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNodosSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtUnidadesPreviasMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ParametrosMerUtil;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.services.comun.Utilidades;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author ADMIN
 */
@ManagedBean(name = "infoCreaCMBean")
@ViewScoped
public class InfoCreaCMBean implements Serializable {

    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal basicasMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal tipoBasicasMglFacadeLocal;
    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;
    @EJB
    private CmtCompaniaMglFacadeLocal cmtCompaniaMglFacadeLocal;
    @EJB
    private CmtDireccionSolicitudMglFacadeLocal cmtDireccionSolicitudMglFacadeLocal;
    @EJB
    private CmtSolicitudCmMglFacadeLocal cmtSolicitudCmMglFacadeLocal;
    @EJB
    private CmtSolicitudSubEdificioMglFacadeLocal cmtSolicitudSubEdificioMglFacade;
    @EJB
    private CmtNotasSolicitudMglFacadeLocal cmtNotasSolicitudMglFacadeLocal;
    @EJB
    private DrDireccionFacadeLocal drDireccionMglFacadeLocal;
    @EJB
    private CmtValidadorDireccionesFacadeLocal direccionesFacadeLocal;
    @EJB
    private CmtUnidadesPreviasMglFacadeLocal previasMglFacadeLocal;
    @EJB
    private ParametrosCallesFacadeLocal parametrosCallesFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private UsuarioServicesFacadeLocal usuarioServicesFacade;
    @EJB
    private HhppMglFacadeLocal hhppMglFacadeLocal;
    @EJB
    private CmtNodosSolicitudMglFacadeLocal cmtNodosSolicitudMglFacadeLocal;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cuentaMatrizFacadeLocal;
    @EJB
    private CmtArchivosVtMglFacadeLocal archivosVtMglFacadeLocal;
    @EJB
    private SolicitudFacadeLocal solicitudFacadeLocal;
    @EJB
    private CmtInfoTecnicaMglFacadeLocal cmtInfoTecnicaMglFacadeLocal;
    @EJB
    private CmtTecnologiaSubMglFacadeLocal tecnologiaSubMglFacadeLocal;

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(InfoCreaCMBean.class);
    private final String FORMULARIO = "INFOGESTIONCREACM";
    private EncabezadoSolicitudModificacionCMBean modificacionCMBean;
    public List<GeograficoPoliticoMgl> departamentoList;
    public List<GeograficoPoliticoMgl> ciudadList;
    public List<GeograficoPoliticoMgl> centroPobladoList;
    private BigDecimal departamento = null;
    private BigDecimal ciudad = null;
    public BigDecimal centroProblado = null;
    private CmtSolicitudCmMgl cmtSolicitudCmMgl;
    private String usuarioVT = null;
    private int perfilVT = 0;
    private String cedulaUsuarioVT = null;
    private CmtDireccionSolicitudMgl cmtDireccionSolictudMgl = new CmtDireccionSolicitudMgl();
    private String serverHost;
    private String direccion;
    private String direccionAntigua;
    private boolean direccionValidada = false;
    private ValidadorDireccionBean validadorDireccionBean;
    private DrDireccion drDireccion;
    private ResponseValidarDireccionDto responseValidarDireccionDto;
    private List<String> barrioslist;
    private String selectedBarrio;
    private CmtSolicitudSubEdificioMgl solicitudSubEdificioMgl = new CmtSolicitudSubEdificioMgl();
    private List<CmtBasicaMgl> basicasMglTipoEificioList;
    private List<CmtBasicaMgl> basicasMglOrigenSolictudList;
    private String nododelBean;
    private String nodoCercanoDelBean;
    private List<CmtCompaniaMgl> listcompaniaAscensores;
    private List<CmtCompaniaMgl> listcompaniaConstructoras;
    private List<CmtCompaniaMgl> listcompaniaAdministracion;
    private CmtNotasSolicitudMgl cmtNotasSolicitudMgl;
    private CmtNotasSolicitudMgl cmtNotasSolicitudMglGestion;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private boolean otroBarrio = false;
    private HashMap<String, NodoDto> listaNodosCercanos;
    private AddressService addressService = new AddressService();
    private String primerNodoCercano;
    private List<String> listaNodosCobertura = new ArrayList<>();
    private List<NodoDto> listaNodosCercanosArray = new ArrayList<>();
    private List<CmtBasicaMgl> listEstratosBasica;
    private List<CmtTipoBasicaMgl> listaDeAcciones;
    private List<CmtBasicaMgl> listResultadosGestion;
    private List<CmtUnidadesPreviasMgl> unidadesDeLadireccion;
    private DireccionRREntity direccionRRentity;
    private List<ParametrosCalles> listTipoNivel5;
    private List<ParametrosCalles> listTipoNivel6;
    private boolean mostrarLinkOtraDireccion = false;
    private CmtCuentaMatrizMgl cmtCuentaMatrizMglOtraDireccion = null;
    private UploadedFile uploadedFile;
    private String urlArchivoSoporte = "";
    private Date fechaEntrega = null;
    private SecurityLogin securityLogin;
    private List<CmtBasicaMgl> listTablaBasicaTecnologias;
    private boolean habilitarCombo;
    
    private final String TECNOLOGIA_DTH = "DTH";
    private final String TECNOLOGIA_FTTH = "FTTH";
    private final String TECNOLOGIA_FOG = "FOG";
    private final String TECNOLOGIA_FOU = "FOU";
    private final String TECNOLOGIA_BI = "BI";
    private final String TECNOLOGIA_UNI = "UNI";
    private final String TECNOLOGIA_MOV  = "MOV";
    private final String TECNOLOGIA_LTE  = "LTE";
    
    private final String VALIDARDIRSOLCM = "VALIDARDIRSOLCM";
    
    private UsuariosServicesDTO usuarioSolicitudCm;
    private UsuariosServicesDTO usuarioGestSolicitudCm;
    private Map<CmtBasicaMgl, NodoMgl> datosGestionFinal;
    private EncabezadoSolicitudModificacionCMBean encabezadoSolModiCMBean;
    private boolean procesoterminado;
    private boolean validaDireccion;
    private UsuariosServicesDTO usuarioLogin;
    public BigDecimal tecnologia = null;
    private BigDecimal idNodoCercano;
    private Map<CmtBasicaMgl, NodoMgl> datosGestion;
    private Map<Long, Boolean> checked = new HashMap<>();
    private boolean selected;
    private CmtCuentaMatrizMgl cuentaMatriz;
    private List<String> tecnologiasCables = new ArrayList<>();
    private BigDecimal basicaIdEstrato;
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private CmtDireccionDetalladaMglDto direccionDetalladaMgl;

    public enum Presentar implements Serializable {

        LLENAR_DIRRECCION("LlenarDireccion"),
        LLENAR_DATOS_SOLICITUD("LlenarDatosSolicitud");
        private final String view;

        private Presentar(String view) {
            this.view = view;
        }

        public String getView() {
            return view;
        }
    }
    Presentar vistas = Presentar.LLENAR_DIRRECCION;

    public InfoCreaCMBean() {
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
            cedulaUsuarioVT = securityLogin.getIdUser();
            modificacionCMBean = JSFUtil.getSessionBean(EncabezadoSolicitudModificacionCMBean.class);
            modificacionCMBean.setShowHorario(false);
            cmtSolicitudCmMgl = modificacionCMBean.getSolicitudModCM();
            validadorDireccionBean = JSFUtil.getSessionBean(ValidadorDireccionBean.class);
            validadorDireccionBean.setMostrarPopupSub(false);
            validadorDireccionBean.limpiarCamposTipoDireccion();

        } catch (IOException e) {
            String msg = "Error al iniciar el formulario de solicitud de creaci&oacute;n CM:..." + e.getMessage();
            FacesUtil.mostrarMensajeError(msg + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     *
     */
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        encabezadoSolModiCMBean = JSFUtil.getSessionBean(EncabezadoSolicitudModificacionCMBean.class);
        try {
            datosGestion = new HashMap<>();
            selected = false;
            datosGestionFinal = new HashMap<>();
            CmtTipoBasicaMgl tipoBasicaTipoProyecto;
            tipoBasicaTipoProyecto = tipoBasicasMglFacadeLocal.findByCodigoInternoApp(
                    co.com.claro.mgl.utils.Constant.TIPO_BASICA_TECNOLOGIA);
            listTablaBasicaTecnologias = basicasMglFacadeLocal.
                    findByTipoBasica(tipoBasicaTipoProyecto);

            cmtSolicitudCmMglFacadeLocal.setUser(usuarioVT, perfilVT);
            cmtSolicitudSubEdificioMglFacade.setUser(usuarioVT, perfilVT);
            cmtDireccionSolicitudMglFacadeLocal.setUser(usuarioVT, perfilVT);
            direccionesFacadeLocal.setUser(usuarioVT, perfilVT);
            previasMglFacadeLocal.setUser(usuarioVT, perfilVT);
            serverHost = Utilidades.queryParametrosConfig(co.com.telmex.catastro.services.util.Constant.HOST_REDIR_VT);
            CmtTipoBasicaMgl basicaTipoMgl;
            basicaTipoMgl = tipoBasicasMglFacadeLocal.findByCodigoInternoApp(
                    co.com.claro.mgl.utils.Constant.TIPO_BASICA_TIPO_CUENTA_MATRIZ);
            basicasMglTipoEificioList = basicasMglFacadeLocal.findByTipoBasica(basicaTipoMgl);

            basicaTipoMgl = tipoBasicasMglFacadeLocal.findByCodigoInternoApp(
                    co.com.claro.mgl.utils.Constant.TIPO_BASICA_ORIGEN_SOLICITUD);
            basicasMglOrigenSolictudList = basicasMglFacadeLocal.findByTipoBasica(basicaTipoMgl);

            departamentoList = geograficoPoliticoMglFacadeLocal.findDptos();
            
            //Valido direccion capturada en el modulo de  factibilidades
            if (session.getAttribute("direccionDeta") != null) {
                direccionDetalladaMgl = (CmtDireccionDetalladaMglDto) session.getAttribute("direccionDeta");
                DireccionMgl direccionM = direccionDetalladaMgl.getDireccionMgl();
                UbicacionMgl ubicacionMgl = direccionM.getUbicacion();
                centroProblado = ubicacionMgl.getGpoIdObj().getGpoId();
                if (ubicacionMgl.getGpoIdObj().getGeoGpoId() != null) {
                    GeograficoPoliticoMgl city = geograficoPoliticoMglFacadeLocal.
                            findById(ubicacionMgl.getGpoIdObj().getGeoGpoId());
                    ciudad = city.getGpoId();
                    if (city.getGeoGpoId() != null) {
                        GeograficoPoliticoMgl dep = geograficoPoliticoMglFacadeLocal.
                                findById(city.getGeoGpoId());
                        departamento = dep.getGpoId();
                        consultarCiudades();
                        consultarCentroPoblado();
                        obtenerCentroPoblado();
                    }
                }
                direccion = direccionM.getDirNostandar();
                session.removeAttribute("direccionDeta");
            }

            if (cmtSolicitudCmMgl != null) {
                if (cmtSolicitudCmMgl.getSolicitudCmId() == null) {
                    solicitudSubEdificioMgl.setTipoEdificioObj(new CmtBasicaMgl());
                    cmtSolicitudCmMgl.setOrigenSolicitud(new CmtBasicaMgl());
                    solicitudSubEdificioMgl.setCompaniaAdministracionObj(new CmtCompaniaMgl());
                    solicitudSubEdificioMgl.setCompaniaConstructoraObj(new CmtCompaniaMgl());
                    solicitudSubEdificioMgl.setCompaniaAscensorObj(new CmtCompaniaMgl());
                    cmtSolicitudCmMgl.setNotasSolicitudList(new ArrayList<>());
                    cmtNotasSolicitudMgl = new CmtNotasSolicitudMgl();
                    cmtNotasSolicitudMgl.setTipoNotaObj(basicasMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_NOTA_CREACION_CUENTA_MATRIZ));
                    modificacionCMBean.setTipoNotaSelected(new CmtBasicaMgl());
                    usuarioSolicitudCm = usuarioServicesFacade.consultaInfoUserPorUsuario(usuarioVT);
                } else {
                    if (cmtSolicitudCmMgl.getEstadoSolicitudObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SOLICITUD_FINALIZADO)) {
                        modificacionCMBean.setGestionado(true);
                    }
                    procesoterminado = false;
                    if (cmtSolicitudCmMgl.getUsuarioSolicitudId() != null) {
                        usuarioSolicitudCm = usuarioServicesFacade.consultaInfoUserPorCedula(cmtSolicitudCmMgl.getUsuarioSolicitudId().toString());
                    }
                    usuarioGestSolicitudCm = usuarioServicesFacade.consultaInfoUserPorUsuario(usuarioVT);
                    FiltroConsultaCompaniasDto filtroConsultaCompaniasDto = new FiltroConsultaCompaniasDto();
                    filtroConsultaCompaniasDto.setEstado(ConstantsCM.REGISTRO_ACTIVO_VALUE);
                    filtroConsultaCompaniasDto.setMunicipio(cmtSolicitudCmMgl.getCiudadGpo().getGpoId());
                    filtroConsultaCompaniasDto.setTipoCompania(ConstantsCM.TIPO_COMPANIA_ADMINISTRACION);
                    listcompaniaAdministracion = cmtCompaniaMglFacadeLocal.findByfiltro(filtroConsultaCompaniasDto,false);
                    filtroConsultaCompaniasDto.setTipoCompania(ConstantsCM.TIPO_COMPANIA_ASCENSORES);
                    listcompaniaAscensores = cmtCompaniaMglFacadeLocal.findByfiltro(filtroConsultaCompaniasDto,false);
                    filtroConsultaCompaniasDto.setTipoCompania(ConstantsCM.TIPO_COMPANIA_CONSTRUCTORAS);
                    listcompaniaConstructoras = cmtCompaniaMglFacadeLocal.findByfiltro(filtroConsultaCompaniasDto,false);
                    vistas = Presentar.LLENAR_DATOS_SOLICITUD;
                    if (cmtSolicitudCmMgl.getListCmtSolicitudSubEdificioMgl() != null && !cmtSolicitudCmMgl.getListCmtSolicitudSubEdificioMgl().isEmpty()) {
                        solicitudSubEdificioMgl = cmtSolicitudCmMgl.getListCmtSolicitudSubEdificioMgl().get(0);
                    }
                    if (cmtSolicitudCmMgl.getListCmtDireccionesMgl() != null && !cmtSolicitudCmMgl.getListCmtDireccionesMgl().isEmpty()) {
                        cmtDireccionSolictudMgl = cmtSolicitudCmMgl.getListCmtDireccionesMgl().get(0);
                    }
                    cmtNotasSolicitudMgl = cmtSolicitudCmMgl.getFirstNota();
                    drDireccion = new DrDireccion();
                    drDireccion = cmtDireccionSolictudMgl.getCamposDrDireccion();
                    direccion = drDireccionMglFacadeLocal.getDireccion(drDireccion);
                    if (cmtDireccionSolictudMgl.getBarrio() != null && !cmtDireccionSolictudMgl.getBarrio().isEmpty()) {
                        selectedBarrio = cmtDireccionSolictudMgl.getBarrio().toUpperCase();
                    }
                    if (modificacionCMBean.isModoGestion()) {
                        addressService = direccionesFacadeLocal.calcularCobertura(cmtDireccionSolictudMgl);
                        if (addressService.getNodoUno() != null
                                && !addressService.getNodoUno().isEmpty()) {
                            listaNodosCobertura.add("B:" + addressService.getNodoUno());
                        }
                        if (addressService.getNodoDos() != null
                                && !addressService.getNodoDos().isEmpty()) {
                            listaNodosCobertura.add("U:" + addressService.getNodoDos());
                        }
                        if (addressService.getNodoTres() != null
                                && !addressService.getNodoTres().isEmpty()) {
                            listaNodosCobertura.add("U:" + addressService.getNodoTres());
                        }
                        
                       establecerCheckAutomaticoATecnologias();



                        basicaTipoMgl = tipoBasicasMglFacadeLocal.findByCodigoInternoApp(
                                co.com.claro.mgl.utils.Constant.TIPO_BASICA_ESTRATO);
                        listEstratosBasica = basicasMglFacadeLocal.findByTipoBasica(basicaTipoMgl);

                        if (addressService.getLeveleconomic() != null
                                && !addressService.getLeveleconomic().isEmpty()) {

                            if (listEstratosBasica.size() > 0) {
                                for (CmtBasicaMgl basicaMgl : listEstratosBasica) {
                                    if (basicaMgl.getCodigoBasica().
                                            equalsIgnoreCase(addressService.getLeveleconomic())) {
                                        basicaIdEstrato = basicaMgl.getBasicaId();
                                        if (basicaIdEstrato != null) {
                                            solicitudSubEdificioMgl.setEstratoSubEdificioObj(
                                                    basicasMglFacadeLocal.findById(
                                                            basicaIdEstrato));
                                        }
                                    }
                                }
                            }
                        } else {
                            for (CmtBasicaMgl basicaMgl : listEstratosBasica) {
                                if (basicaMgl.getIdentificadorInternoApp().
                                        equalsIgnoreCase(Constant.ESTRATO_NG)) {
                                    basicaIdEstrato = basicaMgl.getBasicaId();
                                    if (basicaIdEstrato != null) {
                                        solicitudSubEdificioMgl.setEstratoSubEdificioObj(
                                                basicasMglFacadeLocal.findById(
                                                        basicaIdEstrato));
                                    }
                                }
                            }
                        }

                        if (addressService.getReliability() != null && !addressService.getReliability().isEmpty()) {
                            addressService.setReliability(addressService.getReliability() + "%");
                        } else {
                            addressService.setReliability("0%");
                        }

                        if (addressService.getReliabilityPlaca() != null && !addressService.getReliabilityPlaca().isEmpty()) {
                            addressService.setReliabilityPlaca(addressService.getReliabilityPlaca() + "%");
                        } else {
                            addressService.setReliabilityPlaca("0%");
                        }

                        if (cmtSolicitudCmMgl.getOrigenSolicitud().getIdentificadorInternoApp().
                                equals(Constant.BASICA_ORIGENDATOS_PYME)) {
                            solicitudSubEdificioMgl.setEstratoSubEdificioObj(
                                    basicasMglFacadeLocal.findEstratoByLevelGeo("0"));
                        }

                        listaDeAcciones = modificacionCMBean.getAccionList();
                        listTipoNivel5 = parametrosCallesFacadeLocal.findByTipo("DIR_NUM_APTO");
                        listTipoNivel6 = parametrosCallesFacadeLocal.findByTipo("DIR_NUM_APTOC");
                        unidadesDeLadireccion = cmtSolicitudCmMgl.getListUnidadesPreviasCm();
                        if (!modificacionCMBean.isGestionado()) {
                            cmtNotasSolicitudMglGestion = new CmtNotasSolicitudMgl();
                            cmtNotasSolicitudMglGestion.setTipoNotaObj(basicasMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_NOTA_CREACION_CUENTA_MATRIZ));
                            CmtTipoBasicaMgl accionGestion = new CmtTipoBasicaMgl();
                            CmtBasicaMgl accionRespuestaGestion = new CmtBasicaMgl();
                            accionRespuestaGestion.setTipoBasicaObj(accionGestion);
                            cmtSolicitudCmMgl.setResultGestion(accionRespuestaGestion);
                        } else {
                            listResultadosGestion = basicasMglFacadeLocal.findByTipoBasica(cmtSolicitudCmMgl.getResultGestion().getTipoBasicaObj());
                            cmtNotasSolicitudMglGestion = getCmtSolicitudCmMgl().getLastNota();
                        }
                        if (cmtSolicitudCmMgl.getEstadoSolicitudObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE)) {
                            if (cmtSolicitudCmMgl.getListUnidadesPreviasCm() == null
                                    || cmtSolicitudCmMgl.getListUnidadesPreviasCm().isEmpty()) {
                                unidadesDeLadireccion
                                        = direccionesFacadeLocal.unidadesDeLaDireccionMgl(cmtDireccionSolictudMgl);
                            }
                        }

                        if (cmtSolicitudCmMgl.getNombreArchivo() != null
                                && !cmtSolicitudCmMgl.getNombreArchivo().isEmpty()) {

                            CmtArchivosVtMgl archivosVtMgl = archivosVtMglFacadeLocal.
                                    findAllBySolId(cmtSolicitudCmMgl);
                            
                            String urlOriginal = archivosVtMgl.getRutaArchivo();

                            urlArchivoSoporte = " <a href=\"" + urlOriginal
                                    + "\"  target=\"blank\">" + archivosVtMgl.getNombreArchivo() + "</a>";
                        }
                    }
                }
                asignarNombreCobertura();//valbuenayf ajuste checkbox
            } else {
                String msg = "No se identificó ninguna solicitud para poder continuar.";
                LOGGER.error(msg);

                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msg, ""));
                context.renderResponse();
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera un error al momento de obtener la información de la CCMM: " + e.getMessage(), e, LOGGER);
            context.renderResponse();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera un error al momento de obtener la información de la CCMM: " + e.getMessage(), e, LOGGER);
            context.renderResponse();
        }
    }

    public void obtenerListaCiudades() {
        try {
            cmtSolicitudCmMgl.setComunidad("");
            direccionValidada = false;
            drDireccion = new DrDireccion();
            cmtDireccionSolictudMgl = new CmtDireccionSolicitudMgl();
            GeograficoPoliticoMgl centroPoblado = new GeograficoPoliticoMgl();
            cmtSolicitudCmMgl.setCentroPobladoGpo(centroPoblado);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }
    }
    
    
    
    /**
     *
     * @throws ApplicationException
     */
    public void consultarCiudades() {
        try {
            cmtSolicitudCmMgl.setDepartamentoGpo(geograficoPoliticoMglFacadeLocal.findById(departamento));
            ciudadList = geograficoPoliticoMglFacadeLocal.findCiudades(departamento);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    /**
     * Obtener la lista de Centros Poblados.
     *
     * @throws ApplicationException
     */
    public void consultarCentroPoblado()  {
        try {
            cmtSolicitudCmMgl.setCiudadGpo(geograficoPoliticoMglFacadeLocal.findById(ciudad));
            centroPobladoList = geograficoPoliticoMglFacadeLocal.findCentroPoblado(ciudad);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    public void obtenerTecnologia() {
        try {
            cmtSolicitudCmMgl.setBasicaIdTecnologia(basicasMglFacadeLocal.findById(tecnologia));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }
    }
    
             /**
     * Función que establece si se debe o no marcar la casilla de sincronizacion a rr
     *
     * @author Juan David Hernandez
     * @param tecnologia
     * @param habilitarRR
     * @return 
     */
     public boolean establecerCheckAutomaticoSincronizaRr(CmtBasicaMgl tecnologia, boolean habilitarRR) {
        boolean result = false;

        try {
            if (tecnologia.getIdentificadorInternoApp() != null
                    && !tecnologia.getIdentificadorInternoApp().isEmpty()) {
                /*si la tecnologia es la misma de la solicitud, 
                tiene cobertura del geo, RR esta prendido*/
                if (tecnologia.getIdentificadorInternoApp().equals(Constant.DTH)
                        && cmtSolicitudCmMgl != null && cmtSolicitudCmMgl.getBasicaIdTecnologia() != null
                        && cmtSolicitudCmMgl.getBasicaIdTecnologia().getIdentificadorInternoApp() != null
                        && cmtSolicitudCmMgl.getBasicaIdTecnologia().getIdentificadorInternoApp().equalsIgnoreCase(tecnologia.getIdentificadorInternoApp())
                        && addressService != null && addressService.getNodoDth() != null && !addressService.getNodoDth().isEmpty()
                        && habilitarRR) {
                    //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                    if (tecnologia.getListCmtBasicaExtMgl() != null
                            && !tecnologia.getListCmtBasicaExtMgl().isEmpty()) {

                        for (CmtBasicaExtMgl cmtBasicaExtMgl
                                : tecnologia.getListCmtBasicaExtMgl()) {
                            if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                    equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                    && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {
                                guardaNodoGestion(tecnologia);
                                return true;
                            }
                        }
                    }
                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.FIBRA_FTTTH)
                        && cmtSolicitudCmMgl != null && cmtSolicitudCmMgl.getBasicaIdTecnologia() != null
                        && cmtSolicitudCmMgl.getBasicaIdTecnologia().getIdentificadorInternoApp() != null
                        && cmtSolicitudCmMgl.getBasicaIdTecnologia()
                                .getIdentificadorInternoApp().equalsIgnoreCase(tecnologia.getIdentificadorInternoApp())
                        && addressService != null && addressService.getNodoFtth() != null && !addressService.getNodoFtth().isEmpty()
                        && habilitarRR) {
                    //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                    if (tecnologia.getListCmtBasicaExtMgl() != null
                            && !tecnologia.getListCmtBasicaExtMgl().isEmpty()) {

                        for (CmtBasicaExtMgl cmtBasicaExtMgl
                                : tecnologia.getListCmtBasicaExtMgl()) {
                            if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                    equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                    && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {
                                guardaNodoGestion(tecnologia);
                                return true;
                            }
                        }
                    }

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.HFC_BID)
                        && cmtSolicitudCmMgl != null && cmtSolicitudCmMgl.getBasicaIdTecnologia() != null
                        && cmtSolicitudCmMgl.getBasicaIdTecnologia().getIdentificadorInternoApp() != null
                        && cmtSolicitudCmMgl.getBasicaIdTecnologia()
                                .getIdentificadorInternoApp().equalsIgnoreCase(tecnologia.getIdentificadorInternoApp())
                        && addressService != null && addressService.getNodoUno() != null && !addressService.getNodoUno().isEmpty()
                        && habilitarRR) {
                    //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                    if (tecnologia.getListCmtBasicaExtMgl() != null
                            && !tecnologia.getListCmtBasicaExtMgl().isEmpty()) {

                        for (CmtBasicaExtMgl cmtBasicaExtMgl
                                : tecnologia.getListCmtBasicaExtMgl()) {
                            if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                    equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                    && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {
                                guardaNodoGestion(tecnologia);
                                return true;
                            }
                        }
                    }

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.HFC_UNI)
                        && cmtSolicitudCmMgl != null && cmtSolicitudCmMgl.getBasicaIdTecnologia() != null
                        && cmtSolicitudCmMgl.getBasicaIdTecnologia().getIdentificadorInternoApp() != null
                        && cmtSolicitudCmMgl.getBasicaIdTecnologia()
                                .getIdentificadorInternoApp().equalsIgnoreCase(tecnologia.getIdentificadorInternoApp())
                        && addressService != null && addressService.getNodoDos() != null && !addressService.getNodoDos().isEmpty()
                        && habilitarRR) {
                    //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                    if (tecnologia.getListCmtBasicaExtMgl() != null
                            && !tecnologia.getListCmtBasicaExtMgl().isEmpty()) {

                        for (CmtBasicaExtMgl cmtBasicaExtMgl
                                : tecnologia.getListCmtBasicaExtMgl()) {
                            if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                    equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                    && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {
                                guardaNodoGestion(tecnologia);
                                return true;
                            }
                        }
                    }

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.FIBRA_OP_GPON)
                        && cmtSolicitudCmMgl != null &&  cmtSolicitudCmMgl.getBasicaIdTecnologia() != null
                        &&  cmtSolicitudCmMgl.getBasicaIdTecnologia().getIdentificadorInternoApp() != null
                        &&  cmtSolicitudCmMgl.getBasicaIdTecnologia()
                                .getIdentificadorInternoApp().equalsIgnoreCase(tecnologia.getIdentificadorInternoApp())
                        && addressService != null && addressService.getNodoTres() != null && !addressService.getNodoTres().isEmpty()
                        && habilitarRR) {
                    //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                    if (tecnologia.getListCmtBasicaExtMgl() != null
                            && !tecnologia.getListCmtBasicaExtMgl().isEmpty()) {

                        for (CmtBasicaExtMgl cmtBasicaExtMgl
                                : tecnologia.getListCmtBasicaExtMgl()) {
                            if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                    equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                    && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {
                                guardaNodoGestion(tecnologia);
                                return true;
                            }
                        }
                    }

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.LTE_INTERNET)
                        && cmtSolicitudCmMgl != null &&  cmtSolicitudCmMgl.getBasicaIdTecnologia() != null
                        && cmtSolicitudCmMgl.getBasicaIdTecnologia().getIdentificadorInternoApp() != null
                        && cmtSolicitudCmMgl.getBasicaIdTecnologia()
                                .getIdentificadorInternoApp().equalsIgnoreCase(tecnologia.getIdentificadorInternoApp())
                        && addressService != null && addressService.getNodoWifi() != null && !addressService.getNodoWifi().isEmpty()
                        && habilitarRR) {
                    //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                    if (tecnologia.getListCmtBasicaExtMgl() != null
                            && !tecnologia.getListCmtBasicaExtMgl().isEmpty()) {

                        for (CmtBasicaExtMgl cmtBasicaExtMgl
                                : tecnologia.getListCmtBasicaExtMgl()) {
                            if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                    equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                    && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {
                                guardaNodoGestion(tecnologia);
                                return true;
                            }
                        }
                    }

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.BTS_MOVIL)
                        && cmtSolicitudCmMgl != null && cmtSolicitudCmMgl.getBasicaIdTecnologia() != null
                        && cmtSolicitudCmMgl.getBasicaIdTecnologia().getIdentificadorInternoApp() != null
                        && cmtSolicitudCmMgl.getBasicaIdTecnologia()
                                .getIdentificadorInternoApp().equalsIgnoreCase(tecnologia.getIdentificadorInternoApp())
                        && addressService != null && addressService.getNodoMovil() != null && !addressService.getNodoMovil().isEmpty()
                        && habilitarRR) {
                    //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                    if (tecnologia.getListCmtBasicaExtMgl() != null
                            && !tecnologia.getListCmtBasicaExtMgl().isEmpty()) {

                        for (CmtBasicaExtMgl cmtBasicaExtMgl
                                : tecnologia.getListCmtBasicaExtMgl()) {
                            if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                    equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                    && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {
                                guardaNodoGestion(tecnologia);
                                return true;
                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {            
            FacesUtil.mostrarMensajeError("Error en establecer selección de check automatico" + ex.getMessage(), ex, LOGGER);
            return result;
        }
        return result;

    }

         /**
     * Función que establece al listado de tecnologias si se debe o no marcar la casilla de sincronizacion a rr
     *
     * @author Juan David Hernandez
     */
    public void establecerCheckAutomaticoATecnologias() {

        try {
            boolean habilitarRR = false;
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                habilitarRR = true;
            }
            if (listTablaBasicaTecnologias != null && !listTablaBasicaTecnologias.isEmpty()) {
                for (CmtBasicaMgl tecnologiaBasica : listTablaBasicaTecnologias) {
                    if (habilitarRR) {
                        
                        tecnologiaBasica.setSincronizaRr(establecerCheckAutomaticoSincronizaRr(tecnologiaBasica, habilitarRR));
                        tecnologiaBasica.setNodoTecnologia(tecnologiaBasica.isSincronizaRr());
                        
                    //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                    if (tecnologiaBasica.getListCmtBasicaExtMgl() != null
                            && !tecnologiaBasica.getListCmtBasicaExtMgl().isEmpty()) {

                        for (CmtBasicaExtMgl cmtBasicaExtMgl
                                : tecnologiaBasica.getListCmtBasicaExtMgl()) {
                            if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                    equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                    && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {
                                // guardaNodoGestion(tecnologiaBasica);
                                 tecnologiaBasica.setSincronizaRr(true);
                            }
                        }
                    }
                        
                    }
                }
            }

        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en establecer selección de check automatico" + ex.getMessage(), ex, LOGGER);

        }
    }

    public void obtenerCentroPoblado()  {
        try {
            cmtSolicitudCmMgl.setCentroPobladoGpo(geograficoPoliticoMglFacadeLocal.findById(centroProblado));
            direccionValidada = false;
            drDireccion = new DrDireccion();
            cmtDireccionSolictudMgl = new CmtDireccionSolicitudMgl();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public String avisaCambiaDireccion() {
        mostrarLinkOtraDireccion = false;
        cmtCuentaMatrizMglOtraDireccion = null;
        drDireccion = new DrDireccion();
        cmtDireccionSolictudMgl = new CmtDireccionSolicitudMgl();
        direccionValidada = false;
        return null;
    }

    public String irPopUpDireccion() {
        try {
            if (cmtSolicitudCmMgl.getDepartamentoGpo() != null && cmtSolicitudCmMgl.getCiudadGpo() != null
                    && cmtSolicitudCmMgl.getCentroPobladoGpo() != null && cmtSolicitudCmMgl.getBasicaIdTecnologia().getBasicaId() != null) {

                 validaDireccion = false;
                drDireccion = new DrDireccion();
                validadorDireccionBean = (ValidadorDireccionBean) JSFUtil.getSessionBean(ValidadorDireccionBean.class);
                usuarioLogin = usuarioServicesFacade.consultaInfoUserPorUsuario(usuarioVT);
                validadorDireccionBean.setCiudadDelBean(geograficoPoliticoMglFacadeLocal.findById(cmtSolicitudCmMgl.getCiudadGpo()));
                validadorDireccionBean.setIdCentroPoblado(centroProblado);
                validadorDireccionBean.setUsuarioLogin(usuarioLogin);
                validadorDireccionBean.setTecnologia(cmtSolicitudCmMgl.getBasicaIdTecnologia());
                //JDHT
                String barrio = "";
                if(cmtSolicitudCmMgl != null && cmtSolicitudCmMgl.getCuentaMatrizObj() != null 
                        && cmtSolicitudCmMgl.getCuentaMatrizObj().getDireccionPrincipal() != null
                        && cmtSolicitudCmMgl.getCuentaMatrizObj().getDireccionPrincipal().getBarrio() != null
                        && !cmtSolicitudCmMgl.getCuentaMatrizObj().getDireccionPrincipal().getBarrio().isEmpty()){
                    barrio = cmtSolicitudCmMgl.getCuentaMatrizObj().getDireccionPrincipal().getBarrio();
                }
                validadorDireccionBean.validarDireccion(drDireccion, direccion, cmtSolicitudCmMgl.getCentroPobladoGpo(), barrio, this, InfoCreaCMBean.class, Constant.TIPO_VALIDACION_DIR_CM, Constant.DIFERENTE_MODIFICACION_CM);
            } else {
                String msn = "No se ha completado el Formulario , Existen campos en Blanco ";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            }

        } catch (ApplicationException e) {
           FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
           FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void recargar(ResponseValidarDireccionDto direccionDto) {
        //JDHT
        direccionValidada = direccionDto.isValidacionExitosa();

        responseValidarDireccionDto = direccionDto;
        drDireccion = responseValidarDireccionDto.getDrDireccion();
        direccion = responseValidarDireccionDto.getDireccion();
        
        //se asigna direccion respuesta del geo sin alteraciones para busqueda por avenidas        
        if(direccionDto != null && direccionDto.getDireccionRespuestaGeo() != null
                && !direccionDto.getDireccionRespuestaGeo().isEmpty()){
            if(drDireccion != null){
                drDireccion.setDireccionRespuestaGeo(direccionDto.getDireccionRespuestaGeo());
            }
        }
      
        direccionAntigua = responseValidarDireccionDto.getDireccionAntigua();
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
            selectedBarrio = responseValidarDireccionDto.getBarrioGeo().toUpperCase();
            drDireccion.setBarrio(responseValidarDireccionDto.getBarrioGeo().toUpperCase());
        }
        else {
            if (responseValidarDireccionDto.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("IT")
                    || responseValidarDireccionDto.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("BM")) {
                selectedBarrio = drDireccion.getBarrio();
            } 
        }
        cmtDireccionSolictudMgl.mapearCamposDrDireccion(drDireccion);
        cmtDireccionSolictudMgl.setCalleRr(responseValidarDireccionDto.getDireccionRREntity().getCalle());
        cmtDireccionSolictudMgl.setUnidadRr(responseValidarDireccionDto.getDireccionRREntity().getNumeroUnidad());
    }

    public String cambiaDatosSolicitud() {
        try {
            if (cmtSolicitudCmMgl.getCentroPobladoGpo()!= null 
                    && cmtSolicitudCmMgl.getCentroPobladoGpo() != null
                    && cmtSolicitudCmMgl.getCentroPobladoGpo().getGpoMultiorigen().equalsIgnoreCase("1")
                    && drDireccion.getIdTipoDireccion() != null
                    && drDireccion.getIdTipoDireccion().equalsIgnoreCase("CK")
                    && selectedBarrio != null && !selectedBarrio.isEmpty()) {
                cmtDireccionSolictudMgl.setBarrio(selectedBarrio.toUpperCase());
                drDireccion.setBarrio(selectedBarrio.toUpperCase());
            }

            cmtDireccionSolicitudMglFacadeLocal.siExistenSolictudesEnCurso(
                    drDireccion, cmtSolicitudCmMgl.getCentroPobladoGpo().getGpoId().toString());
            if (drDireccion.getIdTipoDireccion().equalsIgnoreCase("CK")
                    && direccionAntigua != null && !direccionAntigua.isEmpty()) {
                try {
                    DrDireccion drDireccionAntigua = direccionesFacadeLocal.convertirDireccionStringADrDireccion(direccionAntigua, cmtSolicitudCmMgl.getCentroPobladoGpo().getGpoId());
                    if (drDireccionAntigua != null 
                            && cmtSolicitudCmMgl.getCentroPobladoGpo().getGpoMultiorigen().equalsIgnoreCase("1")
                            && selectedBarrio != null && !selectedBarrio.isEmpty()) {
                        drDireccionAntigua.setBarrio(selectedBarrio.toUpperCase());
                    }
                    if (drDireccionAntigua != null) {
                        cmtDireccionSolicitudMglFacadeLocal.siExistenSolictudesEnCurso(drDireccionAntigua,
                                cmtSolicitudCmMgl.getCentroPobladoGpo().getGpoId().toString());
                    }
                } catch (ApplicationExceptionCM ex2) {
                    String msg = "Exite cuenta matriz N° <b>"
                            + ex2.getMatrizMgl().getNumeroCuenta().toString()
                            + "</b> con direcci&oacute;n antigua, debe solicitar la actualizaci&oacute;n a la nueva direcci&oacute;n"
                            + " en el siguiente formulario";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                    if (ex2.getMatrizMgl() != null) {
                        mostrarLinkOtraDireccion = true;
                        cmtCuentaMatrizMglOtraDireccion = ex2.getMatrizMgl();
                        return irAModificarDirCM();
                    }
                    return null;
                } catch (ApplicationException ex) {
                    String msg = "Error validando direcci&oacute;n antigua:..." + ex.getMessage();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                    LOGGER.error(msg);
                    return null;
                }
            }
            FiltroConsultaCompaniasDto filtroConsultaCompaniasDto = new FiltroConsultaCompaniasDto();
            filtroConsultaCompaniasDto.setEstado(ConstantsCM.REGISTRO_ACTIVO_VALUE);
            filtroConsultaCompaniasDto.setMunicipio(cmtSolicitudCmMgl.getCiudadGpo().getGpoId());
            filtroConsultaCompaniasDto.setTipoCompania(ConstantsCM.TIPO_COMPANIA_ADMINISTRACION);
            listcompaniaAdministracion = cmtCompaniaMglFacadeLocal.findByfiltro(filtroConsultaCompaniasDto,false);
            filtroConsultaCompaniasDto.setTipoCompania(ConstantsCM.TIPO_COMPANIA_ASCENSORES);
            listcompaniaAscensores = cmtCompaniaMglFacadeLocal.findByfiltro(filtroConsultaCompaniasDto,false);
            filtroConsultaCompaniasDto.setTipoCompania(ConstantsCM.TIPO_COMPANIA_CONSTRUCTORAS);
            listcompaniaConstructoras = cmtCompaniaMglFacadeLocal.findByfiltro(filtroConsultaCompaniasDto,false);
            direccionRRentity = null;
            if (cmtSolicitudCmMgl.getCiudadGpo().getGpoMultiorigen().equalsIgnoreCase("1")
                    && drDireccion.getIdTipoDireccion().equalsIgnoreCase("CK")
                    && selectedBarrio != null && !selectedBarrio.isEmpty()) {
                drDireccion.setBarrio(selectedBarrio.toUpperCase());
                String msg;
                boolean dirExiste = false;
                if (barrioslist != null && !barrioslist.isEmpty()) {
                    for (String bar : barrioslist) {
                        if (selectedBarrio.toUpperCase().trim().equalsIgnoreCase(bar.toUpperCase().trim())) {
                            dirExiste = true;
                        }
                    }
                }
                if (dirExiste) {
                    if (responseValidarDireccionDto.getAddressService().getExist().equalsIgnoreCase("true")) {
                        msg = "La direcci&oacute;n existe con el barrio asociado " + selectedBarrio;
                    } else {
                        msg = "La direcci&oacute;n no existe";
                    }
                } else {
                    msg = "La direcci&oacute;n no existe con el barrio asociado " + selectedBarrio;
                }
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                msg, ""));
            }
            
            direccionRRentity = direccionesFacadeLocal.convertirDrDireccionARR(drDireccion,
                    cmtSolicitudCmMgl.getCiudadGpo().getGpoMultiorigen());

            mostrarLinkOtraDireccion = false;
            cmtCuentaMatrizMglOtraDireccion = null;
            vistas = Presentar.LLENAR_DATOS_SOLICITUD;

        } catch (ApplicationExceptionCM ex) {
            String msg = ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
            LOGGER.error(msg);
            if (ex.getMatrizMgl() != null) {
                mostrarLinkOtraDireccion = true;
                cmtCuentaMatrizMglOtraDireccion = ex.getMatrizMgl();
            }    
            return null;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }
        
        return null;
        
    }

    public void irCuentaCMdir() {
        try {
            CuentaMatrizBean cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            cuentaMatrizBean.setCuentaMatriz(cmtCuentaMatrizMglOtraDireccion);
            ConsultaCuentasMatricesBean consultaCuentasMatricesBean = JSFUtil.getBean("consultaCuentasMatricesBean", ConsultaCuentasMatricesBean.class);
            consultaCuentasMatricesBean.tecnologiasCoberturaCuentaMatriz();
            cuentaMatriz = cuentaMatrizBean.getCuentaMatriz();
            try {
                tecnologiasCables = cuentaMatrizFacadeLocal.getTecnologiasInst(cuentaMatriz, true);
                cuentaMatriz.setCoberturasList(consultaCuentasMatricesBean.getTecnologiasList());
            } catch (ApplicationException ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                LOGGER.error(msg);
            }
            FacesUtil.navegarAPagina("/view/MGL/CM/tabs/general.jsf");
        } catch (ApplicationException e) {
            String msn = co.com.claro.mgl.utils.Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + msn + ": " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    public String irAModificarDirCM() {
        CuentaMatrizBean cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
        cuentaMatrizBean.setCuentaMatriz(cmtCuentaMatrizMglOtraDireccion);
        EncabezadoSolicitudModificacionCMBean encSolModCm = (EncabezadoSolicitudModificacionCMBean) JSFUtil.getSessionBean(EncabezadoSolicitudModificacionCMBean.class);
        return encSolModCm.ingresarSolicitudModificacionCm();
    }

    public String cambiaDatosDireccion() {
        vistas = Presentar.LLENAR_DIRRECCION;
        return null;
    }

    public String obtenerTipoEdificio() {
        try {
            if (solicitudSubEdificioMgl.getTipoEdificioObj().getBasicaId() != null) {
                solicitudSubEdificioMgl.setTipoEdificioObj(basicasMglFacadeLocal.findById(solicitudSubEdificioMgl.getTipoEdificioObj().getBasicaId()));
            }
        } catch (ApplicationException e) {
            String msg = "Error al buscar el tipo de edificio espec&iacute;fico:..." + e.getMessage();
            FacesUtil.mostrarMensajeError(msg + e.getMessage(), e, LOGGER);
            LOGGER.error(msg);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String obtenerConstructora() {
        try {
            if (solicitudSubEdificioMgl.getCompaniaConstructoraObj().getCompaniaId() != null) {
                solicitudSubEdificioMgl.setCompaniaConstructoraObj(cmtCompaniaMglFacadeLocal.findById(solicitudSubEdificioMgl.getCompaniaConstructoraObj().getCompaniaId()));
            }

        } catch (ApplicationException e) {
            String msg = "Error al buscar la compa&ntilde;&iacute;a constructora seleccionada:...";
            FacesUtil.mostrarMensajeError(msg + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }

        return null;
    }

    public String obtenerAdministracion() {
        try {
            if (solicitudSubEdificioMgl.getCompaniaAdministracionObj().getCompaniaId() != null) {
                solicitudSubEdificioMgl.setCompaniaAdministracionObj(
                        cmtCompaniaMglFacadeLocal.findById(
                                solicitudSubEdificioMgl.getCompaniaAdministracionObj().getCompaniaId()));
            }
        } catch (ApplicationException e) {
            String msg = "Error al buscar la compa&ntilde;&iacute;a Administracion seleccionada:...";
            FacesUtil.mostrarMensajeError(msg + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String obtenerAscensores() {
        try {
            if (solicitudSubEdificioMgl.getCompaniaAscensorObj().getCompaniaId() != null) {
                solicitudSubEdificioMgl.setCompaniaAscensorObj(
                        cmtCompaniaMglFacadeLocal.findById(
                                solicitudSubEdificioMgl.getCompaniaAscensorObj().getCompaniaId()));
            }
        } catch (ApplicationException e) {
            String msg = "Error al buscar la compa&ntilde;&iacute;a Ascensores seleccionada:..." ;
            FacesUtil.mostrarMensajeError(msg+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String obtenerOrigen() {
        try {
            if (cmtSolicitudCmMgl.getOrigenSolicitud().getBasicaId() != null) {
                cmtSolicitudCmMgl.setOrigenSolicitud(
                        basicasMglFacadeLocal.findById(
                                cmtSolicitudCmMgl.getOrigenSolicitud().getBasicaId()));
            }
        } catch (ApplicationException e) {
            String msg = "Error al buscar el origen seleccionado:...";
            FacesUtil.mostrarMensajeError(msg + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String obtenerEstrato() {
        try {
            if (solicitudSubEdificioMgl.getEstratoSubEdificioObj() != null && solicitudSubEdificioMgl.getEstratoSubEdificioObj().getBasicaId() != null) {
                solicitudSubEdificioMgl.setEstratoSubEdificioObj(
                        basicasMglFacadeLocal.findById(
                                basicaIdEstrato));
            }
        } catch (ApplicationException e) {
            String msg = "Error al buscar el Estrato seleccionado:..." ;
            FacesUtil.mostrarMensajeError(msg + e.getMessage(), e, LOGGER);
            LOGGER.error(msg);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String obtenerAccionGestion() {
        try {
            if (cmtSolicitudCmMgl.getResultGestion().getTipoBasicaObj().getTipoBasicaId() != null
                    && cmtSolicitudCmMgl.getResultGestion().getTipoBasicaObj().getTipoBasicaId().compareTo(BigDecimal.ZERO) != 0) {
                CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                basicaMgl.setTipoBasicaObj(
                        tipoBasicasMglFacadeLocal.findById(
                                cmtSolicitudCmMgl.getResultGestion().getTipoBasicaObj()));
                cmtSolicitudCmMgl.setResultGestion(basicaMgl);
                listResultadosGestion = basicasMglFacadeLocal.findByTipoBasica(basicaMgl.getTipoBasicaObj());
            } else {
                listResultadosGestion = new ArrayList<>();
            }

        } catch (ApplicationException e) {
            String msg = "Error al buscar la acci&oacute;n seleccionada:...";
            FacesUtil.mostrarMensajeError(msg + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String obtenerAccionResultadoGestion() {
        try {
            if (cmtSolicitudCmMgl.getResultGestion().getBasicaId() != null) {
                cmtSolicitudCmMgl.setResultGestion(
                        basicasMglFacadeLocal.findById(
                                cmtSolicitudCmMgl.getResultGestion().getBasicaId()));
            }
        } catch (ApplicationException e) {
            String msg = "Error al buscar la acci&oacute;n seleccionada:...";
            FacesUtil.mostrarMensajeError(msg + e.getMessage(), e, LOGGER);
            LOGGER.error(msg);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String crearSolicitud() {
        boolean ocurrioError = false;
        
        try {
            CmtNodoValidado cmtNodoValidado;
            if (cmtSolicitudCmMgl.getOrigenSolicitud() != null
                    && cmtSolicitudCmMgl.getOrigenSolicitud().getIdentificadorInternoApp().
                            equals(co.com.claro.mgl.utils.Constant.BASICA_ORIGEN_DE_SOLICTUD_CONSTRUCTORA)) {
                if (solicitudSubEdificioMgl.getCompaniaConstructoraObj() == null
                        || solicitudSubEdificioMgl.getCompaniaConstructoraObj().
                                getCompaniaId() == null
                        || solicitudSubEdificioMgl.getCompaniaConstructoraObj().
                                getCompaniaId().compareTo(BigDecimal.ZERO) == 0) {
                    String msg = "Debe seleccionar una Constructora";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                    ocurrioError = true;
                    return null;
                }
                if (fechaEntrega == null || fechaEntrega.toString().isEmpty()) {
                    String msg = "Debe Ingresar Fecha de Entrega";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                    ocurrioError = true;
                    return "";
                } else {
                    if (fechaEntrega.compareTo(new Date()) < 0) {
                        String msg = "La fecha de entrega debe ser mayor a hoy";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        msg, ""));
                        ocurrioError = true;
                        return "";

                    }
                    Calendar fechaSelected = new GregorianCalendar();
                    fechaSelected.setTime(fechaEntrega);
                    int dia = fechaSelected.get(Calendar.DAY_OF_MONTH);

                    if (dia != 1) {
                        String msg = "Fecha Entrega: solo el primero de cada mes";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msg, ""));
                        ocurrioError = true;
                        return "";
                    }
                    solicitudSubEdificioMgl.setFechaEntregaEdificio(fechaEntrega);
                }
            } else {
                if (fechaEntrega != null && !fechaEntrega.toString().isEmpty()) {
                    if (fechaEntrega.compareTo(new Date()) < 0) {
                        String msg = "La fecha de entrega debe ser mayor a hoy";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        msg, ""));
                        ocurrioError = true;
                        return "";

                    }
                    Calendar fechaSelected = new GregorianCalendar();
                    fechaSelected.setTime(fechaEntrega);
                    int dia = fechaSelected.get(Calendar.DAY_OF_MONTH);

                    if (dia != 1) {
                        String msg = "Fecha Entrega: solo el primero de cada mes";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msg, ""));
                        ocurrioError = true;
                        return "";
                    }
                    solicitudSubEdificioMgl.setFechaEntregaEdificio(fechaEntrega);
                }
            }

            if (solicitudSubEdificioMgl.getUnidades() == 0) {
                String msg = "El numero de HHPP no puede ser cero";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msg, ""));
                ocurrioError = true;
                return "";
            }

            if (nododelBean != null && !nododelBean.isEmpty()) {
                cmtNodoValidado = nodoMglFacadeLocal.validarNodo(nododelBean, cmtSolicitudCmMgl.getComunidad(), usuarioVT);
                if (cmtNodoValidado != null) {
                    solicitudSubEdificioMgl.setNodoObj(cmtNodoValidado.getNodoMgl());
                    if (cmtNodoValidado.getMessage() != null && !cmtNodoValidado.getMessage().isEmpty()) {
                        String msg = cmtNodoValidado.getMessage();
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                        msg, ""));
                    }

                } else {
                    String msg = "Error en la validaci&oacute;n del nodo";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                    ocurrioError = true;
                    return "";
                }
            }
            if (cmtNotasSolicitudMgl.getNota().length() > 4000) {
                String msg = "El comentario no puede ser mayor a 4000 caracteres";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msg, ""));
                ocurrioError = true;
                return "";
            }

            if (cmtNotasSolicitudMgl.getNota().length() > 4000) {
                String msg = "El comentario no puede ser mayor a 4000 caracteres";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msg, ""));
                ocurrioError = true;
                return "";
            }

            cmtSolicitudCmMgl.setListCmtSolicitudSubEdificioMgl(new ArrayList<>());
            int totalDireccion = cmtSolicitudCmMglFacadeLocal.validaLongitudDireccion(cmtSolicitudCmMgl, cmtDireccionSolictudMgl);
            if (totalDireccion > 50) {
                throw new ApplicationException(";  No se puede crear la solicitud, "
                        + "la longitud de la calle en formato RR, superará 50 caracteres cuando se agregen los subedificio(torres), "
                        + "verifique la direccion disminuyendo el tamaño de la calle de RR para que la longitud "
                        + "no supere  40 caracters");
            }
            
        
            
            cmtSolicitudCmMgl.setEstadoSolicitudObj(basicasMglFacadeLocal.findByCodigoInternoApp(
                    co.com.claro.mgl.utils.Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE));
            
            
            if (solicitudSubEdificioMgl.getNombreSubedificio() != null
                    && !solicitudSubEdificioMgl.getNombreSubedificio().isEmpty()
                    && solicitudSubEdificioMgl.getNombreSubedificio().length() <= 4) {
                String msg = "El nombre del edificio debe ser mayor de 4 caracteres";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        msg, ""));
                return "";
            }

            
            if (solicitudSubEdificioMgl.getNombreSubedificio() == null
                    || solicitudSubEdificioMgl.getNombreSubedificio().isEmpty()) {
                solicitudSubEdificioMgl.
                        setNombreSubedificio("N.N " + direccionRRentity.getCalle().toUpperCase() + " " 
                                + direccionRRentity.getNumeroUnidad().toUpperCase());
            }else{
               solicitudSubEdificioMgl.setNombreSubedificio(solicitudSubEdificioMgl.getNombreSubedificio().toUpperCase().replace("Ñ", "N")); 
            }
            
                
            cmtSolicitudCmMgl.setTempSolicitud(encabezadoSolModiCMBean.getTimeSol());
            cmtSolicitudCmMgl.setDisponibilidadGestion("1");
            cmtSolicitudCmMgl.setBasicaIdTecnologia(cmtSolicitudCmMgl.getBasicaIdTecnologia());
            
            if (cmtSolicitudCmMgl.getCorreoAsesor() != null
                    && !cmtSolicitudCmMgl.getCorreoAsesor().trim().isEmpty()) {
                if (!validarCorreo(cmtSolicitudCmMgl.getCorreoAsesor())) {
                    String msg = "El campo correo asesor no tiene el formato requerido";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return "";
                } else {
                    if (cmtSolicitudCmMgl.getCorreoAsesor() != null && !cmtSolicitudCmMgl.getCorreoAsesor().isEmpty()) {
                        if (!validarDominioCorreos(cmtSolicitudCmMgl.getCorreoAsesor())) {
                            String msg = "El campo asesor debe ser un correo con dominio telmexla.com, telmex.com.co, telmex.com, comcel.com.co o claro.com.co";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msg, ""));
                            ocurrioError = true;
                            return "";
                        }
                    }
                }
            }
    
            
            String destinatariosCopia = "";
            if (usuarioSolicitudCm != null && usuarioSolicitudCm.getEmail() != null
                    && !usuarioSolicitudCm.getEmail().isEmpty()) {
                // Se adiciona el email del usuario solicitante.
                destinatariosCopia = usuarioSolicitudCm.getEmail();
            }
            if (cmtSolicitudCmMgl.getCorreoCopiaSolicitud() != null
                    && !cmtSolicitudCmMgl.getCorreoCopiaSolicitud().isEmpty()) {

                if (!validarCorreo(cmtSolicitudCmMgl.getCorreoCopiaSolicitud())) {
                    String msg = "El campo copia a no tiene el formato requerido";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return "";
                } else {
                    if (validarDominioCorreos(cmtSolicitudCmMgl.getCorreoCopiaSolicitud())) {
                        // los destinatarios se separan por comma.
                        if (!destinatariosCopia.isEmpty()) {
                            destinatariosCopia += ",";
                        }
                        destinatariosCopia += cmtSolicitudCmMgl.getCorreoCopiaSolicitud();
                    } else {
                        String msg = "El campo copia a debe ser un correo con dominio telmexla.com, telmex.com.co, telmex.com, comcel.com.co o claro.com.co";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msg, ""));
                        ocurrioError = true;
                        return "";
                    }
                }
            }
            cmtSolicitudCmMgl.setCorreoCopiaSolicitud(destinatariosCopia);
            
            cmtSolicitudCmMglFacadeLocal.setUser(usuarioVT, perfilVT);
            
            if (solicitudSubEdificioMgl.getCompaniaConstructoraObj() == null) {
                solicitudSubEdificioMgl.setCompaniaConstructoraObj(null);
            } else {
                if (solicitudSubEdificioMgl.getCompaniaConstructoraObj().getCompaniaId() == null) {
                    solicitudSubEdificioMgl.setCompaniaConstructoraObj(null);
                }
            }

            if (solicitudSubEdificioMgl.getCompaniaAscensorObj() == null) {
                solicitudSubEdificioMgl.setCompaniaAscensorObj(null);
            } else {
                if (solicitudSubEdificioMgl.getCompaniaAscensorObj().getCompaniaId() == null) {
                    solicitudSubEdificioMgl.setCompaniaAscensorObj(null);
                }
            }
            
                if (solicitudSubEdificioMgl.getCompaniaAdministracionObj() == null) {
                solicitudSubEdificioMgl.setCompaniaAdministracionObj(null);
            } else {
                if (solicitudSubEdificioMgl.getCompaniaAdministracionObj().getCompaniaId() == null) {
                    solicitudSubEdificioMgl.setCompaniaAdministracionObj(null);
                }
            }
                
            cmtSolicitudCmMgl = cmtSolicitudCmMglFacadeLocal.crearSol(cmtSolicitudCmMgl);
            cargarArchivo();
            cmtSolicitudCmMgl = cmtSolicitudCmMglFacadeLocal.update(cmtSolicitudCmMgl);
            solicitudSubEdificioMgl.setEstadoRegistro(1);
            solicitudSubEdificioMgl.setSolicitudCMObj(cmtSolicitudCmMgl);
            cmtDireccionSolictudMgl.setEstadoRegistro(1);
            cmtDireccionSolictudMgl.setSolicitudCMObj(cmtSolicitudCmMgl);
            cmtNotasSolicitudMgl.setEstadoRegistro(1);
            cmtNotasSolicitudMgl.setSolicitudCm(cmtSolicitudCmMgl);
            solicitudSubEdificioMgl = cmtSolicitudSubEdificioMglFacade.crearSolSub(solicitudSubEdificioMgl);
            cmtDireccionSolictudMgl.setCalleRr(direccionRRentity.getCalle().toUpperCase());
            cmtDireccionSolictudMgl.setUnidadRr(direccionRRentity.getNumeroUnidad().toUpperCase());
            
            //Si es multiorigen
            if(cmtSolicitudCmMgl.getCiudadGpo() != null 
                    && cmtSolicitudCmMgl.getCiudadGpo().getGpoMultiorigen() != null
                    && !cmtSolicitudCmMgl.getCiudadGpo().getGpoMultiorigen().isEmpty()
                    && cmtSolicitudCmMgl.getCiudadGpo().getGpoMultiorigen().equalsIgnoreCase("1")
                    && selectedBarrio != null && !selectedBarrio.isEmpty()){
                cmtDireccionSolictudMgl.setBarrio(selectedBarrio.toUpperCase());
            }
            
            cmtDireccionSolictudMgl = cmtDireccionSolicitudMglFacadeLocal.crearDir(cmtDireccionSolictudMgl);

            if (!cmtNotasSolicitudMgl.getNota().isEmpty()) {
                cmtNotasSolicitudMgl = cmtNotasSolicitudMglFacadeLocal.crearNotSol(cmtNotasSolicitudMgl);
            }
            cmtSolicitudCmMgl.getListCmtSolicitudSubEdificioMgl().add(solicitudSubEdificioMgl);
            cmtSolicitudCmMgl.getListCmtDireccionesMgl().add(cmtDireccionSolictudMgl);

            if (!cmtNotasSolicitudMgl.getNota().isEmpty()) {
                cmtSolicitudCmMgl.getNotasSolicitudList().add(cmtNotasSolicitudMgl);
            }
            cmtSolicitudCmMgl = cmtSolicitudCmMglFacadeLocal.findById(cmtSolicitudCmMgl.getSolicitudCmId());
            modificacionCMBean.setSolicitudModCM(cmtSolicitudCmMgl);

            String msg = "Se crea con &eacute;xito la solicitud: <b>" + cmtSolicitudCmMgl.getSolicitudCmId().toString() + "</b>.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            msg, ""));

        } catch (ApplicationException e) {
            String msg = "Error al crear la solicitud: ";
            FacesUtil.mostrarMensajeError(msg + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        } finally {
            if (ocurrioError) {
                if (solicitudSubEdificioMgl.getCompaniaAdministracionObj() == null) {
                    solicitudSubEdificioMgl.setCompaniaAdministracionObj(new CmtCompaniaMgl());
                }
                if (solicitudSubEdificioMgl.getCompaniaConstructoraObj() == null) {
                    solicitudSubEdificioMgl.setCompaniaConstructoraObj(new CmtCompaniaMgl());
                }
                if (solicitudSubEdificioMgl.getCompaniaAscensorObj() == null) {
                    solicitudSubEdificioMgl.setCompaniaAscensorObj(new CmtCompaniaMgl());
                }
            }
        }
        return "";
    }

    private boolean validarArchivo() {
        if (uploadedFile != null && uploadedFile.getFileName() != null) {
            String filename = uploadedFile.getFileName();
            Pattern p = Pattern.compile("[a-zA-Z|\\d|.]*");
            Matcher matcher = p.matcher(filename);
            if (!matcher.matches()) {
                return false;
            }
            if (filename.length() > 120) {
                return false;
            }
        }
        return true;
    }

    public void cargarArchivo() {
        
        File archive = null;
        try {
            String usuario = usuarioVT;
            Date fecha = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
            String fechaN =  format.format(fecha);

            if (uploadedFile != null && uploadedFile.getFileName() != null) {

                if (uploadedFile.getFileName().length() <= 255) {
                    String fileName = fechaN;
                    fileName += FilenameUtils.getName(uploadedFile.getFileName());
                    File file = new File(System.getProperty("user.dir"));
                    String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
                    archive = File.createTempFile(fileName + "-", "." + extension, file);
                    escribirFichero(archive);
                    String nombreArchivo = "SOL_" + cmtSolicitudCmMgl.getSolicitudCmId().toString()
                            + "_" + fileName;
                    try {
                        cmtSolicitudCmMgl.setNombreArchivo(nombreArchivo);

                        String cargaArchivo = solicitudFacadeLocal.uploadArchivoCambioEstrato(archive, fileName);

                        if (cargaArchivo == null
                                || cargaArchivo.isEmpty()) {

                            String msg = "Error al subir el archivo";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            msg, ""));
                            cmtSolicitudCmMgl.setNombreArchivo(null);

                        } else {
                            String msg = "Archivo cargado correctamente";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            msg, ""));

                            CmtArchivosVtMgl cmtArchivosVtMgl = new CmtArchivosVtMgl();
                            cmtArchivosVtMgl.setSolicitudCmMgl(cmtSolicitudCmMgl);
                            cmtArchivosVtMgl.setRutaArchivo(cargaArchivo);
                            cmtArchivosVtMgl.setNombreArchivo(nombreArchivo);
                            cmtArchivosVtMgl = archivosVtMglFacadeLocal.create(cmtArchivosVtMgl, usuario, perfilVT);
                            if (cmtArchivosVtMgl.getIdArchivosVt() != null) {
                                LOGGER.info("Archivo almacenado en el repositorio");
                            } else {
                                LOGGER.info("Ocurrio un error al momento de guardar el archivo en el repositorio");
                            }

                        }
                        FileToolUtils.deleteFile(archive);
                    } catch (ApplicationException | FileNotFoundException | MalformedURLException e) {
                        String msg = "Error al crear la solicitud ocurrio un error "
                                + "al momento de subir el archivo a UCM: " + e.getMessage();
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        msg, ""));
                        cmtSolicitudCmMgl.setNombreArchivo(null);
                        LOGGER.error(msg, e);
                        if (archive != null) {
                            try {
                                Files.deleteIfExists(archive.toPath());
                            } catch (IOException ex) {
                                LOGGER.error(ex.getMessage(), ex);
                            }
                        }
                    }
                } else {
                    String msg = "El nombre del archivo excede el tamaño permitido que es 255 caracteres ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                }
            }
        } catch (IOException e) {
            if (archive != null) {
                try {
                    Files.deleteIfExists(archive.toPath());
                } catch (IOException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }
            }
            FacesUtil.mostrarMensajeError("Se genera error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            if (archive != null) {
                try {
                    Files.deleteIfExists(archive.toPath());
                } catch (IOException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }
            }
            FacesUtil.mostrarMensajeError("Se genera error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Escritura del fichero temporal
     *
     * @param archive {@link File}
     * @author Gildardo Mora
     */
    private void escribirFichero(File archive) throws IOException {
        try (FileOutputStream output = new FileOutputStream(archive)) {
            output.write(uploadedFile.getContent());
        } catch (IOException e) {
            String msgError = "Se genera error al escribir el fichero temporal";
            LOGGER.error(msgError, e);
            throw e;
        }
    }

    public String gestionarSolicitud() {
        CmtBasicaMgl estadoActualSol= null;
        try {
            //valbuenayf inicio ajuste chekbox 
            boolean nodoSeleccionado = false;
            boolean estadoSol = true;
            boolean esSolicitudGponUnifilar = false;
            boolean esSolicitudRedFo = false;   
 
            
            if(!validarNodoGponRedFOIngresado(listTablaBasicaTecnologias)){
                return "";
            }
            
            for (CmtBasicaMgl s : listTablaBasicaTecnologias) {
                if (s.isNodoTecnologia()) {
                    nodoSeleccionado = true;
                    break;
                }
            }
            if (cmtNotasSolicitudMglGestion.getNota() == null || cmtNotasSolicitudMglGestion.getNota().trim().isEmpty()) {
                String msg = "La nota es obligatoria para Gestionar la solicitud";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, ""));
                return null;
            }
            if (cmtSolicitudCmMgl.getCorreoCopiaGestion() != null
                    && !cmtSolicitudCmMgl.getCorreoCopiaGestion().trim().isEmpty()) {
                if (!validarCorreo(cmtSolicitudCmMgl.getCorreoCopiaGestion())) {
                    String msg = "El campo copia no tiene el formato requerido";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return null;
                } else {
                    if (cmtSolicitudCmMgl.getCorreoCopiaGestion() != null && !cmtSolicitudCmMgl.getCorreoCopiaGestion().isEmpty()) {
                        if (!validarDominioCorreos(cmtSolicitudCmMgl.getCorreoCopiaGestion())) {
                            String msg = "El campo copia debe ser un correo con dominio telmexla.com, telmex.com.co, telmex.com, comcel.com.co o claro.com.co";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msg, ""));
                            return "";
                        }
                    }
                }
            }
    
            
            if (cmtSolicitudCmMgl != null && cmtSolicitudCmMgl.getResultGestion().getBasicaId() == null) {
                String msg = "El campo Resultado de la gesti&oacute;n es obligatorio";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        msg, ""));
                return null;
            }
            if (cmtSolicitudCmMgl.getResultGestion().getTipoBasicaObj().
                    getTipoBasicaId().compareTo(cmtTipoBasicaMglFacadeLocal.
                            findByCodigoInternoApp(
                                    co.com.claro.mgl.utils.Constant.TIPO_BASICA_ACCION_RECHAZAR_CUENTA_MATRIZ).getTipoBasicaId()) == 0) {
                estadoSol = false;

            } else if (cmtSolicitudCmMgl.getResultGestion().getTipoBasicaObj().
                    getTipoBasicaId().compareTo(cmtTipoBasicaMglFacadeLocal.
                            findByCodigoInternoApp(
                                    co.com.claro.mgl.utils.Constant.TIPO_BASICA_ACCION_ESCALAR_CUENTA_MATRIZ).getTipoBasicaId()) == 0) {
                estadoSol = false;
            }
            NodoMgl nodoXdefecto;
            CmtNodoValidado cmtNodoValidado;
            //Autor: Victor Bocanegra  
            //se cambia la validacion del nodo
            if (estadoSol) {
                if (datosGestion.size() > 0) {
                    if (!nodoSeleccionado) {
                        String msg = "Error: debe seleccionar un nodo para la sincronizaci&oacute;n con RR:  ";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, ""));
                        return null;
                    }
                    Iterator<Entry<CmtBasicaMgl, NodoMgl>> iterator = datosGestion.entrySet().iterator();
                    while (iterator.hasNext()) {

                        Entry<CmtBasicaMgl, NodoMgl> n = iterator.next();
                        if (!n.getValue().getNodCodigo().equalsIgnoreCase("")) {

                            NodoMgl nodoMgl = nodoMglFacadeLocal.findByCodigoNodo(n.getValue().getNodCodigo(), cmtSolicitudCmMgl.getCentroPobladoGpo().getGpoId(), n.getKey().getBasicaId());
                            cmtNodoValidado = new CmtNodoValidado();
                            cmtNodoValidado.setNodoMgl(nodoMgl);
                            if (cmtNodoValidado.getNodoMgl() == null) {

                                String msg = "Error en la validaci&oacute;n del nodo:  " + n.getValue().getNodCodigo() + "  "
                                        + "no se encuentra configurado para la "
                                        + "tecnolog&iacute;a:  " + n.getKey().getNombreBasica() + "";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                msg, ""));
                                iterator.remove();

                                return null;

                            } else {
                                if(n.getKey().getIdentificadorInternoApp().
                                        equalsIgnoreCase(Constant.FIBRA_OP_GPON)||
                                   n.getKey().getIdentificadorInternoApp().
                                        equalsIgnoreCase(Constant.FIBRA_OP_UNI)){
                                    esSolicitudGponUnifilar=true;
                                }
                                if (n.getKey().getIdentificadorInternoApp().
                                        equalsIgnoreCase(Constant.RED_FO)) {
                                    esSolicitudRedFo = true;
                                }
                                datosGestionFinal.put(n.getKey(), nodoMgl);
                            }
                        } else {
                            iterator.remove();
                        }
                    }
                }else{
                    String msg = "Error: Debe Seleccionar al menos un nodo  ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, ""));
                    return null;
                }                
                if (esSolicitudGponUnifilar && !esSolicitudRedFo) {
                    String msg = "Error en la validaci&oacute;n del nodo:  RED FO,"
                                        + " Cuando se digita Nodo Gpon o Unifilar es requerido Red FO";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, ""));
                    return null;

                }                
            }
            //valbuenayf fin ajuste chekbox
            if (cmtNotasSolicitudMglGestion.getNota().length() > 4000) {
                String msg = "El comentario no puede ser mayor a 4000 caracteres";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msg, ""));
                return null;
            }
            nodoXdefecto = nodoMglFacadeLocal.findByCodigo("ND");
            if (nodoXdefecto.getNodId() != null) {
                solicitudSubEdificioMgl.setNodoObj(nodoXdefecto);
            } else {
                String msg = "Error al momento de consultar el nodo"
                        + "por defecto";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msg, ""));
                return null;

            }
            solicitudSubEdificioMgl.setNodoObj(nodoXdefecto);
            //Guardo los nodos de gestion por tecnologia asociados a la solicitud
            List<CmtNodosSolicitudMgl> lstCmtNodosSolicitudMgl = cmtNodosSolicitudMglFacadeLocal.findBySolicitudId(cmtSolicitudCmMgl);
            if (lstCmtNodosSolicitudMgl != null && lstCmtNodosSolicitudMgl.size() > 0) {
                cmtNodosSolicitudMglFacadeLocal.update(lstCmtNodosSolicitudMgl, usuarioVT, perfilVT);
            }
            boolean respuesta = cmtNodosSolicitudMglFacadeLocal.create(datosGestionFinal, cmtSolicitudCmMgl, usuarioVT, perfilVT);
            if (respuesta) {
                LOGGER.info("Proceso exitoso: "
                        + "guardo los registros satisfactoriamente");
            } else {
                LOGGER.error("Proceso fallo: "
                        + "ocurrio un error al momento de crear los registros");
            }
            cmtSolicitudSubEdificioMglFacade.update(solicitudSubEdificioMgl);
            estadoActualSol = cmtSolicitudCmMgl.getEstadoSolicitudObj();
            cmtSolicitudCmMgl.getListCmtSolicitudSubEdificioMgl().clear();
            cmtSolicitudCmMgl.getListCmtSolicitudSubEdificioMgl().add(solicitudSubEdificioMgl);
            if (cmtSolicitudCmMgl.getResultGestion().getTipoBasicaObj().
                    getTipoBasicaId().compareTo(cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                            co.com.claro.mgl.utils.Constant.TIPO_BASICA_ACCION_CREAR_CUENTA_MATRIZ)
                            .getTipoBasicaId()) == 0) {
                CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                basicaMgl.setBasicaId(basicasMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_FINALIZADO).getBasicaId());
                basicaMgl = basicasMglFacadeLocal.findById(basicaMgl);
                cmtSolicitudCmMgl.setEstadoSolicitudObj(basicaMgl);
            } else if (cmtSolicitudCmMgl.getResultGestion().getTipoBasicaObj().
                    getTipoBasicaId().compareTo(cmtTipoBasicaMglFacadeLocal.
                            findByCodigoInternoApp(
                                    co.com.claro.mgl.utils.Constant.TIPO_BASICA_ACCION_RECHAZAR_CUENTA_MATRIZ).getTipoBasicaId()) == 0) {
                CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                basicaMgl.setBasicaId(basicasMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_FINALIZADO).getBasicaId());
                basicaMgl = basicasMglFacadeLocal.findById(basicaMgl);
                cmtSolicitudCmMgl.setEstadoSolicitudObj(basicaMgl);
            } else if (cmtSolicitudCmMgl.getResultGestion().getTipoBasicaObj().
                    getTipoBasicaId().compareTo(cmtTipoBasicaMglFacadeLocal.
                            findByCodigoInternoApp(
                                    co.com.claro.mgl.utils.Constant.TIPO_BASICA_ACCION_ESCALAR_CUENTA_MATRIZ).getTipoBasicaId()) == 0) {
                CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                basicaMgl.setBasicaId(basicasMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_COBERTURA).getBasicaId());
                basicaMgl = basicasMglFacadeLocal.findById(basicaMgl);
                cmtSolicitudCmMgl.setEstadoSolicitudObj(basicaMgl);
            }
            if (unidadesDeLadireccion != null && !unidadesDeLadireccion.isEmpty()) {
                if (unidadesDeLadireccion.get(0).getUnidadPreviaId() == null) {
                    for (CmtUnidadesPreviasMgl cupm : unidadesDeLadireccion) {
                        cupm.setSolicitudObj(cmtSolicitudCmMgl);
                    }
                    previasMglFacadeLocal.createList(unidadesDeLadireccion, usuarioVT, perfilVT);
                    cmtSolicitudCmMgl.setListUnidadesPreviasCm(unidadesDeLadireccion);
                }
            }
            cmtSolicitudCmMglFacadeLocal.updateSolicitudCreaCM(cmtSolicitudCmMgl, addressService,
                    datosGestionFinal, nodoXdefecto,false, usuarioVT, perfilVT);//cmtNodoValidado
            if (!cmtNotasSolicitudMglGestion.getNota().isEmpty()) {
                cmtNotasSolicitudMglGestion.setSolicitudCm(cmtSolicitudCmMgl);
                cmtNotasSolicitudMglGestion = cmtNotasSolicitudMglFacadeLocal.crearNotSol(cmtNotasSolicitudMglGestion);
            }
            if (!cmtNotasSolicitudMglGestion.getNota().isEmpty()) {
                cmtSolicitudCmMgl.getNotasSolicitudList().add(cmtNotasSolicitudMglGestion);
            }
            String estadoTecnologiaCM = cmtSolicitudCmMgl.getEstadoTecnoRR();
            cmtSolicitudCmMgl = cmtSolicitudCmMglFacadeLocal.findById(cmtSolicitudCmMgl.getSolicitudCmId());
            cmtSolicitudCmMgl.setEstadoTecnoRR(estadoTecnologiaCM);
            modificacionCMBean.setSolicitudModCM(cmtSolicitudCmMgl);
            modificacionCMBean.setGestionado(true);
            if (cmtSolicitudCmMgl.getResultGestion().
                    getTipoBasicaObj().getTipoBasicaId().
                    compareTo(cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                            co.com.claro.mgl.utils.Constant.TIPO_BASICA_ACCION_CREAR_CUENTA_MATRIZ)
                            .getTipoBasicaId()) == 0) {
                cmtSolicitudCmMglFacadeLocal.envioCorreoGestion(cmtSolicitudCmMgl);
                BigDecimal numeroCMRR = (cmtSolicitudCmMgl.getCuentaMatrizObj().getNumeroCuenta() != null 
                        && !cmtSolicitudCmMgl.getCuentaMatrizObj().getNumeroCuenta().equals(BigDecimal.ZERO)) 
                        ? cmtSolicitudCmMgl.getCuentaMatrizObj().getNumeroCuenta() : BigDecimal.ZERO;
                String mensajeEstCombinados = !cmtSolicitudCmMgl.getEstadoTecnoRR().isEmpty() ? cmtSolicitudCmMgl.getEstadoTecnoRR() : ".";
                //Crea registros de infotecnica
                crearInfoTecnica(datosGestion, cmtSolicitudCmMgl);
                String msg = "Se Gestion&oacute; la solicitud correctamente: <b>"
                        + cmtSolicitudCmMgl.getSolicitudCmId().toString()
                        + "</b>, La <i>cuenta matriz</i> generada es <b>"
                        + cmtSolicitudCmMgl.getListCmtSolicitudSubEdificioMgl().get(0).
                                getNombreSubedificio() + "</b> <i>N&uacute;mero de cuenta en RR</i>: <b>"
                        + numeroCMRR + "</b>." + " "
                        + "<i>N&uacute;mero de cuenta en MGL</i>:  <b>" + cmtSolicitudCmMgl.getCuentaMatrizObj().getCuentaMatrizId() + "</b>"
                        + " <b>" + mensajeEstCombinados + "</b>";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                msg, ""));
                procesoterminado = true;
            } else {
                String msg = "Se Actualiz&oacute; la solicitud correctamente: <b>"
                        + cmtSolicitudCmMgl.getSolicitudCmId().toString() + "</b>";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                msg, ""));
                procesoterminado = true;
            }
            cmtSolicitudCmMgl.setTempGestion(encabezadoSolModiCMBean.getTimeSol());
            cmtSolicitudCmMgl.setFechaGestion(new Date());
            cmtSolicitudCmMglFacadeLocal.updateCm(cmtSolicitudCmMgl);
        } catch (ApplicationException ex) {
            String msg = "Error al actualizar la solicitud: " + ex.getMessage();
            cmtSolicitudCmMgl.setEstadoSolicitudObj(estadoActualSol);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,msg, ""));
            LOGGER.error(msg, ex);
            procesoterminado = true;
        } catch (Exception ex) {
            String msg = "Error al actualizar la solicitud: " + ex.getMessage();
            cmtSolicitudCmMgl.setEstadoSolicitudObj(estadoActualSol);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,msg, ""));
            LOGGER.error(msg, ex);
            procesoterminado = true;
        }
        return null;
    }

    /**
     * valbuenayf metodo para validar correo
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
            LOGGER.error("Error en validarCorreo de InfoCreaCMBean: " + e);
        }
        return respuesta;
    }

    public String mostrarOtrobarrio() {
        otroBarrio = !otroBarrio;
        selectedBarrio = "";
        return null;
    }


    /**
     *
     * @return
     */
    public boolean validarGestion() {
        return validarAccion(ValidacionUtil.OPC_CREACION);
    }

    /**
     * M&eacute;todo para validar las acciones a realizar en el formulario
     *
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el
     * componente
     * @return boolean indicador para verificar si se visualizan o no los
     * componentes
     */
    private boolean validarAccion(String opcion) {
        try {
            return ValidacionUtil.validarVisualizacion(FORMULARIO, opcion, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(co.com.claro.mgl.utils.Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION, e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }
        return false;
    }


    /**
     * valbuenayf metodo para asignar el nombre de la cobertura
     */
    private void asignarNombreCobertura() {
        for (CmtBasicaMgl b : listTablaBasicaTecnologias) {
            b.setNodoCobertura(escribeNombreCobertura(b));
            b.setNodoGestion(b.getNodoCobertura());
            b.setSincronizaRr(sincronizaRr(b));
        }
    }

    /**
     * valbuenayf metodo para para saber si sincroniza con RR
     *
     * @param cmtBasicaMgl
     * @return
     */
    private boolean sincronizaRr(CmtBasicaMgl cmtBasicaMgl) {
        boolean respuesta = false;
        try {
            if (cmtBasicaMgl.getListCmtBasicaExtMgl() != null && !cmtBasicaMgl.getListCmtBasicaExtMgl().isEmpty()) {
                for (CmtBasicaExtMgl cmtBasicaExtMgl : cmtBasicaMgl.getListCmtBasicaExtMgl()) {
                    if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase(Constant.SINCRONIZA_RR)
                            && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {
                        respuesta = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            respuesta = false;
            return respuesta;
        }
        return respuesta;
    }

    public String escribeNombreCobertura(CmtBasicaMgl tecnologia) {

        String nodoCobertura = "";

        try {
            if (addressService != null) {
                switch (tecnologia.getCodigoBasica()) {
                    case TECNOLOGIA_DTH:
                        nodoCobertura = addressService.getNodoDth();
                        break;
                    case TECNOLOGIA_FTTH:
                        nodoCobertura = addressService.getNodoFtth();
                        break;
                    case TECNOLOGIA_BI:
                        nodoCobertura = addressService.getNodoUno();
                        break;
                    case TECNOLOGIA_UNI:
                        nodoCobertura = addressService.getNodoDos();
                        break;
                    case TECNOLOGIA_MOV:
                        nodoCobertura = addressService.getNodoMovil();
                        break;
                    case TECNOLOGIA_FOG:
                        nodoCobertura = addressService.getNodoTres();
                        break;
                    case TECNOLOGIA_LTE:
                        nodoCobertura = addressService.getNodoWifi();
                        break;
                    case TECNOLOGIA_FOU:
                        nodoCobertura = addressService.getNodoZonaUnifilar();
                        break;
                    default:
                        break;
                }
            }
        } catch (NullPointerException e) {
            nodoCobertura = "";
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            nodoCobertura = "";
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }

        if (!procesoterminado) {

            if (nodoCobertura == null || nodoCobertura.equals("")) {
                habilitarCombo = false;
            } else {
                habilitarCombo = true;
                try {
                    NodoMgl nodoMgl = nodoMglFacadeLocal.findByCodigo(nodoCobertura);//valbuenayf ajuste checkbox
                    if (nodoMgl != null) {
                        datosGestion.put(tecnologia, nodoMgl);
                    } else {
                        habilitarCombo = false;
                    }
                } catch (ApplicationException e) {
                    String msg = "Error al consultar nodos";
                      FacesUtil.mostrarMensajeError(msg + e.getMessage(), e, LOGGER);
                } catch (Exception e) {
                    FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
                }

            }
        }
        return nodoCobertura;
    }

    public List<NodoMgl> buscarNodoCercano(CmtBasicaMgl cmtBasicaMgl) {

        List<NodoMgl> lstNodosCercano = null;
        try {

            lstNodosCercano = hhppMglFacadeLocal.retornaNodosCercano(cmtDireccionSolictudMgl, cmtBasicaMgl, false);

        } catch (ApplicationException e) {
            String msg = "Error al consultar nodos cercanos"
                    + "asociados a una tecnolog&iacute;a";
            FacesUtil.mostrarMensajeError(msg+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }

        return lstNodosCercano;

    }

    public CmtBasicaMgl seleccionaNodoCercano(CmtBasicaMgl cmtBasicaMgl) {

        NodoMgl nodoMgl;
        try {
            if (idNodoCercano != null) {
                nodoMgl = nodoMglFacadeLocal.findById(idNodoCercano);
                datosGestion.put(cmtBasicaMgl, nodoMgl);
                cmtBasicaMgl.setNodoGestion(nodoMgl.getNodCodigo());
            } else {
                cmtBasicaMgl.setNodoGestion("");
                datosGestion.remove(cmtBasicaMgl);
            }
        } catch (ApplicationException e) {
            String msg = "Error al consultar nodos cercanos"
                    + "asociados a una tecnolog&iacute;a";
            FacesUtil.mostrarMensajeError(msg+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }
        return cmtBasicaMgl;

    }

    public void guardaNodoGestion(CmtBasicaMgl cmtBasicaMgl) {
        //valbuenayf inicio ajuste checkbox
        NodoMgl nodoMgl = new NodoMgl();
        nodoMgl.setNodCodigo(cmtBasicaMgl.getNodoGestion());
        nodoMgl.setSincronizaRr(cmtBasicaMgl.isNodoTecnologia());
        if (cmtBasicaMgl.getNodoGestion() != null && !cmtBasicaMgl.getNodoGestion().trim().isEmpty()) {
            datosGestion.put(cmtBasicaMgl, nodoMgl);
        } else {
            datosGestion.remove(cmtBasicaMgl);
        }
        //valbuenayf fin ajuste checkbox
    }

    /**
     * valbuenayf metodo para seleccionar el nodo que sincroniza con RR
     *
     * @param cmtBasicaMgl
     */
    public void seleccionarTecnologiaNodo(CmtBasicaMgl cmtBasicaMgl) {
        for (CmtBasicaMgl s : listTablaBasicaTecnologias) {
            if (s.getBasicaId().compareTo(cmtBasicaMgl.getBasicaId()) == 0) {
                s.setNodoTecnologia(cmtBasicaMgl.isNodoTecnologia());
            } else {
                s.setNodoTecnologia(false);
            }
            guardaNodoGestion(s);
        }

    }
    
    public boolean validarDominioCorreos(String correoCopia){

        if (correoCopia != null && !correoCopia.isEmpty()){
            if (correoCopia.toLowerCase().contains("claro.com.co")
                    || correoCopia.toLowerCase().contains("telmex.com.co")
                    || correoCopia.toLowerCase().contains("comcel.com.co")
                    || correoCopia.toLowerCase().contains("telmexla.com")
                    || correoCopia.toLowerCase().contains("telmex.com")){
            return true;
        }else{
            return false;
        }
    }else{
            return true;
        }
    }
    
        /**
     * Función que valida que
     *
     * @author Juan David Hernandez
     * @param tecnologiaBasicaList
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public boolean validarNodoGponRedFOIngresado(List<CmtBasicaMgl> tecnologiaBasicaList)
            throws ApplicationException {
        try {            

            //Si RR se encuentra habilitado y se trata de una solicitud de creacion de hhpp
            if (tecnologiaBasicaList != null && !tecnologiaBasicaList.isEmpty()) {               
                boolean tecGpon = false;             
                boolean tecRedFO = false;  
                boolean tecUnifilar = false;
                
                for (CmtBasicaMgl tec : tecnologiaBasicaList) {                    

                     //Condicional para determinar si se ingreso un nodo GPON
                    if (tec.getIdentificadorInternoApp() != null
                            && !tec.getIdentificadorInternoApp().isEmpty()
                            && tec.getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_OP_UNI)) {

                        if (tec.getNodoGestion() != null
                                && !tec.getNodoGestion().isEmpty()) {
                            tecUnifilar = true;
                        }
                    }
                    
                    //Condicional para determinar si se ingreso un nodo GPON
                    if (tec.getIdentificadorInternoApp() != null
                            && !tec.getIdentificadorInternoApp().isEmpty()
                            && tec.getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_OP_GPON)) {

                        if (tec.getNodoGestion() != null
                                && !tec.getNodoGestion().isEmpty()) {
                            tecGpon = true;
                        }
                    }

                    //Condicional para determinar si se ingreso un nodo RED FO
                    if (tec.getIdentificadorInternoApp() != null
                            && !tec.getIdentificadorInternoApp().isEmpty()
                            && tec.getIdentificadorInternoApp().equalsIgnoreCase(Constant.RED_FO)) {

                        if (tec.getNodoGestion() != null
                                && !tec.getNodoGestion().isEmpty()) {
                            tecRedFO = true;
                        }
                    }

                }


                
                //si se encuentra lleno el nodo de GPON o Red FO
                if (tecGpon || tecUnifilar) {

                    if (tecGpon) {
                        if (!tecRedFO) {
                            String msn = "Validación de nodo: Cuando se ingresa un nodo GPON es necesario ingresar un nodo RED FO";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msn, ""));
                            
                            return false;
                        }
                    } else {
                        if (tecUnifilar) {
                            if (!tecRedFO) {
                                String msn = "Validación de nodo: Cuando se ingresa un nodo UNIFILAR es necesario ingresar un nodo RED FO";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                msn, ""));
                                return false;
                            }
                        }
                    }

                    return true;
                }
            }
               
            return true;
        } catch (Exception e) {
            String msn = "Error al validar nodo red fo digitado";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
            return false;
        }
    }
    
    public void crearInfoTecnica(Map<CmtBasicaMgl, NodoMgl> datosGestionCcmm,
            CmtSolicitudCmMgl solicitudCmMgl) throws ApplicationException {
        
        Iterator<Entry<CmtBasicaMgl, NodoMgl>> iterator = datosGestionCcmm.entrySet().iterator();
        List<CmtSubEdificioMgl> lstsubEdificioMgls = solicitudCmMgl.getCuentaMatrizObj().getListCmtSubEdificioMgl();

        if (CollectionUtils.isEmpty(lstsubEdificioMgls)) {
            LOGGER.debug("no se encontraron subedificios asociados a la cuenta matriz");
            return;
        }

        while (iterator.hasNext()) {
            Entry<CmtBasicaMgl, NodoMgl> n = iterator.next();

            for (CmtSubEdificioMgl subEdificioMgl1 : lstsubEdificioMgls) {
                agregarInfoTecnica(subEdificioMgl1, n);
            }
        }
    }

    /**
     * Permite agregar la información técnica asociada a un subedificio
     *
     * @param subEdificioMgl1        {@link CmtSubEdificioMgl}
     * @param techAndNodoMap         {@link Entry}
     * @throws ApplicationException Excepción de aplicación
     * @author Gildardo Mora
     */
    private void agregarInfoTecnica(CmtSubEdificioMgl subEdificioMgl1, Entry<CmtBasicaMgl,NodoMgl> techAndNodoMap) throws ApplicationException {
        try {
            CmtTecnologiaSubMgl tecnologiaSubMgl = tecnologiaSubMglFacadeLocal
                    .findBySubEdificioTecnologia(subEdificioMgl1, techAndNodoMap.getKey());

            if (tecnologiaSubMgl == null || tecnologiaSubMgl.getTecnoSubedificioId() == null) {
                LOGGER.debug("no se encontró tecnología subedificio asociada al subedificio");
                return;
            }

            CmtInfoTecnicaMgl cmtInfoTecni = new CmtInfoTecnicaMgl();
            cmtInfoTecni.setIdSubedificio(subEdificioMgl1);
            Map<String, CmtBasicaMgl> identificadores = obtenerIdentificadores();
            cmtInfoTecni.setBasicaIdInfoN1(identificadores.getOrDefault(techAndNodoMap.getKey()
                    .getIdentificadorInternoApp().toUpperCase(), null));

            if (cmtInfoTecni.getBasicaIdInfoN1() == null) {
                LOGGER.debug("No se encontró ID basica asociada a la tecnología");
                return;
            }

            cmtInfoTecni.setFechaCreacion(new Date());
            cmtInfoTecni.setEstadoRegistro(ConstantsCM.REGISTRO_ENUSO_ID);
            cmtInfoTecni.setPerfilEdicion(0);
            cmtInfoTecni.setTecnologiaSubMglObj(tecnologiaSubMgl);
            cmtInfoTecnicaMglFacadeLocal.setUser(usuarioVT, perfilVT);
            // Crea el registro de la info técnica en la cuenta matriz
            cmtInfoTecni = cmtInfoTecnicaMglFacadeLocal.create(cmtInfoTecni);

            if (cmtInfoTecni.getId() != null) {
                LOGGER.info("Registro infotecnica creado satisfactoriamente");
            }
        } catch (Exception e) {
            String msgError = "Error al agregar la información técnica";
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError,e);
        }
    }

    /**
     * Permite obtener los identificadores de la información técnica
     *
     * @return {@link Map} con los identificadores de la información técnica
     * @author Gildardo Mora
     */
    private Map<String, CmtBasicaMgl> obtenerIdentificadores() throws ApplicationException {
        try {
            String identificadoresInfoTecnica = ParametrosMerUtil.findValor(ParametrosMerEnum.IDENTIFICADORES_INFO_TECNICA_CCMM);
            List<String> identificadoresN1 = StringToolUtils.convertStringToList(
                    identificadoresInfoTecnica, DelimitadorEnum.PUNTO_Y_COMA, true);
            Map<String, CmtBasicaMgl> mapIdentificadores = new HashMap<>();
            Map<String, CmtBasicaMgl> mapBasica = new HashMap<>();

            for (String identificadorN1 : identificadoresN1) {
                // Divide la cadena de texto en dos partes en el primer paréntesis
                String[] parts = identificadorN1.split("\\(");
                // Filtra las cadenas que no tienen paréntesis
                if (parts.length > 1) {
                    String identificadorInfoTecnica = parts[0].trim();
                    mapBasica.put(identificadorInfoTecnica, findBasicaByIdInternoApp(identificadorInfoTecnica));
                    String contentInBrackets = parts[1].substring(0, parts[1].length() - 1); // Elimina el último paréntesis
                  /* Genera una lista a partir del String que contiene los identificadores de app de las tecnologías
                  y asigna a cada una, las instancias de cada identificador
                   para procesarlas posteriormente en la info técnica de la CCMM */
                    StringToolUtils.convertStringToList(contentInBrackets, DelimitadorEnum.COMA, true)
                            .forEach(tech -> {
                                if (StringUtils.isNotBlank(identificadorInfoTecnica)) {
                                    mapIdentificadores.put(tech.trim(),
                                            mapBasica.getOrDefault(identificadorInfoTecnica, null));
                                }
                            });
                }
            }

            mapIdentificadores.entrySet().removeIf(entry -> entry.getValue() == null);
            return mapIdentificadores;
        } catch (ApplicationException e) {
            String msgError = "Error al obtener los identificadores de la información técnica";
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Permite consultar la tecnologia a partir del identificador interno de app
     *
     * @param identificadorInfoTecnica {@link String} Identificador de la información técnica
     * @return {@link CmtBasicaMgl} Tecnología encontrada
     * @throws ApplicationException Excepción al consultar la tecnología
     * @author Gildardo Mora
     */
    private CmtBasicaMgl findBasicaByIdInternoApp(String identificadorInfoTecnica) throws ApplicationException {
        try {
            return basicasMglFacadeLocal.findByCodigoInternoApp(identificadorInfoTecnica);
        } catch (ApplicationException e) {
            String msgError = "Error al consultar la basica por identificador interno";
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }

    private boolean validarEdicion(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public boolean validarDirSolCm() {
        return validarEdicion(VALIDARDIRSOLCM);
    }


    public List<GeograficoPoliticoMgl> getCentroPobladoList() {
        return centroPobladoList;
    }

    public void setCentroPobladoList(List<GeograficoPoliticoMgl> centroPobladoList) {
        this.centroPobladoList = centroPobladoList;
    }

    public CmtSolicitudCmMgl getCmtSolicitudCmMgl() {
        return cmtSolicitudCmMgl;
    }

    public void setCmtSolicitudCmMgl(CmtSolicitudCmMgl cmtSolicitudCmMgl) {
        this.cmtSolicitudCmMgl = cmtSolicitudCmMgl;
    }

    public CmtDireccionSolicitudMgl getCmtDireccionSolictudMgl() {
        return cmtDireccionSolictudMgl;
    }

    public void setCmtDireccionSolictudMgl(CmtDireccionSolicitudMgl cmtDireccionSolictudMgl) {
        this.cmtDireccionSolictudMgl = cmtDireccionSolictudMgl;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
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

    public Presentar getVistas() {
        return vistas;
    }

    public void setVistas(Presentar vistas) {
        this.vistas = vistas;
    }

    public CmtSolicitudSubEdificioMgl getSolicitudSubEdificioMgl() {
        return solicitudSubEdificioMgl;
    }

    public void setSolicitudSubEdificioMgl(CmtSolicitudSubEdificioMgl solicitudSubEdificioMgl) {
        this.solicitudSubEdificioMgl = solicitudSubEdificioMgl;
    }

    public List<CmtBasicaMgl> getBasicasMglTipoEificioList() {
        return basicasMglTipoEificioList;
    }

    public void setBasicasMglTipoEificioList(List<CmtBasicaMgl> basicasMglTipoEificioList) {
        this.basicasMglTipoEificioList = basicasMglTipoEificioList;
    }

    public String getNododelBean() {
        return nododelBean;
    }

    public void setNododelBean(String nododelBean) {
        this.nododelBean = nododelBean;
    }

    public List<CmtCompaniaMgl> getListcompaniaAscensores() {
        return listcompaniaAscensores;
    }

    public void setListcompaniaAscensores(List<CmtCompaniaMgl> listcompaniaAscensores) {
        this.listcompaniaAscensores = listcompaniaAscensores;
    }

    public List<CmtCompaniaMgl> getListcompaniaConstructoras() {
        return listcompaniaConstructoras;
    }

    public void setListcompaniaConstructoras(List<CmtCompaniaMgl> listcompaniaConstructoras) {
        this.listcompaniaConstructoras = listcompaniaConstructoras;
    }

    public List<CmtCompaniaMgl> getListcompaniaAdministracion() {
        return listcompaniaAdministracion;
    }

    public void setListcompaniaAdministracion(List<CmtCompaniaMgl> listcompaniaAdministracion) {
        this.listcompaniaAdministracion = listcompaniaAdministracion;
    }

    public List<CmtBasicaMgl> getBasicasMglOrigenSolictudList() {
        return basicasMglOrigenSolictudList;
    }

    public void setBasicasMglOrigenSolictudList(List<CmtBasicaMgl> basicasMglOrigenSolictudList) {
        this.basicasMglOrigenSolictudList = basicasMglOrigenSolictudList;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public CmtNotasSolicitudMgl getCmtNotasSolicitudMgl() {
        return cmtNotasSolicitudMgl;
    }

    public void setCmtNotasSolicitudMgl(CmtNotasSolicitudMgl cmtNotasSolicitudMgl) {
        this.cmtNotasSolicitudMgl = cmtNotasSolicitudMgl;
    }

    public boolean isOtroBarrio() {
        return otroBarrio;
    }

    public void setOtroBarrio(boolean otroBarrio) {
        this.otroBarrio = otroBarrio;
    }

    public HashMap<String, NodoDto> getListaNodosCercanos() {
        return listaNodosCercanos;
    }

    public void setListaNodosCercanos(HashMap<String, NodoDto> listaNodosCercanos) {
        this.listaNodosCercanos = listaNodosCercanos;
    }

    public AddressService getAddressService() {
        return addressService;
    }

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public String getPrimerNodoCercano() {
        return primerNodoCercano;
    }

    public void setPrimerNodoCercano(String primerNodoCercano) {
        this.primerNodoCercano = primerNodoCercano;
    }

    public List<String> getListaNodosCobertura() {
        return listaNodosCobertura;
    }

    public void setListaNodosCobertura(List<String> listaNodosCobertura) {
        this.listaNodosCobertura = listaNodosCobertura;
    }

    public List<NodoDto> getListaNodosCercanosArray() {
        return listaNodosCercanosArray;
    }

    public void setListaNodosCercanosArray(List<NodoDto> listaNodosCercanosArray) {
        this.listaNodosCercanosArray = listaNodosCercanosArray;
    }

    public String getNodoCercanoDelBean() {
        return nodoCercanoDelBean;
    }

    public void setNodoCercanoDelBean(String nodoCercanoDelBean) {
        this.nodoCercanoDelBean = nodoCercanoDelBean;
    }

    public List<CmtBasicaMgl> getListEstratosBasica() {
        return listEstratosBasica;
    }

    public void setListEstratosBasica(List<CmtBasicaMgl> listEstratosBasica) {
        this.listEstratosBasica = listEstratosBasica;
    }

    public List<CmtTipoBasicaMgl> getListaDeAcciones() {
        return listaDeAcciones;
    }

    public void setListaDeAcciones(List<CmtTipoBasicaMgl> listaDeAcciones) {
        this.listaDeAcciones = listaDeAcciones;
    }

    public List<CmtBasicaMgl> getListResultadosGestion() {
        return listResultadosGestion;
    }

    public void setListResultadosGestion(List<CmtBasicaMgl> listResultadosGestion) {
        this.listResultadosGestion = listResultadosGestion;
    }

    public CmtNotasSolicitudMgl getCmtNotasSolicitudMglGestion() {
        return cmtNotasSolicitudMglGestion;
    }

    public void setCmtNotasSolicitudMglGestion(CmtNotasSolicitudMgl cmtNotasSolicitudMglGestion) {
        this.cmtNotasSolicitudMglGestion = cmtNotasSolicitudMglGestion;
    }

    public List<CmtUnidadesPreviasMgl> getUnidadesDeLadireccion() {
        return unidadesDeLadireccion;
    }

    public void setUnidadesDeLadireccion(List<CmtUnidadesPreviasMgl> unidadesDeLadireccion) {
        this.unidadesDeLadireccion = unidadesDeLadireccion;
    }

    public List<ParametrosCalles> getListTipoNivel5() {
        return listTipoNivel5;
    }

    public void setListTipoNivel5(List<ParametrosCalles> listTipoNivel5) {
        this.listTipoNivel5 = listTipoNivel5;
    }

    public List<ParametrosCalles> getListTipoNivel6() {
        return listTipoNivel6;
    }

    public void setListTipoNivel6(List<ParametrosCalles> listTipoNivel6) {
        this.listTipoNivel6 = listTipoNivel6;
    }

    public boolean isMostrarLinkOtraDireccion() {
        return mostrarLinkOtraDireccion;
    }

    public void setMostrarLinkOtraDireccion(boolean mostrarLinkOtraDireccion) {
        this.mostrarLinkOtraDireccion = mostrarLinkOtraDireccion;
    }

    public CmtCuentaMatrizMgl getCmtCuentaMatrizMglOtraDireccion() {
        return cmtCuentaMatrizMglOtraDireccion;
    }

    public void setCmtCuentaMatrizMglOtraDireccion(CmtCuentaMatrizMgl cmtCuentaMatrizMglOtraDireccion) {
        this.cmtCuentaMatrizMglOtraDireccion = cmtCuentaMatrizMglOtraDireccion;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getUrlArchivoSoporte() {
        return urlArchivoSoporte;
    }

    public void setUrlArchivoSoporte(String urlArchivoSoporte) {
        this.urlArchivoSoporte = urlArchivoSoporte;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public List<GeograficoPoliticoMgl> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<GeograficoPoliticoMgl> departamentoList) {
        this.departamentoList = departamentoList;
    }

    public List<GeograficoPoliticoMgl> getCiudadList() {
        return ciudadList;
    }

    public void setCiudadList(List<GeograficoPoliticoMgl> ciudadList) {
        this.ciudadList = ciudadList;
    }

    public BigDecimal getDepartamento() {
        return departamento;
    }

    public void setDepartamento(BigDecimal departamento) {
        this.departamento = departamento;
    }

    public BigDecimal getCiudad() {
        return ciudad;
    }

    public void setCiudad(BigDecimal ciudad) {
        this.ciudad = ciudad;
    }

    public BigDecimal getCentroProblado() {
        return centroProblado;
    }

    public void setCentroProblado(BigDecimal centroProblado) {
        this.centroProblado = centroProblado;
    }

    public List<CmtBasicaMgl> getListTablaBasicaTecnologias() {
        return listTablaBasicaTecnologias;
    }

    public void setListTablaBasicaTecnologias(List<CmtBasicaMgl> listTablaBasicaTecnologias) {
        this.listTablaBasicaTecnologias = listTablaBasicaTecnologias;
    }

    public boolean isHabilitarCombo() {
        return habilitarCombo;
    }

    public void setHabilitarCombo(boolean habilitarCombo) {
        this.habilitarCombo = habilitarCombo;
    }

    public BigDecimal getIdNodoCercano() {
        return idNodoCercano;
    }

    public void setIdNodoCercano(BigDecimal idNodoCercano) {
        this.idNodoCercano = idNodoCercano;
    }

    public Map<CmtBasicaMgl, NodoMgl> getDatosGestion() {
        return datosGestion;
    }

    public void setDatosGestion(Map<CmtBasicaMgl, NodoMgl> datosGestion) {
        this.datosGestion = datosGestion;
    }

    public Map<CmtBasicaMgl, NodoMgl> getDatosGestionFinal() {
        return datosGestionFinal;
    }

    public void setDatosGestionFinal(Map<CmtBasicaMgl, NodoMgl> datosGestionFinal) {
        this.datosGestionFinal = datosGestionFinal;
    }

    public UsuariosServicesDTO getUsuarioSolicitudCm() {
    return usuarioSolicitudCm;
    }
    
    public void setUsuarioSolicitudCm(UsuariosServicesDTO usuarioSolicitudCm) {
    this.usuarioSolicitudCm = usuarioSolicitudCm;
    }
    
    public UsuariosServicesDTO getUsuarioGestSolicitudCm() {
        return usuarioGestSolicitudCm;
    }
    
    public void setUsuarioGestSolicitudCm(UsuariosServicesDTO usuarioGestSolicitudCm) {
        this.usuarioGestSolicitudCm = usuarioGestSolicitudCm;
    }
    
    public EncabezadoSolicitudModificacionCMBean getEncabezadoSolModiCMBean() {
        return encabezadoSolModiCMBean;
    }

    public void setEncabezadoSolModiCMBean(EncabezadoSolicitudModificacionCMBean encabezadoSolModiCMBean) {
        this.encabezadoSolModiCMBean = encabezadoSolModiCMBean;
    }

    public boolean isProcesoterminado() {
        return procesoterminado;
    }

    public void setProcesoterminado(boolean procesoterminado) {
        this.procesoterminado = procesoterminado;
    }

    public boolean isValidaDireccion() {
        return validaDireccion;
    }

    public void setValidaDireccion(boolean validaDireccion) {
        this.validaDireccion = validaDireccion;
    }

    public BigDecimal getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(BigDecimal tecnologia) {
        this.tecnologia = tecnologia;
    }

    public Map<Long, Boolean> getChecked() {
        return checked;
    }

    public void setChecked(Map<Long, Boolean> checked) {
        this.checked = checked;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public CmtCuentaMatrizMgl getCuentaMatriz() {
        return cuentaMatriz;
    }

    public void setCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) {
        this.cuentaMatriz = cuentaMatriz;
    }

    public List<String> getTecnologiasCables() {
        return tecnologiasCables;
    }

    public void setTecnologiasCables(List<String> tecnologiasCables) {
        this.tecnologiasCables = tecnologiasCables;
    }

    public BigDecimal getBasicaIdEstrato() {
        return basicaIdEstrato;
    }

    public void setBasicaIdEstrato(BigDecimal basicaIdEstrato) {
        this.basicaIdEstrato = basicaIdEstrato;
    }
    
}
