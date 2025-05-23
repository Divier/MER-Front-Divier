/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtRelacionMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author User
 */
public interface CmtRelacionMglFacadeLocal extends BaseFacadeLocal<CmtRelacionMgl> {
    
    public List<CmtRelacionMgl> findRelacionId(BigDecimal cuentaMatrizId) throws ApplicationException;
}
