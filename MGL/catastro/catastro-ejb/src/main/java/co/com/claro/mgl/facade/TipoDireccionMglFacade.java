/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.TipoDireccionMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoDireccionMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class TipoDireccionMglFacade implements TipoDireccionMglFacadeLocal {

    @Override
    public List<TipoDireccionMgl> findAll() throws ApplicationException {
        TipoDireccionMglManager tipoDireccionMglManager = new TipoDireccionMglManager();
        return tipoDireccionMglManager.findAll();
    }

    @Override
    public TipoDireccionMgl create(TipoDireccionMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoDireccionMgl update(TipoDireccionMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(TipoDireccionMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoDireccionMgl findById(TipoDireccionMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
