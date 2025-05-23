/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.UbicacionMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.UbicacionMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class UbicacionMglFacade implements UbicacionMglFacadeLocal {

    @Override
    public List<UbicacionMgl> findAll() throws ApplicationException {
        UbicacionMglManager ubicacionMglManager = new UbicacionMglManager();
        return ubicacionMglManager.findAll();
    }

    @Override
    public UbicacionMgl create(UbicacionMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UbicacionMgl update(UbicacionMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(UbicacionMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UbicacionMgl findById(UbicacionMgl sqlData) throws ApplicationException {
        UbicacionMglManager ubicacionMglManager = new UbicacionMglManager();
        return ubicacionMglManager.findById(sqlData);
    }


}
