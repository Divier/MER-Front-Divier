package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Request para el procedimiento almacenado CON_TBL_LINEAS_MASIVO
 * <p>
 *     Parametros:
 *     <ul>
 *         <li>PI_NOMBRE</li>
 *         <li>PI_TIPO</li>
 *         <li>PI_LIN_MASIVO_ID</li>
 *         <li>PI_USUARIO</li>
 *      </ul>
 * </p>
 * @see StoredProcedureData para la anotacion de parametros
 * @see co.com.claro.mer.dtos.response.procedure.ConTblLineasMasivoResponseDto para el response del procedimiento
 * @author Manuel Hernández
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConTblLineasMasivoRequestDto {

    @StoredProcedureData(parameterName = "PI_NOMBRE", parameterType = String.class)
    private String nombre;

    @StoredProcedureData(parameterName = "PI_TIPO", parameterType = String.class)
    private String tipo;

    @StoredProcedureData(parameterName = "PI_LIN_MASIVO_ID", parameterType = BigDecimal.class)
    private BigDecimal linMasivoId;

    @StoredProcedureData(parameterName = "PI_USUARIO", parameterType = String.class)
    private String usuario;
}
