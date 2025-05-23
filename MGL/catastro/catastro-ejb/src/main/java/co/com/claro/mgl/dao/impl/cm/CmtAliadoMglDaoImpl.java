/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;


import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtAliados;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author bocanegravm
 */
public class CmtAliadoMglDaoImpl {

    private static final Logger LOGGER = LogManager.getLogger(CmtAliadoMglDaoImpl.class);
    
    /**
     * Busca un Aliado en el repositorio.
     *
     * @author Victor Bocanegra
     * @param idAliado
     * @return un la informacion completa de un Aliado
     * @throws ApplicationException
     */
    public CmtAliados findByIdAliado(BigDecimal idAliado) throws ApplicationException {
        
         CmtAliados resulList = null;
        try {
           
            EntityManager entityManager = null;
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionseguridad-ejbPU");
            entityManager = emf.createEntityManager();
            entityManager.setProperty("eclipselink.flush-clear.cache", "drop");
            entityManager.setProperty("javax.persistence.cache.storeMode", "REFRESH");
            entityManager.getEntityManagerFactory().getCache().evictAll();
          
            Query query = entityManager.createNamedQuery("CmtAliados.findByIdAliado");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (idAliado != null) {
                query.setParameter("idAliado", idAliado);
            }
            resulList = (CmtAliados) query.getSingleResult();
            return resulList;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }
}
