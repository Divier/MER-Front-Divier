/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ConfCreahppAuto;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * DAO de la configuracion de para creacion de HHPP. Permite acceder a los datos
 * almacenados en la BB de la configuracion de las ciudades para la creacion
 * autmatica de los HHPP desde la solicitud.
 *
 * @author Johnnatan Ortiz
 * @versión 1.00.000
 */
public class ConfCreahppAutoDaoImpl extends GenericDaoImpl<ConfCreahppAuto> {
    
    private static final Logger LOGGER = LogManager.getLogger(ConfCreahppAutoDaoImpl.class);

    /**
     * Obtien la configuracion para una ciudad.Permite obtener los datos de
 configuracion para la creacion automatica de un HHPP en la creacion de la
 solicitud para una ciudad determinada.
     *
     * @author Johnnatan Ortiz
     * @param city ciudad de la cual se desea consultar la configuracion.
     * @return configuracion de creacion automatica de la ciudad.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public ConfCreahppAuto findByIdCity(GeograficoPoliticoMgl city)
            throws ApplicationException {
        try {

            ConfCreahppAuto result;
            Query query = entityManager.createNamedQuery("ConfCreahppAuto.findByIdCity");
            query.setParameter("gpoId", city);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            result = (ConfCreahppAuto) query.getSingleResult();
            return result;
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
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
     * Obtiene todas configuraciones.Permite obtener todos los datos de
 configuracion almacenados de creacion automatica de un HHPP en la
 creacion de la solicitud.
     *
     * @author Johnnatan Ortiz
     * @return configuracion de creacion automatica de la ciudad.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<ConfCreahppAuto> findAll()
            throws ApplicationException {
        try {
            List<ConfCreahppAuto> result;
            Query query = entityManager.createNamedQuery("ConfCreahppAuto.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            result = (List<ConfCreahppAuto>) query.getResultList();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
}
