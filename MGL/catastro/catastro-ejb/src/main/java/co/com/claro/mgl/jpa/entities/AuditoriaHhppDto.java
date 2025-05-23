/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.util.Date;

/**
 * Clase DTO para mapear procesos de auditoria de agendamientos.
 *
 * @author Luz Villalobos
 * @version  1.0
 */
public class AuditoriaHhppDto {

    private Date fechaCreacion;
    private Date fechaModificacion;
    private String usuarioCreacion;
    private String usuarioModificacion;
    private String antes = "";
    private String despues = "";
    private String justificacion = "";

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
        return antes;
    }

    public void setAntes(String antes) {
        this.antes = antes;
    }

    public String getDespues() {
        return despues;
    }

    public void setDespues(String despues) {
        this.despues = despues;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }
    
    public int compareTo(AuditoriaHhppDto o) {
        int valor = getFechaCreacion().compareTo(o.getFechaCreacion());

        return (valor != 0 ? valor : getFechaCreacion().compareTo(o.getFechaCreacion()));
    }
    

}
