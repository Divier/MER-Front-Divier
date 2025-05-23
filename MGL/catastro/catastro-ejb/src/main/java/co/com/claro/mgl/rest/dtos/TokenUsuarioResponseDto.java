/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que contiene la propiedad del token de seguridad para salida de la operacion consultarTokenUsuario
 *
 * @author Yasser Leon
 */
@XmlRootElement(name = "tokenUsuarioResponse")
public class TokenUsuarioResponseDto extends CmtDefaultBasicResponse {

    @XmlElement
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
