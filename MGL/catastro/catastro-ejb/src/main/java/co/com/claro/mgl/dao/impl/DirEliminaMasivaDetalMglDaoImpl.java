/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DirEliminaMasivaDetalMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class DirEliminaMasivaDetalMglDaoImpl extends GenericDaoImpl<DirEliminaMasivaDetalMgl> {

    public List<DirEliminaMasivaDetalMgl> findAll() throws ApplicationException {
        List<DirEliminaMasivaDetalMgl> resultList;
        Query query = entityManager.createNamedQuery("DirEliminaMasivaDetalMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<DirEliminaMasivaDetalMgl>) query.getResultList();
        return resultList;
    }

    public List<DirEliminaMasivaDetalMgl> findByLemId(BigDecimal lemId) throws ApplicationException {
        List<DirEliminaMasivaDetalMgl> resultList;
        Query query = entityManager.createQuery("SELECT d FROM DirEliminaMasivaDetalMgl d WHERE d.lemID = :lemID");
        query.setParameter("lemID", lemId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<DirEliminaMasivaDetalMgl>) query.getResultList();
        return resultList;
    }
}
