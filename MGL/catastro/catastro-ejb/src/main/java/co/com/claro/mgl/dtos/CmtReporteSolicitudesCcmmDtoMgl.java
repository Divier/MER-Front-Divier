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
 * Clase para controlar el proceso de reporte detallado de solicitudes de
 * cuentas matrices
 *
 * @author cardenaslb
 * @version 1.0
 */
public final class CmtReporteSolicitudesCcmmDtoMgl {

    private static List<CmtReporteDetalladoDto> listCmtReporteDetalladoDto;
    private static List<CmtReporteDetalladoDto> listSolicitudesTotalesReport;
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
        listCmtReporteDetalladoDto = new LinkedList<CmtReporteDetalladoDto>();
        numeroRegistrosAProcesar = 0;
        numeroRegistrosProcesados = 0;
        isProcessing = true;
        isFinished = false;
        userRunningProcess = user;
        startProcessDate = new Date();
    }

    public static void endProcess(List<CmtReporteDetalladoDto> resultado) throws ApplicationException {
        if(!isProcessing){
            throw new ApplicationException("No hay ningun proceso en ejecucion");
        }
        listCmtReporteDetalladoDto = resultado;
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
        listCmtReporteDetalladoDto = null;
        numeroRegistrosAProcesar = 0;
        numeroRegistrosProcesados = 0;
        isProcessing = false;
        isFinished = false;
        userRunningProcess = "";
        startProcessDate = null;
        endProcessDate = null;

    }

    public static List<CmtReporteDetalladoDto> getListCmtReporteDetalladoDto() {
        return listCmtReporteDetalladoDto;
    }

    public static void setListCmtReporteDetalladoDto(List<CmtReporteDetalladoDto> listCmtReporteDetalladoDto) {
        CmtReporteSolicitudesCcmmDtoMgl.listCmtReporteDetalladoDto = listCmtReporteDetalladoDto;
    }
    
 
    public static int getNumeroRegistrosAProcesar() {
        return numeroRegistrosAProcesar;
    }

    public static void setNumeroRegistrosAProcesar(int numeroRegistrosAProcesar) {
        CmtReporteSolicitudesCcmmDtoMgl.numeroRegistrosAProcesar = numeroRegistrosAProcesar;
    }

    public static int getNumeroRegistrosProcesados() {
        return numeroRegistrosProcesados;
    }

    public static void setNumeroRegistrosProcesados(int numeroRegistrosProcesados) {
        CmtReporteSolicitudesCcmmDtoMgl.numeroRegistrosProcesados = numeroRegistrosProcesados;
    }

    
    public static boolean isIsProcessing() {
        return isProcessing;
    }

    public static void setIsProcessing(boolean isProcessing) {
        CmtReporteSolicitudesCcmmDtoMgl.isProcessing = isProcessing;
    }

    public static boolean isIsFinished() {
        return isFinished;
    }

    public static void setIsFinished(boolean isFinished) {
        CmtReporteSolicitudesCcmmDtoMgl.isFinished = isFinished;
    }

    public static String getUserRunningProcess() {
        return userRunningProcess;
    }

    public static void setUserRunningProcess(String userRunningProcess) {
        CmtReporteSolicitudesCcmmDtoMgl.userRunningProcess = userRunningProcess;
    }

    public static Date getStartProcessDate() {
        return startProcessDate;
    }

    public static void setStartProcessDate(Date startProcessDate) {
        CmtReporteSolicitudesCcmmDtoMgl.startProcessDate = startProcessDate;
    }

    public static Date getEndProcessDate() {
        return endProcessDate;
    }

    public static void setEndProcessDate(Date endProcessDate) {
        CmtReporteSolicitudesCcmmDtoMgl.endProcessDate = endProcessDate;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        CmtReporteSolicitudesCcmmDtoMgl.message = message;
    }

    public static List<CmtReporteDetalladoDto> getListSolicitudesTotalesReport() {
        return listSolicitudesTotalesReport;
    }

    public static void setListSolicitudesTotalesReport(List<CmtReporteDetalladoDto> listSolicitudesTotalesReport) {
        CmtReporteSolicitudesCcmmDtoMgl.listSolicitudesTotalesReport = listSolicitudesTotalesReport;
    }

}
