/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtTpMultiedificioSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtTpMultiedificioSolicitudMglDaoImpl extends GenericDaoImpl<CmtTpMultiedificioSolicitudMgl> {

    public List<CmtTpMultiedificioSolicitudMgl> findAll() throws ApplicationException {
        List<CmtTpMultiedificioSolicitudMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtTpMultiedificioSolicitudMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtTpMultiedificioSolicitudMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtTpMultiedificioSolicitudMgl> findByIdSol(BigDecimal IdSolcm) throws ApplicationException {
        List<CmtTpMultiedificioSolicitudMgl> resultList;
        Query query =
                entityManager.createQuery("SELECT c FROM CmtTpMultiedificioSolicitudMgl c "
                + "WHERE c.idSolicitudCm = :IdSolcm ORDER BY c.numSubedificio ASC ");
        query.setParameter("IdSolcm", IdSolcm);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtTpMultiedificioSolicitudMgl>) query.getResultList();
        return resultList;
    }
}