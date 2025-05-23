
package com.amx.schema.operation.getmassivefailurerequest.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.amx.co.schema.claroheaders.v1.HeaderRequest;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="headerRequest" type="{http://www.amx.com/CO/Schema/ClaroHeaders/v1}HeaderRequest"/>
 *         &lt;element name="massiveFailureRequest" type="{http://www.amx.com/Schema/Operation/GetMassiveFailureRequest/V1.0}MassiveFailureRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "headerRequest",
    "massiveFailureRequest"
})
@XmlRootElement(name = "GetMassiveFailureRequest")
public class GetMassiveFailureRequest {

    @XmlElement(required = true)
    protected HeaderRequest headerRequest;
    @XmlElement(required = true)
    protected MassiveFailureRequest massiveFailureRequest;

    /**
     * Gets the value of the headerRequest property.
     * 
     * @return
     *     possible object is
     *     {@link HeaderRequest }
     *     
     */
    public HeaderRequest getHeaderRequest() {
        return headerRequest;
    }

    /**
     * Sets the value of the headerRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderRequest }
     *     
     */
    public void setHeaderRequest(HeaderRequest value) {
        this.headerRequest = value;
    }

    /**
     * Gets the value of the massiveFailureRequest property.
     * 
     * @return
     *     possible object is
     *     {@link MassiveFailureRequest }
     *     
     */
    public MassiveFailureRequest getMassiveFailureRequest() {
        return massiveFailureRequest;
    }

    /**
     * Sets the value of the massiveFailureRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link MassiveFailureRequest }
     *     
     */
    public void setMassiveFailureRequest(MassiveFailureRequest value) {
        this.massiveFailureRequest = value;
    }

}
