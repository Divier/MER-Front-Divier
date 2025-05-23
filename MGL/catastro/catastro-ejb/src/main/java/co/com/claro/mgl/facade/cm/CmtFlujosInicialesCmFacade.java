/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtFlujosInicialesCmManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtFlujosInicialesCm;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author cardenaslb
 */
@Stateless
public class CmtFlujosInicialesCmFacade implements CmtFlujosInicialesCmFacadeLocal{
     private String user;
    private int perfil;
    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
         this.user = mUser;
        this.perfil = mPerfil;
    }

    @Override
    public CmtFlujosInicialesCm update(CmtFlujosInicialesCm t, String usuario, int perfilUsu) throws ApplicationException {
        CmtFlujosInicialesCmManager cmtEstadosInicialesCmManager = new CmtFlujosInicialesCmManager();
        return cmtEstadosInicialesCmManager.update(t, user, perfil);
    }

    @Override
    public boolean delete(CmtFlujosInicialesCm t, String usuario, int perfilUsu) throws ApplicationException {
       CmtFlujosInicialesCmManager cmtEstadosInicialesCmManager = new CmtFlujosInicialesCmManager();
        return cmtEstadosInicialesCmManager.delete(t, user, perfil);
    }

    @Override
    public CmtFlujosInicialesCm create(CmtFlujosInicialesCm t, String usuario, int perfilUsu) throws ApplicationException {
       CmtFlujosInicialesCmManager cmtEstadosInicialesCmManager = new CmtFlujosInicialesCmManager();
       return  cmtEstadosInicialesCmManager.create(t, user, perfil);
    }

    @Override
    public List<CmtFlujosInicialesCm> getEstadosIniCMByEstadoxFlujoId(CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl) throws ApplicationException {
        CmtFlujosInicialesCmManager cmtEstadosInicialesCmManager = new CmtFlujosInicialesCmManager();
        return cmtEstadosInicialesCmManager.getEstadosIniCMByEstadoxFlujoId(cmtEstadoxFlujoMgl);
    }
}
