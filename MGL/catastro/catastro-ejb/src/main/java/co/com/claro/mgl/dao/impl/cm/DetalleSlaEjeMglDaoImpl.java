/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DetalleSlaEjecucionMgl;
import co.com.claro.mgl.jpa.entities.SlaEjecucionMgl;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author bocanegravm
 */
public class DetalleSlaEjeMglDaoImpl extends GenericDaoImpl<DetalleSlaEjecucionMgl> {

    /**
     * Autor: victor bocanegra Metodo para consultar el detalle de un Sla de
     * ejecucion activos de la BD
     *
     * @param slaEjecucionMgl
     * @return resulList
     * @throws ApplicationException
     */
    public List<DetalleSlaEjecucionMgl> findBySlaEjecucion(SlaEjecucionMgl slaEjecucionMgl) throws ApplicationException {
        List<DetalleSlaEjecucionMgl> resultList;
        Query query = entityManager.createNamedQuery("DetalleSlaEjecucionMgl.findBySlaEjecucion");
        if (slaEjecucionMgl != null) {
            query.setParameter("slaEjecucionMgl", slaEjecucionMgl);
        }
        resultList = query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    /**
     * Autor: Angel Gonzalez Metodo para consultar el detalle de un Sla de forma
     * paginada
     *
     * @return resulList
     * @throws ApplicationException
     */
    public List<DetalleSlaEjecucionMgl> findBySlaEjecucionPaginated() throws ApplicationException {
        List<DetalleSlaEjecucionMgl> resultList;
        Query query = entityManager.createNamedQuery("DetalleSlaEjecucionMgl.findBySlaEjecucionPaginated");
        resultList = query.getResultList();
        getEntityManager().clear();
        return resultList;

    }

    /**
     * Autor: victor bocanegra Metodo para consultar el detalle de un Sla de
     * ejecucion activos por Sla de ejecucion y sub tipo de ot de la BD
     *
     * @return DetalleSlaEjecucionMgl
     * @throws ApplicationException
     */
    public DetalleSlaEjecucionMgl findBySlaEjecucionAndTipoEje(SlaEjecucionMgl slaEjecucionMgl,
            CmtTipoOtMgl tipoOtCCmm, TipoOtHhppMgl tipoOtHhppMgl) throws ApplicationException {

        try {
            String consulta = "SELECT d FROM DetalleSlaEjecucionMgl d "
                    + " WHERE d.estadoRegistro=1 AND d.slaEjecucionMgl = :slaEjecucionMgl ";

            if (tipoOtCCmm != null) {
                consulta += " AND d.subTipoOtCCMM = :subTipoOtCCMM";
            }

            if (tipoOtHhppMgl != null) {
                consulta += " AND d.subTipoOtUnidad = :subTipoOtUnidad";
            }

            Query query = entityManager.createQuery(consulta);

            if (slaEjecucionMgl != null) {
                query.setParameter("slaEjecucionMgl", slaEjecucionMgl);
            }
            if (tipoOtCCmm != null) {
                query.setParameter("subTipoOtCCMM", tipoOtCCmm);
            }

            if (tipoOtHhppMgl != null) {
                query.setParameter("subTipoOtUnidad", tipoOtHhppMgl);
            }
            return (DetalleSlaEjecucionMgl) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }

    }

    /**
     * Autor: victor bocanegra Metodo para consultar la suma de los sla de un
     * Sla de ejecucion activos
     *
     * @return Integer
     * @throws ApplicationException
     */
    public Integer findSumSlaBySlaEje(SlaEjecucionMgl slaEjecucionMgl) throws ApplicationException {

        String consulta = "SELECT SUM(d.sla) as suma FROM DetalleSlaEjecucionMgl d "
                + " WHERE d.estadoRegistro=1 AND d.slaEjecucionMgl = :slaEjecucionMgl ";

        Query query = entityManager.createQuery(consulta);

        if (slaEjecucionMgl != null) {
            query.setParameter("slaEjecucionMgl", slaEjecucionMgl);
        }
        Long valor = (Long) query.getSingleResult();

        if (valor != null) {
            return valor.intValue();
        } else {
            return 0;
        }

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

        DetalleSlaEjecucionMgl detalleSlaEjecucionMgl = null;

        try {
            String consulta = "SELECT d FROM DetalleSlaEjecucionMgl d "
                    + " WHERE d.estadoRegistro=1 AND d.slaEjecucionMgl = :slaEjecucionMgl ";

            if (estadoCCMM != null) {
                consulta += " AND d.estadoCcmm = :estadoCcmm";
            }

            if (tipoOtHhppMgl != null) {
                consulta += " AND d.subTipoOtUnidad = :subTipoOtUnidad";
            }

            Query query = entityManager.createQuery(consulta);

            if (slaEjecucionMgl != null) {
                query.setParameter("slaEjecucionMgl", slaEjecucionMgl);
            }
            if (estadoCCMM != null) {
                query.setParameter("estadoCcmm", estadoCCMM);
            }
            if (tipoOtHhppMgl != null) {
                query.setParameter("subTipoOtUnidad", tipoOtHhppMgl);
            }

            List<DetalleSlaEjecucionMgl> list = query.getResultList();

            if (list != null && !list.isEmpty()) {
                detalleSlaEjecucionMgl = list.get(0);
            }
            return detalleSlaEjecucionMgl;

        } catch (NoResultException ex) {
            return null;
        }

    }

    /**
     * Autor: victor bocanegra Metodo para consultar lista de detalle de un Sla
     * de ejecucion mayor a la secuencia
     *
     * @param detalleSlaEjecucionMgl
     * @param controlConsulta
     * @return List<DetalleSlaEjecucionMgl>
     * @throws ApplicationException
     */
    public List<DetalleSlaEjecucionMgl> findDetalleSlaEjecucionMaySecProcesoList(DetalleSlaEjecucionMgl detalleSlaEjecucionMgl, int controlConsulta)
            throws ApplicationException {

        String consulta = null;
        try {
            switch (controlConsulta) {
                case 1:
                case 0:
                    //Abierta o estado CCMM
                    consulta = "SELECT d FROM DetalleSlaEjecucionMgl d "
                            + " WHERE d.estadoRegistro=1 AND d.slaEjecucionMgl = :slaEjecucionMgl "
                            + " AND d.seqProceso >= :seqProceso ";
                    break;
                case 2:
                    //Cerrada
                    consulta = "SELECT d FROM DetalleSlaEjecucionMgl d "
                            + " WHERE d.estadoRegistro=1 AND d.slaEjecucionMgl = :slaEjecucionMgl "
                            + " AND d.seqProceso > :seqProceso ";
                    break;
                case 3:
                    //Anulada
                    consulta = "SELECT d FROM DetalleSlaEjecucionMgl d "
                            + " WHERE d.estadoRegistro=1 AND d.slaEjecucionMgl = :slaEjecucionMgl ";
                    break;
                default:
                    break;
            }

            Query query = entityManager.createQuery(consulta);

            if (detalleSlaEjecucionMgl != null) {
                query.setParameter("slaEjecucionMgl", detalleSlaEjecucionMgl.getSlaEjecucionMgl());
                if (controlConsulta != 3) {
                    query.setParameter("seqProceso", detalleSlaEjecucionMgl.getSeqProceso());
                }
            }

            List<DetalleSlaEjecucionMgl> resulList = query.getResultList();
            getEntityManager().clear();
            return resulList;

        } catch (NoResultException ex) {
            return null;
        }
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

        String consulta;
        try {

            consulta = "SELECT d FROM DetalleSlaEjecucionMgl d "
                    + " WHERE d.estadoRegistro=1 AND d.detSlaEjecucionId IN :ids "
                    + " ORDER BY d.seqProceso ASC";

            Query query = entityManager.createQuery(consulta);

            if (ids.size() > 0) {
                query.setParameter("ids", ids);
            }

            List<DetalleSlaEjecucionMgl> resulList = query.getResultList();
            getEntityManager().clear();
            return resulList;

        } catch (NoResultException ex) {
            return null;
        }
    }
}
