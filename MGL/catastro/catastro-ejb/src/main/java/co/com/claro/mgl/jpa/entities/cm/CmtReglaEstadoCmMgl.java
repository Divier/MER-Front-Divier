/*
 * To change this template, choose Tools | Templates
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan David Hernandez
 */
@Entity
@Table(name = "CMT_REGLA_ESTADO_CM", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtReglaEstadoCmMgl.findAll", query = "SELECT n FROM CmtReglaEstadoCmMgl n ")})
public class CmtReglaEstadoCmMgl implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtReglaEstadoCmMgl.CmtReglaEstadoCmMglSq",
            sequenceName = "MGL_SCHEME.CMT_REGLA_ESTADO_CM_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtReglaEstadoCmMgl.CmtReglaEstadoCmMglSq")
    @Column(name = "REGLA_ESTADO_CM_ID", nullable = false)
    private BigDecimal reglaEstadoCmId;
    
    @Column(name = "NUMERO_REGLA")
    private String numeroRegla;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TECNOLOGIA_BASICA_ID", referencedColumnName = "BASICA_ID", nullable = false)  
    private CmtBasicaMgl tecnologiaBasicaId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADO_RR_ID", referencedColumnName = "BASICA_ID", nullable = false)  
    private CmtBasicaMgl estadoCmBasicaId;   
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADO_TEC_BASICA_ID", referencedColumnName = "BASICA_ID", nullable = false)  
    private CmtBasicaMgl estadoTecBasicaId;       
   
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

    public BigDecimal getReglaEstadoCmId() {
        return reglaEstadoCmId;
    }

    public void setReglaEstadoCmId(BigDecimal reglaEstadoCmId) {
        this.reglaEstadoCmId = reglaEstadoCmId;
    }

    public CmtBasicaMgl getTecnologiaBasicaId() {
        return tecnologiaBasicaId;
    }

    public void setTecnologiaBasicaId(CmtBasicaMgl tecnologiaBasicaId) {
        this.tecnologiaBasicaId = tecnologiaBasicaId;
    }

    public CmtBasicaMgl getEstadoCmBasicaId() {
        return estadoCmBasicaId;
    }

    public void setEstadoCmBasicaId(CmtBasicaMgl estadoCmBasicaId) {
        this.estadoCmBasicaId = estadoCmBasicaId;
    }

    public String getNumeroRegla() {
        return numeroRegla;
    }

    public void setNumeroRegla(String numeroRegla) {
        this.numeroRegla = numeroRegla;
    }

    public CmtBasicaMgl getEstadoTecBasicaId() {
        return estadoTecBasicaId;
    }

    public void setEstadoTecBasicaId(CmtBasicaMgl estadoTecBasicaId) {
        this.estadoTecBasicaId = estadoTecBasicaId;
    }
    
        
}
