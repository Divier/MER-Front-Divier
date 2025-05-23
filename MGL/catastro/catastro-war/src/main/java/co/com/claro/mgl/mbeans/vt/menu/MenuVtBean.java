/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.menu;

import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.OtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOrdenTrabajoMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOpcionesRolMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.mbeans.cm.CuentaMatrizBean;
import co.com.claro.mgl.mbeans.cm.ot.OtMglBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.mbeans.vt.otHhpp.OtHhppMglSessionBean;
import co.com.claro.mgl.utils.MenuEnum;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
@ManagedBean(name = "menuVtBean")
@SessionScoped
public class MenuVtBean {

    /**
     * Constante para manejar el log de la clase.
     */
    private static final Logger LOGGER = LogManager.getLogger(MenuVtBean.class);
    /**
     * Contexto de jsf.
     */
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    /**
     * Contiene la respuesta http.
     */
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    /**
     * Contiene la sesion http´.
     */
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
    @EJB
    private CmtOpcionesRolMglFacadeLocal operacionesRolFacade;
    @EJB
    private CmtOrdenTrabajoMglFacadeLocal ordenTrabajoMglFacadeLocal;
    @EJB
    private OtHhppMglFacadeLocal otHhppMglFacadeLocal;

    private List<CmtOpcionesRolMgl> opcionesRol;
    private Map<String, Boolean> validaciones;
    private SecurityLogin securityLogin;
    private int perfil = 0;
    private String usuarioSesion;
    
