/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtNotasSolicitudMglDaoImpl extends GenericDaoImpl<CmtNotasSolicitudMgl> {

    public List<CmtNotasSolicitudMgl> findAll() throws ApplicationException {
        List<CmtNotasSolicitudMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtNotasSolicitudMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasSolicitudMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasSolicitudMgl> findTipoNotasId(BigDecimal tipoNotasId)
            throws ApplicationException {
        List<CmtNotasSolicitudMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "CmtNotasSolicitudMgl c WHERE c.basicaIdTipoNota = :tipoNotasId ");
        query.setParameter("tipoNotasId", tipoNotasId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasSolicitudMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasSolicitudMgl> findNotasBySolicitudId(BigDecimal idSolicitudCm)
            throws ApplicationException {
        List<CmtNotasSolicitudMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "CmtNotasSolicitudMgl c WHERE c.solicitudCmId = :idSolicitudCm  "
                + "ORDER BY c.fechaCreacion ");
        query.setParameter("idSolicitudCm", idSolicitudCm);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasSolicitudMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasSolicitudMgl> findNotasSolByIdSolAndBasicaIdTipoNota(
            BigDecimal idSolicitudCm, BigDecimal basicaIdTipoNota)
            throws ApplicationException {
        List<CmtNotasSolicitudMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "CmtNotasSolicitudMgl c WHERE c.solicitudCmId = :idSolicitudCm "
                + "AND c.basicaIdTipoNota = :basicaIdTipoNota "
                + "ORDER BY c.fechaCreacion ");
        query.setParameter("idSolicitudCm", idSolicitudCm);
        query.setParameter("basicaIdTipoNota", basicaIdTipoNota);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasSolicitudMgl>) query.getResultList();
        return resultList;
    }
}