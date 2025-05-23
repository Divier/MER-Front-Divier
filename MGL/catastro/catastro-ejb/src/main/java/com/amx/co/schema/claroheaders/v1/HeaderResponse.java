
package com.amx.co.schema.claroheaders.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for HeaderResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HeaderResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="responseDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="traceabilityId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderResponse", propOrder = {
    "statusCode",
    "statusDesc",
    "responseDate",
    "traceabilityId",
    "headerError"
})
public class HeaderResponse {

    @XmlElement(required = false)
    protected String statusCode;
    
    @XmlElement(required = false)
    protected String statusDesc;
    
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar responseDate;
    
    @XmlElement(required = true)
    protected String traceabilityId;
    
    @XmlElement(required = false)
    protected HeaderError headerError;
    
    
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
    
    /**
     * Gets the value of the responseDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getResponseDate() {
        return responseDate;
    }

    /**
     * Sets the value of the responseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setResponseDate(XMLGregorianCalendar value) {
        this.responseDate = value;
    }

    /**
     * Gets the value of the traceabilityId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTraceabilityId() {
        return traceabilityId;
    }

    /**
     * Sets the value of the traceabilityId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTraceabilityId(String value) {
        this.traceabilityId = value;
    }

    public HeaderError getHeaderError() {
        return headerError;
    }

    public void setHeaderError(HeaderError headerError) {
        this.headerError = headerError;
    }

    
    
}
