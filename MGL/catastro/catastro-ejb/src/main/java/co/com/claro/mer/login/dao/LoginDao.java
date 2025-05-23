package co.com.claro.mer.login.dao;

import co.com.claro.mer.auth.portal.usuarios.IPortalUsuariosClient;
import co.com.claro.mer.auth.portal.usuarios.impl.PortalUsuariosClientImpl;
import co.com.claro.mer.dtos.request.procedure.RegisterUserLoginRequestDto;
import co.com.claro.mer.dtos.request.service.AutenticaRequestDto;
import co.com.claro.mer.dtos.response.procedure.RegisterUserLoginResponseDto;
import co.com.claro.mer.dtos.response.service.AutenticaResponseDto;
import co.com.claro.mer.dtos.response.service.UsuarioPortalResponseDto;
import co.com.claro.mer.parametro.facade.ParametroFacadeLocal;
import co.com.claro.mer.utils.DateToolUtils;
import co.com.claro.mer.utils.ServerInfoUtil;
import co.com.claro.mer.utils.StoredProcedureUtil;
import co.com.claro.mer.utils.StringToolUtils;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ParametrosMerUtil;
import co.com.telmex.catastro.data.DatosSesionMgl;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static co.com.claro.mer.constants.StoredProcedureNamesConstants.INSERT_SESSION;
import static co.com.claro.mer.utils.enums.ParametrosMerEnum.*;

/**
 * Clase que implementa la interfaz ILoginDao y se encarga de registrar la sesión del usuario.
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/08/16
 */
@Log4j2
@Stateless
public class LoginDao implements ILoginDao {

    @EJB
    private ParametroFacadeLocal parametroFacade;

    private static final String LLAVE_ENCRIPTACION = "MGL_LOGIN_ENCRIPTAR_LLAVE";

    /**
     * Método que registra la sesión del usuario.
     *
     * @param sessionData Datos de la sesión del usuario.
     * @return {@link BigDecimal} Id de la sesión del usuario.
     * @throws ApplicationException Excepción en caso de error.
     * @author Gildardo Mora
     */
    @Override
    public BigDecimal registerUserLogin(DatosSesionMgl sessionData) throws ApplicationException {
        try {
            StoredProcedureUtil spUtil = new StoredProcedureUtil(INSERT_SESSION);
            RegisterUserLoginRequestDto requestDto = new RegisterUserLoginRequestDto();
            String user = Optional.ofNullable(sessionData)
                    .map(DatosSesionMgl::getUsuarioMgl)
                    .map(UsuarioPortalResponseDto::getUsuario)
                    .orElse("USER");
            requestDto.setUser(user);
            Integer userProfile = Optional.ofNullable(sessionData)
                    .map(DatosSesionMgl::getUsuarioMgl)
                    .map(UsuarioPortalResponseDto::getIdPerfil)
                    .map(Integer::parseInt)
                    .orElse(0);
            requestDto.setUserProfile(userProfile);
            requestDto.setServerName(ServerInfoUtil.getServerName());
            requestDto.setClusterName(ServerInfoUtil.getClusterName());
            spUtil.addRequestData(requestDto);
            RegisterUserLoginResponseDto registerUserLoginResponseDto = spUtil.executeStoredProcedure(RegisterUserLoginResponseDto.class);

            if (registerUserLoginResponseDto.getCode() == null || registerUserLoginResponseDto.getCode() != 0) {
                LOGGER.error("No se pudo registrar la sesión del usuario: {} ", registerUserLoginResponseDto.getResult());
            }

            return registerUserLoginResponseDto.getIdAcceso();
        } catch (ApplicationException ae) {
            LOGGER.error("Error al registrar la sesión del usuario: {}", ae.getMessage());
            throw ae;
        } catch (Exception e) {
            String msgError = "Error al registrar la sesión del usuario." + e.getMessage();
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError);
        }
    }

    /**
     * Método que autentica al usuario.
     *
     * @param user Usuario a autenticar
     * @param password Contraseña del usuario
     * @return {@link Optional<DatosSesionMgl>} Datos de la sesión del usuario
     * @throws ApplicationException Excepción en caso de error
     * @author Gildardo Mora
     */
    @Override
    public Optional<DatosSesionMgl> authenticateUser(String user, String password) throws ApplicationException {
        if (StringUtils.isBlank(user) || StringUtils.isBlank(password)) {
            throw new ApplicationException("El usuario o la contraseña no pueden estar vacíos.");
        }

        String key = parametroFacade.findParameterValueByAcronym(LLAVE_ENCRIPTACION);

        if (StringUtils.isBlank(key)) {
            throw new ApplicationException("No se encuentra configurado el parámetro '" + LLAVE_ENCRIPTACION + "'.");
        }

        try {
            String generatedPass = generatePass(password, key);
            String baseUriAuth = parametroFacade.findParameterValueByAcronym(BASE_URI_AUTENTICACION.getAcronimo());
            AutenticaRequestDto autenticaRequestDto = new AutenticaRequestDto(user, generatedPass, ParametrosMerUtil.findValor(APP_NAME.getAcronimo()));
            String endPoint = ParametrosMerUtil.findValor(ENDPOINT_AUTENTICACION.getAcronimo());
            IPortalUsuariosClient portalUsuariosClient = new PortalUsuariosClientImpl(baseUriAuth);
            Optional<AutenticaResponseDto> autenticaResponse = portalUsuariosClient.autentica(autenticaRequestDto, endPoint);

            if (!autenticaResponse.isPresent()) {
                String msgError = "No hubo respuesta del servicio de autenticación para el usuario " + user;
                LOGGER.error(msgError);
                throw new ApplicationException("No hubo respuesta del servicio de autenticación.");
            }

            return Optional.ofNullable(assignSessionData(autenticaResponse.get()));
        } catch (ApplicationException e) {
            String msgError = "Error al autenticar el usuario: " + e.getMessage();
            LOGGER.error(msgError, e);
            throw e;
        } catch (Exception e) {
            String msgError = "Error al autenticar el usuario: " + e.getMessage();
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError);
        }
    }

    /**
     * Asigna los datos de la sesión a partir de la respuesta de autenticación.
     * @param autenticaResponseDto {@link AutenticaResponseDto} Respuesta de autenticación.
     * @return {@link DatosSesionMgl} Datos de la sesión.
     * @author Gildardo Mora
     */
    private DatosSesionMgl assignSessionData(AutenticaResponseDto autenticaResponseDto) {
        return DatosSesionMgl.builder()
                .accesoAutorizado(true)
                .estadoSesion(autenticaResponseDto.getEstado())
                .fechaExpiracionToken(autenticaResponseDto.getFechaExpiracionToken())
                .tokenSession(autenticaResponseDto.getTokenSession())
                .usuarioMgl(autenticaResponseDto.getUsuario())
                .build();
    }

    /**
     * Genera la contraseña encriptada.
     *
     * @param pass Contraseña a encriptar.
     * @param llave Llave de encriptación.
     * @return {@link String} Contraseña encriptada.
     * @throws ApplicationException Excepción en caso de error.
     * @author Gildardo Mora
     */
    private String generatePass(String pass, String llave) throws ApplicationException {
        try {
            String claveEcriptada = pass + "|" + DateToolUtils.formatoFechaLocalDate(LocalDate.now(), "yyyy-MM-dd");
            return StringToolUtils.encriptarTexto(claveEcriptada, llave.trim());
        } catch (Exception ex) {
            String msgError = "Error al encriptar la contraseña: " + ex.getMessage();
            LOGGER.error(msgError, ex.getMessage());
            throw new ApplicationException(msgError);
        }
    }

}
