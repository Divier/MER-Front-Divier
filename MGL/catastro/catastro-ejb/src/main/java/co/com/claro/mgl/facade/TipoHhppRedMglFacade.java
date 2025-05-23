/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.TipoHhppRedMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoHhppRedMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class TipoHhppRedMglFacade implements TipoHhppRedMglFacadeLocal {

    @Override
    public List<TipoHhppRedMgl> findAll() throws ApplicationException {
        TipoHhppRedMglManager tipoHhppRedMglManager = new TipoHhppRedMglManager();
        return tipoHhppRedMglManager.findAll();
    }

    @Override
    public TipoHhppRedMgl create(TipoHhppRedMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoHhppRedMgl update(TipoHhppRedMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(TipoHhppRedMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoHhppRedMgl findById(TipoHhppRedMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
