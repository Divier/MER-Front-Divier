/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author camargomf
 */
@XmlRootElement
public class RequestDataConsultaPorTelefono {

    @XmlElement
    private String telefonoEdificio = "";

    public String getTelefonoEdificio() {
        return telefonoEdificio;
    }

    public void setTelefonoEdificio(String telefonoEdificio) {
        this.telefonoEdificio = telefonoEdificio;
    }

}
