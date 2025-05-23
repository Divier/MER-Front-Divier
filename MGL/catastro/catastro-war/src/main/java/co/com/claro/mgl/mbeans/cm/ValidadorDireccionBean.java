/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.businessmanager.address.DrDireccionManager;
import co.com.claro.mgl.dtos.ResponseValidarDireccionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.DireccionRRFacadeLocal;
import co.com.claro.mgl.facade.DireccionesValidacionFacadeLocal;
import co.com.claro.mgl.facade.ParametrosCallesFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtComponenteDireccionesMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtValidadorDireccionesFacadeLocal;
import co.com.claro.mgl.jpa.entities.ConfigurationAddressComponent;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.OpcionIdNombre;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.mbeans.direcciones.FichaGenerarBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseValidacionDireccion;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.telmex.catastro.data.AddressSuggested;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import javax.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
@ManagedBean(name = "validadorDireccionBean")
@SessionScoped
public class ValidadorDireccionBean implements Serializable {

    private static final long serialVersionUID = 1L;
    List<String> listBarrios = new ArrayList<String>();
    private boolean multiorigen = false;
    private ConfigurationAddressComponent configurationAddressComponent;
    private Object paternBean;
    private DrDireccion drDireccionRetorno;
    private String stringDireccionRetorno;
    private Class<?> classOfPaternsBean;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session
            = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response
            = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(ValidadorDireccionBean.class);
    @EJB
    private CmtValidadorDireccionesFacadeLocal direccionesFacadeLocal;
    private GeograficoPoliticoMgl ciudadDelBean;
    private String barrioDelBean = "";
    private String direccionDelBean = "";
    private ResponseValidarDireccionDto responseValidarDireccionDto;
    private boolean direccionValidada = false;
    private boolean mostrarPopupSub = false;
    private boolean mostrarPopupHhpp = false;
    private boolean showITPanel = false;
    private boolean showBMPanel = false;
    private boolean showCKPanel = false;
    private boolean notGeoReferenciado = true;
    private String createDireccion = "";
    private String direccionCk;
    private DrDireccion direccionValidar = new DrDireccion();
    private UsuariosServicesDTO usuarioLogin;
    private ResponseConstruccionDireccion responseConstruirDireccion;
    private String nivelValorIt;
    private String nivelValorBm;
    private String tipoNivelBm;
    private String tipoNivelIt;
    private String tipoNivelItAux = "";
    private String tipoNivelApartamento;
    private String tipoNivelComplemento;
    private String direccionLabel;
    private String apartamento;
    private String complemento;
    private String tipoDireccion;
    private boolean unidadesPredio;
    private boolean crearSolicitudButton;
    private String rrRegional;
    private CmtBasicaMgl tecnologia;
    private TecnologiaHabilitadaVtBean tecnologiaHabilitadaVtBean;
    private VisitaTecnicaBean visitaTecnicaBean;
    private List<OpcionIdNombre> ckList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> itList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> aptoList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> complementoList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> bmList = new ArrayList<OpcionIdNombre>();
    private boolean bloquearCambioAmbigua;
    private boolean ambiguaAntigua;
    private String dirEstado;
    private boolean ambiguaNueva;
    private boolean direccionConstruida;
    private boolean barrioSugeridoField;
    private String barrioSugeridoStr;
    private DrDireccion drDireccionCuentaMatriz;
    private boolean showApartComp;
    private boolean apartamentoIngresado;
    private String espacio = "&nbsp;";
    private boolean deshabilitarValidar;
    private ResponseValidacionDireccion responseValidarDireccion = new ResponseValidacionDireccion();
    private String tipoValidacionDireccion;
    private BigDecimal idCentroPoblado;
    private String direccionAConstruir;
    private RequestConstruccionDireccion request;
    private String usuarioVT = null;
    private boolean direccionAmbiguaPanel;
    boolean direccionConstruidaOk = false;
    private String obtenerDireccionPanel;
      
    @EJB
    private CmtComponenteDireccionesMglFacadeLocal componenteDireccionesMglFacade;
    @EJB
    private DireccionRRFacadeLocal direccionRRFacadeLocal;
    @EJB
    private DireccionesValidacionFacadeLocal direccionesValidacionFacadeLocal;
    @EJB
    private ParametrosCallesFacadeLocal parametrosCallesFacade;

    private String tipoDirIT;

    public <A> void validarDireccion(DrDireccion drDireccion, String direccion,
            GeograficoPoliticoMgl ciudad, String barrio, 
            A patern, Class<A> classOfBean, String tipoValidacionDir, String origen) {
        try {
            
            obtenerDireccionPanel =  (direccion); 
            // variable para discriminar el tipo de valdacion y mostrar la carga de listas complemetos y apartamento
            tipoValidacionDireccion = tipoValidacionDir;
            ApplicationBean applicationBean = (ApplicationBean) JSFUtil
                    .getBean(ConstantsCM.APPLICATION_MANAGEDBEAN);
            configurationAddressComponent = applicationBean
                    .getConfigurationAddressComponent();

            // carga de listas complemetos y Apartamentos 
            try {
                cargarListadosConfiguracion();
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error en ValidadorDireccionBean: validarDireccion(). " + e.getMessage(), e, LOGGER);
            }
            paternBean = patern;
            classOfPaternsBean = classOfBean;
            
            boolean consultaCuentaMatrices = false;
            if(classOfPaternsBean.equals(ConsultaCuentasMatricesBean.class)){
                consultaCuentaMatrices = true;
            }

            direccionValidar = drDireccion.clone();
            ciudadDelBean = ciudad;
            barrioDelBean = null;
            direccionDelBean = direccion;
            validarDireccionFunction(true, origen, consultaCuentaMatrices);
        } catch (CloneNotSupportedException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidadorDireccionBean: validarDireccion(). " + e.getMessage(), e, LOGGER);
        }
    }

