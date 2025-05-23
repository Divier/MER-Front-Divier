/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.atencionInmediata.agenda.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author bocanegravm
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "agendaInmediataRequest", propOrder = {
    "workOrder",
    "type",
    "techncianId",
    "start",
    "end",
    "userId"
})
public class RequestAgendaInmediata {
    
    @XmlElement
    protected Long  workOrder;
    @XmlElement
    protected String type;
    @XmlElement
    protected String techncianId;
    @XmlElement
    protected String start;
    @XmlElement
    protected String end;
    @XmlElement
    protected String userId;

    public Long getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(Long workOrder) {
        this.workOrder = workOrder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTechncianId() {
        return techncianId;
    }

    public void setTechncianId(String techncianId) {
        this.techncianId = techncianId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}
