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
 * Clase Java para TypeAppointmentElementCr complex type.
 *
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypeAppointmentElementCr"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="appt_number" type="{http://www.w3.org/2001/XMLSch
 * ema}string" minOccurs="0"/&gt;
 *         &lt;element name="customer_number" type="{http://www.w3.org/2001/X
 * MLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="worktype_label" type="{http://www.w3.org/2001/XM
 * LSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sla_window_start" type="{http://www.w3.org/2001/X
 * MLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sla_window_end" type="{http://www.w3.org/2001/X
 * MLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="time_slot" type="{http://www.w3.org/2001/XMLSch
 * ema}string" minOccurs="0"/&gt;
 *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="cell" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="phone" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="zip" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="points" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="coordx" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="coordy" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="properties" type="{http://www.amx.com.co/SIEC/ETA
 * direct/WS_SIEC_CrearOrdenMGWInbound}TypePropertiesArrayCr" minOccurs="0"/&gt;
 *         &lt;element name="inventories" type="{http://www.amx.com.co/SIEC/ETA
 * direct/WS_SIEC_CrearOrdenMGWInbound}TypeInventoriesArrayCr" minOccurs="0"/&gt;
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
    "customer_number",
    "worktype_label",
    "sla_window_start",
    "sla_window_end",
    "time_slot",
    "duration",
    "name",
    "email",
    "cell",
    "phone",
    "address",
    "city",
    "state",
    "zip",
    "points",
    "coordx",
    "coordy",
    "properties",
    "inventories"
})
public class TypeAppointmentElementCr {

    /**
     *
     */
    public TypeAppointmentElementCr() {
    }

