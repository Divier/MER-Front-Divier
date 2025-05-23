/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement(name = "mglRequestGeoPoliticoDto")
public class MglRequestGeoPoliticoDto {
    
    @XmlElement
    private BigDecimal idGeoPol;

    public BigDecimal getIdGeoPol() {
        return idGeoPol;
    }

    public void setIdGeoPol(BigDecimal idGeoPol) {
        this.idGeoPol = idGeoPol;
    }
    
}
