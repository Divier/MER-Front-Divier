/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtRelacionMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtRelacionMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *Fabian Barrera
 * @author User
 */
public class CmtRelacionMglManager {
    
    public List<CmtRelacionMgl> findAll() throws ApplicationException {
        List<CmtRelacionMgl> resulList;
        CmtRelacionMglDaoImpl cmtRelacionMglDaoImpl = new CmtRelacionMglDaoImpl();
        resulList = cmtRelacionMglDaoImpl.findAll();
        return resulList;
    }

    public List<CmtRelacionMgl> findRelacionId(BigDecimal cuentaMatrizId) throws ApplicationException {
        CmtRelacionMglDaoImpl cmtRelacionMglDaoImpl = new CmtRelacionMglDaoImpl();
        return cmtRelacionMglDaoImpl.findRelacionId(cuentaMatrizId);
    }

    public CmtRelacionMgl create(CmtRelacionMgl cmtRelacionMgl) throws ApplicationException {
        CmtRelacionMglDaoImpl cmtRelacionMglDaoImpl = new CmtRelacionMglDaoImpl();
        return cmtRelacionMglDaoImpl.create(cmtRelacionMgl);
    }

    public CmtRelacionMgl update(CmtRelacionMgl cmtRelacionMgl) throws ApplicationException {
        CmtRelacionMglDaoImpl cmtRelacionMglDaoImpl = new CmtRelacionMglDaoImpl();
        return cmtRelacionMglDaoImpl.update(cmtRelacionMgl);
    }

    public boolean delete(CmtRelacionMgl cmtRelacionMgl) throws ApplicationException {
        CmtRelacionMglDaoImpl cmtRelacionMglDaoImpl = new CmtRelacionMglDaoImpl();
        return cmtRelacionMglDaoImpl.delete(cmtRelacionMgl);
    }

    public CmtRelacionMgl findById(CmtRelacionMgl cmtRelacionMgl) throws ApplicationException {
        CmtRelacionMglDaoImpl cmtRelacionMglDaoImpl = new CmtRelacionMglDaoImpl();
        return cmtRelacionMglDaoImpl.find(cmtRelacionMgl.getRelacionId());
    }
}
