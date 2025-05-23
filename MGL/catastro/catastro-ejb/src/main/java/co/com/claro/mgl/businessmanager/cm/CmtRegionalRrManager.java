/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtRegionalRrDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author valbuenayf
 */
public class CmtRegionalRrManager {

    private static final Logger LOGGER = LogManager.getLogger(CmtRegionalRrManager.class);

    /**
     * valbuenayf Metodo para consultar todas las reginales
     *
     * @return
     */
    public List<CmtRegionalRr> findAllRegional() {
        List<CmtRegionalRr> resultList;
        CmtRegionalRrDaoImpl listaRegional;
        try {
            listaRegional = new CmtRegionalRrDaoImpl();
            resultList = listaRegional.findAllItems();

        } catch (ApplicationException e) {
            LOGGER.error("Error findAllRegional de CmtRegionalRrManager " + e.getMessage());
            return null;
        }
        return resultList;
    }
    /**
     * valbuenayf Metodo para consultar todas las reginales
     *
     * @return
     */
    public List<CmtRegionalRr> findAllRegionalActive() {
        List<CmtRegionalRr> resultList;
        CmtRegionalRrDaoImpl listaRegional;
        try {
            listaRegional = new CmtRegionalRrDaoImpl();
            resultList = listaRegional.findAllItemsActive();

        } catch (ApplicationException e) {
            LOGGER.error("Error findAllRegional de CmtRegionalRrManager " + e.getMessage());
            return null;
        }
        return resultList;
    }
    
    /**
     * bocanegravm Metodo para consultar una regional por Id
     *
     * @param idRegional
     * @return CmtRegionalRr
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public CmtRegionalRr findRegionalById(BigDecimal idRegional) 
            throws ApplicationException{
        
        CmtRegionalRr result;
        CmtRegionalRrDaoImpl consulta;
        try {
            consulta = new CmtRegionalRrDaoImpl();
            result = consulta.find(idRegional);

        } catch (ApplicationException e) {
            LOGGER.error("Error findRegionalById de CmtRegionalRrManager " + e.getMessage());
            return null;
        }
        return result;
    }
    
       /**
     * bocanegravm Metodo para consultar una regional por codigo
     *
     * @param codigo
     * @return CmtRegionalRr
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public CmtRegionalRr findRegionalByCod(String codigo)
            throws ApplicationException {

        CmtRegionalRrDaoImpl consulta = new CmtRegionalRrDaoImpl();
        return consulta.findRegionalByCod(codigo);
    }
    
    /**
     * bocanegravm Metodo para consultar todas las regionales en BD
     *
     * @return List<CmtRegionalRr>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtRegionalRr> findAllRegionales()
            throws ApplicationException {

        CmtRegionalRrDaoImpl consulta = new CmtRegionalRrDaoImpl();
        return consulta.findAllRegionales();
    }
}
