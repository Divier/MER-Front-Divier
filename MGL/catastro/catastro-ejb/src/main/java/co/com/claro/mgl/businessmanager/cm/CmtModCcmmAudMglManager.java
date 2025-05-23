/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtModCcmmAudMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtModificacionesCcmmAudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;

import java.util.List;

/**
 *
 * @author bocanegravm
 */
public class CmtModCcmmAudMglManager {
    
    CmtModCcmmAudMglDaoImpl dao = new CmtModCcmmAudMglDaoImpl();

    
   /**
     * Crea un registro con los datos originales del subedificio,
     * direccion de la ccmm o hhpp  en el repositorio.
     *
     * @author victor Bocanegra
     * @param perfil
     * @param usuario
     * @return CmtModificacionesCcmmAudMgl
     * creada en el repositorio
     * @throws ApplicationException
     */
    public CmtModificacionesCcmmAudMgl crear(CmtModificacionesCcmmAudMgl cmtModificacionesCcmmAudMgl,
            String usuario, int perfil)
            throws ApplicationException {

        return dao.createCm(cmtModificacionesCcmmAudMgl, usuario, perfil);

    }
    
    /**
     * Consulta todos los registros que tiene una solicitud en la tabla de
     * modificaciones.
     *
     * @author victor Bocanegra
     * @param solicitudCmMgl
     * @return List<CmtModificacionesCcmmAudMgl>
     * @throws ApplicationException
     */
    public List<CmtModificacionesCcmmAudMgl> findBySolicitud(CmtSolicitudCmMgl solicitudCmMgl)
            throws ApplicationException {

        return dao.findBySolicitud(solicitudCmMgl);
    }
}
