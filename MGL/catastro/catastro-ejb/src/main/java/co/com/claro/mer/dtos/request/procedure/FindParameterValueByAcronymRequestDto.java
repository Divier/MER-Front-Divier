package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.*;

/**
 * DTO que representa el request para la búsqueda de un valor de parámetro por acrónimo.
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/08/21
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class FindParameterValueByAcronymRequestDto {

    @StoredProcedureData(parameterName = "PI_ACRONIMO", parameterType = String.class)
    private String acronym;

}
