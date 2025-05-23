/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dtos.CmtReporteHistoricoOtDIRDto;
import co.com.claro.mgl.dtos.ReporteHistoricoOtDIRDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoIntxExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
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
public class CmtRunAbleReporteHistoricoOtDIRManager  implements Runnable{
    private static final Logger LOGGER = LogManager.getLogger(CmtRunAbleReporteHistoricoOtDIRManager.class);

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
    List<CmtBasicaMgl> listaBasicaDir;
    List<CmtBasicaMgl> listaBasicaCm;
    List<CmtRegionalRr> listacmtRegionalMgl;
    List<CmtComunidadRr> listacmtComunidadRr;
    List<CmtEstadoIntxExtMgl> listaEstadosIntExt;

    private CmtRunAbleReporteHistoricoOtDIRManager() {
    }

    public CmtRunAbleReporteHistoricoOtDIRManager(HashMap<String, Object> params, String usuario,
            List<CmtBasicaMgl> listaBasicaDir,
            List<CmtBasicaMgl> listaBasicaCm,
            List<CmtRegionalRr> listacmtRegionalMgl,
            List<CmtComunidadRr> listacmtComunidadRr,
            List<CmtEstadoIntxExtMgl> listaEstadosIntExt) {
        this.params = params;
        this.usuario = usuario;
        this.cmtOrdenTrabajoReportThreadManager = new CmtOrdenTrabajoReportThreadManager();
        this.timer = new Timer();
        this.listaBasicaDir = listaBasicaDir;
        this.listaBasicaCm = listaBasicaCm;
        this.listacmtRegionalMgl = listacmtRegionalMgl;
        this.listacmtComunidadRr = listacmtComunidadRr;
        this.listaEstadosIntExt = listaEstadosIntExt;
    }

    @Override
    public void run() {
        int numreg_perpage = 0;
        int numRegistros = 0;
        numRegistros = (int) this.params.get("regAPro");
        numreg_perpage = (int) this.params.get("cantRegConsulta");
        List<ReporteHistoricoOtDIRDto> listaReporteHistoricoOtDIRDto = new ArrayList<ReporteHistoricoOtDIRDto>();
        try {
            CmtReporteHistoricoOtDIRDto.cleanBeforeStart();
            CmtReporteHistoricoOtDIRDto.startProcess(usuario);
        } catch (ApplicationException ex) {
            CmtReporteHistoricoOtDIRDto.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
        }
        try {
            // consultar numero de registros y asiganr a siguiente linea
            numRegistros = cmtOrdenTrabajoReportThreadManager.getReporteHistoricoOtDIRCount(params, contar, 0, 0,
                    usuario, null, null, null, null, null);
            CmtReporteHistoricoOtDIRDto.setNumeroRegistrosAProcesar(numRegistros);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
        }
        try {
            if (numRegistros != 0 && numRegistros > 0) {
                for (int page = 0; page <= (numRegistros / numreg_perpage); page++) {
                    LOGGER.error("pagina" + page);
                    CmtReporteHistoricoOtDIRDto.setNumeroRegistrosProcesados(listaReporteHistoricoOtDIRDto.size());
                    CmtReporteHistoricoOtDIRDto.setListCReporteHistoricoOtDIRDto(listaReporteHistoricoOtDIRDto);
                }
                numRegistros = cmtOrdenTrabajoReportThreadManager.getReporteHistoricoOtDIRCount(params, contar, 0, 0,
                        usuario, null, null, null, null, null);
                CmtReporteHistoricoOtDIRDto.setNumeroRegistrosAProcesar(numRegistros);
                CmtReporteHistoricoOtDIRDto.setListCReporteHistoricoOtDIRDto(listaReporteHistoricoOtDIRDto);
            }
        } catch (ApplicationException ex) {
            CmtReporteHistoricoOtDIRDto.setIsProcessing(false);
            CmtReporteHistoricoOtDIRDto.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
        }
        try {
            CmtReporteHistoricoOtDIRDto.setNumeroRegistrosProcesados(listaReporteHistoricoOtDIRDto.size());
            CmtReporteHistoricoOtDIRDto.endProcess(listaReporteHistoricoOtDIRDto);
        } catch (ApplicationException ex) {
            CmtReporteHistoricoOtDIRDto.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
        }

    }
}
