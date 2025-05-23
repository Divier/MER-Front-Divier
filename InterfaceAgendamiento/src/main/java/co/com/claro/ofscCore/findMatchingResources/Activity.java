/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.ofscCore.findMatchingResources;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author bocanegravm
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "activity_element", propOrder
        = {
            "accessSchedule",
            "activityType",
            "apptNumber",
            "city",
            "country_code",
            "customerCell",
            "customerEmail",
            "customerName",
            "customerNumber",
            "customerPhone",
            "date",
            "deliveryWindowEnd",
            "deliveryWindowStart",
            "duration",
            "language",
            "latitude",
            "longitude",
            "points",
            "postalCode",
            "reminderTime",
            "resourceId",
            "serviceWindowEnd",
            "serviceWindowStart",
            "slaWindowEnd",
            "slaWindowStart",
            "stateProvince",
            "streetAddress",
            "teamResourceId",
            "timeOfBooking",
            "timeSlot",
            "timeZone",
            "XA_Red",
            "XA_WorkOrderSubtype",
            "Node"
        })
public class Activity {

    /**
     *
     */
    @XmlElement
    protected String accessSchedule;

    /**
     *
     */
    @XmlElement
    protected String activityType;

    /**
     *
     */
    @XmlElement
    protected String apptNumber;

    /**
     *
     */
    @XmlElement
    protected String city;

    /**
     *
     */
    @XmlElement
    protected String country_code;

    /**
     *
     */
    @XmlElement
    protected String customerCell;

    /**
     *
     */
    @XmlElement
    protected String customerEmail;

    /**
     *
     */
    @XmlElement
    protected String customerName;

    /**
     *
     */
    @XmlElement
    protected String customerNumber;

    /**
     *
     */
    @XmlElement
    protected String customerPhone;

    /**
     *
     */
    @XmlElement
    protected String date;

    /**
     *
     */
    @XmlElement
    protected String deliveryWindowEnd;

    /**
     *
     */
    @XmlElement
    protected String deliveryWindowStart;

    /**
     *
     */
    @XmlElement
    protected int duration;

    /**
     *
     */
    @XmlElement
    protected String language;

    /**
     *
     */
    @XmlElement
    protected double latitude;

    /**
     *
     */
    @XmlElement
    protected double longitude;

    /**
     *
     */
    @XmlElement
    protected String points;

    /**
     *
     */
    @XmlElement
    protected String postalCode;

    /**
     *
     */
    @XmlElement
    protected int reminderTime;

    /**
     *
     */
    @XmlElement
    protected String resourceId;

    /**
     *
     */
    @XmlElement
    protected String serviceWindowEnd;

    /**
     *
     */
    @XmlElement
    protected String serviceWindowStart;

    /**
     *
     */
    @XmlElement
    protected String slaWindowEnd;

    /**
     *
     */
    @XmlElement
    protected String slaWindowStart;

    /**
     *
     */
    @XmlElement
    protected String stateProvince;

    /**
     *
     */
    @XmlElement
    protected String streetAddress;

    /**
     *
     */
    @XmlElement
    protected String teamResourceId;

    /**
     *
     */
    @XmlElement
    protected String timeOfBooking;

    /**
     *
     */
    @XmlElement
    protected String timeSlot;

    /**
     *
     */
    @XmlElement
    protected String timeZone;

    /**
     *
     */
    @XmlElement
    protected String XA_Red;

    /**
     *
     */
    @XmlElement
    protected String XA_WorkOrderSubtype;
    
    /**
     *
     */
    @XmlElement
    protected String Node;



    public String getAccessSchedule() {
        return accessSchedule;
    }

    public void setAccessSchedule(String accessSchedule) {
        this.accessSchedule = accessSchedule;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getApptNumber() {
        return apptNumber;
    }

    public void setApptNumber(String apptNumber) {
        this.apptNumber = apptNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCustomerCell() {
        return customerCell;
    }

    public void setCustomerCell(String customerCell) {
        this.customerCell = customerCell;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeliveryWindowEnd() {
        return deliveryWindowEnd;
    }

    public void setDeliveryWindowEnd(String deliveryWindowEnd) {
        this.deliveryWindowEnd = deliveryWindowEnd;
    }

    public String getDeliveryWindowStart() {
        return deliveryWindowStart;
    }

    public void setDeliveryWindowStart(String deliveryWindowStart) {
        this.deliveryWindowStart = deliveryWindowStart;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(int reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getServiceWindowEnd() {
        return serviceWindowEnd;
    }

    public void setServiceWindowEnd(String serviceWindowEnd) {
        this.serviceWindowEnd = serviceWindowEnd;
    }

    public String getServiceWindowStart() {
        return serviceWindowStart;
    }

    public void setServiceWindowStart(String serviceWindowStart) {
        this.serviceWindowStart = serviceWindowStart;
    }

    public String getSlaWindowEnd() {
        return slaWindowEnd;
    }

    public void setSlaWindowEnd(String slaWindowEnd) {
        this.slaWindowEnd = slaWindowEnd;
    }

    public String getSlaWindowStart() {
        return slaWindowStart;
    }

    public void setSlaWindowStart(String slaWindowStart) {
        this.slaWindowStart = slaWindowStart;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getTeamResourceId() {
        return teamResourceId;
    }

    public void setTeamResourceId(String teamResourceId) {
        this.teamResourceId = teamResourceId;
    }

    public String getTimeOfBooking() {
        return timeOfBooking;
    }

    public void setTimeOfBooking(String timeOfBooking) {
        this.timeOfBooking = timeOfBooking;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getXA_Red() {
        return XA_Red;
    }

    public void setXA_Red(String XA_Red) {
        this.XA_Red = XA_Red;
    }

    public String getXA_WorkOrderSubtype() {
        return XA_WorkOrderSubtype;
    }

    public void setXA_WorkOrderSubtype(String XA_WorkOrderSubtype) {
        this.XA_WorkOrderSubtype = XA_WorkOrderSubtype;
    }

    public String getNode() {
        return Node;
    }

    public void setNode(String Node) {
        this.Node = Node;
    }
    
}
