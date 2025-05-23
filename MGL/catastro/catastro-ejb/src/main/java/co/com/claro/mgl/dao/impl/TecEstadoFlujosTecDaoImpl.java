/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TecEstadosFlujosTecHab;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;


/**
 * Data Access Object para el manejo de Tecnolog&iacute;as de Estado por Flujo.
 * 
 * @author User
 */
public class TecEstadoFlujosTecDaoImpl  extends GenericDaoImpl<TecEstadosFlujosTecHab>{
    
    private static final Logger LOGGER = LogManager.getLogger(TecEstadoFlujosTecDaoImpl.class);
    
   
    /**
     * Obtiene el siguiente estado del flujo.
     * 
     * @param basicaEstado B&aacute;sica del Estado.
     * @param estadoActual Estado Actual.
     * @param estadoNuevo Nuevo Estado.
     * @return Siguiente Estado.
     * @throws ApplicationException 
     */
   public TecEstadosFlujosTecHab findNextEstate(String basicaEstado, String estadoActual, String estadoNuevo ) throws ApplicationException {
       TecEstadosFlujosTecHab tec = null;
        try {
            String queryStr = "SELECT t FROM TecEstadosFlujosTecHab t "
                            + "WHERE t.basicaEstadoTecnologia = :codigoTecnologia "
                            + "AND t.estadoActual = :estadoActual "
                            + "AND t.estadoSiguiente = :estadoNuevo";

            Query query = entityManager.createQuery(queryStr);
            query.setParameter("codigoTecnologia", basicaEstado);
            query.setParameter("estadoActual", estadoActual);
            query.setParameter("estadoNuevo", estadoNuevo);

            tec = (TecEstadosFlujosTecHab) query.getSingleResult();
        } catch (NonUniqueResultException e) {
            String msg = "La consulta '"+ClassUtils.getCurrentMethodName(this.getClass())+"' obtuvo más de un resultado: " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        } catch (NoResultException e) {
            LOGGER.error("La consulta '"+ClassUtils.getCurrentMethodName(this.getClass())+"' no arrojó ningún resultado.");
            tec = null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar la consulta '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg, e);
            throw new ApplicationException(msg, e);
        }

        return tec;
   
   }

}
