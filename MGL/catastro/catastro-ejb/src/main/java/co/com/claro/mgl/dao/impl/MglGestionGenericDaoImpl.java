/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dao.IGenericDao;
import co.com.claro.mgl.error.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;


/**
 * Clase que maneja las conecciones a la base de datos de gestion
 * @author villamilc
 * @version 1.0
 * @param <T>
 */
public class MglGestionGenericDaoImpl<T> implements IGenericDao<T>  {

    protected EntityManager entityManager;
    protected Class<T> entityClass;
    private static final Logger LOGGER = LogManager.getLogger(MglGestionGenericDaoImpl.class);

    
    /**
     * Constructor de la clase.
     *
     * @author Carlos Villamil
     */    
    public MglGestionGenericDaoImpl() {
        try {
            if (entityManager == null) { 
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionseguridad-ejbPU");
                entityManager = emf.createEntityManager();
                entityManager.setProperty("eclipselink.flush-clear.cache", "drop");
                entityManager.setProperty("javax.persistence.cache.storeMode", "REFRESH");
                entityManager.getEntityManagerFactory().getCache().evictAll();
            }
            ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
            this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        } catch (Exception e) {
            LOGGER.error("Error en GenericDao: ", e);
        }
    }

    /**
     * Maneja generico de persistencia create.
     *
     * @author carlos Villamil
     * @param t
     * @param usuario usuario que realiza transaccion
     * @param perfil Perfil de la transaccion
     * @return <T> clase generica de respuesta la misma de la entrada
     * @throws ApplicationException
     */    
    @Override
    public T createCm(T t, String usuario, int perfil) throws ApplicationException {
        try {
            if (usuario.equals("") || perfil == 0) {
                throw new ApplicationException("El Usuario o perfil no pueden ser nulos");
            }
            Class<?> myEntity = t.getClass();
            Method myMethodFc = myEntity.getMethod("setFechaCreacion", Date.class);
            myMethodFc.invoke(t, new Date());
            Method myMethodFd = myEntity.getMethod("setFechaEdicion", Date.class);
            myMethodFd.invoke(t, new Date());
            Method myMethodUc = myEntity.getMethod("setUsuarioCreacion", String.class);
            myMethodUc.invoke(t, usuario);
            Method myMethodUd = myEntity.getMethod("setUsuarioEdicion", String.class);
            myMethodUd.invoke(t, usuario);
            Method myMethodPc = myEntity.getMethod("setPerfilCreacion", int.class);
            myMethodPc.invoke(t, perfil);
            Method myMethodPd = myEntity.getMethod("setPerfilEdicion", int.class);
            myMethodPd.invoke(t, perfil);
            Method myMethodEr = myEntity.getMethod("setEstadoRegistro", int.class);
            myMethodEr.invoke(t, 1);

            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            this.entityManager.persist(t);
            entityManager.flush();

            entityManager.getTransaction().commit();

            return t;
        } catch (ApplicationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Maneja generico de consulta.
     *
     * @author carlos Villamil
     * @param id Es la clave primaria de la clasde de la percistencia
     * @return <T> clase generica de respuesta
     * @throws ApplicationException
     */    
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
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Realiza una consulta generica de entidad por el id o primary key
     *
     * @param id String pk entidad
     * @return T Valor generico dela entidad encontrada la tarea.
     * @throws ApplicationException
     */
    public T find(String id) throws ApplicationException {
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
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Maneja generico de persistencia actualizacion.
     *
     * @author carlos Villamil
     * @param t
     * @param usuario usuario que realiza transaccion
     * @param perfil Perfil de la transaccion
     * @return <T> clase generica de respuesta la misma de la entrada
     * @throws ApplicationException
     */    
    
    @Override
    public T updateCm(T t, String usuario, int perfil) throws ApplicationException {
        try {
            if (usuario.equals("") || perfil == 0) {
                throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
            }
            Class<?> myEntity = t.getClass();
            Method myMethodFd = myEntity.getMethod("setFechaEdicion", Date.class);
            myMethodFd.invoke(t, new Date());
            Method myMethodUd = myEntity.getMethod("setUsuarioEdicion", String.class);
            myMethodUd.invoke(t, usuario);
            Method myMethodPd = myEntity.getMethod("setPerfilEdicion", int.class);
            myMethodPd.invoke(t, perfil);
            entityManager.getTransaction().begin();
            entityManager.merge(t);
            entityManager.getTransaction().commit();
            return t;
        } catch (ApplicationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Maneja generico de persistencia delete.
     *
     * @author carlos Villamil
     * @param t
     * @param usuario usuario que realiza transaccion
     * @param perfil Perfil de la transaccion
     * @return boolean ejecucion exitosa o fallida
     * @throws ApplicationException
     */    
    
    @Override
    public boolean deleteCm(T t, String usuario, int perfil) throws ApplicationException {
        try {
            if (usuario.equals("") || perfil == 0) {
                throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
            }
            Class<?> myEntity = t.getClass();
            Method myMethodFd = myEntity.getMethod("setFechaEdicion", Date.class);
            myMethodFd.invoke(t, new Date());
            Method myMethodUd = myEntity.getMethod("setUsuarioEdicion", String.class);
            myMethodUd.invoke(t, usuario);
            Method myMethodPd = myEntity.getMethod("setPerfilEdicion", int.class);
            myMethodPd.invoke(t, perfil);
            Method myMethodEr = myEntity.getMethod("setEstadoRegistro", int.class);
            myMethodEr.invoke(t, 0);

            entityManager.getTransaction().begin();
            this.entityManager.merge(t);
            entityManager.getTransaction().commit();
            return true;
        } catch (ApplicationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    
    /**
     * Maneja generico de persistencia create de listas dependientes.
     *
     * @author carlos Villamil
     * @param tList
     * @return List<T> Lista de la clase generica de respuesta la misma de la entrada
     * @throws ApplicationException
     */    
    
    public List<T> create(List<T> tList) throws ApplicationException {
        try {
            entityManager.getTransaction().begin();
            for (T t : tList) {
                this.entityManager.persist(t);
            }
            entityManager.getTransaction().commit();
            return tList;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Maneja generico de persistencia find all.
     *
     * @author carlos Villamil
     * @return <T> clase generica de respuesta la misma de la entrada
     * @throws ApplicationException
     */    
    
    public List<T> findAllItems() throws ApplicationException {
        try {
            List<T> resultList;
            Query q = entityManager.createQuery("Select t from " + entityClass.getSimpleName() + " t");
            resultList = (List<T>) q.getResultList();
            return resultList;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Maneja generico de persistencia find all.
     *
     * @author carlos Villamil
     * @return <T> clase generica de respuesta la misma de la entrada
     * @throws ApplicationException
     */    
    
    public List<T> findAllItemsActive() throws ApplicationException {
        try {
            List<T> resultList;
            Query q = entityManager.createQuery("Select t from " + entityClass.getSimpleName()
                    + " t WHERE t.estadoRegistro = :estadoRegistro");
            q.setParameter("estadoRegistro", 1);
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<T>) q.getResultList();
            return resultList;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Maneja generico de persistencia conteo de los registros.
     *
     * @author carlos Villamil
     * @return Long conteo resultado
     * @throws ApplicationException
     */    
    
    public Long countAllItems() throws ApplicationException {
        try {
            Query q = entityManager.createQuery("Select COUNT(1) from " + entityClass.getSimpleName() + " t");
            return (Long) q.getSingleResult();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Maneja generico de persistencia cunsulta de paginacion.
     *
     * @author carlos Villamil
     * @param firstResult primer registro de la pagina
     * @param maxResults Numero de rultados por pagina
     * @return List<T> clase generica de respuesta la misma de la entrada
     * @throws ApplicationException
     */    
    
    public List<T> findItemsPaginacion(int firstResult, int maxResults) throws ApplicationException {
        try {
            List<T> resultList;
            Query q = entityManager.createQuery("Select t from " + entityClass.getSimpleName() + " t");
            q.setFirstResult(firstResult);
            q.setMaxResults(maxResults);
            resultList = (List<T>) q.getResultList();
            return resultList;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Maneja generico de persistencia create.
     *
     * @author carlos Villamil
     * @param t
     * @return <T> clase generica de respuesta la misma de la entrada
     * @throws ApplicationException
     */    
    
    @Override
    public T create(T t) throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            this.entityManager.persist(t);
            entityManager.getTransaction().commit();
            return t;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Maneja generico de persistencia update.
     *
     * @author carlos Villamil
     * @param t
     * @return <T> clase generica de respuesta la misma de la entrada
     * @throws ApplicationException
     */    
    
    @Override
    public T update(T t) throws ApplicationException {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(t);
            entityManager.getTransaction().commit();
            return t;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Maneja generico de persistencia delete.
     *
     * @author carlos Villamil
     * @param t
     * @return boolean exito ofracaso de la transaccion
     * @throws ApplicationException
     */    
    
    @Override
    public boolean delete(T t) throws ApplicationException {
        try {
            entityManager.getTransaction().begin();
            t = entityManager.merge(t);
            this.entityManager.remove(t);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
