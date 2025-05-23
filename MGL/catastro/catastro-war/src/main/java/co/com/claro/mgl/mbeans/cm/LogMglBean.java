package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.war.task.LogMglTask;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;


/**
 * Managed Bean para el manejo de Logs del aplicativo.
 * 
 * @author V&iacute;ctor Bocanegra (<i>bocanegravm</i>).
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
@ViewScoped
@ManagedBean(name = "logMglBean")
public class LogMglBean implements Observer {

    private static final Logger LOGGER = LogManager.getLogger(LogMglBean.class);
    
    /** Hilo de ejecuci&oacute;n de lectura de Log. */
    private LogMglTask logMglTask;
    /** Cadena de Log del aplicativo. */
    private String log;
    /** Ruta f&iacute;sica donde se encuentra almacenado el Log. */
    private String rutaLog;
    
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    
    /** Archivo de Log para descargar. */
    private StreamedContent file;
    
    
    /**
     * M&eacute;todo llamado al momento posterior a la construcci&oacute;n del objeto.
     */
    @PostConstruct
    public void init() {
        try {
            ParametrosMgl parametroRutaLog = parametrosMglFacadeLocal.
                    findByAcronimoName(Constant.RUTA_LOG_SERVER);

            if (parametroRutaLog != null) {
                ExecutorService executor = Executors.newFixedThreadPool(4);
                this.rutaLog = parametroRutaLog.getParValor();
                if (rutaLog != null && !rutaLog.isEmpty()) {
                    File archivoLog = new File(rutaLog);
                    int runEveryNSeconds = 2000;

                    // Start running log file tailer on crunchify.log file
                    this.logMglTask = new LogMglTask(archivoLog, runEveryNSeconds, this);
                    executor.execute(this.logMglTask);


                    // Crear el stream para la descarga del archivo de Log del aplicativo.
                    this.loadLogFileStream();
                    
                } else {
                    FacesUtil.mostrarMensajeError("No existe valor para el parámetro " + Constant.RUTA_LOG_SERVER, null, LOGGER);
                }
            } else {
                FacesUtil.mostrarMensajeError("No hay un parametro configurado para la ruta del log.", null, LOGGER);
            }
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en la consulta del parametro en LogMglBean: " + ex.getMessage() + ex.getMessage(), ex, LOGGER);

        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en la consulta del parametro en LogMglBean: " + ex.getMessage() + ex.getMessage(), ex, LOGGER);

        }

    }
    
    
    /**
     * M&eacute;todo llamado al momento previo de destruir el objeto.
     */
    @PreDestroy
    public void onDestroy() {
        if (this.logMglTask != null) {
            // detener el hilo de ejecucion.
            this.logMglTask.stopRunning();
        }
    }
    
    
    /**
     * Realiza la lectura del archivo del Log para construir el Stream de descarga.
     */
    private void loadLogFileStream() {
        if (rutaLog != null && !rutaLog.isEmpty()) {
            try {
                // Crear el stream para la descarga del archivo de Log del aplicativo.
                File archivoLog = new File(rutaLog);
                int initPos = rutaLog.lastIndexOf(File.separator);
                initPos = initPos < 0 ? 0 : initPos + 1;
                String filename = rutaLog.substring(initPos, rutaLog.length());
                FileInputStream stream = new FileInputStream(archivoLog);
                file = new DefaultStreamedContent(stream, "text/plain", filename);
            } catch (FileNotFoundException e) {
                FacesUtil.mostrarMensajeError("No se encuentra el archivo '" + rutaLog + "'.", e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error al momento de realizar la lectura del archivo '" + rutaLog + "': " + e.getMessage(), e, LOGGER);
            }
        }
    }
    
    
    
    /**
     * M&eacute;todo llamado al momento de recibir una notificaci&oacute;n de 
     * actualizaci&oacute;n del Log.
     * 
     * @param o Objeto observable.
     * @param data Informaci&oacute;n notificada. 
     */
    @Override
    public void update(Observable o, Object data) {
        if (data instanceof String) {
            this.log = (String) data;
            
            // Actualizar el stream de descarga del archivo de Log.
            this.loadLogFileStream();
            
            // Actualizar el componente.
            PrimeFaces faces = PrimeFaces.current();
            if (faces != null && faces.ajax() != null) {
                faces.ajax().update("formPrincipal:logEditor");
            }
        }
    }
    
    
    /**
     * Obtiene el texto del Log.
     * @return log
     */
    public String getLog() {
        return log;
    }
    
    
    /**
     * Establece el archivo del Log.
     * @param log Texto con el Log del aplicativo.
     */
    public void setLog(String log) {
    }
    
    
    /**
     * Obtiene el archivo para descarga del Log.
     * @return 
     */
    public StreamedContent getFile() {
        return file;
    }
}
