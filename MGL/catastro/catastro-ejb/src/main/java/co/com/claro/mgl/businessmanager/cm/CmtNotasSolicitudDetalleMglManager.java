/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtNotasSolicitudDetalleMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudDetalleMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CmtNotasSolicitudDetalleMglManager {

    public List<CmtNotasSolicitudDetalleMgl> findAll() throws ApplicationException {
        List<CmtNotasSolicitudDetalleMgl> resulList;
        CmtNotasSolicitudDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasSolicitudDetalleMglDaoImpl();
        resulList = cmtNotasDetalleMglDaoImpl.findAll();
        return resulList;
    }

    public List<CmtNotasSolicitudDetalleMgl> findNotasByNotasId(BigDecimal notasId) throws ApplicationException {
        CmtNotasSolicitudDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasSolicitudDetalleMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.findNotasByNotasId(notasId);
    }

    public CmtNotasSolicitudDetalleMgl create(CmtNotasSolicitudDetalleMgl cmtNotasDetalleMgl, String usuario, int perfil) throws ApplicationException {
        CmtNotasSolicitudDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasSolicitudDetalleMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.createCm(cmtNotasDetalleMgl, usuario, perfil);
    }

    public CmtNotasSolicitudDetalleMgl update(CmtNotasSolicitudDetalleMgl cmtNotasDetalleMgl) throws ApplicationException {
        CmtNotasSolicitudDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasSolicitudDetalleMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.update(cmtNotasDetalleMgl);
    }

    public boolean delete(CmtNotasSolicitudDetalleMgl cmtNotasDetalleMgl) throws ApplicationException {
        CmtNotasSolicitudDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasSolicitudDetalleMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.delete(cmtNotasDetalleMgl);
    }

    public CmtNotasSolicitudDetalleMgl findById(CmtNotasSolicitudDetalleMgl cmtNotasDetalleMgl) throws ApplicationException {
        CmtNotasSolicitudDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasSolicitudDetalleMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.find(cmtNotasDetalleMgl.getNotasDetalleId());
    }

    public CmtNotasSolicitudDetalleMgl findById(BigDecimal id) throws ApplicationException {
        CmtNotasSolicitudDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasSolicitudDetalleMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.find(id);
    }
}
