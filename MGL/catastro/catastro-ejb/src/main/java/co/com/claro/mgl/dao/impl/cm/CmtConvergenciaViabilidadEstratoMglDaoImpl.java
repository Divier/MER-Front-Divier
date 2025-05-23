/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaViabilidadSegmentoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtConvergenciaViabilidadEstratoMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtConvergenciaViabilidadEstratoMglDaoImpl extends GenericDaoImpl<CmtConvergenciaViabilidadEstratoMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtConvergenciaViabilidadEstratoMglDaoImpl.class);

    public List<CmtConvergenciaViabilidadEstratoMgl> findAllActivos() throws ApplicationException {
        List<CmtConvergenciaViabilidadEstratoMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtConvergenciaViabilidadEstratoMgl.findAllActivos");
        resultList = (List<CmtConvergenciaViabilidadEstratoMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtConvergenciaViabilidadEstratoMgl> findAllConEliminados() throws ApplicationException {
        List<CmtConvergenciaViabilidadEstratoMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtConvergenciaViabilidadEstratoMgl.findAllConEliminados");
        resultList = (List<CmtConvergenciaViabilidadEstratoMgl>) query.getResultList();
        return resultList;
    }

    public CmtConvergenciaViabilidadEstratoMgl findByRegla(CmtConvergenciaViabilidadEstratoMgl viabilidadEstratoMgl)
            throws ApplicationException {
        CmtConvergenciaViabilidadEstratoMgl result;
        try {
            Query query = entityManager.createNamedQuery("CmtConvergenciaViabilidadEstratoMgl.findByRegla");
            query.setParameter("segmento", viabilidadEstratoMgl.getSegmento());
            query.setParameter("estrato", viabilidadEstratoMgl.getEstrato());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = (CmtConvergenciaViabilidadEstratoMgl) query.getSingleResult();
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            result = null;
        }
        return result;
    }
    
    public List<CmtConvergenciaViabilidadEstratoMgl> findAll() throws ApplicationException {
        List<CmtConvergenciaViabilidadEstratoMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtConvergenciaViabilidadEstratoMgl.findAll");
        resultList = (List<CmtConvergenciaViabilidadEstratoMgl>) query.getResultList();
        return resultList;
    }
     
     /**
     * Busca las Viabilidades de Segmento Estratos.Permite realizar la busqueda de
 las Viabilidades de Segmento Estratos paginando el resultado.
     *
     * @author Laura Carolina Muñoz - HITSS
     * @param filtro para la consulta
     * @param firstResult
     * @param maxResults maximo numero de resultados
     * @return Viabilidades de Segmento Estratos
     * @throws ApplicationException
     */
    public List<CmtConvergenciaViabilidadEstratoMgl> findViabilidadSegEstratoPaginacion(FiltroConsultaViabilidadSegmentoDto filtro,
            int firstResult, int maxResults) throws ApplicationException {
        List<CmtConvergenciaViabilidadEstratoMgl> resultList;

        String queryStr = "SELECT c FROM CmtConvergenciaViabilidadEstratoMgl c  WHERE 1=1";

        if (filtro.getIdViaSeleccionada() != null
                && !filtro.getIdViaSeleccionada().equals(BigDecimal.ZERO)) {
            queryStr += " AND c.reglaId = :reglaId ";
        }

        if (filtro.getEstadoViaSeleccionada() != null && !filtro.getEstadoViaSeleccionada().isEmpty()) {
            queryStr += " AND c.estadoRegistro = :estadoRegistro ";
        }

        if (filtro.getSegmentoSeleccionado() != null && !filtro.getSegmentoSeleccionado().isEmpty()) {
            queryStr += " AND UPPER(c.segmento) LIKE :segmento ";
        }

        if (filtro.getEstratoSeleccionado() != null && !filtro.getEstratoSeleccionado().isEmpty()) {
            queryStr += " AND UPPER(c.estrato) LIKE :estrato ";
        }

        if (filtro.getViableSeleccionada() != null && !filtro.getViableSeleccionada().isEmpty()) {
            queryStr += " AND UPPER(c.viable) LIKE :viable ";
        }

        Query query = entityManager.createQuery(queryStr);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);

        if (filtro.getIdViaSeleccionada() != null
                && !filtro.getIdViaSeleccionada().equals(BigDecimal.ZERO)) {
            query.setParameter("reglaId", filtro.getIdViaSeleccionada());
        }

        if (filtro.getEstadoViaSeleccionada() != null && !filtro.getEstadoViaSeleccionada().isEmpty()) {
            String est = filtro.getEstadoViaSeleccionada().trim().toUpperCase();
            int e;
            if (est.equalsIgnoreCase("ACTIVO")) {
                e = 1;
            } else if (est.equalsIgnoreCase("INACTIVO")) {
                e = 0;
            } else {
                e = 3;
            }
            query.setParameter("estadoRegistro", e);
        }

        if (filtro.getSegmentoSeleccionado() != null && !filtro.getSegmentoSeleccionado().isEmpty()) {
            query.setParameter("segmento", "%" + filtro.getSegmentoSeleccionado().trim().toUpperCase() + "%");
        }

        if (filtro.getEstratoSeleccionado() != null && !filtro.getEstratoSeleccionado().isEmpty()) {
            query.setParameter("estrato", "%" + filtro.getEstratoSeleccionado().trim().toUpperCase() + "%");
        }

        if (filtro.getViableSeleccionada() != null && !filtro.getViableSeleccionada().isEmpty()) {
            query.setParameter("viable", "%" + filtro.getViableSeleccionada().trim().toUpperCase() + "%");
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");

        resultList = (List<CmtConvergenciaViabilidadEstratoMgl>) query.getResultList();
        return resultList;
    }

     /**
     * Cuenta las las Viabilidades de Segmento Estratos. Permite realizar el conteo de
     * las Viabilidades de Segmento Estratos.
     *
     * @param filtro para la consulta
     * @author Laura Carolina Muñoz
     * @return numero de Viabilidades de Segmento Estratos
     * @throws ApplicationException
     */
    public int getCountFindByAll(FiltroConsultaViabilidadSegmentoDto filtro) throws ApplicationException {
        int result;
        
         String queryStr = "SELECT COUNT(DISTINCT c) FROM CmtConvergenciaViabilidadEstratoMgl c WHERE 1=1";

        if (filtro.getIdViaSeleccionada() != null
                && !filtro.getIdViaSeleccionada().equals(BigDecimal.ZERO)) {
            queryStr += " AND c.reglaId = :reglaId ";
        }

        if (filtro.getEstadoViaSeleccionada() != null && !filtro.getEstadoViaSeleccionada().isEmpty()) {
            queryStr += " AND c.estadoRegistro = :estadoRegistro ";
        }

        if (filtro.getSegmentoSeleccionado() != null && !filtro.getSegmentoSeleccionado().isEmpty()) {
            queryStr += " AND UPPER(c.segmento) LIKE :segmento ";
        }

        if (filtro.getEstratoSeleccionado() != null && !filtro.getEstratoSeleccionado().isEmpty()) {
            queryStr += " AND UPPER(c.estrato) LIKE :estrato ";
        }

        if (filtro.getViableSeleccionada() != null && !filtro.getViableSeleccionada().isEmpty()) {
            queryStr += " AND UPPER(c.viable) LIKE :viable ";
        }

        Query query = entityManager.createQuery(queryStr);

        if (filtro.getIdViaSeleccionada() != null
                && !filtro.getIdViaSeleccionada().equals(BigDecimal.ZERO)) {
            query.setParameter("reglaId", filtro.getIdViaSeleccionada());
        }

        if (filtro.getEstadoViaSeleccionada() != null && !filtro.getEstadoViaSeleccionada().isEmpty()) {
            String est = filtro.getEstadoViaSeleccionada().trim().toUpperCase();
            int e;
            if (est.equalsIgnoreCase("ACTIVO")) {
                e = 1;
            } else if (est.equalsIgnoreCase("INACTIVO")) {
                e = 0;
            } else {
                e = 3;
            }
            query.setParameter("estadoRegistro", e);
        }

        if (filtro.getSegmentoSeleccionado() != null && !filtro.getSegmentoSeleccionado().isEmpty()) {
            query.setParameter("segmento", "%" + filtro.getSegmentoSeleccionado().trim().toUpperCase() + "%");
        }

        if (filtro.getEstratoSeleccionado() != null && !filtro.getEstratoSeleccionado().isEmpty()) {
            query.setParameter("estrato", "%" + filtro.getEstratoSeleccionado().trim().toUpperCase() + "%");
        }

        if (filtro.getViableSeleccionada() != null && !filtro.getViableSeleccionada().isEmpty()) {
            query.setParameter("viable", "%" + filtro.getViableSeleccionada().trim().toUpperCase() + "%");
        }
        
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        result = query.getSingleResult() == null ? 0 : ((Long) query.getSingleResult()).intValue();
        return result;
    }
}