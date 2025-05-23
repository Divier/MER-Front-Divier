/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author camargomf
 */
@XmlRootElement
public class RequestDataNotasOtCmSubEdificio {

    @XmlElement
    private String nombreUsuario = "";
    @XmlElement
    private String codigoEdificioConsulta = "";
    @XmlElement
    private String codigoSubEdificioConsulta = "";
    @XmlElement
    private String numeroOtConsulta = "";
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

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCodigoEdificioConsulta() {
        return codigoEdificioConsulta;
    }

    public void setCodigoEdificioConsulta(String codigoEdificioConsulta) {
        this.codigoEdificioConsulta = codigoEdificioConsulta;
    }

    public String getCodigoSubEdificioConsulta() {
        return codigoSubEdificioConsulta;
    }

    public void setCodigoSubEdificioConsulta(String codigoSubEdificioConsulta) {
        this.codigoSubEdificioConsulta = codigoSubEdificioConsulta;
    }

    public String getNumeroOtConsulta() {
        return numeroOtConsulta;
    }

    public void setNumeroOtConsulta(String numeroOtConsulta) {
        this.numeroOtConsulta = numeroOtConsulta;
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
