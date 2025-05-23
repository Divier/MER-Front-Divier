/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AreaDir;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;


/**
 *
 * @author User
 */
public class AreaDirDaoImpl extends GenericDaoImpl<AreaDir>{
   
    public List<AreaDir> findByIdDiv(BigDecimal divId) throws ApplicationException{
        List<AreaDir> resultList; 
        Query query = entityManager.createNamedQuery("AreaDir.findByIdDiv");
        query.setParameter("divId", divId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<AreaDir>)query.getResultList();        
        return resultList;
    } 
    
}
