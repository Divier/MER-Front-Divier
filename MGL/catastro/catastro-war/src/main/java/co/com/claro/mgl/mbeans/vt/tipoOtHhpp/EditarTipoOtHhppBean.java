/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.tipoOtHhpp;

import co.com.claro.mgl.businessmanager.cm.CmtOpcionesRolMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.RazonesOtDireccionMglFacadeLocal;
import co.com.claro.mgl.facade.TipoOtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuariosPortalFacadeLocal;
import co.com.claro.mgl.jpa.entities.MglRazonesOtDireccion;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.JSFUtil;
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
@ManagedBean(name = "editarTipoOtHhppBean")
@ViewScoped
public class EditarTipoOtHhppBean implements Serializable {

    private TipoOtHhppMgl tipoOtHhppSeleccionado;
    private List<CmtBasicaMgl> estadoGeneralList;
    private List<CmtBasicaMgl> estadoInicialInternoList;
    private List<CmtBasicaMgl> estadoFinalInternoList;
    private List<CmtBasicaMgl> estadoCreacionInternoList;
    private List<CmtBasicaMgl> tipoTrabajoList;
    private List<CmtBasicaMgl> subTipoTrabajoList;
    private List<String> rolesList;
    private List<MglRazonesOtDireccion> gestionRolesOtDireccionesList
            = new ArrayList<MglRazonesOtDireccion>();
    private boolean agendable;
    private boolean ampliacionDeTab;
    private boolean ampliacionDeTabIsChecked = false;
    private boolean renderGuardar = true;
    private boolean renderEliminar = true;
    private boolean showTipoOtParaAmpliacionDeTab = false;
    private boolean showRoles = false;
    private boolean manejaTecnologias;
    private boolean manejaVisitaDomiciliaria;
    private String rolSeleccionado = "";
    private String estadoCreacion;
    private String serverHost;
    private String usuarioVT = null;
    private int perfilVt = 0;
    List<TipoOtHhppMgl> tipoOtList;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private static final Logger LOGGER = LogManager.getLogger(EditarTipoOtHhppBean.class);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private BigDecimal tipoOtDeAmpliacionDeTabSeleccionada;
    private BigDecimal tipoTrabajoSeleccionado;
    private BigDecimal subTipoTrabajoSeleccionado;
    private BigDecimal razonSeleccionada;
    private boolean requiereOnyx;
    private List<CmtBasicaMgl> listadoTipodeTrabajo;
    private List<CmtBasicaMgl> listadoEstadoCierre;
    private List<CmtBasicaMgl> listadoEstadoInicial;
    private BigDecimal tipoTrabajoRRSelect;
    private BigDecimal estadoResultadoInicialRRSelect;
    private BigDecimal estadoResultadoCierreRRSelect;
    private String isMultiagenda;
    private String isTipoVerificacion;
    
    @EJB
    private RazonesOtDireccionMglFacadeLocal razonesOtDireccionesMglFacadeLocal;
    @EJB
    private UsuariosPortalFacadeLocal usuariosPortalFacadeLocal;
    @EJB
    private TipoOtHhppMglFacadeLocal tipoOtHhppMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;

