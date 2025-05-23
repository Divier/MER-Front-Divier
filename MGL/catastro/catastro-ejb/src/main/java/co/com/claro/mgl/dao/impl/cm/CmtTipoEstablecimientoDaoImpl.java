/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstablecimientoCmMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 * Parzifal de León
 */
public class CmtTipoEstablecimientoDaoImpl
        extends GenericDaoImpl<CmtEstablecimientoCmMgl> {

    
        public List<CmtEstablecimientoCmMgl> findAll()  {
        List<CmtEstablecimientoCmMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtEstablecimientoCmMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtEstablecimientoCmMgl>) query.getResultList();
        return resultList;
    }
    
    
    public List<CmtEstablecimientoCmMgl> findByCompania(BigDecimal id) {
        List<CmtEstablecimientoCmMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "CmtTipoEstablecimiento c WHERE c.compId = :compId");
        query.setParameter("compId", id);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0 
        resultList = (List<CmtEstablecimientoCmMgl>) query.getResultList();
        return resultList;
    }
}
