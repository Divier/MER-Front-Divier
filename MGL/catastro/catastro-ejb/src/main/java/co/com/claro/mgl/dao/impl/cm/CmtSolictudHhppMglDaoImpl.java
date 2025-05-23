/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import java.util.List;
import javax.persistence.Query;

/**
 * Dao
 * @author gilaj
 */
public class CmtSolictudHhppMglDaoImpl extends GenericDaoImpl<CmtSolicitudHhppMgl>{
    
    /**
     * Lista toda la informacion de la solicitud de Hhpp.
     *
     * @author Antonio Gil@return Lista
     * @return 
     * @throws ApplicationException
     */
    public List<CmtSolicitudHhppMgl> findAll() throws ApplicationException{
        List<CmtSolicitudHhppMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtSolicitudHhppMgl.findAll");
        resultList = (List<CmtSolicitudHhppMgl>) query.getResultList();
        return resultList;
    }
    
    /**
     * Lista toda la informacion de los HHPP.
     *
     * @author Antonio Gil     * @param HHPP
     * @param hhpId
     * @return Lista
     * @throws ApplicationException
     */
    public List<CmtSolicitudHhppMgl> findByHhpp(HhppMgl hhpId) throws ApplicationException{
        List<CmtSolicitudHhppMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtSolicitudHhppMgl.findByHhpp");
        query.setParameter("hhppMglObj", hhpId);
        resultList = (List<CmtSolicitudHhppMgl>) query.getResultList();
        return resultList;
    }
    
    /**
     * Lista toda la informacion de la solicitud creada.
     *
     * @author Antonio Gil
     * @param solicitudId
     * @return Lista
     * @throws ApplicationException
     */
    public List<CmtSolicitudHhppMgl> findBySolicitud(CmtSolicitudCmMgl solicitudId) throws ApplicationException{
        List<CmtSolicitudHhppMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtSolicitudHhppMgl.findBySolicitud");
        query.setParameter("cmtSolicitudCmMglObj", solicitudId);
        resultList = (List<CmtSolicitudHhppMgl>) query.getResultList();
        return resultList;
    }
    
    /**
     * Lista toda la informacion de la solicitud Hhpp con los datos del subEdificio
     *
     * @author Antonio Gil
     * @param subEdificioId
     * @return Lista
     * @throws ApplicationException
     */
    public List<CmtSolicitudHhppMgl> findBySubEdificio(CmtSubEdificioMgl subEdificioId) throws ApplicationException{
        List<CmtSolicitudHhppMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtSolicitudHhppMgl.findBySubEdificio");
        query.setParameter("cmtSubEdificioMglObj", subEdificioId);
        resultList = (List<CmtSolicitudHhppMgl>) query.getResultList();
        return resultList;
    }
}
