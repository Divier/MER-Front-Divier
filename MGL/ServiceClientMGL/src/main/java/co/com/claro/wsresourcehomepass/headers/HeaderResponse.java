
package co.com.claro.wsresourcehomepass.headers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para headerResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="headerResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="statusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="statusDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="responseDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="traceabilityId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="headerError" type="{http://implement.wsresourcehomepass.claro.com.co/}headerError" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "headerResponse", propOrder = {
    "statusCode",
    "statusDesc",
    "responseDate",
    "traceabilityId",
    "headerError"
})
public class HeaderResponse {

    @XmlElement(required = true)
    protected String statusCode;
    @XmlElement(required = true)
    protected String statusDesc;
    @XmlElement(required = true)
    protected String responseDate;
    @XmlElement(required = true)
    protected String traceabilityId;
    protected HeaderError headerError;

    /**
     * Obtiene el valor de la propiedad statusCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Define el valor de la propiedad statusCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusCode(String value) {
        this.statusCode = value;
    }

    /**
     * Obtiene el valor de la propiedad statusDesc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusDesc() {
        return statusDesc;
    }

    /**
     * Define el valor de la propiedad statusDesc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusDesc(String value) {
        this.statusDesc = value;
    }

    /**
     * Obtiene el valor de la propiedad responseDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponseDate() {
        return responseDate;
    }

    /**
     * Define el valor de la propiedad responseDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponseDate(String value) {
        this.responseDate = value;
    }

    /**
     * Obtiene el valor de la propiedad traceabilityId.
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
     * Define el valor de la propiedad traceabilityId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTraceabilityId(String value) {
        this.traceabilityId = value;
    }

    /**
     * Obtiene el valor de la propiedad headerError.
     * 
     * @return
     *     possible object is
     *     {@link HeaderError }
     *     
     */
    public HeaderError getHeaderError() {
        return headerError;
    }

    /**
     * Define el valor de la propiedad headerError.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderError }
     *     
     */
    public void setHeaderError(HeaderError value) {
        this.headerError = value;
    }

}
