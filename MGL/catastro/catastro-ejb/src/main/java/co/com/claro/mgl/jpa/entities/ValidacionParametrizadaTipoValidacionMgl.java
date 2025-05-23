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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan David Hernandez
 */
@Entity
@Table(name = "MGL_VAL_PARAMETRIZADA_TIPO_VAL", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValidacionParametrizadaTipoValidacionMgl.findAll", query = "SELECT n FROM ValidacionParametrizadaTipoValidacionMgl n ")})
public class ValidacionParametrizadaTipoValidacionMgl implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "ValidacionParametrizadaTipoValidacionMgl.ValidacionParametrizadaTipoValSq",
            sequenceName = "MGL_SCHEME.MGL_VAL_PARAMETRIZADA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "ValidacionParametrizadaTipoValidacionMgl.ValidacionParametrizadaTipoValSq")
    @Column(name = "VAL_PARAMETRIZADA_TIPO_VAL_ID", nullable = false)
    private BigDecimal valParametrizadaTipoValId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_VALIDACION_BASICA_ID", referencedColumnName = "BASICA_ID", nullable = false)  
    private CmtBasicaMgl tipoValidacionBasicaId;   
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VALIDACION_PARAMETRIZADA_ID", referencedColumnName = "VALIDACION_PARAMETRIZADA_ID", nullable = false)  
    private ValidacionParametrizadaMgl validacionParametrizadaId;
    
    @Column(name = "VALIDACION_ACTIVA")
    private BigDecimal validacionActiva;
    
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
   
    @Transient 
    private boolean validacionSeleccionada;
    

    public BigDecimal getValParametrizadaTipoValId() {
        return valParametrizadaTipoValId;
    }

    public void setValParametrizadaTipoValId(BigDecimal valParametrizadaTipoValId) {
        this.valParametrizadaTipoValId = valParametrizadaTipoValId;
    }

    public ValidacionParametrizadaMgl getValidacionParametrizadaId() {
        return validacionParametrizadaId;
    }

    public void setValidacionParametrizadaId(ValidacionParametrizadaMgl validacionParametrizadaId) {
        this.validacionParametrizadaId = validacionParametrizadaId;
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

    public BigDecimal getValidacionActiva() {
        return validacionActiva;
    }

    public void setValidacionActiva(BigDecimal validacionActiva) {
        this.validacionActiva = validacionActiva;
    }

    public boolean isValidacionSeleccionada() {
        return validacionSeleccionada;
    }

    public void setValidacionSeleccionada(boolean validacionSeleccionada) {
        this.validacionSeleccionada = validacionSeleccionada;
    }
    
    
    
}
