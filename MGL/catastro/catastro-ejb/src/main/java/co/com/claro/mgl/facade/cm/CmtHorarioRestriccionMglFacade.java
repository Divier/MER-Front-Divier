/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtHorarioRestriccionMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgw.agenda.restrition_ccmm.RestritionCcmmRequest;
import co.com.claro.mgw.agenda.restrition_ccmm.SchedulerRestrictionResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class CmtHorarioRestriccionMglFacade implements CmtHorarioRestriccionMglFacadeLocal {

    private String user = "";
    private int perfil = 0;

    @Override
    public List<CmtHorarioRestriccionMgl> findAll() throws ApplicationException {
        CmtHorarioRestriccionMglManager cmtHorarioRestriccionMglManager = new CmtHorarioRestriccionMglManager();
        return cmtHorarioRestriccionMglManager.findAll();
    }

    @Override
    public CmtHorarioRestriccionMgl create(CmtHorarioRestriccionMgl t) throws ApplicationException {
        CmtHorarioRestriccionMglManager cmtHorarioRestriccionMglManager = new CmtHorarioRestriccionMglManager();
        return cmtHorarioRestriccionMglManager.create(t,user,perfil);
    }

    @Override
    public boolean delete(CmtHorarioRestriccionMgl t) throws ApplicationException {
        CmtHorarioRestriccionMglManager cmtHorarioRestriccionMglManager = new CmtHorarioRestriccionMglManager();
        return cmtHorarioRestriccionMglManager.delete(t, user, perfil);
    }

    @Override
    public boolean deleteByCompania(CmtCompaniaMgl compania) throws ApplicationException {
        CmtHorarioRestriccionMglManager cmtHorarioRestriccionMglManager = new CmtHorarioRestriccionMglManager();
        return cmtHorarioRestriccionMglManager.deleteByCompania(compania,user,perfil);
    }

    @Override
    public boolean deleteByCuentaMatrizId(BigDecimal cuentaMatrizId) throws ApplicationException {
        CmtHorarioRestriccionMglManager cmtHorarioRestriccionMglManager = new CmtHorarioRestriccionMglManager();
        return cmtHorarioRestriccionMglManager.deleteByCuentaMatrizId(cuentaMatrizId,user,perfil);
    }

    @Override
    public List<CmtHorarioRestriccionMgl> findByHorarioCompania(CmtCompaniaMgl compania) throws ApplicationException {
        CmtHorarioRestriccionMglManager cmtHorarioRestriccionMglManager = new CmtHorarioRestriccionMglManager();
        return cmtHorarioRestriccionMglManager.findByHorarioCompania(compania);

    }

    @Override
    public List<CmtHorarioRestriccionMgl> findByCuentaMatrizId(BigDecimal cuentaMatrizId) throws ApplicationException {
        CmtHorarioRestriccionMglManager cmtHorarioRestriccionMglManager = new CmtHorarioRestriccionMglManager();
        return cmtHorarioRestriccionMglManager.findByCuentaMatrizId(cuentaMatrizId);
    }
    
    @Override
     public List<CmtHorarioRestriccionMgl> findBySubEdificioId(BigDecimal subEdificioId) throws ApplicationException {
        CmtHorarioRestriccionMglManager cmtHorarioRestriccionMglManager = new CmtHorarioRestriccionMglManager();
        return cmtHorarioRestriccionMglManager.findBySubEdificioId(subEdificioId);
    }

    public CmtHorarioRestriccionMgl findById(BigDecimal Id) throws ApplicationException {
        CmtHorarioRestriccionMglManager cmtHorarioRestriccionMglManager = new CmtHorarioRestriccionMglManager();
        return cmtHorarioRestriccionMglManager.findById(Id);
    }

    @Override
    public CmtHorarioRestriccionMgl update(CmtHorarioRestriccionMgl t) throws ApplicationException {
        CmtHorarioRestriccionMglManager cmtHorarioRestriccionMglManager = new CmtHorarioRestriccionMglManager();
        return cmtHorarioRestriccionMglManager.update(t, user, perfil);
    }

    @Override
    public CmtHorarioRestriccionMgl findById(CmtHorarioRestriccionMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<CmtHorarioRestriccionMgl> findBySolicitud(CmtSolicitudCmMgl solicitudCm) throws ApplicationException {
        CmtHorarioRestriccionMglManager manager = new CmtHorarioRestriccionMglManager();
        return manager.findBySolicitud(solicitudCm);
    }

    @Override
    public boolean deleteBySolicitud(CmtSolicitudCmMgl solicitudCm) throws ApplicationException {
        CmtHorarioRestriccionMglManager manager = new CmtHorarioRestriccionMglManager();
        return manager.deleteBySolicitud(solicitudCm, user, perfil);
    }
    
    @Override
    public ArrayList<CmtHorarioRestriccionMgl> findByVt(CmtVisitaTecnicaMgl vt) throws ApplicationException {
        CmtHorarioRestriccionMglManager manager = new CmtHorarioRestriccionMglManager();
        return manager.findByVt(vt);
    }

    @Override
    public boolean deleteByVt(CmtVisitaTecnicaMgl vt) throws ApplicationException {
        CmtHorarioRestriccionMglManager manager = new CmtHorarioRestriccionMglManager();
        return manager.deleteByVt(vt, user, perfil);
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
     * metodo para consultar las restricciones de horario en una ccmm o
     * subedificio Autor: victor bocanegra
     *
     * @param restritionCcmmRequest
     * @return SchedulerRestrictionResponse
     */
    @Override
    public SchedulerRestrictionResponse schedulerCcmmRestrition(RestritionCcmmRequest restritionCcmmRequest) {
        
        CmtHorarioRestriccionMglManager manager = new CmtHorarioRestriccionMglManager();
        return manager.schedulerCcmmRestrition(restritionCcmmRequest);
        
    }
    
    @Override
    public List<String> tiemposCcmm() {
        CmtHorarioRestriccionMglManager manager = new CmtHorarioRestriccionMglManager();
        return manager.tiemposCcmm();
    }

}
