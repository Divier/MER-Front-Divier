package co.com.claro.mer.login.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.DatosSesionMgl;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Fachada que define las operaciones que se pueden realizar sobre el proceso de login.
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/08/16
 */
@Local
public interface LoginFacadeLocal {

    /**
     * Método que registra la sesión del usuario.
     *
     * @param sessionData Datos de la sesión del usuario.
     * @return {@link BigDecimal} Id de la sesión del usuario.
     * @throws ApplicationException Excepción en caso de error.
     * @author Gildardo Mora
     */
    BigDecimal registerUserLogin(DatosSesionMgl sessionData) throws ApplicationException;

    /**
     * Método que autentica al usuario.
     *
     * @param user Usuario a autenticar
     * @param pass Contraseña del usuario
     * @return {@link Optional<DatosSesionMgl>} Datos de la sesión del usuario
     * @throws ApplicationException Excepción en caso de error
     * @author Gildardo Mora
     */
    Optional<DatosSesionMgl> authenticateUser(String user, String pass) throws ApplicationException;

}
