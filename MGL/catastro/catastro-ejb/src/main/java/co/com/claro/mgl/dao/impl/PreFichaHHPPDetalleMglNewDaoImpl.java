/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaHHPPDetalleMglNew;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMglNew;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 * Impl Dao para operaciones en TEC_PREF_TEC_HAB_NEW
 * 
 * @author Miguel Barrios Hitss
 * @version 1.1 Rev Miguel Barrios Hitss
 */

public class PreFichaHHPPDetalleMglNewDaoImpl extends GenericDaoImpl<PreFichaHHPPDetalleMglNew>{
    
    
    private static final Logger LOGGER = LogManager.getLogger(PreFichaHHPPDetalleMglDaoImpl.class);
    
    
    public List<PreFichaHHPPDetalleMglNew> obtenerDetallesHHPP(PreFichaXlsMglNew prefichaXls) throws ApplicationException{
        try {
            Query query = entityManager.createQuery("SELECT p FROM PreFichaHHPPDetalleMglNew p WHERE p.preFichaXlsMgl.id = :idPrefichaXls");
            query.setParameter("idPrefichaXls", prefichaXls.getId());
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("No se pudo consultar deatlle preficha xls: "+e.getMessage());
            throw new ApplicationException("No se pudo consultar deatlle preficha xls: "+e.getMessage());
        }
    }
    
}
