package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase Zona
 *
 * @author 	Nombre del autor.
 * @version	1.0
 */
public class Zona implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal zonId;
    private String zonNombre;
    private String zonUsuarioCreacion;
    private String zonUsuarioModificacion;
    private Date zonFechaCreacion;
    private Date zonFechaModificacion;

    /**
     *  Constructor de la clase.
     */
    public Zona() {
    }

    /**
     * Constructor con parámetros
     * @param zonId Entero código de la zona.
     */
    public Zona(BigDecimal zonId) {
        this.zonId = zonId;
    }

    /**
     * Retorna el valor Id de la zona.
     * @return Entero con el código de la zona.
     */
    public BigDecimal getZonId() {
        return zonId;
    }

    /**
     *  Establece el código de la zona.
     * @param zonId Entero, Código de la zona.
     */
    public void setZonId(BigDecimal zonId) {
        this.zonId = zonId;
    }

    /**
     * Retorna el nombre de la zona.
     * @return Cadena con el nombre de la zona.
     */
    public String getZonNombre() {
        return zonNombre;
    }

    /**
     * Establece el nombre de la zona.
     * @param zonNombre Cadena Nombre de la zona.
     */
    public void setZonNombre(String zonNombre) {
        this.zonNombre = zonNombre;
    }

    /**
     * Retorna el nombre del usuario que creo la zona.
     * @return
     */
    public String getZonUsuarioCreacion() {
        return zonUsuarioCreacion;
    }

    /**
     * Establece el nombre del usuario que creo la zona.
     * @param zonUsuarioCreacion Cadena Nombre del usuario que creo la zona.
     */
    public void setZonUsuarioCreacion(String zonUsuarioCreacion) {
        this.zonUsuarioCreacion = zonUsuarioCreacion;
    }

    /**
     * Retorna el nombre del usuario que modificó la zona.
     * @return Cadena Nombre del usuario que modificó la zona.
     */
    public String getZonUsuarioModificacion() {
        return zonUsuarioModificacion;
    }

    /**
     * Establece el nombre del usuario que realiza la modificación de la zona.
     * @param zonUsuarioModificacion Cadena Nombre del usuario que modifica la zona.
     */
    public void setZonUsuarioModificacion(String zonUsuarioModificacion) {
        this.zonUsuarioModificacion = zonUsuarioModificacion;
    }

    /**
     * Fecha en la que se creo la zona.
     * @return Fecha en la que se creo la zona.
     */
    public Date getZonFechaCreacion() {
        return zonFechaCreacion;
    }

    /**
     * Establece la fecha en la que se creo la zona.
     * @param zonFechaCreacion Fecha en la que se creo la zona.
     */
    public void setZonFechaCreacion(Date zonFechaCreacion) {
        this.zonFechaCreacion = zonFechaCreacion;
    }

    /**
     * Fecha en la que se modifico la zona.
     * @return Fecha en la que se modifico la zona.
     */
    public Date getZonFechaModificacion() {
        return zonFechaModificacion;
    }

    /**
     * Establece la fecha en la que se modifico la zona.
     * @param zonFechaModificacion Fecha en la que se modifico la zona.
     */
    public void setZonFechaModificacion(Date zonFechaModificacion) {
        this.zonFechaModificacion = zonFechaModificacion;
    }

    /**
     * Cadena con la configuración zona, nombre.
     * @return Cadena con la información Zona, nombre.
     */
    public String auditoria() {
        return "Zona:" + "zonId=" + zonId + ", zonNombre=" + zonNombre + ".";
    }

    /**
     * Sobre Escritura del método toString()
     * @return Cadena con la información Zona, nombre.
     */
    @Override
    public String toString() {
        return "Zona:" + "zonId=" + zonId + ", zonNombre=" + zonNombre + ".";
    }
}
