/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudTipoSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public interface CmtSolicitudTipoSolicitudMglFacadeLocal extends BaseFacadeLocal <CmtSolicitudTipoSolicitudMgl> {
    
    @Override
    public List<CmtSolicitudTipoSolicitudMgl> findAll() throws ApplicationException;
        
    public void setUser(String mUser, int mPerfil) throws ApplicationException;
    
    public CmtSolicitudTipoSolicitudMgl findByIdSolicitud(BigDecimal idSolicitud) throws ApplicationException;;
  
}
