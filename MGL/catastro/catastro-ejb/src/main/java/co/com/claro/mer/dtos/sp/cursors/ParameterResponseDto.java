package co.com.claro.mer.dtos.sp.cursors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa la respuesta del cursor de la búsqueda de un parámetro.
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/08/22
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ParameterResponseDto {

    private String acronym;
    private String value;
    private String description;
    private String parameterType;

}
