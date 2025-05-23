/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.rr;

import co.com.claro.mgl.dao.IGenericDao;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author User
 * @param <T>
 */
public class GenericDaoRRImpl<T> implements IGenericDao<T> {

    protected EntityManager entityManager;
    protected Class<T> entityClass;
    private static final Logger LOGGER = LogManager.getLogger(GenericDaoRRImpl.class);

    public GenericDaoRRImpl() {
        try {
            if (entityManager == null) {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("AS400PU");
                entityManager = emf.createEntityManager();
            }
            ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
            this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        } catch (Exception e) {
            LOGGER.error("Error en GenericDao: ", e);
        }
    }
    
    

    @Override
    public T create(T t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T find(BigDecimal id) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public T find(String id) throws ApplicationException {
        try {
            return this.entityManager.find(entityClass, id);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @Override
    public T update(T t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(T t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T createCm(T t, String usuario, int Perfil) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T updateCm(T t, String usuario, int Perfil) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteCm(T t, String usuario, int Perfil) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
