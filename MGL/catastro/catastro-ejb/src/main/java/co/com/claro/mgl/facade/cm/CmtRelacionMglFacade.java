
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtRelacionMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtRelacionMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class CmtRelacionMglFacade implements CmtRelacionMglFacadeLocal {
    
    @Override
    public List<CmtRelacionMgl> findAll() throws ApplicationException {
        CmtRelacionMglManager cmtRelacionMglManager = new CmtRelacionMglManager();
        return cmtRelacionMglManager.findAll();
    }

    @Override
    public List<CmtRelacionMgl> findRelacionId(BigDecimal cuentaMatrizId) throws ApplicationException {
        CmtRelacionMglManager cmtRelacionMglManager = new CmtRelacionMglManager();
        return cmtRelacionMglManager.findRelacionId(cuentaMatrizId);
    }

    @Override
    public CmtRelacionMgl create(CmtRelacionMgl cmtRelacionMgl) throws ApplicationException {
        CmtRelacionMglManager cmtRelacionMglManager = new CmtRelacionMglManager();
        return cmtRelacionMglManager.create(cmtRelacionMgl);
    }

    @Override
    public CmtRelacionMgl update(CmtRelacionMgl cmtRelacionMgl) throws ApplicationException {
        CmtRelacionMglManager cmtRelacionMglManager = new CmtRelacionMglManager();
        return cmtRelacionMglManager.update(cmtRelacionMgl);
    }

    @Override
    public boolean delete(CmtRelacionMgl cmtRelacionMgl) throws ApplicationException {
               CmtRelacionMglManager cmtRelacionMglManager = new CmtRelacionMglManager();
        return cmtRelacionMglManager.delete(cmtRelacionMgl);
    }

    @Override
    public CmtRelacionMgl findById(CmtRelacionMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
