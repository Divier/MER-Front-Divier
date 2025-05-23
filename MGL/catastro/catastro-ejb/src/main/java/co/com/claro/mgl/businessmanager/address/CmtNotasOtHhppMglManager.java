/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.CmtNotasOtHhppMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtNotasOtHhppMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class CmtNotasOtHhppMglManager {

    public List<CmtNotasOtHhppMgl> findAll() throws ApplicationException {
        List<CmtNotasOtHhppMgl> resulList;
        CmtNotasOtHhppMglDaoImpl CmtNotasOtHhppMglDaoImpl
                = new CmtNotasOtHhppMglDaoImpl();
        resulList = CmtNotasOtHhppMglDaoImpl.findAll();
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
    public List<CmtNotasOtHhppMgl> findTipoNotasId(BigDecimal tipoNotasId) 
            throws ApplicationException {
        CmtNotasOtHhppMglDaoImpl cmtNotasMglDaoImpl
                = new CmtNotasOtHhppMglDaoImpl();
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
    public List<CmtNotasOtHhppMgl> 
            findNotasByOtHhppId(BigDecimal idHhpp)
            throws ApplicationException {
        CmtNotasOtHhppMglDaoImpl cmtNotasMglDaoImpl 
                = new CmtNotasOtHhppMglDaoImpl();
        return cmtNotasMglDaoImpl.findNotasByOtHhppId(idHhpp);
    }


      public CmtNotasOtHhppMgl create(CmtNotasOtHhppMgl 
              cmtNotasMgl, String usuario, int perfil) throws ApplicationException {
          CmtNotasOtHhppMglDaoImpl cmtNotasHhppMglDaoImpl
                  = new CmtNotasOtHhppMglDaoImpl();
          CmtNotasOtHhppMgl solicitudSE 
                  = cmtNotasHhppMglDaoImpl.createCm(cmtNotasMgl,
                  usuario, perfil);
          return solicitudSE;
    }

    public CmtNotasOtHhppMgl update(CmtNotasOtHhppMgl cmtNotasMgl)
            throws ApplicationException {
        CmtNotasOtHhppMglDaoImpl cmtNotasMglDaoImpl 
                = new CmtNotasOtHhppMglDaoImpl();
        return cmtNotasMglDaoImpl.update(cmtNotasMgl);
    }

    public boolean delete(CmtNotasOtHhppMgl cmtNotasMgl)
            throws ApplicationException {
        CmtNotasOtHhppMglDaoImpl cmtNotasMglDaoImpl
                = new CmtNotasOtHhppMglDaoImpl();
        return cmtNotasMglDaoImpl.delete(cmtNotasMgl);
    }

    public CmtNotasOtHhppMgl findById(CmtNotasOtHhppMgl cmtNotasMgl) 
            throws ApplicationException {
        CmtNotasOtHhppMglDaoImpl cmtNotasMglDaoImpl
                = new CmtNotasOtHhppMglDaoImpl();
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
    public CmtNotasOtHhppMgl findById(BigDecimal id) 
            throws ApplicationException {
        CmtNotasOtHhppMglDaoImpl cmtNotasMglDaoImpl
                = new CmtNotasOtHhppMglDaoImpl();
        return cmtNotasMglDaoImpl.find(id);
    }

    public List<CmtNotasOtHhppMgl> findNotasHhppByIdHhppAndBasicaIdTipoNota(
            BigDecimal idSol, BigDecimal basicaIdTipoNota) 
            throws ApplicationException {
        CmtNotasOtHhppMglDaoImpl cmtNotasMglDaoImpl 
                = new CmtNotasOtHhppMglDaoImpl();
        return cmtNotasMglDaoImpl.findNotasHhppByIdHhppAndBasicaIdTipoNota
                (idSol, basicaIdTipoNota);
    }
}
