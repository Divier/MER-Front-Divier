package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request para el procedimiento almacenado CON_TBL_MASIVOS
 * <p>
 *     Parametros:
 *     <ul>
 *         <li>PI_NOMBRE</li>
 *     </ul>
 * </p>
 * @see StoredProcedureData para la anotacion de parametros
 * @see co.com.claro.mer.dtos.response.procedure.ConTblMasivosResponseDto para el response del procedimiento
 * @author Manuel Hernández
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConTblMasivosRequestDto {

    @StoredProcedureData(parameterName = "PI_NOMBRE", parameterType = String.class)
    private String nombre;

}
