package co.com.claro.visitastecnicas.ws.proxy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "responseMessageUnit")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "responseMessageUnit", propOrder = {
    "akyn",
    "messageText",
    "messageType",
    "strn"
})
public class ResponseMessageUnit {

    @XmlElement(name = "AKYN")
    protected String akyn;
    @XmlElement(name = "messageText")
    protected String messageText;
    @XmlElement(name = "messageType")
    protected String messageType;
    @XmlElement(name = "STRN")
    protected String strn;

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

    public String getAkyn() {
        return akyn;
    }

    public void setAkyn(String akyn) {
        this.akyn = akyn;
    }

    public String getStrn() {
        return strn;
    }

    public void setStrn(String strn) {
        this.strn = strn;
    }
}
