package co.com.claro.mer.auth.micrositio.facade;

import co.com.claro.mgl.error.ApplicationException;

import java.math.BigDecimal;

/**
 * Interfaz que define los métodos para el acceso a datos de la sesión del usuario de micrositio.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/03
 */
public interface LoginMicroSitioFacadeLocal {

    /**
     * Realiza el registro de la sesión del usuario de micrositio.
     * @param user Usuario.
     * @param profile Perfil del usuario.
     * @return {@link BigDecimal} Id de la sesión registrada del usuario de micrositio.
     * @throws ApplicationException Excepción en caso de error.
     */
    BigDecimal registerUserLogin(String user, String profile) throws ApplicationException;

}
