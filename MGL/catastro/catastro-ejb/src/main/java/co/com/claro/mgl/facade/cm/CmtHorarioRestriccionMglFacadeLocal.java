/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgw.agenda.restrition_ccmm.RestritionCcmmRequest;
import co.com.claro.mgw.agenda.restrition_ccmm.SchedulerRestrictionResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface CmtHorarioRestriccionMglFacadeLocal extends BaseFacadeLocal<CmtHorarioRestriccionMgl> {

    public List<CmtHorarioRestriccionMgl> findByHorarioCompania(CmtCompaniaMgl compania) throws ApplicationException;

    public List<CmtHorarioRestriccionMgl> findByCuentaMatrizId(BigDecimal cuentaMatrizId) throws ApplicationException;

    public List<CmtHorarioRestriccionMgl> findBySubEdificioId(BigDecimal cuentaMatrizId) throws ApplicationException;

    public boolean deleteByCompania(CmtCompaniaMgl compania) throws ApplicationException;

    public boolean deleteByCuentaMatrizId(BigDecimal cuentaMatrizId) throws ApplicationException;

    ArrayList<CmtHorarioRestriccionMgl> findBySolicitud(CmtSolicitudCmMgl solicitudCm) throws ApplicationException;

    boolean deleteBySolicitud(CmtSolicitudCmMgl solicitudCm) throws ApplicationException;

    ArrayList<CmtHorarioRestriccionMgl> findByVt(CmtVisitaTecnicaMgl vt) throws ApplicationException;

    boolean deleteByVt(CmtVisitaTecnicaMgl vt) throws ApplicationException;

    public void setUser(String mUser, int mPerfil) throws ApplicationException;
   
    /**
     * metodo para consultar las restricciones de horario en una ccmm o
     * subedificio Autor: victor bocanegra
     *
     * @param restritionCcmmRequest
     * @return SchedulerRestrictionResponse
     */
    public SchedulerRestrictionResponse schedulerCcmmRestrition(RestritionCcmmRequest restritionCcmmRequest);

    
    public List<String> tiemposCcmm();
}
