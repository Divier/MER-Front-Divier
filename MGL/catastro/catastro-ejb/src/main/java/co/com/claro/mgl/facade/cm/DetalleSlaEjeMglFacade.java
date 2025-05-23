/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.DetalleSlaEjeMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DetalleSlaEjecucionMgl;
import co.com.claro.mgl.jpa.entities.SlaEjecucionMgl;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author bocanegravm
 */
@Stateless
public class DetalleSlaEjeMglFacade implements DetalleSlaEjeMglFacadeLocal {

    /**
     * Autor: victor bocanegra Metodo para consultar todos los detalles de un
     * Sla de ejecucion activos de la BD
     *
     * @param slaEjecucionMgl
     * @param conTotal
     * @return List<DetalleSlaEjecucionMgl>
     * @throws ApplicationException
     */
    @Override
    public List<DetalleSlaEjecucionMgl> findBySlaEjecucion(SlaEjecucionMgl slaEjecucionMgl, boolean conTotal) throws ApplicationException {
        List<DetalleSlaEjecucionMgl> resulList;
        DetalleSlaEjeMglManager manager = new DetalleSlaEjeMglManager();
        resulList = manager.findBySlaEjecucion(slaEjecucionMgl, conTotal);
        return resulList;
    }

    /**
     * Autor: victor bocanegra Metodo para crear un detalle de SLA de ejecucion
     * en la BD
     *
     * @param detalleSlaEjecucionMgl a crear
     * @param mUser usuario que crea el registro
     * @param mPerfil perfil del usuario que crea el registro
     * @return DetalleSlaEjecucionMgl
     * @throws ApplicationException
     */
    @Override
    public DetalleSlaEjecucionMgl create(DetalleSlaEjecucionMgl detalleSlaEjecucionMgl, String mUser, int mPerfil)
            throws ApplicationException {

        DetalleSlaEjeMglManager manager = new DetalleSlaEjeMglManager();
        return manager.create(detalleSlaEjecucionMgl, mUser, mPerfil);

    }

    /**
     * Autor: victor bocanegra Metodo para borrar logicamente un detalle de un
     * SLA de ejecucion en la BD
     *
     * @param detalleSlaEjecucionMgl a borrar
     * @param mUser usuario que borra el registro
     * @param mPerfil perfil del usuario que borra el registro
     * @return boolean
     * @throws ApplicationException
     */
    @Override
    public boolean delete(DetalleSlaEjecucionMgl detalleSlaEjecucionMgl, String mUser, int mPerfil)
            throws ApplicationException {

        DetalleSlaEjeMglManager manager = new DetalleSlaEjeMglManager();
        return manager.delete(detalleSlaEjecucionMgl, mUser, mPerfil);

    }

    /**
     * Autor: victor bocanegra Metodo para consultar el detalle de un Sla de
     * ejecucion activos por Sla de ejecucion y sub tipo de ot de la BD
     *
     * @return resulList
     * @throws ApplicationException
     */
    @Override
    public DetalleSlaEjecucionMgl findBySlaEjecucionAndTipoEje(SlaEjecucionMgl slaEjecucionMgl,
            CmtTipoOtMgl tipoOtCCmm, TipoOtHhppMgl tipoOtHhppMgl) throws ApplicationException {
        DetalleSlaEjeMglManager manager = new DetalleSlaEjeMglManager();
        return manager.findBySlaEjecucionAndTipoEje(slaEjecucionMgl, tipoOtCCmm, tipoOtHhppMgl);
    }

    /**
     * Autor: victor bocanegra Metodo para consultar la suma de los sla de un
     * Sla de ejecucion activos
     *
     * @return Integer
     * @throws ApplicationException
     */
    @Override
    public Integer findSumSlaBySlaEje(SlaEjecucionMgl slaEjecucionMgl) throws ApplicationException {

        DetalleSlaEjeMglManager manager = new DetalleSlaEjeMglManager();
        return manager.findSumSlaBySlaEje(slaEjecucionMgl);
    }

    /**
     * Autor: victor bocanegra Metodo para consultar el detalle de un Sla de
     * ejecucion activos por Sla de ejecucion - Estado CCMM o Tipo de ot Hhpp
     *
     * @param slaEjecucionMgl
     * @param estadoCCMM
     * @param tipoOtHhppMgl
     * @return resulList
     * @throws ApplicationException
     */
    @Override
    public DetalleSlaEjecucionMgl findBySlaEjecucionAndEstCcmmAndTipoOt(SlaEjecucionMgl slaEjecucionMgl,
            CmtBasicaMgl estadoCCMM, TipoOtHhppMgl tipoOtHhppMgl) throws ApplicationException {

        DetalleSlaEjeMglManager manager = new DetalleSlaEjeMglManager();
        return manager.findBySlaEjecucionAndEstCcmmAndTipoOt(slaEjecucionMgl, estadoCCMM, tipoOtHhppMgl);
    }

    /**
     * Autor: victor bocanegra Metodo para consultar lista de detalle de un Sla
     * de ejecucion mayor a la secuencia
     *
     * @param detalleSlaEjecucionMgl
     * @param controlConsulta
     * @return int dias faltantes de ejecucion
     * @throws ApplicationException
     */
    @Override
    public List<DetalleSlaEjecucionMgl> findDetalleSlaEjecucionMaySecProcesoList(DetalleSlaEjecucionMgl detalleSlaEjecucionMgl, int controlConsulta)
            throws ApplicationException {
        DetalleSlaEjeMglManager manager = new DetalleSlaEjeMglManager();
        return manager.findDetalleSlaEjecucionMaySecProcesoList(detalleSlaEjecucionMgl, controlConsulta);
    }

    /**
     * Autor: victor bocanegra Metodo para consultar lista de detalle de un Sla
     * de ejecucion por lista de ids
     *
     * @param ids
     * @return List<DetalleSlaEjecucionMgl>n
     * @throws ApplicationException
     */
    @Override
    public List<DetalleSlaEjecucionMgl> findDetalleSlaEjecucionByIds(List<BigDecimal> ids)
            throws ApplicationException {
        DetalleSlaEjeMglManager manager = new DetalleSlaEjeMglManager();
        return manager.findDetalleSlaEjecucionByIds(ids);
    }

    
    @Override
    public List<DetalleSlaEjecucionMgl> findBySlaEjecucionPaginated() throws ApplicationException {
        DetalleSlaEjeMglManager manager = new DetalleSlaEjeMglManager();
        return manager.findBySlaEjecucionPaginated();

    }

}
