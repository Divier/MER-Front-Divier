package co.com.claro.mer.blockreport.domain.ports;

import co.com.claro.mer.blockreport.domain.models.dto.EmailDetailsReportDto;
import co.com.claro.mgl.error.ApplicationException;

/**
 * Define las operaciones del proceso de envío de correos de notificación de
 * ejecución de reportes.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/13
 */
public interface ISendEmailOfReportDetails {

    /**
     * Crea el mensaje formateado para el request,
     * requerido para consumir el servicio de envío de correos
     *
     * @param emailDetailsReportDto {@link EmailDetailsReportDto}
     * @return {@link String}
     * @throws ApplicationException Excepción al momento de crear el mensaje
     * @author Gildardo Mora
     */
    String createMessageRequest(EmailDetailsReportDto emailDetailsReportDto) throws ApplicationException;

}
