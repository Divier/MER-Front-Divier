/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.RRDaneManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RRDane;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class RRDaneFacade implements RRDaneFacadeLocal{

    @Override
    public RRDane findByCodCiudad(String codCiudad) throws ApplicationException {
        RRDaneManager daneManager = new RRDaneManager();
        return daneManager.findByCodCiudad(codCiudad);
    }
    
}
