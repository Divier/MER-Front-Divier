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
public class ResponseDataManttoSubEdificios {

    @XmlElement
    private String codigo = "";
    @XmlElement
    private String descripcion = "";
    @XmlElement
    private String codigoEstado = "";
    @XmlElement
    private String estado = "";
    @XmlElement
    private String nodo = "";

    public ResponseDataManttoSubEdificios() {
    }

    public ResponseDataManttoSubEdificios(String responseString)
             {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            codigo = spliter[0];
            descripcion = spliter[1];
            codigoEstado = spliter[2];
            estado = spliter[3];
            nodo = spliter[4];
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

    public String getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(String codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }
}
