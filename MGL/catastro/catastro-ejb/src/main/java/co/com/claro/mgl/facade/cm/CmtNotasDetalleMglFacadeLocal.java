/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasDetalleMgl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface CmtNotasDetalleMglFacadeLocal extends BaseFacadeLocal<CmtNotasDetalleMgl> {

    public List<CmtNotasDetalleMgl> findNotasByNotasId(BigDecimal notasId) throws ApplicationException;

    void setUser(String mUser, int mPerfil) throws ApplicationException;

    public List<AuditoriaDto> construirAuditoria(CmtNotasDetalleMgl cmtNotasDetalleMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;  
}
