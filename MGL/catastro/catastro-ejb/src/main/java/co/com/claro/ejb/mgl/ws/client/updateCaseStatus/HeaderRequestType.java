
package co.com.claro.ejb.mgl.ws.client.updateCaseStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para HeaderRequestType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="HeaderRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="channel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idApplication" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userApplication" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userSession" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idESBTransaction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idBusinessTransaction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
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
@XmlType(name = "HeaderRequestType", namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", propOrder = {
    "channel",
    "idApplication",
    "userApplication",
    "userSession",
    "idESBTransaction",
    "idBusinessTransaction",
    "startDate",
    "additionalNode"
})
public class HeaderRequestType {

    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/")
    protected String channel;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/")
    protected String idApplication;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/")
    protected String userApplication;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/")
    protected String userSession;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/")
    protected String idESBTransaction;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/")
    protected String idBusinessTransaction;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/")
    protected Object additionalNode;

    /**
     * Obtiene el valor de la propiedad channel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Define el valor de la propiedad channel.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannel(String value) {
        this.channel = value;
    }

    /**
     * Obtiene el valor de la propiedad idApplication.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdApplication() {
        return idApplication;
    }

    /**
     * Define el valor de la propiedad idApplication.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdApplication(String value) {
        this.idApplication = value;
    }

    /**
     * Obtiene el valor de la propiedad userApplication.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserApplication() {
        return userApplication;
    }

    /**
     * Define el valor de la propiedad userApplication.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserApplication(String value) {
        this.userApplication = value;
    }

    /**
     * Obtiene el valor de la propiedad userSession.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserSession() {
        return userSession;
    }

    /**
     * Define el valor de la propiedad userSession.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserSession(String value) {
        this.userSession = value;
    }

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
