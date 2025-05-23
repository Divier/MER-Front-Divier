package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtSolicitudSubEdificioMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudSubEdificioMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CmtSolicitudSubEdificioMglManager {

    public List<CmtSolicitudSubEdificioMgl> findAll() throws ApplicationException {
        List<CmtSolicitudSubEdificioMgl> resulList;
        CmtSolicitudSubEdificioMglDaoImpl cmtSolicitudSubEdificioMglDaoImpl = new CmtSolicitudSubEdificioMglDaoImpl();
        resulList = cmtSolicitudSubEdificioMglDaoImpl.findAll();
        return resulList;
    }

    public List<CmtSolicitudSubEdificioMgl> findSolicitudSubEdificioBySolicitud(CmtSolicitudCmMgl solicitudCM) throws ApplicationException {
        CmtSolicitudSubEdificioMglDaoImpl cmtSolicitudSubEdificioMglDaoImpl = new CmtSolicitudSubEdificioMglDaoImpl();
        return cmtSolicitudSubEdificioMglDaoImpl.findSolicitudSubEdificioBySolicitud(solicitudCM);
    }

    public CmtSolicitudSubEdificioMgl create(CmtSolicitudSubEdificioMgl solSe, String usuario, int perfil) throws ApplicationException {
        CmtSolicitudSubEdificioMglDaoImpl cmtSolicitudSubEdificioMglDaoImpl = new CmtSolicitudSubEdificioMglDaoImpl();
        CmtSolicitudSubEdificioMgl solicitudSE = cmtSolicitudSubEdificioMglDaoImpl.createCm(solSe, usuario, perfil);
        return solicitudSE;
    }

    public CmtSolicitudSubEdificioMgl update(CmtSolicitudSubEdificioMgl cmtSolicitudSubEdificioMgl) throws ApplicationException {
        CmtSolicitudSubEdificioMglDaoImpl cmtSolicitudSubEdificioMglDaoImpl = new CmtSolicitudSubEdificioMglDaoImpl();
        return cmtSolicitudSubEdificioMglDaoImpl.update(cmtSolicitudSubEdificioMgl);
    }

    public boolean delete(CmtSolicitudSubEdificioMgl cmtSolicitudSubEdificioMgl) throws ApplicationException {
        CmtSolicitudSubEdificioMglDaoImpl cmtSolicitudSubEdificioMglDaoImpl = new CmtSolicitudSubEdificioMglDaoImpl();
        return cmtSolicitudSubEdificioMglDaoImpl.delete(cmtSolicitudSubEdificioMgl);
    }

    public CmtSolicitudSubEdificioMgl findById(CmtSolicitudSubEdificioMgl cmtSolSubEdificioMgl) throws ApplicationException {
        CmtSolicitudSubEdificioMglDaoImpl cmtSolicitudSubEdificioMglDaoImpl = new CmtSolicitudSubEdificioMglDaoImpl();
        return cmtSolicitudSubEdificioMglDaoImpl.find(cmtSolSubEdificioMgl.getSolSubEdificioId());
    }

    public CmtSolicitudSubEdificioMgl findById(BigDecimal id) throws ApplicationException {
        CmtSolicitudSubEdificioMglDaoImpl cmtSolicitudSubEdificioMglDaoImpl = new CmtSolicitudSubEdificioMglDaoImpl();
        return cmtSolicitudSubEdificioMglDaoImpl.find(id);
    }
    
      public CmtSolicitudSubEdificioMgl updateCm
              (CmtSolicitudSubEdificioMgl cmtSolicitudSubEdificioMgl, String usuario, int perfil) 
              throws ApplicationException {
        CmtSolicitudSubEdificioMglDaoImpl cmtSolicitudSubEdificioMglDaoImpl = new CmtSolicitudSubEdificioMglDaoImpl();
        return cmtSolicitudSubEdificioMglDaoImpl.updateCm(cmtSolicitudSubEdificioMgl, usuario, perfil);
    }

}
