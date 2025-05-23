/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.AreaDirDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AreaDir;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author User
 */
public class AreaDirManager {
    
    public List<AreaDir> findByIdDiv(BigDecimal divId) throws ApplicationException{       
        AreaDirDaoImpl areaDirDaoImpl = new AreaDirDaoImpl();        
        return areaDirDaoImpl.findByIdDiv(divId);
    }
    
}
