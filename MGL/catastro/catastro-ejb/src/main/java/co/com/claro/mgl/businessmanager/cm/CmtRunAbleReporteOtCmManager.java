/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dtos.CmtReporteEstadoActualOtCMDto;
import co.com.claro.mgl.dtos.ReporteOtCMDto;
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
public class CmtRunAbleReporteOtCmManager implements Runnable{
    private static final Logger LOGGER = LogManager.getLogger(CmtRunAbleReporteOtCmManager.class);

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

    private CmtRunAbleReporteOtCmManager() {
    }

    public CmtRunAbleReporteOtCmManager(HashMap<String, Object> params, String usuario, List<CmtBasicaMgl> listaBasicaDir,
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
        List<ReporteOtCMDto> listaCmtReporteEstadoActualOtCMDto = new ArrayList<ReporteOtCMDto>();
        try {
            CmtReporteEstadoActualOtCMDto.cleanBeforeStart();
            CmtReporteEstadoActualOtCMDto.startProcess(usuario);
        } catch (ApplicationException ex) {
            CmtReporteEstadoActualOtCMDto.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }catch (Exception ex) {
            CmtReporteEstadoActualOtCMDto.setIsProcessing(false);
            CmtReporteEstadoActualOtCMDto.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        try {
            //consultar numero de registros y asiganr a siguiente linea
            numRegistros = cmtOrdenTrabajoReportThreadManager.getReporteEstadoActualOtCMCount(params, true,   0,   0,  usuario,null,null,null,null,null);
            CmtReporteEstadoActualOtCMDto.setNumeroRegistrosAProcesar(numRegistros);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        try {
            if (numRegistros != 0) {
                CmtReporteEstadoActualOtCMDto.setNumeroRegistrosAProcesar(numRegistros);
                if (numRegistros > 0) {
                    for (int page = 0; page <= (numRegistros / numreg_perpage); page++) {
                        LOGGER.error("pagina" + page);
                        int inicioRegistros = page * numreg_perpage;
                        listaCmtReporteEstadoActualOtCMDto.addAll(cmtOrdenTrabajoReportThreadManager.getReporteEstadoActualOtCM
                                (params, false,   inicioRegistros,   numreg_perpage,  usuario, listaBasicaDir,listaBasicaCm,listacmtRegionalMgl,
                                listacmtComunidadRr,
                                listaEstadosIntExt));
                        CmtReporteEstadoActualOtCMDto.setNumeroRegistrosProcesados(listaCmtReporteEstadoActualOtCMDto.size());
                        CmtReporteEstadoActualOtCMDto.setListReporteOtCMDto(listaCmtReporteEstadoActualOtCMDto);
                    }
                     numRegistros = cmtOrdenTrabajoReportThreadManager.getReporteEstadoActualOtCMCount(params, true,   0,   0,  usuario,null,null,null,null,null);
                    CmtReporteEstadoActualOtCMDto.setNumeroRegistrosAProcesar(numRegistros);
                    CmtReporteEstadoActualOtCMDto.setListReporteOtCMDto(listaCmtReporteEstadoActualOtCMDto);
                }
            }
        } catch (ApplicationException ex) {
            CmtReporteEstadoActualOtCMDto.setIsProcessing(false);
            CmtReporteEstadoActualOtCMDto.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }catch (Exception ex) {
            CmtReporteEstadoActualOtCMDto.setIsProcessing(false);
            CmtReporteEstadoActualOtCMDto.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        try {
            CmtReporteEstadoActualOtCMDto.endProcess(listaCmtReporteEstadoActualOtCMDto);
        } catch (ApplicationException ex) {
            CmtReporteEstadoActualOtCMDto.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }catch (Exception ex) {
            CmtReporteEstadoActualOtCMDto.setIsProcessing(false);
            CmtReporteEstadoActualOtCMDto.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }

    }
}
