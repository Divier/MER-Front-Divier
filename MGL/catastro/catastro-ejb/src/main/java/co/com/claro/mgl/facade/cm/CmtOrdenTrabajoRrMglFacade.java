/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.cmas400.ejb.respons.ResponseOtEdificiosList;
import co.com.claro.mgl.businessmanager.cm.CmtOrdenTrabajoRrMglManager;
import co.com.claro.mgl.error.ApplicationException;
import javax.ejb.Stateless;

/**
 *
 * @author bocanegravm
 */

@Stateless
public class CmtOrdenTrabajoRrMglFacade implements CmtOrdenTrabajoRrMglFacadeLocal {
    
        
    /**
     * Metodo para consultar los trabajos que tiene una CM en RR
     *
     * @author Victor bocanegra
     * @param numeroCuenta cnumero de la cuenta matriz
     * @return ResponseOtEdificiosList encontrado
     * @throws ApplicationException
     */
    @Override
    public ResponseOtEdificiosList ordenTrabajoEdificioQuery(String numeroCuenta)
            throws ApplicationException {
        CmtOrdenTrabajoRrMglManager manager = new CmtOrdenTrabajoRrMglManager();
        return manager.ordenTrabajoEdificioQuery(numeroCuenta);

    }
}
