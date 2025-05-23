package co.com.claro.mer.auth.micrositio.dao;

import co.com.claro.mer.dtos.response.procedure.GetUserInfoResponseDto;
import co.com.claro.mer.dtos.sp.cursors.UsuarioMicroSitioDto;
import co.com.claro.mer.utils.StoredProcedureUtil;
import co.com.claro.mgl.error.ApplicationException;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.ejb.Stateless;
import java.util.Objects;
import java.util.Optional;

import static co.com.claro.mer.constants.StoredProcedureNamesConstants.GET_USER_INFO_SP;

/**
 * Implementación de los procesos de consulta de información de usuario de aplicación para micrositio.
 * @author Manuel Hernandez
 */
@Log4j2
@NoArgsConstructor
@Stateless
public class GetUserInfoImpl implements IGetUserInfo {

    public Optional<UsuarioMicroSitioDto> consultarUsuarioApp(String usuario) throws ApplicationException {
        try {
            final String P_USUARIO = "PI_USUARIO";
            StoredProcedureUtil spUtil = new StoredProcedureUtil(GET_USER_INFO_SP);
            spUtil.addRequestData(P_USUARIO, String.class, usuario);
            GetUserInfoResponseDto response = spUtil.executeStoredProcedure(GetUserInfoResponseDto.class);
            Objects.requireNonNull(response, "No se recibió respuesta válida " + GET_USER_INFO_SP);

            if (response.getCodigo() == -20003) {
                LOGGER.warn(response.getResultado());
                return Optional.empty();
            }

            if (response.getCodigo() != 0) {
                String msgError = "Error en el procedimiento almacenado "
                        + GET_USER_INFO_SP + ", codigo: " + response.getCodigo()
                        + ", resultado: " + response.getResultado();
                LOGGER.error(msgError);
                return Optional.empty();
            }

            return Optional.of(response.getCursor()).flatMap(result -> result.stream().findFirst());
        } catch (Exception e) {
            String msgError = "Error en el procedimiento almacenado "
                    + GET_USER_INFO_SP +", mensaje: " + e.getMessage();
            LOGGER.error(msgError);
            throw new ApplicationException(msgError, e);
        }
    }
}
