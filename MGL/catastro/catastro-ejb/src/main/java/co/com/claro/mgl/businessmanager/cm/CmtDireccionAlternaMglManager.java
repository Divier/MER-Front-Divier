/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtDireccionAlternaMglImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionAlternaMgl;
import java.math.BigDecimal;
import java.util.List;
/**
 *
 * @author User
 */
public class CmtDireccionAlternaMglManager {
    
    public List<CmtDireccionAlternaMgl> findAll() throws ApplicationException {
        List<CmtDireccionAlternaMgl> resulList;
        CmtDireccionAlternaMglImpl cmtDireccionAlternaMglImpl = new CmtDireccionAlternaMglImpl();
        resulList = cmtDireccionAlternaMglImpl.findAll();
        return resulList;
    }

    public List<CmtDireccionAlternaMgl> findAlternaId(BigDecimal diaId) throws ApplicationException {
        CmtDireccionAlternaMglImpl cmtDireccionAlternaMglImpl = new CmtDireccionAlternaMglImpl();
        return cmtDireccionAlternaMglImpl.findAlternaId(diaId);
    }

    public CmtDireccionAlternaMgl create(CmtDireccionAlternaMgl cmtDireccionAlternaMgl) throws ApplicationException {
        CmtDireccionAlternaMglImpl cmtDireccionAlternaMglImpl = new CmtDireccionAlternaMglImpl();
        return cmtDireccionAlternaMglImpl.create(cmtDireccionAlternaMgl);
    }

    public CmtDireccionAlternaMgl update(CmtDireccionAlternaMgl cmtDireccionAlternaMgl) throws ApplicationException {
        CmtDireccionAlternaMglImpl cmtDireccionAlternaMglImpl = new CmtDireccionAlternaMglImpl();
        return cmtDireccionAlternaMglImpl.update(cmtDireccionAlternaMgl);
    }

    public boolean delete(CmtDireccionAlternaMgl cmtDireccionAlternaMgl) throws ApplicationException {
        CmtDireccionAlternaMglImpl cmtDireccionAlternaMglImpl = new CmtDireccionAlternaMglImpl();
        return cmtDireccionAlternaMglImpl.delete(cmtDireccionAlternaMgl);
    }

    public CmtDireccionAlternaMgl findById(CmtDireccionAlternaMgl cmtDireccionAlternaMgl) throws ApplicationException {
        CmtDireccionAlternaMglImpl cmtDireccionAlternaMglImpl = new CmtDireccionAlternaMglImpl();
        return cmtDireccionAlternaMglImpl.find(cmtDireccionAlternaMgl.getDiaId());
    }

    public CmtDireccionAlternaMgl findById(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     public CmtDireccionAlternaMgl createCmtDireccionAlternaMgl(CmtDireccionAlternaMgl cmtDireccionAlternaMgl,
             String usuario, int perfil) throws ApplicationException {
        CmtDireccionAlternaMglImpl cmtDireccionAlternaMglImpl = new CmtDireccionAlternaMglImpl();
        return cmtDireccionAlternaMglImpl.createCm(cmtDireccionAlternaMgl, usuario, perfil);
    }
}
