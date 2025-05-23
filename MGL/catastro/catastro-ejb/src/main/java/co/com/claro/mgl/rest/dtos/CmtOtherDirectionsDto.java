/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que permite reportar otras direcciones de una CCMM
 * @author villamilc
 * @version 1.0
 * 
 */

@XmlRootElement
public class CmtOtherDirectionsDto {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
