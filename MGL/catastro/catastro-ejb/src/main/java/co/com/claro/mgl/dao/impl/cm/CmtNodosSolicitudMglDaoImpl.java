/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNodosSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author bocanegravm
 */
public class CmtNodosSolicitudMglDaoImpl extends GenericDaoImpl<CmtNodosSolicitudMgl> {

    /**
     * Autor: Victor Bocanegra Consulta lista en estructura CmtNodosSolicitudMgl
     *
     * @param cmtSolicitudCmMgl
     * @return List<CmtNodosSolicitudMgl>
     * @throws ApplicationException Error lanzado al realizar consultas
     */
    public List<CmtNodosSolicitudMgl> findBySolicitudId(CmtSolicitudCmMgl cmtSolicitudCmMgl)
            throws ApplicationException {

        List<CmtNodosSolicitudMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtNodosSolicitudMgl.findBySolicitudId");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        if (cmtSolicitudCmMgl != null) {
            query.setParameter("cmtSolicitudCmMglObj", cmtSolicitudCmMgl);
        }
        resultList = (List<CmtNodosSolicitudMgl>) query.getResultList();
        return resultList;
    }
}
