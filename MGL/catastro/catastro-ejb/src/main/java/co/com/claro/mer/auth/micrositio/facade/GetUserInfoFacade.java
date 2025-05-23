package co.com.claro.mer.auth.micrositio.facade;

import co.com.claro.mer.auth.micrositio.dto.UserDataDto;
import co.com.claro.mer.auth.micrositio.dao.IGetUserInfo;
import co.com.claro.mer.auth.portal.usuarios.IPortalUsuariosClient;
import co.com.claro.mer.auth.portal.usuarios.impl.PortalUsuariosClientImpl;
import co.com.claro.mer.dtos.request.service.ConsultaInfoRequestDto;
import co.com.claro.mer.dtos.request.service.ConsultaRolesPorAplicativoPerfilRequestDto;
import co.com.claro.mer.dtos.request.service.ConsultaRolesPorAplicativoUsuarioRequestDto;
import co.com.claro.mer.dtos.response.service.ConsultaInfoResponseDto;
import co.com.claro.mer.dtos.response.service.ConsultaRolesResponseDto;
import co.com.claro.mer.dtos.response.service.InformacionUsuarioDto;
import co.com.claro.mer.dtos.response.service.RolesDto;
import co.com.claro.mer.dtos.sp.cursors.UsuarioMicroSitioDto;
import co.com.claro.mer.parametro.dao.IParametroDao;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtOpcionesRolMgl;
import co.com.claro.mgl.utils.ParametrosMerUtil;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static co.com.claro.mer.utils.constants.MicrositioConstants.*;
import static co.com.claro.mer.utils.constants.SessionConstants.*;

/**
 * Fachada para obtener la información del usuario.
 *
 * @author Manuel Hernández Rivas
 * @version 1.0, 2024/09/02
 */
@Stateless
public class GetUserInfoFacade implements GetUserInfoFacadeLocal {


    @EJB
    private IGetUserInfo iGetUserInfo;
    @EJB
    private CmtOpcionesRolMglFacadeLocal opcionesRolMglFacadeLocal;
    @EJB
    private IParametroDao parametroDao;

    /**
     * Consulta la información de un usuario
     *
     * @param usuario Usuario a consultar
     * @param appName Nombre de la aplicación
     * @return {@link UserDataDto} Información del usuario
     * @throws ApplicationException Excepción en caso de error.
     */
    @Override
    public Optional<UserDataDto> consultarUsuario(String usuario, String appName) throws ApplicationException {
        String urlPortalUsuarios = parametroDao.findParameterValueByAcronym(ParametrosMerEnum.BASE_URI_REST_USUARIOS.getAcronimo());
        //NOTE: Para la fase 2, aplicar flag, para consultar usuario solo en la BD o tambien en portal usuarios
        IPortalUsuariosClient portalUsuariosClient = new PortalUsuariosClientImpl(urlPortalUsuarios);
        Optional<UserDataDto> usuarioApp = consultarUsuarioApp(usuario, appName, portalUsuariosClient);

        if (usuarioApp.isPresent()) {
            return usuarioApp;
        }

        String flagMicrositioPortal = ParametrosMerUtil.findValor(FLAG_MICRO_SITIO_PORTAL);//1 encendido, 0 apagado
        boolean isAccessMicrositioPortal = StringUtils.isNotBlank(flagMicrositioPortal) && flagMicrositioPortal.trim().equals("1");

        if (!isAccessMicrositioPortal) {
            return Optional.empty();
        }

        //NOTE: Para la fase 2, Si se aplica el flag, hacer petición al método autentica de portal usuarios
        return consultarUsuarioPortal(usuario, appName, portalUsuariosClient);
    }

