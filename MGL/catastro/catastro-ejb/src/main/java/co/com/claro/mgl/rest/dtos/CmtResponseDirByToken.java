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
 * @author cardenaslb
 */
@XmlRootElement(name = "cmtResponseDirByToken")
public class CmtResponseDirByToken {
    
    
    
    
    @XmlElement
    private BigDecimal direccionDetalladaId;

    public BigDecimal getDireccionDetalladaId() {
        return direccionDetalladaId;
    }

    public void setDireccionDetalladaId(BigDecimal direccionDetalladaId) {
        this.direccionDetalladaId = direccionDetalladaId;
    }

 
    
    
    
    
}
