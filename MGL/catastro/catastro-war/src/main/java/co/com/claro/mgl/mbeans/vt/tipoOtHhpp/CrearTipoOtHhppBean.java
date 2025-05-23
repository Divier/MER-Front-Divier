/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.tipoOtHhpp;

import co.com.claro.mgl.businessmanager.cm.CmtOpcionesRolMglManager;
import co.com.claro.mgl.dtos.GestionRolesOtDireccionesDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.RazonesOtDireccionMglFacadeLocal;
import co.com.claro.mgl.facade.TipoOtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.MglRazonesOtDireccion;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.services.comun.Utilidades;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Juan David Hernandez
 */
@ManagedBean(name = "crearTipoOtHhppBean")
@ViewScoped
public class CrearTipoOtHhppBean implements Serializable {

    private TipoOtHhppMgl tipoOtHhppMgl;
    private List<CmtBasicaMgl> estadoGeneralList;
    private List<CmtBasicaMgl> tipoTrabajoList;
    private List<CmtBasicaMgl> subTipoTrabajoList;
    private List<CmtBasicaMgl> estadoInicialInternoList;
    private List<CmtBasicaMgl> estadoFinalInternoList;
    private List<CmtBasicaMgl> estadoCreacionInternoList;
    private List<GestionRolesOtDireccionesDto> gestionRolesOtDireccionesList
            = new ArrayList<>();
    private List<String> rolesList;
    private boolean agendable;
    private boolean manejaTecnologias;
    private boolean ampliacionDeTab;
    private boolean showEstadoFinal;
    private boolean showEstadoCreacion;
    private boolean manejaVisitaDomiciliaria;
    private boolean showTipoOtParaAmpliacionDeTab = false;
    private boolean ampliacionDeTabIsChecked = false;
    private boolean showRoles = false;
    private BigDecimal estadoInicial;
    private BigDecimal tipoTrabajoSeleccionado;
    private BigDecimal subTipoTrabajoSeleccionado;
    private BigDecimal razonSeleccionada;
    private String estadoFinal;
    private String estadoCreacion;
    private String nombreTipoOt;
    private BigDecimal estadoCreacionInterno;
    private BigDecimal tipoOtDeAmpliacionDeTabSeleccionada;
    private String rolSeleccionado = "";
    private String ans;
    private String ansAviso;
    private String serverHost;
    private String usuarioVT = null;
    private String cedulaUsuarioVT = null;
    private int perfilVt = 0;
    List<TipoOtHhppMgl> tipoOtList;
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private static final Logger LOGGER = LogManager.getLogger(CrearTipoOtHhppBean.class);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private boolean requiereOnyx;
    private String isMultiagenda;
    private String isTipoVerificacion="0";
    private List<CmtBasicaMgl> listadoTipodeTrabajo;
    private List<CmtBasicaMgl> listadoEstadoCierre;
    private List<CmtBasicaMgl> listadoEstadoInicial;
    private BigDecimal tipoTrabajoRRSelect;
    private BigDecimal estadoResultadoInicialRRSelect;
    private BigDecimal estadoResultadoCierreRRSelect;
    private SecurityLogin securityLogin;
    
    @EJB
    private RazonesOtDireccionMglFacadeLocal razonesOtDireccionesMglFacadeLocal;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private TipoOtHhppMglFacadeLocal tipoOtHhppMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;

