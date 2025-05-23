/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.rest.dtos.MasivoModificacionDto;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author valbuenayf
 */
public final class CmtMasivoCuentaMatrizDtoMgl {

    private static List<MasivoModificacionDto> listMasivoModificacionDto;
    private static int numeroRegistrosAProcesar;
    private static int numeroRegistrosProcesados;
    private static boolean isProcessing;
    private static boolean isFinished;
    private static String userRunningProcess;
    private static String nombreArchivo;
    private static Date startProcessDate;
    private static Date endProcessDate;
    private static String message;
    private static String tipoArchivo;

    public static void startProcess(String user) throws ApplicationException {
        if (isProcessing) {
            throw new ApplicationException("Ya hay un proceso en ejecucion usuraio:"
                    .concat(userRunningProcess)
                    .concat("Hora inicio:").concat(startProcessDate.toString()));
        }
        listMasivoModificacionDto = new LinkedList<MasivoModificacionDto>();
        numeroRegistrosAProcesar = 0;
        numeroRegistrosProcesados = 0;
        isProcessing = true;
        isFinished = false;
        userRunningProcess = user;
        startProcessDate = new Date();
    }

    public static void endProcess(List<MasivoModificacionDto> resultado) throws ApplicationException {
        if (!isProcessing) {
            throw new ApplicationException("No hay ningun proceso en ejecucion");
        }
        listMasivoModificacionDto = resultado;
        isProcessing = false;
        isFinished = true;
        endProcessDate = new Date();
        message = "Proceso teminado exitosamente";
    }

    public static void cleanBeforeStart() throws ApplicationException {
        if (isProcessing) {
            throw new ApplicationException("Ya hay un proceso en ejecucion usuraio:"
                    .concat(userRunningProcess)
                    .concat("Hora inicio:").concat(startProcessDate.toString()));
        }
        listMasivoModificacionDto = null;
        numeroRegistrosAProcesar = 0;
        numeroRegistrosProcesados = 0;
        isProcessing = false;
        isFinished = false;
        userRunningProcess = "";
        startProcessDate = null;
        endProcessDate = null;
        nombreArchivo = "";

    }

    public static List<MasivoModificacionDto> getListMasivoModificacionDto() {
        return listMasivoModificacionDto;
    }

    public static void setListMasivoModificacionDto(List<MasivoModificacionDto> listMasivoModificacionDto) {
        CmtMasivoCuentaMatrizDtoMgl.listMasivoModificacionDto = listMasivoModificacionDto;
    }

    public static int getNumeroRegistrosAProcesar() {
        return numeroRegistrosAProcesar;
    }

    public static void setNumeroRegistrosAProcesar(int numeroRegistrosAProcesar) {
        CmtMasivoCuentaMatrizDtoMgl.numeroRegistrosAProcesar = numeroRegistrosAProcesar;
    }

    public static int getNumeroRegistrosProcesados() {
        return numeroRegistrosProcesados;
    }

    public static void setNumeroRegistrosProcesados(int numeroRegistrosProcesados) {
        CmtMasivoCuentaMatrizDtoMgl.numeroRegistrosProcesados = numeroRegistrosProcesados;
    }

    public static boolean isIsProcessing() {
        return isProcessing;
    }

    public static void setIsProcessing(boolean isProcessing) {
        CmtMasivoCuentaMatrizDtoMgl.isProcessing = isProcessing;
    }

    public static boolean isIsFinished() {
        return isFinished;
    }

    public static void setIsFinished(boolean isFinished) {
        CmtMasivoCuentaMatrizDtoMgl.isFinished = isFinished;
    }

    public static String getUserRunningProcess() {
        return userRunningProcess;
    }

    public static void setUserRunningProcess(String userRunningProcess) {
        CmtMasivoCuentaMatrizDtoMgl.userRunningProcess = userRunningProcess;
    }

    public static Date getStartProcessDate() {
        return startProcessDate;
    }

    public static void setStartProcessDate(Date startProcessDate) {
        CmtMasivoCuentaMatrizDtoMgl.startProcessDate = startProcessDate;
    }

    public static Date getEndProcessDate() {
        return endProcessDate;
    }

    public static void setEndProcessDate(Date endProcessDate) {
        CmtMasivoCuentaMatrizDtoMgl.endProcessDate = endProcessDate;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        CmtMasivoCuentaMatrizDtoMgl.message = message;
    }

    public static String getNombreArchivo() {
        return nombreArchivo;
    }

    public static void setNombreArchivo(String nombreArchivo) {
        CmtMasivoCuentaMatrizDtoMgl.nombreArchivo = nombreArchivo;
    }

    public static String getTipoArchivo() {
        return tipoArchivo;
    }

    public static void setTipoArchivo(String tipoArchivo) {
        CmtMasivoCuentaMatrizDtoMgl.tipoArchivo = tipoArchivo;
    }
    
}
