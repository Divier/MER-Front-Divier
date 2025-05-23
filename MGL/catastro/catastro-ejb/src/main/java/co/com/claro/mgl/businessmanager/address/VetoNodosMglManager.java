/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.VetoNodosMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.VetoNodosMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class VetoNodosMglManager {
    
    VetoNodosMglDaoImpl vetoNodosDaoImpl = new VetoNodosMglDaoImpl();

    public VetoNodosMgl createVetoNodos(VetoNodosMgl vetoNodosMgl, 
            String usuario, int perfil) throws ApplicationException {
        VetoNodosMgl vetoNodos = vetoNodosDaoImpl.createCm(vetoNodosMgl,
                usuario, perfil);
        return vetoNodos;
    }
    
    public List<BigDecimal> findVetoNodoByIdNodo(BigDecimal idNodo) 
            throws ApplicationException {
        List<BigDecimal> result;
        result = vetoNodosDaoImpl.findVetoNodoByIdNodo(idNodo);
        return result;
    }
    
    public List<BigDecimal> findVetoNodoByCodigoNodo(String codigoNodo) 
            throws ApplicationException {
        List<BigDecimal> result;
        result = vetoNodosDaoImpl.findVetoNodoByCodigoNodo(codigoNodo);
        return result;
    }
}
