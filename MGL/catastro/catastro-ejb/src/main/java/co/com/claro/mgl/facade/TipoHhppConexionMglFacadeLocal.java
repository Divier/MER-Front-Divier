/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoHhppConexionMgl;
import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public interface TipoHhppConexionMglFacadeLocal extends BaseFacadeLocal<TipoHhppConexionMgl> {
  
public TipoHhppConexionMgl findById(BigDecimal idDecimal) throws ApplicationException;
    
}
