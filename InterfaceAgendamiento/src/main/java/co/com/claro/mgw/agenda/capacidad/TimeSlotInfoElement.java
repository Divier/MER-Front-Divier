/**
 *
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 *
 */
package co.com.claro.mgw.agenda.capacidad;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Clase Java para time_slot_info_element complex type.
 *
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="time_slot_info_element"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}
 * string"/&gt;
 *         &lt;element name="label" type="{http://www.w3.org/2001/XMLSchema}
 * string"/&gt;
 *         &lt;element name="time_from" type="{http://www.w3.org/2001/XMLSchema}
 * time" minOccurs="0"/&gt;
 *         &lt;element name="time_to" type="{http://www.w3.org/2001/XMLSchema}
 * time" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "time_slot_info_element", propOrder = {
    "name",
    "label",
    "time_from",
    "time_to"
})
public class TimeSlotInfoElement {

    /**
     *
     */
    @XmlElement(required = true)
    protected String name;
    /**
     *
     */
    @XmlElement(required = true)
    protected String label;
    /**
     *
     */
    @XmlElement(name = "time_from")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar time_from;
    /**
     *
     */
    @XmlElement(name = "time_to")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar time_to;

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
     * Obtiene el valor de la propiedad label.
     *
     * @return possible object is {@link String }
     *
     */
    public String getLabel() {
        return label;
    }

    /**
     * Define el valor de la propiedad label.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setLabel(String value) {
        this.label = value;
    }

    /**
     * Obtiene el valor de la propiedad time_from.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getTime_from() {
        return time_from;
    }

    /**
     * Define el valor de la propiedad time_from.
     *
     *
     * @param time_from
     */
    public void setTime_from(XMLGregorianCalendar time_from) {
        this.time_from = time_from;
    }

    /**
     * Obtiene el valor de la propiedad time_to.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getTime_to() {
        return time_to;
    }

    public void setTime_to(XMLGregorianCalendar time_to) {
        this.time_to = time_to;
    }

    /**
     * Define el valor de la propiedad timeTo.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
}
