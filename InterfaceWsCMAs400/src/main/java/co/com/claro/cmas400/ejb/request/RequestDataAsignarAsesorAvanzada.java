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
public class RequestDataAsignarAsesorAvanzada {

    @XmlElement
    private String nombreUsuario = "";
    @XmlElement
    private String cuentaConsulta = "";
    @XmlElement
    private String comunidadConsulta = "";
    @XmlElement
    private String asesorConsulta = "";
    @XmlElement
    private String nombreConsulta = "";
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

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCuentaConsulta() {
        return cuentaConsulta;
    }

    public void setCuentaConsulta(String cuentaConsulta) {
        this.cuentaConsulta = cuentaConsulta;
    }

    public String getComunidadConsulta() {
        return comunidadConsulta;
    }

    public void setComunidadConsulta(String comunidadConsulta) {
        this.comunidadConsulta = comunidadConsulta;
    }

    public String getAsesorConsulta() {
        return asesorConsulta;
    }

    public void setAsesorConsulta(String asesorConsulta) {
        this.asesorConsulta = asesorConsulta;
    }

    public String getNombreConsulta() {
        return nombreConsulta;
    }

    public void setNombreConsulta(String nombreConsulta) {
        this.nombreConsulta = nombreConsulta;
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
