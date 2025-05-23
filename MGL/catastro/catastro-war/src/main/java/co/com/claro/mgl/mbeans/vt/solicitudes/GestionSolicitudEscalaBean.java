package co.com.claro.mgl.mbeans.vt.solicitudes;

import co.com.claro.mer.address.standardize.facade.StandardizeAddressFacadeLocal;
import co.com.claro.mer.utils.FileToolUtils;
import co.com.claro.mer.utils.constants.ConstantsSolicitudHhpp;
import co.com.claro.mer.utils.enums.AddressTypeEnum;
import co.com.claro.mer.utils.enums.ApartamentosCombinadosEnum;
import co.com.claro.mer.utils.enums.MessageSeverityEnum;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.dtos.FiltroConjsultaBasicasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.*;
import co.com.claro.mgl.facade.cm.*;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.*;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.MailSender;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.file.UploadedFile;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;


/**
 * ManagedBean que se encarga de resolver la vista para la gestión
 * de solicitudes de escalamiento HHPP
 * @author José René Miranda de la O
 */
@Log4j2
@ManagedBean(name = "gestionSolicitudEscalaBean")
@ViewScoped
public class GestionSolicitudEscalaBean {

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private SecurityLogin securityLogin;
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    private List<CmtTipoSolicitudMgl> rolesUsuarioList = new ArrayList<>();
    private List<CmtBasicaMgl> tipoTecnologiaBasicaList;
    private List<CmtBasicaMgl> tipoAccionSolicitudBasicaList;
    private List<GeograficoPoliticoMgl> centroPobladoList;
    private List<GeograficoPoliticoMgl> departamentoList;
    private List<GeograficoPoliticoMgl> ciudadesList;
    private List<ParametrosCalles> areaCanalList;
    private List<TecArchivosEscalamientosHHPP> tecArchivosEscalamientosHHPP;
    private List<TecArchivosEscalamientosHHPP> tecArchivosEstadoEscalamientosHHPP;
    private List<TecObservacionSolicitudHHPP> observacionesList;
    private List<CmtBasicaMgl> tablasBasicasMglList;
    private List<CmtBasicaMgl> tablasBasicasResultadoMglList;
    private List<OpcionIdNombre> ckList = new ArrayList<>();
    private List<OpcionIdNombre> bmList = new ArrayList<>();
    private List<OpcionIdNombre> itList = new ArrayList<>();
    private List<OpcionIdNombre> aptoList = new ArrayList<>();
    private List<OpcionIdNombre> complementoList = new ArrayList<>();
    private List<File> lstFilesEscalamientoHHPP = new ArrayList<>();
    private List<CmtNotasSolicitudVtMgl> notasSolicitudList;
    private List<CmtBasicaMgl> tipoNotaList;
    private Solicitud solicitudSeleccionada = new Solicitud();
    private CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl;
    private DrDireccion drDireccion;
    private CmtBasicaMgl tipoTecnologiaBasica = new CmtBasicaMgl();
    private GeograficoPoliticoMgl ciudadGpo;
    private GeograficoPoliticoMgl departamentoGpo;
    private GeograficoPoliticoMgl centroPobladoGpo;
    private ConfigurationAddressComponent configurationAddressComponent;
    private CmtNotasSolicitudVtMgl cmtNotasSolicitudVtMgl = new CmtNotasSolicitudVtMgl();
    private CmtBasicaMgl tipoNotaSelected = new CmtBasicaMgl();
    private CmtTiempoSolicitudMgl cmtTiempoSolicitudMgl = new CmtTiempoSolicitudMgl();
    private CmtTiempoSolicitudMgl cmtTiempoSolicitudMglToCreate = new CmtTiempoSolicitudMgl();
    private CmtTipoSolicitudMgl cmtTipoSolicitudMgl;
    private UploadedFile fileEscalamientoHHPP;
    private Date dateInicioCreacion;
    private BigDecimal solicitudIdSeleccionada;
    private String notaComentarioStr;
    private String usuarioVt = null;
    private String cedulaUsuarioVt = null;
    private String direccionRespuestaGeo;
    private String complementoDireccion;
    private String tmpInicio;
    private String deltaTiempo;
    private String tmpFin;
    private String timeSol;
    private String departamento;
    private String tipoTecnologia;
    private String tipoDireccion;
    private String ccmm;
    private String solicitudOT;
    private String tipoGestion;
    private String tipoHHPP;
    private String direccionCk;
    private String area;
    private String tipoNivelApartamento;
    private String tipoNivelComplemento;
    private String accion;
    private String resultadoGestion;
    private String respuestaActual;
    private String usuarioGestionador;
    private String observaciones;
    private String selectedTab = "SOLICITUD";
    @Getter
    @Setter
    private String direccionCompleta;
    private BigDecimal ciudad;
    private BigDecimal idCentroPoblado;
    private static final String CREACION = "CREACION";
    private static final String ESTADO = "ESTADO";
    private int perfilVt = 0;
    private boolean solicitudGestionada;
    private boolean detalleSolicitud;
    private boolean habilitarRR;
    private boolean showCKPanel;
    private boolean showBMPanel;
    private boolean showITPanel;
    private boolean notGeoReferenciado = true;
    private boolean estadoSolicitud;
    private boolean showBack;
    private boolean showSolicitud;
    private boolean showTrack;
    private boolean showNotas;

    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private CmtTiempoSolicitudMglFacadeLocal cmtTiempoSolicitudMglFacadeLocal;
    @EJB
    private CmtTipoSolicitudMglFacadeLocal cmtTipoSolicitudMglFacadeLocal;
    @EJB
    private SolicitudFacadeLocal solicitudFacadeLocal;
    @EJB
    private CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMglFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMgl;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private ParametrosCallesFacadeLocal parametrosCallesFacade;
    @EJB
    private ITecArcEscalaHHPPFacadeLocal tecArcEscalaHHPPFacadeLocal;
    @EJB
    private CmtComponenteDireccionesMglFacadeLocal componenteDireccionesMglFacade;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacade;
    @EJB
    private ITecObservacionSolicitudHHPPFacadeLocal observacionSolicitudHHPPFacadeLocal;
    @EJB
    private CmtNotasSolicitudDetalleVtMglFacadeLocal notasSolicitudDetalleVtMglFacadeLocal;
    @EJB
    private CmtNotasSolicitudVtMglFacadeLocal notasSolicitudVtMglFacadeLocal;
    @EJB
    private StandardizeAddressFacadeLocal standardizeAddressFacadeLocal;

    public GestionSolicitudEscalaBean() {
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
            FacesUtil.mostrarMensajeError("Error al iniciar la pantalla" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al iniciar la pantalla" + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVt);
            cmtTiempoSolicitudMglFacadeLocal.setUser(usuarioVt, perfilVt);
            // Instacia Bean de Session para obtener la solicitud seleccionada
            SolicitudSessionBean solicitudSessionBean = JSFUtil.getSessionBean(SolicitudSessionBean.class);

            if (solicitudSessionBean != null) {
                solicitudIdSeleccionada = Optional.ofNullable(solicitudSessionBean.getSolicitudDthSeleccionada())
                        .map(Solicitud::getIdSolicitud)
                        .orElse(null);
                detalleSolicitud = solicitudSessionBean.isDetalleSolicitud();
            }

            List<CmtTipoSolicitudMgl> gestionRolList = cmtTipoSolicitudMglFacadeLocal.obtenerTipoSolicitudHhppByRolList(facesContext,
                    Constant.TIPO_APLICACION_SOLICITUD_HHPP, false);
            SecurityLogin securityLoginUser = new SecurityLogin(facesContext);

            if (CollectionUtils.isNotEmpty(gestionRolList)) {
                for (CmtTipoSolicitudMgl rol : gestionRolList) {
                    if (securityLoginUser.usuarioTieneRoll(rol.getGestionRol())) {
                        rolesUsuarioList.add(rol);
                    }
                }
            }

            List<Solicitud> solicitudList = Optional.ofNullable(solicitudIdSeleccionada)
                    .map(id -> solicitudFacadeLocal.findSolicitudByIdRol(id, rolesUsuarioList))
                    .orElse(Collections.emptyList());

            if (CollectionUtils.isEmpty(solicitudList)) {
               LOGGER.debug("No se encontraron solicitudes para gestionar...");
                return;
            }

            solicitudSeleccionada = solicitudList.get(0);
            solicitudSeleccionada.setUsuario(Optional.ofNullable(usuarioLogin.getNombre()).orElse("Usuario No Válido"));

            if (!detalleSolicitud) {
                solicitudSeleccionada.setUsuarioGestionador(Optional.ofNullable(usuarioLogin.getNombre()).orElse("Usuario No Válido"));
            }

            solicitudGestionada = false;
            direccionRespuestaGeo = Optional.ofNullable(solicitudSeleccionada.getDireccionRespuestaGeo()).orElse(StringUtils.EMPTY);
            habilitarRR = validarRrHabilitado();
            obtenerDireccionDetallada(solicitudSeleccionada);
            obtenerDrDireccion(solicitudSeleccionada.getDireccionDetallada());
            obtenerCiudadDepartamentoByCentroPobladoId(solicitudSeleccionada.getCentroPobladoId());
            obtenerComplementoDireccion();
            direccionCompleta = construirDireccionCompleta(solicitudSeleccionada);
            obtenerColorAlerta(solicitudList, obtenerTipoSolicitud());
            iniciarTiempoGestion();
            asignarTiempoEsperaGestion();
            obtenerTipoAccionSolicitudList();
            obtenerCentroPobladoList();
            obtenerDepartamentoList();
            obtenerCiudadesList();
            obtenerTipoTecnologiaList();
            obtenerListadoAreaCanalList();
            cargarListadosConfiguracion();
            consultarTipoGestion();
            consultarResultadoGestion();
            obtenerTrackBySolicitud();
            showSolicitud();
            idCentroPoblado = solicitudSeleccionada.getCentroPobladoId();
            departamento = departamentoGpo.getGpoId().toString();
            ciudad = ciudadGpo.getGpoId();
            tipoTecnologia = solicitudSeleccionada.getTecnologiaId().getBasicaId().toString();
            area = solicitudSeleccionada.getTipoSol();
            tipoHHPP = solicitudSeleccionada.getTipoHHPP();
            tecArchivosEscalamientosHHPP = tecArcEscalaHHPPFacadeLocal.
                    findUrlsByIdSolicitudAndOrigenAndObservacion(solicitudSeleccionada, CREACION, null);
            ajustarVisbilidadPaneles(solicitudSeleccionada.getDireccionDetallada().getIdTipoDireccion());
            solicitudGestionada = false;
            estadoSolicitud = Optional.ofNullable(solicitudSessionBean).map(SolicitudSessionBean::isDetalleSolicitud).orElse(false);
            observacionesList = observacionSolicitudHHPPFacadeLocal.findByIdSolicitud(solicitudSeleccionada);

            for (TecObservacionSolicitudHHPP observacion : observacionesList) {
                observacion.setArchivos(tecArcEscalaHHPPFacadeLocal.
                        findUrlsByIdSolicitudAndOrigenAndObservacion(solicitudSeleccionada, "ESTADO", observacion));
            }

            if (estadoSolicitud) {
                accion = Optional.ofNullable(solicitudSeleccionada.getEstado()).orElse(StringUtils.EMPTY);
                resultadoGestion = Optional.ofNullable(solicitudSeleccionada.getRptGestion()).orElse(StringUtils.EMPTY);
                respuestaActual = Optional.ofNullable(solicitudSeleccionada.getRespuesta()).orElse(StringUtils.EMPTY);
                observacionesList = observacionSolicitudHHPPFacadeLocal.findByIdSolicitud(solicitudSeleccionada);

                for (TecObservacionSolicitudHHPP observacion : observacionesList) {
                    observacion.setArchivos(tecArcEscalaHHPPFacadeLocal.
                            findUrlsByIdSolicitudAndOrigenAndObservacion(solicitudSeleccionada, "ESTADO", observacion));
                }
            }
        } catch (Exception e) {
            capturarInitException(e);
        }
    }

