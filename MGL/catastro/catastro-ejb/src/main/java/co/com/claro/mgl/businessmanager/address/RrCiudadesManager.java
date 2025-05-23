/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.RrCiudadesDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RrCiudades;
import java.util.List;

/**
 *
 * @author Parzifal de León
 */
public class RrCiudadesManager {
    RrCiudadesDaoImpl daoImpl = new RrCiudadesDaoImpl();
    public List<RrCiudades> findByCodregional(String codregional){
        List<RrCiudades> unibiList;
        
        unibiList = daoImpl.findByCodregional(codregional);
        return unibiList;        
    }
    
    public RrCiudades findById(String id) throws ApplicationException {
        return daoImpl.find(id);        
    }
    
    public RrCiudades findNombreCiudadByCodigo(String codigo) throws ApplicationException {
        return daoImpl.findNombreCiudadByCodigo(codigo);
    }
    
    
    
    
}
