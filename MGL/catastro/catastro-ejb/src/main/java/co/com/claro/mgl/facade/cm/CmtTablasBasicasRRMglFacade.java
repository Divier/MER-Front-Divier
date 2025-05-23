/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtTablasBasicasRRMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import javax.ejb.Stateless;

/**
 *
 * @author bocanegravm
 */
@Stateless
public class CmtTablasBasicasRRMglFacade implements CmtTablasBasicasRRMglFacadeLocal {
    
    
    
      @Override
      public boolean edificioDelete(CmtCuentaMatrizMgl cuentaMatrizMgl, String usuario)
              throws ApplicationException {

        CmtTablasBasicasRRMglManager manager = new CmtTablasBasicasRRMglManager();
        return manager.edificioDelete(cuentaMatrizMgl, usuario);
    }
    
}
