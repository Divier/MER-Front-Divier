package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.CursorType;
import co.com.claro.mer.annotations.StoredProcedureData;
import co.com.claro.mer.dtos.sp.cursors.ParameterResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * DTO que representa la respuesta de la búsqueda de un parámetro.
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/08/22
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class FindParameterResponseDto {

    @StoredProcedureData(parameterName = "PO_CURSOR", parameterType = Class.class)
    @CursorType(type = ParameterResponseDto.class)
    private List<ParameterResponseDto> cursor;

    @StoredProcedureData(parameterName = "PO_CODIGO", parameterType = Integer.class)
    private Integer code;

    @StoredProcedureData(parameterName = "PO_RESULTADO", parameterType = String.class)
    private String result;

}
