/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.PreFichaAlertaMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaAlertaMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class PreFichaAlertaMglFacade implements PreFichaAlertaMglFacadeLocal{

    @Override
    public List<PreFichaAlertaMgl> getListPrefichaByFase() throws ApplicationException {
        PreFichaAlertaMglManager manager = new PreFichaAlertaMglManager();
        return manager.getListPrefichaByFase();
    }

    @Override
    public PreFichaAlertaMgl savePreFichaAlerta(PreFichaAlertaMgl preFichaAlertaMgl) throws ApplicationException {
        PreFichaAlertaMglManager manager = new PreFichaAlertaMglManager();
        return manager.savePreFichaAlerta(preFichaAlertaMgl);
    }
    
    @Override
    public PreFichaAlertaMgl updatePreficha(PreFichaAlertaMgl preFichaAlertaMgl) throws ApplicationException {
        PreFichaAlertaMglManager manager = new PreFichaAlertaMglManager();
        return manager.updatePreFichaAlerta(preFichaAlertaMgl);
    }

    
}
