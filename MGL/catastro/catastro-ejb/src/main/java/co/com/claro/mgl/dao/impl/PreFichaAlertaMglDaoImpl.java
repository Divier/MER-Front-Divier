/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaAlertaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author User
 */
public class PreFichaAlertaMglDaoImpl extends GenericDaoImpl<PreFichaAlertaMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(PreFichaAlertaMglDaoImpl.class);

    public List<PreFichaAlertaMgl> getListPrefichaByFase() throws ApplicationException {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            
            Date dateToCompare = new Date();
            dateToCompare = calendar.getTime();
            
            List<PreFichaAlertaMgl> resultList;
            Query query = entityManager.createNamedQuery("PreFichaAlertaMgl.findToProcess");
            query.setParameter("dateToCompare", dateToCompare, TemporalType.TIMESTAMP);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<PreFichaAlertaMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }

    }
}
