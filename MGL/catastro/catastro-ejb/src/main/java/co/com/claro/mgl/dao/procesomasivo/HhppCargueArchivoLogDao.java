/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.dao.procesomasivo;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.entities.procesomasivo.HhppCargueArchivoLog;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Realizar las consultas a la entidad HhppCargueArchivoLog
 * <p>
 * Se realiza las diferentes consultas a la base de datoas con respecto
 * HhppCargueArchivoLog
 *
 * @author becerraarmr
 *
 */
public class HhppCargueArchivoLogDao
        extends GenericDaoImpl<HhppCargueArchivoLog> {
    
    private static final Logger LOGGER = LogManager.getLogger(HhppCargueArchivoLogDao.class);

    public Long sequencia() throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();
            String sql = "select "+Constant.MGL_DATABASE_SCHEMA+"."+ "TEC_TEC_HABI_CARGUE_ARC_LOG_SQ.nextval from dual";

            Query query = this.entityManager.createNativeQuery(sql);
            Long cant = null;
            if (query != null) {
                List<Object> list = query.getResultList();
                if (!list.isEmpty()) {
                    Object aux = list.get(0);
                    if (aux != null) {
                        cant = new Long(aux.toString());
                    }
                }
            }
            entityManager.getTransaction().commit();
            return cant;
        } catch (NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Buscar en la base de datos el nombre del archivo a procesar
     *
     * Se busca en la tabla HHPP_CARGUE_ARCHIVO_LOG el nombre del archivo a
     * procesar que tenga el estado 0 y en la primera parte del listado
     * asccendente.
     *
     * @author becerraarmr
     * @return HhppCargueArchivoLog con la data correspondiete
     * @throws co.com.claro.mgl.error.ApplicationException si hay algún problema
     * en la realización de la consulta.
     */
    public HhppCargueArchivoLog findToProcesar() throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();

            String nameQuery = "HhppCargueArchivoLog.findByEstadoOrderFechaRegistroAsc";
            Query query = this.entityManager.createNamedQuery(nameQuery);
            query.setParameter("estado", 0);
            List<HhppCargueArchivoLog> list = query.getResultList();
            HhppCargueArchivoLog hhppCargueArchivoLog = null;
            if (!list.isEmpty()) {
                hhppCargueArchivoLog = list.get(0);
            }
            entityManager.getTransaction().commit();
            return hhppCargueArchivoLog;
        } catch (NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Buscar en la base de datos Se busca en la base de datos el listado de
     * registros correspondientes a HhppCargueArchivoLog según el parámetro
     * estado.
     *
     * @author becerraarmr
     * @param estado valor para realizar la busqueda
     * @return el listado con la solicitud requerida.
     * @throws co.com.claro.mgl.error.ApplicationException si hay algún error al
     * momento de realizar la petición.
     */
    public List<HhppCargueArchivoLog> findAllEstado(short estado)
            throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();

            String nameQuery = "HhppCargueArchivoLog.findByEstadoOrderFechaRegistroAsc";
            Query query = this.entityManager.createNamedQuery(nameQuery);
            query.setParameter("estado", estado);
            List<HhppCargueArchivoLog> list = query.getResultList();
            entityManager.getTransaction().commit();
            return list;
        } catch (NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Buscar en la base de datos Se busca en la base de datos el listado de
     * registros correspondientes a HhppCargueArchivoLog según el parámetro
     * estado y el rango establecido.
     *
     * @author becerraarmr
     * @param estado valor para realizar la busqueda
     * @param rango vector de inicio y fin de la busqueda
     * @param usuario usuario que ha realizado el cargue.
     * @return el listado con la solicitud requerida.
     * @throws co.com.claro.mgl.error.ApplicationException si hay algún error al
     * momento de realizar la petición.
     */
   public List<HhppCargueArchivoLog> findRangeEstado(
            int[] rango, short estado, String usuario)
            throws ApplicationException {
        try {
            
            String nameQuery = "HhppCargueArchivoLog.findByEstadoUsuarioOrderFechaRegistroDesc";
            Query query = this.entityManager.createNamedQuery(nameQuery);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("estado", estado);
            query.setParameter("usuario", usuario);
            
            if(rango != null){
            query.setMaxResults(rango[1] - rango[0] + 1);
            query.setFirstResult(rango[0]);                
            }
            List<HhppCargueArchivoLog> list = query.getResultList();
            return list;
            
        } catch (NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
   
    /**
     * Diana -> findRangeEstado para Fraudes 
     * @param rango
     * @param estado
     * @param usuario
     * @return
     * @throws ApplicationException 
     */
    public List<HhppCargueArchivoLog> findRangeEstadoByOrigen(
            int[] rango, short estado, String usuario)
            throws ApplicationException {
        try {
            
            String nameQuery = "HhppCargueArchivoLog.findByEstadoUsuarioOrderFechaRegistroDescByOrigen";
            Query query = this.entityManager.createNamedQuery(nameQuery);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("estado", estado);
            query.setParameter("usuario", usuario);
            
            if(rango != null){
            query.setMaxResults(rango[1] - rango[0] + 1);
            query.setFirstResult(rango[0]);                
            }
            List<HhppCargueArchivoLog> list = query.getResultList();
            return list;
            
        } catch (NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Buscar en la base de datos Se busca en la base de datos el listado de
     * registros correspondientes a HhppCargueArchivoLog según el parámetro
     * estado y el rango establecido.
     *
     * @author becerraarmr
     * @param estado valor para realizar la busqueda
     * @param rango vector de inicio y fin de la busqueda
     * @return el listado con la solicitud requerida.
     * @throws co.com.claro.mgl.error.ApplicationException si hay algún error al
     * momento de realizar la petición.
     */
    public List<HhppCargueArchivoLog> findRangeEstado(
            int[] rango, short estado)
            throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();

            String nameQuery = "HhppCargueArchivoLog.findByEstadoOrderFechaRegistroAsc";
            Query query = this.entityManager.createNamedQuery(nameQuery);
            query.setParameter("estado", estado);
            query.setMaxResults(rango[1] - rango[0] + 1);
            query.setFirstResult(rango[0]);
            List<HhppCargueArchivoLog> list = query.getResultList();
            entityManager.getTransaction().commit();
            return list;
        } catch (NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
    /**
     * Buscar en la base de datos Se busca en la base de datos el listado de
     * registros correspondientes a HhppCargueArchivoLog según el parámetro
     * estado y el rango establecido., para Fraudes
     *
     * @author Diana
     * @param estado valor para realizar la busqueda
     * @param rango vector de inicio y fin de la busqueda
     * @return el listado con la solicitud requerida.
     * @throws co.com.claro.mgl.error.ApplicationException si hay algún error al
     * momento de realizar la petición.
     */
    public List<HhppCargueArchivoLog> findRangeEstadoByOrigen(
            int[] rango, short estado)
            throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();

            String nameQuery = "HhppCargueArchivoLog.findByEstadoOrderFechaRegistroAscByOrigen";
            Query query = this.entityManager.createNamedQuery(nameQuery);
            query.setParameter("estado", estado);
            query.setMaxResults(rango[1] - rango[0] + 1);
            query.setFirstResult(rango[0]);
            List<HhppCargueArchivoLog> list = query.getResultList();
            entityManager.getTransaction().commit();
            return list;
        } catch (NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Solicitar a la base de datos el conteo de los registros según el estado.
     * Se solicita a la base de datos el conteo de los registros que tengan el
     * estado establecido en la tabla HHPP_CARGUE_ARCHIVO_LOG
     *
     * @author becerraarmr
     * @param estado valor para realizar el conteo.
     * @param usuario usuario que realizó el cargue.
     * @return el conteo realizado según la petición.
     */
    public int countEstado(short estado, String usuario) throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();
            javax.persistence.criteria.CriteriaQuery cq
                    = entityManager.getCriteriaBuilder().createQuery();
            javax.persistence.criteria.Root<HhppCargueArchivoLog> rt
                    = cq.from(HhppCargueArchivoLog.class);
            cq.select(entityManager.getCriteriaBuilder().count(rt));
            javax.persistence.Query query = entityManager.createQuery(cq);
            query.setParameter("estado", estado);
            query.setParameter("usuario", usuario);
            entityManager.getTransaction().commit();
            return ((Long) query.getSingleResult()).intValue();
        } catch (NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Solicitar a la base de datos el conteo de los registros según el estado.
     * Se solicita a la base de datos el conteo de los registros que tengan el
     * estado establecido en la tabla HHPP_CARGUE_ARCHIVO_LOG
     *
     * @author becerraarmr
     * @param estado valor para realizar el conteo.
     * @return el conteo realizado según la petición.
     */
    public int countEstado(short estado) throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();
            javax.persistence.criteria.CriteriaQuery cq
                    = entityManager.getCriteriaBuilder().createQuery();
            javax.persistence.criteria.Root<HhppCargueArchivoLog> rt
                    = cq.from(HhppCargueArchivoLog.class);
            cq.select(entityManager.getCriteriaBuilder().count(rt));
            javax.persistence.Query query = entityManager.createQuery(cq);
            query.setParameter("estado", estado);
            entityManager.getTransaction().commit();
            return ((Long) query.getSingleResult()).intValue();
        } catch (NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
    /**
     * Buscar en la base de datos el registro del archivo.
     *
     * @author bocanegra vm
     * @param nombreArchivo nombre del archivo
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException si hay algún error al
     * momento de realizar la petición.
     */
    public HhppCargueArchivoLog findArchivoByNombre(String nombreArchivo)
            throws ApplicationException {
        try {
            HhppCargueArchivoLog hhppCargueArchivoLog;
            Query query = this.entityManager.createNamedQuery("HhppCargueArchivoLog.findByNombreArchivo");
            query.setParameter("nombreArchivo", nombreArchivo);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            hhppCargueArchivoLog = (HhppCargueArchivoLog) query.getSingleResult();
            return hhppCargueArchivoLog;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + ex.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
    /**
     * Buscar en la base de datos el registro del archivo resumen con el nombre
     * del archivo TCRM.
     *
     * @author bocanegra vm
     * @param nombreArchivo nombre del archivo
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException si hay algún error al
     * momento de realizar la petición.
     */
    public HhppCargueArchivoLog findByNombreArchivoTcrm(String nombreArchivo)
            throws ApplicationException {
        try {
            HhppCargueArchivoLog hhppCargueArchivoLog;
            Query query = this.entityManager.createNamedQuery("HhppCargueArchivoLog.findByNombreArchivoTcrm");
            query.setParameter("nombreArchivo", nombreArchivo);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            hhppCargueArchivoLog = (HhppCargueArchivoLog) query.getSingleResult();
            return hhppCargueArchivoLog;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + ex.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
}
