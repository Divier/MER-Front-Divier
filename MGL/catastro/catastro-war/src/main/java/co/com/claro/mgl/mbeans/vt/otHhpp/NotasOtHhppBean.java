/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.otHhpp;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.AgendamientoHhppMglFacadeLocal;
import co.com.claro.mgl.facade.CmtNotasOtHhppDetalleMglFacadeLocal;
import co.com.claro.mgl.facade.CmtNotasOtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.OtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOrdenTrabajoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.CmtNotasOtHhppDetalleMgl;
import co.com.claro.mgl.jpa.entities.CmtNotasOtHhppMgl;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccion;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
 * @author Orlando Velasquez
 */
@ViewScoped
@ManagedBean(name = "notasOtHhppBean")
public class NotasOtHhppBean {

    private static final String TAB_NOTAS_EDITOR_OT_HHPP = "TABNOTASEDITOROTHHPP";
    private SecurityLogin securityLogin;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String usuarioVT = null;
    private int perfilVt = 0;
    private static final Logger LOGGER = LogManager.getLogger(NotasOtHhppBean.class);
    private String selectedTab = "NOTAS";
    private CmtNotasOtHhppMgl cmtNotasOtHhppMgl = new CmtNotasOtHhppMgl();
    private CmtBasicaMgl tipoNotaSelected = new CmtBasicaMgl();
    private List<CmtBasicaMgl> tipoNotaList;
    private String notaComentarioStr = "";
    private List<CmtNotasOtHhppMgl> notasOtHhppList;
    private boolean detalleOtHhpp;
    private OtHhppMgl otHhppSeleccionado;
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private boolean activacionUCM;
    private boolean otCerradaAnulada = false;
    private List<MglAgendaDireccion> agendasListConsulta;
    private List<String> agendasList;

    @EJB
    private CmtNotasOtHhppMglFacadeLocal cmtNotasOtHhppMglFacadeLocal;
    
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    
    @EJB
    private CmtNotasOtHhppDetalleMglFacadeLocal cmtNotasOtHhppDetalleMglFacadeLocal;
    
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    
    @EJB
    private OtHhppMglFacadeLocal otHhppMglFacadeLocal;
    
    @EJB
    private AgendamientoHhppMglFacadeLocal agendamientoHhppFacade;
    
    @EJB
    private CmtOrdenTrabajoMglFacadeLocal ordenTrabajoMglFacadeLocal;

    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;

