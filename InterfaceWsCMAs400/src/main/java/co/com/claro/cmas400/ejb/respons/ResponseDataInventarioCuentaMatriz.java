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
public class ResponseDataInventarioCuentaMatriz {

    @XmlElement
    private String codigoSubedificio = "";
    @XmlElement
    private String tipo = "";
    @XmlElement
    private String fabrica = "";
    @XmlElement
    private String numeroSerie = "";
    @XmlElement
    private String estado = "";

    public ResponseDataInventarioCuentaMatriz() {
    }

    public ResponseDataInventarioCuentaMatriz(String responseString) {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            codigoSubedificio = spliter[0];
            tipo = spliter[1];
            fabrica = spliter[2];
            numeroSerie = spliter[3];
            estado = spliter[4];
        }
    }

    public String getCodigoSubedificio() {
        return codigoSubedificio;
    }

    public void setCodigoSubedificio(String codigoSubedificio) {
        this.codigoSubedificio = codigoSubedificio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFabrica() {
        return fabrica;
    }

    public void setFabrica(String fabrica) {
        this.fabrica = fabrica;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
