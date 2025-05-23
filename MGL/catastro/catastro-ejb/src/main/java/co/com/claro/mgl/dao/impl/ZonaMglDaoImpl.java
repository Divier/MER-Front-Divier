/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ZonaMgl;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
public class ZonaMglDaoImpl  extends GenericDaoImpl<ZonaMgl>{
    
    public List<ZonaMgl> findAll() throws ApplicationException{
        List<ZonaMgl> resultList; 
        Query query = entityManager.createNamedQuery("ZonaMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<ZonaMgl>)query.getResultList();        
        return resultList;
    }    
}
