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
@XmlType(name = "criteria_element", propOrder
        = {
            "includeResources",
            "resourcePreference",
            "workSkill",
            "workTime",
            "workZone"
        })
public class Criteria {

    /**
     *
     */
    public Criteria() {
    }


    /**
     *
     */
    @XmlElement
    protected String includeResources;

    /**
     *
     */
    @XmlElement
    protected int resourcePreference;

    /**
     *
     */
    @XmlElement
    protected int workSkill;

    /**
     *
     */
    @XmlElement
    protected int workTime;

    /**
     *
     */
    @XmlElement
    protected int workZone;

    public String getIncludeResources() {
        return includeResources;
    }

    public void setIncludeResources(String includeResources) {
        this.includeResources = includeResources;
    }

    public int getResourcePreference() {
        return resourcePreference;
    }

    public void setResourcePreference(int resourcePreference) {
        this.resourcePreference = resourcePreference;
    }

    public int getWorkSkill() {
        return workSkill;
    }

    public void setWorkSkill(int workSkill) {
        this.workSkill = workSkill;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public int getWorkZone() {
        return workZone;
    }

    public void setWorkZone(int workZone) {
        this.workZone = workZone;
    }

}
