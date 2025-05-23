package co.com.claro.mer.error.application.services;

import co.com.claro.mer.email.application.ports.IEmailParameterPort;
import co.com.claro.mer.email.application.ports.ISendEmailPort;
import co.com.claro.mer.email.application.services.ISendEmailService;
import co.com.claro.mer.email.application.services.impl.SendEmailService;
import co.com.claro.mer.error.application.ports.IErrorPort;
import co.com.claro.mer.error.application.usecases.CreateEmailErrorMessage;
import co.com.claro.mer.error.application.usecases.ObtainErrorDetails;
import co.com.claro.mer.error.domain.models.dto.EmailErrorDto;
import co.com.claro.mer.error.domain.ports.IErrorDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Procesos asociados a la notificación de errores ocurridos en MER
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/25
 */
@Log4j2
@RequiredArgsConstructor
public class ErrorService {

    private final IEmailParameterPort emailParameterPort;
    private final ISendEmailPort sendEmailPort;
    private final IErrorPort errorPort;

    /**
     * Notifica por correo el error ocurrido en la capa presentación
     *
     * @param emailErrorDto {@link EmailErrorDto}
     * @author Gildardo Mora
     */
    public void notifyError(EmailErrorDto emailErrorDto) {
        Runnable sendErrorEmailTask = () -> {
            try {
                if (!errorPort.isNotifyErrorActive()) {
                    LOGGER.error("La notificación de errores de MER está apagada. ");
                    return;
                }

                IErrorDetails errorDetails = new CreateEmailErrorMessage(errorPort);
                ObtainErrorDetails obtainErrorDetails = new ObtainErrorDetails();
                String msgErrorDetails = obtainErrorDetails.obtainDetails(emailErrorDto.getException());
                emailErrorDto.setMessage(msgErrorDetails);
                String messageRequest = errorDetails.createMessageRequest(emailErrorDto);
                ISendEmailService emailService = new SendEmailService(emailParameterPort, sendEmailPort);
                emailService.sendEmail(messageRequest);

            } catch (Exception e) {
                LOGGER.error("Ocurrió un problema al enviar el correo de notificación de errores de MER: "
                        + e.getMessage(), e);
            }
        };

        Thread thread = new Thread(sendErrorEmailTask);
        thread.start();
    }

}
