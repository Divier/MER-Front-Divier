package co.com.claro.mer.blockreport;


import co.com.claro.mer.blockreport.application.ports.IReportParameterPort;
import co.com.claro.mer.blockreport.application.services.ReportDetailService;
import co.com.claro.mer.blockreport.domain.models.dto.EmailDetailsReportDto;
import co.com.claro.mer.blockreport.infraestructure.adapters.ParameterReportAdapter;
import co.com.claro.mer.email.application.ports.IEmailParameterPort;
import co.com.claro.mer.email.application.ports.ISendEmailPort;
import co.com.claro.mer.email.infraestructure.adapters.ParameterEmailAdapter;
import co.com.claro.mer.email.infraestructure.adapters.SendEmailAdapter;
import co.com.claro.mgl.error.ApplicationException;
import lombok.extern.log4j.Log4j2;

/**
 * Utilitario para coordinar operaciones con correos.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/08/17
 */
@Log4j2
public class EmailReportUtil {

    /**
     * Envía correo para notificar proceso de exportación de datos o generación de reportes en MER
     *
     * @param detailsReportDto {@link EmailDetailsReportDto}
     * @author Gildardo Mora
     */
    public void sendEmailDetailsReport(EmailDetailsReportDto detailsReportDto) {
        try {
            IEmailParameterPort emailParameterPort = new ParameterEmailAdapter();
            IReportParameterPort parameterPort = new ParameterReportAdapter();
            String url = emailParameterPort.findUrlEmailService();
            ISendEmailPort sendEmailPort = new SendEmailAdapter(url);
            ReportDetailService reportDetailService = new ReportDetailService(emailParameterPort, parameterPort, sendEmailPort);
            reportDetailService.sendEmailReportDetails(detailsReportDto);
        } catch (ApplicationException e) {
            String msgError = "Error al enviar el correo de detalle de reportes: " + e.getMessage();
            LOGGER.error(msgError, e);
        }
    }

}
