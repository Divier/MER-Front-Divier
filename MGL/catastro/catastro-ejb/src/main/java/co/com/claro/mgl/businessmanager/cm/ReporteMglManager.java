package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.ReporteMglManagerDaoImpl;
import co.com.claro.mgl.dtos.ArchivoReporteDTO;
import co.com.claro.mgl.dtos.EstadoReporteDTO;
import co.com.claro.mgl.dtos.ReporteConsultaQueryDTO;
import co.com.claro.mgl.error.ApplicationException;

/**
 * Manager que llama procedimientos y funciones de DAO que consume CONSULTAS_FILTROS_EC_PKG
 * @author dayver.delahoz@vasslatam.com
 * @version Brief100417
 */
public class ReporteMglManager {
    public ReporteConsultaQueryDTO consultarEstadoReporte(String accion, Integer idReporte, String estado) throws ApplicationException {
        ReporteMglManagerDaoImpl reporteMglManagerDaoImpl = new ReporteMglManagerDaoImpl();
        return reporteMglManagerDaoImpl.consultaEstadoReporte(accion, idReporte, estado);
    }

    public EstadoReporteDTO insertOrUpdateEstadoReporte(EstadoReporteDTO estadoReporteDTO) throws ApplicationException {
        ReporteMglManagerDaoImpl reporteMglManagerDaoImpl = new ReporteMglManagerDaoImpl();
        return reporteMglManagerDaoImpl.insertOrUpdateEstadoReporte(estadoReporteDTO);
    }

    public ArchivoReporteDTO nombreArchivo(Integer idReporte, String nombreArchivo) {
        ReporteMglManagerDaoImpl reporteMglManagerDaoImpl = new ReporteMglManagerDaoImpl();
        return reporteMglManagerDaoImpl.nombreArchivo(idReporte, nombreArchivo);
    }
}
