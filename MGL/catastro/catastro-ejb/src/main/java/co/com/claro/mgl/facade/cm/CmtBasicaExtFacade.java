
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtBasicaExtMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author cardenaslb
 */
@Stateless
public class CmtBasicaExtFacade implements CmtBasicaExtMglFacadeLocal{
    
    
    private String user = "";
    private int perfil = 0;
    
    
    
 
    @Override
    public List<CmtBasicaExtMgl> findAll() throws ApplicationException {
         CmtBasicaExtMglManager cmtBasicaExtMglManager = new CmtBasicaExtMglManager();
        return cmtBasicaExtMglManager.findAll();
    }

    @Override
    public CmtBasicaExtMgl create(CmtBasicaExtMgl t) throws ApplicationException {
      CmtBasicaExtMglManager cmtBasicaExtMglManager = new CmtBasicaExtMglManager();
        return cmtBasicaExtMglManager.createCamposAdic(t, user, perfil);
    }

    @Override
    public CmtBasicaExtMgl update(CmtBasicaExtMgl t) throws ApplicationException {
        CmtBasicaExtMglManager cmtBasicaExtMglManager = new CmtBasicaExtMglManager();
        return cmtBasicaExtMglManager.createUpdateAdic(t, user, perfil);
    }

    @Override
    public boolean delete(CmtBasicaExtMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtBasicaExtMgl findById(CmtBasicaExtMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CmtBasicaExtMgl> getCamposBasicaExt(CmtBasicaMgl cmtBasicaMgl) throws ApplicationException {
        CmtBasicaExtMglManager cmtBasicaExtMglManager = new CmtBasicaExtMglManager();
        return cmtBasicaExtMglManager.getCamposBasicaExt(cmtBasicaMgl);
    }

}
