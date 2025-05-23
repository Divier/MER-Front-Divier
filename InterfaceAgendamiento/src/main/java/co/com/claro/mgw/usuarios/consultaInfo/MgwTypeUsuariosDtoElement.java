/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgw.usuarios.consultaInfo;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Data Transfer Object para Usuarios.
 *
 * @author
 * @author bocanegravm.
 */
@XmlRootElement
public class MgwTypeUsuariosDtoElement {

    @XmlElement(name = "cedula", required = true)
    private Long cedula;
    @XmlElement(name = "nombre", required = true)
    private String nombre;
    @XmlElement(name = "telefono", required = true)
    private String telefono;
    @XmlElement(name = "email", required = true)
    private String email;
    @XmlElement(name = "direccion", required = true)
    private String direccion;
    @XmlElement(name = "usuario", required = true)
    private String usuario;
    @XmlElement(name = "codPerfil", required = true)
    private String codPerfil;
    @XmlElement(name = "descripcionPerfil", required = true)
    private String descripcionPerfil;
    @XmlElement(name = "idArea", required = true)
    private BigInteger idArea;
    @XmlElement(name = "descripcionArea", required = true)
    private String descripcionArea;

    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (cedula != null) {
            builder.append("ID: ").append(cedula).append("; ");
        }
        if (nombre != null) {
            builder.append("Nombre: ").append(nombre).append("; ");
        }
        if (usuario != null) {
            builder.append("Usuario: ").append(usuario).append("; ");
        }
        if (descripcionPerfil != null) {
            builder.append("Perfil: ").append(descripcionPerfil);
        }

        return (builder.toString());
    }

    public Long getCedula() {
        return cedula;
    }

    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCodPerfil() {
        return codPerfil;
    }

    public void setCodPerfil(String codPerfil) {
        this.codPerfil = codPerfil;
    }

    public String getDescripcionPerfil() {
        return descripcionPerfil;
    }

    public void setDescripcionPerfil(String descripcionPerfil) {
        this.descripcionPerfil = descripcionPerfil;
    }

    public BigInteger getIdArea() {
        return idArea;
    }

    public void setIdArea(BigInteger idArea) {
        this.idArea = idArea;
    }
    
    public String getDescripcionArea() {
        return descripcionArea;
    }

    public void setDescripcionArea(String descripcionArea) {
        this.descripcionArea = descripcionArea;
    }
    
}
