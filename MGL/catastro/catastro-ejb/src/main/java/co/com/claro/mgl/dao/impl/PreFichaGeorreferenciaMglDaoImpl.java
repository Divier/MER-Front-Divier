/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.jpa.entities.PreFichaGeorreferenciaMgl;
import java.math.BigDecimal;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class PreFichaGeorreferenciaMglDaoImpl extends GenericDaoImpl<PreFichaGeorreferenciaMgl>{
    
    public PreFichaGeorreferenciaMgl findPreFichaGeorreferenciaByIdPrefichaXls(BigDecimal id){
      
            String consulta = "SELECT a FROM PreFichaGeorreferenciaMgl a WHERE a.preFichaXlsMgl.id = :id";
            Query query = entityManager.createQuery(consulta);  
            query.setParameter("id",id);            
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
           
           return (PreFichaGeorreferenciaMgl) query.getSingleResult();
    }
    
}
