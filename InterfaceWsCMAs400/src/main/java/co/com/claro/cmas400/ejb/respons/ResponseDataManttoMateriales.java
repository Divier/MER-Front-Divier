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
public class ResponseDataManttoMateriales {

    @XmlElement
    private String codigo = "";
    @XmlElement
    private String nombre = "";
    @XmlElement
    private String tipo = "";
    @XmlElement
    private String tipoNombre = "";
    @XmlElement
    private String codigo_a;
    @XmlElement
    private String tipo_a;

    public ResponseDataManttoMateriales() {
    }

    public ResponseDataManttoMateriales(String responseString)  {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            codigo = spliter[0];
            nombre = spliter[1];
            tipo = spliter[2];
            tipoNombre = spliter[3];
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoNombre() {
        return tipoNombre;
    }

    public void setTipoNombre(String tipoNombre) {
        this.tipoNombre = tipoNombre;
    }

    public String getCodigo_a() {
        return codigo_a;
    }

    public void setCodigo_a(String codigo_a) {
        this.codigo_a = codigo_a;
    }

    public String getTipo_a() {
        return tipo_a;
    }

    public void setTipo_a(String tipo_a) {
        this.tipo_a = tipo_a;
    }
}
