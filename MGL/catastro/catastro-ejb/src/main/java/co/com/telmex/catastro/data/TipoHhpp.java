package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase TipoHhpp
 * Implementa Serialización.
 *
 * @author 	Deiver.
 * @version	1.0
 */
public class TipoHhpp implements Serializable {

    private String thhId;
    private String thhValor;
    private Date thhFechaCreacion;
    private String thhUsuarioCreacion;
    private Date thhFechaModificacion;
    private String thhUsuarioModificacion;

    /**
     * Constructor.
     */
    public TipoHhpp() {
    }

    /**
     * Constructor con parámetros.
     * @param thhId Cadena con el Id del TipoHhpp.
     */
    public TipoHhpp(String thhId) {
        this.thhId = thhId;
    }

    /**
     * Obtiene el Id del TipoHhpp
     * @return Cadena con el Id del TipoHhpp.
     */
    public String getThhId() {
        return thhId;
    }

    /**
     * Establece el Id del TipoHhpp.
     * @param thhId Cadena con el Id del TipoHhpp.
     */
    public void setThhId(String thhId) {
        this.thhId = thhId;
    }

    /**
     * Obtiene el valor del TipoHhpp.
     * @return Cadena con el valor del TipoHhpp.
     */
    public String getThhValor() {
        return thhValor;
    }

    /**
     * Establece el valor del TipoHhpp.
     * @param thhValor Cadena con el valor del TipoHhpp.
     */
    public void setThhValor(String thhValor) {
        this.thhValor = thhValor;
    }

    /**
     * Obtiene la fecha de creación del TipoHhpp.
     * @return Fecha de creación del TipoHhpp.
     */
    public Date getThhFechaCreacion() {
        return thhFechaCreacion;
    }

    /**
     * Establece la fecha de creación del TipoHhpp.
     * @param thhFechaCreacion Fecha de creación del TipoHhpp.
     */
    public void setThhFechaCreacion(Date thhFechaCreacion) {
        this.thhFechaCreacion = thhFechaCreacion;
    }

    /**
     * Obtiene el usuario que crea el TipoHhpp.
     * @return Cadena con el nombre de usuario que crea el TipoHhpp.
     */
    public String getThhUsuarioCreacion() {
        return thhUsuarioCreacion;
    }

    /**
     * Establece el usuario que crea el TipoHhpp.
     * @param thhUsuarioCreacion Cadena con el usuario que crea el TipoHhpp.
     */
    public void setThhUsuarioCreacion(String thhUsuarioCreacion) {
        this.thhUsuarioCreacion = thhUsuarioCreacion;
    }

    /**
     * Obtiene la fecha de modificación del TipoHhpp.
     * @return Fecha de modificación del TipoHhpp.
     */
    public Date getThhFechaModificacion() {
        return thhFechaModificacion;
    }

    /**
     * Establece la fecha de modificación del TipoHhpp.
     * @param thhFechaModificacion Fecha de modificación del TipoHhpp.
     */
    public void setThhFechaModificacion(Date thhFechaModificacion) {
        this.thhFechaModificacion = thhFechaModificacion;
    }

    /**
     * Obtiene el usuario que modifica el TipoHhpp.
     * @return Cadena con el usuario que modifica el TipoHhpp.
     */
    public String getThhUsuarioModificacion() {
        return thhUsuarioModificacion;
    }

    /**
     * Establece el usuario que modifica el TipoHhpp.
     * @param thhUsuarioModificacion Cadena con el usuario que modifica el TipoHhpp.
     */
    public void setThhUsuarioModificacion(String thhUsuarioModificacion) {
        this.thhUsuarioModificacion = thhUsuarioModificacion;
    }

    /**
     * Obtiene información para el control del TipoHhpp en uso.
     * @return Cadena con información del TipoHhpp. (Id, Nombre)
     */
    public String auditoria() {
        return "TipoHhpp:" + "thhId=" + thhId + ", thhValor=" + thhValor + '.';
    }

    /**
     * Sobre Escritura del método toString().
     * @return Cadena con información del TipoHhpp. (Id, Nombre)
     */
    @Override
    public String toString() {
        return "TipoHhpp:" + "thhId=" + thhId + ", thhValor=" + thhValor + '.';
    }
}
