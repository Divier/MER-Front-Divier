package co.com.claro.mgl.mbeans.cm;


import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.DireccionRRFacadeLocal;
import co.com.claro.mgl.facade.DrDireccionFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.RrCiudadesFacadeLocal;
import co.com.claro.mgl.facade.RrRegionalesFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtAgendamientoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtComponenteDireccionesMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCuentaMatrizMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtEstadoxFlujoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtNotasSolicitudMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOrdenTrabajoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSolicitudCmMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSolicitudSubEdificioMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificioMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTablasBasicasRRMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoSolicitudMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.ConfigurationAddressComponent;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.OpcionIdNombre;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.RrCiudades;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendamientoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.MessageUtils;
import co.com.claro.mgl.utils.StringUtils;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseConstruccionDireccion;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * ManageBean para interacci&oacute;n en la pantalla de eliminaci&oacute;n de CM
 *
 * @author wgavidia
 */
@ViewScoped
@ManagedBean(name = "eliminarCmBean")
public class EliminarCmBean implements Serializable {

    @EJB
    private CmtCuentaMatrizMglFacadeLocal cuentaMatrizMglFacadeLocal;
    @EJB
    private RrRegionalesFacadeLocal rrRegionalesFacade;
    @EJB
    private CmtComponenteDireccionesMglFacadeLocal componenteDireccionesMglFacade;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private RrCiudadesFacadeLocal rrCiudadesFacade;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    //@EJB
    @EJB
    private DireccionRRFacadeLocal direccionRRFacadeLocal;
    @EJB
    private CmtSolicitudCmMglFacadeLocal solicitudCmMglFacadeLocal;
    @EJB
    private CmtDireccionMglFacadeLocal direccionMglFacadeLocal;
    @EJB
    private CmtTipoSolicitudMglFacadeLocal cmtTipoSolicitudMglFacadeLocal;
    @EJB
    private DrDireccionFacadeLocal drDireccionMglFacadeLocal;
    @EJB
    private CmtSolicitudSubEdificioMglFacadeLocal solicitudSubEdificioMglFacadeLocal;
    @EJB
    private CmtSubEdificioMglFacadeLocal subEdificioMglFacadeLocal;
    @EJB
    private HhppMglFacadeLocal hhppMglFacadeLocal;
    @EJB
    private CmtOrdenTrabajoMglFacadeLocal otFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtNotasSolicitudMglFacadeLocal notaFacadeLocal;
    @EJB
    private CmtTablasBasicasRRMglFacadeLocal cmtTablasBasicasRRMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private CmtEstadoxFlujoMglFacadeLocal estadoxFlujoMglFacadeLocal;
    @EJB
    private CmtAgendamientoMglFacadeLocal cmtAgendamientoMglFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    @EJB
    private CmtOrdenTrabajoMglFacadeLocal cmtOrdenTrabajoMglFacadeLocal;
    
    private static final Logger LOGGER = LogManager.getLogger(EliminarCmBean.class);
    private final String VALIDARELIMINARCM = "VALIDARELIMINARCM";
    private final String PROCESO_EXITOSO = "Se ha eliminado la cuenta matriz con numero: %s de manera exitosa.";
    private final String PROCESO_FALLIDO = "Se presentó un error al procesar la petición. Por favor intente más tarde";
    private BigDecimal idCuentaMatriz;
    private CmtCuentaMatrizMgl cuentaMatriz;
    private CmtSolicitudCmMgl solicitudCmMgl;
    private CmtSolicitudSubEdificioMgl solicitudSub;
    private SecurityLogin securityLogin;
    private String usuarioVT;
    private int perfilVT;
    private String cedulaUsuarioVT;
    private ConfigurationAddressComponent configurationAddressComponent;
    private List<DrDireccion> barriosMultiorigen;
    private Boolean validacion;
    private Boolean validacionLimpiarDireccion;
    private String tipoSolicitud;
    private List<CmtRegionalRr> regionalList;
    private List<RrCiudades> ciudadesList;
    private List<GeograficoPoliticoMgl> centroPobladoList;
    private String rrRegional;
    private String rrCiudad;
    private BigDecimal idCentroPoblado;
    private String tipoAccionSolicitud;
    private Boolean multiEdificio;
    private String tipoDireccion;
    private Boolean showCKPanel;
    private Boolean showBMPanel;
    private Boolean showITPanel;
    private List<OpcionIdNombre> bmList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> itList = new ArrayList<OpcionIdNombre>();
    private List<CmtBasicaMgl> tipoSolicitudBasicaList;
    private List<CmtBasicaMgl> tipoAccionSolicitudBasicaList;
    private List<CmtBasicaMgl> listTablaBasicaTecnologias;
    private List<CmtOrdenTrabajoMgl> ordenesTrabajo;
    private List<ParametrosCalles> tipoSolicitudList;
    private String direccionCk;
    private RequestConstruccionDireccion request;
    private DrDireccion drDireccion;
    private UsuariosServicesDTO usuarioLogin;
    private ResponseConstruccionDireccion responseConstruirDireccion;
    private String direccionLabel;
    private String tipoNivelBm;
    private String nivelValorBm;
    private String tipoNivelIt;
    private String nivelValorIt;
    private List<CmtBasicaMgl> origenSolicitudList;
    private BigDecimal solicitud;
    private EncabezadoSolicitudModificacionCMBean encabezadoSolModiCMBean;
    private Boolean validacionInventario;
    private List<CmtTipoBasicaMgl> tiposAccion;
    private List<CmtBasicaMgl> acciones;
    private BigDecimal tipoAccion;
    private BigDecimal accion;
    private String comentario;
    public List<GeograficoPoliticoMgl> ciudadList;
    private BigDecimal departamento = null;
    private BigDecimal ciudad = null;
    private BigDecimal centropoblado = null;
    private GeograficoPoliticoMgl centroPoblado;
    private BigDecimal tecnologia;
    private List<CmtCuentaMatrizMgl> listcuentaMatriz;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private String cantidadSubedificios= "";
    private int cantidadHhpp;
    private final String FORMULARIO_ELIMINAR_CM = "ELIMINARCM";
    private boolean botonCrear;

    /**
     * Constructor de la clase
     */
    public EliminarCmBean() {
    }

