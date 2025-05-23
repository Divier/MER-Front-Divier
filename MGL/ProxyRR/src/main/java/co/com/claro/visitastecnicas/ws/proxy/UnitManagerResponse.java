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
 * @author carlos.villa.ext
 */
@XmlRootElement(name = "UnitManagerResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnitManagerResponse", propOrder = {
    "responseAddUnit"
})
public class UnitManagerResponse {

    @XmlElement(name = "responseAddUnit")
    protected ResponseMessageUnit responseAddUnit;

    public ResponseMessageUnit getResponseAddUnit() {
        return responseAddUnit;
    }

    public void setResponseAddUnit(ResponseMessageUnit value) {
        this.responseAddUnit = value;
    }
}