    public NotasOtHhppBean() {

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

            if (usuarioVT == null) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en NotasOtHhppBean, validando autenticación. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en NotasOtHhppBean, validando autenticación. ", e, LOGGER);
        }

    }

    @PostConstruct
    public void init() {

        try {
            cargarOtHhppSeleccionada();
            findNotasByOtHhppId(otHhppSeleccionado.getOtHhppId());
            obtenerTipoNotasList();
            validarEstadoRenderizarBotones(otHhppSeleccionado);
            String subTipo = otHhppSeleccionado.getTipoOtHhppId().getSubTipoOrdenOFSC().getCodigoBasica();
            agendasList = null;
            agendasListConsulta = agendamientoHhppFacade.buscarAgendasPendientesByOtAndSubtipopOfsc(otHhppSeleccionado, subTipo);

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al hacer cargue inicial de la página: " +e.getMessage(), e, LOGGER);    
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al hacer cargue inicial de la página: " +e.getMessage(), e, LOGGER);    
        }

    }

    /**
     * Metodo para validar si el usuario tiene permisos de creacion
     * @return  {@code boolean} true si tiene permisos de creacion, false en caso contrario
     * @author Manuel Hernandez
     */
    public boolean isRolCrear(){
        try {
            return ValidacionUtil.validarVisualizacion(TAB_NOTAS_EDITOR_OT_HHPP, ValidacionUtil.OPC_CREACION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar el rol de creación. ");
        }
        return false;
    }
    
             /**
     * Función que valida si la OT esta cerrada o anulada para renderizar los botones de guardar cambios
     *
     * @author Juan David Hernandez
     * @param otHhppSeleccionado
     */
    public void validarEstadoRenderizarBotones(OtHhppMgl otHhppSeleccionado){
        try {
            if(otHhppSeleccionado != null && otHhppSeleccionado.getEstadoGeneral() != null 
                    && otHhppSeleccionado.getEstadoGeneral().getIdentificadorInternoApp() != null
                    && !otHhppSeleccionado.getEstadoGeneral().getIdentificadorInternoApp().isEmpty()){
                
                if(otHhppSeleccionado.getEstadoGeneral().getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO_COMPLETO)
                        || otHhppSeleccionado.getEstadoGeneral().getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ANULADO_COMPLETO)){
                    otCerradaAnulada = true;
                }else{
                    otCerradaAnulada = false;
                }
            }else{
                 otCerradaAnulada = false;
            }
        } catch (Exception e) {        
        FacesUtil.mostrarMensajeError("Error al validar estado de la ot para renderizar botones ", e, LOGGER);
          
        }
    }

    public void cargarOtHhppSeleccionada() throws ApplicationException {
        try {
            OtHhppMglSessionBean otHhppMglSessionBean
                    = (OtHhppMglSessionBean) JSFUtil.getSessionBean(OtHhppMglSessionBean.class);
            OtHhppMgl otHhpp = new OtHhppMgl();
            otHhpp.setOtHhppId(otHhppMglSessionBean.getOtHhppMglSeleccionado().getOtHhppId());
            otHhppSeleccionado = otHhppMglFacadeLocal.findById(otHhpp);           

        } catch (RuntimeException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
    /**
     * Función que permite navegar entre Tabs en la pantalla
     *
     * @author Juan David Hernandez
     * @param sSeleccionado
     */
    public void cambiarTab(String sSeleccionado) {
        try {
            ConstantsCM.TABS_HHPP Seleccionado
                    = ConstantsCM.TABS_HHPP.valueOf(sSeleccionado);
            switch (Seleccionado) {
                case GENERAL:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                                            + "editarOtHhpp.jsf");
                    break;
                case AGENDAMIENTO:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                                    + "agendamientoOtHhpp.jsf");
                    selectedTab = "AGENDAMIENTO";
                    activacionUCM = activaDesactivaUCM();
                    session.setAttribute("activaUCM", activacionUCM);
                    break;
                case NOTAS:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                                    + "notasOtHhpp.jsf");
                    selectedTab = "NOTAS";
                    break;
                case ONYX:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "otOnixHhpp.jsf");
                    selectedTab = "ONYX";
                    break;
                case HISTORICO:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "historicoAgendasOtHhpp.jsf");
                    selectedTab = "HISTORICO";
                    activacionUCM = activaDesactivaUCM();
                    session.setAttribute("activaUCM", activacionUCM);
                    break;
                case BITACORA:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "bitacoraOtHhpp.jsf");
                    selectedTab = "BITACORA";
                    break;    
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cambiarTab. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cambiarTab. ", e, LOGGER);
        }
    }
    

    /**
     * Función que consulta las notas de la solicitud creada.
     *
     * @author Orlando Velasquez
     * @param otHhppId id de la orden de trabajo seleccionada
     */
    public void findNotasByOtHhppId(BigDecimal otHhppId) throws ApplicationException {
        try {
            notasOtHhppList = cmtNotasOtHhppMglFacadeLocal
                    .findNotasByOtHhppId(otHhppId);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Función que obtiene tipo de notas
     *
     * @author Juan David Hernandez
     */
    public void obtenerTipoNotasList() throws ApplicationException {
        try {
            CmtTipoBasicaMgl tipoBasicaNotaOt;
            tipoBasicaNotaOt = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_DE_NOTAS);
            tipoNotaList = cmtBasicaMglFacadeLocal
                    .findByTipoBasica(tipoBasicaNotaOt);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    public void guardarNota() {
        try {
            if (agendasList != null && !agendasList.isEmpty()) {
                if (validarUpdateAgenda()) {
                    if (validarCamposNota()) {
                        cmtNotasOtHhppMgl.setOtHhppId(otHhppSeleccionado);
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
                        }
                        String mensajeFin = "";

                        for (String idAgenda : agendasList) {
                            for (MglAgendaDireccion agendas : agendasListConsulta) {
                                if (agendas.getAgendaId().toString().equalsIgnoreCase(idAgenda)) {

                                    agendamientoHhppFacade.updateAgendasForNotas(agendas, usuarioVT, perfilVt);
                                    String msn = "Se ha modificado la agenda:  " + agendas.getOfpsOtId() + "  "
                                            + "  para la ot: " + agendas.getOrdenTrabajo().getOtHhppId() + "\n";

                                    mensajeFin = mensajeFin += msn;
                                }
                            }
                        }
                        agendasList = null;
                        FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            "Nota agregada correctamente. \n "+mensajeFin+"", ""));
                    }
                }
            } else {
                if (validarCamposNota()) {
                    cmtNotasOtHhppMgl.setOtHhppId(otHhppSeleccionado);
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
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en guardarNota: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en guardarNota: " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que valida los datos necesarios para guardar una descripción de
     * la nota
     *
     * @author Juan David Hernandez return true si no se encuentran datos vacios
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public boolean validarCamposNota() throws ApplicationException {
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
                            if (cmtNotasOtHhppMgl.getNota().length() > 4000) {
                                String msnError = "Número máximo de caracteres es 4000 para la nota. ";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                msnError, ""));
                                return false;
                            } else {
                                return true;
                            }

                        }
                    }
                }

            }
        } catch (RuntimeException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Función que obtiene las notas de la solicitud creada.
     * @throws co.com.claro.mgl.error.ApplicationException   *
     * @author Juan David Hernandez
     */
    public void findNotasByOtHhppId() throws ApplicationException {
        try {
            notasOtHhppList = cmtNotasOtHhppMglFacadeLocal
                    .findNotasByOtHhppId(otHhppSeleccionado.getOtHhppId());
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Función que guarda el comentario de una nota.
     *
     * @author Orlando Velasquez
     * @throws java.io.IOException
     */
    public void guardarComentarioNota()  {
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
                    findNotasByOtHhppId();
                    notaComentarioStr = "";
                    tipoNotaSelected = new CmtBasicaMgl();
                    cmtNotasOtHhppMgl = new CmtNotasOtHhppMgl();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Comentario añadido correctamente.", ""));
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en guardarComentarioNota, al guardar comentario de la nota seleccionada: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en guardarComentarioNota, al guardar comentario de la nota seleccionada: " + e.getMessage(), e, LOGGER);
        }
    }

    public boolean validarDatosComentarioNota() throws ApplicationException {
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
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
     public boolean activaDesactivaUCM(){

        String msn;
        try {
            ParametrosMgl parametrosMgl = parametrosMglFacadeLocal.findByAcronimoName(Constant.ACTIVACION_ENVIO_UCM);
            String valor;
            if (parametrosMgl != null) {
                valor = parametrosMgl.getParValor();
                if (!valor.equalsIgnoreCase("1") && !valor.equalsIgnoreCase("0")) {
                    msn = "El valor configurado para el parametro:  "
                            + "" + Constant.ACTIVACION_ENVIO_UCM + " debe ser '1' o '0'  "
                            + "actualmente se encuentra el valor: " + valor + "";
                      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    activacionUCM = false;
                } else if (valor.equalsIgnoreCase("1")) {
                    activacionUCM = true;
                } else {
                    activacionUCM = false;
                }

            }
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al consultar el parametro: " + Constant.ACTIVACION_ENVIO_UCM + "" + ex.getMessage(), ex, LOGGER);
            activacionUCM = false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al consultar el parametro: " + Constant.ACTIVACION_ENVIO_UCM + "" + e.getMessage(), e, LOGGER);
            activacionUCM = false;
        }
        return activacionUCM;
    }
     
    public boolean validarUpdateAgenda() {

        boolean respuesta = false;

        try {
            String mensajesValidacion;
            String mensajesValidacionFinal = "";

            if (agendasList != null && !agendasList.isEmpty()) {
                for (String idAgendaLis : new ArrayList<>(agendasList)) {
                    MglAgendaDireccion agenda = agendamientoHhppFacade.buscarAgendaByIdAgenda(new Long(idAgendaLis));

                    if (agenda != null && agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                            .equalsIgnoreCase(Constant.ESTADO_AGENDA_CANCELADA)) {
                        mensajesValidacion = "La agenda " + agenda.getOfpsOtId() + " se encuentra cancelada y no se puede editar.";
                        mensajesValidacionFinal += mensajesValidacion;
                        for (String idAgenda : new ArrayList<>(agendasList)) {
                            if (agenda.getAgendaId().toString().equalsIgnoreCase(idAgenda)) {
                                agendasList.remove(idAgenda);
                            }
                        }
                    } else if (agenda != null && agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                            .equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA)) {
                        mensajesValidacion = "La agenda " + agenda.getOfpsOtId() + " se encuentra cerrada y no se puede editar.";
                        mensajesValidacionFinal += mensajesValidacion;
                        for (String idAgenda : new ArrayList<>(agendasList)) {
                            if (agenda.getAgendaId().toString().equalsIgnoreCase(idAgenda)) {
                                agendasList.remove(idAgenda);
                            }
                        }
                    } else if (agenda != null && agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                                .equalsIgnoreCase(Constant.ESTADO_AGENDA_NODONE)) {
                            mensajesValidacion = "La agenda " + agenda.getOfpsOtId() + " se encuentra nodone(cerrada) y no se puede editar.";
                            mensajesValidacionFinal += mensajesValidacion;
                            for (String idAgenda : new ArrayList<>(agendasList)) {
                                if (agenda.getAgendaId().toString().equalsIgnoreCase(idAgenda)) {
                                    agendasList.remove(idAgenda);
                                }
                            }
                        }
                    
                }
                if (!mensajesValidacionFinal.isEmpty()) {
                    respuesta = false;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacionFinal, null));
                } else {
                    respuesta = true;
                }
            } else {
                respuesta = true;
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error validando la Agenda  en NotasOtHhppBean: validarUpdateAgenda()" + e.getMessage(), e, LOGGER);
        }
        return respuesta;
    }

    public void limpiarCamposNota() {
        cmtNotasOtHhppMgl = new CmtNotasOtHhppMgl();
        tipoNotaSelected = new CmtBasicaMgl();
        agendasList = null;
    }

    public void mostarComentario(CmtNotasOtHhppMgl nota) {
        cmtNotasOtHhppMgl = nota;
        tipoNotaSelected = nota.getTipoNotaObj();
    }
    
     public String devuelveFechaFormater(Date fechaAgenda) {

        String date="";
        if (fechaAgenda != null) {
           date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(fechaAgenda);
        }
        return date;
    }

    public String getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(String selectedTab) {
        this.selectedTab = selectedTab;
    }

    public CmtNotasOtHhppMgl getCmtNotasOtHhppMgl() {
        return cmtNotasOtHhppMgl;
    }

    public void setCmtNotasOtHhppMgl(CmtNotasOtHhppMgl cmtNotasOtHhppMgl) {
        this.cmtNotasOtHhppMgl = cmtNotasOtHhppMgl;
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

    public List<CmtNotasOtHhppMgl> getNotasOtHhppList() {
        return notasOtHhppList;
    }

    public void setNotasOtHhppList(List<CmtNotasOtHhppMgl> notasOtHhppList) {
        this.notasOtHhppList = notasOtHhppList;
    }

    public boolean isDetalleOtHhpp() {
        return detalleOtHhpp;
    }

    public void setDetalleOtHhpp(boolean detalleOtHhpp) {
        this.detalleOtHhpp = detalleOtHhpp;
    }

    public OtHhppMgl getOtHhppSeleccionado() {
        return otHhppSeleccionado;
    }

    public void setOtHhppSeleccionado(OtHhppMgl otHhppSeleccionado) {
        this.otHhppSeleccionado = otHhppSeleccionado;
    }

    public boolean isOtCerradaAnulada() {
        return otCerradaAnulada;
    }

    public void setOtCerradaAnulada(boolean otCerradaAnulada) {
        this.otCerradaAnulada = otCerradaAnulada;
    }

    public List<String> getAgendasList() {
        return agendasList;
    }

    public void setAgendasList(List<String> agendasList) {
        this.agendasList = agendasList;
    }

    public List<MglAgendaDireccion> getAgendasListConsulta() {
        return agendasListConsulta;
    }

    public void setAgendasListConsulta(List<MglAgendaDireccion> agendasListConsulta) {
        this.agendasListConsulta = agendasListConsulta;
    }

}
