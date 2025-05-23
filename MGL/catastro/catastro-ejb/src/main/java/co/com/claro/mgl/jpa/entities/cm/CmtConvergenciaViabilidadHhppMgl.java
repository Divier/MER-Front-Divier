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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CMT_CONVERGENCIA_VIABILIDAD", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtConvergenciaViabilidadHhppMgl.findAll", query = "SELECT c FROM CmtConvergenciaViabilidadHhppMgl c"),
    @NamedQuery(name = "CmtConvergenciaViabilidadHhppMgl.countFindAll", query = "SELECT COUNT(1) FROM CmtConvergenciaViabilidadHhppMgl c"),
    @NamedQuery(name = "CmtConvergenciaViabilidadHhppMgl.findAllActivos", query = "SELECT c FROM CmtConvergenciaViabilidadHhppMgl c WHERE c.estadoRegistro=1"),
    @NamedQuery(name = "CmtConvergenciaViabilidadHhppMgl.findByTipoBasica", query = "SELECT c FROM CmtConvergenciaViabilidadHhppMgl c WHERE c.estadoRegistro=1"),
    @NamedQuery(name = "CmtConvergenciaViabilidadHhppMgl.findByRegla", query = "SELECT c FROM CmtConvergenciaViabilidadHhppMgl c WHERE c.estadoRegistro=1 and c.estadoHhpp = :estadoHhpp and c.estadoNodo= :estadoNodo and c.estadoCuentamatriz= :estadoCuentamatriz")
})

public class CmtConvergenciaViabilidadHhppMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtConvergenciaViabilidadHhppMgl.CmtConvergenciaViabilidadHhppSq",
            sequenceName = "MGL_SCHEME.CMT_CONVER_VIABI_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtConvergenciaViabilidadHhppMgl.CmtConvergenciaViabilidadHhppSq")
    @Column(name = "REGLA_ID", nullable = false)
    private BigDecimal reglaId;
    
    @NotNull
    @Basic(optional = false)
    @Column(name = "ESTADO_NODO")
    private String estadoNodo;
    
    @NotNull
    @Basic(optional = false)
    @Column(name = "ESTADO_CM")
    private String estadoCuentamatriz;
    
    @NotNull
    @Basic(optional = false)
    @Column(name = "ESTADO_HHPP")
    private String estadoHhpp;
    
    @NotNull
    @Basic(optional = false)
    @Column(name = "VIABLE")
    private String viable;
    
    @NotNull
    @Basic(optional = false)
    @Column(name = "MENSAJE")
    private String mensaje;
    
    @NotNull
    @Basic(optional = false)
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @NotNull
    @Basic(optional = false)
    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;
    
    @NotNull
    @Basic(optional = false)
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    
    @NotNull
    @Basic(optional = false)
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    
    @NotNull
    @Basic(optional = false)
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;
    
    @NotNull
    @Basic(optional = false)
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    
    @NotNull
    @Basic(optional = false)
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;

    public BigDecimal getReglaId() {
        return reglaId;
    }

    public void setReglaId(BigDecimal reglaId) {
        this.reglaId = reglaId;
    }

    public String getEstadoNodo() {
        return estadoNodo;
    }

    public void setEstadoNodo(String estadoNodo) {
        this.estadoNodo = estadoNodo;
    }

    public String getEstadoCuentamatriz() {
        return estadoCuentamatriz;
    }

    public void setEstadoCuentamatriz(String estadoCuentamatriz) {
        this.estadoCuentamatriz = estadoCuentamatriz;
    }

    public String getEstadoHhpp() {
        return estadoHhpp;
    }

    public void setEstadoHhpp(String estadoHhpp) {
        this.estadoHhpp = estadoHhpp;
    }

    public String getViable() {
        return viable;
    }

    public void setViable(String viable) {
        this.viable = viable;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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
