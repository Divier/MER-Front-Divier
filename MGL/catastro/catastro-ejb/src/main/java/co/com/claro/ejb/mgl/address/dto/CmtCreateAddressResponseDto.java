/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.ejb.mgl.address.dto;

import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que encapsula el listado de direcciones creadas.
 * @author villamilc hitss
 */
@XmlRootElement(name = "cmtCreateAddressResponse")
public class CmtCreateAddressResponseDto extends CmtDefaultBasicResponse{
    @XmlElement
    private List<CmtCreateAddressResultadoItemDto> cmtCreateAddressResultadoItemDto;  

   /*
    * getCmtCreateAddressResultadoItemDto
    * @return cmtCreateAddressResultadoItemDto
    */     
    public List<CmtCreateAddressResultadoItemDto> getCmtCreateAddressResultadoItemDto() {
        return cmtCreateAddressResultadoItemDto;
    }
   /*
    * setCmtCreateAddressResultadoItemDto
    * @Param cmtCreateAddressResultadoItemDto
    */ 
    public void setCmtCreateAddressResultadoItemDto(List<CmtCreateAddressResultadoItemDto> cmtCreateAddressResultadoItemDto) {
        this.cmtCreateAddressResultadoItemDto = cmtCreateAddressResultadoItemDto;
    }
    
}
