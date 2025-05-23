/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author valbuenayf
 */
public class CmtRegionalRrDaoImpl extends GenericDaoImpl<CmtRegionalRr> {

    private static final Logger LOGGER = LogManager.getLogger(CmtRegionalRrDaoImpl.class);
    
    
    /**
     * bocanegravm Metodo para consultar una regional por codigo
     * @param  codigo
     * @return CmtRegionalRr
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public CmtRegionalRr findRegionalByCod(String codigo)
            throws ApplicationException {

        List<CmtRegionalRr> lstResult;
        CmtRegionalRr regionalRr = null;
        try {

            Query query = entityManager.createNamedQuery("CmtRegionalRr.findByCodigo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (codigo != null && !codigo.isEmpty()) {
                query.setParameter("codigoRr", codigo);
            }
            lstResult = (List<CmtRegionalRr>) query.getResultList();

            if (lstResult.size() > 0) {
                regionalRr = lstResult.get(0);
            }
            return regionalRr;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            return regionalRr;
        }
    }
    
      
    /**
     * bocanegravm Metodo para consultar todas las regionales en BD
     *
     * @return List<CmtRegionalRr>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtRegionalRr> findAllRegionales()
            throws ApplicationException {

        List<CmtRegionalRr> lstResult = new ArrayList<CmtRegionalRr>();
        try {

            Query query = entityManager.createNamedQuery("CmtRegionalRr.findEstado1");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            lstResult = (List<CmtRegionalRr>) query.getResultList();

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            return lstResult;
        }
        return lstResult;
    }
}