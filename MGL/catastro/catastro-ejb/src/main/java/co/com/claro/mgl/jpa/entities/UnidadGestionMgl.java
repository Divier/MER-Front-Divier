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
@Table(name = "MGL_UNIDAD_GESTION", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
@NamedQuery(name = "UnidadGestionMgl.findAll", query = "SELECT s FROM UnidadGestionMgl s")})
public class UnidadGestionMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "UnidadGestionMgl.MGL_UNIDAD_GESTION_SQ", schema = "MGL_SCHEME",
            sequenceName = "MGL_SCHEME.MGL_UNIDAD_GESTION_SQ", allocationSize = 1)
    @GeneratedValue(generator = "UnidadGestionMgl.MGL_UNIDAD_GESTION_SQ")
    @Column(name = "UNIDAD_GESTION_ID", nullable = false)
    private BigDecimal ugeId;
    @Column(name = "NOMBRE")
    private String ugeNombre;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION")
    private Integer perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private Integer perfilEdicion;

    public BigDecimal getUgeId() {
        return ugeId;
    }

    public void setUgeId(BigDecimal ugeId) {
        this.ugeId = ugeId;
    }

    public String getUgeNombre() {
        return ugeNombre;
    }

    public void setUgeNombre(String ugeNombre) {
        this.ugeNombre = ugeNombre;
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

    public Integer getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(Integer perfilCreacion) {
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

    public Integer getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(Integer perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    @Override
    public String toString() {
        return "co.com.claro.mgl.jpa.entities.UNIDAD_GESTION[ UGE_ID = " + ugeId + ", UGE_NOMBRE = " + ugeNombre + "  ]";
    }
    
}
