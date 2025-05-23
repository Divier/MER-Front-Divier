/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.ejb.mgl.address.dto;

import co.com.claro.mgl.dtos.CmtDefaultBasicResquest;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase para encapsular los valores de reques te multiple caeacion de direcciones.
 * @author villamilc hitss
 */
@XmlRootElement(name = "cmtCreateAddressRequest")
public class CmtCreateAddressRequestDto extends CmtDefaultBasicResquest{
    @XmlElement
    private List<CmtCreateAddressPeticionItemDto> cmtCreateAddressDtoList;

   /*
    * getCmtCreateAddressDtoList
    * @return cmtCreateAddressDtoList
    */     
    public List<CmtCreateAddressPeticionItemDto> getCmtCreateAddressDtoList() {
        if(cmtCreateAddressDtoList==null){
            cmtCreateAddressDtoList=new ArrayList<CmtCreateAddressPeticionItemDto>(9);
        }
        return cmtCreateAddressDtoList;
    }

   /*
    * setCmtCreateAddressDtoList
    * @Param cmtCreateAddressDtoList
    */     
    public void setCmtCreateAddressDtoList(List<CmtCreateAddressPeticionItemDto> cmtCreateAddressDtoList) {
        this.cmtCreateAddressDtoList = cmtCreateAddressDtoList;
    }
}
