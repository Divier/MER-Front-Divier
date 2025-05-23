/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtNotasHhppVtMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;


/**
 *
 * @author Juan David Hernandez
 */
public class CmtNotasHhppVtMglDaoImpl extends GenericDaoImpl<CmtNotasHhppVtMgl> {

    public List<CmtNotasHhppVtMgl> findAll() throws ApplicationException {
        List<CmtNotasHhppVtMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtNotasHhppVtMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasHhppVtMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasHhppVtMgl> findTipoNotasId(BigDecimal tipoNotasId)
            throws ApplicationException {
        List<CmtNotasHhppVtMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "CmtNotasHhppVtMgl c WHERE c.basicaIdTipoNota = :tipoNotasId ");
        query.setParameter("tipoNotasId", tipoNotasId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasHhppVtMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasHhppVtMgl> findNotasByHhppId(BigDecimal idHhpp)
            throws ApplicationException {
        List<CmtNotasHhppVtMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "CmtNotasHhppVtMgl c WHERE c.hhppId.hhpId = :idHhpp  "
                + "ORDER BY c.fechaCreacion DESC");
        query.setParameter("idHhpp", idHhpp);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasHhppVtMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtNotasHhppVtMgl> findNotasHhppByIdHhppAndBasicaIdTipoNota(
            BigDecimal idHhpp, BigDecimal basicaIdTipoNota)
            throws ApplicationException {
        List<CmtNotasHhppVtMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "CmtNotasHhppVtMgl c WHERE c.hhppId = :idHhpp "
                + "AND c.basicaIdTipoNota = :basicaIdTipoNota "
                + "ORDER BY c.fechaCreacion ");
        query.setParameter("idHhpp", idHhpp);
        query.setParameter("basicaIdTipoNota", basicaIdTipoNota);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtNotasHhppVtMgl>) query.getResultList();
        return resultList;
    }
}