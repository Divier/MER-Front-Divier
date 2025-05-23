/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DistritoMgl;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
public class DistritoMglDaoImpl extends GenericDaoImpl<DistritoMgl>{
    
    public List<DistritoMgl> findAll() throws ApplicationException{
        List<DistritoMgl> resultList; 
        Query query = entityManager.createNamedQuery("DistritoMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<DistritoMgl>)query.getResultList();        
        return resultList;
    } 
    
}
