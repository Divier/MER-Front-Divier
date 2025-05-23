package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa el request para la búsqueda de un valor de parámetro por tipo.
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/08/23
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class FindParameterValueByTypeRequestDto {

    @StoredProcedureData(parameterName = "PI_TPO_PARAMETRO", parameterType = String.class)
    private String type;

}
