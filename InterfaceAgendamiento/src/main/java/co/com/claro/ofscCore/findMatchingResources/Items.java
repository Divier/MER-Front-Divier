/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.ofscCore.findMatchingResources;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement(name = "items")
public class Items {
    
    @XmlElement
    protected String estimatedCompleteDate;
    
    @XmlElement
    protected Fitness fitness;
    
    @XmlElement
    protected Resource resource;
    
    @XmlElement
    public Map<String, Object> schedules;  
    
    public List<String> franjasDisponibles;
    

    public String getEstimatedCompleteDate() {
        return estimatedCompleteDate;
    }

    public void setEstimatedCompleteDate(String estimatedCompleteDate) {
        this.estimatedCompleteDate = estimatedCompleteDate;
    }

    public Fitness getFitness() {
        return fitness;
    }

    public void setFitness(Fitness fitness) {
        this.fitness = fitness;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @JsonAnyGetter
    public Map<String, Object> getSchedules() {
        return schedules;
    }
    
    @JsonAnySetter
    public void setSchedules(String key, Object value) {
       schedules.put(key, value);
    }

    public List<String> getFranjasDisponibles() {
        return franjasDisponibles;
    }

    public void setFranjasDisponibles(List<String> franjasDisponibles) {
        this.franjasDisponibles = franjasDisponibles;
    }
}
