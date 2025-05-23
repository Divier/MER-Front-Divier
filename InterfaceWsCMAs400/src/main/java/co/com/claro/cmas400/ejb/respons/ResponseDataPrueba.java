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
 * @author camargomf
 */
@XmlRootElement
public class ResponseDataPrueba {

    @XmlElement
    private String codigoBarrio = "";
    @XmlElement
    private String descripcionBarrio = "";
    @XmlElement
    private String codigoDivision = "";
    @XmlElement
    private String descripcionDivision = "";
    @XmlElement
    private String codigoComunidad = "";
    @XmlElement
    private String descripcionComunidad = "";

    public ResponseDataPrueba() {
    }

    public ResponseDataPrueba(String responseString)  {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            codigoBarrio = spliter[0];
            descripcionBarrio = spliter[1];
            codigoDivision = spliter[2];
            descripcionDivision = spliter[3];
            codigoComunidad = spliter[4];
            descripcionComunidad = spliter[5];
        }
    }

    public String getCodigoBarrio() {
        return codigoBarrio;
    }

    public void setCodigoBarrio(String codigoBarrio) {
        this.codigoBarrio = codigoBarrio;
    }

    public String getDescripcionBarrio() {
        return descripcionBarrio;
    }

    public void setDescripcionBarrio(String descripcionBarrio) {
        this.descripcionBarrio = descripcionBarrio;
    }

    public String getCodigoDivision() {
        return codigoDivision;
    }

    public void setCodigoDivision(String codigoDivision) {
        this.codigoDivision = codigoDivision;
    }

    public String getDescripcionDivision() {
        return descripcionDivision;
    }

    public void setDescripcionDivision(String descripcionDivision) {
        this.descripcionDivision = descripcionDivision;
    }

    public String getCodigoComunidad() {
        return codigoComunidad;
    }

    public void setCodigoComunidad(String codigoComunidad) {
        this.codigoComunidad = codigoComunidad;
    }

    public String getDescripcionComunidad() {
        return descripcionComunidad;
    }

    public void setDescripcionComunidad(String descripcionComunidad) {
        this.descripcionComunidad = descripcionComunidad;
    }
}
