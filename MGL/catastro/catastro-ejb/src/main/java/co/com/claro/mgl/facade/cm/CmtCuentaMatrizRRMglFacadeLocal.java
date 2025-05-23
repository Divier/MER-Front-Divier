/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;

/**
 *
 * @author bocanegravm
 */
public interface CmtCuentaMatrizRRMglFacadeLocal {
    
    
    /**
     * Actualiza la Infomacion general de la CM.Permite actualizar la
 informacion general de la CM en RR.
     *
     * @author Victor Bocanegra
     * @param subEdificioMgl subEdificio general o unico a actualizar
     * @param usuario
     * @return si se realizo la actualizacion del subEdificio
     * @throws ApplicationException
     */
   
    public boolean updateInfoCm(CmtSubEdificioMgl subEdificioMgl, String usuario)
            throws ApplicationException;
    
}
