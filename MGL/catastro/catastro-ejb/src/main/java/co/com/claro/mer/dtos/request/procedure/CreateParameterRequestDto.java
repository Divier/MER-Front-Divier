package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa el request para la creación de un parámetro
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/08/26
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CreateParameterRequestDto {

    @StoredProcedureData(parameterName = "PI_ACRONIMO", parameterType = String.class)
    private String acronym;

    @StoredProcedureData(parameterName = "PI_VALOR", parameterType = String.class)
    private String value;

    @StoredProcedureData(parameterName = "PI_DESCRIPCION", parameterType = String.class)
    private String description;

    @StoredProcedureData(parameterName = "PI_TIPO_PARAMETRO", parameterType = String.class)
    private String parameterType;

}
