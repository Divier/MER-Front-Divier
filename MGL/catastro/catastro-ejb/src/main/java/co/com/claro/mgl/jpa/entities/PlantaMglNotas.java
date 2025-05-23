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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;



/**
 *
 * @author JPeña
 */

@Entity
@Table(name = "TEC_CONF_PLANTA_NOTAS", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({})
public class PlantaMglNotas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
     @NotNull
    @SequenceGenerator(
            name = "PlantaMglNotas.PlantaMglNotasSeq",
            sequenceName = "MGL_SCHEME.TEC_CONF_PLANTA_NOTAS_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "PlantaMglNotas.PlantaMglNotasSeq")  
    @Column(name = "NOTECONFIGURACIONPLANTAID")
    private BigDecimal noteconfiguracionplantaid;
    @Size(max = 2000)
    @Column(name = "NOTE")
    private String note;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 50)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION")
    private BigDecimal perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Size(max = 50)
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private BigDecimal perfilEdicion;
    @JoinColumn(name = "CONFIGURACIONPLANTAID", referencedColumnName = "CONFIGURACIONPLANTAID")
    @ManyToOne
    private PlantaMgl configuracionplantaid;

    public PlantaMglNotas() {
    }

    public PlantaMglNotas(BigDecimal noteconfiguracionplantaid) {
        this.noteconfiguracionplantaid = noteconfiguracionplantaid;
    }

    public BigDecimal getNoteconfiguracionplantaid() {
        return noteconfiguracionplantaid;
    }

    public void setNoteconfiguracionplantaid(BigDecimal noteconfiguracionplantaid) {
        this.noteconfiguracionplantaid = noteconfiguracionplantaid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public BigDecimal getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(BigDecimal perfilCreacion) {
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

    public BigDecimal getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(BigDecimal perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public PlantaMgl getConfiguracionplantaid() {
        return configuracionplantaid;
    }

    public void setConfiguracionplantaid(PlantaMgl configuracionplantaid) {
        this.configuracionplantaid = configuracionplantaid;
    }

    @Override
    protected PlantaMglNotas clone() throws CloneNotSupportedException {
        return (PlantaMglNotas)super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    @Override
    public String toString() {
        return "co.com.claro.mgl.jpa.entities.TecConfPlantaNotas[ noteconfiguracionplantaid=" + noteconfiguracionplantaid + " ]";
    }
    
}
