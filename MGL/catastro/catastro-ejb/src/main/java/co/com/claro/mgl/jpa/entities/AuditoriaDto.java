/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class AuditoriaDto {

    private Date fechaCreacion;
    private Date fechaModificacion;
    private String usuarioCreacion;
    private String usuarioModificacion;
    private String Antes = "";
    private String Despues = "";
    private String Justificacion="";

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public String getAntes() {
        return Antes;
    }

    public void setAntes(String Antes) {
        this.Antes = Antes;
    }

    public String getDespues() {
        return Despues;
    }

    public void setDespues(String Despues) {
        this.Despues = Despues;
    }

    public String getJustificacion() {
        return Justificacion;
    }

    public void setJustificacion(String Justificacion) {
        this.Justificacion = Justificacion;
    }
    
    public int compareTo(AuditoriaDto o) {
        int valor = getFechaCreacion().compareTo(o.getFechaCreacion());

        return (valor != 0 ? valor : getFechaCreacion().compareTo(o.getFechaCreacion()));
    }
    

}
