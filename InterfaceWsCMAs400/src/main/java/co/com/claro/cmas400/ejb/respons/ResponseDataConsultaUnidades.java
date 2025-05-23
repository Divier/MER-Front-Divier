/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.respons;

import java.util.regex.Pattern;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@XmlRootElement
public class ResponseDataConsultaUnidades {

    @XmlElement
    private String direccion = "";
    @XmlElement
    private String apto = "";
    @XmlElement
    private String comunidad = "";
    @XmlElement
    private String nodo = "";
    @XmlElement
    private String suscriptor = "";
    @XmlElement
    private String estado = "";

    public ResponseDataConsultaUnidades() {
    }

    public ResponseDataConsultaUnidades(String responseString) {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            direccion = spliter[0];
            apto = spliter[1];
            comunidad = spliter[2];
            nodo = spliter[3];
            suscriptor = spliter[4];
            estado = spliter[5];
        }
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getApto() {
        return apto;
    }

    public void setApto(String apto) {
        this.apto = apto;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public String getSuscriptor() {
        return suscriptor;
    }

    public void setSuscriptor(String suscriptor) {
        this.suscriptor = suscriptor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
