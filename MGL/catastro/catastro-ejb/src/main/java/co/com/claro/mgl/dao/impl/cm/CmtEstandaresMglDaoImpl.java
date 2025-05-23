/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtEstandaresMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author cardenaslb
 */
public class CmtEstandaresMglDaoImpl extends GenericDaoImpl<CmtEstandaresMgl> {
    private static final Logger LOGGER = LogManager.getLogger(CmtEstandaresMglDaoImpl.class);
    
    
    /**
     * Consulta del codigo rr segun el valor Catastro
     * 
     * @param valor catastro
     * @return valor rr
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public String findByValor(String valor)
            throws ApplicationException {
        try {
            Query query = entityManager.createNamedQuery("CmtEstandaresMgl.findByValor");
            query.setParameter("valor_catastro", valor);
            return (String) query.getSingleResult();
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
             LOGGER.error(msg);
            return null;
        }
    }
    
}
