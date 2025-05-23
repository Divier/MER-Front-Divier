/**
 *
 * Objetivo: Clase data Ws Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 *
 */
package co.com.claro.mgw.agenda.agendar.crear;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Clase Java para TypeCommandElementCr complex type.
 *
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypeCommandElementCr"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="external_id" type="{http://www.w3.org/2001/XMLSche
 * ma}string" minOccurs="0"/&gt;
 *         &lt;element name="fallback_external_id" type="{http://www.w3.org/2001
 * /XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="appointment" type="{http://www.amx.com.co/SIEC/ETA
 * direct/WS_SIEC_CrearOrdenMGWInbound}TypeAppointmentElementCr" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeCommandElement", propOrder = {
    "date",
    "external_id",
    "type",
    "fallback_external_id",
    "appointment"
})
public class TypeCommandElementCr {

    /**
     *
     */
    public TypeCommandElementCr() {
    }

    /**
     *
     * @param date
     * @param externalId
     * @param fallbackExternalId
     * @param type
     * @param appointment
     */
    public TypeCommandElementCr(String date, String externalId,
            String fallbackExternalId, String type, TypeAppointmentElementCr appointment) {
        this.date = date;
        this.external_id = externalId;
        this.fallback_external_id = fallbackExternalId;
        this.appointment = appointment;
        this.type = type;
    }
    /**
     *
     */
    protected String date;
    /**
     *
     */
    @XmlElement(name = "external_id")
    protected String external_id;
    /**
     *
     */
    @XmlElement(name = "fallback_external_id")
    protected String fallback_external_id;
    /**
     *
     */
    @XmlElement(name = "type")
    protected String type;
    /**
     *
     */
    protected TypeAppointmentElementCr appointment;

    /**
     * Obtiene el valor de la propiedad date.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDate() {
        return date;
    }

    /**
     * Define el valor de la propiedad date.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Obtiene el valor de la propiedad externalId.
     *
     * @return possible object is {@link String }
     *
     */
    public String getExternal_id() {
        return external_id;
    }

    /**
     * Define el valor de la propiedad external_id.
     *
     *
     * @param external_id
     */
    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }

    /**
     * Obtiene el valor de la propiedad fallback_external_id.
     *
     * @return possible object is {@link String }
     *
     */
    public String getFallback_external_id() {
        return fallback_external_id;
    }

    /**
     * Define el valor de la propiedad fallback_external_id.
     *
     *
     * @param fallback_external_id
     */
    public void setFallback_external_id(String fallback_external_id) {
        this.fallback_external_id = fallback_external_id;
    }

    /**
     * Obtiene el valor de la propiedad appointment.
     *
     * @return possible object is {@link TypeAppointmentElementCr }
     *
     */
    public TypeAppointmentElementCr getAppointment() {
        return appointment;
    }

    /**
     * Define el valor de la propiedad appointment.
     *
     * @param value allowed object is {@link TypeAppointmentElementCr }
     *
     */
    public void setAppointment(TypeAppointmentElementCr value) {
        this.appointment = value;
    }

    /**
     * Obtiene el valor de la propiedad type.
     *
     * @return possible object is {@link type }
     *
     */
    public String getType() {
        return type;
    }

    /**
     * Define el valor de la propiedad type.
     *
     * @param type
     *
     */
    public void setType(String type) {
        this.type = type;
    }
}
