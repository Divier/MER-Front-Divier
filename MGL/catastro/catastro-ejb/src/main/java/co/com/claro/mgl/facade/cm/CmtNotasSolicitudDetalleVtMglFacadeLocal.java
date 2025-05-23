/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudDetalleVtMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public interface CmtNotasSolicitudDetalleVtMglFacadeLocal extends BaseFacadeLocal<CmtNotasSolicitudDetalleVtMgl> {

    public List<CmtNotasSolicitudDetalleVtMgl> findNotasByNotasId(BigDecimal notasId) throws ApplicationException;
    
    void setUser(String mUser, int mPerfil) throws ApplicationException;
}
