/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author valbuenayf
 */
public final class CmtReporteCuentaMatrizDtoMgl {

    private static List<CmtTecnologiaSubMgl> listCmtTecnologiaSubMgl;
    private static int numeroRegistrosAProcesar;
    private static int numeroRegistrosProcesados;
    private static boolean isProcessing;
    private static boolean isFinished;
    private static String userRunningProcess;
    private static Date startProcessDate;
    private static Date endProcessDate;
    private static String message;

    public static void startProcess(String user) throws ApplicationException {
        if (isProcessing) {
            throw new ApplicationException("Ya hay un proceso en ejecucion usuraio:"
                    .concat(userRunningProcess)
                    .concat("Hora inicio:").concat(startProcessDate.toString()));
        }
        listCmtTecnologiaSubMgl = new LinkedList<CmtTecnologiaSubMgl>();
        numeroRegistrosAProcesar = 0;
        numeroRegistrosProcesados = 0;
        isProcessing = true;
        isFinished = false;
        userRunningProcess = user;
        startProcessDate = new Date();
    }

    public static void endProcess(List<CmtTecnologiaSubMgl> resultado) throws ApplicationException {
        if (!isProcessing) {
            throw new ApplicationException("No hay ningun proceso en ejecucion");
        }
        listCmtTecnologiaSubMgl = resultado;
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
        listCmtTecnologiaSubMgl = null;
        numeroRegistrosAProcesar = 0;
        numeroRegistrosProcesados = 0;
        isProcessing = false;
        isFinished = false;
        userRunningProcess = "";
        startProcessDate = null;
        endProcessDate = null;

    }

    public static List<CmtTecnologiaSubMgl> getListCmtTecnologiaSubMgl() {
        return listCmtTecnologiaSubMgl;
    }

    public static void setListCmtTecnologiaSubMgl(List<CmtTecnologiaSubMgl> listCmtTecnologiaSubMgl) {
        CmtReporteCuentaMatrizDtoMgl.listCmtTecnologiaSubMgl = listCmtTecnologiaSubMgl;
    }

    public static int getNumeroRegistrosAProcesar() {
        return numeroRegistrosAProcesar;
    }

    public static void setNumeroRegistrosAProcesar(int numeroRegistrosAProcesar) {
        CmtReporteCuentaMatrizDtoMgl.numeroRegistrosAProcesar = numeroRegistrosAProcesar;
    }

    public static int getNumeroRegistrosProcesados() {
        return numeroRegistrosProcesados;
    }

    public static void setNumeroRegistrosProcesados(int numeroRegistrosProcesados) {
        CmtReporteCuentaMatrizDtoMgl.numeroRegistrosProcesados = numeroRegistrosProcesados;
    }

    public static boolean isIsProcessing() {
        return isProcessing;
    }

    public static void setIsProcessing(boolean isProcessing) {
        CmtReporteCuentaMatrizDtoMgl.isProcessing = isProcessing;
    }

    public static boolean isIsFinished() {
        return isFinished;
    }

    public static void setIsFinished(boolean isFinished) {
        CmtReporteCuentaMatrizDtoMgl.isFinished = isFinished;
    }

    public static String getUserRunningProcess() {
        return userRunningProcess;
    }

    public static void setUserRunningProcess(String userRunningProcess) {
        CmtReporteCuentaMatrizDtoMgl.userRunningProcess = userRunningProcess;
    }

    public static Date getStartProcessDate() {
        return startProcessDate;
    }

    public static void setStartProcessDate(Date startProcessDate) {
        CmtReporteCuentaMatrizDtoMgl.startProcessDate = startProcessDate;
    }

    public static Date getEndProcessDate() {
        return endProcessDate;
    }

    public static void setEndProcessDate(Date endProcessDate) {
        CmtReporteCuentaMatrizDtoMgl.endProcessDate = endProcessDate;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        CmtReporteCuentaMatrizDtoMgl.message = message;
    }
}
