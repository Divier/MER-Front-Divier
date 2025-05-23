/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtTipoCompaniaMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoCompaniaMgl;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CmtTipoCompaniaMglManager {

    public List<CmtTipoCompaniaMgl> findAll() throws ApplicationException {

        List<CmtTipoCompaniaMgl> resulList;
        CmtTipoCompaniaMglDaoImpl cmtTipoCompaniaMglDaoImpl = new CmtTipoCompaniaMglDaoImpl();

        resulList = cmtTipoCompaniaMglDaoImpl.findAll();

        return resulList;
    }

    public CmtTipoCompaniaMgl create(CmtTipoCompaniaMgl cmtTipoCompaniaMgl) throws ApplicationException {
        CmtTipoCompaniaMglDaoImpl cmtTipoCompaniaMglDaoImpl = new CmtTipoCompaniaMglDaoImpl();
        return cmtTipoCompaniaMglDaoImpl.create(cmtTipoCompaniaMgl);
    }

    public CmtTipoCompaniaMgl update(CmtTipoCompaniaMgl cmtTipoCompaniaMgl) throws ApplicationException {
        CmtTipoCompaniaMglDaoImpl cmtTipoCompaniaMglDaoImpl = new CmtTipoCompaniaMglDaoImpl();
        return cmtTipoCompaniaMglDaoImpl.update(cmtTipoCompaniaMgl);
    }

    public boolean delete(CmtTipoCompaniaMgl cmtTipoCompaniaMgl) throws ApplicationException {
        CmtTipoCompaniaMglDaoImpl cmtTipoCompaniaMglDaoImpl = new CmtTipoCompaniaMglDaoImpl();
        return cmtTipoCompaniaMglDaoImpl.delete(cmtTipoCompaniaMgl);
    }

    public CmtTipoCompaniaMgl findById(CmtTipoCompaniaMgl cmtTipoCompaniaMgl) throws ApplicationException {
        CmtTipoCompaniaMglDaoImpl cmtTipoCompaniaMglDaoImpl = new CmtTipoCompaniaMglDaoImpl();
        return cmtTipoCompaniaMglDaoImpl.find(cmtTipoCompaniaMgl.getTipoCompaniaId());
    }
    
    /**
     * Busca un tipo de compañia por el nombre
     *
     * @author Victor Bocanegra
     * @param nombreTipo
     * @return CmtTipoCompaniaMgl
     * @throws ApplicationException
     */
    public CmtTipoCompaniaMgl findByNombreTipo(String nombreTipo) throws ApplicationException {
        CmtTipoCompaniaMglDaoImpl cmtTipoCompaniaMglDaoImpl = new CmtTipoCompaniaMglDaoImpl();
        return cmtTipoCompaniaMglDaoImpl.findByNombreTipo(nombreTipo);
    }
}
