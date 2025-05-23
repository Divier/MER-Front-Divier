package co.com.claro.mer.auth.portal.usuarios.impl;

import co.com.claro.mer.auth.portal.usuarios.IPortalUsuariosClient;
import co.com.claro.mer.auth.portal.usuarios.service.PortalUsuariosService;
import co.com.claro.mer.dtos.request.service.AutenticaRequestDto;
import co.com.claro.mer.dtos.request.service.ConsultaInfoRequestDto;
import co.com.claro.mer.dtos.request.service.ConsultaRolesPorAplicativoPerfilRequestDto;
import co.com.claro.mer.dtos.request.service.ConsultaRolesPorAplicativoUsuarioRequestDto;
import co.com.claro.mer.dtos.response.service.AutenticaResponseDto;
import co.com.claro.mer.dtos.response.service.ConsultaInfoResponseDto;
import co.com.claro.mer.dtos.response.service.ConsultaRolesResponseDto;
import co.com.claro.mgl.error.ApplicationException;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Optional;

import static co.com.claro.mer.utils.enums.ServicesEnum.*;

/**
 * Ejecuta el llamado a las operaciones del servicio de PortalUsuarios
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/08/29
 */
@Log4j2
public class PortalUsuariosClientImpl implements IPortalUsuariosClient {

    private final PortalUsuariosService service;

    public PortalUsuariosClientImpl(String baseUri) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUri)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(PortalUsuariosService.class);
    }

    /**
     * Realiza la autenticación de un usuario en el servicio de PortalUsuarios
     *
     * @param autenticaRequestDto Datos de autenticación del usuario
     * @param endPoint         Endpoint del método a consumir
     * @return Respuesta de la autenticación
     * @throws ApplicationException Error al autenticar el usuario
     * @author Gildardo Mora
     */
    @Override
    public Optional<AutenticaResponseDto> autentica(AutenticaRequestDto autenticaRequestDto, String endPoint) throws ApplicationException {
        try {
            Call<AutenticaResponseDto> call = service.autentica(endPoint, autenticaRequestDto);
            Response<AutenticaResponseDto> response = call.execute();

            if (!response.isSuccessful() || response.body() == null) {
                String msgError;
                try (ResponseBody errorBody = response.errorBody()) {
                    String error = errorBody != null ? errorBody.string() : "";
                    msgError = "Error en la ejecución del método autentica en el servicio: " + error;
                }

                throw new ApplicationException(msgError);
            }

            return Optional.of(response.body());
        } catch (SocketTimeoutException e ) {
            String msgError = "Error al consumir el método autentica del servicio de PortalUsuarios, tiempo de espera agotado";
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        } catch (IOException e) {
            String msgError = "Error al consumir el método autentica del servicio de PortalUsuarios ";
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }

    @Override
    public Optional<ConsultaInfoResponseDto> consultaInfo(ConsultaInfoRequestDto request) throws ApplicationException {
        try {
            Call<ConsultaInfoResponseDto> call = service.consultaInfo(CONSULTA_INFO.getName(), request);
            Response<ConsultaInfoResponseDto> response = call.execute();

            if (!response.isSuccessful() || response.body() == null) {
                String msgError;
                try (ResponseBody errorBody = response.errorBody()) {
                    String error = errorBody != null ? errorBody.string() : "";
                    msgError = "Error en la ejecución del método consultaInfo en el servicio: " + error;
                }

                throw new ApplicationException(msgError);
            }

            return Optional.of(response.body())
                    .filter(info -> !info.getInformacionUsuario().isEmpty());
        }  catch (SocketTimeoutException e ) {
            String msgError = "Error al consumir el método consultaInfo del servicio de PortalUsuarios, tiempo de espera agotado";
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        } catch (Exception e) {
            String msgError = "Error al consumir el método consultaInfo del servicio de PortalUsuarios ";
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }

    @Override
    public Optional<ConsultaRolesResponseDto> consultaRolesPorAplicativoUsuario(
            ConsultaRolesPorAplicativoUsuarioRequestDto request) throws ApplicationException {
        try {
            Call<ConsultaRolesResponseDto> call = service.consultaRolesPorAplicativoUsuario(CONSULTA_ROLES_USER.getName(), request);
            Response<ConsultaRolesResponseDto> response = call.execute();

            if (!response.isSuccessful() || response.body() == null) {
                String msgError;
                try (ResponseBody errorBody = response.errorBody()) {
                    String error = errorBody != null ? errorBody.string() : "";
                    msgError = "Error en la ejecución del método consultaRolesPorAplicativoUsuario en el servicio: " + error;
                }

                throw new ApplicationException(msgError);
            }

            return Optional.of(response.body());
        } catch (IOException e) {
            String msgError = "Error al consumir el método " + CONSULTA_ROLES_USER.getName() + " del servicio de PortalUsuarios ";
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }

    @Override
    public Optional<ConsultaRolesResponseDto> consultaRolesPorAplicativoPerfil(
            ConsultaRolesPorAplicativoPerfilRequestDto request) throws ApplicationException {
        try {
            Call<ConsultaRolesResponseDto> call = service.consultaRolesPorAplicativoPerfil(CONSULTA_ROLES_PERFIL.getName(), request);
            Response<ConsultaRolesResponseDto> response = call.execute();

            if (!response.isSuccessful() || response.body() == null) {
                String msgError;
                try (ResponseBody errorBody = response.errorBody()) {
                    String error = errorBody != null ? errorBody.string() : "";
                    msgError = "Error en la ejecución del método " + CONSULTA_ROLES_PERFIL.getName() + " en el servicio: " + error;
                }

                throw new ApplicationException(msgError);
            }

            return Optional.of(response.body());
        } catch (IOException e) {
            String msgError = "Error al consumir el método consultaRolesPorAplicativoUsuario del servicio de PortalUsuarios ";
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }
}
