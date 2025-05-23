package co.com.claro.mer.auth.micrositio.dao.impl;

import co.com.claro.mer.auth.micrositio.dao.ILoginMicrositioDao;
import co.com.claro.mer.dtos.request.procedure.RegisterUserLoginMicrositioRequestDto;
import co.com.claro.mer.dtos.response.procedure.RegisterUserLoginMicrositioResponseDto;
import co.com.claro.mer.utils.ServerInfoUtil;
import co.com.claro.mer.utils.StoredProcedureUtil;
import co.com.claro.mgl.error.ApplicationException;
import lombok.extern.log4j.Log4j2;

import javax.ejb.Stateless;
import java.math.BigDecimal;

import static co.com.claro.mer.constants.StoredProcedureNamesConstants.INSERT_MICROSITIO_SESSION;

/**
 * Clase que implementa la interfaz ILoginMicrositioDao y se encarga de registrar la sesión del usuario de micrositio.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/03
 */
@Log4j2
@Stateless
public class LoginMicrositioDaoImpl implements ILoginMicrositioDao {

    /**
     * Método que registra la sesión del usuario de micrositio.
     * @param user Usuario.
     * @param profile Perfil del usuario.
     * @return {@link BigDecimal} Id de la sesión registrada del usuario.
     * @throws ApplicationException Excepción en caso de error.
     */
    @Override
    public BigDecimal registerUserLogin(String user, String profile) throws ApplicationException {
        try {
            StoredProcedureUtil spUtil = new StoredProcedureUtil(INSERT_MICROSITIO_SESSION);
            RegisterUserLoginMicrositioRequestDto requestDto = new RegisterUserLoginMicrositioRequestDto();
            requestDto.setUser(user);
            requestDto.setUserProfile(Integer.parseInt(profile));
            requestDto.setServerName(ServerInfoUtil.getServerName());
            requestDto.setClusterName(ServerInfoUtil.getClusterName());
            spUtil.addRequestData(requestDto);
            RegisterUserLoginMicrositioResponseDto registerUserLoginResponseDto = spUtil.executeStoredProcedure(RegisterUserLoginMicrositioResponseDto.class);

            if (registerUserLoginResponseDto.getCode() == null || registerUserLoginResponseDto.getCode() != 0) {
                String msg = "No se pudo registrar la sesión del usuario de micrositio: "+  registerUserLoginResponseDto.getResult();
                throw new ApplicationException(msg);
            }

            return registerUserLoginResponseDto.getIdAcceso();
        } catch (ApplicationException ae) {
            LOGGER.error("Error al registrar la sesión del usuario de micrositio {}", ae.getMessage());
            throw ae;
        }catch (Exception e) {
            String message = "Error al registrar la sesión del usuario de micrositio";
            throw new ApplicationException(message, e);
        }
    }

}
