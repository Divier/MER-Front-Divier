/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResquest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que contiene la propiedad del nombre de usuario para entrada de la operacion consultarTokenUsuario
 *
 * @author Yasser Leon
 */
@XmlRootElement(name = "tokenUsuarioRequest")
public class TokenUsuarioRequestDto extends CmtDefaultBasicResquest {

    @XmlElement
    String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
