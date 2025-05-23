package co.com.claro.mgl.jpa.entities.ptlus;

import co.com.claro.mgl.jpa.entities.cm.UsPerfil;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Entidad asociada a la tabla <i>GESTIONNEW</i>.<i>PTLUS_USUARIOS_PORTAL</i>.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
@Entity
@Table(name = "PTLUS_USUARIOS_PORTAL", schema = "GESTIONNEW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuariosPortal.findAll", query = "SELECT u FROM UsuariosPortal u "),
    @NamedQuery(name = "UsuariosPortal.findUsuarioPortalByCedula", query = "SELECT u FROM UsuariosPortal u  WHERE u.cedula = :cedula"),
    @NamedQuery(name = "UsuariosPortal.findUsuarioPortalByUsuario", query = "SELECT u FROM UsuariosPortal u  WHERE LOWER(u.usuario) = LOWER(:usuario)")
})
public class UsuariosPortal implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "UsuariosPortal.usuariosPortalSeq",
            sequenceName = "GESTIONNEW.PTLUS_USUARIOS_PORTAL_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "UsuariosPortal.usuariosPortalSeq")
    @Column(name = "ID_PORTAL", nullable = false)
    protected BigDecimal idPortal;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PERFIL", nullable = false)
    protected UsPerfil perfil;
    
    @Column(name = "IDALIADO")
    protected BigDecimal idAliado;
    
    @Column(name = "CEDULA")
    protected String cedula;
    
    @Column(name = "NOMBRE")
    protected String nombre;
    
    @Column(name = "TELEFONO")
    protected String telefono;
    
    @Column(name = "DIRECCION")
    protected String direccion;
    
    @Column(name = "EMAIL")
    protected String email;
    
    @Column(name = "RUTA_WSDL")
    protected String rutaWSDL;
    
    @Column(name = "APP")
    protected String app;
    
    @Column(name = "DESCRIPCION_WSDL")
    protected String descripcionWSDL;
    
    @Column(name = "ESTADO")
    protected String estado;
    
    @Column(name = "USUARIO")
    protected String usuario;
    
    @Column(name = "USUARIO_CREO")
    protected String usuarioCreo;
    
    @Column(name = "USUARIO_MODIFICO")
    protected String usuarioModifico;
    
    @Column(name = "FECHA_REGISTRO")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date fechaRegistro;
    
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date fechaModificacion;
    
    
    @Override
    public String toString(){
        return "["+this.usuario+"]"+this.nombre;
    }
    
    
    public BigDecimal getIdPortal() {
        return idPortal;
    }

    public void setIdPortal(BigDecimal idPortal) {
        this.idPortal = idPortal;
    }
    
    public UsPerfil getPerfil() {
        return perfil;
    }

    public void setPerfil(UsPerfil perfil) {
        this.perfil = perfil;
    }

    public BigDecimal getIdAliado() {
        return idAliado;
    }

    public void setIdAliado(BigDecimal idAliado) {
        this.idAliado = idAliado;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRutaWSDL() {
        return rutaWSDL;
    }

    public void setRutaWSDL(String rutaWSDL) {
        this.rutaWSDL = rutaWSDL;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getDescripcionWSDL() {
        return descripcionWSDL;
    }

    public void setDescripcionWSDL(String descripcionWSDL) {
        this.descripcionWSDL = descripcionWSDL;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuarioCreo() {
        return usuarioCreo;
    }

    public void setUsuarioCreo(String usuarioCreo) {
        this.usuarioCreo = usuarioCreo;
    }

    public String getUsuarioModifico() {
        return usuarioModifico;
    }

    public void setUsuarioModifico(String usuarioModifico) {
        this.usuarioModifico = usuarioModifico;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    
    /**
     * Obtiene el ID del Perfil.
     * @return perfil.idPerfil
     */
    public BigDecimal getIdPerfil() {
        return perfil != null ? perfil.getIdPerfil() : null;
    }
    
    
    /**
     * Obtiene la c&eacute;dula del Usuario.
     * @return cedula
     */
    public String getIdUsuario() {
        return ( this.cedula );
    }
    
    
    /**
     * Obtiene la descripci&oacute;n del &aacute;rea.
     * 
     * @return perfil.area.areaNombre
     */
    @Transient
    public String getDescripcionArea() {
        String descArea = null;
        if (perfil != null && perfil.getArea() != null) {
            descArea = perfil.getArea().getAreaNombre();
        }
        return (descArea);
    }
}
