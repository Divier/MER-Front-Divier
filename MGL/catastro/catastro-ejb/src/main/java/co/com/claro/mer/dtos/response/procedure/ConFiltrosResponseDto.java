package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.CursorType;
import co.com.claro.mer.annotations.StoredProcedureData;
import co.com.claro.mer.dtos.sp.cursors.ConFiltrosCursorDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Dto de respuesta del procedimiento almacenado CON_FILTROS_SP
 *<p>
 *     Parametros:
 *     <ul>
 *         <li>PO_CODIGO</li>
 *         <li>PO_RESULTADO</li>
 *         <li>PO_FILTROS</li>
 *      </ul>
 * </p>
 *
 * @see co.com.claro.mer.annotations.StoredProcedureData para más información de los parametros
 * @see co.com.claro.mer.annotations.CursorType para más información de los cursores
 * @see co.com.claro.mer.dtos.sp.cursors.ConFiltrosCursorDto para más información del tipo de cursor
 * @author Manuel Hernández
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConFiltrosResponseDto {

    @StoredProcedureData(parameterName = "PO_CODIGO", parameterType = BigDecimal.class)
    private BigDecimal codigo;

    @StoredProcedureData(parameterName = "PO_RESULTADO", parameterType = String.class)
    private String resultado;

    @StoredProcedureData(parameterName = "PO_FILTROS", parameterType = List.class)
    @CursorType(type = ConFiltrosCursorDto.class)
    private List<ConFiltrosCursorDto> cursorDto;


}
