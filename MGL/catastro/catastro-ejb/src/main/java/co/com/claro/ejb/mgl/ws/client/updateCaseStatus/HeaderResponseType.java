
package co.com.claro.ejb.mgl.ws.client.updateCaseStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para HeaderResponseType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="HeaderResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idESBTransaction" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idBusinessTransaction" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="additionalNode" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderResponseType", namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", propOrder = {
    "idESBTransaction",
    "idBusinessTransaction",
    "startDate",
    "endDate",
    "additionalNode"
})
public class HeaderResponseType {

    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", required = true)
    protected String idESBTransaction;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", required = true)
    protected String idBusinessTransaction;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/")
    protected Object additionalNode;

    /**
     * Obtiene el valor de la propiedad idESBTransaction.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdESBTransaction() {
        return idESBTransaction;
    }

    /**
     * Define el valor de la propiedad idESBTransaction.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdESBTransaction(String value) {
        this.idESBTransaction = value;
    }

    /**
     * Obtiene el valor de la propiedad idBusinessTransaction.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdBusinessTransaction() {
        return idBusinessTransaction;
    }

    /**
     * Define el valor de la propiedad idBusinessTransaction.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdBusinessTransaction(String value) {
        this.idBusinessTransaction = value;
    }

    /**
     * Obtiene el valor de la propiedad startDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Define el valor de la propiedad startDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Obtiene el valor de la propiedad endDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Define el valor de la propiedad endDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Obtiene el valor de la propiedad additionalNode.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getAdditionalNode() {
        return additionalNode;
    }

    /**
     * Define el valor de la propiedad additionalNode.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setAdditionalNode(Object value) {
        this.additionalNode = value;
    }

}
