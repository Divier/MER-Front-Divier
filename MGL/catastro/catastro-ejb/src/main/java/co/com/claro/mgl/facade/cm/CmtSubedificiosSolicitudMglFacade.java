/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

/**
 *
 * @author Admin
 */
import co.com.claro.mgl.businessmanager.cm.CmtSubedificiosSolicitudMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtSubedificiosSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class CmtSubedificiosSolicitudMglFacade implements CmtSubedificiosSolicitudMglFacadeLocal {

    @Override
    public List<CmtSubedificiosSolicitudMgl> findAll() throws ApplicationException {
        CmtSubedificiosSolicitudMglManager cmtSubedificiosSolicitudMglManager = new CmtSubedificiosSolicitudMglManager();
        return cmtSubedificiosSolicitudMglManager.findAll();
    }

    @Override
    public CmtSubedificiosSolicitudMgl create(CmtSubedificiosSolicitudMgl t) throws ApplicationException {
        CmtSubedificiosSolicitudMglManager cmtSubedificiosSolicitudMglManager = new CmtSubedificiosSolicitudMglManager();
        return cmtSubedificiosSolicitudMglManager.create(t);
    }

    @Override
    public CmtSubedificiosSolicitudMgl update(CmtSubedificiosSolicitudMgl t) throws ApplicationException {
        CmtSubedificiosSolicitudMglManager cmtSubedificiosSolicitudMglManager = new CmtSubedificiosSolicitudMglManager();
        return cmtSubedificiosSolicitudMglManager.update(t);
    }

    @Override
    public boolean delete(CmtSubedificiosSolicitudMgl t) throws ApplicationException {
        CmtSubedificiosSolicitudMglManager cmtSubedificiosSolicitudMglManager = new CmtSubedificiosSolicitudMglManager();
        return cmtSubedificiosSolicitudMglManager.delete(t);
    }

    @Override
    public CmtSubedificiosSolicitudMgl findById(CmtSubedificiosSolicitudMgl sqlData) throws ApplicationException {
        CmtSubedificiosSolicitudMglManager cmtSubedificiosSolicitudMglManager = new CmtSubedificiosSolicitudMglManager();
        return cmtSubedificiosSolicitudMglManager.findById(sqlData);
    }

    @Override
    public List<CmtSubedificiosSolicitudMgl> findByIdMultiSol(BigDecimal idMultiSol) throws ApplicationException {
        CmtSubedificiosSolicitudMglManager cmtSubedificiosSolicitudMglManager = new CmtSubedificiosSolicitudMglManager();
        return cmtSubedificiosSolicitudMglManager.findByIdMultiSol(idMultiSol);
    }
}
