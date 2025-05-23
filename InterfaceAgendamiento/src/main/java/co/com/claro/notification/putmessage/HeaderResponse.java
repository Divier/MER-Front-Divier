/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.notification.putmessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author bocanegravm
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "headerResponse", propOrder = {
    "responseDate",
    "traceabilityId"
})
public class HeaderResponse {

    @XmlElement()
    protected String responseDate;

    @XmlElement()
    protected String traceabilityId;

    public String getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(String responseDate) {
        this.responseDate = responseDate;
    }

    public String getTraceabilityId() {
        return traceabilityId;
    }

    public void setTraceabilityId(String traceabilityId) {
        this.traceabilityId = traceabilityId;
    }

}
