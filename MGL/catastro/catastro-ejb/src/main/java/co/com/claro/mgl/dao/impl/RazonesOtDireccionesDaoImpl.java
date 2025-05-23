/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglRazonesOtDireccion;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class RazonesOtDireccionesDaoImpl extends GenericDaoImpl<MglRazonesOtDireccion> {
    
    private static final Logger LOGGER = LogManager.getLogger(RazonesOtDireccionesDaoImpl.class);

    public List<MglRazonesOtDireccion> findRazonesOtDireccionByTipoOTDirecciones(
            TipoOtHhppMgl tipoOtDirecciones)
            throws ApplicationException {
        StringBuilder queryString;
        queryString = new StringBuilder();
        queryString.append("SELECT m")
                .append(" FROM MglRazonesOtDireccion m")
                .append(" WHERE m.tipoOtId = :tipoOtId")
                .append(" AND m.estadoRegistro = 1");

        Query query = entityManager.createQuery(queryString.toString());
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0        
        query.setParameter("tipoOtId", tipoOtDirecciones);
        return query.getResultList();

    }

    public MglRazonesOtDireccion eliminarRazonOtDirecciones(MglRazonesOtDireccion razonesOtDirecciones)
            throws ApplicationException {

        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            this.entityManager.flush();
            this.entityManager.merge(razonesOtDirecciones);
            entityManager.getTransaction().commit();
            return razonesOtDirecciones;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }

    }
}
