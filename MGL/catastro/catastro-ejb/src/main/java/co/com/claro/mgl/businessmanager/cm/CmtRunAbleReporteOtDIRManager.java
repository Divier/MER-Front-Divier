/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dtos.CmtReporteEstadoActualOtDirHhppDto;
import co.com.claro.mgl.dtos.ReporteOtDIRDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

/**
 *
 * @author cardenaslb
 */
public class CmtRunAbleReporteOtDIRManager implements Runnable{
    private static final Logger LOGGER = LogManager.getLogger(CmtRunAbleReporteOtDIRManager.class);

    private CmtOrdenTrabajoReportThreadManager cmtOrdenTrabajoReportThreadManager;
    String tipoReporte;
    Date fechaInicio;
    Date fechaFin;
    BigDecimal estrato;
    BigDecimal tecnologia;
    String nodo;
    String usuario;
    Timer timer;
    HashMap<String, Object> params;
    boolean contar;
    int firstResult;
    int maxResults;

    private CmtRunAbleReporteOtDIRManager() {
    }

    public CmtRunAbleReporteOtDIRManager(HashMap<String, Object> params,String usuario) {
        this.params = params;
        this.usuario = usuario;
        this.cmtOrdenTrabajoReportThreadManager = new CmtOrdenTrabajoReportThreadManager();
        this.timer = new Timer();
    }

    @Override
    public void run() {
        int numreg_perpage = 10;
        int numRegistros = 0;
        List<ReporteOtDIRDto> listaCmtReporteEstadoActualOtDirHhppDto = new ArrayList<ReporteOtDIRDto>();
        try {
            CmtReporteEstadoActualOtDirHhppDto.cleanBeforeStart();
            CmtReporteEstadoActualOtDirHhppDto.startProcess(usuario);
        } catch (ApplicationException ex) {
            CmtReporteEstadoActualOtDirHhppDto.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        try {
            //consultar numero de registros y asiganr a siguiente linea
            numRegistros = cmtOrdenTrabajoReportThreadManager.getReporteOtDIRCount(params, contar,   0,   0,  usuario);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        try {
            if (numRegistros != 0) {
                CmtReporteEstadoActualOtDirHhppDto.setNumeroRegistrosAProcesar(numRegistros);
                if (numRegistros > 0) {
                    for (int page = 0; page <= (numRegistros / numreg_perpage); page++) {
                        LOGGER.error("pagina" + page);
                        int inicioRegistros = page * numreg_perpage;
                        listaCmtReporteEstadoActualOtDirHhppDto.addAll(cmtOrdenTrabajoReportThreadManager.getReporteOtDIR
                                (params, contar,   inicioRegistros,   numreg_perpage,  usuario));
                        CmtReporteEstadoActualOtDirHhppDto.setNumeroRegistrosProcesados(listaCmtReporteEstadoActualOtDirHhppDto.size());
                        CmtReporteEstadoActualOtDirHhppDto.setListReporteOtDIRDto(listaCmtReporteEstadoActualOtDirHhppDto);
                    }
                    CmtReporteEstadoActualOtDirHhppDto.setListReporteOtDIRDto(listaCmtReporteEstadoActualOtDirHhppDto);
                }
            }
        } catch (ApplicationException ex) {
            CmtReporteEstadoActualOtDirHhppDto.setIsProcessing(false);
            CmtReporteEstadoActualOtDirHhppDto.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        try {
            CmtReporteEstadoActualOtDirHhppDto.endProcess(listaCmtReporteEstadoActualOtDirHhppDto);
        } catch (ApplicationException ex) {
            CmtReporteEstadoActualOtDirHhppDto.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }

    }
}
