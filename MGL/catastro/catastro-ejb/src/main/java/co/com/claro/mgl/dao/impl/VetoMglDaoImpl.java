/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.VetoMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Juan David Hernandez
 */
public class VetoMglDaoImpl extends GenericDaoImpl <VetoMgl>{
    
    private static final Logger LOGGER = LogManager.getLogger(VetoMglDaoImpl.class);
    
 
     public List<VetoMgl> findAll() throws ApplicationException {
        try {
            Query query = entityManager.createNamedQuery("VetoMgl.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return (List<VetoMgl>) query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }   
     
    public int countAllVetoList() throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }           
            int resulList;
            String sql = "SELECT count(1) FROM VetoMgl"
                    + " n Where n.estadoRegistro = 1";
            Query query = entityManager.createQuery(sql);       
            resulList = query.getSingleResult() == null ? 0
                    : ((Long) query.getSingleResult()).intValue();
             return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }  
     
    public List<VetoMgl> findAllVetoPaginadaList(int firstResult,
            int maxResults) throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            List<VetoMgl> resulList;
            String sql = "SELECT n FROM VetoMgl n Where n.estadoRegistro = 1"
                    + " ORDER BY n.vetoId DESC";
            Query query = entityManager.createQuery(sql);
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
            resulList = (List<VetoMgl>) query.getResultList();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
    public List<VetoMgl> validarVigenciaVeto(Date fecha, String tipoVeto, BigDecimal vetoId) throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            List<VetoMgl> resulList;
            String sql = "SELECT n FROM VetoMgl n Where n.vetoId =:vetoId AND "
                    + " :fechaActual BETWEEN n.fechaInicio AND n.fechaFin"
                    + " AND (n.tipoVeto =:tipoVeto OR n.tipoVeto =:tipoVetoHhppVenta) AND n.estadoRegistro =:estado  ";
            Query query = entityManager.createQuery(sql);
            query.setParameter("fechaActual", fecha);
            query.setParameter("vetoId", vetoId);
            query.setParameter("estado", 1);
            query.setParameter("tipoVeto", tipoVeto);
            query.setParameter("tipoVetoHhppVenta", Constant.VETO_TIPO_CREACION_HHPP_VENTA);
            resulList = (List<VetoMgl>) query.getResultList();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
    public VetoMgl findByPolitica(String politica) throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            List<VetoMgl> resulList;
            String sql = "SELECT n FROM VetoMgl n Where "
                    + " n.numeroPolitica =:politica"
                    + " AND n.estadoRegistro =:estado  ";
            Query query = entityManager.createQuery(sql);
            query.setParameter("politica", politica);
            query.setParameter("estado", 1);
            
            resulList = (List<VetoMgl>) query.getResultList();
            if(resulList==null || resulList.isEmpty()){
                return null;
            }
            return resulList.get(0);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
}
