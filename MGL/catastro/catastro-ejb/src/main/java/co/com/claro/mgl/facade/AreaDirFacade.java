
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.AreaDirManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AreaDir;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class AreaDirFacade implements AreaDirFacadeLocal{

    @Override
    public List<AreaDir> findByIdDivisional(BigDecimal idDivisional) throws ApplicationException {
        AreaDirManager areaDirManager = new AreaDirManager();
        return areaDirManager.findByIdDiv(idDivisional);
    }

    @Override
    public List<AreaDir> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AreaDir create(AreaDir t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AreaDir update(AreaDir t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(AreaDir t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AreaDir findById(AreaDir sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
