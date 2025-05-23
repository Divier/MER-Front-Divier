/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.error.ApplicationException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public class MasivoControlFactibilidadDtoMgl {

    private static List<CmtDireccionDetalladaMglDto> listDireccionesDetalladas;
    private static List<String> listArchivoWithErrors;
    private static int numeroRegistrosAProcesar;
    private static double numeroRegistrosProcesados;
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
        listDireccionesDetalladas = new LinkedList<>();
        numeroRegistrosAProcesar = 0;
        numeroRegistrosProcesados = 0;
        isProcessing = true;
        isFinished = false;
        userRunningProcess = user;
        startProcessDate = new Date();
    }

    public static void endProcess(List<CmtDireccionDetalladaMglDto> resultado) throws ApplicationException {
        if (!isProcessing) {
            throw new ApplicationException("No hay ningun proceso en ejecucion");
        }
        listDireccionesDetalladas = resultado;  
        isProcessing = false;
        isFinished = true;
        endProcessDate = new Date();
        message = "Proceso teminado exitosamente";
    }

    public static void cleanBeforeStart() throws ApplicationException {
        if (isProcessing) {
            if (startProcessDate != null) {
                throw new ApplicationException("Ya hay un proceso en ejecucion usuario:"
                        .concat(userRunningProcess)
                        .concat("Hora inicio:").concat(startProcessDate.toString()));
            } else {
                throw new ApplicationException("Ya hay un proceso en ejecucion usuario:"
                        .concat(userRunningProcess));
            }

        }
        listDireccionesDetalladas = null;
        numeroRegistrosAProcesar = 0;
        numeroRegistrosProcesados = 0;
        isProcessing = false;
        isFinished = false;
        userRunningProcess = "";
        startProcessDate = null;
        endProcessDate = null;
        nombreArchivo = "";

    }

    public static List<CmtDireccionDetalladaMglDto> getListDireccionesDetalladas() {
        return listDireccionesDetalladas;
    }

    public static void setListDireccionesDetalladas(List<CmtDireccionDetalladaMglDto> listDireccionesDetalladas) {
        MasivoControlFactibilidadDtoMgl.listDireccionesDetalladas = listDireccionesDetalladas;
    }
    

    public static int getNumeroRegistrosAProcesar() {
        return numeroRegistrosAProcesar;
    }

    public static void setNumeroRegistrosAProcesar(int numeroRegistrosAProcesar) {
        MasivoControlFactibilidadDtoMgl.numeroRegistrosAProcesar = numeroRegistrosAProcesar;
    }

    public static double getNumeroRegistrosProcesados() {
        return numeroRegistrosProcesados;
    }

    public static void setNumeroRegistrosProcesados(double numeroRegistrosProcesados) {
        MasivoControlFactibilidadDtoMgl.numeroRegistrosProcesados = numeroRegistrosProcesados;
    }

    public static boolean isIsProcessing() {
        return isProcessing;
    }

    public static void setIsProcessing(boolean isProcessing) {
        MasivoControlFactibilidadDtoMgl.isProcessing = isProcessing;
    }

    public static boolean isIsFinished() {
        return isFinished;
    }

    public static void setIsFinished(boolean isFinished) {
        MasivoControlFactibilidadDtoMgl.isFinished = isFinished;
    }

    public static String getUserRunningProcess() {
        return userRunningProcess;
    }

    public static void setUserRunningProcess(String userRunningProcess) {
        MasivoControlFactibilidadDtoMgl.userRunningProcess = userRunningProcess;
    }

    public static Date getStartProcessDate() {
        return startProcessDate;
    }

    public static void setStartProcessDate(Date startProcessDate) {
        MasivoControlFactibilidadDtoMgl.startProcessDate = startProcessDate;
    }

    public static Date getEndProcessDate() {
        return endProcessDate;
    }

    public static void setEndProcessDate(Date endProcessDate) {
        MasivoControlFactibilidadDtoMgl.endProcessDate = endProcessDate;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        MasivoControlFactibilidadDtoMgl.message = message;
    }

    public static String getNombreArchivo() {
        return nombreArchivo;
    }

    public static void setNombreArchivo(String nombreArchivo) {
        MasivoControlFactibilidadDtoMgl.nombreArchivo = nombreArchivo;
    }

    public static String getTipoArchivo() {
        return tipoArchivo;
    }

    public static void setTipoArchivo(String tipoArchivo) {
        MasivoControlFactibilidadDtoMgl.tipoArchivo = tipoArchivo;
    }

    public static List<String> getListArchivoWithErrors() {
        return listArchivoWithErrors;
    }

    public static void setListArchivoWithErrors(List<String> listArchivoWithErrors) {
        MasivoControlFactibilidadDtoMgl.listArchivoWithErrors = listArchivoWithErrors;
    }

}
