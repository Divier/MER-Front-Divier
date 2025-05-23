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
public class RequestDataProveedores {

    @XmlElement
    private String nombreUsuario = "";
    @XmlElement
    private String codigo = "";
    @XmlElement
    private String nombre = "";
    @XmlElement
    private String nit = "";
    @XmlElement
    private String telefono1 = "";
    @XmlElement
    private String telefono2 = "";
    @XmlElement
    private String telefono3 = "";
    @XmlElement
    private String telefono4 = "";
    @XmlElement
    private String gerente = "";
    @XmlElement
    private String direccion = "";
    @XmlElement
    private String email1 = "";
    @XmlElement
    private String email2 = "";
    @XmlElement
    private String paginaWeb = "";
    @XmlElement
    private String personaContacto1 = "";
    @XmlElement
    private String personaContacto2 = "";
    @XmlElement
    private String personaContacto3 = "";

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
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

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
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

    public String getTelefono3() {
        return telefono3;
    }

    public void setTelefono3(String telefono3) {
        this.telefono3 = telefono3;
    }

    public String getTelefono4() {
        return telefono4;
    }

    public void setTelefono4(String telefono4) {
        this.telefono4 = telefono4;
    }

    public String getGerente() {
        return gerente;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public String getPersonaContacto1() {
        return personaContacto1;
    }

    public void setPersonaContacto1(String personaContacto1) {
        this.personaContacto1 = personaContacto1;
    }

    public String getPersonaContacto2() {
        return personaContacto2;
    }

    public void setPersonaContacto2(String personaContacto2) {
        this.personaContacto2 = personaContacto2;
    }

    public String getPersonaContacto3() {
        return personaContacto3;
    }

    public void setPersonaContacto3(String personaContacto3) {
        this.personaContacto3 = personaContacto3;
    }
}
