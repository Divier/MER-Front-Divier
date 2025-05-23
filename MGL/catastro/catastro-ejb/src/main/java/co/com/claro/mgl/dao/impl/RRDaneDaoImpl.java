/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RRDane;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class RRDaneDaoImpl extends GenericDaoImpl<RRDane> {

    public RRDane findByCodCiudad(String codCiudad) throws ApplicationException {
        RRDane result;
        Query query = entityManager.createNamedQuery("RRDane.findByCodCidad");
        query.setParameter("codCiudad", codCiudad);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        result = (RRDane) query.getSingleResult();
        return result;
    }

    public List<RRDane> findByCodDane(String codigoDane){
        List<RRDane> resultList;
        Query query = entityManager.createNamedQuery("RRDane.findByDane"); 
        query.setParameter("codDane", codigoDane);
        query.setParameter("estado", "A");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<RRDane>) query.getResultList();
        if (resultList == null) {
            resultList = new ArrayList<RRDane>();
        }
        return resultList;
    }
}
