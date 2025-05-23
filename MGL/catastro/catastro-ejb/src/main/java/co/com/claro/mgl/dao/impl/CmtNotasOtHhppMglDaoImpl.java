/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtNotasOtHhppMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;


/**
 *
 * @author Juan David Hernandez
 */
public class CmtNotasOtHhppMglDaoImpl extends GenericDaoImpl<CmtNotasOtHhppMgl> {

    public List<CmtNotasOtHhppMgl> findAll() throws ApplicationException {
        List<CmtNotasOtHhppMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtNotasOtHhppMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasOtHhppMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasOtHhppMgl> findTipoNotasId(BigDecimal tipoNotasId)
            throws ApplicationException {
        List<CmtNotasOtHhppMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "CmtNotasOtHhppMgl c WHERE c.basicaIdTipoNota = :tipoNotasId ");
        query.setParameter("tipoNotasId", tipoNotasId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasOtHhppMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasOtHhppMgl> findNotasByOtHhppId(BigDecimal idOtHhpp)
            throws ApplicationException {
        List<CmtNotasOtHhppMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "CmtNotasOtHhppMgl c WHERE c.otHhppId.otHhppId = :idOtHhpp  "
                + "ORDER BY c.fechaCreacion ");
        query.setParameter("idOtHhpp", idOtHhpp);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasOtHhppMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasOtHhppMgl> findNotasHhppByIdHhppAndBasicaIdTipoNota(
            BigDecimal idHhpp, BigDecimal basicaIdTipoNota)
            throws ApplicationException {
        List<CmtNotasOtHhppMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "CmtNotasOtHhppMgl c WHERE c.hhppId = :idHhpp "
                + "AND c.basicaIdTipoNota = :basicaIdTipoNota "
                + "ORDER BY c.fechaCreacion ");
        query.setParameter("idHhpp", idHhpp);
        query.setParameter("basicaIdTipoNota", basicaIdTipoNota);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasOtHhppMgl>) query.getResultList();
        return resultList;
    }
}