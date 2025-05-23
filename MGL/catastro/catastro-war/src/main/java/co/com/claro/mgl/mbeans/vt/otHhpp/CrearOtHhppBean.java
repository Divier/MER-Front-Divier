/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.otHhpp;

import co.com.claro.mer.exception.ExceptionServBean;
import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.NodoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.CmtNotasOtHhppDetalleMglFacadeLocal;
import co.com.claro.mgl.facade.CmtNotasOtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.DireccionRRFacadeLocal;
import co.com.claro.mgl.facade.DireccionesValidacionFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.OtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosCallesFacadeLocal;
import co.com.claro.mgl.facade.RrCiudadesFacadeLocal;
import co.com.claro.mgl.facade.TipoOtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtComponenteDireccionesMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionDetalleMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtValidadorDireccionesFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.CmtNotasOtHhppDetalleMgl;
import co.com.claro.mgl.jpa.entities.CmtNotasOtHhppMgl;
import co.com.claro.mgl.jpa.entities.ConfigurationAddressComponent;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.OpcionIdNombre;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.RrCiudades;
import co.com.claro.mgl.jpa.entities.RrRegionales;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.mgl.ws.cm.request.RequestCreaDireccionOtDirecciones;
import co.com.claro.mgl.ws.cm.response.ResponseConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseMesaje;
import co.com.claro.mgl.ws.cm.response.ResponseValidacionDireccion;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.AddressSuggested;
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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Juan David Hernandez
 */
@ViewScoped
@ManagedBean(name = "crearOtHhppBean")
public class CrearOtHhppBean implements Serializable {

    private static final String CREAR_OT_HHPP = "Crear OT HHPP";
    private String serverHost;
    private AddressService addressService;
    private SecurityLogin securityLogin;
    private String usuarioVT = null;
    private String cedulaUsuarioVT = null;
    private int perfilVt = 0;
    private String selectedTab = "GENERAL";
    private String nombreContacto;
    private String telefonoContacto;
    private String correoContacto;
    private String notaComentarioStr;
    private Date fechaCreacionOt;
    private String[] tecnologiaId;
    private boolean generalTab;
    private boolean agendamientoTab;
    private boolean notasTab;
    private boolean barrioSugeridoField;
    private boolean obtenerNodoGestion;
    private BigDecimal estadoInicial;
    private BigDecimal estadoInicialInterno;
    private CmtBasicaMgl estadoInicialBasica;
    private BigDecimal segmentoIdBasica;
    private BigDecimal tipoOtHhppId;
    private BigDecimal idCentroPoblado;
    private OtHhppMgl otHhppCreada = new OtHhppMgl();
    private CmtBasicaMgl tipoNotaSelected = new CmtBasicaMgl();
    private CmtNotasOtHhppMgl cmtNotasOtHhppMgl = new CmtNotasOtHhppMgl();
    private GeograficoPoliticoMgl centroPobladoSeleccionado;
    private TipoOtHhppMgl tipoOtHhppSeleccionado;
    private GeograficoPoliticoMgl ciudadGpo;
    private GeograficoPoliticoMgl departamentoGpo;
    private List<CmtNotasOtHhppMgl> notasOtHhppList;
    private List<CmtBasicaMgl> tipoNotaList;
    private List<CmtBasicaMgl> tecnologiaBasicaList;
    private List<CmtBasicaMgl> tipoSegmentoOtList;
    private List<TipoOtHhppMgl> tipoOtHhppList;
    private List<CmtBasicaMgl> estadoGeneralList;
    private List<CmtBasicaMgl> estadoInicialInternoList;
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    private ConfigurationAddressComponent configurationAddressComponent;
    private List<HhppMgl> hhppList;
    private List<CmtDireccionDetalladaMgl> direccionesDetalladasList;
    private CmtDireccionDetalladaMgl direccionDetalladaSeleccionada;
    private List<GeograficoPoliticoMgl> centroPobladoList;
    private List<RrRegionales> regionalList;
    private List<GeograficoPoliticoMgl> ciudadesList;
    private List<RrCiudades> comunidadList;
    private List<GeograficoPoliticoMgl> ciudadList;
    private List<GeograficoPoliticoMgl> departamentoList;
    private List<OpcionIdNombre> ckList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> bmList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> itList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> aptoList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> complementoList = new ArrayList<OpcionIdNombre>();
    private DrDireccion drDireccion = new DrDireccion();
    private CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl;
    private GeograficoPoliticoMgl departamentoSeleccionado;
    private ResponseValidacionDireccion responseValidarDireccion = new ResponseValidacionDireccion();
    private RequestCreaDireccionOtDirecciones crearDireccioOt = new RequestCreaDireccionOtDirecciones();
    private List<AddressSuggested> barrioSugeridoList;
    private boolean notGeoReferenciado = true;
    private String barrioSugerido;
    private boolean barrioSugeridoPanel;
    private boolean otCreada;
    private boolean showCKPanel;
    private boolean showBMPanel;
    private boolean showITPanel;
    private boolean busquedaHhppManual;
    private boolean crearDireccion;
    private boolean botonCrearDireccion;
    private boolean mostrarPanelCrearOT = false;
    private boolean mostrarTablaTecnologias = false;
    private String espacio = "&nbsp;";
    private String departamento;
    private String createDireccion;
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
    private String apartamento;
    private String complemento;
    private String direccionLabel;
    private String rrRegional;
    private String pageActual;
    private String numPagina = "1";
    private String inicioPagina = "<< - Inicio";
    private String anteriorPagina = "< - Anterior";
    private String finPagina = "Fin - >>";
    private String siguientePagina = "Siguiente - >";
    private List<Integer> paginaList;
    private String barrioSugeridoStr;
    private List<HhppMgl> tecnologiaHabilitadaList;
    private HhppMgl tecnologiaHabTrabajo = new HhppMgl();
    private int actual;
    private int filasPag = ConstantsCM.PAGINACION_DIEZ_FILAS;
    private ResponseConstruccionDireccion responseConstruirDireccion
            = new ResponseConstruccionDireccion();
    private FacesContext facesContext = FacesContext.getCurrentInstance();

    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private RequestConstruccionDireccion request
            = new RequestConstruccionDireccion();
    private static final Logger LOGGER = LogManager.getLogger(CrearOtHhppBean.class);
    
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private OtHhppMglFacadeLocal otHhppMglFacadeLocal;
    @EJB
    private CmtNotasOtHhppMglFacadeLocal cmtNotasOtHhppMglFacadeLocal;
    @EJB
    private TipoOtHhppMglFacadeLocal tipoOtHhppMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
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
    private HhppMglFacadeLocal hhppMglFacadeLocal;
    @EJB
    private CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMglFacadeLocal;
    @EJB
    private CmtNotasOtHhppDetalleMglFacadeLocal cmtNotasOtHhppDetalleMglFacadeLocal;
    @EJB
    private CmtValidadorDireccionesFacadeLocal cmtValidadorDireccionesFacadeLocal;
    @EJB
    private ParametrosCallesFacadeLocal parametrosCallesFacade;
    @Inject
    private ExceptionServBean exceptionServBean;


