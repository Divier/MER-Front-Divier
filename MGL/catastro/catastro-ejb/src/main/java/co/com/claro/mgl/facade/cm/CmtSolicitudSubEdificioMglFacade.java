package co.com.claro.mgl.facade.cm;

/**
 *
 * @author Admin
 */
import co.com.claro.mgl.businessmanager.cm.CmtSolicitudSubEdificioMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudSubEdificioMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class CmtSolicitudSubEdificioMglFacade implements CmtSolicitudSubEdificioMglFacadeLocal {

    private String user = "";
    private int perfil = 0;

    @Override
    public List<CmtSolicitudSubEdificioMgl> findAll() throws ApplicationException {
        CmtSolicitudSubEdificioMglManager cmtSolicitudSubEdificioMglMglManager = new CmtSolicitudSubEdificioMglManager();
        return cmtSolicitudSubEdificioMglMglManager.findAll();
    }

    @Override
    public List<CmtSolicitudSubEdificioMgl> findSolicitudSubEdificioBySolicitud(CmtSolicitudCmMgl solicitudCM) throws ApplicationException {
        CmtSolicitudSubEdificioMglManager cmtSolicitudSubEdificioMglMglManager = new CmtSolicitudSubEdificioMglManager();
        return cmtSolicitudSubEdificioMglMglManager.findSolicitudSubEdificioBySolicitud(solicitudCM);
    }

    @Override
    public CmtSolicitudSubEdificioMgl crearSolSub(CmtSolicitudSubEdificioMgl t) throws ApplicationException {
        CmtSolicitudSubEdificioMglManager cmtSolicitudSubEdificioMglMglManager = new CmtSolicitudSubEdificioMglManager();
        return cmtSolicitudSubEdificioMglMglManager.create(t, user, perfil);
    }

    @Override
    public CmtSolicitudSubEdificioMgl update(CmtSolicitudSubEdificioMgl t) throws ApplicationException {
        CmtSolicitudSubEdificioMglManager cmtSolicitudSubEdificioMglMglManager = new CmtSolicitudSubEdificioMglManager();
        return cmtSolicitudSubEdificioMglMglManager.update(t);
    }

    @Override
    public boolean delete(CmtSolicitudSubEdificioMgl t) throws ApplicationException {
        CmtSolicitudSubEdificioMglManager cmtSolicitudSubEdificioMglMglManager = new CmtSolicitudSubEdificioMglManager();
        return cmtSolicitudSubEdificioMglMglManager.delete(t);
    }

    @Override
    public CmtSolicitudSubEdificioMgl findById(BigDecimal id) throws ApplicationException {
        CmtSolicitudSubEdificioMglManager cmtSolicitudSubEdificioMglMglManager = new CmtSolicitudSubEdificioMglManager();
        return cmtSolicitudSubEdificioMglMglManager.findById(id);
    }

    @Override
    public CmtSolicitudSubEdificioMgl findById(CmtSolicitudSubEdificioMgl cmtSolSubEdificioMgl) throws ApplicationException {
        CmtSolicitudSubEdificioMglManager cmtSolicitudSubEdificioMglMglManager = new CmtSolicitudSubEdificioMglManager();
        return cmtSolicitudSubEdificioMglMglManager.findById(cmtSolSubEdificioMgl);
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
    public CmtSolicitudSubEdificioMgl create(CmtSolicitudSubEdificioMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public CmtSolicitudSubEdificioMgl updateCm(CmtSolicitudSubEdificioMgl cmtSolicitudSubEdificioMgl, String usuario, int perfil)
            throws ApplicationException {
        
        CmtSolicitudSubEdificioMglManager cmtSolicitudSubEdificioMglMglManager = new CmtSolicitudSubEdificioMglManager();
        return cmtSolicitudSubEdificioMglMglManager.updateCm(cmtSolicitudSubEdificioMgl, usuario, perfil);
    }

}
