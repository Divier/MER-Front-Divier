package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.jpa.entities.cm.UsArea;
import co.com.claro.mgl.jpa.entities.cm.UsPerfil;
import co.com.claro.mgl.jpa.entities.cm.Usuarios;
import java.math.BigDecimal;
import java.math.BigInteger;




/**
 *
 * @author bocanegravm
 */
public class UsuariosServicesDTO {

    private String cedula;
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;
    private String usuario;
    private String codPerfil;
    private String descripcionPerfil;
    private BigInteger idArea;
    private String descripcionArea;
    private BigDecimal idPerfil;
    
    
    /**
     * Constructor.
     */
    public UsuariosServicesDTO(){
        
    }
    
    
    /**
     * Constructor empleado para la construcci&oacute;n de un objeto con los
     * datos de la consulta del servicio.
     *
     * @param cedula {@link String} identificacion del usuario.
     * @param nombre {@link String} nombre del usuario.
     * @param telefono {@link String} telefono del usuario.
     * @param email {@link String} correo del usuario.
     * @param direccion {@link String} direccion del usuario.
     * @param usuario {@link String} login del usuario.
     * @param idPerfil {@link Integer} Identificador del perfil del usuario.
     * @param codPerfil {@link String} codigo de perfil del usuario.
     * @param descripcionPerfil {@link String} descripcion del perfil del
     * usuario.
     * @param idArea {@link String} area a la que pertenece el usuario.
     * @param descripcionArea {@link String} descripcion del area a la que
     * pertenece el usuario.
     *
     */
    public UsuariosServicesDTO(String cedula, String nombre,
            String telefono, String email, String direccion, String usuario, BigDecimal idPerfil,
            String codPerfil, String descripcionPerfil, BigInteger idArea, String descripcionArea) {

        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.usuario = usuario;
        this.idPerfil = idPerfil;
        this.codPerfil = codPerfil;
        this.descripcionPerfil = descripcionPerfil;
        this.idArea = idArea;
        this.descripcionArea = descripcionArea;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCodPerfil() {
        return codPerfil;
    }

    public void setCodPerfil(String codPerfil) {
        this.codPerfil = codPerfil;
    }

    public String getDescripcionPerfil() {
        return descripcionPerfil;
    }

    public void setDescripcionPerfil(String descripcionPerfil) {
        this.descripcionPerfil = descripcionPerfil;
    }

    public BigInteger getIdArea() {
        return idArea;
    }

    public void setIdArea(BigInteger idArea) {
        this.idArea = idArea;
    }

    public String getDescripcionArea() {
        return descripcionArea;
    }

    public void setDescripcionArea(String descripcionArea) {
        this.descripcionArea = descripcionArea;
    }

    public BigDecimal getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(BigDecimal idPerfil) {
        this.idPerfil = idPerfil;
    }
    
    
    
    /**
     * Clona la respectiva entidad en una entidad <i>USUARIOS</i>.
     * 
     * @return Nueva instancia de Usuarios.
     */
    public Usuarios cloneToUsuarios() {
        Usuarios user = new Usuarios();
        if (this.cedula != null) {
            user.setIdUsuario(new BigDecimal(this.cedula));
        }
        user.setUsuario(this.usuario);
        user.setTelefono(this.telefono);
        user.setNombre(this.nombre);
        user.setEmail(this.email);
        user.setDireccion(this.direccion);
        
        UsArea area = new UsArea();
        if (this.idArea != null) {
            area.setIdArea(new BigDecimal(this.idArea));
        }
        area.setAreaNombre(this.descripcionArea);
        
        UsPerfil perfil = new UsPerfil();
        perfil.setIdPerfil(this.idPerfil);
        perfil.setCodPerfil(this.codPerfil);
        perfil.setDescripcion(this.descripcionPerfil);
        perfil.setArea(area);
        
        user.setPerfil(perfil);
        
        return (user);
    }
}
