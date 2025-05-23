package co.com.claro.visitastecnicas.ws.proxy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "responseCRUDUnit")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "responseCRUDUnit", propOrder = {
    "messageText",
    "messageType"
})
public class ResponseCRUDUnit {

    @XmlElement(name = "messageText")
    protected String messageText;
    @XmlElement(name = "messageType")
    protected String messageType;

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

}
