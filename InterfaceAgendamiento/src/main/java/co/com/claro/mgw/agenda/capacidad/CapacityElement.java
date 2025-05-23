/**
 *
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 *
 */
package co.com.claro.mgw.agenda.capacidad;

import co.com.claro.mgw.agenda.shareddata.MgwTypeErrorReportElement;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Clase Java para capacity_element complex type.
 *
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="capacity_element"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}date"
 * /&gt;
 *         &lt;element name="time_slot" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="work_skill"
 * type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="quota" type="{http://www.w3.org/2001/XMLSchema}
 * long"/&gt;
 *         &lt;element name="available" type="{http://www.w3.org/2001/XMLSchema}
 * long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "capacity_element", propOrder = {
    "location",
    "date",
    "time_slot",
    "work_skill",
    "quota",
    "available",
    "bucket",
    "report",
    "restriction"
})
public class CapacityElement {

    /**
     *
     */
    protected String location;
    /**
     *
     */
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar date;
    /**
     *
     */
    @XmlElement(name = "time_slot")
    protected String time_slot;
    /**
     *
     */
    @XmlElement(name = "work_skill")
    protected String work_skill;
    /**
     *
     */
    @XmlElement(name = "quota")
    protected long quota;
    /**
     *
     */
    @XmlElement(name = "available")
    protected long available;
    /**
     *
     */
    @XmlElement(name = "bucket")
    protected String bucket;
    /**
     *
     */
    @XmlElement(name = "report")
    protected MgwTypeErrorReportElement report;
    /**
     *
     */
    @XmlElement(name = "restriction")
    protected List<CapacityRestrictionElement> restriction;

    /**
     * Obtiene el valor de la propiedad location.
     *
     * @return possible object is {@link String }
     *
     */
    public String getLocation() {
        return location;
    }

    /**
     * Define el valor de la propiedad location.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Obtiene el valor de la propiedad date.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Define el valor de la propiedad date.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
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
     * Obtiene el valor de la propiedad work_skill.
     *
     * @return possible object is {@link String }
     *
     */
    public String getWork_skill() {
        return work_skill;
    }

    /**
     * Define el valor de la propiedad workSkill.
     *
     *
     * @param work_skill
     */
    public void setWork_skill(String work_skill) {
        this.work_skill = work_skill;
    }

    /**
     * Obtiene el valor de la propiedad quota.
     *
     * @return
     */
    public long getQuota() {
        return quota;
    }

    /**
     * Define el valor de la propiedad quota.
     *
     * @param value
     */
    public void setQuota(long value) {
        this.quota = value;
    }

    /**
     * Obtiene el valor de la propiedad available.
     *
     * @return
     */
    public long getAvailable() {
        return available;
    }

    /**
     * Define el valor de la propiedad available.
     *
     * @param value
     */
    public void setAvailable(long value) {
        this.available = value;
    }
    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    /**
     *
     * @return
     */
    public MgwTypeErrorReportElement getReport() {
        return report;
    }

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
