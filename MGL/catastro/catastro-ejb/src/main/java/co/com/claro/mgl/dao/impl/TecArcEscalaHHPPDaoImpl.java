package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.TecArchivosEscalamientosHHPP;
import co.com.claro.mgl.jpa.entities.TecObservacionSolicitudHHPP;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

/**
 * Implementaciones para proceso de escalamiento de HHPP
 *
 * @author José René Miranda de la O
 */
public class TecArcEscalaHHPPDaoImpl extends GenericDaoImpl<TecArchivosEscalamientosHHPP> {
    
    private static final Logger LOGGER = LogManager.getLogger(TecArcEscalaHHPPDaoImpl.class);

    /**
     * Metodo para consultar todos los links de las rutas de los archivos de
     * soportes asociados a una solicitud de escalamiento HHPP.
     *
     * @author José René Miranda de la O
     * @param solicitud solicitud de escalamiento.
     * @return List<TecArchivosCambioEstrato lista de URLS @throws
     * ApplicationException
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<TecArchivosEscalamientosHHPP> findUrlsByIdSolicitud(final Solicitud solicitud) throws ApplicationException {
        try {
            final Query query = entityManager.createNamedQuery("TecArchivosEscalamientosHHPP.findUrlsByIdSolicitud");
            query.setParameter("solicitudObj", solicitud);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            return (List<TecArchivosEscalamientosHHPP>) query.getResultList();
        } catch (Exception e) {
            final String msg = "Se produjo un error al momento de ejecutar el método '"
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
        /**
     * Metodo para consultar todos los links de las rutas de los archivos de
     * soportes asociados a una solicitud de escalamientos HHPP.
     *
     * @author José René Miranda de la O
     * @param direccionDetalladaId
     * @return List<TecArchivosEscalamientosHHPP lista de URLS @throws
     * ApplicationException
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<TecArchivosEscalamientosHHPP> findUrlsByIdDireccionDetalladaSolicitud(final BigDecimal direccionDetalladaId) throws ApplicationException {
        try {
            final Query query = entityManager.createNamedQuery("TecArchivosEscalamientosHHPP.findUrlsByIdDireccionDetalladaSolicitud");
            query.setParameter("direccionDetalladaId", direccionDetalladaId);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            return (List<TecArchivosEscalamientosHHPP>) query.getResultList();
        } catch (Exception e) {
            final String msg = "Se produjo un error al momento de ejecutar el método '"
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Metodo para consultar todos los links de las rutas de los archivos de
     * soportes asociados a una solicitud de escalamiento HHPP y por medio del origen de guardado.
     *
     * @author José René Miranda de la O
     * @param solicitud solicitud de escalamiento.
     * @param origen origen de donde se guardo el archivo.
     * @return List<TecArchivosCambioEstrato lista de URLS @throws
            * ApplicationException
            * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<TecArchivosEscalamientosHHPP> findUrlsByIdSolicitudAndOrigenAndObservacion(final Solicitud solicitud, final String origen, final TecObservacionSolicitudHHPP observacion) throws ApplicationException {
        try {
            final Query query = entityManager.createNamedQuery("TecArchivosEscalamientosHHPP.findUrlsByIdSolicitudAndOrigen");
            query.setParameter("solicitudObj", solicitud);
            query.setParameter("origen", origen);
            query.setParameter("observacion", observacion);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            return (List<TecArchivosEscalamientosHHPP>) query.getResultList();
        } catch (Exception e) {
            final String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
