/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.CmtNotasOtHhppDetalleMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtNotasOtHhppDetalleMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class CmtNotasOtHhppDetalleMglManager {

    public List<CmtNotasOtHhppDetalleMgl> findAll() {
        List<CmtNotasOtHhppDetalleMgl> resulList;
        CmtNotasOtHhppDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasOtHhppDetalleMglDaoImpl();
        resulList = cmtNotasDetalleMglDaoImpl.findAll();
        return resulList;
    }

    public List<CmtNotasOtHhppDetalleMgl> findNotasByNotasId(BigDecimal notasId) {
        CmtNotasOtHhppDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasOtHhppDetalleMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.findNotasByNotasId(notasId);
    }

    public CmtNotasOtHhppDetalleMgl create(CmtNotasOtHhppDetalleMgl cmtNotasDetalleMgl, String usuario, int perfil) throws ApplicationException {
        CmtNotasOtHhppDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasOtHhppDetalleMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.createCm(cmtNotasDetalleMgl, usuario, perfil);
    }

    public CmtNotasOtHhppDetalleMgl update(CmtNotasOtHhppDetalleMgl cmtNotasDetalleMgl) throws ApplicationException {
        CmtNotasOtHhppDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasOtHhppDetalleMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.update(cmtNotasDetalleMgl);
    }

    public boolean delete(CmtNotasOtHhppDetalleMgl cmtNotasDetalleMgl) throws ApplicationException {
        CmtNotasOtHhppDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasOtHhppDetalleMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.delete(cmtNotasDetalleMgl);
    }

    public CmtNotasOtHhppDetalleMgl findById(CmtNotasOtHhppDetalleMgl cmtNotasDetalleMgl) throws ApplicationException {
        CmtNotasOtHhppDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasOtHhppDetalleMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.find(cmtNotasDetalleMgl.getNotasDetalleId());
    }

    public CmtNotasOtHhppDetalleMgl findById(BigDecimal id) throws ApplicationException {
        CmtNotasOtHhppDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasOtHhppDetalleMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.find(id);
    }
}
