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
public class ResponseDataManttoEdificios {

    @XmlElement
    private String codigo = "";
    @XmlElement
    private String descripcion = "";
    @XmlElement
    private String codigoDivision = "";
    @XmlElement
    private String nombreDivision = "";
    @XmlElement
    private String codigoComunidad = "";
    @XmlElement
    private String nombreComunidad = "";
    @XmlElement
    private String codigoBarrio = "";
    @XmlElement
    private String nombreBarrio = "";

    public ResponseDataManttoEdificios() {
    }

    public ResponseDataManttoEdificios(String responseString)  {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            codigo = spliter[0];
            descripcion = spliter[1];
            codigoDivision = spliter[2];
            nombreDivision = spliter[3];
            codigoComunidad = spliter[4];
            nombreComunidad = spliter[5];
            codigoBarrio = spliter[6];
            nombreBarrio = spliter[7];
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

    public String getNombreDivision() {
        return nombreDivision;
    }

    public void setNombreDivision(String nombreDivision) {
        this.nombreDivision = nombreDivision;
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

    public String getCodigoBarrio() {
        return codigoBarrio;
    }

    public void setCodigoBarrio(String codigoBarrio) {
        this.codigoBarrio = codigoBarrio;
    }

    public String getNombreBarrio() {
        return nombreBarrio;
    }

    public void setNombreBarrio(String nombreBarrio) {
        this.nombreBarrio = nombreBarrio;
    }
}
