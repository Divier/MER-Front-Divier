/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dtos.CmtReporteDetalladoDto;
import co.com.claro.mgl.dtos.CmtReporteSolicitudesCcmmDtoMgl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

/**
 *
 * @author cardenaslb
 */
public class CmtRunableReporteDetalladoManager implements Runnable {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtRunableReporteDetalladoManager.class);

    private CmtSolicitudCmMglManager cmMglManager;
    String tipoReporte;
    CmtTipoSolicitudMgl cmtTipoSolicitudMgl;
    Date fechaInicio;
    Date fechaFin;
    BigDecimal estado;
    String usuario;
    Timer timer;

    private CmtRunableReporteDetalladoManager() {
    }

    public CmtRunableReporteDetalladoManager(String tipoReporte, CmtTipoSolicitudMgl cmtTipoSolicitudMgl, Date fechaInicio, Date fechaFin, BigDecimal estado, String usuario) {
        this.tipoReporte = tipoReporte;
        this.cmtTipoSolicitudMgl = cmtTipoSolicitudMgl;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.usuario = usuario;
        cmMglManager = new CmtSolicitudCmMglManager();
        this.timer = new Timer();
    }

    @Override
    public void run() {
        int numreg_perpage = 10;
        int numRegistros = 0;
        List<CmtReporteDetalladoDto> listaSolicitudesTotales = new ArrayList<CmtReporteDetalladoDto>();
        try {
            CmtReporteSolicitudesCcmmDtoMgl.cleanBeforeStart();
            CmtReporteSolicitudesCcmmDtoMgl.startProcess(usuario);
        } catch (ApplicationException ex) {
            CmtReporteSolicitudesCcmmDtoMgl.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
        
        try {
            if (numRegistros > 0) {
                CmtReporteSolicitudesCcmmDtoMgl.setNumeroRegistrosAProcesar(numRegistros);

               
                    for (int page = 0; page <= (numRegistros / numreg_perpage); page++) {
                        LOGGER.error("pagina" + page);
                        int inicioRegistros = page * numreg_perpage;
                        listaSolicitudesTotales.addAll(cmMglManager.getSolicitudesSearch(tipoReporte, cmtTipoSolicitudMgl, fechaInicio,
                                fechaFin, estado, inicioRegistros,
                                numreg_perpage, usuario,
                                listaSolicitudesTotales.size(), numRegistros));
                        CmtReporteSolicitudesCcmmDtoMgl.setNumeroRegistrosProcesados(listaSolicitudesTotales.size());
                        CmtReporteSolicitudesCcmmDtoMgl.setListCmtReporteDetalladoDto(listaSolicitudesTotales);
                    }
                    CmtReporteSolicitudesCcmmDtoMgl.setListSolicitudesTotalesReport(listaSolicitudesTotales);
                    CmtReporteSolicitudesCcmmDtoMgl.setNumeroRegistrosProcesados(listaSolicitudesTotales.size());
                
            }
        } catch (ApplicationException ex) {
            CmtReporteSolicitudesCcmmDtoMgl.setIsProcessing(false);
            CmtReporteSolicitudesCcmmDtoMgl.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
        try {
            CmtReporteSolicitudesCcmmDtoMgl.endProcess(listaSolicitudesTotales);
        } catch (ApplicationException ex) {
            CmtReporteSolicitudesCcmmDtoMgl.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }

    }
}
