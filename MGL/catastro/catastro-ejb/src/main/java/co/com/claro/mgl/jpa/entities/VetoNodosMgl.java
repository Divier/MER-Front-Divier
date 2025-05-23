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
@Table(name = "TEC_VETO_NODO", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VetoNodosMgl.findAll", query = "SELECT n FROM VetoNodosMgl n ")})
public class VetoNodosMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "VetoNodosMgl.VetoNodosMglSq",
            sequenceName = "MGL_SCHEME.TEC_VETO_NODO_SQ", allocationSize = 1)
    @GeneratedValue(generator = "VetoNodosMgl.VetoNodosMglSq")
    @Column(name = "VETO_NODO_ID", nullable = false)
    private BigDecimal vetoNodosId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VETO_ID", referencedColumnName = "VETO_ID", nullable = false)  
    private VetoMgl vetoId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NODO_ID", referencedColumnName = "NODO_ID", nullable = false)  
    private NodoMgl nodoMgl;    
   
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


    public BigDecimal getVetoNodosId() {
        return vetoNodosId;
    }

    public void setVetoNodosId(BigDecimal vetoNodosId) {
        this.vetoNodosId = vetoNodosId;
    }

    public NodoMgl getNodoMgl() {
        return nodoMgl;
    }

    public void setNodoMgl(NodoMgl nodoMgl) {
        this.nodoMgl = nodoMgl;
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

    public VetoMgl getVetoId() {
        return vetoId;
    }

    public void setVetoId(VetoMgl vetoId) {
        this.vetoId = vetoId;
    }

    
    

}
