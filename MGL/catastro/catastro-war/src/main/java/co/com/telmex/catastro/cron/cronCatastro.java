package co.com.telmex.catastro.cron;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Clase cronCatastro
 * Extiende de HttpServlet
 *
 * @author 	Deiver Rovira.
 * @version	1.0
 */
public class cronCatastro extends HttpServlet {
    
    private static final Logger LOGGER = LogManager.getLogger(cronCatastro.class);

    /**
     * 
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        try {
            CronScheluder objPlugin = new CronScheluder();
        } catch (Exception ex) {
            LOGGER.error("Error al momento de instanciar el cron de catastro. EX000: " + ex.getMessage(), ex);
        }

    }
}
