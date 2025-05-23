/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DirLogEliminacionMasivaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class DirLogEliminacionMasivaMglDaoImpl extends GenericDaoImpl<DirLogEliminacionMasivaMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(DirLogEliminacionMasivaMglDaoImpl.class);

    public List<DirLogEliminacionMasivaMgl> findAll() throws ApplicationException {
        List<DirLogEliminacionMasivaMgl> resultList;
        Query query = entityManager.createNamedQuery("DirLogEliminacionMasivaMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<DirLogEliminacionMasivaMgl>) query.getResultList();
        return resultList;
    }

    public DirLogEliminacionMasivaMgl findDirLogEliminacionMasivaMglDaoImplByDate(Date fecha) throws ApplicationException {
        try {
            DirLogEliminacionMasivaMgl resultList;
            Query query = entityManager.createQuery("SELECT d FROM DirLogEliminacionMasivaMgl d WHERE  d.lemFechaOperacion = :lemFechaOperacion ");
            query.setParameter("lemFechaOperacion", fecha);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (DirLogEliminacionMasivaMgl) query.getSingleResult();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }


    }

    public List<DirLogEliminacionMasivaMgl> findByBetweenDate(Date fechaInicial, Date fechaFinal) throws ApplicationException {
        try {
            List<DirLogEliminacionMasivaMgl> resultList;
            Query query = entityManager.createQuery("SELECT d FROM DirLogEliminacionMasivaMgl d WHERE   ( d.lemFechaOperacion BETWEEN :lemFechaOperacionI and  :lemFechaOperacionF ) ");
            query.setParameter("lemFechaOperacionI", fechaInicial);
            query.setParameter("lemFechaOperacionF", fechaFinal);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<DirLogEliminacionMasivaMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
}