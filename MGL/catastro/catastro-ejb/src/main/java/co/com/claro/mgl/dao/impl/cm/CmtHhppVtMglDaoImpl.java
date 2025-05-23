package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtHhppVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author alejandro.martine.ext@claro.com.co
 */
public class CmtHhppVtMglDaoImpl extends GenericDaoImpl<CmtHhppVtMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtHhppVtMglDaoImpl.class);

    public List<CmtHhppVtMgl> findAll() throws ApplicationException {
        try {
            List<CmtHhppVtMgl> resulList;
            Query query = entityManager.createNamedQuery("CmtHhppVtMgl.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resulList = (List<CmtHhppVtMgl>) query.getResultList();
            return resulList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    public List<CmtHhppVtMgl> findBySubEdiVt(CmtSubEdificiosVt subEdificioVtObj)
            throws ApplicationException {
        try {
            List<CmtHhppVtMgl> resulList;
            Query query = entityManager.createNamedQuery("CmtHhppVtMgl.findBySubEdiVt");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (subEdificioVtObj != null) {
                query.setParameter("subEdificioVtObj", subEdificioVtObj);
            }
            resulList = (List<CmtHhppVtMgl>) query.getResultList();
            Collections.sort(resulList, new Comparator<CmtHhppVtMgl>() {
                @Override
                public int compare(CmtHhppVtMgl obj1, CmtHhppVtMgl obj2) {
                    return obj1.getOpcionNivel5().compareTo(obj2.getOpcionNivel5());
                }
            });
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<CmtHhppVtMgl> findByVt(CmtVisitaTecnicaMgl vtObj) throws ApplicationException {
        try {
            List<CmtHhppVtMgl> resulList;
            Query query = entityManager.createNamedQuery("CmtHhppVtMgl.findByVt");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (vtObj != null) {
                query.setParameter("vtObj", vtObj);
            }
            resulList = (List<CmtHhppVtMgl>) query.getResultList();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    
        public CmtHhppVtMgl findByIdDrDireccion(BigDecimal drDireccion) throws ApplicationException {
        try {
            CmtHhppVtMgl cmtHhppVtMgl;
            Query query = entityManager.createNamedQuery("CmtHhppVtMgl.findByIdDrDireccion");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (drDireccion != null) {
                query.setParameter("drDireccion", drDireccion);
            }
            cmtHhppVtMgl = (CmtHhppVtMgl) query.getSingleResult();
            return cmtHhppVtMgl;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
        
    /**
     * Busca Lista de CmtHhppVtMgl en repositorio por suedificio Vt y piso
     *
     * @param subEdificioVtObj  subedificio vt
     * @param piso
     * @return List<CmtHhppVtMgl> encontrada en el repositorio
     * @author Victor Bocanegra
     * @throws co.com.claro.mgl.error.ApplicationException
     */    
    public List<CmtHhppVtMgl> findBySubedificioVtAndPiso(CmtSubEdificiosVt subEdificioVtObj,
            int piso) throws ApplicationException {
        try {
            List<CmtHhppVtMgl> resulList;
            Query query = entityManager.createNamedQuery("CmtHhppVtMgl.findBySubEdiVtAndPiso");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (subEdificioVtObj != null) {
                query.setParameter("subEdificioVtObj", subEdificioVtObj);
            }
            
            query.setParameter("piso", piso);
            
            resulList = (List<CmtHhppVtMgl>) query.getResultList();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
        

}

