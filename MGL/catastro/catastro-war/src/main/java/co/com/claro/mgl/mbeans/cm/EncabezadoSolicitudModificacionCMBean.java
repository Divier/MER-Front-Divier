/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.HhppVirtualMglFacadeLocal;
import co.com.claro.mgl.facade.cm.*;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.cm.*;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



@ManagedBean(name = "encabezadoSolicitudModificacionCMBean")
@SessionScoped
public final class EncabezadoSolicitudModificacionCMBean implements Serializable {

    @EJB
    private CmtCuentaMatrizMglFacadeLocal cuentaMatrizMglFacadeLocal;
    @EJB
    private CmtSolicitudCmMglFacadeLocal solicitudFacadeLocal;
    @EJB
    private CmtTipoSolicitudMglFacadeLocal tipoSolicitudFacadeLocal;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private CmtNotasSolicitudMglFacadeLocal notasSolicitudMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    @EJB
    private CmtNotasSolicitudDetalleMglFacadeLocal notasSolicitudDetalleMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtHorarioRestriccionMglFacadeLocal horarioRestriccionMglFacadeLocal;
    @EJB
    private CmtTipoOtMglFacadeLocal tipoOtMglFacadeLocal;
    @EJB
    private CmtOrdenTrabajoMglFacadeLocal ordenTrabajoMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private CmtTipoSolicitudMglFacadeLocal tipoSolicitudMglFacadeLocal;
    @EJB
    private HhppVirtualMglFacadeLocal hhppVirtualMglFacadeLocal;

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(EncabezadoSolicitudModificacionCMBean.class);
    private final String FORMULARIO_NOTA = "SOLICITUDNOTASCM";
    private final String FORMULARIO_COMENTARIO = "SOLICITUDNOTASCMCOMENTARIO";
    private final String TAB_TIPO_MOD_CM_DATOS = "TABSTIPOSMODCMDATOSCM";
    private final String TAB_TIPO_MOD_CM_DIRECCION = "TABSTIPOSMODCMDIRECCION";
    private final String TAB_TIPO_MOD_CM_SUBEDIFICIOS = "TABSTIPOSMODCMSUBEDIFICIOS";
    private final String TAB_TIPO_MOD_CM_COBERTURA = "TABSTIPOSMODCMCOBERTURA";
    private final String FORMULARIO_GESTION_SOLICITUD = "INFOGESTIONCREACM";
    private String usuarioVT = null;
    private int perfilVT = 0;
    private String tmpInicio;
    private String deltaTiempo;
    private String tmpFin;
    private String timeSol;
    private boolean solicitudModificacionCM;
    private boolean solicitudCreacionModificacionHHPP;
    private boolean activoCheckModDatosCM;
    private boolean activoCheckModDireccion;
    private boolean activoCheckModSubedificios;
    private boolean activoCheckModCobertura;
    private boolean activoCheckCreacionHHPP;
    private boolean activoCheckModificacionHHPP;
    private boolean activoCheckTrasladoHhppBloqueado;//Brief 57762 creación HHPP Virtual
    private boolean modoGestion;
    private boolean visibleDatosSolicitud;
    private CmtCuentaMatrizMgl cuentaMatriz;
    private CmtSolicitudCmMgl solicitudModCM;
    private CmtNotasSolicitudMgl cmtNotasSolicitudMgl;
    private List<CmtTipoSolicitudMgl> tipoSolicitudList;
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    private String selectedTab = "SOLICITUD";

    private CmtBasicaMgl tipoAcondicionamiento;
    private List<CmtBasicaMgl> tipoAcondicionamientoList;
    private CmtBasicaMgl tipoSegmento;
    private List<CmtBasicaMgl> tipoSegmentoList;
    private CmtBasicaMgl tipoNotaSelected;
    private List<CmtBasicaMgl> tipoNotaList;
    private String notaComentarioStr;
    private boolean showNotas;
    private List<CmtBasicaMgl> resultadoGestList;
    private List<CmtBasicaMgl> resultadoGestDatosCmList;
    private List<CmtBasicaMgl> resultadoGestDireccionList;
    private List<CmtBasicaMgl> resultadoGestSubEdiList;
    private List<CmtBasicaMgl> resultadoGestCoberturaList;
    private List<CmtBasicaMgl> origenSolicitudList;
    private boolean showHorario;
    private String selectedTrack = "TRACKSOLICITUD";
    private Date dateInicioCreacion;
    private List<CmtHorarioRestriccionMgl> horarioSolicitudList;
    private BigDecimal accionSelect;
    private BigDecimal accionModDatosSelect;
    private BigDecimal accionModDireccionSelect;
    private BigDecimal accionModSubEdiSelect;
    private BigDecimal accionModCoberturaSelect;
    private BigDecimal respuestaSelect;
    private BigDecimal respuestaModDatosSelect;
    private BigDecimal respuestaModSubEdiSelect;
    private BigDecimal respuestaModDireccionSelect;
    private BigDecimal respuestaModCoberturaSelect;
    private List<CmtTipoBasicaMgl> accionList;
    private List<CmtTipoBasicaMgl> accionGestModDatosCmList;
    private List<CmtTipoBasicaMgl> accionGestModDireccionList;
    private List<CmtTipoBasicaMgl> accionGestModSubEdiList;
    private List<CmtTipoBasicaMgl> accionGestModCoberturaList;
    private List<CmtTipoBasicaMgl> accionGestCrearVisitaList;
    private CmtSubEdificioMgl selectedCmtSubEdificioMgl;
    private List<CmtSubEdificioMgl> listCmtSubEdificioMgl;
    private boolean disableCantidadTorres;

    private HhppMgl selectHhppMgl;
    private boolean visibleDatosDatosDireccion = false;
    private boolean gestionado = false;
    private List<CmtBasicaMgl> listaTecnologiasBasica;
    private CmtBasicaMgl tecnologiaSelect;
    private SecurityLogin securityLogin;
    private UsuariosServicesDTO usuarioSolicitud;
    private CmtTipoSolicitudMgl cmtTipoSolicitudMgl;
    private String cantidadSubedificios = "";
    private final String VALIDARCREARSOLVTCM = "VALIDARCREARSOLVTCM";
    private final String VALIDARGESTSOLVTCM = "VALIDARGESTSOLVTCM";
    


    public EncabezadoSolicitudModificacionCMBean() {
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
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {
            usuarioSolicitud = new UsuariosServicesDTO();
            activoCheckModDatosCM = false;
            activoCheckModDireccion = false;
            activoCheckModSubedificios = false;
            activoCheckModCobertura = false;
            activoCheckCreacionHHPP = true;
            activoCheckModificacionHHPP = false;
            activoCheckTrasladoHhppBloqueado = false;

            visibleDatosSolicitud = true;
            disableCantidadTorres = true;
            selectedCmtSubEdificioMgl = null;
            visibleDatosDatosDireccion = true;

            solicitudFacadeLocal.setUser(usuarioVT, perfilVT);
            notasSolicitudMglFacadeLocal.setUser(usuarioVT, perfilVT);
            notasSolicitudDetalleMglFacadeLocal.setUser(usuarioVT, perfilVT);
            horarioRestriccionMglFacadeLocal.setUser(usuarioVT, perfilVT);
            tipoSolicitudList = tipoSolicitudFacadeLocal.findAll();
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
            usuarioSolicitudCreador = usuarioLogin;

            CmtTipoBasicaMgl tipoBasicaAcondicimiento;
            tipoBasicaAcondicimiento=cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_ACONDICIONAMIENTO);
            tipoAcondicionamientoList = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaAcondicimiento);

