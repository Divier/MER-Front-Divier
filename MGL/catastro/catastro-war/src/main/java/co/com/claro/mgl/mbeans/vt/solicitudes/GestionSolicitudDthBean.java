
package co.com.claro.mgl.mbeans.vt.solicitudes;

import co.com.claro.mer.exception.ExceptionServBean;
import co.com.claro.mer.utils.constants.ConstantsSolicitudHhpp;
import co.com.claro.mer.utils.enums.MessageSeverityEnum;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.SolicitudFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionDetalleMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoSolicitudMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.RrCiudades;
import co.com.claro.mgl.jpa.entities.RrRegionales;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.UbicacionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Juan David Hernandez
 */
@Log4j2
@ManagedBean(name = "gestionSolicitudDthBean")
@ViewScoped
public class GestionSolicitudDthBean implements Serializable {

    private static final String GESTIONAR_SOLICITUD = "Gestionar Solicitud";
    private String usuarioVT = null;
    @Getter
    @Setter
    private int perfilVt = 0;
    private String cedulaUsuarioVT = null;
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    @Getter
    @Setter
    private BigDecimal idSolicitudDth;
    @Getter
    @Setter
    private String direccion;
    @Getter
    @Setter
    private Solicitud solicitudDthSeleccionada;
    @Getter
    @Setter
    private List<Solicitud> solicitudesDthList;
    @Getter
    @Setter
    private List<Solicitud> solicitudesDthAuxList;
    @Getter
    @Setter
    private List<RrRegionales> regionalList;
    @Getter
    @Setter
    private List<RrCiudades> ciudadesList;
    @Getter
    @Setter
    private List<ParametrosCalles> resultGestionDth;
    @Getter
    @Setter
    private List<ParametrosCalles> tipoSolicitudList;
    @Getter
    @Setter
    private List<CmtBasicaMgl> tipoAccionSolicitudBasicaList;
    @Getter
    @Setter
    private List<CmtTipoSolicitudMgl> tipoSolicitudByRolList = new ArrayList<>();
    @Getter
    @Setter
    private boolean showGestionSolicitudList;
    @Getter
    @Setter
    private boolean showGestionSolicitud;
    @Getter
    @Setter
    private boolean showSolicitud;
    @Getter
    @Setter
    private boolean showTrack;
    private boolean ordenMayorMenor;
    private String tipoSolicitudFiltro;
    private String pageActual;
    @Getter
    @Setter
    private String numPagina = "1";
    @Getter
    @Setter
    private String inicioPagina = "<< - Inicio";
    @Getter
    @Setter
    private String anteriorPagina = "< - Anterior";
    @Getter
    @Setter
    private String finPagina = "Fin - >>";
    @Getter
    @Setter
    private String siguientePagina = "Siguiente - >";
    @Getter
    @Setter
    private List<Integer> paginaList;
    @Getter
    @Setter
    private int actual;
    @Getter
    @Setter
    private int filasPag = ConstantsCM.PAGINACION_DIEZ_FILAS;
    @Getter
    @Setter
    private String regresarMenu = "<- Regresar Menú";
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private CmtTipoSolicitudMgl cmtTipoSolicitudMgl;
    private HttpServletResponse response = (HttpServletResponse)
            facesContext.getExternalContext().getResponse();
    private CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl;
    @EJB
    private CmtTipoSolicitudMglFacadeLocal cmtTipoSolicitudMglFacadeLocal;
    @EJB
    private SolicitudFacadeLocal solicitudFacadeLocal;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMglFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    @Inject
    private ExceptionServBean exceptionServBean;

    //Opciones agregadas para Rol
    private final String TBLBTGSTI = "TBLBTGSTI";

    @Getter
    @Setter
    private List<CmtBasicaMgl> listBasicaDivisional;
    @Getter
    @Setter
    private String dividionalSelected;
    @Getter
    @Setter
    private BigDecimal filtroDivisional;
    private SecurityLogin securityLogin;

    public GestionSolicitudDthBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                response.sendRedirect
                        (securityLogin.redireccionarLogin());
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();
            cedulaUsuarioVT = securityLogin.getIdUser();

