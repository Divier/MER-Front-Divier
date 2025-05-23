package co.com.claro.visitastecnicas.ws.proxy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * objetivo de la clase. Clase plantilla de objetos que retonan las respuestas
 * de creacion de una Llamada de servicio RR.
 *
 * @author Carlos Leonardo Villamil.
 * @versión 1.00.000
 */
@XmlRootElement(name = "ResponseCreateServiceCall")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseCreateServiceCall", propOrder = {
    "messageText",
    "messageType",
    "callServiceId"
})
public class ResponseCreateServiceCall {

    @XmlElement(name = "messageText")
    protected String messageText;
    @XmlElement(name = "messageType")
    protected String messageType;
    @XmlElement(name = "callServiceId")
    protected String callServiceId;

    public String getCallServiceId() {
        return callServiceId;
    }

    public void setCallServiceId(String callServiceId) {
        this.callServiceId = callServiceId;
    }

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
