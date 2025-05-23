/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.businessmanager.address.DrDireccionManager;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.DireccionRRFacadeLocal;
import co.com.claro.mgl.facade.DireccionesValidacionFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosCallesFacadeLocal;
import co.com.claro.mgl.facade.RrCiudadesFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtComponenteDireccionesMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionDetalleMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.ConfigurationAddressComponent;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.OpcionIdNombre;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.RrCiudades;
import co.com.claro.mgl.jpa.entities.RrRegionales;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.HhppUtils;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseConstruccionDireccion;
import co.com.claro.visitasTecnicas.business.NegocioParamMultivalor;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressSuggested;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.ResponseMessage;
import co.com.telmex.catastro.utilws.SecurityLogin;
import com.jlcg.db.exept.ExceptionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Juan David Hernandez
 */
@ViewScoped
@ManagedBean(name = "consultaHhppBean")
public class ConsultaHhppBean implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(ConsultaHhppBean.class);
    protected ResponseConstruccionDireccion responseConstruirDireccion
            = new ResponseConstruccionDireccion();
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    protected HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private RequestConstruccionDireccion request
            = new RequestConstruccionDireccion();
    protected String usuarioVT = null;
    private int perfilVt = 0;
    private String cedulaUsuarioVT = null;
    protected BigDecimal idCentroPoblado;
    private GeograficoPoliticoMgl ciudadGpo;
    private GeograficoPoliticoMgl departamentoGpo;
    private GeograficoPoliticoMgl centroPobladoSeleccionado;
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    CmtDireccionDetalleMglManager direccionDetalladaManager = new CmtDireccionDetalleMglManager();
    public HhppMglManager hhppMglManager = new HhppMglManager();
    private String espacio = "&nbsp;";
    private String departamento;
    private BigDecimal ciudad;
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
    private String rrRegional;
    private String suscriptor;
    private String createDireccion;
    private String barrioSugerido;
    private String barrioSugeridoStr;
    private ConfigurationAddressComponent configurationAddressComponent;
    private List<HhppMgl> hhppList;
    private List<ParametrosCalles> tipoSolicitudList;
    public List<CmtDireccionDetalladaMgl> direccionDetalladaList;
    public List<CmtDireccionDetalladaMgl> direccionDetalladaBusquedaOriginalList;
    private List<GeograficoPoliticoMgl> centroPobladoList;
    private List<RrRegionales> regionalList;
    private List<GeograficoPoliticoMgl> ciudadesList;
    private List<RrCiudades> comunidadList;
    private List<GeograficoPoliticoMgl> ciudadList;
    private List<GeograficoPoliticoMgl> departamentoList;
    private String regresarMenu = "<- Regresar Menú";
    private String pageActual;
    private String numPagina = "1";
    private String inicioPagina = "<< - Inicio";
    private String anteriorPagina = "< - Anterior";
    private String finPagina = "Fin - >>";
    private String siguientePagina = "Siguiente - >";
    private List<Integer> paginaList;
    private int actual;
    protected int filasPag = ConstantsCM.PAGINACION_DIEZ_FILAS;
    protected int filasPag5 = ConstantsCM.PAGINACION_CINCO_FILAS;
    private int maximoRegistrosBusqueda;
    private List<OpcionIdNombre> ckList = new ArrayList<>();
    private List<OpcionIdNombre> bmList = new ArrayList<>();
    private List<OpcionIdNombre> itList = new ArrayList<>();
    private List<OpcionIdNombre> aptoList = new ArrayList<>();
    private List<OpcionIdNombre> complementoList = new ArrayList<>();
    private List<AddressSuggested> barrioSugeridoList;
    public DrDireccion drDireccion = new DrDireccion();
    private boolean showCKPanel;
    private boolean showBMPanel;
    private boolean showITPanel;
    public boolean showFooter;
    public boolean showPanelBusquedaDireccion;
    public boolean notGeoReferenciado = true;
    private boolean showBarrio;
    private boolean direccionConstruida;
    private boolean barrioSugeridoField;
    protected SecurityLogin securityLogin;
    private List<String> lstCodigoNodos;
    private String codigoNodo;
    private String barrio;

    @EJB
    private RrCiudadesFacadeLocal rrCiudadesFacadeLocal;
    @EJB
    private DireccionRRFacadeLocal direccionRRFacadeLocal;
    @EJB
    private DireccionesValidacionFacadeLocal direccionesValidacionFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private CmtComponenteDireccionesMglFacadeLocal componenteDireccionesMglFacade;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;    
    @EJB
    private HhppMglFacadeLocal hhppMglFacadeLocal;
    @EJB
    private CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMglFacadeLocal;
    @EJB
    private ParametrosCallesFacadeLocal parametrosCallesFacade;
    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    
    
    //Opciones agregadas para Rol
    private final String BSMDHHPPB = "BSMDHHPPB";
    

    public ConsultaHhppBean() {
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
            FacesUtil.mostrarMensajeError("Error en ConsultaHhppBean, validando autenticacion. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ConsultaHhppBean, validando autenticacion. ", e, LOGGER);
        }
    }

    @PostConstruct
    protected void init() {
        try {
            showPanelBusquedaDireccion = true;
            obtenerDepartamentoList();
            obtenerListadoTipoSolicitudList();
            cargarListadosConfiguracion();
            showCK();
            //obtener numero de resultado para busqueda parcial
            maximoRegistrosBusqueda = cmtDireccionDetalleMglFacadeLocal.numeroResultadoBusquedaParcialDireccion("ROW_NUM_RESULTS");
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
        } catch (ApplicationException ex) {
            LOGGER.error("Error en init, al realizar cargue de configuración de listados"
                    + " ", ex);
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
        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Error en validarTipoDireccion. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validarTipoDireccion. ", e, LOGGER);
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
        barrio = "";
        codigoNodo = "";
    }

    /**
     * Función que limpiar los valores de la pantalla
     *
     * @author Juan David Hernandez
     */
    public void limpiarBusqueda() {
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
        comunidadList = new ArrayList();
        ciudadesList = new ArrayList();
        centroPobladoList = new ArrayList();
        hhppList = new ArrayList();
        complemento = "";
        apartamento = "";
        direccionDetalladaList = new ArrayList();
        direccionDetalladaBusquedaOriginalList = new ArrayList();
        showFooter = false;
        showPanelBusquedaDireccion = true;
        notGeoReferenciado = true;
        direccionConstruida = false;
        createDireccion = "";
        showBarrio = false;
        barrioSugerido = "";
        barrioSugeridoField = false;
        barrioSugeridoStr = "";
        codigoNodo = "";
        showCK();
        barrio = "";

    }

    /**
     * Función utilizada para reiniciar la dirección al realizar un cambio de
     * ciudad.
     *
     * @author Juan David Hernandez
     */
    public void reiniciarDireccion() {
        request = new RequestConstruccionDireccion();
        responseConstruirDireccion = new ResponseConstruccionDireccion();
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
        ciudad = null;
        idCentroPoblado = null;
        centroPobladoList = null;
        notGeoReferenciado = true;
        direccionConstruida = false;
        createDireccion = "";
        showBarrio = false;
        barrioSugerido = "";
        barrioSugeridoField = false;
        barrioSugeridoStr = "";
        showCK();
        hideTipoDireccion();
        showCK();
        barrio = "";
        codigoNodo = "";
    }

    /**
     * Función que realiza la busqueda de un unidades que coincidan con los
     * criterios de busqueda ingresados en pantalla de busqueda de hhpp.
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void buscarHhpp() {
        try {
            //Si la busqueda se realiza por suscriptor
            if (suscriptor != null && !suscriptor.isEmpty()) {
                hhppList = hhppMglFacadeLocal.findHhppBySuscriptor(suscriptor);
                if (hhppList != null && !hhppList.isEmpty()) {
                    BigDecimal sdirId = null;
                    if (hhppList.get(0).getSubDireccionObj() != null
                            && hhppList.get(0).getSubDireccionObj().getSdiId() != null) {
                        sdirId = hhppList.get(0).getSubDireccionObj().getSdiId();
                    }

                    direccionDetalladaList = cmtDireccionDetalleMglFacadeLocal
                            .findDireccionDetallaByDirIdSdirId(hhppList.get(0).getDireccionObj().getDirId(), sdirId);
                    // Agregar Nodo
                    direccionDetalladaList = agregarNodoCertificado(direccionDetalladaList);
                    if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {
                        obtenerParametrosHhppListado();
                        showPanelBusquedaDireccion = false;
                    } else {
                        String msnError = "No se obtuvieron resultados de direccion para el hhpp asociado al suscriptor ingresado";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msnError, ""));
                        showPanelBusquedaDireccion = true;
                        return;
                    }

                } else {
                    String msnError = "No se obtuvieron hhpp asociados al numero de cuenta de suscriptor ingresado";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));
                    showPanelBusquedaDireccion = true;
                    return;
                }

            } else {

                if (!notGeoReferenciado) {
                    if (drDireccion == null) {
                        String msnError = "Es necesario construir una dirección con el panel de dirección tabulada.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msnError, ""));
                        return;
                    }
                    responseConstruirDireccion.setDrDireccion(drDireccion);
                }

                //Si la busqueda se realiza por dirección construida
                if (responseConstruirDireccion.getDrDireccion() != null) {
                    
                    //valida si la direccion ingresada 
                    /*esta correctamente construida 
                            o es una construcción parcial para buscar la dirección por partes*/
                    boolean busquedaParcial = false;
                    if(!direccionesValidacionFacadeLocal
                                .validarEstructuraDireccionBoolean(responseConstruirDireccion.getDrDireccion(),
                                        Constant.TIPO_VALIDACION_DIR_HHPP)){
                        busquedaParcial = true;
                    }
                    //Busqueda de direccion de manera tabulada
                    busquedaDireccionDetalladaTabuladaTexto(busquedaParcial);
                    
                    //si la direccion no la encuentra tampoco por texteo se procede a crearla
                    if (direccionDetalladaBusquedaOriginalList == null 
                            || direccionDetalladaBusquedaOriginalList.isEmpty()) {

                        //Si el centro poblado es multiorigen se debe pedir el barrio para buscar y crear la dirección.
                        if (centroPobladoSeleccionado.getGpoMultiorigen().equalsIgnoreCase("1")
                                && !showBarrio && drDireccion != null
                                && drDireccion.getIdTipoDireccion() != null
                                && !drDireccion.getIdTipoDireccion().isEmpty()
                                && drDireccion.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                            showBarrio = true;
                            validarBarrioCiudadMultiOrigen(centroPobladoSeleccionado.getGpoMultiorigen());
                            return;
                        }

                        //Si el centro poblado es multiorigen se debe pedir el barrio para buscar y crear la dirección.
                        if (centroPobladoSeleccionado.getGpoMultiorigen().equalsIgnoreCase("1")) {
                            if (barrioSugeridoStr == null || barrioSugeridoStr.isEmpty()) {
                                {
                                    if (drDireccion.getIdTipoDireccion() != null
                                            && !drDireccion.getIdTipoDireccion().isEmpty()
                                            && drDireccion.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                                        String msnError = "La ciudad es multiorigen y es necesario ingresar un barrio.";
                                        FacesContext.getCurrentInstance().addMessage(null,
                                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                        msnError, ""));
                                        return;
                                    }
                                }
                            }
                        }

                        //se asigna el barrio al objeto DrDireccion que va a crear la dirección.
                        if (barrioSugeridoStr != null && !barrioSugeridoStr.isEmpty()) {
                            responseConstruirDireccion.getDrDireccion().setBarrio(barrioSugeridoStr.toUpperCase());
                        }

                        //si tiene lo minimo de una dirección se envia a creacion
                        if (direccionesValidacionFacadeLocal.validarEstructuraDireccion(responseConstruirDireccion.getDrDireccion(),
                                Constant.TIPO_VALIDACION_DIR_HHPP)) {

                            if (centroPobladoSeleccionado != null) {
                                //crear la direccion en MGL si no se encontró
                                crearDireccionConsultada(responseConstruirDireccion.getDrDireccion(), centroPobladoSeleccionado);
                                //busca nuevamente la direccion para mostrarla en pantalla
                                busquedaDireccionDetalladaTabuladaTexto(busquedaParcial);

                                if (direccionDetalladaBusquedaOriginalList == null || direccionDetalladaBusquedaOriginalList.isEmpty()) {
                                    String msnError = "No se obtuvieron resultados con la dirección"
                                            + " construida.";
                                    FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                    msnError, ""));
                                    showPanelBusquedaDireccion = true;
                                    return;
                                }
                            }
                        }else{
                             if (direccionDetalladaBusquedaOriginalList == null || direccionDetalladaBusquedaOriginalList.isEmpty()) {
                                    String msnError = "No se obtuvieron resultados con la dirección"
                                            + " construida.";
                                    FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                    msnError, ""));
                                    showPanelBusquedaDireccion = true;
                                    return;
                                }
                        }
                    } else {
                        showPanelBusquedaDireccion = false;
                    }
                    
                } else {
                    String msnError = "Ingrese un criterio de búsqueda"
                            + " por favor. ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en buscarHhpp. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en buscarHhpp. " + e.getMessage(), e, LOGGER);
        }
    }    
    
    /**
     * Función que realiza la creacion de una direccion en las tablas direccion,
     * subdireccion, direcciondetallada
     *
     * @author Juan David Hernandez
     * @param centroPobladoSeleccionado
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void crearDireccionConsultada(DrDireccion direccionConstruida, 
            GeograficoPoliticoMgl centroPobladoSeleccionado) throws ApplicationException {
        try {

            NegocioParamMultivalor param = new NegocioParamMultivalor();
            CityEntity cityEntityCreaDir = param.consultaDptoCiudadGeo(centroPobladoSeleccionado.getGeoCodigoDane());

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
                            usuarioVT, "MGL", "", direccionConstruida);
            if (responseMessageCreateDir.getIdaddress() != null
                    && !responseMessageCreateDir.getIdaddress().trim()
                            .isEmpty()) {
            }

        } catch (ApplicationException | ExceptionDB ex) {
            LOGGER.error("Error en crearDireccionConsultada " + ex.getMessage(), ex);
            throw new ApplicationException("Error en crearDireccionConsultada " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error en crearDireccionConsultada " + ex.getMessage(), ex);
            throw new ApplicationException("Error en crearDireccionConsultada " + ex.getMessage(), ex);
        }
    }

    /**
     * Función que redirecciona al detalle del hhpp seleccionado
     *
     * @author Juan David Hernandez
     * @param direccionDetalladaSeleccionada
     */
    public void verDetalleHhpp(CmtDireccionDetalladaMgl direccionDetalladaSeleccionada) {
        try {
            //Si la direccion detallada seleccionada tiene hhpp.
            if (direccionDetalladaSeleccionada != null) {
                HhppDetalleSessionBean hhppDetalleSessionBean
                        = (HhppDetalleSessionBean) JSFUtil.getBean("hhppDetalleSessionBean");
                //Si la direccion detallada tiene un hhpp asociado
                if (direccionDetalladaSeleccionada.isHhppExistente()
                        && direccionDetalladaSeleccionada.getHhppMgl() != null) {
                    // Instacia Bean de Session para obtener el hhpp seleccionado                    
                    hhppDetalleSessionBean.setHhppSeleccionado(direccionDetalladaSeleccionada.getHhppMgl());
                    hhppDetalleSessionBean.setDireccionDetalladaSeleccionada(direccionDetalladaSeleccionada);
                    FacesUtil.navegarAPagina("/view/MGL/VT/moduloHhpp/detalleHhpp.jsf");
                } else {
                    hhppDetalleSessionBean.setHhppSeleccionado(null);
                    hhppDetalleSessionBean.setDireccionDetalladaSeleccionada(direccionDetalladaSeleccionada);
                    FacesUtil.navegarAPagina("/view/MGL/VT/moduloHhpp/detalleHhpp.jsf");

                }
            } else {
                String msnError = "Ocurrio un error al seleccionar el registro, "
                        + "intente nuevamente por favor ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError, ""));
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en verDetalleHhpp. al redireccionar a detalle de Hhpp. ", e, LOGGER);
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
                    barrioSugeridoStr = barrioSugerido.toUpperCase();
                    barrioSugeridoField = false;
                    request.setBarrio(barrioSugerido.toUpperCase());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al validar barrio sugerido seleccionado ", e);
            String msnError = "Error al validar barrio sugerido seleccionado.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError
                            + e.getMessage(), ""));
        }
    }

    /**
     * Función que agrega el barrio sugerido ingresado manualmente
     *
     * @author Juan David Hernandez
     */
    public void agregarBarrioSugerido() {
        try {
            if (barrioSugeridoStr == null || barrioSugeridoStr.isEmpty() || barrioSugeridoStr.equals("")) {
                String msnError = "Ingrese el nombre de un barrio sugerido por "
                        + "favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msnError,
                                ""));
            } else {
                request.setBarrio(barrioSugeridoStr.toUpperCase());
                barrioSugeridoStr = barrioSugeridoStr.toUpperCase();
                String msn = "Barrio sugerido agregado exitosamente.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
        } catch (Exception e) {
            LOGGER.error("Error al agregar barrio sugerido ", e);
            String msnError = "Error al agregar barrio sugerido.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msnError + e.getMessage(), ""));
        }
    }
    
    /**
     * Función que obtiene parametros especificos que se muestran en en listado
     * de hhpp encontrado en pantalla
     *
     * @author Juan David Hernandez
     */
    public void obtenerParametrosHhppListado() throws ApplicationException {
        try {
            if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {
                GeograficoPoliticoMgl ciudadGeograficoPolitico = new GeograficoPoliticoMgl();
                GeograficoPoliticoMgl departamentoGeograficoPolitico = new GeograficoPoliticoMgl();
                
                for (CmtDireccionDetalladaMgl dirDetallada : direccionDetalladaList) {

                    //Obtenemos los Hhpp de la Subdireccion  
                    if (dirDetallada.getSubDireccion() != null
                            && dirDetallada.getSubDireccion().getSdiId() != null) {

                        List<HhppMgl> hhhpSubDirList = hhppMglFacadeLocal
                                .findHhppSubDireccion(dirDetallada.getSubDireccion().getSdiId());

                        if (hhhpSubDirList != null && !hhhpSubDirList.isEmpty()) {
                            if (hhhpSubDirList.get(0).getEstadoRegistro() == 1) {
                                dirDetallada.setHhppExistente(true);
                                dirDetallada.setHhppMgl(hhhpSubDirList.get(0));
                                //Obtener numero de cuenta matriz si tiene asociada
                                if (hhhpSubDirList.get(0).getHhppSubEdificioObj() != null && hhhpSubDirList.get(0).getHhppSubEdificioObj()
                                        .getCmtCuentaMatrizMglObj() != null) {
                                    //Asignamos el número de la cuenta matriz
                                    dirDetallada.setNumeroCuentaMatriz(hhhpSubDirList.get(0).getHhppSubEdificioObj().getCmtCuentaMatrizMglObj().getCuentaMatrizId()
                                            .toString());
                                }
                            }
                        }
                    } else {
                        //Obtenemos los Hhpp de la Direccion principal    
                        if (dirDetallada.getDireccion() != null
                                && dirDetallada.getDireccion().getDirId() != null) {

                            List<HhppMgl> hhhpDirList
                                    = hhppMglFacadeLocal
                                            .findHhppDireccion(dirDetallada.getDireccion().getDirId());

                            if (hhhpDirList != null && !hhhpDirList.isEmpty()) {
                                if (hhhpDirList.get(0).getEstadoRegistro() == 1) {
                                    dirDetallada.setHhppExistente(true);
                                    dirDetallada.setHhppMgl(hhhpDirList.get(0));
                                    //Obtener numero de cuenta matriz si tiene asociada
                                    if (hhhpDirList.get(0).getHhppSubEdificioObj() != null && hhhpDirList.get(0).getHhppSubEdificioObj()
                                            .getCmtCuentaMatrizMglObj() != null) {
                                        //Asignamos el número de la cuenta matriz
                                        dirDetallada.setNumeroCuentaMatriz(hhhpDirList.get(0).getHhppSubEdificioObj().getCmtCuentaMatrizMglObj().getNumeroCuenta()
                                                .toString());
                                    }
                                }
                            }
                        }
                    }

                    //obtener departamento hhpp
                    ciudadGeograficoPolitico = geograficoPoliticoMglFacadeLocal
                            .findById(dirDetallada.getDireccion()
                                    .getUbicacion().getGpoIdObj().getGeoGpoId());

                    if (ciudadGeograficoPolitico != null) {
                        departamentoGeograficoPolitico = geograficoPoliticoMglFacadeLocal
                                .findById(ciudadGeograficoPolitico.getGeoGpoId());
                        dirDetallada.setCiudad(ciudadGeograficoPolitico.getGpoNombre());
                    }

                    if (departamentoGeograficoPolitico != null) {
                        dirDetallada.setDepartamento(departamentoGeograficoPolitico.getGpoNombre());
                    }

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
     * Función que obtiene parametros especificos que se muestran en en listado
     * de hhpp encontrado en pantalla
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void obtenerParametrosHhppListadoOriginal() throws ApplicationException {
        try {
            if (direccionDetalladaBusquedaOriginalList != null && !direccionDetalladaBusquedaOriginalList.isEmpty()) {               
                for (CmtDireccionDetalladaMgl dirDetallada : direccionDetalladaBusquedaOriginalList) {
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
     * Función para validar datos nulos al momento de construir.una dirección
     * tipo Calle-Carrera.
     *
     * @author Juan David Hernandez return false si algun dato se encuentra
     * vacio.
     * @return
     */
    public boolean validarDatosDireccionCk() throws ApplicationException {
        try {
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
                    if (ciudad == null) {
                        String msnError = "Seleccione la CIUDAD por favor.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msnError, ""));
                        return false;
                    } else {
                        if (idCentroPoblado == null) {
                            String msnError = "Seleccione el CENTRO POBLADO por favor.";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msnError, ""));
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (RuntimeException e) {
            LOGGER.error("Error al validarDatosDireccionCk. ", e);
            throw new ApplicationException("Error al validarDatosDireccionCk. ", e);
        } catch (Exception e) {
            LOGGER.error("Error al validarDatosDireccionCk. ", e);
            throw new ApplicationException("Error al validarDatosDireccionCk. ", e);
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
    public boolean validarDatosDireccionBm() throws ApplicationException {
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
                    if (ciudad == null) {
                        String msnError = "Seleccione la CIUDAD por favor.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msnError, ""));
                        return false;
                    } else {
                        if (idCentroPoblado == null) {
                            String msnError = "Seleccione el CENTRO POBLADO por favor.";
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
            }
            return true;
        } catch (RuntimeException e) {
            LOGGER.error("Error al validarDatosDireccionBm. ", e);
            throw new ApplicationException("Error al validarDatosDireccionBm. ", e);
        } catch (Exception e) {
            LOGGER.error("Error al validarDatosDireccionBm. ", e);
            throw new ApplicationException("Error al validarDatosDireccionBm. ", e);
        }
    }

    /**
     * Función para validar datos nulos al momento de construir.una dirección
     * tipo Intraducible.
     *
     * @author Juan David Hernandez return false si algun dato se encuentra
     * vacio
     * @return
     */
    public boolean validarDatosDireccionIt() throws ApplicationException {
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
                    if (ciudad == null) {
                        String msnError = "Seleccione la CIUDAD por favor.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msnError, ""));
                        return false;
                    } else {
                        if (idCentroPoblado == null) {
                            String msnError = "Seleccione el CENTRO POBLADO por favor.";
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
                                            + "puede exceder los 6 caracteres";
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
            }
            return true;
        } catch (RuntimeException e) {
            LOGGER.error("Error en validarDatosDireccionIt. " + e.getMessage(), e);
            throw new ApplicationException("Error en validarDatosDireccionIt. ", e);
        } catch (Exception e) {
            LOGGER.error("Error en validarDatosDireccionIt. " + e.getMessage(), e);
            throw new ApplicationException("Error en validarDatosDireccionIt. ", e);
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
     * @author Juan David Hernandez
     */
    public void construirDireccionCk() {
        try {
            if (validarDatosDireccionCk()) {
                request.setDireccionStr(direccionCk);
                request.setComunidad(centroPobladoSeleccionado.getGeoCodigoDane());
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoAdicion("N");
                request.setTipoNivel("N");
                if (usuarioLogin != null
                        && usuarioLogin.getCedula() != null) {
                    request.setIdUsuario(usuarioLogin.getCedula().toString());
                } else {
                    String msnError = "Ocurrio un error con los datos del usuario logueado."
                            + " Revisar autenticación por favor.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msnError, ""));
                    return;
                }
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
            direccionConstruida = false;
            notGeoReferenciado = false;
            FacesUtil.mostrarMensajeError("Error en construirDireccionCk. ", e, LOGGER);
        } catch (Exception e) {
            direccionConstruida = false;
            notGeoReferenciado = false;
            FacesUtil.mostrarMensajeError("Error en construirDireccionCk. ", e, LOGGER);
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
                        request.setDrDireccion(responseConstruirDireccion
                                .getDrDireccion());
                        String barrio = responseConstruirDireccion.getDrDireccion()
                                .getBarrio() != null ? responseConstruirDireccion
                                                .getDrDireccion().getBarrio().toUpperCase() : "";
                        direccionLabel = responseConstruirDireccion.getDireccionStr()
                                .toString();
                        tipoNivelBm = "";
                        nivelValorBm = "";
                    }
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en construirDireccionBm. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en construirDireccionBm. ", e, LOGGER);
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
                        request.setDrDireccion(responseConstruirDireccion
                                .getDrDireccion());

                        String barrioDireccion = responseConstruirDireccion
                                .getDrDireccion().getBarrio() != null
                                        ? responseConstruirDireccion.getDrDireccion()
                                                .getBarrio().toUpperCase() : "";
                        direccionLabel = responseConstruirDireccion
                                .getDireccionStr().toString();
                        tipoNivelIt = "";
                        nivelValorIt = "";
                    }
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en construirDireccionIt. ", e, LOGGER);
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
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelComplemento);
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
                        String barrio = responseConstruirDireccion
                                .getDrDireccion().getBarrio() != null
                                        ? responseConstruirDireccion.getDrDireccion()
                                                .getBarrio().toUpperCase() : "";
                        direccionLabel = responseConstruirDireccion
                                .getDireccionStr().toString();
                        complemento = "";
                        tipoNivelComplemento = "";
                    }
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en construirDireccionComplemento. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en construirDireccionComplemento. ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para construir dirección con apartamento
     *
     * @author Juan David Hernandez
     */
    public void construirDireccionApartamento() {
        try {
            if (validarDatosDireccionApartamento()) {
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
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
            FacesUtil.mostrarMensajeError("Error en construirDireccionApartamento. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en construirDireccionApartamento. ", e, LOGGER);
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
    public boolean validarDatosDireccionApartamento() throws ApplicationException {
        try {
            if (tipoDireccion == null || tipoDireccion.isEmpty()) {
                String msnError = "Seleccione el TIPO DE DIRECCIÓN que desea"
                        + " ingresar por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msnError, ""));
                return false;
            } else {
                if (ciudad == null) {
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
                    }
                }
            }
            return true;
        } catch (RuntimeException e) {
            LOGGER.error("Error al validarDatosDireccionApartamento ", e);
            throw new ApplicationException("Error al validarDatosDireccionApartamento ", e);
        } catch (Exception e) {
            LOGGER.error("Error al validarDatosDireccionApartamento ", e);
            throw new ApplicationException("Error al validarDatosDireccionApartamento ", e);
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
    public boolean validarDatosDireccionComplemento() throws ApplicationException {
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
                    if (ciudad == null) {
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
        } catch (RuntimeException e) {
            LOGGER.error("Error al validarDatosDireccionComplemento ", e);
            throw new ApplicationException("Error al validarDatosDireccionComplemento ", e);
        } catch (Exception e) {
            LOGGER.error("Error al validarDatosDireccionComplemento ", e);
            throw new ApplicationException("Error al validarDatosDireccionComplemento ", e);
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
            return direccionesValidacionFacadeLocal.
                    validarConstruccionApartamentoBiDireccional(drDireccion,
                            "U", tipoNivelApartamento, apartamento);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en validarDatosDireccionApartamentoBiDireccional. ", e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validarDatosDireccionApartamentoBiDireccional. ", e, LOGGER);
            return false;
        }
    }

    /**
     * Obtiene el listado de departamentos de la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void obtenerCiudadesList() throws ApplicationException {
        try {
            if (departamento != null
                    && !departamento.isEmpty()) {

                //Obtenemos el listado de ciudades para el filtro de la pantalla
                ciudadesList
                        = geograficoPoliticoMglFacadeLocal.findCiudades(new BigDecimal(departamento));
                centroPobladoList = new ArrayList();
            }
            ciudadList = new ArrayList();

        } catch (ApplicationException e) {
            LOGGER.error("Error al obtenerCiudadesList ", e);
            throw (e);
        } catch (Exception e) {
            LOGGER.error("Error al obtenerCiudadesList ", e);
            throw new ApplicationException("Error al obtenerCiudadesList ", e);
        }
    }

    /**
     * Obtiene el listado de comunidades de la base de datos por división.
     *
     * @author Juan David Hernandez
     */
    public void obtenerComunidadesList() {
        try {
            ciudad = null;
            departamento = "";
            comunidad = "";
            idCentroPoblado = null;
            ciudadesList = new ArrayList();
            centroPobladoList = new ArrayList();
            ciudadList = new ArrayList();
            if (division != null && !division.isEmpty()) {
                comunidadList = rrCiudadesFacadeLocal.findByCodregional(division);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerComunidadesList. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerComunidadesList. ", e, LOGGER);
        }
    }

    /**
     * Funcion utilizada para obtener los listados utilizados en la pantalla
     * desde la base de datos
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void cargarListadosConfiguracion() throws ApplicationException {
        try {
            //Obtiene lista de listas de los componentes de la dirección.          
            configurationAddressComponent
                    = componenteDireccionesMglFacade.getConfiguracionComponente();

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
            FacesUtil.mostrarMensajeError("Error en cargarListadosConfiguracion. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarListadosConfiguracion. ", e, LOGGER);
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
            if (comunidad == null || comunidad.isEmpty()) {
                hideTipoDireccion();
            } else {
                //Obtiene centro Poblado
                obtenerGeograficoPoliticoList();
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en validarCiudadSeleccionada. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validarCiudadSeleccionada. ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para obtener del georeferenciador el departamento,
     * ciudad y el listado de centro poblado.
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void obtenerGeograficoPoliticoList() throws ApplicationException {
        try {
            GeograficoPoliticoMgl geograficoPoliticoMgl
                    = geograficoPoliticoMglFacadeLocal
                            .findCityByComundidad(ciudad.toString());
            departamentoGpo = (geograficoPoliticoMglFacadeLocal
                    .findById(geograficoPoliticoMgl.getGeoGpoId()));
            ciudadGpo = geograficoPoliticoMgl;
            centroPobladoList = geograficoPoliticoMglFacadeLocal
                    .findCentroPoblado(ciudadGpo.getGpoId());

        } catch (ApplicationException e) {
            LOGGER.error("Error al obtenerGeograficoPoliticoList. ", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error al obtenerGeograficoPoliticoList. ", e);
            throw new ApplicationException("Error al obtenerGeograficoPoliticoList. ", e);
        }

    }

    /**
     * Función que obtiene asigna el valor del departamento y la ciudad al ser
     * seleccionada una comunidad.
     *
     * @author Juan David Hernandez
     */
    public void obtenerCiudadDepartamentoByComunidad() {
        try {
            departamento = "";
            ciudad = null;
            idCentroPoblado = null;
            centroPobladoList = new ArrayList();
            ciudadList = new ArrayList();

            if (comunidad != null && !comunidad.isEmpty()) {
                GeograficoPoliticoMgl geograficoPoliticoMgl
                        = geograficoPoliticoMglFacadeLocal
                                .findCityByComundidad(comunidad);
                if (geograficoPoliticoMgl != null) {
                    obtenerDepartamentoList();
                    departamento = geograficoPoliticoMgl.getGeoGpoId() + "";
                    obtenerCiudadesList();
                    ciudad = geograficoPoliticoMgl.getGpoId();
                    obtenerCentroPobladoList();
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerCiudadDepartamentoByComunidad. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerCiudadDepartamentoByComunidad. ", e, LOGGER);
        }
    }

    /**
     * Función que obtiene el valor completo de el centro poblado seleccionado
     * por el usuario en pantalla.
     *
     * @author Juan David Hernandez
     */
    public void obtenerCentroPobladoSeleccionado(boolean moduloFraudes) {
        try {
            if (idCentroPoblado != null) {
                centroPobladoSeleccionado = geograficoPoliticoMglFacadeLocal.findById(idCentroPoblado);
                if (centroPobladoSeleccionado != null && centroPobladoSeleccionado.getGpoNombre() != null
                        && !centroPobladoSeleccionado.getGpoNombre().isEmpty()
                        && (centroPobladoSeleccionado.getGpoNombre().equalsIgnoreCase("PALMIRA")
                        || centroPobladoSeleccionado.getGpoNombre().equalsIgnoreCase("CANDELARIA"))) {

                    String msnError = "Para esta Ciudad, por favor ingrese la "
                            + "dirección por el panel de DIRECCIÓN TABULADA";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));
                    notGeoReferenciado = false;
                } else {
                    notGeoReferenciado = true;

                }
                //bandera que nos indica si el cargue se hace desde el modulo de fraudes
                if (moduloFraudes) {
                    lstCodigoNodos = nodoMglFacadeLocal.findCodigoNodoByCentroP(idCentroPoblado);
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerCentroPobladoSeleccionado. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerCentroPobladoSeleccionado. ", e, LOGGER);
        }
    }

    public boolean validarCiudadSeleccionadaNoGeoreferenciable() {
        try {
            if (centroPobladoSeleccionado != null && centroPobladoSeleccionado.getGpoNombre() != null
                    && !centroPobladoSeleccionado.getGpoNombre().isEmpty()
                    && (centroPobladoSeleccionado.getGpoNombre().equalsIgnoreCase("PALMIRA")
                    || centroPobladoSeleccionado.getGpoNombre().equalsIgnoreCase("CANDELARIA"))) {

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

        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Error en validarCiudadSeleccionadaNoGeoreferenciable. ", e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validarCiudadSeleccionadaNoGeoreferenciable. ", e, LOGGER);
            return false;
        }
    }

    /**
     * Función utilizada para obtener el listado de tipos de solicitudes desde
     * la base de datos
     *
     * @author Juan David Hernandez
     */
    public void obtenerListadoTipoSolicitudList() {
        try {
            tipoSolicitudList
                    = parametrosCallesFacade.findByTipo("TIPO_SOLICITUD");
        } catch (ApplicationException e) {
            LOGGER.error("Error al realizar consulta para obtener listado de"
                    + " tipo de solicitudes ", e);
            String msn = "Error al realizar consulta para obtener listado de"
                    + " tipo de solicitudes.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msn + e.getMessage(), ""));
        }
    }

    /**
     * Metodo utilizado para realizar la busqueda de parametros en la base de
     * datos
     *
     * @param type llave de busqueda a realizar
     * @return
     */
    public List<ParametrosCalles> obtenerListadoTipoSolicitudList(String type) throws ApplicationException {
        List<ParametrosCalles> tList = new ArrayList<ParametrosCalles>();
        try {
            tList = parametrosCallesFacade.findByTipo(type);
        } catch (ApplicationException e) {
            LOGGER.error("Error en obtenerListadoTipoSolicitudList. ", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en obtenerListadoTipoSolicitudList. ", e);
            throw new ApplicationException("Error en obtenerListadoTipoSolicitudList. ", e);
        }
        return tList;

    }

    /**
     * Obtiene el listado de centro poblado por ciudad de la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void obtenerCentroPobladoList() throws ApplicationException {
        try {
            if (ciudad != null) {
                centroPobladoList = geograficoPoliticoMglFacadeLocal
                        .findCentroPoblado(ciudad);
            } else {
                centroPobladoList = new ArrayList();
            }

        } catch (ApplicationException e) {
            LOGGER.error("Error al obtenerCentroPobladoList. ", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error al obtenerCentroPobladoList. ", e);
            throw new ApplicationException("Error al obtenerCentroPobladoList. ", e);
        }
    }

    /**
     * Obtiene el listado de departamentos de la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void obtenerDepartamentoList() throws ApplicationException {
        try {
            departamentoList = geograficoPoliticoMglFacadeLocal.findDptos();
        } catch (ApplicationException e) {
            LOGGER.error("Error al obtenerDepartamentoList ", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error al obtenerDepartamentoList ", e);
            throw new ApplicationException("Error al obtenerDepartamentoList ", e);
        }
    }

    /**
     * Función utilizada para validar si la excepción arrogada por el servicio
     * de validación es por ser una ciudad multi-origen.
     *
     * @author Juan David Hernandez
     * @param multiOrigen
     */
    public void validarBarrioCiudadMultiOrigen(String multiOrigen) {
        try {
            /*Valida si la dirección construida es una dirección 
             Multi-Origen para hacer cargue de listado de barrios sugeridos. */
            if (multiOrigen.equalsIgnoreCase("1")) {
                /*Consume servicio que permite obtener un listado de barrios 
                 * sugeridos para el usuario. */
                barrioSugeridoList
                        = direccionesValidacionFacadeLocal
                                .obtenerBarrioSugerido(request);


                /*Valida que se haya obtenido un listado de barrios sugeridos
                 para desplegarlos en pantalla. */
                if (barrioSugeridoList != null && !barrioSugeridoList.isEmpty()) {
                    String msn = "La ciudad es MultiOrigen, es necesario"
                            + " seleccionar un barrio sugerido y buscar "
                            + "nuevamente";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msn, ""));

                    //Se agrega el valor 'Otro' al listado de barrios sugeridos    
                    AddressSuggested otro = new AddressSuggested();
                    otro.setAddress("Otro");
                    otro.setNeighborhood("Otro");
                    barrioSugeridoList.add(otro);
                    showBarrio = true;
                } else {
                    String msn = "La ciudad es MultiOrigen y no fue posible"
                            + " obtener barrios sugeridos. Seleccione el valor"
                            + " 'Otro' e ingreselo manualmente por favor";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msn, ""));
                    //Se agrega el valor 'Otro' al listado de barrios sugeridos
                    barrioSugeridoList = new ArrayList();
                    AddressSuggested otro = new AddressSuggested();
                    otro.setAddress("Otro");
                    otro.setNeighborhood("Otro");
                    barrioSugeridoList.add(otro);
                    showBarrio = true;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al validar barrio de la dirección ", e);
            String msnError = "Error al validar barrio de la dirección.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msnError + e.getMessage(), ""));
        }
    }

    /**
     * Función que realiza paginación de la tabla.
     *
     * @param page;
     * @author Juan David Hernandez
     * @return
     */
    public String busquedaDireccionDetalladaTabuladaTexto(boolean busquedaDireccionParcial) throws ApplicationException {
        try {
            
            //Si la direccion ingresada no es completa y es se desea buscar por partes la dirección
            if (busquedaDireccionParcial) {
                direccionDetalladaBusquedaOriginalList = cmtDireccionDetalleMglFacadeLocal.buscarDireccionTabuladaCompletaParcial(responseConstruirDireccion.getDrDireccion(), 
                        idCentroPoblado, maximoRegistrosBusqueda);
            } else {
                //busqueda normal de una dirección completa construida correctamente
                direccionDetalladaBusquedaOriginalList = cmtDireccionDetalleMglFacadeLocal
                        .findDireccionByDireccionDetallada(responseConstruirDireccion.getDrDireccion(),
                                idCentroPoblado, false, 1, filasPag5, false);
            }

             List <CmtDireccionDetalladaMgl>  direccionDetalladaTextoList = new ArrayList();
            ///Busqueda por texto para direcciones con nombres en la via principal      
            if (responseConstruirDireccion != null
                    && responseConstruirDireccion.getDireccionRespuestaGeo() != null
                    && !responseConstruirDireccion.getDireccionRespuestaGeo().isEmpty()
                    && responseConstruirDireccion != null && responseConstruirDireccion.getDrDireccion() != null) {

              direccionDetalladaTextoList = direccionDetalladaManager.busquedaDireccionTextoRespuestaGeo
                 (responseConstruirDireccion.getDireccionRespuestaGeo(),
                        responseConstruirDireccion.getDrDireccion(), centroPobladoSeleccionado.getGpoId());
            }
            
            if ((direccionDetalladaBusquedaOriginalList != null && !direccionDetalladaBusquedaOriginalList.isEmpty())
                    || (direccionDetalladaTextoList != null && !direccionDetalladaTextoList.isEmpty())) {
                direccionDetalladaBusquedaOriginalList = direccionDetalladaManager.combinarDireccionDetalladaList
                                                      (direccionDetalladaBusquedaOriginalList, direccionDetalladaTextoList);
            }
            
            if (direccionDetalladaBusquedaOriginalList != null && !direccionDetalladaBusquedaOriginalList.isEmpty()) {                 
                obtenerParametrosHhppListadoOriginal();
                //Agregar nodo
                direccionDetalladaBusquedaOriginalList = agregarNodoCertificado(direccionDetalladaBusquedaOriginalList);
                //Verifica cuales de las direcciones encontradas son HHPP                                 
                listInfoByPage(1, direccionDetalladaBusquedaOriginalList);
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
        return null;
    }
    
     /**
     * Función que realiza paginación de la tabla.de las unidades asociadas al
        predio
     *
     * @param page;
     * @author Juan David Hernandez
     * @param direccionDetalladaListCompletaOriginal
     */
    public void listInfoByPage(int page, List<CmtDireccionDetalladaMgl> direccionDetalladaListCompletaOriginal) {
        try {
            actual = page;
            getPageActual();
            int firstResult = 0;
            if (page > 1) {
                firstResult = (filasPag5 * (page - 1));
            }

            //Obtenemos el rango de la paginación
            int maxResult;
            if ((firstResult + filasPag5) > direccionDetalladaListCompletaOriginal.size()) {
                maxResult = direccionDetalladaListCompletaOriginal.size();
            } else {
                maxResult = (firstResult + filasPag5);
            }
     
            /*Obtenemos los objetos que se encuentran en el rango de la paginación
             y los guardarmos en la lista que se va a desplegar en pantalla*/
            direccionDetalladaList = new ArrayList<CmtDireccionDetalladaMgl>();
            for (int i = firstResult; i < maxResult; i++) {
                direccionDetalladaList.add(direccionDetalladaListCompletaOriginal.get(i).clone());
            }
               obtenerParametrosHhppListado();    

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en lista de paginación" + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que carga la primera página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageFirst() {
        try {
            listInfoByPage(1, direccionDetalladaBusquedaOriginalList);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en pageFirst, redireccionando a primera pagina. ", ex, LOGGER);
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
            listInfoByPage(nuevaPageActual, direccionDetalladaBusquedaOriginalList);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en pagePrevious, direccionando a la página anterior. ", ex, LOGGER);
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
            listInfoByPage(nuevaPageActual, direccionDetalladaBusquedaOriginalList);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irPagina. ", e, LOGGER);
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
            listInfoByPage(nuevaPageActual, direccionDetalladaBusquedaOriginalList);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageNext, direccionando a la siguiente página ", e, LOGGER);
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
            listInfoByPage(totalPaginas, direccionDetalladaBusquedaOriginalList);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageLast, direccionando a la última página ", e, LOGGER);
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
            int totalPaginas;
            int pageSol = 1;
            if (direccionDetalladaBusquedaOriginalList != null && !direccionDetalladaBusquedaOriginalList.isEmpty()) {
                pageSol = direccionDetalladaBusquedaOriginalList.size();
            } else {
                pageSol = 1;
            }

            totalPaginas = (int) ((pageSol % filasPag5 != 0)
                    ? (pageSol / filasPag5) + 1 : pageSol / filasPag5);

            return totalPaginas;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginas, redireccionand página. ", e, LOGGER);
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

    public void showHideDireccion() {
        if (showPanelBusquedaDireccion) {
            showPanelBusquedaDireccion = false;
        } else {
            showPanelBusquedaDireccion = true;
        }
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
        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Error en buildDireccion. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en buildDireccion. ", e, LOGGER);
        }
    }

    public List<ParametrosCalles> optionBisDir(String tipo) {
        try {
            List<ParametrosCalles> result = new ArrayList<ParametrosCalles>();
            ParametrosCalles pc = new ParametrosCalles();
            pc.setIdParametro("BIS");
            pc.setDescripcion("BIS");
            pc.setIdTipo("BIS");
            result.add(pc);
            result.addAll(parametrosCallesFacade.findByTipo(tipo));
            return result;
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en optionBisDir. ", ex, LOGGER);
        }
        return null;
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public ResponseConstruccionDireccion getResponseConstruirDireccion() {
        return responseConstruirDireccion;
    }

    public void setResponseConstruirDireccion(ResponseConstruccionDireccion responseConstruirDireccion) {
        this.responseConstruirDireccion = responseConstruirDireccion;
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

    public List<OpcionIdNombre> getComplementoList() {
        return complementoList;
    }

    public void setComplementoList(List<OpcionIdNombre> complementoList) {
        this.complementoList = complementoList;
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

    public List<RrRegionales> getRegionalList() {
        return regionalList;
    }

    public void setRegionalList(List<RrRegionales> regionalList) {
        this.regionalList = regionalList;
    }

    public String getRrRegional() {
        return rrRegional;
    }

    public void setRrRegional(String rrRegional) {
        this.rrRegional = rrRegional;
    }

    public GeograficoPoliticoMgl getCiudadGpo() {
        return ciudadGpo;
    }

    public void setCiudadGpo(GeograficoPoliticoMgl ciudadGpo) {
        this.ciudadGpo = ciudadGpo;
    }

    public GeograficoPoliticoMgl getDepartamentoGpo() {
        return departamentoGpo;
    }

    public void setDepartamentoGpo(GeograficoPoliticoMgl departamentoGpo) {
        this.departamentoGpo = departamentoGpo;
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

    public List<GeograficoPoliticoMgl> getCiudadList() {
        return ciudadList;
    }

    public void setCiudadList(List<GeograficoPoliticoMgl> ciudadList) {
        this.ciudadList = ciudadList;
    }

    public List<RrCiudades> getComunidadList() {
        return comunidadList;
    }

    public void setComunidadList(List<RrCiudades> comunidadList) {
        this.comunidadList = comunidadList;
    }

    public List<GeograficoPoliticoMgl> getCiudadesList() {
        return ciudadesList;
    }

    public void setCiudadesList(List<GeograficoPoliticoMgl> ciudadesList) {
        this.ciudadesList = ciudadesList;
    }

    public List<GeograficoPoliticoMgl> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<GeograficoPoliticoMgl> departamentoList) {
        this.departamentoList = departamentoList;
    }

    public String getEspacio() {
        return espacio;
    }

    public void setEspacio(String espacio) {
        this.espacio = espacio;
    }

    public List<HhppMgl> getHhppList() {
        return hhppList;
    }

    public void setHhppList(List<HhppMgl> hhppList) {
        this.hhppList = hhppList;
    }

    public String getSuscriptor() {
        return suscriptor;
    }

    public void setSuscriptor(String suscriptor) {
        this.suscriptor = suscriptor;
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

    public String getSiguientePagina() {
        return siguientePagina;
    }

    public void setSiguientePagina(String siguientePagina) {
        this.siguientePagina = siguientePagina;
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

    public String getRegresarMenu() {
        return regresarMenu;
    }

    public void setRegresarMenu(String regresarMenu) {
        this.regresarMenu = regresarMenu;
    }

    public BigDecimal getCiudad() {
        return ciudad;
    }

    public void setCiudad(BigDecimal ciudad) {
        this.ciudad = ciudad;
    }

    public boolean isShowFooter() {
        return showFooter;
    }

    public void setShowFooter(boolean showFooter) {
        this.showFooter = showFooter;
    }

    public List<CmtDireccionDetalladaMgl> getDireccionDetalladaList() {
        return direccionDetalladaList;
    }

    public void setDireccionDetalladaList(List<CmtDireccionDetalladaMgl> direccionDetalladaList) {
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

    public boolean isBarrioSugeridoField() {
        return barrioSugeridoField;
    }

    public void setBarrioSugeridoField(boolean barrioSugeridoField) {
        this.barrioSugeridoField = barrioSugeridoField;
    }

    public List<AddressSuggested> getBarrioSugeridoList() {
        return barrioSugeridoList;
    }

    public void setBarrioSugeridoList(List<AddressSuggested> barrioSugeridoList) {
        this.barrioSugeridoList = barrioSugeridoList;
    }

    public List<CmtDireccionDetalladaMgl> getDireccionDetalladaBusquedaOriginalList() {
        return direccionDetalladaBusquedaOriginalList;
    }

    public void setDireccionDetalladaBusquedaOriginalList(List<CmtDireccionDetalladaMgl> direccionDetalladaBusquedaOriginalList) {
        this.direccionDetalladaBusquedaOriginalList = direccionDetalladaBusquedaOriginalList;
    }    
    
    public String getCodigoNodo() {
        return codigoNodo;
    }

    public void setCodigoNodo(String codigoNodo) {
        this.codigoNodo = codigoNodo;
    }

    public List<String> getLstCodigoNodos() {
        return lstCodigoNodos;
    }

    public void setLstCodigoNodos(List<String> lstCodigoNodos) {
        this.lstCodigoNodos = lstCodigoNodos;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public GeograficoPoliticoMgl getCentroPobladoSeleccionado() {
        return centroPobladoSeleccionado;
    }

    public void setCentroPobladoSeleccionado(GeograficoPoliticoMgl centroPobladoSeleccionado) {
        this.centroPobladoSeleccionado = centroPobladoSeleccionado;
    }

    public CmtDireccionDetalleMglFacadeLocal getCmtDireccionDetalleMglFacadeLocal() {
        return cmtDireccionDetalleMglFacadeLocal;
    }

    public void setCmtDireccionDetalleMglFacadeLocal(CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMglFacadeLocal) {
        this.cmtDireccionDetalleMglFacadeLocal = cmtDireccionDetalleMglFacadeLocal;
    }

    public HhppMglFacadeLocal getHhppMglFacadeLocal() {
        return hhppMglFacadeLocal;
    }

    public void setHhppMglFacadeLocal(HhppMglFacadeLocal hhppMglFacadeLocal) {
        this.hhppMglFacadeLocal = hhppMglFacadeLocal;
    }

    public HhppMglManager getHhppMglManager() {
        return hhppMglManager;
    }

    public void setHhppMglManager(HhppMglManager hhppMglManager) {
        this.hhppMglManager = hhppMglManager;
    }
    
    // Validar Opciones por Rol
    public boolean validarOpcionBuscar() {
        return validarEdicionRol(BSMDHHPPB);
    }
    
    //funcion General
      private boolean validarEdicionRol(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
            return tieneRolPermitido;
        }catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    private List<CmtDireccionDetalladaMgl> agregarNodoCertificado(List<CmtDireccionDetalladaMgl> direccionDetalladaList) throws ApplicationException {
        List<CmtDireccionDetalladaMgl> direccionDetalladaListNodos = new ArrayList<>();
        hhppList = new ArrayList<>();
        for (CmtDireccionDetalladaMgl direccion : direccionDetalladaList) {
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
    
    
    
}
