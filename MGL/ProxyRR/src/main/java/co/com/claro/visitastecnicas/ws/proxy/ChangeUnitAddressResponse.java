/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.visitastecnicas.ws.proxy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author carlos.villa.ext
 */
@XmlRootElement(name = "changeUnitAddressResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "changeUnitAddressResponse", propOrder = {
    "response"
})
public class ChangeUnitAddressResponse {

    protected ResponseMessageUnit response;

    /**
     * Obtiene el valor de la propiedad response.
     *
     * @return possible object is {@link ResponseMessageUnit }
     *
     */
    public ResponseMessageUnit getResponse() {
        return response;
    }

    /**
     * Define el valor de la propiedad response.
     *
     * @param value allowed object is {@link ResponseMessageUnit }
     *
     */
    public void setResponse(ResponseMessageUnit value) {
        this.response = value;
    }

}
