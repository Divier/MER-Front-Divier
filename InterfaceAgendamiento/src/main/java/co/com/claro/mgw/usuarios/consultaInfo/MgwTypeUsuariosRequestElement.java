/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgw.usuarios.consultaInfo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement
public class MgwTypeUsuariosRequestElement {
    
    /** N&uacute;mero de c&eacute;dula. */
    @XmlElement
    private String cedula;
    /** Identificador del usuario (LDAP). */
    @XmlElement
    private String usuario;
    

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
