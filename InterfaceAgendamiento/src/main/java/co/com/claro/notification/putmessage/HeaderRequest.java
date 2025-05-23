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
@XmlType(name = "headerRequest_element", propOrder = {
    "transactionId",
    "system",
    "target",
    "user",
    "password",
    "requestDate",
    "ipApplication",
    "traceabilityId"
})
public class HeaderRequest {

    @XmlElement()
    protected String transactionId;

    @XmlElement()
    protected String system;

    @XmlElement()
    protected String target;

    @XmlElement()
    protected String user;

    @XmlElement()
    protected String password;

    @XmlElement()
    protected String requestDate;

    @XmlElement()
    protected String ipApplication;

    @XmlElement()
    protected String traceabilityId;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getIpApplication() {
        return ipApplication;
    }

    public void setIpApplication(String ipApplication) {
        this.ipApplication = ipApplication;
    }

    public String getTraceabilityId() {
        return traceabilityId;
    }

    public void setTraceabilityId(String traceabilityId) {
        this.traceabilityId = traceabilityId;
    }
}
