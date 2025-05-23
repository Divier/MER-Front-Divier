/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResquest;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement(name = "requestConstruccionDireccion")
public class CmtRequestConstruccionDireccionDto extends  CmtDefaultBasicResquest{
    
    @XmlElement
    private DrDireccion drDireccion;
    @XmlElement
    private String direccionStr;
    @XmlElement
    private String comunidad;
    @XmlElement
    private String barrio;
    @XmlElement
    private String tipoAdicion;//P-pincipal,C-Complemento,A-apartamento,N-Nada
    @XmlElement
    private String tipoNivel;
    @XmlElement
    private String valorNivel;
    @XmlElement
    private String idUsuario;

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    public String getDireccionStr() {
        return direccionStr;
    }

    public void setDireccionStr(String direccionStr) {
        this.direccionStr = direccionStr;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getTipoAdicion() {
        return tipoAdicion;
    }

    public void setTipoAdicion(String tipoAdicion) {
        this.tipoAdicion = tipoAdicion;
    }

    public String getTipoNivel() {
        return tipoNivel;
    }

    public void setTipoNivel(String tipoNivel) {
        this.tipoNivel = tipoNivel;
    }

    public String getValorNivel() {
        return valorNivel;
    }

    public void setValorNivel(String valorNivel) {
        this.valorNivel = valorNivel;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
