/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudDetalleVtMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Juan David Hernandez
 */
public class CmtNotasSolicitudDetalleVtMglDaoImpl extends GenericDaoImpl<CmtNotasSolicitudDetalleVtMgl> {

    public List<CmtNotasSolicitudDetalleVtMgl> findAll() throws ApplicationException {
        List<CmtNotasSolicitudDetalleVtMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtNotasSolicitudDetalleMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasSolicitudDetalleVtMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasSolicitudDetalleVtMgl> findNotasByNotasId(BigDecimal notasId) throws ApplicationException {
        List<CmtNotasSolicitudDetalleVtMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM CmtNotasSolicitudDetalleVtMgl c WHERE c.notaSolicitud.notasId = :notasId ORDER BY c.notasDetalleId ASC ");
        query.setParameter("notasId", notasId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasSolicitudDetalleVtMgl>) query.getResultList();
        return resultList;
    }
}