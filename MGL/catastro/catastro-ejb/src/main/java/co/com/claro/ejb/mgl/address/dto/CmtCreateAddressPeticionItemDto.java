/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.ejb.mgl.address.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase para encapula valores de creacion de dirreccion
 * @author villamilc hitss
 */
@XmlRootElement(name = "cmtCreateAddressPeticionItem")
public class CmtCreateAddressPeticionItemDto {   
    @XmlElement
    private long itemId;
    @XmlElement
    private String populatedCenterDaneCode;
    @XmlElement
    private CmtSplitAddressMglDto splitAddressMglDto;
   /*
    * getItemId
    * @return int
    */ 
    public long getItemId() {
        return itemId;
    }

   /*
    * setItemId
    * @Param itemId
    */ 
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
   /*
    * getPopulatedCenterDaneCode
    * @return String
    */ 

    public String getPopulatedCenterDaneCode() {
        return populatedCenterDaneCode;
    }

   /*
    * setPopulatedCenterDaneCode
    * @Param populatedCenterDaneCode
    */ 
    public void setPopulatedCenterDaneCode(String populatedCenterDaneCode) {
        this.populatedCenterDaneCode = populatedCenterDaneCode;
    }
   /*
    * getSplitAddressMglDto
    * @return CmtSplitAddressMglDto
    */ 

    public CmtSplitAddressMglDto getSplitAddressMglDto() {
        return splitAddressMglDto;
    }

   /*
    * setSplitAddressMglDto
    * @Param CmtSplitAddressMglDto
    */ 
    public void setSplitAddressMglDto(CmtSplitAddressMglDto splitAddressMglDto) {
        this.splitAddressMglDto = splitAddressMglDto;
    }
    
    
}
