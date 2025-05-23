/**
 *
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 *
 */
package co.com.claro.mgw.agenda.shareddata;

import co.com.claro.mgw.agenda.capacidad.ActivityFieldElement;
import co.com.claro.mgw.agenda.capacidad.InventoryTypeElement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Clase Java para capacity_request_element complex type.
 *
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="capacity_request_element"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="user" type="{urn:toa:capacity}user_element"/&gt;
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}date"
 * maxOccurs="unbounded"/&gt;
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}
 * string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="calculate_duration" type=
 * "{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="calculate_travel_time"
 * type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="calculate_work_skill"
 * type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="return_time_slot_info"
 * type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="determine_location_by_work_zone"
 * type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="dont_aggregate_results"
 * type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="min_time_to_end_of_time_slot"
 * type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="default_duration"
 * type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="time_slot"
 * type="{http://www.w3.org/2001/XMLSchema}string"
 * maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="work_skill"
 * type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"
 * minOccurs="0"/&gt;
 *         &lt;element name="activity_field" type="{urn:toa:capacity}
 * activity_field_element" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "capacity_request_element", propOrder = {
    "user",
    "date",
    "location",
    "calculate_duration",
    "calculate_travel_time",
    "calculate_work_skill",
    "return_time_slot_info",
    "determine_location_by_work_zone",
    "dont_aggregate_results",
    "min_time_to_end_of_time_slot",
    "default_duration",
    "time_slot",
    "work_skill",
    "activity_field",
    //CAMPOS NUEVOS PARA PETICION
    "appt_number",
    "document_id",
    "address_id",
    "inventories",
    //CAMPOS NUEVOS PARA PETICION
    "infoOrderAct"
})
public class MgwTypeCapacityRequestElement {

    /**
     *
     */
    @XmlElement(required = true)
    protected MgwUserElement user;
    /**
     *
     */
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected List<String> date;
    /**
     *
     */
    protected List<String> location;
    /**
     *
     */
    @XmlElement(name = "calculate_duration")
    protected Boolean calculate_duration;
    /**
     *
     */
    @XmlElement(name = "calculate_travel_time")
    protected Boolean calculate_travel_time;
    /**
     *
     */
    @XmlElement(name = "calculate_work_skill")
    protected Boolean calculate_work_skill;
    /**
     *
     */
    @XmlElement(name = "return_time_slot_info")
    protected Boolean return_time_slot_info;
    /**
     *
     */
    @XmlElement(name = "determine_location_by_work_zone")
    protected Boolean determine_location_by_work_zone;
    /**
     *
     */
    @XmlElement(name = "dont_aggregate_results")
    protected Boolean dont_aggregate_results;
    /**
     *
     */
    @XmlElement(name = "min_time_to_end_of_time_slot")
    protected Integer min_time_to_end_of_time_slot;
    /**
     *
     */
    @XmlElement(name = "default_duration")
    protected Integer default_duration;
    /**
     *
     */
    @XmlElement(name = "time_slot")
    protected List<String> time_slot;
    /**
     *
     */
    @XmlElement(name = "work_skill")
    protected List<String> work_skill;
    /**
     *
     */
    protected List<ActivityFieldElement> activity_field;
    /**
     *
     */
    @XmlElement(name = "appt_number")
    protected String appt_number;
    /**
     *
     */
    @XmlElement(name = "document_id")
    protected Integer document_id;
    /**
     *
     */
    @XmlElement(name = "address_id")
    protected Integer address_id;
    /**
     *
     */
    @XmlElement(name = "inventories")
    protected List<InventoryTypeElement> inventories;
    /**
     *
     * Propiedades para el calculo de las actividades de la orden
     *
     */
    @XmlElement(name = "infoOrderAct")
    protected List<InfoOrdenAct> infoOrderAct;

    /**
     * Obtiene el valor de la propiedad user.
     *
     * @return possible object is {@link MgwUserElement }
     *
     */
    public MgwUserElement getUser() {
        return user;
    }

    /**
     * Define el valor de la propiedad user.
     *
     * @param value allowed object is {@link MgwUserElement }
     *
     */
    public void setUser(MgwUserElement value) {
        this.user = value;
    }

    /**
     * Gets the value of the date property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the date property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDate().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XMLGregorianCalendar }
     *
     *
     * @return
     */
    public List<String> getDate() {
        if (date == null) {
            date = new ArrayList();
        }
        return this.date;
    }

    /**
     * Gets the value of the location property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the location property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocation().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     *
     *
     * @return
     */
    public List<String> getLocation() {
        if (location == null) {
            location = new ArrayList();
        }
        return this.location;
    }

