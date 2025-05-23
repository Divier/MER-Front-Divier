/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;

/**
 *
 * @author bocanegravm
 */
public interface CmtTablasBasicasRRMglFacadeLocal {

    public boolean edificioDelete(CmtCuentaMatrizMgl cuentaMatrizMgl, String usuario)
            throws ApplicationException;
}
