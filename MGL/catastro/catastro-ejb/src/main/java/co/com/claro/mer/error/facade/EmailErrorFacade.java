package co.com.claro.mer.error.facade;

import co.com.claro.mer.email.application.ports.IEmailParameterPort;
import co.com.claro.mer.email.application.ports.ISendEmailPort;
import co.com.claro.mer.email.infraestructure.adapters.ParameterEmailAdapter;
import co.com.claro.mer.email.infraestructure.adapters.SendEmailAdapter;
import co.com.claro.mer.error.application.ports.IErrorPort;
import co.com.claro.mer.error.application.services.ErrorService;
import co.com.claro.mer.error.domain.models.dto.EmailErrorDto;
import co.com.claro.mer.error.infraestructure.adapters.ParameterErrorAdapter;
import lombok.extern.log4j.Log4j2;

import javax.ejb.Stateless;
import java.util.Objects;

/**
 * Procesa las operaciones notificaciones por correo de errores ocurridos en la aplicación.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/25
 */
@Stateless
@Log4j2
public class EmailErrorFacade implements EmailErrorFacadeLocal {

    /**
     * Notifica por correo un error ocurrido en la capa de presentación de la aplicación
     *
     * @param emailErrorDto {@link EmailErrorDto}
     * @author Gildardo Mora
     */
    @Override
    public void sendEmailErrorDetails(EmailErrorDto emailErrorDto) {
        if (Objects.isNull(emailErrorDto)) {
            LOGGER.error("No se recibió detalles del error ocurrido, no se enviará correo.. ");
            return;
        }

        nofifyError(emailErrorDto);
    }

    /**
     * Notifica los detalles del error ocurrido a través de correo.
     *
     * @param emailErrorDto {@link EmailErrorDto}
     * @author Gildardo Mora
     */
    private void nofifyError(EmailErrorDto emailErrorDto) {
        try {
            IEmailParameterPort emailParameterPort = new ParameterEmailAdapter();
            String url = emailParameterPort.findUrlEmailService();
            ISendEmailPort sendEmailPort = new SendEmailAdapter(url);
            IErrorPort errorPort = new ParameterErrorAdapter();
            ErrorService errorService = new ErrorService(emailParameterPort, sendEmailPort, errorPort);
            errorService.notifyError(emailErrorDto);

        } catch (Exception e) {
            String msgError = "Ocurrió un problema al enviar el correo con los detalles de error"
                    + " generado en la aplicación.";
            LOGGER.error(msgError, e);
        }
    }

}
