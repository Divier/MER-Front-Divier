/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtItemMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtItemMglDaoImpl extends GenericDaoImpl<CmtItemMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtItemMglDaoImpl.class);

    /**
     * findByTipoItem lista los items de acuerdo a su tipo, mano de obra o
     * materiales.
     *
     * alejandro.martinez.ext@claro.com.co
     *
     * @param tipoItem
     * @return
     * @throws ApplicationException
     */
    public List<CmtItemMgl> findByTipoItem(String tipoItem) throws ApplicationException {
        try {
            List<CmtItemMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtItemMgl.findByTipoItem");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (tipoItem != null && !tipoItem.trim().isEmpty()) {
                query.setParameter("tipoItem", tipoItem);
            }

            resultList = (List<CmtItemMgl>) query.getResultList();
            return resultList;

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
