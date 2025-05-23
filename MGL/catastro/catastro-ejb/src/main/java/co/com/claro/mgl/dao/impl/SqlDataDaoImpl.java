/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.SqlData;
import java.util.List;
import javax.persistence.Query;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.PersistenceException;


/**
 *
 * @author User
 */
public class SqlDataDaoImpl extends GenericDaoImpl<SqlData> {

    private static final Logger LOGGER = LogManager.getLogger(SqlDataDaoImpl.class);

    public List<SqlData> findAll() throws ApplicationException{
        try {
            Query query = entityManager.createNamedQuery("SqlData.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            return (List<SqlData>) query.getResultList();
        } catch (PersistenceException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    public List<SqlData> findSqlDataById(String idSqlData) throws ApplicationException {
        try {
            List<SqlData> resultList;
            Query query = entityManager.createQuery("SELECT s FROM SqlData s  WHERE UPPER(s.id) like :id   ORDER BY s.id ASC  ");
            query.setParameter("id", "%" + idSqlData.toUpperCase() + "%");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<SqlData>) query.getResultList();
            return resultList;
        } catch (PersistenceException e) {
            String msg = "Se produjo un error al momento de consultar la data por ID " + idSqlData + ": " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de consultar la data por ID " + idSqlData + ": " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
}