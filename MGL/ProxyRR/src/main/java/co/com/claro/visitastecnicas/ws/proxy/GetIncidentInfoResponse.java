/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.visitastecnicas.ws.proxy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author aleal
 */
@XmlRootElement(name = "getIncidentInfoResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getIncidentInfoResponse", propOrder = {
    "incidentInfoList"
})
public class GetIncidentInfoResponse {

    @XmlElement(name = "incidentInfoList")
    protected IncidentInfoList incidentInfoList;

    public IncidentInfoList getIncidentInfoList() {
        return incidentInfoList;
    }

    public void setIncidentInfoList(IncidentInfoList incidentInfoList) {
        this.incidentInfoList = incidentInfoList;
    }
}
