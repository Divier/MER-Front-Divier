/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.SolicitudCEMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.SolicitudCEMgl;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SolicitudCEMglManager {

    SolicitudCEMglDaoImpl solicitudCEMglDaoImpl = new SolicitudCEMglDaoImpl();

    public List<SolicitudCEMgl> findAll() throws ApplicationException {
        List<SolicitudCEMgl> result;
        SolicitudCEMglDaoImpl solicitudCEMglDaoImpl1 = new SolicitudCEMglDaoImpl();
        result = solicitudCEMglDaoImpl1.findAll();
        return result;
    }
    
    public SolicitudCEMgl create(SolicitudCEMgl solicitudCEMgl) throws ApplicationException {

        return solicitudCEMglDaoImpl.create(solicitudCEMgl);

    }
}
