/**
 *
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 *
 */
package co.com.claro.mgw.agenda.shareddata;

import co.com.claro.mgw.agenda.capacidad.CapacityElement;
import co.com.claro.mgw.agenda.capacidad.CapacityRestrictionElement;
import co.com.claro.mgw.agenda.capacidad.TimeSlotInfoElement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Clase Java para capacity_response_element complex type.
 *
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="capacity_response_element"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="activity_duration"
 * type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="activity_travel_time"
 * type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="capacity"
 * type="{urn:toa:capacity}capacity_element" maxOccurs="unbounded" minOccurs="0"
 * /&gt;
 *         &lt;element name="time_slot_info"
 * type="{urn:toa:capacity}time_slot_info_element" maxOccurs="unbounded"
 * minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "capacity_response_element", propOrder = {
    "activity_duration",
    "activity_travel_time",
    "capacity",
    "time_slot_info",
    "report",
    "restriction"
})
public class MgwTypeCapacityResponseElement {

    /**
     *
     */
    public MgwTypeCapacityResponseElement() {
    }

    /**
     *
     * @param activityDuration
     * @param activityTravelTime
     * @param capacity
     * @param timeSlotInfo
     * @param report
     * @param restriction
     */
    public MgwTypeCapacityResponseElement(Long activityDuration, Long activityTravelTime,
            List<CapacityElement> capacity, List<TimeSlotInfoElement> timeSlotInfo,
            MgwTypeErrorReportElement report,
            List<CapacityRestrictionElement> restriction) {
        this.activity_duration = activityDuration;
        this.activity_travel_time = activityTravelTime;
        this.capacity = capacity;
        this.time_slot_info = timeSlotInfo;
        this.report = report;
        this.restriction = restriction;
    }
    /**
     *
     */
    @XmlElement(name = "activity_duration")
    protected Long activity_duration;
    /**
     *
     */
    @XmlElement(name = "activity_travel_time")
    protected Long activity_travel_time;
    /**
     *
     */
    @XmlElement(name = "capacity")
    protected List<CapacityElement> capacity;
    /**
     *
     */
    @XmlElement(name = "time_slot_info")
    protected List<TimeSlotInfoElement> time_slot_info;
    /**
     *
     */
    @XmlElement(name = "report", required = false)
    protected MgwTypeErrorReportElement report;
    /**
     *
     */
    @XmlElement(name = "restriction", required = false)
    protected List<CapacityRestrictionElement> restriction;

    /**
     * Obtiene el valor de la propiedad activityDuration.
     *
     * @return possible object is {@link Long }
     *
     */
    public Long getActivity_duration() {
        return activity_duration;
    }

    /**
     * Define el valor de la propiedad activityDuration.
     *
     * @param value allowed object is {@link Long }
     *
     */
    public void setActivity_duration(Long activity_duration) {
        this.activity_duration = activity_duration;
    }

    /**
     * Obtiene el valor de la propiedad activityTravelTime.
     *
     * @return possible object is {@link Long }
     *
     */
    public Long getActivity_travel_time() {
        return activity_travel_time;
    }

    /**
     * Define el valor de la propiedad activityTravelTime.
     *
     * @param value allowed object is {@link Long }
     *
     */
    public void setActivity_travel_time(Long activity_travel_time) {
        this.activity_travel_time = activity_travel_time;
    }

    /**
     * Gets the value of the capacity property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the capacity property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCapacity().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CapacityElement }
     *
     *
     * @return
     */
    public List<CapacityElement> getCapacity() {
        if (capacity == null) {
            capacity = new ArrayList();
        }
        return this.capacity;
    }

    /**
     * Gets the value of the timeSlotInfo property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the timeSlotInfo property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTimeSlotInfo().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TimeSlotInfoElement }
     *
     *
     * @return
     */
    public List<TimeSlotInfoElement> getTime_slot_info() {
        if (time_slot_info == null) {
            time_slot_info = new ArrayList();
        }
        return this.time_slot_info;
    }

    /**
     *
     * @param capacity
     */
    public void setCapacity(List<CapacityElement> capacity) {
        this.capacity = capacity;
    }

    /**
     *
     * @param timeSlotInfo
     */
    public void setTime_slot_info(List<TimeSlotInfoElement> time_slot_info) {
        this.time_slot_info = time_slot_info;
    }

    /**
     *
     * @return MgwTypeErrorReportElement
     */
    public MgwTypeErrorReportElement getReport() {
        return report;
    }

    /**
     *
     * @param report
     */
    public void setReport(MgwTypeErrorReportElement report) {
        this.report = report;
    }

    /**
     *
     * @return List CapacityRestrictionElement
     */
    public List<CapacityRestrictionElement> getRestriction() {
        return restriction;
    }

    /**
     *
     * @param restriction
     */
    public void setRestriction(List<CapacityRestrictionElement> restriction) {
        this.restriction = restriction;
    }

}
