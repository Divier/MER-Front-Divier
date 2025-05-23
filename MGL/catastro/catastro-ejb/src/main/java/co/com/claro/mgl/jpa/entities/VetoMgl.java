/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "TEC_VETO", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VetoMgl.findAll", query = "SELECT n FROM VetoMgl n Where n.estadoRegistro = 1 ")})
public class VetoMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "VetoMgl.VetoMglSq",
            sequenceName = "MGL_SCHEME.TEC_VETO_SQ", allocationSize = 1)
    @GeneratedValue(generator = "VetoMgl.VetoMglSq")
    @Column(name = "VETO_ID", nullable = false)
    private BigDecimal vetoId;
    
    @Column(name = "NUM_POLITICA", nullable = false)
    private String numeroPolitica;
    
    @Column(name = "CORREO", nullable = false)
    private String correo;
    
    @Column(name = "FECHA_INICIO")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaInicio;
    
    @Column(name = "FECHA_FIN")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaFin;    
    
    @Column(name = "TIPO_VETO", nullable = false)
    private String tipoVeto;
      
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
    private String vigencia;
    
    @OneToMany(mappedBy = "vetoId", fetch = FetchType.LAZY)
    List<VetoNodosMgl> vetoNodosList;
    
    @OneToMany(mappedBy = "vetoId", fetch = FetchType.LAZY)
    List<VetoCiudadMgl> vetoCiudadList;
    
    @OneToMany(mappedBy = "vetoId", fetch = FetchType.LAZY)
    List<VetoCanalMgl> vetoCanalList;



    public BigDecimal getVetoId() {
        return vetoId;
    }

    public void setVetoId(BigDecimal vetoId) {
        this.vetoId = vetoId;
    }

    public String getNumeroPolitica() {
        return numeroPolitica;
    }

    public void setNumeroPolitica(String numeroPolitica) {
        this.numeroPolitica = numeroPolitica;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
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

    public List<VetoNodosMgl> getVetoNodosList() {
        return vetoNodosList;
    }

    public void setVetoNodosList(List<VetoNodosMgl> vetoNodosList) {
        this.vetoNodosList = vetoNodosList;
    }

    public List<VetoCiudadMgl> getVetoCiudadList() {
        return vetoCiudadList;
    }

    public void setVetoCiudadList(List<VetoCiudadMgl> vetoCiudadList) {
        this.vetoCiudadList = vetoCiudadList;
    }

    public List<VetoCanalMgl> getVetoCanalList() {
        return vetoCanalList;
    }

    public void setVetoCanalList(List<VetoCanalMgl> vetoCanalList) {
        this.vetoCanalList = vetoCanalList;
    }

    public String getTipoVeto() {
        return tipoVeto;
    }

    public void setTipoVeto(String tipoVeto) {
        this.tipoVeto = tipoVeto;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }
    
    


 
    
}
