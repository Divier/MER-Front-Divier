/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtModificacionesCcmmAudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public interface CmtModCcmmAudMglFacadeLocal {

    /**
     * Crea un registro con los datos originales del subedificio, direccion de
     * la ccmm o hhpp en el repositorio.
     *
     * @author victor Bocanegra
     * @param perfil
     * @param usuario
     * @return CmtModificacionesCcmmAudMgl creada en el repositorio
     * @throws ApplicationException
     */
    CmtModificacionesCcmmAudMgl crear(CmtModificacionesCcmmAudMgl cmtModificacionesCcmmAudMgl,
            String usuario, int perfil)
            throws ApplicationException;
    
        /**
     * Consulta todos los registros que tiene una solicitud en la tabla de
     * modificaciones.
     *
     * @author victor Bocanegra
     * @param solicitudCmMgl
     * @return List<CmtModificacionesCcmmAudMgl>
     * @throws ApplicationException
     */
    List<CmtModificacionesCcmmAudMgl> findBySolicitud(CmtSolicitudCmMgl solicitudCmMgl)
            throws ApplicationException; 
}
