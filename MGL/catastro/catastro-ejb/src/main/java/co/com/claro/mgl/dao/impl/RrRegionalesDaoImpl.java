/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RrRegionales;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Parzifal de León
 */
public class RrRegionalesDaoImpl extends GenericDaoImpl<RrRegionales> {
    
    private static final Logger LOGGER = LogManager.getLogger(RrRegionalesDaoImpl.class);

    public List<CmtRegionalRr> findByUnibi(String unibi) {
        List<CmtRegionalRr> resultList = null;
        String query = "SELECT\n" +
                        "    regional_rr_id, codigo_rr,\n" +
                        "    nombre_regional\n" +
                        "FROM\n" +
                        "    "+Constant.MGL_DATABASE_SCHEMA+"."+ "cmt_regional_rr\n" +
                        "where ESTADO_REGISTRO = 1 and codigo_rr not in (?)\n" +
                        "    ORDER BY nombre_regional";
        Query q = entityManager.createNativeQuery(query, CmtRegionalRr.class);
        q.setParameter(1, "CAL");
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<CmtRegionalRr>) q.getResultList();
        return resultList;
    }

    public List<RrRegionales> findByUniAndBi() {
        List<RrRegionales> resultList = null;
        Query query = entityManager.createNamedQuery("RrRegionales.findUniAndBi");
        query.setParameter("cal", "CAL");
        query.setParameter("uni", "U");
        query.setParameter("bi", "B");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<RrRegionales>) query.getResultList();
        return resultList;
    }

    @Override
    public RrRegionales find(String id) throws ApplicationException {
        try {
            return this.entityManager.find(entityClass, id);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    public List<CmtRegionalRr> findRegionales() {
        List<CmtRegionalRr> resultList = null;
        Query q = entityManager.createNamedQuery("CmtRegionalRr.findEstado1");
        resultList = (List<CmtRegionalRr>)q.getResultList();
        return resultList;
    }
    
     public String findNombreRegionalByCodigo(String codigo) throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("RrRegionales.findNombreRegionalByCodigo");
            query.setParameter("codigo", codigo);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            String result = (String) query.getSingleResult();
            return result;

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
     
    public List<CmtRegionalRr> findByCodigo(List<BigDecimal> codigo) throws ApplicationException {
        try {

            
            Query query = entityManager.createQuery("SELECT c FROM CmtRegionalRr c where c.regionalRrId  IN :codigo ");
            query.setParameter("codigo", codigo);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            List<CmtRegionalRr> result = query.getResultList();
            return result;

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
      
}
