/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtNotasHhppDetalleVtMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public interface CmtNotasHhppDetalleVtMglFacadeLocal extends BaseFacadeLocal<CmtNotasHhppDetalleVtMgl> {

    public List<CmtNotasHhppDetalleVtMgl> findNotasByNotasId(BigDecimal notasId) throws ApplicationException;
    
    void setUser(String mUser, int mPerfil) throws ApplicationException;
    
    public CmtNotasHhppDetalleVtMgl create(CmtNotasHhppDetalleVtMgl cmtNotasDetalleMgl, String usuario, int perfil) throws ApplicationException;
    
}
