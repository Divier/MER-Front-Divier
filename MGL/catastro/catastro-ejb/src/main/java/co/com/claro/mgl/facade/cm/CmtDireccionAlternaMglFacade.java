/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtDireccionAlternaMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionAlternaMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
/**
 *
 * @author User
 */
@Stateless
public class CmtDireccionAlternaMglFacade implements CmtDireccionAlternaMglFacadeLocal{
    
    @Override
    public List<CmtDireccionAlternaMgl> findAll() throws ApplicationException {
        CmtDireccionAlternaMglManager cmtDireccionAlternaMglManager = new CmtDireccionAlternaMglManager();
        return cmtDireccionAlternaMglManager.findAll();
    }

    @Override
    public List<CmtDireccionAlternaMgl> findTipoBasicaId(BigDecimal diaId) throws ApplicationException {
        CmtDireccionAlternaMglManager cmtDireccionAlternaMglManager = new CmtDireccionAlternaMglManager();
        return cmtDireccionAlternaMglManager.findAlternaId(diaId);
    }

    @Override
    public CmtDireccionAlternaMgl create(CmtDireccionAlternaMgl cmtDireccionAlternaMgl) throws ApplicationException {
        CmtDireccionAlternaMglManager cmtDireccionAlternaMglManager = new CmtDireccionAlternaMglManager();
        return cmtDireccionAlternaMglManager.create(cmtDireccionAlternaMgl);
    }

    @Override
    public CmtDireccionAlternaMgl update(CmtDireccionAlternaMgl cmtDireccionAlternaMgl) throws ApplicationException {
        CmtDireccionAlternaMglManager cmtDireccionAlternaMglManager = new CmtDireccionAlternaMglManager();
        return cmtDireccionAlternaMglManager.update(cmtDireccionAlternaMgl);
    }

    @Override
    public boolean delete(CmtDireccionAlternaMgl cmtDireccionAlternaMgl) throws ApplicationException {
               CmtDireccionAlternaMglManager cmtDireccionAlternaMglManager = new CmtDireccionAlternaMglManager();
        return cmtDireccionAlternaMglManager.delete(cmtDireccionAlternaMgl);
    }

    @Override
    public CmtDireccionAlternaMgl findById(BigDecimal id) throws ApplicationException {
        CmtDireccionAlternaMglManager cmtDireccionAlternaMglManager = new CmtDireccionAlternaMglManager();
        return cmtDireccionAlternaMglManager.findById(id);
    }

    @Override
    public CmtDireccionAlternaMgl findById(CmtDireccionAlternaMgl sqlData) throws ApplicationException {
        CmtDireccionAlternaMglManager cmtDireccionAlternaMglManager = new CmtDireccionAlternaMglManager();
        return cmtDireccionAlternaMglManager.findById(sqlData);
    }
}
