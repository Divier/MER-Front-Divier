/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtTipoCompaniaMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoCompaniaMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class CmtTipoCompaniaMglFacade implements CmtTipoCompaniaMglFacadeLocal {

    @Override
    public List<CmtTipoCompaniaMgl> findAll() throws ApplicationException {
        CmtTipoCompaniaMglManager cmtTipoCompaniaMglManager = new CmtTipoCompaniaMglManager();
        return cmtTipoCompaniaMglManager.findAll();
    }

    @Override
    public CmtTipoCompaniaMgl create(CmtTipoCompaniaMgl t) throws ApplicationException {
        CmtTipoCompaniaMglManager cmtTipoCompaniaMglManager = new CmtTipoCompaniaMglManager();
        return cmtTipoCompaniaMglManager.create(t);
    }

    @Override
    public CmtTipoCompaniaMgl update(CmtTipoCompaniaMgl t) throws ApplicationException {
        CmtTipoCompaniaMglManager cmtTipoCompaniaMglManager = new CmtTipoCompaniaMglManager();
        return cmtTipoCompaniaMglManager.update(t);
    }

    @Override
    public boolean delete(CmtTipoCompaniaMgl t) throws ApplicationException {
        CmtTipoCompaniaMglManager cmtTipoCompaniaMglManager = new CmtTipoCompaniaMglManager();
        return cmtTipoCompaniaMglManager.delete(t);
    }

    @Override
    public CmtTipoCompaniaMgl findById(CmtTipoCompaniaMgl sqlData) throws ApplicationException {
        CmtTipoCompaniaMglManager cmtTipoCompaniaMglManager = new CmtTipoCompaniaMglManager();
        return cmtTipoCompaniaMglManager.findById(sqlData);
    }
}
