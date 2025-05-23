/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.PreFichaTxtMglNewDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaMglNew;
import co.com.claro.mgl.jpa.entities.PreFichaTxtMglNew;
import java.util.List;

/**
 * Manager para almacenar en TEC_PREFICHA_TXT
 * 
 * @author Miguel Barrios Hitss
 * @version 1.1 Rev Miguel Barrios Hitss
 */
public class PreFichaTxtMglNewManager {

    public List<PreFichaTxtMglNew> savePreFichaTxtNewList(PreFichaMglNew preFichaMgl, List<PreFichaTxtMglNew> prefichaTxtList) throws ApplicationException {
        
        for (PreFichaTxtMglNew pft : prefichaTxtList) {
            pft.setPrfId(preFichaMgl.getPrfId());
            pft.setFechaCreacion(preFichaMgl.getFechaCreacion());
            pft.setUsuarioCreacion(preFichaMgl.getUsuarioCreacion());
            pft.setFechaEdicion(preFichaMgl.getFechaEdicion());
            pft.setUsuarioEdicion(preFichaMgl.getUsuarioEdicion());            
            
        }
         PreFichaTxtMglNewDaoImpl daoImpl = new PreFichaTxtMglNewDaoImpl();
         
        return daoImpl.create(prefichaTxtList);
    }
}
