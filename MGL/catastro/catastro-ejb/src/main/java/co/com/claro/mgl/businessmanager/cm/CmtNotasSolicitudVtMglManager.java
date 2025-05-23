/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtNotasSolicitudVtMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudVtMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class CmtNotasSolicitudVtMglManager {

    public List<CmtNotasSolicitudVtMgl> findAll() throws ApplicationException {
        List<CmtNotasSolicitudVtMgl> resulList;
        CmtNotasSolicitudVtMglDaoImpl cmtNotasMglDaoImpl
                = new CmtNotasSolicitudVtMglDaoImpl();
        resulList = cmtNotasMglDaoImpl.findAll();
        return resulList;
    }

     /**
     * Lista los diferentes tipos de notas por el ID.
     *
     * @author Juan David Hernandez
     * @param tipoNotasId
     * @return
     * @throws ApplicationException
     */
    public List<CmtNotasSolicitudVtMgl> findTipoNotasId(BigDecimal tipoNotasId) 
            throws ApplicationException {
        CmtNotasSolicitudVtMglDaoImpl cmtNotasMglDaoImpl
                = new CmtNotasSolicitudVtMglDaoImpl();
        return cmtNotasMglDaoImpl.findTipoNotasId(tipoNotasId);
    }

     /**
     * Listado de notas por id de solicitud
     *
     * @author Juan David Hernandez
     * @param idSolicitudCm
     * @return
     * @throws ApplicationException
     */
    public List<CmtNotasSolicitudVtMgl> 
            findNotasBySolicitudId(BigDecimal idSolicitudCm)
            throws ApplicationException {
        CmtNotasSolicitudVtMglDaoImpl cmtNotasMglDaoImpl 
                = new CmtNotasSolicitudVtMglDaoImpl();
        return cmtNotasMglDaoImpl.findNotasBySolicitudId(idSolicitudCm);
    }


      public CmtNotasSolicitudVtMgl create(CmtNotasSolicitudVtMgl 
              cmtNotasMgl, String usuario, int perfil) throws ApplicationException {
          CmtNotasSolicitudVtMglDaoImpl cmtNotasSolicitudMglDaoImpl
                  = new CmtNotasSolicitudVtMglDaoImpl();
          CmtNotasSolicitudVtMgl solicitudSE 
                  = cmtNotasSolicitudMglDaoImpl.createCm(cmtNotasMgl,
                  usuario, perfil);
          return solicitudSE;
    }

    public CmtNotasSolicitudVtMgl update(CmtNotasSolicitudVtMgl cmtNotasMgl)
            throws ApplicationException {
        CmtNotasSolicitudVtMglDaoImpl cmtNotasMglDaoImpl 
                = new CmtNotasSolicitudVtMglDaoImpl();
        return cmtNotasMglDaoImpl.update(cmtNotasMgl);
    }

    public boolean delete(CmtNotasSolicitudVtMgl cmtNotasMgl)
            throws ApplicationException {
        CmtNotasSolicitudVtMglDaoImpl cmtNotasMglDaoImpl
                = new CmtNotasSolicitudVtMglDaoImpl();
        return cmtNotasMglDaoImpl.delete(cmtNotasMgl);
    }

    public CmtNotasSolicitudVtMgl findById(CmtNotasSolicitudVtMgl cmtNotasMgl) 
            throws ApplicationException {
        CmtNotasSolicitudVtMglDaoImpl cmtNotasMglDaoImpl
                = new CmtNotasSolicitudVtMglDaoImpl();
        return cmtNotasMglDaoImpl.find(cmtNotasMgl.getNotasId());
    }

         /**
     * Lista notas tipos por el ID.
     *
     * @author Juan David Hernandez
     * @param id
     * @return
     * @throws ApplicationException
     */
    public CmtNotasSolicitudVtMgl findById(BigDecimal id) 
            throws ApplicationException {
        CmtNotasSolicitudVtMglDaoImpl cmtNotasMglDaoImpl
                = new CmtNotasSolicitudVtMglDaoImpl();
        return cmtNotasMglDaoImpl.find(id);
    }

    public List<CmtNotasSolicitudVtMgl> findNotasSolByIdSolAndBasicaIdTipoNota(
            BigDecimal idSol, BigDecimal basicaIdTipoNota) 
            throws ApplicationException {
        CmtNotasSolicitudVtMglDaoImpl cmtNotasMglDaoImpl 
                = new CmtNotasSolicitudVtMglDaoImpl();
        return cmtNotasMglDaoImpl.findNotasSolByIdSolAndBasicaIdTipoNota
                (idSol, basicaIdTipoNota);
    }
}
