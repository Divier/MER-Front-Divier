package co.com.claro.mer.error.facade;

import co.com.claro.mer.error.domain.models.dto.EmailErrorDto;

import javax.ejb.Local;

/**
 * Define las operaciones a realizar en la gestión de notificación por correo
 * de errores ocurridos en MER.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/25
 */
@Local
public interface EmailErrorFacadeLocal {

    /**
     * Notifica por correo un error ocurrido en la capa de presentación de la aplicación
     *
     * @param emailErrorDto {@link EmailErrorDto}
     * @author Gildardo Mora
     */
    void sendEmailErrorDetails(EmailErrorDto emailErrorDto);

}
