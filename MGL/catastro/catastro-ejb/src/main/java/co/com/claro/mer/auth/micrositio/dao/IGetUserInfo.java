package co.com.claro.mer.auth.micrositio.dao;

import co.com.claro.mer.dtos.sp.cursors.UsuarioMicroSitioDto;
import co.com.claro.mgl.error.ApplicationException;

import java.util.Optional;

/**
 *  Define las operaciones para obtener la información de un usuario.
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/09/20
 */
public interface IGetUserInfo {

    Optional<UsuarioMicroSitioDto> consultarUsuarioApp(String usuario) throws ApplicationException;

}