    public EditarTipoOtHhppBean() {
        try {
            serverHost
                    = Utilidades.queryParametrosConfig(co.com.telmex.catastro.services.util.Constant.HOST_REDIR_VT);
            SecurityLogin securityLogin = new SecurityLogin(facesContext);
              if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();

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
            tipoOtHhppMglFacadeLocal.setUser(usuarioVT, perfilVt);
            obtenerRoles();
            cargarTipoOtHhppSeleccionada();
            obtenerTipoTrabajoList();
            obtenerSubTipoTrabajoList();
            obtenerListadoDeTipoOt();
            consultarRazonesOtDirecciones();
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
     * Funcion para consultar las razones asociadas a este tipo Ot
     *
     * @author Orlando Velasquez Diaz
     */
    public void consultarRazonesOtDirecciones() {
        try {
            gestionRolesOtDireccionesList = razonesOtDireccionesMglFacadeLocal.
                    findRazonesOtDireccionByTipoOTDirecciones(tipoOtHhppSeleccionado);

        } catch (ApplicationException e) {
            String msn = "Error al consultar Razones Ot Direcciones";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al consultar Razones Ot Direcciones";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Funcion para desactivar la razon Ot Direcciones en la base de datos y
     * eliminarla de la tabla
     *
     * @author Orlando Velasquez Diaz
     * @param razonOtDireccion
     */
    public void eliminarRazonOtDirecciones(MglRazonesOtDireccion razonOtDireccion) {
        try {
            razonesOtDireccionesMglFacadeLocal.eliminarRazonOtDirecciones(razonOtDireccion);
            consultarRazonesOtDirecciones();
        } catch (ApplicationException e) {
            String msn = "Error al Eliminar Razon";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al Eliminar Razon";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Funcion para obtener la informacion del tipo de Ot al cargar la pantalla
     *
     * @author Orlando Velasquez Diaz
     */
    public void cargarTipoOtHhppSeleccionada() {
        try {
            // Instacia Bean de Session para obtener la solicitud seleccionada
            TipoOtHhppMglSessionBean tipoOtHhppMglSessionBean
                    = (TipoOtHhppMglSessionBean) JSFUtil.getSessionBean(TipoOtHhppMglSessionBean.class);

            BigDecimal idTipoOtHhppSeleccionada
                    = tipoOtHhppMglSessionBean.getTipoOtHhppMglSeleccionado().getTipoOtId();
            if (idTipoOtHhppSeleccionada != null) {
                TipoOtHhppMgl tipoOtHhpp = new TipoOtHhppMgl();
                tipoOtHhpp.setTipoOtId(idTipoOtHhppSeleccionada);
                tipoOtHhppSeleccionado = tipoOtHhppMglFacadeLocal.findById(tipoOtHhpp);
                obtenerEstadoGeneralList();
                agendable = parseValorCheckBox(tipoOtHhppSeleccionado.getAgendable());
                ampliacionDeTab = parseValorCheckBox(tipoOtHhppSeleccionado.getAmpliacionTab());
                manejaTecnologias = parseValorCheckBox(tipoOtHhppSeleccionado.getManejaTecnologias());
                manejaVisitaDomiciliaria = parseValorCheckBox(tipoOtHhppSeleccionado.getManejaVisitaDomiciliaria());
                if (ampliacionDeTab) {
                    showTipoOtParaAmpliacionDeTab = true;
                    ampliacionDeTabIsChecked = true;
                    tipoOtDeAmpliacionDeTabSeleccionada = tipoOtHhppSeleccionado.getTipoOtAmpliacionTab();
                }

                obtenerEstadoCreacionInternoList();
                tipoTrabajoSeleccionado = tipoOtHhppSeleccionado.getTipoTrabajoOFSC().getBasicaId();
                subTipoTrabajoSeleccionado = tipoOtHhppSeleccionado.getSubTipoOrdenOFSC().getBasicaId();
                isMultiagenda = tipoOtHhppSeleccionado.getIsMultiagenda();
                isTipoVerificacion=tipoOtHhppSeleccionado.getEsTipoVisitaTecnica();
                tipoTrabajoRRSelect = tipoOtHhppSeleccionado.getTipoTrabajoRR() != null ? tipoOtHhppSeleccionado.getTipoTrabajoRR().getBasicaId(): null;
                estadoResultadoInicialRRSelect = tipoOtHhppSeleccionado.getEstadoOtRRInicial() != null ? tipoOtHhppSeleccionado.getEstadoOtRRInicial().getBasicaId(): null;
                estadoResultadoCierreRRSelect = tipoOtHhppSeleccionado.getEstadoOtRRFinal() != null ? tipoOtHhppSeleccionado.getEstadoOtRRFinal().getBasicaId(): null;
            } else {
                String msn = "Ocurrio un error intentando cargar la tipo"
                        + " de ot seleccionada";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msn, ""));
            }

        } catch (ApplicationException e) {
            String msn = "Error al intentar cargar el tipo de ot seleccionado";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al intentar cargar el tipo de ot seleccionado";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }

    }

    /**
     * Funcion utilizada para obtener listado de tipos de trabajo
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
     * Funcion utilizada para obtener listado de sub tipos de trabajo
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
     * Funcion utilizada para renderiza o no el desplegable para seleccionar un
     * Tipo ot para ampliacion de Tab
     *
     * @author Orlando Velasquez Diaz
     */
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
     * Funcion utilizada para obtener el listado de tipos de Ot para poder
     * asociarla como ampliacion de Tab
     *
     * @author Orlando Velasquez Diaz
     */
    public void obtenerListadoDeTipoOt() {
        try {
            tipoOtList = tipoOtHhppMglFacadeLocal.findAll();

            //Metodo Para remover el Tipo Ot seleccionado de la lista de 
            //Ot disponibles para ampliacion de Tab
            for (int i = 0; i < tipoOtList.size(); i++) {
                TipoOtHhppMgl tipoOt = tipoOtList.get(i);
                if (tipoOt.getTipoOtId().compareTo(
                        tipoOtHhppSeleccionado.getTipoOtId()) == 0) {
                    tipoOtList.remove(tipoOt);
                }
            }

        } catch (ApplicationException e) {
            String msn = "Error al cargar listado de tipos de Ot";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al cargar listado de tipos de Ot";
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
    public BigDecimal obtenerValorCheckBox(boolean agendableSeleccionado) {
        if (agendableSeleccionado) {
            return new BigDecimal(1);
        } else {
            return new BigDecimal(0);
        }
    }

    /**
     * Función utilizada para obtener el valor del si la tipo de ot es agendable
     *
     * @author Juan David Hernandez
     * @param valorAgendableSeleccionado
     * @return 
     */
    public boolean parseValorCheckBox(BigDecimal valorAgendableSeleccionado) {
        if (valorAgendableSeleccionado.equals(new BigDecimal(1))) {
            return true;
        } else {
            return false;
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
    public String estadoInternoASeleccionar(String estadoSeleccionado) {
        try {
            if (estadoSeleccionado != null
                    && !estadoSeleccionado.trim().isEmpty()) {

                if (estadoSeleccionado.contains(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO)) {
                    return Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO_COMPLETO;
                } else {
                    if (estadoSeleccionado.contains(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO)) {
                        return Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO_COMPLETO;
                    } else {
                        if (estadoSeleccionado.contains(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ANULADO)) {
                            return Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ANULADO_COMPLETO;
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
     * Obtiene listado de estados iniciales internos
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

                    if (!estadoCreacion.equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO_COMPLETO)) {
                        showRoles = false;
                    } else {
                        showRoles = true;
                    }
                } else {
                    estadoCreacionInternoList = new ArrayList();
                }
            }
        } catch (ApplicationException e) {
            String msn = "Error al cargar listado de estados final interno";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al cargar listado de estados final interno";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función utilizada para editar un tipo de ot en la base de datos
     *
     * @author Juan David Hernandez
     */
    public void editarTipoOtHhpp() {
        try {
            if (validacionDatosTipoOtHhpp()) {
                BigDecimal valorAgendable = obtenerValorCheckBox(agendable);
                BigDecimal valorAmpliacionDeTab = obtenerValorCheckBox(ampliacionDeTab);
                BigDecimal valorManejaTecnologias = obtenerValorCheckBox(manejaTecnologias);
                BigDecimal valorManejaVisitaDomiciliaria = obtenerValorCheckBox(manejaVisitaDomiciliaria);
                tipoOtHhppSeleccionado.setAgendable(valorAgendable);
                tipoOtHhppSeleccionado.setAmpliacionTab(valorAmpliacionDeTab);
                tipoOtHhppSeleccionado.setManejaTecnologias(valorManejaTecnologias);
                tipoOtHhppSeleccionado.setManejaVisitaDomiciliaria(valorManejaVisitaDomiciliaria);
                tipoOtHhppSeleccionado.setNombreTipoOt(tipoOtHhppSeleccionado.getNombreTipoOt().toUpperCase());
                tipoOtHhppSeleccionado.setAns(tipoOtHhppSeleccionado.getAns());
                tipoOtHhppSeleccionado.setAnsAviso(tipoOtHhppSeleccionado.getAnsAviso());
                tipoOtHhppSeleccionado.setEsTipoVisitaTecnica(isTipoVerificacion);
                CmtBasicaMgl tipoTrabajo = cmtBasicaMglFacadeLocal.findById(tipoTrabajoSeleccionado);
                CmtBasicaMgl subTipoTrabajo = cmtBasicaMglFacadeLocal.findById(subTipoTrabajoSeleccionado);
                tipoOtHhppSeleccionado.setTipoTrabajoOFSC(tipoTrabajo);
                tipoOtHhppSeleccionado.setSubTipoOrdenOFSC(subTipoTrabajo);

                if (tipoOtDeAmpliacionDeTabSeleccionada != null && ampliacionDeTab) {
                    TipoOtHhppMgl tipoOtSeleccionada = tipoOtHhppMglFacadeLocal.findTipoOtHhppById(tipoOtDeAmpliacionDeTabSeleccionada);
                    tipoOtHhppSeleccionado.setTipoOtAmpliacionTab(tipoOtSeleccionada.getTipoOtId());
                } else {
                    tipoOtHhppSeleccionado.setTipoOtAmpliacionTab(null);
                }
                
                tipoOtHhppSeleccionado.setRequiereOnyx(requiereOnyx == true ? "Y" : "N");
                
                if(isMultiagenda.equalsIgnoreCase("S")){
                    
                    tipoOtHhppSeleccionado.setIsMultiagenda(isMultiagenda);
                    CmtBasicaMgl tipoTrabajoRR = new CmtBasicaMgl();
                    tipoTrabajoRR.setBasicaId(tipoTrabajoRRSelect);
                    tipoOtHhppSeleccionado.setTipoTrabajoRR(tipoTrabajoRR);

                    CmtBasicaMgl estadoResultadoIniRR = new CmtBasicaMgl();
                    estadoResultadoIniRR.setBasicaId(estadoResultadoInicialRRSelect);
                    tipoOtHhppSeleccionado.setEstadoOtRRInicial(estadoResultadoIniRR);

                    CmtBasicaMgl estadoResultadoCieRR = new CmtBasicaMgl();
                    estadoResultadoCieRR.setBasicaId(estadoResultadoCierreRRSelect);
                    tipoOtHhppSeleccionado.setEstadoOtRRFinal(estadoResultadoCieRR);
                } else {
                    tipoOtHhppSeleccionado.setIsMultiagenda(isMultiagenda);
                    tipoOtHhppSeleccionado.setTipoTrabajoRR(null);
                    tipoOtHhppSeleccionado.setEstadoOtRRInicial(null);
                    tipoOtHhppSeleccionado.setEstadoOtRRFinal(null);
                }
               
                
                tipoOtHhppMglFacadeLocal.update(tipoOtHhppSeleccionado);

                String msn = "Cambios realizados a la Tipo de Ot Hhpp satisfactoriamente";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                msn, ""));
            }

        } catch (ApplicationException e) {
            String msn = "Error al intentar guardar cambios en tipo de ot";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al intentar guardar cambios en tipo de ot";
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
                MglRazonesOtDireccion razonesOtDireccion = new MglRazonesOtDireccion();
                razonesOtDireccion.setEstadoGeneral(estadoGeneral);
                razonesOtDireccion.setRazon(razon);
                if (estadoCreacion.equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO_COMPLETO)) {
                    razonesOtDireccion.setRol(rolSeleccionado);
                } else {
                    razonesOtDireccion.setRol(null);
                }
                razonesOtDireccion.setTipoOtId(tipoOtHhppSeleccionado);

                if (validarRazonesRepetidas(razonesOtDireccion)) {
                    razonesOtDireccionesMglFacadeLocal.create(razonesOtDireccion);
                    consultarRazonesOtDirecciones();
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
     * Función utilizada para validar que no exista en la tabla razones iguales
     *
     * @author Orlando Velasquez
     * @param razonOtdireccion
     * @return
     */
    public boolean validarRazonesRepetidas(MglRazonesOtDireccion razonOtdireccion) {

        for (MglRazonesOtDireccion razonesEnLaTabla : gestionRolesOtDireccionesList) {

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
     * Validar campos al momento de agregar una razon
     *
     * @author Orlando Velasquez Diaz
     * @return
     */
    public boolean validarCamposAgregarRazon() {

        if (estadoCreacion == null) {
            String msn = "Seleccione un estado porfavor.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            return false;
        } else if (razonSeleccionada == null) {
            String msn = "Seleccione una razon porfavor.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            return false;
        } else if (estadoCreacion.equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO_COMPLETO)) {
            if (rolSeleccionado == null) {
                String msn = "Seleccione un rol porfavor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;
            }
        }
        return true;
    }

    /**
     * Desactiva El tipo Ot seleccionado
     *
     * @author Orlando Velasquez Diaz
     */
    public void eliminarTipoOTHhpp() {
        try {
            if (tipoOtHhppSeleccionado == null
                    || tipoOtHhppSeleccionado.getTipoOtId().equals(BigDecimal.ZERO)) {
                String msn = "Ha ocurrido un error al seleccionar el registro. "
                        + "Intente nuevamente por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }

            if (!tipoOtHhppMglFacadeLocal
                    .validarTipoOtHhppEnUso(tipoOtHhppSeleccionado.getTipoOtId())) {

                if (tipoOtHhppMglFacadeLocal.eliminarTipoOtHhpp(tipoOtHhppSeleccionado)) {
                    String msn = "Tipo de Ot eliminada satisfactoriamente";
                    renderEliminar = false;
                    renderGuardar = false;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                } else {
                    String msn = "Ha ocurrido un error intentando eliminar"
                            + " el registro. Intente nuevamente por favor.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                }
            } else {
                String msn = "La tipo de ot que desea eliminar se encuentra"
                        + " en uso asociada a una orden de trabajo. "
                        + "Es necesario cambiar la asociación en la orden "
                        + "de trabajo para poder eliminar este tipo de ot. ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            }
        } catch (ApplicationException e) {
            String msn = "Error al redireccionar a creación de Tipo Ot";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al redireccionar a creación de Tipo Ot";
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
            if (tipoOtHhppSeleccionado.getNombreTipoOt() == null
                    || tipoOtHhppSeleccionado.getNombreTipoOt().isEmpty()) {

                String msn = "Debe ingresar un nombre de tipo de ot por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msn, ""));
                return false;

            } else {

                if (tipoOtHhppSeleccionado.getAns() == 0) {
                    String msn = "Debe ingresar un valor ANS por favor.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msn, ""));
                    return false;

                } else {
                    if (tipoOtHhppSeleccionado.getAnsAviso() == 0) {
                        String msn = "Debe ingresar un "
                                + "valor ANS AVISO por favor.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msn, ""));
                        return false;
                    } else {
                        if (tipoOtHhppSeleccionado.getAnsAviso() > tipoOtHhppSeleccionado.getAns()) {
                            String msn = "EL valor de ANS "
                                    + "debe ser mayor al de "
                                    + "ANS AVISO por favor.";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msn, ""));
                            return false;
                        }
                    }
                }
              
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
        } catch (Exception e) {
            String msn = "Error al validar datos de creación de tipo de ot";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
            return false;
        }
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

    public TipoOtHhppMgl getTipoOtHhppSeleccionado() {
        return tipoOtHhppSeleccionado;
    }

    public void setTipoOtHhppSeleccionado(TipoOtHhppMgl tipoOtHhppSeleccionado) {
        this.tipoOtHhppSeleccionado = tipoOtHhppSeleccionado;
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

    public String getEstadoCreacion() {
        return estadoCreacion;
    }

    public void setEstadoCreacion(String estadoCreacion) {
        this.estadoCreacion = estadoCreacion;
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

    public boolean isAmpliacionDeTab() {
        return ampliacionDeTab;
    }

    public void setAmpliacionDeTab(boolean ampliacionDeTab) {
        this.ampliacionDeTab = ampliacionDeTab;
    }

    public boolean isAmpliacionDeTabIsChecked() {
        return ampliacionDeTabIsChecked;
    }

    public void setAmpliacionDeTabIsChecked(boolean ampliacionDeTabIsChecked) {
        this.ampliacionDeTabIsChecked = ampliacionDeTabIsChecked;
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

    public BigDecimal getTipoOtDeAmpliacionDeTabSeleccionada() {
        return tipoOtDeAmpliacionDeTabSeleccionada;
    }

    public void setTipoOtDeAmpliacionDeTabSeleccionada(BigDecimal tipoOtDeAmpliacionDeTabSeleccionada) {
        this.tipoOtDeAmpliacionDeTabSeleccionada = tipoOtDeAmpliacionDeTabSeleccionada;
    }

    public boolean isRenderGuardar() {
        return renderGuardar;
    }

    public void setRenderGuardar(boolean renderGuardar) {
        this.renderGuardar = renderGuardar;
    }

    public boolean isRenderEliminar() {
        return renderEliminar;
    }

    public void setRenderEliminar(boolean renderEliminar) {
        this.renderEliminar = renderEliminar;
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

    public List<String> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<String> roles) {
        this.rolesList = roles;
    }

    public String getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(String rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public List<MglRazonesOtDireccion> getGestionRolesOtDireccionesList() {
        return gestionRolesOtDireccionesList;
    }

    public void setGestionRolesOtDireccionesList(List<MglRazonesOtDireccion> gestionRolesOtDireccionesList) {
        this.gestionRolesOtDireccionesList = gestionRolesOtDireccionesList;
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
    
    public boolean getRequiereOnyx() {
        if (tipoOtHhppSeleccionado.getRequiereOnyx() != null) {
            requiereOnyx = tipoOtHhppSeleccionado.getRequiereOnyx().equals("Y");            
        }else {
            requiereOnyx = false;
        }                        
        return requiereOnyx;
    }

    public void setRequiereOnyx(boolean requiereOnyx) {
        this.requiereOnyx = requiereOnyx;
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

    public String getIsMultiagenda() {
        return isMultiagenda;
    }

    public void setIsMultiagenda(String isMultiagenda) {
        this.isMultiagenda = isMultiagenda;
    }

    public String getIsTipoVerificacion() {
        return isTipoVerificacion;
}

    public void setIsTipoVerificacion(String isTipoVerificacion) {
        this.isTipoVerificacion = isTipoVerificacion;
    }

    
    
}
