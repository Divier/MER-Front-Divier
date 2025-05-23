/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.PreFichaGeorreferenciaMglNewDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaGeorreferenciaMglNew;
import java.math.BigDecimal;
import java.util.List;

/**
 * Manager para operaciones en TEC_PREFICHA_GEOREFERENCIA_NEW
 * 
 * @author Miguel Barrios Hitss
 * @version 1.1 Rev Miguel Barrios Hitss
 */
public class PreFichaGeorreferenciaMglNewManager {
    
    public List<PreFichaGeorreferenciaMglNew> savePreFichaGeoNewList(List<PreFichaGeorreferenciaMglNew> prefichaGeoList) throws ApplicationException {
        PreFichaGeorreferenciaMglNewDaoImpl daoImpl = new PreFichaGeorreferenciaMglNewDaoImpl();
        return daoImpl.create(prefichaGeoList);
    }
    
    public PreFichaGeorreferenciaMglNew findPreFichaGeorreferenciaNewByIdPrefichaXls(BigDecimal id) throws ApplicationException{
     PreFichaGeorreferenciaMglNewDaoImpl daoImpl = new PreFichaGeorreferenciaMglNewDaoImpl();
     return daoImpl.findPreFichaGeorreferenciaByIdPrefichaXls(id);    
    }
    
}
