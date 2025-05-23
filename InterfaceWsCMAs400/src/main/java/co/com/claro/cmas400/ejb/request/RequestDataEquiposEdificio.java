/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@XmlRootElement
public class RequestDataEquiposEdificio {

    @XmlElement
    private String codigoEdificio = "";
    
    public String getCodigoEdificio() {
        return codigoEdificio;
    }

    public void setCodigoEdificio(String codigoEdificio) {
        this.codigoEdificio = codigoEdificio;
    }
  
}
