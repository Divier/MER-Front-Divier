/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface CmtNotasSolicitudMglFacadeLocal{

    public List<CmtNotasSolicitudMgl> findTipoNotasId(BigDecimal tipoNotasId) throws ApplicationException;

    public CmtNotasSolicitudMgl findById(BigDecimal id) throws ApplicationException;
    
    public CmtNotasSolicitudMgl crearNotSol(CmtNotasSolicitudMgl t) throws ApplicationException;

    public List<CmtNotasSolicitudMgl> findNotasBySolicitudId(BigDecimal idSolicitudCm) throws ApplicationException;
    
    void setUser(String mUser, int mPerfil) throws ApplicationException;
    
}