    /**
     * Consulta la información del usuario de portal usuarios.
     *
     * @param usuario Usuario a consultar.
     * @param nombreApp Nombre de la aplicación.
     * @param portalUsuariosClient Cliente de portal usuarios.
     * @return {@link Optional<UserDataDto>} con la información del usuario.
     * @throws ApplicationException Error al consultar la información del usuario.
     * @author Gildardo Mora
     */
    private Optional<UserDataDto> consultarUsuarioPortal(String usuario, String nombreApp, IPortalUsuariosClient portalUsuariosClient) throws ApplicationException {
        ConsultaInfoRequestDto infoPortalUsuarioRequest = new ConsultaInfoRequestDto(usuario, StringUtils.EMPTY);
        Optional<ConsultaInfoResponseDto> infoUsuarioPortalUsuarios = portalUsuariosClient.consultaInfo(infoPortalUsuarioRequest);

        if (!infoUsuarioPortalUsuarios.isPresent()) {
            return Optional.empty();
        }

        //Consultar roles del usuario de portal usuarios
        ConsultaRolesPorAplicativoUsuarioRequestDto rolesRequest
                = new ConsultaRolesPorAplicativoUsuarioRequestDto(
                nombreApp, infoUsuarioPortalUsuarios.get().getInformacionUsuario().get(0).getUsuario());
        Optional<ConsultaRolesResponseDto> rolesResponseDto = portalUsuariosClient.consultaRolesPorAplicativoUsuario(rolesRequest);
        List<RolesDto> roles = rolesResponseDto.map(ConsultaRolesResponseDto::getRoles).orElse(Collections.emptyList());
        String idPerfil = rolesResponseDto.map(ConsultaRolesResponseDto::getIdPerfil)
                .filter(StringUtils::isNotBlank)
                .orElseThrow(() -> new ApplicationException("No se encontró el idPerfil del usuario"));
        Map<String, Boolean> stringBooleanMap = verificarRolesMicrositio(roles);
        UserDataDto datosUsuario = new UserDataDto();
        Optional<InformacionUsuarioDto> infoUsuario = infoUsuarioPortalUsuarios
                .map(ConsultaInfoResponseDto::getInformacionUsuario)
                .map(info -> info.get(0));
        datosUsuario.setNombreUsuario(infoUsuario.map(InformacionUsuarioDto::getNombre)
                .filter(StringUtils::isNotBlank)
                .orElseThrow(() -> new ApplicationException("No se encontró el nombre del usuario")));
        datosUsuario.setEmailUsuario(infoUsuario.map(InformacionUsuarioDto::getEmail)
                .filter(StringUtils::isNotBlank)
                .orElseThrow(() -> new ApplicationException("No se encontró el email del usuario")));
        datosUsuario.setIdUsuario(infoUsuario.map(InformacionUsuarioDto::getCedula)
                .filter(StringUtils::isNotBlank)
                .orElseThrow(() -> new ApplicationException("No se encontró la cédula del usuario")));
        datosUsuario.setTelefono(infoUsuario.map(InformacionUsuarioDto::getTelefono)
                .filter(StringUtils::isNotBlank)
                .orElseThrow(() -> new ApplicationException("No se encontró el teléfono del usuario")));
        datosUsuario.setIdentificacion(infoUsuario.map(InformacionUsuarioDto::getCedula)
                .filter(StringUtils::isNotBlank)
                .orElseThrow(() -> new ApplicationException("No se encontró la cédula del usuario")));
        datosUsuario.setIdPerfil(Integer.valueOf(idPerfil));
        datosUsuario.setAccessRol(stringBooleanMap.get(IS_ROL_FAC));
        datosUsuario.setViewMapRol(stringBooleanMap.get(IS_ROL_FAC_MAP));
        return Optional.of(datosUsuario);
    }

