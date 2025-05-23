/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.VetoNodo;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class VetoNodoDaoImpl extends GenericDaoImpl<VetoNodo> {
    
    private static final Logger LOGGER = LogManager.getLogger(VetoNodoDaoImpl.class);

    public boolean vetarNodos(List<NodoMgl> nodosList, Date iniVeto, Date finVeto, String politica, String correo) throws ApplicationException {
        boolean result = false;
        entityManager.getTransaction().begin();
        try {
            VetoNodo vetoNodo;
            for (NodoMgl nd : nodosList) {
                vetoNodo = new VetoNodo();
                vetoNodo.setVetId(BigDecimal.ZERO);
                vetoNodo.setVetPolitica(politica);
                vetoNodo.setVetFechaInicio(iniVeto);
                vetoNodo.setVetFechaFin(finVeto);
                vetoNodo.setVetCorreo(correo);
                vetoNodo.setNodId(nd);
                vetoNodo.setGpoId(nd.getGpoId());
                
                entityManager.persist(vetoNodo);
            }
            entityManager.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            entityManager.getTransaction().rollback();
            throw new ApplicationException(e.getMessage(), e);
        }
        return result;
    }
    
    public List<VetoNodo> validaNodoVetado (String nodo){
        List<VetoNodo> resultList; 
        NodoMgl nodId = new NodoMgl();
        nodId.setNodCodigo(nodo);
        Query query = entityManager.createNamedQuery("VetoNodoMGL.findNodoByIdNodFecha");
        query.setParameter("nodId", nodId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<VetoNodo>)query.getResultList();
        return resultList;
    }
    
    public List<VetoNodo> getNodosVetados (){
        List<VetoNodo> resultList; 
        Query query = entityManager.createNamedQuery("VetoNodoMGL.findVetosActivos");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<VetoNodo>)query.getResultList();
        return resultList;
    }
    
    public List<VetoNodo> findVetos(String politica, Date initDate, Date endDate, BigDecimal ciudad, String tipoVeto) throws ApplicationException{
        List<VetoNodo> resultList; 

        
        String queryStr = "Select v from VetoNodo v where "
                + "(v.vetFechaInicio BETWEEN :vetFechaInicio AND :vetFechaFin "
                + "OR v.vetFechaFin BETWEEN :vetFechaInicio AND :vetFechaFin "
                + "OR (v.vetFechaInicio < :vetFechaInicio AND :vetFechaFin < v.vetFechaFin) ) ";
        if (politica !=null && !politica.isEmpty()){
            queryStr +="AND v.vetPolitica = :vetPolitica ";
        } 
        if (ciudad != null){
            queryStr +="AND v.gpoId = :gpoId ";
        }
        if (tipoVeto != null && !tipoVeto.isEmpty()){
            queryStr += "AND v.vetArea = :vetArea";
        }
        
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("vetFechaInicio", initDate);
        query.setParameter("vetFechaFin", endDate);
        if (politica !=null && !politica.isEmpty()){
            query.setParameter("vetPolitica", politica);
        } 
        if (ciudad != null){
            query.setParameter("gpoId", ciudad);
        }
        if (tipoVeto != null && !tipoVeto.isEmpty()){
            query.setParameter("vetArea", tipoVeto);
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<VetoNodo>)query.getResultList();
        return resultList;
    }
}
