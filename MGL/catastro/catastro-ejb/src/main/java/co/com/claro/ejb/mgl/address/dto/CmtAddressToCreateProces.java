/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.ejb.mgl.address.dto;

import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.telmex.catastro.data.AddressGeodata;

/**
 * Clase para manejar las direcciones a crear con mas info que la extendida.
 * @author villamilc hitss
 */
public class CmtAddressToCreateProces extends CmtCreateAddressPeticionItemDto{
    private AddressGeodata addressGeodata= new AddressGeodata(); 
    private GeograficoPoliticoMgl geograficoPoliticoMgl;
    private boolean noProcess=false;
    private String message;

    /*
     * CmtAddressToCreateProces Constructor
     * @return CmtAddressToCreateProces
     */    
    public CmtAddressToCreateProces(CmtCreateAddressPeticionItemDto addressPeticionItemDto) {
        this.setItemId(addressPeticionItemDto.getItemId());
        this.setPopulatedCenterDaneCode(addressPeticionItemDto.getPopulatedCenterDaneCode());
        this.setSplitAddressMglDto(addressPeticionItemDto.getSplitAddressMglDto());
    }

    
    
    /*
     * getAddressService
     * @return int
     */
    public AddressGeodata getAddressGeodata() {
        return addressGeodata;
    }
   /*
    * setAddressService
    * @Param addressService
    */ 
    public void setAddressGeodata(AddressGeodata addressGeodata) {
        this.addressGeodata = addressGeodata;
    }
    /*
     * getGeograficoPoliticoMgl
     * @return geograficoPoliticoMgl
     */
    public GeograficoPoliticoMgl getGeograficoPoliticoMgl() {
        return geograficoPoliticoMgl;
    }
   /*
    * setGeograficoPoliticoMgl
    * @Param geograficoPoliticoMgl
    */ 
    public void setGeograficoPoliticoMgl(GeograficoPoliticoMgl geograficoPoliticoMgl) {
        this.geograficoPoliticoMgl = geograficoPoliticoMgl;
    }
    /*
     * isNoProcess
     * @return noProcess
     */
    public boolean isNoProcess() {
        return noProcess;
    }
   /*
    * setNoProcess
    * @Param noProcess
    */ 
    public void setNoProcess(boolean noProcess) {
        this.noProcess = noProcess;
    }
    /*
     * getMessage
     * @return message
     */
    public String getMessage() {
        return message;
    }
   /*
    * setMessage
    * @Param message
    */ 
    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
