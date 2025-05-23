package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.CursorType;
import co.com.claro.mer.annotations.StoredProcedureData;
import co.com.claro.mer.dtos.sp.cursors.InfoGeneralCargueDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Dto de respuesta del procedimiento almacenado CON_TBL_MASIVOS_SP
 * <p>
 *     Parametros:
 *     <ul>
 *         <li>PO_CODIGO</li>
 *         <li>PO_RESULTADO</li>
 *         <li>PO_REPORTES</li>
 *     </ul>
 * </p>
 * @see co.com.claro.mer.annotations.StoredProcedureData para mas informacion de los parametros
 * @see co.com.claro.mer.annotations.CursorType para mas informacion del cursor
 * @see co.com.claro.mer.dtos.request.procedure.ConTblMasivosRequestDto para mas informacion de los parametros de entrada
 * @author Manuel Hernández
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConTblMasivosResponseDto {

    @StoredProcedureData(parameterName = "PO_REPORTES", parameterType = Class.class)
    @CursorType(type = InfoGeneralCargueDto.class)
    private List<InfoGeneralCargueDto> reportes;

    @StoredProcedureData(parameterName = "PO_CODIGO", parameterType = BigDecimal.class)
    private BigDecimal codigo;

    @StoredProcedureData(parameterName = "PO_RESULTADO", parameterType = String.class)
    private String resultado;

}
