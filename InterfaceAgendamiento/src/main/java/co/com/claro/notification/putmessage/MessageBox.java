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
@XmlType(name = "messageBox", propOrder = {
    "customerId",
    "customerBox"
})
public class MessageBox {

    @XmlElement()
    protected String customerId;

    @XmlElement()
    protected String customerBox;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerBox() {
        return customerBox;
    }

    public void setCustomerBox(String customerBox) {
        this.customerBox = customerBox;
    }
    
    

}
