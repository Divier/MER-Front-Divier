package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.ArchivoReporteDTO;
import co.com.claro.mgl.dtos.EstadoReporteDTO;
import co.com.claro.mgl.dtos.ReporteConsultaQueryDTO;
import co.com.claro.mgl.error.ApplicationException;

public interface IReporteMglFacadeLocal {
    ReporteConsultaQueryDTO consultarEstadoReporte(String accion, Integer idReporte, String estado) throws ApplicationException;

    EstadoReporteDTO insertOrUpdateEstadoReporte(EstadoReporteDTO estadoReporteDTO) throws ApplicationException;

    ArchivoReporteDTO nombreArchivo(Integer idReporte, String nombreArchivo) throws ApplicationException;
}
