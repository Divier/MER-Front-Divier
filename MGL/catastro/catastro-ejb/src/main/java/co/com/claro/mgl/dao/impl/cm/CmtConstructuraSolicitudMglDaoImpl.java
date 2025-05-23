/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtConstructuraSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtConstructuraSolicitudMglDaoImpl extends GenericDaoImpl<CmtConstructuraSolicitudMgl> {

    public List<CmtConstructuraSolicitudMgl> findAll() throws ApplicationException {
        List<CmtConstructuraSolicitudMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtConstructuraSolicitudMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtConstructuraSolicitudMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtConstructuraSolicitudMgl> findByIdSol(BigDecimal IdSolcm) throws ApplicationException {
        List<CmtConstructuraSolicitudMgl> resultList;
        Query query =
                entityManager.createQuery("SELECT c FROM CmtConstructuraSolicitudMgl c "
                + "WHERE c.idSolicitudCm = :IdSolcm ");
        query.setParameter("IdSolcm", IdSolcm);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtConstructuraSolicitudMgl>) query.getResultList();
        return resultList;
    }
}