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
public class RequestDataGestionDeAvanzada {
    
    @XmlElement
    private String nombreUsuario = "";
    @XmlElement
    private String codigoEdificioConsultar = "";
    @XmlElement
    private String subEdificioConsultar = "";
    @XmlElement
    private String codigoEdificio = "";
    @XmlElement
    private String nombreEdificio = "";
    @XmlElement
    private String nombreAsesor = "";
    @XmlElement
    private String telefono1 = "";
    @XmlElement
    private String codigoSupervisor = "";
    @XmlElement
    private String telefono2 = "";
    @XmlElement
    private String descripcionEstado = "";
    @XmlElement
    private String estado = "";
    @XmlElement
    private String codigoSubEdificio = "";
    @XmlElement
    private String nombreSubEdificio = "";
    @XmlElement
    private String fechaGestion = "";
    @XmlElement
    private String observacion1 = "";
    @XmlElement
    private String observacion2 = "";
    @XmlElement
    private String observacion3 = "";
    @XmlElement
    private String observacion4 = "";
    @XmlElement
    private String observacion5 = "";

    public String getCodigoEdificioConsultar() {
        return codigoEdificioConsultar;
    }

    public void setCodigoEdificioConsultar(String codigoEdificioConsultar) {
        this.codigoEdificioConsultar = codigoEdificioConsultar;
    }

    public String getSubEdificioConsultar() {
        return subEdificioConsultar;
    }

    public void setSubEdificioConsultar(String subEdificioConsultar) {
        this.subEdificioConsultar = subEdificioConsultar;
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

    public String getNombreAsesor() {
        return nombreAsesor;
    }

    public void setNombreAsesor(String nombreAsesor) {
        this.nombreAsesor = nombreAsesor;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getCodigoSupervisor() {
        return codigoSupervisor;
    }

    public void setCodigoSupervisor(String codigoSupervisor) {
        this.codigoSupervisor = codigoSupervisor;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoSubEdificio() {
        return codigoSubEdificio;
    }

    public void setCodigoSubEdificio(String codigoSubEdificio) {
        this.codigoSubEdificio = codigoSubEdificio;
    }

    public String getNombreSubEdificio() {
        return nombreSubEdificio;
    }

    public void setNombreSubEdificio(String nombreSubEdificio) {
        this.nombreSubEdificio = nombreSubEdificio;
    }

    public String getFechaGestion() {
        return fechaGestion;
    }

    public void setFechaGestion(String fechaGestion) {
        this.fechaGestion = fechaGestion;
    }

    public String getObservacion1() {
        return observacion1;
    }

    public void setObservacion1(String observacion1) {
        this.observacion1 = observacion1;
    }

    public String getObservacion2() {
        return observacion2;
    }

    public void setObservacion2(String observacion2) {
        this.observacion2 = observacion2;
    }

    public String getObservacion3() {
        return observacion3;
    }

    public void setObservacion3(String observacion3) {
        this.observacion3 = observacion3;
    }

    public String getObservacion4() {
        return observacion4;
    }

    public void setObservacion4(String observacion4) {
        this.observacion4 = observacion4;
    }

    public String getObservacion5() {
        return observacion5;
    }

    public void setObservacion5(String observacion5) {
        this.observacion5 = observacion5;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
}
