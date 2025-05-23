/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.CmtFiltroConsultaRegionalDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *
 * @author JPeña
 */
public class CmtRegionalMglDaoImpl extends GenericDaoImpl<CmtRegionalRr>{
    
    private static final Logger LOGGER = LogManager.getLogger(CmtRegionalMglDaoImpl.class);
    
    public List<CmtRegionalRr> findByFiltro(int paginaSelected,
            int maxResults, CmtFiltroConsultaRegionalDto consulta) throws ApplicationException {
        List<CmtRegionalRr> resultList;

        String queryTipo = "SELECT n FROM CmtRegionalRr n  WHERE  n.estadoRegistro= 1  ";

        if (consulta.getIdRegional()!= null) {
            queryTipo += "AND  n.regionalRrId =  :regionalRrId ";
        }
        if (consulta.getCodigoRegional()!= null && !consulta.getCodigoRegional().isEmpty()) {
            queryTipo += "AND UPPER(n.codigoRr) LIKE :codigoRr ";
        }
        if (consulta.getNombreRegional()!= null && !consulta.getNombreRegional().isEmpty()) {
            queryTipo += "AND UPPER(n.nombreRegional) LIKE :nombreRegional ";
        }

        Query query = entityManager.createQuery(queryTipo);

        if (consulta.getIdRegional() != null) {
            query.setParameter("regionalRrId", consulta.getIdRegional());
        }
        if (consulta.getCodigoRegional() != null && !consulta.getCodigoRegional().isEmpty()) {
            query.setParameter("codigoRr", "%" + consulta.getCodigoRegional().toUpperCase() + "%");
        }
        if (consulta.getNombreRegional() != null && !consulta.getNombreRegional().isEmpty()) {
            query.setParameter("nombreRegional", "%" + consulta.getNombreRegional().toUpperCase() + "%");
        }
        

        query.setFirstResult(paginaSelected);
        query.setMaxResults(maxResults);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtRegionalRr>) query.getResultList();

        return resultList;
    }

    public Long countByNodFiltro(CmtFiltroConsultaRegionalDto consulta)
            throws ApplicationException {

        Long resultCount = 0L;
        String queryTipo;
        queryTipo = "SELECT COUNT(1) FROM CmtRegionalRr n  "
                + "WHERE n.estadoRegistro= 1 ";

       
        if (consulta.getIdRegional()!= null) {
            queryTipo += "AND  n.regionalRrId =  :regionalRrId ";
        }
        if (consulta.getCodigoRegional()!= null && !consulta.getCodigoRegional().isEmpty()) {
            queryTipo += "AND UPPER(n.codigoRr) LIKE :codigoRr ";
        }
        if (consulta.getNombreRegional()!= null && !consulta.getNombreRegional().isEmpty()) {
            queryTipo += "AND UPPER(n.nombreRegional) LIKE :nombreRegional ";
        }

        Query query = entityManager.createQuery(queryTipo);

        if (consulta.getIdRegional() != null) {
            query.setParameter("regionalRrId", consulta.getIdRegional());
        }
        if (consulta.getCodigoRegional() != null && !consulta.getCodigoRegional().isEmpty()) {
            query.setParameter("codigoRr", "%" + consulta.getCodigoRegional().toUpperCase() + "%");
        }
        if (consulta.getNombreRegional() != null && !consulta.getNombreRegional().isEmpty()) {
            query.setParameter("nombreRegional", "%" + consulta.getNombreRegional().toUpperCase() + "%");
        }


        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultCount = (Long) query.getSingleResult();
        return resultCount;
    }

    
    public CmtRegionalRr findByCodigoRR(String codigoRR) throws ApplicationException{
        try {
            List<CmtRegionalRr> result;
            Query query = entityManager.createQuery("SELECT n FROM CmtRegionalRr n WHERE UPPER(n.codigoRr) = :codigoRR  AND  n.estadoRegistro = 1 ");
            query.setParameter("codigoRR", codigoRR.toUpperCase());
            result = (List<CmtRegionalRr>) query.getResultList();
            if (result == null || result.isEmpty()) {
                return null;
            }
            return result.get(0);
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);

            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }

        
    }
    
    public CmtRegionalRr findByIdRR(CmtRegionalRr codigoRR) throws ApplicationException{
         CmtRegionalRr cmtRegionalRr = null;
        try {
            Query query = entityManager.createNamedQuery("CmtComunidadRr.findComunidadById");
            query.setParameter("comunidadRrId", cmtRegionalRr.getCodigoRr());
            cmtRegionalRr = (CmtRegionalRr) query.getSingleResult();
        } catch (NonUniqueResultException e) {
            LOGGER.error("Error findComunidadId de CmtComunidadRrDaoImpl hay mas de un registro " + e.getMessage());
            return null;
        } catch (NoResultException e) {
            LOGGER.error("Error findComunidadId de CmtComunidadRrDaoImpl no hay registros " + e.getMessage());
            return null;
        } catch (Exception e) {
            LOGGER.error("Error findComunidadId de CmtComunidadRrDaoImpl " + e.getMessage());
            return null;
        }
        return cmtRegionalRr;
    }
    
    public List<CmtRegionalRr> findAll() throws ApplicationException{
        Query query = entityManager.createNamedQuery("CmtRegionalRr.findAll");
        return query.getResultList();
    }
    
    
}