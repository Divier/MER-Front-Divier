/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import static co.com.claro.mer.utils.constants.MicrositioConstants.ERROR_ATTRIBUTE;
import static co.com.claro.mer.utils.constants.MicrositioConstants.MENSAJE_EXEPCION;


/**
 *
 * @author bocanegravm
 */
@ViewScoped
@ManagedBean(name = "respuestaPostFactibilidadesBean")
public class RespuestaPostFactibilidadesBean {

    private boolean usuarioValido;
    private boolean usuarioNoAcceso;
    private boolean perfilValido;
    private boolean paramValido;
    private boolean paramPostValido;
    private boolean mensajeExcepcion;
    private String respuestaMensajeExcepcion;
    private int error;

    private final FacesContext facesContext = FacesContext.getCurrentInstance();
    private final HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

    /**
     * Método post construct de la clase para inicializar componentes y
     * atributos
     */
    @PostConstruct
    public void init() {

        if (session.getAttribute(ERROR_ATTRIBUTE) != null) {
            error = (int) session.getAttribute(ERROR_ATTRIBUTE);
            session.removeAttribute(ERROR_ATTRIBUTE);
        }
        if (session.getAttribute(MENSAJE_EXEPCION) != null) {
            respuestaMensajeExcepcion = (String) session.getAttribute(MENSAJE_EXEPCION);
            session.removeAttribute(MENSAJE_EXEPCION);
        }
        switch (error) {
            case 0:
                usuarioNoAcceso = true;
                break;
            case 1:
                usuarioValido = true;
                break;
            case 2:
                perfilValido = true;
                break;
            case 3:
                paramValido = true;
                break;
            case 4:
                paramPostValido = true;
                break;
            case 5:
                mensajeExcepcion = true;
                break;
            default:
                break;
        }

    }

    public boolean isUsuarioValido() {
        return usuarioValido;
    }

    public void setUsuarioValido(boolean usuarioValido) {
        this.usuarioValido = usuarioValido;
    }

    public boolean isPerfilValido() {
        return perfilValido;
    }

    public void setPerfilValido(boolean perfilValido) {
        this.perfilValido = perfilValido;
    }

    public boolean isParamValido() {
        return paramValido;
    }

    public void setParamValido(boolean paramValido) {
        this.paramValido = paramValido;
    }

    public boolean isParamPostValido() {
        return paramPostValido;
    }

    public void setParamPostValido(boolean paramPostValido) {
        this.paramPostValido = paramPostValido;
    }

    public boolean isMensajeExcepcion() {
        return mensajeExcepcion;
    }

    public void setMensajeExcepcion(boolean mensajeExcepcion) {
        this.mensajeExcepcion = mensajeExcepcion;
    }

    public String getRespuestaMensajeExcepcion() {
        return respuestaMensajeExcepcion;
    }

    public void setRespuestaMensajeExcepcion(String respuestaMensajeExcepcion) {
        this.respuestaMensajeExcepcion = respuestaMensajeExcepcion;
    }

    public boolean isUsuarioNoAcceso() {
        return usuarioNoAcceso;
    }

    public void setUsuarioNoAcceso(boolean usuarioNoAcceso) {
        this.usuarioNoAcceso = usuarioNoAcceso;
    }
}
