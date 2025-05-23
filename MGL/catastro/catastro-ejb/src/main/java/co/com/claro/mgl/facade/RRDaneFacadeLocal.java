/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RRDane;

/**
 *
 * @author User
 */
public interface RRDaneFacadeLocal {
    
    RRDane findByCodCiudad(String codCiudad) throws ApplicationException;
    
}
