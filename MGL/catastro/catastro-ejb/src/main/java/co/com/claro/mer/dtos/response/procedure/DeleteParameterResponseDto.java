package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa la respuesta de la eliminación de un parámetro.
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/08/26
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DeleteParameterResponseDto {

    @StoredProcedureData(parameterName = "PO_CODIGO", parameterType = Integer.class)
    private Integer code;

    @StoredProcedureData(parameterName = "PO_RESULTADO", parameterType = String.class)
    private String result;

}
