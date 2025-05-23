/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.TipoHhppMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoHhppMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class TipoHhppMglFacade implements TipoHhppMglFacadeLocal {

    @Override
    public List<TipoHhppMgl> findAll() throws ApplicationException {
        TipoHhppMglManager tipoHhppMglManager = new TipoHhppMglManager();
        return tipoHhppMglManager.findAll();
    }

    @Override
    public TipoHhppMgl create(TipoHhppMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoHhppMgl update(TipoHhppMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(TipoHhppMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoHhppMgl findTipoHhppMglById(String thhID) throws ApplicationException {
        TipoHhppMglManager tipoHhppMglManager = new TipoHhppMglManager();
        return tipoHhppMglManager.findById(thhID);
    }

    @Override
    public TipoHhppMgl findById(TipoHhppMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
