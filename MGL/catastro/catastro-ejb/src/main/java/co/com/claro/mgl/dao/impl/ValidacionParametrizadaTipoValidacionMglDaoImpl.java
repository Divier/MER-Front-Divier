/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ValidacionParametrizadaTipoValidacionMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Juan David Hernandez
 */
public class ValidacionParametrizadaTipoValidacionMglDaoImpl 
        extends GenericDaoImpl <ValidacionParametrizadaTipoValidacionMgl>{
    
    private static final Logger LOGGER = LogManager.getLogger(ValidacionParametrizadaTipoValidacionMglDaoImpl.class);
    
     /**
     * Obtiene el listado de validaciones por tipo de la base de datos
     *
     * @param tipoValidacion tipo de validacion que se desea obtener.
     * @author Juan David Hernandez
     * @return Listado de validaciones
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<ValidacionParametrizadaTipoValidacionMgl> findValidacionParametrizadaByTipo(BigDecimal tipoValidacion)
            throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            List<ValidacionParametrizadaTipoValidacionMgl> resulList;
            String sql = "SELECT vc FROM ValidacionParametrizadaTipoValidacionMgl vc "
                    + " Where vc.tipoValidacionBasicaId.basicaId =:tipoValidacion AND "
                    + " vc.estadoRegistro =:estado ";
            Query query = entityManager.createQuery(sql);

            query.setParameter("tipoValidacion", tipoValidacion);
            query.setParameter("estado", 1);

            resulList = query.getResultList();

            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
    
}
