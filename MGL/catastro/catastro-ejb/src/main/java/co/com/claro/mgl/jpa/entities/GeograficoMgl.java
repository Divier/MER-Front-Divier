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
@Table(name = "MGL_GEOGRAFICO", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GeograficoMgl.findAll", query = "SELECT s FROM GeograficoMgl s"),
    @NamedQuery(name = "GeograficoMgl.findByIdGeograficoPolitico", query = "SELECT s FROM GeograficoMgl s where s.gpoId = :gpoId")})
public class GeograficoMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "GeograficoMgl.MGL_GEOGRAFICO_SQ",
            sequenceName = "MGL_SCHEME.MGL_GEOGRAFICO_SQ", allocationSize = 1)
    @GeneratedValue(generator = "GeograficoMgl.MGL_GEOGRAFICO_SQ")
    @Column(name = "GEOGRAFICO_ID", nullable = false)
    private BigDecimal geoId;
    @Column(name = "NOMBRE")
    private String geoNombre;
    @Column(name = "GEOGRAFICO_POLITICO_ID", nullable = false)
    private BigDecimal gpoId;
    @Column(name = "TIPO_GEOGRAFICO_ID", nullable = false)
    private BigDecimal tipoGeografico;
    @Column(name = "GEOGRAFICO_GEOGRAFICO_ID ", nullable = false)
    private BigDecimal geograficoSuperior;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION")
    private int  perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;

    
    public BigDecimal getGeoId() {
        return geoId;
    }

    public void setGeoId(BigDecimal geoId) {
        this.geoId = geoId;
    }

    public String getGeoNombre() {
        return geoNombre;
    }

    public void setGeoNombre(String geoNombre) {
        this.geoNombre = geoNombre;
    }

    public BigDecimal getGpoId() {
        return gpoId;
    }

    public void setGpoId(BigDecimal gpoId) {
        this.gpoId = gpoId;
    }

    public BigDecimal getTipoGeografico() {
        return tipoGeografico;
    }

    public void setTipoGeografico(BigDecimal tipoGeografico) {
        this.tipoGeografico = tipoGeografico;
    }

    public BigDecimal getGeograficoSuperior() {
        return geograficoSuperior;
    }

    public void setGeograficoSuperior(BigDecimal geograficoSuperior) {
        this.geograficoSuperior = geograficoSuperior;
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
        return "co.com.claro.mgl.jpa.entities.GEOGRAFICO[ GEO_ID = " + geoId + ", GEO_NOMBRE = " + geoNombre + "  ]";
    }

}
