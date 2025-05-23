
package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Clase que contiene las propiedades de salida a la operacion consultaFactibilidad
 *
 * @author Yasser Leon
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultaFactibilidadResponseDto", propOrder = {
        "factibilidadId",
        "codigoDane",
        "direccionEstandarizada",
        "latitud",
        "longitud",
        "ccmId",
        "direccionId",
        "factibilidades"
})
public class ConsultaFactibilidadResponseDto extends CmtDefaultBasicResponse {

    private String factibilidadId;
    private String codigoDane;
    private String direccionEstandarizada;
    private String latitud;
    private String longitud;
    private String ccmId;
    private String direccionId;
    private FactibilidadesDto factibilidades;

    /**
     * Gets the value of the factibilidadId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getFactibilidadId() {
        return factibilidadId;
    }

    /**
     * Sets the value of the factibilidadId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setFactibilidadId(String value) {
        this.factibilidadId = value;
    }

    /**
     * Gets the value of the codigoDane property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCodigoDane() {
        return codigoDane;
    }

    /**
     * Sets the value of the codigoDane property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCodigoDane(String value) {
        this.codigoDane = value;
    }

    /**
     * Gets the value of the direccionEstandarizada property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDireccionEstandarizada() {
        return direccionEstandarizada;
    }

    /**
     * Sets the value of the direccionEstandarizada property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDireccionEstandarizada(String value) {
        this.direccionEstandarizada = value;
    }

    /**
     * Gets the value of the latitud property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLatitud() {
        return latitud;
    }

    /**
     * Sets the value of the latitud property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLatitud(String value) {
        this.latitud = value;
    }

    /**
     * Gets the value of the longitud property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLongitud() {
        return longitud;
    }

    /**
     * Sets the value of the longitud property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLongitud(String value) {
        this.longitud = value;
    }

    /**
     * Gets the value of the ccmId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCcmId() {
        return ccmId;
    }

    /**
     * Sets the value of the ccmId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCcmId(String value) {
        this.ccmId = value;
    }

    /**
     * Gets the value of the direccionId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDireccionId() {
        return direccionId;
    }

    /**
     * Sets the value of the direccionId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDireccionId(String value) {
        this.direccionId = value;
    }

    /**
     * Gets the value of the factibilidades property.
     *
     * @return possible object is
     * {@link FactibilidadesDto }
     */
    public FactibilidadesDto getFactibilidades() {
        return factibilidades;
    }

    /**
     * Sets the value of the factibilidades property.
     *
     * @param value allowed object is
     *              {@link FactibilidadesDto }
     */
    public void setFactibilidades(FactibilidadesDto value) {
        this.factibilidades = value;
    }

}
