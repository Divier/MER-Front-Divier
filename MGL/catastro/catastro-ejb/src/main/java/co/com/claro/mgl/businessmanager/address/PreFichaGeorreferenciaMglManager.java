/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.PreFichaGeorreferenciaMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaGeorreferenciaMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author User
 */
public class PreFichaGeorreferenciaMglManager {
    
    public List<PreFichaGeorreferenciaMgl> savePreFichaGeoList(List<PreFichaGeorreferenciaMgl> prefichaGeoList) throws ApplicationException {
        PreFichaGeorreferenciaMglDaoImpl daoImpl = new PreFichaGeorreferenciaMglDaoImpl();
        return daoImpl.create(prefichaGeoList);
    }
    
    public PreFichaGeorreferenciaMgl findPreFichaGeorreferenciaByIdPrefichaXls(BigDecimal id) throws ApplicationException{
     PreFichaGeorreferenciaMglDaoImpl daoImpl = new PreFichaGeorreferenciaMglDaoImpl();
     return daoImpl.findPreFichaGeorreferenciaByIdPrefichaXls(id);    
    }
    
}
