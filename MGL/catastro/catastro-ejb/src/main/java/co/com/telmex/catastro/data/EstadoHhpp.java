package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase EstadoHhpp
 * Implementa Serialización.
 *
 * @author 	Nataly Orozco Torres
 * @version	1.0
 */
public class EstadoHhpp implements Serializable {

    private String ehhId;
    private String ehhNombre;
    private Date ehhFechaCreacion;
    private String ehhUsuarioCreacion;
    private Date ehhFechaModificacion;
    private String ehhUsuarioModificacion;

    /**
     * Constructor.
     */
    public EstadoHhpp() {
    }

    /**
     * Obtiene la fecha de creación
     * @return Fecha de creación.
     */
    public Date getEhhFechaCreacion() {
        return ehhFechaCreacion;
    }

    /**
     * Establece la fecha de creación
     * @param ehhFechaCreacion Fecha de creación.
     */
    public void setEhhFechaCreacion(Date ehhFechaCreacion) {
        this.ehhFechaCreacion = ehhFechaCreacion;
    }

    /**
     * Obtiene la fecha de modificación.
     * @return Fecha de modificación.
     */
    public Date getEhhFechaModificacion() {
        return ehhFechaModificacion;
    }

    /**
     * Establece la fecha de modificación
     * @param ehhFechaModificacion Fecha de modificación.
     */
    public void setEhhFechaModificacion(Date ehhFechaModificacion) {
        this.ehhFechaModificacion = ehhFechaModificacion;
    }

    /**
     * Obtiene el Id.
     * @return Cadena con el Id.
     */
    public String getEhhId() {
        return ehhId;
    }

    /**
     * Establece el Id.
     * @param ehhId Cadena con el Id.
     */
    public void setEhhId(String ehhId) {
        this.ehhId = ehhId;
    }

    /**
     * Obtiene el nombre.
     * @return Cadena con el nombre.
     */
    public String getEhhNombre() {
        return ehhNombre;
    }

    /**
     * Establece el nombre.
     * @param ehhNombre Cadena con el nombre.
     */
    public void setEhhNombre(String ehhNombre) {
        this.ehhNombre = ehhNombre;
    }

    /**
     * Obtiene el usuario que realiza la creación. 
     * @return Cadena con el usuario que realiza la creación.
     */
    public String getEhhUsuarioCreacion() {
        return ehhUsuarioCreacion;
    }

    /**
     * Establece el usuario que realiza la creación
     * @param ehhUsuarioCreacion Cadena con el usuario que realiza la creación.
     */
    public void setEhhUsuarioCreacion(String ehhUsuarioCreacion) {
        this.ehhUsuarioCreacion = ehhUsuarioCreacion;
    }

    /**
     * Obtiene el usuario que realiza la modificación.
     * @return Cadena con el usuario que realiza la modificación.
     */
    public String getEhhUsuarioModificacion() {
        return ehhUsuarioModificacion;
    }

    /**
     * Establece el usuario que realiza la modificación.
     * @param ehhUsuarioModificacion Usuario que realiza la modificación.
     */
    public void setEhhUsuarioModificacion(String ehhUsuarioModificacion) {
        this.ehhUsuarioModificacion = ehhUsuarioModificacion;
    }

    /**
     * Obtiene información para auditoría
     * @return Cadena con información para auditoría.
     */
    public String auditoria() {
        return "EstadoHhpp:" + "ehhId=" + ehhId
                + ", ehhNombre=" + ehhNombre + '.';
    }

    /**
     * Sobre Escritura del método toString()
     * @return Cadena con la información de la auditoría.
     */
    @Override
    public String toString() {
        return "EstadoHhpp:" + "ehhId=" + ehhId
                + ", ehhNombre=" + ehhNombre + '.';
    }
}
