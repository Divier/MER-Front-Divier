/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.VetoCanalMgl;

/**
 *
 * @author Juan David Hernandez
 */
public interface VetoCanalMglFacadeLocal extends BaseFacadeLocal<VetoCanalMgl>{
    
     public void setUser(String mUser, int mPerfil) throws ApplicationException;
     
     /**
     * Crea un veto canal en la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @return
     * @throws ApplicationException
     */
    public VetoCanalMgl createVetoCanal(VetoCanalMgl t)
            throws ApplicationException;
}
