package co.com.claro.mer.email.infraestructure.adapters;

import co.com.claro.mer.email.application.ports.IEmailParameterPort;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ParametrosMerUtil;

/**
 * Adaptador para procesar las operaciones de consulta de parámetros asociados
 * al proceso de envío de correos.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/13
 */
public class ParameterEmailAdapter implements IEmailParameterPort {

    /**
     * Consulta la URL del servicio de envío de correos de torre de control
     *
     * @return {@link String} Retorna la url contenida en el parámetro.
     * @throws ApplicationException Excepción en caso de error al identificar la url
     * @author Gildardo Mora
     */
    @Override
    public String findUrlEmailService() throws ApplicationException {
        return ParametrosMerUtil.findValor(ParametrosMerEnum.URL_EMAIL_SERVICE_TORRE_CONTROL);
    }

    /**
     * Consulta la contraseña del servicio de envío de correos de torre de control
     *
     * @return {@link String} Retorna la contraseña contenida en el parámetro.
     * @throws ApplicationException Excepción en caso de error al identificar la contraseña
     * @author Gildardo Mora
     */
    @Override
    public String findPassEmailService() throws ApplicationException {
        return ParametrosMerUtil.findValor(ParametrosMerEnum.PASS_EMAIL_SERVICE_TORRE_CONTROL);
    }

    /**
     * Consulta el usuario del servicio de envío de correos de torre de control
     *
     * @return {@link String} Retorna el usuario contenido en el parámetro.
     * @throws ApplicationException Excepción en caso de error al identificar el usuario
     * @author Gildardo Mora
     */
    @Override
    public String findUserEmailService() throws ApplicationException {
        return ParametrosMerUtil.findValor(ParametrosMerEnum.USER_EMAIL_SERVICE_TORRE_CONTROL);
    }

    /**
     * Consulta el usuario de sistema del servicio de envío de correos de torre de control.
     *
     * @return {@link String} Retorna el usuario de sistema contenido en el parámetro
     * @throws ApplicationException Excepción en caso de error al identificar el usuario de sistema
     * @author Gildardo Mora
     */
    @Override
    public String findSystemEmailService() throws ApplicationException {
        return ParametrosMerUtil.findValor(ParametrosMerEnum.SYSTEM_EMAIL_SERVICE_TORRE_CONTROL);
    }

}
