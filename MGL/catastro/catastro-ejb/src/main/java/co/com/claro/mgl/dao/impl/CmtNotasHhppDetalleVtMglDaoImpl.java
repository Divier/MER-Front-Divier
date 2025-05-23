/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtNotasHhppDetalleVtMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Juan David Hernandez
 */
public class CmtNotasHhppDetalleVtMglDaoImpl extends GenericDaoImpl<CmtNotasHhppDetalleVtMgl> {

    public List<CmtNotasHhppDetalleVtMgl> findAll() throws ApplicationException {
        List<CmtNotasHhppDetalleVtMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtNotasHhppDetalleVtMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasHhppDetalleVtMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasHhppDetalleVtMgl> findNotasByNotasId(BigDecimal notasId) throws ApplicationException {
        List<CmtNotasHhppDetalleVtMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM CmtNotasHhppDetalleVtMgl c WHERE c.notaSolicitud.notasId = :notasId ORDER BY c.notasDetalleId ASC ");
        query.setParameter("notasId", notasId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasHhppDetalleVtMgl>) query.getResultList();
        return resultList;
    }
}