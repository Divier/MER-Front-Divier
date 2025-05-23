/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoHhppConexionMgl;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
public class TipoHhppConexionMglDaoImpl extends GenericDaoImpl<TipoHhppConexionMgl> {

    public List<TipoHhppConexionMgl> findAll() throws ApplicationException {
        List<TipoHhppConexionMgl> resultList;
        Query query = entityManager.createNamedQuery("TipoHhppConexionMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<TipoHhppConexionMgl>) query.getResultList();
        return resultList;
    }
}
