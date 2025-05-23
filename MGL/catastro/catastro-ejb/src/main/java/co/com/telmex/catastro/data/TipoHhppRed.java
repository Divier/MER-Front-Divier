package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase TipoHhppRed
 * Implementa Serialización.
 *
 * @author 	Nataly Orozco Torres.
 * @version	1.0
 */
public class TipoHhppRed implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal thrId;
    private String thrCodigo;
    private String thrNombre;
    private Date thrFechaCreacion;
    private String thrUsuarioCreacion;
    private Date thrFechaModificacion;
    private String thrUsuarioModificacion;

    /**
     * Constructor
     */
    public TipoHhppRed() {
    }

    /**
     * Obtiene la fecha de creación del tipoHhppRed.
     * @return Fecha de creación del tipoHhppRed.
     */
    public Date getThrFechaCreacion() {
        return thrFechaCreacion;
    }

    /**
     * Establece la fecha de creación del tipoHhppRed.
     * @param thrFechaCreacion Fecha de creación del tipoHhppRed.
     */
    public void setThrFechaCreacion(Date thrFechaCreacion) {
        this.thrFechaCreacion = thrFechaCreacion;
    }

    /**
     * Obtiene la fecha de modificación del tipoHhppRed.
     * @return Fecha de modificación del tipoHhppRed.
     */
    public Date getThrFechaModificacion() {
        return thrFechaModificacion;
    }

    /**
     * Establece la fecha de modificación del tipoHhppRed.
     * @param thrFechaModificacion Fecha de modificación del tipoHhppRed.
     */
    public void setThrFechaModificacion(Date thrFechaModificacion) {
        this.thrFechaModificacion = thrFechaModificacion;
    }

    /**
     * Obtiene el Id de tipoHhppRed.
     * @return Entero con el valor del Id del tipoHhppRed.
     */
    public BigDecimal getThrId() {
        return thrId;
    }

    /**
     * Establece el valor del Id del tipoHhppRed.
     * @param thrId Entero con el valor del Id del tipoHhppRed.
     */
    public void setThrId(BigDecimal thrId) {
        this.thrId = thrId;
    }

    /**
     * Obtiene el código del tipoHhppRed.
     * @return Cadena con el código del tipoHhppRed.
     */
    public String getThrCodigo() {
        return thrCodigo;
    }

    /**
     * Establece el código del tipoHhppRed.
     * @param thrCodigo Cadena con el código del tipoHhppRed.
     */
    public void setThrCodigo(String thrCodigo) {
        this.thrCodigo = thrCodigo;
    }

    /**
     * Obtiene el nombre del tipoHhppRed.
     * @return Cadena con el nombre del tipoHhppRed.
     */
    public String getThrNombre() {
        return thrNombre;
    }

    /**
     * Establece el nombre del tipoHhppRed.
     * @param thrNombre Cadena con el nombre del tipoHhppRed.
     */
    public void setThrNombre(String thrNombre) {
        this.thrNombre = thrNombre;
    }

    /**
     * Retorna el nombre del usuario que crea el tipoHhppRed.
     * @return Cadena con el nombre del usuario que crea el tipoHhppRed.
     */
    public String getThrUsuarioCreacion() {
        return thrUsuarioCreacion;
    }

    /**
     * Establece el nombre del usuario que crea el tipoHhppRed.
     * @param thrUsuarioCreacion Cadena con el nombre del usuario que crea el tipoHhppRed.
     */
    public void setThrUsuarioCreacion(String thrUsuarioCreacion) {
        this.thrUsuarioCreacion = thrUsuarioCreacion;
    }

    /**
     * Obtiene el nombre del usuario que modifica el tipoHhppRed.
     * @return Cadena con el nombre del usuario que modifica el tipoHhppRed.
     */
    public String getThrUsuarioModificacion() {
        return thrUsuarioModificacion;
    }

    /**
     * Establece el nombre del usuario que modifica el tipoHhppRed.
     * @param thrUsuarioModificacion Cadena con el nombre del usuario que modifica el tipoHhppRed.
     */
    public void setThrUsuarioModificacion(String thrUsuarioModificacion) {
        this.thrUsuarioModificacion = thrUsuarioModificacion;
    }

    /**
     * Obtiene información para el control del tipoHhppRed en uso.
     * @return Cadena con información del tipoHhppRed. (Id, Código, Nombre)
     */
    public String auditoria() {
        return "TipoHhppRed:" + "thrId=" + thrId + ", thrCodigo=" + thrCodigo
                + ", thrNombre=" + thrNombre + '.';
    }

    /**
     * Sobre Escritura del método toString().
     * @return Cadena con información del tipoHhppRed. (Id, Código, Nombre)
     */
    @Override
    public String toString() {
        return "TipoHhppRed:" + "thrId=" + thrId + ", thrCodigo=" + thrCodigo
                + ", thrNombre=" + thrNombre + '.';
    }
}
