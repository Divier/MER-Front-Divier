package co.com.telmex.catastro.util;

import com.jlcg.db.init.ResponseInit;
import com.jlcg.db.init.SyncInit;

/**
 * Clase InitializeResource
 * Implementa SyncInit
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class InitializeResource implements SyncInit {

    private String resource = null;

    /**
     * 
     * @param resource
     */
    public InitializeResource(String resource) {
        this.resource = resource;
    }

    /**
     * 
     * @param operation
     * @return
     * @throws InterruptedException
     */
    @Override
    public synchronized ResponseInit running(int operation) throws InterruptedException {
        TaskLoad taskLoad = new TaskLoad(operation, resource);
        taskLoad.init();
        taskLoad.getThread().join();
        return taskLoad.getResponse();
    }
}
