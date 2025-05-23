/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudDetalleMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtNotasSolicitudDetalleMglDaoImpl extends GenericDaoImpl<CmtNotasSolicitudDetalleMgl> {

    public List<CmtNotasSolicitudDetalleMgl> findAll() throws ApplicationException {
        List<CmtNotasSolicitudDetalleMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtNotasSolicitudDetalleMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasSolicitudDetalleMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasSolicitudDetalleMgl> findNotasByNotasId(BigDecimal notasId) throws ApplicationException {
        List<CmtNotasSolicitudDetalleMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM CmtNotasSolicitudDetalleMgl c WHERE c.notasId = :notasId ORDER BY c.notasDetalleId ASC ");
        query.setParameter("notasId", notasId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasSolicitudDetalleMgl>) query.getResultList();
        return resultList;
    }
}