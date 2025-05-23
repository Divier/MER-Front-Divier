/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.services.ws.initialize;

import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.init.ResponseInit;
import com.jlcg.db.init.TaskInit;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author user
 */
public class Initialized {
    
    private static final Logger LOGGER = LogManager.getLogger(Initialized.class);
    
    private static Initialized instance = null;

    private Initialized() {
        initial();
    }
    
    public static Initialized getInstance(){
        if (instance == null){
            instance = new Initialized();
        }
        
        return instance;
    }
    
    
    private void initial() {
        //inicializar Data sources / properties
        ResponseInit response = null;
        InitializeResource initialize = InitializeResource.getInstance("jdbc/Dir");
        try {
            response = initialize.running(TaskInit.LOAD_DATASOURCE);
            if (response.getDatasource() == null) {
                
            } else {
                ManageAccess.init(response.getDatasource());
                response = initialize.running(TaskInit.TEST_CONNECTION);
                if (response.getState()) {
                    response = initialize.running(TaskInit.LOAD_SQL);
                }
            }
        } catch (ExceptionDB | InterruptedException ex) {
            LOGGER.error("Error al momento de inicializar. EX000: " + ex.getMessage(), ex);
        }
    }
    
}
