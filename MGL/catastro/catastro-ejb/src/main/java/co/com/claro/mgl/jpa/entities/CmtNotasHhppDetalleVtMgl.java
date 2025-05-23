/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

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
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan David Hernandez
 */
@Entity
@Table(name = "CMT_NOTAS_TEC_HAB_DETALLE_VT", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtNotasHhppDetalleVtMgl.findAll", query = "SELECT n FROM CmtNotasHhppDetalleVtMgl n ")})
public class CmtNotasHhppDetalleVtMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtNotasHhppDetalleVtMgl.CmtNotasHhppDetalleVtMglSq",
            sequenceName = "MGL_SCHEME.CMT_NOTAS_TEC_HA_DETALLE_VT_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtNotasHhppDetalleVtMgl.CmtNotasHhppDetalleVtMglSq")
    @Column(name = "NOTAS_DETALLE_ID", nullable = false)
    private BigDecimal notasDetalleId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTAS_ID")
    private CmtNotasHhppVtMgl notaHhpp;
    @Column(name = "NOTA")
    private String nota;  
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Transient
    private String linkNota;

    public BigDecimal getNotasDetalleId() {
        return notasDetalleId;
    }

    public void setNotasDetalleId(BigDecimal notasDetalleId) {
        this.notasDetalleId = notasDetalleId;
    }

    public CmtNotasHhppVtMgl getNotaHhpp() {
        return notaHhpp;
    }

    public void setNotaHhpp(CmtNotasHhppVtMgl notaHhpp) {
        this.notaHhpp = notaHhpp;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
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

    public String getLinkNota() {
        return linkNota;
    }

    public void setLinkNota(String linkNota) {
        this.linkNota = linkNota;
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
    
    @PrePersist
    public void prePersist() {
        this.estadoRegistro = 1;
    }
}