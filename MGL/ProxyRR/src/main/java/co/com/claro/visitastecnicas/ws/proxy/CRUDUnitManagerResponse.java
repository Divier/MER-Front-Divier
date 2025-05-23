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
@XmlRootElement(name = "CRUDUnitManagerResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CRUDUnitManagerResponse", propOrder = {
    "responseCRUDUnit"
})
public class CRUDUnitManagerResponse {

    @XmlElement(name = "responseCRUDUnit")
    protected ResponseCRUDUnit responseCRUDUnit;

    public ResponseCRUDUnit getResponseCRUDUnit() {
        return responseCRUDUnit;
    }

    public void setResponseCRUDUnit(ResponseCRUDUnit responseCRUDUnit) {
        this.responseCRUDUnit = responseCRUDUnit;
    }
}
