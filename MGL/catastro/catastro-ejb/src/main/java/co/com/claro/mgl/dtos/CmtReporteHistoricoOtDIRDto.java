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
public class CmtReporteHistoricoOtDIRDto {
      
    private static List<ReporteHistoricoOtDIRDto> listCReporteHistoricoOtDIRDto;
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
        listCReporteHistoricoOtDIRDto = new LinkedList<ReporteHistoricoOtDIRDto>();
        numeroRegistrosAProcesar = 0;
        numeroRegistrosProcesados = 0;
        isProcessing = true;
        isFinished = false;
        userRunningProcess = user;
        startProcessDate = new Date();
    }

    public static void endProcess(List<ReporteHistoricoOtDIRDto> resultado) throws ApplicationException {
        if(!isProcessing){
            throw new ApplicationException("No hay ningun proceso en ejecucion");
        }
        listCReporteHistoricoOtDIRDto = resultado;
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
        listCReporteHistoricoOtDIRDto = null;
        numeroRegistrosAProcesar = 0;
        numeroRegistrosProcesados = 0;
        isProcessing = false;
        isFinished = false;
        userRunningProcess = "";
        startProcessDate = null;
        endProcessDate = null;

    }

    public static List<ReporteHistoricoOtDIRDto> getListCReporteHistoricoOtDIRDto() {
        return listCReporteHistoricoOtDIRDto;
    }

    public static void setListCReporteHistoricoOtDIRDto(List<ReporteHistoricoOtDIRDto> listCReporteHistoricoOtDIRDto) {
        CmtReporteHistoricoOtDIRDto.listCReporteHistoricoOtDIRDto = listCReporteHistoricoOtDIRDto;
    }

 
 
    public static int getNumeroRegistrosAProcesar() {
        return numeroRegistrosAProcesar;
    }

    public static void setNumeroRegistrosAProcesar(int numeroRegistrosAProcesar) {
        CmtReporteHistoricoOtDIRDto.numeroRegistrosAProcesar = numeroRegistrosAProcesar;
    }

    public static int getNumeroRegistrosProcesados() {
        return numeroRegistrosProcesados;
    }

    public static void setNumeroRegistrosProcesados(int numeroRegistrosProcesados) {
        CmtReporteHistoricoOtDIRDto.numeroRegistrosProcesados = numeroRegistrosProcesados;
    }

    
    public static boolean isIsProcessing() {
        return isProcessing;
    }

    public static void setIsProcessing(boolean isProcessing) {
        CmtReporteHistoricoOtDIRDto.isProcessing = isProcessing;
    }

    public static boolean isIsFinished() {
        return isFinished;
    }

    public static void setIsFinished(boolean isFinished) {
        CmtReporteHistoricoOtDIRDto.isFinished = isFinished;
    }

    public static String getUserRunningProcess() {
        return userRunningProcess;
    }

    public static void setUserRunningProcess(String userRunningProcess) {
        CmtReporteHistoricoOtDIRDto.userRunningProcess = userRunningProcess;
    }

    public static Date getStartProcessDate() {
        return startProcessDate;
    }

    public static void setStartProcessDate(Date startProcessDate) {
        CmtReporteHistoricoOtDIRDto.startProcessDate = startProcessDate;
    }

    public static Date getEndProcessDate() {
        return endProcessDate;
    }

    public static void setEndProcessDate(Date endProcessDate) {
        CmtReporteHistoricoOtDIRDto.endProcessDate = endProcessDate;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        CmtReporteHistoricoOtDIRDto.message = message;
    }


   
}