    public CrearTipoOtHhppBean() {
        try {
            serverHost
                    = Utilidades.queryParametrosConfig(co.com.telmex.catastro.services.util.Constant.HOST_REDIR_VT);
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
            String msn = "Error al arrancar la pantalla de crear tipo ot.";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al arrancar la pantalla de crear tipo ot.";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    @PostConstruct
    private void init() {
        try {
            razonesOtDireccionesMglFacadeLocal.setUser(usuarioVT, perfilVt);
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
            tipoOtHhppMglFacadeLocal.setUser(usuarioVT, perfilVt);
            obtenerEstadoGeneralList();
            obtenerTipoTrabajoList();
            obtenerSubTipoTrabajoList();
            obtenerRoles();
            obtenerListasOtRr();
        } catch (ApplicationException e) {
            String msn = "Error al realizar init de PostConstruct";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al realizar init de PostConstruct";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Obtiene listado de estados generales iniciales
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
            String msn = "Error al cargar listado de estados inicial interno";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al cargar listado de estados inicial interno";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Funcion para consultar todos los roles existentes
     *
     * @author Orlando Velasquez Diaz
     */
    public void obtenerRoles() {
        try {
            CmtOpcionesRolMglManager cmtOpcionesRolMglManager = new CmtOpcionesRolMglManager();
            rolesList = cmtOpcionesRolMglManager.consultarRoles();
        } catch (ApplicationException e) {
            String msn = "Error al consultar Roles";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al consultar Roles";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Funcion usada para guardar la razon en la base de datos y mostrarla en la
     * tabla
     *
     * @author Orlando Velasquez Diaz
     */
    public void agregarRazon() {
        try {
            if (validarCamposAgregarRazon()) {
                CmtBasicaMgl estadoGeneral = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(estadoCreacion);
                CmtBasicaMgl razon = cmtBasicaMglFacadeLocal.findById(razonSeleccionada);
                GestionRolesOtDireccionesDto gestionRolesOtDireccionesDto
                        = new GestionRolesOtDireccionesDto();
                gestionRolesOtDireccionesDto.setEstadoGeneral(estadoGeneral);
                gestionRolesOtDireccionesDto.setRazon(razon);
                if (estadoCreacion.equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO_COMPLETO)) {
                    gestionRolesOtDireccionesDto.setRol(rolSeleccionado);
                } else {
                        gestionRolesOtDireccionesDto.setRol(null);
                }

                if (validarRazonesRepetidas(gestionRolesOtDireccionesDto)) {
                    gestionRolesOtDireccionesList.add(gestionRolesOtDireccionesDto);
                } else {
                    LOGGER.error("La razon ya existe para este tipo de Ot");
                    String msn = "La razon ya existe para este tipo de Ot";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msn, ""));
                }

            }

        } catch (ApplicationException e) {
            String msn = "Error al agregar razon";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al agregar razon";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Validar campos al momento de agregar una razon
     *
     * @author Orlando Velasquez Diaz
     * @return
     */
    public boolean validarCamposAgregarRazon() {

        if (estadoCreacion == null) {
            String msn = "Seleccione un estado por favor.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            return false;
        } else if (razonSeleccionada == null) {
            String msn = "Seleccione una razon por favor.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            return false;
        } else if (estadoCreacion.equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO_COMPLETO)) {
            if (rolSeleccionado == null) {
                String msn = "Seleccione un rol por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;
            }
        }
        return true;
    }

    /**
     * Funcion para desactivar la razon Ot Direcciones en la base de datos y
     * eliminarla de la tabla
     *
     * @author Orlando Velasquez Diaz
     * @param razonOtDireccion
     */
    public void eliminarRazonOtDirecciones(GestionRolesOtDireccionesDto razonOtDireccion) {
        gestionRolesOtDireccionesList.remove(razonOtDireccion);
    }

    /**
     * Obtiene listado de tipos de trabajo
     *
     * @author Orlando Velasquez Diaz
     */
    public void obtenerTipoTrabajoList() {
        try {
            CmtTipoBasicaMgl tipoBasica;
            tipoBasica = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_GENERAL_OT);
            tipoTrabajoList
                    = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasica);
        } catch (ApplicationException e) {
            String msn = "Error al cargar listado de estados inicial interno";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al cargar listado de estados inicial interno";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Obtiene listado de sub tipos de trabajo
     *
     * @author Orlando Velasquez Diaz
     */
    public void obtenerSubTipoTrabajoList() {
        try {
            CmtTipoBasicaMgl tipoBasica;
            tipoBasica = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_SUB_TIPO_TRABAJO_DIRECCIONES);
            subTipoTrabajoList
                    = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasica);
        } catch (ApplicationException e) {
            String msn = "Error al cargar listado de estados inicial interno";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al cargar listado de estados inicial interno";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Obtiene listado de razones finales
     *
     * @author Juan David Hernandez
     */
    public void obtenerEstadoFinalInternoList() {
        try {
            if (estadoGeneralList != null && !estadoGeneralList.isEmpty()) {
                if (estadoFinal != null && !estadoFinal.trim().isEmpty()) {

                    CmtTipoBasicaMgl tipoBasica;
                    tipoBasica = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                            estadoInternoACargar(estadoFinal));

                    estadoFinalInternoList = cmtBasicaMglFacadeLocal
                            .findByTipoBasica(tipoBasica);
                    showEstadoFinal = true;
                } else {
                    estadoFinalInternoList = new ArrayList();
                    showEstadoFinal = false;
                }
            }
        } catch (ApplicationException e) {
            String msn = "Error al cargar listado de razones finales";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al cargar listado de razones finales";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Obtiene listado de razones de creación
     *
     * @author Juan David Hernandez
     */
    public void obtenerEstadoCreacionInternoList() {
        try {
            if (estadoGeneralList != null && !estadoGeneralList.isEmpty()) {
                if (estadoCreacion != null && !estadoCreacion.trim().isEmpty()) {

                    CmtTipoBasicaMgl tipoBasica;
                    tipoBasica = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                            estadoInternoACargar(estadoCreacion));

                    estadoCreacionInternoList = cmtBasicaMglFacadeLocal
                            .findByTipoBasica(tipoBasica);
                    showEstadoCreacion = true;
                    if (!estadoCreacion.equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO_COMPLETO)) {
                        showRoles = false;
                    } else {
                        showRoles = true;
                    }
                } else {
                    estadoCreacionInternoList = new ArrayList();
                    showEstadoCreacion = false;
                }
            }
        } catch (ApplicationException e) {
            String msn = "Error al cargar listado de razones de creación";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al cargar listado de razones de creación";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Obtiene listado de tipos de Ot para poder asociarla como ampliacion de
     * Tab
     *
     * @author Orlando Velasquez Diaz
     */
    public void obtenerListadoDeTipoOt() {
        try {
            tipoOtList = tipoOtHhppMglFacadeLocal.findAll();

        } catch (ApplicationException e) {
            String msn = "Error al cargar listado de tipos de Ot";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al cargar listado de tipos de Ot";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
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
        } catch (Exception e) {
            String msn = "Error al validar el estado interno a cargar";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
            return "--";
        }
    }

    /**
     * Función utilizada para crear un tipo de ot en la base de datos
     *
     * @author Juan David Hernandez
     */
    public void crearTipoOtHhpp() {
        try {
            if (validacionDatosTipoOtHhpp()) {
                tipoOtHhppMgl = new TipoOtHhppMgl();
                tipoOtHhppMgl.setNombreTipoOt(nombreTipoOt.toUpperCase());
                BigDecimal valorAgendable = obtenerValorCheckbox(agendable);
                BigDecimal valorAmpliacion = obtenerValorCheckbox(ampliacionDeTab);
                BigDecimal valorManejaTecnologias = obtenerValorCheckbox(manejaTecnologias);
                BigDecimal valorManejaVisitaDomiciliaria = obtenerValorCheckbox(manejaVisitaDomiciliaria);
                tipoOtHhppMgl.setAgendable(valorAgendable);
                tipoOtHhppMgl.setAmpliacionTab(valorAmpliacion);
                tipoOtHhppMgl.setManejaTecnologias(valorManejaTecnologias);
                tipoOtHhppMgl.setManejaVisitaDomiciliaria(valorManejaVisitaDomiciliaria);
                tipoOtHhppMgl.setAns(Integer.parseInt(ans));
                tipoOtHhppMgl.setAnsAviso(Integer.parseInt(ansAviso));
                
                tipoOtHhppMgl.setDiasAMostrarAgenda(Constant.DIAS_A_MOSTRAR_AGENDA);

                CmtBasicaMgl tipoTrabajo = cmtBasicaMglFacadeLocal.findById(tipoTrabajoSeleccionado);
                CmtBasicaMgl subTipoTrabajo = cmtBasicaMglFacadeLocal.findById(subTipoTrabajoSeleccionado);
                tipoOtHhppMgl.setTipoTrabajoOFSC(tipoTrabajo);
                tipoOtHhppMgl.setSubTipoOrdenOFSC(subTipoTrabajo);

                if (tipoOtDeAmpliacionDeTabSeleccionada != null) {
                    TipoOtHhppMgl tipoOtSeleccionada = tipoOtHhppMglFacadeLocal.findTipoOtHhppById(tipoOtDeAmpliacionDeTabSeleccionada);
                    tipoOtHhppMgl.setTipoOtAmpliacionTab(tipoOtSeleccionada.getTipoOtId());
                }
                
               tipoOtHhppMgl.setRequiereOnyx(requiereOnyx == true ? "Y" : "N");
               tipoOtHhppMgl.setIsMultiagenda(isMultiagenda);
               tipoOtHhppMgl.setEsTipoVisitaTecnica(isTipoVerificacion);
               
                if (isMultiagenda.equalsIgnoreCase("S")) {
                    CmtBasicaMgl tipoTrabajoRR = new CmtBasicaMgl();
                    tipoTrabajoRR.setBasicaId(tipoTrabajoRRSelect);
                    tipoOtHhppMgl.setTipoTrabajoRR(tipoTrabajoRR);

                    CmtBasicaMgl estadoResultadoIniRR = new CmtBasicaMgl();
                    estadoResultadoIniRR.setBasicaId(estadoResultadoInicialRRSelect);
                    tipoOtHhppMgl.setEstadoOtRRInicial(estadoResultadoIniRR);

                    CmtBasicaMgl estadoResultadoCieRR = new CmtBasicaMgl();
                    estadoResultadoCieRR.setBasicaId(estadoResultadoCierreRRSelect);
                    tipoOtHhppMgl.setEstadoOtRRFinal(estadoResultadoCieRR);
                } else {
                    tipoOtHhppMgl.setTipoTrabajoRR(null);
                    tipoOtHhppMgl.setEstadoOtRRInicial(null);
                    tipoOtHhppMgl.setEstadoOtRRFinal(null);
                }
               
                tipoOtHhppMgl = tipoOtHhppMglFacadeLocal
                        .crearTipoOtHhpp(tipoOtHhppMgl);
                if (tipoOtHhppMgl.getTipoOtId() != null) {

                    for (GestionRolesOtDireccionesDto gestionRolesOtDireccionesDto : gestionRolesOtDireccionesList) {
                        MglRazonesOtDireccion razonesOtDireccion = new MglRazonesOtDireccion();
                        razonesOtDireccion.setEstadoGeneral(gestionRolesOtDireccionesDto.getEstadoGeneral());
                        razonesOtDireccion.setRazon(gestionRolesOtDireccionesDto.getRazon());
                        razonesOtDireccion.setRol(gestionRolesOtDireccionesDto.getRol());
                        razonesOtDireccion.setTipoOtId(tipoOtHhppMgl);
                        razonesOtDireccionesMglFacadeLocal.create(razonesOtDireccion);
                    }

                    String msn = "Tipo de Ot Hhpp creada satisfactoriamente";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    msn, ""));
                    limpiarTipoOtHhpp();
                } else {
                    String msn = "No se ha creado correctamente el tipo de ot";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msn, ""));
                }
            }
        } catch (ApplicationException | NumberFormatException e) {
            String msn = "Error al cargar intentar crear un tipo de OT";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al cargar intentar crear un tipo de OT";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función utilizada para obtener el valor del si la tipo de ot es agendable
     *
     * @author Juan David Hernandez
     * @param agendableSeleccionado
     * @return 
     */
    public BigDecimal obtenerValorCheckbox(boolean agendableSeleccionado) {
        if (agendableSeleccionado) {
            return new BigDecimal(1);
        } else {
            return new BigDecimal(0);
        }
    }

    /**
     * Función utilizada para validar que no exista en la tabla razones iguales
     *
     * @author Orlando Velasquez
     * @param razonOtdireccion
     * @return
     */
    public boolean validarRazonesRepetidas(GestionRolesOtDireccionesDto razonOtdireccion) {

        for (GestionRolesOtDireccionesDto razonesEnLaTabla : gestionRolesOtDireccionesList) {

            if (razonesEnLaTabla.getEstadoGeneral().getBasicaId()
                    .compareTo(razonOtdireccion.getEstadoGeneral().getBasicaId()) == 0) {

                if (razonesEnLaTabla.getRazon().getBasicaId()
                        .compareTo(razonOtdireccion.getRazon().getBasicaId()) == 0) {

                    if ((razonesEnLaTabla.getRol() == null
                            && razonOtdireccion.getRol() == null)) {
                        return false;
                    } else if ((razonesEnLaTabla.getRol() != null
                            || razonOtdireccion.getRol() != null)) {

                        if (razonesEnLaTabla.getRol() != null) {
                            if ((razonesEnLaTabla.getRol().equalsIgnoreCase(razonOtdireccion.getRol()))) {
                                return false;
                            }
                        } else if (razonOtdireccion.getRol() != null) {
                            if ((razonOtdireccion.getRol().equalsIgnoreCase(razonesEnLaTabla.getRol()))) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;

    }

    /**
     * Función utilizada para redireccionar al listado de tipo de OT
     *
     * @author Juan David Hernandez
     */
    public void volverListadoTipoOt() {
        try {
            
             FacesUtil.navegarAPagina("/view/MGL/VT/tipoOtHhpp/"
                            + "tipoOtHhppList.jsf");
        } catch (ApplicationException e) {
            String msn = "Error al cargar intentar crear un tipo de OT";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al cargar intentar crear un tipo de OT";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función utilizada para validar los datos requeridos para la creación de
     * una tipo Ot
     *
     * @author Juan David Hernandez
     * @return 
     */
    public boolean validacionDatosTipoOtHhpp() {
        try {
            if (nombreTipoOt == null
                    || nombreTipoOt.isEmpty()) {
                String msn = "Debe ingresar un nombre de tipo de ot por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msn, ""));
                return false;
            } else {
                if (!validarDuplicidadNombreTipoOt(nombreTipoOt)) {
                    String msn = "El nombre ingresado a la tipo de ot"
                            + " ya se encuentra registrado, ingrese uno "
                            + "diferente por favor.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msn, ""));
                    return false;
                } else {
                    if (ans == null || ans.trim().isEmpty()) {
                        String msn = "Debe ingresar un valor ANS por favor.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msn, ""));
                        return false;

                    } else {
                        if (ansAviso == null || ansAviso.trim().isEmpty()) {
                            String msn = "Debe ingresar un "
                                    + "valor ANS AVISO por favor.";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msn, ""));
                            return false;
                        } else {
                            if (!isNumeric(ans)) {
                                String msn = "El valor de ANS debe ser númerico por favor.";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                msn, ""));
                                return false;
                            } else {
                                if (!isNumeric(ansAviso)) {
                                    String msn = "El valor de ANS AVISO debe ser númerico por favor.";
                                    FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                    msn, ""));
                                    return false;
                                } else {
                                    if (tipoTrabajoSeleccionado == null) {
                                        String msn = "Debe seleccionar un tipo de trabajo";
                                        FacesContext.getCurrentInstance().addMessage(null,
                                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                        msn, ""));
                                        return false;
                                    } else {
                                        if (Integer.parseInt(ansAviso) > Integer.parseInt(ans)) {
                                            String msn = "EL valor de ANS "
                                                    + "debe ser mayor al de "
                                                    + "ANS AVISO por favor.";
                                            FacesContext.getCurrentInstance().addMessage(null,
                                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                            msn, ""));
                                            return false;
                                        } else {
                                            if (subTipoTrabajoSeleccionado == null) {
                                                String msn = "Debe seleccionar un subTipo de trabajo";
                                                FacesContext.getCurrentInstance().addMessage(null,
                                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                                msn, ""));
                                                return false;
                                            } else if (ampliacionDeTab) {
                                                if (tipoOtDeAmpliacionDeTabSeleccionada == null) {
                                                    String msn = "Debe seleccionar una Ot para "
                                                            + "asociar en la ampliacion de Tab.";
                                                    FacesContext.getCurrentInstance().addMessage(null,
                                                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                                    msn, ""));
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
                if (isMultiagenda != null) {
                    if (isMultiagenda.equalsIgnoreCase("S")) {
                    if (tipoTrabajoRRSelect != null) {
                        if (estadoResultadoInicialRRSelect != null) {
                            if (estadoResultadoCierreRRSelect != null) {
                                LOGGER.info("campos de ot en rr listos");
                            } else {
                                String msn = "Debe seleccionar un Estado Final de la Ot en RR ";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                msn, ""));
                                return false;                                
                            }
                        } else {
                            String msn = "Debe seleccionar un Estado Inicial de la Ot en RR ";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msn, ""));
                            return false;                            
                        }
                    } else {
                        String msn = "Debe seleccionar un Tipo de Ot RR ";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msn, ""));
                        return false;
                    }
                    }
                }else {
                    String msn = "Debe seleccionar si es Multiagenda o no";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msn, ""));
                    return false;
                }
            }
            if(isTipoVerificacion.equals("0")){
                if(ampliacionDeTab){
                    String msn = "Un tipo de OT que no es de Verificacion ".
                            concat("no puede manejar ampliacion de Taps ".
                            concat(", no marque la opciones ampliacion de taps").
                            concat(", o marquela como verificacion, e intente de nuevo guardar"));
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msn, ""));
                    return false;
                
                }
            
            }else{
                if (!manejaTecnologias || isMultiagenda.equals("S")) {
                    String msn = "Un tipo de OT de Verificacion ".
                            concat("debe crear tecnologias ".
                            concat("y no puede manejar multiagenda, marque".
                            concat(" la opcion crea tecnologias y desmarque multiagenda, o ").
                            concat(" desmarque verificacion,  e intente de nuevo guardar")));
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msn, ""));
                    return false;                
                }
            }
            return true;
        } catch (NumberFormatException e) {
            String msn = "Error al validar datos de creación de tipo de ot";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
            return false;
        } catch (Exception e) {
            String msn = "Error al validar datos de creación de tipo de ot";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
            return false;
        }
    }

    /**
     * Función utilizada para validar que el nombre de tipo de ot no se enuentra
     * previamente registrado en la base de datos.
     *
     * @author Juan David Hernandez
     * @param nombreTipoOt
     * @return 
     */
    public boolean validarDuplicidadNombreTipoOt(String nombreTipoOt) {
        try {
            TipoOtHhppMgl nombreDuplicado = tipoOtHhppMglFacadeLocal
                    .findTipoOtHhppByNombreTipoOt(nombreTipoOt.toUpperCase());

            if (nombreDuplicado != null && nombreDuplicado.getNombreTipoOt()
                    .equalsIgnoreCase(nombreTipoOt)) {
                return false;
            }
            return true;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en validar Duplicidad Nombre Tipo Ot" + e.getMessage() , e, LOGGER);
            return true;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validar Duplicidad Nombre Tipo Ot" + e.getMessage() , e, LOGGER);
            return true;
        }
    }

    /**
     * Función utilizada para limpiar el formulario de la pantalla
     *
     * @author Juan David Hernandez
     */
    public void limpiarTipoOtHhpp() {
        tipoOtHhppMgl = new TipoOtHhppMgl();
        estadoInicialInternoList = new ArrayList();
        estadoFinalInternoList = new ArrayList();
        estadoCreacionInternoList = new ArrayList();
        agendable = false;
        ampliacionDeTab = false;
        manejaTecnologias = false;
        manejaVisitaDomiciliaria = false;
        showEstadoFinal = false;
        showEstadoCreacion = false;
        showTipoOtParaAmpliacionDeTab = false;
        ampliacionDeTabIsChecked = false;
        estadoInicial = BigDecimal.ZERO;
        estadoFinal = "";
        subTipoTrabajoSeleccionado = BigDecimal.ZERO;
        tipoTrabajoSeleccionado = BigDecimal.ZERO;
        estadoCreacion = "";
        nombreTipoOt = "";
        estadoCreacionInterno = BigDecimal.ZERO;
        ans = "";
        ansAviso = "";
        gestionRolesOtDireccionesList = new ArrayList<GestionRolesOtDireccionesDto>();
        showRoles = false;
        rolSeleccionado = "";
        tipoTrabajoRRSelect = BigDecimal.ZERO;
        estadoResultadoInicialRRSelect=BigDecimal.ZERO;
        estadoResultadoCierreRRSelect= BigDecimal.ZERO;
    }

    public void obtenerTipoOtParaAmpliacionDeTab() {
        if (!ampliacionDeTabIsChecked) {
            showTipoOtParaAmpliacionDeTab = true;
            obtenerListadoDeTipoOt();
            ampliacionDeTabIsChecked = true;
        } else {
            showTipoOtParaAmpliacionDeTab = false;
            ampliacionDeTabIsChecked = false;
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
            return false;
        } catch (Exception nfe) {            
            return false;
        }
    }
    
    
    public void obtenerListasOtRr() throws ApplicationException {

        CmtTipoBasicaMgl tipoBasicaEstadoResultado
                = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_RESULTADO);
        List<String> tiposEstadosInicialRR = Arrays.asList("PROGRAMADO", "EN CURSO");
        listadoEstadoInicial
                = cmtBasicaMglFacadeLocal.findEstadoResultadoOTRR(tiposEstadosInicialRR, tipoBasicaEstadoResultado);
        List<String> tiposEstadosCierreRR = Arrays.asList("REALIZADO");
        listadoEstadoCierre
                = cmtBasicaMglFacadeLocal.findEstadoResultadoOTRR(tiposEstadosCierreRR, tipoBasicaEstadoResultado);
        CmtTipoBasicaMgl tipoBasicaTipoTrabajo
                = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_DE_TRABAJO);
        listadoTipodeTrabajo = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoTrabajo);

    }

    public TipoOtHhppMgl getTipoOtHhppMgl() {
        return tipoOtHhppMgl;
    }

    public void setTipoOtHhppMgl(TipoOtHhppMgl tipoOtHhppMgl) {
        this.tipoOtHhppMgl = tipoOtHhppMgl;
    }

    public boolean isAgendable() {
        return agendable;
    }

    public void setAgendable(boolean agendable) {
        this.agendable = agendable;
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

    public List<CmtBasicaMgl> getEstadoFinalInternoList() {
        return estadoFinalInternoList;
    }

    public void setEstadoFinalInternoList(List<CmtBasicaMgl> estadoFinalInternoList) {
        this.estadoFinalInternoList = estadoFinalInternoList;
    }

    public List<CmtBasicaMgl> getEstadoCreacionInternoList() {
        return estadoCreacionInternoList;
    }

    public void setEstadoCreacionInternoList(List<CmtBasicaMgl> estadoCreacionInternoList) {
        this.estadoCreacionInternoList = estadoCreacionInternoList;
    }

    public String getEstadoFinal() {
        return estadoFinal;
    }

    public void setEstadoFinal(String estadoFinal) {
        this.estadoFinal = estadoFinal;
    }

    public String getEstadoCreacion() {
        return estadoCreacion;
    }

    public void setEstadoCreacion(String estadoCreacion) {
        this.estadoCreacion = estadoCreacion;
    }

    public BigDecimal getEstadoCreacionInterno() {
        return estadoCreacionInterno;
    }

    public void setEstadoCreacionInterno(BigDecimal estadoCreacionInterno) {
        this.estadoCreacionInterno = estadoCreacionInterno;
    }

    public BigDecimal getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(BigDecimal estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getAnsAviso() {
        return ansAviso;
    }

    public void setAnsAviso(String ansAviso) {
        this.ansAviso = ansAviso;
    }

    public String getNombreTipoOt() {
        return nombreTipoOt;
    }

    public void setNombreTipoOt(String nombreTipoOt) {
        this.nombreTipoOt = nombreTipoOt;
    }

    public boolean isShowEstadoFinal() {
        return showEstadoFinal;
    }

    public void setShowEstadoFinal(boolean showEstadoFinal) {
        this.showEstadoFinal = showEstadoFinal;
    }

    public boolean isShowEstadoCreacion() {
        return showEstadoCreacion;
    }

    public void setShowEstadoCreacion(boolean showEstadoCreacion) {
        this.showEstadoCreacion = showEstadoCreacion;
    }

    public boolean isAmpliacionDeTab() {
        return ampliacionDeTab;
    }

    public void setAmpliacionDeTab(boolean ampliacionDeTab) {
        this.ampliacionDeTab = ampliacionDeTab;
    }

    public boolean isShowTipoOtParaAmpliacionDeTab() {
        return showTipoOtParaAmpliacionDeTab;
    }

    public void setShowTipoOtParaAmpliacionDeTab(boolean showTipoOtParaAmpliacionDeTab) {
        this.showTipoOtParaAmpliacionDeTab = showTipoOtParaAmpliacionDeTab;
    }

    public List<TipoOtHhppMgl> getTipoOtList() {
        return tipoOtList;
    }

    public void setTipoOtList(List<TipoOtHhppMgl> tipoOtList) {
        this.tipoOtList = tipoOtList;
    }

    public boolean isAmpliacionDeTabIsChecked() {
        return ampliacionDeTabIsChecked;
    }

    public void setAmpliacionDeTabIsChecked(boolean ampliacionDeTabIsChecked) {
        this.ampliacionDeTabIsChecked = ampliacionDeTabIsChecked;
    }

    public BigDecimal getTipoOtDeAmpliacionDeTabSeleccionada() {
        return tipoOtDeAmpliacionDeTabSeleccionada;
    }

    public void setTipoOtDeAmpliacionDeTabSeleccionada(BigDecimal tipoOtDeAmpliacionDeTabSeleccionada) {
        this.tipoOtDeAmpliacionDeTabSeleccionada = tipoOtDeAmpliacionDeTabSeleccionada;
    }

    public List<CmtBasicaMgl> getTipoTrabajoList() {
        return tipoTrabajoList;
    }

    public void setTipoTrabajoList(List<CmtBasicaMgl> tipoTrabajoList) {
        this.tipoTrabajoList = tipoTrabajoList;
    }

    public List<CmtBasicaMgl> getSubTipoTrabajoList() {
        return subTipoTrabajoList;
    }

    public void setSubTipoTrabajoList(List<CmtBasicaMgl> subTipoTrabajoList) {
        this.subTipoTrabajoList = subTipoTrabajoList;
    }

    public BigDecimal getTipoTrabajoSeleccionado() {
        return tipoTrabajoSeleccionado;
    }

    public void setTipoTrabajoSeleccionado(BigDecimal tipoTrabajoSeleccionado) {
        this.tipoTrabajoSeleccionado = tipoTrabajoSeleccionado;
    }

    public BigDecimal getSubTipoTrabajoSeleccionado() {
        return subTipoTrabajoSeleccionado;
    }

    public void setSubTipoTrabajoSeleccionado(BigDecimal subTipoTrabajoSeleccionado) {
        this.subTipoTrabajoSeleccionado = subTipoTrabajoSeleccionado;
    }

    public boolean isManejaTecnologias() {
        return manejaTecnologias;
    }

    public void setManejaTecnologias(boolean manejaTecnologias) {
        this.manejaTecnologias = manejaTecnologias;
    }

    public boolean isManejaVisitaDomiciliaria() {
        return manejaVisitaDomiciliaria;
    }

    public void setManejaVisitaDomiciliaria(boolean manejaVisitaDomiciliaria) {
        this.manejaVisitaDomiciliaria = manejaVisitaDomiciliaria;
    }

    public BigDecimal getRazonSeleccionada() {
        return razonSeleccionada;
    }

    public void setRazonSeleccionada(BigDecimal razonSeleccionada) {
        this.razonSeleccionada = razonSeleccionada;
    }

    public boolean isShowRoles() {
        return showRoles;
    }

    public void setShowRoles(boolean showRoles) {
        this.showRoles = showRoles;
    }

    public String getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(String rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public List<String> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<String> rolesList) {
        this.rolesList = rolesList;
    }

    public List<GestionRolesOtDireccionesDto> getGestionRolesOtDireccionesList() {
        return gestionRolesOtDireccionesList;
    }

    public void setGestionRolesOtDireccionesList(List<GestionRolesOtDireccionesDto> gestionRolesOtDireccionesList) {
        this.gestionRolesOtDireccionesList = gestionRolesOtDireccionesList;
    }

    public boolean isRequiereOnyx() {
        return requiereOnyx;
    }

    public void setRequiereOnyx(boolean requiereOnyx) {
        this.requiereOnyx = requiereOnyx;
    }

    public String getIsMultiagenda() {
        return isMultiagenda;
    }

    public void setIsMultiagenda(String isMultiagenda) {
        this.isMultiagenda = isMultiagenda;
    }

    public List<CmtBasicaMgl> getListadoTipodeTrabajo() {
        return listadoTipodeTrabajo;
    }

    public void setListadoTipodeTrabajo(List<CmtBasicaMgl> listadoTipodeTrabajo) {
        this.listadoTipodeTrabajo = listadoTipodeTrabajo;
    }

    public List<CmtBasicaMgl> getListadoEstadoCierre() {
        return listadoEstadoCierre;
    }

    public void setListadoEstadoCierre(List<CmtBasicaMgl> listadoEstadoCierre) {
        this.listadoEstadoCierre = listadoEstadoCierre;
    }

    public List<CmtBasicaMgl> getListadoEstadoInicial() {
        return listadoEstadoInicial;
    }

    public void setListadoEstadoInicial(List<CmtBasicaMgl> listadoEstadoInicial) {
        this.listadoEstadoInicial = listadoEstadoInicial;
    }

    public BigDecimal getTipoTrabajoRRSelect() {
        return tipoTrabajoRRSelect;
    }

    public void setTipoTrabajoRRSelect(BigDecimal tipoTrabajoRRSelect) {
        this.tipoTrabajoRRSelect = tipoTrabajoRRSelect;
    }

    public BigDecimal getEstadoResultadoInicialRRSelect() {
        return estadoResultadoInicialRRSelect;
    }

    public void setEstadoResultadoInicialRRSelect(BigDecimal estadoResultadoInicialRRSelect) {
        this.estadoResultadoInicialRRSelect = estadoResultadoInicialRRSelect;
    }

    public BigDecimal getEstadoResultadoCierreRRSelect() {
        return estadoResultadoCierreRRSelect;
    }

    public void setEstadoResultadoCierreRRSelect(BigDecimal estadoResultadoCierreRRSelect) {
        this.estadoResultadoCierreRRSelect = estadoResultadoCierreRRSelect;
    }
    
    public String getIsTipoVerificacion() {
        return isTipoVerificacion;
}

    public void setIsTipoVerificacion(String isTipoVerificacion) {
        this.isTipoVerificacion = isTipoVerificacion;
    }
}
