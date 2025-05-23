/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */

@XmlRootElement
public class RequestDataConstructorasMgl {

    @XmlElement
    private String constructoraId = "";

    public String getConstructoraId() {
        return constructoraId;
    }

    public void setConstructoraId(String constructoraId) {
        this.constructoraId = constructoraId;
    }
    
    
   
}
