/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtInfoTecnicaMglManager;
import co.com.claro.mgl.dtos.FiltroInformacionTecnicaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtInfoTecnicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author cardenaslb
 */
@Stateless
public class CmtInfoTecnicaMglFacade implements CmtInfoTecnicaMglFacadeLocal {

    private String user = "";
    private int perfil = 0;

    @Override
    public List<CmtInfoTecnicaMgl> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtInfoTecnicaMgl create(CmtInfoTecnicaMgl t) throws ApplicationException {
        CmtInfoTecnicaMglManager cmtInfoTecnicaMglManager = new CmtInfoTecnicaMglManager();
        return cmtInfoTecnicaMglManager.create(t,user,perfil);
    }

    @Override
    public CmtInfoTecnicaMgl update(CmtInfoTecnicaMgl t) throws ApplicationException {
        CmtInfoTecnicaMglManager cmtInfoTecnicaMglManager = new CmtInfoTecnicaMglManager();
        return cmtInfoTecnicaMglManager.update(t,user,perfil);
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

    @Override
    public List<CmtInfoTecnicaMgl> findBySubEdificioId(CmtSubEdificioMgl subEdificioId) throws ApplicationException {
        CmtInfoTecnicaMglManager cmtInfoTecnicaMglManager = new CmtInfoTecnicaMglManager();
        return cmtInfoTecnicaMglManager.findBySubEdificioId(subEdificioId);
    }

    @Override
    public boolean delete(CmtInfoTecnicaMgl t) throws ApplicationException {
        CmtInfoTecnicaMglManager cmtInfoTecnicaMglManager = new CmtInfoTecnicaMglManager();
        return cmtInfoTecnicaMglManager.deleteCm(t, user, perfil);
    }

    @Override
    public CmtInfoTecnicaMgl findById(CmtInfoTecnicaMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
    @Override
    public FiltroInformacionTecnicaDto findInformacionTecnica(
            BigDecimal subEdificio,boolean contar, int firstResult, int maxResults)
            throws ApplicationException {
        CmtInfoTecnicaMglManager cmtInfoTecnicaMglManager = new CmtInfoTecnicaMglManager();

        return cmtInfoTecnicaMglManager.getInfoTecnicaSearch(subEdificio,contar, firstResult, maxResults);
    }

    @Override
    public CmtInfoTecnicaMgl createCm(CmtInfoTecnicaMgl t) throws ApplicationException {
        CmtInfoTecnicaMglManager cmtInfoTecnicaMglManager = new CmtInfoTecnicaMglManager();
        return cmtInfoTecnicaMglManager.create(t, user, perfil);
    }

    @Override
    public CmtInfoTecnicaMgl updateCm(CmtInfoTecnicaMgl t) throws ApplicationException {
        CmtInfoTecnicaMglManager cmtInfoTecnicaMglManager = new CmtInfoTecnicaMglManager();
        return cmtInfoTecnicaMglManager.update(t, user, perfil);
    }

}
