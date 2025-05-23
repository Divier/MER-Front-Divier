/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.VetoMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.VetoMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Juan David Hernandez
 */
@Stateless
public class VetoMglFacade implements VetoMglFacadeLocal{
 
    private String user = "";
    private int perfil = 0;

    @Override
    public List<VetoMgl> findAllVetoList()
            throws ApplicationException {
        VetoMglManager vetoMgr = new VetoMglManager();
        return vetoMgr.findAllVetoList();
    }
    
    @Override
    public List<VetoMgl> findAllVetoPaginadaList(int page, int maxResults)
            throws ApplicationException {
        VetoMglManager vetoMgr = new VetoMglManager();
        return vetoMgr.findAllVetoPaginadaList(page, maxResults);
    }
    
    @Override
       public int countAllVetoList()
            throws ApplicationException {
        VetoMglManager vetoMgr = new VetoMglManager();
        return vetoMgr.countAllVetoList();
    }

    /**
     * Crea una nota en la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @return
     * @throws ApplicationException
     */
    @Override
    public VetoMgl crearVeto(VetoMgl t)
            throws ApplicationException {
        VetoMglManager vetoMgr = new VetoMglManager();
        return vetoMgr.createVeto(t, user, perfil);
    }

    @Override
    public List<VetoMgl> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VetoMgl create(VetoMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VetoMgl update(VetoMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(VetoMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VetoMgl findById(VetoMgl sqlData) throws ApplicationException {
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
     * Actualiza un veto en la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @return
     * @throws ApplicationException
     */
    @Override
    public VetoMgl updateVeto(VetoMgl t) 
            throws ApplicationException {
        VetoMglManager vetoMgr = new VetoMglManager();
        return vetoMgr.updateVeto(t,user, perfil);
    }
    
    
    @Override
        public VetoMgl findByPolitica(String politica) 
            throws ApplicationException {
        VetoMglManager vetoMgr = new VetoMglManager();
        return vetoMgr.findByPolitica(politica);
    }
}