    /**
     * Obtiene el valor de la propiedad calculate_duration.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean getCalculate_duration() {
        return calculate_duration;
    }

    /**
     * Define el valor de la propiedad calculate_duration.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setCalculate_duration(Boolean calculate_duration) {
        this.calculate_duration = calculate_duration;
    }

    /**
     * Obtiene el valor de la propiedad calculate_travel_time.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean getCalculate_travel_time() {
        return calculate_travel_time;
    }

    /**
     * Define el valor de la propiedad calculate_travel_time.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setCalculate_travel_time(Boolean calculate_travel_time) {
        this.calculate_travel_time = calculate_travel_time;
    }

    /**
     * Gets the value of the timeSlot property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the timeSlot property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTimeSlot().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     *
     *
     * @return
     */
    public List<String> getTime_slot() {
        if (time_slot == null) {
            time_slot = new ArrayList();
        }
        return this.time_slot;
    }

    /**
     * Gets the value of the workSkill property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the workSkill property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWorkSkill().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     *
     *
     * @return
     */
    public List<String> getWork_skill() {
        if (work_skill == null) {
            work_skill = new ArrayList();
        }
        return this.work_skill;
    }

    /**
     * Gets the value of the activityField property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the activityField property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActivityField().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActivityFieldElement }
     *
     *
     * @return
     */
    public List<ActivityFieldElement> getActivity_field() {
        if (activity_field == null) {
            activity_field = new ArrayList();
        }
        return this.activity_field;
    }

    /**
     *
     * @param date
     */
    public void setDate(List<String> date) {
        this.date = date;
    }

    /**
     *
     * @param location
     */
    public void setLocation(List<String> location) {
        this.location = location;
    }

    /**
     *
     * @param activityField
     */
    @XmlElement(name = "activity_field")
    public void setActivityField(List<ActivityFieldElement> activityField) {
        this.activity_field = activityField;
    }

    /**
     *
     * @return String
     */
    public String getAppt_number() {
        return appt_number;
    }

    /**
     *
     * @param appt_number
     */
    public void setAppt_number(String appt_number) {
        this.appt_number = appt_number;
    }

    /**
     *
     * @return Integer
     */
    public Integer getDocument_id() {
        return document_id;
    }

    /**
     *
     * @param document_id
     */
    public void setDocument_id(Integer document_id) {
        this.document_id = document_id;
    }

    /**
     *
     * @return Integer
     */
    public Integer getAddress_id() {
        return address_id;
    }

    /**
     *
     * @param address_id
     */
    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }

    /**
     *
     * @return List InventoryTypeElement
     */
    public List<InventoryTypeElement> getInventories() {
        return inventories;
    }

    /**
     *
     * @param inventories
     */
    public void setInventories(List<InventoryTypeElement> inventories) {
        this.inventories = inventories;
    }

    /**
     *
     * @return List InventoryTypeElement
     */
    public List<InventoryTypeElement> getInventorys() {
        return inventories;
    }

    /**
     *
     * @param inventories
     */
    public void setInventorys(List<InventoryTypeElement> inventories) {
        this.inventories = inventories;
    }

    /**
     *
     * @return List InfoOrdenAct
     */
    public List<InfoOrdenAct> getInfoOrderAct() {
        return infoOrderAct;
    }

    /**
     *
     * @param infoOrderAct
     */
    public void setInfoOrderAct(List<InfoOrdenAct> infoOrderAct) {
        this.infoOrderAct = infoOrderAct;
    }

    /**
     *
     * @return Boolean calculate_work_skill
     */
    public Boolean getCalculate_work_skill() {
        return calculate_work_skill;
    }

    /**
     *
     * @param calculate_work_skill
     */
    public void setCalculate_work_skill(Boolean calculate_work_skill) {
        this.calculate_work_skill = calculate_work_skill;
    }

    /**
     *
     * @return Boolean getReturn_time_slot_info
     */
    public Boolean getReturn_time_slot_info() {
        return return_time_slot_info;
    }

    /**
     *
     * @param return_time_slot_info
     */
    public void setReturn_time_slot_info(Boolean return_time_slot_info) {
        this.return_time_slot_info = return_time_slot_info;
    }

    /**
     *
     * @return Boolean getDetermine_location_by_work_zone
     */
    public Boolean getDetermine_location_by_work_zone() {
        return determine_location_by_work_zone;
    }

    /**
     *
     * @param determine_location_by_work_zone
     */
    public void setDetermine_location_by_work_zone(Boolean determine_location_by_work_zone) {
        this.determine_location_by_work_zone = determine_location_by_work_zone;
    }

    /**
     *
     * @return Boolean getDont_aggregate_results
     */
    public Boolean getDont_aggregate_results() {
        return dont_aggregate_results;
    }

    /**
     *
     * @param dont_aggregate_results
     */
    public void setDont_aggregate_results(Boolean dont_aggregate_results) {
        this.dont_aggregate_results = dont_aggregate_results;
    }

    /**
     *
     * @return Integer getMin_time_to_end_of_time_slot
     */
    public Integer getMin_time_to_end_of_time_slot() {
        return min_time_to_end_of_time_slot;
    }

    /**
     *
     * @param min_time_to_end_of_time_slot
     */
    public void setMin_time_to_end_of_time_slot(Integer min_time_to_end_of_time_slot) {
        this.min_time_to_end_of_time_slot = min_time_to_end_of_time_slot;
    }

}
