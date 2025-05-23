package co.com.claro.mer.email.infraestructure.adapters;

import co.com.claro.cmas400.ejb.request.RequestEmail;
import co.com.claro.cmas400.ejb.respons.ResponseEmail;
import co.com.claro.mer.email.application.ports.ISendEmailPort;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientGeneric;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;

/**
 * Adaptador para gestionar el consumo del servicio de envío de correos
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/13
 */
@Log4j2
@AllArgsConstructor
public class SendEmailAdapter implements ISendEmailPort {
    private String url;

    /**
     * Procesa el envío del correo
     *
     * @param request {@link RequestEmail}
     * @return {@link ResponseEmail}
     * @author Gildardo Mora
     */
    @Override
    public ResponseEmail sendEmail(RequestEmail request) {
        ResponseEmail responseEmail = new ResponseEmail();

        try {
            Objects.requireNonNull(request, "No se puede enviar correo porque no se recibió el request");
            RestClientGeneric client = new RestClientGeneric(url, "PutMessage");
            responseEmail = client.callWebServiceMethodPut(ResponseEmail.class, request);
            LOGGER.info("Respuesta servicio {}", responseEmail);

        } catch (Exception ae) {
            LOGGER.error("Error al enviar el correo: ", ae);
            responseEmail.setIsValid(Boolean.FALSE.toString());
            responseEmail.setMessage(ae.getMessage());
        }

        return responseEmail;
    }
}
