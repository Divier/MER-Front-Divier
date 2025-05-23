/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.war.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Observable;
import java.util.Observer;


/**
 * Tarea de ejecuci&oacute;n para el manejo de Logs.
 * 
 * @author V&iacute;ctor Bocanegra (<i>bocanegravm</i>).
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
public class LogMglTask extends Observable implements Runnable {
    
    private static final Logger LOGGER = LogManager.getLogger(LogMglTask.class);
    private static int contador = 0;

    private int runEveryNSeconds = 2000;
    private long lastKnownPosition = 0;
    private boolean shouldIRun = true;
    private File archivoLog = null;
    private String log;
    
    
    /**
     * Constructor.
     * 
     * @param archivoLog Archivo de Log a leer.
     * @param runEveryNSeconds Cantidad de milisegundos que duerme el hilo.
     * @param o Observador.
     */
    public LogMglTask(File archivoLog, int runEveryNSeconds, Observer o) {
        this.archivoLog = archivoLog;
        this.runEveryNSeconds = runEveryNSeconds;
        this.addObserver(o);
    }
    
    @Override
    public void run() {
        try {
            while (shouldIRun) {
                Thread.sleep(runEveryNSeconds);
                long fileLength = archivoLog.length();
                if (fileLength > lastKnownPosition) {

                    // Abrir archivo como solo lectura.
                    RandomAccessFile readOnlyFileAccess = new RandomAccessFile(archivoLog, "r");
                    readOnlyFileAccess.seek(lastKnownPosition);
                    String line = null;
                    while ((line = readOnlyFileAccess.readLine()) != null) {
                        log += line;
                        contador++;
                    }
                    lastKnownPosition = readOnlyFileAccess.getFilePointer();
                    readOnlyFileAccess.close();
                    
                    // Notifica una actualizacion en el log.
                    this.setChanged();
                    this.notifyObservers(log);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Se produjo un error al visualizar el log: " + e.getMessage(), e);
            stopRunning();
        }
    }
    
    
    /**
     * Detiene la ejecuci&oacute;n del hilo.
     */
    public void stopRunning() {
        shouldIRun = false;
    }
}
