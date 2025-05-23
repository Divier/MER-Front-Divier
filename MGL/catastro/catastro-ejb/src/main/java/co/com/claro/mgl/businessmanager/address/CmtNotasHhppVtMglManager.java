/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.CmtNotasHhppVtMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtNotasHhppVtMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class CmtNotasHhppVtMglManager {

    public List<CmtNotasHhppVtMgl> findAll() throws ApplicationException {
        List<CmtNotasHhppVtMgl> resulList;
        CmtNotasHhppVtMglDaoImpl cmtNotasMglDaoImpl
                = new CmtNotasHhppVtMglDaoImpl();
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
    public List<CmtNotasHhppVtMgl> findTipoNotasId(BigDecimal tipoNotasId) 
            throws ApplicationException {
        CmtNotasHhppVtMglDaoImpl cmtNotasMglDaoImpl
                = new CmtNotasHhppVtMglDaoImpl();
        return cmtNotasMglDaoImpl.findTipoNotasId(tipoNotasId);
    }

     /**
     * Listado de notas por id de solicitud
     *
     * @author Juan David Hernandez
     * @param idHhpp
     * @return
     * @throws ApplicationException
     */
    public List<CmtNotasHhppVtMgl> 
            findNotasByHhppId(BigDecimal idHhpp)
            throws ApplicationException {
        CmtNotasHhppVtMglDaoImpl cmtNotasMglDaoImpl 
                = new CmtNotasHhppVtMglDaoImpl();
        return cmtNotasMglDaoImpl.findNotasByHhppId(idHhpp);
    }


      public CmtNotasHhppVtMgl create(CmtNotasHhppVtMgl 
              cmtNotasMgl, String usuario, int perfil) throws ApplicationException {
          CmtNotasHhppVtMglDaoImpl cmtNotasHhppMglDaoImpl
                  = new CmtNotasHhppVtMglDaoImpl();
          CmtNotasHhppVtMgl solicitudSE 
                  = cmtNotasHhppMglDaoImpl.createCm(cmtNotasMgl,
                  usuario, perfil);
          return solicitudSE;
    }

    public CmtNotasHhppVtMgl update(CmtNotasHhppVtMgl cmtNotasMgl)
            throws ApplicationException {
        CmtNotasHhppVtMglDaoImpl cmtNotasMglDaoImpl 
                = new CmtNotasHhppVtMglDaoImpl();
        return cmtNotasMglDaoImpl.update(cmtNotasMgl);
    }

    public boolean delete(CmtNotasHhppVtMgl cmtNotasMgl)
            throws ApplicationException {
        CmtNotasHhppVtMglDaoImpl cmtNotasMglDaoImpl
                = new CmtNotasHhppVtMglDaoImpl();
        return cmtNotasMglDaoImpl.delete(cmtNotasMgl);
    }

    public CmtNotasHhppVtMgl findById(CmtNotasHhppVtMgl cmtNotasMgl) 
            throws ApplicationException {
        CmtNotasHhppVtMglDaoImpl cmtNotasMglDaoImpl
                = new CmtNotasHhppVtMglDaoImpl();
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
    public CmtNotasHhppVtMgl findById(BigDecimal id) 
            throws ApplicationException {
        CmtNotasHhppVtMglDaoImpl cmtNotasMglDaoImpl
                = new CmtNotasHhppVtMglDaoImpl();
        return cmtNotasMglDaoImpl.find(id);
    }

    public List<CmtNotasHhppVtMgl> findNotasHhppByIdHhppAndBasicaIdTipoNota(
            BigDecimal idSol, BigDecimal basicaIdTipoNota) 
            throws ApplicationException {
        CmtNotasHhppVtMglDaoImpl cmtNotasMglDaoImpl 
                = new CmtNotasHhppVtMglDaoImpl();
        return cmtNotasMglDaoImpl.findNotasHhppByIdHhppAndBasicaIdTipoNota
                (idSol, basicaIdTipoNota);
    }
}
