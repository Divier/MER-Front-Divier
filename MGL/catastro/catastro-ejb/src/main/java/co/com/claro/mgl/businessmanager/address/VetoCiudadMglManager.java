/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.VetoCiudadMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.VetoCiudadMgl;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class VetoCiudadMglManager {
    
    VetoCiudadMglDaoImpl vetoCiudadDaoImpl = new VetoCiudadMglDaoImpl();

    public VetoCiudadMgl createVetoCiudad(VetoCiudadMgl vetoCiudadMgl, 
            String usuario, int perfil) throws ApplicationException {
        VetoCiudadMgl vetoCiudad = vetoCiudadDaoImpl.createCm(vetoCiudadMgl, usuario, perfil);
        return vetoCiudad;
    }
    
    public List<BigDecimal> findVetoCiudadByIdGpo(BigDecimal idGpo) throws ApplicationException {
        List<BigDecimal> result;
        result = vetoCiudadDaoImpl.findVetoCiudadByIdGpo(idGpo);
        return result;
    }
    
    
}
