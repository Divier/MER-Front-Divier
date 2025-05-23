/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@XmlRootElement
public class RequestDataInventarioCuentaMatriz {

    @XmlElement
    private String nombreUsuario = "";
    @XmlElement
    private String codigoEdificio = "";
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

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCodigoEdificio() {
        return codigoEdificio;
    }

    public void setCodigoEdificio(String codigoEdificio) {
        this.codigoEdificio = codigoEdificio;
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
