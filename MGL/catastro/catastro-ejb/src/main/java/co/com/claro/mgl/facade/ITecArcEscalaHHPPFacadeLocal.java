/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.TecArchivosEscalamientosHHPP;
import co.com.claro.mgl.jpa.entities.TecObservacionSolicitudHHPP;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author José René Miranda de la O
 */
public interface ITecArcEscalaHHPPFacadeLocal {
    
      /**
     * Metodo para consultar todos los links de las rutas de los archivos de
     * soportes asociados a una solicitud de escalamientos HHPP.
     *
     * @author José René Miranda de la O
     * @param solicitud solicitud de cambio de estrato.
     * @return List<TecArchivosEscalamientosHHPP lista de URLS @throws
     * ApplicationException
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    List<TecArchivosEscalamientosHHPP> findUrlsByIdSolicitud(Solicitud solicitud)
            throws ApplicationException;
    
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
    List<TecArchivosEscalamientosHHPP> findUrlsByIdDireccionDetalladaSolicitud(BigDecimal direccionDetalladaId)
            throws ApplicationException;

    /**
     * Método para crear el link de las rutas de los archivos de soporte asociado a una solicitud de escalamiento HHPP
     *
     * @author José René Miranda de la O
     * @param tecArchivosEscalamientosHHPP
     * @param usuCrea
     * @param perCrea
     * @return
     * @throws ApplicationException
     */
    TecArchivosEscalamientosHHPP crear(TecArchivosEscalamientosHHPP tecArchivosEscalamientosHHPP,
            String usuCrea, int perCrea) throws ApplicationException;

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
    List<TecArchivosEscalamientosHHPP> findUrlsByIdSolicitudAndOrigenAndObservacion(final Solicitud solicitud, final String origen, final TecObservacionSolicitudHHPP observacion)
            throws ApplicationException;
}
