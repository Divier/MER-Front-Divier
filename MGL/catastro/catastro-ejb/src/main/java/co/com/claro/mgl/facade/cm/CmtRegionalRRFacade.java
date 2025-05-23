/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtRegionalRrManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author cardenaslb
 */
@Stateless
public class CmtRegionalRRFacade implements CmtRegionalRRFacadeLocal {

    private String user = "";
    private int perfil = 0;

    @Override
    public List<CmtRegionalRr> findAllRegional() throws ApplicationException {
        CmtRegionalRrManager cmtRegionalRrManager
                = new CmtRegionalRrManager();
        return cmtRegionalRrManager.findAllRegional();
    }
    
    @Override
    public List<CmtRegionalRr> findAllRegionalActive() throws ApplicationException {
        CmtRegionalRrManager cmtRegionalRrManager
                = new CmtRegionalRrManager();
        return cmtRegionalRrManager.findAllRegionalActive();
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
     * bocanegravm Metodo para consultar una regional por Id
     *
     * @param idRegional
     * @return CmtRegionalRr
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public CmtRegionalRr findRegionalById(BigDecimal idRegional)
            throws ApplicationException {
        
        CmtRegionalRrManager cmtRegionalRrManager = new CmtRegionalRrManager();
        return cmtRegionalRrManager.findRegionalById(idRegional);
    }
    
        
    /**
     * bocanegravm Metodo para consultar una regional por codigo
     *
     * @param codigo
     * @return CmtRegionalRr
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public CmtRegionalRr findRegionalByCod(String codigo)
            throws ApplicationException {

        CmtRegionalRrManager cmtRegionalRrManager = new CmtRegionalRrManager();
        return cmtRegionalRrManager.findRegionalByCod(codigo);
    }
    
    /**
     * bocanegravm Metodo para consultar todas las regionales en BD
     *
     * @return List<CmtRegionalRr>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public List<CmtRegionalRr> findAllRegionales()
            throws ApplicationException {

        CmtRegionalRrManager cmtRegionalRrManager = new CmtRegionalRrManager();
        return cmtRegionalRrManager.findAllRegionales();

    }
}
