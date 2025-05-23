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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * objetivo de la clase. 
 * Clase JPA entity que mapea a la tabla del mismo nombre
 *
 * @author Carlos Leonardo Villamil
 * @versión 1.00.000
 */
@Entity
@Table(name = "MGL_MULTIVALOR", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Multivalor.findAll", query = "SELECT m FROM Multivalor m"),
    @NamedQuery(name = "Multivalor.findByIdGmu", query = "SELECT m FROM Multivalor m where m.gmuId = :gmuId  ORDER BY M.mulDescripcion ASC "),
    @NamedQuery(name = "Multivalor.findByIdGmuAndmulValor", query = "SELECT m FROM Multivalor m where m.gmuId = :gmuId and m.mulValor= :mulValor")
})

public class Multivalor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "Multivalor.MGL_MULTIVALOR",
            sequenceName = "MGL_SCHEME.MGL_MULTIVALOR", allocationSize = 1)
    @GeneratedValue(generator = "Multivalor.MGL_MULTIVALOR")
    @Column(name = "MULTIVALOR_ID", nullable = false)
    private BigDecimal mulId;
    @Column(name = "GRUPO_MULTIVALOR_ID")
    private BigDecimal gmuId;
    @Column(name = "VALOR")
    private String mulValor;
    @Column(name = "DESCRIPCION")
    private String mulDescripcion;
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

    public BigDecimal getMulId() {
        return mulId;
    }

    public void setMulId(BigDecimal mulId) {
        this.mulId = mulId;
    }

    public BigDecimal getGmuId() {
        return gmuId;
    }

    public void setGmuId(BigDecimal gmuId) {
        this.gmuId = gmuId;
    }

    public String getMulValor() {
        return mulValor;
    }

    public void setMulValor(String mulValor) {
        this.mulValor = mulValor;
    }

    public String getMulDescripcion() {
        return mulDescripcion;
    }

    public void setMulDescripcion(String mulDescripcion) {
        this.mulDescripcion = mulDescripcion;
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

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

}
