package co.com.claro.mer.email.application.ports;

import co.com.claro.cmas400.ejb.request.RequestEmail;
import co.com.claro.cmas400.ejb.respons.ResponseEmail;

/**
 * Define los procesos para usar como puerto de comunicación
 * para el consumo de servicio de envío de correos.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/17
 */
public interface ISendEmailPort {

    /**
     * Procesa el envío del correo
     *
     * @param request {@link RequestEmail}
     * @return {@link ResponseEmail}
     * @author Gildardo Mora
     */
    ResponseEmail sendEmail(RequestEmail request);

}
