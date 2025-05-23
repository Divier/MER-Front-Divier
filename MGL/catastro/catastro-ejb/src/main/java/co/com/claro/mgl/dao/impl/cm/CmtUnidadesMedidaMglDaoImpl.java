package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtUnidadesMedidaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtUnidadesMedidaMglDaoImpl extends GenericDaoImpl<CmtUnidadesMedidaMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtUnidadesMedidaMglDaoImpl.class);

    public List<CmtUnidadesMedidaMgl> findAll() throws ApplicationException {
        try {
            List<CmtUnidadesMedidaMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtUnidadesMedidaMgl.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtUnidadesMedidaMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
}
