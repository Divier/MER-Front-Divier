package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudSubEdificioMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface CmtSolicitudSubEdificioMglFacadeLocal extends BaseFacadeLocal<CmtSolicitudSubEdificioMgl>{
   
    List<CmtSolicitudSubEdificioMgl> findSolicitudSubEdificioBySolicitud(CmtSolicitudCmMgl solicitudCM) throws ApplicationException;

    public CmtSolicitudSubEdificioMgl findById(BigDecimal id) throws ApplicationException;
    
    @Override
    public CmtSolicitudSubEdificioMgl findById(CmtSolicitudSubEdificioMgl cmtSolSubEdificioMgl) throws ApplicationException ;

    void setUser(String mUser, int mPerfil) throws ApplicationException;
    
    public CmtSolicitudSubEdificioMgl crearSolSub(CmtSolicitudSubEdificioMgl t) throws ApplicationException;
    
    CmtSolicitudSubEdificioMgl updateCm(CmtSolicitudSubEdificioMgl cmtSolicitudSubEdificioMgl, String usuario, int perfil)
            throws ApplicationException;

}