    public CrearOtHhppBean() throws ApplicationException, IOException {
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
            FacesUtil.mostrarMensajeError("Error en CrearOtHhppBean. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en CrearOtHhppBean. ", e, LOGGER);
        }
    }

    @PostConstruct
    private void init() {
        try {
            crearDireccion = false;
            botonCrearDireccion = false;
            otCreada = false;
            fechaCreacionOt = new Date();
            obtenerDepartamentoList();
            cargarListadosConfiguracion();
            showCK();
            obtenerEstadoGeneralList();
            obtenerTipoOtHhppList();
            obtenerTipoTecnologiaList();
            obtenerSegmentoList();
            showGeneralTab();
            obtenerEstadoInicialInternoList();
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
            otHhppMglFacadeLocal.setUser(usuarioVT, perfilVt);

        } catch (ApplicationException e) {
            String msgError = "Error al realizar cargue inicial de la pantalla: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, CREAR_OT_HHPP);
        } catch (Exception e) {
            String msgError = "Ocurrió un error al realizar cargue inicial de la pantalla: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, CREAR_OT_HHPP);
        }
    }

    /**
     * Función que obtiene los estados segun el tipo de ot seleccionado
     *
     * @author Juan David Hernandez
     */
    public void obtenerEstadosTipoOtHhpp() {
        try {
            if (tipoOtHhppId != null) {
                tipoOtHhppSeleccionado
                        = tipoOtHhppMglFacadeLocal.findTipoOtHhppById(tipoOtHhppId);

                if (tipoOtHhppSeleccionado != null) {
                    mostrarTablaTecnologias = tipoOtHhppSeleccionado.getManejaTecnologias().compareTo(BigDecimal.ONE) == 0;
                } else {
                    mostrarTablaTecnologias = false;
                }
                obtenerEstadoInicialInternoList();
            }
        } catch (ApplicationException e) {
            String msgError = "Error al obtener los estados para el tipo de ot seleccionado: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, CREAR_OT_HHPP);
        } catch (Exception e) {
            String msgError = "Ocurrió un error al obtener los estados para el tipo de ot seleccionado: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, CREAR_OT_HHPP);
        }
    }

    /**
     * Función utilizada para crear una ot en la base de datos
     *
     * @author Juan David Hernandez
     */
    public void crearOtHhpp() {
        try {
            if (validarDatosOtHhpp()) {

                otHhppCreada.setTipoOtHhppId(tipoOtHhppSeleccionado);
                otHhppCreada.setNombreContacto(nombreContacto);
                otHhppCreada.setTelefonoContacto(telefonoContacto);
                otHhppCreada.setCorreoContacto(correoContacto);
                otHhppCreada.setFechaCreacionOt(fechaCreacionOt);
                CmtBasicaMgl razonInicial = cmtBasicaMglFacadeLocal.findById(estadoInicialInterno);
                otHhppCreada.setEstadoInternoInicial(razonInicial);
                CmtBasicaMgl estadoGeneral = cmtBasicaMglFacadeLocal.findById(estadoInicial);
                otHhppCreada.setEstadoGeneral(estadoGeneral);

                CmtBasicaMgl razonInicialInternaSeleccionada = cmtBasicaMglFacadeLocal.findById(estadoInicialInterno);
                otHhppCreada.setEstadoInternoInicial(razonInicialInternaSeleccionada);
                
                CmtBasicaMgl segmentoSeleccionado = cmtBasicaMglFacadeLocal.findById(segmentoIdBasica);
                otHhppCreada.setSegmento(segmentoSeleccionado);

                if (!direccionesDetalladasList.isEmpty()) {
                    if (direccionesDetalladasList.get(0).isSelected()) {
                        otHhppCreada.setDireccionId(direccionDetalladaSeleccionada.getDireccion());
                        otHhppCreada.setSubDireccionId(direccionDetalladaSeleccionada.getSubDireccion());
                    }
                }

                if (!mostrarTablaTecnologias) {
                    tecnologiaBasicaList = new ArrayList<CmtBasicaMgl>();
                    CmtBasicaMgl cmtBasicaMglTec = new CmtBasicaMgl();
                    tecnologiaHabTrabajo = hhppMglFacadeLocal.findById(tecnologiaHabTrabajo.getHhpId());
                    cmtBasicaMglTec.setBasicaId(tecnologiaHabTrabajo.getNodId().getNodTipo().getBasicaId());
                    cmtBasicaMglTec.setNodoGestion(tecnologiaHabTrabajo.getNodId().getNodCodigo());
                    cmtBasicaMglTec.setSincronizaRr(false);
                    tecnologiaBasicaList.add(cmtBasicaMglTec);
                }

                otHhppCreada = otHhppMglFacadeLocal.createIndependentOt(otHhppCreada, tecnologiaBasicaList);

                if (otHhppCreada != null && otHhppCreada.getOtHhppId() != null) {
                    String msnError = "Se ha creado la Ot numero: " + otHhppCreada.getOtHhppId() + " satisfactoriamente";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, msnError, ""));
                    OtHhppMglSessionBean otHhppMglSessionBean
                            = (OtHhppMglSessionBean) JSFUtil.getSessionBean(OtHhppMglSessionBean.class);
                    otHhppMglSessionBean.setOtHhppMglSeleccionado(otHhppCreada);
                    otHhppMglSessionBean.setDetalleOtHhpp(true);
                    otCreada = true;
                } else {
                    String msnError = "No se ha podido crear la Ot"
                            + " de manera correcta. Intente nuevamente por favor";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError, ""));
                    otCreada = false;
                }
            }
        } catch (ApplicationException e) {
            String msgError = "Error al crear la OT del HHPP: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, CREAR_OT_HHPP);
        } catch (Exception e) {
            String msgError = "Ocurrió un error al crear la OT del HHPP: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, CREAR_OT_HHPP);
        }
    }

    /**
     * Función utilizada para validar los datos correspondiente a la creación de
     * una ot
     *
     * @author Juan David Hernandez
     * @return 
     */
    public boolean validarDatosOtHhpp() {
        try {            
            if(segmentoIdBasica == null || segmentoIdBasica.equals(BigDecimal.ZERO)){
               String msnError = "Debe seleccionar un SEGMENTO por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
                return false; 
            }else{            
            if (tipoOtHhppId == null) {
                String msnError = "Seleccione un TIPO DE OT por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
                return false;
            } else {
                if (nombreContacto == null || nombreContacto.trim().isEmpty()) {
                    String msnError = "Seleccione un NOMBRE DE CONTACTO por favor.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
                    return false;
                } else {
                    if (telefonoContacto == null || telefonoContacto.trim().isEmpty()) {
                        String msnError = "Seleccione un TELÉFONO DE CONTACTO por favor.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
                        return false;
                    }
                    if (correoContacto == null || correoContacto.trim().isEmpty()) {
                        String msnError = "Debe ingresar un CORREO ELECTRÓNICO "
                                + "del contacto.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msnError, ""));
                        return false;
                    } else {
                        if (!correoContacto.contains("@")
                                || !correoContacto.contains(".")) {
                            String msnError = "Debe ingresar un CORREO "
                                    + "ELECTRÓNICO Válido.";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msnError, ""));
                            return false;
                        } else {
                            if (estadoInicial == null) {
                                String msnError = "Debe existir seleccionado un "
                                        + "estado general.";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                msnError, ""));
                                return false;
                            } else {
                                if (estadoInicialInterno == null) {
                                    String msnError = "Debe existir seleccionada una "
                                            + "razon inicial.";
                                    FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                    msnError, ""));
                                    return false;
                                } else {
                                    if (!busquedaHhppManual) {
                                        if (direccionesDetalladasList == null || direccionesDetalladasList.isEmpty()) {
                                            String msnError = "Debe realizar "
                                                    + "la búsqueda de una dirección y seleccionarla por favor ";
                                            FacesContext.getCurrentInstance().addMessage(null,
                                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                            msnError, ""));
                                            return false;
                                        } else {
                                            if (!validarSeleccionDireccion()) {
                                                return false;
                                            } else {
                                                if (!validarSeleccionNodoGestion()) {
                                                    String msnError = "Debe seleccionar al menos un nodo de gestion";
                                                    FacesContext.getCurrentInstance().addMessage(null,
                                                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                                    msnError, ""));
                                                    return false;
                                                } else {
                                                    if (!validarDireccionYSubdireccion()) {
                                                        String msnError = "No es posible realizar la Ot para esta direccion";
                                                        FacesContext.getCurrentInstance().addMessage(null,
                                                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                                        msnError, ""));
                                                        return false;
                                                    } else {
                                                        if (mostrarTablaTecnologias) {
                                                            if (!validarTecnologiasParaHhpp()) {
                                                                return false;
                                                            } else {
                                                                if (!validarNodosIngresados()) {
                                                                    return false;
                                                                } else {
                                                                    if (!validarExistenciaDeOtRepetida()) {
                                                                        return false;
                                                                    }
                                                                }
                                                            }
                                                            if (isMasDeUnaTecnologiaNoVerificacion()) {
                                                                return false;
                                                        }
                                                        } else {
                                                            if (tecnologiaHabTrabajo == null
                                                                    || tecnologiaHabTrabajo.getHhpId() == null
                                                                    || (tecnologiaHabTrabajo.getHhpId().
                                                                            compareTo(BigDecimal.ZERO) == 0)) {
                                                                String msnError = "Debe selccionar una Tecnología Habilitada";
                                                                    FacesContext.getCurrentInstance().addMessage(null,
                                                                            new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
                                                                    return false;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return true;
        } catch (RuntimeException e) {
            LOGGER.error("Error en validarDatosOtHhpp, al validar dato de creacion de ot hhpp: " + e.getMessage(), e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Error en validarDatosOtHhpp, al validar dato de creacion de ot hhpp: " + e.getMessage(), e);
            return false;
        }
    }

    private boolean isMasDeUnaTecnologiaNoVerificacion() {

        try {
            if (tipoOtHhppSeleccionado.getEsTipoVisitaTecnica().equals("1")) {
                return false;
            }
            int contadorTecnologiasGestion = 0;
            for (CmtBasicaMgl tecnologiaBasica : tecnologiaBasicaList) {
                if (!tecnologiaBasica.getNodoGestion().trim().isEmpty()) {
                    contadorTecnologiasGestion++;
                }
            }
            if (contadorTecnologiasGestion > 1) {
                String msnError = "Este tipo de OT no es de verificacion, "
                        + "Solo se puede gestionar una sola tecnologia";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
                return true;
            }
            return false;
        } catch (Exception e) {
            LOGGER.error("Error en validarTecnologiasParaHhpp, validarMasDeUnaTecnologiaNoVerificacion", e);
            return true;
        }
    }

    /**
     * Función utilizada para que la direccion contenga valores en en nivel 5
     *
     * @author Orlando Velasquez
     * @return 
     */
    public boolean validarDireccionYSubdireccion() {

        if (direccionDetalladaSeleccionada.getCpTipoNivel5() != null) {
            if ((direccionDetalladaSeleccionada.getSubDireccion() != null
                    && direccionDetalladaSeleccionada.getDireccion() != null)
                    || direccionDetalladaSeleccionada.getCpTipoNivel5().equalsIgnoreCase(Constant.CP_TIPO_NIVEL_5_CASA)) {
                return true;
            }
        } else {
            if ((direccionDetalladaSeleccionada.getSubDireccion() != null
                    && direccionDetalladaSeleccionada.getDireccion() != null)) {
                return true;
            }
        }
        return false;

    }

    /**
     * Función utilizada para validar si existe una Ot en estado abierto con la
     * misma direccion
     *
     * @author Orlando Velasquez
     * @return 
     */
    public boolean validarExistenciaDeOtRepetida() {
        try {
            CmtBasicaMgl estadoActivo;
            estadoActivo = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.ESTADO_OT_ABIERTO);
            OtHhppMgl otAlmacenada = otHhppMglFacadeLocal.findOtByDireccionAndSubDireccion(
                    direccionDetalladaSeleccionada.getDireccion(),
                    direccionDetalladaSeleccionada.getSubDireccion(),
                    estadoActivo);
            if (otAlmacenada != null) {
                String msn = "Ya existe una ot pendiente por gestion para esta direccion";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msn, ""));
                return false;
            }

        } catch (ApplicationException e) {
            LOGGER.error("Error en validarExistenciaDeOtRepetida, obteniendo Direccion Detallada: " + e.getMessage(), e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Error en validarExistenciaDeOtRepetida, obteniendo Direccion Detallada: " + e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * Función utilizada para validar los nodos ingresados en pantalla para la
     * gestion de la solicitud
     *
     * @author Juan David Hernandez
     * @return 
     */
    public boolean validarNodosIngresados() {
        try {

            if (tecnologiaBasicaList != null) {

                NodoMglManager nodoMglManager = new NodoMglManager();
                for (CmtBasicaMgl cmtBasicaMgl : tecnologiaBasicaList) {
                    if (cmtBasicaMgl.getNodoGestion() != null
                            && !cmtBasicaMgl.getNodoGestion().isEmpty()) {
                        NodoMgl nodoMgl = nodoMglManager.findByCodigoNodo(cmtBasicaMgl.getNodoGestion(),
                                idCentroPoblado,
                                cmtBasicaMgl.getBasicaId());
                        if (nodoMgl == null) {
                            String msn = "El nodo " + cmtBasicaMgl.getNodoGestion()
                                    + " no corresponde a la tecnología, a la ciudad "
                                    + "o no se encuentra bien digitado. ";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msn, ""));
                            return false;
                        }
                    }
                }
            }

            return true;
        } catch (ApplicationException e) {
            LOGGER.error("Error en validarNodosIngresados, validando los nodos digitados: " + e.getMessage(), e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Error en validarNodosIngresados, validando los nodos digitados: " + e.getMessage(), e);
            return false;
        }
    }

    public boolean validarSeleccionNodoGestion() {
        if(!mostrarTablaTecnologias){
            return true;
        }
        for (CmtBasicaMgl tecnologiaBasica : tecnologiaBasicaList) {
            if (!tecnologiaBasica.getNodoGestion().trim().equalsIgnoreCase("")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Función utilizada para validar si las tecnologias seleccionadas para
     * creacion de Hhpp ya existen para esta direccion
     *
     * @author Orlando Velasquez
     * @return 
     */
    public boolean validarTecnologiasParaHhpp() {

        try {
            DireccionMgl direccion = direccionDetalladaSeleccionada.getDireccion();
            SubDireccionMgl subDireccion = null;
            if (direccionDetalladaSeleccionada.getSubDireccion() != null) {
                subDireccion = direccionDetalladaSeleccionada.getSubDireccion();
            }

            //Homepass asociados a la direccion y subdireccion
            List<HhppMgl> hhppSelectedList = hhppMglFacadeLocal.findByDirAndSubDir(direccion, subDireccion);

            for (CmtBasicaMgl tecnologiaBasica : tecnologiaBasicaList) {
                for (HhppMgl hhpp : hhppSelectedList) {
                    if ((hhpp.getNodId().getNodTipo().getBasicaId().compareTo(tecnologiaBasica.getBasicaId()) == 0)
                            && !tecnologiaBasica.getNodoGestion().trim().equalsIgnoreCase("")) {
                        String msnError = "Esta direccion ya posee un "
                                + "Hhpp con la tecnologia:  " + tecnologiaBasica.getNombreBasica();
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
                        return false;
                    }
                }
            }
            return true;
        } catch (ApplicationException e) {
            LOGGER.error("Error en validarTecnologiasParaHhpp, al validar dato de creacion de ot hhpp ", e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Error en validarTecnologiasParaHhpp, al validar dato de creacion de ot hhpp ", e);
            return false;
        }

    }

    /**
     * Función utilizada para obtener las tecnologias seleccionadas en pantalla
     * por el usuario.
     *
     * @author Juan David Hernandez
     * @param tecnologiaId
     * @return
     */
    public List<CmtBasicaMgl> obtenerListadoTecnologiasSeleccionadas(String[] tecnologiaId) {
        try {
            if (tecnologiaId != null && tecnologiaId.length > 0) {
                List<CmtBasicaMgl> tecnologiaIdList = new ArrayList();
                for (int i = 0; i < tecnologiaId.length; i++) {
                    BigDecimal idTecnologiaSeleccionada = new BigDecimal(tecnologiaId[i]);
                    CmtBasicaMgl tecnologiaBasica = new CmtBasicaMgl();
                    tecnologiaBasica.setBasicaId(idTecnologiaSeleccionada);
                    tecnologiaIdList.add(tecnologiaBasica);
                }
                return tecnologiaIdList;
            }
            return null;
        } catch (RuntimeException e) {
            LOGGER.error("Error en obtenerListadoTecnologiasSeleccionadas: " + e.getMessage(), e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en obtenerListadoTecnologiasSeleccionadas: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Función utilizada para obtener el id de la dirección seleccionada en
     * pantalla.
     *
     * @author Juan David Hernandez
     * @return
     */
    public CmtDireccionDetalladaMgl obtenerDireccionSeleccionada() {
        try {
            if (hhppList != null && !hhppList.isEmpty()) {

                for (HhppMgl hhpp : hhppList) {
                    if (hhpp.isSelected()) {
                        List<CmtDireccionDetalladaMgl> direccionList = new ArrayList();

                        if (hhpp.getDireccionObj() != null
                                && hhpp.getDireccionObj().getDirId() != null
                                && hhpp.getSubDireccionObj() != null
                                && hhpp.getSubDireccionObj().getDirId() != null) {
                            direccionList
                                    = cmtDireccionDetalleMglFacadeLocal
                                            .findDireccionDetallaByDirIdSdirId(hhpp.getDirId(), hhpp.getSdiId());
                        } else {
                            if (hhpp.getDireccionObj() != null
                                    && hhpp.getDireccionObj().getDirId() != null
                                    && (hhpp.getSubDireccionObj() == null
                                    || hhpp.getSubDireccionObj().getSdiId() == null
                                    || hhpp.getSubDireccionObj().getSdiId().equals(Constant.BASICA_ID_DEFAULT))) {
                                direccionList
                                        = cmtDireccionDetalleMglFacadeLocal
                                                .findDireccionDetallaByDirIdSdirId(hhpp.getDireccionObj().getDirId(), null);
                            }
                        }

                        if (direccionList != null && direccionList.size() > 1) {
                            for (CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl1 : direccionList) {
                                if (cmtDireccionDetalladaMgl1.getDireccion() != null
                                        && cmtDireccionDetalladaMgl1.getDireccion().getDirId() != null
                                        && (cmtDireccionDetalladaMgl1.getSubDireccion() == null
                                        || cmtDireccionDetalladaMgl1.getSubDireccion().getSdiId() == null)) {
                                    return cmtDireccionDetalladaMgl1;
                                }
                            }
                        } else {
                            if (direccionList != null) {
                                return direccionList.get(0);
                            }
                        }
                    }
                }
            }
            return null;
        } catch (ApplicationException e) {
            LOGGER.error("Error en obtenerDireccionSeleccionada, al validar la seleccion de una única dirección ", e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en obtenerDireccionSeleccionada, al validar la seleccion de una única dirección ", e);
            return null;
        }
    }

    /**
     * Función utilizada para validar que solo se haya seleccionada una única
     * dirección en pantalla por parte del usuario para la creacion de la ot.
     *
     * @author Juan David Hernandez
     * @return
     */
    public boolean validarSeleccionDireccion() {
        try {
            if (direccionesDetalladasList != null && !direccionesDetalladasList.isEmpty()) {
                int cont = 0;
                for (CmtDireccionDetalladaMgl direccioneDetallada : direccionesDetalladasList) {
                    if (direccioneDetallada.isSelected()) {
                        cont++;
                    }
                }
                if (cont > 1) {
                    String msnError = "Debe seleccionar "
                            + "solamente una dirección ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));
                    return false;
                } else {
                    if (cont == 0) {
                        String msnError = "Debe seleccionar "
                                + "una dirección del listado ";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msnError, ""));
                        return false;
                    }
                }
            }
            return true;
        } catch (RuntimeException e) {
            LOGGER.error("Error en validarSeleccionDireccion, al validar la seleccion de una única dirección: " + e.getMessage(), e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Error en validarSeleccionDireccion, al validar la seleccion de una única dirección: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Función que obtiene tipo de tecnología
     *
     * @author Juan David Hernandez
     */
    public void obtenerTipoTecnologiaList() {
        try {
            //Obtiene valores de tipo de tecnología
            CmtTipoBasicaMgl tipoBasicaTipoTecnologia;
            tipoBasicaTipoTecnologia = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TECNOLOGIA);
            tecnologiaBasicaList
                    = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoTecnologia);

            if (tecnologiaBasicaList != null && !tecnologiaBasicaList.isEmpty()) {
                for (CmtBasicaMgl tecnologiaBasicaMgl : tecnologiaBasicaList) {
                    tecnologiaBasicaMgl.setNodoGestion(
                            establecerNodoCoberturaSugerido(tecnologiaBasicaMgl));
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerTipoTecnologiaList. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerTipoTecnologiaList. ", e, LOGGER);
        }
    }
    
    /**
     * Obtiene listado de segmentos
     *
     * @author Diana Enriquez
     */
    public void obtenerSegmentoList() throws ApplicationException {
        try {
            CmtTipoBasicaMgl tipoBasicaSegmento;
            tipoBasicaSegmento = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_DE_SEGMENTO);
            tipoBasicaSegmento = cmtTipoBasicaMglFacadeLocal.findById(tipoBasicaSegmento);
            tipoSegmentoOtList = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaSegmento);
        } catch (ApplicationException e) {
            LOGGER.error("Error en obtenerSegmentoList. " + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en obtenerSegmentoList. " + e.getMessage());
            throw new ApplicationException("Error en obtenerSegmentoList: " + e.getMessage(), e);
        }
    }

    /**
     * Función que obtiene los tipo de ot registrados en la base de datos
     *
     * @author Juan David Hernandez
     */
    public void obtenerTipoOtHhppList() {
        try {
            tipoOtHhppList = tipoOtHhppMglFacadeLocal.findAll();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerTipoOtHhppList, al realizar cargue del listado de tipo de ot hhpp ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerTipoOtHhppList, al realizar cargue del listado de tipo de ot hhpp ", e, LOGGER);
        }
    }

    /**
     * Obtiene listado de estados generales iniciales de tipo de ot
     *
     * @author Juan David Hernandez
     */
    public void obtenerEstadoGeneralList() {
        try {
            CmtTipoBasicaMgl tipoBasica;
            tipoBasica = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.ESTADO_GENERAL_OT_HHPP);
            estadoGeneralList
                    = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasica);

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerEstadoGeneralList, al cargar listado de estados inicial interno ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerEstadoGeneralList, al cargar listado de estados inicial interno ", e, LOGGER);
        }
    }

    /**
     * Obtiene listado de estados iniciales internos
     *
     * @author Juan David Hernandez
     */
    public void obtenerEstadoInicialInternoList() {
        try {
            if (estadoGeneralList != null && !estadoGeneralList.isEmpty()) {
                if (estadoInicial != null) {

                    estadoInicialBasica = cmtBasicaMglFacadeLocal.findById(estadoInicial);
                    CmtTipoBasicaMgl tipoBasica;
                    tipoBasica = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                            estadoInternoACargar(estadoInicialBasica.getIdentificadorInternoApp()));

                    estadoInicialInternoList = cmtBasicaMglFacadeLocal
                            .findByTipoBasica(tipoBasica);

                    TipoOtHhppMgl tipoOtSeleccionado = tipoOtHhppMglFacadeLocal.findTipoOtHhppById(tipoOtHhppId);

                    if (tipoOtSeleccionado.getManejaTecnologias().compareTo(BigDecimal.ONE) == 0) {
                    } else {
                        for (int i = 0; i < estadoInicialInternoList.size(); i++) {
                            if (estadoInicialInternoList.get(i).getIdentificadorInternoApp()
                                    .equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO_CREAR_HHPP)) {
                                estadoInicialInternoList.remove(estadoInicialInternoList.get(i));
                            }
                        }
                    }
                } else {
                    estadoInicialInternoList = new ArrayList();
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerEstadoInicialInternoList, al cargar listado de estados inicial interno ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerEstadoInicialInternoList, al cargar listado de estados inicial interno ", e, LOGGER);
        }
    }

    /**
     * Función que válida el el tipo de estado interno que se debe cargar según
     * el seleccionado por el usuario.
     *
     * @author Juan David Hernandez
     * @param estadoSeleccionado
     * @return
     */
    public String estadoInternoACargar(String estadoSeleccionado) {
        try {
            if (estadoSeleccionado != null
                    && !estadoSeleccionado.trim().isEmpty()) {

                if (estadoSeleccionado.contains(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO)) {
                    return Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO;
                } else {
                    if (estadoSeleccionado.contains(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO)) {
                        return Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO;
                    } else {
                        if (estadoSeleccionado.contains(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ANULADO)) {
                            return Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ANULADO;
                        }
                    }
                }
            }
            return "--";
        } catch (RuntimeException e) {
            LOGGER.error("Error en estadoInternoACargar: " + e.getMessage(), e);
            return "--";
        } catch (Exception e) {
            LOGGER.error("Error en estadoInternoACargar: " + e.getMessage(), e);
            return "--";
        }
    }

    /**
     * Función que permite navegar entre Tabs en la pantalla
     *
     * @author Orlando Velasquez Diaz
     * @param tabSeleccionado
     */
    public void cambiarTab(String tabSeleccionado) {
        try {
            ConstantsCM.TABS_HHPP Seleccionado
                    = ConstantsCM.TABS_HHPP.valueOf(tabSeleccionado);
            switch (Seleccionado) {
                case GENERAL:
                    selectedTab = "GENERAL";
                    showGeneralTab();
                    break;
                case AGENDAMIENTO:
                    selectedTab = "AGENDAMIENTO";
                    showAgendamientoTab();
                    break;
                case NOTAS:
                    if (otHhppCreada != null && otHhppCreada.getOtHhppId() != null) {
                        cmtNotasOtHhppMgl = new CmtNotasOtHhppMgl();
                        tipoNotaSelected = new CmtBasicaMgl();
                        notaComentarioStr = "";
                        selectedTab = "NOTAS";
                        findNotasByOtHhppId();
                        obtenerTipoNotasList();
                        showNotasTab();
                    } else {
                        String msnError = "Debe contar con una OT creada "
                                + "para asociar las notas.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msnError, ""));
                    }
                    break;
            }
        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Error en cambiarTab. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cambiarTab. ", e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Error en validarTipoDireccion, al validar el tipo de dirección seleccionado ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validarTipoDireccion, al validar el tipo de dirección seleccionado ", e, LOGGER);
        }
    }

    /**
     * Función que obtiene las notas de la solicitud creada.
     *
     * @author Juan David Hernandez
     */
    public void findNotasByOtHhppId() {
        try {
            notasOtHhppList = cmtNotasOtHhppMglFacadeLocal
                    .findNotasByOtHhppId(otHhppCreada.getOtHhppId());
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en findNotasByOtHhppId, al obtener las notas de la ot ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en findNotasByOtHhppId, al obtener las notas de la ot ", e, LOGGER);
        }
    }

    /**
     * Función que guardar una comentario a una nota asociada a la ot
     *
     * @author Juan David Hernandez
     */
    public void guardarComentarioNota() {
        try {
            if (validarDatosComentarioNota()) {
                CmtNotasOtHhppDetalleMgl notaComentarioMgl
                        = new CmtNotasOtHhppDetalleMgl();
                notaComentarioMgl.setNota(notaComentarioStr);
                notaComentarioMgl.setNotaOtHhpp(cmtNotasOtHhppMgl);
                cmtNotasOtHhppDetalleMglFacadeLocal.setUser(usuarioVT, perfilVt);
                notaComentarioMgl
                        = cmtNotasOtHhppDetalleMglFacadeLocal.create(notaComentarioMgl);
                if (notaComentarioMgl.getNotasDetalleId() != null) {
                    notaComentarioStr = "";
                    tipoNotaSelected = new CmtBasicaMgl();
                    cmtNotasOtHhppMgl = new CmtNotasOtHhppMgl();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Comentario añadido correctamente.", ""));
                }
                cambiarTab("NOTAS");
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en guardarComentarioNota, al guardar comentario en la nota seleccionada ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en guardarComentarioNota, al guardar comentario en la nota seleccionada ", e, LOGGER);
        }
    }

    public boolean validarDatosComentarioNota() {
        try {
            if (notaComentarioStr == null || notaComentarioStr.trim().isEmpty()) {
                String msn = "Debe ingresar un comentario a la nota por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msn, ""));
                return false;
            }
            return true;
        } catch (RuntimeException e) {
            LOGGER.error("Error en validarDatosComentarioNota, al validar datos de guardar comentario a nota ", e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Error en validarDatosComentarioNota, al validar datos de guardar comentario a nota ", e);
            return false;
        }
    }

    /**
     * Función utilizada para mostrar el panel que permite agregar un comentario
     *
     * @author Juan David Hernandez
     * @param nota
     */
    public void mostarComentario(CmtNotasOtHhppMgl nota) {
        cmtNotasOtHhppMgl = nota;
        tipoNotaSelected = nota.getTipoNotaObj();
    }

    public void limpiarCamposNota() {
        cmtNotasOtHhppMgl = new CmtNotasOtHhppMgl();
        tipoNotaSelected = new CmtBasicaMgl();
    }

    /**
     * Función que valida los datos necesarios para guardar una descripción de
     * la nota
     *
     * @author Juan David Hernandez return true si no se encuentran datos vacios
     * @return
     */
    public boolean validarCamposNota() {
        try {
            if (cmtNotasOtHhppMgl == null) {
                String msn = "Ha ocurrido un error intentando guardar una nota."
                        + " Intente más tarde por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;
            } else {
                if (cmtNotasOtHhppMgl.getDescripcion() == null
                        || cmtNotasOtHhppMgl.getDescripcion().isEmpty()) {
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
                        if (cmtNotasOtHhppMgl.getNota() == null
                                || cmtNotasOtHhppMgl.getNota().isEmpty()) {
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
            LOGGER.error("Error en validarCamposNota: " + e.getMessage(), e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Error en validarCamposNota: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Función que guardar una nota asociada a la solicitud
     *
     * @author Juan David Hernandez
     */
    public void guardarNota() {
        try {
            if (validarCamposNota()) {
                cmtNotasOtHhppMgl.setOtHhppId(otHhppCreada);
                cmtNotasOtHhppMgl.setTipoNotaObj(tipoNotaSelected);
                cmtNotasOtHhppMglFacadeLocal.setUser(usuarioVT, perfilVt);
                cmtNotasOtHhppMgl
                        = cmtNotasOtHhppMglFacadeLocal
                                .crearNotSol(cmtNotasOtHhppMgl);
                if (cmtNotasOtHhppMgl != null
                        && cmtNotasOtHhppMgl.getNotasId() != null) {
                    findNotasByOtHhppId();
                    tipoNotaSelected = new CmtBasicaMgl();
                    cmtNotasOtHhppMgl = new CmtNotasOtHhppMgl();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Nota agregada correctamente.", ""));
                }
                cambiarTab("NOTAS");
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en guardarNota. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en guardarNota. ", e, LOGGER);
        }
    }

    /**
     * Función que obtiene tipo de notas
     *
     * @author Juan David Hernandez
     */
    public void obtenerTipoNotasList() {
        try {
            CmtTipoBasicaMgl tipoBasicaNotaOt;
            tipoBasicaNotaOt = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_DE_NOTAS);
            tipoNotaList = cmtBasicaMglFacadeLocal
                    .findByTipoBasica(tipoBasicaNotaOt);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerTipoNotasList, al obtener tipos de notas  ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerTipoNotasList, al obtener tipos de notas  ", e, LOGGER);
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
        createDireccion = "";
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
        request.setBarrio(null);
        barrioSugeridoStr = "";
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
        botonCrearDireccion = false;
        crearDireccion = false;
        barrioSugeridoPanel = false;
        mostrarPanelCrearOT = false;
        notGeoReferenciado = true;
        createDireccion = "";
        tipoOtHhppId = BigDecimal.ZERO;
        nombreContacto = "";
        telefonoContacto = "";
        correoContacto = "";
        estadoInicialInterno = BigDecimal.ZERO;
        estadoInicialBasica = new CmtBasicaMgl();
        tecnologiaId = null;
        otCreada = false;
        estadoInicial = BigDecimal.ZERO;
        busquedaHhppManual = false;
        otHhppCreada = new OtHhppMgl();
        mostrarTablaTecnologias = false;       

    }

    /**
     * Función utilizada para limpiar la pantalla
     *
     * @author Juan David Hernandez
     */
    public void limpiarFormulario() {
        tipoOtHhppId = BigDecimal.ZERO;
        nombreContacto = "";
        telefonoContacto = "";
        correoContacto = "";
        estadoInicialInterno = BigDecimal.ZERO;
        estadoInicialBasica = new CmtBasicaMgl();
        tecnologiaId = null;
        otCreada = false;
        estadoInicial = BigDecimal.ZERO;
        busquedaHhppManual = false;
        otHhppCreada = new OtHhppMgl();
        mostrarTablaTecnologias = false;        
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
        request.setBarrio(null);
        barrioSugeridoStr = "";
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
        botonCrearDireccion = false;
        crearDireccion = false;
        barrioSugeridoPanel = false;
        mostrarPanelCrearOT = false;
        notGeoReferenciado = true;
        createDireccion = "";
    }

    /**
     * Función utilizada para redireccionar a la pantalla listado de Ot
     *
     * @author Juan David Hernandez
     */
    public void irListadoOtHhpp() {
        try {
            FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/otHhppList.jsf");
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irListadoOtHhpp, al redireccionar a creación de Tipo Ot. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irListadoOtHhpp, al redireccionar a creación de Tipo Ot. ", e, LOGGER);
        }
    }

    public void seleccionarDireccion(CmtDireccionDetalladaMgl direccionDetallada) {
        try {
           
            if (!direccionesDetalladasList.isEmpty()) {
                //recorrido para deshacer la seleccion de las direcciones y dejarla limpia
                for (CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl : direccionesDetalladasList) {
                    cmtDireccionDetalladaMgl.setSelected(false);
                }
                otHhppCreada.setDireccionId(direccionDetallada.getDireccion());
                otHhppCreada.setSubDireccionId(direccionDetallada.getSubDireccion());
            } else {
                String msnError = "La Dirección seleccionada no posee dirección "
                        + "detallada.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msnError, ""));
                return;
            }
            direccionDetalladaSeleccionada = direccionDetallada;
            mostrarPanelCrearOT = true;
            //se selecciona solo la dirección del click del usuario
            direccionDetalladaSeleccionada.setSelected(true);
            obtenerDireccionDetallada(direccionDetallada);
            obtenerDrDireccion(cmtDireccionDetalladaMgl);
            obtenerNodosCoberturaGeo(drDireccion, idCentroPoblado, departamentoSeleccionado);
            obtenerTipoTecnologiaList();
            obtenerTecnologiasHabilitadasDireccion();
            obtenerNodoGestion = true;
        } catch (ApplicationException e) {
            String msgError = "Error al redireccionar a creación de Tipo OT:  " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, CREAR_OT_HHPP);
        } catch (Exception e) {
            String msgError = "Ocurrió un error al redireccionar a creación de Tipo OT: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, CREAR_OT_HHPP);
        }
    }

    private void obtenerTecnologiasHabilitadasDireccion() {
        if (direccionDetalladaSeleccionada.getSubDireccion() == null) {
            tecnologiaHabilitadaList = hhppMglFacadeLocal.findHhppDireccion(
                    direccionDetalladaSeleccionada.getDireccion().getDirId());

        } else {
            tecnologiaHabilitadaList = hhppMglFacadeLocal.findHhppSubDireccion(
                    direccionDetalladaSeleccionada.getSubDireccion().getSdiId());
        }
        tecnologiaHabTrabajo.setHhpId(BigDecimal.ZERO);
        if (tecnologiaHabilitadaList == null || tecnologiaHabilitadaList.isEmpty()) {
            String msnError = "La Direccion seleccionada no posee Tecnologias"
                    + " habilitadas, solo se puden generar tipos de OT que generen tecnologia";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError, ""));
        }

    }

    public void seleccionaNodoCercano(CmtBasicaMgl cmtBasicaMgl) {
        try {
            if (!obtenerNodoGestion) {
                if (cmtBasicaMgl != null && tecnologiaBasicaList != null) {
                    //recorrido a listado de tecnologias en pantalla
                    for (CmtBasicaMgl basicaMgl : tecnologiaBasicaList) {
                        //valida la tecnologia seleccionada
                        if (basicaMgl.getBasicaId().equals(cmtBasicaMgl.getBasicaId())) {
                            NodoMglManager nodoMglManager = new NodoMglManager();
                            if (cmtBasicaMgl.getNodoCercano() != null && !cmtBasicaMgl.getNodoCercano().isEmpty()) {
                                //busqueda del nodo cercano seleccionado
                                NodoMgl nodoCercanoMgl = nodoMglManager.findById(new BigDecimal(cmtBasicaMgl.getNodoCercano()));
                                basicaMgl.setNodoGestion(nodoCercanoMgl.getNodCodigo());
                            } else {
                                basicaMgl.setNodoGestion("");
                            }
                        }
                    }
                } else {
                    String msg = "Ha ocurrido un error seleccionando el nodo cercano,"
                            + " por favor intente de nuevo ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                }
            }
            obtenerNodoGestion = false;
        } catch (ApplicationException e) {
            String msgError = "Error al consultar nodos cercanos: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, CREAR_OT_HHPP);
        } catch (Exception e) {
            String msgError = "Ocurrió un error al consultar nodos cercanos: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, CREAR_OT_HHPP);
        }

    }

    /**
     * Función que obtiene listado de nodos cercanos
     *
     * @author Juan David Hernandez
     * @param tecnologiaBasica
     * @return
     */
    public List<NodoMgl> obtenerNodosCercanosList(CmtBasicaMgl tecnologiaBasica) {
        try {
            List<NodoMgl> nodoCercanoList = null;
            if (cmtDireccionDetalladaMgl != null) {

                nodoCercanoList = hhppMglFacadeLocal.obtenerNodosCercanoSolicitudHhpp(cmtDireccionDetalladaMgl, tecnologiaBasica, idCentroPoblado);

            } else {
                String msn = "No es posible obtener el listado de nodos"
                        + " cercanos debido a que no se cuenta con la direccion "
                        + "detallada correctamente. ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msn, ""));
            }
            return nodoCercanoList;

        } catch (ApplicationException e) {
            String msgError = "Error al obtener la lista de nodos cercanos: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, CREAR_OT_HHPP);
            return null;
        } catch (Exception e) {
            String msgError = "Ocurrió un error al obtener la lista de nodos cercanos: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, CREAR_OT_HHPP);
            return null;
        }
    }

    /**
     * Función que realiza la busqueda de un unidades que coincidan con los
     * criterios de busqueda ingresados en pantalla de busqueda de hhpp.
     *
     * @author Juan David Hernandez
     */
    public void buscarHhpp() {
        try {
          //Si la busqueda se realiza por dirección construida
            if (responseConstruirDireccion.getDrDireccion() != null
                    && direccionesValidacionFacadeLocal
                            .validarEstructuraDireccion(responseConstruirDireccion.getDrDireccion(),
                                    Constant.TIPO_VALIDACION_DIR_HHPP)) {

                listInfoByPage(1);

                      CmtDireccionDetalleMglManager manager = new CmtDireccionDetalleMglManager();
                    //si la direccion no fue encontrada de manera tabulada
                    if (direccionesDetalladasList == null || direccionesDetalladasList.isEmpty()) {
                        if (responseConstruirDireccion != null
                                && responseConstruirDireccion.getDireccionRespuestaGeo() != null
                                && !responseConstruirDireccion.getDireccionRespuestaGeo().isEmpty()
                                && responseConstruirDireccion != null
                                && responseConstruirDireccion.getDrDireccion() != null) {
                            
                            GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                            GeograficoPoliticoMgl centroPobladoCompleto = geograficoPoliticoManager.findById(centroPobladoSeleccionado.getGpoId());

                            if (centroPobladoCompleto != null && centroPobladoCompleto.getGpoMultiorigen() != null
                                    && !centroPobladoCompleto.getGpoMultiorigen().isEmpty() && centroPobladoCompleto.getGpoMultiorigen().equalsIgnoreCase("1")) {
                                responseConstruirDireccion.getDrDireccion().setMultiOrigen("1");
                            } else {
                                responseConstruirDireccion.getDrDireccion().setMultiOrigen("0");
                            }

                            direccionesDetalladasList = manager.busquedaDireccionTextoRespuestaGeo(responseConstruirDireccion.getDireccionRespuestaGeo(),
                                    responseConstruirDireccion.getDrDireccion(), centroPobladoSeleccionado.getGpoId());

                        }
                    }

                if (direccionesDetalladasList == null || direccionesDetalladasList.isEmpty()) {
                    String msnError = "No se obtuvieron resultados con la dirección"
                            + " construida.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));
                    botonCrearDireccion = true;
                    crearDireccion = true;
                    return;
                }
                botonCrearDireccion = true;
                crearDireccion = true;
                obtenerDepartamento();
                busquedaHhppManual = false;
                mostrarPanelCrearOT = false;
            } 

        } catch (RuntimeException e) {
            exceptionServBean.notifyError(e, "Error en buscar HHPP: " + e.getMessage(), CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en buscar HHPP: " + e.getMessage(), CREAR_OT_HHPP);
        }
    }

    /**
     * Función utilizada para crear la direccion
     *
     * @author Orlando Velasquez
     */
    public void crearDireccionOtDirecciones() {
        try {
            CityEntity cityEntity;
            try {
                cityEntity = direccionesValidacionFacadeLocal
                        .validaDireccion(request, false);

                ResponseMesaje responseMesaje = new ResponseMesaje();
                responseMesaje.setMensaje("Proceso Exitoso");
                responseMesaje.setTipoRespuesta("I");
                if (request.getDrDireccion().getBarrio() == null
                        || request.getDrDireccion().getBarrio()
                                .isEmpty()) {
                    request.getDrDireccion().setBarrio(cityEntity.getBarrio());
                }
                responseValidarDireccion
                        .setResponseMesaje(responseMesaje);
                request.getDrDireccion()
                        .convertToDetalleDireccionEntity().
                        setMultiOrigen(
                                cityEntity.getActualDetalleDireccionEntity()
                                        .getMultiOrigen());
                responseValidarDireccion
                        .setDrDireccion(request.getDrDireccion());
                responseValidarDireccion.setCityEntity(cityEntity);

                if (request != null) {
                    //Construcción de request de solicitud
                    crearDireccioOt = new RequestCreaDireccionOtDirecciones();
                    crearDireccioOt.setIdUsuario(request.getIdUsuario());
                    crearDireccioOt.setDrDireccion(request.getDrDireccion());
                    crearDireccioOt.setComunidad(responseValidarDireccion
                            .getCityEntity().getCodCity());
                    crearDireccioOt.setCityEntity(responseValidarDireccion.getCityEntity());
                    otHhppMglFacadeLocal.createDireccionParaOtDirecciones(crearDireccioOt, idCentroPoblado);
                    buscarHhpp();
                    botonCrearDireccion = false;
                    String msn = "La direccion fue creada Exitosamente.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    msn, ""));
                }

            } catch (ApplicationException e) {
                responseValidarDireccion = new ResponseValidacionDireccion();
                ResponseMesaje responseMesaje = new ResponseMesaje();
                responseMesaje.setMensaje("Ocurrio Un Error Validando"
                        + " la Dirección " + e.getMessage());
                responseMesaje.setTipoRespuesta("E");
                responseValidarDireccion.setResponseMesaje(responseMesaje);
                responseValidarDireccion.setDrDireccion(request.getDrDireccion());
                //Si el error no es de multiorigen lo muestre
                if (!e.getMessage().contains("{multiorigen=1}")) {
                    FacesUtil.mostrarMensajeError(e.getMessage(), e, LOGGER);
                }
            }catch (Exception e) {
                responseValidarDireccion = new ResponseValidacionDireccion();
                ResponseMesaje responseMesaje = new ResponseMesaje();
                responseMesaje.setMensaje("Ocurrio Un Error Validando"
                        + " la Dirección " + e.getMessage());
                responseMesaje.setTipoRespuesta("E");
                responseValidarDireccion.setResponseMesaje(responseMesaje);
                responseValidarDireccion.setDrDireccion(request.getDrDireccion());
                //Si el error no es de multiorigen lo muestre
                if (!e.getMessage().contains("{multiorigen=1}")) {
                    FacesUtil.mostrarMensajeError(e.getMessage(), e, LOGGER);
                }
            }

            /*Se valida si la respuesta de la excepción es por ciudad 
                 * multi-origen*/
            if (responseValidarDireccion != null
                    && responseValidarDireccion.getResponseMesaje().
                            getTipoRespuesta().equalsIgnoreCase("E")) {
                validarBarrioCiudadMultiOrigen();

            }
        } catch (RuntimeException e) {
            exceptionServBean.notifyError(e, "Error en crear dirección Ot Direcciones, al validar dirección. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en crear dirección Ot Direcciones, al validar dirección. ", CREAR_OT_HHPP);
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
                    && (request.getBarrio() == null || !request.getBarrio().isEmpty())) {
                /*Consume servicio que permite obtener un listado de barrios 
                 * sugeridos para el usuario. */
                ResponseConstruccionDireccion responseObtenerBarrio;
                try {
                    List<AddressSuggested> barrioList = direccionesValidacionFacadeLocal
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
                    String msg = "Error al validar el barrio ciudad multiorigen en '"+
                        ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
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
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msn, ""));
                    //Se agrega el valor 'Otro' al listado de barrios sugeridos
                    barrioSugeridoList = new ArrayList();
                    AddressSuggested otro = new AddressSuggested();
                    otro.setAddress("Otro");
                    otro.setNeighborhood("Otro");
                    barrioSugeridoList.add(otro);
                    barrioSugeridoPanel = true;
                }
            }

        } catch (RuntimeException e) {
            exceptionServBean.notifyError(e, "Error en validar barrio ciudad MultiOrigen, al validar barrio de la dirección. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en validar barrio ciudad MultiOrigen, al validar barrio de la dirección. ", CREAR_OT_HHPP);
        }
    }

    /**
     * Función que obtiene el valor completo de el centro poblado seleccionado
     * por el usuario en pantalla.
     *
     * @author Juan David Hernandez
     */
    public void obtenerCentroPobladoSeleccionado() {
        try {
            if (idCentroPoblado != null) {
                centroPobladoSeleccionado = geograficoPoliticoMglFacadeLocal.findById(idCentroPoblado);
                barrioSugeridoPanel = false;
            }
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al obtener el centro poblado seleccionado. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error al obtener el centro poblado seleccionado. ", CREAR_OT_HHPP);
        }
    }

    /**
     * Función que obtiene el departamento del Hhpp
     *
     * @author Juan David Hernandez
     */
    public void obtenerDepartamento() {
        try {
            if (direccionesDetalladasList != null && !direccionesDetalladasList.isEmpty()) {
                for (CmtDireccionDetalladaMgl direccionDetallada : direccionesDetalladasList) {
                    GeograficoPoliticoMgl geo;
                    geo = geograficoPoliticoMglFacadeLocal
                            .findById(direccionDetallada.getDireccion()
                                    .getUbicacion().getGpoIdObj().getGeoGpoId());

                    if (geo != null && (geo.getGpoNombre() != null
                            && !geo.getGpoNombre().isEmpty())) {
                        departamentoSeleccionado = geo;
                        direccionDetallada.setDepartamento(geo.getGpoNombre());
                    }
                }
            }
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al obtener el Departamento del listado HHPP. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error al obtener el Departamento del listado HHPP. ", CREAR_OT_HHPP);
        }
    }

    /**
     * Función para validar datos nulos al momento de construir. una dirección
     * tipo Calle-Carrera.
     *
     * @author Juan David Hernandez return false si algun dato se encuentra
     * vacio.
     * @return
     */
    public boolean validarDatosDireccionCk() {
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
            exceptionServBean.notifyError(e, "Error en validar datos dirección CK. ", CREAR_OT_HHPP);
            return false;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en validar datos dirección CK. ", CREAR_OT_HHPP);
            return false;
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
                String msn = "Barrio sugerido agregado exitosamente.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
        } catch (RuntimeException e) {
            exceptionServBean.notifyError(e, "Error en agregar barrio sugerido. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en agregar barrio sugerido. ", CREAR_OT_HHPP);
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
                if (!barrioSugerido.equalsIgnoreCase("Otro") && barrioSugerido != null
                        && !barrioSugerido.isEmpty()
                        && !barrioSugerido.equals("")) {
                    barrioSugeridoStr = barrioSugerido.toUpperCase();
                    barrioSugeridoField = false;
                    request.setBarrio(barrioSugerido.toUpperCase());
                }
            }
        } catch (RuntimeException e) {
            exceptionServBean.notifyError(e, "Error en validar barrio sugerido seleccionado. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en validar barrio sugerido seleccionado. ", CREAR_OT_HHPP);
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
            exceptionServBean.notifyError(e, "Error en validar datos dirección BM. ", CREAR_OT_HHPP);
            return false;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en validar datos dirección BM. ", CREAR_OT_HHPP);
            return false;
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
            }
            return true;
        } catch (RuntimeException e) {
            exceptionServBean.notifyError(e, "Error en validar datos dirección IT. ", CREAR_OT_HHPP);
            return false;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en validar datos dirección IT. ", CREAR_OT_HHPP);
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
            LOGGER.error("Error en isNumeric. " +e.getMessage(), e);    
            return false;
        } catch (Exception e) {
            LOGGER.error("Error en isNumeric. " +e.getMessage(), e);    
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
                //se usa para limpiar el campo de barrio
                request.setBarrio(null);
                barrioSugeridoStr = "";
                request.setDireccionStr(direccionCk);
                request.setComunidad(centroPobladoSeleccionado.getGeoCodigoDane());
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

                    direccionLabel
                            = responseConstruirDireccion.getDireccionStr().
                                    toString();
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
                        notGeoReferenciado = false;
                        direccionLabel = "";

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
            mostrarPanelCrearOT = false;
            crearDireccion = false;
            barrioSugeridoPanel = false;
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error en construir Dirección CK. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en construir Dirección CK. ", CREAR_OT_HHPP);
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
                request.setComunidad(centroPobladoSeleccionado.getGeoCodigoDane());
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
                                                .getDrDireccion().getBarrio() : "";
                        direccionLabel = responseConstruirDireccion.getDireccionStr()
                                .toString();
                        tipoNivelBm = "";
                        nivelValorBm = "";
                    }
                }
            }
            crearDireccion = false;
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error en construir Dirección BM. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en construir Dirección BM. ", CREAR_OT_HHPP);
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
                request.setComunidad(centroPobladoSeleccionado.getGeoCodigoDane());
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
                                                .getBarrio() : "";
                        direccionLabel = responseConstruirDireccion
                                .getDireccionStr().toString();
                        tipoNivelIt = "";
                        nivelValorIt = "";
                    }
                }
            }
            crearDireccion = false;
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error en construirDireccionIt, al construir dirección Intraducible ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en construirDireccionIt, al construir dirección Intraducible ", CREAR_OT_HHPP);
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
                                                .getBarrio() : "";
                        direccionLabel = responseConstruirDireccion
                                .getDireccionStr().toString();
                        complemento = "";
                        tipoNivelComplemento = "";
                    }
                }
            }
            mostrarPanelCrearOT = false;
            crearDireccion = false;
            barrioSugeridoPanel = false;
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error en construir Direccion Complemento. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en construir Direccion Complemento. ", CREAR_OT_HHPP);
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
                request.setComunidad(centroPobladoSeleccionado.getGeoCodigoDane());
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
                                                .getDrDireccion().getBarrio() : "";
                        direccionLabel = responseConstruirDireccion
                                .getDireccionStr().toString();
                        apartamento = "";
                        tipoNivelApartamento = "";
                    }
                }
            }
            mostrarPanelCrearOT = false;
            crearDireccion = false;
            barrioSugeridoPanel = false;
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error en construir Dirección Apartamento. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en construir Dirección Apartamento. ", CREAR_OT_HHPP);
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
                    } else {
                        if (tipoNivelApartamento.equalsIgnoreCase("CASA")
                                || tipoNivelApartamento
                                        .equalsIgnoreCase("ADMINISTRACION")
                                || tipoNivelApartamento
                                        .equalsIgnoreCase("FUENTE")
                                || tipoNivelApartamento
                                        .equalsIgnoreCase("RECEPTOR")) {
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
        } catch (RuntimeException e) {
            exceptionServBean.notifyError(e, "Error en validar Datos Dirección Apartamento. ", CREAR_OT_HHPP);
            return false;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en validar Datos Dirección Apartamento. ", CREAR_OT_HHPP);
            return false;
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
            exceptionServBean.notifyError(e, "Error en validar Datos Dirección Complemento. ", CREAR_OT_HHPP);
            return false;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en validar Datos Dirección Complemento. ", CREAR_OT_HHPP);
            return false;
        }
    }

    /**
     * Obtiene el listado de departamentos de la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void obtenerCiudadesList() {
        try {
            if (departamento != null
                    && !departamento.isEmpty()) {

                //Obtenemos el listado de ciudades para el filtro de la pantalla
                ciudadesList
                        = geograficoPoliticoMglFacadeLocal.findCiudades(new BigDecimal(departamento));
                centroPobladoList = new ArrayList();
            }
            ciudadList = new ArrayList();
            barrioSugeridoPanel = false;
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error en obtenerCiudadesList, al obtener listado de departamentos. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en obtenerCiudadesList, al obtener listado de departamentos. ", CREAR_OT_HHPP);
        }
    }

    /**
     * Función utilizada para obtener el listado de regiones desde la base
     * datos.
     *
     * @author Juan David Hernandez
     */
    public void obtenerListaRegionales() {
        try {
            cargarListadosConfiguracion();
            ciudadesList = null;
            centroPobladoList = null;
            rrRegional = null;
            ciudad = null;
            idCentroPoblado = null;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerListaRegionales. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerListaRegionales. ", e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Error en obtenerComunidadesList, al obtener listado de comunidades. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerComunidadesList, al obtener listado de comunidades. ", e, LOGGER);
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
            String msgError = "Error en cargarListadosConfiguracion, al realizar consultas para obtener configuración de listados. ";
            exceptionServBean.notifyError(e, msgError, CREAR_OT_HHPP);
        } catch (Exception e) {
            String msgError = "Ocurrió un error en cargarListadosConfiguracion, al realizar consultas para obtener configuración de listados. ";
            exceptionServBean.notifyError(e, msgError, CREAR_OT_HHPP);
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
            FacesUtil.mostrarMensajeError("Error en obtenerGeograficoPoliticoList, al validar ciudad seleccionada. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerGeograficoPoliticoList, al validar ciudad seleccionada. ", e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Error en obtenerCiudadDepartamentoByComunidad, al obtener listado ciudad y departamento. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerCiudadDepartamentoByComunidad, al obtener listado ciudad y departamento. ", e, LOGGER);
        }
    }

    public String establecerNodoCoberturaSugerido(CmtBasicaMgl tecnologia) {
        String nodoCobertura = "";
        try {
            if (tecnologia.getIdentificadorInternoApp() != null && !tecnologia.getIdentificadorInternoApp().isEmpty()) {
                if (addressService != null) {
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
            }
        } catch (RuntimeException ex) {
            nodoCobertura = "";
            LOGGER.error("Error en establecerNodoCoberturaSugerido " + ex.getMessage(), ex);
        } catch (Exception ex) {
            nodoCobertura = "";
            LOGGER.error("Error en establecerNodoCoberturaSugerido " + ex.getMessage(), ex);
        }
        return nodoCobertura;

    }

    public String establecerNodoGestion(CmtBasicaMgl tecnologia) {
        String nodoGestion = "";
        try {
            if (tecnologia.getIdentificadorInternoApp() != null && !tecnologia.getIdentificadorInternoApp().isEmpty()) {
                if (tecnologia.getIdentificadorInternoApp().equals(Constant.DTH)) {
                    nodoGestion = addressService.getNodoDth();

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.FIBRA_FTTTH)) {
                    nodoGestion = addressService.getNodoFtth();

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.HFC_BID)) {
                    nodoGestion = addressService.getNodoUno();

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.HFC_UNI)) {
                    nodoGestion = addressService.getNodoDos();

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.FIBRA_OP_GPON)) {
                    nodoGestion = addressService.getNodoTres();

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.LTE_INTERNET)) {
                    nodoGestion = addressService.getNodoWifi();

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.BTS_MOVIL)) {
                    nodoGestion = addressService.getNodoMovil();
                }
            }
        } catch (RuntimeException ex) {
            nodoGestion = "";
            LOGGER.error("Error en establecerNodoGestion " + ex.getMessage(), ex);
        } catch (Exception ex) {
            nodoGestion = "";
            LOGGER.error("Error en establecerNodoGestion " + ex.getMessage(), ex);
        }
        return nodoGestion;

    }

    /**
     * Función que obtiene el DrDireccion de una direccion apartir de la
     * dirDetallada
     *
     * @author Juan David Hernandez
     * @param direccionDetallada
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void obtenerDrDireccion(CmtDireccionDetalladaMgl direccionDetallada)
            throws ApplicationException {
        try {
            if (direccionDetallada != null) {
                CmtDireccionDetalleMglManager direccionDetalladaManager = new CmtDireccionDetalleMglManager();
                drDireccion
                        = direccionDetalladaManager.parseCmtDireccionDetalladaMglToDrDireccion(direccionDetallada);

                if (drDireccion == null) {
                    throw new ApplicationException("No fue posible obtener la direccion apartir de la dirección detallada,"
                            + " intente crear de nuevo la solicitud ya quen o es posible gestionarla ");
                }
            } else {
                throw new ApplicationException("No fue posible obtener la direccion apartir de la dirección detallada,"
                        + " intente crear de nuevo la solicitud ya quen o es posible gestionarla ");
            }

        } catch (ApplicationException e) {
            LOGGER.error("Error en obtenerDrDireccion, al obtener la direccion detallada de la solicitud. ", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en obtenerDrDireccion, al obtener la direccion detallada de la solicitud. ", e);
            throw new ApplicationException("Error en obtenerDrDireccion. ",e);
        }
    }

    /**
     * Función que obtiene la direccion detallada extraida de la solicitud
     *
     * @author Juan David Hernandez
     * @param dirDetalle
     */
    public void obtenerDireccionDetallada(CmtDireccionDetalladaMgl dirDetalle) {

            cmtDireccionDetalladaMgl = dirDetalle;

    }

    /**
     * Obtiene el listado de centro poblado por ciudad de la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void obtenerCentroPobladoList() {
        try {
            if (ciudad != null) {
                centroPobladoList = geograficoPoliticoMglFacadeLocal
                        .findCentroPoblado(ciudad);
            } else {
                centroPobladoList = new ArrayList();
            }

        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error en obtenerCentroPobladoList, al obtener listado de centro poblado. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en obtenerCentroPobladoList, al obtener listado de centro poblado. ", CREAR_OT_HHPP);
        }
    }

    /**
     * Obtiene el listado de departamentos de la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void obtenerDepartamentoList() {
        try {
            departamentoList = geograficoPoliticoMglFacadeLocal.findDptos();
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al obtener listado de departamentos. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error al obtener listado de departamentos. ", CREAR_OT_HHPP);
        }
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
        hideTipoDireccion();
        showCK();
    }

    /**
     * Función que realiza paginación de la tabla.
     *
     * @param page;
     * @author Juan David Hernandez
     * @return
     */
    public String listInfoByPage(int page) {
        try {
            actual = page;
            getPageActual();

            List<CmtDireccionDetalladaMgl> direccionesDetalladaList;
            responseConstruirDireccion.setDrDireccion(request.getDrDireccion());
            direccionesDetalladaList = cmtDireccionDetalleMglFacadeLocal
                    .findDireccionByDireccionDetallada(responseConstruirDireccion.getDrDireccion(), idCentroPoblado, true,
                            page, filasPag, true);
            
             CmtDireccionDetalleMglManager direccionDetalladaManager = new CmtDireccionDetalleMglManager();         
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
            
            if ((direccionesDetalladaList != null && !direccionesDetalladaList.isEmpty())
                    || (direccionDetalladaTextoList != null && !direccionDetalladaTextoList.isEmpty())) {
                direccionesDetalladaList = direccionDetalladaManager.combinarDireccionDetalladaList
                                                      (direccionesDetalladaList, direccionDetalladaTextoList);
            }            

            if (direccionesDetalladaList != null && !direccionesDetalladaList.isEmpty()) {
                for (CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl1 : direccionesDetalladaList) {
                    if (cmtDireccionDetalladaMgl1.getCpTipoNivel5() != null) {
                        if ((cmtDireccionDetalladaMgl1.getSubDireccion() != null
                                && cmtDireccionDetalladaMgl1.getDireccion() != null)
                                || cmtDireccionDetalladaMgl1.getCpTipoNivel5()
                                        .equalsIgnoreCase(Constant.CP_TIPO_NIVEL_5_CASA)) {
                        } else {
                            cmtDireccionDetalladaMgl1.setCpTipoNivel5(null);
                            cmtDireccionDetalladaMgl1.setCpValorNivel5(null);
                        }
                    } else {
                        if ((cmtDireccionDetalladaMgl1.getSubDireccion() != null
                                && cmtDireccionDetalladaMgl1.getDireccion() != null)) {
                        } else {
                            cmtDireccionDetalladaMgl1.setCpTipoNivel5(null);
                            cmtDireccionDetalladaMgl1.setCpValorNivel5(null);
                        }
                    }
                }

                direccionesDetalladasList = direccionesDetalladaList;
            } else {
                direccionesDetalladasList = null;
            }

        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error en listInfoByPage, en lista de paginación. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en listInfoByPage, en lista de paginación. ", CREAR_OT_HHPP);
        }
        return null;
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

           //direccionConstruida = true;
            direccionLabel = createDireccion;
        } catch (RuntimeException e) {
            exceptionServBean.notifyError(e, "Error en buildDireccion. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en buildDireccion. ", CREAR_OT_HHPP);
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
            exceptionServBean.notifyError(ex, "Error en optionBisDir. ", CREAR_OT_HHPP);
        }
        return null;
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
            throw new ApplicationException("Error en obtenerListadoTipoSolicitudList. ",e);
        }
        return tList;

    }

    /**
     * Función que carga la primera página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageFirst() {
            listInfoByPage(1);

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
        } catch (NumberFormatException e) {
            exceptionServBean.notifyError(e, "Error en pagePrevious, direccionando a la página anterior. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en pagePrevious, direccionando a la página anterior. ", CREAR_OT_HHPP);
        }
    }

    /**
     * Función que permite ir directamente a la página seleccionada de
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
        } catch (NumberFormatException e) {
            exceptionServBean.notifyError(e, "Error en irPagina, direccionando a página. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en irPagina, direccionando a página. ", CREAR_OT_HHPP);
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
        } catch (NumberFormatException e) {
            exceptionServBean.notifyError(e, "Error en pageNext, direccionando a la siguiente página. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en pageNext, direccionando a la siguiente página. ", CREAR_OT_HHPP);
        }
    }

    /**
     * Función que obtiene listado de nodos dth por ciudad
     *
     * @author Juan David Hernandez
     * @param departamento
     * @param centroPoblado
     */
    public void obtenerNodosCoberturaGeo(DrDireccion drDireccion,
            BigDecimal centroPoblado, GeograficoPoliticoMgl departamento) {
        try {

            GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
            GeograficoPoliticoMgl centroPobladoObj = geograficoPoliticoManager.findById(centroPoblado);

            addressService = cmtValidadorDireccionesFacadeLocal
                    .calcularCoberturaDrDireccion(drDireccion, centroPobladoObj);

        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error en obtenerNodosCoberturaGeo, al obtener listado de Rr nodos Dth. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en obtenerNodosCoberturaGeo, al obtener listado de Rr nodos Dth. ", CREAR_OT_HHPP);
        }
    }

    /**
     * Función que carga la última página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageLast() {
        int totalPaginas = getTotalPaginas();
        listInfoByPage(totalPaginas);

    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     *
     * @author Juan David Hernandez
     * @return retorna el numero de paginas
     */
    public int getTotalPaginas() {
        try {
            int totalPaginas;

            List<CmtDireccionDetalladaMgl> totalDirecciones = cmtDireccionDetalleMglFacadeLocal
                    .findDireccionByDireccionDetallada(responseConstruirDireccion.getDrDireccion(), idCentroPoblado, true,
                            0, 0, false);

            int pageSol = totalDirecciones.size();
            //CONSULTA DE CONTEO

            totalPaginas = (int) ((pageSol % filasPag != 0)
                    ? (pageSol / filasPag) + 1 : pageSol / filasPag);

            return totalPaginas;
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error en getTotalPaginas, direccionando a la primera página. ", CREAR_OT_HHPP);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Ocurrió un error en getTotalPaginas, direccionando a la primera página. ", CREAR_OT_HHPP);
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

    /**
     * Función que renderiza la pestaña de general
     *
     * @author Juan David Hernandez
     */
    public void showGeneralTab() {
        generalTab = true;
        agendamientoTab = false;
        notasTab = false;
    }

    /**
     * Función que renderiza la pestaña de agendamiento
     *
     * @author Juan David Hernandez
     */
    public void showAgendamientoTab() {
        generalTab = false;
        agendamientoTab = true;
        notasTab = false;
    }

    /**
     * Función que renderiza la pestaña de notas
     *
     * @author Juan David Hernandez
     */
    public void showNotasTab() {
        generalTab = false;
        agendamientoTab = false;
        notasTab = true;
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

    public boolean isAgendamientoTab() {
        return agendamientoTab;
    }

    public void setAgendamientoTab(boolean agendamientoTab) {
        this.agendamientoTab = agendamientoTab;
    }

    public boolean isNotasTab() {
        return notasTab;
    }

    public void setNotasTab(boolean notasTab) {
        this.notasTab = notasTab;
    }

    public List<TipoOtHhppMgl> getTipoOtHhppList() {
        return tipoOtHhppList;
    }

    public void setTipoOtHhppList(List<TipoOtHhppMgl> tipoOtHhppList) {
        this.tipoOtHhppList = tipoOtHhppList;
    }

    public BigDecimal getTipoOtHhppId() {
        return tipoOtHhppId;
    }

    public void setTipoOtHhppId(BigDecimal tipoOtHhppId) {
        this.tipoOtHhppId = tipoOtHhppId;
    }

    public List<CmtBasicaMgl> getEstadoGeneralList() {
        return estadoGeneralList;
    }

    public void setEstadoGeneralList(List<CmtBasicaMgl> estadoGeneralList) {
        this.estadoGeneralList = estadoGeneralList;
    }

    public List<CmtBasicaMgl> getEstadoInicialInternoList() {
        return estadoInicialInternoList;
    }

    public void setEstadoInicialInternoList(List<CmtBasicaMgl> estadoInicialInternoList) {
        this.estadoInicialInternoList = estadoInicialInternoList;
    }

    public CmtBasicaMgl getEstadoInicialBasica() {
        return estadoInicialBasica;
    }

    public void setEstadoInicialBasica(CmtBasicaMgl estadoInicialBasica) {
        this.estadoInicialBasica = estadoInicialBasica;
    }

    public BigDecimal getEstadoInicialInterno() {
        return estadoInicialInterno;
    }

    public void setEstadoInicialInterno(BigDecimal estadoInicialInterno) {
        this.estadoInicialInterno = estadoInicialInterno;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public Date getFechaCreacionOt() {
        return fechaCreacionOt;
    }

    public void setFechaCreacionOt(Date fechaCreacionOt) {
        this.fechaCreacionOt = fechaCreacionOt;
    }

    public String[] getTecnologiaId() {
        return tecnologiaId;
    }

    public void setTecnologiaId(String[] tecnologiaId) {
        this.tecnologiaId = tecnologiaId;
    }

    public List<CmtBasicaMgl> getTecnologiaBasicaList() {
        return tecnologiaBasicaList;
    }

    public void setTecnologiaBasicaList(List<CmtBasicaMgl> tecnologiaBasicaList) {
        this.tecnologiaBasicaList = tecnologiaBasicaList;
    }

    public BigDecimal getIdCentroPoblado() {
        return idCentroPoblado;
    }

    public void setIdCentroPoblado(BigDecimal idCentroPoblado) {
        this.idCentroPoblado = idCentroPoblado;
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

    public List<HhppMgl> getHhppList() {
        return hhppList;
    }

    public void setHhppList(List<HhppMgl> hhppList) {
        this.hhppList = hhppList;
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

    public List<GeograficoPoliticoMgl> getCiudadesList() {
        return ciudadesList;
    }

    public void setCiudadesList(List<GeograficoPoliticoMgl> ciudadesList) {
        this.ciudadesList = ciudadesList;
    }

    public List<RrCiudades> getComunidadList() {
        return comunidadList;
    }

    public void setComunidadList(List<RrCiudades> comunidadList) {
        this.comunidadList = comunidadList;
    }

    public List<GeograficoPoliticoMgl> getCiudadList() {
        return ciudadList;
    }

    public void setCiudadList(List<GeograficoPoliticoMgl> ciudadList) {
        this.ciudadList = ciudadList;
    }

    public List<GeograficoPoliticoMgl> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<GeograficoPoliticoMgl> departamentoList) {
        this.departamentoList = departamentoList;
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

    public String getEspacio() {
        return espacio;
    }

    public void setEspacio(String espacio) {
        this.espacio = espacio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public BigDecimal getCiudad() {
        return ciudad;
    }

    public void setCiudad(BigDecimal ciudad) {
        this.ciudad = ciudad;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
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

    public String getDireccionLabel() {
        return direccionLabel;
    }

    public void setDireccionLabel(String direccionLabel) {
        this.direccionLabel = direccionLabel;
    }

    public String getRrRegional() {
        return rrRegional;
    }

    public void setRrRegional(String rrRegional) {
        this.rrRegional = rrRegional;
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

    public OtHhppMgl getOtHhppCreada() {
        return otHhppCreada;
    }

    public void setOtHhppCreada(OtHhppMgl otHhppCreada) {
        this.otHhppCreada = otHhppCreada;
    }

    public boolean isBusquedaHhppManual() {
        return busquedaHhppManual;
    }

    public void setBusquedaHhppManual(boolean busquedaHhppManual) {
        this.busquedaHhppManual = busquedaHhppManual;
    }

    public boolean isOtCreada() {
        return otCreada;
    }

    public void setOtCreada(boolean otCreada) {
        this.otCreada = otCreada;
    }

    public List<CmtNotasOtHhppMgl> getNotasOtHhppList() {
        return notasOtHhppList;
    }

    public void setNotasOtHhppList(List<CmtNotasOtHhppMgl> notasOtHhppList) {
        this.notasOtHhppList = notasOtHhppList;
    }

    public String getNotaComentarioStr() {
        return notaComentarioStr;
    }

    public void setNotaComentarioStr(String notaComentarioStr) {
        this.notaComentarioStr = notaComentarioStr;
    }

    public CmtBasicaMgl getTipoNotaSelected() {
        return tipoNotaSelected;
    }

    public void setTipoNotaSelected(CmtBasicaMgl tipoNotaSelected) {
        this.tipoNotaSelected = tipoNotaSelected;
    }

    public CmtNotasOtHhppMgl getCmtNotasOtHhppMgl() {
        return cmtNotasOtHhppMgl;
    }

    public void setCmtNotasOtHhppMgl(CmtNotasOtHhppMgl cmtNotasOtHhppMgl) {
        this.cmtNotasOtHhppMgl = cmtNotasOtHhppMgl;
    }

    public List<CmtBasicaMgl> getTipoNotaList() {
        return tipoNotaList;
    }

    public void setTipoNotaList(List<CmtBasicaMgl> tipoNotaList) {
        this.tipoNotaList = tipoNotaList;
    }

    public List<CmtDireccionDetalladaMgl> getDireccionesDetalladasList() {
        return direccionesDetalladasList;
    }

    public void setDireccionesDetalladasList(List<CmtDireccionDetalladaMgl> direccionesDetalladasList) {
        this.direccionesDetalladasList = direccionesDetalladasList;
    }

    public boolean isCrearDireccion() {
        return crearDireccion;
    }

    public void setCrearDireccion(boolean crearDireccion) {
        this.crearDireccion = crearDireccion;
    }

    public boolean isBotonCrearDireccion() {
        return botonCrearDireccion;
    }

    public void setBotonCrearDireccion(boolean botonCrearDireccion) {
        this.botonCrearDireccion = botonCrearDireccion;
    }

    public List<AddressSuggested> getBarrioSugeridoList() {
        return barrioSugeridoList;
    }

    public void setBarrioSugeridoList(List<AddressSuggested> barrioSugeridoList) {
        this.barrioSugeridoList = barrioSugeridoList;
    }

    public boolean isBarrioSugeridoPanel() {
        return barrioSugeridoPanel;
    }

    public void setBarrioSugeridoPanel(boolean barrioSugeridoPanel) {
        this.barrioSugeridoPanel = barrioSugeridoPanel;
    }

    public String getBarrioSugerido() {
        return barrioSugerido;
    }

    public void setBarrioSugerido(String barrioSugerido) {
        this.barrioSugerido = barrioSugerido;
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

    public boolean isMostrarPanelCrearOT() {
        return mostrarPanelCrearOT;
    }

    public void setMostrarPanelCrearOT(boolean mostrarPanelCrearOT) {
        this.mostrarPanelCrearOT = mostrarPanelCrearOT;
    }

    public boolean isMostrarTablaTecnologias() {
        return mostrarTablaTecnologias;
    }

    public void setMostrarTablaTecnologias(boolean mostrarTablaTecnologias) {
        this.mostrarTablaTecnologias = mostrarTablaTecnologias;
    }

    public BigDecimal getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(BigDecimal estadoInicial) {
        this.estadoInicial = estadoInicial;
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
    
    public List<HhppMgl> getTecnologiaHabilitadaList() {
        return tecnologiaHabilitadaList;
    }
    
    public void setTecnologiaHabilitadaList(List<HhppMgl> tecnologiaHabilitadaList) {
        this.tecnologiaHabilitadaList = tecnologiaHabilitadaList;
    }
    
    public HhppMgl getTecnologiaHabTrabajo() {
        return tecnologiaHabTrabajo;
    }
    
    public void setTecnologiaHabTrabajo(HhppMgl tecnologiaHabTrabajo) {
        this.tecnologiaHabTrabajo = tecnologiaHabTrabajo;
}

    public TipoOtHhppMgl getTipoOtHhppSeleccionado() {
        return tipoOtHhppSeleccionado;
    }

    public void setTipoOtHhppSeleccionado(TipoOtHhppMgl tipoOtHhppSeleccionado) {
        this.tipoOtHhppSeleccionado = tipoOtHhppSeleccionado;
    }

    public BigDecimal getSegmentoIdBasica() {
        return segmentoIdBasica;
    }

    public void setSegmentoIdBasica(BigDecimal segmentoIdBasica) {
        this.segmentoIdBasica = segmentoIdBasica;
    }

    public List<CmtBasicaMgl> getTipoSegmentoOtList() {
        return tipoSegmentoOtList;
    }

    public void setTipoSegmentoOtList(List<CmtBasicaMgl> tipoSegmentoOtList) {
        this.tipoSegmentoOtList = tipoSegmentoOtList;
    }
    
}
