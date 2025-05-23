/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

/**
 *
 * @author Admin
 */
import co.com.claro.mgl.businessmanager.cm.CmtNotasDetalleMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasDetalleMgl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class CmtNotasDetalleMglFacade implements CmtNotasDetalleMglFacadeLocal {

    private String user = "";
    private int perfil = 0;

    @Override
    public List<CmtNotasDetalleMgl> findAll() throws ApplicationException {
        CmtNotasDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasDetalleMglManager();
        return cmtNotasDetalleMglMglManager.findAll();
    }

    @Override
    public List<CmtNotasDetalleMgl> findNotasByNotasId(BigDecimal notasId) throws ApplicationException {
        CmtNotasDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasDetalleMglManager();
        return cmtNotasDetalleMglMglManager.findNotasByNotasId(notasId);
    }

    @Override
    public CmtNotasDetalleMgl create(CmtNotasDetalleMgl notasDetalleMgl) throws ApplicationException {
        CmtNotasDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasDetalleMglManager();
        return cmtNotasDetalleMglMglManager.create(notasDetalleMgl, user, perfil);
    }

    @Override
    public CmtNotasDetalleMgl update(CmtNotasDetalleMgl t) throws ApplicationException {
        CmtNotasDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasDetalleMglManager();
        return cmtNotasDetalleMglMglManager.update(t);
    }

    @Override
    public boolean delete(CmtNotasDetalleMgl t) throws ApplicationException {
        CmtNotasDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasDetalleMglManager();
        return cmtNotasDetalleMglMglManager.delete(t);
    }

    public CmtNotasDetalleMgl findById(BigDecimal id) throws ApplicationException {
        CmtNotasDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasDetalleMglManager();
        return cmtNotasDetalleMglMglManager.findById(id);
    }

    @Override
    public CmtNotasDetalleMgl findById(CmtNotasDetalleMgl sqlData) throws ApplicationException {
        CmtNotasDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasDetalleMglManager();
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
    public List<AuditoriaDto> construirAuditoria(CmtNotasDetalleMgl cmtNotasDetalleMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        CmtNotasDetalleMglManager cmtNotasDetalleMglMglManager = new CmtNotasDetalleMglManager();
        return cmtNotasDetalleMglMglManager.construirAuditoria(cmtNotasDetalleMgl);
    }
}
