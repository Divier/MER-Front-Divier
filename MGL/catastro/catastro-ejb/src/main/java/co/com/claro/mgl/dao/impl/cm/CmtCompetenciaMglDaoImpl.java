/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtCompetenciaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 * Dao Competencia. Contiene la logica de acceso a datos competencias en el
 * repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtCompetenciaMglDaoImpl extends GenericDaoImpl<CmtCompetenciaMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtCompetenciaMglDaoImpl.class);

    public List<CmtCompetenciaMgl> findAll() throws ApplicationException {
        List<CmtCompetenciaMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtCompetenciaMgl.findAll");
        resultList = (List<CmtCompetenciaMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtCompetenciaMgl> findCompetenciaId(BigDecimal subEdificiosId) throws ApplicationException {
        List<CmtCompetenciaMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM CmtCompetenciaMgl c WHERE c.subEdificiosIdd = :subEdificiosId ORDER BY c.basicaId ASC");
        query.setParameter("subEdificiosId", subEdificiosId);
        resultList = (List<CmtCompetenciaMgl>) query.getResultList();
        LOGGER.error("resultList " + resultList);
        return resultList;
    }

    /**
     * Busca las Competencias por SubEdificio. Permite realizar la busqueda de
     * las Compentencias de un mismo SubEdificio.
     *
     * @author Johnnatan Ortiz
     * @param subEdificio SubEdificio
     * @return Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    public List<CmtCompetenciaMgl> findBySubEdificio(CmtSubEdificioMgl subEdificio) throws ApplicationException {
        List<CmtCompetenciaMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtCompetenciaMgl.findBySubEdificio");
        query.setParameter("subEdificio", subEdificio);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtCompetenciaMgl>) query.getResultList();
        return resultList;
    }

    /**
     * Cuenta las Competencias por SubEdificio. Permite realizar el conteo de
     * las Compentencias de un mismo SubEdificio.
     *
     * @author Johnnatan Ortiz
     * @param subEdificio SubEdificio
     * @return numero de Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    public int getCountBySubEdificio(CmtSubEdificioMgl subEdificio) throws ApplicationException {
        int result = 0;
        Query query = entityManager.createNamedQuery("CmtCompetenciaMgl.getCountBySubEdificio");
        query.setParameter("subEdificio", subEdificio);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        result = query.getSingleResult() == null ? 0 : ((Long) query.getSingleResult()).intValue();
        return result;
    }

    /**
     * Busca las Competencias por SubEdificio.Permite realizar la busqueda de
 las Compentencias de un mismo SubEdificio.
     *
     * @author Johnnatan Ortiz
     * @param firstResult
     * @param maxResults maximo numero de resultados
     * @param subEdificio SubEdificio
     * @return Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    public List<CmtCompetenciaMgl> findBySubEdificioPaginado(int firstResult,
            int maxResults, CmtSubEdificioMgl subEdificio) throws ApplicationException {
        List<CmtCompetenciaMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtCompetenciaMgl.findBySubEdificio");
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        query.setParameter("subEdificio", subEdificio);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtCompetenciaMgl>) query.getResultList();
        return resultList;
    }
}