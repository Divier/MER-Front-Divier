/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.visitasTecnicas.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.visitasTecnicas.entities.SolicitudCambioEstratoEntity;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author user
 */
@Remote
public interface SolicitudCambioEstratoEJBRemote {
   
    List<SolicitudCambioEstratoEntity> getSolicitudesCEByIdSol(String idSolicitud) throws ApplicationException;
    boolean deleteSolicitudesCEByIdSol(String idSolicitud) throws ApplicationException;
    
}
