/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.TecArcCamEstratoManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.TecArchivosCambioEstrato;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author bocanegravm
 */
@Stateless
public class TecArcCamEstratoFacade implements TecArcCamEstratoFacadeLocal{
    
    
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
    @Override
    public List<TecArchivosCambioEstrato> findUrlsByIdSolicitud(Solicitud solicitud)
            throws ApplicationException {

        TecArcCamEstratoManager tecArcCamEstratoManager = new TecArcCamEstratoManager();
        return tecArcCamEstratoManager.findUrlsByIdSolicitud(solicitud);
    }
    
              /**
     * Metodo para consultar todos los links de las rutas de los archivos de
     * soportes asociados a una solicitud de cambio de estrato.
     *
     * @author Victor Bocanegra
     * @param direccionDetalladaId
     * @param solicitud solicitud de cambio de estrato.
     * @return List<TecArchivosCambioEstrato lista de URLS @throws
     * ApplicationException
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public List<TecArchivosCambioEstrato> findUrlsByIdDireccionDetalladaSolicitud(BigDecimal direccionDetalladaId)
            throws ApplicationException {

        TecArcCamEstratoManager tecArcCamEstratoManager = new TecArcCamEstratoManager();
        return tecArcCamEstratoManager.findUrlsByIdDireccionDetalladaSolicitud(direccionDetalladaId);
    }
    
    @Override
    public TecArchivosCambioEstrato crear(TecArchivosCambioEstrato tecArchivosCambioEstrato,
            String usuCrea, int perCrea) throws ApplicationException {

        TecArcCamEstratoManager tecArcCamEstratoManager = new TecArcCamEstratoManager();
        return tecArcCamEstratoManager.crear(tecArchivosCambioEstrato, usuCrea, perCrea);
    }
}
