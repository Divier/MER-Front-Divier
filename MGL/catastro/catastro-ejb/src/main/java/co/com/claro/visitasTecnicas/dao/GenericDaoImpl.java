package co.com.claro.visitasTecnicas.dao;

import co.com.claro.mgl.dao.IGenericDao;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 * Objetivo:
 *
 * Descripción:
 * 
 * @author becerraarmr
 *
 * @version 1.0 revisión 1.0
 * 
 * @param <T>
 */
public class GenericDaoImpl<T> implements IGenericDao<T> {

  protected EntityManager entityManager;
  protected Class<T> entityClass;
  private static final Logger LOGGER = LogManager.getLogger(GenericDaoImpl.class);

  public GenericDaoImpl() {
    try {
      if (entityManager == null) {
        EntityManagerFactory emf = 
                Persistence.createEntityManagerFactory("catastro-ejbPU");
        entityManager = emf.createEntityManager();
        entityManager.
                setProperty("javax.persistence.cache.storeMode", "REFRESH");
        entityManager.getEntityManagerFactory().getCache().evictAll();
      }
      ParameterizedType genericSuperclass = 
              (ParameterizedType) getClass().getGenericSuperclass();
      this.entityClass = 
              (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    } catch (Exception e) {
      String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
      LOGGER.error(msg);
      throw new Error(e.getMessage());
    }
  }

  @Override
  public T create(T t) throws ApplicationException {
    try {
      entityManager.getTransaction().begin();
        this.entityManager.persist(t);
      entityManager.getTransaction().commit();
      return t;
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
      LOGGER.error(msg);      
      throw new ApplicationException(e.getMessage());
    }
  }

  @Override
  public T createCm(T t, String usuario, int Perfil) throws ApplicationException {
    throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public T find(BigDecimal id) throws ApplicationException {
    try {
      if (!entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().begin();
      }
      entityManager.clear();
      T resultEntity = this.entityManager.find(entityClass, id);
      entityManager.flush();
      entityManager.getTransaction().commit();
      if (resultEntity == null) {
        throw new NoResultException("No Fue posible encontrar una entidad ".concat(entityClass.getCanonicalName()).concat("-").concat(id.toString()));
      }
      return resultEntity;
    } catch (NoResultException e) {
      String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
      LOGGER.error(msg);
      throw new ApplicationException(e.getMessage());
    }
  }

  @Override
  public T update(T t) throws ApplicationException {
    throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public T updateCm(T t, String usuario, int Perfil) throws ApplicationException {
    throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean delete(T t) throws ApplicationException {
    throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean deleteCm(T t, String usuario, int Perfil) throws ApplicationException {
    throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
  }
}
