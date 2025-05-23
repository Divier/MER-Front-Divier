/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AreaDir;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author User
 */
public interface AreaDirFacadeLocal extends BaseFacadeLocal<AreaDir>{
    
    List<AreaDir> findByIdDivisional(BigDecimal idDivisional) throws ApplicationException;
    
}
