package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase SolicitudRed
 * Implementa Serialización.
 *
 * @author 	Nombre del autor.
 * @version	1.0
 */
public class SolicitudRed implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal sreId;
    private String sreObservaciones;
    private String sreEstado;
    private GeograficoPolitico geograficoPolitico;
    private Date sreFechaCreacion;
    private String sreUsuarioCreacion;
    private Date sreFechaModificacion;
    private String sreUsuarioModificacion;

    /**
     * Constructor
     */
    public SolicitudRed() {
    }

    /**
     * Constructor con parámetros.
     * @param sreId Entero con el Código Id de la solicitud de red.
     */
    public SolicitudRed(BigDecimal sreId) {
        this.sreId = sreId;
    }





    /**
     * Obtiene el valor del parámetro SreId.
     * @return Entero con el valor del parámetro SreId.
     */
    public BigDecimal getSreId() {
        return sreId;
    }

    /**
     * Establece el valor del parámetro SreId.
     * @param sreId Entero con el valor del parámetro SreId.
     */
    public void setSreId(BigDecimal sreId) {
        this.sreId = sreId;
    }

    /**
     * Obtiene las observaciones del Sre.
     * @return Cadena con las observaciones del Sre.
     */
    public String getSreObservaciones() {
        return sreObservaciones;
    }

    /**
     * Establece las observaciones del Sre.
     * @param sreObservaciones Cadena con las observaciones Sre.
     */
    public void setSreObservaciones(String sreObservaciones) {
        this.sreObservaciones = sreObservaciones;
    }

    /**
     * Obtiene la fecha de creación del Sre.
     * @return Fecha de creación del Sre.
     */
    public Date getSreFechaCreacion() {
        return sreFechaCreacion;
    }

    /**
     * Establece la fecha de creación del Sre.
     * @param sreFechaCreacion Fecha de creación del Sre.
     */
    public void setSreFechaCreacion(Date sreFechaCreacion) {
        this.sreFechaCreacion = sreFechaCreacion;
    }

    /**
     * Obtiene el usuario que realiza la creación del Sre.
     * @return Cadena con el usuario que crea el Sre.
     */
    public String getSreUsuarioCreacion() {
        return sreUsuarioCreacion;
    }

    /**
     * Establece el usuario que crea el Sre.
     * @param sreUsuarioCreacion Cadena con el usuario que crea el Sre.
     */
    public void setSreUsuarioCreacion(String sreUsuarioCreacion) {
        this.sreUsuarioCreacion = sreUsuarioCreacion;
    }

    /**
     * Obtiene la fecha de modificación del Sre.
     * @return Fecha de modificación del Sre.
     */
    public Date getSreFechaModificacion() {
        return sreFechaModificacion;
    }

    /**
     * Establece la fecha de modificación del Sre.
     * @param sreFechaModificacion Fecha de modificación del Sre.
     */
    public void setSreFechaModificacion(Date sreFechaModificacion) {
        this.sreFechaModificacion = sreFechaModificacion;
    }

    /**
     * Obtiene el usuario que modifica el Sre.
     * @return Cadena con el usuario que modifica el Sre.
     */
    public String getSreUsuarioModificacion() {
        return sreUsuarioModificacion;
    }

    /**
     * Establece el usuario que realiza la modificación del Sre.
     * @param sreUsuarioModificacion Cadena con el usuario que modifica el Sre.
     */
    public void setSreUsuarioModificacion(String sreUsuarioModificacion) {
        this.sreUsuarioModificacion = sreUsuarioModificacion;
    }

    /**
     * Obtiene el estado del Sre.
     * @return Cadena con el estado del Sre.
     */
    public String getSreEstado() {
        return sreEstado;
    }

    /**
     * Establece el estado del Sre.
     * @param sreEstado Cadena con el estado del Sre.
     */
    public void setSreEstado(String sreEstado) {
        this.sreEstado = sreEstado;
    }

    /**
     * Establece el GeograficoPolitico de la solicitud de red.
     * @return GeograficoPolitico de la solicitud de red.
     */
    public GeograficoPolitico getGeograficoPolitico() {
        return geograficoPolitico;
    }

    /**
     * Establece el GeograficoPolitico de la solicitud de red.
     * @param geograficoPolitico GeograficoPolitico de la solicitud de red.
     */
    public void setGeograficoPolitico(GeograficoPolitico geograficoPolitico) {
        this.geograficoPolitico = geograficoPolitico;
    }



    /**
     * Obtiene información para el control del SolicitudRed en uso.
     * @return Cadena con información del SolicitudRed. (Id, Observaciones, Estado, Archivo Generado, Email del solicitante, Tiempo Horas, GeograficoPolitico)
     */
    public String auditoria() {
        return "SolicitudRed={" + "sreId=" + sreId
                + ", sreObservaciones=" + sreObservaciones
                + ", sreEstado=" + sreEstado
                + ", geograficoPolitico=" + geograficoPolitico + '}';
    }

    /**
     * Sobre escritura del método toString().
     * @return Cadena con información del SolicitudRed. (Id, Observaciones, Estado, Archivo Generado, Email del solicitante, Tiempo Horas, GeograficoPolitico)
     */
    @Override
    public String toString() {
        return "SolicitudRed={" + "sreId=" + sreId
                + ", sreObservaciones=" + sreObservaciones
                + ", sreEstado=" + sreEstado
                + ", geograficoPolitico=" + geograficoPolitico + '}';
    }
}
