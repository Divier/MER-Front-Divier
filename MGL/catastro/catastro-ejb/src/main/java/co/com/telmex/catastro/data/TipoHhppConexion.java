package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase TipoHhppConexion
 * Implementa Serialización.
 *
 * @author 	Nataly Orozco Torres.
 * @version	1.0
 */
public class TipoHhppConexion implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal thcId;
    private String thcCodigo;
    private String thcNombre;
    private Date thcFechaCreacion;
    private String thcUsuarioCreacion;
    private Date thcFechaModificacion;
    private String thcUsuarioModificacion;

    /**
     * Constructor.
     */
    public TipoHhppConexion() {
    }

    /**
     * Obtiene la fecha de creación del TipoHhppConexion.
     * @return Fecha de creación del TipoHhppConexion.
     */
    public Date getThcFechaCreacion() {
        return thcFechaCreacion;
    }

    /**
     * Establece la fecha de creación del TipoHhppConexion.
     * @param thcFechaCreacion Fecha de creación del TipoHhppConexion.
     */
    public void setThcFechaCreacion(Date thcFechaCreacion) {
        this.thcFechaCreacion = thcFechaCreacion;
    }

    /**
     * Obtiene la fecha de modificación del TipoHhppConexion.
     * @return Fecha de modificación del TipoHhppConexion.
     */
    public Date getThcFechaModificacion() {
        return thcFechaModificacion;
    }

    /**
     * Establece la fecha de modificación del TipoHhppConexion.
     * @param thcFechaModificacion Fecha de modificación del TipoHhppConexion.
     */
    public void setThcFechaModificacion(Date thcFechaModificacion) {
        this.thcFechaModificacion = thcFechaModificacion;
    }

    /**
     * Obtiene el Id del TipoHhppConexion.
     * @return Entero con el Id del TipoHhppConexion.
     */
    public BigDecimal getThcId() {
        return thcId;
    }

    /**
     * Establece el Id del TipoHhppConexion.
     * @param thcId Entero con el Id del TipoHhppConexion.
     */
    public void setThcId(BigDecimal thcId) {
        this.thcId = thcId;
    }

    /**
     * Obtiene el nombre del usuario que crea el TipoHhppConexion.
     * @return Cadena con el nombre del usuario que crea el TipoHhppConexion.
     */
    public String getThcUsuarioCreacion() {
        return thcUsuarioCreacion;
    }

    /**
     * Establece el nombre del usuario que crea el TipoHhppConexion.
     * @param thcUsuarioCreacion Cadena con el nombre del usuario que crea el TipoHhppConexion.
     */
    public void setThcUsuarioCreacion(String thcUsuarioCreacion) {
        this.thcUsuarioCreacion = thcUsuarioCreacion;
    }

    /**
     * Obtiene el nombre del usuario que modifica el TipoHhppConexion.
     * @return Cadena con el nombre del usuario que modifica el TipoHhppConexion.
     */
    public String getThcUsuarioModificacion() {
        return thcUsuarioModificacion;
    }

    /**
     * Establece el nombre del usuario que modifica el TipoHhppConexion.
     * @param thcUsuarioModificacion Cadena con el nombre del usuario que modifica el TipoHhppConexion.
     */
    public void setThcUsuarioModificacion(String thcUsuarioModificacion) {
        this.thcUsuarioModificacion = thcUsuarioModificacion;
    }

    /**
     * Obtiene el Código del TipoHhppConexion.
     * @return Cadena con el Código del TipoHhppConexion.
     */
    public String getThcCodigo() {
        return thcCodigo;
    }

    /**
     * Establece el Código del TipoHhppConexion.
     * @param thcCodigo Cadena con el Código del TipoHhppConexion.
     */
    public void setThcCodigo(String thcCodigo) {
        this.thcCodigo = thcCodigo;
    }

    /**
     * Obtiene el nombre del TipoHhppConexion.
     * @return Cadena con el nombre del TipoHhppConexion.
     */
    public String getThcNombre() {
        return thcNombre;
    }

    /**
     * Establece el nombre del TipoHhppConexion.
     * @param thcNombre Cadena con el nombre del TipoHhppConexion.
     */
    public void setThcNombre(String thcNombre) {
        this.thcNombre = thcNombre;
    }

    /**
     * Obtiene información para el control del TipoHhppConexion en uso.
     * @return Cadena con información del TipoHhppConexion. (Id, Código, Nombre)
     */
    public String auditoria() {
        return "TipoHhppConexion:" + "thcId=" + thcId
                + ", thcCodigo=" + thcCodigo + ", thcNombre=" + thcNombre + '.';
    }

    /**
     * Sobre Escritura del método toString().
     * @return Cadena con información del TipoHhppConexion. (Id, Código, Nombre)
     */
    @Override
    public String toString() {
        return "TipoHhppConexion:" + "thcId=" + thcId
                + ", thcCodigo=" + thcCodigo + ", thcNombre=" + thcNombre + '.';
    }
}
