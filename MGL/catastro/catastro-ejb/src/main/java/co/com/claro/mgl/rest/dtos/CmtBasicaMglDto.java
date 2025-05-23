/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jrodriguez
 */

@XmlRootElement(name = "cmtBasicaMgl")
public class CmtBasicaMglDto {

    @XmlElement
    private String codigoTipo;
    @XmlElement
    private String nombreTipo;
    @XmlElement
    private String codigoBasica;
    @XmlElement
    private String abreviatura;
    @XmlElement
    private String nombreBasica;


    public String getCodigoTipo() {
        return codigoTipo;
    }

    public void setCodigoTipo(String codigoTipo) {
        this.codigoTipo = codigoTipo;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public String getCodigoBasica() {
        return codigoBasica;
    }

    public void setCodigoBasica(String codigoBasica) {
        this.codigoBasica = codigoBasica;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getNombreBasica() {
        return nombreBasica;
    }

    public void setNombreBasica(String nombreBasica) {
        this.nombreBasica = nombreBasica;
    }    

}
