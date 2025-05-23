/*
 * To change this template, choose Tools | Templates
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
public class CmtReporteCompromisoComercialDto {
    
    
     private static List<CuentaMatrizCompComercialDto> listCuentaMatrizCompComercialDto;
      private static List<CuentaMatrizCompComercialDto> listSolicitudesTotalesReport;
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
        listCuentaMatrizCompComercialDto = new LinkedList<CuentaMatrizCompComercialDto>();
        numeroRegistrosAProcesar = 0;
        numeroRegistrosProcesados = 0;
        isProcessing = true;
        isFinished = false;
        userRunningProcess = user;
        startProcessDate = new Date();
    }

    public static void endProcess(List<CuentaMatrizCompComercialDto> resultado) throws ApplicationException {
        if(!isProcessing){
            throw new ApplicationException("No hay ningun proceso en ejecucion");
        }
        listCuentaMatrizCompComercialDto = resultado;
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
        listCuentaMatrizCompComercialDto = null;
        numeroRegistrosAProcesar = 0;
        numeroRegistrosProcesados = 0;
        isProcessing = false;
        isFinished = false;
        userRunningProcess = "";
        startProcessDate = null;
        endProcessDate = null;

    }

    public static List<CuentaMatrizCompComercialDto> getListCuentaMatrizCompComercialDto() {
        return listCuentaMatrizCompComercialDto;
    }

    public static void setListCuentaMatrizCompComercialDto(List<CuentaMatrizCompComercialDto> listCuentaMatrizCompComercialDto) {
        CmtReporteCompromisoComercialDto.listCuentaMatrizCompComercialDto = listCuentaMatrizCompComercialDto;
    }

 
 
 
    public static int getNumeroRegistrosAProcesar() {
        return numeroRegistrosAProcesar;
    }

    public static void setNumeroRegistrosAProcesar(int numeroRegistrosAProcesar) {
        CmtReporteCompromisoComercialDto.numeroRegistrosAProcesar = numeroRegistrosAProcesar;
    }

    public static int getNumeroRegistrosProcesados() {
        return numeroRegistrosProcesados;
    }

    public static void setNumeroRegistrosProcesados(int numeroRegistrosProcesados) {
        CmtReporteCompromisoComercialDto.numeroRegistrosProcesados = numeroRegistrosProcesados;
    }

    
    public static boolean isIsProcessing() {
        return isProcessing;
    }

    public static void setIsProcessing(boolean isProcessing) {
        CmtReporteCompromisoComercialDto.isProcessing = isProcessing;
    }

    public static boolean isIsFinished() {
        return isFinished;
    }

    public static void setIsFinished(boolean isFinished) {
        CmtReporteCompromisoComercialDto.isFinished = isFinished;
    }

    public static String getUserRunningProcess() {
        return userRunningProcess;
    }

    public static void setUserRunningProcess(String userRunningProcess) {
        CmtReporteCompromisoComercialDto.userRunningProcess = userRunningProcess;
    }

    public static Date getStartProcessDate() {
        return startProcessDate;
    }

    public static void setStartProcessDate(Date startProcessDate) {
        CmtReporteCompromisoComercialDto.startProcessDate = startProcessDate;
    }

    public static Date getEndProcessDate() {
        return endProcessDate;
    }

    public static void setEndProcessDate(Date endProcessDate) {
        CmtReporteCompromisoComercialDto.endProcessDate = endProcessDate;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        CmtReporteCompromisoComercialDto.message = message;
    }

    public static List<CuentaMatrizCompComercialDto> getListSolicitudesTotalesReport() {
        return listSolicitudesTotalesReport;
    }

    public static void setListSolicitudesTotalesReport(List<CuentaMatrizCompComercialDto> listSolicitudesTotalesReport) {
        CmtReporteCompromisoComercialDto.listSolicitudesTotalesReport = listSolicitudesTotalesReport;
    }

   

}
