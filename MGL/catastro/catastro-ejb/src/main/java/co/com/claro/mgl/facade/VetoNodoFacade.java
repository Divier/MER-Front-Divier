/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.VetoNodoManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.VetoNodo;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class VetoNodoFacade implements VetoNodoFacadeLocal{

    @Override
    public List<VetoNodo> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VetoNodo create(VetoNodo t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VetoNodo update(VetoNodo t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(VetoNodo t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VetoNodo findById(VetoNodo sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<VetoNodo> findVetos(String politica, Date initDate, Date endDate, BigDecimal ciudad, String tipoVeto) throws ApplicationException {
        VetoNodoManager vetoNodoManager = new VetoNodoManager();
        return vetoNodoManager.findVetos(politica, initDate, endDate, ciudad, tipoVeto);
    }
    
}
