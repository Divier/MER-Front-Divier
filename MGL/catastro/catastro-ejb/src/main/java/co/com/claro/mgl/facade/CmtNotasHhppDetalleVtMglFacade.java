/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.CmtNotasHhppDetalleVtMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtNotasHhppDetalleVtMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 *@author Juan David Hernandez
 */
@Stateless
public class CmtNotasHhppDetalleVtMglFacade implements CmtNotasHhppDetalleVtMglFacadeLocal {
    
    private String user = "";
    private int perfil = 0;

    @Override
    public List<CmtNotasHhppDetalleVtMgl> findAll() throws ApplicationException {
        CmtNotasHhppDetalleVtMglManager cmtNotasDetalleMglMglManager = new CmtNotasHhppDetalleVtMglManager();
        return cmtNotasDetalleMglMglManager.findAll();
    }

    @Override
    public List<CmtNotasHhppDetalleVtMgl> findNotasByNotasId(BigDecimal notasId) throws ApplicationException {
        CmtNotasHhppDetalleVtMglManager cmtNotasDetalleMglMglManager = new CmtNotasHhppDetalleVtMglManager();
        return cmtNotasDetalleMglMglManager.findNotasByNotasId(notasId);
    }

    @Override
    public CmtNotasHhppDetalleVtMgl create(CmtNotasHhppDetalleVtMgl t) throws ApplicationException {
        CmtNotasHhppDetalleVtMglManager cmtNotasDetalleMglMglManager = new CmtNotasHhppDetalleVtMglManager();
        return cmtNotasDetalleMglMglManager.create(t, user, perfil);
    }

    @Override
    public CmtNotasHhppDetalleVtMgl update(CmtNotasHhppDetalleVtMgl t) throws ApplicationException {
        CmtNotasHhppDetalleVtMglManager cmtNotasDetalleMglMglManager = new CmtNotasHhppDetalleVtMglManager();
        return cmtNotasDetalleMglMglManager.update(t);
    }

    @Override
    public boolean delete(CmtNotasHhppDetalleVtMgl t) throws ApplicationException {
        CmtNotasHhppDetalleVtMglManager cmtNotasDetalleMglMglManager = new CmtNotasHhppDetalleVtMglManager();
        return cmtNotasDetalleMglMglManager.delete(t);
    }

    public CmtNotasHhppDetalleVtMgl findById(BigDecimal id) throws ApplicationException {
        CmtNotasHhppDetalleVtMglManager cmtNotasDetalleMglMglManager = new CmtNotasHhppDetalleVtMglManager();
        return cmtNotasDetalleMglMglManager.findById(id);
    }

    @Override
    public CmtNotasHhppDetalleVtMgl findById(CmtNotasHhppDetalleVtMgl sqlData) throws ApplicationException {
        CmtNotasHhppDetalleVtMglManager cmtNotasDetalleMglMglManager = new CmtNotasHhppDetalleVtMglManager();
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

    @Override
    public CmtNotasHhppDetalleVtMgl create(CmtNotasHhppDetalleVtMgl cmtNotasDetalleMgl, String usuario, int perfil) throws ApplicationException {
       CmtNotasHhppDetalleVtMglManager cmtNotasDetalleMglMglManager = new CmtNotasHhppDetalleVtMglManager();
        return cmtNotasDetalleMglMglManager.create(cmtNotasDetalleMgl, usuario, perfil);
    }

}
