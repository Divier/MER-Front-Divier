/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.RRDaneDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RRDane;
import java.util.List;

/**
 *
 * @author User
 */
public class RRDaneManager {
    
    public RRDane findByCodCiudad(String codCiudad) throws ApplicationException{
        
        RRDaneDaoImpl daneDaoImpl = new RRDaneDaoImpl();
        
        return daneDaoImpl.findByCodCiudad(codCiudad);
    }
    
   public List<RRDane> findByCodDane(String codigoDane){
        RRDaneDaoImpl daneDaoImpl = new RRDaneDaoImpl();        
        return daneDaoImpl.findByCodDane(codigoDane);
   
   } 
}
