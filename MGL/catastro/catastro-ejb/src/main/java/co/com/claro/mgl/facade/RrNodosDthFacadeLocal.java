/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RrNodosDth;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public interface RrNodosDthFacadeLocal {

   public List<RrNodosDth> findNodosDthByCodCiudad(String codCiudad) throws ApplicationException;
}
