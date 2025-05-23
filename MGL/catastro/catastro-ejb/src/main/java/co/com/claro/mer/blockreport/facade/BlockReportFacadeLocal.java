package co.com.claro.mer.blockreport.facade;

import co.com.claro.mgl.error.ApplicationException;

import javax.ejb.Local;
import java.util.Map;

/**
 * Fachada que define los procesos implicados en el bloqueo de generación de reportes.
 *
 * @version 1.0, 2023/09/07
 * @author Gildardo Mora
 */
@Local
public interface BlockReportFacadeLocal {

    /**
     * Realiza la validación para determinar si está permitido generar un reporte
     * o exportar datos de un proceso específico.
     *
     * @param nameReport {@link String} Nombre del reporte o proceso de exportación
     *                                      que se quiere validar.
     * @return {@code Map<String, Object>} Retorna los datos de la validación realizada.
     * @author Gildardo Mora
     */
    Map<String, Object> validateReportGeneration(String nameReport) throws ApplicationException;

}
