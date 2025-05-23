/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoHhppMgl;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class TipoHhppMglDaoImpl extends GenericDaoImpl<TipoHhppMgl> {

    public List<TipoHhppMgl> findAll() throws ApplicationException {
        List<TipoHhppMgl> resultList;
        Query query = entityManager.createNamedQuery("TipoHhppMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<TipoHhppMgl>) query.getResultList();
        return resultList;
    }
}
