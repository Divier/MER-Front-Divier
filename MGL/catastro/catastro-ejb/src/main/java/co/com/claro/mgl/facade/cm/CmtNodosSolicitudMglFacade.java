/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtNodosSolicitudMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNodosSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author bocanegravm
 */
@Stateless
public class CmtNodosSolicitudMglFacade implements CmtNodosSolicitudMglFacadeLocal {

    /**
     * Crea un nodo X tecnologia asociados a una solicitud en el repositorio.
     *
     * @author Victor Bocanegra
     * @param cmtNodosSolicitudMgl
     * @param cmtSolicitudCmMgl
     * @param usuarioCrea
     * @param perfilCrea
     * @return true si los crea correctamente false si no los crea
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    @Override
    public boolean create(Map<CmtBasicaMgl, NodoMgl> datosGestion,
            CmtSolicitudCmMgl cmtSolicitudCmMgl, String usuarioCrea,
            int perfilCrea) throws ApplicationException {

        CmtNodosSolicitudMglManager cmtNodosSolicitudMglManager = new CmtNodosSolicitudMglManager();
        return cmtNodosSolicitudMglManager.create(datosGestion, cmtSolicitudCmMgl, usuarioCrea, perfilCrea);
    }

    /**
     * Actualiza nodo X tecnologia asociados a una solicitud en el repositorio.
     *
     * @author Victor Bocanegra
     * @param lstCmtNodosSolicitudMgls
     * @param usuarioMod
     * @param perfilCrea
     * @param perfilMod
     * @return true si los Modifica correctamente false si no los Modifica
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    @Override
    public void update(List<CmtNodosSolicitudMgl> lstCmtNodosSolicitudMgls, String usuarioCrea,
            int perfilCrea) throws ApplicationException {

        CmtNodosSolicitudMglManager cmtNodosSolicitudMglManager = new CmtNodosSolicitudMglManager();
        cmtNodosSolicitudMglManager.update(lstCmtNodosSolicitudMgls, usuarioCrea, perfilCrea);
    }

    /**
     * Autor: Victor Bocanegra Consulta lista en estructura CmtNodosSolicitudMgl
     *
     * @param cmtSolicitudCmMgl
     * @return List<CmtNodosSolicitudMgl>
     * @throws ApplicationException Error lanzado al realizar consultas
     */
    @Override
    public List<CmtNodosSolicitudMgl> findBySolicitudId(CmtSolicitudCmMgl cmtSolicitudCmMgl)
            throws ApplicationException {

        CmtNodosSolicitudMglManager cmtNodosSolicitudMglManager = new CmtNodosSolicitudMglManager();
        return cmtNodosSolicitudMglManager.findBySolicitudId(cmtSolicitudCmMgl);

    }
}
