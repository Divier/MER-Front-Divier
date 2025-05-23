/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.cron;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.delegate.HhppDelegate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Orlando Velasquez
 */
public class UpdateHhppCronJob implements Job {
    
    private static final Logger LOGGER = LogManager.getLogger(UpdateHhppCronJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            HhppDelegate.updateHhppMgl();
        } catch (ApplicationException ex) {
            LOGGER.error("Error al momento de ejecutar el Job de actualización de HHPP. EX000: " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al momento de ejecutar el Job de actualización de HHPP. EX000: " + ex.getMessage(), ex);
        }

    }

}