    /**
     * M&eacute;todo post construct de la clase para inicializar componentes y
     * atributos
     */
    @PostConstruct
    public void init() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            securityLogin = new SecurityLogin(facesContext);
            validacionInventario = null;
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();
            cedulaUsuarioVT = securityLogin.getIdUser();
            encabezadoSolModiCMBean = JSFUtil.getBean("encabezadoSolicitudModificacionCMBean", EncabezadoSolicitudModificacionCMBean.class);
            solicitudCmMgl = new CmtSolicitudCmMgl();
            botonCrear = false;
            if (encabezadoSolModiCMBean.getSolicitudModCM() != null
                    && encabezadoSolModiCMBean.getSolicitudModCM().getSolicitudCmId() != null) {
                setDatosCreados();
                return;
            }
            if (encabezadoSolModiCMBean.getSolicitudModCM() != null) {
                setDatosCreados();
                return;
            }
            solicitudCmMglFacadeLocal.setUser(usuarioVT, perfilVT);
            solicitudSubEdificioMglFacadeLocal.setUser(usuarioVT, perfilVT);
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
            CmtTipoBasicaMgl cmtTipoBasicaMgl = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_ACCION_SOLICITUD);
            tipoAccionSolicitudBasicaList =
                    obtenerTipoAccion(cmtTipoBasicaMgl.getTipoBasicaId());
            CmtTipoBasicaMgl cmtTipoBasicaSolicitud = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_SOLICITUD);
            encabezadoSolModiCMBean.setShowHorario(false);
            CmtTipoBasicaMgl tipoBasicaTipoProyecto;
            tipoBasicaTipoProyecto = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    co.com.claro.mgl.utils.Constant.TIPO_BASICA_TECNOLOGIA);
            listTablaBasicaTecnologias = cmtBasicaMglFacadeLocal.
                    findByTipoBasica(tipoBasicaTipoProyecto);
            CmtTipoBasicaMgl tipoBasicaOrigenSolicitud = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_ORIGEN_SOLICITUD);
            tipoSolicitudBasicaList = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaOrigenSolicitud);
            limpiarPantalla();
        } catch (IOException | ApplicationException e) {
             FacesUtil.mostrarMensajeError("Error cargando los datos del usuario"+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * M&eacute;todo para cargar la inforaci&oacute;n de la solicitud a
     * gestionar
     *
     * @throws ApplicationException Excepci&oacute;n lanzada por consultas
     */
    private void setDatosCreados()  {
        try {
            cuentaMatrizMglFacadeLocal.setUser(usuarioVT, perfilVT);
            solicitudCmMgl = encabezadoSolModiCMBean.getSolicitudModCM();

            if (encabezadoSolModiCMBean.getSolicitudModCM().getSolicitudCmId() != null) {
                solicitudSub = solicitudSubEdificioMglFacadeLocal
                        .findSolicitudSubEdificioBySolicitud(solicitudCmMgl)
                        .get(0);
            }
            obtenerTipoSolicitudSolicitud();
            cuentaMatriz = solicitudCmMgl.getCuentaMatrizObj();
            drDireccion = new DrDireccion();
            drDireccion.obtenerFromCmtDireccionMgl(solicitudCmMgl.getCuentaMatrizObj().getDireccionPrincipal());
            drDireccion.setBarrio(drDireccionMglFacadeLocal.obtenerBarrio(drDireccion));
            direccionLabel = drDireccionMglFacadeLocal.getDireccion(drDireccion);
            multiEdificio = hhppMglFacadeLocal.cantidadHhppCuentaMatriz(cuentaMatriz) > 1;
            hhppMglFacadeLocal.setUser(usuarioVT, perfilVT);
            subEdificioMglFacadeLocal.setUser(usuarioVT, perfilVT);
            notaFacadeLocal.setUser(usuarioVT, perfilVT);
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);

            tiposAccion = cmtTipoBasicaMglFacadeLocal.encontrarTiposEliminacionCM();

            CmtBasicaMgl estadoPenEsc = cmtBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_ESCALADO_ACO);
            if (estadoPenEsc != null) {
                if (solicitudCmMgl.getEstadoSolicitudObj().getBasicaId().
                        compareTo(estadoPenEsc.getBasicaId()) == 0) {
                    otFacadeLocal.setUser(usuarioVT, perfilVT);
                    ordenesTrabajo = otFacadeLocal.ordenesTrabajoActivas(cuentaMatriz);
                }
            }

            if (solicitudCmMgl.getCuentaMatrizObj().
                    getListCmtSubEdificioMgl().size() > 0) {
                int resta = solicitudCmMgl.
                        getCuentaMatrizObj().getListCmtSubEdificioMgl().size() - 1;
                cantidadSubedificios = String.valueOf(resta);
            } else {
                cantidadSubedificios = "0";
            }
            cantidadHhpp = hhppMglFacadeLocal.countListHhppCM(null, cuentaMatriz.getSubEdificioGeneral());
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * M&eacute;todo para limpiar los campos de la interfaz de usuario
     *
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public void limpiarPantalla() {
        try {
            configurationAddressComponent = new ConfigurationAddressComponent();
            barriosMultiorigen = new ArrayList<DrDireccion>();
            obtenerTipoSolicitud();
            reiniciarDireccion();
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
            if (usuarioLogin.getCedula() != null) {
                solicitudCmMgl.setUsuarioSolicitudId(new BigDecimal(usuarioLogin.getCedula()));
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * M&eacute;todo para limpiar los campos de la interfaz de usuario
     *
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public void limpiarCampos()  {
        try {
            drDireccion = new DrDireccion();
            responseConstruirDireccion = new ResponseConstruccionDireccion();
            request = new RequestConstruccionDireccion();
            direccionCk = "";
            nivelValorIt = "";
            nivelValorBm = "";
            tipoNivelBm = "";
            tipoNivelIt = "";
            direccionLabel = "";
            departamento = null;
            ciudad = null;
            centropoblado = null;
            solicitud = null;
            solicitudCmMgl = null;
            solicitudCmMgl = new CmtSolicitudCmMgl();
            idCuentaMatriz = null;
            validacion = false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * M&eacute;todo para consultar los tipos de solicitud y seleccionar la
     * opci&oacute;n de eliminaci&oacute;n
     */
    private void obtenerTipoSolicitud() {
        try {
            solicitudCmMgl = new CmtSolicitudCmMgl();
            encabezadoSolModiCMBean.setSolicitudModCM(solicitudCmMgl);
            encabezadoSolModiCMBean.iniciarTiempoCreaSolicitud();
            CmtTipoBasicaMgl tipoBasicaOrigenSolicitud = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_ORIGEN_SOLICITUD);
            origenSolicitudList = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaOrigenSolicitud);
            for (CmtBasicaMgl origen : origenSolicitudList) {
                if (Constant.BASICA_ORIGEN_ELIMINAR_CM.equals(origen.getIdentificadorInternoApp())) {
                    solicitudCmMgl.setOrigenSolicitud(origen);
                    break;
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error cargando los tipos de solicitud" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }
    
        private void obtenerTipoSolicitudSolicitud() {
        try {
         
            CmtTipoBasicaMgl tipoBasicaOrigenSolicitud = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_ORIGEN_SOLICITUD);
            origenSolicitudList = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaOrigenSolicitud);
    
        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Error cargando los tipos de solicitud"+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * M&eacute;todo para crear la solicitud de eliminaci&oacute;n
     * @return 
     */
    public String generarSolicitud() {
          boolean ocurrioError = false;
        try {
            if (solicitudCmMgl.getTipoSolicitudObj() != null && solicitudCmMgl.getCorreoAsesor() != null 
                    && solicitudCmMgl.getTelefonoAsesor() != null) {
            // correo asesor
            if (solicitudCmMgl.getCorreoAsesor() != null
                    && !solicitudCmMgl.getCorreoAsesor().trim().isEmpty()) {
                if (!validarCorreo(solicitudCmMgl.getCorreoAsesor())) {
                    String msg = "El campo correo asesor no tiene el formato requerido";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return null;
                } else {
                    if (solicitudCmMgl.getCorreoAsesor() != null && !solicitudCmMgl.getCorreoAsesor().isEmpty()) {
                        if (!validarDominioCorreos(solicitudCmMgl.getCorreoAsesor())) {
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
            
            // correo copia
            if (solicitudCmMgl.getCorreoCopiaSolicitud() != null
                    && !solicitudCmMgl.getCorreoCopiaSolicitud().trim().isEmpty()) {
                if (!validarCorreo(solicitudCmMgl.getCorreoCopiaSolicitud())) {
                    String msg = "El campo copia a no tiene el formato requerido";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return null;
                } else {
                    if (solicitudCmMgl.getCorreoCopiaSolicitud() != null && !solicitudCmMgl.getCorreoCopiaSolicitud().isEmpty()) {
                        if (!validarDominioCorreos(solicitudCmMgl.getCorreoCopiaSolicitud())) {
                            String msg = "El campo copia a debe ser un correo con dominio telmexla.com, telmex.com.co, telmex.com, comcel.com.co o claro.com.co";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msg, ""));
                            ocurrioError = true;
                            return "";
                        }
                    }
                }
            }
                if (this.validateRequiredInputFields()) {

                    solicitudCmMgl.setCuentaMatrizObj(cuentaMatriz);
                    solicitudCmMgl.setCiudadGpo(cuentaMatriz.getMunicipio());
                    solicitudCmMgl.setCentroPobladoGpo(cuentaMatriz.getCentroPoblado());
                    solicitudCmMgl.setDepartamentoGpo(cuentaMatriz.getDepartamento());
                    CmtBasicaMgl estado = new CmtBasicaMgl();
                    estado.setBasicaId(cmtBasicaMglFacadeLocal
                            .findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE).getBasicaId());
                    solicitudCmMgl.setEstadoSolicitudObj(estado);
                    solicitudCmMgl.setResultGestion(
                            cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ELIMINAR_CM));
                    solicitudCmMgl.setTempSolicitud(encabezadoSolModiCMBean.getTimeSol());
                    solicitudCmMgl.setTipoSolicitudObj(cmtTipoSolicitudMglFacadeLocal.obtenerSolicitudEliminacion());
                    solicitudCmMgl.setDivision(cuentaMatriz.getDivision());
                    if (centroPoblado != null) {
                        solicitudCmMgl.setComunidad(centroPoblado.getGpoNombre());
                    }

                    solicitudCmMgl.setTipoAcondicionamiento(null);
                    solicitudCmMgl.setResultGestionModCobertura(null);
                    solicitudCmMgl.setResultGestionModDatosCm(null);
                    solicitudCmMgl.setResultGestionModSubEdi(null);
                    solicitudCmMgl.setResultGestionModDir(null);
                    solicitudCmMgl.setOrigenSolicitud(cmtBasicaMglFacadeLocal.findById(solicitud));
                    solicitudCmMgl.setBasicaIdTecnologia(null);
                    solicitudCmMgl.setTipoSegmento(null);

                    CmtBasicaMgl estadoSolMgl = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(
                            Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE);


                    CmtSolicitudCmMgl solElimina =
                            solicitudCmMglFacadeLocal.findBySolCMTipoSol(cuentaMatriz, solicitudCmMgl.getTipoSolicitudObj(), estadoSolMgl);

                    if (solElimina != null) {
                         if (solElimina.getSolicitudCmId() != null) {
                            validacion = false;
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    String.format("Ya existe la  solicitud:  " + solElimina.getSolicitudCmId() + " para eliminar la cuenta matriz "),
                                    ""));

                        }
                    } else {
                        solicitudCmMglFacadeLocal.setUser(usuarioVT, perfilVT);
                        if (solicitudCmMgl != null && solicitudCmMgl.getCorreoCopiaSolicitud() != null
                                && !solicitudCmMgl.getCorreoCopiaSolicitud().isEmpty()
                                && encabezadoSolModiCMBean.getUsuarioSolicitudCreador() != null && encabezadoSolModiCMBean.getUsuarioSolicitudCreador().getEmail() != null
                                && !encabezadoSolModiCMBean.getUsuarioSolicitudCreador().getEmail().isEmpty()) {
                            solicitudCmMgl.setCorreoCopiaSolicitud(solicitudCmMgl.getCorreoCopiaSolicitud() + "," + encabezadoSolModiCMBean.getUsuarioSolicitudCreador().getEmail());
                        } else {
                            if (encabezadoSolModiCMBean.getUsuarioSolicitudCreador() != null && encabezadoSolModiCMBean.getUsuarioSolicitudCreador().getEmail() != null
                                    && !encabezadoSolModiCMBean.getUsuarioSolicitudCreador().getEmail().isEmpty()) {
                                solicitudCmMgl.setCorreoCopiaSolicitud(encabezadoSolModiCMBean.getUsuarioSolicitudCreador().getEmail());
                            }
                        }
                        
                        solicitudCmMglFacadeLocal.crearSol(solicitudCmMgl);
                        solicitudSub = new CmtSolicitudSubEdificioMgl();
                        generarSolicitudSubEdificio(solicitudSub);
                        solicitudSubEdificioMglFacadeLocal.setUser(usuarioVT, perfilVT);
                        solicitudSubEdificioMglFacadeLocal.crearSolSub(solicitudSub);
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                String.format("Se ha creado la solicitud <b> %s </b> y esta pendiente de gestión",
                                solicitudCmMgl.getSolicitudCmId()),
                                ""));
                        solicitudCmMgl = solicitudCmMglFacadeLocal.findById(solicitudCmMgl.getSolicitudCmId());
                        encabezadoSolModiCMBean.setSolicitudModCM(solicitudCmMgl);
                        botonCrear=true;
                    }

                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        String.format("Existen campos obligatorios "),
                        ""));
            }

        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Error generando la solicitud" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * M&eacute;todo para configurar los datos de la solicitud para un
     * subedificio
     *
     * @param solicitudSub {@link CmtSolicitudSubEdificioMgl} Solicitud a
     * configurar
     * @throws ApplicationException Excepci&oacute;n lanzada por consultas
     */
    private void generarSolicitudSubEdificio(CmtSolicitudSubEdificioMgl solicitudSub) {
        try{
        CmtSubEdificioMgl subEdificio = subEdificioMglFacadeLocal.findSubEdificioGeneralByCuentaMatriz(cuentaMatriz);
        solicitudSub.setNombreSubedificio(subEdificio.getNombreSubedificio());
        solicitudSub.setEstadoSubEdificioObj(subEdificio.getEstadoSubEdificioObj());
        solicitudSub.setCompaniaConstructoraObj(subEdificio.getCompaniaConstructoraObj());
        solicitudSub.setTipoAcometidaObj(subEdificio.getTipoAcometidaObj());
        solicitudSub.setCompaniaAdministracionObj(subEdificio.getCompaniaAdministracionObj());
        solicitudSub.setCompaniaAscensorObj(subEdificio.getCompaniaAscensorObj());
        solicitudSub.setNodoObj(subEdificio.getNodoObj());
        solicitudSub.setFechaEntregaEdificio(subEdificio.getFechaEntregaEdificio());
        solicitudSub.setAdministrador(subEdificio.getAdministrador());
        solicitudSub.setUnidadesVt(subEdificio.getUnidadesVt());
        solicitudSub.setUnidades(subEdificio.getUnidades());
        solicitudSub.setFechaCreacion(new Date());
        solicitudSub.setTelefonoPorteria(subEdificio.getTelefonoPorteria());
        solicitudSub.setTelefonoPorteria2(subEdificio.getTelefonoPorteria2());
        solicitudSub.setTipoEdificioObj(subEdificio.getTipoEdificioObj());
        solicitudSub.setEstadoRegistro(1);
        solicitudSub.setDireccion(subEdificio.getDireccion());
        solicitudSub.setSolicitudCMObj(solicitudCmMgl);
        solicitudSub.setSubEdificioObj(subEdificio);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * M&eacute;todo para validar los campos requeridos para la creaci&oacute;n
     * de la solicitud
     *
     * @return boolean resultado de la validaci&oacute;n
     */
    public boolean validarCreacion() {
        try {
            validacion = false;

            if (idCuentaMatriz == null
                    && responseConstruirDireccion == null) {
                FacesContext facesMessage = FacesContext.getCurrentInstance();
                facesMessage.addMessage(
                        null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Debe digitar el número o la direccion de cuenta matriz", ""));
                return validacion;
            }

            if (idCuentaMatriz != null) {
                cuentaMatriz = new CmtCuentaMatrizMgl();
                listcuentaMatriz = cuentaMatrizMglFacadeLocal.findByNumeroCM(idCuentaMatriz);
                   cuentaMatriz = listcuentaMatriz.get(0);
                if (listcuentaMatriz == null || listcuentaMatriz.isEmpty()) {
                    FacesContext facesMessage = FacesContext.getCurrentInstance();
                    facesMessage.addMessage(
                            null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encontró cuenta matriz para eliminar", ""));
                    validacion = false;
                    validacionLimpiarDireccion = true;
                    return validacion;
                } else {
                    FacesContext facesMessage = FacesContext.getCurrentInstance();
                    facesMessage.addMessage(
                            null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            String.format("Se encontró la cuenta %s para gestionar.", cuentaMatriz.getNumeroCuenta()), ""));
                    validacion = true;
                    return validacion;
                }

            } else {
                List<CmtDireccionMgl> cuentas = direccionMglFacadeLocal.findByDireccion(
                        responseConstruirDireccion.getDrDireccion().convertToCmtDireccionMgl());
                if (cuentas.size() > 1) {
                    FacesContext facesMessage = FacesContext.getCurrentInstance();
                    facesMessage.addMessage(
                            null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Se encontró más cuenta matriz para eliminar", ""));
                    return validacion;
                }
                cuentaMatriz = cuentas.isEmpty() ? null : cuentas.get(0).getCuentaMatrizObj();
                 Integer solicitudesAbiertas =
                    solicitudCmMglFacadeLocal.cantidadSolicitudesEliminacionPorCuentaMatriz(cuentaMatriz.getNumeroCuenta());

            if (solicitudesAbiertas > 0) {
                FacesContext facesMessage = FacesContext.getCurrentInstance();
                facesMessage.addMessage(
                        null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Ya existe una solicitud de eliminación para la cuenta matriz consultada.", ""));
                return validacion;
            }

                if (cuentaMatriz == null) {
                    FacesContext facesMessage = FacesContext.getCurrentInstance();
                    facesMessage.addMessage(
                            null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encontró cuenta matriz para eliminar", ""));
                    validacion = false;
                    validacionLimpiarDireccion = true;
                    return validacion;
                }else{
                     FacesContext facesMessage = FacesContext.getCurrentInstance();
                    facesMessage.addMessage(
                            null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            String.format("Se encontró la cuenta %s para gestionar.", cuentaMatriz.getNumeroCuenta()), ""));
                    validacion = true;
                    return validacion;
                }
                
            }

          
           

        } catch (ApplicationException e) {
            String msn
                    = String.format("Ocurrió un error validando la cuenta matriz. %s", e.getMessage());
             FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);

        } finally {
            return validacion;
        }
    }

    /**
     * M&eacute;todo para realizar la consulta de regionales
     */
    public void obtenerListaRegionales() {
        try {
            if (!"".equals(tipoSolicitud)) {
                regionalList = rrRegionalesFacade.findByUnibi(
                        tipoSolicitud.equalsIgnoreCase("DTH") ? "U" : tipoSolicitud);
                cargarListadosConfiguracion();
            }
            ciudadesList = null;
            centroPobladoList = null;
            rrRegional = null;
            rrCiudad = null;
            idCentroPoblado = null;
        } catch (ApplicationException e) {
            String msn = "Error al realizar consulta para obtener listado de regiones";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void consultarCiudades()  {
        try {
            ciudadList = geograficoPoliticoMglFacadeLocal.findCiudades(departamento);
            cargarListadosConfiguracion();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    public void consultarCentroPoblado() {
        try {
            centroPobladoList = geograficoPoliticoMglFacadeLocal.findCentroPoblado(ciudad);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void obtenerCentroPoblado() {
        try {
            centroPoblado = geograficoPoliticoMglFacadeLocal.findById(centropoblado);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * M&eacute;todo para consultar los listados requeridos en la
     * creaci&oacute;n de la direcci&oacute;n
     *
     * @throws ApplicationException Excepci&oacute;n lanzada por consultas
     */
    private void cargarListadosConfiguracion() {
        try {
            //Obtiene lista de listas de los componentes de la dirección.          
            configurationAddressComponent = componenteDireccionesMglFacade
                    .getConfiguracionComponente(tipoSolicitud);
            bmList = componenteDireccionesMglFacade
                    .obtenerBarrioManzanaList(configurationAddressComponent);
            itList = componenteDireccionesMglFacade
                    .obtenerIntraducibleList(configurationAddressComponent);
        } catch (ApplicationException e) {
            String msn = "Error al realizar consultas para obtener configuración de listados.";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * M&eacute;todo para obtener el listado de ciudades por departamento
     *
     * @throws ApplicationException Excepci&oacute;n lanzada por consultas
     */
    public void obtenerListaCiudades()  {
        try {
            ciudadesList = null;
            centroPobladoList = null;
            reiniciarDireccion();
            if (ValidacionUtil.validarParametro(rrRegional)
                    && !rrRegional.isEmpty()) {
                ciudadesList = rrCiudadesFacade.findByCodregional(rrRegional);
            }
        } catch (ApplicationException e) {
            String msn = "Error al realizar consulta para obtener listado de ciudades";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * M&eacute;todo para validar la ciudad seleccionada y lanzar la consulta de
     * centros poblados.
     */
    public void validarCiudadSeleccionada() {
        if (rrCiudad != null && !rrCiudad.isEmpty()) {
            obtenerGeograficoPoliticoList();
        }
    }

    /**
     * M&eacute;todo para realizar la consulta de centros poblados por regional
     */
    private void obtenerGeograficoPoliticoList() {
        try {
            GeograficoPoliticoMgl geograficoPoliticoMgl =
                    geograficoPoliticoMglFacadeLocal.findCityByComundidad(rrCiudad);
            centroPobladoList = geograficoPoliticoMglFacadeLocal
                    .findCentroPoblado(geograficoPoliticoMgl.getGpoId());
        } catch (ApplicationException e) {
            String msn = "Error al validar ciudad seleccionada.";
             FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * M&eacute;todo para limpiar los datos de la direcci&oacute;n
     */
    public void reiniciarDireccion() {
        direccionCk = "";
        solicitudCmMgl = null;
        solicitudCmMgl = new CmtSolicitudCmMgl();
        idCuentaMatriz = null;
        validacion = false;
        request = new RequestConstruccionDireccion();
        tipoSolicitud = "";
        tipoAccionSolicitud = "";
        limpiarCamposTipoDireccion();
        tipoDireccion = "";
        rrCiudad = "";
        idCentroPoblado = BigDecimal.ZERO;
        centroPobladoList = null;
        departamento = null;
        ciudad = null;
        centropoblado = null;
        solicitud = null;
        showPanel(true, false, false);
    }

    /**
     * M&eacute;todo para verificar que panel de direcciones se muestra en la
     * interfaz
     */
    public void validarTipoDireccion() {
        limpiarCamposTipoDireccion();
        showPanel(
                "CK".equals(tipoDireccion),
                "BM".equals(tipoDireccion),
                "IT".equals(tipoDireccion));
    }

    /**
     * M&eacute;todo para limpiar los campos de la direcci&oacute;n buscada
     */
    public void limpiarCamposTipoDireccion() {
        drDireccion = new DrDireccion();
        responseConstruirDireccion = new ResponseConstruccionDireccion();
        direccionLabel = "";
        direccionCk = "";
        nivelValorIt = "";
        nivelValorBm = "";
        tipoNivelBm = "";
        tipoNivelIt = "";
        direccionLabel = "";
        departamento = null;
        ciudad = null;
        centropoblado = null;
        validacion = false;
    }

    /**
     * M&eacute;todo para configurar los atributos que hacen render a los
     * paneles de direcci&oacute;n
     *
     * @param showCK boolean para visualizar el panel de direcci&oacute;n por
     * Calle - Carrera
     * @param showBM boolean para visualizar el panel de direcci&oacute;n por
     * Barrio - Manzana
     * @param showIT boolean para visualizar el panel de direcci&oacute;n
     * intraducible
     */
    private void showPanel(Boolean showCK, Boolean showBM, Boolean showIT) {
        showCKPanel = showCK;
        showBMPanel = showBM;
        showITPanel = showIT;
    }

    /**
     * M&eacute;todo para consultar un tipo de acci&oacute;n por tipo de
     * solicitud
     *
     * @param tipoSolicitud {@link BigDecimal} Id de tipo de solicitud
     * @return {@link List}&lt{@link CmtBasicaMgl}> listado de tipos de
     * acci&oacute;n encontrados
     */
    private List<CmtBasicaMgl> obtenerTipoAccion(BigDecimal tipoSolicitud) {
        try {
            CmtTipoBasicaMgl cmtTipoBasicaMgl = new CmtTipoBasicaMgl();
            cmtTipoBasicaMgl.setTipoBasicaId(tipoSolicitud);
            return cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl);
        } catch (ApplicationException e) {
            String msn = "Error al obtener el tipo de acción de la solicitud.";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            return null;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;

    }

    /**
     * M&eacute;todo para construir una direcci&oacute;n de tipo calle - carrera
     */
    public void construirDireccionCk() {
        try {
            if (!validarDatosDireccionCk()) {
                return;
            }
            request.setDireccionStr(direccionCk);
            construirDireccion("N", "N", true);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * M&eacute;todo para validar si se ha seleccionado la ciudad en la interfaz
     *
     * @return boolean retorno de la validaci&oacute;n
     */
    private boolean validarTipoCiudad() {
        if (tipoDireccion == null || tipoDireccion.trim().isEmpty()) {
            String msnError = "Seleccione el TIPO DE DIRECCIÓN que desea ingresar por favor.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, msnError,
                    ""));
            return false;
        }

        if (centroPoblado == null) {
            String msnError = "Seleccione el centro Poblado por favor.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError, ""));
            return false;
        }
        return true;
    }

    /**
     * M&eacute;todo para construir una direcci&oacute;n de tipo Barrio -
     * Manzana
     */
    public void construirDireccionBm() {
        try {
            if (!validarDatosDireccionBm()) {
                return;
            }
            request.setValorNivel(nivelValorBm.trim());
            construirDireccion(tipoNivelBm, "P", false);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * M&eacute;todo para validar los campos requeridos en la
     * construcci&oacute;n de una direcci&oacute;n tipo Barrio - Manzana
     *
     * @return boolean retorno de la validaci&oacute;n
     */
    private boolean validarDatosDireccionBm() {
        if (!validarTipoCiudad()) {
            return false;
        }

        if (tipoNivelBm == null || tipoNivelBm.isEmpty()) {
            String msnError = "Seleccione el TIPO DE NIVEL que desea agregar por favor.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, msnError,
                    ""));
            return false;
        }

        if (nivelValorBm == null || nivelValorBm.isEmpty()) {
            String msnError = "Ingrese un valor en el campo de nivel barrio-manzana por favor.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError, ""));
            return false;
        }

        return true;
    }

    /**
     * M&eacute;todo para validar los campos requeridos en la
     * construcci&oacute;n de una direcci&oacute;n tipo Calle - Carrera
     *
     * @return boolean retorno de la validaci&oacute;n
     */
    private boolean validarDatosDireccionCk() {
        if (!validarTipoCiudad()) {
            return false;
        }
        if (direccionCk == null || direccionCk.trim().isEmpty()) {
            String msnError = "Ingrese la dirección por favor.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError, ""));
            return false;
        }
        return true;
    }

    /**
     * M&eacute;todo para construir una direcci&oacute;n intraducible
     */
    public void construirDireccionIt() {
        try {
            if (!validarDatosDireccionIt()) {
                return;
            }
            request.setValorNivel(nivelValorIt.trim());
            construirDireccion(tipoNivelIt, "P", false);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * M&eacute;todo para validar los campos requeridos en la
     * construcci&oacute;n de una direcci&oacute;n Intraducible
     *
     * @return boolean retorno de la validaci&oacute;n
     */
    private boolean validarDatosDireccionIt() {
        if (!validarTipoCiudad()) {
            return false;
        }

        if (tipoNivelIt == null || tipoNivelIt.isEmpty()) {
            String msnError = "Seleccione el TIPO DE NIVEL que desea agregar por favor.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError, ""));
            return false;
        }

        if (nivelValorIt == null || nivelValorIt.isEmpty()) {
            String msnError = "Ingrese un valor en el campo de nivel intraducible por favor";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError, ""));
            return false;
        }

        if (tipoNivelIt.equalsIgnoreCase("CONTADOR") && nivelValorIt.length() > 7) {
            String msnError = "El valor para Contador no puede exceder los 7 caracteres";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError, ""));
            return false;
        }

        if (tipoNivelIt.equalsIgnoreCase("CONTADOR") && !nivelValorIt.matches("\\d+")) {
            String msnError = "El valor para Contador debe ser númerico.";
            FacesContext.getCurrentInstance()
                    .addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError, ""));
            return false;
        }
        return true;
    }

    /**
     * M&eacute;todo para construir la direcci&oacute;n digitada en la interfaz
     *
     * @param tipoNivel {@link String} tipo de nivel para la craci&oacute;n de
     * la direcci&oacute;n
     * @param tipoAdicion {@link String} tipo de adici&oacute;n para la
     * craci&oacute;n de la direcci&oacute;n
     * @param direccionCK boolean para verificar si la direcci&oacute;n es de
     * tipo calle - carrera
     * @throws ApplicationException Excepci&oacute;n lanzada en las consultas
     * realizadas
     */
    private void construirDireccion(String tipoNivel, String tipoAdicion, boolean direccionCK){
              try {
            request.setComunidad(centroPoblado.getGeoCodigoDane());
            drDireccion.setIdTipoDireccion(tipoDireccion);
            request.setDrDireccion(drDireccion);
            request.setTipoNivel(tipoNivel);
            request.setTipoAdicion(tipoAdicion);
            request.setIdUsuario(usuarioLogin.getCedula().toString());

            if (responseConstruirDireccion != null && responseConstruirDireccion.getDrDireccion() != null) {
                request.setDrDireccion(responseConstruirDireccion.getDrDireccion());
            }
            // Consume servicio que retorna la dirección traducida.
            responseConstruirDireccion = direccionRRFacadeLocal.construirDireccionSolicitud(request);

            // Dirección no construida correctamente
            if (responseConstruirDireccion == null || responseConstruirDireccion.getResponseMesaje()
                    .getTipoRespuesta().equals("E")) {
                if (direccionCK) {
                    direccionLabel = direccionCk;
                }
                String msnError = responseConstruirDireccion.getResponseMesaje()
                        .getMensaje().toString();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msnError, ""));
                return;
            }

            //Direccion traducida correctamente
            if (responseConstruirDireccion != null
                    && responseConstruirDireccion.getDireccionStr() != null
                    && !responseConstruirDireccion.getDireccionStr().isEmpty()
                    && responseConstruirDireccion.getResponseMesaje()
                            .getTipoRespuesta().equalsIgnoreCase("I")) {
                direccionLabel = responseConstruirDireccion.getDireccionStr().toString();
                request.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                tipoNivelBm = "";
                nivelValorBm = "";
                tipoNivelIt = "";
                nivelValorIt = "";
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * M&eacute;todo para redireccionar a la informaci&oacute;n de la cuenta
     * matriz
     *
     * @return {@link String} url de pantalla CM
     */
    public String ingresarGestionCm() {
        try {
            CuentaMatrizBean cuentaMatrizBean = (CuentaMatrizBean) JSFUtil.getSessionBean(CuentaMatrizBean.class);
            cuentaMatrizBean.setCuentaMatriz(cuentaMatriz);
            FacesUtil.navegarAPagina("/view/MGL/CM/tabs/hhpp.jsf");
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error consultando la cuenta matriz" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void buscarBasicasEliminacion() {
        try {
            CmtTipoBasicaMgl tipo = new CmtTipoBasicaMgl();
            tipo.setTipoBasicaId(tipoAccion);
            acciones = cmtBasicaMglFacadeLocal.findByTipoBasica(tipo);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error consultando las acciones por tipo" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public String gestionar() {
        try {
            if (this.accion == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No ha seleccionado la acción a realizar.", ""));
                return null;
            }
            if (this.comentario == null || "".equals(this.comentario.trim())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Debe ingresar un comentario por la eliminación.", ""));
                return null;
            }

            CmtNotasSolicitudMgl nota = new CmtNotasSolicitudMgl();
            CmtBasicaMgl accionNota = obtenerTipoAccion();
            nota.setNota(comentario);
            nota.setSolicitudCm(solicitudCmMgl);
            nota.setTipoNotaObj(accionNota);
            notaFacadeLocal.crearNotSol(nota);
            if (comentario != null) {
                solicitudCmMgl.setRespuestaActual(comentario);
            }

            if (Constant.BASICA_ELIMINAR_CM.equals(accionNota.getIdentificadorInternoApp())) {
                return eliminarCM();
            } else if (Constant.BASICA_ELIMINAR_CM_HHPP.equals(accionNota.getIdentificadorInternoApp())) {
               return  eliminarCmHhpp();
            } else if (Constant.BASICA_ESCALAR_HHPP_SERVICIO.equals(accionNota.getIdentificadorInternoApp())
                    || Constant.BASICA_ESCALAR_EQUIPOS_ASOCIADOS.equals(accionNota.getIdentificadorInternoApp())) {
                realizarEscalamiento();
            } else if (Constant.BASICA_ESCALAR_HHPP_SERVICIO_DESASOCIADOS.equals(accionNota.getIdentificadorInternoApp())
                    || Constant.BASICA_ESCALAR_EQUIPOS_DESASOCIADOS.equals(accionNota.getIdentificadorInternoApp())) {
                finalizarEscalamiento();
            } else if (Constant.BASICA_RECHAZAR_HHPP_SERVICIO.equals(accionNota.getIdentificadorInternoApp())
                    || Constant.BASICA_RECHAZAR_EQUIPOS_ASOCIADOS.equals(accionNota.getIdentificadorInternoApp())) {
                rechazarEliminacion();
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error eliminando la cuenta matriz:" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
        return "";
    }

    private CmtBasicaMgl obtenerTipoAccion() {
        for (CmtBasicaMgl registro : acciones) {
            if (registro.getBasicaId().equals(accion)) {
                return registro;
            }
        }
        return null;
    }

    /**
     * M&eacute;todo para eliminar una cuenta matriz de &uacute;nico edificio.
     */
    private String eliminarCM() {
        try {
            
            //Valida si RR se encuentra encendido o apagado para realizar las operaciones en RR
            boolean habilitarRR = false;
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                habilitarRR = true;
            }
           Boolean valInventario = validacionInventario != null ? validacionInventario : validarInventario();
           if (valInventario) {
               return null;
           }
            List<CmtSubEdificioMgl> subEdificios =
                    subEdificioMglFacadeLocal.findSubEdificioByCuentaMatriz(cuentaMatriz);
            if (subEdificios.size() > 1) {
                List<CmtSubEdificioMgl> subEdificiosCm = eliminarSubEdificios(subEdificios);
                if (!subEdificiosCm.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "No se pudieron eliminar todos los Subedificios asociados a la cuenta matriz.", ""));
                    return null;
                }
            }
            
            boolean eliminaCcmmRr = false;
            if(habilitarRR){            
            eliminaCcmmRr = cmtTablasBasicasRRMglFacadeLocal.edificioDelete(cuentaMatriz, usuarioVT);
            }else{
                eliminaCcmmRr = true;
            }
            if (eliminaCcmmRr) {
                cuentaMatrizMglFacadeLocal.deleteCm(cuentaMatriz);
                cambiarEstadoSolicitud(Constant.BASICA_ELIMINAR_CM_ACEPTA);
                
                //JDHT
                if(solicitudCmMgl != null && solicitudCmMgl.getCorreoCopiaSolicitud() != null
                        && !solicitudCmMgl.getCorreoCopiaSolicitud().isEmpty()
                        && encabezadoSolModiCMBean != null
                        && encabezadoSolModiCMBean.getUsuarioLogin() != null
                        && !encabezadoSolModiCMBean.getUsuarioLogin().getEmail().isEmpty()){
                    String correoCopia = solicitudCmMgl.getCorreoCopiaSolicitud() + ", " 
                            + encabezadoSolModiCMBean.getUsuarioLogin().getEmail(); 
                    solicitudCmMgl.setCorreoCopiaSolicitud(correoCopia);
                    
                }else{
                     if(encabezadoSolModiCMBean != null
                        && encabezadoSolModiCMBean.getUsuarioLogin() != null
                        && !encabezadoSolModiCMBean.getUsuarioLogin().getEmail().isEmpty()){
                    solicitudCmMgl.setCorreoCopiaSolicitud(encabezadoSolModiCMBean.getUsuarioLogin().getEmail());
                     }
                }
                
                //Se eliminan las OT asociadas a la CM.
                cmtOrdenTrabajoMglFacadeLocal.eliminarOTByCM(cuentaMatriz.getCuentaMatrizId());
                
                
                solicitudCmMglFacadeLocal.envioCorreoGestion(solicitudCmMgl);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        String.format(PROCESO_EXITOSO,
                        "<b>"+cuentaMatriz.getCuentaMatrizId()+"</b> en MGL y con numero: <b>"
                        + " "+cuentaMatriz.getNumeroCuenta()+"</b> en RR "), ""));
                return encabezadoSolModiCMBean.regresar(null);
            } else {                
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Ocurrio un error eliminando la Cm en RR", ""));
                return null;
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error eliminando la cuenta matriz:" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
         return "";
    }

    /**
     * M&eacute;todo para eliminar una cuenta matriz con hhpp asociados.
     */
    private String eliminarCmHhpp() {
        try {
            if (validarInventario()) {
                return null;
            }
            Integer hhppActivos = hhppMglFacadeLocal.cantidadHhppEnServicio(cuentaMatriz);
            if (hhppActivos > 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se puede eliminar la cuenta matriz porque existen HHPP en servicio,"
                        + " suspendidos o desconexión fisica.", ""));
                return null;
            }

            List<HhppMgl> hhppNoProcesados = hhppMglFacadeLocal.eliminarHhppCuentaMatriz(cuentaMatriz);
            if (!hhppNoProcesados.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se pudieron eliminar todos los hhpp asociados a la cuenta matriz.", ""));
                return null;
            }
           return eliminarCM();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError( "No se pudo eliminar la cuenta: " + cuentaMatriz.getNumeroCuenta() + " " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
        return "";
    }

    /**
     * M&eacute;todo para eliminar los registros de subedificios asociados a una
     * CM
     *
     * @param subEdificios {@link List}&lt;{@link CmtSubEdificioMgl> listado de subedificios a eliminar
     * @return {@link List}&lt;{@link CmtSubEdificioMgl> listado de subedificios que no se pudieron eliminar
     * @throws ApplicationException Excepcion lanzada al no poder procesar
     */
    private List<CmtSubEdificioMgl> eliminarSubEdificios(
            List<CmtSubEdificioMgl> subEdificios)  {
         List<CmtSubEdificioMgl> noProcesados = new ArrayList<CmtSubEdificioMgl>();
        try {
           
            for (CmtSubEdificioMgl sub : subEdificios) {
                if (!subEdificioMglFacadeLocal.delete(sub)) {
                    noProcesados.add(sub);
                }
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
        return noProcesados;
    }

    private boolean validarInventario() {
        
        boolean enviarInformacionRR = false;
        try {
            ParametrosMgl parametroHabilitarRR = parametrosMglFacadeLocal.findParametroMgl(Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                enviarInformacionRR = true;
            }
            if (enviarInformacionRR) {
                if (cuentaMatrizMglFacadeLocal.existeInventarioCuentaMatriz(cuentaMatriz)) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    String.format("Existe inventario asociado a la cuenta matriz %s. No se puede realizar el proceso.",
                                            cuentaMatriz.getCuentaMatrizId()), ""));
                    return true;
                }
            }

            ordenesTrabajo = otFacadeLocal.ordenesTrabajoActivas(cuentaMatriz);
            
            if (ordenesTrabajo != null && !ordenesTrabajo.isEmpty()) {
                String encabezado = "Existen ordenes de trabajo activas asociadas  "
                        + "a la cuenta matriz: " + cuentaMatriz.getCuentaMatrizId() + ".";

                String mensajeFinal = "";

                for (CmtOrdenTrabajoMgl ordenesActivas : ordenesTrabajo) {

                    String msn = "Se encuentran la orden: " + ordenesActivas.getIdOt() + "  "
                            + "en estado interno: "
                            + "" + ordenesActivas.getEstadoInternoObj().getNombreBasica() + " "
                            + "y en estado externo: ABIERTO ";
                    mensajeFinal += msn;
                }
                String fin = ".No se puede eliminar la CCMM";
                
                FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            encabezado + mensajeFinal + fin, ""));
                return true;
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /**
     * M&eacute;todo para rechazo de solicitudes de eliminaci&oacute;n
     */
    private void rechazarEliminacion() {
        try {
            cambiarEstadoSolicitud(Constant.BASICA_ELIMINAR_CM_RECHAZO);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    String.format("Se ha rechazado la solicitud de eliminación para la cuenta matriz ",
                    cuentaMatriz.getCuentaMatrizId()), ""));
       
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * M&eacute;todo para cambiar el estado de la solicitud despu&eacute;s de la
     * gesti&oacute;n
     *
     * @param resultado {@link String} resultado de la gesti&oacute;n
     * @throws ApplicationException Excepci&oacute;n lanzada en la
     * gesti&oacute;n
     */
    private void cambiarEstadoSolicitud(final String resultado)  {
        try {
            solicitudCmMgl.setFechaGestion(new Date());
            CmtBasicaMgl estado = new CmtBasicaMgl();
            estado.setBasicaId(cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_FINALIZADO).getBasicaId());
            solicitudCmMgl.setEstadoSolicitudObj(estado);
            solicitudCmMgl.setDisponibilidadGestion("0");
            solicitudCmMgl.setResultGestion(cmtBasicaMglFacadeLocal.findByCodigoInternoApp(resultado));       
            if (usuarioLogin.getCedula() != null) {
                solicitudCmMgl.setUsuarioGestionId(new BigDecimal(usuarioLogin.getCedula()));
            }
            solicitudCmMglFacadeLocal.updateCm(solicitudCmMgl);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * M&eacute;todo para realizar el escalamiento de la solicitud
     */
    private void realizarEscalamiento() {
        try {
            solicitudCmMgl.setDisponibilidadGestion("1");
            CmtBasicaMgl estadoPenEsc = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_ESCALADO_ACO);
            if (estadoPenEsc != null) {
                solicitudCmMgl.setEstadoSolicitudObj(estadoPenEsc);
            }
            if (accion != null) {
                CmtBasicaMgl Accion = cmtBasicaMglFacadeLocal.findById(accion);
                if (Accion != null) {
                    solicitudCmMgl.setResultGestion(Accion);
                    solicitudCmMgl.setRespuestaActual(Accion.getNombreBasica());
                }
            }
            if (usuarioLogin.getCedula() != null) {
                solicitudCmMgl.setUsuarioGestionId(new BigDecimal(usuarioLogin.getCedula()));
            }
            solicitudCmMglFacadeLocal.update(solicitudCmMgl);
            ordenesTrabajo = otFacadeLocal.ordenesTrabajoActivas(cuentaMatriz);
            
           FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    String.format("Se ha escalado la solicitud de la cuenta matriz  "
                    + "No en MGL: %s y No en RR: %s a acometidas. <b>",
                    cuentaMatriz.getCuentaMatrizId(), cuentaMatriz.getNumeroCuenta() + "</b>"), ""));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(PROCESO_FALLIDO + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    private void finalizarEscalamiento() {
        try {
            solicitudCmMgl.setTipoSolicitudObj(cmtTipoSolicitudMglFacadeLocal.obtenerSolicitudEliminacion());
            solicitudCmMgl.setDisponibilidadGestion("1");
            
            if (accion != null) {
                CmtBasicaMgl Accion = cmtBasicaMglFacadeLocal.findById(accion);
                if (Accion != null) {
                    solicitudCmMgl.setResultGestion(Accion);
                    solicitudCmMgl.setRespuestaActual(Accion.getNombreBasica());
                }
            }
            if (usuarioLogin.getCedula() != null) {
                solicitudCmMgl.setUsuarioGestionId(new BigDecimal(usuarioLogin.getCedula()));
            }
            solicitudCmMglFacadeLocal.update(solicitudCmMgl);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    String.format("La solicitud de la cuenta matriz %s ha quedado pendiente de eliminación.",
                    "<b>" + cuentaMatriz.getCuentaMatrizId()+"</b>"), ""));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(PROCESO_FALLIDO + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void cancelarOrdenTrabajo(CmtOrdenTrabajoMgl orden) {
        try {
            solicitudCmMglFacadeLocal.setUser(usuarioVT, perfilVT);
            solicitudSubEdificioMglFacadeLocal.setUser(usuarioVT, perfilVT);

            CmtEstadoxFlujoMgl estadoxFlujoMgl = estadoxFlujoMglFacadeLocal.
                    findByTipoFlujoAndTecnoAndCancelado(orden.getTipoOtObj().getTipoFlujoOt(),
                    orden.getBasicaIdTecnologia());

            if (estadoxFlujoMgl != null) {
                //validamos que la orden no tenga agendamientos pendientes

                CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = estadoxFlujoMglFacadeLocal.
                        findByTipoFlujoAndEstadoInt(orden.getTipoOtObj().getTipoFlujoOt(),
                        orden.getEstadoInternoObj(),
                        orden.getBasicaIdTecnologia());

                String subTipoWorForce = "";

                if (cmtEstadoxFlujoMgl != null) {
                    if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                        subTipoWorForce = cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC().getNombreBasica();
                    }
                }
                if (!subTipoWorForce.isEmpty()) {
                    List<CmtAgendamientoMgl> agendas = cmtAgendamientoMglFacadeLocal.
                            agendasPendientesByOrdenAndSubTipoWorkfoce(orden, subTipoWorForce);
                    if (agendas.size() > 0) {
                        boolean ultimaAge = false;
                        for (CmtAgendamientoMgl agenda : agendas) {
                            if (agenda.getUltimaAgenda().equalsIgnoreCase("Y")) {
                                ultimaAge = true;
                            }
                        }
                        if (ultimaAge) {
                            LOGGER.info("NO HAY AGENDAS PENDIENTES");
                            otFacadeLocal.actualizarOt(orden, estadoxFlujoMgl.getEstadoInternoObj().getBasicaId());
                            String msg = "Se ha cancelado la orden de trabajo No: " + orden.getIdOt() + "";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    msg, ""));
                            ordenesTrabajo = otFacadeLocal.ordenesTrabajoActivas(cuentaMatriz);
                        } else {
                            String msg = "La orden de trabajo tiene agendas pendientes por cerrar";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                            ordenesTrabajo = otFacadeLocal.ordenesTrabajoActivas(cuentaMatriz);
                        }
                    }
                } else {
                      otFacadeLocal.actualizarOt(orden, estadoxFlujoMgl.getEstadoInternoObj().getBasicaId());
                      String msg = "Se ha cancelado la orden de trabajo No: "+orden.getIdOt()+"";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    msg, ""));
                     ordenesTrabajo = otFacadeLocal.ordenesTrabajoActivas(cuentaMatriz);
                }

            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error cancelando la OT" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public String obtenerOrigen() {
        try {
            if (solicitudCmMgl.getOrigenSolicitud().getBasicaId() != null) {
                solicitudCmMgl.setOrigenSolicitud(
                        cmtBasicaMglFacadeLocal.findById(
                        solicitudCmMgl.getOrigenSolicitud().getBasicaId()));
            }
        } catch (ApplicationException e) {
            String msg = "Error al buscar el origen seleccionado:...";
            FacesUtil.mostrarMensajeError(msg + e.getMessage(), e, LOGGER);
            LOGGER.error(msg);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS & SETTERS">
    public BigDecimal getIdCuentaMatriz() {
        return idCuentaMatriz;
    }

    public void setIdCuentaMatriz(BigDecimal idCuentaMatriz) {
        this.idCuentaMatriz = idCuentaMatriz;
    }

    public String getUsuarioVT() {
        return usuarioVT;
    }

    public void setUsuarioVT(String usuarioVT) {
        this.usuarioVT = usuarioVT;
    }

    public int getPerfilVT() {
        return perfilVT;
    }

    public void setPerfilVT(int perfilVT) {
        this.perfilVT = perfilVT;
    }

    public ConfigurationAddressComponent getConfigurationAddressComponent() {
        return configurationAddressComponent;
    }

    public void setConfigurationAddressComponent(ConfigurationAddressComponent configurationAddressComponent) {
        this.configurationAddressComponent = configurationAddressComponent;
    }

    public List<DrDireccion> getBarriosMultiorigen() {
        return barriosMultiorigen;
    }

    public void setBarriosMultiorigen(List<DrDireccion> barriosMultiorigen) {
        this.barriosMultiorigen = barriosMultiorigen;
    }

    public CmtSolicitudCmMgl getSolicitudCmMgl() {
        return solicitudCmMgl;
    }

    public void setSolicitudCmMgl(CmtSolicitudCmMgl solicitudCmMgl) {
        this.solicitudCmMgl = solicitudCmMgl;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public List<CmtBasicaMgl> getTipoSolicitudBasicaList() {
        return tipoSolicitudBasicaList;
    }

    public void setTipoSolicitudBasicaList(List<CmtBasicaMgl> tipoSolicitudBasicaList) {
        this.tipoSolicitudBasicaList = tipoSolicitudBasicaList;
    }

    public String getRrRegional() {
        return rrRegional;
    }

    public void setRrRegional(String rrRegional) {
        this.rrRegional = rrRegional;
    }

    public List<CmtRegionalRr> getRegionalList() {
        return regionalList;
    }

    public void setRegionalList(List<CmtRegionalRr> regionalList) {
        this.regionalList = regionalList;
    }

    public String getRrCiudad() {
        return rrCiudad;
    }

    public void setRrCiudad(String rrCiudad) {
        this.rrCiudad = rrCiudad;
    }

    public List<RrCiudades> getCiudadesList() {
        return ciudadesList;
    }

    public void setCiudadesList(List<RrCiudades> ciudadesList) {
        this.ciudadesList = ciudadesList;
    }

    public BigDecimal getIdCentroPoblado() {
        return idCentroPoblado;
    }

    public void setIdCentroPoblado(BigDecimal idCentroPoblado) {
        this.idCentroPoblado = idCentroPoblado;
    }

    public List<GeograficoPoliticoMgl> getCentroPobladoList() {
        return centroPobladoList;
    }

    public void setCentroPobladoList(List<GeograficoPoliticoMgl> centroPobladoList) {
        this.centroPobladoList = centroPobladoList;
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public Boolean getShowCKPanel() {
        return showCKPanel;
    }

    public void setShowCKPanel(Boolean showCKPanel) {
        this.showCKPanel = showCKPanel;
    }

    public Boolean getShowBMPanel() {
        return showBMPanel;
    }

    public void setShowBMPanel(Boolean showBMPanel) {
        this.showBMPanel = showBMPanel;
    }

    public Boolean getShowITPanel() {
        return showITPanel;
    }

    public void setShowITPanel(Boolean showITPanel) {
        this.showITPanel = showITPanel;
    }

    public String getTipoAccionSolicitud() {
        return tipoAccionSolicitud;
    }

    public void setTipoAccionSolicitud(String tipoAccionSolicitud) {
        this.tipoAccionSolicitud = tipoAccionSolicitud;
    }

    public List<CmtBasicaMgl> getTipoAccionSolicitudBasicaList() {
        return tipoAccionSolicitudBasicaList;
    }

    public void setTipoAccionSolicitudBasicaList(List<CmtBasicaMgl> tipoAccionSolicitudBasicaList) {
        this.tipoAccionSolicitudBasicaList = tipoAccionSolicitudBasicaList;
    }

    public BigDecimal getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(BigDecimal solicitud) {
        this.solicitud = solicitud;
    }

    public List<ParametrosCalles> getTipoSolicitudList() {
        return tipoSolicitudList;
    }

    public void setTipoSolicitudList(List<ParametrosCalles> tipoSolicitudList) {
        this.tipoSolicitudList = tipoSolicitudList;
    }

    public String getDireccionCk() {
        return direccionCk;
    }

    public void setDireccionCk(String direccionCk) {
        this.direccionCk = direccionCk;
    }

    public String getDireccionLabel() {
        return direccionLabel;
    }

    public void setDireccionLabel(String direccionLabel) {
        this.direccionLabel = direccionLabel;
    }

    public List<OpcionIdNombre> getBmList() {
        return bmList;
    }

    public void setBmList(List<OpcionIdNombre> bmList) {
        this.bmList = bmList;
    }

    public String getTipoNivelBm() {
        return tipoNivelBm;
    }

    public void setTipoNivelBm(String tipoNivelBm) {
        this.tipoNivelBm = tipoNivelBm;
    }

    public String getNivelValorBm() {
        return nivelValorBm;
    }

    public void setNivelValorBm(String nivelValorBm) {
        this.nivelValorBm = nivelValorBm;
    }

    public List<OpcionIdNombre> getItList() {
        return itList;
    }

    public void setItList(List<OpcionIdNombre> itList) {
        this.itList = itList;
    }

    public String getTipoNivelIt() {
        return tipoNivelIt;
    }

    public void setTipoNivelIt(String tipoNivelIt) {
        this.tipoNivelIt = tipoNivelIt;
    }

    public String getNivelValorIt() {
        return nivelValorIt;
    }

    public void setNivelValorIt(String nivelValorIt) {
        this.nivelValorIt = nivelValorIt;
    }

    public List<CmtBasicaMgl> getOrigenSolicitudList() {
        return origenSolicitudList;
    }

    public void setOrigenSolicitudList(List<CmtBasicaMgl> origenSolicitudList) {
        this.origenSolicitudList = origenSolicitudList;
    }

    public Boolean getValidacion() {
        return validacion;
    }

    public void setValidacion(Boolean validacion) {
        this.validacion = validacion;
    }

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    public CmtSolicitudSubEdificioMgl getSolicitudSub() {
        return solicitudSub;
    }

    public void setSolicitudSub(CmtSolicitudSubEdificioMgl solicitudSub) {
        this.solicitudSub = solicitudSub;
    }

    public Boolean getMultiEdificio() {
        return multiEdificio;
    }

    public void setMultiEdificio(Boolean multiEdificio) {
        this.multiEdificio = multiEdificio;
    }

    public Boolean getEscaladoAcometida() {
        
        CmtBasicaMgl estadoPenEsc;
        boolean resultado = false;
        try {
            estadoPenEsc = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_ESCALADO_ACO);
            if (estadoPenEsc != null) {
                if (solicitudCmMgl.getEstadoSolicitudObj().getBasicaId().
                        compareTo(estadoPenEsc.getBasicaId()) == 0) {
                    resultado = true;
                }
            }
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        return resultado;
        // return ConstantsCM.TIPO_SOLICITUD_ELIMINACION_ESCALAMIENTO
    }

    public List<CmtOrdenTrabajoMgl> getOrdenesTrabajo() {
        return ordenesTrabajo;
    }

    public List<CmtTipoBasicaMgl> getTiposAccion() {
        return tiposAccion;
    }

    public BigDecimal getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(BigDecimal tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public List<CmtBasicaMgl> getAcciones() {
        return acciones;
    }

    public BigDecimal getAccion() {
        return accion;
    }

    public void setAccion(BigDecimal accion) {
        this.accion = accion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    //</editor-fold>
    public EncabezadoSolicitudModificacionCMBean getEncabezadoSolModiCMBean() {
        return encabezadoSolModiCMBean;
    }

    public void setEncabezadoSolModiCMBean(EncabezadoSolicitudModificacionCMBean encabezadoSolModiCMBean) {
        this.encabezadoSolModiCMBean = encabezadoSolModiCMBean;
    }

    public UsuariosServicesDTO getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(UsuariosServicesDTO usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
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

    public GeograficoPoliticoMgl getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(GeograficoPoliticoMgl centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public BigDecimal getCentropoblado() {
        return centropoblado;
    }

    public void setCentropoblado(BigDecimal centropoblado) {
        this.centropoblado = centropoblado;
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

    public List<CmtCuentaMatrizMgl> getListcuentaMatriz() {
        return listcuentaMatriz;
    }

    public void setListcuentaMatriz(List<CmtCuentaMatrizMgl> listcuentaMatriz) {
        this.listcuentaMatriz = listcuentaMatriz;
    }

    public Boolean getValidacionLimpiarDireccion() {
        return validacionLimpiarDireccion;
    }

    public void setValidacionLimpiarDireccion(Boolean validacionLimpiarDireccion) {
        this.validacionLimpiarDireccion = validacionLimpiarDireccion;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public String getCantidadSubedificios() {
        return cantidadSubedificios;
    }

    public void setCantidadSubedificios(String cantidadSubedificios) {
        this.cantidadSubedificios = cantidadSubedificios;
    }

    public int getCantidadHhpp() {
        return cantidadHhpp;
    }

    public void setCantidadHhpp(int cantidadHhpp) {
        this.cantidadHhpp = cantidadHhpp;
    }

    public CmtCuentaMatrizMgl getCuentaMatriz() {
        return cuentaMatriz;
    }

    public void setCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) {
        this.cuentaMatriz = cuentaMatriz;
    }
    
    private boolean validateRequiredInputFields() {
        
        
        if (!StringUtils.isTextValue(solicitudCmMgl.getAreaSolictud())) {
            MessageUtils.buildFacesMessage(FacesMessage.SEVERITY_ERROR, "El campo Área solo acepta letras");
            return false;
        }

        if (!StringUtils.isTextValue(solicitudCmMgl.getAsesor())) {
            MessageUtils.buildFacesMessage(FacesMessage.SEVERITY_ERROR, "El campo Asesor solo acepta letras");
            return false;
        }

        if (!StringUtils.isValidMail(solicitudCmMgl.getCorreoAsesor())) {
            MessageUtils.buildFacesMessage(FacesMessage.SEVERITY_ERROR, "El campo Correo Asesor es invalido");
            return false;
        }

        if (!StringUtils.isNumberText(solicitudCmMgl.getTelefonoAsesor())) {
            MessageUtils.buildFacesMessage(FacesMessage.SEVERITY_ERROR, "El campo Teléfono Asesor debe ser numeríco");
            return false;

        } else if (!StringUtils.isMinAndMaxRangeValid(solicitudCmMgl.getTelefonoAsesor(), 7, 10)) {
            MessageUtils.buildFacesMessage(FacesMessage.SEVERITY_ERROR, "El campo Teléfono Asesor no puede debe estar entre 7 a 10 digitos");
            return false;
        }         
     
       return true;
    }
    
    public boolean validarDominioCorreos(String correoCopia) {
        boolean resulto = false;
        if (correoCopia != null && !correoCopia.isEmpty()) {
            if (correoCopia.toLowerCase().contains("claro.com.co")
                    || correoCopia.toLowerCase().contains("telmex.com.co")
                    || correoCopia.toLowerCase().contains("comcel.com.co")
                    || correoCopia.toLowerCase().contains("telmex.com")
                    || correoCopia.toLowerCase().contains("telmexla.com")) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
    
    public boolean validarCancelarOt() {
        return validarPermisos(FORMULARIO_ELIMINAR_CM, ValidacionUtil.OPC_EDICION);
    }

    private boolean validarPermisos(String formulario, String accion) {
        try {
            return ValidacionUtil.validarVisualizacion(formulario, accion,
                    cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError( Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION+ e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
    public String escribeComentario(){
     
        CmtBasicaMgl Accion;
        CmtTipoBasicaMgl tipoBasicaMgl;

        try {
            if (tipoAccion != null) {
                tipoBasicaMgl = cmtTipoBasicaMglFacadeLocal.findByTipoBasicaId(tipoAccion);
                if (tipoBasicaMgl != null) {
                    if (tipoBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase("@TB_E_CM")) {
                        Accion = cmtBasicaMglFacadeLocal.findById(accion);
                        if (Accion != null) {
                            if (Accion.getIdentificadorInternoApp().equalsIgnoreCase("@B_E_CM_H")) {
                                comentario = Accion.getNombreBasica() + ": Se elimina la cuenta matriz con numero"
                                        + " en MGL: " + cuentaMatriz.getCuentaMatrizId() + " y numero "
                                        + " en RR: " + cuentaMatriz.getNumeroCuenta() + " y los HHPP asociados.";
                            } else if (Accion.getIdentificadorInternoApp().equalsIgnoreCase("@B_EL_CM")) {
                                comentario = Accion.getNombreBasica() + ": Se elimina la cuenta matriz con numero"
                                        + " en MGL: " + cuentaMatriz.getCuentaMatrizId() + " y numero "
                                        + " en RR: " + cuentaMatriz.getNumeroCuenta()+".";
                            }
                        }
                    } else if (tipoBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase("@TB_E_A")) {
                        Accion = cmtBasicaMglFacadeLocal.findById(accion);
                        if (Accion != null) {
                            if (Accion.getIdentificadorInternoApp().equalsIgnoreCase("@B_E_E_A")) {
                                comentario = Accion.getNombreBasica() + ": Se escala acometidas la cuenta matriz con numero"
                                        + " en MGL: " + cuentaMatriz.getCuentaMatrizId() + " y numero "
                                        + " en RR: " + cuentaMatriz.getNumeroCuenta() + " por equipos asociados.";
                            } else if (Accion.getIdentificadorInternoApp().equalsIgnoreCase("@B_E_H_S")) {
                                comentario = Accion.getNombreBasica() + ": Se escala acometidas la cuenta matriz con numero"
                                        + " en MGL: " + cuentaMatriz.getCuentaMatrizId() + " y numero "
                                        + " en RR: " + cuentaMatriz.getNumeroCuenta() + " por HHPP en servicio. ";
                            }
                        }
                    } else if (tipoBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase("@TB_E_H")) {
                        Accion = cmtBasicaMglFacadeLocal.findById(accion);
                        if (Accion != null) {
                            if (Accion.getIdentificadorInternoApp().equalsIgnoreCase("@B_E_E_D")) {
                                comentario = Accion.getNombreBasica() + ": Se escala HHPP la cuenta matriz con numero"
                                        + " en MGL: " + cuentaMatriz.getCuentaMatrizId() + " y numero "
                                        + " en RR: " + cuentaMatriz.getNumeroCuenta() + " por equipos desasociados.";
                            } else if (Accion.getIdentificadorInternoApp().equalsIgnoreCase("@B_E_H_SD")) {
                                comentario = Accion.getNombreBasica() + ": Se escala HHPP la cuenta matriz con numero"
                                        + " en MGL: " + cuentaMatriz.getCuentaMatrizId() + " y numero "
                                        + " en RR: " + cuentaMatriz.getNumeroCuenta() + " por HHPP en servicio desasociados. ";
                            }
                        }
                    } else if (tipoBasicaMgl.getIdentificadorInternoApp().equalsIgnoreCase("@TB_E_R")) {
                        Accion = cmtBasicaMglFacadeLocal.findById(accion);
                        if (Accion != null) {
                            if (Accion.getIdentificadorInternoApp().equalsIgnoreCase("@B_E_R_A")) {
                                comentario = Accion.getNombreBasica() + ": Se rechaza la eliminacion de la cuenta matriz con numero"
                                        + " en MGL: <b>" + cuentaMatriz.getCuentaMatrizId() + "</b> y numero "
                                        + " en RR: <b>" + cuentaMatriz.getNumeroCuenta() + "</b> por equipos asociados.";
                            } else if (Accion.getIdentificadorInternoApp().equalsIgnoreCase("@B_E_R_H")) {
                                comentario = Accion.getNombreBasica() + ": Se rechaza la eliminacion la cuenta matriz con numero"
                                        + " en MGL: <b>" + cuentaMatriz.getCuentaMatrizId() + "</b> y numero "
                                        + " en RR: <b>" + cuentaMatriz.getNumeroCuenta() + "</b> por HHPP en servicio. ";
                            }
                        }
                    }

                }


            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(EliminarCmBean.class.getName() + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EliminarCmBean class ..." + e.getMessage(), e, LOGGER);
        }

        return comentario;

    }
                                 /**
     * cardenaslb metodo para validar correo
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

    public boolean isBotonCrear() {
        return botonCrear;
    }

    public void setBotonCrear(boolean botonCrear) {
        this.botonCrear = botonCrear;
    }
    
    public boolean validarEliminarCM() {
        return validarEdicion(VALIDARELIMINARCM);
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

    
    

}
