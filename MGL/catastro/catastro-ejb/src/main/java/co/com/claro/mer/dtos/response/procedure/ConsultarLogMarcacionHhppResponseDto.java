package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.CursorType;
import co.com.claro.mer.annotations.StoredProcedureData;
import co.com.claro.mgl.dtos.CmtLogMarcacionesHhppVtDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * DTO que representa la respuesta del procedimiento que consulta
 * los registros de la tabla log de marcaciones HHPP virtual.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/06/02
 * @see co.com.claro.mgl.dao.impl.cm.CmtLogMarcacionesHhppVtMglDaoImpl
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ConsultarLogMarcacionHhppResponseDto {

    /**
     * Listado de registros de la tabla log de marcaciones HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_LOG_MARCACIONES_LIST", parameterType = Class.class)
    @CursorType(type = CmtLogMarcacionesHhppVtDto.class)
    private List<CmtLogMarcacionesHhppVtDto> logMarcacionesList;

}
