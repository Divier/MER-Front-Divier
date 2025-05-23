
package com.amx.service.exp.operation.mernotify.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MERNotifyResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MERNotifyResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="icodOperacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="vchDescOperacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MERNotifyResponseType", propOrder = {
    "icodOperacion",
    "vchDescOperacion"
})
public class MERNotifyResponseType{

    @XmlElement(required = true)
    protected String icodOperacion;
    @XmlElement(required = true)
    protected String vchDescOperacion;
    

    /**
     * Gets the value of the icodOperacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcodOperacion() {
        return icodOperacion;
    }

    /**
     * Sets the value of the icodOperacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcodOperacion(String value) {
        this.icodOperacion = value;
    }

    /**
     * Gets the value of the vchDescOperacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVchDescOperacion() {
        return vchDescOperacion;
    }

    /**
     * Sets the value of the vchDescOperacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVchDescOperacion(String value) {
        this.vchDescOperacion = value;
    }

}
