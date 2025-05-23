package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtItemMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVigenciaCostoItemMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtVigenciaCostoItemMglDaoImpl extends GenericDaoImpl<CmtVigenciaCostoItemMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtVigenciaCostoItemMglDaoImpl.class);

    /**
     * findByItem es empleado para consultar los registros de VigenciaCosto por
     * un Item especifico
     *
     * alejandro.martinez.ext@claro.com.co
     *
     * @param idItem
     * @return
     * @throws ApplicationException
     */
    public List<CmtVigenciaCostoItemMgl> findByItem(BigDecimal idItem) throws ApplicationException {
        try {
            List<CmtVigenciaCostoItemMgl> resultList;
            String queryStr = "SELECT c FROM CmtVigenciaCostoItemMgl c WHERE c.idItem =: idItem";
            Query query = entityManager.createQuery(queryStr, CmtVigenciaCostoItemMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (idItem != null) {
                query.setParameter("idItem", idItem);
            }

            resultList = (List<CmtVigenciaCostoItemMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * findByPeriod es empleado para consultar los registros de VigenciaCosto
     * especificando una fecha inicial y una final que delimitan el periodo para
     * el que sera aplicado el costo registrado.
     *
     * alejandro.martinez.ext@claro.com.co
     *
     * @param fechaInicio
     * @param fechaFin
     * @return
     * @throws ApplicationException
     */
    public List<CmtVigenciaCostoItemMgl> findByPeriod(Date fechaInicio, Date fechaFin) throws ApplicationException {
        try {
            List<CmtVigenciaCostoItemMgl> resultList;
            String queryStr = "SELECT c FROM CmtVigenciaCostoItemMgl c WHERE c.fechaInicio =: fechaInicio"
                    + " AND c.fechaFin =: fechaFin";

            Query query = entityManager.createQuery(queryStr, CmtVigenciaCostoItemMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (fechaInicio != null) {
                query.setParameter("fechaInicio", fechaInicio);
            }

            if (fechaFin != null) {
                query.setParameter("fechaFin", fechaFin);
            }

            resultList = (List<CmtVigenciaCostoItemMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * findByItemPeriod es empleado para consultar los registros de
     * VigenciaCosto especificando una fecha inicial y una final que delimitan
     * el periodo para el que sera aplicado el costo registrado y tambien
     * especificando el item al que aplica el periodo.alejandro.martinez.ext@claro.com.co
     *
     *
     * @param itemObj
     * @return
     * @throws ApplicationException
     */
    public CmtVigenciaCostoItemMgl findByItemVigencia(CmtItemMgl itemObj) throws ApplicationException {
        try {
            CmtVigenciaCostoItemMgl result;

            Query query = entityManager.createNamedQuery("CmtVigenciaCostoItemMgl.findByItemVigencia");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (itemObj != null) {
                query.setParameter("itemObj", itemObj);
            }

            result = (CmtVigenciaCostoItemMgl) query.getSingleResult();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            if (e instanceof NoResultException) {
                throw new ApplicationException("No se encontro vingencia de costo para el producto en el periodo actual");
            }else if(e instanceof NonUniqueResultException){
                throw new ApplicationException("Se encontro mas de un registro de vingencia de costo para el producto en el periodo actual");
            } else {                
                throw new ApplicationException(msg, e);
            }
        }
    }
}