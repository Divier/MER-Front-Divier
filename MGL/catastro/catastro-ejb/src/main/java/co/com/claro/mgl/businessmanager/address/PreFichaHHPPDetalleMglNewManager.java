/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.PreFichaHHPPDetalleMglNewDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaHHPPDetalleMglNew;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMglNew;
import java.math.BigDecimal;
import java.util.List;

/**
 * Manager para operaciones en TEC_PREF_TEC_HAB_NEW
 * 
 * @author Miguel Barrios Hitss
 * @version 1.1 Rev Miguel Barrios Hitss
 */
public class PreFichaHHPPDetalleMglNewManager {
    
    public List<PreFichaHHPPDetalleMglNew> savePreFichaDetalleHHPPNewList(List<PreFichaHHPPDetalleMglNew> prefichaDetalleHHHPList) throws ApplicationException {
        PreFichaHHPPDetalleMglNewDaoImpl daoImpl = new PreFichaHHPPDetalleMglNewDaoImpl();
        return daoImpl.create(prefichaDetalleHHHPList);
    }
    
    public PreFichaHHPPDetalleMglNew getPreFichaDetalleHHPPNewById(BigDecimal idHHppDetalle) throws ApplicationException {
        PreFichaHHPPDetalleMglNewDaoImpl daoImpl = new PreFichaHHPPDetalleMglNewDaoImpl();
        return daoImpl.find(idHHppDetalle);
    }
    
    public List<PreFichaHHPPDetalleMglNew> obtenerDetallesHHPPNew(PreFichaXlsMglNew prefichaXls) throws ApplicationException{
         PreFichaHHPPDetalleMglNewDaoImpl daoImpl = new PreFichaHHPPDetalleMglNewDaoImpl();
         return daoImpl.obtenerDetallesHHPP(prefichaXls);
    }
}

