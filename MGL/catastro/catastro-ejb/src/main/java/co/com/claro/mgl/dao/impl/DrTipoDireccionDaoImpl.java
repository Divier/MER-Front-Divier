/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrTipoDireccion;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author camargomf
 */
public class DrTipoDireccionDaoImpl extends GenericDaoImpl<DrTipoDireccion> {

    public List<DrTipoDireccion> findAll() throws ApplicationException {

        List<DrTipoDireccion> resultList;
        TypedQuery<DrTipoDireccion> query = entityManager.createNamedQuery("DrTipoDireccion.findAll", DrTipoDireccion.class);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = query.getResultList();
        return resultList;
    }

}
