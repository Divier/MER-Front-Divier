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
public class ResponseDataManttoBarrio {

    @XmlElement
    private String codigo = "";
    @XmlElement
    private String descripcion = "";
    @XmlElement
    private String codigoDivision = "";
    @XmlElement
    private String descripcionDivision = "";
    @XmlElement
    private String codigoComunidad = "";
    @XmlElement
    private String descripcionComunidad = "";

    public ResponseDataManttoBarrio() {
    }

    public ResponseDataManttoBarrio(String responseString) {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            codigo = spliter[0];
            descripcion = spliter[1];
            codigoDivision = spliter[2];
            descripcionDivision = spliter[3];
            codigoComunidad = spliter[4];
            descripcionComunidad = spliter[4];
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
