package co.com.claro.mer.blockreport.facade;

import co.com.claro.mer.blockreport.application.ports.IReportParameterPort;
import co.com.claro.mer.blockreport.application.services.ReportDetailService;
import co.com.claro.mer.blockreport.domain.models.dto.EmailDetailsReportDto;
import co.com.claro.mer.blockreport.infraestructure.adapters.ParameterReportAdapter;
import co.com.claro.mer.email.application.ports.IEmailParameterPort;
import co.com.claro.mer.email.application.ports.ISendEmailPort;
import co.com.claro.mer.email.infraestructure.adapters.ParameterEmailAdapter;
import co.com.claro.mer.email.infraestructure.adapters.SendEmailAdapter;
import lombok.extern.log4j.Log4j2;

import javax.ejb.Stateless;

/**
 * Fachada que realiza la gestión de los casos de uso correspondientes
 * a envío de correos.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/12
 */
@Stateless
@Log4j2
public class EmailReportFacade implements EmailReportFacadeLocal {

    /**
     * Realiza el envío de correos con los detalles del proceso de reporte
     * o exportación de datos que se esté procesando en MER.
     *
     * @param emailDetailsReportDto {@link EmailDetailsReportDto}
     * @author Gildardo Mora
     */
    @Override
    public void sendEmailReportDetails(EmailDetailsReportDto emailDetailsReportDto) {
        try {
            IEmailParameterPort emailParameters = new ParameterEmailAdapter();
            String url = emailParameters.findUrlEmailService();
            ISendEmailPort sendEmailPort = new SendEmailAdapter(url);
            IReportParameterPort parameterPort = new ParameterReportAdapter();
            ReportDetailService reportDetailService = new ReportDetailService(emailParameters, parameterPort, sendEmailPort);
            reportDetailService.sendEmailReportDetails(emailDetailsReportDto);
        } catch (Exception e) {
            String msgError = "Error al enviar el correo de detalle de reportes: " + e.getMessage();
            LOGGER.error(msgError, e);
        }
    }

}
