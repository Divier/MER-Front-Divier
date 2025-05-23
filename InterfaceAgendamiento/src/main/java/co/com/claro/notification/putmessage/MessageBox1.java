/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.notification.putmessage;

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
@XmlType(name = "messageBox1", propOrder = {
    "messageChannel",
    "messageBox"
})
public class MessageBox1 {

    @XmlElement()
    protected String messageChannel;

    @XmlElement()
    protected List<MessageBox> messageBox;

    public String getMessageChannel() {
        return messageChannel;
    }

    public void setMessageChannel(String messageChannel) {
        this.messageChannel = messageChannel;
    }

    public List<MessageBox> getMessageBox() {
        return messageBox;
    }

    public void setMessageBox(List<MessageBox> messageBox) {
        this.messageBox = messageBox;
    }
}
