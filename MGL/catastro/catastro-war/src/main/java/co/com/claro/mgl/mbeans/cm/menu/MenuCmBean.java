package co.com.claro.mgl.mbeans.cm.menu;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.OtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOrdenTrabajoMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOpcionesRolMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.mbeans.cm.CuentaMatrizBean;
import co.com.claro.mgl.mbeans.cm.EncabezadoSolicitudModificacionCMBean;
import co.com.claro.mgl.mbeans.cm.ot.OtMglBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.mbeans.vt.otHhpp.OtHhppMglSessionBean;
import co.com.claro.mgl.utils.MenuEnum;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Administrar la funcionalidad de reglas de proyecto. ManageBean que administra
 * los menus de la funcionalidad de reglas de proyecto.
 *
 * @author ortizjaf
 * @version 1.0 revision 16/05/2017.
 */
@ManagedBean(name = "menuCmBean")
@SessionScoped
public class MenuCmBean {

    /**
     * Constante para manejar el log de la clase.
     */
    private static final Logger LOGGER = LogManager.getLogger(MenuCmBean.class);
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
    SecurityLogin securityLogin;
    private int perfil = 0;
    private String usuarioSesion;

    /**
     * Constructor de la clase. Construye la clase y obtiene el usuario y su
     * perfil.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     */
    public MenuCmBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin() && !response.isCommitted()) {
                response.sendRedirect(securityLogin.redireccionarLogin());
            }

            usuarioSesion = securityLogin.getLoginUser();
            perfil =  Optional.of(securityLogin).map(SecurityLogin::getPerfilUsuario).orElse(0);
        } catch (IOException ex) {
            String msn2 = "Error al cargar Bean de Visita Tecnica:...." + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            LOGGER.error(msn2);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en MenuCmBean() " + e.getMessage(), e, LOGGER);
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
            String msn2 = "Error al cargar los Menu por Rol: ".concat(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            LOGGER.error(msn2);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en MenuCmBean:  cargaPermisosMenu() " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * M&eacute;todo para inicializar el mapa y adicionar los campos booleanos
     * para validar la visualizaci&oacute;n de los menus. Por defecto se
     * inicializan en false.
     */
    private void addValidaciones() {
        validaciones = new HashMap<String, Boolean>();
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
        if (Objects.nonNull(opcion) && StringUtils.isNotEmpty(opcion.getNombreOpcion())
                && StringUtils.isNotEmpty(opcion.getRol())) {
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
            String msn2 = String.format("Error al Validar el Rol %s %s", rol, e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            LOGGER.error(msn2);
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
        ConstantsCM.ITEM_MENU Seleccionado = ConstantsCM.ITEM_MENU.valueOf(caseMenu);
        switch (Seleccionado) {
            case TBBASICA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/tablasBasicasMglListView?faces-redirect=true";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case ESTADOINTERNOGA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/estadoInternoGA-detalle";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case RELACIONESTADOCMTIPOGA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/relacionEstadoCmTipoGaView";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case COMPADMIN:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/otras-tablas/companias-administracion";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case COMPCONSTRUCCION:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/otras-tablas/constructoras";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case COMPASCENSORES:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/otras-tablas/ascensoresMglListView";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case GESTIONOT:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/ot/otGestionar";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case GENERAROTEJECUCION:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/ot/otEjecucionGestionar";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case CREAROTVT:
                bloquearDesbloquearOrden();
                OtMglBean otMglBean = JSFUtil.getSessionBean(OtMglBean.class);
                return otMglBean.ingresar();
            case CONSULTACM:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/cuentas-matrices?faces-redirect=true";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case GESTIONSOLICITUDCM:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/solicitudes/gestionCMSolicitudes";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                iniciarTiempoCreaSol();
                break;
            case CREACIONSOLICITUDCREACM:
                bloquearDesbloquearOrden();
                EncabezadoSolicitudModificacionCMBean encSolCreaCm = JSFUtil.getSessionBean(EncabezadoSolicitudModificacionCMBean.class);
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                formTabSeleccionado = encSolCreaCm.ingresarSolicitudCrearCm();
                break;
            case CREACIONSOLICITUDMODCM:
                bloquearDesbloquearOrden();
                EncabezadoSolicitudModificacionCMBean encSolModCm = JSFUtil.getSessionBean(EncabezadoSolicitudModificacionCMBean.class);
                formTabSeleccionado = encSolModCm.ingresarSolicitudModificacionCm();
                break;
            case TBCOMPETENCIA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/competenciaTipo";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                iniciarTiempoCreaSol();
                break;
            case CREACIONSOLICITUDVT:
                bloquearDesbloquearOrden();
                EncabezadoSolicitudModificacionCMBean encabezadoSolicitud = JSFUtil.getSessionBean(EncabezadoSolicitudModificacionCMBean.class);
                formTabSeleccionado = encabezadoSolicitud.ingresarSolicitudVt();
                break;
            case CREACIONSOLICITUHHPP:
                bloquearDesbloquearOrden();
                EncabezadoSolicitudModificacionCMBean solicitudHhppCM = JSFUtil.getSessionBean(EncabezadoSolicitudModificacionCMBean.class);
                formTabSeleccionado = solicitudHhppCM.ingresarSolicitudHhpp();
                break;
            case GESTIONSOLICITUD:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/solicitudes/general/solicitudesGestionar";
                break;
            //Autor: Victor Bocanegra
            //Nuevo link para la consulta del estado de las solicitudes      
            case ESTADOSOLICITUD:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/solicitudes/consulta/consultarEstadoSolicitud?faces-redirect=true";
                break;
            case VIABILIDADHHPP:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/viabilidad/viabilidadHhpp";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case VIABILIDADREGLAVALIDACION:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/viabilidad/reglaValidacion";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case VIABILIDADTIPOVALIDACION:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/viabilidad/tipoValidacionListView";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case VIABILIDADVALIDACIONPROYECTO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/viabilidad/validacionProyectoListView";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case VIABILIDADSEGESTRATO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/viabilidad/viabilidadSegmentoEstrato";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case VIABILIDADMENSAJEREGLA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/viabilidad/mensajeViabilidad";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case CRUDTBLBASICA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/tablatipoBasicaMglListView?faces-redirect=true";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case MTOTABLAS:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/mantenimientoTablas?faces-redirect=true";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
                 //Autor: Jimmy Garcia Ospina
                //Date: 13-09-2021
                //Nueva opción para marcaciónes para creación de Home Passed Virtuales con Razon y Subrazón (R1 y R2)
            case MARCACREAHHPPVIRTR1R2MTOTABLAS:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/marcacionesCreaHHPPVirtualR1R2";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case SLAOT:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/slaOt";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case SLASOL:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/slaSol";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            //Autor: Victor Bocanegra
            //Nuevo link para la gestion de nodos    CARGUEMASIVONODO
            case NODO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/direcciones/nodoMglListView";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case REGLAFLUJOOT:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/reglaFlujoOt";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case ESTXFLUJO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/estadoxflujoOt";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case SOLICITUDELIMINAIONCM:
                bloquearDesbloquearOrden();
                EncabezadoSolicitudModificacionCMBean encSoleliminar = JSFUtil.getSessionBean(EncabezadoSolicitudModificacionCMBean.class);
                formTabSeleccionado = encSoleliminar.ingresarEliminarCm();
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case ESTADOXTECNOLOGIA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/estadoxTecnologia";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case REPORTESOLICITUDES:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/reporteSolicitud/reporteSolCM";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case REPORTEPENETRACION:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/reporteSolicitud/reportePenetracion";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case CARGUEMASIVOMODIFICACION:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/utilidades/cargueMasivoModificacion";
                break;
            case MASIVOMODIFICACION:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/utilidades/masivoModificacion";
                break;
            case REGIONAL:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/codigosDane/regionalMglListView";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case COMUNIDADES:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/codigosDane/comunidadesMglViewList";
                break;
            case PLANTA:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/mantenimientoPlantas/plantaMglListView";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case REPORTECOMPROMISOCOMERCIAL:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/reporteSolicitud/reporteCompComercial";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case PARAMCOBERTURAENTERGAS:
                //Fabian Castro FCP Menu para Parametrizacion de Operadores . Cobertura Entregas
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/coberturaentregas/coberturasEntregas";
                LOGGER.error("FCP");
                break;
                //Autor: Victor Bocanegra
                //Nuevo link para la gestion de parametros      
            case GESTIONPARAMETROS:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/direcciones/parametrosMglListView";
                break;
            //Autor: Juan David Hernandez
            case ESTADOINTEXT:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/estadoInternoExternoOt/estadoInternoExternoOt";
                break;
            case ESTADOACTUALOTCM:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/reportesCCMM/estadoActualOtCm";
                break;
            case HISTORICOORDENESCM:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/reportesCCMM/historicoOtCm";
                break;
            case ESTADOACTUALOTDIR:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/reportesCCMM/estadoActualOtDir";
                break;
            case HISTORICOOTDIR:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/reportesCCMM/historicoOtDir";
                break;
            case SUBTIPOSOTCMVTTEC:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/subtiposOtVtTec";
                break;
            //Autor: Victor Bocanegra
            //Nuevo link para la gestion de arrendatarios      
            case ARRENDATARIO:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/arrendatario/arrendatarioMglAdminConsulta";
                break;
            //Autor: Victor Bocanegra
            //Nuevo link para la gestion de SLA de ejecucion      
            case SLAEJECUCION:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/sla/slaEjecucionMglAdminConsulta";
                break;
            case HOMOLOGACIONRAZONES:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/homologacion/homologacionRazonesMglAdminConsulta";
                break;
            case ACTESTCOMER:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/direcciones/actEstructComercial";
                break;
            case CARGUEMASIVOS:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/direcciones/carguemasivos";
                clearBeaOrdenTrabajo();
                clearBeanCuentaMatriz();
                break;
            case CREARREGLAESTADOS:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/crearReglaEstados.xhtml";
                break;
            default:
                bloquearDesbloquearOrden();
                formTabSeleccionado = "/view/MGL/CM/cuentas-matrices";
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
        CuentaMatrizBean cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
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
    /**
     * ocultar los menus padres para las opciones de hijos sin permisos de rol NIVEL 2
     * @return 
     */
    public boolean isRolMenuCuentasMatrices(){
        return isRolMenuCreaSolCreacionCm() ||
                isRolMenuAdminBuscaCm() ||
                isRolMenuCreaSolModificacionCm() ||
                isRolMenuCreaSolVt() ||
                isRolMenuElimarCM() ||
                isRolMenuCreaSolCreacionHhppCm() ||
                isRolMenuCreaSolModificacionHhppCm() ||
                isRolMenuEstadoSolicitud();
    }
    public boolean isRolMenuCuentaMatrices(){
        return isRolMenuAdministracion()  || isRolMenuCuentaMatriz();
    }
    public boolean isRolMenuAdministracion(){
        return validaciones.get("rolMenuAdministracion");
    }
    
    public boolean isRolMenuMantenimientoTablas() {
        return validaciones.get("rolMenuMantenimientoTablas");
    }

    public boolean isRolMenuParametrizacionProveedores(){
        return validaciones.get("rolMenuParametrizacionProveedores");
    }

    public boolean isRolMenuEstadosCombinados(){
        return validaciones.get("rolMenuEstadosCombinados");
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
    
    //<editor-fold defaultstate="collapsed" desc="Getters && Setters">
    /**
     * Obtener el rol del menu basicas. Obtiene el rol del menu basicas.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol del menu basicas.
     */
    public boolean isRolMenuTbBasicas() {
        return validaciones.get("rolMenuTbBasicas");
    }

    /**
     * Obtener el rol del menu competencias. Obtiene el rol del menu
     * competencias.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol del menu competencias.
     */
    public boolean isRolMenuTbCompetencias() {
        return validaciones.get("rolMenuTbCompetencias");
    }

    /**
     * espinosadiea menu padre de gestion avanzada no se debe mostrar si los hijos tampoco aparecen
     * @return 
     */
    public boolean isRolMenuGestionAvanzada() {
        return isRolMenuEstadoInternoGa() || isRolMenuRelacionCmGa() || 
                isRolMenuEstadoTecnologia();
    }
    /**
     * Obtener el rol del menu estado interno. Obtiene el rol del menu estado
     * interno.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol del menu estado interno.
     */
    public boolean isRolMenuEstadoInternoGa() {
        return validaciones.get("rolMenuEstadoInternoGa");
    }

    /**
     * Obtener el rol del menu relacion. Obtiene el rol del menu relacion.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol del menu Relacion.
     */
    public boolean isRolMenuRelacionCmGa() {
        return validaciones.get("rolMenuRelacionCmGa");
    }
    
    public boolean isRolMenuCompania() {
        return isRolMenuCompaniaAdmin() || isRolMenuCompaniaConstructora() ||  
                isRolMenuCompaniaAscensonres();
    }
    /**
     * Obtener el rol del menu compania administrativa. Obtiene el rol del menu
     * compania administrativa.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol del menu compania administrativa.
     */
    public boolean isRolMenuCompaniaAdmin() {
        return validaciones.get("rolMenuCompaniaAdmin");
    }
    /**
     * Obtener el rol del menu compania compania contructora. Obtiene el rol del
     * menu compania compania contructora.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol del menu compania compania contructora.
     */
    public boolean isRolMenuCompaniaConstructora() {
        return validaciones.get("rolMenuCompaniaConstructora");
    }

    /**
     * Obtener el rol del menu compania compania asesores. Obtiene el rol del
     * menu compania compania asesores.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol del menu compania compania contructora.
     */
    public boolean isRolMenuCompaniaAscensonres() {
        return validaciones.get("rolMenuCompaniaAscensonres");
    }
    /**
     * espinosadiea padre de los menu ordenes de trabajo implementacion visualizacion por roll
     * @return 
     */
    public boolean isRolMenuOrdenesTrabajo() {
        return validaciones.get("rolMenuOrdenesTrabajo");
    }
    /**
     * espinosadiea padre de los menu ordenes de trabajo implementacion visualizacion por roll
     * @return 
     */
    public boolean isRolMenuGestionOT() {
        return isRolMenuGestionOt() || isRolMenuEstadoSolicitud();
    }
    /**
     * espinosadiea padre de los menu ordenes de trabajo implementacion visualizacion por roll
     * @return 
     */
    public boolean isRolCreaOT() {
        return isRolMenuCreaOtVt();
    }
    /**
     * espinosadiea padre de los menu ordenes de trabajo implementacion visualizacion por roll
     * @return 
     */
    public boolean isRolAdministraOT() {
        return isRolMenuEstadosxFlujoOt() || isRolMenuReglaFlujoOt();
    }
    /**
     * Obtener el rol del menu compania gestion ot. Obtiene el rol del menu
     * compania gestion ot.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol del menu compania gestion ot.
     */
    public boolean isRolMenuGestionOt() {
        return validaciones.get("rolMenuGestionOt");
    }

    /**
     * Obtener el rol del menu crear ot. Obtiene el rol del menu crear ot.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol del menu crear ot.
     */
    public boolean isRolMenuCreaOtVt() {
        return validaciones.get("rolMenuCreaOtVt");
    }
    
    public boolean isRolMenuCuentaMatriz() {
        return isRolMenuCMAdministrar() || isRolMenuCMSolicitudes() || 
                isRolMenuCMGestionSolicitudes() || isRolMenuCMReportes() ||
                isRolMenuCMEstadoSolicitud() || isRolMenuCMModificaciones();
    }
    
    public boolean isRolMenuCMModificaciones(){
        return isRolMenuCmModificacionMasiva() || isRolMenuCmCargueMasivo();
    }
    public boolean isRolMenuCMEstadoSolicitud (){
        return isRolMenuEstadoSolicitud();
    }
    
    public boolean isRolMenuCMReportes(){
        return isRolMenuReportesSolicitud();
    }
    public boolean isRolMenuCMAdministrar() {
        return isRolMenuAdminBuscaCm();
    }
    public boolean isRolMenuCMSolicitudes(){
        return isRolMenuCreaSolCreacionCm() || isRolMenuCreaSolModificacionCm() || 
                isRolMenuCreaSolVt() || isRolMenuElimarCM() || isRolMenuCreaSolCreacionHhppCm()||
                isRolMenuCreaSolModificacionHhppCm() ;
    }
    public boolean isRolMenuCMGestionSolicitudes(){
        return isRolMenuGestionSolicitud();
    }
    /**
     * Obtener el rol de busqueda admin. Obtiene el rol debusqueda admin.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol de busqueda admin.
     */
    public boolean isRolMenuAdminBuscaCm() {
        return validaciones.get("rolMenuAdminBuscaCm");
    }

    /**
     * Obtener el rol de solicitud creacion. Obtiene el rol de solicitud
     * creacion.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol de solicitud creacion.
     */
    public boolean isRolMenuCreaSolCreacionCm() {
        return validaciones.get("rolMenuCreaSolCreacionCm");
    }

    /**
     * Obtener el rol de actualizar solicitud. Obtiene el rol de actualizar
     * solicitud.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol de actualizar solicitud.
     */
    public boolean isRolMenuCreaSolModificacionCm() {
        return validaciones.get("rolMenuCreaSolModificacionCm");
    }

    /**
     * Obtener el rol de crear solicitud vt. Obtiene el rol de crear solicitud
     * vt.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol de crear solicitud vt.
     */
    public boolean isRolMenuCreaSolVt() {
        return validaciones.get("rolMenuCreaSolVt");
    }

    /**
     * Obtener el rol de crear solicitud hhpp. Obtiene el rol de crear solicitud
     * hhpp.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol de crear solicitud hhpp.
     */
    public boolean isRolMenuCreaSolCreacionHhppCm() {
        return validaciones.get("rolMenuCreaSolCreacionHhppCm");
    }

    /**
     * Obtener el rol de modificar solicitud hhpp. Obtiene el rol de modificar
     * solicitud hhpp.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol de modificar solicitud hhpp.
     */
    public boolean isRolMenuCreaSolModificacionHhppCm() {
        return validaciones.get("rolMenuCreaSolModificacionHhppCm");
    }

    /**
     * Obtener el rol de gestion solicitud. Obtiene el rol de gestion solicitud.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol de gestion solicitud.
     */
    public boolean isRolMenuGestionSolicitud() {
        return validaciones.get("rolMenuGestionSolicitud");
    }
    /**
     * espinosadiea implementacion de padress para mostar menus
     * @return 
     */
    public boolean isRolMenuViabilidad() {
        return validaciones.get("rolMenuViablilidad");
    }
    /**
     * espinosadiea implementacion de padress para mostar menus
     * @return 
     */
    public boolean isRolMenuMatrizViabilidad() {
        return validaciones.get("rolMenuMatrizViablilidad");
    }
    
    
       
    public boolean isRolMenuReporteOtCm() {
        return isRolOtCcmm() || isRolHistOtCm() || 
                isRolOtDir() || isRolHistOtDir();
    }
    /**
     * Obtener el rol de viabilidad hhpp. Obtiene el rol de viabilidad hhpp.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol de viabilidad hhpp.
     */
    public boolean isRolMenuViabilidadHhpp() {
        return validaciones.get("rolMenuViabilidadHhpp");
    }

    /**
     * Obtener el rol de viabilidad estrato. Obtiene el rol de viabilidad
     * estrato.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol de viabilidad estrato.
     */
    public boolean isRolMenuViabilidadEstrato() {
        return validaciones.get("rolMenuViabilidadEstrato");
    }

    /**
     * Obtener el rol de detalle general. Obtiene el rol de detalle general.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol de detalle general.
     */
    public boolean isRolDetalleCmPtGeneral() {
        return validaciones.get("rolDetalleCmPtGeneral");
    }

    /**
     * Obtener el rol de detalle hhpp. Obtiene el rol de detalle hhpp.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol de detalle hhpp.
     */
    public boolean isRolDetalleCmPtHhpp() {
        return validaciones.get("rolDetalleCmPtHhpp");
    }

    /**
     * Obtener el rol de detalle compania. Obtiene el rol de detalle compania.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol de detalle compania.
     */
    public boolean isRolDetalleCmPtCompania() {
        return validaciones.get("rolDetalleCmPtCompania");
    }

    /**
     * Obtener el rol de detalle pt ot. Obtiene el rol de detalle pt ot.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol de detalle pt ot.
     */
    public boolean isRolDetalleCmPtOt() {
        return validaciones.get("rolDetalleCmPtOt");
    }

    /**
     * Obtener el rol de detalle notas. Obtiene el rol de detalle notas.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol de detalle notas.
     */
    public boolean isRolDetalleCmPtNotas() {
        return validaciones.get("rolDetalleCmPtNotas");
    }

    /**
     * Obtener el rol de detalle horario. Obtiene el rol de detalle horario.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol de detalle horario.
     */
    public boolean isRolDetalleCmPtHorario() {
        return validaciones.get("rolDetalleCmPtHorario");
    }

    /**
     * Obtener el rol de detalle competencias. Obtiene el rol de detalle
     * competencias.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol de detalle competencias.
     */
    public boolean isRolDetalleCmPtCompetencias() {
        return validaciones.get("rolDetalleCmPtCompetencias");
    }

    /**
     * Obtener el rol de detalle direcciones. Obtiene el rol de detalle
     * direcciones.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol de detalle direcciones.
     */
    public boolean isRolDetalleCmPtDirecciones() {
        return validaciones.get("rolDetalleCmPtDirecciones");
    }

    /**
     * Obtener el rol de detalle black list. Obtiene el rol de detalle black
     * list.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @return el rol de detalle black list.
     */
    public boolean isRolDetalleCmPtBlackList() {
        return validaciones.get("rolDetalleCmPtBlackList");
    }

    /**
     * Obtener el rol de detalle Penetracion. Obtiene el rol de detalle
     * Penetracion.
     *
     * @author Lenis Cardenas.
     * @version 1.0 revision 14/06/2017.
     * @return el rol de Penetracion.
     */
    public boolean isRolDetalleCmPtPenetracion() {
        return validaciones.get("rolDetalleCmPtPenetracion");
    }

    /**
     * Obtener el rol de detalle Inventario. Obtiene el rol de detalle
     * Inventario.
     *
     * @author Lenis Cardenas
     * @version 1.0 revision 14/06/2017.
     * @return el rol de detalle Inventario
     */
    public boolean isRolDetalleCmPtInventario() {
        return validaciones.get("rolDetalleCmPtInventario");
    }

    /**
     * Obtener el rol de detalle Info Tecnica. Obtiene el rol de detalle
     * Penetracion.
     *
     * @author Lenis Carndeas
     * @version 1.0 revision 16/05/2017.
     * @return el rol de detalle black list.
     */
    public boolean isRolDetalleCmPtInfoTecnica() {
        return validaciones.get("rolDetalleCmPtInfoTecnica");
    }

    /**
     * Obtener el rol de detalle Proyectos.
     * .
     *
     * @author Johan Gómez
     * @version 1.0 revision 17/12/2024.
     * @return el rol de proyectos.
     */
    public boolean isRolDetalleCmProyectos() {
        return validaciones.get("rolDetalleCmProyectos");
    }
    /**
     * Obtener el rol de detalle Info Tecnica. Obtiene el rol de detalle
     * Penetracion.
     *
     * @author Lenis Carndeas
     * @version 1.0 revision 16/05/2017.
     * @return el rol de detalle black list.
     */
    public boolean isRolDetalleCmPtBitacora() {
        return validaciones.get("rolDetalleCmPtBitacora");
    }

    /**
     * Obtener el rol de detalle Info Tecnica. Obtiene el rol de detalle
     * Penetracion.
     *
     * @author Lenis Carndeas
     * @version 1.0 revision 16/05/2017.
     * @return el rol de detalle black list.
     */
    public boolean isRolDetalleCmPtCasos() {
        return validaciones.get("rolDetalleCmPtCasos");
    }

    /**
     * Obtener el rol de crud tablas basicas.
     *
     * @author Lenis Cardenas.
     * @version 1.0 revision 16/06/2017.
     * @return el rol crud tablas basicas.
     */
    public boolean isRolMenuCrudTbBasicas() {
        return validaciones.get("rolMenuCrudTbBasicas");
    }

    /**
     * Obtener el rol de mto tablas basicas.
     *
     * @author Lenis Cardenas.
     * @version 1.0 revision 16/06/2017.
     * @return el rol crud tablas basicas.
     */
    public boolean isRolMenuMtoTbTablabBasicas() {
        return validaciones.get("rolMenuMtoTbTablabBasicas");
    }

    public boolean isRolMenuEstadoSlaTipoOt() {
        return validaciones.get("rolMenuEstadoSlaTipoOt");
    }

    public boolean isRolMenuReglaFlujoOt() {
        return validaciones.get("rolMenuReglaFlujoOt");
    }

    public boolean isRolMenuSlaSolicitudOt() {
        return validaciones.get("rolMenuSlaSolicitudOt");
    }

    public boolean isRolMenuEstadosxFlujoOt() {
        return validaciones.get("rolMenuEstadosxFlujoOt");
    }

    public boolean isRolMenuEstadoTecnologia() {
        return validaciones.get("rolMenuEstadoTecnologia");
    }

    /**
     * Obtener el rol del menu CM Nodos.
     *
     * @author Victor Bocanegra Ortiz.
     * @version 1.0 revision 09/10/2017.
     * @return el rol del menu CM Nodos.
     */
    public boolean isRolMenuCmNodo() {
        return validaciones.get("rolMenuCmNodo");
    }

    public boolean isRolMenuCmActEstCom() {
        return validaciones.get("rolMenuCmActEstCom");
    }

    public boolean isRolMenuElimarCM() {
        return validaciones.get("rolMenuElimarCM");
    }

    /**
     * Obtener el rol de reportes de la solicitud .
     *
     * @author Lenis Cardenas.
     * @version 1.0 revision 13/10/2017.
     * @return el rol de reportes.
     */
    public boolean isRolMenuReportesSolicitud() {
        return validaciones.get("rolMenuCmReporteSolicitud");
    }

     public boolean isRolMenuReportesPenetracion() {
        return validaciones.get("rolMenuCmReportePenetracion");
    }
    
    /**
     * Obtener el rol de estado solicitud.
     *
     * @author Victor Bocanegra.
     * @version 1.0 revision 17/10/2017.
     * @return el rol de estado solicitud.
     */
    public boolean isRolMenuEstadoSolicitud() {
        return validaciones.get("rolMenuCmEstadoSolicitudes");
    }
    //</editor-fold>

    /**
     * Obtener el rol del menu CM Nodos.
     *
     * @author Jonathan Peña
     * @version 1.0 revision 13/06/2018.
     * @return el rol del menu CM Regional.
     */
    public boolean isRolMenuCmRegional() {
        return validaciones.get("rolMenuCmRegional");
    }

    /**
     * Obtener el rol del menu CM Plantas.
     *
     * @author Jonathan Peña
     * @version 1.0 revision 13/06/2018.
     * @return el rol del menu CM Regional.
     */
    public boolean isRolMenuCmPlanta() {
        return validaciones.get("rolMenuCmPlanta");
    }

    /**
     *
     * /**
     * Obtener el rol de generar Ot de Ejecucion.
     *
     * @author Victor Bocanegra.
     * @version 1.0 revision 10/06/2018.
     * @return el rol de generar Ot de Ejecucion.
     */
    public boolean isRolMenuCmGenerarOtEjecucion() {
        return validaciones.get("rolMenuCmGenerarOtEjecucion");
    }

    public boolean isRolMenuCmComunidad() {
        return validaciones.get("rolMenuCmComunidad");
    }

    //</editor-fold>
    
    public boolean isRolMenuMttoTipoTabla() {
        return validaciones.get("rolMenuMttoTipoTabla");
    }
  
    public boolean isRolMenuMttoTablas() {
        return validaciones.get("rolMenuMttoTablas");
    }
    
    public boolean isRolMenuCmModificacionMasiva (){
        return validaciones.get("rolMenuCmModificacionMasiva");
    }
    
    public boolean isRolMenuCmCargueMasivo (){
        return validaciones.get("rolMenuCmCargueMasivo");
    }
 
    /**
     * Obtener el rol del menu . Obtiene el rol del menu parametros.
     *
     * @author Victor Bocanegra.
     * @version 1.0 revision 06/03/2019.
     * @return el rol del menu parametros.
     */
    public boolean isRolMenuGestionParametros() {
        return validaciones.get("rolMenuGestionParametros");
    }
    
    public boolean isRolMenuFraudes(){
        return validaciones.get("rolMenuFraudes");
    }
    
    	  public boolean isRolOtCcmm() {
        return validaciones.get("rolOtCcmm");
    }

    public boolean isRolHistOtCm() {
        return validaciones.get("rolHistOtCm");
    }

    public boolean isRolOtDir() {
        return validaciones.get("rolOtDir");
    }

    public boolean isRolHistOtDir() {
        return validaciones.get("rolHistOtDir");
    }

    public boolean isRolMenuEstadoActualOtCm() {
        return validaciones.get("rolMenuEstadoActualOtCm");
    }

    public boolean isRolMenuReporteHistOtCm() {
        return validaciones.get("rolMenuReporteHistOtCm");
    }

    public boolean isRolMenuEstadoOtDir() {
        return validaciones.get("rolMenuEstadoOtDir");
    }

    public boolean isRolMenuHistOtDireccion() {
        return validaciones.get("rolHistOtDireccion");
    }    
   
    public boolean isRolMenuSubtipoOtCmVt() {
        return validaciones.get("rolMenuSubtipoOtCmVt");
    }
    
        /**
     * Obtener el rol del menu CM Arrendatario.
     *
     * @author Victor bocanegra
     * @version 1.0 revision 19/05/2020.
     * @return el rol del menu CM Arrendatario.
     */
    public boolean isRolMenuCmArrendatario() {
        return validaciones.get("rolMenuCmArrendatario");
    }
    
    /**
     * Obtener el rol del menu CM SLA Ejecucion.
     *
     * @author Victor bocanegra
     * @version 1.0 revision 21/05/2020.
     * @return el rol del menu CM SLA Ejecucion.
     */
    public boolean isRolMenuCmSlaEjecucion() {
        return validaciones.get("rolMenuCmSLAEjecucion");
    }
    
     /**
     * Obtener el rol del menu CM Homologacion Razones.
     *
     * @author Victor bocanegra
     * @version 1.0 revision 09/12/2020.
     * @return el rol del menu CM Homologacion Razones.
     */
    public boolean isRolMenuCmHomologacionRazones() {
        return validaciones.get("rolMenuCmHomologacionCodigos");
    }
    
    
    /**
     * @author Jimmy Garcia Ospina
     * @return 
     */
    public boolean isRolMenuMarcacionesHHPPVirtual() {
        return validaciones.get("rolMenuMarcacionesHHPPVirtual");
    }

    /**
     * Verifica el rol para visualizar el item de cargue masivo en el menú
     *
     * @return {@code boolean} Retorna true si el usuario tiene el rol de cargue masivo
     */
    public boolean isRolCargueMasivoCcmm(){
        return validaciones.get("rolMenuCargueMasivo");
    }
}
