/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudVtMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;


/**
 *
 * @author Juan David Hernandez
 */
public class CmtNotasSolicitudVtMglDaoImpl extends GenericDaoImpl<CmtNotasSolicitudVtMgl> {

    public List<CmtNotasSolicitudVtMgl> findAll() throws ApplicationException {
        List<CmtNotasSolicitudVtMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtNotasSolicitudVtMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasSolicitudVtMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasSolicitudVtMgl> findTipoNotasId(BigDecimal tipoNotasId)
            throws ApplicationException {
        List<CmtNotasSolicitudVtMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "CmtNotasSolicitudVtMgl c WHERE c.basicaIdTipoNota = :tipoNotasId ");
        query.setParameter("tipoNotasId", tipoNotasId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasSolicitudVtMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasSolicitudVtMgl> findNotasBySolicitudId(BigDecimal idSolicitud)
            throws ApplicationException {
        List<CmtNotasSolicitudVtMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "CmtNotasSolicitudVtMgl c WHERE c.solicitud.idSolicitud = :idSolicitud  "
                + "ORDER BY c.fechaCreacion ");
        query.setParameter("idSolicitud", idSolicitud);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasSolicitudVtMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasSolicitudVtMgl> findNotasSolByIdSolAndBasicaIdTipoNota(
            BigDecimal idSolicitudCm, BigDecimal basicaIdTipoNota)
            throws ApplicationException {
        List<CmtNotasSolicitudVtMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "CmtNotasSolicitudVtMgl c WHERE c.solicitud = :idSolicitudCm "
                + "AND c.basicaIdTipoNota = :basicaIdTipoNota "
                + "ORDER BY c.fechaCreacion ");
        query.setParameter("idSolicitudCm", idSolicitudCm);
        query.setParameter("basicaIdTipoNota", basicaIdTipoNota);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasSolicitudVtMgl>) query.getResultList();
        return resultList;
    }
}