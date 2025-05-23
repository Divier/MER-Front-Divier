/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaHHPPDetalleMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class PreFichaHHPPDetalleMglDaoImpl extends GenericDaoImpl<PreFichaHHPPDetalleMgl>{
    
    
    private static final Logger LOGGER = LogManager.getLogger(PreFichaHHPPDetalleMglDaoImpl.class);
    
    
    public List<PreFichaHHPPDetalleMgl> obtenerDetallesHHPP(PreFichaXlsMgl prefichaXls) throws ApplicationException{
        try {
            Query query = entityManager.createQuery("SELECT p FROM PreFichaHHPPDetalleMgl p WHERE p.preFichaXlsMgl.id = :idPrefichaXls");
            query.setParameter("idPrefichaXls", prefichaXls.getId());
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("No se pudo consultar deatlle preficha xls: "+e.getMessage());
            throw new ApplicationException("No se pudo consultar deatlle preficha xls: "+e.getMessage());
        }
    }
    
}
