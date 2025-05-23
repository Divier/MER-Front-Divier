/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglParametrosTrabajos;
import co.com.claro.mgl.jpa.entities.MglParametrosTrabajosDetail;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Orlando Velasquez
 */
public class MglParametrosTrabajosDetailDaoImpl extends GenericDaoImpl<MglParametrosTrabajos> {

    private static final Logger LOGGER = LogManager.getLogger(MglParametrosTrabajosDetailDaoImpl.class);

    public MglParametrosTrabajosDetail createDetalleTareaProgramada(
            MglParametrosTrabajosDetail mglParametrosTrabajosDetail) 
        throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            this.entityManager.flush();
            this.entityManager.persist(mglParametrosTrabajosDetail);
            entityManager.getTransaction().commit();
            return mglParametrosTrabajosDetail;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            LOGGER.error(e.getMessage());
            throw new ApplicationException("Se ha producido un error al momento de crear el detalle de la tarea programada: " + e.getMessage(), e);
        }
    }

    public MglParametrosTrabajosDetail updateDetalleTareaProgramada(
            MglParametrosTrabajosDetail mglParametrosTrabajosDetail) throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.merge(mglParametrosTrabajosDetail);
            entityManager.getTransaction().commit();
            return mglParametrosTrabajosDetail;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            LOGGER.error(e.getMessage());
            throw new ApplicationException("Se ha producido un error al momento de actualizar el detalle de la tarea programada: " + e.getMessage(), e);
        }
    }

    public int contarNumeroDeRegistrosDetalleTareaProgramada() throws ApplicationException {
        Query query = entityManager.createQuery("SELECT count(1) FROM "
                + "MglParametrosTrabajosDetail m");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        int result = query.getResultList() == null ? 0
                : ((Long) query.getSingleResult()).intValue();
        return result;
    }

    public MglParametrosTrabajosDetail findUltimoRegistroDetalleTareaProgramada( CmtBasicaMgl estadoTarea ) throws ApplicationException {
        TypedQuery<MglParametrosTrabajosDetail> query = entityManager.createQuery("SELECT m FROM "
                + " MglParametrosTrabajosDetail m WHERE m.estado =:estadoFinalizado "
                + " ORDER BY m.paramTrabajosDetailId DESC ", MglParametrosTrabajosDetail.class);
        query.setFirstResult(0);
        query.setMaxResults(1);
        query.setParameter("estadoFinalizado", estadoTarea);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<MglParametrosTrabajosDetail> result = query.getResultList();
        if (!result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }

}
