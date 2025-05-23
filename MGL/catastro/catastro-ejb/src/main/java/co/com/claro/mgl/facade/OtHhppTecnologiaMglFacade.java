
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.OtHhppTecnologiaMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.OtHhppTecnologiaMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Juan David Hernandez
 */
@Stateless
public class OtHhppTecnologiaMglFacade implements OtHhppTecnologiaMglFacadeLocal {
    
    private String user = "";
    private int perfil = 0;  


    @Override
    public List<OtHhppTecnologiaMgl> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OtHhppTecnologiaMgl create(OtHhppTecnologiaMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OtHhppTecnologiaMgl update(OtHhppTecnologiaMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(OtHhppTecnologiaMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OtHhppTecnologiaMgl findById(OtHhppTecnologiaMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
      
    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }
    
    /**
     * Lista tecnologias por el ID de ot que se encuentran relacionadas.
     *
     * @author Juan David Hernandez
     * @param otId
     * @return
     * @throws ApplicationException
     */
    @Override
    public List<OtHhppTecnologiaMgl> findOtHhppTecnologiaByOtHhppId
            (BigDecimal otId) throws ApplicationException {
        OtHhppTecnologiaMglManager otHhppTecnologiaMglManager
                = new OtHhppTecnologiaMglManager();
        return otHhppTecnologiaMglManager.findOtHhppTecnologiaByOtHhppId(otId);
    }

}
