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
public class ResponseDataNotasOtCuentaMatriz {

    @XmlElement
    private String codigoNota = "";
    @XmlElement
    private String codigoDescripcion = "";
    @XmlElement
    private String tipoNota = "";
    @XmlElement
    private String nombreTipo = "";
    @XmlElement
    private String ptoInic = "";
    @XmlElement
    private String aliElec = "";
    @XmlElement
    private String tipAcom = "";
    @XmlElement
    private String ubiCaja = "";
    @XmlElement
    private String tipDist = "";
    @XmlElement
    private String descripcion = "";
    @XmlElement
    private String fechaCreacion = "";

    public ResponseDataNotasOtCuentaMatriz() {
    }

    public ResponseDataNotasOtCuentaMatriz(String responseString)  {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            codigoNota = spliter[0];
            codigoDescripcion = spliter[1];
            tipoNota = spliter[2];
            nombreTipo = spliter[3];
            ptoInic = spliter[4];
            aliElec = spliter[5];
            tipAcom = spliter[6];
            ubiCaja = spliter[7];
            tipDist = spliter[8];
            descripcion = spliter[9];
            fechaCreacion = spliter[10];
        }
    }

    public String getCodigoNota() {
        return codigoNota;
    }

    public void setCodigoNota(String codigoNota) {
        this.codigoNota = codigoNota;
    }

    public String getCodigoDescripcion() {
        return codigoDescripcion;
    }

    public void setCodigoDescripcion(String codigoDescripcion) {
        this.codigoDescripcion = codigoDescripcion;
    }

    public String getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(String tipoNota) {
        this.tipoNota = tipoNota;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public String getPtoInic() {
        return ptoInic;
    }

    public void setPtoInic(String ptoInic) {
        this.ptoInic = ptoInic;
    }

    public String getAliElec() {
        return aliElec;
    }

    public void setAliElec(String aliElec) {
        this.aliElec = aliElec;
    }

    public String getTipAcom() {
        return tipAcom;
    }

    public void setTipAcom(String tipAcom) {
        this.tipAcom = tipAcom;
    }

    public String getUbiCaja() {
        return ubiCaja;
    }

    public void setUbiCaja(String ubiCaja) {
        this.ubiCaja = ubiCaja;
    }

    public String getTipDist() {
        return tipDist;
    }

    public void setTipDist(String tipDist) {
        this.tipDist = tipDist;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}
