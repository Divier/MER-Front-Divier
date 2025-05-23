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
 * @author User
 */
@Entity
@Table(name = "TEC_LOG_ELIMINACION_MASIVA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DirLogEliminacionMasivaMgl.findAll", query = "SELECT d FROM DirLogEliminacionMasivaMgl d")})
public class DirLogEliminacionMasivaMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "DirLogEliminacionMasivaMgl.DirLogseq",
            sequenceName = "MGL_SCHEME.TEC_LOG_ELIMINACION_MASIVA_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "DirLogEliminacionMasivaMgl.DirLogseq")
    @Column(name = "LOG_ELIMINACION_MASIVA_ID", nullable = false)
    private BigDecimal lemID;
    @Column(name = "FECHA_OPERACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lemFechaOperacion;
    @Column(name = "USUARIO_OPERACION")
    private String lemUsuarioOperacion;
    @Column(name = "OBSERVACIONES")
    private String lemObservaciones;
    @Column(name = "ARCHIVO")
    private String lemArchivo;
    @Column(name = "FILTROS")
    private String lemFiltros;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = false)
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION", nullable = false)
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = false)
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION", nullable = false)
    private int perfilEdicion;
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private int estadoRegistro;

    public BigDecimal getLemID() {
        return lemID;
    }

    public void setLemID(BigDecimal lemID) {
        this.lemID = lemID;
    }

    public Date getLemFechaOperacion() {
        return lemFechaOperacion;
    }

    public void setLemFechaOperacion(Date lemFechaOperacion) {
        this.lemFechaOperacion = lemFechaOperacion;
    }

    public String getLemUsuarioOperacion() {
        return lemUsuarioOperacion;
    }

    public void setLemUsuarioOperacion(String lemUsuarioOperacion) {
        this.lemUsuarioOperacion = lemUsuarioOperacion;
    }

    public String getLemObservaciones() {
        return lemObservaciones;
    }

    public void setLemObservaciones(String lemObservaciones) {
        this.lemObservaciones = lemObservaciones;
    }

    public String getLemArchivo() {
        return lemArchivo;
    }

    public void setLemArchivo(String lemArchivo) {
        this.lemArchivo = lemArchivo;
    }

    public String getLemFiltros() {
        return lemFiltros;
    }

    public void setLemFiltros(String lemFiltros) {
        this.lemFiltros = lemFiltros;
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
