/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.TecArcEscalaHHPPDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.TecArchivosEscalamientosHHPP;
import co.com.claro.mgl.jpa.entities.TecObservacionSolicitudHHPP;

import java.math.BigDecimal;
import java.util.List;


/**
 * Manager asociado a las operaciones de CRUD de proceso de escalamiento de HHPP
 *
 * @author José René Miranda de la O
 */
public class TecArcEscalaHHPPManager {


    TecArcEscalaHHPPDaoImpl tecArcEscalaHHPPDaoImpl = new TecArcEscalaHHPPDaoImpl();

    /**
     * Registra el escalamiento de HHPP
     *
     * @param tecArchivosEscalamientosHHPP {@link TecArchivosEscalamientosHHPP}
     * @param usuCrea usuario de creación
     * @param perCrea perfil de creación
     * @return  {@link TecArchivosEscalamientosHHPP}
     * @throws ApplicationException Excepcion de la APP
     * @author José René Miranda de la O
     */
    public TecArchivosEscalamientosHHPP crear(final TecArchivosEscalamientosHHPP tecArchivosEscalamientosHHPP,
                                          final String usuCrea, final int perCrea) throws ApplicationException {
        return tecArcEscalaHHPPDaoImpl.createCm(tecArchivosEscalamientosHHPP, usuCrea, perCrea);
    }
    
     /**
     * Metodo para consultar todos los links de las rutas de los archivos de
     * soportes asociados a una solicitud de escalamientos HHPP.
     *
     * @author José René Miranda de la O
     * @param solicitud solicitud de escalamiento hhpp.
     * @return List<TecArchivosEscalamientosHHPP> lista de URLS
     * @throws ApplicationException Excepción de la App
     */
    public List<TecArchivosEscalamientosHHPP> findUrlsByIdSolicitud(final Solicitud solicitud)
            throws ApplicationException {
        
         return tecArcEscalaHHPPDaoImpl.findUrlsByIdSolicitud(solicitud);
    }
    
     /**
     * Metodo para consultar todos los links de las rutas de los archivos de
     * soportes asociados a una solicitud de escalamientosHHPP.
     *
     * @author José René Miranda de la O
     * @param direccionDetalladaId
     * @return List<TecArchivosCambioEstrato> lista de URLS
     * @throws ApplicationException Excepción de la APP
     */
    public List<TecArchivosEscalamientosHHPP> findUrlsByIdDireccionDetalladaSolicitud(final BigDecimal direccionDetalladaId)
            throws ApplicationException {
        
         return tecArcEscalaHHPPDaoImpl.findUrlsByIdDireccionDetalladaSolicitud(direccionDetalladaId);
    }

    /**
     * Metodo para consultar todos los links de las rutas de los archivos de
     * soportes asociados a una solicitud de escalamiento HHPP y por medio del origen de guardado.
     *
     * @author José René Miranda de la O
     * @param solicitud solicitud de escalamiento.
     * @param origen origen de donde se guardo el archivo.
     * @return List<TecArchivosCambioEstrato> lista de URLS
     * @throws ApplicationException Excepción de la App
     */
    public List<TecArchivosEscalamientosHHPP> findUrlsByIdSolicitudAndOrigenAndObservacion(final Solicitud solicitud, final String origen, final TecObservacionSolicitudHHPP observacion)
            throws ApplicationException {

        return tecArcEscalaHHPPDaoImpl.findUrlsByIdSolicitudAndOrigenAndObservacion(solicitud, origen, observacion);
    }
}
