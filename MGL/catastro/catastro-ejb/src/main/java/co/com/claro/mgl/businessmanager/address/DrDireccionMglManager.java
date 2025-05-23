/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;
import co.com.claro.mgl.dao.impl.DrDireccionMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrDireccionMgl;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DrDireccionMglManager {

    DrDireccionMglDaoImpl drDireccionMglDaoImpl = new DrDireccionMglDaoImpl();

    public List<DrDireccionMgl> findAll() throws ApplicationException {
        List<DrDireccionMgl> result;
        DrDireccionMglDaoImpl drDireccionMglDaoImpl1 = new DrDireccionMglDaoImpl();
        result = drDireccionMglDaoImpl1.findAll();
        return result;
    }

    public DrDireccionMgl findByRequest(String idRequest) throws ApplicationException {

        return drDireccionMglDaoImpl.findByRequest(idRequest);

    }
}
