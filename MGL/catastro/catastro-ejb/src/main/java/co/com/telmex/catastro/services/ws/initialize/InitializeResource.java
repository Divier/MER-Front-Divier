/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.services.ws.initialize;

import com.jlcg.db.init.ResponseInit;

/**
 *
 * @author user
 */
public class InitializeResource {
    
    private String resource = null;
    private static InitializeResource instance = null;

    /**
     * 
     * @param resource
     */
    private InitializeResource(String resource) {
        this.resource = resource;
    }
    
    private InitializeResource() {
        
    }
    
    public static InitializeResource getInstance(String resource){
        if (instance == null){
            instance = new InitializeResource (resource);
        }
        
        return instance;
    }

    /**
     * 
     * @param operation
     * @return
     * @throws InterruptedException
     */
    public synchronized ResponseInit running(int operation) throws InterruptedException {
        TaskLoad taskLoad = new TaskLoad(operation, resource);
        taskLoad.init();
        taskLoad.getThread().join();
        return taskLoad.getResponse();
    }
    
}
