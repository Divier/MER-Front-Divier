/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CMT_COMPETENCIA$AUD", schema = "MGL_SCHEME")
@XmlRootElement
public class CmtCompetenciaAuditoriaMgl implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "COMPETENCIAAUD_ID", nullable = false)
    private BigDecimal competenciaAuditoriaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPETENCIA_ID", nullable = false)
    private CmtCompetenciaMgl competenciaIdObj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBEDIFICIO_ID")
    private CmtSubEdificioMgl subEdificioObj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPETENCIA_TIPO_ID")
    private CmtCompetenciaTipoMgl competenciaTipo;

    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;

    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;

    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;

    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;

    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;

    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;

    @Column(name = "DETALLE", nullable = true, length = 200)
    private String detalle;

    @Column(name = "AUD_ACTION", length = 3)
    private String accionAuditoria;

    @Column(name = "AUD_TIMESTAMP")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaAuditoria;

    @Column(name = "AUD_USER", length = 30)
    private String usuarioAuditoria;

    @Column(name = "SESSION_ID")
    private BigDecimal sesionAuditoria;

    public BigDecimal getCompetenciaAuditoriaId() {
        return competenciaAuditoriaId;
    }

    public void setCompetenciaAuditoriaId(BigDecimal competenciaAuditoriaId) {
        this.competenciaAuditoriaId = competenciaAuditoriaId;
    }

    public CmtCompetenciaMgl getCompetenciaIdObj() {
        return competenciaIdObj;
    }

    public void setCompetenciaIdObj(CmtCompetenciaMgl competenciaIdObj) {
        this.competenciaIdObj = competenciaIdObj;
    }

    public CmtSubEdificioMgl getSubEdificioObj() {
        return subEdificioObj;
    }

    public void setSubEdificioObj(CmtSubEdificioMgl subEdificioObj) {
        this.subEdificioObj = subEdificioObj;
    }

    public CmtCompetenciaTipoMgl getCompetenciaTipo() {
        return competenciaTipo;
    }

    public void setCompetenciaTipo(CmtCompetenciaTipoMgl competenciaTipo) {
        this.competenciaTipo = competenciaTipo;
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

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
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

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
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
    
    public BigDecimal getCompetenciaId() {
        return competenciaIdObj.getCompetenciaId();
    }
    
}
