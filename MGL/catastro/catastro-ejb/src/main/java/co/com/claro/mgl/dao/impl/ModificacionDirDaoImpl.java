/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ModificacionDir;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class ModificacionDirDaoImpl extends GenericDaoImpl<ModificacionDir> {
    
    public List<ModificacionDir> findByIdSolicitud(BigDecimal solicitud) throws ApplicationException{
        List<ModificacionDir> resultList; 
        Query query = entityManager.createNamedQuery("ModificacionDir.findByIdSolicitud");
        query.setParameter("solicitud", solicitud);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<ModificacionDir>)query.getResultList();        
        return resultList;
    }
    
     public List<ModificacionDir> findHhppConflicto(BigDecimal solicitud) throws ApplicationException{
        List<ModificacionDir> resultList; 
        Query query = entityManager.createNamedQuery("ModificacionDir.findHhppConflicto");
        query.setParameter("solicitud", solicitud);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<ModificacionDir>)query.getResultList();        
        return resultList;
    }
    
}
