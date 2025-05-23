/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.PreFichaAlertaMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaAlertaMgl;
import java.util.List;

/**
 *
 * @author User
 */
public class PreFichaAlertaMglManager {
    
    public List<PreFichaAlertaMgl> getListPrefichaByFase() throws ApplicationException{
        PreFichaAlertaMglDaoImpl daoImpl = new PreFichaAlertaMglDaoImpl();
        return daoImpl.getListPrefichaByFase();
    }
    
    public PreFichaAlertaMgl savePreFichaAlerta(PreFichaAlertaMgl preFichaAlertaMgl) throws ApplicationException {
        PreFichaAlertaMglDaoImpl daoImpl = new PreFichaAlertaMglDaoImpl();
        return daoImpl.create(preFichaAlertaMgl);
    }
    
    public PreFichaAlertaMgl updatePreFichaAlerta(PreFichaAlertaMgl preFichaAlertaMgl) throws ApplicationException {
        PreFichaAlertaMglDaoImpl daoImpl = new PreFichaAlertaMglDaoImpl();
        return daoImpl.update(preFichaAlertaMgl);
    }
    
}
