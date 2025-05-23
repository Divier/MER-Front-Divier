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
public class CmtFraudesMasivoHHPPDesmarcadoDtoMgl {
    private static List<FraudesHHPPDesmarcadoMasivoDto> listFraudesHHPPDesmarcadoMasivoDto;
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
        listFraudesHHPPDesmarcadoMasivoDto = new LinkedList<FraudesHHPPDesmarcadoMasivoDto>();
        numeroRegistrosAProcesar = 0;
        numeroRegistrosProcesados = 0;
        nombreArchivo = "Ningún archivo cargado";
        isProcessing = true;
        isFinished = false;
        userRunningProcess = user;
        startProcessDate = new Date();
    }
    
    public static void endProcess(List<FraudesHHPPDesmarcadoMasivoDto> resultado) throws ApplicationException {
        if (!isProcessing) {
            throw new ApplicationException("No hay ningun proceso en ejecucion");
        }
        listFraudesHHPPDesmarcadoMasivoDto = resultado;
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
        listFraudesHHPPDesmarcadoMasivoDto = null;
        numeroRegistrosAProcesar = 0;
        numeroRegistrosProcesados = 0;
        isProcessing = false;
        isFinished = false;
        userRunningProcess = "";
        startProcessDate = null;
        endProcessDate = null;
        nombreArchivo = "";
    }
    
    public static List<FraudesHHPPDesmarcadoMasivoDto> getListFraudesHHPPDesmarcadoMasivoDto() {
        return listFraudesHHPPDesmarcadoMasivoDto;
    }

    public static void setListFraudesHHPPDesmarcadoMasivoDto(List<FraudesHHPPDesmarcadoMasivoDto> listFraudesHHPPDesmarcadoMasivoDto) {
        CmtFraudesMasivoHHPPDesmarcadoDtoMgl.listFraudesHHPPDesmarcadoMasivoDto = listFraudesHHPPDesmarcadoMasivoDto;
    }

    public static int getNumeroRegistrosAProcesar() {
        return numeroRegistrosAProcesar;
    }

    public static void setNumeroRegistrosAProcesar(int numeroRegistrosAProcesar) {
        CmtFraudesMasivoHHPPDesmarcadoDtoMgl.numeroRegistrosAProcesar = numeroRegistrosAProcesar;
    }

    public static int getNumeroRegistrosProcesados() {
        return numeroRegistrosProcesados;
    }

    public static void setNumeroRegistrosProcesados(int numeroRegistrosProcesados) {
        CmtFraudesMasivoHHPPDesmarcadoDtoMgl.numeroRegistrosProcesados = numeroRegistrosProcesados;
    }

    public static boolean isIsProcessing() {
        return isProcessing;
    }

    public static void setIsProcessing(boolean isProcessing) {
        CmtFraudesMasivoHHPPDesmarcadoDtoMgl.isProcessing = isProcessing;
    }

    public static boolean isIsFinished() {
        return isFinished;
    }

    public static void setIsFinished(boolean isFinished) {
        CmtFraudesMasivoHHPPDesmarcadoDtoMgl.isFinished = isFinished;
    }

    public static String getUserRunningProcess() {
        return userRunningProcess;
    }

    public static void setUserRunningProcess(String userRunningProcess) {
        CmtFraudesMasivoHHPPDesmarcadoDtoMgl.userRunningProcess = userRunningProcess;
    }

    public static String getNombreArchivo() {
        return nombreArchivo;
    }

    public static void setNombreArchivo(String nombreArchivo) {
        CmtFraudesMasivoHHPPDesmarcadoDtoMgl.nombreArchivo = nombreArchivo;
    }

    public static Date getStartProcessDate() {
        return startProcessDate;
    }

    public static void setStartProcessDate(Date startProcessDate) {
        CmtFraudesMasivoHHPPDesmarcadoDtoMgl.startProcessDate = startProcessDate;
    }

    public static Date getEndProcessDate() {
        return endProcessDate;
    }

    public static void setEndProcessDate(Date endProcessDate) {
        CmtFraudesMasivoHHPPDesmarcadoDtoMgl.endProcessDate = endProcessDate;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        CmtFraudesMasivoHHPPDesmarcadoDtoMgl.message = message;
    }
    
}
