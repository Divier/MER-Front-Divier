
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtVigenciaCostoItemMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtItemMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVigenciaCostoItemMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class CmtVigenciaCostoItemMglFacade implements CmtVigenciaCostoItemMglFacadeLocal {

    @Override
    public CmtVigenciaCostoItemMgl findByItemVigencia(CmtItemMgl itemObj) throws ApplicationException {
        CmtVigenciaCostoItemMglManager manager = new CmtVigenciaCostoItemMglManager();
        return manager.findByItemVigencia(itemObj);
    }

    @Override
    public List<CmtVigenciaCostoItemMgl> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtVigenciaCostoItemMgl create(CmtVigenciaCostoItemMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtVigenciaCostoItemMgl update(CmtVigenciaCostoItemMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CmtVigenciaCostoItemMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtVigenciaCostoItemMgl findById(CmtVigenciaCostoItemMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
