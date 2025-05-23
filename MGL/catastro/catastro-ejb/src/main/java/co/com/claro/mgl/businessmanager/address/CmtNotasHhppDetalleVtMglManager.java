/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.CmtNotasHhppDetalleVtMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtNotasHhppDetalleVtMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class CmtNotasHhppDetalleVtMglManager {

    public List<CmtNotasHhppDetalleVtMgl> findAll() throws ApplicationException {
        List<CmtNotasHhppDetalleVtMgl> resulList;
        CmtNotasHhppDetalleVtMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasHhppDetalleVtMglDaoImpl();
        resulList = cmtNotasDetalleMglDaoImpl.findAll();
        return resulList;
    }

    public List<CmtNotasHhppDetalleVtMgl> findNotasByNotasId(BigDecimal notasId) throws ApplicationException {
        CmtNotasHhppDetalleVtMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasHhppDetalleVtMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.findNotasByNotasId(notasId);
    }

    public CmtNotasHhppDetalleVtMgl create(CmtNotasHhppDetalleVtMgl cmtNotasDetalleMgl, String usuario, int perfil) throws ApplicationException {
        CmtNotasHhppDetalleVtMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasHhppDetalleVtMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.createCm(cmtNotasDetalleMgl, usuario, perfil);
    }

    public CmtNotasHhppDetalleVtMgl update(CmtNotasHhppDetalleVtMgl cmtNotasDetalleMgl) throws ApplicationException {
        CmtNotasHhppDetalleVtMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasHhppDetalleVtMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.update(cmtNotasDetalleMgl);
    }

    public boolean delete(CmtNotasHhppDetalleVtMgl cmtNotasDetalleMgl) throws ApplicationException {
        CmtNotasHhppDetalleVtMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasHhppDetalleVtMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.delete(cmtNotasDetalleMgl);
    }

    public CmtNotasHhppDetalleVtMgl findById(CmtNotasHhppDetalleVtMgl cmtNotasDetalleMgl) throws ApplicationException {
        CmtNotasHhppDetalleVtMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasHhppDetalleVtMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.find(cmtNotasDetalleMgl.getNotasDetalleId());
    }

    public CmtNotasHhppDetalleVtMgl findById(BigDecimal id) throws ApplicationException {
        CmtNotasHhppDetalleVtMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasHhppDetalleVtMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.find(id);
    }
}
