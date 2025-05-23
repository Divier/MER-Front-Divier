package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * DTO que representa la respuesta de ejecución del procedimiento almacenado
 * encargado de la actualización de marcación de HPP virtual.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/06/02
 * @see co.com.claro.mgl.dao.impl.cm.CmtMarcacionesHhppVtMglDaoImpl
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ActualizaMarcacionHhppVirtualResponseDto {

    /**
     * Mapea la respuesta del procedimiento almacenado de la actualización de
     * marcación de HPP virtual.
     */
    @StoredProcedureData(parameterName = "V_RESULTADO", parameterType = String.class)
    private String resultado;

}