    /**
     * Ajustar visibilidad de paneles según el tipo de dirección
     * @param idTipoDireccion Identificador del tipo de dirección
     */
    private void ajustarVisbilidadPaneles(String idTipoDireccion) {
        showCKPanel = "CK".equals(idTipoDireccion);
        showBMPanel = "BM".equals(idTipoDireccion);
        showITPanel = "IT".equals(idTipoDireccion);
    }

    /**
     * Procesar excepcion capturada en el método init
     * @param e Excepción capturada
     */
    private void capturarInitException(Exception e) {
        FacesUtil.mostrarMensajeError("Error en el cargue inicial de la gestión de la solicitud " + e.getMessage(), e, LOGGER);
        String msn = "Error en el cargue inicial de la gestión de la solicitud";
        showPopupMessage(MessageSeverityEnum.ERROR, msn + e.getMessage());
        try {
            FacesUtil.navegarAPagina("/view/MGL/VT/gestion/DTH/gestionSolicitudDthListado.jsf");
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al momento de redireccionar: " + ex.getMessage(), ex, LOGGER);
        }
    }

    /**
     * Construye la direccion completa de la solicitud
     * @param solicitudSeleccionada solicitud seleccionada
     * @return direccion completa
     */
    private String construirDireccionCompleta(Solicitud solicitudSeleccionada) {
        if (Objects.isNull(solicitudSeleccionada)) {
            LOGGER.error("La solicitud seleccionada es nula");
            return StringUtils.EMPTY;
        }

        if (Objects.isNull(solicitudSeleccionada.getDireccionDetallada())) {
            LOGGER.error("La dirección detallada de la solicitud seleccionada es nula");
            return StringUtils.EMPTY;
        }

        boolean isValidAddressType = false;

        Optional<String> tipoDir = Optional.ofNullable(solicitudSeleccionada.getDireccionDetallada())
                .map(CmtDireccionDetalladaMgl::getIdTipoDireccion)
                .filter(StringUtils::isNotBlank);

        if (tipoDir.isPresent()) {
            isValidAddressType = Arrays.stream(AddressTypeEnum.values())
                    .anyMatch(x -> x.getAddressType().equalsIgnoreCase(tipoDir.get()));
        }

        if (isValidAddressType) {
            AddressTypeEnum addressTypeEnum = AddressTypeEnum.valueOf(solicitudSeleccionada.getDireccionDetallada().getIdTipoDireccion());
            boolean isMultiOrigin = Optional.ofNullable(centroPobladoGpo)
                    .map(GeograficoPoliticoMgl::getGpoMultiorigen)
                    .filter(StringUtils::isNotBlank)
                    .filter(value -> value.equals("1")).isPresent();
            String standardizeAddress = standardizeAddressFacadeLocal.standardizeAddress(solicitudSeleccionada.getDireccionDetallada(), addressTypeEnum, isMultiOrigin);

            if (StringUtils.isNotBlank(standardizeAddress)) {
                return standardizeAddress;
            }
        }

        String cpTipoNivel5 = Optional.ofNullable(solicitudSeleccionada.getDireccionDetallada())
                .map(CmtDireccionDetalladaMgl::getCpTipoNivel5).orElse(StringUtils.EMPTY);
        ApartamentosCombinadosEnum[] nivelesApartCombinados = ApartamentosCombinadosEnum.values();
        Optional<ApartamentosCombinadosEnum> apartCombinado = Arrays.stream(nivelesApartCombinados)
                .filter(apartamentosCombinadosEnum -> apartamentosCombinadosEnum.getTipoApartamento().equals(cpTipoNivel5))
                .findFirst();

        StringBuilder direccionCmpl = new StringBuilder();
        if (StringUtils.isNotBlank(solicitudSeleccionada.getDireccion())) {
            direccionCmpl.append(solicitudSeleccionada.getDireccion()).append(StringUtils.SPACE);
        }

        if (StringUtils.isNotBlank(solicitudSeleccionada.getComplemento())) {
            direccionCmpl.append(solicitudSeleccionada.getComplemento()).append(StringUtils.SPACE);
        }

        String cpValorNivel5 = solicitudSeleccionada.getDireccionDetallada().getCpValorNivel5();

        if (apartCombinado.isPresent()
                && "CK".equals(solicitudSeleccionada.getDireccionDetallada().getIdTipoDireccion())) {
            String tipoApartamentoCombinado = solicitudSeleccionada.getDireccionDetallada().getCpTipoNivel6();
            String valorCombinado = solicitudSeleccionada.getDireccionDetallada().getCpValorNivel6();
            direccionCmpl.append(apartCombinado.get().getValorPrincipal()).append(StringUtils.SPACE);
            direccionCmpl.append(cpValorNivel5).append(StringUtils.SPACE);
            direccionCmpl.append(tipoApartamentoCombinado).append(StringUtils.SPACE);
            direccionCmpl.append(valorCombinado);
            direccionCmpl.append(StringUtils.SPACE);
            direccionCmpl.append(solicitudSeleccionada.getDireccionDetallada().getBarrio());
            return direccionCmpl.toString();
        }

        if (StringUtils.isNotBlank(cpTipoNivel5) && !direccionCmpl.toString().contains(cpTipoNivel5)) {
            direccionCmpl.append(cpTipoNivel5).append(StringUtils.SPACE);

            if (StringUtils.isNotBlank(cpValorNivel5)) {
                direccionCmpl.append(cpValorNivel5);
            }
        }

        direccionCmpl.append(StringUtils.SPACE);
        direccionCmpl.append(solicitudSeleccionada.getDireccionDetallada().getBarrio());
        return direccionCmpl.toString();
    }

    /**
     * Método para realizar la gestión de la solicitud
     */
    public void gestionarSolicitud() {
        try {
            if (validarGestion()) {
                Solicitud solicitudUpdate = solicitudFacadeLocal.findById(solicitudSeleccionada.getIdSolicitud());
                solicitudUpdate.setRespuesta(respuestaActual);
                solicitudUpdate.setRptGestion(resultadoGestion);
                if (StringUtils.isNotBlank(accion)) {
                    solicitudUpdate.setEstado(accion);
                    if (accion.equals("PENDIENTE")) {
                        solicitudUpdate.setFechaModificacion(new Date());
                        solicitudUpdate.setDisponibilidadGestion("0");
                    } else {
                        solicitudUpdate.setFechaCancelacion(new Date());
                    }
                }
                solicitudUpdate.setFechaModificacion(new Date());
                solicitudUpdate.setIdUsuarioGestion(new BigDecimal(usuarioLogin.getCedula()));
                solicitudUpdate.setUsuarioGestionador(usuarioLogin.getNombre());
                solicitudUpdate = solicitudFacadeLocal.update(solicitudUpdate);
                solicitudGestionada = true;

                final String tipoSolicitudStr = obtenerTipoSolicitudStr(solicitudSeleccionada.getCambioDir());
                Solicitud finalSolicitudUpdate = solicitudUpdate;
                CompletableFuture.runAsync(() -> enviarCorreoGestionSolicitud(finalSolicitudUpdate, tipoSolicitudStr));
                final String msg = "La solicitud quedo gestionada como " + solicitudUpdate.getEstado()
                        + ", " + resultadoGestion + ", " + respuestaActual + ".";

                showPopupMessage(MessageSeverityEnum.INFO, msg);

            }
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al gestionar la solicitud: " + ex.getMessage(), ex, LOGGER);
        }
    }

    private void enviarCorreoGestionSolicitud(Solicitud solicitudUpdate, String tipoSolicitudStr) {
        try {
            MailSender.send(usuarioLogin.getEmail(),
                    "", "",
                    "Gestión Escalamientos HHPP su número de Solicitud es " + solicitudUpdate.getIdSolicitud(),
                    mensajeCorreoGestionEscalamientosHHPP(solicitudUpdate,
                            departamentoGpo.getGpoNombre(), ciudadGpo.getGpoNombre(),
                            centroPobladoGpo.getGpoNombre(), tipoSolicitudStr));

        } catch (Exception e) {
            LOGGER.error("Error Enviando Mail" + e.getMessage());
        }
    }

