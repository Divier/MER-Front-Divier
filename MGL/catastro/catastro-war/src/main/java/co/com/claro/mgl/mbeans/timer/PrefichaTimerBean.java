/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.timer;

import co.com.claro.mgl.facade.PreFichaAlertaMglFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Singleton;

/**
 *
 * @author User
 */
@Singleton
public class PrefichaTimerBean {

    @EJB
    PreFichaAlertaMglFacadeLocal preFichaAlertaMglFacadeLocal;


}