    public String ocultar() {
        direccionCk = null;
        request = new RequestConstruccionDireccion();
        direccionLabel = "";
        direccionDelBean = null;
        direccionValidar = new DrDireccion();
        mostrarPopupSub = false;
        notGeoReferenciado = true;
        createDireccion = "";
        return null;
    }

    public String limpiar() {
        mostrarPopupSub = true;
        return null;
    }

    public String validar() {
        if (direccionValidar != null
                && direccionValidar.getBarrio() != null
                && !direccionValidar.getBarrio().isEmpty()) {
            barrioDelBean = direccionValidar.getBarrio().toUpperCase();
            request.setBarrio(barrioDelBean.toUpperCase());
        }
        if (direccionValidar.getIdTipoDireccion() == null
                || direccionValidar.getIdTipoDireccion().isEmpty()) {
            String msn = "Debe ingresar una direccion valida para continuar";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msn, ""));
            return "";
        }

        if (direccionValidar != null && direccionValidar.getIdTipoDireccion() != null
                && !direccionValidar.getIdTipoDireccion().isEmpty()
                && (direccionValidar.getIdTipoDireccion().contains("IT")
                || direccionValidar.getIdTipoDireccion().contains("BM"))
                && (direccionValidar.getBarrio() != null
                && !direccionValidar.getBarrio().isEmpty())) {
            request.setBarrio("");
        }
        if (request != null) {
            if (direccionValidar != null) {
                request.setDrDireccion(direccionValidar);
            }
            if (validarEstructuraDireccion(request.getDrDireccion())) {
                try {
                    validarDireccionFunction(notGeoReferenciado, null, false);

                } catch (Exception e) {
                    String msn = Constant.MSN_PROCESO_FALLO + "; " + e.getMessage();
                    FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
                    return "";

                }
                reloadPatern();
            }
        } else {
            request = new RequestConstruccionDireccion();
            try {
                validarDireccionFunction(true, null, false);

            } catch (Exception e) {
                String msn = Constant.MSN_PROCESO_FALLO + "; " + e.getMessage();
                FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
                return "";

            }
            reloadPatern();
        }

        return "";
    }
    
    //Fichas
    public String validarDrFichas() {
        direccionValidar.setIdTipoDireccion("CK");
        if (direccionValidar != null
                && direccionValidar.getBarrio() != null
                && !direccionValidar.getBarrio().isEmpty()) {
            barrioDelBean = direccionValidar.getBarrio().toUpperCase();
            request.setBarrio(barrioDelBean.toUpperCase());
        }
        if (direccionValidar.getIdTipoDireccion() == null
                || direccionValidar.getIdTipoDireccion().isEmpty()) {
            String msn = "Debe ingresar una direccion valida para continuar";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msn, ""));
            return "";
        }

        if (direccionValidar != null && direccionValidar.getIdTipoDireccion() != null
                && !direccionValidar.getIdTipoDireccion().isEmpty()
                && (direccionValidar.getIdTipoDireccion().contains("IT")
                || direccionValidar.getIdTipoDireccion().contains("BM"))
                && (direccionValidar.getBarrio() != null
                && !direccionValidar.getBarrio().isEmpty())) {
            request.setBarrio("");
        }
        if (request != null) {
            if (direccionValidar != null) {
                request.setDrDireccion(direccionValidar);
            }

            direccionConstruidaOk = validarEstructuraDireccion(request.getDrDireccion());
            if (direccionConstruidaOk) {
                request.setDirConstruccionCorrecta(true);
                notGeoReferenciado = false;
                try {
                    validarDireccionFunction(notGeoReferenciado, null, false);

                } catch (Exception e) {
                    String msn = Constant.MSN_PROCESO_FALLO + "; " + e.getMessage();
                    FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
                    return "";

                }
                reloadPatern();
                mostrarPopupSub = false;
            } else {
                String msnError = "No se pudo validar la dirección, intente nuevamente.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msnError, ""));
            }
        } else {
            request = new RequestConstruccionDireccion();
            try {
                validarDireccionFunction(false, null, false);

            } catch (Exception e) {
                String msn = Constant.MSN_PROCESO_FALLO + "; " + e.getMessage();
                FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
                return "";

            }
            reloadPatern();
        }
         limpiarCamposTipoDireccion();
        return "";
    }

    /*
     * @param direccionCreadaTabulada true si la direccion no fue creara por el panel de direccion tabulada
     * y no es necesario hacer que la direccion pase la validacion de manera obliatoria
     */
    public void validarDireccionFunction(boolean direccionCreadaTabulada, String origen, boolean cambiarMallaDir) {
        try {

            if (request != null) {
                //si es true la direccion NO fue construida por el panel de tabulada
                if (direccionCreadaTabulada) {
                    responseValidarDireccionDto = direccionesFacadeLocal.validarDireccion(
                            request.getDrDireccion(), direccionDelBean, ciudadDelBean, barrioDelBean, cambiarMallaDir);
                  
                      
                } else {
                    //JDHT
                    //si es false la direccion fue construida por el panel de tabulada para CK
                    DrDireccionManager drDireccionManager = new DrDireccionManager();
                    responseValidarDireccionDto.setDrDireccion(request.getDrDireccion());
                    responseValidarDireccionDto.setValidacionExitosa(true);
                    responseValidarDireccionDto.getValidationMessages();
                    responseValidarDireccionDto.setIntradusible(false);
                    responseValidarDireccionDto.setDireccionRespuestaGeo(null);
                    String direccionConstruidaTabulada
                            = drDireccionManager.getDireccion(request.getDrDireccion());
                    responseValidarDireccionDto.setDireccion(direccionConstruidaTabulada);
                    responseValidarDireccionDto.getValidationMessages().add("Direccion validada exitosamente, puede continuar.");

                    DetalleDireccionEntity detalleDireccion = request.getDrDireccion().convertToDetalleDireccionEntity();
                    responseValidarDireccionDto.setDireccion(drDireccionManager.getDireccion(request.getDrDireccion()));
                    DireccionRRManager direccionRr = new DireccionRRManager(detalleDireccion, ciudadDelBean.getGpoMultiorigen(),ciudadDelBean);
                    responseValidarDireccionDto.setDireccionRREntity(direccionRr.getDireccion());

                    if (responseValidarDireccionDto.getDireccionRREntity() == null
                            || responseValidarDireccionDto.getDireccionRREntity().getCalle() == null
                            || responseValidarDireccionDto.getDireccionRREntity().getCalle().isEmpty()
                            || responseValidarDireccionDto.getDireccionRREntity().getNumeroUnidad() == null
                            || responseValidarDireccionDto.getDireccionRREntity().getNumeroUnidad().isEmpty()) {
                        responseValidarDireccionDto.setValidationMessages(null);
                        responseValidarDireccionDto.getValidationMessages().add("La dirección digitada no tiene la informacion suficiente "
                                + "para ser traducida, la validacion no es exitosa, intente crear la dirección por el panel de direccion tabulada");
                        responseValidarDireccionDto.setValidacionExitosa(false);
                        responseValidarDireccionDto.setDireccionRespuestaGeo(null);
                    }
                }

            } else {
                //cuando se digita por el campo de texto la direccion CK
                request = new RequestConstruccionDireccion();
                responseValidarDireccionDto = direccionesFacadeLocal.validarDireccion(
                        request.getDrDireccion(), direccionDelBean, ciudadDelBean, barrioDelBean,
                        cambiarMallaDir);
            }

            if (responseValidarDireccionDto.getValidationMessages() != null
                    && !responseValidarDireccionDto.getValidationMessages().isEmpty()
                    && responseValidarDireccionDto.getValidationMessages().size() > 0) {

                for (String singleMessage : responseValidarDireccionDto.getValidationMessages()) {
                    String msn = singleMessage;
                    if (responseValidarDireccionDto.isValidacionExitosa()) {
                        if (msn.contains(Constant.DIRECCIONES_ORIGEN_ANTIGUA)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    }
                }
                // No hubo errores, por lo tanto se marca como no georeferenciado.
                //

                responseValidarDireccionDto.getValidationMessages().clear();
            }

            direccionValidar = responseValidarDireccionDto.getDrDireccion().clone();

            if (responseValidarDireccionDto.isValidacionExitosa()) {
                direccionValidada = true;
                notGeoReferenciado = true;
            } else {
                direccionValidada = false;
                direccionValidar.setIdTipoDireccion("CK");

                notGeoReferenciado = false;
            }

            multiorigen = responseValidarDireccionDto.isMultiOrigen();
            if (responseValidarDireccionDto.getBarrios() != null && !responseValidarDireccionDto.getBarrios().isEmpty()) {
                if (multiorigen) {
                    getBarrios(request);
                }
            }

            if (responseValidarDireccionDto != null && responseValidarDireccionDto.isIntradusible()
                    || responseValidarDireccionDto.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("IT")
                    || responseValidarDireccionDto.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("BM")) {
                direccionLabel = "";
                request = new RequestConstruccionDireccion();
                responseConstruirDireccion = new ResponseConstruccionDireccion();
                //JDHT
                if (mostrarPopupSub) {
                    mostrarPopupSub = false;
                } else {
                    mostrarPopupSub = true;
                    tipoDireccion = "CK";
                }

                notGeoReferenciado = false;

                direccionCk = "";
                direccionDelBean = "";
                showCK();
               
                Aceptar(origen);

            } else {
               
                responseValidarDireccionDto.setDirTabulada(request.isDirConstruccionCorrecta());
                request = new RequestConstruccionDireccion();
                responseConstruirDireccion = new ResponseConstruccionDireccion();
                mostrarPopupSub = false;
                notGeoReferenciado = true;
                Aceptar(origen);

            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en ValidadorDireccionBean: validarDireccionFunction(). " + e.getMessage(), e, LOGGER);
        } catch (CloneNotSupportedException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (IllegalArgumentException e) {
            FacesUtil.mostrarMensajeError("Error en ValidadorDireccionBean: validarDireccionFunction(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidadorDireccionBean: validarDireccionFunction(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void getBarrios(RequestConstruccionDireccion request) {
        List<String> barrios = new ArrayList<String>();
        request.setComunidad(ciudadDelBean.getGeoCodigoDane());
        request.setIdUsuario(usuarioLogin.getCedula().toString());
        List<AddressSuggested> barrioList
                = direccionesValidacionFacadeLocal
                        .obtenerBarrioSugerido(request);
        if (barrioList != null) {
            for (AddressSuggested addressSuggested : barrioList) {
                barrios.add(addressSuggested.getNeighborhood());
            }
        }
        responseValidarDireccionDto.setBarrios(barrios);

    }

    public void Aceptar(String origen) {

        if (origen == null) {
            if (direccionValidar.getBarrio() != null && !direccionValidar.getBarrio().isEmpty()) {
                barrioDelBean = direccionValidar.getBarrio().toUpperCase();
            }

            if (ciudadDelBean.getGpoMultiorigen().equalsIgnoreCase("1") && !direccionValidar.getIdTipoDireccion().equalsIgnoreCase("BM") && !direccionValidar.getIdTipoDireccion().equalsIgnoreCase("IT")) {
                if (barrioDelBean == null || barrioDelBean.isEmpty()) {
                    if (!tipoValidacionDireccion.equals(Constant.TIPO_VALIDACION_DIR_CM_HHPP)) {
                        String msn = "El barrio es necesario para ciudades Multiorigen";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    }
                }
            }

        }
        request = new RequestConstruccionDireccion();
        reloadPatern();

    }

    private void reloadPatern() {

        String nombreMetodoRecarga = "recargar";
        Method methodReload;
        try {
            methodReload = classOfPaternsBean.getMethod(nombreMetodoRecarga, ResponseValidarDireccionDto.class);
            methodReload.invoke(paternBean, responseValidarDireccionDto);
        } catch (NoSuchMethodException e) {
            if (!classOfPaternsBean.equals(FichaGenerarBean.class)) {
                String msn = Constant.MSN_PROCESO_FALLO;
                FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidadorDireccionBean: reloadPatern(). " + e.getMessage(), e, LOGGER);
        }

    }

    /**
     * Función que renderiza panel de tipo dirección intraducible en la pantalla
     *
     * @author cardenaslb
     */
    private void showIT() {
        showITPanel = true;
        showBMPanel = false;
        showCKPanel = false;
    }

    /**
     * Función que renderiza panel de tipo dirección intraducible en la pantalla
     *
     * @author cardenaslb
     */
    private void showBM() {
        showITPanel = false;
        showBMPanel = true;
        showCKPanel = false;
    }

    /**
     * Función que renderiza panel de tipo dirección intraducible en la pantalla
     *
     * @author cardenaslb
     */
    private void showCK() {
        showITPanel = false;
        showBMPanel = false;
        showCKPanel = true;
        notGeoReferenciado = false;
    }

    /**
     * Función que limpiar los valores de la pantalla de tipo dirección
     *
     * @author Juan David Hernandez
     */
    public void limpiarCamposTipoDireccion() {
        direccionAConstruir = "";
        direccionValidar = new DrDireccion();
        responseValidarDireccionDto = new ResponseValidarDireccionDto();
        request = new RequestConstruccionDireccion();
        responseConstruirDireccion = new ResponseConstruccionDireccion();
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
        showApartComp = false;
        notGeoReferenciado = false;
        createDireccion = "";
    }

    /**
     * Funcion utilizada para obtener los listados utilizados en la pantalla
     * desde la base de datos
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void cargarListadosConfiguracion() {
        try {
            //Obtiene lista de listas de los componentes de la dirección.          
            configurationAddressComponent
                    = componenteDireccionesMglFacade
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
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidadorDireccionBean: cargarListadosConfiguracion(). " + e.getMessage(), e, LOGGER);
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
                direccionValidar.setIdTipoDireccion(tipoDireccion);
                showCK();
            } else if (tipoDireccion.equals("BM")) {
                limpiarCamposTipoDireccion();
                direccionValidar.setIdTipoDireccion(tipoDireccion);
                showBM();
            } else if (tipoDireccion.equals("IT")) {
                limpiarCamposTipoDireccion();
                direccionValidar.setIdTipoDireccion(tipoDireccion);
                showIT();
            } else if (tipoDireccion.equals("")) {
                limpiarCamposTipoDireccion();
            }
        } catch (Exception e) {
            LOGGER.error("Error al validar el tipo de dirección seleccionado- ValidadorDireccionBean: validarTipoDireccion() ", e);
            String msn = "Error al validar el tipo de dirección seleccionado - ValidadorDireccionBean: validarTipoDireccion()";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msn + e.getMessage(), ""));
        }
    }

    /**
     * Función utilizada para construir dirección de tipo calle-carrera
     *
     * @author Juan David Hernandez
     * @param ambigua
     */
    public void construirDireccionCk(boolean ambigua) {
        try {

            if (validarDatosDireccionCk()) {
                request = new RequestConstruccionDireccion();
                request.setDireccionStr(direccionCk.toUpperCase());
                request.setComunidad(ciudadDelBean.getGeoCodigoDane());
                direccionValidar.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(direccionValidar);

                request.setTipoAdicion("N");
                request.setTipoNivel("N");
                request.setIdUsuario(getUsuarioLogin().getCedula().toString());

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

                        List<String> ambiguaList
                                = direccionesValidacionFacadeLocal
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
                                direccionLabel
                                        = responseConstruirDireccion.getDireccionStr().
                                                toString();
                                return;
                            }
                        }
                    }

                    dirEstado = responseConstruirDireccion.getDrDireccion().
                            getDirEstado();
                    direccionLabel
                            = responseConstruirDireccion.getDireccionStr().
                                    toString();
                    direccionConstruida = true;
                    barrioSugeridoField = false;
                    barrioSugeridoStr = "";
                    notGeoReferenciado = true;
                    request.setDrDireccion(responseConstruirDireccion
                            .getDrDireccion());
                    drDireccionCuentaMatriz = new DrDireccion();
                    drDireccionCuentaMatriz
                            = responseConstruirDireccion.getDrDireccion();
                } else {
                    //Dirección que no pudo ser traducida
                    if (responseConstruirDireccion.getDireccionStr() == null
                            || responseConstruirDireccion.getDireccionStr()
                                    .isEmpty() && responseConstruirDireccion
                                    .getResponseMesaje().getTipoRespuesta()
                                    .equalsIgnoreCase("E")) {

                        direccionLabel = direccionCk.toUpperCase();
                        barrioSugeridoField = false;
                        crearSolicitudButton = false;
                        barrioSugeridoStr = "";
                        notGeoReferenciado = false;
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
            notGeoReferenciado = false;
            String msnError = "Error al construir dirección Calle-Carrera.";
            FacesUtil.mostrarMensajeError(msnError + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidadorDireccionBean: construirDireccionCk(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función para validar datos nulos al momento de construir.una dirección
     * tipo Calle-Carrera.
     *
     * @author Juan David Hernandez return false si algun dato se encuentra
     * vacio.
     * @return
     */
    public boolean validarDatosDireccionCk() {
        try {

            tipoDireccion = "CK";
            if (Constant.TIPO_VALIDACION_DIR_CM_HHPP.equals(tipoValidacionDireccion)) {

                if (tecnologia.getAbreviatura() == null
                        || tecnologia.getAbreviatura().isEmpty()) {
                    String msnError = "Seleccione un TIPO de TECNOLOGÍA por favor. ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, msnError,
                                    ""));
                    return false;
                } else {
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
                            if (ciudadDelBean == null || ciudadDelBean.equals(BigDecimal.ZERO)) {
                                String msnError = "No se ha asignado un  CIUDAD por favor.";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                msnError, ""));
                                return false;
                            } else {
                                if (idCentroPoblado == null || idCentroPoblado.equals(BigDecimal.ZERO)) {
                                    String msnError = "No se ha asignado un centro poblado";
                                    FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                    msnError, ""));
                                    return false;
                                }
                            }
                        }
                    }
                }

            } else {
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
                        if (ciudadDelBean == null || ciudadDelBean.equals(BigDecimal.ZERO)) {
                            String msnError = "Seleccione la CIUDAD por favor.";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msnError, ""));
                            return false;
                        } else {
                            if (idCentroPoblado == null || idCentroPoblado.equals(BigDecimal.ZERO)) {
                                String msnError = "Seleccione el CENTRO POBLADO por favor.";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                msnError, ""));
                                return false;
                            }
                        }
                    }
                }

            }
            return true;
        } catch (Exception e) {
            LOGGER.error("Error al validar los datos de la dirección. ", e);
            String msnError = "Error al validar los datos de la dirección "
                    + "calle-carrera. - validarDatosDireccionCk()";
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

        try {
            if (validarDatosDireccionBm()) {
                request.setComunidad(ciudadDelBean.getGeoCodigoDane());
                direccionValidar.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(direccionValidar);
                request.setTipoNivel(tipoNivelBm.toUpperCase());
                request.setValorNivel(nivelValorBm.trim().toUpperCase());
                request.setTipoAdicion("P");
                request.setIdUsuario(getUsuarioLogin().getCedula().toString());

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
                                .toString().toUpperCase();
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
            FacesUtil.mostrarMensajeError("Error en ValidadorDireccionBean: construirDireccionBm(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función para validar datos nulos al momento de construir.una dirección
     * tipo Barrio-Manzana.
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
                    if (ciudadDelBean == null || ciudadDelBean.equals(BigDecimal.ZERO)) {
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
                    + "barrio-manzana.-- validarDatosDireccionBm()";
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

            if (validarDatosDireccionIt()) {

                if (tipoNivelIt.equalsIgnoreCase("CONTADOR")) {
                    if (nivelValorIt.trim().length() > 6) {

                        String msnError = "Error al construir dirección Intraducible. El valor contador debe tener 6 caracteres";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        msnError, ""));
                        return;
                    }
                }
                request.setComunidad(ciudadDelBean.getGeoCodigoDane());
                direccionValidar.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(direccionValidar);
                request.setTipoNivel(tipoNivelIt.toUpperCase());
                request.setValorNivel(nivelValorIt.trim().toUpperCase());
                request.setTipoAdicion("P");
                request.setIdUsuario(getUsuarioLogin().getCedula().toString());
                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion
                            .getDrDireccion());
                }
                direccionAConstruir += tipoNivelBm + " ";

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
            String msnError = "Error al construir dirección Intraducible.";
            FacesUtil.mostrarMensajeError(msnError + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ValidadorDireccionBean:  construirDireccionIt(). " + e.getMessage(), e, LOGGER);
        }
    }

    public boolean validarBmCasaLote() {
        if (direccionAConstruir.contains("CASA")) {
            String msnError = "Ud ya ingreso este nivel";
            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));
            return false;

        } else {
            return true;
        }
    }

    // ajuste validacion contador o placa solo para intraducible
    public boolean validarPlacaContadorIt() {
        if (direccionLabel.contains("CONTADOR") || direccionLabel.contains("PLACA")) {
            if (direccionLabel.contains("CONTADOR") || direccionLabel.contains("PLACA")) {
                String msnError = "Ud ya ingreso este nivel";
                FacesContext.getCurrentInstance()
                        .addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msnError, ""));
                return false;
            } else {
                return true;
            }

        }
        return true;
    }

    /**
     * Función para validar datos nulos al momento de construir.una dirección
     * tipo Intraducible.
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
                    if (ciudadDelBean == null || ciudadDelBean.equals(BigDecimal.ZERO)) {
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
                    + "intraducible.--validarDatosDireccionIt()";
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
        } catch (NumberFormatException nfe) {
            LOGGER.error("El valor '" + cadena + "' no es numérico.");
            return false;
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
                direccionValidar.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(direccionValidar);
                request.setTipoNivel(tipoNivelComplemento.toUpperCase());
                request.setComunidad(ciudadDelBean.getGeoCodigoDane());
                request.setValorNivel(complemento);
                request.setTipoAdicion("C");
                request.setIdUsuario(getUsuarioLogin().getCedula().toString());

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
                    String msnError
                            = responseConstruirDireccion.getResponseMesaje()
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
            String msnError = "Error al construir dirección complemento.";
            FacesUtil.mostrarMensajeError(msnError + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidadorDireccionBean:  construirDireccionComplemento(). " + e.getMessage(), e, LOGGER);
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
                    if (ciudadDelBean == null) {
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
            String msnError = "Error al validar datos de direccion complemento.-validarDatosDireccionComplemento()";
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

            if (validarDatosDireccionApartamento()
                    && validarDatosDireccionApartamentoBiDireccional(responseConstruirDireccion.getDrDireccion(),
                            tipoNivelApartamento, apartamento)) {
                direccionValidar.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(direccionValidar);
                request.setComunidad(ciudadDelBean.getGeoCodigoDane());
                request.setTipoNivel(tipoNivelApartamento);
                request.setValorNivel(apartamento);
                request.setTipoAdicion("A");
                request.setIdUsuario(getUsuarioLogin().getCedula().toString());

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
                    String msnError
                            = responseConstruirDireccion.getResponseMesaje()
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
                        String barrioL
                                = responseConstruirDireccion.getDrDireccion()
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
            String msnError = "Error al construir dirección apartamento.";
            FacesUtil.mostrarMensajeError(msnError + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ValidadorDireccionBean: construirDireccionApartamento(). " + e.getMessage(), e, LOGGER);
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
                if (ciudadDelBean == null) {
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
            String msnError = "Error al validar datos de direccion apartamento: validarDatosDireccionApartamento().";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError
                            + e.getMessage(),
                            ""));
            return false;
        }
    }

    /**
     * Función utilizada para validar que no existan datos nulos al momento de
     * crear una solicitud Dth.
     *
     * @author cardenaslb
     * @return
     */
    public boolean validarCrearSolicitud() {
        try {
            if (tecnologia == null
                    || tecnologia.getAbreviatura().trim().isEmpty()) {
                String msn = "Seleccione TIPO DE TECNOLOGÍA por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;
            } else {
                if (ciudadDelBean == null || ciudadDelBean.equals(BigDecimal.ZERO)) {
                    String msn = "Seleccione una CIUDAD por favor.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, msn,
                                    ""));
                    return false;
                } else {
                    if (tipoDireccion == null || tipoDireccion.isEmpty()) {
                        String msn = "Seleccione TIPO DE DIRECCIÓN por favor.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msn, ""));
                        return false;
                    } else {
                        if (idCentroPoblado == null
                                || idCentroPoblado.compareTo(BigDecimal.ZERO)
                                == 0) {
                            String msn = "Seleccione el CENTRO POBLADO "
                                    + "por favor.";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msn, ""));
                            return false;
                        } else {
                            if (tipoDireccion.equalsIgnoreCase("CK")
                                    && (direccionCk == null
                                    || direccionCk.isEmpty())) {
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
                                        } else {

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("Error al validar campos al validar dirección ", e);
            String msn = "Ocurrio un error validando los datos "
                    + "de la dirección para la creación de la solicitud : validarCrearSolicitud()";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn
                            + e.getMessage(), ""));
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
     * @author cardenaslb
     *
     * return true si la dirección cumple con la estructura minima de
     * construcción de una dirección
     * @return
     */
    public boolean validarEstructuraDireccion(DrDireccion direccion) {
        try {
            return direccionesValidacionFacadeLocal.validarEstructuraDireccion(direccion, Constant.TIPO_VALIDACION_DIR_CM);
        } catch (ApplicationException e) {
            String msn = "Error validando construcción de la dirección.";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ValidadorDireccionBean: validarEstructuraDireccion(). " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /**
     * Función utilizada para reiniciar la dirección al realizar un cambio de
     * ciudad.
     *
     * @author Juan David Hernandez
     */
    public void reiniciarDireccion() {
        responseValidarDireccionDto = new ResponseValidarDireccionDto();
        request = new RequestConstruccionDireccion();
        responseConstruirDireccion = new ResponseConstruccionDireccion();
        direccionValidar = new DrDireccion();
        InfoCreaCMBean infoCreaCMBean = (InfoCreaCMBean) JSFUtil.getBean("InfoCreaCMBean", InfoCreaCMBean.class);
        infoCreaCMBean.setDireccion(null);
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

        hideTipoDireccion();
        showCK();
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

            if (Constant.TIPO_VALIDACION_DIR_CM_HHPP.equals(tipoValidacionDireccion)) {
                visitaTecnicaBean = (VisitaTecnicaBean) JSFUtil.getSessionBean(VisitaTecnicaBean.class);
                tecnologia = visitaTecnicaBean.getVt().getOtObj().getBasicaIdTecnologia();
            }
            return direccionesValidacionFacadeLocal.
                    validarConstruccionApartamentoBiDireccional(drDireccion,
                            tecnologia.getNombreBasica(), tipoNivelApartamento, apartamento);
        } catch (ApplicationException e) {
            String msnError = "Error al validar apartamento para tecnologia"
                    + " bidireccional. ";
            FacesUtil.mostrarMensajeError(msnError + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ValidadorDireccionBean: validarDatosDireccionApartamentoBiDireccional(). " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public void init() {
    }

    public void buildDireccion() {
        if (direccionValidar == null) {
            createDireccion = "";
            return;
        }
        createDireccion = "";
        createDireccion += direccionValidar.getTipoViaPrincipal() != null
                ? !direccionValidar.getTipoViaPrincipal().isEmpty() ? direccionValidar.getTipoViaPrincipal() + " " : "" : "";
        createDireccion += direccionValidar.getNumViaPrincipal() != null
                ? !direccionValidar.getNumViaPrincipal().isEmpty() ? " " + direccionValidar.getNumViaPrincipal() + " " : "" : "";
        createDireccion += direccionValidar.getLtViaPrincipal() != null
                ? !direccionValidar.getLtViaPrincipal().isEmpty() ? " " + direccionValidar.getLtViaPrincipal() + " " : "" : "";
        createDireccion += direccionValidar.getNlPostViaP() != null
                ? !direccionValidar.getNlPostViaP().isEmpty() ? " " + direccionValidar.getNlPostViaP() + " " : "" : "";
        createDireccion += direccionValidar.getBisViaPrincipal() != null
                ? !direccionValidar.getBisViaPrincipal().isEmpty() ? " - " + direccionValidar.getBisViaPrincipal() + " " : "" : "";
        createDireccion += direccionValidar.getCuadViaPrincipal() != null
                ? !direccionValidar.getCuadViaPrincipal().isEmpty() ? " " + direccionValidar.getCuadViaPrincipal() + " " : "" : "";
        createDireccion += direccionValidar.getTipoViaGeneradora() != null
                ? !direccionValidar.getTipoViaGeneradora().isEmpty() ? " " + direccionValidar.getTipoViaGeneradora() + " " : "" : "";
        createDireccion += direccionValidar.getNumViaGeneradora() != null
                ? !direccionValidar.getNumViaGeneradora().isEmpty() ? " # " + direccionValidar.getNumViaGeneradora() + " " : "" : "";
        createDireccion += direccionValidar.getLtViaGeneradora() != null
                ? !direccionValidar.getLtViaGeneradora().isEmpty() ? " " + direccionValidar.getLtViaGeneradora() + " " : "" : "";
        createDireccion += direccionValidar.getNlPostViaG() != null
                ? !direccionValidar.getNlPostViaG().isEmpty() ? " - " + direccionValidar.getNlPostViaG() + " " : "" : "";
        createDireccion += direccionValidar.getLetra3G() != null
                ? !direccionValidar.getLetra3G().isEmpty() ? " - " + direccionValidar.getLetra3G() + " " : "" : "";
       
        if (direccionValidar.getBisViaGeneradora() != null && !direccionValidar.getBisViaGeneradora().isEmpty()
                && direccionValidar.getBisViaGeneradora().equalsIgnoreCase("BIS")) {
            createDireccion += direccionValidar.getBisViaGeneradora() + " ";
        } else {
            createDireccion += direccionValidar.getBisViaGeneradora() != null
                    ? !direccionValidar.getBisViaGeneradora().isEmpty() ? " Bis " + direccionValidar.getBisViaGeneradora() + " " : "" : "";
        }

        createDireccion += direccionValidar.getCuadViaGeneradora() != null
                ? !direccionValidar.getCuadViaGeneradora().isEmpty() ? " " + direccionValidar.getCuadViaGeneradora() + " " : "" : "";
        createDireccion += direccionValidar.getPlacaDireccion() != null
                ? !direccionValidar.getPlacaDireccion().isEmpty() ? " " + Integer.parseInt(direccionValidar.getPlacaDireccion())  + " " : "" : "";
        
          if (direccionValidar.getPlacaDireccion() != null && !direccionValidar.getPlacaDireccion().isEmpty()){
            int placaSinCeros = Integer.parseInt(direccionValidar.getPlacaDireccion());
            direccionValidar.setPlacaDireccion(String.valueOf(placaSinCeros));
        }
        direccionConstruida = true;
        direccionLabel = createDireccion;
        direccionDelBean = createDireccion;
    }

    /**
     * Función que obtiene los valores del listado de parametros calles
     *
     * @author Juan David Hernandez
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<ParametrosCalles> optionBisDir(String tipo) {
        List<ParametrosCalles> result = new ArrayList<ParametrosCalles>();
        try {

            ParametrosCalles pc = new ParametrosCalles();
            pc.setIdParametro("BIS");
            pc.setDescripcion("BIS");
            pc.setIdTipo("BIS");
            result.add(pc);
            result.addAll(parametrosCallesFacade.findByTipo(tipo));
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidadorDireccionBean: optionBisDir(). " + e.getMessage(), e, LOGGER);
        }
        return result;
    }

    /**
     * Metodo utilizado para realizar la busqueda de parametros en la base de
     * datos
     *
     * @param type llave de busqueda a realizar
     * @return
     */
    public List<ParametrosCalles> obtenerListadoTipoSolicitudList(String type) {
        List<ParametrosCalles> tList = new ArrayList<ParametrosCalles>();
        try {
            tList = parametrosCallesFacade.findByTipo(type);
        } catch (ApplicationException e) {
            String msn = "Error al realizar consulta para obtener listado de"
                    + " tipo de solicitudes.";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidadorDireccionBean: obtenerListadoTipoSolicitudList(). " + e.getMessage(), e, LOGGER);
        }
        return tList;

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

    public boolean isShowITPanel() {
        return showITPanel;
    }

    public void setShowITPanel(boolean showITPanel) {
        this.showITPanel = showITPanel;
    }

    public boolean isShowBMPanel() {
        return showBMPanel;
    }

    public void setShowBMPanel(boolean showBMPanel) {
        this.showBMPanel = showBMPanel;
    }

    public boolean isShowCKPanel() {
        return showCKPanel;
    }

    public void setShowCKPanel(boolean showCKPanel) {
        this.showCKPanel = showCKPanel;
    }

    public String getStringDireccionRetorno() {
        return stringDireccionRetorno;
    }

    public void setStringDireccionRetorno(String stringDireccionRetorno) {
        this.stringDireccionRetorno = stringDireccionRetorno;
    }

    public String getBarrioDelBean() {
        return barrioDelBean;
    }

    public void setBarrioDelBean(String barrioDelBean) {
        this.barrioDelBean = barrioDelBean;
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

    public boolean isCrearSolicitudButton() {
        return crearSolicitudButton;
    }

    public void setCrearSolicitudButton(boolean crearSolicitudButton) {
        this.crearSolicitudButton = crearSolicitudButton;
    }

    public CmtBasicaMgl getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(CmtBasicaMgl tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public DrDireccion getDrDireccionRetorno() {
        return drDireccionRetorno;
    }

    public void setDrDireccionRetorno(DrDireccion drDireccionRetorno) {
        this.drDireccionRetorno = drDireccionRetorno;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public ResponseValidarDireccionDto getResponseValidarDireccionDto() {
        return responseValidarDireccionDto;
    }

    public void setResponseValidarDireccionDto(ResponseValidarDireccionDto responseValidarDireccionDto) {
        this.responseValidarDireccionDto = responseValidarDireccionDto;
    }

    public ResponseConstruccionDireccion getResponseConstruirDireccion() {
        return responseConstruirDireccion;
    }

    public void setResponseConstruirDireccion(ResponseConstruccionDireccion responseConstruirDireccion) {
        this.responseConstruirDireccion = responseConstruirDireccion;
    }

    public String getNivelValorBm() {
        return nivelValorBm;
    }

    public void setNivelValorBm(String nivelValorBm) {
        this.nivelValorBm = nivelValorBm;
    }

    public String getRrRegional() {
        return rrRegional;
    }

    public void setRrRegional(String rrRegional) {
        this.rrRegional = rrRegional;
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

    public String getDireccionDelBean() {
        return direccionDelBean;
    }

    public void setDireccionDelBean(String direccionDelBean) {
        this.direccionDelBean = direccionDelBean;
    }

    public boolean isUnidadesPredio() {
        return unidadesPredio;
    }

    public void setUnidadesPredio(boolean unidadesPredio) {
        this.unidadesPredio = unidadesPredio;
    }

    public VisitaTecnicaBean getVisitaTecnicaBean() {
        return visitaTecnicaBean;
    }

    public void setVisitaTecnicaBean(VisitaTecnicaBean visitaTecnicaBean) {
        this.visitaTecnicaBean = visitaTecnicaBean;
    }

    public String getUsuarioVT() {
        return usuarioVT;
    }

    public void setUsuarioVT(String usuarioVT) {
        this.usuarioVT = usuarioVT;
    }

    public String getDirEstado() {
        return dirEstado;
    }

    public void setDirEstado(String dirEstado) {
        this.dirEstado = dirEstado;
    }

    public DrDireccion getDrDireccionCuentaMatriz() {
        return drDireccionCuentaMatriz;
    }

    public void setDrDireccionCuentaMatriz(DrDireccion drDireccionCuentaMatriz) {
        this.drDireccionCuentaMatriz = drDireccionCuentaMatriz;
    }

    public boolean isShowApartComp() {
        return showApartComp;
    }

    public void setShowApartComp(boolean showApartComp) {
        this.showApartComp = showApartComp;
    }

    public RequestConstruccionDireccion getRequest() {
        return request;
    }

    public void setRequest(RequestConstruccionDireccion request) {
        this.request = request;
    }

    public boolean isApartamentoIngresado() {
        return apartamentoIngresado;
    }

    public void setApartamentoIngresado(boolean apartamentoIngresado) {
        this.apartamentoIngresado = apartamentoIngresado;
    }

    public String getEspacio() {
        return espacio;
    }

    public void setEspacio(String espacio) {
        this.espacio = espacio;
    }

    public boolean isDeshabilitarValidar() {
        return deshabilitarValidar;
    }

    public void setDeshabilitarValidar(boolean deshabilitarValidar) {
        this.deshabilitarValidar = deshabilitarValidar;
    }

    public TecnologiaHabilitadaVtBean getTecnologiaHabilitadaVtBean() {
        return tecnologiaHabilitadaVtBean;
    }

    public void setTecnologiaHabilitadaVtBean(TecnologiaHabilitadaVtBean tecnologiaHabilitadaVtBean) {
        this.tecnologiaHabilitadaVtBean = tecnologiaHabilitadaVtBean;
    }

    public ResponseValidacionDireccion getResponseValidarDireccion() {
        return responseValidarDireccion;
    }

    public String getTipoValidacionDireccion() {
        return tipoValidacionDireccion;
    }

    public void setTipoValidacionDireccion(String tipoValidacionDireccion) {
        this.tipoValidacionDireccion = tipoValidacionDireccion;
    }

    public BigDecimal getIdCentroPoblado() {
        return idCentroPoblado;
    }

    public void setIdCentroPoblado(BigDecimal idCentroPoblado) {
        this.idCentroPoblado = idCentroPoblado;
    }

    public String getTipoNivelItAux() {
        return tipoNivelItAux;
    }

    public void setTipoNivelItAux(String tipoNivelItAux) {
        this.tipoNivelItAux = tipoNivelItAux;
    }

    public UsuariosServicesDTO getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(UsuariosServicesDTO usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getDireccionAConstruir() {
        return direccionAConstruir;
    }

    public void setDireccionAConstruir(String direccionAConstruir) {
        this.direccionAConstruir = direccionAConstruir;
    }

    public String getCreateDireccion() {
        return createDireccion;
    }

    public void setCreateDireccion(String createDireccion) {
        this.createDireccion = createDireccion;
    }

    public boolean isNotGeoReferenciado() {
        return notGeoReferenciado;
    }

    public void setNotGeoReferenciado(boolean notGeoReferenciado) {
        this.notGeoReferenciado = notGeoReferenciado;
    }

    public String getTipoDirIT() {
        return tipoDirIT;
    }

    public void setTipoDirIT(String tipoDirIT) {
        this.tipoDirIT = tipoDirIT;
    }
    
    public boolean isDireccionConstruidaOk() {
        return direccionConstruidaOk;
    }

    public void setDireccionConstruidaOk(boolean direccionConstruidaOk) {
        this.direccionConstruidaOk = direccionConstruidaOk;
    }

    public String getObtenerDireccionPanel() {
        return obtenerDireccionPanel;
    }

    public void setObtenerDireccionPanel(String obtenerDireccionPanel) {
        this.obtenerDireccionPanel = obtenerDireccionPanel;
    }


}
