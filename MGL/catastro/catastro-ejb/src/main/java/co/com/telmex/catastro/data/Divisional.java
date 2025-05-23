package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase Divisional
 * Implementa Serialización.
 *
 * @author 	Deiver Rovira
 * @version	1.0
 */
public class Divisional implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal divId;
    private String divNombre;
    private String divUsuarioCreacion;
    private String divUsuarioModificacion;
    private Date divFechaCreacion;
    private Date divFechaModificacion;

    /**
     * Constructor
     */
    public Divisional() {
    }

    /**
     * Constructor con parámetros.
     * @param divId
     */
    public Divisional(BigDecimal divId) {
        this.divId = divId;
    }

    /**
     * Obtiene el Id.
     * @return Entero con el Id.
     */
    public BigDecimal getDivId() {
        return divId;
    }

    /**
     * Establece el Id. 
     * @param divId Entero con el Id.
     */
    public void setDivId(BigDecimal divId) {
        this.divId = divId;
    }

    /**
     * Obtiene el nombre.
     * @return Cadena con el nombre.
     */
    public String getDivNombre() {
        return divNombre;
    }

    /**
     * Establece el nombre.
     * @param divNombre Cadena con el nombre.
     */
    public void setDivNombre(String divNombre) {
        this.divNombre = divNombre;
    }

    /**
     * Obtiene el usuario que realiza la creación.
     * @return Cadena con el usuario que realiza la creación.
     */
    public String getDivUsuarioCreacion() {
        return divUsuarioCreacion;
    }

    /**
     * Establece el usuario que realiza la creación.
     * @param divUsuarioCreacion Cadena con el usuario que realiza la creación.
     */
    public void setDivUsuarioCreacion(String divUsuarioCreacion) {
        this.divUsuarioCreacion = divUsuarioCreacion;
    }

    /**
     * Obtiene al usuario que realiza la modificación.
     * @return Cadena con el usuario que realiza la modificación.
     */
    public String getDivUsuarioModificacion() {
        return divUsuarioModificacion;
    }

    /**
     * Establece el usuario que realiza la modificación.
     * @param divUsuarioModificacion Cadena con el usuario que realiza la modificación.
     */
    public void setDivUsuarioModificacion(String divUsuarioModificacion) {
        this.divUsuarioModificacion = divUsuarioModificacion;
    }

    /**
     * Obtiene la fecha de creación.
     * @return Fecha de creación.
     */
    public Date getDivFechaCreacion() {
        return divFechaCreacion;
    }

    /**
     * Establece la fecha de creación.
     * @param divFechaCreacion Fecha de creación
     */
    public void setDivFechaCreacion(Date divFechaCreacion) {
        this.divFechaCreacion = divFechaCreacion;
    }

    /**
     * Obtiene la fecha de modificación.
     * @return Fecha de modificación.
     */
    public Date getDivFechaModificacion() {
        return divFechaModificacion;
    }

    /**
     * Establece la fecha de modificación.
     * @param divFechaModificacion Fecha de modificación.
     */
    public void setDivFechaModificacion(Date divFechaModificacion) {
        this.divFechaModificacion = divFechaModificacion;
    }

    /**
     * Obtiene información para auditoría
     * @return Cadena con información para auditoría.
     */
    public String auditoria() {
        return "Divisional:" + "divId=" + divId + ", divNombre=" + divNombre + '.';
    }

    /**
     * Sobre Escritura del método toString()
     * @return Cadena con la información de la auditoría.
     */
    @Override
    public String toString() {
        return "Divisional:" + "divId=" + divId + ", divNombre=" + divNombre + '.';
    }
}
