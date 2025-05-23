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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CMT_SOL_UNIDADES_PREVIAS", schema = "MGL_SCHEME")
@XmlRootElement
public class CmtUnidadesPreviasMgl implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtUnidadesPreviasMgl.CmtUnidadesPreviasMglSeq",
            sequenceName = "MGL_SCHEME.CMT_SOL_UNIDADES_PREV_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtUnidadesPreviasMgl.CmtUnidadesPreviasMglSeq")
    @Column(name = "UNIDADES_PREVIAS_ID", nullable = false)
    private BigDecimal unidadPreviaId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOLICITUD_ID")
    private CmtSolicitudCmMgl solicitudObj;
    @Basic(optional = false)
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @NotNull
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @NotNull
    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;
    @NotNull
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "JUSTIFICACION")
    private String justificacion;
    @Basic(optional = false)
    @Column(name = "NIVEL5_TIPO_NUEVO", nullable = false, length = 20)
    private String nivel5TipoNuevo;
    @Basic(optional = false)
    @Column(name = "NIVEL5_VALOR_NUEVO", nullable = false, length = 20)
    private String nivel5ValorNuevo;
    @Basic(optional = false)
    @Column(name = "NIVEL6_TIPO_NUEVO", nullable = false, length = 20)
    private String nivel6TipoNuevo;
    @Basic(optional = false)
    @Column(name = "NIVEL6_VALOR_NUEVO", nullable = false, length = 20)
    private String nivel6ValorNuevo;
    @Basic(optional = false)
    @Column(name = "INFO_TEC_HHPP", nullable = false, length = 200)
    private String infoTecHhpp;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIRECCION_DETALLADA_ID", referencedColumnName = "DIRECCION_DETALLADA_ID", nullable = false)
    private CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl;
 

    public BigDecimal getUnidadPreviaId() {
        return unidadPreviaId;
    }

    public void setUnidadPreviaId(BigDecimal unidadPreviaId) {
        this.unidadPreviaId = unidadPreviaId;
    }

    public CmtSolicitudCmMgl getSolicitudObj() {
        return solicitudObj;
    }

    public void setSolicitudObj(CmtSolicitudCmMgl solicitudObj) {
        this.solicitudObj = solicitudObj;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
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

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getNivel5TipoNuevo() {
        return nivel5TipoNuevo;
    }

    public void setNivel5TipoNuevo(String nivel5TipoNuevo) {
        this.nivel5TipoNuevo = nivel5TipoNuevo;
    }

    public String getNivel5ValorNuevo() {
        return nivel5ValorNuevo;
    }

    public void setNivel5ValorNuevo(String nivel5ValorNuevo) {
        this.nivel5ValorNuevo = nivel5ValorNuevo;
    }

    public String getNivel6TipoNuevo() {
        return nivel6TipoNuevo;
    }

    public void setNivel6TipoNuevo(String nivel6TipoNuevo) {
        this.nivel6TipoNuevo = nivel6TipoNuevo;
    }

    public String getNivel6ValorNuevo() {
        return nivel6ValorNuevo;
    }

    public void setNivel6ValorNuevo(String nivel6ValorNuevo) {
        this.nivel6ValorNuevo = nivel6ValorNuevo;
    }

    public String getInfoTecHhpp() {
        return infoTecHhpp;
    }

    public void setInfoTecHhpp(String infoTecHhpp) {
        this.infoTecHhpp = infoTecHhpp;
    }

    public CmtDireccionDetalladaMgl getCmtDireccionDetalladaMgl() {
        return cmtDireccionDetalladaMgl;
    }

    public void setCmtDireccionDetalladaMgl(CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl) {
        this.cmtDireccionDetalladaMgl = cmtDireccionDetalladaMgl;
    }

    @PrePersist
    public void prePersist() {
        if (this.justificacion == null || this.justificacion.trim().isEmpty()) {
            this.justificacion = "CREACION";
        }
        this.estadoRegistro = 1;
    }
    
}
