package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.CursorType;
import co.com.claro.mer.annotations.StoredProcedureData;
import co.com.claro.mer.dtos.sp.cursors.OpcionesCarguesDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Dto de respuesta del procedimiento almacenado CON_TBL_MASIVOS_NOMBRES_SP
 * <p>
 *     Parametros:
 *     <ul>
 *         <li>PO_CODIGO</li>
 *         <li>PO_RESULTADO</li>
 *         <li>PO_REPORTES</li>
 *     </ul>
 *  </p>
 *
 *  @see co.com.claro.mer.annotations.StoredProcedureData para mas informacion de los parametros
 *  @see co.com.claro.mer.annotations.CursorType para mas informacion del cursor
 *  @author Manuel Hernández
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConTblMasivosNombresResponseDto {

    @StoredProcedureData(parameterName = "PO_REPORTES", parameterType = Class.class)
    @CursorType(type = OpcionesCarguesDto.class)
    private List<OpcionesCarguesDto> reportes;

    @StoredProcedureData(parameterName = "PO_CODIGO", parameterType = int.class)
    private int codigo;

    @StoredProcedureData(parameterName = "PO_RESULTADO", parameterType = String.class)
    private String resultado;

}
