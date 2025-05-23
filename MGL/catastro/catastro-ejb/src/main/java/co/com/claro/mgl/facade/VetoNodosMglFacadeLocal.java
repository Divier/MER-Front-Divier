/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.VetoNodosMgl;

/**
 *
 * @author Juan David Hernandez
 */
public interface VetoNodosMglFacadeLocal extends BaseFacadeLocal<VetoNodosMgl>{
     
    public void setUser(String mUser, int mPerfil) throws ApplicationException;
    
     /**
     * Crea un veto nodos en la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @return
     * @throws ApplicationException
     */
    public VetoNodosMgl createVetoNodos(VetoNodosMgl t)
            throws ApplicationException;
    
}
