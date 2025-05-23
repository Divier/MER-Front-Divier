package co.com.claro.mgl.dtos;

/**
 * Dto response basico de ws 
 *
 * @author carlos.villa.ext@claro.com.co
 *
 * @versión 1.00.0000
 */
public class CmtBasicResponse {
    String message="";
    String messageType="";
    String value="";

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
