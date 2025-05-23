/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtModificacionesCcmmAudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author bocanegravm
 */
public class CmtModCcmmAudMglDaoImpl extends GenericDaoImpl<CmtModificacionesCcmmAudMgl>{

    private static final Logger LOGGER = LogManager.getLogger(CmtModCcmmAudMglDaoImpl.class);
    
    /**
     * Consulta todos los registros que tiene una solicitud en la tabla de
     * modificaciones.
     *
     * @author victor Bocanegra
     * @param solicitudCmMgl
     * @return List<CmtModificacionesCcmmAudMgl>
     * @throws ApplicationException
     */
    public List<CmtModificacionesCcmmAudMgl> findBySolicitud(CmtSolicitudCmMgl solicitudCmMgl)
            throws ApplicationException {

        try {
            List<CmtModificacionesCcmmAudMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtModificacionesCcmmAudMgl.findBySolicitud");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("solicitudCMObj", solicitudCmMgl);
            resultList = (List<CmtModificacionesCcmmAudMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }

    }
}
