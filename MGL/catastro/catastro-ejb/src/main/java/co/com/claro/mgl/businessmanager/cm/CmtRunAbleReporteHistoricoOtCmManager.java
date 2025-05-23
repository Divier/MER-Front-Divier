/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dtos.CmtReporteHistoricoOtCmDto;
import co.com.claro.mgl.dtos.ReporteHistoricoOtCmDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

/**
 *
 * @author cardenaslb
 */
public class CmtRunAbleReporteHistoricoOtCmManager implements Runnable{
    private static final Logger LOGGER = LogManager.getLogger(CmtRunAbleReporteHistoricoOtCmManager.class);

    private CmtOrdenTrabajoReportThreadManager cmtOrdenTrabajoReportThreadManager;
    String tipoReporte;
    Date fechaInicio;
    Date fechaFin;

    String nodo;
    String usuario;
    Timer timer;
    HashMap<String, Object> params;
    boolean contar;
    int firstResult;
    int maxResults;

    private CmtRunAbleReporteHistoricoOtCmManager() {
    }

    public CmtRunAbleReporteHistoricoOtCmManager(HashMap<String, Object> params,String usuario) {
        this.params = params;
        this.usuario = usuario;
        this.cmtOrdenTrabajoReportThreadManager = new CmtOrdenTrabajoReportThreadManager();
        this.timer = new Timer();
    }

    @Override
    public void run() {
        int numreg_perpage = 0;
        int numRegistros = 0;
        numRegistros = (Integer)params.get("regAPro");
        numreg_perpage = (Integer)params.get("cantRegConsulta") ; 
        List<ReporteHistoricoOtCmDto> listaCmtReporteEstadoActualOtCMDto = new ArrayList<>();
        try {
            CmtReporteHistoricoOtCmDto.cleanBeforeStart();
            CmtReporteHistoricoOtCmDto.startProcess(usuario);
        } catch (ApplicationException ex) {
            CmtReporteHistoricoOtCmDto.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        try {
            //consultar numero de registros y asiganr a siguiente linea
            numRegistros = cmtOrdenTrabajoReportThreadManager.getReporteHistoricoOtCMCount(params, contar,   0,   0,  usuario);
            CmtReporteHistoricoOtCmDto.setNumeroRegistrosAProcesar(numRegistros);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        try {
            if (numRegistros != 0) {
                if (numRegistros > 0) {
                    for (int page = 0; page <= (numRegistros / numreg_perpage); page++) {
                        LOGGER.error("pagina" + page);
                        int inicioRegistros = page * numreg_perpage;
                        listaCmtReporteEstadoActualOtCMDto.addAll(cmtOrdenTrabajoReportThreadManager.getReporteHistoricoOtCM
                            (params, contar,   inicioRegistros,   numreg_perpage,  usuario));
                        CmtReporteHistoricoOtCmDto.setNumeroRegistrosProcesados(listaCmtReporteEstadoActualOtCMDto.size());
                        CmtReporteHistoricoOtCmDto.setListReporteHistoricoOtCMDto(listaCmtReporteEstadoActualOtCMDto);
                    }
                   
                    CmtReporteHistoricoOtCmDto.setListReporteHistoricoOtCMDto(listaCmtReporteEstadoActualOtCMDto);
                }
            }
        } catch (ApplicationException ex) {
            CmtReporteHistoricoOtCmDto.setIsProcessing(false);
            CmtReporteHistoricoOtCmDto.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        try {
            CmtReporteHistoricoOtCmDto.setNumeroRegistrosAProcesar(listaCmtReporteEstadoActualOtCMDto.size());
            CmtReporteHistoricoOtCmDto.endProcess(listaCmtReporteEstadoActualOtCMDto);
        } catch (ApplicationException ex) {
            CmtReporteHistoricoOtCmDto.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }

    }
}
