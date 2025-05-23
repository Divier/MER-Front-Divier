/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.OtHhppTecnologiaMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Juan David Hernandez
 */
public class OtHhppTecnologiaMglDaoImpl extends GenericDaoImpl<OtHhppTecnologiaMgl> {

    public List<OtHhppTecnologiaMgl> findOtHhppTecnologiaByOtHhppId(BigDecimal otId)
            throws ApplicationException {
        List<OtHhppTecnologiaMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "OtHhppTecnologiaMgl c WHERE c.otHhppId.otHhppId = :otId "
                + " AND c.estadoRegistro = 1");
        query.setParameter("otId", otId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<OtHhppTecnologiaMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    public List<OtHhppTecnologiaMgl> findTecnologiasViables(BigDecimal otId)
            throws ApplicationException {
        List<OtHhppTecnologiaMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "OtHhppTecnologiaMgl c WHERE c.otHhppId.otHhppId = :otId "
                + " AND c.estadoRegistro = 1 AND c.tecnologiaViable = 'Y'");
        query.setParameter("otId", otId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<OtHhppTecnologiaMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }
    
    public List<OtHhppTecnologiaMgl> findTecnologias(BigDecimal otId)
            throws ApplicationException {
        List<OtHhppTecnologiaMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "OtHhppTecnologiaMgl c WHERE c.otHhppId.otHhppId = :otId "
                + " AND c.estadoRegistro = 1 ");
        query.setParameter("otId", otId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<OtHhppTecnologiaMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

}
