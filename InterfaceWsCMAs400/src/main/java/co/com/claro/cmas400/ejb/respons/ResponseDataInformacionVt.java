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
public class ResponseDataInformacionVt {

    @XmlElement
    private String numTrabajo = "";
    @XmlElement
    private String numNota = "";
    @XmlElement
    private String descripcion = "";
    @XmlElement
    private String tipoNota = "";
    @XmlElement
    private String descTipoNota = "";
    @XmlElement
    private String puntoInicial = "";
    @XmlElement
    private String codigo = "";
    @XmlElement
    private String tipoAcompanamiento = "";
    @XmlElement
    private String ubicacionCaja = "";
    @XmlElement
    private String codigo2 = "";

    public ResponseDataInformacionVt() {
    }

    public ResponseDataInformacionVt(String responseString) {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            numTrabajo = spliter[0];
            numNota = spliter[1];
            descripcion = spliter[2];
            tipoNota = spliter[3];
            descTipoNota = spliter[4];
            puntoInicial = spliter[5];
            codigo = spliter[6];
            tipoAcompanamiento = spliter[7];
            ubicacionCaja = spliter[8];
            codigo2 = spliter[9];
        }
    }

    public String getNumTrabajo() {
        return numTrabajo;
    }

    public void setNumTrabajo(String numTrabajo) {
        this.numTrabajo = numTrabajo;
    }

    public String getNumNota() {
        return numNota;
    }

    public void setNumNota(String numNota) {
        this.numNota = numNota;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(String tipoNota) {
        this.tipoNota = tipoNota;
    }

    public String getDescTipoNota() {
        return descTipoNota;
    }

    public void setDescTipoNota(String descTipoNota) {
        this.descTipoNota = descTipoNota;
    }

    public String getPuntoInicial() {
        return puntoInicial;
    }

    public void setPuntoInicial(String puntoInicial) {
        this.puntoInicial = puntoInicial;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipoAcompanamiento() {
        return tipoAcompanamiento;
    }

    public void setTipoAcompanamiento(String tipoAcompanamiento) {
        this.tipoAcompanamiento = tipoAcompanamiento;
    }

    public String getUbicacionCaja() {
        return ubicacionCaja;
    }

    public void setUbicacionCaja(String ubicacionCaja) {
        this.ubicacionCaja = ubicacionCaja;
    }

    public String getCodigo2() {
        return codigo2;
    }

    public void setCodigo2(String codigo2) {
        this.codigo2 = codigo2;
    }
}
