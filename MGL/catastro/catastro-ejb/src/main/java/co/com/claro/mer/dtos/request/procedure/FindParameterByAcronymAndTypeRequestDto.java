package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa el request para la búsqueda de un parámetro por acrónimo y tipo.
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/08/26
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class FindParameterByAcronymAndTypeRequestDto {

    @StoredProcedureData(parameterName = "PI_ACRONIMO", parameterType = String.class)
    private String acronym;

    @StoredProcedureData(parameterName = "PI_TPO_PARAMETRO", parameterType = String.class)
    private String parameterType;

}
