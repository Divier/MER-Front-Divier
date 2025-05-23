/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.TecArchivosCambioEstrato;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author bocanegravm
 */
public class TecArcCamEstratoDaoImpl extends GenericDaoImpl<TecArchivosCambioEstrato> {
    
    private static final Logger LOGGER = LogManager.getLogger(TecArcCamEstratoDaoImpl.class);

    /**
     * Metodo para consultar todos los links de las rutas de los archivos de
     * soportes asociados a una solicitud de cambio de estrato.
     *
     * @author Victor Bocanegra
     * @param solicitud solicitud de cambio de estrato.
     * @return List<TecArchivosCambioEstrato lista de URLS @throws
     * ApplicationException
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<TecArchivosCambioEstrato> findUrlsByIdSolicitud(Solicitud solicitud) throws ApplicationException {
        try {
            Query query = entityManager.createNamedQuery("TecArchivosCambioEstrato.findUrlsByIdSolicitud");
            query.setParameter("solicitudObj", solicitud);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            return (List<TecArchivosCambioEstrato>) query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
        /**
     * Metodo para consultar todos los links de las rutas de los archivos de
     * soportes asociados a una solicitud de cambio de estrato.
     *
     * @author Juan David Hernandez
     * @param direccionDetalladaId
     * @return List<TecArchivosCambioEstrato lista de URLS @throws
     * ApplicationException
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<TecArchivosCambioEstrato> findUrlsByIdDireccionDetalladaSolicitud(BigDecimal direccionDetalladaId) throws ApplicationException {
        try {
            Query query = entityManager.createNamedQuery("TecArchivosCambioEstrato.findUrlsByIdDireccionDetalladaSolicitud");
            query.setParameter("direccionDetalladaId", direccionDetalladaId);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            return (List<TecArchivosCambioEstrato>) query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
