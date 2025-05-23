/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public interface CmtTipoBasicaExtMglFacadeLocal extends BaseFacadeLocal<CmtTipoBasicaExtMgl>{
    
           /**
     * Función que obtiene los campos del form campos adicionales
     *
     * @author cardenaslb
     * @param t entidad basica extendida
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    
     public List<CmtTipoBasicaExtMgl> getCamposAdic(CmtTipoBasicaMgl t) throws ApplicationException;

    
}
