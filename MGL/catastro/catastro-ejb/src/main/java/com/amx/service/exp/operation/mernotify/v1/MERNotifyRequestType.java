
package com.amx.service.exp.operation.mernotify.v1;

import com.amx.co.schema.claroheaders.v1.HeaderRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MERNotifyRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MERNotifyRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IncidentMer" type="{http://www.amx.com/Service/exp/Operation/MERNotify/v1.0}string50" minOccurs="0"/>
 *         &lt;element name="Incidentonyx" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MERNotifyRequestType", propOrder = {
    "incidentMer",
    "incidentonyx"
})
public class MERNotifyRequestType  extends HeaderRequest{

    @XmlElement(name = "IncidentMer")
    protected String incidentMer;
    @XmlElement(name = "Incidentonyx")
    protected Integer incidentonyx;

    /**
     * Gets the value of the incidentMer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncidentMer() {
        return incidentMer;
    }

    /**
     * Sets the value of the incidentMer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncidentMer(String value) {
        this.incidentMer = value;
    }

    /**
     * Gets the value of the incidentonyx property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIncidentonyx() {
        return incidentonyx;
    }

    /**
     * Sets the value of the incidentonyx property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIncidentonyx(Integer value) {
        this.incidentonyx = value;
    }

}