    /**
     * Consulta la información del usuario de aplicación para micrositio.
     *
     * @param usuario Usuario a consultar.
     * @param nombreApp Nombre de la aplicación.
     * @param portalUsuariosClient Cliente de portal usuarios.
     * @return {@link Optional<UserDataDto>} con la información del usuario.
     * @throws ApplicationException Error al consultar la información del usuario.
     * @author Gildardo Mora
     */
    private Optional<UserDataDto> consultarUsuarioApp(String usuario, String nombreApp, IPortalUsuariosClient portalUsuariosClient) throws ApplicationException {
        Optional<UsuarioMicroSitioDto> usuarioMicroSitio = iGetUserInfo.consultarUsuarioApp(usuario);

        if (!usuarioMicroSitio.isPresent()) {
            return Optional.empty();
        }

        Integer idPerfil = usuarioMicroSitio.map(UsuarioMicroSitioDto::getIdPerfil)
                .map(BigDecimal::intValue)
                .orElseThrow(() -> new ApplicationException("No se encontró el idPerfil del usuario"));
        //Consultar roles del perfil asociado al usuario de app
        ConsultaRolesPorAplicativoPerfilRequestDto rolesRequest = new ConsultaRolesPorAplicativoPerfilRequestDto(nombreApp, idPerfil.toString());
        Optional<ConsultaRolesResponseDto> rolesResponseDto = portalUsuariosClient.consultaRolesPorAplicativoPerfil(rolesRequest);
        List<RolesDto> roles = rolesResponseDto.map(ConsultaRolesResponseDto::getRoles).orElse(Collections.emptyList());

        if (roles.isEmpty()) {
            throw new ApplicationException("No se encontraron roles para el usuario: " + usuario);
        }

        Map<String, Boolean> rolesMicrositio = verificarRolesMicrositio(roles);
        UserDataDto datosUsuario = new UserDataDto();
        datosUsuario.setNombreUsuario(usuarioMicroSitio.get().getNombre());
        datosUsuario.setEmailUsuario(usuarioMicroSitio.get().getEmail());
        datosUsuario.setIdUsuario(usuarioMicroSitio.map(UsuarioMicroSitioDto::getIdUsuario)
                .map(BigDecimal::toString)
                .orElseThrow(() -> new ApplicationException("No se encontró el id del usuario")));
        datosUsuario.setIdPerfil(idPerfil);
        datosUsuario.setAccessRol(rolesMicrositio.get(IS_ROL_FAC));
        datosUsuario.setViewMapRol(rolesMicrositio.get(IS_ROL_FAC_MAP));
        datosUsuario.setTelefono(usuarioMicroSitio.get().getTelefono());
        datosUsuario.setIdentificacion(usuarioMicroSitio.get().getIdentificacion());
        return Optional.of(datosUsuario);
    }

    /**
     * Verifica si el usuario tiene los roles necesarios para acceder al micrositio.
     *
     * @param listaRoles Lista de roles del usuario.
     * @return Mapa con los roles necesarios para acceder al micrositio.
     * @throws ApplicationException Error al verificar los roles del usuario.
     * @author Gildardo Mora
     */
    private Map<String, Boolean> verificarRolesMicrositio(List<RolesDto> listaRoles) throws ApplicationException {
        String rolAcceso = obtenerRol(ROL_ACCESO_MICROSITIO);
        String rolMapa = obtenerRol(ROL_MAPA_MICROSITIO);
        Set<String> rolesRequeridos = new HashSet<>();
        rolesRequeridos.add(rolAcceso);
        rolesRequeridos.add(rolMapa);
        Set<String> rolesAsignados = listaRoles.stream()
                .map(RolesDto::getCodigoRol)
                .filter(rolesRequeridos::contains)
                .collect(Collectors.toSet());
        boolean isRolFac = rolesAsignados.contains(rolAcceso);
        boolean isRolFacMap = rolesAsignados.contains(rolMapa);
        Map<String, Boolean> result = new HashMap<>();
        result.put(IS_ROL_FAC, isRolFac);
        result.put(IS_ROL_FAC_MAP, isRolFacMap);
        return result;
    }

    /**
     * Obtiene el rol asociado a una funcionalidad.
     *
     * @param funcionalidad Funcionalidad a consultar.
     * @return Rol asociado a la funcionalidad.
     * @throws ApplicationException Error al obtener el rol asociado a la funcionalidad.
     */
    private String obtenerRol(String funcionalidad) throws ApplicationException {
        CmtOpcionesRolMgl opcionesRolMgl =
                opcionesRolMglFacadeLocal.consultarOpcionRol(FORM_MICROSITIO, funcionalidad);
        return opcionesRolMgl.getRol() != null ? opcionesRolMgl.getRol() : "";
    }

}
