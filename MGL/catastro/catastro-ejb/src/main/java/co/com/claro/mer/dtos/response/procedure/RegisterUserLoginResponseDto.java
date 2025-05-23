package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Clase que representa el DTO de respuesta del procedimiento almacenado para registrar la sesión del usuario.
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/08/16
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RegisterUserLoginResponseDto {

    @StoredProcedureData(parameterName = "PO_CODIGO", parameterType = Integer.class)
    private Integer code;

    @StoredProcedureData(parameterName = "PO_RESULTADO", parameterType = String.class)
    private String result;

    @StoredProcedureData(parameterName = "PO_ID_ACCESO", parameterType = BigDecimal.class)
    private BigDecimal idAcceso;

}
