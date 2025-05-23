/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public interface CmtBasicaExtMglFacadeLocal extends BaseFacadeLocal<CmtBasicaExtMgl> {
    
     /**
     * Permite obtener los campos de la tabla cmtBasicaExt para mostrarlos
     *
     * @author cardenaslb
     * @param cmtBasicaMgl
     * @return lista
     * @throws ApplicationException
     */
    public List<CmtBasicaExtMgl> getCamposBasicaExt(CmtBasicaMgl cmtBasicaMgl) throws ApplicationException;
    
    
    
    
    
}
