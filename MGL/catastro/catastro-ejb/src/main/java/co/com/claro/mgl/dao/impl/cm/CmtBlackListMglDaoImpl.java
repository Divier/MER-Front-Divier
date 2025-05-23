/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBlackListMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import java.util.List;
import javax.persistence.Query;

/**
 * Dao CmtBlackListMgl. Contiene la logica de acceso a datos de los BlackList en
 * el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtBlackListMglDaoImpl extends GenericDaoImpl<CmtBlackListMgl> {

    /**
     * Busca todos los BlackList. Permite realizar la busqueda de todos los
     * BlackList.
     *
     * @author Johnnatan Ortiz
     * @return Todos BlackList
     * @throws ApplicationException
     */
    public List<CmtBlackListMgl> findAll() throws ApplicationException {
        List<CmtBlackListMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtBlackListMgl.findAll");
        resultList = (List<CmtBlackListMgl>) query.getResultList();
        return resultList;
    }

    /**
     * Busca los BlackList por SubEdificio.Permite realizar la busqueda de los
 BlackList de un mismo SubEdificio.
     *
     * @author Johnnatan Ortiz
     * @param firstResult
     * @param maxResults maximo numero de resultados
     * @param subEdificio SubEdificio
     * @return BlackList asociados a un SubEdifico
     * @throws ApplicationException
     */
    public List<CmtBlackListMgl> findBySubEdificioPaginado(int firstResult,
            int maxResults, CmtSubEdificioMgl subEdificio) throws ApplicationException {
        List<CmtBlackListMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtBlackListMgl.findBySubEdificio");
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        query.setParameter("subEdificio", subEdificio);
        resultList = (List<CmtBlackListMgl>) query.getResultList();
        return resultList;
    }

    /**
     * Cuenta los BlackList por SubEdificio. Permite realizar el conteo de los
     * BlackList de un mismo SubEdificio.
     *
     * @author Johnnatan Ortiz
     * @param subEdificio SubEdificio
     * @return numero de BlackList asociados a un SubEdifico
     * @throws ApplicationException
     */
    public int getCountBySubEdificio(CmtSubEdificioMgl subEdificio) throws ApplicationException {
        int result = 0;
        Query query = entityManager.createNamedQuery("CmtBlackListMgl.getCountBySubEdificio");
        query.setParameter("subEdificio", subEdificio);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        result = query.getSingleResult() == null ? 0 : ((Long) query.getSingleResult()).intValue();
        return result;
    }
    
      public List<CmtBlackListMgl> findBySubEdificio(CmtSubEdificioMgl subEdificio) throws ApplicationException {
        List<CmtBlackListMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtBlackListMgl.findBySubEdificioActivo");
        query.setParameter("subEdificio", subEdificio);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtBlackListMgl>) query.getResultList();
        return resultList;
    }
}
