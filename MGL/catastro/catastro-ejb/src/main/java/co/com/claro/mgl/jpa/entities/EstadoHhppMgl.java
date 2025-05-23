/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
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
@Table(name = "TEC_ESTADO_TEC_HABILITADA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoHhppMgl.findAll", query = "SELECT e FROM EstadoHhppMgl e WHERE e.estadoRegistro= 1"),
    @NamedQuery(name = "EstadoHhppMgl.findEhhID", query = "SELECT e FROM EstadoHhppMgl e WHERE e.ehhID=:ehhID AND e.estadoRegistro= 1")})
public class EstadoHhppMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "EstadoHhppMgl.TecEstadoTecHabSq",
            sequenceName = "MGL_SCHEME.TEC_ESTADO_TEC_HABILITADA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "EstadoHhppMgl.TecEstadoTecHabSq")
    @Column(name = "ESTADO_TEC_HABILITADA_ID", nullable = false)
    private String ehhID;
    @Column(name = "NOMBRE_ESTADO_TEC_HABILITADA")
    private String ehhNombre;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "FECHA_CREACION", nullable = false)
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
    
    public String getEhhID() {
        return ehhID;
    }

    public void setEhhID(String ehhID) {
        this.ehhID = ehhID;
    }

    public String getEhhNombre() {
        return ehhNombre;
    }

    public void setEhhNombre(String ehhNombre) {
        this.ehhNombre = ehhNombre;
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

    @Override
    public String toString() {
        return "ehhID=" + ehhID + ", ehhNombre=" + ehhNombre;
    }
}
