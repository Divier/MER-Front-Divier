/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.VetoCiudadMgl;

/**
 *
 * @author Juan David Hernandez
 */
public interface VetoCiudadMglFacadeLocal extends BaseFacadeLocal<VetoCiudadMgl>{
    
     public void setUser(String mUser, int mPerfil) throws ApplicationException;
    
     /**
     * Crea un veto ciudad en la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @return
     * @throws ApplicationException
     */
    public VetoCiudadMgl createVetoCiudad(VetoCiudadMgl t)
            throws ApplicationException;
}
