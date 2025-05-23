package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa la respuesta de ejecución del procedimiento
 * que consulta los registros de marcaciones de HHPP virtual.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/06/02
 * @see co.com.claro.mgl.dao.impl.cm.CmtLogMarcacionesHhppVtMglDaoImpl
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RegistrarLogMarcacionHhppVirtualResponseDto {

    /**
     * Mapea el valor de la respuesta de la ejecución del procedimiento
     * que consulta los registros de marcaciones de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_RESULTADO", parameterType = String.class)
    private String resultado;

}
