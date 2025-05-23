/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtTpMultiedificioSolicitudMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtTpMultiedificioSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CmtTpMultiedificioSolicitudMglManager {

    public List<CmtTpMultiedificioSolicitudMgl> findAll() throws ApplicationException {
        List<CmtTpMultiedificioSolicitudMgl> resulList;
        CmtTpMultiedificioSolicitudMglDaoImpl CmtTpMultiedificioSolicitudMglDaoImpl = new CmtTpMultiedificioSolicitudMglDaoImpl();
        resulList = CmtTpMultiedificioSolicitudMglDaoImpl.findAll();
        return resulList;
    }

    public CmtTpMultiedificioSolicitudMgl create(CmtTpMultiedificioSolicitudMgl CmtTpMultiedificioSolicitudMgl) throws ApplicationException {
        CmtTpMultiedificioSolicitudMglDaoImpl CmtTpMultiedificioSolicitudMglDaoImpl = new CmtTpMultiedificioSolicitudMglDaoImpl();
        return CmtTpMultiedificioSolicitudMglDaoImpl.create(CmtTpMultiedificioSolicitudMgl);
    }

    public CmtTpMultiedificioSolicitudMgl update(CmtTpMultiedificioSolicitudMgl CmtTpMultiedificioSolicitudMgl) throws ApplicationException {
        CmtTpMultiedificioSolicitudMglDaoImpl CmtTpMultiedificioSolicitudMglDaoImpl = new CmtTpMultiedificioSolicitudMglDaoImpl();
        return CmtTpMultiedificioSolicitudMglDaoImpl.update(CmtTpMultiedificioSolicitudMgl);
    }

    public boolean delete(CmtTpMultiedificioSolicitudMgl CmtTpMultiedificioSolicitudMgl) throws ApplicationException {
        CmtTpMultiedificioSolicitudMglDaoImpl CmtTpMultiedificioSolicitudMglDaoImpl = new CmtTpMultiedificioSolicitudMglDaoImpl();
        return CmtTpMultiedificioSolicitudMglDaoImpl.delete(CmtTpMultiedificioSolicitudMgl);
    }

    public CmtTpMultiedificioSolicitudMgl findById(CmtTpMultiedificioSolicitudMgl CmtTpMultiedificioSolicitudMgl) throws ApplicationException {
        CmtTpMultiedificioSolicitudMglDaoImpl CmtTpMultiedificioSolicitudMglDaoImpl = new CmtTpMultiedificioSolicitudMglDaoImpl();
        return CmtTpMultiedificioSolicitudMglDaoImpl.find(CmtTpMultiedificioSolicitudMgl.getIdMultiSol());
    }

    public CmtTpMultiedificioSolicitudMgl findById(BigDecimal id) throws ApplicationException {
        CmtTpMultiedificioSolicitudMglDaoImpl CmtTpMultiedificioSolicitudMglDaoImpl = new CmtTpMultiedificioSolicitudMglDaoImpl();
        return CmtTpMultiedificioSolicitudMglDaoImpl.find(id);
    }

    public List<CmtTpMultiedificioSolicitudMgl> findByIdSol(BigDecimal IdSolcm) throws ApplicationException {
        CmtTpMultiedificioSolicitudMglDaoImpl CmtTpMultiedificioSolicitudMglDaoImpl = new CmtTpMultiedificioSolicitudMglDaoImpl();
        return CmtTpMultiedificioSolicitudMglDaoImpl.findByIdSol(IdSolcm);
    }
}
