package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
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
public class CmtSubEdificiosVtDaoImp extends GenericDaoImpl<CmtSubEdificiosVt> {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtSubEdificiosVtDaoImp.class);

    public List<CmtSubEdificiosVt> findAll() throws ApplicationException {
        try {
            List<CmtSubEdificiosVt> resulList;
            Query query = entityManager.createNamedQuery("CmtSubEdificiosVt.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resulList = (List<CmtSubEdificiosVt>) query.getResultList();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<CmtSubEdificiosVt> findByVt(CmtVisitaTecnicaMgl vtObj) throws ApplicationException {
        try {
            List<CmtSubEdificiosVt> resulList;
            Query query = entityManager.createNamedQuery("CmtSubEdificiosVt.findByVt");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (vtObj != null) {
                query.setParameter("vtObj", vtObj);
            }
            resulList = (List<CmtSubEdificiosVt>) query.getResultList();
            Collections.sort(resulList, new Comparator<CmtSubEdificiosVt>() {
                @Override
                public int compare(CmtSubEdificiosVt a, CmtSubEdificiosVt b) {
                     if (a.getNombreSubEdificio() == null) {
                        return (b.getNombreSubEdificio() == null) ? 0 : -1;
                    }

                    if (b.getNombreSubEdificio() == null) {
                        return 0;
                    }
                    return a.getNombreSubEdificio().compareTo(b.getNombreSubEdificio());
                }
            });
            
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Cuenta los Sub Edificio creados en una visita tecnica. Permite realizar
     * el conteo de las Compentencias de un mismo SubEdificio.
     *
     * @author alejandro.martine.ext@claro.com.co
     * @param vtObj VisitaTecnica
     * @return numero de Sub Edificios asociadas a una VisitaTecnica
     * @throws ApplicationException
     */
    public int getCountByVt(CmtVisitaTecnicaMgl vtObj) throws ApplicationException {
        int result = 0;
        Query query = entityManager.createNamedQuery("CmtSubEdificiosVt.getCountByVt");
        query.setParameter("vtObj", vtObj);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        result = query.getSingleResult() == null ? 0 : ((Long) query.getSingleResult()).intValue();
        return result;
    }

    /**
     * Busca los Sub Edificio creados en una VisitaTecnica.Permite realizar la
 busqueda de los Sub Edificio de un mismo VisitaTecnica.
     *
     * @author Johnnatan Ortiz
     * @param firstResult
     * @param maxResults maximo numero de resultados
     * @param vtObj VisitaTecnica
     * @return Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    public List<CmtSubEdificiosVt> findByVtPaginado(int firstResult,
            int maxResults, CmtVisitaTecnicaMgl vtObj) throws ApplicationException {
        List<CmtSubEdificiosVt> resultList;
        Query query = entityManager.createNamedQuery("CmtSubEdificiosVt.findByVt");
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        query.setParameter("vtObj", vtObj);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtSubEdificiosVt>) query.getResultList();
        return resultList;
    }

    public CmtSubEdificiosVt findByIdSubEdificio(BigDecimal id) throws ApplicationException {
        try {
            CmtSubEdificiosVt cmtSubEdificiosVt;
            Query query = entityManager.createNamedQuery("CmtSubEdificiosVt.findByIdSubEdificio");
            query.setParameter("subEdificioId", id);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            cmtSubEdificiosVt = (CmtSubEdificiosVt) query.getSingleResult();
            return cmtSubEdificiosVt;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    public CmtSubEdificiosVt findVtByIdSubEdificioAndLastVt(BigDecimal id) throws ApplicationException {
        try {
            CmtSubEdificiosVt cmtSubEdificiosVt;
            Query query = entityManager.createNamedQuery("CmtSubEdificiosVt.findVtByIdSubEdificioAndLastVt");
            query.setParameter("subEdificioId", id);
            query.setParameter("estadoVisitaTecnica", BigDecimal.ONE);            
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            cmtSubEdificiosVt = (CmtSubEdificiosVt) query.getSingleResult();
            return cmtSubEdificiosVt;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    
    /**
     * Busca los Sub Edificio creados en una VisitaTecnica que no tienen orden de acometida
     * @author Victor Bocanegra 
     * @param vtObj VisitaTecnica
     * @return List<CmtSubEdificiosVt>
     * @throws ApplicationException
     */
    public List<CmtSubEdificiosVt> findByVtAndAcometida(CmtVisitaTecnicaMgl vtObj) throws ApplicationException {
        try {
            List<CmtSubEdificiosVt> resulList;
            Query query = entityManager.createNamedQuery("CmtSubEdificiosVt.findByVtAndAcometida");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (vtObj != null) {
                query.setParameter("vtObj", vtObj);
            }
            resulList = (List<CmtSubEdificiosVt>) query.getResultList();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    /**
     * Busca los Sub Edificio creados en una VisitaTecnica que tienen orden de
     * acometida
     *
     * @author Victor Bocanegra
     * @param idOt orden de acometida
     * @return List<CmtSubEdificiosVt>
     * @throws ApplicationException
     */
    public List<CmtSubEdificiosVt> findByIdOtAcometida(BigDecimal idOt) throws ApplicationException {
        try {
            List<CmtSubEdificiosVt> resulList;
            Query query = entityManager.createNamedQuery("CmtSubEdificiosVt.findByIdOtAcometida");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (idOt != null) {
                query.setParameter("otAcometidaId", idOt);
            }
            resulList = (List<CmtSubEdificiosVt>) query.getResultList();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
