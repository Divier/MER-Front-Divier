/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtCompetenciaTipoMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 * Dao Competencia Tipo. Contiene la logica de acceso a datos competenciaTipos
 * en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtCompetenciaTipoMglDaoImpl extends GenericDaoImpl<CmtCompetenciaTipoMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtCompetenciaTipoMglDaoImpl.class);

    /**
     * Busca las Competencias Tipos. Permite realizar la busqueda de las
     * Compentencias Tipos Activas Paginado el resultado.
     *
     * @author Johnnatan Ortiz
     * @return Compentencias Activas
     * @throws ApplicationException
     */
    public List<CmtCompetenciaTipoMgl> findAllActiveItems() throws ApplicationException {
        try {
            List<CmtCompetenciaTipoMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtCompetenciaTipoMgl.findAllActiveItemsOrderNameComp");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtCompetenciaTipoMgl>) query.getResultList();
            return resultList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
     LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }

    }

    /**
     * Busca las Competencias Tipos.Permite realizar la busqueda de las
 Compentencias Tipos Activas Paginado el resultado.
     *
     * @author Johnnatan Ortiz
     * @param firstResult
     * @param maxResults maximo numero de resultados
     * @return Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    public List<CmtCompetenciaTipoMgl> findAllActiveItemsPaginado(int firstResult,
            int maxResults) throws ApplicationException {
        try {
            List<CmtCompetenciaTipoMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtCompetenciaTipoMgl.findAllActiveItems");
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtCompetenciaTipoMgl>) query.getResultList();
            return resultList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
     LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    /**
     * Cuenta las Competencias Tipo. Permite realizar el conteo de las
     * Compentencias Tipos Activas.
     *
     * @author Johnnatan Ortiz
     * @return numero de Compentencias Tipos activas
     * @throws ApplicationException
     */
    public int getCountAllActiveItems() throws ApplicationException {
        try {
            int result = 0;
            Query query = entityManager.createNamedQuery("CmtCompetenciaTipoMgl.getCountAllActiveItems");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = query.getSingleResult() == null ? 0 : ((Long) query.getSingleResult()).intValue();
            return result;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    /**
     * Cuenta las Competencias Tipo. Permite realizar el conteo de las
     * Compentencias Tipos Activas.
     *
     * @author Johnnatan Ortiz
     * @return numero de Compentencias Tipos activas
     * @throws ApplicationException
     */
    public String getMaxCodigoRr() throws ApplicationException {
        try {
            String result = "0";
            Query query = entityManager.createNamedQuery("CmtCompetenciaTipoMgl.getMaxCodigoRr");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = (String) query.getSingleResult();
            return result;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
}
