/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.estadoSolicitudHhppInspira;

import co.com.claro.mer.exception.ExceptionServBean;
import co.com.claro.mer.utils.enums.MessageSeverityEnum;
import co.com.claro.mgl.dtos.CmtParamentrosComplejosDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.SolicitudFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.mbeans.vt.migracion.beans.ControllerListSolicitud;
import co.com.claro.mgl.mbeans.vt.migracion.beans.enums.AccionesVT;
import co.com.claro.mgl.mbeans.vt.solicitudes.SolicitudSessionBean;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Juan David Hernandez
 */
@ViewScoped
@ManagedBean(name = "estadoSolicitudHhppBean")
public class EstadoSolicitudHhppBean implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(EstadoSolicitudHhppBean.class);
    private static final String ESTADO_DE_SOLICITUD_HHPP = "Estado de Solicitud HHPP";
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String usuarioVt = null;
    private int perfilVt = 0;
    @Getter
    @Setter
    private BigDecimal numeroSolicitud;
    @Getter
    @Setter
    private Solicitud solicitud;
    private boolean goGestion = false;
    private SecurityLogin securityLogin;
    private List<CmtBasicaMgl> tipoAccionSolicitudBasicaList;
    @EJB
    private SolicitudFacadeLocal solicitudFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
     @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    @Inject
    private ExceptionServBean exceptionServBean;
     
    //Opciones agregadas para Rol
    private final String BSESOLBTN = "BSESOLBTN";


    public EstadoSolicitudHhppBean() {
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

            if (usuarioVt == null) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en EstadoSolicitudHhppBean. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en EstadoSolicitudHhppBean. ", e, LOGGER);
        }
    }
    
     @PostConstruct
    public void init() {
        try {
            obtenerTipoAccionSolicitudList();
            goGestion = false;

            HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if (StringUtils.isNotBlank(req.getParameter("solicitud"))) {
                numeroSolicitud = new BigDecimal(req.getParameter("solicitud"));
            }
            if (numeroSolicitud != null) {
                buscarSolicitud();
            }
        } catch (ApplicationException e) {
            String msg = "Error en el cargue inicial de la estado de la solicitud: " + e.getMessage();
            exceptionServBean.notifyError(e, msg, ESTADO_DE_SOLICITUD_HHPP);
        } catch (Exception e) {
            String msg = "Error al cargar el estado de la solicitud: " + e.getMessage();
            exceptionServBean.notifyError(e, msg, ESTADO_DE_SOLICITUD_HHPP);
        }
    }

      /**
     * Función utilizada buscar una solicitud por idSolicitud
     *
     *
     * @author Juan David Hernandez
     */
    public void buscarSolicitud() {
        try {
            if (numeroSolicitud == null || numeroSolicitud.equals(BigDecimal.ZERO)) {
                String msn = "Debe ingresar un número de solicitud para ser consultada. ";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
                return;
            }

            solicitud = solicitudFacadeLocal.findById(numeroSolicitud);

            if (solicitud == null) {
                String msn = "No se encontraron solicitudes por el número de solicitud ingresado  ";
                FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, msn);
                return;
            }

            obtenerTipoSolicitud(solicitud);

        } catch (RuntimeException e) {
            String msgError = "Error al buscar la solicitud .  Intente de nuevo por favor  ";
            exceptionServBean.notifyError(e, msgError, ESTADO_DE_SOLICITUD_HHPP);
        } catch (Exception e) {
            String msgError = "Se presentó un error al buscar la solicitud. "  + numeroSolicitud
                    + " .Intente de nuevo por favor  ";
            exceptionServBean.notifyError(e, msgError, ESTADO_DE_SOLICITUD_HHPP);
        }
    }
    
          /**
     * Función utilizada redireccionar al detalle de una solicitud
     *
     * @param solicitudSeleccionada
     *
     * @author Juan David Hernandez
     */
    public void detallarSolicitud(Solicitud solicitudSeleccionada) {
        try {
            if (solicitudSeleccionada != null) {
                if (solicitudSeleccionada.getCambioDir().equals(Constant.RR_DIR_ESCALAMIENTO_HHPP)) {
                    SolicitudSessionBean solicitudSessionBean =
                            JSFUtil.getSessionBean(SolicitudSessionBean.class);
                    solicitudSessionBean.setSolicitudDthSeleccionada(solicitudSeleccionada);
                    solicitudSessionBean.setDetalleSolicitud(true);
                    FacesUtil.navegarAPagina("/view/MGL/VT/gestion/Escalamientos/gestionSolicitudEscala.jsf");
                } else {
                    //Valido si es un tipo de las migradas
                    if (solicitudSeleccionada.getTipo() != null
                            && tipoSolicitudesMigracion(solicitudSeleccionada.getTipo())) {
                        // Instacia Bean de Session para obtener la solicitud seleccionada
                        SolicitudSessionBean solicitudSessionBean =
                                JSFUtil.getSessionBean(SolicitudSessionBean.class);
                        solicitudSessionBean.setSolicitudDthSeleccionada(solicitudSeleccionada);
                        //muestra detalle de la solicitud en estado de la solicitud pero en la gestion deshabilita boton gestionar
                        solicitudSessionBean.setDetalleSolicitud(true);
                        FacesUtil.navegarAPagina("/view/MGL/VT/gestion/DTH/gestionSolicitudDth.jsf");
                    } else {
                        ControllerListSolicitud controllerListSolicitud = new ControllerListSolicitud();
                        session.setAttribute("solicitud", solicitudSeleccionada);
                        session.setAttribute("usuario", usuarioVt);
                        session.setAttribute("facade", solicitudFacadeLocal);
                        session.setAttribute("accionVt", tipoAccionSolicitudesMigracion(solicitudSeleccionada.getTipo()));
                        controllerListSolicitud.prepareEdit(1);
                        FacesUtil.navegarAPagina("/view/MGL/VT/Migracion/Solicitudes/"
                                + "Gestionar.xhtml");
                    }
                }
            } else {
                String msn = "Se presentó un error al detallar la solicitud. "
                        + "Intente de nuevo por favor  ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        msn, ""));
            }

        } catch (ApplicationException e) {
            String msg = "Error al detallar la solicitud. " + e.getMessage();
            exceptionServBean.notifyError(e, msg, ESTADO_DE_SOLICITUD_HHPP);
        } catch (Exception e) {
            String msg = "Error al momento de detallar la solicitud. " + e.getMessage();
            exceptionServBean.notifyError(e, msg, ESTADO_DE_SOLICITUD_HHPP);
        }
    }

    
              /**
     * Función utilizada para obtener el tipo de solicitud de la solicitud
     *
     * @param solicitudSeleccionada
     *
     * @author Juan David Hernandez
     */
    public void obtenerTipoSolicitud(Solicitud solicitudSeleccionada) throws ApplicationException {
        try {
            if (solicitudSeleccionada != null && tipoAccionSolicitudBasicaList != null) {
                for (CmtBasicaMgl tipoAccion : tipoAccionSolicitudBasicaList) {
                    if (solicitudSeleccionada.getCambioDir().equalsIgnoreCase(tipoAccion.getCodigoBasica())) {
                        solicitudSeleccionada.setTipoAccionSolicitudStr(tipoAccion.getNombreBasica());
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
    }
    
         
          /**
     * Función utilizada para obtiener valores de tipo de acción de solicitud
     * básica.
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void obtenerTipoAccionSolicitudList() throws ApplicationException {
        try {
            CmtTipoBasicaMgl cmtTipoBasicaMgl;
            cmtTipoBasicaMgl=cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_ACCION_SOLICITUD);
            tipoAccionSolicitudBasicaList =
                    cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl);
        } catch (ApplicationException e) {
            LOGGER.error("Error en obtenerTipoAccionSolicitudList. " + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en obtenerTipoAccionSolicitudList. " + e.getMessage());
            throw new ApplicationException("Error al obtener el listado de tipos de acción de solicitud: " + e.getMessage(), e);
        }
    }
    
    
    /**
     * Verifica si el usuario tiene el perfil para gestionar. Realiza la
     * validacion para determinar si el perfil del usuario esta autorizado para
     * gestionar una solicitud.
     *
     * @author Johnnatan Ortiz
     * @return retorna si el perfil de usuario permite gestionar.
     */
    private boolean isPerfilGestionar() throws ApplicationException {
        boolean isGestionador = false;
        try {
            cargaPermisosMenu();
        } catch (ApplicationException e) {
            LOGGER.error("Error en isPerfilGestionar. validando autenticacion " +e.getMessage()); 
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en isPerfilGestionar. validando autenticacion " +e.getMessage()); 
            throw new ApplicationException("Error en isPerfilGestionar. validando permisos: " +e.getMessage(), e);
        }
        return isGestionador;
    }

    /**
     * Carga los roles con los que cuenta el usuario logueado.
     *
     * @author Juan David Hernandez
     */
    private void cargaPermisosMenu() throws ApplicationException {
        try {
            List<CmtParamentrosComplejosDto> parametroMenuRolList
                    = parametrosMglFacadeLocal.findComplejo(Constant.PARAMETRO_CONFIG_MENU_ROL_VT);
            if (parametroMenuRolList != null && !parametroMenuRolList.isEmpty()) {
                for (CmtParamentrosComplejosDto p : parametroMenuRolList) {
                    if (p != null) {
                        validaMenuRol(p);
                    }
                }
            } else {
                throw new ApplicationException("No fue posible encotrar la "
                        + "configuración del Menu por Rol");
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en cargaPermisosMenu. Al cargar los Menu por Rol:  " +e.getMessage()); 
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en cargaPermisosMenu. Al cargar los Menu por Rol:  " +e.getMessage()); 
            throw new ApplicationException("Error en cargaPermisosMenu. Al cargar los Menu por Rol: " + e.getMessage(), e);
        }
    }

    /**
     * Validar el menú frente al rol.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/09/2017.
     * @param p Contiene los parámetros del menú.
     */
    private void validaMenuRol(CmtParamentrosComplejosDto p) throws ApplicationException {
        try {
            if (p != null && p.getKey() != null && !p.getKey().trim().isEmpty()
                    && p.getValue() != null && !p.getValue().trim().isEmpty()) {
                if (p.getKey().equalsIgnoreCase(Constant.ID_MENU_VT_GESTION_FROM_SOLICITUD)) {
                    goGestion = verificaRol(p.getValue());
                }
            }
        } catch (RuntimeException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en validaMenuRol: " + e.getMessage(), e);
        }
    }

    /**
     * Verificar el rol del usuario. Verifica si el usuario tiene el pasado por
     * parámetro.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/09/2017.
     * @param rol Rol a verificar.
     * @return True cuando el usuario tiene el rol del parámetro de lo contrario
     * false.
     */
    private boolean verificaRol(String rol) throws ApplicationException {
        try {
            return securityLogin.usuarioTieneRoll(rol);
        } catch (RuntimeException e) {
            throw new ApplicationException("Error en verificaRol: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ApplicationException("Error en verificaRol: " + e.getMessage(), e);
        }
    }

    public boolean tipoSolicitudesMigracion(String tipo) {

        boolean respuesta = true;

        if (tipo.equalsIgnoreCase("REPLANVTCON")
                || tipo.equalsIgnoreCase("VIINTERNET")
                || tipo.equalsIgnoreCase("CAMBIOEST")) {

            respuesta = false;
        }

        return respuesta;
    }
    
    
    public AccionesVT tipoAccionSolicitudesMigracion(String tipo) {

        AccionesVT respuesta = null;

        if (tipo.equalsIgnoreCase("REPLANVTCON")) {
            respuesta = AccionesVT.GESTIONAR_VT_REPLANTEAMIENTO;
        } else if (tipo.equalsIgnoreCase("VIINTERNET")) {
            respuesta = AccionesVT.GESTIONAR_VIABILIDAD_INTERNET;
        } else if (tipo.equalsIgnoreCase("CAMBIOEST")) {
            respuesta = AccionesVT.GESTIONAR_ACTUALIZACION_CAMBIO_ESTRATO;
        }

        return respuesta;
    }
    
     // Validar Opciones por Rol
    public boolean validarOpcionBuscar() {
        return validarEdicionRol(BSESOLBTN);
    }
    
    //funcion General
      private boolean validarEdicionRol(String formulario) {
        try {
            return ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
        }catch (ApplicationException e) {
            exceptionServBean.notifyError(e, Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), ESTADO_DE_SOLICITUD_HHPP);
            return false;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al momento de validar rol de edición:  " + e.getMessage(), ESTADO_DE_SOLICITUD_HHPP);
            return false;
        }
    }
}
