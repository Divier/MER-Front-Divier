/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.EstadoHhppMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class EstadoHhppMglDaoImpl extends GenericDaoImpl<EstadoHhppMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(EstadoHhppMglDaoImpl.class);

    public List<EstadoHhppMgl> findAll() throws ApplicationException {
        List<EstadoHhppMgl> resultList;
        Query query = entityManager.createNamedQuery("EstadoHhppMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<EstadoHhppMgl>) query.getResultList();
        return resultList;
    }
    
    public EstadoHhppMgl findByIdEstHhpp(String ehhID) throws ApplicationException {
       
        EstadoHhppMgl result;
        try {
            Query query = entityManager.createNamedQuery("EstadoHhppMgl.findEhhID");
            query.setParameter("ehhID", ehhID);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            result = (EstadoHhppMgl) query.getSingleResult();
        } catch (Exception e) {
             String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            result = null;
        }
        return result;
    }
    
    public EstadoHhppMgl findByNameEstHhpp(String nombre) throws ApplicationException {

        List<EstadoHhppMgl> lista;
        EstadoHhppMgl result = null;
        try {
            String queryStr ="SELECT e FROM EstadoHhppMgl e WHERE 1=1"
                    + " AND  UPPER(e.ehhNombre) LIKE :ehhNombre ";
            
            Query query = entityManager.createQuery(queryStr);
            query.setParameter("ehhNombre", "%" + nombre.trim().toUpperCase() + "%");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            lista = query.getResultList();
            if (lista != null && !lista.isEmpty()) {
                result = lista.get(0);
            }
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            result = null;
        }
        return result;
    }
}
