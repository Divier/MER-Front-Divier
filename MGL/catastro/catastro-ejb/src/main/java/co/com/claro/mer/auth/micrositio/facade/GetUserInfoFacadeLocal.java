package co.com.claro.mer.auth.micrositio.facade;

import co.com.claro.mer.auth.micrositio.dto.UserDataDto;
import co.com.claro.mgl.error.ApplicationException;

import javax.ejb.LocalBean;
import java.util.Optional;

/**
 * Define las operaciones para obtener la información de un usuario
 *
 * @author Manuel Hernández Rivas
 * @version 1.0, 2024/09/02
 */
@LocalBean
public interface GetUserInfoFacadeLocal {

    /**
     * Consulta la información de un usuario
     *
     * @param usuario Usuario a consultar
     * @param appName Nombre de la aplicación
     * @return {@link UserDataDto} Información del usuario
     * @throws ApplicationException Excepción en caso de error.
     */
    Optional<UserDataDto> consultarUsuario(String usuario, String appName) throws ApplicationException;

}