    /**
     * Método para agregar datos al estado de la solicitud
     */
    public void agregarEstado() {
        try {
            if (validarAgregarEstado()) {
                Solicitud solicitudUpdate = solicitudFacadeLocal.findById(solicitudSeleccionada.getIdSolicitud());
                solicitudUpdate.setDisponibilidadGestion("0");
                solicitudUpdate.setUsuarioVerificador(null);
                solicitudUpdate = solicitudFacadeLocal.update(solicitudUpdate);

                TecObservacionSolicitudHHPP observacionSolicitudHHPP = new TecObservacionSolicitudHHPP();
                observacionSolicitudHHPP.setObservacion(observaciones);
                observacionSolicitudHHPP.setSolicitudObj(solicitudUpdate);
                observacionSolicitudHHPP = observacionSolicitudHHPPFacadeLocal.crear(observacionSolicitudHHPP);

                if (!lstFilesEscalamientoHHPP.isEmpty()) {
                    for (File archivo : lstFilesEscalamientoHHPP) {
                        String nombreArchivo;
                        String[] partsUrls = archivo.getName().split("-");
                        nombreArchivo = partsUrls[0];
                        String extension = FilenameUtils.getExtension(archivo.getName());
                        String nombreArchivoFinal = null;
                        if(nombreArchivo.contains(extension)) {
                            nombreArchivoFinal = solicitudUpdate.getIdSolicitud() + "_" + nombreArchivo;
                        } else {
                            nombreArchivoFinal = solicitudUpdate.getIdSolicitud() + "_" + nombreArchivo + "." + extension;
                        }

                        String responseArc = solicitudFacadeLocal.
                                uploadArchivoEscalamientoHHPP(archivo,
                                        nombreArchivoFinal);

                        if (responseArc == null
                                || responseArc.isEmpty()) {

                            String msg = "Ocurrio un error al subir el archivo: " + nombreArchivoFinal + "\n";
                            showPopupMessage(MessageSeverityEnum.ERROR, msg);

                        } else {

                            TecArchivosEscalamientosHHPP tecArchivosEscalamientosHHPP = new TecArchivosEscalamientosHHPP();
                            tecArchivosEscalamientosHHPP.setUrlArchivoSoporte(responseArc);
                            tecArchivosEscalamientosHHPP.setSolicitudObj(solicitudUpdate);
                            tecArchivosEscalamientosHHPP.setNombreArchivo(nombreArchivoFinal);
                            tecArchivosEscalamientosHHPP.setOrigen(ESTADO);
                            tecArchivosEscalamientosHHPP.setTecObservacionSolicitudHHPP(observacionSolicitudHHPP);
                            tecArchivosEscalamientosHHPP = tecArcEscalaHHPPFacadeLocal.crear(tecArchivosEscalamientosHHPP, usuarioVt, perfilVt);

                        }
                        FileToolUtils.deleteFile(archivo);
                    }
                }

                observacionesList = observacionSolicitudHHPPFacadeLocal.findByIdSolicitud(solicitudUpdate);
                for (TecObservacionSolicitudHHPP observacion : observacionesList) {
                    observacion.setArchivos(tecArcEscalaHHPPFacadeLocal.
                            findUrlsByIdSolicitudAndOrigenAndObservacion(solicitudUpdate, ESTADO, observacion));
                }
            }

            showBack = true;
            goBackEstadoSolicitud();
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al momento de redireccionar: " + ex.getMessage(), ex, LOGGER);
        }

    }

    /**
     * Método para validar los datos ingresados para la gestión
     * @return true si se ingreso correctamente toda la información, false en caso comtrario
     */
    private boolean validarGestion() {

        if (StringUtils.isBlank(resultadoGestion)) {
            showPopupMessage(MessageSeverityEnum.ERROR, "Seleccione Resultado de la Gestión por favor.");
            return false;
        }
        if (StringUtils.isBlank(respuestaActual)) {
            showPopupMessage(MessageSeverityEnum.ERROR, "Ingrese Respuesta Actual por favor.");
            return false;
        }
        return true;
    }

    /**
     * Método para validar se ingresen datos para agregar estado
     * @return
     */
    private boolean validarAgregarEstado() {
        if (StringUtils.isNotBlank(observaciones) && observaciones.length() > 2000) {
            showPopupMessage(MessageSeverityEnum.ERROR, "Las Observaciones no pueden ser mayores a 2000 caracteres.");
            return false;
        }
        return true;
    }

    /**
     * Método para envíar correo de gestión realizada
     * @param solicitud
     * @param departamento
     * @param ciudad
     * @param centroPoblado
     * @return
     */
    private StringBuffer mensajeCorreoGestionEscalamientosHHPP(final Solicitud solicitud, final String departamento, final String ciudad, final String centroPoblado, final String tipoSolicitudStr) {
        StringBuffer mensaje = new StringBuffer();
        if (solicitud != null) {

            if (solicitud.getIdSolicitud() != null) {
                mensaje.append("<b>N&uacute;mero Solicitud\t:</b>" + solicitud.getIdSolicitud() + "<br/>");
            }
            if (StringUtils.isNotEmpty(tipoSolicitudStr)) {
                mensaje.append("<b>Tipo Solicitud:</b>" + tipoSolicitudStr + "<br/>");
            }
            if (StringUtils.isNotEmpty(solicitud.getEstado())) {
                mensaje.append("<b>Estado Solicitud:</b>" + solicitud.getEstado() + "<br/>");
            }
            if (solicitud.getTecnologiaId() != null) {
                mensaje.append("<b>Tipo Tecnología:</b> " + solicitud.getTecnologiaId().getDescripcion() + "<br/>");
            }
            if (StringUtils.isNotEmpty(solicitud.getDireccion())) {
                mensaje.append("<b>Dirección:</b>" + solicitud.getDireccion() + "<br/>");
            }
            if (StringUtils.isNotEmpty(departamento)) {
                mensaje.append("<b>Departamento:</b>" + departamento + "<br/>");
            }
            if (StringUtils.isNotEmpty(ciudad)) {
                mensaje.append("<b>Ciudad:</b>" + ciudad + "<br/>");
            }
            if (StringUtils.isNotEmpty(centroPoblado)) {
                mensaje.append("<b>Centro Poblado:</b>" + centroPoblado + "<br/>");
            }
            if (StringUtils.isNotEmpty(solicitud.getRespuesta())) {
                mensaje.append("<b>Respuesta Actual:</b>" + solicitud.getRespuesta() + "<br/>");
            }
            if (StringUtils.isNotEmpty(solicitud.getRespuesta())) {
                mensaje.append("<b>Resultado de la Gestión:</b>" + solicitud.getRptGestion() + "<br/>");
            }
            if (StringUtils.isNotEmpty(solicitud.getUsuarioGestionador())) {
                mensaje.append("<b>Usuario Gestionador:</b>" + solicitud.getUsuarioGestionador() + "<br/>");
            }
            if (solicitud.getEstado().equals("PENDIENTE")) {
                mensaje.append("<br>");
                mensaje.append("<b>Nota:</b> Hay algo pendiente, debe ingresar la información requerida <br> " +
                        "y el tiempo máximo que tiene para dar solución es de 1 día hábil.");
            }
        }

        return mensaje;
    }

