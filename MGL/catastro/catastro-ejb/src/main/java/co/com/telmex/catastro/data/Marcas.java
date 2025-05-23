package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase Marcas
 * Implementa Serialización.
 *
 * @author 	Nataly Orozco Torres
 * @version	1.0
 */
public class Marcas implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal marId;
    private String marNombre;
    private String marCodigo;
    private TipoMarcas tipoMarca;
    private String marUsuarioCreacion;
    private Date marFechaCreacion;
    private String marUsuarioModificacion;
    private Date marFechaModificacion;

    /**
     * Constructor
     */
    public Marcas() {
    }

    /**
     * Obtiene el código de la marca
     * @return Cadena con el código de la marca.
     */
    public String getMarCodigo() {
        return marCodigo;
    }

    /**
     * Establece el código de la marca.
     * @param marCodigo Cadena con el código de la marca.
     */
    public void setMarCodigo(String marCodigo) {
        this.marCodigo = marCodigo;
    }

    /**
     * Obtiene el Id de la marca.
     * @return Entero con el Id de la marca.
     */
    public BigDecimal getMarId() {
        return marId;
    }

    /**
     * Establece el Id de la marca.
     * @param marId Entero con el Id de la marca.
     */
    public void setMarId(BigDecimal marId) {
        this.marId = marId;
    }

    /**
     * Obtiene el nombre de la marca.
     * @return Cadena con el nombre de la marca.
     */
    public String getMarNombre() {
        return marNombre;
    }

    /**
     * Establece el nombre de la marca.
     * @param marNombre Cadena con el nombre de la marca.
     */
    public void setMarNombre(String marNombre) {
        this.marNombre = marNombre;
    }

    /**
     * Obtiene el Tipo de la marca.
     * @return Objeto TipoMarcas con la información solicitada.
     */
    public TipoMarcas getTipoMarca() {
        return tipoMarca;
    }

    /**
     * Establece el TipoMarca
     * @param tipoMarca Objeto TipoMarcas con la información solicitada.
     */
    public void setTipoMarca(TipoMarcas tipoMarca) {
        this.tipoMarca = tipoMarca;
    }

    /**
     * Obtiene la fecha de creación.
     * @return Fecha de creación.
     */
    public Date getMarFechaCreacion() {
        return marFechaCreacion;
    }

    /**
     * Establece la fecha de creación
     * @param marFechaCreacion Fecha de creación.
     */
    public void setMarFechaCreacion(Date marFechaCreacion) {
        this.marFechaCreacion = marFechaCreacion;
    }

    /**
     * Obtiene el usuario que realiza la creación.
     * @return Cadena con el usuario que realiza la creación.
     */
    public String getMarUsuarioCreacion() {
        return marUsuarioCreacion;
    }

    /**
     * Establece el usuario que realiza la creación.
     * @param marUsuarioCreacion Cadena con el usuario de creación.
     */
    public void setMarUsuarioCreacion(String marUsuarioCreacion) {
        this.marUsuarioCreacion = marUsuarioCreacion;
    }

    /**
     * Obtiene la fecha de modificación.
     * @return Fecha de modificación.
     */
    public Date getMarFechaModificacion() {
        return marFechaModificacion;
    }

    /**
     * Establece la fecha de modificación
     * @param marFechaModificacion Fecha de modificación.
     */
    public void setMarFechaModificacion(Date marFechaModificacion) {
        this.marFechaModificacion = marFechaModificacion;
    }

    /**
     * Obtiene el usuario que realiza la modificación.
     * @return Cadena con el usuario que realiza la modificación.
     */
    public String getMarUsuarioModificacion() {
        return marUsuarioModificacion;
    }

    /**
     * Establece el usuario que realiza la modificación.
     * @param marUsuarioModificacion Cadena con el usuario que realiza la modificación.
     */
    public void setMarUsuarioModificacion(String marUsuarioModificacion) {
        this.marUsuarioModificacion = marUsuarioModificacion;
    }

    /**
     * Obtiene información para auditoría
     * @return Cadena con información para auditoría.
     */
    public String auditoria() {
        String auditoria = "Marcas:" + "marId=" + marId
                + ", marNombre=" + marNombre + ", marCodigo=" + marCodigo;
        if (tipoMarca != null) {
            auditoria = auditoria + tipoMarca.getTmaId();
        }
        auditoria = auditoria + ", marUsuarioCreacion=" + marUsuarioCreacion
                + ", marFechaCreacion=" + marFechaCreacion
                + ", marUsuarioModificacion=" + marUsuarioModificacion
                + ", marFechaModificacion=" + marFechaModificacion;

        return auditoria;
    }

    /**
     * Sobre Escritura del método toString()
     * @return Cadena con la información de la auditoría.
     */
    @Override
    public String toString() {
        String resultado = "Marcas:" + "marId=" + marId
                + ", marNombre=" + marNombre + ", marCodigo=" + marCodigo;
        if (tipoMarca != null) {
            resultado = resultado + tipoMarca.getTmaId();
        }
        resultado = resultado + ", marUsuarioCreacion=" + marUsuarioCreacion
                + ", marFechaCreacion=" + marFechaCreacion
                + ", marUsuarioModificacion=" + marUsuarioModificacion
                + ", marFechaModificacion=" + marFechaModificacion;

        return resultado;
    }
}
