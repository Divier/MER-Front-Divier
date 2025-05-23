/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.jpa.entities.EstadoHhpp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Parzifal de León
 */
public class EstadoHhppDaoImpl extends GenericDaoImpl<EstadoHhpp>{
    
    private static final Logger LOGGER = LogManager.getLogger(EstadoHhppDaoImpl.class);
    
    public List<EstadoHhpp> findAll(){
        
        List<EstadoHhpp> resultList;
        String query = "select * from estado_hhpp";
        Query q = entityManager.createNativeQuery(query,EstadoHhpp.class);
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<EstadoHhpp>)q.getResultList(); 
        LOGGER.error("resultList: "+resultList);
        return resultList; 
    }
}
