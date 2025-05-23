/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.FiltroNotasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtNotasMglDaoImpl extends GenericDaoImpl<CmtNotasMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtCuentaMatrizMglDaoImpl.class);

    public List<CmtNotasMgl> findAll() throws ApplicationException {
        List<CmtNotasMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtNotasMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasMgl> findTipoNotasId(BigDecimal tipoNotasId) throws ApplicationException {
        List<CmtNotasMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM CmtNotasMgl c WHERE c.basicaId = :tipoNotasId ");
        query.setParameter("tipoNotasId", tipoNotasId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasMgl> findNotasBySubEdificioId(BigDecimal subEdificioId, BigDecimal tipoNota) throws ApplicationException {
        List<CmtNotasMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM CmtNotasMgl c WHERE c.subEdificioObj.subEdificioId = :subedificioId "
                + " AND c.estadoRegistro=1 and (:tipoNota IS NULL or c.tipoNotaObj.basicaId = :tipoNota) order by c.notasId asc");
        query.setParameter("subedificioId", subEdificioId);
        query.setParameter("tipoNota", tipoNota);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasMgl>) query.getResultList();
        return resultList;
    }

    public FiltroNotasDto findNotasSearch(HashMap<String, Object> params, BigDecimal subEdificio,boolean contar, int firstResult, int maxResults) throws ApplicationException {
        FiltroNotasDto filtroNotasDto = new FiltroNotasDto();
        if (contar) {
            filtroNotasDto.setNumRegistros(findNotasSearchContar(params,subEdificio));
        } else {
            filtroNotasDto.setListaNotas(findNotasSearchDatos(params, subEdificio,firstResult, maxResults));
        }
        return filtroNotasDto;
    }

    public long findNotasSearchContar(HashMap<String, Object> params, BigDecimal subEdificio) throws ApplicationException {
        try {
            long resultList;
            Query query = entityManager.createNamedQuery("CmtNotasMgl.getCountBySubEdificio");
            query.setParameter("subEdificio", subEdificio);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = query.getSingleResult() == null ? 0 : ((Long) query.getSingleResult()).intValue();
            return resultList;
        } catch (NoResultException nre) {
            LOGGER.error("No se encontraron resultados para la consulta: ", nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (Exception e) {
             String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "los datos para la Cuenta Matriz. Intente más tarde.");
        }
    }

    public List<CmtNotasMgl> findNotasSearchDatos(HashMap<String, Object> params, BigDecimal subEdificio, int firstResult, int maxResults) throws ApplicationException {
        try {
            List<CmtNotasMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtNotasMgl.findBySubEdificio");
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
            query.setParameter("subEdificio", subEdificio);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtNotasMgl>) query.getResultList();
            return resultList;
        } catch (NoResultException nre) {
            LOGGER.error("No se encontraron resultados para la consulta: ", nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (Exception e) {
             String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "los datos para la Cuenta Matriz. Intente más tarde.");
        }

    }

}
