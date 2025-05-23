/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtNotasDetalleMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasDetalleAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasDetalleMgl;
import co.com.claro.mgl.utils.UtilsCMAuditoria;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CmtNotasDetalleMglManager {

    public List<CmtNotasDetalleMgl> findAll() throws ApplicationException {
        List<CmtNotasDetalleMgl> resulList;
        CmtNotasDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasDetalleMglDaoImpl();
        resulList = cmtNotasDetalleMglDaoImpl.findAll();
        return resulList;
    }

    public List<CmtNotasDetalleMgl> findNotasByNotasId(BigDecimal notasId) throws ApplicationException {
        CmtNotasDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasDetalleMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.findNotasByNotasId(notasId);
    }

    public CmtNotasDetalleMgl create(CmtNotasDetalleMgl cmtNotasDetalleMgl, String usuario, int perfil) throws ApplicationException {
        CmtNotasDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasDetalleMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.createCm(cmtNotasDetalleMgl, usuario, perfil);
    }

    public CmtNotasDetalleMgl update(CmtNotasDetalleMgl cmtNotasDetalleMgl) throws ApplicationException {
        CmtNotasDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasDetalleMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.update(cmtNotasDetalleMgl);
    }

    public boolean delete(CmtNotasDetalleMgl cmtNotasDetalleMgl) throws ApplicationException {
        CmtNotasDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasDetalleMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.delete(cmtNotasDetalleMgl);
    }

    public CmtNotasDetalleMgl findById(CmtNotasDetalleMgl cmtNotasDetalleMgl) throws ApplicationException {
        CmtNotasDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasDetalleMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.find(cmtNotasDetalleMgl.getNotasDetalleId());
    }

    public CmtNotasDetalleMgl findById(BigDecimal id) throws ApplicationException {
        CmtNotasDetalleMglDaoImpl cmtNotasDetalleMglDaoImpl = new CmtNotasDetalleMglDaoImpl();
        return cmtNotasDetalleMglDaoImpl.find(id);
    }

    public List<AuditoriaDto> construirAuditoria(CmtNotasDetalleMgl cmtNotasDetalleMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UtilsCMAuditoria<CmtNotasDetalleMgl, CmtNotasDetalleAuditoriaMgl> utilsCMAuditoria = new UtilsCMAuditoria<CmtNotasDetalleMgl, CmtNotasDetalleAuditoriaMgl>();
        List<AuditoriaDto> listAuditoriaDto = utilsCMAuditoria.construirAuditoria(cmtNotasDetalleMgl);
        return listAuditoriaDto;

    }
}
