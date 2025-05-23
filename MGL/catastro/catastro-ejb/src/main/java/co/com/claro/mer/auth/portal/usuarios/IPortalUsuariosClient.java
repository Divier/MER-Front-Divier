package co.com.claro.mer.auth.portal.usuarios;

import co.com.claro.mer.dtos.request.service.AutenticaRequestDto;
import co.com.claro.mer.dtos.request.service.ConsultaInfoRequestDto;
import co.com.claro.mer.dtos.request.service.ConsultaRolesPorAplicativoPerfilRequestDto;
import co.com.claro.mer.dtos.request.service.ConsultaRolesPorAplicativoUsuarioRequestDto;
import co.com.claro.mer.dtos.response.service.AutenticaResponseDto;
import co.com.claro.mer.dtos.response.service.ConsultaInfoResponseDto;
import co.com.claro.mer.dtos.response.service.ConsultaRolesResponseDto;
import co.com.claro.mgl.error.ApplicationException;

import java.util.Optional;

/**
 * Define las operaciones del servicio de PortalUsuarios
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/08/29
 */
public interface IPortalUsuariosClient {

    /**
     * Realiza la autenticación de un usuario en el servicio de PortalUsuarios
     *
     * @param autenticaRequestDto Datos de autenticación del usuario
     * @param endPoint          Endpoint del método a consumir
     * @return Respuesta de la autenticación
     * @throws ApplicationException Error al autenticar el usuario
     * @author Gildardo Mora
     */
    Optional<AutenticaResponseDto> autentica(AutenticaRequestDto autenticaRequestDto, String endPoint) throws ApplicationException;


    /**
     * Consulta la información de un usuario en el servicio de PortalUsuarios
     *
     * @param request         Datos de la consulta de información del usuario
     * @return Respuesta de la consulta de información del usuario
     * @throws ApplicationException Error al consultar la información del usuario
     * @author Manuel Hernandez Rivas
     */
    Optional<ConsultaInfoResponseDto> consultaInfo(ConsultaInfoRequestDto request) throws ApplicationException;

    /**
     * Consulta los roles de un usuario para una aplicacion especificada en el servicio de PortalUsuarios
     *
     * @param request        Datos de la consulta de roles por aplicativo y usuario
     * @return Respuesta de la consulta de roles por aplicativo y usuario
     * @throws ApplicationException Error al consultar los roles del usuario
     * @author Manuel Hernandez Rivas
     */
    Optional<ConsultaRolesResponseDto> consultaRolesPorAplicativoUsuario(
            ConsultaRolesPorAplicativoUsuarioRequestDto request) throws ApplicationException;

    /**
     * Consulta los roles de un perfil para una aplicacion especificada en el servicio de PortalUsuarios
     *
     * @param request        Datos de la consulta de roles por aplicativo y usuario
     * @return Respuesta de la consulta de roles por aplicativo y usuario
     * @throws ApplicationException Error al consultar los roles del usuario
     * @author Manuel Hernandez Rivas
     */
    Optional<ConsultaRolesResponseDto> consultaRolesPorAplicativoPerfil(
            ConsultaRolesPorAplicativoPerfilRequestDto request) throws ApplicationException;
}
