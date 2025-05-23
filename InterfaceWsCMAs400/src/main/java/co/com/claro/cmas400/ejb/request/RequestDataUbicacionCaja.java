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
public class RequestDataUbicacionCaja {

    @XmlElement
    private String userId = "";
    @XmlElement
    private String codigo = "";
    @XmlElement
    private String nombre = "";

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

    public String getUserId(){
      return userId;
    }

    public void setUserId(String userId){
      this.userId = userId;
    }
        
}
