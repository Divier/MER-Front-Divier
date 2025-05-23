package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase Rol
 * Implementa Serialización.
 *
 * @author 	
 * @version	1.0
 */
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal rolId;
    private String rolNombre;
    private String rolLdap;
    private Date rolFechaCreacion;
    private String rolUsuarioCreacion;
    private Date rolFechaModificacion;
    private String rolUsuarioModificacion;

    /**
     * Constructor
     */
    public Rol() {
    }

    /**
     * Constructor con parámetros.
     * @param rolId Entero Id del rol.
     */
    public Rol(BigDecimal rolId) {
        this.rolId = rolId;
    }

    /**
     * Obtiene el Identificador del rol.
     * @return Entero Identificador del rol.
     */
    public BigDecimal getRolId() {
        return rolId;
    }

    /**
     * Establece el identificador del rol.
     * @param rolId Entero Identificador del rol.
     */
    public void setRolId(BigDecimal rolId) {
        this.rolId = rolId;
    }

    /**
     * Obtiene el nombre del rol.
     * @return Cadena con el nombre del rol.
     */
    public String getRolNombre() {
        return rolNombre;
    }

    /**
     * Establece el nombre del rol.
     * @param rolNombre Cadena con el nombre del rol.
     */
    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    /**
     * Obtiene el rol LDAP
     * @return Cadena con el rol LDAP
     */
    public String getRolLdap() {
        return rolLdap;
    }

    /**
     * Establece el ron LDAP
     * @param rolLdap Cadena con el rol LDAP.
     */
    public void setRolLdap(String rolLdap) {
        this.rolLdap = rolLdap;
    }

    /**
     * Obtiene la fecha de creación.
     * @return Fecha de creación del rol.
     */
    public Date getRolFechaCreacion() {
        return rolFechaCreacion;
    }

    /**
     * Establece la fecha de creación.
     * @param rolFechaCreacion Fecha de creación del rol.
     */
    public void setRolFechaCreacion(Date rolFechaCreacion) {
        this.rolFechaCreacion = rolFechaCreacion;
    }

    /**
     * Obtiene el usuario que crea el rol.
     * @return Cadena con el usuario que crea el rol.
     */
    public String getRolUsuarioCreacion() {
        return rolUsuarioCreacion;
    }

    /**
     * Establece el rol del usuario que crea.
     * @param rolUsuarioCreacion Cadena con el rol de usuario de creación.
     */
    public void setRolUsuarioCreacion(String rolUsuarioCreacion) {
        this.rolUsuarioCreacion = rolUsuarioCreacion;
    }

    /**
     * Obtiene la fecha de modificación.
     * @return Fecha de modificación.
     */
    public Date getRolFechaModificacion() {
        return rolFechaModificacion;
    }

    /**
     * Establece la fecha de modificación.
     * @param rolFechaModificacion Fecha de modificación.
     */
    public void setRolFechaModificacion(Date rolFechaModificacion) {
        this.rolFechaModificacion = rolFechaModificacion;
    }

    /**
     * Obtiene el rol del usuario que realiza la modificación.
     * @return Cadena con el usuario que realiza la modificación.
     */
    public String getRolUsuarioModificacion() {
        return rolUsuarioModificacion;
    }

    /**
     * Establece el rol de usuario que modifica.
     * @param rolUsuarioModificacion Cadena con el rol del usuario que modifica.
     */
    public void setRolUsuarioModificacion(String rolUsuarioModificacion) {
        this.rolUsuarioModificacion = rolUsuarioModificacion;
    }

    /**
     * Cadena con la configuración para auditoría de carga.
     * @return Cadena con los campos que contiene la clase.
     */
    public String auditoria() {
        return "Rol:" + "rolId=" + rolId + ", rolNombre=" + rolNombre
                + ", rolLdap=" + rolLdap + '.';
    }

    /**
     * Sobre escritura del método toString().
     * @return Cadena con información de auditoría.
     */
    @Override
    public String toString() {
        return "Rol:" + "rolId=" + rolId + ", rolNombre=" + rolNombre
                + ", rolLdap=" + rolLdap + '.';
    }
}
