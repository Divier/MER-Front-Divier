/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.businessmanager.address.EstadoHhppMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTipoBasicaMglManager;
import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaViabilidadHhppDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.EstadoHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtConvergenciaViabilidadHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author User
 */
public class CmtConvergenciaViabilidadHhppMglDaoImpl extends GenericDaoImpl<CmtConvergenciaViabilidadHhppMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtConvergenciaViabilidadHhppMglDaoImpl.class);

    public List<CmtConvergenciaViabilidadHhppMgl> findAllActivos() throws ApplicationException {
        List<CmtConvergenciaViabilidadHhppMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtConvergenciaViabilidadHhppMgl.findAllActivos");
        resultList = (List<CmtConvergenciaViabilidadHhppMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtConvergenciaViabilidadHhppMgl> findAllConEliminados() throws ApplicationException {
        List<CmtConvergenciaViabilidadHhppMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtConvergenciaViabilidadHhppMgl.findAllConEliminados");
        resultList = (List<CmtConvergenciaViabilidadHhppMgl>) query.getResultList();
        return resultList;
    }

    public CmtConvergenciaViabilidadHhppMgl findByRegla(CmtConvergenciaViabilidadHhppMgl viabilidadHhppMgl)
            throws ApplicationException {
        CmtConvergenciaViabilidadHhppMgl result;
        try {
            Query query = entityManager.createNamedQuery("CmtConvergenciaViabilidadHhppMgl.findByRegla");
            query.setParameter("estadoNodo", viabilidadHhppMgl.getEstadoNodo());
            query.setParameter("estadoHhpp", viabilidadHhppMgl.getEstadoHhpp());
            query.setParameter("estadoCuentamatriz", viabilidadHhppMgl.getEstadoCuentamatriz());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");            
            result = (CmtConvergenciaViabilidadHhppMgl) query.getSingleResult();
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            result = null;
        }
        return result;
    }
    
     public List<CmtConvergenciaViabilidadHhppMgl> findAll() throws ApplicationException {
        List<CmtConvergenciaViabilidadHhppMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtConvergenciaViabilidadHhppMgl.findAll");
        resultList = (List<CmtConvergenciaViabilidadHhppMgl>) query.getResultList();
        return resultList;
    }
     
     /**
     * Busca las Viabilidades de HHPP.Permite realizar la busqueda de
 las Viabilidades de HHPP paginando el resultado.
     *
     * @author Laura Carolina Muñoz - HITSS
     * @param filtro para la consulta
     * @param firstResult
     * @param maxResults maximo numero de resultados
     * @return Viabilidades de HHPP
     * @throws ApplicationException
     */
    public List<CmtConvergenciaViabilidadHhppMgl> findViabilidadHhppPaginacion(FiltroConsultaViabilidadHhppDto filtro,
            int firstResult, int maxResults) throws ApplicationException {
        List<CmtConvergenciaViabilidadHhppMgl> resultList;

        String queryStr = "SELECT DISTINCT c FROM CmtConvergenciaViabilidadHhppMgl c WHERE 1=1";

        if (filtro.getIdViaSeleccionada() != null
                && !filtro.getIdViaSeleccionada().equals(BigDecimal.ZERO)) {
            queryStr += " AND c.reglaId = :reglaId ";
        }

        if (filtro.getEstadoViaSeleccionada() != null && !filtro.getEstadoViaSeleccionada().isEmpty()) {
            queryStr += " AND c.estadoRegistro = :estadoRegistro ";
        }

        if (filtro.getEstadoNodoSeleccionada() != null && !filtro.getEstadoNodoSeleccionada().isEmpty()) {
            queryStr += " AND c.estadoNodo = :estadoNodo  ";
        }

        if (filtro.getEstadoCcmmSeleccionada() != null && !filtro.getEstadoCcmmSeleccionada().isEmpty()) {
            queryStr += " AND UPPER(c.estadoCuentamatriz) LIKE :estadoCuentamatriz ";
        }

        if (filtro.getEstadoHhppSeleccionada() != null && !filtro.getEstadoHhppSeleccionada().isEmpty()) {
            queryStr += " AND c.estadoHhpp = :estadoHhpp  ";
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

        if (filtro.getEstadoNodoSeleccionada() != null && !filtro.getEstadoNodoSeleccionada().isEmpty()) {
            String est = filtro.getEstadoNodoSeleccionada().trim().toUpperCase();
            String e;
            if (est.equalsIgnoreCase("ACTIVO")) {
                e = "A";
            } else if (est.equalsIgnoreCase("INACTIVO")) {
                e = "I";
            } else {
                e = "P";
            }
            query.setParameter("estadoNodo", e);
        }

        if (filtro.getEstadoCcmmSeleccionada() != null && !filtro.getEstadoCcmmSeleccionada().isEmpty()) {
            String codigo = "";
            CmtTipoBasicaMglManager manager = new CmtTipoBasicaMglManager();
            CmtTipoBasicaMgl cmtTipoBasicaMgl = manager.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);

            CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();

            CmtBasicaMgl basicaMgl = basicaMglManager.findByTipoBasicaAndNombre(cmtTipoBasicaMgl, filtro.getEstadoCcmmSeleccionada());
            if (basicaMgl != null && basicaMgl.getBasicaId() != null) {
                codigo = StringUtils.leftPad(basicaMgl.getCodigoBasica(), 4, "0");
            }
            query.setParameter("estadoCuentamatriz", codigo);
        }

        if (filtro.getEstadoHhppSeleccionada() != null && !filtro.getEstadoHhppSeleccionada().isEmpty()) {
            String estHhpp = filtro.getEstadoHhppSeleccionada().trim().toUpperCase();
            EstadoHhppMglManager estadoHhppMglManager = new EstadoHhppMglManager();
            EstadoHhppMgl estadoHhppMgl = estadoHhppMglManager.findByNameEstHhpp(estHhpp);
            if (estadoHhppMgl != null && estadoHhppMgl.getEhhID() != null) {
                query.setParameter("estadoHhpp", estadoHhppMgl.getEhhID());
            } else {
                query.setParameter("estadoHhpp", estHhpp);
            }
        }

        if (filtro.getViableSeleccionada() != null && !filtro.getViableSeleccionada().isEmpty()) {
            query.setParameter("viable", "%" + filtro.getViableSeleccionada().trim().toUpperCase() + "%");
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtConvergenciaViabilidadHhppMgl>) query.getResultList();
        return resultList;
    }
    
    /**
     * Cuenta las las Viabilidades de HHPP. Permite realizar el conteo de
     * las Viabilidades de HHPP.
     *
     * @author Laura Carolina Muñoz
     * @param filtro
     * @return numero de Viabilidades de HHPP
     * @throws ApplicationException
     */
    public int getCountFindByAll(FiltroConsultaViabilidadHhppDto filtro) throws ApplicationException {

        int result;
        String queryStr = "SELECT COUNT(DISTINCT c) FROM CmtConvergenciaViabilidadHhppMgl c WHERE 1=1";

        if (filtro.getIdViaSeleccionada() != null
                && !filtro.getIdViaSeleccionada().equals(BigDecimal.ZERO)) {
            queryStr += " AND c.reglaId = :reglaId ";
        }

        if (filtro.getEstadoViaSeleccionada() != null && !filtro.getEstadoViaSeleccionada().isEmpty()) {
            queryStr += " AND c.estadoRegistro = :estadoRegistro ";
        }

        if (filtro.getEstadoNodoSeleccionada() != null && !filtro.getEstadoNodoSeleccionada().isEmpty()) {
            queryStr += " AND c.estadoNodo = :estadoNodo  ";
        }

        if (filtro.getEstadoCcmmSeleccionada() != null && !filtro.getEstadoCcmmSeleccionada().isEmpty()) {
            queryStr += " AND UPPER(c.estadoCuentamatriz) LIKE :estadoCuentamatriz ";
        }

        if (filtro.getEstadoHhppSeleccionada() != null && !filtro.getEstadoHhppSeleccionada().isEmpty()) {
            queryStr += " AND c.estadoHhpp = :estadoHhpp  ";
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

        if (filtro.getEstadoNodoSeleccionada() != null && !filtro.getEstadoNodoSeleccionada().isEmpty()) {
            String est = filtro.getEstadoNodoSeleccionada().trim().toUpperCase();
            String e;
            if (est.equalsIgnoreCase("ACTIVO")) {
                e = "A";
            } else if (est.equalsIgnoreCase("INACTIVO")) {
                e = "I";
            } else {
                e = "P";
            }
            query.setParameter("estadoNodo", e);
        }

        if (filtro.getEstadoCcmmSeleccionada() != null && !filtro.getEstadoCcmmSeleccionada().isEmpty()) {
            String codigo = "";
            CmtTipoBasicaMglManager manager = new CmtTipoBasicaMglManager();
            CmtTipoBasicaMgl cmtTipoBasicaMgl = manager.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);

            CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();

            CmtBasicaMgl basicaMgl = basicaMglManager.findByTipoBasicaAndNombre(cmtTipoBasicaMgl, filtro.getEstadoCcmmSeleccionada());
            if (basicaMgl != null && basicaMgl.getBasicaId() != null) {
                codigo = StringUtils.leftPad(basicaMgl.getCodigoBasica(), 4, "0");
            }
            query.setParameter("estadoCuentamatriz", codigo);
        }

        if (filtro.getEstadoHhppSeleccionada() != null && !filtro.getEstadoHhppSeleccionada().isEmpty()) {
            String estHhpp = filtro.getEstadoHhppSeleccionada().trim().toUpperCase();
            EstadoHhppMglManager estadoHhppMglManager = new EstadoHhppMglManager();
            EstadoHhppMgl estadoHhppMgl = estadoHhppMglManager.findByNameEstHhpp(estHhpp);
            if (estadoHhppMgl != null && estadoHhppMgl.getEhhID() != null) {
                query.setParameter("estadoHhpp", estadoHhppMgl.getEhhID());
            } else {
                query.setParameter("estadoHhpp", estHhpp);
            }
        }

        if (filtro.getViableSeleccionada() != null && !filtro.getViableSeleccionada().isEmpty()) {
            query.setParameter("viable", "%" + filtro.getViableSeleccionada().trim().toUpperCase() + "%");
        }

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        result = query.getSingleResult() == null ? 0 : ((Long) query.getSingleResult()).intValue();
        return result;
    }
}
