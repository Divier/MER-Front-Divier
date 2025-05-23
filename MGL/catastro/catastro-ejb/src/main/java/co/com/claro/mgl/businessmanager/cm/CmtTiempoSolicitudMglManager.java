/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtTiempoSolicitudMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtTiempoSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class CmtTiempoSolicitudMglManager {

    /**
     *
     * @return resulList
     * @throws ApplicationException
     */
    public List<CmtTiempoSolicitudMgl> findAll() throws ApplicationException {
        CmtTiempoSolicitudMglDaoImpl cmtTiempoSolicitudMglDaoImpl 
                = new CmtTiempoSolicitudMglDaoImpl();
        return cmtTiempoSolicitudMglDaoImpl.findAll();
    }

    public CmtTiempoSolicitudMgl create(CmtTiempoSolicitudMgl cmt, 
            String usuario, int perfil) throws ApplicationException {
        CmtTiempoSolicitudMglDaoImpl cmtTiempoSolicitudMglDaoImpl 
                = new CmtTiempoSolicitudMglDaoImpl();
        cmt.setEstadoRegistro(1);
        return cmtTiempoSolicitudMglDaoImpl.createCm(cmt, usuario.toUpperCase(), perfil);

    }

    public CmtTiempoSolicitudMgl update(CmtTiempoSolicitudMgl cmt, 
            String usuario, int perfil) throws ApplicationException {
        CmtTiempoSolicitudMglDaoImpl cmtTiempoSolicitudMglDaoImpl
                = new CmtTiempoSolicitudMglDaoImpl();

        return cmtTiempoSolicitudMglDaoImpl.updateCm(cmt, usuario.toUpperCase(),
                perfil);

    }

    public boolean delete(CmtTiempoSolicitudMgl cmt, String usuario, int perfil)
            throws ApplicationException, ApplicationException {
        CmtTiempoSolicitudMglDaoImpl cmtTiempoSolicitudMglDaoImpl 
                = new CmtTiempoSolicitudMglDaoImpl();
        return cmtTiempoSolicitudMglDaoImpl.deleteCm(cmt, usuario.toUpperCase(), 
                perfil);

    }

    /**
     * Obtiene tiempos de traza por IdSolicitud
     *
     * @author Juan David Hernandez
     * @param idSolicitud
     * @return CmtTiempoSolicitudMgl
     * @throws ApplicationException
     */
    public CmtTiempoSolicitudMgl findTiemposBySolicitud(BigDecimal idSolicitud)
            throws ApplicationException {
        CmtTiempoSolicitudMglDaoImpl cmtTiempoSolicitudMglDaoImpl
                = new CmtTiempoSolicitudMglDaoImpl();
        return cmtTiempoSolicitudMglDaoImpl.findTiemposBySolicitud(idSolicitud);

    }
}
