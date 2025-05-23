
package com.amx.schema.operation.getmassivefailurerequest.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for MassiveFailureResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MassiveFailureResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="advancedNotes" type="{http://www.amx.com/Schema/Operation/GetMassiveFailureRequest/V1.0}noteVO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="affectedCI" type="{http://www.amx.com/Schema/Operation/GetMassiveFailureRequest/V1.0}ciVO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="affectedPlataforms" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="affectedServices" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="affectedTecnicalLocations" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estimatedEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="estimatedSolutionTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="failureCause" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="failureLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interactionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reporterGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reporterUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="script" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="throubleTickectSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ticketCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ticketDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ticketEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ticketId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ticketPriority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ticketSolution" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ticketStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ticketStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ticketSymptom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MassiveFailureResponse", propOrder = {
    "advancedNotes",
    "affectedCI",
    "affectedPlataforms",
    "affectedServices",
    "affectedTecnicalLocations",
    "estimatedEndDate",
    "estimatedSolutionTime",
    "failureCause",
    "failureLocation",
    "interactionId",
    "reporterGroup",
    "reporterUser",
    "script",
    "throubleTickectSystem",
    "ticketCategory",
    "ticketDescription",
    "ticketEndDate",
    "ticketId",
    "ticketPriority",
    "ticketSolution",
    "ticketStartDate",
    "ticketStatus",
    "ticketSymptom"
})
public class MassiveFailureResponse {

    @XmlElement(nillable = true)
    protected List<NoteVO> advancedNotes;
    @XmlElement(nillable = true)
    protected List<CiVO> affectedCI;
    protected String affectedPlataforms;
    protected String affectedServices;
    protected String affectedTecnicalLocations;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar estimatedEndDate;
    protected String estimatedSolutionTime;
    protected String failureCause;
    protected String failureLocation;
    protected String interactionId;
    protected String reporterGroup;
    protected String reporterUser;
    protected String script;
    protected String throubleTickectSystem;
    protected String ticketCategory;
    protected String ticketDescription;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar ticketEndDate;
    protected String ticketId;
    protected String ticketPriority;
    protected String ticketSolution;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar ticketStartDate;
    protected String ticketStatus;
    protected String ticketSymptom;

    /**
     * Gets the value of the advancedNotes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the advancedNotes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdvancedNotes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NoteVO }
     * 
     * 
     */
    public List<NoteVO> getAdvancedNotes() {
        if (advancedNotes == null) {
            advancedNotes = new ArrayList<NoteVO>();
        }
        return this.advancedNotes;
    }

    /**
     * Gets the value of the affectedCI property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the affectedCI property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAffectedCI().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CiVO }
     * 
     * 
     */
    public List<CiVO> getAffectedCI() {
        if (affectedCI == null) {
            affectedCI = new ArrayList<CiVO>();
        }
        return this.affectedCI;
    }

    /**
     * Gets the value of the affectedPlataforms property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAffectedPlataforms() {
        return affectedPlataforms;
    }

    /**
     * Sets the value of the affectedPlataforms property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAffectedPlataforms(String value) {
        this.affectedPlataforms = value;
    }

    /**
     * Gets the value of the affectedServices property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAffectedServices() {
        return affectedServices;
    }

    /**
     * Sets the value of the affectedServices property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAffectedServices(String value) {
        this.affectedServices = value;
    }

    /**
     * Gets the value of the affectedTecnicalLocations property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAffectedTecnicalLocations() {
        return affectedTecnicalLocations;
    }

    /**
     * Sets the value of the affectedTecnicalLocations property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAffectedTecnicalLocations(String value) {
        this.affectedTecnicalLocations = value;
    }

    /**
     * Gets the value of the estimatedEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEstimatedEndDate() {
        return estimatedEndDate;
    }

    /**
     * Sets the value of the estimatedEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEstimatedEndDate(XMLGregorianCalendar value) {
        this.estimatedEndDate = value;
    }

    /**
     * Gets the value of the estimatedSolutionTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstimatedSolutionTime() {
        return estimatedSolutionTime;
    }

    /**
     * Sets the value of the estimatedSolutionTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstimatedSolutionTime(String value) {
        this.estimatedSolutionTime = value;
    }

    /**
     * Gets the value of the failureCause property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFailureCause() {
        return failureCause;
    }

    /**
     * Sets the value of the failureCause property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFailureCause(String value) {
        this.failureCause = value;
    }

    /**
     * Gets the value of the failureLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFailureLocation() {
        return failureLocation;
    }

    /**
     * Sets the value of the failureLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFailureLocation(String value) {
        this.failureLocation = value;
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
     * Gets the value of the reporterGroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReporterGroup() {
        return reporterGroup;
    }

    /**
     * Sets the value of the reporterGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReporterGroup(String value) {
        this.reporterGroup = value;
    }

    /**
     * Gets the value of the reporterUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReporterUser() {
        return reporterUser;
    }

    /**
     * Sets the value of the reporterUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReporterUser(String value) {
        this.reporterUser = value;
    }

    /**
     * Gets the value of the script property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScript() {
        return script;
    }

    /**
     * Sets the value of the script property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScript(String value) {
        this.script = value;
    }

    /**
     * Gets the value of the throubleTickectSystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThroubleTickectSystem() {
        return throubleTickectSystem;
    }

    /**
     * Sets the value of the throubleTickectSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThroubleTickectSystem(String value) {
        this.throubleTickectSystem = value;
    }

    /**
     * Gets the value of the ticketCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicketCategory() {
        return ticketCategory;
    }

    /**
     * Sets the value of the ticketCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicketCategory(String value) {
        this.ticketCategory = value;
    }

    /**
     * Gets the value of the ticketDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicketDescription() {
        return ticketDescription;
    }

    /**
     * Sets the value of the ticketDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicketDescription(String value) {
        this.ticketDescription = value;
    }

    /**
     * Gets the value of the ticketEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTicketEndDate() {
        return ticketEndDate;
    }

    /**
     * Sets the value of the ticketEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTicketEndDate(XMLGregorianCalendar value) {
        this.ticketEndDate = value;
    }

    /**
     * Gets the value of the ticketId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicketId() {
        return ticketId;
    }

    /**
     * Sets the value of the ticketId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicketId(String value) {
        this.ticketId = value;
    }

    /**
     * Gets the value of the ticketPriority property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicketPriority() {
        return ticketPriority;
    }

    /**
     * Sets the value of the ticketPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicketPriority(String value) {
        this.ticketPriority = value;
    }

    /**
     * Gets the value of the ticketSolution property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicketSolution() {
        return ticketSolution;
    }

    /**
     * Sets the value of the ticketSolution property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicketSolution(String value) {
        this.ticketSolution = value;
    }

    /**
     * Gets the value of the ticketStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTicketStartDate() {
        return ticketStartDate;
    }

    /**
     * Sets the value of the ticketStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTicketStartDate(XMLGregorianCalendar value) {
        this.ticketStartDate = value;
    }

    /**
     * Gets the value of the ticketStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicketStatus() {
        return ticketStatus;
    }

    /**
     * Sets the value of the ticketStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicketStatus(String value) {
        this.ticketStatus = value;
    }

    /**
     * Gets the value of the ticketSymptom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicketSymptom() {
        return ticketSymptom;
    }

    /**
     * Sets the value of the ticketSymptom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicketSymptom(String value) {
        this.ticketSymptom = value;
    }

}
