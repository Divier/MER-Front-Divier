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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "TEC_TIPO_CONEXION_TEC_HABI", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoHhppConexionMgl.findAll", query = "SELECT s FROM TipoHhppConexionMgl s")})
public class TipoHhppConexionMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "TipoHhppConexionMgl.TEC_TIPO_CONEXION_TEC_HABI_SQ",
            sequenceName = "MGL_SCHEME.TEC_TIPO_CONEXION_TEC_HABI_SQ", allocationSize = 1)
    @GeneratedValue(generator = "TipoHhppConexionMgl.TEC_TIPO_CONEXION_TEC_HABI_SQ")
    @Column(name = "TIPO_CONEXION_TEC_HABI_ID", nullable = false)
    private BigDecimal thcId;
    @Column(name = "NOMBRE")
    private String thcNombre;
    @Column(name = "CODIGO  ")
    private String thcCodigo;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;

    public BigDecimal getThcId() {
        return thcId;
    }

    public void setThcId(BigDecimal thcId) {
        this.thcId = thcId;
    }

    public String getThcNombre() {
        return thcNombre;
    }

    public void setThcNombre(String thcNombre) {
        this.thcNombre = thcNombre;
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

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }
     
    @Override
    public String toString() {
        return "co.com.claro.mgl.jpa.entities.TipoHhppConexionMgl[ id=" + thcId + ", THC_NOMBRE = " + thcNombre + "  ]";
    }

  /**
   * @return the thcCodigo
   */
  public String getThcCodigo() {
    return thcCodigo;
  }

  /**
   * @param thcCodigo the thcCodigo to set
   */
  public void setThcCodigo(String thcCodigo) {
    this.thcCodigo = thcCodigo;
  }
  
}
