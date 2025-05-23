/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.UnidadGestionMgl;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
public class UnidadGestionMglDaoImpl extends GenericDaoImpl<UnidadGestionMgl> {

    public List<UnidadGestionMgl> findAll() throws ApplicationException {
        List<UnidadGestionMgl> resultList;
        Query query = entityManager.createNamedQuery("UnidadGestionMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<UnidadGestionMgl>) query.getResultList();
        return resultList;
    }
}
