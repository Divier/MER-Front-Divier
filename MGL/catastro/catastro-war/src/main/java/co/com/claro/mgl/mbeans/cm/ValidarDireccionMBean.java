/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

/**
 *
 * @author cardenaslb
 */
import co.com.claro.mgl.businessmanager.cm.CmtDireccionMglManager;
import co.com.claro.mgl.dtos.ResponseValidarDireccionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.DireccionRRFacadeLocal;
import co.com.claro.mgl.facade.DireccionesValidacionFacadeLocal;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.PcmlFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtComponenteDireccionesMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionDetalleMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtValidadorDireccionesFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.ConfigurationAddressComponent;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.OpcionIdNombre;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHhppVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.mbeans.cm.ot.OtMglBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseMesaje;
import co.com.claro.mgl.ws.cm.response.ResponseValidacionDireccion;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.telmex.catastro.data.AddressSuggested;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

@ManagedBean(name = "validarDireccionMBean")
@SessionScoped
public class ValidarDireccionMBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private DrDireccion direccionValidar;
    private boolean mostrarPopupSub = false;
    private boolean mostrarPopupHhpp = false;
    List<String> listBarrios = new ArrayList<String>();
    private boolean multiorigen = false;
    private ConfigurationAddressComponent configurationAddressComponent;
    private Object paternBean;
    private Class<?> classOfPaternsBean;
    private static final Logger LOGGER = LogManager.getLogger(ValidarDireccionMBean.class);
    private GeograficoPoliticoMgl ciudadDelBean;
    private String barrioDelBean = "";
    private String direccionDelBean = "";
    private ResponseValidarDireccionDto responseValidarDireccionDto;
    private boolean direccionValidada = false;
    private String direccionValida;
    private List<OpcionIdNombre> ckList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> itList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> aptoList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> complementoList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> bmList = new ArrayList<OpcionIdNombre>();
    private boolean showCKPanel;
    private boolean showBMPanel;
    private boolean showITPanel;
    private boolean showApartComp;
    private boolean enableTabs;
    private boolean barrioSugeridoPanel;
    private boolean deshabilitarValidar;
    private boolean apartamentoIngresado;
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    private String usuarioVT = null;
    private String cedulaUsuarioVT = null;
    private String tipoDireccion;
    private DrDireccion drDireccion = new DrDireccion();
    private ResponseConstruccionDireccion responseConstruirDireccion = new ResponseConstruccionDireccion();
    private String direccionCk;
    private String nivelValorIt;
    private String nivelValorBm;
    private String tipoNivelBm;
    private String tipoNivelIt;
    private String tipoNivelApartamento;
    private String tipoNivelComplemento;
    private String direccionLabel;
    private String apartamento;
    private String complemento;
    private boolean unidadesPredio;
    private boolean crearSolicitudButton;
    private BigDecimal centroPoblado;
    private GeograficoPoliticoMgl codigoDane;
    private String tecnologia;
    private boolean direccionAmbiguaPanel;
    private boolean bloquearCambioAmbigua;
    private boolean ambiguaAntigua;
    private boolean ambiguaNueva;
    private String dirEstado;
    private boolean direccionConstruida;
    private boolean barrioSugeridoField;
    private String barrioSugeridoStr;
    private DrDireccion drDireccionCuentaMatriz;
    private String tipoSolicitud;
    private String tipoAccionSolicitud;
    private boolean habilitarViabilidad;
    private boolean cambioDireccionPanel;
    private List<AddressSuggested> barrioSugeridoList;
    private List<UnidadStructPcml> unidadList;
    private List<UnidadStructPcml> unidadAuxiliarList;
    private ResponseValidacionDireccion responseValidarDireccion = new ResponseValidacionDireccion();
    private CmtCuentaMatrizMgl cuentaMatrizOtraDireccion;
    private boolean cuentaMatrizActiva;
    private boolean deshabilitarCreacion;
    private String barrioSugerido;
    private boolean enableApto;
    private List<OpcionIdNombre> listNivel5;
    private List<OpcionIdNombre> listNivel6;
    private int perfilVt = 0;
    private String espacio = "&nbsp;";
    private SecurityLogin securityLogin;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private RequestConstruccionDireccion request = new RequestConstruccionDireccion();
    private CuentaMatrizBean cuentaMatrizBean;
    private OtMglBean otMglBean;
    private List<ParametrosCalles> dirNivel5List;
    private List<ParametrosCalles> dirNivel6List;
    private String complementoDir;
    private String apartamentoDir;
    private VisitaTecnicaBean visitaTecnicaBean;
    private SubEdificiosVtBean subEdificiosVtBean;
    private TecnologiaHabilitadaVtBean tecnologiaHabilitadaVtBean;
    private CmtSubEdificiosVt subEdifSelected;
    DireccionRREntity entityResponse = new DireccionRREntity();
    
    
    @EJB
    private DireccionRRFacadeLocal direccionRRFacadeLocal;
    @EJB
    private DireccionesValidacionFacadeLocal direccionesValidacionFacadeLocal;
    @EJB
    private HhppMglFacadeLocal hhppMglFacadeLocal;
    @EJB
    private PcmlFacadeLocal pcmlFacadeLocal;
    @EJB
    private CmtComponenteDireccionesMglFacadeLocal componenteDireccionesMglFacade;
    @EJB
    private CmtValidadorDireccionesFacadeLocal direccionesFacadeLocal;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMglFacade;

    /**
     * Creates a new instance of ValidarDireccionMBean
     */
    public ValidarDireccionMBean() {

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

            if (usuarioVT == null) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
            }

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al ValidarDireccionMBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ValidarDireccionMBean. " + e.getMessage(), e, LOGGER);
        }
    }

    public void init() {

        hideTipoDireccion();
        showCKPanel = true;
        enableTabs = false;
        apartamentoIngresado = false;

    }

    public <A> void validarDireccion(DrDireccion drDireccion, String direccion, GeograficoPoliticoMgl ciudad, String barrio, A patern, Class<A> classOfBean)  {
        try {
            //multiedificio
             tecnologiaHabilitadaVtBean = (TecnologiaHabilitadaVtBean) JSFUtil.getSessionBean(TecnologiaHabilitadaVtBean.class);
            tecnologiaHabilitadaVtBean = (TecnologiaHabilitadaVtBean) JSFUtil.getSessionBean(TecnologiaHabilitadaVtBean.class);
            visitaTecnicaBean = (VisitaTecnicaBean) JSFUtil.getSessionBean(VisitaTecnicaBean.class);
            tecnologia = visitaTecnicaBean.getVt().getOtObj().getBasicaIdTecnologia().getCodigoBasica();

            cargarListadosConfiguracion();
            ApplicationBean applicationBean = (ApplicationBean) JSFUtil
                    .getBean(ConstantsCM.APPLICATION_MANAGEDBEAN);
            configurationAddressComponent = applicationBean
                    .getConfigurationAddressComponent();
            paternBean = patern;
            classOfPaternsBean = classOfBean;

            direccionValidar = drDireccion.clone();
            ciudadDelBean = ciudad;
            barrioDelBean = barrio;
            direccionDelBean = direccion ;
            validarDireccionFunction();
            
        } catch (CloneNotSupportedException e) {
            String msn = Constant.MSN_PROCESO_FALLO ;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidarDireccionMBean: validarDireccion(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void validarDireccionFunction() {
        try {
            responseValidarDireccionDto = direccionesFacadeLocal.validarDireccion(
                    direccionValidar, direccionDelBean, ciudadDelBean, barrioDelBean, false);
            if (responseValidarDireccionDto.getValidationMessages() != null
                    && !responseValidarDireccionDto.getValidationMessages().isEmpty()
                    && responseValidarDireccionDto.getValidationMessages().size() > 0) {
                for (String singleMessage : responseValidarDireccionDto.getValidationMessages()) {
                    String msn = singleMessage;
                    if (responseValidarDireccionDto.isValidacionExitosa()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    }
                }
                responseValidarDireccionDto.getValidationMessages().clear();
            }
            if (responseValidarDireccionDto.isValidacionExitosa()) {
                direccionValidada = true;
            } else {
                direccionValidada = false;
            }
            // LC
            entityResponse = responseValidarDireccionDto.getDireccionRREntity();
            direccionValidar = responseValidarDireccionDto.getDrDireccion();
            
                multiorigen = responseValidarDireccionDto.isMultiOrigen();
                if (responseValidarDireccionDto.isIntradusible()
                        || responseValidarDireccionDto.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("IT")
                        || responseValidarDireccionDto.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("BM")) {
                    direccionDelBean = "";
                    mostrarPopupSub = true;
                    
                } else {
                    mostrarPopupSub = false;
                    tecnologiaHabilitadaVtBean.setHabilitarColums(true);
                    reloadPatern();
                }
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidarDireccionMBean: validarDireccionFunction(). " + e.getMessage(), e, LOGGER);
        }
    }

    private void reloadPatern() {
        String nombreMetodoRecarga = "recargar";
        Method methodReload;
        try {
            methodReload = classOfPaternsBean.getMethod(nombreMetodoRecarga, ResponseValidarDireccionDto.class);
            methodReload.invoke(paternBean, responseValidarDireccionDto);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ValidarDireccionMBean: reloadPatern(). " + e.getMessage(), e, LOGGER);
        }

    }

    // inicio validar metodos
    public String Aceptar() {
        barrioDelBean = direccionValidar.getBarrio();

        if (ciudadDelBean.getGpoMultiorigen().equalsIgnoreCase("1") && !direccionValidar.getIdTipoDireccion().equalsIgnoreCase("BM") && !direccionValidar.getIdTipoDireccion().equalsIgnoreCase("IT")) {
            if (barrioDelBean == null || barrioDelBean.isEmpty()) {
                String msn = "El barrio es necesario para ciudades Multiorigen";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
        }
        reloadPatern();
        return null;
    }

    /**
     * Funcion utilizada para obtener los listados utilizados en la pantalla
     * desde la base de datos
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void cargarListadosConfiguracion()  {
        try {
            //Obtiene lista de listas de los componentes de la dirección.          
            configurationAddressComponent =
                    componenteDireccionesMglFacade
                    .getConfiguracionComponente(Constant.RR_DIR_CREA_HHPP);

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
            String msn = "Error al realizar consultas para obtener configuración "
                    + "de listados.";
             FacesUtil.mostrarMensajeError(msn+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidarDireccionMBean:cargarListadosConfiguracion() . " + e.getMessage(), e, LOGGER);
        }
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

    public String ocultar() {
        limpiarCamposTipoDireccion();
        mostrarPopupSub = false;
        return null;
    }


    public String validar() {
        barrioDelBean = direccionValidar.getBarrio();
        validarDireccionFunction();
        reloadPatern();
        return null;
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
            LOGGER.error("Error al validar el tipo de dirección seleccionado ", e);
            String msn = "Error al validar el tipo de dirección seleccionado- ValidarDireccionMBean:validarTipoDireccion().";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
        }
    }

    /**
     * Función que limpiar los valores de la pantalla de tipo dirección
     *
     * @author Juan David Hernandez
     */
    public void limpiarCamposTipoDireccion() {
        drDireccion = new DrDireccion();
        responseConstruirDireccion.setDrDireccion(null);
        direccionCk = "";
        nivelValorIt = "";
        nivelValorBm = "";
        tipoNivelBm = ";";
        tipoNivelIt = "";
        tipoNivelApartamento = "";
        tipoNivelComplemento = "";
        direccionLabel = "";
        apartamento = "";
        complemento = "";
        unidadesPredio = false;
        crearSolicitudButton = false;
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
        showApartComp = true;
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
        showApartComp = true;
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
        showApartComp = true;
        
    }

    /**
     * Función utilizada para construir dirección de tipo calle-carrera
     *
     * @author Juan David Hernandez
     * @param ambigua
     */
    public void construirDireccionCk(boolean ambigua) {
        try {
            visitaTecnicaBean = (VisitaTecnicaBean) JSFUtil.getSessionBean(VisitaTecnicaBean.class);
            codigoDane = visitaTecnicaBean.getVt().getOtObj().getCmObj().getCentroPoblado();
            centroPoblado = visitaTecnicaBean.getVt().getOtObj().getCmObj().getCentroPoblado().getGpoId();
            tecnologia = visitaTecnicaBean.getVt().getOtObj().getBasicaIdTecnologia().getCodigoBasica();
          
            cargarListadosConfiguracion();
            if (validarDatosDireccionCk()) {
                request.setDireccionStr(direccionCk);
                request.setComunidad(codigoDane.getGeoCodigoDane());
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);

                request.setTipoAdicion("N");
                request.setTipoNivel("N");
                request.setIdUsuario(usuarioLogin.getCedula().toString());

                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion
                            .getDrDireccion());
                }
                //Consume servicio que retorna la dirección calle-carrera traducida.
                responseConstruirDireccion = direccionRRFacadeLocal.
                        construirDireccionSolicitud(request);

                //Direccion traducida correctamente
                if (responseConstruirDireccion.getDireccionStr() != null
                        && !responseConstruirDireccion.getDireccionStr().isEmpty()
                        && responseConstruirDireccion.getResponseMesaje().
                        getTipoRespuesta().equalsIgnoreCase("I")) {

                    if (!ambigua && !direccionAmbiguaPanel) {

                        List<String> ambiguaList =
                                direccionesValidacionFacadeLocal
                                .validarDireccionAmbigua(request.getComunidad(),
                                request.getBarrio(),
                                responseConstruirDireccion.getDrDireccion());

                        if ((ambiguaList == null || ambiguaList.isEmpty())
                                || (ambiguaList.get(0).equals("0"))) {
                            direccionAmbiguaPanel = false;
                            bloquearCambioAmbigua = false;
                        } else {
                            if (ambiguaList.get(0).equals("1")) {
                                String msn = "La dirección ingresada es AMBIGUA."
                                        + " Por favor seleccione en que Malla Vial "
                                        + " se encuentra, NUEVA o ANTIGUA para "
                                        + "continuar con la solicitud. ";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msn, ""));
                                ambiguaAntigua = false;
                                ambiguaNueva = false;
                                direccionAmbiguaPanel = true;
                                direccionLabel =
                                        responseConstruirDireccion.getDireccionStr().
                                        toString();
                                return;
                            }
                        }
                    }

                    dirEstado = responseConstruirDireccion.getDrDireccion().
                            getDirEstado();
                    direccionLabel =
                            responseConstruirDireccion.getDireccionStr().
                            toString();
                    direccionConstruida = true;
                    barrioSugeridoField = false;
                    barrioSugeridoStr = "";
                    request.setDrDireccion(responseConstruirDireccion
                            .getDrDireccion());
                    drDireccionCuentaMatriz = new DrDireccion();
                    drDireccionCuentaMatriz =
                            responseConstruirDireccion.getDrDireccion();
                } else {
                    //Dirección que no pudo ser traducida
                    if (responseConstruirDireccion.getDireccionStr() == null
                            || responseConstruirDireccion.getDireccionStr()
                            .isEmpty() && responseConstruirDireccion
                            .getResponseMesaje().getTipoRespuesta()
                            .equalsIgnoreCase("E")) {

                        direccionLabel = direccionCk;
                        barrioSugeridoField = false;
                        crearSolicitudButton = false;
                        barrioSugeridoStr = "";
                        String msnError = "La dirección calle-carrera"
                                + " no pudo ser traducida."
                                + responseConstruirDireccion.getResponseMesaje()
                                .getMensaje().toString();
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msnError, ""));
                    }
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al construir dirección Calle-Carrera ", e);
            String msnError = "Error al construir dirección Calle-Carrera.";
            FacesUtil.mostrarMensajeError(msnError + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidarDireccionMBean: construirDireccionCk(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función para validar datos nulos al momento de construir.una dirección
 tipo Calle-Carrera.
     *
     * @author Juan David Hernandez return false si algun dato se encuentra
     * vacio.
     * @return 
     */
    public boolean validarDatosDireccionCk() {
        try {

            tipoDireccion = "CK";
            if (tipoDireccion == null || tipoDireccion.isEmpty()) {
                String msnError = "Seleccione el TIPO DE DIRECCIÓN que "
                        + "desea ingresar por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msnError,
                        ""));
                return false;
            } else {
                if (direccionCk == null || direccionCk.isEmpty()) {
                    String msnError = "Ingrese la dirección por favor.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError, ""));
                    return false;
                } else {
                    if (codigoDane == null) {
                        String msnError = "Seleccione la CIUDAD por favor.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msnError, ""));
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("Error al validar los datos de la dirección. ", e);
            String msnError = "Error al validar los datos de la dirección "
                    + "calle-carrera - ValidarDireccionMBean: validarDatosDireccionCk()";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError
                    + e.getMessage(),
                    ""));
            return false;
        }
    }

    /**
     * Función utilizada para construir dirección de tipo Barrio-Manzana
     *
     * @author Juan David Hernandez
     */
    public void construirDireccionBm() {
        tipoDireccion = "BM";
        visitaTecnicaBean = (VisitaTecnicaBean) JSFUtil.getSessionBean(VisitaTecnicaBean.class);
        codigoDane = visitaTecnicaBean.getVt().getOtObj().getCmObj().getCentroPoblado();
        tecnologia = visitaTecnicaBean.getVt().getOtObj().getBasicaIdTecnologia().getCodigoBasica();
        try {
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en ValidarDireccionMBean: construirDireccionBm(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidarDireccionMBean: construirDireccionBm(). " + e.getMessage(), e, LOGGER);
        }

        try {
            if (validarDatosDireccionBm()) {
                request.setComunidad(codigoDane.getGeoCodigoDane());
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelBm);
                request.setValorNivel(nivelValorBm.trim());
                request.setTipoAdicion("P");
                request.setIdUsuario(usuarioLogin.getCedula().toString());

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
                    String msnError = responseConstruirDireccion.getResponseMesaje()
                            .getMensaje().toString();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError, ""));
                } else {
                    //Dirección construida correctamente
                    if (responseConstruirDireccion != null
                            && responseConstruirDireccion.getResponseMesaje()
                            .getTipoRespuesta().equals("I")) {
                        dirEstado = responseConstruirDireccion.getDrDireccion()
                                .getDirEstado();
                        request.setDrDireccion(responseConstruirDireccion
                                .getDrDireccion());
                        String barrioL = responseConstruirDireccion.getDrDireccion()
                                .getBarrio() != null ? responseConstruirDireccion
                                .getDrDireccion().getBarrio() : "";
                        direccionLabel = responseConstruirDireccion.getDireccionStr()
                                .toString();
                        direccionValidar = responseConstruirDireccion.getDrDireccion();
                        direccionConstruida = true;
                        tipoNivelBm = "";
                        nivelValorBm = "";
                    }
                }
            }
        } catch (ApplicationException e) {
            String msnError = "Error al construir dirección Barrio-Manzana.";
            FacesUtil.mostrarMensajeError(msnError + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ValidarDireccionMBean:construirDireccionBm(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función para validar datos nulos al momento de construir.una dirección
 tipo Barrio-Manzana.
     *
     * @author Juan David Hernandez return boolean false si algun dato se
     * encuentra vacio
     * @return 
     */
    public boolean validarDatosDireccionBm() {
        try {
            if (tipoDireccion == null || tipoDireccion.isEmpty()) {
                String msnError = "Seleccione el TIPO DE DIRECCIÓN que desea"
                        + " ingresar por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        msnError, ""));
                return false;
            } else {
                if (tipoNivelBm == null || tipoNivelBm.isEmpty()) {
                    String msnError = "Seleccione el TIPO DE NIVEL que desea"
                            + " agregar por favor.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, msnError,
                            ""));
                    return false;
                } else {
                    if (codigoDane == null) {
                        String msnError = "Seleccione la CIUDAD por favor.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msnError, ""));
                        return false;
                    } else {
                        if (nivelValorBm == null || nivelValorBm.isEmpty()) {
                            String msnError = "Ingrese un valor en el campo de "
                                    + "nivel barrio-manzana por favor.";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("Error al validar los datos de la dirección"
                    + " barrio-manzana. ", e);
            String msnError = "Error al validar los datos de la dirección "
                    + "barrio-manzana--ValidarDireccionMBean: validarDatosDireccionBm()";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError
                    + e.getMessage(),
                    ""));
            return false;
        }
    }

    /**
     * Función utilizada para construir dirección de tipo Intraducible
     *
     * @author Juan David Hernandez
     */
    public void construirDireccionIt() {
        try {

            tipoDireccion = "IT";
            visitaTecnicaBean = (VisitaTecnicaBean) JSFUtil.getSessionBean(VisitaTecnicaBean.class);
            codigoDane = visitaTecnicaBean.getVt().getOtObj().getCmObj().getCentroPoblado();
            tecnologia = visitaTecnicaBean.getVt().getOtObj().getBasicaIdTecnologia().getCodigoBasica();
            try {
                usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
            } catch (ApplicationException e) {
               FacesUtil.mostrarMensajeError("Error al ValidarDireccionMBean: construirDireccionIt(). " + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error al ValidarDireccionMBean: construirDireccionIt(). " + e.getMessage(), e, LOGGER);
            }

            if (validarDatosDireccionIt()) {

                request.setComunidad(codigoDane.getGeoCodigoDane());
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelIt);
                request.setValorNivel(nivelValorIt.trim());
                request.setTipoAdicion("P");
                request.setIdUsuario(usuarioLogin.getCedula().toString());
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
                    String msnError = responseConstruirDireccion
                            .getResponseMesaje().getMensaje().toString();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError, ""));
                } else {
                    //Dirección construida correctamente
                    if (responseConstruirDireccion != null
                            && responseConstruirDireccion.getResponseMesaje()
                            .getTipoRespuesta().equals("I")) {
                        direccionConstruida = true;
                        request.setDrDireccion(responseConstruirDireccion
                                .getDrDireccion());
                        dirEstado = responseConstruirDireccion.getDrDireccion()
                                .getDirEstado();
                        String barrioDireccion = responseConstruirDireccion
                                .getDrDireccion().getBarrio() != null
                                ? responseConstruirDireccion.getDrDireccion()
                                .getBarrio() : "";
                        direccionLabel = responseConstruirDireccion
                                .getDireccionStr().toString();
                        tipoNivelIt = "";
                        nivelValorIt = "";
                    }
                }
            }
        } catch (ApplicationException e) {
            String msnError = "Error al construir dirección Barrio-Manzana.";
            FacesUtil.mostrarMensajeError(msnError + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidarDireccionMBean: construirDireccionIt(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función para validar datos nulos al momento de construir.una dirección
 tipo Intraducible.
     *
     * @author Juan David Hernandez return false si algun dato se encuentra
     * vacio
     * @return 
     */
    public boolean validarDatosDireccionIt() {
        try {
            if (tipoDireccion == null || tipoDireccion.isEmpty()) {
                String msnError = "Seleccione el TIPO DE DIRECCIÓN "
                        + "que desea ingresar por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        msnError, ""));
                return false;
            } else {
                if (tipoNivelIt == null || tipoNivelIt.isEmpty()) {
                    String msnError = "Seleccione el TIPO DE NIVEL que "
                            + "desea agregar por favor.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError, ""));
                    return false;
                } else {
                    if (codigoDane == null) {
                        String msnError = "Seleccione la CIUDAD por favor.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msnError, ""));
                        return false;
                    } else {
                        if (nivelValorIt == null || nivelValorIt.isEmpty()) {
                            String msnError = "Ingrese un valor en el campo "
                                    + "de nivel intraducible por favor";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));
                            return false;
                        } else {
                            if (tipoNivelIt.equalsIgnoreCase("CONTADOR")
                                    && nivelValorIt.length() > 7) {
                                String msnError = "El valor para Contador no "
                                        + "puede exceder los 7 caracteres";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msnError, ""));
                                return false;
                            } else {
                                if (tipoNivelIt.equalsIgnoreCase("CONTADOR")
                                        && !isNumeric(nivelValorIt)) {
                                    String msnError = "El valor para Contador "
                                            + "debe ser númerico.";
                                    FacesContext.getCurrentInstance()
                                            .addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msnError, ""));
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("Error al validar datos de dirección intraducible ",
                    e);
            String msnError = "Error al validar los datos de la dirección "
                    + "intraducible.- ValidarDireccionMBean: validarDatosDireccionIt()";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError
                    + e.getMessage(),
                    ""));
            return false;
        }
    }

    /**
     * Función para validar si el dato recibido es númerico
     *
     * @author Juan David Hernandez} return true si el dato es úumerico
     * @param cadena
     * @return 
     */
    public boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en ValidarDireccionMBean: isNumeric(). " + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidarDireccionMBean: isNumeric(). " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /**
     * Función utilizada para construir dirección con complemento
     *
     * @author Juan David Hernandez
     */
    public void construirDireccionComplemento() {
        try {


            visitaTecnicaBean = (VisitaTecnicaBean) JSFUtil.getSessionBean(VisitaTecnicaBean.class);
            codigoDane = visitaTecnicaBean.getVt().getOtObj().getCmObj().getCentroPoblado();
            tecnologia = visitaTecnicaBean.getVt().getOtObj().getBasicaIdTecnologia().getCodigoBasica();

            if (validarDatosDireccionComplemento()) {
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelComplemento);
                request.setComunidad(codigoDane.getGeoCodigoDane());
                request.setValorNivel(complemento);
                request.setTipoAdicion("C");
                request.setIdUsuario(usuarioLogin.getCedula().toString());
                
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
                    String msnError =
                            responseConstruirDireccion.getResponseMesaje()
                            .getMensaje().toString();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError, ""));
                } else {
                    //Complemento agregado a la dirección correctamente
                    if (responseConstruirDireccion != null
                            && responseConstruirDireccion.getResponseMesaje()
                            .getTipoRespuesta().equals("I")) {
                        dirEstado = responseConstruirDireccion.getDrDireccion()
                                .getDirEstado();
                        String barrioL = responseConstruirDireccion
                                .getDrDireccion().getBarrio() != null
                                ? responseConstruirDireccion.getDrDireccion()
                                .getBarrio() : "";
                        direccionLabel = responseConstruirDireccion
                                .getDireccionStr().toString();
                        complemento = "";
                        tipoNivelComplemento = "";
                    }
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al construir dirección complemento ", e);
            String msnError = "Error al construir dirección complemento.";
           FacesUtil.mostrarMensajeError(msnError+ e.getMessage(), e, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidarDireccionMBean: construirDireccionComplemento(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función para validar datos nulos al momento de construir una dirección
     * con complemento.
     *
     * @author Juan David Hernandez return false si algun dato se encuentra
     * vacio
     * @return 
     */
    public boolean validarDatosDireccionComplemento() {
        try {
            if (tipoDireccion == null || tipoDireccion.isEmpty()) {
                String msnError = "Seleccione el TIPO DE DIRECCIÓN que desea"
                        + " ingresar por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        msnError, ""));
                return false;
            } else {
                if (tipoNivelComplemento == null
                        || tipoNivelComplemento.isEmpty()) {
                    String msnError = "Seleccione TIPO DE NIVEL del complemento"
                            + " por favor.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError, ""));
                    return false;
                } else {
                    if (codigoDane == null) {
                        String msnError = "Seleccione la CIUDAD por favor.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msnError, ""));
                        return false;
                    } else {
                        if (complemento == null || complemento.isEmpty()) {
                            String msnError = "Ingrese un valor en el campo "
                                    + "del complemento por favor.";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("Error al validar datos de direccion complemento ", e);
            String msnError = "Error al validar datos de direccion complemento.-ValidarDireccionMBean: validarDatosDireccionComplemento()";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msnError + e.getMessage(), ""));
            return false;
        }
    }

    /**
     * Función utilizada para construir dirección con apartamento
     *
     * @author Juan David Hernandez
     */
    public void construirDireccionApartamento() {
        try {
            visitaTecnicaBean = (VisitaTecnicaBean) JSFUtil.getSessionBean(VisitaTecnicaBean.class);
            codigoDane = visitaTecnicaBean.getVt().getOtObj().getCmObj().getCentroPoblado();
            tecnologia = visitaTecnicaBean.getVt().getOtObj().getBasicaIdTecnologia().getCodigoBasica();
            
            if (validarDatosDireccionApartamento()
                    && validarDatosDireccionApartamentoBiDireccional(responseConstruirDireccion.getDrDireccion(),
                    tipoNivelApartamento, apartamento)) {
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setComunidad(codigoDane.getGeoCodigoDane());
                request.setTipoNivel(tipoNivelApartamento);
                request.setValorNivel(apartamento);
                request.setTipoAdicion("A");
                request.setIdUsuario(usuarioLogin.getCedula().toString());

                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                }
                // Consume servicio que retorna la dirección con apartamento.
                responseConstruirDireccion = direccionRRFacadeLocal.
                        construirDireccionSolicitud(request);

                // Apartamento no agregado a la dirección correctamente
                if (responseConstruirDireccion == null
                        || responseConstruirDireccion.getResponseMesaje()
                        .getTipoRespuesta().equals("E")) {
                    crearSolicitudButton = false;
                    apartamentoIngresado = false;
                    String msnError =
                            responseConstruirDireccion.getResponseMesaje()
                            .getMensaje().toString();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError, ""));
                } else {
                    // Apartamento agregado a la dirección correctamente
                    if (responseConstruirDireccion != null
                            && responseConstruirDireccion.getResponseMesaje()
                            .getTipoRespuesta().equals("I")) {
                        dirEstado = responseConstruirDireccion.getDrDireccion()
                                .getDirEstado();
                        String barrioL =
                                responseConstruirDireccion.getDrDireccion()
                                .getBarrio() != null ? responseConstruirDireccion
                                .getDrDireccion().getBarrio() : "";
                        direccionLabel = responseConstruirDireccion
                                .getDireccionStr().toString();
                        apartamento = "";
                        tipoNivelApartamento = "";
                        apartamentoIngresado = true;
                    }
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al construir dirección apartamento ", e);
            String msnError = "Error al construir dirección apartamento.- ValidarDireccionMBean: construirDireccionApartamento()";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError
                    + e.getMessage(), ""));
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidarDireccionMBean: construirDireccionApartamento(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función para validar datos nulos al momento de construir una dirección
     * con apartamento.
     *
     * @author Juan David Hernandez return false si algun dato se encuentra
     * vacio
     * @return 
     */
    public boolean validarDatosDireccionApartamento() {
        try {
            if (tipoDireccion == null || tipoDireccion.isEmpty()) {
                String msnError = "Seleccione el TIPO DE DIRECCIÓN que desea"
                        + " ingresar por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        msnError, ""));
                return false;
            } else {
                if (codigoDane == null) {
                    String msnError = "Seleccione la CIUDAD por favor.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError, ""));
                    return false;
                } else {
                    if (tipoNivelApartamento == null
                            || tipoNivelApartamento.isEmpty()) {
                        String msnError = "Seleccione el TIPO DE NIVEL de"
                                + " apartamento que desea agregar por favor.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msnError, ""));
                        return false;
                    } else {
                        if (tipoNivelApartamento.equalsIgnoreCase("CASA")
                                || tipoNivelApartamento
                                .equalsIgnoreCase("ADMINISTRACION")
                                || tipoNivelApartamento
                                .equalsIgnoreCase("FUENTE")) {
                        } else {
                            if (apartamento == null || apartamento.isEmpty()) {
                                String msnError = "Ingrese un valor en el campo"
                                        + " apartamento por favor.";
                                FacesContext.getCurrentInstance()
                                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msnError, ""));
                                return false;
                            } else {
                                if (tipoNivelApartamento.contains("PI")
                                        && !(apartamento.equalsIgnoreCase("1")
                                        || apartamento.equalsIgnoreCase("2")
                                        || apartamento.equalsIgnoreCase("3"))) {

                                    String msnError = "El valor permitido"
                                            + " para PISO solo puede "
                                            + "ser 1, 2 o 3";
                                    FacesContext.getCurrentInstance()
                                            .addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msnError, ""));
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("Error al validar datos de direccion apartamento ", e);
            String msnError = "Error al validar datos de direccion apartamento.- ValidarDireccionMBean: validarDatosDireccionApartamento()";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError
                    + e.getMessage(),
                    ""));
            return false;
        }
    }

    /**
     * Función para validar la construccion de una direccion bidireccional
     *
     * @author Juan David Hernandez return false si la dirección no cumple con
     * algun criterio de validación
     * @param apartamento
     * @return 
     */
    public boolean validarDatosDireccionApartamentoBiDireccional(DrDireccion drDireccion, String tipoNivelApartamento, String apartamento) {
        try {
            // ajuste para traer la descripcion de la tecnologia
            // ajuste para enviar la tecnologia
            return direccionesValidacionFacadeLocal.
                    validarConstruccionApartamentoBiDireccional(drDireccion,
                    tecnologia, tipoNivelApartamento, apartamento);
        } catch (ApplicationException e) {
            String msnError = "Error al validar apartamento para tecnologia"
                    + " bidireccional. - ValidarDireccionMBean:validarDatosDireccionApartamentoBiDireccional";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError
                    + e.getMessage(), ""));
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidarDireccionMBean: validarDatosDireccionApartamentoBiDireccional(). " + e.getMessage(), e, LOGGER);
        }
        return false;
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
                    List<AddressSuggested> barrioList =
                            direccionesValidacionFacadeLocal
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
                }

                /*Valida que se haya obtenido un listado de barrios sugeridos
                 para desplegarlos en pantalla. */
                if (responseObtenerBarrio.getBarrioList() != null) {
                    String msn = "La ciudad es MultiOrigen, es necesario"
                            + " seleccionar un barrio sugerido y validar "
                            + "nuevamente";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msn, ""));

                    //Se agrega el valor 'Otro' al listado de barrios sugeridos
                    barrioSugeridoList = new ArrayList();
                    barrioSugeridoList = responseObtenerBarrio.getBarrioList();
                    AddressSuggested otro = new AddressSuggested();
                    otro.setAddress("Otro");
                    otro.setNeighborhood("Otro");
                    barrioSugeridoList.add(otro);
                    barrioSugeridoPanel = true;
                } else {
                    String msn = "La ciudad es MultiOrigen y no fue posible"
                            + " obtener barrios sugeridos. Seleccione el valor"
                            + " 'Otro' e ingreselo manualmente por favor";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msn, ""));
                    //Se agrega el valor 'Otro' al listado de barrios sugeridos
                    barrioSugeridoList = new ArrayList();
                    AddressSuggested otro = new AddressSuggested();
                    otro.setAddress("Otro");
                    otro.setNeighborhood("Otro");
                    barrioSugeridoList.add(otro);
                    barrioSugeridoPanel = true;
                    validarBarrioSugeridoSeleccionado();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al validar barrio de la dirección ", e);
            String msnError = "Error al validar barrio de la dirección --ValidarDireccionMBean: validarBarrioCiudadMultiOrigen()";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msnError + e.getMessage(), ""));
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
                    barrioSugeridoStr = barrioSugerido;
                    barrioSugeridoField = false;
                    request.setBarrio(barrioSugerido);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al validar barrio sugerido seleccionado ", e);
            String msnError = "Error al validar barrio sugerido seleccionado.-- ValidarDireccionMBean: validarBarrioSugeridoSeleccionado()";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError
                    + e.getMessage(), ""));
        }
    }

    /**
     * Función utilizada para validar que no existan datos nulos al momento de
     * crear una solicitud Dth.
     *
     * @author Juan David Hernandez return boolean
     * @return 
     */
    public boolean validarCrearSolicitud() {

        try {
            if (tipoDireccion == null || tipoDireccion.isEmpty()) {
                String msn = "Seleccione TIPO DE DIRECCIÓN por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        msn, ""));
                return false;
            }

            if (tipoDireccion.equalsIgnoreCase("CK") && (direccionCk == null || direccionCk.isEmpty())) {
                String msn = "Ingrese una dirección por favor.";
                FacesContext.getCurrentInstance()
                        .addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;
            } else {
                if (request.getDrDireccion() == null) {
                    String msn = "Debe construir una "
                            + "dirección para poder validarla.";
                    FacesContext.getCurrentInstance()
                            .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msn, ""));
                    return false;
                } else {
                    if (barrioSugeridoField
                            && (barrioSugeridoStr == null
                            || barrioSugeridoStr.isEmpty())) {
                        String msn = "Debe ingresar un barrio"
                                + " al haber seleccionado la "
                                + "opción 'OTRO'";
                        FacesContext.getCurrentInstance()
                                .addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msn, ""));
                        return false;
                    } else {
                        if (!direccionConstruida) {
                            String msn = "Debe construir una "
                                    + "dirección correcta para "
                                    + "poder validarla.";
                            FacesContext.getCurrentInstance()
                                    .addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msn, ""));
                            return false;
                        }
                        
                    }
                }
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("Error al validar campos al validar dirección ", e);
            String msn = "Ocurrio un error validando los datos "
                    + "de la dirección para la creación de la solicitud --ValidarDireccionMBean: validarCrearSolicitud()";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn
                    + e.getMessage(), ""));
            return false;
        }
    }

    /**
     * Función utilizada validar si la dirección ingresada ya se encuentra
     * asociada a una cuenta matriz.Se realiza la validación suprimiendo los
 complementos del a dirección. Se realiza una copia y se le quita el
 complemento a la dirección para esta ser buscada hasta la placa de la
 dirección.
     *
     * @author Juan David Hernandez return true si se encuentra una cuenta
     * matriz
     * @return 
     */
    public boolean validarSolicitudesActivasCuentaMatriz() {
        try {
            if (request.getDrDireccion() != null && request.getComunidad()
                    != null) {
                CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
                DrDireccion DrDireccionSinComplemento = request.getDrDireccion().clone();
                DrDireccionSinComplemento.setCpTipoNivel1(null);
                DrDireccionSinComplemento.setCpValorNivel1(null);
                DrDireccionSinComplemento.setCpTipoNivel2(null);
                DrDireccionSinComplemento.setCpValorNivel2(null);
                DrDireccionSinComplemento.setCpTipoNivel3(null);
                DrDireccionSinComplemento.setCpValorNivel3(null);
                DrDireccionSinComplemento.setCpTipoNivel4(null);
                DrDireccionSinComplemento.setCpValorNivel4(null);
                DrDireccionSinComplemento.setCpTipoNivel5(null);
                DrDireccionSinComplemento.setCpValorNivel5(null);
                DrDireccionSinComplemento.setCpTipoNivel6(null);
                DrDireccionSinComplemento.setCpValorNivel6(null);

                List<CmtCuentaMatrizMgl> listaCmtCuentaMatrizMgl =
                        cmtDireccionMglManager
                        .findByDrDireccion(DrDireccionSinComplemento,
                        request.getComunidad());

                if (listaCmtCuentaMatrizMgl != null
                        && !listaCmtCuentaMatrizMgl.isEmpty()) {
                    for (CmtCuentaMatrizMgl cmtCuentaMatrizMgl
                            : listaCmtCuentaMatrizMgl) {

                        String msn = "La dirección se encuentra asociada a una "
                                + "cuenta matriz. "
                                + "Número de cuenta: " + cmtCuentaMatrizMgl
                                .getNumeroCuenta()
                                + " No es posible realizar la solicitud. ";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN, msn,
                                ""));
                        cuentaMatrizOtraDireccion = cmtCuentaMatrizMgl;
                        cuentaMatrizActiva = true;
                        crearSolicitudButton = true;
                        deshabilitarCreacion = true;
                        return false;
                    }
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
            return true;
        } catch (ApplicationException | CloneNotSupportedException e) {
            LOGGER.error("Ocurrio un error realizando la validación de la"
                    + " dirección en una cuenta matriz. ", e);
            String msn = "Ocurrio un error realizando la validación de la"
                    + " dirección en una cuenta matriz. ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn,
                    ""));
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidarDireccionMBean: validarSolicitudesActivasCuentaMatriz(). " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /**
     * Valida si Existe la unidad.Función utilizada para validar si la unidad
 existe, debe ser utilizada luego de la validacion de la direccion
     *
     * @param cityEntity
     * @param tipoAccion
     * @param tecnologiaBasicaId
     * @return verdarero si la unidad Existe
     * @author Johnnatan Ortiz
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public boolean validaExistenciaUnidad(CityEntity cityEntity,
            String tipoAccion, BigDecimal tecnologiaBasicaId)  {
        try {
            HhppMgl hhppMgl = hhppMglFacadeLocal.validaExistenciaHhpp(
                    cityEntity, centroPoblado, tipoAccionSolicitud,
                    tecnologiaBasicaId);

            if (hhppMgl != null && hhppMgl.getHhpId() != null) {
                return true;
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidarDireccionMBean: validaExistenciaUnidad(). " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /**
     * Función utilizada desplegar los mensajes en pantalla correspondientes a
     * las respuestas obtenidas por el web service que valida la dirección.
     *
     * @param cityEntity
     *
     * @author Juan David Hernandez return false cuando se trata de una opción
     * en la que no puede continuar con la solicitud
     * @return 
     */
    public boolean mensajesValidacionesDetalleDireccion(CityEntity cityEntity) {
        try {
            boolean result = true;
            if (cityEntity != null) {
                /* Si la dirección ya existe en RR como Antigua y se esta
                 * ingresando una dirección nueva */
                if (cityEntity.getExisteRr().contains("(C)")) {
                    String msn = cityEntity.getExisteRr();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msn, ""));
                } else {
                    /*Si la direccion ya existe en RR y no es posible realizar
                     * una  solicitud */
                    if (cityEntity.getExisteRr().contains("(N)")) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                cityEntity.getExisteRr(), ""));
                        result = false;
                    } else {
                        //Si la direccion tiene una direccion antigua pero no un hhpp
                        if (cityEntity.getExisteRr() != null
                                && !cityEntity.getExisteRr().isEmpty()) {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    cityEntity.getExisteRr(), ""));
                        }
                    }
                }
                /*si existen HHPP tanto en la direccion antigua como en
                 la nueva se deja la anotacion en la nota */
                if (cityEntity.isExisteHhppAntiguoNuevo()) {
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

                if (visitaTecnicaBean.getSelectedTab().toString().contains("MULTIEDIFICIO")) {
                    List<CmtSubEdificiosVt> subEdifLista;

                    subEdifLista = subEdificiosVtBean.getSubEdificioVtList();
                    for (CmtSubEdificiosVt subEdificioVt : subEdifLista) {
                        if (subEdificioVt.getListTecnologiaHabilitada().isEmpty() && subEdifSelected.getIdEdificioVt() == subEdificioVt.getIdEdificioVt()) {
                            subEdificioVt.setDireccionSubEdificio(direccionLabel);
                        }
                    }
                    subEdificiosVtBean.setSubEdificioVtList(subEdifLista);
                } else {
                    /* Lista para cargar el form de cargue de hhpp para casas con su propia direccion */
                    List<CmtHhppVtMgl> listcmtHhppVtCargue = new ArrayList<CmtHhppVtMgl>();
                       listcmtHhppVtCargue = tecnologiaHabilitadaVtBean.getListHhppCargue();
                    for (CmtHhppVtMgl cmtHhppVtMgl : listcmtHhppVtCargue) {
                        cmtHhppVtMgl.setDireccionValidada(direccionLabel);
                    }
                    tecnologiaHabilitadaVtBean.setListHhppCargue(listcmtHhppVtCargue);



                }





            }
            return result;
        } catch (Exception e) {
            LOGGER.error("Error al obtener los mensajes de validación de la"
                    + " dirección ", e);
            String msnError = "Error al obtener los mensajes de validación"
                    + " de la dirección.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError
                    + e.getMessage(),
                    ""));
            return false;
        }
    }

    /**
     * Función utilizada para validar la correcta construicción de la dirección
     * cuando es tipo Barrio Manzana.
     *
     * @param direccion
     * @param DrDireccion de con la información de la dirección construida
     *
     * @author Juan David Hernandez
     *
     * return true si la dirección cumple con la estructura minima de
     * construcción de una dirección
     * @return 
     */
    public boolean validarEstructuraDireccion(DrDireccion direccion) {
        try {
            return direccionesValidacionFacadeLocal.validarEstructuraDireccion(direccion, Constant.TIPO_VALIDACION_DIR_HHPP);
        } catch (ApplicationException e) {
            LOGGER.error("Error validando construcción de la dirección. ", e);
            String msn = "Error validando construcción de la dirección.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn
                    + e.getMessage(), ""));
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidarDireccionMBean: validarEstructuraDireccion(). " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /**
     * Función utilizada para obtener las unidades asociadas que tiene un predio
     * de la dirección nueva y antigua.
     *
     * @param cityEntity
     *
     * @author Juan David Hernandez
     */
    public void obtenerUnidadesVecinasPredio(CityEntity cityEntity) {
        try {
            ArrayList<UnidadStructPcml> unidadesVecinas = new ArrayList();

            // Si tiene dirección nueva se buscan unidades con la direccion nueva.
            if (cityEntity.getDireccionRREntityNueva() != null) {
                String calle = cityEntity.getDireccionRREntityNueva().getCalle();
                String casa = cityEntity.getDireccionRREntityNueva()
                        .getNumeroUnidad();
                String apto = "";
                String codCity = cityEntity.getCodCity();
                unidadesVecinas =
                        pcmlFacadeLocal.getUnidades(calle, casa, apto, codCity);
            }
            //Si tiene dirección antigua, se buscan unidades con esa dirección
            if (cityEntity.getDireccionRREntityAntigua() != null) {

                String calleAntigua =
                        cityEntity.getDireccionRREntityAntigua().getCalle();
                String casaAntigua =
                        cityEntity.getDireccionRREntityAntigua()
                        .getNumeroUnidad();
                String aptoAntigua = "";
                String codCityAntigua = cityEntity.getCodCity();
                ArrayList<UnidadStructPcml> unidadesVecinasTemp =
                        pcmlFacadeLocal.getUnidades(calleAntigua,
                        casaAntigua, aptoAntigua, codCityAntigua);

                asignarDireccionAntigua(cityEntity);

                if (unidadesVecinasTemp != null
                        && !unidadesVecinasTemp.isEmpty()) {
                    if (unidadesVecinas == null || unidadesVecinas.isEmpty()) {
                        unidadesVecinas = unidadesVecinasTemp;
                    } else {
                        unidadesVecinas.addAll(unidadesVecinasTemp);
                    }
                }
            }
            if (unidadesVecinas != null && unidadesVecinas.size() > 0) {
                unidadList = new ArrayList<UnidadStructPcml>();
                unidadAuxiliarList = new ArrayList<UnidadStructPcml>();

                for (UnidadStructPcml u : unidadesVecinas) {
                    unidadList.add(u);
                }

                // Se realiza copia del listado para la paginación del listado.
                for (UnidadStructPcml u : unidadesVecinas) {
                    unidadAuxiliarList.add(u);
                }

                if (unidadList != null && !unidadList.isEmpty()) {
                    unidadesPredio = true;
                    String msn = "El predio cuenta con unidades asociadas.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, msn,
                            ""));
                }
            }
            /* Listados de opciones para cambio de apartamento cuando un predio 
             tiene unidades asociadas */
            listNivel5 = configurationAddressComponent.getAptoValues()
                    .getTiposApto();
            listNivel6 = configurationAddressComponent.getAptoValues()
                    .getTiposAptoComplemento();
        } catch (ApplicationException e) {
            LOGGER.error("Error al obtener las unidades asociadas al predio. "
                    + "", e);
            String msnError = "Error al obtener las unidades asociadas al"
                    + " predio.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError
                    + e.getMessage(), ""));
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidarDireccionMBean: obtenerUnidadesVecinasPredio(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función utilizada para asigar en el campo de OFICINA de la solicitud la
     * dirección antigua del hhpp si llega a tener.Este es separado por &; para
 posteriormente ser dividido y obtener la dirección.
     *
     * @author Juan David Hernandez
     * @param cityEntity
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
            }
        } catch (Exception e) {
            LOGGER.error("Error al momento de asignar la dirección antigua. EX000: " + e.getMessage(), e);
        }
    }

    public DrDireccion getDireccionValidar() {
        return direccionValidar;
    }

    public void setDireccionValidar(DrDireccion direccionValidar) {
        this.direccionValidar = direccionValidar;
    }

    public List<String> getListBarrios() {
        return listBarrios;
    }

    public void setListBarrios(List<String> listBarrios) {
        this.listBarrios = listBarrios;
    }

    public boolean isMultiorigen() {
        return multiorigen;
    }

    public void setMultiorigen(boolean multiorigen) {
        this.multiorigen = multiorigen;
    }

    public ConfigurationAddressComponent getConfigurationAddressComponent() {
        return configurationAddressComponent;
    }

    public void setConfigurationAddressComponent(ConfigurationAddressComponent configurationAddressComponent) {
        this.configurationAddressComponent = configurationAddressComponent;
    }

    public GeograficoPoliticoMgl getCiudadDelBean() {
        return ciudadDelBean;
    }

    public void setCiudadDelBean(GeograficoPoliticoMgl ciudadDelBean) {
        this.ciudadDelBean = ciudadDelBean;
    }

    public boolean isDireccionValidada() {
        return direccionValidada;
    }

    public void setDireccionValidada(boolean direccionValidada) {
        this.direccionValidada = direccionValidada;
    }

    public String getDireccionDelBean() {
        return direccionDelBean;
    }

    public void setDireccionDelBean(String direccionDelBean) {
        this.direccionDelBean = direccionDelBean;
    }

    public String getDireccionValida() {
        return direccionValida;
    }

    public void setDireccionValida(String direccionValida) {
        this.direccionValida = direccionValida;
    }

    public Object getPaternBean() {
        return paternBean;
    }

    public void setPaternBean(Object paternBean) {
        this.paternBean = paternBean;
    }

    public Class<?> getClassOfPaternsBean() {
        return classOfPaternsBean;
    }

    public void setClassOfPaternsBean(Class<?> classOfPaternsBean) {
        this.classOfPaternsBean = classOfPaternsBean;
    }

    public String getBarrioDelBean() {
        return barrioDelBean;
    }

    public void setBarrioDelBean(String barrioDelBean) {
        this.barrioDelBean = barrioDelBean;
    }

    public ResponseValidarDireccionDto getResponseValidarDireccionDto() {
        return responseValidarDireccionDto;
    }

    public void setResponseValidarDireccionDto(ResponseValidarDireccionDto responseValidarDireccionDto) {
        this.responseValidarDireccionDto = responseValidarDireccionDto;
    }

    public List<OpcionIdNombre> getCkList() {
        return ckList;
    }

    public void setCkList(List<OpcionIdNombre> ckList) {
        this.ckList = ckList;
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

    public List<OpcionIdNombre> getComplementoList() {
        return complementoList;
    }

    public void setComplementoList(List<OpcionIdNombre> complementoList) {
        this.complementoList = complementoList;
    }

    public List<OpcionIdNombre> getBmList() {
        return bmList;
    }

    public void setBmList(List<OpcionIdNombre> bmList) {
        this.bmList = bmList;
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

    public boolean isEnableTabs() {
        return enableTabs;
    }

    public void setEnableTabs(boolean enableTabs) {
        this.enableTabs = enableTabs;
    }

    public boolean isBarrioSugeridoPanel() {
        return barrioSugeridoPanel;
    }

    public void setBarrioSugeridoPanel(boolean barrioSugeridoPanel) {
        this.barrioSugeridoPanel = barrioSugeridoPanel;
    }

    public boolean isDeshabilitarValidar() {
        return deshabilitarValidar;
    }

    public void setDeshabilitarValidar(boolean deshabilitarValidar) {
        this.deshabilitarValidar = deshabilitarValidar;
    }

    public boolean isApartamentoIngresado() {
        return apartamentoIngresado;
    }

    public void setApartamentoIngresado(boolean apartamentoIngresado) {
        this.apartamentoIngresado = apartamentoIngresado;
    }

/*    public UsuariosPortal getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(UsuariosPortal usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }
    
    public UsuariosServicesDTO getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(UsuariosServicesDTO usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }*/

    public String getUsuarioVT() {
        return usuarioVT;
    }

    public void setUsuarioVT(String usuarioVT) {
        this.usuarioVT = usuarioVT;
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    public String getDireccionCk() {
        return direccionCk;
    }

    public void setDireccionCk(String direccionCk) {
        this.direccionCk = direccionCk;
    }

    public String getNivelValorIt() {
        return nivelValorIt;
    }

    public void setNivelValorIt(String nivelValorIt) {
        this.nivelValorIt = nivelValorIt;
    }

    public String getNivelValorBm() {
        return nivelValorBm;
    }

    public void setNivelValorBm(String nivelValorBm) {
        this.nivelValorBm = nivelValorBm;
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

    public String getDireccionLabel() {
        return direccionLabel;
    }

    public void setDireccionLabel(String direccionLabel) {
        this.direccionLabel = direccionLabel;
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

    public boolean isUnidadesPredio() {
        return unidadesPredio;
    }

    public void setUnidadesPredio(boolean unidadesPredio) {
        this.unidadesPredio = unidadesPredio;
    }

    public boolean isCrearSolicitudButton() {
        return crearSolicitudButton;
    }

    public void setCrearSolicitudButton(boolean crearSolicitudButton) {
        this.crearSolicitudButton = crearSolicitudButton;
    }

    public BigDecimal getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(BigDecimal centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public GeograficoPoliticoMgl getCodigoDane() {
        return codigoDane;
    }

    public void setCodigoDane(GeograficoPoliticoMgl codigoDane) {
        this.codigoDane = codigoDane;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    public boolean isDireccionAmbiguaPanel() {
        return direccionAmbiguaPanel;
    }

    public void setDireccionAmbiguaPanel(boolean direccionAmbiguaPanel) {
        this.direccionAmbiguaPanel = direccionAmbiguaPanel;
    }

    public boolean isBloquearCambioAmbigua() {
        return bloquearCambioAmbigua;
    }

    public void setBloquearCambioAmbigua(boolean bloquearCambioAmbigua) {
        this.bloquearCambioAmbigua = bloquearCambioAmbigua;
    }

    public boolean isAmbiguaAntigua() {
        return ambiguaAntigua;
    }

    public void setAmbiguaAntigua(boolean ambiguaAntigua) {
        this.ambiguaAntigua = ambiguaAntigua;
    }

    public boolean isAmbiguaNueva() {
        return ambiguaNueva;
    }

    public void setAmbiguaNueva(boolean ambiguaNueva) {
        this.ambiguaNueva = ambiguaNueva;
    }

    public String getDirEstado() {
        return dirEstado;
    }

    public void setDirEstado(String dirEstado) {
        this.dirEstado = dirEstado;
    }

    public boolean isDireccionConstruida() {
        return direccionConstruida;
    }

    public void setDireccionConstruida(boolean direccionConstruida) {
        this.direccionConstruida = direccionConstruida;
    }

    public boolean isBarrioSugeridoField() {
        return barrioSugeridoField;
    }

    public void setBarrioSugeridoField(boolean barrioSugeridoField) {
        this.barrioSugeridoField = barrioSugeridoField;
    }

    public String getBarrioSugeridoStr() {
        return barrioSugeridoStr;
    }

    public void setBarrioSugeridoStr(String barrioSugeridoStr) {
        this.barrioSugeridoStr = barrioSugeridoStr;
    }

    public DrDireccion getDrDireccionCuentaMatriz() {
        return drDireccionCuentaMatriz;
    }

    public void setDrDireccionCuentaMatriz(DrDireccion drDireccionCuentaMatriz) {
        this.drDireccionCuentaMatriz = drDireccionCuentaMatriz;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getTipoAccionSolicitud() {
        return tipoAccionSolicitud;
    }

    public void setTipoAccionSolicitud(String tipoAccionSolicitud) {
        this.tipoAccionSolicitud = tipoAccionSolicitud;
    }

    public boolean isHabilitarViabilidad() {
        return habilitarViabilidad;
    }

    public void setHabilitarViabilidad(boolean habilitarViabilidad) {
        this.habilitarViabilidad = habilitarViabilidad;
    }

    public boolean isCambioDireccionPanel() {
        return cambioDireccionPanel;
    }

    public void setCambioDireccionPanel(boolean cambioDireccionPanel) {
        this.cambioDireccionPanel = cambioDireccionPanel;
    }

    public List<AddressSuggested> getBarrioSugeridoList() {
        return barrioSugeridoList;
    }

    public void setBarrioSugeridoList(List<AddressSuggested> barrioSugeridoList) {
        this.barrioSugeridoList = barrioSugeridoList;
    }

    public List<UnidadStructPcml> getUnidadList() {
        return unidadList;
    }

    public void setUnidadList(List<UnidadStructPcml> unidadList) {
        this.unidadList = unidadList;
    }

    public List<UnidadStructPcml> getUnidadAuxiliarList() {
        return unidadAuxiliarList;
    }

    public void setUnidadAuxiliarList(List<UnidadStructPcml> unidadAuxiliarList) {
        this.unidadAuxiliarList = unidadAuxiliarList;
    }

    public ResponseValidacionDireccion getResponseValidarDireccion() {
        return responseValidarDireccion;
    }

    public void setResponseValidarDireccion(ResponseValidacionDireccion responseValidarDireccion) {
        this.responseValidarDireccion = responseValidarDireccion;
    }

    public CmtCuentaMatrizMgl getCuentaMatrizOtraDireccion() {
        return cuentaMatrizOtraDireccion;
    }

    public void setCuentaMatrizOtraDireccion(CmtCuentaMatrizMgl cuentaMatrizOtraDireccion) {
        this.cuentaMatrizOtraDireccion = cuentaMatrizOtraDireccion;
    }

    public boolean isCuentaMatrizActiva() {
        return cuentaMatrizActiva;
    }

    public void setCuentaMatrizActiva(boolean cuentaMatrizActiva) {
        this.cuentaMatrizActiva = cuentaMatrizActiva;
    }

    public boolean isDeshabilitarCreacion() {
        return deshabilitarCreacion;
    }

    public void setDeshabilitarCreacion(boolean deshabilitarCreacion) {
        this.deshabilitarCreacion = deshabilitarCreacion;
    }

    public String getBarrioSugerido() {
        return barrioSugerido;
    }

    public void setBarrioSugerido(String barrioSugerido) {
        this.barrioSugerido = barrioSugerido;
    }

    public boolean isEnableApto() {
        return enableApto;
    }

    public void setEnableApto(boolean enableApto) {
        this.enableApto = enableApto;
    }

    public List<OpcionIdNombre> getListNivel5() {
        return listNivel5;
    }

    public void setListNivel5(List<OpcionIdNombre> listNivel5) {
        this.listNivel5 = listNivel5;
    }

    public List<OpcionIdNombre> getListNivel6() {
        return listNivel6;
    }

    public void setListNivel6(List<OpcionIdNombre> listNivel6) {
        this.listNivel6 = listNivel6;
    }

    public int getPerfilVt() {
        return perfilVt;
    }

    public void setPerfilVt(int perfilVt) {
        this.perfilVt = perfilVt;
    }

    public String getEspacio() {
        return espacio;
    }

    public void setEspacio(String espacio) {
        this.espacio = espacio;
    }

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public RequestConstruccionDireccion getRequest() {
        return request;
    }

    public void setRequest(RequestConstruccionDireccion request) {
        this.request = request;
    }

    public CuentaMatrizBean getCuentaMatrizBean() {
        return cuentaMatrizBean;
    }

    public void setCuentaMatrizBean(CuentaMatrizBean cuentaMatrizBean) {
        this.cuentaMatrizBean = cuentaMatrizBean;
    }

    public OtMglBean getOtMglBean() {
        return otMglBean;
    }

    public void setOtMglBean(OtMglBean otMglBean) {
        this.otMglBean = otMglBean;
    }

    public List<ParametrosCalles> getDirNivel5List() {
        return dirNivel5List;
    }

    public void setDirNivel5List(List<ParametrosCalles> dirNivel5List) {
        this.dirNivel5List = dirNivel5List;
    }

    public List<ParametrosCalles> getDirNivel6List() {
        return dirNivel6List;
    }

    public void setDirNivel6List(List<ParametrosCalles> dirNivel6List) {
        this.dirNivel6List = dirNivel6List;
    }

    public String getComplementoDir() {
        return complementoDir;
    }

    public void setComplementoDir(String complementoDir) {
        this.complementoDir = complementoDir;
    }

    public String getApartamentoDir() {
        return apartamentoDir;
    }

    public void setApartamentoDir(String apartamentoDir) {
        this.apartamentoDir = apartamentoDir;
    }

    public HhppMglFacadeLocal getHhppMglFacadeLocal() {
        return hhppMglFacadeLocal;
    }

    public void setHhppMglFacadeLocal(HhppMglFacadeLocal hhppMglFacadeLocal) {
        this.hhppMglFacadeLocal = hhppMglFacadeLocal;
    }

    public boolean isMostrarPopupSub() {
        return mostrarPopupSub;
    }

    public void setMostrarPopupSub(boolean mostrarPopupSub) {
        this.mostrarPopupSub = mostrarPopupSub;
    }

    public boolean isMostrarPopupHhpp() {
        return mostrarPopupHhpp;
    }

    public void setMostrarPopupHhpp(boolean mostrarPopupHhpp) {
        this.mostrarPopupHhpp = mostrarPopupHhpp;
    }

    public CmtSubEdificiosVt getSubEdifSelected() {
        return subEdifSelected;
    }

    public void setSubEdifSelected(CmtSubEdificiosVt subEdifSelected) {
        this.subEdifSelected = subEdifSelected;
    }

    public DireccionRREntity getEntityResponse() {
        return entityResponse;
    }

    public void setEntityResponse(DireccionRREntity entityResponse) {
        this.entityResponse = entityResponse;
    }

    public boolean isShowApartComp() {
        return showApartComp;
    }

    public void setShowApartComp(boolean showApartComp) {
        this.showApartComp = showApartComp;
    }

   
    
    
    
    
}
