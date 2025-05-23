package co.com.claro.launcher;

import com.jlcg.db.init.ResponseInit;
import com.jlcg.db.init.SyncInit;
import javax.naming.InitialContext;

/**
 * Clase InitializeResource
 * Implementa SyncInit
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class InitializeResource implements SyncInit {

    private String resource = null;
    private InitialContext ic = null;

    /**
     * 
     * @param ic
     * @param resource
     */
    public InitializeResource(InitialContext ic,String resource) {
        this.resource = resource;
        this.ic = ic;
    }

    /**
     * 
     * @param operation
     * @return
     * @throws InterruptedException
     */
    @Override
    public synchronized ResponseInit running(int operation) throws InterruptedException {
        TaskLoad taskLoad = new TaskLoad(ic, operation, resource);
        taskLoad.init();
        taskLoad.getThread().join();
        return taskLoad.getResponse();
    }
}
