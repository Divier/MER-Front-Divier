/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtRelacionMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;
/**
 *Fabian Barrera
 * @author User
 */
public class CmtRelacionMglDaoImpl extends GenericDaoImpl<CmtRelacionMgl>{
    
    private static final Logger LOGGER = LogManager.getLogger(CmtRelacionMglDaoImpl.class);
    
    public List<CmtRelacionMgl> findAll() throws ApplicationException {
        List<CmtRelacionMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtRelacionMgl.findAll");
        resultList = (List<CmtRelacionMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtRelacionMgl> findRelacionId(BigDecimal cuentaMatrizId) throws ApplicationException {
        List<CmtRelacionMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM CmtRelacionMgl c WHERE c.cuentaMatrizId :cuentaMatrizId ");
        query.setParameter("cuentaMatrizId", cuentaMatrizId);
        resultList = (List<CmtRelacionMgl>) query.getResultList();
        LOGGER.error("resultList "+ resultList);
        return resultList;
    }
}