            if (usuarioVT == null) {
                response.sendRedirect
                        (securityLogin.redireccionarLogin());
            }
            showList();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al arrancar la pantalla" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al arrancar la pantalla" + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    private void init() {
        try {
            tipoSolicitudByRolList
                    = cmtTipoSolicitudMglFacadeLocal.obtenerTipoSolicitudHhppByRolList(facesContext,
                    Constant.TIPO_APLICACION_SOLICITUD_HHPP, false);
            tipoSolicitudByRolList.removeIf(tipo -> tipo.getNombreTipo().equalsIgnoreCase(ConstantsSolicitudHhpp.NOMBRE_TIPO_TRASLADO_HHPP_BLOQUEADO));
            obtenerTipoAccionSolicitudList(tipoSolicitudByRolList);

            SolicitudSessionBean solicitudSessionBean
                    = JSFUtil.getSessionBean(SolicitudSessionBean.class);

            ordenMayorMenor = false;
            //Obtenemos pagina actual de bean de Session.
            int page = solicitudSessionBean.getPaginaActual();
            listInfoByPage(page == 0 ? 1 : page);
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
            obtenerListadoDivisionales();
        } catch (ApplicationException | IOException e) {
            exceptionServBean.notifyError(e, "Error al realizar init de PostConstruct " + e.getMessage(), GESTIONAR_SOLICITUD);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al realizar init de PostConstruct: " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Función que obtiene el tipo de solicitud apartir del cambio dir de la solicitud
     * consultando su correpondiente basica y haciendo el cruce con la tabla de tipo de solicitud
     *
     * @param tipoAccionSolicitudBasicaIdList
     * @param cambioDir
     * @return
     * @author Juan David Hernandez
     */
    public CmtTipoSolicitudMgl obtenerTipoSolicitud(List<CmtBasicaMgl> tipoAccionSolicitudBasicaIdList,
                                                    String cambioDir) {
        try {
            CmtBasicaMgl tipoSolicitudSeleccionadaBasicaId = new CmtBasicaMgl();
            if (tipoAccionSolicitudBasicaIdList != null && !tipoAccionSolicitudBasicaIdList.isEmpty()) {
                for (CmtBasicaMgl cmtBasicaMgl : tipoAccionSolicitudBasicaIdList) {
                    if (cambioDir.equalsIgnoreCase(cmtBasicaMgl.getCodigoBasica())) {
                        tipoSolicitudSeleccionadaBasicaId = cmtBasicaMgl.clone();
                        break;
                    }
                }
            }
            if (tipoSolicitudSeleccionadaBasicaId != null
                    && tipoSolicitudSeleccionadaBasicaId.getBasicaId() != null) {
                return cmtTipoSolicitudMgl = cmtTipoSolicitudMglFacadeLocal
                        .findTipoSolicitudByTipoSolicitudBasicaId(tipoSolicitudSeleccionadaBasicaId);
            } else {
                return null;
            }

        } catch (ApplicationException | CloneNotSupportedException e) {
            String msgError = "Error al obtener tipo de solicitud ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), GESTIONAR_SOLICITUD);
            return null;
        }
    }

    /**
     * Función que realiza filtro por IdSolicitud.
     *
     * @author Juan David Hernandez
     */
    public void findSolicitudByIdRol() {
        try {
            if (idSolicitudDth != null) {
                //Busca por id pero solo si la solicitud tiene el rol que se lo permite
                solicitudesDthList =
                        solicitudFacadeLocal
                                .findSolicitudByIdRol(idSolicitudDth, tipoSolicitudByRolList);

                if (solicitudesDthList != null && !solicitudesDthList.isEmpty()) {
                    obtenerColorAlerta(tipoAccionSolicitudBasicaList);
                    obtenerCiudadSolicitudesByCentroPoblado();
                    obtenerTipoSolicitudAListadoSolicitudes(tipoAccionSolicitudBasicaList);
                } else {
                    String msnError = "No se encontraron resultados ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
                }
            } else {
                String msnError = "Ingrese un criterio de búsqueda para "
                        + "filtrar el listado de solicitudes "
                        + "por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
            }
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al realizar cargue de solicitudes por id: " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }


    /**
     * Función que realiza filtro por IdSolicitud.
     *
     * @author Juan David Hernandez
     */
    public void filtrarSolicitudes() {
        try {
            if (tipoSolicitudFiltro != null
                    && !tipoSolicitudFiltro.isEmpty()) {
                listInfoByPage(1);
            }
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al realizar cargue de solicitudes por id " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }


    /**
     * Función que carga todas las solicitudes Dth
     *
     * @author Juan David Hernandez
     */
    public void cargarSolicitudDthList() {
        try {
            tipoSolicitudFiltro = "";
            listInfoByPage(1);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al realizar cargue de solicitudes " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }


    /**
     * Función utilizada para establecer el valor del color de la alerta
     *
     * @param tipoAccionSolicitudBasicaList
     * @author Juan David Hernandez
     */
    public void obtenerColorAlerta(List<CmtBasicaMgl> tipoAccionSolicitudBasicaList) {
        try {
            for (Solicitud solicitud : solicitudesDthList) {
                solicitud.setColorAlerta(solicitudFacadeLocal
                        .obtenerColorAlerta(obtenerTipoSolicitud(tipoAccionSolicitudBasicaList, solicitud.getCambioDir()),
                                solicitud.getFechaIngreso()));
            }

        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al establecer el valor de la alerta ans " + e.getMessage(), GESTIONAR_SOLICITUD);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al establecer el valor de la alerta ans: " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Función utilizada para obtiener valores de tipo de acción de solicitud
     * básica.
     *
     * @param tipoSolicitudByRolList
     * @author Juan David Hernandez
     */
    public void obtenerTipoAccionSolicitudList(List<CmtTipoSolicitudMgl> tipoSolicitudByRolList) {
        try {
            CmtTipoBasicaMgl cmtTipoBasicaMgl;
            cmtTipoBasicaMgl = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_ACCION_SOLICITUD);
            tipoAccionSolicitudBasicaList =
                    cmtBasicaMglFacadeLocal.findByCodigoBasicaList(tipoSolicitudByRolList, cmtTipoBasicaMgl);
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al obtener el tipo de acción de la solicitud. " + e.getMessage(), GESTIONAR_SOLICITUD);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al obtener el tipo de acción de la solicitud: " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Función para obtener la ciudad de la solicitud apartir del centropoblado
     *
     * @author Juan David Hernandez
     */
    public void obtenerCiudadSolicitudesByCentroPoblado() {
        try {
            if (solicitudesDthList != null) {
                for (Solicitud solicitud : solicitudesDthList) {
                    GeograficoPoliticoMgl centroPoblado;
                    GeograficoPoliticoMgl ciudad = new GeograficoPoliticoMgl();
                    if (solicitud.getCentroPobladoId() != null) {
                        centroPoblado = geograficoPoliticoMglFacadeLocal
                                .findById(solicitud.getCentroPobladoId());
                        ciudad = geograficoPoliticoMglFacadeLocal
                                .findById(centroPoblado.getGeoGpoId());
                    }
                    if (ciudad != null) {
                        solicitud.setCiudad(ciudad.getGpoNombre());
                    } else {
                        solicitud.setCiudad("");
                    }
                }
            }

        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al obtener el tipo de acción de la solicitud. " + e.getMessage(), GESTIONAR_SOLICITUD);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al obtener el tipo de acción de la solicitud: " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Función para obtener el tipo de accion/solicitud del listado de solicitudes
     *
     * @param tipoAccionSolicitudBasicaList
     * @author Juan David Hernandez
     */
    public void obtenerTipoSolicitudAListadoSolicitudes(List<CmtBasicaMgl> tipoAccionSolicitudBasicaList) {
        try {
            if (solicitudesDthList != null && tipoAccionSolicitudBasicaList != null) {
                for (Solicitud solicitud : solicitudesDthList) {
                    for (CmtBasicaMgl tipoAccion : tipoAccionSolicitudBasicaList) {
                        if (solicitud.getCambioDir().equalsIgnoreCase(tipoAccion.getCodigoBasica())) {
                            solicitud.setTipoAccionSolicitudStr(tipoAccion.getNombreBasica());
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al obtener el tipo de acción de la solicitud estableciendo valor al listado "
                    + e.getMessage(), GESTIONAR_SOLICITUD);
        }

    }

    /**
     * Función que redirecciona al menú principal del a aplicación.
     *
     * @throws java.io.IOException
     * @author Juan David Hernandez
     */
    public void redirecToMenu() throws IOException {
        try {
            desbloquearDisponibilidadGestionDth(solicitudDthSeleccionada);
            SolicitudSessionBean solicitudSessionBean
                    = JSFUtil.getSessionBean(SolicitudSessionBean.class);
            solicitudSessionBean.setPaginaActual(1);
            FacesUtil.navegarAPagina(FacesUtil.getShortRequestContextPath());
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Se genera error al itentar regresar al menú principal " + e.getMessage(), GESTIONAR_SOLICITUD);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Se genera error al itentar regresar al menú principal: " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }


    /**
     * Función que redirecciona a pantalla de gestión de solicitud.
     *
     * @param solicitudDth
     * @author Juan David Hernandez
     */
    public void goGestionarSolicitudDth(Solicitud solicitudDth) {
        try {

            if (Objects.isNull(solicitudDth) || Objects.isNull(solicitudDth.getIdSolicitud())) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, "Ha ocurrido un error seleccionando la solicitud, intente de nuevo por favor.");
                return;
            }

            if (!solicitudFacadeLocal.validarDisponibilidadSolicitud(solicitudDth.getIdSolicitud(), solicitudDth.getTipo())) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "No es posible gestionar la solicitud debido a que ya se encuentra en gestión por otra persona. ");
                return;
            }

            if (!Constant.CAMBIO_ESTRATO_2.equalsIgnoreCase(solicitudDth.getCambioDir())
                    && Constant.VALIDACION_COBERTURA_12.equalsIgnoreCase(solicitudDth.getCambioDir())
                    && Objects.isNull(solicitudDth.getIdFactibilidad())) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.INFO, "La Solicitud ya no cuenta con Id de Factibilidad por tal motivo no es posible abrirla para gestión");
                return;
            }

            if (Objects.isNull(solicitudDth.getDireccionDetallada())) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.INFO,"La solicitud no dispone de dirección detallada.");
                return;
            }

            cmtDireccionDetalladaMgl = cmtDireccionDetalleMglFacadeLocal.buscarDireccionIdDireccion(solicitudDth.getDireccionDetallada().getDireccionDetalladaId());
            if (Objects.isNull(cmtDireccionDetalladaMgl) || Objects.isNull(cmtDireccionDetalladaMgl.getDireccionDetalladaId())) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR,"No fue posible abrir la solicitud debido a que no se encuentra la dirección registrada en la base de datos.");
                return;
            }

            UbicacionMgl ubicacionMgl = cmtDireccionDetalladaMgl.getDireccion().getUbicacion();
            if (Objects.isNull(ubicacionMgl.getUbiId()) || Objects.isNull(ubicacionMgl.getGpoIdObj())) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR,"Ocurrio un error cargando la informacion de la solicitud: la dirección no tiene geografico en la ubicación  ");
                return;
            }

            if (Constant.ESTADO_SOL_VERIFICADO.equalsIgnoreCase(solicitudDth.getEstado())
                    && !Objects.isNull(solicitudDth.getUsuarioVerificador())
                    && !solicitudDth.getUsuarioVerificador().isEmpty()
                    && solicitudDth.getUsuarioVerificador().equalsIgnoreCase(usuarioLogin.getNombre())) {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.INFO,"La solicitud no puede ser Gestionada por la misma persona que la Verificó.");
                return;
            }else{
                solicitudFacadeLocal.bloquearDisponibilidadGestionVerificada(solicitudDth.getIdSolicitud());
            }

            if (!Objects.isNull(solicitudDth.getDireccion())) {
                direccion = solicitudDth.getDireccion().replace("null", " ");
            } else {
                direccion = "Sin dirección registrada.";
            }

            solicitudFacadeLocal.bloquearDisponibilidadGestionDth(solicitudDth.getIdSolicitud(), usuarioLogin.getNombre().toUpperCase());
            this.solicitudDthSeleccionada = solicitudDth;
            if (StringUtils.isNotBlank(solicitudDth.getCambioDir()) && Objects.equals(solicitudDth.getCambioDir(), Constant.RR_DIR_ESCALAMIENTO_HHPP)) {
                SolicitudSessionBean solicitudSessionBean = JSFUtil.getSessionBean(SolicitudSessionBean.class);
                if (!Objects.isNull(solicitudSessionBean)){
                    solicitudSessionBean.setSolicitudDthSeleccionada(solicitudDth);
                    solicitudSessionBean.setPaginaActual(actual);
                    solicitudSessionBean.setDetalleSolicitud(false);
                    FacesUtil.navegarAPagina("/view/MGL/VT/gestion/Escalamientos/gestionSolicitudEscala.jsf");
                }
            } else {
                // Instacia Bean de Session para obtener la solicitud seleccionada
                SolicitudSessionBean solicitudSessionBean = JSFUtil.getSessionBean(SolicitudSessionBean.class);
                if(!Objects.isNull(solicitudSessionBean)){
                    solicitudSessionBean.setSolicitudDthSeleccionada(solicitudDth);
                    solicitudSessionBean.setPaginaActual(actual);
                    solicitudSessionBean.setDetalleSolicitud(false);
                    FacesUtil.navegarAPagina("/view/MGL/VT/gestion/DTH/gestionSolicitudDth.jsf");
                }
            }
        }catch (Exception e) {
            String msgError = "Error al gestionar solicitud ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }


    /**
     * Función que redirecciona a pantalla de gestión de solicitud.
     *
     * @param solicitudDth
     * @author Juan David Hernandez
     */
    public void goGestionarSolicitudDesdeCrearSolicitud(Solicitud solicitudDth) {
        try {
            if (solicitudFacadeLocal.validarDisponibilidadSolicitud(solicitudDth.getIdSolicitud(), solicitudDth.getTipo())) {
                this.solicitudDthSeleccionada = solicitudDth;

                if (solicitudDthSeleccionada.getCambioDir().equalsIgnoreCase(Constant.CAMBIO_ESTRATO_2)) {

                    if (solicitudDthSeleccionada.getDireccionDetallada() != null) {

                        if (!solicitudDthSeleccionada.getEstado().equalsIgnoreCase(Constant.ESTADO_SOL_VERIFICADO)) {
                            /* bloquea disponibilidad de la solicitud al momento de
                                             ingresar en la gestión de la misma. */
                            solicitudFacadeLocal.bloquearDisponibilidadGestionDth(
                                    solicitudDthSeleccionada.getIdSolicitud(),
                                    usuarioLogin.getNombre().toUpperCase());
                        } else {
                            if (solicitudDthSeleccionada.getUsuarioVerificador() != null
                                    && !solicitudDthSeleccionada.getUsuarioVerificador().isEmpty()) {
                                if (solicitudDthSeleccionada.getUsuarioVerificador().equalsIgnoreCase(usuarioLogin.getNombre().toUpperCase())) {
                                    FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "La solicitud no puede ser Gestionada por la misma persona que la Verificó.");
                                    return;
                                }
                            }
                            /* bloquea disponibilidad de la solicitud al momento de
                                             ingresar en la gestión de la misma. */
                            solicitudFacadeLocal.
                                    bloquearDisponibilidadGestionVerificada(solicitudDthSeleccionada.getIdSolicitud());

                        }
                    } else {
                        FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, "NO fue posible abrir la solicitud" +
                                " debido a que no se encuentra la dirección registrada en la base de datos.");

                        return;
                    }
                } else {

                    /* bloquea disponibilidad de la solicitud al momento de
                         ingresar en la gestión de la misma. */
                    solicitudFacadeLocal.bloquearDisponibilidadGestionDth(solicitudDthSeleccionada.getIdSolicitud(), usuarioLogin.getNombre().toUpperCase());
                }

                if (solicitudDthSeleccionada.getDireccion() != null) {
                    direccion = solicitudDthSeleccionada.getDireccion().replace("null", " ");
                } else {
                    direccion = "Sin dirección registrada.";
                }

                // Instacia Bean de Session para obtener la solicitud seleccionada
                SolicitudSessionBean solicitudSessionBean
                        = JSFUtil.getSessionBean(SolicitudSessionBean.class);
                solicitudSessionBean.setSolicitudDthSeleccionada(solicitudDthSeleccionada);
                solicitudSessionBean.setPaginaActual(actual);
                solicitudSessionBean.setDetalleSolicitud(false);
                FacesUtil.navegarAPagina("/view/MGL/VT/gestion/DTH/"
                        + "gestionSolicitudDth.jsf");
            } else {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "No es posible gestionar la solicitud" +
                        " debido a que ya se encuentra en gestión por otra persona. ");
            }

        } catch (Exception e) {
            String msgError =  "Error al gestionar solicitud ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Función que desbloquea la disponibilidad de las solicitudes
     *
     * @param solicitudDthSeleccionada
     * @author Juan David Hernandez
     */
    public void desbloquearDisponibilidadGestionDth(Solicitud solicitudDthSeleccionada) {
        try {
            if (solicitudDthSeleccionada != null) {

                if (solicitudDthSeleccionada.getCambioDir().
                        equalsIgnoreCase(Constant.CAMBIO_ESTRATO_2)) {
                    if (solicitudDthSeleccionada.getEstado().
                            equalsIgnoreCase(Constant.ESTADO_SOL_VERIFICADO)) {
                        solicitudFacadeLocal.
                                desbloquearDisponibilidadGestionVerificada(solicitudDthSeleccionada.getIdSolicitud());
                    } else {
                        solicitudFacadeLocal.
                                desbloquearDisponibilidadGestionDth(solicitudDthSeleccionada.getIdSolicitud());
                    }
                } else {
                    solicitudFacadeLocal.
                            desbloquearDisponibilidadGestionDth(solicitudDthSeleccionada.getIdSolicitud());
                }
                // Instacia Bean de Session para obtener la solicitud seleccionada
                SolicitudSessionBean solicitudSessionBean =
                        JSFUtil.getSessionBean(SolicitudSessionBean.class);
                listInfoByPage(solicitudSessionBean.getPaginaActual());
            } else {
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "Ha ocurrido un error seleccionando la " +
                        "solicitud, intente de nuevo por favor.");
            }
        } catch (Exception e) {
            String msgError = "Error al desbloquear Disponibilidad Gestion solicitud";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }


    /**
     * Función que guarda la pagina actual de la paginación del listado en
     * session para esta poder ser accedida desde el bean de la gestion
     * de la solicitud
     *
     * @author Juan David Hernandez
     */
    public void guardarPaginaActualSession() {
        try {
            // Instacia Bean de Session para obtener la solicitud seleccionada
            SolicitudSessionBean solicitudSessionBean =
                    JSFUtil.getSessionBean(SolicitudSessionBean.class);

            //enviamos pagina actual a bean de session.
            solicitudSessionBean.setPaginaActual(actual);
        } catch (Exception e) {
            String msgError = "Error al guardar página actual en session ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }


    /**
     * Función que realiza paginación de la tabla.
     *
     * @param page;
     * @return
     * @author Juan David Hernandez
     */
    public String listInfoByPage(int page) {
        try {
            actual = page;
            getPageActual();
            solicitudesDthList =
                    solicitudFacadeLocal
                            .findPendientesParaGestionPaginacionByRol
                                    (page, filasPag, tipoSolicitudByRolList, tipoSolicitudFiltro,
                                            ordenMayorMenor, filtroDivisional);

            if (solicitudesDthList != null && !solicitudesDthList.isEmpty()) {
                 /* enviamos el tipo de solicitud de un registro para obtener
                el ans*/
                obtenerColorAlerta(tipoAccionSolicitudBasicaList);
                obtenerCiudadSolicitudesByCentroPoblado();
                obtenerTipoSolicitudAListadoSolicitudes(tipoAccionSolicitudBasicaList);
            }
            guardarPaginaActualSession();
        } catch (Exception e) {
            String msgError = "Error en lista de paginación";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), GESTIONAR_SOLICITUD);
        }
        return null;
    }

    public void cambiarOrdenMayorMenorListado() {
        if (ordenMayorMenor) {
            ordenMayorMenor = false;
        } else {
            ordenMayorMenor = true;
        }
        listInfoByPage(1);
    }


    /**
     * Función que carga la primera página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a la primera página " + e.getMessage(), GESTIONAR_SOLICITUD);
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
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a la página anterior " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Función que cpermite ir directamente a la página seleccionada
     * de resultados.
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
            exceptionServBean.notifyError(e, "Error direccionando a página " + e.getMessage(), GESTIONAR_SOLICITUD);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a página: " + e.getMessage(), GESTIONAR_SOLICITUD);
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
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a la siguiente página " + e.getMessage(), GESTIONAR_SOLICITUD);
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
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            exceptionServBean.notifyError(ex, "Error direccionando a la última página" + ex.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     *
     * @return
     * @author Juan David Hernandez
     */
    public int getTotalPaginas() {
        try {
            int totalPaginas;
            int pageSol =
                    solicitudFacadeLocal
                            .countAllSolicitudByRolList(tipoSolicitudByRolList,
                                    tipoSolicitudFiltro);

            totalPaginas = (pageSol % filasPag != 0) ?
                    (pageSol / filasPag) + 1 : pageSol / filasPag;
            return totalPaginas;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al obtener el total de páginas " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario
     * en los resultados.
     *
     * @return
     * @author Juan David Hernandez
     */
    public String getPageActual() {
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

        return pageActual;
    }

    /**
     * Función que redirecciona a la pantalla del listado de solicitudes.
     *
     * @author Juan David Hernandez
     */
    public void goBackSolicitudDthList() {
        try {
            FacesUtil.navegarAPagina("/view/MGL/VT/gestion/"
                    + "gestionSolicitudListado.jsf");
            // Instacia Bean de Session para obtener la solicitud seleccionada
            SolicitudSessionBean solicitudSessionBean =
                    JSFUtil.getSessionBean(SolicitudSessionBean.class);
            listInfoByPage(solicitudSessionBean.getPaginaActual());

        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al ir a pantalla de listado de solicitudes " + e.getMessage(), GESTIONAR_SOLICITUD);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al ir a pantalla de listado de solicitudes: " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Función para obtener listado de divisionales
     *
     * @author cardenaslb
     */
    public void obtenerListadoDivisionales() {
        try {
            CmtTipoBasicaMgl tipoBasicaDivicional;
            tipoBasicaDivicional = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_DIVICIONAL_COMERCIAL_TELMEX);
            listBasicaDivisional = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaDivicional);
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al obtener el tipo de acción de la solicitud estableciendo valor al listado.  "
                    + e.getMessage(), GESTIONAR_SOLICITUD);
        }

    }

    /**
     * Función que realiza filtro por IdSolicitud.
     *
     * @author Juan David Hernandez
     */
    public void filtrarDivicional() {
        try {
            if (filtroDivisional != null) {
                listInfoByPage(1);
            }
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al realizar cargue de solicitudes por id " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Función que renderizar la pantalla de listado de solicitudes.
     *
     * @author Juan David Hernandez
     */
    public void showList() {
        showGestionSolicitudList = true;
        showGestionSolicitud = false;
    }

    public String getTipoTecnologiaFiltro() {
        return tipoSolicitudFiltro;
    }

    public void setTipoTecnologiaFiltro(String tipoTecnologiaFiltro) {
        this.tipoSolicitudFiltro = tipoTecnologiaFiltro;
    }

    // Validar Opciones por Rol
    public boolean validarOpcionGestion() {
        return validarEdicionRol(TBLBTGSTI);
    }

    //funcion General
    private boolean validarEdicionRol(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
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
