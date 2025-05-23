package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO encargado de almacenar los datos de entrada del procedimiento almacenado SET_USER_SP
 * <p>
 *     Parametros:
 *      <ul>
 *         <li>PI_USUARIO</li>
 *         <li>PI_FLAG</li>
 *      </ul>
 * </p>
 * @see co.com.claro.mer.annotations.StoredProcedureData para mas informacion de la anotacion
 * @see co.com.claro.mer.dtos.response.procedure.SetUserResponseDto para ver el DTO de respuesta
 * @author Manuel Hernández
 */
@Setter
@Getter
@AllArgsConstructor
public class SetUserRequestDto {
    @StoredProcedureData(parameterName = "PI_USUARIO", parameterType = String.class)
    private String usuario;

    @StoredProcedureData(parameterName = "PI_FLAG", parameterType = String.class)
    private String flag;
}
