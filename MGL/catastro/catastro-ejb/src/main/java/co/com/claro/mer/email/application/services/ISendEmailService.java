package co.com.claro.mer.email.application.services;

import co.com.claro.cmas400.ejb.respons.ResponseEmail;

/**
 * Define los tipos de envío de correo
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/12
 */
public interface ISendEmailService {

    /**
     * Realiza el envío del correo a partir del mensaje previamente formateado
     * con la estructura requerida por el servicio.
     *
     * @param formattedMessage {@link String}
     * @return {@link ResponseEmail}
     */
    ResponseEmail sendEmail(String formattedMessage);
}
