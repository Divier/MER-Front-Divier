/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author castrofo
 */
@Entity
@Table(name = "TEC_TIPO_MARCAS" , schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TecTipoMarcas.findAll", query = "SELECT t FROM TecTipoMarcas t"),
    @NamedQuery(name = "TecTipoMarcas.findByTipoMarcasId", query = "SELECT t FROM TecTipoMarcas t WHERE t.tipoMarcasId = :tipoMarcasId"),
    @NamedQuery(name = "TecTipoMarcas.findByNombre", query = "SELECT t FROM TecTipoMarcas t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TecTipoMarcas.findByFechaCreacion", query = "SELECT t FROM TecTipoMarcas t WHERE t.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "TecTipoMarcas.findByUsuarioCreacion", query = "SELECT t FROM TecTipoMarcas t WHERE t.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "TecTipoMarcas.findByFechaEdicion", query = "SELECT t FROM TecTipoMarcas t WHERE t.fechaEdicion = :fechaEdicion"),
    @NamedQuery(name = "TecTipoMarcas.findByUsuarioEdicion", query = "SELECT t FROM TecTipoMarcas t WHERE t.usuarioEdicion = :usuarioEdicion"),
    @NamedQuery(name = "TecTipoMarcas.findByCodigo", query = "SELECT t FROM TecTipoMarcas t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "TecTipoMarcas.findByEstadoRegistro", query = "SELECT t FROM TecTipoMarcas t WHERE t.estadoRegistro = :estadoRegistro"),
    @NamedQuery(name = "TecTipoMarcas.findByPerfilCreacion", query = "SELECT t FROM TecTipoMarcas t WHERE t.perfilCreacion = :perfilCreacion"),
    @NamedQuery(name = "TecTipoMarcas.findByPerfilEdicion", query = "SELECT t FROM TecTipoMarcas t WHERE t.perfilEdicion = :perfilEdicion")})
public class TecTipoMarcas implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO_MARCAS_ID")
    private BigDecimal tipoMarcasId;
    @Size(max = 200)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Size(max = 100)
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Size(max = 5)
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private short estadoRegistro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERFIL_CREACION")
    private long perfilCreacion;
    @Column(name = "PERFIL_EDICION")
    private Long perfilEdicion;

    public TecTipoMarcas() {
    }

    public TecTipoMarcas(BigDecimal tipoMarcasId) {
        this.tipoMarcasId = tipoMarcasId;
    }

    public TecTipoMarcas(BigDecimal tipoMarcasId, Date fechaCreacion, String usuarioCreacion, short estadoRegistro, long perfilCreacion) {
        this.tipoMarcasId = tipoMarcasId;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.estadoRegistro = estadoRegistro;
        this.perfilCreacion = perfilCreacion;
    }

    public BigDecimal getTipoMarcasId() {
        return tipoMarcasId;
    }

    public void setTipoMarcasId(BigDecimal tipoMarcasId) {
        this.tipoMarcasId = tipoMarcasId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public short getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(short estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public long getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(long perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    public Long getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(Long perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoMarcasId != null ? tipoMarcasId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TecTipoMarcas)) {
            return false;
        }
        TecTipoMarcas other = (TecTipoMarcas) object;
        if ((this.tipoMarcasId == null && other.tipoMarcasId != null) || (this.tipoMarcasId != null && !this.tipoMarcasId.equals(other.tipoMarcasId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.claro.mgl.jpa.entities.TecTipoMarcas[ tipoMarcasId=" + tipoMarcasId + " ]";
    }
    
}
