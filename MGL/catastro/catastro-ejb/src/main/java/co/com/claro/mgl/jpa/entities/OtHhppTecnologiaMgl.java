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
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
   
 /**
 *
 * @author Juan David Hernandez
 */
@Entity
@Table(name = "MGL_OT_DIRECCION_TECNOLOGIA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtHhppTecnologiaMgl.findAll", query = "SELECT m FROM OtHhppTecnologiaMgl m where m.estadoRegistro = 1")})
public class OtHhppTecnologiaMgl implements Serializable {

    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "OtHhppTecnologiaMgl.OtHhppTecnologiaMglSq",
            sequenceName = "MGL_SCHEME.MGL_OT_DIR_TECNOLOGIA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "OtHhppTecnologiaMgl.OtHhppTecnologiaMglSq")
    @Column(name = "OT_DIR_TECNOLOGIA_ID", nullable = false)
    private BigDecimal otHhppTecnologiaId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OT_DIRECCION_ID", referencedColumnName = "OT_DIRECCION_ID", nullable = false)
    private OtHhppMgl otHhppId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TECNOLOGIA_BASICA_ID", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl tecnoglogiaBasicaId;   
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
    @Column(name = "TECNOLOGIA_VIABLE",length = 1)
    private String tecnologiaViable;
    
    @Column(name = "AMPLIACION_TAB",length = 1)
    private String ampliacionTab;
    
    @Column(name = "SINCRONIZA_RR")
    private String sincronizaRr;
    
    @NotNull
    @JoinColumn(name = "NODO_ID", referencedColumnName = "NODO_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private NodoMgl nodo;
    
    public BigDecimal getOtHhppTecnologiaId() {
        return otHhppTecnologiaId;
    }

    public void setOtHhppTecnologiaId(BigDecimal otHhppTecnologiaId) {
        this.otHhppTecnologiaId = otHhppTecnologiaId;
    }

    public OtHhppMgl getOtHhppId() {
        return otHhppId;
    }

    public void setOtHhppId(OtHhppMgl otHhppId) {
        this.otHhppId = otHhppId;
    }

    public CmtBasicaMgl getTecnoglogiaBasicaId() {
        return tecnoglogiaBasicaId;
    }

    public void setTecnoglogiaBasicaId(CmtBasicaMgl tecnoglogiaBasicaId) {
        this.tecnoglogiaBasicaId = tecnoglogiaBasicaId;
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

    public String getTecnologiaViable() {
        return tecnologiaViable;
    }

    public void setTecnologiaViable(String tecnologiaViable) {
        this.tecnologiaViable = tecnologiaViable;
    }

    public NodoMgl getNodo() {
        return nodo;
    }

    public void setNodo(NodoMgl nodo) {
        this.nodo = nodo;
    }
    
    @PrePersist
    public void prePersist() {
        this.estadoRegistro = 1;
        this.tecnologiaViable = "N";
    }

    public String getAmpliacionTab() {
        return ampliacionTab;
    }

    public void setAmpliacionTab(String ampliacionTab) {
        this.ampliacionTab = ampliacionTab;
    }

    public String getSincronizaRr() {
        return sincronizaRr;
    }

    public void setSincronizaRr(String sincronizaRr) {
        this.sincronizaRr = sincronizaRr;
    }
    
}
