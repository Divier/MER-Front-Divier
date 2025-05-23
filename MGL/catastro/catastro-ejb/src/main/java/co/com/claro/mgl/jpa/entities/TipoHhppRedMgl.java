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
@Table(name = "TEC_TIPO_RED_TEC_HABI", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoHhppRedMgl.findAll", query = "SELECT s FROM TipoHhppRedMgl s")})
public class TipoHhppRedMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "TipoHhppRedMgl.TEC_TIPO_RED_TEC_HABI_SQ",
            sequenceName = "MGL_SCHEME.TEC_TIPO_RED_TEC_HABI_SQ", allocationSize = 1)
    @GeneratedValue(generator = "TipoHhppRedMgl.TEC_TIPO_RED_TEC_HABI_SQ")
    @Column(name = "TIPO_RED_TEC_HABI_ID", nullable = false)
    private BigDecimal thrId;
    @Column(name = "CODIGO")
    private String thrCodigo;
    @Column(name = "NOMBRE")
    private String thrNokmbre;
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

    public BigDecimal getThrId() {
        return thrId;
    }

    public void setThrId(BigDecimal thrId) {
        this.thrId = thrId;
    }

    public String getThrNokmbre() {
        return thrNokmbre;
    }

    public void setThrNokmbre(String thrNokmbre) {
        this.thrNokmbre = thrNokmbre;
    }

    public String getThrCodigo() {
        return thrCodigo;
    }

    public void setThrCodigo(String thrCodigo) {
        this.thrCodigo = thrCodigo;
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
        return "co.com.claro.mgl.jpa.entities.TipoHhppRedMgl[ id=" + thrId + ", THR_NOMBRE = " + thrNokmbre + "  ]";
    }
    
}
