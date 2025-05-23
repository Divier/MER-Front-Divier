/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtDireccionSolicitudMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.error.ApplicationExceptionCM;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Facade mapeo tabla CMT_DIRECCION_SOLICITUD
 *
 * @author yimy diaz
 * @versión 1.00.0000
 */
@Stateless
public class CmtDireccionSolicitudMglFacade
        implements CmtDireccionSolicitudMglFacadeLocal {

    private static final Logger LOGGER = LogManager.getLogger(CmtDireccionSolicitudMglFacade.class);
     private String user = "";
     private int perfil = 0;
     

     @Override
    public List<CmtDireccionSolicitudMgl> findAll() throws ApplicationException {
        CmtDireccionSolicitudMglManager cmtDireccionSolicitudMglMglManager =
                new CmtDireccionSolicitudMglManager();
        return cmtDireccionSolicitudMglMglManager.findAll();
    }

     @Override
    public CmtDireccionSolicitudMgl crearDir(CmtDireccionSolicitudMgl t)
            throws ApplicationException {
        CmtDireccionSolicitudMglManager cmtDireccionSolicitudMglMglManager =
                new CmtDireccionSolicitudMglManager();
        return cmtDireccionSolicitudMglMglManager.create(t,user, perfil);
    }

     @Override
    public CmtDireccionSolicitudMgl update(CmtDireccionSolicitudMgl t)
            throws ApplicationException {
        CmtDireccionSolicitudMglManager cmtDireccionSolicitudMglMglManager =
                new CmtDireccionSolicitudMglManager();
        return cmtDireccionSolicitudMglMglManager.update(t);
    }

     @Override
    public boolean delete(CmtDireccionSolicitudMgl t)
            throws ApplicationException {
        CmtDireccionSolicitudMglManager cmtDireccionSolicitudMglMglManager =
                new CmtDireccionSolicitudMglManager();
        return cmtDireccionSolicitudMglMglManager.delete(t);
    }

     @Override
    public CmtDireccionSolicitudMgl findById(BigDecimal id)
            throws ApplicationException {
        CmtDireccionSolicitudMglManager cmtDireccionSolicitudMglMglManager =
                new CmtDireccionSolicitudMglManager();
        return cmtDireccionSolicitudMglMglManager.findById(id);
    }

     @Override
    public CmtDireccionSolicitudMgl findById(CmtDireccionSolicitudMgl sqlData)
            throws ApplicationException {
        CmtDireccionSolicitudMglManager cmtDireccionSolicitudMglMglManager =
                new CmtDireccionSolicitudMglManager();
        return cmtDireccionSolicitudMglMglManager.findById(sqlData);
    }

     @Override
    public List<CmtDireccionSolicitudMgl> findSolicitudId(BigDecimal idSolicitud) {
        CmtDireccionSolicitudMglManager cmtDireccionSolicitudMglMglManager =
                new CmtDireccionSolicitudMglManager();
        return cmtDireccionSolicitudMglMglManager.findSolicitudId(idSolicitud);
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
    public CmtDireccionSolicitudMgl create(CmtDireccionSolicitudMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     @Override
    public List<CmtSolicitudCmMgl> findByDrDireccion(DrDireccion drDireccion, String comunidad)
            throws ApplicationException {
        CmtDireccionSolicitudMglManager cmtDireccionSolicitudMglMglManager =
                new CmtDireccionSolicitudMglManager();
        return cmtDireccionSolicitudMglMglManager.findByDrDireccion(
                drDireccion, comunidad);
    }
    
     @Override
    public void siExistenSolictudesEnCurso(DrDireccion drDireccion,String comunidad) throws ApplicationExceptionCM{
            CmtDireccionSolicitudMglManager cmtDireccionSolicitudMglMglManager =
                new CmtDireccionSolicitudMglManager();
         try {
             cmtDireccionSolicitudMglMglManager.siExistenSolictudesEnCurso(drDireccion, comunidad);
         } catch (CloneNotSupportedException ex) {
             LOGGER.error(ex.getMessage(), ex);
         }
    } 
    
    

}
