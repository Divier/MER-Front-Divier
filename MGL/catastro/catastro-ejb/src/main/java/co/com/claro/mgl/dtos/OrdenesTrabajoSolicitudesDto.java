/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author espinosadiea
 */
public class OrdenesTrabajoSolicitudesDto {
    private String ordSol;//identifica si es orden o solicitud
    private BigDecimal numeroOtSol;
    private String tipoOtSol;
    private String tecnologiaOT;//solo para ordenes de trabajo
    private String estadoOT;//solo para ordenes de trabajo
    private String estadoInternoOtSol;
    private String ansSol;//solo para solicitudes
    private String Log;
    private String usuario;
    private Date fechaCreacion;
    private Date fechaEdicion;
    private CmtSolicitudCmMgl solicitudObj;
    private CmtOrdenTrabajoMgl ordenObj;

    public String getOrdSol() {
        return ordSol;
    }

    public void setOrdSol(String ordSol) {
        this.ordSol = ordSol;
    }

    public BigDecimal getNumeroOtSol() {
        return numeroOtSol;
    }

    public void setNumeroOtSol(BigDecimal numeroOtSol) {
        this.numeroOtSol = numeroOtSol;
    }

    public String getTipoOtSol() {
        return tipoOtSol;
    }

    public void setTipoOtSol(String tipoOtSol) {
        this.tipoOtSol = tipoOtSol;
    }

    public String getTecnologiaOT() {
        return tecnologiaOT;
    }

    public void setTecnologiaOT(String tecnologiaOT) {
        this.tecnologiaOT = tecnologiaOT;
    }

    public String getEstadoOT() {
        return estadoOT;
    }

    public void setEstadoOT(String estadoOT) {
        this.estadoOT = estadoOT;
    }

    public String getEstadoInternoOtSol() {
        return estadoInternoOtSol;
    }

    public void setEstadoInternoOtSol(String estadoInternoOtSol) {
        this.estadoInternoOtSol = estadoInternoOtSol;
    }

    public String getAnsSol() {
        return ansSol;
    }

    public void setAnsSol(String ansSol) {
        this.ansSol = ansSol;
    }

    public String getLog() {
        return Log;
    }

    public void setLog(String Log) {
        this.Log = Log;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public CmtSolicitudCmMgl getSolicitudObj() {
        return solicitudObj;
    }

    public void setSolicitudObj(CmtSolicitudCmMgl solicitudObj) {
        this.solicitudObj = solicitudObj;
    }

    public CmtOrdenTrabajoMgl getOrdenObj() {
        return ordenObj;
    }

    public void setOrdenObj(CmtOrdenTrabajoMgl ordenObj) {
        this.ordenObj = ordenObj;
    }
    
}
