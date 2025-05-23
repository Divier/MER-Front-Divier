package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase TipoDireccion.
 * Implementa Serialización.
 *
 * @author 	Deiver.
 * @version	1.0
 */
public class TipoDireccion implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal tdiId;
    private String tdiValor;
    private String tdiUsuarioCreacion;
    private Date tdiFechaCreacion;
    private String tdiUsuarioModificacion;
    private Date tdiFechaModificacion;

    /**
     * Constructor.
     */
    public TipoDireccion() {
    }

    /**
     * Constructor con parámetros.
     * @param tdiId Entero con el Id del TipoDireccion.
     */
    public TipoDireccion(BigDecimal tdiId) {
        this.tdiId = tdiId;
    }

    /**
     * Obtiene el Id del TipoDireccion.
     * @return Entero con el Id del TipoDireccion.
     */
    public BigDecimal getTdiId() {
        return tdiId;
    }

    /**
     * Establece el Id del TipoDireccion.
     * @param tdiId Entero con el Id del TipoDireccion.
     */
    public void setTdiId(BigDecimal tdiId) {
        this.tdiId = tdiId;
    }

    /**
     * Obtiene el valor del TipoDireccion.
     * @return Cadena con el valor del TipoDireccion.
     */
    public String getTdiValor() {
        return tdiValor;
    }

    /**
     * Establece el valor del TipoDireccion.
     * @param tdiValor Cadena con el valor del TipoDireccion.
     */
    public void setTdiValor(String tdiValor) {
        this.tdiValor = tdiValor;
    }

    /**
     * Obtiene el usuario que crea el TipoDireccion.
     * @return Cadena con el usuario que crea el TipoDireccion.
     */
    public String getTdiUsuarioCreacion() {
        return tdiUsuarioCreacion;
    }

    /**
     * Establece el usuario que crea el TipoDireccion.
     * @param tdiUsuarioCreacion Cadena con el usuario que crea el TipoDireccion.
     */
    public void setTdiUsuarioCreacion(String tdiUsuarioCreacion) {
        this.tdiUsuarioCreacion = tdiUsuarioCreacion;
    }

    /**
     * Obtiene la fecha de creación del TipoDireccion.
     * @return Fecha de creación del TipoDireccion.
     */
    public Date getTdiFechaCreacion() {
        return tdiFechaCreacion;
    }

    /**
     * Establece la fecha de creación del TipoDireccion.
     * @param tdiFechaCreacion Fecha de creación del TipoDireccion.
     */
    public void setTdiFechaCreacion(Date tdiFechaCreacion) {
        this.tdiFechaCreacion = tdiFechaCreacion;
    }

    /**
     * Obtiene el usuario que modifica el TipoDireccion.
     * @return Cadena con el usuario que modifica el TipoDireccion.
     */
    public String getTdiUsuarioModificacion() {
        return tdiUsuarioModificacion;
    }

    /**
     * Establece el usuario que modifica el TipoDireccion.
     * @param tdiUsuarioModificacion Cadena con el usuario que modifica el TipoDireccion.
     */
    public void setTdiUsuarioModificacion(String tdiUsuarioModificacion) {
        this.tdiUsuarioModificacion = tdiUsuarioModificacion;
    }

    /**
     * Obtiene la fecha de modificación del TipoDireccion.
     * @return Fecha de modificación del TipoDireccion.
     */
    public Date getTdiFechaModificacion() {
        return tdiFechaModificacion;
    }

    /**
     * Establece la fecha de modificación del TipoDireccion.
     * @param tdiFechaModificacion Fecha de modificación del TipoDireccion.
     */
    public void setTdiFechaModificacion(Date tdiFechaModificacion) {
        this.tdiFechaModificacion = tdiFechaModificacion;
    }

    /**
     * Obtiene información para el control del TipoDireccion en uso.
     * @return Cadena con información del TipoDireccion. (Id, Valor)
     */
    public String auditoria() {
        return "TipoDireccion:" + "tdiId=" + tdiId
                + ", tdiValor=" + tdiValor + '.';
    }

    /**
     * Sobre Escritura del método toString().
     * @return Cadena con información del TipoDireccion. (Id, Valor)
     */
    @Override
    public String toString() {
        return "TipoDireccion:" + "tdiId=" + tdiId
                + ", tdiValor=" + tdiValor + '.';
    }
}
