/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cardenaslb
 */
@XmlRootElement(name = "cmtRequestConsByTokenDto")
public class CmtRequestConsByTokenDto {
    @XmlElement
    String tokenConsulta;

    public String getTokenConsulta() {
        return tokenConsulta;
    }

    public void setTokenConsulta(String tokenConsulta) {
        this.tokenConsulta = tokenConsulta;
    }
    
    
    
    
    
}
