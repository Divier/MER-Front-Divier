/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtInventariosTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoInventarioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Victor Bocanegra
 */
public interface CmtOrdenTrabajoInventarioMglFacadeLocal {

    CmtOrdenTrabajoInventarioMgl crearOtInv(CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl) throws ApplicationException;

    CmtOrdenTrabajoInventarioMgl crearOtInvCm(CmtInventariosTecnologiaMgl invTec, 
            CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl, String usuarioCrea, int perfilCrea,
            CmtSubEdificiosVt cmtSubEdificiosVt
            ) throws ApplicationException;

    CmtOrdenTrabajoInventarioMgl crearOtInvForm(CmtOrdenTrabajoInventarioMgl 
            cmtOrdenTrabajoInventarioMgl, String usuariocrea, int perfilCrea) throws ApplicationException;

    List<CmtOrdenTrabajoInventarioMgl> findByIdOt(BigDecimal Otid) throws ApplicationException;

    CmtOrdenTrabajoInventarioMgl findByIdOtInvTec(BigDecimal idOtInvTec) throws ApplicationException;

    void updateOtInv(char action, CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl, String usuarioEdi, int perfilEdi) throws ApplicationException;

    void deleteOtInv(CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl, String usuarioEdi, int perfilEdi) throws ApplicationException;

    /**
     * Retorna la lista de inventarios de tecnologias asociados a una OT
     *
     * @author Victor Bocanegra
     * @param cmtOrdenTrabajoMgl
     * @param usuarioVt
     * @param perfilVt
     * @return 
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoInventarioMgl> cargarInfo(CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl, String usuarioVt, int perfilVt) throws ApplicationException;
}
