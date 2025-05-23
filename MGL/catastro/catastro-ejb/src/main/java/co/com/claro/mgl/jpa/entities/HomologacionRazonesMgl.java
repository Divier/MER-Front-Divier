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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "MGL_HOMOLOGACION_RAZONES", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HomologacionRazonesMgl.findAll", query = "SELECT a FROM HomologacionRazonesMgl a WHERE a.estadoRegistro=1")
})
public class HomologacionRazonesMgl implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "HomologacionRazonesMgl.seq_homologacionRazones",
            sequenceName = "MGL_SCHEME.MGL_HOMOLOGACION_RAZONES_SQ", allocationSize = 1
    )
    @GeneratedValue(generator = "HomologacionRazonesMgl.seq_homologacionRazones")
    @Column(name = "HOMOLOGACION_RAZONES_ID", nullable = false)
    private BigDecimal homologacionRazonesId;
    @Column(name = "CODIGO_RAZON_OFSC_MER")
    private String codRazonOfscMer;
    @Column(name = "DESCRIPCION_OFSC_MER")
    private String descripcionOfscMer;
    @Column(name = "CODIGO_RES_ONIX")
    private String codResOnix;
    @Column(name = "DESCRIPCION_ONYX")
    private String descripcionOnyx;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;

    public BigDecimal getHomologacionRazonesId() {
        return homologacionRazonesId;
    }

    public void setHomologacionRazonesId(BigDecimal homologacionRazonesId) {
        this.homologacionRazonesId = homologacionRazonesId;
    }

    public String getCodRazonOfscMer() {
        return codRazonOfscMer;
    }

    public void setCodRazonOfscMer(String codRazonOfscMer) {
        this.codRazonOfscMer = codRazonOfscMer;
    }

    public String getDescripcionOfscMer() {
        return descripcionOfscMer;
    }

    public void setDescripcionOfscMer(String descripcionOfscMer) {
        this.descripcionOfscMer = descripcionOfscMer;
    }

    public String getCodResOnix() {
        return codResOnix;
    }

    public void setCodResOnix(String codResOnix) {
        this.codResOnix = codResOnix;
    }

    public String getDescripcionOnyx() {
        return descripcionOnyx;
    }

    public void setDescripcionOnyx(String descripcionOnyx) {
        this.descripcionOnyx = descripcionOnyx;
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

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

}
