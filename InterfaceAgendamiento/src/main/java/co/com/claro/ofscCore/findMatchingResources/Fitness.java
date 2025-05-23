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
@XmlRootElement(name = "fitness")
public class Fitness {

    @XmlElement
    protected double resourcePreference;

    @XmlElement
    protected int workSkill;

    @XmlElement
    protected int workTime;

    @XmlElement
    protected int workZone;

    public double getResourcePreference() {
        return resourcePreference;
    }

    public void setResourcePreference(double resourcePreference) {
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
