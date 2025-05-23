/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtNotasSolicitudDetalleVtMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudDetalleVtMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class CmtNotasSolicitudDetalleVtMglManager {

    public List<CmtNotasSolicitudDetalleVtMgl> findAll() throws ApplicationException {
        List<CmtNotasSolicitudDetalleVtMgl> resulList;
        CmtNotasSolicitudDetalleVtMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasSolicitudDetalleVtMglDaoImpl();
        resulList = cmtNotasDetalleMglDaoImpl.findAll();
        return resulList;
    }

    public List<CmtNotasSolicitudDetalleVtMgl> findNotasByNotasId(BigDecimal notasId) throws ApplicationException {
        CmtNotasSolicitudDetalleVtMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasSolicitudDetalleVtMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.findNotasByNotasId(notasId);
    }

    public CmtNotasSolicitudDetalleVtMgl create(CmtNotasSolicitudDetalleVtMgl cmtNotasDetalleMgl, String usuario, int perfil) throws ApplicationException {
        CmtNotasSolicitudDetalleVtMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasSolicitudDetalleVtMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.createCm(cmtNotasDetalleMgl, usuario, perfil);
    }

    public CmtNotasSolicitudDetalleVtMgl update(CmtNotasSolicitudDetalleVtMgl cmtNotasDetalleMgl) throws ApplicationException {
        CmtNotasSolicitudDetalleVtMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasSolicitudDetalleVtMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.update(cmtNotasDetalleMgl);
    }

    public boolean delete(CmtNotasSolicitudDetalleVtMgl cmtNotasDetalleMgl) throws ApplicationException {
        CmtNotasSolicitudDetalleVtMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasSolicitudDetalleVtMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.delete(cmtNotasDetalleMgl);
    }

    public CmtNotasSolicitudDetalleVtMgl findById(CmtNotasSolicitudDetalleVtMgl cmtNotasDetalleMgl) throws ApplicationException {
        CmtNotasSolicitudDetalleVtMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasSolicitudDetalleVtMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.find(cmtNotasDetalleMgl.getNotasDetalleId());
    }

    public CmtNotasSolicitudDetalleVtMgl findById(BigDecimal id) throws ApplicationException {
        CmtNotasSolicitudDetalleVtMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasSolicitudDetalleVtMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.find(id);
    }
}
