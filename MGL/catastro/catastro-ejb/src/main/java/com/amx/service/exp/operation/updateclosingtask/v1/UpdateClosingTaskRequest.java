
package com.amx.service.exp.operation.updateclosingtask.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdateClosingTaskRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateClosingTaskRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="iIncidentIdOTH" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="iCode1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="vchAnotaciones" type="{http://www.amx.com/Service/exp/Operation/updateClosingTask/v1.0}string3000" minOccurs="0"/>
 *         &lt;element name="iEventoId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="chuserid" type="{http://www.amx.com/Service/exp/Operation/updateClosingTask/v1.0}string10" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateClosingTaskRequest", propOrder = {
    "iIncidentIdOTH",
    "iCode1",
    "vchAnotaciones",
    "iEventoId",
    "chuserid"
})
public class UpdateClosingTaskRequest{

    protected Integer iIncidentIdOTH;
    protected Integer iCode1;
    protected String vchAnotaciones;
    protected Integer iEventoId;
    protected String chuserid;

    
    /**
     * Gets the value of the iIncidentIdOTH property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIIncidentIdOTH() {
        return iIncidentIdOTH;
    }

    /**
     * Sets the value of the iIncidentIdOTH property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIIncidentIdOTH(Integer value) {
        this.iIncidentIdOTH = value;
    }

    /**
     * Gets the value of the iCode1 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getICode1() {
        return iCode1;
    }

    /**
     * Sets the value of the iCode1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setICode1(Integer value) {
        this.iCode1 = value;
    }

    /**
     * Gets the value of the vchAnotaciones property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVchAnotaciones() {
        return vchAnotaciones;
    }

    /**
     * Sets the value of the vchAnotaciones property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVchAnotaciones(String value) {
        this.vchAnotaciones = value;
    }

    /**
     * Gets the value of the iEventoId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIEventoId() {
        return iEventoId;
    }

    /**
     * Sets the value of the iEventoId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIEventoId(Integer value) {
        this.iEventoId = value;
    }

    /**
     * Gets the value of the chuserid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChuserid() {
        return chuserid;
    }

    /**
     * Sets the value of the chuserid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChuserid(String value) {
        this.chuserid = value;
    }

}
