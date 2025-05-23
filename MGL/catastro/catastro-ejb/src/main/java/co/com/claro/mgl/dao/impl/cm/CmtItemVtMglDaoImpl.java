package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtItemMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtItemVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 * Clase que implementa el acceso a datos para la tabla CMT_ITEMVT
 *
 * alejandro.martinez.ext@claro.com.co
 *
 * @versión 1.00.0000
 */
public class CmtItemVtMglDaoImpl extends GenericDaoImpl<CmtItemVtMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtItemVtMglDaoImpl.class);

    public List<CmtItemVtMgl> findAll()
            throws ApplicationException {
        try {
            List<CmtItemVtMgl> resultList;
            Query query =
                    entityManager.createNamedQuery("CmtItemVtMgl.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<CmtItemVtMgl>) query.getResultList();
            return resultList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    /**
     * findItemByVt metodo empleado para consultar empleando el id de la visita
     * tecnica los items de mano de obra o materiales correspondientes a esta.alejandro.martinez.ext@claro.com.co
     *
     *
     * @param vtObj
     * @param idVt
     * @param tipoItem
     * @param versionVt
     * @return
     * @throws ApplicationException
     */
    public List<CmtItemVtMgl> findItemVtByVt(CmtVisitaTecnicaMgl vtObj, String tipoItem) throws ApplicationException {
        try {
            List<CmtItemVtMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtItemVtMgl.findItemVtByVt");
            if (vtObj != null) {
                query.setParameter("vtObj", vtObj);
                query.setParameter("tipoItem", tipoItem);
            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtItemVtMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public CmtItemVtMgl findItemVtByItem(CmtVisitaTecnicaMgl vtObj, CmtItemMgl idItem) throws ApplicationException {
        try {
            CmtItemVtMgl result;
            Query query = entityManager.createNamedQuery("CmtItemVtMgl.findItemVtByItem");
            query.setParameter("vtObj", vtObj);
            query.setParameter("idItem", idItem.getIdItem());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = (CmtItemVtMgl) query.getSingleResult();
            return result;
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                return null;
            } else if (e instanceof NonUniqueResultException) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException(e.getMessage(), e);
            } else {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException(e.getMessage(), e);
            }
        }
    }

    public int getCountByVt(CmtVisitaTecnicaMgl vtObj, String tipoItem) throws ApplicationException {
        int result = 0;
        Query query = entityManager.createNamedQuery("CmtItemVtMgl.getCountByVt");
        if (vtObj != null) {
            query.setParameter("vtObj", vtObj);
            query.setParameter("tipoItem", tipoItem);
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        result = query.getSingleResult() == null ? 0 : ((Long) query.getSingleResult()).intValue();
        return result;
    }

    public List<CmtItemVtMgl> findItemByVtPaginado(int firstResult,
            int maxResults, CmtVisitaTecnicaMgl vtObj, String tipoItem) throws ApplicationException {
        try {
            List<CmtItemVtMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtItemVtMgl.findItemVtByVt");
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
            if (vtObj != null) {
                query.setParameter("vtObj", vtObj);
                query.setParameter("tipoItem", tipoItem);
            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtItemVtMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * findByVtItem metodo empleado para la busqueda de un item especifico en
     * una visita tecnica especifica.
     *
     * alejandro.martinez.ext@claro.com.co
     *
     * @param idVt
     * @param idItem
     * @param versionVt
     * @return
     * @throws ApplicationException
     */
    public List<CmtItemVtMgl> findByVtItem(BigDecimal idVt, BigDecimal idItem, int versionVt) throws ApplicationException {
        try {
            List<CmtItemVtMgl> resultList;
            String queryStr =
                    "SELECT i from CmtItemVtMgl i WHERE i.idVt =: idVt AND i.idItem =: idItem "
                    + "AND i.versionVt =: versionVt";
            Query query = entityManager.createQuery(queryStr, CmtItemVtMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (idVt != null) {
                query.setParameter("idVt", idVt);
            }

            if (idItem != null) {
                query.setParameter("idItem", idItem);
            }

            if (idItem != null) {
                query.setParameter("idItem", idItem);
            }

            resultList = (List<CmtItemVtMgl>) query.getResultList();
            return resultList;

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
