package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Clase que representa el objeto de salida para el procedimiento almacenado CREATE_HHPP_SP.
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/07/29
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CreateHhppResponseDto {

    @StoredProcedureData(parameterName = "PO_HHPP_ID", parameterType = BigDecimal.class)
    private BigDecimal idHhpp;

    @StoredProcedureData(parameterName = "PO_CODIGO", parameterType = Integer.class)
    private Integer code;

    @StoredProcedureData(parameterName = "PO_RESULTADO", parameterType = String.class)
    private String description;

}
