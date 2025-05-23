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
public class ResponseDataConsultaComunidad {

    @XmlElement
    private String codigoComunidad = "";
    @XmlElement
    private String nombreComunidad = "";
    @XmlElement
    private String codigoDivision = "";

    public ResponseDataConsultaComunidad() {
    }

    public ResponseDataConsultaComunidad(String responseString) {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            codigoComunidad = spliter[0];
            nombreComunidad = spliter[1];
            codigoDivision = spliter[2];
        }
    }

    public String getCodigoComunidad() {
        return codigoComunidad;
    }

    public void setCodigoComunidad(String codigoComunidad) {
        this.codigoComunidad = codigoComunidad;
    }

    public String getNombreComunidad() {
        return nombreComunidad;
    }

    public void setNombreComunidad(String nombreComunidad) {
        this.nombreComunidad = nombreComunidad;
    }

    public String getCodigoDivision() {
        return codigoDivision;
    }

    public void setCodigoDivision(String codigoDivision) {
        this.codigoDivision = codigoDivision;
    }

}
