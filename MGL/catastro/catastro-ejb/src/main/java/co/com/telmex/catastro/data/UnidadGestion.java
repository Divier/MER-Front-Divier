package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase UnidadGestion
 * Implementa Serialización.
 *
 * @author 	Nombre del autor.
 * @version	1.0
 */
public class UnidadGestion implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal ugeId;
    private String ugeNombre;
    private Date ugeFechaModificacion;
    private Date ugeFechaCreacion;
    private String ugeUsuarioModificacion;
    private String ugeUsuarioCreacion;

    /**
     * Constructor.
     */
    public UnidadGestion() {
    }

    /**
     * Constructor con parámetros.
     * @param ugeId Entero Código de la unidad de gestión.
     */
    public UnidadGestion(BigDecimal ugeId) {
        this.ugeId = ugeId;
    }

    /**
     * Obtiene el código de la unidad de gestión.
     * @return Entero Código de la unidad de gestión.
     */
    public BigDecimal getUgeId() {
        return ugeId;
    }

    /**
     * Establece el código de la unidad de gestión.
     * @param ugeId Entero Código de la unidad de gestión.
     */
    public void setUgeId(BigDecimal ugeId) {
        this.ugeId = ugeId;
    }

    /**
     * Obtiene el nombre de la unidad de gestión.
     * @return Cadena con el nombre de la unidad de gestión.
     */
    public String getUgeNombre() {
        return ugeNombre;
    }

    /**
     * Establece el nombre de la unidad de gestión.
     * @param ugeNombre Cadena Nombre de la unidad de gestión.
     */
    public void setUgeNombre(String ugeNombre) {
        this.ugeNombre = ugeNombre;
    }

    /**
     * Obtiene la fecha en la que se modifica la unidad de gestión.
     * @return Fecha en la que se modifica la unidad de gestión.
     */
    public Date getUgeFechaModificacion() {
        return ugeFechaModificacion;
    }

    /**
     * Establece la fecha en la que se modifica la unidad de gestión.
     * @param ugeFechaModificacion Fecha en la que se modifica la unidad de gestión.
     */
    public void setUgeFechaModificacion(Date ugeFechaModificacion) {
        this.ugeFechaModificacion = ugeFechaModificacion;
    }

    /**
     * Obtiene la fecha en la que se crea la unidad de gestión.
     * @return Fecha en la que se crea la unidad de gestión.
     */
    public Date getUgeFechaCreacion() {
        return ugeFechaCreacion;
    }

    /**
     * Establece la fecha en la que se crea la unidad de gestión.
     * @param ugeFechaCreacion Fecha en la que se crea la unidad de gestión.
     */
    public void setUgeFechaCreacion(Date ugeFechaCreacion) {
        this.ugeFechaCreacion = ugeFechaCreacion;
    }

    /**
     * Obtiene el usuario que realiza la modificación de la unidad de gestión.
     * @return Cadena con el nombre de usuario que modifica la unidad de gestión.
     */
    public String getUgeUsuarioModificacion() {
        return ugeUsuarioModificacion;
    }

    /**
     * Establece el nombre de usuario que modifica la unidad de gestión.
     * @param ugeUsuarioModificacion Cadena con el nombre de usuario que modifica la unidad de gestión.
     */
    public void setUgeUsuarioModificacion(String ugeUsuarioModificacion) {
        this.ugeUsuarioModificacion = ugeUsuarioModificacion;
    }

    /**
     * Obtiene el nombre de usuario que crea la unidad de gestión.
     * @return Cadena con el nombre de usuario que crea la unidad de gestión.
     */
    public String getUgeUsuarioCreacion() {
        return ugeUsuarioCreacion;
    }

    /**
     * Establece el nombre de usuario que crea la unidad de gestión.
     * @param ugeUsuarioCreacion Cadena con el nombre de usuario que crea la unidad de gestión.
     */
    public void setUgeUsuarioCreacion(String ugeUsuarioCreacion) {
        this.ugeUsuarioCreacion = ugeUsuarioCreacion;
    }

    /**
     * Cadena de control con la información de la unidad de gestión.
     * @return Cadena con la información de la unidad de gestión. (Código, Nombre).
     */
    public String auditoria() {
        return "UnidadGestion:" + "ugeId=" + ugeId
                + ", ugeNombre=" + ugeNombre + '.';
    }

    /**
     * Sobre Escritura del método toString().
     * @return Cadena con la información de la unidad de gestión. (Código, Nombre).
     */
    @Override
    public String toString() {
        return "UnidadGestion:" + "ugeId=" + ugeId
                + ", ugeNombre=" + ugeNombre + '.';
    }
}
