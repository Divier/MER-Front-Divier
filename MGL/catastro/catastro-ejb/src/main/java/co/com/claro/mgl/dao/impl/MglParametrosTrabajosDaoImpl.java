/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglParametrosTrabajos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.ArrayList;

/**
 *
 * @author Orlando Velasquez
 */
public class MglParametrosTrabajosDaoImpl extends GenericDaoImpl<MglParametrosTrabajos> {

    private static final Logger LOGGER = LogManager.getLogger(MglParametrosTrabajosDaoImpl.class);
    
    
    /**
     * Obtiene el listado de par&aacute;metros de trabajo por nombre.
     * @param nombre Nombre del par&aacute;metro.
     * @return Listado de par&aacute;metros de trabajo.
     * @throws ApplicationException 
     */
    public ArrayList<MglParametrosTrabajos> findMglParametrosTrabajosByName(String nombre) throws ApplicationException {
        try {
            ArrayList<MglParametrosTrabajos> resultList;
            TypedQuery<MglParametrosTrabajos> query = entityManager.createQuery("SELECT m FROM MglParametrosTrabajos m WHERE UPPER(m.nombre) = :nombre", MglParametrosTrabajos.class);
            query.setParameter("nombre", nombre.toUpperCase());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = new ArrayList<>(query.getResultList());
            if (resultList.isEmpty()) {
                return null;
            } else {
                return resultList;
            }
        } catch (PersistenceException e) {
            String msg = "Error al momento de ejecutar la consulta de los parámetros de trabajo con nombre '" + nombre + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
    
    /**
     * Obtiene el par&aacute;metro de trabajo por nombre y servidor.
     * @param nombre Nombre del par&aacute;metro.
     * @param server Nombre del servidor.
     * @return Par&aacute;metro de trabajo.
     * @throws ApplicationException 
     */
    public MglParametrosTrabajos findMglParametrosTrabajosByName(String nombre, String server) throws ApplicationException {
        try {
            TypedQuery<MglParametrosTrabajos> query = entityManager.createQuery("SELECT m FROM MglParametrosTrabajos m "
                    + "WHERE UPPER(m.nombre) = :nombre AND "
                    + " UPPER(m.nombreServidor) = :nombreServer", MglParametrosTrabajos.class);
            query.setParameter("nombre", nombre.toUpperCase());
            query.setParameter("nombreServer", server.toUpperCase());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            return query.getSingleResult();
        } catch (NonUniqueResultException e) {
            String msg = "La consulta de los trabajos con nombre '" + nombre + "' para el servidor '" + server + "' obtuvo más de un resultado.";
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        } catch (NoResultException e) {
            String msg = "No se obtuvo resultados para la consulta de los trabajos con nombre '" + nombre + "' para el servidor '" + server + "'.";
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de realizar la consulta de los trabajos con nombre '" + nombre + "' para el servidor '" + server + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    public MglParametrosTrabajos updateMglParametrosTrabajos(MglParametrosTrabajos mglParametrosTrabajos) throws ApplicationException {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(mglParametrosTrabajos);
            entityManager.getTransaction().commit();
            return mglParametrosTrabajos;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
