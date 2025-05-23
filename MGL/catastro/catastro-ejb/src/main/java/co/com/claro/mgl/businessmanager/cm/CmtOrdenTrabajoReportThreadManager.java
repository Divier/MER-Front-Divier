/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtOrdenTrabajoMglReportDaoImpl;
import co.com.claro.mgl.dao.impl.cm.CmtOrdenTrabajoReportCmDirMglDaoImpl;
import co.com.claro.mgl.dtos.ReporteHistoricoOtCmDto;
import co.com.claro.mgl.dtos.ReporteHistoricoOtDIRDto;
import co.com.claro.mgl.dtos.ReporteOtCMDto;
import co.com.claro.mgl.dtos.ReporteOtDIRDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoIntxExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class CmtOrdenTrabajoReportThreadManager {
    CmtOrdenTrabajoMglReportDaoImpl dao = new CmtOrdenTrabajoMglReportDaoImpl();
    CmtOrdenTrabajoReportCmDirMglDaoImpl daoReport = new CmtOrdenTrabajoReportCmDirMglDaoImpl();
      /**
     * Clase RunAble para el reporte de las ordenes de direcciones
     *
     * @author Lenis Cardenas
     * @param params
     * @param usuario
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public void getReporteOtDIR(HashMap<String, Object> params, String usuario) throws ApplicationException {
        Thread thread = new Thread(new CmtRunAbleReporteOtDIRManager(params, usuario));
        thread.start();
    }
        
    /**
     * Registros del reporte ordenes de direcciones con rango
     *
     * @author Lenis Cardenas
     * @param params
     * @param contar
     * @param firstResult
     * @param usuario
     * @param maxResults
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public List<ReporteOtDIRDto> getReporteOtDIR(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario) throws ApplicationException {
        List<ReporteOtDIRDto> reporteHistoricoOtCmDto;
        reporteHistoricoOtCmDto = dao.getReporteEstadoActualOtDIR(params, true, firstResult, maxResults, usuario);
        return reporteHistoricoOtCmDto;
    }
     
     
      /**
     * Conteo de registros consulta ordenes de direcciones con rango
     *
     * @author Lenis Cardenas
     * @param params
     * @param contar
     * @param firstResult
     * @param usuario
     * @param maxResults
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
        public Integer getReporteOtDIRCount(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario) throws ApplicationException {
        Integer numeroRegistrosReporte;
        List<ReporteOtDIRDto> listaOrdenes;
        listaOrdenes = dao.getReporteEstadoActualOtDIR(params, contar, firstResult, maxResults, usuario);
        numeroRegistrosReporte = listaOrdenes.size();
        return numeroRegistrosReporte;
    }
        
        
              /**
     * Conteo total de registros de reporte ordenes de direcciones 
     *
     * @author Lenis Cardenas
     * @param params
     * @param usuario
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */

    public int getReporteOtDIRTotal(HashMap<String, Object> params, String usuario) throws ApplicationException {
        int numeroRegistrosReporte;
        List<ReporteOtDIRDto> listaReporteHistoricoOtCmDto=  dao.getReporteEstadoActualOtDIR(params, true, 0, 0, usuario);
        numeroRegistrosReporte = listaReporteHistoricoOtCmDto.size();
        return numeroRegistrosReporte;
    }
    
       /**
     * Clase RunAble para el reporte Historico de  ordenes cm
     *
     * @author Lenis Cardenas
     * @param params
     * @param usuario
     * @param listaBasicaDir
     * @param listaBasicaCm
     * @param listacmtRegionalMgl
     * @param listacmtComunidadRr
     * @param listaEstadosIntExt
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public List<ReporteHistoricoOtDIRDto> getReporteHistoricoOtDIR(HashMap<String, Object> params, String usuario, List<CmtBasicaMgl> listaBasicaDir,
            List<CmtBasicaMgl> listaBasicaCm,
            List<CmtRegionalRr> listacmtRegionalMgl,
            List<CmtComunidadRr> listacmtComunidadRr,
            List<CmtEstadoIntxExtMgl> listaEstadosIntExt) throws ApplicationException {
        List<ReporteHistoricoOtDIRDto> listaHist;
        CmtOrdenTrabajoReportThreadManager cmtOrdenTrabajoReportThreadManager = new CmtOrdenTrabajoReportThreadManager();
        listaHist = cmtOrdenTrabajoReportThreadManager.getReporteHistoricoOtCMDIR(params, true, 0, 0, usuario, listaBasicaDir, listaBasicaCm, listacmtRegionalMgl, listacmtComunidadRr, listaEstadosIntExt);
        return listaHist;
    }
    
           
              /**
     * Conteo total de registros de reporte Historico ot de cm 
     *
     * @author Lenis Cardenas
     * @param params
     * @param usuario
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */

    public int getReporteHistoricoOtDIRTotal(HashMap<String, Object> params, String usuario) throws ApplicationException {
        int numeroRegistrosReporte;
       numeroRegistrosReporte=  dao.getCountgetReporteHistoricoOtDIR(params, true, 0, 0, usuario);
        return numeroRegistrosReporte;
    }
    
         
      /**
     * Conteo de registros del reporte de Historico de las ordenes cm con rango
     *
     * @author Lenis Cardenas
     * @param params
     * @param contar
     * @param firstResult
     * @param usuario
     * @param maxResults
     * @param listaBasicaDir
     * @param listaBasicaCm
     * @param listacmtRegionalMgl
     * @param listacmtComunidadRr
     * @param listaEstadosIntExt
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public Integer getReporteHistoricoOtDIRCount(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario, List<CmtBasicaMgl> listaBasicaDir,
            List<CmtBasicaMgl> listaBasicaCm,
            List<CmtRegionalRr> listacmtRegionalMgl,
            List<CmtComunidadRr> listacmtComunidadRr,
            List<CmtEstadoIntxExtMgl> listaEstadosIntExt) throws ApplicationException {
        Integer numeroRegistrosReporte;
        numeroRegistrosReporte = dao.getCountgetReporteHistoricoOtDIR(params, contar, firstResult, maxResults, usuario);
        return numeroRegistrosReporte;
    }
        
          /**
     * Registros del reporte de Historico de  ordenes cm con rango
     *
     * @author Lenis Cardenas
     * @param params
     * @param contar
     * @param firstResult
     * @param usuario
     * @param maxResults
     * @param listaBasicaDir
     * @param listaBasicaCm
     * @param listacmtRegionalMgl
     * @param listacmtComunidadRr
     * @param listaEstadosIntExt
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public List<ReporteHistoricoOtDIRDto> getReporteHistoricoOtCMDIR(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario, List<CmtBasicaMgl> listaBasicaDir,
            List<CmtBasicaMgl> listaBasicaCm,
            List<CmtRegionalRr> listacmtRegionalMgl,
            List<CmtComunidadRr> listacmtComunidadRr,
            List<CmtEstadoIntxExtMgl> listaEstadosIntExt) throws ApplicationException {
        List<ReporteHistoricoOtDIRDto> reporteHistoricoOtCmDto;
        reporteHistoricoOtCmDto = dao.getReporteHistoricoOtDIR(params, true, firstResult, maxResults, usuario, listaBasicaDir,
                listaBasicaCm,
                listacmtRegionalMgl,
                listacmtComunidadRr,
                listaEstadosIntExt);
        return reporteHistoricoOtCmDto;
    }
         
    /**
     * Clase RunAble para el reporte de las ordenes cm
     *
     * @author Lenis Cardenas
     * @param params
     * @param usuario
     * @param listaBasicaDir
     * @param listaBasicaCm
     * @param listacmtRegionalMgl
     * @param listacmtComunidadRr
     * @param listaEstadosIntExt
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public List<ReporteOtCMDto> getReporteOtCM(HashMap<String, Object> params, String usuario,List<CmtBasicaMgl> listaBasicaDir,
            List<CmtBasicaMgl> listaBasicaCm,
            List<CmtRegionalRr> listacmtRegionalMgl,
            List<CmtComunidadRr> listacmtComunidadRr,
            List<CmtEstadoIntxExtMgl> listaEstadosIntExt) throws ApplicationException {
        List<ReporteOtCMDto> listOrdenesCcmmDir;
        
        listOrdenesCcmmDir =daoReport.getReporteEstadoActualOtCM(params, true, 0, 0, usuario, 
               listaBasicaDir, listaBasicaCm, listacmtRegionalMgl, listacmtComunidadRr, listaEstadosIntExt);
       return listOrdenesCcmmDir;
    }
  
       /**
     * Conteo de registros del reporte de las ordenes cm con rango
     *
     * @author Lenis Cardenas
     * @param params
     * @param contar
     * @param firstResult
     * @param usuario
     * @param maxResults
     * @param listaBasicaDir
     * @param listaBasicaCm
     * @param listacmtRegionalMgl
     * @param listacmtComunidadRr
     * @param listaEstadosIntExt
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public Integer getReporteEstadoActualOtCMCount(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario, List<CmtBasicaMgl> listaBasicaDir,
            List<CmtBasicaMgl> listaBasicaCm,
            List<CmtRegionalRr> listacmtRegionalMgl,
            List<CmtComunidadRr> listacmtComunidadRr,
            List<CmtEstadoIntxExtMgl> listaEstadosIntExt) throws ApplicationException {
        Integer numeroRegistrosReporte;
        numeroRegistrosReporte = daoReport.getCountReporteEstadoActualOtCM(params, contar, firstResult, maxResults, usuario);
        return numeroRegistrosReporte;
    }
 /**
     * Registros del reporte  ordenes cm con rango
     *
     * @author Lenis Cardenas
     * @param params
     * @param contar
     * @param firstResult
     * @param usuario
     * @param maxResults
     * @param listaBasicaDir
     * @param listaBasicaCm
     * @param listacmtRegionalMgl
     * @param listacmtComunidadRr
     * @param listaEstadosIntExt
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public List<ReporteOtCMDto> getReporteEstadoActualOtCM(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario,List<CmtBasicaMgl> listaBasicaDir,
            List<CmtBasicaMgl> listaBasicaCm,
            List<CmtRegionalRr> listacmtRegionalMgl,
            List<CmtComunidadRr> listacmtComunidadRr,
            List<CmtEstadoIntxExtMgl> listaEstadosIntExt) throws ApplicationException {
        List<ReporteOtCMDto> cmtReporteEstadoActualOtCMDto;
        cmtReporteEstadoActualOtCMDto = daoReport.getReporteEstadoActualOtCM(params, contar, firstResult, maxResults, usuario,
                listaBasicaDir,
                listaBasicaCm,
                listacmtRegionalMgl,
                listacmtComunidadRr,
                listaEstadosIntExt);
        return cmtReporteEstadoActualOtCMDto;
    }
    
        /**
     * Conteo total de registros de reporte ot de cm sin rango
     *
     * @author Lenis Cardenas
     * @param params
     * @param usuario
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */

    public int getReporteEstadoActualOtTotal(HashMap<String, Object> params, String usuario) throws ApplicationException {
        int numeroRegistrosReporte;
        numeroRegistrosReporte =  daoReport.getCountReporteEstadoActualOtCM(params, true, 0, 0, usuario);
        return numeroRegistrosReporte;
    }
    
    /**
     * Registros del reporte de Historico de  ordenes cm con rango
     *
     * @author Lenis Cardenas
     * @param params
     * @param contar
     * @param firstResult
     * @param usuario
     * @param maxResults
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
     public List<ReporteHistoricoOtCmDto> getReporteHistoricoOtCM(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario) throws ApplicationException {
        List<ReporteHistoricoOtCmDto> reporteHistoricoOtCmDto;
        reporteHistoricoOtCmDto = dao.getReporteHistoricoOtCM(params, true, firstResult, maxResults, usuario);
        return reporteHistoricoOtCmDto;
    }
     
     
      /**
     * Conteo de registros del reporte de Historico de las ordenes cm con rango
     *
     * @author Lenis Cardenas
     * @param params
     * @param contar
     * @param firstResult
     * @param usuario
     * @param maxResults
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
        public Integer getReporteHistoricoOtCMCount(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario) throws ApplicationException {
        Integer numeroRegistrosReporte;
        numeroRegistrosReporte = dao.getCountReporteHistoricoOtCm(params, contar, firstResult, maxResults, usuario);
        return numeroRegistrosReporte;
    }
        
        
              /**
     * Conteo total de registros de reporte Historico ot de cm 
     *
     * @author Lenis Cardenas
     * @param params
     * @param usuario
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */

    public int getReporteHistoricoOtTotal(HashMap<String, Object> params, String usuario) throws ApplicationException {
        int numeroRegistrosReporte;
        numeroRegistrosReporte=  dao.getCountReporteHistoricoOtCm(params, true, 0, 0, usuario);
        return numeroRegistrosReporte;
    }
    
    
    
        /**
     * Clase RunAble para el reporte Historico de  ordenes cm
     *
     * @author Lenis Cardenas
     * @param params
     * @param usuario
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public void getReporteHistoricoOtCM(HashMap<String, Object> params, String usuario) throws ApplicationException {
        Thread thread = new Thread(new CmtRunAbleReporteHistoricoOtCmManager(params, usuario));
        thread.start();
    }
    
        
    public int getCountReporteHistoricoOtCM(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario) throws ApplicationException {
        int countOrdenes;
        countOrdenes = dao.getCountReporteHistoricoOtCm(params, contar, firstResult, maxResults, usuario);
        return countOrdenes;
    }
    
}
