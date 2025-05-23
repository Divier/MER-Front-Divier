/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.services.util;

import com.jlcg.db.AccessData;
import com.jlcg.db.exept.ExceptionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author User
 */
public class DireccionUtil {
    
    private static final Logger LOGGER = LogManager.getLogger(DireccionUtil.class);
    
    public static void closeConnection(AccessData adb){        
        try {
            if (adb != null){
                adb.clear();
            }
        } catch (ExceptionDB e) {
            LOGGER.error("Ocurrio un error en DireccionUtil intentando cerrar la conexion "+e.getMessage(), e);
        }        
    }
    
}
