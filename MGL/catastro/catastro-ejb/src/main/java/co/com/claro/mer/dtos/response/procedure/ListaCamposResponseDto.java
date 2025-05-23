package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.CursorType;
import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Dto de respuesta del procedimiento almacenado de metodo Lista filtros
 * <p>
 *     Parametros:
 *     <ul>
 *         <li>PO_CODIGO</li>
 *         <li>PO_RESULTADO</li>
 *         <li>PO_CURSOR</li>
 *     </ul>
 * </p>
 * @see co.com.claro.mer.annotations.StoredProcedureData para mas informacion de los parametros
 * @see co.com.claro.mer.annotations.CursorType para mas informacion del cursor
 * @author Manuel Hernández
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListaCamposResponseDto {

    @StoredProcedureData(parameterName = "PO_CODIGO", parameterType = BigDecimal.class)
    private BigDecimal codigo;

    @StoredProcedureData(parameterName = "PO_RESULTADO", parameterType = String.class)
    private String resultado;

    @StoredProcedureData(parameterName = "PO_CURSOR", parameterType = List.class)
    @CursorType(type = String.class)
    private List<String> cursor;
}
