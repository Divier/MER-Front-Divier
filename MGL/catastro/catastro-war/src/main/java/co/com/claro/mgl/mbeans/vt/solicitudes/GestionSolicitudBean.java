package co.com.claro.mgl.mbeans.vt.solicitudes;

import co.com.claro.mer.exception.ExceptionServBean;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.NodoMglManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtValidadorDireccionesManager;
import co.com.claro.mgl.dtos.MailSenderDTO;
import co.com.claro.mgl.dtos.NodoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.*;
import co.com.claro.mgl.facade.cm.*;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.*;
import co.com.claro.mgl.mbeans.direcciones.HhppDetalleSessionBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.mbeans.vt.otHhpp.OtHhppMglSessionBean;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.claro.visitasTecnicas.business.ConsultaEspecificaManager;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.services.comun.Constantes;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * Managed Bean asociado a la vista de gestión de solicitudes.
 *
 * @author Juan David Hernandez
 */
@ManagedBean(name = "gestionSolicitudBean")
@ViewScoped
@Log4j2
public class GestionSolicitudBean implements Serializable {

    public static final String DEFAULT_TIME = "00:00:00";
    public static final String USUARIO_NO_VALIDO = "Usuario No Válido";
    private static final String SELECCIONE_UNO = "Seleccione Uno";
    private final Format formatter = new SimpleDateFormat("yyyy-MM-dd");
    private Date dateInicioCreacion;
    private String usuarioVt = null;
    private String cedulaUsuarioVt = null;
    private String nodoCercano;
    private String nodoGestion;
    private String nodoCobertura;
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    private int perfilVt = 0;
    private AddressService addressService;
    private DrDireccion drDireccion;
    private CmtBasicaMgl tipoTecnologiaBasica = new CmtBasicaMgl();
    private GeograficoPoliticoMgl ciudadGpo;
    private GeograficoPoliticoMgl departamentoGpo;
    @Getter
    @Setter
    private GeograficoPoliticoMgl centroPobladoGpo;
    private Solicitud solicitudDthSeleccionada = new Solicitud();
    private CmtTipoSolicitudMgl cmtTipoSolicitudMgl;
    private List<HhppMgl> hhppList;
    private List<CmtNotasSolicitudVtMgl> notasSolicitudList;
    private List<CmtBasicaMgl> estratoList;
    private List<GeograficoPoliticoMgl> centroPobladoList;
    private List<RrRegionales> regionalList;
    private List<RrCiudades> ciudadesList;
    private List<ParametrosCalles> resultGestionDth;
    private List<ParametrosCalles> tipoSolicitudList;
    private List<CmtBasicaMgl> tecnologiaBasicaList;
    private List<CmtBasicaMgl> tipoTecnologiaBasicaList;
    private List<CmtBasicaMgl> tipoAccionSolicitudBasicaList;
    private List<CmtBasicaMgl> tipoNotaList;
    private List<String> nodosCoberturaList;
    private List<NodoMgl> nodoAnteriorList;
    private List<NodoDto> listaNodosCercanosArray;
    private List<UnidadStructPcml> unidadList;
    private List<UnidadStructPcml> unidadAuxiliarList;
    private List<UnidadStructPcml> unidadConflictoList = new ArrayList<>();
    private List<UnidadStructPcml> unidadConflictoTmpList = new ArrayList<>();
    private List<UnidadStructPcml> unidadModificadaList = new ArrayList<>();
    private List<UnidadStructPcml> unidadModificadaTemporalList = new ArrayList<>();
    private List<String> listaNodosCobertura = new ArrayList<>();
    private List<NodoMgl> nodosDthList;
    @Getter
    @Setter
    private List<OpcionIdNombre> listNivel5;
    @Getter
    @Setter
    private List<OpcionIdNombre> listNivel6;
    private boolean alertaMostradaCambioAptos = false;
    @Getter
    @Setter
    private boolean deshabilitarNodoCercano;
    private boolean unidadConflictoPanel;
    private boolean cambioDireccionPanel;
    private boolean solicitudGestionada;
    private boolean showGestionSolicitudList;
    private boolean showGestionSolicitud;
    @Getter
    @Setter
    private boolean showSolicitud;
    private boolean showSolicitudCambioEstrato;
    private boolean showTrack;
    private boolean showNotas;
    private boolean unidadesPredio;
    private boolean unidadesPredioModificadas;
    @Getter
    @Setter
    private boolean showEtiquetas;
    private boolean habilitarRR = false;
    private boolean detalleSolicitud = false;
    private boolean hhppExiste = false;
    @Getter
    @Setter
    private boolean gestionarOt = false;
    @Getter
    @Setter
    private boolean mostrarCampoDocumento;
    private String tipoNivelNuevoApartamento;
    private String valorNivelNuevoApartamento;
    private String accionConflicto;
    private String complementoDireccion;
    private String dirAntiguaFormatoIgac;
    private String barrioGeo;
    private String notaComentarioStr;
    private String nombreRegional;
    private String nombreCiudad;
    @Getter
    @Setter
    private String tmpInicio;
    private String deltaTiempo;
    private String tmpFin;
    private String timeSol;
    private String direccion;
    private String selectedTab = "SOLICITUD";
    private String rrNodoDth;
    private String nodoAnterior;
    private String numPagina = "1";
    private String inicioPagina = "<< - Inicio";
    private String anteriorPagina = "< - Anterior";
    private String finPagina = "Fin - >>";
    private String siguientePagina = "Siguiente - >";
    private String direccionRespuestaGeo;
    @Getter
    @Setter
    private String etiquetasHhpp;
    private BigDecimal idSolicitudDthSeleccionada;
    @Getter
    @Setter
    private BigDecimal etiquetaAgregarSeleccionada;
    @Getter
    @Setter
    private BigDecimal etiquetaEliminarSeleccionada;
    private List<Integer> paginaList;
    private int actual;
    private int filasPag = ConstantsCM.PAGINACION_CINCO_FILAS;
    private DireccionMgl direccionMglActual;
    private SubDireccionMgl subDireccionMglActual;
    private DireccionRREntity direccionRREntity;
    private CmtBasicaMgl tipoNotaSelected = new CmtBasicaMgl();
    private List<CmtTipoSolicitudMgl> rolesUsuarioList = new ArrayList<>();
    private DetalleDireccionEntity detalleDireccionEntity;
    private CmtNotasSolicitudVtMgl cmtNotasSolicitudVtMgl = new CmtNotasSolicitudVtMgl();
    private CmtTiempoSolicitudMgl cmtTiempoSolicitudMgl = new CmtTiempoSolicitudMgl();
    private CmtTiempoSolicitudMgl cmtTiempoGestionDth = new CmtTiempoSolicitudMgl();
    private CmtTiempoSolicitudMgl cmtTiempoSolicitudMglToCreate = new CmtTiempoSolicitudMgl();
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl;
    @Getter
    @Setter
    private CmtBasicaMgl estratoAntiguo = new CmtBasicaMgl();
    @Getter
    @Setter
    private CmtBasicaMgl estratoNuevo = new CmtBasicaMgl();
    private List<TecArchivosCambioEstrato> tecArchivosCambioEstratos;
    private List<ParametrosCalles> resultGestionCambioEs;
    private List<CmtBasicaMgl> listaEstratos;
    private OtHhppMgl otCreada = new OtHhppMgl();
    @Getter
    @Setter
    private boolean gestionarSolicitud = false;
    @Getter
    @Setter
    private String subscriptores;
    SecurityLogin securityLogin;
    private List<ParametrosCalles> lstDocumentosSoporte = new ArrayList<>();
    @Getter
    @Setter
    private String estiloObligatorio = "<font color='red'>*</font>";
    @Getter
    @Setter
    private String tipoViviendaSeleccionada;
    @Getter
    @Setter
    private String tipoDocumento;
    @Getter
    @Setter
    private String numDocCli;
    @Getter
    @Setter
    private List<TipoHhppMgl> listaTipoHhpp;
    @Getter
    @Setter
    private TipoHhppMgl tipoTecHabilitadaIdSol;
    @Getter
    @Setter
    private CmtArchivosVtMgl archivosVtMgl;
    @Getter
    @Setter
    private List <MarcasMgl> marcasHhppList;
    @Getter
    @Setter
    private List<MarcasMgl> marcasAgregadasHhppList;
    @Getter
    @Setter
    private List<MarcasMgl> marcasList;
    private NodoMgl nodoMglAnterior = null;
    @Getter
    @Setter
    private List<SelectItem> documentoList = new ArrayList<>();
    @Getter
    @Setter
    private List<DetalleFactibilidadMgl> listaDetalleFactibilidadMgl;
    @Getter
    @Setter
    private boolean showValidacionCobertura;

    /* ------------ Inyección de dependencias ------------ */
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacade;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMgl;
    @EJB
    private CmtComponenteDireccionesMglFacadeLocal componenteDireccionesMglFacade;
    @EJB
    private CmtNotasSolicitudDetalleVtMglFacadeLocal notasSolicitudDetalleVtMglFacadeLocal;
    @EJB
    private ModificacionDirFacadeLocal modificacionDirFacadeLocal;
    @EJB
    private CmtNotasSolicitudVtMglFacadeLocal notasSolicitudVtMglFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private ParametrosCallesFacadeLocal parametrosCallesFacade;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private SolicitudFacadeLocal solicitudFacadeLocal;
    @EJB
    private CmtTiempoSolicitudMglFacadeLocal cmtTiempoSolicitudMglFacadeLocal;
    @EJB
    private CmtTipoSolicitudMglFacadeLocal cmtTipoSolicitudMglFacadeLocal;
    @EJB
    private DireccionRRFacadeLocal direccionRRFacadeLocal;
    @EJB
    private SubDireccionMglFacadeLocal subDireccionMglFacadeLocal;
    @EJB
    private DireccionMglFacadeLocal direccionMglFacadeLocal;
    @EJB
    private CmtValidadorDireccionesFacadeLocal cmtValidadorDireccionesFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMglFacadeLocal;
    @EJB
    private TecArcCamEstratoFacadeLocal tecArcCamEstratoFacadeLocal;
    @EJB
    private ParametrosCallesFacadeLocal parametrosCallesFacadeLocal;
    @EJB
    private HhppMglFacadeLocal hhppMglFacadeLocal;
    @EJB
    private OtHhppMglFacadeLocal otHhppMglFacadeLocal;
    @EJB
    private TipoHhppMglFacadeLocal tipoHhppMglFacadeLocal;
    @EJB
    private CmtArchivosVtMglFacadeLocal cmtArchivosVtMglFacadeLocal;
    @EJB
    private MarcasMglFacadeLocal marcasMglFacadeLocal;
    @EJB
    private MarcasHhppMglFacadeLocal marcasHhppMglFacadeLocal;
    @EJB
    private DetalleFactibilidadMglFacadeLocal detalleFactibilidadMglFacadeLocal;
    @EJB
    private  CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;
    @Inject
    private ExceptionServBean exceptionServBean;

