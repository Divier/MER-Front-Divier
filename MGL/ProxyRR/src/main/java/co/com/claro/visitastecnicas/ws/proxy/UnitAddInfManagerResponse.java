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
@XmlRootElement(name = "UnitAddInfManagerResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnitAddInfManagerResponse", propOrder = {
    "responseUnitAddInf"
})
public class UnitAddInfManagerResponse {

    @XmlElement(name = "responseUnitAddInf")
    protected ResponseMessageUnit responseUnitAddInf;

    public ResponseMessageUnit getResponseUnitAddInf() {
        return responseUnitAddInf;
    }

    public void setResponseUnitAddInf(ResponseMessageUnit responseUnitAddInf) {
        this.responseUnitAddInf = responseUnitAddInf;
    }

}
