package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.TecObservacionSolicitudHHPPManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.TecObservacionSolicitudHHPP;

import javax.ejb.Stateless;
import java.util.List;

/**
 * @author José René Miranda de la O
 */
@Stateless
public class TecObservacionSolicitudHHPPFacade implements ITecObservacionSolicitudHHPPFacadeLocal {

    /**
     * Metodo para consultar las observaciones asociados a una solicitud de escalamientos HHPP.
     *
     * @param solicitud solicitud de cambio de estrato.
     * @return List<TecObservacionSolicitudHHPP lista de URLS
            * @ throws ApplicationExceptio n
            * @ author José René Miranda de la
            O
     */
    @Override
    public List<TecObservacionSolicitudHHPP> findByIdSolicitud(final Solicitud solicitud) throws ApplicationException {
        final TecObservacionSolicitudHHPPManager manager = new TecObservacionSolicitudHHPPManager();
        return manager.findByIdSolicitud(solicitud);
    }

    /**
     * Método para crear observaciones asociado a una solicitud de escalamiento HHPP
     *
     * @param tecObservacionSolicitudHHPP
     * @return
     * @throws ApplicationException
     * @author José René Miranda de la O
     */
    @Override
    public TecObservacionSolicitudHHPP crear(final TecObservacionSolicitudHHPP tecObservacionSolicitudHHPP) throws ApplicationException {
        final TecObservacionSolicitudHHPPManager manager = new TecObservacionSolicitudHHPPManager();
        return manager.crear(tecObservacionSolicitudHHPP);
    }
}
