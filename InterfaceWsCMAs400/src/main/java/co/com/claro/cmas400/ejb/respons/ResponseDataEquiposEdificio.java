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
public class ResponseDataEquiposEdificio {

    @XmlElement
    private String codigoEdificio = "";
    @XmlElement
    private String estadoEdificio = "";
    @XmlElement
    private String nombreEdificio = "";
    @XmlElement
    private String direccionEdificio = "";
    @XmlElement
    private String tipoEdificio = "";
    @XmlElement
    private String tipo = "";
    @XmlElement
    private String fabricante = "";
    @XmlElement
    private String numSerie = "";
    @XmlElement
    private String estado = "";
    @XmlElement
    private String anchoBanda = "";

    public ResponseDataEquiposEdificio() {
    }

    public ResponseDataEquiposEdificio(String responseString) {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            codigoEdificio = spliter[0];
            estadoEdificio = spliter[1];
            nombreEdificio = spliter[2];
            direccionEdificio = spliter[3];
            tipoEdificio = spliter[4];
            tipo = spliter[5];
            fabricante = spliter[6];
            numSerie = spliter[7];
            estado = spliter[8];
            anchoBanda = spliter[9];
        }
    }

    public String getCodigoEdificio() {
        return codigoEdificio;
    }

    public void setCodigoEdificio(String codigoEdificio) {
        this.codigoEdificio = codigoEdificio;
    }

    public String getEstadoEdificio() {
        return estadoEdificio;
    }

    public void setEstadoEdificio(String estadoEdificio) {
        this.estadoEdificio = estadoEdificio;
    }

    public String getNombreEdificio() {
        return nombreEdificio;
    }

    public void setNombreEdificio(String nombreEdificio) {
        this.nombreEdificio = nombreEdificio;
    }

    public String getDireccionEdificio() {
        return direccionEdificio;
    }

    public void setDireccionEdificio(String direccionEdificio) {
        this.direccionEdificio = direccionEdificio;
    }

    public String getTipoEdificio() {
        return tipoEdificio;
    }

    public void setTipoEdificio(String tipoEdificio) {
        this.tipoEdificio = tipoEdificio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAnchoBanda() {
        return anchoBanda;
    }

    public void setAnchoBanda(String anchoBanda) {
        this.anchoBanda = anchoBanda;
    }
}
