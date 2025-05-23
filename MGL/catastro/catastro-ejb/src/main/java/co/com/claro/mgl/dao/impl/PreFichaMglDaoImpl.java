/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class PreFichaMglDaoImpl extends GenericDaoImpl<PreFichaMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(PreFichaMglDaoImpl.class);
    

    @SuppressWarnings("unchecked")
    public List<PreFichaMgl> getListPrefichaByFase(List<String> faseList, int firstResult,
            int maxResults, boolean paginar) {
        
        List<PreFichaMgl> resultList;
        Query query = entityManager.createNamedQuery("PreFichaMgl.findByFase");
        
        if (paginar) {
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
        }
        
        query.setParameter("faseList", faseList);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<PreFichaMgl>)query.getResultList();
        return resultList;
    }
    
    
    @SuppressWarnings("unchecked")
    public List<PreFichaMgl> getListPrefichaByFaseAndDate(List<String> faseList, int firstResult,
            int maxResults, boolean paginar, Date fechaInicial, Date fechaFinal) throws ApplicationException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoLargo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = format.format(new Date());
        List<PreFichaMgl> resultList;
        Calendar calendar = Calendar.getInstance();
        try {
            if (fechaInicial == null) {

                fechaInicial = format.parse("1000-01-01");

            }
            if (fechaFinal == null) {
                fechaFinal = format.parse("9999-01-01");
            } else {
                calendar.setTime(fechaFinal);
                calendar.add(Calendar.DATE, 1);
                calendar.add(Calendar.SECOND, -1);
                fechaFinal=calendar.getTime();               
                        
            }
        } catch (ParseException ex) {
            LOGGER.error("Error en getListPrefichaByFaseAndDate. ",ex);
            throw new ApplicationException(ex);
        }
        Query query = entityManager.createNamedQuery("PreFichaMgl.findByFaseAndDate");

        if (paginar) {
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
        }

        query.setParameter("faseList", faseList);
        query.setParameter("fechaInicial", fechaInicial);
        query.setParameter("fechaFinal", fechaFinal);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<PreFichaMgl>) query.getResultList();

        return resultList;
    }
    
    public List<PreFichaMgl> getListPrefichaGeoFase() {
        List<PreFichaMgl> resultList;
        Query query = entityManager.createNamedQuery("PreFichaMgl.findGeorreferenciada");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<PreFichaMgl>)query.getResultList();
        return resultList;
    }
    
    public List<PreFichaMgl> getListFichaToCreate(int firstResult,
            int maxResults, boolean paginar) {
        List<PreFichaMgl> resultList;
        Query query = entityManager.createNamedQuery("PreFichaMgl.findFichaToCreate");

        if (paginar) {
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<PreFichaMgl>) query.getResultList();
        return resultList;
    }
    
    @SuppressWarnings("unchecked")
    public List<PreFichaMgl> getListFichaToCreateByDate(int firstResult,
            int maxResults, boolean paginar, Date fechaInicial, Date fechaFinal) throws ApplicationException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(new Date());
        List<PreFichaMgl> resultList;
        Calendar calendar = Calendar.getInstance();
        try {
            if (fechaInicial == null) {

                fechaInicial = format.parse("1000-01-01");

            }
            if (fechaFinal == null) {
                fechaFinal = format.parse("9999-01-01");
            } else {
                calendar.setTime(fechaFinal);
                calendar.add(Calendar.DATE, 1);
                calendar.add(Calendar.SECOND, -1);
                fechaFinal=calendar.getTime();               
                        
            }
        } catch (ParseException ex) {
            LOGGER.error("Error en getListFichaToCreateByDate. ", ex);
            throw new ApplicationException(ex);
        }
        Query query = entityManager.createNamedQuery("PreFichaMgl.findFichaToCreateByDate");

        if (paginar) {
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
        }
        
        query.setParameter("fechaInicial", fechaInicial);
        query.setParameter("fechaFinal", fechaFinal);       
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<PreFichaMgl>) query.getResultList();
        return resultList;
    }

    public List<PreFichaMgl> getListFichaToValidate() {
        List<PreFichaMgl> resultList;
        Query query = entityManager.createNamedQuery("PreFichaMgl.findFichaToValidate");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<PreFichaMgl>)query.getResultList();
        return resultList;
    }
}
