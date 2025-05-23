/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtTiempoSolicitudMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtTiempoSolicitudMgl;
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
public class CmtTiempoSolicitudMglFacade implements CmtTiempoSolicitudMglFacadeLocal {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtTiempoSolicitudMglFacade.class);
    
    private String user = "";
    private int perfil = 0;

    @Override
    public List<CmtTiempoSolicitudMgl> findAll() throws ApplicationException {
        CmtTiempoSolicitudMglManager cmtTiempoSolicitudMglManager = new CmtTiempoSolicitudMglManager();
        return cmtTiempoSolicitudMglManager.findAll();
    }

    @Override
    public CmtTiempoSolicitudMgl create(CmtTiempoSolicitudMgl t) throws ApplicationException {
         CmtTiempoSolicitudMglManager cmtTiempoSolicitudMglManager = new CmtTiempoSolicitudMglManager();
        return cmtTiempoSolicitudMglManager.create(t, user, perfil);
    }

    @Override
    public CmtTiempoSolicitudMgl update(CmtTiempoSolicitudMgl t) throws ApplicationException {
       CmtTiempoSolicitudMglManager cmtTiempoSolicitudMglManager = new CmtTiempoSolicitudMglManager();
        return cmtTiempoSolicitudMglManager.update(t, user, perfil);
    }

    @Override
    public boolean delete(CmtTiempoSolicitudMgl t) throws ApplicationException {
         CmtTiempoSolicitudMglManager cmtTiempoSolicitudMglManager = new CmtTiempoSolicitudMglManager();
         try {
            return cmtTiempoSolicitudMglManager.delete(t, user, perfil);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            
            return false;
        }
    }

    @Override
    public CmtTiempoSolicitudMgl findById(CmtTiempoSolicitudMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil No pueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

      /**
     * Obtiene tiempos de traza por IdSolicitud
     *
     * @author Juan David Hernandez
     * @param idSolicitud
     * @return CmtTiempoSolicitudMgl
     */
    @Override
     public CmtTiempoSolicitudMgl findTiemposBySolicitud(BigDecimal idSolicitud) {
        try {
            CmtTiempoSolicitudMglManager cmtTiempoSolicitudMglManager = new CmtTiempoSolicitudMglManager();
            return cmtTiempoSolicitudMglManager.findTiemposBySolicitud(idSolicitud);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            
            return null;
        }
    }
}
