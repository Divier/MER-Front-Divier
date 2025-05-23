package co.com.telmex.catastro.cron;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.delegate.HhppDelegate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Clase MyJob
 * implementa Job
 *
 * @author 	Carlos Villamil
 * @version	1.0
 */
public class MyJob implements Job {
    
    private static final Logger LOGGER = LogManager.getLogger(MyJob.class);

    /**
     * 
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            HhppDelegate.updateEstadosRR();

        } catch (ApplicationException e) {
            LOGGER.error("Error en execute " + e.getMessage(),e);
        }catch (Exception e) {
            LOGGER.error("Error en execute " + e.getMessage(),e);
        }

    }
}
