/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dtos.CmtReporteCuentaMatrizDtoMgl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author valbuenayf
 */
public class CmtRunableReporteCuentaMatrizManager implements Runnable {

    CmtTecnologiaSubMglManager cmMglManager;
    CmtTecnologiaSubMgl cmtTecnologiaSubMgl;
    String usuario;
    Map<String, Object> parametros;

    private static final Logger LOGGER = LogManager.getLogger(CmtRunableReporteCuentaMatrizManager.class);

    public CmtRunableReporteCuentaMatrizManager() {
    }

    public CmtRunableReporteCuentaMatrizManager(Map<String, Object> params, String usuario) {
        this.parametros = params;
        this.usuario = usuario;
        cmMglManager = new CmtTecnologiaSubMglManager();
    }

    @Override
    public void run() {
        int numreg_perpage = 1000;
        int numReg = 0;

        List<CmtTecnologiaSubMgl> cmMgls = new ArrayList<CmtTecnologiaSubMgl>();
        try {
            CmtReporteCuentaMatrizDtoMgl.cleanBeforeStart();
            CmtReporteCuentaMatrizDtoMgl.startProcess(usuario);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
        try {
            //consultar numero de registros y asignar a siguiente linea
            numReg = cmMglManager.findCountRepGeneralDetallado(parametros);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg, e);
        }
        CmtReporteCuentaMatrizDtoMgl.setNumeroRegistrosAProcesar(numReg);

        try {
            if (numReg > 0) {
                for (int page = 0; page <= (numReg / numreg_perpage); page++) {
                    int inicioRegistros = page * numreg_perpage;
                    List<CmtTecnologiaSubMgl> cmMglss = cmMglManager.findReporteGeneralDetallado(parametros, inicioRegistros, numreg_perpage);
                    if (cmMglss != null && !cmMglss.isEmpty()) {
                        cmMgls.addAll(cmMglss);
                    }
                    cmMglss = null;
                    CmtReporteCuentaMatrizDtoMgl.setNumeroRegistrosProcesados(cmMgls.size());
                }
            }
        } catch (Exception ex) {
            CmtReporteCuentaMatrizDtoMgl.setIsProcessing(false);
            CmtReporteCuentaMatrizDtoMgl.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
        try {
            CmtReporteCuentaMatrizDtoMgl.endProcess(cmMgls);
        } catch (ApplicationException ex) {
            CmtReporteCuentaMatrizDtoMgl.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
    }
}
