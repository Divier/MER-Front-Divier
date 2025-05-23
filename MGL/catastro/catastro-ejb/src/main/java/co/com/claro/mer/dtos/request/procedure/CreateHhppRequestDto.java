package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.ProcedureType;
import co.com.claro.mer.annotations.StoredProcedureData;
import co.com.claro.mer.homepassed.dto.HomePassedDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que representa el objeto de entrada para el procedimiento almacenado CREATE_HHPP_SP.
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/07/29
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
public class CreateHhppRequestDto {

    @StoredProcedureData(parameterName = "PI_OPCION", parameterType = Integer.class)
    private Integer option;

    @ProcedureType(nameOfType = "HHPP_DATA_TYPE")
    @StoredProcedureData(parameterName = "PI_HHPP_DATA", parameterType = HomePassedDto.class)
    private HomePassedDto homePassedDto;

}
