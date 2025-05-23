/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaAlertaMgl;
import java.util.List;

/**
 *
 * @author User
 */
public interface PreFichaAlertaMglFacadeLocal {
    
    List<PreFichaAlertaMgl> getListPrefichaByFase() throws ApplicationException;
    PreFichaAlertaMgl savePreFichaAlerta(PreFichaAlertaMgl preFichaAlertaMgl) throws ApplicationException;
    PreFichaAlertaMgl updatePreficha(PreFichaAlertaMgl preFichaAlertaMgl) throws ApplicationException;
    
}
