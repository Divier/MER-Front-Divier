package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase TipoMarcas
 * Implementa Serialización
 *
 * @author 	Nataly Orozco Torres.
 * @version	1.0
 */
public class TipoMarcas implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal tmaId;
    private String tmaCodigo;
    private String tmaNombre;
    private String tmaUsuarioCreacion;
    private Date tmaFechaCreacion;
    private String tmaUsuarioModificacion;
    private Date tmaFechaModificacion;

    /**
     * Constructor.
     */
    public TipoMarcas() {
    }

    /**
     * Obtiene la fecha de creación del tipo marca.
     * @return Fecha de creación del tipo marca.
     */
    public Date getTmaFechaCreacion() {
        return tmaFechaCreacion;
    }

    /**
     * Establece la fecha de creación del tipo marca.
     * @param tmaFechaCreacion Fecha de creación del tipo marca.
     */
    public void setTmaFechaCreacion(Date tmaFechaCreacion) {
        this.tmaFechaCreacion = tmaFechaCreacion;
    }

    /**
     * Obtiene la fecha de modificación del tipo marca.
     * @return Fecha de modificación del tipo marca.
     */
    public Date getTmaFechaModificacion() {
        return tmaFechaModificacion;
    }

    /**
     * Establece la fecha de modificación del tipo marca.
     * @param tmaFechaModificacion Fecha de modificación del tipo marca.
     */
    public void setTmaFechaModificacion(Date tmaFechaModificacion) {
        this.tmaFechaModificacion = tmaFechaModificacion;
    }

    /**
     * Obtiene el Id del tipo marca.
     * @return Entero con el valor del Id del tipo marca.
     */
    public BigDecimal getTmaId() {
        return tmaId;
    }

    /**
     * Establece el Id del tipo marca.
     * @param tmaId Entero con el valor del Id tipo marca.
     */
    public void setTmaId(BigDecimal tmaId) {
        this.tmaId = tmaId;
    }

    /**
     * Obtiene el código del tipo marca.
     * @return Cadena con el código del tipo marca.
     */
    public String getTmaCodigo() {
        return tmaCodigo;
    }

    /**
     *  Establece el valor de código tipo marca.
     * @param tmaCodigo Cadena con el código tipo marca.
     */
    public void setTmaCodigo(String tmaCodigo) {
        this.tmaCodigo = tmaCodigo;
    }

    /**
     * Obtiene el nombre del tipo Marca.
     * @return Cadena con el nombre del tipo marca.
     */
    public String getTmaNombre() {
        return tmaNombre;
    }

    /**
     * Establece el nombre del tipo marca.
     * @param tmaNombre Cadena con el nombre del tipo marca.
     */
    public void setTmaNombre(String tmaNombre) {
        this.tmaNombre = tmaNombre;
    }

    /**
     * Obtiene el nombre del usuario que crea el tipo marca.
     * @return Cadena con el nombre del usuario que crea el tipo marca.
     */
    public String getTmaUsuarioCreacion() {
        return tmaUsuarioCreacion;
    }

    /**
     * Establece el nombre del usuario que crea el tipo marca.
     * @param tmaUsuarioCreacion Cadena con el nombre del usuario que crea el tipo marca.
     */
    public void setTmaUsuarioCreacion(String tmaUsuarioCreacion) {
        this.tmaUsuarioCreacion = tmaUsuarioCreacion;
    }

    /**
     * Obtiene el nombre del usuario que modifica el tipo marca.
     * @return Cadena con el nombre de usuario que modifica el tipo marca.
     */
    public String getTmaUsuarioModificacion() {
        return tmaUsuarioModificacion;
    }

    /**
     * Establece el nombre del usuario que modifica el tipo marca.
     * @param tmaUsuarioModificacion Cadena con el usuario que modifica el tipo marca.
     */
    public void setTmaUsuarioModificacion(String tmaUsuarioModificacion) {
        this.tmaUsuarioModificacion = tmaUsuarioModificacion;
    }

    /**
     * Obtiene información de control para el seguimiento del tipo marca.
     * @return Cadena con información del tipo marca empleado. (Id, código, nombre)
     */
    public String auditoria() {
        return "TipoMarcas:" + "tmaId=" + tmaId + ", tmaCodigo=" + tmaCodigo
                + ", tmaNombre=" + tmaNombre + '.';
    }

    /**
     * Sobre Escritura del método toString().
     * @return Cadena con información del tipo marca empleado. (Id, código, nombre)
     */
    @Override
    public String toString() {
        return "TipoMarcas:" + "tmaId=" + tmaId + ", tmaCodigo=" + tmaCodigo
                + ", tmaNombre=" + tmaNombre + '.';
    }
}
