/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.DistritoMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DistritoMgl;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DistritoMglManager {
    
        public List<DistritoMgl> findAll() throws ApplicationException{
        
        List<DistritoMgl> resulList;
        DistritoMglDaoImpl distritoMglDaoImpl = new DistritoMglDaoImpl();
        
        resulList = distritoMglDaoImpl.findAll();        
        
       return resulList;
    }
    
}
