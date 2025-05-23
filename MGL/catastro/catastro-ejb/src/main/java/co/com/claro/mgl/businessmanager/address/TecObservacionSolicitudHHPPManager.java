package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.TecObservacionSolicitudHHPPDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.TecObservacionSolicitudHHPP;

import java.util.List;

/**
 * Manager de apoyo al procesode observación de escalamiento de HHPP.
 *
 * @author José René Miranda de la O
 */
public class TecObservacionSolicitudHHPPManager {

    private TecObservacionSolicitudHHPPDaoImpl tecObservacionSolicitudHHPPDao = new TecObservacionSolicitudHHPPDaoImpl();

    /**
     * Registra la observación sobre el escalamiento de HHPP
     *
     * @param tecObservacionSolicitudHHPP {@link TecObservacionSolicitudHHPP}
     * @return {@link TecObservacionSolicitudHHPP}
     * @throws ApplicationException Excepción de la APP
     * @author José René Miranda de la O
     */
    public TecObservacionSolicitudHHPP crear(final TecObservacionSolicitudHHPP tecObservacionSolicitudHHPP) throws ApplicationException {
        return tecObservacionSolicitudHHPPDao.create(tecObservacionSolicitudHHPP);
    }

    /**
     * Metodo para consultar todas las observaciones de la solicitud
     *
     * @author José René Miranda de la O
     * @param solicitud solicitud de escalamiento hhpp.
     * @return List<TecObservacionSolicitudHHPP > lista de URLS
     * @throws ApplicationException Excepción de la APP
     */
    public List<TecObservacionSolicitudHHPP> findByIdSolicitud(final Solicitud solicitud)
            throws ApplicationException {

        return tecObservacionSolicitudHHPPDao.findByIdSolicitud(solicitud);
    }
}
