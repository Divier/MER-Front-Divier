/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cardenaslb
 */
@Entity
@Table(name = "MGL_BASICA_EXT", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtBasicaExtMgl.findAll", query = "SELECT c FROM CmtBasicaExtMgl c"),
    @NamedQuery(name = "CmtBasicaExtMgl.findByIdBasicaExt", query = "SELECT c FROM CmtBasicaExtMgl c WHERE c.idBasicaExt = :idBasicaExt and c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtBasicaExtMgl.findByValorExtendido", query = "SELECT c FROM CmtBasicaExtMgl c WHERE c.valorExtendido = :valorExtendido and c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtBasicaExtMgl.findByIdTipoBasicaExt", query = "SELECT c FROM CmtBasicaExtMgl c WHERE c.idTipoBasicaExt = :idTipoBasicaExt and c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtBasicaExtMgl.findByBasicaId", query = "SELECT c FROM CmtBasicaExtMgl c WHERE c.idBasicaObj.basicaId = :basicaId and c.estadoRegistro = 1")})

public class CmtBasicaExtMgl implements Serializable, Comparable<CmtBasicaExtMgl> {

    private static final long serialVersionUID = 1L;
   
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtBasicaExtMgl.CmtBasicaExtMglSq",
            sequenceName = "MGL_SCHEME.MGL_BASICA_EXT_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtBasicaExtMgl.CmtBasicaExtMglSq")
    @Column(name = "ID_BASICA_EXT", nullable = false, precision = 20, scale = 0)
    private BigDecimal idBasicaExt;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "VALOR_EXTENDIDO", nullable = false, length = 20)
    private String valorExtendido;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_BASICA_EXT")
    private CmtTipoBasicaExtMgl idTipoBasicaExt;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_BASICA")
    private CmtBasicaMgl idBasicaObj;
    
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_CREACION", nullable = false)
    private Date fechaCreacion;
    
    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_CREACION", nullable = false, length = 20)
    private String usuarioCreacion;
    
    @NotNull
    @Basic(optional = false)
    @Column(name = "PERFIL_CREACION", nullable = false)
    private int perfilCreacion;
    
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    
    @Size(max = 20)
    @Column(name = "USUARIO_EDICION", length = 20)
    private String usuarioEdicion;
    
    @NotNull
    @Basic(optional = false)
    @Column(name = "PERFIL_EDICION", nullable = false)
    private int perfilEdicion;
    
    @NotNull
    @Basic(optional = false)
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private int estadoRegistro;

    public CmtBasicaExtMgl() {
    }

    public CmtBasicaExtMgl(BigDecimal idBasicaExt) {
        this.idBasicaExt = idBasicaExt;
    }

    public CmtBasicaExtMgl(BigDecimal idBasicaExt, String valorExtendido) {
        this.idBasicaExt = idBasicaExt;
        this.valorExtendido = valorExtendido;
    }

    public BigDecimal getIdBasicaExt() {
        return idBasicaExt;
    }

    public void setIdBasicaExt(BigDecimal idBasicaExt) {
        this.idBasicaExt = idBasicaExt;
    }

    public String getValorExtendido() {
        return valorExtendido;
    }

    public void setValorExtendido(String valorExtendido) {
        this.valorExtendido = valorExtendido;
    }

    public CmtTipoBasicaExtMgl getIdTipoBasicaExt() {
        return idTipoBasicaExt;
    }

    public void setIdTipoBasicaExt(CmtTipoBasicaExtMgl idTipoBasicaExt) {
        this.idTipoBasicaExt = idTipoBasicaExt;
    }

    public CmtBasicaMgl getIdBasicaObj() {
        return idBasicaObj;
    }

    public void setIdBasicaObj(CmtBasicaMgl idBasicaObj) {
        this.idBasicaObj = idBasicaObj;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBasicaExt != null ? idBasicaExt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmtBasicaExtMgl)) {
            return false;
        }
        CmtBasicaExtMgl other = (CmtBasicaExtMgl) object;
        if ((this.idBasicaExt == null && other.idBasicaExt != null) || (this.idBasicaExt != null && !this.idBasicaExt.equals(other.idBasicaExt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.claro.mgl.jpa.entities.cm.CmtBasicaExt[ idBasicaExt=" + idBasicaExt + " ]";
    }
    
    @Override
    public int compareTo(CmtBasicaExtMgl basicaExtMgl) {

        if (idTipoBasicaExt.getIdTipoBasicaExt().intValue()
                < basicaExtMgl.getIdTipoBasicaExt().getIdTipoBasicaExt().intValue()) {
            return -1;
        }
        if (idTipoBasicaExt.getIdTipoBasicaExt().intValue()
                > basicaExtMgl.getIdTipoBasicaExt().getIdTipoBasicaExt().intValue()) {
            return 1;
        }

        return 0;
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
}
