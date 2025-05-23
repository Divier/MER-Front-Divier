/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtSolicitudTipoSolicitudMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudTipoSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class CmtSolicitudTipoSolicitudMglManager {
    
    /**
     *
     * @return resulList
     * @throws ApplicationException
     */
    public List<CmtSolicitudTipoSolicitudMgl> findAll() throws ApplicationException {
        CmtSolicitudTipoSolicitudMglDaoImpl cmtTiempoSolicitudMglDaoImpl = new CmtSolicitudTipoSolicitudMglDaoImpl();
        return cmtTiempoSolicitudMglDaoImpl.findAll();
    }

    public CmtSolicitudTipoSolicitudMgl create(CmtSolicitudTipoSolicitudMgl cmt, String usuario, int perfil) throws ApplicationException {
        CmtSolicitudTipoSolicitudMglDaoImpl cmtTiempoSolicitudMglDaoImpl = new CmtSolicitudTipoSolicitudMglDaoImpl();
        cmt.setEstadoRegistro(1);
        return cmtTiempoSolicitudMglDaoImpl.createCm(cmt, usuario.toUpperCase(), perfil);

    }

    public CmtSolicitudTipoSolicitudMgl update(CmtSolicitudTipoSolicitudMgl cmt, String usuario, int perfil) throws ApplicationException {
        CmtSolicitudTipoSolicitudMglDaoImpl cmtTiempoSolicitudMglDaoImpl = new CmtSolicitudTipoSolicitudMglDaoImpl();

        return cmtTiempoSolicitudMglDaoImpl.updateCm(cmt, usuario.toUpperCase(), perfil);

    }

    public boolean delete(CmtSolicitudTipoSolicitudMgl cmt, String usuario, int perfil) throws ApplicationException, ApplicationException {
       CmtSolicitudTipoSolicitudMglDaoImpl cmtTiempoSolicitudMglDaoImpl = new CmtSolicitudTipoSolicitudMglDaoImpl();
        return cmtTiempoSolicitudMglDaoImpl.deleteCm(cmt, usuario.toUpperCase(), perfil);

    }
    

    public CmtSolicitudTipoSolicitudMgl findByIdSolicitud(BigDecimal idSolicitud) throws ApplicationException {
        CmtSolicitudTipoSolicitudMglDaoImpl cmtTiempoSolicitudMglDaoImpl = new CmtSolicitudTipoSolicitudMglDaoImpl();
        return cmtTiempoSolicitudMglDaoImpl.findByIdSolicitud(idSolicitud);
    }
    
}
