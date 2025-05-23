/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.VetoCanalMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.VetoCanalMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Juan David Hernandez
 */
@Stateless
public class VetoCanalMglFacade implements VetoCanalMglFacadeLocal{

    private String user = "";
    private int perfil = 0;
    
    @Override
    public List<VetoCanalMgl> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VetoCanalMgl create(VetoCanalMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VetoCanalMgl update(VetoCanalMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(VetoCanalMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VetoCanalMgl findById(VetoCanalMgl sqlData) throws ApplicationException {
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
     * Crea un vetoCanal en la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @return
     * @throws ApplicationException
     */
    @Override
    public VetoCanalMgl createVetoCanal(VetoCanalMgl t) 
            throws ApplicationException {
        VetoCanalMglManager vetoCanalMgr = new VetoCanalMglManager();
        return vetoCanalMgr.createVetoCanal(t,user, perfil);
    }
    
    
}