    private ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
    /**
     * Constructor de la clase. Construye la clase y obtiene el usuario y su
     * perfil.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     */
    public MenuVtBean() {

        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
            }
            usuarioSesion = securityLogin.getLoginUser();
            perfil = securityLogin.getPerfilUsuario();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en MenuVtBean. Al cargar Bean de Visita Tecnica. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en MenuVtBean. Al cargar Bean de Visita Tecnica. ", e, LOGGER);
        }
    }

    /**
     * Cargar los permisos del menú. Carga los permisos del menú.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     */
    @PostConstruct
    public void cargaPermisosMenu() {
        try {
            opcionesRol = operacionesRolFacade.consultarOpcionesRol("MENU");

            if (CollectionUtils.isEmpty(opcionesRol)) {
                String msgError = "No fue posible encontrar la configuración del Menu por Rol";
                LOGGER.error(msgError);
                throw new ApplicationException(msgError);
            }

            addValidaciones();
            opcionesRol.forEach(this::validaMenuRol);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargaPermisosMenu. Al cargar los Menu por Rol ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargaPermisosMenu. Al cargar los Menu por Rol ", e, LOGGER);
        }
    }

    public void redirecToGestionar() {

        try {
            FacesUtil.navegarAPagina("/view/MGL/VT/gestion/DTH/gestionSolicitudDthListado.jsf");
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en redirecToGestionar. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en redirecToGestionar. ", e, LOGGER);
        }

    }

    /**
     * M&eacute;todo para inicializar el mapa y adicionar los campos booleanos
     * para validar la visualizaci&oacute;n de los menus. Por defecto se
     * inicializan en false.
     */
    private void addValidaciones() {
        validaciones = new HashMap<>();
        validaciones.put("rolDetalleCmPtGeneral", true);
        for (MenuEnum menu : MenuEnum.values()) {
            validaciones.put(menu.getValidador(), Boolean.FALSE);
        }
    }

    /**
     * M&eacute;todo para validar que la opci&oacute;n de men&uacute; cargada de
     * base de datos no llegue vacia.
     *
     * @param opcion objeto {@link CmtOpcionesRolMgl} con la opcion de
     * men&uacute; cargada
     */
    private void validaMenuRol(CmtOpcionesRolMgl opcion) {
        if (opcion != null && !"".equals(opcion.getNombreOpcion())
                && !"".equals(opcion.getRol())) {
            validarMenuRol(opcion);
        }
    }

    /**
     * M&eacute;todo privado para validar que la opci&oacute;n de men&uacute;
     * cargada corresponda a una opci&oacute;n existente en el aplicativo.
     *
     * @param opcion objeto {@link CmtOpcionesRolMgl} con la opcion de
     * men&uacute; cargada
     */
    private void validarMenuRol(CmtOpcionesRolMgl opcion) {
        for (MenuEnum menu : MenuEnum.values()) {
            if (menu.getDescripcion().equals(opcion.getNombreOpcion())) {
                validaciones.put(menu.getValidador(), verificaRol(opcion.getRol()));
                return;
            }
        }
    }

    /**
     * Verificar el rol del usuario. Verifica si el usuario tiene el pasado por
     * parámetro.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @param rol Rol a verificar.
     * @return True cuando el usuario tiene el rol del parámetro de lo contrario
     * false.
     */
    private boolean verificaRol(String rol) {
        try {
            return securityLogin.usuarioTieneRoll(rol);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en verificaRol. ", e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en verificaRol. ", e, LOGGER);
            return false;
        }
    }

    /**
     * Navegabilidad de la aplicación. Se encarga de redirigir las paginas segun
     * opción de menú seleccionada.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param caseMenu Contiene el opción del menú seleccionado.
     * @return Pagina de respuesta.
     */
    public String irMenu(String caseMenu) {
        String formTabSeleccionado;
        ConstantsCM.ITEM_MENU_HHPP Seleccionado = ConstantsCM.ITEM_MENU_HHPP.valueOf(caseMenu);
        switch (Seleccionado) {
            case SOLICITUDVTCREARSOLICITUD:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/solicitudes/DTH/crearSolicitudHhppDth?faces-redirect=true";
                break;
            case SOLICITUDVTEDIFICIOCONJUNTO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/EdificioConjuntoView";
                break;
            case SOLICITUDVTREPLANTEAMIENTO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/ReplanteamientoView.xhtml";
                break;
            case SOLICITUDVTENCASA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/EnCasaView.xhtml";
                break;
            case SOLICITUDVTCREACIONCM:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/CreacionCuentaMatrizView.xhtml";
                break;
            case SOLICITUDVTCREACIONHHPPUNIDI:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/CreacionHHPPUniView.xhtml";
                break;
            case SOLICITUDVTCREACIONHHPPCM:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/CreacionHHPPEnCuentaMatrizView.xhtml";
                break;
            case SOLICITUDVTCAMBIOESTRATO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/CambioEstratoView.xhtml";
                break;
            case SOLICITUDVTMODIFICARELIMINARCM:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/ElimModCuentaMatrizView.xhtml";
                break;
            case SOLICITUDVTMODIFICARHHPP:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/index.xhtml";
                break;
            case SOLICITUDVERIFICARCASA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/index.xhtml";
                break;
            case SOLICITUDVTVIABILIDADINTERNET:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/ViabilidadInternetView.xhtml";
                break;
            case SOLICITUDVTGESTIONSOLICITUD:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/gestion/DTH/gestionSolicitudDthListado.jsf";
                break;
            case GESTIONCAMBIOSESTRATO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/gestion/DTH/gestionSolicitudDthListado.jsf";
                break;
            case GESTIONVTEDCO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/Lista.xhtml";
                break;
            case GESTIONVTREPLANTEAMIENTO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/Lista.xhtml";
                break;
            case GESTIONVTENCAS:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/Lista.xhtml";
                break;
            case GESTIONCRCUMA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/Lista.xhtml";
                break;
            case GESTIONCRHHPPUNI:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/Lista.xhtml";
                break;
            case GESTIONCRHHPPCUMA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/Lista.xhtml";
                break;
            case GESTIONMODELCUMA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/Lista.xhtml";
                break;
            case GESTIONMODHHPP:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/Lista.xhtml";
                break;
            case GESTIONVERCASAS:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/Lista.xhtml";
                break;
            case GESTIONVIAINTERNET:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/Lista.xhtml";
                break;
            case GESTIONCAMBIOESTRATO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/Lista.xhtml";
                break;
            case ESTADOSOLICITUDVT:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/solicitudes/estadoSolicitud/estadoSolicitudHhpp.jsf?faces-redirect=true";
                break;

            case VETONODOS:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/vetoNodos/vetoNodosVt/vetoNodosVt";
                break;
            case MODELOHHPP:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/moduloHhpp/consultaHhpp";
                break;
            case ADMINISTRADORVT:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Parametros/Lista.xhtml";
                break;
            case REPORTESVT:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/VerReporte.xhtml";
                break;
            case CIERREMASIVOVT:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/Solicitudes/CerrarMasivamente.xhtml?faces-redirect=true";
                break;
            case ADMINNODO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/index.xhtml";
                break;
            case ADMINDIRECCIONES:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/index.xhtml";
                break;
            case ADMINCIUDADES:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/direcciones/geografico-politico.xhtml";
                break;
            case OPERACIONESCAMBESTRATOMASIVO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/index.xhtml";
                break;
            case OPERACIONESELIMINARMASIVO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/Migracion/index.xhtml";
                break;
            case OPERACIONESCREARREPORTE:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/procesoMasivo/solicitar_reporte.xhtml";
                break;
            case OPERACIONESCARGUEMASIVO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/procesoMasivo/cargar_reporte.xhtml";
                break;
            case OPERACIONESINHABILITARMASIVO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/procesoMasivo/inhabilitarHhpp.xhtml";
                break;
            case GESTIONHHPPOT:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/otHhpp/otHhppList.xhtml";
                break;
            case CONFIGURACIONHHPPOT:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/VT/tipoOtHhpp/tipoOtHhppList.xhtml";
                break;
            case GENERARPREFICHA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/ficha/GenerarPreFicha.xhtml";
                break;
            case VALIDARPREFICHA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/ficha/validarPreFicha.xhtml";
                break;
            case CREARPREFICHA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/ficha/crearPreFicha.xhtml";
                break;
            case CARGUEFICHA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/ficha/cargueFichas.xhtml";
                break;
            case GENERARFICHA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/ficha/generarFicha.xhtml";
                break;
            case VALIDARFICHA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/ficha/validarFicha.xhtml";
                break;
            case ACTUALIZARFICHA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/ficha/actualizarFicha.xhtml";
                break;
            case HISTORICOEXPORTFICHAS:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/ficha/historicoExportFichas.xhtml";
                break;
            case LOGACTUALIZABATCH:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/ficha/logActualizaBatch.xhtml";
                break;
            case PARAMETROSCORREO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/ficha/parametrosCorreoFichas.xhtml";
                break;
            case FRAUDESUNUAUNO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/hhpp/fraudes/marcacionunoauno.xhtml";
                session.setAttribute("tipofraude", "marcar");
                break;
            case FRAUDESUNUAUNODESM:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/hhpp/fraudes/marcacionunoauno.xhtml";
                session.setAttribute("tipofraude", "desmarcar");
                break;
            case DESCARGUEMARCADODF:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/hhpp/fraudes/descargueMarcadoDF.xhtml";
                session.setAttribute("tipoDescargue", "marcado");
                break;
            case DESCARGUEDESMARCADODF:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/hhpp/fraudes/descargueMarcadoDF.xhtml";
                session.setAttribute("tipoDescargue", "desmarcado");
                break;
            case CARGUEMARCADODF:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/hhpp/procesoMasivo/cargue_reporte_marcado.xhtml";
                break;
            case CARGUEDESMARCADODF:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/hhpp/procesoMasivo/cargue_reporte_desmarcado.xhtml";
                break;
            case MODELOOVERVIEW:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/coberturas/consultaCoberturasV1.xhtml";
                break;
            case MODELOADMPERFIL:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/coberturas/admPerfilFact.xhtml";
                break;
            case MODELOOVERVIEWREPORTE:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/coberturas/reporteFactibilidad.xhtml";
                break;
            case NODCUAGESSOL:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/solicitudes/nodoCuadranteGenSol.jsf";
                break;
            //Si crea alguna opcion nueva agregar el metodo bloquearDesbloquearOrden();
            default:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/";
                break;
        }
        return formTabSeleccionado;
    }

    /**
     * Limpiar el bean. Inicializa el bean de cuenta matriz.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     */
    private void clearBeanCuentaMatriz() {
        CuentaMatrizBean cuentaMatrizBean = (CuentaMatrizBean) JSFUtil.getSessionBean(CuentaMatrizBean.class);
        if (cuentaMatrizBean != null) {
            cuentaMatrizBean.setCuentaMatriz(null);
        }
    }

    /**
     * Limpiar el bean. Limpia el bean de orden de trabajo.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     */
    private void clearBeaOrdenTrabajo() {
        OtMglBean otBean = (OtMglBean) JSFUtil.getSessionBean(OtMglBean.class);
        if (otBean != null) {
            otBean.setOrdenTrabajo(null);
        }
    }

    /**
     * Iniciar el timepo de inicio de la solcitud. Inicia el timepo de inicio de
     * la solcitud.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     */
    public void iniciarTiempoCreaSol() {
        session.setAttribute("id_Solicitud_CM", null);
        session.setAttribute("tiempoInicio", null);
        session.setAttribute("idSolicitudCM", null);
    }

    public void bloquearDesbloquearOrden() {

        try {

            OtMglBean otMglBean = (OtMglBean) JSFUtil.getSessionBean(OtMglBean.class);
            CmtOrdenTrabajoMgl orden = otMglBean.getOrdenTrabajo();
            if (orden != null && orden.getIdOt() != null) {
                orden = ordenTrabajoMglFacadeLocal.findOtById(orden.getIdOt());
                orden.setDisponibilidadGestion(null);
                ordenTrabajoMglFacadeLocal.actualizarOtCcmm(orden, usuarioSesion, perfil);
            }

            OtHhppMglSessionBean otHhppMglSessionBean
                    = JSFUtil.getSessionBean(OtHhppMglSessionBean.class);
            OtHhppMgl otHhppMgl = otHhppMglSessionBean.getOtHhppMglSeleccionado();

            if (otHhppMgl != null && otHhppMgl.getOtHhppId() != null) {
                otHhppMgl = otHhppMglFacadeLocal.findOtByIdOt(otHhppMgl.getOtHhppId());
                otHhppMgl.setDisponibilidadGestion(null);
                otHhppMglFacadeLocal.update(otHhppMgl);
            }

        } catch (ApplicationException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
            LOGGER.error(ex.getMessage());
        }
    }

    public boolean isRolMenuHomePass() {
        return validaciones.get("rolMenuHomePassed");
    }

    public boolean isRolMenuHpSolicitar() {
        return isRolMenuVtSolCrearSolicitud();
    }

    public boolean isRolMenuVtSolCrearSolicitud() {
        return validaciones.get("rolMenuVisitaTecnicaSolCrearSolicitud");
    }

    public boolean isRolMenuHpGestionarSolicitud() {
        return isRolMenuVtGestionSolicitud();
    }

    public boolean isRolMenuHpAdministradorDireccciones() {
        return isRolMenuVtAdministradorDirecciones();
    }

    public boolean isRolMenuHhpp() {
        return isRolMenuHomePass();
    }

    public boolean isRolMenuHpOperacionesHhpp() {
        return isRolMenuVtOperacionesCrearReporte();
    }

    public boolean isRolMenuHpGestionOt() {
        return isRolMenuHhppGestionOt();
    }
    
    public boolean isRolMenuVtNodoCuadrante() {
        return validaciones.get("rolMenuVtNodoCuadrante");
    }

    public boolean isRolMenuHpOrdenesTrabajo() {
        return validaciones.get("rolMenuOrdenesTrabajoHHPP");
    }

    public boolean isRolMenuVtSolEdificioConjunto() {
        return validaciones.get("rolMenuVisitaTecnicaSolEdificioConjunto");
    }

    public boolean isRolMenuVtSolReplanteamiento() {
        return validaciones.get("rolMenuVisitaTecnicaSolReplanteamiento");
    }

    public boolean isRolMenuVtSolEnCasa() {
        return validaciones.get("rolMenuVisitaTecnicaSolEnCasa");
    }

    public boolean isRolMenuVtSolCreacionCuentaMatriz() {
        return validaciones.get("rolMenuVtSolCreacionCuentaMatriz");
    }

    public boolean isRolMenuVtSolCreacionHHPPUnidireccional() {
        return validaciones.get("rolMenuVtSolCreacionHHPPUnidireccional");
    }

    public boolean isRolMenuVtSolCreacionHHPPEnCuentaMatriz() {
        return validaciones.get("rolMenuVtSolCreacionHHPPEnCuentaMatriz");
    }

    public boolean isRolMenuVtSolCambioEstrato() {
        return validaciones.get("rolMenuVtSolCambioEstrato");
    }

    public boolean isRolMenuVtSolModificarEliminarCuentaMatriz() {
        return validaciones.get("rolMenuVtSolModificarEliminarCuentaMatriz");
    }

    public boolean isRolMenuVtSolModificarHHPP() {
        return validaciones.get("rolMenuVtSolModificarHHPP");
    }

    public boolean isRolMenuVtSolVerificarCasa() {
        return validaciones.get("rolMenuVtSolVerificarCasa");
    }

    public boolean isRolMenuVtSolViabilidadInternet() {
        return validaciones.get("rolMenuVtSolViabilidadInternet");
    }

    public boolean isRolMenuVtGestionSolicitud() {
        return validaciones.get("rolMenuVtGestionSolicitud");
    }
    
    public boolean isRolMenuVtGestionCambiosEstrato() {
        return validaciones.get("rolMenuVtGestionCambiosEstrato");
    }

    public boolean isRolMenuVtGestionEdificioConjunto() {
        return validaciones.get("rolMenuVtGestionEdificioConjunto");
    }

    public boolean isRolMenuVtGestionReplanteamiento() {
        return validaciones.get("rolMenuVtGestionReplanteamiento");
    }

    public boolean isRolMenuVtGestionEnCasa() {
        return validaciones.get("rolMenuVtGestionEnCasa");
    }

    public boolean isRolMenuVtGestionCreacionCuentaMatriz() {
        return validaciones.get("rolMenuVtGestionCreacionCuentaMatriz");
    }

    public boolean isRolMenuVtGestionCreacionHHPPUni() {
        return validaciones.get("rolMenuVtGestionCreacionHHPPUni");
    }

    public boolean isRolMenuVtGestionCreacionHHPPCuentaMatriz() {
        return validaciones.get("rolMenuVtGestionCreacionHHPPCuentaMatriz");
    }

    public boolean isRolMenuVtGestionModificarEliminarCuentaMatriz() {
        return validaciones.get("rolMenuVtGestionModificarEliminarCuentaMatriz");
    }

    public boolean isRolMenuVtGestionModificarHHPP() {
        return validaciones.get("rolMenuVtGestionModificarHHPP");
    }

    public boolean isRolMenuVtGestionVerificacionCasas() {
        return validaciones.get("rolMenuVtGestionVerificacionCasas");
    }

    public boolean isRolMenuVtGestionViabilidadInternet() {
        return validaciones.get("rolMenuVtGestionViabilidadInternet");
    }

    public boolean isRolMenuVtVetoNodos() {
        return validaciones.get("rolMenuVtVetoNodos");
    }

    public boolean isRolMenuVtModeloHHPP() {
        return validaciones.get("rolMenuVtModeloHHPP");
    }

    public boolean isRolMenuHHPPBusquedaHHPP() {
        return validaciones.get("rolMenuHHPPBusquedaHHPP");
    }

    public boolean isRolMenuVtEstadoSolicitud() {
        return validaciones.get("rolMenuVtEstadoSolicitud");
    }

    public boolean isRolMenuVtAdministrador() {
        return validaciones.get("rolMenuVtAdministrador");
    }

    public boolean isRolMenuVtReporteSolicitudes() {
        return validaciones.get("rolMenuVtReporteSolicitudes");
    }

    public boolean isRolMenuVtCierreMasivo() {
        return validaciones.get("rolMenuVtCierreMasivo");
    }

    public boolean isRolMenuVtAdministradorNodo() {
        return validaciones.get("rolMenuVtAdministradorNodo");
    }

    public boolean isRolMenuVtOrdenesTrabajoHhpp() {
        return validaciones.get("rolMenuVtOrdenesTrabajoHhpp");
    }

    public boolean isRolMenuVtOtPendientes() {
        return validaciones.get("rolMenuVtAdministradorNodo");
    }

    public boolean isRolMenuVtAdministradorHHPP() {
        return validaciones.get("rolMenuVtAdministradorHHPP");
    }

    public boolean isRolMenuVtAdministradorDirecciones() {
        return validaciones.get("rolMenuAdministradorDirecciones");
    }

    public boolean isRolMenuVtAdministradorCiudades() {
        return validaciones.get("rolMenuVtAdministradorCiudades");
    }

    public boolean isRolMenuVtOperacionesCambioEstratoMasivo() {
        return validaciones.get("rolMenuVtOperacionesCambioEstratoMasivo");
    }

    public boolean isRolMenuVtOperacionesEliminarMasivoHHPP() {
        return validaciones.get("rolMenuVtOperacionesEliminarMasivoHHPP");
    }

    public boolean isRolMenuVtOperacionesCrearReporte() {
        return validaciones.get("rolMenuVtOperacionesCrearReporte");
    }

    public boolean isRolMenuVtOperacionesCargueMasivo() {
        return validaciones.get("rolMenuVtOperacionesCargueMasivo");
    }

    public boolean isRolMenuVtOperacionesInhabilitarMasivo() {
        return validaciones.get("rolMenuVtOperacionesInhabilitarMasivo");
    }

    public boolean isRolMenuVtCargueFicha() throws ApplicationException {
        boolean flagParam = validaciones.get("rolMenuVtGenerarPreficha");
        if(flagParam){
           
            flagParam = Boolean.valueOf(parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.MENU_CARGUE_FICHAS_VALIDATION_ENABLED)
                    .iterator().next().getParValor());
        }
        return flagParam;
    }
    
    public boolean isRolMenuVtGenerarPreficha() throws ApplicationException {
        return validaciones.get("rolMenuVtGenerarPreficha");
    }

    public boolean isRolMenuVtValidarPreficha() {
        return validaciones.get("rolMenuVtValidarPreficha");
    }

    public boolean isRolMenuVtCrearPreficha() {
        return validaciones.get("rolMenuVtCrearPreficha");
    }

    public boolean isRolMenuVtCargueFichas() {
        return validaciones.get("rolMenuVtCargueFichas");
    }

    public boolean isRolMenuVtGenerarFicha() {
        return validaciones.get("rolMenuVtGenerarFicha");
    }

    public boolean isRolMenuVtValidarFicha() {
        return validaciones.get("rolMenuVtValidarFicha");
    }

    public boolean isRolMenuVtActualizarFicha() {
        return validaciones.get("rolMenuVtActualizarFicha");
    }

    public boolean isRolMenuVtLogActualizarNap() {
        return validaciones.get("rolMenuVtLogActualizarNap");
    }

    public boolean isRolMenuHhppGestionOt() {
        return validaciones.get("rolMenuHhppGestionOt");
    }

    public boolean isRolMenuFraudes() {
        return validaciones.get("rolMenuFraudes");
    }

    public boolean isRolMenuFraudesMarcaUnoUno() {
        return validaciones.get("rolMenuFraudesMarcaUnoUno");
    }

    public boolean isRolMenuFraudesDesmarcaUnoUno() {
        return validaciones.get("rolMenuFraudesDesmarcaUnoUno");
    }

    public boolean isRolMenuDescargueMar() {
        return validaciones.get("rolMenuDescargueMar");
    }

    public boolean isRolMenuDescargueDes() {
        return validaciones.get("rolMenuDescargueMar");
    }

    public boolean isRolMenuCargueMar() {
        return validaciones.get("rolMenuCargueMar");
    }

    public boolean isRolMenuCargueDes() {
        return validaciones.get("rolMenuCargueDes");
    }

    public boolean isRolMenuVtModeloOverview() {
        return validaciones.get("rolMenuVtModeloOverview");
    }

    public boolean isRolMenuAdmPerfilFact() {
        return validaciones.get("rolMenuAdmPerfilFact");
    }

    public boolean isRolMenuReporteFact() {
        return validaciones.get("rolMenuReporteFact");
    }

    /**
     * Verifica el rol para visualizar el item Fichas en el menú
     * @return {@code boolean} true si el usuario tiene el rol, de lo contrario false
     */
    public boolean isRolFichas() {
        return validaciones.get("rolMenuFichas ");
    }
}
