/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtNotasSolicitudMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CmtNotasSolicitudMglManager {

    public List<CmtNotasSolicitudMgl> findAll() throws ApplicationException {
        List<CmtNotasSolicitudMgl> resulList;
        CmtNotasSolicitudMglDaoImpl cmtNotasMglDaoImpl = new CmtNotasSolicitudMglDaoImpl();
        resulList = cmtNotasMglDaoImpl.findAll();
        return resulList;
    }

    public List<CmtNotasSolicitudMgl> findTipoNotasId(BigDecimal tipoNotasId) throws ApplicationException {
        CmtNotasSolicitudMglDaoImpl cmtNotasMglDaoImpl = new CmtNotasSolicitudMglDaoImpl();
        return cmtNotasMglDaoImpl.findTipoNotasId(tipoNotasId);
    }

    public List<CmtNotasSolicitudMgl> findNotasBySolicitudId(BigDecimal idSolicitudCm) throws ApplicationException {
        CmtNotasSolicitudMglDaoImpl cmtNotasMglDaoImpl = new CmtNotasSolicitudMglDaoImpl();
        return cmtNotasMglDaoImpl.findNotasBySolicitudId(idSolicitudCm);
    }


      public CmtNotasSolicitudMgl create(CmtNotasSolicitudMgl cmtNotasMgl, String usuario, int perfil) throws ApplicationException {
          CmtNotasSolicitudMglDaoImpl cmtNotasSolicitudMglDaoImpl = new CmtNotasSolicitudMglDaoImpl();
          CmtNotasSolicitudMgl solicitudSE = cmtNotasSolicitudMglDaoImpl.createCm(cmtNotasMgl, usuario, perfil);
          return solicitudSE;
    }

    public CmtNotasSolicitudMgl update(CmtNotasSolicitudMgl cmtNotasMgl) throws ApplicationException {
        CmtNotasSolicitudMglDaoImpl cmtNotasMglDaoImpl = new CmtNotasSolicitudMglDaoImpl();
        return cmtNotasMglDaoImpl.update(cmtNotasMgl);
    }

    public boolean delete(CmtNotasSolicitudMgl cmtNotasMgl) throws ApplicationException {
        CmtNotasSolicitudMglDaoImpl cmtNotasMglDaoImpl = new CmtNotasSolicitudMglDaoImpl();
        return cmtNotasMglDaoImpl.delete(cmtNotasMgl);
    }

    public CmtNotasSolicitudMgl findById(CmtNotasSolicitudMgl cmtNotasMgl) throws ApplicationException {
        CmtNotasSolicitudMglDaoImpl cmtNotasMglDaoImpl = new CmtNotasSolicitudMglDaoImpl();
        return cmtNotasMglDaoImpl.find(cmtNotasMgl.getNotasId());
    }

    public CmtNotasSolicitudMgl findById(BigDecimal id) throws ApplicationException {
        CmtNotasSolicitudMglDaoImpl cmtNotasMglDaoImpl = new CmtNotasSolicitudMglDaoImpl();
        return cmtNotasMglDaoImpl.find(id);
    }

    public List<CmtNotasSolicitudMgl> findNotasSolByIdSolAndBasicaIdTipoNota(
            BigDecimal idSol, BigDecimal basicaIdTipoNota) throws ApplicationException {
        CmtNotasSolicitudMglDaoImpl cmtNotasMglDaoImpl = new CmtNotasSolicitudMglDaoImpl();
        return cmtNotasMglDaoImpl.findNotasSolByIdSolAndBasicaIdTipoNota(idSol, basicaIdTipoNota);
    }
}
