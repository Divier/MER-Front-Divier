/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;


import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MarcasHhppMgl;
import co.com.claro.mgl.jpa.entities.TecMarcasTecnologiaHabAud;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author espinosadiea
 */
public class TecMarcasTecnologiaHabAudDaoImpl extends GenericDaoImpl<TecMarcasTecnologiaHabAud> {
    private static final Logger LOGGER = LogManager.getLogger(TecMarcasTecnologiaHabAudDaoImpl.class);

    public List<TecMarcasTecnologiaHabAud> obtenerTodos() throws ApplicationException {
        try{
            Query query = entityManager.createNamedQuery("TecMarcasTecnologiaHabAud.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            List<TecMarcasTecnologiaHabAud> resultList = (List<TecMarcasTecnologiaHabAud>) query.getResultList();
            return (resultList);
        }catch( Exception e ){
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<TecMarcasTecnologiaHabAud> obtenerLog(MarcasHhppMgl marcaHomePassSeleccionado) throws ApplicationException {
        try{
            Query query = entityManager.createNamedQuery("TecMarcasTecnologiaHabAud.findByMarcasTecnologiaHabId");
            query.setParameter("marcasTecnologiaHabId", marcaHomePassSeleccionado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            List<TecMarcasTecnologiaHabAud> resultList = (List<TecMarcasTecnologiaHabAud>) query.getResultList();
            return (resultList);
        }catch( Exception e ){
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
}
