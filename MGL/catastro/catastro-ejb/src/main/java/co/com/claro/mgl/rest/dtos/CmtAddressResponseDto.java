/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valbuenayf
 */
@XmlRootElement(name = "addressResponse")
public class CmtAddressResponseDto extends CmtDefaultBasicResponse {

    @XmlElement
    private CmtAddressDto addresses;

    public CmtAddressDto getAddresses() {
        return addresses;
    }

    public void setAddresses(CmtAddressDto addresses) {
        this.addresses = addresses;
    }
}
