/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.TipoHhppConexionMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoHhppConexionMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class TipoHhppConexionMglFacade implements TipoHhppConexionMglFacadeLocal {

    @Override
    public List<TipoHhppConexionMgl> findAll() throws ApplicationException {
        TipoHhppConexionMglManager tipoHhppConexionMglManager = new TipoHhppConexionMglManager();
        return tipoHhppConexionMglManager.findAll();
    }

    @Override
    public TipoHhppConexionMgl create(TipoHhppConexionMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoHhppConexionMgl update(TipoHhppConexionMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(TipoHhppConexionMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoHhppConexionMgl findById(TipoHhppConexionMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  @Override
  public TipoHhppConexionMgl findById(BigDecimal idDecimal)throws ApplicationException {
    TipoHhppConexionMglManager manager=new TipoHhppConexionMglManager();
    return manager.findById(idDecimal);
  }
}
