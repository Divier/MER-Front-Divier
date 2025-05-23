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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cardenaslb
 */
@Entity
@Table(name = "CMT_FLUJOS_INICIALES_CM", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtFlujosInicialesCm.findAll", query = "SELECT c FROM CmtFlujosInicialesCm c WHERE  c.estadoRegistro = 1")
})
public class CmtFlujosInicialesCm implements Serializable{
    		
  private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtFlujosInicialesCm.CmtFlujosInicialesCmSq",
            sequenceName = "MGL_SCHEME.CMT_FLUJOS_INICIALES_CM_SQ",
            allocationSize = 1)
    @GeneratedValue(generator = "CmtFlujosInicialesCm.CmtFlujosInicialesCmSq")
    @Column(name = "ID_FLUJO_INI_CM")
    private BigDecimal estadosIniCm;
     @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADO_INICIAL_CM")
    private CmtBasicaMgl idBasicaEstadoCm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADOXFLUJO")
    private CmtEstadoxFlujoMgl idEstadoFlujo;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    public BigDecimal getEstadosIniCm() {
        return estadosIniCm;
    }
    public void setEstadosIniCm(BigDecimal estadosIniCm) {
        this.estadosIniCm = estadosIniCm;
    }
    public CmtEstadoxFlujoMgl getIdEstadoFlujo() {
        return idEstadoFlujo;
    }
    public void setIdEstadoFlujo(CmtEstadoxFlujoMgl idEstadoFlujo) {
        this.idEstadoFlujo = idEstadoFlujo;
    }
  
    
    public Date getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public Date getFechaEdicion() {
        return fechaEdicion;
    }
    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
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
    public int getEstadoRegistro() {
        return estadoRegistro;
    }
    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
    public CmtBasicaMgl getIdBasicaEstadoCm() {
        return idBasicaEstadoCm;
    }
    public void setIdBasicaEstadoCm(CmtBasicaMgl idBasicaEstadoCm) {
        this.idBasicaEstadoCm = idBasicaEstadoCm;
    }
}
