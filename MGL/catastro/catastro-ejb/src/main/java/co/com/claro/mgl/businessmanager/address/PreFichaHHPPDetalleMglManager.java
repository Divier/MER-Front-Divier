/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.PreFichaHHPPDetalleMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaHHPPDetalleMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author User
 */
public class PreFichaHHPPDetalleMglManager {
    
    public List<PreFichaHHPPDetalleMgl> savePreFichaDetalleHHPPList(List<PreFichaHHPPDetalleMgl> prefichaDetalleHHHPList) throws ApplicationException {
        PreFichaHHPPDetalleMglDaoImpl daoImpl = new PreFichaHHPPDetalleMglDaoImpl();
        return daoImpl.create(prefichaDetalleHHHPList);
    }
    
    public PreFichaHHPPDetalleMgl getPreFichaDetalleHHPPById(BigDecimal idHHppDetalle) throws ApplicationException {
        PreFichaHHPPDetalleMglDaoImpl daoImpl = new PreFichaHHPPDetalleMglDaoImpl();
        return daoImpl.find(idHHppDetalle);
    }
    
    public List<PreFichaHHPPDetalleMgl> obtenerDetallesHHPP(PreFichaXlsMgl prefichaXls) throws ApplicationException{
         PreFichaHHPPDetalleMglDaoImpl daoImpl = new PreFichaHHPPDetalleMglDaoImpl();
         return daoImpl.obtenerDetallesHHPP(prefichaXls);
    }
}
