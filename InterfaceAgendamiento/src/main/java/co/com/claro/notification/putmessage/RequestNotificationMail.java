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
@XmlType(name = "requestNotificationMail", propOrder = {
    "headerRequest",
    "message"
})
public class RequestNotificationMail {
    
    @XmlElement()
    protected HeaderRequest headerRequest;
    
    @XmlElement()
    protected String message;

    public HeaderRequest getHeaderRequest() {
        return headerRequest;
    }

    public void setHeaderRequest(HeaderRequest headerRequest) {
        this.headerRequest = headerRequest;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
