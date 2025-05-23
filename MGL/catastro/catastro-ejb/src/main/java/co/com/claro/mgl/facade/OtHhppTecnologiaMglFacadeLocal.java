/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.OtHhppTecnologiaMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public interface OtHhppTecnologiaMglFacadeLocal extends BaseFacadeLocal<OtHhppTecnologiaMgl>{

      /**
     * Listado de tecnologia por id de ot 
     *
     * @author Juan David Hernandez
     * @param otId
     * @return
     * @throws ApplicationException
     */
    public  List<OtHhppTecnologiaMgl> findOtHhppTecnologiaByOtHhppId
            (BigDecimal otId)
            throws ApplicationException;

    void setUser(String mUser, int mPerfil) throws ApplicationException;
 

}