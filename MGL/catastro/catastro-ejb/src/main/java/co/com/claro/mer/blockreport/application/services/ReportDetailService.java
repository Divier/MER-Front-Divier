package co.com.claro.mer.blockreport.application.services;

import co.com.claro.cmas400.ejb.respons.ResponseEmail;
import co.com.claro.mer.blockreport.application.ports.IReportParameterPort;
import co.com.claro.mer.blockreport.application.usecases.SendEmailOfReportDetails;
import co.com.claro.mer.blockreport.domain.models.dto.EmailDetailsReportDto;
import co.com.claro.mer.blockreport.domain.ports.ISendEmailOfReportDetails;
import co.com.claro.mer.email.application.ports.IEmailParameterPort;
import co.com.claro.mer.email.application.ports.ISendEmailPort;
import co.com.claro.mer.email.application.services.ISendEmailService;
import co.com.claro.mer.email.application.services.impl.SendEmailService;
import co.com.claro.mgl.error.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Procesos asociados al bloqueo de generación de reportes
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/17
 */
@Log4j2
@RequiredArgsConstructor
public class ReportDetailService {
    private final IEmailParameterPort emailParameters;
    private final IReportParameterPort parameterPort;
    private final ISendEmailPort sendEmailPort;

    /**
     * Realiza el envío de correos de notificación de reportes
     * o exportación de datos en proceso.
     *
     * @param emailDetailsReportDto {@link EmailDetailsReportDto}
     * @author Gildardo Mora
     */
    public void sendEmailReportDetails(EmailDetailsReportDto emailDetailsReportDto) {
        Runnable sendReportEmailTask = () -> {
            try {
                if (!parameterPort.isNotifyReportGenerationActive()) {
                    LOGGER.info("La notificación de generación de reportes está apagada. ");
                    return;
                }

                ISendEmailOfReportDetails emailOfReportDetails = new SendEmailOfReportDetails(parameterPort);
                String messageRequest = emailOfReportDetails.createMessageRequest(emailDetailsReportDto);
                ISendEmailService emailService = new SendEmailService(emailParameters, sendEmailPort);
                ResponseEmail responseEmail = emailService.sendEmail(messageRequest);

                if (responseEmail.getIsValid().equalsIgnoreCase(Boolean.FALSE.toString())) {
                    LOGGER.error(responseEmail.getMessage());
                }

            } catch (ApplicationException ae) {
                String msgError = "Error al enviar el correo con los detalles del reporte: " + ae.getMessage();
                LOGGER.error(msgError, ae);
            } catch (Exception e) {
                String msgError = "Ocurrió un error al enviar el correo de detalles del reporte: " + e.getMessage();
                LOGGER.error(msgError, e);
            }
        };

        Thread thread = new Thread(sendReportEmailTask);
        thread.start();
    }

}
