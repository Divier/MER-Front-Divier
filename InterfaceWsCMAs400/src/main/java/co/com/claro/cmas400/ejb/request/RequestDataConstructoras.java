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
public class RequestDataConstructoras {

    @XmlElement
    private String nombreUsuario = "";
    @XmlElement
    private String codigo = "";
    @XmlElement
    private String descripcion = "";
    @XmlElement
    private String telefono1 = "";
    @XmlElement
    private String telefono2 = "";
    @XmlElement
    private String correoElectronico = "";
    @XmlElement
    private String nombreContacto = "";
    @XmlElement
    private String direccion = "";
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
