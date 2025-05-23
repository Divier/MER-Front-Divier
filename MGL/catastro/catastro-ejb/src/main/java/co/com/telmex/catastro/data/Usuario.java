package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase Usuario
 * Implementa Serialización
 *
 * @author 	Jose Luis Caicedo G.
 * @version	1.0
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal usuId;
    private String usuLogin;
    private BigDecimal usuIdentifica;
    private String usuNombre;
    private String usuApellidos;
    private String usuEmail; // Este atributo no existe en la base de datos.
    private String usuEstado;
    private Date usuFechaCreacion;
    private String usuUsuarioCreacion;
    private Date usuFechaModificacion;
    private String usuUsuarioModificacion;
    private Rol rol;
    private String message = null; // Este atributo no existe en la base de datos.

    /**
     * Constructor 
     */
    public Usuario() {
    }

    /**
     *  Constructor con parámetros.
     * @param usuId Entero Código del usuario.
     */
    public Usuario(BigDecimal usuId) {
        this.usuId = usuId;
    }

    /**
     * Obtiene el código del usuario 
     * @return Entero Código del usuario.
     */
    public BigDecimal getUsuId() {
        return usuId;
    }

    /**
     * Establece el código del usuario
     * @param usuId Entero Código del usuario.
     */
    public void setUsuId(BigDecimal usuId) {
        this.usuId = usuId;
    }

    /**
     * Obtiene El usuario con el que se realiza el login.
     * @return Cadena con el nombre de usuario.
     */
    public String getUsuLogin() {
        return usuLogin;
    }

    /**
     * Establece el nombre de usuario con el que se realiza el login.
     * @param usuLogin Cadena nombre de usuario con el que se realiza el login.
     */
    public void setUsuLogin(String usuLogin) {
        this.usuLogin = usuLogin;
    }

    /**
     * Obtiene el numero de identificación del usuario.
     * @return Entero Numero de identificación del usuario.
     */
    public BigDecimal getUsuIdentifica() {
        return usuIdentifica;
    }

    /**
     * Establece el numero de identificación del usuario.
     * @param usuIdentifica Entero Numero de identificación del usuario.
     */
    public void setUsuIdentifica(BigDecimal usuIdentifica) {
        this.usuIdentifica = usuIdentifica;
    }

    /**
     * Obtiene el nombre del Usuario.
     * @return Cadena Nombre del usuario.
     */
    public String getUsuNombre() {
        return usuNombre;
    }

    /**
     * Establece el nombre del usuario.
     * @param usuNombre Cadena Nombre del usuario.
     */
    public void setUsuNombre(String usuNombre) {
        this.usuNombre = usuNombre;
    }

    /**
     * Establece los apellidos del usuario.
     * @return Cadena Apellidos del usuarios.
     */
    public String getUsuApellidos() {
        return usuApellidos;
    }

    /**
     * Retorna los apellidos del usuario.
     * @param usuApellidos Cadena Apellidos del usuario.
     */
    public void setUsuApellidos(String usuApellidos) {
        this.usuApellidos = usuApellidos;
    }

    /**
     * Retorna el E-Mail de usuario.
     * @return Cadena E-Mail del usuario.
     */
    public String getUsuEmail() {
        return usuEmail;
    }

    /**
     * Establece el E-Mail del usuario.
     * @param usuEmail Cadena E-Mail del usuario.
     */
    public void setUsuEmail(String usuEmail) {
        this.usuEmail = usuEmail;
    }

    /**
     * Retorna el estado del usuario.
     * @return Cadena Estado del usuario.
     */
    public String getUsuEstado() {
        return usuEstado;
    }

    /**
     * Establece el estado del usuario.
     * @param usuEstado Cadena Estado del usuario.
     */
    public void setUsuEstado(String usuEstado) {
        this.usuEstado = usuEstado;
    }

    /**
     * Retorna la fecha en la que fue creado el usuario.
     * @return Fecha de creación del usuario.
     */
    public Date getUsuFechaCreacion() {
        return usuFechaCreacion;
    }

    /**
     * Establece la fecha de creación del usuario.
     * @param usuFechaCreacion Fecha de creación del usuario.
     */
    public void setUsuFechaCreacion(Date usuFechaCreacion) {
        this.usuFechaCreacion = usuFechaCreacion;
    }

    /**
     * Retorna el usuario empleado para la creación del usuario consultado.
     * @return Cadena con el nombre de usuario creador.
     */
    public String getUsuUsuarioCreacion() {
        return usuUsuarioCreacion;
    }

    /**
     * Establece el nombre del usuario que realiza la creación del usuario consultado.
     * @param usuUsuarioCreacion Cadena con el nombre del usuario que realiza la creación.
     */
    public void setUsuUsuarioCreacion(String usuUsuarioCreacion) {
        this.usuUsuarioCreacion = usuUsuarioCreacion;
    }

    /**
     * Obtiene la fecha en la que se realizo la modificación del usuario.
     * @return Fecha en la que se realiza la modificación del usuario.
     */
    public Date getUsuFechaModificacion() {
        return usuFechaModificacion;
    }

    /**
     * Establece la fecha en la que se realiza la modificación del usuario.
     * @param usuFechaModificacion Fecha en la que se realiza la modificación del usuario.
     */
    public void setUsuFechaModificacion(Date usuFechaModificacion) {
        this.usuFechaModificacion = usuFechaModificacion;
    }

    /**
     * Obtiene el nombre del usuario que realiza la modificación.
     * @return Cadena nombre del usuario que realiza la modificación.
     */
    public String getUsuUsuarioModificacion() {
        return usuUsuarioModificacion;
    }

    /**
     * Establece el nombre del usuario que realiza la modificación.
     * @param usuUsuarioModificacion Cadena Nombre del usuario que realiza la modificación.
     */
    public void setUsuUsuarioModificacion(String usuUsuarioModificacion) {
        this.usuUsuarioModificacion = usuUsuarioModificacion;
    }

    /**
     * Obtiene el mensaje del proceso de consulta.
     * @return Cadena con el mensaje del proceso de consulta.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Establece el mensaje resultado del proceso de consulta.
     * @param message Cadena con el mensaje del resultado del proceso.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Objeto Rol con la información del rol del usuario.
     * @return Objeto Rol con la información del rol del usuario.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario consultado.
     * @param rol Objeto Rol con la información del rol del usuario consultado.
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Retorna información para consulta rápida del usuario en procesos de auditoría.
     * @return Cadena con la información del usuario (Usuario, Login, Identificación, Nombre, Apellido, Email, Estado y mensaje.)
     */
    public String auditoria() {
        return "Usuario:" + "usuId=" + usuId + ", usuLogin=" + usuLogin
                + ", usuIdentifica=" + usuIdentifica
                + ", usuNombre=" + usuNombre
                + ", usuApellidos=" + usuApellidos
                + ", usuEmail=" + usuEmail
                + ", usuEstado=" + usuEstado + ", rol=" + rol
                + ", message=" + message + '.';
    }

    /**
     * Sobre Escritura del método toString()
     * @return Cadena con la información del usuario (Usuario, Login, Identificación, Nombre, Apellido, Email, Estado y mensaje.)
     */
    @Override
    public String toString() {
        return "Usuario:" + "usuId=" + usuId + ", usuLogin=" + usuLogin
                + ", usuIdentifica=" + usuIdentifica
                + ", usuNombre=" + usuNombre
                + ", usuApellidos=" + usuApellidos
                + ", usuEmail=" + usuEmail
                + ", usuEstado=" + usuEstado + ", rol=" + rol
                + ", message=" + message + '.';
    }
}