    /**
     *
     * @param apptNumber
     * @param customerNumber
     * @param worktypeLabel
     * @param slaWindowStart
     * @param slaWindowEnd
     * @param timeSlot
     * @param duration
     * @param name
     * @param email
     * @param cell
     * @param phone
     * @param address
     * @param city
     * @param state
     * @param zip
     * @param points
     * @param coordx
     * @param coordy
     * @param properties
     * @param inventories
     */
    public TypeAppointmentElementCr(String apptNumber, String customerNumber,
            String worktypeLabel, String slaWindowStart, String slaWindowEnd,
            String timeSlot, String duration, String name, String email, String cell,
            String phone, String address, String city, String state, String zip,
            String points, String coordx, String coordy,
            TypePropertiesArrayCr properties, TypeInventoriesArrayCr inventories) {
        this.appt_number = apptNumber;
        this.customer_number = customerNumber;
        this.worktype_label = worktypeLabel;
        this.sla_window_start = slaWindowStart;
        this.sla_window_end = slaWindowEnd;
        this.time_slot = timeSlot;
        this.duration = duration;
        this.name = name;
        this.email = email;
        this.cell = cell;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.points = points;
        this.coordx = coordx;
        this.coordy = coordy;
        this.properties = properties;
        this.inventories = inventories;
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
    @XmlElement(name = "sla_window_start")
    protected String sla_window_start;
    /**
     *
     */
    @XmlElement(name = "sla_window_end")
    protected String sla_window_end;
    /**
     *
     */
    @XmlElement(name = "time_slot")
    protected String time_slot;
    /**
     *
     */
    protected String duration;
    /**
     *
     */
    protected String name;
    /**
     *
     */
    protected String email;
    /**
     *
     */
    protected String cell;
    /**
     *
     */
    protected String phone;
    /**
     *
     */
    protected String address;
    /**
     *
     */
    protected String city;
    /**
     *
     */
    protected String state;
    /**
     *
     */
    protected String zip;
    /**
     *
     */
    protected String points;
    /**
     *
     */
    protected String coordx;
    /**
     *
     */
    protected String coordy;
    /**
     *
     */
    protected TypePropertiesArrayCr properties;
    /**
     *
     */
    protected TypeInventoriesArrayCr inventories;
    /**
     *
     */
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
     *
     * @param customer_number
     */
    public void setCustomer_number(String customer_number) {
        this.customer_number = customer_number;
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
     * Obtiene el valor de la propiedad sla_window_start.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSla_window_start() {
        return sla_window_start;
    }

    /**
     * Define el valor de la propiedad sla_window_start.
     *
     *
     * @param sla_window_start
     */
    public void setSla_window_start(String sla_window_start) {
        this.sla_window_start = sla_window_start;
    }

    /**
     * Obtiene el valor de la propiedad sla_window_end.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSla_window_end() {
        return sla_window_end;
    }

    /**
     * Define el valor de la propiedad sla_window_end.
     *
     *
     * @param sla_window_end
     */
    public void setSla_window_end(String sla_window_end) {
        this.sla_window_end = sla_window_end;
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
     * Obtiene el valor de la propiedad duration.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Define el valor de la propiedad duration.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDuration(String value) {
        this.duration = value;
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
     * Obtiene el valor de la propiedad email.
     *
     * @return possible object is {@link String }
     *
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define el valor de la propiedad email.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Obtiene el valor de la propiedad cell.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCell() {
        return cell;
    }

    /**
     * Define el valor de la propiedad cell.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCell(String value) {
        this.cell = value;
    }

    /**
     * Obtiene el valor de la propiedad phone.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Define el valor de la propiedad phone.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Obtiene el valor de la propiedad address.
     *
     * @return possible object is {@link String }
     *
     */
    public String getAddress() {
        return address;
    }

    /**
     * Define el valor de la propiedad address.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Obtiene el valor de la propiedad city.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCity() {
        return city;
    }

    /**
     * Define el valor de la propiedad city.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Obtiene el valor de la propiedad state.
     *
     * @return possible object is {@link String }
     *
     */
    public String getState() {
        return state;
    }

    /**
     * Define el valor de la propiedad state.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Obtiene el valor de la propiedad zip.
     *
     * @return possible object is {@link String }
     *
     */
    public String getZip() {
        return zip;
    }

    /**
     * Define el valor de la propiedad zip.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setZip(String value) {
        this.zip = value;
    }

    /**
     * Obtiene el valor de la propiedad points.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPoints() {
        return points;
    }

    /**
     * Define el valor de la propiedad points.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPoints(String value) {
        this.points = value;
    }

    /**
     * Obtiene el valor de la propiedad coordx.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCoordx() {
        return coordx;
    }

    /**
     * Define el valor de la propiedad coordx.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCoordx(String value) {
        this.coordx = value;
    }

    /**
     * Obtiene el valor de la propiedad coordy.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCoordy() {
        return coordy;
    }

    /**
     * Define el valor de la propiedad coordy.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCoordy(String value) {
        this.coordy = value;
    }

    /**
     * Obtiene el valor de la propiedad properties.
     *
     * @return possible object is {@link TypePropertiesArrayCr }
     *
     */
    public TypePropertiesArrayCr getProperties() {
        return properties;
    }

    /**
     * Define el valor de la propiedad properties.
     *
     * @param value allowed object is {@link TypePropertiesArrayCr }
     *
     */
    public void setProperties(TypePropertiesArrayCr value) {
        this.properties = value;
    }

    /**
     * Obtiene el valor de la propiedad inventories.
     *
     * @return possible object is {@link TypeInventoriesArrayCr }
     *
     */
    public TypeInventoriesArrayCr getInventories() {
        return inventories;
    }

    /**
     * Define el valor de la propiedad inventories.
     *
     * @param value allowed object is {@link TypeInventoriesArrayCr }
     *
     */
    public void setInventories(TypeInventoriesArrayCr value) {
        this.inventories = value;
    }

    /**
     * Obtiene el valor de la propiedad resourcePreferences.
     *
     * @return possible object is {@link ResourcePreferences }
     *
     */
    public ResourcePreferences getResourcePreferences() {
        return resourcePreferences;
    }

    /**
     * Define el valor de la propiedad resourcePreferences.
     *
     * @param value allowed object is {@link ResourcePreferences }
     *
     */
    public void setResourcePreferences(ResourcePreferences resourcePreferences) {
        this.resourcePreferences = resourcePreferences;
    }

}
