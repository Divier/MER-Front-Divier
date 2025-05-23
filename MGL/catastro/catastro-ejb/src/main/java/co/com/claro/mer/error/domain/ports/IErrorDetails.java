package co.com.claro.mer.error.domain.ports;

import co.com.claro.mer.error.domain.models.dto.EmailErrorDto;
import co.com.claro.mgl.error.ApplicationException;

/**
 * Define las operaciones de interacción con el proceso de detalles de errores.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/25
 */
public interface IErrorDetails {

    /**
     * Crea el mensaje con los detalles del error con la estructura requerida.
     *
     * @param emailErrorDto {@link EmailErrorDto}
     * @return {@link String}
     * @throws ApplicationException Excepción en caso de error al crear el mensaje.
     * @author Gildardo Mora
     */
    String createMessageRequest(EmailErrorDto emailErrorDto) throws ApplicationException;

}
