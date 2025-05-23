package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.cm.ReporteMglManager;
import co.com.claro.mgl.dtos.ArchivoReporteDTO;
import co.com.claro.mgl.dtos.EstadoReporteDTO;
import co.com.claro.mgl.dtos.ReporteConsultaQueryDTO;
import co.com.claro.mgl.error.ApplicationException;

import javax.ejb.Stateless;

/**
 * Clase que interactúa con procedimientos y funciones de CONSULTAS_FILTROS_EC_PKG
 * @author dayver.delahoz@vasslatam.com
 * @version Brief100417
 */
@Stateless
public class ReporteMglFacade implements IReporteMglFacadeLocal {
    /**
     * Consulta si el proceso está libre para generación de consultas
     * @param accion
     * @param idReporte
     * @param estado
     * @return
     * @throws ApplicationException
     */
    @Override
    public ReporteConsultaQueryDTO consultarEstadoReporte(String accion, Integer idReporte, String estado) throws ApplicationException {
        ReporteMglManager reporteMglManager = new ReporteMglManager();
        return reporteMglManager.consultarEstadoReporte(accion, idReporte, estado);
    }

    /**
     * Guargar registro de solicitud de consulta o actualización
     * @param estadoReporteDTO
     * @return
     * @throws ApplicationException
     */
    @Override
    public EstadoReporteDTO insertOrUpdateEstadoReporte(EstadoReporteDTO estadoReporteDTO) throws ApplicationException {
        ReporteMglManager reporteMglManager = new ReporteMglManager();
        return reporteMglManager.insertOrUpdateEstadoReporte(estadoReporteDTO);
    }

    /**
     * Validar registro de cargue por nombre de archivo a cargar.
     * @param idReporte
     * @param nombreArchivo
     * @return
     * @throws ApplicationException
     */
    @Override
    public ArchivoReporteDTO nombreArchivo(Integer idReporte, String nombreArchivo) throws ApplicationException {
        ReporteMglManager reporteMglManager = new ReporteMglManager();
        return reporteMglManager.nombreArchivo(idReporte, nombreArchivo);
    }
}
