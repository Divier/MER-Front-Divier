
package co.com.claro.ejb.mgl.ws.client.updateCaseStatus;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para EricssonFaultMessageType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="EricssonFaultMessageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="enterpriseServiceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="enterpriseServiceOperationName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="originSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idAudit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idESBTransaction" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idBusinessTransaction" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="errorCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="errorLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="faultSource" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EricssonFaultMessageType", namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", propOrder = {
    "enterpriseServiceName",
    "enterpriseServiceOperationName",
    "originSystem",
    "idAudit",
    "idESBTransaction",
    "idBusinessTransaction",
    "errorCode",
    "errorMessage",
    "errorLocation",
    "date",
    "faultSource"
})
public class EricssonFaultMessageType {

    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", required = true, nillable = true)
    protected String enterpriseServiceName;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", required = true, nillable = true)
    protected String enterpriseServiceOperationName;
    @XmlElementRef(name = "originSystem", namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", type = JAXBElement.class)
    protected JAXBElement<String> originSystem;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/")
    protected String idAudit;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", required = true)
    protected String idESBTransaction;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", required = true)
    protected String idBusinessTransaction;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", required = true)
    protected String errorCode;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", required = true)
    protected String errorMessage;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/")
    protected String errorLocation;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/")
    protected Object faultSource;

    /**
     * Obtiene el valor de la propiedad enterpriseServiceName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnterpriseServiceName() {
        return enterpriseServiceName;
    }

    /**
     * Define el valor de la propiedad enterpriseServiceName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnterpriseServiceName(String value) {
        this.enterpriseServiceName = value;
    }

    /**
     * Obtiene el valor de la propiedad enterpriseServiceOperationName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnterpriseServiceOperationName() {
        return enterpriseServiceOperationName;
    }

    /**
     * Define el valor de la propiedad enterpriseServiceOperationName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnterpriseServiceOperationName(String value) {
        this.enterpriseServiceOperationName = value;
    }

    /**
     * Obtiene el valor de la propiedad originSystem.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOriginSystem() {
        return originSystem;
    }

    /**
     * Define el valor de la propiedad originSystem.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOriginSystem(JAXBElement<String> value) {
        this.originSystem = value;
    }

    /**
     * Obtiene el valor de la propiedad idAudit.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAudit() {
        return idAudit;
    }

    /**
     * Define el valor de la propiedad idAudit.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAudit(String value) {
        this.idAudit = value;
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
     * Obtiene el valor de la propiedad errorCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Define el valor de la propiedad errorCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorCode(String value) {
        this.errorCode = value;
    }

    /**
     * Obtiene el valor de la propiedad errorMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Define el valor de la propiedad errorMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Obtiene el valor de la propiedad errorLocation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorLocation() {
        return errorLocation;
    }

    /**
     * Define el valor de la propiedad errorLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorLocation(String value) {
        this.errorLocation = value;
    }

    /**
     * Obtiene el valor de la propiedad date.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Define el valor de la propiedad date.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Obtiene el valor de la propiedad faultSource.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getFaultSource() {
        return faultSource;
    }

    /**
     * Define el valor de la propiedad faultSource.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setFaultSource(Object value) {
        this.faultSource = value;
    }

}
