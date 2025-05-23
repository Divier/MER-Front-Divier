/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtSolicitudTipoSolicitudMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudTipoSolicitudMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Juan David Hernandez
 */
@Stateless
public class CmtSolicitudTipoSolicitudMglFacade implements CmtSolicitudTipoSolicitudMglFacadeLocal {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtSolicitudTipoSolicitudMglFacade.class);
    
    private String user = "";
    private int perfil = 0;

    @Override
    public List<CmtSolicitudTipoSolicitudMgl> findAll() throws ApplicationException {
        CmtSolicitudTipoSolicitudMglManager cmtTiempoSolicitudMglManager = new CmtSolicitudTipoSolicitudMglManager();
        return cmtTiempoSolicitudMglManager.findAll();
    }

    @Override
    public CmtSolicitudTipoSolicitudMgl create(CmtSolicitudTipoSolicitudMgl t) throws ApplicationException {
         CmtSolicitudTipoSolicitudMglManager cmtTiempoSolicitudMglManager = new CmtSolicitudTipoSolicitudMglManager();
        return cmtTiempoSolicitudMglManager.create(t, user, perfil);
    }

    @Override
    public CmtSolicitudTipoSolicitudMgl update(CmtSolicitudTipoSolicitudMgl t) throws ApplicationException {
       CmtSolicitudTipoSolicitudMglManager cmtTiempoSolicitudMglManager = new CmtSolicitudTipoSolicitudMglManager();
        return cmtTiempoSolicitudMglManager.update(t, user, perfil);
    }

    @Override
    public boolean delete(CmtSolicitudTipoSolicitudMgl t) throws ApplicationException {
         CmtSolicitudTipoSolicitudMglManager cmtTiempoSolicitudMglManager = new CmtSolicitudTipoSolicitudMglManager();
         try {
            return cmtTiempoSolicitudMglManager.delete(t, user, perfil);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);

            return false;
        }
    }

    @Override
    public CmtSolicitudTipoSolicitudMgl findById(CmtSolicitudTipoSolicitudMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
      public CmtSolicitudTipoSolicitudMgl findByIdSolicitud(BigDecimal idSolicitud) throws ApplicationException {
       CmtSolicitudTipoSolicitudMglManager cmtTiempoSolicitudMglManager = new CmtSolicitudTipoSolicitudMglManager();
        return cmtTiempoSolicitudMglManager.findByIdSolicitud(idSolicitud);
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
