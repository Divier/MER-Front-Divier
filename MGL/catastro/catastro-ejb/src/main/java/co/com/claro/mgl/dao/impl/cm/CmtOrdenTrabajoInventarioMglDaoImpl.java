/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoInventarioMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 * Dao Orden de Trabajo Inventario. Contiene la logica de acceso a datos ordenes
 * de trabajo inventario en el repositorio.
 *
 * @author Victor Bocanegra
 * @version 1.00.000
 */
public class CmtOrdenTrabajoInventarioMglDaoImpl extends GenericDaoImpl<CmtOrdenTrabajoInventarioMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtOrdenTrabajoInventarioMglDaoImpl.class);


    /**
     * Busca los inventarios de tecnologias asociados Ordenes de Trabajo
     *
     * @author Victor Bocanegra
     * @param Otid ID de la orden de trabajo
     * @return Lista de Inventarios de tecnologias asociados a una Orden de
     * Trabajo encontradas en el repositorio
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoInventarioMgl> findByIdOt(BigDecimal Otid)
            throws ApplicationException {
        try {
            List<CmtOrdenTrabajoInventarioMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtOrdenTrabajoInventarioMgl.findByOtId");
            query.setParameter("otId", Otid);
            query.setParameter("estadoRegistro", 1);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtOrdenTrabajoInventarioMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Busca el inventarios de tecnologias por el Id del inventario 
     *
     * @author Victor Bocanegra
     * @param idOtInvTec ID del inventario 
     * @return CmtOrdenTrabajoInventarioMgl encontradas en el repositorio
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoInventarioMgl findByIdOtInvTec(BigDecimal idOtInvTec)
            throws ApplicationException {
        try {
            CmtOrdenTrabajoInventarioMgl resultList;
            Query query = entityManager.createNamedQuery("CmtOrdenTrabajoInventarioMgl.findByOrdentrabajoInventarioId");
            query.setParameter("ordentrabajoInventarioId", idOtInvTec);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (CmtOrdenTrabajoInventarioMgl) query.getSingleResult();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
