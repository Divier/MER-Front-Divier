/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.jpa.entities.CmtNotasOtHhppDetalleMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Juan David Hernandez
 */
public class CmtNotasOtHhppDetalleMglDaoImpl extends GenericDaoImpl<CmtNotasOtHhppDetalleMgl> {

    public List<CmtNotasOtHhppDetalleMgl> findAll() {
        List<CmtNotasOtHhppDetalleMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtNotasOtHhppDetalleMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasOtHhppDetalleMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasOtHhppDetalleMgl> findNotasByNotasId(BigDecimal notasId) {
        List<CmtNotasOtHhppDetalleMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM CmtNotasOtHhppDetalleMgl c WHERE c.notaSolicitud.notasId = :notasId ORDER BY c.notasDetalleId ASC ");
        query.setParameter("notasId", notasId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasOtHhppDetalleMgl>) query.getResultList();
        return resultList;
    }
}