            CmtTipoBasicaMgl tipoBasicaOrigenSolicitud;
            tipoBasicaOrigenSolicitud=cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_ORIGEN_SOLICITUD);
            origenSolicitudList = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaOrigenSolicitud);

            CmtTipoBasicaMgl tipoBasicaSegmento;            
            tipoBasicaSegmento=cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_DE_SEGMENTO);
            tipoSegmentoList = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaSegmento);

            CmtTipoBasicaMgl tipoBasicaNotaOt;
            tipoBasicaNotaOt=cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_DE_NOTAS);
            
            tipoNotaList = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaNotaOt);

            CmtTipoBasicaMgl tipoBasicaTecnologia;
            tipoBasicaTecnologia=cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
            listaTecnologiasBasica = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaTecnologia);
            

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
    }

    public void iniciarTiempoCreaSolicitud() {
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

    public void obtenerAccionesList()  {
        try {
            CmtTipoBasicaMgl accion;
            accionList = new ArrayList<CmtTipoBasicaMgl>();
            accionGestModDatosCmList = new ArrayList<CmtTipoBasicaMgl>();
            accionGestModDireccionList = new ArrayList<CmtTipoBasicaMgl>();
            accionGestModSubEdiList = new ArrayList<CmtTipoBasicaMgl>();
            accionGestModCoberturaList = new ArrayList<CmtTipoBasicaMgl>();

            accionModCoberturaSelect = null;
            accionModDatosSelect = null;
            accionModDireccionSelect = null;
            accionModSubEdiSelect = null;

            respuestaModCoberturaSelect = null;
            respuestaModDatosSelect = null;
            respuestaModDireccionSelect = null;
            respuestaModSubEdiSelect = null;

            if (modoGestion) {
                if (cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_MODIFICACION_CM).compareTo(
                        solicitudModCM.getTipoSolicitudObj().getTipoSolicitudId()) == 0) {

                    accion = obtenerAccionesGestion(cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                            Constant.TIPO_BASICA_ACCION_MODIFICAR_CM).getTipoBasicaId());
                    accionGestModDatosCmList.add(accion);
                    accion = obtenerAccionesGestion(cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                            Constant.TIPO_BASICA_ACCION_RECHAZAR_MODIFICAR_CM).getTipoBasicaId());
                    accionGestModDatosCmList.add(accion);

                    accion = obtenerAccionesGestion(cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                            Constant.TIPO_BASICA_ACCION_MODIFICAR_DIRECCION).getTipoBasicaId());
                    accionGestModDireccionList.add(accion);
                    accion = obtenerAccionesGestion(cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                            Constant.TIPO_BASICA_ACCION_RECHAZAR_MODIFICAR_DIRECCION).getTipoBasicaId());
                    accionGestModDireccionList.add(accion);

                    accion = obtenerAccionesGestion(cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                            Constant.TIPO_BASICA_ACCION_MODIFICAR_SUBEDIFICIOS)
                            .getTipoBasicaId());
                    accionGestModSubEdiList.add(accion);
                    accion = obtenerAccionesGestion(cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                            Constant.TIPO_BASICA_ACCION_RECHAZAR_MODIFICAR_SUBEDIFICIOS)
                            .getTipoBasicaId());
                    accionGestModSubEdiList.add(accion);

                    accion = obtenerAccionesGestion(cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                            Constant.TIPO_BASICA_ACCION_MODIFICAR_COBERTURA).getTipoBasicaId());
                    accionGestModCoberturaList.add(accion);
                    accion = obtenerAccionesGestion(cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                            Constant.TIPO_BASICA_ACCION_RECHAZAR_MODIFICAR_COBERTURA).getTipoBasicaId());
                    accionGestModCoberturaList.add(accion);

                }
                if (cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_VISITA_TECNICA).compareTo(
                        solicitudModCM.getTipoSolicitudObj().getTipoSolicitudId()) == 0) {
                    accionList.add(obtenerAccionesGestion(cmtTipoBasicaMglFacadeLocal.
                            findByCodigoInternoApp(
                                    Constant.TIPO_BASICA_ACCION_FINALIZAR_SOLICITUD_VISITA_TECNICA)
                            .getTipoBasicaId()));
                    accionList.add(obtenerAccionesGestion(cmtTipoBasicaMglFacadeLocal.
                            findByCodigoInternoApp(
                                    Constant.TIPO_BASICA_ACCION_RECHAZAR_VISITA_TECNICA).getTipoBasicaId()));
                    accionList.add(obtenerAccionesGestion(cmtTipoBasicaMglFacadeLocal.
                            findByCodigoInternoApp(
                                    Constant.TIPO_BASICA_ACCION_POSPONER_VISITA_TECNICA).getTipoBasicaId()));
                }
                if (cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_CREACION_CM).compareTo(solicitudModCM.getTipoSolicitudObj()
                        .getTipoSolicitudId()) == 0) {
                    accionList.add(obtenerAccionesGestion(cmtTipoBasicaMglFacadeLocal.
                            findByCodigoInternoApp(
                                    Constant.TIPO_BASICA_ACCION_CREAR_CUENTA_MATRIZ).getTipoBasicaId()));
                    accionList.add(obtenerAccionesGestion(cmtTipoBasicaMglFacadeLocal.
                            findByCodigoInternoApp(
                                    Constant.TIPO_BASICA_ACCION_RECHAZAR_CUENTA_MATRIZ).getTipoBasicaId()));
                    accionList.add(obtenerAccionesGestion(cmtTipoBasicaMglFacadeLocal.
                            findByCodigoInternoApp(
                                    Constant.TIPO_BASICA_ACCION_ESCALAR_CUENTA_MATRIZ).getTipoBasicaId()));
                }
                if (cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_CREACION_HHPP).compareTo(
                        solicitudModCM.getTipoSolicitudObj().getTipoSolicitudId()) == 0
                        || cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_MODIFICACION_HHPP).compareTo(
                                solicitudModCM.getTipoSolicitudObj().getTipoSolicitudId()) == 0) {
                    accionList.add(obtenerAccionesGestion(cmtTipoBasicaMglFacadeLocal.
                            findByCodigoInternoApp(
                                    Constant.TIPO_BASICA_ACCION_CREAR_HHPP).getTipoBasicaId()));
                    accionList.add(obtenerAccionesGestion(cmtTipoBasicaMglFacadeLocal.
                            findByCodigoInternoApp(
                                    Constant.TIPO_BASICA_ACCION_RECHAZAR_HHPP).getTipoBasicaId()));
                }
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }

    }

    public CmtTipoBasicaMgl obtenerAccionesGestion(BigDecimal idAccion) {
        CmtTipoBasicaMgl cmtTipoBasicaMgl = new CmtTipoBasicaMgl();
        try {
            cmtTipoBasicaMgl.setTipoBasicaId(idAccion);
            cmtTipoBasicaMgl = cmtTipoBasicaMglFacadeLocal.findById(cmtTipoBasicaMgl);
        } catch (ApplicationException e) {
            String msnErr = "Ha ocurrido un error buscando las acciones de Gesti\u00f3n.";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
        return cmtTipoBasicaMgl;
    }

    public String obtenerResultadoAccion(BigDecimal idAccion) {
        try {
            CmtTipoBasicaMgl cmtTipoBasicaMgl = new CmtTipoBasicaMgl();
            cmtTipoBasicaMgl.setTipoBasicaId(idAccion);
            resultadoGestList = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl);
            if (cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_ACCION_MODIFICAR_CM).getTipoBasicaId().
                    equals(idAccion) || 
                    cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_ACCION_RECHAZAR_MODIFICAR_CM).
                    getTipoBasicaId().equals(idAccion)) {
                resultadoGestDatosCmList = resultadoGestList;
            }
            if (cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_ACCION_MODIFICAR_DIRECCION)
                    .getTipoBasicaId().equals(idAccion) || 
                    cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_ACCION_RECHAZAR_MODIFICAR_DIRECCION)
                    .getTipoBasicaId().equals(idAccion)) {
                resultadoGestDireccionList = resultadoGestList;
            }
            if (cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_ACCION_MODIFICAR_SUBEDIFICIOS)
                    .getTipoBasicaId().equals(idAccion) || 
                    cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_ACCION_RECHAZAR_MODIFICAR_SUBEDIFICIOS)
                        .getTipoBasicaId().equals(idAccion)) {
                resultadoGestSubEdiList = resultadoGestList;
            }
            if (cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_ACCION_MODIFICAR_COBERTURA).getTipoBasicaId().
                    equals(idAccion) || 
                    cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_ACCION_RECHAZAR_MODIFICAR_COBERTURA)
                    .getTipoBasicaId().equals(idAccion)) {
                resultadoGestCoberturaList = resultadoGestList;
            }
        } catch (ApplicationException e) {
            String msnErr = "Ha ocurrido un error buscando las respuestas de Gesti\u00f3n.";
            FacesUtil.mostrarMensajeError(msnErr+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Funcion utilizada para poder navegar sobre las diferentes opciones de
     * tipos de modificacion de cuenta matriz.
     *
     * @param sSeleccionado
     *
     * @return retorna el nombre de la pagina con el tipo de modificaci�n a la
     * que tiene que ir
     */
    public String cambiarTab(String sSeleccionado) {
        String formTabSeleccionado = "";
        ConstantsCM.TABS_MOD_CM Seleccionado = ConstantsCM.TABS_MOD_CM.valueOf(sSeleccionado);
        SolicitudModificacionCMBean solicitudModificacionCMBean = JSFUtil.getSessionBean(SolicitudModificacionCMBean.class);
        boolean permitir;
        switch (Seleccionado) {
            case DATOSCM:
                permitir = solicitudModificacionCMBean.verificarPreguardado();
                if (permitir) {
                    formTabSeleccionado = "/view/MGL/CM/solicitudes/modificacionCM/principalSolicitudModificarCM";
                    selectedTab = "DATOSCM";
                }
                break;
            case DIRECCION:
                permitir = solicitudModificacionCMBean.verificarPreguardado();
                if (permitir) {
                    formTabSeleccionado = "/view/MGL/CM/solicitudes/modificacionCM/solicitudModificarDireccionCM";
                    selectedTab = "DIRECCION";
                    solicitudModificacionCMBean.verificarCambioDireccion();
                }
                break;
            case SUBEDIFICIOS:
                permitir = solicitudModificacionCMBean.verificarPreguardado();
                if (permitir) {
                    formTabSeleccionado = "/view/MGL/CM/solicitudes/modificacionCM/solicitudModificarSubedificiosCM";
                    selectedTab = "SUBEDIFICIOS";
                }
                break;
            case COBERTURA:
                permitir = solicitudModificacionCMBean.verificarPreguardado();
                if (permitir) {
                    formTabSeleccionado = "/view/MGL/CM/solicitudes/modificacionCM/solicitudModificarCoberturaCM";
                    selectedTab = "COBERTURA";
                }
                break;
            case TRACK:
                formTabSeleccionado = "/view/MGL/CM/solicitudes/general/solicitudTrack";
                selectedTab = "TRACK";
                showNotas = true;
                
                try {
                    if (usuarioVT != null && !usuarioVT.isEmpty()) {
                         usuarioSolicitud = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
                    }
                }
                catch (ApplicationException e) {
                    LOGGER.error("No fue posible buscar el usuario del track: "+e.getMessage());
                }
                
                break;

            case NOTAS:
                formTabSeleccionado = "/view/MGL/CM/solicitudes/general/solicitudNotasCM";
                selectedTab = "NOTAS";
                showNotas = true;
                limpiarCamposNota();
                break;
            case HORARIO:
                formTabSeleccionado = "/view/MGL/CM/solicitudes/general/solicitudHorario";
                selectedTab = "HORARIO";
                showNotas = true;
                break;
            case SOLICITUD:
                formTabSeleccionado = getUrlSolicitud();
                if (solicitudModCM.getTipoSolicitudObj().getTipoSolicitudId().compareTo(
                    cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_MODIFICACION_CM)) == 0){
                    LOGGER.info("Deja el tab de modificacion");
                }else{
                    selectedTab = "SOLICITUD";
                }
                showNotas = false;
                break;
            default:
                formTabSeleccionado = "/view/MGL/CM/solicitudes/modificacionCm/principalSolicitudModificarCM";
                selectedTab = "DATOSCM";
                break;
        }
        return formTabSeleccionado;
    }

    /**
     * Apoya el proceso de cambio de vista seleccionada desde las tabs
     *
     * @param sSeleccionado {@link String}
     * @return {@link String} formTabSeleccionado Retorna la ruta de la vista a la que se va a redireccionar.
     */
    public String cambiarTabHhpp(String sSeleccionado) {

        String formTabSeleccionado;
        ConstantsCM.TABS_CM_HHPP seleccionado = ConstantsCM.TABS_CM_HHPP.valueOf(sSeleccionado);

        switch (seleccionado) {
            case CREACION_HHPP:
                solicitudModCM.setListCmtSolicitudHhppMgl(new ArrayList<>());
                activoCheckCreacionHHPP = true;
                activoCheckModificacionHHPP = false;
                activoCheckTrasladoHhppBloqueado = false;
                formTabSeleccionado = "/view/MGL/CM/solicitudes/HHPP/crearSolicitudhhpp";
                selectedTab = "CREACION_HHPP";
                break;
            case MODIFICACION_HHPP:
                solicitudModCM.setListCmtSolicitudHhppMgl(new ArrayList<>());
                activoCheckCreacionHHPP = false;
                activoCheckModificacionHHPP = true;
                activoCheckTrasladoHhppBloqueado = false;
                formTabSeleccionado = "/view/MGL/CM/solicitudes/HHPP/modificaSolicitudhhpp";
                selectedTab = "MODIFICACION_HHPP";
                break;
            case ELIMINACION_HHPP:
                formTabSeleccionado = "/view/MGL/CM/solicitudes/HHPP/eliminaSolicitudhhpp";
                selectedTab = "ELIMINACION_HHPP";
                break;
            case GESTION_HHPP:
                formTabSeleccionado = "/view/MGL/CM/solicitudes/HHPP/gestionarHhpp";
                selectedTab = "GESTION_HHPP";
                break;
                 /* Brief 57762 Creación HHPP Virtual*/
            case TRASLADO_HHPP_BLOQUEADO:
                solicitudModCM.setListCmtSolicitudHhppMgl(new ArrayList<>());
                activoCheckTrasladoHhppBloqueado = true;
                activoCheckCreacionHHPP = false;
                activoCheckModificacionHHPP = false;
                formTabSeleccionado = "/view/MGL/CM/solicitudes/HHPP/trasladarHhppBloqueado";
                selectedTab = "TRASLADO_HHPP_BLOQUEADO";
                break;
                /* Cierre Brief 57762 */
            default:
                formTabSeleccionado = "/view/MGL/CM/solicitudes/HHPP/crearSolicitudhhpp";
                selectedTab = "CREACION_HHPP";
                break;
        }
        return formTabSeleccionado;
    }

    public String cambiaEdificio() {
        String formTabSeleccionado;
        ConstantsCM.TABS_CM_HHPP Seleccionado = ConstantsCM.TABS_CM_HHPP.valueOf(selectedTab);
        switch (Seleccionado) {
            case CREACION_HHPP:
                formTabSeleccionado = "/view/MGL/CM/solicitudes/HHPP/crearSolicitudhhpp";
                selectedTab = "CREACION_HHPP";
                break;
            case MODIFICACION_HHPP:
                formTabSeleccionado = "/view/MGL/CM/solicitudes/HHPP/modificaSolicitudhhpp";
                selectedTab = "MODIFICACION_HHPP";
                break;
            case ELIMINACION_HHPP:
                formTabSeleccionado = "/view/MGL/CM/solicitudes/HHPP/eliminaSolicitudhhpp";
                selectedTab = "ELIMINACION_HHPP";
                break;
                /* Brief 57762 */
            case TRASLADO_HHPP_BLOQUEADO:
                formTabSeleccionado = "/view/MGL/CM/solicitudes/HHPP/trasladarHhppBloqueado";
                selectedTab = "TRASLADO_HHPP_BLOQUEADO";
                break;
                /* Cierre Brief 57762*/
            default:
                formTabSeleccionado = "/view/MGL/CM/solicitudes/HHPP/crearSolicitudhhpp";
                selectedTab = "CREACION_HHPP";
                break;
        }
        return formTabSeleccionado;
    }

    public String cambiarTrack(String trackSelected) {
        selectedTrack = trackSelected;
        return null;
    }

    private String getUrlSolicitud() {
        String urlReturn = "";
        if (solicitudModCM.getSolicitudCmId() != null && solicitudModCM.getTipoSolicitudObj() != null) {
            if (solicitudModCM.getTipoSolicitudObj().getTipoSolicitudId().compareTo(
                    cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_VISITA_TECNICA)) == 0) {
                urlReturn = "/view/MGL/CM/solicitudes/visitaTecnica/crearSolicitudVt";
            } else if (solicitudModCM.getTipoSolicitudObj().getTipoSolicitudId().compareTo(
                    cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_MODIFICACION_CM)) == 0) {
                if (!modoGestion) {
                    urlReturn = "/view/MGL/CM/solicitudes/modificacionCM/principalSolicitudModificarCM";
                } else {
                    urlReturn = ingresarGestionModificacionCm(solicitudModCM);
                }
            } else if (solicitudModCM.getTipoSolicitudObj().getTipoSolicitudId().compareTo(
                    cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_CREACION_CM)) == 0) {
                if (!modoGestion) {
                    urlReturn = "/view/MGL/CM/solicitudes/CreacionCm/infoCreaCM";
                } else {
                    urlReturn = "/view/MGL/CM/solicitudes/CreacionCm/infoGestionCreaCM";
                }
            } else if (solicitudModCM.getTipoSolicitudObj().getTipoSolicitudId().compareTo(
                    cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_MODIFICACION_HHPP)) == 0) {
                if (!modoGestion) {
                    urlReturn = "/view/MGL/CM/solicitudes/HHPP/crearSolicitudhhpp";
                } else {
                    urlReturn = "/view/MGL/CM/solicitudes/HHPP/gestionarHhpp";
                }
            } else if (solicitudModCM.getTipoSolicitudObj().getTipoSolicitudId().compareTo(
                    cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_ELIMINACION_CM)) == 0) {
                if (!modoGestion) {
                    urlReturn = "/view/MGL/CM/eliminarCm";
                } else {
                    urlReturn = "/view/MGL/CM/gestionarEliminacionCM";
                }
            }
        } else {
            FacesUtil.mostrarMensajeError("Todavia no se ha creado la solicitud", null);
        }
        return urlReturn;
    }

    public void limpiarDataGestion(BigDecimal tipoSolicitudId) {
        try {
            solicitudModCM = solicitudFacadeLocal.bloquearDesbloquearSolicitud(solicitudModCM, true);
            tipoAcondicionamiento = solicitudModCM.getTipoAcondicionamiento();
            tipoSegmento = solicitudModCM.getTipoSegmento();
            tecnologiaSelect = solicitudModCM.getBasicaIdTecnologia();

            iniciarTiempoCreaSolicitud();
            Date hoy = new Date();
            if (solicitudModCM.getUsuarioGestionId() == null) {
                if (usuarioLogin.getCedula() != null) {
                    solicitudModCM.setUsuarioGestionId(new BigDecimal(usuarioLogin.getCedula()));
                }
            }

            if (tipoSolicitudId.compareTo(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_MODIFICACION_CM)) == 0) {
                if (solicitudModCM.getModCobertura() == 1 && solicitudModCM.getUsuarioGestionCoberturaId() == null) {
                    if (usuarioLogin.getCedula() != null) {
                        solicitudModCM.setUsuarioGestionCoberturaId(new BigDecimal(usuarioLogin.getCedula()));
                    }
                }
                if (solicitudModCM.getModCobertura() == 1 && solicitudModCM.getFechaInicioGestionCobertura() == null) {
                    solicitudModCM.setFechaInicioGestionCobertura(hoy);
                } else {
                    if (solicitudModCM.getFechaInicioGestion() == null) {
                        solicitudModCM.setFechaInicioGestion(hoy);
                    }
                }
            } else if (tipoSolicitudId.compareTo(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_VISITA_TECNICA)) == 0) {
                if (solicitudModCM.getFechaInicioGestion() == null) {
                    solicitudModCM.setFechaInicioGestion(hoy);
                }
            } else if (tipoSolicitudId.compareTo(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_MODIFICACION_HHPP)) == 0) {
                if (solicitudModCM.getFechaInicioGestionHhpp() == null) {
                    solicitudModCM.setFechaInicioGestionHhpp(hoy);
                }
            } else if (tipoSolicitudId.compareTo(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_CREACION_HHPP)) == 0) {
                if (solicitudModCM.getFechaInicioGestionHhpp() == null) {
                    solicitudModCM.setFechaInicioGestionHhpp(hoy);
                }
            } else if (tipoSolicitudId.compareTo(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_ELIMINACION_CM)) == 0) {
                if (solicitudModCM.getFechaInicioGestion() == null) {
                    solicitudModCM.setFechaInicioGestion(hoy);
                }
            } else if (tipoSolicitudId.compareTo(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_CREACION_CM)) == 0) {
                if (solicitudModCM.getFechaInicioGestion() == null) {
                    solicitudModCM.setFechaInicioGestion(hoy);
                }
            }
            obtenerAccionesList();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
    }

    public void limpiarDataSolicitud(BigDecimal tipoSolicitudId) {
        try {
            iniciarTiempoCreaSolicitud();
            Date hoy = new Date();
            showNotas = false;
            showHorario = false;
            modoGestion = false;
            selectedTrack = "TRACKSOLICITUD";
            visibleDatosSolicitud = true;
            if (tipoSolicitudId.equals(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_MODIFICACION_CM))) {
                selectedTab = "DATOSCM";
            } else {
                selectedTab = "SOLICITUD";
            }

            disableCantidadTorres = true;
            horarioSolicitudList = new ArrayList<CmtHorarioRestriccionMgl>();
            solicitudModCM = new CmtSolicitudCmMgl();
            CmtBasicaMgl basicaIdTecnologia = new CmtBasicaMgl(); 
            solicitudModCM.setBasicaIdTecnologia(basicaIdTecnologia);
            solicitudModCM.setFechaInicioCreacion(dateInicioCreacion);
            tipoSolicitudList = tipoSolicitudFacadeLocal.findAll();
            
            CmtTipoSolicitudMgl tipoSolicitudMgl = tipoSolicitudMglFacadeLocal.find(tipoSolicitudId);

            if (tipoSolicitudMgl != null) {
                solicitudModCM.setTipoSolicitudObj(tipoSolicitudMgl);
            }
           
            solicitudModCM.setUsuarioCreacion(usuarioVT);
            if (usuarioLogin != null && usuarioLogin.getCedula() != null) {
                solicitudModCM.setUsuarioSolicitudId(new BigDecimal(usuarioLogin.getCedula()));
            }
            solicitudModCM.setUnidad("1");

            CuentaMatrizBean cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            CmtCuentaMatrizMgl cm = cuentaMatrizBean.getCuentaMatriz();
            
            if (tipoSolicitudMgl != null) {
                if (tipoSolicitudMgl.getTipoSolicitudId().compareTo(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_CREACION_CM)) != 0) {
                    cuentaMatriz = cuentaMatrizMglFacadeLocal.findById(cm);
                    solicitudModCM.setCuentaMatrizObj(cuentaMatriz);
                    solicitudModCM.setComunidad(cuentaMatriz.getComunidad());
                    solicitudModCM.setDivision(cuentaMatriz.getDivision());
                    solicitudModCM.setCiudadGpo(cuentaMatriz.getMunicipio());
                    solicitudModCM.setDepartamentoGpo(cuentaMatriz.getDepartamento());
                    solicitudModCM.setCentroPobladoGpo(cuentaMatriz.getCentroPoblado());
                }
            }
           
            if (tipoSolicitudMgl != null) {
                if (tipoSolicitudMgl.getTipoSolicitudId().compareTo(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_ELIMINACION_CM)) == 0) {
                    cuentaMatriz = cuentaMatrizMglFacadeLocal.findById(cm);
                    solicitudModCM.setCuentaMatrizObj(cuentaMatriz);
                    solicitudModCM.setComunidad(cuentaMatriz.getComunidad());
                    solicitudModCM.setDivision(cuentaMatriz.getDivision());
                    solicitudModCM.setCiudadGpo(cuentaMatriz.getMunicipio());
                    solicitudModCM.setDepartamentoGpo(cuentaMatriz.getDepartamento());
                    solicitudModCM.setCentroPobladoGpo(cuentaMatriz.getCentroPoblado());
                }
            }
            
            solicitudModCM.setOrigenSolicitud(new CmtBasicaMgl());
            solicitudModCM.getOrigenSolicitud().setBasicaId(BigDecimal.ZERO);

            CmtBasicaMgl estadoSolicitud = new CmtBasicaMgl();
            estadoSolicitud.setBasicaId(cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE).getBasicaId());
            solicitudModCM.setEstadoSolicitudObj(estadoSolicitud);
            solicitudModCM.setModCobertura(new Short("0"));
            solicitudModCM.setModDatosCm(new Short("0"));
            solicitudModCM.setModDireccion(new Short("0"));
            solicitudModCM.setModSubedificios(new Short("0"));
            solicitudModCM.setFechaInicioCreacion(hoy);

            tipoAcondicionamiento = new CmtBasicaMgl();
            tipoSegmento = new CmtBasicaMgl();
            tecnologiaSelect = new CmtBasicaMgl();

        } catch (ApplicationException | NumberFormatException e) {

            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
    }

    public String limpiarDataNota(BigDecimal tipoNotaId)  {
        try {
            cmtNotasSolicitudMgl = new CmtNotasSolicitudMgl();
            CmtBasicaMgl tipoNota = basicaMglFacadeLocal.findById(tipoNotaId);
            cmtNotasSolicitudMgl.setTipoNotaObj(tipoNota);
            cmtNotasSolicitudMgl.setTelefonoUsuario(usuarioLogin.getTelefono());
            return "/view/MGL/CM/solicitudes/general/solicitudNotasCM";
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String regresar(CmtSolicitudCmMgl solicitud) {
        String returnUrl = "/view/MGL/CM/cuentas-matrices";
        try {
            if (solicitud != null && solicitud.getSolicitudCmId() != null) {
                solicitudFacadeLocal.bloquearDesbloquearSolicitud(solicitud, false);
            }
        } catch (ApplicationException e) {
            String msn = "Ocurrio un error desbloqueando la solicitud " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
        if (modoGestion) {
            returnUrl = "/view/MGL/CM/solicitudes/general/solicitudesGestionar";
        }
        return returnUrl;
    }

    /**
     * Inicia form de solicitudes de HHPP
     *
     * @author Antonio Gil
     * @return jsf
     */
    public String ingresarSolicitudHhpp() {
        try {
            solicitudCreacionModificacionHHPP = true;
            activoCheckCreacionHHPP = true;
            activoCheckModificacionHHPP = false;
            activoCheckTrasladoHhppBloqueado = false;// Brief 57762
            obtenerAccionesList();
            CuentaMatrizBean cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            CmtCuentaMatrizMgl cm = cuentaMatrizBean.getCuentaMatriz();
            usuarioSolicitud = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
            usuarioSolicitudCreador = usuarioSolicitud;
            if (cm == null || cm.getCuentaMatrizId() == null) {
                String msn = "Debe estar sobre el detalle de una cuenta matriz para realizar la creacion de una Solicitud";
                LOGGER.error(msn);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }

            limpiarDataSolicitud(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_MODIFICACION_HHPP));
            limpiarDataNota(cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_NOTA_HHHPP).getBasicaId());
            solicitudModificacionCM = false;
            selectedCmtSubEdificioMgl = null;
            selectedCmtSubEdificioMgl = getSelectedCmtSubEdificioMgl();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
        //Predeterminado
        selectedTab = "CREACION_HHPP";
        return "/view/MGL/CM/solicitudes/HHPP/crearSolicitudhhpp";
    }

    public String ingresarSolicitudVt() {
        try {
            CuentaMatrizBean cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            CmtCuentaMatrizMgl cm = cuentaMatrizBean.getCuentaMatriz();
            //Validamos que se haya seleccionado una Cuenta Matriz
            if (cm == null || cm.getCuentaMatrizId() == null) {
                String msn = "Debe estar sobre el detalle de una cuenta matriz para realizar la creacion de una Solicitud";
                LOGGER.error(msn);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            } else {//Validamos el estado de la Cuenta Matriz para la creacion de la Solicitud
                //buscamos el tipo de OT Visita Tecnica
                CmtTipoOtMgl tipoOtMgl = tipoOtMglFacadeLocal.findById(Constant.TIPO_OT_VISITA_TECNICA);
                //Validamos el estado de la CM
                if (!ordenTrabajoMglFacadeLocal.validaEstadoCm(tipoOtMgl, cm)) {
                    String msn = "Estado de la CM NO Valido para la creacion de la Solicitud";
                    LOGGER.error(msn);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, msn, ""));
                    return null;
                }
            }

            limpiarDataSolicitud(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_VISITA_TECNICA));
            limpiarDataNota(cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_NOTA_VISITA_TECNICA).getBasicaId());
            solicitudModificacionCM = false;
            showHorario = true;
            usuarioSolicitud = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
            usuarioSolicitudCreador = usuarioSolicitud;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
        return "/view/MGL/CM/solicitudes/visitaTecnica/crearSolicitudVt";
    }

    public String ingresarSolicitudModificacionCm() {
        try {
            cambiarTab("DATOSCM");
            solicitudModificacionCM = true;
            modoGestion = false;
            CuentaMatrizBean cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            CmtCuentaMatrizMgl cm = cuentaMatrizBean.getCuentaMatriz();
            if (cm == null || cm.getCuentaMatrizId() == null) {
                String msn = "Debe estar sobre el detalle de una cuenta matriz para realizar la creacion de una Solicitud";
                LOGGER.error(msn);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
            cuentaMatriz = cuentaMatrizMglFacadeLocal.findById(cm);
            limpiarDataSolicitud(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_MODIFICACION_CM));
            limpiarDataNota(cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_NOTA_MODIFICACION_CUENTA_MATRIZ).getBasicaId());
            SolicitudModificacionCMBean solModCm = JSFUtil.getSessionBean(SolicitudModificacionCMBean.class);
            
            if (usuarioLogin != null && usuarioLogin.getCedula() != null) {
                usuarioSolicitud = usuariosFacade.consultaInfoUserPorCedula(usuarioLogin.getCedula().toString());
            }
            usuarioSolicitudCreador = usuarioSolicitud;
            
            return solModCm.ingresarModificacionCM(ConstantsCM.MODO_INGRESO_SOLICITUD);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
        return "/view/MGL/CM/solicitudes/modificacionCM/principalSolicitudModificarCM";
    }

    public String ingresarGestionSolicitud(CmtSolicitudCmMgl solicitudCmMgl) {
        try {
            gestionado = false;
            solicitudModCM = solicitudFacadeLocal.findById(solicitudCmMgl.getSolicitudCmId());
            LOGGER.info("metodo ingresarGestionSolicitud-gestionado: " + gestionado);
            
            if (solicitudModCM.getUsuarioSolicitudId() != null) {
                usuarioSolicitud = usuariosFacade.consultaInfoUserPorCedula(solicitudModCM.getUsuarioSolicitudId());
            }    
             usuarioSolicitudCreador = usuarioLogin;
           
                
            if (solicitudModCM == null || solicitudCmMgl.getTipoSolicitudObj() == null
                    || solicitudCmMgl.getTipoSolicitudObj().getTipoSolicitudId() == null) {
                String msnErr = "No fue posible consultar la solicitud.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msnErr, ""));
                return null;
            }
            BigDecimal tipoSolicitud = solicitudCmMgl.getTipoSolicitudObj().getTipoSolicitudId();

            Boolean esEliminacion =
                    ConstantsCM.TIPO_SOLICITUD_ELIMINACION_CM.equals(solicitudCmMgl.getTipoSolicitudObj().getAbreviatura())
                    || ConstantsCM.TIPO_SOLICITUD_ELIMINACION_ESCALAMIENTO.equals(solicitudCmMgl.getTipoSolicitudObj().getAbreviatura());

            if (tipoSolicitud.compareTo(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_VISITA_TECNICA)) == 0) {
                return ingresarGestionSolicitudVt(solicitudModCM);
            }
            if (tipoSolicitud.compareTo(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_CREACION_CM)) == 0 || esEliminacion) {
                return ingresarGestionSolicitudCreaCM(solicitudModCM);
            }
            if (tipoSolicitud.compareTo(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_MODIFICACION_CM)) == 0) {
                return ingresarGestionModificacionCm(solicitudModCM);
            }
            if (tipoSolicitud.compareTo(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_CREACION_HHPP)) == 0) {
                return ingresarGestionSolicitudHhpp(solicitudModCM);
            }
            if (tipoSolicitud.compareTo(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_MODIFICACION_HHPP)) == 0) {
                return ingresarGestionSolicitudHhpp(solicitudModCM);
            }
        } catch (ApplicationException e) {
            String msnErr = "Ha ocurrido un error Ingresando a la Gestion de la solicitud.";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
        return null;
    }
    
    private UsuariosServicesDTO usuarioSolicitudCreador;

    public String ingresarGestionSolicitudVt(CmtSolicitudCmMgl solicitudCmMgl) {
        String urlIngreso = "";
        try {
            accionSelect = null;
            respuestaSelect = null;
            visibleDatosSolicitud = false;

            boolean disponible = solicitudModCM.getDisponibilidadGestion() != null
                    && solicitudModCM.getDisponibilidadGestion().equalsIgnoreCase("1");
            
            if (usuarioLogin != null && usuarioLogin.getCedula() != null) {
                usuarioSolicitudCreador = (usuariosFacade.consultaInfoUserPorUsuario(solicitudModCM.getUsuarioCreacion()));
                usuarioSolicitud = usuariosFacade.consultaInfoUserPorCedula(solicitudModCM.getUsuarioSolicitudId());
            }
            if (disponible) {
                horarioSolicitudList = horarioRestriccionMglFacadeLocal.findBySolicitud(solicitudModCM);
                if (solicitudModCM != null) {
                    cmtNotasSolicitudMgl = solicitudModCM.getFirstNota();
                    solicitudModificacionCM = false;
                    showHorario = true;
                    modoGestion = true;
                    limpiarDataGestion(solicitudModCM.getTipoSolicitudObj().getTipoSolicitudId());
                    urlIngreso = cambiarTab("SOLICITUD");
                    if (solicitudModCM.getResultGestion() != null) {
                        solicitudModCM.setRespuestaActual("");
                    }
                }
            } else {
                urlIngreso = "/view/MGL/CM/solicitudes/general/solicitudesGestionar";
            }
        } catch (ApplicationException e) {
            String msnErr = "Ha ocurrido un error Ingresando a la Gestion de la solicitud de VT.";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
        return urlIngreso;
    }

    public String ingresarGestionSolicitudCreaCM(CmtSolicitudCmMgl solicitudCmMgl) {
        try {
            solicitudModificacionCM = false;
            showHorario = false;
            modoGestion = true;
            limpiarDataGestion(solicitudModCM.getTipoSolicitudObj().getTipoSolicitudId());
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ha ocurrido un error Ingresando a la Gestion de la solicitud de Creacion de CM." + e.getMessage(), e, LOGGER);
        }
        return cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_CREACION_CM).equals(solicitudCmMgl.getTipoSolicitudObj().getTipoSolicitudId())
                ? "/view/MGL/CM/solicitudes/CreacionCm/infoGestionCreaCM"
                : "/view/MGL/CM/gestionarEliminacionCM";
    }

    public String ingresarGestionModificacionCm(CmtSolicitudCmMgl solicitudCmMgl) {
        try {
            cmtNotasSolicitudMgl = solicitudModCM.getFirstNota();
            solicitudModificacionCM = true;
            showHorario = false;
            modoGestion = true;
            showNotas=false;
            cuentaMatriz = solicitudModCM.getCuentaMatrizObj();
            if (usuarioLogin.getCedula() != null) {
                solicitudModCM.setUsuarioGestionId(new BigDecimal(usuarioLogin.getCedula()));
            }
            
            limpiarDataGestion(solicitudModCM.getTipoSolicitudObj().getTipoSolicitudId());

            SolicitudModificacionCMBean solModCm = (SolicitudModificacionCMBean) JSFUtil.getSessionBean(SolicitudModificacionCMBean.class);
            solModCm.ingresarModificacionCM(ConstantsCM.MODO_INGRESO_GESTION);

            if (isActivoCheckModDatosCM()) {
                return cambiarTab("DATOSCM");
            } else if (isActivoCheckModDireccion()) {
                return cambiarTab("DIRECCION");
            } else if (isActivoCheckModSubedificios()) {
                return cambiarTab("SUBEDIFICIOS");
            } else if (isActivoCheckModCobertura()) {
                return cambiarTab("COBERTURA");
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ha ocurrido un error Ingresando a la Gestion de la solicitud de Modificacion de CM." + e.getMessage(), e, LOGGER);
        }
        return "/view/MGL/CM/solicitudes/modificacionCM/principalSolicitudModificarCM";
    }

    public String ingresarGestionSolicitudHhpp(CmtSolicitudCmMgl solicitudCmMgl) {
        try {
            solicitudModificacionCM = false;
            showHorario = true;
            modoGestion = true;
            selectedCmtSubEdificioMgl = null;
            accionSelect = null;
            respuestaSelect = null;
            if (solicitudModCM != null) {
                cmtNotasSolicitudMgl = solicitudModCM.getFirstNota();
                cuentaMatriz = solicitudModCM.getCuentaMatrizObj();
                if (usuarioLogin.getCedula() != null) {
                    solicitudModCM.setUsuarioGestionId(new BigDecimal(usuarioLogin.getCedula()));
                }
                limpiarDataGestion(solicitudModCM.getTipoSolicitudObj().getTipoSolicitudId());
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ha ocurrido un error Ingresando a la Gestion de la solicitud de Modificacion de CM." + e.getMessage(), e, LOGGER);
        }
        
        //JDHT
        cambiarTab("SOLICITUD");
        selectedTab = "GESTION_HHPP";
        return cambiarTabHhpp(selectedTab);
    }

    public String ingresarSolicitudCrearCm() {
        try {
            CmtCuentaMatrizMgl cm = new CmtCuentaMatrizMgl();
            limpiarDataSolicitud(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_CREACION_CM));
            limpiarDataNota(cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_NOTA_VISITA_TECNICA).getBasicaId());
            solicitudModificacionCM = false;
            showHorario = true;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Ha ocurrido un error Ingresando a la Gestion de la solicitud de Modificacion de CM." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ha ocurrido un error Ingresando a la Gestion de la solicitud de Modificacion de CM." + e.getMessage(), e, LOGGER);
        }
        return "/view/MGL/CM/solicitudes/CreacionCm/infoCreaCM";
    }

    public String ingresarEliminarCm() {
        try {
            
            CuentaMatrizBean cuentaMatrizBean = (CuentaMatrizBean) JSFUtil.getSessionBean(CuentaMatrizBean.class);
            CmtCuentaMatrizMgl cm = cuentaMatrizBean.getCuentaMatriz();

            if (cm == null || cm.getCuentaMatrizId() == null) {
                String msn = "Debe estar sobre el detalle de una cuenta matriz para solicitar la  Eliminacion";
                LOGGER.error(msn);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
            cuentaMatriz = cuentaMatrizMglFacadeLocal.findById(cm);
            limpiarDataSolicitud(cargarTipoSolicitud(ConstantsCM.TIPO_SOLICITUD_ELIMINACION_CM));
            solicitudModificacionCM = false;
            showHorario = true;
            
            if(solicitudModCM.getCuentaMatrizObj().
                    getListCmtSubEdificioMgl().size() > 0){
                int resta = solicitudModCM.
                        getCuentaMatrizObj().getListCmtSubEdificioMgl().size()-1;
                cantidadSubedificios= String.valueOf(resta);
            }else{
                cantidadSubedificios="0";
            }
            
        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Ha ocurrido un error Ingresando a la Gestion de la solicitud de Modificacion de CM." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ha ocurrido un error Ingresando a la Gestion de la solicitud de Modificacion de CM." + e.getMessage(), e, LOGGER);
        }
        return "/view/MGL/CM/eliminarCm";
    }

    public String crearSolicitudVt() {
        boolean ocurrioError = false;
        try {
            if (tipoAcondicionamiento != null
                    && tipoAcondicionamiento.getBasicaId() != null) {
                solicitudModCM.setTipoAcondicionamiento(tipoAcondicionamiento);
            }

             // correo asesor
            if (solicitudModCM.getCorreoAsesor() != null
                    && !solicitudModCM.getCorreoAsesor().trim().isEmpty()) {
                if (!validarCorreo(solicitudModCM.getCorreoAsesor())) {
                    String msg = "El campo correo asesor no tiene el formato requerido";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return null;
                } else {
                    if (solicitudModCM.getCorreoAsesor() != null && !solicitudModCM.getCorreoAsesor().isEmpty()) {
                        if (!validarDominioCorreos(solicitudModCM.getCorreoAsesor())) {
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
            if (usuarioSolicitud != null && usuarioSolicitud.getEmail() != null
                    && !usuarioSolicitud.getEmail().isEmpty()) {
                // Se adiciona el email del usuario solicitante.
                destinatariosCopia = usuarioSolicitud.getEmail();
            }
           
            // correo copia
            if (solicitudModCM.getCorreoCopiaSolicitud() != null
                    && !solicitudModCM.getCorreoCopiaSolicitud().trim().isEmpty()) {
                if (!validarCorreo(solicitudModCM.getCorreoCopiaSolicitud())) {
                    String msg = "El campo copia a no tiene el formato requerido";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return null;
                } else {
                    if (solicitudModCM.getCorreoCopiaSolicitud() != null && !solicitudModCM.getCorreoCopiaSolicitud().isEmpty()) {
                    if (validarDominioCorreos(solicitudModCM.getCorreoCopiaSolicitud())) {
                        // los destinatarios se separan por comma.
                        if (!destinatariosCopia.isEmpty()) {
                            destinatariosCopia += ",";
                        }
                        destinatariosCopia += solicitudModCM.getCorreoCopiaSolicitud();
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
            }
            solicitudModCM.setCorreoCopiaSolicitud(destinatariosCopia);
            solicitudModCM.setTipoSegmento(tipoSegmento);
            solicitudModCM.setTempSolicitud(timeSol);
            solicitudModCM.setFechaInicioCreacion(dateInicioCreacion);

            if (tecnologiaSelect != null && tecnologiaSelect.getBasicaId() != null && tecnologiaSelect.getBasicaId() != BigDecimal.ZERO) {
                solicitudModCM.setBasicaIdTecnologia(tecnologiaSelect);
            } else {
                solicitudModCM.setBasicaIdTecnologia(null);
            }
            
            //valbuenayf inicio ajuste centro poblado en null
            if (cuentaMatriz != null && cuentaMatriz.getCentroPoblado() != null && cuentaMatriz.getCentroPoblado().getGpoId() != null) {
                solicitudModCM.setCentroPobladoGpo(cuentaMatriz.getCentroPoblado());
            } 
            
            //cardenaslb ajuste fecha de progra posterior a la actual
            if (solicitudModCM.getFechaProgramcionVt() == null || solicitudModCM.getFechaProgramcionVt().toString().isEmpty()) {
                    String msg = "Debe Ingresar Fecha de Entrega";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return null;
                }else{
                    if (solicitudModCM.getFechaProgramcionVt().compareTo(new Date()) < 0) {
                        String msg = "La fecha  debe ser mayor a hoy";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msg, ""));
                        return null;

                    }
        
                }

            //valbuenayf fin ajuste centro poblado en null
            solicitudModCM.setDisponibilidadGestion("1");
            solicitudFacadeLocal.setUser(usuarioVT, perfilVT);
            solicitudModCM = solicitudFacadeLocal.crearSol(solicitudModCM);
            if (solicitudModCM.getSolicitudCmId() != null) {
                solicitudModCM = solicitudFacadeLocal.findById(solicitudModCM.getSolicitudCmId());
                cmtNotasSolicitudMgl.setSolicitudCm(solicitudModCM);
                cmtNotasSolicitudMgl.setDescripcion("Creacion Solicitud Visita Tecnica");
                cmtNotasSolicitudMgl = notasSolicitudMglFacadeLocal.crearNotSol(cmtNotasSolicitudMgl);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, ConstantsCM.MSN_PROCESO_EXITOSO + ", Se ha creado la Solicitud: <b>" + solicitudModCM.getSolicitudCmId().toString() + "</b>", ""));
        } catch (ApplicationException e) {
            String msn = ConstantsCM.MSN_ERROR_PROCESO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
        return "/view/MGL/CM/solicitudes/visitaTecnica/crearSolicitudVt";
    }

    public String gestionarSolicitudVt() {
        boolean ocurrioError = false;
        try {
            
            CmtBasicaMgl resultadoGestion = new CmtBasicaMgl();
            resultadoGestion.setBasicaId(respuestaSelect);
            solicitudModCM.setResultGestion(resultadoGestion);
            solicitudModCM.setFechaGestion(new Date());
            solicitudModCM.setTempGestion(solicitudModCM.getTiempoTotalGestionSolicitud());
            // correo asesor
            if (solicitudModCM.getCorreoAsesor() != null
                    && !solicitudModCM.getCorreoAsesor().trim().isEmpty()) {
                if (!validarCorreo(solicitudModCM.getCorreoAsesor())) {
                    String msg = "El campo correo asesor no tiene el formato requerido";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return null;
                } else {
                    if (solicitudModCM.getCorreoAsesor() != null && !solicitudModCM.getCorreoAsesor().isEmpty()) {
                        if (!validarDominioCorreos(solicitudModCM.getCorreoAsesor())) {
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
            String destinatariosCopia = "";
            if (usuarioSolicitudCreador != null && usuarioSolicitudCreador.getEmail() != null
                    && !usuarioSolicitudCreador.getEmail().isEmpty()) {
                // Se adiciona el email del usuario solicitante.
                destinatariosCopia = usuarioSolicitudCreador.getEmail();
            }
            if (solicitudModCM.getCorreoCopiaGestion() != null
                    && !solicitudModCM.getCorreoCopiaGestion().trim().isEmpty()) {
                if (!validarCorreo(solicitudModCM.getCorreoCopiaGestion())) {
                    String msg = "El campo copia a no tiene el formato requerido";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return null;
                } else {
                    if (solicitudModCM.getCorreoCopiaGestion() != null && !solicitudModCM.getCorreoCopiaGestion().isEmpty()) {
                        if (validarDominioCorreos(solicitudModCM.getCorreoCopiaGestion())) {
                            // los destinatarios se separan por comma.
                            if (!destinatariosCopia.isEmpty()) {
                                destinatariosCopia += ",";
                            }
                            destinatariosCopia += solicitudModCM.getCorreoCopiaGestion();
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
            }
            
            //cardenaslb ajuste fecha de progra posterior a la actual
             solicitudModCM.setCorreoCopiaSolicitud(destinatariosCopia);
            if (solicitudModCM.getFechaProgramcionVt() == null || solicitudModCM.getFechaProgramcionVt().toString().isEmpty()) {
                    String msg = "Debe Ingresar Fecha de Entrega";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return null;
                }else{
                    if (solicitudModCM.getFechaProgramcionVt().compareTo(new Date()) < 0) {
                        String msg = "La fecha  debe ser mayor a hoy";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msg, ""));
                        return null;

                    }
        
                }
            solicitudModCM = solicitudFacadeLocal.gestionSolicitudVt(solicitudModCM);
            if (solicitudModCM.getSolicitudCmId() != null) {
                gestionado = true;
                solicitudModCM = solicitudFacadeLocal.findById(solicitudModCM.getSolicitudCmId());
                CmtNotasSolicitudMgl notaGestionSolicitud = new CmtNotasSolicitudMgl();
                CmtBasicaMgl tipoNota = new CmtBasicaMgl();
                tipoNota.setBasicaId(cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_NOTA_VISITA_TECNICA).getBasicaId());

                notaGestionSolicitud.setTipoNotaObj(tipoNota);
                notaGestionSolicitud.setSolicitudCm(solicitudModCM);
                notaGestionSolicitud.setDescripcion("Gestion Solicitud Visita Tecnica");
                notaGestionSolicitud.setNota(solicitudModCM.getRespuestaActual());

                notasSolicitudMglFacadeLocal.crearNotSol(notaGestionSolicitud);
            }
            String msn = ConstantsCM.MSN_PROCESO_EXITOSO;
            if (solicitudModCM.getOrdenTrabajo() != null
                    && solicitudModCM.getOrdenTrabajo().getIdOt() != null) {
                msn += ", Se ha creado la Orden de trabajo: <b>"
                        + solicitudModCM.getOrdenTrabajo().getIdOt().toString() + "</b>";
            }
         
            
            if (usuarioLogin != null && usuarioLogin.getEmail() != null
                    && !usuarioLogin.getEmail().isEmpty()) {
                // Se adiciona el email del usuario solicitante.
                destinatariosCopia = usuarioLogin.getEmail();
            }
            // los destinatarios se separan por comma.
            if (destinatariosCopia != null && !destinatariosCopia.isEmpty()) {
                destinatariosCopia += ",";
            }
            destinatariosCopia += solicitudModCM.getCorreoCopiaSolicitud();
            solicitudModCM.setCorreoCopiaSolicitud(destinatariosCopia);
                    
            solicitudFacadeLocal.envioCorreoGestion(solicitudModCM);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, msn, ""));
        } catch (ApplicationException e) {
            try {
                CmtBasicaMgl cmtBasicaEstadoSol = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE);
                solicitudModCM.setEstadoSolicitudObj(cmtBasicaEstadoSol);
            } catch (ApplicationException ex) {
                String msn = ConstantsCM.MSN_ERROR_PROCESO + "" + ex.getMessage();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, msn, ""));
                return "";
            } 
            solicitudModCM.setEstadoSolicitudObj(tipoSegmento);
            String msn = ConstantsCM.MSN_ERROR_PROCESO + "" + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, msn, ""));
        }
        return cambiarTab("SOLICITUD");
    }

    public String mostarComentario(CmtNotasSolicitudMgl nota) {
        cmtNotasSolicitudMgl = nota;
        tipoNotaSelected = nota.getTipoNotaObj();
        return null;
    }

    public String guardarNota() {
        try {
            cmtNotasSolicitudMgl.setSolicitudCm(solicitudModCM);
            cmtNotasSolicitudMgl.setTipoNotaObj(tipoNotaSelected);
            cmtNotasSolicitudMgl = notasSolicitudMglFacadeLocal.crearNotSol(cmtNotasSolicitudMgl);
            if (cmtNotasSolicitudMgl != null && cmtNotasSolicitudMgl.getNotasId() != null) {
                solicitudModCM = solicitudFacadeLocal.findById(solicitudModCM.getSolicitudCmId());
                tipoNotaSelected = new CmtBasicaMgl();
                cmtNotasSolicitudMgl = new CmtNotasSolicitudMgl();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.MSN_PROCESO_EXITOSO, ""));
            }
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO ;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
        return cambiarTab("NOTAS");
    }

    public String guardarComentarioNota() {
        try {
            CmtNotasSolicitudDetalleMgl notaComentarioMgl = new CmtNotasSolicitudDetalleMgl();
            notaComentarioMgl.setNota(notaComentarioStr);
            notaComentarioMgl.setNotaSolicitud(cmtNotasSolicitudMgl);
            notaComentarioMgl = notasSolicitudDetalleMglFacadeLocal.create(notaComentarioMgl);
            if (notaComentarioMgl.getNotasDetalleId() != null) {
                notaComentarioStr = "";
                solicitudModCM = solicitudFacadeLocal.findById(solicitudModCM.getSolicitudCmId());
                tipoNotaSelected = new CmtBasicaMgl();
                cmtNotasSolicitudMgl = new CmtNotasSolicitudMgl();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.MSN_PROCESO_EXITOSO, ""));
            }
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO ;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
        return cambiarTab("NOTAS");
    }

    public boolean isActivoCheckModDatosCM() {
        activoCheckModDatosCM = solicitudModCM != null && (solicitudModCM.getModDatosCm() == 1) ? true : false;
        return activoCheckModDatosCM;
    }

    public void setActivoCheckModDatosCM(boolean activoCheckModDatosCM) {
        if (solicitudModCM != null) {
            if (activoCheckModDatosCM) {
                solicitudModCM.setModDatosCm(new Short("1"));
            } else {
                solicitudModCM.setModDatosCm(new Short("0"));
            }
        } 
        this.activoCheckModDatosCM = activoCheckModDatosCM;
    }

    public boolean isActivoCheckModDireccion() {
        activoCheckModDireccion = solicitudModCM != null && (solicitudModCM.getModDireccion() == 1) ? true : false;
        return activoCheckModDireccion;
    }

    public void setActivoCheckModDireccion(boolean activoCheckModDireccion) {
        if (activoCheckModDireccion) {
            solicitudModCM.setModDireccion(new Short("1"));
        } else {
            solicitudModCM.setModDireccion(new Short("0"));
        }
        this.activoCheckModDireccion = activoCheckModDireccion;
    }

    public boolean isActivoCheckModCobertura() {
        activoCheckModCobertura = solicitudModCM != null && (solicitudModCM.getModCobertura() == 1) ? true : false;
        return activoCheckModCobertura;
    }

    public void setActivoCheckModCobertura(boolean activoCheckModCobertura) {
        if (solicitudModCM != null) {
            if (activoCheckModCobertura) {
                solicitudModCM.setModCobertura(new Short("1"));
            } else {
                solicitudModCM.setModCobertura(new Short("0"));
            }
        }
        this.activoCheckModCobertura = activoCheckModCobertura;
    }

    public boolean isActivoCheckModSubedificios() {
        activoCheckModSubedificios = solicitudModCM != null && (solicitudModCM.getModSubedificios() == 1) ? true : false;
        return activoCheckModSubedificios;
    }

    public void setActivoCheckModSubedificios(boolean activoCheckModSubedificios) {
        if (solicitudModCM != null) {
            if (activoCheckModSubedificios) {
                solicitudModCM.setModSubedificios(new Short("1"));
            } else {
                solicitudModCM.setModSubedificios(new Short("0"));
            }
        }
        this.activoCheckModSubedificios = activoCheckModSubedificios;
    }

    public boolean isVisibleDatosSolicitud() {
        return visibleDatosSolicitud;
    }

    public void setVisibleDatosSolicitud(boolean visibleDatosSolicitud) {
        this.visibleDatosSolicitud = visibleDatosSolicitud;
    }

    public String cambiarVisibleDatosSolicitud() {
        visibleDatosSolicitud = !visibleDatosSolicitud;
        return null;
    }

    public String cambiarVisibleDatosDireccion() {
        visibleDatosDatosDireccion = !visibleDatosDatosDireccion;
        return null;
    }

    public String unidadRadioButtonChange() {
        disableCantidadTorres = (solicitudModCM != null
                && solicitudModCM.getUnidad() != null && solicitudModCM.getUnidad().equalsIgnoreCase("1"));
        return null;
    }

    public String guardarHorarioRestriccion()  {
        try {
            horarioRestriccionMglFacadeLocal.setUser(usuarioVT, perfilVT);
            if (session.getAttribute("ComponenteHorario") != null) {
                horarioSolicitudList = (List<CmtHorarioRestriccionMgl>) session.getAttribute("ComponenteHorario");
                session.removeAttribute("ComponenteHorario");
            }
            if (solicitudModCM != null) {
                if (horarioSolicitudList != null && !horarioSolicitudList.isEmpty()) {
                    if (!horarioRestriccionMglFacadeLocal.deleteBySolicitud(solicitudModCM)) {
                        String msn = "Ocurrio un error actualizando el horario ";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        return null;
                    }
                    try {
                        CuentaMatrizBean cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
                        CmtCuentaMatrizMgl cuentaMatriz = cuentaMatrizBean.getCuentaMatriz();
                        CmtSubEdificioMgl cmSubEdificio = cuentaMatrizBean.getSelectedCmtSubEdificioMgl();
                        for (CmtHorarioRestriccionMgl hr : horarioSolicitudList) {
                            hr.setSolicitudCm(solicitudModCM);
                            hr.setHorRestriccionId(null);
                            hr.setEstadoRegistro(ConstantsCM.REGISTRO_ENUSO_ID);
                            hr.setCuentaMatrizObj(cuentaMatriz);
                            hr.setSubEdificioObj(cmSubEdificio);
                            horarioRestriccionMglFacadeLocal.create(hr);
                            hr.setCuentaMatrizObj(cuentaMatriz);
                            hr.setSubEdificioObj(cmSubEdificio);
                        }
                        horarioSolicitudList = horarioRestriccionMglFacadeLocal.findBySolicitud(solicitudModCM);
                        session.removeAttribute("ComponenteHorario");
                        String msn = "Horario actualizado con �xito";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                    } catch (ApplicationException ex) {
                        String msn = "Error actualizado horario";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    }
                } else {
                    String msn = "No puede agregar horario vac�o.";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                }
            } else {
                String msn = "No puede agregar horario si la solicitud no ha sido creada.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
        return cambiarTab("HORARIO");
    }

    public void limpiarCamposNota() {
        cmtNotasSolicitudMgl = new CmtNotasSolicitudMgl();
        tipoNotaSelected = new CmtBasicaMgl();
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
     
    
    public boolean validarGestionSolicitud() {
        return validarAccion(FORMULARIO_GESTION_SOLICITUD, ValidacionUtil.OPC_CREACION);
    }
    /**
     *
     * @return
     */
    public boolean validarCreacionNota() {
        return validarAccion(FORMULARIO_NOTA, ValidacionUtil.OPC_CREACION);
    }

    /**
     *
     * @return
     */
    public boolean validarCreacionComentario() {
        return validarAccion(FORMULARIO_COMENTARIO, ValidacionUtil.OPC_CREACION);
    }

    public boolean validarTabModificacionCMDatos() {
        return validarAccion(TAB_TIPO_MOD_CM_DATOS, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarTabModificacionCMDireccion() {
        return validarAccion(TAB_TIPO_MOD_CM_DIRECCION, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarTabModificacionCMSubEdificios() {
        return validarAccion(TAB_TIPO_MOD_CM_SUBEDIFICIOS, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarTabModificacionCMCobertura() {
        return validarAccion(TAB_TIPO_MOD_CM_COBERTURA, ValidacionUtil.OPC_EDICION);
    }

    /**
     * M&eacute;todo para validar las acciones a realizar en el formulario
     *
     * @param formulario String componente del formulario a validar
     * @param opcion String accion que se valida del componente
     * @return boolean indicador para verificar si se visualizan o no los
     * componentes
     */
    private boolean validarAccion(String formulario, String opcion) {
        try {
            return ValidacionUtil.validarVisualizacion(formulario, opcion, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
    public BigDecimal cargarTipoSolicitud(String abreviatura) {
        try {
            cmtTipoSolicitudMgl = tipoSolicitudFacadeLocal.findTipoSolicitudByAbreviatura(abreviatura);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
        return cmtTipoSolicitudMgl.getTipoSolicitudId();
    }
    public boolean validarDominioCorreos(String correoCopia){
        boolean resulto = false;
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
    
    public boolean validarCrearSolVtCm() {
        return validarEdicion(VALIDARCREARSOLVTCM);
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

    /**
     * Verifica si se encuentra activo el flag que permite visualizar
     * la opción de traslado de HHPP Bloqueado
     *
     * @return Retorna true si el flag para validar la opción está activo
     * @author Gildardo Mora
     */
    public boolean isActiveCcmmTrasladoHhppBloqueado() {
        return hhppVirtualMglFacadeLocal.isActiveCcmmTrasladoHhppBloqueado();
    }


    public void initializedComponents() {
        LOGGER.info("Session Bean CM iniciada");
    }

    public CmtCuentaMatrizMgl getCuentaMatriz() {
        return cuentaMatriz;
    }

    public void setCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) {
        this.cuentaMatriz = cuentaMatriz;
    }

    public String getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(String selectedTab) {
        this.selectedTab = selectedTab;
    }

    public CmtSolicitudCmMgl getSolicitudModCM() {
        return solicitudModCM;
    }

    public void setSolicitudModCM(CmtSolicitudCmMgl solicitudModCM) {
        this.solicitudModCM = solicitudModCM;
    }

    public List<CmtTipoSolicitudMgl> getTipoSolicitudList() {
        return tipoSolicitudList;
    }

    public void setTipoSolicitudList(List<CmtTipoSolicitudMgl> tipoSolicitudList) {
        this.tipoSolicitudList = tipoSolicitudList;
    }

    public UsuariosServicesDTO getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(UsuariosServicesDTO usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public CmtNotasSolicitudMgl getCmtNotasSolicitudMgl() {
        return cmtNotasSolicitudMgl;
    }

    public void setCmtNotasSolicitudMgl(CmtNotasSolicitudMgl cmtNotasSolicitudMgl) {
        this.cmtNotasSolicitudMgl = cmtNotasSolicitudMgl;
    }

    public String getTmpInicio() {
        return tmpInicio;
    }

    public void setTmpInicio(String tmpInicio) {
        this.tmpInicio = tmpInicio;
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

    public boolean isSolicitudModificacionCM() {
        return solicitudModificacionCM;
    }

    public void setSolicitudModificacionCM(boolean solicitudModificacionCM) {
        this.solicitudModificacionCM = solicitudModificacionCM;
    }

    public boolean isSolicitudCreacionModificacionHHPP() {
        return solicitudCreacionModificacionHHPP;
    }

    public void setSolicitudCreacionModificacionHHPP(boolean solicitudCreacionModificacionHHPP) {
        this.solicitudCreacionModificacionHHPP = solicitudCreacionModificacionHHPP;
    }

    public CmtBasicaMgl getTipoAcondicionamiento() {
        return tipoAcondicionamiento;
    }

    public void setTipoAcondicionamiento(CmtBasicaMgl tipoAcondicionamiento) {
        this.tipoAcondicionamiento = tipoAcondicionamiento;
    }

    public List<CmtBasicaMgl> getTipoAcondicionamientoList() {
        return tipoAcondicionamientoList;
    }

    public void setTipoAcondicionamientoList(List<CmtBasicaMgl> tipoAcondicionamientoList) {
        this.tipoAcondicionamientoList = tipoAcondicionamientoList;
    }

    public CmtBasicaMgl getTipoSegmento() {
        return tipoSegmento;
    }

    public void setTipoSegmento(CmtBasicaMgl tipoSegmento) {
        this.tipoSegmento = tipoSegmento;
    }

    public List<CmtBasicaMgl> getTipoSegmentoList() {
        return tipoSegmentoList;
    }

    public void setTipoSegmentoList(List<CmtBasicaMgl> tipoSegmentoList) {
        this.tipoSegmentoList = tipoSegmentoList;
    }

    public CmtBasicaMgl getTipoNotaSelected() {
        return tipoNotaSelected;
    }

    public void setTipoNotaSelected(CmtBasicaMgl tipoNotaSelected) {
        this.tipoNotaSelected = tipoNotaSelected;
    }

    public List<CmtBasicaMgl> getTipoNotaList() {
        return tipoNotaList;
    }

    public void setTipoNotaList(List<CmtBasicaMgl> tipoNotaList) {
        this.tipoNotaList = tipoNotaList;
    }

    public String getNotaComentarioStr() {
        return notaComentarioStr;
    }

    public void setNotaComentarioStr(String notaComentarioStr) {
        this.notaComentarioStr = notaComentarioStr;
    }

    public boolean isShowNotas() {
        return showNotas;
    }

    public void setShowNotas(boolean showNotas) {
        this.showNotas = showNotas;
    }

    public boolean isModoGestion() {
        return modoGestion;
    }

    public void setModoGestion(boolean modoGestion) {
        this.modoGestion = modoGestion;
    }

    public String getSelectedTrack() {
        return selectedTrack;
    }

    public void setSelectedTrack(String selectedTrack) {
        this.selectedTrack = selectedTrack;
    }

    public boolean isShowHorario() {
        return showHorario;
    }

    public void setShowHorario(boolean showHorario) {
        this.showHorario = showHorario;
    }

    public List<CmtHorarioRestriccionMgl> getHorarioSolicitudList() {
        return horarioSolicitudList;
    }

    public void setHorarioSolicitudList(List<CmtHorarioRestriccionMgl> horarioSolicitudList) {
        this.horarioSolicitudList = horarioSolicitudList;
    }

    public CmtSubEdificioMgl getSelectedCmtSubEdificioMgl()  {
        try {
            if (selectedCmtSubEdificioMgl != null) {
                return selectedCmtSubEdificioMgl;
            } else {
                if (activoCheckCreacionHHPP) {
                    listCmtSubEdificioMgl = cuentaMatriz.getSubEdificiosValidacionesHhpp();
                } else {
                    listCmtSubEdificioMgl = solicitudModCM.getCuentaMatrizObj().getSubEdificiosMglNoGeneral();
                }
                if (listCmtSubEdificioMgl != null && !listCmtSubEdificioMgl.isEmpty()) {
                    selectedCmtSubEdificioMgl = listCmtSubEdificioMgl.get(0);
                    return selectedCmtSubEdificioMgl;
                }
                return null;
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error EncabezadoSolicitudModificacionCMBean class" + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void setSelectedCmtSubEdificioMgl(CmtSubEdificioMgl selectedCmtSubEdificioMgl) {
        this.selectedCmtSubEdificioMgl = selectedCmtSubEdificioMgl;
    }

    public List<CmtBasicaMgl> getResultadoGestDatosCmList() {
        return resultadoGestDatosCmList;
    }

    public void setResultadoGestDatosCmList(List<CmtBasicaMgl> resultadoGestDatosCmList) {
        this.resultadoGestDatosCmList = resultadoGestDatosCmList;
    }

    public List<CmtBasicaMgl> getResultadoGestDireccionList() {
        return resultadoGestDireccionList;
    }

    public void setResultadoGestDireccionList(List<CmtBasicaMgl> resultadoGestDireccionList) {
        this.resultadoGestDireccionList = resultadoGestDireccionList;
    }

    public List<CmtBasicaMgl> getResultadoGestSubEdiList() {
        return resultadoGestSubEdiList;
    }

    public void setResultadoGestSubEdiList(List<CmtBasicaMgl> resultadoGestSubEdiList) {
        this.resultadoGestSubEdiList = resultadoGestSubEdiList;
    }

    public List<CmtBasicaMgl> getResultadoGestCoberturaList() {
        return resultadoGestCoberturaList;
    }

    public void setResultadoGestCoberturaList(List<CmtBasicaMgl> resultadoGestCoberturaList) {
        this.resultadoGestCoberturaList = resultadoGestCoberturaList;
    }

    public List<CmtTipoBasicaMgl> getAccionList() {
        return accionList;
    }

    public void setAccionList(List<CmtTipoBasicaMgl> accionList) {
        this.accionList = accionList;
    }

    public List<CmtTipoBasicaMgl> getAccionGestModDatosCmList() {
        return accionGestModDatosCmList;
    }

    public void setAccionGestModDatosCmList(List<CmtTipoBasicaMgl> accionGestModDatosCmList) {
        this.accionGestModDatosCmList = accionGestModDatosCmList;
    }

    public List<CmtTipoBasicaMgl> getAccionGestModDireccionList() {
        return accionGestModDireccionList;
    }

    public void setAccionGestModDireccionList(List<CmtTipoBasicaMgl> accionGestModDireccionList) {
        this.accionGestModDireccionList = accionGestModDireccionList;
    }

    public List<CmtTipoBasicaMgl> getAccionGestModSubEdiList() {
        return accionGestModSubEdiList;
    }

    public void setAccionGestModSubEdiList(List<CmtTipoBasicaMgl> accionGestModSubEdiList) {
        this.accionGestModSubEdiList = accionGestModSubEdiList;
    }

    public List<CmtTipoBasicaMgl> getAccionGestModCoberturaList() {
        return accionGestModCoberturaList;
    }

    public void setAccionGestModCoberturaList(List<CmtTipoBasicaMgl> accionGestModCoberturaList) {
        this.accionGestModCoberturaList = accionGestModCoberturaList;
    }

    public List<CmtTipoBasicaMgl> getAccionGestCrearVisitaList() {
        return accionGestCrearVisitaList;
    }

    public void setAccionGestCrearVisitaList(List<CmtTipoBasicaMgl> accionGestCrearVisitaList) {
        this.accionGestCrearVisitaList = accionGestCrearVisitaList;
    }

    public List<CmtBasicaMgl> getResultadoGestList() {
        return resultadoGestList;
    }

    public void setResultadoGestList(List<CmtBasicaMgl> resultadoGestList) {
        this.resultadoGestList = resultadoGestList;
    }

    public BigDecimal getAccionSelect() {
        return accionSelect;
    }

    public void setAccionSelect(BigDecimal accionSelect) {
        this.accionSelect = accionSelect;
    }

    public BigDecimal getAccionModDatosSelect() {
        return accionModDatosSelect;
    }

    public void setAccionModDatosSelect(BigDecimal accionModDatosSelect) {
        this.accionModDatosSelect = accionModDatosSelect;
    }

    public BigDecimal getAccionModDireccionSelect() {
        return accionModDireccionSelect;
    }

    public void setAccionModDireccionSelect(BigDecimal accionModDireccionSelect) {
        this.accionModDireccionSelect = accionModDireccionSelect;
    }

    public BigDecimal getAccionModSubEdiSelect() {
        return accionModSubEdiSelect;
    }

    public void setAccionModSubEdiSelect(BigDecimal accionModSubEdiSelect) {
        this.accionModSubEdiSelect = accionModSubEdiSelect;
    }

    public BigDecimal getAccionModCoberturaSelect() {
        return accionModCoberturaSelect;
    }

    public void setAccionModCoberturaSelect(BigDecimal accionModCoberturaSelect) {
        this.accionModCoberturaSelect = accionModCoberturaSelect;
    }

    public BigDecimal getRespuestaSelect() {
        return respuestaSelect;
    }

    public void setRespuestaSelect(BigDecimal respuestaSelect) {
        this.respuestaSelect = respuestaSelect;
    }

    public BigDecimal getRespuestaModDatosSelect() {
        return respuestaModDatosSelect;
    }

    public void setRespuestaModDatosSelect(BigDecimal respuestaModDatosSelect) {
        this.respuestaModDatosSelect = respuestaModDatosSelect;
    }

    public BigDecimal getRespuestaModSubEdiSelect() {
        return respuestaModSubEdiSelect;
    }

    public void setRespuestaModSubEdiSelect(BigDecimal respuestaModSubEdiSelect) {
        this.respuestaModSubEdiSelect = respuestaModSubEdiSelect;
    }

    public BigDecimal getRespuestaModDireccionSelect() {
        return respuestaModDireccionSelect;
    }

    public void setRespuestaModDireccionSelect(BigDecimal respuestaModDireccionSelect) {
        this.respuestaModDireccionSelect = respuestaModDireccionSelect;
    }

    public BigDecimal getRespuestaModCoberturaSelect() {
        return respuestaModCoberturaSelect;
    }

    public void setRespuestaModCoberturaSelect(BigDecimal respuestaModCoberturaSelect) {
        this.respuestaModCoberturaSelect = respuestaModCoberturaSelect;
    }

    public List<CmtBasicaMgl> getOrigenSolicitudList() {
        return origenSolicitudList;
    }

    public void setOrigenSolicitudList(List<CmtBasicaMgl> origenSolicitudList) {
        this.origenSolicitudList = origenSolicitudList;
    }

    public boolean isDisableCantidadTorres() {
        return disableCantidadTorres;
    }

    public void setDisableCantidadTorres(boolean disableCantidadTorres) {
        this.disableCantidadTorres = disableCantidadTorres;
    }

    public HhppMgl getSelectHhppMgl() {
        return selectHhppMgl;
    }

    public void setSelectHhppMgl(HhppMgl selectHhppMgl) {
        this.selectHhppMgl = selectHhppMgl;
    }

    public Date getDateInicioCreacion() {
        return dateInicioCreacion;
    }

    public void setDateInicioCreacion(Date dateInicioCreacion) {
        this.dateInicioCreacion = dateInicioCreacion;
    }

    public boolean isVisibleDatosDatosDireccion() {
        return visibleDatosDatosDireccion;
    }

    public void setVisibleDatosDatosDireccion(boolean visibleDatosDatosDireccion) {
        this.visibleDatosDatosDireccion = visibleDatosDatosDireccion;
    }

    public boolean isGestionado() {
        return gestionado;
    }

    public void setGestionado(boolean gestionado) {
        this.gestionado = gestionado;
    }

    public List<CmtSubEdificioMgl> getListCmtSubEdificioMgl() {
        return listCmtSubEdificioMgl;
    }

    public void setListCmtSubEdificioMgl(List<CmtSubEdificioMgl> listCmtSubEdificioMgl) {
        this.listCmtSubEdificioMgl = listCmtSubEdificioMgl;
    }

    public String getDeltaTiempo() {
        return deltaTiempo;
    }

    public void setDeltaTiempo(String deltaTiempo) {
        this.deltaTiempo = deltaTiempo;
    }

    public boolean isActivoCheckCreacionHHPP() {
        return activoCheckCreacionHHPP;
    }

    public void setActivoCheckCreacionHHPP(boolean activoCheckCreacionHHPP) {
        activoCheckModificacionHHPP = !activoCheckCreacionHHPP && !activoCheckTrasladoHhppBloqueado;
        activoCheckTrasladoHhppBloqueado = !activoCheckCreacionHHPP && !activoCheckModificacionHHPP;
        this.activoCheckCreacionHHPP = activoCheckCreacionHHPP;
    }

    public boolean isActivoCheckModificacionHHPP() {
        return activoCheckModificacionHHPP;
    }

    public void setActivoCheckModificacionHHPP(boolean activoCheckModificacionHHPP) {
        activoCheckCreacionHHPP = !activoCheckModificacionHHPP && !activoCheckTrasladoHhppBloqueado;
        activoCheckTrasladoHhppBloqueado = !activoCheckModificacionHHPP && !activoCheckCreacionHHPP;
        this.activoCheckModificacionHHPP = activoCheckModificacionHHPP;
    }

    /* Brief 57762 Creacion HHPP Virtual */
    public boolean isActivoCheckTrasladoHhppBloqueado() {
        return activoCheckTrasladoHhppBloqueado;
    }

    public void setActivoCheckTrasladoHhppBloqueado(boolean activoCheckTrasladoHhppBloqueado) {
        activoCheckCreacionHHPP = !activoCheckTrasladoHhppBloqueado && !activoCheckModificacionHHPP;
        activoCheckModificacionHHPP = !activoCheckTrasladoHhppBloqueado && !activoCheckCreacionHHPP;
        this.activoCheckTrasladoHhppBloqueado = activoCheckTrasladoHhppBloqueado;
    }
    /*Cierre Brief 57762*/

    public List<CmtBasicaMgl> getListaTecnologiasBasica() {
        return listaTecnologiasBasica;
    }

    public void setListaTecnologiasBasica(List<CmtBasicaMgl> listaTecnologiasBasica) {
        this.listaTecnologiasBasica = listaTecnologiasBasica;
    }

    public CmtBasicaMgl getTecnologiaSelect() {
        return tecnologiaSelect;
    }

    public void setTecnologiaSelect(CmtBasicaMgl tecnologiaSelect) {
        this.tecnologiaSelect = tecnologiaSelect;
    }

    public UsuariosServicesDTO getUsuarioSolicitud() {
        return usuarioSolicitud;
    }

    public void setUsuarioSolicitud(UsuariosServicesDTO usuarioSolicitud) {
        this.usuarioSolicitud = usuarioSolicitud;
    }

    public CmtTipoSolicitudMgl getCmtTipoSolicitudMgl() {
        return cmtTipoSolicitudMgl;
    }

    public void setCmtTipoSolicitudMgl(CmtTipoSolicitudMgl cmtTipoSolicitudMgl) {
        this.cmtTipoSolicitudMgl = cmtTipoSolicitudMgl;
    } 

    public String getCantidadSubedificios() {
        return cantidadSubedificios;
    }

    public void setCantidadSubedificios(String cantidadSubedificios) {
        this.cantidadSubedificios = cantidadSubedificios;
    }

    public UsuariosServicesDTO getUsuarioSolicitudCreador() {
        return usuarioSolicitudCreador;
    }

    public void setUsuarioSolicitudCreador(UsuariosServicesDTO usuarioSolicitudCreador) {
        this.usuarioSolicitudCreador = usuarioSolicitudCreador;
    }

}
