
package co.com.claro.ejb.mgl.ws.client.updateCaseStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * A base / value business entity used to represent a period of time, between two timepoints
 * 
 * <p>Clase Java para TimePeriod complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="TimePeriod">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="enddatetime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="startdatetime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimePeriod", propOrder = {
    "enddatetime",
    "startdatetime"
})
public class TimePeriod {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar enddatetime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startdatetime;

    /**
     * Obtiene el valor de la propiedad enddatetime.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEnddatetime() {
        return enddatetime;
    }

    /**
     * Define el valor de la propiedad enddatetime.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEnddatetime(XMLGregorianCalendar value) {
        this.enddatetime = value;
    }

    /**
     * Obtiene el valor de la propiedad startdatetime.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartdatetime() {
        return startdatetime;
    }

    /**
     * Define el valor de la propiedad startdatetime.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartdatetime(XMLGregorianCalendar value) {
        this.startdatetime = value;
    }

}
