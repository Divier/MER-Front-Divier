/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
public class CmtHorarioRestriccionMglDaoImpl extends GenericDaoImpl<CmtHorarioRestriccionMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtHorarioRestriccionMglDaoImpl.class);

    public boolean deleteByCompania(CmtCompaniaMgl compania,String user, int perfil) throws ApplicationException { 
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("UPDATE CmtHorarioRestriccionMgl c SET c.estadoRegistro= :estadoRegistro,c.fechaEdicion= :fechaEdicion,c.usuarioEdicion= :usuarioEdicion,c.perfilEdicion= :perfilEdicion  WHERE  c.companiaObj = :compania "); 
            query.setParameter("compania", compania);
            query.setParameter("estadoRegistro", 0);
            query.setParameter("fechaEdicion", new Date());
            query.setParameter("usuarioEdicion", user);
            query.setParameter("perfilEdicion", perfil);
            
            query.executeUpdate();
            entityManager.getTransaction().commit();
            return true; 
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg); 
            return false;
        } 
    }
    
    
    public boolean deleteByCuentaMatrizId(BigDecimal cuentaMatrizId,String user,int perfil) throws ApplicationException { 
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("UPDATE CmtHorarioRestriccionMgl  c SET c.estadoRegistro=  :estadoRegistro,c.fechaEdicion= :fechaEdicion,c.usuarioEdicion= :usuarioEdicion,c.perfilEdicion= :perfilEdicion  WHERE  c.cuentaMatrizObj.cuentaMatrizId = :cuentaMatrizId"); 
            query.setParameter("cuentaMatrizId", cuentaMatrizId);
            query.setParameter("estadoRegistro", 0); 
            query.setParameter("fechaEdicion", new Date());
            query.setParameter("usuarioEdicion", user);
            query.setParameter("perfilEdicion", perfil);
            query.executeUpdate();            
            entityManager.getTransaction().commit();
            return true; 
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg); 
            return false;
        } 
    }
   
    public List<CmtHorarioRestriccionMgl> findAll() throws ApplicationException {
        List<CmtHorarioRestriccionMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtHorarioRestriccionMgl.findAll");
        resultList = (List<CmtHorarioRestriccionMgl>) query.getResultList();
        return resultList;
    }
    
    public List<CmtHorarioRestriccionMgl> findByHorarioCompania(CmtCompaniaMgl compania) throws ApplicationException { 
        List<CmtHorarioRestriccionMgl> resultList; 
        Query query = entityManager.createNamedQuery("CmtHorarioRestriccionMgl.findByCompania");
        query.setParameter("compania", compania);
        query.setParameter("estadoRegistro", 1);
        resultList = query.getResultList();
        return resultList; 
    }
    
    public List<CmtHorarioRestriccionMgl> findByHorarioCuentaMatrizId(BigDecimal cuentaMatrizId) throws ApplicationException { 
        List<CmtHorarioRestriccionMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM CmtHorarioRestriccionMgl c WHERE c.cuentaMatrizObj.cuentaMatrizId = :cuentaMatrizId and c.estadoRegistro =1 ");
        query.setParameter("cuentaMatrizId", cuentaMatrizId); 
        resultList = (List<CmtHorarioRestriccionMgl>) query.getResultList();
        return resultList; 
    }
    
      public List<CmtHorarioRestriccionMgl> findByHorarioSubEdificioId(BigDecimal subedificio) throws ApplicationException { 
        List<CmtHorarioRestriccionMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM CmtHorarioRestriccionMgl c WHERE c.subEdificioObj.subEdificioId = :subEdificioId and c.estadoRegistro =1 ");
        query.setParameter("subEdificioId", subedificio); 
        resultList = (List<CmtHorarioRestriccionMgl>) query.getResultList();
        return resultList; 
    }
    
    public List<CmtHorarioRestriccionMgl> findBySolicitud(CmtSolicitudCmMgl solicitudCm) throws ApplicationException { 
        List<CmtHorarioRestriccionMgl> resultList; 
        Query query = entityManager.createNamedQuery("CmtHorarioRestriccionMgl.findBySolicitud");
        query.setParameter("solicitudCm", solicitudCm);
        query.setParameter("estadoRegistro", 1);
        resultList = (List<CmtHorarioRestriccionMgl>)query.getResultList();
        return resultList; 
    }
    
    public boolean deleteBySolicitud(CmtSolicitudCmMgl solicitudCm,String user, int perfil) throws ApplicationException { 
        try {
            Query query = entityManager.createNamedQuery("CmtHorarioRestriccionMgl.deleteBySolicitud");
            query.setParameter("solicitudCm", solicitudCm);
            query.setParameter("estadoRegistro", 0);
            query.setParameter("fechaEdicion", new Date());
            query.setParameter("usuarioEdicion", user);
            query.setParameter("perfilEdicion", perfil);
            
            entityManager.getTransaction().begin();
            query.executeUpdate();
            entityManager.getTransaction().commit();
            return true; 
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg); 
            return false;
        } 
    }
    
    public List<CmtHorarioRestriccionMgl> findByVt(CmtVisitaTecnicaMgl vt) throws ApplicationException { 
        List<CmtHorarioRestriccionMgl> resultList; 
        Query query = entityManager.createNamedQuery("CmtHorarioRestriccionMgl.findByVt");
        query.setParameter("vt", vt);
        query.setParameter("estadoRegistro", 1);
        resultList = (List<CmtHorarioRestriccionMgl>)query.getResultList();
        return resultList; 
    }
    
    public boolean deleteByVt(CmtVisitaTecnicaMgl vt,String user, int perfil) throws ApplicationException { 
        try {
            Query query = entityManager.createNamedQuery("CmtHorarioRestriccionMgl.deleteByVt");
            query.setParameter("vt", vt);
            query.setParameter("estadoRegistro", 0);
            query.setParameter("fechaEdicion", new Date());
            query.setParameter("usuarioEdicion", user);
            query.setParameter("perfilEdicion", perfil);
            
            entityManager.getTransaction().begin();
            query.executeUpdate();
            entityManager.getTransaction().commit();
            return true; 
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg); 
            return false;
        } 
    }
}
