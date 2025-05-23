/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.dao.procesomasivo;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.entities.procesomasivo.CargueArchivoLogItem;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Realizar las consultas a la entidad CargueArchivoLogItem
 * <p>
 * Se realiza las diferentes consultas a la base de datoas haciendo uso de la
 * entidad CargueArchivoLogItem.
 *
 * @author becerraarmr
 *
 */
public class CargueArchivoLogItemDao
        extends GenericDaoImpl<CargueArchivoLogItem> {
    
    private static final Logger LOGGER = LogManager.getLogger(CargueArchivoLogItemDao.class);

    public CargueArchivoLogItemDao() {
    }

    public BigDecimal sequence() throws ApplicationException {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        String sql = "SELECT "+Constant.MGL_DATABASE_SCHEMA+"."+ "TEC_CARG_ARCHIVO_LOG_ITEM_SEQ.NEXTVAL FROM SYS.DUAL";
        Query query = entityManager.createNativeQuery(sql);
        BigDecimal t = (BigDecimal) query.getSingleResult();
        entityManager.getTransaction().commit();
        return t;

    }
    
     /**
     * Busca todos los registros que fueron leidos del excel
     * @author Victor Bocanegra 
     * @param idArchivo id del archivo resumen
     * @return List<CargueArchivoLogItem>
     * @throws ApplicationException
     */
    public List<CargueArchivoLogItem> findByIdArchivoGeneral(Long idArchivo)
            throws ApplicationException {
        try {
            List<CargueArchivoLogItem> resulList;
            Query query = entityManager.createNamedQuery("CargueArchivoLogItem.findByIdArchivoLog");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (idArchivo != null) {
                query.setParameter("idArchivoLog", idArchivo);
            }
            resulList = (List<CargueArchivoLogItem>) query.getResultList();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
    /**
     * Busca todos los registros que fueron leidos del masivo de fraudes
     * @author Diana Enriquez 
     * @param idArchivo id del archivo resumen
     * @return List<CargueArchivoLogItem>
     * @throws ApplicationException
     */
    public List<CargueArchivoLogItem> findByIdArchivoFraude(Long idArchivo)
            throws ApplicationException {
        try {
            List<CargueArchivoLogItem> resulList;
            Query query = entityManager.createNamedQuery("CargueArchivoLogItem.findByIdArchivoLogFraude");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (idArchivo != null) {
                query.setParameter("idArchivoLog", idArchivo);
            }
            resulList = (List<CargueArchivoLogItem>) query.getResultList();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
       /**
     * Busca todos los registros que tuvieron cambios
     * @author Victor Bocanegra 
     * @param idArchivo id del archivo resumen
     * @return List<CargueArchivoLogItem>
     * @throws ApplicationException
     */
    public List<CargueArchivoLogItem> findByIdArchivoGeneralAndProcesado(Long idArchivo)
            throws ApplicationException {
        try {
            List<CargueArchivoLogItem> resulList;
            Query query = entityManager.createNamedQuery("CargueArchivoLogItem.findByIdArchivoLogAndProcesado");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (idArchivo != null) {
                query.setParameter("idArchivoLog", idArchivo);
            }
            resulList = (List<CargueArchivoLogItem>) query.getResultList();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
    
    /**
     * Busca registro por id del archivo general y complemento
     *
     * @author Victor Bocanegra
     * @param idArchivo id del archivo resumen
     * @param idComplemento id del complemento
     * @return CargueArchivoLogItem
     * @throws ApplicationException
     */
    public CargueArchivoLogItem findByIdArchivoLogAndIdComplemento(Long idArchivo, BigDecimal idComplemento)
            throws ApplicationException {
        try {
            CargueArchivoLogItem result;
            Query query = entityManager.createNamedQuery("CargueArchivoLogItem.findByIdArchivoLogAndIdComplemento");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (idArchivo != null) {
                query.setParameter("idArchivoLog", idArchivo);
            }
            if (idComplemento != null) {
                query.setParameter("idComplemento", idComplemento);
            }
            result = (CargueArchivoLogItem) query.getSingleResult();
            return result;
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, ex);
        }
    }
}
