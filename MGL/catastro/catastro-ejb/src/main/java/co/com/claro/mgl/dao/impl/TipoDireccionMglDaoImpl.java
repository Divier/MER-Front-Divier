/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoDireccionMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
public class TipoDireccionMglDaoImpl extends GenericDaoImpl<TipoDireccionMgl> {
    
    public List<TipoDireccionMgl> findAll() throws ApplicationException {
        List<TipoDireccionMgl> resultList;
        Query query = entityManager.createNamedQuery("TipoDireccionMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<TipoDireccionMgl>) query.getResultList();
        return resultList;
    }
    
    public TipoDireccionMgl findId( String tdId ) throws ApplicationException {
        return entityManager.find(TipoDireccionMgl.class, BigDecimal.valueOf( Long.parseLong( tdId)));
    }
    
}
