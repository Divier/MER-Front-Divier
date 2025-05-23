package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase TipoGeografico.
 * Implementa Serialización.
 *
 * @author 	Nombre del autor.
 * @version	1.0
 */
public class TipoGeografico implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal tgeId;
    private String tgeValor;
    private Date tgeFechaCreacion;
    private String tgeUsuarioCreacion;
    private Date tgeFechaModificacion;
    private String tgeUsuarioModificacion;

    /**
     * Constructor.
     */
    public TipoGeografico() {
    }

    /**
     * Constructor con parámetros.
     * @param tgeId Entero con el Id del TipoGeografico.
     */
    public TipoGeografico(BigDecimal tgeId) {
        this.tgeId = tgeId;
    }

    /**
     * Obtiene el Id del TipoGeografico.
     * @return Entero con el Id del TipoGeografico.
     */
    public BigDecimal getTgeId() {
        return tgeId;
    }

    /**
     * Establece el Id del TipoGeografico.
     * @param tgeId Entero con el Id del TipoGeografico.
     */
    public void setTgeId(BigDecimal tgeId) {
        this.tgeId = tgeId;
    }

    /**
     * Obtiene el valor del TipoGeografico.
     * @return Cadena con el valor del TipoGeografico.
     */
    public String getTgeValor() {
        return tgeValor;
    }

    /**
     * Establece el valor del TipoGeografico.
     * @param tgeValor Cadena con el valor del TipoGeografico.
     */
    public void setTgeValor(String tgeValor) {
        this.tgeValor = tgeValor;
    }

    /**
     * Obtiene la fecha de creación del TipoGeografico.
     * @return Fecha de creación del TipoGeografico.
     */
    public Date getTgeFechaCreacion() {
        return tgeFechaCreacion;
    }

    /**
     * Establece la fecha de creación del TipoGeografico.
     * @param tgeFechaCreacion Fecha de creación del TipoGeografico.
     */
    public void setTgeFechaCreacion(Date tgeFechaCreacion) {
        this.tgeFechaCreacion = tgeFechaCreacion;
    }

    /**
     * Obtiene el usuario que crea el TipoGeografico.
     * @return Cadena con el usuario que crea el TipoGeografico.
     */
    public String getTgeUsuarioCreacion() {
        return tgeUsuarioCreacion;
    }

    /**
     * Establece el usuario que crea el TipoGeografico.
     * @param tgeUsuarioCreacion Cadena con el usuario que crea el TipoGeografico.
     */
    public void setTgeUsuarioCreacion(String tgeUsuarioCreacion) {
        this.tgeUsuarioCreacion = tgeUsuarioCreacion;
    }

    /**
     * Obtiene la fecha de modificación del TipoGeografico.
     * @return Fecha de modificación del TipoGeografico.
     */
    public Date getTgeFechaModificacion() {
        return tgeFechaModificacion;
    }

    /**
     * Establece la fecha de modificación del TipoGeografico.
     * @param tgeFechaModificacion Fecha de modificación del TipoGeografico.
     */
    public void setTgeFechaModificacion(Date tgeFechaModificacion) {
        this.tgeFechaModificacion = tgeFechaModificacion;
    }

    /**
     * Obtiene el usuario que modifica el TipoGeografico.
     * @return Cadena con el usuario que modifica el TipoGeografico.
     */
    public String getTgeUsuarioModificacion() {
        return tgeUsuarioModificacion;
    }

    /**
     * Establece el usuario que modifica el TipoGeografico.
     * @param tgeUsuarioModificacion Cadena con el usuario que modifica el TipoGeografico.
     */
    public void setTgeUsuarioModificacion(String tgeUsuarioModificacion) {
        this.tgeUsuarioModificacion = tgeUsuarioModificacion;
    }

    /**
     * Obtiene información para el control del TipoGeografico en uso.
     * @return Cadena con información del TipoGeografico. (Id, Valor)
     */
    public String auditoria() {
        return "TipoGeografico:" + "tgeId=" + tgeId + ", tgeValor=" + tgeValor + '.';
    }

    /**
     * Sobre Escritura del método toString().
     * @return Cadena con información del TipoGeografico. (Id, Valor)
     */
    @Override
    public String toString() {
        return "TipoGeografico:" + "tgeId=" + tgeId + ", tgeValor=" + tgeValor + '.';
    }
}
