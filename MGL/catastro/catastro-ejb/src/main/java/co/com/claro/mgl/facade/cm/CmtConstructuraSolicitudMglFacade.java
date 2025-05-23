/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

/**
 *
 * @author Admin
 */
import co.com.claro.mgl.businessmanager.cm.CmtConstructuraSolicitudMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtConstructuraSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class CmtConstructuraSolicitudMglFacade implements CmtConstructuraSolicitudMglFacadeLocal {

    @Override
    public List<CmtConstructuraSolicitudMgl> findAll() throws ApplicationException {
        CmtConstructuraSolicitudMglManager cmtConstructuraSolicitudMglManager = new CmtConstructuraSolicitudMglManager();
        return cmtConstructuraSolicitudMglManager.findAll();
    }

    @Override
    public CmtConstructuraSolicitudMgl create(CmtConstructuraSolicitudMgl t) throws ApplicationException {
        CmtConstructuraSolicitudMglManager cmtConstructuraSolicitudMglManager = new CmtConstructuraSolicitudMglManager();
        return cmtConstructuraSolicitudMglManager.create(t);
    }

    @Override
    public CmtConstructuraSolicitudMgl update(CmtConstructuraSolicitudMgl t) throws ApplicationException {
        CmtConstructuraSolicitudMglManager cmtConstructuraSolicitudMglManager = new CmtConstructuraSolicitudMglManager();
        return cmtConstructuraSolicitudMglManager.update(t);
    }

    @Override
    public boolean delete(CmtConstructuraSolicitudMgl t) throws ApplicationException {
        CmtConstructuraSolicitudMglManager cmtConstructuraSolicitudMglManager = new CmtConstructuraSolicitudMglManager();
        return cmtConstructuraSolicitudMglManager.delete(t);
    }

    @Override
    public CmtConstructuraSolicitudMgl findById(CmtConstructuraSolicitudMgl sqlData) throws ApplicationException {
        CmtConstructuraSolicitudMglManager cmtConstructuraSolicitudMglManager = new CmtConstructuraSolicitudMglManager();
        return cmtConstructuraSolicitudMglManager.findById(sqlData);
    }

    @Override
    public List<CmtConstructuraSolicitudMgl> findByIdSol(BigDecimal IdSolcm) throws ApplicationException {
        CmtConstructuraSolicitudMglManager cmtConstructuraSolicitudMglManager = new CmtConstructuraSolicitudMglManager();
        return cmtConstructuraSolicitudMglManager.findByIdSol(IdSolcm);
    }
}