/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
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
 * @author Juan David Hernandez
 */
@Entity
@Table(name = "MGL_VALIDACION_PARAMETRIZADA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValidacionParametrizadaMgl.findAll", query = "SELECT n FROM ValidacionParametrizadaMgl n ")})
public class ValidacionParametrizadaMgl implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "ValidacionParametrizadaMgl.ValidacionParametrizadaSq",
            sequenceName = "MGL_SCHEME.MGL_VAL_PARAMETRIZADA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "ValidacionParametrizadaMgl.ValidacionParametrizadaSq")
    @Column(name = "VALIDACION_PARAMETRIZADA_ID", nullable = false)
    private BigDecimal validacionParametrizadaId;
    
    @Column(name = "NOMBRE_VALIDACION", nullable = true)
    private String nombreValidacion;
    
    @Column(name = "DESCRIPCION_VALIDACION", nullable = true)
    private String descripcionValidacion;
    
    @Column(name = "NOMBRE_CLASE", nullable = true)
    private String nombreClase;
    
    @Column(name = "NOMBRE_METODO", nullable = true)
    private String nombreMetodo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_VALIDACION_BASICA_ID", referencedColumnName = "BASICA_ID", nullable = false)  
    private CmtBasicaMgl tipoValidacionBasicaId;        
    
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro; 

    public BigDecimal getValidacionParametrizadaId() {
        return validacionParametrizadaId;
    }

    public void setValidacionParametrizadaId(BigDecimal validacionParametrizadaId) {
        this.validacionParametrizadaId = validacionParametrizadaId;
    }

    public String getNombreValidacion() {
        return nombreValidacion;
    }

    public void setNombreValidacion(String nombreValidacion) {
        this.nombreValidacion = nombreValidacion;
    }

    public String getDescripcionValidacion() {
        return descripcionValidacion;
    }

    public void setDescripcionValidacion(String descripcionValidacion) {
        this.descripcionValidacion = descripcionValidacion;
    }    

    public String getNombreClase() {
        return nombreClase;
    }

    public void setNombreClase(String nombreClase) {
        this.nombreClase = nombreClase;
    }

    public String getNombreMetodo() {
        return nombreMetodo;
    }

    public void setNombreMetodo(String nombreMetodo) {
        this.nombreMetodo = nombreMetodo;
    }

    public CmtBasicaMgl getTipoValidacionBasicaId() {
        return tipoValidacionBasicaId;
    }

    public void setTipoValidacionBasicaId(CmtBasicaMgl tipoValidacionBasicaId) {
        this.tipoValidacionBasicaId = tipoValidacionBasicaId;
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

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
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

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
    
}
