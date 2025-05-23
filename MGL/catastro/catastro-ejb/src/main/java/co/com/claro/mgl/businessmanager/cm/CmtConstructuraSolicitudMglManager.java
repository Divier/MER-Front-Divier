/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtConstructuraSolicitudMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtConstructuraSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CmtConstructuraSolicitudMglManager {

    public List<CmtConstructuraSolicitudMgl> findAll() throws ApplicationException {
        List<CmtConstructuraSolicitudMgl> resulList;
        CmtConstructuraSolicitudMglDaoImpl CmtConstructuraSolicitudMglDaoImpl = new CmtConstructuraSolicitudMglDaoImpl();
        resulList = CmtConstructuraSolicitudMglDaoImpl.findAll();
        return resulList;
    }

    public CmtConstructuraSolicitudMgl create(CmtConstructuraSolicitudMgl CmtConstructuraSolicitudMgl) throws ApplicationException {
        CmtConstructuraSolicitudMglDaoImpl CmtConstructuraSolicitudMglDaoImpl = new CmtConstructuraSolicitudMglDaoImpl();
        return CmtConstructuraSolicitudMglDaoImpl.create(CmtConstructuraSolicitudMgl);
    }

    public CmtConstructuraSolicitudMgl update(CmtConstructuraSolicitudMgl CmtConstructuraSolicitudMgl) throws ApplicationException {
        CmtConstructuraSolicitudMglDaoImpl CmtConstructuraSolicitudMglDaoImpl = new CmtConstructuraSolicitudMglDaoImpl();
        return CmtConstructuraSolicitudMglDaoImpl.update(CmtConstructuraSolicitudMgl);
    }

    public boolean delete(CmtConstructuraSolicitudMgl CmtConstructuraSolicitudMgl) throws ApplicationException {
        CmtConstructuraSolicitudMglDaoImpl CmtConstructuraSolicitudMglDaoImpl = new CmtConstructuraSolicitudMglDaoImpl();
        return CmtConstructuraSolicitudMglDaoImpl.delete(CmtConstructuraSolicitudMgl);
    }

    public CmtConstructuraSolicitudMgl findById(CmtConstructuraSolicitudMgl CmtConstructuraSolicitudMgl) throws ApplicationException {
        CmtConstructuraSolicitudMglDaoImpl CmtConstructuraSolicitudMglDaoImpl = new CmtConstructuraSolicitudMglDaoImpl();
        return CmtConstructuraSolicitudMglDaoImpl.find(CmtConstructuraSolicitudMgl.getIdConstructoraSol());
    }

    public CmtConstructuraSolicitudMgl findById(BigDecimal id) throws ApplicationException {
        CmtConstructuraSolicitudMglDaoImpl CmtConstructuraSolicitudMglDaoImpl = new CmtConstructuraSolicitudMglDaoImpl();
        return CmtConstructuraSolicitudMglDaoImpl.find(id);
    }

    public List<CmtConstructuraSolicitudMgl> findByIdSol(BigDecimal IdSolcm) throws ApplicationException {
        List<CmtConstructuraSolicitudMgl> resulList;
        CmtConstructuraSolicitudMglDaoImpl CmtConstructuraSolicitudMglDaoImpl = new CmtConstructuraSolicitudMglDaoImpl();
        resulList = CmtConstructuraSolicitudMglDaoImpl.findByIdSol(IdSolcm);
        return resulList;
    }
}
