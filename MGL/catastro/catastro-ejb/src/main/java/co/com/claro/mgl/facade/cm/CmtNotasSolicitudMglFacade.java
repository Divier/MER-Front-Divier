/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

/**
 *
 * @author Admin
 */
import co.com.claro.mgl.businessmanager.cm.CmtNotasSolicitudMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class CmtNotasSolicitudMglFacade implements CmtNotasSolicitudMglFacadeLocal {

    private String user = "";
    private int perfil = 0;
    
    public List<CmtNotasSolicitudMgl> findAll() throws ApplicationException {
        CmtNotasSolicitudMglManager cmtNotasMglMglManager = new CmtNotasSolicitudMglManager();
        return cmtNotasMglMglManager.findAll();
    }

    @Override
    public List<CmtNotasSolicitudMgl> findTipoNotasId(BigDecimal tipoNotasId) throws ApplicationException {
        CmtNotasSolicitudMglManager cmtNotasMglMglManager = new CmtNotasSolicitudMglManager();
        return cmtNotasMglMglManager.findTipoNotasId(tipoNotasId);
    }

    @Override
    public List<CmtNotasSolicitudMgl> findNotasBySolicitudId(BigDecimal idSolicitudCm) throws ApplicationException {
        CmtNotasSolicitudMglManager cmtNotasMglMglManager = new CmtNotasSolicitudMglManager();
        return cmtNotasMglMglManager.findNotasBySolicitudId(idSolicitudCm);
    }

    @Override
    public CmtNotasSolicitudMgl crearNotSol(CmtNotasSolicitudMgl t) throws ApplicationException {
        CmtNotasSolicitudMglManager cmtNotasMglMglManager = new CmtNotasSolicitudMglManager();
        return cmtNotasMglMglManager.create(t,user, perfil);
    }

    public CmtNotasSolicitudMgl update(CmtNotasSolicitudMgl t) throws ApplicationException {
        CmtNotasSolicitudMglManager cmtNotasMglMglManager = new CmtNotasSolicitudMglManager();
        return cmtNotasMglMglManager.update(t);
    }

    public boolean delete(CmtNotasSolicitudMgl t) throws ApplicationException {
        CmtNotasSolicitudMglManager cmtNotasMglMglManager = new CmtNotasSolicitudMglManager();
        return cmtNotasMglMglManager.delete(t);
    }

    @Override
    public CmtNotasSolicitudMgl findById(BigDecimal id) throws ApplicationException {
        CmtNotasSolicitudMglManager cmtNotasMglMglManager = new CmtNotasSolicitudMglManager();
        return cmtNotasMglMglManager.findById(id);
    }

    public CmtNotasSolicitudMgl findById(CmtNotasSolicitudMgl sqlData) throws ApplicationException {
        CmtNotasSolicitudMglManager cmtNotasMglMglManager = new CmtNotasSolicitudMglManager();
        return cmtNotasMglMglManager.findById(sqlData);
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
