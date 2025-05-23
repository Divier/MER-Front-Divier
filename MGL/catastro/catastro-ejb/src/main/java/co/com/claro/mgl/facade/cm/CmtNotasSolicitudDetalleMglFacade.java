/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

/**
 *
 * @author Admin
 */
import co.com.claro.mgl.businessmanager.cm.CmtNotasSolicitudDetalleMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudDetalleMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class CmtNotasSolicitudDetalleMglFacade implements CmtNotasSolicitudDetalleMglFacadeLocal {
    
    private String user = "";
    private int perfil = 0;

    @Override
    public List<CmtNotasSolicitudDetalleMgl> findAll() throws ApplicationException {
        CmtNotasSolicitudDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasSolicitudDetalleMglManager();
        return cmtNotasDetalleMglMglManager.findAll();
    }

    @Override
    public List<CmtNotasSolicitudDetalleMgl> findNotasByNotasId(BigDecimal notasId) throws ApplicationException {
        CmtNotasSolicitudDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasSolicitudDetalleMglManager();
        return cmtNotasDetalleMglMglManager.findNotasByNotasId(notasId);
    }

    @Override
    public CmtNotasSolicitudDetalleMgl create(CmtNotasSolicitudDetalleMgl t) throws ApplicationException {
        CmtNotasSolicitudDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasSolicitudDetalleMglManager();
        return cmtNotasDetalleMglMglManager.create(t, user, perfil);
    }

    @Override
    public CmtNotasSolicitudDetalleMgl update(CmtNotasSolicitudDetalleMgl t) throws ApplicationException {
        CmtNotasSolicitudDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasSolicitudDetalleMglManager();
        return cmtNotasDetalleMglMglManager.update(t);
    }

    @Override
    public boolean delete(CmtNotasSolicitudDetalleMgl t) throws ApplicationException {
        CmtNotasSolicitudDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasSolicitudDetalleMglManager();
        return cmtNotasDetalleMglMglManager.delete(t);
    }

    public CmtNotasSolicitudDetalleMgl findById(BigDecimal id) throws ApplicationException {
        CmtNotasSolicitudDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasSolicitudDetalleMglManager();
        return cmtNotasDetalleMglMglManager.findById(id);
    }

    @Override
    public CmtNotasSolicitudDetalleMgl findById(CmtNotasSolicitudDetalleMgl sqlData) throws ApplicationException {
        CmtNotasSolicitudDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasSolicitudDetalleMglManager();
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
