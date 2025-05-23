/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.PreFichaTxtMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaMgl;
import co.com.claro.mgl.jpa.entities.PreFichaTxtMgl;
import java.util.List;

/**
 *
 * @author User
 */
public class PreFichaTxtMglManager {

    public List<PreFichaTxtMgl> savePreFichaTxtList(PreFichaMgl preFichaMgl, List<PreFichaTxtMgl> prefichaTxtList) throws ApplicationException {
        
        for (PreFichaTxtMgl pft : prefichaTxtList) {
            pft.setPrfId(preFichaMgl.getPrfId());
            pft.setFechaCreacion(preFichaMgl.getFechaCreacion());
            pft.setUsuarioCreacion(preFichaMgl.getUsuarioCreacion());
            pft.setFechaEdicion(preFichaMgl.getFechaEdicion());
            pft.setUsuarioEdicion(preFichaMgl.getUsuarioEdicion());            
            
        }
         PreFichaTxtMglDaoImpl daoImpl = new PreFichaTxtMglDaoImpl();
         
        return daoImpl.create(prefichaTxtList);
    }
}
