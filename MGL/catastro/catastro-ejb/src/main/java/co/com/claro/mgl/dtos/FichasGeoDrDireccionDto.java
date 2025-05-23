package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.telmex.catastro.data.AddressService;


public class FichasGeoDrDireccionDto {
     
    private DrDireccion drDireccion;
    private AddressService responseGeo;
    
    public FichasGeoDrDireccionDto(){
        
    }

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    public AddressService getResponseGeo() {
        return responseGeo;
    }

    public void setResponseGeo(AddressService responseGeo) {
        this.responseGeo = responseGeo;
    }
    
    
    
}
