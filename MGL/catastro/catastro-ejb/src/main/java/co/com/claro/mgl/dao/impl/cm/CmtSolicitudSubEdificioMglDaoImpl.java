package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudSubEdificioMgl;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtSolicitudSubEdificioMglDaoImpl extends GenericDaoImpl<CmtSolicitudSubEdificioMgl> {

    public List<CmtSolicitudSubEdificioMgl> findAll() throws ApplicationException {
        List<CmtSolicitudSubEdificioMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtSolicitudSubEdificioMgl.findAll");
        resultList = (List<CmtSolicitudSubEdificioMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtSolicitudSubEdificioMgl> findSolicitudSubEdificioBySolicitud(CmtSolicitudCmMgl solicitudCM) throws ApplicationException {
        List<CmtSolicitudSubEdificioMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtSolicitudSubEdificioMgl.findBySolicitud");
        query.setParameter("solicitudCM", solicitudCM);
        resultList = query.getResultList();
        return resultList;
    }

}
