/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "MGL_SLA_EJECUCION$AUD", schema = "MGL_SCHEME")
@XmlRootElement
public class SlaEjecucionAuditoriaMgl implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SLA_EJECUCION_AUD_ID", nullable = false)
    private BigDecimal slaEjeAuditoriaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SLA_EJECUCION_ID", nullable = false)
    private SlaEjecucionMgl slaEjecucionMgl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TECNOLOGIA", nullable = true)
    private CmtBasicaMgl basicaIdTecnologia;

    @Column(name = "TIPO_EJECUCION")
    private String tipoEjecucion;

    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;

    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;

    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;

    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;

    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;

    @Column(name = "AUD_ACTION", length = 3)
    private String accionAuditoria;

    @Column(name = "AUD_TIMESTAMP")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaAuditoria;

    @Column(name = "AUD_USER", length = 30)
    private String usuarioAuditoria;

    @Column(name = "SESSION_ID")
    private BigDecimal sesionAuditoria;

    public BigDecimal getSlaEjeAuditoriaId() {
        return slaEjeAuditoriaId;
    }

    public void setSlaEjeAuditoriaId(BigDecimal slaEjeAuditoriaId) {
        this.slaEjeAuditoriaId = slaEjeAuditoriaId;
    }

    public void setSlaEjecucionMgl(SlaEjecucionMgl slaEjecucionMgl) {
        this.slaEjecucionMgl = slaEjecucionMgl;
    }
    
    public BigDecimal getSlaEjecucionId() {
        return slaEjecucionMgl.getSlaEjecucionId();
    }

    public CmtBasicaMgl getBasicaIdTecnologia() {
        return basicaIdTecnologia;
    }

    public void setBasicaIdTecnologia(CmtBasicaMgl basicaIdTecnologia) {
        this.basicaIdTecnologia = basicaIdTecnologia;
    }

    public String getTipoEjecucion() {
        return tipoEjecucion;
    }

    public void setTipoEjecucion(String tipoEjecucion) {
        this.tipoEjecucion = tipoEjecucion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public String getAccionAuditoria() {
        return accionAuditoria;
    }

    public void setAccionAuditoria(String accionAuditoria) {
        this.accionAuditoria = accionAuditoria;
    }

    public Date getFechaAuditoria() {
        return fechaAuditoria;
    }

    public void setFechaAuditoria(Date fechaAuditoria) {
        this.fechaAuditoria = fechaAuditoria;
    }

    public String getUsuarioAuditoria() {
        return usuarioAuditoria;
    }

    public void setUsuarioAuditoria(String usuarioAuditoria) {
        this.usuarioAuditoria = usuarioAuditoria;
    }

    public BigDecimal getSesionAuditoria() {
        return sesionAuditoria;
    }

    public void setSesionAuditoria(BigDecimal sesionAuditoria) {
        this.sesionAuditoria = sesionAuditoria;
    }
}
