package co.com.telmex.catastro.cron;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Clase StartCron
 * Implementa ServeletContextListener
 *
 * @author 	Deiver Rovira.
 * @version	1.0
 */
public class StartCron implements ServletContextListener {

    
    private static final Logger LOGGER = LogManager.getLogger(StartCron.class);
    /**
     * 
     * @param arg0
     */
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        LOGGER.error("Stopping Application successfully");
    }

    /**
     * 
     * @param arg0
     */
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        LOGGER.error("Initializing Application successfully");

        try {
            CronScheluder objPlugin = new CronScheluder();
        } catch (Exception ex) {
            LOGGER.error("Error al momento de inicializar el contexto. EX000: " + ex.getMessage(), ex);
        }
    }
}
