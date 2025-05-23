/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.PreFichaMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaMgl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author User
 */
public class PreFichaMglManager {

    PreFichaMglDaoImpl daoImpl = new PreFichaMglDaoImpl();

    public PreFichaMgl savePreficha(PreFichaMgl preFichaMgl) throws ApplicationException {        
        return daoImpl.create(preFichaMgl);

    }
    
    public List<PreFichaMgl> getListPrefichaByFase(List<String> faseList, int paginaSelected,
            int maxResults, boolean paginar) throws ApplicationException {

        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return daoImpl.getListPrefichaByFase(faseList, firstResult, maxResults, paginar);
    }

    public List<PreFichaMgl> getListPrefichaByFaseAndDate(List<String> faseList, int paginaSelected,
            int maxResults, boolean paginar, Date fechaInicial, Date fechaFinal) throws ApplicationException {

        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return daoImpl.getListPrefichaByFaseAndDate(faseList, firstResult, maxResults, paginar, fechaInicial, fechaFinal);
    }    
    
    public PreFichaMgl updatePreficha (PreFichaMgl preFichaMgl) throws ApplicationException {
        return daoImpl.update(preFichaMgl);
    }
    
    public boolean deletePreficha (PreFichaMgl preFichaMgl) throws ApplicationException {
        return daoImpl.delete(preFichaMgl);
    }
    
    public List<PreFichaMgl> getListPrefichaGeoFase() throws ApplicationException{
        return daoImpl.getListPrefichaGeoFase();
    }
    
    public List<PreFichaMgl> getListFichaToCreate(int paginaSelected,
            int maxResults, boolean paginar) throws ApplicationException {

        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return daoImpl.getListFichaToCreate(firstResult, maxResults, paginar);
    }
    
    public List<PreFichaMgl> getListFichaToCreateByDate(int paginaSelected,
            int maxResults, boolean paginar, Date fechaInicial, Date fechaFinal) throws ApplicationException {

        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return daoImpl.getListFichaToCreateByDate(firstResult, maxResults, paginar, fechaInicial, fechaFinal);
    }
    
    public List<PreFichaMgl> getListFichaToValidate() throws ApplicationException{
        return daoImpl.getListFichaToValidate();
    }
    
    public PreFichaMgl getPreFichaById(BigDecimal idPreficha) throws ApplicationException{
        return daoImpl.find(idPreficha);
    }
   
}
