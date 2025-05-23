/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.visitastecnicas.ws.proxy;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author cardenaslb
 */
public class EmailCTec {
    
    
   @XmlElement(name = "emailCTec")
    protected String emailCTec;

    public String getEmailCTec() {
        return emailCTec;
    }

    public void setEmailCTec(String emailCTec) {
        this.emailCTec = emailCTec;
    }
   
   
   
   
}