    /**
     * Método constructor.
     */
    public GestionSolicitudBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(SecurityLogin.SERVER_PARA_AUTENTICACION);
                }
                return;
            }
            
            usuarioVt = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();
            cedulaUsuarioVt = securityLogin.getIdUser();

            if (usuarioVt == null && !response.isCommitted()) {
                response.sendRedirect(securityLogin.redireccionarLogin());
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al iniciar la pantalla" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error al iniciar la pantalla" + e.getMessage() , e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVt);
            cmtTiempoSolicitudMglFacadeLocal.setUser(usuarioVt, perfilVt);

            // Instacia Bean de Session para obtener la solicitud seleccionada
            SolicitudSessionBean solicitudSessionBean = JSFUtil.getSessionBean(SolicitudSessionBean.class);

            if (Objects.isNull(solicitudSessionBean) || Objects.isNull(solicitudSessionBean.getSolicitudDthSeleccionada()) || Objects.isNull(solicitudSessionBean.getSolicitudDthSeleccionada().getIdSolicitud())) {
                throw new ApplicationException("Ha ocurrido un error seleccionando la solicitud, intente de nuevo por favor.");
            }

            if (Objects.isNull(usuarioLogin.getNombre())) {
                throw new ApplicationException("El usuario es invalido.");
            }

            solicitudDthSeleccionada = solicitudSessionBean.getSolicitudDthSeleccionada();
            idSolicitudDthSeleccionada = solicitudSessionBean.getSolicitudDthSeleccionada().getIdSolicitud();
            detalleSolicitud = solicitudSessionBean.isDetalleSolicitud();

            List<CmtTipoSolicitudMgl> gestionRolList = cmtTipoSolicitudMglFacadeLocal.obtenerTipoSolicitudHhppByRolList(facesContext, Constant.TIPO_APLICACION_SOLICITUD_HHPP, false);
            SecurityLogin securityLoginUser = new SecurityLogin(facesContext);

            if (gestionRolList != null && !gestionRolList.isEmpty()) {
                for (CmtTipoSolicitudMgl rol : gestionRolList) {
                    if (securityLoginUser.usuarioTieneRoll(rol.getGestionRol())) {
                        rolesUsuarioList.add(rol);
                    }
                }
            }

            solicitudDthSeleccionada.setUsuario(usuarioLogin.getNombre());

            if (!detalleSolicitud) {
                solicitudDthSeleccionada.setIdUsuarioGestion(new BigDecimal(usuarioLogin.getCedula()));
                solicitudDthSeleccionada.setUsuarioGestionador(usuarioLogin.getNombre());
            }

            tipoDocumento = solicitudDthSeleccionada.getTipoDoc();
            numDocCli = solicitudDthSeleccionada.getNumDocCliente();

            solicitudGestionada = false;
            if (solicitudDthSeleccionada.getDireccionRespuestaGeo() != null &&
                    !solicitudDthSeleccionada.getDireccionRespuestaGeo().isEmpty()) {
                direccionRespuestaGeo = solicitudDthSeleccionada.getDireccionRespuestaGeo();
            }

            if (StringUtils.equalsIgnoreCase(solicitudDthSeleccionada.getCambioDir(), Constant.CAMBIO_ESTRATO_2)) {
                //se carga la lista de estrato disponibles
                cargarListaEstratos();
                cargarInformacionCambioEstrato(solicitudDthSeleccionada);
                obtenerValorTipoTecnologia(solicitudDthSeleccionada);
                cargarListadosConfiguracion(solicitudDthSeleccionada);
                obtenerResultadoGestionList(solicitudDthSeleccionada.getTecnologiaId());
                obtenerColorAlerta(solicitudDthSeleccionada, obtenerTipoSolicitud());
                showSolicitud();
                iniciarTiempoGestion();
                asignarTiempoEsperaGestion();
                subscriptores = buscarSubscriptores(cmtDireccionDetalladaMgl);
                obtenerHhppAsociadosList(cmtDireccionDetalladaMgl);
            } else {
                if (!StringUtils.equalsIgnoreCase(solicitudDthSeleccionada.getCambioDir(), Constant.VALIDACION_COBERTURA_12)) {
                    habilitarRR = validarRrHabilitado();
                    obtenerDireccionDetallada(solicitudDthSeleccionada);
                    obtenerDrDireccion(cmtDireccionDetalladaMgl);
                    obtenerHhppAsociadosList(cmtDireccionDetalladaMgl);
                    obtenerFuenteDireccionFormatoRr(cmtDireccionDetalladaMgl);
                    habilitarPanelCambioDireccion();
                    obtenerCiudadDepartamentoByCentroPobladoId(solicitudDthSeleccionada.getCentroPobladoId());
                    obtenerNodosCoberturaGeo(drDireccion, centroPobladoGpo);
                    cargarListadosConfiguracion(solicitudDthSeleccionada);
                    obtenerNombreTipoSolicitud();
                    obtenerResultadoGestionList(solicitudDthSeleccionada.getTecnologiaId());
                    obtenerDireccionFormatoRr(solicitudDthSeleccionada.getCentroPobladoId());
                    obtenerComplementoDireccion();
                    obtenerDireccionAntiguaFormatoIgac();

                    //creación hhpp && cambio dirección
                    if (StringUtils.equalsIgnoreCase(solicitudDthSeleccionada.getCambioDir(), Constant.RR_DIR_CREA_HHPP_0)
                            || solicitudDthSeleccionada.getCambioDir().equals(Constant.RR_DIR_CAMB_HHPP_1)) {
                        obtenerUnidadesAsociadasPredio(drDireccion, solicitudDthSeleccionada.getCentroPobladoId());
                        obtenerUnidadesConflictoList();
                    }
                    //Creación Hhpp
                    if (StringUtils.equalsIgnoreCase(solicitudDthSeleccionada.getCambioDir(), Constant.RR_DIR_CREA_HHPP_0)) {
                        obtenerUnidadesPrediosModificadas();
                    }

                    obtenerColorAlerta(solicitudDthSeleccionada, obtenerTipoSolicitud());
                    obtenerTrackBySolicitud();
                    showSolicitud();
                    iniciarTiempoGestion();
                    asignarTiempoEsperaGestion();
                    cargueParametrosArchivoAdjunto(solicitudDthSeleccionada);
                    establecerCheckAutomaticoATecnologias();

                    deshabilitarNodoCercano = true;
                    //Reactivación
                    if (StringUtils.equalsIgnoreCase(solicitudDthSeleccionada.getCambioDir(), Constant.RR_DIR_REAC_HHPP_3)) {
                        deshabilitarNodoCercano = false;
                        if (solicitudDthSeleccionada.getHhppMgl() == null) {
                            mostrarMensajePopup(FacesMessage.SEVERITY_ERROR, "No se puede realizar "
                                    + "la solicitud debido a un error en la creación de la misma,"
                                    + " intente de nuevo creando la solicitud por favor");
                            return;
                        }
                        obtenerNodoAnteriorReactivacion();
                        //Si el GEO entrega cobertura en la tecnologia de la solicitud de reactivación se agrega al listado de nodo anterior
                        if (addressService != null && solicitudDthSeleccionada != null
                                && solicitudDthSeleccionada.getTecnologiaId() != null
                                && solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp() != null
                                && !solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp().isEmpty()) {
                            String nodoCoberturaTecSeleccionada
                                    = obtenerNodoCoberturaTecnologiaSeleccionada(addressService, solicitudDthSeleccionada);

                            if (nodoCoberturaTecSeleccionada != null && !nodoCoberturaTecSeleccionada.isEmpty()
                                    && nodoAnteriorList != null && !nodoAnteriorList.isEmpty()) {
                                //Si el nodo de la cobertura es el mismo nodo anterior no se agrega
                                int nodoExistente = 0;
                                for (NodoMgl nodoAnterior1 : nodoAnteriorList) {
                                    if (StringUtils.equalsIgnoreCase(nodoAnterior1.getNodCodigo(), nodoCoberturaTecSeleccionada)) {
                                        nodoExistente++;
                                        break;
                                    }
                                }
                                if (nodoExistente == 0) {
                                    NodoMgl nodoCoberturaGeo = new NodoMgl();
                                    nodoCoberturaGeo.setNodNombre(nodoCoberturaTecSeleccionada + " - Nodo GEO");
                                    nodoCoberturaGeo.setNodCodigo(nodoCoberturaTecSeleccionada);
                                    nodoAnteriorList.add(nodoCoberturaGeo);
                                }
                            } else {
                                if (nodoCoberturaTecSeleccionada != null && !nodoCoberturaTecSeleccionada.isEmpty()) {
                                    nodoAnteriorList = new ArrayList<>();
                                    NodoMgl nodoCoberturaGeo = new NodoMgl();
                                    nodoCoberturaGeo.setNodNombre(nodoCoberturaTecSeleccionada + " - Nodo GEO");
                                    nodoCoberturaGeo.setNodCodigo(nodoCoberturaTecSeleccionada);
                                    nodoAnteriorList.add(nodoCoberturaGeo);
                                }
                            }
                        }
                    }
                } else {

                    if (Objects.isNull(solicitudDthSeleccionada.getIdFactibilidad())) {
                        throw new ApplicationException("La Solicitud ya no cuenta con Id de Factibilidad por tal motivo no es posible abrirla para gestión");
                    }

                    listaDetalleFactibilidadMgl = detalleFactibilidadMglFacadeLocal.findListByIdFactTec(solicitudDthSeleccionada);
                    habilitarRR = validarRrHabilitado();
                    obtenerDireccionDetallada(solicitudDthSeleccionada);
                    obtenerDrDireccion(solicitudDthSeleccionada.getDireccionDetallada());
                    obtenerHhppAsociadosList(cmtDireccionDetalladaMgl);
                    habilitarPanelCambioDireccion();
                    obtenerCiudadDepartamentoByCentroPobladoId(solicitudDthSeleccionada.getCentroPobladoId());
                    obtenerNodosCoberturaGeo(drDireccion, centroPobladoGpo);
                    cargarListadosConfiguracion(solicitudDthSeleccionada);
                    obtenerNombreTipoSolicitud();
                    resultGestionDth = parametrosCallesFacade.findByTipo(Constant.RESULTADO_GESTION_VAL_COBER);
                    obtenerDireccionFormatoRr(solicitudDthSeleccionada.getCentroPobladoId());
                    obtenerComplementoDireccion();
                    obtenerDireccionAntiguaFormatoIgac();
                    obtenerFuenteDireccionFormatoRr(cmtDireccionDetalladaMgl);
                    obtenerColorAlerta(solicitudDthSeleccionada, obtenerTipoSolicitud());
                    obtenerTrackBySolicitud();
                    showSolicitud();
                    iniciarTiempoGestion();
                    asignarTiempoEsperaGestion();
                    setShowEtiquetas(false);
                    deshabilitarNodoCercano = true;
                }

            }

            //Ajuste para mostrar el tiempo detenido al visualizar una solicitud finalizada
            if (solicitudDthSeleccionada != null
                    && StringUtils.equalsIgnoreCase(solicitudDthSeleccionada.getEstado(), "FINALIZADO")) {
                solicitudGestionada = true;
                obtenerTrackBySolicitud();
                if (cmtTiempoSolicitudMgl.getTiempoGestion() != null
                        && !cmtTiempoSolicitudMgl.getTiempoGestion().isEmpty()) {
                    cmtTiempoSolicitudMglToCreate.setTiempoGestion(cmtTiempoSolicitudMgl.getTiempoGestion());
                }
            }

            //Creacion de hhpp o cambio de direccion cargue tipo vivienda
            if (StringUtils.equalsIgnoreCase(solicitudDthSeleccionada.getCambioDir(), Constant.RR_DIR_CREA_HHPP_0)
                    || StringUtils.equalsIgnoreCase(solicitudDthSeleccionada.getCambioDir(), Constant.RR_DIR_CAMB_HHPP_1)) {
                listaTipoHhpp = tipoHhppMglFacadeLocal.findAll();
                if (direccionRREntity != null && direccionRREntity.getNumeroApartamento() != null
                        && !direccionRREntity.getNumeroApartamento().isEmpty()) {
                    obtenerReglaTipoVivienda(direccionRREntity.getNumeroApartamento());
                }
            }
            //cambio de direccion, deshabilita tabla de nodos.
            if (StringUtils.equalsIgnoreCase(solicitudDthSeleccionada.getCambioDir(), Constant.RR_DIR_CAMB_HHPP_1)) {
                deshabilitarNodoCercano = false;
            }

            if (solicitudDthSeleccionada.getTipoTecHabilitadaId() != null) {
                tipoTecHabilitadaIdSol = tipoHhppMglFacadeLocal.findTipoHhppMglById(solicitudDthSeleccionada.getTipoTecHabilitadaId());
            }

            // Para cambio dir, cambio estrato, creacion hhpp asignar marcas
            if (StringUtils.equalsIgnoreCase(solicitudDthSeleccionada.getCambioDir(), Constant.RR_DIR_CAMB_HHPP_1)
                    || StringUtils.equalsIgnoreCase(solicitudDthSeleccionada.getCambioDir(), Constant.RR_DIR_CAMBIO_ESTRATO_2)
                    || StringUtils.equalsIgnoreCase(solicitudDthSeleccionada.getCambioDir(), Constant.RR_DIR_CREA_HHPP_0)) {

                marcasAgregadasHhppList = new ArrayList<>();
                marcasHhppList = marcasMglFacadeLocal.findAllMarcasMgl();

                //cargue de marcas que tiene actualmente el hhpp solo para cambiodir y cambioestrato

                if ((StringUtils.equalsIgnoreCase(solicitudDthSeleccionada.getCambioDir(), Constant.RR_DIR_CAMB_HHPP_1)
                        || StringUtils.equalsIgnoreCase(solicitudDthSeleccionada.getCambioDir(), Constant.RR_DIR_CAMBIO_ESTRATO_2))
                        && solicitudDthSeleccionada.getHhppMgl() != null) {
                    etiquetasHhpp = obtenerEtiquetasHhppSeleccionadoList(solicitudDthSeleccionada.getHhppMgl(), hhppList);
                    if (StringUtils.isBlank(etiquetasHhpp)) {
                        etiquetasHhpp = "Sin Etiquetas asociadas";
                    }
                }
                //asigna la lista de tipos de documento del cliente
                generarListaTipoDocumento();

            }
            puedeMostrarCampos(solicitudDthSeleccionada.getCambioDir());

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en el cargue inicial de la gestión de la solicitud: " + e.getMessage(), e, LOGGER);

            try {
                FacesUtil.navegarAPagina("/view/MGL/VT/gestion/DTH/gestionSolicitudDthListado.jsf");
            } catch (Exception ei) {
                FacesUtil.mostrarMensajeError("Error al momento de redireccionar: " + ei.getMessage(), ei, LOGGER);
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en el cargue inicial de la gestión de la solicitud ", e, LOGGER);
            try {
                FacesUtil.navegarAPagina("/view/MGL/VT/gestion/DTH/gestionSolicitudDthListado.jsf");
            } catch (Exception ei) {
                FacesUtil.mostrarMensajeError("Error al momento de redireccionar: " + ei.getMessage(), ei, LOGGER);
            }
        }
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
     * Función que obtiene listado de Etiquetas del Hhpp
     *
     * @param hhppMgl           {@link HhppMgl}
     * @param hhppAsociadosList {@link List<HhppMgl>}
     * @author Juan David Hernandez
     */
    public String obtenerEtiquetasHhppSeleccionadoList(HhppMgl hhppMgl, List<HhppMgl> hhppAsociadosList) throws ApplicationException {
        StringBuilder etiquetasHhppTecnologias = new StringBuilder();
        try {
            if (CollectionUtils.isEmpty(hhppAsociadosList)) {
                if (Objects.nonNull(hhppMgl) && CollectionUtils.isNotEmpty(hhppMgl.getListMarcasHhpp())) {
                    //recorrido que se realiza para obtener todas las marcas en un String separadas por -
                    int cont = 1;
                    etiquetasHhppTecnologias = new StringBuilder();

                    for (MarcasHhppMgl eti : hhppMgl.getListMarcasHhpp()) {
                        etiquetasHhppTecnologias.append(eti.getMarId().getMarNombre());
                        if (cont < hhppMgl.getListMarcasHhpp().size()) {
                            etiquetasHhppTecnologias.append(" - ");
                        }
                        cont++;
                    }
                }
                return etiquetasHhppTecnologias.toString();
            }

                List<MarcasHhppMgl> etiquetas = new ArrayList<>();
                for (HhppMgl hhppMgl1 : hhppAsociadosList) {
                    List<MarcasHhppMgl> etiquetasHhppList;
                    etiquetasHhppList = marcasHhppMglFacadeLocal
                            .findMarcasHhppMglidHhpp(hhppMgl.getHhpId());

                    if (CollectionUtils.isNotEmpty(etiquetasHhppList)) {
                        if (CollectionUtils.isNotEmpty(etiquetas)) {

                            for (MarcasHhppMgl etiqueta : etiquetasHhppList) {
                                List<MarcasHhppMgl> etiquetasCopiaList = copiaMarcasList(etiquetas);

                                if (etiquetasCopiaList != null && !etiquetasCopiaList.isEmpty()) {
                                    int contEtiqueta = 0;
                                    for (MarcasHhppMgl marcasHhppMgl : etiquetasCopiaList) {
                                        if (etiqueta.getMarId().equals(marcasHhppMgl.getMarId())) {
                                            contEtiqueta++;
                                        }
                                    }

                                    if (contEtiqueta == 0) {
                                        etiquetas.add(etiqueta);
                                    }
                                }
                            }

                        } else {
                            etiquetas.addAll(etiquetasHhppList);
                        }

                    }
                }

                //recorrido que se realiza para obtener todas las marcas en un String separadas por -qñ
                if (!etiquetas.isEmpty()) {
                    int cont = 1;
                    etiquetasHhppTecnologias = new StringBuilder();
                    for (MarcasHhppMgl eti : etiquetas) {
                        etiquetasHhppTecnologias.append(eti.getMarId().getMarNombre());
                        if (cont < etiquetas.size()) {
                            etiquetasHhppTecnologias.append(" - ");
                        }
                        cont++;
                    }
                }
            return etiquetasHhppTecnologias.toString();
        } catch (ApplicationException e) {
            LOGGER.error("Error al obtener listado de etiquetas " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error al obtener listado de etiquetas " + e.getMessage(), e);
            throw new ApplicationException("Error al obtener listado de etiquetas. ",e);
        }
    }

    public List<MarcasHhppMgl> copiaMarcasList(List<MarcasHhppMgl> etiquetasList) throws ApplicationException {

        List<MarcasHhppMgl> copiaMarcasList = new ArrayList<>();
        try {
            if (CollectionUtils.isNotEmpty(etiquetasList)) {
                copiaMarcasList.addAll(etiquetasList);
            }

        } catch (RuntimeException e) {
            LOGGER.error("Error al realizar copia del listado de etiquetas", e);
            throw new ApplicationException("Error al realizar copia del listado de etiquetas. ",e);
        } catch (Exception e) {
            LOGGER.error("Error al realizar copia del listado de etiquetas {}",  e.getMessage());
            throw new ApplicationException("Error al realizar copia del listado de etiquetas. ",e);
        }
        return copiaMarcasList;
    }

    /**
     * Función utilizada para mostrar/ocultar panel de etiquetas de hhpp.
     *
     * @author Juan David Hernandez
     */
    public void mostrarOcultarEtiquetas() {
        showEtiquetas = !showEtiquetas;
    }

    public void cargueParametrosArchivoAdjunto(Solicitud solicitud) {
        try {
            archivosVtMgl = cmtArchivosVtMglFacadeLocal.findAllBySolIdHhpp(solicitud);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al realizar la consulta del archivo adjunto: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error al realizar la consulta del archivo adjunto: " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función utilizada para obtener el tipo de solicitud de la solicitud
     *
     *
     * @author Juan David Hernandez
     */
    public void obtenerNombreTipoSolicitud() {
        try {
            if (solicitudDthSeleccionada == null || CollectionUtils.isEmpty(tipoAccionSolicitudBasicaList)) {
                String msgWarn = "El valor de solicitudDthSeleccionada = '{}' o tipoAccionSolicitudBasicaList = '{}' es nulo o vacío.";
                LOGGER.warn(msgWarn, solicitudDthSeleccionada, tipoAccionSolicitudBasicaList);
                return;
            }

            for (CmtBasicaMgl tipoAccion : tipoAccionSolicitudBasicaList) {
                if (solicitudDthSeleccionada.getCambioDir().equalsIgnoreCase(tipoAccion.getCodigoBasica())) {
                    solicitudDthSeleccionada.setTipoAccionSolicitudStr(tipoAccion.getNombreBasica());
                    break;
                }
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se presentó un error estableciendo el tipo de solicitud " + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función utilizada para hacer el redireccionamiento al modelo de hhpp
     *
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Juan David Hernandez}
     */
    public void irModeloHhpp() throws ApplicationException {
        try {
            if (solicitudDthSeleccionada != null && solicitudDthSeleccionada.getHhppMgl() != null) {
                // Instacia Bean de Session para obtener el hhpp seleccionado
                HhppDetalleSessionBean hhppDetalleSessionBean
                        = (HhppDetalleSessionBean) JSFUtil.getBean("hhppDetalleSessionBean");
                BigDecimal subDirId = null;
                if (solicitudDthSeleccionada.getHhppMgl().getSubDireccionObj() != null) {
                    subDirId = solicitudDthSeleccionada.getHhppMgl().getSubDireccionObj().getSdiId();
                }

                List<CmtDireccionDetalladaMgl> direccionDetalladaList = cmtDireccionDetalleMglFacadeLocal
                        .findDireccionDetallaByDirIdSdirId(solicitudDthSeleccionada.getHhppMgl()
                                .getDireccionObj().getDirId(), subDirId);

                if (CollectionUtils.isEmpty(direccionDetalladaList)) {
                    String msn = "No fue posible hallar la direccion "
                            + "detallada del Hhpp para hacer la redirección al modelo de hhpp";
                    mostrarMensajePopup(FacesMessage.SEVERITY_ERROR, msn);
                    return;
                }

                hhppDetalleSessionBean.setHhppSeleccionado(solicitudDthSeleccionada.getHhppMgl());
                hhppDetalleSessionBean.setDireccionDetalladaSeleccionada(direccionDetalladaList.get(0));
                FacesUtil.navegarAPagina("/view/MGL/VT/moduloHhpp/detalleHhpp.jsf");
                return;
            }

            if (CollectionUtils.isNotEmpty(hhppList)) {
                // Instacia Bean de Session para obtener el hhpp seleccionado
                HhppDetalleSessionBean hhppDetalleSessionBean
                        = (HhppDetalleSessionBean) JSFUtil.getBean("hhppDetalleSessionBean");

                BigDecimal subDirId = null;
                if (hhppList.get(0).getSubDireccionObj() != null && hhppList.get(0).getSubDireccionObj().getSdiId() != null) {
                    subDirId = hhppList.get(0).getSubDireccionObj().getSdiId();
                }

                List<CmtDireccionDetalladaMgl> direccionDetalladaList = cmtDireccionDetalleMglFacadeLocal
                        .findDireccionDetallaByDirIdSdirId(hhppList.get(0).getDireccionObj().getDirId(), subDirId);

                if (CollectionUtils.isEmpty(direccionDetalladaList)) {
                    String msn = "No fue posible hallar la direccion "
                            + "detallada del Hhpp para hacer la redirección al modelo de hhpp";
                    mostrarMensajePopup(FacesMessage.SEVERITY_ERROR, msn);
                    return;
                }

                hhppDetalleSessionBean.setHhppSeleccionado(hhppList.get(0));
                hhppDetalleSessionBean.setDireccionDetalladaSeleccionada(direccionDetalladaList.get(0));
                FacesUtil.navegarAPagina("/view/MGL/VT/moduloHhpp/detalleHhpp.jsf");
            }
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en ir Modelo Hhpp" + ex.getMessage(), ex, LOGGER);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en ir Modelo Hhpp" + ex.getMessage(), ex, LOGGER);
        }
    }

    /**
     * Genera el mensaje a mostrar en el popup de la aplicación.
     *
     * @param severityError {@link FacesMessage.Severity}
     * @param msn           {@link String}
     * @author Gildardo Mora
     */
    private void mostrarMensajePopup(FacesMessage.Severity severityError, String msn) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(severityError, msn, ""));
    }

    /**
     * Función que realiza la gestión de la solicitud
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void gestionarSolicitud() throws ApplicationException {
        try {
            if (!requisitosValidosGestionarSolicitud()) {
                LOGGER.debug("No se cumplen los requisitos para gestionar la solicitud.");
                return;
            }

                //Se asignan los tiempos de duración de la solicitud.
                asignarTiemposGestion();
                copiaUnidadesModificadas();
                copiaUnidadesConflicto();
                asignarMarcasAgregadasHhpp(marcasAgregadasHhppList);
                solicitudDthSeleccionada.setTipoHhpp(tipoViviendaSeleccionada);
                solicitudDthSeleccionada.setIdUsuarioGestion(new BigDecimal(usuarioLogin.getCedula()));
                solicitudDthSeleccionada.setUsuarioGestionador(usuarioLogin.getNombre() != null
                            ? usuarioLogin.getNombre() : USUARIO_NO_VALIDO);
                solicitudDthSeleccionada.setIdUsuarioGestion(new BigDecimal(usuarioLogin.getCedula()));

                if(solicitudDthSeleccionada.getRespuesta() != null){
                    solicitudDthSeleccionada.setRespuesta(solicitudDthSeleccionada.getRespuesta().replace(";", " "));
                }

                /*Si es una solicitud de creación se crea una solicitud por cada nodo
                 * de cada tecnologia ingresada */
                if (solicitudDthSeleccionada.getCambioDir().equals(Constant.RR_DIR_CREA_HHPP_0)) {
                    gestionarSolicitudCrearHomePassed();
                    solicitudGestionada = true;
                    return;
                }

                // Si es reactivación de HHPP
                if (solicitudDthSeleccionada.getCambioDir().equals(Constant.RR_DIR_REAC_HHPP_3)) {
                    gestionarSolicitudReactivarHomePassed();
                    solicitudGestionada = true;
                    return;
                }

                // Si es cambio de dirección del HHPP
                if (solicitudDthSeleccionada.getCambioDir().equals(Constant.RR_DIR_CAMB_HHPP_1)) {
                    gestionarSolicitudCambioDirHomePassed();
                    solicitudGestionada = true;
                    return;
                }

                // Si es validación de cobertura.
                if (solicitudDthSeleccionada.getCambioDir().equals(Constant.VALIDACION_COBERTURA_12)) {
                    gestionarSolicitudValidarCobertura();
                    solicitudGestionada = true;
                    return;
                }

                String msn = "La acción seleccionada no se encuentra disponible en el sistema.";
                mostrarMensajePopup(FacesMessage.SEVERITY_ERROR, msn);
                solicitudGestionada = true;
        } catch (ApplicationException e) {
            String msgError = "Error al gestionar la solicitud: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, "GESTIONAR SOLICITUDES");
        } catch (Exception e) {
            String msgError = "Ocurrió un error al gestionar la solicitud: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, "GESTIONAR SOLICITUDES");
        }
    }

    /**
     * Verifica que se cumplan todas las validaciones pertinentes para poder procesar la gestión de la solicitud.
     *
     * @return {@code boolean} Retorna {@code true} cuando cumple todas las condiciones
     * y validaciones para procesar la gestión de solicitudes.
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    private boolean requisitosValidosGestionarSolicitud() throws ApplicationException {
        return validarCambioViviendaAlerta() && validarRespuestaGestion() && validarRespuestaGestionCambioDir()
                && validarRespuestaGestionReactivacion()
                && validarTecnologiaARR()
                && !validarUnidadesConflicto()
                && validarInformacionUnidadesConflicto()
                && validarUnidadesEnConflictoRepetidas()
                && validarConflictosResueltos()
                && validarExistenciaHhpp(tecnologiaBasicaList)
                && validacionTecSincronizaRr_NodoGpon_RedFO(tecnologiaBasicaList);
    }

    /**
     * Realiza la gestión de solicitud de validación de cobertura.
     *
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    private void gestionarSolicitudValidarCobertura() throws ApplicationException {
        //cardenaslb
        solicitudFacadeLocal.gestionSolicitud(solicitudDthSeleccionada,
                drDireccion, detalleDireccionEntity, direccionRREntity,
                null, usuarioVt, perfilVt,
                usuarioLogin.getCedula(),
                unidadModificadaList,
                cmtTiempoSolicitudMglToCreate, unidadConflictoList, false, true, habilitarRR,
                centroPobladoGpo, ciudadGpo, departamentoGpo,null);

        String msn = "Solicitud Número: '"
                + solicitudDthSeleccionada.getIdSolicitud()
                + "' HA SIDO FINALIZADA para la dirección "
                + direccionRREntity.getCalle()
                + " " + direccionRREntity.getNumeroUnidad()
                + " " + direccionRREntity.getNumeroApartamento()
                + " en " + centroPobladoGpo.getGpoNombre();
        mostrarMensajePopup(FacesMessage.SEVERITY_INFO, msn);
    }

    /**
     * Realiza la gestión de solicitud de cambio de dirección de HHPP
     *
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    private void gestionarSolicitudCambioDirHomePassed() throws ApplicationException {
        solicitudFacadeLocal.gestionSolicitud(solicitudDthSeleccionada,
                drDireccion, detalleDireccionEntity, direccionRREntity,
                null, usuarioVt, perfilVt, usuarioLogin.getCedula(),
                unidadModificadaList, cmtTiempoSolicitudMglToCreate, unidadConflictoList,
                false, true, habilitarRR,  centroPobladoGpo,
                ciudadGpo, departamentoGpo,null);

        String msn = "Solicitud Número: '" + solicitudDthSeleccionada.getIdSolicitud()
                + "' HA SIDO FINALIZADA, para la dirección "
                + direccionRREntity.getCalle()
                + " " + direccionRREntity.getNumeroUnidad()
                + " " + direccionRREntity.getNumeroApartamento()
                + " en " + centroPobladoGpo.getGpoNombre();
        mostrarMensajePopup(FacesMessage.SEVERITY_INFO, msn);
    }

    /**
     * Realiza la gestión de solicitud de reactivación de HHPP.
     *
     * @throws ApplicationException Excepción personalizada de la aplicación
     */
    private void gestionarSolicitudReactivarHomePassed() throws ApplicationException {
        //Si es una reactivación cambia el nodo y el que se envia es el nodo anterior seleccionado.
        solicitudFacadeLocal.gestionSolicitud(solicitudDthSeleccionada,
                drDireccion, detalleDireccionEntity, direccionRREntity,
                nodoMglAnterior,
                usuarioVt, perfilVt,
                usuarioLogin.getCedula(),
                unidadModificadaList,
                cmtTiempoSolicitudMglToCreate, unidadConflictoList, false, true, habilitarRR,
                centroPobladoGpo, ciudadGpo, departamentoGpo,null);

        String msn = "Solicitud Número: '"
                + solicitudDthSeleccionada.getIdSolicitud()
                + "' HA SIDO FINALIZADA para la "
                + "dirección "
                + direccionRREntity.getCalle()
                + " " + direccionRREntity.getNumeroUnidad()
                + " " + direccionRREntity.getNumeroApartamento()
                + " en " + centroPobladoGpo.getGpoNombre();
        mostrarMensajePopup(FacesMessage.SEVERITY_INFO, msn);
    }

    /**
     * Realiza la gestión de solicitud de creación de HHPP
     *
     * @throws ApplicationException Excepción personalizada de la aplicación
     */
    private void gestionarSolicitudCrearHomePassed() throws ApplicationException {
        if (solicitudFacadeLocal.validaRptParaCreacionOt(solicitudDthSeleccionada.getRptGestion())) {
            //JAOF
            if (validarExistenciaDeOtRepetida()) {
                solicitudDthSeleccionada.setEstratoAntiguo(drDireccion.getEstrato());
                //Se crea la OT
                otCreada = otHhppMglFacadeLocal.createOtFromSolicitud(
                        solicitudDthSeleccionada, usuarioVt, perfilVt,
                        tecnologiaBasicaList);
                String msn = "Solicitud Número: '"
                        + solicitudDthSeleccionada.getIdSolicitud()
                        + "' HA SIDO FINALIZADA. SE HA CREADO OT NUMERO: "
                        + otCreada.getOtHhppId();
                mostrarMensajePopup(FacesMessage.SEVERITY_INFO, msn);
                OtHhppMglSessionBean otHhppMglSessionBean = JSFUtil.getSessionBean(OtHhppMglSessionBean.class);

                if (Objects.isNull(otHhppMglSessionBean)) {
                    throw new ApplicationException("No se pudo recuperar los datos de OtHhppMglSessionBean");
                }

                otHhppMglSessionBean.setOtHhppMglSeleccionado(otCreada);
                otHhppMglSessionBean.setDetalleOtHhpp(true);
                gestionarOt = true;
            }

            return;
        }

        if (solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_HHPP_CREADO)) {
            actualizarEstratoDireccion(cmtDireccionDetalladaMgl, drDireccion);
            //creacion de hhpp por cada nodo ingresado (multi-tecnologia)
            if (CollectionUtils.isEmpty(tecnologiaBasicaList)) {
                LOGGER.warn("No hay tecnologías para procesar, en la gestión de solicitud de creación de HHPP");
                return;
            }

            for (CmtBasicaMgl tecnSol : tecnologiaBasicaList) {
                procesarTecnologiaCrearHomePassed(tecnSol);
            }

            return;
        }

        //opcion cuando una solicitud de creacion de hhpp se rechaza.
        solicitudFacadeLocal.gestionSolicitud(solicitudDthSeleccionada,
                drDireccion, detalleDireccionEntity, direccionRREntity,
                null, usuarioVt, perfilVt, usuarioLogin.getCedula(),
                null, cmtTiempoSolicitudMglToCreate, unidadConflictoTmpList,
                false, true, habilitarRR, centroPobladoGpo, ciudadGpo, departamentoGpo,null);

        //Mensaje para cuando se crea un Hhpp
        String msn = "Solicitud Número: '"
                + solicitudDthSeleccionada.getIdSolicitud()
                + "' HA SIDO FINALIZADA para la "
                + "dirección "
                + direccionRREntity.getCalle()
                + " " + direccionRREntity.getNumeroUnidad()
                + " " + direccionRREntity.getNumeroApartamento()
                + " en " + centroPobladoGpo.getGpoNombre();
        mostrarMensajePopup(FacesMessage.SEVERITY_INFO, msn);
    }

    /**
     * Procesa la gestión de solicitud de creación de HHPP para la tecnologia recibida.
     *
     * @param tecnSol {@link CmtBasicaMgl}
     * @throws ApplicationException Excepción personalizada de la aplicación
     */
    private void procesarTecnologiaCrearHomePassed(CmtBasicaMgl tecnSol) throws ApplicationException {
        if (StringUtils.isBlank(tecnSol.getNodoGestion())
                || Objects.isNull(tecnSol.getNodoMgl()) || tecnSol.isTecnologiaProcesada()) {
            LOGGER.warn("La tecnología actual no cumple condiciones para creación de HHPP, se continua con las demás de la lista..");
            return;
        }

        //Nodos ingresados
        boolean tecnologiaGestionada;
        tecnologiaGestionada = solicitudFacadeLocal.gestionSolicitud(solicitudDthSeleccionada,
                drDireccion, detalleDireccionEntity, direccionRREntity,
                tecnSol.getNodoMgl(),
                usuarioVt, perfilVt, cedulaUsuarioVt,
                unidadModificadaTemporalList,
                cmtTiempoSolicitudMglToCreate, unidadConflictoTmpList,
                tecnSol.isSincronizaRr(), true,
                habilitarRR, centroPobladoGpo, ciudadGpo, departamentoGpo,tecnSol.getNapCercana());

        if (tecnologiaGestionada) {
            unidadModificadaTemporalList = null;
            unidadConflictoTmpList = null;
        }

       /*Si la solicitud con el nodo se gestiono correctamente
        se registra como true para no volverla a enviar
        a gestionar si alguna otro nodo presentó problemas*/
        tecnSol.setTecnologiaProcesada(tecnologiaGestionada);

        //Mensaje para cuando se crea un Hhpp
        String msn = "Solicitud Número: '"
                + solicitudDthSeleccionada.getIdSolicitud()
                + "' HA SIDO FINALIZADA. SE HA CREADO EL HHPP para la "
                + "dirección "
                + direccionRREntity.getCalle()
                + " " + direccionRREntity.getNumeroUnidad()
                + " " + direccionRREntity.getNumeroApartamento()
                + " en " + centroPobladoGpo.getGpoNombre() + " con tecnología "
                + tecnSol.getNombreBasica();
        mostrarMensajePopup(FacesMessage.SEVERITY_INFO, msn);
    }

    /**
     * Función utilizada para asignar las marcas agregadas al listado al objeto que se va a crear la solicitud.
     *
     * @author Juan David Hernandez
     * @param marcasAgregadasList
     * @return
     */
    public void asignarMarcasAgregadasHhpp(List<MarcasMgl> marcasAgregadasList){
        try {
            //solo se asigna si la solicitud es de creacion, cambio dir, o cambio de estrato
            if(marcasAgregadasList != null && !marcasAgregadasList.isEmpty()
                    && (solicitudDthSeleccionada.getCambioDir().equalsIgnoreCase(Constant.RR_DIR_CREA_HHPP_0)
                    || solicitudDthSeleccionada.getCambioDir().equalsIgnoreCase(Constant.RR_DIR_CAMB_HHPP_1)
                    || solicitudDthSeleccionada.getCambioDir().equalsIgnoreCase(Constant.RR_DIR_CAMBIO_ESTRATO_2))){
                if(solicitudDthSeleccionada != null){
                    solicitudDthSeleccionada.setAgregarMarcasHhppList(marcasAgregadasList);
                }
            }else{
                solicitudDthSeleccionada.setAgregarMarcasHhppList(null);
            }            
        } catch (Exception e) {
         FacesUtil.mostrarMensajeError("Error al establecer  la lista de marcas al objeto solicitud" + e.getMessage() , e, LOGGER); 
        }
    }

    /**
     * Función utilizada para validar que no existan OT pendientes para esta
     * direccion
     *
     * @author Juan David Hernandez
     * @return
     */
    public boolean validarExistenciaDeOtRepetida() {
        try {
            CmtBasicaMgl estadoActivo;
            estadoActivo = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.ESTADO_OT_ABIERTO);
            OtHhppMgl otAlmacenada = otHhppMglFacadeLocal.findOtByDireccionAndSubDireccion(
                    solicitudDthSeleccionada.getDireccionDetallada().getDireccion(),
                    solicitudDthSeleccionada.getDireccionDetallada().getSubDireccion(),
                    estadoActivo);
            if (otAlmacenada != null) {
                String msn = "Ya existe una ot pendiente por gestion para esta direccion";
                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                return false;
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error obteniendo Direccion Detallada" + e.getMessage() , e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error obteniendo Direccion Detallada" + e.getMessage() , e, LOGGER);
            return false;
        }
        return true;
    }

    /**
     * Función utilizada para establecer el valor del color de la alerta
     *
     * @param solicitudesList
     *
     * @author Juan David Hernandez
     * @param cmtTipoSolicitudMgl
     */
    public void obtenerColorAlerta(List<Solicitud> solicitudesList,
            CmtTipoSolicitudMgl cmtTipoSolicitudMgl) {
        try {
            if (cmtTipoSolicitudMgl != null) {
                for (Solicitud solicitud1 : solicitudesList) {
                    solicitud1.setColorAlerta(solicitudFacadeLocal
                            .obtenerColorAlerta(cmtTipoSolicitudMgl,
                                    solicitudDthSeleccionada.getFechaIngreso()));
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al establecer el valor de la alerta ans" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al establecer el valor de la alerta ans" + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función utilizada para establecer el valor del color de la alerta
     */
    public void obtenerColorAlerta(Solicitud solicitud, CmtTipoSolicitudMgl cmtTipoSolicitudMgl) throws ApplicationException {
        if (!Objects.isNull(cmtTipoSolicitudMgl) && !Objects.isNull(solicitud)) {
            solicitud.setColorAlerta(solicitudFacadeLocal.obtenerColorAlerta(cmtTipoSolicitudMgl, solicitudDthSeleccionada.getFechaIngreso()));
        }
    }

    /**
     * Función utilizada actualizar el estrato de la dirección con el estrato
     * seleccionado en la gestión
     *
     * @param direccionDetallada  Dirección detalla de la dirección a actualizar
     *                            el estrato
     * @param estratoSeleccionado Estrato seleccionado para actualizar
     * @author Juan David Hernandez
     */
    public void actualizarEstratoDireccion(CmtDireccionDetalladaMgl direccionDetallada, DrDireccion estratoSeleccionado) {
        try {
            if (estratoSeleccionado == null || estratoSeleccionado.getEstrato().isEmpty()) {
                String msnError = "Es necesario seleccionar un estrato para la direccion";
                mostrarMensajePopup(FacesMessage.SEVERITY_ERROR, msnError);
                return;
            }

            DireccionRRManager rrManager = new DireccionRRManager(false);
            String tipoEdificio = rrManager.obtenerTipoEdificio(direccionRREntity.getNumeroApartamento(),
                    Constant.FUNCIONALIDAD_DIRECCIONES, solicitudDthSeleccionada.getTipoSol());

            if (direccionDetallada == null) {
                LOGGER.debug("La dirección detallada es nula, no se actualiza estrato.");
                return;
            }

            //Estrato
            //se obtiene los registros de direccion y sub direccion para actualizar el estrato de cada uno si es una subdireccion
            if (direccionDetallada.getDireccion() != null && direccionDetallada.getSubDireccion() != null) {
                SubDireccionMgl subDireccion = subDireccionMglFacadeLocal.findById(direccionDetallada.getSubDireccion());
                if (subDireccion != null) {
                    String estrato = obtenerEstrato(estratoSeleccionado.getEstrato(),
                            solicitudDthSeleccionada.getTipoSol(), tipoEdificio);
                    subDireccion.setSdiEstrato(new BigDecimal(estrato));
                    subDireccionMglFacadeLocal.updateSubDireccionMgl(subDireccion);
                }
                return;
            }

            if (direccionDetallada.getDireccion() != null) {
                DireccionMgl direccionMgl = direccionMglFacadeLocal.findById(direccionDetallada.getDireccion());
                if (direccionMgl != null) {
                    String estrato = obtenerEstrato(estratoSeleccionado.getEstrato(),
                            solicitudDthSeleccionada.getTipoSol(), tipoEdificio);
                    direccionMgl.setDirEstrato(new BigDecimal(estrato));
                    direccionMglFacadeLocal.updateDireccionMgl(direccionMgl);
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error al intentar hacer la actualización del estrato de la dirección" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al intentar hacer la actualización del estrato de la dirección" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función utilizada para establecer el valor del color de la alerta
     *
     * @param direccionAntiguaFormatoIgac
     *
     * @author Juan David Hernandez
     * @return
     */
    public List<UnidadStructPcml> obtenerUnidadesAsociadasAntiguas(String direccionAntiguaFormatoIgac) {
        try {
            if (direccionAntiguaFormatoIgac != null && !direccionAntiguaFormatoIgac.isEmpty()) {
                List<UnidadStructPcml> unidadesList = new ArrayList();
                CmtValidadorDireccionesManager validadorDireccionesManager
                        = new CmtValidadorDireccionesManager();

                DrDireccion dirConst = validadorDireccionesManager.convertirDireccionStringADrDireccion(
                        direccionAntiguaFormatoIgac, centroPobladoGpo.getGpoId());

                /*Se valida si la dirección georeferenciada cuenta 
                 * con dirección antigua y la georeferenciamos para 
                 * obtener el drDireccion de la dirección antigua*/
                if (dirConst != null) {
                    dirConst.setCpTipoNivel5(null);
                    dirConst.setCpTipoNivel6(null);
                    dirConst.setCpValorNivel5(null);
                    dirConst.setCpValorNivel6(null);

                    //Obtenemos los hhpp asociados a la dirección antigua
                    List<HhppMgl> hhppDirAntiguaList = cmtDireccionDetalleMglFacadeLocal
                            .findHhppByDireccion(dirConst, centroPobladoGpo.getGpoId(), true,
                                    0, 0, false);

                    //Los hhpp encontrados los pasamos a la estructura de unidades.
                    if (hhppDirAntiguaList != null && !hhppDirAntiguaList.isEmpty()) {

                        int idIncremental = 111;
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

                            unidad.setEstratoUnidad(hhppMgl.getDireccionObj().getDirEstrato() != null
                                    ? hhppMgl.getDireccionObj().getDirEstrato().toString() : "");

                            //conversion de estrato
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

                            unidad.setNodUnidad(hhppMgl.getNodId() != null
                                    ? hhppMgl.getNodId().getNodCodigo() : "");
                            unidad.setTecnologiaHabilitadaId(hhppMgl.getHhpId());
                            unidad.setTecnologiaId(hhppMgl.getNodId() != null ? hhppMgl.getNodId().getNodTipo().getBasicaId() : null);
                            unidad.setNombreTecnologia(hhppMgl.getNodId() != null ? hhppMgl.getNodId().getNodTipo().getNombreBasica() : "");
                            unidad.setConflictApto("0");
                            //hhpp completo
                            unidad.setHhppMgl(hhppMgl);
                            unidadesList.add(unidad);
                        }
                    } else {
                        String msnError = "No se obtuvieron direcciones antiguas ";
                        mostrarMensajePopup(FacesMessage.SEVERITY_ERROR, msnError);
                        return null;
                    }
                } else {
                    String msnError = "Error al obtener el DrDireccion de la dirección "
                            + "antigua al consultar las unidades de la direccion antigua ";
                    mostrarMensajePopup(FacesMessage.SEVERITY_ERROR, msnError);
                    return null;
                }
                return unidadesList;

            } else {

                String msnError = "NO fue posible obtener las unidades de la dirección"
                        + " antigua debido a que no se encuentra relacionada la dirección antigua ";
                mostrarMensajePopup(FacesMessage.SEVERITY_ERROR, msnError);

            }
            return null;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al establecer el valor de la alerta ans" + e.getMessage() , e, LOGGER);
            return null;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al establecer el valor de la alerta ans" + e.getMessage() , e, LOGGER);
            return null;
        }
    }

    /**
     * Función que realizar validación de los datos ingresados al cambio de
     * apartamento en las unidades asociadas al predio
     *
     * @return {@code boolean}
     * @author Juan David Hernandez
     */
    public boolean validarInformacionUnidadesConflicto() {
        try {
            if (CollectionUtils.isEmpty(unidadConflictoList)) {
                return true;
            }

            BiPredicate<String, String> isValidUnidadAndDrDireccion = (unidad, diDireccion) ->
                    StringUtils.isNotBlank(unidad) && StringUtils.isNotBlank(diDireccion)
                            && unidad.equals(diDireccion.trim());

            for (UnidadStructPcml unidadConflicto : unidadConflictoList) {
                if (isValidUnidadAndDrDireccion.test(unidadConflicto.getTipoNivel5(), drDireccion.getCpTipoNivel5())
                        && isValidUnidadAndDrDireccion.test(unidadConflicto.getValorNivel5(), drDireccion.getCpValorNivel5())) {
                    asignarErrorValidator(unidadConflicto, "El Apto solicitado a cambiar no puede ser igual al del HHPP solicitado a crear",
                            "Existen valores no permitidos para las unidades en conflicto. ");
                    return false;
                }

                if (isValidUnidadAndDrDireccion.test(unidadConflicto.getTipoNivel6(), drDireccion.getCpTipoNivel6())
                        && isValidUnidadAndDrDireccion.test(unidadConflicto.getValorNivel6(), drDireccion.getCpValorNivel6())) {
                    asignarErrorValidator(unidadConflicto, "El Apto solicitado a cambiar no puede ser igual al del HHPP solicitado a crear",
                            "Existen valores no permitidos para las unidades en el Predio ");
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            String msn = "Error validando la información de las unidades en conflicto. ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
            return false;
        }
    }

    /**
     * Asigna el mensaje de error a la unidadConflicto y lo muestra al usuario.
     *
     * @param unidadConflicto {@link UnidadStructPcml}
     * @param errorValidator  {@link String
     * @param msn             {@link String}
     * @author Gildardo Mora
     */
    private void asignarErrorValidator(UnidadStructPcml unidadConflicto, String errorValidator, String msn) {
        unidadConflicto.setErrorValidator(errorValidator);
        mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
    }

    /** Función utilizada para validar los nodos ingresados en pantalla
     * para la gestion de la solicitud 
     *
     * @return {@code boolean}
     * @author Juan David Hernandez
     */
    public boolean validarNodosIngresados() {
        try {
            if ((solicitudDthSeleccionada.getCambioDir().equals(Constant.RR_DIR_CREA_HHPP_0)
                    && (solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_HHPP_CREADO)
                    || solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_VERIFICACION_AGENDADA)))) {
                boolean nodoIngresado = false;

                if (tecnologiaBasicaList != null) {
                    NodoMglManager nodoMglManager = new NodoMglManager();
                    for (CmtBasicaMgl cmtBasicaMgl : tecnologiaBasicaList) {
                        if (StringUtils.isNotBlank(cmtBasicaMgl.getNodoGestion())) {
                            NodoMgl nodoMgl = nodoMglManager.findByCodigoNodo(cmtBasicaMgl.getNodoGestion(),
                                    solicitudDthSeleccionada.getCentroPobladoId(), cmtBasicaMgl.getBasicaId());
                            
                            if (nodoMgl == null) {
                                String msn = "El nodo " + cmtBasicaMgl.getNodoGestion()
                                        + " no corresponde a la tecnología ó no corresponde a la ciudad "
                                        + "ó no se encuentra bien digitado. ";
                                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                                return false;
                            }else{
                                String niac = Constant.BASICA_TIPO_ESTADO_NODO_NO_CERTIFICADO;
                                if (nodoMgl.getEstado() != null && nodoMgl.getEstado().getIdentificadorInternoApp() != null
                                        && nodoMgl.getEstado().getIdentificadorInternoApp().equalsIgnoreCase(niac)) {
                                    String msn = "El nodo " + cmtBasicaMgl.getNodoGestion()
                                            + " no se encuentra certificado. Intente con otro por favor";
                                    mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                                    return false;
                                }
                                //Se asigna el nodo que se va enviar a la gestion
                                cmtBasicaMgl.setNodoMgl(nodoMgl);
                            }
                        }
                    }

                    for (CmtBasicaMgl cmtBasicaMgl : tecnologiaBasicaList) {
                        if (cmtBasicaMgl.getNodoGestion() != null
                                && !cmtBasicaMgl.getNodoGestion().isEmpty()) {
                            nodoIngresado = true;
                            return true;
                        }
                    }
                    if (!nodoIngresado) {
                        String msn = "Debe seleccionar al menos 1 nodo de gestión.";
                        mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                        nodoIngresado = false;
                        return false;
                    }
                }
            }

            if (solicitudDthSeleccionada.getCambioDir().equals(Constant.RR_DIR_REAC_HHPP_3)) {
                if (nodoAnterior != null && !nodoAnterior.isEmpty()) {
                    NodoMglManager nodoMglManager = new NodoMglManager();
                    nodoMglAnterior = nodoMglManager.findByCodigoAndCity(nodoAnterior,
                            solicitudDthSeleccionada.getCentroPobladoId());
                    if (nodoMglAnterior != null) {
                        return true;
                    } else {
                        String msn = "No fue posible obtener el nodo anterior de la base de datos.";
                        mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                        return false;
                    }
                } else {
                    String msn = "Debe seleccionar el nodo anterior para la reactivación del Hhpp ";
                    mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                    return false;
                }
            }
                              
            return false;
        } catch (ApplicationException e) {
            String msn = "Error validando unidades con conflicto sin resolver  ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
            return false;
        } catch (Exception e) {
            String msn = "Error validando unidades con conflicto sin resolver  ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
            return false;
        }
    }

    /**
     * Función utilizada para validar si existen conflictos, que estos hayan
     * sido resueltos para poder proceder con la gestión de la solicitud
     *
     * @return {@code boolean}
     * @author Juan David Hernandez
     */
    public boolean validarConflictosResueltos() {
        try {
            boolean result = true;

            if (CollectionUtils.isEmpty(unidadConflictoList)) {
                return true;
            }

            UnidadStructPcml unidadAux = new UnidadStructPcml();
            unidadAux.setNewAparment(null);

            for (UnidadStructPcml unidadStructPcml : unidadConflictoList) {
                if (unidadAux.getNewAparment() == null) {
                    if (unidadStructPcml.getNewAparment() == null) {
                        unidadStructPcml.setNewAparment("");
                    }

                    unidadAux.setNewAparment(unidadStructPcml.getNewAparment());
                } else {
                    if (unidadAux.getNewAparment().equals(unidadStructPcml.getNewAparment())) {
                        String msn = "Existen conflictos sin resolver."
                                + " Por favor verifique. ";
                        mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                        return false;
                    }
                    unidadAux.setNewAparment(null);
                }
            }
            return result;
        } catch (Exception e) {
            String msn = "Error validando unidades con conflicto sin resolver  ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            return false;
        }
    }

    /**
     * Función utilizada para validar si existen valores repetidos en el listado
     * de unidades debido a que no se debe estar ingresando una unidad ya
     * existente
     *
     * @return {@code boolean}
     * @author Juan David Hernandez
     */
    public boolean validarUnidadesEnConflictoRepetidas() {
        try {
            boolean result = true;

            if (CollectionUtils.isEmpty(unidadConflictoList)) {
                return result;
            }

            List<UnidadStructPcml> unidadesConflictoCopiaList = new ArrayList<>();

                //Se realiza copia con la que se realizara la comparación.
                for (UnidadStructPcml unidadAux : unidadConflictoList) {
                    UnidadStructPcml unidadStructPcml = new UnidadStructPcml();
                    unidadStructPcml.setIdUnidad(unidadAux.getIdUnidad());
                    unidadStructPcml.setAptoUnidad(unidadAux.getAptoUnidad());
                    unidadStructPcml.setTipoNivel5(unidadAux.getTipoNivel5()
                            == null ? "" : unidadAux.getTipoNivel5());
                    unidadStructPcml.setValorNivel5(unidadAux.getValorNivel5()
                            == null ? "" : unidadAux.getValorNivel5());
                    unidadStructPcml.setTipoNivel6(unidadAux.getTipoNivel6()
                            == null ? "" : unidadAux.getTipoNivel6());
                    unidadStructPcml.setValorNivel6(unidadAux.getValorNivel6()
                            == null ? "" : unidadAux.getValorNivel6());
                    unidadesConflictoCopiaList.add(unidadStructPcml);
                }

                if (unidadesConflictoCopiaList.isEmpty()) {
                    return result;
                }

                    for (UnidadStructPcml unidadStructPcml : unidadesConflictoCopiaList) {
                        for (UnidadStructPcml unidad : unidadConflictoList) {
                            if (StringUtils.isNotBlank(unidad.getTipoNivel5())) {
                                DetalleDireccionEntity direccion1 = new DetalleDireccionEntity();
                                direccion1.setCptiponivel5(unidad.getTipoNivel5());

                                if (StringUtils.isNotBlank(unidad.getValorNivel5())) {
                                    direccion1.setCpvalornivel5(unidad.getValorNivel5().trim());
                                }

                                if (unidad.getTipoNivel5().contains("+")
                                        && StringUtils.isNotBlank(unidad.getTipoNivel6())
                                        && StringUtils.isNotBlank(unidad.getValorNivel6())) {
                                    direccion1.setCptiponivel6(unidad.getTipoNivel6());
                                    direccion1.setCpvalornivel6(unidad.getValorNivel6().trim());
                                }

                                String newApto = direccionRRFacadeLocal.generarNumAptoRR(direccion1);
                                if (unidadStructPcml.getAptoUnidad().equalsIgnoreCase(newApto)) {
                                    String msn = "Existen unidades con conflicto "
                                            + " repetidas. Verifique por favor.";
                                    mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                                    return false;
                                }
                                // Nivel5 && Nivel6
                                //validacion datos vacios
                                if (StringUtils.isNotBlank(unidad.getTipoNivel5())
                                        && StringUtils.isNotBlank(unidad.getValorNivel5())
                                        && StringUtils.isNotBlank(unidad.getTipoNivel6())
                                        && StringUtils.isNotBlank(unidad.getValorNivel6())
                                        //Validacion segunda unidad
                                        && StringUtils.isNotBlank(unidadStructPcml.getTipoNivel5())
                                        && StringUtils.isNotBlank(unidadStructPcml.getValorNivel5())
                                        && StringUtils.isNotBlank(unidadStructPcml.getTipoNivel6())
                                        && StringUtils.isNotBlank(unidadStructPcml.getValorNivel6())
                                        // Condición
                                        && !unidad.getIdUnidad()
                                                .equals(unidadStructPcml.getIdUnidad())
                                        && unidad.getTipoNivel5()
                                                .equals(unidadStructPcml.getTipoNivel5())
                                        && unidad.getValorNivel5()
                                                .equals(unidadStructPcml.getValorNivel5())
                                        && unidad.getTipoNivel6()
                                                .equals(unidadStructPcml.getTipoNivel6())
                                        && unidad.getValorNivel6()
                                                .equals(unidadStructPcml.getValorNivel6())) {
                                    String msn = "Existen unidades con conflicto "
                                            + " repetidas en los valores ingresados. "
                                            + " Verifique por favor.";
                                    mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                                    return false;
                                }

                                // Nivel5 && NULL
                                if (unidad.getTipoNivel5() != null
                                        && !unidad.getTipoNivel5().trim()
                                                .isEmpty()
                                        && unidad.getValorNivel5() != null
                                        && !unidad.getValorNivel5().trim()
                                                .isEmpty()
                                        && (unidad.getTipoNivel6() == null
                                        || unidad.getTipoNivel6().isEmpty())
                                        && (unidad.getValorNivel6() == null
                                        || unidad.getValorNivel6().trim()
                                                .isEmpty())
                                        && unidadStructPcml.getTipoNivel5()
                                        != null
                                        && !unidadStructPcml.getTipoNivel5()
                                                .trim().isEmpty()
                                        && unidadStructPcml.getValorNivel5()
                                        != null
                                        && !unidadStructPcml.getValorNivel5()
                                                .trim().isEmpty()
                                        && (unidadStructPcml.getTipoNivel6()
                                        == null
                                        || unidadStructPcml.getTipoNivel6()
                                                .isEmpty())
                                        && (unidadStructPcml.getValorNivel6()
                                        == null
                                        || unidadStructPcml.getValorNivel6()
                                                .trim().isEmpty())
                                        //Condicion
                                        && !unidad.getIdUnidad()
                                                .equals(unidadStructPcml.getIdUnidad())
                                        && unidad.getTipoNivel5()
                                                .equals(unidadStructPcml.getTipoNivel5())
                                        && unidad.getValorNivel5()
                                                .equals(unidadStructPcml.getValorNivel5())) {
                                    String msn = "Existen unidades con "
                                            + "conflicto "
                                            + " repetidas en los valores "
                                            + "ingresados. "
                                            + " Verifique por favor.";
                                    mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                                    return false;
                                }

                                // Nivel5 Null && NULL
                                if (unidad.getTipoNivel5() != null
                                        && !unidad.getTipoNivel5()
                                                .trim().isEmpty()
                                        && (unidad.getValorNivel5() == null
                                        || unidad.getValorNivel5()
                                                .trim().isEmpty())
                                        && unidadStructPcml.getTipoNivel5()
                                        != null
                                        && !unidadStructPcml.getTipoNivel5()
                                                .trim().isEmpty()
                                        && (unidadStructPcml.getValorNivel5()
                                        == null
                                        || unidadStructPcml.getValorNivel5()
                                                .trim().isEmpty())
                                        //Condicion
                                        && !unidad.getIdUnidad()
                                                .equals(unidadStructPcml.getIdUnidad())
                                        && unidad.getTipoNivel5()
                                                .equals(unidadStructPcml.getTipoNivel5())
                                        && unidad.getValorNivel5()
                                                .equals(unidadStructPcml.getValorNivel5())) {
                                    String msn = "Existen unidades con "
                                            + "conflicto "
                                            + " repetidas en los valores ingresados. "
                                            + " Verifique por favor.";
                                    mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                                    return false;
                                }

                                //Validación sobre unidades asociadas al predio
                                if (unidadAuxiliarList != null
                                        && !unidadAuxiliarList.isEmpty()) {
                                    for (UnidadStructPcml unidadAsociada
                                            : unidadAuxiliarList) {
                                        if (unidadAsociada.getAptoUnidad()
                                                .equals(newApto)) {
                                            String msn = "Existen unidades"
                                                    + " con conflicto "
                                                    + " repetidas a las unidades"
                                                    + " asociadas al predio. "
                                                    + " Verifique por favor.";
                                            mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                                            return false;
                                        }
                                    }
                                }

                                /*Validación sobre unidades a modificar
                                 * y que se ha seleccionado la aprobación del 
                                 * cambio*/
                                if (unidadModificadaList != null
                                        && !unidadModificadaList.isEmpty()) {
                                    for (UnidadStructPcml unidadModificada
                                            : unidadModificadaList) {
                                        if (unidadModificada.getNewAparment()
                                                != null
                                                && !unidadModificada
                                                        .getNewAparment().isEmpty()
                                                && unidadModificada.isSelected()) {
                                            if (unidadModificada.getNewAparment()
                                                    .equals(newApto)) {
                                                String msn = "Existen"
                                                        + " unidades con "
                                                        + "conflicto "
                                                        + " repetidas a las "
                                                        + "unidades a modificar"
                                                        + " al predio. "
                                                        + " Verifique por favor.";
                                                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                                                return false;
                                            }
                                        }
                                    }
                                }

                                /*Validación de unidades vs la hhpp que se
                                 esta creando */
                                if (direccionRREntity.getNumeroApartamento()
                                        .equals(newApto)) {
                                    String msn = "Existen unidades en "
                                            + "conflicto con el mismo valor "
                                            + "del hhpp que se desea crear. "
                                            + "Por favor verifique.";
                                    mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                                    return false;
                                }
                                unidad.setNewAparment(newApto);
                            } else {
                                unidad.setNewAparment("");
                            }

                        }
                    }
            return result;
        } catch (ApplicationException e) {
            String msn = "Error validando unidades con conflicto repetidas  ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
            return false;
        } catch (Exception e) {
            String msn = "Error validando unidades con conflicto repetidas  ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
            return false;
        }
    }

    /**
     * Función que realizar validación de los datos ingresados a las unidades en
     * conflicto que los datos ingresados se encuentren correctamente construid
     *
     * @author Juan David Hernandez
     * @return 
     */
    public boolean validarUnidadesConflicto() {
        boolean errorEnUnidades = false;

        if (CollectionUtils.isEmpty(unidadConflictoList)) {
            return errorEnUnidades;
        }

            for (UnidadStructPcml u : unidadConflictoList) {
                u.setErrorValidator("");
                //Validaciones para el nivel 5
                if (u.getTipoNivel5() != null) {
                    if (!u.getTipoNivel5().trim().equalsIgnoreCase("CASA")
                            && !u.getTipoNivel5().trim()
                                    .equalsIgnoreCase("FUENTE")
                            && !u.getTipoNivel5().trim()
                                    .equalsIgnoreCase("ADMINISTRACION")) {
                        if (u.getValorNivel5().trim().isEmpty()) {
                            asignarErrorValidator(u, "El valor para el campo "
                                    + u.getTipoNivel5() + " es obligatorio", "Existen valores no permitidos para"
                                    + " las unidades en el Predio ");
                            return errorEnUnidades = true;
                        }
                    }

                    if (u.getTipoNivel5().trim().equalsIgnoreCase("CASA")
                            && (u.getValorNivel5() != null
                            && !u.getValorNivel5().isEmpty())
                            && solicitudDthSeleccionada.getTipo()
                                    .equalsIgnoreCase("BID")) {
                        asignarErrorValidator(u, "El valor CASA para tecnología"
                                + " BI no puede llevar complemento", "Existen valores no permitidos para"
                                + " las unidades en el Predio ");
                        return errorEnUnidades = true;
                    }
                    if (u.getTipoNivel5().contains("+")) {
                        if (u.getTipoNivel6() == null
                                || u.getTipoNivel6().trim().isEmpty()) {
                            asignarErrorValidator(u, "Debe seleccionar valor en la "
                                    + "segunda lista ", "Existen valores no permitidos"
                                    + " para las unidades en "
                                    + "el Predio ");
                            return errorEnUnidades = true;
                        }

                    } else if (u.getTipoNivel6() != null
                            && !u.getTipoNivel6().trim().isEmpty()) {
                        asignarErrorValidator(u, "No Debe seleccionar valor en la "
                                + "segunda lista ", "Existen valores no permitidos "
                                + "para las unidades en "
                                + "el Predio ");
                        return errorEnUnidades = true;
                    }
                    if (u.getTipoNivel5().contains("PI") && !u.getTipoNivel5().contains("CASA")) {
                        if (!u.getValorNivel5().equalsIgnoreCase("1")
                                && !u.getValorNivel5().equalsIgnoreCase("2")
                                && !u.getValorNivel5().equalsIgnoreCase("3")
                                && tipoTecnologiaBasica.getIdentificadorInternoApp() != null 
                                && tipoTecnologiaBasica.getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_BID)) {
                            asignarErrorValidator(u, "El valor permitido en "
                                    + "Piso es 1, 2 o 3. ", "Existen valores no permitidos para"
                                    + " las unidades en "
                                    + "el Predio ");
                            return errorEnUnidades = true;
                        }
                    }
                }
                //Validaciones para el nivel 6
                if (u.getTipoNivel6() != null) {
                    if (u.getTipoNivel5() == null
                            || !u.getTipoNivel5().contains("+")) {
                        asignarErrorValidator(u, "Seleccione valor para la primera "
                                + "lista", "Existen valores no permitidos para las "
                                + "unidades en "
                                + "el Predio ");
                        return errorEnUnidades = true;
                    }
                    if (u.getValorNivel6() == null
                            || u.getValorNivel6().trim().isEmpty()) {
                        asignarErrorValidator(u, "Valor no permitido el campo de la "
                                + "segunda lista", "Existen valores no permitidos para"
                                + " las unidades en "
                                + "el Predio ");
                        return errorEnUnidades = true;
                    }
                }
                //Validaciones para el campo del nivel 5
                if (u.getValorNivel5() != null
                        && !u.getValorNivel5().trim().isEmpty()) {
                    if (u.getTipoNivel5() == null
                            || u.getTipoNivel5().trim().isEmpty()) {
                        asignarErrorValidator(u, "Seleccione valor para la "
                                + "primera lista", "Existen valores no permitidos"
                                + " para las unidades en el Predio ");
                        return errorEnUnidades = true;
                    }
                }
                //Validaciones para el campo del nivel 6
                if (StringUtils.isNotBlank(u.getValorNivel6()) && StringUtils.isBlank(u.getTipoNivel6())) {
                    asignarErrorValidator(u, "Seleccione valor para la "
                            + "segunda lista", "Existen valores no permitidos "
                            + "para las unidades en el Predio ");
                    return errorEnUnidades = true;
                }
            }

        return errorEnUnidades;
    }

    /**
     * Función que valida si la solicitud tiene una dirección antigua para
     * obtener las unidades de la antigua dirección
     *
     * @author Juan David Hernandez
     */
    public void obtenerDireccionAntiguaFormatoIgac() {
        try {
            dirAntiguaFormatoIgac = "";
            if (!Objects.isNull(solicitudDthSeleccionada) && !Objects.isNull(solicitudDthSeleccionada.getDireccionAntiguaIgac())
                    && !solicitudDthSeleccionada.getDireccionAntiguaIgac().isEmpty()) {

                String[] direccionAntigua = solicitudDthSeleccionada
                        .getDireccionAntiguaIgac().trim().split("&");
                if (direccionAntigua.length > 0){
                    String calleAntigua = direccionAntigua[0];
                    String casaAntigua = direccionAntigua[1];
                    dirAntiguaFormatoIgac = calleAntigua + " " + casaAntigua;
                }
            }
        } catch (Exception e) {
            String msn = "Error al obtener dirección antigua formato igac. ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función obtiene el nodo anterior de la solicitud desde la tabla de
     * auditoria de la entidad
     *
     * @author Juan David Hernandez
     */
    public void obtenerNodoAnteriorReactivacion() {
        try {
            HhppMglManager hhppMglManager = new HhppMglManager();
            HhppMgl hhppRepo = null;
            if (solicitudDthSeleccionada.getHhppMgl() != null
                    && solicitudDthSeleccionada.getHhppMgl().getHhpId() != null) {
                hhppRepo  = hhppMglManager.findById(solicitudDthSeleccionada.getHhppMgl().getHhpId());
            }

            if (hhppRepo != null && hhppRepo.getHhpId() != null
                    && hhppRepo.getNodId().getNodCodigo() != null
                    && !hhppRepo.getNodId().getNodCodigo().isEmpty()) {
                nodoAnteriorList = new ArrayList<>();
                NodoMgl nodoAnterior = new NodoMgl();
                nodoAnterior.setNodNombre(hhppRepo
                        .getNodId().getNodCodigo() + " - Nodo Anterior");
                nodoAnterior.setNodCodigo(hhppRepo
                        .getNodId().getNodCodigo());
                nodoAnteriorList.add(nodoAnterior);
            }
        } catch (ApplicationException e) {
            String msn = "Error al obtener nodo anterior  ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al obtener nodo anterior  ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que carga la dirección que se desea cambiar
     *
     * @author Juan David Hernandez
     */
    public void habilitarPanelCambioDireccion() {
        try {
            if (Objects.equals(solicitudDthSeleccionada.getCambioDir(),Constant.RR_DIR_CAMB_HHPP_1)) {
                cambioDireccionPanel = true;
                deshabilitarNodoCercano = false;
            }
        } catch (Exception e) {
            String msn = "Error al habilitar panel de cambio de dirección ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que realiza validación de los datos de la gestión de la solicitud
     *
     * @return {@code boolean}
     * @author Juan David Hernandez
     */
    public boolean validarRespuestaGestion() {
        try {
            if (StringUtils.isBlank(tipoViviendaSeleccionada)
                    && (solicitudDthSeleccionada.getCambioDir().equals(Constant.RR_DIR_CREA_HHPP_0)
                    || solicitudDthSeleccionada.getCambioDir().equals(Constant.RR_DIR_CAMB_HHPP_1))) {
                String msn = "Seleccione un tipo de vivienda por favor.";
                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                return false;
            }
            
            if (solicitudDthSeleccionada.getRptGestion() == null
                    || solicitudDthSeleccionada.getRptGestion().isEmpty()) {
                String msn = "Debe seleccionar un resultado de la gestión "
                        + "para la solicitud.";
                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);

                return false;
            }

            if (solicitudDthSeleccionada.getRespuesta() == null
                    || solicitudDthSeleccionada.getRespuesta().isEmpty()) {
                String msn = "Ingrese una respuesta actual por favor.";
                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                return false;
            }

            if ((solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_HHPP_CREADO)
                    || solicitudDthSeleccionada.getRptGestion()
                    .equalsIgnoreCase(Constant.RZ_REACTIVACION_HHPP_REALIZADO)
                    || solicitudDthSeleccionada.getRptGestion()
                    .equalsIgnoreCase(Constant.RZ_VERIFICACION_AGENDADA))
                    && (solicitudDthSeleccionada.getCambioDir().equals(Constant.RR_DIR_CREA_HHPP_0)
                    || solicitudDthSeleccionada.getCambioDir().equals(Constant.RR_DIR_REAC_HHPP_3))) {
                return validarNodosIngresados();
            }
            return true;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validarRespuesta Gestion" + e.getMessage() , e, LOGGER);
            return false;
        }
    }


    /**
     * Función que realiza validación de que el usuario haya verificado los cambios existentes
     *
     * @return {@code boolean}
     * @author Juan David Hernandez
     */
    public boolean validarCambioViviendaAlerta() {
        try {
            //Si existen cambios a unidades sin verificar 
            if (CollectionUtils.isEmpty(unidadModificadaList)) {
                return true;
            }

            int aprobadas = 0;
            for (UnidadStructPcml unidadStructPcml : unidadModificadaList) {
                //Si alguna unidad de las de cambio no esta seleccionada
                if (!unidadStructPcml.isSelected()) {
                    aprobadas++;
                }
            }

            if (aprobadas == 0) {
                return true;
            }

            // si alguna no esta seleccionada muestra la alerta 1 vez
            if (!alertaMostradaCambioAptos) {
                String msn = "Existen unidades en el Predio a modificar sin "
                        + "ser aprobadas, Si se continua la gestión dichas modificaciones no serán aplicadas.";
                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                //se cambia el valor de la alerta para que no la vuelva a mostrar.
                alertaMostradaCambioAptos = true;
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validarCambioViviendaAlerta" + e.getMessage(), e, LOGGER);
            return true;
        }
    }

    /**
     * Función que realiza validación de los datos de la gestión de la solicitud
     * para cambio de dirección.
     *
     * @author Juan David Hernandez
     * @return 
     */
    public boolean validarRespuestaGestionCambioDir() {
        try {
            if (solicitudDthSeleccionada.getCambioDir().equalsIgnoreCase(Constant.RR_DIR_CAMB_HHPP_1)) {
                if (solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_CAMBIO_DE_DIRECCION_REALIZADO)
                        || solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_CAMBIO_DE_DIRECCION_NOREALIZADO)) {
                    return true;
                } else {
                    String msn = "Debe seleccionar como respuesta de gestión 'CAMBIO DE DIRECCIÓN REALIZADO' "
                            + "o 'CAMBIO DE DIRECCIÓN NO REALIZADO' para gestionar la solicitud.";
                    mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validar Respuesta Gestion Cambio Dir" + e.getMessage() , e, LOGGER);
            return false;
        }
    }
    
     /**
     * Función que realiza validación de los datos de la gestión de la solicitud
     * para reactivacion de hhpp.
     *
     * @author Juan David Hernandez
     * @return 
     */
    public boolean validarRespuestaGestionReactivacion() {
        try {
            if (solicitudDthSeleccionada.getCambioDir().equalsIgnoreCase(Constant.RR_DIR_REAC_HHPP_3)) {
                if (solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_REACTIVACION_HHPP_REALIZADO)
                        || solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_REACTIVACION_HHPP_NO_REALIZADO)) {
                   //Si es una reactivación se envia el nodo vacio para evitar errores de null
                    if(solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_REACTIVACION_HHPP_NO_REALIZADO)){
                        nodoAnterior = "";
                    }
                    return true;
                } else {
                    String msn = "Debe seleccionar como respuesta de gestión 'REACTIVACION HHPP REALIZADO' "
                            + "o 'REACTIVACION HHPP NO REALIZADO' para gestionar la solicitud.";
                    mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validar Respuesta Gestion de reactivacion" + e.getMessage() , e, LOGGER);
            return false;
        }
    }

    /**
     * Función que obtiene el complemento de la dirección
     *
     * @author Juan David Hernandez
     */
    public void obtenerComplementoDireccion() {
        try {
            complementoDireccion = getComplementoDireccion(drDireccion);
        } catch (Exception e) {
            String msn = "Error al obtener el complemento de la dirección ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que obtiene valor del tipo de tecnologia
     *
     * @author Juan David Hernandez
     */
    public void obtenerValorTipoTecnologia(Solicitud solicitudDth) {
        try {
            tipoTecnologiaBasica
                    = cmtBasicaMglFacadeLocal.findValorTipoSolicitud(solicitudDth.getTipo());
            if (tipoTecnologiaBasica != null && tipoTecnologiaBasica.getAbreviatura() != null) {
                solicitudDthSeleccionada.setTipo(tipoTecnologiaBasica.getAbreviatura());
            }
        } catch (Exception e) {
            String msn = "Error al obtener valor del tipo de tecnologia ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Retorna el complemento de la direccion, o direccion intraducible.
     *
     * @param drDirec
     * @return
     */
    public String getComplementoDireccion(DrDireccion drDirec) {
        Objects.requireNonNull(drDirec, "Los datos de dirección son nulos, no se puede obtener complemento.");
        String dirResult = "";

        if (StringUtils.isNotBlank(drDirec.getCpTipoNivel1())
                && StringUtils.isNotBlank(drDirec.getCpValorNivel1())) {
            dirResult += drDirec.getCpTipoNivel1()
                    + " " + drDirec.getCpValorNivel1() + " ";
        }

        if (StringUtils.isNotBlank(drDirec.getCpTipoNivel2())
                && StringUtils.isNotBlank(drDirec.getCpValorNivel2())) {
            dirResult += drDirec.getCpTipoNivel2()
                    + " " + drDirec.getCpValorNivel2() + " ";
        }

        if (drDirec.getCpTipoNivel3() != null
                && !drDirec.getCpTipoNivel3().isEmpty()
                && drDirec.getCpValorNivel3()
                != null && !drDirec.getCpValorNivel3().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel3()
                    + " " + drDirec.getCpValorNivel3() + " ";
        }

        if (drDirec.getCpTipoNivel4() != null
                && !drDirec.getCpTipoNivel4().isEmpty()
                && drDirec.getCpValorNivel4()
                != null && !drDirec.getCpValorNivel4().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel4()
                    + " " + drDirec.getCpValorNivel4() + " ";
        }

        if (StringUtils.isNotBlank(drDirec.getCpTipoNivel5())
                && StringUtils.isNotBlank(drDirec.getCpValorNivel5())) {

            if (drDirec.getCpTipoNivel5().equalsIgnoreCase(Constant.OPT_CASA_PISO)) {
                dirResult += "CASA" + " " + drDirec.getCpValorNivel5() + " ";
            } else if (drDirec.getCpTipoNivel5().equalsIgnoreCase(Constant.OPT_PISO_INTERIOR)
                    || drDirec.getCpTipoNivel5().equalsIgnoreCase(Constant.OPT_PISO_LOCAL)
                    || drDirec.getCpTipoNivel5() .equalsIgnoreCase(Constant.OPT_PISO_APTO)) {

                dirResult += "PISO" + " " + drDirec.getCpValorNivel5() + " ";
            } else {
                dirResult += drDirec.getCpTipoNivel5()
                        + " " + drDirec.getCpValorNivel5() + " ";
            }

        }
        // @author Juan David Hernandez 
        if (StringUtils.isNotBlank(drDirec.getCpTipoNivel5())
                && StringUtils.isBlank(drDirec.getCpValorNivel5())) {
            dirResult += "CASA";
        }

        if (drDirec.getBarrioTxtBM() != null
                && !drDirec.getBarrioTxtBM().isEmpty()) {
            dirResult += "Barrio" + " " + drDirec.getBarrioTxtBM();
        }

        if (drDirec.getCpTipoNivel6() != null
                && !drDirec.getCpTipoNivel6().isEmpty()
                && drDirec.getCpValorNivel6() != null
                && !drDirec.getCpValorNivel6().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel6() + " "
                    + drDirec.getCpValorNivel6() + " ";
        }
        return dirResult;
    }

    /**
     * Función que realiza el cargue de los listados de configuración necesarios
     * en la pantalla.
     *
     * @author Juan David Hernandez
     */
    public void cargarListadosConfiguracion(Solicitud solicitudDth) throws ApplicationException {
        try {
            obtenerTipoTecnologiaList(solicitudDth);
            obtenerTipoNotasList();
            obtenerTipoAccionSolicitudList();
            obtenerListadoTipoSolicitudList();
            obtenerEstratoList();
            obtenerTipoDocSoporte();
        } catch (Exception e) {
            LOGGER.error("cargarListadosConfiguracion() -> " + e.getMessage());
            throw new ApplicationException("Error al hacer el cargue de los listados de configuración");
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
               GeograficoPoliticoMgl centroPobladoSolicitud = geograficoPoliticoMglFacadeLocal
                       .findById(centroPobladoId);

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
     * Función que obtiene listado de respuesta de la gestión de la solicitud
     *
     * @author Juan David Hernandez
     * @param tecnologiaBasicaId
     */
    public void obtenerResultadoGestionList(CmtBasicaMgl tecnologiaBasicaId) {
        try {
            if (tecnologiaBasicaId == null) {
                resultGestionDth = parametrosCallesFacade
                        .findByTipo(Constant.RESULTADO_GESTION_HHPP_UNI);
            } else {
                CmtBasicaMgl tecnologiaBasica = cmtBasicaMglFacadeLocal.findById(tecnologiaBasicaId);

                if (!Objects.isNull(tecnologiaBasica) && StringUtils.equalsIgnoreCase(Constant.HFC_BID, tecnologiaBasica.getIdentificadorInternoApp())) {
                    resultGestionDth = parametrosCallesFacade.findByTipo(Constant.RESULTADO_GESTION_VC);
                } else {
                    resultGestionDth = parametrosCallesFacade
                            .findByTipo(Constant.RESULTADO_GESTION_HHPP_UNI);
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al obtener listado de respuesta" + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que obtiene nodos de cobertura y dirección en formato Rr
     *
     * @author Juan David Hernandez
     * @param direccionDetallada
     */
    public void obtenerFuenteDireccionFormatoRr(CmtDireccionDetalladaMgl direccionDetallada) {
        try {
            if (!Objects.isNull(direccionDetallada) && !Objects.isNull(direccionDetallada.getIdDirCatastro())) {
                if (direccionDetallada.getIdDirCatastro()
                        .substring(0, 1)
                        .equalsIgnoreCase("s")) {
                    String idSubdireccion
                            = direccionDetallada.getIdDirCatastro().substring(1);
                    //obtener subdireccion apartir del idDirCatastro
                    SubDireccionMgl subDireccionMgl = new SubDireccionMgl();
                    subDireccionMgl.setSdiId(new BigDecimal(idSubdireccion));
                    subDireccionMglActual
                            = subDireccionMglFacadeLocal.findById(subDireccionMgl);

                    if (!Objects.isNull(subDireccionMglActual)) {
                        drDireccion.setEstrato(String.valueOf(subDireccionMglActual.getSdiEstrato()));

                        //obtener direccion apartir de la subdireccion.
                        DireccionMgl direccionMgl = new DireccionMgl();
                        direccionMgl.setDirId(subDireccionMglActual.getDirId());
                        direccionMglActual = direccionMglFacadeLocal
                                .findById(direccionMgl);

                        direccionMglActual = Objects.isNull(direccionMglActual) ? new DireccionMgl() : direccionMglActual;

                        direccionMglActual.setDirNodoDth(subDireccionMglActual.getSdiNodoDth()
                                != null ? subDireccionMglActual.getSdiNodoDth() : null);
                        direccionMglActual.setDirNodoFtth(subDireccionMglActual.getSdiNodoFtth()
                                != null ? subDireccionMglActual.getSdiNodoFtth() : null);
                        direccionMglActual.setDirNodoMovil(subDireccionMglActual.getSdiNodoMovil()
                                != null ? subDireccionMglActual.getSdiNodoMovil() : null);
                        direccionMglActual.setDirNodoWifi(subDireccionMglActual.getSdiNodoWifi()
                                != null ? subDireccionMglActual.getSdiNodoWifi() : null);
                        direccionMglActual.setDirNodouno(subDireccionMglActual.getSdiNodouno()
                                != null ? subDireccionMglActual.getSdiNodouno() : null);
                        direccionMglActual.setDirNododos(subDireccionMglActual.getSdiNododos()
                                != null ? subDireccionMglActual.getSdiNododos() : null);
                        direccionMglActual.setDirNodotres(subDireccionMglActual.getSdiNodotres()
                                != null ? subDireccionMglActual.getSdiNodotres() : null);

                        //obtiene barrio georefenciado
                        ConsultaEspecificaManager manager = new ConsultaEspecificaManager();
                        String dirId = String.valueOf(subDireccionMglActual.getDirId());
                        barrioGeo = manager.obtenerLocalidadDir(!Objects.isNull(dirId)?dirId.trim():"");
                    }

                }else if (direccionDetallada.getIdDirCatastro().substring(0, 1)
                        .equalsIgnoreCase("d")) {
                    String idDireccion
                            = direccionDetallada.getIdDirCatastro().substring(1);

                    //obtener direccion apartir del id de la direccion.
                    DireccionMgl direccionMgl = new DireccionMgl();
                    direccionMgl.setDirId(new BigDecimal(idDireccion));
                    //obtiene barrio georefenciado
                    ConsultaEspecificaManager manager
                            = new ConsultaEspecificaManager();
                    barrioGeo = manager.obtenerLocalidadDir(idDireccion.trim());

                    direccionMglActual = direccionMglFacadeLocal
                            .findById(direccionMgl);
                    if (!Objects.isNull(direccionMglActual)) {
                        drDireccion.setEstrato(String.valueOf(direccionMglActual.getDirEstrato()));
                    }
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al obtener fuente y dirección en formato Rr" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener fuente y dirección en formato Rr" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que obtiene la direccion detallada extraida de la solicitud
     *
     * @author Juan David Hernandez
     * @param solicitudSeleccionada
     */
    public void obtenerDireccionDetallada(Solicitud solicitudSeleccionada) {
        try {
            if (solicitudSeleccionada != null && solicitudSeleccionada.getDireccionDetallada() != null) {
                cmtDireccionDetalladaMgl
                        = cmtDireccionDetalleMglFacadeLocal
                                .buscarDireccionIdDireccion(solicitudSeleccionada.getDireccionDetallada()
                                        .getDireccionDetalladaId());

                if (cmtDireccionDetalladaMgl == null) {
                    throw new ApplicationException("No fue posible obtener la direccion detallada"
                            + " de la solicitud por tal motivo no se puede gestionar ");
                }
            } else {
                throw new ApplicationException("La solicitud no cuenta con la direccion "
                        + "detallada asociada por tal motivo no es posible gestionar");
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener la direccion detallada de la solicitud" + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que obtiene la información de si RR se encuentra habilitado o no
     *
     * @author Juan David Hernandez
     * @return 
     */
    public boolean validarRrHabilitado() {
        boolean habilitaRR = false;
        try {            
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                 habilitaRR = true;
                return habilitaRR;
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al informacion si RR se encuentra habilitado" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al informacion si RR se encuentra habilitado" + e.getMessage() , e, LOGGER);
        }
        return habilitaRR;
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
            if (Objects.isNull(direccionDetallada) || Objects.isNull(solicitudDthSeleccionada)) {
                throw new ApplicationException("No fue posible obtener la dirección a partir de la dirección detallada,"
                        + " intente crear de nuevo la solicitud ya que no es posible gestionarla ");
            }

            CmtDireccionDetalleMglManager direccionDetalladaManager = new CmtDireccionDetalleMglManager();
            if (Objects.equals(solicitudDthSeleccionada.getCambioDir(), Constant.RR_DIR_CAMB_HHPP_1)) {
                direccionDetallada.setCpTipoNivel5(solicitudDthSeleccionada.getCpTipoNivel5() != null
                        ? solicitudDthSeleccionada.getCpTipoNivel5() : null);
                direccionDetallada.setCpTipoNivel6(solicitudDthSeleccionada.getCpTipoNivel6() != null
                        ? solicitudDthSeleccionada.getCpTipoNivel6() : null);
                direccionDetallada.setCpValorNivel5(solicitudDthSeleccionada.getCpValorNivel5() != null
                        ? solicitudDthSeleccionada.getCpValorNivel5() : null);
                direccionDetallada.setCpValorNivel6(solicitudDthSeleccionada.getCpValorNivel6() != null
                        ? solicitudDthSeleccionada.getCpValorNivel6() : null);
            }

            drDireccion = direccionDetalladaManager.parseCmtDireccionDetalladaMglToDrDireccion(direccionDetallada);

            if (Objects.isNull(drDireccion)) {
                throw new ApplicationException("No fue posible obtener la direccion apartir de la dirección detallada,"
                        + " intente crear de nuevo la solicitud ya quen o es posible gestionarla ");
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error al obtener la direccion detallada de la solicitud" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener la direccion detallada de la solicitud" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que obtiene los diferentes hhpp asociados al que se desea crear
     *
     * @author Juan David Hernandez
     * @param dirDetallada
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void obtenerHhppAsociadosList(CmtDireccionDetalladaMgl dirDetallada)
            throws ApplicationException {
        try {
            this.hhppList = new ArrayList<>();
            boolean evaluarEstadoRegistro = true;
            HhppMglManager hhppMglManager = new HhppMglManager();
            if (!Objects.isNull(dirDetallada)) {
                //Obtenemos los Hhpp de la Subdireccion
                if (!Objects.isNull(dirDetallada.getSubDireccion())
                        && !Objects.isNull(dirDetallada.getSubDireccion().getSdiId())) {
                    List<HhppMgl> hhhpSubDirList = hhppMglManager
                            .findHhppSubDireccion(dirDetallada.getSubDireccion().getSdiId());

                    if (!Objects.isNull(hhhpSubDirList) && !hhhpSubDirList.isEmpty()) {
                        for (HhppMgl hhppSubMgl : hhhpSubDirList) {
                            if (evaluarEstadoRegistro) {
                                if (!hhppList.contains(hhppSubMgl) && hhppSubMgl.getEstadoRegistro() == 1) {
                                    hhppList.add(hhppSubMgl);
                                }
                            } else {
                                if (!hhppList.contains(hhppSubMgl)) {
                                    hhppList.add(hhppSubMgl);
                                }
                            }
                        }
                    }

                } else {
                    //Obtenemos los Hhpp de la Direccion principal    
                    if (!Objects.isNull(dirDetallada.getDireccion())
                            && !Objects.isNull(dirDetallada.getDireccion().getDirId())) {

                        List<HhppMgl> hhhpDirList
                                = hhppMglManager
                                        .findHhppDireccion(dirDetallada.getDireccion().getDirId());

                        if (!Objects.isNull(hhhpDirList) && !hhhpDirList.isEmpty()) {
                            for (HhppMgl hhppMgl : hhhpDirList) {
                                if (evaluarEstadoRegistro) {
                                    if (!hhppList.contains(hhppMgl) && Objects.equals(hhppMgl.getEstadoRegistro(), 1)) {
                                        hhppList.add(hhppMgl);
                                    }
                                } else {
                                    if (!hhppList.contains(hhppMgl)) {
                                        hhppList.add(hhppMgl);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(hhppList)) {
                hhppExiste = true;
            }

        } catch (Exception e) {
            String msn = "Error al obtener hhpp asociados ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que valida que si se encuentra ingresado un nod red FO cuando ingresan un nodo GPON
     * y la validacion de que solo se seleccione 1 tecnología que viaje a RR cuando se encuentra
     * habilitado
     *
     * @author Juan David Hernandez
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public boolean validacionTecSincronizaRr_NodoGpon_RedFO(List<CmtBasicaMgl> tecnologiaBasicaList)
            throws ApplicationException {
        try {            

            //Si RR se encuentra habilitado y se trata de una solicitud de creacion de hhpp
            if (solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_HHPP_CREADO)
                    && solicitudDthSeleccionada.getCambioDir().equalsIgnoreCase(Constant.RR_DIR_CREA_HHPP_0)
                    && tecnologiaBasicaList != null && !tecnologiaBasicaList.isEmpty()) {
                
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
                   
                        if(tecGpon){
                            if (!validarNodoRedFO(solicitudDthSeleccionada)) {
                                
                                if (!tecRedFO) {
                                    String msn = "Validación de nodo: Cuando se ingresa un nodo GPON es necesario ingresar un nodo RED FO";
                                    mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                                    return false;
                                }
                            }
                             
                        }else{
                            if (tecUnifilar) {
                                if (!validarNodoRedFO(solicitudDthSeleccionada)) {
                                    if (!tecRedFO) {
                                        String msn = "Validación de nodo: Cuando se ingresa un nodo UNIFILAR es necesario ingresar un nodo RED FO";
                                        mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                                        return false;
                                    }
                                }

                            }
                        }  
                    
                    return true;
                    
                }
            }else{
                if(solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_VERIFICACION_AGENDADA)
                        && tecnologiaBasicaList != null && !tecnologiaBasicaList.isEmpty()){
                    
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
              
                        if(tecGpon){
                            if (!validarNodoRedFO(solicitudDthSeleccionada)) {
                                if (!tecRedFO) {
                                    String msn = "Validación de nodo: Cuando se ingresa un nodo GPON es necesario ingresar un nodo RED FO";
                                    mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                                    return false;
                                }
                            }
                             
                        }else{
                            if (tecUnifilar) {
                                if (!validarNodoRedFO(solicitudDthSeleccionada)) {
                                    if (!tecRedFO) {
                                        String msn = "Validación de nodo: Cuando se ingresa un nodo UNIFILAR es necesario ingresar un nodo RED FO";
                                        mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                                        return false;
                                    }
                                }

                            }
                        } 
                                            
                    return true;
                    
                }
                    
                    return true;
                }
            }
               
            return true;
        } catch (Exception e) {
            String msn = "Error al validar tecnologia seleccionada para RR";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
            return false;
        }
    }

    /**
     * Función que obtiene los diferentes hhpp asociados al que se desea crear
     *
     * @author Juan David Hernandez
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public boolean validarExistenciaHhpp(List<CmtBasicaMgl> tecnologiaBasicaList)
            throws ApplicationException {
        try {
            if (solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_HHPP_CREADO)) {
                if (CollectionUtils.isNotEmpty(tecnologiaBasicaList)) {
                    for (CmtBasicaMgl tec : tecnologiaBasicaList) {
                        if (tec.getNodoGestion() != null && !tec.getNodoGestion().isEmpty()) {
                            if (hhppList != null && !hhppList.isEmpty()) {
                                for (HhppMgl hhppMgl : hhppList) {
                                    if (hhppMgl.getNodId() != null && hhppMgl.getNodId().getNodTipo() != null
                                            && hhppMgl.getNodId().getNodTipo().getBasicaId().equals(tec.getBasicaId())) {
                                        String msn = "La tecnología " + tec.getNombreBasica() + " "
                                                + "que desea crear ya existe para esta dirección.";
                                        mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                                        return false;
                                    }
                                }
                            }
                        }
                        if (tec.getNapCercana() != null || tec.getNap() != null && !tec.getNap().isEmpty()) {
                            if (tec.getNap() != null && !tec.getNap().isEmpty()) {
                                String tipoBasica = establecerNodoCoberturaSugerido(tec);
                                if (tipoBasica == null || tipoBasica.isEmpty()) {
                                    if (tec.getNap().length() < 8 || tec.getNap().length() > 24) {
                                        String msn = "La NAP ingresada no tiene la estructura Minimo 8 y maximo 24 caracteres";
                                        mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                                        return false;
                                    }
                                } else {
                                    if (tec.getNap().length() > 8) {
                                        String msn = "Se excede el numero permitido de caracteres en la NAP (8)";
                                        mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                                        return false;
                                    }
                                    String nodoCobertu = establecerNodoCoberturaSugerido(tec);
                                    String napCorta = tec.getNap().substring(0, tec.getNap().length()-2);
                                    if (!nodoCobertu.equals(napCorta)) {
                                        String msn = "La nap ingresada no corresponde al nodo";
                                        mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                                        return false;
                                    }
                                }
                            }
                            if (tec.getNapCercana() == null && tec.getNap() != null && !tec.getNap().isEmpty()) {
                                tec.setNapCercana(tec.getNap());
                            }
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            String msn = "Error al obtener hhpp asociados ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            return false;
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
            if (Constant.RR_DIR_CREA_HHPP_0.equals(solicitudDthSeleccionada.getCambioDir()) && !Objects.isNull(cmtDireccionDetalladaMgl)) {
                return hhppMglFacadeLocal.obtenerNodosCercanoSolicitudHhpp(cmtDireccionDetalladaMgl, tecnologiaBasica, centroPobladoGpo.getGpoId());
            }
            throw new ApplicationException("No es posible obtener el listado de nodos"
                    + " cercanos debido a que no se cuenta con la direccion "
                    + "detallada correctamente. ");
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(e.getMessage(), e, LOGGER);
            return Collections.emptyList();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener listado de nodos cercanos " , e, LOGGER);
            return Collections.emptyList();
        }
    }
    
        
    /**
     * Función que obtiene listado de naps asociadas
     *
     * @param tecnologiaBasica
     * @return 
     */
    public List<HhppMgl> obtenerNapsList(CmtBasicaMgl tecnologiaBasica) {
        try {
            List<HhppMgl> hhpp = new ArrayList<>();
            if (tecnologiaBasica.isNapFTTH()) {
            if (Constant.RR_DIR_CREA_HHPP_0.equals(solicitudDthSeleccionada.getCambioDir())) {
                // se busca la nap asociada a la direccion principal
                hhpp = hhppMglFacadeLocal.findHhppNap(direccionRREntity.getCalle(), direccionRREntity.getNumeroUnidad(),centroPobladoGpo.getGpoCodigo());
                if (!hhpp.isEmpty()) {
                    return hhpp;
                }
                // Si no se encuentra nap principal se realiza la busqueda segun logica de nodo cercano
                String placaCorta = direccionRREntity.getNumeroUnidad().substring(0, direccionRREntity.getNumeroUnidad().length() - 2).concat("%");
                hhpp = hhppMglFacadeLocal.findHhppNap(direccionRREntity.getCalle(), placaCorta, centroPobladoGpo.getGpoCodigo() );    
                return hhpp;
            }
            }
            return hhpp;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener listado de nodos cercanos " , e, LOGGER);
            return Collections.emptyList();
        }

    }

    /**
     * Función que obtiene listado de nodos de cobertura
     *
     * @author Juan David Hernandez
     */
    public void obtenerNodosCoberturaList() {
        try {
            nodosCoberturaList = new ArrayList<>();
            
            if (solicitudDthSeleccionada.getCambioDir().
                    equals(Constant.RR_DIR_CREA_HHPP_0)) {

                if (direccionMglActual.getDirNodoDth() != null
                        && !direccionMglActual.getDirNodoDth().isEmpty()) {
                    nodosCoberturaList.add(direccionMglActual.getDirNodoDth());
                }
                if (direccionMglActual.getDirNodoFtth() != null
                        && !direccionMglActual.getDirNodoFtth().isEmpty()) {
                    nodosCoberturaList.add(direccionMglActual.getDirNodoFtth());
                }
                if (direccionMglActual.getDirNodoMovil() != null
                        && !direccionMglActual.getDirNodoMovil().isEmpty()) {
                    nodosCoberturaList.add(direccionMglActual.getDirNodoMovil());
                }
                if (direccionMglActual.getDirNodoWifi() != null
                        && !direccionMglActual.getDirNodoWifi().isEmpty()) {
                    nodosCoberturaList.add(direccionMglActual.getDirNodoWifi());
                }

                if (direccionMglActual.getDirNodouno() != null
                        && !direccionMglActual.getDirNodouno().isEmpty()) {
                    nodosCoberturaList.add(direccionMglActual.getDirNodouno());
                }

                if (direccionMglActual.getDirNododos() != null) {
                    nodosCoberturaList.add(direccionMglActual.getDirNododos());
                }

                if (direccionMglActual.getDirNodotres() != null
                        && !direccionMglActual.getDirNodotres().isEmpty()) {
                    nodosCoberturaList.add(direccionMglActual.getDirNodotres());
                }
            }
        } catch (Exception e) {
            String msn = "Error al obtener listado de nodos de cobertura ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que obtiene listado de nodos dth por ciudad
     *
     * @author Juan David Hernandez
     * @param drDireccion
     * @param centroPoblado
     */
    public void obtenerNodosCoberturaGeo(DrDireccion drDireccion,
            GeograficoPoliticoMgl centroPoblado) {
        try {
            if (!Objects.isNull(drDireccion) && !Objects.isNull(centroPoblado)){
                addressService = cmtValidadorDireccionesFacadeLocal
                        .calcularCoberturaDrDireccion(drDireccion, centroPoblado);

                if (!Objects.isNull(addressService.getReliability()) && !addressService.getReliability().isEmpty()) {
                    addressService.setReliability(addressService.getReliability() + "%");
                } else {
                    addressService.setReliability("0%");
                }

                if (!Objects.isNull(addressService.getReliabilityPlaca()) && !addressService.getReliabilityPlaca().isEmpty()) {
                    addressService.setReliabilityPlaca(addressService.getReliabilityPlaca() + "%");
                } else {
                    addressService.setReliabilityPlaca("0%");
                }
            }

        } catch (ApplicationException e) {
            String msn = "Error al obtener listado de Rr nodos Dth ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al obtener listado de Rr nodos Dth ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que obtiene listado de unidades asociadas al predio que fueron
     * modificadas en la creación de la solicitud.
     *
     * @author Juan David Hernandez
     */
    public void obtenerUnidadesPrediosModificadas() {
        try {
            DireccionRRManager direccionRRManager
                    = new DireccionRRManager(detalleDireccionEntity);
            //verificamos que existan cambios de HHPP en el Creacion
            List<ModificacionDir> modificacionList
                    = modificacionDirFacadeLocal
                            .findByIdSolicitud(idSolicitudDthSeleccionada);

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
            if (unidadModificadaList != null && !unidadModificadaList.isEmpty()) {
                unidadesPredioModificadas = true;
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

    public void obtenerEstadoUnidadesModificadas() {
        try {
            if (unidadModificadaList != null && !unidadModificadaList.isEmpty()) {
                for (UnidadStructPcml u : unidadModificadaList) {
                    if (unidadAuxiliarList != null
                            && !unidadAuxiliarList.isEmpty()) {
                        for (UnidadStructPcml unidadPredio
                                : unidadAuxiliarList) {
                            if (unidadPredio.getCalleUnidad()
                                    .equals(u.getCalleUnidad())
                                    && unidadPredio.getCasaUnidad()
                                            .equals(u.getCasaUnidad())
                                    && unidadPredio.getAptoUnidad()
                                            .equals(u.getAptoUnidad())) {

                                u.setEstadUnidadad(unidadPredio.getEstadUnidadad()
                                        != null ? unidadPredio.getEstadUnidadad() : "");
                                u.setNodUnidad(unidadPredio.getNodUnidad() != null
                                        ? unidadPredio.getNodUnidad() : "");
                                u.setNombreTecnologia(unidadPredio.getNombreTecnologia() != null
                                        ? unidadPredio.getNombreTecnologia() : "");
                                //conversion de estrato
                                if (unidadPredio.getEstratoUnidad() != null) {
                                    if (unidadPredio.getEstratoUnidad().equals("-1")) {
                                        u.setEstratoUnidad("NG");
                                    } else {
                                        if (unidadPredio.getEstratoUnidad().equals("0")) {
                                            u.setEstratoUnidad("NR");
                                        } else {
                                            u.setEstratoUnidad(unidadPredio.getEstratoUnidad());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            String msn = "Error al realizar copia de unidades modificadas  ";
            FacesUtil.mostrarMensajeError(msn+ e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que se utilizada para obtener el listado de unidades que
     * presentan conflicto y eliminarlas del listado de cambios de apto
     *
     * @author Juan David Hernandez
     */
    public void obtenerUnidadesConflictoList() {
        try {
            //verificamos que existan cambios de HHPP en el Creacion
            List<ModificacionDir> hhppConflictoList
                    = modificacionDirFacadeLocal
                            .findHhppConflicto(idSolicitudDthSeleccionada);

            if (hhppConflictoList != null && !hhppConflictoList.isEmpty()) {
                HhppMglManager hhppMglManager = new HhppMglManager();
                int idUnidadTmp = 0;

                for (ModificacionDir unidadConflicto : hhppConflictoList) {
                    if ((unidadConflicto.getConflictApto() != null && !unidadConflicto.getConflictApto().isEmpty())
                            && StringUtils.equalsIgnoreCase("1", unidadConflicto.getConflictApto())) {

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
                unidadConflictoPanel = true;

                /* Listados de opciones para cambio de apartamento cuando un predio 
             tiene unidades asociadas */
                ConfigurationAddressComponent configurationComponentListadoUnidades
                        = componenteDireccionesMglFacade
                                .getConfiguracionComponente(solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp());

                if (!Objects.isNull(configurationComponentListadoUnidades)){
                    listNivel5 = configurationComponentListadoUnidades.getAptoValues()
                            .getTiposApto();
                    listNivel6 = configurationComponentListadoUnidades.getAptoValues()
                            .getTiposAptoComplemento();
                }
            }
        } catch (ApplicationException e) {
            String msn = "Error al obteniendo unidades con conflicto ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al obteniendo unidades con conflicto ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que obtiene listado de unidades asociadas al predio
     *
     * @author Juan David Hernandez
     * @param drDireccionActual
     * @param centroPobladoId
     */
    public void obtenerUnidadesAsociadasPredio(DrDireccion drDireccionActual, BigDecimal centroPobladoId) {
        try {
            DrDireccion drDireccionBusqueda = drDireccionActual;
            unidadList = new ArrayList<>();
            unidadAuxiliarList = new ArrayList<>();

            //cargue de unidades asociadas al predio desde MGL
            // Si tiene dirección nueva se buscan unidades con la direccion nueva.
            if (drDireccionBusqueda != null) {
                /*Reseteamos los complementos para buscar direcciones 
                 solo hasta la placa de la direccion */
                drDireccionBusqueda.setCpTipoNivel5(null);
                drDireccionBusqueda.setCpTipoNivel6(null);

                drDireccionBusqueda.setCpValorNivel5(null);
                drDireccionBusqueda.setCpValorNivel6(null);
                
                //se asigna la direccion respuesta del geo con AVENIDA sin alteraciones para ser buscada
                if(direccionRespuestaGeo != null && !direccionRespuestaGeo.isEmpty()){
                    drDireccionBusqueda.setDireccionRespuestaGeo(direccionRespuestaGeo);
                }

                //Obtenemos los hhpp asociados a la direccion y subdirección.
                List<HhppMgl> hhppList2 = cmtDireccionDetalleMglFacadeLocal
                        .findHhppByDireccion(drDireccionBusqueda, centroPobladoId, true,
                                0, 0, false);

                if (!Objects.isNull(hhppList2) && !hhppList2.isEmpty()) {
                    int idIncremental = 0;
                    for (HhppMgl hhppMgl : hhppList2) {
                        UnidadStructPcml unidad = new UnidadStructPcml();
                        idIncremental++;
                        unidad.setIdUnidad(new BigDecimal(idIncremental));
                        unidad.setCalleUnidad(hhppMgl.getHhpCalle() != null ? hhppMgl.getHhpCalle() : "");
                        unidad.setCasaUnidad(hhppMgl.getHhpPlaca() != null ? hhppMgl.getHhpPlaca() : "");
                        unidad.setAptoUnidad(hhppMgl.getHhpApart() != null ? hhppMgl.getHhpApart() : "");
                        unidad.setEstadUnidadad(hhppMgl.getEhhId() != null ? hhppMgl.getEhhId().getEhhNombre() : "");
                        if (hhppMgl.getSubDireccionObj() != null) {
                            if (Objects.equals(hhppMgl.getSubDireccionObj().getSdiEstrato(), BigDecimal.valueOf(-1))) {
                                unidad.setEstratoUnidad("NG");
                            } else if(Objects.equals(hhppMgl.getSubDireccionObj().getSdiEstrato(), BigDecimal.ZERO)){
                                unidad.setEstratoUnidad("NR");
                            } else {
                                unidad.setEstratoUnidad(String.valueOf(hhppMgl.getSubDireccionObj().getSdiEstrato()));
                            }
                        } else {
                            if (hhppMgl.getDireccionObj().getDirEstrato() != null) {
                                if (Objects.equals(hhppMgl.getDireccionObj().getDirEstrato(), BigDecimal.valueOf(-1))) {
                                    unidad.setEstratoUnidad("NG");
                                } else if(Objects.equals(hhppMgl.getDireccionObj().getDirEstrato(), BigDecimal.ZERO)){
                                    unidad.setEstratoUnidad("NR");
                                } else {
                                    unidad.setEstratoUnidad(String.valueOf(hhppMgl.getDireccionObj().getDirEstrato()));
                                }
                            }
                        }

                        unidad.setNodUnidad(hhppMgl.getNodId() != null ? hhppMgl.getNodId().getNodCodigo() : "");
                        unidad.setTecnologiaId(hhppMgl.getNodId() != null ? hhppMgl.getNodId().getNodTipo().getBasicaId() : null);
                        unidad.setNombreTecnologia(hhppMgl.getNodId() != null ? hhppMgl.getNodId().getNodTipo().getNombreBasica() : "");
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
                                        && unidadValidada != null
                                        && unidadValidada.getHhppMgl() != null
                                        && unidadValidada.getHhppMgl().getDireccionObj() != null
                                        && unidadValidada.getHhppMgl().getDireccionObj().getDirId() != null
                                        && unidadValidada.getHhppMgl().getSubDireccionObj() != null
                                        && unidadValidada.getHhppMgl().getSubDireccionObj().getSdiId() != null) {

                                    if (Objects.equals(unidadValidada.getHhppMgl().getDireccionObj().getDirId(), hhppMgl.getDireccionObj().getDirId())
                                            && Objects.equals(hhppMgl.getSubDireccionObj().getSdiId(), unidadValidada.getHhppMgl().getSubDireccionObj().getSdiId())) {
                                        hhppRepetida = true;
                                    }
                                } else {
                                    if (hhppMgl.getDireccionObj() != null
                                            && hhppMgl.getDireccionObj().getDirId() != null
                                            && hhppMgl.getSubDireccionObj() == null
                                            && unidadValidada != null
                                            && unidadValidada.getHhppMgl() != null
                                            && unidadValidada.getHhppMgl().getDireccionObj() != null
                                            && unidadValidada.getHhppMgl().getDireccionObj().getDirId() != null
                                            && unidadValidada.getHhppMgl().getSubDireccionObj() == null
                                            && Objects.equals(hhppMgl.getDireccionObj().getDirId(), unidadValidada.getHhppMgl().getDireccionObj().getDirId())) {
                                        hhppRepetida = true;
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
                if (unidadList != null && !unidadList.isEmpty()) {
                    unidadesPredio = true;
                    listInfoByPage(1);
                }
            }

        } catch (ApplicationException e) {
            String msn = "Error al obtener listado de unidades asociadas al predio ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } 
    }

    /**
     * Función utilizada para realizar una copia de los hhpp asociados a la
     * direccion encontrados y poder validar si ya se encuentran en el listado
     * con diferente tecnologia para no mostrarlos en pantalla
     *
     * @param unidadesHhppList listado de hhpp
     *
     * @author Juan David Hernandez
     * @return 
     */
    public List<UnidadStructPcml> copiaUnidadesValidacionHhppMismaDir(List<UnidadStructPcml> unidadesHhppList) {
        try {
            List<UnidadStructPcml> copiaUnidadesHhpp = new ArrayList<UnidadStructPcml>();

            if (unidadesHhppList != null && !unidadesHhppList.isEmpty()) {
                for (UnidadStructPcml unidadStructPcml : unidadesHhppList) {
                    copiaUnidadesHhpp.add(unidadStructPcml.clone());
                }
            }
            return copiaUnidadesHhpp;

        } catch (CloneNotSupportedException e) {
            FacesUtil.mostrarMensajeError("Error en copia Unidades Validacion Hhpp Misma Dirección" + e.getMessage() , e, LOGGER);
            return null;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en copia Unidades Validacion Hhpp Misma Dirección" + e.getMessage() , e, LOGGER);
            return null;
        }
    }

    /**
     * Función que obtiene listado de unidades asociadas al predio
     *
     * @author Juan David Hernandez
     * @param centroPobladoId
     */
    public void obtenerCiudadDepartamentoByCentroPobladoId(BigDecimal centroPobladoId) {
        try {
            if (centroPobladoId.compareTo(BigDecimal.ZERO) > 0){
                centroPobladoGpo = geograficoPoliticoMglFacadeLocal
                        .findById(centroPobladoId);

                if (!Objects.isNull(centroPobladoGpo)){
                    ciudadGpo = geograficoPoliticoMglFacadeLocal
                            .findById(centroPobladoGpo.getGeoGpoId());

                    if(!Objects.isNull(ciudadGpo)){
                        departamentoGpo = (geograficoPoliticoMglFacadeLocal
                                .findById(ciudadGpo.getGeoGpoId()));
                    }
                }
            }
        } catch (ApplicationException e) {
            String msn = "Error al nombre de ciudad predio ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al nombre de ciudad predio ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que obtiene listado estratos.
     *
     * @author Juan David Hernandez
     */
    public void obtenerEstratoList() throws ApplicationException {
        try {
            CmtTipoBasicaMgl cmtTipoBasicaMgl1 = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(co.com.claro.mgl.utils.Constant.TIPO_BASICA_ESTRATOS_HHPP);
            if(Objects.isNull(cmtTipoBasicaMgl1)){
                throw new ApplicationException("Error al obtener listado de estratos de hhpp ");
            }
            List<CmtBasicaMgl> list = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl1);
            estratoList = Objects.isNull(list)?new ArrayList<>():list;
        } catch (Exception e) {
            LOGGER.error("obtenerEstratoList() -> " + e.getMessage());
            throw new ApplicationException("Error al obtener listado de estratos de hhpp ");
        }
    }

    /**
     * Función que obtiene el tipo de solicitud.
     *
     * @author Juan David Hernandez
     * @return 
     */
    public CmtTipoSolicitudMgl obtenerTipoSolicitud() {
        try {
            return cmtTipoSolicitudMgl = cmtTipoSolicitudMglFacadeLocal
                    .findTipoSolicitudByAbreviatura(solicitudDthSeleccionada.getTipo());
        } catch (ApplicationException e) {
            String msn = "Error al obtener el tipo de solicitud ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
            return null;
        } catch (Exception e) {
            String msn = "Error al obtener el tipo de solicitud ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
            return null;
        }
    }

    /**
     * Función utilizada para obtener el listado de tipos de solicitudes desde
     * la base de datos
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void obtenerListadoTipoSolicitudList() throws ApplicationException {
        tipoSolicitudList = parametrosCallesFacade.findByTipo("TIPO_SOLICITUD");
    }

    /**
     * Función que obtiene valores de tipo de acción de solicitud básica. la
     * base de datos
     *
     * @author Juan David Hernandez
     */
    public void obtenerTipoAccionSolicitudList() throws ApplicationException {
        try {
            CmtTipoBasicaMgl cmtTipoBasicaMgl1 = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_ACCION_SOLICITUD);
            if(Objects.isNull(cmtTipoBasicaMgl1)){
                throw new ApplicationException("No se pudo obtener el tipo de acción");
            }
            List<CmtBasicaMgl> list = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl1);
            tipoAccionSolicitudBasicaList = Objects.isNull(list)?new ArrayList<>():list;
        } catch (Exception e) {
            LOGGER.error("obtenerTipoAccionSolicitudList() ->" + e.getMessage());
            throw new ApplicationException("Error al realizar cargue de listado de tipo de acción ");
        }
    }

    /**
     * Función que obtiene valores de tipo de notas
     *
     * @author Juan David Hernandez
     */
    public void obtenerTipoNotasList() throws ApplicationException {
        try {
            CmtTipoBasicaMgl tipoBasicaNotaOt = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_DE_NOTAS);
            if (Objects.isNull(tipoBasicaNotaOt)){
                throw new ApplicationException("No existe el tipo de nota");
            }
            List<CmtBasicaMgl> list = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaNotaOt);
            tipoNotaList = Objects.isNull(list)?new ArrayList<>():list;
        } catch (Exception e) {
            LOGGER.error("" + e.getMessage());
            throw new ApplicationException("Error al obtener listado de tipos de notas ");
        }
    }

    /**
     * Función que obtiene valores de tipo de tecnología
     *
     * @author Juan David Hernandez
     */
    public void obtenerTipoTecnologiaList(Solicitud solicitudDth) throws ApplicationException {
        try {
            CmtTipoBasicaMgl cmtTipoBasicaMgl1 = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
            //Obtiene valores de tipo de tecnología
            tipoTecnologiaBasicaList = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl1);
            tecnologiaBasicaList = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl1);

            if (!StringUtils.equalsIgnoreCase(solicitudDth.getCambioDir(),Constant.RR_DIR_CAMBIO_ESTRATO_2) && !Objects.isNull(tecnologiaBasicaList)) {
                for (CmtBasicaMgl tecnologiaBasicaMgl : tecnologiaBasicaList) {
                    tecnologiaBasicaMgl.setNodoGestion(establecerNodoCoberturaSugerido(tecnologiaBasicaMgl));
                }
            }
        } catch (Exception e) {
            LOGGER.error("obtenerTipoTecnologiaList() -> " + e.getMessage());
            throw new ApplicationException("Error al obtener listado de tipos de tecnología ");
        }
    }

        /**
     * Función utilizada para establece el nodo de cobertura sugerido por el GEO
     *
     * @author Juan David Hernandez
     * @param tecnologia tecnologia para saber si hay cobertura en ella
     * @return 
     */
    public String establecerNodoCoberturaSugerido(CmtBasicaMgl tecnologia) {
        String nodoCobertura = "";
        try {
            if (!Objects.isNull(addressService)){
                if (StringUtils.equalsIgnoreCase(tecnologia.getIdentificadorInternoApp(), Constant.DTH)) {
                    nodoCobertura = addressService.getNodoDth();

                } else if (StringUtils.equalsIgnoreCase(tecnologia.getIdentificadorInternoApp(),Constant.FIBRA_FTTX)) {
                    nodoCobertura = addressService.getNodoCuatro();

                }
                  else if (StringUtils.equalsIgnoreCase(tecnologia.getIdentificadorInternoApp(),Constant.FIBRA_FTTTH)) {
                    nodoCobertura = addressService.getNodoFtth();

                } else if (StringUtils.equalsIgnoreCase(tecnologia.getIdentificadorInternoApp(),Constant.HFC_BID)) {
                    nodoCobertura = addressService.getNodoUno();

                } else if (StringUtils.equalsIgnoreCase(tecnologia.getIdentificadorInternoApp(),Constant.HFC_UNI)) {
                    nodoCobertura = addressService.getNodoDos();

                } else if (StringUtils.equalsIgnoreCase(tecnologia.getIdentificadorInternoApp(),Constant.FIBRA_OP_GPON)) {
                    nodoCobertura = addressService.getNodoTres();

                } else if (StringUtils.equalsIgnoreCase(tecnologia.getIdentificadorInternoApp(),Constant.LTE_INTERNET)) {
                    nodoCobertura = addressService.getNodoWifi();

                } else if (StringUtils.equalsIgnoreCase(tecnologia.getIdentificadorInternoApp(),Constant.BTS_MOVIL)) {
                    nodoCobertura = addressService.getNodoMovil();

                } else if (StringUtils.equalsIgnoreCase(tecnologia.getIdentificadorInternoApp(),Constant.FIBRA_OP_UNI)) {
                    nodoCobertura = addressService.getNodoZonaUnifilar();
                }
            }
        } catch (Exception ex) {
            nodoCobertura = "";
            FacesUtil.mostrarMensajeError("Error en establecer Nodo Cobertura Sugerido" + ex.getMessage() , ex, LOGGER);
        }
        return nodoCobertura;
    }
    
        /**
     * Función establece de forma automatica el check de envio de creacion de hhpp a RR
     *
     * @author Juan David Hernandez
     * @param tecnologia que se desea validar si se envia a RR
     */
    public boolean establecerCheckAutomaticoSincronizaRr(CmtBasicaMgl tecnologia) {
        boolean result = false;

        try {
            if (tecnologia.getIdentificadorInternoApp() != null
                    && !tecnologia.getIdentificadorInternoApp().isEmpty()) {
                /*si la tecnologia es la misma de la solicitud, 
                tiene cobertura del geo, RR esta prendido*/
                if (tecnologia.getIdentificadorInternoApp().equals(Constant.DTH)
                        && solicitudDthSeleccionada != null
                        && solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp() != null
                        && solicitudDthSeleccionada.getTecnologiaId()
                                .getIdentificadorInternoApp().equalsIgnoreCase(tecnologia.getIdentificadorInternoApp())
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
                                return true;
                            }
                        }
                    }
                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.FIBRA_FTTTH)
                        && solicitudDthSeleccionada != null
                        && solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp() != null
                        && solicitudDthSeleccionada.getTecnologiaId()
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
                                return true;
                            }
                        }
                    }

                }  else if (tecnologia.getIdentificadorInternoApp().equals(Constant.HFC_BID)
                        && solicitudDthSeleccionada != null
                        && solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp() != null
                        && solicitudDthSeleccionada.getTecnologiaId()
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
                                return true;
                            }
                        }
                    }

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.HFC_UNI)
                        && solicitudDthSeleccionada != null
                        && solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp() != null
                        && solicitudDthSeleccionada.getTecnologiaId()
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
                                return true;
                            }
                        }
                    }

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.FIBRA_OP_GPON)
                        && solicitudDthSeleccionada != null
                        && solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp() != null
                        && solicitudDthSeleccionada.getTecnologiaId()
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
                                return true;
                            }
                        }
                    }

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.LTE_INTERNET)
                        && solicitudDthSeleccionada != null
                        && solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp() != null
                        && solicitudDthSeleccionada.getTecnologiaId()
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
                                return true;
                            }
                        }
                    }

                } else if (tecnologia.getIdentificadorInternoApp().equals(Constant.BTS_MOVIL)
                        && solicitudDthSeleccionada != null
                        && solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp() != null
                        && solicitudDthSeleccionada.getTecnologiaId()
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
                                return true;
                            }
                        }
                    }
                }else if (tecnologia.getIdentificadorInternoApp().equals(Constant.FIBRA_OP_UNI)
                        && solicitudDthSeleccionada != null
                        && solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp() != null
                        && solicitudDthSeleccionada.getTecnologiaId()
                                .getIdentificadorInternoApp().equalsIgnoreCase(tecnologia.getIdentificadorInternoApp())
                        && addressService != null && addressService.getNodoZonaUnifilar() != null && !addressService.getNodoZonaUnifilar().isEmpty()
                        && habilitarRR) {
                    //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                    if (tecnologia.getListCmtBasicaExtMgl() != null
                            && !tecnologia.getListCmtBasicaExtMgl().isEmpty()) {

                        for (CmtBasicaExtMgl cmtBasicaExtMgl
                                : tecnologia.getListCmtBasicaExtMgl()) {
                            if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                    equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                    && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {
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
     * Función que obtiene valores de tipo de tecnología
     *
     * @author Juan David Hernandez
     */
    public void establecerCheckAutomaticoATecnologias() {
        try {
            if (tecnologiaBasicaList != null && !tecnologiaBasicaList.isEmpty()) {
                for (CmtBasicaMgl tecnologiaBasica : tecnologiaBasicaList) {
                    tecnologiaBasica.setSincronizaRr(establecerCheckAutomaticoSincronizaRr(tecnologiaBasica));
                    //si la tecnologia no sincroniza con RR queda en false el check                   
                    tecnologiaBasica.setNodoTecnologia(deshabilitarCheckEnvioRr(tecnologiaBasica));
                    // se Habilita el campo nap si es de tipo ftth
                    if (tecnologiaBasica.getBasicaId() != null && tecnologiaBasica.getBasicaId().intValue() == 2314) {
                        tecnologiaBasica.setNapFTTH(true);
                    } else {
                        tecnologiaBasica.setNapFTTH(false);
                    }
                }
            }
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en establecer selección de check automatico" + ex.getMessage(), ex, LOGGER);
        }
    }

        /**
     * Función deshabilita el check de envió de creacion de hhpp a RR
     *
     * @author Juan David Hernandez
     * @param tecnologia que se desea validar si se envia o no a RR
     */
    public boolean deshabilitarCheckEnvioRr(CmtBasicaMgl tecnologia) {
         boolean result = false;
        try {           
            if (tecnologiaBasicaList != null && !tecnologiaBasicaList.isEmpty()
                    && habilitarRR) {
                    //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                    if (tecnologia.getListCmtBasicaExtMgl() != null
                            && !tecnologia.getListCmtBasicaExtMgl().isEmpty()) {

                        for (CmtBasicaExtMgl cmtBasicaExtMgl
                                : tecnologia.getListCmtBasicaExtMgl()) {
                            if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                    equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                    && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {
                                result = true;
                                return result;
                            }
                        }
                    }
            }
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en deshabilitar selección de check automatico" + ex.getMessage(), ex, LOGGER);
            return result;
        }
        return result; 
    }

    
    public String obtenerNodoCoberturaTecnologiaSeleccionada(AddressService addressService,
            Solicitud solicitudDthSeleccionada) {
        String nodoCobertura = "";
        try {
            if (addressService != null && solicitudDthSeleccionada != null
                    && solicitudDthSeleccionada.getTecnologiaId() != null
                    && solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp() != null
                    && !solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp().isEmpty()) {
                
                if (solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp().equals(Constant.DTH)) {
                    nodoCobertura = addressService.getNodoDth();

                } else if (solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp().equals(Constant.FIBRA_FTTTH)) {
                    nodoCobertura = addressService.getNodoFtth();

                } else if (solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp().equals(Constant.HFC_BID)) {
                    nodoCobertura = addressService.getNodoUno();

                } else if (solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp().equals(Constant.HFC_UNI)) {
                    nodoCobertura = addressService.getNodoDos();

                } else if (solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp().equals(Constant.FIBRA_OP_GPON)) {
                    nodoCobertura = addressService.getNodoTres();

                } else if (solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp().equals(Constant.LTE_INTERNET)) {
                    nodoCobertura = addressService.getNodoWifi();

                } else if (solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp().equals(Constant.BTS_MOVIL)) {
                    nodoCobertura = addressService.getNodoMovil();
                    
                } else if (solicitudDthSeleccionada.getTecnologiaId().getIdentificadorInternoApp().equals(Constant.FIBRA_OP_UNI)) {
                   nodoCobertura = addressService.getNodoZonaUnifilar();
            }
    }
        } catch (Exception ex) {
            nodoCobertura = "";
            FacesUtil.mostrarMensajeError("Error en establecer Nodo Cobertura para reactivacion" + ex.getMessage(), ex, LOGGER);
        }
        return nodoCobertura;

    }

    /**
     * Función utilizada para obtener el listado de tipos de solicitudes desde
     * la base de datos
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void obtenerListadoTipoSolicitud() throws ApplicationException {
        try {
            tipoSolicitudList
                    = parametrosCallesFacade.findByTipo("TIPO_SOLICITUD");
        } catch (ApplicationException e) {
            String msn = "Error al obtener listado de tipos de solicitud ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al obtener listado de tipos de solicitud ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
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
            if(!Objects.isNull(solicitudDthSeleccionada)){
                cmtTiempoSolicitudMglToCreate.setTiempoEspera(getTiempo(solicitudDthSeleccionada.getFechaIngreso(),
                        new Date()));
            }
        } catch (Exception e) {
            String msn = "Error al consulta tiempo de espera de la solicitud ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que obtiene los tiempos de la traza de la solicitud seleccionada
     *
     * @author Juan David Hernandez
     */
    public void obtenerTrackBySolicitud() {
        try {
            cmtTiempoSolicitudMgl = cmtTiempoSolicitudMglFacadeLocal
                    .findTiemposBySolicitud(idSolicitudDthSeleccionada);
            if (cmtTiempoSolicitudMgl != null
                    && StringUtils.equalsIgnoreCase(cmtTiempoSolicitudMgl.getTiempoEspera(), DEFAULT_TIME)) {
                cmtTiempoSolicitudMgl.setTiempoEspera(cmtTiempoSolicitudMglToCreate.getTiempoEspera());
                cmtTiempoSolicitudMgl.setTiempoTotal(getTiempo(solicitudDthSeleccionada.getFechaIngreso(), new Date()));
            }
        } catch (ApplicationException e) {
            String msn = "Error al obtener registro de track de la solicitud ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al obtener registro de track de la solicitud ";
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
            Solicitud solicitudCreated = new Solicitud();
            /*El tiempo de espera se asigna en un método 
             * independiente al momento de ingresar a la
             * gestión de la solicitud. */

            if (cmtTiempoSolicitudMgl != null && cmtTiempoSolicitudMgl.getTiempoSolicitudId() != null) {

                cmtTiempoSolicitudMglToCreate.setTiempoSolicitudId(cmtTiempoSolicitudMgl.getTiempoSolicitudId());
                cmtTiempoSolicitudMglToCreate.setFechaCreacion(cmtTiempoSolicitudMgl.getFechaCreacion());
                cmtTiempoSolicitudMglToCreate.setUsuarioCreacion(cmtTiempoSolicitudMgl.getUsuarioCreacion());
                cmtTiempoSolicitudMglToCreate.setPerfilCreacion(cmtTiempoSolicitudMgl.getPerfilCreacion());
                cmtTiempoSolicitudMglToCreate.setTiempoSolicitud(cmtTiempoSolicitudMgl.getTiempoSolicitud());
                cmtTiempoSolicitudMglToCreate.setEstadoRegistro(1);
                solicitudCreated.setIdSolicitud(idSolicitudDthSeleccionada);
                //asigna tiempo transcurrido en la gestión (cronómetro en pantalla)
                cmtTiempoSolicitudMglToCreate.setTiempoGestion(timeSol);
                /*asigna el tiempo total entre la creación de la solicitud y el 
             * momento en el que se realiza la gestión*/
                cmtTiempoSolicitudMglToCreate.setTiempoTotal(getTiempo(solicitudDthSeleccionada.getFechaIngreso(),
                        new Date()));
                /*asigna el objeto solicitud con el cual se relacionaran los 
             tiempos guardados */
                cmtTiempoSolicitudMglToCreate.setSolicitudObj(solicitudCreated);
                /*Realiza la actualización de los tiempos de la solicitud en la base
             de datos. */

                cmtTiempoSolicitudMglToCreate.setArchivoSoporte(solicitudDthSeleccionada.getTiempoSolicitudMgl()
                        .getArchivoSoporte());

            } else {     
               
                //asigna tiempo transcurrido en la solicitud (cronómetro en pantalla)
                cmtTiempoSolicitudMglToCreate.setTiempoSolicitud("00:02:30");
                /*asigna el objeto solicitud con el cual se relacionaran los tiempos guardados */
                cmtTiempoSolicitudMglToCreate.setSolicitudObj(solicitudDthSeleccionada);
                /*Realiza la actualización de los tiempos de la solicitud en la base de datos.*/
                cmtTiempoSolicitudMglToCreate.setTiempoEspera(DEFAULT_TIME);
                cmtTiempoSolicitudMglToCreate.setTiempoGestion(timeSol);
                cmtTiempoSolicitudMglToCreate.setTiempoTotal(getTiempo(solicitudDthSeleccionada.getFechaIngreso(),new Date()));
                cmtTiempoSolicitudMglToCreate.setArchivoSoporte("NA");
                cmtTiempoSolicitudMglToCreate.setFechaCreacion(solicitudDthSeleccionada.getFechaIngreso());
                cmtTiempoSolicitudMglToCreate.setUsuarioCreacion(solicitudDthSeleccionada.getUsuario());
                cmtTiempoSolicitudMglToCreate.setPerfilCreacion(perfilVt);
                //guarda en la base de datos el track de tiempos.
                cmtTiempoSolicitudMglFacadeLocal.setUser(usuarioVt, perfilVt);
                cmtTiempoSolicitudMglToCreate = cmtTiempoSolicitudMglFacadeLocal.create(cmtTiempoSolicitudMglToCreate);
                
            }




        } catch (Exception e) {
            String msn = "Error al realizar actualización del registro de tiempos.";
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

    /**
     * Función que permite navegar entre Tabs en la pantalla de gestión de la
     * solicitud
     *
     * @author Juan David Hernandez
     * @param sSeleccionado
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
                    obtenerTrackBySolicitud();
                    showTrack();
                    break;
                case NOTAS:
                    selectedTab = "NOTAS";
                    findNotasBySolicitud();
                    showNotas();
                    break;
            }
        } catch (Exception e) {
            String msn = "Error al realizar el cambio de Tab";
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

    /**
     * Función que guarda una nota y la asocia a la solicitud
     *
     * @author Juan David Hernandez
     */
    public void guardarNota() {
        try {
            if (validarCamposNota()) {
                cmtNotasSolicitudVtMgl.setSolicitud(solicitudDthSeleccionada);
                cmtNotasSolicitudVtMgl.setTipoNotaObj(tipoNotaSelected);
                notasSolicitudVtMglFacadeLocal.setUser(usuarioVt, perfilVt);
                cmtNotasSolicitudVtMgl = notasSolicitudVtMglFacadeLocal
                        .crearNotSol(cmtNotasSolicitudVtMgl);
                if (cmtNotasSolicitudVtMgl != null
                        && cmtNotasSolicitudVtMgl.getNotasId() != null) {
                    findNotasBySolicitud();
                    tipoNotaSelected = new CmtBasicaMgl();
                    cmtNotasSolicitudVtMgl = new CmtNotasSolicitudVtMgl();
                    mostrarMensajePopup(FacesMessage.SEVERITY_INFO, "Nota agregada correctamente.");
                }
                cambiarTab("NOTAS");
            }

        } catch (ApplicationException ex) {
            String msn = "Error al guardar una nota";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage() , ex, LOGGER);
        } catch (Exception ex) {
            String msn = "Error al guardar una nota";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage() , ex, LOGGER);
        }
    }

    /**
     * Función que guarda un comentario a una nota y la asocia a la solicitud
     *
     * @author Juan David Hernandez
     */
    public void guardarComentarioNota() {
        try {

            if (validarComentarioNota()) {
                CmtNotasSolicitudDetalleVtMgl notaComentarioMgl
                        = new CmtNotasSolicitudDetalleVtMgl();
                notaComentarioMgl.setNota(notaComentarioStr);
                notaComentarioMgl.setNotaSolicitud(cmtNotasSolicitudVtMgl);
                notasSolicitudDetalleVtMglFacadeLocal.setUser(usuarioVt, perfilVt);
                notaComentarioMgl = notasSolicitudDetalleVtMglFacadeLocal
                        .create(notaComentarioMgl);
                if (notaComentarioMgl.getNotasDetalleId() != null) {
                    notaComentarioStr = "";
                    tipoNotaSelected = new CmtBasicaMgl();
                    cmtNotasSolicitudVtMgl = new CmtNotasSolicitudVtMgl();
                    mostrarMensajePopup(FacesMessage.SEVERITY_INFO, "Comentario añadido correctamente.");
                }
                cambiarTab("NOTAS");
            }
        } catch (ApplicationException ex) {
            String msn = "Error al guardar un comentario a la nota";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage() , ex, LOGGER);
        } catch (Exception ex) {
            String msn = "Error al guardar un comentario a la nota";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage() , ex, LOGGER);
        }
    }

    /**
     * Función que que los datos necesarios para guardar una nota esten
     * ingresados
     *
     * @author Juan David Hernandez
     * @return 
     */
    public boolean validarCamposNota() {
        try {
            if (cmtNotasSolicitudVtMgl == null) {
                String msn = "Ha ocurrido un error intentando guardar una nota."
                        + " Intente más tarde por favor.";
                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                return false;
            } else {
                if (cmtNotasSolicitudVtMgl.getDescripcion() == null
                        || cmtNotasSolicitudVtMgl.getDescripcion().isEmpty()) {
                    String msn = "Ingrese una descripción a la nota por favor.";
                    mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                    return false;
                } else {
                    if (tipoNotaSelected.getBasicaId() == null) {
                        String msn = "Ingrese un tipo de nota por favor.";
                        mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                        return false;
                    } else {
                        if (cmtNotasSolicitudVtMgl.getNota() == null
                                || cmtNotasSolicitudVtMgl.getNota().isEmpty()) {
                            String msn = "Ingrese la nota que desea guardar"
                                    + " por favor.";
                            mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            String msn = "Error validando campos al agregar nota  ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
            return false;
        }
    }

    /**
     * Función que que los datos necesarios para guardar una nota esten
     * ingresados
     *
     * @author Juan David Hernandez
     * @return 
     */
    public boolean validarComentarioNota() {
        try {
            if (notaComentarioStr == null || notaComentarioStr.isEmpty()) {
                String msn = "Ingrese el comentario de la nota por favor."
                        + " Intente más tarde por favor.";
                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msn);
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            String msn = "Error validando campos al agregar nota  ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
            return false;
        }

    }

    /**
     * Función que guarda un comentario a una nota y la asocia a la solicitud
     *
     * @author Juan David Hernandez
     * @param nota
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
     * Función que obtiene las notas de la solicitud creada.
     *
     * @author Juan David Hernandez
     */
    public void findNotasBySolicitud() {
        try {
            notasSolicitudList = notasSolicitudVtMglFacadeLocal
                    .findNotasBySolicitudId(solicitudDthSeleccionada.getIdSolicitud());
        } catch (ApplicationException e) {
            String msn = "Error al obtener tiempos de la solicitud ";
            FacesUtil.mostrarMensajeError(msn+ e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al obtener tiempos de la solicitud ";
            FacesUtil.mostrarMensajeError(msn+ e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que copia la respuesta en el textArea de la respuesta
     *
     * @author Juan David Hernandez
     */
    public void copiarMensajeRespuesta() {
        try {
            String fecha = formatter.format(new Date());

            if (solicitudDthSeleccionada.getRptGestion()
                    .equalsIgnoreCase("CAMBIO DE DIRECCION REALIZADO")
                    && solicitudDthSeleccionada.getCambioDir()
                    .equals(Constant.RR_DIR_CAMB_HHPP_1)) {
                asignarMsgRespuestaCambioDir(fecha);
                return;
            }

            if (solicitudDthSeleccionada.getRptGestion()
                    .equalsIgnoreCase("CAMBIO DE DIRECCION NO REALIZADO")
                    && solicitudDthSeleccionada.getCambioDir()
                    .equals(Constant.RR_DIR_CAMB_HHPP_1)) {
                asignarMsgRespuestaCambioDirNoRealizado(fecha);
                return;
            }

            if (solicitudDthSeleccionada.getRptGestion()
                    .equalsIgnoreCase("REACTIVACION HHPP REALIZADO")
                    && solicitudDthSeleccionada.getCambioDir()
                    .equals(Constant.RR_DIR_REAC_HHPP_3)) {
                asignarMsgRespuestaReactivacionHhpp(fecha);
                return;
            }

            if (solicitudDthSeleccionada.getRptGestion()
                    .equalsIgnoreCase("REACTIVACION HHPP NO REALIZADO")
                    && solicitudDthSeleccionada.getCambioDir()
                    .equals(Constant.RR_DIR_REAC_HHPP_3)) {
                asignarMsgRespuestaReactivacionHhppNoRealizado(fecha);
                return;
            }

            if (solicitudDthSeleccionada.getRptGestion()
                    .equalsIgnoreCase("FACTIBILIDAD NEGATIVA")
                    && solicitudDthSeleccionada.getCambioDir()
                    .equals(Constant.VALIDACION_COBERTURA_12)) {
                asignarMsgRespuestaFactibilidadNegativa(fecha);
                return;
            }

            if (solicitudDthSeleccionada.getRptGestion()
                    .equalsIgnoreCase("FACTIBILIDAD POSITIVA")
                    && solicitudDthSeleccionada.getCambioDir()
                    .equals(Constant.VALIDACION_COBERTURA_12)) {
                asignarMsgFactibilidadPositiva(fecha);
                return;
            }

            if (solicitudDthSeleccionada.getRptGestion()
                    .equalsIgnoreCase("PREDIO NO UBICADO")
                    && solicitudDthSeleccionada.getCambioDir()
                    .equals(Constant.VALIDACION_COBERTURA_12)) {
                asignarMsgRespuestaPredioNoUbicado(fecha);
                return;
            }

            asignarMsgRespuestaDireccionEstandarizada(fecha);
        } catch (Exception e) {
            String msn = "Error al copiando respuesta a observaciones";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        }
    }

    private void asignarMsgRespuestaDireccionEstandarizada(String fecha) {
        String msn = "Direcci&oacute;nn estandarizada: [Calle: "
                + direccionRREntity.getCalle() + " Placa: "
                + direccionRREntity.getNumeroUnidad()
                + " Apartamento: "
                + direccionRREntity.getNumeroApartamento()
                + " ] Centro Poblado: "
                + StringUtils.stripAccents(centroPobladoGpo.getGpoNombre()) + " Ciudad "
                + StringUtils.stripAccents((ciudadGpo.getGpoNombre()));

        solicitudDthSeleccionada.setRespuesta(fecha + ": "
                + msn + " - "
                + solicitudDthSeleccionada.getRptGestion());
    }

    private void asignarMsgRespuestaPredioNoUbicado(String fecha) {
        String msn = "Se gestiono solicitud de validacion cobertura para Predio no ubicado: "
                + "Direcci&oacute;n: [Calle: "
                + direccionRREntity.getCalle() + " Placa: "
                + direccionRREntity.getNumeroUnidad()
                + " Apartamento: "
                + direccionRREntity.getNumeroApartamento()
                + " ] Centro Poblado: "
                + StringUtils.stripAccents(centroPobladoGpo.getGpoNombre()) + " Ciudad "
                + StringUtils.stripAccents((ciudadGpo.getGpoNombre()));

        solicitudDthSeleccionada.setRespuesta(fecha + ": "
                + msn + " - "
                + solicitudDthSeleccionada.getRptGestion());
    }

    private void asignarMsgFactibilidadPositiva(String fecha) {
        String msn = "Se gestiono solicitud de validacion cobertura para Factibilidad positiva: "
                + "Direcci&oacute;n: [Calle: "
                + direccionRREntity.getCalle() + " Placa: "
                + direccionRREntity.getNumeroUnidad()
                + " Apartamento: "
                + direccionRREntity.getNumeroApartamento()
                + " ] Centro Poblado: "
                + StringUtils.stripAccents(centroPobladoGpo.getGpoNombre()) + " Ciudad "
                + StringUtils.stripAccents((ciudadGpo.getGpoNombre()));

        solicitudDthSeleccionada.setRespuesta(fecha + ": "
                + msn + " - "
                + solicitudDthSeleccionada.getRptGestion());
    }

    private void asignarMsgRespuestaFactibilidadNegativa(String fecha) {
        String msn = "Se gestiono solicitud de validacion cobertura para Factibilidad negativa: "
                + "Direcci&oacute;n: [Calle: "
                + direccionRREntity.getCalle() + " Placa: "
                + direccionRREntity.getNumeroUnidad()
                + " Apartamento: "
                + direccionRREntity.getNumeroApartamento()
                + " ] Centro Poblado: "
                + centroPobladoGpo.getGpoNombre() + " Ciudad "
                + ciudadGpo.getGpoNombre();

        solicitudDthSeleccionada.setRespuesta(fecha + ": "
                + msn + " - "
                + solicitudDthSeleccionada.getRptGestion());
    }

    private void asignarMsgRespuestaReactivacionHhppNoRealizado(String fecha) {
        String msn = "NO se realiza Activación del HHPP en RR: "
                + "Dirección estandarizada: [Calle: "
                + direccionRREntity.getCalle() + " Placa: "
                + direccionRREntity.getNumeroUnidad()
                + " Apartamento: "
                + direccionRREntity.getNumeroApartamento()
                + " ] Centro Poblado: "
                + centroPobladoGpo.getGpoNombre() + " Ciudad "
                + ciudadGpo.getGpoNombre();

        solicitudDthSeleccionada.setRespuesta(fecha + ": "
                + msn + " - "
                + solicitudDthSeleccionada.getRptGestion());
    }

    private void asignarMsgRespuestaReactivacionHhpp(String fecha) {
        String msn = "Se realiza Activación del HHPP"
                + "Dirección estandarizada: [Calle: "
                + direccionRREntity.getCalle() + " Placa: "
                + direccionRREntity.getNumeroUnidad()
                + " Apartamento: "
                + direccionRREntity.getNumeroApartamento()
                + " ] Centro Poblado: "
                + centroPobladoGpo.getGpoNombre() + " Ciudad: "
                + ciudadGpo.getGpoNombre();

        solicitudDthSeleccionada.setRespuesta(fecha + ": "
                + msn + " - "
                + solicitudDthSeleccionada.getRptGestion());
    }

    private void asignarMsgRespuestaCambioDirNoRealizado(String fecha) {
        String msn = "No se realiza actualización de la dirección"
                + " actual [Calle:"
                + solicitudDthSeleccionada.getStreetName() + " Placa: "
                + solicitudDthSeleccionada.getHouseNumber()
                + " Apartamento: "
                + solicitudDthSeleccionada.getAparmentNumber()
                + " ] Centro Poblado: "
                + centroPobladoGpo.getGpoNombre() + " Ciudad: "
                + " a la dirección estandarizada: [Calle: "
                + direccionRREntity.getCalle() + " Placa: "
                + direccionRREntity.getNumeroUnidad()
                + " Apartamento: "
                + direccionRREntity.getNumeroApartamento()
                + " ] Centro Poblado: "
                + centroPobladoGpo.getGpoNombre();

        solicitudDthSeleccionada.setRespuesta(fecha + ": " + msn
                + " - " + solicitudDthSeleccionada.getRptGestion());
    }

    private void asignarMsgRespuestaCambioDir(String fecha) {
        String msn = "Se realiza actualización de la dirección"
                + " actual: [Calle:"
                + solicitudDthSeleccionada.getStreetName() + " Placa: "
                + solicitudDthSeleccionada.getHouseNumber()
                + " Apartamento: "
                + solicitudDthSeleccionada.getAparmentNumber()
                + " ] Centro Poblado: "
                + centroPobladoGpo.getGpoNombre() + " Ciudad: "
                + ciudadGpo.getGpoNombre()
                + " a la dirección estandarizada: [Calle: "
                + direccionRREntity.getCalle() + " Placa: "
                + direccionRREntity.getNumeroUnidad()
                + " Apartamento: "
                + direccionRREntity.getNumeroApartamento()
                + " ] Centro Poblado: "
                + centroPobladoGpo.getGpoNombre();

        solicitudDthSeleccionada.setRespuesta(fecha + ": " + " - "
                + msn + " - "
                + solicitudDthSeleccionada.getRptGestion());
    }

    /**
     * Función que copia la respuesta en el textArea de la respuesta de cambio
     * de estrato
     *
     * @author Victor Bocanegra
     */
    public void copiarMensajeRespuestaCambioEstrato() {
        if (StringUtils.isBlank(solicitudDthSeleccionada.getRptGestion())
                || SELECCIONE_UNO.equals(solicitudDthSeleccionada.getRptGestion())) {
            solicitudDthSeleccionada.setRespuesta("");
            return;
        }

        String fecha = formatter.format(new Date());
        String mensaje = "La dirección: [" + cmtDireccionDetalladaMgl.getDireccionTexto() + "] "
                    + "cambia del estrato: [" + estratoAntiguo.getNombreBasica() + "] al estrato:"
                    + " [" + estratoNuevo.getNombreBasica() + "] ";
        solicitudDthSeleccionada.setRespuesta(fecha + ": " + mensaje + " - "
                + solicitudDthSeleccionada.getRptGestion());
    }

    /**
     * Función redirecciona a la pantalla del listado de solicitudes . y
     * desbloquea la solicitud si no fue gestionada.
     *
     * @author Juan David Hernandez
     */
    public void goBackSolicitudDthList() {
        try {

            if (solicitudDthSeleccionada.getEstado() != null
                    && solicitudDthSeleccionada.getEstado().equalsIgnoreCase(Constant.ESTADO_SOL_VERIFICADO)) {
                solicitudFacadeLocal.desbloquearDisponibilidadGestionVerificada(solicitudDthSeleccionada.getIdSolicitud());
            } else {
                desbloquearDisponibilidadGestionDth(solicitudDthSeleccionada);
            }

            SolicitudSessionBean solicitudSessionBean
                    = JSFUtil.getSessionBean(SolicitudSessionBean.class);

            int page = solicitudSessionBean.getPaginaActual();

            GestionSolicitudDthBean gestionSolicitudDthBean
                    = (GestionSolicitudDthBean) JSFUtil.getBean("gestionSolicitudDthBean");
            FacesUtil.navegarAPagina("/view/MGL/VT/gestion/DTH/"
                    + "gestionSolicitudDthListado.jsf");
            gestionSolicitudDthBean.listInfoByPage(page);

        } catch (ApplicationException e) {
            String msn = "Error al ir a pantalla de listado de solicitudes";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al ir a pantalla de listado de solicitudes";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función redirecciona a la pantalla del estado de solicitud.
     *
     * @author Juan David Hernandez
     */
    public void goBackEstadoSolicitud() {
        try {
            FacesUtil.navegarAPagina("/view/MGL/VT/solicitudes/estadoSolicitud/estadoSolicitudHhpp.jsf");
        } catch (ApplicationException e) {
            String msn = "Error al ir a pantalla de listado estado de solicitud";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al ir a pantalla de listado estado de solicitud";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que desbloquea la disponibilidad de las solicitudes
     *
     * @param solicitudDthSeleccionada
     *
     * @author Juan David Hernandez
     *
     */
    public void desbloquearDisponibilidadGestionDth(Solicitud solicitudDthSeleccionada) {
        try {
            if (solicitudDthSeleccionada != null) {
                solicitudFacadeLocal.
                        desbloquearDisponibilidadGestionDth(solicitudDthSeleccionada.getIdSolicitud());
            } else {
                String msnError = "Ha ocurrido un error seleccionando la"
                        + "solicitud, intente de nuevo por favor.";
                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msnError);
            }
        } catch (Exception e) {
            String msn = "Ha ocurrido un error seleccionando la"
                    + " solicitud, intente de nuevo por favor.";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función se utiliza para limpiar los campos del formulario
     *
     * @author Juan David Hernandez
     */
    public void limpiarFormulario() {
        solicitudDthSeleccionada.setRptGestion("");
        solicitudDthSeleccionada.setRespuesta("");
        nodoCercano = "";
        nodoCobertura = "";
        nodoGestion = "";
        rrNodoDth = "";
        unidadesPredio = false;
        unidadesPredioModificadas = false;
        unidadConflictoPanel = false;
        unidadList = new ArrayList<>();
        unidadModificadaList = new ArrayList<>();
    }

    public void copiarNodo() {
        nodoGestion = nodoCobertura;
        rrNodoDth = "";
        nodoCercano = "";
    }

    public void copiarNodoCercano() {
        nodoGestion = nodoCercano;
        nodoCobertura = "";
        rrNodoDth = "";
    }

    public void copiarNodoDth() {
        nodoGestion = rrNodoDth;
        nodoCobertura = "";
        nodoCercano = "";
    }

    public void copiarAnterior() {
        nodoGestion = nodoAnterior;
        nodoCobertura = "";
        nodoCercano = "";
        rrNodoDth = "";
    }

    /**
     * Función que obtiene los tiempos de la traza de las solicitud
     * selesccionada
     *
     * @author Juan David Hernandez
     */
     public void showSolicitud() {
         if(!Objects.isNull(solicitudDthSeleccionada)){
             if (StringUtils.equalsIgnoreCase(solicitudDthSeleccionada.getCambioDir(), Constant.CAMBIO_ESTRATO_2)) {
                 showSolicitudCambioEstrato = true;
             } else if (StringUtils.equalsIgnoreCase(solicitudDthSeleccionada.getCambioDir(), Constant.VALIDACION_COBERTURA_12)) {
                 showValidacionCobertura = true;
             } else {
                 showSolicitud = true;
             }
         }
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
        showSolicitudCambioEstrato = false;
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
        showSolicitudCambioEstrato = false;
        showTrack = false;
        showNotas = true;
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
            int maxResult;
            if ((firstResult + filasPag) > unidadAuxiliarList.size()) {
                maxResult = unidadAuxiliarList.size();
            } else {
                maxResult = (firstResult + filasPag);
            }
            unidadList = new ArrayList<>();
            for (int i = firstResult; i < maxResult; i++) {
                unidadList.add(unidadAuxiliarList.get(i));
            }

        } catch (Exception e) {
            String msn = "Error en lista de paginación";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
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
        } catch (Exception ex) {
            String msn = "Error direccionando a la primera página";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage() , ex, LOGGER);
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
        } catch (Exception ex) {
            String msn = "Error direccionando a la página anterior";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage() , ex, LOGGER);
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
        } catch (NumberFormatException ex) {
            String msn = "Error direccionando a página";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage() , ex, LOGGER);
        } catch (Exception ex) {
            String msn = "Error direccionando a página";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage() , ex, LOGGER);
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
        } catch (Exception ex) {
            String msn = "Error direccionando a la siguiente página";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage() , ex, LOGGER);
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
        } catch (Exception ex) {
            String msn = "Error direccionando a la última página";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage() , ex, LOGGER);
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados de las
     * unidades asociadas al predio
     *
     * @author Juan David Hernandez
     * @return 
     */
    public int getTotalPaginas() {
        try {
            int totalPaginas;
            //obtener la lista original para conocer su tamaño total                 
            int pageSol = unidadAuxiliarList.size();
            totalPaginas = (int) ((pageSol % filasPag != 0)
                    ? (pageSol / filasPag) + 1 : pageSol / filasPag);
            return totalPaginas;
        } catch (Exception ex) {
            String msn = "Error direccionando a la primera página";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage() , ex, LOGGER);
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario en
     * los resultados.
     *
     * @author Juan David Hernandez
     * @return  {@link String} Retorna la información de la pagina actual.
     */
    public String getPageActual() {
        paginaList = new ArrayList<>();
        int totalPaginas = getTotalPaginas();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaList.add(i);
        }
        String pageActual = actual + " de " + totalPaginas;

        if (numPagina == null) {
            numPagina = "1";
        }
        numPagina = String.valueOf(actual);
        return pageActual;
    }

    public void cargarInformacionCambioEstrato(Solicitud solicitudCam) throws ApplicationException {
        try {
            BigDecimal dirDetalladaId = Optional.ofNullable(solicitudCam).map(Solicitud::getDireccionDetallada)
                    .map(CmtDireccionDetalladaMgl::getDireccionDetalladaId)
                    .orElse(null);

            if (Objects.isNull(dirDetalladaId)) {
                throw new ApplicationException("No se pudo obtener la dirección detallada en el cambio de estrato");
            }

            cmtDireccionDetalladaMgl = cmtDireccionDetalleMglFacadeLocal.buscarDireccionIdDireccion(dirDetalladaId);
            UbicacionMgl ubicacionMgl = Optional.ofNullable(cmtDireccionDetalladaMgl)
                    .map(CmtDireccionDetalladaMgl::getDireccion)
                    .map(DireccionMgl::getUbicacion)
                    .orElse(null);
            BigDecimal ubiId = Optional.ofNullable(ubicacionMgl)
                    .map(UbicacionMgl::getUbiId).orElse(null);
            GeograficoPoliticoMgl politicoMgl = Optional.ofNullable(ubicacionMgl)
                    .map(UbicacionMgl::getGpoIdObj).orElse(null);

            if (Objects.isNull(ubiId) || Objects.isNull(politicoMgl)) {
                throw new ApplicationException("Ocurrio un error cargando la informacion de la solicitud: la dirección no tiene "
                        + "geografico en la ubicación  ");
            }

            centroPobladoGpo = cmtDireccionDetalladaMgl.getDireccion().getUbicacion().getGpoIdObj();
            ciudadGpo = geograficoPoliticoMglFacadeLocal.findById(centroPobladoGpo.getGeoGpoId());

            if (Objects.isNull(ciudadGpo) || Objects.isNull(ciudadGpo.getGpoId()) || Objects.isNull(ciudadGpo.getGeoGpoId())) {
                throw new ApplicationException("Ocurrio un error cargando la informacion de la solicitud: la dirección no tiene "
                        + "geografico en la ubicación  ");
            }

            departamentoGpo = geograficoPoliticoMglFacadeLocal.findById(ciudadGpo.getGeoGpoId());
            asignarEstratoAntiguo(solicitudCam);
            //carga la lista de links de documentos
            tecArchivosCambioEstratos = tecArcCamEstratoFacadeLocal.findUrlsByIdSolicitud(solicitudDthSeleccionada);
            //combo de resultado de gestion cambio estrato
            resultGestionCambioEs = parametrosCallesFacadeLocal.findByTipo(Constant.RESULTADO_GESTION_CE);
            //respuesta actual
            estratoNuevo = buscarBasicaEstrato(solicitudCam.getEstratoNuevo() == null ? "-1" : solicitudCam.getEstratoNuevo());
        } catch (Exception ex) {
            LOGGER.error("cargarInformacionCambioEstrato() -> " + ex.getMessage());
            throw new ApplicationException("Error en cargarInformacionCambioEstrato");
        }
    }

    /**
     * Asigna el estrato antiguo a la solicitud.
     *
     * @param solicitudCam {@link Solicitud}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    private void asignarEstratoAntiguo(Solicitud solicitudCam) throws ApplicationException {
        if (cmtDireccionDetalladaMgl.getSubDireccion() != null) {
            if (cmtDireccionDetalladaMgl.getSubDireccion().getSdiEstrato() != null) {
                solicitudCam.setEstratoAntiguo(String.valueOf(cmtDireccionDetalladaMgl.getSubDireccion().getSdiEstrato()));
                estratoAntiguo = buscarBasicaEstrato(String.valueOf(cmtDireccionDetalladaMgl.getSubDireccion().getSdiEstrato()));
            } else {
                estratoAntiguo.setNombreBasica("ESTRATO NO GEOREFERENCIADO");
            }
            return;
        }

        if (cmtDireccionDetalladaMgl.getDireccion().getDirEstrato() != null) {
            estratoAntiguo = buscarBasicaEstrato(String.valueOf(cmtDireccionDetalladaMgl.getDireccion().getDirEstrato()));
        } else {
            estratoAntiguo.setNombreBasica("ESTRATO NO GEOREFERENCIADO");
        }
    }

    public String primeraVerificacion() {

        try {
            if (estratoAntiguo.getCodigoBasica().equalsIgnoreCase(estratoNuevo.getCodigoBasica())) {
                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, "El estrato nuevo debe ser diferente al estrato antiguo");
                return null;
            }

            if (solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(SELECCIONE_UNO)) {
                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, "Debe seleccionar un resultado de gestión");
                return null;
            }

            if (!tecArchivosCambioEstratos.isEmpty() && solicitudDthSeleccionada.getRptGestion().
                    equalsIgnoreCase(Constant.CAMBIO_DE_ESTRATO_REALIZADO)
                    && solicitudDthSeleccionada.getTipoDocSoporteCamEstarto()
                    .equalsIgnoreCase(SELECCIONE_UNO)) {
                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, "Debe seleccionar un tipo de documento soporte");
                return null;
            }

            solicitudDthSeleccionada.setEstado(Constant.ESTADO_SOL_VERIFICADO);
            solicitudDthSeleccionada.setUsuarioVerificador(usuarioLogin.getNombre());

            if (solicitudDthSeleccionada.getRptGestion().
                    equalsIgnoreCase(Constant.CAMBIO_DE_ESTRATO_REALIZADO)) {
                solicitudDthSeleccionada.setResultadoVerificacionCamEstrato(Constant.CAMBIO_ESTRATO_ACEPTADO);
            } else {
                solicitudDthSeleccionada.setResultadoVerificacionCamEstrato(Constant.CAMBIO_ESTRATO_RECHAZADO);
            }

            solicitudDthSeleccionada.setDisponibilidadGestion("0");
            String archivosAdjuntos = tecArchivosCambioEstratos.stream()
                    .map(tecArchivosCambioEstrato -> retornaNombreArchivo(tecArchivosCambioEstrato.getUrlArchivoSoporte()))
                    .collect(Collectors.joining("|"));
            solicitudDthSeleccionada.setDocSoporteCamEstarto(archivosAdjuntos);
            String subject = "Su numero de VT es :" + solicitudDthSeleccionada.getIdSolicitud() + " y a sido verificada";

            String mensajePantalla = "SU SOLICITUD DE CAMBIO DE ESTRATO NÚMERO:  "
                    + solicitudDthSeleccionada.getIdSolicitud() + "  "
                    + "HA SIDO VERIFICADA, ESTA GESTIÓN AUN NO SE HA FINALIZADO.";

            solicitudDthSeleccionada.setFechaModificacion(new Date());
            solicitudDthSeleccionada = solicitudFacadeLocal.update(solicitudDthSeleccionada);

            if (solicitudDthSeleccionada.getEstado().equalsIgnoreCase(Constant.ESTADO_SOL_VERIFICADO)) {
                mostrarMensajePopup(FacesMessage.SEVERITY_INFO, mensajePantalla);
            } else {
                mostrarMensajePopup(FacesMessage.SEVERITY_ERROR, "Ocurrio un error actualizando la solicitud");
                return null;
            }

            try {
                ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
                ParametrosMgl correoServer = parametrosMglManager.findByAcronimoName(
                        ParametrosMerEnum.MAIL_SMTPSERVER.getAcronimo());
                ParametrosMgl mailFrom = parametrosMglManager.findByAcronimoName(
                        ParametrosMerEnum.MAIL_FROM.getAcronimo());
                MailSenderDTO mailSenderDTO = new MailSenderDTO();
                mailSenderDTO.setHostSmtp(correoServer.getParValor());
                mailSenderDTO.setSenderAddress(mailFrom.getParValor());
                mailSenderDTO.setToAddress(usuarioLogin.getEmail());
                mailSenderDTO.setCcAddress(solicitudDthSeleccionada.getCorreo());
                mailSenderDTO.setBccAddress("");
                mailSenderDTO.setSubject(subject);
                mailSenderDTO.setHtmlFormat(true);
                mailSenderDTO.setBody(new StringBuffer(solicitudDthSeleccionada.getRespuesta()));
                mailSenderDTO.setDebug(true);
                mailSenderDTO.setUseAuth(false);
                mailSenderDTO.setUseTLS(false);
                co.com.claro.mgl.utils.MailSender.send(mailSenderDTO);

            } catch (Exception e) {
                LOGGER.error("Error Enviando Mail" + e.getMessage());
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error SALE_149F " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error SALE_149F " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String retornaNombreArchivo(String url) {
        String nombre;
        String urlValida;

        if (url.length() == (url.lastIndexOf('/') + 1)) {
            urlValida = url.substring(0, url.length() - 1);
            nombre = urlValida.substring(urlValida.lastIndexOf('/') + 1);
        } else {
            nombre = url.substring(url.lastIndexOf('/') + 1);
        }
        LOGGER.info("Path: " + url + " -- File: " + nombre);
        return nombre;
    }

    /**
     * Función que realiza la gestión de la solicitud Cambio de estrato
     *
     * @author Victor Bocanegra
     */
    public void gestionarSolicitudCambioEstrato() {

        if (esSolicitudIncompleta()){
            LOGGER.debug("La solicitud no se puede finalizar, debido a que no cumple con los requisitos necesarios.");
            return;
        }

        if (solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.CAMBIO_DE_ESTRATO_REALIZADO)) {
            gestionarSolicitudCambioEstratoRealizado();
            return;
        }

        try {
            solicitudDthSeleccionada.setEstado(Constant.ESTADO_SOL_FINALIZADO);
            solicitudDthSeleccionada.setUsuario(usuarioVt);
            solicitudDthSeleccionada.setFechaModificacion(new Date());
            solicitudDthSeleccionada.setFechaCancelacion(new Date());
            solicitudDthSeleccionada.setIdUsuarioGestion(new BigDecimal(usuarioLogin.getCedula()));
            solicitudDthSeleccionada.setUsuarioGestionador(usuarioLogin.getNombre() != null
                    ? usuarioLogin.getNombre() : USUARIO_NO_VALIDO);
            solicitudFacadeLocal.update(solicitudDthSeleccionada);
            String mensaje = "Su numero de VT es: " + solicitudDthSeleccionada.getIdSolicitud() + " y ha sido gestionada.";
            mostrarMensajePopup(FacesMessage.SEVERITY_INFO, mensaje);
        } catch (ApplicationException ex) {
            mostrarMensajePopup(FacesMessage.SEVERITY_ERROR, ex.getMessage());
            String msg = "Ocurrió un error al momento de ejecutar el método ' "
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
        } catch (Exception ex) {
            mostrarMensajePopup(FacesMessage.SEVERITY_ERROR, ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método ' "
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
        }
    }

    private void gestionarSolicitudCambioEstratoRealizado() {
        String respuestaGestion = "";
        try {
            solicitudDthSeleccionada.setIdUsuarioGestion(new BigDecimal(usuarioLogin.getCedula()));
            solicitudDthSeleccionada.setUsuarioGestionador(usuarioLogin.getNombre() != null
                    ? usuarioLogin.getNombre() : USUARIO_NO_VALIDO);
            respuestaGestion = solicitudFacadeLocal.gestionarCambioEstrato(solicitudDthSeleccionada,
                            estratoNuevo.getCodigoBasica(), usuarioVt, perfilVt);
            String error = respuestaGestion.substring(0, 5);
            String warning = respuestaGestion.substring(0, 7);

            if (error.equalsIgnoreCase("Error")) {
                mostrarMensajePopup(FacesMessage.SEVERITY_ERROR, respuestaGestion);
                return;
            }

            if (warning.equalsIgnoreCase("WARNING")) {
                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, respuestaGestion);
                return;
            }

            mostrarMensajePopup(FacesMessage.SEVERITY_INFO, respuestaGestion);
        } catch (ApplicationException ex) {
            mostrarMensajePopup(FacesMessage.SEVERITY_WARN, respuestaGestion);
            String msg = "Ocurrió un error al momento de ejecutar el método ' "
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);

        } catch (Exception ex) {
            mostrarMensajePopup(FacesMessage.SEVERITY_WARN, respuestaGestion);
            String msg = "Se produjo un error al momento de ejecutar el método ' "
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);

        }
    }

    private boolean esSolicitudIncompleta() {
        Objects.requireNonNull(solicitudDthSeleccionada, "El valor de la solicitud es nulo.");
        if (estratoAntiguo.getCodigoBasica().equalsIgnoreCase(estratoNuevo.getCodigoBasica())) {
            String mensaje = "El estrato nuevo debe ser diferente al estrato antiguo por favor.";
            mostrarMensajePopup(FacesMessage.SEVERITY_WARN, mensaje);
            return true;
        }

        if (StringUtils.isBlank(solicitudDthSeleccionada.getRptGestion())) {
            mostrarMensajePopup(FacesMessage.SEVERITY_WARN, "Debe seleccionar un resultado de gestión");
            return true;
        }

        if (!tecArchivosCambioEstratos.isEmpty() && solicitudDthSeleccionada.getRptGestion().
                equalsIgnoreCase(Constant.CAMBIO_DE_ESTRATO_REALIZADO) && solicitudDthSeleccionada.getTipoDocSoporteCamEstarto().
                equalsIgnoreCase(SELECCIONE_UNO)) {
            mostrarMensajePopup(FacesMessage.SEVERITY_WARN, "Debe seleccionar un tipo de documento soporte");
            return true;
        }

        validarRol();
        asignarMarcasAgregadasHhpp(marcasAgregadasHhppList);

        //JDHT
        if (StringUtils.isNotBlank(solicitudDthSeleccionada.getUsuarioVerificador())
                && solicitudDthSeleccionada.getUsuarioVerificador().equalsIgnoreCase(usuarioLogin.getNombre())) {
            String mensaje = "LA SOLICITUD DE CAMBIO DE ESTRATO: " + solicitudDthSeleccionada.getIdSolicitud() + "  "
                    + "SOLO PUEDE SER FINALIZADA POR UN USUARIO DIFERENTE AL QUE LA VERIFICÓ.";
            mostrarMensajePopup(FacesMessage.SEVERITY_WARN, mensaje);
            return true;
        }

        if (!gestionarSolicitud) {
            String mensaje = "LA SOLICITUD DE CAMBIO DE ESTRATO NUMERO: " + solicitudDthSeleccionada.getIdSolicitud() + "  "
                    + "NO PUEDE SER FINALIZADA POR EL USUARIO, NO TIENE LOS PERMISOS SUFICIENTES.";
            mostrarMensajePopup(FacesMessage.SEVERITY_WARN, mensaje);
            return true;
        }
        return false;
    }

    public void irGestionOt() {

        try {
            if (otCreada != null
                    && otCreada.getOtHhppId() != null) {
                // Instacia Bean de Session para obtener la solicitud seleccionada
                OtHhppMglSessionBean otHhppMglSessionBean
                        = JSFUtil.getSessionBean(OtHhppMglSessionBean.class);
                otHhppMglSessionBean.setOtHhppMglSeleccionado(otCreada);
                otHhppMglSessionBean.setDetalleOtHhpp(true);
                FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                                + "editarOtHhpp.jsf");

            } else {
                String msnError = "Ha ocurrido un error seleccionando la"
                        + " ot, intente de nuevo por favor.";
                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msnError);
            }

        } catch (ApplicationException e) {
            String msn = "Error al redireccionar a editar a la Ot";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al redireccionar a editar a la Ot";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }

    }

    public void seleccionaNodoCercano(CmtBasicaMgl cmtBasicaMgl) {
        try {
            if (cmtBasicaMgl == null || tecnologiaBasicaList == null) {
                String msg = "Ha ocurrido un error seleccionando el nodo cercano,"
                        + " por favor intente de nuevo ";
                mostrarMensajePopup(FacesMessage.SEVERITY_ERROR, msg);
                return;
            }

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
        } catch (ApplicationException ex) {
            String msg = "Error al consultar nodos cercanos "
                    + "asociados a una tecnologia" + ex.getMessage();
            FacesUtil.mostrarMensajeError(msg , ex, LOGGER);
        } catch (Exception ex) {
            String msg = "Ocurrió un error al consultar nodos cercanos "
                    + "asociados a una tecnologia" + ex.getMessage();
            FacesUtil.mostrarMensajeError(msg , ex, LOGGER);
        }
    }

    /**
     * Funcion que se encarga de carga la lista de estrato posibles para el
     * cambio de estrato
     *
     * @author Jonathan Peña
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    public void cargarListaEstratos() throws ApplicationException {
        try {
            CmtTipoBasicaMgl cmtTipoBasica = cmtTipoBasicaMgl.findByCodigoInternoApp(co.com.claro.mgl.utils.Constant.TIPO_BASICA_ESTRATO_HHPP);
            listaEstratos = cmtBasicaMglFacade.findByTipoBasica(cmtTipoBasica);
        } catch (Exception e) {
            LOGGER.error("cargarListaEstratos() -> " + e.getMessage());
            throw new ApplicationException("Error al obtener el listado de estratos para HHPP ");
        }
    }

    /**
     * Funcion que se encarga de actualizar el estrato en el mensaje de
     * respuesta
     *
     * @author Jonathan Peña
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    public void actualizarMensajeCambioEstrato() throws ApplicationException {
        estratoNuevo = buscarBasicaEstrato(estratoNuevo.getCodigoBasica());
        solicitudDthSeleccionada.setEstratoNuevo(estratoNuevo.getCodigoBasica());
        String mensaje = "La dirección:  " + cmtDireccionDetalladaMgl.getDireccionTexto()
                + "cambia del estrato:  " + estratoAntiguo.getNombreBasica() + " al estrato: "
                + estratoNuevo.getNombreBasica();
        solicitudDthSeleccionada.setRespuesta(mensaje);
    }

    /**
     * Funcion que se encarga buscar el codigo de la basica del estrato anterior
     *
     * @author Jonathan Peña
     * @return {@link CmtBasicaMgl}
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public CmtBasicaMgl buscarBasicaEstrato(String estrato) throws ApplicationException {
        try {
            CmtTipoBasicaMgl cmtTipoBasica = cmtTipoBasicaMgl.
                    findByCodigoInternoApp(co.com.claro.mgl.utils.Constant.TIPO_BASICA_ESTRATO_HHPP);
            return cmtBasicaMglFacade.findByBasicaCode(estrato, cmtTipoBasica.getTipoBasicaId());
        } catch (ApplicationException e) {
            String mensaje = "Error al obtener el listado de estratos para HHPP " + e;
            FacesUtil.mostrarMensajeError(mensaje + e.getMessage() , e, LOGGER);
            return null;
        } catch (Exception e) {
            String mensaje = "Error al obtener el listado de estratos para HHPP " + e;
            FacesUtil.mostrarMensajeError(mensaje + e.getMessage() , e, LOGGER);
            return null;
        }
    }

    /**
     * Funcion que permite validar que el usuario actual tenga el rol para
     * gestionar la solicitud
     *
     * @author Jonathan Peña
     */
    public void validarRol() {
        try {
            String formulario = "GESTIONSOLICITUDDTH";
            String opcion = "GESTIONAR";
            CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade = new CmtOpcionesRolMglFacade();
            gestionarSolicitud = ValidacionUtil.validarVisualizacion(formulario, opcion, cmtOpcionesRolMglFacade, securityLogin);
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en validarRol" + ex.getMessage() , ex, LOGGER);
            gestionarSolicitud = false;
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en validarRol" + ex.getMessage() , ex, LOGGER);
            gestionarSolicitud = false;
        }
    }

    /**
     * Función utilizada para obtener el listado de tipos de documento soporte
     * desde la base de datos
     *
     * @author Victor Bocanegra
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void obtenerTipoDocSoporte() throws ApplicationException {
        try {
            lstDocumentosSoporte = parametrosCallesFacade.findByTipo(Constant.TIPOS_DOCUMENTO_SOPORTE);
        } catch (Exception e) {
            LOGGER.error("obtenerTipoDocSoporte() -> " + e.getMessage());
            throw new ApplicationException("Error al realizar consulta para obtener listado de tipo de documentos ");
        }
    }

    /**
     * Funcion que permite la obtencion de todos los posibles subscriptores de
     * una direccion
     *
     * @author Jonathan Peña
     * @return {@link String}
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public String buscarSubscriptores(CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl) throws ApplicationException {
        StringBuilder infoSuscriptores = new StringBuilder();

        if (cmtDireccionDetalladaMgl == null) {
            return StringUtils.EMPTY;
        }

        if (cmtDireccionDetalladaMgl.getSubDireccion() != null) {
            List<HhppMgl> hhppList1 = hhppMglFacadeLocal.findHhppSubDireccion(
                    cmtDireccionDetalladaMgl.getSubDireccion().getSdiId());
            for (HhppMgl hhpp : hhppList1) {
                if (hhpp != null && hhpp.getSuscriptor() != null
                        && !infoSuscriptores.toString().contains(hhpp.getSuscriptor())) {
                    infoSuscriptores.append(hhpp.getSuscriptor()).append(", ");
                }
            }
            return infoSuscriptores.toString();
        }

        if (cmtDireccionDetalladaMgl.getDireccion() != null) {
            List<HhppMgl> hhppList2 = hhppMglFacadeLocal
                    .findHhppByDirId(cmtDireccionDetalladaMgl.getDireccion().getDirId());
            for (HhppMgl hhpp : hhppList2) {
                if (hhpp != null && hhpp.getSuscriptor() != null
                        && !infoSuscriptores.toString().contains(hhpp.getSuscriptor())) {
                    infoSuscriptores.append(hhpp.getSuscriptor()).append(", ");
                }
            }
        }

        return infoSuscriptores.toString();
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

    private boolean validarTecnologiaARR() {
        String mensajeError = "";    
                      
        if (solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase("VERIFICACION AGENDADA") 
                || solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_HHPP_CREADO)) {
            for (CmtBasicaMgl tec : tecnologiaBasicaList) {
                if (tec.isSincronizaRr() && tec.getNodoGestion().isEmpty()) {
                    mensajeError = "La tecnología que viaja a RR, no tiene un nodo válido, "
                            + "por favor ingrese un nodo válido para la tecnologia que viaja a RR";
                    break;
                }
            }

            if (!mensajeError.isEmpty()) {
                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, mensajeError);
                return false;
            }
        }
        
        return true;
    }

    /**
     * Asigna el tipo de vivienda seleccionada.
     *
     * @param apartamento {@link String}
     * @return {@link String}
     */
    public String obtenerReglaTipoVivienda(String apartamento) {
        DireccionRRManager direccionRRManager = new DireccionRRManager(false);
        tipoViviendaSeleccionada = direccionRRManager.obtenerTipoEdificio(apartamento, usuarioVt, null);
        return tipoViviendaSeleccionada;
    }
     
      public String obtenerTipoViviendaHhpp(Solicitud solilcitudActual) {
          if(solilcitudActual != null && solilcitudActual.getHhppMgl() != null 
                  && solilcitudActual.getHhppMgl().getThhId() != null &&
                  !solilcitudActual.getHhppMgl().getThhId().isEmpty()){
              tipoViviendaSeleccionada = solilcitudActual.getHhppMgl().getThhId();
          }
        return tipoViviendaSeleccionada;
    }

    /**
     * Funcion que se encarga de carga la lista de estrato posibles para el
     * cambio de estrato
     *
     * @author Juan David Hernandez
     */
    public void agregarEtiquetaSeleccionada() {
        try {
            if (etiquetaAgregarSeleccionada == null || etiquetaAgregarSeleccionada.equals(BigDecimal.ZERO)) {
                String msnError = "Debe seleccionar una etiqueta para ser agregada al listado.";
                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msnError);
                return;
            }

            if (!validarAgregarEtiquetaSeleccionada(solicitudDthSeleccionada.getHhppMgl(),
                    hhppList, etiquetaAgregarSeleccionada)) {
                return;
            }

            boolean marcaRepetida = false;

            if (CollectionUtils.isNotEmpty(marcasAgregadasHhppList)) {
                for (MarcasMgl marcasMgl : marcasAgregadasHhppList) {
                    if (etiquetaAgregarSeleccionada.equals(marcasMgl.getMarId())) {
                        String msnError = "La etiqueta que intenta agregar ya se encuentra en el listado. Verifique por favor.";
                        mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msnError);
                        marcaRepetida = true;
                        break;
                    }
                }
            }

            //si la marca ya se encuentra en el listado return a la pantalla.
            if (marcaRepetida) {
                return;
            }

            MarcasMgl marcaSeleccionada = marcasMglFacadeLocal.findById(etiquetaAgregarSeleccionada);
            if (marcaSeleccionada == null) {
                String msnError = "No fue posible encontrar la marca "
                        + "seleccionada para ser agregada al listado de marcas.";
                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msnError);
                return;
            }

            marcasAgregadasHhppList.add(marcaSeleccionada);
        } catch (Exception e) {
            String mensaje = "Error al agregar marca a listado " + e;
            FacesUtil.mostrarMensajeError(mensaje + e.getMessage(), e, LOGGER);
        }
    }

    public boolean validarAgregarEtiquetaSeleccionada(HhppMgl hhppMgl, List<HhppMgl> hhppAsociadosList, 
            BigDecimal etiquetaSeleccionada){
        try {
            //listado de hhpp asociados al hhpp
            if(hhppAsociadosList != null && !hhppAsociadosList.isEmpty() && etiquetaSeleccionada != null
                    && !etiquetaSeleccionada.equals(BigDecimal.ZERO)){
                
                for (HhppMgl hhppMgl1 : hhppAsociadosList) {
                    if(hhppMgl1.getListMarcasHhpp() != null && !hhppMgl1.getListMarcasHhpp().isEmpty()){
                        for (MarcasHhppMgl marcasHhppMgl : hhppMgl1.getListMarcasHhpp()) {
                            if(marcasHhppMgl.getMarId().equals(etiquetaSeleccionada)){
                                  String msnError = "La marca que desea agrega ya pertenece al hhpp.";
                                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msnError);
                                return false;
                            }
                        }
                    }
                } 
                //hhpp actual seleccionado
                if (hhppMgl != null && hhppMgl.getListMarcasHhpp() != null
                        && !hhppMgl.getListMarcasHhpp().isEmpty() && etiquetaSeleccionada != null
                    && !etiquetaSeleccionada.equals(BigDecimal.ZERO)) {
                    
                    for (MarcasHhppMgl marcasHhppMgl : hhppMgl.getListMarcasHhpp()) {
                        if (marcasHhppMgl.getMarId().getMarId().equals(etiquetaSeleccionada)) {
                            String msnError = "La marca que desea agrega ya pertenece al hhpp.";
                            mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msnError);
                            return false;
                        }
                    }
                }
   
            }else{
                if (hhppMgl != null && hhppMgl.getListMarcasHhpp() != null
                        && !hhppMgl.getListMarcasHhpp().isEmpty() && etiquetaSeleccionada != null
                    && !etiquetaSeleccionada.equals(BigDecimal.ZERO)) {
                    
                    for (MarcasHhppMgl marcasHhppMgl : hhppMgl.getListMarcasHhpp()) {
                        if (marcasHhppMgl.getMarId().getMarId().equals(etiquetaSeleccionada)) {
                            String msnError = "La marca que desea agrega ya pertenece al hhpp.";
                            mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msnError);
                            return false;
                        }
                    }
                }
            }
            return true;
            
        } catch (Exception e) {
            return false;
        }
    }
    public boolean validarNodoRedFO(Solicitud solicitudDthSelected) {
        BigDecimal subDir = null;
        boolean siRedFO = false;
        List<HhppMgl> listHhpp = null;
        if (solicitudDthSeleccionada.getDireccionDetallada() != null
                && solicitudDthSeleccionada.getDireccionDetallada().getSubDireccion() != null
                && solicitudDthSeleccionada.getDireccionDetallada().getSubDireccion().getSdiId() != null) {
            subDir = solicitudDthSeleccionada.getDireccionDetallada().getSubDireccion().getSdiId();
        }
        if (solicitudDthSeleccionada.getDireccionDetallada() != null) {
            try {
                listHhpp = hhppMglFacadeLocal.findHhppByDirIdSubDirId(solicitudDthSelected.getDireccionDetallada().getDireccionId(), subDir);
            } catch (ApplicationException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }

        if (listHhpp == null) {
            return false;
        }

        for (HhppMgl hhpp : listHhpp) {
            if (hhpp.getNodId().getNodTipo().getIdentificadorInternoApp() != null
                    && !hhpp.getNodId().getNodTipo().getIdentificadorInternoApp().isEmpty()
                    && hhpp.getNodId().getNodTipo().getIdentificadorInternoApp().equalsIgnoreCase(Constant.RED_FO)) {
                siRedFO = true;
                break;

            }
        }
        return siRedFO;

    }
    
     /**
     * Funcion que se encarga de carga la lista de estrato posibles para el
     * cambio de estrato
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void eliminarEtiquetaSeleccionada() throws ApplicationException {
        try {
            if (etiquetaEliminarSeleccionada != null && !etiquetaEliminarSeleccionada.equals(BigDecimal.ZERO)) {

                MarcasMgl marcaSeleccionada = marcasMglFacadeLocal.findById(etiquetaEliminarSeleccionada);
                if (marcaSeleccionada != null) {
                    marcasAgregadasHhppList.remove(marcaSeleccionada);
                } else {
                    String msnError = "No fue posible encontrar la marca "
                            + "seleccionada para ser agregada al listado de marcas.";
                    mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msnError);
                }

            } else {
                String msnError = "Debe seleccionar la etiqueta que desea eliminar del listado.";
                mostrarMensajePopup(FacesMessage.SEVERITY_WARN, msnError);
            }
        } catch (ApplicationException e) {
            String mensaje = "Error al eliminar marca de listado " + e;
            FacesUtil.mostrarMensajeError(mensaje + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String mensaje = "Error al eliminar marca de listado " + e;
            FacesUtil.mostrarMensajeError(mensaje + e.getMessage() , e, LOGGER);
        }

    }   
    
    public boolean validarGestionValCobertura() {
        final String GESTIONVALCOBERTURA = "GESTIONVALCOBERTURA";
        return validarEdicion(GESTIONVALCOBERTURA);
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

    public Solicitud getSolicitudDthSeleccionada() {
        return solicitudDthSeleccionada;
    }

    public void setSolicitudDthSeleccionada(Solicitud solicitudDthSeleccionada) {
        this.solicitudDthSeleccionada = solicitudDthSeleccionada;
    }

    public List<RrRegionales> getRegionalList() {
        return regionalList;
    }

    public void setRegionalList(List<RrRegionales> regionalList) {
        this.regionalList = regionalList;
    }

    public List<RrCiudades> getCiudadesList() {
        return ciudadesList;
    }

    public void setCiudadesList(List<RrCiudades> ciudadesList) {
        this.ciudadesList = ciudadesList;
    }

    public List<ParametrosCalles> getResultGestionDth() {
        return resultGestionDth;
    }

    public void setResultGestionDth(List<ParametrosCalles> resultGestionDth) {
        this.resultGestionDth = resultGestionDth;
    }

    public List<ParametrosCalles> getTipoSolicitudList() {
        return tipoSolicitudList;
    }

    public void setTipoSolicitudList(List<ParametrosCalles> tipoSolicitudList) {
        this.tipoSolicitudList = tipoSolicitudList;
    }

    public boolean isShowGestionSolicitudList() {
        return showGestionSolicitudList;
    }

    public void setShowGestionSolicitudList(boolean showGestionSolicitudList) {
        this.showGestionSolicitudList = showGestionSolicitudList;
    }

    public boolean isShowGestionSolicitud() {
        return showGestionSolicitud;
    }

    public void setShowGestionSolicitud(boolean showGestionSolicitud) {
        this.showGestionSolicitud = showGestionSolicitud;
    }

    public boolean isShowTrack() {
        return showTrack;
    }

    public void setShowTrack(boolean showTrack) {
        this.showTrack = showTrack;
    }

    public String getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(String selectedTab) {
        this.selectedTab = selectedTab;
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

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDeltaTiempo() {
        return deltaTiempo;
    }

    public void setDeltaTiempo(String deltaTiempo) {
        this.deltaTiempo = deltaTiempo;
    }

    public String getTmpFin() {
        return tmpFin;
    }

    public void setTmpFin(String tmpFin) {
        this.tmpFin = tmpFin;
    }

    public String getTimeSol() {
        return timeSol;
    }

    public void setTimeSol(String timeSol) {
        this.timeSol = timeSol;
    }

    public BigDecimal getIdSolicitudDthSeleccionada() {
        return idSolicitudDthSeleccionada;
    }

    public void setIdSolicitudDthSeleccionada(BigDecimal idSolicitudDthSeleccionada) {
        this.idSolicitudDthSeleccionada = idSolicitudDthSeleccionada;
    }

    public CmtTiempoSolicitudMgl getCmtTiempoSolicitudMgl() {
        return cmtTiempoSolicitudMgl;
    }

    public void setCmtTiempoSolicitudMgl(CmtTiempoSolicitudMgl cmtTiempoSolicitudMgl) {
        this.cmtTiempoSolicitudMgl = cmtTiempoSolicitudMgl;
    }

    public CmtTipoSolicitudMgl getCmtTipoSolicitudMgl() {
        return cmtTipoSolicitudMgl;
    }

    public void setCmtTipoSolicitudMgl(CmtTipoSolicitudMgl cmtTipoSolicitudMgl) {
        this.cmtTipoSolicitudMgl = cmtTipoSolicitudMgl;
    }

    public CmtTiempoSolicitudMgl getCmtTiempoGestionDth() {
        return cmtTiempoGestionDth;
    }

    public void setCmtTiempoGestionDth(CmtTiempoSolicitudMgl cmtTiempoGestionDth) {
        this.cmtTiempoGestionDth = cmtTiempoGestionDth;
    }

    public Date getDateInicioCreacion() {
        return dateInicioCreacion;
    }

    public void setDateInicioCreacion(Date dateInicioCreacion) {
        this.dateInicioCreacion = dateInicioCreacion;
    }

    public boolean isShowNotas() {
        return showNotas;
    }

    public void setShowNotas(boolean showNotas) {
        this.showNotas = showNotas;
    }

    public boolean isSolicitudGestionada() {
        return solicitudGestionada;
    }

    public void setSolicitudGestionada(boolean solicitudGestionada) {
        this.solicitudGestionada = solicitudGestionada;
    }

    public List<CmtBasicaMgl> getTecnologiaBasicaList() {
        return tecnologiaBasicaList;
    }

    public void setTecnologiaBasicaList(List<CmtBasicaMgl> tecnologiaBasicaList) {
        this.tecnologiaBasicaList = tecnologiaBasicaList;
    }

    public List<CmtBasicaMgl> getTipoAccionSolicitudBasicaList() {
        return tipoAccionSolicitudBasicaList;
    }

    public void setTipoAccionSolicitudBasicaList(List<CmtBasicaMgl> tipoAccionSolicitudBasicaList) {
        this.tipoAccionSolicitudBasicaList = tipoAccionSolicitudBasicaList;
    }

    public List<CmtBasicaMgl> getTipoNotaList() {
        return tipoNotaList;
    }

    public void setTipoNotaList(List<CmtBasicaMgl> tipoNotaList) {
        this.tipoNotaList = tipoNotaList;
    }

    public GeograficoPoliticoMgl getCiudadGpo() {
        return ciudadGpo;
    }

    public void setCiudadGpo(GeograficoPoliticoMgl ciudadGpo) {
        this.ciudadGpo = ciudadGpo;
    }

    public List<GeograficoPoliticoMgl> getCentroPobladoList() {
        return centroPobladoList;
    }

    public void setCentroPobladoList(List<GeograficoPoliticoMgl> centroPobladoList) {
        this.centroPobladoList = centroPobladoList;
    }

    public List<CmtBasicaMgl> getEstratoList() {
        return estratoList;
    }

    public void setEstratoList(List<CmtBasicaMgl> estratoList) {
        this.estratoList = estratoList;
    }

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    public String getNombreRegional() {
        return nombreRegional;
    }

    public void setNombreRegional(String nombreRegional) {
        this.nombreRegional = nombreRegional;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public CmtNotasSolicitudVtMgl getCmtNotasSolicitudVtMgl() {
        return cmtNotasSolicitudVtMgl;
    }

    public void setCmtNotasSolicitudVtMgl(CmtNotasSolicitudVtMgl cmtNotasSolicitudVtMgl) {
        this.cmtNotasSolicitudVtMgl = cmtNotasSolicitudVtMgl;
    }

    public String getNotaComentarioStr() {
        return notaComentarioStr;
    }

    public void setNotaComentarioStr(String notaComentarioStr) {
        this.notaComentarioStr = notaComentarioStr;
    }

    public List<CmtNotasSolicitudVtMgl> getNotasSolicitudList() {
        return notasSolicitudList;
    }

    public void setNotasSolicitudList(List<CmtNotasSolicitudVtMgl> notasSolicitudList) {
        this.notasSolicitudList = notasSolicitudList;
    }

    public CmtBasicaMgl getTipoNotaSelected() {
        return tipoNotaSelected;
    }

    public void setTipoNotaSelected(CmtBasicaMgl tipoNotaSelected) {
        this.tipoNotaSelected = tipoNotaSelected;
    }

    public DireccionRREntity getDireccionRREntity() {
        return direccionRREntity;
    }

    public void setDireccionRREntity(DireccionRREntity direccionRREntity) {
        this.direccionRREntity = direccionRREntity;
    }

    public DireccionMgl getDireccionMglActual() {
        return direccionMglActual;
    }

    public void setDireccionMglActual(DireccionMgl direccionMglActual) {
        this.direccionMglActual = direccionMglActual;
    }

    public List<String> getNodosCoberturaList() {
        return nodosCoberturaList;
    }

    public void setNodosCoberturaList(List<String> nodosCoberturaList) {
        this.nodosCoberturaList = nodosCoberturaList;
    }

    public String getNodoCercano() {
        return nodoCercano;
    }

    public void setNodoCercano(String nodoCercano) {
        this.nodoCercano = nodoCercano;
    }

    public String getNodoGestion() {
        return nodoGestion;
    }

    public void setNodoGestion(String nodoGestion) {
        this.nodoGestion = nodoGestion;
    }

    public String getNodoCobertura() {
        return nodoCobertura;
    }

    public void setNodoCobertura(String nodoCobertura) {
        this.nodoCobertura = nodoCobertura;
    }

    public List<NodoDto> getListaNodosCercanosArray() {
        return listaNodosCercanosArray;
    }

    public void setListaNodosCercanosArray(List<NodoDto> listaNodosCercanosArray) {
        this.listaNodosCercanosArray = listaNodosCercanosArray;
    }

    public List<UnidadStructPcml> getUnidadList() {
        return unidadList;
    }

    public void setUnidadList(List<UnidadStructPcml> unidadList) {
        this.unidadList = unidadList;
    }

    public boolean isUnidadesPredio() {
        return unidadesPredio;
    }

    public void setUnidadesPredio(boolean unidadesPredio) {
        this.unidadesPredio = unidadesPredio;
    }

    public CmtTiempoSolicitudMgl getCmtTiempoSolicitudMglToCreate() {
        return cmtTiempoSolicitudMglToCreate;
    }

    public void setCmtTiempoSolicitudMglToCreate(CmtTiempoSolicitudMgl cmtTiempoSolicitudMglToCreate) {
        this.cmtTiempoSolicitudMglToCreate = cmtTiempoSolicitudMglToCreate;
    }

    public List<NodoMgl> getNodosDthList() {
        return nodosDthList;
    }

    public void setNodosDthList(List<NodoMgl> nodosDthList) {
        this.nodosDthList = nodosDthList;
    }

    public String getRrNodoDth() {
        return rrNodoDth;
    }

    public void setRrNodoDth(String rrNodoDth) {
        this.rrNodoDth = rrNodoDth;
    }

    public List<UnidadStructPcml> getUnidadModificadaList() {
        return unidadModificadaList;
    }

    public void setUnidadModificadaList(List<UnidadStructPcml> unidadModificadaList) {
        this.unidadModificadaList = unidadModificadaList;
    }

    public boolean isUnidadesPredioModificadas() {
        return unidadesPredioModificadas;
    }

    public void setUnidadesPredioModificadas(boolean unidadesPredioModificadas) {
        this.unidadesPredioModificadas = unidadesPredioModificadas;
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

    public String getBarrioGeo() {
        return barrioGeo;
    }

    public void setBarrioGeo(String barrioGeo) {
        this.barrioGeo = barrioGeo;
    }

    public String getDirAntiguaFormatoIgac() {
        return dirAntiguaFormatoIgac;
    }

    public void setDirAntiguaFormatoIgac(String dirAntiguaFormatoIgac) {
        this.dirAntiguaFormatoIgac = dirAntiguaFormatoIgac;
    }

    public String getComplementoDireccion() {
        return complementoDireccion;
    }

    public void setComplementoDireccion(String complementoDireccion) {
        this.complementoDireccion = complementoDireccion;
    }

    public boolean isCambioDireccionPanel() {
        return cambioDireccionPanel;
    }

    public void setCambioDireccionPanel(boolean cambioDireccionPanel) {
        this.cambioDireccionPanel = cambioDireccionPanel;
    }

    public List<NodoMgl> getNodoAnteriorList() {
        return nodoAnteriorList;
    }

    public void setNodoAnteriorList(List<NodoMgl> nodoAnteriorList) {
        this.nodoAnteriorList = nodoAnteriorList;
    }

    public String getNodoAnterior() {
        return nodoAnterior;
    }

    public void setNodoAnterior(String nodoAnterior) {
        this.nodoAnterior = nodoAnterior;
    }

    public List<UnidadStructPcml> getUnidadConflictoList() {
        return unidadConflictoList;
    }

    public void setUnidadConflictoList(List<UnidadStructPcml> unidadConflictoList) {
        this.unidadConflictoList = unidadConflictoList;
    }

    public boolean isUnidadConflictoPanel() {
        return unidadConflictoPanel;
    }

    public void setUnidadConflictoPanel(boolean unidadConflictoPanel) {
        this.unidadConflictoPanel = unidadConflictoPanel;
    }

    public String getAccionConflicto() {
        return accionConflicto;
    }

    public void setAccionConflicto(String accionConflicto) {
        this.accionConflicto = accionConflicto;
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

    public boolean isShowSolicitudCambioEstrato() {
        return showSolicitudCambioEstrato;
    }

    public void setShowSolicitudCambioEstrato(boolean showSolicitudCambioEstrato) {
        this.showSolicitudCambioEstrato = showSolicitudCambioEstrato;
    }

    public GeograficoPoliticoMgl getDepartamentoGpo() {
        return departamentoGpo;
    }

    public void setDepartamentoGpo(GeograficoPoliticoMgl departamentoGpo) {
        this.departamentoGpo = departamentoGpo;
    }

    public CmtDireccionDetalladaMgl getCmtDireccionDetalladaMgl() {
        return cmtDireccionDetalladaMgl;
    }

    public void setCmtDireccionDetalladaMgl(CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl) {
        this.cmtDireccionDetalladaMgl = cmtDireccionDetalladaMgl;
    }

    public List<TecArchivosCambioEstrato> getTecArchivosCambioEstratos() {
        return tecArchivosCambioEstratos;
    }

    public void setTecArchivosCambioEstratos(List<TecArchivosCambioEstrato> tecArchivosCambioEstratos) {
        this.tecArchivosCambioEstratos = tecArchivosCambioEstratos;
    }

    public List<ParametrosCalles> getResultGestionCambioEs() {
        return resultGestionCambioEs;
    }

    public void setResultGestionCambioEs(List<ParametrosCalles> resultGestionCambioEs) {
        this.resultGestionCambioEs = resultGestionCambioEs;
    }

    public AddressService getAddressService() {
        return addressService;
    }

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public List<String> getListaNodosCobertura() {
        return listaNodosCobertura;
    }

    public void setListaNodosCobertura(List<String> listaNodosCobertura) {
        this.listaNodosCobertura = listaNodosCobertura;
    }

    public SubDireccionMgl getSubDireccionMglActual() {
        return subDireccionMglActual;
    }

    public void setSubDireccionMglActual(SubDireccionMgl subDireccionMglActual) {
        this.subDireccionMglActual = subDireccionMglActual;
    }

    public List<UnidadStructPcml> getUnidadConflictoTmpList() {
        return unidadConflictoTmpList;
    }

    public void setUnidadConflictoTmpList(List<UnidadStructPcml> unidadConflictoTmpList) {
        this.unidadConflictoTmpList = unidadConflictoTmpList;
    }

    public List<CmtBasicaMgl> getListaEstratos() {
        return listaEstratos;
    }

    public void setListaEstratos(List<CmtBasicaMgl> listaEstratos) {
        this.listaEstratos = listaEstratos;
    }

    public List<CmtBasicaMgl> getTipoTecnologiaBasicaList() {
        return tipoTecnologiaBasicaList;
    }

    public void setTipoTecnologiaBasicaList(List<CmtBasicaMgl> tipoTecnologiaBasicaList) {
        this.tipoTecnologiaBasicaList = tipoTecnologiaBasicaList;
    }

    public boolean isDetalleSolicitud() {
        return detalleSolicitud;
    }

    public void setDetalleSolicitud(boolean detalleSolicitud) {
        this.detalleSolicitud = detalleSolicitud;
    }

    public boolean isHhppExiste() {
        return hhppExiste;
    }

    public void setHhppExiste(boolean hhppExiste) {
        this.hhppExiste = hhppExiste;
    }

    public List<ParametrosCalles> getLstDocumentosSoporte() {
        return lstDocumentosSoporte;
    }

    public void setLstDocumentosSoporte(List<ParametrosCalles> lstDocumentosSoporte) {
        this.lstDocumentosSoporte = lstDocumentosSoporte;
    }

    /**
     * Determina si se puede mostrar campos de documentos.
     *
     * @param cambioDir {@link String}
     */
    private void puedeMostrarCampos(String cambioDir) {
        mostrarCampoDocumento = cambioDir.equals(Constant.RR_DIR_CREA_HHPP_0)
                || cambioDir.equals(Constant.RR_DIR_CAMB_HHPP_1)
                || cambioDir.equals(Constant.RR_DIR_CAMBIO_ESTRATO_2);
    }
}
