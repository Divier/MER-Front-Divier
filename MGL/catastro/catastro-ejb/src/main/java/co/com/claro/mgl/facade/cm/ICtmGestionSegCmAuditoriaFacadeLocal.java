/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mer.dtos.sp.cursors.CtmGestionSegCMAuditoriaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CtmGestionSegCMAuditoria;
import java.math.BigDecimal;
import java.util.List;

/**
 * interface ICtmGestionSegCmAuditoriaFacadeLocal. Permite manejar   
 * las conexiones al ejb CtmGestionSegCmAuditoriaFacade
 * @author Carlos Andres Caicedo
 * @versión 1.00.000
 */
public interface ICtmGestionSegCmAuditoriaFacadeLocal extends BaseFacadeLocal<CtmGestionSegCMAuditoria> {
    
    public List<CtmGestionSegCMAuditoriaDto> findAllAuditParentAccount(BigDecimal cuentaMatriz)throws ApplicationException;;
    
}
