/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.ejb.mgl.address.dto;

import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que encapsula la direccion creada.
 * @author villamilc hitss
 */
@XmlRootElement(name = "cmtCreateAddressResultadoItem")
public class CmtCreateAddressResultadoItemDto {
    @XmlElement
    private int itemId;
    @XmlElement
    private CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl;
   /*
    * getItemId
    * @return int
    */ 
    public int getItemId() {
        return itemId;
    }
   /*
    * setItemId
    * @Param itemId
    */ 
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
   /*
    * getCmtDireccionDetalladaMgl
    * @return cmtDireccionDetalladaMgl
    */ 
    public CmtDireccionDetalladaMgl getCmtDireccionDetalladaMgl() {
        return cmtDireccionDetalladaMgl;
    }
   /*
    * setCmtDireccionDetalladaMgl
    * @Param cmtDireccionDetalladaMgl
    */ 
    public void setCmtDireccionDetalladaMgl(CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl) {
        this.cmtDireccionDetalladaMgl = cmtDireccionDetalladaMgl;
    }
}
