/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionAlternaMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtDireccionAlternaMglImpl extends GenericDaoImpl<CmtDireccionAlternaMgl> {
    
    public List<CmtDireccionAlternaMgl> findAll() throws ApplicationException {
        List<CmtDireccionAlternaMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtDireccionAlternaMgl.findAll");
        resultList = (List<CmtDireccionAlternaMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtDireccionAlternaMgl> findAlternaId(BigDecimal diaId) throws ApplicationException {
        List<CmtDireccionAlternaMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM CmtDireccionAlternaMgl c WHERE c.diaId = :diaId ");
        query.setParameter("diaId", diaId);
        resultList = (List<CmtDireccionAlternaMgl>) query.getResultList();
        return resultList;
    }
}
