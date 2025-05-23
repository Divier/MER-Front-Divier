package co.com.claro.mgl.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Dto response basico de ws
 *
 * @author carlos.villa.ext@claro.com.co
 *
 * @versión 1.00.0000
 */
@XmlRootElement(name = "CmtDefaultBasicResponse")
public class CmtDefaultBasicResponse {

    @XmlElement
    private String message;
    @XmlElement
    private String messageType;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
