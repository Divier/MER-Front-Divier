/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.TecArcCamEstratoDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.TecArchivosCambioEstrato;
import java.math.BigDecimal;
import java.util.List;


/**
 *
 * @author bocanegravm
 */
public class TecArcCamEstratoManager {


    TecArcCamEstratoDaoImpl tecArcCamEstratoDaoImpl = new TecArcCamEstratoDaoImpl();

    public TecArchivosCambioEstrato crear(TecArchivosCambioEstrato tecArchivosCambioEstrato,
            String usuCrea, int perCrea) throws ApplicationException {
        return tecArcCamEstratoDaoImpl.createCm(tecArchivosCambioEstrato, usuCrea, perCrea);
    }
    
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
    public List<TecArchivosCambioEstrato> findUrlsByIdSolicitud(Solicitud solicitud)
            throws ApplicationException {
        
         return tecArcCamEstratoDaoImpl.findUrlsByIdSolicitud(solicitud);
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
    public List<TecArchivosCambioEstrato> findUrlsByIdDireccionDetalladaSolicitud(BigDecimal direccionDetalladaId)
            throws ApplicationException {
        
         return tecArcCamEstratoDaoImpl.findUrlsByIdDireccionDetalladaSolicitud(direccionDetalladaId);
    }
}
