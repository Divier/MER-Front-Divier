/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.mbeans.vt.migracion.beans;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtOpcionesRolMgl;
import co.com.claro.mgl.mbeans.vt.migracion.beans.enums.AccionesVT;
import co.com.claro.mgl.utils.MenuEnum;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 * Controlar las acciones del menú de visitas técnicas Permite controlar las
 * opciones del menú que se registra en la presentación de visitas técnicas.
 *
 * @author becerraarmr
 */
@ManagedBean(name = "actionManagedBenan")
@SessionScoped
public class ActionManagedBean implements Serializable {
    private static final Logger LOGGER = LogManager.getLogger(ActionManagedBean.class);

    /**
     * Para controlar donde Iniciar
     */
    private AccionesVT action = AccionesVT.INDEX;

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    
    @EJB
    private CmtOpcionesRolMglFacadeLocal operacionesRolFacade;
    private List<CmtOpcionesRolMgl> opcionesRol;
    private Map<String, Boolean> validaciones;
    
    private String mensaje = "";
    private String estadoNuevo = "";
    private String suscriptor = "";
    private String idHhpp = "";
    /**
     * Crear la instancia
     * <p>
     * Crea una instancia ActionManagedBean
     *
     * @author becerraarmr
     */
    public ActionManagedBean() {
        try {
            SecurityLogin securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en ActionManagedBean. ", e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ActionManagedBean. ", e, LOGGER);
        }
    }
    
    @PostConstruct
    public void cargaPermisosMenu() {
      
        try {
            opcionesRol = operacionesRolFacade.consultarOpcionesRol("MENU");
            if (opcionesRol == null || opcionesRol.isEmpty()) {
                throw new ApplicationException("No fue posible encotrar la configuración del Menu por Rol");
            }
            addValidaciones();
            for (CmtOpcionesRolMgl opcion : opcionesRol) {
                validaMenuRol(opcion);
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en cargaPermisosMenu. Al cargar los Menu por Rol " +e.getMessage(), e);   
           
        } catch (Exception e) {
            LOGGER.error("Error en cargaPermisosMenu. Al cargar los Menu por Rol " +e.getMessage(), e);   
           
        }
    }
    
     private void addValidaciones() {
        validaciones = new HashMap<String, Boolean>();
        validaciones.put("rolDetalleCmPtGeneral", true);
        for (MenuEnum menu : MenuEnum.values()) {
            validaciones.put(menu.getValidador(), Boolean.FALSE);
        }
    }
  
    private void validaMenuRol(CmtOpcionesRolMgl opcion) {
        if (opcion != null && !"".equals(opcion.getNombreOpcion())
                && !"".equals(opcion.getRol())) {
            validarMenuRol(opcion);
        }
    }
    
    private void validarMenuRol(CmtOpcionesRolMgl opcion) {
        for (MenuEnum menu : MenuEnum.values()) {
            if (menu.getDescripcion().equals(opcion.getNombreOpcion())) {
                validaciones.put(menu.getValidador(), verificaRol(opcion.getRol()));
                return;
            }
        }
    }
    
    private boolean verificaRol(String rol) {
        try {
            SecurityLogin securityLogin = new SecurityLogin(facesContext);
            return securityLogin.usuarioTieneRoll(rol);
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en verificaRol. ", e, LOGGER);            
            return false;
        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Error en verificaRol. ", e, LOGGER);            
            return false;
        } catch (Exception e) {
             FacesUtil.mostrarMensajeError("Error en verificaRol. ", e, LOGGER);            
            return false;
        }
    }

    /**
     * Buscar el valor del atributo action.
     *
     * Muestra el valor del atributo action.
     *
     * @author becerraarmr
     *
     * @return un enúm AccionesVT que representa el valor.
     */
    public AccionesVT getAction() {
        return action;
    }

    /**
     * Actualizar el valor del atributo.
     *
     * Actualiza el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @param action the action to set
     */
    public void setAction(AccionesVT action) {
        this.action = action;
    }

    /**
     * Buscar la accione Index
     *
     * Muestra el valor de la acción INDEX
     *
     * @author becerraarmr
     *
     * @return AccionesVT.INDEX;
     */
    public AccionesVT getAccionIndex() {
        return AccionesVT.INDEX;
    }

    /**
     * Buscar el valor de la accion solicitud Visista técnica Conjunto
     *
     * Busca el valor del atributo correspondiente. Consigue la acción para
     * solicitar la visita técnica a edificio o conjunto
     *
     * @author becerraarmr
     *
     * @return SOLICITAR_VT_EDIFICIO_CONJUNTO
     */
    public AccionesVT getSolicitudVisitaTecnicaEdificioConjunto() {
        return AccionesVT.SOLICITAR_VT_EDIFICIO_CONJUNTO;
    }

    /**
     * Consigue la acción para solicitar visita tecnica replanteamiento
     *
     * @return SOLICITAR_VT_REPLANTEAMIENTO
     */
    public AccionesVT getSolicitudVisitaTecnicaReeplanteamiento() {
        return AccionesVT.SOLICITAR_VT_REPLANTEAMIENTO;
    }

    /**
     * Consigue la acción para solicitar visita técnica en casa
     *
     * @return SOLICITAR_VT_EN_CASA
     */
    public AccionesVT getSolicitudVisitaTecnicaEnCasa() {
        return AccionesVT.SOLICITAR_VT_EN_CASA;
    }

    /**
     * Consigue la acción para solicitar la creación de la cuenta Matriz.
     *
     * @return SOLICITAR_CREACION_CUENTA_MATRIZ
     */
    public AccionesVT getSolicitarCreacionCuentaMatriz() {
        return AccionesVT.SOLICITAR_CREACION_CUENTA_MATRIZ;
    }

    /**
     * Consigue la acción para solicitar la creación HHPP Unidireccional.
     *
     * @return SOLICITAR_CREACION_HHPP_UNIDIRECCIONAL
     */
    public AccionesVT getSolicitarCreacionHhppUnidireccional() {
        return AccionesVT.SOLICITAR_CREACION_HHPP_UNIDIRECCIONAL;
    }

    /**
     * Consigue la acción para solicitar la creación HHPP en cuenta Matriz
     *
     * @return SOLICITAR_CREACION_HHPP_EN_CUENTA_MATRIZ
     */
    public AccionesVT getSolicitarCreacionHhppEnCuentaMatriz() {
        return AccionesVT.SOLICITAR_CREACION_HHPP_EN_CUENTA_MATRIZ;
    }

    /**
     * Consigue la acción para solicitar la actualización de cambio de estrato.
     *
     * @return SOLICITAR_ACTUALIZACION_CAMBIO_ESTRATO
     */
    public AccionesVT getSolicitarActualizacionCambioEstrato() {
        return AccionesVT.SOLICITAR_ACTUALIZACION_CAMBIO_ESTRATO;
    }

    /**
     * Consigue la acción para solicitar la modificación de eliminar la cuenta
     * matriz.
     *
     * @return SOLICITAR_MODIFICAR_ELIMINACION_CUENTA_MATRIZ
     */
    public AccionesVT getSolicitarActualizacionModificarEliminarCuentaMatriz() {
        return AccionesVT.SOLICITAR_MODIFICAR_ELIMINACION_CUENTA_MATRIZ;
    }

    /**
     * Consigue la acción para solicitar modificación del HHPPP.
     *
     * @return SOLICITAR_MODIFICAR_HHPP
     */
    public AccionesVT getSolicitarModificarHHPP() {
        return AccionesVT.SOLICITAR_MODIFICAR_HHPP;
    }

    /**
     * Consigue la acción para solicitar la verificación de casas.
     *
     * @return SOLICITAR_VERIFICACION_CASAS
     */
    public AccionesVT getSolicitarVerificacionCasas() {
        return AccionesVT.SOLICITAR_VERIFICACION_CASAS;
    }

    /**
     * Consigue la acción para solicitar la viabilidad internet
     *
     * @return SOLICITAR_VIABILIDAD_INTERNET
     */
    public AccionesVT getSolicitarViabilidadInternet() {
        return AccionesVT.SOLICITAR_VIABILIDAD_INTERNET;
    }
    
      /**
     * Consigue la acción para solicitar cambio de estrato
     *
     * @return SOLICITAR_CAMBIO_ESTRATO
     */
    public AccionesVT getSolicitarCambioEstrato() {
        return AccionesVT.SOLICITAR_CAMBIO_ESTRATO;
    }

    /**
     * Consigue la acción para gestioinar visita técnica Edificio o Conjunto.
     *
     * @return GESTIONAR_VT_EDIFICIO_CONJUNTO
     */
    public AccionesVT getGestionarVtEdificioConjunto() {
        return AccionesVT.GESTIONAR_VT_EDIFICIO_CONJUNTO;
    }

    /**
     * Consigue la acción para gestionar visita técnica reeplanteamiento.
     *
     * @return GESTIONAR_VT_REPLANTEAMIENTO
     */
    public AccionesVT getGestionarVtReeplanteamiento() {
        return AccionesVT.GESTIONAR_VT_REPLANTEAMIENTO;
    }

    /**
     * Consigue la acción para gestionar visita técnica en casas.
     *
     * @return GESTIONAR_VT_EN_CASAS
     */
    public AccionesVT getGestionarVtEnCasas() {
        return AccionesVT.GESTIONAR_VT_EN_CASAS;
    }

    /**
     * Consigue la acción para gestionar la creación de la cuenta matriz.
     *
     * @return GESTIONAR_CREACION_CUENTA_MATRIZ
     */
    public AccionesVT getGestionarCreacionCuentaMatriz() {
        return AccionesVT.GESTIONAR_CREACION_CUENTA_MATRIZ;
    }

    /**
     * Consigue la acción para gestionar la creación del HHPP Unidireccional.
     *
     * @return GESTIONAR_CREACION_HHPP_UNIDIRECCIONAL
     */
    public AccionesVT getGestionarCreacionHHPPUnidireccional() {
        return AccionesVT.GESTIONAR_CREACION_HHPP_UNIDIRECCIONAL;
    }

    /**
     * Consigue la acción para gestionar la creación del HHPP en cuenta Matriz.
     *
     * @return GESTIONAR_CREACION_HHPP_EN_CUENTA_MATRIZ
     */
    public AccionesVT getGestionarCreacionHHPPEnCuentaMatriz() {
        return AccionesVT.GESTIONAR_CREACION_HHPP_EN_CUENTA_MATRIZ;
    }

    /**
     * Consigue la acción para gestionar la actualización cambio de estrato.
     *
     * @return GESTIONAR_ACTUALIZACION_CAMBIO_ESTRATO
     */
    public AccionesVT getGestionarActualizacionCambioEstrato() {
        return AccionesVT.GESTIONAR_ACTUALIZACION_CAMBIO_ESTRATO;
    }

    //TODO metodo provicional para la ejecuccion de pruebas
    public void redirecToGestionar() throws ApplicationException {
        try {
            FacesUtil.navegarAPagina("/view/MGL/VT/gestion/DTH/gestionSolicitudDthListado.jsf");
        } catch (ApplicationException e) {
            throw new ApplicationException("Error al momento de redireccionar a gestionar: " + e.getMessage(), e);
        }
    }

    /**
     * Consigue la acción para gestionar la actualización de eliminar cuenta
     * matriz.
     *
     * @return GESTIONAR_MODIFICAR_ELIMINAR_CUENTA_MATRIZ
     */
    public AccionesVT getGestionarModificarEliminarCuentaMatriz() {
        return AccionesVT.GESTIONAR_MODIFICAR_ELIMINAR_CUENTA_MATRIZ;
    }

    /**
     * Consigue la acción para gestionar la actualización de HHPP
     *
     * @return GESTIONAR_ACTUALIZACION_HHPP
     */
    public AccionesVT getGestionarActualizacionHHPP() {
        return AccionesVT.GESTIONAR_ACTUALIZACION_HHPP;
    }

    /**
     * Consigue la acción para gestionar la la verificación de casas.
     *
     * @return GESTIONAR_VERIFICACION_CASAS
     */
    public AccionesVT getGestionarVerificacionCasas() {
        return AccionesVT.GESTIONAR_VERIFICACION_CASAS;
    }

    /**
     * Consigue la acción para gestionar la viabilidad Internet.
     *
     * @return GESTIONAR_VIABILIDAD_INTERNET
     */
    public AccionesVT getGestionarViabilidadInternet() {
        return AccionesVT.GESTIONAR_VIABILIDAD_INTERNET;
    }

    /**
     * Consigue la acción el estado de la solicitud.
     *
     * @return ESTADO_SOLICITUD
     */
    public AccionesVT getEstadoSolicitud() {
        return AccionesVT.ESTADO_SOLICITUD;
    }

    /**
     * Consigue la acción el administrador.
     *
     * @return ADMINISTRADOR
     */
    public AccionesVT getAdministrador() {
        return AccionesVT.ADMINISTRADOR;
    }

    /**
     * Consigue la acción de reportes de solicitudes.
     *
     * @return REPORTES_SOLICITUDES
     */
    public AccionesVT getReportesSolicitudes() {
        return AccionesVT.REPORTES_SOLICITUDES;
    }

    /**
     * Consigue la acción de cierre Masivo.
     *
     * @return CIERRE_MASIVO
     */
    public AccionesVT getCierreMasivo() {
        return AccionesVT.CIERRE_MASIVO;
    }

    /**
     * Consigue la acción de Administrar Direcciones nodo.
     *
     * @return ADMINISTRADOR_DE_DIRECCIONES_NODO
     */
    public AccionesVT getAdministradorDeDireccionesNodo() {
        return AccionesVT.ADMINISTRADOR_DE_DIRECCIONES_NODO;
    }

    /**
     * Consigue la acción de Administrar Direcciones HHPP.
     *
     * @return ADMINISTRADOR_DE_DIRECCIONES_HHPP
     */
    public AccionesVT getAdministradorDeDireccionesHHPP() {
        return AccionesVT.ADMINISTRADOR_DE_DIRECCIONES_HHPP;
    }

    /**
     * Consigue la acción de Administrar Direcciones de Direcciones.
     *
     * @return ADMINISTRADOR_DE_DIRECCIONES_DIRECCION
     */
    public AccionesVT getAdministradorDeDireccionesDireccion() {
        return AccionesVT.ADMINISTRADOR_DE_DIRECCIONES_DIRECCION;
    }

    /**
     * Consigue la acción de Administrar Direcciones de departamentos y ciudades
     *
     * @return ADMINISTRADOR_DE_DIRECCIONES_DPTO_CIUDADES
     */
    public AccionesVT getAdministradorDeDireccionesDptoCiudades() {
        return AccionesVT.ADMINISTRADOR_DE_DIRECCIONES_DPTO_CIUDADES;
    }

    /**
     * Consigue la acción de operaciones HHPP de cambio de estrato Masivo.
     *
     * @return OPERACIONES_HHPP_CAMBIO_ESTRATO_MASIVO
     */
    public AccionesVT getOperacionesHHPPCambioEstratoMasivo() {
        return AccionesVT.OPERACIONES_HHPP_CAMBIO_ESTRATO_MASIVO;
    }

    /**
     * Consigue la acción de operaciones HHPP de eliminar masivamente HHPP.
     *
     * @return OPERACIONES_HHPP_ELIMINAR_MASIVAMENTE_HHPP
     */
    public AccionesVT getOperacionesHHPPEliminarMasivamenteHHPP() {
        return AccionesVT.OPERACIONES_HHPP_ELIMINAR_MASIVAMENTE_HHPP;
    }

    public String getHostName() {
        String hostName = "";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            FacesUtil.mostrarMensajeError("Error en getHostName. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getHostName. ", e, LOGGER);
        }
        return hostName;
    }
    
    public String getIdHhpp() {
        return idHhpp;
    }

    public void setIdHhpp(String idHhpp) {
        this.idHhpp = idHhpp;
    }
    
    
    public String getMensaje() {
        return mensaje;
    }

    public void setEstadoNuevo(String estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public void setSuscriptor(String suscriptor) {
        this.suscriptor = suscriptor;
    }

    public String getEstadoNuevo() {
        return estadoNuevo;
    }

    public String getSuscriptor() {
        return suscriptor;
    }
    
    
}
