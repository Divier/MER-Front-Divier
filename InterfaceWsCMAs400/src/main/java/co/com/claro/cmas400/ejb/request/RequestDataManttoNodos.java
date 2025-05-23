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
public class RequestDataManttoNodos {

    @XmlElement
    private String codNodo = "";

    public String getCodNodo() {
        return codNodo;
    }

    public void setCodNodo(String codNodo) {
        this.codNodo = codNodo;
    }
}
