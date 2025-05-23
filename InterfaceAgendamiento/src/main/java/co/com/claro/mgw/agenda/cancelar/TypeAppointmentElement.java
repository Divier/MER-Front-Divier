/**
 *
 * Objetivo: Clase data Ws Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 *
 */
package co.com.claro.mgw.agenda.cancelar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Clase Java para TypeAppointmentElement complex type.
 *
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypeAppointmentElement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="appt_number"
 * type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="properties"
 * type="{http://www.amx.com.co/SIEC/ETAdirect/WS_SIEC_CancelarOrdenMGWInbound}
 * TypePropertiesArray" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeAppointmentElement", propOrder = {
    "appt_number",
    "time_slot",
    "date",
    "external_id",
    "worktype_label",
    "name",
    "properties"
})
public class TypeAppointmentElement {

    /**
     *
     */
    public TypeAppointmentElement() {
    }

    /**
     *
     * @param apptNumber
     * @param timeSlot
     * @param date
     * @param externalId
     * @param properties
     * @param name
     * @param worktypeLabel
     */
    public TypeAppointmentElement(String apptNumber, String timeSlot,
            String date, String externalId, String worktypeLabel,
            String name, TypePropertiesArray properties) {
        this.appt_number = apptNumber;
        this.date = date;
        this.external_id = externalId;
        this.name = name;
        this.worktype_label = worktypeLabel;
        this.time_slot = timeSlot;
        this.properties = properties;
    }
    /**
     *
     */
    @XmlElement(name = "appt_number")
    protected String appt_number;
    /**
     *
     */
    @XmlElement(name = "time_slot")
    protected String time_slot;
    /**
     *
     */
    @XmlElement(name = "date")
    protected String date;
    /**
     *
     */
    @XmlElement(name = "external_id")
    protected String external_id;
    /**
     *
     */
    @XmlElement(name = "worktype_label")
    protected String worktype_label;
    /**
     *
     */
    @XmlElement(name = "name")
    protected String name;
    /**
     *
     */
    protected TypePropertiesArray properties;

    /**
     * Obtiene el valor de la propiedad appt_number.
     *
     * @return possible object is {@link String }
     *
     */
    public String getAppt_number() {
        return appt_number;
    }

    /**
     * Define el valor de la propiedad appt_number.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setAppt_number(String appt_number) {
        this.appt_number = appt_number;
    }

    /**
     * Obtiene el valor de la propiedad properties.
     *
     * @return possible object is {@link TypePropertiesArray }
     *
     */
    public TypePropertiesArray getProperties() {
        return properties;
    }

    /**
     * Define el valor de la propiedad properties.
     *
     * @param value allowed object is {@link TypePropertiesArray }
     *
     */
    public void setProperties(TypePropertiesArray value) {
        this.properties = value;
    }

    /**
     * Obtiene el valor de la propiedad time_slot.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTime_slot() {
        return time_slot;
    }

    /**
     * Define el valor de la propiedad time_slot.
     *
     *
     * @param time_slot
     */
    public void setTime_slot(String time_slot) {
        this.time_slot = time_slot;
    }

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
     * @param date
     *
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Obtiene el valor de la propiedad external_id.
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
     * @param externalId
     *
     */
    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }

    /**
     * Obtiene el valor de la propiedad worktype_label.
     *
     * @return possible object is {@link String }
     *
     */
    public String getWorktype_label() {
        return worktype_label;
    }

    /**
     * Define el valor de la propiedad worktypeLabel.
     *
     *
     * @param worktype_label
     */
    public void setWorktype_label(String worktype_label) {
        this.worktype_label = worktype_label;
    }

    /**
     * Obtiene el valor de la propiedad name.
     *
     * @return possible object is {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Define el valor de la propiedad name.
     *
     * @param name
     *
     */
    public void setName(String name) {
        this.name = name;
    }
}
