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
 * @author enriquedm
 */
public class CmtFraudesMasivoHHPPDtoMgl {
    
    private static List<FraudesHHPPMasivoDto> listFraudesHHPPMasivoDto;
    private static int numeroRegistrosAProcesar;
    private static int numeroRegistrosProcesados;
    private static boolean isProcessing;
    private static boolean isFinished;
    private static String userRunningProcess;
    private static String nombreArchivo;
    private static Date startProcessDate;
    private static Date endProcessDate;
    private static String message;
    
    public static void startProcess(String user) throws ApplicationException {
        if (isProcessing) {
            throw new ApplicationException("Ya hay un proceso en ejecucion usuraio:"
                    .concat(userRunningProcess)
                    .concat("Hora inicio:").concat(startProcessDate.toString()));
        }
        listFraudesHHPPMasivoDto = new LinkedList<FraudesHHPPMasivoDto>();
        numeroRegistrosAProcesar = 0;
        numeroRegistrosProcesados = 0;
        nombreArchivo = "Ningún archivo cargado";
        isProcessing = true;
        isFinished = false;
        userRunningProcess = user;
        startProcessDate = new Date();
    }
    
    public static void endProcess(List<FraudesHHPPMasivoDto> resultado) throws ApplicationException {
        if (!isProcessing) {
            throw new ApplicationException("No hay ningun proceso en ejecucion");
        }
        listFraudesHHPPMasivoDto = resultado;
        isProcessing = false;
        isFinished = true;
        endProcessDate = new Date();
        message = "Proceso teminado exitosamente";
    }
    
    public static void cleanBeforeStart() throws ApplicationException {
        if (isProcessing) {
            throw new ApplicationException("Ya hay un proceso en ejecucion usuario: "
                    .concat(userRunningProcess)
                    .concat("Hora inicio:").concat(startProcessDate.toString()));
        }
        listFraudesHHPPMasivoDto = null;
        numeroRegistrosAProcesar = 0;
        numeroRegistrosProcesados = 0;
        isProcessing = false;
        isFinished = false;
        userRunningProcess = "";
        startProcessDate = null;
        endProcessDate = null;
        nombreArchivo = "";
    }
    
    public static List<FraudesHHPPMasivoDto> getListFraudesHHPPMasivoDto() {
        return listFraudesHHPPMasivoDto;
    }

    public static void setListFraudesHHPPMasivoDto(List<FraudesHHPPMasivoDto> listFraudesHHPPMasivoDto) {
        CmtFraudesMasivoHHPPDtoMgl.listFraudesHHPPMasivoDto = listFraudesHHPPMasivoDto;
    }

    public static int getNumeroRegistrosAProcesar() {
        return numeroRegistrosAProcesar;
    }

    public static void setNumeroRegistrosAProcesar(int numeroRegistrosAProcesar) {
        CmtFraudesMasivoHHPPDtoMgl.numeroRegistrosAProcesar = numeroRegistrosAProcesar;
    }

    public static int getNumeroRegistrosProcesados() {
        return numeroRegistrosProcesados;
    }

    public static void setNumeroRegistrosProcesados(int numeroRegistrosProcesados) {
        CmtFraudesMasivoHHPPDtoMgl.numeroRegistrosProcesados = numeroRegistrosProcesados;
    }

    public static boolean isIsProcessing() {
        return isProcessing;
    }

    public static void setIsProcessing(boolean isProcessing) {
        CmtFraudesMasivoHHPPDtoMgl.isProcessing = isProcessing;
    }

    public static boolean isIsFinished() {
        return isFinished;
    }

    public static void setIsFinished(boolean isFinished) {
        CmtFraudesMasivoHHPPDtoMgl.isFinished = isFinished;
    }

    public static String getUserRunningProcess() {
        return userRunningProcess;
    }

    public static void setUserRunningProcess(String userRunningProcess) {
        CmtFraudesMasivoHHPPDtoMgl.userRunningProcess = userRunningProcess;
    }

    public static String getNombreArchivo() {
        return nombreArchivo;
    }

    public static void setNombreArchivo(String nombreArchivo) {
        CmtFraudesMasivoHHPPDtoMgl.nombreArchivo = nombreArchivo;
    }

    public static Date getStartProcessDate() {
        return startProcessDate;
    }

    public static void setStartProcessDate(Date startProcessDate) {
        CmtFraudesMasivoHHPPDtoMgl.startProcessDate = startProcessDate;
    }

    public static Date getEndProcessDate() {
        return endProcessDate;
    }

    public static void setEndProcessDate(Date endProcessDate) {
        CmtFraudesMasivoHHPPDtoMgl.endProcessDate = endProcessDate;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        CmtFraudesMasivoHHPPDtoMgl.message = message;
    }
    
}
