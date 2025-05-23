/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

/**
 *
 * @author Admin
 */
import co.com.claro.mgl.businessmanager.cm.CmtTpMultiedificioSolicitudMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtTpMultiedificioSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class CmtTpMultiedificioSolicitudMglFacade implements CmtTpMultiedificioSolicitudMglFacadeLocal {

    @Override
    public List<CmtTpMultiedificioSolicitudMgl> findAll() throws ApplicationException {
        CmtTpMultiedificioSolicitudMglManager cmtTpMultiedificioSolicitudMglManager = new CmtTpMultiedificioSolicitudMglManager();
        return cmtTpMultiedificioSolicitudMglManager.findAll();
    }

    @Override
    public CmtTpMultiedificioSolicitudMgl create(CmtTpMultiedificioSolicitudMgl t) throws ApplicationException {
        CmtTpMultiedificioSolicitudMglManager cmtTpMultiedificioSolicitudMglManager = new CmtTpMultiedificioSolicitudMglManager();
        return cmtTpMultiedificioSolicitudMglManager.create(t);
    }

    @Override
    public CmtTpMultiedificioSolicitudMgl update(CmtTpMultiedificioSolicitudMgl t) throws ApplicationException {
        CmtTpMultiedificioSolicitudMglManager cmtTpMultiedificioSolicitudMglManager = new CmtTpMultiedificioSolicitudMglManager();
        return cmtTpMultiedificioSolicitudMglManager.update(t);
    }

    @Override
    public boolean delete(CmtTpMultiedificioSolicitudMgl t) throws ApplicationException {
        CmtTpMultiedificioSolicitudMglManager cmtTpMultiedificioSolicitudMglManager = new CmtTpMultiedificioSolicitudMglManager();
        return cmtTpMultiedificioSolicitudMglManager.delete(t);
    }

    @Override
    public CmtTpMultiedificioSolicitudMgl findById(CmtTpMultiedificioSolicitudMgl sqlData) throws ApplicationException {
        CmtTpMultiedificioSolicitudMglManager cmtTpMultiedificioSolicitudMglManager = new CmtTpMultiedificioSolicitudMglManager();
        return cmtTpMultiedificioSolicitudMglManager.findById(sqlData.getIdMultiSol());
    }

    @Override
    public List<CmtTpMultiedificioSolicitudMgl> findByIdSol(BigDecimal IdSolcm) throws ApplicationException {
        CmtTpMultiedificioSolicitudMglManager cmtTpMultiedificioSolicitudMglManager = new CmtTpMultiedificioSolicitudMglManager();
        return cmtTpMultiedificioSolicitudMglManager.findByIdSol(IdSolcm);
    }
}
