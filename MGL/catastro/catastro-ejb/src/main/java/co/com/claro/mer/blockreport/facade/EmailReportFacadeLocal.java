package co.com.claro.mer.blockreport.facade;

import co.com.claro.mer.blockreport.domain.models.dto.EmailDetailsReportDto;

import javax.ejb.Local;

/**
 * Definición de operaciones de procesos de envío de correos.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/12
 */
@Local
public interface EmailReportFacadeLocal {

    /**
     * Realiza el envío de correos con los detalles del proceso de reporte
     * o exportación de datos que se esté procesando en MER.
     *
     * @param emailDetailsReportDto {@link EmailDetailsReportDto}
     * @author Gildardo Mora
     */
    void sendEmailReportDetails(EmailDetailsReportDto emailDetailsReportDto);

}
