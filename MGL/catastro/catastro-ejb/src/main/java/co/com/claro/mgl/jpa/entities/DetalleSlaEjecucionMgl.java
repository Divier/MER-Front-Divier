/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "MGL_DETALLE_SLA_EJECUCION", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleSlaEjecucionMgl.findBySlaEjecucion", query = "SELECT d FROM DetalleSlaEjecucionMgl d "
            + "WHERE d.estadoRegistro=1 AND d.slaEjecucionMgl = :slaEjecucionMgl ORDER BY d.seqProceso ASC"),
    @NamedQuery(name = "DetalleSlaEjecucionMgl.findBySlaEjecucionPaginated", query = "SELECT d FROM DetalleSlaEjecucionMgl d WHERE d.estadoRegistro=1 ORDER BY d.seqProceso DESC"),
    
})
public class DetalleSlaEjecucionMgl implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "DetalleSlaEjecucionMgl.seq_detSlaEjecucion",
            sequenceName = "MGL_SCHEME.MGL_DETALLE_SLA_EJECUCION_SQ", allocationSize = 1
    )

    @GeneratedValue(generator = "DetalleSlaEjecucionMgl.seq_detSlaEjecucion")
    @Column(name = "DET_SLA_EJECUCION_ID", nullable = false)
    private BigDecimal detSlaEjecucionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SLA_EJECUCION_ID", nullable = true)
    private SlaEjecucionMgl slaEjecucionMgl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_OT_ID", nullable = true)
    private CmtTipoOtMgl subTipoOtCCMM;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_OT2_ID", nullable = true)
    private TipoOtHhppMgl subTipoOtUnidad;

    @Column(name = "SLA")
    private int sla;

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
    
    @Column(name = "SEQ_PROCESO")
    private int seqProceso;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADO_CCMM", nullable = true)
    private CmtBasicaMgl estadoCcmm;
    

    public BigDecimal getDetSlaEjecucionId() {
        return detSlaEjecucionId;
    }

    public void setDetSlaEjecucionId(BigDecimal detSlaEjecucionId) {
        this.detSlaEjecucionId = detSlaEjecucionId;
    }

    public SlaEjecucionMgl getSlaEjecucionMgl() {
        return slaEjecucionMgl;
    }

    public void setSlaEjecucionMgl(SlaEjecucionMgl slaEjecucionMgl) {
        this.slaEjecucionMgl = slaEjecucionMgl;
    }

    public CmtTipoOtMgl getSubTipoOtCCMM() {
        return subTipoOtCCMM;
    }

    public void setSubTipoOtCCMM(CmtTipoOtMgl subTipoOtCCMM) {
        this.subTipoOtCCMM = subTipoOtCCMM;
    }

    public TipoOtHhppMgl getSubTipoOtUnidad() {
        return subTipoOtUnidad;
    }

    public void setSubTipoOtUnidad(TipoOtHhppMgl subTipoOtUnidad) {
        this.subTipoOtUnidad = subTipoOtUnidad;
    }

    public int getSla() {
        return sla;
    }

    public void setSla(int sla) {
        this.sla = sla;
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

    public int getSeqProceso() {
        return seqProceso;
    }

    public void setSeqProceso(int seqProceso) {
        this.seqProceso = seqProceso;
    }

    public CmtBasicaMgl getEstadoCcmm() {
        return estadoCcmm;
    }

    public void setEstadoCcmm(CmtBasicaMgl estadoCcmm) {
        this.estadoCcmm = estadoCcmm;
    }
    
}
