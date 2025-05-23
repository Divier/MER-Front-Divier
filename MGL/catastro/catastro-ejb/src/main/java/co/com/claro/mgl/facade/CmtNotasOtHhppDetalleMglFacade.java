/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.CmtNotasOtHhppDetalleMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtNotasOtHhppDetalleMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 *@author Juan David Hernandez
 */
@Stateless
public class CmtNotasOtHhppDetalleMglFacade implements CmtNotasOtHhppDetalleMglFacadeLocal {
    
    private String user = "";
    private int perfil = 0;

    @Override
    public List<CmtNotasOtHhppDetalleMgl> findAll() throws ApplicationException {
        CmtNotasOtHhppDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasOtHhppDetalleMglManager();
        return cmtNotasDetalleMglMglManager.findAll();
    }

    @Override
    public List<CmtNotasOtHhppDetalleMgl> findNotasByNotasId(BigDecimal notasId) throws ApplicationException {
        CmtNotasOtHhppDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasOtHhppDetalleMglManager();
        return cmtNotasDetalleMglMglManager.findNotasByNotasId(notasId);
    }

    @Override
    public CmtNotasOtHhppDetalleMgl create(CmtNotasOtHhppDetalleMgl t) throws ApplicationException {
        CmtNotasOtHhppDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasOtHhppDetalleMglManager();
        return cmtNotasDetalleMglMglManager.create(t, user, perfil);
    }

    @Override
    public CmtNotasOtHhppDetalleMgl update(CmtNotasOtHhppDetalleMgl t) throws ApplicationException {
        CmtNotasOtHhppDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasOtHhppDetalleMglManager();
        return cmtNotasDetalleMglMglManager.update(t);
    }

    @Override
    public boolean delete(CmtNotasOtHhppDetalleMgl t) throws ApplicationException {
        CmtNotasOtHhppDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasOtHhppDetalleMglManager();
        return cmtNotasDetalleMglMglManager.delete(t);
    }

    public CmtNotasOtHhppDetalleMgl findById(BigDecimal id) throws ApplicationException {
        CmtNotasOtHhppDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasOtHhppDetalleMglManager();
        return cmtNotasDetalleMglMglManager.findById(id);
    }

    @Override
    public CmtNotasOtHhppDetalleMgl findById(CmtNotasOtHhppDetalleMgl sqlData) throws ApplicationException {
        CmtNotasOtHhppDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasOtHhppDetalleMglManager();
        return cmtNotasDetalleMglMglManager.findById(sqlData);
    }
    
    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil No pueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

}
