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

@XmlRootElement(name = "SpecialUpdateManagerResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpecialUpdateManagerResponse", propOrder = {
    "responseSpecialUpdate"
})
public class SpecialUpdateManagerResponse {

    @XmlElement(name = "responseSpecialUpdate")
    protected ResponseMessageUnit responseSpecialUpdate;

    public ResponseMessageUnit getResponseSpecialUpdate() {
        return responseSpecialUpdate;
    }

    public void setResponseSpecialUpdate(ResponseMessageUnit value) {
        this.responseSpecialUpdate = value;
    }

}
