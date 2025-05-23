/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.FiltroConsultaHhppDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.DrDireccionFacadeLocal;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.TipoHhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.*;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.TipoHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtArchivosVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtModificacionesCcmmAudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNodosSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtUnidadesPreviasMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import static co.com.claro.mgl.mbeans.util.ConstantsCM.TABS_CM_HHPP.CREACION_HHPP;
import static co.com.claro.mgl.mbeans.util.ConstantsCM.TABS_CM_HHPP.MODIFICACION_HHPP;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author bocanegravm
 */


@ManagedBean(name = "cmtEstadoSolicitudBean")
@ViewScoped
public class CmtEstadoSolicitudBean implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(CmtEstadoSolicitudBean.class);
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private CmtSolicitudCmMgl cmtSolicitudCmMgl;
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private String selectedTab = "SOLICITUD";
    private String direccion;
    private String selectedBarrio;
    private DrDireccion drDireccion;
    private CmtDireccionSolicitudMgl cmtDireccionSolictudMgl = new CmtDireccionSolicitudMgl();
    private List<CmtDireccionSolicitudMgl> direccionSolModList;
    private CmtSolicitudSubEdificioMgl solicitudSubEdificioMgl = new CmtSolicitudSubEdificioMgl();
    private CmtSolicitudSubEdificioMgl solSubEdificioSelecMod = new CmtSolicitudSubEdificioMgl();
    private CmtSolicitudSubEdificioMgl solSubEdificioCreaCM = new CmtSolicitudSubEdificioMgl();
    private List<CmtSolicitudSubEdificioMgl> coberturaModList;
    private List<CmtSolicitudSubEdificioMgl> solicitudesSubEdificiosList;
    private List<CmtSolicitudSubEdificioMgl> subEdificiosModList;
    private List<CmtSubEdificioMgl> listCmtSubEdificioMgl;
    private List<CmtSolicitudHhppMgl> cmtSolicitudHhppMglListToChanges;
    private CmtSubEdificioMgl selectedCmtSubEdificioMgl;
    private CmtSubEdificioMgl subEdificioGeneralCm;
    private String nododelBean;
    private CmtNotasSolicitudMgl cmtNotasSolicitudMgl;
    private CmtCuentaMatrizMgl cuentaMatriz;
    private boolean activoCheckModDatosCM;
    private boolean controlModDatosCM;
    private boolean activoCheckModDireccion;
    private boolean controlModDatosDir;
    private boolean activoCheckModSubedificios;
    private boolean controlModDatosSub;
    private boolean activoCheckModCobertura;
    private boolean controlModDatosCobertura;
    private String tiempoCreacionSol;
    public boolean modifica;
    private boolean showHorario;
    private CmtBasicaMgl tipoAcondicionamiento;
    private CmtBasicaMgl tipoSegmento;
    private CmtBasicaMgl tecnologiaSelect;
    private boolean activoCheckCreacionHHPP;
    private boolean activoCheckModificacionHHPP;
    private boolean solicitudCreaHhpp;
    private boolean solicitudModHhpp;
    private List<HhppMgl> listaHhppToChanges;
    private int filasPag = ConstantsCM.PAGINACION_OCHO_FILAS;
    private List<HhppMgl> hhppCmList;
    private List<Integer> paginaList;
    private List<CmtHorarioRestriccionMgl> horarioSolicitudList;
    private String numPagina;
    private String pageActual;
    private int actual;
    private CmtTipoSolicitudMgl cmtTipoSolicitudMgl;
    private UsuariosServicesDTO usuarioSolicitud;
    private UsuariosServicesDTO usuarioGestSolicitudCm;
    private AddressService addressService;
    private List<CmtUnidadesPreviasMgl> unidadesDeLadireccion;
    private List<CmtNodosSolicitudMgl> listTablaNodosSolicitud;
    private CmtNotasSolicitudMgl cmtNotasGestionSolicitudMgl;
    private String urlArchivoSoporte = "";
    private CmtSubEdificioMgl subEdificioAudMgl;
    private CmtSubEdificioMgl subGralEli;
    @EJB
    private DrDireccionFacadeLocal drDireccionMglFacadeLocal;
    @EJB
    private CmtHorarioRestriccionMglFacadeLocal horarioRestriccionMglFacadeLocal;
    @EJB
    private HhppMglFacadeLocal hhppFacadeLocal;
    @EJB
    private CmtSolicitudCmMglFacadeLocal cmtSolicitudCmMglFacadeLocal;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private CmtValidadorDireccionesFacadeLocal direccionesFacadeLocal;
    @EJB
    private CmtNodosSolicitudMglFacadeLocal cmtNodosSolicitudMglFacadeLocal;
    @EJB
    private CmtSubEdificioMglFacadeLocal subEdificioMglFacadeLocal;
    private boolean visibleDatosSolicitud;
    private boolean visibleDatosDireccion;
    @EJB
    private CmtArchivosVtMglFacadeLocal archivosVtMglFacadeLocal;
    @EJB
    private CmtModCcmmAudMglFacadeLocal modCcmmAudMglFacadeLocal;
    @EJB
    private TipoHhppMglFacadeLocal tipoHhppMglFacadeLocal;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cuentaMatrizFacadeLocal;
    @EJB
    private CmtSubEdificioMglFacadeLocal cmtSubEdificioMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    private SecurityLogin securityLogin;
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private List<TipoHhppMgl> listaTipoHhpp;
    private String tipoHHppSelected;
    private String usuarioVt ;
    
    
    public CmtEstadoSolicitudBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
             if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
            }
                usuarioVt = securityLogin.getLoginUser();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se genera error al consultar el estado de solicitud: en CmtEstadoSolicitudBean" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al consultar el estado de solicitud: en CmtEstadoSolicitudBean" + e.getMessage(), e, LOGGER);
        }
    }
    

    @PostConstruct
    public void init() {
        try {

            cmtSolicitudCmMgl = (CmtSolicitudCmMgl) session.getAttribute("idSolicitudCm");
            cmtSolicitudCmMgl = cmtSolicitudCmMglFacadeLocal.findById(cmtSolicitudCmMgl.getSolicitudCmId());
            listaTipoHhpp = tipoHhppMglFacadeLocal.findAll();
            if (cmtSolicitudCmMgl.getUsuarioSolicitudId() != null) {
                usuarioSolicitud = usuariosFacade.consultaInfoUserPorCedula(cmtSolicitudCmMgl.getUsuarioSolicitudId());
            }
            
            visibleDatosSolicitud = false;
            visibleDatosDireccion = false;
            selectedTab = (String) session.getAttribute("selectedTab");

            if (selectedTab == null || selectedTab.isEmpty()) {
                selectedTab = "SOLICITUD";
            }
            cmtTipoSolicitudMgl = cmtSolicitudCmMgl.getTipoSolicitudObj();
            tipoAcondicionamiento = cmtSolicitudCmMgl.getTipoAcondicionamiento();
            tipoSegmento = cmtSolicitudCmMgl.getTipoSegmento();
            tecnologiaSelect = cmtSolicitudCmMgl.getBasicaIdTecnologia();

            if (cmtSolicitudCmMgl.getCuentaMatrizObj() != null) {
                cuentaMatriz = cmtSolicitudCmMgl.getCuentaMatrizObj();
                listCmtSubEdificioMgl = cuentaMatriz.getSubEdificiosMglNoGeneral();
            }

            if (listCmtSubEdificioMgl != null && !listCmtSubEdificioMgl.isEmpty()) {
                selectedCmtSubEdificioMgl = listCmtSubEdificioMgl.get(0);

            }
            if (cmtTipoSolicitudMgl.getAbreviatura().equalsIgnoreCase(ConstantsCM.TIPO_SOLICITUD_CREACION_CM)) {
                modifica = false;
                listTablaNodosSolicitud = cmtNodosSolicitudMglFacadeLocal.findBySolicitudId(cmtSolicitudCmMgl);

            } else if (cmtTipoSolicitudMgl.getAbreviatura().equalsIgnoreCase(ConstantsCM.TIPO_SOLICITUD_VISITA_TECNICA)) {
                modifica = false;
            } else if (cmtTipoSolicitudMgl.getAbreviatura().equalsIgnoreCase(ConstantsCM.TIPO_SOLICITUD_ELIMINACION_CM)) {
                modifica = false;
                subGralEli = subEdificioMglFacadeLocal.findSubEdificioGeneralByCuentaMatrizEliminado(cuentaMatriz);
            } else if (cmtTipoSolicitudMgl.getAbreviatura().equalsIgnoreCase(ConstantsCM.TIPO_SOLICITUD_CREACION_HHPP)) {
                modifica = false;
                activoCheckCreacionHHPP = true;
                activoCheckModificacionHHPP = false;
                cargarHhppCm();

                cmtSolicitudHhppMglListToChanges = cmtSolicitudCmMgl.getHhppToChangeList();
                if (selectedCmtSubEdificioMgl != null) {
                    listaHhppToChanges = selectedCmtSubEdificioMgl.getListHhpp();
                }

            } else if (cmtTipoSolicitudMgl.getAbreviatura().equalsIgnoreCase(ConstantsCM.TIPO_SOLICITUD_MODIFICACION_HHPP)) {
                modifica = false;
                activoCheckCreacionHHPP = false;
                activoCheckModificacionHHPP = true;
                cargarHhppCm();

                cmtSolicitudHhppMglListToChanges = cmtSolicitudCmMgl.getHhppToChangeList();
                if (selectedCmtSubEdificioMgl != null) {
                    listaHhppToChanges = selectedCmtSubEdificioMgl.getListHhpp();
                }

            } else {
                modifica = true;
                if (cmtSolicitudCmMgl.getEstadoSolicitudObj().
                        getNombreBasica().equalsIgnoreCase("FINALIZADO")) {
                    if (cmtSolicitudCmMgl.getModDatosCm() == 1 || cmtSolicitudCmMgl.getModSubedificios() == 1) {
                        cargaDatosAud();
                    }

                }
            }

            if (cmtSolicitudCmMgl.getListCmtDireccionesMgl().size() > 0) {
                cmtDireccionSolictudMgl = cmtSolicitudCmMgl.getListCmtDireccionesMgl().get(0);
                direccionSolModList = cmtSolicitudCmMgl.getListCmtDireccionesMgl();

                addressService = direccionesFacadeLocal.calcularCobertura(cmtDireccionSolictudMgl);

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

                if (cmtSolicitudCmMgl.getListUnidadesPreviasCm() == null
                        || cmtSolicitudCmMgl.getListUnidadesPreviasCm().isEmpty()) {
                    unidadesDeLadireccion
                            = direccionesFacadeLocal.unidadesDeLaDireccionMgl(cmtDireccionSolictudMgl);
                } else {
                    unidadesDeLadireccion = cmtSolicitudCmMgl.getListUnidadesPreviasCm();
                }

            }
            if (cmtSolicitudCmMgl.getUsuarioGestionId() != null) {
                usuarioGestSolicitudCm = usuariosFacade
                        .consultaInfoUserPorCedula(cmtSolicitudCmMgl.getUsuarioGestionId());
                //usuarioGestSolicitudCm = usuarioServicesFacadeLocal.
            }

            drDireccion = new DrDireccion();
            drDireccion = cmtDireccionSolictudMgl.getCamposDrDireccion();
            direccion = drDireccionMglFacadeLocal.getDireccion(drDireccion);
            selectedBarrio = cmtDireccionSolictudMgl.getBarrio();

            if (cmtSolicitudCmMgl.getListCmtSolicitudSubEdificioMgl().size() > 0) {
                solicitudesSubEdificiosList = cmtSolicitudCmMgl.getListCmtSolicitudSubEdificioMgl();
                solSubEdificioCreaCM = solicitudesSubEdificiosList.get(0);

                CmtSubEdificioMgl se = new CmtSubEdificioMgl();
                if (cuentaMatriz != null) {
                    se = cuentaMatriz.getSubEdificioGeneral();
                }
                coberturaModList = new ArrayList<>();
                subEdificiosModList = new ArrayList<>();

                for (CmtSolicitudSubEdificioMgl sse : solicitudesSubEdificiosList) {

                    if (sse.getSubEdificioObj() != null && sse.getSubEdificioObj().getSubEdificioId()
                            != null && sse.getSubEdificioObj().getSubEdificioId().
                            equals(se.getSubEdificioId())) {
                        sse.setEdificioGeneral(true);
                        solicitudSubEdificioMgl = sse;
                        if (sse.getSolModCobertura() == 1) {
                            coberturaModList.add(sse);
                        }

                    } else {
                        sse.setEdificioGeneral(false);
                        if (sse.getSolModDatos() == 1 || sse.getSolModEliminacion() == 1) {
                            subEdificiosModList.add(sse);
                        }
                        if (sse.getSolModCobertura() == 1) {
                            coberturaModList.add(sse);
                        }
                    }

                }
                if (!subEdificiosModList.isEmpty()) {

                    solSubEdificioSelecMod = subEdificiosModList.get(0);

                }
            }
            if (cmtSolicitudCmMgl.getDisponibilidadGestion() != null) {
                boolean disponible = cmtSolicitudCmMgl.getDisponibilidadGestion() != null
                        && cmtSolicitudCmMgl.getDisponibilidadGestion().equalsIgnoreCase("1");
                if (disponible) {
                    horarioSolicitudList = horarioRestriccionMglFacadeLocal.findBySolicitud(cmtSolicitudCmMgl);

                    if (horarioSolicitudList != null) {
                        if (horarioSolicitudList.size() > 0) {
                            showHorario = true;
                        }
                    }

                }
            }
            nododelBean = "";
            if(cmtSolicitudCmMgl.getJustificacion() != null){
            cmtSolicitudCmMgl.getFirstNota().setNota(cmtSolicitudCmMgl.getJustificacion());
            cmtNotasSolicitudMgl = cmtSolicitudCmMgl.getFirstNota();
            }else{
                cmtNotasSolicitudMgl = cmtSolicitudCmMgl.getFirstNota();
            }
            
            tiempoCreacionSol = cmtSolicitudCmMgl.getTiempoCreacionSolicitud();

            if (cmtSolicitudCmMgl.getEstadoSolicitudObj().getNombreBasica().equalsIgnoreCase("PENDIENTE")) {
                CmtNotasSolicitudMgl cmtNotasSolicitudMglCon = new CmtNotasSolicitudMgl();
                cmtNotasSolicitudMglCon.setNota("");
                cmtNotasGestionSolicitudMgl = cmtNotasSolicitudMglCon;
            } else {
                cmtNotasGestionSolicitudMgl = cmtSolicitudCmMgl.getLastNota();
            }

            if (cmtSolicitudCmMgl.getNombreArchivo() != null
                    && !cmtSolicitudCmMgl.getNombreArchivo().isEmpty()) {

                if (cmtSolicitudCmMgl.getNombreArchivo() != null
                        && !cmtSolicitudCmMgl.getNombreArchivo().isEmpty()) {

                    CmtArchivosVtMgl archivosVtMgl = archivosVtMglFacadeLocal.
                            findAllBySolId(cmtSolicitudCmMgl);

                    urlArchivoSoporte = " <a href=\"" + archivosVtMgl.getRutaArchivo()
                            + "\"  target=\"blank\">" + cmtSolicitudCmMgl.getNombreArchivo() + "</a>";
                }
            }
            if (cmtSolicitudHhppMglListToChanges != null
                    && !cmtSolicitudHhppMglListToChanges.isEmpty()) {
                ArrayList<CmtSolicitudHhppMgl> listaSolicitudes = new ArrayList<>(cmtSolicitudHhppMglListToChanges);
                String tipoHhppVivienda;
                for (CmtSolicitudHhppMgl cmtSolicitudHhppMgl : listaSolicitudes) {
                    if (cmtSolicitudHhppMgl.getOpcionNivel5() != null) {
                        tipoHhppVivienda = obtenerReglaTipoVivienda(cmtSolicitudHhppMgl.getOpcionNivel5());
                        cmtSolicitudHhppMgl.setTipoHhpp(tipoHhppVivienda);
                    }
                    cmtSolicitudHhppMglListToChanges.add(cmtSolicitudHhppMgl);
                }
            }
            
            controlModDatosCM = false;
            controlModDatosDir = false;
            controlModDatosSub = false;
            controlModDatosCobertura = false;


        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error al momento de iniciar el formulario de estado de solicitudes: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al consultar el estado de solicitud, al cargar la información inicial del formulario: " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Método para validar si el usuario tiene permisos para "Finalizar" la solicitud
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolFinalizar() {
        try {
            return ValidacionUtil.validarVisualizacion("TABORDENCCMMSOLICITUD",
                    "FINALIZAR", cmtOpcionesRolMglFacade, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al verificar si tiene rol de permisos para finalizar la solicitud. " , e);
        }

        return false;
    }

    public String regresarAConsulta() {

        return "/view/MGL/CM/solicitudes/consulta/consultarEstadoSolicitud";

    }

    public String regresarCM() {

        return "/view/MGL/CM/cuentas-matrices";

    }

    /**
     * Funcion utilizada para poder navegar sobre las diferentes opciones de
     * tipos de modificacion de cuenta matriz.
     *
     * @param sSeleccionado
     *
     * @return retorna el nombre de la pagina con el tipo de modificación a la
     * que tiene que ir
     */
    public String cambiarTab(String sSeleccionado) {
        String formTabSeleccionado = "";
        ConstantsCM.TABS_MOD_CM Seleccionado = ConstantsCM.TABS_MOD_CM.valueOf(sSeleccionado);

        switch (Seleccionado) {

            case TRACK:
                formTabSeleccionado = "/view/MGL/CM/solicitudes/consulta/consultaSolicitudTrack";
                selectedTab = "TRACK";
                session.setAttribute("selectedTab", selectedTab);
                break;
            case HORARIO:
                formTabSeleccionado = "/view/MGL/CM/solicitudes/consulta/consultaSolicitudHorarios";
                selectedTab = "HORARIO";
                session.setAttribute("selectedTab", selectedTab);
                break;
            case NOTAS:
                formTabSeleccionado = "/view/MGL/CM/solicitudes/consulta/consultaSolicitudNotas";
                selectedTab = "NOTAS";
                session.setAttribute("selectedTab", selectedTab);
                break;
            case SOLICITUD:
                if (cmtTipoSolicitudMgl.getAbreviatura().equalsIgnoreCase(ConstantsCM.TIPO_SOLICITUD_CREACION_CM)) {
                    formTabSeleccionado = "/view/MGL/CM/solicitudes/consulta/solicitudCreacionCm";
                } else if (cmtTipoSolicitudMgl.getAbreviatura().equalsIgnoreCase(ConstantsCM.TIPO_SOLICITUD_MODIFICACION_CM)) {
                    formTabSeleccionado = "/view/MGL/CM/solicitudes/consulta/solicitudModificacionCm";
                } else if (cmtTipoSolicitudMgl.getAbreviatura().equalsIgnoreCase(ConstantsCM.TIPO_SOLICITUD_VISITA_TECNICA)) {
                    formTabSeleccionado = "/view/MGL/CM/solicitudes/consulta/solicitudCreacionVt";
                } else if (cmtTipoSolicitudMgl.getAbreviatura().equalsIgnoreCase(ConstantsCM.TIPO_SOLICITUD_ELIMINACION_CM)) {
                    formTabSeleccionado = "/view/MGL/CM/solicitudes/consulta/solicitudEliminacionCm";
                } else {
                    formTabSeleccionado = "/view/MGL/CM/solicitudes/consulta/solicitudCreaModHhpp";
                }

                selectedTab = "SOLICITUD";
                session.setAttribute("selectedTab", selectedTab);
                break;
            case DATOSCM:
                controlModDatosCM = true;
                controlModDatosDir = false;
                controlModDatosSub = false;
                controlModDatosCobertura = false;
                break;
            case DIRECCION:
                controlModDatosDir = true;
                controlModDatosCM = false;
                controlModDatosSub = false;
                controlModDatosCobertura = false;
                break;
            case SUBEDIFICIOS:
                controlModDatosSub = true;
                controlModDatosCM = false;
                controlModDatosDir = false;
                controlModDatosCobertura = false;
                break;
            case COBERTURA:
                controlModDatosCobertura = true;
                controlModDatosCM = false;
                controlModDatosDir = false;
                controlModDatosSub = false;
                break;

            default:
                formTabSeleccionado = "/view/MGL/CM/solicitudes/modificacionCm/principalSolicitudModificarCM";
                selectedTab = "DATOSCM";
                break;
        }

        return formTabSeleccionado;
    }

    public String obtenerTipoDireccion(int cod) {
        String tipoDireccion = "";
        if (cod == Constant.ID_TIPO_DIRECCION_PRINCIPAL) {
            tipoDireccion = "PRINCIPAL";
        }
        if (cod == Constant.ID_TIPO_DIRECCION_ALTERNA) {
            tipoDireccion = "ALTERNA";
        }
        if (cod == Constant.ID_TIPO_DIRECCION_SUBEDIFICIO) {
            tipoDireccion = "SUBEDIFICIO";
        }
        return tipoDireccion;
    }

    public CmtSolicitudCmMgl getCmtSolicitudCmMgl() {
        return cmtSolicitudCmMgl;
    }

    public void setCmtSolicitudCmMgl(CmtSolicitudCmMgl cmtSolicitudCmMgl) {
        this.cmtSolicitudCmMgl = cmtSolicitudCmMgl;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public String getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(String selectedTab) {
        this.selectedTab = selectedTab;
    }

    public String getSelectedBarrio() {
        return selectedBarrio;
    }

    public void setSelectedBarrio(String selectedBarrio) {
        this.selectedBarrio = selectedBarrio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public CmtDireccionSolicitudMgl getCmtDireccionSolictudMgl() {
        return cmtDireccionSolictudMgl;
    }

    public void setCmtDireccionSolictudMgl(CmtDireccionSolicitudMgl cmtDireccionSolictudMgl) {
        this.cmtDireccionSolictudMgl = cmtDireccionSolictudMgl;
    }

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    public CmtSolicitudSubEdificioMgl getSolicitudSubEdificioMgl() {
        return solicitudSubEdificioMgl;
    }

    public void setSolicitudSubEdificioMgl(CmtSolicitudSubEdificioMgl solicitudSubEdificioMgl) {
        this.solicitudSubEdificioMgl = solicitudSubEdificioMgl;
    }

    public String getNododelBean() {
        return nododelBean;
    }

    public void setNododelBean(String nododelBean) {
        this.nododelBean = nododelBean;
    }

    public CmtNotasSolicitudMgl getCmtNotasSolicitudMgl() {
        return cmtNotasSolicitudMgl;
    }

    public void setCmtNotasSolicitudMgl(CmtNotasSolicitudMgl cmtNotasSolicitudMgl) {
        this.cmtNotasSolicitudMgl = cmtNotasSolicitudMgl;
    }

    public CmtCuentaMatrizMgl getCuentaMatriz() {
        return cuentaMatriz;
    }

    public void setCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) {
        this.cuentaMatriz = cuentaMatriz;
    }

    public String getTiempoCreacionSol() {
        return tiempoCreacionSol;
    }

    public void setTiempoCreacionSol(String tiempoCreacionSol) {
        this.tiempoCreacionSol = tiempoCreacionSol;
    }

    public boolean isActivoCheckModDatosCM() {
        activoCheckModDatosCM = cmtSolicitudCmMgl != null && (cmtSolicitudCmMgl.getModDatosCm() == 1);
        return activoCheckModDatosCM;
    }

    public void setActivoCheckModDatosCM(boolean activoCheckModDatosCM) {
        if (activoCheckModDatosCM) {
            cmtSolicitudCmMgl.setModDatosCm(new Short("1"));
        } else {
            cmtSolicitudCmMgl.setModDatosCm(new Short("0"));
        }
        this.activoCheckModDatosCM = activoCheckModDatosCM;
    }

    public boolean isActivoCheckModDireccion() {
        activoCheckModDireccion = cmtSolicitudCmMgl != null && (cmtSolicitudCmMgl.getModDireccion() == 1);
        return activoCheckModDireccion;
    }

    public void setActivoCheckModDireccion(boolean activoCheckModDireccion) {
        if (activoCheckModDireccion) {
            cmtSolicitudCmMgl.setModDireccion(new Short("1"));
        } else {
            cmtSolicitudCmMgl.setModDireccion(new Short("0"));
        }
        this.activoCheckModDireccion = activoCheckModDireccion;
    }

    public boolean isActivoCheckModCobertura() {
        activoCheckModCobertura = cmtSolicitudCmMgl != null && (cmtSolicitudCmMgl.getModCobertura() == 1);
        return activoCheckModCobertura;
    }

    public void setActivoCheckModCobertura(boolean activoCheckModCobertura) {
        if (activoCheckModCobertura) {
            cmtSolicitudCmMgl.setModCobertura(new Short("1"));
        } else {
            cmtSolicitudCmMgl.setModCobertura(new Short("0"));
        }
        this.activoCheckModCobertura = activoCheckModCobertura;
    }

    public boolean isActivoCheckModSubedificios() {
        activoCheckModSubedificios = cmtSolicitudCmMgl != null && (cmtSolicitudCmMgl.getModSubedificios() == 1);
        return activoCheckModSubedificios;
    }

    public void setActivoCheckModSubedificios(boolean activoCheckModSubedificios) {
            if (activoCheckModSubedificios) {
                cmtSolicitudCmMgl.setModSubedificios(new Short("1"));
            } else {
                cmtSolicitudCmMgl.setModSubedificios(new Short("0"));
            }
            this.activoCheckModSubedificios = activoCheckModSubedificios;
        }

    public void cambiarTabHhpp(String sSeleccionado) {
        try {

            ConstantsCM.TABS_CM_HHPP Seleccionado = ConstantsCM.TABS_CM_HHPP.valueOf(sSeleccionado);

            if (Seleccionado.equals(CREACION_HHPP)) {
                cmtSolicitudCmMgl.setListCmtSolicitudHhppMgl(new ArrayList<>());
                activoCheckCreacionHHPP = true;
                activoCheckModificacionHHPP = false;
                solicitudCreaHhpp = true;
                solicitudModHhpp = false;
            }
            if (Seleccionado.equals(MODIFICACION_HHPP)) {
                cmtSolicitudCmMgl.setListCmtSolicitudHhppMgl(new ArrayList<>());
                activoCheckCreacionHHPP = false;
                activoCheckModificacionHHPP = true;
                solicitudModHhpp = true;
                solicitudCreaHhpp = false;
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al cambiar Tap de hhpp en CmtEstadoSolicitudBean: cambiarTabHhpp()" + e.getMessage(), e, LOGGER);
        }
    }

    public void cargarHhppCm() {
        try {
            if (cmtSolicitudCmMgl != null
                    && cmtSolicitudCmMgl.getCuentaMatrizObj() != null) {
                subEdificioGeneralCm = cmtSolicitudCmMgl.getCuentaMatrizObj().getSubEdificioGeneral();
                if (subEdificioGeneralCm != null) {
                    pageFirst();
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al momento de cargar el HHPP. EX000: "  + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al cargar HHPP: " + e.getMessage(), e, LOGGER);
        }
    }

    public String goCuentaMatriz() throws ApplicationException {
        try {            
            if (cmtSolicitudCmMgl.getCuentaMatrizObj() != null) {
                
                CuentaMatrizBean cuentaMatrizBean = (CuentaMatrizBean) JSFUtil.getBean("cuentaMatrizBean");
                ConsultaCuentasMatricesBean consultaCuentasMatricesBean = (ConsultaCuentasMatricesBean) JSFUtil.getBean("consultaCuentasMatricesBean");
                cuentaMatrizBean.setCuentaMatriz(cmtSolicitudCmMgl.getCuentaMatrizObj());
                consultaCuentasMatricesBean.obtenerTipoTecnologiaList();
                consultaCuentasMatricesBean.tecnologiasCoberturaCuentaMatriz();

                consultaCuentasMatricesBean.setTecnologiasCables(cuentaMatrizFacadeLocal.getTecnologiasInst(cmtSolicitudCmMgl.getCuentaMatrizObj(), true));
                consultaCuentasMatricesBean.setTecnologiasVarias(cuentaMatrizFacadeLocal.getTecnologiasInst(cmtSolicitudCmMgl.getCuentaMatrizObj(), false));
                cmtSolicitudCmMgl.getCuentaMatrizObj().setCoberturasList(consultaCuentasMatricesBean.getTecnologiasList());
                List<CmtSubEdificioMgl> subEdificiosMgl = cmtSubEdificioMglFacadeLocal.findSubEdificioByCuentaMatriz(cmtSolicitudCmMgl.getCuentaMatrizObj());
                if (subEdificiosMgl.size() > 0) {
                    CmtCuentaMatrizMgl cmtCuentaMatrizMgl = cmtSolicitudCmMgl.getCuentaMatrizObj();
                    cmtCuentaMatrizMgl.setListCmtSubEdificioMgl(subEdificiosMgl);
                    cuentaMatrizBean.setCuentaMatriz(cmtCuentaMatrizMgl);
                } else {
                    CmtCuentaMatrizMgl cmtCuentaMatrizMgl = cmtSolicitudCmMgl.getCuentaMatrizObj();
                    cuentaMatrizBean.setCuentaMatriz(cmtCuentaMatrizMgl);
                }

                FacesUtil.navegarAPagina("/view/MGL/CM/tabs/hhpp.jsf");                
                
            } else {
                String msg = "No existe una Cuenta Matriz creada para esta solicitud";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        msg, ""));
            }
        } catch (ApplicationException e) {
            String msg = "Se genera error al cargar una CM en CmtEstadoSolicitudBean: goCuentaMatriz()";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    e.getCause().getMessage(), msg ));
            LOGGER.error(msg, e);
        }
        return null;
    }

    public String goGestion(CmtSolicitudCmMgl solicitudCmMgl) {
        try {
            if (solicitudCmMgl == null
                    || solicitudCmMgl.getSolicitudCmId() == null
                    || solicitudCmMgl.getTipoSolicitudObj() == null
                    || solicitudCmMgl.getTipoSolicitudObj().getTipoSolicitudId() == null) {
                return null;
            }
            EncabezadoSolicitudModificacionCMBean encabezadoBean =
                    JSFUtil.getSessionBean(EncabezadoSolicitudModificacionCMBean.class);
            CmtSolicitudCmMgl solicitudModCM = cmtSolicitudCmMglFacadeLocal.findById(solicitudCmMgl.getSolicitudCmId());
            boolean disponible = solicitudModCM.getDisponibilidadGestion() != null
                    && solicitudModCM.getDisponibilidadGestion().equalsIgnoreCase("1");

            if (disponible) {
                return encabezadoBean.ingresarGestionSolicitud(solicitudCmMgl);
            } else {
                if (solicitudModCM.getDisponibilidadGestion() == null || solicitudModCM.getDisponibilidadGestion().equalsIgnoreCase("0")) {
                    solicitudModCM.setDisponibilidadGestion("0");
                    solicitudModCM = cmtSolicitudCmMglFacadeLocal.update(solicitudModCM);
                }
                String msnErr = "La solicitud " + solicitudModCM.getSolicitudCmId() + " "
                        + " se encuentra en proceso de Gestion. Por favor actualice la disponibilidad de esta en '1'";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msnErr, ""));
            }
        } catch (ApplicationException e) {
            String msnErr = "Ha ocurrido un error redirigiendo a la Gestion.";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al cargar la gestion cm  CmtEstadoSolicitudBean: goGestion()"  + e.getMessage(), e, LOGGER);
        }
        return "";
    }

    public void cargaDatosAud() {
        try {
            List<CmtModificacionesCcmmAudMgl> auditoriaEdificiosMgl =
                    modCcmmAudMglFacadeLocal.findBySolicitud(cmtSolicitudCmMgl);

            if (auditoriaEdificiosMgl.size() > 0) {

                if (cmtSolicitudCmMgl.getModDatosCm() == 1) {
                    CmtModificacionesCcmmAudMgl cmtModificacionesCcmmAudMgl = auditoriaEdificiosMgl.get(0);

                    subEdificioAudMgl = new CmtSubEdificioMgl();
                    subEdificioAudMgl.setNombreSubedificio(cmtModificacionesCcmmAudMgl.getNombreSubedificioAnt());
                    subEdificioAudMgl.setTipoEdificioObj(cmtModificacionesCcmmAudMgl.getTipoEdificioObjAnt());
                    subEdificioAudMgl.setTelefonoPorteria(cmtModificacionesCcmmAudMgl.getTelefonoPorteriaAnt());
                    subEdificioAudMgl.setTelefonoPorteria2(cmtModificacionesCcmmAudMgl.getTelefonoPorteria2Ant());
                    subEdificioAudMgl.setCompaniaAdministracionObj(cmtModificacionesCcmmAudMgl.getCompaniaAdministracionObjAnt());
                    subEdificioAudMgl.setAdministrador(cmtModificacionesCcmmAudMgl.getAdministradorAnt());
                    subEdificioAudMgl.setCompaniaAscensorObj(cmtModificacionesCcmmAudMgl.getCompaniaAscensorObjAnt());
                } else {
                    for (CmtModificacionesCcmmAudMgl cmtModificacionesCcmmAudMgl : auditoriaEdificiosMgl) {
                        if (cmtModificacionesCcmmAudMgl.getSubEdificioIdObj().getSubEdificioId().
                                compareTo(selectedCmtSubEdificioMgl.getSubEdificioId()) == 0) {
                            subEdificioAudMgl = new CmtSubEdificioMgl();
                            subEdificioAudMgl.setNombreSubedificio(cmtModificacionesCcmmAudMgl.getNombreSubedificioAnt());
                            subEdificioAudMgl.setTelefonoPorteria(cmtModificacionesCcmmAudMgl.getTelefonoPorteriaAnt());
                            subEdificioAudMgl.setCompaniaAdministracionObj(cmtModificacionesCcmmAudMgl.getCompaniaAdministracionObjAnt());
                            subEdificioAudMgl.setAdministrador(cmtModificacionesCcmmAudMgl.getAdministradorAnt());
                            subEdificioAudMgl.setFechaEntregaEdificio(cmtModificacionesCcmmAudMgl.getFechaEntregaEdificioAnt());
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError(CmtEstadoSolicitudBean.class.getName() + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al cargar datos de auditoria  CmtEstadoSolicitudBean: cargaDatosAud()" + e.getMessage(), e, LOGGER);
        }
    }
    
    

    public String listInfoByPage(int page) {
        try {
            FiltroConsultaHhppDto filtro = new FiltroConsultaHhppDto();
            PaginacionDto<HhppMgl> paginacionDto =
                    hhppFacadeLocal.findBySubOrCM(page, filasPag, subEdificioGeneralCm,
                    filtro, Constant.FIND_HHPP_BY.CUENTA_MATRIZ);
            hhppCmList = paginacionDto.getListResultado();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error al consultar el estado de solicitud:" + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al cargar lista de CM en CmtEstadoSolicitudBean: listInfoByPage()" + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Error en la paginacion en CmtEstadoSolicitudBean: pageFirst()"  + ex.getMessage(), ex);
        }
    }

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
            LOGGER.error("Error en la paginacion en CmtEstadoSolicitudBean: pagePrevious()" + ex.getMessage(), ex);
        }
    }

    public void irPagina() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = Integer.parseInt(numPagina);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }

            listInfoByPage(nuevaPageActual);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error al momento de navegar la página. EX000: "  + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en la paginacion al navegar la página: " + e.getMessage(), e, LOGGER);
        }
    }

    public void pageNext() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error en la paginacion en CmtEstadoSolicitudBean: pageNext()" + ex.getMessage(), ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            LOGGER.error("Error en la paginacion en CmtEstadoSolicitudBean: pageLast()" + ex.getMessage(), ex);
        }

    }

    public int getTotalPaginas() {
        try {
            int totalPaginas;
            FiltroConsultaHhppDto filtro = new FiltroConsultaHhppDto();
            PaginacionDto<HhppMgl> paginacionDto =
                    hhppFacadeLocal.findBySubOrCM(0, filasPag, subEdificioGeneralCm,
                    filtro, Constant.FIND_HHPP_BY.CUENTA_MATRIZ_SOLO_CONTAR);
            long pageSol = paginacionDto.getNumPaginas();
            totalPaginas = (int) ((pageSol % filasPag != 0) ? (pageSol / filasPag) + 1 : pageSol / filasPag);

            return totalPaginas;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error al consultar el estado de solicitud:" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en la paginacion en CmtEstadoSolicitudBean: getTotalPaginas()" + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    public CmtSubEdificioMgl getSubGralEli() {
        return subGralEli;
    }

    public void setSubGralEli(CmtSubEdificioMgl subGralEli) {
        this.subGralEli = subGralEli;
    }

    public boolean isControlModDatosCM() {
        return controlModDatosCM;
    }

    public void setControlModDatosCM(boolean controlModDatosCM) {
        this.controlModDatosCM = controlModDatosCM;
    }

    public boolean isControlModDatosDir() {
        return controlModDatosDir;
    }

    public void setControlModDatosDir(boolean controlModDatosDir) {
        this.controlModDatosDir = controlModDatosDir;
    }

    public boolean isControlModDatosSub() {
        return controlModDatosSub;
    }

    public void setControlModDatosSub(boolean controlModDatosSub) {
        this.controlModDatosSub = controlModDatosSub;
    }

    public boolean isControlModDatosCobertura() {
        return controlModDatosCobertura;
    }

    public void setControlModDatosCobertura(boolean controlModDatosCobertura) {
        this.controlModDatosCobertura = controlModDatosCobertura;
    }

    public List<CmtDireccionSolicitudMgl> getDireccionSolModList() {
        return direccionSolModList;
    }

    public void setDireccionSolModList(List<CmtDireccionSolicitudMgl> direccionSolModList) {
        this.direccionSolModList = direccionSolModList;
    }

    public List<CmtSolicitudSubEdificioMgl> getCoberturaModList() {
        return coberturaModList;
    }

    public void setCoberturaModList(List<CmtSolicitudSubEdificioMgl> coberturaModList) {
        this.coberturaModList = coberturaModList;
    }

    public List<CmtSolicitudSubEdificioMgl> getSolicitudesSubEdificiosList() {
        return solicitudesSubEdificiosList;
    }

    public void setSolicitudesSubEdificiosList(List<CmtSolicitudSubEdificioMgl> solicitudesSubEdificiosList) {
        this.solicitudesSubEdificiosList = solicitudesSubEdificiosList;
    }

    public boolean isModifica() {
        return modifica;
    }

    public void setModifica(boolean modifica) {
        this.modifica = modifica;
    }

    public List<CmtHorarioRestriccionMgl> getHorarioSolicitudList() {
        return horarioSolicitudList;
    }

    public void setHorarioSolicitudList(List<CmtHorarioRestriccionMgl> horarioSolicitudList) {
        this.horarioSolicitudList = horarioSolicitudList;
    }

    public boolean isShowHorario() {
        return showHorario;
    }

    public void setShowHorario(boolean showHorario) {
        this.showHorario = showHorario;
    }

    public CmtBasicaMgl getTipoAcondicionamiento() {
        return tipoAcondicionamiento;
    }

    public void setTipoAcondicionamiento(CmtBasicaMgl tipoAcondicionamiento) {
        this.tipoAcondicionamiento = tipoAcondicionamiento;
    }

    public CmtBasicaMgl getTipoSegmento() {
        return tipoSegmento;
    }

    public void setTipoSegmento(CmtBasicaMgl tipoSegmento) {
        this.tipoSegmento = tipoSegmento;
    }

    public CmtBasicaMgl getTecnologiaSelect() {
        return tecnologiaSelect;
    }

    public void setTecnologiaSelect(CmtBasicaMgl tecnologiaSelect) {
        this.tecnologiaSelect = tecnologiaSelect;
    }

    public List<CmtSubEdificioMgl> getListCmtSubEdificioMgl() {
        return listCmtSubEdificioMgl;
    }

    public void setListCmtSubEdificioMgl(List<CmtSubEdificioMgl> listCmtSubEdificioMgl) {
        this.listCmtSubEdificioMgl = listCmtSubEdificioMgl;
    }

    public CmtSubEdificioMgl getSelectedCmtSubEdificioMgl() {
        return selectedCmtSubEdificioMgl;
    }

    public void setSelectedCmtSubEdificioMgl(CmtSubEdificioMgl selectedCmtSubEdificioMgl) {
        this.selectedCmtSubEdificioMgl = selectedCmtSubEdificioMgl;
    }

    public boolean isActivoCheckCreacionHHPP() {
        return activoCheckCreacionHHPP;
    }

    public void setActivoCheckCreacionHHPP(boolean activoCheckCreacionHHPP) {
        this.activoCheckCreacionHHPP = activoCheckCreacionHHPP;
    }

    public boolean isActivoCheckModificacionHHPP() {
        return activoCheckModificacionHHPP;
    }

    public void setActivoCheckModificacionHHPP(boolean activoCheckModificacionHHPP) {
        this.activoCheckModificacionHHPP = activoCheckModificacionHHPP;
    }

    public List<CmtSolicitudHhppMgl> getCmtSolicitudHhppMglListToChanges() {
        return cmtSolicitudHhppMglListToChanges;
    }

    public void setCmtSolicitudHhppMglListToChanges(List<CmtSolicitudHhppMgl> cmtSolicitudHhppMglListToChanges) {
        this.cmtSolicitudHhppMglListToChanges = cmtSolicitudHhppMglListToChanges;
    }

    public boolean isSolicitudCreaHhpp() {
        return solicitudCreaHhpp;
    }

    public void setSolicitudCreaHhpp(boolean solicitudCreaHhpp) {
        this.solicitudCreaHhpp = solicitudCreaHhpp;
    }

    public boolean isSolicitudModHhpp() {
        return solicitudModHhpp;
    }

    public void setSolicitudModHhpp(boolean solicitudModHhpp) {
        this.solicitudModHhpp = solicitudModHhpp;
    }

    public List<HhppMgl> getListaHhppToChanges() {
        return listaHhppToChanges;
    }

    public void setListaHhppToChanges(List<HhppMgl> listaHhppToChanges) {
        this.listaHhppToChanges = listaHhppToChanges;
    }

    public List<CmtSolicitudSubEdificioMgl> getSubEdificiosModList() {
        return subEdificiosModList;
    }

    public void setSubEdificiosModList(List<CmtSolicitudSubEdificioMgl> subEdificiosModList) {
        this.subEdificiosModList = subEdificiosModList;
    }

    public CmtSolicitudSubEdificioMgl getSolSubEdificioSelecMod() {
        return solSubEdificioSelecMod;
    }

    public void setSolSubEdificioSelecMod(CmtSolicitudSubEdificioMgl solSubEdificioSelecMod) {
        this.solSubEdificioSelecMod = solSubEdificioSelecMod;
    }

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    public List<HhppMgl> getHhppCmList() {
        return hhppCmList;
    }

    public void setHhppCmList(List<HhppMgl> hhppCmList) {
        this.hhppCmList = hhppCmList;
    }

    public CmtSubEdificioMgl getSubEdificioGeneralCm() {
        return subEdificioGeneralCm;
    }

    public void setSubEdificioGeneralCm(CmtSubEdificioMgl subEdificioGeneralCm) {
        this.subEdificioGeneralCm = subEdificioGeneralCm;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    public String getPageActual() {
        return pageActual;
    }

    public void setPageActual(String pageActual) {
        this.pageActual = pageActual;
    }

   public UsuariosServicesDTO getUsuarioSolicitud() {
        return usuarioSolicitud;
    }

    public void setUsuarioSolicitud(UsuariosServicesDTO usuarioSolicitud) {
        this.usuarioSolicitud = usuarioSolicitud;
    }

    public CmtSolicitudSubEdificioMgl getSolSubEdificioCreaCM() {
        return solSubEdificioCreaCM;
    }

    public void setSolSubEdificioCreaCM(CmtSolicitudSubEdificioMgl solSubEdificioCreaCM) {
        this.solSubEdificioCreaCM = solSubEdificioCreaCM;
    }

    public CmtTipoSolicitudMgl getCmtTipoSolicitudMgl() {
        return cmtTipoSolicitudMgl;
    }

    public void setCmtTipoSolicitudMgl(CmtTipoSolicitudMgl cmtTipoSolicitudMgl) {
        this.cmtTipoSolicitudMgl = cmtTipoSolicitudMgl;
    }

    public AddressService getAddressService() {
        return addressService;
    }

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public List<CmtUnidadesPreviasMgl> getUnidadesDeLadireccion() {
        return unidadesDeLadireccion;
    }

    public void setUnidadesDeLadireccion(List<CmtUnidadesPreviasMgl> unidadesDeLadireccion) {
        this.unidadesDeLadireccion = unidadesDeLadireccion;
    }

    public UsuariosServicesDTO getUsuarioGestSolicitudCm() {
        return usuarioGestSolicitudCm;
    }

    public void setUsuarioGestSolicitudCm(UsuariosServicesDTO usuarioGestSolicitudCm) {
        this.usuarioGestSolicitudCm = usuarioGestSolicitudCm;
    }

    public List<CmtNodosSolicitudMgl> getListTablaNodosSolicitud() {
        return listTablaNodosSolicitud;
    }

    public void setListTablaNodosSolicitud(List<CmtNodosSolicitudMgl> listTablaNodosSolicitud) {
        this.listTablaNodosSolicitud = listTablaNodosSolicitud;
    }

    public CmtNotasSolicitudMgl getCmtNotasGestionSolicitudMgl() {
        return cmtNotasGestionSolicitudMgl;
    }

    public void setCmtNotasGestionSolicitudMgl(CmtNotasSolicitudMgl cmtNotasGestionSolicitudMgl) {
        this.cmtNotasGestionSolicitudMgl = cmtNotasGestionSolicitudMgl;
    }

    public String getUrlArchivoSoporte() {
        return urlArchivoSoporte;
    }

    public void setUrlArchivoSoporte(String urlArchivoSoporte) {
        this.urlArchivoSoporte = urlArchivoSoporte;
    }

    public CmtSubEdificioMgl getSubEdificioAudMgl() {
        return subEdificioAudMgl;
    }

    public void setSubEdificioAudMgl(CmtSubEdificioMgl subEdificioAudMgl) {
        this.subEdificioAudMgl = subEdificioAudMgl;
    }

    public boolean isVisibleDatosSolicitud() {
        return visibleDatosSolicitud;
    }

    public void setVisibleDatosSolicitud(boolean visibleDatosSolicitud) {
        this.visibleDatosSolicitud = visibleDatosSolicitud;
    }

    public boolean isVisibleDatosDireccion() {
        return visibleDatosDireccion;
    }

    public void setVisibleDatosDireccion(boolean visibleDatosDireccion) {
        this.visibleDatosDireccion = visibleDatosDireccion;
    }

    public String cambiarVisibleDatosSolicitud() {
        visibleDatosSolicitud = !visibleDatosSolicitud;
        return null;
    }

    public String cambiarVisibleDatosDireccion() {
        visibleDatosDireccion = !visibleDatosDireccion;
        return null;
    }

    public String consultarAudDirMod() {

        String dirMod = "";
        try {
            List<CmtModificacionesCcmmAudMgl> dirCcmmAudMgls =
                    modCcmmAudMglFacadeLocal.findBySolicitud(cmtSolicitudCmMgl);
            if (dirCcmmAudMgls.size() > 0) {
                CmtModificacionesCcmmAudMgl modificacionesCcmmAudMgl = dirCcmmAudMgls.get(0);
                dirMod = modificacionesCcmmAudMgl.getDireccionCcmmAnt();
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error al consultar el estado de solicitud:" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al consultar el estado de solicitud:" + e.getMessage(), e, LOGGER);
        }

        return dirMod;
    }

    public String consultarAudBarMod() {

        String barMod = "";
        try {
            List<CmtModificacionesCcmmAudMgl> dirCcmmAudMgls =
                    modCcmmAudMglFacadeLocal.findBySolicitud(cmtSolicitudCmMgl);
            if (dirCcmmAudMgls.size() > 0) {
                CmtModificacionesCcmmAudMgl modificacionesCcmmAudMgl = dirCcmmAudMgls.get(0);
                barMod = modificacionesCcmmAudMgl.getBarrioCcmmAnt();
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error al consultar el estado de solicitud:" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al consultar el estado de solicitud:" + e.getMessage(), e, LOGGER);
        }

        return barMod;
    }

    public String consultarAudHHppMod(HhppMgl hhppMgl) {

        String apartamento = "";
        try {
            List<CmtModificacionesCcmmAudMgl> hhCcmmAudMgls =
                    modCcmmAudMglFacadeLocal.findBySolicitud(cmtSolicitudCmMgl);
            if (hhCcmmAudMgls.size() > 0) {
                for (CmtModificacionesCcmmAudMgl modificacionesCcmmAudMgl : hhCcmmAudMgls) {
                    if (hhppMgl.getHhpId().compareTo(modificacionesCcmmAudMgl.getHhpIdObj().getHhpId()) == 0) {
                        apartamento = modificacionesCcmmAudMgl.getHhpApartAnt();
                    }
                }

            }

        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en la consulta de la auditoría: "+ ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en la consulta de la auditoría: "+ e.getMessage(), e, LOGGER);
        }
        return apartamento;
    }
    
    public String seleccionarSubEdificioMod(CmtSolicitudSubEdificioMgl se){
        try {
            if (cmtSolicitudCmMgl.getEstadoSolicitudObj().getNombreBasica().equalsIgnoreCase("FINALIZADO")) {
                List<CmtModificacionesCcmmAudMgl> auditoriaEdificiosMgl
                        = modCcmmAudMglFacadeLocal.findBySolicitud(cmtSolicitudCmMgl);
                for (CmtModificacionesCcmmAudMgl cmtModificacionesCcmmAudMgl : auditoriaEdificiosMgl) {
                    if (cmtModificacionesCcmmAudMgl.getSubEdificioIdObj().getSubEdificioId().
                            compareTo(se.getSubEdificioObj().getSubEdificioId()) == 0) {
                        subEdificioAudMgl = new CmtSubEdificioMgl();
                        subEdificioAudMgl.setNombreSubedificio(cmtModificacionesCcmmAudMgl.getNombreSubedificioAnt());
                        subEdificioAudMgl.setTelefonoPorteria(cmtModificacionesCcmmAudMgl.getTelefonoPorteriaAnt());
                        subEdificioAudMgl.setCompaniaAdministracionObj(cmtModificacionesCcmmAudMgl.getCompaniaAdministracionObjAnt());
                        subEdificioAudMgl.setAdministrador(cmtModificacionesCcmmAudMgl.getAdministradorAnt());
                        subEdificioAudMgl.setFechaEntregaEdificio(cmtModificacionesCcmmAudMgl.getFechaEntregaEdificioAnt());
                        solSubEdificioSelecMod = se;
                    }
                }
            } else {
                solSubEdificioSelecMod.setSeleccionado(false);
                solSubEdificioSelecMod = se;
                solSubEdificioSelecMod.setSeleccionado(true);
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en la seleccion del subedificio a modificar : seleccionarSubEdificioMod()" + e.getMessage(), e, LOGGER);
        }
        return null;
    }
    
       public String obtenerReglaTipoVivienda(String apartamento) {
        DireccionRRManager DireccionRRManager = new DireccionRRManager(true);
        tipoHHppSelected = DireccionRRManager.obtenerTipoEdificio(apartamento, usuarioVt, null);
        return tipoHHppSelected;
    }
    

    public List<TipoHhppMgl> getListaTipoHhpp() {
        return listaTipoHhpp;
    }

    public void setListaTipoHhpp(List<TipoHhppMgl> listaTipoHhpp) {
        this.listaTipoHhpp = listaTipoHhpp;
    }

    public String getTipoHHppSelected() {
        return tipoHHppSelected;
    }

    public void setTipoHHppSelected(String tipoHHppSelected) {
        this.tipoHHppSelected = tipoHHppSelected;
    }
    
    
}
