/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Parzifal de León
 */
@Entity
@Table(name = "TEC_ESTADO", schema = "MGL_SCHEME")
@XmlRootElement
public class EstadoHhpp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ESTADO_ID", nullable=false)
    private String ehhId;
    @Column(name="NOMBRE")
    private String ehhNombre;
    @Column(name="FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date ehhFechaCreacion;
    @Column(name="USUARIO_CREACION")
    private String ehhUsuarioCreacion;
    @Column(name="FECHA_MODIFICACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date ehhFechaModificacion;
    @Column(name="USUARIO_MODIFICACION")
    private String ehhUsuarioModificacion;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;

    public String getEhhId() {
        return ehhId;
    }

    public void setEhhId(String ehhId) {
        this.ehhId = ehhId;
    }

    public String getEhhNombre() {
        return ehhNombre;
    }

    public void setEhhNombre(String ehhNombre) {
        this.ehhNombre = ehhNombre;
    }

    public Date getEhhFechaCreacion() {
        return ehhFechaCreacion;
    }

    public void setEhhFechaCreacion(Date ehhFechaCreacion) {
        this.ehhFechaCreacion = ehhFechaCreacion;
    }

    public String getEhhUsuarioCreacion() {
        return ehhUsuarioCreacion;
    }

    public void setEhhUsuarioCreacion(String ehhUsuarioCreacion) {
        this.ehhUsuarioCreacion = ehhUsuarioCreacion;
    }

    public Date getEhhFechaModificacion() {
        return ehhFechaModificacion;
    }

    public void setEhhFechaModificacion(Date ehhFechaModificacion) {
        this.ehhFechaModificacion = ehhFechaModificacion;
    }

    public String getEhhUsuarioModificacion() {
        return ehhUsuarioModificacion;
    }

    public void setEhhUsuarioModificacion(String ehhUsuarioModificacion) {
        this.ehhUsuarioModificacion = ehhUsuarioModificacion;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

}
