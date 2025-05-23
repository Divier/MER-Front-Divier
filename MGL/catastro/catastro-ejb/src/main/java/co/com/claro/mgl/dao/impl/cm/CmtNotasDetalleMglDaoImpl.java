/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasDetalleMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtNotasDetalleMglDaoImpl extends GenericDaoImpl<CmtNotasDetalleMgl> {

    public List<CmtNotasDetalleMgl> findAll() throws ApplicationException {
        List<CmtNotasDetalleMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtNotasDetalleMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasDetalleMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasDetalleMgl> findNotasByNotasId(BigDecimal notasId) throws ApplicationException {
        List<CmtNotasDetalleMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM CmtNotasDetalleMgl c WHERE c.notasId = :notasId ORDER BY c.notasDetalleId ASC ");
        query.setParameter("notasId", notasId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasDetalleMgl>) query.getResultList();
        return resultList;
    }
}