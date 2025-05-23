package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dao.IGenericDao;
import co.com.claro.mgl.dtos.AtributoValorDto;
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
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

/**
 *
 * @author User
 * @param <T>
 */
public class GenericDaoImpl<T> implements IGenericDao<T> {

    protected EntityManager entityManager;
    protected Class<T> entityClass;
    private static final Logger LOGGER = LogManager.getLogger(GenericDaoImpl.class);

    public GenericDaoImpl() {
        try {
            if (entityManager == null) {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("catastro-ejbPU");
                entityManager = emf.createEntityManager();
                entityManager.setProperty("eclipselink.flush-clear.cache", "drop");
                entityManager.setProperty("javax.persistence.cache.storeMode", "REFRESH");
                entityManager.getEntityManagerFactory().getCache().evictAll();
            }
            ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
            this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        } catch (Exception e) {
            LOGGER.error("Error al inicializar GenericDao: " + e.getMessage(), e);
        }
    }

    @Override
    public T createCm(T t, String usuario, int perfil) throws ApplicationException {
        try {
            if (usuario.equals("") || perfil == 0) {
                throw new ApplicationException("El Usuario o perfil no pueden ser nulos");
            }
            Class<?> myEntity = t.getClass();
            Method myMethodFc = myEntity.getMethod("setFechaCreacion", Date.class);
            myMethodFc.invoke(t, new Date());
            
            Method myMethodUc = myEntity.getMethod("setUsuarioCreacion", String.class);
            myMethodUc.invoke(t, usuario);
            
            Method myMethodPc = myEntity.getMethod("setPerfilCreacion", int.class);
            myMethodPc.invoke(t, perfil);
            
            Method myMethodEr = myEntity.getMethod("setEstadoRegistro", int.class);
            myMethodEr.invoke(t, 1);

            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            this.entityManager.flush();
            this.entityManager.persist(t);
            entityManager.getTransaction().commit();

            return t;
        } catch (ApplicationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @Override
    public T find(BigDecimal id) throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            T resultEntity = null;
            if (id != null) {
                resultEntity = this.entityManager.find(entityClass, id);
            }
            entityManager.flush();
            entityManager.getTransaction().commit();
            if (resultEntity == null) {
                throw new NoResultException("No Fue posible encontrar una entidad ".concat(entityClass.getCanonicalName()).concat("-").concat(id != null ? id.toString() : ""));
            }
            return resultEntity;
        } catch (NoResultException e) {         
            return null;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
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
            T resultEntity = null;
            if (id != null) {
                resultEntity = this.entityManager.find(entityClass, id);
            }
            entityManager.flush();
            entityManager.getTransaction().commit();
            if (resultEntity == null) {
                throw new NoResultException("No Fue posible encontrar una entidad ".concat(entityClass.getCanonicalName()).concat("-").concat(id.toString()));
            }
            return resultEntity;
        } catch (NoResultException e) {
            LOGGER.error(e.getMessage());
            return null;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @Override
    public T updateCm(T t, String usuario, int perfil) throws ApplicationException {
        try {
            if (usuario.equals("") || perfil == 0) {
                throw new ApplicationException("El Usuario o perfil No pueden ser nulos");
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
        } catch (ApplicationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException  e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteCm(T t, String usuario, int perfil) throws ApplicationException {
        try {
            if (usuario.equals("") || perfil == 0) {
                throw new ApplicationException("El Usuario o perfil No pueden ser nulos");
            }
            
            if (t != null) {
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
            } else {
                throw new ApplicationException("La entidad a eliminar no puede ser nula.");
            }
        } catch (ApplicationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<T> create(List<T> tList) throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            
            this.entityManager.flush();
            
            for (T t : tList) {
                this.entityManager.persist(t);
            }
            entityManager.getTransaction().commit();
            return tList;
        } catch (PersistenceException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<T> findAllItems() throws ApplicationException {
        try {
            List<T> resultList;
            Query q = entityManager.createQuery("Select t from " + entityClass.getSimpleName() + " t");
            resultList = (List<T>) q.getResultList();
            return resultList;
        } catch (PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<T> findAllItemsActive() throws ApplicationException {
        try {
            List<T> resultList;
            Query q = entityManager.createQuery("Select t from " + entityClass.getSimpleName()
                    + " t WHERE t.estadoRegistro = :estadoRegistro");
            q.setParameter("estadoRegistro", 1);
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<T>) q.getResultList();
            return resultList;
        } catch (PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public Long countAllItems() throws ApplicationException {
        try {
            Query q = entityManager.createQuery("Select COUNT(1) from " + entityClass.getSimpleName() + " t where t.estadoRegistro = 1");
            return (Long) q.getSingleResult();
        } catch (PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<T> findItemsPaginacion(int firstResult, int maxResults) throws ApplicationException {
        try {
            List<T> resultList;
            Query q = entityManager.createQuery("Select t from " + entityClass.getSimpleName() + " t where t.estadoRegistro = 1");
            q.setFirstResult(firstResult);
            q.setMaxResults(maxResults);
            resultList = (List<T>) q.getResultList();
            getEntityManager().clear();
            return resultList;
        } catch (PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }  catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @Override
    public T create(T t) throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            this.entityManager.flush();
            this.entityManager.persist(t);
            entityManager.getTransaction().commit();
            return t;
        } catch (PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }  catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @Override
    public T update(T t) throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.merge(t);
            entityManager.getTransaction().commit();
            return t;
        } catch (PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }  catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(T t) throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            t = entityManager.merge(t);
            this.entityManager.remove(t);
            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }  catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    
    /**
     * Buscar un listado
     * <p>
     * Busca un listado teniendo en cuenta el rango y los demás atributos.
     * Por defecto, ordena a manera Descendiente.
     *
     * @author becerraarmr
     * @param range rango de busqueda
     * @param attributeOrder orden de la consulta
     * @param listAttributes Lista de atributos y sus valores para realizar la
     * consulta.
     *
     * @return
     * @throws ApplicationException Error en la ejecución de la consulta.
     */
    public List<T> findRange(int[] range,
            String attributeOrder, List<AtributoValorDto> listAttributes)
            throws ApplicationException {
        return (findRange(range, attributeOrder, listAttributes, true));
    }
    
    
    /**
     * Buscar un listado
     * <p>
     * Busca un listado teniendo en cuenta el rango y los demás atributos
     *
     * @author becerraarmr
     * @param range rango de busqueda
     * @param attributeOrder orden de la consulta
     * @param listAttributes Lista de atributos y sus valores para realizar la
     * consulta.
     * @param orderDESC Indica si se debe realizar order descendiente.
     *
     * @return
     * @throws ApplicationException Error en la ejecución de la consulta.
     */
    public List<T> findRange(int[] range,
            String attributeOrder, List<AtributoValorDto> listAttributes, boolean orderDESC)
            throws ApplicationException {
        
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> c = cq.from(entityClass);

            Order order;
            if (orderDESC) {
                order = cb.desc(c.get(attributeOrder));
            } else {
                order = cb.asc(c.get(attributeOrder));
            }


            AtributoValorDto item = listAttributes.get(0);
            AtributoValorDto item1 = listAttributes.get(1);

            cq.select(c).where(
                    cb.and(
                            cb.equal(c.get(item.getNombreAtributo()),
                                    item.getValorAtributo()),
                            cb.equal(c.get(item1.getNombreAtributo()),
                                    item1.getValorAtributo()))).orderBy(order);
            Query q = entityManager.createQuery(cq);
            int maxResults = range[1] - range[0] + 1;
            if (maxResults < 0) maxResults = 0;
            q.setMaxResults(maxResults);
            q.setFirstResult(range[0]);
            return q.getResultList();
        } catch (PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } 
    }

    /**
     * Buscar un listado
     * <p>
     * Busca un listado teniendo en cuenta el rango
     *
     * @author becerraarmr
     * @param range rango de busqueda
     *
     * @return listado de registros encontrados
     * @throws ApplicationException Error en la ejecución de la consulta.
     */
    public List<T> findRange(int[] range)
            throws ApplicationException {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> c = cq.from(entityClass);
            cq.select(c);
            Query q = entityManager.createQuery(cq);
            q.setMaxResults(range[1] - range[0] + 1);
            q.setFirstResult(range[0]);
            return q.getResultList();
        } catch (PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } 
    }

    /**
     * Contar los registros encontrados.
     *
     * Cuenta los registros según los valores de la consulta.
     *
     * @author becerraarmr
     * @param listAttributes lista de atributos de la entidad y sus correspon-
     * dientes valores.
     *
     * @return el valor que representa el atributo
     * @throws ApplicationException Error en la ejecución de la consulta.
     */
    public int count(List<AtributoValorDto> listAttributes)
            throws ApplicationException {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(entityClass);
            Root<T> c = cq.from(entityClass);
            AtributoValorDto item = listAttributes.get(0);
            AtributoValorDto item1 = listAttributes.get(1);
            cq.select(entityManager.getCriteriaBuilder().count(c)).
                    where(cb.and(
                            cb.equal(c.get(item.getNombreAtributo()),
                                    item.getValorAtributo()),
                            cb.equal(c.get(item1.getNombreAtributo()),
                                    item1.getValorAtributo())));
            Query q = entityManager.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } catch (NoResultException e) {
            LOGGER.error(e.getMessage(), e);
            return 0;
        } catch (PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } 
    }

    /**
     * Contar según un atributo
     *
     * Se cuenta los registros según el atributo.
     *
     * @author becerraarmr
     *
     * @param atribute attributo para hacer el filtro de cuenta
     * @return valor entero
     * @throws ApplicationException Si ocurre un error
     */
    public int count(AtributoValorDto atribute)
            throws ApplicationException {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(entityClass);
            Root<T> c = cq.from(entityClass);
            cq.select(entityManager.getCriteriaBuilder().count(c)).
                    where(cb.equal(c.get(atribute.getNombreAtributo()),
                            atribute.getValorAtributo()));
            Query q = entityManager.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } catch (NoResultException e) {
            LOGGER.error(e.getMessage(), e);
            return 0;
        } catch (PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } 
    }

    /**
     * Contar los registros encontrados.
     *
     * Cuenta los registros según los valores de la consulta.
     *
     * @author becerraarmr
     *
     * @return la cantidad de registros
     * @throws ApplicationException Error en la ejecución de la consulta.
     */
    public int count()
            throws ApplicationException {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(entityClass);
            Root<T> c = cq.from(entityClass);
            cq.select(entityManager.getCriteriaBuilder().count(c));
            Query q = entityManager.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } catch (NoResultException e) {
            LOGGER.error(e.getMessage(), e);
            return 0;
        } catch (PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } 
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    
}