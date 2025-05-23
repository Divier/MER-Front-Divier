/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtSubedificiosSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtSubedificiosSolicitudMglDaoImpl extends GenericDaoImpl<CmtSubedificiosSolicitudMgl> {

    public List<CmtSubedificiosSolicitudMgl> findAll() throws ApplicationException {
        List<CmtSubedificiosSolicitudMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtSubedificiosSolicitudMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtSubedificiosSolicitudMgl>) query.getResultList();
        return resultList;
    }
    
    
    public List<CmtSubedificiosSolicitudMgl> findByIdMultiSol(BigDecimal idMultiSol) throws ApplicationException {
        List<CmtSubedificiosSolicitudMgl> resultList;
        Query query =
                entityManager.createQuery("SELECT c FROM CmtSubedificiosSolicitudMgl c "
                + "WHERE c.idMultiSol = :idMultiSol  ");
        query.setParameter("idMultiSol", idMultiSol);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtSubedificiosSolicitudMgl>) query.getResultList();
        return resultList;
    }
    
}