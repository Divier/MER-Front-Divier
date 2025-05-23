/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtFlujosInicialesCmDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtFlujosInicialesCm;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class CmtFlujosInicialesCmManager {
    CmtFlujosInicialesCmDaoImpl cmtEstadosInicialesCmDaoImpl= new CmtFlujosInicialesCmDaoImpl();
    
      public CmtFlujosInicialesCm create(CmtFlujosInicialesCm cmtFlujosInicialesCm,String mUser, int mPerfil) throws ApplicationException {
        return cmtEstadosInicialesCmDaoImpl.createCm(cmtFlujosInicialesCm, mUser, mPerfil);
    }

    public CmtFlujosInicialesCm update(CmtFlujosInicialesCm cmtFlujosInicialesCm,String mUser, int mPerfil) throws ApplicationException {
        return cmtEstadosInicialesCmDaoImpl.updateCm(cmtFlujosInicialesCm, mUser, mPerfil);
    }

    public boolean delete(CmtFlujosInicialesCm cmtFlujosInicialesCm,String mUser, int mPerfil) throws ApplicationException {
        return cmtEstadosInicialesCmDaoImpl.deleteCm(cmtFlujosInicialesCm, mUser, mPerfil);
        
    }
    
    
    public List<CmtFlujosInicialesCm> getEstadosIniCMByEstadoxFlujoId(CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl) throws ApplicationException {
        List<CmtFlujosInicialesCm> listaBasica = new ArrayList<>();
        listaBasica = cmtEstadosInicialesCmDaoImpl.getEstadosIniCMByEstadoxFlujoId(cmtEstadoxFlujoMgl);
		 listaBasica = cmtEstadosInicialesCmDaoImpl.getEstadosIniCMByEstadoxFlujoId(cmtEstadoxFlujoMgl);
        return listaBasica;
    }
}
