/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.ofscCore.findMatchingResources;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement(name = "resource")
public class Resource {

    @XmlElement
    protected String dateFormat;

    @XmlElement
    protected int durationStatisticsInitialPeriod;

    @XmlElement
    protected double durationStatisticsInitialRatio;

    @XmlElement
    protected String email;

    @XmlElement
    protected String language;

    @XmlElement
    protected String name;

    @XmlElement
    protected String parentResourceId;

    @XmlElement
    protected String phone;

    @XmlElement
    protected String resourceId;

    @XmlElement
    protected String resourceInternalId;

    @XmlElement
    protected String resourceType;

    @XmlElement
    protected String languageISO;

    @XmlElement
    protected String status;
    
    @XmlElement
    protected String XR_IdAliado;
    

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public int getDurationStatisticsInitialPeriod() {
        return durationStatisticsInitialPeriod;
    }

    public void setDurationStatisticsInitialPeriod(int durationStatisticsInitialPeriod) {
        this.durationStatisticsInitialPeriod = durationStatisticsInitialPeriod;
    }

    public double getDurationStatisticsInitialRatio() {
        return durationStatisticsInitialRatio;
    }

    public void setDurationStatisticsInitialRatio(double durationStatisticsInitialRatio) {
        this.durationStatisticsInitialRatio = durationStatisticsInitialRatio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentResourceId() {
        return parentResourceId;
    }

    public void setParentResourceId(String parentResourceId) {
        this.parentResourceId = parentResourceId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceInternalId() {
        return resourceInternalId;
    }

    public void setResourceInternalId(String resourceInternalId) {
        this.resourceInternalId = resourceInternalId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getLanguageISO() {
        return languageISO;
    }

    public void setLanguageISO(String languageISO) {
        this.languageISO = languageISO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getXR_IdAliado() {
        return XR_IdAliado;
    }

    public void setXR_IdAliado(String XR_IdAliado) {
        this.XR_IdAliado = XR_IdAliado;
    }
}
