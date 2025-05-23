/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GrupoMultivalor;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class GrupoMultvalorDaoImpl extends GenericDaoImpl<GrupoMultivalor>{
    
    public GrupoMultivalor finByGmuNombre (String gmuNombre) throws ApplicationException{
        GrupoMultivalor grupoMultivalorResult = new GrupoMultivalor();
        Query query = entityManager.createNamedQuery("GrupoMultivalor.findByGmuName");
        query.setParameter("gmuNombre", gmuNombre);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        grupoMultivalorResult = (GrupoMultivalor)query.getSingleResult();
        return grupoMultivalorResult;
    }
    
}
