package co.com.telmex.catastro.cron;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ParametrosTareasProgramadas;
import co.com.telmex.catastro.services.util.ResourceEJB;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

/**
 * Clase CronScheluder
 *
 * @author Carlos Villamil.
 * @version	1.0
 */
public class CronScheluder {

    private static final Logger LOGGER = LogManager.getLogger(CronScheluder.class);

    /**
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public CronScheluder() throws ApplicationException {
        ResourceEJB recursos = new ResourceEJB();
        //Tarea Programada para actualizacion de Hhpp en MGL desde RR
        Parametros flagHhppUpdateCronJob;
        flagHhppUpdateCronJob = recursos.queryParametros(Constant.TAREA_PROGRAMADA_HHPP_UPDATE);
        if (flagHhppUpdateCronJob.getValor().equalsIgnoreCase(Constant.COMMON_TRUE)) {
            try {
                LOGGER.error("Tarea Programada para actualizacion de Hhpp en MGL Activada");
                SchedulerFactory schedulerFactory = new StdSchedulerFactory();
                Scheduler scheduler = schedulerFactory.getScheduler();
                ParametrosTareasProgramadas paramCronJob;
                String nomServer;
                //JDHT
                nomServer = InetAddress.getLocalHost().getHostName();
                paramCronJob = recursos.queryParametrosTareasProgramadas(
                        co.com.claro.mgl.utils.Constant.TAREAS_PROGRAMADAS_HHPP_UPDATE, nomServer);
                if (paramCronJob==null){
                    /**
                     * si tarea no esta parametrizada no se ejecutara nunca la tarea en este servidor
                     */
                    scheduler.shutdown();
                    return;
                }
                scheduler.start();
                JobDetail hhppUpdateDetail = new JobDetailImpl("Catastro", "UpdateHhpp", UpdateHhppCronJob.class);
                CronTrigger hhppUpdateTrigger = new CronTriggerImpl("Catastro", "UpdateHhpp", paramCronJob.getValor());
                scheduler.scheduleJob(hhppUpdateDetail, hhppUpdateTrigger);
            } catch (SchedulerException | ParseException | ApplicationException | UnknownHostException ex) {
                String msg = "Se produjo un error al momento de ejecutar el método  "
                        + "" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException("Error al momento de construir el scheduler. EX000: " + ex.getMessage(), ex);
            }
        }
    }
}
