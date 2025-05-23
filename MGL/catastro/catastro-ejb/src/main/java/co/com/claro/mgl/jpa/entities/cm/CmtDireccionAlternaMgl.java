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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "MGL_DIRECCION_ALTERNA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtDireccionAlternaMgl.findAll", query = "SELECT c FROM CmtDireccionAlternaMgl c ")})
public class CmtDireccionAlternaMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtDireccionAlternaMgl.CmtDireccionAlternaMglSeq",
            sequenceName = "MGL_SCHEME.MGL_DIRECCION_ALTERNA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtDireccionAlternaMgl.CmtDireccionAlternaMglSeq")
    
    @Column(name = "DIRECCION_ALTERNA_ID", nullable = false)
    private BigDecimal diaId;
    @Column(name = "DIRECCION_ID")
    private BigDecimal dirId;
    @Column(name = "DIRECCION_IGAC")
    private String diaDireccionIgac;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;  
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;  
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;  

    @Transient
    private boolean seleccionado;

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public BigDecimal getDiaId() {
        return diaId;
    }

    public void setDiaId(BigDecimal diaId) {
        this.diaId = diaId;
    }

    public BigDecimal getDirId() {
        return dirId;
    }

    public void setDirId(BigDecimal dirId) {
        this.dirId = dirId;
    }

    public String getDiaDireccionIgac() {
        return diaDireccionIgac;
    }

    public void setDiaDireccionIgac(String diaDireccionIgac) {
        this.diaDireccionIgac = diaDireccionIgac;
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
}
