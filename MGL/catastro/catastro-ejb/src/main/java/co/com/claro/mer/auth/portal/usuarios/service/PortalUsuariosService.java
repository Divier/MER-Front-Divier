package co.com.claro.mer.auth.portal.usuarios.service;

import co.com.claro.mer.dtos.request.service.AutenticaRequestDto;
import co.com.claro.mer.dtos.request.service.ConsultaInfoRequestDto;
import co.com.claro.mer.dtos.request.service.ConsultaRolesPorAplicativoPerfilRequestDto;
import co.com.claro.mer.dtos.request.service.ConsultaRolesPorAplicativoUsuarioRequestDto;
import co.com.claro.mer.dtos.response.service.AutenticaResponseDto;
import co.com.claro.mer.dtos.response.service.ConsultaInfoResponseDto;
import co.com.claro.mer.dtos.response.service.ConsultaRolesResponseDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Url;

/**
 * Define la firma de las operaciones del servicio de PortalUsuarios
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/08/29
 */
public interface PortalUsuariosService {

    /**
     * Realiza la autenticación de un usuario en el servicio de PortalUsuarios
     *
     * @param endpoint endpoint del método a consumir
     * @param autenticaRequestDto Datos del request de autenticación del usuario
     * @return Respuesta de la autenticación
     * @author Gildardo Mora
     */
    @PUT
    Call<AutenticaResponseDto> autentica(@Url String endpoint, @Body AutenticaRequestDto autenticaRequestDto);

    /**
     * Consulta la información de un usuario en el servicio de PortalUsuarios
     * @param endpoint endpoint del método a consumir
     * @param consultaInfoRequestDto Datos del request de consulta de información del usuario
     * @return Respuesta de la consulta de información del usuario
     * @Author Manuel Hernandez Rivas
     */
    @PUT
    Call<ConsultaInfoResponseDto> consultaInfo(@Url String endpoint, @Body ConsultaInfoRequestDto consultaInfoRequestDto);


    /**
     * Consulta los roles de un usuario para una aplicacion especificada en el servicio de PortalUsuarios
     * @param endpoint endpoint del método a consumir
     * @param request Datos del request de consulta de roles por aplicativo y usuario
     * @return Respuesta de la consulta de roles por aplicativo y usuario
     * @Author Manuel Hernandez Rivas
     */
    @PUT
    Call<ConsultaRolesResponseDto> consultaRolesPorAplicativoUsuario(
            @Url String endpoint, @Body ConsultaRolesPorAplicativoUsuarioRequestDto request);

    /**
     * Consulta los roles de un perfil para una aplicacion especificada en el servicio de PortalUsuarios
     * @param endpoint endpoint del método a consumir
     * @param request Datos del request de consulta de roles por aplicativo y usuario
     * @return Respuesta de la consulta de roles por aplicativo y usuario
     * @Author Manuel Hernandez Rivas
     */
    @PUT
    Call<ConsultaRolesResponseDto> consultaRolesPorAplicativoPerfil(
            @Url String endpoint, @Body ConsultaRolesPorAplicativoPerfilRequestDto request);

}
