/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtTiempoSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public interface CmtTiempoSolicitudMglFacadeLocal extends BaseFacadeLocal <CmtTiempoSolicitudMgl>{

    @Override
    public List<CmtTiempoSolicitudMgl> findAll() throws ApplicationException;

    public void setUser(String mUser, int mPerfil) throws ApplicationException;

     /**
     * Obtiene tiempos de traza por IdSolicitud
     *
     * @author Juan David Hernandez
     * @param idSolicitud
     * @return CmtTiempoSolicitudMgl
     * @throws ApplicationException
     */
    public CmtTiempoSolicitudMgl findTiemposBySolicitud(BigDecimal idSolicitud) throws ApplicationException;

}
