/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtGrupoProyectoValidacionMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Juan David Hernandez
 */
public class CmtGrupoProyectoValidacionMglDaoImpl extends GenericDaoImpl<CmtGrupoProyectoValidacionMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtGrupoProyectoValidacionMglDaoImpl.class);

    public List<CmtGrupoProyectoValidacionMgl> findAll() throws ApplicationException {

        try {
            List<CmtGrupoProyectoValidacionMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtGrupoProyectoValidacionMgl.findAll");
            query.setParameter("estado", 1);
            resultList = (List<CmtGrupoProyectoValidacionMgl>) query.getResultList();
            return resultList;

        } catch (Exception ex) {    
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

   public List<CmtGrupoProyectoValidacionMgl> findPendientesParaGestionPaginacion(int firstResult,
            int maxResults) throws ApplicationException {
        try {
           
            String queryStr = "SELECT s FROM CmtGrupoProyectoValidacionMgl s WHERE "
                    + " s.estadoRegistro =:estado Order by s.grupoProyectoId DESC ";

            Query query = entityManager.createQuery(queryStr);

            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
            query.setParameter("estado", 1);     
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            return (List<CmtGrupoProyectoValidacionMgl>) query.getResultList();

        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
   
      public List<CmtGrupoProyectoValidacionMgl> findByProyectoGeneral() throws ApplicationException {
        try {
           
            String queryStr = "SELECT s FROM CmtGrupoProyectoValidacionMgl s, CmtBasicaMgl b WHERE "
                    + " s.grupoProyectoBasicaId = b.basicaId AND "
                    + " b.tipoBasicaObj.tipoBasicaId = :tipoBasicaId AND "
                    + " b.estadoRegistro =:estadoRegistro AND "
                    + " s.estadoRegistro =:estadoRegistro ";
            
            Query query = entityManager.createQuery(queryStr);

            query.setParameter("tipoBasicaId", 150);     
            query.setParameter("estadoRegistro", 1); 
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            return (List<CmtGrupoProyectoValidacionMgl>) query.getResultList();

        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
}
