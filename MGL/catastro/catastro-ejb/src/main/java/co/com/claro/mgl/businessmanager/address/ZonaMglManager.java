/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.ZonaMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ZonaMgl;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ZonaMglManager {
    
        public List<ZonaMgl> findAll() throws ApplicationException{
        
        List<ZonaMgl> resulList;
        ZonaMglDaoImpl zonaMglDaoImpl = new ZonaMglDaoImpl();
        
        resulList = zonaMglDaoImpl.findAll();        
        
       return resulList;
    }
    
}
