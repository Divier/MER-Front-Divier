/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResquest;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */ 
@XmlRootElement(name = "indicacionesRequest")
public class CmtIndicacionesRequestDto extends CmtDefaultBasicResquest {
    
    @XmlElement
    private BigDecimal idDetallada;
    
    @XmlElement
    private String indicacion;

    public BigDecimal getIdDetallada() {
        return idDetallada;
    }

    public void setIdDetallada(BigDecimal idDetallada) {
        this.idDetallada = idDetallada;
    }

    public String getIndicacion() {
        return indicacion;
    }

    public void setIndicacion(String indicacion) {
        this.indicacion = indicacion;
    }
}
