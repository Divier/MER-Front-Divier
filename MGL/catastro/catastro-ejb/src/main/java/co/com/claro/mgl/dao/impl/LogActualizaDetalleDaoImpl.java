package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.jpa.entities.LogActualizaDetalle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
public class LogActualizaDetalleDaoImpl extends GenericDaoImpl<LogActualizaDetalle> {

    private static final Logger LOGGER = LogManager.getLogger(LogActualizaDetalleDaoImpl.class);
    
        /**
     * duartey Metodo para buscar el detalle de un log
     *
     *
     * @param idMaster
     * @return
     */
    public List<LogActualizaDetalle> findDetalleMaster(BigDecimal idMaster) {
        try {
            Query query = entityManager.createNamedQuery("LogActualizaDetalle.findLogMaster");
            query.setParameter("idNap", idMaster);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el LogActualizaDetalle. EX000: " + e.getMessage(), e);
            return null;
        }
    }
        /**
     * duartey Metodo para buscar el detalle de un log por estado
     *
     *
     * @param idMaster
     * @param estado
     * @return
     */
    public List<LogActualizaDetalle> findDetalleMasterByEstado(BigDecimal idMaster, String estado) {
        try {
            Query query = entityManager.createNamedQuery("LogActualizaDetalle.findLogMasterByEstado");
            query.setParameter("idNap", idMaster);
            query.setParameter("estadoTransaccion", estado);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el LogActualizaDetalle. EX000: " + e.getMessage(), e);
            return null;
        }
    }
}
