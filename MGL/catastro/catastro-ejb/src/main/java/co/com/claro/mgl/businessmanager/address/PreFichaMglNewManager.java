/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.PreFichaMglNewDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaMglNew;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Manager para operaciones en TEC_PREFICHA_NEW
 * 
 * @author Miguel Barrios Hitss
 * @version 1.1 Rev Miguel Barrios Hitss
 */
public class PreFichaMglNewManager {
        PreFichaMglNewDaoImpl daoImpl = new PreFichaMglNewDaoImpl();

    public PreFichaMglNew savePrefichaNew(PreFichaMglNew preFichaMgl) throws ApplicationException {        
        return daoImpl.create(preFichaMgl);
    }
    
    public List<PreFichaMglNew> getListPrefichaNewByFase(List<String> faseList, int paginaSelected,
            int maxResults, boolean paginar) throws ApplicationException {

        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return daoImpl.getListPrefichaNewByFase(faseList, firstResult, maxResults, paginar);
    }

    public List<PreFichaMglNew> getListPrefichaNewByFaseAndDate(List<String> faseList, int paginaSelected,
            int maxResults, boolean paginar, Date fechaInicial, Date fechaFinal) throws ApplicationException {

        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return daoImpl.getListPrefichaNewByFaseAndDate(faseList, firstResult, maxResults, paginar, fechaInicial, fechaFinal);
    }    
    
    public PreFichaMglNew updatePrefichaNew (PreFichaMglNew preFichaMgl) throws ApplicationException {
        return daoImpl.update(preFichaMgl);
    }
    
    public boolean deletePrefichaNew (PreFichaMglNew preFichaMgl) throws ApplicationException {
        return daoImpl.delete(preFichaMgl);
    }
    
    public List<PreFichaMglNew> getListPrefichaNewGeoFase() throws ApplicationException{
        return daoImpl.getListPrefichaNewGeoFase();
    }
    
    public List<PreFichaMglNew> getListFichaNewToCreate(int paginaSelected,
            int maxResults, boolean paginar) throws ApplicationException {

        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return daoImpl.getListFichaNewToCreate(firstResult, maxResults, paginar);
    }
    
    public List<PreFichaMglNew> getListFichaNewToCreateByDate(int paginaSelected,
            int maxResults, boolean paginar, Date fechaInicial, Date fechaFinal) throws ApplicationException {

        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return daoImpl.getListFichaNewToCreateByDate(firstResult, maxResults, paginar, fechaInicial, fechaFinal);
    }
    
    public List<PreFichaMglNew> getListFichaNewToValidate() throws ApplicationException{
        return daoImpl.getListFichaNewToValidate();
    }
    
    public PreFichaMglNew getPreFichaNewById(BigDecimal idPreficha) throws ApplicationException{
        return daoImpl.find(idPreficha);
    }
}
