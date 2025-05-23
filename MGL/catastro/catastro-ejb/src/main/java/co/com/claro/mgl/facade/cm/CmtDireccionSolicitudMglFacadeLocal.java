/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.error.ApplicationExceptionCM;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 * interface mapeo tabla CMT_DIRECCION_SOLICITUD
 *
 * @author yimy diaz
 * @versión 1.00.0000
 */
public interface CmtDireccionSolicitudMglFacadeLocal
        extends BaseFacadeLocal<CmtDireccionSolicitudMgl> {

    public CmtDireccionSolicitudMgl findById(BigDecimal id)
            throws ApplicationException;

    public List<CmtDireccionSolicitudMgl> findSolicitudId(BigDecimal idSolicitud);
    
    public void setUser(String mUser, int mPerfil) throws ApplicationException;

    
    
    CmtDireccionSolicitudMgl crearDir(CmtDireccionSolicitudMgl dir) throws ApplicationException;
    
     public List<CmtSolicitudCmMgl> findByDrDireccion(DrDireccion drDireccion, String comunidad)
            throws ApplicationException;
     public void siExistenSolictudesEnCurso(DrDireccion drDireccion,String comunidad) throws ApplicationExceptionCM;
}
