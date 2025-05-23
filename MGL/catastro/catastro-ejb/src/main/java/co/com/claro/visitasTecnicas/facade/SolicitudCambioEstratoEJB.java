package co.com.claro.visitasTecnicas.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.visitasTecnicas.business.SolicitudCambioEstratoBusiness;
import co.com.claro.visitasTecnicas.entities.SolicitudCambioEstratoEntity;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless(name = "solicitudCambioEstratoEJB", mappedName = "solicitudCambioEstratoEJB", description = "solicitudCambioEstrato")
@Remote({SolicitudCambioEstratoEJBRemote.class})
public class SolicitudCambioEstratoEJB implements SolicitudCambioEstratoEJBRemote {

    @Override
    public List<SolicitudCambioEstratoEntity> getSolicitudesCEByIdSol(String idSolicitud) throws ApplicationException {
        SolicitudCambioEstratoBusiness business = new SolicitudCambioEstratoBusiness();
        return business.getSolicitudesCambioEByIdSol(idSolicitud);
    }

    @Override
    public boolean deleteSolicitudesCEByIdSol(String idSolicitud) throws ApplicationException{
        SolicitudCambioEstratoBusiness business = new SolicitudCambioEstratoBusiness();
        return business.deleteSolicitudesCambioEByIdSol(idSolicitud);
    }

}
