package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase Distrito
 * Implementa Serialización.
 *
 * @author 	Deiver Rovira
 * @version	1.0
 */
public class Distrito implements Serializable {

    private static final long serialVersionUID = 1L;
    private String disId;
    private String disNombre;
    private String disUsuarioCreacion;
    private String disUsuarioModificacion;
    private Date disFechaModificacion;
    private Date disFechaCreacion;

    /**
     * Constructor.
     */
    public Distrito() {
    }

    /**
     * Constructor con parámetros.
     * @param disId
     */
    public Distrito(String disId) {
        this.disId = disId;
    }

    /**
     * Obtiene el Id.
     * @return Cadena con el Id.
     */
    public String getDisId() {
        return disId;
    }

    /**
     * Establece el Id.
     * @param disId Cadena con el Id.
     */
    public void setDisId(String disId) {
        this.disId = disId;
    }

    /**
     * Obtiene el nombre.
     * @return Cadena con el nombre.
     */
    public String getDisNombre() {
        return disNombre;
    }

    /**
     * Establece el nombre.
     * @param disNombre Cadena con el nombre.
     */
    public void setDisNombre(String disNombre) {
        this.disNombre = disNombre;
    }

    /**
     * Obtiene el usuario que realiza la creación.
     * @return Cadena con el usuario que realiza la creación.
     */
    public String getDisUsuarioCreacion() {
        return disUsuarioCreacion;
    }

    /**
     * Establece el usuario que realiza la creación.
     * @param disUsuarioCreacion Cadena con el usuario que realiza la creación.
     */
    public void setDisUsuarioCreacion(String disUsuarioCreacion) {
        this.disUsuarioCreacion = disUsuarioCreacion;
    }

    /**
     * Obtiene el usuario que realiza la modificación.
     * @return Cadena con el usuario que realiza la modificación.
     */
    public String getDisUsuarioModificacion() {
        return disUsuarioModificacion;
    }

    /**
     * Establece el usuario que realiza la modificación.
     * @param disUsuarioModificacion Cadena con el usuario que realiza la modificación.
     */
    public void setDisUsuarioModificacion(String disUsuarioModificacion) {
        this.disUsuarioModificacion = disUsuarioModificacion;
    }

    /**
     * Obtiene la fecha de modificación.
     * @return Fecha de modificación.
     */
    public Date getDisFechaModificacion() {
        return disFechaModificacion;
    }

    /**
     * Establece la fecha de modificación.
     * @param disFechaModificacion Fecha de modificación.
     */
    public void setDisFechaModificacion(Date disFechaModificacion) {
        this.disFechaModificacion = disFechaModificacion;
    }

    /**
     * Obtiene la fecha de creación.
     * @return Fecha de creación
     */
    public Date getDisFechaCreacion() {
        return disFechaCreacion;
    }

    /**
     * Establece la fecha de Creación.
     * @param disFechaCreacion Fecha de creación.
     */
    public void setDisFechaCreacion(Date disFechaCreacion) {
        this.disFechaCreacion = disFechaCreacion;
    }

    /**
     * Obtiene información para auditoría
     * @return Cadena con información para auditoría.
     */
    public String auditoria() {
        return "Distrito:" + "disId=" + disId + ", disNombre=" + disNombre + '.';
    }

    /**
     * Sobre Escritura del método toString()
     * @return Cadena con la información de la auditoría.
     */
    @Override
    public String toString() {
        return "Distrito:" + "disId=" + disId + ", disNombre=" + disNombre + '.';
    }
}
