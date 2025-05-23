/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoInternoGaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtEstadoInternoGaMglDaoImpl extends GenericDaoImpl<CmtEstadoInternoGaMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtEstadoInternoGaMglDaoImpl.class);

    public List<CmtEstadoInternoGaMgl> findAll() throws ApplicationException {
        try {
            List<CmtEstadoInternoGaMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtEstadoInternoGaMgl.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<CmtEstadoInternoGaMgl>) query.getResultList();
            return resultList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    public List<CmtEstadoInternoGaMgl> findByCodigo(String codigo) throws ApplicationException {
        try {
            List<CmtEstadoInternoGaMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtEstadoInternoGaMgl.findByCodigo");
            query.setParameter("codigo", codigo);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<CmtEstadoInternoGaMgl>) query.getResultList();
            return resultList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    public List<CmtEstadoInternoGaMgl> findByFiltro(CmtBasicaMgl estadoIniCmFiltro,
            CmtBasicaMgl estadoExternoAntGaFiltro,
            CmtBasicaMgl estadoInternoGaFiltro,
            CmtBasicaMgl estadoExternoPostGaFiltro,
            CmtBasicaMgl estadoFinalCmFiltro,
            CmtBasicaMgl tipoGaFiltro) throws ApplicationException {
        try {
            List<CmtEstadoInternoGaMgl> resultList;
            String queryStr = "SELECT c FROM CmtEstadoInternoGaMgl c WHERE c.estadoRegistro=1 ";

            if (estadoIniCmFiltro != null && estadoIniCmFiltro.getBasicaId() != null) {
                queryStr += " AND";
                queryStr += " c.estadoInicialCM = :estadoInicialCM";
            }

            if (estadoExternoAntGaFiltro != null && estadoExternoAntGaFiltro.getBasicaId() != null) {
                queryStr += " AND";
                queryStr += " c.estadoExternoAnteriorGA = :estadoExternoAnteriorGA";
            }

            if (estadoInternoGaFiltro != null && estadoInternoGaFiltro.getBasicaId() != null) {
                queryStr += " AND";
                queryStr += " c.estadoInternoGA = :estadoInternoGA";
            }

            if (estadoExternoPostGaFiltro != null && estadoExternoPostGaFiltro.getBasicaId() != null) {
                queryStr += " AND";
                queryStr += " c.estadoExternoPosteriorGA = :estadoExternoPosteriorGA";
            }

            if (estadoFinalCmFiltro != null && estadoFinalCmFiltro.getBasicaId() != null) {
                queryStr += " AND";
                queryStr += " c.estadoFinalCM = :estadoFinalCM";
            }

            if (tipoGaFiltro != null && tipoGaFiltro.getBasicaId() != null) {
                queryStr += " AND";
                queryStr += " c.tipoGaObj = :tipoGaObj";
            }
            
            
            Query query = entityManager.createQuery(queryStr);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            if (estadoIniCmFiltro != null && estadoIniCmFiltro.getBasicaId() != null) {
                query.setParameter("estadoInicialCM", estadoIniCmFiltro);
            }
            if (estadoExternoAntGaFiltro != null && estadoExternoAntGaFiltro.getBasicaId() != null) {
                query.setParameter("estadoExternoAnteriorGA", estadoExternoAntGaFiltro);
            }
            if (estadoInternoGaFiltro != null && estadoInternoGaFiltro.getBasicaId() != null) {
                query.setParameter("estadoInternoGA", estadoInternoGaFiltro);
            }
            if (estadoExternoPostGaFiltro != null && estadoExternoPostGaFiltro.getBasicaId() != null) {
                query.setParameter("estadoExternoPosteriorGA", estadoExternoPostGaFiltro);
            }
            if (estadoFinalCmFiltro != null && estadoFinalCmFiltro.getBasicaId() != null) {
                query.setParameter("estadoFinalCM", estadoFinalCmFiltro);
            }
            if (tipoGaFiltro != null && tipoGaFiltro.getBasicaId() != null) {
                query.setParameter("tipoGaObj", tipoGaFiltro);
            }

            resultList = (List<CmtEstadoInternoGaMgl>) query.getResultList();
            return resultList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
}
