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
 * @author Orlando Velasquez
 */
@XmlRootElement(name = "geograficoPolitico")
public class GeograficoPoliticoDto {

    @XmlElement
    private GpoCentroPlobladoMglDto centroPoblado;

    public GpoCentroPlobladoMglDto getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(GpoCentroPlobladoMglDto centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

}
