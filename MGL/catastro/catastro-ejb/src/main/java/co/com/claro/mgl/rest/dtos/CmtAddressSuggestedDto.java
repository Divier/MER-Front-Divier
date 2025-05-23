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
 * @author bocanegravm
 */
@XmlRootElement(name = "addressSuggested")
public class CmtAddressSuggestedDto  extends  CmtDefaultBasicResponse{

    @XmlElement
    private String address = null;
    @XmlElement
    private String neighborhood = null;

    /**
     * 
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     * 
     * @param neighborhood
     */
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    @Override
    public String toString() {
        return "AddressSuggested{" + "Dirección=" + address + ", Barrio=" + neighborhood + '}';
    }
}
