
package com.amx.schema.operation.getmassivefailurerequest.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.amx.co.schema.claroheaders.v1.HeaderResponse;


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
 *         &lt;element name="headerResponse" type="{http://www.amx.com/CO/Schema/ClaroHeaders/v1}HeaderResponse"/>
 *         &lt;element name="massiveFailureResponse" type="{http://www.amx.com/Schema/Operation/GetMassiveFailureRequest/V1.0}MassiveFailureResponse" maxOccurs="unbounded"/>
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
    "headerResponse",
    "massiveFailureResponse"
})
@XmlRootElement(name = "GetMassiveFailureResponse")
public class GetMassiveFailureResponse {

    @XmlElement(required = true)
    protected HeaderResponse headerResponse;
    @XmlElement(required = true)
    protected List<MassiveFailureResponse> massiveFailureResponse;

    /**
     * Gets the value of the headerResponse property.
     * 
     * @return
     *     possible object is
     *     {@link HeaderResponse }
     *     
     */
    public HeaderResponse getHeaderResponse() {
        return headerResponse;
    }

    /**
     * Sets the value of the headerResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderResponse }
     *     
     */
    public void setHeaderResponse(HeaderResponse value) {
        this.headerResponse = value;
    }

    /**
     * Gets the value of the massiveFailureResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the massiveFailureResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMassiveFailureResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MassiveFailureResponse }
     * 
     * 
     */
    public List<MassiveFailureResponse> getMassiveFailureResponse() {
        if (massiveFailureResponse == null) {
            massiveFailureResponse = new ArrayList<MassiveFailureResponse>();
        }
        return this.massiveFailureResponse;
    }

}
