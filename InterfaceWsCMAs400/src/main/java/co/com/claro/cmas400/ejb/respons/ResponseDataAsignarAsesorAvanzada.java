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
public class ResponseDataAsignarAsesorAvanzada {

    @XmlElement
    private String codigoEdificio = "";
    @XmlElement
    private String nombreEdificio = "";
    @XmlElement
    private String comunidad = "";
    @XmlElement
    private String codigoAsesor = "";
    @XmlElement
    private String nombreAsesor = "";

    public ResponseDataAsignarAsesorAvanzada() {
    }

    public ResponseDataAsignarAsesorAvanzada(String responseString) {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            codigoEdificio = spliter[0];
            nombreEdificio = spliter[1];
            comunidad = spliter[2];
            codigoAsesor = spliter[3];
            nombreAsesor = spliter[4];
        }
    }

    public String getCodigoEdificio() {
        return codigoEdificio;
    }

    public void setCodigoEdificio(String codigoEdificio) {
        this.codigoEdificio = codigoEdificio;
    }

    public String getNombreEdificio() {
        return nombreEdificio;
    }

    public void setNombreEdificio(String nombreEdificio) {
        this.nombreEdificio = nombreEdificio;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getCodigoAsesor() {
        return codigoAsesor;
    }

    public void setCodigoAsesor(String codigoAsesor) {
        this.codigoAsesor = codigoAsesor;
    }

    public String getNombreAsesor() {
        return nombreAsesor;
    }

    public void setNombreAsesor(String nombreAsesor) {
        this.nombreAsesor = nombreAsesor;
    }

}
