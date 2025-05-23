/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rodriguezluim
 */
@Entity
@Table(name = "CMT_CONTACTOS", schema = "MGL_SCHEME")
@XmlRootElement
public class CmtContactosMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(
            name = "CmtContactosMgl.CmtContactosMglSq",
            sequenceName = "MGL_SCHEME.CMT_CONTACTOS_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtContactosMgl.CmtContactosMglSq")
    @Column(name = "CONTACTOS_ID", nullable = false)
    private BigDecimal contactosId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CCMM_ID")
    private CmtCuentaMatrizMgl ccmmId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OT_ID")
    private CmtOrdenTrabajoMgl otId;
    
    @Column(name = "NOMBRE_APELLIDO", nullable = false, length = 20)
    private String nombreApellido;
    
    @Column(name = "CORREO_ELECTRONICO", nullable = false, length = 20)
    private String correoElectronico;
    
    @Column(name = "TELEFONO_PRINCIPAL", nullable = false)
    private BigDecimal telefonoPrincipal;
    
    @Column(name = "TELEFONO_SECUNDARIO", nullable = false)
    private BigDecimal telefonoSecundario;
    
    @Column(name = "COMENTARIO", nullable = false, length = 20)
    private String comentario;
    
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @NotNull
    @Column(name = "USUARIO_CREACION", nullable = false, length = 20)
    private String usuarioCreacion;
    
    @NotNull
    @Column(name = "PERFIL_CREACION", nullable = false)
    private int perfilCreacion;
    
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    
    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioEdicion;
    
    @Column(name = "PERFIL_MODIFICACION")
    private int perfilEdicion;
    
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private int estadoRegistro;

    public BigDecimal getContactosId() {
        return contactosId;
    }

    public void setContactosId(BigDecimal contactosId) {
        this.contactosId = contactosId;
    }

    public CmtCuentaMatrizMgl getCcmmId() {
        return ccmmId;
    }

    public void setCcmmId(CmtCuentaMatrizMgl ccmmId) {
        this.ccmmId = ccmmId;
    }

    public CmtOrdenTrabajoMgl getOtId() {
        return otId;
    }

    public void setOtId(CmtOrdenTrabajoMgl otId) {
        this.otId = otId;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public BigDecimal getTelefonoPrincipal() {
        return telefonoPrincipal;
    }

    public void setTelefonoPrincipal(BigDecimal telefonoPrincipal) {
        this.telefonoPrincipal = telefonoPrincipal;
    }

    public BigDecimal getTelefonoSecundario() {
        return telefonoSecundario;
    }

    public void setTelefonoSecundario(BigDecimal telefonoSecundario) {
        this.telefonoSecundario = telefonoSecundario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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
