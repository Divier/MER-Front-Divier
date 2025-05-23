/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtNodosSolicitudMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNodosSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bocanegravm
 */
public class CmtNodosSolicitudMglManager {

    private CmtNodosSolicitudMglDaoImpl dao = new CmtNodosSolicitudMglDaoImpl();

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
    public boolean create(Map<CmtBasicaMgl, NodoMgl> datosGestion,
            CmtSolicitudCmMgl cmtSolicitudCmMgl, String usuarioCrea,
            int perfilCrea) throws ApplicationException {

        boolean respuesta = true;
        CmtNodosSolicitudMgl cmtNodosSolicitudMgl = null;

        for (Map.Entry<CmtBasicaMgl, NodoMgl> n : datosGestion.entrySet()) {

            cmtNodosSolicitudMgl = new CmtNodosSolicitudMgl();
            cmtNodosSolicitudMgl.setCmtSolicitudCmMglObj(cmtSolicitudCmMgl);
            cmtNodosSolicitudMgl.setTipoTecnologiaObj(n.getKey());
            cmtNodosSolicitudMgl.setNodoMglObj(n.getValue());
            cmtNodosSolicitudMgl.setFechaEdicion(cmtSolicitudCmMgl.getFechaEdicion());
            cmtNodosSolicitudMgl.setFechaCreacion(cmtSolicitudCmMgl.getFechaCreacion());
            
            cmtNodosSolicitudMgl = dao.createCm(cmtNodosSolicitudMgl, usuarioCrea, perfilCrea);

            if (cmtNodosSolicitudMgl.getNodosSolicitudId() == null) {

                respuesta = false;

            }

        }

        return respuesta;
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
    public void update(List<CmtNodosSolicitudMgl> lstCmtNodosSolicitudMgls, String usuarioCrea,
            int perfilCrea) throws ApplicationException {


        for (CmtNodosSolicitudMgl cmtNodosSolicitudMgl : lstCmtNodosSolicitudMgls) {
            cmtNodosSolicitudMgl.setEstadoRegistro(0);
            dao.updateCm(cmtNodosSolicitudMgl, usuarioCrea, perfilCrea);


        }


    }

    /**
     * Autor: Victor Bocanegra Consulta lista en estructura CmtNodosSolicitudMgl
     *
     * @param cmtSolicitudCmMgl
     * @return List<CmtNodosSolicitudMgl>
     * @throws ApplicationException Error lanzado al realizar consultas
     */
    public List<CmtNodosSolicitudMgl> findBySolicitudId(CmtSolicitudCmMgl cmtSolicitudCmMgl)
            throws ApplicationException {

        return dao.findBySolicitudId(cmtSolicitudCmMgl);
    }
}
