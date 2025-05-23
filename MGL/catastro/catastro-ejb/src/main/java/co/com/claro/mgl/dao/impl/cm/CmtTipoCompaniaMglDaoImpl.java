/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoCompaniaMgl;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtTipoCompaniaMglDaoImpl extends GenericDaoImpl<CmtTipoCompaniaMgl> {

    public List<CmtTipoCompaniaMgl> findAll() throws ApplicationException {
        List<CmtTipoCompaniaMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtTipoCompaniaMgl.findAll");
        resultList = (List<CmtTipoCompaniaMgl>) query.getResultList();
        return resultList;
    }
    
    
    /**
     * Busca un tipo de compañia por el nombre
     * @author Victor Bocanegra 
     * @param nombreTipo
     * @return CmtTipoCompaniaMgl
     * @throws ApplicationException
     */
      public CmtTipoCompaniaMgl findByNombreTipo(String nombreTipo) throws ApplicationException {
        CmtTipoCompaniaMgl result;
        Query query = entityManager.createNamedQuery("CmtTipoCompaniaMgl.findByNombreTipo");
        query.setParameter("nombreTipo", nombreTipo);
        result = (CmtTipoCompaniaMgl) query.getSingleResult();
        return result;
    }
}