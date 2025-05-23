/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.VisorFactibilidad;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Query;
import java.math.BigDecimal;

/**
 *  Clase para obtener la relacion de la factibilidad con el codigo de consulta
 *
 * @author Yasser Leon
 */
public class VisorFactibilidadDaoImpl extends GenericDaoImpl<VisorFactibilidad> {

    private static final Logger LOGGER = LogManager.getLogger(VisorFactibilidadDaoImpl.class);

    /**
     * Metodo para obtener una factibilidad dado el codigo asociado
     *
     * @param codigo codigo asociado a la factibilidad
     * @return {@link BigDecimal}
     * @throws ApplicationException excepcion
     */
    public BigDecimal findIdFactibilidadByCodigo(String codigo) throws ApplicationException {
        try {
            Query query = entityManager.createNamedQuery("VisorFactibilidad.findIdFactibilidadByCodigo");
            query.setParameter("codigo", codigo);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            return (BigDecimal) query.getSingleResult();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

}
