/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mer.dtos.sp.cursors.CtmGestionSegCMDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CtmGestionSegCM;
import java.math.BigDecimal;

/**
 * interface ICtmGestionSegCmFacadeLocal. Permite manejar   
 * las conexiones al ejb CtmGestionSegCmFacade
 * @author Carlos Andres Caicedo
 * @versión 1.00.000
 */
public interface ICtmGestionSegCmFacadeLocal extends BaseFacadeLocal<CtmGestionSegCM>{
    
        CtmGestionSegCMDto findAllManagementAccount(BigDecimal cuentaMatriz)throws ApplicationException;
        
        CtmGestionSegCMDto save(CtmGestionSegCMDto t) throws ApplicationException;

    
}
