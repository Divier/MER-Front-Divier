package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.jpa.entities.HhppAuditoriaMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Juan David Hernandez
 */
public class HhppAuditoriaMglDaoImpl extends GenericDaoImpl<HhppAuditoriaMgl> {

    private static final Logger LOGGER = LogManager.getLogger(HhppAuditoriaMglDaoImpl.class);
    
     /**
     * Obtiene listado de historico de suscriptores del hhpp
     *
     * @param idHhpp del cual se desea obtener el historico de clientes
     * @param firstResult
     * @param maxResults
     * @return listado suscriptores que ha tenia el hhpp
     * @author Juan David Hernandez Torres
     */
    public List<String> findHistoricoSuscriptor(BigDecimal idHhpp,
            int firstResult, int maxResults) {
        try {
            TypedQuery<String> query =
                    (TypedQuery<String>) entityManager.createQuery("SELECT distinct g.suscriptor FROM HhppAuditoriaMgl g "
                    + " where g.hhpIdObj.hhpId =:idHhpp AND g.suscriptor is not null ORDER BY g.hhpFechaCreacion ASC ");
            query.setParameter("idHhpp", idHhpp);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el histórico del suscriptor. EX000: " + e.getMessage(), e);
            return null;
        }
    }
    
    
    /**
     * Obtiene conteo de historico de suscriptores del hhpp
     *
     * @param idHhpp del cual se desea obtener el historico de clientes
     * @return valor conteo suscriptores que ha tenido el hhpp
     * @author Juan David Hernandez Torres
     */
    public int countHistoricoSuscriptor(BigDecimal idHhpp) {
        try {    
            
              Query query = entityManager.createQuery("SELECT Count(1) FROM"
                      + " HhppAuditoriaMgl g "
                    + " where g.hhpIdObj.hhpId =:idHhpp AND g.suscriptor !=:NA"
                      + " ORDER BY g.hhpFechaCreacion ASC");
            
            query.setParameter("idHhpp", idHhpp);
            query.setParameter("NA", "NA");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            return query.getSingleResult() == null ? 0
                    : ((Long) query.getSingleResult()).intValue();

        } catch (Exception e) {
            LOGGER.error("Error al momento de calcular el conteo de histórico de suscriptor. EX000: " + e.getMessage(), e);
            return 0;
        }
    }
}
