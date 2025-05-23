/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtTiempoSolicitudMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Juan David Hernandez
 */
public class CmtTiempoSolicitudMglDaoImpl extends GenericDaoImpl<CmtTiempoSolicitudMgl>{
    
    private static final Logger LOGGER = LogManager.getLogger(CmtTiempoSolicitudMglDaoImpl.class);
    
     public List<CmtTiempoSolicitudMgl> findAll() throws ApplicationException {

        try {
            List<CmtTiempoSolicitudMgl> resultList;
            Query query = entityManager
                    .createNamedQuery("CmtTiempoSolicitudMgl.findAll");
            query.setParameter("estado", 1);
            resultList = (List<CmtTiempoSolicitudMgl>) query.getResultList();
            return resultList;

        } catch (Exception e) { 
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }   
     
     /**
     * Obtiene tiempos de traza por IdSolicitud
     *
     * @author Juan David Hernandez
     * @param idSolicitud
     * @return CmtTiempoSolicitudMgl
     * @throws ApplicationException
     */
      public CmtTiempoSolicitudMgl findTiemposBySolicitud(BigDecimal idSolicitud)
              throws ApplicationException {
        try {
           
            CmtTiempoSolicitudMgl result = new CmtTiempoSolicitudMgl();
            String queryStr = "SELECT s FROM CmtTiempoSolicitudMgl s WHERE "
                    + " s.solicitudObj.idSolicitud =:idSolicitud AND "
                    + "s.estadoRegistro =:estado ";

            Query query = entityManager.createQuery(queryStr); 
            query.setParameter("idSolicitud", idSolicitud); 
            query.setParameter("estado", 1);     

           result = (CmtTiempoSolicitudMgl) query.getSingleResult();
            return result;

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
}
