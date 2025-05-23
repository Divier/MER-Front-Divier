/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtSubedificiosSolicitudMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtSubedificiosSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CmtSubedificiosSolicitudMglManager {

    public List<CmtSubedificiosSolicitudMgl> findAll() throws ApplicationException {
        List<CmtSubedificiosSolicitudMgl> resulList;
        CmtSubedificiosSolicitudMglDaoImpl CmtSubedificiosSolicitudMglDaoImpl = new CmtSubedificiosSolicitudMglDaoImpl();
        resulList = CmtSubedificiosSolicitudMglDaoImpl.findAll();
        return resulList;
    }

    public CmtSubedificiosSolicitudMgl create(CmtSubedificiosSolicitudMgl CmtSubedificiosSolicitudMgl) throws ApplicationException {
        CmtSubedificiosSolicitudMglDaoImpl CmtSubedificiosSolicitudMglDaoImpl = new CmtSubedificiosSolicitudMglDaoImpl();
        return CmtSubedificiosSolicitudMglDaoImpl.create(CmtSubedificiosSolicitudMgl);
    }

    public CmtSubedificiosSolicitudMgl update(CmtSubedificiosSolicitudMgl CmtSubedificiosSolicitudMgl) throws ApplicationException {
        CmtSubedificiosSolicitudMglDaoImpl CmtSubedificiosSolicitudMglDaoImpl = new CmtSubedificiosSolicitudMglDaoImpl();
        return CmtSubedificiosSolicitudMglDaoImpl.update(CmtSubedificiosSolicitudMgl);
    }

    public boolean delete(CmtSubedificiosSolicitudMgl CmtSubedificiosSolicitudMgl) throws ApplicationException {
        CmtSubedificiosSolicitudMglDaoImpl CmtSubedificiosSolicitudMglDaoImpl = new CmtSubedificiosSolicitudMglDaoImpl();
        return CmtSubedificiosSolicitudMglDaoImpl.delete(CmtSubedificiosSolicitudMgl);
    }

    public CmtSubedificiosSolicitudMgl findById(CmtSubedificiosSolicitudMgl CmtSubedificiosSolicitudMgl) throws ApplicationException {
        CmtSubedificiosSolicitudMglDaoImpl CmtSubedificiosSolicitudMglDaoImpl = new CmtSubedificiosSolicitudMglDaoImpl();
        return CmtSubedificiosSolicitudMglDaoImpl.find(CmtSubedificiosSolicitudMgl.getIdSubedificiosSol());
    }

    public CmtSubedificiosSolicitudMgl findById(BigDecimal id) throws ApplicationException {
        CmtSubedificiosSolicitudMglDaoImpl CmtSubedificiosSolicitudMglDaoImpl = new CmtSubedificiosSolicitudMglDaoImpl();
        return CmtSubedificiosSolicitudMglDaoImpl.find(id);
    }

    public List<CmtSubedificiosSolicitudMgl> findByIdMultiSol(BigDecimal idMultiSol) throws ApplicationException {
        CmtSubedificiosSolicitudMglDaoImpl CmtSubedificiosSolicitudMglDaoImpl = new CmtSubedificiosSolicitudMglDaoImpl();
        return CmtSubedificiosSolicitudMglDaoImpl.findByIdMultiSol(idMultiSol);
    }
}
