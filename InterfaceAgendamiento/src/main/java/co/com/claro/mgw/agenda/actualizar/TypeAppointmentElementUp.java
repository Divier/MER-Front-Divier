/**
 *
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 *
 */
package co.com.claro.mgw.agenda.actualizar;

import co.com.claro.mgw.agenda.agendar.crear.ResourcePreferences;
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
 *         &lt;element name="worktype_label"
 * type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="time_slot" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="properties"
 * type="{http://www.amx.com.co/SIEC/ETAdirect/WS_SIEC_
 * RealizarReagendacionMGWInbound}TypePropertiesArray" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeAppointmentElement", propOrder
        = {
            "appt_number",
            "customer_number",
            "worktype_label",
            "time_slot",
            "name",
            "properties"
        })
public class TypeAppointmentElementUp {

    /**
     *
     */
    public TypeAppointmentElementUp() {
    }

    /**
     *
     * @param apptNumber
     * @param customerNumber
     * @param worktypeLabel
     * @param timeSlot
     * @param name
     * @param properties
     */
    public TypeAppointmentElementUp(String apptNumber, String customerNumber, String worktypeLabel, String timeSlot, String name,
            TypePropertiesArrayUp properties) {
        this.appt_number = apptNumber;
        this.customer_number = customerNumber;
        this.worktype_label = worktypeLabel;
        this.time_slot = timeSlot;
        this.name = name;
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
    @XmlElement(name = "customer_number")
    protected String customer_number;

    /**
     *
     */
    @XmlElement(name = "worktype_label")
    protected String worktype_label;

    /**
     *
     */
    @XmlElement(name = "time_slot")
    protected String time_slot;

    /**
     *
     */
    protected String name;

    /**
     *
     */
    protected TypePropertiesArrayUp properties;
    
        
    @XmlElement(name = "resourcePreferences")
    protected ResourcePreferences resourcePreferences;

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
     *
     * @param appt_number
     */
    public void setAppt_number(String appt_number) {
        this.appt_number = appt_number;
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
     * Define el valor de la propiedad worktype_label.
     *
     *
     * @param worktype_label
     */
    public void setWorktype_label(String worktype_label) {
        this.worktype_label = worktype_label;
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
     * @param value allowed object is {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtiene el valor de la propiedad properties.
     *
     * @return possible object is {@link TypePropertiesArray }
     *
     */
    public TypePropertiesArrayUp getProperties() {
        return properties;
    }

    /**
     * Define el valor de la propiedad properties.
     *
     * @param value allowed object is {@link TypePropertiesArray }
     *
     */
    public void setProperties(TypePropertiesArrayUp value) {
        this.properties = value;
    }

    /**
     * Obtiene el valor de la propiedad customer_number.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCustomer_number() {
        return customer_number;
    }

    /**
     * Define el valor de la propiedad customer_number.
     *
     * @param customer_number allowed object is {@link String }
     *
     */
    public void setCustomer_number(String customer_number) {
        this.customer_number = customer_number;
    }

    public ResourcePreferences getResourcePreferences() {
        return resourcePreferences;
    }

    public void setResourcePreferences(ResourcePreferences resourcePreferences) {
        this.resourcePreferences = resourcePreferences;
    }
}
