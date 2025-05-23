/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoHhppRedMgl;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
public class TipoHhppRedMglDaoImpl extends GenericDaoImpl<TipoHhppRedMgl> {

    public List<TipoHhppRedMgl> findAll() throws ApplicationException {
        List<TipoHhppRedMgl> resultList;
        Query query = entityManager.createNamedQuery("TipoHhppRedMgl.findAll");
        resultList = (List<TipoHhppRedMgl>) query.getResultList();
        return resultList;
    }
}
