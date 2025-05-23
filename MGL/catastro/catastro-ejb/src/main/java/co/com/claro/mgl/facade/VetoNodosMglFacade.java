/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.VetoNodosMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.VetoNodosMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Juan David Hernandez
 */
@Stateless
public class VetoNodosMglFacade implements VetoNodosMglFacadeLocal{

    private String user = "";
    private int perfil = 0;
    
    @Override
    public List<VetoNodosMgl> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VetoNodosMgl create(VetoNodosMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VetoNodosMgl update(VetoNodosMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(VetoNodosMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VetoNodosMgl findById(VetoNodosMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil No pueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }
    
      /**
     * Crea una vetoNodos en la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @return
     * @throws ApplicationException
     */
    @Override
    public VetoNodosMgl createVetoNodos(VetoNodosMgl t) 
            throws ApplicationException {
        VetoNodosMglManager vetoNodosMgr = new VetoNodosMglManager();
        return vetoNodosMgr.createVetoNodos(t,user, perfil);
    }    
}
