/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;


import co.com.claro.mgl.businessmanager.address.EstadoHhppMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.EstadoHhppMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class EstadoHhppMglFacade implements EstadoHhppMglFacadeLocal {

    @Override
    public List<EstadoHhppMgl> findAll() throws ApplicationException {
        EstadoHhppMglManager estadoHhppMglManager = new EstadoHhppMglManager();
        return estadoHhppMglManager.findAll();
    }

    @Override
    public EstadoHhppMgl create(EstadoHhppMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EstadoHhppMgl update(EstadoHhppMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(EstadoHhppMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EstadoHhppMgl findById(EstadoHhppMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