    /**
     * Función que obtiene la información de si RR se encuentra habilitado o no
     *
     * @return
     * @author Juan David Hernandez
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
            FacesUtil.mostrarMensajeError("Error al informacion si RR se encuentra habilitado" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al informacion si RR se encuentra habilitado" + e.getMessage(), e, LOGGER);
        }
        return habilitaRR;
    }

    /**
     * Función que obtiene la direccion detallada extraida de la solicitud
     *
     * @param solicitudSeleccionada
     * @author Juan David Hernandez
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

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al obtener la direccion detallada de la solicitud" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener la direccion detallada de la solicitud" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que el DrDireccion de una direccion apartir de la dirDetallada
     *
     * @param direccionDetallada
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
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
            FacesUtil.mostrarMensajeError("Error al obtener la direccion detallada de la solicitud" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener la direccion detallada de la solicitud" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que obtiene listado de unidades asociadas al predio
     *
     * @param centroPobladoId
     * @author Juan David Hernandez
     */
    public void obtenerCiudadDepartamentoByCentroPobladoId(BigDecimal centroPobladoId) {
        try {

            centroPobladoGpo = geograficoPoliticoMglFacadeLocal
                    .findById(centroPobladoId);

            ciudadGpo = geograficoPoliticoMglFacadeLocal
                    .findById(centroPobladoGpo.getGeoGpoId());

            departamentoGpo = (geograficoPoliticoMglFacadeLocal
                    .findById(ciudadGpo.getGeoGpoId()));

        } catch (ApplicationException e) {
            String msn = "Error al nombre de ciudad predio ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al nombre de ciudad predio ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Retorna el complemento de la direccion, o direccion intraducible.
     *
     * @param drDirec
     * @return
     */
    public String getComplementoDireccion(DrDireccion drDirec) {
        String dirResult = "";

        if (drDirec.getCpTipoNivel1() != null
                && !drDirec.getCpTipoNivel1().isEmpty()
                && drDirec.getCpValorNivel1() != null
                && !drDirec.getCpValorNivel1().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel1()
                    + " " + drDirec.getCpValorNivel1() + " ";
        }

        if (drDirec.getCpTipoNivel2() != null
                && !drDirec.getCpTipoNivel2().isEmpty()
                && drDirec.getCpValorNivel2()
                != null && !drDirec.getCpValorNivel2().isEmpty()) {
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

        if (drDirec.getCpTipoNivel5() != null
                && !drDirec.getCpTipoNivel5().isEmpty()
                && drDirec.getCpValorNivel5()
                != null && !drDirec.getCpValorNivel5().isEmpty()) {

            if (drDirec.getCpTipoNivel5()
                    .equalsIgnoreCase(Constant.OPT_CASA_PISO)) {

                dirResult += "CASA" + " " + drDirec.getCpValorNivel5() + " ";

            } else if (drDirec.getCpTipoNivel5()
                    .equalsIgnoreCase(Constant.OPT_PISO_INTERIOR)
                    || drDirec.getCpTipoNivel5()
                    .equalsIgnoreCase(Constant.OPT_PISO_LOCAL)
                    || drDirec.getCpTipoNivel5()
                    .equalsIgnoreCase(Constant.OPT_PISO_APTO)) {

                dirResult += "PISO" + " " + drDirec.getCpValorNivel5() + " ";
            } else {
                dirResult += drDirec.getCpTipoNivel5()
                        + " " + drDirec.getCpValorNivel5() + " ";
            }

        }
        // @author Juan David Hernandez
        if (drDirec.getCpTipoNivel5() != null
                && !drDirec.getCpTipoNivel5().isEmpty()
                && (drDirec.getCpValorNivel5() == null
                || drDirec.getCpValorNivel5().isEmpty())) {
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
        session.setAttribute(ConstantsSolicitudHhpp.ATRIBUTO_TIEMPO_INICIO, null);
        tmpInicio = (String) session.getAttribute(ConstantsSolicitudHhpp.ATRIBUTO_TIEMPO_INICIO);
        if (tmpInicio == null) {
            long milli = (new Date()).getTime();
            if (session.getAttribute("deltaTiempo") != null) {
                long delta = ((Long) session.getAttribute("deltaTiempo")).longValue();
                milli = milli - (delta);
            }
            tmpInicio = milli + "";
            session.setAttribute(ConstantsSolicitudHhpp.ATRIBUTO_TIEMPO_INICIO, tmpInicio);
        }
    }

    /**
     * Función que obtiene valores de tipo de acción de solicitud básica. la
     * base de datos
     *
     * @author Juan David Hernandez
     */
    public void obtenerTipoAccionSolicitudList() {
        try {
            CmtTipoBasicaMgl cmtTipoBasicaMgl1;

            cmtTipoBasicaMgl1 = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_ACCION_SOLICITUD);
            tipoAccionSolicitudBasicaList
                    = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl1);
        } catch (ApplicationException e) {
            String msn = "Error al realizar cargue de listado de tipo de acción ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al realizar cargue de listado de tipo de acción ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Obtiene el listado de centro poblado por ciudad de la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void obtenerCentroPobladoList() {
        try {
            centroPobladoList = geograficoPoliticoMglFacadeLocal
                    .findCentroPoblado(ciudadGpo.getGpoId());

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al obtener listado de centro poblado " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener listado de centro poblado " + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Error al obtener listado de departamentos " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener listado de departamentos " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Obtiene el listado de ciudades de la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void obtenerCiudadesList() {
        try {
            ciudadesList
                    = geograficoPoliticoMglFacadeLocal.findCiudades(departamentoGpo.getGpoId());
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al obtener listado de departamentos" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener listado de departamentos" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que obtiene tipo de tecnología
     *
     * @author Juan David Hernandez
     */
    public void obtenerTipoTecnologiaList() {
        try {
            CmtTipoBasicaMgl tipoBasicaTipoTecnologia;
            tipoBasicaTipoTecnologia = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TECNOLOGIA);
            tipoTecnologiaBasicaList
                    = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoTecnologia);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al obtener el tipo de tecnología" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener el tipo de tecnología" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función utilizada para obtener el listado de tipos de solicitudes desde
     * la base de datos
     *
     * @author Juan David Hernandez
     */
    public void obtenerListadoAreaCanalList() {
        try {
            areaCanalList
                    = parametrosCallesFacade.findByTipo("TIPO_SOLICITUD");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            FacesUtil.mostrarMensajeError("Error al realizar consulta para obtener listado de"
                    + " tipo de solicitudes" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Funcion utilizada para obtener los listados utilizados en la pantalla
     * desde la base de datos
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    public void cargarListadosConfiguracion() throws ApplicationException {
        try {
            configurationAddressComponent
                    = componenteDireccionesMglFacade
                    .getConfiguracionComponente();

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
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            FacesUtil.mostrarMensajeError("Error al realizar consultas para obtener configuración "
                    + "de listados" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Método creado para eliminar un archivo de Escalamiento HHPP
     *
     * @param archivo es el archivo que se va a eliminar
     * @return mensaje de borrado satisfactorio
     */
    public String eliminarArchivoEscalamientoHHPP(File archivo) {

        if (lstFilesEscalamientoHHPP.remove(archivo)) {
            FileToolUtils.deleteFile(archivo);
            String msg = "Archivo borrado satisfatoriamente";
            showPopupMessage(MessageSeverityEnum.INFO, msg);
        }

        return "";
    }

    /**
     * Método creado para guardar los archivos para el Escalamiento HHPP
     *
     * @return ""
     * @throws IOException
     * @author José René Miranda de la O  - ALGARTECH
     */
    public String guardarArchivoEscalamientoHHPP() throws IOException {

        Date fecha = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String fechaN = format.format(fecha);


        if (lstFilesEscalamientoHHPP.size() >= 10) {
            String msg = "Solo se pueden subir un máximo de 10 archivos";
            showPopupMessage(MessageSeverityEnum.ERROR, msg);
        } else {
            if (fileEscalamientoHHPP != null && StringUtils.isNotEmpty(fileEscalamientoHHPP.getFileName())) {
                long kb = fileEscalamientoHHPP.getSize()/1024;
                if (kb > 512) {
                    String msg = "El archivo que ud esta cargando, es mayor al permitido, debe ser menor o igual a 512 kb";
                    showPopupMessage(MessageSeverityEnum.ERROR, msg);
                    fileEscalamientoHHPP = null;
                    return "";
                }
                String fileName = fechaN;
                fileName += FilenameUtils.getName(fileEscalamientoHHPP.getFileName().trim());
                File file = new File(System.getProperty("user.dir"));
                String extension = FilenameUtils.getExtension(fileEscalamientoHHPP.getFileName());
                if (StringUtils.isBlank(extension)) {
                    String msg = "El archivo que ud esta cargando no cuenta con extensión, solamente se pueden cargar archivos con extensión";
                    showPopupMessage(MessageSeverityEnum.ERROR, msg);
                    return "";
                }
                File archive = File.createTempFile(fileName + "-", "." + extension, file);

                try(FileOutputStream output = new FileOutputStream(archive)) {
                    output.write(fileEscalamientoHHPP.getContent());
                } catch (IOException e) {
                    LOGGER.error("Error al escribir el fichero escalamiento HHPP: ", e);
                }
                lstFilesEscalamientoHHPP.add(archive);
            } else {
                String msg = "Debe seleccionar un archivo para guardar";
                showPopupMessage(MessageSeverityEnum.ERROR, msg);
            }
        }
        return "";
    }

    public String nombreArchivo(File archive) {

        String name = archive.getName();
        String[] partsUrls = name.split("-");
        name = partsUrls[0];

        return name;
    }

    /**
     * Función redirecciona a la pantalla del estado de solicitud.
     *
     * @author Juan David Hernandez
     */
    public void goBackEstadoSolicitud() {
        try {
            ExternalContext context =
                    FacesContext.getCurrentInstance().getExternalContext();
            String contextPath = context.getRequestContextPath();

            context.redirect(contextPath + "/view/MGL/VT/solicitudes/estadoSolicitud/estadoSolicitudHhpp.jsf?solicitud="+solicitudSeleccionada.getIdSolicitud());

        } catch (Exception e) {
            String msn = "Error al ir a pantalla de listado estado de solicitud";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función redirecciona a la pantalla del listado de solicitudes . y
     * desbloquea la solicitud si no fue gestionada.
     *
     * @author Juan David Hernandez
     */
    public void goBackSolicitudDthList() {
        try {

            SolicitudSessionBean solicitudSessionBean
                    = JSFUtil.getSessionBean(SolicitudSessionBean.class);
            Objects.requireNonNull(solicitudSessionBean, "Los datos de SolicitudSessionBean son nulos.");

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

    public void consultarTipoGestion() {
        try {
            final FiltroConjsultaBasicasDto filtroConjsultaBasicasDto = new FiltroConjsultaBasicasDto();
            filtroConjsultaBasicasDto.setIdTipoBasica(new BigDecimal("141236"));
            tablasBasicasMglList = cmtBasicaMglFacade.findByFiltro(filtroConjsultaBasicasDto);
        } catch (ApplicationException e) {
            String msn = "Se presenta error al filtrar los registros: " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TablasBasicasMglBean: consultarByTipoTablaBasica(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void consultarResultadoGestion() {
        try {
            final FiltroConjsultaBasicasDto filtroConjsultaBasicasDto = new FiltroConjsultaBasicasDto();
            filtroConjsultaBasicasDto.setIdTipoBasica(new BigDecimal("141235"));
            tablasBasicasResultadoMglList = cmtBasicaMglFacade.findByFiltro(filtroConjsultaBasicasDto);
        } catch (ApplicationException e) {
            String msn = "Se presenta error al filtrar los registros: " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TablasBasicasMglBean: consultarByTipoTablaBasica(). " + e.getMessage(), e, LOGGER);
        }
    }

     public void guardarNota() {
        try {
            if (validarCamposNota()) {
                cmtNotasSolicitudVtMgl.setSolicitud(solicitudSeleccionada);
                cmtNotasSolicitudVtMgl.setTipoNotaObj(tipoNotaSelected);
                notasSolicitudVtMglFacadeLocal.setUser(usuarioVt, perfilVt);
                cmtNotasSolicitudVtMgl = notasSolicitudVtMglFacadeLocal
                        .crearNotSol(cmtNotasSolicitudVtMgl);
                if (cmtNotasSolicitudVtMgl != null
                        && cmtNotasSolicitudVtMgl.getNotasId() != null) {
                    findNotasBySolicitud();
                    tipoNotaSelected = new CmtBasicaMgl();
                    cmtNotasSolicitudVtMgl = new CmtNotasSolicitudVtMgl();
                    showPopupMessage(MessageSeverityEnum.INFO, "Nota agregada correctamente.");
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
                    showPopupMessage(MessageSeverityEnum.INFO, "Comentario añadido correctamente.");
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

    public void limpiarCamposNota() {
        cmtNotasSolicitudVtMgl = new CmtNotasSolicitudVtMgl();
        tipoNotaSelected = new CmtBasicaMgl();
    }

    private boolean validarCamposNota() {
        try {
            if (cmtNotasSolicitudVtMgl == null) {
                String msn = "Ha ocurrido un error intentando guardar una nota."
                        + " Intente más tarde por favor.";
                showPopupMessage(MessageSeverityEnum.WARN, msn);
                return false;
            } else {
                if (cmtNotasSolicitudVtMgl.getDescripcion() == null
                        || cmtNotasSolicitudVtMgl.getDescripcion().isEmpty()) {
                    String msn = "Ingrese una descripción a la nota por favor.";
                    showPopupMessage(MessageSeverityEnum.WARN, msn);
                    return false;
                } else {
                    if (tipoNotaSelected.getBasicaId() == null) {
                        String msn = "Ingrese un tipo de nota por favor.";
                        showPopupMessage(MessageSeverityEnum.WARN, msn);
                        return false;
                    } else {
                        if (cmtNotasSolicitudVtMgl.getNota() == null
                                || cmtNotasSolicitudVtMgl.getNota().isEmpty()) {
                            String msn = "Ingrese la nota que desea guardar"
                                    + " por favor.";
                            showPopupMessage(MessageSeverityEnum.WARN, msn);
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

    private boolean validarComentarioNota() {
        try {
            if (notaComentarioStr == null || notaComentarioStr.isEmpty()) {
                String msn = "Ingrese el comentario de la nota por favor."
                        + " Intente más tarde por favor.";
                showPopupMessage(MessageSeverityEnum.WARN, msn);
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

    public void findNotasBySolicitud() {
        try {
            notasSolicitudList = notasSolicitudVtMglFacadeLocal
                    .findNotasBySolicitudId(solicitudSeleccionada.getIdSolicitud());
        } catch (ApplicationException e) {
            String msn = "Error al obtener tiempos de la solicitud ";
            FacesUtil.mostrarMensajeError(msn+ e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al obtener tiempos de la solicitud ";
            FacesUtil.mostrarMensajeError(msn+ e.getMessage() , e, LOGGER);
        }
    }

    public void cambiarTab(String sSeleccionado) {
        try {
            ConstantsCM.TABS_MOD_CM tabSelected = ConstantsCM.TABS_MOD_CM.valueOf(sSeleccionado);
            switch (tabSelected) {
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
                default:
                    String msg = "La opción seleccionada no es valida para cambiar Tap";
                    showPopupMessage(MessageSeverityEnum.WARN, msg);
            }
        } catch (Exception e) {
            String msn = "Error al realizar el cambio de Tab";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Se encarga de mostrar el popup al usuario con el mensaje recibido.
     *
     * @param severity {@link MessageSeverityEnum} Tipo de severidad del mensaje
     * @param msg      {@link String} Mensaje a mostrar en el popup.
     * @author Gildardo Mora
     */
    private static void showPopupMessage(MessageSeverityEnum severity, String msg) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(severity.getSeverity(), msg, ""));
    }

    public void obtenerTrackBySolicitud() {
        try {
            cmtTiempoSolicitudMgl = cmtTiempoSolicitudMglFacadeLocal
                    .findTiemposBySolicitud(solicitudIdSeleccionada);
            if (cmtTiempoSolicitudMgl != null && cmtTiempoSolicitudMgl.getTiempoEspera().equalsIgnoreCase(ConstantsSolicitudHhpp.DEFAULT_TIME)) {
                cmtTiempoSolicitudMgl.setTiempoEspera(cmtTiempoSolicitudMglToCreate.getTiempoEspera());
                cmtTiempoSolicitudMgl.setTiempoTotal(getTiempo(solicitudSeleccionada.getFechaIngreso(), new Date()));
            }
        } catch (ApplicationException e) {
            String msn = "Error al obtener registro de track de la solicitud ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al obtener registro de track de la solicitud ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    public String getTiempo(Date dateInicio, Date dateFin) {
        String result = "00:00:00";
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

            String hoursStr = (hours < 10 ? "0" + hours
                    : String.valueOf(hours));
            String minutesStr = (minutes < 10 ? "0" + minutes
                    : String.valueOf(minutes));
            String secondsStr = (seconds < 10 ? "0" + seconds
                    : String.valueOf(seconds));
            result = hoursStr + ":" + minutesStr + ":" + secondsStr;
        }
        return result;
    }

    public void showSolicitud() {
        showSolicitud = true;
        showTrack = false;
        showNotas = false;
    }

    public void showTrack() {
        showSolicitud = false;
        showTrack = true;
        showNotas = false;
    }

    public void showNotas() {
        showSolicitud = false;
        showTrack = false;
        showNotas = true;
    }

    public void asignarTiempoEsperaGestion() {
        try {
            cmtTiempoSolicitudMglToCreate.setTiempoEspera(getTiempo(solicitudSeleccionada.getFechaIngreso(),
                    new Date()));
        } catch (Exception e) {
            String msn = "Error al consulta tiempo de espera de la solicitud ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    public void mostarComentario(CmtNotasSolicitudVtMgl nota) {
        cmtNotasSolicitudVtMgl = nota;
        tipoNotaSelected = nota.getTipoNotaObj();
        cambiarTab("NOTAS");
    }

    public CmtTipoSolicitudMgl obtenerTipoSolicitud() {
        try {
            return cmtTipoSolicitudMgl = cmtTipoSolicitudMglFacadeLocal
                    .findTipoSolicitudByAbreviatura(solicitudSeleccionada.getTipo());
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

    public void obtenerColorAlerta(List<Solicitud> solicitudesList,
                                   CmtTipoSolicitudMgl cmtTipoSolicitudMgl) {
        try {
            if (cmtTipoSolicitudMgl != null) {
                for (Solicitud solicitud1 : solicitudesList) {
                    solicitud1.setColorAlerta(solicitudFacadeLocal
                            .obtenerColorAlerta(cmtTipoSolicitudMgl,
                                    solicitudSeleccionada.getFechaIngreso()));
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al establecer el valor de la alerta ans" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al establecer el valor de la alerta ans" + e.getMessage() , e, LOGGER);
        }
    }

    public String obtenerTipoSolicitudStr(final String tipoAccionSolicitud) throws ApplicationException {
        String tipoSolStr = null;
        try {
            if (tipoAccionSolicitud != null && tipoAccionSolicitudBasicaList != null) {
                for (CmtBasicaMgl tipoAccion : tipoAccionSolicitudBasicaList) {
                    if (tipoAccionSolicitud.equalsIgnoreCase(tipoAccion.getCodigoBasica())) {
                        tipoSolStr = tipoAccion.getNombreBasica();
                        break;
                    }
                }
            }
        } catch (RuntimeException e) {
            LOGGER.error("Error en obtenerTipoSolicitud, estableciendo el tipo de solicitud " +e.getMessage());
            throw new ApplicationException("Error en obtenerTipoSolicitud, estableciendo el tipo de solicitud: " +e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en obtenerTipoSolicitud, estableciendo el tipo de solicitud " +e.getMessage());
            throw new ApplicationException("Error en obtenerTipoSolicitud, estableciendo el tipo de solicitud: " +e.getMessage(), e);
        }
        return tipoSolStr;
    }


    /**
     * get field
     *
     * @return facesContext
     */
    public FacesContext getFacesContext() {
        return this.facesContext;
    }

    /**
     * set field
     *
     * @param facesContext
     */
    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    /**
     * get field
     *
     * @return securityLogin
     */
    public SecurityLogin getSecurityLogin() {
        return this.securityLogin;
    }

    /**
     * set field
     *
     * @param securityLogin
     */
    public void setSecurityLogin(SecurityLogin securityLogin) {
        this.securityLogin = securityLogin;
    }

    /**
     * get field
     *
     * @return session
     */
    public HttpSession getSession() {
        return this.session;
    }

    /**
     * set field
     *
     * @param session
     */
    public void setSession(HttpSession session) {
        this.session = session;
    }

    /**
     * get field
     *
     * @return response
     */
    public HttpServletResponse getResponse() {
        return this.response;
    }

    /**
     * set field
     *
     * @param response
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * get field
     *
     * @return usuarioLogin
     */
    public UsuariosServicesDTO getUsuarioLogin() {
        return this.usuarioLogin;
    }

    /**
     * set field
     *
     * @param usuarioLogin
     */
    public void setUsuarioLogin(UsuariosServicesDTO usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    /**
     * get field
     *
     * @return rolesUsuarioList
     */
    public List<CmtTipoSolicitudMgl> getRolesUsuarioList() {
        return this.rolesUsuarioList;
    }

    /**
     * set field
     *
     * @param rolesUsuarioList
     */
    public void setRolesUsuarioList(List<CmtTipoSolicitudMgl> rolesUsuarioList) {
        this.rolesUsuarioList = rolesUsuarioList;
    }

    /**
     * get field
     *
     * @return tipoTecnologiaBasicaList
     */
    public List<CmtBasicaMgl> getTipoTecnologiaBasicaList() {
        return this.tipoTecnologiaBasicaList;
    }

    /**
     * set field
     *
     * @param tipoTecnologiaBasicaList
     */
    public void setTipoTecnologiaBasicaList(List<CmtBasicaMgl> tipoTecnologiaBasicaList) {
        this.tipoTecnologiaBasicaList = tipoTecnologiaBasicaList;
    }

    /**
     * get field
     *
     * @return tipoAccionSolicitudBasicaList
     */
    public List<CmtBasicaMgl> getTipoAccionSolicitudBasicaList() {
        return this.tipoAccionSolicitudBasicaList;
    }

    /**
     * set field
     *
     * @param tipoAccionSolicitudBasicaList
     */
    public void setTipoAccionSolicitudBasicaList(List<CmtBasicaMgl> tipoAccionSolicitudBasicaList) {
        this.tipoAccionSolicitudBasicaList = tipoAccionSolicitudBasicaList;
    }

    /**
     * get field
     *
     * @return centroPobladoList
     */
    public List<GeograficoPoliticoMgl> getCentroPobladoList() {
        return this.centroPobladoList;
    }

    /**
     * set field
     *
     * @param centroPobladoList
     */
    public void setCentroPobladoList(List<GeograficoPoliticoMgl> centroPobladoList) {
        this.centroPobladoList = centroPobladoList;
    }

    /**
     * get field
     *
     * @return departamentoList
     */
    public List<GeograficoPoliticoMgl> getDepartamentoList() {
        return this.departamentoList;
    }

    /**
     * set field
     *
     * @param departamentoList
     */
    public void setDepartamentoList(List<GeograficoPoliticoMgl> departamentoList) {
        this.departamentoList = departamentoList;
    }

    /**
     * get field
     *
     * @return ciudadesList
     */
    public List<GeograficoPoliticoMgl> getCiudadesList() {
        return this.ciudadesList;
    }

    /**
     * set field
     *
     * @param ciudadesList
     */
    public void setCiudadesList(List<GeograficoPoliticoMgl> ciudadesList) {
        this.ciudadesList = ciudadesList;
    }

    /**
     * get field
     *
     * @return areaCanalList
     */
    public List<ParametrosCalles> getAreaCanalList() {
        return this.areaCanalList;
    }

    /**
     * set field
     *
     * @param areaCanalList
     */
    public void setAreaCanalList(List<ParametrosCalles> areaCanalList) {
        this.areaCanalList = areaCanalList;
    }

    /**
     * get field
     *
     * @return tecArchivosEscalamientosHHPP
     */
    public List<TecArchivosEscalamientosHHPP> getTecArchivosEscalamientosHHPP() {
        return this.tecArchivosEscalamientosHHPP;
    }

    /**
     * set field
     *
     * @param tecArchivosEscalamientosHHPP
     */
    public void setTecArchivosEscalamientosHHPP(List<TecArchivosEscalamientosHHPP> tecArchivosEscalamientosHHPP) {
        this.tecArchivosEscalamientosHHPP = tecArchivosEscalamientosHHPP;
    }

    /**
     * get field
     *
     * @return tecArchivosEstadoEscalamientosHHPP
     */
    public List<TecArchivosEscalamientosHHPP> getTecArchivosEstadoEscalamientosHHPP() {
        return this.tecArchivosEstadoEscalamientosHHPP;
    }

    /**
     * set field
     *
     * @param tecArchivosEstadoEscalamientosHHPP
     */
    public void setTecArchivosEstadoEscalamientosHHPP(List<TecArchivosEscalamientosHHPP> tecArchivosEstadoEscalamientosHHPP) {
        this.tecArchivosEstadoEscalamientosHHPP = tecArchivosEstadoEscalamientosHHPP;
    }

    /**
     * get field
     *
     * @return observacionesList
     */
    public List<TecObservacionSolicitudHHPP> getObservacionesList() {
        return this.observacionesList;
    }

    /**
     * set field
     *
     * @param observacionesList
     */
    public void setObservacionesList(List<TecObservacionSolicitudHHPP> observacionesList) {
        this.observacionesList = observacionesList;
    }

    /**
     * get field
     *
     * @return tablasBasicasMglList
     */
    public List<CmtBasicaMgl> getTablasBasicasMglList() {
        return this.tablasBasicasMglList;
    }

    /**
     * set field
     *
     * @param tablasBasicasMglList
     */
    public void setTablasBasicasMglList(List<CmtBasicaMgl> tablasBasicasMglList) {
        this.tablasBasicasMglList = tablasBasicasMglList;
    }

    /**
     * get field
     *
     * @return tablasBasicasResultadoMglList
     */
    public List<CmtBasicaMgl> getTablasBasicasResultadoMglList() {
        return this.tablasBasicasResultadoMglList;
    }

    /**
     * set field
     *
     * @param tablasBasicasResultadoMglList
     */
    public void setTablasBasicasResultadoMglList(List<CmtBasicaMgl> tablasBasicasResultadoMglList) {
        this.tablasBasicasResultadoMglList = tablasBasicasResultadoMglList;
    }

    /**
     * get field
     *
     * @return ckList
     */
    public List<OpcionIdNombre> getCkList() {
        return this.ckList;
    }

    /**
     * set field
     *
     * @param ckList
     */
    public void setCkList(List<OpcionIdNombre> ckList) {
        this.ckList = ckList;
    }

    /**
     * get field
     *
     * @return bmList
     */
    public List<OpcionIdNombre> getBmList() {
        return this.bmList;
    }

    /**
     * set field
     *
     * @param bmList
     */
    public void setBmList(List<OpcionIdNombre> bmList) {
        this.bmList = bmList;
    }

    /**
     * get field
     *
     * @return itList
     */
    public List<OpcionIdNombre> getItList() {
        return this.itList;
    }

    /**
     * set field
     *
     * @param itList
     */
    public void setItList(List<OpcionIdNombre> itList) {
        this.itList = itList;
    }

    /**
     * get field
     *
     * @return aptoList
     */
    public List<OpcionIdNombre> getAptoList() {
        return this.aptoList;
    }

    /**
     * set field
     *
     * @param aptoList
     */
    public void setAptoList(List<OpcionIdNombre> aptoList) {
        this.aptoList = aptoList;
    }

    /**
     * get field
     *
     * @return complementoList
     */
    public List<OpcionIdNombre> getComplementoList() {
        return this.complementoList;
    }

    /**
     * set field
     *
     * @param complementoList
     */
    public void setComplementoList(List<OpcionIdNombre> complementoList) {
        this.complementoList = complementoList;
    }

    /**
     * get field
     *
     * @return lstFilesEscalamientoHHPP
     */
    public List<File> getLstFilesEscalamientoHHPP() {
        return this.lstFilesEscalamientoHHPP;
    }

    /**
     * set field
     *
     * @param lstFilesEscalamientoHHPP
     */
    public void setLstFilesEscalamientoHHPP(List<File> lstFilesEscalamientoHHPP) {
        this.lstFilesEscalamientoHHPP = lstFilesEscalamientoHHPP;
    }

    /**
     * get field
     *
     * @return notasSolicitudList
     */
    public List<CmtNotasSolicitudVtMgl> getNotasSolicitudList() {
        return this.notasSolicitudList;
    }

    /**
     * set field
     *
     * @param notasSolicitudList
     */
    public void setNotasSolicitudList(List<CmtNotasSolicitudVtMgl> notasSolicitudList) {
        this.notasSolicitudList = notasSolicitudList;
    }

    /**
     * get field
     *
     * @return tipoNotaList
     */
    public List<CmtBasicaMgl> getTipoNotaList() {
        return this.tipoNotaList;
    }

    /**
     * set field
     *
     * @param tipoNotaList
     */
    public void setTipoNotaList(List<CmtBasicaMgl> tipoNotaList) {
        this.tipoNotaList = tipoNotaList;
    }

    /**
     * get field
     *
     * @return solicitudSeleccionada
     */
    public Solicitud getSolicitudSeleccionada() {
        return this.solicitudSeleccionada;
    }

    /**
     * set field
     *
     * @param solicitudSeleccionada
     */
    public void setSolicitudSeleccionada(Solicitud solicitudSeleccionada) {
        this.solicitudSeleccionada = solicitudSeleccionada;
    }

    /**
     * get field
     *
     * @return cmtDireccionDetalladaMgl
     */
    public CmtDireccionDetalladaMgl getCmtDireccionDetalladaMgl() {
        return this.cmtDireccionDetalladaMgl;
    }

    /**
     * set field
     *
     * @param cmtDireccionDetalladaMgl
     */
    public void setCmtDireccionDetalladaMgl(CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl) {
        this.cmtDireccionDetalladaMgl = cmtDireccionDetalladaMgl;
    }

    /**
     * get field
     *
     * @return drDireccion
     */
    public DrDireccion getDrDireccion() {
        return this.drDireccion;
    }

    /**
     * set field
     *
     * @param drDireccion
     */
    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    /**
     * get field
     *
     * @return tipoTecnologiaBasica
     */
    public CmtBasicaMgl getTipoTecnologiaBasica() {
        return this.tipoTecnologiaBasica;
    }

    /**
     * set field
     *
     * @param tipoTecnologiaBasica
     */
    public void setTipoTecnologiaBasica(CmtBasicaMgl tipoTecnologiaBasica) {
        this.tipoTecnologiaBasica = tipoTecnologiaBasica;
    }

    /**
     * get field
     *
     * @return ciudadGpo
     */
    public GeograficoPoliticoMgl getCiudadGpo() {
        return this.ciudadGpo;
    }

    /**
     * set field
     *
     * @param ciudadGpo
     */
    public void setCiudadGpo(GeograficoPoliticoMgl ciudadGpo) {
        this.ciudadGpo = ciudadGpo;
    }

    /**
     * get field
     *
     * @return departamentoGpo
     */
    public GeograficoPoliticoMgl getDepartamentoGpo() {
        return this.departamentoGpo;
    }

    /**
     * set field
     *
     * @param departamentoGpo
     */
    public void setDepartamentoGpo(GeograficoPoliticoMgl departamentoGpo) {
        this.departamentoGpo = departamentoGpo;
    }

    /**
     * get field
     *
     * @return centroPobladoGpo
     */
    public GeograficoPoliticoMgl getCentroPobladoGpo() {
        return this.centroPobladoGpo;
    }

    /**
     * set field
     *
     * @param centroPobladoGpo
     */
    public void setCentroPobladoGpo(GeograficoPoliticoMgl centroPobladoGpo) {
        this.centroPobladoGpo = centroPobladoGpo;
    }

    /**
     * get field
     *
     * @return configurationAddressComponent
     */
    public ConfigurationAddressComponent getConfigurationAddressComponent() {
        return this.configurationAddressComponent;
    }

    /**
     * set field
     *
     * @param configurationAddressComponent
     */
    public void setConfigurationAddressComponent(ConfigurationAddressComponent configurationAddressComponent) {
        this.configurationAddressComponent = configurationAddressComponent;
    }

    /**
     * get field
     *
     * @return cmtNotasSolicitudVtMgl
     */
    public CmtNotasSolicitudVtMgl getCmtNotasSolicitudVtMgl() {
        return this.cmtNotasSolicitudVtMgl;
    }

    /**
     * set field
     *
     * @param cmtNotasSolicitudVtMgl
     */
    public void setCmtNotasSolicitudVtMgl(CmtNotasSolicitudVtMgl cmtNotasSolicitudVtMgl) {
        this.cmtNotasSolicitudVtMgl = cmtNotasSolicitudVtMgl;
    }

    /**
     * get field
     *
     * @return tipoNotaSelected
     */
    public CmtBasicaMgl getTipoNotaSelected() {
        return this.tipoNotaSelected;
    }

    /**
     * set field
     *
     * @param tipoNotaSelected
     */
    public void setTipoNotaSelected(CmtBasicaMgl tipoNotaSelected) {
        this.tipoNotaSelected = tipoNotaSelected;
    }

    /**
     * get field
     *
     * @return cmtTiempoSolicitudMgl
     */
    public CmtTiempoSolicitudMgl getCmtTiempoSolicitudMgl() {
        return this.cmtTiempoSolicitudMgl;
    }

    /**
     * set field
     *
     * @param cmtTiempoSolicitudMgl
     */
    public void setCmtTiempoSolicitudMgl(CmtTiempoSolicitudMgl cmtTiempoSolicitudMgl) {
        this.cmtTiempoSolicitudMgl = cmtTiempoSolicitudMgl;
    }

    /**
     * get field
     *
     * @return cmtTiempoSolicitudMglToCreate
     */
    public CmtTiempoSolicitudMgl getCmtTiempoSolicitudMglToCreate() {
        return this.cmtTiempoSolicitudMglToCreate;
    }

    /**
     * set field
     *
     * @param cmtTiempoSolicitudMglToCreate
     */
    public void setCmtTiempoSolicitudMglToCreate(CmtTiempoSolicitudMgl cmtTiempoSolicitudMglToCreate) {
        this.cmtTiempoSolicitudMglToCreate = cmtTiempoSolicitudMglToCreate;
    }

    /**
     * get field
     *
     * @return fileEscalamientoHHPP
     */
    public UploadedFile getFileEscalamientoHHPP() {
        return this.fileEscalamientoHHPP;
    }

    /**
     * set field
     *
     * @param fileEscalamientoHHPP
     */
    public void setFileEscalamientoHHPP(UploadedFile fileEscalamientoHHPP) {
        this.fileEscalamientoHHPP = fileEscalamientoHHPP;
    }

    /**
     * get field
     *
     * @return dateInicioCreacion
     */
    public Date getDateInicioCreacion() {
        return this.dateInicioCreacion;
    }

    /**
     * set field
     *
     * @param dateInicioCreacion
     */
    public void setDateInicioCreacion(Date dateInicioCreacion) {
        this.dateInicioCreacion = dateInicioCreacion;
    }

    /**
     * get field
     *
     * @return solicitudIdSeleccionada
     */
    public BigDecimal getSolicitudIdSeleccionada() {
        return this.solicitudIdSeleccionada;
    }

    /**
     * set field
     *
     * @param solicitudIdSeleccionada
     */
    public void setSolicitudIdSeleccionada(BigDecimal solicitudIdSeleccionada) {
        this.solicitudIdSeleccionada = solicitudIdSeleccionada;
    }

    /**
     * get field
     *
     * @return notaComentarioStr
     */
    public String getNotaComentarioStr() {
        return this.notaComentarioStr;
    }

    /**
     * set field
     *
     * @param notaComentarioStr
     */
    public void setNotaComentarioStr(String notaComentarioStr) {
        this.notaComentarioStr = notaComentarioStr;
    }

    /**
     * get field
     *
     * @return usuarioVt
     */
    public String getUsuarioVt() {
        return this.usuarioVt;
    }

    /**
     * set field
     *
     * @param usuarioVt
     */
    public void setUsuarioVt(String usuarioVt) {
        this.usuarioVt = usuarioVt;
    }

    /**
     * get field
     *
     * @return cedulaUsuarioVt
     */
    public String getCedulaUsuarioVt() {
        return this.cedulaUsuarioVt;
    }

    /**
     * set field
     *
     * @param cedulaUsuarioVt
     */
    public void setCedulaUsuarioVt(String cedulaUsuarioVt) {
        this.cedulaUsuarioVt = cedulaUsuarioVt;
    }

    /**
     * get field
     *
     * @return direccionRespuestaGeo
     */
    public String getDireccionRespuestaGeo() {
        return this.direccionRespuestaGeo;
    }

    /**
     * set field
     *
     * @param direccionRespuestaGeo
     */
    public void setDireccionRespuestaGeo(String direccionRespuestaGeo) {
        this.direccionRespuestaGeo = direccionRespuestaGeo;
    }

    /**
     * set field
     *
     * @param complementoDireccion
     */
    public void setComplementoDireccion(String complementoDireccion) {
        this.complementoDireccion = complementoDireccion;
    }

    /**
     * get field
     *
     * @return tmpInicio
     */
    public String getTmpInicio() {
        return this.tmpInicio;
    }

    /**
     * set field
     *
     * @param tmpInicio
     */
    public void setTmpInicio(String tmpInicio) {
        this.tmpInicio = tmpInicio;
    }

    /**
     * get field
     *
     * @return deltaTiempo
     */
    public String getDeltaTiempo() {
        return this.deltaTiempo;
    }

    /**
     * set field
     *
     * @param deltaTiempo
     */
    public void setDeltaTiempo(String deltaTiempo) {
        this.deltaTiempo = deltaTiempo;
    }

    /**
     * get field
     *
     * @return tmpFin
     */
    public String getTmpFin() {
        return this.tmpFin;
    }

    /**
     * set field
     *
     * @param tmpFin
     */
    public void setTmpFin(String tmpFin) {
        this.tmpFin = tmpFin;
    }

    /**
     * get field
     *
     * @return timeSol
     */
    public String getTimeSol() {
        return this.timeSol;
    }

    /**
     * set field
     *
     * @param timeSol
     */
    public void setTimeSol(String timeSol) {
        this.timeSol = timeSol;
    }

    /**
     * get field
     *
     * @return departamento
     */
    public String getDepartamento() {
        return this.departamento;
    }

    /**
     * set field
     *
     * @param departamento
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * get field
     *
     * @return tipoTecnologia
     */
    public String getTipoTecnologia() {
        return this.tipoTecnologia;
    }

    /**
     * set field
     *
     * @param tipoTecnologia
     */
    public void setTipoTecnologia(String tipoTecnologia) {
        this.tipoTecnologia = tipoTecnologia;
    }

    /**
     * get field
     *
     * @return tipoDireccion
     */
    public String getTipoDireccion() {
        return this.tipoDireccion;
    }

    /**
     * set field
     *
     * @param tipoDireccion
     */
    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    /**
     * get field
     *
     * @return ccmm
     */
    public String getCcmm() {
        return this.ccmm;
    }

    /**
     * set field
     *
     * @param ccmm
     */
    public void setCcmm(String ccmm) {
        this.ccmm = ccmm;
    }

    /**
     * get field
     *
     * @return solicitudOT
     */
    public String getSolicitudOT() {
        return this.solicitudOT;
    }

    /**
     * set field
     *
     * @param solicitudOT
     */
    public void setSolicitudOT(String solicitudOT) {
        this.solicitudOT = solicitudOT;
    }

    /**
     * get field
     *
     * @return tipoGestion
     */
    public String getTipoGestion() {
        return this.tipoGestion;
    }

    /**
     * set field
     *
     * @param tipoGestion
     */
    public void setTipoGestion(String tipoGestion) {
        this.tipoGestion = tipoGestion;
    }

    /**
     * get field
     *
     * @return tipoHHPP
     */
    public String getTipoHHPP() {
        return this.tipoHHPP;
    }

    /**
     * set field
     *
     * @param tipoHHPP
     */
    public void setTipoHHPP(String tipoHHPP) {
        this.tipoHHPP = tipoHHPP;
    }

    /**
     * get field
     *
     * @return direccionCk
     */
    public String getDireccionCk() {
        return this.direccionCk;
    }

    /**
     * set field
     *
     * @param direccionCk
     */
    public void setDireccionCk(String direccionCk) {
        this.direccionCk = direccionCk;
    }

    /**
     * get field
     *
     * @return area
     */
    public String getArea() {
        return this.area;
    }

    /**
     * set field
     *
     * @param area
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * get field
     *
     * @return tipoNivelApartamento
     */
    public String getTipoNivelApartamento() {
        return this.tipoNivelApartamento;
    }

    /**
     * set field
     *
     * @param tipoNivelApartamento
     */
    public void setTipoNivelApartamento(String tipoNivelApartamento) {
        this.tipoNivelApartamento = tipoNivelApartamento;
    }

    /**
     * get field
     *
     * @return tipoNivelComplemento
     */
    public String getTipoNivelComplemento() {
        return this.tipoNivelComplemento;
    }

    /**
     * set field
     *
     * @param tipoNivelComplemento
     */
    public void setTipoNivelComplemento(String tipoNivelComplemento) {
        this.tipoNivelComplemento = tipoNivelComplemento;
    }

    /**
     * get field
     *
     * @return accion
     */
    public String getAccion() {
        return this.accion;
    }

    /**
     * set field
     *
     * @param accion
     */
    public void setAccion(String accion) {
        this.accion = accion;
    }

    /**
     * get field
     *
     * @return resultadoGestion
     */
    public String getResultadoGestion() {
        return this.resultadoGestion;
    }

    /**
     * set field
     *
     * @param resultadoGestion
     */
    public void setResultadoGestion(String resultadoGestion) {
        this.resultadoGestion = resultadoGestion;
    }

    /**
     * get field
     *
     * @return respuestaActual
     */
    public String getRespuestaActual() {
        return this.respuestaActual;
    }

    /**
     * set field
     *
     * @param respuestaActual
     */
    public void setRespuestaActual(String respuestaActual) {
        this.respuestaActual = respuestaActual;
    }

    /**
     * get field
     *
     * @return usuarioGestionador
     */
    public String getUsuarioGestionador() {
        return this.usuarioGestionador;
    }

    /**
     * set field
     *
     * @param usuarioGestionador
     */
    public void setUsuarioGestionador(String usuarioGestionador) {
        this.usuarioGestionador = usuarioGestionador;
    }

    /**
     * get field
     *
     * @return observaciones
     */
    public String getObservaciones() {
        return this.observaciones;
    }

    /**
     * set field
     *
     * @param observaciones
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * get field
     *
     * @return selectedTab
     */
    public String getSelectedTab() {
        return this.selectedTab;
    }

    /**
     * set field
     *
     * @param selectedTab
     */
    public void setSelectedTab(String selectedTab) {
        this.selectedTab = selectedTab;
    }

    /**
     * get field
     *
     * @return ciudad
     */
    public BigDecimal getCiudad() {
        return this.ciudad;
    }

    /**
     * set field
     *
     * @param ciudad
     */
    public void setCiudad(BigDecimal ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * get field
     *
     * @return idCentroPoblado
     */
    public BigDecimal getIdCentroPoblado() {
        return this.idCentroPoblado;
    }

    /**
     * set field
     *
     * @param idCentroPoblado
     */
    public void setIdCentroPoblado(BigDecimal idCentroPoblado) {
        this.idCentroPoblado = idCentroPoblado;
    }

    /**
     * get field
     *
     * @return perfilVt
     */
    public int getPerfilVt() {
        return this.perfilVt;
    }

    /**
     * set field
     *
     * @param perfilVt
     */
    public void setPerfilVt(int perfilVt) {
        this.perfilVt = perfilVt;
    }

    /**
     * get field
     *
     * @return solicitudGestionada
     */
    public boolean isSolicitudGestionada() {
        return this.solicitudGestionada;
    }

    /**
     * set field
     *
     * @param solicitudGestionada
     */
    public void setSolicitudGestionada(boolean solicitudGestionada) {
        this.solicitudGestionada = solicitudGestionada;
    }

    /**
     * get field
     *
     * @return detalleSolicitud
     */
    public boolean isDetalleSolicitud() {
        return this.detalleSolicitud;
    }

    /**
     * set field
     *
     * @param detalleSolicitud
     */
    public void setDetalleSolicitud(boolean detalleSolicitud) {
        this.detalleSolicitud = detalleSolicitud;
    }

    /**
     * get field
     *
     * @return habilitarRR
     */
    public boolean isHabilitarRR() {
        return this.habilitarRR;
    }

    /**
     * set field
     *
     * @param habilitarRR
     */
    public void setHabilitarRR(boolean habilitarRR) {
        this.habilitarRR = habilitarRR;
    }

    /**
     * get field
     *
     * @return showCKPanel
     */
    public boolean isShowCKPanel() {
        return this.showCKPanel;
    }

    /**
     * set field
     *
     * @param showCKPanel
     */
    public void setShowCKPanel(boolean showCKPanel) {
        this.showCKPanel = showCKPanel;
    }

    /**
     * get field
     *
     * @return showBMPanel
     */
    public boolean isShowBMPanel() {
        return this.showBMPanel;
    }

    /**
     * set field
     *
     * @param showBMPanel
     */
    public void setShowBMPanel(boolean showBMPanel) {
        this.showBMPanel = showBMPanel;
    }

    /**
     * get field
     *
     * @return showITPanel
     */
    public boolean isShowITPanel() {
        return this.showITPanel;
    }

    /**
     * set field
     *
     * @param showITPanel
     */
    public void setShowITPanel(boolean showITPanel) {
        this.showITPanel = showITPanel;
    }

    /**
     * get field
     *
     * @return notGeoReferenciado
     */
    public boolean isNotGeoReferenciado() {
        return this.notGeoReferenciado;
    }

    /**
     * set field
     *
     * @param notGeoReferenciado
     */
    public void setNotGeoReferenciado(boolean notGeoReferenciado) {
        this.notGeoReferenciado = notGeoReferenciado;
    }

    /**
     * get field
     *
     * @return estadoSolicitud
     */
    public boolean isEstadoSolicitud() {
        return this.estadoSolicitud;
    }

    /**
     * set field
     *
     * @param estadoSolicitud
     */
    public void setEstadoSolicitud(boolean estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * get field
     *
     * @return showBack
     */
    public boolean isShowBack() {
        return this.showBack;
    }

    /**
     * set field
     *
     * @param showBack
     */
    public void setShowBack(boolean showBack) {
        this.showBack = showBack;
    }

    /**
     * get field
     *
     * @return showSolicitud
     */
    public boolean isShowSolicitud() {
        return this.showSolicitud;
    }

    /**
     * set field
     *
     * @param showSolicitud
     */
    public void setShowSolicitud(boolean showSolicitud) {
        this.showSolicitud = showSolicitud;
    }

    /**
     * get field
     *
     * @return showTrack
     */
    public boolean isShowTrack() {
        return this.showTrack;
    }

    /**
     * set field
     *
     * @param showTrack
     */
    public void setShowTrack(boolean showTrack) {
        this.showTrack = showTrack;
    }

    /**
     * get field
     *
     * @return showNotas
     */
    public boolean isShowNotas() {
        return this.showNotas;
    }

    /**
     * set field
     *
     * @param showNotas
     */
    public void setShowNotas(boolean showNotas) {
        this.showNotas = showNotas;
    }

    /**
     * get field @EJB
     *
     * @return usuariosFacade @EJB

     */
    public UsuarioServicesFacadeLocal getUsuariosFacade() {
        return this.usuariosFacade;
    }

    /**
     * set field @EJB
     *
     * @param usuariosFacade @EJB

     */
    public void setUsuariosFacade(UsuarioServicesFacadeLocal usuariosFacade) {
        this.usuariosFacade = usuariosFacade;
    }

    /**
     * get field @EJB
     *
     * @return cmtTiempoSolicitudMglFacadeLocal @EJB

     */
    public CmtTiempoSolicitudMglFacadeLocal getCmtTiempoSolicitudMglFacadeLocal() {
        return this.cmtTiempoSolicitudMglFacadeLocal;
    }

    /**
     * set field @EJB
     *
     * @param cmtTiempoSolicitudMglFacadeLocal @EJB

     */
    public void setCmtTiempoSolicitudMglFacadeLocal(CmtTiempoSolicitudMglFacadeLocal cmtTiempoSolicitudMglFacadeLocal) {
        this.cmtTiempoSolicitudMglFacadeLocal = cmtTiempoSolicitudMglFacadeLocal;
    }

    /**
     * get field @EJB
     *
     * @return cmtTipoSolicitudMglFacadeLocal @EJB

     */
    public CmtTipoSolicitudMglFacadeLocal getCmtTipoSolicitudMglFacadeLocal() {
        return this.cmtTipoSolicitudMglFacadeLocal;
    }

    /**
     * set field @EJB
     *
     * @param cmtTipoSolicitudMglFacadeLocal @EJB

     */
    public void setCmtTipoSolicitudMglFacadeLocal(CmtTipoSolicitudMglFacadeLocal cmtTipoSolicitudMglFacadeLocal) {
        this.cmtTipoSolicitudMglFacadeLocal = cmtTipoSolicitudMglFacadeLocal;
    }

    /**
     * get field @EJB
     *
     * @return solicitudFacadeLocal @EJB

     */
    public SolicitudFacadeLocal getSolicitudFacadeLocal() {
        return this.solicitudFacadeLocal;
    }

    /**
     * set field @EJB
     *
     * @param solicitudFacadeLocal @EJB

     */
    public void setSolicitudFacadeLocal(SolicitudFacadeLocal solicitudFacadeLocal) {
        this.solicitudFacadeLocal = solicitudFacadeLocal;
    }

    /**
     * get field @EJB
     *
     * @return cmtDireccionDetalleMglFacadeLocal @EJB

     */
    public CmtDireccionDetalleMglFacadeLocal getCmtDireccionDetalleMglFacadeLocal() {
        return this.cmtDireccionDetalleMglFacadeLocal;
    }

    /**
     * set field @EJB
     *
     * @param cmtDireccionDetalleMglFacadeLocal @EJB

     */
    public void setCmtDireccionDetalleMglFacadeLocal(CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMglFacadeLocal) {
        this.cmtDireccionDetalleMglFacadeLocal = cmtDireccionDetalleMglFacadeLocal;
    }

    /**
     * get field @EJB
     *
     * @return geograficoPoliticoMglFacadeLocal @EJB

     */
    public GeograficoPoliticoMglFacadeLocal getGeograficoPoliticoMglFacadeLocal() {
        return this.geograficoPoliticoMglFacadeLocal;
    }

    /**
     * set field @EJB
     *
     * @param geograficoPoliticoMglFacadeLocal @EJB

     */
    public void setGeograficoPoliticoMglFacadeLocal(GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal) {
        this.geograficoPoliticoMglFacadeLocal = geograficoPoliticoMglFacadeLocal;
    }

    /**
     * get field @EJB
     *
     * @return cmtTipoBasicaMgl @EJB

     */
    public CmtTipoBasicaMglFacadeLocal getCmtTipoBasicaMgl() {
        return this.cmtTipoBasicaMgl;
    }

    /**
     * set field @EJB
     *
     * @param cmtTipoBasicaMgl @EJB

     */
    public void setCmtTipoBasicaMgl(CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMgl) {
        this.cmtTipoBasicaMgl = cmtTipoBasicaMgl;
    }

    /**
     * get field @EJB
     *
     * @return cmtTipoBasicaMglFacadeLocal @EJB

     */
    public CmtTipoBasicaMglFacadeLocal getCmtTipoBasicaMglFacadeLocal() {
        return this.cmtTipoBasicaMglFacadeLocal;
    }

    /**
     * set field @EJB
     *
     * @param cmtTipoBasicaMglFacadeLocal @EJB

     */
    public void setCmtTipoBasicaMglFacadeLocal(CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal) {
        this.cmtTipoBasicaMglFacadeLocal = cmtTipoBasicaMglFacadeLocal;
    }

    /**
     * get field @EJB
     *
     * @return cmtBasicaMglFacadeLocal @EJB

     */
    public CmtBasicaMglFacadeLocal getCmtBasicaMglFacadeLocal() {
        return this.cmtBasicaMglFacadeLocal;
    }

    /**
     * set field @EJB
     *
     * @param cmtBasicaMglFacadeLocal @EJB

     */
    public void setCmtBasicaMglFacadeLocal(CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal) {
        this.cmtBasicaMglFacadeLocal = cmtBasicaMglFacadeLocal;
    }

    /**
     * get field @EJB
     *
     * @return parametrosCallesFacade @EJB

     */
    public ParametrosCallesFacadeLocal getParametrosCallesFacade() {
        return this.parametrosCallesFacade;
    }

    /**
     * set field @EJB
     *
     * @param parametrosCallesFacade @EJB

     */
    public void setParametrosCallesFacade(ParametrosCallesFacadeLocal parametrosCallesFacade) {
        this.parametrosCallesFacade = parametrosCallesFacade;
    }

    /**
     * get field @EJB
     *
     * @return tecArcEscalaHHPPFacadeLocal @EJB

     */
    public ITecArcEscalaHHPPFacadeLocal getTecArcEscalaHHPPFacadeLocal() {
        return this.tecArcEscalaHHPPFacadeLocal;
    }

    /**
     * set field @EJB
     *
     * @param tecArcEscalaHHPPFacadeLocal @EJB

     */
    public void setTecArcEscalaHHPPFacadeLocal(ITecArcEscalaHHPPFacadeLocal tecArcEscalaHHPPFacadeLocal) {
        this.tecArcEscalaHHPPFacadeLocal = tecArcEscalaHHPPFacadeLocal;
    }

    /**
     * get field @EJB
     *
     * @return componenteDireccionesMglFacade @EJB

     */
    public CmtComponenteDireccionesMglFacadeLocal getComponenteDireccionesMglFacade() {
        return this.componenteDireccionesMglFacade;
    }

    /**
     * set field @EJB
     *
     * @param componenteDireccionesMglFacade @EJB

     */
    public void setComponenteDireccionesMglFacade(CmtComponenteDireccionesMglFacadeLocal componenteDireccionesMglFacade) {
        this.componenteDireccionesMglFacade = componenteDireccionesMglFacade;
    }

    /**
     * get field @EJB
     *
     * @return cmtBasicaMglFacade @EJB

     */
    public CmtBasicaMglFacadeLocal getCmtBasicaMglFacade() {
        return this.cmtBasicaMglFacade;
    }

    /**
     * set field @EJB
     *
     * @param cmtBasicaMglFacade @EJB

     */
    public void setCmtBasicaMglFacade(CmtBasicaMglFacadeLocal cmtBasicaMglFacade) {
        this.cmtBasicaMglFacade = cmtBasicaMglFacade;
    }

    /**
     * get field @EJB
     *
     * @return observacionSolicitudHHPPFacadeLocal @EJB

     */
    public ITecObservacionSolicitudHHPPFacadeLocal getObservacionSolicitudHHPPFacadeLocal() {
        return this.observacionSolicitudHHPPFacadeLocal;
    }

    /**
     * set field @EJB
     *
     * @param observacionSolicitudHHPPFacadeLocal @EJB

     */
    public void setObservacionSolicitudHHPPFacadeLocal(ITecObservacionSolicitudHHPPFacadeLocal observacionSolicitudHHPPFacadeLocal) {
        this.observacionSolicitudHHPPFacadeLocal = observacionSolicitudHHPPFacadeLocal;
    }

    /**
     * get field @EJB
     *
     * @return notasSolicitudDetalleVtMglFacadeLocal @EJB

     */
    public CmtNotasSolicitudDetalleVtMglFacadeLocal getNotasSolicitudDetalleVtMglFacadeLocal() {
        return this.notasSolicitudDetalleVtMglFacadeLocal;
    }

    /**
     * set field @EJB
     *
     * @param notasSolicitudDetalleVtMglFacadeLocal @EJB

     */
    public void setNotasSolicitudDetalleVtMglFacadeLocal(CmtNotasSolicitudDetalleVtMglFacadeLocal notasSolicitudDetalleVtMglFacadeLocal) {
        this.notasSolicitudDetalleVtMglFacadeLocal = notasSolicitudDetalleVtMglFacadeLocal;
    }

    /**
     * get field @EJB
     *
     * @return notasSolicitudVtMglFacadeLocal @EJB

     */
    public CmtNotasSolicitudVtMglFacadeLocal getNotasSolicitudVtMglFacadeLocal() {
        return this.notasSolicitudVtMglFacadeLocal;
    }

    /**
     * set field @EJB
     *
     * @param notasSolicitudVtMglFacadeLocal @EJB

     */
    public void setNotasSolicitudVtMglFacadeLocal(CmtNotasSolicitudVtMglFacadeLocal notasSolicitudVtMglFacadeLocal) {
        this.notasSolicitudVtMglFacadeLocal = notasSolicitudVtMglFacadeLocal;
    }

    /**
     * get field
     *
     * @return cmtTipoSolicitudMgl
     */
    public CmtTipoSolicitudMgl getCmtTipoSolicitudMgl() {
        return this.cmtTipoSolicitudMgl;
    }

    /**
     * set field
     *
     * @param cmtTipoSolicitudMgl
     */
    public void setCmtTipoSolicitudMgl(CmtTipoSolicitudMgl cmtTipoSolicitudMgl) {
        this.cmtTipoSolicitudMgl = cmtTipoSolicitudMgl;
    }
}
