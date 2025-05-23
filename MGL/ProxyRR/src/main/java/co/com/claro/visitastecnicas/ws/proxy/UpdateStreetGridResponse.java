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
 *
 * @author user
 */
@XmlRootElement(name = "updateStreetGridResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateStreetGridResponse", propOrder = {
    "responseUpdateStreetGrid"
})
public class UpdateStreetGridResponse {

    @XmlElement(name = "responseUpdateStreetGrid")
    protected ResponseMessageUnit responseUpdateStreetGrid;

    public ResponseMessageUnit getResponseSpecialUpdate() {
        return responseUpdateStreetGrid;
    }

    public void setResponseSpecialUpdate(ResponseMessageUnit value) {
        this.responseUpdateStreetGrid = value;
    }

}
