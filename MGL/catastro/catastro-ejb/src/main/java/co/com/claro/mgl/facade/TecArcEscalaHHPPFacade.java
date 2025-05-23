package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.TecArcEscalaHHPPManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.TecArchivosEscalamientosHHPP;
import co.com.claro.mgl.jpa.entities.TecObservacionSolicitudHHPP;

import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author José René Miranda de la O
 */
@Stateless
public class TecArcEscalaHHPPFacade implements ITecArcEscalaHHPPFacadeLocal {
    
    
    /**
     * Metodo para consultar todos los links de las rutas de los archivos de
     * soportes asociados a una solicitud de escalamientos HHPP.
     *
     * @author José Rebé Miranda de la O
     * @param solicitud solicitud de cambio de estrato.
     * @return List<TecArchivosEscalamientosHHPP lista de URLS @throws
     * ApplicationException
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public List<TecArchivosEscalamientosHHPP> findUrlsByIdSolicitud(Solicitud solicitud)
            throws ApplicationException {

        TecArcEscalaHHPPManager tecArcEscalaHHPPManager = new TecArcEscalaHHPPManager();
        return tecArcEscalaHHPPManager.findUrlsByIdSolicitud(solicitud);
    }
    
    /**
     * Metodo para consultar todos los links de las rutas de los archivos de
     * soportes asociados a una solicitud de escalamientos HHPP.
     *
     * @author Victor Bocanegra
     * @param direccionDetalladaId
     * @return List<TecArchivosEscalamientosHHPP lista de URLS @throws
     * ApplicationException
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public List<TecArchivosEscalamientosHHPP> findUrlsByIdDireccionDetalladaSolicitud(BigDecimal direccionDetalladaId)
            throws ApplicationException {

        TecArcEscalaHHPPManager tecArcEscalaHHPPManager = new TecArcEscalaHHPPManager();
        return tecArcEscalaHHPPManager.findUrlsByIdDireccionDetalladaSolicitud(direccionDetalladaId);
    }
    
    @Override
    public TecArchivosEscalamientosHHPP crear(TecArchivosEscalamientosHHPP tecArchivosEscalamientosHHPP,
            String usuCrea, int perCrea) throws ApplicationException {

        TecArcEscalaHHPPManager tecArcEscalaHHPPManager = new TecArcEscalaHHPPManager();
        return tecArcEscalaHHPPManager.crear(tecArchivosEscalamientosHHPP, usuCrea, perCrea);
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
    @Override
    public List<TecArchivosEscalamientosHHPP> findUrlsByIdSolicitudAndOrigenAndObservacion(final Solicitud solicitud, final String origen, final TecObservacionSolicitudHHPP observacion)
            throws ApplicationException {

        TecArcEscalaHHPPManager tecArcEscalaHHPPManager = new TecArcEscalaHHPPManager();
        return tecArcEscalaHHPPManager.findUrlsByIdSolicitudAndOrigenAndObservacion(solicitud, origen, observacion);
    }
}
