/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.DetalleSlaEjeMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DetalleSlaEjecucionMgl;
import co.com.claro.mgl.jpa.entities.SlaEjecucionMgl;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public class DetalleSlaEjeMglManager {

    /**
     * Autor: victor bocanegra Metodo para consultar todos los detalles de un
     * Sla de ejecucion activos de la BD
     *
     * @param slaEjecucionMgl
     * @param conTotal
     * @return List<DetalleSlaEjecucionMgl>
     * @throws ApplicationException
     */
    public List<DetalleSlaEjecucionMgl> findBySlaEjecucion(SlaEjecucionMgl slaEjecucionMgl, boolean conTotal) throws ApplicationException {
        List<DetalleSlaEjecucionMgl> resulList;
        DetalleSlaEjeMglDaoImpl dao = new DetalleSlaEjeMglDaoImpl();
        resulList = dao.findBySlaEjecucion(slaEjecucionMgl);

        if (conTotal) {
            if (resulList != null && !resulList.isEmpty()) {
                int sum = 0;
                for (DetalleSlaEjecucionMgl detalleSlaEjecucionMgl : resulList) {
                    sum = sum + detalleSlaEjecucionMgl.getSla();
                }
                DetalleSlaEjecucionMgl total = new DetalleSlaEjecucionMgl();
                if (slaEjecucionMgl.getTipoEjecucion().equalsIgnoreCase("CCMM")) {
                    CmtTipoOtMgl cmtTipoOtMgl = new CmtTipoOtMgl();
                    cmtTipoOtMgl.setDescTipoOt("TOTAL DIAS");
                    total.setSubTipoOtCCMM(cmtTipoOtMgl);
                    total.setSla(sum);
                } else if (slaEjecucionMgl.getTipoEjecucion().equalsIgnoreCase("UNIDAD")) {
                    TipoOtHhppMgl tipoOtHhppMgl = new TipoOtHhppMgl();
                    tipoOtHhppMgl.setNombreTipoOt("TOTAL DIAS");
                    total.setSubTipoOtUnidad(tipoOtHhppMgl);
                    total.setSla(sum);
                }
                resulList.add(total);
            }
        }

        return resulList;
    }

    /**
     * Autor: Angel Gonzalez Metodo para consultar el detalle de un Sla de forma
     * paginada
     *
     * @return resulList
     * @throws ApplicationException
     */
    public List<DetalleSlaEjecucionMgl> findBySlaEjecucionPaginated() throws ApplicationException {
        List<DetalleSlaEjecucionMgl> resulList;
        DetalleSlaEjeMglDaoImpl dao = new DetalleSlaEjeMglDaoImpl();
        resulList = dao.findBySlaEjecucionPaginated();

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
    public DetalleSlaEjecucionMgl create(DetalleSlaEjecucionMgl detalleSlaEjecucionMgl, String mUser, int mPerfil)
            throws ApplicationException {

        DetalleSlaEjeMglDaoImpl dao = new DetalleSlaEjeMglDaoImpl();
        return dao.createCm(detalleSlaEjecucionMgl, mUser, mPerfil);

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
    public boolean delete(DetalleSlaEjecucionMgl detalleSlaEjecucionMgl, String mUser, int mPerfil)
            throws ApplicationException {

        DetalleSlaEjeMglDaoImpl dao = new DetalleSlaEjeMglDaoImpl();
        return dao.deleteCm(detalleSlaEjecucionMgl, mUser, mPerfil);

    }

    /**
     * Autor: victor bocanegra Metodo para consultar el detalle de un Sla de
     * ejecucion activos por Sla de ejecucion y sub tipo de ot de la BD
     *
     * @return resulList
     * @throws ApplicationException
     */
    public DetalleSlaEjecucionMgl findBySlaEjecucionAndTipoEje(SlaEjecucionMgl slaEjecucionMgl,
            CmtTipoOtMgl tipoOtCCmm, TipoOtHhppMgl tipoOtHhppMgl) throws ApplicationException {

        DetalleSlaEjeMglDaoImpl dao = new DetalleSlaEjeMglDaoImpl();
        return dao.findBySlaEjecucionAndTipoEje(slaEjecucionMgl, tipoOtCCmm, tipoOtHhppMgl);
    }

    /**
     * Autor: victor bocanegra Metodo para consultar la suma de los sla de un
     * Sla de ejecucion activos
     *
     * @return Integer
     * @throws ApplicationException
     */
    public Integer findSumSlaBySlaEje(SlaEjecucionMgl slaEjecucionMgl) throws ApplicationException {

        DetalleSlaEjeMglDaoImpl dao = new DetalleSlaEjeMglDaoImpl();
        return dao.findSumSlaBySlaEje(slaEjecucionMgl);
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
    public DetalleSlaEjecucionMgl findBySlaEjecucionAndEstCcmmAndTipoOt(SlaEjecucionMgl slaEjecucionMgl,
            CmtBasicaMgl estadoCCMM, TipoOtHhppMgl tipoOtHhppMgl) throws ApplicationException {

        DetalleSlaEjeMglDaoImpl dao = new DetalleSlaEjeMglDaoImpl();
        return dao.findBySlaEjecucionAndEstCcmmAndTipoOt(slaEjecucionMgl, estadoCCMM, tipoOtHhppMgl);
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
    public List<DetalleSlaEjecucionMgl> findDetalleSlaEjecucionMaySecProcesoList(DetalleSlaEjecucionMgl detalleSlaEjecucionMgl, int controlConsulta)
            throws ApplicationException {

        DetalleSlaEjeMglDaoImpl dao = new DetalleSlaEjeMglDaoImpl();
        return dao.findDetalleSlaEjecucionMaySecProcesoList(detalleSlaEjecucionMgl, controlConsulta);
    }

    /**
     * Autor: victor bocanegra Metodo para consultar lista de detalle de un Sla
     * de ejecucion por lista de ids
     *
     * @param ids
     * @return List<DetalleSlaEjecucionMgl>n
     * @throws ApplicationException
     */
    public List<DetalleSlaEjecucionMgl> findDetalleSlaEjecucionByIds(List<BigDecimal> ids)
            throws ApplicationException {
        DetalleSlaEjeMglDaoImpl dao = new DetalleSlaEjeMglDaoImpl();
        List<DetalleSlaEjecucionMgl> resulList = dao.findDetalleSlaEjecucionByIds(ids);

        if (resulList != null && !resulList.isEmpty()) {
            int sum = 0;
            SlaEjecucionMgl slaEjecucionMgl = resulList.get(0).getSlaEjecucionMgl();
            for (DetalleSlaEjecucionMgl detalleSlaEjecucionMgl : resulList) {
                sum = sum + detalleSlaEjecucionMgl.getSla();
            }
            DetalleSlaEjecucionMgl total = new DetalleSlaEjecucionMgl();
            if (slaEjecucionMgl != null
                    && slaEjecucionMgl.getTipoEjecucion().equalsIgnoreCase("CCMM")) {
                CmtTipoOtMgl cmtTipoOtMgl = new CmtTipoOtMgl();
                cmtTipoOtMgl.setDescTipoOt("TOTAL DIAS");
                total.setSubTipoOtCCMM(cmtTipoOtMgl);
                total.setSla(sum);
            } else if (slaEjecucionMgl != null
                    && slaEjecucionMgl.getTipoEjecucion().equalsIgnoreCase("UNIDAD")) {
                TipoOtHhppMgl tipoOtHhppMgl = new TipoOtHhppMgl();
                tipoOtHhppMgl.setNombreTipoOt("TOTAL DIAS");
                total.setSubTipoOtUnidad(tipoOtHhppMgl);
                total.setSla(sum);
            }
            resulList.add(total);
        }

        return resulList;
    }
}
