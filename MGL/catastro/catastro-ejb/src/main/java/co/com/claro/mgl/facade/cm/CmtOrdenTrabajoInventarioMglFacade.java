/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtOrdenTrabajoInventarioMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtInventariosTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoInventarioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Facade Orden de Trabajo Inventario. Expone la logica de negocio para el
 * manejo de inventarios de tecnologias asociados a ordenes de trabajo en el
 * repositorio.
 *
 * @author Victor Bocanegra
 * @version 1.00.000
 */
@Stateless
public class CmtOrdenTrabajoInventarioMglFacade implements CmtOrdenTrabajoInventarioMglFacadeLocal {

    private String user = "";
    private int perfil = 0;

    /**
     * Crea un inventario de tecnologia asociado a una Orden de Trabajo.Permite
 realizar la creacion de un inventario de tecnologia asociado a una Orden
 de Trabajo en el repositorio.
     *
     * @author Victor Bocanegra
     * @param otInv
     * @return Inventario de tecnologia asociado a una Orden de Trabajo
     * @throws ApplicationException
     */
    @Override
    public CmtOrdenTrabajoInventarioMgl crearOtInv(CmtOrdenTrabajoInventarioMgl otInv) throws ApplicationException {
        CmtOrdenTrabajoInventarioMglManager manager = new CmtOrdenTrabajoInventarioMglManager();
        return manager.crearOtInv(otInv, user, perfil);
    }

    /**
     * Crea un inventario de tecnologia asociado a una Orden de Trabajo.Permite
 realizar la creacion de un inventario de tecnologia asociado a una Orden
 de Trabajo en el repositorio.
     *
     * @author Victor Bocanegra
     * @param invTec inventario de tecnologia
     * @param idOt
     * @param cmtSubEdificiosVt
     * @param perfilCrea
     * @return Inventario de tecnologia asociado a una Orden de Trabajo
     * @throws ApplicationException
     */
    @Override
    public CmtOrdenTrabajoInventarioMgl crearOtInvCm(CmtInventariosTecnologiaMgl invTec, 
            CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl, String usuarioCrea, int perfilCrea,
            CmtSubEdificiosVt cmtSubEdificiosVt) throws ApplicationException {

        CmtOrdenTrabajoInventarioMglManager manager = new CmtOrdenTrabajoInventarioMglManager();
        return manager.crearOtInvCm(invTec, cmtOrdenTrabajoMgl, usuarioCrea, perfilCrea,cmtSubEdificiosVt);
    }

    /**
     * Crea un inventario de tecnologia asociado a una Orden de Trabajo.Permite
 realizar la creacion de un inventario de tecnologia asociado a una Orden
 de Trabajo en el repositorio con los datos de entrada del formulario.
     *
     * @author Victor Bocanegra
     * @param cmtOrdenTrabajoInventarioMgl
     * @param usuariocrea
     * @param idOt
     * @param tipoInv
     * @param claseInv
     * @param fab
     * @param cadena
     * @param perfilCrea
     * @return Inventario de tecnologia asociado a una Orden de Trabajo
     * @throws ApplicationException
     */
    @Override
    public CmtOrdenTrabajoInventarioMgl crearOtInvForm(CmtOrdenTrabajoInventarioMgl 
            cmtOrdenTrabajoInventarioMgl, String usuariocrea, int perfilCrea) throws ApplicationException {

        CmtOrdenTrabajoInventarioMglManager manager = new CmtOrdenTrabajoInventarioMglManager();
        return manager.crearOtInvForm(cmtOrdenTrabajoInventarioMgl,usuariocrea, perfilCrea);
    }

    /**
     * Busca el inventarios de tecnologias por el Id del inventario y estado
     * registro
     *
     * @author Victor Bocanegra
     * @param Otid
     * @return CmtOrdenTrabajoInventarioMgl encontradas en el repositorio
     * @throws ApplicationException
     */
    @Override
    public List<CmtOrdenTrabajoInventarioMgl> findByIdOt(BigDecimal Otid) throws ApplicationException {
        CmtOrdenTrabajoInventarioMglManager manager = new CmtOrdenTrabajoInventarioMglManager();
        return manager.findByIdOt(Otid);
    }

    /**
     * Busca el inventarios de tecnologias por el Id del inventario
     *
     * @author Victor Bocanegra
     * @param idOtInvTec ID del inventario
     * @return CmtOrdenTrabajoInventarioMgl encontradas en el repositorio
     * @throws ApplicationException
     */
    @Override
    public CmtOrdenTrabajoInventarioMgl findByIdOtInvTec(BigDecimal idOtInvTec)
            throws ApplicationException {
        CmtOrdenTrabajoInventarioMglManager manager = new CmtOrdenTrabajoInventarioMglManager();
        return manager.findByIdOtInvTec(idOtInvTec);
    }

    /**
     * Actualiza un inventario de tecnologia asociado a una Ot
     *
     * @author Victor Bocanegra
     * @param action
     * @param cmtOrdenTrabajoInventarioMgl
     * @param usuarioEdi
     * @param perfilEdi
     * @throws ApplicationException
     */
    @Override
    public void updateOtInv(char action, CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl, String usuarioEdi, int perfilEdi) throws ApplicationException {

        CmtOrdenTrabajoInventarioMglManager manager = new CmtOrdenTrabajoInventarioMglManager();
        manager.updateOtInv(action, cmtOrdenTrabajoInventarioMgl, usuarioEdi, perfilEdi);
    }

    /**
     * Borra un inventario de tecnologia asociado a una Ot
     *
     * @author Victor Bocanegra
     * @param cmtOrdenTrabajoInventarioMgl
     * @param usuarioEdi
     * @param perfilEdi
     * @throws ApplicationException
     */
    @Override
    public void deleteOtInv(CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl, String usuarioEdi, int perfilEdi) throws ApplicationException {

        CmtOrdenTrabajoInventarioMglManager manager = new CmtOrdenTrabajoInventarioMglManager();
        manager.deleteOtInv(cmtOrdenTrabajoInventarioMgl, usuarioEdi, perfilEdi);
    }

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
    @Override
    public List<CmtOrdenTrabajoInventarioMgl> cargarInfo(CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl, String usuarioVt, int perfilVt) throws ApplicationException {

        CmtOrdenTrabajoInventarioMglManager manager = new CmtOrdenTrabajoInventarioMglManager();
        return manager.cargarInfo(cmtOrdenTrabajoMgl, usuarioVt, perfilVt);
    }
}
