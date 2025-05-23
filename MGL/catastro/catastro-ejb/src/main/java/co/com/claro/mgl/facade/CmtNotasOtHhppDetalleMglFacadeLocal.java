/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtNotasOtHhppDetalleMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public interface CmtNotasOtHhppDetalleMglFacadeLocal extends BaseFacadeLocal<CmtNotasOtHhppDetalleMgl> {

    public List<CmtNotasOtHhppDetalleMgl> findNotasByNotasId(BigDecimal notasId) throws ApplicationException;
    
    void setUser(String mUser, int mPerfil) throws ApplicationException;
}
