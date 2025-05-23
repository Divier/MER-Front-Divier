package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.TecObservacionSolicitudHHPP;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Query;
import java.util.List;

public class TecObservacionSolicitudHHPPDaoImpl  extends GenericDaoImpl<TecObservacionSolicitudHHPP> {

    private static final Logger LOGGER = LogManager.getLogger(TecArcEscalaHHPPDaoImpl.class);

    /**
     * Metodo para consultar las observaciones de una solicitud.
     *
     * @author José René Miranda de la O
     * @param solicitud solicitud de escalamiento.
     * @return List<TecObservacionSolicitudHHPP lista de las observaciones
     * @throws ApplicationException
     */
    public List<TecObservacionSolicitudHHPP> findByIdSolicitud(final Solicitud solicitud) throws ApplicationException {
        try {
            final Query query = entityManager.createNamedQuery("TecObservacionSolicitudHHPP.findUrlsByIdSolicitud");
            query.setParameter("solicitudObj", solicitud);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            return (List<TecObservacionSolicitudHHPP>) query.getResultList();
        } catch (Exception e) {
            final String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

}
