/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtNotasSolicitudDetalleVtMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudDetalleVtMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 *@author Juan David Hernandez
 */
@Stateless
public class CmtNotasSolicitudDetalleVtMglFacade implements CmtNotasSolicitudDetalleVtMglFacadeLocal {
    
    private String user = "";
    private int perfil = 0;

    @Override
    public List<CmtNotasSolicitudDetalleVtMgl> findAll() throws ApplicationException {
        CmtNotasSolicitudDetalleVtMglManager cmtNotasDetalleMglMglManager = new CmtNotasSolicitudDetalleVtMglManager();
        return cmtNotasDetalleMglMglManager.findAll();
    }

    @Override
    public List<CmtNotasSolicitudDetalleVtMgl> findNotasByNotasId(BigDecimal notasId) throws ApplicationException {
        CmtNotasSolicitudDetalleVtMglManager cmtNotasDetalleMglMglManager = new CmtNotasSolicitudDetalleVtMglManager();
        return cmtNotasDetalleMglMglManager.findNotasByNotasId(notasId);
    }

    @Override
    public CmtNotasSolicitudDetalleVtMgl create(CmtNotasSolicitudDetalleVtMgl t) throws ApplicationException {
        CmtNotasSolicitudDetalleVtMglManager cmtNotasDetalleMglMglManager = new CmtNotasSolicitudDetalleVtMglManager();
        return cmtNotasDetalleMglMglManager.create(t, user, perfil);
    }

    @Override
    public CmtNotasSolicitudDetalleVtMgl update(CmtNotasSolicitudDetalleVtMgl t) throws ApplicationException {
        CmtNotasSolicitudDetalleVtMglManager cmtNotasDetalleMglMglManager = new CmtNotasSolicitudDetalleVtMglManager();
        return cmtNotasDetalleMglMglManager.update(t);
    }

    @Override
    public boolean delete(CmtNotasSolicitudDetalleVtMgl t) throws ApplicationException {
        CmtNotasSolicitudDetalleVtMglManager cmtNotasDetalleMglMglManager = new CmtNotasSolicitudDetalleVtMglManager();
        return cmtNotasDetalleMglMglManager.delete(t);
    }

    public CmtNotasSolicitudDetalleVtMgl findById(BigDecimal id) throws ApplicationException {
        CmtNotasSolicitudDetalleVtMglManager cmtNotasDetalleMglMglManager = new CmtNotasSolicitudDetalleVtMglManager();
        return cmtNotasDetalleMglMglManager.findById(id);
    }

    @Override
    public CmtNotasSolicitudDetalleVtMgl findById(CmtNotasSolicitudDetalleVtMgl sqlData) throws ApplicationException {
        CmtNotasSolicitudDetalleVtMglManager cmtNotasDetalleMglMglManager = new CmtNotasSolicitudDetalleVtMglManager();
        return cmtNotasDetalleMglMglManager.findById(sqlData);
    }
    
    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

}
