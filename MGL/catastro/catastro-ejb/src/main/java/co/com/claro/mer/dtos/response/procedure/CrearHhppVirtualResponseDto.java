package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa la respuesta del procedimiento de creación de HPP virtual.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/06/02
 * @see co.com.claro.mgl.dao.impl.HhppVirtualMglDaoImpl
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearHhppVirtualResponseDto {

    @StoredProcedureData(parameterName = "PO_RESPUESTA", parameterType = String.class)
    private String respuesta;

    @StoredProcedureData(parameterName = "PO_CODIGO", parameterType = Integer.class)
    private Integer codigo;

    @StoredProcedureData(parameterName = "PO_DESCRIPCION", parameterType = String.class)
    private String descripcion;
}
