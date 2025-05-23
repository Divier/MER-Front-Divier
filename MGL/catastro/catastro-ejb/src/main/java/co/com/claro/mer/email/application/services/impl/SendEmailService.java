package co.com.claro.mer.email.application.services.impl;

import co.com.claro.cmas400.ejb.request.RequestEmail;
import co.com.claro.cmas400.ejb.respons.ResponseEmail;
import co.com.claro.mer.email.application.ports.IEmailParameterPort;
import co.com.claro.mer.email.application.ports.ISendEmailPort;
import co.com.claro.mer.email.application.services.ISendEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;

/**
 * @author Gildardo Mora
 * @version 1.0, 2023/10/12
 */
@Log4j2
@RequiredArgsConstructor
public class SendEmailService implements ISendEmailService {

    private final IEmailParameterPort emailParameters;
    private final ISendEmailPort sendEmail;


    /**
     * Realiza el envío del correo a partir del mensaje previamente formateado
     * con la estructura requerida por el servicio.
     *
     * @param formattedMessage {@link String}
     * @return {@link ResponseEmail}
     */
    public ResponseEmail sendEmail(String formattedMessage) {
        ResponseEmail responseEmail = new ResponseEmail();

        try {
            Objects.requireNonNull(formattedMessage, "No se puede enviar correo porque no se recibió el request");
            String system = emailParameters.findSystemEmailService();
            String user = emailParameters.findUserEmailService();
            String pass = emailParameters.findPassEmailService();

            RequestEmail request = new RequestEmail(formattedMessage, system, user, pass);
            request.getHeaderRequest().setSystem(system);
            request.getHeaderRequest().setUser(user);
            request.getHeaderRequest().setPassword(pass);
            responseEmail = sendEmail.sendEmail(request);
            LOGGER.info("Respuesta servicio {}", responseEmail);

        } catch (Exception ae) {
            LOGGER.error("Error : ", ae);
            responseEmail.setIsValid(Boolean.FALSE.toString());
            responseEmail.setMessage(ae.getMessage());
        }

        return responseEmail;
    }

}
