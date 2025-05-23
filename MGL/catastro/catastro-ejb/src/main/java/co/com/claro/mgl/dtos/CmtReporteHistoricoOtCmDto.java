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
 * @author cardenaslb
 */
public class CmtReporteHistoricoOtCmDto {
    private static List<ReporteHistoricoOtCmDto> listReporteHistoricoOtCMDto;
    private static int numeroRegistrosAProcesar;
    private static int numeroRegistrosProcesados;
    private static boolean isProcessing;
    private static boolean isFinished;
    private static String userRunningProcess;
    private static Date startProcessDate;
    private static Date endProcessDate;
    private static String message;

    public static void startProcess(String user) throws ApplicationException {
        if(isProcessing){
            throw new ApplicationException("Ya hay un proceso en ejecucion usuraio:"
                    .concat(userRunningProcess)
                    .concat("Hora inicio:").concat(startProcessDate.toString()));
        }
        listReporteHistoricoOtCMDto = new LinkedList<ReporteHistoricoOtCmDto>();
        numeroRegistrosAProcesar = 0;
        numeroRegistrosProcesados = 0;
        isProcessing = true;
        isFinished = false;
        userRunningProcess = user;
        startProcessDate = new Date();
    }

    public static void endProcess(List<ReporteHistoricoOtCmDto> resultado) throws ApplicationException {
        if(!isProcessing){
            throw new ApplicationException("No hay ningun proceso en ejecucion");
        }
        listReporteHistoricoOtCMDto = resultado;
        isProcessing = false;
        isFinished = true;
        endProcessDate = new Date();
        message="Proceso teminado exitosamente";
    }

    public static  void cleanBeforeStart() throws ApplicationException {
        if(isProcessing){
            throw new ApplicationException("Ya hay un proceso en ejecucion usuraio:"
                    .concat(userRunningProcess)
                    .concat("Hora inicio:").concat(startProcessDate.toString()));
        }
        listReporteHistoricoOtCMDto = null;
        numeroRegistrosAProcesar = 0;
        numeroRegistrosProcesados = 0;
        isProcessing = false;
        isFinished = false;
        userRunningProcess = "";
        startProcessDate = null;
        endProcessDate = null;

    }

    public static List<ReporteHistoricoOtCmDto> getListReporteHistoricoOtCMDto() {
        return listReporteHistoricoOtCMDto;
    }

    public static void setListReporteHistoricoOtCMDto(List<ReporteHistoricoOtCmDto> listReporteHistoricoOtCMDto) {
        CmtReporteHistoricoOtCmDto.listReporteHistoricoOtCMDto = listReporteHistoricoOtCMDto;
    }

    public static int getNumeroRegistrosAProcesar() {
        return numeroRegistrosAProcesar;
    }

    public static void setNumeroRegistrosAProcesar(int numeroRegistrosAProcesar) {
        CmtReporteHistoricoOtCmDto.numeroRegistrosAProcesar = numeroRegistrosAProcesar;
    }

    public static int getNumeroRegistrosProcesados() {
        return numeroRegistrosProcesados;
    }

    public static void setNumeroRegistrosProcesados(int numeroRegistrosProcesados) {
        CmtReporteHistoricoOtCmDto.numeroRegistrosProcesados = numeroRegistrosProcesados;
    }

    public static boolean isIsProcessing() {
        return isProcessing;
    }

    public static void setIsProcessing(boolean isProcessing) {
        CmtReporteHistoricoOtCmDto.isProcessing = isProcessing;
    }

    public static boolean isIsFinished() {
        return isFinished;
    }

    public static void setIsFinished(boolean isFinished) {
        CmtReporteHistoricoOtCmDto.isFinished = isFinished;
    }

    public static String getUserRunningProcess() {
        return userRunningProcess;
    }

    public static void setUserRunningProcess(String userRunningProcess) {
        CmtReporteHistoricoOtCmDto.userRunningProcess = userRunningProcess;
    }

    public static Date getStartProcessDate() {
        return startProcessDate;
    }

    public static void setStartProcessDate(Date startProcessDate) {
        CmtReporteHistoricoOtCmDto.startProcessDate = startProcessDate;
    }

    public static Date getEndProcessDate() {
        return endProcessDate;
    }

    public static void setEndProcessDate(Date endProcessDate) {
        CmtReporteHistoricoOtCmDto.endProcessDate = endProcessDate;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        CmtReporteHistoricoOtCmDto.message = message;
    }

 
 
}
