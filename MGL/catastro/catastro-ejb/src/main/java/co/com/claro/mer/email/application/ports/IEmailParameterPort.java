package co.com.claro.mer.email.application.ports;

import co.com.claro.mgl.error.ApplicationException;

/**
 * Define las operaciones de consulta de parámetros específicos asociados al
 * proceso de envío de correos.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/13
 */
public interface IEmailParameterPort {

    /**
     * Consulta la URL del servicio de envío de correos de torre de control
     *
     * @return {@link String} Retorna la url contenida en el parámetro.
     * @throws ApplicationException Excepción en caso de error al identificar la url
     * @author Gildardo Mora
     */
    String findUrlEmailService() throws ApplicationException;

    /**
     * Consulta la contraseña del servicio de envío de correos de torre de control
     *
     * @return {@link String} Retorna la contraseña contenida en el parámetro.
     * @throws ApplicationException Excepción en caso de error al identificar la contraseña
     * @author Gildardo Mora
     */
    String findPassEmailService() throws ApplicationException;

    /**
     * Consulta el usuario del servicio de envío de correos de torre de control
     *
     * @return {@link String} Retorna el usuario contenido en el parámetro.
     * @throws ApplicationException Excepción en caso de error al identificar el usuario
     * @author Gildardo Mora
     */
    String findUserEmailService() throws ApplicationException;

    /**
     * Consulta el usuario de sistema del servicio de envío de correos de torre de control.
     *
     * @return {@link String} Retorna el usuario de sistema contenido en el parámetro
     * @throws ApplicationException Excepción en caso de error al identificar el usuario de sistema
     * @author Gildardo Mora
     */
    String findSystemEmailService() throws ApplicationException;

}
