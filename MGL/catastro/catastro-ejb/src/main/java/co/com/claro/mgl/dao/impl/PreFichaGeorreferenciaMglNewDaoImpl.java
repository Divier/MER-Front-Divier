/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.jpa.entities.PreFichaGeorreferenciaMglNew;
import java.math.BigDecimal;
import javax.persistence.Query;

/**
 * Impl Dao para operaciones en TEC_PREFICHA_GEOREFERENCIA_NEW
 * 
 * @author Miguel Barrios Hitss
 * @version 1.1 Rev Miguel Barrios Hitss
 */
public class PreFichaGeorreferenciaMglNewDaoImpl extends GenericDaoImpl<PreFichaGeorreferenciaMglNew>{
    
    public PreFichaGeorreferenciaMglNew findPreFichaGeorreferenciaByIdPrefichaXls(BigDecimal id){
      
            String consulta = "SELECT a FROM PreFichaGeorreferenciaMglNew a WHERE a.preFichaXlsMgl.id = :id";
            Query query = entityManager.createQuery(consulta);  
            query.setParameter("id",id);            
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
           
           return (PreFichaGeorreferenciaMglNew) query.getSingleResult();
    }
    
}