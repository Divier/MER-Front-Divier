/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaMglNew;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Impl Dao para operaciones en TEC_PREFICHA_NEW
 * 
 * @author Miguel Barrios Hitss
 * @version 1.1 Rev Miguel Barrios Hitss
 */

public class PreFichaMglNewDaoImpl extends GenericDaoImpl<PreFichaMglNew>{
    private static final Logger LOGGER = LogManager.getLogger(PreFichaMglDaoImpl.class);
    
    @SuppressWarnings("unchecked")
    public List<PreFichaMglNew> getListPrefichaNewByFase(List<String> faseList, int firstResult,
            int maxResults, boolean paginar) {
        
        List<PreFichaMglNew> resultList;
        Query query = entityManager.createNamedQuery("PreFichaMglNew.findByFase");
        
        if (paginar) {
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
        }
        
        query.setParameter("faseList", faseList);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<PreFichaMglNew>)query.getResultList();
        return resultList;
    }
    
    
    @SuppressWarnings("unchecked")
    public List<PreFichaMglNew> getListPrefichaNewByFaseAndDate(List<String> faseList, int firstResult,
            int maxResults, boolean paginar, Date fechaInicial, Date fechaFinal) throws ApplicationException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoLargo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = format.format(new Date());
        List<PreFichaMglNew> resultList;
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
        Query query = entityManager.createNamedQuery("PreFichaMglNew.findByFaseAndDate");

        if (paginar) {
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
        }

        query.setParameter("faseList", faseList);
        query.setParameter("fechaInicial", fechaInicial);
        query.setParameter("fechaFinal", fechaFinal);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<PreFichaMglNew>) query.getResultList();

        return resultList;
    }
    
    public List<PreFichaMglNew> getListPrefichaNewGeoFase() {
        List<PreFichaMglNew> resultList;
        Query query = entityManager.createNamedQuery("PreFichaMglNew.findGeorreferenciada");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<PreFichaMglNew>)query.getResultList();
        return resultList;
    }
    
    public List<PreFichaMglNew> getListFichaNewToCreate(int firstResult,
            int maxResults, boolean paginar) {
        List<PreFichaMglNew> resultList;
        Query query = entityManager.createNamedQuery("PreFichaMglNew.findFichaToCreate");

        if (paginar) {
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<PreFichaMglNew>) query.getResultList();
        return resultList;
    }
    
    @SuppressWarnings("unchecked")
    public List<PreFichaMglNew> getListFichaNewToCreateByDate(int firstResult,
            int maxResults, boolean paginar, Date fechaInicial, Date fechaFinal) throws ApplicationException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(new Date());
        List<PreFichaMglNew> resultList;
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
        Query query = entityManager.createNamedQuery("PreFichaMglNew.findFichaToCreateByDate");

        if (paginar) {
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
        }
        
        query.setParameter("fechaInicial", fechaInicial);
        query.setParameter("fechaFinal", fechaFinal);       
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<PreFichaMglNew>) query.getResultList();
        return resultList;
    }

    public List<PreFichaMglNew> getListFichaNewToValidate() {
        List<PreFichaMglNew> resultList;
        Query query = entityManager.createNamedQuery("PreFichaMglNew.findFichaToValidate");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<PreFichaMglNew>)query.getResultList();
        return resultList;
    }
}
