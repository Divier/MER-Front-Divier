package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.CursorType;
import co.com.claro.mer.annotations.StoredProcedureData;
import co.com.claro.mer.dtos.sp.cursors.MarcacionesHhppVirtualDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * DTO que representa la respuesta del procedimiento
 * de consulta de marcaciones de HHPP virtual.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/06/02
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ConsultaMarcacionHhppVirtualResponseDto {

    /**
     * Mapea la lista de marcaciones de HHPP virtual encontradas.
     */
    @StoredProcedureData(parameterName = "V_MARCACIONES_ENCONTRADAS", parameterType = Class.class)
    @CursorType(type = MarcacionesHhppVirtualDto.class)
    private List<MarcacionesHhppVirtualDto> marcaciones;

}
