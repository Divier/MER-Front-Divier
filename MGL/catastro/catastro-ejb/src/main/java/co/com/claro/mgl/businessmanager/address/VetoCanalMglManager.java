/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.VetoCanalMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.VetoCanalMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class VetoCanalMglManager {
    
    VetoCanalMglDaoImpl vetoCanalMglDaoImpl = new VetoCanalMglDaoImpl();

    public VetoCanalMgl createVetoCanal(VetoCanalMgl vetoCanalMgl, 
            String usuario, int perfil) throws ApplicationException {
        VetoCanalMgl vetoCanal = vetoCanalMglDaoImpl.createCm(vetoCanalMgl, usuario, perfil);
        return vetoCanal;
    }
    
        public List<BigDecimal> findVetoCanalByIdCanalParametro(String idCanalParametro) 
            throws ApplicationException {
        List<BigDecimal> result;
        result = vetoCanalMglDaoImpl.findVetoCanalByIdCanalParametro(idCanalParametro);
        return result;
    }
}
