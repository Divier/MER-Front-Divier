/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
@XmlRootElement(name = "createServiceCallResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateServiceCallResponse", propOrder = {
    "responseCreateServiceCall"
})
public class CreateServiceCallResponse {

    @XmlElement(name = "ResponseCreateServiceCall")
    protected ResponseCreateServiceCall responseCreateServiceCall;

    public ResponseCreateServiceCall getResponseCreateServiceCall() {
        return responseCreateServiceCall;
    }

    public void setResponseCreateServiceCall(ResponseCreateServiceCall responseCreateServiceCall) {
        this.responseCreateServiceCall = responseCreateServiceCall;
    }
}
