package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase TipoUbicacion
 * Implementa Serialización
 *
 * @author 	Deiver
 * @version	1.0
 */
public class TipoUbicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal tubId;
    private String tubNombre;
    private String tubUsuarioCreacion;
    private Date tubFechaCreacion;
    private String tubUsuarioModificacion;
    private Date tubFechaModificacion;

    /**
     * Constructor.
     */
    public TipoUbicacion() {
    }

    /**
     * Constructor con parámetros.
     * @param tubId Entero código tipo ubicación.
     */
    public TipoUbicacion(BigDecimal tubId) {
        this.tubId = tubId;
    }

    /**
     * Obtiene el código del tipo ubicación.
     * @return Entero con el código del tipo de ubicación.
     */
    public BigDecimal getTubId() {
        return tubId;
    }

    /**
     * Establece el código del tipo de ubicación.
     * @param tubId Entero con el código del tipo de ubicación.
     */
    public void setTubId(BigDecimal tubId) {
        this.tubId = tubId;
    }

    /**
     * Obtiene el nombre del tipo de ubicación.
     * @return Cadena con el nombre del tipo de ubicación.
     */
    public String getTubNombre() {
        return tubNombre;
    }

    /**
     * Establece el nombre del tipo de ubicación.
     * @param tubNombre Cadena con el nombre del tipo de ubicación.
     */
    public void setTubNombre(String tubNombre) {
        this.tubNombre = tubNombre;
    }

    /**
     * Obtiene la fecha de creación del tipo de ubicación.
     * @return Fecha de creación del tipo de ubicación.
     */
    public Date getTubFechaCreacion() {
        return tubFechaCreacion;
    }

    /**
     * Establece la fecha de creación del tipo de ubicación.
     * @param tubFechaCreacion Fecha de creación del tipo de ubicación.
     */
    public void setTubFechaCreacion(Date tubFechaCreacion) {
        this.tubFechaCreacion = tubFechaCreacion;
    }

    /**
     * Obtiene la fecha de modificación del tipo de ubicación.
     * @return Fecha de modificación del tipo de ubicación.
     */
    public Date getTubFechaModificacion() {
        return tubFechaModificacion;
    }

    /**
     * Establece la fecha de modificación del tipo de ubicación.
     * @param tubFechaModificacion Fecha de modificación del tipo de ubicación.
     */
    public void setTubFechaModificacion(Date tubFechaModificacion) {
        this.tubFechaModificacion = tubFechaModificacion;
    }

    /**
     * Obtiene el nombre del usuario que crea el tipo de ubicación.
     * @return Cadena con el nombre de usuario que crea el tipo de ubicación.
     */
    public String getTubUsuarioCreacion() {
        return tubUsuarioCreacion;
    }

    /**
     * Establece el nombre del usuario que crea el tipo de ubicación.
     * @param tubUsuarioCreacion Cadena con el nombre de usuario que crea el tipo de ubicación.
     */
    public void setTubUsuarioCreacion(String tubUsuarioCreacion) {
        this.tubUsuarioCreacion = tubUsuarioCreacion;
    }

    /**
     * Obtiene el nombre de usuario que modifica el tipo de ubicación.
     * @return Cadena con el nombre de usuario que modifica el tipo de ubicación.
     */
    public String getTubUsuarioModificacion() {
        return tubUsuarioModificacion;
    }

    /**
     * Establece el nombre de usuario que modifica el tipo de ubicación.
     * @param tubUsuarioModificacion Cadena con el nombre de usuario que modifica el tipo de ubicación.
     */
    public void setTubUsuarioModificacion(String tubUsuarioModificacion) {
        this.tubUsuarioModificacion = tubUsuarioModificacion;
    }

    /**
     * Obtiene información de control correspondiente al tipo de ubicación.
     * @return Cadena con información del tipo de ubicación (Id, Nombre).
     */
    public String auditoria() {
        return "TipoUbicacion:" + "tubId=" + tubId
                + ", tubNombre=" + tubNombre + '.';
    }

    /**
     * Sobre Escritura del método toString().
     * @return Cadena con información del tipo de ubicación (Id, Nombre).
     */
    @Override
    public String toString() {
        return "TipoUbicacion:" + "tubId=" + tubId
                + ", tubNombre=" + tubNombre + '.';
    }
}
