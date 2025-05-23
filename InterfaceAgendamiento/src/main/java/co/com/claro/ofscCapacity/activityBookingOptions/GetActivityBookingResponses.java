/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.ofscCapacity.activityBookingOptions;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author bocanegravm
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getActivityBookingResponses", propOrder = {
    "duration",
    "categories",
    "workZone"
})
public class GetActivityBookingResponses {
    
    @XmlElement(name = "duration")
    protected int duration;
    @XmlElement(name = "categories")
    protected List<String> categories;
    @XmlElement(name = "workZone")
    protected String workZone;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getWorkZone() {
        return workZone;
    }

    public void setWorkZone(String workZone) {
        this.workZone = workZone;
    }

}
