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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "TEC_VETO_NODO", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VetoNodoMGL.findAll", query = "SELECT v FROM VetoNodo v"),
    @NamedQuery(name = "VetoNodoMGL.findNodoByIdNodFecha", query = "SELECT v FROM VetoNodo v where v.nodId = :nodId and CURRENT_DATE BETWEEN v.vetFechaInicio and v.vetFechaFin"),
    @NamedQuery(name = "VetoNodoMGL.findVetosActivos", query = "SELECT v FROM VetoNodo v where CURRENT_DATE BETWEEN v.vetFechaInicio and v.vetFechaFin")})
                                                                
public class VetoNodo implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "VETO_NODO_ID", nullable = false)
    private BigDecimal vetId; 
    @ManyToOne()
    @JoinColumn(name = "NODO_ID", referencedColumnName = "NODO_ID", nullable = false)
    private NodoMgl nodId;
    @Column(name = "GEOGRAFICO_POLITICO_ID", nullable = false)
    private BigDecimal gpoId;
    @Column(name = "POLITICA", nullable = false)
    private String vetPolitica;
    @Column(name = "AREA")
    private String vetArea;
    @Column(name = "CORREO", nullable = false)
    private String vetCorreo;
    @Column(name = "FECHA_INI", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date vetFechaInicio;
    @Column(name = "FECHA_FIN", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date vetFechaFin;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    
    
    

    public BigDecimal getVetId() {
        return vetId;
    }

    public void setVetId(BigDecimal vetId) {
        this.vetId = vetId;
    }

    public NodoMgl getNodId() {
        return nodId;
    }

    public void setNodId(NodoMgl nodId) {
        this.nodId = nodId;
    }

    public BigDecimal getGpoId() {
        return gpoId;
    }

    public void setGpoId(BigDecimal gpoId) {
        this.gpoId = gpoId;
    }

    public String getVetPolitica() {
        return vetPolitica;
    }

    public void setVetPolitica(String vetPolitica) {
        this.vetPolitica = vetPolitica;
    }

    public String getVetArea() {
        return vetArea;
    }

    public void setVetArea(String vetArea) {
        this.vetArea = vetArea;
    }

    public String getVetCorreo() {
        return vetCorreo;
    }

    public void setVetCorreo(String vetCorreo) {
        this.vetCorreo = vetCorreo;
    }

    public Date getVetFechaInicio() {
        return vetFechaInicio;
    }

    public void setVetFechaInicio(Date vetFechaInicio) {
        this.vetFechaInicio = vetFechaInicio;
    }

    public Date getVetFechaFin() {
        return vetFechaFin;
    }

    public void setVetFechaFin(Date vetFechaFin) {
        this.vetFechaFin = vetFechaFin;
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

    public Integer getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(Integer perfilCreacion) {
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

    public Integer getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(Integer perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }
    
}
