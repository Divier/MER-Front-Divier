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
public class ResponseDataInformacionNodo {

    @XmlElement
    private String numero = "";
    @XmlElement
    private String codigo = "";
    @XmlElement
    private String nombre = "";
    @XmlElement
    private String fechaApertura = "";
    @XmlElement
    private String costoRed = "";
    @XmlElement
    private String limites = "";

    public ResponseDataInformacionNodo() {
    }

    public ResponseDataInformacionNodo(String responseString) {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            numero = spliter[0];
            codigo = spliter[1];
            nombre = spliter[2];
            fechaApertura = spliter[3];
            costoRed = spliter[4];
            limites = spliter[5];
        }
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public String getCostoRed() {
        return costoRed;
    }

    public void setCostoRed(String costoRed) {
        this.costoRed = costoRed;
    }

    public String getLimites() {
        return limites;
    }

    public void setLimites(String limites) {
        this.limites = limites;
    }
}
