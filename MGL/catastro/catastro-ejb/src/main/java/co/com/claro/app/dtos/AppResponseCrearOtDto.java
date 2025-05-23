/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.app.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement(name = "appResponseCrearOtDto")
public class AppResponseCrearOtDto extends CmtDefaultBasicResponse {

    @XmlElement
    private BigDecimal idOt;
    @XmlElement
    private String otLocation;

    public BigDecimal getIdOt() {
        return idOt;
    }

    public void setIdOt(BigDecimal idOt) {
        this.idOt = idOt;
    }

    public String getOtLocation() {
        return otLocation;
    }

    public void setOtLocation(String otLocation) {
        this.otLocation = otLocation;
    }
    
    


    
    
    
    
}
