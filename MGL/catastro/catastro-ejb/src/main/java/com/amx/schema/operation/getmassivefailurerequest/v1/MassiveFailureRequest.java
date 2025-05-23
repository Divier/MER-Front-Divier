
package com.amx.schema.operation.getmassivefailurerequest.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for MassiveFailureRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MassiveFailureRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accessEquipment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="affectedService" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="caseId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ccmm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cmts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerMin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerVoice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="interactionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="locationAddressMobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mac" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="neighborhood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="node" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pe" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="plan" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="plataformId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="plataformName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sds" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="softSwitch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="technology" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MassiveFailureRequest", propOrder = {
    "accessEquipment",
    "affectedService",
    "caseId",
    "ccmm",
    "city",
    "cmts",
    "customerAccount",
    "customerAddress",
    "customerMin",
    "customerType",
    "customerVoice",
    "endDate",
    "interactionId",
    "locationAddressMobile",
    "mac",
    "neighborhood",
    "node",
    "pe",
    "plan",
    "plataformId",
    "plataformName",
    "sds",
    "serial",
    "softSwitch",
    "startDate",
    "state",
    "technology"
})
public class MassiveFailureRequest {

    protected String accessEquipment;
    protected String affectedService;
    protected String caseId;
    protected String ccmm;
    protected String city;
    protected String cmts;
    protected String customerAccount;
    protected String customerAddress;
    protected String customerMin;
    protected String customerType;
    protected String customerVoice;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    protected String interactionId;
    protected String locationAddressMobile;
    protected String mac;
    protected String neighborhood;
    protected String node;
    protected String pe;
    protected String plan;
    protected String plataformId;
    protected String plataformName;
    protected String sds;
    protected String serial;
    protected String softSwitch;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    protected String state;
    protected String technology;

    /**
     * Gets the value of the accessEquipment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessEquipment() {
        return accessEquipment;
    }

    /**
     * Sets the value of the accessEquipment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessEquipment(String value) {
        this.accessEquipment = value;
    }

    /**
     * Gets the value of the affectedService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAffectedService() {
        return affectedService;
    }

    /**
     * Sets the value of the affectedService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAffectedService(String value) {
        this.affectedService = value;
    }

    /**
     * Gets the value of the caseId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * Sets the value of the caseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaseId(String value) {
        this.caseId = value;
    }

    /**
     * Gets the value of the ccmm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcmm() {
        return ccmm;
    }

    /**
     * Sets the value of the ccmm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcmm(String value) {
        this.ccmm = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the cmts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCmts() {
        return cmts;
    }

    /**
     * Sets the value of the cmts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCmts(String value) {
        this.cmts = value;
    }

    /**
     * Gets the value of the customerAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerAccount() {
        return customerAccount;
    }

    /**
     * Sets the value of the customerAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerAccount(String value) {
        this.customerAccount = value;
    }

    /**
     * Gets the value of the customerAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Sets the value of the customerAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerAddress(String value) {
        this.customerAddress = value;
    }

    /**
     * Gets the value of the customerMin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerMin() {
        return customerMin;
    }

    /**
     * Sets the value of the customerMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerMin(String value) {
        this.customerMin = value;
    }

    /**
     * Gets the value of the customerType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerType() {
        return customerType;
    }

    /**
     * Sets the value of the customerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerType(String value) {
        this.customerType = value;
    }

    /**
     * Gets the value of the customerVoice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerVoice() {
        return customerVoice;
    }

    /**
     * Sets the value of the customerVoice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerVoice(String value) {
        this.customerVoice = value;
    }

    /**
     * Gets the value of the endDate property.
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
     * Sets the value of the endDate property.
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
     * Gets the value of the interactionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInteractionId() {
        return interactionId;
    }

    /**
     * Sets the value of the interactionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInteractionId(String value) {
        this.interactionId = value;
    }

    /**
     * Gets the value of the locationAddressMobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationAddressMobile() {
        return locationAddressMobile;
    }

    /**
     * Sets the value of the locationAddressMobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationAddressMobile(String value) {
        this.locationAddressMobile = value;
    }

    /**
     * Gets the value of the mac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMac() {
        return mac;
    }

    /**
     * Sets the value of the mac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMac(String value) {
        this.mac = value;
    }

    /**
     * Gets the value of the neighborhood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     * Sets the value of the neighborhood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNeighborhood(String value) {
        this.neighborhood = value;
    }

    /**
     * Gets the value of the node property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNode() {
        return node;
    }

    /**
     * Sets the value of the node property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNode(String value) {
        this.node = value;
    }

    /**
     * Gets the value of the pe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPe() {
        return pe;
    }

    /**
     * Sets the value of the pe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPe(String value) {
        this.pe = value;
    }

    /**
     * Gets the value of the plan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlan() {
        return plan;
    }

    /**
     * Sets the value of the plan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlan(String value) {
        this.plan = value;
    }

    /**
     * Gets the value of the plataformId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlataformId() {
        return plataformId;
    }

    /**
     * Sets the value of the plataformId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlataformId(String value) {
        this.plataformId = value;
    }

    /**
     * Gets the value of the plataformName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlataformName() {
        return plataformName;
    }

    /**
     * Sets the value of the plataformName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlataformName(String value) {
        this.plataformName = value;
    }

    /**
     * Gets the value of the sds property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSds() {
        return sds;
    }

    /**
     * Sets the value of the sds property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSds(String value) {
        this.sds = value;
    }

    /**
     * Gets the value of the serial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerial() {
        return serial;
    }

    /**
     * Sets the value of the serial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerial(String value) {
        this.serial = value;
    }

    /**
     * Gets the value of the softSwitch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftSwitch() {
        return softSwitch;
    }

    /**
     * Sets the value of the softSwitch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftSwitch(String value) {
        this.softSwitch = value;
    }

    /**
     * Gets the value of the startDate property.
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
     * Sets the value of the startDate property.
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
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the technology property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTechnology() {
        return technology;
    }

    /**
     * Sets the value of the technology property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTechnology(String value) {
        this.technology = value;
    }

}
