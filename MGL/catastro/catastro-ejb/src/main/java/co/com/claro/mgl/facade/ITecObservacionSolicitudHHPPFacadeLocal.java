package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.TecObservacionSolicitudHHPP;

import java.util.List;

/**
 * @author José René Miranda de la O
 */
public interface ITecObservacionSolicitudHHPPFacadeLocal {

    /**
     * Metodo para consultar las observaciones asociados a una solicitud de escalamientos HHPP.
     *
     * @author José René Miranda de la O
     * @param solicitud solicitud de cambio de estrato.
     * @return List<TecObservacionSolicitudHHPP lista de URLS
     * @throws ApplicationException
     */
    List<TecObservacionSolicitudHHPP> findByIdSolicitud(final Solicitud solicitud)
            throws ApplicationException;

    /**
     * Método para crear observaciones asociado a una solicitud de escalamiento HHPP
     *
     * @author José René Miranda de la O
     * @param tecObservacionSolicitudHHPP
     * @return
     * @throws ApplicationException
     */
    TecObservacionSolicitudHHPP crear(final TecObservacionSolicitudHHPP tecObservacionSolicitudHHPP) throws ApplicationException;

}
