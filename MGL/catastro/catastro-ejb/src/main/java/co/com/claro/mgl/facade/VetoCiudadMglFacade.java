/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.VetoCiudadMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.VetoCiudadMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Juan David Hernandez
 */
@Stateless
public class VetoCiudadMglFacade implements VetoCiudadMglFacadeLocal{

    
    private String user = "";
    private int perfil = 0;
    
    @Override
    public List<VetoCiudadMgl> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VetoCiudadMgl create(VetoCiudadMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VetoCiudadMgl update(VetoCiudadMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(VetoCiudadMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VetoCiudadMgl findById(VetoCiudadMgl sqlData) throws ApplicationException {
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
     * Crea un vetoCiudad en la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @return
     * @throws ApplicationException
     */
    @Override
    public VetoCiudadMgl createVetoCiudad(VetoCiudadMgl t) 
            throws ApplicationException {
        VetoCiudadMglManager vetoCiudadMgr = new VetoCiudadMglManager();
        return vetoCiudadMgr.createVetoCiudad(t,user, perfil);
    }

}
