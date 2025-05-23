/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.VetoCiudadMgl;
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
public class VetoCiudadMglDaoImpl extends GenericDaoImpl <VetoCiudadMgl>{
    
    private static final Logger LOGGER = LogManager.getLogger(VetoCiudadMglDaoImpl.class);
    
        
    /**
     * Obtiene el listado de Ids de Veto que contengan el id GPO buscado
     *
     * @param idGpo Ciudad o centro poblado a buscar.
     * @author Juan David Hernandez
     * @return Listado de ids de Veto
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<BigDecimal> findVetoCiudadByIdGpo(BigDecimal idGpo)
            throws ApplicationException {
        try {
            List<BigDecimal> resulList;
            String sql = "SELECT vc.vetoId.vetoId FROM VetoCiudadMgl vc "
                    + " Where vc.centroPobladoId.gpoId =:idGpo AND "
                    + " vc.estadoRegistro =:estado ";
            Query query = entityManager.createQuery(sql);

            query.setParameter("idGpo", idGpo);
            query.setParameter("estado", 1);

            resulList = query.getResultList();

            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
 
    
      
}
